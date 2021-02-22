package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.Consts;

/**
 * 号码调拨实体
 * 
 * @author wui
 */
public class AccNbr implements java.io.Serializable {
	private String num_id;
	private String num_type;
	private String num_code;
	private String area_code;
	private Integer min_consume;
	private Integer min_month;
	private Integer pre_cash;
	private String cust_name;
	private String id_card_code;
	private String state;
	private String create_date;
	private String state_date;
	private String acc_level;
	private String memo;
	private String comments;
	private String num_flag;
	private String acc_type;
	private String to_userid;
	private String from_userid;
	private String order_id;
	
	private String use_type;
	private String iccid;
	private String imsi;
	private String contact_phone;
	private String contact_addr;
	private String sec_order_id;//淘宝订单
	
	private String area_name;
	private String state_name;
	private String use_name;
	private String deal_time;
	private String first_userid; //冗余字段、统计使用，记录一级分销商卡销售记录
	private String ship_state;
	private String first_orderid; //冗余字段、统计使用，记录一级分销商订单编号
	
	private String p_apply_show_parent_num; //可用上级号码
	private String query_type; //查询号码类型
	
	private String begin_nbr;//开始号码
	private String end_nbr;//结束号码
	
	private String batch_id;	//批次号
	
	private String lan_id;	//归属本地网
	
	private String code_head_name;	//号码号段
	private String choose_date;		//选号时间
	private String modify_date;		//修改时间
	private String take_date;		//占用时间
	private String opr_state_id;	//业务状态
	
	//private IDaoSupport daoSupport =  (IDaoSupport)SpringContextHolder.getBean("daoSupport");
	
	public String getNum_id() {
		return num_id;
	}
	public void setNum_id(String num_id) {
		this.num_id = num_id;
	}
	public String getNum_type() {
		return num_type;
	}
	public void setNum_type(String num_type) {
		this.num_type = num_type;
	}
	public String getNum_code() {
		return num_code;
	}
	public void setNum_code(String num_code) {
		this.num_code = num_code;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public Integer getMin_consume() {
		return min_consume;
	}
	public void setMin_consume(Integer min_consume) {
		this.min_consume = min_consume;
	}
	public Integer getMin_month() {
		return min_month;
	}
	public void setMin_month(Integer min_month) {
		this.min_month = min_month;
	}
	public Integer getPre_cash() {
		return pre_cash;
	}
	public void setPre_cash(Integer pre_cash) {
		this.pre_cash = pre_cash;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getId_card_code() {
		return id_card_code;
	}
	public void setId_card_code(String id_card_code) {
		this.id_card_code = id_card_code;
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
	public String getState_date() {
		return state_date;
	}
	public void setState_date(String state_date) {
		this.state_date = state_date;
	}
	public String getAcc_level() {
		return acc_level;
	}
	public void setAcc_level(String acc_level) {
		this.acc_level = acc_level;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getNum_flag() {
		return num_flag;
	}
	public void setNum_flag(String num_flag) {
		this.num_flag = num_flag;
	}
	public String getAcc_type() {
		return acc_type;
	}
	public void setAcc_type(String acc_type) {
		this.acc_type = acc_type;
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
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getSec_order_id() {
		return sec_order_id;
	}
	public void setSec_order_id(String sec_order_id) {
		this.sec_order_id = sec_order_id;
	}
	public String getUse_type() {
		return use_type;
	}
	public void setUse_type(String use_type) {
		this.use_type = use_type;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getContact_phone() {
		return contact_phone;
	}
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}
	public String getContact_addr() {
		return contact_addr;
	}
	public void setContact_addr(String contact_addr) {
		this.contact_addr = contact_addr;
	}
	
	@NotDbField
	public String getArea_name() {
		/*String sql = "select lan_id, lan_name from es_lan ";
		List list = daoSupport.queryForList(sql);
		Map areaMap = new HashedMap();
		if(null != list && !list.isEmpty()){
			for(int i = 0; i < list.size(); i++){
				areaMap = (Map)list.get(i);
				if(area_code.equals(areaMap.get("lan_id").toString())){
					return areaMap.get("lan_name").toString();
				}
			}
		}*/
		return area_name;
	}
	
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	
	@NotDbField
	public String getState_name() {
		String stateName = "";
		if(state.equals(Consts.ACC_NBR_STATE_0)){
			stateName = "可用";
		}else if(state.equals(Consts.ACC_NBR_STATE_1)){
			stateName = "预占";
		}else if(state.equals(Consts.ACC_NBR_STATE_2)){
			stateName = "已用";
		}else if(state.equals(Consts.ACC_NBR_STATE_3)){
			stateName = "失效";
		}
		return stateName;
	}
	
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	@NotDbField
	public String getUse_name() {
		String useName = "";
		if(use_type.equals("00A")){
			useName = "网厅";
		}else if(use_type.equals("00B")){
			useName = "分销商";
		}
		return useName;
	}
	public void setUse_name(String use_name) {
		this.use_name = use_name;
	}
	
	public String getDeal_time() {
		return deal_time;
	}
	public void setDeal_time(String deal_time) {
		this.deal_time = deal_time;
	}
	public String getFirst_userid() {
		return first_userid;
	}
	public void setFirst_userid(String first_userid) {
		this.first_userid = first_userid;
	}
	public String getShip_state() {
		return ship_state;
	}
	public void setShip_state(String ship_state) {
		this.ship_state = ship_state;
	}
	
	@NotDbField
	public String getP_apply_show_parent_num() {
		return p_apply_show_parent_num;
	}
	@NotDbField
	public void setP_apply_show_parent_num(String p_apply_show_parent_num) {
		this.p_apply_show_parent_num = p_apply_show_parent_num;
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
	
	public String getFirst_orderid() {
		return first_orderid;
	}
	public void setFirst_orderid(String first_orderid) {
		this.first_orderid = first_orderid;
	}
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public String getQuery_type() {
		return query_type;
	}
	public void setQuery_type(String query_type) {
		this.query_type = query_type;
	}
	@NotDbField
	public String getLan_id() {
		return lan_id;
	}
	@NotDbField
	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}
	public String getCode_head_name() {
		return code_head_name;
	}
	public void setCode_head_name(String code_head_name) {
		this.code_head_name = code_head_name;
	}
	public String getChoose_date() {
		return choose_date;
	}
	public void setChoose_date(String choose_date) {
		this.choose_date = choose_date;
	}
	public String getModify_date() {
		return modify_date;
	}
	public void setModify_date(String modify_date) {
		this.modify_date = modify_date;
	}
	public String getTake_date() {
		return take_date;
	}
	public void setTake_date(String take_date) {
		this.take_date = take_date;
	}
	public String getOpr_state_id() {
		return opr_state_id;
	}
	public void setOpr_state_id(String opr_state_id) {
		this.opr_state_id = opr_state_id;
	}
	
	
}