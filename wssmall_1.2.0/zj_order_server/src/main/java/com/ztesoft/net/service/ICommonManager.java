package com.ztesoft.net.service;

import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ems.req.EmsRoutePushServiceReq;
import zte.net.ecsord.params.order.req.GoodsSynWMSReq;
import zte.net.ecsord.params.order.resp.GoodsSynWMSResp;
import zte.net.ecsord.params.wyg.req.MallOpIDSynInfoReq;
import zte.net.ecsord.params.wyg.resp.MallOpIDSynInfoResp;
import zte.net.ecsord.params.wyg.resp.WYGAcceptanceQueryResp;
import zte.net.ecsord.params.zb.req.StateUtil;

import com.ztesoft.net.mall.core.model.OrderReleaseRecord;
import com.ztesoft.net.model.BusiDealResult;


public interface ICommonManager {
	/**
	 * 设置已退单
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult setOrdRefund(BusiCompRequest busiCompRequest);
	
	/**
	 * 订单开户,返销余额通知
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult orderAccountOrBuybackInform(String order_id,String operatorFlag);
	
	/**
	 * 订单同步老系统
	 * @return
	 */
	public BusiDealResult synOrderToOldSys(BusiCompRequest busiCompRequest);
	
	/**
	 * 订单环节跳转，标记异常，恢复异常通知老系统
	 * @return
	 */
	public BusiDealResult synOrderStatusToOldSys(BusiCompRequest busiCompRequest);
	
	/**
	 * 保存终端串号/号码释放记录
	 * @return
	 */
	public String saveReleaseRecord(OrderReleaseRecord record);
	
	/**
	 * 新商城同步工号信息
	 * @return
	 */
	public MallOpIDSynInfoResp wygOperatorIDSyn(MallOpIDSynInfoReq req);
	
	/**
	 * 同步SKU到WMS
	 * @param req
	 * @return
	 */
	public GoodsSynWMSResp synchronizedGoodsToWMS(GoodsSynWMSReq req);

	/**
	 * 订单环节同步本地商城
	 * @param vo 特殊的环节状态和描述
	 * @return
	 */
	public BusiDealResult ordFlowTraceSyn(String order_id,StateUtil vo);

	/**
	 * 社会渠道购机券换手机BSS端支持(加锁)
	 * @return
	 */
	public BusiDealResult purchaseCouponsLock(String order_id);

	/**
	 * 社会渠道购机券换手机BSS端支持(转兑)
	 * @return
	 */
	public BusiDealResult purchaseCouponsExchange(String order_id);
	
	/**
	 * 沃云购 受理单信息查询
	 * @return
	 */
	public WYGAcceptanceQueryResp acceptanceQuery(String order_id);

	/**
	 * 出库信息回传SAP
	 * @return
	 */
	public BusiDealResult deliveNotifyHS(String order_id);

	/**
	 * 退货入库传输SAP
	 * @return
	 */
	public BusiDealResult returnWarehousingHS(String order_id);

	/**
	 * 仓储商采购退货订单出库回传SAP接口
	 * @return
	 */
	public BusiDealResult orderCheckOutB2B(String order_id);
	
	/**
	 *  修改订单状态（全部归档）
	 * @param order_id
	 * @return
	 */
	public BusiDealResult setOrderStatusAllArchive(String order_id);
	
	/**
	 *  修改订单状态（部分归档）
	 * @param order_id
	 * @return
	 */
	public BusiDealResult setOrderStatusPartArchive(String order_id);
	
	/**
	 * EMS物流路由信息推送处理
	 * @param req
	 * @return
	 */
	public BusiDealResult emsRoutePush(EmsRoutePushServiceReq req);
	
	/**
	 * BSS本地商城订单状态同步
	 * @param req
	 * @return
	 */
	public BusiDealResult localGoodsStatusSynchronization(String order_id);
	/**
	 * [爬虫]
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult zsRefundApply(String order_id);
	
	public BusiDealResult cancelOrderStatusQry(String order_id);
	
	public BusiDealResult tabuserBtocbSub(String order_id);
	
	public BusiDealResult resourCecenterApp(String order_id);

	public BusiDealResult objectQry(String order_id);

	public BusiDealResult rateStatusQry(String order_id);

    public EmpOperInfoVo findEssOperatorInfo(String oper_from,String city_id, String provinceQueryPara_001);
	
}
