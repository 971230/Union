package zte.params.order.req;

import params.ZteRequest;
import zte.params.order.resp.PromotionDeleteResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * @author 黄记新
 * 打折请求参数。
 */
public class PromotionDeleteReq extends ZteRequest<PromotionDeleteResp> {

	@ZteSoftCommentAnnotationParam(name = "打折id数组，表示在此范围的打折都将被删除", type = "String", isNecessary = "Y", desc = "打折id数组，表示在此范围的打折都将被删除")
	private String idStr;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.promotion.delete";
	}
	
	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}
}
