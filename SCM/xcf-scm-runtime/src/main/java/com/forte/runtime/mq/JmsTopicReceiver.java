package com.forte.runtime.mq;

import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.Destination;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/***
 * @desc
 * @author wangbin
 * @date 2015/8/19
 */
public class JmsTopicReceiver extends JmsReceiver{

    public JmsTopicReceiver() {
        super();
        this.setMaxConnections(1);
        this.isTopic = true;
        //this.setMessageSelector("env='"+ AppContextConfig.get("common.message.group",AppContextConfig.getAppEnv())+"'");
    }
    /*@Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }*/

    @Override
    protected Destination getDestination() {
        return new ActiveMQTopic(getDestinationName());
    }
    @Override
    protected void initExecutor(){
        super.executor = (Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "topic-receiver-handler-" + idx++);
            }
        }));
    }
}
