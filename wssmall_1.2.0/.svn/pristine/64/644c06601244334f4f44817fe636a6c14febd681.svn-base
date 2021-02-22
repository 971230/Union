package com.ztesoft.common.query;

public class DatabaseUtil {

	public static String doPreFilterWithoutFilterSQL(String sql,int startIndex,int endIndex) {		
		  sql = "select * from (select my_table.*,rownum as my_rownum from( " + sql + " ) my_table where rownum< "
				+ endIndex + ") where my_rownum>= " + startIndex;
		return sql;
	}
	
	public static String doPreFilterWithoutFilterCountSQL(String sql) {		
		  sql = "select count(*) as amount from ( " + sql + " )  tab_1 ";		
		return sql;
	}
	
	public static int getEndIndex(String pageIndex,String pageSize) {
		int nPageIndex = (int)Double.parseDouble(pageIndex);
		int nPageSize = (int)Double.parseDouble(pageSize);
		
	
		
		if(nPageIndex - 1 <0) {
			nPageIndex =0;
		}else {
			nPageIndex = nPageIndex - 1;
		}
		return (nPageIndex*nPageSize) + nPageSize + 1;
	}
	
	
	public static int getPageIndex(String pageIndex,String pageSize) {
		int nPageIndex = (int)Double.parseDouble(pageIndex);
		int nPageSize = (int)Double.parseDouble(pageSize);
		
		if(nPageIndex - 1 <0) {
			nPageIndex =0;
		}else {
			nPageIndex = nPageIndex - 1;
		}
		return nPageIndex*nPageSize + 1;
		
	}
}
