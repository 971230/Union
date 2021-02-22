package params.req;


import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class CrawlerProcCondReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name="运行的进程所在机器的IP",type="String",isNecessary="Y",desc="运行的进程所在机器的IP")
	private String ip; 
	@ZteSoftCommentAnnotationParam(name="运行的进程对应的端口",type="String",isNecessary="Y",desc="运行的进程对应的端口")
	private String port; 
	@ZteSoftCommentAnnotationParam(name="线程名称",type="String",isNecessary="Y",desc="线程名称")
	private String thread_name;
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
	}

	@Override
	public String getApiMethodName() {
		return "zte.net.iservice.impl.CrawlerProcCondService.getProcCond";
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


	
}
