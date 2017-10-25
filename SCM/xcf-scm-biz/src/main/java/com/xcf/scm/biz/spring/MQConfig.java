package com.xcf.scm.biz.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import com.forte.runtime.spring.MQConfiguration;

/**
 * Created by wangb on 2015/12/23.
 */
//@Component
//@Configuration
@ImportResource({
        "classpath:mq-config.xml"
})
public class MQConfig extends MQConfiguration{

}
 