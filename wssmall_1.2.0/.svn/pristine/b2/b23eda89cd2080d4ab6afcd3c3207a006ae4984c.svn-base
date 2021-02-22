/**
 * 
 */
package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.RedPkgEditResp;

/**
 * @author ZX
 * RedEditReq.java
 * 2015-3-17
 */
public class RedPkgEditReq extends ZteRequest<RedPkgEditResp> {
	
	@ZteSoftCommentAnnotationParam(name="促销红包ID",type="String",isNecessary="Y",desc="促销红包ID")
	private String red_id;
	@ZteSoftCommentAnnotationParam(name="促销红包名称",type="String",isNecessary="Y",desc="促销红包名称")
	private String name;
	@ZteSoftCommentAnnotationParam(name="促销红包备注",type="String",isNecessary="Y",desc="促销红包备注")
	private String memo;
	
	public String getRed_id() {
		return red_id;
	}

	public void setRed_id(String red_id) {
		this.red_id = red_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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
		return "zte.orderService.promotionredpkg.edit";
	}

}
