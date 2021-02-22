/**
 * 
 */
package zte.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.AttrPackageBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageSubProdBusiRequest;
import zte.net.ecsord.params.busi.req.AttrTmResourceInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderRealNameInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderSubProductBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyAcctInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyActParaVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyActivityInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyCustomerInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyCustomerRemarkVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyNewCustomerInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyNiceInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyNumIdVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyPackageElementVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyPayInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyProductVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyResourcesInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplySimCardNoVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyUserInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.ParamsVo;
import zte.net.ecsord.params.ecaop.req.vo.ProductActivityInfoVo;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.ecsord.utils.DataUtil;
import zte.net.ecsord.utils.SpecUtils;

/**
 * @author ZX
 * @version 2015-05-06
 * @see 开户处理申请
 * 
 */
public class OpenDealApplyReqZJ extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "operatorId：操作员ID")
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
	@ZteSoftCommentAnnotationParam(name = "外围系统订单ID", type = "String", isNecessary = "Y", desc = "ordersId：外围系统订单ID")
	private String ordersId;
	@ZteSoftCommentAnnotationParam(name = "办理业务系统", type = "String", isNecessary = "N", desc = "opeSysType：办理业务系统 1：ESS 2：CBSS")
	private String opeSysType;
	@ZteSoftCommentAnnotationParam(name = "扣款标示", type = "String", isNecessary = "N", desc = "deductionTag：扣款标示 0：不扣款 1：扣款 该字段没传的时候默认扣款")
	private String deductionTag;
	@ZteSoftCommentAnnotationParam(name = "号码资源", type = "NumIdVo", isNecessary = "N", desc = "opeSysType：号码资料(无线上网卡不一定存在)")
	private List<OpenDealApplyNumIdVo> numId;
	@ZteSoftCommentAnnotationParam(name = "卡信息资料", type = "SimCardNoVo", isNecessary = "N", desc = "simCardNo：卡信息资料")
	private List<OpenDealApplySimCardNoVo> simCardNo;
	@ZteSoftCommentAnnotationParam(name = "客户信息", type = "CustomerInfoVo", isNecessary = "N", desc = "CustomerInfoVo：客户信息")
	private List<OpenDealApplyCustomerInfoVo> customerInfo;
	@ZteSoftCommentAnnotationParam(name = "账户信息", type = "AcctInfoVo", isNecessary = "N", desc = "acctInfo：账户信息")
	private List<OpenDealApplyAcctInfoVo> acctInfo;
	@ZteSoftCommentAnnotationParam(name = "用户信息", type = "UserInfoVo", isNecessary = "N", desc = "userInfo：用户信息")
	private List<OpenDealApplyUserInfoVo> userInfo;
	@ZteSoftCommentAnnotationParam(name = "客户业务发展区域", type = "String", isNecessary = "N", desc = "recomDis：客户业务发展区域")
	private String recomDis;
	@ZteSoftCommentAnnotationParam(name = "发展员工", type = "String", isNecessary = "Y", desc = "recomPersonId：发展员工")
	private String recomPersonId;
	@ZteSoftCommentAnnotationParam(name = "发展人名称", type = "String", isNecessary = "Y", desc = "recomPersonName：发展人名称")
	private String recomPersonName;
	@ZteSoftCommentAnnotationParam(name = "发展人渠道", type = "String", isNecessary = "N", desc = "recomDepartId：发展人渠道")
	private String recomDepartId;
	@ZteSoftCommentAnnotationParam(name = "资源信息", type = "ResourcesInfoVo", isNecessary = "N", desc = "resourcesInfo：资源信息")
	private List<OpenDealApplyResourcesInfoVo> resourcesInfo;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "ParamsVo", isNecessary = "N", desc = "para：保留字段")
	private List<ParamsVo> para;
	@ZteSoftCommentAnnotationParam(name = "是否压单标示", type = "String", isNecessary = "N", desc = "delayTag：0:不压单  1:压单  该字段没传的时候默认不压单")
	private String delayTag;
	@ZteSoftCommentAnnotationParam(name = "是否校验黑名单", type = "String", isNecessary = "N", desc = "chkBlcTag：是否校验黑名单0：校验1：不校验该字段没传的时候默认0：校验")
	private String chkBlcTag;
	@ZteSoftCommentAnnotationParam(name = "销售模式类型", type = "String", isNecessary = "N", desc = "saleModType：0：自营厅行销模式 1：行销渠道直销模式")
	private String saleModType;
	@ZteSoftCommentAnnotationParam(name = "是否行销装备", type = "String", isNecessary = "N", desc = "chkBlcTag：0：否，1：是 该字段没传的时候默认 0：否")
	private String markingTag;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "N", desc = "订单是否同步到电子渠道激活1：同步默认该字段不传")
	private String isAfterActivation;

	private EmpOperInfoVo essOperInfo;

	public String getSaleModType() {
		saleModType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SALE_MOD_TYPE);
		return saleModType;
	}

	public void setSaleModType(String saleModType) {
		this.saleModType = saleModType;
	}

	public String getMarkingTag() {
		markingTag = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.MARKING_TAG);
		return markingTag;
	}

	public void setMarkingTag(String markingTag) {
		this.markingTag = markingTag;
	}

	public String getOperatorId() {

		String order_id=getNotNeedReqStrOrderId();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		if(orderTree!=null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())) {
			this.operatorId = getEssOperInfo().getEss_emp_id();
		}else
			this.operatorId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,AttrConsts.OUT_OPERATOR);
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
		if (this.city.trim().length() != 3) {
			this.city = CommonDataFactory.getInstance().getOtherDictVodeValue("city", this.city);
		}
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		this.district = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.DISTRICT_CODE);
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getChannelId() {
		String order_id=getNotNeedReqStrOrderId();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		if(orderTree!=null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())) {
			this.channelId = getEssOperInfo().getChannel_id();
		}else
			this.channelId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.ORDER_CHA_CODE);
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelType() {
		String order_id=getNotNeedReqStrOrderId();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		if(orderTree!=null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())) {
			this.channelType = getEssOperInfo().getChannel_type();
		}else
			this.channelType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.CHANNEL_TYPE);
		return this.channelType.trim().length() == 0 ? "1030100" : this.channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getOrdersId() {
		ordersId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);
		if (StringUtils.isBlank(ordersId))
			return null;
		return ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public String getOpeSysType() {
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002,
				null, SpecConsts.NET_TYPE);
		opeSysType = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_operSys_by_mobileNet", net_type);
		return opeSysType; // 业务类型
							// 1：ESS，2：CBSS（默认给2，根据号码网别判断,3G,4G现在办理业务都是在CBSS系统办理）
	}

	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}

	public String getDeductionTag() {
		return EcsOrderConsts.AOP_IS_YES;// 扣款标示 0：不扣款 1：扣款 该字段没传的时候默认扣款
	}

	public void setDeductionTag(String deductionTag) {
		this.deductionTag = deductionTag;
	}

	public List<OpenDealApplyNumIdVo> getNumId() {
		numId = getNumIdVo(notNeedReqStrOrderId);
		if (numId == null || numId.size() <= 0)
			return null;
		return numId;
	}

	public void setNumId(List<OpenDealApplyNumIdVo> numId) {
		this.numId = numId;
	}

	public String getRecomDis() {
		if (StringUtils.isBlank(recomDis))
			return null;
		return recomDis;
	}

	public void setRecomDis(String recomDis) {
		this.recomDis = recomDis;
	}

	public String getRecomPersonId() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.ORDER_FROM);
		if (cacheUtil.getConfigInfo("CB_RECOM_ORDER_FROM").contains(order_from)) {
			recomPersonId = cacheUtil.getConfigInfo("RECOMPERSONID" + order_from);
		} else {
			//recomPersonId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,AttrConsts.DEVELOPMENT_CODE);
	         recomPersonId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,"develop_code_new");

			if (StringUtils.isBlank(recomPersonId))
				return null;
		}
		return recomPersonId;
	}

	public void setRecomPersonId(String recomPersonId) {
		this.recomPersonId = recomPersonId;
	}

	public String getRecomPersonName() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.ORDER_FROM);
		if (cacheUtil.getConfigInfo("CB_RECOM_ORDER_FROM").contains(order_from)) {
			recomPersonName = cacheUtil.getConfigInfo("RECOMPERSONNAME" + order_from);
		} else {
		/*	recomPersonName = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
					AttrConsts.DEVELOPMENT_NAME);*/
			recomPersonName = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,"develop_name_new");

			if (StringUtils.isBlank(recomPersonName))
				return null;
		}
		return recomPersonName;
	}

	public void setRecomPersonName(String recomPersonName) {
		this.recomPersonName = recomPersonName;
	}

	public String getRecomDepartId() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.ORDER_FROM);
		if (cacheUtil.getConfigInfo("CB_RECOM_ORDER_FROM").contains(order_from)) {
			recomDepartId = cacheUtil.getConfigInfo("RECOMDEPARTID" + order_from);
		} else {
			recomDepartId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
					"development_point_code");
			if (StringUtils.isBlank(recomDepartId)) {
				recomDepartId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						"develop_point_code_new");
			}
			if (StringUtils.isBlank(recomDepartId)) {
				return null;
			}
		}
		return recomDepartId;
	}

	public void setRecomDepartId(String recomDepartId) {
		this.recomDepartId = recomDepartId;
	}

	public List<OpenDealApplySimCardNoVo> getSimCardNo() {
		String sim_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SIM_TYPE);
		if (EcsOrderConsts.SIM_TYPE_BCK.equals(sim_type)) {// 半成卡
			simCardNo = getSimCardNoVo(notNeedReqStrOrderId);
		}
		if (simCardNo == null || simCardNo.size() <= 0)
			return null;
		return simCardNo;
	}

	public void setSimCardNo(List<OpenDealApplySimCardNoVo> simCardNo) {
		this.simCardNo = simCardNo;
	}

	public List<OpenDealApplyCustomerInfoVo> getCustomerInfo() {
		customerInfo = getCustomerInfoVo(notNeedReqStrOrderId);
		if (customerInfo == null || customerInfo.size() <= 0)
			return null;
		return customerInfo;
	}

	public void setCustomerInfo(List<OpenDealApplyCustomerInfoVo> customerInfo) {
		this.customerInfo = customerInfo;
	}

	public List<OpenDealApplyAcctInfoVo> getAcctInfo() {
		acctInfo = getAcctInfoVo(notNeedReqStrOrderId);
		if (acctInfo == null || acctInfo.size() <= 0)
			return null;
		return acctInfo;
	}

	public void setAcctInfo(List<OpenDealApplyAcctInfoVo> acctInfo) {
		this.acctInfo = acctInfo;
	}

	public List<OpenDealApplyUserInfoVo> getUserInfo() {
		userInfo = getUserInfoVo(notNeedReqStrOrderId);
		if (userInfo == null || userInfo.size() <= 0)
			return null;
		return userInfo;
	}

	public void setUserInfo(List<OpenDealApplyUserInfoVo> userInfo) {
		this.userInfo = userInfo;
	}

	public List<OpenDealApplyResourcesInfoVo> getResourcesInfo() throws Exception {
		resourcesInfo = getResourcesInfoVo(notNeedReqStrOrderId);
		if (resourcesInfo == null || resourcesInfo.size() <= 0)
			return null;
		return resourcesInfo;
	}

	public void setResourcesInfo(List<OpenDealApplyResourcesInfoVo> resourcesInfo) {
		this.resourcesInfo = resourcesInfo;
	}

	public List<ParamsVo> getPara() {
	    List<ParamsVo> plist = new ArrayList<ParamsVo>();
        // 4G的加参数
        String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002,
                null, SpecConsts.NET_TYPE);
        if (StringUtils.equals(EcsOrderConsts.NET_TYPE_4G, net_type)) {
            ParamsVo vo = new ParamsVo();
            vo.setParaId(EcsOrderConsts.AOP_4G_PARA_SPEED);
            vo.setParaValue(EcsOrderConsts.AOP_4G_PARA_SPEED_VALUE);
            plist.add(vo);
        }

        String NEWKSVIP = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, null, "NEWKSVIP");
        if (!StringUtil.isEmpty(NEWKSVIP)) {
            ParamsVo vo = new ParamsVo();
            vo.setParaId("NEWKSVIP");
            vo.setParaValue(NEWKSVIP);
            plist.add(vo);
        }

        if (plist.size() == 0)
            return null;
        return plist;

	}

	public void setPara(List<ParamsVo> para) {
		this.para = para;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	/**
	 * 获取号码信息
	 * 
	 * @param order_id
	 * @return
	 */
	private List<OpenDealApplyNumIdVo> getNumIdVo(String order_id) {
		List<OpenDealApplyNumIdVo> ls = new ArrayList<OpenDealApplyNumIdVo>();
		OpenDealApplyNumIdVo vo = new OpenDealApplyNumIdVo();
		vo.setSerialNumber(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM)); // Y
																												// 号码
		// 获取号码节点
		OrderPhoneInfoBusiRequest phoneInfo = CommonDataFactory.getInstance().getOrderTree(order_id)
				.getPhoneInfoBusiRequest();
		vo.setProKey(phoneInfo.getProKey()); // Y 资源预占关键字
		// 是否靓号
		// String isGoodNum =
		// CommonDataFactory.getInstance().getAttrFieldValue(order_id,SpecConsts.NUMERO_IS_LUCKY);
		String classid = phoneInfo.getClassId() + "";
		// 靓号才需要传这个节点
		/*
		 * if(StringUtils.equals(isGoodNum, EcsOrderConsts.ORDER_TYPE_02)){
		 * vo.setNiceInfo(getGoodNumInfoVo(phoneInfo)); // 靓号信息 }
		 */
		if (!StringUtils.isEmpty(classid) && !StringUtils.equals(classid, "9")) {
			vo.setNiceInfo(getGoodNumInfoVo(phoneInfo)); // 靓号信息
		}
		if (DataUtil.checkFieldValueNull(vo))
			return null;
		ls.add(vo);
		return ls;
	}

	/**
	 * 获取靓号信息
	 * 
	 * @param order_id
	 * @return
	 */
	private OpenDealApplyNiceInfoVo getGoodNumInfoVo(OrderPhoneInfoBusiRequest phoneInfo) {
		OpenDealApplyNiceInfoVo vo = new OpenDealApplyNiceInfoVo();

		vo.setAdvancePay(phoneInfo.getAdvancePay()); // Y 预存话费金额
		vo.setAdvanceCom(phoneInfo.getAdvanceCom()); // N 普通预存
		vo.setAdvanceSpe(phoneInfo.getAdvanceSpe()); // N 专项预存
		vo.setNumThawTim(phoneInfo.getNumThawTim()); // N 返还时长
		vo.setNumThawPro(phoneInfo.getNumThawPro()); // N 分月返还金额
		vo.setClassId(phoneInfo.getClassId() + ""); // Y 号码等级：1：一级靓号 2：二级靓号
													// 3：三级靓号 4：四级靓号 5：五级靓号
													// 6：六级靓号 9：普通号码
		vo.setLowCostChe(phoneInfo.getLowCostChe()); // N 考核期最低消费
		vo.setTimeDurChe(phoneInfo.getTimeDurChe()); // N 考核时长
		vo.setChangeTagChe(phoneInfo.getChangeTagChe() + ""); // N 考核期是否过户 0:允许
																// 1：不允许
		vo.setCancelTagChe(phoneInfo.getCancelTagChe() + ""); // N 考核期是否销户 0:允许
																// 1：不允许
		vo.setBremonChe(phoneInfo.getBremonChe()); // N 考核期违约金月份
		vo.setLowCostPro(phoneInfo.getLowCostPro()); // N 协议期最低消费
		vo.setTimeDurPro(phoneInfo.getTimeDurPro()); // N 协议时长 当值为00000时表示无期限
		vo.setChangeTagPro(phoneInfo.getChangeTagPro() + ""); // N 协议期是否过户 0:允许
																// 1：不允许
		vo.setCancelTagPro(phoneInfo.getCancelTagPro() + ""); // N 协议期是否销户 0:允许
																// 1：不允许
		vo.setBroMonPro(phoneInfo.getBroMonPro()); // N 协议期违约金月份

		if (DataUtil.checkFieldValueNull(vo))
			return null;
		return vo;
	}

	/**
	 * 获取卡信息(目前只有半成卡用到)
	 * 
	 * @param order_id
	 * @return
	 */
	private List<OpenDealApplySimCardNoVo> getSimCardNoVo(String order_id) {
		List<OpenDealApplySimCardNoVo> ls = new ArrayList<OpenDealApplySimCardNoVo>();
		OpenDealApplySimCardNoVo vo = new OpenDealApplySimCardNoVo();
		vo.setCardDataProcId(""); // 获取写卡数据业务流水（此字段用途是什么，暂时还未明确）
		String ICCID = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID);
		vo.setSimId(null == ICCID ? "" : ICCID); // simId：如果是成卡是USIM卡号，如果是白卡是ICCID(卡号，是否指ICCID??)
		vo.setImsi(""); // 新IMSI号(新IMSI号，未读写卡，何来此信息？)
		vo.setCardType(""/*
							 * CommonDataFactory.getInstance().
							 * getAttrFieldValue( order_id, "WHITE_CART_TYPE")
							 */); // cardType：白卡类型 参考附录白卡类型说明(2015-05-19
								// 朱红玉说这个整点不要传，不管取到娶不到)
		vo.setCardData(""); // cardData：白卡数据(白卡数据，未读写卡，何来此信息？)
		vo.setCardFee(""); // cardFee：卡费，单位：厘
		// if (DataUtil.checkFieldValueNull(vo))return null;
		ls.add(vo);
		return ls;
	}

	/**
	 * 获取客户信息
	 * 
	 * @param order_id
	 * @return
	 */
	private List<OpenDealApplyCustomerInfoVo> getCustomerInfoVo(String order_id) {
		List<OpenDealApplyCustomerInfoVo> ls = new ArrayList<OpenDealApplyCustomerInfoVo>();
		OpenDealApplyCustomerInfoVo vo = new OpenDealApplyCustomerInfoVo();
		vo.setAuthTag("0"); // Y 鉴权标识 0：未鉴权 1：已鉴权(如何理解？是否和客户信息校验理解一样？)
		vo.setRealNameType("0"); // Y 客户实名标识 0：实名 1：匿名(现在都是0)
		vo.setCustType(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD)); // Y
																										// 新老客户标识
																										// 0：新增客户
																										// 1：老客户(完客户资料校验接口之后就知道是新客户还是老客户了)
		if (vo.getCustType() != null && !vo.getCustType().equals(EcsOrderConsts.IS_OLD_0)) { // ZX
																								// add
																								// 2016-01-22
																								// 15:50:29(注：只是在原基础上增加了if判断)
			vo.setCustId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CUST_ID)); // N
																											// 老客户ID
		}
		vo.setNewCustomerInfo(getNewCustomerInfoVo(order_id)); // 新客户信息
																// OpenDealApplyNewCustomerInfoVo
		if (DataUtil.checkFieldValueNull(vo))
			return null;
		ls.add(vo);
		return ls;
	}

	/**
	 * 获取新客户信息
	 * 
	 * @param order_id
	 * @return
	 */
	private List<OpenDealApplyNewCustomerInfoVo> getNewCustomerInfoVo(String order_id) {
		List<OpenDealApplyNewCustomerInfoVo> ls = new ArrayList<OpenDealApplyNewCustomerInfoVo>();
		OpenDealApplyNewCustomerInfoVo vo = new OpenDealApplyNewCustomerInfoVo();
		vo.setCertType(getCertType(order_id)); // Y 证件类型 参考附录证件类型说明
		vo.setCertNum(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM)); // Y
																												// 证件号码
		vo.setCertAdress(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS)); // Y
																												// 证件地址
		vo.setCustomerName(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_OWNER_NAME)); // Y
																														// 客户名称（不能全数字）
		String certExpireDate = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
				AttrConsts.CERT_FAILURE_TIME);
		certExpireDate = certExpireDate.replace("-", "");
		certExpireDate = StringUtils.isEmpty(certExpireDate) ? "20591231" : certExpireDate;
		vo.setCertExpireDate(certExpireDate.substring(0, 8)); // Y 格式：yyyymmdd
																// 证件失效日期(考虑默认)
		vo.setContactPerson(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_NAME)); // N
																												// 联系人（不能全数字）
		vo.setContactPhone(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REVEIVER_MOBILE)); // Y
																														// 联系人电话>6位
		vo.setContactAddress(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CONTACT_ADDR)); // N
																													// 通讯地址
		vo.setCustType(getCustType(order_id)); // N 客户类型： 01：个人客户 02：集团客户
		vo.setCustomerRemark(getCustomerRemarkVo(order_id)); // N 客户备注
																// OpenDealApplyCustomerRemarkVo
																// (2015-05-06下午朱红玉说整个对象都可以不传)
		if (DataUtil.checkFieldValueNull(vo))
			return null;
		ls.add(vo);
		return ls;
	}

	/**
	 * 获取客户备注信息
	 * 
	 * @param order_id
	 * @return
	 */
	private List<OpenDealApplyCustomerRemarkVo> getCustomerRemarkVo(String order_id) {
		List<OpenDealApplyCustomerRemarkVo> ls = new ArrayList<OpenDealApplyCustomerRemarkVo>();
		OpenDealApplyCustomerRemarkVo vo = new OpenDealApplyCustomerRemarkVo();
		vo.setCustomerRemarkId(null);// Y 客户备注ID(未知，不晓得填什么)
		vo.setCustomerRemarkValue(null); // Y客户备注信息(未知，不晓得填什么)
		if (DataUtil.checkFieldValueNull(vo))
			return null;
		ls.add(vo);
		return ls;
	}

	/**
	 * 获取帐号信息
	 * 
	 * @param order_id
	 * @return
	 */
	private List<OpenDealApplyAcctInfoVo> getAcctInfoVo(String order_id) {
		List<OpenDealApplyAcctInfoVo> ls = new ArrayList<OpenDealApplyAcctInfoVo>();
		OpenDealApplyAcctInfoVo vo = new OpenDealApplyAcctInfoVo();
		vo.setCreateOrExtendsAcct("0");// Y 是否继承账户标示 0：新建账户 1：继承老账户（2015-05-06
										// 朱红玉说我们系统都是新用户）
		vo.setAccountPayType(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BILL_TYPE)); // Y
																													// 账户付费方式，默认：10
																													// 参考附录证件类型说明
		vo.setDebutySn(null); // N 合帐号码(未知，暂时不晓得填什么)
		if (DataUtil.checkFieldValueNull(vo))
			return null;
		ls.add(vo);
		return ls;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param order_id
	 * @return
	 */
	private List<OpenDealApplyUserInfoVo> getUserInfoVo(String order_id) {
		List<OpenDealApplyUserInfoVo> ls = new ArrayList<OpenDealApplyUserInfoVo>();
		OpenDealApplyUserInfoVo vo = new OpenDealApplyUserInfoVo();
		vo.setUserType("1"); // Y 用户类型 1：新用户 2：老用户 （2015-05-06 朱红玉说我们系统都是新用户）
		vo.setBipType(getBipType_id(order_id)); // Y 业务类型： 1：号卡类业务 2：合约类业务 3：上网卡
												// 4：上网本 5：其它配件类 6：自备机合约类业务
		vo.setIs3G(getNet_type(order_id)); // Y 0-2G 1-3G 2-4G
		vo.setSerType(getSerType(order_id)); // Y 受理类型 1：后付费 2：预付费 3：准预付费
		vo.setPackageTag(getPackageTag(order_id)); // Y 套包销售标记 0：非套包销售 1：套包销售
		vo.setFirstMonBillMode(getFirstBillMode(order_id)); // N 首月付费模式
															// 01：标准资费（免首月月租）
															// 02：全月套餐 03：套餐减半
		vo.setCheckType(getCheckType(order_id)); // N 认证类型：01：本地认证 02：公安认证
													// 03：二代证读卡器 （默认 02）
		vo.setCreditVale(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CREDIT_CLASS)); // N
																												// 信用等级，（3G后付费业务取值范围A、B、C）
		vo.setCheckCreditVale(null); // N 用户选择信用等级（3G后付费业务取值范围A、B、C） （2015-05-06
										// 朱红玉说未知）
		vo.setUserPwd(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PASSWORD)); // N
																											// 用户密码
		String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.TYPE_ID);
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String xx_card_center = cacheUtil.getConfigInfo("xx_card_center");//号卡-总部（AOP）
        Boolean flag = false;
        if(!StringUtils.isEmpty(xx_card_center) && xx_card_center.contains(type_id)){
                flag = true;
        }
		if (flag ||StringUtil.equals(type_id, SpecConsts.TYPE_ID_GOODS_CBSS) || "226723870818693120".equals(type_id)) {// 号卡-总部（AOP）
			vo.setProduct(getProductVo2(order_id)); // + 开户时选择的产品信息
													// OpenDealApplyProductVo
													// (需要一个4G自由组合套餐数据样例？)
			vo.setActivityInfo(getActivityInfo2(order_id)); // 开户时活动信息
															// OpenDealApplyActivityInfoVo
		} else {
			vo.setProduct(getProductVo(order_id)); // + 开户时选择的产品信息
													// OpenDealApplyProductVo
													// (需要一个4G自由组合套餐数据样例？)
			vo.setActivityInfo(getActivityInfo(order_id)); // 开户时活动信息
															// OpenDealApplyActivityInfoVo
		}

		vo.setGroupFlag(getGroupFlag(order_id)); // N 集团标识 0：非集团用户 1：集团用户
		if (StringUtils.equals(vo.getGroupFlag(), EcsOrderConsts.IS_DEFAULT_VALUE)) {// 集团才有担保
			vo.setGroupId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GROUP_CODE));// N
																												// 集团ID
			vo.setAppType(getAppType(vo.getGroupFlag()));// N 应用类别，当加入具体集团时为必填
															// 0：行业应用 1：非行业应用
			vo.setSubAppType(null);// N 应用子类别 参考附录应用子类别说明
			vo.setGuarantorType(null);// N 担保方式 参见附录
			vo.setGuaratorID(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GUARANTEE_INFO));// N
																														// 担保信息参数
																														// 根据担保类型确定
																														// 如担保类型为01
																														// 则填担保人客户ID
																														// 如担保类型为02
			// 则填退款方式（11 按年，12 一次性） 如担保类型为03 则置空 如担保类型为04
			// 则填无担保原因 如担保类型为05 则填社保卡卡号 如担保类型为06
			// 则填“单位名称，单位编码，合同协议编码” 如担保类型为07 则填“银行名称，银行编码
			// 如担保类型为08 则填“银行或保险公司名称，银行或保险公司编码，合同协议编码”
			// 如担保类型为09
			// 则填“银行公司名称,银行公司编码,合同协议编码,冻结存款流水号,冻结金额(单位：元)”
			// 如担保类型为10 则填“担保公司名称，担保公司编码，合同协议编码”
			// 如担保类型为11
			// 则填“单位名称，单位编码，银行名称，银行编码，合同协议编码，冻结金额（单位：元）”如担保类型为12
			// 则填“担保公司名称，担保公司编码，合同协议编码”"
			vo.setGuCertType(
					CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GUARANTOR_CERTI_TYPE));// N
																													// 被担保人证件类型
			vo.setGuCertNum(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GUARANTOR_CERTI_NO));// N
																														// 被担保人证件号码
		}
		vo.setPayInfo(getPayInfoVo(order_id));// Y 支付信息 OpenDealApplyPayInfoVo

		if (DataUtil.checkFieldValueNull(vo))
			return null;
		ls.add(vo);
		return ls;
	}

	/**
	 * 获取产品信息
	 * 
	 * @param order_id
	 * @return
	 */
	private List<OpenDealApplyProductVo> getProductVo(String order_id) {
		List<OpenDealApplyProductVo> ls = new ArrayList<OpenDealApplyProductVo>();
		OpenDealApplyProductVo vo = new OpenDealApplyProductVo();
		vo.setProductId(CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null,
				SpecConsts.ESS_CODE)); // Y 产品标识
		vo.setProductMode("1"); // Y 产品模式 1：主产品 2：附加产品 （）
		vo.setPackageElement(getPackageElement(vo.getProductId())); // *
																	// 产品下附加包及包元素（资费，服务）
																	// OpenDealApplyPackageElementVo
		vo.setProductCode(null); // N 套包编码，北六无线上网卡必传
		if (DataUtil.checkFieldValueNull(vo))
			return null;
		ls.add(vo);

		// 附加产品处理
		List<OrderSubProductBusiRequest> subProductLists = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getOrderSubProductBusiRequest();
		// 获取附加产品数据
		for (OrderSubProductBusiRequest subProduct : subProductLists) {
			// 只处理主卡的可选包 2016-05-06
			if (null != subProduct.getProd_inst_id() && "0".equals(subProduct.getProd_inst_id())) {
				OpenDealApplyProductVo subVo = new OpenDealApplyProductVo();
				subVo.setProductId(subProduct.getSub_pro_code());
				subVo.setPackageElement(
						getSubPackageElement(subProduct.getSub_pro_code(), subProduct.getSub_pro_inst_id()));
				subVo.setProductMode("2");
				subVo.setProductCode(null);
				ls.add(subVo);
			}
		}
		
		IGoodsManager goodsManager = SpringContextHolder.getBean("goodsManager");
		//所有货品
		List<Goods> products = goodsManager.listGoodsRelProducts(
				CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "goods_id"));
		// 附加产品
		List<Goods> productList = new ArrayList<Goods>();
		for (Goods p : products) {
			if ("181251045132000162".equals(p.getCat_id())) {// 总部-附加产品 货品类型
				productList.add(p);
			}
		}
		if (productList.size() > 0) {
			Set<String> productIdStrs = new HashSet<String>();// 多个附加产品的编码
			for (Goods g : productList) {
				Map specs = SpecUtils.getProductSpecMap(g);
				if (specs != null && !specs.isEmpty()) {
					String productId = specs.get("cbss_product_code") == null ? "" : specs.get("cbss_product_code") + "";
					if (!StringUtil.isEmpty(productId)) {
						productIdStrs.add(productId);
					}
				}
			}
			if (!productIdStrs.isEmpty()) {
				for (String pid : productIdStrs) {// 不同的产品编码可能会有多个包元素编码
					if (!StringUtil.isEmpty(pid)) {
						OpenDealApplyProductVo subVo = new OpenDealApplyProductVo();
						subVo.setProductId(pid);
						subVo.setProductMode("2");
						subVo.setProductCode(null);
						
						List<OpenDealApplyPackageElementVo> packageElement = new ArrayList<OpenDealApplyPackageElementVo>();
						for (Goods g : productList) {
							Map specs = SpecUtils.getProductSpecMap(g);
							if (specs != null && !specs.isEmpty()) {
								String productId = specs.get("cbss_product_code") == null ? "" : specs.get("cbss_product_code") + "";
								if (!StringUtil.isEmpty(productId) && pid.equals(productId)) {
									String packageId = specs.get("package_code") == null ? null : specs.get("package_code") + "";
									String elementId = specs.get("element_code") == null ? null : specs.get("element_code") + "";
									String elementType = specs.get("elementType") == null ? "D" : specs.get("elementType") + "";
									if (!StringUtil.isEmpty(packageId) || !StringUtil.isEmpty(elementId)) {
										OpenDealApplyPackageElementVo element = new OpenDealApplyPackageElementVo();
										element.setElementId(elementId);
										element.setPackageId(packageId);
										element.setElementType(elementType);
										packageElement.add(element);
									}
								}
							}
						}
						if(packageElement.size()>0){
							subVo.setPackageElement(packageElement);
						}else{
							subVo.setPackageElement(null);
						}
						ls.add(subVo);
					}
				}
			}
		}
		
		return ls;
	}

	private List<OpenDealApplyProductVo> getProductVo2(String order_id) {
		List<OpenDealApplyProductVo> ls = new ArrayList<OpenDealApplyProductVo>();
		OpenDealApplyProductVo vo = new OpenDealApplyProductVo();
		OrderTreeBusiRequest tree = new OrderTreeBusiRequest();
		tree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String goods_id = tree.getOrderItemBusiRequests().get(0).getGoods_id();
		IDaoSupport daoSupport = (IDaoSupport) SpringContextHolder.getBean("daoSupport");
		String sql = "select t.p_code,t.sn from es_goods_package t where t.goods_id='" + goods_id
				+ "' and t.source_from='" + ManagerUtils.getSourceFrom() + "'";
		List list = daoSupport.queryForListByMap(sql, null);
		Map map = (Map) list.get(0);
		vo.setProductId(com.ztesoft.ibss.common.util.Const.getStrValue(map, "sn") + ""); // Y
																							// 产品标识
		vo.setProductMode("1"); // Y 产品模式 1：主产品 2：附加产品 （）
		// vo.setPackageElement(getPackageElement(vo.getProductId())); // *
		// 产品下附加包及包元素（资费，服务） OpenDealApplyPackageElementVo
		// vo.setProductCode(null); // N 套包编码，北六无线上网卡必传
		if (DataUtil.checkFieldValueNull(vo))
			return null;
		ls.add(vo);

		// 附加产品处理
		OpenDealApplyProductVo subVo = new OpenDealApplyProductVo();
		sql = "select z_goods_id from es_goods_rel where a_goods_id='" + goods_id + "'";
		list = daoSupport.queryForListByMap(sql, null);
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(0);
			String prodid = CommonDataFactory.getInstance().getGoodSpec(null, map.get("z_goods_id").toString(),
					"bss_code");
			if (StringUtils.isEmpty(prodid) || "-1".equals(prodid)) {
				continue;
			}
			subVo.setProductId(
					CommonDataFactory.getInstance().getGoodSpec(null, map.get("z_goods_id").toString(), "bss_code"));
			subVo.setProductMode("2");
			ls.add(subVo);
		}
		
		IGoodsManager goodsManager = SpringContextHolder.getBean("goodsManager");
		//所有货品
		List<Goods> products = goodsManager.listGoodsRelProducts(
				CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "goods_id"));
		// 附加产品
		List<Goods> productList = new ArrayList<Goods>();
		for (Goods p : products) {
			if ("181251045132000162".equals(p.getCat_id())) {// 总部-附加产品 货品类型
				productList.add(p);
			}
		}
		if (productList.size() > 0) {
			Set<String> productIdStrs = new HashSet<String>();// 多个附加产品的编码
			for (Goods g : productList) {
				Map specs = SpecUtils.getProductSpecMap(g);
				if (specs != null && !specs.isEmpty()) {
					String productId = specs.get("cbss_product_code") == null ? "" : specs.get("cbss_product_code") + "";
					if (!StringUtil.isEmpty(productId)) {
						productIdStrs.add(productId);
					}
				}
			}
			if (!productIdStrs.isEmpty()) {
				for (String pid : productIdStrs) {// 不同的产品编码可能会有多个包元素编码
					if (!StringUtil.isEmpty(pid)) {
						OpenDealApplyProductVo subVo1 = new OpenDealApplyProductVo();
						subVo1.setProductId(pid);
						subVo1.setProductMode("2");
						subVo1.setProductCode(null);
						
						List<OpenDealApplyPackageElementVo> packageElement = new ArrayList<OpenDealApplyPackageElementVo>();
						for (Goods g : productList) {
							Map specs = SpecUtils.getProductSpecMap(g);
							if (specs != null && !specs.isEmpty()) {
								String productId = specs.get("cbss_product_code") == null ? "" : specs.get("cbss_product_code") + "";
								if (!StringUtil.isEmpty(productId) && pid.equals(productId)) {
									String packageId = specs.get("package_code") == null ? null : specs.get("package_code") + "";
									String elementId = specs.get("element_code") == null ? null : specs.get("element_code") + "";
									String elementType = specs.get("elementType") == null ? "D" : specs.get("elementType") + "";
									if (!StringUtil.isEmpty(packageId) || !StringUtil.isEmpty(elementId)) {
										OpenDealApplyPackageElementVo element = new OpenDealApplyPackageElementVo();
										element.setElementId(elementId);
										element.setPackageId(packageId);
										element.setElementType(elementType);
										packageElement.add(element);
									}
								}
							}
						}
						if(packageElement.size()>0){
							subVo1.setPackageElement(packageElement);
						}else{
							subVo1.setPackageElement(null);
						}
						ls.add(subVo1);
					}
				}
			}
		}
		return ls;
	}

	/**
	 * 获取支付信息
	 * 
	 * @param order_id
	 * @return
	 */
	private OpenDealApplyPayInfoVo getPayInfoVo(String order_id) {
		OpenDealApplyPayInfoVo vo = new OpenDealApplyPayInfoVo();
		vo.setPayType(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BILL_TYPE)); // 付费方式
		vo.setPayOrg(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PAY_PROVIDER_NAME)); // 支付机构名称
		vo.setPayNum(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BANK_ACCOUNT)); // 支付账号
		if (DataUtil.checkFieldValueNull(vo))
			return null;
		return vo;
	}

	/**
	 * 获取活动信息
	 * 
	 * @param order_id
	 * @return
	 */
	private List<OpenDealApplyActivityInfoVo> getActivityInfo(String order_id) {
		List<OpenDealApplyActivityInfoVo> ls = new ArrayList<OpenDealApplyActivityInfoVo>();
		OpenDealApplyActivityInfoVo vo = new OpenDealApplyActivityInfoVo();
		vo.setActPlanId(getOutPackageId(order_id)); // Y 活动ID（合约编码）

		String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.TYPE_ID);// 商品大类
		vo.setResourcesType(CommonDataFactory.getInstance().getOtherDictVodeValue("aop_resources_type", type_id));// N
																													// 资源类型
																													// 03：移动电话
																													// 04：上网卡
																													// 05：上网本
		vo.setResProcId(null);// N 预占流水，终端预占接口没有这个东西，因此我们也取不到

		// N 终端资源编码，一般是IMEI码
		// B环节的时候，终端串码还未录入，因此，传虚拟串码
		String flowNode = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest()
				.getFlow_trace_id();
		if (StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)) {// B环节还没有串码,传虚拟的
			vo.setResourcesCode(CommonDataFactory.getInstance().getTerminalNum(notNeedReqStrOrderId));
			vo.setIsTest("0"); // N 是否测试终端 0：测试 1：正式
		} else {
			vo.setResourcesCode(
					CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.TERMINAL_NUM));
			vo.setIsTest("1"); // N 是否测试终端 0：测试 1：正式
		}

		// 合约机的商品属性中可以取到购机费用
		if (StringUtils.equals(type_id, EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE)) {
			String rFee = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.MOBILE_PRICE);
			if (!StringUtils.isEmpty(rFee)) {
				rFee = Double.parseDouble(rFee) * 1000 + "";
				vo.setResourcesFee(rFee.substring(0, rFee.indexOf(".")));// N
																			// 资源费用
			}
		} else {
			vo.setResourcesFee(null);
			vo.setActivityFee(null);
		}

		vo.setPackageElement(null); // 活动下自选包
		vo.setActPara(getActParaVo(order_id)); // * 活动扩展字段

		// 没有活动id的，整个就不传了
		if (StringUtils.isEmpty(vo.getActPlanId()))
			return null;
		ls.add(vo);
		return ls;
	}

	private List<OpenDealApplyActivityInfoVo> getActivityInfo2(String order_id) {
		List<OpenDealApplyActivityInfoVo> ls = new ArrayList<OpenDealApplyActivityInfoVo>();

		OrderTreeBusiRequest tree = new OrderTreeBusiRequest();
		tree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String goods_id = tree.getOrderItemBusiRequests().get(0).getGoods_id();
		IDaoSupport daoSupport = (IDaoSupport) SpringContextHolder.getBean("daoSupport");
		String sql = "select goods_id from es_pmt_goods where pmt_id in (select (case pmts_id when 'gift' then pmt_id else '' end) from es_promotion where pmta_id in (select pmta_id  from es_promotion where pmt_id in (select pmt_id from es_pmt_goods where goods_id = '"
				+ goods_id + "')))";
		List list = daoSupport.queryForListByMap(sql, null);
		for (int i = 0; i < list.size(); i++) {
			OpenDealApplyActivityInfoVo vo = new OpenDealApplyActivityInfoVo();
			Map map = (Map) list.get(i);
			vo.setActPlanId(CommonDataFactory.getInstance().getGoodSpec(null, map.get("goods_id").toString(),
					"cbss_product_code")); // Y 活动ID（合约编码）

			String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.TYPE_ID);// 商品大类
			vo.setResourcesType(CommonDataFactory.getInstance().getOtherDictVodeValue("aop_resources_type", type_id));// N
																														// 资源类型
																														// 03：移动电话
																														// 04：上网卡
																														// 05：上网本
			vo.setResProcId(null);// N 预占流水，终端预占接口没有这个东西，因此我们也取不到

			// N 终端资源编码，一般是IMEI码
			// B环节的时候，终端串码还未录入，因此，传虚拟串码
			String flowNode = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId)
					.getOrderExtBusiRequest().getFlow_trace_id();
			if (StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)) {// B环节还没有串码,传虚拟的
				vo.setResourcesCode(CommonDataFactory.getInstance().getTerminalNum(notNeedReqStrOrderId));
				vo.setIsTest("0"); // N 是否测试终端 0：测试 1：正式
			} else {
				vo.setResourcesCode(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.TERMINAL_NUM));
				vo.setIsTest("1"); // N 是否测试终端 0：测试 1：正式
			}

			// 合约机的商品属性中可以取到购机费用
			if (StringUtils.equals(type_id, EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE)) {
				String rFee = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.MOBILE_PRICE);
				if (!StringUtils.isEmpty(rFee)) {
					rFee = Double.parseDouble(rFee) * 1000 + "";
					vo.setResourcesFee(rFee.substring(0, rFee.indexOf(".")));// N
																				// 资源费用
				}
			} else {
				vo.setResourcesFee(null);
				vo.setActivityFee(null);
			}
			List<OpenDealApplyPackageElementVo> packageElementls = new ArrayList<OpenDealApplyPackageElementVo>();
			OpenDealApplyPackageElementVo packageElement = new OpenDealApplyPackageElementVo();
			packageElement.setElementId(
					CommonDataFactory.getInstance().getGoodSpec(null, map.get("goods_id").toString(), "element_code"));
			packageElement.setElementType("D");
			packageElement.setPackageId(
					CommonDataFactory.getInstance().getGoodSpec(null, map.get("goods_id").toString(), "package_code"));
			packageElementls.add(packageElement);
			vo.setPackageElement(packageElementls); // 活动下自选包
			vo.setActPara(getActParaVo(order_id)); // * 活动扩展字段

			// 没有活动id的，整个就不传了
			if (StringUtils.isEmpty(vo.getActPlanId()))
				return null;
			ls.add(vo);
		}

		return ls;
	}

	/**
	 * 获取可选包
	 * 
	 * @param order_id
	 * @return
	 */
	private List<OpenDealApplyPackageElementVo> getPackageElement(String product_id) {
		List<OpenDealApplyPackageElementVo> ls = new ArrayList<OpenDealApplyPackageElementVo>();
		List<AttrPackageBusiRequest> list = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId)
				.getPackageBusiRequests();
		// 没可选包就不用处理了
		if (list == null || list.size() == 0)
			return null;

		// 获取可选包的依赖包等信息
		List<Map> map = AttrUtils.getInstance().doGetDependElementList(list, product_id,
				CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.FIRST_PAYMENT));
		if (map != null) {
			for (Map m : map) {
				OpenDealApplyPackageElementVo vo = new OpenDealApplyPackageElementVo();
				if (m.size() > 0) {
					vo.setPackageId(m.get("package_id").toString());
					vo.setElementId(m.get("element_id").toString());
					vo.setElementType(m.get("element_type_code").toString());
					ls.add(vo);
				}
			}
			return ls;
		} else {
			return null;
		}
	}

	/**
	 * 获取活动扩展信息
	 * 
	 * @param order_id
	 * @return
	 */
	private List<OpenDealApplyActParaVo> getActParaVo(String order_id) {
		List<OpenDealApplyActParaVo> ls = new ArrayList<OpenDealApplyActParaVo>();
		OpenDealApplyActParaVo vo = new OpenDealApplyActParaVo();
		vo.setActParaId(null); // Y 活动扩展字段ID
		vo.setActParaValue(null); // Y 活动扩展字段值
		if (DataUtil.checkFieldValueNull(vo))
			return null;
		ls.add(vo);
		return ls;
	}

	/**
	 * 获取终端资源信息
	 * 
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	private List<OpenDealApplyResourcesInfoVo> getResourcesInfoVo(String order_id) throws Exception {
		List<OpenDealApplyResourcesInfoVo> ls = new ArrayList<OpenDealApplyResourcesInfoVo>();
		OpenDealApplyResourcesInfoVo vo = new OpenDealApplyResourcesInfoVo();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<AttrTmResourceInfoBusiRequest> resourcelist = orderTree.getTmResourceInfoBusiRequests();
		if (resourcelist.size() > 0) {
			AttrTmResourceInfoBusiRequest resourceInfo = resourcelist.get(0);
			vo.setSalePrice(resourceInfo.getSale_price()); // N 销售价格（单位：厘）
			vo.setCost(resourceInfo.getC_cost()); // N 成本价格（单位：厘）
			vo.setCardPrice(resourceInfo.getCard_price()); // N 卡费（单位：厘）
			vo.setReservaPrice(resourceInfo.getReserva_price()); // N
																	// 预存话费金额（单位：厘）
			// o.setProductActivityInfo(getActivityInfoVo(resourceInfo.getProductActivityInfo()));
			// // 套包对应产品活动信息（待定 2015-05-21 朱红玉说待定）
			vo.setResourcesBrandCode(resourceInfo.getResources_brand_code()); // N
																				// 品牌
			vo.setOrgDeviceBrandCode(resourceInfo.getOrg_device_brand_code()); // N
																				// 3GESS维护品牌，当iphone时品牌与上面的一致
			vo.setResourcesBrandName(resourceInfo.getResources_brand_name()); // N
																				// 终端品牌名称
			vo.setResourcesModelCode(resourceInfo.getResources_model_code()); // N
																				// 型号
			vo.setResourcesModelName(resourceInfo.getResources_model_name()); // N
																				// 终端型号名称
			vo.setResourcesSrcCode(resourceInfo.getResources_src_code()); // N
																			// 终端来源编码
			vo.setResourcesSrcName(resourceInfo.getResources_src_name()); // N
																			// 终端来源名称
			vo.setResourcesSupplyCorp(resourceInfo.getResources_supply_corp()); // N
																				// 终端供货商名称
			vo.setResourcesServiceCorp(resourceInfo.getResources_service_corp()); // N
																					// 终端服务商名称
			vo.setResourcesColor(resourceInfo.getResources_color()); // N 终端颜色
			vo.setMachineTypeCode(resourceInfo.getMachine_type_code()); // N
																		// 终端机型编码
			vo.setMachineTypeName(resourceInfo.getMachine_type_name()); // N
																		// 终端机型名称
			vo.setDistributionTag(resourceInfo.getDistribution_tag()); // N
																		// "铺货标志
																		// 0非铺货终端
																		// 1铺货终端"
			vo.setResRele(resourceInfo.getRes_rele()); // N 资源归属 01 总部管理资源 02
														// 省分管理资源 03：全部资源 04
														// 社会渠道资源
			vo.setTerminalType(resourceInfo.getTerminal_type()); // N
																	// 终端类别编码：Iphone：IP
																	// 乐phone：LP
																	// 智能终端：PP普通定制终端：01
																	// 上网卡：04
																	// 上网本：05
			vo.setTerminalTSubType(resourceInfo.getTerminal_t_sub_type()); // N
																			// 当终端类别为智能终端时，该字段必填，终端子类别编码：入门级：04
																			// 普及型：03
																			// 中高端：02
																			// 明星：01
			vo.setServiceNumber(resourceInfo.getService_number()); // N
																	// 终端绑定的服务号码，当终端为打包的预付费产品时必传，如上网卡套包等
			if (DataUtil.checkFieldValueNull(vo))
				return null;
			ls.add(vo);
		}
		return ls;
	}

	/**
	 * 获取是否集团标志
	 * 
	 * @param order_id
	 * @return
	 */
	private String getGroupFlag(String order_id) {
		String custType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CUSTOMER_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_is_group", custType);
	}

	/**
	 * 获取受理类型
	 * 
	 * @param order_id
	 * @return
	 */
	private String getSerType(String order_id) {
		// 受理类型
		String serType = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null,
				SpecConsts.PAY_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_pay_type_open_app", serType);
	}

	/**
	 * 获取首月资费
	 * 
	 * @param order_id
	 * @return
	 */
	private String getFirstBillMode(String order_id) {
		String first_payment = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.FIRST_PAYMENT);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_first_payment", first_payment);
	}

	/**
	 * 获取活动信息
	 * 
	 * @param order_id
	 * @return
	 * @throws ClassNotFoundException
	 */
	private List<ProductActivityInfoVo> getActivityInfoVo(String activityinfoStr) throws Exception {
		return null;// DataUtil.getActivityInfoVo(activityinfoStr);
	}

	/**
	 * 获取预留
	 * 
	 * @param order_id
	 * @return
	 */
	private List<ParamsVo> getParaVo(String order_id) {
		List<ParamsVo> ls = new ArrayList<ParamsVo>();
		ParamsVo vo = new ParamsVo();
		vo.setParaId(null);
		vo.setParaValue(null);
		if (DataUtil.checkFieldValueNull(vo))
			return null;
		ls.add(vo);
		return ls;
	}

	/**
	 * 获取客户类型
	 * 
	 * @param order_id
	 * @return
	 */
	private String getCustType(String order_id) {
		String custType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CUSTOMER_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_user_type", custType);
	}

	/**
	 * 获取套包销售标记
	 * 
	 * @param order_id
	 * @return
	 */
	private String getPackageTag(String order_id) {
		String packageSale = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PACKAGE_SALE);
		String packageTag = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_package_sale", packageSale);
		if (StringUtils.isBlank(packageTag)) {
			packageTag = "0";
		}
		return packageTag;
	}

	/**
	 * 获取业务类型
	 * 
	 * @param order_id
	 * @return
	 */
	private String getBipType_id(String order_id) {
		String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.TYPE_ID);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_bip_type", type_id);
	}

	/**
	 * 获取网别
	 * 
	 * @param order_id
	 * @return
	 */
	private String getNet_type(String order_id) {
		String net_type = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null,
				SpecConsts.NET_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_mobile_net", net_type);
	}

	/**
	 * 获取行业应用
	 * 
	 * @param order_id
	 * @return
	 */
	private String getAppType(String groupFlag) {
		String appType = "";
		if (StringUtils.equals(groupFlag, EcsOrderConsts.NO_DEFAULT_VALUE)) { // 非集团客户
			appType = "1"; // 1：非行业应用
		} else if (StringUtils.equals(groupFlag, EcsOrderConsts.IS_DEFAULT_VALUE)) { // 集团客户
			appType = "0"; // 0：行业应用
		}
		return appType;
	}

	/**
	 * 获取证件类型
	 * 
	 * @param order_id
	 * @return
	 */
	private String getCertType(String order_id) {
		String cert_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_cert_type", cert_type);
	}

	private String getCheckType(String order_id) {// 取不到值默认2代
		String checkType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_EXAMINE_CARD);
		if (StringUtils.isEmpty(checkType))
			checkType = "0";
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_check_type", checkType);
	}

	/**
	 * 获取活动编码，即合约编码
	 * 
	 * @param order_id
	 * @return
	 */
	private String getOutPackageId(String order_id) {
		// 有 合约期限 的才取 活动编码
		String limit = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001,
				SpecConsts.PACKAGE_LIMIT);
		if (StringUtils.isNotBlank(limit)) {
			return CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_PACKAGE_ID);
		} else {
			return null;
		}
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
		return "ecaop.trades.sell.mob.newu.open.appzj";
	}

	public EmpOperInfoVo getEssOperInfo() {
		if (essOperInfo == null) {
			essOperInfo = CommonDataFactory.getEssInfoByOrderId(notNeedReqStrOrderId).getOperInfo();
		}
		return essOperInfo;
	}

	public void setEssOperInfo(EmpOperInfoVo essOperInfo) {
		this.essOperInfo = essOperInfo;
	}

	/**
	 * 获取附加产品可选包
	 * 
	 * @param product_id
	 * @param sub_inst_id
	 * @return
	 */
	private List<OpenDealApplyPackageElementVo> getSubPackageElement(String product_id, String sub_inst_id) {
		List<OpenDealApplyPackageElementVo> ls = new ArrayList<OpenDealApplyPackageElementVo>();
		List<AttrPackageBusiRequest> list = new ArrayList<AttrPackageBusiRequest>();
		List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getAttrPackageSubProdBusiRequest();

		// 没可选包就不用处理了
		if (attrPackageSubProdList == null || attrPackageSubProdList.size() == 0)
			return null;

		// 附加产品可选包转换为AttrPackageBusiRequest类对象
		for (AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList) {
			if (sub_inst_id.equals(subPackage.getSubProd_inst_id())) {
				AttrPackageBusiRequest attrPackage = new AttrPackageBusiRequest();
				attrPackage.setElementCode(subPackage.getElement_code());
				attrPackage.setPackageCode(subPackage.getPackage_code());
				attrPackage.setElementType(EcsOrderConsts.ELEMENT_TYPE_D);
				attrPackage.setProductCode(subPackage.getProduct_code());
				attrPackage.setPackageName(subPackage.getPackage_name());
				attrPackage.setElementName(subPackage.getElement_name());
				list.add(attrPackage);
			}
		}

		// 获取可选包的依赖包等信息
		List<Map> map = AttrUtils.getInstance().doGetDependElementList(list, product_id,
				CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.FIRST_PAYMENT));
		if (map != null) {
			for (Map m : map) {
				OpenDealApplyPackageElementVo vo = new OpenDealApplyPackageElementVo();
				if (m.size() > 0) {
					vo.setPackageId(m.get("package_id").toString());
					vo.setElementId(m.get("element_id").toString());
					vo.setElementType(m.get("element_type_code").toString());
					ls.add(vo);
				}
			}
			return ls;
		} else {
			return null;
		}
	}

	public String getDelayTag() {
		if (StringUtil.isEmpty(delayTag)) {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
			String order_from = orderTree.getOrderExtBusiRequest().getOrder_from();
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");
			
			if(orderTree!=null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
					&& "1".equals(flag)){
				String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(this.notNeedReqStrOrderId,
						"order_deal_method");
				
				//自定义流程
				if("2".equals(deal_method)){
					//线下方式受理业务，现场激活
					delayTag = EcsOrderConsts.NO_DEFAULT_VALUE;
				}else{
					//线上方式受理，后激活
					delayTag = EcsOrderConsts.IS_DEFAULT_VALUE;
				}
			}else{
				String DELAY_TAG_ORDER_FROM = cacheUtil.getConfigInfo("DELAY_TAG_ORDER_FROM");
				if (DELAY_TAG_ORDER_FROM.contains(order_from)) {
					delayTag = "1";
				} else {
					// 0:不压单 1:压单 后向激活时压单
					String later_active_flag = EcsOrderConsts.NO_DEFAULT_VALUE;
					OrderRealNameInfoBusiRequest realNameBus = orderTree.getOrderRealNameInfoBusiRequest();
					if (null != realNameBus) {
						later_active_flag = realNameBus.getLater_active_flag();
					}
					String goods_cat_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
							AttrConsts.GOODS_CAT_ID);
					if (StringUtils.equals(later_active_flag, EcsOrderConsts.IS_DEFAULT_VALUE)) {
						delayTag = EcsOrderConsts.IS_DEFAULT_VALUE;
					} else if (!StringUtils.isEmpty(goods_cat_id) && (EcsOrderConsts.GOODS_CAT_ID_DDWK.equals(goods_cat_id)
							|| EcsOrderConsts.GOODS_CAT_ID_TXWK.equals(goods_cat_id))) {
						delayTag = EcsOrderConsts.IS_DEFAULT_VALUE;
					} else {
						delayTag = EcsOrderConsts.NO_DEFAULT_VALUE;
					}
				}
			}
		}
		return delayTag;
	}

	public void setDelayTag(String delayTag) {
		this.delayTag = delayTag;
	}

	public String getIsAfterActivation() {
		if ("1".equals(getDelayTag())) {
			isAfterActivation = "1";
		}
		return isAfterActivation;
	}

	public void setIsAfterActivation(String isAfterActivation) {
		this.isAfterActivation = isAfterActivation;
	}

	public String getChkBlcTag() {
		return "1";
	}

	public void setChkBlcTag(String chkBlcTag) {
		this.chkBlcTag = chkBlcTag;
	}
}
