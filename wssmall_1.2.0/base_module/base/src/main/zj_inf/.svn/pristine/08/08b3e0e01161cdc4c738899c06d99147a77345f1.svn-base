/**
 * 
 */
package zte.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.params.ecaop.resp.EmpIdEssIDResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * @author ZX
 * @version 2015-06-15
 *
 */
public class EmpInfoByOrderFrom extends ZteRequest<EmpIdEssIDResp> {
	
	@ZteSoftCommentAnnotationParam(name = "订单编号", type = "String", isNecessary = "Y", desc = "order_id:订单编号")
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
		// TODO Auto-generated method stub
		return "zte.net.ecsord.params.ecaop.req.EmpInfoByOrderFrom";
	}

}
