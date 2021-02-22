package com.ztesoft.net.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.AdjunctItem;

public class OuterItem  implements Serializable{
	
	@ZteSoftCommentAnnotationParam(name="产品ID",type="String",isNecessary="Y",desc="产品ID")
	private String product_id;//产品ID
	@ZteSoftCommentAnnotationParam(name="产品名称",type="String",isNecessary="Y",desc="产品名称")
	private String pro_name;//产品名称
	@ZteSoftCommentAnnotationParam(name="产品类型",type="String",isNecessary="Y",desc="产品类型[类型CAT_ID]")
	private String pro_type;//产品类型
	@ZteSoftCommentAnnotationParam(name="是否赠品",type="String",isNecessary="Y",desc="是否赠品[0否，1是]")
	private String isgifts;//是否赠品	字典：0否，1是
	@ZteSoftCommentAnnotationParam(name="品牌编码",type="String",isNecessary="Y",desc="品牌编码")
	private String brand_number;//品牌编码
	@ZteSoftCommentAnnotationParam(name="品牌名称",type="String",isNecessary="Y",desc="品牌名称")
	private String brand_name;//品牌名称
	@ZteSoftCommentAnnotationParam(name="商品应收金额",type="String",isNecessary="Y",desc="商品应收金额")
	private Double pro_origfee;//商品应收金额
	@ZteSoftCommentAnnotationParam(name="颜色编码",type="String",isNecessary="Y",desc="颜色编码")
	private String color_code;//颜色编码
	@ZteSoftCommentAnnotationParam(name="颜色名称",type="String",isNecessary="Y",desc="颜色名称")
	private String color_name;//颜色名称
	@ZteSoftCommentAnnotationParam(name="型号编码",type="String",isNecessary="Y",desc="型号编码")
	private String specification_code;//型号编码
	@ZteSoftCommentAnnotationParam(name="型号名称",type="String",isNecessary="Y",desc="型号名称")
	private String specificatio_nname;//型号名称
	@ZteSoftCommentAnnotationParam(name="机型编码",type="String",isNecessary="Y",desc="机型编码")
	private String model_code;//机型编码
	@ZteSoftCommentAnnotationParam(name="机型名称",type="String",isNecessary="Y",desc="机型名称")
	private String model_name;//机型名称
	@ZteSoftCommentAnnotationParam(name="终端串号",type="String",isNecessary="Y",desc="终端串号")
	private String terminal_num;//终端串号
	@ZteSoftCommentAnnotationParam(name="手机号码",type="String",isNecessary="N",desc="手机号码")
	private String phone_num;//手机号码
	@ZteSoftCommentAnnotationParam(name="机主姓名",type="String",isNecessary="N",desc="机主姓名")
	private String phone_owner_name;//机主姓名
	@ZteSoftCommentAnnotationParam(name="卡类型",type="String",isNecessary="Y",desc="卡类型")
	private String white_cart_type;//卡类型
	@ZteSoftCommentAnnotationParam(name="首月资费方式",type="String",isNecessary="Y",desc="首月资费方式")
	private String first_payment;//首月资费方式
	@ZteSoftCommentAnnotationParam(name="是否托收",type="String",isNecessary="Y",desc="是否托收[0否，1是]")
	private String collection;//是否托收	字典：0否，1是
	@ZteSoftCommentAnnotationParam(name="合约编码",type="String",isNecessary="Y",desc="合约编码")
	private String out_package_id;//合约编码
	@ZteSoftCommentAnnotationParam(name="套餐名称",type="String",isNecessary="Y",desc="套餐名称")
	private String plan_title;//套餐名称
	@ZteSoftCommentAnnotationParam(name="总部套餐编码",type="String",isNecessary="N",desc="总部套餐编码")
	private String out_plan_id_ess;//总部套餐编码
	@ZteSoftCommentAnnotationParam(name="BSS套餐编码",type="String",isNecessary="N",desc="BSS套餐编码")
	private String out_plan_id_bss;//BSS套餐编码
	@ZteSoftCommentAnnotationParam(name="减免预存标记",type="String",isNecessary="N",desc="减免预存标记")
	private String reliefpres_flag;//减免预存标记
	@ZteSoftCommentAnnotationParam(name="SIM卡号",type="String",isNecessary="N",desc="SIM卡号")
	private String simid;//SIM卡号
	@ZteSoftCommentAnnotationParam(name="产品网别",type="String",isNecessary="N",desc="产品网别[2G、3G]")
	private String product_net;//产品网别
	@ZteSoftCommentAnnotationParam(name="合约期限",type="String",isNecessary="N",desc="合约期限")
	private String act_protper;//合约期限
	@ZteSoftCommentAnnotationParam(name="活动预存款",type="String",isNecessary="N",desc="活动预存款")
	private String phone_deposit;//活动预存款
	@ZteSoftCommentAnnotationParam(name="亲情号码",type="String",isNecessary="N",desc="亲情号码")
	private String famliy_num;//亲情号码
	
