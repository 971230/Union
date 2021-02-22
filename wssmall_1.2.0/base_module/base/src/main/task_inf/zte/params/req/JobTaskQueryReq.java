package zte.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import params.ZteRequest;
import zte.params.resp.JobTaskQueryResp;

public class JobTaskQueryReq extends ZteRequest<JobTaskQueryResp> {

	@ZteSoftCommentAnnotationParam(name="定时任务类名",type="Array",isNecessary="Y",desc="定时任务类名")
	private String className;
	@ZteSoftCommentAnnotationParam(name="定时任务执行方法",type="Array",isNecessary="Y",desc="定时任务执行方法")
	private String method;
	@ZteSoftCommentAnnotationParam(name="定时任务执行IP",type="Array",isNecessary="Y",desc="定时任务执行IP")
	private String ip;
	@ZteSoftCommentAnnotationParam(name="定时任务执行端口",type="Array",isNecessary="Y",desc="定时任务执行端口")
	private String port;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(className))CommonTools.addFailError("className is null");
		if(StringUtil.isEmpty(method))CommonTools.addFailError("method is null");
		if(StringUtil.isEmpty(ip))CommonTools.addFailError("ip is null");
		if(StringUtil.isEmpty(port))CommonTools.addFailError("port is null");
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.jobTaskService.task.query";
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String getMethod() {
		return method;
	}

	@Override
	public void setMethod(String method) {
		this.method = method;
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

}