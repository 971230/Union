package com.ztesoft.net.ecsord.params.ecaop.req;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy; 
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.util.ZjCommonUtils;

public class OrderChargeReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "支付方式", type = "String", isNecessary = "Y", desc = "1:现金 7:MISPOS")
	private String fund_source;// 暂时默认1
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单号")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name = "操作点", type = "String", isNecessary = "Y", desc = "操作点")
	private String office_id;
	@ZteSoftCommentAnnotationParam(name = "操作员", type = "String", isNecessary = "Y", desc = "操作员")
	private String operator_id;
	@ZteSoftCommentAnnotationParam(name = "交易ID", type = "String", isNecessary = "Y", desc = "交易ID")
	private String trade_id;
	@ZteSoftCommentAnnotationParam(name = "费用", type = "String", isNecessary = "N", desc = "费用")
	private String realfee;
	@ZteSoftCommentAnnotationParam(name = "0:固网;1:移网,可空,默认固网", type = "String", isNecessary = "N", desc = "0:固网;1:移网,可空,默认固网")
	private String op_type;

	public String getOrder_id() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getBssOrderId();
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getFund_source() {
		fund_source = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderPayBusiRequests().get(0).getPay_method();
		return fund_source;
	}

	public void setFund_source(String fund_source) {
		this.fund_source = fund_source;
	}

	public String getOffice_id() {
		String source_from = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getOrder_from();
		String OUT_OFFICE = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OFFICE);
		if (!StringUtil.isEmpty(OUT_OFFICE)) {
			office_id = OUT_OFFICE;
		} else {
			office_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
		}
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public String getOperator_id() {
		String source_from = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getOrder_from();
		String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
		if (!StringUtil.isEmpty(OUT_OPERATOR)) {
			operator_id = OUT_OPERATOR;
		} else {
			operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
		}
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getTrade_id() {
		String fund_source_new = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderPayBusiRequests().get(0).getPay_method();
		if (!StringUtil.isEmpty(fund_source_new) && fund_source_new.equals("7")) {
			try {
				trade_id = DateUtil.getTime7();
			} catch (FrameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return trade_id;
	}

	public void setTrade_id(String trade_id) {
		this.trade_id = trade_id;
	}

	public String getRealfee() {
		return realfee;
	}

	public void setRealfee(String realfee) {
		this.realfee = realfee;
	}

	public String getOp_type() {
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM);
		if ("10078".equals(order_from)) {
			return "0";
		}
		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODS_TYPE);
		op_type = "0";
		IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<Map> list = dcPublicCache.getList("DIC_XXAPP_BSS_OP_TYPE");
		String pname = "";
		for (int i = 0; i < list.size(); i++) {
			if (StringUtil.equals(goods_type, (String) list.get(i).get("pkey"))) {
				pname = (String) list.get(i).get("pname");
				break;
			}
		}
		if (!StringUtil.isEmpty(pname)) {
			op_type = pname;
		}
		
		//融合业务，无线宽带 传1
		String catId = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, null, SpecConsts.CAT_ID);
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
	    String BSS_GET_MONEY = cacheUtil.getConfigInfo("BSS_GET_MONEY");//号卡-总部（AOP）
	    if(BSS_GET_MONEY.contains(goods_type)){//根据商品大类配置传值op_type
	        op_type="1";
	    }
	    // 押金业务,移网传1
	    String BSS_GET_MONEY_DEPOSIT = cacheUtil.getConfigInfo("BSS_GET_MONEY_DEPOSIT");
	    if(BSS_GET_MONEY_DEPOSIT.contains(goods_type)){//根据商品大类配置传值op_type
	        op_type="1";
	    }
	    String BSS_GET_MONEYONE = cacheUtil.getConfigInfo("BSS_GET_MONEY_ISONE");//号卡-总部（AOP）
	    if(BSS_GET_MONEYONE.contains(goods_type)){//根据商品大类配置传值op_type
            op_type="0";
        }
		if(StringUtils.equals(catId, EcsOrderConsts.GOODS_CAT_ID_WUXIANKD) && !"10093".equals(order_from)) {
			op_type = "1";
		}
		if(StringUtils.equals(catId, EcsOrderConsts.GOODS_CAT_ID_WUXIANKD) && "10093".equals(order_from)) {
            op_type = "0";
        }
		return op_type;
	}

	public void setOp_type(String op_type) {
		this.op_type = op_type;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.callOrderCharge";
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

}
