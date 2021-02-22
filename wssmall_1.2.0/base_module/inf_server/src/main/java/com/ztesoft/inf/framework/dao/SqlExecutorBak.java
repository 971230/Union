package com.ztesoft.inf.framework.dao;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.SQLWarningException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultReader;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.ResultSetSupportingSqlParameter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultReader;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlProvider;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.SqlRowSetResultSetExtractor;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.inf.framework.utils.CollectionUtils;

public class SqlExecutorBak {

	private boolean useStrValue;
	private boolean ignoreWarnings = true;
	private int fetchSize = 0;
	private int maxRows = 0;
	private Logger logger = Logger.getLogger(SqlExecutorBak.class);

	public SqlExecutorBak(String dataSourceName) {
		this(dataSourceName, false);
	}
	private Connection conn;
	public SqlExecutorBak(Connection conn) {
		this.conn=conn;
	}

	public SqlExecutorBak(String dataSourceName, boolean useStrValue) {
		this.useStrValue = useStrValue;
	}

	public void setIgnoreWarnings(boolean ignoreWarnings) {
		this.ignoreWarnings = ignoreWarnings;
	}

	public boolean isIgnoreWarnings() {
		return ignoreWarnings;
	}

	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	public int getFetchSize() {
		return fetchSize;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	public int getMaxRows() {
		return maxRows;
	}



	public Object execute(StatementCallback action) throws DataAccessException {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			applyStatementSettings(stmt);
			Statement stmtToUse = stmt;
			Object result = action.doInStatement(stmtToUse);
			SQLWarning warning = stmt.getWarnings();
			throwExceptionOnWarningIfNotIgnoringWarnings(warning);
			return result;
		} catch (SQLException ex) {
			throw new UncategorizedSQLException("StatementCallback",
					getSql(action), ex);
		} finally {
			JdbcUtils.closeStatement(stmt);
			JdbcUtils.closeConnection(conn);
		}
	}


	public Object query(final String sql, final ResultSetExtractor rse)
			throws DataAccessException {
		if (sql == null) {
			throw new InvalidDataAccessApiUsageException("SQL must not be null");
		}
//		if (JdbcUtils.countParameterPlaceholders(sql, '?', "'\"") > 0) {
//			throw new InvalidDataAccessApiUsageException("Cannot execute ["
//					+ sql + "] as a static query: it contains bind variables");
//		}
		if (logger.isDebugEnabled()) {
			logger.debug("Executing SQL query [" + sql + "]");
		}
		class QueryStatementCallback implements StatementCallback, SqlProvider {
			@Override
			public Object doInStatement(Statement stmt) throws SQLException {
				ResultSet rs = null;
				try {
					rs = stmt.executeQuery(sql);
					ResultSet rsToUse = rs;
					return rse.extractData(rsToUse);
				} finally {
					JdbcUtils.closeResultSet(rs);
				}
			}

			@Override
			public String getSql() {
				return sql;
			}
		}
		return execute(new QueryStatementCallback());
	}

	public List query(String sql, RowCallbackHandler rch)
			throws DataAccessException {
		return (List) query(sql, new RowCallbackHandlerResultSetExtractor(rch));
	}

	public List query(String sql, RowMapper rowMapper)
			throws DataAccessException {
		return query(sql, new RowMapperResultReader(rowMapper));
	}

	public Map queryForMap(String sql) throws DataAccessException {
		return (Map) queryForObject(sql, new LowwerCaseColumnMapRowMapper(
				useStrValue));
	}

	public Object queryForObject(String sql, RowMapper rowMapper)
			throws DataAccessException {
		List results = query(sql, rowMapper);
		return CollectionUtils.unique(results);
	}

	public Object queryForObject(String sql, Class requiredType)
			throws DataAccessException {
		return queryForObject(sql, new SingleColumnRowMapper(
				requiredType));
	}

	public long queryForLong(String sql) throws DataAccessException {
		Number number = (Number) queryForObject(sql, Long.class);
		return (number != null ? number.longValue() : 0);
	}

	public int queryForInt(String sql) throws DataAccessException {
		Number number = (Number) queryForObject(sql, Integer.class);
		return (number != null ? number.intValue() : 0);
	}

	public List queryForList(String sql, Class elementType)
			throws DataAccessException {
		return query(sql, new SingleColumnRowMapper(elementType));
	}

