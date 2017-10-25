package com.forte.runtime.bean;

public enum BusiCodeEnum {	
		
		PC_INVEST("00", "PC端充值购买"),

		MISCARRAY_REPAY("01", "流标还款"),
		
		COMPANY_LEAN("02", "成标给融资方放款"),
	    		
		FEE_SPLIT_PP("03", "成标分账手续费"),
				
		CUSTOMER_REPAY("04", "标的到期还款");
		
	    public static BusiCodeEnum getTypeByKey(String key) {
		for (BusiCodeEnum acctResCode : BusiCodeEnum.values()) {
		    if (key.equals(acctResCode.getKey())) {
			return acctResCode;
		    }
		}
		return null;
	    }

	    BusiCodeEnum(String key, String value) {
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