	@ZteSoftCommentAnnotationParam(name="调整信用度",type="String",isNecessary="N",desc="调整信用度")
	private String adjustment_credit;//调整信用度
	@ZteSoftCommentAnnotationParam(name="上级银行编码",type="String",isNecessary="N",desc="上级银行编码")
	private String superiors_bankcode;//上级银行编码
	@ZteSoftCommentAnnotationParam(name="银行编码",type="String",isNecessary="N",desc="银行编码")
	private String bank_code;//银行编码
	@ZteSoftCommentAnnotationParam(name="银行账号",type="String",isNecessary="N",desc="银行账号")
	private String bank_account;//银行账号
	@ZteSoftCommentAnnotationParam(name="银行开户名",type="String",isNecessary="N",desc="银行开户名")
	private String bank_user;//银行开户名
	@ZteSoftCommentAnnotationParam(name="是否iphone套餐 ",type="String",isNecessary="Y",desc="是否iphone套餐 [0否，1是]")
	private String is_iphone_plan;//是否iphone套餐  字典：0否，1是
	@ZteSoftCommentAnnotationParam(name="是否情侣号码 ",type="String",isNecessary="Y",desc="是否情侣号码[0否，1是 --默认0]")
	private String is_loves_phone="0";//是否情侣号码 字典：0否，1是 --默认0
	@ZteSoftCommentAnnotationParam(name="是否靓号 ",type="String",isNecessary="N",desc="是否靓号")
	private String is_liang;//是否靓号
	
	@ZteSoftCommentAnnotationParam(name="靓号金额 ",type="String",isNecessary="N",desc="靓号金额")
	private Double liang_price;//靓号金额
	@ZteSoftCommentAnnotationParam(name="靓号单编号",type="String",isNecessary="N",desc="靓号单编号")
	private String liang_code;//靓号单编号
	@ZteSoftCommentAnnotationParam(name="是否库存机",type="String",isNecessary="N",desc="是否库存机")
	private String is_stock_phone;//是否库存机
	@ZteSoftCommentAnnotationParam(name="活动类型",type="String",isNecessary="N",desc="活动类型")
	private String ative_type;//活动类型
	@ZteSoftCommentAnnotationParam(name="优惠选项",type="String",isNecessary="N",desc="优惠选项")
	private String disfee_select;//优惠选项
	@ZteSoftCommentAnnotationParam(name="社会代理商名称",type="String",isNecessary="N",desc="社会代理商名称")
	private String society_name;//社会代理商名称
	@ZteSoftCommentAnnotationParam(name="代理商终端结算价格",type="String",isNecessary="Y",desc="代理商终端结算价格")
	private Double society_price;//代理商终端结算价格
	@ZteSoftCommentAnnotationParam(name="订货数量",type="String",isNecessary="Y",desc="订货数量")
	private Integer pro_num;//订货数量
	//private String pro_detail_code;
	
