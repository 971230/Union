package com.ztesoft.net.server.jsonserver.wcfpojo;

import java.util.List;


public class MallOrder_List {

	//商品编号
	private String prod_offer_code="";
	//商品价格(厘)
	private String offer_price="";
	//优惠价格（厘）
	private String offer_disacount_price="";
	//实收价格（厘）
	private String offer_coupon_price="";
	//商品兑换积分
	private String offer_point = "";
	//商品数量
	private String prod_offer_num="";
	//是否打印发票
	private String is_invoice_print="";
	//发票类型
	private String invoice_type="";
	//发票打印方式
	private String invoice_print_type="";
	//发票抬头
	private String invoice_title="";
	//发票内容
	private String invoice_group_content="";
	//发票明细
	private String invoice_content="";
	//仓库编码
	private String inventory_code="";
	//仓库名称
	private String inventory_name="";
	//商品备注
	private String offer_comment="";
	//证件类型
	private String certi_type="";
	//证件号码
	private String certi_num="";
	//民族【智慧沃企】
	private String certi_nation="";
	//证件地址
	private String certi_address="";
	//发证机关【智慧沃企】
	private String certi_issuer="";
	//证件生效时间【智慧沃企】
	private String certi_eff_date="";
	//证件失效时间
	private String certi_valid_date="";
	//实名标识
	private String realname_flag;
	//性别
	private String certi_sex = "";
	//客户名称
	private String cust_name="";
	//首月资费方式
	private String offer_eff_type="";
	//合约编码
	private String activitycode="";
	//机型编码
	private String resourcestypeid="";
	//是否变更套餐
	private String is_change = "";
	//优惠信息
	private List<MallActivity_List> activity_list;
	//商品参数
	private List<MallOffer_Param> offer_param;
	//套包销售标记
	private String package_sale="";
	//可选活动
	private String choose_active="";
	//可选包
	private String terminal_num="";
	//客户类型
	private String cust_type="";
	//原套餐名称
	private String old_Out_plan_name_ess;
	//优惠券列表
	private List<MallCoupons_List> coupons_list;
	
	private List<ZhwqInfoList> zhwq_info;
	
