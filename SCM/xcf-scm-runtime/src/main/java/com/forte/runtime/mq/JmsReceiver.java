package com.forte.runtime.mq;

import com.aliyun.openservices.ons.api.*;
import com.forte.runtime.spring.AppContextConfig;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.*;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/***
 * @desc
 * @author wangbin
 * @date 2015/8/18
 */
public abstract class JmsReceiver implements MessageListener{
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private DefaultMessageListenerContainer messageListenerContainer;
    private static PooledConnectionFactory factory;
    private final AtomicBoolean initialized = new AtomicBoolean(false);
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    protected int idx = 1;
    protected ExecutorService executor = null;

    protected abstract Destination getDestination();
    protected void initExecutor(){

    }
    private String messageSelector;
    private int concurrentConsumers = 1;//Runtime.getRuntime().availableProcessors();
    private String destinationName = "test.queue";
    private String brokerURL = AppContextConfig.get("brokerURL");
    private String mqUser = AppContextConfig.get("mq.user");
    private String mqPassword = AppContextConfig.get("mq.password");
    private ReceiveHandler receiveHandler;
    private int maxConnections = 10;
    private int threadCoreSize = 3 * Runtime.getRuntime().availableProcessors();
    private int threadMaxSize = 25 * Runtime.getRuntime().availableProcessors();
    private long keepAlive = 600L;
    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public String getMessageSelector() {
        return messageSelector;
    }

    public void setMessageSelector(String messageSelector) {
        this.messageSelector = messageSelector;
    }

    private void createConFactory(){
        if(factory == null) {
            factory = new PooledConnectionFactory();
            ActiveMQConnectionFactory act = new ActiveMQConnectionFactory();
            act.setUserName(mqUser);
            act.setPassword(mqPassword);
            act.setBrokerURL(this.brokerURL);
            act.setCloseTimeout(6000);
            act.setOptimizedAckScheduledAckInterval(1000);
            factory.setConnectionFactory(act);
            factory.setMaxConnections(maxConnections);
            //factory.setTargetConnectionFactory(act);
            //factory.setBlockIfSessionPoolIsFull(false);
        }
    }
    protected boolean isMqMessageEnabled(){
        return !"false".equals(AppContextConfig.get("common.message.enabled"));
    }
    protected boolean isAliyunMessageEnabled(){
        return "true".equals(AppContextConfig.get("aliyun.message.enabled"));
    }
    public void init(){
        if(isAliyunMessageEnabled()){
            logger.info("************aliyun.message.enabled=true**************");
            this.initExecutor();
            this.initAliyunConsumer();
            return;
        }
        if(!isMqMessageEnabled()){
            logger.warn("*************common.message.enabled=false*****************");
            return;
        }

        Lock l = lock.writeLock();
        if(initialized.compareAndSet(false,true)) {
            try {
                l.lock();
                createConFactory();
                this.initExecutor();
                messageListenerContainer = new DefaultMessageListenerContainer();
                messageListenerContainer.setConnectionFactory(factory);
                messageListenerContainer.setSessionTransacted(false);
                messageListenerContainer.setAutoStartup(true);
                messageListenerContainer.setMessageListener(this);
                messageListenerContainer.setConcurrentConsumers(1);
                //messageListenerContainer.setMaxConcurrentConsumers(3 * Runtime.getRuntime().availableProcessors());
                //messageListenerContainer.setConcurrency("1-20");
                messageListenerContainer.setDestination(this.getDestination());
                messageListenerContainer.setCacheLevel(DefaultMessageListenerContainer.CACHE_SESSION);
                messageListenerContainer.setMessageSelector(this.getMessageSelector());
                messageListenerContainer.setAcceptMessagesWhileStopping(false);
                messageListenerContainer.initialize();
                messageListenerContainer.start();
                messageListenerContainer.setExceptionListener(new ExceptionListener() {
                    @Override
                    public void onException(JMSException e) {
                        logger.error("mq-consumer-exception occur,detail:",e);
                    }
                });
                logger.info("================first-init--listener-container-for:{},broker={},user={}",
                        this.getDestinationName(),this.brokerURL,this.mqUser);
            }finally {
                l.unlock();
            }
        }else {
            logger.info("================listener-container-for {} initialized,thread={}",
                    this.getDestinationName(),Thread.currentThread().getName());
        }
    }

