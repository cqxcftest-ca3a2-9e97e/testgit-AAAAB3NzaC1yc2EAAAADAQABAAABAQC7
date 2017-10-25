package com.forte.runtime.bean;

import java.io.Serializable;
import java.util.UUID;

/**
 * 服务调用 附加验证参数，双向认证，认证通过执行后续操作。
 * Created by WangBin on 2016/3/17.
 */
public class ServiceAccessRequest implements Serializable {
    /**
     * 随机数种子,服务端设置
     */
    private String seed = UUID.randomUUID().toString();
    /***
     * 服务端生成，返回给调用方
     */
    /*private String accessKey;*/
    /**
     * 客户端设置随机字符串，建议为时间戳
     */
    private String randomStr;
    /**
     * 请求服务资源URL
     */
    private String serviceURL;

    /*private String serviceSignature;*/

    @Override
    public String toString() {
        return "ServiceAccessRequest{" +
                "seed='" + seed + '\'' +
               /* ", accessKey='" + accessKey + '\'' +*/
                ", randomStr='" + randomStr + '\'' +
                ", serviceURL='" + serviceURL + '\'' +
                /*", serviceSignature='" + serviceSignature + '\'' +*/
                '}';
    }

/*    public String getServiceSignature() {
        return serviceSignature;
    }

    public void setServiceSignature(String serviceSignature) {
        this.serviceSignature = serviceSignature;
    }*/

    public String getSeed() {
        return seed;
    }
    /*public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getAccessKey() {
        //accessKey =
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }*/

    public String getRandomStr() {
        return randomStr;
    }

    public void setRandomStr(String randomStr) {
        this.randomStr = randomStr;
    }

    public String getServiceURL() {
        return serviceURL;
    }

    public void setServiceURL(String serviceURL) {
        this.serviceURL = serviceURL;
    }
}
