package zte.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.CustInfoCreateVo;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.ParamsVo;
import zte.net.ecsord.utils.DataUtil;

public class CustInfoCreateReq extends ZteRequest{
    @ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
    private String notNeedReqStrOrderId;
    @ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "operatorId：操作员ID")
    private String operatorId;
    @ZteSoftCommentAnnotationParam(name = "省份", type = "String", isNecessary = "Y", desc = "province：省份")
    private String province;
    @ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "Y", desc = "city：地市")
    private String city;
    @ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "channelId：渠道编码")
    private String channelId;
    @ZteSoftCommentAnnotationParam(name = "区县", type = "String", isNecessary = "N", desc = "district：区县")
    private String district;
    @ZteSoftCommentAnnotationParam(name = "办理业务系统", type = "String", isNecessary = "N", desc = "opeSysType：办理业务系统 1：ESS 2：CBSS")
    private String opeSysType;
    @ZteSoftCommentAnnotationParam(name = "客户信息", type = "String", isNecessary = "Y", desc = "客户信息")
    private List<CustInfoCreateVo> custInfo;
    @ZteSoftCommentAnnotationParam(name = "保留字段", type = "ParamsVo", isNecessary = "N", desc = "para：保留字段")
    private List<ParamsVo> para;
    @ZteSoftCommentAnnotationParam(name = "渠道标示", type = "String", isNecessary = "N", desc = "渠道标示")
    private String eModeCode;
    @ZteSoftCommentAnnotationParam(name = "是否校验黑名单", type = "String", isNecessary = "N", desc = "chkBlcTag：是否校验黑名单0：校验1：不校验该字段没传的时候默认0：校验")
    private String chkBlcTag;
    
    public String getNotNeedReqStrOrderId() {
        return notNeedReqStrOrderId;
    }

    public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
        this.notNeedReqStrOrderId = notNeedReqStrOrderId;
    }

    private EmpOperInfoVo essOperInfo;
    
	public String getOperatorId() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(this.notNeedReqStrOrderId);
		String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");

		if (orderTree != null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)) {
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
					"order_deal_method");

			// 自定义流程
			if ("2".equals(deal_method)) {
				// 线下方式受理业务，先取传入的操作员
				this.operatorId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.OUT_OPERATOR);

				if (StringUtil.isEmpty(this.operatorId)) {
					// 操作员没传的取配置的操作员
					this.operatorId = getEssOperInfo().getEss_emp_id();
				}
			} else {
				// 线上方式直接取配置的操作员
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

    public String getDistrict() {
        return getEssOperInfo().getDistrict();
    }

    public void setDistrict(String district) {
        this.district = district;
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

    public List<CustInfoCreateVo> getCustInfo() {
        List<CustInfoCreateVo> ls = new ArrayList<CustInfoCreateVo>();
        CustInfoCreateVo custInfoCreateVo = new CustInfoCreateVo();
        String customerName =  CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_OWNER_NAME);//客户名称 1 
        String custType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CUSTOMER_TYPE);
        if(StringUtil.isEmpty(custType)){
            custType="GRKH";//若是该值为空  则默认是个人客户
        }
        String customerType =  CommonDataFactory.getInstance().getOtherDictVodeValue("aop_user_custType", custType);//0 个人  1 集团                                                                                                     // 联系人电话>6位
        String isAssure = "0";//鲍明确定
        String certExpireDate = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,AttrConsts.CERT_FAILURE_TIME);
        certExpireDate = certExpireDate.replace("-", "");
        certExpireDate = StringUtils.isEmpty(certExpireDate) ? "20591231" : certExpireDate;//证件有效日期
        //   custInfoCreateVo.setCertExpireDate(certExpireDate.substring(0, 8)); // Y 格式：yyyymmdd 1
        // 证件失效日期(考虑默认)
        String certAddress =  CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_address();
        String cert_type =  CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCerti_type();
        String certType = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_cert_type", cert_type);//证件类别 1 
        String certNum =  CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_card_num();//证件号码
        String sex =  CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCerti_sex();//性别

        String postAddress="";//通信地址
        String ship_addr = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderDeliveryBusiRequests().get(0).getShip_addr();//通信地址
        if(!StringUtil.isEmpty(ship_addr)){
            postAddress = ship_addr;
        }else {
            postAddress = certAddress;
        }
        String phone = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);//电话 1
        String contactPerson = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderDeliveryBusiRequests().get(0).getShip_name();//联系人 1
        String contactPhone = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderDeliveryBusiRequests().get(0).getShip_tel();//联系电话 1

        String checkType=CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "checkType");;//认证类型： 01：本地认证 02：公安认证  03：二代证读卡器
        if(StringUtil.isEmpty(checkType)){//
            checkType = "03";
        }
        custInfoCreateVo.setSex(sex);
        custInfoCreateVo.setCertAddress(certAddress.replace(" ", ""));
        custInfoCreateVo.setCertExpireDate(certExpireDate.substring(0, 8));
        custInfoCreateVo.setCertNum(certNum);
        custInfoCreateVo.setCertType(certType);
        custInfoCreateVo.setContactPerson(contactPerson);
        custInfoCreateVo.setContactPhone(contactPhone);
        custInfoCreateVo.setCustomerName(customerName);
        custInfoCreateVo.setCustomerType(customerType);
        custInfoCreateVo.setIsAssure(isAssure);
        custInfoCreateVo.setPhone(phone);
        custInfoCreateVo.setCheckType(checkType);
        custInfoCreateVo.setPostAddress(postAddress.replace(" ", ""));
        if (DataUtil.checkFieldValueNull(custInfoCreateVo))
            return null;
        ls.add(custInfoCreateVo);
        return ls;
    }

    public void setCustInfo(List<CustInfoCreateVo> custInfo) {
        this.custInfo = custInfo;
    }

    public List<ParamsVo> getPara() {
        List<ParamsVo> plist = new ArrayList<ParamsVo>();
        String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002,
                null, SpecConsts.NET_TYPE);
        if (StringUtils.equals(EcsOrderConsts.NET_TYPE_4G, net_type)) {
            ParamsVo vo = new ParamsVo();
            vo.setParaId(EcsOrderConsts.AOP_4G_PARA_SPEED);
            vo.setParaValue(EcsOrderConsts.AOP_4G_PARA_SPEED_VALUE);
            plist.add(vo);
        }

        if (plist.size() == 0)
            return null;
        return plist;
    }

    public void setPara(List<ParamsVo> para) {
        this.para = para;
    }

    public String geteModeCode() {
        return eModeCode;
    }

    public void seteModeCode(String eModeCode) {
        this.eModeCode = eModeCode;
    }

    public String getChkBlcTag() {
        return "1";
    }

    public void setChkBlcTag(String chkBlcTag) {
        this.chkBlcTag = chkBlcTag;
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

    @Override
    @NotDbField
    public void check() throws ApiRuleException {
    }

    @Override
    @NotDbField
    public String getApiMethodName() {
        return "ecaop.serv.curt.custinfo.create";
    }

}
