package zte.params.order.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.DeliveryFlow;

import params.ZteResponse;

public class DeliveryFlowListResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="物流信息列表",type="String",isNecessary="Y",desc="物流信息列表",hasChild=true)
	private List<DeliveryFlow> deliveryFlow;

	public List<DeliveryFlow> getDeliveryFlow() {
		return deliveryFlow;
	}

	public void setDeliveryFlow(List<DeliveryFlow> deliveryFlow) {
		this.deliveryFlow = deliveryFlow;
	}
	
}
