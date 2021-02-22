package com.ztesoft.net.eop.sdk.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class Test {
	private static Logger logger = Logger.getLogger(Test.class);
	private static List<String> tables = new ArrayList<String>();
	private static List<String> keywords=new ArrayList<String>();
	static {
		keywords.add("COMMENT");
		
		tables.add("ES_THEMEURI");
		tables.add("ES_GOODS_FIELD");
		tables.add("ES_GOODS");
		tables.add("ES_GOODS");
		tables.add("ES_DATA_FIELD");
		tables.add("ES_CART");
		tables.add("ES_MEMBER");
		tables.add("ES_GROUP_BUY");
		tables.add("ES_GOODS_TYPE");
		tables.add("ES_DLY_TYPE_AREA");
		tables.add("ES_COMMENTS");
		tables.add("ES_CART_OPTIC");
		tables.add("ES_PROMOTION_ACTIVITY");
		tables.add("ES_PRODUCT_COLOR");
		tables.add("ES_PAYMENT_LOGS");
		tables.add("ES_ORDER_ITEMS");
		tables.add("ES_MESSAGE");
		tables.add("ES_GNOTIFY");
		tables.add("ES_FREEOFFER");
		tables.add("EOP_SITE");
		tables.add("ES_PROMOTION");
		tables.add("ES_ORDER_META");
		tables.add("ES_EN_ARTICLE");
		tables.add("EOP_APP");
		tables.add("ES_PRINT_TMPL");
		tables.add("ES_ORDER_LOG");
		tables.add("ES_ORDER");
		tables.add("ES_GOODS_ADJUNCT");
		tables.add("ES_DLY_TYPE");
		tables.add("EOP_ASK");
		tables.add("ES_GUESTBOOK");
		tables.add("EOP_DATA_LOG");
		tables.add("ES_RETURNS_ORDER");
		tables.add("ES_PRODUCT");
		tables.add("ES_PAYMENT_CFG");
		tables.add("ES_DELIVERY");
		tables.add("ES_BRAND");
		tables.add("ES_ARTICLE");
		tables.add("EOP_USERDETAIL");
		tables.add("ES_INVOICE_APPLY");
		tables.add("ES_EN_ARTICLE_HIS");
		tables.add("ES_DLY_CENTER");
		tables.add("ES_AUTH_ACTION");
		tables.add("EOP_REPLY");
	}

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "spring/dataAccessContext-jdbc.xml");
		ComboPooledDataSource ds = (ComboPooledDataSource) ctx
				.getBean("dataSource");
		ComboPooledDataSource oracleDS = (ComboPooledDataSource) ctx
				.getBean("oracleDS");
		Connection mysqlConn = null, conn = null;
		PreparedStatement pst = null, pst1 = null;
		ResultSet rs = null;
		try {
			mysqlConn = ds.getConnection();
			for (String table : tables) {
				String sql = "select * from " + table;
				pst = mysqlConn.prepareStatement(sql);
				rs = pst.executeQuery();
				while (rs != null && rs.next()) {
					StringBuffer insert = new StringBuffer("insert into ");
					StringBuffer values = new StringBuffer("values (");
					StringBuffer select = new StringBuffer("select * from ");
					insert.append(table);
					insert.append("(");
					select.append(table);
					ResultSetMetaData meatData = rs.getMetaData();
					List<Map<String, String>> params = new ArrayList<Map<String, String>>();
					for (int i = 1; i <= meatData.getColumnCount(); i++) {
						String name = meatData.getColumnLabel(i).toLowerCase();
						int type = meatData.getColumnType(i);
						if (i > 1) {
							insert.append(",");
							values.append(",");
						} else {
							select.append(" where " + name + "="
									+ rs.getInt(name) + " for update");
						}
						if (keywords.contains(name.toUpperCase())) {
							insert.append("\"" + name + "\"");
						} else {
							insert.append(name);
						}
						String value = "";
						if (Types.TIME == type || Types.TIMESTAMP == type) {
							String date = rs.getTimestamp(name).toString();
							value = "TIMESTAMP'" + date + "'";
						} else if (Types.VARCHAR == type || Types.CHAR == type
								|| Types.NCHAR == type) {
							value = "'" + rs.getString(name) + "'";
						} else if (Types.INTEGER == type
								|| Types.SMALLINT == type
								|| Types.TINYINT == type
								|| Types.BOOLEAN == type) {
							value += rs.getInt(name);
						} else if (Types.BIGINT == type
								|| Types.NUMERIC == type) {
							value += rs.getBigDecimal(name);
						} else if (Types.LONGVARCHAR == type
								|| Types.LONGNVARCHAR == type
								|| Types.LONGVARBINARY == type) {
							value = "'" + rs.getString(name) + "'";
//							values.append("?");
//							HashMap<String, String> map = new HashMap<String, String>();
//							map.put(name, rs.getString(name));
//							params.add(map);
						} else {
							values.append(rs.getObject(name));
						}
						if (!"".equals(value))
							values.append(value);
					}
					insert.append(")");
					values.append(")");
					insert.append(values);
					logger.info(insert);
					ResultSet rs1 = null;
					try {
						conn = oracleDS.getConnection();
						pst1 = conn.prepareStatement(insert.toString());
//						for (int i = 0; i < params.size(); i++) {
//							pst1.setObject(i + 1,CLOB.getEmptyCLOB());
//						}
						pst1.executeUpdate();
						conn.commit();
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						if (rs1 != null) {
							rs1.close();
						}
						if (pst1 != null) {
							pst1.close();
						}
						if (conn != null) {
							conn.close();
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (mysqlConn != null) {
					mysqlConn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
