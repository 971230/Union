package zte.params.goods.req;

import java.util.Map;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class ParamsPutReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="挂件参数",type="Map",isNecessary="Y",desc="pluginParams：挂件参数")	
	private Map<String,Object> pluginParams;
	
	@ZteSoftCommentAnnotationParam(name="搜索uri",type="String",isNecessary="Y",desc="uri：搜索uri")	
	private String uri;
	public Map<String, Object> getPluginParams() {
		return pluginParams;
	}

	public void setPluginParams(Map<String, Object> pluginParams) {
		this.pluginParams = pluginParams;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.params.put";
	}

}
