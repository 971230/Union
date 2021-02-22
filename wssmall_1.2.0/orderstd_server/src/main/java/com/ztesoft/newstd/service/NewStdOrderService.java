package com.ztesoft.newstd.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import params.ZteError;
import params.paycfg.req.PaymentCfgReq;
import services.PaymentCfgInf;
import services.ProductInf;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.busi.req.MemberBusiRequest;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtvlBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPayBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.order.req.UpdateOrderTreeReq;
import zte.net.ecsord.params.order.req.WorkflowMatchReq;
import zte.net.ecsord.params.order.resp.UpdateOrderTreeRsp;
import zte.net.ecsord.params.order.resp.WorkflowMatchRsp;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_CFG;
import zte.net.ecsord.rule.mode.PreRuleFact;
import zte.net.iservice.IGoodsService;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.RuleTreeExeResp;
import zte.params.goods.req.ProductsListReq;
import zte.params.goods.resp.ProductsListResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.util.ReflectionUtil;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.BusinessCatId;
import com.ztesoft.net.mall.core.model.BusinessType;
import com.ztesoft.net.mall.core.model.DlyType;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.PayCfg;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.service.IBusinessCatManager;
import com.ztesoft.net.mall.core.service.IBusinessTypeManager;
import com.ztesoft.net.mall.core.service.IRuleTypeRelaManager;
import com.ztesoft.net.mall.core.service.impl.RequestStoreManager;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.model.OuterItem;
import com.ztesoft.net.outter.inf.iservice.IOutterTmplManager;
import com.ztesoft.net.outter.inf.model.PublicConst;
import com.ztesoft.net.outter.inf.util.OuterSysConst;
import com.ztesoft.newstd.common.CommonData;
import com.ztesoft.newstd.dao.ICommonDataDao;
import com.ztesoft.newstd.util.BusinessHandlerUtil;
import com.ztesoft.newstd.util.JsonUtil;
import com.ztesoft.orderstd.service.IDlyTypeManager;
import com.ztesoft.util.OrderThreadLocalHolder;

import commons.CommonTools;
import consts.ConstsCore;

public class NewStdOrderService {
	private static Logger logger = Logger.getLogger(NewStdOrderService.class);
	@Resource
	private IDaoSupport baseDaoSupport;
	@Resource
	private IOutterTmplManager outterSTmplManager;
	@Resource
	private IGoodsService goodServices;
	@Resource
	private IDlyTypeManager dlyTypeSManager;
	@Resource
	private ProductInf proudctServ;
	@Resource
	private PaymentCfgInf paymentCfgServ;
	@Autowired
	private IRuleTypeRelaManager ruleTypeRelaManager;
	@Autowired
	private IBusinessTypeManager businessTypeManager;
	@Autowired
    private IBusinessCatManager businessCatManager;
	@Resource
	private ICommonDataDao commonDataDao;

	private INetCache cache = CacheFactory.getCacheByType(""); // add by wui订单调用单独的订单缓存机器，通过业务分开，和业务静态数据分开
	private DecimalFormat df=new DecimalFormat("00000000");
	/**
	 * xiaoruidan 订单标准化入库 20180520
	 */
	public List saveOrder(List<Outer> outerList) throws Exception {
		Long time1 = System.currentTimeMillis();
		String batch_id = this.baseDaoSupport.getSequences("s_es_order");
		List list = new ArrayList();
		
		try{
			for (Outer outer : outerList) {
				// List<OuterItem> itemList = outer.getItemList();
				// List<OrderOuter> orderOuterList = new ArrayList<OrderOuter>();
				// if(itemList!=null && itemList.size()>0){
				// for(OuterItem item:itemList){
				// OrderOuter orderOuter = packageOrderOuter( outer, batch_id);
				// orderOuter.setProduct_id(item.getProduct_id());
				// orderOuter.setService_code(Consts.SERVICE_CODE_CO_GUIJI_NEW); //设置服务编码
				// orderOuter.setUserSessionId(orderOuter.getUserSessionId());
				// orderOuter.setCreate_type("1");
				// orderOuter.setOrder_type("1");
				// orderOuter.setService_code(com.ztesoft.ibss.common.util.Const.ORDER_STAND_AUTO_SERVICE_CODE);
				// orderOuter.setP_order_amount(orderOuter.getOrder_amount());
				// orderOuter.setApp_key(orderOuter.getOrder_from());
				// orderOuter.setAppKey(orderOuter.getOrder_from());
				// orderOuter.setStatus("2");//不以淘宝的状态为准
				// orderOuterList.add(orderOuter);
				// }
				List<Map> paramsl = createParamsl(outer, batch_id);
				// stdOrderServ.initParam(req);
				initParam(paramsl);
				Long time2 = System.currentTimeMillis();
				logger.info(Thread.currentThread().getId() + "===================orderOuter封装耗时：" + (time2 - time1)
						+ "====开始时间" + time1 + "结束时间" + time2);
					String order_id = setOrderTreeInfo(outer, paramsl, batch_id);
					list.add(order_id);
				// }
			}
		}catch(Exception e){
			logger.error("NewStdOrderService saveOrder error:"+e.getMessage(), e);
			throw e;
		}finally{
			CommonData.getData().removeData();
		}
		
		return list;
	}

	/**
	 *
	 * @param outer
	 * @param orderOuterList
	 * @param paramsl
	 */
	public String setOrderTreeInfo(Outer outer, List<Map> paramsl, String batch_id) throws Exception {
		if (null == paramsl || paramsl.size() == 0) {
			return null;
		}
		
		Long time1 = System.currentTimeMillis();
		CommonData commonData = CommonData.getData();
		// 设置订单信息
		// List<String> orderIdlist = new ArrayList<String>();
		OrderOuter orderOuter = packageOrderOuter(outer, batch_id);
		if (outer.getItemList() != null && outer.getItemList().size() > 0) {
			OuterItem oi = null;
			if ("HB".equals(ManagerUtils.getSourceFrom())) {
				int i = Integer.parseInt(outer.getReserve9());
				oi = outer.getItemList().get(i);
			} else {
				oi = outer.getItemList().get(0);
			}
			orderOuter.setHouse_id(oi.getHouse_id());
			orderOuter.setOrg_id(oi.getOrg_id());
			orderOuter.setProduct_id(oi.getProduct_id());
			// orderOuter.setPayment_id("ZXZF");
			orderOuter.setShipping_id("PY");
			orderOuter.setP_order_amount(orderOuter.getOrder_amount());
		}

		HashMap map = (HashMap) paramsl.get(0);
		// 设置会员信息
		Long time11 = System.currentTimeMillis();
		MemberBusiRequest member = setMember(orderOuter, map);
		Long time12 = System.currentTimeMillis();
		logger.info(Thread.currentThread().getId() + "==============member对象封装耗时:" + (time12 - time11));
		// 设置订单主表
		orderOuter.setParamsl(paramsl);

		OrderBusiRequest order = setOrder(outer, orderOuter, member);
		Long time13 = System.currentTimeMillis();
		logger.info(Thread.currentThread().getId() + "==============order对象封装耗时:" + (time13 - time12));

		// 订单树
		setOrderTree(order);
		Long time14 = System.currentTimeMillis();
		logger.info(Thread.currentThread().getId() + "=============orderTree对象封装耗时:" + (time14 - time13));

		// 订单扩展表
		setOrderExt(order, outer);
		Long time15 = System.currentTimeMillis();
		logger.info(Thread.currentThread().getId() + "==============orderExt对象封装耗时:" + (time15 - time14));

		// 纵转横表信息
		setOrderExtVl(order, outer);
		Long time16 = System.currentTimeMillis();
		logger.info(Thread.currentThread().getId() + "==============orderExtvl对象封装耗时:" + (time16 - time15));

		// 订单支付信息
		setPaymentInfo(order, orderOuter, outer);
		Long time17 = System.currentTimeMillis();
		logger.info(Thread.currentThread().getId() + "=============payment对象封装耗时:" + (time17 - time16));

		// 订单物流信息
		OrderDeliveryBusiRequest orderDeliver = setDeliver(order, outer);
		Long time18 = System.currentTimeMillis();
		logger.info(Thread.currentThread().getId() + "==============deliver对象封装耗时:" + (time18 - time17));

		// 创建订单项 es_order_item ，参见OrderSManager.saveGoodsItem
		// 一个订单有一到多个的商品，一个商品对应一个orderItem，一个商品有多个货品，一个货品对于一个OrderItemProd
		// for (int i = 0; i < orderOuterList.size(); i++) {
		List<OuterItem> items = outer.getItemList();
		for (int i = 0; i < items.size(); i++) {
			// OrderOuter tmpOrderOuter = orderOuterList.get(i);
			// 设置订单项目
			Long time21 = System.currentTimeMillis();
			OrderItemBusiRequest orderItem = setOrderItem(orderOuter, order);
			Long time22 = System.currentTimeMillis();
			commonData.getOrderTreeBusiRequest().getOrderItemBusiRequests().add(orderItem);

			logger.info(Thread.currentThread().getId() + "==============orderItem对象封装耗时:" + (time22 - time21));

			// 设置订单项扩展表
			OrderItemExtBusiRequest orderItemExt = setOrderItemExt(order, orderItem, outer);
			Long time23 = System.currentTimeMillis();
			System.out
					.println(Thread.currentThread().getId() + "==============orderItemExt对象封装耗时:" + (time23 - time22));

			// 设置订单项物流信息
			OrderDeliveryItemBusiRequest orderDeliverItem = setOrderDeliverItem(orderItem, orderDeliver);
			if (orderDeliverItem.getNum() > 0) {// 有没发货商品才加到物流item表
				orderItem.setShip_num(orderDeliverItem.getNum());
				commonData.getOrderDeliveryItemBusiRequests().add(orderDeliverItem);
			}
			Long time24 = System.currentTimeMillis();
			logger.info(
					Thread.currentThread().getId() + "==============orderDeliveryItem对象封装耗时:" + (time24 - time23));

			// 货品信息表
			List<Goods> products = null;
			ProductsListReq plreq = new ProductsListReq();
			plreq.setGoods_id(orderItem.getGoods_id());
			ProductsListResp plresp = goodServices.listProducts(plreq);
			if (plresp != null) {
				products = plresp.getProducts();
			}
			Long time25 = System.currentTimeMillis();
			logger.info(Thread.currentThread().getId() + "==============goodsServices查询耗时:" + (time25 - time24));
			// 插入商品货品表
			if (products != null && products.size() > 0) {
				for (Goods g : products) {
					// 订单项货品信息
					Long time31 = System.currentTimeMillis();
					OrderItemProdBusiRequest orderItemProd = setOrderItemProd(order, orderItem, g);
					orderItem.getOrderItemProdBusiRequests().add(orderItemProd);
					Long time32 = System.currentTimeMillis();
					logger.info(
							Thread.currentThread().getId() + "==============orderItemProd查询耗时:" + (time32 - time31));

					// 订单项货品扩展信息
					OrderItemProdExtBusiRequest orderItemProdExt = setOrderItemProdExt(orderItemProd, outer);
					orderItemProd.setOrderItemProdExtBusiRequest(orderItemProdExt);
					Long time33 = System.currentTimeMillis();
					logger.info(
							Thread.currentThread().getId() + "==============orderItemProdext查询耗时:" + (time33 - time32));
				}
			}
		}
		
		// 执行属性处理树
		Long time40 = System.currentTimeMillis();
		logger.info(Thread.currentThread().getId() + "==============订单树初始化总耗时:" + (time40 - time1) + "====开始时间"
				+ time1 + "结束时间" + time40);
		// 执行属性处理树
		BusinessHandlerUtil businessHandlerUtil = new BusinessHandlerUtil();
		businessHandlerUtil.executeHandler(map);

		Long time41 = System.currentTimeMillis();
		logger.info(Thread.currentThread().getId() + "==============属性处理器总耗时:" + (time41 - time40) + "====开始时间"
				+ time40 + "结束时间" + time41);
	
		Long time2 = System.currentTimeMillis();
		logger.info(Thread.currentThread().getId() + "====================订单树对象封装耗时:" + (time2 - time1)
				+ "====开始时间" + time1 + "结束时间" + time2);
		// 调用前置规则
		readPreRule();

		Long time3 = System.currentTimeMillis();
		logger.info(Thread.currentThread().getId() + "====================前置规则调用耗时:" + (time3 - time2)
				+ "====开始时间" + time2 + "结束时间" + time3);
	
	    // 保存之前先进行自定义流程校验
	    ES_WORK_CUSTOM_CFG flowCfg = this.doWorkflowMatch(outer.getFlow_code(),
	    		CommonData.getData().getOrderTreeBusiRequest(),
	    		CommonData.getData().getOrderExtvlBusiRequest());
	    
	    outer.setFlowCfg(flowCfg);
	    
	    if(flowCfg != null && 
	            org.apache.commons.lang.StringUtils.isNotBlank(flowCfg.getOrder_deal_method())){
	        
	        //用于自定义流程指定了受理方式、但是传入报文中未指定受理方式的情况，沉淀自定义流程配置的受理方式
	        OrderExtBusiRequest extReq = CommonData.getData().getOrderTreeBusiRequest().getOrderExtBusiRequest();
	        extReq.setOrder_deal_method(flowCfg.getOrder_deal_method());
	    }

//	    BSS预CBSS传参校验开关
	    ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String check_switch = cacheUtil.getConfigInfo("ORDER_BCB_CHECK_SWITCH");
		if("1".equals(check_switch)) {
			if( "1".equals(CommonData.getData().getOrderTreeBusiRequest().getOrderExtBusiRequest().getIs_aop())) {
		    	if (null != outer.getNice_info() && null !=outer.getNice_info().get("lhscheme_id") 
		    			&& "".equals(outer.getNice_info().get("lhscheme_id"))) {
					throw new Exception("CBSS靓号传参错误");
				}
		    }else {
				if (null != outer.getNice_info() && null !=outer.getNice_info().get("classId")
						&& "".equals(outer.getNice_info().get("lhscheme_id"))) {
					throw new Exception("BSS靓号传参错误");
				}
			}
		}
		// 保存订单树
		saveOrderTree();
		
		Long time4 = System.currentTimeMillis();
		logger.info(Thread.currentThread().getId() + "====================saveOrderTree订单树对象封装耗时:"
				+ (time4 - time3) + "====开始时间" + time3 + "结束时间" + time4);
		
		return order.getOrder_id();
	}
	
