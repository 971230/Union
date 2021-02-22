/**
 * 
 */
package com.ztesoft.net.server.jsonserver.zbpojo;

/**
 * @author ZX
 * @version 2016-01-08
 * @see 附加产品可选包
 * 
 */
public class CenterMallAttrPackageSubProd {

	private String package_inst_id; // 主键
	private String order_id; // 订单编号
	private String subprod_inst_id; // 关联附加产品主键
	private String package_code;
	private String package_name;
	private String product_code;
	private String element_code;
	private String element_type;
	private String element_name;
	private String oper_type;
	private String change_type;
	private String biz_type;
	private String source_from;
	
	public String getPackage_inst_id() {
		return package_inst_id;
	}
	public void setPackage_inst_id(String package_inst_id) {
		this.package_inst_id = package_inst_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getSubprod_inst_id() {
		return subprod_inst_id;
	}
	public void setSubprod_inst_id(String subprod_inst_id) {
		this.subprod_inst_id = subprod_inst_id;
	}
	public String getPackage_code() {
		return package_code;
	}
	public void setPackage_code(String package_code) {
		this.package_code = package_code;
	}
	public String getPackage_name() {
		return package_name;
	}
	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getElement_code() {
		return element_code;
	}
	public void setElement_code(String element_code) {
		this.element_code = element_code;
	}
	public String getElement_type() {
		return element_type;
	}
	public void setElement_type(String element_type) {
		this.element_type = element_type;
	}
	public String getElement_name() {
		return element_name;
	}
	public void setElement_name(String element_name) {
		this.element_name = element_name;
	}
	public String getOper_type() {
		return oper_type;
	}
	public void setOper_type(String oper_type) {
		this.oper_type = oper_type;
	}
	public String getChange_type() {
		return change_type;
	}
	public void setChange_type(String change_type) {
		this.change_type = change_type;
	}
	public String getBiz_type() {
		return biz_type;
	}
	public void setBiz_type(String biz_type) {
		this.biz_type = biz_type;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

}
