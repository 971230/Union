package zte.net.jsord.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.collections.map.HashedMap;

import params.ZteBusiRequest;
import params.ZteResponse;
import zte.net.common.params.ZtePofBusiRequest;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.wyg.req.StatuSynchReq;
import zte.net.ecsord.params.wyg.resp.StatuSynchResp;
import zte.net.ecsord.params.zb.vo.orderattr.Fee;
import zte.net.ecsord.params.zb.vo.orderattr.GoodsAtt;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.ecsord.utils.SpecUtils;
import zte.net.iservice.ILogsServices;
import zte.net.iservice.IOrderServices;
import zte.net.iservice.IRuleService;
import zte.net.params.req.CataloguePlanExeReq;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.CataloguePlanExeResp;
import zte.net.params.resp.PlanRuleTreeExeResp;
import zte.net.params.resp.RuleTreeExeResp;
import zte.net.treeInst.RequestStoreExector;
import zte.params.ecsord.req.GetOrderByInfIdReq;
import zte.params.ecsord.req.GetOrderByOutTidReq;
import zte.params.ecsord.req.InsertOrderHandLogReq;
import zte.params.ecsord.resp.GetOrderByInfIdResp;
import zte.params.ecsord.resp.GetOrderByOutTidResp;
import zte.params.goodstype.req.GoodsTypeGetReq;
import zte.params.goodstype.resp.GoodsTypeGetResp;
import zte.params.order.req.ChargebackStatuChgReq;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Brand;
import com.ztesoft.net.mall.core.model.BrandModel;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import commons.CommonTools;

import consts.ConstsCore;


/**
 * 
 * @author wu.i
 * 通用订单操作对象
 * 
 *
 */
public class CommonDataFactory {
	private IRuleService ruleService;
	private ILogsServices logsServices;
	private IOrderServices orderServices;
	
	public static CommonDataFactory dataFactory;
	public static CommonDataFactory getInstance(){
		if(dataFactory ==null)
			dataFactory = new CommonDataFactory();
		return dataFactory;
	}
	
