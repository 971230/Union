package zte.net.common.annontion.context.request;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;

/**
 * 
 * 
 * @author
 * @version 1.0
 * @wu.i
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface RequestBeanAnnontion { //@interface


	String service_name() default "";

	String primary_keys() default "";
	
	String depency_keys() default "";
	
	String table_name() default "";
	
	String table_type() default "";			//业务对象对应的表是否有多条记录 List 表示多条记录

	String service_desc() default "";
	
	String store_type() default "db";
	
	String cache_store() default "no";

	String query_store() default "yes";
	
	String  table_archive() default "no"; //历史归档
	
	String  source_from() default "ECS,SHP,JS";
	
	String cache_keys() default "";

}
