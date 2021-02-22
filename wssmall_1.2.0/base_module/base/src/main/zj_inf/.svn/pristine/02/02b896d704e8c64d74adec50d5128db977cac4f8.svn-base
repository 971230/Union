package zte.net.ecsord.params.zb.req;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.zb.vo.ResourcesInfo;

public class NumberStateChangeZBRequest extends ZteRequest{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	protected String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "N", desc = "operatorId：操作员ID")
	protected String operatorId;
	
	@ZteSoftCommentAnnotationParam(name = "省份", type = "String", isNecessary = "Y", desc = "province：省份")
	protected String province;
	
	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "N", desc = "city：地市")
	protected String city;
	
	@ZteSoftCommentAnnotationParam(name = "区县", type = "String", isNecessary = "N", desc = "district：区县")
	protected String district;
	
	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "channelId：渠道编码")
	protected String channelId;
	
	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "channelType：渠道类型")
	protected String channelType;
	
	@ZteSoftCommentAnnotationParam(name = "资源信息", type = "List", isNecessary = "Y", desc = "resourcesInfo：资源信息")
	protected List<ResourcesInfo> resourcesInfo;

	protected String operFlag;
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	protected EmpOperInfoVo essOperInfo;

	public String getOperatorId() {
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
				this.operatorId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.OUT_OPERATOR);

				if (StringUtils.isEmpty(this.operatorId)) {
					this.operatorId = getEssOperInfo().getEss_emp_id();
				}
			} else {
				// 线上方式受理取配置的操作点
				this.operatorId = getEssOperInfo().getEss_emp_id();
			}
		} else {

			if (cacheUtil.getConfigInfo("FUKA_ORDER_FROM").contains(
					CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM))) {
				this.operatorId = getEssOperInfo().getEss_emp_id();
			} else {
				this.operatorId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.OUT_OPERATOR);
				if (StringUtils.isEmpty(this.operatorId)) {
					this.operatorId = getEssOperInfo().getEss_emp_id();
				}
			}
		}
		return this.operatorId;
	}

	public String getOperFlag() {
		return operFlag;
	}

	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getProvince() {
			this.province = getEssOperInfo().getProvince();
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
			city =  getEssOperInfo().getCity();
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
			district =  getEssOperInfo().getDistrict();
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getChannelId() {
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
				this.channelId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.ORDER_CHA_CODE);
				if (StringUtils.isEmpty(this.channelId)) {
					this.channelId = getEssOperInfo().getChannel_id();
				}
			} else {

				// 线上方式受理取配置的操作点
				this.channelId = getEssOperInfo().getChannel_id();
			}
		} else {

			if (cacheUtil.getConfigInfo("FUKA_ORDER_FROM").contains(
					CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM))) {
				this.channelId = getEssOperInfo().getChannel_id();
			} else {
				this.channelId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.ORDER_CHA_CODE);
				if (StringUtils.isEmpty(this.channelId)) {
					this.channelId = getEssOperInfo().getChannel_id();
				}
			}
		}
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelType() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(this.notNeedReqStrOrderId);
		String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");
		
		if(orderTree!=null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)){
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(this.notNeedReqStrOrderId,
					"order_deal_method");
			
			//自定义流程
			if("2".equals(deal_method)){
				//线下方式受理业务，取传入的操作点
				this.channelType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.CHANNEL_TYPE);
				if (StringUtil.isEmpty(this.channelType)) {
					this.channelType = getEssOperInfo().getChannel_type();
				}
			}else{
				//线上方式受理取配置的操作点
				this.channelType = getEssOperInfo().getChannel_type();
			}
		}else{
		
		if (cacheUtil.getConfigInfo("FUKA_ORDER_FROM").contains(
				CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM))) {
			this.channelType = getEssOperInfo().getChannel_type();
		} else {
			this.channelType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
					AttrConsts.CHANNEL_TYPE);
			if (StringUtil.isEmpty(this.channelType)) {
				this.channelType = getEssOperInfo().getChannel_type();
			}
		}
		}
		return this.channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public List<ResourcesInfo> getResourcesInfo() {
		if(this.resourcesInfo==null){
			return null;
		}
		for(int i=0;i<this.resourcesInfo.size();i++){
			ResourcesInfo resourcesInfo=this.resourcesInfo.get(i);
			resourcesInfo.setKeyChangeTag(resourcesInfo.getSnChangeTag());
			resourcesInfo.setProKeyMode(EcsOrderConsts.AOP_PROKEY_TYPE_0);
			String pay_type=CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.PAY_TYPE);
			pay_type=CommonDataFactory.getInstance().getOtherDictVodeValue("aop_num_pay_type", pay_type);
			String pay_status=CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_STATUS);
			resourcesInfo.setResourcesType(pay_type);
			if(EcsOrderConsts.OCCUPIEDFLAG_1.equals(resourcesInfo.getOccupiedFlag())){
				//预占半小时
				resourcesInfo.setOccupiedTime(getTime(1));
			}else if(EcsOrderConsts.OCCUPIEDFLAG_2.equals(resourcesInfo.getOccupiedFlag())){
				OrderPhoneInfoBusiRequest phoneInfoBusiRequest = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getPhoneInfoBusiRequest();
				resourcesInfo.setOccupiedTime(phoneInfoBusiRequest.getOccupiedTime());
				if(EcsOrderConsts.AOP_PAY_STUTAS_1.equals(pay_status)){
					resourcesInfo.setOccupiedFlag(EcsOrderConsts.OCCUPIEDFLAG_3);
				}
			}
			if(StringUtils.isEmpty(resourcesInfo.getResourcesCode())){
				String phone_num=CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
				resourcesInfo.setResourcesCode(phone_num);
			}
			if(EcsOrderConsts.OCCUPIEDFLAG_2.equals(resourcesInfo.getOccupiedFlag())||EcsOrderConsts.OCCUPIEDFLAG_3.equals(resourcesInfo.getOccupiedFlag())){
				resourcesInfo.setCustName(CommonDataFactory.getInstance()
						.getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NAME));
				String certType=CommonDataFactory.getInstance()
						.getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERTI_TYPE);
				 if(!StringUtils.isEmpty(certType)){
					 resourcesInfo.setCertType(CommonDataFactory.getInstance().getOtherDictVodeValue("aop_cert_type", certType));
			       }
				resourcesInfo.setCertNum(CommonDataFactory.getInstance()
						.getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NUM));
			}
			resourcesInfo.setContactNum(CommonDataFactory.getInstance()
				.getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.REVEIVER_MOBILE));
			resourcesInfo.setDevelopPersonTag(EcsOrderConsts.AOP_DEV_PERSON_TAG_1);
		}
		return this.resourcesInfo;
	}
	
	public String getTime(int time){
		DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
 	    Date date = new Date();
   	    Calendar c = Calendar.getInstance();
   	    c.setTime(date);
   	    c.add(Calendar.MINUTE, EcsOrderConsts.AOP_OCCUPIED_TIME*time);
	    String str = sdf.format(c.getTime());
	    return str;
	}

	public void setResourcesInfo(List<ResourcesInfo> resourcesInfo) {
		this.resourcesInfo = resourcesInfo;
	}
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zb.numberStateChange";
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
