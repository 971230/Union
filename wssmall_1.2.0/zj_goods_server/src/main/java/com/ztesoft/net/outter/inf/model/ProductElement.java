package com.ztesoft.net.outter.inf.model;

public class ProductElement {
		private String productCode;  //产品编码
		private String productName; //产品名称
		private String productDesc;	//产品描述
		private String productValue;	//产品价格
		private String productNet;		//产品网别
		private String productCategory;	//产品类别
		public String getProductCode() {
			return productCode;
		}
		public void setProductCode(String productCode) {
			this.productCode = productCode;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public String getProductDesc() {
			return productDesc;
		}
		public void setProductDesc(String productDesc) {
			this.productDesc = productDesc;
		}
		public String getProductValue() {
			return productValue;
		}
		public void setProductValue(String productValue) {
			this.productValue = productValue;
		}
		public String getProductNet() {
			return productNet;
		}
		public void setProductNet(String productNet) {
			this.productNet = productNet;
		}
		public String getProductCategory() {
			return productCategory;
		}
		public void setProductCategory(String productCategory) {
			this.productCategory = productCategory;
		}
		public String getProductType() {
			return productType;
		}
		public void setProductType(String productType) {
			this.productType = productType;
		}
		private String productType;	//产品类型
}
