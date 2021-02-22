package com.ztesoft.net.server.jsonserver.rhywpojo;

import java.util.List;

public class GroupOrderBroadInfo {
	private String product_type;// 产品分类-必填
	
	private String std_address;// 标准地址-必填
	
	private String user_address;// 宽带用户地址-必填
	
	private String county_id;// 县分编码-必填
	
	private String develop_point_code;// 发展点编码-非必填
	
	private String develop_point_name;// 发展点名称-非必填
	
	private String moderm_id;// 光猫ID-非必填
	
	private String exch_code;// 局向编码-必填
	
	private String area_exch_id ;// 区局--必填
	
	private String point_exch_id ;// 端局--必填
	
	private String module_exch_id ;// 模块局
	
	private String grid;// 固网网格-非必填
	
	private String user_kind;// 用户种类-必填
	
	private String deal_operator;// 办理操作员-非必填
	
	private String deal_office_id;// 办理操作点-非必填
	
	private String bb_account;// 宽带账号-必填
	
	private String bb_num;// 宽带号码-必填
	
	private String appo_date;// 预约装机时间-非必填
	
	private String req_swift_num;// 拍照流水-非必填
	
	private String access_type;// 接入方式-非必填
	
	private String Object_status;// 光猫物品状态-非必填
	
	private String business_county;// 营业县分-非必填
	
	private String wotv_num;//沃tv号码
	
	private List<GroupOrderFeeInfo> fee_list;// 费用信息节点-非必填
	
	private String bind_rela_type;//绑定类型，老号新宽绑定手机时必传
	
	private String bb_password;//密码
	
	/**
	 * 销售模式	否	broad_info	sale_mode	10	01：免费租用 06：用户自购 07：用户自备用户自备
新设备档位	否	broad_info	devic_gear	10	00：标准版(光猫) 
01：加强版(光猫)
是否竣工生效	否	broad_info	is_done_active	10	1：是 
0：不是

	 * @return
	 */
	private String sale_mode ;
	
	private String devic_gear ;
	
	private String is_done_active ;
	
	private String account_number ;
	
	private String kd_low_rate ;
	public String getWotv_num() {
		return wotv_num;
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

	public void setWotv_num(String wotv_num) {
		this.wotv_num = wotv_num;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public String getStd_address() {
		return std_address;
	}

	public void setStd_address(String std_address) {
		this.std_address = std_address;
	}

	public String getUser_address() {
		return user_address;
	}

	public void setUser_address(String user_address) {
		this.user_address = user_address;
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

	public String getExch_code() {
		return exch_code;
	}

	public void setExch_code(String exch_code) {
		this.exch_code = exch_code;
	}
	public String getArea_exch_id() {
		return area_exch_id;
	}

	public void setArea_exch_id(String area_exch_id) {
		this.area_exch_id = area_exch_id;
	}

	public String getPoint_exch_id() {
		return point_exch_id;
	}

	public void setPoint_exch_id(String point_exch_id) {
		this.point_exch_id = point_exch_id;
	}

	public String getModule_exch_id() {
		return module_exch_id;
	}

	public void setModule_exch_id(String module_exch_id) {
		this.module_exch_id = module_exch_id;
	}
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

	public String getAppo_date() {
		return appo_date;
	}

	public void setAppo_date(String appo_date) {
		this.appo_date = appo_date;
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
		return Object_status;
	}

	public void setObject_status(String object_status) {
		Object_status = object_status;
	}

	public String getBusiness_county() {
		return business_county;
	}

	public void setBusiness_county(String business_county) {
		this.business_county = business_county;
	}

	public List<GroupOrderFeeInfo> getFee_list() {
		return fee_list;
	}

	public void setFee_list(List<GroupOrderFeeInfo> fee_list) {
		this.fee_list = fee_list;
	}
	
	public String getBind_rela_type() {
		return bind_rela_type;
	}

	public void setBind_rela_type(String bind_rela_type) {
		this.bind_rela_type = bind_rela_type;
	}

	public String getBb_password() {
		return bb_password;
	}

	public void setBb_password(String bb_password) {
		this.bb_password = bb_password;
	}

	public String getAccount_number() {
		return account_number;
	}

	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

    public String getKd_low_rate() {
        return kd_low_rate;
    }

    public void setKd_low_rate(String kd_low_rate) {
        this.kd_low_rate = kd_low_rate;
    }
}
