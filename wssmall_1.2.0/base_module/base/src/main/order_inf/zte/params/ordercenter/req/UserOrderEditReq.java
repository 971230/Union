package zte.params.ordercenter.req;

import java.util.Map;

import org.drools.core.util.StringUtils;

import params.ZteRequest;
import zte.params.ordercenter.resp.UserOrderEditResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

public class UserOrderEditReq extends ZteRequest<UserOrderEditResp> {
	
	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="Y",desc="订单ID")
	private String order_id;
	
	@ZteSoftCommentAnnotationParam(name="订单属性信息",type="String",isNecessary="Y",desc="订单属性信息Map[key为属性名称,value为修改后的值]属性的key请参考zte.net.ecsord.common.AttrConsts")
	private Map<String,String> attrsMap;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(order_id))CommonTools.addError("-1", "order_id不能为空");
		if(attrsMap==null || attrsMap.size()==0)CommonTools.addError("-1", "attrs不能为空");
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.user.ordercenter.edit";
	}

	public Map<String, String> getAttrsMap() {
		return attrsMap;
	}

	public void setAttrsMap(Map<String, String> attrsMap) {
		this.attrsMap = attrsMap;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	
}
