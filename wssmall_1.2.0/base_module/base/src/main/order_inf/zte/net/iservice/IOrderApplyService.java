package zte.net.iservice;

import zte.params.order.req.OrderApplyAddReq;
import zte.params.order.req.OrderApplyPageListReq;
import zte.params.order.resp.OrderApplyAddResp;
import zte.params.order.resp.OrderApplyPageListResp;

/**
 * 订单申请
 * @作者 MoChunrun
 * @创建日期 2014-2-18 
 * @版本 V 1.0
 */
public interface IOrderApplyService {

	/**
	 * 按申请类型查询订单申请数据
	 * @作者 MoChunrun
	 * @创建日期 2014-2-18 
	 * @param req
	 * @return
	 */
	public OrderApplyPageListResp queryOrderApplyForPage(OrderApplyPageListReq req);
	
	/**
	 * 添加订单申请
	 * @作者 MoChunrun
	 * @创建日期 2014-2-18 
	 * @param req
	 * @return
	 */
	public OrderApplyAddResp addOrderApply(OrderApplyAddReq req);
	
}
