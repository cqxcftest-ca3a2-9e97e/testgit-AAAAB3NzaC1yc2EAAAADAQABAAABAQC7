package com.xcf.scm.biz.impl;

/*import com.xcf.scm.facade.HelloFacade;
import com.xcf.scm.facade.request.AccountQueryReq;
import com.xcf.scm.facade.response.AccountQueryRes;*/
import com.forte.runtime.mq.JmsQueueSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component(value = "helloFacade")
public class HelloFacadeImpl/* implements HelloFacade*/ {

	private static Logger logger = LoggerFactory.getLogger(HelloFacadeImpl.class);

	@Autowired
	private JmsQueueSender queueSender;
	
	//@Override
	@Cacheable(cacheNames = "cache-simple-val")
	public String hello(String input) {
		logger.info("param-input={}",input);
		//queueSender.sendMessage("hello,test-send-queue-msg", AppContextConfig.get("mq.destinationName.test"));
		return "hello "+input;
	}

	//@Override
	/*@Cacheable(cacheNames = "cache-account",key = "#req.loginName+#req.status")
	public AccountQueryRes queryAccount(AccountQueryReq req) {
		logger.info("query-Account,req:{}",req);
		return null;
	}*/
}
