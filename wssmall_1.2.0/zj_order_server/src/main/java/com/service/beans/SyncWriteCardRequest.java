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
public class SyncWriteCardRequest {
	
	@XmlElement(required=true, nillable=false)
	private String activeNo;//访问流水
	
	@XmlElement(required=true, nillable=false)
	private String reqId;//接入ID，由订单系统提供
	
	private String reqType;	//固定值：at_syn_readId
	
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

	@XmlType(name="SyncWriteCard_OrderInfo")
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
	
	@XmlType(name="SyncWriteCard_GoodInfo")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class GoodInfo{
		@XmlElement(required=true, nillable=false)
		private String orderProductId;//货品ID
		@XmlElement(required=true, nillable=false)
		private String packageId;//商品ID
		private String mobileTel;	//手机号码
		@XmlElement(required=true, nillable=false)
		private String readId;	//写卡机编号
		@XmlElement(required=true, nillable=false)
		private String indexNum;//顺序号
		private String userDef1;//预留字段1
		private String userDef2;//预留字段2
		
		public String getOrderProductId() {
			return orderProductId;
		}
		public void setOrderProductId(String orderProductId) {
			this.orderProductId = orderProductId;
		}
		public String getPackageId() {
			return packageId;
		}
		public void setPackageId(String packageId) {
			this.packageId = packageId;
		}
		public String getMobileTel() {
			return mobileTel;
		}
		public void setMobileTel(String mobileTel) {
			this.mobileTel = mobileTel;
		}
		public String getReadId() {
			return readId;
		}
		public void setReadId(String readId) {
			this.readId = readId;
		}
		public String getIndexNum() {
			return indexNum;
		}
		public void setIndexNum(String indexNum) {
			this.indexNum = indexNum;
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
	
	@XmlType(name="SyncWriteCard_DateAdapter")
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
	
	@Override
	public String toString() {
		
		return super.toString();
	}
}


