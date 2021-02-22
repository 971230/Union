package params.order.resp;

import java.util.List;

import params.ZteResponse;
import zte.net.ord.params.busi.req.OrderWorkItemBusiRequest;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class OrderWorkItemQueryResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="订单工作项List",type="String",isNecessary="Y",desc="订单工作项List")
	private List<OrderWorkItemBusiRequest> itemList;
	
	@ZteSoftCommentAnnotationParam(name="描述",type="String",isNecessary="Y",desc="描述")
	private String desc;

	
	
	
	public List<OrderWorkItemBusiRequest> getItemList() {
		return itemList;
	}
	public void setItemList(List<OrderWorkItemBusiRequest> itemList) {
		this.itemList = itemList;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
