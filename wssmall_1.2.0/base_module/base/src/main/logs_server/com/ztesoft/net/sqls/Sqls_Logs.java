package com.ztesoft.net.sqls;
/**
 * 
 * @author wu.i
 * 移植CRM代码
 *
 */
public class Sqls_Logs extends Sqls {

	/*static class SingletonHolder {
		static Sqls sqlsManager = new Sqls_Task();
		static { 
			try{
				Map<String , String>  mysqls = sqlsManager.sqls;
				Class o_sql_local_class = Class.forName(sqlsManager.getClass().getName() + "_LOCAL");
				Sqls o_sql_local = (Sqls)o_sql_local_class.newInstance();
				SqlUtil.initSqls(o_sql_local_class, o_sql_local , mysqls) ;
			}catch(Exception ex){
//				throw new RuntimeException(ex);
			}
		}
	}*/
	
	public Sqls_Logs(){
		//SqlUtil.initSqls(Sqls_Task.class, this , sqls) ;
	}
	
	/*public static Sqls getInstance() {
		return SingletonHolder.sqlsManager;
	}*/
	
	// 配置的SQL...

}
