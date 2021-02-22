package com.ztesoft.framework.sqls;
import java.lang.reflect.Field;
import java.util.Map;


public class SqlUtil {
	
	private static final int PRIVATE = 2 ;
	
	public static void initSqls(Class clazz , Object instance , Map sqls){
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (Field f : fields) {
				f.setAccessible(true) ;
				if(null == f.get(instance) || "".equals( ((String) f.get(instance)).trim() ))
					throw new RuntimeException("类【"+clazz.getName()+"】的属性【"+f.getName()+"】值不能为空！" +
							" 或许【"+f.getName()+"】对应的SQL使用到的对象与当前对象有循环调用，请把使用到的对象方法抽取到DatabaseFunction类中，避免循环引用!") ;
				
				sqls.put(f.getName(), f.get(instance));
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e) ;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e) ;
		}
	}
}
