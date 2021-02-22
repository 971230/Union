package com.ztesoft.inf.service.util;

public interface IConst {
	
	String EXCHANGE_OUT_OBJ = "EXCHANGE_OUT_OBJ";
	String EXCHANGE_OUT_DOC = "EXCHANGE_OUT_DOC";
	String EXCHANGE_OUT_XML = "EXCHANGE_OUT_XML";
	String EXCHANGE_IN_XML = "EXCHANGE_IN_XML";
	String EXCHANGE_IN_DOC = "EXCHANGE_IN_DOC";
	String EXCHANGE_CLIENT_IP = "EXCHANGE_CLIENT_IP";
	String EXCHANGE_CLIENT_PWD = "EXCHANGE_CLIENT_PWD";
	String EXCHANGE_CLIENT_ID = "EXCHANGE_CLIENT_ID";
	String EXCHANGE_BIZ_CODE = "EXCHANGE_BIZ_CODE";
	String EXCHANGE_LOG = "EXCHANGE_LOG";
	String EXCHANGE_BIZ_PROVIDEINFO = "EXCHANGE_BIZ_PROVIDEINFO";
	// 查找变量的正则表达式，如${var1}这样的格式
	String FIND_QUERY_VARIABLE_REXP = "\\u0024\\u007B\\S+?}";

	// 查找SQL中需要直接替换的地方，如#{var1}，与${var1}的区别为，${var1}替换成问号，#{var1}直接替换成具体的字符
	String FIND_SQL_REPLACE_REXP = "#\\u007B\\S+?}";

	// 使用中括号“[]”括起来的为可选SQL语句，当可选语句里面的#{var1}或者${var1}为空时，则认为是不需要此查询条件，直接去掉
	String FIND_SQL_OPTIONAL_REXP = "\\[[\\S\\s]*?\\]";

	// 找出所有变量，包括#{var1}、${var1}
	String FIND_ALL_QUERY_VARIABLE_REXP = "\\u007B\\S+?}";
	/**
	 * http://www.w3.org/2001/XMLSchema
	 */
	String XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	String LOG_ID = "INF_LOG_ID";
}

