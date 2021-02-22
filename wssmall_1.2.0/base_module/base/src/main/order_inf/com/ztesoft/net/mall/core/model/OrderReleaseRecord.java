package com.ztesoft.net.mall.core.model;

/**
 * 终端串号/号码释放记录实体
 * @author Rapon
 */
public class OrderReleaseRecord implements java.io.Serializable {

	private String	release_id;//主键
	private String	order_id;//订单号
	private String	type;//终端/号码,参考es_dc_public_ext字典组product_type
	private String	serial_or_num;//手机终端串号/号码
	private String	occupied_essid;//预占工号
	private String	prokey;//预占关键字
	private String	deal_desc;//释放描述
	private String	create_time;//创建时间
	private String	is_deal;//处理方式:0未处理 1线下处理 2接口处理
	private String	deal_username;//处理人es_adminuser.username
	private String	deal_time;//处理人处理时间
	private String	source_from;//系统来源
	private String	first_req_json;//首次请求报文
	public String getRelease_id() {
		return release_id;
	}
	public void setRelease_id(String release_id) {
		this.release_id = release_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSerial_or_num() {
		return serial_or_num;
	}
	public void setSerial_or_num(String serial_or_num) {
		this.serial_or_num = serial_or_num;
	}
	public String getOccupied_essid() {
		return occupied_essid;
	}
	public void setOccupied_essid(String occupied_essid) {
		this.occupied_essid = occupied_essid;
	}
	public String getProkey() {
		return prokey;
	}
	public void setProkey(String prokey) {
		this.prokey = prokey;
	}
	public String getDeal_desc() {
		return deal_desc;
	}
	public void setDeal_desc(String deal_desc) {
		this.deal_desc = deal_desc;
	}
	public String getFirst_req_json() {
		return first_req_json;
	}
	public void setFirst_req_json(String first_req_json) {
		this.first_req_json = first_req_json;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getIs_deal() {
		return is_deal;
	}
	public void setIs_deal(String is_deal) {
		this.is_deal = is_deal;
	}
	public String getDeal_username() {
		return deal_username;
	}
	public void setDeal_username(String deal_username) {
		this.deal_username = deal_username;
	}
	public String getDeal_time() {
		return deal_time;
	}
	public void setDeal_time(String deal_time) {
		this.deal_time = deal_time;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}	
}