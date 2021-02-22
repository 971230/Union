package com.ztesoft.net.mall.core.model;

/**
 * 商品使用业务
 * 
 * @author wui
 */
public class GoodsUsage implements java.io.Serializable {

	private String usageid;
	private String userid;
	private String goods_id;
	private String state;
	private String create_date;
	private String state_date;
	private String usagetype;
	private String block_reason;
	private Integer stock_num;//库存
	private Integer baseline_num;//阀值[底线值]
	public String getUsageid() {
		return usageid;
	}
	public String getBlock_reason() {
		return block_reason;
	}
	public void setBlock_reason(String block_reason) {
		this.block_reason = block_reason;
	}
	public void setUsageid(String usageid) {
		this.usageid = usageid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getState_date() {
		return state_date;
	}
	public void setState_date(String state_date) {
		this.state_date = state_date;
	}
	public String getUsagetype() {
		return usagetype;
	}
	public void setUsagetype(String usagetype) {
		this.usagetype = usagetype;
	}
	public Integer getStock_num() {
		return stock_num;
	}
	public void setStock_num(Integer stock_num) {
		this.stock_num = stock_num;
	}
	public Integer getBaseline_num() {
		return baseline_num;
	}
	public void setBaseline_num(Integer baseline_num) {
		this.baseline_num = baseline_num;
	}
	
	

}