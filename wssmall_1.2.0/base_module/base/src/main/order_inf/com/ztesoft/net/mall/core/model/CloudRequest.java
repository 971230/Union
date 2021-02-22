package com.ztesoft.net.mall.core.model;

/**
 * 
 * 云卡调拨请求参数对象
 * 
 * @author wui
 */
public class CloudRequest implements java.io.Serializable {
	//private String cloud_ids;// 调拨云卡ids

	private String begin_nbr;//库存不足，申请库存继续调拨 
	private String end_nbr;// 库存不足，申请库存继续调拨
	
//	public String getCloud_ids() {
//		return cloud_ids;
//	}
//
//	public void setCloud_ids(String cloud_ids) {
//		this.cloud_ids = cloud_ids;
//	}

	public String getBegin_nbr() {
		return begin_nbr;
	}

	public void setBegin_nbr(String begin_nbr) {
		this.begin_nbr = begin_nbr;
	}

	public String getEnd_nbr() {
		return end_nbr;
	}

	public void setEnd_nbr(String end_nbr) {
		this.end_nbr = end_nbr;
	}

	

}