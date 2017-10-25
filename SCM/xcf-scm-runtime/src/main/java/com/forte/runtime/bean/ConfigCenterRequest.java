package com.forte.runtime.bean;

import java.io.Serializable;

/***
 * @desc
 * @author wangbin
 * @date 2015/8/26
 */
public class ConfigCenterRequest implements Serializable {
    private String projectName = "";
    private String env = "";
    private String id;
    private String code;
    private String name;
    private String status;
    private String desc;
    private String operate;

    @Override
    public String toString() {
        return "ConfigCenterRequest{" +
                "projectName='" + projectName + '\'' +
                ", env='" + env + '\'' +
                ", id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", desc='" + desc + '\'' +
                ", operate='" + operate + '\'' +
                "} ";
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }
}
