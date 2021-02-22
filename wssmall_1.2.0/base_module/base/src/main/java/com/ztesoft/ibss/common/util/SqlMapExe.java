package com.ztesoft.ibss.common.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.ibss.common.dao.DAOSQLUtils;
import com.ztesoft.ibss.common.dao.DAOUtils;

/**
 * 初始化需要传入Connection，才可用于各数据库操作
 * 
 * @author Reason
 * @version Created Aug 23, 2011
 */
public class SqlMapExe implements java.io.Serializable {
	private static final long serialVersionUID = -5365048015191186792L;

	private static Logger logger = Logger.getLogger(SqlMapExe.class);

	private Connection connection;

	public SqlMapExe(Connection conn) {
		this.connection = conn;
	}

	public Connection getConnection() {
		return connection;
	}

	/**
	 * 返回制定条数num的查询结果列表
	 * 
	 * @param sql
	 * @param sqlParams
	 * @param num
	 * @return
	 */
	public List queryForStringListEx(String sql, String[] sqlParams, int num) {
		List list = new ArrayList();
		Connection dbConnection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			dbConnection = connection;
			String sqlStr = DAOSQLUtils.getFilterSQL(sql);
			stmt = dbConnection.prepareStatement(sqlStr);
			logger.debug("================>sql:" + sql);
			logger.debug("================>para:"
					+ StringUtils.stringToList(sqlParams));
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
				stmt.setString(i + 1, sqlParams[i]);
			}
			result = stmt.executeQuery();
			if (result.next()) {
				for (int i = 1; sqlParams != null && i < num + 1; i++) {
					list.add(DAOUtils.trimStr(result.getString(i)));
				}
			}
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql
					+ "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);
			

		}
		return list;
	}

	/**
	 * 根据sql和参数得到查询结果列表
	 * 
	 * @param sql
	 * @param sqlParams
	 * @return
	 */
	public List queryForStringListEx(String sql, String[] sqlParams) {
		List list = new ArrayList();
		Connection dbConnection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			dbConnection = connection;
			String sqlStr = DAOSQLUtils.getFilterSQL(sql);
			stmt = dbConnection.prepareStatement(sqlStr);
			logger.debug("================>sql:" + sql);
			logger.debug("================>para:"
					+ StringUtils.stringToList(sqlParams));
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
				stmt.setString(i + 1, sqlParams[i]);
			}
			result = stmt.executeQuery();
			while (result.next()) {
				list.add(DAOUtils.trimStr(result.getString(1)));
			}
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql
					+ "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return list;
	}

	/**
	 * 通过sql和参数查询单个值
	 * 
	 * @param sql
	 * @param param
	 * @return
	 */
	public String querySingleValue(String sql, String param) {
		Connection dbConnection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String returnValue = "";

		try {
			dbConnection = connection;

			stmt = dbConnection.prepareStatement(DAOSQLUtils.getFilterSQL(sql,
					DAOSQLUtils.CRM_DB));
			logger.debug("================>sql:" + sql);
			logger.debug("================>para:" + param);
			if (param != null) {
				stmt.setString(1, param);
			}

			result = stmt.executeQuery();
			if (result.next()) {
				returnValue = DAOUtils.trimStr(result.getString(1));
			}
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql
					+ "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return returnValue;
	}

	/**
	 * 根据sql和多个参数得到单个值
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public String querySingleValue(String sql, String params[]) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String returnValue = "";

		try {
			conn = connection;
			if (sql.indexOf("dual") > -1 || sql.indexOf("DUAL") > -1) {
				stmt = conn.prepareStatement(sql);
			} else {
				stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql,
						DAOSQLUtils.CRM_DB));
			}
			logger.debug("================>sql:" + sql);
			logger.debug("================>para:"
					+ StringUtils.stringToList(params));
			for (int i = 0; params != null && i < params.length; i++) {
				stmt.setString(i + 1, params[i]);
			}
			result = stmt.executeQuery();
			if (result.next()) {
				returnValue = DAOUtils.trimStr(result.getString(1));
			}
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql
					+ "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return returnValue;
	}

	public String querySingleValue(String sql, List params) {
		return querySingleValue(sql, getStrArrayFromList(params));
	}

	/**
	 * 根据条件查询得到一个map
	 * 
	 * @param sql
	 * @param param
	 * @return
	 * @throws DAOSystemException
	 */
	public Map queryMap(String sql, String[] param) throws DAOSystemException {
		Map map = new HashMap();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = connection;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql,
					DAOSQLUtils.CRM_DB));
			logger.debug("================>sql:" + sql);
			logger.debug("================>para:"
					+ StringUtils.stringToList(param));
			if (param != null && param.length > 0) {
				for (int i = 0; i < param.length; i++) {
					stmt.setString(i + 1, param[i]);
				}
			}

			rs = stmt.executeQuery();

			while (rs.next()) {
				map.put(rs.getString(1).toLowerCase(), rs.getString(2));
			}
			return map;
		} catch (SQLException se) {
			logger.info(se.toString());
			logger.info(sql);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);

		}

	}

	/**
	 * 得到map的列表
	 * 
	 * @param sql
	 * @param param
	 * @return
	 * @throws DAOSystemException
	 */
	public List<Map<String, String>> queryMapList(String sql, String[] param)
			throws DAOSystemException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = connection;
			logger.debug("================>sql:" + sql);
			logger.debug("================>para:"
					+ StringUtils.stringToList(param));
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql,
					DAOSQLUtils.CRM_DB));

			if (param != null && param.length > 0) {
				for (int i = 0; i < param.length; i++) {
					stmt.setString(i + 1, param[i]);
				}
			}
			rs = stmt.executeQuery();
			list = handle(rs);
		} catch (SQLException se) {
			logger.info(se.toString());
			logger.info(sql);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
		}
		return list;
	}

	/**
	 * 直接通过SQL 获取结果集，如果值为null则转换为""
	 * 
	 * @param sql
	 * @param sqlParams
	 * @return
	 * @throws DAOSystemException
	 */
	public List<Map<String, String>> queryForMapListBySql(String sql,
			String[] sqlParams) throws DAOSystemException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = connection;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql,
					DAOSQLUtils.CRM_DB));
			logger.debug("================>sql:" + sql);
			logger.debug("================>para:"
					+ StringUtils.stringToList(sqlParams));
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
				stmt.setString(i + 1, sqlParams[i]);
			}
			rs = stmt.executeQuery();

			list = handle(rs);
		} catch (SQLException se) {
			logger.info(se.toString());
			logger.info(sql);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return list;
	}

	/**
	 * 直接通过SQL 获取结果集，如果值为null则转换为""
	 * 
	 * @param sql
	 * @param sqlParams
	 * @return
	 * @throws DAOSystemException
	 */
	public List queryForMapUpperListBySql(String sql, String[] sqlParams)
			throws DAOSystemException {

		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = connection;

			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql,
					DAOSQLUtils.CRM_DB));
			logger.debug("================>sql:" + sql);
			logger.debug("================>para:"
					+ StringUtils.stringToList(sqlParams));
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
				stmt.setString(i + 1, sqlParams[i]);
			}
			rs = stmt.executeQuery();

			list = handleUpper(rs);
		} catch (SQLException se) {
			logger.info(se.toString());
			logger.info(sql);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return list;
	}

	// 直接通过SQL 获取结果集
	public List queryForMapListBySql(String sql, List sqlParams)
			throws DAOSystemException {

		return queryForMapListBySql(sql, getStrArrayFromList(sqlParams));
	}

	public List queryForUpperMapListBySql(String sql, List sqlParams)
			throws DAOSystemException {

		return queryForMapUpperListBySql(sql, getStrArrayFromList(sqlParams));
	}

	public static void main(String[] args) {
		List ls = new ArrayList();
		ls.add("10A");
		ls.add("10B");
		ls.add("10C");
		Object[] ret = ls.toArray();
		logger.info(ret);
	}

	/**
	 * 查询字符串list
	 * 
	 * @param sql
	 * @param sqlParams
	 * @return
	 */
	public List queryForStringList(String sql, String[] sqlParams) {
		List list = new ArrayList();
		Connection dbConnection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			dbConnection = connection;
			String sqlStr = DAOSQLUtils.getFilterSQL(sql);
			stmt = dbConnection.prepareStatement(sqlStr);
			logger.debug("================>sql:" + sql);
			logger.debug("================>para:"
					+ StringUtils.stringToList(sqlParams));
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
				stmt.setString(i + 1, sqlParams[i]);
			}
			result = stmt.executeQuery();
			while (result.next()) {
				list.add(DAOUtils.trimStr(result.getString(1)));
			}
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql
					+ "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return list;
	}

	/**
	 * 查询行map
	 * 
	 * @param sql
	 * @param sqlParams
	 * @return
	 * @throws DAOSystemException
	 */
	public Map queryRowsForMap(String sql, String[] sqlParams)
			throws DAOSystemException {

		Map map = new HashMap();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = connection;

			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql,
					DAOSQLUtils.CRM_DB));
			logger.debug("================>sql:" + sql);
			logger.debug("================>para:"
					+ StringUtils.stringToList(sqlParams));
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {

				stmt.setString(i + 1, sqlParams[i]);
			}
			rs = stmt.executeQuery();

			while (rs.next()) {

				map.put(DAOUtils.trimStr(rs.getString(1)), DAOUtils.trimStr(rs
						.getString(2)));
			}

			return map;
		} catch (SQLException se) {
			logger.info(se.toString());
			logger.info(sql);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);

		}

	}

	/**
	 * 执行sql更新
	 * 
	 * @param sql
	 * @return
	 * @throws DAOSystemException
	 */
	public int executeUpdate(String sql) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = connection;
			logger.debug("================>sql:" + sql);
			logger.debug("================>para:");
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));

			return stmt.executeUpdate();

		} catch (SQLException se) {
			logger.info(sql);
			throw new DAOSystemException("SQLException while update sql:\n"
					+ sql.toString(), se);
		} finally {
			DAOUtils.closeStatement(stmt, this);

		}
	}

	/**
	 * 根据条件更新数据:条件全部是字符串
	 * 
	 * @param sql
	 * @param sqlParams
	 * @return
	 * @throws DAOSystemException
	 */
	public int excuteUpdate(String sql, List sqlParams)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = connection;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));
			logger.debug("================>sql:" + sql);
			logger.debug("================>para:" + sqlParams);
			for (int i = 0; i < sqlParams.size(); i++) {
				stmt.setString(i + 1, (String) sqlParams.get(i));
			}
			return stmt.executeUpdate();
		} catch (SQLException se) {
			logger.info(sql);
			throw new DAOSystemException("SQLException while update sql:\n"
					+ sql.toString(), se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
		}
	}

	/**
	 * 根据条件更新数据:按照条件类型设置参数
	 * 
	 * @param sql
	 * @param sqlParams
	 * @return
	 * @throws DAOSystemException
	 */
	public int excuteUpdate2(String sql, List sqlParams)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = connection;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));
			for (int i = 0; i < sqlParams.size(); i++) {
				setObject2Statement(sqlParams.get(i), stmt, i + 1);
			}
			return stmt.executeUpdate();
		} catch (SQLException se) {
			logger.info(sql);
			throw new DAOSystemException("SQLException while update sql:\n"
					+ sql.toString(), se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
		}
	}

	public void setObject2Statement(Object o, PreparedStatement s, int i)
			throws SQLException {
		if (isDateType(o)) {
			long time = ((java.sql.Date) o).getTime();
			s.setTimestamp(i, new Timestamp(time));
		} else if (isTimestampType(o)) {
			s.setTimestamp(i, (java.sql.Timestamp) o);
		} else {
			if (o == null)
				s.setString(i, "");
			else
				s.setObject(i, o);
		}
	}

	public void excuteBatchUpdate(String sql, List sqlParams) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = connection;

			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));
			logger.debug("================>sql:" + sql);
			logger.debug("================>para:" + sqlParams);
			for (int i = 0; i < sqlParams.size(); i++) {
				List params = (List) sqlParams.get(i);
				for (int j = 0; j < params.size(); j++) {
					stmt.setString(j + 1, (String) params.get(j));
				}
				stmt.addBatch();
			}
			stmt.executeBatch();
		} catch (SQLException se) {
			logger.info(sql);
			throw new DAOSystemException("SQLException while update sql:\n"
					+ sql.toString(), se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
		}
	}

	/***************************************************************************
	 * 批量更新，支持日期类型
	 * 
	 * @param sql
	 * @param sqlParams
	 */
	public void excuteBatchUpdateSupportDate(String sql, List sqlParams) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {

			conn = connection;

			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));
			logger.debug("================>sql:" + sql);
			logger.debug("================>para:" + sqlParams);
			for (int i = 0; i < sqlParams.size(); i++) {
				List params = (List) sqlParams.get(i);
				for (int j = 0; j < params.size(); j++) {

					if (isDateType(params.get(j))) {
						stmt.setDate(j + 1, (java.sql.Date) params.get(j));
					} else if (isTimestampType(params.get(j))) {
						stmt.setTimestamp(j + 1, (java.sql.Timestamp) params
								.get(j));
					} else {
						if (params.get(j) == null)
							stmt.setString(j + 1, "");

						else
							stmt.setObject(j + 1, params.get(j));
					}
				}
				stmt.addBatch();
			}
			stmt.executeBatch();
		} catch (SQLException se) {
			logger.info(sql);
			throw new DAOSystemException("SQLException while update sql:\n"
					+ sql.toString(), se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
		}
	}

	public static boolean isDateType(Object o) {
		return o != null
				&& ("java.util.Date".equals(o.getClass().getName()) || "java.sql.Date"
						.equals(o.getClass().getName()));
	}

	public static boolean isTimestampType(Object o) {
		return o != null && "java.sql.Timestamp".equals(o.getClass().getName());
	}

	/**
	 * 根据条件更新数据。
	 * 
	 * @param sql
	 * @param sqlParams
	 * @return
	 * @throws DAOSystemException
	 */
	public int excuteUpdate(String sql, String[] sqlParams)
			throws DAOSystemException {
		List<String> params = new ArrayList<String>();
		for (int i = 0; i < sqlParams.length; i++) {
			params.add(sqlParams[i]);
		}
		return excuteUpdate(sql, params);
	}

	public String querySingleValue(String sql) {
		Connection dbConnection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String returnValue = "";
		try {
			dbConnection = connection;
			String sqlStr = "";
			if (sql.indexOf("dual") > -1 || sql.indexOf("DUAL") > -1) {
				sqlStr = sql;
			} else {
				sqlStr = DAOSQLUtils.getFilterSQL(sql);
			}
			logger.debug("================>sql:" + sql);
			logger.debug("================>para:");
			stmt = dbConnection.prepareStatement(sqlStr);
			result = stmt.executeQuery();
			if (result.next()) {
				returnValue = DAOUtils.trimStr(result.getString(1));
			}
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql
					+ "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);
		}
		return returnValue;
	}

	public List<String> queryForStringList(String sql) {
		List<String> list = new ArrayList<String>();
		Connection dbConnection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			dbConnection = connection;
			String sqlStr = DAOSQLUtils.getFilterSQL(sql);
			stmt = dbConnection.prepareStatement(sqlStr);
			logger.debug("================>sql:" + sql);
			logger.debug("================>para:");
			result = stmt.executeQuery();
			while (result.next()) {
				list.add(result.getString(1));
			}
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql
					+ "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return list;
	}

	public List<Map<String, String>> queryForMapList(String sql)
			throws DAOSystemException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = connection;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql,
					DAOSQLUtils.CRM_DB));
			logger.debug("================>sql:" + sql);
			logger.debug("================>para:");
			rs = stmt.executeQuery();

			list = handle(rs);
		} catch (SQLException se) {
			logger.info(se.toString());
			logger.info(sql);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return list;
	}

	public List<Map<String, String>> execForMapList(String sql,
			String[] sqlParams) throws DAOSystemException {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = connection;
			logger.debug(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));
			logger.debug("================>sql:" + sql);
			logger.debug("================>para:"
					+ StringUtils.stringToList(sqlParams));
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql,
					DAOSQLUtils.CRM_DB));
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
				stmt.setString(i + 1, sqlParams[i]);
			}
			rs = stmt.executeQuery();

			list = handle(rs);
		} catch (SQLException se) {
			logger.info(se.toString());
			logger.info(sql);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return list;
	}

	/**
	 * 根据SQL,参数，分页查询结果
	 * 
	 * @param String
	 *            sql，List params
	 * @return PageModel
	 * @throws Exception
	 */
	public PageModel queryPageModelResult(String sql, ArrayList params, int pi,
			int ps) {
		PageModel pageModel = new PageModel();

		String countSql = " select count(*) count from ( " + sql + " ) ";// 计算总记录数

		int totalCount = new Long(this.querySingleValue(countSql, params))
				.intValue();

		pageModel.setTotalCount(totalCount);

		if (totalCount == 0)
			return new PageModel();

		if (totalCount % ps > 0) {
			pageModel.setPageCount(totalCount / ps + 1);
		} else {
			pageModel.setPageCount(totalCount / ps);
		}

		// 边界的处理
		if (pi < 0) {
			pageModel.setPageIndex(1);
		} else if (pi > pageModel.getPageCount()) {
			pageModel.setPageIndex(pageModel.getPageCount());
		} else {
			pageModel.setPageIndex(pi);
		}

		if (ps < 0) {
			pageModel.setPageSize(totalCount);
		} else {
			pageModel.setPageSize(ps);
		}

		String queryResultSql = " select * from ( select mytable.*, rownum num from ( ";
		queryResultSql += sql;
		queryResultSql += " ) mytable )where num > ? and num <= ?";

		params.add(String.valueOf(ps * (pi - 1)));

		params.add(String.valueOf(ps * pi));

		List resultList = this.queryForMapListBySql(queryResultSql, params);

		pageModel.setList(resultList);

		return pageModel;

	}

	/**
	 * 根据List，生成分页查询结果
	 * 
	 * @author 肖镇涛 2012-2-23
	 * @param List
	 *            ls,int pi,int ps
	 * @return PageModel
	 * @throws Exception
	 */
	public static PageModel createPageModelResult(List ls, int pi, int ps) {
		PageModel pageModel = new PageModel();

		if (ls != null) {
			int totalCount = ls.size();
			pageModel.setTotalCount(totalCount);
			if (totalCount == 0)
				return new PageModel();
			if (totalCount % ps > 0) {
				pageModel.setPageCount(totalCount / ps + 1);
			} else {
				pageModel.setPageCount(totalCount / ps);
			}
			// 边界的处理
			if (pi < 0) {
				pageModel.setPageIndex(1);
			} else if (pi > pageModel.getPageCount()) {
				pageModel.setPageIndex(pageModel.getPageCount());
			} else {
				pageModel.setPageIndex(pi);
			}

			if (ps < 0) {
				pageModel.setPageSize(totalCount);
			} else {
				pageModel.setPageSize(ps);
			}

			int fromIndex = ps * (pi - 1);

			int toIndex = ps * pi <= totalCount ? ps * pi : totalCount;

			List resultList = ls.subList(fromIndex, toIndex);

			pageModel.setList(resultList);
		}
		return pageModel;

	}

	public List<Map<String, String>> getUpcaseKeyMapList(String sql,
			String[] sqlParams) throws DAOSystemException {
		return getMapList(sql, sqlParams, "T");
	}

	public List<Map<String, String>> getLowercaseKeyMapList(String sql,
			String[] sqlParams) throws DAOSystemException {
		return getMapList(sql, sqlParams, "F");
	}

	// 现在系统都是默认小写key，增加一个方法，用来选定MAP中是大写还是小写
	// T 大写 否则小写
	public List<Map<String, String>> getMapList(String sql, String[] sqlParams,
			String Upcaseflag) throws DAOSystemException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = connection;
			logger.debug(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));
			logger.debug("================>sql:" + sql);
			logger.debug("================>para:"
					+ StringUtils.stringToList(sqlParams));
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql,
					DAOSQLUtils.CRM_DB));
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
				stmt.setString(i + 1, sqlParams[i]);
			}
			rs = stmt.executeQuery();
			list = handle(rs);
			for (Map<String, String> map : list) {
				Map<String, String> cloneMap = new HashMap<String, String>();
				for (Entry<String, String> entry : map.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					key = "T".equals(Upcaseflag) ? key.toUpperCase() : key
							.toLowerCase();
					cloneMap.put(key, value);
				}
				retList.add(cloneMap);
			}
		} catch (SQLException se) {
			logger.info(se.toString());
			logger.info(sql);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return retList;
	}

	private List<Map<String, String>> handle(ResultSet rs) throws SQLException {
		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
		while (rs.next()) {
			results.add(this.populateDto(rs));
		}
		return results;
	}

	private List<Map<String, String>> handleUpper(ResultSet rs)
			throws SQLException {
		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
		while (rs.next()) {
			results.add(this.populateDtoUpper(rs));
		}
		return results;
	}

	private HashMap<String, String> populateDtoUpper(ResultSet rs)
			throws SQLException {
		HashMap<String, String> vo = new HashMap<String, String>();
		int fieldcount = rs.getMetaData().getColumnCount();
		String ColumnName = "";
		String ColumnTypeName = "";
		for (int i = 0; i < fieldcount; i++) {
			ColumnTypeName = rs.getMetaData().getColumnTypeName(i + 1);
			ColumnName = rs.getMetaData().getColumnName(i + 1).toLowerCase();
			if (ColumnTypeName.toLowerCase().equals("date")
					|| ColumnTypeName.toLowerCase().equals(
							"datetime year to second")) {
				String _dateVal = DAOUtils.getFormatedDateTime(rs
						.getTimestamp(ColumnName));
				if (_dateVal != null && !_dateVal.equals("")) {
					if (_dateVal.indexOf(".") >= 0) {
						String[] _lstDateVal = _dateVal.split(".");// 如果是2009-12-11
						// 00:00:00.0改成2009-12-11
						// 00:00:00
						vo.put(ColumnName.toUpperCase(), _lstDateVal[0]);
					} else {
						vo.put(ColumnName.toUpperCase(), _dateVal);
					}
				} else {
					vo.put(ColumnName.toUpperCase(), "");
				}
			} else {
				vo.put(ColumnName.toUpperCase(),
						rs.getString(ColumnName) == null ? "" : rs.getString(
								ColumnName).trim());
			}
		}
		return vo;
	}

	private HashMap<String, String> populateDto(ResultSet rs)
			throws SQLException {
		HashMap<String, String> vo = new HashMap<String, String>();
		int fieldcount = rs.getMetaData().getColumnCount();
		String columnName = "";
		String ColumnTypeName = "";
		for (int i = 0; i < fieldcount; i++) {
			ColumnTypeName = rs.getMetaData().getColumnTypeName(i + 1);
			columnName = rs.getMetaData().getColumnName(i + 1).toLowerCase();
			if (ColumnTypeName.toLowerCase().equals("date")
					|| ColumnTypeName.toLowerCase().equals(
							"datetime year to second")) {
				String _dateVal = DAOUtils.getFormatedDateTime(rs
						.getTimestamp(columnName));
				if (_dateVal != null && !_dateVal.equals("")) {
					if (_dateVal.indexOf(".") >= 0) {
						String[] _lstDateVal = _dateVal.split(".");// 如果是2009-12-11
						// 00:00:00.0改成2009-12-11
						// 00:00:00
						vo.put(columnName, _lstDateVal[0]);
					} else {
						vo.put(columnName, _dateVal);
					}
				} else {
					vo.put(columnName, "");
				}
			} else {
				vo.put(columnName, rs.getString(columnName) == null ? "" : rs
						.getString(columnName).trim());
			}
		}
		return vo;
	}

	private Map rowToMap(ResultSet rs) throws SQLException {
		Map result = new HashMap();
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();
		for (int i = 1; i <= cols; i++) {
			result.put(rsmd.getColumnName(i).toLowerCase(), rs.getString(i));
		}
		return result;
	}

	public Map queryMapBySql(String sql, String[] sqlParams)
			throws DAOSystemException {

		Map map = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = connection;

			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql,
					DAOSQLUtils.CRM_DB));
			logger.debug("================>sql:" + sql);
			logger.debug("================>para:"
					+ StringUtils.stringToList(sqlParams));
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
				stmt.setString(i + 1, sqlParams[i]);
			}
			rs = stmt.executeQuery();
			if (rs.next()) {
				map = this.rowToMap(rs);
			}

			return map;
		} catch (SQLException se) {
			logger.info(se.toString());
			logger.info(sql);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);

		}
	}

	public List callProc(String procName, List inParams, int[] outParams)
			throws SQLException {
		CallableStatement proc = null;

		int outParamIndex = (inParams == null ? 0 : inParams.size());
		int outParamLength = (outParams == null ? 0 : outParams.length);
		List result = (outParams == null || outParams.length == 0) ? null
				: new ArrayList();
		String callSQL = "";
		try {
			StringBuffer paramStr = new StringBuffer();

			// 设置绑定参数位置 ?
			wrapProcParam(paramStr, outParamIndex + outParamLength);

			callSQL = "{ call " + procName.toUpperCase() + "("
					+ paramStr.toString() + ")}";
			proc = connection.prepareCall(callSQL);
			// 设置输入参数
			if (inParams != null && !inParams.isEmpty()) {
				for (int i = 0, j = inParams.size(); i < j; i++) {
					Object o = inParams.get(i);
					setObject2Statement(o, proc, i + 1);
				}
			}
			// 设置输出参数
			if (outParams != null && outParams.length > 0) {
				for (int i = 0, j = outParams.length; i < j; i++) {
					proc.registerOutParameter(outParamIndex + i + 1,
							outParams[i]);
				}
			}

			proc.execute();

			// 组装输出结果
			if (result != null) {
				for (int i = 0, j = outParams.length; i < j; i++) {
					int dataType = outParams[i];
					Object o = getObject(proc, outParamIndex + i + 1,
							outParams[i]);
					result.add(o);
				}
			}
		} catch (Exception se) {
			se.printStackTrace();
			throw new DAOSystemException("SQLException while execProc:"
					+ callSQL + "\n", se);
		} finally {
			proc.close();
		}
		return result;
	}

	private void wrapProcParam(StringBuffer sb, int paramSize) {
		for (int i = 0, j = paramSize; i < j; i++) {
			if (i == j - 1) {
				sb.append("?");
			} else {
				sb.append("?,");
			}
		}
	}

	private Object getObject(CallableStatement s, int i, int dataType)
			throws SQLException {
		if (dataType == Types.DATE)
			return s.getDate(i);

		if (dataType == Types.TIMESTAMP)
			return s.getTimestamp(i);
		if (dataType == Types.INTEGER)
			return new Integer(s.getInt(i));
		if (dataType == Types.DOUBLE)
			return new Double(s.getDouble(i));
		return s.getString(i);

	}

	private String[] getStrArrayFromList(List ls) {
		String[] ret = new String[ls.size()];
		for (int i = 0; i < ls.size(); i++) {
			ret[i] = (String) ls.get(i);
		}
		return ret;
	}

}
