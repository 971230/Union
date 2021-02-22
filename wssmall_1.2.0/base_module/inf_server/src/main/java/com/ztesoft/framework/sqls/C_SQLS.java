package com.ztesoft.framework.sqls;

import java.util.Map;

import com.ztesoft.common.util.PlatService;


public class C_SQLS extends Sqls {

	static class SingletonHolder {
		static Sqls sqlsManager = new C_SQLS();
		static {
			try {
				Map<String, String> mysqls = sqlsManager.sqls;
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
			}
		}
	}

	public static Sqls getInstance() {
		return SingletonHolder.sqlsManager;
	}
	
	// 配置的SQL...
	static final String mySQL = "select * from dual";

	// 相关的SQL放置到集合map中
	public C_SQLS() {
		SqlUtil.initSqls(C_SQLS.class, this, sqls);
	}
  
    	
	/**
	 * DcDataDAO
	 */
	private   static final String SELECT_DC_SQL = "select * from dc_sql a where a.dc_name=?";	
	private   static final String SELECT_DC_PUBLIC_BY_STYPE = "select * from dc_public a where a.stype=?";
	private   static final String SELECT_DC_PUBLIC_BY_STYPE_PKEY = "select * from dc_public a where a.stype=? and a.pkey=?";
	
			
  
}
