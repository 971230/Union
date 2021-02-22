package com.service.beans;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SyncAuditingInfoRequest {
	
	@XmlElement(required=true, nillable=false)
	private String activeNo;//访问流水
	
	@XmlElement(required=true, nillable=false)
	private String reqId;//接入ID，由订单系统提供
	
	private String reqType;	//固定值：at_syn_auditing
	
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date reqTime;//格式：yyyy-mm-dd HH:mi:ss
	
	@XmlElement(required=true, nillable=false)
	private OrderInfo orderInfo;
	
	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	@XmlType(name="SyncAuditingInfo_OrderInfo")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class OrderInfo{
		@XmlElement(required=true, nillable=false)
		private String orderId;//内部订单号
		@XmlElement(required=true, nillable=false)
		private List<GoodInfo> goodInfo;
		
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public List<GoodInfo> getGoodInfo() {
			return goodInfo;
		}
		public void setGoodInfo(List<GoodInfo> goodInfo) {
			this.goodInfo = goodInfo;
		}
		
		
	}
	
	@XmlType(name="SyncAuditingInfo_GoodInfo")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class GoodInfo{
		@XmlElement(required=true, nillable=false)
		private String orderPorductId;//货品ID
		@XmlElement(required=true, nillable=false)
		private String itemsType;	//物品类型
		@XmlElement(required=true, nillable=false)
		private String productCode;	//货品编码
		@XmlElement(required=true, nillable=false)
		private String productName;		//货品明细名称
		@XmlElement(required=true, nillable=false)
		private String productNum;//数量
		private String imei;		//唯一串号
		private String userDef1;//预留字段1
		private String userDef2;//预留字段2
		
		public String getOrderPorductId() {
			return orderPorductId;
		}
		public void setOrderPorductId(String orderPorductId) {
			this.orderPorductId = orderPorductId;
		}
		public String getItemsType() {
			return itemsType;
		}
		public void setItemsType(String itemsType) {
			this.itemsType = itemsType;
		}
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
		public String getProductNum() {
			return productNum;
		}
		public void setProductNum(String productNum) {
			this.productNum = productNum;
		}
		public String getUserDef1() {
			return userDef1;
		}
		public void setUserDef1(String userDef1) {
			this.userDef1 = userDef1;
		}
		public String getUserDef2() {
			return userDef2;
		}
		public void setUserDef2(String userDef2) {
			this.userDef2 = userDef2;
		}
		public String getImei() {
			return imei;
		}
		public void setImei(String imei) {
			this.imei = imei;
		}
	}

	public String getActiveNo() {
		return activeNo;
	}

	public void setActiveNo(String activeNo) {
		this.activeNo = activeNo;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public Date getReqTime() {
		return reqTime;
	}

	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}
	
	@XmlType(name="SyncAuditingInfo_DateAdapter")
	public static class DateAdapter extends XmlAdapter<String, Date> {  
	    private SimpleDateFormat yyyyMMddHHmmss = new  SimpleDateFormat(  
	            "yyyy-mm-dd HH:mm:ss");  
	  
	    @Override  
	    public Date unmarshal(String v) throws Exception {  
	        return yyyyMMddHHmmss.parse(v);  
	    }  
	  
	    @Override  
	    public String marshal(Date v) throws Exception {  
	        return yyyyMMddHHmmss.format(v);  
	    }  
	}  
}


