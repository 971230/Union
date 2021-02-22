package com.zte.cbss.autoprocess.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlBeanUtil {
	
 	public static Logger logger = Logger.getLogger(XmlBeanUtil.class);

 	/**
	 * 根据对象名称和属性名称生成Xml格式字符串
	 * @param entity 对象
	 * @return  Xml格式字符串
	 */
	public static <T> String writeXml(T entity) {   
		String xml =null;
        try {   
            Document document = DocumentHelper.createDocument();   
            String rootname = entity.getClass().getSimpleName();//获得类名   
            Element root = document.addElement(rootname);//添加根节点   
            Field[] properties = entity.getClass().getDeclaredFields();//获得实体类的所有属性   
             
            for (int i = 0; i < properties.length; i++) {                      
                //反射get方法       
                Method meth = entity.getClass().getMethod("get"+ properties[i].getName().substring(0, 1).toUpperCase()+ properties[i].getName().substring(1));   
              //为二级节点添加属性，属性值为对应属性的值   
                Object obj = meth.invoke(entity);
                if(null != obj){
                	root.addElement(properties[i].getName()).setText(obj.toString());   
                } 
            }    
            xml =  document.asXML();
            return xml;
        } catch (Exception e) {   
        	logger.info("把对象转为XML文件字符失败： ", e);
         }
		return xml;   
    }  
	
	/**
	 *  根据对象名称和属性名称生成Xml格式字符串
	 * @param entity 对象
	 * @param rootName xml root节点名称
	 * @return  Xml格式字符串
	 */
	public static <T> String writeXml(T entity,String rootName) {   
		String xml =null;
        try {   
            Document document = DocumentHelper.createDocument();   
            Element root = document.addElement(rootName);//添加根节点   
            Field[] properties = entity.getClass().getDeclaredFields();//获得实体类的所有属性   
             
            for (int i = 0; i < properties.length; i++) {                      
                //反射get方法       
                Method meth = entity.getClass().getMethod("get"+ properties[i].getName().substring(0, 1).toUpperCase()+ properties[i].getName().substring(1));   
              //为二级节点添加属性，属性值为对应属性的值   
                Object obj = meth.invoke(entity);
                if(null != obj){
                	root.addElement(properties[i].getName()).setText(obj.toString());   
                } 
            }    
            xml =  document.asXML();
            return xml;
        } catch (Exception e) {   
        	logger.info("把对象转为XML文件字符失败： ", e);
         }
		return xml;   
    }  
	
	@SuppressWarnings("unchecked")
	public static <T> T readXML(String xml, T t) {
		try {
			Document doc = DocumentHelper.parseText(xml);
			
			Element root = doc.getRootElement();// 获得根节点
 
 			Field[] properties = t.getClass().getDeclaredFields();// 获得实例的属性
			 
			// 实例的set方法
			Method setmeth = null;

			t = (T) t.getClass().newInstance();// 获得对象的新的实例

			for (int j = 0; j < properties.length; j++) {// 遍历所有孙子节点

				// 实例的set方法
				setmeth = t.getClass().getMethod(
						"set"+ properties[j].getName().substring(0, 1).toUpperCase()
							 + properties[j].getName().substring(1),properties[j].getType());
				// properties[j].getType()为set方法入口参数的参数类型(Class类型)
				setmeth.invoke(t, root.elementText(properties[j].getName()));// 将对应节点的值存入
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
}
