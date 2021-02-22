package com.ztesoft.framework.sqls;


import java.util.Map;

import com.ztesoft.common.util.PlatService;

public class C_SQLS_INFORMIX extends Sqls {

	static class SingletonHolder {
		static Sqls sqlsManager = new C_SQLS_INFORMIX();
		static { 
			try{
				Map<String , String>  mysqls = sqlsManager.sqls;
				// 以_Hn(省份标示)或_LOCAL结尾
				//String system_tag=StrTools.isEmptyDefault(DcSystemParamUtil.getSysParamByCache("SYSTEM_TAG"), "LOCAL"); 
				//Class c_sql_local_class = Class.forName(sqlsManager.getClass().getName() + "_"+system_tag);
//				Class c_sql_local_class = Class.forName(sqlsManager.getClass().getName());
//				Sqls c_sql_local = (Sqls)c_sql_local_class.newInstance();
				
				Sqls c_sql_local = PlatService.getPlatClass(sqlsManager.getClass());
				Class c_sql_local_class=c_sql_local.getClass();
				SqlUtil.initSqls(c_sql_local_class, c_sql_local , mysqls);
			}catch(Exception ex){
//				throw new RuntimeException(ex);
			}finally{
				
			}
		}
	}

	public static  Sqls getInstance() {
		return SingletonHolder.sqlsManager;
	}
	
	//配置的SQL...
	static final String mySQL = "select * from dual";
	 
	// 相关的SQL放置到集合map中
	public C_SQLS_INFORMIX() {
		SqlUtil.initSqls(C_SQLS_INFORMIX.class, this , sqls) ;
	}

	
}
