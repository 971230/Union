package com.ztesoft.net.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * droos face扫描对象
 * @作者 MoChunrun
 * @创建日期 2014-3-4 
 * @版本 V 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DroolsFact {

	String name();
	
	String code();
	
	String objType() default "fact";
	
}
