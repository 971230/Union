package zte.params.order.resp;

import java.util.List;
import params.ZteResponse;

public class UnimallOrderQueryResp extends ZteResponse{
	
	private List orderInfo ; //订单内容
	private String orderTotalNum;//订单总数
	private String page_index;
	private String page_size;
	public String getOrderTotalNum() {
		return orderTotalNum;
	}

	public void setOrderTotalNum(String orderTotalNum) {
		this.orderTotalNum = orderTotalNum;
	}

	public String getPage_index() {
		return page_index;
	}

	public void setPage_index(String page_index) {
		this.page_index = page_index;
	}

	public String getPage_size() {
		return page_size;
	}

	public void setPage_size(String page_size) {
		this.page_size = page_size;
	}

	public List getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(List orderInfo) {
		this.orderInfo = orderInfo;
	}
	
	//TODO

}
