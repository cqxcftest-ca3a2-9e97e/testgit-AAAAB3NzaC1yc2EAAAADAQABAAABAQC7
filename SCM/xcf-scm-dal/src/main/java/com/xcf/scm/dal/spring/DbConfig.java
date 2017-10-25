package com.xcf.scm.dal.spring;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

/**
 * Created by wangb on 2015/12/18.
 */
@Component
@Configuration
@ImportResource({
        "classpath:/spring/datasource.xml",
        "classpath:/spring/application-context-*.xml",
        /*"classpath:/ibatis/sqlmapconfig.xml",*/
        "classpath:mybatis-config.xml"
})
public class DbConfig {

}
