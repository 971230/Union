
/*
 * Created on 2003-5-18
 * 错误号码段 22980xxx
 * 获取数据库连接对象(Connection)的类，缺省数据库连接有三个,分别是Default，LogDB,BackGround，数据库配置名称大小写敏感
 */
package com.powerise.ibss.util;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.powerise.ibss.framework.FrameException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据库连接的处理
 */
public class DBUtil {

//	private static Logger m_Logger;

	/**
	 * 数据库连接配置: TYPE=1 使用DataSource ，TYPE=2 使用标准的JDBC连接
	 * 确保在函数出错抛出意外之前将已Open的Connection关闭
	 * 
	 * @return
	 * @throws FrameException
	 */
	public static Connection getConnection(String dBName)
			throws FrameException {
//		 return pureConnection(dBName) ;
		if(StringUtil.isEmpty(dBName)){
			dBName="dataSource";
		}
		
		DataSource dataSource = SpringContextHolder.getBean(dBName);
		try {
			if (dataSource != null)
				return dataSource.getConnection();
			else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * 无拦截Connection,避免代理中出现死循环!
	 * 
	 * @param aDBName
	 * @return
	 * @throws FrameException
	 */
//	private static Connection pureConnection(String aDBName)
//			throws FrameException {
//		Connection conn = null;
//		String strDBName = null;
//		HashMap hmDB = null;
//		String strType = null, strSetName = null, strDriver = "", strUrl = "";
//		Object obj = null;
//		getLogger();
//		// 判断是否已经初始化系统
//		if (!GlobalNames.CONFIG_LOADED) {
//			SysSet.initSystem(3);
//		}
//		strDBName = aDBName;
//		if (System.getProperty("RUN_MODE") != null
//				&& System.getProperty("RUN_MODE")
//						.equalsIgnoreCase("BACKGROUND")
//				&& strDBName.equals(SysSet.getDefaultDBName())) {
//			strDBName = "BackGround";
//		}
//
//		hmDB = SysSet.getDBInfoByName(strDBName);
//		obj = hmDB.get("Type");
//		if (obj == null) {
//			strType = "2"; // 标准的JDBC连接
//		} else {
//			strType = (String) obj;
//		}
//		if (strType.equals("1")) {// 通过DataSource连接数据库
//			try {
//				String jndiName = (String) hmDB.get("DataSource");
//				DataSource ds = (DataSource) ServiceLocator.getInstance()
//						.getDataSource(jndiName);
//				conn = ds.getConnection();
//				conn.setAutoCommit(false);
//			} catch (java.sql.SQLException esql) {
//				esql.printStackTrace();
//				throw new FrameException(-22990212, "在连接数据库 " + strDBName
//						+ "(DataSource） 失败( SQLException ）", SysSet
//						.getStackMsg(esql));
//			}
//		} else if (strType.equals("2")) {// 数据库直连
//			try {
//				Iterator it = hmDB.keySet().iterator();
//				Properties props = new Properties();
//				while (it.hasNext()) {
//					strSetName = (String) it.next();
//					if (strSetName.equals("Driver"))
//						strDriver = (String) hmDB.get(strSetName);
//					else if (strSetName.equals("Url"))
//						strUrl = (String) hmDB.get(strSetName);
//					else if (strSetName.equals("XAType"))
//						continue;
//					else {
//						props.put(strSetName, (String) hmDB.get(strSetName));
//					}
//				}
//				DriverManager.registerDriver((Driver) Class.forName(strDriver)
//						.newInstance());
//				conn = DriverManager.getConnection(strUrl, props);
//				conn.setAutoCommit(false);
//			} catch (Exception e) {
//				if (conn != null) {
//					try {
//						conn.close();
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//					conn = null;
//				}
//				m_Logger.info(SysSet.getStackMsg(e));
//				throw new FrameException(-22990213,
//						"连接连数据库" + strDBName + "失败", e.getMessage());
//			}
//		} else {
//			throw new FrameException(-22990214, "配置的数据库Type不支持:" + strType);
//		}
//		return conn;
//
//	}

//	private static void getLogger() {
//		if (m_Logger == null)
//			m_Logger = Logger.getLogger("com.powerise.ibss.util.DBUtil");
//	}

	/**
	 * 通过指定的名字得到数据库的连接
	 * 
	 * @param name
	 *            指定的连接名
	 */
	public static Connection getConnection() throws FrameException {
		return getConnection("dataSource");
	}

	/**
	 * 此方法将被废弃
	 * 
	 * @return
	 * @throws FrameException
	 */
	public static Connection getConnectionThin() throws FrameException {
		return getConnection("BackGround");
	}

	/**
	 * 关闭链接
	 * 
	 * @param conn
	 * @throws FrameException
	 */
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
				conn.close();
				conn = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
