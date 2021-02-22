package com.ztesoft.net.ecsord.params.ecaop.vo;

public class KdyydInfoVO {

	private String id;//消息id，作为消息唯一标识
	private String bespeakid;//订单ID
	private String time;//订单ID
	private String comefrom;//web-网厅；mobile-手厅；weixin-联能微厅；jd-京东；tmall-天猫；alipay-支付宝；wkzs-王卡助手；msg-码上购；wkkd-商城统一王卡宽带预约服务
	private String thirdno;//第三方预约ID
	private String thirdtime;//第三方下单时间，格式：YYYY-MM-DD HH24:mi:ss
	private String provincecode;//省份编码
	private String provincename;//省份名称
	private String citycode;//地市编码
	private String cityname;//地市名称
	private String districtcode;//区县编码
	private String districtname;//区县名称
	private String username;//用户名
	private String userphone;//手机号码
	private String certno;//身份证号
	private String useraddress;//装机地址
	private String productname;//产品名称
	private String broadbandid;//产品编码
	private String price;//价格 单位：离
	private String speed;//速率 eg. 6M
	private String tarifftype;//资费方式 eg.包一年
	private String appointment;//预约安装时间 格式：yyyy-mm-dd HH24:mi:ss
	private String appoactivity;//预约活动ID
	private String actphone;//优惠活动手机号
	private String devprovincecode;//发展人省份编码
	private String devcitycode;//发展人地市编码
	private String devid;//发展人编码
	private String devname;//发展人名称
	private String channelid;//渠道ID
	private String devdepartcode;//发展人部门编码
	private String devphoneno;//发展人电话
	private String relatedcardnum;//下单时关联的王卡号码 (目前只针对于 wkkd 渠道)
	private String prenum;//宽带融合业务下单时关联的号码
	private String hotspotid;//热点ID
	private String del_status;//消息删除状态
	private String deal_status;//处理状态
	private String deal_num;//处理次数
	private String source_from;
	private String address_code;//标准地址编码
	private String cell_name;//小区名称
	private String iom_cell_name;//小区名称（号线系统）
	private String cell_code;//小区编码
	private String cell_type;//01 预判未进线02 预判已进线03 未匹配小区
	private String package_state;//是否为融合产品00 单宽带01 融合02 王卡宽带
	private String post_add;//配送地址
	private String pre_num;//业务号码
	private String num_order_id;//商城订单编号（号卡）
	private String user_tag;//用户类型 1:新用户,2:老用户（指号卡的用户类型）
	private String ope_sys;//业务系统：1、cbss2、bss
	private String product_id;//产品ID（省分配置，BSS/CBSS里的产品ID）
	private String broadband_price;//产品价格，对于融合产品，此价格可以为预存款，单位：厘
	private String tariff_mode;//套餐资费，与TARIFFTYPE字段组成宽带套餐的完整资费，如99包月、1000包年等 单位：厘
	private String appointment_date_segment;//预约办理时间段：0-全天；1-上午；2-下午
	private String activename;//活动名称，当活动id有值时该字段必传
	private String optproduct_id;//附加产品集。多个产品id之间使用英文分号分隔“;”，如1234;456
	private String optproduct_price;//附加产品价格集，与节点OPTPRODUCT_ID对应，多个价格之间使用英文分号分隔。单位：厘
	private String optpackage_id;//附加包集。多个包id之间使用英文分号分隔“;”，如1234;456
	private String optpackage_price;//附加包价格集，与节点OPTPACKAGE_ID对应，多个价格之间使用英文分号分隔。单位：厘
	private String pay_type;//支付方式01在线支付03线下现场付款
	private String pay_state;//支付状态 00 未支付 01 已支付
	private String pay_amout;//支付金额，单位：厘
	private String serviceplatid;//业务平台编码
	private String functioncode;//业务平台功能编码
	private String supplierid;//供应商编码
	private String syn_mode;//同步方式
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBespeakid() {
		return bespeakid;
	}
	public void setBespeakid(String bespeakid) {
		this.bespeakid = bespeakid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getComefrom() {
		return comefrom;
	}
	public void setComefrom(String comefrom) {
		this.comefrom = comefrom;
	}
	public String getThirdno() {
		return thirdno;
	}
	public void setThirdno(String thirdno) {
		this.thirdno = thirdno;
	}
	public String getThirdtime() {
		return thirdtime;
	}
	public void setThirdtime(String thirdtime) {
		this.thirdtime = thirdtime;
	}
	public String getProvincecode() {
		return provincecode;
	}
	public void setProvincecode(String provincecode) {
		this.provincecode = provincecode;
	}
	public String getProvincename() {
		return provincename;
	}
	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public String getDistrictcode() {
		return districtcode;
	}
	public void setDistrictcode(String districtcode) {
		this.districtcode = districtcode;
	}
	public String getDistrictname() {
		return districtname;
	}
	public void setDistrictname(String districtname) {
		this.districtname = districtname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserphone() {
		return userphone;
	}
	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}
	public String getCertno() {
		return certno;
	}
	public void setCertno(String certno) {
		this.certno = certno;
	}
	public String getUseraddress() {
		return useraddress;
	}
	public void setUseraddress(String useraddress) {
		this.useraddress = useraddress;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getBroadbandid() {
		return broadbandid;
	}
	public void setBroadbandid(String broadbandid) {
		this.broadbandid = broadbandid;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getTarifftype() {
		return tarifftype;
	}
	public void setTarifftype(String tarifftype) {
		this.tarifftype = tarifftype;
	}
	public String getAppointment() {
		return appointment;
	}
	public void setAppointment(String appointment) {
		this.appointment = appointment;
	}
	public String getAppoactivity() {
		return appoactivity;
	}
	public void setAppoactivity(String appoactivity) {
		this.appoactivity = appoactivity;
	}
	public String getActphone() {
		return actphone;
	}
	public void setActphone(String actphone) {
		this.actphone = actphone;
	}
	public String getDevprovincecode() {
		return devprovincecode;
	}
	public void setDevprovincecode(String devprovincecode) {
		this.devprovincecode = devprovincecode;
	}
	public String getDevcitycode() {
		return devcitycode;
	}
	public void setDevcitycode(String devcitycode) {
		this.devcitycode = devcitycode;
	}
	public String getDevid() {
		return devid;
	}
	public void setDevid(String devid) {
		this.devid = devid;
	}
	public String getDevname() {
		return devname;
	}
	public void setDevname(String devname) {
		this.devname = devname;
	}
	public String getChannelid() {
		return channelid;
	}
	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}
	public String getDevdepartcode() {
		return devdepartcode;
	}
	public void setDevdepartcode(String devdepartcode) {
		this.devdepartcode = devdepartcode;
	}
	public String getDevphoneno() {
		return devphoneno;
	}
	public void setDevphoneno(String devphoneno) {
		this.devphoneno = devphoneno;
	}
	public String getRelatedcardnum() {
		return relatedcardnum;
	}
	public void setRelatedcardnum(String relatedcardnum) {
		this.relatedcardnum = relatedcardnum;
	}
	public String getPrenum() {
		return prenum;
	}
	public void setPrenum(String prenum) {
		this.prenum = prenum;
	}
	public String getHotspotid() {
		return hotspotid;
	}
	public void setHotspotid(String hotspotid) {
		this.hotspotid = hotspotid;
	}
	public String getDel_status() {
		return del_status;
	}
	public void setDel_status(String del_status) {
		this.del_status = del_status;
	}
	public String getDeal_status() {
		return deal_status;
	}
	public void setDeal_status(String deal_status) {
		this.deal_status = deal_status;
	}
	public String getDeal_num() {
		return deal_num;
	}
	public void setDeal_num(String deal_num) {
		this.deal_num = deal_num;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getAddress_code() {
		return address_code;
	}
	public void setAddress_code(String address_code) {
		this.address_code = address_code;
	}
	public String getCell_name() {
		return cell_name;
	}
	public void setCell_name(String cell_name) {
		this.cell_name = cell_name;
	}
	public String getIom_cell_name() {
		return iom_cell_name;
	}
	public void setIom_cell_name(String iom_cell_name) {
		this.iom_cell_name = iom_cell_name;
	}
	public String getCell_code() {
		return cell_code;
	}
	public void setCell_code(String cell_code) {
		this.cell_code = cell_code;
	}
	public String getCell_type() {
		return cell_type;
	}
	public void setCell_type(String cell_type) {
		this.cell_type = cell_type;
	}
	public String getPackage_state() {
		return package_state;
	}
	public void setPackage_state(String package_state) {
		this.package_state = package_state;
	}
	public String getPost_add() {
		return post_add;
	}
	public void setPost_add(String post_add) {
		this.post_add = post_add;
	}
	public String getPre_num() {
		return pre_num;
	}
	public void setPre_num(String pre_num) {
		this.pre_num = pre_num;
	}
	public String getNum_order_id() {
		return num_order_id;
	}
	public void setNum_order_id(String num_order_id) {
		this.num_order_id = num_order_id;
	}
	public String getUser_tag() {
		return user_tag;
	}
	public void setUser_tag(String user_tag) {
		this.user_tag = user_tag;
	}
	public String getOpe_sys() {
		return ope_sys;
	}
	public void setOpe_sys(String ope_sys) {
		this.ope_sys = ope_sys;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getBroadband_price() {
		return broadband_price;
	}
	public void setBroadband_price(String broadband_price) {
		this.broadband_price = broadband_price;
	}
	public String getTariff_mode() {
		return tariff_mode;
	}
	public void setTariff_mode(String tariff_mode) {
		this.tariff_mode = tariff_mode;
	}
	public String getAppointment_date_segment() {
		return appointment_date_segment;
	}
	public void setAppointment_date_segment(String appointment_date_segment) {
		this.appointment_date_segment = appointment_date_segment;
	}
	public String getActivename() {
		return activename;
	}
	public void setActivename(String activename) {
		this.activename = activename;
	}
	public String getOptproduct_id() {
		return optproduct_id;
	}
	public void setOptproduct_id(String optproduct_id) {
		this.optproduct_id = optproduct_id;
	}
	public String getOptproduct_price() {
		return optproduct_price;
	}
	public void setOptproduct_price(String optproduct_price) {
		this.optproduct_price = optproduct_price;
	}
	public String getOptpackage_id() {
		return optpackage_id;
	}
	public void setOptpackage_id(String optpackage_id) {
		this.optpackage_id = optpackage_id;
	}
	public String getOptpackage_price() {
		return optpackage_price;
	}
	public void setOptpackage_price(String optpackage_price) {
		this.optpackage_price = optpackage_price;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getPay_state() {
		return pay_state;
	}
	public void setPay_state(String pay_state) {
		this.pay_state = pay_state;
	}
	public String getPay_amout() {
		return pay_amout;
	}
	public void setPay_amout(String pay_amout) {
		this.pay_amout = pay_amout;
	}
	public String getServiceplatid() {
		return serviceplatid;
	}
	public void setServiceplatid(String serviceplatid) {
		this.serviceplatid = serviceplatid;
	}
	public String getFunctioncode() {
		return functioncode;
	}
	public void setFunctioncode(String functioncode) {
		this.functioncode = functioncode;
	}
	public String getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}
	public String getSyn_mode() {
		return syn_mode;
	}
	public void setSyn_mode(String syn_mode) {
		this.syn_mode = syn_mode;
	}
	
}
