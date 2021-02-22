package com.ztesoft.net.mall.core.consts;

public enum Operations {
	
	EQUALS("="),LIKE("like"),UNEQUALS("<>"),NOT_LIKE("not like");

	private String value;
	
	private Operations(String value) {
		this.value = value;
	}
	
	/**
	 * 是否是模糊查询
	 * @param oper
	 * @return
	 */
	public static boolean isLike(Operations oper) {
		return oper.equals(LIKE) || oper.equals(NOT_LIKE);
	}
	
	public static void main(String[] args) {
		/*logger.info(isLike(EQUALS));
		logger.info(isLike(LIKE));
		logger.info(isLike(UNEQUALS));
		logger.info(isLike(NOT_LIKE));*/
		
	}
 }
