package com.xcf.scm.web.converter;

/**
 * <p>
 * key value 键值队列
 * </p>
 * 
 * @author liaosifa913 (18516086389)
 * @date 2013-11-21 上午11:34:19
 * @version v1.0
 */

public class CommonParameter implements java.io.Serializable {
     
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/** 界面展示专用  key**/
	private String paramCode;
	/** 界面展示 专用 value **/
	private String paramDesc;

    /** 要放弃的代码 **/	    
	private String collectionCode;
	
	private String valueCode;
	
	private String parentValueCode;
	
	private String valueChineseName;
	
	private String valueEnglishName;

	public String getCollectionCode() {
		return collectionCode;
	}

	public void setCollectionCode(String collectionCode) {
		this.collectionCode = collectionCode;
	}

	public String getValueCode() {
		return valueCode;
	}

	public void setValueCode(String valueCode) {
		this.valueCode = valueCode;
	}

	public String getParentValueCode() {
		return parentValueCode;
	}

	public void setParentValueCode(String parentValueCode) {
		this.parentValueCode = parentValueCode;
	}

	public String getValueChineseName() {
		return valueChineseName;
	}

	public void setValueChineseName(String valueChineseName) {
		this.valueChineseName = valueChineseName;
	}

	public String getValueEnglishName() {
		return valueEnglishName;
	}

	public void setValueEnglishName(String valueEnglishName) {
		this.valueEnglishName = valueEnglishName;
	}

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}
	
	
}