	/**
	 * 自定义流程校验
	 * @param flow_code 流程编码
	 * @param orderTree 订单树对象
	 * @param orderExtval 订单扩展对象
	 * @return
	 * @throws Exception
	 */
	private ES_WORK_CUSTOM_CFG doWorkflowMatch(String flow_code,OrderTreeBusiRequest orderTree,
			OrderExtvlBusiRequest orderExtval) throws Exception{
		WorkflowMatchReq matchReq = new WorkflowMatchReq();
		matchReq.setFlow_code(flow_code);
		matchReq.setOrderTree(orderTree);
		
		// 根据传入的操作方式匹配流程类型
		if ("01".equals(orderExtval.getOpttype())){
			// 退订
			matchReq.setCfg_type("prod_cancel");
		}else if ("02".equals(orderExtval.getOpttype())) {
			// 变更
			matchReq.setCfg_type("prod_change");
		}else{
			// 订购
			matchReq.setCfg_type("order");
		}
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		WorkflowMatchRsp matchRsp = client.execute(matchReq, WorkflowMatchRsp.class);
		
		String error_code = matchRsp.getError_code();
		if("-1".equals(error_code)){
			//调用DUBBO异常
			throw new Exception(matchRsp.getError_msg());
		}else if("0".equals(error_code)){
		 	return null;
		}else{
			return matchRsp.getCfg();
		}
	}

	private void setOrderTreeToCache() {
		cache.set(RequestStoreManager.NAMESPACE,
				RequestStoreManager.DC_TREE_CACHE_NAME_KEY + "order_id"
						+ CommonData.getData().getOrderTreeBusiRequest().getOrder_id(),
				CommonData.getData().getOrderTreeBusiRequest(), RequestStoreManager.time);
	}

	/***
	 * 调用规则暂时没有其他逻辑，只是设置几个值 暂时先将规则的几个值设置到业务类型表中
	 */
	private void readPreRule() {
		// 流程匹配
		OrderBusiRequest order = CommonData.getData().getOrderTreeBusiRequest().getOrderBusiRequest();
		OrderExtBusiRequest ordExt = CommonData.getData().getOrderTreeBusiRequest().getOrderExtBusiRequest();
	    OrderItemExtBusiRequest	ordItemExt = CommonData.getData().getOrderTreeBusiRequest().getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest();
        OrderExtvlBusiRequest extvtl = CommonData.getData().getOrderExtvlBusiRequest();
        // 若本次不上线  代码进行回退
        String optType="";
        if(extvtl != null){
            optType = extvtl.getOpttype();
        }
        if(StringUtil.isEmpty(optType) && "10093".equals(ordExt.getOrder_from()) && "170801434582003010".equals(order.getGoods_type())){
            optType="00";
        }
		String order_id = order.getOrder_id();
		try {

			if (null == order_id || "".equals(order_id)) {
				CommonTools.addBusiError("-1", "获取order_id失败");
			}

			// 根据商品类型，订单来源查询业务类型id
			// Map<String,String> typeMap = new HashMap<String,String>();
			// typeMap.put("GOODS_TYPE_ID", order.getGoods_type());
			// typeMap.put("ORDERFROM", ordExt.getOrder_from());
			BusinessType type = businessTypeManager.getBusiType(order.getGoods_type(), ordExt.getOrder_from());
			//add by sgf
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String goods_type = cacheUtil.getConfigInfo("goods_type_check");
			BusinessCatId businessCatId = null;
			if(goods_type.contains(order.getGoods_type()+",")){//设置是否查商品小类 根据设置条件判断该大类下不同的小类中不同的流程形式 为无线宽带融合特增加小类
			    businessCatId = businessCatManager.getBusiCat(ordItemExt.getGoods_cat_id(),ordExt.getOrder_from(),optType);
			}
			if(businessCatId != null){
			    type.setFlow_id(businessCatId.getFlow_id());
			    type.setOper_mode(businessCatId.getOper_mode());
			    type.setBusi_type_id(businessCatId.getBusi_cat_id());//大类表中和小类表中对应的busi_type_id 和busi_cat_id保持一致，仅为了替换模式和流程
			    type.setIs_aop(businessCatId.getIs_aop());
			    type.setIs_send_wms(businessCatId.getIs_send_wms());
			}
			if (null != type) {
				Long time1 = System.currentTimeMillis();

				String busiType = type.getBusi_type_id();
				String oper_mode = type.getOper_mode();
				String is_send_wms = type.getIs_send_wms();
				if (!StringUtils.isEmpty(oper_mode)) {
					CommonData.getData().getOrderTreeBusiRequest().getOrderExtBusiRequest()
									.setOrder_model(oper_mode);
				}
				if (!StringUtils.isEmpty(is_send_wms)) {
					CommonData.getData().getOrderTreeBusiRequest().getOrderExtBusiRequest()
									.setIs_send_wms(is_send_wms);
				}
					
				String is_aop = type.getIs_aop();
				if (!StringUtils.isEmpty(is_aop)) {
					CommonData.getData().getOrderTreeBusiRequest().getOrderExtBusiRequest().setIs_aop(is_aop);
				}
				if (!StringUtils.equals(EcsOrderConsts.NO_DEFAULT_VALUE, is_aop)) {
					CommonData.getData().getOrderTreeBusiRequest().getOrderExtBusiRequest()
									.setSend_zb(EcsOrderConsts.NO_DEFAULT_VALUE);
				}

				// 工作流匹配规则ID
				String flow_id = type.getFlow_id();
				if (!StringUtils.isEmpty(flow_id)) {
							CommonData.getData().getOrderTreeBusiRequest().getOrderExtBusiRequest().setFlow_id(flow_id);
				}

			} else {
					throw new Exception("未配置业务类型:" + order.getGoods_type() + ",订单来源:" + ordExt.getOrder_from() + "数据");
			}

		} catch (Exception e) {
			// 异常处理
			e.printStackTrace();
		}
	}

	/**
	 * 调用前置规则
	 * 
	 * @param req
	 * @param queryParams
	 * @param order_id
	 * @return
	 */
	private RuleTreeExeResp exePreRule(RuleTreeExeReq req, Map queryParams, String order_id) {
		BusiCompRequest request = new BusiCompRequest();
		request.setQueryParams(queryParams);
		PreRuleFact fact = new PreRuleFact();
		OrderBusiRequest order = CommonData.getData().getOrderTreeBusiRequest().getOrderBusiRequest();
		fact.setRegion(order.getRegionid() + "");
		fact.setGoods_type(order.getGoods_type());

		fact.setShop_code(CommonData.getData().getOrderTreeBusiRequest().getOrderExtBusiRequest().getOrder_from());

		OrderItemExtBusiRequest item = CommonData.getData().getOrderTreeBusiRequest().getOrderItemBusiRequests().get(0)
				.getOrderItemExtBusiRequest();
		fact.setGoods_cat_id(item.getGoods_cat_id());

		fact.setParamMap(queryParams);
		fact.setGoods_id(order.getGoods_id());
		fact.setOrder_id(order_id);
		queryParams.put("fact", fact);
		fact.setRequest(request);
		req.setFact(fact);
		return CommonDataFactory.getInstance().exeRuleTree(req);
	}

