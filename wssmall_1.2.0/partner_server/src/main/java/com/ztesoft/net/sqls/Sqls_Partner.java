package com.ztesoft.net.sqls;

import com.ztesoft.net.mall.core.utils.ManagerUtils;
/**
 * 
 * @author wu.i
 * 移植CRM代码
 *
 */
public class Sqls_Partner extends Sqls {
	
	public Sqls_Partner(){
		
	} 
	
	
	public String getPARTNER_STAFF_CODE (){
		return "select count(*) staff_code from es_partner_staff a where a.partner_id = ? " + ManagerUtils.apSFromCond("a");
	}
	
	public String getQRY_PARTER_STAFF_BY_ID_CODE(){
		return "select a.* from es_partner_staff a where a.partner_id = ? and a.staff_code = ? " + ManagerUtils.apSFromCond("a");
	}
}
