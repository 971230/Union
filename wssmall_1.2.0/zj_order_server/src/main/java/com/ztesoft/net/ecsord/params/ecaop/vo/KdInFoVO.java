package com.ztesoft.net.ecsord.params.ecaop.vo;

public class KdInFoVO {

	private String is_new = "";// 是否新装,0.否;1.是
	private String subs_id = "";// 宽带ID
	private String service_num1 = "";// 宽带账号
	private String service_num2 = "";// 宽带号码
	private String source_flag = "";// 号码来源,1:总部;0:省份
	private String url_key = "";// 服务器地址配置标识
	private String http_request = "";// 向资源中心发起的HTTP请求:预占
	private String res_number = "";// 资源中心号码
	private String http_request1 = "";// 向资源中心发起的HTTP请求:占用
	private String nomalize_flag = "";// 用户是否激活,1是,0否
	private String service_type = "";// 宽带服务类型
	private String standard_address = "";// 标准地址
	private String Object_id = "";// 光猫物品ID
	private String speed_level = "";// 速率,沃TV为空,宽带必传
	private String product_id = "";// 宽带产品
	private String exch_code = "";// 局向编码
	private String fee_list = "";// 费用清单：科目1&应收费用&减免费用&实收费用；科目2&应收费用&减免费用&实收费用；费用精确到小数点2位
	private String user_kind = "";// 用户种类,沃TV默认传11 
	private String accessWay = "";// 接入方式
	private String totalFee = "";// 宽带总费用
	private String cust_grid = "";// 固网网格
	private String grid = "";// 商企网格
	private String cust_building_id = "";// 聚类市场信息
	private String user_addr = "";// 装机地址
	private String is_must = "";// 是否必要成员
	private String is_wotv = "";// 是否沃TV
	private String object_status = "";
	private String county_id = "";
	
	/**
	 * Add by wcl
	 * 融合预提交新增参数
	 */
	private String sale_mode = "";
	private String devic_gear = "";
	private String is_done_active = "";
	
	public String getCounty_id() {
		return county_id; 
	}

	public void setCounty_id(String county_id) {
		this.county_id = county_id;
	}

	public String getIs_new() {
		return is_new;
	}

	public void setIs_new(String is_new) {
		this.is_new = is_new;
	}

	public String getSubs_id() {
		return subs_id;
	}

	public void setSubs_id(String subs_id) {
		this.subs_id = subs_id;
	}

	public String getService_num1() {
		return service_num1;
	}

	public void setService_num1(String service_num1) {
		this.service_num1 = service_num1;
	}

	public String getService_num2() {
		return service_num2;
	}

	public void setService_num2(String service_num2) {
		this.service_num2 = service_num2;
	}

	public String getSource_flag() {
		return source_flag;
	}

	public void setSource_flag(String source_flag) {
		this.source_flag = source_flag;
	}

	public String getUrl_key() {
		return url_key;
	}

	public void setUrl_key(String url_key) {
		this.url_key = url_key;
	}

	public String getHttp_request() {
		return http_request;
	}

	public void setHttp_request(String http_request) {
		this.http_request = http_request;
	}

	public String getRes_number() {
		return res_number;
	}

	public void setRes_number(String res_number) {
		this.res_number = res_number;
	}

	public String getHttp_request1() {
		return http_request1;
	}

	public void setHttp_request1(String http_request1) {
		this.http_request1 = http_request1;
	}

	public String getNomalize_flag() {
		return nomalize_flag;
	}

	public void setNomalize_flag(String nomalize_flag) {
		this.nomalize_flag = nomalize_flag;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getStandard_address() {
		return standard_address;
	}

	public void setStandard_address(String standard_address) {
		this.standard_address = standard_address;
	}

	public String getObject_id() {
		return Object_id;
	}

	public void setObject_id(String object_id) {
		Object_id = object_id;
	}

	public String getSpeed_level() {
		return speed_level;
	}

	public void setSpeed_level(String speed_level) {
		this.speed_level = speed_level;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getExch_code() {
		return exch_code;
	}

	public void setExch_code(String exch_code) {
		this.exch_code = exch_code;
	}

	public String getFee_list() {
		return fee_list;
	}

	public void setFee_list(String fee_list) {
		this.fee_list = fee_list;
	}

	public String getUser_kind() {
		return user_kind;
	}

	public void setUser_kind(String user_kind) {
		this.user_kind = user_kind;
	}

	public String getAccessWay() {
		return accessWay;
	}

	public void setAccessWay(String accessWay) {
		this.accessWay = accessWay;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getCust_grid() {
		return cust_grid;
	}

	public void setCust_grid(String cust_grid) {
		this.cust_grid = cust_grid;
	}

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public String getCust_building_id() {
		return cust_building_id;
	}

	public void setCust_building_id(String cust_building_id) {
		this.cust_building_id = cust_building_id;
	}

	public String getUser_addr() {
		return user_addr;
	}

	public void setUser_addr(String user_addr) {
		this.user_addr = user_addr;
	}

	public String getIs_must() {
		return is_must;
	}

	public void setIs_must(String is_must) {
		this.is_must = is_must;
	}

	public String getIs_wotv() {
		return is_wotv;
	}

	public void setIs_wotv(String is_wotv) {
		this.is_wotv = is_wotv;
	}

	public String getObject_status() {
		return object_status;
	}

	public void setObject_status(String object_status) {
		this.object_status = object_status;
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

}
