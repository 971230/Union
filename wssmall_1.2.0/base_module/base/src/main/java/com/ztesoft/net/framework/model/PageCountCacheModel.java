
package com.ztesoft.net.framework.model;

import java.io.Serializable;

/**
 * 分页总数缓存模型（存入缓存中的模型）
 * 
 * @作者 Z
 * @创建日期 2015年12月24日 上午11:31:49
 * @版本 V 1.0
 */
public class PageCountCacheModel implements Serializable {
	private static final long serialVersionUID = -7299453869629125212L;
	
	/**
	 * 总数sql
	 */
	private String countSql;
	
	/**
	 * 参数值
	 */
	private Object[] varArgs;

	public String getCountSql() {
		return countSql;
	}

	public Object[] getVarArgs() {
		return varArgs;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

	public void setVarArgs(Object[] varArgs) {
		this.varArgs = varArgs;
	}
 
	
}
