package zte.params.order.req;

import params.ZteRequest;
import params.ZteResponse;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 添加订单
 * @作者 MoChunrun
 * @创建日期 2014-1-8 
 * @版本 V 1.0
 */
public class EcsTestReq extends ZteRequest<ZteResponse> {
	
	private ZteRequest creq;
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.service.wssmall.ecs.ecstest";
	}

	public ZteRequest getCreq() {
		return creq;
	}

	public void setCreq(ZteRequest creq) {
		this.creq = creq;
	}

	
}
