package com.ztesoft.net.server.jsonserver.hlwgwsdpojo;

import java.util.List;

import org.springframework.util.StringUtils;

public class InterFixBusiOrderBroadInfo {
	private String is_judge_address;// 是否预判 0-未预判 1-已预判
	private String product_type; // 产品分类 KD - 宽带 ; TV - 沃tv
	private String std_address;// 标准地址
	private String adsl_addr_desc;// 标准地址
	private String user_address;// 宽带用户地址
	private String moderm_id;// 光猫ID
	private String moderm_name;// 光猫ID中文名称
	private String moderm_status;// 光猫物品状态 1 已领取;0 未领取
	private String exch_code;// 局向编码
	private String grid;// 固网网格
	private String bb_account;// 宽带账号
	private String bb_num;// 宽带号码
	private String appo_date;// 预约装机时间
	private String xcounty_id;// 行政县分
	private List<InterFixFeeInfo> fee_list; // 费用项
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
	public String getIs_judge_address() {
		return is_judge_address;
	}

	public void setIs_judge_address(String is_judge_address) {
		this.is_judge_address = is_judge_address;
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

	public String getModerm_id() {
		return moderm_id;
	}

	public void setModerm_id(String moderm_id) {
		this.moderm_id = moderm_id;
	}

	public String getModerm_status() {
		return moderm_status;
	}

	public void setModerm_status(String moderm_status) {
		this.moderm_status = moderm_status;
	}

	public String getExch_code() {
		return exch_code;
	}

	public void setExch_code(String exch_code) {
		this.exch_code = exch_code;
	}

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
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

	public String getXcounty_id() {
		return xcounty_id;
	}

	public void setXcounty_id(String xcounty_id) {
		this.xcounty_id = xcounty_id;
	}

	public List getFee_list() {
		return fee_list;
	}

	public void setFee_list(List fee_list) {
		this.fee_list = fee_list;
	}

	public String getModerm_name() {
		return moderm_name;
	}

	public void setModerm_name(String moderm_name) {
		this.moderm_name = moderm_name;
	}

	public String getAdsl_addr_desc() {
		if(StringUtils.isEmpty(adsl_addr_desc)){
			return user_address;
		}
		return adsl_addr_desc;
	}

	public void setAdsl_addr_desc(String adsl_addr_desc) {
		this.adsl_addr_desc = adsl_addr_desc;
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
