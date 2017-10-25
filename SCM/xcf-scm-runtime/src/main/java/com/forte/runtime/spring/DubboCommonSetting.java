package com.forte.runtime.spring;

import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.Iterator;
import java.util.Map;

/**
 * dubbo 服务分组，多个环境公用一个zookeeper,默认分组为dubbo
 * Created by wangbin on 2016/9/29
 */
@Configuration
public class DubboCommonSetting implements ApplicationContextAware,InitializingBean {
    private transient ApplicationContext ctx;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
        /*RegistryConfig config = applicationContext.getBean(RegistryConfig.class);
        config.setGroup(AppContextConfig.get("dubbo.group","dubbo"));*/
        System.out.println("============DubboCommonSetting.setApplicationContext called");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RegistryConfig config = null;//BeanFactoryUtils.beanOfType(ctx,RegistryConfig.class,false,false);
        Map<String,RegistryConfig> map = ctx.getBeansOfType(RegistryConfig.class);
        Iterator<RegistryConfig> iterator = map.values().iterator();
        while (iterator.hasNext()) {
            config = iterator.next();
            if (config != null) {
                config.setGroup(AppContextConfig.get("dubbo.group", "dubbo"));
                System.out.println("============registryConfig="+config.toString());
            }
        }
        /*Map<String,ProtocolConfig> protocol = ctx.getBeansOfType(ProtocolConfig.class);
        Iterator<ProtocolConfig> itr = protocol.values().iterator();
        ProtocolConfig cfg = null;
        while (itr.hasNext()) {
            cfg = itr.next();
            if (cfg != null) {
                cfg.setSerialization(AppContextConfig.get("dubbo.protocol.serialization", "hessian2"));
                System.out.println("============ProtocolConfig="+cfg.toString());
            }
        }*/
        System.out.println("============DubboCommonSetting.afterPropertiesSet called");
    }
}
