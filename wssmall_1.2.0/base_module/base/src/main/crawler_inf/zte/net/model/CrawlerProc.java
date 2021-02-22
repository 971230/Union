package zte.net.model;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 爬虫进程实体类
 */
public class CrawlerProc implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ZteSoftCommentAnnotationParam(name="主键",type="String",isNecessary="Y",desc="主键")
	private String proc_id; 
	@ZteSoftCommentAnnotationParam(name="进程编码",type="String",isNecessary="Y",desc="进程编码")
	private String proc_code; 
	@ZteSoftCommentAnnotationParam(name="进程名称",type="String",isNecessary="Y",desc="进程名称")
	private String proc_name; 
	@ZteSoftCommentAnnotationParam(name="运行的进程所在机器的IP",type="String",isNecessary="Y",desc="运行的进程所在机器的IP")
	private String ip; 
	@ZteSoftCommentAnnotationParam(name="运行的进程对应的端口",type="String",isNecessary="Y",desc="运行的进程对应的端口")
	private String port; 
	@ZteSoftCommentAnnotationParam(name="线程名称",type="String",isNecessary="Y",desc="线程名称")
	private String thread_name;
	@ZteSoftCommentAnnotationParam(name="状态",type="String",isNecessary="Y",desc="0 正常； 1 失效")
	private String status;
	
	public String getProc_id() {
		return proc_id;
	}
	public void setProc_id(String proc_id) {
		this.proc_id = proc_id;
	}
	
	public String getProc_code() {
		return proc_code;
	}
	public void setProc_code(String proc_code) {
		this.proc_code = proc_code;
	}
	public String getProc_name() {
		return proc_name;
	}
	public void setProc_name(String proc_name) {
		this.proc_name = proc_name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getThread_name() {
		return thread_name;
	}
	public void setThread_name(String thread_name) {
		this.thread_name = thread_name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	} 
	
	
}

