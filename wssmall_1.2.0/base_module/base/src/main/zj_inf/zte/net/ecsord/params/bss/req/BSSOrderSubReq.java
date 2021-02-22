/**
 * 
 */
package zte.net.ecsord.params.bss.req;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.BSSFeeInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSParaVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSPayInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.utils.DataUtil;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * @author zengxianlian
 * @version 2016-03-15
 * @see 开户处理提交
 */
public class BSSOrderSubReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name="操作员ID",type="String",isNecessary="Y",desc="operatorId：操作员ID")
	private String OperatorID;
	@ZteSoftCommentAnnotationParam(name = "省份", type = "String", isNecessary = "Y", desc = "province：省份")
	private String Province;
	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "Y", desc = "city：地市")
	private String City;
	@ZteSoftCommentAnnotationParam(name = "区县", type = "String", isNecessary = "Y", desc = "district：区县")
	private String District;
	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "channelId：渠道编码")
	private String ChannelID;
	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "channelType：渠道类型:参考附录渠道类型编码")
	private String ChannelType;
	@ZteSoftCommentAnnotationParam(name = "接入类型", type = "String", isNecessary = "Y", desc = "01 沃受理； 02 沃云购； 03 ECS； 04 ESS")
	private String AccessType;
	@ZteSoftCommentAnnotationParam(name = "订单类型", type = "String", isNecessary = "Y", desc = "0提交订单;1取消订单")
	private String OrderType;
	@ZteSoftCommentAnnotationParam(name = "ESS订单交易流水", type = "String", isNecessary = "Y", desc = "provOrderId：ESS订单交易流水  为正式提交时使用")
	private String ProvOrderID;
	@ZteSoftCommentAnnotationParam(name = "总部订单ID，与总部开户信息预提交接口交易流水必须一致。", type = "String", isNecessary = "Y", desc = "总部订单ID，与总部开户信息预提交接口交易流水必须一致。")
	private String EcsOrderID;
	@ZteSoftCommentAnnotationParam(name = "发票批次号码", type = "String", isNecessary = "N", desc = "发票批次号码")
	private String TaxBatchNo;
	@ZteSoftCommentAnnotationParam(name = "发票号码", type = "String", isNecessary = "N", desc = "发票号码")
	private String TaxNo;
	@ZteSoftCommentAnnotationParam(name = "发票打印标识", type = "String", isNecessary = "N", desc = "发票打印标识")
	private String TaxType;
	
	@ZteSoftCommentAnnotationParam(name = "收费信息", type = "List", isNecessary = "Y", desc = "feeInfo：收费信息")
	private List<BSSFeeInfoReqVo> FeeInfo =  new ArrayList<BSSFeeInfoReqVo>();
	@ZteSoftCommentAnnotationParam(name="应收总金额",type="String",isNecessary="Y",desc="origTotalFee：应收总金额，单位：厘")
	private String OrigTotalFee;
	@ZteSoftCommentAnnotationParam(name="实收总金额",type="String",isNecessary="Y",desc="origTotalFee：实收总金额，单位：厘")
	private String RealTotalFee;
	@ZteSoftCommentAnnotationParam(name = "客户支付信息", type = "PayInfoVo", isNecessary = "N", desc = "payInfo：客户支付信息")
	private BSSPayInfoReqVo PayInfo = new BSSPayInfoReqVo();
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "ParamsVo", isNecessary = "N", desc = "para：保留字段")
	private BSSParaVo Para = new BSSParaVo();
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}
	
	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	
	private EmpOperInfoVo essOperInfo;

	public String getOperatorID() {
		return getEssOperInfo().getEss_emp_id();
	}

	public void setOperatorID(String operatorID) {
		this.OperatorID = operatorID;
	}

	public String getProvince() {
		return getEssOperInfo().getProvince();
	}

	public void setProvince(String province) {
		this.Province = province;
	}

	public String getCity() {
		return getEssOperInfo().getCity();
	}

	public void setCity(String city) {
		this.City = city;
	}

	public String getDistrict() {
		return getEssOperInfo().getDistrict();
	}

	public void setDistrict(String district) {
		this.District = district;
	}

	public String getChannelID() {
		return getEssOperInfo().getChannel_id();
	}

	public void setChannelID(String channelID) {
		this.ChannelID = channelID;
	}

	public String getChannelType() {
		return getEssOperInfo().getChannel_type();
	}

	public void setChannelType(String channelType) {
		this.ChannelType = channelType;
	}

	public String getProvOrderID() {
		ProvOrderID =CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ACTIVE_NO);
		return ProvOrderID;
	}

	public void setProvOrderID(String provOrderID) {
		this.ProvOrderID = provOrderID;
	}

	public List<BSSFeeInfoReqVo> getFeeInfo() {
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		List<AttrFeeInfoBusiRequest> list=orderTree.getFeeInfoBusiRequests();
		for(AttrFeeInfoBusiRequest req :list){
			if(StringUtils.equals(EcsOrderConsts.BASE_YES_FLAG_1, req.getIs_aop_fee())){
				BSSFeeInfoReqVo vo=new BSSFeeInfoReqVo();
				vo.setFeeID(req.getFee_item_code());
				vo.setFeeCategory(req.getFee_category());//收费项科目
				//vo.setFeeDes(req.getFee_item_name());
				
				String origFee = req.getO_fee_num()*100+"";
				vo.setOrigFee(origFee.substring(0, origFee.indexOf(".")));
				
				String reliefFee = req.getDiscount_fee()*100+"";
				vo.setReliefFee(reliefFee.substring(0, reliefFee.indexOf(".")));
				
				String realFee = req.getN_fee_num()*100+"";
				vo.setRealFee(realFee.substring(0, realFee.indexOf(".")));
				
				vo.setReliefResult(StringUtils.isEmpty(req.getDiscount_reason())?"无":req.getDiscount_reason());
				FeeInfo.add(vo);
			}	
		}
		return FeeInfo;
	}

	public void setFeeInfo(List<BSSFeeInfoReqVo> feeInfo) {
		this.FeeInfo = feeInfo;
	}

	public String getOrigTotalFee() {
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		List<OrderItemBusiRequest> orderItemBusiRequests =orderTree.getOrderItemBusiRequests();
		OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
		OrigTotalFee = orderItemExtBusiRequest.getTotalFee();
		return OrigTotalFee;
	}

	public void setOrigTotalFee(String origTotalFee) {
		this.OrigTotalFee = origTotalFee;
	}

	public BSSPayInfoReqVo getPayInfo() {
		PayInfo.setPayType(EcsOrderConsts.BILL_TYPE_10);//支付类型
		return PayInfo;
	}

	public void setPayInfo(BSSPayInfoReqVo payInfo) {
		this.PayInfo = payInfo;
	}

	public BSSParaVo getPara() {		
		//4G的加参数
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		if(StringUtils.equals(EcsOrderConsts.NET_TYPE_4G, net_type)){
			Para.setParaID(EcsOrderConsts.AOP_4G_PARA_SPEED);
			Para.setParaValue(EcsOrderConsts.AOP_4G_PARA_SPEED_VALUE);
		}
		
		if (DataUtil.checkFieldValueNull(Para))return null;
		return Para;
	}

	public void setPara(BSSParaVo para) {
		this.Para = para;
	}
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.zte.unicomService.zb.openDealSubmitBSS.bss";
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

	public String getAccessType() {
		return EcsOrderConsts.BSS_ACCESS_TYPE_WYG;//牛姐说写死沃云购
	}

	public void setAccessType(String accessType) {
		this.AccessType = accessType;
	}

	public String getOrderType() {
		return "0";
	}

	public void setOrderType(String orderType) {
		this.OrderType = orderType;
	}

	public String getTaxBatchNo() {
		return TaxBatchNo;
	}

	public void setTaxBatchNo(String taxBatchNo) {
		this.TaxBatchNo = taxBatchNo;
	}

	public String getTaxNo() {
		return TaxNo;
	}

	public void setTaxNo(String taxNo) {
		this.TaxNo = taxNo;
	}

	public String getTaxType() {
		return TaxType;
	}

	public void setTaxType(String taxType) {
		this.TaxType = taxType;
	}

	public String getRealTotalFee() {
		return RealTotalFee;
	}

	public void setRealTotalFee(String realTotalFee) {
		this.RealTotalFee = realTotalFee;
	}

	public String getEcsOrderID() {
		EcsOrderID=CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ACTIVE_NO);
		return EcsOrderID;
	}

	public void setEcsOrderID(String ecsOrderID) {
		EcsOrderID = ecsOrderID;
	}
}
