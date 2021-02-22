package com.ztesoft.common.dao;

import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.UserTransaction;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.config.JNDINames;

public class SysSet {

	private static HashMap m_DBLink = null; // 数据库的连接配置,因为存在多个的数据库配置，因此其结构是二级hashmap

	private static String m_DefaultDBName = JNDINames.DEFAULT_DATASOURCE; // 缺省的数据库连接名称，考虑同时连接多个数据库，所以此值可变

	private static int m_XATranTimeOut = 60;

	public static boolean surportAjax = true;// 是否支持bho和ajax

	/**
	 * 在XA环境下,获取JTA的UserTransaction
	 * 
	 * @return
	 * @throws FrameException
	 */
	public static UserTransaction getUserTransaction() throws FrameException {
		UserTransaction usTran = null;
		Context usContext = null;
		try {
			usContext = new InitialContext();
			usTran = (UserTransaction) usContext.lookup("java:comp/UserTransaction");
			// Set the transaction timeout to 30 seconds
			usTran.setTransactionTimeout(m_XATranTimeOut);
		} catch (Exception e) {
			throw new FrameException(-22990013, "创建UserTransaction异常",e.toString());
		}
		return usTran;
	}

	/**
	 * xa环境下的启动事务
	 * 
	 * @param usTran
	 * @throws FrameException
	 */
	public static void tpBegin(UserTransaction usTran) throws FrameException {
		// // Use JNDI to locate the UserTransaction object
		try {
			// ...
			// Begin a transaction
			usTran.begin();
		} catch (Exception e) {
			throw new FrameException(-22990010, "xa初始化出错:" + e.getClass().getName(), e.toString());
		}
	}

	/**
	 * XA环境下的提交事务
	 * 
	 * @param usTran
	 * @throws FrameException
	 */
	public static void tpCommit(UserTransaction usTran) throws FrameException {
		if (usTran != null) {
			try {
				usTran.commit();
				usTran = null;
			} catch (Exception e) {
				throw new FrameException(-22990010, "xa提交数据出错", e.toString());
			}
		}
	}

	/**
	 * xa环境下的回滚事务
	 * 
	 * @param usTran
	 * @throws FrameException
	 */
	public static void tpRollback(UserTransaction usTran) throws FrameException {
		if (usTran != null) {
			try {
				usTran.rollback();
				usTran = null;
			} catch (Exception e) {
				throw new FrameException(-22990010, "xa回滚数据出错", e.toString());
			}
		}
	}
	 /**
	   * 获取系统的HOME目录，首先从系统变量IBSS_HOME中获取，如果没有，则直接获取当前用户的 HOME环境变量
	   *
	   * @return java.lang.String
	   */
	  public static String getHomeDirectory() {
	    String s = System.getProperty("IBSS_HOME");
	    if (s != null)
	      return s;
	    else
	      return System.getProperty("user.home");
	  }

}
