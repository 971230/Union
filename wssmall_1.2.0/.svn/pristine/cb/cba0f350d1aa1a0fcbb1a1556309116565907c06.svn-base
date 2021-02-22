package com.ztesoft.net.sqls;

import org.apache.commons.lang.StringUtils;

/**
 * like语句SQL构造器，返回的SQL为“ and 列名 like '%参数%'”
 * @author zhaoc
 *
 */
public class SqlLikeBuilder extends SqlBuilder {
	public SqlLikeBuilder(){
		super();
	}

	public SqlLikeBuilder(String col_name, Object parm) {
		super(col_name, parm);
	}
	
	public SqlLikeBuilder(String col_name, Object parm,boolean isAnd) {
		super(col_name, parm, isAnd);
	}

	@Override
	public String getSql() throws Exception {
		if(StringUtils.isBlank(this.col_name) || this.parm==null)
			return "";
		
		String condition = " and ";
		if(!isAnd)
			condition = " or ";
		
		StringBuilder builder = new StringBuilder();
		
		if(this.parm!=null && StringUtils.
				isNotBlank(String.valueOf(this.parm))){
			String value = String.valueOf(this.parm);
			
			builder.append(condition).append(this.col_name).append(" like '%").append(value).append("%' ");
		}
		
		
		return builder.toString();
	}
}
