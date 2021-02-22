package com.ztesoft.net.ecsord.params.ecaop.req;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.util.ZjCommonUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

public class CardDataSyncBssReq  extends ZteRequest{
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name="预占号码",type="String",isNecessary="Y",desc="预占号码")
	private String service_num;
	@ZteSoftCommentAnnotationParam(name="地区编码",type="String",isNecessary="Y",desc="地区编码")
	private String region_id;
	@ZteSoftCommentAnnotationParam(name="写卡状态",type="String",isNecessary="Y",desc="0:写卡成功，未正式占用  1:写卡失败  9:写卡成功，正式占用")
	private String write_status;
	@ZteSoftCommentAnnotationParam(name="白卡卡号",type="String",isNecessary="Y",desc="白卡卡号")
	private String iccid;
	@ZteSoftCommentAnnotationParam(name="写卡结果",type="String",isNecessary="Y",desc="0：写卡成功 非0则由读卡器返回的错误代码或系统错误原因")
	private String reason_id;
	@ZteSoftCommentAnnotationParam(name="IMSI值",type="String",isNecessary="Y",desc="IMSI值")
	private String imsi;
	@ZteSoftCommentAnnotationParam(name="发起方业务流水",type="String",isNecessary="Y",desc="发起方业务流水")
	private String proc_id;
	@ZteSoftCommentAnnotationParam(name="操作点",type="String",isNecessary="Y",desc="操作点")
	private String office_id;
	@ZteSoftCommentAnnotationParam(name="操作员",type="String",isNecessary="Y",desc="操作员")
	private String operator_id;
	
	public String getService_num() {
		return service_num;
	}
	public void setService_num(String service_num) {
		this.service_num = service_num;
	}
	public String getRegion_id() {
		String region_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,AttrConsts.ORDER_CITY_CODE);
		region_id = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.BSS_AREA_CODE, region_id);
		return region_id;
	}
	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	public String getWrite_status() {
		return write_status;
	}
	public void setWrite_status(String write_status) {
		this.write_status = write_status;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getReason_id() {
		return StringUtil.isEmpty(this.reason_id)?"0":this.reason_id;
	}
	public void setReason_id(String reason_id) {
		this.reason_id = reason_id;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getProc_id() {
		return proc_id;
	}
	public void setProc_id(String proc_id) {
		this.proc_id = proc_id;
	}

	public String getOffice_id() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(this.notNeedReqStrOrderId);
		String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");

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

			if (cacheUtil.getConfigInfo("FUKA_ORDER_FROM").contains(
					CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM))) {
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
		return office_id;
	}
	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public String getOperator_id() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(this.notNeedReqStrOrderId);
		String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");

		if (orderTree != null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)) {
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(this.notNeedReqStrOrderId,
					"order_deal_method");

			// 自定义流程
			if ("2".equals(deal_method)) {
				// 线下方式受理业务，取传入的操作点
				this.operator_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.OUT_OPERATOR);

				if (StringUtils.isEmpty(this.operator_id)) {
					this.operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
				}
			} else {
				// 线上方式受理取配置的操作点
				this.operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
			}
		} else {

			if (cacheUtil.getConfigInfo("FUKA_ORDER_FROM").contains(
					CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM))) {
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
		}
		return operator_id;
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
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
		return "zte.net.iservice.impl.ZJInfServices.CardDataSyncBssReq";
	}

}
