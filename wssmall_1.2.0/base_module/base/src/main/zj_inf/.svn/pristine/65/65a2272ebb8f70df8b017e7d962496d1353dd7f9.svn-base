package zte.net.ecsord.params.ecaop.req;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.utils.ZjCommonUtils;


public class ReturnFileReq extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name = "联通服务号码", type = "String", isNecessary = "Y", desc = "用户返档->service_num:联通服务号码，非空【20】")
	private String service_num = "";
	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "Y", desc = "用户返档->cert_type:证件类型，非空 【2】")
	private String cert_type = "";
	@ZteSoftCommentAnnotationParam(name = "证件号码", type = "String", isNecessary = "Y", desc = "用户返档->cert_num:证件号码，非空【50】")
	private String cert_num = "";
	@ZteSoftCommentAnnotationParam(name = "主产品ID", type = "String", isNecessary = "Y", desc = "用户返档->product_id:主产品ID，非空【8】")
	private String product_id = "";
	@ZteSoftCommentAnnotationParam(name = "客户姓名", type = "String", isNecessary = "Y", desc = "用户返档->customer_name:客户姓名，非空【60】")
	private String cust_name = "";
	@ZteSoftCommentAnnotationParam(name = "客户地址", type = "String", isNecessary = "Y", desc = "用户返档->cust_addr:客户地址，非空【128】")
	private String cust_addr = "";
	@ZteSoftCommentAnnotationParam(name = "邮编号码", type = "String", isNecessary = "Y", desc = "用户返档->cert_zip:邮编号码，非空【128】")
	private String cust_zip = "";
	@ZteSoftCommentAnnotationParam(name = "账户名称", type = "String", isNecessary = "Y", desc = "用户返档->accout_name:账户名称，非空【128】")
	private String accout_name = "";
	@ZteSoftCommentAnnotationParam(name = "结算通讯地址", type = "String", isNecessary = "Y", desc = "用户返档->account_addr:结算通讯地址，非空【128】")
	private String account_addr = "";
	@ZteSoftCommentAnnotationParam(name = "性别", type = "String", isNecessary = "N", desc = "用户返档->sex:性别，非空【1】0男性，1女性")
	private String sex = "";
	@ZteSoftCommentAnnotationParam(name = "客户类型", type = "String", isNecessary = "N", desc = "用户返档->customer_type:客户类型，非空【3】100：个人客户;200：单位客户;300：集团客户")
	private String customer_type = "";
	@ZteSoftCommentAnnotationParam(name = "客户级别", type = "String", isNecessary = "N", desc = "用户返档->customer_level:客户级别，非空【3】100：普通客户；200：重要客户；300：大客户")
	private String customer_level = "";
	@ZteSoftCommentAnnotationParam(name = "客户邮件", type = "String", isNecessary = "N", desc = "用户返档->customer_email:客户邮件，非空【】")
	private String customer_email = "";
	@ZteSoftCommentAnnotationParam(name = "联系电话", type = "String", isNecessary = "Y", desc = "用户返档->contact_phone:联系电话，非空【20】")
	private String contact_phone = "";
	@ZteSoftCommentAnnotationParam(name = "客户生日", type = "String", isNecessary = "N", desc = "用户返档->customer_birthday:客户生日，非空[8],YYYYMMDD")
	private String customer_birthday = "";
	@ZteSoftCommentAnnotationParam(name = "客户职业", type = "String", isNecessary = "N", desc = "用户返档->customer_job:客户职业，可空[2]"+
						          "10：党政机关干部、公务员 11：公司、企业、事业单位职工 12：工矿企业职工 13：农、林、牧、渔、水利 14：教师、教工 15：军人、警察 16：体育 17：医疗卫生 18：交通运输 19：金融 20：证券 21：保险 22：邮政、电信与通信 23：IT行业 24：旅游、服务、娱乐业")
	private String customer_job = "";
	@ZteSoftCommentAnnotationParam(name = "客户工作单位", type = "String", isNecessary = "N", desc = "用户返档->customer_orga:客户工作单位，可空[8],YYYYMMDD")
	private String customer_orga = "";
	@ZteSoftCommentAnnotationParam(name = "单位性质", type = "String", isNecessary = "N", desc = "用户返档->org_type:单位性质，可空[8],YYYYMMDD")
	private String org_type = "";
	@ZteSoftCommentAnnotationParam(name = "客户标识", type = "String", isNecessary = "N", desc = "用户返档->cert_verified:客户标识，Y实名-二代，可空【2】")
	private String cert_verified = "";
	@ZteSoftCommentAnnotationParam(name = "拍照流水号,非空", type = "String", isNecessary = "Y", desc = "用户返档->req_swift_num:拍照流水号,可空")
	private String req_swift_num = "";
	@ZteSoftCommentAnnotationParam(name = "是否压单,1:是 = 0:否", type = "String", isNecessary = "Y", desc = "用户返档->IsBacklogOrder:是否压单,1:是;0: =")
	private String IsBacklogOrder = "";
	@ZteSoftCommentAnnotationParam(name = "商城订单号", type = "String", isNecessary = "Y", desc = "用户返档->out_out_order_id:商城订单号")
	private String out_order_id = "";
	@ZteSoftCommentAnnotationParam(name = "办理操作点", type = "String", isNecessary = "Y", desc = "用户返档->office_id:办理操作点，非空【30】")
	private String office_id = "";
	@ZteSoftCommentAnnotationParam(name = "受理工号", type = "String", isNecessary = "Y", desc = "用户返档->operator_id:受理工号，非空【30】")
	private String operator_id = "";
	@ZteSoftCommentAnnotationParam(name = "发展点", type = "String", isNecessary = "Y", desc = "用户返档->channel_id:发展点，可空【30】")
	private String channel_id = "";
	@ZteSoftCommentAnnotationParam(name = "发展人", type = "String", isNecessary = "Y", desc = "用户返档->develop_id:发展人，可空【30】")
	private String develop_id = "";

	
	
	public String getService_num() {
		service_num = CommonDataFactory.getInstance().getAttrFieldValue(out_order_id, AttrConsts.PHONE_NUM);
		return service_num;
	}
	public void setService_num(String service_num) {
		this.service_num = service_num;
	}
	
	public String getCert_type() {
		return CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_BSS_CERT_TYPE", CommonDataFactory.getInstance().getAttrFieldValue(out_order_id, AttrConsts.CERTI_TYPE));
	}
	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}
	
	public String getCert_num() {
		cert_num = CommonDataFactory.getInstance().getAttrFieldValue(out_order_id, AttrConsts.CERT_CARD_NUM);//证件号码
		return cert_num;
	}
	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
	}
	
	public String getProduct_id() {
//		IDcPublicInfoManager orderInfManager = SpringContextHolder.getBean("dcPublicInfoManager");
//		Map map = orderInfManager.getProductIdByOrderId(out_order_id);
//		product_id = com.ztesoft.ibss.common.util.Const.getStrValue(map, "sn")+"";
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	
	public String getCust_name() {
		cust_name = CommonDataFactory.getInstance().getAttrFieldValue(out_order_id, AttrConsts.CERT_CARD_NAME);//开户人姓名
		return cust_name;
	}
	public void setCustomer_name(String cust_name) {
		this.cust_name = cust_name;
	}
	
	public String getCust_addr() {
		cust_addr = CommonDataFactory.getInstance().getAttrFieldValue(out_order_id, AttrConsts.CERT_ADDRESS);
		return cust_addr;
	}
	public void setCert_addr(String cust_addr) {
		this.cust_addr = cust_addr;
	}
	
	public String getCust_zip() {
		cust_zip = CommonDataFactory.getInstance().getAttrFieldValue(out_order_id, AttrConsts.ORDER_CITY_CODE);
		return cust_zip;
	}
	public void setCust_zip(String cust_zip) {
		this.cust_zip = cust_zip;
	}
	
	public String getAccout_name() {
		accout_name = CommonDataFactory.getInstance().getAttrFieldValue(out_order_id, AttrConsts.CERT_CARD_NAME);
		return accout_name;
	}
	public void setAccout_name(String accout_name) {
		this.accout_name = accout_name;
	}
	public String getAccount_addr() {
		return CommonDataFactory.getInstance().getAttrFieldValue(out_order_id, AttrConsts.CERT_ADDRESS);
	}
	public void setAccount_addr(String account_addr) {
		this.account_addr = account_addr;
	}
	
	public String getSex() {
		String certnum = getCert_num();
		int num = Character.getNumericValue(certnum.charAt(certnum.length()-2));
		num = Math.abs(num%2-1);
		return String.valueOf(num);
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getCustomer_type() {
		//客户
		customer_type = CommonDataFactory.getInstance().getGoodSpec(out_order_id, null, "customer_type");
		return customer_type;
	}
	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}
	public String getCustomer_level() {
		customer_level = CommonDataFactory.getInstance().getGoodSpec(out_order_id, null, "customer_level");
		return customer_level;
	}
	public void setCustomer_level(String customer_level) {
		this.customer_level = customer_level;
	}
	public String getCustomer_email() {
		customer_email = CommonDataFactory.getInstance().getOrderTree(out_order_id).getOrderDeliveryBusiRequests().get(0).getShip_email();
		return customer_email;
	}
	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}
	
	public String getContact_phone() {
		contact_phone = CommonDataFactory.getInstance().getOrderTree(out_order_id).getOrderDeliveryBusiRequests().get(0).getShip_mobile();
		return contact_phone;
	}
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}
	
	public String getCustomer_birthday() {
		customer_birthday = CommonDataFactory.getInstance().getAttrFieldValue(out_order_id, AttrConsts.CERT_CARD_BIRTH);
		return customer_birthday;
	}
	public void setCustomer_birthday(String customer_birthday) {
		this.customer_birthday = customer_birthday;
	}
	public String getCustomer_job() {
		//职业
		return customer_job;
	}
	public void setCustomer_job(String customer_job) {
		this.customer_job = customer_job;
	}
	public String getCustomer_orga() {
		//
		return customer_orga;
	}
	public void setCustomer_orga(String customer_orga) {
		this.customer_orga = customer_orga;
	}
	public String getOrg_type() {
		return org_type;
	}
	public void setOrg_type(String org_type) {
		this.org_type = org_type;
	}
	public String getCert_verified() {
		return cert_verified;
	}
	public void setCert_verified(String cert_verified) {
		this.cert_verified = cert_verified;
	}
	public String getReq_swift_num() {
		req_swift_num = "";
		String new_req_swift_num = CommonDataFactory.getInstance().getAttrFieldValue(out_order_id, AttrConsts.COUPON_BATCH_ID);
		if(!StringUtil.isEmpty(new_req_swift_num)){
			req_swift_num = new_req_swift_num;
		}
		return req_swift_num;
	}
	public void setReq_swift_num(String req_swift_num) {
		this.req_swift_num = req_swift_num;
	}
	public String getIsBacklogOrder() {
		String goods_id = CommonDataFactory.getInstance().getOrderTree(out_order_id).getOrderItemBusiRequests().get(0).getGoods_id();
		this.IsBacklogOrder = CommonDataFactory.getInstance().getGoodSpec(null, goods_id, "is_last_active");
		return StringUtil.isEmpty(IsBacklogOrder)?"0":IsBacklogOrder;
	}
	public void setIsBacklogOrder(String isBacklogOrder) {
		IsBacklogOrder = isBacklogOrder;
	}
	
	public String getOffice_id() {
		String source_from = CommonDataFactory.getInstance().getOrderTree(out_order_id).getOrderExtBusiRequest().getOrder_from();
		String OUT_OFFICE = CommonDataFactory.getInstance().getAttrFieldValue(out_order_id, AttrConsts.OUT_OFFICE);
		if(!StringUtils.isEmpty(source_from)&&!StringUtil.isEmpty(OUT_OFFICE)){
			office_id = OUT_OFFICE;
		}else{
			office_id = ZjCommonUtils.getGonghaoInfoByOrderId(out_order_id).getDept_id();
		}
		return office_id;
	}
	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}
	public String getOperator_id() {
		String source_from = CommonDataFactory.getInstance().getOrderTree(out_order_id).getOrderExtBusiRequest().getOrder_from();
		String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(out_order_id, AttrConsts.OUT_OPERATOR);
		if(!StringUtils.isEmpty(source_from)&&!StringUtil.isEmpty(OUT_OPERATOR)){
			operator_id = OUT_OPERATOR;
		}else{
			operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(out_order_id).getUser_code();
		}
		return operator_id;
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	public String getChannel_id() {

		channel_id = CommonDataFactory.getInstance().getAttrFieldValue(out_order_id, AttrConsts.DEVELOPMENT_CODE);

		return channel_id;
	}
	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}
	public String getDevelop_id() {
		develop_id = CommonDataFactory.getInstance().getAttrFieldValue(out_order_id, AttrConsts.DEVELOPMENT_NAME);

		return develop_id;
	}
	public void setDevelop_id(String develop_id) {
		this.develop_id = develop_id;
	}
	
	public String getOut_order_id() {
		return out_order_id;
	}
	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public void setCust_addr(String cust_addr) {
		this.cust_addr = cust_addr;
	}
	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.zte.unicomService.zb.returnFile";
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	/**
	* get the value from Map
//	*/
//	public void fromMap(Map map) {
//		setService_num((map.get("service_num") == null?"":(map.get("service_num").toString())));
//		setService_type((map.get("service_type") == null?"":(map.get("service_type").toString())));
//		setSim_card((map.get("sim_card") == null?"":(map.get("sim_card").toString())));
//		setProduct_id((map.get("product_id") == null?"":(map.get("product_id").toString())));
//		setPcheme_id((map.get("pcheme_id") == null?"":(map.get("pcheme_id").toString())));
//		setCustomer_name((map.get("customer_name") == null?"":(map.get("customer_name").toString())));
//		setCert_type((map.get("cert_type") == null?"":(map.get("cert_type").toString())));
//		setCert_num((map.get("cert_num") == null?"":(map.get("cert_num").toString())));
//		setCert_addr((map.get("cert_addr") == null?"":(map.get("cert_addr").toString())));
//		setCert_nation((map.get("cert_nation") == null?"":(map.get("cert_nation").toString())));
//		setCert_sex((map.get("cert_sex") == null?"":(map.get("cert_sex").toString())));
//		setCert_issuedat((map.get("cert_issuedat") == null?"":(map.get("cert_issuedat").toString())));
//		setCert_expire_date((map.get("cert_expire_date") == null?"":(map.get("cert_expire_date").toString())));
//		setCert_effected_date((map.get("cert_effected_date") == null?"":(map.get("cert_effected_date").toString())));
//		setCert_photo((map.get("cert_photo") == null?"":(map.get("cert_photo").toString())));
//		setContact_name((map.get("contact_name") == null?"":(map.get("contact_name").toString())));
//		setContact_phone((map.get("contact_phone") == null?"":(map.get("contact_phone").toString())));
//		setCustomer_adder((map.get("customer_adder") == null?"":(map.get("customer_adder").toString())));
//		setCert_verified((map.get("cert_verified") == null?"":(map.get("cert_verified").toString())));
//		setDeal_operator((map.get("deal_operator") == null?"":(map.get("deal_operator").toString())));
//		setDeal_office_id((map.get("deal_office_id") == null?"":(map.get("deal_office_id").toString())));
//		setParam11((map.get("param11") == null?"":(map.get("param11").toString())));
//		setParam12((map.get("param12") == null?"":(map.get("param12").toString())));
//		setParam13((map.get("param13") == null?"":(map.get("param13").toString())));
//		setParam14((map.get("param14") == null?"":(map.get("param14").toString())));
//		setParam15((map.get("param15") == null?"":(map.get("param15").toString())));
//		setSource_flag((map.get("source_flag") == null?"":(map.get("source_flag").toString())));
//		setUrl_key((map.get("url_key") == null?"":(map.get("url_key").toString())));
//		setHttp_request((map.get("http_request") == null?"":(map.get("http_request").toString())));
//		setRes_number((map.get("res_number") == null?"":(map.get("res_number").toString())));
//		setNomalize_flag((map.get("nomalize_flag") == null?"":(map.get("nomalize_flag").toString())));
//		setHttp_request1((map.get("http_request1") == null?"":(map.get("http_request1").toString())));
//		setReq_swift_num((map.get("req_swift_num") == null?"":(map.get("req_swift_num").toString())));
//		setParam16((map.get("param16") == null?"":(map.get("param16").toString())));
//		setLhscheme_id((map.get("lhscheme_id") == null?"":(map.get("lhscheme_id").toString())));
//		setPre_fee((map.get("pre_fee") == null?"":(map.get("pre_fee").toString())));
//		setIsBacklogOrder((map.get("IsBacklogOrder") == null?"":(map.get("IsBacklogOrder").toString())));
//		setOut_out_order_id((map.get("out_out_order_id") == null?"":(map.get("out_out_order_id").toString())));
//		
//		setGroup_code(map.get("group_code") == null?"":map.get("group_code").toString());
//		setCust_id(map.get("cust_id") == null?"":map.get("cust_id").toString());
//		setCertify_flag(map.get("certify_flag") == null?"":map.get("certify_flag").toString());
//		setCertify_cert_type(map.get("certify_cert_type") == null?"":map.get("certify_cert_type").toString());
//		setCertify_cert_num(map.get("certify_cert_num") == null?"":map.get("certify_cert_num").toString());
//		setCertify_cust_name(map.get("certify_cust_name") == null?"":map.get("certify_cust_name").toString());
//		setCertify_cert_addr(map.get("certify_cert_addr") == null?"":map.get("certify_cert_addr").toString());
//	}
//	/**
//	* set the value from Map
//	*/
//	public Map toMap() {
//		Map map = new HashMap();
//		map.put("service_num",getService_num());
//		map.put("service_type",getService_type());
//		map.put("sim_card",getSim_card());
//		map.put("product_id",getProduct_id());
//		map.put("pcheme_id",getPcheme_id());
//		map.put("customer_name",getCustomer_name());
//		map.put("cert_type",getCert_type());
//		map.put("cert_num",getCert_num());
//		map.put("cert_addr",getCert_addr());
//		map.put("cert_nation",getCert_nation());
//		map.put("cert_sex",getCert_sex());
//		map.put("cert_issuedat",getCert_issuedat());
//		map.put("cert_expire_date",getCert_expire_date());
//		map.put("cert_effected_date",getCert_effected_date());
//		map.put("cert_photo",getCert_photo());
//		map.put("contact_name",getContact_name());
//		map.put("contact_phone",getContact_phone());
//		map.put("customer_adder",getCustomer_adder());
//		map.put("cert_verified",getCert_verified());
//		map.put("deal_operator",getDeal_operator());
//		map.put("deal_office_id",getDeal_office_id());
//		map.put("param11",getParam11());
//		map.put("param12",getParam12());
//		map.put("param13",getParam13());
//		map.put("param14",getParam14());
//		map.put("param15",getParam15());
//		map.put("source_flag",getSource_flag());
//		map.put("url_key",getUrl_key());
//		map.put("http_request",getHttp_request());
//		map.put("res_number",getRes_number());
//		map.put("nomalize_flag",getNomalize_flag());
//		map.put("http_request1",getHttp_request1());
//		map.put("req_swift_num",getReq_swift_num());
//		map.put("param16",getParam16());
//		map.put("lhscheme_id",getLhscheme_id());
//		map.put("pre_fee",getPre_fee());
//		map.put("IsBacklogOrder",getIsBacklogOrder());
//		map.put("out_out_order_id",getOut_out_order_id());
//
//
//		map.put("group_code", getGroup_code());
//		map.put("cust_id", getCust_id());
//		map.put("certify_flag", getCertify_flag());
//		map.put("certify_cert_type", getCertify_cert_type());
//		map.put("certify_cert_num", getCertify_cert_num());
//		map.put("certify_cust_name", getCertify_cust_name());
//		map.put("certify_cert_addr", getCertify_cert_addr());
//		return map;
//	}
}
