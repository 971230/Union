package zte.net.ecsord.params.taobao.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;


/**
 * 
 * @author sguo 
 * 订单详情查询接口
 * 
 */
public class GetTaobaoOrderInfoTAOBAORequset extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="fields：多个用逗号分隔，示例seller_nick, buyer_nick, title, type, created, tid, seller_rate, buyer_rate, status, payment, discount_fee, adjust_fee, post_fee, total_fee, pay_time, end_time, modified, consign_time, buyer_obtain_point_fee, point_fee, real_point_fee, received_payment, commission_fee, buyer_memo, seller_memo, alipay_no, buyer_message, pic_path, num_iid, num, price, cod_fee, cod_status, shipping_type,orders.title, orders.pic_path, orders.price, orders.num, orders.num_iid, orders.sku_id, orders.refund_status, orders.status, orders.oid, orders.total_fee, orders.payment, orders.discount_fee, orders.adjust_fee, orders.sku_properties_name, orders.item_meal_name, orders.outer_sku_id, orders.outer_iid, orders.buyer_rate, orders.seller_rate")
	private String fields;

	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="Y",desc="tid：订单ID")
	private String tid;
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="notNeedReqStrOrderId：订单编号")
	private String notNeedReqStrOrderId;
	
	public String getFields() {
		return "seller_nick, buyer_nick, title, type, created, " +
				"tid, seller_rate, buyer_rate, status, payment, discount_fee," +
				" adjust_fee, post_fee, total_fee, pay_time, end_time, modified, " +
				"consign_time, buyer_obtain_point_fee, point_fee, real_point_fee," +
				" received_payment, commission_fee, buyer_memo, seller_memo, alipay_no, " +
				"buyer_message, pic_path, num_iid, num, price, cod_fee, cod_status, shipping_type," +
				"orders.title, orders.pic_path, orders.price, orders.num, orders.num_iid, orders.sku_id, " +
				"orders.refund_status, orders.status, orders.oid, orders.total_fee, orders.payment, " +
				"orders.discount_fee, orders.adjust_fee, orders.sku_properties_name, orders.item_meal_name," +
				" orders.outer_sku_id, orders.outer_iid, orders.buyer_rate, orders.seller_rate";
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getTid() {
		tid = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_TID);
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.taobao.getTaobaoOrder";
	}

}
