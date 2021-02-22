package zte.net.ecsord.params.taobao.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class TaobaoIdentityGetRequest extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="内部订单编号",type="String",isNecessary="Y",desc="notNeedReqStrOrderId：内部订单编号")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="Y",desc="tid：订单ID")
	private String tid;
	@ZteSoftCommentAnnotationParam(name="店铺名称",type="String",isNecessary="Y",desc="shop_name：店铺名称")
	private String shop_name;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getTid() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_TID);
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getShop_name() {
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM);
		return CommonDataFactory.getInstance().getTbShopName(order_from);
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.taobao.getIdentity";
	}

}
