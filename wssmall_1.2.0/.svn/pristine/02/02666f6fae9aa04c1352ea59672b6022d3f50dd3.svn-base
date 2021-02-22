/*
 * Created on 2004-11-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.powerise.ibss.util;

import com.powerise.ibss.framework.FrameException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * 
 * @author Administrator
 * 
 * Preferences - Java - Code Style - Code Templates
 */
public class MultiDBUtil {
	HashMap<String, Connection> conns; // 放置数据库连接的地方
	boolean m_XAType;// 放置数据库连接类型的地方，考虑在Oracle中xa支持dblink的视图不是很好的情况，某些情况下不使用xa，尽管在多数据库模式下
	static private Logger m_Logger = null;

	public MultiDBUtil() {
		m_XAType = false;
		conns = new HashMap<String, Connection>();
		m_Logger = Logger.getLogger(this.getClass().getName()); // 初始化Logger
	}

	public Connection getConnection() throws FrameException {
		return getConnection("dataSource");// 获取缺省的数据库连接
	}

	public Connection getConnection(String strDB) throws FrameException {
		Object obj = null;
		Connection conn = null;

		m_Logger.debug("开始获取数据库连接，" + strDB);
		obj = conns.get(strDB);
		if (obj == null) // 没有，则新建一个
		{
			// 判断是否使用XA Type ,以第一个连接为准

			m_Logger.debug("获取数据库连接:" + strDB);
			conn = DBUtil.getConnection(strDB);
			conns.put(strDB, conn); // 加入到容器中，最后一并关闭
		} else {
			conn = (Connection) obj;
		}
		return conn;
	}

	public void close() {
		if (conns != null) {
			for (Entry<String, Connection> entry : conns.entrySet()) {
				String dbName = entry.getKey();
				try {
					m_Logger.debug("关闭数据库:" + dbName);
					Connection conn = entry.getValue();
					conn.close();
				} catch (Exception e) {
					m_Logger.info("关于数据库连接失败:" + dbName
							+ SysSet.getStackMsg(e));
				}
			}
			conns.clear();
		}
		conns = null;
	}

	public boolean isXAType() {
		return m_XAType;
	}

	public void commit() throws FrameException {
		if (conns != null) {
			for (Entry<String, Connection> entry : conns.entrySet()) {
				String dbName = entry.getKey();
				try {
					m_Logger.debug("关闭数据库:" + dbName);
					Connection conn = entry.getValue();
					conn.commit();
				} catch (Exception e) {
					m_Logger.info("关于数据库连接失败:" + dbName
							+ SysSet.getStackMsg(e));
				}
			}
		}
	}

	public void rollback() throws FrameException {
		if (conns != null) {
			for (Entry<String, Connection> entry : conns.entrySet()) {
				String dbName = entry.getKey();
				try {
					m_Logger.debug("关闭数据库:" + dbName);
					Connection conn = entry.getValue();
					conn.rollback();
				} catch (Exception e) {
					m_Logger.info("关于数据库连接失败:" + dbName
							+ SysSet.getStackMsg(e));
				}
			}
		}
	}

}
