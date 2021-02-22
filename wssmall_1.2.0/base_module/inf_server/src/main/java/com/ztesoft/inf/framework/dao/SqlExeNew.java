package com.ztesoft.inf.framework.dao;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.config.InfConnUtil;
import com.ztesoft.ibss.common.dao.DAOUtils;
import com.ztesoft.inf.service.util.IConst;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.SQLWarningException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.SqlProvider;
import org.springframework.jdbc.datasource.DataSourceUtils;

import java.io.Reader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据库操作工具类
 * 
 * @author hzcl_sky gong.zhiwei
 * 
 */
public class SqlExeNew {
	private JdbcTemplate jdbcTemplate;
	public Connection conn;
	private static Logger logger = Logger.getLogger(SqlExeNew.class);
	
	private int fetchSize = 0;
	private int maxRows = 0;
	private boolean ignoreWarnings = true;
	public boolean isIgnoreWarnings() {
		return ignoreWarnings;
	}

	public void setIgnoreWarnings(boolean ignoreWarnings) {
		this.ignoreWarnings = ignoreWarnings;
	}

	public int getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	public int getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	/**
	 * 判断是否关闭连接
	 */
	private boolean flag = true;

	public SqlExeNew() {
	}

	public SqlExeNew(Connection conn) {
		this.conn = conn;
		this.flag = false;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
		this.flag = false;
	}