	public void init(){
		this.ruleService = SpringContextHolder.getBean("ruleService");
		this.orderServices = SpringContextHolder.getBean("orderServices");
		this.logsServices = SpringContextHolder.getBean("logsServices");
	};
	/**
	 * 获取订单树
	 * @param order_id
	 * @param args
	 *     need_query  是否查询字库表节点
	 *     not_query_cache 是够使用内存原有数据
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public OrderTreeBusiRequest getOrderTree(String order_id,Object ...args){
		OrderTreeBusiRequest orderTree = new OrderTreeBusiRequest();
		Map<String,String> queryParams = new HashedMap();
		queryParams.put("order_id", order_id);
		String query_from_cache =(args.length == 0  || StringUtil.isEmpty((String)args[0]))? ConstsCore.CONSTS_YES :(String)args[0];
		String need_query_next =(args.length == 1 || args.length == 0 || StringUtil.isEmpty((String)args[1]))? ConstsCore.CONSTS_NO :(String)args[1];
		String query_his_table =(args.length == 1 || args.length == 0  || args.length == 2 || StringUtil.isEmpty((String)args[2]))? ConstsCore.CONSTS_NO :(String)args[2];
		orderTree.setNeed_query(need_query_next);
		orderTree.setOrder_id(order_id);
		queryParams.put("query_from_cache",query_from_cache);
		queryParams.put("query_his_table", query_his_table);
		ZteInstQueryRequest<OrderTreeBusiRequest> instParam = new ZteInstQueryRequest<OrderTreeBusiRequest>(); 
		instParam.setQueryParmas(queryParams);
		instParam.setQueryObject(orderTree);
//		long start1 = System.currentTimeMillis();
		OrderTreeBusiRequest  treeBusiRequest = RequestStoreExector.getInstance().getZteRequestInst(instParam);
//		long start2 = System.currentTimeMillis();
//		logger.info("订单tree执行时间===============================================>"+(start2-start1));
		//判断全视图内存树是否已渲染，未渲染则再次渲染（首次订单标准化OrderStandardizing.syncOrderStandardizing异步渲染一次）。
//		if(ConstsCore.CONSTS_YES.equals(is_continue) && treeBusiRequest !=null && !StringUtil.isEmpty(treeBusiRequest.getOrder_id()) && StringUtil.isEmpty(treeBusiRequest.getOrderBusiRequest().getOrder_id())){
//			CommonDataFactory.getInstance().updateOrderTree(treeBusiRequest.getOrder_id(),ConstsCore.CONSTS_YES); 
//			return this.getOrderTree(order_id, query_from_cache,need_query_next,ConstsCore.CONSTS_NO);
//		}
		return treeBusiRequest;
	}
	
	/**
	 * 根据外部订单编号获取OrderTree
	 * @param outt_id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public OrderTreeBusiRequest getOrderTreeByOutId(String outt_id){
		//获取外部订单编号
		GetOrderByOutTidReq oreq = new GetOrderByOutTidReq();
		oreq.setOut_tid(outt_id);
		ZteClient client= ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
		GetOrderByOutTidResp oresp = client.execute(oreq, GetOrderByOutTidResp.class);
		String order_id = oresp.getOrder_id();
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(order_id);
		return tree;
	}
	
	/**
	 * 根据外部订单编号获取OrderTree
	 * @param zb_inf_id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public OrderTreeBusiRequest getOrderTreeByZBInfId(String zb_inf_id){
		//获取外部订单编号
		GetOrderByInfIdReq oreq = new GetOrderByInfIdReq();
		oreq.setZb_inf_id(zb_inf_id);
		ZteClient client= ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
		GetOrderByInfIdResp oresp = client.execute(oreq, GetOrderByInfIdResp.class);
		String order_id = oresp.getOrder_id();
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(order_id);
		return tree;
	}
	
			
	/**
	 * 获取历史订单树
	 * @param order_id
	 * @param args
	 *     need_query  是否查询字库表节点
	 *     not_query_cache 是够使用内存原有数据
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public OrderTreeBusiRequest getOrderTreeHis(String order_id){
		OrderTreeBusiRequest orderTreeBusiRequest =  getOrderTree(order_id, ConstsCore.CONSTS_YES,ConstsCore.CONSTS_YES,ConstsCore.CONSTS_YES);
		orderTreeBusiRequest.setCol10(ConstsCore.CONSTS_YES);//查询历史订单
		return orderTreeBusiRequest;
//		OrderTreeHisBusiRequest orderTree = new OrderTreeHisBusiRequest();
//		Map<String,String> queryParams = new HashedMap();
//		queryParams.put("order_id", order_id);
//		String query_from_cache =(args.length == 0  || StringUtil.isEmpty((String)args[0]))? ConstsCore.CONSTS_YES :(String)args[0];
//		String need_query_next =(args.length == 1 || args.length == 0 || StringUtil.isEmpty((String)args[1]))? ConstsCore.CONSTS_NO :(String)args[1];
//		orderTree.setNeed_query(need_query_next);
//		queryParams.put("query_from_cache",query_from_cache);
//		ZteInstQueryRequest<OrderTreeHisBusiRequest> instParam = new ZteInstQueryRequest<OrderTreeHisBusiRequest>(); 
//		instParam.setQueryParmas(queryParams);
//		instParam.setQueryObject(orderTree);
//		orderTree = RequestStoreExector.getInstance().getZteRequestInst(instParam);
//		return orderTree;
	}
	
	/**
	 * 获取订单树,根据order_id 获取es_order_tree实体数据
	 * @param order_id
	 * @param query_from_cache 缺省值为yes
	 */
	@SuppressWarnings("rawtypes")
	public OrderTreeBusiRequest getOrderTree(ZteBusiRequest zteRequest,Object ...args){
		try {
			String query_from_cache =(args.length == 0  || StringUtil.isEmpty((String)args[0]))? ConstsCore.CONSTS_YES :(String)args[0];
			String need_query_next =(args.length == 1 || args.length == 0 || StringUtil.isEmpty((String)args[1]))? ConstsCore.CONSTS_NO :(String)args[1];
			String order_id = (String) MethodUtils.invokeMethod(zteRequest, "getOrder_id", null);
			if(StringUtil.isEmpty(order_id))
				CommonTools.addFailError("订单编号为空，更新订单树失败");
			return getOrderTree(order_id,query_from_cache,need_query_next);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 全量更新订单树
	 * @param zteRequest
	 */
	@SuppressWarnings("rawtypes")
	public void updateOrderTree(String order_id,Object ...args){
		String firstGlobal = ConstsCore.CONSTS_NO;
		if(args.length !=0)
			firstGlobal = (String)args[0];
		this.init();
		//全量查询订单树
//		long start = System.currentTimeMillis();
		OrderTreeBusiRequest treeBusiRequest =getOrderTree(order_id,ConstsCore.CONSTS_NO,ConstsCore.CONSTS_YES,ConstsCore.CONSTS_NO);
//		long end = System.currentTimeMillis();
//		logger.info("对象操作总耗时："+(end-start));
		//更新OrderTree
		treeBusiRequest.setIs_dirty(ConstsCore.CONSTS_YES);
		//全量数据存储
		List<String> changeFields = new ArrayList<String>();
		changeFields.add(EcsOrderConsts.ALL_FIELDS);
		treeBusiRequest.setChangeFiels(changeFields);
		treeBusiRequest.setUpdate_time(Consts.SYSDATE);
		//立即放入内存
		OrderTreeBusiRequest oldBusiRequest = logsServices.getOrderTreeFromCache(order_id);
		//首次全量更新，当内存中已存在，则不在覆盖保存
		if(ConstsCore.CONSTS_YES.equals(firstGlobal) && oldBusiRequest !=null && !StringUtil.isEmpty(oldBusiRequest.getOrder_id()) && !StringUtil.isEmpty(oldBusiRequest.getOrderBusiRequest().getOrder_id()))
			return;
		try {logsServices.cacheOrderTree(treeBusiRequest);} catch (ApiBusiException e) {}//更新完成立即设置缓存
		treeBusiRequest.store();
	}
	
	/**
	 * 订单归集首次，将库表信息写入订单树
	 * @param order_id
	 */
//	@SuppressWarnings("rawtypes")
//	public void updateOrderTreeFirstAsy(String order_id){
//		updateOrderTreeAsy(order_id,ConstsCore.CONSTS_YES);
//	}
	
//	/**
//	 * 异步全量更新订单树
//	 * 
//	 * 1、数据库信息全量写入ordertree
//	 * 2、orderTree数据信息入库
//	 * @param zteRequest
//	 */
//	@SuppressWarnings("rawtypes")
//	public void updateOrderTreeAsy(String order_id,String first_global){
//			OrderAddReq zteRequest = new OrderAddReq() ;
//			Map params = new HashedMap();
//			params.put("order_id", order_id);
//			params.put("first_global", first_global);
//			zteRequest.setParams(params);
//			TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(zteRequest) {
//				public ZteResponse execute(ZteRequest zteRequest) {
//					//全量查询订单树'
//					OrderAddReq req = (OrderAddReq)zteRequest;
//					String order_id = (String)req.getParams().get("order_id");
//					String first_global = (String)req.getParams().get("first_global");
//					CommonDataFactory.getInstance().updateOrderTree(order_id,first_global);
//					return new ZteResponse();
//				}
//			});
//			ThreadPoolFactory.cacheExecute(taskThreadPool);
//	}
//	
//	
	/**
	 * 全量更新订单树
	 * @param zteRequest
	 */
	@SuppressWarnings("rawtypes")
	public void updateOrderTree(ZteBusiRequest zteRequest){
		//全量查询订单树
		OrderTreeBusiRequest treeBusiRequest =getOrderTree(zteRequest,ConstsCore.CONSTS_NO,ConstsCore.CONSTS_YES);
		
		//更新OrderTree
		treeBusiRequest.setIs_dirty(ConstsCore.CONSTS_YES);
		treeBusiRequest.store();
	}
	
	/**
	 * 从订单树取属性值
	 * @return
	 */
	public String getAttrFieldValue(String order_id,String field_name){
		return AttrUtils.getInstance().getAttrFieldValue(order_id, field_name,"","");
	}
	
	/**
	 * 从历史订单树取属性值
	 * @return
	 */
	public String getAttrFieldValueHis(String order_id,String field_name){
		return AttrUtils.getInstance().getAttrFieldValueHis(order_id, field_name,"","");
	}

	/**
	 * 从订单树取属性值
	 * @return
	 */
	@Deprecated
	public String getAttrFieldValue(OrderTreeBusiRequest tree,String order_id,String field_name){
		return AttrUtils.getInstance().getAttrFieldValue(tree,order_id, field_name,"","");
	}
	
	
	/**
	 * 从订单树取属性值
	 * @return
	 */
	public String getAttrFieldValue(String order_id,String field_name,String table_name,String spec_id){
		return AttrUtils.getInstance().getAttrFieldValue(order_id, field_name,table_name,spec_id);
	}
	
	/**
	 * 从历史订单树取属性值
	 * @return
	 */
	public String getAttrFieldValueHis(String order_id,String field_name,String table_name,String spec_id){
		return AttrUtils.getInstance().getAttrFieldValueHis(order_id, field_name,table_name,spec_id);
	}

	/**
	 * 从订单树取属性值
	 * @return
	 */
	@Deprecated
	public String getAttrFieldValue(OrderTreeBusiRequest tree,String order_id,String field_name,String table_name,String spec_id){
		return AttrUtils.getInstance().getAttrFieldValue(tree,order_id, field_name,table_name,spec_id);
	}
	
	
	
	/**
	 * 从属性实例表取属性值，主要针对赠品信息，优惠信息等json格式的属性值
	 * @param order_id
	 * @param field_name
	 * @return
	 */
//	public String getAttrFieldValueFromDB(String order_id,String field_name){
//		return AttrUtils.getInstance().getAttrFieldValueFromDB(order_id, field_name);
//	}
//	
	/**
	 * 判断货品是否赠品
	 * @param goods_id 货品goods_id
	 * @return
	 */
	public String isGift(String goods_id){
		return SpecUtils.isGift(goods_id);
	}
	
	/**
	 * 判断订单是否总部
	 * @param plat_type 平台类型
	 * @return
	 */
	public boolean isZbOrder(String plat_type){
		return AttrUtils.getInstance().isZbOrder(plat_type);
	}
	
	/**
	 * 取合约编码、活动编码
	 * @param goods_id 商品ID
	 * @return
	 */
	public String getPCode(String goods_id){
		return SpecUtils.getPCode(goods_id);
	}
	
	/**
	 * 是否总部合约，order_from和goods_id不能为空
	 * @param order_from 订单来源
	 * @param goods_id 商品ID
	 * @return
	 */
	public String isGroupContract(String order_from, String goods_id){
		return SpecUtils.isGroupContract(order_from, goods_id);
	}
	
	/**
	 * 判断货品是否虚拟货品
	 * @param prod_goods_id 货品ID
	 * @return
	 */
	public String isVirtualProduct(String prod_goods_id){
		String is_virtual = SpecUtils.isVirtualProduct(prod_goods_id);
		return is_virtual;
	}

	/**
	 * 判断订单商品单中是否有实物货品，用作判断是否出货的条件
	 * order_id和goods_id不能同时为空
	 * @param order_id 订单ID
	 * @param goods_type 商品类型
	 * @return
	 */
	public String hasEntityProduct(String order_id, String goods_type){
		return SpecUtils.hasEntityProduct(order_id, goods_type);
	}
	
	/**
	 * 判断订单中是否有终端货品，有则返回“1”，没有则返回“0”
	 * @param order_id
	 * @return
	 */
	public String hasTerminal(String order_id){
		return SpecUtils.hasTypeOfProduct(order_id,EcsOrderConsts.PRODUCT_TYPE_TERMINAL);
	}
	
	/**
	 * 判断订单中是否有指定类型的货品，有则返回“1”，没有则返回“0”
	 * @param order_id
	 * @return
	 */
	public String hasTypeOfProduct(String order_id,String type_id){
		return SpecUtils.hasTypeOfProduct(order_id,type_id);
	}
	/**
	 * 历史单
	 * 判断订单中是否有指定类型的货品，有则返回“1”，没有则返回“0”
	 * @param order_id
	 * @return
	 */
	public String hasTypeOfProductHis(String order_id,String type_id){
		return SpecUtils.hasTypeOfProductHis(order_id,type_id);
	}
	
	/**
	 * 取号码规格信息
	 * @param number 号码
	 * @param field_name 字段名
	 * @return
	 */
	public String getNumberSpec(String number, String field_name){
		return SpecUtils.getNumberSpec(number, field_name);
	}
	
	public Map getGoodsSpecMap(String order_id, String goods_id){
		return SpecUtils.getGoodsSpecMap(order_id, goods_id);
	}
	
	public Map getProductSpecMap(String order_id, String type_id){
		return SpecUtils.getProductSpecMap(order_id, type_id);
	}
	
	/**
	 * 取商品规格信息
	 * 1、key不能为空
	 * 2、如果goods_id为空，order_id必填，根据order_id得到goods_id，再取商品规格信息
	 * 3、如果goods_id不为空，根据goods_id取商品规格信息
	 * @param order_id 订单ID
	 * @param goods_id 商品ID
	 * @param key 规格字段，查看SpecConsts.java
	 * @return
	 */
	public String getGoodSpec(String order_id, String goods_id, String field_name){
		return SpecUtils.getGoodsSpec(order_id,goods_id, field_name);
	}
	/**
	 * 历史单 
	 * 取商品规格信息
	 * 1、key不能为空
	 * 2、如果goods_id为空，order_id必填，根据order_id得到goods_id，再取商品规格信息
	 * 3、如果goods_id不为空，根据goods_id取商品规格信息
	 * @param order_id 订单ID
	 * @param goods_id 商品ID
	 * @param key 规格字段，查看SpecConsts.java
	 * @return
	 */
	public String getGoodSpecHis(String order_id, String goods_id, String field_name){
		return SpecUtils.getGoodsSpecHis(order_id,goods_id, field_name);
	}
	
	/**
	 * 获取货品规格信息
	 * 1、field_name不能为空
	 * 2、如果入参goods_id为空，order_id和type_id必填，根据order_id和type_id得到goods_id，再取货品规格信息；
	 * 3、如果goods_id不为空，根据goods_id取货品规格信息
	 * @param order_id 订单ID
	 * @param goods_id 货品ID
	 * @param type_id 货品类型ID，查看SpecConsts.java
	 * @param field_name 规格字段，查看SpecConsts.java
	 * @return
	 */
	public String getProductSpec(String order_id, String type_id, String goods_id, String field_name){
		return SpecUtils.getProductSpec(order_id, type_id, goods_id, field_name);
	}
	/**
	 * 获取货品规格信息--历史单  还没实现
	 * 1、field_name不能为空
	 * 2、如果入参goods_id为空，order_id和type_id必填，根据order_id和type_id得到goods_id，再取货品规格信息；
	 * 3、如果goods_id不为空，根据goods_id取货品规格信息
	 * @param order_id 订单ID
	 * @param goods_id 货品ID
	 * @param type_id 货品类型ID，查看SpecConsts.java
	 * @param field_name 规格字段，查看SpecConsts.java
	 * @return
	 */
	public String getProductSpecHis(String order_id, String type_id, String goods_id, String field_name){
		return SpecUtils.getProductSpecHis(order_id, type_id, goods_id, field_name);
	}
	
	/**
	 * 获取货品规格信息
	 * 1、field_name不能为空
	 * @param order_id 订单ID
	 * @param type_id 货品类型ID，查看SpecConsts.java
	 * @param field_name 规格字段，查看SpecConsts.java
	 * @return
	 */
	public String getProductSpec(String order_id, String type_id, String field_name){
		return SpecUtils.getProductSpec(order_id, type_id, field_name);
	}
	
	/**
	 * 获取活动规格信息：优惠信息，活动名称，key值见SpecConsts.java
	 * 如果key为空，直接返回null
	 * @param activity_code 活动编码【pmt_code】
	 * @param key 规格字段，见SpecConsts.java活动规格常量
	 * @return
	 */
	public String getActivitySpec(String activity_code,String field_name){
		String value = SpecUtils.getActivitySpec(activity_code,field_name);
		return value;
	}
	
	/**
	 * 获取号卡SKU
	 * @return
	 */
	public String getNumberSku(String order_id,String goods_type,String specification_code,String sim_type,String white_card_type){
		return SpecUtils.getNumberSku(order_id,goods_type,specification_code,sim_type,white_card_type);
	}
	
	/**
	 * 判断是否合约机、号卡、上网卡，是返回true，否则返回false
	 * @param order_id 订单ID
	 * @param goods_id 商品ID
	 * @return true/false
	 */
	public boolean isHyjHkSwk(String order_id,String goods_id){
		return SpecUtils.isHyjHkSwk(order_id, goods_id);
	}

	/**
	 * 返回ActiveNo访问流水:总部订单的访问流水前缀EM；非总部的访问流水前缀P51
	 * @param isZbOrder
	 * @return
	 */
	public String getActiveNo(boolean isZbOrder){
		return SpecUtils.getActiveNo(isZbOrder);
	}
	
	
	
	/**
	 * 返回定制流程
	 * @param order_id
	 * @return
	 */
	public String getOrderFlow(String order_id){
		return SpecUtils.getOrderFlow(order_id);
	}
	
	/**
	 * 根据字典编码取静态数据【es_dc_public_ext】
	 * @param stype
	 * @return
	 */
	public List listDcPublicData(String stype){
		return AttrUtils.getInstance().listDcPublicData(stype);
	}
	/**
	 * 根据字典编码和pkey取静态数据pname【es_dc_public_ext】
	 * @param stype
	 * @param pkey
	 * @return pname
	 */
	public String  getDcPublicDataByPkey(String stype,String pkey){
		return AttrUtils.getInstance().getDcPublicDataByPkey(stype,pkey);
	}
	
	/**
	 * 根据字典编码取静态数据【es_dc_public_ext】
	 * @param stype
	 * @return
	 */
	public List listDropDownData(String attrCode){
		return AttrUtils.getInstance().listDropDownData(attrCode);
	}
	/**
	 * 取物流公司联系人
	 * @param post_id 物流公司编码
	 * @param city_code 地市编码
	 * @return
	 */
	public Map getLogiCompPersonData(String post_id, String city_code){
		return AttrUtils.getInstance().getLogiCompPersonData(post_id,city_code);
	}
	/**
	 * 取系统映射值
	 * @param stype 字典编码
	 * @param other_dict_code 原始值
	 * @return 映射值
	 */
	public String getDictCodeValue(String stype, String other_dict_code){
		return AttrUtils.getInstance().getDictCodeValue(stype, other_dict_code);
	}
	
	/**
	 * 取外系统映射值
	 * @param stype 字典编码
	 * @param dict_code 原始值
	 * @return 映射值
	 */
	public String getOtherDictVodeValue(String stype, String dict_code){
		return AttrUtils.getInstance().getOtherDictCodeValue(stype, dict_code);
	}
	
	public String getZbDictCodeValue(String other_system, String field_name, String field_attr_id, String field_value){
		return AttrUtils.getInstance().getZbDictCodeValue(other_system, field_name, field_attr_id, field_value);
	}
	/**
	 * 取订单外部状态
	 * @param stype 字典编码，见EcsOrderConsts.java
	 * @return  	
	 */
	public List getDictRelationListData(String stype){
		return AttrUtils.getInstance().getDictRelationListData(stype);
	}
	
	
	/**
	 * 根据商品type_id查询映射到总部的货品类型【商品里的食物货品】
	 * @param type_id 货品类型
	 * @return
	 */
	public String getZbProductType(String type_id){
		return AttrUtils.getInstance().getZbProductType(type_id);
	}
	
	/**
	 * 根据商品field_name查询映射到总部的值
	 * @param field_name 属性名称
	 * @return
	 */
	public String getZbValue(String order_id, String field_name){
		return AttrUtils.getInstance().getZbValue(order_id, field_name);
	}
	
	/**
	 * 获取默认串号
	 * @param order_id
	 * @return
	 */
	public String getTerminalNum(String order_id){
		String terminalNum = SpecUtils.getTerminalNum(order_id);
		return StringUtil.isEmpty(terminalNum) ? "" : terminalNum;
	}
	
	/**
	 * 根据赠品名称取赠品信息
	 * @param name
	 * @return
	 */
	public Map getVProductByName(String name){
		return SpecUtils.getProductByName(name);
	}
	
	/**
	 * 获取销售方式
	 * @param order_id 订单ID
	 * @return
	 */
	public String getSaleMode(String order_id){
		return AttrUtils.getInstance().getSaleMode(order_id);
	}
	
	/**
	 * 取订单商品附属信息
	 * @param order_id
	 * @return
	 */
	public List<GoodsAtt> getGoodsAttrInfo(String order_id){
		return AttrUtils.getInstance().getGoodsAttrInfo(order_id);
	}
	
	public List<Fee> getFeeInfo(String order_id){
		return AttrUtils.getInstance().getFeeInfo(order_id);
	}
	
	/**
	 * 取实物货品
	 * @param order_id
	 * @param goods_id
	 * @return
	 */
	public List<Goods> getEntityProducts(String order_id){
		return SpecUtils.getEntityProducts(order_id);
	}
	/**
	 * 取商品基本信息
	 * @param sku
	 * @return
	 */
	public Goods getGoodsBySku(String sku){
		return SpecUtils.getGoodsBySku(sku);
	}
	
	
	/**
	 * 查询商品货品类型列表
	 * @param type 查询商品类型传goods，查询货品类型传product，不传查询全部
	 * @return
	 */
	public List<GoodsType> getGoodsTypeList(String type){
		return SpecUtils.getGoodsTypeList(type);
	}
	
	/**
	 * 根据类型ID查询品牌
	 * @param type_id
	 * @return
	 */
	public List<Brand> getBrandByTypeId(String type_id){
		return SpecUtils.getBrandByTypeId(type_id);
	}
	
	/**
	 * 根据品牌ID查询型号
	 * @param brand_id
	 * @return
	 */
	public List<BrandModel> getModelByBrandId(String brand_id){
		return SpecUtils.getModelByBrandId(brand_id);
	}
	
	/**
	 * 根据类型ID查询分类列表
	 * @param type_id 
	 * @return
	 */
	public List<Cat> getCatsByTypeId(String type_id){
		return SpecUtils.getCatsByTypeId(type_id);
	}
	
	/**
	 * 取本地网地市编码
	 * @param order_city_code 订单归属地市
	 * @return
	 */
	public String getLanCode(String order_city_code){
		return SpecUtils.getLanCode(order_city_code);
	}
	
	/**
	 * 根据type_code获取type_id
	 * @param type_code
	 * @return
	 */
	public String getTypeIdByTypeCode(String type_code){
		return SpecUtils.getTypeIdByTypeCode(type_code);
	}
	
	/**
	 * 根据物流公司ID获取物流公司编码
	 * @param logi_company_id
	 * @return
	 */
	public String getLogiCompanyCode(String logi_company_id){
		return SpecUtils.getLogiCompanyCode(logi_company_id);
	}
	
	/**
	 * 去本地网地市名称
	 * @param order_city_code
	 * @return
	 */
	public String getLocalName(String order_city_code){
		return SpecUtils.getLocalName(order_city_code);
	}
	
	/**
	 * 获取寄件方信息
	 * @param order_city_code 订单归属地市
	 * @return
	 */
	public Map getPostRegion(String order_city_code){
		return SpecUtils.getPostRegion(order_city_code);
	}
	
	public String getFeatureFromTaobao(String order_id, String out_tid){
		return AttrUtils.getInstance().getFeatureFromTaobao(order_id, out_tid);
	}
	
	/**
	 * 调用全部总部接口
	 * @param order_id
	 */

	public void callInterface(String order_id){
		ZteClient client24 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		StatuSynchReq req24 = new StatuSynchReq();
		req24.setORDER_ID(order_id);
		req24.setTRACE_CODE("O");
		req24.setTRACE_NAME("11");
		req24.setDEAL_STATUS("1");
		req24.setDEAL_DESC("1");
		req24.setORDER_ID_ECS("ORDER_ID_ECS");
		req24.setSUCC_SKU_LST("SUCC_SKU_LST");
		StatuSynchResp resp24 = client24.execute(req24, StatuSynchResp.class); 
//		ZteClient client71 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
//		GetTaobaoOrderInfoTAOBAORequset req71 = new GetTaobaoOrderInfoTAOBAORequset();
//		req71.setNotNeedReqStrOrderId(order_id);
//		GetTaobaoOrderInfoTAOBAOResponse resp71 = client71.execute(req71, GetTaobaoOrderInfoTAOBAOResponse.class);

//		ZteClient client73 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
//		SynchronousOrderTBRequset req73 = new SynchronousOrderTBRequset();
//		req73.setNotNeedReqStrOrderId(order_id);
//		SynchronousOrderTBResponse resp73 = client73.execute(req73, SynchronousOrderTBResponse.class);

		/*		
		//订单信息变更 J101
		ZteClient client1 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		NotifyOrderInfoZBRequest req1 = new NotifyOrderInfoZBRequest();
		req1.setOrderId(order_id);
		NotifyOrderInfoZBResponse resp1 = client1.execute(req1, NotifyOrderInfoZBResponse.class);

		//订单信息同步到总部 J102
		ZteClient client2 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		SynchronousOrderZBRequest req2 = new SynchronousOrderZBRequest();
		req2.setOrderId(order_id);
		SynchronousOrderZBResponse resp2 = client2.execute(req2, SynchronousOrderZBResponse.class);

		//货品信息通知 J103
		ZteClient client3 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		NotifyStringZBRequest req3 = new NotifyStringZBRequest();
		req3.setOrderId(order_id);
		NotifyStringZBResponse resp3 = client3.execute(req3, NotifyStringZBResponse.class);

		//写卡数据查询 J106
		ZteClient client6 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		PrecheckOpenAcctRequest req6 = new PrecheckOpenAcctRequest();
		req6.setOrderId(order_id);
		PrecheckOpenAcctResponse resp6 = client6.execute(req6, PrecheckOpenAcctResponse.class);
		
		//写卡数据查询 J107
		ZteClient client7 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		QryCRMInfo2CardRequest req7 = new QryCRMInfo2CardRequest();
		req7.setOrderId(order_id);
		QryCRMInfo2CardResponse resp7 = client7.execute(req7, QryCRMInfo2CardResponse.class);

		//写卡数据查询 J108
		ZteClient client8 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		NotifyWriteCardInfoRequest req8 = new NotifyWriteCardInfoRequest();
		req8.setOrderId(order_id);
		NotifyWriteCardInfoResponse resp8 = client8.execute(req8, NotifyWriteCardInfoResponse.class);
		
		//写卡数据查询 J110
		ZteClient client10 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		NotifyDeliveryZBRequest req10 = new NotifyDeliveryZBRequest();
		req10.setOrderId(order_id);
		NotifyDeliveryResponse resp10 = client10.execute(req10, NotifyDeliveryResponse.class);
		
		//订单异常通知 J111
		ZteClient client11 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		NotifyOrderAbnormalToZBRequest req11 = new NotifyOrderAbnormalToZBRequest();
		req11.setOrderId(order_id);
		NotifyOrderAbnormalToZBResponse resp11 = client11.execute(req11, NotifyOrderAbnormalToZBResponse.class);
		
		//状态同步 J115
		ZteClient client15 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		StateSynToZBRequest req15 = new StateSynToZBRequest();
		req15.setOrderId(order_id);
		StateSynToZBResponse resp15 = client15.execute(req15, StateSynToZBResponse.class);

		ZteClient client21 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		NotifyOrderInfoWYGRequset req21 = new NotifyOrderInfoWYGRequset();
		req21.setNotNeedReqStrOrderId(order_id);
		NotifyOrderInfoWYGResponse resp21 = client21.execute(req21, NotifyOrderInfoWYGResponse.class); 

		ZteClient client24 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		StatuSynchReq req24 = new StatuSynchReq();
		req24.setORDER_ID("ORDER_ID");
		req24.setACTIVE_NO("ACTIVE_NO");
		req24.setTRACE_CODE("TRACE_CODE");
		req24.setTRACE_NAME("TRACE_NAME");
		req24.setDEAL_STATUS("DEAL_STATUS");
		req24.setDEAL_DESC("DEAL_DESC");
		req24.setORDER_ID_ECS("ORDER_ID_ECS");
		req24.setSUCC_SKU_LST("SUCC_SKU_LST");
		StatuSynchResp resp24 = client24.execute(req24, StatuSynchResp.class); 

		ZteClient client31 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		NotifyOrderInfoWMSReq req31 = new NotifyOrderInfoWMSReq();
		req31.setOrderId(order_id);
		NotifyOrderInfoWMSResp resp31 = client31.execute(req31, NotifyOrderInfoWMSResp.class);
			
		ZteClient client41 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		SSOLoginBSSReq req41 = new SSOLoginBSSReq();
		req41.setNotNeedReqStrOrderId(order_id);
		SSOLoginBSSResp resp41 = client41.execute(req41, SSOLoginBSSResp.class); 
		
		ZteClient client45 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		ActionRecvBSSReq req45 = new ActionRecvBSSReq();
		req45.setORDER_ID(order_id);
		ActionRecvBSSResp resp45 = client45.execute(req45, ActionRecvBSSResp.class); 

		ZteClient client51 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		ReadICCIDSRRequset req51 = new ReadICCIDSRRequset();
		req51.setNotNeedReqStrOrderId(order_id);
		ReadICCIDSRResponse resp51 = client51.execute(req51, ReadICCIDSRResponse.class); 

					
		//国政通 J61
		ZteClient client61 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		CheckIDECAOPRequset req61 = new CheckIDECAOPRequset();
		req61.setOrderId(order_id);
		CheckIDECAOPResponse resp61 = client61.execute(req61, CheckIDECAOPResponse.class);	
		
		ZteClient client71 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		GetTaobaoOrderInfoTAOBAORequset req71 = new GetTaobaoOrderInfoTAOBAORequset();
		req71.setNotNeedReqStrOrderId(order_id);
		GetTaobaoOrderInfoTAOBAOResponse resp71 = client71.execute(req71, GetTaobaoOrderInfoTAOBAOResponse.class);

		ZteClient client73 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		SynchronousOrderTBRequset req73 = new SynchronousOrderTBRequset();
		req73.setNotNeedReqStrOrderId(order_id);
		req73.setMemo("123321123");
		SynchronousOrderTBResponse resp73 = client73.execute(req73, SynchronousOrderTBResponse.class);

		ZteClient client81 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		NotifyOrderInfoNDRequset req81 = new NotifyOrderInfoNDRequset();
		req81.setNotNeedReqStrorderId(order_id);
		NotifyOrderInfoNDResponse resp81 = client81.execute(req81, NotifyOrderInfoNDResponse.class);
						
		ZteClient client91 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		NotifyOrderInfoSFRequset req91 = new NotifyOrderInfoSFRequset();
		req91.setOrderid(order_id);
		NotifyOrderInfoSFResponse resp91 = client91.execute(req91, NotifyOrderInfoSFResponse.class);	
		
		ZteClient client92 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		OrderFilterServiceRequset req92 = new OrderFilterServiceRequset();
		req92.setNotNeedReqStrOrderId(order_id);
		OrderFilterServiceResponse resp92 = client92.execute(req92, OrderFilterServiceResponse.class);
		
		 
		ZteClient orderInfExecReqClient = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		OrderInfExecReq orderInfExecReq = new OrderInfExecReq();
		orderInfExecReq.setOp_req("zte.net.ecsord.params.zb.req.NotifyOpenAccountGDRequest");
		orderInfExecReq.setOp_rsp("zte.net.ecsord.params.zb.resp.NotifyOpenAccountGDResponse");
		orderInfExecReq.setParam_desc("{\"AccountInfo\":[{\"AcceptanceForm\":\"{\\\"mainContentOne\\\":\\\"1、套餐名称：A类3G基本套餐46元档。<br/>2、套餐当月生效方式为：套餐减半：您入网当月在所选基本套餐的基础上，对套餐月费和套餐所含内容均减半，减半处理本着“惠及用户”原则进行取整，其中来电显示照常赠送。<br/>3、套餐信息：套餐月费46元，套餐包含国内语音拨打分钟数50分钟，国内流量150MB，国内接听免费（含可视电话）。套餐超出后，国内语音拨打0.25元/分钟，国内流量0.0003元/KB，国内可视电话拨打0.60元/分钟。套餐赠送多媒体内容3个M、文本内容5个T、国内可视电话拨打分钟数5分钟，以及来电显示和手机邮箱，其他执行标准资费。月套餐及叠加包内包含的业务量仅限当月使用，不能延续至下月使用。<br/>4、客户每月使用手机上网流量达到6GB后，达到封顶流量。<br/>\\\",\\\"contactAddr\\\":\\\"广东省佛山市南海区大沥镇黄岐岐西路31号\\\",\\\"mainContentTwo\\\":\\\"基本通信服务及附加业务名称及描述：国内通话。<br/>可选业务包名称及描述：无。<br/>\\\",\\\"agentPaperType\\\":\\\"\\\",\\\"acctAddr\\\":\\\"广东省佛山市南海区大沥镇黄岐岐西路31号\\\",\\\"userType\\\":\\\"3G普通用户\\\",\\\"paperExpr\\\":\\\"20161228\\\",\\\"acctName\\\":\\\"陈炜\\\",\\\"mainContentFive\\\":\\\"\\\",\\\"agentPhone\\\":\\\"\\\",\\\"custType\\\":\\\"个人\\\",\\\"payMethod\\\":\\\"现金\\\",\\\"paperAddr\\\":\\\"广州市白云区石井石沙路1989号412栋101房\\\",\\\"paperType\\\":\\\"18位身份证\\\",\\\"staffInfo\\\":\\\"曾月华TJFSJ041\\\",\\\"contactPhone\\\":\\\"陈先生18923179773\\\",\\\"bankAcctName\\\":\\\"\\\",\\\"bankName\\\":\\\"\\\",\\\"bankAcct\\\":\\\"\\\",\\\"paperNo\\\":\\\"440111198902170336\\\",\\\"mainContentThree\\\":\\\"\\\",\\\"bankCode\\\":\\\"\\\",\\\"agentPaperNo\\\":\\\"\\\",\\\"mainContentFour\\\":\\\"1、优惠活动名称：存费送费一年期。<br/>2、优惠活动协议期：客户承诺自2014年12月至2015年10月，在网12个月。<br/>3、优惠活动信息：预存话费送话费：一次性缴纳合约计划标准预存款为84.00元，其中首月返还金额0.00元，次月起连续11个月每月返还金额7.00元，最后一个月返还剩余部分金额；次月起连续12个月每月赠送话费14.00元。<br/>\\\",\\\"agentName\\\":\\\"\\\",\\\"userNo\\\":\\\"18675727307\\\",\\\"custName\\\":\\\"陈炜\\\"}\",\"AcceptanceMode\":\"0\",\"AcceptanceTp\":\"postsub_t\",\"DevelopCode\":\"5103424290\",\"DevelopName\":\"DGTSCD04\",\"OpenStaff\":\"GD002439\",\"OpenStaffName\":\"曾日华\",\"OpenTime\":\"2014-11-1015:09:02\",\"SerialNumber\":\"18675727307\",\"SimCardNo\":\"\",\"TaxNo\":\"\",\"TradeId\":\"5114111054829130\"}],\"ActiveNo\":\"P51351954461168427\",\"OrderId\":\"WCS1411181465586406017\"}");
		OrderInfExecResp orderInfExecResp = orderInfExecReqClient.execute(orderInfExecReq, OrderInfExecResp.class); 
		 
		
*/
	}
	/**
	 * 根据订单id、货品类型，获取货品信息
	 * @param order_id
	 * @param prod_spec_type_code
	 * @return
	 */
	public OrderItemProdBusiRequest getOrderItemProdBusi(String order_id, String prod_spec_type_code){
		OrderItemProdBusiRequest result = null;
		List<OrderItemProdBusiRequest> orderItemProdList = CommonDataFactory.getInstance().getOrderTree(order_id)
		.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests();
		if(null != orderItemProdList && !orderItemProdList.isEmpty()){
			for(int i = 0; i < orderItemProdList.size(); i++){
				OrderItemProdBusiRequest prod = orderItemProdList.get(i);
				if(prod_spec_type_code.equals(prod.getProd_spec_type_code())){
					result = prod;
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * 根据方案执行规则
	 * @param req
	 * @return
	 */
	public PlanRuleTreeExeResp exePlanRuleTree(PlanRuleTreeExeReq req){
		this.init();
		return ruleService.exePlanRuleTree(req);
	}
	
	/**
	 * 根据目录id执行方案（所有方案互斥）
	 * @param req
	 * @return
	 */
	public CataloguePlanExeResp exeCataloguePlan(CataloguePlanExeReq req){
		this.init();
		return ruleService.exeCataloguePlan(req);
	}
	

	
	/**
	 * 直接调用业务组件
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	public BusiCompResponse execBusiComp(BusiCompRequest req){
		this.init();
		try {
			return orderServices.execBusiComp(req);
		} catch (Exception e) {
			try {
				CommonTools.addBusiError("-1",e.getMessage());
			} catch (ApiBusiException e1) {
				e1.printStackTrace();
			}
		}
		return new BusiCompResponse();
	}
	
	/**
	 * 根据规则id执行
	 * @param req
	 * @return
	 */
	public RuleTreeExeResp exeRuleTree(RuleTreeExeReq req){
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		RuleTreeExeResp rsp = client.execute(req, RuleTreeExeResp.class);
		return rsp;
	}
	
	public BusiCompResponse getRuleTreeresult(RuleTreeExeResp presp){
		Map<String, ZteResponse> resp = presp.getFact().getResponses();
		Iterator it = resp.keySet().iterator();
		Map filterMap = new HashMap();
		BusiCompResponse retresp = new BusiCompResponse();
		retresp.setError_code(ConstsCore.ERROR_FAIL);
		//规则稽核
		String errorMsg = "";
		while (it.hasNext()) {
			String key = (String)it.next();
			ZteResponse zteResp = (ZteResponse) resp.get(key);
			if(zteResp.getError_code().equals(ConstsCore.ERROR_FAIL)) //失败
				errorMsg +=zteResp.getError_msg()+",";
		}
		//没有异常信息，则代表成功
		if(StringUtil.isEmpty(errorMsg)){
			retresp.setError_code(ConstsCore.ERROR_SUCC);
		}else{
			retresp.setError_msg(errorMsg);
		}
		return retresp;
	}
	
	public BusiCompResponse getRuleTreeresult(PlanRuleTreeExeResp presp){
		Map<String, ZteResponse> resp = presp.getFact().getResponses();
		Iterator it = resp.keySet().iterator();
		Map filterMap = new HashMap();
		BusiCompResponse retresp = new BusiCompResponse();
		retresp.setError_code(ConstsCore.ERROR_FAIL);
		//规则稽核
		String errorMsg = "";
		while (it.hasNext()) {
			String key = (String)it.next();
			ZteResponse zteResp = (ZteResponse) resp.get(key);
			if(zteResp.getError_code().equals(ConstsCore.ERROR_FAIL)) //失败
				errorMsg +=zteResp.getError_msg()+",";
		}
		//没有异常信息，则代表成功
		if(StringUtil.isEmpty(errorMsg)){
			retresp.setError_code(ConstsCore.ERROR_SUCC);
		}else{
			retresp.setError_msg(errorMsg);
		}
		return retresp;
	}
	
	public BusiCompResponse getRuleTreeresult(BusiCompResponse presp){
		Map<String, ZteResponse> resp = presp.getFact().getResponses();
		Iterator it = resp.keySet().iterator();
		Map filterMap = new HashMap();
		BusiCompResponse retresp = new BusiCompResponse();
		retresp.setError_code(ConstsCore.ERROR_FAIL);
		//规则稽核
		String errorMsg = "";
		while (it.hasNext()) {
			String key = (String)it.next();
			ZteResponse zteResp = (ZteResponse) resp.get(key);
			if(zteResp.getError_code().equals(ConstsCore.ERROR_FAIL)) //失败
				errorMsg +=zteResp.getError_msg()+",";
		}
		//没有异常信息，则代表成功
		if(StringUtil.isEmpty(errorMsg)){
			retresp.setError_code(ConstsCore.ERROR_SUCC);
		}else{
			retresp.setError_msg(errorMsg);
		}
		return retresp;
	}
	
	/**
	 * 更新属性值
	 * @param order_id 订单ID
	 * @param field_name 属性字段名
	 * @return
	 */
	public boolean updateAttrFieldValue(String order_id,String[] field_name, String[] field_value){
		
		AttrUtils.getInstance().updateAttrFieldValue(order_id, field_name, field_value);
		return true;
	}
	
	/**
	 * 开户结果
	 * @param order_id
	 * @return
	 */
	public boolean getOrdOpenActStatus(String order_id){
		
		return true;
	}
	
	/**
	 * 获取商品类型
	 * @param order_id
	 * @return
	 */
	public String getGoodsType(String order_id){
		String goods_type = CommonDataFactory.getInstance().getZbValue(order_id, AttrConsts.GOODS_TYPE);
		if(EcsOrderConsts.ZB_GOODS_TYPE_ZHKL.equals(goods_type)){
			String hasHy = CommonDataFactory.getInstance()
					.hasTypeOfProduct(order_id, EcsOrderConsts.PRODUCT_TYPE_CONTRACT);
			if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(hasHy)){
				goods_type = EcsOrderConsts.ZB_GOODS_TYPE_ZHYL;
			}
		}
		return goods_type;
	}
	
	/**
	 * 根据表名和订单编码获取对象集合
	 * @param order_id
	 * @param table_name
	 * @return
	 */
	public Object getObjectListByOrderId(String order_id,String table_name) {
		return null;
	}
	/**
	 * 根据下标获取对象集合中的对象，默认为第0个
	 * @param order_id
	 * @param index
	 * @param table_name
	 * @return
	 */
	public Object getObjectByOrderId(String order_id,int index,String table_name) {
		return null;
	}
	/**
	 * 根据辅助字段获取对象集合中的对象。
	 * @param order_id
	 * @param fieldName
	 * @param fieldValue
	 * @param table_name
	 * @return
	 */
	public Object  getObjectByOtherCode(String order_id,String fieldName,String fieldValue,String table_name){
		return null;
	}
	/**
	 * 默认获取第0个对象中的keyName字段的值
	 * @param order_id
	 * @param keyName
	 * @param table_name
	 * @return
	 */
	public Object  getValueByKeyName(String order_id,String keyName,String table_name){
		return null;
	}
	/**
	 * 获取第index个对象中的keyName字段的值
	 * @param order_id
	 * @param index
	 * @param keyName
	 * @param table_name
	 * @return
	 */
	public Object  getValueByKeyName(String order_id,int index,String keyName,String table_name){
		return null;
	}
	/**
	 * 获取第index个对象中的keyName字段的值
	 * @param order_id
	 * @param fieldName
	 * @param fieldValue
	 * @param keyName
	 * @param table_name
	 * @return
	 */
	public Object getValueByKeyName(String order_id,String fieldName,String fieldValue,String keyName,String table_name) {
		return null;
	}

	/**
	 * 取操作人编码
	 * @param order_id
	 * @return
	 */
	public String getOperatorCode(String order_id){
		return AttrUtils.getInstance().getOperatorCode(order_id);
	}
	
	/**
	 * 获取操作人信息：名称等
	 * @param userid
	 * @return
	 */
	public AdminUser getOperatorInfo(String userid){
		return AttrUtils.getInstance().getOperatorInfo(userid);
	}

	/**
	 * 根据type_id获取实物类型
	 * @param type_id
	 * @return
	 */
	public GoodsType getGoodsTypeName(String type_id) {
		GoodsTypeGetReq req = new GoodsTypeGetReq();
		req.setType_id(type_id);
		GoodsTypeGetResp resp = orderServices.getGoodsTypeName(req);
		if (resp != null)
			return resp.getGoodsType();
		return null;
	}
	
	/**
	 * 判断订单是否跟总部交互
	 * @param card_time 卡时长
	 * @param pay_type 付费类型
	 * @param goods_type 商品类型
	 * @param sim_type 卡种类（白卡，成卡）
	 * @param society_flag 是否社会机
	 * @param is_old 新老用户
	 * @param hy_cat_id 合约计划分类ID
	 * @param net_type 网别
	 * @param configs 总部下发规则
	 * @return
	 */
	public boolean matchSendZbOption(String card_time,String pay_type,String goods_type,String sim_type,
			String society_flag,String is_old,String hy_cat_id,String net_type,List<Map> configs){
		return SpecUtils.matchSendZbOption(card_time, pay_type, goods_type, sim_type, society_flag, is_old, hy_cat_id, net_type, configs);
	}
	
	/**
	 * 
	 * @param order_id;error_msg
	 * @return
	 */
	public ZteResponse notifyBusiException(HashMap para){
		try{
			String order_id = (String)para.get("order_id");
			OrderExtBusiRequest orderExtBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			String trace_code = orderExtBusiRequest.getFlow_trace_id();
			if(StringUtil.isEmpty(trace_code)){
				trace_code = EcsOrderConsts.DIC_ORDER_NODE_O;
			}
			String trace_name = (String)EcsOrderConsts.DIC_ORDER_NODE_DESC_MAP.get(trace_code);
			String deal_status = "0";
			String deal_desc = (String)para.get("error_msg");;
			String connects = "";
			StatuSynchReq statuSyn = new StatuSynchReq(order_id, trace_code, trace_name, deal_status, deal_desc, connects);
			return notifyNewShop(statuSyn);
		}catch(Exception e){
			
		}
		return null;
	}
	
	/**
	 * 环节状态通知新商城
	 * @param order_id
	 * @param trace_code  环节
	 * @param trace_name  环节名称
	 * @param deal_status 处理状态
	 * @param deal_desc   处理描述
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ZteResponse notifyNewShop(final StatuSynchReq statuSyn){
		String order_id = statuSyn.getORDER_ID();
//		RuleTreeExeReq ruleTreeExeReq = new RuleTreeExeReq();
//		ruleTreeExeReq.setCheckCurrRelyOnRule(true);
//		ruleTreeExeReq.setCheckAllRelyOnRule(true);
//		ruleTreeExeReq.setReCurrRule(true);
//		ruleTreeExeReq.setPlan_id(EcsOrderConsts.WYG_NOTIFY_PLANID);
//		ruleTreeExeReq.setRule_id(EcsOrderConsts.WYG_NOTIFY_RULEID);
		TacheFact fact = new TacheFact();
		fact.setOrder_id(order_id);
		fact.setRequest(statuSyn);
//		ruleTreeExeReq.setFact(fact);
		
//		RuleTreeExeResp presp = CommonDataFactory.getInstance().exeRuleTree(ruleTreeExeReq);
		
		
		
		//当前订单为写卡环节,发起重新写卡操作流程
		PlanRuleTreeExeReq planTreeExeReq = new PlanRuleTreeExeReq();
		planTreeExeReq.setPlan_id(EcsOrderConsts.WYG_NOTIFY_PLANID);
		planTreeExeReq.setDeleteLogs(true);
		fact.setOrder_id(order_id);
		planTreeExeReq.setFact(fact);
		PlanRuleTreeExeResp resp = CommonDataFactory.getInstance().exePlanRuleTree(planTreeExeReq);
		
//		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
//		RuleTreeExeResp rsp = client.execute(planTreeExeReq, RuleTreeExeResp.class);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(resp);
		
		OrderTreeBusiRequest orderTree = new OrderTreeBusiRequest();
		orderTree.setOrder_id(order_id);
		orderTree.setCol3(EcsOrderConsts.TRACE_TRIGGER_INF);
		orderTree.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderTree.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderTree.store();
		
		return busiResp;
	}
	
	@SuppressWarnings("unchecked")
	public void markException(String order_id, Map params){
		try{
			BusiCompRequest bcr = new BusiCompRequest();
			bcr.setEnginePath("zteCommonTraceRule.markedException");
			bcr.setOrder_id(order_id);
			bcr.setQueryParams(params);
			CommonDataFactory.getInstance().execBusiComp(bcr);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public void insertOrderHandlerLogs(InsertOrderHandLogReq req){
		AttrUtils.getInstance().insertOrderHandlerLogs(req);
	}

	public void chargebackStackChg(ChargebackStatuChgReq req){
		orderServices.chargebackStackChg(req);
	}
	
	public IRuleService getRuleService() {
		return ruleService;
	}

	public void setRuleService(IRuleService ruleService) {
		this.ruleService = ruleService;
	}

	public ILogsServices getLogsServices() {
		return logsServices;
	}

	public void setLogsServices(ILogsServices logsServices) {
		this.logsServices = logsServices;
	}

	public IOrderServices getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(IOrderServices orderServices) {
		this.orderServices = orderServices;
	}
	
	/**
	 * 获取实体数据
	 * @param req
	 * @return
	 */
	public  <T> T getZtePofBusiRequest(ZtePofBusiRequest<?> req,String orderId){
		return (T) null;
	}
}
