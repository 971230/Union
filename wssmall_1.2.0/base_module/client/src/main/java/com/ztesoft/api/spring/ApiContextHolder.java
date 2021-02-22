package com.ztesoft.api.spring;

import com.ztesoft.net.framework.cache.CacheLoaderInitBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-01-20 09:05
 * To change this template use File | Settings | File Templates.
 */
public class ApiContextHolder implements ApplicationContextAware,InitializingBean {
    private static ConfigurableApplicationContext applicationContext=null;

    /**
     * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
     */
    @Override
	public void setApplicationContext(ApplicationContext applicationContext) {
        ApiContextHolder.applicationContext =(ConfigurableApplicationContext)applicationContext;
       // DefaultServiceContext.getInstance(); add by wui装载一次就够了
    }

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        checkApplicationContext();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        return (T) applicationContext.getBeansOfType(clazz);
    }

    private static void checkApplicationContext() {
        if (applicationContext == null)
            throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义ApiContextHolder");
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        CacheLoaderInitBean.load();
    }
}