	public void saveOrderTree() throws Exception {
		commonDataDao.insertNoCache();
		setOrderTreeToCache();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map> createParamsl(Outer ot, String batch_id) throws ApiBusiException {
		List<Map> mapList = new ArrayList<Map>();
		List<OuterItem> itemList = ot.getItemList();
		// OrderOuter outer = orderOuterList.get(0);
		if (itemList != null && itemList.size() > 0) {
			OrderOuter outer = packageOrderOuter(ot, batch_id);

			Map map_all = new HashMap();
			Map otMap = ReflectionUtil.po2Map(ot);
			if (ot.getExtMap() != null && ot.getExtMap().size() > 0) {
				map_all.putAll(ot.getExtMap());
			}
			if (ot.getLocalObject() != null) {
				map_all.putAll(ReflectionUtil.po2Map(ot.getLocalObject()));
			}
			map_all.putAll(otMap);
			for (OuterItem ci : itemList) {
				Map map = new HashMap();
				map.putAll(ReflectionUtil.po2Map(outer));
				map.putAll(otMap);
				map.putAll(ReflectionUtil.po2Map(ci));
				map.putAll(map_all);
				map.put("shipping_id", outer.getShipping_id());
				map.put("ship_mobile", outer.getShip_mobile());
				map.put("ship_addr", outer.getShip_addr());
				map.put("ship_tel", outer.getShip_tel());
				map.put("ship_zip", outer.getShip_zip());
				map.put("province_id", outer.getProvince_id());
				map.put("city_id", outer.getCity_id());
				map.put("region_id", outer.getRegion_id());
				map.put("province", outer.getProvince());
				map.put("city", outer.getCity());
				map.put("region", outer.getRegion());
				map.put("ship_name", outer.getShip_name());
				map.put("remark", outer.getRemark());
				// 发票信息
				map.put("invoice_title", outer.getInvoice_title());
				map.put("invoice_title_desc", outer.getInvoice_title());
				map.put("invoice_type", outer.getInvoice_type());
				map.put("house_id", ci.getHouse_id());
				outer.setHouse_id(ci.getHouse_id());
				outer.setOrg_id(ci.getOrg_id());
				outer.setProduct_id(ci.getProduct_id());
				map.put("item_type", Consts.ITEM_TYPE_0);
				map.put("product_id", ci.getProduct_id());// 150 201310113208001326 //156
				map.put("goods_num", ci.getPro_num());
				// if(StringUtil.isEmpty(outer.getPayment_id()))
				outer.setPayment_id("ZXZF");
				map.put("payment_id", outer.getPayment_id());
				// if(StringUtil.isEmpty(outer.getShipping_id()))
				outer.setShipping_id("PY");
				map.put("source_from", ManagerUtils.getSourceFrom());
				map.put("goods_name", ci.getPro_name());

				mapList.add(map);
			}
		}
		// OrderSyncReq oreq = new OrderSyncReq();
		// oreq.setParamsl(mapList);
		// oreq.setCreate_type("1");
		// oreq.setService_code(Const.ORDER_STAND_AUTO_SERVICE_CODE);

		return mapList;
	}

	private void initParam(List<Map> paramsl) throws ApiBusiException {
		for (Map map : paramsl) {
			if (map != null) {
				String source_from = (String) map.get("source_from");
				if (StringUtil.isEmpty(source_from)) {
					map.put("source_from", CommonTools.getSourceForm());
				}
				if (OrderThreadLocalHolder.getInstance().getOrderCommonData().getAdminUser() != null) {
					String name = (String) map.get("name");
					if (StringUtil.isEmpty(name)) {
						map.put("name",
								OrderThreadLocalHolder.getInstance().getOrderCommonData().getAdminUser().getRealname());
					}

					String uname = (String) map.get("uname");
					if (StringUtil.isEmpty(uname)) {
						map.put("uname",
								OrderThreadLocalHolder.getInstance().getOrderCommonData().getAdminUser().getUsername());
					}
				}
				Product product = proudctServ.get((String) map.get("product_id"));
				// 对象拷贝
				map.put("service_code", Const.ORDER_STAND_AUTO_SERVICE_CODE);
				map.put("create_type", "1");
				// map.put("userSessionId", req.getUserSessionId());
				map.put("goods_id", product.getGoods_id());
				// map.put("spread_member_id", req.getSpread_member_id());
				// map.put("service_type", req.getService_type());
				// if(!StringUtil.isEmpty(req.getService_id()))
				// map.put("service_id", req.getService_id());
				// map.put("warehousePurorder", req.getWarehousePurorder());

			}
		}
	}

	private OrderOuter packageOrderOuter(Outer oo, String batch_id) {

		OrderOuter oa = new OrderOuter();
		oa.setAddress_id(oo.getAddress_id());
		oa.setOrder_from(oo.getOrder_from());
		oa.setSource_from(ManagerUtils.getSourceFrom());
		oa.setBatch_id(batch_id);
		// String order_id = this.baseDaoSupport.getSequences("s_es_order");

		// orderOuter.setProduct_id(item.getProduct_id());
		oa.setService_code(Consts.SERVICE_CODE_CO_GUIJI_NEW); // 设置服务编码
		oa.setCreate_type("1");
		oa.setOrder_type("01");
		oa.setService_code(com.ztesoft.ibss.common.util.Const.ORDER_STAND_AUTO_SERVICE_CODE);
		// oa.setP_order_amount(oo.getOrder_amount());
		oa.setApp_key(oa.getOrder_from());
		oa.setAppKey(oa.getOrder_from());
		oa.setStatus("2");// 不以淘宝的状态为准

		// 发票信息
		oa.setInvoice_type(oo.getInvoice_type());
		oa.setInvoice_title_desc(oo.getInvoice_title_desc());
		oa.setInvoice_content(oo.getInvoice_content());

		// oa.setOrder_id(order_id);
		oa.setOld_sec_order_id(oo.getOut_tid());
		oa.setOrder_channel(oo.getOrder_channel());
		// oa.setOrder_type(oo.getType());
		oa.setO_order_status(oo.getStatus());
		oa.setMerge_status(oo.getMerge_status());
		oa.setPlat_type(oo.getPlat_type());
		oa.setPro_totalfee(oo.getPro_totalfee());
		oa.setOrder_amount(oo.getOrder_totalfee());
		oa.setTid_time(oo.getTid_time());
		oa.setCreate_time(oo.getGet_time());
		oa.setRemark(oo.getBuyer_message());
		oa.setIs_adv_sale(oo.getIs_adv_sale());
		oa.setO_pay_status(oo.getPay_status());
		oa.setShip_name(oo.getReceiver_name());
		oa.setShip_mobile(oo.getReceiver_mobile());
		oa.setShip_addr(oo.getAddress());
		oa.setSpread_name(oo.getRecommended_name());
		oa.setSpread_code(oo.getRecommended_code());
		oa.setSpread_phone(oo.getRecommended_phone());
		oa.setDevelopment_code(oo.getDevelopment_code());
		oa.setDevelopment_name(oo.getDevelopment_name());
		oa.setUname(oo.getBuyer_id());
		oa.setName(oo.getBuyer_name());
		oa.setShip_amount(oo.getPost_fee());
		oa.setProvince(oo.getProvince());
		oa.setCity(oo.getCity());
		oa.setRegion(oo.getDistrict());
		oa.setShip_zip(oo.getPost());
		oa.setShip_tel(StringUtil.isEmpty(oo.getPhone()) ? oo.getReceiver_mobile() : oo.getPhone());
		try {
			if (!StringUtil.isEmpty(oo.getProvinc_code()))
				oa.setProvince_id(Long.valueOf(oo.getProvinc_code()));
			if (!StringUtil.isEmpty(oo.getCity_code()))
				oa.setCity_id(Long.valueOf(oo.getCity_code()));
			if (!StringUtil.isEmpty(oo.getArea_code()))
				oa.setRegion_id(Long.valueOf(oo.getArea_code()));

			if (!StringUtil.isEmpty(oo.getInvoice_title())) {
				oa.setInvoice_title(Integer.valueOf((oo.getInvoice_title())));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		oa.setWm_isreservation_from(oo.getWm_isreservation_from());

		if (!StringUtils.isEmpty(oo.getPay_method())) {
			// 支付方式ID
			PublicConst payment = outterSTmplManager.queryPublicConst(oo.getOrder_from(),
					OuterSysConst.CONST_OBJECT_TYPE_PAYMENT, oo.getPay_method());
			if (payment != null) {
				oa.setPayment_id(payment.getZte_code());
			} else {
				oa.setPayment_id(oo.getPay_method());
			}
		}
		if (!StringUtils.isEmpty(oo.getSending_type())) {
			// 配送方式
			PublicConst shipping = outterSTmplManager.queryPublicConst(oo.getOrder_from(),
					OuterSysConst.CONST_OBJECT_TYPE_SHIPPING, oo.getSending_type());
			if (shipping != null) {
				oa.setShipping_id(shipping.getZte_code());
				oo.setSending_type(shipping.getC_name());
			} else {
				oa.setShipping_id(oo.getSending_type());
			}
		}
		if (!StringUtils.isEmpty(oa.getO_order_status())) {
			// 订单状态
			PublicConst pc = outterSTmplManager.queryPublicConst(oo.getOrder_from(),
					OuterSysConst.CONST_OBJECT_TYPE_ORDER_STATUS, oa.getO_order_status());
			if (pc != null) {
				oa.setStatus(pc.getZte_code());
				oa.setO_order_status(pc.getC_name());
			} // else{
				// oa.setStatus(oa.getO_order_status());
				// }
		}
		if (!StringUtils.isEmpty(oa.getO_pay_status())) {
			// 支付状态
			PublicConst pc = outterSTmplManager.queryPublicConst(oo.getOrder_from(),
					OuterSysConst.CONST_OBJECT_TYPE_PAY_STATUS, oa.getO_pay_status());
			if (pc != null) {
				oa.setPay_status(pc.getZte_code());
				oa.setO_pay_status(pc.getC_name());
			} // else{
				// oa.setPay_status(oa.getO_pay_status());
				// }
		}

		if (oo.getItemList() != null && oo.getItemList().size() > 0) {
			OuterItem oi = null;
			if ("HB".equals(ManagerUtils.getSourceFrom())) {
				int i = Integer.parseInt(oo.getReserve9());
				oi = oo.getItemList().get(i);
			} else {
				oi = oo.getItemList().get(0);
			}
			oa.setProduct_id(oi.getProduct_id());
			oa.setCerti_type(oi.getCert_type());
			oa.setGoods_id(oi.getGoods_id());
			// oa.setGoods_name(oi.getPro_name());
			oa.setCerti_number(oi.getCert_card_num());
			oa.setGoods_num(oi.getPro_num() + "");
			oa.setGoods_attrs(JsonUtil.beanToJson(oo.getItemList()));
		}
		oa.setGoods_name(oo.getExtMap().get("GoodsName"));
		oa.setPay_method(oo.getPay_method());
		
		return oa;
	}

	private MemberBusiRequest setMember(OrderOuter orderOuter, HashMap<String, String> map) {
		// 生成会员,一个订单一个会员
		MemberBusiRequest member = CommonData.getData().getMemberBusiRequest();
		AdminUser user = OrderThreadLocalHolder.getInstance().getOrderCommonData().getAdminUser();
		if (orderOuter.getUname() == null && user != null) {
			orderOuter.setUname(user.getUsername());
		}
		if (orderOuter.getName() == null && user != null) {
			orderOuter.setName(user.getUsername());
		}
		String member_name = orderOuter.getUname();
		if (StringUtil.isEmpty(member_name))
			member_name = orderOuter.getName();
		if (map != null && StringUtil.isEmpty(member_name)) {
			member_name = map.get("buyer_id");
		}
		if (StringUtil.isEmpty(member_name))
			member_name = "defmember";
		// 都是新会员
		String member_id = this.baseDaoSupport.getSequences("s_es_member");
		orderOuter.setMember_id(member_id);
		member.setMember_id(member_id);
		if (StringUtil.isEmpty(orderOuter.getName()) && map != null)
			orderOuter.setName(map.get("buyer_name"));
		member.setName(orderOuter.getName());
		member.setUname(member_name);
		member.setPassword(member.getUname());
		if (StringUtil.isEmpty(orderOuter.getShip_tel()) && map != null)
			orderOuter.setShip_tel(map.get("reference_phone"));
		member.setTel(orderOuter.getShip_tel());
		if (StringUtil.isEmpty(orderOuter.getShip_mobile()) && map != null)
			orderOuter.setShip_mobile(map.get("phone_num"));
		member.setMobile(orderOuter.getShip_mobile());
		member.setZip(orderOuter.getShip_zip());
		if (StringUtil.isEmpty(orderOuter.getRegion()) && map != null)
			orderOuter.setRegion(map.get("district"));
		member.setRegion(orderOuter.getRegion());
		if (StringUtil.isEmpty(orderOuter.getCity()) && map != null)
			orderOuter.setCity(map.get("city"));
		member.setCity(orderOuter.getCity());
		if (StringUtil.isEmpty(orderOuter.getProvince()) && map != null)
			orderOuter.setProvince(map.get("province"));
		member.setProvince(orderOuter.getProvince());
		member.setSex(orderOuter.getSex());
		member.setAddress(orderOuter.getShip_addr());
		member.setSource_from(orderOuter.getSource_from());

		if (map != null) {
			String rid = map.get("area_code");
			if (!StringUtil.isEmpty(rid))
				member.setRegion_id(Integer.valueOf(rid));
			String cid = map.get("city_code");
			if (!StringUtil.isEmpty(cid))
				member.setCity_id(Integer.valueOf(cid));
			String pid = map.get("provinc_code");
			if (!StringUtil.isEmpty(pid))
				member.setProvince_id(Integer.valueOf(pid));

			member.setBuyer_uid(map.get("uid"));
			member.setShip_area(map.get("ship_area"));
			member.setCert_card_num(map.get("cert_card_num"));
			member.setCert_address(map.get("cert_address"));
			member.setCert_failure_time(map.get("cert_failure_time"));
			member.setCert_type(map.get("certi_type"));
			member.setCustomertype(map.get("CustomerType"));
			member.setPlat_type(map.get("plat_type"));
		}
		// member.setRegtime(DBTUtil.current());
		if (StringUtil.isEmpty(member.getParentid()))
			member.setParentid("0");
		member.setLv_id(Const.MEMBER_LV_COMMON);
		member.setLv_name("普通会员");

		member.setPoint(0);
		member.setAdvance(0D);
		// member.setRegtime(DBTUtil.current());//lzf add
		// member.setLastlogin(DBTUtil.current());
		member.setLogincount(1);
		member.setPassword(StringUtil.md5(member.getPassword()));

		// //判断会员是否存在
		// Member m=null;
		// if (!StringUtil.isEmpty(member.getMember_id()))
		// m = memberManager.get(member.getMember_id());
		// if (m == null) {
		// int result = memberManager.add(member);
		// } else {
		// member = m;
		// }
		// this.baseDaoSupport.insert("member", member);
		return member;
	}

	private OrderBusiRequest setOrder(Outer outer, OrderOuter orderOuter, MemberBusiRequest member) {
		// 参考StdOrderServ.createOrder
		OrderBusiRequest order = CommonData.getData().getOrderTreeBusiRequest().getOrderBusiRequest();
		String batchID = UUID.randomUUID().toString().replace("-", "");

		if (!StringUtil.isEmpty(orderOuter.getUserid())) {
			order.setUserid(orderOuter.getUserid());
			// 设置订单归属对象
			AdminUser adminUser = OrderThreadLocalHolder.getInstance().getOrderCommonData().getAdminUser();
			if (adminUser != null)
				order.setOrder_adscription_id(adminUser.getFounder() + "");
		} else {
			order.setUserid("-1");
		}
		order.setGoods_id(orderOuter.getGoods_id());
		order.setGoods_type(
				CommonDataFactory.getInstance().getGoodSpec(null, orderOuter.getGoods_id(), SpecConsts.TYPE_ID));
		order.setCreate_type(Integer.valueOf(orderOuter.getCreate_type()));
		order.setBatch_id(batchID);
		order.setSource_from(orderOuter.getSource_from());
		order.setShipping_id(orderOuter.getShipping_id()); // 配送方式
		order.setPayment_id(orderOuter.getPayment_id());// 支付方式
		order.setShip_addr(orderOuter.getShip_addr());
		order.setShip_mobile(orderOuter.getShip_mobile());
		order.setShip_tel(orderOuter.getShip_tel());
		order.setShip_zip(orderOuter.getShip_zip());
		order.setShipping_area(orderOuter.getProvince() + "-" + orderOuter.getCity() + "-" + orderOuter.getRegion());
		order.setShip_name(orderOuter.getShip_name());
		order.setRegionid(orderOuter.getRegion_id());
		order.setType_code(OrderBuilder.COMMONAGE);
		order.setShip_day(orderOuter.getShip_day());
		order.setService_code(orderOuter.getService_code());
		order.setApp_key(orderOuter.getApp_key());
		order.setShip_time(new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT).format(new Date(System.currentTimeMillis())));
		order.setShipping_type(outer.getSending_type());
		order.setRemark(orderOuter.getRemark());
		// 发票信息
		order.setInvoice_content(orderOuter.getInvoice_content());
		order.setInvoice_title(orderOuter.getInvoice_title());
		order.setInvoice_title_desc(orderOuter.getInvoice_title_desc());
		order.setInvoice_type(orderOuter.getInvoice_type());
		order.setApp_key(orderOuter.getApp_key());
		order.setDly_address_id(orderOuter.getDlyaddressid());
		order.setLan_id(orderOuter.getLan_code());
		// order_type
		String order_type = orderOuter.getOrder_type();
		order.setSpread_id(orderOuter.getSpread_member_id());
		order.setService_type(orderOuter.getService_type());
		order.setService_id(orderOuter.getService_id());
		if (!StringUtils.isEmpty(order_type)) {
			order.setOrder_type(order_type);
		} else {
			order.setOrder_type(OrderStatus.ORDER_TYPE_1);// 默认为订购
		}

		String order_id = this.baseDaoSupport.getSequences("s_es_order");
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String prex = cacheUtil.getConfigInfo("ORDER_PREX"); // 订单Id前缀 2017年10月18日AOP后激活需求订单号统一加D songqi
		order_id = prex + order_id;
		Map map = outer.getExtMap();
		if(null!=map.get("intent_order_id")&&!StringUtils.isEmpty(map.get("intent_order_id")+"")){
    		String sql ="select t.order_id from es_order_intent t where t.intent_order_id='"+map.get("intent_order_id")+"' and t.source_from='"+ManagerUtils.getSourceFrom()+"'";
    		order_id = this.baseDaoSupport.queryForString(sql);
    		if(StringUtils.isEmpty(order_id)){
    			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "根据intent_order_id获取order_id异常"));
    		}
    	}
		
		// 总部商城转换
		Map outOrderMap = new HashMap();
		String plat_type = orderOuter.getPlat_type();
		String order_from = orderOuter.getOrder_from();// 获取商城来源
		if (CommonDataFactory.getInstance().isZbOrder(plat_type)) {
			order_from = EcsOrderConsts.ORDER_FROM_10003;
		}
		// 淘宝商城转换
		List<Map> relations = CommonDataFactory.getInstance().getDictRelationListData(StypeConsts.ISTBORDER);
		if (relations != null && relations.size() > 0) {
			for (Map relation : relations) {
				if (order_from.equals(Const.getStrValue(relation, "field_value"))) {
					order_from = Const.getStrValue(relation, "other_field_value");
					break;
				}
			}
		}
		orderOuter.setOrder_from(order_from);
		outer.setOrder_from(order_from);
		// 需要确认是否需要
		// for(Map tempap:orderOuter.getParamsl()){
		// if(null!=tempap.get("intent_order_id")&&!StringUtils.isEmpty(tempap.get("intent_order_id")+"")){
		// String sql ="select t.order_id from es_order_intent t where
		// t.intent_order_id='"+tempap.get("intent_order_id")+"' and
		// t.source_from='"+ManagerUtils.getSourceFrom()+"'";
		// order_id = this.baseDaoSupport.queryForString(sql);
		// if(StringUtils.isEmpty(order_id)){
		// CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,
		// "根据intent_order_id获取order_id异常"));
		// }
		// }
		// }
		order.setOrder_id(order_id);
		order.setSource_from(orderOuter.getSource_from());
		order.setCreaterOrder("true");
		/************************** 用户信息 ****************************/
		// 不确定
		order.setQuery_user_id("-1");
		// order.setMember_id(member.getMember_id());
		// 非匿名购买
		if (member != null) {
			order.setMember_id(member.getMember_id());
		}

		// /**************************计算价格、重量、积分****************************/
		// boolean isProtected = order.getIs_protect().compareTo(1)==0;
		// OrderPrice orderPrice =cartSManager.countPrice(cp,member,sessionid,
		// order.getShipping_id(), ""+order.getRegionid(), isProtected, true,staff_no);
		order.setIs_protect(0);
		order.setProtect_price(0.0);
		order.setGoods_num(Integer.valueOf(orderOuter.getGoods_num()));
		// 修改ding.xiaotao这个移动至属性处理器根据商品规格获取
		// order.setGoods_amount(outer.getOrder_totalfee()==null?0:Double.valueOf(outer.getOrder_totalfee()));//商品金额
		order.setWeight(0.0);
		order.setDiscount(outer.getOrder_disfee() == null ? 0 : Double.valueOf(outer.getOrder_disfee()));
		// 设置原价钱
		order.setOrder_amount(Double.valueOf(orderOuter.getOrder_amount()));
		Double ship_amount = null;
		if (!StringUtil.isEmpty(orderOuter.getShip_amount())) {
			ship_amount = new Double(orderOuter.getShip_amount());
			order.setShipping_amount(ship_amount);
			order.setOrder_amount(order.getOrder_amount() + ship_amount);
		} else {
			// 没传邮费
			order.setShipping_amount(0.0);
		}
		order.setGainedpoint(0L);
		// 配送方式名称
		if (order.getShipping_id() != null && !"".equals(order.getShipping_id())) {
			DlyType dlyType = dlyTypeSManager.getDlyTypeById(order.getShipping_id());
			if (dlyType == null)
				throw new RuntimeException("shipping not found count error");
			order.setShipping_type(dlyType.getName());
		}

		/************ 支付方式价格及名称 ************************/
		PaymentCfgReq reqid = new PaymentCfgReq();
		reqid.setPayment_cfg_id("ZXZF");
		PayCfg payCfg = paymentCfgServ.queryPaymentCfgById(reqid).getPaymentCfg();
		order.setPaymoney(Double.valueOf(outer.getOrder_realfee()));
		if (null != payCfg) {
			order.setPayment_name(payCfg.getName());
			order.setPayment_type(payCfg.getType());
		}

		/************ 创建订单 ************************/
		order.setCreate_time(order.getShip_time());
		order.setStatus(OrderStatus.ORDER_NOT_PAY);
		order.setDisabled(0);
		order.setPay_status(OrderStatus.PAY_NO);
		order.setShip_status(OrderStatus.SHIP_NO);

		order.setPay_time(outer.getPay_time());
		order.setSn(this.createSn());
		return order;
	}

