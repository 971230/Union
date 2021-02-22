package com.ztesoft.net.sqls;

/**
 * 
 * @author wu.i
 * 移植CRM代码
 *
 */
public class Sqls_esearch extends Sqls {

	public Sqls_esearch(){
		//SqlUtil.initSqls(Sqls_Order.class, this , sqls) ;}
	} 
	
	public String getLOGS_LIST() {
		return "SELECT t.REQ_TIME,t.LOG_ID,t.OP_CODE,t.REQ_XML,t.RSP_XML FROM INF_COMM_CLIENT_CALLLOG t";
	}
	
	public String getDELAY_UPDATE_LIST(){
		return "SELECT T.* FROM ES_ESEARCH_DELAYUPDATE_INFO T WHERE T.IS_UPDATED IN ('0','-1') and rownum <3000";
	}
	
}
