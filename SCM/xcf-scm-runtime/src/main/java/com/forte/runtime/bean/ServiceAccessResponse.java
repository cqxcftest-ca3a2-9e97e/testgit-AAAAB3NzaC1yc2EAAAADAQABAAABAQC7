package com.forte.runtime.bean;

/**
 * Created by WangBin on 2016/3/17.
 */
public class ServiceAccessResponse extends AbsResponse{
    /**
     * 服务端返回的
     */
    private String seed;
    /***
     * 服务端生成，返回给调用方
     */
    private String accessKey;
    /**
     * 客户端设置随机字符串，建议为时间戳
     */
    private String randomStr;
    /**
     * 客户端设置的请求服务资源URL
     */
    private String serviceURL;
    /**
     * 客户端返回给服务方的签名
     */
    private String serviceSignature;

    @Override
    public String toString() {
        return "ServiceAccessResponse{" +
                "seed='" + seed + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", randomStr='" + randomStr + '\'' +
                ", serviceURL='" + serviceURL + '\'' +
                ", serviceSignature='" + serviceSignature + '\'' +
                '}';
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getServiceURL() {
        return serviceURL;
    }

    public void setServiceURL(String serviceURL) {
        this.serviceURL = serviceURL;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getRandomStr() {
        return randomStr;
    }

    public void setRandomStr(String randomStr) {
        this.randomStr = randomStr;
    }

    public String getServiceSignature() {
        return serviceSignature;
    }

    public void setServiceSignature(String serviceSignature) {
        this.serviceSignature = serviceSignature;
    }
}