	private OrderTreeBusiRequest setOrderTree(OrderBusiRequest order) {
		// 设置订单树
		OrderTreeBusiRequest orderTreeBusiRequest = CommonData.getData().getOrderTreeBusiRequest();
		orderTreeBusiRequest.setOrder_id(order.getOrder_id());
		orderTreeBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
		orderTreeBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderTreeBusiRequest.setCreate_time(order.getCreate_time());
		orderTreeBusiRequest.setUpdate_time(order.getCreate_time());
		return orderTreeBusiRequest;
	}

	private OrderExtBusiRequest setOrderExt(OrderBusiRequest order, Outer outer) {
		// 订单扩展表
		OrderExtBusiRequest req = CommonData.getData().getOrderTreeBusiRequest().getOrderExtBusiRequest();
		String simple_order_id = this.baseDaoSupport.getSequences("s_es_order");
		req.setSimple_order_id(simple_order_id);
		req.setOrder_id(order.getOrder_id());
		// 不确定
		// req.setReceive_num(this.createRevceiveNum());
		req.setVisible_status(EcsOrderConsts.VISIBLE_STATUS_0);
		req.setRefund_deal_type(EcsOrderConsts.REFUND_DEAL_TYPE_01);
		req.setAbnormal_type(EcsOrderConsts.ORDER_ABNORMAL_TYPE_0);
		req.setIf_Send_Photos(outer.getExtMap().get("if_Send_Photos"));
		// req.setLock_status(EcsOrderConsts.UNLOCK_STATUS);
		req.setLast_deal_time(order.getCreate_time());
		// req.setTid_time(order.getCreate_time());
		req.setTid_time(outer.getTid_time());
		req.setOut_tid(outer.getOut_tid());
		req.setShipping_quick(EcsOrderConsts.SHIPPING_QUICK_02);
		req.setDb_action(ConstsCore.DB_ACTION_INSERT);
		req.setIs_dirty(ConstsCore.IS_DIRTY_YES);

		// 属性处理器前置默认属性
		req.setO_ship_status(outer.getDelivery_status());
		req.setPay_type(outer.getPaytype());
		req.setOrder_from(outer.getOrder_from());

		req.setOrder_city_code(outer.getOrder_city_code());
		req.setPlat_type(outer.getPlat_type());
		req.setOrder_channel(outer.getOrder_channel());
		
		req.setOrder_deal_method(outer.getOrder_deal_method());

		return req;
	}

