package com.ztesoft.net.mall.core.model;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.ztesoft.net.framework.database.NotDbField;

/**
 * 商品实体
 * @author kingapex
 *2010-4-25下午09:40:24
 */
public class Goods implements java.io.Serializable {
	private static final long serialVersionUID = 808400934523865555L;
	private String goods_id; //商品ID
	private String prod_goods_id;
	private String product_id;  //货品ID，es_product表
	private String name; //商品名称
	private String sn;//条形码
	private String brand_id; //品牌ID
	private String brand_code;
	private String cat_id;//分类ID
	private String type_id;//类型ID
	private String stype_id;//服务类型ID
    private String sub_stype_id;//子服务类型ID
	private String goods_type; //enum('normal', 'bind') default 'normal'
	private String unit;//单位
	private Double weight;//重量
	private Integer market_enable;//是否上架，广东联通ECS：停用-0，启用-1
	private String image_default;//默认图片
	private String image_file;//图片
	private String brief;//简述
	private String intro;//描述
	private Double price;//销售价格
	private Double mktprice;//市场价格
	private Integer store;//库存
	private String adjuncts;//配件
	private String params;//参数，json格式
	private String specs;//规格，json格式
	private String create_time;//创建时间
	private String last_modify;//最后修改时间
	private Integer view_count;//浏览次数
	private Integer buy_count;//购买次数
	private Integer disabled;//删除标识，未删除-0，删除-1
	private String page_title;//商品页面标题，作SEO分析用
	private String meta_keywords;//页面关键词
	private String meta_description;//页面描述
	private Integer point; //积分
	private Integer sord;
	private String staff_no; //add by wui供应商标识、如果是电信内部员工，则为-1
	private String crm_offer_id;//CRM销售品ID
	private String service_type;
	private String type_code;//类型编码
	private String have_spec;//是否有规格
	private String search_key;//搜索关键字
	private Integer comm_num;//评论数
	private Double score;//评分
	private String p_code;//合约套餐关系编码
	private String barcode;
	private String source_from;
	private Integer publish_status; //发布状态
	private String rel_code;
	private String month_fee;
	private Double pay_price;
	private String act_code;  //总部的活动编码或商品编码（用于总部的商品映射es_goods_package.p_code）
	private String prod_code;  //总部的产品编码（用于总部的商品映射es_goods_package.sn）
	private String matnr;  //华盛物料号（用于华盛订单的商品映射es_goods_package.hs_matnr）
	private String is_select;
	private Integer ctn;
	private String oper_name;
	private String oper_no;
	private String oper_date;
	private String serial_no;
	
	//以下是扩展字段
	private Integer wait_audit;
	private Integer audit_y;
	private Integer audit_n;
	private Integer all_count;
	private Integer have_stock;
	private Integer have_price;
	private String state;
	private String apply_userid;//申请人id
	private String apply_username;//商品发布人
	private String type_name;//类型名称
	private String agent_name;//上架名称
	private String brand_name;//品牌名称
	private String cat_name;//分类名称
	private String lan_id;//
	private String userid;
	private Double cost;
	private String effective_area_flag;

	private String arrival;
	private String exp_date;

	private Long min_num;
	private Long week1_num;
	private Long week2_num;
	private Long week3_num;
	private Long week4_num;

	private Integer taxes_ratio;

	//商品创建人
	private String creator_user;
	private String creator_user_name;

	private String supper_id;//供货商id,记录商品供货人 by wui

	private String audit_state;//审批状态

	//private String total_qty; //数量个数
	
	private String apply_reson;
	
	private String simple_name; //产品简称
	private String bussiness_type;//经营方式
	private String produce_area;//产地
	private String purchase_area;//采购范围
	private String specifications;//规格
	
	private String effect_date;	//生效时间
	private String fail_date;	//失效时间
	
	private String start_date;
	private String end_date;
	
