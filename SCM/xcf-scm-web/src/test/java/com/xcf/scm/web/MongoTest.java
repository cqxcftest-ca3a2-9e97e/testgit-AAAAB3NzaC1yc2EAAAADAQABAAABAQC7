package com.xcf.scm.web;

import com.xcf.scm.mongo.model.InResourceDO;
import com.xcf.scm.mongo.repository.InTestRepository;
import com.forte.runtime.spring.MongoConfiguration;
import com.forte.runtime.startup.PropertyLoaderConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 
 * ClassName: AcctAccountTest <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2015-12-22 上午10:48:17 <br/> 
 * 
 * @author liaosifa 
 * @version  
 * @since JDK 1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PropertyLoaderConfig.class,MongoConfiguration.class})
@WebAppConfiguration
public class MongoTest {

	 @Autowired
	 private InTestRepository testRepository;

	 @Test
	 public void testCreate()throws Exception{
	    	InResourceDO dao = new InResourceDO();
	    	dao.setParentNo("1231311");
	    	dao.setStatus("Y");
	    	dao.setSystemCode("tes1t11");
	    	dao.setResourceNo("11231111");
	    	dao =testRepository.save(dao);      
	    	System.out.println(dao.getParentNo());
	    	System.out.println(dao.getId());
	 }
   
}
