package com.ztesoft.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 数据源
 * @作者 MoChunrun
 * @创建日期 2014-12-4 
 * @版本 V 1.0
 */
public class DataModule {
	
	private static String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static String USERNAME = "";
	private static String PASSWORD = "";
	private static String URL = "";
	private static String JNDI = "";
	private static String GET_TYPE = "jdbc";
		

	static Properties prop = new Properties();
	
	static {
		InputStream in = null;
		try {
			//URL datas = DataModule.class.getResource("/config/jdbc.properties");
			//prop.load(datas.openStream());
			String path = System.getProperty("CONFIG")+"\\"+"jdbc.properties";
			in = new FileInputStream(new File(path));
			prop.load(in);
			DRIVER = prop.getProperty("jdbc.driverClassName");
			URL = prop.getProperty("jdbc.url");
			USERNAME = prop.getProperty("jdbc.username"); 
			PASSWORD = prop.getProperty("jdbc.password");
			JNDI = prop.getProperty("jndiName");
			GET_TYPE = prop.getProperty("conn.type");
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(in!=null)in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Connection getConnection()throws Exception{
		if("jndi".equals(GET_TYPE)){
			return getJndiConnection();
		}else{
			return getJdbcConnection();
		}
	}
	
	/**
	 * jndi Connection
	 * @作者 MoChunrun
	 * @创建日期 2014-12-4 
	 * @return
	 * @throws Exception
	 */
	public static Connection getJndiConnection() throws Exception{
		 Context initCtx = new InitialContext();
	     DataSource ds = (DataSource)initCtx.lookup(JNDI);
	     Connection conn = ds.getConnection();
	     return conn;
	}
	
	/**
	 * jdb cconnection
	 * @作者 MoChunrun
	 * @创建日期 2014-12-4 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Connection getJdbcConnection() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException sql) {
			sql.printStackTrace();
			if (conn != null) {
				conn.close(); conn = null;
			} 
			throw sql;
		} catch (ClassNotFoundException cnfe) {
			throw cnfe;
		}
		return conn;
	}
	
	public static void close(PreparedStatement stmt, ResultSet rset, Connection conn){
		try {
			if (rset != null) {
				rset.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement stmt,ResultSet rset){
		close(stmt,rset,null);
	}
	
	public static void close(PreparedStatement stmt){
		close(stmt,null,null);
	}

}
