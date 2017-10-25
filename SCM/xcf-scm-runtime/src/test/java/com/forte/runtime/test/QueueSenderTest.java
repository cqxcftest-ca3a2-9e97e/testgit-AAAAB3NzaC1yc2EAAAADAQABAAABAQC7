package com.forte.runtime.test;

import com.forte.runtime.mq.JmsQueueReceiver;
import com.forte.runtime.mq.JmsQueueSender;
import com.forte.runtime.spring.MQConfiguration;
import com.forte.runtime.startup.PropertyLoaderConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wangb on 2015/12/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        PropertyLoaderConfig.class, MQConfiguration.class})
public class QueueSenderTest{

    @Autowired
    private JmsQueueSender queueSender;

    private JmsQueueReceiver receiver;
    private static final String queue = "q_pay_notify_cdj-transcore_order.pay";
    /*@Before
    public void init(){
        receiver = new JmsQueueReceiver();
        receiver.setDestinationName(queue);
        receiver.setReceiveHandler(new ReceiveHandler() {
            @Override
            public void handle(Serializable serializable) {
                System.out.println("=====================msg-got:" + serializable);
            }
        });
        receiver.setConcurrentConsumers(1);
        receiver.init();
    }*/

    @Test
    public void testSender() throws Exception{
        queueSender.sendMessage("hello,test-queue-msg",queue);
        //System.in.read();
        Thread.currentThread().sleep(10000);
    }

}
