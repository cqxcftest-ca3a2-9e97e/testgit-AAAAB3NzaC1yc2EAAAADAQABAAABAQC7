package com.forte.runtime.mq;

import com.forte.runtime.bean.ConfigCenterRequest;
import com.forte.runtime.spring.AppContextConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/***
 * @desc
 * @author wangbin
 * @date 2015/8/26
 */
@Component
@Qualifier(value = "topicConfigReceiver")
public class JmsTopicConfigReceiver extends JmsTopicReceiver{
    private static String dest = "topic.configcenter.dispatcher";

    private JmsTopicConfigReceiver() {
        super();
        logger.info("JmsTopicConfigReceiver constructor begin...");
        this.setDestinationName(null);
        this.setReceiveHandler(null);
        this.setMessageSelector(null);
        //this.setConcurrentConsumers(1);
        logger.info("JmsTopicConfigReceiver constructor end..");
        this.init();
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public String getDestinationName() {
        return dest;
    }

    @Override
    public void setDestinationName(String destinationName) {
        super.setDestinationName(dest);
    }

    @Override
    public void setMessageSelector(String messageSelector) {
        //and env='"+AppContextConfig.get("common.message.group",AppContextConfig.getAppEnv())+"'
        String selector = "msgFrom in('"+ AppContextConfig.getAppName()+"','base') ";
        //selector = "typeCode='"+AppContextConfig.getAppName()+"'";
        //logger.info("set messageSelector={}",selector);
        super.setMessageSelector(selector);
    }

    @Override
    public void setReceiveHandler(final ReceiveHandler receiveHandler) {
        ReceiveHandler r = new ReceiveHandler() {
            @Override
            public void handle(Serializable message) {
                if (message instanceof ConfigCenterRequest){
                    ConfigCenterRequest request = (ConfigCenterRequest)message;
                    String oper = request.getOperate();
                    String app = request.getProjectName();
                    String env = request.getEnv();
                    logger.info("receive msg {} from config-center,operType={},app={},env={}", message,oper,app,env);
                    if((!AppContextConfig.getAppName().equals(app) && !"base".equals(app))
                            ||!AppContextConfig.getAppEnv().equals(env)){
                        logger.warn("not related config");
                        return;
                    }
                    if("false".equals(AppContextConfig.get("base.config.include"))){
                        if("base".equals(app)){
                            return;
                        }
                    }
                    //Note base的配置修改了 会覆盖项目的设置，若有冲突，项目的设置需要再修改一次
                    String key = request.getCode();
                    String val = request.getName();
                    if("add".equalsIgnoreCase(oper)){
                        AppContextConfig.put(key,val);
                        logger.info("add-config:key={},val={}",key,val);
                    }
                    if("update".equalsIgnoreCase(oper)){
                        AppContextConfig.put(key,val);
                        logger.info("update-config:key={},val={}",key,val);
                    }
                    if("delete".equalsIgnoreCase(oper)){
                        AppContextConfig.remove(key);
                        logger.info("remove-config:key={},val={}",key,val);
                    }
                }
            }
        };
        super.setReceiveHandler(r);
    }
}
