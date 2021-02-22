package zte.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.ecaop.req.vo.CustomerInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.NumIdVo;
import zte.net.ecsord.params.ecaop.req.vo.Para;
import zte.net.ecsord.params.ecaop.req.vo.RecomDis;
import zte.net.ecsord.params.ecaop.req.vo.ResourcesInfo;
import zte.net.ecsord.params.ecaop.req.vo.SimCardNo;
import zte.net.ecsord.params.ecaop.req.vo.UseCustInfo;
import zte.net.ecsord.params.ecaop.req.vo.UserInfoVO;

import java.util.ArrayList;
import java.util.List;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
//import com.ztesoft.net.util.ZjCommonUtils;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * songqi 后激活订单信息同步接口
 */
public class OrderInfoSynReq extends ZteRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;

	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "operatorId：操作员ID")
	private String operatorId;
	@ZteSoftCommentAnnotationParam(name = "省分", type = "String", isNecessary = "Y", desc = "province：省分")
	private String province;
	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "Y", desc = "city：地市")
	private String city;
	@ZteSoftCommentAnnotationParam(name = "区县", type = "String", isNecessary = "Y", desc = "district：区县")
	private String district;
	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "channelId：渠道编码")
	private String channelId;
	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "channelType：渠道类型")
	private String channelType;
	@ZteSoftCommentAnnotationParam(name = "渠道标示", type = "String", isNecessary = "N", desc = "eModeCode：渠道标示")
	private String eModeCode;
	@ZteSoftCommentAnnotationParam(name = "BSS交互订单号,外围系统订单ID", type = "String", isNecessary = "Y", desc = "ordersId：外围系统订单ID")
	private String ordersId;
	@ZteSoftCommentAnnotationParam(name = "办理业务系统：1：BSS,2：CBSS", type = "Integer", isNecessary = "Y", desc = "opeSysType：办理业务系统：1：BSS,2：CBSS")
	private Integer opeSysType;
	@ZteSoftCommentAnnotationParam(name = "下单时间(YYYYMMDDHHMI24SS)", type = "String", isNecessary = "Y", desc = "orderTime：下单时间(YYYYMMDDHHMI24SS)")
	private String orderTime;
	@ZteSoftCommentAnnotationParam(name = "开户时间(YYYYMMDDHHMI24SS)", type = "String", isNecessary = "Y", desc = "openTime：开户时间(YYYYMMDDHHMI24SS)")
	private String openTime;
	@ZteSoftCommentAnnotationParam(name = "号码资料", type = "NumIdVo", isNecessary = "Y", desc = "numId：号码资料")
	private List<NumIdVo> numId;
	@ZteSoftCommentAnnotationParam(name = "卡信息资料", type = "NumIdVo", isNecessary = "Y", desc = "simCardNo：卡信息资料")
	private List<SimCardNo> simCardNo;
	@ZteSoftCommentAnnotationParam(name = "客户信息", type = "String", isNecessary = "Y", desc = "customerInfo：客户信息")
	private List<CustomerInfoVo> customerInfo;
	@ZteSoftCommentAnnotationParam(name = "用户信息", type = "String", isNecessary = "Y", desc = "用户信息")
	private List<UserInfoVO> userInfo;
	@ZteSoftCommentAnnotationParam(name = " ", type = "String", isNecessary = "Y", desc = " ")
	private List<ResourcesInfo> resourcesInfo;
	@ZteSoftCommentAnnotationParam(name = "责任人使用人信息(集客开户传)", type = "String", isNecessary = "Y", desc = "责任人使用人信息(集客开户传)")
	private List<UseCustInfo> useCustInfo;
	@ZteSoftCommentAnnotationParam(name = "发展人信息", type = "String", isNecessary = "Y", desc = "发展人信息")
	private List<RecomDis> recomDis;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "String", isNecessary = "Y", desc = "保留字段")
	private List<Para> para;

	private EmpOperInfoVo essOperInfo;
	
	@Override
	public String getApiMethodName() {
		return "ecaop.trades.syn.orderinfo.sub_aop";
	}

	@Override
	public void check() throws ApiRuleException {
	}

	public String getOperatorId() {
		this.operatorId = getEssInfo().getEss_emp_id();
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getProvince() {
		this.province = getEssInfo().getProvince();
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		this.city = getEssInfo().getCity();
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		this.district = getEssInfo().getDistrict();
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getChannelId() {
		this.channelId = getEssInfo().getChannel_id();
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelType() {
		this.channelType = getEssInfo().getChannel_type();
		return this.channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String geteModeCode() {

		return eModeCode;
	}

	public void seteModeCode(String eModeCode) {
		this.eModeCode = eModeCode;
	}

	public String getOrdersId() {
		// ordersId =
		// CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
		// AttrConsts.BSSORDERID);
		ordersId = notNeedReqStrOrderId;// 传order_id
		if (StringUtil.isEmpty(ordersId)) {
			return null;
		}
		return ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public Integer getOpeSysType() {
		// 暂无其他类型
		// opeSysType = 1;
		String type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.IS_AOP);
		if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(type)) {
			opeSysType = 1;
		} else {
			opeSysType = 2;
		}
		return opeSysType;
	}

	public void setOpeSysType(Integer opeSysType) {
		this.opeSysType = opeSysType;
	}

	public String getOrderTime() {
		orderTime = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.TID_TIME)
				.replace(" ", "").replace("-", "").replace(":", "");
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOpenTime() {
		openTime = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.BSS_ACCOUNT_TIME);
		if (StringUtil.isEmpty(openTime)) {// 如果实在没有可以传当前时间
			try {
				openTime = DateUtil.getTime("yyyyMMddHHmmss");
			} catch (FrameException e) {
				e.printStackTrace();
				openTime = getOrderTime();
			}
		}
		return openTime.replace(" ", "").replace("-", "").replace(":", "");
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public List<SimCardNo> getSimCardNo() {
		simCardNo = new ArrayList<SimCardNo>();
		SimCardNo simCard = new SimCardNo();
		simCard.setNotNeedReqStrOrderId(notNeedReqStrOrderId);
		simCardNo.add(simCard);
		return simCardNo;
	}

	public void setSimCardNo(List<SimCardNo> simCardNo) {
		this.simCardNo = simCardNo;
	}

	public List<NumIdVo> getNumId() {
		numId = new ArrayList<NumIdVo>();
		NumIdVo numIdVo = new NumIdVo();
		numIdVo.setNotNeedReqStrOrderId(notNeedReqStrOrderId);
		numId.add(numIdVo);
		return numId;
	}

	public void setNumId(List<NumIdVo> numId) {
		this.numId = numId;
	}

	public List<UserInfoVO> getUserInfo() {
		userInfo = new ArrayList<UserInfoVO>();
		UserInfoVO userInfoVO = new UserInfoVO();
		userInfoVO.setNotNeedReqStrOrderId(notNeedReqStrOrderId);
		userInfo.add(userInfoVO);
		return userInfo;
	}

	public void setUserInfo(List<UserInfoVO> userInfo) {
		this.userInfo = userInfo;
	}

	public List<CustomerInfoVo> getCustomerInfo() {
		customerInfo = new ArrayList<CustomerInfoVo>();
		CustomerInfoVo customerInfoVo = new CustomerInfoVo();
		customerInfoVo.setNotNeedReqStrOrderId(notNeedReqStrOrderId);
		customerInfo.add(customerInfoVo);
		return customerInfo;
	}

	public void setCustomerInfo(List<CustomerInfoVo> customerInfo) {
		this.customerInfo = customerInfo;
	}

	public List<ResourcesInfo> getResourcesInfo() {
		resourcesInfo = new ArrayList<ResourcesInfo>();
		ResourcesInfo re = new ResourcesInfo();
		re.setNotNeedReqStrOrderId(notNeedReqStrOrderId);
		resourcesInfo.add(re);
		return resourcesInfo;
	}

	public void setResourcesInfo(List<ResourcesInfo> resourcesInfo) {
		this.resourcesInfo = resourcesInfo;
	}

	public List<UseCustInfo> getUseCustInfo() {
		String type_id = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, null, "type_id");
		if (!"171261137411616561".equals(type_id)) {// 责任人使用人信息(集客开户传)
			return null;
		}
		useCustInfo = new ArrayList<UseCustInfo>();
		UseCustInfo u = new UseCustInfo();
		u.setNotNeedReqStrOrderId(notNeedReqStrOrderId);
		useCustInfo.add(u);
		return useCustInfo;
	}

	public void setUseCustInfo(List<UseCustInfo> useCustInfo) {
		this.useCustInfo = useCustInfo;
	}

	public List<RecomDis> getRecomDis() {
		String recomPersonId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.DEVELOPMENT_CODE);
		String recomPersonName = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.DEVELOPMENT_NAME);
		String recomDepartId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				"development_point_code");
		if (StringUtil.isEmpty(recomDepartId)) {
			recomDepartId = getEssInfo().getDep_id();
		}
		if (StringUtil.isEmpty(recomPersonId) || StringUtil.isEmpty(recomPersonName)) {
			return null;
		}
		recomDis = new ArrayList<RecomDis>();
		RecomDis re = new RecomDis();
		re.setRecomPersonId(recomPersonId);
		re.setRecomPersonName(recomPersonName);
		re.setRecomPersonPhone(recomDepartId);
		recomDis.add(re);
		return recomDis;
	}

	public void setRecomDis(List<RecomDis> recomDis) {
		this.recomDis = recomDis;
	}

	public List<Para> getPara() {
		para = new ArrayList<Para>();
		Para p = new Para();
		p.setNotNeedReqStrOrderId(notNeedReqStrOrderId);
		para.add(p);
		return para;
	}

	public void setPara(List<Para> para) {
		this.para = para;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	
	public EmpOperInfoVo getEssInfo() {
		if (essOperInfo == null) {
			String opercode = CommonDataFactory.getInstance().getOperatorCodeByOrderFrom(notNeedReqStrOrderId);
			if (StringUtil.isEmpty(opercode)) {
				opercode = "zjplxk";
			}
			String cityId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
					AttrConsts.ORDER_CITY_CODE);
			String otherCityCode = CommonDataFactory.getInstance().getOtherDictVodeValue("city", cityId);
			IDaoSupport<EmpOperInfoVo> support = SpringContextHolder.getBean("baseDaoSupport");
			String sql = "select * from Es_OPERATOR_REL_EXT a where a.ORDER_GONGHAO ='" + opercode + "' and a.CITY='"
					+ otherCityCode + "' and a.source_from ='" + ManagerUtils.getSourceFrom() + "'";
			essOperInfo = support.queryForObject(sql, EmpOperInfoVo.class);
		}
		return essOperInfo;
	}

}
