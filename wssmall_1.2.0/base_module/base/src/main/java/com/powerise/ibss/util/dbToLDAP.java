package com.powerise.ibss.util;

import com.powerise.ibss.framework.FrameException;

import java.sql.*;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class dbToLDAP{
	private static Logger logger = Logger.getLogger(dbToLDAP.class);
	public static void main(String args[]) throws FrameException{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try{			
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			String strUser ="SELECT STAFF_NO,PASSWORD,STATE FROM TSM_STAFF";
			rs = stmt.executeQuery(strUser);
			while(rs.next() && !rs.isAfterLast()){
				String uid = rs.getString("STAFF_NO").trim();
				String pwd = rs.getString("PASSWORD").trim();
				String status = rs.getString("STATE").trim();
				if(status.equals("1")){
					status = "0";
				}else{
					status ="1";
				}
				HashMap hm = new HashMap();	
				
				hm.put("BFIBSSID",uid);
                hm.put("BFIBSSIDTYPE","1");
		        hm.put("BFIBSSCHANNEL","1");
		        if(pwd.length()>10){
		             pwd = TEA.Decrypt(pwd);
		        }
		        hm.put("BFIBSSPASSWD",pwd);
		       
		        hm.put("BFIBSSSTATUS",status);
		        int iRet = LDAPUtils.addToLDAP(hm);
		        if(iRet ==0){
		        	logger.info("System information: Add Successed!uid=[" + uid +"]");
		        }else{
		        	logger.info("System Error: uid=["+ uid+"]\n" + LDAPUtils.getErrMsg());
		        }
			}
			if(rs != null){
				rs.close();
				rs = null;
			}
			if(stmt != null){
				stmt.close();
				stmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}/*catch(FrameException fe){
			try{
				if(rs != null){
					rs.close();
					rs = null;
				}
				if(stmt != null){
					stmt.close();
					stmt = null;
				}
				if(conn != null){
					conn.close();
					conn = null;
				}
			}catch(SQLException sql){}
			logger.info("Error Code: " + fe.getErrorCode() +"\n" +
			                   "Error Msg : " + fe.getErrorMsg());
		}*/catch(SQLException sqle){
			try{
				if(rs != null){
					rs.close();
					rs = null;
				}
				if(stmt != null){
					stmt.close();
					stmt = null;
				}
				if(conn != null){
					conn.close();
					conn = null;
				}
			}catch(SQLException sql){}
			String str = " sql error code: "+ String.valueOf(sqle.getErrorCode()) ;
			str = str + "sqlerror msg: " + sqle.toString();
			logger.info(str);
		}
	}
	
	private static Connection getConnectionA(){
		Connection conn = null;
		try{
		   logger.info("Connection to dataBase");		
	   	   Class.forName("oracle.jdbc.driver.OracleDriver");
		   conn =DriverManager.getConnection("jdbc:oracle:oci8:@sd_ibss", "bfibss", "bfibss");
		}catch(ClassNotFoundException ce){
			
		}catch(SQLException sqle){
			
		}
		return conn;
		
	}
}
