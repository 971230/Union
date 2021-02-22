package com.ztesoft.net.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * drools fact 属性注解
 * @作者 MoChunrun
 * @创建日期 2014-3-4 
 * @版本 V 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DroolsFactField {

	String name();
	
	String ele_type() default "";
	
	String stype_code() default "";
	
	
	String type() default "fact";
	
}
