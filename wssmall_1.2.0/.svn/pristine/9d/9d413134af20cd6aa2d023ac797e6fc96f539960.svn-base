package com.ztesoft.remote.basic.params.req;

import java.util.Date;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.remote.basic.params.resp.InsertPreOrderResponse;

import params.ZteRequest;

public class InsertPreOrderRequest extends ZteRequest<InsertPreOrderResponse> {

	@ZteSoftCommentAnnotationParam(name = "主订单编号", type = "String", isNecessary = "Y", desc = "主订单编号")
	private String order_id;

	@ZteSoftCommentAnnotationParam(name = "序列", type = "String", isNecessary = "Y", desc = "序列，0的为在用数据")
	private int sequ;

	@ZteSoftCommentAnnotationParam(name = "子订单编号", type = "String", isNecessary = "Y", desc = "子订单编号")
	private String item_id;

	@ZteSoftCommentAnnotationParam(name = "产品id", type = "String", isNecessary = "Y", desc = "产品id(套餐的销售品id)")
	private String product_id;

	@ZteSoftCommentAnnotationParam(name = "状态", type = "String", isNecessary = "Y", desc = "1成功(竣工成功)、2失败(竣工失败)、3待审核、4竣工中(已审核、正式单送EOP)、5 撤单中、6撤单成功")
	private String state;

	@ZteSoftCommentAnnotationParam(name = "创建时间", type = "String", isNecessary = "Y", desc = "创建时间")
	private String create_time;

	@ZteSoftCommentAnnotationParam(name = "实例id", type = "String", isNecessary = "Y", desc = "实例id（主键）")
	private String inst_id;

	@ZteSoftCommentAnnotationParam(name = "产品名称", type = "String", isNecessary = "Y", desc = "产品名称")
	private String product_name;

	@ZteSoftCommentAnnotationParam(name = "服务编码", type = "String", isNecessary = "Y", desc = "服务编码")
	private String service_code;

	@ZteSoftCommentAnnotationParam(name = "登陆工号", type = "String", isNecessary = "Y", desc = "登陆工号")
	private String user_id;

	@ZteSoftCommentAnnotationParam(name = "客户编码", type = "String", isNecessary = "Y", desc = "客户编码")
	private String cust_number;

	@ZteSoftCommentAnnotationParam(name = "客户名称", type = "String", isNecessary = "Y", desc = "客户名称")
	private String cust_name;

	@ZteSoftCommentAnnotationParam(name = "客户证件类型", type = "String", isNecessary = "Y", desc = "客户证件类型")
	private String cert_type;

	@ZteSoftCommentAnnotationParam(name = "客户证件号码", type = "String", isNecessary = "Y", desc = "客户证件号码")
	private String cert_number;

	@ZteSoftCommentAnnotationParam(name = "证件地址", type = "String", isNecessary = "Y", desc = "证件地址")
	private String cert_address;

	@ZteSoftCommentAnnotationParam(name = "客户类型", type = "String", isNecessary = "Y", desc = "客户类型")
	private String cust_type;

	@ZteSoftCommentAnnotationParam(name = "客户通信地址", type = "String", isNecessary = "Y", desc = "客户通信地址")
	private String cust_mail_address;

	@ZteSoftCommentAnnotationParam(name = "通信地址邮政编码", type = "String", isNecessary = "Y", desc = "通信地址邮政编码")
	private String cust_post_code;

	@ZteSoftCommentAnnotationParam(name = "联系人", type = "String", isNecessary = "Y", desc = "联系人")
	private String contact_name;

	@ZteSoftCommentAnnotationParam(name = "联系电话", type = "String", isNecessary = "Y", desc = "联系电话")
	private String contact_phone_num;

	@ZteSoftCommentAnnotationParam(name = "账户编码", type = "String", isNecessary = "Y", desc = "账户编码")
	private String acct_number;

	@ZteSoftCommentAnnotationParam(name = "账户名称", type = "String", isNecessary = "Y", desc = "账户名称")
	private String acct_name;

	@ZteSoftCommentAnnotationParam(name = "付款方式", type = "String", isNecessary = "Y", desc = "付款方式")
	private String pay_method;

	@ZteSoftCommentAnnotationParam(name = "银行开户名", type = "String", isNecessary = "Y", desc = "银行开户名")
	private String pay_name;

	@ZteSoftCommentAnnotationParam(name = "银行ID", type = "String", isNecessary = "Y", desc = "银行ID")
	private String bank_id;

	@ZteSoftCommentAnnotationParam(name = "银行账号", type = "String", isNecessary = "Y", desc = "银行账号")
	private String bank_number;

	@ZteSoftCommentAnnotationParam(name = "账单邮寄地址", type = "String", isNecessary = "Y", desc = "账单邮寄地址")
	private String acct_mail_address;

