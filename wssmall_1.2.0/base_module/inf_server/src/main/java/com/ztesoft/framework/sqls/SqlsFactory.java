package com.ztesoft.framework.sqls;

import com.ztesoft.config.ParamsConfig;

public class SqlsFactory {

	//private static String databaseType = CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE");
	
	private static String databaseType = ParamsConfig.getInstance()
			.getParamValue("DATABASE_TYPE");			

	// public static Sqls getOSQLS() {
	// Sqls sqls = null;
	// if (CrmConstants.DB_TYPE_ORACLE.equals(databaseType)) {
	// sqls = O_SQLS.getInstance();
	// } else if (CrmConstants.DB_TYPE_INFORMIX.equals(databaseType)) {
	// sqls = O_SQLS_INFORMIX.getInstance();
	// }
	//
	// return sqls;
	// }
	//
	 public static Sqls getCSQLS() {
	 Sqls sqls = null;
	 if (ParamsConfig.DB_TYPE_ORACLE.equals(databaseType)) {
	 sqls = C_SQLS.getInstance();
	 } else if (ParamsConfig.DB_TYPE_INFORMIX.equals(databaseType)) {
	 sqls = C_SQLS_INFORMIX.getInstance();
	 }

		return sqls;
	}
	//
	// public static Sqls getSSQLS() {
	// Sqls sqls = null;
	// if (CrmConstants.DB_TYPE_ORACLE.equals(databaseType)) {
	// sqls = S_SQLS.getInstance();
	// } else if (CrmConstants.DB_TYPE_INFORMIX.equals(databaseType)) {
	// sqls = S_SQLS_INFORMIX.getInstance();
	// }else {
	// sqls = S_SQLS.getInstance();
	// }
	//
	// return sqls;
	// }
	//
	// public static Sqls getCFSQLS() {
	// Sqls sqls = null;
	// if (CrmConstants.DB_TYPE_ORACLE.equals(databaseType)) {
	// sqls = CF_SQLS.getInstance();
	// } else if (CrmConstants.DB_TYPE_INFORMIX.equals(databaseType)) {
	// sqls = CF_SQLS_INFORMIX.getInstance();
	// }
	//
	// return sqls;
	// }

	public static Sqls getFSQLS() {
		Sqls sqls = null;
		if (ParamsConfig.DB_TYPE_ORACLE.equals(databaseType)) {
			sqls = F_SQLS.getInstance();
		} else if (ParamsConfig.DB_TYPE_INFORMIX.equals(databaseType)) {
			sqls = F_SQLS_INFORMIX.getInstance();
		}

		return sqls;
	}

}
