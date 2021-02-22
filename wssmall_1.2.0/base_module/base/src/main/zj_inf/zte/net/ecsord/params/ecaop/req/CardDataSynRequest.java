package zte.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.FeeInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.ParamsVo;
import zte.net.ecsord.params.ecaop.req.vo.SimCardNoVo;

@SuppressWarnings("rawtypes")
public class CardDataSynRequest extends ZteRequest {

	private static final long serialVersionUID = -1976455240902498175L;
	@ZteSoftCommentAnnotationParam(name="操作员ID",type="String",isNecessary="Y",desc="operatorId：操作员ID")
	private String operatorId;
	@ZteSoftCommentAnnotationParam(name="省分",type="String",isNecessary="Y",desc="province：省分")
	private String province;
	@ZteSoftCommentAnnotationParam(name="地市",type="String",isNecessary="Y",desc="city：地市")
	private String city;
	@ZteSoftCommentAnnotationParam(name="区县",type="String",isNecessary="Y",desc="district：区县")
	private String district;
	@ZteSoftCommentAnnotationParam(name="渠道编码",type="String",isNecessary="Y",desc="channelId：渠道编码")
	private String channelId;
	@ZteSoftCommentAnnotationParam(name="渠道类型",type="String",isNecessary="Y",desc="channelType：渠道类型")
	private String channelType;
	@ZteSoftCommentAnnotationParam(name="外围系统订单ID",type="String",isNecessary="Y",desc="ordersId：外围系统订单ID")
	private String ordersId;
	@ZteSoftCommentAnnotationParam(name="ESS订单交易流水",type="String",isNecessary="Y",desc="provOrderId：ESS订单交易流水")
	private String provOrderId;
	@ZteSoftCommentAnnotationParam(name="手机号码",type="String",isNecessary="Y",desc="numId：手机号码")
	private String numId;
	@ZteSoftCommentAnnotationParam(name="办理业务系统：1：ESS,2：CBSS",type="String",isNecessary="N",desc="opeSysType：办理业务系统：1：ESS,2：CBSS")
	private String opeSysType;
	@ZteSoftCommentAnnotationParam(name="卡信息资料",type="String",isNecessary="N",desc="simCardNos：卡信息资料")
	private List<SimCardNoVo> simCardNo;
	@ZteSoftCommentAnnotationParam(name="收费信息*（ESS从BSS获取到的）",type="String",isNecessary="N",desc="feeInfos：收费信息*（ESS从BSS获取到的）")
	private List<FeeInfoReqVo> feeInfo;
	@ZteSoftCommentAnnotationParam(name="发票批次号码",type="String",isNecessary="N",desc="taxBatchNo：发票批次号码")
	private String taxBatchNo;
	@ZteSoftCommentAnnotationParam(name="发票号码",type="String",isNecessary="N",desc="taxNo：发票号码")
	private String taxNo;
	@ZteSoftCommentAnnotationParam(name="发票打印标识：0 打印发票,1 重打发票,2 补打发票,3 不打发票,注：如果需打印发票，则发票号码不能为空",type="String",isNecessary="Y",desc="taxType：发票打印标识：0 打印发票,1 重打发票,2 补打发票,3 不打发票,注：如果需打印发票，则发票号码不能为空")
	private String taxType;
	@ZteSoftCommentAnnotationParam(name="保留字段",type="String",isNecessary="N",desc="paras：保留字段")
	private List<ParamsVo> para;
	
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;

	private EmpOperInfoVo essOperInfo;

