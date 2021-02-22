package zte.net.service;

import params.ZteResponse;
import params.req.ZbBackfillLogisticsReq;
import params.req.ZbOrderDeliveryCodeQueryReq;
import params.req.ZbOrderDeliveryReq;
import params.req.ZbOrderPrintReq;

public interface IOrderDeliverySectionService {
	/**
	 * 总部订单回填物流单接口
	 * @param req
	 * @return
	 */
	public ZteResponse zbBackfillLogistics(ZbBackfillLogisticsReq req);

	/**
	 * 总部订单发货接口
	 * @param req
	 * @return
	 */
	public ZteResponse zbOrderDelivery(ZbOrderDeliveryReq req);
	/**
	 * 总部订单打印接口
	 * @param req
	 * @return
	 */
	public ZteResponse zbOrderPrint(ZbOrderPrintReq req); 
	
	/**
	 * add by duan.jingliang
	 * 总部订单获取电子物流单号接口
	 * @param req
	 * @return
	 */
	public ZteResponse zbOrderQueryDeliveryNum(ZbOrderDeliveryCodeQueryReq req);
}
