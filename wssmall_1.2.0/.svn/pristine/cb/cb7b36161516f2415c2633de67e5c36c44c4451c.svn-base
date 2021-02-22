package zte.params.order.req;

import params.ZteRequest;
import zte.params.order.resp.OffLineOpenActResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class OffLineOpenActReq extends ZteRequest<OffLineOpenActResp>{
	@ZteSoftCommentAnnotationParam(name="短信接收号码",type="String",isNecessary="Y",desc="短信接收号码")
	private String order_id;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.service.wssmall.ecs.offLineOpenAct";
	}
	
}
