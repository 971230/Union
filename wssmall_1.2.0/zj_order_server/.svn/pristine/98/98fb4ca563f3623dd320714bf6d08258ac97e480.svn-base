package zte.net.common.rule;

import java.util.List;

import javax.annotation.Resource;

import zte.net.common.annontion.context.action.ZteMethodAnnontion;
import zte.net.common.annontion.context.action.ZteServiceAnnontion;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.order.req.GoodsSynWMSReq;
import zte.net.ecsord.params.order.resp.GoodsSynWMSResp;
import zte.net.ecsord.params.wms.req.NotifyOrderInfoWMSReq;
import zte.net.ecsord.params.wms.vo.NotifyGoodInfoVo;
import zte.net.ecsord.params.wms.vo.NotifyOrderInfoVo;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.ICommonManager;
import com.ztesoft.net.service.IOrdPickingTacheManager;

/**
 * 校验系统通用业务组件
 * @author Administrator
 *
 */
@ZteServiceAnnontion(trace_name = "ZteEccCommonTraceRule", trace_id = "1", version = "1.0", desc = "校验系统通用业务组件")
public class ZteEccCommonTraceRule extends ZteTraceBaseRule {

	@Resource
	private IOrdPickingTacheManager ordPickingTacheManager;
	
	@Resource
	private ICommonManager CommonManager;
	
	@Override
	@ZteMethodAnnontion(name = "通用组件入口", group_name = "自动化模式", order = "1", page_show = true, path = "zteEccCommonTraceRule.exec")
	public BusiCompResponse engineDo(BusiCompRequest traceRequest) {
		// TODO Auto-generated method stub
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	
	@ZteMethodAnnontion(name = "重新同步订单到WMS解决方案组件", group_name = "通用组件", order = "2", page_show = true, path = "zteEccCommonTraceRule.synOrderInfoToWMS")
	public BusiCompResponse synOrderInfoToWMS(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		String orderId = busiCompRequest.getOrder_id();
		if(StringUtil.isEmpty(orderId)){
			orderId = busiCompRequest.getQueryParams().get("order_id").toString();
		}
		BusiDealResult result = ordPickingTacheManager.synOrdInfToWMS(orderId);
		if(!EcsOrderConsts.WMS_INF_RESP_CODE_0000.equals(result.getError_code())){
			resp.setError_code(result.getError_code());
			resp.setError_msg(result.getError_msg());
		}
		return resp;
	}
	
	
	@ZteMethodAnnontion(name = "同步订单中实物SKU到WMS解决方案组件", group_name = "通用组件", order = "3", page_show = true, path = "zteEccCommonTraceRule.synSKUToWMS")
	public BusiCompResponse synSKUToWMS(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		String orderId = busiCompRequest.getOrder_id();
		if(StringUtil.isEmpty(orderId)){
			orderId = busiCompRequest.getQueryParams().get("order_id").toString();
		}
		NotifyOrderInfoWMSReq req = new NotifyOrderInfoWMSReq();
		req.setOrderId(orderId);
		List<NotifyOrderInfoVo> list = req.getOrderInfo();
		for(NotifyOrderInfoVo vo : list){
			List<NotifyGoodInfoVo> goodList =  vo.getGoodInfo();
			for(NotifyGoodInfoVo good : goodList){
				GoodsSynWMSReq goodsReq = new GoodsSynWMSReq();
				goodsReq.setAction_code(Consts.ACTION_CODE_A);
				goodsReq.setGoods_id(good.getDetailId());
				goodsReq.setService_code(Consts.SERVICE_CODE_CO_HUOPIN);
				GoodsSynWMSResp goodsResp = CommonManager.synchronizedGoodsToWMS(goodsReq);
//				if(!"0".equals(goodsResp.getError_code())){
//					resp.setError_code(goodsResp.getError_cause());
//					resp.setError_msg(goodsResp.getError_msg());
//				}
			}
		}
		PlanRuleTreeExeReq planTreeExeReq = new PlanRuleTreeExeReq();
		planTreeExeReq.setPlan_id(EcsOrderConsts.YJH_PLAN_ID);
		planTreeExeReq.setDeleteLogs(true);
		TacheFact fact = new TacheFact();
		fact.setOrder_id(orderId);
		planTreeExeReq.setFact(fact);
		PlanRuleTreeExeResp rsp = CommonDataFactory.getInstance().exePlanRuleTree(planTreeExeReq);
		resp.setError_code(rsp.getError_code());
		resp.setError_msg(rsp.getError_msg());
		return resp;
	}	
}