	public String getOperatorId() {
		this.operatorId = getEssOperInfo().getEss_emp_id();
		return this.operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getProvince() {
		return getEssOperInfo().getProvince();
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return getEssOperInfo().getCity();
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return getEssOperInfo().getDistrict();
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getChannelId() {
		this.channelId = getEssOperInfo().getChannel_id();
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelType() {
		this.channelType = getEssOperInfo().getChannel_type();
		return this.channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getOrdersId() {
		if (ordersId == null || "".equals(ordersId)) {
			ordersId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);
		}
		if ("".equals(ordersId)) {
			return null;
		}
		return ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public String getProvOrderId() {
		if (provOrderId == null || "".equals(provOrderId)) {
			provOrderId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ACTIVE_NO);
		}
		if ("".equals(provOrderId)) {
			return null;
		}
		return provOrderId;
	}

	public void setProvOrderId(String provOrderId) {
		this.provOrderId = provOrderId;
	}

	public String getNumId() {
		if (numId == null || "".equals(numId)) {
			numId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
		}
		if ("".equals(numId)) {
			return null;
		}
		return numId;
	}

	public void setNumId(String numId) {
		this.numId = numId;
	}

	public String getOpeSysType() {
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		opeSysType = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_operSys_by_mobileNet", net_type);
		return opeSysType; // 业务类型 1：ESS，2：CBSS（默认给2，根据号码网别判断,3G,4G现在办理业务都是在CBSS系统办理）
	}

	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}

	public List<SimCardNoVo> getSimCardNo() {
		if (simCardNo == null) {
			simCardNo = new ArrayList<SimCardNoVo>();
		}
		if (simCardNo.isEmpty()) {
			SimCardNoVo simCardNoVo = new SimCardNoVo();
			simCardNoVo.setNotNeedReqStrOrderId(notNeedReqStrOrderId);
			simCardNoVo.setCardType("4");
			simCardNo.add(simCardNoVo);
		}
		return simCardNo;
	}

	public void setSimCardNos(List<SimCardNoVo> simCardNos) {
		this.simCardNo = simCardNos;
	}

	public List<FeeInfoReqVo> getFeeInfo() {
		if (feeInfo == null) {
			feeInfo = new ArrayList<FeeInfoReqVo>();
		}
		if (feeInfo.isEmpty()) {
			OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
			List<AttrFeeInfoBusiRequest> list=orderTree.getFeeInfoBusiRequests();
			for(AttrFeeInfoBusiRequest req :list){
				if(StringUtils.equals(EcsOrderConsts.BASE_YES_FLAG_1, req.getIs_aop_fee())){
					FeeInfoReqVo vo=new FeeInfoReqVo();
					vo.setFeeId(req.getFee_item_code());
					vo.setFeeCategory(req.getFee_category());//收费项科目
					vo.setFeeDes(req.getFee_item_name());
					
					String origFee = req.getO_fee_num()*1000+"";
					vo.setOrigFee(origFee.substring(0, origFee.indexOf(".")));
					
					String reliefFee = req.getDiscount_fee()*1000+"";
					vo.setReliefFee(reliefFee.substring(0, reliefFee.indexOf(".")));
					
					String realFee = req.getN_fee_num()*1000+"";
					vo.setRealFee(realFee.substring(0, realFee.indexOf(".")));
					
					vo.setReliefResult(req.getDiscount_reason()+"");
					vo.setReliefResult(req.getDiscount_reason());
					feeInfo.add(vo);
				}	
			}
		}
		return feeInfo;
	}

	public void setFeeInfos(List<FeeInfoReqVo> feeInfos) {
		this.feeInfo = feeInfos;
	}

	public String getTaxBatchNo() {//先不传
		return taxBatchNo;
	}

	public void setTaxBatchNo(String taxBatchNo) {
		this.taxBatchNo = taxBatchNo;
	}

	public String getTaxNo() {//先不传
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getTaxType() {
		taxType = EcsOrderConsts.AOP_TAX_TYPE_3;
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public List<ParamsVo> getParas() {
		return para;
	}

	public void setParas(List<ParamsVo> paras) {
		this.para = paras;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "ecaop.trades.sell.mob.newu.opencarddate.syn";
	}


	public EmpOperInfoVo getEssOperInfo() {
		if(essOperInfo==null){
			essOperInfo = CommonDataFactory.getEssInfoByOrderId(notNeedReqStrOrderId).getOperInfo();
		}
		return essOperInfo;
	}

	public void setEssOperInfo(EmpOperInfoVo essOperInfo) {
		this.essOperInfo = essOperInfo;
	}
}
