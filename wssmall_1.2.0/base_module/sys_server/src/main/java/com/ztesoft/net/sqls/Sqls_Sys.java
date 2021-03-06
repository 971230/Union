package com.ztesoft.net.sqls;
/**
 * 
 * @author wu.i
 * 移植CRM代码
 *
 */
public class Sqls_Sys extends Sqls {

	/*static class SingletonHolder {
		static Sqls sqlsManager = new Sqls_Sys();
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
	
	public Sqls_Sys(){
		//SqlUtil.initSqls(Sqls_Sys.class, this , sqls) ;
	}
	
	/*public static Sqls getInstance() {
		return SingletonHolder.sqlsManager;
	}*/
	// 配置的SQL...
	public String getGET_USER_APP_LIST(){return "select distinct a.objvalue from es_auth_action a where a.type = 'app' " +
			"and exists (select 1 from es_user_role m, es_role_auth n where a.actid = n.authid and " +
			"m.roleid = n.roleid and exists(select 1 from es_adminuser ad where m.userid = ad.userid and ad.username = ?)) and a.source_from is not null";}
	
	public String getGET_USER_FOUNDER(){return "select a.founder from es_adminuser a where a.username = ? and a.source_from is not null";}
	
	public String getACCESS_COUNT_ONE_HOUR(){return "select count(0)  from access where ip=? and url=? and access_time>=sysdate-1/24";}
	
	public String getMSG_SET_LIST() {
		
		return "select set_id, set_type, receiver, mobile_no, msg, comments, source_from "
				+ " from es_msg_set where 1=1 ";
	}
	
	public String getMSG_SET_DELETE_BY_KEY() {
		
		return " delete from es_msg_set where 1=1 and set_id = ?";
	}
	
	public String getADMIN_USER_GET(){
		return "select * from es_adminuser where userid=? ";
	}
}
