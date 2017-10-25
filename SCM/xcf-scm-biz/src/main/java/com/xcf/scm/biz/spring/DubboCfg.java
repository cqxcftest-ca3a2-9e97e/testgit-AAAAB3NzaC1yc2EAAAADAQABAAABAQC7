package com.xcf.scm.biz.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

/***
 * @desc
 * @author wangbin
 * @date 2015/9/30
 */
//@Component
//@Configuration
@ImportResource({
        "classpath:dubbo-provider-config.xml"
})
public class DubboCfg{

}
