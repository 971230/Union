package com.ztesoft.inf.framework.commons;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class ProxyCreator {

	public static <T> T newInstance(Class<? extends MethodInterceptor> proxyClz,
		Class<T> targetClz) {
		MethodInterceptor proxy;
		try {
			proxy = proxyClz.newInstance();
			return newInstance(proxy, targetClz);
		} catch (Exception e) {
			throw new RuntimeException("生成代理对象失败!", e);
		}
	}
	public static <T> T newInstance(MethodInterceptor proxy, Class<T> targetClz) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetClz);
		enhancer.setCallback(proxy);		
		return (T) enhancer.create();
	}
}
