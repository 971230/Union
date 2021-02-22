package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;
import consts.ConstsCore;
import params.ZteError;
import params.ZteRequest;
import zte.params.order.resp.OrderStatusEditResp;

/**
 * 修改订单价格
 * @作者 MoChunrun
 * @创建日期 2014-1-8 
 * @版本 V 1.0
 */
public class OrderPriceEditReq extends ZteRequest<OrderStatusEditResp> {

	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="订单编号")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="订单金额",type="String",isNecessary="Y",desc="修改后的订单金额:-1为不修改")
	private double order_price =-1;
	@ZteSoftCommentAnnotationParam(name="物流费用",type="String",isNecessary="Y",desc="修改后的物流费用：-1为不修改")
	private double ship_price =-1; 
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(order_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "order_id不能为空"));
		if(order_price==-1 && ship_price==-1)CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "order_price、ship_price最少要修改其中的一项"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.orderPrice.edit";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public double getOrder_price() {
		return order_price;
	}

	public void setOrder_price(double order_price) {
		this.order_price = order_price;
	}

	public double getShip_price() {
		return ship_price;
	}

	public void setShip_price(double ship_price) {
		this.ship_price = ship_price;
	}

}
