package com.xcf.scm.web;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.xcf.scm.dal.dao.AcctAccountDAO;
import com.xcf.scm.dal.model.AcctAccountDO;
import com.xcf.scm.web.spring.MvcConfiguration;
import com.forte.runtime.pagination.Page;
import com.forte.runtime.pagination.PaginationBean;
import com.forte.runtime.startup.PropertyLoaderConfig;

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
public class AcctAccountTest {

    @Autowired
    private AcctAccountDAO acctAccountDAO;

    @Test
    public void testCreate()throws Exception{
    	System.out.println("111111");
    	AcctAccountDO acctAccount = new AcctAccountDO();
    	acctAccount.setAcctId("testAcctId2");
    	acctAccount.setAcctName("测试");
    	acctAccount.setAcctStatus("Y");
    	acctAccount.setAcctType("I");
    	acctAccount.setCcy("cny");
    	acctAccount.setPayStop("N");
    	acctAccount.setReceviepayStop("N");
    	acctAccount.setMaxTransAmt(new BigDecimal("200000.00"));
    	acctAccount.setCreateTime(new Date());
    	acctAccount.setLastTransTime(new Date());
    	acctAccountDAO.insertData(acctAccount);      
    }
    
    @Test
    public void testBatchCreate()throws Exception{
    	List<AcctAccountDO> dataList = new ArrayList<AcctAccountDO>();
    	for(int i=1;i<10;i++){
    		AcctAccountDO acctAccount = new AcctAccountDO();
        	acctAccount.setAcctId("batchInsert"+i);
        	acctAccount.setAcctName("测试"+i);
        	acctAccount.setAcctStatus("Y");
        	acctAccount.setAcctType("I");
        	acctAccount.setCcy("cny");
        	acctAccount.setPayStop("N");
        	acctAccount.setReceviepayStop("N");
        	acctAccount.setMaxTransAmt(new BigDecimal("200000.00"));
        	acctAccount.setCreateTime(new Date());
        	acctAccount.setLastTransTime(new Date());
        	dataList.add(acctAccount);
    	}
    	 acctAccountDAO.batchInsertData(dataList);      
    }
    
    @Test
    public void testUpdate()throws Exception{
    	System.out.println("111111");
    	AcctAccountDO acctAccount = new AcctAccountDO();
    	acctAccount.setAcctId("testAcctId1");
    	acctAccount.setAcctName("测试1");
    	acctAccount.setAcctStatus("Y1");
    	acctAccount.setAcctType("I1");
    	acctAccount.setCcy("cn1");
    	acctAccount.setPayStop("Y");
    	acctAccount.setReceviepayStop("Y");
    	acctAccount.setMaxTransAmt(new BigDecimal("200000.00"));
    	//acctAccount.setCreateTime(new Date());
    	acctAccount.setLastTransTime(new Date());
    	acctAccountDAO.updateData(acctAccount);
    }
    
    @Test
    public void testDelete()throws Exception{
    	System.out.println("111111");
    	AcctAccountDO acctAccount = new AcctAccountDO(); 
    	acctAccount.setAcctId("testAcctId1");
    	acctAccount.setAcctName("测试1");
    	acctAccount.setAcctStatus("Y1");
    	acctAccount.setAcctType("I1");
    	acctAccount.setCcy("cn1");
    	acctAccount.setPayStop("Y");
    	acctAccount.setReceviepayStop("Y");
    	acctAccount.setMaxTransAmt(new BigDecimal("200000.00"));
    	acctAccount.setCreateTime(new Date());
    	acctAccount.setLastTransTime(new Date());
    	acctAccountDAO.updateData(acctAccount);
    }
    
    @Test
    public void testQuery()throws Exception{
    	System.out.println("111111");
    	AcctAccountDO acctAccount = new AcctAccountDO();
    	acctAccount.setAcctId("testAcctId1");
    	acctAccount.setAcctName("测试1");
    	acctAccount.setAcctStatus("Y1");
    	acctAccount.setAcctType("I1");
    	acctAccount.setCcy("cn1");
    	acctAccount.setPayStop("Y");
    	acctAccount.setReceviepayStop("Y");
    	acctAccount.setMaxTransAmt(new BigDecimal("200000.00"));
    	acctAccount.setCreateTime(new Date());
    	acctAccount.setLastTransTime(new Date());
    	acctAccountDAO.updateData(acctAccount);
    }
    
    @Test
    public void testQueryForPage()throws Exception{
    	System.out.println("111111");
    	AcctAccountDO acctAccount = new AcctAccountDO();
//    	acctAccount.setAcctId("testAcctId1");
//    	acctAccount.setAcctName("测试1");
//    	acctAccount.setAcctStatus("Y1");
//    	acctAccount.setAcctType("I1");
//    	acctAccount.setCcy("cn1");
//    	acctAccount.setPayStop("Y");
//    	acctAccount.setReceviepayStop("Y");
//    	acctAccount.setMaxTransAmt(new BigDecimal("200000.00"));
//    	acctAccount.setCreateTime(new Date());
//    	acctAccount.setLastTransTime(new Date());
    	Page page = new Page();
    	page.setCurrPage(2);
    	page.setPageSize(2);
    	PaginationBean<AcctAccountDO> dataList =  acctAccountDAO.queryForListByPagination(acctAccount, page);
    	List<AcctAccountDO> accountList = dataList.getPageList();
    	for(AcctAccountDO data:accountList){
    		System.out.println(data.getAcctId());
    	}
    	
    }
    
}
