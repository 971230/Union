package com.ztesoft.net.sqls;

import com.ztesoft.net.mall.core.utils.ManagerUtils;
/**
 * 
 * @author wu.i
 * 移植CRM代码
 *
 */
public class Sqls_Payment extends Sqls {

	/*static class SingletonHolder {
		static Sqls sqlsManager = new Sqls_Payment();
		static { 
			try{
				Map<String , String>  mysqls = sqlsManager.sqls;
				Class o_sql_local_class = Class.forName(sqlsManager.getClass().getName() + "_LOCAL");
				Sqls o_sql_local = (Sqls)o_sql_local_class.newInstance();
				SqlUtil.initSqls(o_sql_local_class, o_sql_local , mysqls) ;
			}catch(Exception ex){
				//throw new RuntimeException(ex);
			}
		}
	}*/
	
	public Sqls_Payment(){
		//SqlUtil.initSqls(Sqls_Payment.class, this , sqls) ;
	}
	
	/*public static Sqls getInstance() {
		return SingletonHolder.sqlsManager;
	}*/
	// 配置的SQL...

	public String getGET_ACCOUNT_LIST(){return "select a.* from es_payment_cfg c, es_payment_account a where" +
			" a.cfg_id=c.id and cfg_id=? and c.source_from = a.source_from and a.source_from = '" 
			+ ManagerUtils.getSourceFrom() + "'";}
	
	public String getSHOW_BANK_LIST(){return "select b.*,r.cfg_id from es_bank b,es_payment_cfg_bank_rel r " +
			"where b.bank_id=r.bank_id and cfg_id=? and b.source_from = r.source_from " +
			"and r.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
}
