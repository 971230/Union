package zte.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPayBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.FeeInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.ParaVo;
import zte.net.ecsord.params.ecaop.req.vo.PayInfoReqVo;
/**
 * 裸机销售
 * @author Administrator
 *
 */
public class BareMachineSaleReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;

	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "operatorId：操作员ID")
	private String operatorId;

	@ZteSoftCommentAnnotationParam(name = "省份", type = "String", isNecessary = "Y", desc = "province：省份")
	private String province;

	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "Y", desc = "city：地市")
	private String city;

	@ZteSoftCommentAnnotationParam(name = "区县", type = "district", isNecessary = "Y", desc = "district：区县")
	private String district;

	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "channelId：渠道编码")
	private String channelId;

	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "channelType：渠道类型")
	private String channelType;
	
	@ZteSoftCommentAnnotationParam(name = "外围系统订单ID", type = "String", isNecessary = "Y", desc = "ordersId：外围系统订单ID")
	private String ordersId;
	
	@ZteSoftCommentAnnotationParam(name = "终端资源编码，一般是IMEI码", type = "String", isNecessary = "Y", desc = "resourcesCode：终端资源编码，一般是IMEI码")
	private String resourcesCode;
	
	@ZteSoftCommentAnnotationParam(name = "收取费用明细", type = "String", isNecessary = "Y", desc = "收取费用明细")
	private List<FeeInfoReqVo> feeInfo;
	
	@ZteSoftCommentAnnotationParam(name = "客户支付信息", type = "String", isNecessary = "Y", desc = "客户支付信息")
	private List<PayInfoReqVo> payInfo;
	
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "String", isNecessary = "N", desc = "保留字段")
	private List<ParaVo> para;
	
	private EmpOperInfoVo essOperInfo;
	
	
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getOperatorId() {
		return getEssOperInfo().getEss_emp_id();
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
		return getEssOperInfo().getChannel_id();
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelType() {
		return getEssOperInfo().getChannel_type();
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getOrdersId() {
		ordersId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);
		if (StringUtils.isBlank(ordersId)) return null;
		return ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public String getResourcesCode() {
		 resourcesCode = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.TERMINAL_NUM);
		 if (StringUtils.isBlank(resourcesCode)) return null;
			return resourcesCode;
	}

	public void setResourcesCode(String resourcesCode) {
		this.resourcesCode = resourcesCode;
	}

	public List<FeeInfoReqVo> getFeeInfo() {
		List<FeeInfoReqVo> feeInfo = new ArrayList<FeeInfoReqVo>();
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
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

	public List<PayInfoReqVo> getPayInfo() {
		List<PayInfoReqVo> list = new ArrayList<PayInfoReqVo>();
		PayInfoReqVo vo = new PayInfoReqVo();
		String aop_pay_method = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.BILL_TYPE);
		if (!aop_pay_method.equals(EcsOrderConsts.BILL_TYPE_10)) { // 非现金支付
			String payOrg = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_PROVIDER_NAME); // 支付机构名称
			String payNum = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.BANK_ACCOUNT); // 银行支付账号
			vo.setPayOrg(payOrg);
			vo.setPayNum(payNum);
			vo.setPayType(aop_pay_method);
		} else {
			vo.setPayOrg("");
			vo.setPayNum("");
			vo.setPayType(aop_pay_method);
		}
		list.add(vo);
		return list;
	}

	public void setPayInfo(List<PayInfoReqVo> payInfo) {
		this.payInfo = payInfo;
	}

	public List<ParaVo> getPara() {
		if (CommonDataFactory.checkFieldValueNull(para))return null;
		return para;
	}

	public void setPara(List<ParaVo> para) {
		this.para = para;
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

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "ecaop.trades.sell.mob.comm.term.sale";
	}

}
