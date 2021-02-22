package com.ztesoft.net.mall.core.model;



/**
 * Activity generated by MyEclipse - Hibernate Tools
 */

public class GoodsCo  implements java.io.Serializable {


    private String id;  //发布标识
    private String goods_id;  //商品标识
    private String org_id;  //销售组织
    private String oper_id;  //发布工号
    private String created_date;  //发布时间
    private Integer status;  //发布状态
    private String status_date;  //状态时间
    private String source_from;  //数据来源
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getOper_id() {
		return oper_id;
	}
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatus_date() {
		return status_date;
	}
	public void setStatus_date(String status_date) {
		this.status_date = status_date;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
     
}