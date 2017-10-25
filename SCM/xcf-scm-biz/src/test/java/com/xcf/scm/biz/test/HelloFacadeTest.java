package com.xcf.scm.biz.test;

import com.xcf.scm.biz.impl.HelloFacadeImpl;
import com.xcf.scm.biz.spring.CacheConfig;
import com.xcf.scm.biz.spring.DubboCfg;
import com.xcf.scm.biz.spring.MQConfig;
//import com.xcf.scm.facade.HelloFacade;
//import com.xcf.scm.facade.request.AccountQueryReq;
import junit.framework.TestCase;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.spring.SpringCacheManager;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by WangBin on 2016/1/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DbConfigTest.class, DubboCfg.class,MQConfig.class, CacheConfig.class})
public class HelloFacadeTest extends TestCase {

	//@Autowired
	//private HelloFacadeImpl helloFacade;

	@Test
	public void testQuery() {
		/*for (int i = 0; i < 10; i++){
			helloFacade.hello("sj1234" + i%5);
		}*/
	}

	@Test
	public void testQueryAccount() {
		for (int i = 0; i < 10; i++){
			/*AccountQueryReq req = new AccountQueryReq();
			req.setLoginName("loginName"+i%5);
			req.setStatus("1");
			System.out.println("query-account:"+req.getLoginName());
			helloFacade.queryAccount(req);*/
		}
	}

	@Before
	public void init(){
		//IgniteCache cache = ignite.getOrCreateCache("cache-account");
		Cache cache =  cacheManager.getCache("cache-account");
		cache.clear();
	}
	@After
	public void clear(){
		//IgniteCache cache = ignite.getOrCreateCache("cache-account");
		Cache cache =  cacheManager.getCache("cache-account");
		//cache.clear();
		System.out.println("get-loginName0="+cache.get("loginName01").get());
		IgniteCache igniteCache = (IgniteCache)cache.getNativeCache();

		cache.evict("loginName01");
		cache.evict("loginName21");
		System.out.println("get-loginName0="+igniteCache.containsKey("loginName01"));
	}

	/**
	 * ignite-client
     */
	//@IgniteInstanceResource
	private Ignite ignite;

	//@Autowired
	private SpringCacheManager cacheManager;
}
