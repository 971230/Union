package com.ztesoft.common.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.GlobalNames;
import com.ztesoft.common.query.SqlMapExe;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.JNDINames;

public class DAOUtils {
	private static int WAIT_SECONDS = 200;

	private static String mainSql="";
	private static LinkedHashMap whereCond=new LinkedHashMap();
	private static HashMap manualVoType=new HashMap();
	private static HashMap manualVoName=new HashMap();
	private static SqlMapExe  sqlMapExe=SqlMapExe.getInstance();//SQL查询工具
	
	private DAOUtils() {
	}

	/**
	 * 当前系统使用的数据库类型
	 * 
	 * @return
	 */
	public static String getDatabaseType() {
		return System.getProperty("DATABASE_TYPE");
	}

	public static void systempInit(){
		//判断是否已经初始化系统
	      if(! GlobalNames.CONFIG_LOADED){
	         try {
//	        	 配置参数载入
//	 			CrmParamsConfig.getInstance().initParams(CrmConstants.WEB_INF_PATH);
	        	 CrmParamsConfig.getInstance().initParams(null);
	 			//SysSet.initSystem(3);//web + app 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	}
	/**
	 * @param :source
	 *            数据源名字
	 */
	public static Connection getDBConnection(String source)
			throws DAOSystemException {
		throw new DAOSystemException("不能直接通过此方式获取数据库连接!") ;	}

	/**
	 * @param :source
	 *            数据源名字, callerObj 调用者句柄
	 */
	public static Connection getDBConnection(String source, Object callerObj)
			throws DAOSystemException {
		throw new DAOSystemException("不能直接通过此方式获取数据库连接!") ;	
	}

	/**
	 * Specify informixLockModeWait if you prefer to wait for locks for the
	 * specified amount of time before an exception is thrown.
	 * 
	 * @param seconds
	 *            The number of seconds to wait. If seconds is == 0 the waiting
	 *            is disabled. If seconds is less than 0 then wait indefinitly.
	 * @exception java.sql.SQLException
	 *                If a database access error occurs.
	 */
	public synchronized static void setLockModeToWait(int seconds,
			Connection conn) throws SQLException {
		String sql = "";
		if (seconds > 0) {
			sql = "SET LOCK MODE TO WAIT " + seconds;
		} else if (seconds == 0) {
			sql = "SET LOCK MODE TO NOT WAIT";
		} else {
			sql = "SET LOCK MODE TO WAIT";
		}

		Statement stmt = conn.createStatement();
		stmt.execute(sql);
		stmt.close();
	}

	/**
	 * 直接使用JDBC的方式获取当前数据库的连接.
	 * 
	 * @return
	 */
	public static Connection getDirectConnection(String dbName) throws DAOSystemException {
		throw new DAOSystemException("不能直接通过此方式获取数据库连接!") ;
	}

	/**
	 * @param :dbConnection
	 *            需要关闭的数据库连接
	 */
	public static void closeConnection(Connection dbConnection)
			throws DAOSystemException {
		throw new DAOSystemException("不能直接通过此方式关闭数据库连接!") ;
	}

	/**
	 * @param :dbConnection
	 *            需要关闭的数据库连接, callerObj 调用者句柄
	 */
	public static void closeConnection(Connection dbConnection, Object callerObj)
			throws DAOSystemException {
		/*try {
			if(dbConnection != null  && !dbConnection.isClosed()){
				if(dbConnection.getTransactionIsolation()==dbConnection.TRANSACTION_READ_UNCOMMITTED)
					dbConnection.setTransactionIsolation(dbConnection.TRANSACTION_READ_COMMITTED);
				dbConnection.close();
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQL Exception while closing "
					+ "DB connection : \n" + se);
		}*/
	}

	/**
	 * @param :result
	 *            需要关闭的结果集
	 */
	public static void closeResultSet(ResultSet result)
			throws DAOSystemException {
		try {
			if (result != null) {
				result.close();
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQL Exception while closing "
					+ "Result Set : \n" + se);
		}
	}
	
	
	
	/**
	 * 关闭命名参数语句(Statement)
	 * @param st
	 * @throws DAOSystemException
	 */
	public static void closeNamedParameterStatement(NamedParameterStatement st)
			throws DAOSystemException {
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQL Exception while closing "
					+ "NamedParameterStatement : \n" + se);
		}
	}

	/**
	 * @param :result
	 *            需要关闭的结果集, callerObj 调用者句柄
	 */
	public static void closeResultSet(ResultSet result, Object callerObj)
			throws DAOSystemException {
		try {
			if (result != null) {
				result.close();
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQL Exception while closing "
					+ "Result Set : \n" + se);
		}
	}

	/**
	 * @param :stmt
	 *            需要关闭的陈述
	 */
	public static void closeStatement(PreparedStatement stmt)
			throws DAOSystemException {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQL Exception while closing "
					+ "Statement : \n" + se);
		}
	}

	/**
	 * @param :stmt
	 *            需要关闭的陈述, callerObj 调用者句柄
	 */
	public static void closeStatement(PreparedStatement stmt, Object callerObj)
			throws DAOSystemException {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQL Exception while closing "
					+ "Statement : \n" + se);
		}
	}

	/**
	 * 
	 * @param stmt
	 * @throws DAOSystemException
	 */
	public static void closeStatement(Statement stmt)
		throws DAOSystemException {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQL Exception while closing "
					+ "Statement : \n" + se);
		}
	}

	
	/**
	 * 打开脏读设置，允许一个事务可以读取另一个事务尚未提交的修改。
	 * 
	 * @param conn
	 * @throws DAOSystemException
	 */
	public static void openDirtyRead(Connection conn) throws DAOSystemException {
		try {
			if (conn != null) {
				if (DAOUtils.getDatabaseType().equals(CrmConstants.DB_TYPE_INFORMIX)) {
					conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
				} else if (DAOUtils.getDatabaseType().equals(CrmConstants.DB_TYPE_ORACLE)) {
					conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
					/*String sql = "SET TRANSACTION READ ONLY";
					Statement stmt = null;
					try {
						stmt = conn.createStatement();
						stmt.execute(sql);
					} finally {
						if (stmt != null)
							stmt.close();
					}*/
				}
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQL Exception while openDirtyRead : "
					+ se);
		}
	}

	/**
	 * 关闭脏读设置，返回提交读的状态。 一个事务读取另一个事务全部提交的修改。
	 * 
	 * @param conn
	 * @throws DAOSystemException
	 */
	public static void closeDirtyRead(Connection conn)
			throws DAOSystemException {
		throw new DAOSystemException("不能直接通过此方式关闭数据库连接!") ;	
//		try {
//			if (conn != null) {
//				if (DAOUtils.getDatabaseType().equals(CrmConstants.DB_TYPE_INFORMIX)) { 
//					conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
//				} else if (DAOUtils.getDatabaseType().equals(CrmConstants.DB_TYPE_ORACLE)) {	
//					//conn.commit();
//					conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
//				}
//			}
//		} catch (SQLException se) {
//			throw new DAOSystemException("SQL Exception while openDirtyRead : "
//					+ se);
//		}
	}

	/**
	 * 获取当前的时间。当前实现从应用服务器取时间。 如果要统一取数据库时间，也将从这里统一获取。
	 * 
	 * @return
	 */
	public static java.sql.Date getCurrentDate() {

		return new java.sql.Date(System.currentTimeMillis());

	}
	
	/**
	 * 获取当前的时间。当前实现从应用服务器取时间。 如果要统一取数据库时间，也将从这里统一获取。
	 * 
	 * @return
	 */
	public static java.sql.Timestamp getCurrentTimestamp() {

		return new java.sql.Timestamp(System.currentTimeMillis());

	}

	/**
	 * 获取当前的时间。当前实现从应用服务器取时间。 并且通过统一的格式化。
	 * 
	 * @return
	 */
	public static String getFormatedDate() {

		SimpleDateFormat dateFormator = new SimpleDateFormat(
				CrmConstants.DATE_TIME_FORMAT);
		return dateFormator
				.format(new java.sql.Date(System.currentTimeMillis()));

	}
	
	
	/**
	 * 获取当前的时间。当前实现从应用服务器取时间。 并且通过统一的格式化。
	 * @return
	 */
	public static String getFormatedDate(String dateFormat) {

		SimpleDateFormat dateFormator = new SimpleDateFormat(
				dateFormat);
		return dateFormator
				.format(new java.sql.Date(System.currentTimeMillis()));

	}
	
	/**
	 * 获取当前的数据库时间。当前实现从应用服务器取时间。 并且通过统一的格式化。
	 * @author RyoUehara
	 * 添加时间:090504
	 * @return
	 */
	public static String getFormatedDbDate() throws Exception
	{
		//modify by RyoUehara 090504 itime改为取数据库的sysdate
		//ComDAO commandDAO=(ComDAO)ComServiceDAO.getInstance().getComDAO("");
		
		String sql = "";
		if (isInformixDB()) {
			sql = "select to_char(current,'%Y-%m-%d %H:%M:%S') curentDate from dual";
		}else if(isOracleDB()){
			sql = "select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') curentDate from dual";
		}else{
			throw new FrameException("没有获取到数据库服务器的当前时间！");
		}
		String cur_date = Base.query_string(JNDINames.DEFAULT_DATASOURCE, sql, -210020502);
		return cur_date;
		
	}
	
	/**
	 * 获取当前的时间。当前实现从应用服务器取时间。 并且通过统一的格式化。
	 * 
	 * @return
	 */
	public static String getShortFormatedDate() {

		SimpleDateFormat dateFormator = new SimpleDateFormat(
				CrmConstants.DATE_FORMAT);
		return dateFormator
				.format(new java.sql.Date(System.currentTimeMillis()));

	}

	/**
	 * 获取当前的时间。当前实现从应用服务器取时间。 并且通过统一的格式化。
	 * 
	 * @return
	 */
	public static String getFormatedDateTime14() {

		SimpleDateFormat dateFormator = new SimpleDateFormat(
				CrmConstants.DATE_TIME_FORMAT_14);
		return dateFormator
				.format(new java.sql.Date(System.currentTimeMillis()));

	}
	
	/**
	 * 获取默认的失效的时间
	 * 
	 * @return
	 */
	public static java.sql.Date getDefaultExpiredDate() {

		SimpleDateFormat dateFormator = new SimpleDateFormat(
				CrmConstants.DATE_TIME_FORMAT);
		java.util.Date tDate = dateFormator.parse(
				CrmConstants.DEFAULT_EXPIRED_DATE, new ParsePosition(0));
		return new java.sql.Date(tDate.getTime());

	}

	/**
	 * 获取默认的失效的时间 并且通过统一的格式化。
	 * 
	 * @return
	 */
	public static String getFormatedExpiredDate() {

		return CrmConstants.DEFAULT_EXPIRED_DATE;

	}

	/**
	 * 通过统一的格式将文本转换成Date。输入为日期和时间。
	 * 
	 * @return
	 */
	public static Date parseDateTime(String sdate) {
		if (null == sdate || "".equals(sdate))
			return null;

		// 只有日期类型
		if (sdate.length() <= 10) {
			return parseDate(sdate);
		}

		SimpleDateFormat dateFormator = new SimpleDateFormat(
				CrmConstants.DATE_TIME_FORMAT);
		SimpleDateFormat dateFormator2 = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT_14);
		java.util.Date tDate = dateFormator.parse(sdate, new ParsePosition(0));
		if (tDate == null){
//			再做一次格式转换
			tDate = dateFormator2.parse(sdate, new ParsePosition(0));
			if(tDate == null){
				return null;
			}
		}

		return new java.sql.Date(tDate.getTime());

	}
	
	/**
	 * 通过统一的格式将文本转换成Date。输入为日期和时间。
	 * 
	 * @return
	 */
	public static Date parseDateTime(Object sdate) {
		if (null == sdate )
			return null;
		return parseDateTime((String) sdate)  ;
	}
	/**
	 * 通过统一的格式将文本转换成Timestamp。输入为日期和时间。
	 * 
	 * @return
	 */
	public static Timestamp parseTimestamp(String sdate) {
		if (null == sdate || "".equals(sdate))
			return null;
		
		java.util.Date tDate = null;

		// 只有日期类型
		if (sdate.length() <= 10) {
			SimpleDateFormat dateFormator = new SimpleDateFormat(
					CrmConstants.DATE_FORMAT);

			tDate = dateFormator.parse(sdate, new ParsePosition(0));
		}else{

			SimpleDateFormat dateFormator = new SimpleDateFormat(
					CrmConstants.DATE_TIME_FORMAT);

			tDate = dateFormator.parse(sdate, new ParsePosition(0));
		}
		
		if (tDate == null)
			return null;

		return new java.sql.Timestamp(tDate.getTime());

	}
	

	/**
	 * 通过统一的格式将文本转换成Date。输入为日期。
	 * 
	 * @return
	 */
	public static Date parseDate(String sdate) {
		if (null == sdate || "".equals(sdate))
			return null;

		SimpleDateFormat dateFormator = new SimpleDateFormat(
				CrmConstants.DATE_FORMAT);

		java.util.Date tDate = dateFormator.parse(sdate, new ParsePosition(0));
		if (tDate == null)
			return null;

		return new java.sql.Date(tDate.getTime());
	}

    /**
     * 将字CLOB转成STRING类型
     * 
     * @param clob
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public static String ClobToString(Clob clob){
        if(clob==null)return null;
        String reString = "";
        Reader is;
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            is = clob.getCharacterStream();
            // 得到流
            br = new BufferedReader(is);
            String s = br.readLine();

            while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
                sb.append(s);
                s = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally{
        	try {
				if (br != null){
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        reString = sb.toString();
        return reString;
    }

	/**
	 * 将Date转换成统一的日期格式文本。
	 * 
	 * @return
	 */
	public static String getFormatedDate(Date date) {
		if (null == date)
			return "";

		SimpleDateFormat dateFormator = new SimpleDateFormat(
				CrmConstants.DATE_FORMAT);
		return dateFormator.format(new java.sql.Date(date.getTime()));
	}

	/**
	 * 将Date转换成统一的日期时间格式文本。
	 * 
	 * @return
	 */
	public static String getFormatedDateTime(Date date) {
		if (null == date)
			return "";

		SimpleDateFormat dateFormator = new SimpleDateFormat(
				CrmConstants.DATE_TIME_FORMAT);
		return dateFormator.format(new java.sql.Date(date.getTime()));
	}
	
	/**
	 * 将Date转换成统一的日期时间格式文本。
	 * 
	 * @return
	 */
	public static String getFormatedDateTime(Timestamp stamp) {
		if (null == stamp)
			return "";

		SimpleDateFormat dateFormator = new SimpleDateFormat(
				CrmConstants.DATE_TIME_FORMAT);
		return dateFormator.format(new java.sql.Date(stamp.getTime()));
	}
	
	/**
	 * 将Date转换成统一的日期格式文本。
	 * 格式：yyyy-mm-dd
	 * @return
	 * @author suns
	 */
	public static String getFormatedDate(Timestamp stamp) {
		if (null == stamp)
			return "";

		SimpleDateFormat dateFormator = 
			new SimpleDateFormat(CrmConstants.DATE_FORMAT);
		return dateFormator.format(new java.sql.Date(stamp.getTime()));
	}
	
	/**
	 * 计算两日时间之差
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String caculateTwoDayInterval(Date startDate ,Date endDate ) {
		if(startDate.after(endDate)) return null;   
		            
         Calendar firstDay = Calendar.getInstance();   
         Calendar lastDay = Calendar.getInstance();   
         firstDay.setTime(startDate);   
         lastDay.setTime(endDate);   
            
         //算出天数总差值   
        long allDays = ((lastDay.getTimeInMillis()) - (firstDay.getTimeInMillis()))/(1000*24*60*60); 
        
        return String.valueOf(allDays);
	}

	/**
	 * 提供删除字符串前后的空格的功能
	 * 
	 * @return
	 */
	public static String trimStr(String str) {

		if (null == str)
			return "";
		else
			return str.trim();

	}

	/**
	 * 用户输入的查询条件中，转义、过滤特殊的字符。 informix下的转义符为"\" 需要转义的符号： ' - % \
	 * 
	 * @param condStr
	 * @return
	 */
	public static String filterQureyLikeCond(String condStr){
		if (condStr == null || "".equals(condStr))
			return "";
		condStr = Pattern.compile("(\\')").matcher(condStr).replaceAll("''");
		//condStr = Pattern.compile("(\\\\)").matcher(condStr).replaceAll(
	    //			"\\\\\\\\");
		//condStr = Pattern.compile("(\\-)").matcher(condStr).replaceAll("\\\\-");
		//condStr = Pattern.compile("(\\%)").matcher(condStr).replaceAll("\\\\%");
		return condStr;
	}

	/**
	 * 用户输入的查询条件中，转义、过滤特殊的字符。 需要转义的符号： '
	 * 
	 * @param condStr
	 * @return
	 */
	public static String filterQureyCond(String condStr) {
		if (condStr == null || "".equals(condStr))
			return "";
		condStr = Pattern.compile("(\\')").matcher(condStr).replaceAll("''");

		return condStr;
	}

	/**
	 * 说明：获取系统参数－－传入参数ｃｏｄｅ得到值
	 * 
	 * @param paramCode
	 * @return
	 */
	public static String getSysParamValue(String paramCode) {

		if ("".equals(paramCode.trim())) {
			throw new DAOSystemException("取系统参数出错:传入的paramCode为空!");
		}
		String GET_PARAM_VALUE = "SELECT param_val FROM dc_system_param "
				+ " WHERE param_code=? ";

		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection conn = null;
		String paramVal = "";
		try {
			conn = ConnectionContext.getQueryConnection(JNDINames.DEFAULT_DATASOURCE);
			stmt = conn.prepareStatement(DAOSQLUtils
					.getFilterSQL(GET_PARAM_VALUE));
			stmt.setString(1, paramCode) ;
			
			result = stmt.executeQuery();
			if (result.next()) {
				paramVal = result.getString("param_val");
			} else {
				throw new DAOSystemException("取系统参数出错:不存在该参数:param_code:"
						+ paramCode);
			}
			return paramVal;
		} catch (SQLException se) {
			se.printStackTrace();
			throw new DAOSystemException("SQLException while execute "
					+ GET_PARAM_VALUE + ":\n" + se.getMessage(), se);
		} finally {
			DAOUtils.closeResultSet(result);
			DAOUtils.closeStatement(stmt);
			ConnectionContext.closeQueryConnection(conn) ;
		}
	}
	
	public static boolean isOracleDB() {
		return CrmConstants.DB_TYPE_ORACLE.equalsIgnoreCase(CrmConstants.CRM_DATABASE_TYPE); 
	}
	
	public static boolean isInformixDB() {
		return CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.CRM_DATABASE_TYPE);
	}
	
	public static String getDBCurrentDateKeyWord() {
		if (isOracleDB()) {
			return "sysdate";
		} else if(isInformixDB()){
			return "current";
		}
		return null;
	}

}
