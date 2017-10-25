package com.forte.runtime.startup;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
//import com.forte.configure.facade.ConfigureLoadFacade;
import com.forte.runtime.bean.ConfigCenterRequest;
import com.forte.runtime.spring.AppContextConfig;
import com.forte.runtime.util.HttpRequestHelper;
import com.forte.runtime.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @desc
 * @author wangbin
 * @date 2015/8/24
 */
public class RemoteConfigInfoImpl implements StartUpExecutor {
    private static Logger logger = LoggerFactory.getLogger(RemoteConfigInfoImpl.class);

    //@Autowired
    //private ConfigureLoadFacade queryConfigFacade;
    //private ApplicationConfig appCfg;
    //private ReferenceConfig<ConfigureLoadFacade> refer;
    //private RegistryConfig reg;
    public RemoteConfigInfoImpl(){
        if("scm".equalsIgnoreCase(AppContextConfig.get("app.name"))){
            return;
        }
        logger.info("init-dubbo-config.........");
        ApplicationConfig appCfg;
        RegistryConfig reg;
        try {
            /*appCfg = new ApplicationConfig();
            appCfg.setName(AppContextConfig.get("app.name"));*//**//*
            refer = new ReferenceConfig();
            refer.setInterface(ConfigureLoadFacade.class);
            refer.setVersion(AppContextConfig.get("config.facade.version","1.0"));
            refer.setApplication(appCfg);
            refer.setId("configureLoadFacade");
            refer.setTimeout(10000);
            reg = new RegistryConfig();
            reg.setAddress(AppContextConfig.get("dubbo.registry.address"));
            //reg.setGroup(AppContextConfig.get("dubbo.group","dubbo"));
            //ConfigureLoadFacade 各个group公用
            //reg.setGroup("*");
            reg.setId("configRegistry");
            refer.setRegistry(reg);
            queryConfigFacade = refer.get();*/
        }catch (Exception ex){
            logger.warn("get-dubbo-configFacade-error:{}",ex.getMessage());
        }finally {
            appCfg = null;
            //config.destroy();
            //config = null;
            reg = null;
        }
        //logger.info("init-dubbo-config-done,ConfigureLoadFacade is "+(queryConfigFacade==null?"null":"not null"));
    }
    @Override
    public void execute() {
        /*if("configure".equals(AppContextConfig.get("app.name"))){
            return;
        }*/
        String configCenter = AppContextConfig.get("configcenter.service");
        String system = AppContextConfig.getAppName();
        String env = AppContextConfig.getAppEnv();
        logger.info("configCenter.service:{},system:{},env:{}",configCenter,system,env);
        /*if("configcenter".equals(system)){
            logger.warn("Do not load configs of configcenter");
            return;
        }*/
        Map map = new HashMap();
        map.put("owner",system);
        map.put("env",env);
        String strlist = "";
        /*if(queryConfigFacade !=null){
            strlist = queryConfigFacade.loadSysConfigs(system,env);
            refer.destroy();
            refer = null;
            *//*appCfg.*//*
            logger.debug("==============>load system-configs-from-dubbo-refer:\n{}",strlist);
        }else */{
            strlist = HttpRequestHelper.httpPost(configCenter, map, "application/json");
            logger.debug("--------------->response-from-configCenter-http-req:\n" + strlist);
        }
        if (strlist != null && !strlist.isEmpty()) {
            List<ConfigCenterRequest> list = JsonUtil.Json2List(strlist, ConfigCenterRequest.class);
            for (ConfigCenterRequest it : list) {
                if ("app.name".equals(it.getCode()) || "env".equals(it.getCode())) {
                    continue;
                }
                if("01".equals(it.getStatus())){
                    continue;
                }
                if("false".equals(AppContextConfig.get("base.config.include"))){
                    if("base".equals(it.getProjectName())){
                        continue;
                    }
                }
                if((it.getCode()).indexOf("password")==-1 && it.getCode().indexOf("private")<0) {
                    logger.info("==========>config-item:{}", it);
                }
                AppContextConfig.put(it.getCode(), it.getName());
            }
        } else {
            logger.warn("response-from-configcenter is null");
        }
    }
    private void loggerResourceInfo(String system, String env, String configCenterIp)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("configcenter.project.name").append("=").append(system).append(";");
        sb.append("configcenter.project.env").append("=").append(env).append(";");
        sb.append("configcenter.address").append("=").append(configCenterIp).append(";");
        logger.warn(sb.toString());
    }
}
