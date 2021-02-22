package zte.net.ecsord.params.bss.req;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

public class PreCommitReq extends ZteRequest {
	
	public PreCommitReq() {
	
	}

	public PreCommitReq(String service_num, String service_type,
			String sim_card, String product_id, String scheme_id,
			String customer_name, String cert_type, String cert_num,
			String cert_addr, String cert_nation, String cert_sex,
			String cert_issuedat, String cert_expire_date,
			String cert_effected_date, String cert_photo, String contact_name,
			String contact_phone, String customer_adder, String cert_verified,
			String operator_id, String office_id, String is_spenum,
			String num_lvl, String res_fee, String real_fee,
			String advance_pay, String package_id, String channel_id,
			String develop_id, String is_blank_card, String use_domain,
			String cust_birthday, String notNeedReqStrOrderId) {

		this.service_num = service_num;
		this.service_type = service_type;
		this.sim_card = sim_card;
		this.product_id = product_id;
		this.scheme_id = scheme_id;
		this.customer_name = customer_name;
		this.cert_type = cert_type;
		this.cert_num = cert_num;
		this.cert_addr = cert_addr;
		this.cert_nation = cert_nation;
		this.cert_sex = cert_sex;
		this.cert_issuedat = cert_issuedat;
		this.cert_expire_date = cert_expire_date;
		this.cert_effected_date = cert_effected_date;
		this.cert_photo = cert_photo;
		this.contact_name = contact_name;
		this.contact_phone = contact_phone;
		this.customer_adder = customer_adder;
		this.cert_verified = cert_verified;
		this.operator_id = operator_id;
		this.office_id = office_id;
		this.is_spenum = is_spenum;
		this.num_lvl = num_lvl;
		this.res_fee = res_fee;
		this.real_fee = real_fee;
		this.advance_pay = advance_pay;
		this.package_id = package_id;
		this.channel_id = channel_id;
		this.develop_id = develop_id;
		this.is_blank_card = is_blank_card;
		this.use_domain = use_domain;
		this.cust_birthday = cust_birthday;
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.zte.unicomService.bss.bssPreCommit";
	}
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="订单编号")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name="联通服务号码",type="String",isNecessary="Y",desc="联通服务号码")
	private String service_num;
	
	@ZteSoftCommentAnnotationParam(name="业务类型",type="String",isNecessary="Y",desc="业务类型")
	private String service_type;
	
	@ZteSoftCommentAnnotationParam(name="SIM卡号",type="String",isNecessary="Y",desc="SIM卡号")
	private String sim_card;
	
	@ZteSoftCommentAnnotationParam(name="主产品ID",type="String",isNecessary="Y",desc="主产品ID")
	private String product_id;
	
	@ZteSoftCommentAnnotationParam(name="活动ID",type="String",isNecessary="N",desc="活动ID")
	private String scheme_id;
	
	@ZteSoftCommentAnnotationParam(name="客户姓名",type="String",isNecessary="Y",desc="客户姓名")
	private String customer_name;
	
	@ZteSoftCommentAnnotationParam(name="证件类型",type="String",isNecessary="Y",desc="证件类型")
	private String cert_type;

	@ZteSoftCommentAnnotationParam(name="证件号码",type="String",isNecessary="Y",desc="证件号码")
	private String cert_num;
	
	@ZteSoftCommentAnnotationParam(name="证件地址",type="String",isNecessary="Y",desc="证件地址")
	private String cert_addr;
	
	@ZteSoftCommentAnnotationParam(name="民族",type="String",isNecessary="N",desc="民族")
	private String cert_nation;
	
	@ZteSoftCommentAnnotationParam(name="性别，0男性，1女性",type="String",isNecessary="N",desc="性别")
	private String cert_sex;
	
	@ZteSoftCommentAnnotationParam(name="签证机关",type="String",isNecessary="N",desc="签证机关")
	private String cert_issuedat;
	
	@ZteSoftCommentAnnotationParam(name="证件失效时间",type="date",isNecessary="N",desc="证件失效时间")
	private String cert_expire_date;
	
	@ZteSoftCommentAnnotationParam(name="证件生效时间",type="String",isNecessary="N",desc="证件生效时间")
	private String cert_effected_date;

	@ZteSoftCommentAnnotationParam(name="照片",type="String",isNecessary="N",desc="照片")
	private String cert_photo;
	
	@ZteSoftCommentAnnotationParam(name="联系人",type="String",isNecessary="Y",desc="联系人")
	private String contact_name;
	
	@ZteSoftCommentAnnotationParam(name="联系电话",type="String",isNecessary="Y",desc="联系电话")
	private String contact_phone;
	
	@ZteSoftCommentAnnotationParam(name="通讯地址",type="String",isNecessary="Y",desc="通讯地址")
	private String customer_adder;
	
	@ZteSoftCommentAnnotationParam(name="客户标识",type="String",isNecessary="N",desc="客户标识")
	private String cert_verified;
	
	@ZteSoftCommentAnnotationParam(name="办理操作员",type="String",isNecessary="Y",desc="办理操作员")
	private String operator_id;
	
	@ZteSoftCommentAnnotationParam(name="办理操作点",type="String",isNecessary="Y",desc="办理操作点")
	private String office_id;
	
	@ZteSoftCommentAnnotationParam(name="是否靓号，01 是 02 否",type="String",isNecessary="Y",desc="是否靓号")
	private String is_spenum;
	
	@ZteSoftCommentAnnotationParam(name="靓号等级",type="String",isNecessary="N",desc="靓号等级")
	private String num_lvl;

	@ZteSoftCommentAnnotationParam(name="减免金额，单位：元，小数点2位",type="String",isNecessary="N",desc="减免金额，单位：元，小数点2位")
	private String res_fee;
	
	@ZteSoftCommentAnnotationParam(name="实收金额，单位：元，小数点2位",type="String",isNecessary="N",desc="实收金额，单位：元，小数点2位")
	private String real_fee;
	
	@ZteSoftCommentAnnotationParam(name="预存话费金额,可空，单位：元，小数点2位",type="String",isNecessary="N",desc="预存话费金额,可空，单位：元，小数点2位")
	private String advance_pay;
	
	@ZteSoftCommentAnnotationParam(name="靓号受理包ID",type="String",isNecessary="N",desc="靓号受理包ID")
	private String package_id;
	
	@ZteSoftCommentAnnotationParam(name="发展渠道",type="String",isNecessary="N",desc="发展渠道")
	private String channel_id;
	
	@ZteSoftCommentAnnotationParam(name="发展人ID",type="String",isNecessary="N",desc="发展人ID")
	private String develop_id;
	
	@ZteSoftCommentAnnotationParam(name="是否白卡,01,白卡;02:成卡",type="String",isNecessary="N",desc="是否白卡,01,白卡;02:成卡")
	private String is_blank_card;

	@ZteSoftCommentAnnotationParam(name="01:营业;02;代理",type="String",isNecessary="N",desc="01:营业;02;代理")
	private String use_domain;
	
	@ZteSoftCommentAnnotationParam(name="客户生日",type="String",isNecessary="Y",desc="客户生日")
	private String cust_birthday;

	public String getService_num() {//联通服务号码
		service_num = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);//开户号码
		if(service_num == null)
			service_num = "";
		return service_num;
	}

	public void setService_num(String service_num) {
		this.service_num = service_num;
	}

	public String getService_type() {//业务类型
		service_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.BIZ_TYPE);//业务类型
		if(service_type == null)
			service_type = "";
		return "1001";
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getSim_card() {//SIM卡号
		sim_card = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SIMID);//SIM卡号
		if(sim_card == null)
			sim_card = "";
		return sim_card;
	}

	public void setSim_card(String sim_card) {
		this.sim_card = sim_card;
	}

	public String getProduct_id() {//主产品ID
		product_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_PLAN_ID_ESS);//总部ESS套餐编码/产品编码
		if(product_id == null)
			product_id = "";
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getScheme_id() {//活动ID
		scheme_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.RESERVE9);//活动编码
		if(scheme_id == null)
			scheme_id = "";
		return scheme_id;
	}

	public void setScheme_id(String scheme_id) {
		this.scheme_id = scheme_id;
	}

	public String getCustomer_name() {//客户姓名
		customer_name = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NAME);//开户人姓名
		if(customer_name == null)
			customer_name = "";
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCert_type() {//证件类型
		cert_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERTI_TYPE);//证件类型
		if(cert_type == null){
			cert_type = "";
		}else if(EcsOrderConsts.CERTI_TYPE_SFZ18.equals(cert_type)) {
			cert_type = "11";
		} else if(EcsOrderConsts.CERTI_TYPE_SFZ15.equals(cert_type)) {
			cert_type = "10";
		}
		return cert_type;
	}

	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}

	public String getCert_num() {//证件号码
		cert_num = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NUM);//开户人证件号码
		if(cert_num == null)
			cert_num = "";
		return cert_num;
	}

	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
	}

	public String getCert_addr() {//证件地址
		cert_addr = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_ADDRESS);//证件地址
		if(cert_addr == null)
			cert_addr = "";
		return cert_addr;
	}

	public void setCert_addr(String cert_addr) {
		this.cert_addr = cert_addr;
	}

	public String getCert_nation() {//民族
		cert_nation = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NATION);//开户人民族
		if(cert_nation == null)
			cert_nation = "";
		return cert_nation;
	}

	public void setCert_nation(String cert_nation) {
		this.cert_nation = cert_nation;
	}

	public String getCert_sex() {//性别
		cert_sex = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_SEX);//开户人性别
		if(cert_sex == null)
			cert_sex = "";
		return cert_sex;
	}

	public void setCert_sex(String cert_sex) {
		this.cert_sex = cert_sex;
	}

	public String getCert_issuedat() {//签证机关
		cert_issuedat = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_ISSUER);//签证机关
		if(cert_issuedat == null)
			cert_issuedat = "";
		return cert_issuedat;
	}

	public void setCert_issuedat(String cert_issuedat) {
		this.cert_issuedat = cert_issuedat;
	}

	public String getCert_expire_date() {//证件失效时间
		//DateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
			CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_FAILURE_TIME);//证件失效时间
		

		return cert_expire_date;
	}

	public void setCert_expire_date(String cert_expire_date) {
		this.cert_expire_date = cert_expire_date;
	}

	public String getCert_effected_date() {//证件生效时间
		
			cert_effected_date = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_EFF_TIME);//证件生效时间
		

		return cert_effected_date;
	}

	public void setCert_effected_date(String cert_effected_date) {
		this.cert_effected_date = cert_effected_date;
	}

	public String getCert_photo() {//照片
		if(cert_photo == null)
			cert_photo = "";
		return cert_photo;
	}

	public void setCert_photo(String cert_photo) {
		this.cert_photo = cert_photo;
	}

	public String getContact_name() {//联系人
		contact_name = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIP_NAME);//收获人姓名
		if(contact_name == null)
			contact_name = "";
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getContact_phone() {//联系电话
		contact_phone = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.REFERENCE_PHONE);//收获人电话
		if(contact_phone == null)
			contact_phone = "";
		return contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public String getCustomer_adder() {//通讯地址		
		customer_adder = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CONTACT_ADDR);//收获人联系地址
		if(customer_adder == null)
			customer_adder = "";
		return customer_adder;
	}

	public void setCustomer_adder(String customer_adder) {
		this.customer_adder = customer_adder;
	}

	public String getCert_verified() {//客户标识
		cert_verified = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CUST_ID);//客户标识
		if(cert_verified == null)
			cert_verified = "";
		return cert_verified;
	}

	public void setCert_verified(String cert_verified) {
		this.cert_verified = cert_verified;
	}

	public String getOperator_id() {//办理操作员
		operator_id = "AEDKH135";
		if(operator_id == null)
			operator_id = "";
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOffice_id() {//办理操作点
		office_id = "总部商城";
		if(office_id == null)
			office_id = "";
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public String getIs_spenum() {//是否靓号
		is_spenum = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.IS_LIANG);//是否靓号
		if(is_spenum == null)
			is_spenum = "";
		return is_spenum;
	}

	public void setIs_spenum(String is_spenum) {
		this.is_spenum = is_spenum;
	}

	public String getNum_lvl() {//靓号等级--不传
		if(num_lvl == null)
			num_lvl = "";
		return num_lvl;
	}

	public void setNum_lvl(String num_lvl) {
		this.num_lvl = num_lvl;
	}

	public String getRes_fee() {//减免金额
		res_fee = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DISCOUNTRANGE);//优惠幅度（优惠金额）
		if(res_fee == null)
			res_fee = "";
		return res_fee;
	}

	public void setRes_fee(String res_fee) {
		this.res_fee = res_fee;
	}

	public String getReal_fee() {//实收金额
		real_fee = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_REAL_FEE);//订单实收金额
		if(real_fee == null)
			real_fee = "";
		return real_fee;
	}

	public void setReal_fee(String real_fee) {
		this.real_fee = real_fee;
	}

	public String getAdvance_pay() {//预存话费金额

		if(advance_pay == null)
			advance_pay = "";
		return advance_pay;
	}

	public void setAdvance_pay(String advance_pay) {
		this.advance_pay = advance_pay;
	}

	public String getPackage_id() {//靓号受理包ID
		package_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.LIANG_CODE);//靓号单编码
		if(package_id == null)
			package_id = "";
		return package_id;
	}

	public void setPackage_id(String package_id) {
		this.package_id = package_id;
	}

	public String getChannel_id() {//发展渠道
		channel_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CHA_NAME);//发展人渠道名称
		if(channel_id == null)
			channel_id = "";
		return channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}

	public String getDevelop_id() {//发展人ID
		develop_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DEVELOPMENT_CODE);//发展人编码
		if(develop_id == null)
			develop_id = "";
		return develop_id;
	}

	public void setDevelop_id(String develop_id) {
		this.develop_id = develop_id;
	}

	public String getIs_blank_card() {//是否白卡
		is_blank_card = "01";// 01 白卡  02 成卡  目前只有白卡，默认白卡
		return is_blank_card;
	}

	public void setIs_blank_card(String is_blank_card) {
		this.is_blank_card = is_blank_card;
	}

	public String getUse_domain() {//使用区域
		use_domain = "01";//01 营业  02 代理   默认营业
		return use_domain;
	}

	public void setUse_domain(String use_domain) {
		this.use_domain = use_domain;
	}

	public String getCust_birthday() {//客户生日
		cust_birthday = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_BIRTH);//开户人生日
		if(cust_birthday == null)
			cust_birthday = "";
		return cust_birthday;
	}

	public void setCust_birthday(String cust_birthday) {
		this.cust_birthday = cust_birthday;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	
}