package com.ztesoft.net.sqls;

import org.apache.commons.lang.StringUtils;

/**
 * 数字SQL构造器
 * 在构造查询条件时，如果数字类型的值为0时会跳过
 * 数字SQL构造器，不管值是否为0，都会返回条件SQL
 * @author zhaoc
 *
 */
public class SqlNumberBuilder extends SqlBuilder {

	public SqlNumberBuilder(){
		super();
	}

	public SqlNumberBuilder(String col_name, Object parm) {
		super(col_name, parm);
	}
	
	public SqlNumberBuilder(String col_name, Object parm,boolean isAnd) {
		super(col_name, parm,isAnd);
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
			
			builder.append(condition).append(this.col_name).append("=").append(value).append(" ");
		}
		
		
		return builder.toString();
	}
}
