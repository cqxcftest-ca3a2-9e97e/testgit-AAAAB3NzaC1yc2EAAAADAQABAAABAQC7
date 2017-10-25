package com.forte.runtime.util;

import com.forte.runtime.exception.XmlParserException;
import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;

/***
 * @desc
 * @author wangbin
 * @date 2015/8/26
 */
public class XmlUtil {
    private static Logger logger = LoggerFactory.getLogger(XmlUtil.class);
    private static XStream xstream = new XStream();
    private static XStream xstream2Xml = new XStream();
    private static Cache xstream2ObjMap = new ConcurrentMapCache("xstream2ObjMap",false);

    private static synchronized XStream creat2ObjXStream(Class<?> clazz)
    {
        if (xstream2ObjMap.get(clazz) != null) {
            return (XStream)xstream2ObjMap.get(clazz);
        }
        XStream xstream2Obj = new XStream();
        xstream2Obj.processAnnotations(clazz);
        xstream2ObjMap.put(clazz, xstream2Obj);
        return xstream2Obj;
    }

    public static <T> T toObject(String xml)
            throws XmlParserException
    {
        if (xml == null) {
            logger.warn("xml is null!");
            throw new XmlParserException("xml content is null");
        }
        Object object;
        try
        {
            object = xstream.fromXML(xml);
        } catch (RuntimeException e) {
            logger.error("xml:" + xml, e);
            throw new XmlParserException("xml:" + xml, e);
        }
        return (T)object;
    }

    public static String toXml(Object object)
            throws XmlParserException
    {
        if (object == null) {
            logger.warn("object is null!");
            return null;
        }
        String xml;
        try
        {
            xml = xstream.toXML(object);
        } catch (RuntimeException e) {
            logger.error("object:" + object.getClass(), e);
            throw new XmlParserException("object:" + object.getClass(), e);
        }
        return xml;
    }

    public static String toXmlByAnnotation(Object object)
            throws XmlParserException
    {
        if (object == null) {
            logger.warn("object is null!");
            throw new XmlParserException("object is null");
        }
        return xstream2Xml.toXML(object);
    }

    public static <T> T toObjectByAnnotation(String xml, Class beanClass)
            throws XmlParserException
    {
        if (beanClass == null) {
            logger.warn("beanClasses is null!");
            return null;
        }
        if (xml == null) {
            logger.warn("xml is null!");
            return null;
        }
        XStream xstream2Obj = (XStream)xstream2ObjMap.get(beanClass);

        if (xstream2Obj == null)
            xstream2Obj = creat2ObjXStream(beanClass);
        Object object;
        try
        {
            object = xstream2Obj.fromXML(xml);
        } catch (RuntimeException e) {
            logger.error("xml:" + xml, e);
            throw new XmlParserException("xml:" + xml, e);
        }

        return (T)object;
    }

    static{
        xstream2Xml.autodetectAnnotations(true);
    }
}
