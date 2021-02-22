package com.ztesoft.net.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作用:输出要作为参数的类，其成员变量说明
 * @author sguo
 * @date 2014-01-13
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ZteSoftCommentAnnotationParam {
	/**
	 * 名称
	 * @return
	 */
	String name();
	/**
	 * 类型
	 * @return
	 */
	String type();
	
	/**
	 * 是否必须：Y:必须；N:非必须
	 * @return
	 */
	String isNecessary();
	
	/**
	 * 描述
	 * @return
	 */
	String desc();
	
	boolean hasChild() default false;
}
