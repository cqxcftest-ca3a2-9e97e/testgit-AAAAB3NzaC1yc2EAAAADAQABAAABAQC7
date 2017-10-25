package com.forte.runtime.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.UUID;

/**
 * Created by WangBin on 2016/2/2.
 */
public class AccessKeyGenerator {
    public static void main(String[] args)throws Exception{
        String time = new Date().getTime()+"";
        String uid = UUID.randomUUID().toString();
        String key = "";
        key = genAccessKey(uid);//
        //提供给客户端的uuid,access-key
        System.out.println("access-uuid:"+uid);
        System.out.println("access-key:"+key);
        //客户端自己生成密钥
        String signature = genSignature(key,time,"/service/method/");
        //服务端生成的密钥
        String sec = genSignatureKey(uid,time,"/service/method/");
        System.out.println("secure-key-from-client:"+signature);
        System.out.println("secure-key-from-server:"+sec);
    }

    private static byte[] hmacSHA256(String data, byte[] key) throws Exception  {
        String algorithm="HmacSHA256";
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(data.getBytes("UTF8"));
    }
    /**
     * 服务端生成，不暴漏给客户端
     * @param uid
     * @return
     * @throws Exception
     */
    public static String genAccessKey(String uid)throws Exception{
        return Hex.encodeHexStr(hmacSHA256(uid,("AWS4"+uid).getBytes("UTF8")));
    }

    /**
     * 客户端 生成验证签名
     * @param ak
     * @param serviceName
     * @return
     * @throws Exception
     */
    public static String genSignature(String ak,String ts,String serviceName) throws Exception{
        byte[] kDate = Hex.decodeHex(ak.toCharArray());
        byte[] kRegion  = hmacSHA256(ts, kDate);
        byte[] kService = hmacSHA256(serviceName, kRegion);
        byte[] kSigning = hmacSHA256("aws4_request", kService);
        return Hex.encodeHexStr(kSigning,true);
    }

    /***
     * 服务方 产生服务签名
     * @param uuid
     * @param serviceName
     * @return
     * @throws Exception
     */
    public static String genSignatureKey(String uuid,String ts,String serviceName) throws Exception  {
        byte[] kSecret  = ("AWS4" + uuid).getBytes("UTF8");
        byte[] kDate    = hmacSHA256(uuid, kSecret);
        byte[] kRegion  = hmacSHA256(ts, kDate);
        byte[] kService = hmacSHA256(serviceName, kRegion);
        byte[] kSigning = hmacSHA256("aws4_request", kService);
        return  Hex.encodeHexStr(kSigning,true);
    }
}
