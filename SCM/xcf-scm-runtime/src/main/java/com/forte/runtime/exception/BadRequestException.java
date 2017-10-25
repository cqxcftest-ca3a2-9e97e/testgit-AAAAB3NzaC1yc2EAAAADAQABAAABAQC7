package com.forte.runtime.exception;

/**
 * Created by libin on 5/19/15.
 */
public class BadRequestException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadRequestException(String code,String field) {
        super("{\"errorCode\":"+code+",\"field\":\""+field+"\"}");
    }
}
