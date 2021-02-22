package com.ztesoft.net.mall.core.model;

import java.io.Serializable;
/**
 * 订单仓库关联表，用于记录订单从哪个仓库出货
 * @author ricky
 *
 */
public class OrderWarehouseRef implements Serializable{
	private String ref_id ;
	private String order_id ;
	private String goods_id ;
	private Integer goods_num ;//商品出库数量
	private String phy_house_id ;
	private String log_house_id ;
	private String virt_house_id;
	private Integer status ;//出库状态 1：预出库 2：出库 3：取消出库
	public String getRef_id() {
		return ref_id;
	}
	public void setRef_id(String ref_id) {
		this.ref_id = ref_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public Integer getGoods_num() {
		return goods_num;
	}
	public void setGoods_num(Integer goods_num) {
		this.goods_num = goods_num;
	}
	public String getPhy_house_id() {
		return phy_house_id;
	}
	public void setPhy_house_id(String phy_house_id) {
		this.phy_house_id = phy_house_id;
	}
	public String getLog_house_id() {
		return log_house_id;
	}
	public void setLog_house_id(String log_house_id) {
		this.log_house_id = log_house_id;
	}
	public String getVirt_house_id() {
		return virt_house_id;
	}
	public void setVirt_house_id(String virt_house_id) {
		this.virt_house_id = virt_house_id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
