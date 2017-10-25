package com.xcf.scm.biz.test;

import com.xcf.scm.biz.spring.DubboCfg;
import com.xcf.scm.biz.spring.MQConfig;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;



/**
 * Biz层测试 基础配置，db通过模拟jndi方式访问。
 * Created by WangBin on 2016/1/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DbConfigTest.class,MQConfig.class, DubboCfg.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)  
public class TransactionalJUnit4Tests extends AbstractTransactionalJUnit4SpringContextTests {

}

@Component
@Configuration
@ImportResource({
        "classpath:spring-dao-test.xml"
})
class DbConfigTest{

}
