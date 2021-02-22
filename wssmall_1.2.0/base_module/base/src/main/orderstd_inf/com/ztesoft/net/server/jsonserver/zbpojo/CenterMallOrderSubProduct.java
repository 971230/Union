/**
 * 
 */
package com.ztesoft.net.server.jsonserver.zbpojo;

import java.util.List;

/**
 * @author ZX
 * @version 2016-01-08
 * @see 附加产品信息对应表
 * 
 */
public class CenterMallOrderSubProduct {

	private String sub_pro_inst_id; // 主键
	private String order_id; // 订单ID
	private String sub_pro_code; // 附加产品编码
	private String sub_pro_name; // 附加产品名称
	private String sub_pro_desc; // 附加产品说明
	private String sub_prod_type; // 产品类型(主副卡)
	private String prod_inst_id; // 关联产品主键
	private String source_from;
	private List<CenterMallAttrPackageSubProd> subPackage; // 附加产品可选包
	
	public String getSub_pro_inst_id() {
		return sub_pro_inst_id;
	}
	public void setSub_pro_inst_id(String sub_pro_inst_id) {
		this.sub_pro_inst_id = sub_pro_inst_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getSub_pro_code() {
		return sub_pro_code;
	}
	public void setSub_pro_code(String sub_pro_code) {
		this.sub_pro_code = sub_pro_code;
	}
	public String getSub_pro_name() {
		return sub_pro_name;
	}
	public void setSub_pro_name(String sub_pro_name) {
		this.sub_pro_name = sub_pro_name;
	}
	public String getSub_pro_desc() {
		return sub_pro_desc;
	}
	public void setSub_pro_desc(String sub_pro_desc) {
		this.sub_pro_desc = sub_pro_desc;
	}
	public String getSub_prod_type() {
		return sub_prod_type;
	}
	public void setSub_prod_type(String sub_prod_type) {
		this.sub_prod_type = sub_prod_type;
	}
	public String getProd_inst_id() {
		return prod_inst_id;
	}
	public void setProd_inst_id(String prod_inst_id) {
		this.prod_inst_id = prod_inst_id;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public List<CenterMallAttrPackageSubProd> getSubPackage() {
		return subPackage;
	}
	public void setSubPackage(List<CenterMallAttrPackageSubProd> subPackage) {
		this.subPackage = subPackage;
	}
}
