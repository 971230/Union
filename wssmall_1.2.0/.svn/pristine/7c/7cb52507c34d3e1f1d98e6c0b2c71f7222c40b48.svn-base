package com.ztesoft.net.mall.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.model.AttrInst;
import com.ztesoft.net.model.WarehousePurorder;

/**
 * 外系统订单
 * 
 * @author wui
 */
public class OrderOuter  extends ZteRequest<ZteResponse> implements java.io.Serializable {
	private String order_id;
	
	
	private String ship_name;
	private String ship_addr;
	private String ship_mobile;
	private String ship_day;

	
	private String remark;
	private String source_from;
	private String accept_time;

	
	private String cust_name;
	private String certi_type;
	private String certi_number;

	private String comments;
	
	
	private String goods_id;
	private String goods_num;
	private String goods_name;
	
	private String order_amount;
	private String create_time;
	private String status;
	
	private String offer_name;
	private String acc_nbr;
	private String terminal_code;
	private String terminal_name;
	private String lan_id;
	
	private String col1;
	private String col2;
	
	
	
	private String batch_id;
	
	private String import_userid;	//导入人id
	private String userid;
	
	private String order_type;
	private String old_sec_order_id;//外系统原订单编号（换货、退货、退费）此字段必填
	private String sec_order_id;//外系统订单(淘宝订单、京东订单)
	
	private String ext_cols;//扩展属性，以json格式处理
	
	private String import_state;
	private String iccid;//UIM卡号
	private String o_source_from;//外系统订单来源

	//购买者信息
	private String name;
	private String uname;
	private Integer sex;
	private String payment_id;
	private String shipping_id;
	private String product_id;
	
	private String member_lv_id;
	private String price;
	private Integer item_type=0;//0商品 1配件 2赠品
	
	private String addon;//配件信息
	
	
	private String order_channel;
	private String order_from;
	private String merge_status;
	private String plat_type;
	private String pro_totalfee;
	private String tid_time;
	private String spread_name;
	private String spread_code;
	private String spread_phone;
	private String development_code;
	private String development_name;
	private String wm_isreservation_from;
	private String is_adv_sale;
	
	private String order_attrs;
	private String goods_attrs;
	private String spec_id; //团购、秒杀活动id

	//ding.xiaotao 属性处理器属性
	private String pay_method;//es_payment_logs 支付方法
	
	
	
	//////////////////add by wui 需要扩充的字段
	
	//会员地址信心
	private String member_id;
	private String address_id;
	private String dlyaddressid;
	private String city;
	private long city_id;
	private long province_id;
	private String province;
	private long region_id;
	private String region;
	private String ship_zip;
	private String ship_tel;
	
	//发票信息
	private Integer invoice_content;  //发票内容：1明细；2办公用品；3电脑配件；4耗材
	private Integer invoice_title;  //发票抬头类型：1个人；2单位
	private String invoice_title_desc;  //发票抬头描述
	private Integer invoice_type;  //发票类型：1-普通发票；2-增值发票
	
	private String ship_amount;
	private String create_type;
	private String service_code; //受理规则服务编码
	private String app_key; //接口app_key
	private String lan_code;
	
	//推荐信息
	private String service_id;
	private String service_type;
	private String spread_member_id;
	
	private String o_pay_status;
	private String o_order_status;
	private String p_order_amount;
	private String pay_status;
	private String house_id;//发货创库ID
	private String org_id;//发货组积ID
	
	private WarehousePurorder warehousePurorder;
	private List<AttrInst> outerAttrInsts = new ArrayList<AttrInst>();
	private List<Map> paramsl;
	private Coupons coupon;//优惠券
	private String coupon_code;//优惠券编码

	public String getPay_method() {
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	@NotDbField
	public String getCoupon_code() {
		return coupon_code;
	}

	public void setCoupon_code(String coupon_code) {
		this.coupon_code = coupon_code;
	}

	@NotDbField
	public Coupons getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupons coupon) {
		this.coupon = coupon;
	}

	@NotDbField
	public List<Map> getParamsl() {
		return paramsl;
	}

	public void setParamsl(List<Map> paramsl) {
		this.paramsl = paramsl;
	}