	@ZteSoftCommentAnnotationParam(name = "账单邮政编码", type = "String", isNecessary = "Y", desc = "账单邮政编码")
	private String acct_post_code;

	@ZteSoftCommentAnnotationParam(name = "可选包销售id", type = "String", isNecessary = "Y", desc = "可选包销售id（中间英文逗号隔开）")
	private String pack_ids;

	@ZteSoftCommentAnnotationParam(name = "功能产品id", type = "String", isNecessary = "Y", desc = "功能产品id（中间英文逗号隔开）")
	private String func_ids;

	@ZteSoftCommentAnnotationParam(name = "产品在销售品中的角色CD", type = "String", isNecessary = "Y", desc = "产品在销售品中的角色CD（暂不使用）")
	private String role_cd;

	@ZteSoftCommentAnnotationParam(name = "号码", type = "String", isNecessary = "Y", desc = "号码")
	private String phone_num;

	@ZteSoftCommentAnnotationParam(name = "营销资源串码", type = "String", isNecessary = "Y", desc = "营销资源串码 配合MKT_RES_TYPE_CD使用  存放UIM卡号")
	private String mkt_resource_code;

	@ZteSoftCommentAnnotationParam(name = "营销资源类型", type = "String", isNecessary = "Y", desc = "营销资源类型")
	private String mkt_res_type_cd;

	@ZteSoftCommentAnnotationParam(name = "所在区域ID", type = "String", isNecessary = "Y", desc = "所在区域ID")
	private String common_region_id;

	@ZteSoftCommentAnnotationParam(name = "系统标识", type = "String", isNecessary = "Y", desc = "系统标识")
	private String source_from;

	@ZteSoftCommentAnnotationParam(name = "用户实际支付金额", type = "String", isNecessary = "Y", desc = "用户实际支付金额")
	private String pay_money;

	@ZteSoftCommentAnnotationParam(name = "业务类型", type = "String", isNecessary = "Y", desc = "业务类型 1:开户,2:钱包充值,3:话费充值,4:套餐变更,5:增值业务变更,6:流量包变更,7:补换卡，8:过户")
	private String order_type;

	@ZteSoftCommentAnnotationParam(name = "缴费状态", type = "String", isNecessary = "Y", desc = "0未缴费 1已缴费")
	private String pay_state;

	@ZteSoftCommentAnnotationParam(name = "订单修改时间", type = "String", isNecessary = "Y", desc = "订单修改时间")
	private String update_time;

	@ZteSoftCommentAnnotationParam(name = "订单修改备注", type = "String", isNecessary = "Y", desc = "订单修改备注")
	private String ord_desc;

	@ZteSoftCommentAnnotationParam(name = "LBS缴费流水号", type = "String", isNecessary = "Y", desc = "LBS缴费流水号")
	private String lbs_ord_no;

	@ZteSoftCommentAnnotationParam(name = "银通缴费流水1", type = "String", isNecessary = "Y", desc = "银通缴费流水1")
	private String yt_ord_no_1;

	@ZteSoftCommentAnnotationParam(name = "银通缴费流水2", type = "String", isNecessary = "Y", desc = "银通缴费流水2")
	private String yt_ord_no_2;

	@ZteSoftCommentAnnotationParam(name = "EOP缴费流水'\' EOP客户订单", type = "String", isNecessary = "Y", desc = "EOP缴费流水'\'EOP客户订单")
	private String eop_ord_no;

	@ZteSoftCommentAnnotationParam(name = "外系统标识", type = "String", isNecessary = "Y", desc = "外系统标识（谁发起请求就写谁）1：银通2：LBS 3：LL转售系统")
	private String sys_id;

	@ZteSoftCommentAnnotationParam(name = "C2C转账接口存放代理商电话号码", type = "String", isNecessary = "Y", desc = "C2C转账接口存放代理商电话号码")
	private String col1;// C2C转账接口存放代理商电话号码

	@ZteSoftCommentAnnotationParam(name = "银通缴费流水(关联我们核心表es_payment_list)", type = "String", isNecessary = "Y", desc = "银通缴费流水(关联我们核心表es_payment_list)")
	private String col2;

	@ZteSoftCommentAnnotationParam(name = "扩展字段", type = "String", isNecessary = "Y", desc = "扩展字段")
	private String col3;

	@ZteSoftCommentAnnotationParam(name = "扩展字段", type = "String", isNecessary = "Y", desc = "扩展字段")
	private String col4;

	@ZteSoftCommentAnnotationParam(name = "扩展字段", type = "String", isNecessary = "Y", desc = "扩展字段")
	private String col5;

	@ZteSoftCommentAnnotationParam(name = "扩展字段", type = "String", isNecessary = "Y", desc = "扩展字段")
	private String col6;

