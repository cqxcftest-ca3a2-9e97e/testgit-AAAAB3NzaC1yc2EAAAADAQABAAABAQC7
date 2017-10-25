package com.xcf.scm.dal.test;

import com.forte.runtime.spring.RedisConfiguration;
import com.forte.runtime.startup.PropertyLoaderConfig;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wangb on 2015/12/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        PropertyLoaderConfig.class, RedisConfiguration.class, DbConfig.class})
public class DaoTestBase {

    @BeforeClass
    public static void init(){

    }
}
