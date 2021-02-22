/**
 * 
 */
package zte.params.order.req;

import params.ZteRequest;
import zte.params.order.resp.RefreshOrderTreeAttrResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * @author ZX
 * RefreshOrderTreeAttrReq.java
 * 2014-11-11
 */
public class RefreshOrderTreeAttrReq extends ZteRequest<RefreshOrderTreeAttrResp> {

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.orderService.refresh.order.tree.attr.req";
	}

}
