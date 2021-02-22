package services;

import params.order.req.AcceptReq;
import params.order.req.ItemAcceptReq;
import params.order.resp.ItemAcceptResp;
import rule.params.accept.resp.AcceptRuleResp;

import com.ztesoft.api.ApiBusiException;

/**
 * 受理单
 * 
 * @作者 MoChunrun
 * @创建日期 2013-10-10
 * @版本 V 1.0
 */
public interface AcceptInf {

	/**
	 * 创建受理单
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2013-10-10
	 * @param req
	 * @return
	 */
	public AcceptRuleResp createAccept(AcceptReq acceptReq) throws ApiBusiException;
	
	public ItemAcceptResp itemAccept(ItemAcceptReq req) throws ApiBusiException;

}
