package com.xcf.scm.mongo.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.xcf.scm.mongo.model.InResourceDO;

/**
 * 
 * <p>mongo测试类</p>
 * Created by liaosifa on 2015年08月17号
 */
public interface InTestRepository extends MongoRepository<InResourceDO,String> {
    
    @Query("{'resourceNo':?0}")
    public InResourceDO findResourceByNo(String resourceNo);
    
    @Query("{'status':?0}")
    public List<InResourceDO> findAllByStatus(String status);
        
    @Query("{'parentNo':?0}")
    public List<InResourceDO> findResourceByParentNo(String parentNo);	  
    
    @Query("{\"resourceNo\":{\"$regex\": ?0}}")
    Page<InResourceDO> findPageByResourceNo(String resourceNo,Pageable pageable);
    
}