	private String model;//联通ECS：货品型号
	private String sku;//联通ECS：SKU
	private String model_code;//联通ECS：机型编码
	private String model_name;//机型名称
	private String machine_code; //型号编码
	private String machine_name; //型号名称
	private String type;//类型：货品product，商品goods
	private String color;//颜色编码
	private String color_name;//颜色名称
	
	private String role_type;//商品的售卖对象
	private String normal_price;//普通会员商品价格
	private String silver_price;//银级会员商品价格
	private String gold_price;//黄金级会员商品价格
	private String json;//存储商品规格
	
	private String pc_image_default; // 缺省图片
	private String pc_image_file; // 图片文件
	private String pc_remark;
	private String pc_intors;
	private String mbl_image_default; // 缺省图片
	private String mbl_image_file; // 图片文件
	private String mbl_remark;
	private String mbl_intors;
	private String wx_image_default; // 缺省图片
	private String wx_image_file; // 图片文件
	private String wx_remark;
	private String wx_intors;
	private String other_image_default; // 缺省图片
	private String other_image_file; // 图片文件
	private String other_remark;
	private String other_intors;
	
	private String  sale_store  ;//售卖商城
	
	private String cat_path;
		
	@NotDbField
	public String getAct_code() {
		return act_code;
	}

	@NotDbField
	public void setAct_code(String act_code) {
		this.act_code = act_code;
	}

	@NotDbField
	public String getProd_code() {
		return prod_code;
	}

	@NotDbField
	public void setProd_code(String prod_code) {
		this.prod_code = prod_code;
	}
	
	public String getMonth_fee() {
		return month_fee;
	}

	public void setMonth_fee(String month_fee) {
		this.month_fee = month_fee;
	}

	public String getRel_code() {
		return rel_code;
	}

