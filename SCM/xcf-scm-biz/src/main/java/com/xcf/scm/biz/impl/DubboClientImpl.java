package com.xcf.scm.biz.impl;

import com.alibaba.dubbo.common.utils.ReflectUtils;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MethodConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.xcf.scm.biz.DubboClient;
import com.forte.runtime.spring.AppContextConfig;
import com.forte.runtime.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by WangBin on 2016/8/16.
 */
@Service
public class DubboClientImpl implements DubboClient {
    private ZookeeperUtil zk = null;
    private static Logger logger = LoggerFactory.getLogger(DubboClientImpl.class);
    private ApplicationConfig application;
    private RegistryConfig registry;
    private List<String> cls;

    public static void main(String[] args)throws Exception{
        DubboClient client = new DubboClientImpl();
        client.init("172.20.11.103");
        //client.init("localhost");
        /*AccountRequest req = new AccountRequest();
        req.setPropertyId("-1000");
        req.setNodeSeq("-1000.");*/
        Object res = null;
        String[] input = null;
        /*input = new String[]{"{\"propertyId\":-1000,\"accountId\":10,\"nodeSeq\":\"-1000.\"}"};
        res = client.invoke(AccountFacade.class.getName(), "1.0", "getCommunityTree", input);
        logger.info("result="+res);
        */
        /*input = new String[]{"-1000","notice"};
        res = client.invoke(DictCodeFacade.class.getName(),"dubbo","1.0","queryTypeByModule",input);
        logger.info("result="+res);

        res = client.getSignature(AccountFacade.class.getName(),"1.0.1","getCommunityTree");
        System.out.println("result="+res);

        logger.info("methods:{}",client.getMethods(DictCodeFacade.class.getName(),"1.0"));
        client.close();*/
    }

    public Object[] transferInput(String[] jsonArr,Class<?>[] paramerTypes)throws Exception{
        if(jsonArr== null||jsonArr.length==0
                || paramerTypes ==null || paramerTypes.length==0) return null;
        
        Object[] res = new Object[jsonArr.length];
        int idx = 0;
        for(String param : jsonArr){
            Class<?> clazz = paramerTypes[idx];
            if(ReflectUtils.isPrimitives(clazz)){
                res[idx] = param;
            }else {
                res[idx] = JsonUtil.jsonStrToBean(param, clazz);
            }
            idx++;
        }
        return res;
    }

    @Override
    public void init(String env) throws Exception{
        if(zk == null) {
            zk = new ZookeeperUtil();
            //env =("172.20.11.103")
            zk.connect(env);
            application = new ApplicationConfig();
            application.setName("dubbo-test");
            registry = new RegistryConfig();
            registry.setAddress("zookeeper://" + env + ":2181");
        }
    }

    @Override
    public void close() {
        if(zk!=null){
            try {
                zk.close();
            }catch (Exception ex){
                logger.info("close-exception:",ex);
            }
        }
    }

    @Override
    public List<String> getInterfaceList(String group) {
        try {
            List<String> list = zk.getChild("/"+group);
            Collections.sort(list, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareToIgnoreCase(o2);
                }
            });
            return list;
        }catch (Exception ex){
            logger.error("getInterfaceList-error",ex);
            return null;
        }
    }

    @Override
    public List<String> getMethods(String interfaceName, String version)throws Exception {
        ReferenceConfig reference = new ReferenceConfig();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(interfaceName);//接口类
        reference.setVersion(version);//服务接口版本
        Class<?> interfaceClass = null;
        try {
            interfaceClass = reference.getInterfaceClass();
        }catch (IllegalStateException ex){
            interfaceClass = Class.forName(interfaceName, true, new ClassLoader() {
                @Override
                protected Class<?> findClass(String name) throws ClassNotFoundException {
                    System.setProperty("java.class.path", AppContextConfig.get("dubbo.facade.classpath","/wls/lib"));
                    return super.findClass(name);
                }
            });
        }
        //reference.get();
        Method[] methods = interfaceClass.getMethods();
        List<String> list = new ArrayList<>(methods.length);
        for(Method m:methods){
            list.add(m.getName());
        }
        return list;
    }

    @Override
    public Map getSignature(String interfaceName, String version, String method) throws Exception {
        ReferenceConfig reference = new ReferenceConfig();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(interfaceName);//接口类
        reference.setVersion(version);//服务接口版本
        Class<?> interfaceClass = reference.getInterfaceClass();
        Method mt = ReflectUtils.findMethodByMethodName(interfaceClass, method);
        logger.info("parameters=:{},response:{}",mt.getParameterTypes(),mt.getReturnType());
        Map map = new HashMap(3);
        StringBuffer detail = new StringBuffer("");
        for(Class cls : mt.getParameterTypes()){
            if(ReflectUtils.isPrimitive(cls)){
                detail.append("\n").append(cls.getName()).append("\n");
            }else {
                detail.append("\n").append(cls.getName()).append("={\n");
                for (Field f : cls.getDeclaredFields()) {
                    detail.append(f.getName()).append(":").append(f.getType().getSimpleName()).append(",\n");
                }
                detail.append("};\n");
            }
        }
        //JSONArray.parse
        map.put("input",ReflectUtils.getSignature(method,mt.getParameterTypes()));
        map.put("inputDetail",detail.toString());
        map.put("output",mt.getReturnType().getName());
        //logger.info("signature:{}",map);
        return map;
    }

    @Override
    public Object invoke(String interfaceName,String group,String version,String method,String[] jsonArr) throws Exception {
        if(cls!=null && !cls.contains(interfaceName)) {
            cls = getInterfaceList(group);
        }
        if(cls!=null && !cls.contains(interfaceName)){
            throw new Exception("interface:["+interfaceName+"] do not found in zk");
        }
        //Class clazz = Class.forName(interfaceName);
        ReferenceConfig reference = new ReferenceConfig();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(interfaceName);//接口类
        reference.setVersion(version);//服务接口版本
        Class<?> interfaceClass = reference.getInterfaceClass();
        Object target = null;
        if((target=proxyHolder.get(interfaceName+"-"+version))==null) {
            target = reference.get();
            proxyHolder.put(interfaceName + "-" + version, target);
        }

        List<MethodConfig> methods = reference.getMethods();
        Method[] mths = interfaceClass.getMethods();
        //Map parameters = reference.getParameters();
        Method mt = ReflectUtils.findMethodByMethodName(interfaceClass, method);
        logger.info("parameters=:{},response:{}",mt.getParameterTypes(),mt.getReturnType());
        Object t = null;
        try {
            Object[] input = transferInput(jsonArr,mt.getParameterTypes());
            t = mt.invoke(target, input);
        }finally {
            //reference.destroy();
        }
        return t;
    }

    private static Map proxyHolder = new HashMap();
}
