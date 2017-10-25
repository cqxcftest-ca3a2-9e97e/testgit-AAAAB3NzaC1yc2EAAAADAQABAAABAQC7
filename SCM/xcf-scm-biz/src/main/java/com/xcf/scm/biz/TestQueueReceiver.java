package com.xcf.scm.biz;

import com.forte.runtime.mq.ReceiveHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by wangb on 2015/12/23.
 */
@Service(value = "testQueueReceiver")
public class TestQueueReceiver implements ReceiveHandler {

    private static Logger logger = LoggerFactory.getLogger(TestQueueReceiver.class);
    
    @Override
    public void handle(Serializable serializable) {
        logger.info("got-msg:{}",serializable);
    }
}
