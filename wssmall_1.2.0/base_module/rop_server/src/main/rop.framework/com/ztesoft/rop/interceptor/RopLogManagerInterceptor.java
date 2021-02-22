package com.ztesoft.rop.interceptor;

import java.util.Date;

import com.ztesoft.rop.common.AbstractInterceptor;
import com.ztesoft.rop.common.CurrentThreadVar;
import com.ztesoft.rop.common.RopRequestContext;

/**
 * 
 * 修改日志处理模式：仅写一次库表，两记录合二为一，减少存储量，日志记录等处理方式
 * modified by easonwu 2013-10-17
 *
 */
public class RopLogManagerInterceptor extends AbstractInterceptor {

	public static ThreadLocal<AppLog> CurrentThreadAppLog = new ThreadLocal<AppLog>();
	
	@Override
	public void beforeService(RopRequestContext ropRequestContext) {
		CurrentThreadVar var = CurrentThreadVar.getVar();
		var.setReqDate(new Date()) ;
	}
	
	@Override
	public void beforeResponse(RopRequestContext ropRequestContext) {
//		CurrentThreadVar var = CurrentThreadVar.getVar();
//		Date resDate = new Date() ;
//		String result = "";
//		try {						
//			Object object = ropRequestContext.getRopResponse();
//			if(object != null) {
//				try {
//				String format = ropRequestContext.getFormat();
//				result = MessageMarshallerUtils.getMessage(object, format);
//				}catch(Exception e) {
//					result = "";
//				}
//			}
//			String reqContent = ropRequestContext.getAllParams() == null ? "" : ropRequestContext.getAllParams().toString() ;
//			int data = result.length()+reqContent.length() ;
//			long elapsed = resDate.getTime() - var.getReqDate().getTime() ;
////			RopDaoImpl.getInstance().writeServiceLog(var.getAppId(),var.getFunCode(),var.getFunVersion(),
////					ropRequestContext.getRopRequest().getSessionId(),ropRequestContext.getRopRequest().getSessionId(),
////		    		var.getReqDate(),reqContent,resDate,result,
////		    		ropRequestContext.getIp(), data,var.getExceptionMsg(),var.getFailure(),var.getSuccess(), elapsed,var.getFunId()) ;
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
		
	}
	
	@Override
	public boolean isMatch(RopRequestContext ropRequestContext) {
		return true;
	}
	
	static class AppLog{
		public int log_id ;
		public int fun_id ;
		public int app_id ;
		public String session_id ;
		public String rqquest_id ;
		public Date req_date ;
		public String req_content ;
		public String res_date ;
		public String res_content ;
		public String req_ip ;
		
		public int data ;
		public String exception_msg ;
		public int failure ;
		public int success ;
		public int elapsed ;
		
		
		public AppLog(){
			
		}
		public int getLog_id() {
			return log_id;
		}
		public void setLog_id(int log_id) {
			this.log_id = log_id;
		}
		public int getFun_id() {
			return fun_id;
		}
		public void setFun_id(int fun_id) {
			this.fun_id = fun_id;
		}
		public int getApp_id() {
			return app_id;
		}
		public void setApp_id(int app_id) {
			this.app_id = app_id;
		}
		public String getSession_id() {
			return session_id;
		}
		public void setSession_id(String session_id) {
			this.session_id = session_id;
		}
		public String getRqquest_id() {
			return rqquest_id;
		}
		public void setRqquest_id(String rqquest_id) {
			this.rqquest_id = rqquest_id;
		}
		public Date getReq_date() {
			return req_date;
		}
		public void setReq_date(Date req_date) {
			this.req_date = req_date;
		}
		public String getReq_content() {
			return req_content;
		}
		public void setReq_content(String req_content) {
			this.req_content = req_content;
		}
		public String getRes_date() {
			return res_date;
		}
		public void setRes_date(String res_date) {
			this.res_date = res_date;
		}
		public String getRes_content() {
			return res_content;
		}
		public void setRes_content(String res_content) {
			this.res_content = res_content;
		}
		public String getReq_ip() {
			return req_ip;
		}
		public void setReq_ip(String req_ip) {
			this.req_ip = req_ip;
		}
		public int getData() {
			return data;
		}
		public void setData(int data) {
			this.data = data;
		}
		public String getException_msg() {
			return exception_msg;
		}
		public void setException_msg(String exception_msg) {
			this.exception_msg = exception_msg;
		}
		public int getFailure() {
			return failure;
		}
		public void setFailure(int failure) {
			this.failure = failure;
		}
		public int getSuccess() {
			return success;
		}
		public void setSuccess(int success) {
			this.success = success;
		}
		public int getElapsed() {
			return elapsed;
		}
		public void setElapsed(int elapsed) {
			this.elapsed = elapsed;
		}
	}
}
