package com.ztesoft.net.mall.core.model;



/**
 * @Description 开放服务配置实体类ES_OPEN_SERVICE_CFG
 * @author  zhangJun
 * @date    2015-3-11
 * @version 1.0
 */
public class OrderCfgInfo implements java.io.Serializable {
	

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4268587620183590948L;
	private  OpenServiceCfg openServiceCfg;
	private  String co_id="";//传参数用 队列id
	private  String order_id="";//传参数用 订单id
	
	public   OpenServiceCfg getOpenServiceCfg() {
		return openServiceCfg;
	}
	public void setOpenServiceCfg(OpenServiceCfg openServiceCfg) {
		this.openServiceCfg = openServiceCfg;
	}
	public String getCo_id() {
		return co_id;
	}
	public void setCo_id(String co_id) {
		this.co_id = co_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	
	
	
	
	
}
