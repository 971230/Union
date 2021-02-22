package zte.net.service;

import params.ZteResponse;
import params.req.ZbOrderAuditDetailReq;
import params.req.ZbOrderAuditStatusReq;
import params.req.ZbOrderStateQueryReq;
import params.resp.ZbOrderAuditDetailResp;

/**
 * 总部订单激活环节
 * @author ricky
 *
 */
public interface IOrderArtificialSectionService {
	/**
	 * 订单激活环节审核通过
	 * @param req
	 * @return
	 */
	public ZteResponse orderAuditStatusModify(ZbOrderAuditStatusReq req);
	/**
	 * 订单激活详情，用于下载图片和视频
	 * @param req
	 * @return
	 */
	public ZbOrderAuditDetailResp orderAuditDetail(ZbOrderAuditDetailReq req);
	
	/**
	 * add by duan.jingliang
	 * 订单状态查询
	 * @param req
	 * @return
	 * */
	public ZteResponse orderStateQuery(ZbOrderStateQueryReq req);
}
