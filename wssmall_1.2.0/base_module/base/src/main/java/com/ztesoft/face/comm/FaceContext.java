package com.ztesoft.face.comm;

import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ztesoft.common.query.SqlMapExe;

/**
 * @author Reason.Yea
 * @version Created Feb 5, 2013
 */
public class FaceContext {
	
	private static ThreadLocal local = new ThreadLocal();
	
	public static void set(ContextObj context){
		local.set(context);
	}
	
	public static ContextObj get(){
		return (ContextObj) local.get();
	}
	
	public static class ContextObj{
		private HttpServletRequest request;
		private HttpServletResponse response;
		private HttpSession session;
		private Connection conn;
		private SqlMapExe sqlExe;
		private HashMap map;

		public HashMap getMap() {
			return map;
		}
		public void setMap(HashMap map) {
			this.map = map;
		}
		public Connection getConn() {
			return conn;
		}
		public void setConn(Connection conn) {
			this.conn = conn;
		}
		public SqlMapExe getSqlExe() {
			return sqlExe;
		}
		public void setSqlExe(SqlMapExe sqlExe) {
			this.sqlExe = sqlExe;
		}
		public HttpServletRequest getRequest() {
			return request;
		}
		public void setRequest(HttpServletRequest request) {
			this.request = request;
		}
		public HttpSession getSession() {
			return session;
		}
		public void setSession(HttpSession session) {
			this.session = session;
		}
		public HttpServletResponse getResponse() {
			return response;
		}
		public void setResponse(HttpServletResponse response) {
			this.response = response;
		}
	}
	
}
