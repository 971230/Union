/**
 * 
 */
package zte.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPayBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.FeeInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.ParamsVo;
import zte.net.ecsord.params.ecaop.req.vo.PayInfoVo;
import zte.net.ecsord.utils.DataUtil;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;

/**
 * 
 * @author chen.yi
 * @date 2016-1-5 下午4:08:29
 * @see 4G存费送费正式提交
 */
public class CunFeeSongFee4GSubmitReqZJ extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name="操作员ID",type="String",isNecessary="Y",desc="operatorId：操作员ID")
	private String operatorId;
	@ZteSoftCommentAnnotationParam(name = "省份", type = "String", isNecessary = "Y", desc = "province：省份")
	private String province;
	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "Y", desc = "city：地市")
	private String city;
	@ZteSoftCommentAnnotationParam(name = "区县", type = "String", isNecessary = "Y", desc = "district：区县")
	private String district;
	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "channelId：渠道编码")
	private String channelId;
	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "channelType：渠道类型:参考附录渠道类型编码")
	private String channelType;
	@ZteSoftCommentAnnotationParam(name = "ESS订单交易流水", type = "String", isNecessary = "Y", desc = "provOrderId：ESS订单交易流水  为正式提交时使用")
	private String provOrderId;
	@ZteSoftCommentAnnotationParam(name = "外围系统订单ID", type = "String", isNecessary = "Y", desc = "ordersId：外围系统订单ID")
	private String ordersId;
	@ZteSoftCommentAnnotationParam(name = "办理业务系统", type = "String", isNecessary = "N", desc = "opeSysType：办理业务系统 1：ESS 2：CBSS")
	private String opeSysType;
	@ZteSoftCommentAnnotationParam(name = "收费信息", type = "List", isNecessary = "N", desc = "feeInfo：收费信息")
	private List<FeeInfoReqVo> feeInfo =  new ArrayList<FeeInfoReqVo>();
	@ZteSoftCommentAnnotationParam(name="总费用正整数",type="String",isNecessary="Y",desc="origTotalFee：总费用正整数，单位：厘")
	private String origTotalFee;
	@ZteSoftCommentAnnotationParam(name = "发票号码", type = "FeeInfoReqVo", isNecessary = "Y", desc = "invoiceNo：发票号码")
	private String invoiceNo;
	@ZteSoftCommentAnnotationParam(name = "客户支付信息", type = "PayInfoVo", isNecessary = "N", desc = "payInfo：客户支付信息")
	private PayInfoVo payInfo = new PayInfoVo();
	@ZteSoftCommentAnnotationParam(name="受理单要求标记",type="String",isNecessary="Y",desc="acceptanceReqTag：受理单要求标记  0：不要求受理单 1：要求受理单")
	private String acceptanceReqTag;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "List", isNecessary = "N", desc = "para：保留字段")
	private List<ParamsVo> para = new ArrayList<ParamsVo>();
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}
	
	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	
	private EmpOperInfoVo essOperInfo;

	public String getOperatorId() {
			this.operatorId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
		return this.operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getProvince() {
			this.province = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PROVINCE);
			return "36";
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		this.city = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);
		if (this.city.trim().length()!=3) {
			this.city = CommonDataFactory.getInstance().getOtherDictVodeValue("city", this.city);
		}
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		this.district = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DISTRICT_CODE);
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getChannelId() {
		this.channelId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CHA_CODE);
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelType() {
		this.channelType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CHANNEL_TYPE);
		return this.channelType.trim().length()==0?"1030100":this.channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	
	
	
	public String getProvOrderId() {
		provOrderId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ACTIVE_NO);;
		return provOrderId;
	}

	public void setProvOrderId(String provOrderId) {
		this.provOrderId = provOrderId;
	}

	public String getOrdersId() {
		ordersId=CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);
		return ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public String getOpeSysType() {
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		opeSysType = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_operSys_by_mobileNet", net_type);
		return StringUtil.isEmpty(opeSysType)?"2":opeSysType; // 业务类型 1：ESS，2：CBSS（默认给2，根据号码网别判断,3G,4G现在办理业务都是在CBSS系统办理）
	}

	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}

	public List<FeeInfoReqVo> getFeeInfo() {
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		List<AttrFeeInfoBusiRequest> list=orderTree.getFeeInfoBusiRequests();
		for(AttrFeeInfoBusiRequest req :list){
			if(StringUtils.equals(EcsOrderConsts.BASE_YES_FLAG_1, req.getIs_aop_fee())){
				FeeInfoReqVo vo=new FeeInfoReqVo();
				vo.setFeeId(req.getFee_item_code());
				vo.setFeeCategory(req.getFee_category());//收费项科目
				vo.setFeeDes(req.getFee_item_name());
				
				String origFee = req.getO_fee_num()*100+"";
				vo.setOrigFee(origFee.substring(0, origFee.indexOf(".")));
				
				String reliefFee = req.getDiscount_fee()*100+"";
				vo.setReliefFee(reliefFee.substring(0, reliefFee.indexOf(".")));
				
				String realFee = req.getN_fee_num()*100+"";
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

	public String getOrigTotalFee() {
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		List<OrderItemBusiRequest> orderItemBusiRequests =orderTree.getOrderItemBusiRequests();
		OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
		origTotalFee = orderItemExtBusiRequest.getTotalFee();
		if(origTotalFee!=null){
			return origTotalFee;
		}else{
			return "0";
		}
		
	}

	public void setOrigTotalFee(String origTotalFee) {
		this.origTotalFee = origTotalFee;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
		if(invoiceNo==""){
			this.invoiceNo=null;
		}
	}

	public PayInfoVo getPayInfo() {
		List<PayInfoVo> ls = new ArrayList<PayInfoVo>();
		PayInfoVo bean = new PayInfoVo();
		OrderPayBusiRequest orderpay = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderPayBusiRequests().get(0);
		bean.setPayType(EcsOrderConsts.BILL_TYPE_10);//支付类型
//		bean.setPayType(orderpay.getPay_method());//支付类型
		String payfFee = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderBusiRequest().getOrder_amount()*100+"";
		bean.setPayFee(payfFee.substring(0, payfFee.indexOf(".")));
		ls.add(bean);
		return bean;
	}

	public void setPayInfo(PayInfoVo payInfo) {
		this.payInfo = payInfo;
	}

	public String getAcceptanceReqTag() {
		acceptanceReqTag = EcsOrderConsts.IS_EASY_ACCOUNT_YES;
		return acceptanceReqTag;
	}

	public void setAcceptanceReqTag(String acceptanceReqTag) {
		this.acceptanceReqTag = acceptanceReqTag;
	}

	public List<ParamsVo> getPara() {		
		//4G的加参数
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		ParamsVo pa = new ParamsVo();
		if(StringUtils.equals(EcsOrderConsts.NET_TYPE_4G, net_type)){
			pa.setParaId(EcsOrderConsts.AOP_4G_PARA_SPEED);
			pa.setParaValue(EcsOrderConsts.AOP_4G_PARA_SPEED_VALUE);
			para.add(pa);
		}
		if (DataUtil.checkFieldValueNull(pa))return null;
		return para;
	}

	public void setPara(List<ParamsVo> para) {
		this.para = para;
	}
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "ecaop.trades.sell.mob.oldu.fee.chg.subzj";
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
