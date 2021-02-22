package zte.net.ecsord.params.base.req;

import java.util.Map;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 
 * @author wu.i 业务组建调用请求入参
 * 
 */
public  class BusiCompRequest extends ZteRequest  {
	
	@ZteSoftCommentAnnotationParam(name="业务组件入参",type="String",isNecessary="N",desc="业务组件入参")
	Map queryParams;
	
	@ZteSoftCommentAnnotationParam(name="业务组件路径",type="String",isNecessary="N",desc="业务组件路径")
	String enginePath;
	
	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="N",desc="业务组件入参")
	String order_id;
	
	public String getEnginePath() {
		return enginePath;
	}
	public void setEnginePath(String enginePath) {
		this.enginePath = enginePath;
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.ecsord.get.busicomp";
	}
	
	public Map getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(Map queryParams) {
		this.queryParams = queryParams;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
}
