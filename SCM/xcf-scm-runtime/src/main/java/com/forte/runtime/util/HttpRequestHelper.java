package com.forte.runtime.util;

import com.forte.runtime.spring.AppContextConfig;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/***
 * @desc
 * @author wangbin
 * @date 2015/8/27
 */
public class HttpRequestHelper {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestHelper.class);

    private HttpRequestHelper(){

    }
    public static void main(String[] args){
        Map map = new HashMap();
        map.put("owner","gdcs");
        map.put("env","dev");
        String configCenter = "http://172.20.11.116:9080/configure/configureItem/loadSysCfg/";
        String[] params = new String[]{"owner","captcha","env","dev"};
        String response = HttpRequestHelper.httpPost(configCenter, params,"application/json");
        System.out.println(response);
    }

    public static String httpPost(String url, Map<String,Object> params,String contentType) {
        if (StringUtils.isEmpty(url)) {
            //throw new IllegalArgumentException("error:url must not be empty.");
            logger.info("url is null");
            return "";
        }
        HttpParams httpParams = new BasicHttpParams();
        if ((params != null) && (!params.keySet().isEmpty())) {
            //httpParams = new BasicHttpParams();
            for(Map.Entry<String,Object> e : params.entrySet()) {
                httpParams.setParameter(""+e.getKey(),e.getValue());
                url = url.concat("&" + e.getKey() + "=" + e.getValue() + "");
            }
            if (!url.contains("?")) {
                url = url.replaceFirst("&", "?");
            }
        }
        return post(url,httpParams,contentType);
    }

    public static String httpPost(String url, Object[] params,String contentType) {
        if (StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException("error:url must not be empty.");
        }
        if ((params != null) && (params.length > 1)) {
            for (int i = 0; i <= params.length - 2; i += 2) {
                url = url.concat("&" + params[i] + "=" + params[(i + 1)] + "");
            }
            if (!url.contains("?")) {
                url = url.replaceFirst("&", "?");
            }
        }
        return post(url,new BasicHttpParams(),contentType);
    }

    private static String post(String url,HttpParams params,String contentType){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpPost postRequest = null;
        try {
            httpClient = HttpClients.createDefault();
            postRequest = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(AppContextConfig.getInteger("http.caller.connectionTimeout",5000))
                    .setConnectionRequestTimeout(AppContextConfig.getInteger("http.caller.connectionRequestTimeout",4000))
                    .setSocketTimeout(AppContextConfig.getInteger("http.caller.socketTimeout",3000))
                    .build();
            postRequest.setConfig(requestConfig);
            postRequest.addHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            logger.info("request-url:\n{}",url);
            StringEntity input = new StringEntity("","UTF-8");
            input.setContentType(contentType);
            postRequest.setParams(params);
            postRequest.setEntity(input);
            response = httpClient.execute(postRequest);
            //logger.info("response:{}",response);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }
            String jsonStr = EntityUtils.toString(response.getEntity(),"UTF-8");
            logger.info("Output-from-Server-json-str .... "+jsonStr);
            return jsonStr;
        }catch (Exception ex){
            logger.error("error,",ex);
        }finally {
            if(response!=null){
                try {
                    response.close();
                }catch (Exception ex){
                    logger.error("close-client-error:",ex);
                }
            }
        }
        return null;
    }

}
