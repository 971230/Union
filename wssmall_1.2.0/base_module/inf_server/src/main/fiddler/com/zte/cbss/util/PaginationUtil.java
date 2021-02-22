package com.zte.cbss.util;

public class PaginationUtil {
	
	public static String translateCountSQL(String sql) {
		return " select count(1) from (" + sql + ") temp ";
	}
	
	public static String translatePaginationSQL(String sql, int offset, int length) {
		if (offset < 0) {
			offset = 0;
		}
		String size = "";
		if(length != 0) {//pagesize为0时，取消分布
			size = " limit " + offset + "," + length;
		}
		return sql + size;
	}
	
}