	@ZteSoftCommentAnnotationParam(name = "扩展字段", type = "String", isNecessary = "Y", desc = "扩展字段")
	private String col7;

	@ZteSoftCommentAnnotationParam(name = "扩展字段", type = "String", isNecessary = "Y", desc = "扩展字段")
	private String col8;

	@ZteSoftCommentAnnotationParam(name = "扩展字段", type = "String", isNecessary = "Y", desc = "扩展字段")
	private String col9;

	@ZteSoftCommentAnnotationParam(name = "扩展字段", type = "String", isNecessary = "Y", desc = "扩展字段")
	private String col10;

	@ZteSoftCommentAnnotationParam(name = "银行清算日期", type = "String", isNecessary = "Y", desc = "银行清算日期，格式YYYYMMDD")
	private Date bank_settle_date;

	@ZteSoftCommentAnnotationParam(name = "银行交易金额", type = "String", isNecessary = "Y", desc = "银行交易金额(银行返回)")
	private String bank_pay_money;

	@ZteSoftCommentAnnotationParam(name = "银行支付结果", type = "String", isNecessary = "Y", desc = "银行支付结果，成功SUCCESS")
	private String bank_pay_flag;

	@ZteSoftCommentAnnotationParam(name = "对应用户表(es_ll_access_prod_inst,es_ll_prod_offer_inst,es_ll_func_offer_inst)inst_id", type = "String", isNecessary = "Y", desc = "对应用户表(es_ll_access_prod_inst,es_ll_prod_offer_inst,es_ll_func_offer_inst)inst_id")
	private String local_inst_id;

	@ZteSoftCommentAnnotationParam(name = "卡类型", type = "String", isNecessary = "Y", desc = "卡类型，1：实体卡；2：白卡；3：预开户卡")
	private String card_type;

	@ZteSoftCommentAnnotationParam(name = "图片地址", type = "String", isNecessary = "Y", desc = "图片地址，以逗号相隔（分享计划6张图片上传、代理商开户与网厅预开户身份证照片上传）")
	private String img_addr;

	@ZteSoftCommentAnnotationParam(name = "银通缴费流水", type = "String", isNecessary = "Y", desc = "银通缴费流水(关联我们核心表es_payment_list的流水id)")
	private String es_payment_id;

	@ZteSoftCommentAnnotationParam(name = "请求银通的订单时间（后续校验用）", type = "String", isNecessary = "Y", desc = "请求银通的订单时间（后续校验用）")
	private String ll_pay_time;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return null;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public int getSequ() {
		return sequ;
	}

