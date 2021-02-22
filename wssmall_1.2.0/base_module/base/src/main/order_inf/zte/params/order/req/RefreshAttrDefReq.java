/**
 * 
 */
package zte.params.order.req;

import params.ZteRequest;
import zte.params.order.resp.RefreshAttrDefResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * @author ZX
 * 刷新AttrDef
 * RefreshAttrDefReq.java
 * 2014-11-11
 */
public class RefreshAttrDefReq extends ZteRequest<RefreshAttrDefResp> {

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.orderService.refresh.attr.def.req";
	}

}
