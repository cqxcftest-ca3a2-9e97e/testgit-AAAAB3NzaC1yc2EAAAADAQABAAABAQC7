package com.xcf.scm.biz;

import java.util.List;
import java.util.Map;

/**
 * Created by WangBin on 2016/8/16.
 */
public interface DubboClient {
    /**
     * 获取所有接口
     * @return
     */
    List<String> getInterfaceList(String group);

    /**
     * 获取方法列表
     * @param interfaceName
     * @param version
     * @return
     */
    List<String> getMethods(String interfaceName,String version)throws Exception;

    /**
     * 获取接口 input,output
     * @param interfaceName
     * @param version
     * @param method
     * @return
     * @throws Exception
     */
    Map getSignature(String interfaceName,String version,String method)throws Exception;

    /**
     * 页面json输入 转为接口输入参数
     * @param jsonArr
     * @param paramerTypes
     * @return
     * @throws Exception
     */
    Object[] transferInput(String[] jsonArr,Class<?>[] paramerTypes)throws Exception;

    //Object invoke(String interfaceName,String version,String method,Object... jsonArr) throws Exception;

    /***
     * 接口调用
     *
     * @param interfaceName
     * @param version
     * @param method
     * @param jsonArr
     * @return
     * @throws Exception
     */
    Object invoke(String interfaceName,String group,String version,String method,String[] jsonArr) throws Exception;

    /**
     * 访问zookeeper，初始化
     * @param env
     * @throws Exception
     */
    void init(String env) throws Exception;

    /***
     * 关闭zookeeper连接
     */
    void close();
}
