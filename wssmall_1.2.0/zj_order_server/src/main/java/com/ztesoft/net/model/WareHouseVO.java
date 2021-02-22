package com.ztesoft.net.model;

public class WareHouseVO {

	private String v_house_id;//虚拟仓编码
	private String l_house_id;//逻辑仓编码
	private String p_house_id;//物理仓编码
	private String scope_code;//逻辑仓配送地市范围
	private String product_id;//产品编码
	private String is_owner;//是否自营  
	private String purchase_price;//进货价格
	private String priority;//优先级
	
	public String getV_house_id(){
		return v_house_id;
	}
	public void setV_house_id(String v_house_id){
		this.v_house_id = v_house_id;
	}
	
	public String getL_house_id(){
		return l_house_id;
	}
	public void setL_house_id(String l_house_id){
		this.l_house_id = l_house_id;
	}
	
	public String getP_house_id(){
		return p_house_id;
	}
	public void setP_house_id(String p_house_id){
		this.p_house_id = p_house_id;
	}
	
	public String getScope_code(){
		return this.scope_code;
	}
	public void setScope_code(String scope_code){
		this.scope_code = scope_code;
	}
	
	public String getProduct_id(){
		return this.product_id;
	}
	public void setProduct_id(String product_id){
		this.product_id = product_id;
	}
	
	public String getIs_owner(){
		return this.is_owner;
	}
	public void setIs_owner(String is_owner){
		this.is_owner = is_owner;
	}
	
	public String getPurchase_price(){
		return purchase_price;
	}
	public void setPurchase_price(String purchase_price){
		this.purchase_price = purchase_price;
	}
	
	public String getPriority(){
		return priority;
	}
	public void setPriority(String priority){
		this.priority = priority;
	}
}
