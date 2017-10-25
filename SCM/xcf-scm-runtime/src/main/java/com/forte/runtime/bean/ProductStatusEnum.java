package com.forte.runtime.bean;

public enum ProductStatusEnum {	
		
		CREATE_SUCCESS("00", "已创建"),

		AUDIT_PASS("01", "审核通过"),
		
		BEARING_PRODUCT("02", "已成标"),
	    		
		MISCARRY_PRODUCT("03", "已流标"),
		
		LOAN_MONEY("04", "已放款"),
		
		RETURN_MONEY("05", "已回款"),
				
		REPAY_MONEY("06", "已还款");
		
	    public static ProductStatusEnum getTypeByKey(String key) {
		for (ProductStatusEnum acctResCode : ProductStatusEnum.values()) {
		    if (key.equals(acctResCode.getKey())) {
			return acctResCode;
		    }
		}
		return null;
	    }

	    ProductStatusEnum(String key, String value) {
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