	@NotDbField
	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	@NotDbField
	public String getHouse_id() {
		return house_id;
	}

	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}

	public String getPay_status() {
		return pay_status;
	}

	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}

	public void setO_pay_status(String o_pay_status) {
		this.o_pay_status = o_pay_status;
	}

	public String getO_order_status() {
		return o_order_status;
	}

	public void setO_order_status(String o_order_status) {
		this.o_order_status = o_order_status;
	}

	public String getOrder_channel() {
		return order_channel;
	}

	public void setOrder_channel(String order_channel) {
		this.order_channel = order_channel;
	}

	public String getOrder_from() {
		return order_from;
	}

	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}

	public String getMerge_status() {
		return merge_status;
	}

	public void setMerge_status(String merge_status) {
		this.merge_status = merge_status;
	}

	public String getPlat_type() {
		return plat_type;
	}

	public void setPlat_type(String plat_type) {
		this.plat_type = plat_type;
	}

	public String getPro_totalfee() {
		return pro_totalfee;
	}

	public void setPro_totalfee(String pro_totalfee) {
		this.pro_totalfee = pro_totalfee;
	}

	public String getTid_time() {
		return tid_time;
	}

	public void setTid_time(String tid_time) {
		this.tid_time = tid_time;
	}

	public String getSpread_name() {
		return spread_name;
	}

	public void setSpread_name(String spread_name) {
		this.spread_name = spread_name;
	}

	public String getSpread_code() {
		return spread_code;
	}

	public void setSpread_code(String spread_code) {
		this.spread_code = spread_code;
	}

	public String getSpread_phone() {
		return spread_phone;
	}

	public void setSpread_phone(String spread_phone) {
		this.spread_phone = spread_phone;
	}

	public String getDevelopment_code() {
		return development_code;
	}

	public void setDevelopment_code(String development_code) {
		this.development_code = development_code;
	}

	public String getDevelopment_name() {
		return development_name;
	}

	public void setDevelopment_name(String development_name) {
		this.development_name = development_name;
	}

	public String getWm_isreservation_from() {
		return wm_isreservation_from;
	}

	public void setWm_isreservation_from(String wm_isreservation_from) {
		this.wm_isreservation_from = wm_isreservation_from;
	}

	public String getIs_adv_sale() {
		return is_adv_sale;
	}

	public void setIs_adv_sale(String is_adv_sale) {
		this.is_adv_sale = is_adv_sale;
	}
	@NotDbField
	public String getOrder_attrs() {
		return order_attrs;
	}
	@NotDbField
	public void setOrder_attrs(String order_attrs) {
		this.order_attrs = order_attrs;
	}
	@NotDbField
	public String getGoods_attrs() {
		return goods_attrs;
	}
	@NotDbField
	public void setGoods_attrs(String goods_attrs) {
		this.goods_attrs = goods_attrs;
	}

	public String getAddon() {
		return addon;
	}

	public void setAddon(String addon) {
		this.addon = addon;
	}

	@NotDbField
	public Integer getItem_type() {
		return item_type;
	}

	public void setItem_type(Integer item_type) {
		this.item_type = item_type;
	}

	@NotDbField
	public String getMember_lv_id() {
		return member_lv_id;
	}

	public void setMember_lv_id(String member_lv_id) {
		this.member_lv_id = member_lv_id;
	}

	@NotDbField
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getShip_name() {
		return ship_name;
	}

	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}

	public String getShip_addr() {
		return ship_addr;
	}

	public void setShip_addr(String ship_addr) {
		this.ship_addr = ship_addr;
	}

	public String getShip_mobile() {
		return ship_mobile;
	}

	public void setShip_mobile(String ship_mobile) {
		this.ship_mobile = ship_mobile;
	}

	public String getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}

	public String getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(String goods_num) {
		this.goods_num = goods_num;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getAccept_time() {
		return accept_time;
	}

	public void setAccept_time(String accept_time) {
		this.accept_time = accept_time;
	}

	public String getOffer_name() {
		return offer_name;
	}

	public void setOffer_name(String offer_name) {
		this.offer_name = offer_name;
	}

	public String getAcc_nbr() {
		return acc_nbr;
	}

	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getCerti_type() {
		return certi_type;
	}

	public void setCerti_type(String certi_type) {
		this.certi_type = certi_type;
	}

	public String getCerti_number() {
		return certi_number;
	}

	public void setCerti_number(String certi_number) {
		this.certi_number = certi_number;
	}

	public String getTerminal_code() {
		return terminal_code;
	}

	public void setTerminal_code(String terminal_code) {
		this.terminal_code = terminal_code;
	}

	public String getTerminal_name() {
		return terminal_name;
	}

	public void setTerminal_name(String terminal_name) {
		this.terminal_name = terminal_name;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
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

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@NotDbField
	public String getGoods_name() {
		return goods_name;
	}
	@NotDbField
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getShip_day() {
		return ship_day;
	}

	public void setShip_day(String ship_day) {
		this.ship_day = ship_day;
	}

	public String getLan_id() { 
		return lan_id;
	}

	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getSec_order_id() {
		return sec_order_id;
	}

	public void setSec_order_id(String sec_order_id) {
		this.sec_order_id = sec_order_id;
	}

	public String getOld_sec_order_id() {
		return old_sec_order_id;
	}

	public void setOld_sec_order_id(String old_sec_order_id) {
		this.old_sec_order_id = old_sec_order_id;
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}

	public String getExt_cols() {
		return ext_cols;
	}

	public void setExt_cols(String ext_cols) {
		this.ext_cols = ext_cols;
	}

	public String getImport_userid() {
		return import_userid;
	}

	public void setImport_userid(String import_userid) {
		this.import_userid = import_userid;
	}

	public String getImport_state() {
		return import_state;
	}

	public void setImport_state(String import_state) {
		this.import_state = import_state;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getO_source_from() {
		return o_source_from;
	}

	public void setO_source_from(String o_source_from) {
		this.o_source_from = o_source_from;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}

	public String getShipping_id() {
		return shipping_id;
	}

	public void setShipping_id(String shipping_id) {
		this.shipping_id = shipping_id;
	}

	public String getCreate_type() {
		return create_type;
	}
	public void setCreate_type(String create_type) {
		this.create_type = create_type;
	}
	public String getService_code() {
		return service_code;
	}
	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	public String getService_id() {
		return service_id;
	}
	public void setService_id(String service_id) {
		this.service_id = service_id;
	}
	public String getService_type() {
		return service_type;
	}
	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	public String getSpread_member_id() {
		return spread_member_id;
	}
	public void setSpread_member_id(String spread_member_id) {
		this.spread_member_id = spread_member_id;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public long getCity_id() {
		return city_id;
	}
	public void setCity_id(long city_id) {
		this.city_id = city_id;
	}
	public long getProvince_id() {
		return province_id;
	}
	public void setProvince_id(long province_id) {
		this.province_id = province_id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public long getRegion_id() {
		return region_id;
	}
	public void setRegion_id(long region_id) {
		this.region_id = region_id;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getShip_zip() {
		return ship_zip;
	}
	public void setShip_zip(String ship_zip) {
		this.ship_zip = ship_zip;
	}
	public String getApp_key() {
		return app_key;
	}
	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}
	public String getShip_amount() {
		return ship_amount;
	}
	public void setShip_amount(String ship_amount) {
		this.ship_amount = ship_amount;
	}
	@NotDbField
	public List<AttrInst> getOuterAttrInsts() {
		return outerAttrInsts;
	}
	public void setOuterAttrInsts(List<AttrInst> outerAttrInsts) {
		this.outerAttrInsts = outerAttrInsts;
	}
	
	public String getInvoice_title_desc() {
		return invoice_title_desc;
	}
	public void setInvoice_title_desc(String invoice_title_desc) {
		this.invoice_title_desc = invoice_title_desc;
	}
	
	public String getDlyaddressid() {
		return dlyaddressid;
	}
	public void setDlyaddressid(String dlyaddressid) {
		this.dlyaddressid = dlyaddressid;
	}
	public String getLan_code() {
		return lan_code;
	}
	public void setLan_code(String lan_code) {
		this.lan_code = lan_code;
	}
	public Integer getInvoice_content() {
		return invoice_content;
	}
	public void setInvoice_content(Integer invoice_content) {
		this.invoice_content = invoice_content;
	}
	public Integer getInvoice_title() {
		return invoice_title;
	}
	public void setInvoice_title(Integer invoice_title) {
		this.invoice_title = invoice_title;
	}
	public Integer getInvoice_type() {
		return invoice_type;
	}
	public void setInvoice_type(Integer invoice_type) {
		this.invoice_type = invoice_type;
	}
	public String getAddress_id() {
		return address_id;
	}
	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}

	public String getO_pay_status() {
		return o_pay_status;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	public String getShip_tel() {
		return ship_tel;
	}
	public void setShip_tel(String ship_tel) {
		this.ship_tel = ship_tel;
	}
	@NotDbField
	public WarehousePurorder getWarehousePurorder() {
		return warehousePurorder;
	}
	@NotDbField
	public void setWarehousePurorder(WarehousePurorder warehousePurorder) {
		this.warehousePurorder = warehousePurorder;
	}
	
	@NotDbField
	public String getP_order_amount() {
		return p_order_amount;
	}
	@NotDbField
	public void setP_order_amount(String p_order_amount) {
		this.p_order_amount = p_order_amount;
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
	@NotDbField
	public String getSpec_id() {
		return spec_id;
	}
	@NotDbField
	public void setSpec_id(String spec_id) {
		this.spec_id = spec_id;
	}
	
}