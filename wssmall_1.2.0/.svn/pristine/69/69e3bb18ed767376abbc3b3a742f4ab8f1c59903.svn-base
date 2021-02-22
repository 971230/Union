package zte.params.ecsord.req;

import params.ZteRequest;
import zte.params.ecsord.resp.GetOrderByVBELNResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
/**
 * 由出库单号获取内部单号，华盛订单专用
 * @author Administrator
 *
 */
public class GetOrderByVBELNReq extends ZteRequest<GetOrderByVBELNResp> {
	
	@ZteSoftCommentAnnotationParam(name="出库单号",type="String",isNecessary="Y",desc="出库单号")
	private String vbeln;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.ecsord.params.attr.req.getOrderByVbeln";
	}

	public String getVbeln() {
		return vbeln;
	}

	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}
	
}
