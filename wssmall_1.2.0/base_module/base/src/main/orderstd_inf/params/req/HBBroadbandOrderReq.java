package params.req;

import java.util.Map;

public class HBBroadbandOrderReq {

	private Map ext_params;
	private String order_id;//接口流水ID
	private String trade_type_code;//业务类型
	private String in_mode_code;//预受理接入方式
	private String user_id;//目标系统用户ID
	private String cust_id;//目标系统客户id
	private String pspt_type_code;//客户证件类型
	private String pspt_id;//客户证件号码
	private String serial_number;//号码
	private String cust_name;//客户名称
	private String user_name;//用户名称
	private String install_address;//装机地址
	private String accept_date;//业务受理时间
	private String accept_month;//业务受理月份
	private String develop_depart_id;//业务发展渠道ID
	private String develop_staff_id;//业务发展人ID
	private String develop_eparchy_code;//业务发展地市
	private String develop_city_code;//业务发展区县
	private String develop_date;//业务发展日期
	private String trade_staff_id;//业务受理员工ID
	private String trade_depart_id;//业务受理部门
	private String trade_city_code;//业务受理区县
	private String trade_eparchy_code;//业务受理地市
	private String eparchy_code;//用户所属地市
	private String city_code;//用户所属区县
	private String oper_fee;//预受理工单总费用
	private String fee_state;//是否已经收费
	private String fee_time;//收费时间
	private String fee_staff_id;//收费员ID
	private String cancel_tag;//返销标志
	private String cancel_date;//返销时间
	private String cancel_staff_id;//返销员工
	private String cancel_depart_id;//返销部门
	private String cancel_city_code;//返销区县
	private String cancel_eparchy_code;//返销地市
	private String chk_tag;//预受理工单状态
	private String contact;//联系人
	private String contact_phone;//联系电话
	private String remark;//工单备注
	private String rsrv_str5;//是否CBSS业务工单
	private String rsrv_str6;//外围系统工单ID
	private String rsrv_str3;//对应的后台工号
	private String rsrv_str1;//主产品id
	private String rsrv_str2;//住套餐资费ID
	private String net_type_code;//网别编码
	private String brand_code;//品牌编码
	private String exch_code;//局向编码
	private String grid_manager_staff;//网格经理工号
	private String grid_manager_depart;//网格经理部门
	private String pre_onu_type;//预受理-终端设备类型
	private String onu_in_mode;//终端提供方式
	private String onu_type;//终端类型
	private String onu_brand;//终端品牌
	private String onu_model;//终端型号
	private String onu_id;//终端串码
	private String onu_mac;//终端MAC地址
	private String yifen_tag;//一分卡工单标示
	
	
	private String pspt_addr;//证件地址
	private String user_phone;//买家电话
	private String product_name;//产品名称
	private String broadband_speed;//宽带速率
	private String develop_staff_name;//业务发展名称
	private String order_source;//订单来源
	
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getTrade_type_code() {
		return trade_type_code;
	}
	public void setTrade_type_code(String trade_type_code) {
		this.trade_type_code = trade_type_code;
	}
	public String getIn_mode_code() {
		return in_mode_code;
	}
	public void setIn_mode_code(String in_mode_code) {
		this.in_mode_code = in_mode_code;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getPspt_type_code() {
		return pspt_type_code;
	}
	public void setPspt_type_code(String pspt_type_code) {
		this.pspt_type_code = pspt_type_code;
	}
	public String getPspt_id() {
		return pspt_id;
	}
	public void setPspt_id(String pspt_id) {
		this.pspt_id = pspt_id;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getInstall_address() {
		return install_address;
	}
	public void setInstall_address(String install_address) {
		this.install_address = install_address;
	}
	public String getAccept_date() {
		return accept_date;
	}
	public void setAccept_date(String accept_date) {
		this.accept_date = accept_date;
	}
	public String getAccept_month() {
		return accept_month;
	}
	public void setAccept_month(String accept_month) {
		this.accept_month = accept_month;
	}
	public String getDevelop_depart_id() {
		return develop_depart_id;
	}
	public void setDevelop_depart_id(String develop_depart_id) {
		this.develop_depart_id = develop_depart_id;
	}
	public String getDevelop_staff_id() {
		return develop_staff_id;
	}
	public void setDevelop_staff_id(String develop_staff_id) {
		this.develop_staff_id = develop_staff_id;
	}
	public String getDevelop_eparchy_code() {
		return develop_eparchy_code;
	}
	public void setDevelop_eparchy_code(String develop_eparchy_code) {
		this.develop_eparchy_code = develop_eparchy_code;
	}
	public String getDevelop_city_code() {
		return develop_city_code;
	}
	public void setDevelop_city_code(String develop_city_code) {
		this.develop_city_code = develop_city_code;
	}
	public String getDevelop_date() {
		return develop_date;
	}
	public void setDevelop_date(String develop_date) {
		this.develop_date = develop_date;
	}
	public String getTrade_staff_id() {
		return trade_staff_id;
	}
	public void setTrade_staff_id(String trade_staff_id) {
		this.trade_staff_id = trade_staff_id;
	}
	public String getTrade_depart_id() {
		return trade_depart_id;
	}
	public void setTrade_depart_id(String trade_depart_id) {
		this.trade_depart_id = trade_depart_id;
	}
	public String getTrade_city_code() {
		return trade_city_code;
	}
	public void setTrade_city_code(String trade_city_code) {
		this.trade_city_code = trade_city_code;
	}
	public String getTrade_eparchy_code() {
		return trade_eparchy_code;
	}
	public void setTrade_eparchy_code(String trade_eparchy_code) {
		this.trade_eparchy_code = trade_eparchy_code;
	}
	public String getEparchy_code() {
		return eparchy_code;
	}
	public void setEparchy_code(String eparchy_code) {
		this.eparchy_code = eparchy_code;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public String getOper_fee() {
		return oper_fee;
	}
	public void setOper_fee(String oper_fee) {
		this.oper_fee = oper_fee;
	}
	public String getFee_state() {
		return fee_state;
	}
	public void setFee_state(String fee_state) {
		this.fee_state = fee_state;
	}
	public String getFee_time() {
		return fee_time;
	}
	public void setFee_time(String fee_time) {
		this.fee_time = fee_time;
	}
	public String getFee_staff_id() {
		return fee_staff_id;
	}
	public void setFee_staff_id(String fee_staff_id) {
		this.fee_staff_id = fee_staff_id;
	}
	public String getCancel_tag() {
		return cancel_tag;
	}
	public void setCancel_tag(String cancel_tag) {
		this.cancel_tag = cancel_tag;
	}
	public String getCancel_date() {
		return cancel_date;
	}
	public void setCancel_date(String cancel_date) {
		this.cancel_date = cancel_date;
	}
	public String getCancel_staff_id() {
		return cancel_staff_id;
	}
	public void setCancel_staff_id(String cancel_staff_id) {
		this.cancel_staff_id = cancel_staff_id;
	}
	public String getCancel_depart_id() {
		return cancel_depart_id;
	}
	public void setCancel_depart_id(String cancel_depart_id) {
		this.cancel_depart_id = cancel_depart_id;
	}
	public String getCancel_city_code() {
		return cancel_city_code;
	}
	public void setCancel_city_code(String cancel_city_code) {
		this.cancel_city_code = cancel_city_code;
	}
	public String getCancel_eparchy_code() {
		return cancel_eparchy_code;
	}
	public void setCancel_eparchy_code(String cancel_eparchy_code) {
		this.cancel_eparchy_code = cancel_eparchy_code;
	}
	public String getChk_tag() {
		return chk_tag;
	}
	public void setChk_tag(String chk_tag) {
		this.chk_tag = chk_tag;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContact_phone() {
		return contact_phone;
	}
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRsrv_str5() {
		return rsrv_str5;
	}
	public void setRsrv_str5(String rsrv_str5) {
		this.rsrv_str5 = rsrv_str5;
	}
	public String getRsrv_str6() {
		return rsrv_str6;
	}
	public void setRsrv_str6(String rsrv_str6) {
		this.rsrv_str6 = rsrv_str6;
	}
	public String getRsrv_str3() {
		return rsrv_str3;
	}
	public void setRsrv_str3(String rsrv_str3) {
		this.rsrv_str3 = rsrv_str3;
	}
	public String getRsrv_str1() {
		return rsrv_str1;
	}
	public void setRsrv_str1(String rsrv_str1) {
		this.rsrv_str1 = rsrv_str1;
	}
	public String getRsrv_str2() {
		return rsrv_str2;
	}
	public void setRsrv_str2(String rsrv_str2) {
		this.rsrv_str2 = rsrv_str2;
	}
	public String getNet_type_code() {
		return net_type_code;
	}
	public void setNet_type_code(String net_type_code) {
		this.net_type_code = net_type_code;
	}
	public String getBrand_code() {
		return brand_code;
	}
	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}
	public String getExch_code() {
		return exch_code;
	}
	public void setExch_code(String exch_code) {
		this.exch_code = exch_code;
	}
	public String getGrid_manager_staff() {
		return grid_manager_staff;
	}
	public void setGrid_manager_staff(String grid_manager_staff) {
		this.grid_manager_staff = grid_manager_staff;
	}
	public String getGrid_manager_depart() {
		return grid_manager_depart;
	}
	public void setGrid_manager_depart(String grid_manager_depart) {
		this.grid_manager_depart = grid_manager_depart;
	}
	public String getPre_onu_type() {
		return pre_onu_type;
	}
	public void setPre_onu_type(String pre_onu_type) {
		this.pre_onu_type = pre_onu_type;
	}
	public String getOnu_in_mode() {
		return onu_in_mode;
	}
	public void setOnu_in_mode(String onu_in_mode) {
		this.onu_in_mode = onu_in_mode;
	}
	public String getOnu_type() {
		return onu_type;
	}
	public void setOnu_type(String onu_type) {
		this.onu_type = onu_type;
	}
	public String getOnu_brand() {
		return onu_brand;
	}
	public void setOnu_brand(String onu_brand) {
		this.onu_brand = onu_brand;
	}
	public String getOnu_model() {
		return onu_model;
	}
	public void setOnu_model(String onu_model) {
		this.onu_model = onu_model;
	}
	public String getOnu_id() {
		return onu_id;
	}
	public void setOnu_id(String onu_id) {
		this.onu_id = onu_id;
	}
	public String getOnu_mac() {
		return onu_mac;
	}
	public void setOnu_mac(String onu_mac) {
		this.onu_mac = onu_mac;
	}
	public String getYifen_tag() {
		return yifen_tag;
	}
	public void setYifen_tag(String yifen_tag) {
		this.yifen_tag = yifen_tag;
	}
	public Map getExt_params() {
		return ext_params;
	}
	public void setExt_params(Map ext_params) {
		this.ext_params = ext_params;
	}
	public String getPspt_addr() {
		return pspt_addr;
	}
	public void setPspt_addr(String pspt_addr) {
		this.pspt_addr = pspt_addr;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getBroadband_speed() {
		return broadband_speed;
	}
	public void setBroadband_speed(String broadband_speed) {
		this.broadband_speed = broadband_speed;
	}
	public String getOrder_source() {
		return order_source;
	}
	public void setOrder_source(String order_source) {
		this.order_source = order_source;
	}
	public String getDevelop_staff_name() {
		return develop_staff_name;
	}
	public void setDevelop_staff_name(String develop_staff_name) {
		this.develop_staff_name = develop_staff_name;
	}
	
	
}
