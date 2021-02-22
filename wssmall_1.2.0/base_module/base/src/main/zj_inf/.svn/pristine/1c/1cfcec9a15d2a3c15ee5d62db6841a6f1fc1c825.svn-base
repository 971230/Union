package zte.net.ecsord.params.taobao.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

public class TbTianjiOrderStatusNoticeReq extends ZteRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1319250438307248398L;
	@ZteSoftCommentAnnotationParam(name="内部订单编号",type="String",isNecessary="Y",desc="notNeedReqStrOrderId：内部订单编号")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name="业务类型",type="String",isNecessary="Y",desc="biz_type：业务类型")
	private String biz_type;
	@ZteSoftCommentAnnotationParam(name="结果码",type="String",isNecessary="Y",desc="code：结果码")
	private String code;
	@ZteSoftCommentAnnotationParam(name="结果描述",type="String",isNecessary="Y",desc="desc：结果描述")
	private String desc;
	@ZteSoftCommentAnnotationParam(name="交易订单号",type="String",isNecessary="Y",desc="order_no：交易订单号")
	private String order_no;
	@ZteSoftCommentAnnotationParam(name="供应商外部订单号",type="String",isNecessary="Y",desc="out_order_no：供应商外部订单号")
	private String out_order_no;
	@ZteSoftCommentAnnotationParam(name="订购结果状态",type="String",isNecessary="Y",desc="success：订购结果状态")
	private boolean success;
	
	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.taobao.syncTbTianjiOrderStatus";
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getBiz_type() {
		OrderTreeBusiRequest orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		biz_type = CommonDataFactory.getInstance().getAttrFieldValue(orderTreeBusiRequest, notNeedReqStrOrderId, AttrConsts.SPECIAL_BUSI_TYPE);
		return biz_type;
	}

	public void setBiz_type(String biz_type) {
		this.biz_type = biz_type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getOrder_no() {
		OrderTreeBusiRequest orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		order_no = orderTreeBusiRequest.getOrderExtBusiRequest().getOut_tid();
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getOut_order_no() {
		OrderTreeBusiRequest orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		out_order_no = CommonDataFactory.getInstance().getAttrFieldValue(orderTreeBusiRequest, notNeedReqStrOrderId, AttrConsts.SUPPLIER_ORDER_ID);
		return out_order_no;
	}

	public void setOut_order_no(String out_order_no) {
		this.out_order_no = out_order_no;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
