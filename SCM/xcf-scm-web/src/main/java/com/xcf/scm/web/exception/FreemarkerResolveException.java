package com.xcf.scm.web.exception;

/**
 * Created by WangBin on 2016/6/23.
 */
public class FreemarkerResolveException extends RuntimeException {
    public FreemarkerResolveException() {
    }

    public FreemarkerResolveException(String message) {
        super(message);
    }

    public FreemarkerResolveException(String message, Throwable cause) {
        super(message, cause);
    }
}
