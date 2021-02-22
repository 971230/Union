package zte.net.common.rule;

import java.util.Map;

import javax.annotation.Resource;

import zte.net.common.annontion.context.action.ZteMethodAnnontion;
import zte.net.common.annontion.context.action.ZteServiceAnnontion;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest; 
import zte.net.ecsord.params.ecaop.req.OrderQueryReq;
import zte.net.ecsord.params.ecaop.resp.OrderQueryRespone;
import zte.net.ecsord.params.ecaop.resp.vo.OrderInfoRespVo;
import zte.net.ecsord.rule.tache.TacheFact;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory; 
import com.ztesoft.api.ZteClient;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.ecsord.params.ecaop.req.BusiHandleBSSReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderStatusQryReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.BusiHandleBSSResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderStatusQryResp;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IOrdBSSTacheManager;

import commons.CommonTools;
import consts.ConstsCore;

/**
 * 浙江业务办理
 * 
 * @author Liuquan
 *
 */
@ZteServiceAnnontion(trace_name = "ZteZJBssBusinessTraceRule", trace_id = "1", version = "1.0", desc = "浙江业务办理环节业务通知组件")
public class ZteZJBssBusinessTraceRule extends ZteTraceBaseRule {

	@Resource
	private IOrdBSSTacheManager ordBSSTacheManager;

