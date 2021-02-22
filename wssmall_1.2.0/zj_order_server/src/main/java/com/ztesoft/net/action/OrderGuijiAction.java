package com.ztesoft.net.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.params.req.RuleTreeExeReq;
import zte.params.goods.req.CacheGoodsDataReq;
import zte.params.goods.resp.CacheGoodsDataResp;

import com.powerise.ibss.framework.Const;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.service.IGProdCoQueueManager;
import com.ztesoft.net.service.IOrderGuijiManager;
import com.ztesoft.net.service.IOrderInfManager;

public class OrderGuijiAction extends WWAction {

	private String listFormActionVal;
	//外部订单号
	private Map<String,String> params = new HashMap<String,String>();

	private IOrderGuijiManager orderGuijiManager;
	
	private IGProdCoQueueManager gProdCoQueueManager;
	
	private IOrderInfManager orderInfManager;
	
	public String getListFormActionVal() {
		return listFormActionVal;
	}

	public void setListFormActionVal(String listFormActionVal) {
		this.listFormActionVal = listFormActionVal;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public IOrderGuijiManager getOrderGuijiManager() {
		return orderGuijiManager;
	}

	public void setOrderGuijiManager(IOrderGuijiManager orderGuijiManager) {
		this.orderGuijiManager = orderGuijiManager;
	}

	public IGProdCoQueueManager getgProdCoQueueManager() {
		return gProdCoQueueManager;
	}

	public void setgProdCoQueueManager(IGProdCoQueueManager gProdCoQueueManager) {
		this.gProdCoQueueManager = gProdCoQueueManager;
	}

	public String searchOrderGjCoQueue(){
		//CommonDataFactory.getInstance().updateAttrFieldValue("FS201412041869890379", new String[]{AttrConsts.ICCID}, new String[]{"8986011485105973216L"});
		listFormActionVal = "orderGuijiAction!searchOrderGjCoQueue.do";
		this.webpage = this.orderGuijiManager.searchOrderCoQueue(params, this.getPage(), this.getPageSize());
		return "guiji_co_queue_list";
	}
	
	/**
	 * 迁移生产数据到并行库，上线后作废
	 * @return
	 */
	public String moveData(){
		Map log = this.orderGuijiManager.getDataMoveLog();
		String last_move_time = Const.getStrValue(log, "move_time");
		List<CoQueue> queues = this.gProdCoQueueManager.listCoQueue(Consts.SERVICE_CODE_CO_GUIJI, last_move_time);
		this.orderGuijiManager.insertGuijiCoQueue(queues);
		List orderOuter = this.gProdCoQueueManager.listOrderOuter(last_move_time);
		this.orderGuijiManager.insertOrderOuter(orderOuter);
		this.json="{\"result\":\"0\",\"message\":\"成功搬迁"+orderOuter.size()+"条数据\"}";
		return this.JSON_MESSAGE;
	}
	
	public String moveOrderAndQueue(){
		//订单
		OrderOuter orderOuter = this.gProdCoQueueManager.getOrderOuter(params);
		if(orderOuter==null){
			this.json = "{'result':'1','message':'订单不存在'}";
			return this.JSON_MESSAGE;
		}
		List orderOuterList = new ArrayList();
		orderOuterList.add(orderOuter);
		this.orderGuijiManager.insertOrderOuter(orderOuterList);
		//队列
		String tab_name = Const.getStrValue(params, "tb_name");
		String service_code = Const.getStrValue(params, "service_code");
		CoQueue coQueue = this.gProdCoQueueManager.getGuijiCoQueue(tab_name,orderOuter.getOrder_id(),service_code);
		if(coQueue==null){
			this.json = "{'result':'1','message':'队列信息在"+tab_name+"表不存在'}";
			return this.JSON_MESSAGE;
		}
		List<CoQueue> queues = new ArrayList<CoQueue>();
		queues.add(coQueue);
		this.orderGuijiManager.insertGuijiCoQueue(queues);
		
		//商品
		String goods_id = orderOuter.getGoods_id();
		Goods goods = this.gProdCoQueueManager.getGoods(goods_id);
		
		//商品product表数据
		Product product = this.gProdCoQueueManager.getProduct(goods_id);
		
		//es_goods_rel
		List<Map> relations = this.gProdCoQueueManager.getGoodsRelations(goods_id);
		
		//es_goods_package
		List<Map> packages = this.gProdCoQueueManager.getGoodsPackage(goods_id);
		
		//货品es_goods
		List<Goods> products = this.gProdCoQueueManager.getProducts(goods_id);
		
		//货品es_product
		List<Product> productList = this.gProdCoQueueManager.getProductList(goods_id);
		
		List<Map> pmtGoods = this.gProdCoQueueManager.getPmtGoods(goods_id);
		
		List<Map> pmts = this.gProdCoQueueManager.getPromotions(pmtGoods);
		
		List<Map> pmtActs = this.gProdCoQueueManager.getPromotionActivities(pmts);
		
		this.orderGuijiManager.insertOrderData(goods, product, relations, packages, products, productList,pmtGoods,pmts,pmtActs);
		
		this.json = "{'result':'0','message':'数据迁移成功'}";
		return this.JSON_MESSAGE;
	}
	
	public String cacheGoodsData(){
		//缓存数据
		String goods_id = this.orderGuijiManager.getOrderGoodsId(params);
//		Map orderOuter = this.gProdCoQueueManager.getOrderOuter(params);
		CacheGoodsDataReq req = new CacheGoodsDataReq();
		req.setGoods_id(goods_id);
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		CacheGoodsDataResp response = client.execute(req, CacheGoodsDataResp.class);
		this.json = "{'result':'0','message':'数据缓存成功'}";
		return this.JSON_MESSAGE;
	}
	
	public String doGuiji(){
		try{
			//此处调用归集方法
			String result = this.orderGuijiManager.doGuiji(params);
			if("success".equals(result)){
				this.json = "{\"result\":\"0\",\"message\":\"订单归集成功\"}";
			}
			else{
				this.json = "{\"result\":\"-1\",\"message\":\"订单归集失败\"}";
			}
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{\"result\":\"-1\",\"message\":\"订单归集失败\"}";
		}
		return this.JSON_MESSAGE;
	}
	
	public String listOrderSynService(){
		this.webpage = this.orderGuijiManager.listOrderSynService(params, this.getPage(), this.getPageSize());
		return "order_syn_service_list";
	}
	
	public String startOrderSyn(){
		this.orderGuijiManager.startOrderSyn(params);
		this.json = "{\"result\":\"0\",\"message\":\"服务已经启动\"}";
		return this.JSON_MESSAGE;
	}
	
	public String stopOrderSyn(){
		this.orderGuijiManager.stopOrderSyn(params);
		this.json = "{\"result\":\"0\",\"message\":\"服务已经停止\"}";
		return this.JSON_MESSAGE;
	}
	
	public String testRule(){
//		CommonDataFactory.getInstance().updateOrderTree("ZBWO201412174379142702");
//		CommonDataFactory.getInstance().updateAttrFieldValue("SZ201411143284861976", new String[]{AttrConsts.ORDER_MODEL}, new String[]{"02"});
		RuleTreeExeReq req1 = new RuleTreeExeReq();
		req1.setRule_id("201410257194000080"); 
		TacheFact fact = new TacheFact();
		fact.setOrder_id("ZBWO201412174379142702");
		req1.setFact(fact);
		try {
			CommonDataFactory.getInstance().exeRuleTree(req1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		OrderBusiRequest orderBusi = CommonDataFactory.getInstance()
//				.getOrderTree("ST201411144008862000").getOrderBusiRequest();
//		orderBusi.setOrderStatus(EcsOrderConsts.DIC_ORDER_STATUS_6);
//		orderBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
//		orderBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
//		orderBusi.store();
		
		this.json = "{\"result\":\"0\",\"message\":\"成功\"}";
		return this.JSON_MESSAGE;
	}
	
	public String moveBlobData() {
		try {
//			List list = orderInfManager.getBlobList("es_rule_config_audit");
//			List list = orderInfManager.getReqBlobList();
//			gProdCoQueueManager.moveBlobData(list, "inf_comm_client_request");
			this.json = "{'result':'成功'}";
		} catch (Exception e) {
			this.json = "{'result':'失败'}";
		}
		return this.JSON_MESSAGE;
	}

	public IOrderInfManager getOrderInfManager() {
		return orderInfManager;
	}

	public void setOrderInfManager(IOrderInfManager orderInfManager) {
		this.orderInfManager = orderInfManager;
	}

}
