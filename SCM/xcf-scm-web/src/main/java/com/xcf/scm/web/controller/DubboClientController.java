package com.xcf.scm.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.xcf.scm.biz.DubboClient;
import com.forte.runtime.spring.AppContextConfig;
import com.forte.runtime.util.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by wangbin on 2016/10/8.
 */
/*@Controller
@RequestMapping("/dubbo")*/
public class DubboClientController {

    private static final Logger logger = LoggerFactory.getLogger(DubboClientController.class);

    @Autowired
    private DubboClient dubboClient;

    @ResponseBody
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView();
        /*dubboClient.init(zkhost);
        List fs = dubboClient.getInterfaceList(group);
        view.addObject("data",fs);*/
        view.addObject("zkHost", AppContextConfig.get("dubbo.zkHost","172.95.65.34"));
        return view;
    }
    @ResponseBody
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public Object init(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam String zkhost,@RequestParam String group) throws Exception {
        //ModelAndView view = new ModelAndView();
        dubboClient.init(zkhost);
        List fs = dubboClient.getInterfaceList(group);
        //view.addObject("data",fs);
        //Map map = new HashMap();
        //map.put("data",fs);
        return fs;
    }

    @ResponseBody
    @RequestMapping(value = "/getMethods", method = RequestMethod.POST)
    public Object getMethods(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam String cls,@RequestParam String version) throws Exception {
        logger.info("param:cls:{},version:{}",cls,version);
        List<String> res = dubboClient.getMethods(cls,version);

        return res;
    }
    @ResponseBody
    @RequestMapping(value = "/getSignature", method = RequestMethod.POST)
    public Object getSignature(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam String cls,@RequestParam String version
                                ,@RequestParam String mtd) throws Exception {
        Map res = dubboClient.getSignature(cls,version,mtd);
        //logger.info("getSignatrue-result:{}",res);
        return res;
    }
    @ResponseBody
    @RequestMapping(value = "/invoke", method = RequestMethod.POST/*,produces = {MediaType.APPLICATION_JSON_VALUE}*/)
    public String load(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam String cls,@RequestParam String version,@RequestParam String group,
                       @RequestParam String mtd,@RequestParam String input) throws Exception {
        String res = null;
        response.setHeader("Content-Type","text/plain;charset=UTF-8");
        try {
            Object ret = dubboClient.invoke(cls,group, version, mtd, input.split(";"));
            if(ret!=null) {
                try {
                    res = JSONObject.toJSONString(ret);
                }catch (Exception ex) {
                    //res = JsonUtil.toJson(res, res.getClass());
                    res = XmlUtil.toXml(ret);
                }
            }
        }catch(Exception ex){
           res = ex.getMessage();
        }
        logger.info("invoke-result:{}",res);
        return res;
    }
}
