package com.ztesoft.parameters;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Reason.Yea 
 * @version 创建时间：May 21, 2013
 */
@Retention(RUNTIME) @Target({FIELD, METHOD})
public @interface ListType {
	String name() default "##default";
}
