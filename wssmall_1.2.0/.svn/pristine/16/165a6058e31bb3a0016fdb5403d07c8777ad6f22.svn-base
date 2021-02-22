package zte.params.order.req;

import params.ZteError;
import params.ZteRequest;
import zte.params.order.resp.PaymentListResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

/**
 * 订单支付成功状态
 * @作者 wui
 * @创建日期 2014-1-8 
 * @版本 V 1.0
 */
public class PaymentListReq extends ZteRequest<PaymentListResp> {
	@ZteSoftCommentAnnotationParam(name="订单编号",type="Map",isNecessary="Y",desc="order_id：订单编号不能为空。")
	private String order_id;
	
	@ZteSoftCommentAnnotationParam(name="对账类型编码",type="Map",isNecessary="Y",desc="type_code：对账类型编码不能为空，对账时需要该编码。")
	private String type_code;

	@ZteSoftCommentAnnotationParam(name="处理标志",type="Map",isNecessary="Y",desc="deal_flag：2银行支付成功、4银行，计费支付成功。")
	private String deal_flag;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if("".equals(type_code) || null ==type_code){
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "params-->type_code对账类型编码不能为空"));
		}
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.paymentService.payment.paysucc";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public String getDeal_flag() {
		return deal_flag;
	}

	public void setDeal_flag(String deal_flag) {
		this.deal_flag = deal_flag;
	}
	

}