	@ZteSoftCommentAnnotationParam(name="证件类型",type="String",isNecessary="N",desc="证件类型")
	private String cert_type;//证件类型
	@ZteSoftCommentAnnotationParam(name="证件号码",type="String",isNecessary="N",desc="证件号码")
	private String cert_card_num;//证件号码
	@ZteSoftCommentAnnotationParam(name="民族",type="String",isNecessary="N",desc="民族")
	private String cert_nation;//民族
	@ZteSoftCommentAnnotationParam(name="证件机关",type="String",isNecessary="N",desc="证件机关")
	private String cert_issuer;//证件机关
	@ZteSoftCommentAnnotationParam(name="证件生效时间",type="String",isNecessary="N",desc="证件生效时间")
	private String cert_eff_time;//证件生效时间
	@ZteSoftCommentAnnotationParam(name="证件失效时间",type="String",isNecessary="N",desc="证件失效时间")
	private String cert_failure_time;//证件失效时间
	@ZteSoftCommentAnnotationParam(name="证件地址",type="String",isNecessary="N",desc="证件地址")
	private String cert_address;//证件地址
	@ZteSoftCommentAnnotationParam(name="证件性别",type="String",isNecessary="N",desc="证件性别")
	private String certi_sex;//证件性别
	@ZteSoftCommentAnnotationParam(name="情侣号码",type="String",isNecessary="N",desc="情侣号码")
	private String loves_phone_num;//情侣号码
	@ZteSoftCommentAnnotationParam(name="商品单价",type="String",isNecessary="N",desc="商品单价")
	private Double sell_price;//商品单价
	@ZteSoftCommentAnnotationParam(name="商品ID",type="String",isNecessary="N",desc="商品ID")
	private String goods_id;//商品ID
	@ZteSoftCommentAnnotationParam(name="条型码",type="String",isNecessary="N",desc="条型码")
	private String outer_sku_id;//条型码
	
	@ZteSoftCommentAnnotationParam(name="商品类型id",type="String",isNecessary="N",desc="商品类型id")
	private String type_id;//商品类型id
	@ZteSoftCommentAnnotationParam(name="发货仓库ID",type="String",isNecessary="Y",desc="发货仓库ID")
	private String house_id;
	@ZteSoftCommentAnnotationParam(name="外系统发货仓库ID",type="String",isNecessary="Y",desc="外系统发货仓库ID")
	private String out_house_id;
	@ZteSoftCommentAnnotationParam(name="组织ID",type="String",isNecessary="Y",desc="组织ID")
	private String org_id;
	@ZteSoftCommentAnnotationParam(name="出入境号",type="String",isNecessary="N",desc="出入境号")
    private String cert_num2;
	
	private String tid_category;//订单类别
	
	private Map<String,String> extMap;
	
	private List<AdjunctItem> adjunctList;//配件
	
