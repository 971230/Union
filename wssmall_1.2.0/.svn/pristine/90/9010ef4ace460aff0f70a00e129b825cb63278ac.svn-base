package zte.params.order.req;

import java.util.Map;

import params.ZteRequest;
import zte.params.order.resp.WorkFlowCallBackResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

public class UnimallOrderQueryReq extends ZteRequest<WorkFlowCallBackResp> {
	
	private Map param ; //page_index,page_size, order_from,pag_time

	public Map getParam() {
		return param;
	}

	public void setParam(Map param) {
		this.param = param;
	}

	/**
	 *  where order_from = ?
	 *  where pag_time betwenn 
	 * 
	 */
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.unimallorder.query";
	}

}
