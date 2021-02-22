package com.ztesoft.net.sqls;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

public class Sqls {
	protected Map<String , String> sqls = new HashMap<String , String>();
	public ICacheUtil cacheUtil = new CacheUtil();
	/**
	 * wui
	 * 获取sql
	 * @param getName
	 * @param inst
	 * @return
	 */
	public String getSql(String getName,Sqls cInst){
		try {
			Class clazz = cInst.getClass();
			Method method = clazz.getMethod(getName);
			Object sqlObj = method.invoke(cInst);
			return String.valueOf(sqlObj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

	public static String getPropSql(String getName,Sqls currInst,Sqls cInst){
		String sql ="";
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(currInst.getClass());
			// 给 JavaBean 对象的属性赋值
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if(("get"+propertyName).equals(getName))
				{
					sql = invokeSqlMethod(currInst, getName);
					break;
				}
			}
			if(StringUtil.isEmpty(sql)){
				sql = invokeSqlMethod(cInst, getName);
			}
			return sql;
		} catch (IntrospectionException e1) {
			e1.printStackTrace();
		}
		return "";
	}
	
	
	public String getSql(String name) {

		return sqls.get(name);
	}
	
	public Sqls(){
		
	}
	
//	 public void setLocalMap(Map localMap){
//	BeanInfo beanInfo;
//	try {
//		beanInfo = Introspector.getBeanInfo(this.getClass());
//		// 给 JavaBean 对象的属性赋值
//		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//		for (int i = 0; i < propertyDescriptors.length; i++) {
//			PropertyDescriptor descriptor = propertyDescriptors[i];
//			String propertyName = descriptor.getName();
//			localMap.put("get"+propertyName, propertyName);
//		}
//	} catch (IntrospectionException e1) {
//		e1.printStackTrace();
//	} // 获取类属性
//}
	
	public static String invokeSqlMethod(Sqls inst,String getName){
		try {
			Class clazz = inst.getClass();
			Method method = clazz.getMethod(getName);
			Object sqlObj = method.invoke(inst);
			return String.valueOf(sqlObj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
}
