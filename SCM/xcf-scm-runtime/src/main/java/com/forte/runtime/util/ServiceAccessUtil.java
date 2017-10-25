package com.forte.runtime.util;

import com.forte.runtime.bean.ServiceAccessRequest;
import com.forte.runtime.bean.ServiceAccessResponse;
import com.forte.runtime.spring.AppContextConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by WangBin on 2016/5/4.
 */
public class ServiceAccessUtil {
    private static Logger logger = LoggerFactory.getLogger(ServiceAccessUtil.class);

    public static ServiceAccessResponse generateSignature(ServiceAccessRequest accessRequest){
        ServiceAccessResponse response = new ServiceAccessResponse();
        if("true".equals(AppContextConfig.get("code.genSignature.enabled"))) {
            try {
                String ak = AccessKeyGenerator.genAccessKey(accessRequest.getSeed());
                response.setAccessKey(ak);
                String sig = "";
                sig = AccessKeyGenerator.genSignature(ak, accessRequest.getRandomStr(), accessRequest.getServiceURL());
                response.setServiceSignature(sig);
                response.setSeed(accessRequest.getSeed());
                response.setRandomStr(accessRequest.getRandomStr());
                response.setServiceURL(accessRequest.getServiceURL());
                logger.info("seed={},ak={},key-generate-from-client:{}", accessRequest.getSeed(), ak, sig);
                response.initSuccess();
            } catch (Exception ex) {
                logger.error("generateSignature-error:" + ex.getMessage());
            }
        }
        return response;
    }
}
