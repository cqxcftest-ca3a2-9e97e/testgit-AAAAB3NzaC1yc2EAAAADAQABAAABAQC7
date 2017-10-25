package com.forte.runtime.spring;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/***
 * @desc
 * @author wangbin
 * @date 2015/8/24
 */
public class AppContextConfig {
    private static Map<String,String> configHolder = new ConcurrentHashMap();

    public static Map<String,String> getConfigMap(){
        return configHolder;
    }
    public static String get(String key){
        return configHolder.get(key);
    }
    public static String get(String key,Object defaultVal){
        if(!configHolder.containsKey(key)){
            return ""+defaultVal;
        }
        return configHolder.get(key);
    }
    public static Long getLong(String key,long def){
        return Long.valueOf(get(key,def));
    }
    public static Double getDouble(String key,double def){
        return Double.valueOf(get(key,def));
    }
    public static Integer getInteger(String key,int def){
        return Integer.valueOf(get(key,def));
    }
    public static Boolean getBoolean(String key,boolean def){
        return Boolean.valueOf(get(key,def));
    }

    public static void put(String key,String val){
        if(val!=null){
            val = val.trim();
        }
        configHolder.put(key,val);
    }
    public static String remove(String key){
        return configHolder.remove(key);
    }

    public static String getAppName(){
        return get("app.name");
    }
    public static String getAppEnv(){
        return get("env");
    }
}
