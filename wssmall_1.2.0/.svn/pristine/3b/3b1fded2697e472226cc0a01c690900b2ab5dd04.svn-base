package zte.params.ordercenter.req;

import java.util.Map;

import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import params.ZteRequest;
import zte.params.ordercenter.resp.UserOrderdealResp;

public class UserOrderdealReq extends ZteRequest<UserOrderdealResp> {
	
	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="Y",desc="订单ID")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="要修改的订单属性信息",type="String",isNecessary="Y",desc="要修改的订单属性信息Map[key为属性名称,value为修改后的值]")
	private Map<String,String> attrs;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(order_id))CommonTools.addError("-1", "order_id不能为空");
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.user.ordercenter.deal";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public Map<String, String> getAttrs() {
		return attrs;
	}

	public void setAttrs(Map<String, String> attrs) {
		this.attrs = attrs;
	}

}
