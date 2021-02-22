package zte.net.service;

import com.ztesoft.autoprocess.base.ZBSystemClient;
import com.ztesoft.autoprocess.base.model.ZbOpenType;

import params.ZteResponse;
import params.req.BindCustInfo2OrderReq;
import params.req.ManualModifyBuyerInfoReq;
import params.req.ModifyOpenAccountGoodsReq;
import params.req.OpenAccountDetailReq;
import params.req.OpenAccountSubmitOrderReq;
import params.req.ReAllotOrderReq;
import params.resp.BindCustInfo2OrderResp;
import params.resp.CrawlerResp;
import params.resp.ManualModifyBuyerInfoResp;
import params.resp.OpenAccountDetailResp;
import params.resp.OpenAccountSubmitOrderResp;

public interface IOrderOpenAccountSectionService {
	/**
	 * 二次分配订单
	 * @param req
	 * @return
	 */
	public CrawlerResp reAllotOrder(ReAllotOrderReq req);
	/**
	 * 进入总部开户详情页面
	 * @param req
	 * @return
	 */
	public OpenAccountDetailResp getOpenAccountDetail(OpenAccountDetailReq req);
	/**
	 * 修改开户界面商品信息
	 * @param req
	 * @return
	 */
	public ZteResponse modifyOpenAccountGoods(ModifyOpenAccountGoodsReq req);
	
	/**
	 * 订单详情页--客户信息和订单信息绑定
	 * @param req
	 * @return
	 */
	public BindCustInfo2OrderResp bindCustInfo2Order(BindCustInfo2OrderReq req);
	
	/**
	 * 订单详情页--手工开户订单信息补录
	 * @param req
	 * @return
	 */
	public OpenAccountSubmitOrderResp openAccountSubmitOrder(OpenAccountSubmitOrderReq req);
	
	/**
	 * 手工开户--修改买家信息
	 * @param req
	 * @return
	 */
	public ManualModifyBuyerInfoResp manualModifyBuyerInfo(ManualModifyBuyerInfoReq req);
	public ZbOpenType zbOpenType(ZBSystemClient client,String orderId,String orderNo) throws Exception;
}
