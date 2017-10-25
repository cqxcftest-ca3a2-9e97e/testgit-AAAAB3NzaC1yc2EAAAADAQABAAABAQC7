package com.forte.runtime.mq;

import com.aliyun.openservices.ons.api.SendResult;
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
@Qualifier("queueSender")
@Component
public class JmsQueueSender extends JmsSender {
    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    protected void send(final Serializable message,String destination) {
        if(isMqMessageEnabled()) {
            this.jmsTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    Message msg = session.createObjectMessage(message);
                    //msg.setStringProperty("env",AppContextConfig.get("common.message.group",AppContextConfig.getAppEnv()));
                    return msg;
                }
            });
        }else if(isAliyunMessageEnabled()){
            initAliyunProducer(false);
            com.aliyun.openservices.ons.api.Message msg = new com.aliyun.openservices.ons.api.Message(//
                    destination,AppContextConfig.getAppName(),genBytesFromObj(message));
            SendResult sendResult = getProducer().send(msg);
            logger.info("send-aliyun-message-result:" + sendResult);
        }
    }

    
    protected void send(final Serializable message,String destination,final long delayTime) {
        if(isMqMessageEnabled()) {
            this.jmsTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    Message msg = session.createObjectMessage(message);
                    msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delayTime);
                    //msg.setStringProperty("env",AppContextConfig.getAppEnv());
                    return msg;
                }
            });
        }else if(isAliyunMessageEnabled()){
            initAliyunProducer(false);
            com.aliyun.openservices.ons.api.Message msg = new com.aliyun.openservices.ons.api.Message(//
                    // Message Topic
                    destination,
                    // Message Tag 可理解为Gmail中的标签，对消息进行再归类，
                    // 方便Consumer指定过滤条件在MQ服务器过滤
                    AppContextConfig.getAppName(),
                    // Message Body 可以是任何二进制形式的数据， MQ不做任何干预，
                    // 需要Producer与Consumer协商好一致的序列化和反序列化方式
                    genBytesFromObj(message));
            // 设置代表消息的业务关键属性，请尽可能全局唯一。
            // 以方便您在无法正常收到消息情况下，可通过MQ控制台查询消息并补发。
            // 注意：不设置也不会影响消息正常收发
            //msg.setKey("ORDERID_" + i);
            // 发送消息，只要不抛异常就是成功
            msg.setStartDeliverTime(System.currentTimeMillis() + delayTime);
            SendResult sendResult = getProducer().send(msg);
            logger.info("send-aliyun-message-result:" + sendResult);
        }
    }

    @Override
    public void sendMessage(Serializable message, String destination) {
        super.sendMessage(message, destination);
    }
}
