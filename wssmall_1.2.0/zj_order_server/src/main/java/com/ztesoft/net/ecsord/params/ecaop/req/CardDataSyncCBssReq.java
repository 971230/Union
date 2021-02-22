package com.ztesoft.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import params.ZteRequest;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.BSSParaVo;
import zte.net.ecsord.params.ecaop.req.vo.FeeInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.SimCardNoVo;

public class CardDataSyncCBssReq  extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name = "收取费用明细", type = "String", isNecessary = "Y", desc = "收取费用明细")
	private List<FeeInfoReqVo> feeInfo;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "ParamsVo", isNecessary = "N", desc = "para：保留字段")
	private List<BSSParaVo> para;
	@ZteSoftCommentAnnotationParam(name="卡信息资料",type="String",isNecessary="N",desc="simCardNos：卡信息资料")
	private List<SimCardNoVo> simCardNo;
	@ZteSoftCommentAnnotationParam(name="操作员ID",type="String",isNecessary="Y",desc="操作员ID")
	private String operatorId;
	@ZteSoftCommentAnnotationParam(name="省分",type="String",isNecessary="Y",desc="省分")
	private String province;
	@ZteSoftCommentAnnotationParam(name="地市",type="String",isNecessary="Y",desc="地市")
	private String city;
	@ZteSoftCommentAnnotationParam(name="区县",type="String",isNecessary="Y",desc="区县")
	private String district;
	@ZteSoftCommentAnnotationParam(name="渠道编码",type="String",isNecessary="Y",desc="渠道编码")
	private String channelId;
	@ZteSoftCommentAnnotationParam(name="渠道类型",type="String",isNecessary="Y",desc="渠道类型")
	private String channelType;
	@ZteSoftCommentAnnotationParam(name="外围系统订单ID",type="String",isNecessary="Y",desc="外围系统订单ID")
	private String ordersId;
	@ZteSoftCommentAnnotationParam(name="ESS订单交易流水",type="String",isNecessary="Y",desc="ESS订单交易流水")
	private String provOrderId;
	@ZteSoftCommentAnnotationParam(name="服务号码",type="String",isNecessary="Y",desc="服务号码")
	private String numId;
	@ZteSoftCommentAnnotationParam(name="办理业务系统",type="String",isNecessary="Y",desc="办理业务系统：1：ESS 2：CBSS")
	private String opeSysType;
	@ZteSoftCommentAnnotationParam(name="发票批次号码",type="String",isNecessary="Y",desc="发票批次号码")
	private String taxBatchNo;
	@ZteSoftCommentAnnotationParam(name="发票号码",type="String",isNecessary="Y",desc="发票号码")
	private String taxNo;
	@ZteSoftCommentAnnotationParam(name="操作员ID",type="String",isNecessary="Y",desc="发票打印标识：0 打印发票 1 重打发票 2 补打发票 3 不打发票")
	private String taxType;
	
	
	public List<FeeInfoReqVo> getFeeInfo() {
		List<FeeInfoReqVo> feeInfo = new ArrayList<FeeInfoReqVo>();
//		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(ordersId);
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTreeByOutId(ordersId);
		List<AttrFeeInfoBusiRequest> feeInfoList=orderTree.getFeeInfoBusiRequests();
		for(AttrFeeInfoBusiRequest req :feeInfoList){
			if(StringUtils.equals(EcsOrderConsts.BASE_YES_FLAG_1, req.getIs_aop_fee())){
				FeeInfoReqVo vo=new FeeInfoReqVo();
				vo.setFeeId(req.getFee_item_code());
				vo.setFeeCategory(StringUtils.isEmpty(req.getFee_category()) == true ? "" : req.getFee_category());//收费项科目
				vo.setFeeDes(req.getFee_item_name());
				
				String origFee = req.getO_fee_num()*1000+"";
				vo.setOrigFee(origFee.substring(0, origFee.indexOf(".")));
				
				String reliefFee = req.getDiscount_fee()*1000+"";
				vo.setReliefFee(reliefFee.substring(0, reliefFee.indexOf(".")));
				
				String realFee = req.getN_fee_num()*1000+"";
				vo.setRealFee(realFee.substring(0, realFee.indexOf(".")));
				
				vo.setReliefResult(StringUtils.isEmpty(req.getDiscount_reason())?"无":req.getDiscount_reason());
				feeInfo.add(vo);
			}	
		}
		return feeInfo;
	}

	public void setFeeInfo(List<FeeInfoReqVo> feeInfo) {
		this.feeInfo = feeInfo;
	}
	
	public List<SimCardNoVo> getSimCardNo() {
		if (simCardNo == null) {
			simCardNo = new ArrayList<SimCardNoVo>();
		}
		if (simCardNo.isEmpty()) {
			SimCardNoVo simCardNoVo = new SimCardNoVo();
			simCardNoVo.setNotNeedReqStrOrderId(ordersId);
			simCardNoVo.setCardType("4");
			simCardNo.add(simCardNoVo);
		}
		return simCardNo;
	}

	public void setSimCardNos(List<SimCardNoVo> simCardNos) {
		this.simCardNo = simCardNos;
	}
	
	
	
	public List<BSSParaVo> getPara() {
		return para;
	}

	public void setPara(List<BSSParaVo> para) {
		this.para = para;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getOrdersId() {
		return ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public String getProvOrderId() {
		return provOrderId;
	}

	public void setProvOrderId(String provOrderId) {
		this.provOrderId = provOrderId;
	}

	public String getNumId() {
		return numId;
	}

	public void setNumId(String numId) {
		this.numId = numId;
	}

	public String getOpeSysType() {
		return opeSysType;
	}

	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}

	public String getTaxBatchNo() {
		return taxBatchNo;
	}

	public void setTaxBatchNo(String taxBatchNo) {
		this.taxBatchNo = taxBatchNo;
	}

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public void setSimCardNo(List<SimCardNoVo> simCardNo) {
		this.simCardNo = simCardNo;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.CardDataSyncCBssReq";
	}

}
