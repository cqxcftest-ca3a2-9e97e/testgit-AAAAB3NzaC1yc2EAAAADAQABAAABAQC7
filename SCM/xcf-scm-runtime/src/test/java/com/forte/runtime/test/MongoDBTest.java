package com.forte.runtime.test;

import com.forte.runtime.spring.MongoConfiguration;
import com.forte.runtime.startup.PropertyLoaderConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by WangBin on 2016/4/29.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PropertyLoaderConfig.class, MongoConfiguration.class})
public class MongoDBTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testSave(){
        /*Map map = new HashMap();
        map.put("key1","val1");
        map.put("key2","val2");
        map.put("key3","val3");
        mongoTemplate.save(map);
        */
        //if()
        Model model = new Model();
        model.setValue("value");
        mongoTemplate.save(model);

        model = new Model();
        model.setValue("value11");
        mongoTemplate.save(model);
        System.out.println(mongoTemplate.getCollection("test").count());
        System.out.println("collectionName="+Model.class.getAnnotation(Document.class).collection());
    }
}
