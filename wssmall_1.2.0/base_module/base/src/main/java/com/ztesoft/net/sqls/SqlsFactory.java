package com.ztesoft.net.sqls;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class SqlsFactory {

	
	private static ConcurrentHashMap<String, Sqls> slqsMap =  new ConcurrentHashMap<String, Sqls>();

	public static Sqls getSqls_Order() {
		Sqls sqls = null;
		try{
			Method method = Class.forName("com.ztesoft.net.sqls.Sqls_Order").getMethod("getInstance");
			sqls = (Sqls)method.invoke(null);
		}catch(Exception e){
			throw new RuntimeException("==========>>实例化失败：Sqls_Order");
		}
		return sqls;
	}

	public static Sqls getSqls_Goods() {
		Sqls sqls = null;
		try{
			Method method = Class.forName("com.ztesoft.net.sqls.Sqls_Goods").getMethod("getInstance");
			sqls = (Sqls)method.invoke(null);
		}catch(Exception e){
			throw new RuntimeException("==========>>实例化失败：Sqls_Goods");
		}
		return sqls;
	}
	
	public static Sqls getSqls_Member() {
		Sqls sqls = null;
		try{
			//Method method = Class.forName("com.ztesoft.net.sqls.Sqls_Member").getMethod("getInstance");
			//sqls = (Sqls)method.invoke(null);
			Class clazz = null;
			try{
				clazz = Class.forName("com.ztesoft.net.sqls.Sqls_local_Member");
			}catch(Exception ex){
				clazz = Class.forName("com.ztesoft.net.sqls.Sqls_Member");
			}
			sqls = (Sqls) clazz.newInstance();
		}catch(Exception e){
			throw new RuntimeException("==========>>实例化失败：Sqls_Member");
		}
		return sqls;
	}
	
	public static Sqls getSqls_Sys() {
		Sqls sqls = null;
		try{
			Method method = Class.forName("com.ztesoft.net.sqls.Sqls_Sys").getMethod("getInstance");
			sqls = (Sqls)method.invoke(null);
		}catch(Exception e){
			throw new RuntimeException("==========>>实例化失败：Sqls_Sys");
		}
		return sqls;
	}
	
	public static Sqls getSqls_Task() {
		
		Sqls sqls = null;
		try{
			Method method = Class.forName("com.ztesoft.net.sqls.Sqls_Task").getMethod("getInstance");
			sqls = (Sqls)method.invoke(null);
		}catch(Exception e){
			throw new RuntimeException("==========>>实例化失败：Sqls_Task");
		}
		return sqls;
	}
	
	public static Sqls getSqls_Payment() {
		Sqls sqls = null;
		try{
			Method method = Class.forName("com.ztesoft.net.sqls.Sqls_Payment").getMethod("getInstance");
			sqls = (Sqls)method.invoke(null);
		}catch(Exception e){
			throw new RuntimeException("==========>>实例化失败：Sqls_Payment");
		}
		return sqls;
	}
	
	public static Sqls getSqls_Print() {
		Sqls sqls = null;
		try{
			Method method = Class.forName("com.ztesoft.net.sqls.Sqls_Print").getMethod("getInstance");
			sqls = (Sqls)method.invoke(null);
		}catch(Exception e){
			throw new RuntimeException("==========>>实例化失败：Sqls_Print");
		}
		return sqls;
	}
	
	public static Sqls getSqls_Partner() {
		Sqls sqls = null;
		try{
			Method method = Class.forName("com.ztesoft.net.sqls.Sqls_Partner").getMethod("getInstance");
			sqls = (Sqls)method.invoke(null);
		}catch(Exception e){
			throw new RuntimeException("==========>>实例化失败：Sqls_Partner");
		}
		return sqls;
	}
	
	public static Sqls getSqls_Supplier() {
		Sqls sqls = null;
		try{
			Method method = Class.forName("com.ztesoft.net.sqls.Sqls_Supplier").getMethod("getInstance");
			sqls = (Sqls)method.invoke(null);
		}catch(Exception e){
			throw new RuntimeException("==========>>实例化失败：Sqls_Supplier");
		}
		return sqls;
	}
	

	public static Sqls getSqls_Flow() {
		Sqls sqls = null;
		try{
			Method method = Class.forName("com.ztesoft.net.sqls.Sqls_Flow").getMethod("getInstance");
			sqls = (Sqls)method.invoke(null);
		}catch(Exception e){
			throw new RuntimeException("==========>>实例化失败：Sqls_Flow");
		}
		return sqls;
	}
	
	public static Sqls getSqls_Base() {
		Sqls sqls = null;
		try{
			Method method = Class.forName("com.ztesoft.net.sqls.Sqls_Base").getMethod("getInstance");
			sqls = (Sqls)method.invoke(null);
		}catch(Exception e){
			throw new RuntimeException("==========>>实例化失败：Sqls_Base");
		}
		return sqls;
	}
	
	public static Sqls getSqls_Rule() {
		Sqls sqls = null;
		try{
			Method method = Class.forName("com.ztesoft.net.sqls.Sqls_Rule").getMethod("getInstance");
			sqls = (Sqls)method.invoke(null);
		}catch(Exception e){
			throw new RuntimeException("==========>>实例化失败：Sqls_Rule");
		}
		return sqls;
	}
	
	public static Sqls getSqls_Commiss() {
		Sqls sqls = null;
		try{
			Method method = Class.forName("com.ztesoft.net.sqls.Sqls_Commiss").getMethod("getInstance");
			sqls = (Sqls)method.invoke(null);
		}catch(Exception e){
			throw new RuntimeException("==========>>实例化失败：Sqls_Commiss");
		}
		return sqls;
	}
	
	public static Sqls getSqls_Info() {
		Sqls sqls = null;
		try{
			Method method = Class.forName("com.ztesoft.net.sqls.Sqls_Info").getMethod("getInstance");
			sqls = (Sqls)method.invoke(null);
		}catch(Exception e){
			throw new RuntimeException("==========>>实例化失败：Sqls_Info");
		}
		return sqls;
	}
	
	public static Sqls getDynaSQLS(String dynaClazz) {
		Sqls sqls = null;
		sqls = slqsMap.get(dynaClazz);
		if(  sqls == null ) {
			synchronized (SqlsFactory.class) {
				sqls = slqsMap.get(dynaClazz);
				if(  sqls == null ) {
					try{
						Class clazz = Thread.currentThread().getContextClassLoader().loadClass(dynaClazz);
						sqls = ((Sqls)clazz.newInstance()) ;
						slqsMap.put(dynaClazz, sqls);
					}catch(Exception e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				}
				
			}
			
		}
		sqls = slqsMap.get(dynaClazz);

		return sqls;
	}

	public static Sqls getSqls_Numeros() {
		Sqls sqls = null;
		try{
			Method method = Class.forName("com.ztesoft.net.sqls.Sqls_Numeros").getMethod("getInstance");
			sqls = (Sqls)method.invoke(null);
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("==========>>实例化失败：Sqls_Numeros");
		}
		return sqls;
	}
	
	public static Sqls getSqls_Producto() {
		Sqls sqls = null;
		try{
			Method method = Class.forName("com.ztesoft.net.sqls.Sqls_Productos").getMethod("getInstance");
			sqls = (Sqls)method.invoke(null);
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("==========>>实例化失败：Sqls_Numeros_Reglas");
		}
		return sqls;
	}
	
	
}
