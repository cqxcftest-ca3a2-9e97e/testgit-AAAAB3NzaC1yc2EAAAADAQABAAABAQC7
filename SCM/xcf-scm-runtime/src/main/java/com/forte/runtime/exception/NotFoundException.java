package com.forte.runtime.exception;

/**
 * Created by libin on 5/19/15.
 */
public class NotFoundException extends Exception {

    public NotFoundException(int code, String field) {
        super("{\"errorCode\":"+code+",\"field\":\""+field+"\"}");
    }
}
