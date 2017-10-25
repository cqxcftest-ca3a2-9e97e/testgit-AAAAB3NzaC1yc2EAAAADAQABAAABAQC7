package com.forte.runtime;

import com.forte.runtime.spring.AppContextConfig;
import com.forte.runtime.util.ImgUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @deprecated
 * Created by libin on 5/14/15.
 */
@Component
public class FileService {
    @Autowired
    private GridFsTemplate gridFsTemplate;
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);
    /***
     * @deprecated
     * @see com.forte.hlw.imagefs.facade.FileServiceFacade
     * */
    public GridFSFile createFile(InputStream inputStream, String filename, String contentType, Map<String,String> values){

        DBObject dbObject=new BasicDBObject();
        if(null!=values&&values.size()>0){
            for(String key:values.keySet()){
                dbObject.put(key,values.get(key));
            }
        }
        dbObject.put("owner",AppContextConfig.getAppName());
        return gridFsTemplate.store(inputStream,filename,contentType,dbObject);
    }
    /***
     * @deprecated
     * @see com.forte.hlw.imagefs.facade.FileServiceFacade
     * */
    public GridFSFile saveImage(byte[] data, String filename, String format,String quality, Map<String,String> values){
        /*DBObject dbObject=new BasicDBObject();
        if(null!=values&&values.size()>0){
            for(String key:values.keySet()){
                dbObject.put(key,values.get(key));
            }
        }
        dbObject.put("owner",AppContextConfig.getAppName());

        IMOperation op = new IMOperation();
        op.addImage();
        op.addRawArgs("-format",format);
        op.addRawArgs("-quality",quality);
        op.addImage("-");
        ConvertCmd convert = new ConvertCmd(true);
        if(AppContextConfig.get("os.type","linux").equals("windows")) {
            convert.setSearchPath(AppContextConfig.get("graphicsMagic.path", "C:\\Program Files\\GraphicsMagick-1.3.24-Q16"));
        }
        BufferedImage img = null;
        Stream2BufferedImage buff = new Stream2BufferedImage();
        convert.setOutputConsumer(buff);
        BufferedImage source = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ByteArrayOutputStream bos =new ByteArrayOutputStream();
        try {
            source = ImageIO.read(bis);
            convert.run(op,source);
            img = buff.getImage();
            if(img!=null) {
                ImageIO.write(img, format, bos);
            }
        }catch (Exception ex){
            logger.error("create-img-failed:",ex);
        }
        byte[] trans = bos.toByteArray();
        return gridFsTemplate.store(new ByteArrayInputStream(trans),filename,format,dbObject);*/
        return null;
    }
    /***
     * @deprecated
     * @see com.forte.hlw.imagefs.facade.FileServiceFacade
     * */
    public GridFSFile createFile(byte[] data, String filename, String contentType, Map<String,String> values){
        return createFile(new ByteArrayInputStream(data),filename,contentType,values);
    }

    /**
     *
     * @param fileName
     * @param width
     * @param height
     * @param format
     * @param type 1 像素缩放，2 按比例
     * @return
     * @throws IOException
     */
    public byte[] thumbnail(String fileName,int width,int height,String format,String quality,int type) throws IOException {
        long start = System.currentTimeMillis();
        GridFSDBFile gridFSDBFile = findFile(fileName);
        if(null==gridFSDBFile) return null;

        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        gridFSDBFile.writeTo(outputStream);
        String contentType = "webp";
        if(format!=null && format.length()>0){
            contentType = format;
        }
        //BufferedImage bufferedImage=ImgUtil.thumb(ImgUtil.fromByteArray(outputStream.toByteArray()), width, height, true);
        BufferedImage bufferedImage= null;
        try{
            long s1 = System.currentTimeMillis();
            bufferedImage = ImgUtil.thumbnail(ImgUtil.fromByteArray(outputStream.toByteArray()), width, height,contentType,quality,type);
            logger.info("thumbnail-img：file:{}，w:{},h:{},format:{},quality:{},thumbnail-time-cost:{}s",
                    fileName,width,height,contentType,quality,(System.currentTimeMillis()-s1)/1000.0);
        }catch (Exception ex){
            logger.info("thumbnail-img-error",ex);
        }
        ByteArrayOutputStream bos =new ByteArrayOutputStream();

        if(bufferedImage!=null) {
            ImageIO.write(bufferedImage, contentType, bos);
        }
        byte[] data = bos.toByteArray();
        if(data.length>0) {//生成成功，保存
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            String newFileName = fileName + "_" + width + "_" + height+"_"+format;
            //"image/jpeg"
            gridFsTemplate.store(inputStream, newFileName, gridFSDBFile.getMetaData());
            logger.info("thumbnail-img-time-cost:{}s,file={}",(System.currentTimeMillis()-start)/1000.0,newFileName);
        }else {//返回原图
            if(AppContextConfig.get("thumb.fail.default","1").equals("1")) {
                logger.warn("thumb-file-failed:{}-{}-{}",fileName,width,height);
                data = outputStream.toByteArray();
                logger.info("thumbnail-img-time-cost:{}s,source-file={}",(System.currentTimeMillis()-start)/1000.0,fileName);
            }
        }
        return data;
    }

    /***
     * @deprecated
     * @param fileName
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    public byte[] thumb(String fileName,int width,int height) throws IOException {
        GridFSDBFile gridFSDBFile = findFile(fileName);
        if(null==gridFSDBFile) return null;

        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        gridFSDBFile.writeTo(outputStream);

        BufferedImage bufferedImage=ImgUtil.thumb(ImgUtil.fromByteArray(outputStream.toByteArray()), width, height, true);
        ByteArrayOutputStream bos =new ByteArrayOutputStream();

        String contentType = gridFSDBFile.getContentType();
        if(contentType.startsWith("image")){
            contentType = contentType.substring("image/".length());
        }else if (contentType.startsWith("application")){
            contentType = "png";
        }
        if(contentType.equals("jpeg")){
            contentType = "jpg";
        }
        if(bufferedImage!=null) {
            ImageIO.write(bufferedImage, contentType, bos);
        }
        byte[] data = bos.toByteArray();
        if(data.length>0) {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            String newFileName = fileName + "_" + width + "_" + height;
            //"image/jpeg"
            gridFsTemplate.store(inputStream, newFileName, gridFSDBFile.getMetaData());
        }else {
            if(AppContextConfig.get("thumb.fail.default","1").equals("1")) {
                logger.warn("thumb-file-failed:{}-{}-{}",fileName,width,height);
                data = outputStream.toByteArray();
            }
        }
        return data;
    }

    public void listFile(){
        List<GridFSDBFile> gridFiles=gridFsTemplate.find(new Query().limit(10));
        for(GridFSDBFile file:gridFiles){
            System.out.println(file.getMD5());
        }
    }

    /**
     * @deprecated
     * @param fileName
     * @return
     */
    public GridFSDBFile findFile(String fileName){
        return gridFsTemplate.findOne(Query.query(GridFsCriteria.whereFilename().is(fileName)));
    }
    
    public void deleteFile(String fileName){
         gridFsTemplate.delete(Query.query(GridFsCriteria.whereFilename().is(fileName)));
    }   

    public List<GridFSDBFile> findFilesByAlbum(String albumId,int page,int limit){
        return gridFsTemplate.find(Query.query(GridFsCriteria.whereMetaData("albumId").is(albumId)).skip(page * limit).limit(limit));
    }

    public List<GridFSDBFile> findFilesByUser(String userId,int page,int limit){
        return gridFsTemplate.find(Query.query(GridFsCriteria.whereMetaData("userId").is(userId)).skip(page*limit).limit(limit));
    }

    public void dump(InputStream inputStream,OutputStream outputStream) throws IOException {
        byte[] buffer=new byte[1024];
        int length=-1;
        while ((length=inputStream.read(buffer))>-1){
            outputStream.write(buffer, 0, length);
        }
    }

    public static void main(String[] args){
        /*GenericApplicationContext applicationContext=
                new AnnotationConfigApplicationContext(MongoConfiguration.class);
        FileService fileService = applicationContext.getBean(FileService.class);
        List<GridFSDBFile> gridFiles=fileService.gridFsTemplate.find(null);
        for(GridFSDBFile file:gridFiles){
            System.out.println(file.getMD5());
        }*/

        System.out.println(System.getProperty("java.library.path"));
    }


}