	private OrderExtvlBusiRequest setOrderExtVl(OrderBusiRequest order, Outer outer) {
		// 纵转横表
		OrderExtvlBusiRequest req = CommonData.getData().getOrderExtvlBusiRequest();
		if (req == null)
			req = new OrderExtvlBusiRequest();

		req.setOrder_id(order.getOrder_id());
		req.setReserve2(outer.getReserve2());// 渠道类型
		req.setReserve0(outer.getReserve0());// 接收方系统标识
		req.setReserve1(outer.getReserve1());// 是否2G、3G升4G is_to4g Reserve1
		req.setSell_price(String.valueOf(outer.getItemList().get(0).getSell_price()));
		req.setOrder_provinc_code(outer.getOrder_provinc_code());
		req.setOrderacctype(outer.getOrderacctype());
		req.setPackage_sale(EcsOrderConsts.PACKAGE_SALE_NO);// 套包销售标志默认为0
		req.setOrder_origfee(outer.getOrder_origfee());// 订单应收总价
		req.setCreate_time(order.getCreate_time());
		req.setInvoice_type(String.valueOf(outer.getInvoice_type()));// 发票信息
		req.setChannel_type(outer.getExtMap().get("channel_type"));
		req.setChannelid(outer.getExtMap().get("channelId"));// 对应aop接口channelId
		req.setOut_office(outer.getExtMap().get("out_office"));// bss的操作点
		req.setOut_operator(outer.getExtMap().get("out_operator"));//// 操作员
		req.setPlan_title(outer.getItemList().get(0).getPlan_title());// 套餐名
		req.setBrand_name(outer.getItemList().get(0).getBrand_name());// 品牌名
		req.setGoodsname(outer.getExtMap().get("GoodsName"));
		// dingxiaotao 6.6新增
		req.setIs_pay(outer.getExtMap().get(AttrConsts.IS_PAY));
		req.setDistrict_code(outer.getExtMap().get(AttrConsts.DISTRICT_CODE));
		req.setResult_url(outer.getExtMap().get("result_url"));
		// 属性处理器前置属性设置
		req.setGuarantor(outer.getGuarantor());
		req.setBill_mail_type(outer.getBill_mail_type());
		req.setIs_write_card(EcsOrderConsts.IS_WRITE_CARD_NO);
		req.setReliefpres_flag(outer.getReliefpres_flag());
		req.setCustomertype(outer.getCustomerType());
		req.setOrder_disfee(outer.getOrder_disfee());
		req.setIs_liang(EcsOrderConsts.NO_DEFAULT_VALUE);
		req.setSyn_mode(outer.getExtMap().get("syn_mode"));
		req.setOpttype(outer.getExtMap().get("optType"));
		req.setService_type(outer.getExtMap().get("service_type"));
		req.setModtype(outer.getExtMap().get("modType"));
		req.setMainnumber(outer.getExtMap().get("mainNumber"));
		req.setViceproductid(outer.getExtMap().get("viceProductId"));
		req.setOrdertype(outer.getExtMap().get("orderType"));
		req.setIs_order_master(outer.getExtMap().get("is_order_master"));
		req.setSeccardroletype(outer.getExtMap().get("secCardRoleType"));
		req.setProductmode(outer.getExtMap().get("productMode"));
		req.setChecktype(outer.getExtMap().get("checkType"));
		req.setDebutysn(outer.getExtMap().get("debutySn"));
		req.setDebutysn(outer.getExtMap().get("debutySn"));
		req.setIntent_order_id(outer.getExtMap().get("intent_order_id"));
		req.setIs_realname(outer.getExtMap().get("is_realname"));
		req.setSale_mod_type(outer.getExtMap().get("sale_mod_type"));
		req.setMarking_tag(outer.getExtMap().get("marking_tag"));
		req.setService_remarks(outer.getService_remarks());
		req.setDevelopment_name(StringUtil.isEmpty(outer.getDevelopment_name()) ? outer.getExtMap().get("development_name") : outer.getDevelopment_name());
		//req.setDevelopment_name(outer.getDevelopment_name());
		//req.setDevelopment_code(outer.getDevelopment_code());
		req.setDevelopment_code(StringUtil.isEmpty(outer.getDevelopment_code()) ? outer.getExtMap().get("development_code") : outer.getDevelopment_code());
		req.setDevelopment_point_code(outer.getExtMap().get("development_point_code"));
		req.setDevelopment_point_name(outer.getExtMap().get("development_point_name"));
		
		req.setCbss_develop_code(outer.getExtMap().get("cbss_develop_code"));
		req.setCbss_development_point_code(outer.getExtMap().get("cbss_development_point_code"));
		
		req.setDevelopernumber(outer.getExtMap().get("developerNumber"));
		req.setDevelop_point_code_new(outer.getExtMap().get("develop_point_code_new"));
		req.setDevelop_code_new(outer.getExtMap().get("develop_code_new"));
		req.setDevelop_name_new(outer.getExtMap().get("develop_name_new"));
		req.setSource_type(outer.getExtMap().get("source_type"));
		req.setRegist_type(outer.getExtMap().get("regist_type"));
		req.setDeal_operator_name(outer.getExtMap().get("deal_operator_name"));
		req.setDeal_operator_num(outer.getExtMap().get("deal_operator_num"));
		req.setCouponbatchid(outer.getExtMap().get("couponbatchid"));
		req.setIs_examine_card(outer.getExtMap().get("is_examine_card"));
		req.setCust_id(outer.getExtMap().get("cust_id"));
		req.setAgent_district(outer.getExtMap().get("agent_district"));
		req.setAgent_city(outer.getExtMap().get("agent_city"));
		req.setAgent_name(outer.getExtMap().get("agent_name"));
		req.setAgent_code_dls(outer.getExtMap().get("agent_code"));
		req.setOss_operator(outer.getExtMap().get("oss_operator"));
		req.setBss_operator_name(outer.getExtMap().get("bss_operator_name"));
		req.setBss_operator(outer.getExtMap().get("bss_operator"));
		req.setIndustry_source(outer.getExtMap().get("industry_source"));
		req.setGroup_name(outer.getExtMap().get("group_name"));
		req.setGroup_code(outer.getExtMap().get("group_code"));
		req.setSeller_message(outer.getExtMap().get("seller_message"));
		req.setIccid(outer.getExtMap().get("ICCID"));
		req.setFund_type(outer.getExtMap().get("fund_type"));
		req.setEvdo_num(outer.getExtMap().get("evdo_num"));
		req.setUser_kind(outer.getExtMap().get("user_kind"));
		//add by sgf
		req.setMarket_user_id(outer.getExtMap().get("market_user_id"));
        req.setSeed_user_id(outer.getExtMap().get("seed_user_id"));
        req.setShare_svc_num(outer.getExtMap().get("share_svc_num"));
        req.setActivity_name(outer.getExtMap().get("activity_name"));
        req.setTop_share_userid(outer.getExtMap().get("top_share_userid"));
        req.setTop_share_num(outer.getExtMap().get("top_share_num"));
        req.setSale_mode(outer.getExtMap().get("sale_mode"));
        req.setObject_esn(outer.getExtMap().get("object_esn"));
        req.setObject_id(outer.getExtMap().get("object_id"));
        req.setSubs_id(outer.getExtMap().get("subs_id"));
        req.setService_num(outer.getExtMap().get("service_num"));
        req.setUser_to_account(outer.getExtMap().get("user_to_account"));
        req.setTop_share_num(outer.getExtMap().get("top_share_num"));
        req.setTop_share_userid(outer.getExtMap().get("top_share_userid"));
        req.setIs_new(outer.getExtMap().get("is_new"));
        req.setIs_blankcard(outer.getExtMap().get("is_blankcard"));
        req.setIs_no_modify(outer.getExtMap().get("is_no_modify"));
        req.setTop_seed_professional_line(outer.getExtMap().get("top_seed_professional_line"));
        req.setTop_seed_type(outer.getExtMap().get("top_seed_type"));
        req.setTop_seed_group_id(outer.getExtMap().get("top_seed_group_id"));
//add by mh 泛智能终端字段
        
        req.setElement_id(outer.getExtMap().get("element_id"));
        req.setMobile_type(outer.getExtMap().get("mobile_type"));
        
        return req;
	}

	private OrderPayBusiRequest setPaymentInfo(OrderBusiRequest order, OrderOuter orderOuter, Outer outer) {
		List paymentList = CommonData.getData().getOrderTreeBusiRequest().getOrderPayBusiRequests();
		// 支付信息
		OrderPayBusiRequest payment = new OrderPayBusiRequest();
		paymentList.add(payment);
		payment.setPayment_id(baseDaoSupport.getSequences("S_ES_PAYMENT_LOGS"));
		payment.setOrder_id(order.getOrder_id());
		payment.setPaytype(0);
		payment.setCreate_time(order.getCreate_time());
		// 这个需要根据支付方式进行判断为-1(货到付款)还是0(在线支付)
		if ("online".equals(orderOuter.getPayment_id())) {
			payment.setStatus(OrderStatus.PAY_STATUS_0);
		} else {
			payment.setStatus(OrderStatus.PAY_STATUS_NOT_CONFIRM);
		}
		payment.setUserid(order.getPay_user_id());
		payment.setMember_id(order.getMember_id());
		payment.setOrder_id(order.getOrder_id());
		if (OrderStatus.WP_CREATE_TYPE_6.equals(order.getCreate_type() + "")
				|| OrderStatus.WP_CREATE_TYPE_7.equals(order.getCreate_type() + "")) {
			payment.setType(OrderStatus.PAY_TYPE_2);
		} else {
			payment.setType(OrderStatus.PAY_TYPE_1);
		}
		payment.setPay_method(outer.getPay_method());
		// payment.setPay_type(order.getPayment_id());

		if (EcsOrderConsts.PAY_METHOD_ZFB.equals(outer.getPay_method()))
			payment.setPay_type(EcsOrderConsts.PAY_TYPE_ZXZF);
		else
			payment.setPay_type(outer.getPaytype());
		payment.setMoney(order.getPaymoney());

		return payment;
	}

