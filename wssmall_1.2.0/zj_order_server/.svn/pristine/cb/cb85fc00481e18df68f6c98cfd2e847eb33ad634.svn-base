package com.ztesoft.net.ecsord.params.ecaop.req;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.service.IOrderInfManager;
import com.ztesoft.net.util.ZjCommonUtils;

public class PreCommitBSSReq extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name = "联通服务号码", type = "String", isNecessary = "Y", desc = "开户预提交->service_num:联通服务号码，非空【20】")
	private String service_num = "";
	@ZteSoftCommentAnnotationParam(name = "业务类型", type = "String", isNecessary = "Y", desc = "开户预提交->service_type:业务类型，非空【4】")
	private String service_type = "";
	@ZteSoftCommentAnnotationParam(name = "SIM卡号", type = "String", isNecessary = "Y", desc = "开户预提交->sim_card:SIM卡号，非空【20】")
	private String sim_card = "";
	@ZteSoftCommentAnnotationParam(name = "主产品ID", type = "String", isNecessary = "Y", desc = "开户预提交->product_id:主产品ID，非空【8】")
	private String product_id = "";
	@ZteSoftCommentAnnotationParam(name = "活动ID,可空【8】", type = "String", isNecessary = "N", desc = "开户预提交->pcheme_id:活动ID,可空【8】")
	private String pcheme_id = "";
	@ZteSoftCommentAnnotationParam(name = "客户姓名", type = "String", isNecessary = "Y", desc = "开户预提交->customer_name:客户姓名，非空【60】")
	private String customer_name = "";
	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "Y", desc = "开户预提交->cert_type:证件类型，非空 【2】")
	private String cert_type = "";
	@ZteSoftCommentAnnotationParam(name = "证件号码", type = "String", isNecessary = "Y", desc = "开户预提交->cert_num:证件号码，非空【50】")
	private String cert_num = "";
	@ZteSoftCommentAnnotationParam(name = "证件地址", type = "String", isNecessary = "Y", desc = "开户预提交->cert_addr:证件地址，非空【128】")
	private String cert_addr = "";
	@ZteSoftCommentAnnotationParam(name = "民族", type = "String", isNecessary = "N", desc = "开户预提交->cert_nation:民族，可空【16】")
	private String cert_nation = "";
	@ZteSoftCommentAnnotationParam(name = "性别", type = "String", isNecessary = "N", desc = "开户预提交->cert_sex:性别，可空【1】0男性，1女性")
	private String cert_sex = "";
	@ZteSoftCommentAnnotationParam(name = "签证机关【40】", type = "String", isNecessary = "Y", desc = "开户预提交->cert_issuedat:签证机关【40】")
	private String cert_issuedat = "";
	@ZteSoftCommentAnnotationParam(name = "证件失效时间", type = "String", isNecessary = "Y", desc = "开户预提交->cert_expire_date:证件失效时间，date【8】")
	private String cert_expire_date = "";
	@ZteSoftCommentAnnotationParam(name = "证件生效时间", type = "String", isNecessary = "Y", desc = "开户预提交->cert_effected_date:证件生效时间，date【8】")
	private String cert_effected_date = "";
	@ZteSoftCommentAnnotationParam(name = "照片", type = "String", isNecessary = "Y", desc = "开户预提交->cert_photo:照片，暂不支持")
	private String cert_photo = "";
	@ZteSoftCommentAnnotationParam(name = "联系人", type = "String", isNecessary = "Y", desc = "开户预提交->contact_name:联系人，非空【60】")
	private String contact_name = "";
	@ZteSoftCommentAnnotationParam(name = "联系电话", type = "String", isNecessary = "Y", desc = "开户预提交->contact_phone:联系电话，非空【20】")
	private String contact_phone = "";
	@ZteSoftCommentAnnotationParam(name = "通信地址", type = "String", isNecessary = "Y", desc = "开户预提交->customer_adder:通信地址，非空【128】")
	private String customer_adder = "";
	@ZteSoftCommentAnnotationParam(name = "客户标识", type = "String", isNecessary = "N", desc = "开户预提交->cert_verified:客户标识，Y实名-二代，可空【2】")
	private String cert_verified = "";
	@ZteSoftCommentAnnotationParam(name = "办理操作员", type = "String", isNecessary = "Y", desc = "开户预提交->deal_operator:办理操作员，非空【20】")
	private String deal_operator = "";
	@ZteSoftCommentAnnotationParam(name = "办理操作点", type = "String", isNecessary = "Y", desc = "开户预提交->deal_office_id:办理操作点，非空【30】")
	private String deal_office_id = "";
	@ZteSoftCommentAnnotationParam(name = "是否为白卡", type = "String", isNecessary = "N", desc = "开户预提交->param11:是否为白卡，1:是2否，可空【10】")
	private String param11 = "";
	@ZteSoftCommentAnnotationParam(name = "USE_DOMAIN", type = "String", isNecessary = "N", desc = "开户预提交->param12:USE_DOMAIN，可空【10】")
	private String param12 = "";
	@ZteSoftCommentAnnotationParam(name = "发展人部门", type = "String", isNecessary = "N", desc = "开户预提交->param13:发展人部门，可空【10】")
	private String param13 = "";
	@ZteSoftCommentAnnotationParam(name = "发展人编码", type = "String", isNecessary = "N", desc = "开户预提交->param14:发展人编码，可空【10】")
	private String param14 = "";
	@ZteSoftCommentAnnotationParam(name = "生日", type = "String", isNecessary = "N", desc = "开户预提交->param15:生日，可空【10】,YYYYMMDD")
	private String param15 = "";
	@ZteSoftCommentAnnotationParam(name = "号码来源标识,0:省份 1:总部", type = "String", isNecessary = "Y", desc = "开户预提交->source_flag:号码来源标识,0:省份 1:总部")
	private String source_flag = "";
	@ZteSoftCommentAnnotationParam(name = "服务器地址地址配置标识", type = "String", isNecessary = "Y", desc = "开户预提交->url_key:服务器地址地址配置标识")
	private String url_key = "";
	@ZteSoftCommentAnnotationParam(name = "向号码中心发起预占的请求", type = "String", isNecessary = "Y", desc = "开户预提交->http_request:向号码中心发起预占的请求")
	private String http_request = "";
	@ZteSoftCommentAnnotationParam(name = "资源中心号码", type = "String", isNecessary = "Y", desc = "开户预提交->res_number:资源中心号码")
	private String res_number = "";
	@ZteSoftCommentAnnotationParam(name = "用户是否激活", type = "String", isNecessary = "Y", desc = "开户预提交->nomalize_flag:用户是否激活")
	private String nomalize_flag = "";
	@ZteSoftCommentAnnotationParam(name = "预占成功回调的请求", type = "String", isNecessary = "Y", desc = "开户预提交->http_request1:预占成功回调的请求")
	private String http_request1 = "";
	@ZteSoftCommentAnnotationParam(name = "拍照流水号,非空", type = "String", isNecessary = "Y", desc = "开户预提交->req_swift_num:拍照流水号,非空")
	private String req_swift_num = "";
	@ZteSoftCommentAnnotationParam(name = "日租卡开户费用", type = "String", isNecessary = "N", desc = "开户预提交->param16:日租卡开户费用，单位厘可空【10】")
	private String param16 = "";
	@ZteSoftCommentAnnotationParam(name = "靓号靓号等级", type = "String", isNecessary = "Y", desc = "开户预提交->lhscheme_id:靓号活动ID")
	private String number_level = "";
	@ZteSoftCommentAnnotationParam(name = "靓号活动ID", type = "String", isNecessary = "Y", desc = "开户预提交->lhscheme_id:靓号活动ID")
	private String lhscheme_id = "";
	@ZteSoftCommentAnnotationParam(name = "靓号预存金额", type = "String", isNecessary = "Y", desc = "开户预提交->pre_fee:靓号预存金额")
	private String pre_fee = "";
	@ZteSoftCommentAnnotationParam(name = "是否压单,1:是 = 0:否", type = "String", isNecessary = "Y", desc = "开户预提交->IsBacklogOrder:是否压单,1:是;0: =")
	private String IsBacklogOrder = "";
	@ZteSoftCommentAnnotationParam(name = "商城订单号", type = "String", isNecessary = "Y", desc = "开户预提交->out_order_id:商城订单号")
	private String out_order_id = "";
	@ZteSoftCommentAnnotationParam(name = "订单id", type = "String", isNecessary = "Y", desc = "卡信息获取->order_id:订单中心单号 订单中心单号")
	private String order_id = "";
	/**
	 * Add Wcl
	 * 号卡开户预提交新增imsi字段
	 */
	@ZteSoftCommentAnnotationParam(name = "imsi", type = "String", isNecessary = "Y", desc = "开户预提交->imsi:白卡必传")
	private String imsi = "";
	//集客新增7个字段
	@ZteSoftCommentAnnotationParam(name = "集团编号", type = "String", isNecessary = "Y", desc = "开户预提交->cust_id:集团编号")
	private String group_code = "";
	@ZteSoftCommentAnnotationParam(name = "集团客户编号", type = "String", isNecessary = "Y", desc = "开户预提交->cust_id:集团客户编号")
	private String cust_id = "";
	@ZteSoftCommentAnnotationParam(name = "责任人/使用人标识", type = "String", isNecessary = "Y", desc = "开户预提交->certify_flag:责任人/使用人标识")
	private String certify_flag = "";
	@ZteSoftCommentAnnotationParam(name = "责任人/使用人证件类型", type = "String", isNecessary = "Y", desc = "开户预提交->certify_cert_type:责任人/使用人证件类型")
	private String certify_cert_type = "";
	@ZteSoftCommentAnnotationParam(name = "责任人/使用人证件号码", type = "String", isNecessary = "Y", desc = "开户预提交->certify_cert_num:责任人/使用人证件号码")
	private String certify_cert_num = "";
	@ZteSoftCommentAnnotationParam(name = "责任人/使用人客户姓名", type = "String", isNecessary = "Y", desc = "卡信息获取->certify_cust_name:责任人/使用人客户姓名")
	private String certify_cust_name = "";
	@ZteSoftCommentAnnotationParam(name = "责任人/使用人客户证件地址", type = "String", isNecessary = "Y", desc = "卡信息获取->certify_cust_name:责任人/使用人客户证件地址")
	private String certify_cert_addr = "";
	@ZteSoftCommentAnnotationParam(name = "无线宽带号码", type = "String", isNecessary = "Y", desc = "无线宽带号码")
	private String evdo_num ;
	@ZteSoftCommentAnnotationParam(name = "无线宽带用户种类", type = "String", isNecessary = "Y", desc = "无线宽带用户种类") //和宽带的用户种类分开。。
	private String user_knd ;
	@ZteSoftCommentAnnotationParam(name = "证件号码 出入境号", type = "String", isNecessary = "Y", desc = "开户预提交->cert_num2:出入境号，非空【50】")
    private String cert_num2 = "";
	@ZteSoftCommentAnnotationParam(name = "销售模式", type = "String", isNecessary = "N", desc = "销售模式：01：免费租用 04-全额补贴（无线宽带目前只有这种）06：用户自购 07：用户自备用户自备")
    private String sale_mode = "";
	@ZteSoftCommentAnnotationParam(name = "终端串码", type = "String", isNecessary = "N", desc = "终端串码")
    private String object_esn = "";
	@ZteSoftCommentAnnotationParam(name = "物品ID", type = "String", isNecessary = "N", desc = "物品ID")
    private String object_id = "";
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	public String getEvdo_num() {
		return evdo_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "evdo_num");
	}
	public void setEvdo_num(String evdo_num) {
		this.evdo_num = evdo_num;
	}
	
	public String getUser_knd() {
		return user_knd = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "user_kind");
	}
	public void setUser_knd(String user_knd) {
		this.user_knd = user_knd;
	}
	public String getService_num() {
		service_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
		return service_num;
	}
	public void setService_num(String service_num) {
		this.service_num = service_num;
	}
	public String getService_type() {
		String catId = CommonDataFactory.getInstance().getGoodSpec(order_id, null, SpecConsts.CAT_ID);
		if(StringUtils.equals(catId, EcsOrderConsts.GOODS_CAT_ID_WUXIANDZ)) {
			service_type = "6116";
		}
		
		return StringUtil.isEmpty(service_type)?"1001":service_type;
	}
	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	public String getSim_card() {
		sim_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
		return sim_card;
	}
	public void setSim_card(String sim_card) {
		this.sim_card = sim_card;
	}
	public String getProduct_id() {
		IOrderInfManager orderInfManager = SpringContextHolder.getBean("orderInfManager");
		Map map = orderInfManager.qryGoodsDtl(order_id);
		product_id = com.ztesoft.ibss.common.util.Const.getStrValue(map, "sn")+"";
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getPcheme_id() {
		IOrderInfManager orderInfManager = SpringContextHolder.getBean("orderInfManager");
		Map map = orderInfManager.qryGoodsDtl(order_id);
		pcheme_id = com.ztesoft.ibss.common.util.Const.getStrValue(map, "p_code")+"";
		return pcheme_id;
	}
	public void setPcheme_id(String pcheme_id) {
		this.pcheme_id = pcheme_id;
	}
	public String getCustomer_name() {
		customer_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NAME);//开户人姓名
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getCert_type() {
		return CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_BSS_CERT_TYPE", CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE));
	}
	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}
	public String getCert_num() {
		cert_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM);//证件号码
		return cert_num;
	}
	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
	}
	public String getCert_addr() {
		cert_addr = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS);
		return cert_addr;
	}
	public void setCert_addr(String cert_addr) {
		this.cert_addr = cert_addr;
	}
	public String getCert_nation() {
		cert_nation = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NATION);
		return cert_nation;
	}
	public void setCert_nation(String cert_nation) {
		this.cert_nation = cert_nation;
	}
	public String getCert_sex() {
		String certnum = getCert_num();
		int num = Character.getNumericValue(certnum.charAt(certnum.length()-2));
		num = Math.abs(num%2-1);
		return String.valueOf(num);
	}
	public void setCert_sex(String cert_sex) {
		this.cert_sex = cert_sex;
	}
	public String getCert_issuedat() {
		cert_issuedat = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_ISSUER);
		return cert_issuedat;
	}
	public void setCert_issuedat(String cert_issuedat) {
		this.cert_issuedat = cert_issuedat;
	}
	public String getCert_expire_date() {
		cert_expire_date = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_FAILURE_TIME);//证件失效时间
		return "";
	}
	public void setCert_expire_date(String cert_expire_date) {
		this.cert_expire_date = cert_expire_date;
	}
	public String getCert_effected_date() {
		cert_effected_date = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_EFF_TIME);//证件生效时间
		return "";
	}
	public void setCert_effected_date(String cert_effected_date) {
		this.cert_effected_date = cert_effected_date;
	}
	public String getCert_photo() {
		return cert_photo;
	}
	public void setCert_photo(String cert_photo) {
		this.cert_photo = cert_photo;
	}
	public String getContact_name() {
		contact_name = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderDeliveryBusiRequests().get(0).getShip_name();
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getContact_phone() {
		contact_phone = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderDeliveryBusiRequests().get(0).getShip_mobile();
		return contact_phone;
	}
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}
	public String getCustomer_adder() {
		customer_adder = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderDeliveryBusiRequests().get(0).getShip_addr();
		return customer_adder;
	}
	public void setCustomer_adder(String customer_adder) {
		this.customer_adder = customer_adder;
	}
	public String getCert_verified() {
		String is_realname = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "is_realname");
		if(!StringUtils.isEmpty(is_realname)&&"1".equals(is_realname)){
			cert_verified = "Y";
		}else{
			cert_verified = "";
		}
		return cert_verified;
	}
	public void setCert_verified(String cert_verified) {
		this.cert_verified = cert_verified;
	}
	public String getDeal_operator() {
		ICacheUtil cacheUtil = (ICacheUtil) SpringContextHolder.getBean("cacheUtil");
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(this.order_id);
		String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");
		
		if(orderTree!=null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)){
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(this.order_id,
					"order_deal_method");
			
			//自定义流程
			if("2".equals(deal_method)){
				//线下方式受理业务，取传入的操作员
				String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OPERATOR);
				
				if(!StringUtil.isEmpty(OUT_OPERATOR)){
					deal_operator = OUT_OPERATOR;
				}else{
					deal_operator = ZjCommonUtils.getGonghaoInfoByOrderId(order_id).getUser_code();
				}
			}else{
				//线上方式受理取配置的操作员
				deal_operator = ZjCommonUtils.getGonghaoInfoByOrderId(order_id).getUser_code();
			}
		}else{
			String source_from = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOrder_from();
			String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OPERATOR);
			
			String bss_deal_source_from = cacheUtil.getConfigInfo("BSS_DEAL_SOURCE_FROM");
			if(!StringUtils.isEmpty(source_from)&&bss_deal_source_from.contains(source_from)&&!StringUtil.isEmpty(OUT_OPERATOR)){
				deal_operator = OUT_OPERATOR;
			}else{
				deal_operator = ZjCommonUtils.getGonghaoInfoByOrderId(order_id).getUser_code();
			}
		}
		
		return deal_operator;
	}
	public void setDeal_operator(String deal_operator) {
		this.deal_operator = deal_operator;
	}
	public String getDeal_office_id() {
		ICacheUtil cacheUtil = (ICacheUtil) SpringContextHolder.getBean("cacheUtil");
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(this.order_id);
		String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");
		
		if(orderTree!=null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)){
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(this.order_id,
					"order_deal_method");
			
			//自定义流程
			if("2".equals(deal_method)){
				//线下方式受理业务，取传入的操作点
				String OUT_OFFICE = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OFFICE);
				
				if(!StringUtil.isEmpty(OUT_OFFICE)){
					deal_office_id = OUT_OFFICE;
				}else{
					deal_office_id = ZjCommonUtils.getGonghaoInfoByOrderId(order_id).getDept_id();
				}
			}else{
				//线上方式受理取配置的操作点
				deal_office_id = ZjCommonUtils.getGonghaoInfoByOrderId(order_id).getDept_id();
			}
		}else{
			String source_from = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOrder_from();
			String OUT_OFFICE = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OFFICE);
			
			String bss_deal_source_from = cacheUtil.getConfigInfo("BSS_DEAL_SOURCE_FROM");
			if(!StringUtils.isEmpty(source_from)&&bss_deal_source_from.contains(source_from)&&!StringUtil.isEmpty(OUT_OFFICE)){
				deal_office_id = OUT_OFFICE;
			}else{
				deal_office_id = ZjCommonUtils.getGonghaoInfoByOrderId(order_id).getDept_id();
			}
		}
		return deal_office_id;
	}
	public void setDeal_office_id(String deal_office_id) {
		this.deal_office_id = deal_office_id;
	}
	public String getParam11() {
	    param11 = CommonDataFactory.getInstance().getAttrFieldValue(order_id,"is_blankcard");//是否白卡
	    if("0".equals(param11)){
	        return param11;
	    }else{
	        return StringUtil.isEmpty(param11)?"1":param11;
	    }
	}
	public void setParam11(String param11) {
		this.param11 = param11;
	}
	public String getParam12() {
		/*return StringUtil.isEmpty(param11)?"01":param11; *///01 营业  02 代理   默认营业
		return param12;
	}
	public void setParam12(String param12) {
		this.param12 = param12;
	}
	public String getParam13() {
		//param13 = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.DEVELOPMENT_CODE);//发展人部门
	    param13 = CommonDataFactory.getInstance().getAttrFieldValue(order_id,"develop_point_code_new");//发展人部门
		return param13;
	}
	public void setParam13(String param13) {
		this.param13 = param13;
	}
	public String getParam14() {
		//param14 = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.DEVELOPMENT_NAME);//发展人编码
        param14 = CommonDataFactory.getInstance().getAttrFieldValue(order_id,"develop_code_new");//发展人部门
		return param14;
	}
	public void setParam14(String param14) {
		this.param14 = param14;
	}
	public String getParam15() {
		param15 = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_BIRTH);//生日
		return param15;
	}
	public void setParam15(String param15) {
		this.param15 = param15;
	}
	public String getSource_flag() {
		return source_flag; 
	}
	public void setSource_flag(String source_flag) {
		this.source_flag = source_flag;
	}
	public String getUrl_key() {
		return url_key;
	}
	public void setUrl_key(String url_key) {
		this.url_key = url_key;
	}
	public String getHttp_request() {
		return http_request;
	}
	public void setHttp_request(String http_request) {
		this.http_request = http_request;
	}
	public String getRes_number() {
		return res_number;
	}
	public void setRes_number(String res_number) {
		this.res_number = res_number;
	}
	public String getNomalize_flag() {
		return nomalize_flag;
	}
	public void setNomalize_flag(String nomalize_flag) {
		this.nomalize_flag = nomalize_flag;
	}
	public String getHttp_request1() {
		return http_request1;
	}
	public void setHttp_request1(String http_request1) {
		this.http_request1 = http_request1;
	}
	public String getReq_swift_num() {
//		req_swift_num = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderAdslBusiRequest().get(0).getReq_swift_num();
		req_swift_num = "";
		String new_req_swift_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.COUPON_BATCH_ID);
		if(!StringUtil.isEmpty(new_req_swift_num)){
			req_swift_num = new_req_swift_num;
		}
		return req_swift_num;
	}
	public void setReq_swift_num(String req_swift_num) {
		this.req_swift_num = req_swift_num;
	}
	public String getParam16() {
//		Double paymoney = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderBusiRequest().getPaymoney()*1000;
//		param16 = String.valueOf(paymoney.intValue());
		String goods_id=CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0).getGoods_id();
		String is_day_card = CommonDataFactory.getInstance().getGoodSpec(order_id, goods_id, "is_day_card");
		if(!StringUtil.isEmpty(is_day_card)&&"1".equals(is_day_card)){
			String phone_deposit = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getPhone_deposit();
			Integer cny = (int) (Double.parseDouble(phone_deposit) * 1000);
			param16 = cny + "";
		}
		return param16;
	}
	public void setParam16(String param16) {
		this.param16 = param16;
	}
	
	public String getNumber_level() {
		number_level = CommonDataFactory.getInstance().getOrderTree(order_id).getPhoneInfoBusiRequest().getClassId()+"";
		return StringUtil.equals(number_level, "9")?"":number_level;
	
	}
	public void setNumber_level(String number_level) {
		this.number_level = number_level;
	}
	public String getLhscheme_id() {

		lhscheme_id = CommonDataFactory.getInstance().getOrderTree(order_id).getPhoneInfoBusiRequest().getLhscheme_id();
//		if (StringUtils.isEmpty(lhscheme_id)) {
//			lhscheme_id = CommonDataFactory.getInstance().getOrderTree(order_id).getPhoneInfoBusiRequest().getClassId()+"";
//		}
//		StringUtil.equals(lhscheme_id, "9")?"":lhscheme_id;
		return lhscheme_id;
	}
	public void setLhscheme_id(String lhscheme_id) {
		this.lhscheme_id = lhscheme_id;
	}
	public String getPre_fee() {
		return CommonDataFactory.getInstance().getOrderTree(order_id).getPhoneInfoBusiRequest().getAdvancePay();
	}
	public void setPre_fee(String pre_fee) {
		this.pre_fee = pre_fee;
	}
	@SuppressWarnings("unchecked")
	public String getIsBacklogOrder() {
		ICacheUtil cacheUtil = (ICacheUtil) SpringContextHolder.getBean("cacheUtil");
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(this.order_id);
		String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");
		
		if(orderTree!=null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)){
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(this.order_id,
					"order_deal_method");
			
			//自定义流程
			if("2".equals(deal_method)){
				//线下方式受理业务，不压单
				this.IsBacklogOrder = "0";
			}else{
				//线上方式受理，压单
				this.IsBacklogOrder = "1";
			}
		}else{
			this.IsBacklogOrder = "1";
			
			// 根据生产模式决定是否后激活
			String order_model = CommonDataFactory.getInstance().getOrderTree(order_id).
					getOrderExtBusiRequest().getOrder_model();
			
			String ORDER_MODELS = cacheUtil.getConfigInfo("ACTIVE_NOW_ORDER_MODEL");
			
			if(!StringUtil.isEmpty(ORDER_MODELS)){
				String[] models = ORDER_MODELS.split(",");
				
				if(models!=null && models.length>0){
					List<String> modelsList = Arrays.asList(models);
					
					Set<String> modelsSet = new HashSet<String>(modelsList);
					
					if(modelsSet.contains(order_model)){
						// 行销APP生产模式，现场激活
						this.IsBacklogOrder = "0";
					}
				}
			}
		}
		
		return StringUtil.isEmpty(IsBacklogOrder)?"0":IsBacklogOrder;
	}
	public void setIsBacklogOrder(String isBacklogOrder) {
		IsBacklogOrder = isBacklogOrder;
	}
	public String getOut_order_id() {
		return CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOut_tid();
	}
	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
	}
	
	public String getGroup_code() {
		//
		group_code = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderSubOtherInfoBusiRequest().getGroup_code();
		return group_code;
	}
	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}
	public String getCust_id() {
		//
		cust_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderSubOtherInfoBusiRequest().getCust_id();

		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getCertify_flag() {
		//
		certify_flag = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderSubOtherInfoBusiRequest().getCertify_flag();
		return certify_flag;
	}
	public void setCertify_flag(String certify_flag) {
		this.certify_flag = certify_flag;
	}
	public String getCertify_cert_type() {
		//
		certify_cert_type = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderSubOtherInfoBusiRequest().getCertify_cert_type();
		return certify_cert_type;
	}
	public void setCertify_cert_type(String certify_cert_type) {
		this.certify_cert_type = certify_cert_type;
	}
	public String getCertify_cert_num() {
		//
		certify_cert_num = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderSubOtherInfoBusiRequest().getCertify_cert_num();
		return certify_cert_num;
	}
	public void setCertify_cert_num(String certify_cert_num) {
		this.certify_cert_num = certify_cert_num;
	}
	public String getCertify_cust_name() {
		//
		certify_cust_name = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderSubOtherInfoBusiRequest().getCertify_cust_name();
		return certify_cust_name;
	}
	public void setCertify_cust_name(String certify_cust_name) {
		this.certify_cust_name = certify_cust_name;
	}
	
	public String getCertify_cert_addr() {
		certify_cert_addr = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderSubOtherInfoBusiRequest().getCertify_cert_addr();
		return certify_cert_addr;
	}
	public void setCertify_cert_addr(String certify_cert_addr) {
		this.certify_cert_addr = certify_cert_addr;
	}
	
	public String getImsi() {
		imsi = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "simid");
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	
	public String getCert_num2() {
	    cert_num2 = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "cert_num2");//证件号码
        return cert_num2;

    }
    public void setCert_num2(String cert_num2) {
        this.cert_num2 = cert_num2;
    }
    
    public String getSale_mode() {
        sale_mode = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "sale_mode");
        return sale_mode;
    }
    public void setSale_mode(String sale_mode) {
        this.sale_mode = sale_mode;
    }
    public String getObject_esn() {
        object_esn = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "object_esn");

        return object_esn;
    }
    public void setObject_esn(String object_esn) {
        this.object_esn = object_esn;
    }
    public String getObject_id() {
        object_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "object_id");
        return object_id;
    }
    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }
    @Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.ztesoft.net.ecsord.params.ecaop.req.PreCommitReq";
	}
	
	/**
	* get the value from Map
	*/
	public void fromMap(Map map) {
		setService_num((map.get("service_num") == null?"":(map.get("service_num").toString())));
		setService_type((map.get("service_type") == null?"":(map.get("service_type").toString())));
		setSim_card((map.get("sim_card") == null?"":(map.get("sim_card").toString())));
		setProduct_id((map.get("product_id") == null?"":(map.get("product_id").toString())));
		setPcheme_id((map.get("pcheme_id") == null?"":(map.get("pcheme_id").toString())));
		setCustomer_name((map.get("customer_name") == null?"":(map.get("customer_name").toString())));
		setCert_type((map.get("cert_type") == null?"":(map.get("cert_type").toString())));
		setCert_num((map.get("cert_num") == null?"":(map.get("cert_num").toString())));
	    setCert_num2((map.get("cert_num2") == null?"":(map.get("cert_num2").toString())));
		setCert_addr((map.get("cert_addr") == null?"":(map.get("cert_addr").toString())));
		setCert_nation((map.get("cert_nation") == null?"":(map.get("cert_nation").toString())));
		setCert_sex((map.get("cert_sex") == null?"":(map.get("cert_sex").toString())));
		setCert_issuedat((map.get("cert_issuedat") == null?"":(map.get("cert_issuedat").toString())));
		setCert_expire_date((map.get("cert_expire_date") == null?"":(map.get("cert_expire_date").toString())));
		setCert_effected_date((map.get("cert_effected_date") == null?"":(map.get("cert_effected_date").toString())));
		setCert_photo((map.get("cert_photo") == null?"":(map.get("cert_photo").toString())));
		setContact_name((map.get("contact_name") == null?"":(map.get("contact_name").toString())));
		setContact_phone((map.get("contact_phone") == null?"":(map.get("contact_phone").toString())));
		setCustomer_adder((map.get("customer_adder") == null?"":(map.get("customer_adder").toString())));
		setCert_verified((map.get("cert_verified") == null?"":(map.get("cert_verified").toString())));
		setDeal_operator((map.get("deal_operator") == null?"":(map.get("deal_operator").toString())));
		setDeal_office_id((map.get("deal_office_id") == null?"":(map.get("deal_office_id").toString())));
		setParam11((map.get("param11") == null?"":(map.get("param11").toString())));
		setParam12((map.get("param12") == null?"":(map.get("param12").toString())));
		setParam13((map.get("param13") == null?"":(map.get("param13").toString())));
		setParam14((map.get("param14") == null?"":(map.get("param14").toString())));
		setParam15((map.get("param15") == null?"":(map.get("param15").toString())));
		setSource_flag((map.get("source_flag") == null?"":(map.get("source_flag").toString())));
		setUrl_key((map.get("url_key") == null?"":(map.get("url_key").toString())));
		setHttp_request((map.get("http_request") == null?"":(map.get("http_request").toString())));
		setRes_number((map.get("res_number") == null?"":(map.get("res_number").toString())));
		setNomalize_flag((map.get("nomalize_flag") == null?"":(map.get("nomalize_flag").toString())));
		setHttp_request1((map.get("http_request1") == null?"":(map.get("http_request1").toString())));
		setReq_swift_num((map.get("req_swift_num") == null?"":(map.get("req_swift_num").toString())));
		setParam16((map.get("param16") == null?"":(map.get("param16").toString())));
		setLhscheme_id((map.get("lhscheme_id") == null?"":(map.get("lhscheme_id").toString())));
		setPre_fee((map.get("pre_fee") == null?"":(map.get("pre_fee").toString())));
		setIsBacklogOrder((map.get("IsBacklogOrder") == null?"":(map.get("IsBacklogOrder").toString())));
		setOut_order_id((map.get("out_order_id") == null?"":(map.get("out_order_id").toString())));
		setImsi((map.get("imsi") == null?"":(map.get("imsi").toString())));
		
		setObject_id((map.get("object_id") == null?"":(map.get("object_id").toString())));
		setObject_esn((map.get("object_esn") == null?"":(map.get("object_esn").toString())));
		setSale_mode((map.get("sale_mode") == null?"":(map.get("sale_mode").toString())));
		
		setGroup_code(map.get("group_code") == null?"":map.get("group_code").toString());
		setCust_id(map.get("cust_id") == null?"":map.get("cust_id").toString());
		setCertify_flag(map.get("certify_flag") == null?"":map.get("certify_flag").toString());
		setCertify_cert_type(map.get("certify_cert_type") == null?"":map.get("certify_cert_type").toString());
		setCertify_cert_num(map.get("certify_cert_num") == null?"":map.get("certify_cert_num").toString());
		setCertify_cust_name(map.get("certify_cust_name") == null?"":map.get("certify_cust_name").toString());
		setCertify_cert_addr(map.get("certify_cert_addr") == null?"":map.get("certify_cert_addr").toString());
	}
	/**
	* set the value from Map
	*/
	public Map toMap() {
		Map map = new HashMap();
		map.put("service_num",getService_num());
		map.put("service_type",getService_type());
		map.put("sim_card",getSim_card());
		map.put("product_id",getProduct_id());
		map.put("pcheme_id",getPcheme_id());
		map.put("customer_name",getCustomer_name());
		map.put("cert_type",getCert_type());
		map.put("cert_num",getCert_num());
	    map.put("cert_num2",getCert_num2());
		map.put("cert_addr",getCert_addr());
		map.put("cert_nation",getCert_nation());
		map.put("cert_sex",getCert_sex());
		map.put("cert_issuedat",getCert_issuedat());
		map.put("cert_expire_date",getCert_expire_date());
		map.put("cert_effected_date",getCert_effected_date());
		map.put("cert_photo",getCert_photo());
		map.put("contact_name",getContact_name());
		map.put("contact_phone",getContact_phone());
		map.put("customer_adder",getCustomer_adder());
		map.put("cert_verified",getCert_verified());
		map.put("deal_operator",getDeal_operator());
		map.put("deal_office_id",getDeal_office_id());
		map.put("param11",getParam11());
		map.put("param12",getParam12());
		map.put("param13",getParam13());
		map.put("param14",getParam14());
		map.put("param15",getParam15());
		map.put("source_flag",getSource_flag());
		map.put("url_key",getUrl_key());
		map.put("http_request",getHttp_request());
		map.put("res_number",getRes_number());
		map.put("nomalize_flag",getNomalize_flag());
		map.put("http_request1",getHttp_request1());
		map.put("req_swift_num",getReq_swift_num());
		map.put("param16",getParam16());
		map.put("lhscheme_id",getLhscheme_id());
		map.put("pre_fee",getPre_fee());
		map.put("IsBacklogOrder",getIsBacklogOrder());
		map.put("out_order_id",getOut_order_id());
		map.put("imsi", getImsi());
		map.put("object_id", getObject_id());
		map.put("object_esn", getObject_esn());
		map.put("sale_mode", getSale_mode());
		map.put("group_code", getGroup_code());
		map.put("cust_id", getCust_id());
		map.put("certify_flag", getCertify_flag());
		map.put("certify_cert_type", getCertify_cert_type());
		map.put("certify_cert_num", getCertify_cert_num());
		map.put("certify_cust_name", getCertify_cust_name());
		map.put("certify_cert_addr", getCertify_cert_addr());
		return map;
	}
}
