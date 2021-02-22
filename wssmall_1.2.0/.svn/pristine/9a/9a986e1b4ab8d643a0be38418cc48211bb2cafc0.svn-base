package zte.net.common.annontion.context.request;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 
 * @author
 * @version 1.0
 * @wu.i
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestFieldAnnontion {

	String desc() default "";
	
	String dname() default "";

	String xname() default "";

	String qname() default "";
	
	String need_xshow() default "yes";
	
	String need_insert() default "yes";
	
	String need_query() default "yes";
	
	String service_name() default ""; //依赖服务名称
	
	String asy_store() default "no";
	
	String asy_query() default "no";
	
	int field_Order()default 0;
	
}
