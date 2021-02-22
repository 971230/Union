package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.GnotifyDeleteResp;

public class GnotifyDeleteReq extends ZteRequest<GnotifyDeleteResp> {

	@ZteSoftCommentAnnotationParam(name="缺货ID",type="String",isNecessary="Y",desc="缺货ID")
	private String gnotify_id;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.gnoift.delete";
	}

	public String getGnotify_id() {
		return gnotify_id;
	}

	public void setGnotify_id(String gnotify_id) {
		this.gnotify_id = gnotify_id;
	}

}