	private OrderDeliveryBusiRequest setDeliver(OrderBusiRequest order, Outer outer) {
		List list = CommonData.getData().getOrderTreeBusiRequest().getOrderDeliveryBusiRequests();
		OrderDeliveryBusiRequest delivery = new OrderDeliveryBusiRequest();
		list.add(delivery);
		delivery.setDelivery_id(this.baseDaoSupport.getSequences("S_ES_DELIVERY"));
		if (OrderStatus.WP_CREATE_TYPE_6.equals(order.getCreate_type() + "")
				|| OrderStatus.WP_CREATE_TYPE_7.equals(order.getCreate_type() + "")) {
			delivery.setType(2);
		} else {
			delivery.setType(1);
		}
		delivery.setOrder_id(order.getOrder_id());
		delivery.setMember_id(order.getMember_id());
		delivery.setShip_type(order.getShipping_type());
		delivery.setShip_name(order.getShip_name());
		// 后面属性处理器前移设置
		// delivery.setShip_addr(order.getShip_addr());
		// delivery.setShip_mobile(order.getShip_mobile());
		delivery.setShip_email(order.getShip_email());
		delivery.setShip_tel(order.getShip_tel());
		delivery.setShip_zip(order.getShip_zip());
		delivery.setCreate_time(order.getCreate_time());
		delivery.setWeight(order.getWeight().longValue());
		delivery.setPrint_status(OrderStatus.DELIVERY_PRINT_STATUS_0);
		delivery.setShip_status(OrderStatus.DELIVERY_SHIP_STATUS_NOT_CONFIRM);
		delivery.setShip_num(0);
		delivery.setUserid(order.getShip_user_id());
		delivery.setBatch_id(order.getBatch_id());
		delivery.setCreate_type(order.getCreate_type() + "");
		String delivery_id = this.baseDaoSupport.getSequences("s_es_delivery");
		delivery.setDelivery_id(delivery_id);
		// 不确定
		// AdminUser user =
		// OrderThreadLocalHolder.getInstance().getOrderCommonData().getAdminUser();
		// if(user!=null){
		// delivery.setOp_name(user.getUsername());
		// }

		// 前置属性处理器设置默认值

		delivery.setNeed_receipt(EcsOrderConsts.NO_DEFAULT_VALUE);
		delivery.setN_shipping_amount(EcsOrderConsts.DEFAULT_FEE_ZERO);


		if(org.apache.commons.lang.StringUtils.isNotBlank(outer.getProvinc_code())
				&& org.apache.commons.lang.StringUtils.isNumeric(outer.getProvinc_code()))
			delivery.setProvince_id(Long.valueOf(outer.getProvinc_code()));
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(outer.getCity_code())
				&& org.apache.commons.lang.StringUtils.isNumeric(outer.getCity_code()))
			delivery.setCity_id(Long.valueOf(outer.getCity_code()));
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(outer.getArea_code())
				&& org.apache.commons.lang.StringUtils.isNumeric(outer.getArea_code()))
			delivery.setRegion_id(Long.valueOf(outer.getArea_code()));
		
		delivery.setShipping_time("NOLIMIT");
		delivery.setOut_house_id("-1");
		delivery.setPost_fee(outer.getPost_fee());
		delivery.setDelivery_type("0");
		// 前置属性处理器设置默认值
		delivery.setIs_protect(order.getIs_protect());
		delivery.setRegion(outer.getDistrict());
		delivery.setShip_mobile(outer.getReceiver_mobile());
		delivery.setShip_addr(order.getShip_addr());
		return delivery;
	}

	private OrderItemBusiRequest setOrderItem(OrderOuter orderOuter, OrderBusiRequest order) {
		OrderItemBusiRequest orderItem = new OrderItemBusiRequest();
		String itemid = this.baseDaoSupport.getSequences("s_es_order_items");
		orderItem.setItem_id(itemid);
		orderItem.setPrice(Double.valueOf(orderOuter.getOrder_amount()));
		// 此处要修改OrderOuter Name的名称，如果有货品名称，则取货品名称否则取商品名称
		if (orderOuter.getOffer_name() != null) {
			orderItem.setName(orderOuter.getOffer_name());
		} else {
			orderItem.setName(orderOuter.getGoods_name());
		}
		orderItem.setNum(Integer.valueOf(orderOuter.getGoods_num()));

		orderItem.setGoods_id(orderOuter.getGoods_id());// 需要确认是商品ID还是商品编码,还是货品ID
		orderItem.setShip_num(0);
		orderItem.setSpec_id(
				StringUtil.isEmpty(orderOuter.getSpec_id()) ? orderOuter.getProduct_id() : orderOuter.getSpec_id());
		orderItem.setProduct_id(orderOuter.getProduct_id());
		orderItem.setOrder_id(order.getOrder_id());
		orderItem.setGainedpoint(0L);
		orderItem.setAddon(orderOuter.getAddon());

		// orderItem.setLan_id(order.getLan_id());
		// orderItem.setLv_id(orderOuter.getMember_lv_id());
		orderItem.setItem_type(orderOuter.getItem_type());
		orderItem.setCoupon_price(Double.valueOf(orderOuter.getP_order_amount()));

		return orderItem;
	}

	private OrderItemExtBusiRequest setOrderItemExt(OrderBusiRequest order, OrderItemBusiRequest orderItem,
			Outer outer) {
		OrderItemExtBusiRequest orderItemExt = orderItem.getOrderItemExtBusiRequest();
		orderItemExt.setOrder_id(order.getOrder_id());
		orderItemExt.setItem_id(orderItem.getItem_id());

		// 属性处理器前置
		orderItemExt.setInvoice_print_type(outer.getInvoice_print_type());
		orderItemExt.setReserve8(outer.getReserve8());

		return orderItemExt;
	}

	private OrderDeliveryItemBusiRequest setOrderDeliverItem(OrderItemBusiRequest orderItem,
			OrderDeliveryBusiRequest delivery) {
		OrderDeliveryItemBusiRequest item = new OrderDeliveryItemBusiRequest();
		item.setItem_id(this.baseDaoSupport.getSequences("S_ES_DELIVERY_ITEM"));
		item.setOrder_id(orderItem.getOrder_id());
		int ship_num = orderItem.getNum() - orderItem.getShip_num();
		item.setGoods_id(orderItem.getGoods_id());
		item.setName(orderItem.getName());
		item.setNum(ship_num);
		item.setProduct_id(orderItem.getProduct_id());
		item.setSn(orderItem.getSn());
		item.setItemtype(0);
		item.setOrder_item_id(orderItem.getItem_id());
		item.setDelivery_id(delivery.getDelivery_id());

		return item;
	}

	private OrderItemProdBusiRequest setOrderItemProd(OrderBusiRequest order, OrderItemBusiRequest orderItem, Goods g) {
		String item_pro_inst_id = this.baseDaoSupport.getSequences("S_ES_ORDER_ITEMS_PROD");
		OrderItemProdBusiRequest pro = new OrderItemProdBusiRequest();
		pro.setOrder_id(order.getOrder_id());
		pro.setCreate_time(order.getCreate_time());
		pro.setItem_id(orderItem.getItem_id());
		pro.setItem_prod_inst_id(item_pro_inst_id);
		pro.setItem_spec_goods_id(orderItem.getGoods_id());
		pro.setProd_spec_goods_id(g.getProd_goods_id());
		pro.setProd_spec_type_code(g.getType_code());
		pro.setStatus("0");
		pro.setName(g.getName());
		pro.setDb_action(ConstsCore.DB_ACTION_INSERT);
		pro.setIs_dirty(ConstsCore.IS_DIRTY_YES);

		return pro;
	}

	private OrderItemProdExtBusiRequest setOrderItemProdExt(OrderItemProdBusiRequest orderItemProd, Outer outer) {
		// 扩展表写入数据
		OrderItemProdExtBusiRequest reqext = new OrderItemProdExtBusiRequest();
		reqext.setOrder_id(orderItemProd.getOrder_id());
		reqext.setItem_id(orderItemProd.getItem_id());
		reqext.setItem_prod_inst_id(orderItemProd.getItem_prod_inst_id());
		reqext.setDb_action(ConstsCore.DB_ACTION_INSERT);
		reqext.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		reqext.setCert_card_num(outer.getItemList().get(0).getCert_card_num());
		reqext.setCert_num2(outer.getItemList().get(0).getCert_num2());
		reqext.setCert_address(outer.getItemList().get(0).getCert_address());
		reqext.setCert_card_sex(outer.getExtMap().get("sex"));
		reqext.setCerti_sex(outer.getExtMap().get("sex"));
		reqext.setCert_failure_time(outer.getItemList().get(0).getCert_failure_time());
		reqext.setCert_eff_time(outer.getItemList().get(0).getCert_eff_time());
		reqext.setCerti_type(outer.getItemList().get(0).getCert_type());
		reqext.setCert_card_birth(outer.getExtMap().get("cust_birthday"));//生日
		reqext.setCert_issuer(outer.getExtMap().get("cert_issuer"));//签证机关
		reqext.setCert_card_name(outer.getExtMap().get("useCustName"));//姓名useCustName
/*		reqext.setCert_card_sex(outer.getExtMap().get("is_realname"));
*/		reqext.setIs_old(outer.getExtMap().get("is_old"));
		reqext.setFirst_payment(outer.getItemList().get(0).getFirst_payment());
		if(StringUtils.isEmpty(reqext.getFirst_payment())){
			reqext.setFirst_payment("ALLM");
		}
		reqext.setIs_iphone_plan(outer.getItemList().get(0).getIs_iphone_plan());
		reqext.setIs_loves_phone(outer.getItemList().get(0).getIs_loves_phone());
		return reqext;
	}
	
	/**
	 * 从数据库中更新订单信息
	 * @param order_id
	 * @throws Exception
	 */
	private void updateOrderTreeFromDB(String order_id) throws Exception{
		UpdateOrderTreeReq req = new UpdateOrderTreeReq();
		
		req.setOrder_id(order_id);
		
		// 调用DUBBO
        ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        UpdateOrderTreeRsp rsp = client.execute(req, UpdateOrderTreeRsp.class);

        if(ConstsCore.ERROR_FAIL.equals(rsp.getError_code())){
        	// 调用DUBBO异常或者是流程启动失败，抛出异常
        	throw new Exception(rsp.getError_msg());
        }
	}

	/**
	 * 更新订单信息
	 * @param outerList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes"})
	public void updateOrder(String order_id,Outer outer) throws Exception {
		try{
			String batch_id = this.baseDaoSupport.getSequences("s_es_order");

			List<Map> paramsl = this.createParamsl(outer, batch_id);
			
			this.initParam(paramsl);
			
			this.updateOrderTreeInfo(order_id,outer, paramsl, batch_id);
			
			this.updateOrderTreeFromDB(order_id);
		}catch(Exception e){
			logger.error("NewStdOrderService updateOrder error:"+e.getMessage(), e);
			throw e;
		}finally{
			CommonData.getData().removeData();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public String getExistOrderId(Outer outer) throws Exception{
		Map map = outer.getExtMap();
		
		if(!map.containsKey("intent_order_id"))
			throw new Exception("订单数据更新失败：未传入意向单编号！");
		
		String intent_order_id = String.valueOf(map.get("intent_order_id"));
		
		if(org.apache.commons.lang.StringUtils.isBlank(intent_order_id))
			throw new Exception("订单数据更新失败：未传入意向单编号！");
		
		//根据意向单编号查询订单是否存在
		String sql = "SELECT a.order_id FROM es_order_extvtl a WHERE a.intent_order_id='"+intent_order_id+"' ";
		
		List ret = this.baseDaoSupport.queryForList(sql);
		
		if(ret==null || ret.size()==0)
			throw new Exception("根据意向单编号："+intent_order_id+"未查到订单");
		
		if(ret.size() > 1)
			throw new Exception("根据意向单编号："+intent_order_id+"查到多条订单");
		
		Map m = (Map)ret.get(0);
		
		//是否自定义流程
		String order_id = String.valueOf(m.get("order_id"));
		
		return order_id;
	}
	
	@SuppressWarnings("rawtypes")
	public String getNodeInstanceId(String order_id) throws Exception{
		String sql = "SELECT a.instance_id,a.deal_bean FROM ES_WORK_CUSTOM_NODE_INS A WHERE a.order_id='"
				+order_id+"' and a.is_curr_step=1 ";
		
		List ret = this.baseDaoSupport.queryForList(sql);
		
		if(ret==null || ret.size()==0)
			throw new Exception("根据订单编号："+order_id+"未查到当前环节");
		
		if(ret.size() > 1)
			throw new Exception("根据订单编号："+order_id+"查到当前环节");
		
		Map m = (Map)ret.get(0);
		
		String deal_bean = String.valueOf(m.get("deal_bean"));
		String instance_id = String.valueOf(m.get("instance_id"));
		
		//根据处理类判断是否在“更新订单数据环节”
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String cfg = cacheUtil.getConfigInfo("WORKFLOW_UPDATE_ORDER_NODES");
		
		if(org.apache.commons.lang.StringUtils.isBlank(cfg))
			throw new Exception("未查到更新订单环节配置");
		
		String[] beans = cfg.split(",");
		List<String> beans_list = Arrays.asList(beans);
		Set<String> beans_set = new HashSet<String>(beans_list);
		
		if(!beans_set.contains(deal_bean))
			throw new Exception(order_id+"订单不在更新数据环节");
		
		return instance_id;
	}
	
	/**
	 * 根据订单编号查询ES_MEMBER信息
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private MemberBusiRequest getMemberByOrderId(String order_id) throws Exception{
		String sql = "SELECT a.member_id FROM es_order a WHERE a.order_id='"+order_id+"' ";
		
		List ret = this.baseDaoSupport.queryForList(sql);
		
		if(ret==null || ret.size()==0)
			return null;
		
		Map m = (Map)ret.get(0);
		
		if(!m.containsKey("member_id")){
			return null;
		}
		
		String member_id = String.valueOf(m.get("member_id"));
		
		if(org.apache.commons.lang.StringUtils.isBlank(member_id) || "null".equals(member_id)){
			return null;
		}
		
		sql = "SELECT * FROM es_member a WHERE a.member_id='"+member_id+"' ";
		
		List<MemberBusiRequest> members = this.baseDaoSupport.queryForList(sql, MemberBusiRequest.class);
		
		if(members!=null && members.size()>0){
			return members.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据订单编号查询ES_ORDER_EXTVL信息
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	private OrderExtvlBusiRequest getOrderExtByOrderId(String order_id) throws Exception{
		String sql = "SELECT * FROM es_order_extvtl a WHERE a.order_id='"+order_id+"' ";
		
		List<OrderExtvlBusiRequest> ret = this.baseDaoSupport.queryForList(sql, OrderExtvlBusiRequest.class);
		
		if(ret!=null && ret.size()>0){
			return ret.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 更新订单信息
	 * @param order_id
	 * @param outer
	 * @param paramsl
	 * @param batch_id
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateOrderTreeInfo(String order_id,
			Outer outer, List<Map> paramsl, String batch_id) throws Exception{
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		orderTree.setUpdate_time(DateUtil.getTime2().toString());
		
		if(orderTree == null)
			throw new Exception("根据订单编号"+order_id+"未查到订单树");
		
		if (null == paramsl || paramsl.size() == 0) {
			return;
		}
		
		CommonData commonData = CommonData.getData();
		
		//将订单树对象设置到线程变量中
		commonData.setOrderTreeBusiRequest(orderTree);
		
		// 设置订单信息
		OrderOuter orderOuter = packageOrderOuter(outer, batch_id);
		if (outer.getItemList() != null && outer.getItemList().size() > 0) {
			OuterItem oi = null;
			
			if ("HB".equals(ManagerUtils.getSourceFrom())) {
				int i = Integer.parseInt(outer.getReserve9());
				oi = outer.getItemList().get(i);
			} else {
				oi = outer.getItemList().get(0);
			}
			
			orderOuter.setHouse_id(oi.getHouse_id());
			orderOuter.setOrg_id(oi.getOrg_id());
			orderOuter.setProduct_id(oi.getProduct_id());
			orderOuter.setShipping_id("PY");
			orderOuter.setP_order_amount(orderOuter.getOrder_amount());
		}

		//获取参数MAP
		HashMap map = (HashMap) paramsl.get(0);
		orderOuter.setParamsl(paramsl);
		
		// 设置会员信息
		MemberBusiRequest member = updateMember(orderTree,orderOuter, map);
		
		// 设置订单主表
		OrderBusiRequest order = updateOrder(orderTree,outer, orderOuter, member);

		// 订单扩展表
		updateOrderExt(orderTree,order, outer);

		// 纵转横表信息
		updateOrderExtVl(orderTree,order, outer);
		
		// 订单支付信息
		updatePaymentInfo(orderTree,order, orderOuter, outer);

		// 订单物流信息
		OrderDeliveryBusiRequest orderDeliver = updateDeliver(orderTree,order, outer);
		
		//线程本地对象中清除商品信息信息
		commonData.getOrderTreeBusiRequest().getOrderItemBusiRequests().clear();
		commonData.getOrderDeliveryItemBusiRequests().clear();
		
		//清除数据库订单商品信息
		this.clearDBGoodsItems(order_id);

		//商品信息
		List<OuterItem> items = outer.getItemList();
		
		for (int i = 0; i < items.size(); i++) {
			// 设置订单项目
			OrderItemBusiRequest orderItem = setOrderItem(orderOuter, order);
			commonData.getOrderTreeBusiRequest().getOrderItemBusiRequests().add(orderItem);

			// 设置订单项扩展表
			setOrderItemExt(order, orderItem, outer);

			// 货品信息表
			List<Goods> products = null;
			ProductsListReq plreq = new ProductsListReq();
			plreq.setGoods_id(orderItem.getGoods_id());
			ProductsListResp plresp = goodServices.listProducts(plreq);
			if (plresp != null) {
				products = plresp.getProducts();
			}
			
			// 插入商品货品表
			if (products != null && products.size() > 0) {
				for (Goods g : products) {
					// 订单项货品信息
					OrderItemProdBusiRequest orderItemProd = setOrderItemProd(order, orderItem, g);
					orderItem.getOrderItemProdBusiRequests().add(orderItemProd);

					// 订单项货品扩展信息
					OrderItemProdExtBusiRequest orderItemProdExt = setOrderItemProdExt(orderItemProd, outer);
					orderItemProd.setOrderItemProdExtBusiRequest(orderItemProdExt);
				}
			}
			
			// 设置订单项物流信息
			OrderDeliveryItemBusiRequest orderDeliverItem = setOrderDeliverItem(orderItem, orderDeliver);
			if (orderDeliverItem.getNum() > 0) {// 有没发货商品才加到物流item表
				orderItem.setShip_num(orderDeliverItem.getNum());
				commonData.getOrderDeliveryItemBusiRequests().add(orderDeliverItem);
			}
		}
		
		// 执行属性处理树
		BusinessHandlerUtil businessHandlerUtil = new BusinessHandlerUtil();
		businessHandlerUtil.executeHandler(map);

		// 调用前置规则
		this.readPreRule();

		// 保存订单树
		this.updateOrderTree();
	}
	
	/**
	 * 更新ES_MEMBER
	 * @param orderTree
	 * @param orderOuter
	 * @param map
	 * @return
	 * @throws Exception
	 */
	private MemberBusiRequest updateMember(OrderTreeBusiRequest orderTree,
			OrderOuter orderOuter, HashMap<String, String> map) throws Exception {
		//更新ES_MEMBER表对象
		MemberBusiRequest member = this.getMemberByOrderId(orderTree.getOrder_id());
		
		if(member == null)
			throw new Exception("未查到订单"+orderTree.getOrder_id()+"的ES_MEMBER对象");

		AdminUser user = OrderThreadLocalHolder.getInstance().getOrderCommonData().getAdminUser();
		if (orderOuter.getUname() == null && user != null) {
			orderOuter.setUname(user.getUsername());
		}
		if (orderOuter.getName() == null && user != null) {
			orderOuter.setName(user.getUsername());
		}
		
		String member_name = orderOuter.getUname();
		if (StringUtil.isEmpty(member_name)){
			member_name = orderOuter.getName();
		}

		if (map != null && StringUtil.isEmpty(member_name)) {
			member_name = map.get("buyer_id");
		}
		
		if (StringUtil.isEmpty(member_name)){
			member_name = "defmember";
		}

		if (StringUtil.isEmpty(orderOuter.getName()) && map != null){
			orderOuter.setName(map.get("buyer_name"));
		}

		if (StringUtil.isEmpty(orderOuter.getShip_tel()) && map != null){
			orderOuter.setShip_tel(map.get("reference_phone"));
		}
		
		if (StringUtil.isEmpty(orderOuter.getShip_mobile()) && map != null){
			orderOuter.setShip_mobile(map.get("phone_num"));
		}
		
		if (StringUtil.isEmpty(orderOuter.getRegion()) && map != null){
			orderOuter.setRegion(map.get("district"));
		}

		if (StringUtil.isEmpty(orderOuter.getCity()) && map != null){
			orderOuter.setCity(map.get("city"));
		}

		if (StringUtil.isEmpty(orderOuter.getProvince()) && map != null){
			orderOuter.setProvince(map.get("province"));
		}
		
		member.setName(orderOuter.getName());
		member.setUname(member_name);
		member.setPassword(member.getUname());
		member.setTel(orderOuter.getShip_tel());
		member.setMobile(orderOuter.getShip_mobile());
		
		member.setZip(orderOuter.getShip_zip());
		member.setRegion(orderOuter.getRegion());
		member.setCity(orderOuter.getCity());
		member.setProvince(orderOuter.getProvince());
		member.setSex(orderOuter.getSex());
		
		member.setAddress(orderOuter.getShip_addr());

		if (map != null) {
			String rid = map.get("area_code");
			if (!StringUtil.isEmpty(rid)){
				member.setRegion_id(Integer.valueOf(rid));
			}
				
			String cid = map.get("city_code");
			if (!StringUtil.isEmpty(cid)){
				member.setCity_id(Integer.valueOf(cid));
			}
				
			String pid = map.get("provinc_code");
			if (!StringUtil.isEmpty(pid)){
				member.setProvince_id(Integer.valueOf(pid));
			}

			member.setBuyer_uid(map.get("uid"));
			member.setShip_area(map.get("ship_area"));
			member.setCert_card_num(map.get("cert_card_num"));
			member.setCert_address(map.get("cert_address"));
			member.setCert_failure_time(map.get("cert_failure_time"));
			member.setCert_type(map.get("certi_type"));
			member.setCustomertype(map.get("CustomerType"));
		}
		
		if (StringUtil.isEmpty(member.getParentid())){
			member.setParentid("0");
		}

		member.setLv_id(Const.MEMBER_LV_COMMON);
		member.setLv_name("普通会员");

		member.setPoint(0);
		member.setAdvance(0D);
		member.setLogincount(1);
		member.setPassword(StringUtil.md5(member.getPassword()));
		
		//线程本地对象中设置member对象
		CommonData.getData().setMemberBusiRequest(member);
		
		return member;
	}
	
	/**
	 * 更新ES_ORDER
	 * @param orderTree
	 * @param outer
	 * @param orderOuter
	 * @param member
	 * @return
	 * @throws Exception
	 */
	private OrderBusiRequest updateOrder(OrderTreeBusiRequest orderTree,Outer outer, 
			OrderOuter orderOuter, MemberBusiRequest member) throws Exception {
		OrderBusiRequest order = orderTree.getOrderBusiRequest();

		if (!StringUtil.isEmpty(orderOuter.getUserid())) {
			order.setUserid(orderOuter.getUserid());
			// 设置订单归属对象
			AdminUser adminUser = OrderThreadLocalHolder.getInstance().getOrderCommonData().getAdminUser();
			if (adminUser != null)
				order.setOrder_adscription_id(adminUser.getFounder() + "");
		} else {
			order.setUserid("-1");
		}
		
		order.setCreate_type(Integer.valueOf(orderOuter.getCreate_type()));
		
		order.setGoods_id(orderOuter.getGoods_id());
		order.setGoods_type(CommonDataFactory.getInstance().
				getGoodSpec(null, orderOuter.getGoods_id(), SpecConsts.TYPE_ID));

		order.setShipping_id(orderOuter.getShipping_id()); // 配送方式
		order.setPayment_id(orderOuter.getPayment_id());// 支付方式
		order.setShip_addr(orderOuter.getShip_addr());
		order.setShip_mobile(orderOuter.getShip_mobile());
		order.setShip_tel(orderOuter.getShip_tel());
		order.setShip_zip(orderOuter.getShip_zip());
		order.setShipping_area(orderOuter.getProvince() + "-" 
				+ orderOuter.getCity() + "-" + orderOuter.getRegion());
		order.setShip_name(orderOuter.getShip_name());
		order.setShip_day(orderOuter.getShip_day());
		
		order.setRegionid(orderOuter.getRegion_id());
		order.setType_code(OrderBuilder.COMMONAGE);
		order.setService_code(orderOuter.getService_code());
		order.setApp_key(orderOuter.getApp_key());
		order.setShipping_type(outer.getSending_type());
		order.setRemark(orderOuter.getRemark());
		
		// 发票信息
		order.setInvoice_content(orderOuter.getInvoice_content());
		order.setInvoice_title(orderOuter.getInvoice_title());
		order.setInvoice_title_desc(orderOuter.getInvoice_title_desc());
		order.setInvoice_type(orderOuter.getInvoice_type());
		order.setApp_key(orderOuter.getApp_key());
		order.setDly_address_id(orderOuter.getDlyaddressid());
		order.setLan_id(orderOuter.getLan_code());
		
		// order_type
		String order_type = orderOuter.getOrder_type();
		order.setSpread_id(orderOuter.getSpread_member_id());
		order.setService_type(orderOuter.getService_type());
		order.setService_id(orderOuter.getService_id());
		if (!StringUtils.isEmpty(order_type)) {
			order.setOrder_type(order_type);
		} else {
			order.setOrder_type(OrderStatus.ORDER_TYPE_1);// 默认为订购
		}

		order.setCreaterOrder("true");
		/************************** 用户信息 ****************************/
		order.setQuery_user_id("-1");
		if (member != null) {
			order.setMember_id(member.getMember_id());
		}

		order.setIs_protect(0);
		order.setProtect_price(0.0);
		order.setGoods_num(Integer.valueOf(orderOuter.getGoods_num()));
		order.setWeight(0.0);
		order.setDiscount(outer.getOrder_disfee() == null ? 0 : Double.valueOf(outer.getOrder_disfee()));
		
		// 设置原价钱
		order.setOrder_amount(Double.valueOf(orderOuter.getOrder_amount()));
		Double ship_amount = null;
		if (!StringUtil.isEmpty(orderOuter.getShip_amount())) {
			ship_amount = new Double(orderOuter.getShip_amount());
			order.setShipping_amount(ship_amount);
			order.setOrder_amount(order.getOrder_amount() + ship_amount);
		} else {
			// 没传邮费
			order.setShipping_amount(0.0);
		}
		order.setGainedpoint(0L);
		// 配送方式名称
		if (order.getShipping_id() != null && !"".equals(order.getShipping_id())) {
			DlyType dlyType = dlyTypeSManager.getDlyTypeById(order.getShipping_id());
			if (dlyType == null)
				throw new RuntimeException("shipping not found count error");
			order.setShipping_type(dlyType.getName());
		}

		/************ 支付方式价格及名称 ************************/
		PaymentCfgReq reqid = new PaymentCfgReq();
		reqid.setPayment_cfg_id("ZXZF");
		PayCfg payCfg = paymentCfgServ.queryPaymentCfgById(reqid).getPaymentCfg();
		order.setPaymoney(Double.valueOf(outer.getOrder_realfee()));
		if (null != payCfg) {
			order.setPayment_name(payCfg.getName());
			order.setPayment_type(payCfg.getType());
		}

		/************ 创建订单 ************************/
		order.setStatus(OrderStatus.ORDER_NOT_PAY);
		order.setDisabled(0);
		order.setPay_status(OrderStatus.PAY_NO);
		order.setShip_status(OrderStatus.SHIP_NO);

		order.setPay_time(outer.getPay_time());
		
		return order;
	}
	
	/**
	 * 更新ES_ORDER_EXT
	 * @param orderTree
	 * @param order
	 * @param outer
	 * @return
	 * @throws Exception
	 */
	private OrderExtBusiRequest updateOrderExt(OrderTreeBusiRequest orderTree,
			OrderBusiRequest order, Outer outer) throws Exception {
		// 订单扩展表
		OrderExtBusiRequest req = orderTree.getOrderExtBusiRequest();
		
		req.setVisible_status(EcsOrderConsts.VISIBLE_STATUS_0);
		req.setRefund_deal_type(EcsOrderConsts.REFUND_DEAL_TYPE_01);
		req.setAbnormal_type(EcsOrderConsts.ORDER_ABNORMAL_TYPE_0);
		req.setIf_Send_Photos(outer.getExtMap().get("if_Send_Photos"));
		req.setLast_deal_time(DateUtils.format(new Date(), CrmConstants.DATE_TIME_FORMAT));
		req.setShipping_quick(EcsOrderConsts.SHIPPING_QUICK_02);

		// 属性处理器前置默认属性
		req.setO_ship_status(outer.getDelivery_status());
		req.setPay_type(outer.getPaytype());
		req.setOrder_city_code(outer.getOrder_city_code());
		req.setOrder_channel(outer.getOrder_channel());

		return req;
	}
	
	/**
	 * 更新ES_ORDER_EXTVL
	 * @param orderTree
	 * @param order
	 * @param outer
	 * @return
	 * @throws Exception
	 */
	private OrderExtvlBusiRequest updateOrderExtVl(OrderTreeBusiRequest orderTree,
			OrderBusiRequest order, Outer outer) throws Exception {
		// 纵转横表
		OrderExtvlBusiRequest req = this.getOrderExtByOrderId(orderTree.getOrder_id());
		
		req.setReserve2(outer.getReserve2());// 渠道类型
		req.setReserve0(outer.getReserve0());// 接收方系统标识
		req.setReserve1(outer.getReserve1());// 是否2G、3G升4G is_to4g Reserve1
		req.setSell_price(String.valueOf(outer.getItemList().get(0).getSell_price()));
		
		req.setOrder_provinc_code(outer.getOrder_provinc_code());
		req.setOrderacctype(outer.getOrderacctype());
		req.setPackage_sale(EcsOrderConsts.PACKAGE_SALE_NO);// 套包销售标志默认为0
		req.setOrder_origfee(outer.getOrder_origfee());// 订单应收总价
		
		req.setInvoice_type(String.valueOf(outer.getInvoice_type()));// 发票信息
		req.setChannel_type(outer.getExtMap().get("channel_type"));
		req.setChannelid(outer.getExtMap().get("channelId"));// 对应aop接口channelId
		req.setOut_office(outer.getExtMap().get("out_office"));// bss的操作点
		req.setOut_operator(outer.getExtMap().get("out_operator"));//// 操作员
		
		req.setPlan_title(outer.getItemList().get(0).getPlan_title());// 套餐名
		req.setBrand_name(outer.getItemList().get(0).getBrand_name());// 品牌名
		req.setGoodsname(outer.getExtMap().get("GoodsName"));
		
		// dingxiaotao 6.6新增
		req.setIs_pay(outer.getExtMap().get(AttrConsts.IS_PAY));
		req.setDistrict_code(outer.getExtMap().get(AttrConsts.DISTRICT_CODE));
		req.setResult_url(outer.getExtMap().get("result_url"));
		
		// 属性处理器前置属性设置
		req.setGuarantor(outer.getGuarantor());
		req.setBill_mail_type(outer.getBill_mail_type());
		req.setIs_write_card(EcsOrderConsts.IS_WRITE_CARD_NO);
		req.setReliefpres_flag(outer.getReliefpres_flag());
		req.setCustomertype(outer.getCustomerType());
		
		req.setOrder_disfee(outer.getOrder_disfee());
		req.setIs_liang(EcsOrderConsts.NO_DEFAULT_VALUE);
		req.setSyn_mode(outer.getExtMap().get("syn_mode"));
		req.setOpttype(outer.getExtMap().get("optType"));
		req.setModtype(outer.getExtMap().get("modType"));
		
		req.setMainnumber(outer.getExtMap().get("mainNumber"));
		req.setViceproductid(outer.getExtMap().get("viceProductId"));
		req.setOrdertype(outer.getExtMap().get("orderType"));
		req.setIs_order_master(outer.getExtMap().get("is_order_master"));
		req.setSeccardroletype(outer.getExtMap().get("secCardRoleType"));
		
		req.setProductmode(outer.getExtMap().get("productMode"));
		req.setChecktype(outer.getExtMap().get("checkType"));
		req.setDebutysn(outer.getExtMap().get("debutySn"));
		req.setDebutysn(outer.getExtMap().get("debutySn"));
		
		req.setIs_realname(outer.getExtMap().get("is_realname"));
		req.setSale_mod_type(outer.getExtMap().get("sale_mod_type"));
		req.setMarking_tag(outer.getExtMap().get("marking_tag"));
		
		req.setDevelopment_name(StringUtil.isEmpty(outer.getDevelopment_name()) ? outer.getExtMap().get("development_name") : outer.getDevelopment_name());
		//req.setDevelopment_name(outer.getDevelopment_name());
		//req.setDevelopment_code(outer.getDevelopment_code());
		req.setDevelopment_code(StringUtil.isEmpty(outer.getDevelopment_code()) ? outer.getExtMap().get("development_code") : outer.getDevelopment_code());
		req.setDevelopment_point_code(outer.getExtMap().get("development_point_code"));
		req.setDevelopment_point_name(outer.getExtMap().get("development_point_name"));
		req.setDevelopernumber(outer.getExtMap().get("developerNumber"));
		req.setDevelop_point_code_new(outer.getExtMap().get("develop_point_code_new"));
		req.setDevelop_code_new(outer.getExtMap().get("develop_code_new"));
		req.setDevelop_name_new(outer.getExtMap().get("develop_name_new"));
		
		req.setSource_type(outer.getExtMap().get("source_type"));
		req.setRegist_type(outer.getExtMap().get("regist_type"));
		req.setDeal_operator_name(outer.getExtMap().get("deal_operator_name"));
		req.setDeal_operator_num(outer.getExtMap().get("deal_operator_num"));
		req.setCouponbatchid(outer.getExtMap().get("couponbatchid"));
		
		req.setIs_examine_card(outer.getExtMap().get("is_examine_card"));
		req.setCust_id(outer.getExtMap().get("cust_id"));
		req.setAgent_district(outer.getExtMap().get("agent_district"));
		req.setAgent_city(outer.getExtMap().get("agent_city"));
		req.setAgent_name(outer.getExtMap().get("agent_name"));
		
		req.setAgent_code_dls(outer.getExtMap().get("agent_code"));
		req.setOss_operator(outer.getExtMap().get("oss_operator"));
		req.setBss_operator_name(outer.getExtMap().get("bss_operator_name"));
		req.setBss_operator(outer.getExtMap().get("bss_operator"));
		req.setIndustry_source(outer.getExtMap().get("industry_source"));
		
		req.setGroup_name(outer.getExtMap().get("group_name"));
		req.setGroup_code(outer.getExtMap().get("group_code"));
		req.setSeller_message(outer.getExtMap().get("seller_message"));
		req.setIccid(outer.getExtMap().get("ICCID"));
		req.setFund_type(outer.getExtMap().get("fund_type"));
		
		req.setEvdo_num(outer.getExtMap().get("evdo_num"));
		req.setUser_kind(outer.getExtMap().get("user_kind"));
		
		//add by sgf
		req.setMarket_user_id(outer.getExtMap().get("market_user_id"));
        req.setSeed_user_id(outer.getExtMap().get("seed_user_id"));
        req.setShare_svc_num(outer.getExtMap().get("share_svc_num"));
        req.setActivity_name(outer.getExtMap().get("activity_name"));
        req.setSale_mode(outer.getExtMap().get("sale_mode"));
        req.setObject_esn(outer.getExtMap().get("object_esn"));
        req.setObject_id(outer.getExtMap().get("object_id"));
        req.setSubs_id(outer.getExtMap().get("subs_id"));
        req.setService_num(outer.getExtMap().get("service_num"));
        req.setUser_to_account(outer.getExtMap().get("user_to_account"));
        
        //线程本地对象中设置OrderExtvl对象
        CommonData.getData().setOrderExtvlBusiRequest(req);
        
		return req;
	}
	
	/**
	 * 更新es_payment_logs
	 * @param orderTree
	 * @param order
	 * @param orderOuter
	 * @param outer
	 * @return
	 * @throws Exception
	 */
	private OrderPayBusiRequest updatePaymentInfo(OrderTreeBusiRequest orderTree,OrderBusiRequest order, 
			OrderOuter orderOuter, Outer outer) throws Exception {
		
		List<OrderPayBusiRequest> paymentList = orderTree.getOrderPayBusiRequests();
		// 支付信息
		OrderPayBusiRequest payment = paymentList.get(0);
		
		payment.setPaytype(0);
		// 这个需要根据支付方式进行判断为-1(货到付款)还是0(在线支付)
		if ("online".equals(orderOuter.getPayment_id())) {
			payment.setStatus(OrderStatus.PAY_STATUS_0);
		} else {
			payment.setStatus(OrderStatus.PAY_STATUS_NOT_CONFIRM);
		}
		
		if (OrderStatus.WP_CREATE_TYPE_6.equals(order.getCreate_type() + "")
				|| OrderStatus.WP_CREATE_TYPE_7.equals(order.getCreate_type() + "")) {
			payment.setType(OrderStatus.PAY_TYPE_2);
		} else {
			payment.setType(OrderStatus.PAY_TYPE_1);
		}
		
		if (EcsOrderConsts.PAY_METHOD_ZFB.equals(outer.getPay_method()))
			payment.setPay_type(EcsOrderConsts.PAY_TYPE_ZXZF);
		else
			payment.setPay_type(outer.getPaytype());
		
		payment.setUserid(order.getPay_user_id());
		payment.setPay_method(outer.getPay_method());
		payment.setMoney(order.getPaymoney());

		return payment;
	}
	
	/**
	 * 更新es_delivery
	 * @param orderTree
	 * @param order
	 * @param outer
	 * @return
	 * @throws Exception
	 */
	private OrderDeliveryBusiRequest updateDeliver(OrderTreeBusiRequest orderTree,
			OrderBusiRequest order, Outer outer) throws Exception {
		List<OrderDeliveryBusiRequest> list = orderTree.getOrderDeliveryBusiRequests();
		OrderDeliveryBusiRequest delivery = list.get(0);
		
		if (OrderStatus.WP_CREATE_TYPE_6.equals(order.getCreate_type() + "")
				|| OrderStatus.WP_CREATE_TYPE_7.equals(order.getCreate_type() + "")) {
			delivery.setType(2);
		} else {
			delivery.setType(1);
		}

		delivery.setShip_type(order.getShipping_type());
		delivery.setShip_name(order.getShip_name());
		// 后面属性处理器前移设置
		// delivery.setShip_addr(order.getShip_addr());
		// delivery.setShip_mobile(order.getShip_mobile());
		delivery.setShip_email(order.getShip_email());
		delivery.setShip_tel(order.getShip_tel());
		delivery.setShip_zip(order.getShip_zip());
		delivery.setShip_status(OrderStatus.DELIVERY_SHIP_STATUS_NOT_CONFIRM);
		delivery.setShip_num(0);
		
		delivery.setWeight(order.getWeight().longValue());
		delivery.setPrint_status(OrderStatus.DELIVERY_PRINT_STATUS_0);
		delivery.setUserid(order.getShip_user_id());
		delivery.setBatch_id(order.getBatch_id());
		delivery.setCreate_type(order.getCreate_type() + "");
		
		// 前置属性处理器设置默认值
		delivery.setNeed_receipt(EcsOrderConsts.NO_DEFAULT_VALUE);
		delivery.setN_shipping_amount(EcsOrderConsts.DEFAULT_FEE_ZERO);

		if(org.apache.commons.lang.StringUtils.isNotBlank(outer.getProvinc_code())
				&& org.apache.commons.lang.StringUtils.isNumeric(outer.getProvinc_code()))
			delivery.setProvince_id(Long.valueOf(outer.getProvinc_code()));
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(outer.getCity_code())
				&& org.apache.commons.lang.StringUtils.isNumeric(outer.getCity_code()))
			delivery.setCity_id(Long.valueOf(outer.getCity_code()));
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(outer.getArea_code())
				&& org.apache.commons.lang.StringUtils.isNumeric(outer.getArea_code()))
			delivery.setRegion_id(Long.valueOf(outer.getArea_code()));
		
		delivery.setShipping_time("NOLIMIT");
		delivery.setOut_house_id("-1");
		delivery.setPost_fee(outer.getPost_fee());
		delivery.setDelivery_type("0");
		
		// 前置属性处理器设置默认值
		delivery.setIs_protect(order.getIs_protect());
		delivery.setRegion(outer.getDistrict());
		delivery.setShip_mobile(outer.getReceiver_mobile());
		delivery.setShip_addr(order.getShip_addr());
		
		return delivery;
	}
	
	/**
	 * 更新数据库中的商品信息
	 * @param order_id
	 * @throws Exception
	 */
	private void clearDBGoodsItems(String order_id) throws Exception{
		String es_order_items_sql = "DELETE FROM es_order_items a WHERE a.order_id='"+order_id+"' ";
		String es_order_items_ext_sql = "DELETE FROM es_order_items_ext a WHERE a.order_id='"+order_id+"' ";
		String es_delivery_item_sql = "DELETE FROM es_delivery_item a WHERE a.order_id='"+order_id+"' ";
		String es_order_items_prod_sql = "DELETE FROM es_order_items_prod a WHERE a.order_id='"+order_id+"' ";
		String es_order_items_prod_ext_sql = "DELETE FROM es_order_items_prod_ext a WHERE a.order_id='"+order_id+"' ";
		
		this.baseDaoSupport.execute(es_order_items_sql);
		this.baseDaoSupport.execute(es_order_items_ext_sql);
		this.baseDaoSupport.execute(es_delivery_item_sql);
		this.baseDaoSupport.execute(es_order_items_prod_sql);
		this.baseDaoSupport.execute(es_order_items_prod_ext_sql);
	}
	
	public void updateOrderTree() throws Exception{
		//更新订单数据
		commonDataDao.updateNoCache();
		this.setOrderTreeToCache();
	}
	
	private String createSn(){
		String sql = "select S_ES_ORDER_SN.Nextval from dual";
		int sn = this.baseDaoSupport.queryForInt(sql);
		return df.format(sn);
	}
}
