package com.forte.runtime;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder
  implements ApplicationContextAware
{
  private static ApplicationContext applicationContext;

  public void setApplicationContext(ApplicationContext applicationContext)
    throws BeansException
  {
    SpringContextHolder.applicationContext = applicationContext;
  }

  public static Object getBean(String beanId) {
    return applicationContext.getBean(beanId);
  }

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }
}
