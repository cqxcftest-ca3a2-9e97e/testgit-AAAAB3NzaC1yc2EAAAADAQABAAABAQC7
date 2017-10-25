package com.forte.runtime.spring;


import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.support.IsNewStrategyFactory;
import org.springframework.stereotype.Component;

/**
 * Created by WangBin on 2016/4/28.
 */
@Configuration
@Component
@ComponentScan(basePackages="com.forte")
public class MongoConfiguration extends AbstractMongoConfiguration {

    public boolean isEnabled(){
        return "true".equals(AppContextConfig.get("common.mongodb.enabled"));
    }

    @Override
    public MongoMappingContext mongoMappingContext() throws ClassNotFoundException {
        if(!isEnabled()) return null;
        return super.mongoMappingContext();
    }

    @Override
    public IsNewStrategyFactory isNewStrategyFactory() throws ClassNotFoundException {
        if(!isEnabled()) return null;
        return super.isNewStrategyFactory();
    }

    @Override
    public CustomConversions customConversions() {
        if(!isEnabled()) return null;
        return super.customConversions();
    }

    @Override
    public MappingMongoConverter mappingMongoConverter() throws Exception {
        if(!isEnabled()) return null;
        return super.mappingMongoConverter();
    }


    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        if(!isEnabled()) return null;

        return new MongoTemplate(this.mongoDbFactory(), this.mappingMongoConverter());
    }

    @Override
    protected String getDatabaseName() {
        if(!isEnabled()) return null;

        String dbName = AppContextConfig.get("mongodb.name");
        if(dbName==null || dbName.isEmpty()){
            dbName = "forte";
        }
        return dbName;
    }

    protected String getCacheDatabaseName() {
        if(!isEnabled()) return null;

        String dbName = AppContextConfig.get("mongodb.cache.dbName");
        if(dbName==null || dbName.isEmpty()){
            dbName = "forte_cache";
        }
        return dbName;
    }


    @Override
    @Bean
    public Mongo mongo() throws Exception {
        if(!isEnabled()) return null;
        /*String host = AppContextConfig.get("mongodb.host");
        if(host==null || host.isEmpty()){
            host = "127.0.0.1";
        }
        Mongo mongo = new Mongo(host);
        mongo.setWriteConcern(WriteConcern.SAFE);
        return mongo;*/
        return mongoClient();
    }

    @Bean
    public Mongo mongoFile() throws Exception {
        if(!isEnabled()) return null;
        return mongoClient();
    }


    @Bean
    public MongoClient mongoClient()throws Exception{
        if(!isEnabled()) return null;

        String username = AppContextConfig.get("mongodb.username");
        String password = AppContextConfig.get("mongodb.password");
        //ServerAddress seed1 = new ServerAddress();
        String hostPorts = AppContextConfig.get("mongodb.host-port");
        //另一种通过URI初始化
        //mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]
        MongoClientURI str = new MongoClientURI("mongodb://" + username + ":" + password + "@" +
                hostPorts+ "/" + getDatabaseName() +
                "" + AppContextConfig.get("mongodb.options",""));
        return new MongoClient(str);
    }

    @Bean
    @Override
    public MongoDbFactory mongoDbFactory() throws Exception {
        if(!isEnabled()) return null;

        String username = AppContextConfig.get("mongodb.username");
        if(username==null || username.isEmpty()){
            throw new Exception("mongodb数据库用户名不能为空！");
        }
        String password = AppContextConfig.get("mongodb.password");
        if(password==null || password.isEmpty()){
            throw new Exception("mongodb数据库密码不能为空！");
        }

        //UserCredentials userCredentials = new UserCredentials(username,password);
        return new SimpleMongoDbFactory(mongoClient(), getDatabaseName());
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        if(!isEnabled()) return null;
        if(AppContextConfig.get("common.mongodb.files.enable","false").equals("true")) {
            /*return new GridFsTemplate(new SimpleMongoDbFactory(mongo(),
                    "files", this.getUserCredentials(),
                    this.getAuthenticationDatabaseName()), mappingMongoConverter());*/
            return new GridFsTemplate(new SimpleMongoDbFactory(mongoClient(), "files"), mappingMongoConverter());
        }
        return null;
    }

}

