package com.forte.runtime.spring;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jms.ConnectionFactory;
import java.util.concurrent.ThreadPoolExecutor;

/***
 * @desc
 * @author wangbin
 * @date 2015/8/19
 */
@Component
@Configuration
@ComponentScan(basePackages={"com.forte"})
public class MQConfiguration {
    private PooledConnectionFactory factory;
    private ThreadPoolTaskExecutor executor;
    protected boolean isMqMessageEnabled(){
        return !"false".equals(AppContextConfig.get("common.message.enabled")) && !isAliyunMessageEnabled();
    }
    protected boolean isAliyunMessageEnabled(){
        return false;//"true".equals(AppContextConfig.get("aliyun.message.enabled"));
    }

    @PostConstruct
    public void init(){
        this.taskExecutor();
        if(!isMqMessageEnabled() ){
            return;
        }
        this.createConFactory();
        this.createQueueTemplate();
        this.createTopicTemplate();
    }

    @PreDestroy
    public void destroy(){
        if(!isMqMessageEnabled()){
            return;
        }
        if(factory!=null) {
            factory.stop();
            factory.clear();
        }
        if(executor!=null) {
            executor.destroy();
        }
    }

    @Bean
    ThreadPoolTaskExecutor taskExecutor(){
        if(!isMqMessageEnabled() && !isAliyunMessageEnabled()){
            return null;
        }
        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors()*2);
        executor.setQueueCapacity(100);
        executor.setAllowCoreThreadTimeOut(true);
        executor.setKeepAliveSeconds(1000);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    @Bean
    ConnectionFactory createConFactory(){
        if(!isMqMessageEnabled()){
            return null;
        }
        factory = new PooledConnectionFactory();
        ActiveMQConnectionFactory act = new ActiveMQConnectionFactory();
        act.setUserName(AppContextConfig.get("mq.user"));
        act.setPassword(AppContextConfig.get("mq.password"));
        act.setBrokerURL(AppContextConfig.get("brokerURL"));
        act.setCloseTimeout(6000);
        act.setOptimizedAckScheduledAckInterval(1000);
        factory.setConnectionFactory(act);
        return factory;
    }

    public PooledConnectionFactory getFactory() {
        return factory;
    }

    @Bean(name = "jmsQueueTemplate")
    JmsTemplate createQueueTemplate(){
        if(!isMqMessageEnabled()){
            return null;
        }
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(this.getFactory());
        template.setPubSubDomain(false);
        return template;
    }

    @Bean(name = "jmsTopicTemplate")
    JmsTemplate createTopicTemplate(){
        if(!isMqMessageEnabled()){
            return null;
        }
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(this.getFactory());
        template.setPubSubDomain(true);
        return template;
    }

}
