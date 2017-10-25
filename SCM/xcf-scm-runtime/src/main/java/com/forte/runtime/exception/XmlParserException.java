package com.forte.runtime.exception;
/***
 * @desc
 * @author wangbin
 * @date 2015/8/26
 */
public class XmlParserException extends RuntimeException {
    public XmlParserException(String message)
    {
        super(message);
    }

    public XmlParserException(Throwable cause) {
        super(cause);
    }

    public XmlParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
