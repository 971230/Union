package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;

/**
 * 云卡调拨实体
 * 
 * @author wui
 */
public class Cloud implements java.io.Serializable { //STATUS_DATE
	private String cloud_id;
	private String crm_order_id;
	private String offer_name;
	private String taochan_name;
	private String phone_num;
	private String uim;
	private String batch_id;
	private String batch_oper_id;
	private String order_id;
	private String state;
	private String create_date;
	private String to_userid;
	private String from_userid;
	private String state_name;
	private String sec_order_id;
	
	private String ship_state;
	
	
	//private IDaoSupport daoSupport =  (IDaoSupport)SpringContextHolder.getBean("daoSupport");
	

	
	//CRM销售品id
	private String offer_id;
	private String deal_time;
	
	private Double pay_money; //批开卡支付金额
	
	private String lan_id;
	private String lan_name;
	private String username;
	private String state_date;
	private String first_userid; //冗余字段、统计使用，追踪一级分销商来源
	private String first_orderid; //冗余字段、统计使用，记录一级分销商订单编号
	
	private String acc_type; //号码类型
	private String begin_nbr;//开始号码
	private String end_nbr;//结束号码
	private String p_apply_show_parent_stock; //可申请库存
	
	private String goods_id;
	
	
	public String getCloud_id() {
		return cloud_id;
	}
	public void setCloud_id(String cloud_id) {
		this.cloud_id = cloud_id;
	}
	public String getCrm_order_id() {
		return crm_order_id;
	}
	public void setCrm_order_id(String crm_order_id) {
		this.crm_order_id = crm_order_id;
	}
	public String getOffer_name() {
		return offer_name;
	}
	public void setOffer_name(String offer_name) {
		this.offer_name = offer_name;
	}
	public String getTaochan_name() {
		return taochan_name;
	}
	public void setTaochan_name(String taochan_name) {
		this.taochan_name = taochan_name;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getUim() {
		return uim;
	}
	public void setUim(String uim) {
		this.uim = uim;
	}
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public String getBatch_oper_id() {
		return batch_oper_id;
	}
	public void setBatch_oper_id(String batch_oper_id) {
		this.batch_oper_id = batch_oper_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getTo_userid() {
		return to_userid;
	}
	public void setTo_userid(String to_userid) {
		this.to_userid = to_userid;
	}
	public String getFrom_userid() {
		return from_userid;
	}
	public void setFrom_userid(String from_userid) {
		this.from_userid = from_userid;
	}
	
	@NotDbField
	public String getState_name() {
		String stateName = "";
		if(state.equals("0")){
			stateName = "可用";
		}else if(state.equals("1")){
			stateName = "预占";
		}else if(state.equals("2")){
			stateName = "已用";
		}else if(state.equals("3")){
			stateName = "失效";
		}
		return stateName;
	}
	
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public String getSec_order_id() {
		return sec_order_id;
	}
	public void setSec_order_id(String sec_order_id) {
		this.sec_order_id = sec_order_id;
	}
	public String getDeal_time() {
		return deal_time;
	}
	public void setDeal_time(String deal_time) {
		this.deal_time = deal_time;
	}
	public Double getPay_money() {
		return pay_money;
	}
	public void setPay_money(Double pay_money) {
		this.pay_money = pay_money;
	}
	public String getLan_id() {
		return lan_id;
	}
	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}
	public String getLan_name() {
		/*String sql = "select lan_id, lan_name from es_lan ";
		List list = daoSupport.queryForList(sql);
		Map lanMap = new HashedMap();
		if(null != list && !list.isEmpty()){
			for(int i = 0; i < list.size(); i++){
				lanMap = (Map)list.get(i);
				if(lan_id.equals(lanMap.get("lan_id").toString())){
					return lanMap.get("lan_name").toString();
				}
			}
		}*/
		return lan_name;
	}
	public void setLan_name(String lan_name) {
		this.lan_name = lan_name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getState_date() {
		return state_date;
	}
	public void setState_date(String state_date) {
		this.state_date = state_date;
	}
	public String getOffer_id() {
		return offer_id;
	}
	public void setOffer_id(String offer_id) {
		this.offer_id = offer_id;
	}
	public String getShip_state() {
		return ship_state;
	}
	public void setShip_state(String ship_state) {
		this.ship_state = ship_state;
	}
	public String getFirst_userid() {
		return first_userid;
	}
	public void setFirst_userid(String first_userid) {
		this.first_userid = first_userid;
	}
	public String getFirst_orderid() {
		return first_orderid;
	}
	public void setFirst_orderid(String first_orderid) {
		this.first_orderid = first_orderid;
	}
	
	@NotDbField
	public String getAcc_type() {
		return acc_type;
	}
	@NotDbField
	public void setAcc_type(String acc_type) {
		this.acc_type = acc_type;
	}
	@NotDbField
	public String getBegin_nbr() {
		return begin_nbr;
	}
	@NotDbField
	public void setBegin_nbr(String begin_nbr) {
		this.begin_nbr = begin_nbr;
	}
	@NotDbField
	public String getEnd_nbr() {
		return end_nbr;
	}
	@NotDbField
	public void setEnd_nbr(String end_nbr) {
		this.end_nbr = end_nbr;
	}
	@NotDbField
	public String getP_apply_show_parent_stock() {
		return p_apply_show_parent_stock;
	}
	@NotDbField
	public void setP_apply_show_parent_stock(String p_apply_show_parent_stock) {
		this.p_apply_show_parent_stock = p_apply_show_parent_stock;
	}
	@NotDbField
	public String getGoods_id() {
		return goods_id;
	}
	@NotDbField
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	
	
	
}