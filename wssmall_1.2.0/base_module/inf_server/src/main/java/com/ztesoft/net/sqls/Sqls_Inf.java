package com.ztesoft.net.sqls;



/**
 * 
 * @author wu.i
 * 移植CRM代码
 *
 */
public class Sqls_Inf extends Sqls {

	public Sqls_Inf(){
		
	}
	/**
	 * 模块对应的栏目
	 * @return
	 */
	public String getINF_NEW_FILEDS (){
		return "";
	}
	
	public String getQRY_ORDER_ID_BY_OUT_ID(){
		return "select a.order_id from es_order_ext a where a.out_tid = ?";
	}
}
