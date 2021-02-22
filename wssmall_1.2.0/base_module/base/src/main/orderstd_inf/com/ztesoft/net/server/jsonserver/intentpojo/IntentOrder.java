package com.ztesoft.net.server.jsonserver.intentpojo;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 意向单接单接口 wssmall.intent.order.create
 * 
 * @author song.qi
 *
 */
public class IntentOrder {

	@ZteSoftCommentAnnotationParam(name = "发起方系统标识", type = "String", isNecessary = "Y", desc = "发起方系统标识")
	private String source_system;
	@ZteSoftCommentAnnotationParam(name = "发起方系统标识小类", type = "String", isNecessary = "Y", desc = "发起方系统标识小类")
	private String source_system_type;
	@ZteSoftCommentAnnotationParam(name = "商品信息节点", type = "String", isNecessary = "Y", desc = "商品信息节点")
	private List<IntentOrderGoodsInfo> goods_info;
	@ZteSoftCommentAnnotationParam(name = "人员信息节点", type = "String", isNecessary = "Y", desc = "人员信息节点")
	private IntentOrderDeveloperInfo developer_info;
	@ZteSoftCommentAnnotationParam(name = "客户信息节点", type = "String", isNecessary = "Y", desc = "客户信息节点")
	private IntentOrderCustInfo cust_info;
	@ZteSoftCommentAnnotationParam(name = "客户信息节点", type = "String", isNecessary = "Y", desc = "客户信息节点")
	private IntentOrderPhoneInfo phone_info;
	@ZteSoftCommentAnnotationParam(name = "联系人信息节点", type = "String", isNecessary = "Y", desc = "联系人信息节点")
	private IntentOrderContactInfo contact_info;
	@ZteSoftCommentAnnotationParam(name = "意向单号", type = "String", isNecessary = "Y", desc = "意向单号")
	private String intent_order_id;
	@ZteSoftCommentAnnotationParam(name = "订单归属省份", type = "String", isNecessary = "N", desc = "订单归属省份")
	private String order_province_code;
	@ZteSoftCommentAnnotationParam(name = "订单归属地市", type = "String", isNecessary = "N", desc = "订单归属地市")
	private String order_city_code;
	@ZteSoftCommentAnnotationParam(name = "订单归属县分", type = "String", isNecessary = "N", desc = "订单归属县分")
	private String order_county_code;
	@ZteSoftCommentAnnotationParam(name = "备注", type = "String", isNecessary = "N", desc = "备注")
	private String remark;
	@ZteSoftCommentAnnotationParam(name = "自传播营销id", type = "String", isNecessary = "N", desc = "自传播营销id")
	private String market_user_id;
	@ZteSoftCommentAnnotationParam(name = "种子用户id", type = "String", isNecessary = "N", desc = "种子用户id")
	private String Seed_user_id;
	@ZteSoftCommentAnnotationParam(name = "种子用户号码", type = "String", isNecessary = "N", desc = "种子用户号码")
	private String share_svc_num;
	@ZteSoftCommentAnnotationParam(name = "不可修改字段", type = "String", isNecessary = "N", desc = "不可修改字段")
	private String is_no_modify;
	
	@ZteSoftCommentAnnotationParam(name = "自传播动名称", type = "String", isNecessary = "N", desc = "自定义活动名称")
	private String activity_name;
	@ZteSoftCommentAnnotationParam(name = "顶级种子用户id", type = "String", isNecessary = "N", desc = "顶级种子用户id")
	private String top_share_userid;//同步沃创富时对应的引流受益人ID
	@ZteSoftCommentAnnotationParam(name = "顶级种子用户号码", type = "String", isNecessary = "N", desc = "顶级种子用户号码")
	private String top_share_num;//同步沃创富时对应的引流受益人号码
	@ZteSoftCommentAnnotationParam(name = "首月付费模式", type = "String", isNecessary = "N", desc = "首月付费模式")
	private String offer_eff_type;//枚举值：	CBSS：	01：标准资费（免首月月租）	02：全月套餐	03：套餐减半	04：首月按天
	@ZteSoftCommentAnnotationParam(name = "订单类型", type = "String", isNecessary = "N", desc = "订单类型")
	private String order_type;

