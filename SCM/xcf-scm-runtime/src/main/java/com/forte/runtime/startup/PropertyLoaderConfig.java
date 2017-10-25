package com.forte.runtime.startup;

import com.forte.runtime.spring.AppContextConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/***
 * @desc
 * @author wangbin
 * @date 2015/8/24
 */
@Configuration
public class PropertyLoaderConfig extends PropertyPlaceholderConfigurer {
    private static Logger logger = LoggerFactory.getLogger(PropertyLoaderConfig.class);

    private static String DEFAULT_CONF_FILE = File.separator + "wls" + File.separator + "envconfig" + File.separator + "env.properties";

    private static String DEFAULT_CONF_FILE_LOCAL = "env.properties";

    private static boolean flag = false;
    private volatile List<StartUpExecutor> executers;

    public PropertyLoaderConfig(){
        this.executers = new LinkedList();
        logger.info("\n*********************PropertyLoaderConfig constructor called******************************");
    }

    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)throws BeansException {
        Properties pro = loadFile();
        for (Iterator i$ = pro.keySet().iterator(); i$.hasNext(); ) { Object key = i$.next();
            String keyStr = key.toString();
            String value = pro.getProperty(keyStr);
            if ((null != keyStr) && (!keyStr.isEmpty())) {
                AppContextConfig.put(keyStr, value);
            }
        }
        initExecutors();
        executeAll();
        logger.info("系统配置详情:");
        /*for (Map.Entry me : AppContextConfig.getConfigMap().entrySet()){
            if (me.getKey() != null) {
                if((me.getKey()+"").indexOf("password")==-1 && (me.getKey()+"").indexOf("private")<0) {
                    logger.info(me.getKey() + "=" + me.getValue());
                }
                props.put(me.getKey(), me.getValue());
            }
        }*/
        AppContextConfig.getConfigMap().entrySet().stream()
            .filter(f->f.getKey()!=null)
            .sorted((s1,s2)->s1.getKey().compareTo(s2.getKey()))
            .forEach(val ->{
                if((val.getKey()+"").indexOf("password")==-1 && (val.getKey()+"").indexOf("private")<0) {
                    logger.info(val.getKey() + "=" + val.getValue());
                }else {
                    logger.info(val.getKey() + "=******");
                }
                props.put(val.getKey(), val.getValue());
            });
        super.processProperties(beanFactoryToProcess, props);
    }

    private synchronized void initExecutors(){
        boolean isConnectConfigCenter = true;
        String str = AppContextConfig.get("isConnectConfigCenter");
        if (!StringUtils.isEmpty(str)) {
            try {
                isConnectConfigCenter = Boolean.parseBoolean(str);
            } catch (Exception e) {
                isConnectConfigCenter = true;
                logger.error("Failed to convet a string to a boolean, isConnectConfigCenter=" + str);
            }
        }
        else {
            isConnectConfigCenter = true;
        }
        if (isConnectConfigCenter) {
            logger.info("getting parameters from remote configuration center is enabled");
            this.executers.add(new RemoteConfigInfoImpl());
        } else {
            logger.info("getting parameters from remote configuration center is disabled,isConnectConfigCenter=" + str);
        }
        //this.executers.add(new ErrorCodeLoadExecuter());
        //this.executers.add(new MQConfigInfoImpl());
    }

    private synchronized void executeAll(){
        Properties properties = System.getProperties();
        properties.put("org.apache.cxf.stax.allowInsecureParser", "1");
        if (!flag) {
            for (StartUpExecutor executer : this.executers) {
                executer.execute();
            }
            flag = true;
        }
    }

    private static Properties loadWarFile(){
        Properties filePropIn = new Properties();
        InputStream input = null;
        try {
            input = Thread.currentThread().getContextClassLoader().getResourceAsStream(DEFAULT_CONF_FILE_LOCAL);
            if(input == null){
                /*String path = Thread.currentThread().getContextClassLoader().getResource("") +"/"+DEFAULT_CONF_FILE_LOCAL;
                logger.info("path:"+path);
                input = new FileInputStream(path);*/
                ResourceBundle res = ResourceBundle.getBundle("env");
                Set<String> keys = res.keySet();
                for(String key : keys){
                    filePropIn.put(key,res.getString(key));
                }
            }else {
                logger.info(input == null ? "input=null," : "input not null," + "get-input-stream-from：" + DEFAULT_CONF_FILE_LOCAL);
                filePropIn.load(input);
            }
            logger.info("项目内置配置文件env.properties加载成功!");
        } catch (Exception e) {
            logger.warn("项目内置配置文件env.properties加载失败!使用默认外置配置文件！{}", e.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                    input = null;
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return filePropIn;
    }

    private static Properties loadOutFile(String filePath){
        Properties filePropOut = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(filePath);
            filePropOut.load(input);
            logger.info("外置配置文件{}加载成功", filePath);
        } catch (Exception e) {
            logger.error("外置配置文件" + filePath + "加载失败！{}", e.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                    input = null;
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return filePropOut;
    }

    protected static Properties loadFile() {
        Properties properties = new Properties();
        properties.putAll(loadWarFile());
        if (properties.containsKey("app.name")) {
            String appName = properties.getProperty("app.name");
            DEFAULT_CONF_FILE = File.separator + "wls" + File.separator + "envconfig" + File.separator + appName + File.separator + "env.properties";
        }
        properties.putAll(loadOutFile(DEFAULT_CONF_FILE));
        return properties;
    }
}
