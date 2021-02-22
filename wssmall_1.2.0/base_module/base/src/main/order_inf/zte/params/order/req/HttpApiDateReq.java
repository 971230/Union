package zte.params.order.req;

import params.ZteRequest;
import zte.params.order.resp.OrderAddResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.model.HttpReqData;

/**
 * 添加订单
 * @作者 MoChunrun
 * @创建日期 2014-1-8 
 * @版本 V 1.0
 */
public class HttpApiDateReq extends ZteRequest<OrderAddResp> {
	HttpReqData httpReqData;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.httpdata.add";
	}

	public HttpReqData getHttpReqData() {
		return httpReqData;
	}

	public void setHttpReqData(HttpReqData httpReqData) {
		this.httpReqData = httpReqData;
	}
	
}
