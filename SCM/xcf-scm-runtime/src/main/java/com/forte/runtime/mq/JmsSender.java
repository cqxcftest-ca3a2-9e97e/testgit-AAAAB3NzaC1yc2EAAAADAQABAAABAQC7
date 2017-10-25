package com.forte.runtime.mq;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.PropertyValueConst;
import com.forte.runtime.spring.AppContextConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.jms.Destination;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/***
 * @desc
 * @author wangbin
 * @date 2015/8/18
 */
public abstract class JmsSender{
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private boolean isSendAsync = true;

    protected boolean isMqMessageEnabled(){
        return !"false".equals(AppContextConfig.get("common.message.enabled")) && !isAliyunMessageEnabled();
    }

    protected boolean isAliyunMessageEnabled(){
        return false;//"true".equals(AppContextConfig.get("aliyun.message.enabled"));
    }
    private Producer producer;
    protected Producer getProducer(){
        return producer;
    }
    private AtomicBoolean initialized = new AtomicBoolean(false);
    protected void initAliyunProducer(boolean isTopic){
        if(initialized.compareAndSet(false,true)) {
            Properties properties = new Properties();
            //您在控制台创建的Producer ID
            properties.put(PropertyKeyConst.ProducerId, AppContextConfig.get("aliyun.producerId"));
            // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
            properties.put(PropertyKeyConst.AccessKey, AppContextConfig.get("aliyun.password.accessKey"));
            // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
            properties.put(PropertyKeyConst.SecretKey, AppContextConfig.get("aliyun.password.secretKey"));
            if(isTopic) {
                properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.BROADCASTING);
            }
            try {
                producer = ONSFactory.createProducer(properties);
                // 在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
                producer.start();
            }catch (Exception ex){
                logger.error("start-producer-error:",ex);
            }
        }
    }
    public byte[] genBytesFromObj(Serializable obj){
        if(obj == null){
            return null;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oo = null;
        try{
            oo = new ObjectOutputStream(bos);
            oo.writeObject(obj);
            oo.flush();
            return bos.toByteArray();
        }catch (Exception ex){
            logger.error("gen-byte-array-error:",ex);
        }finally {
            if(oo!=null) {
                try {
                    oo.close();
                    bos.close();
                }catch (Exception ex){
                    logger.error("close-byte-output-stream-error:",ex);
                }
            }
        }
        return null;
    }

    public JmsSender(){}
    public void sendMessage(final Serializable message,final String destination) {
        //final Destination sendDest = destination ;
        if (isSendAsync) {
            if(taskExecutor == null){
                throw new RuntimeException(
                        "ThreadPoolTaskExecutor is null,please check config-item:common.message.enabled is true");
            }
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        send(message, destination);
                        logger.info("sendMessage-async,msg:{} to:{}", message, destination);
                    }catch (Exception ex){
                        logger.error("send-msg-error:",ex);
                    }
                }
            });
        } else {
            try{
                send(message,destination);
                logger.info("sendMessage-sync,msg:{} to:{}", message, destination);
            }catch (Exception ex){
                logger.error("send-msg-error:",ex);
            }
        }
    }

    public void sendMessage(final Serializable message,final String destination,final long delayTime) {
        //final Destination sendDest = destination ;
        if (isSendAsync) {
            if(taskExecutor == null){
                throw new RuntimeException(
                        "ThreadPoolTaskExecutor is null,please check config-item:common.message.enabled is true");
            }
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    send(message,destination,delayTime);
                    logger.info("sendMessage-async,msg {} to {} delayTime {}",message,destination,delayTime);
                }
            });
        } else {
            send(message,destination,delayTime);
        }
    }

    
    protected void send(final Serializable message,String destination){

    }
    
    protected void send(final Serializable message,String destination,long delayTime){

    }
    
    protected void send(final Serializable message,Destination destination){

    }

    public boolean isSendAsync() {
        return isSendAsync;
    }

    public void setSendAsync(boolean isSendAsync) {
        this.isSendAsync = isSendAsync;
    }

}
