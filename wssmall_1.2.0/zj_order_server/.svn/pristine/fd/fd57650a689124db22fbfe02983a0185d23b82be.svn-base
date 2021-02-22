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
public class SyncTerminalImeiRequest {
	
	@XmlElement(required=true, nillable=false)
	private String activeNo;//访问流水
	
	@XmlElement(required=true, nillable=false)
	private String reqId;//接入ID，由订单系统提供
	
	private String reqType;	//固定值：at_syn_series_num
	
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

	@XmlType(name="SyncTerminalImei_OrderInfo")
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
	
	@XmlType(name="SyncTerminalImei_GoodInfo")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class GoodInfo{
		@XmlElement(required=true, nillable=false)
		private String orderProductId;//货品ID
		private String packageId;//商品ID
		private String itemsType;	//物品类型
		private String cardType;	//卡类型
		private String iccid;		//白卡固定值
		@XmlElement(required=true, nillable=false)
		private String terminalImei;//终端串号
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
		public String getItemsType() {
			return itemsType;
		}
		public void setItemsType(String itemsType) {
			this.itemsType = itemsType;
		}
		public String getCardType() {
			return cardType;
		}
		public void setCardType(String cardType) {
			this.cardType = cardType;
		}
		public String getIccid() {
			return iccid;
		}
		public void setIccid(String iccid) {
			this.iccid = iccid;
		}
		public String getTerminalImei() {
			return terminalImei;
		}
		public void setTerminalImei(String terminalImei) {
			this.terminalImei = terminalImei;
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
	
	@XmlType(name="SyncTerminalImei_DateAdapter")
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


