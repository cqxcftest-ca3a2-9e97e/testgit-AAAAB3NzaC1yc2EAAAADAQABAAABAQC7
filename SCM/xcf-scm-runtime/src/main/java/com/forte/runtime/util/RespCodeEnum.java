package com.forte.runtime.util;

public enum RespCodeEnum {	
	
		RESP_CODE_SUCC("000000", "成功"),

		RESP_CODE_FAIL("001001", "失败"),
		
		RESP_CODE_NULL("001002", "参数不能为空"),
	    		
		RESP_CODE_IVLD("001003", "请求参数非法"),
				
		RESP_CODE_NEXI("001004", "数据不存在"),
		
		RESP_CODE_SYSE("999999", "系统异常");
		public static String getRespDesc(String code) {
			//ResultCodeEnum result = null;
			for (RespCodeEnum tmp : RespCodeEnum.values()) {
				if (tmp.getKey().equals(code)) {
					return tmp.getValue();
				}
			}
			return "";
		}
	    public static RespCodeEnum getTypeByKey(String key) {
		for (RespCodeEnum acctResCode : RespCodeEnum.values()) {
		    if (key.equals(acctResCode.getKey())) {
			return acctResCode;
		    }
		}
		return null;
	    }

	    RespCodeEnum(String key, String value) {
		this.key = key;
		this.value = value;
	    }

	    private String key;

	    private String value;

	    public String getKey() {
		return key;
	    }

	    public void setKey(String key) {
		this.key = key;
	    }

	    public String getValue() {
		return value;
	    }

	    public void setValue(String value) {
		this.value = value;
	    }
}
