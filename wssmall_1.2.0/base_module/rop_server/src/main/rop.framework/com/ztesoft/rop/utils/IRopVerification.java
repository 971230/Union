package com.ztesoft.rop.utils;

import java.util.Map;

public interface IRopVerification{
	
	
//	1.验证接口
//	入参
//	String appKey
//	Map    参数集合
//
//	返回加密串
//	企业编码
//	企业名称
//	账户标识
	Map<String,String> validApp(String appKey , Map params) ;

	
}
