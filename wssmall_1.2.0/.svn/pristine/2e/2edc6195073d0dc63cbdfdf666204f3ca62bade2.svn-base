package com.ztesoft.rule.core.module.fact;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 注解：用于Fact类，标识Fact
 * @author easonwu 
 * @creation Dec 16, 2013
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE})
public @interface FactTag {
	String obj_code() default "" ;//对象名称(e文)
	String class_name() default "" ;//类中文名
	String ext_pack() default "" ;//此类import的pack列表
	String remark() default "" ;//类的备注说明，描述类干啥用途
}
