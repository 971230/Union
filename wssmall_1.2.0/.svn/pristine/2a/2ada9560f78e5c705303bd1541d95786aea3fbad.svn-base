/**
 * 
 */
package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.CreateRedPkgCpnsResp;


/**
 * @author ZX
 * CreateRedPkgCpnsReq.java
 * 2015-3-17
 */
public class CreateRedPkgCpnsReq extends ZteRequest<CreateRedPkgCpnsResp> {

	@ZteSoftCommentAnnotationParam(name="促销红包ID",type="String",isNecessary="Y",desc="促销红包ID")
	private String red_id;
	
	public String getRed_id() {
		return red_id;
	}
	
	public void setRed_id(String red_id) {
		this.red_id = red_id;
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
		return "zte.orderService.promotionredpkg.create.cpns";
	}
	
}
