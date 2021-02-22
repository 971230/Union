package zte.net.ecsord.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.net.ecsord.params.order.resp.GoodsSynWMSResp;

public class GoodsSynWMSReq extends ZteRequest<GoodsSynWMSResp>{
	
	@ZteSoftCommentAnnotationParam(name="服务编码",type="String",isNecessary="Y",desc="服务编码")
	private String service_code;
	
	@ZteSoftCommentAnnotationParam(name="货品id",type="String",isNecessary="Y",desc="货品单ID")
	private String goods_id;
	
	@ZteSoftCommentAnnotationParam(name="动作",type="String",isNecessary="Y",desc="动作")
	private String action_code;
	

	
	
	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getAction_code() {
		return action_code;
	}

	public void setAction_code(String action_code) {
		this.action_code = action_code;
	}


	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.ecsord.params.order.req.synchronizedGoodsToWMS";
	}

}
