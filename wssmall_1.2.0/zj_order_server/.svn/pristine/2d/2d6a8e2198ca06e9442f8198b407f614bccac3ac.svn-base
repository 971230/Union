package zte.net.common.rule;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import rule.AbstractRuleHander;
import zte.net.common.annontion.context.action.ZteMethodAnnontion;
import zte.net.common.annontion.context.action.ZteServiceAnnontion;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.rule.orderexp.OrderExpSolutionFact;
import zte.net.iservice.IOrderQueueBaseManager;
import zte.params.orderctn.req.FailAndExpQueueHandleReq;
import zte.params.orderctn.resp.FailAndExpQueueHandleResp;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.model.OrderQueryParams;
import com.ztesoft.net.service.IEcsOrdManager;

import consts.ConstsCore;

/**
 * 异常单处理业务组件
 */
@ZteServiceAnnontion(trace_name = "ZteOrderExpTraceRule", trace_id = "10", version = "1.0", desc = "异常单业务组件")
public class ZteOrderExpTraceRule extends AbstractRuleHander {
	ICacheUtil cacheUtil;
	@Resource
	private IEcsOrdManager ecsOrdManager;

	@ZteMethodAnnontion(name = "异常单过滤", group_name = "自动化模式", order = "1", page_show = true, path = "ZteOrderExpTraceRule.orderExpFilter")
	public BusiCompResponse orderExpFilter(BusiCompRequest busiCompRequest) {
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@ZteMethodAnnontion(name = "异常系统重新标准化订单", group_name = "自动化模式", order = "1", page_show = true, path = "ZteOrderExpTraceRule.afreshStandardizing")
	public BusiCompResponse afreshStandardizing(BusiCompRequest busiCompRequest) {
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code(ConstsCore.ERROR_SUCC);
		
		Map<String, Object> paramsMap = busiCompRequest.getQueryParams();
		OrderExpSolutionFact fact = null;
		if (null == paramsMap) {
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("入参对象[QueryParams]为null!");
			return resp;
		}
		fact = (OrderExpSolutionFact) busiCompRequest.getQueryParams().get("fact");
		String coId = null;
		if (null == fact) {
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("入参对象[Fact]为null!");
			return resp;
		}
		coId = fact.getObj_id();
		IOrderQueueBaseManager orderQueueBaseManager = ApiContextHolder.getBean("orderQueueBaseManager");
		FailAndExpQueueHandleReq faeqhReq = new FailAndExpQueueHandleReq();
		faeqhReq.setCo_id(coId);
		faeqhReq.setIs_exception(true);
		FailAndExpQueueHandleResp faeqhResp = orderQueueBaseManager.failAndExpQueueHandle(faeqhReq);
		if(faeqhResp == null) {
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("队列["+coId+"]标准化失败!");
		}else {
			resp.setError_code(faeqhResp.getError_code());
			resp.setError_msg(faeqhResp.getError_msg());
		}
		return resp;
	}
	@ZteMethodAnnontion(name = "ess资源释放标记完成", group_name = "资源释放", order = "3", page_show = true, path = "ZteOrderExpTraceRule.essReleaseRecord")
	public BusiCompResponse essReleaseRecord(BusiCompRequest busiCompRequest) {
		String order_id = busiCompRequest.getOrder_id();
		BusiCompResponse resp = new BusiCompResponse();
		OrderQueryParams params = new OrderQueryParams();
		params.setOrder_id(order_id);
		params.setType(EcsOrderConsts.AOP_NUM_RELEASE_FAIL);
		Page page = ecsOrdManager.queryReleaseList(1, 20, params);
		List list = page.getResult();
		Map map = (Map)list.get(0);
		String releaseid = map.get("release_id").toString();
		String result = ecsOrdManager.releaseRecord(releaseid);
		if("处理成功".equals(result)){
			resp.setError_code("0");
			resp.setError_msg(result);
		}else{
			resp.setError_code("-1");
			resp.setError_msg(result);
		}
		return resp;
	}
	@ZteMethodAnnontion(name = "bss资源释放标记完成", group_name = "资源释放", order = "4", page_show = true, path = "ZteOrderExpTraceRule.bssReleaseRecord")
	public BusiCompResponse bssReleaseRecord(BusiCompRequest busiCompRequest) {
		String order_id = busiCompRequest.getOrder_id();
		BusiCompResponse resp = new BusiCompResponse();
		OrderQueryParams params = new OrderQueryParams();
		params.setOrder_id(order_id);
		params.setType(EcsOrderConsts.BSS_NUM_RELEASE_FAIL);
		Page page = ecsOrdManager.queryReleaseList(1, 20, params);
		List list = page.getResult();
		Map map = (Map)list.get(0);
		String releaseid = map.get("release_id").toString();
		String result = ecsOrdManager.releaseRecord(releaseid);
		if("处理成功".equals(result)){
			resp.setError_code("0");
			resp.setError_msg(result);
		}else{
			resp.setError_code("-1");
			resp.setError_msg(result);
		}
		return resp;
	}
	@ZteMethodAnnontion(name = "终端资源释放标记完成", group_name = "资源释放", order = "5", page_show = true, path = "ZteOrderExpTraceRule.terReleaseRecord")
	public BusiCompResponse terReleaseRecord(BusiCompRequest busiCompRequest) {
		String order_id = busiCompRequest.getOrder_id();
		BusiCompResponse resp = new BusiCompResponse();
		OrderQueryParams params = new OrderQueryParams();
		params.setOrder_id(order_id);
		params.setType(EcsOrderConsts.TERMINAL_RELEASE_FAIL);
		Page page = ecsOrdManager.queryReleaseList(1, 20, params);
		List list = page.getResult();
		Map map = (Map)list.get(0);
		String releaseid = map.get("release_id").toString();
		String result = ecsOrdManager.releaseRecord(releaseid);
		if("处理成功".equals(result)){
			resp.setError_code("0");
			resp.setError_msg(result);
		}else{
			resp.setError_code("-1");
			resp.setError_msg(result);
		}
		return resp;
	}
	
	public ICacheUtil getCacheUtil() {
		return cacheUtil;
	}

	public void setCacheUtil(ICacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}
	
	public IEcsOrdManager getEcsOrdManager() {
		return ecsOrdManager;
	}

	public void setEcsOrdManager(IEcsOrdManager ecsOrdManager) {
		this.ecsOrdManager = ecsOrdManager;
	}
}
