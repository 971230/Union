package com.ztesoft.net.sqls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * in语句SQL构造器，返回的SQL为" and 列名 in (参数) "
 * 如果传入的枚举值数量大于1000，会自动加上or作为连接多个in语句
 * “ and （列名 in (参数第一部分) or 列名 in （参数第二部分）···）”
 * @author zhaoc
 *
 */
public class SqlInBuilder extends SqlBuilder {
	public SqlInBuilder(){
		super();
	}

	public SqlInBuilder(String col_name, Object parm) {
		super(col_name, parm);
	}
	
	public SqlInBuilder(String col_name, Object parm,boolean isAnd) {
		super(col_name, parm,isAnd);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getSql() throws Exception {
		if(StringUtils.isBlank(this.col_name) || this.parm==null)
			return "";
		
		StringBuilder builder = new StringBuilder();
		
		if(this.parm instanceof List){
			//如果是list
			List list = (List)this.parm;
			List<String> idList = new ArrayList<String>();
			
			for(Iterator iterator = list.iterator();iterator.hasNext();){
				String s = String.valueOf(iterator.next());
				idList.add(s);
			}
			
			String inSql = SqlUtil.getSqlInStr(this.col_name, idList, false,isAnd);
			
			builder.append(inSql);
		}else if(this.parm instanceof String){
			//字符串
			String str = String.valueOf(this.parm);
			
			if(StringUtils.isBlank(str))
				return "";
			
			//字符串以逗号分隔
			String[] arr = str.split(",");
			List<String> idList = Arrays.asList(arr);
			
			String inSql = SqlUtil.getSqlInStr(this.col_name, idList, false,isAnd);
			
			builder.append(inSql);
		}else if(this.parm.getClass().isArray()){
			//数组
			Object[] arr = (Object[])this.parm;
			List<String> idList = new ArrayList<String>();
			
			for(int i=0;i<arr.length;i++){
				String s = String.valueOf(arr[i]);
				idList.add(s);
			}
			
			String inSql = SqlUtil.getSqlInStr(this.col_name, idList, false,isAnd);
			
			builder.append(inSql);
		}else{
			return "";
		}
		
		return builder.toString();
	}
}
