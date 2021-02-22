package zte.net.service;

import params.req.ZbOrderDistributeReq;
import params.req.ZbOrderVerifyReq;
import params.resp.ZbOrderDistributeResp;
import params.resp.ZbOrderVerifyResp;

public interface IOrderVerifySectionService {
	/**
	 * 总部订单审核
	 * @param req
	 * @return
	 */
	ZbOrderVerifyResp orderVerify(ZbOrderVerifyReq req);
	/**
	 * 总部订单分配
	 * @param req
	 * @return
	 */
	ZbOrderDistributeResp orderDistribute(ZbOrderDistributeReq req);
}
