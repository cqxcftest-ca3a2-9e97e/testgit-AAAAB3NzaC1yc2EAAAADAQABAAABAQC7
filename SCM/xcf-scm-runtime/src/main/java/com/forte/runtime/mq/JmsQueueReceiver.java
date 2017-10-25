package com.forte.runtime.mq;

import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.Destination;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/***
 * @desc
 * @author wangbin
 * @date 2015/8/19
 */
public class JmsQueueReceiver extends JmsReceiver{
    public JmsQueueReceiver() {
        //this.setMessageSelector("env='"+ AppContextConfig.get("common.message.group", AppContextConfig.getAppEnv())+"'");
    }

    @Override
    protected Destination getDestination() {
        return new ActiveMQQueue(getDestinationName());
    }

    @Override
    protected void initExecutor(){
        /*super.executor = (Executors.newFixedThreadPool(3 * Runtime.getRuntime().availableProcessors(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "queue-receiver-handler-"+getDestinationName()+"-" + idx++);
            }
        }));*/
        if ((this.executor == null) || (this.executor.isShutdown())) {
            this.executor =  new ThreadPoolExecutor(getThreadCoreSize(), getThreadMaxSize(), getKeepAlive(), TimeUnit.SECONDS,
                    new ArrayBlockingQueue(10),new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "queue-receiver-handler-"+getDestinationName()+"-" + idx++);
                }
            },new ThreadPoolExecutor.CallerRunsPolicy());
            logger.info("init-thread-pool-for-{},param:pool-core-size:{},pool-max-size:{},keepAlive:{}s",
                    getDestinationName(),getThreadCoreSize(),getThreadMaxSize(),getKeepAlive());
        }
    }
}