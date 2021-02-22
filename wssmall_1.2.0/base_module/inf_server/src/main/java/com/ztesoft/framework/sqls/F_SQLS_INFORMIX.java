package com.ztesoft.framework.sqls;

import java.util.Map;

import com.ztesoft.common.util.PlatService;



public class F_SQLS_INFORMIX extends Sqls{
	static class SingletonHolder {
		static Sqls sqlsManager = new F_SQLS_INFORMIX();
		static {
			try {
				Map<String, String> mysqls = sqlsManager.sqls;
				// 以_Hn(省份标示)或_LOCAL结尾
				// String system_tag =
				// StrTools.isEmptyDefault(DcSystemParamUtil.getSysParamByCache("SYSTEM_TAG"),"LOCAL");
				// Class f_sql_local_class =
				// Class.forName(sqlsManager.getClass().getName() + "_" +
				// system_tag);
//				Class f_sql_local_class = Class.forName(sqlsManager.getClass()
//						.getName());
//				Sqls f_sql_local = (Sqls) f_sql_local_class.newInstance();
				
				
				Sqls f_sql_local = PlatService.getPlatClass(sqlsManager.getClass());
				Class f_sql_local_class=f_sql_local.getClass();
				SqlUtil.initSqls(f_sql_local_class, f_sql_local, mysqls);
			} catch (Exception ex) {
				// throw new RuntimeException(ex);
			}
		}	
	}

	public static  Sqls getInstance() {
		return SingletonHolder.sqlsManager;
	}
	
	String mySQL = "select * from dual";

	// WAIT TO BE MODIFY cheng
	String qrySqlRow = "select first ? *  from (select my_table.*  from ( @# )  my_table )   ";
		
	//相关的SQL放置到集合map中
	public F_SQLS_INFORMIX() {
		SqlUtil.initSqls(F_SQLS_INFORMIX.class, this , sqls) ;
	}
	
}
