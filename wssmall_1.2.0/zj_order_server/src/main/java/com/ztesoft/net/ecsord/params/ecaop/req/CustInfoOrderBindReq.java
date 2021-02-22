package com.ztesoft.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.common.util.date.DateFormatUtils;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.util.ZjCommonUtils;

public class CustInfoOrderBindReq  extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name="订单中心订单ID",type="String",isNecessary="Y",desc="order_id：订单中心订单ID")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="服务号码",type="String",isNecessary="Y",desc="service_num：服务号码")
	private String service_num;
	@ZteSoftCommentAnnotationParam(name="是否压单",type="String",isNecessary="Y",desc="delay_tag：是否压单 0:不压单 1：压单")
	private String delay_tag;
	@ZteSoftCommentAnnotationParam(name="客户名称",type="String",isNecessary="Y",desc="cust_name：客户名称")
	private String cust_name;
	@ZteSoftCommentAnnotationParam(name="证件生效日期",type="String",isNecessary="Y",desc="cert_start_date：格式YYYYMMDD")
	private String cert_start_date;
	@ZteSoftCommentAnnotationParam(name="证件失效期",type="String",isNecessary="Y",desc="cert_end_date：格式YYYYMMDD")
	private String cert_end_date;
	@ZteSoftCommentAnnotationParam(name="证件地址",type="String",isNecessary="Y",desc="cert_addr：证件地址")
	private String cert_addr;
	@ZteSoftCommentAnnotationParam(name="证件类型(11)",type="String",isNecessary="Y",desc="cert_type：18位身份证")
	private String cert_type;
	@ZteSoftCommentAnnotationParam(name="证件号码",type="String",isNecessary="Y",desc="cert_num：证件号码")
	private String cert_num;
	@ZteSoftCommentAnnotationParam(name="生日",type="String",isNecessary="Y",desc="cert_birthday：生日")
	private String cert_birthday;
	@ZteSoftCommentAnnotationParam(name="民族",type="String",isNecessary="Y",desc="cert_nation：填汉字")
	private String cert_nation;
	@ZteSoftCommentAnnotationParam(name="签证机关",type="String",isNecessary="Y",desc="cert_issuedat：签证机关")
	private String cert_issuedat;
	@ZteSoftCommentAnnotationParam(name="客户性别",type="String",isNecessary="Y",desc="sex：0:男 1:女")
	private String sex;
	@ZteSoftCommentAnnotationParam(name="客户通信地址",type="String",isNecessary="Y",desc="cust_addr：客户通信地址")
	private String cust_addr;
	@ZteSoftCommentAnnotationParam(name="联系人姓名",type="String",isNecessary="Y",desc="contract_name：联系人姓名")
	private String contract_name;
	@ZteSoftCommentAnnotationParam(name="联系人电话",type="String",isNecessary="Y",desc="contract_phone：联系人电话")
	private String contract_phone;
	@ZteSoftCommentAnnotationParam(name="认证类型",type="String",isNecessary="Y",desc="cert_issuedat：G：国政通认证 Y：二代证读卡器")
	private String check_flag;
	@ZteSoftCommentAnnotationParam(name="操作点",type="String",isNecessary="Y",desc="office_id：操作点")
	private String office_id;
	@ZteSoftCommentAnnotationParam(name="操作员",type="String",isNecessary="Y",desc="operator_id：操作员")
	private String operator_id;
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getService_num() {
		return CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
	}
	public void setService_num(String service_num) {
		this.service_num = service_num;
	}
	public String getDelay_tag() {
		return delay_tag;
	}
	public void setDelay_tag(String delay_tag) {
		this.delay_tag = delay_tag;
	}
	public String getCust_name() {
		return CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_OWNER_NAME);
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getCert_start_date() {
		return DateFormatUtils.convertDateFormat(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_FAILURE_TIME),DateUtil.DATE_TIME_FORMAT,DateUtil.DATE_FORMAT_8);
	}
	public void setCert_start_date(String cert_start_date) {
		this.cert_start_date = cert_start_date;
	}
	public String getCert_end_date() {
		return DateFormatUtils.convertDateFormat(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_FAILURE_TIME),DateUtil.DATE_TIME_FORMAT,DateUtil.DATE_FORMAT_8);
	}
	public void setCert_end_date(String cert_end_date) {
		this.cert_end_date = cert_end_date;
	}
	public String getCert_addr() {
		return CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_EFF_TIME);
	}
	public void setCert_addr(String cert_addr) {
		this.cert_addr = cert_addr;
	}
	public String getCert_type() {
		return CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_BSS_CERT_TYPE", CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE));
	}
	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}
	public String getCert_num() {
		return CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM);
	}
	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
	}
	public String getCert_birthday() {
		return CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_BIRTH);
	}
	public void setCert_birthday(String cert_birthday) {
		this.cert_birthday = cert_birthday;
	}
	public String getCert_nation() {
		return CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NATION);
	}
	public void setCert_nation(String cert_nation) {
		this.cert_nation = cert_nation;
	}
	public String getCert_issuedat() {
		return CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_ISSUER);
	}
	public void setCert_issuedat(String cert_issuedat) {
		this.cert_issuedat = cert_issuedat;
	}
	public String getSex() {
		return CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_SEX);
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCust_addr() {
		return CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_ADDR);
	}
	public void setCust_addr(String cust_addr) {
		this.cust_addr = cust_addr;
	}
	public String getContract_name() {
		return CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_NAME);
	}
	public void setContract_name(String contract_name) {
		this.contract_name = contract_name;
	}
	public String getContract_phone() {
		return CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REVEIVER_MOBILE);
	}
	public void setContract_phone(String contract_phone) {
		this.contract_phone = contract_phone;
	}
	public String getCheck_flag() {
		return check_flag;
	}
	public void setCheck_flag(String check_flag) {
		this.check_flag = check_flag;
	}
	public String getOffice_id() {
		return ZjCommonUtils.getGonghaoInfoByOrderId(order_id).getDept_id();
	}
	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}
	public String getOperator_id() {
		return ZjCommonUtils.getGonghaoInfoByOrderId(order_id).getUser_code();
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.callCustInfoOrderBind";
	}

}
