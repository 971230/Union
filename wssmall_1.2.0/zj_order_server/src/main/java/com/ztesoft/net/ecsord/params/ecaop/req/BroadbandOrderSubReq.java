package com.ztesoft.net.ecsord.params.ecaop.req;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.util.ZjCommonUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

public class BroadbandOrderSubReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单号")
	private String serial_num;
	@ZteSoftCommentAnnotationParam(name = "订单类型", type = "String", isNecessary = "Y", desc = "订单类型 0提交 1取消")
	private String order_type="0";
	@ZteSoftCommentAnnotationParam(name = "办理操作员", type = "String", isNecessary = "Y", desc = "办理操作员")
	private String operator_id;
	@ZteSoftCommentAnnotationParam(name = "办理操作点", type = "String", isNecessary = "Y", desc = "办理操作点")
	private String office_id;
	
	private OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getSerial_num() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getBssOrderId();
		//return "20170616872593293";
	}

	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getOperator_id() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");

		String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");
		
		orderTree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);

		if (orderTree != null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)) {
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
					"order_deal_method");

			// 自定义流程
			if ("2".equals(deal_method)) {
				// 线下方式受理业务，先取传入的操作员
				this.operator_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.OUT_OPERATOR);

				if (StringUtil.isEmpty(this.operator_id)) {
					// 操作员没传的取配置的操作员
					this.operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
				}
			} else {
				// 线上方式直接取配置的操作员
				this.operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
			}
		} else {

			if (StringUtil.isEmpty(this.operator_id)) {
				if (cacheUtil.getConfigInfo("FUKA_ORDER_FROM").contains(CommonDataFactory.getInstance()
						.getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM))) {
					operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
				} else {
					String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
							AttrConsts.OUT_OPERATOR);
					if (!StringUtil.isEmpty(OUT_OPERATOR)) {
						operator_id = OUT_OPERATOR;
					} else {
						operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
					}
				}
				return operator_id;
			} else {
				return this.operator_id;
			}
		}
		
		// 不要删除，会报错的
		orderTree = null;
		
		return this.operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOffice_id() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");

		String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");
		
		orderTree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);

		if (orderTree != null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)) {
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
					"order_deal_method");

			// 自定义流程
			if ("2".equals(deal_method)) {
				// 线下方式受理业务，先取传入的操作点
				this.office_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.OUT_OPERATOR);

				if (StringUtil.isEmpty(this.office_id)) {
					// 操作员没传的取配置的操作点
					this.office_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
				}
			} else {
				// 线上方式直接取配置的操作点
				this.office_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
			}
		} else {

			if (StringUtils.isNotEmpty(this.office_id)) {
				return this.office_id;
			} else {
				if (cacheUtil.getConfigInfo("FUKA_ORDER_FROM").contains(CommonDataFactory.getInstance()
						.getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM))) {
					office_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
				} else {
					String OUT_OFFICE = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
							AttrConsts.OUT_OFFICE);
					if (!StringUtil.isEmpty(OUT_OFFICE)) {
						office_id = OUT_OFFICE;
					} else {
						office_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
					}
				}
			}
		}
		
		// 不要删除，会报错的
		orderTree = null;
		
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}


	public OrderTreeBusiRequest getOrderTree() {
		return orderTree;
	}

	public void setOrderTree(OrderTreeBusiRequest orderTree) {
		this.orderTree = orderTree;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zj.broadband.broadbandOrderSubReq";
	}
	
}
