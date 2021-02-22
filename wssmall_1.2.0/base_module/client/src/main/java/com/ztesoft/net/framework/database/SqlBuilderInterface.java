package com.ztesoft.net.framework.database;

/**
 * 数据库条件构建接口
 * @author zhaoc
 */
public interface SqlBuilderInterface {
	
	/**
	 * 获取列名
	 * @return
	 */
	public String getCol_name();

	/**
	 * 设置列名
	 * @param col_name
	 */
	public void setCol_name(String col_name);

	/**
	 * 获取参数
	 * @return
	 */
	public Object getParm();

	/**
	 * 设置参数
	 * @param parm
	 */
	public void setParm(Object parm);
	
	/**
	 * 获取SQL语句
	 * @return
	 * @throws Exception
	 */
	public String getSql() throws Exception;
}
