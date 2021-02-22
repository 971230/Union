package zte.net.common.rule;



import javax.annotation.Resource;

import zte.net.common.annontion.context.action.ZteMethodAnnontion;
import zte.net.common.annontion.context.action.ZteServiceAnnontion;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.params.order.req.ShipItemSyncReq;
import zte.params.order.resp.ShipItemSyncResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.IOrdQualityTacheManager;

import commons.CommonTools;
import consts.ConstsCore;


//import org.apache.log4j.Logger;

/**
 *
 * 质检稽核
 * @author wu.i
 */
@ZteServiceAnnontion(trace_name="ZteQualityCheckTraceRule",trace_id="1",version="1.0",desc="质检稽核环节业务通知组件")
public  class ZteQualityCheckTraceRule extends ZteTraceBaseRule {
	
	@Resource
	private IOrdQualityTacheManager ordQualityTacheManager;
	/**
	 *质检稽核环节入口
	 */
	@Override
	@ZteMethodAnnontion(name="质检稽核环节入口",group_name="质检稽核",order="1",page_show=true,path="qualityCheckTraceRule.exec")
	public BusiCompResponse engineDo(BusiCompRequest busiCompRequest) {
		String order_id = busiCompRequest.getOrder_id();
		OrderBusiRequest orderBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderBusiRequest();
		orderBusiRequest.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_6);
		orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusiRequest.store();
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * 物流处理完成签收
	 * @throws Exception 
	 */
	@ZteMethodAnnontion(name="物流处理完成签收(质检稽核)",group_name="质检稽核",order="1",page_show=true,path="ZteQualityCheckTraceRule.wlFinishSignIn")
	public BusiCompResponse wlFinishSignIn(BusiCompRequest busiCompRequest) throws Exception{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		ordQualityTacheManager.wlFinishSignIn(order_id);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}

	/**
	 * 通知WMS质检稽核
	 * @throws Exception 
	 */
	@ZteMethodAnnontion(name="通知WMS质检稽核(质检稽核)",group_name="质检稽核",order="1",page_show=true,path="ZteQualityCheckTraceRule.qualityCheckNotifyToWms")
	public BusiCompResponse qualityCheckNotifyToWms(BusiCompRequest busiCompRequest) throws Exception{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordQualityTacheManager.notifyWMSBeginQualityCheck(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.WMS_INF_RESP_CODE_0000))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	
	

	/**
	 * Bss对账信息同步
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="Bss对账信息同步(质检稽核)",group_name="质检稽核",order="2",page_show=true,path="ZteQualityCheckTraceRule.accountInfoSynToBss")
	public BusiCompResponse accountInfoSynToBss(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordQualityTacheManager.generateBssBillInfo(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	
	/**
	 * 下一步（质检稽核）
	 */
	@ZteMethodAnnontion(name="下一步（质检稽核）",group_name="质检稽核",order="3",page_show=true,path="ZteQualityCheckTraceRule.nextStep")
	public BusiCompResponse nextStep(BusiCompRequest busiCompRequest){
		
		ShipItemSyncReq shipReq = new ShipItemSyncReq();
		shipReq.setOrder_id(busiCompRequest.getOrder_id());
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		client.execute(shipReq, ShipItemSyncResp.class);
		
		BusiCompResponse resp = this.nextflow(busiCompRequest,false,"F");
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
}
