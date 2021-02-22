package com.ztesoft.net.app.base.core.model;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.model.Product;

import java.util.List;



/**
 * 会员级别
 * @author kingapex
 *
 */
public class MemberLv  implements java.io.Serializable {


    // Fields 
	
	public MemberLv(){};
	public MemberLv(String lv_id, String name){
		this.lv_id = lv_id;
		this.name = name;
	}
	@ZteSoftCommentAnnotationParam(name="会员等级ID",type="String",isNecessary="Y",desc="会员等级ID")
     private String lv_id;
	@ZteSoftCommentAnnotationParam(name="会员等级名称",type="String",isNecessary="Y",desc="会员等级名称")
     private String name;
     private Integer default_lv;
     @ZteSoftCommentAnnotationParam(name="会员等级折扣",type="String",isNecessary="Y",desc="会员等级折扣")
     private Integer discount;
     private Double lvPrice;
     private int point;
     private String showname;
     private boolean selected;
     
     private List<Product> productList;
     @NotDbField
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	public Integer getDefault_lv() {
		return default_lv;
	}
	public void setDefault_lv(Integer default_lv) {
		this.default_lv = default_lv;
	}
	public String getLv_id() {
		return lv_id;
	}
	public void setLv_id(String lv_id) {
		this.lv_id = lv_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@NotDbField
	public boolean getSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	
	@NotDbField
	public Double getLvPrice() {
		return lvPrice;
	}
	public void setLvPrice(Double lvPrice) {
		this.lvPrice = lvPrice;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getShowname() {
		return showname;
	}
	public void setShowname(String showname) {
		this.showname = showname;
	} 

}