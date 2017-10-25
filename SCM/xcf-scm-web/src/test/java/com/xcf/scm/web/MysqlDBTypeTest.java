package com.xcf.scm.web;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.xcf.scm.dal.dao.AcctAccountDAO;
import com.xcf.scm.dal.model.AcctAccountDO;
import com.xcf.scm.web.spring.MvcConfiguration;
import com.forte.runtime.startup.PropertyLoaderConfig;
import com.forte.runtime.util.DateUtil;

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
@ContextConfiguration(classes = {PropertyLoaderConfig.class,MvcConfiguration.class})
@WebAppConfiguration
public class MysqlDBTypeTest {

    @Autowired
    private AcctAccountDAO acctAccountDAO;

    @Test
    public void testMaxDecimal(){
    	System.out.println("111111");
    	AcctAccountDO acctAccount = new AcctAccountDO();
    	acctAccount.setAcctId("maxDecima12");
    	acctAccount.setAcctName("maxTransAmt:-1234567890123.12341");
    	acctAccount.setAcctStatus("Y");
    	acctAccount.setAcctType("I");
    	acctAccount.setCcy("cny");
    	acctAccount.setPayStop("N");
    	acctAccount.setReceviepayStop("N");
    	acctAccount.setMaxTransAmt(new BigDecimal("-1234567890.12341"));
    	//acctAccount.setDecimalTest(new BigDecimal("1234567891.11"));
    	acctAccount.setCreateTime(DateUtil.toDate("2039-01-01 00:00:00"));
    	acctAccount.setLastTransTime(new Date());
    	acctAccountDAO.insertData(acctAccount);      
    }
    
    @Test
    public void testTimeStamp(){
    	System.out.println("111111");
    	AcctAccountDO acctAccount = new AcctAccountDO();
    	acctAccount.setAcctId("maxDecima3");
    	acctAccount.setAcctName("maxTransAmt:-1234567890123.12341");
    	acctAccount.setAcctStatus("Y");
    	acctAccount.setAcctType("I");
    	acctAccount.setCcy("cny");
    	acctAccount.setPayStop("N");
    	acctAccount.setReceviepayStop("N");
    	acctAccount.setMaxTransAmt(new BigDecimal("-1234567890.12341"));
    	//acctAccount.setDecimalTest(new BigDecimal("1234567891.11"));
    	acctAccount.setCreateTime(new Date());
    	acctAccount.setLastTransTime(new Date());
    	acctAccountDAO.insertData(acctAccount);      
    }
    
   
}