	public void setRel_code(String rel_code) {
		this.rel_code = rel_code;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Integer getComm_num() {
		return comm_num;
	}

	public void setComm_num(Integer comm_num) {
		this.comm_num = comm_num;
	}

	@NotDbField
	public Long getMin_num() {
		return min_num;
	}
	@NotDbField
	public void setMin_num(Long min_num) {
		this.min_num = min_num;
	}

	public String getSearch_key() {
		return search_key;
	}

	public void setSearch_key(String search_key) {
		this.search_key = search_key;
	}
	public Integer getCtn() {
		return ctn;
	}

	public void setCtn(Integer ctn) {
		this.ctn = ctn;
	}

	private String unitName;
	@NotDbField
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getHave_spec() {
		return have_spec;
	}

	public void setHave_spec(String have_spec) {
		this.have_spec = have_spec;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	@NotDbField
	public String getNormal_price() {
		return normal_price;
	}

	public void setNormal_price(String normal_price) {
		this.normal_price = normal_price;
	}
	@NotDbField
	public String getSilver_price() {
		return silver_price;
	}

	public void setSilver_price(String silver_price) {
		this.silver_price = silver_price;
	}
	@NotDbField
	public String getGold_price() {
		return gold_price;
	}

	public void setGold_price(String gold_price) {
		this.gold_price = gold_price;
	}

	@NotDbField
	public String getRole_type() {
		return role_type;
	}

	public void setRole_type(String role_type) {
		this.role_type = role_type;
	}

	public String getAudit_state() {
		return audit_state;
	}

	public void setAudit_state(String audit_state) {
		this.audit_state = audit_state;
	}

	public String getCreator_user() {
		return creator_user;
	}

	public void setCreator_user(String creator_user) {
		this.creator_user = creator_user;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getExp_date() {
		return exp_date;
	}

	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}

	public Integer getTaxes_ratio() {
		return taxes_ratio;
	}

	public void setTaxes_ratio(Integer taxes_ratio) {
		this.taxes_ratio = taxes_ratio;
	}

	public String getBrand_id() {
		if (brand_id == null)
			brand_id = "0";
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}
	@NotDbField
	public String getBrand_code() {
		return brand_code;
	}
	@NotDbField
	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public Integer getBuy_count() {
		return buy_count;
	}

	public void setBuy_count(Integer buy_count) {
		this.buy_count = buy_count;
	}

	public Integer getDisabled() {
		return disabled;
	}

	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getImage_default() {
		return image_default;
	}

	public void setImage_default(String image_default) {
		this.image_default = image_default;
	}

	public String getImage_file() {
		return image_file;
	}

	public void setImage_file(String image_file) {
		this.image_file = image_file;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Integer getMarket_enable() {
		return market_enable;
	}

	public void setMarket_enable(Integer market_enable) {
		this.market_enable = market_enable;
	}

	public String getMeta_description() {
		return meta_description;
	}

	public void setMeta_description(String meta_description) {
		this.meta_description = meta_description;
	}

	public String getMeta_keywords() {
		return meta_keywords;
	}

	public void setMeta_keywords(String meta_keywords) {
		this.meta_keywords = meta_keywords;
	}

	public Double getMktprice() {
		return mktprice;
	}

	public void setMktprice(Double mktprice) {
		this.mktprice = mktprice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPage_title() {
		return page_title;
	}

	public void setPage_title(String page_title) {
		this.page_title = page_title;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(String goodsType) {
		goods_type = goodsType;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getView_count() {
		return view_count;
	}

	public void setView_count(Integer view_count) {
		this.view_count = view_count;
	}

	public Double getWeight() {
		weight = weight == null ? weight = 0D : weight;
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getCat_id() {
		return cat_id;
	}

	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getLast_modify() {
		return last_modify;
	}

	public void setLast_modify(String last_modify) {
		this.last_modify = last_modify;
	}

	public String getSpecs() {
		return specs;
	}

	public void setSpecs(String specs) {
		this.specs = specs;
	}

	public String getAdjuncts() {
		return adjuncts;
	}

	public void setAdjuncts(String adjuncts) {
		this.adjuncts = adjuncts;
	}

	public Integer getPoint() {
		point = point == null ? 0 : point;
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getSord() {
		return sord;
	}

	public void setSord(Integer sord) {
		this.sord = sord;
	}

	public String getStaff_no() {
		return staff_no;
	}

	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getCrm_offer_id() {
		return crm_offer_id;
	}

	public void setCrm_offer_id(String crm_offer_id) {
		this.crm_offer_id = crm_offer_id;
	}

	@NotDbField
	public Integer getWait_audit() {
		return wait_audit;
	}

	public void setWait_audit(Integer wait_audit) {
		this.wait_audit = wait_audit;
	}

	@NotDbField
	public Integer getAudit_y() {
		return audit_y;
	}

	public void setAudit_y(Integer audit_y) {
		this.audit_y = audit_y;
	}

	@NotDbField
	public Integer getAudit_n() {
		return audit_n;
	}

	public void setAudit_n(Integer audit_n) {
		this.audit_n = audit_n;
	}

	@NotDbField
	public Integer getAll_count() {
		return all_count;
	}

	public void setAll_count(Integer all_count) {
		this.all_count = all_count;
	}

	@NotDbField
	public Integer getHave_stock() {
		return have_stock;
	}

	public void setHave_stock(Integer have_stock) {
		this.have_stock = have_stock;
	}

	@NotDbField
	public Integer getHave_price() {
		return have_price;
	}

	public void setHave_price(Integer have_price) {
		this.have_price = have_price;
	}

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	@NotDbField
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@NotDbField
	public String getApply_userid() {
		return apply_userid;
	}

	public void setApply_userid(String apply_userid) {
		this.apply_userid = apply_userid;
	}

	@NotDbField
	public String getApply_username() {
		return apply_username;
	}
	@NotDbField
	public void setApply_username(String apply_username) {
		this.apply_username = apply_username;
	}

	@NotDbField
	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	@NotDbField
	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;

	}

	@NotDbField
	public String getLan_id() {
		return lan_id;
	}

	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}

	@NotDbField
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getEffective_area_flag() {
		return effective_area_flag;
	}

	public void setEffective_area_flag(String effective_area_flag) {
		this.effective_area_flag = effective_area_flag;
	}

	public String getSupper_id() {
		return supper_id;
	}

	public void setSupper_id(String supper_id) {
		this.supper_id = supper_id;
	}

	public String getApply_reson() {
		return apply_reson;
	}

	public void setApply_reson(String apply_reson) {
		this.apply_reson = apply_reson;
	}

	public String getSimple_name() {
		return simple_name;
	}

	public void setSimple_name(String simple_name) {
		this.simple_name = simple_name;
	}

	public String getBussiness_type() {
		return bussiness_type;
	}

	public void setBussiness_type(String bussiness_type) {
		this.bussiness_type = bussiness_type;
	}

	public String getProduce_area() {
		return produce_area;
	}

	public void setProduce_area(String produce_area) {
		this.produce_area = produce_area;
	}

	public String getPurchase_area() {
		return purchase_area;
	}

	public void setPurchase_area(String purchase_area) {
		this.purchase_area = purchase_area;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public String getStype_id() {
		return stype_id;
	}

	public void setStype_id(String stype_id) {
		this.stype_id = stype_id;
	}

	public String getEffect_date() {
		return effect_date;
	}

	public void setEffect_date(String effect_date) {
		this.effect_date = effect_date;
	}

	public String getFail_date() {
		return fail_date;
	}

	public void setFail_date(String fail_date) {
		this.fail_date = fail_date;
	}
	
	@NotDbField
	public String getStart_date() {
		return start_date;
	}
	@NotDbField
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	@NotDbField
	public String getEnd_date() {
		return end_date;
	}
	@NotDbField
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

    public String getSub_stype_id() {
        return sub_stype_id;
    }

    public void setSub_stype_id(String sub_stype_id) {
        this.sub_stype_id = sub_stype_id;
    }

    @NotDbField
	public String getIs_select() {
		return is_select;
	}
    @NotDbField
	public void setIs_select(String is_select) {
		this.is_select = is_select;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getModel_code() {
		return model_code;
	}

	public void setModel_code(String model_code) {
		this.model_code = model_code;
	}
	@NotDbField
	public String getModel_name() {
		return model_name;
	}
	@NotDbField
	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}
	@NotDbField
	public String getMachine_code() {
		return machine_code;
	}
	@NotDbField
	public void setMachine_code(String machine_code) {
		this.machine_code = machine_code;
	}
	@NotDbField
	public String getMachine_name() {
		return machine_name;
	}
	@NotDbField
	public void setMachine_name(String machine_name) {
		this.machine_name = machine_name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getCat_name() {
		return cat_name;
	}

	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}

	private String p1;
	private String p2;
	private String p3;
	private String p4;
	private String p5;
	private String p6;
	private String p7;
	private String p8;
	private String p9;
	private String p10;
	private String p11;
	private String p12;
	private String p13;
	private String p14;
	private String p15;
	private String p16;
	private String p17;
	private String p18;
	private String p19;
	private String p20;

	public String getP1() {
		return p1;
	}

	public void setP1(String p1) {
		this.p1 = p1;
	}

	public String getP2() {
		return p2;
	}

	public void setP2(String p2) {
		this.p2 = p2;
	}

	public String getP3() {
		return p3;
	}

	public void setP3(String p3) {
		this.p3 = p3;
	}

	public String getP4() {
		return p4;
	}

	public void setP4(String p4) {
		this.p4 = p4;
	}

	public String getP5() {
		return p5;
	}

	public void setP5(String p5) {
		this.p5 = p5;
	}

	public String getP6() {
		return p6;
	}

	public void setP6(String p6) {
		this.p6 = p6;
	}

	public String getP7() {
		return p7;
	}

	public void setP7(String p7) {
		this.p7 = p7;
	}

	public String getP8() {
		return p8;
	}

	public void setP8(String p8) {
		this.p8 = p8;
	}

	public String getP9() {
		return p9;
	}

	public void setP9(String p9) {
		this.p9 = p9;
	}

	public String getP10() {
		return p10;
	}

	public void setP10(String p10) {
		this.p10 = p10;
	}

	public String getP11() {
		return p11;
	}

	public void setP11(String p11) {
		this.p11 = p11;
	}

	public String getP12() {
		return p12;
	}

	public void setP12(String p12) {
		this.p12 = p12;
	}

	public String getP13() {
		return p13;
	}

	public void setP13(String p13) {
		this.p13 = p13;
	}

	public String getP14() {
		return p14;
	}

	public void setP14(String p14) {
		this.p14 = p14;
	}

	public String getP15() {
		return p15;
	}

	public void setP15(String p15) {
		this.p15 = p15;
	}

	public String getP16() {
		return p16;
	}

	public void setP16(String p16) {
		this.p16 = p16;
	}

	public String getP17() {
		return p17;
	}

	public void setP17(String p17) {
		this.p17 = p17;
	}

	public String getP18() {
		return p18;
	}

	public void setP18(String p18) {
		this.p18 = p18;
	}

	public String getP19() {
		return p19;
	}

	public void setP19(String p19) {
		this.p19 = p19;
	}

	public String getP20() {
		return p20;
	}

	public void setP20(String p20) {
		this.p20 = p20;
	}

	@NotDbField
	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	@NotDbField
	public String getP_code() {
		return p_code;
	}

	public void setP_code(String p_code) {
		this.p_code = p_code;
	}

	@NotDbField
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	@NotDbField
	public String getColor_name() {
		return color_name;
	}
	@NotDbField
	public void setColor_name(String color_name) {
		this.color_name = color_name;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	@NotDbField
	public Integer getPublish_status() {
		return publish_status;
	}

	public void setPublish_status(Integer publish_status) {
		this.publish_status = publish_status;
	}

	public Double getPay_price() {
		return pay_price;
	}

	public void setPay_price(Double pay_price) {
		this.pay_price = pay_price;
	}

	@NotDbField
	public String getProd_goods_id() {
		return prod_goods_id;
	}
	
	public void setProd_goods_id(String prod_goods_id) {
		this.prod_goods_id = prod_goods_id;
	}
	@NotDbField
	public String getPc_image_default() {
		return pc_image_default;
	}

	public void setPc_image_default(String pc_image_default) {
		this.pc_image_default = pc_image_default;
	}
	@NotDbField
	public String getPc_image_file() {
		return pc_image_file;
	}

	public void setPc_image_file(String pc_image_file) {
		this.pc_image_file = pc_image_file;
	}
	@NotDbField
	public String getPc_remark() {
		return pc_remark;
	}

	public void setPc_remark(String pc_remark) {
		this.pc_remark = pc_remark;
	}
	@NotDbField
	public String getPc_intors() {
		return pc_intors;
	}

	public void setPc_intors(String pc_intors) {
		this.pc_intors = pc_intors;
	}

	@NotDbField
	public String getMbl_image_default() {
		return mbl_image_default;
	}

	public void setMbl_image_default(String mbl_image_default) {
		this.mbl_image_default = mbl_image_default;
	}
	@NotDbField
	public String getMbl_image_file() {
		return mbl_image_file;
	}

	public void setMbl_image_file(String mbl_image_file) {
		this.mbl_image_file = mbl_image_file;
	}
	@NotDbField
	public String getMbl_remark() {
		return mbl_remark;
	}

	public void setMbl_remark(String mbl_remark) {
		this.mbl_remark = mbl_remark;
	}
	@NotDbField
	public String getMbl_intors() {
		return mbl_intors;
	}

	public void setMbl_intors(String mbl_intors) {
		this.mbl_intors = mbl_intors;
	}

	@NotDbField
	public String getWx_image_default() {
		return wx_image_default;
	}

	public void setWx_image_default(String wx_image_default) {
		this.wx_image_default = wx_image_default;
	}
	@NotDbField
	public String getWx_image_file() {
		return wx_image_file;
	}

	public void setWx_image_file(String wx_image_file) {
		this.wx_image_file = wx_image_file;
	}
	@NotDbField
	public String getWx_remark() {
		return wx_remark;
	}

	public void setWx_remark(String wx_remark) {
		this.wx_remark = wx_remark;
	}
	@NotDbField
	public String getWx_intors() {
		return wx_intors;
	}

	public void setWx_intors(String wx_intors) {
		this.wx_intors = wx_intors;
	}

	@NotDbField
	public String getOther_image_default() {
		return other_image_default;
	}

	public void setOther_image_default(String other_image_default) {
		this.other_image_default = other_image_default;
	}
	@NotDbField
	public String getOther_image_file() {
		return other_image_file;
	}

	public void setOther_image_file(String other_image_file) {
		this.other_image_file = other_image_file;
	}
	@NotDbField
	public String getOther_remark() {
		return other_remark;
	}

	public void setOther_remark(String other_remark) {
		this.other_remark = other_remark;
	}
	@NotDbField
	public String getOther_intors() {
		return other_intors;
	}

	public void setOther_intors(String other_intors) {
		this.other_intors = other_intors;
	}

	public String getCreator_user_name() {
		return creator_user_name;
	}
	
	@NotDbField
	public void setCreator_user_name(String creator_user_name) {
		this.creator_user_name = creator_user_name;
	}
	
	public String getSale_store() {
		return sale_store;
	}

	public void setSale_store(String sale_store) {
		this.sale_store = sale_store;
	}

	
	public String getOper_name() {
		return oper_name;
	}

	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}

	public String getOper_no() {
		return oper_no;
	}

	public void setOper_no(String oper_no) {
		this.oper_no = oper_no;
	}

	public String getOper_date() {
		return oper_date;
	}

	public void setOper_date(String oper_date) {
		this.oper_date = oper_date;
	}

	public String getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	public Map BeanToMap(){
		Map map = new HashedMap();
		map.put("goods_id",this.goods_id);
		map.put("prod_goods_id",this.prod_goods_id);
		map.put("product_id",this.product_id);
		map.put("name",this.name);
		map.put("sn",this.sn);
		map.put("brand_id",this.brand_id);
		map.put("brand_code",this.brand_code);
		map.put("cat_id",this.cat_id);
		map.put("type_id",this.type_id);
		map.put("stype_id",this.stype_id);
	    map.put("sub_stype_id",this.sub_stype_id);
		map.put("goods_type",this.goods_type);
		map.put("unit",this.unit);
		map.put("weight",this.weight);
		map.put("market_enable",this.market_enable);
		map.put("image_default",this.image_default);
		map.put("image_file",this.image_file);
		map.put("brief",this.brief);
		map.put("intro",this.intro);
		map.put("price",this.price);
		map.put("mktprice",this.mktprice);
		map.put("store",this.store);
		map.put("adjuncts",this.adjuncts);
		map.put("params",this.params);
		map.put("specs",this.specs);
		map.put("create_time",this.create_time);
		map.put("last_modify",this.last_modify);
		map.put("view_count",this.view_count);
		map.put("buy_count",this.buy_count);
		map.put("disabled",this.disabled);
		map.put("page_title",this.page_title);
		map.put("meta_keywords",this.meta_keywords);
		map.put("meta_description",this.meta_description);
		map.put("point",this.point);
		map.put("sord",this.sord);
		map.put("staff_no",this.staff_no);
		map.put("crm_offer_id",this.crm_offer_id);
		map.put("service_type",this.service_type);
		map.put("type_code",this.type_code);
		map.put("have_spec",this.have_spec);
		map.put("search_key",this.search_key);
		map.put("comm_num",this.comm_num);
		map.put("score",this.score);
		map.put("p_code",this.p_code);
		map.put("barcode",this.barcode);
		map.put("source_from",this.source_from);
		map.put("publish_status",this.publish_status);
		map.put("rel_code",this.rel_code);
		map.put("month_fee",this.month_fee);
		map.put("pay_price",this.pay_price);
		map.put("act_code",this.act_code);
		map.put("prod_code",this.prod_code);
		map.put("is_select",this.is_select);
		map.put("ctn",this.ctn);
		map.put("wait_audit",this.wait_audit);
		map.put("audit_y",this.audit_y);
		map.put("audit_n",this.audit_n);
		map.put("all_count",this.all_count);
		map.put("have_stock",this.have_stock);
		map.put("have_price",this.have_price);
		map.put("state",this.state);
		map.put("apply_userid",this.apply_userid);
		map.put("apply_username",this.apply_username);
		map.put("type_name",this.type_name);
		map.put("agent_name",this.agent_name);
		map.put("brand_name",this.brand_name);
		map.put("cat_name",this.cat_name);
		map.put("lan_id",this.lan_id);
		map.put("userid",this.userid);
		map.put("cost",this.cost);
		map.put("effective_area_flag",this.effective_area_flag);
		map.put("arrival",this.arrival);
		map.put("exp_date",this.exp_date);
		map.put("min_num",this.min_num);
		map.put("week1_num",this.week1_num);
		map.put("week2_num",this.week2_num);
		map.put("week3_num",this.week3_num);
		map.put("week4_num",this.week4_num);
		map.put("taxes_ratio",this.taxes_ratio);
		map.put("creator_user",this.creator_user);
		map.put("creator_user_name",this.creator_user_name);
		map.put("supper_id",this.supper_id);
		map.put("audit_state",this.audit_state);
		map.put("apply_reson",this.apply_reson);
		map.put("simple_name",this.simple_name);
		map.put("bussiness_type",this.bussiness_type);
		map.put("produce_area",this.produce_area);
		map.put("purchase_area",this.purchase_area);
		map.put("specifications",this.specifications);
		map.put("effect_date",this.effect_date);
		map.put("fail_date",this.fail_date);
		map.put("start_date",this.start_date);
		map.put("end_date",this.end_date);
		map.put("model",this.model);
		map.put("sku",this.sku);
		map.put("model_code",this.model_code);
		map.put("model_name",this.model_name);
		map.put("machine_code",this.machine_code);
		map.put("machine_name",this.machine_name);
		map.put("type",this.type);
		map.put("color",this.color);
		map.put("color_name",this.color_name);
		map.put("role_type",this.role_type);
		map.put("normal_price",this.normal_price);
		map.put("silver_price",this.silver_price);
		map.put("gold_price",this.gold_price);
		map.put("json",this.json);
		map.put("pc_image_default",this.pc_image_default);
		map.put("pc_image_file",this.pc_image_file);
		map.put("pc_remark",this.pc_remark);
		map.put("pc_intors",this.pc_intors);
		map.put("mbl_image_default",this.mbl_image_default);
		map.put("mbl_image_file",this.mbl_image_file);
		map.put("mbl_remark",this.mbl_remark);
		map.put("mbl_intors",this.mbl_intors);
		map.put("wx_image_default",this.wx_image_default);
		map.put("wx_image_file",this.wx_image_file);
		map.put("wx_remark",this.wx_remark);
		map.put("wx_intors",this.wx_intors);
		map.put("other_image_default",this.other_image_default);
		map.put("other_image_file",this.other_image_file);
		map.put("other_remark",this.other_remark);
		map.put("other_intors",this.other_intors);
		map.put("sale_store",this.sale_store);
		map.put("oper_no",this.oper_no);
		map.put("oper_name",this.oper_name);
		map.put("oper_date",this.oper_date);
		map.put("sale_store",this.sale_store);
		map.put("cat_path", this.cat_path);
		map.put("matnr",this.matnr);
		return map;
	}

	public String getCat_path() {
		return cat_path;
	}

	public void setCat_path(String cat_path) {
		this.cat_path = cat_path;
	}


	@NotDbField
	public String getMatnr() {
		return matnr;
	}

	@NotDbField
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	
}