	private void getPriConn() throws SQLException {
		if (conn == null || conn.isClosed()) {
			conn = InfConnUtil.getConnection();
		}
	}
	/**
	 * 查询返回String
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public String queryForString(String sql) throws FrameException,
			SQLException {
		getPriConn();
		String retMsg = "";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs != null && rs.next()) {
				retMsg = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs=null;
			}
			if (pst != null) {
				pst.close();
				pst=null;
			}
			closeConnection();
		}
		return retMsg;
	}

	/**
	 * 查询返回String
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public String queryForString(String sql, String param)
			throws FrameException, SQLException {
		getPriConn();
		String retMsg = "";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, param);
			rs = pst.executeQuery();
			if (rs != null && rs.next()) {
				retMsg = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs=null;
			}
			if (pst != null) {
				pst.close();
				pst=null;
			}
			closeConnection();
		}
		return retMsg;
	}

	/**
	 * 查询返回String
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public String queryForString(String sql, String[] params)
			throws FrameException, SQLException {
		getPriConn();
		String retMsg = "";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pst.setString(i + 1, params[i]);
			}
			rs = pst.executeQuery();
			if (rs != null && rs.next()) {
				retMsg = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs=null;
			}
			if (pst != null) {
				pst.close();
				pst=null;
			}
			closeConnection();
		}
		return retMsg;
	}

	/**
	 * 查询返回Map
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public HashMap queryForMap(String sql, String param) throws FrameException,
			SQLException {
		getPriConn();
		HashMap retMap = new HashMap();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, param);
			rs = pst.executeQuery();
			if (rs != null && rs.next()) {
				retMap = convertToMap(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs=null;
			}
			if (pst != null) {
				pst.close();
				pst=null;
			}
			closeConnection();
		}
		return retMap;
	}


	/**
	 * 查询返回Map
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public HashMap queryForMap(String sql) throws FrameException, SQLException {
		getPriConn();
		HashMap retMap = new HashMap();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs != null && rs.next()) {
				retMap = convertToMap(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs=null;
			}
			if (pst != null) {
				pst.close();
				pst=null;
			}
			closeConnection();
		}
		return retMap;
	}

	/**
	 * 查询返回Map
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public HashMap queryForMap(String sql, List params) throws FrameException,
			SQLException {
		getPriConn();
		HashMap retMap = new HashMap();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			for (int i = 0; i < params.size(); i++) {
				pst.setObject(i + 1, params.get(i));
			}
			rs = pst.executeQuery();
			if (rs != null && rs.next()) {
				retMap = convertToMap(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs=null;
			}
			if (pst != null) {
				pst.close();
				pst=null;
			}
			closeConnection();
		}
		return retMap;
	}

	/**
	 * 查询返回Map
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public HashMap queryForMap(String sql, String[] args)
			throws FrameException, SQLException {
		getPriConn();
		HashMap retMap = new HashMap();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				pst.setString(i + 1, args[i]);
			}
			rs = pst.executeQuery();
			if (rs != null && rs.next()) {
				retMap = convertToMap(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs=null;
			}
			if (pst != null) {
				pst.close();
				pst=null;
			}
			closeConnection();
		}
		return retMap;
	}

	/**
	 * 查询返回list list中装载着Map
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public List queryForList(String sql) throws FrameException, SQLException {
		getPriConn();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			List results = new ArrayList();
			while (rs != null && rs.next()) {
				results.add(convertToMap(rs));
			}
			return results;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs=null;
			}
			if (pst != null) {
				pst.close();
				pst=null;
			}
			closeConnection();
		}
	}

	/**
	 * 查询返回list list中装载着Map
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public List queryForList(String sql, String[] args) throws FrameException,
			SQLException {
		getPriConn();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				pst.setString(i + 1, args[i]);
			}
			rs = pst.executeQuery();
			List results = new ArrayList();

			while (rs != null && rs.next()) {
				results.add(convertToMap(rs));
			}
			return results;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs=null;
			}
			if (pst != null) {
				pst.close();
				pst=null;
			}
			closeConnection();
		}
	}

	/**
	 * 查询返回list list中装载着Map
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public List queryForList(String sql, String param) throws FrameException,
			SQLException {
		getPriConn();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, param);
			rs = pst.executeQuery();
			List results = new ArrayList();
			while (rs != null && rs.next()) {
				results.add(convertToMap(rs));
			}
			return results;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs=null;
			}
			if (pst != null) {
				pst.close();
				pst=null;
			}
			closeConnection();
		}
	}

	/**
	 * 更新
	 * 
	 * @param sql
	 * @param list
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public boolean execUpdateForList(String sql, List list)
			throws FrameException, SQLException {
		getPriConn();
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			for (int i = 0; i < list.size(); i++) {
				pst.setObject(i + 1, list.get(i));
			}
			boolean retFlag = pst.executeUpdate() > 0 ? true : false;
			if (flag) {
				logger.info("提交事务.........");
				conn.commit();
			}
			return retFlag;
		} catch (SQLException e) {
			logger.debug("执行更新出错：" + e.getMessage());
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			if (pst != null) {
				pst.close();
				pst=null;
			}
			closeConnection();
		}
	}

	/**
	 * 更新
	 * 
	 * @param sql
	 * @param list
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public boolean execUpdateForList(String sql, String param)
			throws FrameException, SQLException {
		getPriConn();
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setObject(1, param);
			boolean retFlag = pst.executeUpdate() > 0 ? true : false;
			if (flag) {
				logger.info("提交事务.........");
				conn.commit();
			}
			return retFlag;
		} catch (SQLException e) {
			logger.debug("执行更新出错：" + e.getMessage());
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			if (pst != null) {
				pst.close();
				pst=null;
			}
			closeConnection();
		}
	}

	/**
	 * 更新
	 * 
	 * @param sql
	 * @param list
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public boolean execUpdateForList(String sql) throws FrameException,
			SQLException {
		getPriConn();
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			boolean retFlag = pst.executeUpdate() > 0 ? true : false;
			if (flag) {
				logger.info("提交事务.........");
				conn.commit();
			}
			return retFlag;
		} catch (SQLException e) {
			logger.debug("执行更新出错：" + e.getMessage());
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			if (pst != null) {
				pst.close();
				pst=null;
			}
			closeConnection();
		}
	}

	/**
	 * 更新
	 * 
	 * @param sql
	 * @param list
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public boolean execUpdateForList(String sql, String[] params)
			throws FrameException, SQLException {
		getPriConn();
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pst.setString(i + 1, params[i]);
			}
			boolean retFlag = pst.executeUpdate() > 0 ? true : false;
			if (flag) {
				logger.info("提交事务.........");
				conn.commit();
			}
			return retFlag;
		} catch (SQLException e) {
			logger.debug("执行更新出错：" + e.getMessage());
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			if (pst != null) {
				pst.close();
				pst=null;
			}
			closeConnection();
		}
	}

	/**
	 * 批量执行 参数List中装着List
	 * 
	 * @param sql
	 * @param list
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public boolean execBatchForListExt(String sql, List list)
			throws FrameException, SQLException {
		boolean flag = true;
		getPriConn();
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			for (int i = 0; i < list.size(); i++) {
				List args = (List) list.get(i);
				for (int j = 0; j < args.size(); j++) {
					pst.setObject(j + 1, args.get(j));
				}
				pst.addBatch();
			}
			pst.executeBatch();
			if (flag) {
				logger.info("提交事务.........");
				conn.commit();
			}
		} catch (SQLException e) {
			logger.debug("执行更新出错：" + e.getMessage());
			flag = false;
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			if (pst != null) {
				pst.close();
				pst=null;
			}
			closeConnection();
		}
		return flag;
	}

	/**
	 * 批量更新
	 * 
	 * @param sql
	 * @param list
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public boolean execBatchForList(String sql, List list)
			throws FrameException, SQLException {
getPriConn();
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			for (int i = 0; i < list.size(); i++) {
				Object[] args = (Object[]) list.get(i);
				for (int j = 0; j < args.length; j++) {
					pst.setObject(j + 1, args[j]);
				}
				pst.addBatch();
			}
			pst.executeBatch();
			if (flag) {
				logger.info("提交事务.........");
				conn.commit();
			}
		} catch (SQLException e) {
			logger.debug("执行更新出错：" + e.getMessage());
			flag = false;
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			if (pst != null) {
				pst.close();
				pst=null;
			}
			closeConnection();
		}
		return flag;
	}

	public static HashMap convertToMap(ResultSet rs) throws SQLException {
		HashMap retMap = new HashMap();
		ResultSetMetaData meatData = rs.getMetaData();
		for (int i = 1; i <= meatData.getColumnCount(); i++) {
			String name = meatData.getColumnLabel(i).toLowerCase();
			int type = meatData.getColumnType(i);
			if (Types.TIME == type || Types.TIMESTAMP == type) {
				String value = DAOUtils.getFormatedDateTime(rs.getTimestamp(name));
				retMap.put(name, value);
			} else if (Types.VARCHAR == type) {
				retMap.put(name, rs.getString(name));
			} else if (Types.INTEGER == type || Types.BIGINT == type) {
				retMap.put(name, String.valueOf(rs.getInt(name)));
			} else if (Types.BIGINT == type || Types.NUMERIC == type) {
				retMap.put(name, rs.getBigDecimal(name));
			} else if (Types.BLOB == type) {
				Blob blob = rs.getBlob(name);
				byte[] values = null;
				if (blob != null && blob.length() > 0)
					values = blob.getBytes(1, (int) blob.length());
				retMap.put(name, values);
			}  else if (Types.CLOB == type) {
				Clob  clob =  rs.getClob(name);
				String values = null;
				if (clob != null && clob.length() > 0)
					values = Clob2String(clob);
				retMap.put(name, values);
			} else {
				Object value = rs.getObject(name);
				retMap.put(name, value);
			}
		}
		return retMap;
	}
	
	public static String Clob2String(Clob clob) {// Clob转换成String 的方法
		  String content = null;
		  StringBuffer stringBuf = new StringBuffer();
		  try {
		   int length = 0;
		   Reader inStream = clob.getCharacterStream(); // 取得大字侧段对象数据输出流
		   char[] buffer = new char[10];
		   while ((length = inStream.read(buffer)) != -1) // 读取数据库 //每10个10个读取
		   {
		    for (int i = 0; i < length; i++) {
		     stringBuf.append(buffer[i]);
		    }
		   }

		   inStream.close();
		   content = stringBuf.toString();
		  } catch (Exception ex) {
		   logger.info("ClobUtil.Clob2String:" + ex.getMessage());
		  }
		  return content;
		 }

	/**
	 * 执行delete/update/insert等语句
	 * 
	 * @param sql
	 *            执行语句
	 * @param dbName
	 *            连接名称
	 * @param params
	 *            传入参数
	 * @return
	 */
	public int execUpdateForMap(String sql, Map params) throws SQLException,
			Exception {
		PreparedStatement pst = null;
		int retInt = 0;
		try {
			getPriConn();
			sql = this.replaceOptionSqlByParam(sql, params);
			sql = this.replaceSqlByParam(sql, params);
			pst = conn.prepareStatement(sql);
			retInt = pst.executeUpdate();
			if (flag) {
				logger.debug("提交事务....................");
				conn.commit();
			}
		} catch (SQLException e) {
			logger.debug("执行更新出错：" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			logger.debug("执行更新出错：" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			DAOUtils.closeStatement(pst, this);
			pst=null;
			closeConnection();
		}
		return retInt;
	}

	/**
	 * 找出可选SQL语句，如果里面的变量为空，去掉此段可选SQL语句
	 * 
	 * @param queryContent
	 * @param params
	 * @return
	 */
	public String replaceOptionSqlByParam(String sql, Map params) {
		String sqlContent = sql.toString();
		// 用正则解析出变量
		Pattern p = Pattern.compile(IConst.FIND_SQL_OPTIONAL_REXP);
		Matcher m = p.matcher(sql);
		while (m.find()) {
			int start = m.start();
			int end = m.end();
			String optionSql = sql.substring(start, end);
			boolean delete = false;
			Pattern pBuf = Pattern.compile(IConst.FIND_ALL_QUERY_VARIABLE_REXP);
			Matcher mBuf = pBuf.matcher(optionSql);
			while (mBuf.find()) {
				int startBuf = mBuf.start();
				int endBuf = mBuf.end();
				String variable = optionSql.substring(startBuf, endBuf);
				// 去掉首尾的多余字符串
				variable = variable.substring(1, variable.length() - 1);
				if (variable.startsWith("%") || variable.endsWith("%"))
					variable = variable.replace('%', ' ');
				// 判断里面的变量是否存在，不存在则不需要这个查询语句
				if (params.get(variable.toLowerCase()) == null
						|| "".equals(params.get(variable.toLowerCase()))) {
					delete = true;
					break;
				}
			}
			// 删除可选SQL语句
			if (delete) {
				sqlContent = sqlContent.replaceAll(optionSql, "");
			} else {
				sqlContent = sqlContent.replaceAll(optionSql, optionSql
						.substring(1, optionSql.length() - 1));
			}
		}
		return sqlContent;
	}

	/**
	 * 根据将SQL语句中#{var1}替换成对应变量，直接替换，不使用？预编译字符
	 * 
	 * @param sql
	 * @param params
	 */
	public String replaceSqlByParam(String sql, Map params) throws Exception {
		String queryContentBuf = sql.toString();
		// 用正则解析出变量
		try {
			Pattern p = Pattern.compile(IConst.FIND_ALL_QUERY_VARIABLE_REXP);
			Matcher m = p.matcher(sql);
			while (m.find()) {
				int start = m.start();
				int end = m.end();
				String variable = sql.substring(start, end);
				// 去掉首尾的多余字符串
				variable = variable.substring(2, variable.length() - 1);
				String var = variable;
				if (variable.startsWith("%") || variable.endsWith("%"))
					var = variable.replaceAll("%", "");

				if (!params.containsKey(var.toLowerCase())) {
					throw new RuntimeException("未能找到入参变量：" + var);
				}
				// 直接替换字符
				Object object = params.get(var.toLowerCase());
				String value = object == null ? "null" : object.toString();
				if (!"null".equals(value)) {
					value = "'" + value + "'";
				}
				queryContentBuf = queryContentBuf.replaceAll("#\\u007B"
						+ variable + "}", value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return queryContentBuf;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param conn
	 */
	private void closeConnection() {
		try {
			if (conn != null && !conn.isClosed() && flag) {
				conn.close();
				conn = null;
			} else {
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed() && flag) {
					conn.close();
					conn = null;
				} else {
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
				conn = null;
			} else {
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
					conn = null;
				} else {
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
    
	private static class SimplePreparedStatementCreator implements PreparedStatementCreator, SqlProvider {
		private final String sql;
		public SimplePreparedStatementCreator(String sql) {
			this.sql = sql;
		}
		@Override
		public PreparedStatement createPreparedStatement(Connection con)
				throws SQLException {
			return con.prepareStatement(this.sql);
		}
		@Override
		public String getSql() {
			return sql;
		}
	}
	public int update(String sql, final PreparedStatementSetter pss)
			throws DataAccessException {
		return update(new SimplePreparedStatementCreator(sql), pss);
	}
	private static String getSql(Object sqlProvider) {
		if (sqlProvider instanceof SqlProvider) {
			return ((SqlProvider) sqlProvider).getSql();
		} else {
			return null;
		}
	}
	protected int update(final PreparedStatementCreator psc,
			final PreparedStatementSetter pss) throws DataAccessException {
		if (logger.isDebugEnabled()) {
			String sql = getSql(psc);
			logger.debug("Executing SQL update"
					+ (sql != null ? " [" + sql + "]" : ""));
		}
		Integer result = (Integer) execute(psc,
				new PreparedStatementCallback() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException {
						try {
							if (pss != null) {
								pss.setValues(ps);
							}
							int rows = ps.executeUpdate();
							if (logger.isDebugEnabled()) {
								logger.debug("SQL update affected " + rows
										+ " rows");
							}
							return new Integer(rows);
						} finally {
							if (pss instanceof ParameterDisposer) {
								((ParameterDisposer) pss).cleanupParameters();
							}
						}
					}
				});
		return result.intValue();
	}
	
	private Object execute(PreparedStatementCreator psc,
			PreparedStatementCallback action) throws DataAccessException {
		PreparedStatement ps = null;
		Connection connNew =null;
		try {
			//getPriConn(); 
			jdbcTemplate=SpringContextHolder.getBean("jdbcTemplate");
			connNew = DataSourceUtils.getConnection(this.jdbcTemplate.getDataSource());
			ps = psc.createPreparedStatement(connNew);
			applyStatementSettings(ps);
			PreparedStatement psToUse = ps;
			Object result = action.doInPreparedStatement(psToUse);
			SQLWarning warning = ps.getWarnings();
			throwExceptionOnWarningIfNotIgnoringWarnings(warning);
			if(result!=null&&result instanceof Integer){
				if(Integer.parseInt(result.toString())>0 && flag){
					connNew.commit();
				}
			}
			return result;
		} catch (SQLException ex) {
			if (psc instanceof ParameterDisposer) {
				((ParameterDisposer) psc).cleanupParameters();
			}
			String sql = getSql(psc);
			throw new UncategorizedSQLException("PreparedStatementCallback",sql, ex);
		} finally {
			if (psc instanceof ParameterDisposer) {
				((ParameterDisposer) psc).cleanupParameters();
			}
			psc = null;
			ps = null;
			//closeConnection();
			if (connNew != null) {
				try {
					DataSourceUtils.doReleaseConnection(connNew, this.jdbcTemplate.getDataSource());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	protected void applyStatementSettings(Statement stmt) throws SQLException {
		if (getFetchSize() > 0) {
			stmt.setFetchSize(getFetchSize());
		}
		if (getMaxRows() > 0) {
			stmt.setMaxRows(getMaxRows());
		}
	}

	private void throwExceptionOnWarningIfNotIgnoringWarnings(SQLWarning warning)
			throws SQLWarningException {
		if (warning != null) {
			if (isIgnoreWarnings()) {
				logger.warn("SQLWarning ignored: " + warning);
			} else {
				throw new SQLWarningException("Warning not ignored", warning);
			}
		}
	}
}

