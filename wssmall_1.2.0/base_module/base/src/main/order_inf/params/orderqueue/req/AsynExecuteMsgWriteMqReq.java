package params.orderqueue.req;

import java.util.Map;

import params.ZteRequest;
import params.ZteResponse;
import params.orderqueue.resp.AsynExecuteMsgWriteMqResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class AsynExecuteMsgWriteMqReq extends ZteRequest<AsynExecuteMsgWriteMqResp> {

	/** 
	 * serialVersionUID: TODO(用一句话描述这个变量表示什么) 
	 */ 
	private static final long serialVersionUID = 3590836606388668208L;

	@ZteSoftCommentAnnotationParam(name = "服务编码", type = "String", isNecessary = "Y", desc = "服务编码")
	private String service_code;

	@ZteSoftCommentAnnotationParam(name = "服务版本号", type = "String", isNecessary = "Y", desc = "服务版本号")
	private String version;

	@ZteSoftCommentAnnotationParam(name = "队列ID", type = "String", isNecessary = "Y", desc = "队列ID")
	private String co_id;

	@ZteSoftCommentAnnotationParam(name = "消息写入对象", type = "String", isNecessary = "Y", desc = "消息写入对象")
	private ZteRequest<ZteResponse>  zteRequest;

	@ZteSoftCommentAnnotationParam(name = "消息写入对象Map", type = "String", isNecessary = "Y", desc = "消息写入对象Map")
	private Map<String, String> zteRequestMap;
	
	@ZteSoftCommentAnnotationParam(name = "消费机器consume_env", type = "String", isNecessary = "Y", desc = "示例:ecsord_std,ecsord_exp")
	private String consume_env_code;

	@Override
	public void check() throws ApiRuleException {
		// TODO 自动生成的方法存根
	}

	@Override
	public String getApiMethodName() {
		// TODO 自动生成的方法存根
		return null;
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public void setVersion(String version) {
		this.version = version;
	}

	public String getCo_id() {
		return co_id;
	}

	public void setCo_id(String co_id) {
		this.co_id = co_id;
	}


	public ZteRequest getZteRequest() {
		return zteRequest;
	}


	public Map<String, String> getZteRequestMap() {
		return zteRequestMap;
	}

	public void setZteRequestMap(Map<String, String> zteRequestMap) {
		this.zteRequestMap = zteRequestMap;
	}

	public void setZteRequest(ZteRequest<ZteResponse> zteRequest) {
		this.zteRequest = zteRequest;
	}

	public String getConsume_env_code() {
		return consume_env_code;
	}

	public void setConsume_env_code(String consume_env_code) {
		this.consume_env_code = consume_env_code;
	}

}
