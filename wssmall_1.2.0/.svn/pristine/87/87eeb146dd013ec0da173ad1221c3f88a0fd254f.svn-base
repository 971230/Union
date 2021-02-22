package com.ztesoft.rule.core.module.fact;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 注解：用于Fact类的属性信息
 * @author easonwu 
 * @creation Dec 16, 2013
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD})
public @interface FactAttrTag {
	String attr_name() default "" ;//对象名称(e文)
	String remark() default "" ;//类的备注说明，描述类干啥用途
}
