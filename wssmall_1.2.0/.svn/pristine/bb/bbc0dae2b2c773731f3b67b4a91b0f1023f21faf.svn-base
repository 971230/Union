package com.ztesoft.net.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作用：注解生成代码帮助文档Javadoc描述内容
 * @author sguo
 * @date 2014-01-07
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ZteSoftCommentAnnotation {

	/**
	 * <b>类型</b>：<br/>
	 * class:类注解;<br/>
	 * construct:类构造函数注解；<br/>
	 * param:类组员注解；<br/>
	 * method:类方法注解；<br/>
	 *  
	 */
	String type();
	
	/**
	 * <b>简介</b>
	 */
	String summary();
	
	/**
	 * <b>描述</b>
	 */
	String desc();
	
	
	/**
	 * <b>是否对外开放</b>
	 */
	boolean isOpen() default true;
	
	String isvalid() default "Y";
	
	String moduleName() default "";
	
}
