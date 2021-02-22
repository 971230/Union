package com.ztesoft.net.sqls;

import com.ztesoft.net.framework.database.SqlBuilderInterface;

/**
 * 基础的SQL构造器，构造的SQL为“ and 列名  参数 ”
 * @author zhaoc
 *
 */
public class SqlBuilder implements SqlBuilderInterface {
	protected String col_name;
	protected Object parm;
	protected boolean isAnd = true;
	
	public SqlBuilder(){
		
	}
	
	public SqlBuilder(String col_name,Object parm){
		this.col_name = col_name;
		this.parm = parm;
	}
	
	public SqlBuilder(String col_name,Object parm,boolean isAnd){
		this.col_name = col_name;
		this.parm = parm;
		this.isAnd = isAnd;
	}
	
	@Override
	public String getCol_name() {
		return col_name;
	}

	@Override
	public void setCol_name(String col_name) {
		this.col_name = col_name;
	}

	@Override
	public Object getParm() {
		return parm;
	}

	@Override
	public void setParm(Object parm) {
		this.parm = parm;
	}
	
	@Override
	public String getSql() throws Exception {
		
		String condition = " and ";
		if(!isAnd)
			condition = " or ";
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(condition).append(SqlUtil.getStrValue(this.col_name)).append(" ").
			append(SqlUtil.getStrValue(this.parm)).append(" ");
		
		return builder.toString();
	}
}
