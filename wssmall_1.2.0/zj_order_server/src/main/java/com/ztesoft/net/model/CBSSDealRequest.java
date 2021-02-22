package com.ztesoft.net.model;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import params.ZteResponse;

/**
 * cbss业务办理入参
 * @author 吴家勇
 * 2015.01.22
 */
public class CBSSDealRequest extends ZteRequest<ZteResponse> {
	//a.gift_inst_id,a.order_id,a.inst_id,a.goods_name,a.bss_status
	private String gift_inst_id;
	private String order_id;
	private String inst_id;
	private String goods_name;
	private String bss_status;

	public String getGift_inst_id(){
		return gift_inst_id;
	}
	public void setGift_inst_id(String gift_inst_id){
		this.gift_inst_id = gift_inst_id;
	}
	public String getOrder_id(){
		return order_id ;
	}
	public void setOrder_id(String order_id){
		this.order_id = order_id;
	}
	public String getInst_id(){
		return inst_id;
	}
	public void setInst_id(String inst_id){
		this.inst_id = inst_id;
	}
	public String getGoods_name(){
		return goods_name;
	}
	public void setGoods_name(String goods_name){
		this.goods_name = goods_name;
	}
	public String getBss_status(){
		return bss_status;
	}
	public void setBss_status(String bss_status){
		this.bss_status = bss_status;
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "";
	}
}
