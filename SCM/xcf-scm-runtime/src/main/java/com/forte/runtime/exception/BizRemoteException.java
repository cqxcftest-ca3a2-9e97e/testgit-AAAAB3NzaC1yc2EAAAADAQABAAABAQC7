package com.forte.runtime.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
  * 
  * <p> 远程接口类业务异常 </p>
  * @author liaosifa913 (18516086389)
  * @date 2014-1-20 下午1:40:40
  * @version V1.0
 */
public class BizRemoteException extends Exception {
	private final static Logger logger = LoggerFactory.getLogger(BizRemoteException.class);;
	private static final long serialVersionUID = 1;
	private String _errorCode = "9900";
	private String _moduleCode = "";
	private String _subsystem = "";
	private Exception _exception; // 底层异常
	private final static String exception_info = "调用业务接口报错";

	public BizRemoteException(Throwable cause) {
		super(exception_info, cause);
	}

	public BizRemoteException(String msg) {
		super(msg, null);
		logger.debug(msg);
	}

	public BizRemoteException(String errorCode, String msg) {

		super(msg, null);
		_errorCode = errorCode;
		logger.debug("错误代码{" + errorCode + "},错误描述{" + msg + "}");
	}

	public BizRemoteException(String errorCode, String msg, String moduleCode, String subsystem) {

		super(msg, null);
		_errorCode = errorCode;
		_moduleCode = moduleCode;
		_subsystem = subsystem;

		logger.debug("错误代码{" + errorCode + "},错误描述{" + msg + "}");
	}

	public BizRemoteException(Exception exception, String msg) {
		super(msg, null);
		_exception = exception;
		logger.debug(msg);
	}

	public BizRemoteException(Exception exception, String errorCode, String msg) {
		super(msg, null);
		_errorCode = errorCode;
		_exception = exception;
		logger.debug("错误代码{" + errorCode + "},错误描述{" + msg + "}");
	}

	public BizRemoteException(Exception exception, String errorCode, String msg, String moduleCode, String subsystem) {
		super(msg, null);
		_errorCode = errorCode;
		_exception = exception;
		_moduleCode = moduleCode;
		_subsystem = subsystem;
		logger.debug("错误代码{" + errorCode + "},错误描述{" + msg + "}");
	}

	public String getErrorCode() {
		return _errorCode;
	}

	public BizRemoteException() {
		super(exception_info, null);

	}

	public Exception getRootCause() {
		if (_exception instanceof BizRemoteException) {
			return ((BizRemoteException) _exception).getRootCause();
		}
		return _exception == null ? this : _exception;
	}

	@Override
	public String toString() {
		String desc = "错误码为：" + _errorCode + "，错误描述为：" + this.getMessage();
		if (_exception != null) {
			desc = desc + "，底层异常为：" + _exception;
		}
		return desc;
	}

	public String get_moduleCode() {
		return _moduleCode;
	}

	public String get_subsystem() {
		return _subsystem;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Throwable#fillInStackTrace()
	 * @author Jonathan Gong
	 */
	@Override
	public synchronized Throwable fillInStackTrace() {
		// 避免操作系统的压栈操作，提升性能
		return null;
	}

}
