package rule;

import params.order.req.DeliveryReq;
import params.order.resp.DeliveryResp;

public interface IDelveryRule {

	public DeliveryResp computeDelivery(DeliveryReq req);
	
}
