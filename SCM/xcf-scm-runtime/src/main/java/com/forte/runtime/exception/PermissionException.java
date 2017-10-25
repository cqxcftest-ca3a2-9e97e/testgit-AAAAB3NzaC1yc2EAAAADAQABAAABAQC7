package com.forte.runtime.exception;

/**
 * Created by libin on 5/19/15.
 */
public class PermissionException extends Exception {

    public PermissionException(int code, String field) {
        super("{\"errorCode\":"+code+",\"field\":\""+field+"\"}");
    }
}