	/**
	 * 浙江业务办理环节入口
	 */
	@Override
	@ZteMethodAnnontion(name = "浙江业务办理环节入口", group_name = "浙江业务办理", order = "1", page_show = true, path = "ZteZJBssBusinessTraceRule.exec")
	public BusiCompResponse engineDo(BusiCompRequest busiCompRequest) {
		String orderId = busiCompRequest.getOrder_id();
		OrderBusiRequest orderBusiRequest = CommonDataFactory.getInstance().getOrderTree(orderId).getOrderBusiRequest();
		orderBusiRequest.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_55);
		orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusiRequest.store();

		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 行销APP业务办理处理
	 * 
	 * @author liu.quan
	 * @date 2017年4月11日
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "行销APP-活动受理预提交", group_name = "浙江业务办理", order = "1", page_show = true, path = "ZteZJBssBusinessTraceRule.preCommit")
	public BusiCompResponse preCommit(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		Map params = busiCompRequest.getQueryParams();

		//如果是聪充来源的订单
		String order_id = busiCompRequest.getOrder_id();
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
		
		//		如果是自传播来源并且是泛智能终端的单子 by mahui
		String goods_cat = CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.GOODS_CAT_ID);
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String save_terminal_cat_id = cacheUtil.getConfigInfo("save_terminal_cat_id");
		BusiHandleBSSReq requ = new BusiHandleBSSReq();
		if(StringUtils.equals(order_from, "10081")) {
			requ.setNotNeedReqStrOrderId(order_id);
		}else if (StringUtils.equals(order_from, "10093")&& StringUtils.equals(goods_cat,save_terminal_cat_id)) {
			requ.setNotNeedReqStrOrderId(order_id);
		}else {
			TacheFact fact = (TacheFact) params.get("fact");
			requ = (BusiHandleBSSReq) fact.getRequest();
		}
		BusiHandleBSSResp bssResp = client.execute(requ, BusiHandleBSSResp.class);
		if (!StringUtils.equals(bssResp.getCode(), EcsOrderConsts.BSS_SUCCESS_00000)) {
			CommonTools.addBusiError(bssResp.getCode(), bssResp.getMsg());
		}
		String bssOrderId = bssResp.getRespJson().getOrderID();// 预校验返回单号
		// 将BSS返回的单号入库
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(busiCompRequest.getOrder_id());
		OrderItemExtBusiRequest orderItemExtBusiRequest = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest();
		orderItemExtBusiRequest.setBssOrderId(bssOrderId);
		orderItemExtBusiRequest.setActive_no(bssOrderId);
		orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderItemExtBusiRequest.store();

		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 下一步（浙江业务办理）
	 */
	@ZteMethodAnnontion(name = "下一步（浙江业务办理）", group_name = "浙江业务办理", order = "2.1", page_show = true, path = "ZteZJBssBusinessTraceRule.nextStep")
	public BusiCompResponse nextStep(BusiCompRequest busiCompRequest) {
		BusiCompResponse resp = this.nextflow(busiCompRequest, false, "ZJ_O_YWBL");
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	public static void main(String[] args){
		OrderStatusQryResp resp = new OrderStatusQryResp();
		resp.setCode("00000");
		
	}

	private BusiCompResponse bssOrderCheck(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse result = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderStatusQryReq req = new OrderStatusQryReq();
		req.setNotNeedReqStrOrderId(order_id);
		OrderStatusQryResp resp = client.execute(req, OrderStatusQryResp.class);
		if (!resp.getCode().equals(EcsOrderConsts.INF_RESP_CODE_00000)) {
			CommonTools.addBusiError(resp.getCode(), resp.getMsg() + resp.getError_msg());
		} else {
			if (null != resp.getRespJson()) {
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String bssOrderCheck = cacheUtil.getConfigInfo("bssOrderCheck");
				String order_status = resp.getRespJson().getOrder_status();// 0预订单生成;4已作废;8正在执行;9已竣工
				if (bssOrderCheck.contains(order_status)) {// 正常
					result.setError_code("0");
					result.setError_msg("BSS订单校验成功");
					result.setResponse(resp);
					return result;
				} 
			} 
		}
		result.setError_code(resp.getCode());
		result.setError_msg(resp.getMsg() + resp.getError_msg());
		result.setResponse(resp);
		return result;
	}

	private BusiCompResponse cbssOrderCheck(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse result = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderQueryReq req = new OrderQueryReq();
		req.setNotNeedReqStrOrderId(order_id);
		OrderQueryRespone resp = client.execute(req, OrderQueryRespone.class);
		if (!resp.getError_code().equals(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200+"")) {
			CommonTools.addBusiError(resp.getCode(), resp.getDetail() + resp.getError_msg());
		} else {
			if (null != resp.getOrdiInfo() && resp.getOrdiInfo().size() > 0) {
				String bssOrderId = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACTIVE_NO);
				for (OrderInfoRespVo orderInfo : resp.getOrdiInfo()) {
					if (bssOrderId.equals(orderInfo.getOrdersId())) {//单号需要相同
						ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
						String cbssOrderCheck = cacheUtil.getConfigInfo("cbssOrderCheck");
						// 订单状态0：订单成功1：订单失败2：提交未制卡3：订单已返销4：其它
						if (cbssOrderCheck.contains(orderInfo.getOrderCode())) {
							result.setError_code("0");
							result.setError_msg("CBSS订单校验成功");
							result.setResponse(resp);
							return result;
						}
					}
				}
			}

		}
		result.setError_code(resp.getCode());
		result.setError_msg(resp.getDetail() + resp.getError_msg());
		result.setResponse(resp);
		return result;
	}
	
	@ZteMethodAnnontion(name = "BSS/CBSS订单是否存在校验接口", group_name = "BSS/CBSS订单是否存在校验接口", order = "8", page_show = true, path = "ZteZJBssBusinessTraceRule.orderExistCheck")
	public BusiCompResponse orderExistCheck(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse result = new BusiCompResponse();
		
		String order_id = busiCompRequest.getOrder_id();
		
		if(StringUtils.isEmpty(order_id)){
			throw new ApiBusiException("传入的订单编号为空！");
		}
		
		String sysOption = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SYS_OPTION);
		String busOrderId = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BUS_ORDER_ID);
		
		if((!"0".equals(sysOption)) && (!"1".equals(sysOption))){
			throw new ApiBusiException(order_id+"订单中沉淀的SYS_OPTION非法！");
		}
		
		if(StringUtils.isEmpty(busOrderId)){
			throw new ApiBusiException(order_id+"订单中沉淀的BSS/CBSS系统订单编号为空！");
		}
		
		if("0".equals(sysOption)){
			result = this.bssOrderCheck(busiCompRequest);
		}else{
			result = this.cbssOrderCheck(busiCompRequest);
		}
		
		return result;
	}
}
