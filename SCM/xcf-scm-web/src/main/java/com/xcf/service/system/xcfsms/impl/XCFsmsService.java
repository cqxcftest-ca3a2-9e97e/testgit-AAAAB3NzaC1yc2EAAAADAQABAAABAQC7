package com.xcf.service.system.xcfsms.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xcf.dao.DaoSupport;
import com.xcf.entity.Page;
import com.xcf.util.PageData;
import com.xcf.service.system.xcfsms.XCFsmsManager;

/** 
 * 说明： 站内信
 * 创建人：Micro Chen
 * 创建时间：2017-09-25
 * @version
 */
@Service("xcfsmsService")
public class XCFsmsService implements XCFsmsManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("XCFsmsMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("XCFsmsMapper.delete", pd);
	}
	
	/**修改状态
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("XCFsmsMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("XCFsmsMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XCFsmsMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XCFsmsMapper.findById", pd);
	}
	
	/**获取未读总数
	 * @param pd
	 * @throws Exception
	 */
	public PageData findXCFsmsCount(String USERNAME)throws Exception{
		return (PageData)dao.findForObject("XCFsmsMapper.findXCFsmsCount", USERNAME);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("XCFsmsMapper.deleteAll", ArrayDATA_IDS);
	}

	
	
}