	public String getTid_category() {
		return tid_category;
	}
	public void setTid_category(String tid_category) {
		this.tid_category = tid_category;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getPro_type() {
		return pro_type;
	}
	public void setPro_type(String pro_type) {
		this.pro_type = pro_type;
	}
	public String getIsgifts() {
		return isgifts;
	}
	public void setIsgifts(String isgifts) {
		this.isgifts = isgifts;
	}
	public String getBrand_number() {
		return brand_number;
	}
	public void setBrand_number(String brand_number) {
		this.brand_number = brand_number;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public Double getPro_origfee() {
		return pro_origfee;
	}
	public void setPro_origfee(Double pro_origfee) {
		this.pro_origfee = pro_origfee;
	}
	public String getColor_code() {
		return color_code;
	}
	public void setColor_code(String color_code) {
		this.color_code = color_code;
	}
	public String getColor_name() {
		return color_name;
	}
	public void setColor_name(String color_name) {
		this.color_name = color_name;
	}
	public String getSpecification_code() {
		return specification_code;
	}
	public void setSpecification_code(String specification_code) {
		this.specification_code = specification_code;
	}
	public String getSpecificatio_nname() {
		return specificatio_nname;
	}
	public void setSpecificatio_nname(String specificatio_nname) {
		this.specificatio_nname = specificatio_nname;
	}
	public String getModel_code() {
		return model_code;
	}
	public void setModel_code(String model_code) {
		this.model_code = model_code;
	}
	public String getModel_name() {
		return model_name;
	}
	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}
	public String getTerminal_num() {
		return terminal_num;
	}
	public void setTerminal_num(String terminal_num) {
		this.terminal_num = terminal_num;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getPhone_owner_name() {
		return phone_owner_name;
	}
	public void setPhone_owner_name(String phone_owner_name) {
		this.phone_owner_name = phone_owner_name;
	}
	public String getWhite_cart_type() {
		return white_cart_type;
	}
	public void setWhite_cart_type(String white_cart_type) {
		this.white_cart_type = white_cart_type;
	}
	public String getFirst_payment() {
		return first_payment;
	}
	public void setFirst_payment(String first_payment) {
		this.first_payment = first_payment;
	}
	public String getCollection() {
		return collection;
	}
	public void setCollection(String collection) {
		this.collection = collection;
	}
	public String getOut_package_id() {
		return out_package_id;
	}
	public void setOut_package_id(String out_package_id) {
		this.out_package_id = out_package_id;
	}
	public String getPlan_title() {
		return plan_title;
	}
	public void setPlan_title(String plan_title) {
		this.plan_title = plan_title;
	}
	public String getOut_plan_id_ess() {
		return out_plan_id_ess;
	}
	public void setOut_plan_id_ess(String out_plan_id_ess) {
		this.out_plan_id_ess = out_plan_id_ess;
	}
	public String getOut_plan_id_bss() {
		return out_plan_id_bss;
	}
	public void setOut_plan_id_bss(String out_plan_id_bss) {
		this.out_plan_id_bss = out_plan_id_bss;
	}
	public String getReliefpres_flag() {
		return reliefpres_flag;
	}
	public void setReliefpres_flag(String reliefpres_flag) {
		this.reliefpres_flag = reliefpres_flag;
	}
	public String getSimid() {
		return simid;
	}
	public void setSimid(String simid) {
		this.simid = simid;
	}
	public String getProduct_net() {
		return product_net;
	}
	public void setProduct_net(String product_net) {
		this.product_net = product_net;
	}
	public String getAct_protper() {
		return act_protper;
	}
	public void setAct_protper(String act_protper) {
		this.act_protper = act_protper;
	}
	public String getPhone_deposit() {
		return phone_deposit;
	}
	public void setPhone_deposit(String phone_deposit) {
		this.phone_deposit = phone_deposit;
	}
	public String getFamliy_num() {
		return famliy_num;
	}
	public void setFamliy_num(String famliy_num) {
		this.famliy_num = famliy_num;
	}
	public String getAdjustment_credit() {
		return adjustment_credit;
	}
	public void setAdjustment_credit(String adjustment_credit) {
		this.adjustment_credit = adjustment_credit;
	}
	public String getSuperiors_bankcode() {
		return superiors_bankcode;
	}
	public void setSuperiors_bankcode(String superiors_bankcode) {
		this.superiors_bankcode = superiors_bankcode;
	}
	public String getBank_code() {
		return bank_code;
	}
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	public String getBank_account() {
		return bank_account;
	}
	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}
	public String getBank_user() {
		return bank_user;
	}
	public void setBank_user(String bank_user) {
		this.bank_user = bank_user;
	}
	public String getIs_iphone_plan() {
		return is_iphone_plan;
	}
	public void setIs_iphone_plan(String is_iphone_plan) {
		this.is_iphone_plan = is_iphone_plan;
	}
	public String getIs_loves_phone() {
		return is_loves_phone;
	}
	public void setIs_loves_phone(String is_loves_phone) {
		this.is_loves_phone = is_loves_phone;
	}
	public String getIs_liang() {
		return is_liang;
	}
	public void setIs_liang(String is_liang) {
		this.is_liang = is_liang;
	}
	public Double getLiang_price() {
		return liang_price;
	}
	public void setLiang_price(Double liang_price) {
		this.liang_price = liang_price;
	}
	public String getLiang_code() {
		return liang_code;
	}
	public void setLiang_code(String liang_code) {
		this.liang_code = liang_code;
	}
	public String getIs_stock_phone() {
		return is_stock_phone;
	}
	public void setIs_stock_phone(String is_stock_phone) {
		this.is_stock_phone = is_stock_phone;
	}
	public String getAtive_type() {
		return ative_type;
	}
	public void setAtive_type(String ative_type) {
		this.ative_type = ative_type;
	}
	public String getDisfee_select() {
		return disfee_select;
	}
	public void setDisfee_select(String disfee_select) {
		this.disfee_select = disfee_select;
	}
	public String getSociety_name() {
		return society_name;
	}
	public void setSociety_name(String society_name) {
		this.society_name = society_name;
	}
	public Double getSociety_price() {
		return society_price;
	}
	public void setSociety_price(Double society_price) {
		this.society_price = society_price;
	}
	/*public String getPro_detail_code() {
		return pro_detail_code;
	}
	public void setPro_detail_code(String pro_detail_code) {
		this.pro_detail_code = pro_detail_code;
	}*/
	public String getCert_type() {
		return cert_type;
	}
	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}
	public String getCert_card_num() {
		return cert_card_num;
	}
	public void setCert_card_num(String cert_card_num) {
		this.cert_card_num = cert_card_num;
	}
	public String getCert_nation() {
		return cert_nation;
	}
	public void setCert_nation(String cert_nation) {
		this.cert_nation = cert_nation;
	}
	public String getCert_issuer() {
		return cert_issuer;
	}
	public void setCert_issuer(String cert_issuer) {
		this.cert_issuer = cert_issuer;
	}
	public String getCert_eff_time() {
		return cert_eff_time;
	}
	public void setCert_eff_time(String cert_eff_time) {
		this.cert_eff_time = cert_eff_time;
	}
	public String getLoves_phone_num() {
		return loves_phone_num;
	}
	public void setLoves_phone_num(String loves_phone_num) {
		this.loves_phone_num = loves_phone_num;
	}
	public Double getSell_price() {
		return sell_price;
	}
	public void setSell_price(Double sell_price) {
		this.sell_price = sell_price;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getOuter_sku_id() {
		return outer_sku_id;
	}
	public void setOuter_sku_id(String outer_sku_id) {
		this.outer_sku_id = outer_sku_id;
	}
	public String getCert_failure_time() {
		return cert_failure_time;
	}
	public void setCert_failure_time(String cert_failure_time) {
		this.cert_failure_time = cert_failure_time;
	}
	public String getCert_address() {
		return cert_address;
	}
	public void setCert_address(String cert_address) {
		this.cert_address = cert_address;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public Integer getPro_num() {
		return pro_num;
	}
	public void setPro_num(Integer pro_num) {
		this.pro_num = pro_num;
	}
	public String getHouse_id() {
		return house_id;
	}
	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}
	public String getOut_house_id() {
		return out_house_id;
	}
	public void setOut_house_id(String out_house_id) {
		this.out_house_id = out_house_id;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public Map<String,String> getExtMap() {
		return extMap;
	}
	public void setExtMap(Map<String,String> extMap) {
		this.extMap = extMap;
	}
	public List<AdjunctItem> getAdjunctList() {
		return adjunctList;
	}
	public void setAdjunctList(List<AdjunctItem> adjunctList) {
		this.adjunctList = adjunctList;
	}
	public String getCerti_sex(){
		return certi_sex;
	}
	public void setCerti_sex(String certi_sex){
		this.certi_sex = certi_sex;
	}
    public String getCert_num2() {
        return cert_num2;
    }
    public void setCert_num2(String cert_num2) {
        this.cert_num2 = cert_num2;
    }
}
