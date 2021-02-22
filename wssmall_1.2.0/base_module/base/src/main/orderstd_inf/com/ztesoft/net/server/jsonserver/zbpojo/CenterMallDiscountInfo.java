package com.ztesoft.net.server.jsonserver.zbpojo;

/*********************************************
 * 
 * @author duanshaochu
 * 优惠信息
 */
public class CenterMallDiscountInfo {

	//优惠活动类型
	private String discountType = "";
	//优惠活动编码
	private String discountID = "";
	//优惠活动名称
	private String discountName = "";
	//优惠金额，单位为厘
	private String discountValue = "";
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public String getDiscountID() {
		return discountID;
	}
	public void setDiscountID(String discountID) {
		this.discountID = discountID;
	}
	public String getDiscountName() {
		return discountName;
	}
	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}
	public String getDiscountValue() {
		return discountValue;
	}
	public void setDiscountValue(String discountValue) {
		this.discountValue = discountValue;
	}
	
	
	
	
}
