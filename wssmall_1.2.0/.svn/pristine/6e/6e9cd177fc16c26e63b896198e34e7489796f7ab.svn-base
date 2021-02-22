package zte.net.common.annontion.context.action;

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
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface ZteServiceAnnontion {

	String trace_name() default "";
	
	
	String trace_id() default "";
	
	
	String version() default "";
	
	

	String desc() default "";

}
