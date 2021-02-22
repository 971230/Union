package com.ztesoft.net.mall.core.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class IJSONUtil {
	
	/**
	 * bean转换为json
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-23 
	 * @param src
	 * @return
	 */
	public static <T> String beanToJson(T src){
		SerializerFeature[] features = { SerializerFeature.WriteMapNullValue, // 输出空置字段
			SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
			SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
			SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
			SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
			SerializerFeature.WriteClassName,
		};
		
		return JSON.toJSONString(src,features);
	}
	
	/**
	 * bean转换为json
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-23 
	 * @param src
	 * @return
	 */
	public static <T> String beanToJsonFilterNull(T src){
		SerializerFeature[] features = {
			SerializerFeature.WriteClassName,
		};
		return JSON.toJSONString(src,features);
	}
	
	
	
//	public static <T> String beanToXml(T src){
//		String xml = com.ztesoft.common.util.XmlUtils.zteRequestToXml(s);
//	}
//	
	/**
	 * bean转换为json
	 * @作者 wu.i 
	 * @创建日期 2013-9-23 
	 * @param src
	 * @return
	 */
	public static <T> String beanToJsonNCls(T src){
		SerializerFeature[] features = { SerializerFeature.WriteMapNullValue, // 输出空置字段
			SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
			SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
			SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
			SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
		};
		return JSON.toJSONString(src,features);
	}
	
	/**
	 * json转为Bean
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-23 
	 * @param <T>
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T jsonToBean(String json,Class clazz){
		return (T) JSON.parseObject(json, clazz);
	}
	
	
	
	/**
	 * json 转为list
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-23 
	 * @param <T>
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> jsonToList(String json,Class clazz){
		return JSON.parseArray(json, clazz);
	}
	
}
