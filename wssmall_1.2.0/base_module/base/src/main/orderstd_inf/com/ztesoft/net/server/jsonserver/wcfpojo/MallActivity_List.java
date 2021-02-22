package com.ztesoft.net.server.jsonserver.wcfpojo;

import java.util.List;

public class MallActivity_List {

	//优惠编码
	private String activity_code="";
	//优惠标识
	private String activity_id="";
	//赠品信息
	private List<MallGift_List> gift_list;
	public List<MallGift_List> getGift_list() {
		return gift_list;
	}
	public void setGift_list(List<MallGift_List> gift_list) {
		this.gift_list = gift_list;
	}
	public String getActivity_code() {
		return activity_code;
	}
	public void setActivity_code(String activity_code) {
		this.activity_code = activity_code;
	}
	public String getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}	
	
}