    @Override
    public void onMessage(final Message message) {
        logger.info("================>onMessage called,destination={}",destinationName);
        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                logger.error("onMessage-uncaught-exception,{}",t.getName());
            }
        });
        if(receiveHandler!=null) {
            if(message instanceof ActiveMQObjectMessage){
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        long start = System.currentTimeMillis();
                        try {
                            receiveHandler.handle(((ActiveMQObjectMessage) message).getObject());
                        }catch (JMSException jmsex){
                            logger.error("handle-message-jms-error,{}",message,jmsex);
                        }catch (Exception ex){
                            logger.error("handle-message-error,{}",message,ex);
                        }finally {
                            logger.info("mq-consumer:"+Thread.currentThread().getName()+"-time-cost:{}s",(System.currentTimeMillis()-start)/1000.0);
                        }
                    }
                };
                /*task.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        logger.error("uncaught-exception,{}",t.getName());
                    }
                });*/
                executor.execute(task);
            }else if(message instanceof ActiveMQTextMessage){
            	executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        long start = System.currentTimeMillis();
                        try {
                            receiveHandler.handle(((ActiveMQTextMessage) message).getText());
                        }catch (JMSException jmsex){
                            logger.error("handle-message-jms-error,{}",message,jmsex);
                        }catch (Exception ex){
                            logger.error("handle-message-error,{}",message,ex);
                        }finally {
                            logger.info("mq-consumer:"+Thread.currentThread().getName()+"-time-cost:{}s",(System.currentTimeMillis()-start)/1000.0);
                        }
                    }
                });
            }
        }
    }

    /*{
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                logger.info("shutdownHook call close...");
                if(factory!=null){
                    factory.stop();
                    factory.clear();
                }
                if(messageListenerContainer!=null) {
                    messageListenerContainer.stop();
                    messageListenerContainer.destroy();
                }
            }
        }, "MqReceiverShutdownHook-"+Thread.currentThread().getName()));
    }*/

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public void setReceiveHandler(ReceiveHandler receiveHandler) {
        this.receiveHandler = receiveHandler;
    }

    public int getConcurrentConsumers() {
        return concurrentConsumers;
    }

    public void setConcurrentConsumers(int concurrentConsumers) {
        this.concurrentConsumers = concurrentConsumers;
    }
    public String getDestinationName() {
        return destinationName;
    }

    public void setBrokerURL(String brokerURL) {
        this.brokerURL = brokerURL;
    }

    public void setMqUser(String mqUser) {
        this.mqUser = mqUser;
    }

    public void setMqPassword(String mqPassword) {
        this.mqPassword = mqPassword;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public int getThreadCoreSize() {
        return threadCoreSize;
    }

    public void setThreadCoreSize(int threadCoreSize) {
        this.threadCoreSize = threadCoreSize;
    }

    public int getThreadMaxSize() {
        return threadMaxSize;
    }

    public void setThreadMaxSize(int threadMaxSize) {
        this.threadMaxSize = threadMaxSize;
    }

    public long getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(long keepAlive) {
        this.keepAlive = keepAlive;
    }

    /**/
    private AtomicBoolean consumerInitialized = new AtomicBoolean(false);
    private Consumer consumer = null;
    protected boolean isTopic = false;
    protected Consumer initAliyunConsumer(){
        if(consumerInitialized.compareAndSet(false,true)) {
            Properties properties = new Properties();
            //您在控制台创建的Consumer ID
            properties.put(PropertyKeyConst.ConsumerId, AppContextConfig.get("aliyun.consumerId"));
            // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
            properties.put(PropertyKeyConst.AccessKey, AppContextConfig.get("aliyun.password.accessKey"));
            // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
            properties.put(PropertyKeyConst.SecretKey, AppContextConfig.get("aliyun.password.secretKey"));
            if(isTopic) {
                properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.BROADCASTING);
            }
            consumer = ONSFactory.createConsumer(properties);
            consumer.subscribe(this.destinationName, "*", new com.aliyun.openservices.ons.api.MessageListener() {
                @Override
                public Action consume(com.aliyun.openservices.ons.api.Message message, ConsumeContext consumeContext) {
                    return consumeOut(message,consumeContext);
                }
            });

            logger.info("================first-init--listener-container-for:{},user={}",
                    this.getDestinationName(),properties.getProperty(PropertyKeyConst.ConsumerId));
            consumer.start();
        }
        return consumer;
    }

    //@Override
    public Action consumeOut(final com.aliyun.openservices.ons.api.Message message, ConsumeContext consumeContext) {
        logger.info("================>consume-aliyun-msg-called,destination={}",destinationName);
        if(receiveHandler!=null) {
           executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(receiveHandler!=null) {
                            receiveHandler.handle(getObject(message.getBody()));
                        }
                    }catch (Exception ex){
                        logger.error("handle-message-error,{}",message,ex);
                    }
                }
           });
           return Action.CommitMessage;
        }
        return Action.ReconsumeLater;
    }
    public Serializable getObject(byte[] obj){
        if(obj == null){
            return null;
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(obj);
        ObjectInputStream in = null;
        Serializable ret = null;
        try{
            in = new ObjectInputStream(bis);
            ret = (Serializable)in.readObject();
        }catch (Exception ex){
            logger.error("gen-object-from-byte-array-error:",ex);
        }finally {
            if(in!=null) {
                try {
                    bis.close();
                    in.close();
                }catch (Exception ex){
                    logger.error("close-byte-output-stream-error:",ex);
                }
            }
        }
        return ret;
    }
}
