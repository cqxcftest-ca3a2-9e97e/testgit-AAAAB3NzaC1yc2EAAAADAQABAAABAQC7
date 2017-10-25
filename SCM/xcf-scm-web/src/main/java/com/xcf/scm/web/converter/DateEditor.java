/** 
 * Project Name:forte-portal-web-web 
 * File Name:DateEditor.java 
 * Package Name:com.forte.portalweb.web.util.converter 
 * Date:2016年3月10日上午10:23:48 
 * Autho:zhangxuesheng
 * Copyright (c) 2016, www.forte.com.cn All Rights Reserved. 
 * 
 */

package com.xcf.scm.web.converter;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName: DateEditor <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年3月10日 上午10:23:48 <br/>
 * 
 * @author zhangxuesheng
 * @version
 * @since JDK 1.7
 */
public class DateEditor extends PropertyEditorSupport {

	public static final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat TIMEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DateFormat dateFormat;
	private boolean allowEmpty = true;

	public DateEditor() {
	}

	public DateEditor(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public DateEditor(DateFormat dateFormat, boolean allowEmpty) {
		this.dateFormat = dateFormat;
		this.allowEmpty = allowEmpty;
	}

	public void setAsText(String text) throws IllegalArgumentException {
		if ((this.allowEmpty) && (!StringUtils.hasText(text))) {
			setValue(null);
		} else {
			try {
				if (this.dateFormat != null) {
					setValue(this.dateFormat.parse(text));
				} else if (text.contains(":")) {
					setValue(TIMEFORMAT.parse(text));
				} else {
					setValue(DATEFORMAT.parse(text));
				}
			} catch (ParseException ex) {
				throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
			}
		}
	}

	public String getAsText() {
		Date value = (Date) getValue();
		DateFormat dateFormat = this.dateFormat;
		if (dateFormat == null) {
			dateFormat = TIMEFORMAT;
		}
		return value != null ? dateFormat.format(value) : "";
	}
}
