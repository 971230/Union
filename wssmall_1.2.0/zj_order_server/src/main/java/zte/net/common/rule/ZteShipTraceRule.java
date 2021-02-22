package zte.net.common.rule;



import java.util.Map;

import javax.annotation.Resource;

import zte.net.common.annontion.context.action.ZteMethodAnnontion;
import zte.net.common.annontion.context.action.ZteServiceAnnontion;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.params.order.req.ShippingConfirmReq;
import zte.params.order.req.SmsSendReq;
import zte.params.order.resp.ShippingConfirmResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.IOrdShipTacheManager;
import com.ztesoft.net.service.IOrdVisitTacheManager;

import commons.CommonTools;
import consts.ConstsCore;


/**
 *
 * 发货
 * @author wu.i
 */
@ZteServiceAnnontion(trace_name="ZteShipTraceRule",trace_id="1",version="1.0",desc="发货环节业务通知组件")
public  class ZteShipTraceRule extends ZteTraceBaseRule {
	
	@Resource
	private IOrdShipTacheManager  ordShipTacheManager;
	@Resource
	private IOrdVisitTacheManager  ordVisitTacheManager;
	/**
	 *发货环节入口
	 */
	@Override
	@ZteMethodAnnontion(name="发货环节入口",group_name="发货",order="1",page_show=true,path="shipTraceRule.exec")
	public BusiCompResponse engineDo(BusiCompRequest busiCompRequest) {
		String order_id = busiCompRequest.getOrder_id();
		OrderBusiRequest orderBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderBusiRequest();
		orderBusiRequest.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_7);
		orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusiRequest.store();
		
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 *发货环节入口
	 */
	@ZteMethodAnnontion(name="订单发货(待发货)",group_name="发货",order="1",page_show=true,path="ZteShipTraceRule.orderShip")
	public BusiCompResponse orderShip(BusiCompRequest busiCompRequest) {
		String orderId = busiCompRequest.getOrder_id();
		OrderBusiRequest orderBusiRequest = CommonDataFactory.getInstance().getOrderTree(orderId).getOrderBusiRequest();
		orderBusiRequest.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_7);
		orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusiRequest.store();
		
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * 订单发货(待取货)
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="订单发货(待取货)",group_name="发货",order="4",page_show=true,path="ZteShipTraceRule.orderShipForReceive")
	public BusiCompResponse orderShipForReceive(BusiCompRequest busiCompRequest) throws ApiBusiException{
		String orderId = busiCompRequest.getOrder_id();
		OrderBusiRequest orderBusiRequest = CommonDataFactory.getInstance().getOrderTree(orderId).getOrderBusiRequest();
		orderBusiRequest.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_72);
		orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusiRequest.store();
		
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * 订单发货(已发货)
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="订单发货(已发货)",group_name="发货",order="4",page_show=true,path="ZteShipTraceRule.orderShipFinish")
	public BusiCompResponse orderShipFinish(BusiCompRequest busiCompRequest) throws ApiBusiException{
		String orderId = busiCompRequest.getOrder_id();
		OrderBusiRequest orderBusiRequest = CommonDataFactory.getInstance().getOrderTree(orderId).getOrderBusiRequest();
		orderBusiRequest.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_73);
		orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusiRequest.store();
		
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	
	/**
	 *发货自动跳过设置
	 */
	@ZteMethodAnnontion(name="发货自动跳过设置",group_name="发货",order="1",page_show=true,path="shipTraceRule.entranceJump")
	public BusiCompResponse engineDoJump(BusiCompRequest busiCompRequest) {
		BusiCompResponse resp = this.nextflow(busiCompRequest, false,"H");
		return resp;
	}
	
	/**
	 * 稽核完成通知总部
	 */
	@ZteMethodAnnontion(name="稽核完成通知总部(发货)",group_name="发货",order="1",page_show=true,path="ZteShipTraceRule.orderJhwcStatuToZB")
	public BusiCompResponse orderJhwcStatuToZB(BusiCompRequest busiCompRequest) throws Exception{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		ordShipTacheManager.notifyQCStatusToZB(order_id);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}

	/**
	 * 通知WMS进行发货
	 * @throws Exception 
	 */
	@ZteMethodAnnontion(name="通知WMS进行发货(发货)",group_name="发货",order="1",page_show=true,path="ZteShipTraceRule.orderJhwcStatuToWms")
	public BusiCompResponse orderJhwcStatuToWms(BusiCompRequest busiCompRequest) throws Exception{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordShipTacheManager.notifyCheckFinishToWMS(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.WMS_INF_RESP_CODE_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	
	/**
	 * 接收WMS出货通知
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="接收WMS出货通知(发货)",group_name="发货",order="2",page_show=true,path="ZteShipTraceRule.wmsSynShipNotify")
	public BusiCompResponse wmsSynShipNotify(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		BusiDealResult dealResult = ordShipTacheManager.recShipFromWMS(busiCompRequest);
		if(!dealResult.getError_code().equals(EcsOrderConsts.WMS_INF_RESP_CODE_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	/**
	 * 接收WMS发货结果通知
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="接收WMS发货结果通知(发货)",group_name="发货",order="3",page_show=true,path="ZteShipTraceRule.wmsSynShipCompleteNotify")
	public BusiCompResponse wmsSynShipCompleteNotify(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		BusiDealResult dealResult = ordShipTacheManager.recShipFinshFromWMS(busiCompRequest);
		if(!dealResult.getError_code().equals(EcsOrderConsts.WMS_INF_RESP_CODE_0000))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	
	/**
	 * 发货后进行短信提醒
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name="短信提醒(发货)",group_name="发货",order="4",page_show=true,path="ZteShipTraceRule.smsSendNotify")
	public BusiCompResponse smsSendNotify(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		SmsSendReq smsSend = new SmsSendReq();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordShipTacheManager.smsSendNotify(order_id);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * 发货后进行短信提醒
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name="短信提醒(发货)",group_name="发货",order="4",page_show=true,path="ZteShipTraceRule.smsSendNotify")
	public BusiCompResponse send3NetSms(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		SmsSendReq smsSend = new SmsSendReq();
		String order_id = busiCompRequest.getOrder_id();
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact)params.get("fact");
		String template = fact.getCalc_input();
		BusiDealResult dealResult = ordShipTacheManager.send3NetSms(order_id, template);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 *订单变更通知淘宝(发货)
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="订单变更通知淘宝(发货)",group_name="发货",order="5",page_show=true,path="ZteShipTraceRule.orderUpdateStatuToTB")
	public BusiCompResponse orderUpdateStatuToTB(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult result = ordShipTacheManager.notifyShipToTB(order_id);
		if(!EcsOrderConsts.BUSI_DEAL_RESULT_0000.equals(result.getError_code())){
			resp.setError_code("-1");
			resp.setError_msg(result.getError_msg());
		}else{
			resp.setError_code("0");
			resp.setError_msg("执行成功");
		}		
		return resp;
		
	}
	
	/**
	 *订单变更通知淘宝(发货)
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="订单物流信息同步天猫天机(发货)",group_name="发货",order="5",page_show=true,path="ZteShipTraceRule.notifyOrderStatusToTaobaoTianji")
	public BusiCompResponse notifyOrderStatusToTaobaoTianji(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult result = ordShipTacheManager.syncOrderDeliveryToTaobaoTianji(order_id);
		if(!EcsOrderConsts.BUSI_DEAL_RESULT_0000.equals(result.getError_code())){
			resp.setError_code("-1");
			resp.setError_msg(result.getError_msg());
		}else{
			resp.setError_code("0");
			resp.setError_msg("执行成功");
		}		
		return resp;
	}
	
	/**
	 * 订单变更通知新商城(发货)
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="订单变更通知新商城(发货)",group_name="发货",order="4",page_show=true,path="ZteShipTraceRule.orderUpdateStatuToMall")
	public BusiCompResponse orderUpdateStatuToMall(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordShipTacheManager.notifyShipToOuterShop(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	
	/**
	 * 订单变更通知总部(发货)
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="订单变更通知总部(发货)",group_name="发货",order="4",page_show=true,path="ZteShipTraceRule.orderUpdateStatuToZB")
	public BusiCompResponse orderUpdateStatuToZB(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordShipTacheManager.notifyShipToZB(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	/**
	 * [爬虫]物流单号回填总商
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[爬虫]物流单号回填总商(发货)",group_name="发货",order="4",page_show=true,path="ZteShipTraceRule.orderUpdateLogiNoToZS")
	public BusiCompResponse orderUpdateLogiNoToZS(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordShipTacheManager.orderUpdateLogiNoToZS(order_id);
		if(!dealResult.getError_code().equals(ConstsCore.ERROR_SUCC))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg(),"1");
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	
	
	/**
	 * 下一步(发货)
	 */
	@ZteMethodAnnontion(name="下一步(发货)",group_name="发货",order="5",page_show=true,path="ZteShipTraceRule.nextStep")
	public BusiCompResponse nextStep(BusiCompRequest busiCompRequest){
		
		//调用
		ShippingConfirmReq shipReq=new ShippingConfirmReq();
		shipReq.setOrder_id(busiCompRequest.getOrder_id());
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		client.execute(shipReq, ShippingConfirmResp.class);
		
		this.putNextTraceId(busiCompRequest, "L");
		BusiCompResponse resp = this.nextflow(busiCompRequest,false,"H");
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	
	/**
	 * 下一步(发货)
	 */
	@ZteMethodAnnontion(name="自动跳转下一步(发货)",group_name="发货",order="5",page_show=true,path="ZteShipTraceRule.autoNextStep")
	public BusiCompResponse autoNextStep(BusiCompRequest busiCompRequest){
		//调用
		this.putNextTraceId(busiCompRequest, "J");
		BusiCompResponse resp = this.nextflow(busiCompRequest,false,"H");
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}	
	
	/**
	 * 通知WMS已发货
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="通知WMS已发货(发货)",group_name="发货",order="6",page_show=true,path="ZteShipTraceRule.notifyWMSShipFinish")
	public BusiCompResponse notifyWMSShipFinish(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordShipTacheManager.notifyWMSShipFinish(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.WMS_INF_RESP_CODE_0000))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	
	/**
	 * [爬虫]物流单号回填总商
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[爬虫]发货信息回填总商",group_name="发货",order="14",page_show=true,path="ZteShipTraceRule.noticeDeliveryInfoToZS")
	public BusiCompResponse noticeDeliveryInfoToZS(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordShipTacheManager.noticeDeliveryInfoToZS(order_id);
		if(!dealResult.getError_code().equals(ConstsCore.ERROR_SUCC))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg(),"1");
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	/**
	 * [爬虫]物流单号回填总商
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[爬虫]获取总商物流单号",group_name="发货",order="15",page_show=true,path="ZteShipTraceRule.getZbLogiNoByCrawler")
	public BusiCompResponse getZbLogiNoByCrawler(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordShipTacheManager.getZbLogiNoByCrawler(order_id);
		if(!dealResult.getError_code().equals(ConstsCore.ERROR_SUCC))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg(),"1");
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
}