	private String cert_num;
	private String cert_addr;
	private String customer_name;
	private String cert_effected_date;
	private String cert_sex;
	private String cert_nation;
	private String cust_birthday;
	private String cert_expire_date;
	private String cert_num2;
	public String getTerminal_num() {
		return terminal_num;
	}
	public void setTerminal_num(String terminal_num) {
		this.terminal_num = terminal_num;
	}
	private List<MallWCFPackage> Package;

	
	public String getOffer_point() {
		return offer_point;
	}
	public void setOffer_point(String offer_point) {
		this.offer_point = offer_point;
	}
	public String getCerti_valid_date() {
		return certi_valid_date;
	}
	public void setCerti_valid_date(String certi_valid_date) {
		this.certi_valid_date = certi_valid_date;
	}
	public String getIs_change() {
		return is_change;
	}
	public void setIs_change(String is_change) {
		this.is_change = is_change;
	}
	public String getIs_invoice_print() {
		return is_invoice_print;
	}
	public void setIs_invoice_print(String is_invoice_print) {
		this.is_invoice_print = is_invoice_print;
	}
	public String getInvoice_group_content() {
		return invoice_group_content;
	}
	public void setInvoice_group_content(String invoice_group_content) {
		this.invoice_group_content = invoice_group_content;
	}
	public String getCerti_type() {
		return certi_type;
	}
	public void setCerti_type(String certi_type) {
		this.certi_type = certi_type;
	}
	public String getCerti_num() {
		return certi_num;
	}
	public void setCerti_num(String certi_num) {
		this.certi_num = certi_num;
	}
	public String getCerti_address() {
		return certi_address;
	}
	public void setCerti_address(String certi_address) {
		this.certi_address = certi_address;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getOffer_eff_type() {
		return offer_eff_type;
	}
	public void setOffer_eff_type(String offer_eff_type) {
		this.offer_eff_type = offer_eff_type;
	}
	public List<MallWCFPackage> getPackage() {
		return Package;
	}
	public void setPackage(List<MallWCFPackage> package1) {
		Package = package1;
	}
	public List<MallSKU_Param> getSku_param() {
		return sku_param;
	}
	public void setSku_param(List<MallSKU_Param> sku_param) {
		this.sku_param = sku_param;
	}
	//货品参数
	private List<MallSKU_Param> sku_param;
	
	
	public String getProd_offer_code() {
		return prod_offer_code;
	}
	public void setProd_offer_code(String prod_offer_code) {
		this.prod_offer_code = prod_offer_code;
	}
	public String getOffer_price() {
		return offer_price;
	}
	public void setOffer_price(String offer_price) {
		this.offer_price = offer_price;
	}
	public String getOffer_disacount_price() {
		return offer_disacount_price;
	}
	public void setOffer_disacount_price(String offer_disacount_price) {
		this.offer_disacount_price = offer_disacount_price;
	}
	public String getOffer_coupon_price() {
		return offer_coupon_price;
	}
	public void setOffer_coupon_price(String offer_coupon_price) {
		this.offer_coupon_price = offer_coupon_price;
	}
	public String getProd_offer_num() {
		return prod_offer_num;
	}
	public void setProd_offer_num(String prod_offer_num) {
		this.prod_offer_num = prod_offer_num;
	}
	public String getInvoice_type() {
		return invoice_type;
	}
	public void setInvoice_type(String invoice_type) {
		this.invoice_type = invoice_type;
	}
	public String getInvoice_print_type() {
		return invoice_print_type;
	}
	public void setInvoice_print_type(String invoice_print_type) {
		this.invoice_print_type = invoice_print_type;
	}
	public String getInvoice_title() {
		return invoice_title;
	}
	public void setInvoice_title(String invoice_title) {
		this.invoice_title = invoice_title;
	}
	public String getInvoice_content() {
		return invoice_content;
	}
	public void setInvoice_content(String invoice_content) {
		this.invoice_content = invoice_content;
	}
	public String getInventory_code() {
		return inventory_code;
	}
	public void setInventory_code(String inventory_code) {
		this.inventory_code = inventory_code;
	}
	public String getInventory_name() {
		return inventory_name;
	}
	public void setInventory_name(String inventory_name) {
		this.inventory_name = inventory_name;
	}
	public String getOffer_comment() {
		return offer_comment;
	}
	public void setOffer_comment(String offer_comment) {
		this.offer_comment = offer_comment;
	}
	public String getActivitycode() {
		return activitycode;
	}
	public void setActivitycode(String activitycode) {
		this.activitycode = activitycode;
	}
	public String getResourcestypeid() {
		return resourcestypeid;
	}
	public void setResourcestypeid(String resourcestypeid) {
		this.resourcestypeid = resourcestypeid;
	}
	public List<MallActivity_List> getActivity_list() {
		return activity_list;
	}
	public void setActivity_list(List<MallActivity_List> activity_list) {
		this.activity_list = activity_list;
	}
	public List<MallOffer_Param> getOffer_param() {
		return offer_param;
	}
	public void setOffer_param(List<MallOffer_Param> offer_param) {
		this.offer_param = offer_param;
	}
	public String getPackage_sale() {
		return package_sale;
	}
	public void setPackage_sale(String package_sale) {
		this.package_sale = package_sale;
	}
	public String getChoose_active() {
		return choose_active;
	}
	public void setChoose_active(String choose_active) {
		this.choose_active = choose_active;
	}
	
	public String getCerti_sex(){
		return certi_sex;
	}
	public void setCerti_sex(String certi_sex){
		this.certi_sex = certi_sex;
	}
	public String getCust_type() {
		return cust_type;
	}
	public void setCust_type(String cust_type) {
		this.cust_type = cust_type;
	}
	public List<MallCoupons_List> getCoupons_list() {
		return coupons_list;
	}
	public void setCoupons_list(List<MallCoupons_List> coupons_list) {
		this.coupons_list = coupons_list;
	}
	public String getOld_Out_plan_name_ess() {
		return old_Out_plan_name_ess;
	}
	public void setOld_Out_plan_name_ess(String old_Out_plan_name_ess) {
		this.old_Out_plan_name_ess = old_Out_plan_name_ess;
	}
	public String getCerti_nation() {
		return certi_nation;
	}
	public void setCerti_nation(String certi_nation) {
		this.certi_nation = certi_nation;
	}
	public String getCerti_issuer() {
		return certi_issuer;
	}
	public void setCerti_issuer(String certi_issuer) {
		this.certi_issuer = certi_issuer;
	}
	public String getCerti_eff_date() {
		return certi_eff_date;
	}
	public void setCerti_eff_date(String certi_eff_date) {
		this.certi_eff_date = certi_eff_date;
	}
	public String getRealname_flag() {
		return realname_flag;
	}
	public void setRealname_flag(String realname_flag) {
		this.realname_flag = realname_flag;
	}
	public List<ZhwqInfoList> getZhwq_info() {
		return zhwq_info;
	}
	public void setZhwq_info(List<ZhwqInfoList> zhwq_info) {
		this.zhwq_info = zhwq_info;
	}
	public String getCert_num() {
		return cert_num;
	}
	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
	}
	public String getCert_addr() {
		return cert_addr;
	}
	public void setCert_addr(String cert_addr) {
		this.cert_addr = cert_addr;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getCert_effected_date() {
		return cert_effected_date;
	}
	public void setCert_effected_date(String cert_effected_date) {
		this.cert_effected_date = cert_effected_date;
	}
	public String getCert_sex() {
		return cert_sex;
	}
	public void setCert_sex(String cert_sex) {
		this.cert_sex = cert_sex;
	}
	public String getCert_nation() {
		return cert_nation;
	}
	public void setCert_nation(String cert_nation) {
		this.cert_nation = cert_nation;
	}
	public String getCust_birthday() {
		return cust_birthday;
	}
	public void setCust_birthday(String cust_birthday) {
		this.cust_birthday = cust_birthday;
	}
	public String getCert_expire_date() {
		return cert_expire_date;
	}
	public void setCert_expire_date(String cert_expire_date) {
		this.cert_expire_date = cert_expire_date;
	}
    public String getCert_num2() {
        return cert_num2;
    }
    public void setCert_num2(String cert_num2) {
        this.cert_num2 = cert_num2;
    }
	
}
