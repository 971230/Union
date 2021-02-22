/**
 * 
 */
package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;

/**
 * @author ZX
 * RfsLimitDataReq.java
 * 2014-11-22
 */
public class RfsLimitDataReq extends ZteRequest {

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.orderService.refresh.clear.limit.req";
	}

}