	public void setSequ(int sequ) {
		this.sequ = sequ;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getInst_id() {
		return inst_id;
	}

	public void setInst_id(String inst_id) {
		this.inst_id = inst_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCust_number() {
		return cust_number;
	}

	public void setCust_number(String cust_number) {
		this.cust_number = cust_number;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getCert_type() {
		return cert_type;
	}

	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}

	public String getCert_number() {
		return cert_number;
	}

	public void setCert_number(String cert_number) {
		this.cert_number = cert_number;
	}

	public String getCert_address() {
		return cert_address;
	}

	public void setCert_address(String cert_address) {
		this.cert_address = cert_address;
	}

	public String getCust_type() {
		return cust_type;
	}

	public void setCust_type(String cust_type) {
		this.cust_type = cust_type;
	}

	public String getCust_mail_address() {
		return cust_mail_address;
	}

	public void setCust_mail_address(String cust_mail_address) {
		this.cust_mail_address = cust_mail_address;
	}

	public String getCust_post_code() {
		return cust_post_code;
	}

	public void setCust_post_code(String cust_post_code) {
		this.cust_post_code = cust_post_code;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getContact_phone_num() {
		return contact_phone_num;
	}

	public void setContact_phone_num(String contact_phone_num) {
		this.contact_phone_num = contact_phone_num;
	}

	public String getAcct_number() {
		return acct_number;
	}

	public void setAcct_number(String acct_number) {
		this.acct_number = acct_number;
	}

	public String getAcct_name() {
		return acct_name;
	}

	public void setAcct_name(String acct_name) {
		this.acct_name = acct_name;
	}

	public String getPay_method() {
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	public String getPay_name() {
		return pay_name;
	}

	public void setPay_name(String pay_name) {
		this.pay_name = pay_name;
	}

	public String getBank_id() {
		return bank_id;
	}

	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}

	public String getBank_number() {
		return bank_number;
	}

	public void setBank_number(String bank_number) {
		this.bank_number = bank_number;
	}

	public String getAcct_mail_address() {
		return acct_mail_address;
	}

	public void setAcct_mail_address(String acct_mail_address) {
		this.acct_mail_address = acct_mail_address;
	}

	public String getAcct_post_code() {
		return acct_post_code;
	}

	public void setAcct_post_code(String acct_post_code) {
		this.acct_post_code = acct_post_code;
	}

	public String getPack_ids() {
		return pack_ids;
	}

	public void setPack_ids(String pack_ids) {
		this.pack_ids = pack_ids;
	}

	public String getFunc_ids() {
		return func_ids;
	}

	public void setFunc_ids(String func_ids) {
		this.func_ids = func_ids;
	}

	public String getRole_cd() {
		return role_cd;
	}

	public void setRole_cd(String role_cd) {
		this.role_cd = role_cd;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getMkt_resource_code() {
		return mkt_resource_code;
	}

	public void setMkt_resource_code(String mkt_resource_code) {
		this.mkt_resource_code = mkt_resource_code;
	}

	public String getMkt_res_type_cd() {
		return mkt_res_type_cd;
	}

	public void setMkt_res_type_cd(String mkt_res_type_cd) {
		this.mkt_res_type_cd = mkt_res_type_cd;
	}

	public String getCommon_region_id() {
		return common_region_id;
	}

	public void setCommon_region_id(String common_region_id) {
		this.common_region_id = common_region_id;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getPay_money() {
		return pay_money;
	}

	public void setPay_money(String pay_money) {
		this.pay_money = pay_money;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getPay_state() {
		return pay_state;
	}

	public void setPay_state(String pay_state) {
		this.pay_state = pay_state;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getOrd_desc() {
		return ord_desc;
	}

	public void setOrd_desc(String ord_desc) {
		this.ord_desc = ord_desc;
	}

	public String getLbs_ord_no() {
		return lbs_ord_no;
	}

	public void setLbs_ord_no(String lbs_ord_no) {
		this.lbs_ord_no = lbs_ord_no;
	}

	public String getYt_ord_no_1() {
		return yt_ord_no_1;
	}

	public void setYt_ord_no_1(String yt_ord_no_1) {
		this.yt_ord_no_1 = yt_ord_no_1;
	}

	public String getYt_ord_no_2() {
		return yt_ord_no_2;
	}

	public void setYt_ord_no_2(String yt_ord_no_2) {
		this.yt_ord_no_2 = yt_ord_no_2;
	}

	public String getEop_ord_no() {
		return eop_ord_no;
	}

	public void setEop_ord_no(String eop_ord_no) {
		this.eop_ord_no = eop_ord_no;
	}

	public String getSys_id() {
		return sys_id;
	}

	public void setSys_id(String sys_id) {
		this.sys_id = sys_id;
	}

	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	public String getCol2() {
		return col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2;
	}

	public String getCol3() {
		return col3;
	}

	public void setCol3(String col3) {
		this.col3 = col3;
	}

	public String getCol4() {
		return col4;
	}

	public void setCol4(String col4) {
		this.col4 = col4;
	}

	public String getCol5() {
		return col5;
	}

	public void setCol5(String col5) {
		this.col5 = col5;
	}

	public String getCol6() {
		return col6;
	}

	public void setCol6(String col6) {
		this.col6 = col6;
	}

	public String getCol7() {
		return col7;
	}

	public void setCol7(String col7) {
		this.col7 = col7;
	}

	public String getCol8() {
		return col8;
	}

	public void setCol8(String col8) {
		this.col8 = col8;
	}

	public String getCol9() {
		return col9;
	}

	public void setCol9(String col9) {
		this.col9 = col9;
	}

	public String getCol10() {
		return col10;
	}

	public void setCol10(String col10) {
		this.col10 = col10;
	}

	public Date getBank_settle_date() {
		return bank_settle_date;
	}

	public void setBank_settle_date(Date bank_settle_date) {
		this.bank_settle_date = bank_settle_date;
	}

	public String getBank_pay_money() {
		return bank_pay_money;
	}

	public void setBank_pay_money(String bank_pay_money) {
		this.bank_pay_money = bank_pay_money;
	}

	public String getBank_pay_flag() {
		return bank_pay_flag;
	}

	public void setBank_pay_flag(String bank_pay_flag) {
		this.bank_pay_flag = bank_pay_flag;
	}

	public String getLocal_inst_id() {
		return local_inst_id;
	}

	public void setLocal_inst_id(String local_inst_id) {
		this.local_inst_id = local_inst_id;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getImg_addr() {
		return img_addr;
	}

	public void setImg_addr(String img_addr) {
		this.img_addr = img_addr;
	}

	public String getEs_payment_id() {
		return es_payment_id;
	}

	public void setEs_payment_id(String es_payment_id) {
		this.es_payment_id = es_payment_id;
	}

	public String getLl_pay_time() {
		return ll_pay_time;
	}

	public void setLl_pay_time(String ll_pay_time) {
		this.ll_pay_time = ll_pay_time;
	}

}
