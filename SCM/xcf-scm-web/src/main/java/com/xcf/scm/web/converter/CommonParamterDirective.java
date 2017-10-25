package com.xcf.scm.web.converter;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.forte.runtime.util.JsonUtil;
import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

/**
 * 
 * ClassName: CommonParamterDirective <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2016-6-21 下午3:40:19 <br/> 
 * 
 * @author liaosifa 
 * @version  
 * @since JDK 1.7
 */
public class CommonParamterDirective implements TemplateDirectiveModel {
    
    /** 
     * @see TemplateDirectiveModel#execute(Environment, Map, TemplateModel[], TemplateDirectiveBody)
     */
    @Override
    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
        if (!params.containsKey("type")) {
            return;
        }
        SimpleScalar typeValue = (SimpleScalar) params.get("type");
        SimpleScalar codeValue = (SimpleScalar) params.get("code");
        SimpleScalar outputValue = (SimpleScalar) params.get("output");
        if (typeValue == null) {
            return;
        } else {
            if (codeValue != null) {
                //将基表中对应code的信息转换成描述信息
                //String result = commonParamterContext.getValueChineseName(typeValue.getAsString(), codeValue.getAsString());  
            	String result ="";
                if (StringUtils.isEmpty(result)) {
                    result = "empty";
                }
                Writer out = env.getOut();
                out.write(result);
            } else {
                if (outputValue != null) {
                    if (outputValue.getAsString().equals("grid")) {
                        //处理在datagrid中显示基表code的情况
                        List<CommonParameter> paramList = new ArrayList<CommonParameter>();
                        String result = "";
                        result = "<% if(!grid.data('" + typeValue.getAsString() + "')){";
                        result += "var " + typeValue.getAsString() + " = " + JsonUtil.beanToJson(paramList) + ";";
                        result += "grid.data('" + typeValue.getAsString() + "'," + typeValue.getAsString() + ");}%>";
                        Writer out = env.getOut();
                        out.write(result);
                    } else if (outputValue.getAsString().equals("list")) {
                        //输出基表中某一类的信息列表
                    	 List paramList = new ArrayList();
                        env.setVariable("list", DEFAULT_WRAPPER.wrap(paramList));
                        body.render(env.getOut());
                    }else if (outputValue.getAsString().equals("date")) {
                    	List<CommonParameter> paramList = new ArrayList<CommonParameter>();
                    	CommonParameter parameter = new CommonParameter();
                    	parameter.setParamCode("CONVERT_MILLISECOND");
                    	parameter.setParamDesc("CONVERT_MILLISECOND");
                    	paramList.add(parameter);
                    	String result = "";
                        result = "<% if(!grid.data('" + typeValue.getAsString() + "')){";
                        result += "var " + typeValue.getAsString() + " = " + JsonUtil.beanToJson(paramList) + ";";
                        result += "grid.data('" + typeValue.getAsString() + "'," + typeValue.getAsString() + ");}%>";
                        Writer out = env.getOut();
                        out.write(result);
                    }else if (outputValue.getAsString().equals("enumList")) {
                        //输出基表中某一类的信息列表
                        List<CommonParameter> paramList = new ArrayList<CommonParameter>();
                        env.setVariable("list", DEFAULT_WRAPPER.wrap(paramList));
                        body.render(env.getOut());
                    }

                }
            }
        }
    }
    


}
