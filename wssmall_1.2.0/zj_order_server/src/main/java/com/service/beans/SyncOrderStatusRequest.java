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
public class SyncOrderStatusRequest {
	
	@XmlElement(required=true, nillable=false)
	private String activeNo;//访问流水
	
	@XmlElement(required=true, nillable=false)
	private String reqId;//接入ID，由订单系统提供
	
	private String reqType;	//固定值：at_syn_wms_status
	
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

	@XmlType(name="SyncOrderStatus_OrderInfo")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class OrderInfo{
		@XmlElement(required=true, nillable=false)
		private String orderId;//内部订单号
		@XmlElement(required=true, nillable=false)
		private String status;	//处理状态
		@XmlElement(required=true, nillable=false)
		private String desc;	//描述
		@XmlJavaTypeAdapter(DateAdapter.class)
		private Date statusUpdateTime;	//状态变更时间
		private String weight;			//重量
		private String stationNo;			//稽核工位号
		private String toteId;			//周转箱编号
		private String userDef1;			//预留字段1
		private String userDef2;			//预留字段2
		@XmlElement(required=true, nillable=false)
		private List<GoodInfo> goodInfo;
		
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public Date getStatusUpdateTime() {
			return statusUpdateTime;
		}
		public void setStatusUpdateTime(Date statusUpdateTime) {
			this.statusUpdateTime = statusUpdateTime;
		}
		public String getWeight() {
			return weight;
		}
		public void setWeight(String weight) {
			this.weight = weight;
		}
		public String getStationNo() {
			return stationNo;
		}
		public void setStationNo(String stationNo) {
			this.stationNo = stationNo;
		}
		public String getToteId() {
			return toteId;
		}
		public void setToteId(String toteId) {
			this.toteId = toteId;
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
		public List<GoodInfo> getGoodInfo() {
			return goodInfo;
		}
		public void setGoodInfo(List<GoodInfo> goodInfo) {
			this.goodInfo = goodInfo;
		}
	}
	
	@XmlType(name="SyncOrderStatus__GoodInfo")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class GoodInfo{
		@XmlElement(required=true, nillable=false)
		private String orderProductId;//货品ID
		private String packageId;//商品ID
		private String imei;//唯一串号
		private String indexNum;//顺序号
		private String UserDef3;//预留字段3
		private String UserDef4;//预留字段4
		
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
		public String getImei() {
			return imei;
		}
		public void setImei(String imei) {
			this.imei = imei;
		}
		public String getIndexNum() {
			return indexNum;
		}
		public void setIndexNum(String indexNum) {
			this.indexNum = indexNum;
		}
		public String getUserDef3() {
			return UserDef3;
		}
		public void setUserDef3(String userDef3) {
			UserDef3 = userDef3;
		}
		public String getUserDef4() {
			return UserDef4;
		}
		public void setUserDef4(String userDef4) {
			UserDef4 = userDef4;
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
	
	@XmlType(name="SyncOrderStatus_DateAdapter")
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


