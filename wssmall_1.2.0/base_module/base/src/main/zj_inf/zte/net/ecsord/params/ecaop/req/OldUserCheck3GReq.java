/**
 * 
 */
package zte.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.ecaop.req.vo.ActivityInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.ParaVo;
import zte.net.ecsord.params.ecaop.req.vo.ProductInfoReqVo;
import zte.net.ecsord.params.ecaop.resp.OldUserCheck3GResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * @author ZX
 * @version 2015-06-23
 * @see 3G老用户业务校验（AOP二期）
 * @since ecaop.trades.check.oldu.check
 *
 */
public class OldUserCheck3GReq extends ZteRequest<OldUserCheck3GResp> {
	
	@ZteSoftCommentAnnotationParam(name = "订单ID", type = "String", isNecessary = "Y", desc = "订单ID")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "操作员ID")
	private String operatorId;
	@ZteSoftCommentAnnotationParam(name = "省分（客户归属）", type = "String", isNecessary = "Y", desc = "省分（客户归属）")
	private String province;
	@ZteSoftCommentAnnotationParam(name = "地市（客户归属）", type = "String", isNecessary = "Y", desc = "地市（客户归属）")
	private String city;
	@ZteSoftCommentAnnotationParam(name = "区县（客户归属）", type = "String", isNecessary = "Y", desc = "区县（客户归属）")
	private String district;
	@ZteSoftCommentAnnotationParam(name = "渠道编码（联通分配）", type = "String", isNecessary = "Y", desc = "渠道编码（联通分配）")
	private String channelId;
	@ZteSoftCommentAnnotationParam(name = "渠道类型（联通分配）", type = "String", isNecessary = "Y", desc = "渠道类型（联通分配）")
	private String channelType;
	@ZteSoftCommentAnnotationParam(name = "办理业务系统：1：CBSS 2：不限制", type = "String", isNecessary = "Y", desc = "办理业务系统：1：CBSS 2：不限制")
	private String opeSysType;
	@ZteSoftCommentAnnotationParam(name = "业务类型：1-号卡类业务 裸卡?2-合约类业务 包括合约机以及合约号卡?3-上网卡4-上网本5-其它配件类6-沃享", type = "String", isNecessary = "Y", desc = "业务类型")
	private String bipType;
	@ZteSoftCommentAnnotationParam(name = "服务号码", type = "String", isNecessary = "Y", desc = "服务号码")
	private String numId;
	@ZteSoftCommentAnnotationParam(name = "开户时选择的产品信息", type = "ProductInfoReqVo", isNecessary = "Y", desc = "开户时选择的产品信息")
	private List<ProductInfoReqVo> productInfo;
	@ZteSoftCommentAnnotationParam(name = "新活动信息", type = "ActivityInfoReqVo", isNecessary = "Y", desc = "新活动信息")
	private List<ActivityInfoReqVo> activityInfo;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "ParaVo", isNecessary = "N", desc = "保留字段")
	private List<ParaVo> paraVo;
	
	private EmpOperInfoVo essOperInfo;
	
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
	public String getOpeSysType() {
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		opeSysType = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_operSys_by_mobileNet", net_type);
		return opeSysType;// 业务类型 1：ESS，2：CBSS（默认给2，根据号码网别判断,3G,4G现在办理业务都是在CBSS系统办理）
	}
	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}
	public String getBipType() {
		String type_id = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, "", SpecConsts.TYPE_ID);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_bip_type", type_id);
	}
	public void setBipType(String bipType) {
		this.bipType = bipType;
	}
	public String getNumId() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
	}
	public void setNumId(String numId) {
		this.numId = numId;
	}
	public List<ProductInfoReqVo> getProductInfo() {
		ProductInfoReqVo vo = new ProductInfoReqVo();
		List<ProductInfoReqVo> list = new ArrayList<ProductInfoReqVo>();
		vo.setProductId(CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.ESS_CODE)); //下单选择的产品编码，新产品编码
		list.add(vo);
		return list;
	}
	public void setProductInfo(List<ProductInfoReqVo> productInfo) {
		this.productInfo = productInfo;
	}
	public List<ActivityInfoReqVo> getActivityInfo() {
		ActivityInfoReqVo vo = new ActivityInfoReqVo();
		List<ActivityInfoReqVo> ls = new ArrayList<ActivityInfoReqVo>();
		String limit = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10001, SpecConsts.PACKAGE_LIMIT);
		if (StringUtils.isNotBlank(limit)) {
			vo.setActPlanId(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_PACKAGE_ID));
		} else {
			vo.setActPlanId(null);
		}
		vo.setActProtPer(CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10001, SpecConsts.PACKAGE_LIMIT)); // 活动协议期限，单位：月 
		vo.setExtensionTag(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.EXTENSION_TAG)); // 续约标记：0：不续约 1：续约
		vo.setResourcesBrand(CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10000, SpecConsts.BRAND_CODE)); // 终端品牌（购机必传），来自终端查询？
		vo.setResourcesModel(CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10000, SpecConsts.MODEL_CODE)); // 终端型号（购机必传），来自终端查询？
		vo.setActPara(null); //活动扩展字段 （不晓得怎么传值）
		if (StringUtils.isEmpty(vo.getActPlanId()))return null;
		ls.add(vo);
		return ls;
	}
	public void setActivityInfo(List<ActivityInfoReqVo> activityInfo) {
		this.activityInfo = activityInfo;
	}
	public List<ParaVo> getParaVo() {
		return paraVo;
	}
	public void setParaVo(List<ParaVo> paraVo) {
		this.paraVo = paraVo;
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
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}
	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "ecaop.trades.check.oldu.check";
	}

}
