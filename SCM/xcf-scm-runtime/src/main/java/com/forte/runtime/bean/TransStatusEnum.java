package com.forte.runtime.bean;

public enum TransStatusEnum {	
	     		
		STATUS_INITIALIZE("00", "初始化"),

		STATUS_FAILURE("99", "失败"),
		
		STATUS_SUCCESS("10", "交易成功"),
	    		
		STATUS_PAYMENT_SEND_SUCCESS("01", "代付发送成功"),
				
		STATUS_PAYMENT_SEND_FAILURE("02", "代付发送失败");
		
	    public static TransStatusEnum getTypeByKey(String key) {
		for (TransStatusEnum acctResCode : TransStatusEnum.values()) {
		    if (key.equals(acctResCode.getKey())) {
			return acctResCode;
		    }
		}
		return null;
	    }

	    TransStatusEnum(String key, String value) {
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
