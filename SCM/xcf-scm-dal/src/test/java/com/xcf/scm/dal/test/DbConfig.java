package com.xcf.scm.dal.test;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

/**
 * Created by wangb on 2015/12/18.
 */
@Component
@Configuration
@ImportResource({
        "classpath:spring-dao-test.xml",
        "classpath:mybatis-config.xml"
})
//@EnableMongoRepositories(basePackages = {"com.forte.p2p.captcha.dal.dao"})
public class DbConfig/* extends MongoConfiguration*/{

}
