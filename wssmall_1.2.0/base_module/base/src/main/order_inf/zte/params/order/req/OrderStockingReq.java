package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;
import consts.ConstsCore;
import params.ZteError;
import params.ZteRequest;
import zte.params.order.resp.OrderStockingResp;

/**
 * 备货
 * @作者 MoChunrun
 * @创建日期 2014-1-8 
 * @版本 V 1.0
 */
public class OrderStockingReq extends ZteRequest<OrderStockingResp> {
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="订单编号")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="发货人编号",type="String",isNecessary="N",desc="发货人编号")
	private String confirm_userid;
	@ZteSoftCommentAnnotationParam(name="发货人名称",type="String",isNecessary="N",desc="发货人名称")
	private String confirm_username;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(order_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "order_id不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.stocking";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getConfirm_userid() {
		return confirm_userid;
	}

	public void setConfirm_userid(String confirm_userid) {
		this.confirm_userid = confirm_userid;
	}

	public String getConfirm_username() {
		return confirm_username;
	}

	public void setConfirm_username(String confirm_username) {
		this.confirm_username = confirm_username;
	}

}
