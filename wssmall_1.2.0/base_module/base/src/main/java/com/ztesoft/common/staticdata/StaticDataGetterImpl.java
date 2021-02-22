package com.ztesoft.common.staticdata;




import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.sql.Connection;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.crm.report.db.DataSource;
import com.ztesoft.crm.report.ReportContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ztesoft.crm.report.interfaces.StaticData;
import com.ztesoft.crm.report.interfaces.StaticDataGetter;
import com.ztesoft.crm.report.lang.Utils;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class StaticDataGetterImpl implements StaticDataGetter {
	private static Logger logger = Logger.getLogger(StaticDataGetterImpl.class);
	@Override
	public List execute(ServletContext context, HttpServletRequest request,
			String code, String parentId) throws Exception {
		HashMap map = new HashMap();
		map.put("parentValue", parentId);
		return execute(context, request, code, map);
	}

	@Override
	public List execute(ServletContext context, HttpServletRequest request,
			String code, HashMap map) throws Exception {
		DataSource ds = ReportContext.getContext().getDataSource("jdbc/WssmallDb");
		Connection conn = ds.getConnection();
		PreparedStatement stmt = null;
		ResultSet res = null;
		
		AdminUser adminUser = ManagerUtils.getAdminUser();
		List arr = new ArrayList();
		String dcSql = "SELECT dc_sql from es_dc_sql where 1=1 ";
		for (Enumeration keys = request.getParameterNames(); keys
				.hasMoreElements();) {
			String key = (String) keys.nextElement();
			map.put(key, request.getParameter(key));
		}
		
		try {
			if(StringUtils.isNotEmpty(code)){
				dcSql += " and dc_name ='"+code+"'";
			}
			else{
				List localList1 = arr;
				return localList1;
			}
			
			conn = ds.getConnection();
			stmt = conn.prepareStatement(dcSql);
			res = stmt.executeQuery();
			 
			if (res.next()) {
				dcSql = res.getString("dc_sql");
			}

			if (Utils.isEmpty(dcSql)) {
				List localList1 = arr;
				return localList1;
			}
			// StringBuffer sql;
			dcSql = Utils.format(dcSql, new String[]{(String) map
					.get("parentValue")});
			dcSql = Utils.format(dcSql, map);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(dcSql));
			res = stmt.executeQuery();

			int cm = res.getMetaData().getColumnCount();
			while (res.next()) {
				StaticData d = new StaticData();
				d.setValue(res.getString(1));
				d.setText(res.getString(2));
				if (cm >= 3) {
					d.setParent(res.getString(3));
				}
				if (cm >= 4)
					d.setLeaf(Utils.isTrue(res.getString(4)));
				arr.add(d);
			}

			List localList1 = arr;
			return localList1;
		} catch (Exception e) {
			logger.info(DAOSQLUtils.getFilterSQL(dcSql));
			e.printStackTrace();
			throw e;
		} finally {

		}
	}
}