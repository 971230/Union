package zte.net.common.rule;



import javax.annotation.Resource;

import zte.net.common.annontion.context.action.ZteMethodAnnontion;
import zte.net.common.annontion.context.action.ZteServiceAnnontion;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.ICommonManager;
import com.ztesoft.net.service.IOrdBackTacheManager;

import consts.ConstsCore;

/**
 *
 * 订单归档
 * @author wu.i
 */
@ZteServiceAnnontion(trace_name="ZteOrderBackTraceRule",trace_id="1",version="1.0",desc="订单归档环节业务通知组件")
public  class ZteOrderBackTraceRule extends ZteTraceBaseRule {
	
	@Resource
	private IOrdBackTacheManager ordBackTacheManager;
	@Resource
	private ICommonManager commonManager;
	/**
	 *订单归档环节入口
	 */
	@Override
	@ZteMethodAnnontion(name="订单归档环节入口",group_name="订单归档",order="1",page_show=true,path="ZteOrderBackTraceRule.exec")
	public BusiCompResponse engineDo(BusiCompRequest busiCompRequest) {
		String orderId = busiCompRequest.getOrder_id();
		OrderBusiRequest orderBusiRequest = CommonDataFactory.getInstance().getOrderTree(orderId).getOrderBusiRequest();
		orderBusiRequest.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_8);
		orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusiRequest.store();
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	@ZteMethodAnnontion(name="写入AOP查询队列",group_name="订单归档",order="2",page_show=true,path="ZteOrderBackTraceRule.insertAopQryLog")
	public BusiCompResponse insertAopQryLog(BusiCompRequest busiCompRequest){
		String orderId = busiCompRequest.getOrder_id();
		ordBackTacheManager.insertAopQuery(orderId);
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	
	/**
	 * 接收总部回单完成通知
	 * @throws Exception 
	 */
	@ZteMethodAnnontion(name="接收总部回单完成通知",group_name="订单归档",order="3",page_show=true,path="ZteOrderBackTraceRule.zbSynOrderBackNotify")
	public BusiCompResponse zbSynOrderBackNotify(BusiCompRequest busiCompRequest) throws Exception{
		BusiCompResponse resp = new BusiCompResponse();
	    ordBackTacheManager.recReceiptFromZB(busiCompRequest);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	@ZteMethodAnnontion(name="订单部分归档(订单归档)",group_name="订单归档",order="9",page_show=true,path="ZteOrderBackTraceRule.orderPartArchive")
	public BusiCompResponse orderPartArchive(BusiCompRequest busiCompRequest){
		String orderId = busiCompRequest.getOrder_id();
		BusiDealResult result = commonManager.setOrderStatusPartArchive(orderId);
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code(result.getError_code());
		resp.setError_msg(result.getError_msg());
		return resp;
		
	}
	
	@ZteMethodAnnontion(name="订单全部归档(订单归档)",group_name="订单归档",order="10",page_show=true,path="ZteOrderBackTraceRule.orderAllArchive")
	public BusiCompResponse orderAllArchive(BusiCompRequest busiCompRequest){
		String orderId = busiCompRequest.getOrder_id();
		BusiDealResult result = commonManager.setOrderStatusAllArchive(orderId);
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code(result.getError_code());
		resp.setError_msg(result.getError_msg());
		return resp;
		
	}
	
	@ZteMethodAnnontion(name="接收码上购协议照片(订单归档)",group_name="订单归档",order="11",page_show=true,path="ZteOrderBackTraceRule.receiveCodePurchaseAgrtImgs")
	public BusiCompResponse receiveCodePurchaseAgrtImgs(BusiCompRequest busiCompRequest){
		BusiDealResult result = ordBackTacheManager.receiveCodePurchaseAgrtImgs(busiCompRequest);
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code(result.getError_code());
		resp.setError_msg(result.getError_msg());
		return resp;
		
	}
	
	@ZteMethodAnnontion(name="[爬虫]激活状态回填总商(订单归档)",group_name="订单归档",order="12",page_show=true,path="ZteOrderBackTraceRule.noticeActiveStatusToZS")
	public BusiCompResponse noticeActiveStatusToZS(BusiCompRequest busiCompRequest){
		String orderId = busiCompRequest.getOrder_id();
		BusiDealResult result = ordBackTacheManager.noticeActiveStatusToZS(orderId);
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code(result.getError_code());
		resp.setError_msg(result.getError_msg());
		return resp;
		
	}
	
	@ZteMethodAnnontion(name="关闭工单、意向单(订单归档)",group_name="订单归档",order="13",page_show=true,path="ZteOrderBackTraceRule.orderBack")
	public BusiCompResponse orderBack(BusiCompRequest busiCompRequest){
		String orderId = busiCompRequest.getOrder_id();
		BusiDealResult result = ordBackTacheManager.orderBack(orderId);
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code(result.getError_code());
		resp.setError_msg(result.getError_msg());
		return resp;
	}
	
	/**
	 * 下一步(环节跳转)
	 */
	@ZteMethodAnnontion(name="下一步(订单归档)",group_name="订单归档",order="11",page_show=true,path="ZteOrderBackTraceRule.nextStep")
	public BusiCompResponse nextStep(BusiCompRequest busiCompRequest){
		return this.nextflow(busiCompRequest, false,"L");
	}
	
	@ZteMethodAnnontion(name="订单生产结果通知外围",group_name="订单归档",order="13",page_show=true,path="ZteOrderBackTraceRule.orderResultNotify")
	public BusiCompResponse orderResultNotify(BusiCompRequest busiCompRequest){
		BusiCompResponse resp = new BusiCompResponse();
		
		String orderId = busiCompRequest.getOrder_id();
		
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(orderId);
		
		if(orderTree==null || orderTree.getOrderExtBusiRequest()==null){
			resp.setError_code("-999");
			resp.setError_msg("订单信息或订单扩展信息为空！");
			
			return resp;
		}
		
		BusiDealResult result = ordBackTacheManager.orderResultNotify(orderId);
		
		resp.setError_code(result.getError_code());
		resp.setError_msg(result.getError_msg());
		
		return resp;
	}
}
