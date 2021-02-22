package com.ztesoft.net.server.jsonserver.msgpojo;

public class CodePurchaseMallBroadInfo {

	private String product_code;//产品编码 -必填
	
	private String service_type;//受理类型
	
	private String std_address;//标准地址 -必填
	
	private String user_address;//宽带用户地址 -必填
	
	private String exch_code;//局向编码 -必填
	
	//private String fixed_grid;//固网网格 -必填
	private String grid;//固网网格 -非必填
	
	private String user_kind;//用户种类 必填
	
	private String deal_operator;//办理操作员 -非必填
	
	private String deal_office_id;//办理操作点 -非必填
	
	private String bb_account;//宽带账号 -必填
	
	private String bb_num;//宽带号码
	
	private String appt_date;//预约装机时间 -非必填
	
	private String county_id;//bss县份
	
	private String develop_point_code;//发展点编码-非必填
	
	private String develop_point_name;//发展点名称-非必填
	
	private String moderm_id;//光猫ID-业务类型是FTTH时必传
	
	private String req_swift_num;//拍照流水
	
	private String access_type;//接入方式是
	
	private String object_status;//光猫物品状态
	
	private String business_county;//营业县分
	
	private String product_type;//产品分类
	
	private String wotv_num;//沃tv号码
	
	private String old_service_type;//老业务类型
	
	/**
	 * Add by wcl 
	 * 行销宽带收单新增字段
	 * 销售模式	否	broad_info	sale_mode	10	01：免费租用 06：用户自购 07：用户自备用户自备
新设备档位	否	broad_info	devic_gear	10	00：标准版(光猫) 
01：加强版(光猫)
是否竣工生效	否	broad_info	is_done_active	10	1：是 
0：不是

	 */
	private String sale_mode ;
	
	private String devic_gear ;
	
	private String is_done_active ;
	
	private String account_number ;
	
	public String getAccount_number() {
		return account_number;
	}

	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

	public String getSale_mode() {
		return sale_mode;
	}

	public void setSale_mode(String sale_mode) {
		this.sale_mode = sale_mode;
	}

	public String getDevic_gear() {
		return devic_gear;
	}

	public void setDevic_gear(String devic_gear) {
		this.devic_gear = devic_gear;
	}

	public String getIs_done_active() {
		return is_done_active;
	}

	public void setIs_done_active(String is_done_active) {
		this.is_done_active = is_done_active;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getUser_address() {
		return user_address;
	}

	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}

	public String getExch_code() {
		return exch_code;
	}

	public void setExch_code(String exch_code) {
		this.exch_code = exch_code;
	}

//	public String getFixed_grid() {
//		return fixed_grid;
//	}
//
//	public void setFixed_grid(String fixed_grid) {
//		this.fixed_grid = fixed_grid;
//	}
	
	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public String getUser_kind() {
		return user_kind;
	}

	public void setUser_kind(String user_kind) {
		this.user_kind = user_kind;
	}

	public String getDeal_operator() {
		return deal_operator;
	}

	public void setDeal_operator(String deal_operator) {
		this.deal_operator = deal_operator;
	}

	public String getAppt_date() {
		return appt_date;
	}

	public void setAppt_date(String appt_date) {
		this.appt_date = appt_date;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getStd_address() {
		return std_address;
	}

	public void setStd_address(String std_address) {
		this.std_address = std_address;
	}

	public String getDeal_office_id() {
		return deal_office_id;
	}

	public void setDeal_office_id(String deal_office_id) {
		this.deal_office_id = deal_office_id;
	}

	public String getBb_account() {
		return bb_account;
	}

	public void setBb_account(String bb_account) {
		this.bb_account = bb_account;
	}

	public String getBb_num() {
		return bb_num;
	}

	public void setBb_num(String bb_num) {
		this.bb_num = bb_num;
	}

	public String getCounty_id() {
		return county_id;
	}

	public void setCounty_id(String county_id) {
		this.county_id = county_id;
	}

	public String getDevelop_point_code() {
		return develop_point_code;
	}

	public void setDevelop_point_code(String develop_point_code) {
		this.develop_point_code = develop_point_code;
	}

	public String getDevelop_point_name() {
		return develop_point_name;
	}

	public void setDevelop_point_name(String develop_point_name) {
		this.develop_point_name = develop_point_name;
	}

	public String getModerm_id() {
		return moderm_id;
	}

	public void setModerm_id(String moderm_id) {
		this.moderm_id = moderm_id;
	}

	public String getReq_swift_num() {
		return req_swift_num;
	}

	public void setReq_swift_num(String req_swift_num) {
		this.req_swift_num = req_swift_num;
	}

	public String getAccess_type() {
		return access_type;
	}

	public void setAccess_type(String access_type) {
		this.access_type = access_type;
	}

	public String getObject_status() {
		return object_status;
	}

	public void setObject_status(String object_status) {
		this.object_status = object_status;
	}

	public String getBusiness_county() {
		return business_county;
	}

	public void setBusiness_county(String business_county) {
		this.business_county = business_county;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public String getWotv_num() {
		return wotv_num;
	}

	public void setWotv_num(String wotv_num) {
		this.wotv_num = wotv_num;
	}

	public String getOld_service_type() {
		return old_service_type;
	}

	public void setOld_service_type(String old_service_type) {
		this.old_service_type = old_service_type;
	}
	
}