	@ZteSoftCommentAnnotationParam(name = "顶级种子专业线", type = "String", isNecessary = "N", desc = "顶级种子专业线")
	private String top_seed_professional_line;//枚举值：	1（公众），2（客服），3（集客）
	@ZteSoftCommentAnnotationParam(name = "顶级种子类型", type = "String", isNecessary = "N", desc = "顶级种子类型")
	private String top_seed_type;//枚举值：	1（豌豆荚）,2（满天星）
	@ZteSoftCommentAnnotationParam(name = "顶级种子分组", type = "String", isNecessary = "N", desc = "顶级种子分组")
	private String top_seed_group_id;//


	public String getSource_system() {
		return source_system;
	}

	public void setSource_system(String source_system) {
		this.source_system = source_system;
	}

	public String getSource_system_type() {
		return source_system_type;
	}

	public void setSource_system_type(String source_system_type) {
		this.source_system_type = source_system_type;
	}

	public List<IntentOrderGoodsInfo> getGoods_info() {
		return goods_info;
	}

	public void setGoods_info(List<IntentOrderGoodsInfo> goods_info) {
		this.goods_info = goods_info;
	}

	public IntentOrderDeveloperInfo getDeveloper_info() {
		return developer_info;
	}

	public void setDeveloper_info(IntentOrderDeveloperInfo developer_info) {
		this.developer_info = developer_info;
	}

	public IntentOrderCustInfo getCust_info() {
		return cust_info;
	}

	public void setCust_info(IntentOrderCustInfo cust_info) {
		this.cust_info = cust_info;
	}

	public IntentOrderPhoneInfo getPhone_info() {
		return phone_info;
	}

	public void setPhone_info(IntentOrderPhoneInfo phone_info) {
		this.phone_info = phone_info;
	}

	public IntentOrderContactInfo getContact_info() {
		return contact_info;
	}

	public void setContact_info(IntentOrderContactInfo contact_info) {
		this.contact_info = contact_info;
	}

	public String getIntent_order_id() {
		return intent_order_id;
	}

	public void setIntent_order_id(String intent_order_id) {
		this.intent_order_id = intent_order_id;
	}

	public String getOrder_province_code() {
		return order_province_code;
	}

	public void setOrder_province_code(String order_province_code) {
		this.order_province_code = order_province_code;
	}

	public String getOrder_city_code() {
		return order_city_code;
	}

	public void setOrder_city_code(String order_city_code) {
		this.order_city_code = order_city_code;
	}

	public String getOrder_county_code() {
		return order_county_code;
	}

	public void setOrder_county_code(String order_county_code) {
		this.order_county_code = order_county_code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMarket_user_id() {
		return market_user_id;
	}

	public void setMarket_user_id(String market_user_id) {
		this.market_user_id = market_user_id;
	}

	public String getSeed_user_id() {
		return Seed_user_id;
	}

	public void setSeed_user_id(String seed_user_id) {
		Seed_user_id = seed_user_id;
	}

	public String getShare_svc_num() {
		return share_svc_num;
	}

	public void setShare_svc_num(String share_svc_num) {
		this.share_svc_num = share_svc_num;
	}

	public String getIs_no_modify() {
		return is_no_modify;
	}

	public void setIs_no_modify(String is_no_modify) {
		this.is_no_modify = is_no_modify;
	}

	public String getActivity_name() {
		return activity_name;
	}

	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}

	public String getTop_share_userid() {
		return top_share_userid;
	}

	public void setTop_share_userid(String top_share_userid) {
		this.top_share_userid = top_share_userid;
	}

	public String getTop_share_num() {
		return top_share_num;
	}

	public void setTop_share_num(String top_share_num) {
		this.top_share_num = top_share_num;
	}

	public String getOffer_eff_type() {
		return offer_eff_type;
	}

	public void setOffer_eff_type(String offer_eff_type) {
		this.offer_eff_type = offer_eff_type;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	
	public String getTop_seed_professional_line() {
		return top_seed_professional_line;
	}

	public void setTop_seed_professional_line(String top_seed_professional_line) {
		this.top_seed_professional_line = top_seed_professional_line;
	}

	public String getTop_seed_type() {
		return top_seed_type;
	}

	public void setTop_seed_type(String top_seed_type) {
		this.top_seed_type = top_seed_type;
	}

	public String getTop_seed_group_id() {
		return top_seed_group_id;
	}

	public void setTop_seed_group_id(String top_seed_group_id) {
		this.top_seed_group_id = top_seed_group_id;
	}

}