	public List queryForList(String sql) throws DataAccessException {
		return query(sql, new LowwerCaseColumnMapRowMapper(useStrValue));
	}

	public SqlRowSet queryForRowSet(String sql) throws DataAccessException {
		return (SqlRowSet) query(sql, new SqlRowSetResultSetExtractor());
	}

	public int update(final String sql) throws DataAccessException {
		if (sql == null) {
			throw new InvalidDataAccessApiUsageException("SQL must not be null");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Executing SQL update [" + sql + "]");
		}
		class UpdateStatementCallback implements StatementCallback, SqlProvider {

			@Override
			public Object doInStatement(Statement stmt) throws SQLException {
				int rows = stmt.executeUpdate(sql);
				if (logger.isDebugEnabled()) {
					logger.debug("SQL update affected " + rows + " rows");
				}
				return new Integer(rows);
			}

			@Override
			public String getSql() {
				return sql;
			}
		}
		return ((Integer) execute(new UpdateStatementCallback())).intValue();
	}

	public int[] batchUpdate(final String[] sql) throws DataAccessException {
		if (sql == null) {
			throw new InvalidDataAccessApiUsageException("SQL must not be null");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Executing SQL batch update of " + sql.length
					+ " statements");
		}
		class BatchUpdateStatementCallback implements StatementCallback,
				SqlProvider {
			private String currSql;
			@Override
			public Object doInStatement(Statement stmt) throws SQLException,
					DataAccessException {
				int[] rowsAffected = new int[sql.length];
				if (JdbcUtils.supportsBatchUpdates(stmt.getConnection())) {
					for (int i = 0; i < sql.length; i++) {
						this.currSql = sql[i];
						stmt.addBatch(sql[i]);
					}
					rowsAffected = stmt.executeBatch();
				} else {
					for (int i = 0; i < sql.length; i++) {
						this.currSql = sql[i];
						if (!stmt.execute(sql[i])) {
							rowsAffected[i] = stmt.getUpdateCount();
						} else {
							throw new InvalidDataAccessApiUsageException(
									"Invalid batch SQL statement: " + sql[i]);
						}
					}
				}
				return rowsAffected;
			}

			@Override
			public String getSql() {
				return currSql;
			}
		}
		return (int[]) execute(new BatchUpdateStatementCallback());
	}

	// -------------------------------------------------------------------------
	// Methods dealing with prepared statements
	// -------------------------------------------------------------------------
	public Object execute(PreparedStatementCreator psc,
			PreparedStatementCallback action) throws DataAccessException {
		PreparedStatement ps = null;
		try {
			ps = psc.createPreparedStatement(conn);
			applyStatementSettings(ps);
			PreparedStatement psToUse = ps;
			Object result = action.doInPreparedStatement(psToUse);
			SQLWarning warning = ps.getWarnings();
			throwExceptionOnWarningIfNotIgnoringWarnings(warning);
			if(result!=null&&result instanceof Integer){
				if(Integer.parseInt(result.toString())>0){
					conn.commit();
				}
			}
			return result;
		} catch (SQLException ex) {
			if (psc instanceof ParameterDisposer) {
				((ParameterDisposer) psc).cleanupParameters();
			}
			String sql = getSql(psc);
			JdbcUtils.closeStatement(ps);
			throw new UncategorizedSQLException("PreparedStatementCallback",
					sql, ex);
		} finally {
			if (psc instanceof ParameterDisposer) {
				((ParameterDisposer) psc).cleanupParameters();
			}
			JdbcUtils.closeStatement(ps);
			psc = null;
			ps = null;
		}
	}

	public Object execute(final String sql, PreparedStatementCallback action)
			throws DataAccessException {
		return execute(new SimplePreparedStatementCreator(sql), action);
	}

	/**
	 * Query using a prepared statement, allowing for a PreparedStatementCreator
	 * and a PreparedStatementSetter. Most other query methods use this method,
	 * but application code will always work with either a creator or a setter.
	 * 
	 * @param psc
	 *            Callback handler that can create a PreparedStatement given a
	 *            Connection
	 * @param pss
	 *            object that knows how to set values on the prepared statement.
	 *            If this is null, the SQL will be assumed to contain no bind
	 *            parameters.
	 * @param rse
	 *            object that will extract results.
	 * @return an arbitrary result object, as returned by the ResultSetExtractor
	 * @throws DataAccessException
	 *             if there is any problem
	 */
	protected Object query(PreparedStatementCreator psc,
			final PreparedStatementSetter pss, final ResultSetExtractor rse)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
			String sql = getSql(psc);
			logger.debug("SQL:" + (sql != null ? " [" + sql + "]" : ""));
		}
		return execute(psc, new PreparedStatementCallback() {
			@Override
			public Object doInPreparedStatement(PreparedStatement ps)
					throws SQLException {
				ResultSet rs = null;
				try {
					if (pss != null) {
						pss.setValues(ps);
					}
					rs = ps.executeQuery();
					ResultSet rsToUse = rs;
					return rse.extractData(rsToUse);
				} finally {
					JdbcUtils.closeResultSet(rs);
					if (pss instanceof ParameterDisposer) {
						((ParameterDisposer) pss).cleanupParameters();
					}
				}
			}
		});
	}

	public Object query(PreparedStatementCreator psc, ResultSetExtractor rse)
			throws DataAccessException {
		return query(psc, null, rse);
	}

	public Object query(String sql, PreparedStatementSetter pss,
			final ResultSetExtractor rse) throws DataAccessException {
		if (sql == null) {
			throw new InvalidDataAccessApiUsageException("SQL may not be null");
		}
		return query(new SimplePreparedStatementCreator(sql), pss, rse);
	}

	public Object query(String sql, Object[] args, int[] argTypes,
			ResultSetExtractor rse) throws DataAccessException {
		return query(sql, new ArgTypePreparedStatementSetter(args, argTypes),
				rse);
	}

	public Object query(String sql, Object[] args, ResultSetExtractor rse)
			throws DataAccessException {
		return query(sql, new ArgPreparedStatementSetter(args), rse);
	}

	public List query(PreparedStatementCreator psc, RowCallbackHandler rch)
			throws DataAccessException {
		return (List) query(psc, new RowCallbackHandlerResultSetExtractor(rch));
	}

	public List query(String sql, PreparedStatementSetter pss,
			final RowCallbackHandler rch) throws DataAccessException {
		return (List) query(sql, pss, new RowCallbackHandlerResultSetExtractor(
				rch));
	}

	public List query(String sql, Object[] args, int[] argTypes,
			RowCallbackHandler rch) throws DataAccessException {
		return query(sql, new ArgTypePreparedStatementSetter(args, argTypes),
				rch);
	}

	public List query(String sql, Object[] args, RowCallbackHandler rch)
			throws DataAccessException {
		return query(sql, new ArgPreparedStatementSetter(args), rch);
	}

	public List query(PreparedStatementCreator psc, RowMapper rowMapper)
			throws DataAccessException {
		return query(psc, new RowMapperResultReader(rowMapper));
	}

	public List query(String sql, PreparedStatementSetter pss,
			RowMapper rowMapper) throws DataAccessException {
		return query(sql, pss, new RowMapperResultReader(rowMapper));
	}

	public List query(String sql, Object[] args, int[] argTypes,
			RowMapper rowMapper) throws DataAccessException {
		return query(sql, args, argTypes, new RowMapperResultReader(rowMapper));
	}

	public List query(String sql, Object[] args, RowMapper rowMapper)
			throws DataAccessException {
		return query(sql, args, new RowMapperResultReader(rowMapper));
	}

	public Object queryForObject(String sql, Object[] args, int[] argTypes,
			RowMapper rowMapper) throws DataAccessException {
		List results = query(sql, args, argTypes, new RowMapperResultReader(
				rowMapper, 1));
		return DataAccessUtils.uniqueResult(results);
	}

	public Object queryForObject(String sql, Object[] args, RowMapper rowMapper)
			throws DataAccessException {
		List results = query(sql, args, new RowMapperResultReader(rowMapper, 1));
		return DataAccessUtils.uniqueResult(results);
	}

	public Object queryForObject(String sql, Object[] args, int[] argTypes,
			Class requiredType) throws DataAccessException {
		return queryForObject(sql, args, argTypes, new SingleColumnRowMapper(
				requiredType));
	}

	public Object queryForObject(String sql, Class requiredType, Object[] args)
			throws DataAccessException {
		return queryForObject(sql, args, new SingleColumnRowMapper(
				requiredType));
	}

	public Map queryForMap(String sql, Object[] args, int[] argTypes)
			throws DataAccessException {
		return (Map) queryForObject(sql, args, argTypes,
				new LowwerCaseColumnMapRowMapper(useStrValue));
	}

	public Map queryForMap(String sql, Object[] args)
			throws DataAccessException {
		return (Map) queryForObject(sql, args,
				new LowwerCaseColumnMapRowMapper(useStrValue));
	}

	public long queryForLong(String sql, Object[] args, int[] argTypes)
			throws DataAccessException {
		Number number = (Number) queryForObject(sql, args, argTypes, Long.class);
		return (number != null ? number.longValue() : 0);
	}

	public long queryForLong(String sql, Object[] args)
			throws DataAccessException {
		Number number = (Number) queryForObject(sql, Long.class, args);
		return (number != null ? number.longValue() : 0);
	}

	public int queryForInt(String sql, Object[] args, int[] argTypes)
			throws DataAccessException {
		Number number = (Number) queryForObject(sql, args, argTypes,
				Integer.class);
		return (number != null ? number.intValue() : 0);
	}

	public int queryForInt(String sql, Object[] args)
			throws DataAccessException {
		Number number = (Number) queryForObject(sql, Integer.class, args);
		return (number != null ? number.intValue() : 0);
	}

	// public String queryForString(String sql, Object[] args, int[] argTypes)
	// throws DataAccessException {
	// return (String) queryForObject(sql, String.class, args, argTypes);
	// }
	public String queryForString(String sql, Object[] args)
			throws DataAccessException {
		return (String) queryForObject(sql, String.class, args);
	}

	public String queryForString(String sql) throws DataAccessException {
		return (String) queryForObject(sql, String.class, new Object[] {});
	}

	public List queryForList(String sql, Object[] args, int[] argTypes,
			Class elementType) throws DataAccessException {
		return query(sql, args, argTypes,
				new SingleColumnRowMapper(elementType));
	}

	public List queryForList(String sql, final Object[] args, Class elementType)
			throws DataAccessException {
		return query(sql, args, new SingleColumnRowMapper(elementType));
	}

	public List queryForList(String sql, Object[] args, int[] argTypes)
			throws DataAccessException {
		return query(sql, args, argTypes, new LowwerCaseColumnMapRowMapper(
				useStrValue));
	}

	public List queryForList(String sql, final Object[] args)
			throws DataAccessException {
		return query(sql, args, new LowwerCaseColumnMapRowMapper(useStrValue));
	}

	public SqlRowSet queryForRowSet(String sql, final Object[] args,
			int[] argTypes) throws DataAccessException {
		return (SqlRowSet) query(sql, args, argTypes,
				new SqlRowSetResultSetExtractor());
	}

	public SqlRowSet queryForRowSet(String sql, final Object[] args)
			throws DataAccessException {
		return (SqlRowSet) query(sql, args, new SqlRowSetResultSetExtractor());
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

	public int update(PreparedStatementCreator psc) throws DataAccessException {
		return update(psc, (PreparedStatementSetter) null);
	}

	public int update(final PreparedStatementCreator psc,
			final KeyHolder generatedKeyHolder) throws DataAccessException {
		if (logger.isDebugEnabled()) {
			String sql = getSql(psc);
			logger.debug("Executing SQL update and returning generated keys"
					+ (sql != null ? " [" + sql + "]" : ""));
		}
		Integer result = (Integer) execute(psc,
				new PreparedStatementCallback() {

					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException {
						int rows = ps.executeUpdate();
						List generatedKeys = generatedKeyHolder.getKeyList();
						generatedKeys.clear();
						ResultSet keys = ps.getGeneratedKeys();
						if (keys != null) {
							try {
								ColumnMapRowMapper rowMapper = new LowwerCaseColumnMapRowMapper(
										useStrValue);
								RowMapperResultReader resultReader = new RowMapperResultReader(
										rowMapper, 1);
								while (keys.next()) {
									resultReader.processRow(keys);
								}
								generatedKeys.addAll(resultReader.getResults());
							} finally {
								JdbcUtils.closeResultSet(keys);
							}
						}
						if (logger.isDebugEnabled()) {
							logger.debug("SQL update affected " + rows
									+ " rows and returned "
									+ generatedKeys.size() + " keys");
						}
						return new Integer(rows);
					}
				});
		return result.intValue();
	}

	public int update(String sql, final PreparedStatementSetter pss)
			throws DataAccessException {
		return update(new SimplePreparedStatementCreator(sql), pss);
	}

	public int update(String sql, final Object[] args, final int[] argTypes)
			throws DataAccessException {
		return update(sql, new ArgTypePreparedStatementSetter(args, argTypes));
	}

	public int update(String sql, final Object[] args)
			throws DataAccessException {
		return update(sql, new ArgPreparedStatementSetter(args));
	}
	public int[] batchUpdate(String sql, final BatchPreparedStatementSetter pss)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
			logger.debug("Executing SQL batch update [" + sql + "]");
		}
		return (int[]) execute(sql, new PreparedStatementCallback() {
			@Override
			public Object doInPreparedStatement(PreparedStatement ps)
					throws SQLException {
				try {
					int batchSize = pss.getBatchSize();
					if (JdbcUtils.supportsBatchUpdates(ps.getConnection())) {
						for (int i = 0; i < batchSize; i++) {
							pss.setValues(ps, i);
							ps.addBatch();
						}
						return ps.executeBatch();
					} else {
						int[] rowsAffected = new int[batchSize];
						for (int i = 0; i < batchSize; i++) {
							pss.setValues(ps, i);
							rowsAffected[i] = ps.executeUpdate();
						}
						return rowsAffected;
					}
				} finally {
					if (pss instanceof ParameterDisposer) {
						((ParameterDisposer) pss).cleanupParameters();
					}
				}
			}
		});
	}

	// -------------------------------------------------------------------------
	// Methods dealing with callable statements
	// -------------------------------------------------------------------------
	public Object execute(CallableStatementCreator csc,
			CallableStatementCallback action) throws DataAccessException {
		if (logger.isDebugEnabled()) {
			String sql = getSql(csc);
			logger.debug("Calling stored procedure"
					+ (sql != null ? " [" + sql + "]" : ""));
		}
		CallableStatement cs = null;
		try {
			Connection conToUse = conn;
			cs = csc.createCallableStatement(conToUse);
			applyStatementSettings(cs);
			CallableStatement csToUse = cs;
			Object result = action.doInCallableStatement(csToUse);
			SQLWarning warning = cs.getWarnings();
			throwExceptionOnWarningIfNotIgnoringWarnings(warning);
			return result;
		} catch (SQLException ex) {
			if (csc instanceof ParameterDisposer) {
				((ParameterDisposer) csc).cleanupParameters();
			}
			String sql = getSql(csc);
			JdbcUtils.closeStatement(cs);
			throw new UncategorizedSQLException("CallableStatementCallback",
					sql, ex);
		} finally {
			if (csc instanceof ParameterDisposer) {
				((ParameterDisposer) csc).cleanupParameters();
			}
			JdbcUtils.closeStatement(cs);
			csc = null;
			cs = null;
		}
	}

	public Object execute(final String callString,
			CallableStatementCallback action) throws DataAccessException {
		return execute(new SimpleCallableStatementCreator(callString), action);
	}

	public Map call(CallableStatementCreator csc, final List declaredParameters)
			throws DataAccessException {
		return (Map) execute(csc, new CallableStatementCallback() {

			@Override
			public Object doInCallableStatement(CallableStatement cs)
					throws SQLException {
				boolean retVal = cs.execute();
				int updateCount = cs.getUpdateCount();
				if (logger.isDebugEnabled()) {
					logger.debug("CallableStatement.execute() returned '"
							+ retVal + "'");
					logger.debug("CallableStatement.getUpdateCount() returned "
							+ updateCount);
				}
				Map returnedResults = new HashMap();
				if (retVal || updateCount != -1) {
					returnedResults.putAll(extractReturnedResultSets(cs,
							declaredParameters, updateCount));
				}
				returnedResults.putAll(extractOutputParameters(cs,
						declaredParameters));
				return returnedResults;
			}
		});
	}

	/**
	 * Extract returned ResultSets from the completed stored procedure.
	 * 
	 * @param cs
	 *            JDBC wrapper for the stored procedure
	 * @param parameters
	 *            Parameter list for the stored procedure
	 * @return Map that contains returned results
	 */
	protected Map extractReturnedResultSets(CallableStatement cs,
			List parameters, int updateCount) throws SQLException {
		Map returnedResults = new HashMap();
		int rsIndex = 0;
		boolean moreResults;
		do {
			if (updateCount == -1) {
				Object param = null;
				if (parameters != null && parameters.size() > rsIndex) {
					param = parameters.get(rsIndex);
				}
				if (param instanceof SqlReturnResultSet) {
					SqlReturnResultSet rsParam = (SqlReturnResultSet) param;
					returnedResults.putAll(processResultSet(cs.getResultSet(),
							rsParam));
				} else {
					logger
							.warn("Results returned from stored procedure but a corresponding "
									+ "SqlOutParameter/SqlReturnResultSet parameter was not declared");
				}
				rsIndex++;
			}
			moreResults = cs.getMoreResults();
			updateCount = cs.getUpdateCount();
			if (logger.isDebugEnabled()) {
				logger.debug("CallableStatement.getUpdateCount() returned "
						+ updateCount);
			}
		} while (moreResults || updateCount != -1);
		return returnedResults;
	}

	/**
	 * Extract output parameters from the completed stored procedure.
	 * 
	 * @param cs
	 *            JDBC wrapper for the stored procedure
	 * @param parameters
	 *            parameter list for the stored procedure
	 * @return parameters to the stored procedure
	 * @return Map that contains returned results
	 */
	protected Map extractOutputParameters(CallableStatement cs, List parameters)
			throws SQLException {
		Map returnedResults = new HashMap();
		int sqlColIndex = 1;
		for (int i = 0; i < parameters.size(); i++) {
			Object param = parameters.get(i);
			if (param instanceof SqlOutParameter) {
				SqlOutParameter outParam = (SqlOutParameter) param;
				if (outParam.isReturnTypeSupported()) {
					Object out = outParam.getSqlReturnType().getTypeValue(cs,
							sqlColIndex, outParam.getSqlType(),
							outParam.getTypeName());
					returnedResults.put(outParam.getName(), out);
				} else {
					Object out = cs.getObject(sqlColIndex);
					if (out instanceof ResultSet) {
						if (outParam.isResultSetSupported()) {
							returnedResults.putAll(processResultSet(
									(ResultSet) out, outParam));
						} else {
							logger
									.warn("ResultSet returned from stored procedure but a corresponding "
											+ "SqlOutParameter with a RowCallbackHandler was not declared");
							returnedResults.put(outParam.getName(),
									"ResultSet was returned but not processed");
						}
					} else {
						returnedResults.put(outParam.getName(), out);
					}
				}
			}
			if (!(param instanceof SqlReturnResultSet)) {
				sqlColIndex++;
			}
		}
		return returnedResults;
	}

	/**
	 * Process the given ResultSet from a stored procedure.
	 * 
	 * @param rs
	 *            the ResultSet to process
	 * @param param
	 *            the corresponding stored procedure parameter
	 * @return Map that contains returned results
	 */
	protected Map processResultSet(ResultSet rs,
			ResultSetSupportingSqlParameter param) throws SQLException {
		Map returnedResults = new HashMap();
//		try {
//			ResultSet rsToUse = rs;
//			if (param.isRowCallbackHandlerSupported()) {
//				// It's a RowCallbackHandler or RowMapper.
//				// We'll get a RowCallbackHandler to use in both cases.
//				RowCallbackHandler rch = param.getRowCallbackHandler();
//				(new RowCallbackHandlerResultSetExtractor(rch))
//						.extractData(rsToUse);
//				if (rch instanceof ResultReader) {
//					returnedResults.put(param.getName(), ((ResultReader) rch)
//							.getResults());
//				} else {
//					returnedResults
//							.put(param.getName(),
//									"ResultSet returned from stored procedure was processed.");
//				}
//			} else {
//				// It's a ResultSetExtractor - simply apply it.
//				Object result = param.getResultSetExtractor().extractData(
//						rsToUse);
//				returnedResults.put(param.getName(), result);
//			}
//		} finally {
//			JdbcUtils.closeResultSet(rs);
//		}
		return returnedResults;
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

	private static String getSql(Object sqlProvider) {
		if (sqlProvider instanceof SqlProvider) {
			return ((SqlProvider) sqlProvider).getSql();
		} else {
			return null;
		}
	}

	private static class SimplePreparedStatementCreator implements
			PreparedStatementCreator, SqlProvider {
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

	/**
	 * Simple adapter for CallableStatementCreator, allowing to use a plain SQL
	 * statement.
	 */
	private static class SimpleCallableStatementCreator implements
			CallableStatementCreator, SqlProvider {

		private final String callString;

		public SimpleCallableStatementCreator(String callString) {
			this.callString = callString;
		}

		@Override
		public CallableStatement createCallableStatement(Connection con)
				throws SQLException {
			return con.prepareCall(this.callString);
		}

		@Override
		public String getSql() {
			return callString;
		}
	}

	/**
	 * Simple adapter for PreparedStatementSetter that applies a given array of
	 * arguments.
	 */
	private static class ArgPreparedStatementSetter implements
			PreparedStatementSetter, ParameterDisposer {

		private final Object[] args;

		public ArgPreparedStatementSetter(Object[] args) {
			this.args = args;
		}

		@Override
		public void setValues(PreparedStatement ps) throws SQLException {
			if (this.args != null) {
				for (int i = 0; i < this.args.length; i++) {
					if (args[i] == null) {
						ps.setObject(i + 1, null);
					} else
						StatementCreatorUtils.setParameterValue(ps, i + 1,
								SqlTypeValue.TYPE_UNKNOWN, null, this.args[i]);
				}
			}
		}

		@Override
		public void cleanupParameters() {
			StatementCreatorUtils.cleanupParameters(this.args);
		}
	}

	/**
	 * Simple adapter for PreparedStatementSetter that applies given arrays of
	 * arguments and JDBC argument types.
	 */
	private static class ArgTypePreparedStatementSetter implements
			PreparedStatementSetter, ParameterDisposer {

		private final Object[] args;
		private final int[] argTypes;

		public ArgTypePreparedStatementSetter(Object[] args, int[] argTypes) {
			if ((args != null && argTypes == null)
					|| (args == null && argTypes != null)
					|| (args != null && args.length != argTypes.length)) {
				throw new InvalidDataAccessApiUsageException(
						"args and argTypes parameters must match");
			}
			this.args = args;
			this.argTypes = argTypes;
		}

		@Override
		public void setValues(PreparedStatement ps) throws SQLException {
			if (this.args != null) {
				for (int i = 0; i < this.args.length; i++) {
					StatementCreatorUtils.setParameterValue(ps, i + 1,
							this.argTypes[i], null, this.args[i]);
				}
			}
		}

		@Override
		public void cleanupParameters() {
			StatementCreatorUtils.cleanupParameters(this.args);
		}
	}

	/**
	 * Adapter to enable use of a RowCallbackHandler inside a
	 * ResultSetExtractor.
	 * <p>
	 * Uses a regular ResultSet, so we have to be careful when using it: We
	 * don't use it for navigating since this could lead to unpredictable
	 * consequences.
	 */
	private static class RowCallbackHandlerResultSetExtractor implements
			ResultSetExtractor {

		private final RowCallbackHandler rch;

		public RowCallbackHandlerResultSetExtractor(RowCallbackHandler rch) {
			this.rch = rch;
		}

		@Override
		public Object extractData(ResultSet rs) throws SQLException {
			while (rs.next()) {
				this.rch.processRow(rs);
			}
			if (this.rch instanceof ResultReader) {
				return ((ResultReader) this.rch).getResults();
			} else {
				return null;
			}
		}
	}

	public Map callProc(String procName, Map inParamMap, String methodName,
			Class newClass) throws Exception {
		Connection dbConnection = null;
		CallableStatement proc = null;
		Map resultMap = new HashMap();
		try {
			dbConnection = conn;
			proc = dbConnection.prepareCall("{ call " + procName.toUpperCase()
					+ "}");
			Object object = Class.forName(newClass.getName()).newInstance();
			Method method = newClass.getMethod(methodName, new Class[] {
					CallableStatement.class, Map.class });
			resultMap = (Map) method.invoke(object, new Object[] { proc,
					inParamMap });
		} catch (Exception se) {
			se.printStackTrace();
			throw new DAOSystemException("SQLException while execProc:"
					+ procName + "\n", se);
		} finally {
			proc.close();
			try {
				closeConnection(dbConnection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultMap;
	}

	private static void closeConnection(Connection conn) throws SQLException {
		try {
			if (conn != null && !conn.isClosed()) {
				if (conn.getTransactionIsolation() == Connection.TRANSACTION_READ_UNCOMMITTED) {
					conn
							.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
				}
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("SQL Exception while closing "
					+ "DB connection : \n");
		}

	}

}
