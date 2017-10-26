package com.xcf.util;

/** 
 * 说明：
 * 创建人：Micro Chen
 * 修改时间：2017年09月24日
 * @version
 */
public class Constants {
	
	public static String PICTURE_VISIT_FILE_PATH = "";//图片访问的路径
	public static String PICTURE_SAVE_FILE_PATH = "";//图片存放的路径
	public static String getPICTURE_VISIT_FILE_PATH() {
		return PICTURE_VISIT_FILE_PATH;
	}

	public static void setPICTURE_VISIT_FILE_PATH(String pICTURE_VISIT_FILE_PATH) {
		PICTURE_VISIT_FILE_PATH = pICTURE_VISIT_FILE_PATH;
	}

	public static String getPICTURE_SAVE_FILE_PATH() {
		return PICTURE_SAVE_FILE_PATH;
	}

	public static void setPICTURE_SAVE_FILE_PATH(String pICTURE_SAVE_FILE_PATH) {
		PICTURE_SAVE_FILE_PATH = pICTURE_SAVE_FILE_PATH;
	}

	public static void main(String[] args) {
		Constants.setPICTURE_SAVE_FILE_PATH("D:/developer/java/tomcat/apache-tomcat-8.5.20/webapps/XCF/topic/");
		Constants.setPICTURE_VISIT_FILE_PATH("http://127.0.0.1:8080/XCF/topic/");
	}
	
}
