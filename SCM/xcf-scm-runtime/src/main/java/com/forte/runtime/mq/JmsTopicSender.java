package com.forte.runtime.mq;

import com.aliyun.openservices.ons.api.SendResult;
import com.forte.runtime.bean.ConfigCenterRequest;
import com.forte.runtime.spring.AppContextConfig;
import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.Serializable;

/***
 * @desc
 * @author wangbin
 * @date 2015/8/18
 */
@Qualifier("topicSender")
@Component
public class JmsTopicSender extends JmsSender {
    @Autowired
    @Qualifier("jmsTopicTemplate")
    private JmsTemplate jmsTemplate;

    protected void send(final Serializable message,String destination) {
        if(isMqMessageEnabled()) {
            this.jmsTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    Message msg = session.createObjectMessage(message);
                    //set msg from
                    if (message instanceof ConfigCenterRequest) {
                        msg.setStringProperty("msgFrom", ((ConfigCenterRequest) message).getProjectName());
                    } else {
                        msg.setStringProperty("msgFrom", AppContextConfig.getAppName());
                    }
                    //msg.setStringProperty("env",AppContextConfig.get("common.message.group",AppContextConfig.getAppEnv()));
                    return msg;
                }
            });
        }else if(isAliyunMessageEnabled()){
            initAliyunProducer(true);
            com.aliyun.openservices.ons.api.Message msg = new com.aliyun.openservices.ons.api.Message(//
                    destination,AppContextConfig.getAppName(),genBytesFromObj(message));
            if (message instanceof ConfigCenterRequest) {
                msg.setTag(((ConfigCenterRequest) message).getProjectName());
            } else {
                msg.setTag(AppContextConfig.getAppName());
            }
            SendResult sendResult = getProducer().send(msg);
            logger.info("send-aliyun-topic-result:" + sendResult);
        }
    }
    
    protected void send(final Serializable message,String destination,final long delayTime) {
        if(isMqMessageEnabled()) {
            this.jmsTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    Message msg = session.createObjectMessage(message);
                    //set msg from
                    if (message instanceof ConfigCenterRequest) {
                        msg.setStringProperty("msgFrom", ((ConfigCenterRequest) message).getProjectName());
                    } else {
                        msg.setStringProperty("msgFrom", AppContextConfig.getAppName());
                    }
                    msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delayTime);
                    //msg.setStringProperty("env",AppContextConfig.get("common.message.group",AppContextConfig.getAppEnv()));
                    return msg;
                }
            });
        }else if(isAliyunMessageEnabled()){
            initAliyunProducer(true);
            com.aliyun.openservices.ons.api.Message msg = new com.aliyun.openservices.ons.api.Message(//
                    destination,AppContextConfig.getAppName(),genBytesFromObj(message));
            msg.setStartDeliverTime(System.currentTimeMillis() + delayTime);
            if (message instanceof ConfigCenterRequest) {
                msg.setTag(((ConfigCenterRequest) message).getProjectName());
            } else {
                msg.setTag(AppContextConfig.getAppName());
            }
            SendResult sendResult = getProducer().send(msg);
            logger.info("send-aliyun-topic-result:" + sendResult);
        }
    }
    
    @Override
    public void sendMessage(Serializable message, String destination) {
        super.sendMessage(message, destination);
    }
}
