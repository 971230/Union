package com.ztesoft.net.framework.context.spring;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import com.ztesoft.net.framework.cache.CacheLoaderInitBean;
import com.ztesoft.zsmart.hound.client.DemoApplicationContext;
import com.ztesoft.zsmart.hound.client.HoundAPI;

/**
 * 以静态变量保存Spring ApplicationContext,可在任意代码中取出ApplicaitonContext.
 * 
 * @author calvin
 */
public class SpringContextHolder implements ApplicationContextAware,InitializingBean {

	private static ConfigurableApplicationContext  applicationContext;

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextHolder.applicationContext =(ConfigurableApplicationContext)applicationContext;
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
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
	}
	
	public static boolean isConfig(){
		if (applicationContext == null)return false;
		return true;
	}
	
	public static  void loadbean( ) {
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(
				(BeanDefinitionRegistry) applicationContext.getBeanFactory() );
		beanDefinitionReader.setResourceLoader(applicationContext);
		beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(
				applicationContext));
		try {
			beanDefinitionReader.loadBeanDefinitions(applicationContext.getResources("classpath:newspring/newApplicationContext.xml"));
			addBeanPostProcessor();
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void addBeanPostProcessor() {
        String[] postProcessorNames = applicationContext.getBeanFactory().getBeanNamesForType(
            BeanPostProcessor.class, true, false);
        for (String postProcessorName : postProcessorNames) {
            applicationContext.getBeanFactory().addBeanPostProcessor(
                (BeanPostProcessor) applicationContext.getBean(postProcessorName));
        }
    }



	@Override
	public void afterPropertiesSet() throws Exception {
		CacheLoaderInitBean.load();
		setupHound();
		}

		// hound应用初始化
		private void setupHound() throws UnknownHostException {
			String host=InetAddress.getLocalHost().getHostAddress();
			String port=System.getProperty("dubbo.protocol.port");
			DemoApplicationContext demoApplicationContext = new DemoApplicationContext();
			demoApplicationContext.setAppName("wssmall-"+host+":"+port);
			demoApplicationContext.setHostInfo(host);
			HoundAPI.setApplicationContext(demoApplicationContext);
		}         
}
