package com.forte.runtime;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;

import com.forte.runtime.spring.AppContextConfig;
import com.forte.runtime.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 替换FileService，屏蔽存储的具体实现:mongodb,redis,...
 *
 * Created by wangbin on 2016/10/14.
 */
//@Component
public class FileServiceClient {
    /*private FileServiceFacade fileServiceFacade;
    private ReferenceConfig<FileServiceFacade> refer;
    private AtomicBoolean initialized = new AtomicBoolean(false);
    @Autowired
    private GridFsTemplate gridFsTemplate;

    private FileServiceClient() {

    }

    public FileServiceFacade getFileServiceFacade() {
        if(fileServiceFacade == null){
            initialized.set(false);
            init();
        }
        return fileServiceFacade;
    }

    private void init(){
        if (initialized.compareAndSet(false, true)) {
            RegistryConfig reg;
            ApplicationConfig appCfg;
            try {
                appCfg = new ApplicationConfig();
                appCfg.setName(AppContextConfig.getAppName());*//**//*
                refer = new ReferenceConfig();
                refer.setInterface(FileServiceFacade.class);
                refer.setVersion(AppContextConfig.get("fileService.facade.version", "1.0"));
                refer.setTimeout(Integer.parseInt(AppContextConfig.get("fileService.facade.timeout","10000")));
                refer.setApplication(appCfg);
                refer.setId("fileServiceFacade");
                reg = new RegistryConfig();
                reg.setAddress(AppContextConfig.get("dubbo.registry.address"));
                //reg.setGroup(AppContextConfig.get("dubbo.group","dubbo"));
                reg.setId("configRegistry");
                refer.setRegistry(reg);
                fileServiceFacade = refer.get();
            } catch (Exception ex) {
                logger.warn("get-dubbo-configFacade-error:", ex);
            } finally {
                //appCfg = null;
                //config.destroy();
                //config = null;
                reg = null;
            }
            logger.info("init-dubbo-config-done,fileServiceFacade is " + (fileServiceFacade == null ? "null" : "not null"));
        }
    }
    private static final Logger logger = LoggerFactory.getLogger(FileServiceClient.class);

    public FileEntity saveFile(FileEntity file){
        file = getFileServiceFacade().saveFile(file);
        //上传后，数据不返回调用方，其他信息返回
        file.setData(null);
        return file;
    }
    public FileEntity saveFile(byte[] data, String filename, String contentType, Map<String,String> values){
        //return createFile(new ByteArrayInputStream(data),filename,contentType,values);
        FileEntity file = new FileEntity();
        file.setMd5(MD5Util.MD5Short(data));
        file.setContentType(contentType);
        file.setData(data);
        file.setFileName(filename);
        if(values!=null && values.containsKey("accessRule") && values.get("accessRule")!=null) {
            file.setAccessRule(values.get("accessRule"));
        }else {
            file.setAccessRule("*");
        }
        file.setOwner(AppContextConfig.getAppName());
        file = getFileServiceFacade().saveFile(file);
        //上传后，数据不返回调用方，其他信息返回
        file.setData(null);
        return file;
    }

    *//**
     *
     * @param fileName
     * @param width
     * @param height
     * @param format
     * @param type 1 像素缩放，2 按比例
     * @return
     * @throws IOException
     *//*
    public byte[] thumbnail(String fileName,int width,int height,String format,String quality,int type) throws IOException {
        *//*if (initialized.compareAndSet(false, true)) {
            init();
        }*//*
        return getFileServiceFacade().thumbnail(fileName, width, height, format, quality, type);
    }

    public FileEntity findFile(String fileName)throws IOException{
        FileEntity ff = new FileEntity();
        ff.setFileName(fileName);
        return getFileServiceFacade().findFile(ff);
    }

    public void deleteFile(String fileName){
        gridFsTemplate.delete(Query.query(GridFsCriteria.whereFilename().is(fileName)));
    }

    public List<FileEntity> findFilesByAlbum(String albumId, int page, int limit)throws Exception{
        //return gridFsTemplate.find(Query.query(GridFsCriteria.whereMetaData("albumId").is(albumId)).skip(page * limit).limit(limit));
        throw new Exception("not valiable");
    }

    public List<FileEntity> findFilesByUser(String userId,int page,int limit)throws Exception{
        //return gridFsTemplate.find(Query.query(GridFsCriteria.whereMetaData("userId").is(userId)).skip(page*limit).limit(limit));
        return null;
    }

    public void dump(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer=new byte[1024];
        int length=-1;
        while ((length=inputStream.read(buffer))>-1){
            outputStream.write(buffer, 0, length);
        }
    }
*/
}
