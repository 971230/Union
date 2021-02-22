package com.ztesoft.net.app.base.core.service.dbsolution;

import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;

public class DBSolutionFactory {
	public static IDBSolution getDBSolution() {
		IDBSolution result = null;
		if (EopSetting.DBTYPE.equals("1")) {
			result = SpringContextHolder.getBean("mysqlSolution");
		} else if (EopSetting.DBTYPE.equals("2")) {
			result = SpringContextHolder.getBean("oracleSolution");
		} else if (EopSetting.DBTYPE.equals("3")) {
			result = SpringContextHolder.getBean("sqlserverSolution");
		} else
			throw new RuntimeException("未知的数据库类型");
/*
		if (result != null) {
			JdbcTemplate jdbcTemplate = SpringContextHolder
					.getBean("jdbcTemplate");
			try {
				DataSource dataSource = jdbcTemplate.getDataSource();
				Connection conn = dataSource.getConnection();
				result.setConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}*/
		return result;
	}

	public static void main(String[] argv) {

	}
}
