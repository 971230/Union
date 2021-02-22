package com.ztesoft.net.eop.sdk.utils;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.ztesoft.net.framework.database.DBRuntimeException;
import com.ztesoft.net.framework.util.StringUtil;

/**
 * ��ݿ�������
 * 
 * @author hzcl_sky gong.zhiwei
 * 
 */
public class SqlExe {

	// public Connection conn;
	private static Logger logger = Logger.getLogger(SqlExe.class);
	/**
	 * �ж��Ƿ�ر�l��
	 */
	private boolean flag = true;

	public SqlExe() {
	}

	/**
	 * ��ѯ����String
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public String queryForString(String sql, Connection conn) throws Exception,
			SQLException {
		String retMsg = "";
		if (conn == null || conn.isClosed()) {
			throw new RuntimeException("t��Ϊ�ջ���l���ѹر�");
		}
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
				rs = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
		return retMsg;
	}

	/**
	 * ��ѯ����String
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public String queryForString(Connection conn, String sql, String param)
			throws Exception, SQLException {
		String retMsg = "";
		if (conn == null || conn.isClosed()) {
			throw new RuntimeException("t��Ϊ�ջ���l���ѹر�");
		}
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
				rs = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
		return retMsg;
	}

	/**
	 * ��ѯ����String
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public String queryForString(Connection conn, String sql, String[] params)
			throws Exception, SQLException {
		String retMsg = "";
		if (conn == null || conn.isClosed()) {
			throw new RuntimeException("t��Ϊ�ջ���l���ѹر�");
		}
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
				rs = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
		return retMsg;
	}

	/**
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public HashMap<String,Object> queryForMap(Connection conn, String sql, String param)
			throws Exception, SQLException {
		if (conn == null || conn.isClosed()) {
		}
		HashMap<String,Object> retMap = new HashMap<String,Object>();
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
				rs = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
		return retMap;
	}

	/**
	 * ��ѯ����Map
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public HashMap queryForMap(Connection conn, String sql) throws Exception,
			SQLException {
		if (conn == null || conn.isClosed()) {
		}
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
				rs = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
		return retMap;
	}
	public void insert(Connection conn, String table, Map fields) {
		String sql = "";
		try {
			Assert.hasText(table, "表名不能为空");
			Assert.notEmpty(fields, "字段不能为空");
			Object[] cols = fields.keySet().toArray();
			Object[] values = new Object[cols.length];
			for (int i = 0; i < cols.length; i++) {
				values[i] = fields.get(cols[i]);
			}
			sql = "INSERT INTO " + table + " ("
					+ StringUtil.implode(", ", cols);
			sql = sql + ") VALUES (" + StringUtil.implodeValue(", ", values)
					+ ")";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.executeUpdate();
		} catch (Exception e) {
			throw new DBRuntimeException(e, sql);
		}
	}
	/**
	 * ��ѯ����Map
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public HashMap queryForMap(Connection conn, String sql, List params)
			throws Exception, SQLException {
		if (conn == null || conn.isClosed()) {
			throw new RuntimeException("t��Ϊ�ջ���l���ѹر�");
		}
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
				rs = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
		return retMap;
	}

	/**
	 * ��ѯ����Map
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public HashMap queryForMap(Connection conn, String sql, String[] args)
			throws Exception, SQLException {
		if (conn == null || conn.isClosed()) {
			throw new RuntimeException("t��Ϊ�ջ���l���ѹر�");
		}
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
				rs = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
		return retMap;
	}

	/**
	 * ��ѯ����list list��װ����Map
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public List queryForList(Connection conn, String sql) throws Exception,
			SQLException {
		if (conn == null || conn.isClosed()) {
			throw new RuntimeException("t��Ϊ�ջ���l���ѹر�");
		}
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
				rs = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * ��ѯ����list list��װ����Map
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public List queryForList(Connection conn, String sql, String[] args)
			throws Exception, SQLException {
		if (conn == null || conn.isClosed()) {
			throw new RuntimeException("t��Ϊ�ջ���l���ѹر�");
		}
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
				rs = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * ��ѯ����list list��װ����Map
	 * 
	 * @param conn
	 *            ��ݿ�l��
	 * @param sql
	 * @param args
	 *            һ���ַ����
	 * @return list��װ����Map
	 * @throws Exception
	 * @throws SQLException
	 */
	public List queryForList(Connection conn, String sql, String param)
			throws Exception, SQLException {
		if (conn == null || conn.isClosed()) {
			throw new RuntimeException("t��Ϊ�ջ���l���ѹر�");
		}
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
				rs = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * ����
	 * 
	 * @param sql
	 * @param list
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public boolean execUpdateForList(Connection conn, String sql, List list)
			throws Exception, SQLException {
		if (conn == null || conn.isClosed()) {
			throw new RuntimeException("t��Ϊ�ջ���l���ѹر�");
		}
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			for (int i = 0; i < list.size(); i++) {
				pst.setObject(i + 1, list.get(i));
			}
			boolean retFlag = pst.executeUpdate() > 0 ? true : false;
			if (flag) {
				logger.info("�ύ����.........");
				conn.commit();
			}
			return retFlag;
		} catch (SQLException e) {
			logger.debug("ִ�и��³�?" + e.getMessage());
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * ����
	 * 
	 * @param sql
	 * @param list
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public boolean execUpdateForList(Connection conn, String sql, String param)
			throws Exception, SQLException {
		if (conn == null || conn.isClosed()) {
			throw new RuntimeException("t��Ϊ�ջ���l���ѹر�");
		}
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setObject(1, param);
			boolean retFlag = pst.executeUpdate() > 0 ? true : false;
			if (flag) {
				logger.info("�ύ����.........");
				conn.commit();
			}
			return retFlag;
		} catch (SQLException e) {
			logger.debug("ִ�и��³�?" + e.getMessage());
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * ����
	 * 
	 * @param sql
	 * @param list
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public boolean execUpdateForList(Connection conn, String sql)
			throws Exception, SQLException {
		if (conn == null || conn.isClosed()) {
			throw new RuntimeException("t��Ϊ�ջ���l���ѹر�");
		}
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			boolean retFlag = pst.executeUpdate() > 0 ? true : false;
			if (flag) {
				logger.info("�ύ����.........");
				conn.commit();
			}
			return retFlag;
		} catch (SQLException e) {
			logger.debug("ִ�и��³�?" + e.getMessage());
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * ����
	 * 
	 * @param sql
	 * @param list
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public boolean execUpdateForList(Connection conn, String sql,
			String[] params) throws Exception, SQLException {
		if (conn == null || conn.isClosed()) {
			throw new RuntimeException("t��Ϊ�ջ���l���ѹر�");
		}
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pst.setString(i + 1, params[i]);
			}
			boolean retFlag = pst.executeUpdate() > 0 ? true : false;
			if (flag) {
				logger.info("�ύ����.........");
				conn.commit();
			}
			return retFlag;
		} catch (SQLException e) {
			logger.debug("ִ�и��³�?" + e.getMessage());
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
	}

	/**
	 * ��ִ�� ����List��װ��List
	 * 
	 * @param sql
	 * @param list
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public boolean execBatchForListExt(Connection conn, String sql, List list)
			throws Exception, SQLException {
		boolean flag = true;
		if (conn == null || conn.isClosed()) {
			throw new RuntimeException("t��Ϊ�ջ���l���ѹر�");
		}
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
				logger.info("�ύ����.........");
				conn.commit();
			}
		} catch (SQLException e) {
			logger.debug("ִ�и��³�?" + e.getMessage());
			flag = false;
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
		return flag;
	}

	/**
	 * ������
	 * 
	 * @param sql
	 * @param list
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public boolean execBatchForList(Connection conn, String sql, List list)
			throws Exception, SQLException {
		boolean flag = true;
		if (conn == null || conn.isClosed()) {
			throw new RuntimeException("t��Ϊ�ջ���l���ѹر�");
		}
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
				logger.info("�ύ����.........");
				conn.commit();
			}
		} catch (SQLException e) {
			logger.debug("ִ�и��³�?" + e.getMessage());
			flag = false;
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			if (pst != null) {
				pst.close();
				pst = null;
			}
		}
		return flag;
	}

	public static HashMap<String,Object> convertToMap(ResultSet rs) throws SQLException {
		HashMap<String,Object> retMap = new HashMap<String,Object>();
		ResultSetMetaData meatData = rs.getMetaData();
		for (int i = 1; i <= meatData.getColumnCount(); i++) {
			String name = meatData.getColumnLabel(i).toLowerCase();
			int type = meatData.getColumnType(i);
			if (Types.TIME == type || Types.TIMESTAMP == type) {
				String value = DateUtil.toString(rs.getTimestamp(name),"yyyy-mm-dd hh24:hi:ss");
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
			} else {
				Object value = rs.getObject(name);
				retMap.put(name, value);
			}
		}
		return retMap;
	}


	/**
	 * �ر���ݿ�l��
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
}
