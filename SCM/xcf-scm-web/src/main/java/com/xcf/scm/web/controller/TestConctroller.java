package com.xcf.scm.web.controller;

import com.xcf.scm.biz.impl.HelloFacadeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangb on 2015/12/18.
 *
 *
 */
@Controller
@RequestMapping("/test")
public class TestConctroller {

    @Autowired
    private HelloFacadeImpl helloFacade;
    private static final Logger logger = LoggerFactory.getLogger(TestConctroller.class);

    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object testQuery(){
        String v = helloFacade.hello("test-hello");
        logger.info("query-result:{}",v);
        return v;
    }
}
