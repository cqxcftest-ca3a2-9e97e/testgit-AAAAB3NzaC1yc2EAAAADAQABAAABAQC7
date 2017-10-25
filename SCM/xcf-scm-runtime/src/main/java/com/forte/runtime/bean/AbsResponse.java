package com.forte.runtime.bean;

import com.forte.runtime.spring.AppContextConfig;
import com.forte.runtime.util.RespCodeEnum;

import java.io.Serializable;

/***
 * @desc 各个系统 自定义ResponseCode,重写getDefaultDesc()方法。
 *
 * @author wangbin
 * @date 2015/8/17
 */
public abstract class AbsResponse implements Serializable {
    private String respCode = "";
    private String respDesc = "";

    public void initSuccess(){
        this.respCode = "000000";
        this.respDesc = "服务请求成功";
    }
    public String getRespCode() {
        return respCode;
    }

    /*public void setRespCode(String respCode) {
        this.respCode = respCode;
    }*/
    public void setRespCode(String respCode) {
        this.respCode = respCode;
        this.setRespDesc(getDefaultDesc());
    }

    protected String getDefaultDesc(){
        respDesc = RespCodeEnum.getRespDesc(respCode);
        return AppContextConfig.get(respCode,respDesc);
    }
    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    @Override
    public String toString() {
        return "AbsResponse{" +
                "respCode='" + respCode + '\'' +
                ", respDesc='" + respDesc + '\'' +
                '}';
    }
}
