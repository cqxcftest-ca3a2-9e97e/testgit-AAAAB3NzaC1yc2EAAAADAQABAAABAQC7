package com.forte.runtime.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封装JSON工具类
 * 
 * @author wangshujin026 2013-09-16
 * */
public class JsonUtil {

    private final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);	
	private static final String CHARSET = "UTF-8";
	private static Gson gson = new Gson();

    /**
     * 把javabean转化为json字符串
     * */
    public static String beanToJson(Object objBean) throws IOException {
        String jsonStr = "";
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator;
        try {
            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(stringWriter);
            objectMapper.writeValue(jsonGenerator, objBean);
            jsonStr = stringWriter.toString();
            if (null != jsonGenerator) {
                jsonGenerator.flush();
            }
            if (null != stringWriter) {
                stringWriter.flush();
            }
            if (null != jsonGenerator) {
                jsonGenerator.close();
            }
            if (null != stringWriter) {
                stringWriter.close();
            }
            jsonGenerator = null;
            objectMapper = null;
        } catch (IOException e) {
            logger.error("bean to json throws exceptions!param:{}",objBean, e);
            throw new IOException(e.getMessage(), e);
        }
        return jsonStr;
    }

    /**
     * 把json字符串转化为Object
     * */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Object jsonStrToBean(String jsonStr, Class objectClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Object result = null;
        try {
            result = objectMapper.readValue(jsonStr, objectClass);
        } catch (JsonParseException e) {
            logger.error("parse jsonStr to bean throws exceptions!input:{}",jsonStr, e);
            throw new IOException(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error("parse jsonStr to bean throws exceptions!input:{}",jsonStr,e);
            throw new IOException(e.getMessage(), e);
        } catch (IOException e) {
            logger.error("parse jsonStr to bean throws exceptions!input:{}",jsonStr, e);
            throw new IOException(e.getMessage(), e);
        }
        return result;
    }
    
    public static JSONObject Object2JsonObject(Object o){
		try {
			return (JSONObject) JSON.toJSON(o);
		} catch (Exception e) {
			logger.error("JSONUTIL.Object2JsonObject转化报错：：input:{}",o, e);
			return null;
		}
	}
	
    
	public static JSONObject json2JsonObject(String json){
		try {
			return (JSONObject) JSON.parseObject(json);
		} catch (Exception e) {
			logger.error(json, e);
			return null;
		}
	}
	
	public static JSONObject bytes2JsonObject(byte[] bytes){
		try {
			return (JSONObject) JSON.parseObject(new String(bytes,CHARSET));
		} catch (Exception e) {
			logger.error("JSONUTIL.bytes2JsonObject转化报错：：", e);
			return null;
		}
	}
	
	public static <T> T bytes2T(byte[] bytes,Class<T> classOfT){
		try {
			return JSON.parseObject(new String(bytes,CHARSET),classOfT);
		} catch (Exception e) {
			logger.error("JSONUTIL.bytes2T转化报错：：", e);
			return null;
		}
	}
	

	public static JSONArray Collection2JsonArray(Collection collection){
		try {
			return (JSONArray) JSON.toJSON(collection);
		} catch (Exception e) {
			logger.error("JSONUTIL.Collection2JsonArray转化报错：：", e);
			return null;
		}
	}

	public static String Object2Json(Object o){
		try {
			return JSON.toJSONString(o);
		} catch (Exception e) {
			logger.error("JSONUTIL.Object2Json转化报错：param：{}",o, e);
			return null;
		}
	}
	
	
	public static String Object2JsonPrettyFormat(Object o){
		try {
			return JSON.toJSONString(o, true);
		} catch (Exception e) {
			logger.error("JSONUTIL.Object2JsonPrettyFormat转化报错：param：{}",o, e);
			return null;
		}
	}
	
	
	public static String Object2JsonSerial(Object o){
		try {
			return JSON.toJSONString(o,SerializerFeature.WriteClassName);
		} catch (Exception e) {
			logger.error("JSONUTIL.Object2JsonSerial转化报错：param:{}",o, e);
			return null;
		}
	}
	
	
	public static String list2JsonSerial(List l){
		if(null==l){
			return null;
		}
		try {
			return JSON.toJSONString(l,SerializerFeature.WriteClassName);
		} catch (Exception e) {
			logger.error("JSONUTIL.list2JsonSerial转化报错：：", e);
			return null;
		}
	}
	
	
	public static Object JsonSerial2Object(String json){
		try {
			return JSON.parse(json);
		} catch (Exception e) {
			logger.error(json, e);
			return null;
		}
	}
	
	
	public static List JsonSerial2List(String json){
		try {
			return JSON.parseArray(json);
		} catch (Exception e) {
			logger.error(json, e);
			return null;
		}
	}
	

	public static JSONArray JsonSerial2JsonArray(String json){
		try {
			return JSON.parseArray(json);
		} catch (Exception e) {
			logger.error(json, e);
			return null;
		}
	}
	
	
	public static JSONArray Json2JsonArray(String str){
		if(isBlank(str)){
			return null;
		}
		try {
			return JSON.parseArray(str);
		} catch (Exception e) {
			logger.error(str, e);
			return null;
		}
	}
	
	
	public static <T> T Json2T(String str,Class<T> classOfT){
		if(null==str){
			return null;
		}
		try {
			return JSON.parseObject(str,classOfT);
		} catch (Exception e) {
			logger.error(str, e);
			return null;
		}
	}
	
	
	public static <T> List<T> Json2List(String json,Class<T> classOfT){
		if(null==json){
			return null;
		}
		try {
			return JSON.parseArray(json, classOfT);
		} catch (Exception e) {
			logger.error(json, e);
			return null;
		}
	}
	private static boolean isBlank(String str){
		return null==str||"".equals(str);
	}
	
	public static String toJson(Object object, Class beanClass)
	  {
	    if (object == null) {
	      logger.error("object is null");
	      return null;
	    }
	    if (beanClass == null) {
	      logger.error("beanClass is null");
	      return null;
	    }
	    return gson.toJson(object, beanClass);
	  }
	  

    public static void main(String[] args) {
        try {
            String propertesLine = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(new BufferedInputStream(new FileInputStream(
                "C:\\config_1.properties")))));
            Map<String, String> resultMap1 = new HashMap<String, String>();
            while ((propertesLine = br.readLine()) != null) {
                String key = propertesLine.substring(0, propertesLine.indexOf("="));
                String value = propertesLine.substring(propertesLine.indexOf("=") + 1);
                if (resultMap1.get(key) == null) {
                    resultMap1.put(key, value);
                }
            }
            br.close();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(new DataInputStream(new BufferedInputStream(new FileInputStream(
                "C:\\config_2.properties")))));
            Map<String, String> resultMap2 = new HashMap<String, String>();
            while ((propertesLine = br2.readLine()) != null) {
                String key = propertesLine.substring(0, propertesLine.indexOf("="));
                String value = propertesLine.substring(propertesLine.indexOf("=") + 1);
                if (resultMap2.get(key) == null) {
                    resultMap2.put(key, value);
                }
            }
            br2.close();
            for (String key2 : resultMap2.keySet()) {
                if (resultMap1.keySet().contains(key2)) {
                    resultMap1.remove(key2);
                }
            }
            System.out.println(resultMap1);
        } catch (FileNotFoundException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
    }
}
