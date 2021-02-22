package params.orderouter.req;

import java.util.List;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.model.AdjunctItem;

import params.ZteRequest;

public class OrderOuterReq extends ZteRequest {
	private String temp_inst_id; //必填
	private String order_type; //必填
	private String lan_id;
	private String acc_nbr;
	private String goods_num;
	private String certi_type;
	private String shop_id; //必填
	private String cust_name;
	private String certi_number;
	private String comments;
	private String order_amount;
	private String remark;
	private String ship_addr;
	private String ship_day;
	private String ship_mobile;
	private String ship_name;
	private String terminal_code;
	private String old_sec_order_id;
	private String sec_order_id;
	private String source_from; //必填
	private String uname;
	private String name;
	private String sex;
	private String payment_id;
	private String shipping_id;
	private String product_id;
	private String userid;
	private String price;
	private String member_lv_id;
	private String goods_name;
	
	private List<AdjunctItem> adjunctList;
	
	@NotDbField
	public List<AdjunctItem> getAdjunctList() {
		return adjunctList;
	}

	public void setAdjunctList(List<AdjunctItem> adjunctList) {
		this.adjunctList = adjunctList;
	}
	
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getMember_lv_id() {
		return member_lv_id;
	}
	public void setMember_lv_id(String member_lv_id) {
		this.member_lv_id = member_lv_id;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getShipping_id() {
		return shipping_id;
	}
	public void setShipping_id(String shipping_id) {
		this.shipping_id = shipping_id;
	}
	public String getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getTemp_inst_id() {
		return temp_inst_id;
	}
	public void setTemp_inst_id(String temp_inst_id) {
		this.temp_inst_id = temp_inst_id;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getLan_id() {
		return lan_id;
	}
	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}
	public String getAcc_nbr() {
		return acc_nbr;
	}
	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}
	public String getGoods_num() {
		return goods_num;
	}
	public void setGoods_num(String goods_num) {
		this.goods_num = goods_num;
	}
	public String getCerti_type() {
		return certi_type;
	}
	public void setCerti_type(String certi_type) {
		this.certi_type = certi_type;
	}
	public String getShop_id() {
		return shop_id;
	}
	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getCerti_number() {
		return certi_number;
	}
	public void setCerti_number(String certi_number) {
		this.certi_number = certi_number;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getShip_addr() {
		return ship_addr;
	}
	public void setShip_addr(String ship_addr) {
		this.ship_addr = ship_addr;
	}
	public String getShip_day() {
		return ship_day;
	}
	public void setShip_day(String ship_day) {
		this.ship_day = ship_day;
	}
	public String getShip_mobile() {
		return ship_mobile;
	}
	public void setShip_mobile(String ship_mobile) {
		this.ship_mobile = ship_mobile;
	}
	public String getShip_name() {
		return ship_name;
	}
	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}
	public String getTerminal_code() {
		return terminal_code;
	}
	public void setTerminal_code(String terminal_code) {
		this.terminal_code = terminal_code;
	}
	public String getOld_sec_order_id() {
		return old_sec_order_id;
	}
	public void setOld_sec_order_id(String old_sec_order_id) {
		this.old_sec_order_id = old_sec_order_id;
	}
	public String getSec_order_id() {
		return sec_order_id;
	}
	public void setSec_order_id(String sec_order_id) {
		this.sec_order_id = sec_order_id;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
}
