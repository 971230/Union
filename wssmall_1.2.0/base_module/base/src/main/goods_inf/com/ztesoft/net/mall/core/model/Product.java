package com.ztesoft.net.mall.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.net.framework.database.NotDbField;

/**
 * 货品实体
 * @author kingapex
 *2010-3-9下午05:57:16
 */
public class Product implements Serializable {

	private String product_id;
	private String goods_id;
	private String name;
	private String sn;
	private Integer store;
	private Double price;
	private Double cost;
	private Double weight;
	private String specs;
	private List<GoodsLvPrice> goodsLvPrices; //商品会员价格
	private String crm_offer_id;	//关联外系统商品id
	private String is_select;		//判断是否已选
	
	private String start_time;
	private String end_time;
	
	private String haveSpec ;//是否开启规则
	
	private String sku;
	private String type;//商品goods,货品product
	private String color;
	private String rel_code;//与商品关联的关联编码
	
	public String getRel_code() {
		return rel_code;
	}

	public void setRel_code(String rel_code) {
		this.rel_code = rel_code;
	}

	//货品对应规格信息，在添加货品时用到了
	private List<SpecValue> specList;
	
	public Product(){
		specList= new ArrayList();
	}
	
	@NotDbField
	public List<SpecValue> getSpecList() {
		return specList;
	}
	public void setSpecList(List<SpecValue> specList) {
		this.specList = specList;
	}
	
	//添加一种规格
	public void addSpec(SpecValue spec){
		this.specList.add(spec);
	}
	
	/**
	 * 返回根据 specList得到的 specvid json
	 * @return
	 */
	@NotDbField
	public String getSpecsvIdJson(){
		String json="[";
		int i=0;
		for(SpecValue value :specList){
			if(i!=0)json+=",";
			json+=value.getSpec_value_id();
			i++;
		}
		json+="]";
		return json;
	}
	
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String productId) {
		product_id = productId;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goodsId) {
		goods_id = goodsId;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Integer getStore() {
		return store;
	}
	public void setStore(Integer store) {
		this.store = store;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getSpecs() {
		return specs;
	}

	public void setSpecs(String specs) {
		this.specs = specs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotDbField
	public List<GoodsLvPrice> getGoodsLvPrices() {
		return goodsLvPrices;
	}

	public void setGoodsLvPrices(List<GoodsLvPrice> goodsLvPrices) {
		this.goodsLvPrices = goodsLvPrices;
	}

	@NotDbField
	public String getHaveSpec() {
		return haveSpec;
	}

	public void setHaveSpec(String haveSpec) {
		this.haveSpec = haveSpec;
	}

	@NotDbField
	public String getCrm_offer_id() {
		return crm_offer_id;
	}

	@NotDbField
	public void setCrm_offer_id(String crm_offer_id) {
		this.crm_offer_id = crm_offer_id;
	}

	@NotDbField
	public String getIs_select() {
		return is_select;
	}

	@NotDbField
	public void setIs_select(String is_select) {
		this.is_select = is_select;
	}

	public String getStart_time() {
		return start_time;
	}
	@NotDbField
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}
	@NotDbField
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
}
