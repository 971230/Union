/**
 * 
 */
package zte.net.common.rule;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import zte.net.common.annontion.context.action.ZteMethodAnnontion;
import zte.net.common.annontion.context.action.ZteServiceAnnontion;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.warehouse.req.WareHouseMatchReq;
import zte.net.ecsord.params.warehouse.resp.WareHouseMatchResp;
import zte.net.ecsord.rule.warehouse.WareHouseFact;
import zte.params.store.req.GoodsInventoryReduceReq;
import zte.params.store.resp.GoodsInventoryReduceResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.WareHouseVO;
import com.ztesoft.net.service.IWareHouseManager;
import commons.CommonTools;

import consts.ConstsCore;

/**
 * @author ZX
 */
@ZteServiceAnnontion(trace_name="ZteWareHouseTraceRule",trace_id="1",version="1.0",desc="统一仓库规则组件")
public class ZteWareHouseTraceRule extends ZteTraceBaseRule {
	@Resource
	private IWareHouseManager wareHouseManager;

	@ZteMethodAnnontion(name="匹配订单仓库", group_name ="order", order="34",page_show=true,path ="zteOrderTraceRule.matchingWarehouse")
	public BusiCompResponse matchingWarehouse(BusiCompRequest busiCompRequest)
			throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		WareHouseFact fact =  (WareHouseFact) busiCompRequest.getQueryParams().get("fact");
		String api_path = fact.getApi_path();
		List<Object> list = fact.getList();
		fact.setOrder_id(order_id);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		if(!StringUtil.isEmpty(api_path)){
			//调用
			WareHouseMatchReq wareHouseReq = new WareHouseMatchReq();
			wareHouseReq.setWareHouseList(list);
	    	wareHouseReq.setApiMethodName(api_path);
	    	WareHouseMatchResp wareHouseMatchResp = client.execute(wareHouseReq, WareHouseMatchResp.class);
	    	List<Object> houseList = wareHouseMatchResp.getWareHouseList();
	    	if(houseList==null||houseList.size()==0){
	    		//如果没匹配到任何仓库则记录为操作失败  标记为异常
	    		resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("匹配订单仓库失败");
	    	}else if(houseList.size()==1){
	    		//出库
	    		//记录仓库数据到缓存和数据库
	    		resp.setError_code(ConstsCore.ERROR_SUCC);
	    		resp.setError_msg("执行成功");
	    	}else{
	    		//如果匹配到多个仓库，则记录操作成功，执行下一个匹配规则
	    		fact.setList(list);
	    		resp.setFact(fact);
	    		resp.setError_code(ConstsCore.ERROR_SUCC);
	    		resp.setError_msg("执行成功");
	    	}
//	    	if(wareHouseMatchResp.getWareHouseList()!=null&&wareHouseMatchResp.getWareHouseList().size()>0){
//	    		list = wareHouseMatchResp.getWareHouseList();
//	    	}
//	    	fact.setList(list);
//	    	resp.setFact(fact);
//	    	resp.setError_code(ConstsCore.ERROR_SUCC);
//			resp.setError_msg("匹配订单仓库成功");
		}else{
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("未找到匹配方法");
		}
		return resp;
	}
	@ZteMethodAnnontion(name="仓库随机匹配", group_name ="order", order="35",page_show=true,path ="zteOrderTraceRule.matchingWarehouseRandom")
	public BusiCompResponse matchingWarehouseRandom(BusiCompRequest busiCompRequest)
			throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		WareHouseMatchReq req = new WareHouseMatchReq();
		req.setOrder_id(order_id);
		WareHouseFact fact =  (WareHouseFact) busiCompRequest.getQueryParams().get("fact");
		List<Object> list = fact.getList();
		req.setWareHouseList(list);
		WareHouseMatchResp houseResp = wareHouseManager.RandomFirst(req);
		resp.setError_code(houseResp.getError_code());
		resp.setError_msg(houseResp.getError_msg());
		fact.setOrder_id(order_id);
		fact.setList(houseResp.getWareHouseList());
		resp.setFact(fact);
		return resp;
	}
	
	@ZteMethodAnnontion(name="仓库匹配地市优先", group_name ="order", order="4",page_show=true,path ="ZteWareHouseTraceRule.matchingWarehouseByCityCode")
	public BusiCompResponse matchingWarehouseByCityCode(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		WareHouseMatchReq req = new WareHouseMatchReq();
		req.setOrder_id(order_id);
		WareHouseFact fact =  (WareHouseFact) busiCompRequest.getQueryParams().get("fact");
		List<Object> list = fact.getList();
		req.setWareHouseList(list);
		WareHouseMatchResp houseResp = wareHouseManager.cityCodeFirst(req);
		resp.setError_code(houseResp.getError_code());
		resp.setError_msg(houseResp.getError_msg());
		fact.setOrder_id(order_id);
		fact.setList(houseResp.getWareHouseList());
		resp.setFact(fact);
		return resp;
	}
	
	@ZteMethodAnnontion(name="仓库匹配库存优先", group_name ="order", order="5",page_show=true,path ="ZteWareHouseTraceRule.matchingWarehouseHasNum")
	public BusiCompResponse matchingWarehouseHasNum(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		WareHouseMatchReq req = new WareHouseMatchReq();
		req.setOrder_id(order_id);
		WareHouseFact fact =  (WareHouseFact) busiCompRequest.getQueryParams().get("fact");
		List<Object> list = fact.getList();
		req.setWareHouseList(list);
		WareHouseMatchResp houseResp = wareHouseManager.hasNumFirst(req);
		resp.setError_code(houseResp.getError_code());
		resp.setError_msg(houseResp.getError_msg());
		fact.setOrder_id(order_id);
		fact.setList(houseResp.getWareHouseList());
		resp.setFact(fact);
		return resp;
	}

	@ZteMethodAnnontion(name="仓库匹配自营优先", group_name ="order", order="6",page_show=true,path ="ZteWareHouseTraceRule.matchingWarehouseIsOwner")
	public BusiCompResponse matchingWarehouseIsOwner(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		WareHouseMatchReq req = new WareHouseMatchReq();
		req.setOrder_id(order_id);
		WareHouseFact fact =  (WareHouseFact) busiCompRequest.getQueryParams().get("fact");
		req.setWareHouseList(fact.getList());
		WareHouseMatchResp houseResp = wareHouseManager.ownerFirst(req);
		resp.setError_code(houseResp.getError_code());
		resp.setError_msg(houseResp.getError_msg());
		fact.setOrder_id(order_id);
		fact.setList(houseResp.getWareHouseList());
		resp.setFact(fact);
		return resp;
	}
	
	@ZteMethodAnnontion(name="仓库匹配价格优先", group_name ="order", order="7",page_show=true,path ="ZteWareHouseTraceRule.matchingWarehousePrice")
	public BusiCompResponse matchingWarehousePrice(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		WareHouseMatchReq req = new WareHouseMatchReq();
		req.setOrder_id(order_id);
		WareHouseFact fact =  (WareHouseFact) busiCompRequest.getQueryParams().get("fact");
		req.setWareHouseList(fact.getList());
		WareHouseMatchResp houseResp = wareHouseManager.priceFirst(req);
		resp.setError_code(houseResp.getError_code());
		resp.setError_msg(houseResp.getError_msg());
		fact.setOrder_id(order_id);
		fact.setList(houseResp.getWareHouseList());
		resp.setFact(fact);
		return resp;
	}
	
	@ZteMethodAnnontion(name="仓库匹配优先级优先", group_name ="order", order="8",page_show=true,path ="ZteWareHouseTraceRule.matchingWarehousePriority")
	public BusiCompResponse matchingWarehousePriority(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		WareHouseMatchReq req = new WareHouseMatchReq();
		req.setOrder_id(order_id);
		WareHouseFact fact =  (WareHouseFact) busiCompRequest.getQueryParams().get("fact");
		req.setWareHouseList(fact.getList());
		WareHouseMatchResp houseResp = wareHouseManager.priorityFirst(req);
		resp.setError_code(houseResp.getError_code());
		resp.setError_msg(houseResp.getError_msg());
		fact.setOrder_id(order_id);
		fact.setList(houseResp.getWareHouseList());
		resp.setFact(fact);
		return resp;
	}
	
	@ZteMethodAnnontion(name="出库", group_name ="order", order="9",page_show=true,path ="ZteWareHouseTraceRule.enteringWareHouse")
	public BusiCompResponse enteringWareHouse(BusiCompRequest busiCompRequest) throws ApiBusiException{
		ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_WAREHOUSE);
		GoodsInventoryReduceReq req = new GoodsInventoryReduceReq();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderDeliveryBusiRequest delivery = ordertree.getOrderDeliveryBusiRequests().get(0);
		String house_id = delivery.getHouse_id();
		if(StringUtil.isEmpty(house_id)||"-1".equals(house_id)){
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("未匹配仓库");
		}else{
			WareHouseVO house = JsonUtil.fromJson(house_id, WareHouseVO.class);
			List<Object> list = new ArrayList<Object>();
			list.add(house);
			req.setOrder_id(order_id);
			req.setWareHouseList(list);
			GoodsInventoryReduceResp wresp = client.execute(req, GoodsInventoryReduceResp.class);
			resp.setError_code(wresp.getError_code());
			resp.setError_msg(wresp.getError_msg());
		}
		
		return resp;
	}
	
	@ZteMethodAnnontion(name="是否需要匹配仓库", group_name ="order", order="10",page_show=true,path ="ZteWareHouseTraceRule.checkIsMatch")
	public BusiCompResponse checkIsMatch(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		boolean flag = wareHouseManager.isMatch(order_id);
		if(!flag){
			//不需要匹配仓库则执行后面的规则
			resp.setError_code("0");
			resp.setError_msg("执行成功");
		}else{
			//需要匹配仓库则抛异常
			CommonTools.addBusiError("-1", "请先匹配仓库");
		}
		return resp;
	}
	
	@Override
	public BusiCompResponse engineDo(BusiCompRequest traceRequest) {
		return null;
	}

}
