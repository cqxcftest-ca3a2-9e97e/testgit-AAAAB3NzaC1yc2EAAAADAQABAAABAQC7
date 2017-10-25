package com.forte.runtime.bean;

public enum TransTypeEnum {	
	     		
	    invest_buy("00", "投资购买"),

	    PRINCIPAL_REPAY("01", "本金还款"),
	    
	    MISCARRY_REPAY("02", "流标还款"),
	    
	    INCOME_REPAY("03", "利息还款"),
	    
	    OVERDUE_REPAY("04", "逾期利息还款"),
	    
	    COMPANY_LOAN("05", "融资方放款"),
	    
	    FEE_AMOUNT_LOAN("06", "平台手续费分账");
		
		
	    public static TransTypeEnum getTypeByKey(String key) {
		for (TransTypeEnum acctResCode : TransTypeEnum.values()) {
		    if (key.equals(acctResCode.getKey())) {
			return acctResCode;
		    }
		}
		return null;
	    }

	    TransTypeEnum(String key, String value) {
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
