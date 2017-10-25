package com.forte.runtime.util;

import com.forte.runtime.exception.CodeValidateFailedException;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * <p></p>
 * Created by liaosifa on 2015年8月11日
 */
public class StringValidateUtils {
	
	public static void validateNull(String param,String errorMsg) throws CodeValidateFailedException{
		if(StringUtils.isEmpty(param)){
			throw new CodeValidateFailedException(errorMsg);
		}
	}

	private static final Pattern mailMatcher =
			Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");

	public static boolean isEmail(String email){
		try {
			String regExp = "([^@\\s]+)@((?:[-a-zA-Z0-9]+\\.)+[a-zA-Z]{2,})$";
			Pattern p = Pattern.compile(regExp);
			return p.matcher(email).matches();
		}catch (Exception ex){
			return false;
		}
	}
	
	public static boolean isMobile(String mobile){
		try {
			String regExp = "^(13|14|15|17|18)\\d{9}$";
			Pattern p = Pattern.compile(regExp);
			return p.matcher(mobile).matches();
		}catch (Exception ex){
			return false;
		}
	}
	
	public static boolean isValid(String regExp,String value){
		try {
			Pattern p = Pattern.compile(regExp);
			return p.matcher(value).matches();
		}catch (Exception ex){
			return false;
		}
	}

	public static void main(String[] args){
		System.out.println(isEmail("aaaaaaaaaaaaaaa@163.com"));
		System.out.println(isEmail("aaaaaaaaaaaaaaa@qq.com"));
	}
}
