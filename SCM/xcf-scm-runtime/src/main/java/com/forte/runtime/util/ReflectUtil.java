package com.forte.runtime.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;

/**
 * 
 * <p>反射类</p>
 * Created by liaosifa on 2015年11月3日
 */
public class ReflectUtil
{
  private static final Logger logger = LoggerFactory.getLogger("ReflectUtil");

  public static void setFieldValue(Object target, String fname, Class<?> ftype, Object fvalue)
  {
    if ((target == null) || (fname == null) || ("".equals(fname)) || ((fvalue != null) && (!ftype.isAssignableFrom(fvalue.getClass()))))
    {
      return;
    }
    Class clazz = target.getClass();
    try {
      Method method = clazz.getDeclaredMethod("set" + Character.toUpperCase(fname.charAt(0)) + fname.substring(1), new Class[] { ftype });

      if (!Modifier.isPublic(method.getModifiers())) {
        method.setAccessible(true);
      }
      method.invoke(target, new Object[] { fvalue });
    }
    catch (Exception e) {
      logger.error(e.getMessage(), e);
      try {
        Field field = clazz.getDeclaredField(fname);
        if (!Modifier.isPublic(field.getModifiers())) {
          field.setAccessible(true);
        }
        field.set(target, fvalue);
      } catch (Exception fe) {
        logger.error(fe.getMessage(), fe);
      }
    }
  }

  public static <T> Class<T> getSuperClassGenericType(Class clazz)
  {
    return getSuperClassGenricType(clazz, 0);
  }

  public static Class getSuperClassGenricType(Class clazz, int index)
  {
    Type genType = clazz.getGenericSuperclass();

    if (!(genType instanceof ParameterizedType)) {
      logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
      return Object.class;
    }

    Type[] params = ((ParameterizedType)genType).getActualTypeArguments();

    if ((index >= params.length) || (index < 0)) {
      logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);

      return Object.class;
    }
    if (!(params[index] instanceof Class)) {
      logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");

      return Object.class;
    }

    return (Class)params[index];
  }

  public static Field getFieldByFieldName(Object obj, String fieldName)
  {
    for (Class superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass())
      try
      {
        return superClass.getDeclaredField(fieldName);
      }
      catch (NoSuchFieldException e) {
      }
    return null;
  }

  public static Object getValueByFieldName(Object obj, String fieldName)
          throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException
  {
    Field field = getFieldByFieldName(obj, fieldName);
    Object value = null;
    if (field != null) {
      if (field.isAccessible()) {
        value = field.get(obj);
      } else {
        field.setAccessible(true);
        value = field.get(obj);
        field.setAccessible(false);
      }
    }
    return value;
  }

  public static void setValueByFieldName(Object obj, String fieldName, Object value)
          throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException
  {
    Field field = obj.getClass().getDeclaredField(fieldName);
    if (field.isAccessible()) {
      field.set(obj, value);
    } else {
      field.setAccessible(true);
      field.set(obj, value);
      field.setAccessible(false);
    }
  }
}