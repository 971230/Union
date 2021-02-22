package zte.net.ecsord.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.pub.req.LogiCompCodeGetReq;
import zte.net.ecsord.params.pub.req.PostRegionGetReq;
import zte.net.ecsord.params.pub.req.RegionListReq;
import zte.net.ecsord.params.pub.resp.LogiCompCodeGetResp;
import zte.net.ecsord.params.pub.resp.PostRegionGetResp;
import zte.net.ecsord.params.pub.resp.RegionListResp;
import zte.net.ecsord.params.spec.req.ActivitySpecReq;
import zte.net.ecsord.params.spec.req.GoodsSpecReq;
import zte.net.ecsord.params.spec.req.GoodsTypeIdGetReq;
import zte.net.ecsord.params.spec.req.NumberSpecReq;
import zte.net.ecsord.params.spec.req.PCodeGetReq;
import zte.net.ecsord.params.spec.req.ProductSpecReq;
import zte.net.ecsord.params.spec.resp.ActivitySpecResp;
import zte.net.ecsord.params.spec.resp.GoodsSpecResp;
import zte.net.ecsord.params.spec.resp.GoodsTypeIdGetResp;
import zte.net.ecsord.params.spec.resp.NumberSpecResp;
import zte.net.ecsord.params.spec.resp.PCodeGetResp;
import zte.net.ecsord.params.spec.resp.ProductSpecResp;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;
import zte.params.brand.req.BrandListByTypeReq;
import zte.params.brand.req.BrandModelListByBrandReq;
import zte.params.brand.req.BrandModelListByModelCodeReq;
import zte.params.brand.resp.BrandListByTypeResp;
import zte.params.brand.resp.BrandModelListByBrandResp;
import zte.params.brand.resp.BrandModelListByModelCodeResp;
import zte.params.goods.req.GoodsBySkuReq;
import zte.params.goods.req.ProductInfoByNameReq;
import zte.params.goods.req.ProductsListReq;
import zte.params.goods.req.TerminalNumReq;
import zte.params.goods.resp.GoodsBySkuResp;
import zte.params.goods.resp.ProductInfoByNameResp;
import zte.params.goods.resp.ProductsListResp;
import zte.params.goods.resp.TerminalNumResp;
import zte.params.goodscats.req.CatListByTypeReq;
import zte.params.goodscats.resp.CatListByTypeResp;
import zte.params.goodstype.req.GoodsTypeListReq;
import zte.params.goodstype.resp.GoodsTypeListResp;
import zte.params.order.req.OrderItemListHisReq;
import zte.params.order.resp.OrderItemListHisResp;
import zte.params.process.req.UosFlowReq;
import zte.params.process.resp.UosFlowResp;
import zte.params.process.resp.UosNode;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Brand;
import com.ztesoft.net.mall.core.model.BrandModel;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class SpecUtils {
	
	public static final String HAS_GOODS_CACHE_FLAG = "HAS_GOODS_CACHE";//商品是否缓存
	protected static final String CORE_CACHE_TYPE = "";
	
	
	//100以下是保留，最大值不能超过10000
	protected static final int NAMESPACE = 100;
	
	public static boolean isGoodsHasCache(){
		INetCache cache = CacheFactory.getCacheByType(CORE_CACHE_TYPE);
		String str = (String)cache.get(NAMESPACE,HAS_GOODS_CACHE_FLAG);
		if(!StringUtil.isEmpty(str) && "yes".equals(str)){
			return true;
		}else{
			return false;
		}
	}
	
	public static void addHasGoodsCache(){
		INetCache cache = CacheFactory.getCacheByType(CORE_CACHE_TYPE);
		cache.set(NAMESPACE, HAS_GOODS_CACHE_FLAG, "yes");
	}
	
	/**
	 * 判断货品是否赠品
	 * @param goods_id 货品ID
	 * @return
	 */
	public static String isGift(String goods_id){
		GoodsTypeIdGetReq req = new GoodsTypeIdGetReq();
		req.setGoods_id(goods_id);
		ZteClient client = getDubboZteClient();
		GoodsTypeIdGetResp response = client.execute(req, GoodsTypeIdGetResp.class);
		String type_id = response.getType_id();
		String isGifts = EcsOrderConsts.DEFAULT_FEE_ZERO;
		if(Consts.PRODUCT_TYPE_LIPIN.equals(type_id))
			isGifts = EcsOrderConsts.DEFAULT_STR_ONE;
		return isGifts;
	}
	
	public static String isVirtualProduct(String prod_goods_id){
		GoodsTypeIdGetReq req = new GoodsTypeIdGetReq();
		req.setGoods_id(prod_goods_id);
		ZteClient client = getDubboZteClient();
		GoodsTypeIdGetResp response = client.execute(req, GoodsTypeIdGetResp.class);
		String type_id = response.getType_id();
		String is_virtual = AttrUtils.getInstance().getDictCodeValue(StypeConsts.IS_VIRTUAL, type_id);
		if(StringUtils.isEmpty(is_virtual))
			is_virtual = EcsOrderConsts.NO_DEFAULT_VALUE;
		return is_virtual;
	}
	
	/**
	 * 获取合约编码
	 * @param goods_id
	 * @return
	 */
	public static String getPCode(String goods_id){
		ZteClient client = getDubboZteClient();
		PCodeGetReq req = new PCodeGetReq();
		req.setGoods_id(goods_id);
		PCodeGetResp response = client.execute(req, PCodeGetResp.class);
		return response.getP_code();
	}
	
	public static Map getGoodsSpecMap(String order_id, String goods_id){
		//商品不为空，根据商品ID取商品规格信息，商品ID为空，根据订单ID得到商品ID后取商品规格信息
		if(StringUtils.isEmpty(goods_id))
			goods_id = getOrderItem(order_id);
		Map specs = getGoodSpec(goods_id);
		return specs;
	}
	public static Map getGoodsSpecMapHis(String order_id, String goods_id){
		//商品不为空，根据商品ID取商品规格信息，商品ID为空，根据订单ID得到商品ID后取商品规格信息
		if(StringUtils.isEmpty(goods_id))
			goods_id = getOrderItemHis(order_id);
		Map specs = getGoodSpec(goods_id);
		return specs;
	}
	
	/**
	 * 根据key取规格信息，key常量定义见SpecConsts.java
	 * @param goods_id
	 * @param key
	 * @return
	 */
	public static String getGoodsSpec(String order_id,String goods_id,String field_name){
		if(StringUtils.isEmpty(field_name))
			return EcsOrderConsts.EMPTY_STR_VALUE;
		Map specs = getGoodsSpecMap(order_id, goods_id);
		String value = EcsOrderConsts.EMPTY_STR_VALUE;
		if(specs!=null && !specs.isEmpty())
			value = specs.get(field_name)==null?EcsOrderConsts.EMPTY_STR_VALUE:specs.get(field_name).toString() ;
		return value;
	}
	/**
	 * 历史单
	 * 根据key取规格信息，key常量定义见SpecConsts.java
	 * @param goods_id
	 * @param key
	 * @return
	 */
	public static String getGoodsSpecHis(String order_id,String goods_id,String field_name){
		if(StringUtils.isEmpty(field_name))
			return EcsOrderConsts.EMPTY_STR_VALUE;
		//如果订单ID，商品ID都为空，返回空
		if(StringUtils.isEmpty(order_id) && StringUtils.isEmpty(goods_id))
			return EcsOrderConsts.EMPTY_STR_VALUE;
		//商品不为空，根据商品ID取商品规格信息，商品ID为空，根据订单ID得到商品ID后取商品规格信息
		if(StringUtils.isEmpty(goods_id))
			goods_id = getOrderItemHis(order_id);
		Map specs = getGoodsSpecMapHis(order_id, goods_id);
		String value = EcsOrderConsts.EMPTY_STR_VALUE;
		if(specs!=null && !specs.isEmpty())
			value = specs.get(field_name)==null?EcsOrderConsts.EMPTY_STR_VALUE:specs.get(field_name).toString() ;
		return value;
	}
	
	public static Map getProductSpecMap(String order_id, String type_id){
		Goods product = getProduct(order_id,type_id);
		if(product == null)
			return new HashMap();
		Map specMap = getProductSpecMap(product);
		return specMap;
	}

	public static Map getProductSpecMap(Goods product){
		if(product == null) return new HashMap();
		Map specMap = new HashMap();
		ProductSpecReq req = new ProductSpecReq();
		req.setProduct(product);
		ZteClient client = getDubboZteClient();
		ProductSpecResp response = client.execute(req, ProductSpecResp.class);
		return response.getProductSpecMap();
	}
	
	public synchronized static void initGoodsCache(){
		try{
			if(!SpecUtils.isGoodsHasCache()){
				logger.info("==============初始化商品数据===========================");
				IGoodsManager gm = SpringContextHolder.getBean("goodsManager");
				gm.initGoodsCache();
				SpecUtils.addHasGoodsCache();
			}
		}catch( Exception e){
		}
	}
	
	/**
	 * 根据key取规格信息，key常量定义见SpecConsts.java
	 * @param prod_goods_id
	 * @param key
	 * @return
	 */
	public static String getProductSpec(String order_id, String type_id, String prod_goods_id, String field_name){
		initGoodsCache();
		if(StringUtils.isEmpty(field_name))
			return EcsOrderConsts.EMPTY_STR_VALUE;
		Goods product = getProduct(order_id,type_id);
		if(product == null)
			return EcsOrderConsts.EMPTY_STR_VALUE;
		Map specs = getProductSpecMap(product);
		String value = EcsOrderConsts.EMPTY_STR_VALUE;
		if(specs!=null && !specs.isEmpty())
			value = specs.get(field_name)==null ? EcsOrderConsts.EMPTY_STR_VALUE : specs.get(field_name).toString();
		return value;
	}
	/**
	 * 历史单
	 * 根据key取规格信息，key常量定义见SpecConsts.java
	 * @param prod_goods_id
	 * @param key
	 * @return
	 */
	public static String getProductSpecHis(String order_id, String type_id, String prod_goods_id, String field_name){
		if(StringUtils.isEmpty(field_name))
			return EcsOrderConsts.EMPTY_STR_VALUE;
		Goods product = getOrderProductHis(order_id,type_id);
		Map specs = getProductSpecMap(product);
		String value = EcsOrderConsts.EMPTY_STR_VALUE;
		if(specs!=null && !specs.isEmpty())
			value = specs.get(field_name)==null ? EcsOrderConsts.EMPTY_STR_VALUE : specs.get(field_name).toString();
		return value;
	}
	
	/**
	 * 根据key取规格信息，key常量定义见SpecConsts.java
	 * @param goods_id
	 * @param key
	 * @return
	 */
	public static String getProductSpec(String order_id, String type_id, String field_name){
		if(StringUtils.isEmpty(field_name))return EcsOrderConsts.EMPTY_STR_VALUE;
		if(StringUtils.isEmpty(order_id))return EcsOrderConsts.EMPTY_STR_VALUE;
		Goods product = getProduct(order_id, type_id);//获取制定类型货品
		if(product == null)return EcsOrderConsts.EMPTY_STR_VALUE;
		Map specs = getProductSpecMap(product);
		String value = EcsOrderConsts.EMPTY_STR_VALUE;
		if(specs!=null && !specs.isEmpty())
			value = specs.get(field_name)==null ? EcsOrderConsts.EMPTY_STR_VALUE : specs.get(field_name).toString();
		return value;
	}
	/**
	 * 历史单
	 * 根据order_id,type_id获取订单货品单，如果取到多个货品，返回“-1”，如果没有取到货品，返回“0”
	 * @param goods_id 订单ID
	 * @param type_id 类型ID，见SpecConsts.java
	 * @return
	 */
	private static Goods getOrderProductHis(String order_id, String type_id){
		String goods_id = getOrderItemHis(order_id);
		Goods product = getProductHis(order_id, type_id);
		return product;
	}
	private static Goods getProductHis(String order_id, String type_id){
		String prod_goods_id = null;
		Goods product = null;
		List<Goods> products = getAllOrderProductsHis(order_id);
		if(products!=null && products.size()>0){
			for(Goods prod :  products){
				if(type_id.equals(prod.getType_id())){
					product = prod;
					break ;
				}
			}
		}
		return product;
	}
	private static Goods getProduct(String order_id, String type_id){
		String prod_goods_id = null;
		Goods product = null;
		List<Goods> products = getAllOrderProducts(order_id);
		if(products!=null && products.size()>0){
			for(Goods prod :  products){
				if(type_id.equals(prod.getType_id())){
					product = prod;
					break ;
				}
			}
		}
		return product;
	}
	
	/**
	 * 查询订单商品goods_id
	 * @param order_id
	 * @return
	 */
	private static String getOrderItem(String order_id){	
		List<OrderItemBusiRequest> items = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests();
		String goods_id = null;
		if(items!=null && items.size()>0){
			OrderItemBusiRequest item = items.get(0);
			item.getOrderItemProdBusiRequests();
			goods_id = item.getGoods_id();
		}
		return goods_id;
	}
	/**
	 * 历史表
	 * 查询订单商品goods_id
	 * @param order_id
	 * @return
	 */
	private static String getOrderItemHis(String order_id){
		OrderItemListHisReq req = new OrderItemListHisReq();
		req.setOrder_id(order_id);
		
		ZteClient client = getDubboZteClient();
		OrderItemListHisResp response = client.execute(req, OrderItemListHisResp.class);
		List<Map> items = response.getOrderItems();
		Map item = (items==null || items.size()==0) ? new HashMap() : items.get(0);
		String goods_id = item.get("goods_id")==null ? null : item.get("goods_id").toString();
		return goods_id;
	}
	
	/**
	 * 获取商品规格信息
	 * @param goods_id
	 * @return
	 */
	public static Map getGoodSpec(String goods_id){
		ZteClient client = getDubboZteClient();
		GoodsSpecReq req = new GoodsSpecReq();
		req.setGoods_id(goods_id);
		
		GoodsSpecResp resp = client.execute(req, GoodsSpecResp.class);
		Map specs = null;
		if(EcsOrderConsts.DEFAULT_FEE_ZERO.equals(resp.getError_code()))
			specs = resp.getSpecs();
		return specs;
	}
	
	public static String getActivitySpec(String activity_code,String field_name){
		if(StringUtils.isEmpty(activity_code)) return EcsOrderConsts.EMPTY_STR_VALUE;
		ActivitySpecReq req = new ActivitySpecReq();
		req.setActivity_code(activity_code);
		
		ZteClient client = getDubboZteClient();
		ActivitySpecResp response = client.execute(req, ActivitySpecResp.class);
		Map specs = response.getSpecs();
		String value = (specs==null || specs.get(field_name)==null)?EcsOrderConsts.EMPTY_STR_VALUE:specs.get(field_name).toString();
		return value;
	}
	
	/**
	 * 判断是否总部合约
	 * @param order_from
	 * @param goods_id
	 * @return
	 */
	public static String isGroupContract(String order_from, String goods_id){
		if(StringUtils.isEmpty(order_from) || StringUtils.isEmpty(goods_id))
			return null;
		String p_code_exists = getPCode(goods_id);
		String is_group_contract = EcsOrderConsts.NO_DEFAULT_VALUE;
		if(StringUtils.isEmpty(p_code_exists)){
			//如果合约编码不为空且订单来源是总部商城，是总部合约
			if(AttrUtils.getInstance().isZbOrder(order_from)){
				is_group_contract = EcsOrderConsts.IS_DEFAULT_VALUE;
			}
			else{
				is_group_contract = EcsOrderConsts.NO_DEFAULT_VALUE;
			}
		}
		else{
			is_group_contract = EcsOrderConsts.IS_DEFAULT_VALUE;
		}
		return is_group_contract;
	}
	
	/**
	 * 判断订单中是否有指定类型的货品
	 * @param order_id
	 * @param type_id
	 * @return
	 */
	public static String hasTypeOfProduct(String order_id,String type_id){
		String has_terminal = EcsOrderConsts.NO_DEFAULT_VALUE;
		List<Goods> products = getAllOrderProducts(order_id);
		if(products!=null && products.size()>0){
			for(Goods product : products){
				if(type_id.equals(product.getType_id())){
					has_terminal = EcsOrderConsts.IS_DEFAULT_VALUE;
					break;
				}
			}
		}
		return has_terminal;
	}
	/**
	 * 历史单
	 * 判断订单中是否有指定类型的货品
	 * @param order_id
	 * @param type_id
	 * @return
	 */
	public static String hasTypeOfProductHis(String order_id,String type_id){
		String has_terminal = EcsOrderConsts.DEFAULT_STR_SERO;
		String goods_id = getOrderItemHis(order_id);
		if(!StringUtils.isEmpty(goods_id)){
			ProductsListReq req = new ProductsListReq();
			req.setGoods_id(goods_id);
			ZteClient client = getDubboZteClient();
			//rop调用查询商品关联的货品
			ProductsListResp response = client.execute(req, ProductsListResp.class);
			List<Goods> products = response.getProducts();
			if(products!=null && products.size()>0){
				for(Goods product : products){
					if(type_id.equals(product.getType_id())){
						has_terminal = EcsOrderConsts.DEFAULT_STR_ONE;
						break;
					}
				}
			}
		}
		return has_terminal;
	}
	/**
	 * 判断商品中是否具有实物货品
	 * @param goods_id 商品ID
	 * @return
	 */
	public static String hasEntityProduct(String order_id,String goods_type){
		String has_entity_prod = EcsOrderConsts.NO_DEFAULT_VALUE;
		//合约机、裸机、配件、上网卡有实物货品
		String is_physical_order = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.IS_PHYSICAL_ORDER, goods_type);
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_physical_order))
			has_entity_prod = EcsOrderConsts.IS_DEFAULT_VALUE;
		return has_entity_prod;
	}
	
	/**
	 * 获取订单全部货品
	 * @param order_id
	 * @return
	 */
	public static List<Goods> getAllOrderProducts(String order_id){
		List<Goods> products = new ArrayList<Goods>();
		//查询订单商品
		String goods_id = getOrderItem(order_id);
		if(!StringUtils.isEmpty(goods_id)){
			ProductsListReq req = new ProductsListReq();
			req.setGoods_id(goods_id);
			ZteClient client = getDubboZteClient();
			ProductsListResp response = client.execute(req, ProductsListResp.class);
			products = response.getProducts();
		}
		return products;
	}
	/**
	 * 获取订单全部货品-历史单
	 * @param order_id
	 * @return
	 */
	public static List<Goods> getAllOrderProductsHis(String order_id){
		List<Goods> products = new ArrayList<Goods>();
		//查询订单商品
		String goods_id = getOrderItemHis(order_id);
		if(!StringUtils.isEmpty(goods_id)){
			ProductsListReq req = new ProductsListReq();
			req.setGoods_id(goods_id);
			ZteClient client = getDubboZteClient();
			ProductsListResp response = client.execute(req, ProductsListResp.class);
			products = response.getProducts();
		}
		return products;
	}
	
	/**
	 * 取商品中的实物货品
	 * @param order_id 订单ID
	 * @param goods_id 商品ID
	 * @return
	 */
	public static List<Goods> getEntityProducts(String order_id){
		List<Goods> entityProducts = new ArrayList<Goods>();
		List<Goods> products = getAllOrderProducts(order_id);
		if(products!=null && products.size()>0){
			for(Goods product : products){
				String type_id = product.getType_id();
				String is_physical = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.IS_PHYSICAL, type_id);
				if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_physical))
					entityProducts.add(product);
			}
		}
		return entityProducts;
		
	}
	
	/**
	 * 判断是否合约机、号卡、上网卡，是返回true，否则返回false
	 * @param order_id 订单ID
	 * @param goods_id 商品ID
	 * @return true/false
	 */
	public static boolean isHyjHkSwk(String order_id, String goods_id){
		//订单ID，商品ID都为空，直接返回false
		if(StringUtils.isEmpty(goods_id) && StringUtils.isEmpty(order_id))
			return false;
		//如果商品ID为空，从订单树种获取
		if(StringUtils.isEmpty(goods_id) && !StringUtils.isEmpty(order_id)){
			goods_id = getOrderItem(order_id);
		}
		//商品ID为空，返回false
		if(StringUtils.isEmpty(goods_id))
			return false;
		GoodsTypeIdGetReq req = new GoodsTypeIdGetReq();
		req.setGoods_id(goods_id);
		ZteClient client = getDubboZteClient();
		GoodsTypeIdGetResp response = client.execute(req, GoodsTypeIdGetResp.class);
		
		String type_id = response.getType_id();
		if(Consts.GOODS_TYPE_NUM_CARD.equals(type_id) || Consts.GOODS_TYPE_WIFI_CARD.equals(type_id) || Consts.GOODS_TYPE_CONTRACT_MACHINE.equals(type_id))
			return true;
		else
			return false;
	}
	
	/**
	 * 返回ActiveNo访问流水:总部订单的访问流水前缀EM；非总部的访问流水前缀P51
	 * @param isZbOrder
	 * @return
	 */
	public static String getActiveNo(boolean isZbOrder){
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
		int year = c.get(Calendar.YEAR); 
		int month = c.get(Calendar.MONTH); 
		int date = c.get(Calendar.DATE); 
		int hour = c.get(Calendar.HOUR_OF_DAY); 
		int minute = c.get(Calendar.MINUTE); 
		int second = c.get(Calendar.SECOND);
		String sessionId =null;
		if(isZbOrder){
			sessionId = "EM" + year  + "" + month + "" + date+ "" + hour + "" + minute + ""+ second+Math.round(Math.random()*8999+1000);
		}
		else{
			sessionId = "P51" + year  + "" + month + "" + date+ "" + hour + "" + minute + ""+ second+Math.round(Math.random()*8999+1000);
		}
		return sessionId;
	}
	
	/**
	 * 返回定制流程
	 * @param order_id
	 * @return
	 */
	public static String getOrderFlow(String order_id){

		String orderFlow = EcsOrderConsts.EMPTY_STR_VALUE;
		String flow_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_id();
		UosFlowReq uosFlowReq = new UosFlowReq();
		uosFlowReq.setProcessId(flow_id);
		ZteClient client = ClientFactory
				.getZteDubboClient(ManagerUtils.getSourceFrom());
		UosFlowResp uosFlowResp = client.execute(uosFlowReq, UosFlowResp.class);
		
		//调用总部发货流程跳过方案，自动跳过，则不需要送H环节到总部，add by wui
		TacheFact fact =new TacheFact();
		BusiCompRequest busi = new BusiCompRequest();
		fact.setOrder_id(order_id);
		busi.setOrder_id(order_id);
		fact.setRequest(busi);
		PlanRuleTreeExeReq planReq = new PlanRuleTreeExeReq();
		planReq.setFact(fact);
		planReq.setPlan_id(EcsOrderConsts.TRACE_CODE_H_PLAN_ID);
		planReq.setDeleteLogs(true);//同时删除日志
		PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(planReq);
		boolean isExec =planResp.isRuleExecute(); //匹配跳过发货环节成功，则送总部不需要送H环节
		
		
		if(uosFlowResp!=null){
			List<UosNode> nodes = uosFlowResp.getNodes();
			if(null != nodes && !nodes.isEmpty()){
				for(int i = 0; i < nodes.size(); i++){
					//跳过
					UosNode node = nodes.get(i);
					String tacheCode = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.ZB_FLOW_NODE, node.getTacheCode());
					if(EcsOrderConsts.TRACE_CODE_H.equals(tacheCode) && isExec)
						continue;
					if(null != tacheCode && !EcsOrderConsts.EMPTY_STR_VALUE.equals(tacheCode)){
						if(orderFlow.equals(EcsOrderConsts.EMPTY_STR_VALUE)){
							orderFlow += tacheCode;
						}else{
							orderFlow += "-" + tacheCode;
						}
					}
				}
			}
		}
		return orderFlow;
	}
	/**
	 * 根据sku获取商品基本信息
	 * @param sku
	 * @return
	 */
	public static Goods getGoodsBySku(String sku){
		if(StringUtils.isEmpty(sku)) return null;
		ZteClient client = getDubboZteClient();
		GoodsBySkuReq req = new GoodsBySkuReq();
		req.setSku(sku);
		req.setSource_from(ManagerUtils.getSourceFrom());
		
		GoodsBySkuResp resp = client.execute(req, GoodsBySkuResp.class);
		Goods goods = null;
		if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(resp.getError_code()))
			goods = resp.getGoods();
		return goods;
	}
	
	public static String getNumberSku(String order_id,String goods_type,String specification_code,String sim_type,String white_card_type){
		String sku = null;
		if(EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods_type) && StringUtils.isNotEmpty(specification_code)){
			List<BrandModel> brandModels = getBrandModelByModel(specification_code);
			BrandModel brandModel = (brandModels!=null && brandModels.size()>0) ? brandModels.get(0):new BrandModel();
			sku = brandModel.getMachine_code();
		}
		else if(EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(goods_type) && EcsOrderConsts.SIM_TYPE_BK.equals(sim_type)){
			String card_name = AttrUtils.getInstance().getOtherDictCodeValueDesc(StypeConsts.BAI_CARD_SKU, white_card_type);
			Map<String, String> product = getProductByName(card_name);
			sku = product==null?EcsOrderConsts.EMPTY_STR_VALUE : product.get("skua");
		}
		else if(EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(goods_type) && EcsOrderConsts.SIM_TYPE_CK.equals(sim_type)){
			String card_name = AttrUtils.getInstance().getOtherDictCodeValueDesc(StypeConsts.CHENG_CARD_SKU, white_card_type);
			Map<String, String> product = getProductByName(card_name);
			sku = product==null?EcsOrderConsts.EMPTY_STR_VALUE : product.get("skua");
		}
		else if(EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(goods_type)){
			sku = CommonDataFactory.getInstance().getGoodSpec(order_id, null, SpecConsts.NETCARD_TYPE);
		}
		if(StringUtils.isEmpty(sku)){
			sku = EcsOrderConsts.DEFAULT_NUMBER_SKU;
		}
		return sku;
	}
	
	public static List<GoodsType> getGoodsTypeList(String type){
		GoodsTypeListReq req = new GoodsTypeListReq();
		req.setType(type);
		
		ZteClient client = getDubboZteClient();
		GoodsTypeListResp response = client.execute(req, GoodsTypeListResp.class);
		List<GoodsType> typeList = response.getTypeList();
		return typeList;
	}
	
	public static List<Brand> getBrandByTypeId(String type_id){
		BrandListByTypeReq req = new BrandListByTypeReq();
		req.setType_id(type_id);
		
		ZteClient client = getDubboZteClient();
		BrandListByTypeResp response = client.execute(req, BrandListByTypeResp.class);
		List<Brand> brands = response.getBrandList();
		return brands;
	}
	
	public static List<BrandModel> getModelByBrandId(String brand_id){
		BrandModelListByBrandReq req = new BrandModelListByBrandReq();
		req.setBrand_id(brand_id);
		
		ZteClient client = getDubboZteClient();
		BrandModelListByBrandResp response = client.execute(req, BrandModelListByBrandResp.class);
		List<BrandModel> models = response.getModelList();
		return models;
	}
	
	public static List<Cat> getCatsByTypeId(String type_id){
		CatListByTypeReq req = new CatListByTypeReq();
		req.setType_id(type_id);
		
		ZteClient client = getDubboZteClient();
		CatListByTypeResp response = client.execute(req, CatListByTypeResp.class);
		List<Cat> cats = response.getCatList();
		return cats;
	}
	
	public static Map getProductByName(String name){
		ZteClient client = getDubboZteClient();
		ProductInfoByNameReq req = new ProductInfoByNameReq();
		req.setProduct_name(name);
		
		ProductInfoByNameResp resp = client.execute(req, ProductInfoByNameResp.class);
		Map map = null;
		if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(resp.getError_code()))
			map = resp.getData();
		return map;
	}
	
	public static String getNumberSpec(String number, String field_name){
		NumberSpecReq req = new NumberSpecReq();
		req.setDn_no(number);
		
		ZteClient client = getDubboZteClient();
		NumberSpecResp response = client.execute(req, NumberSpecResp.class);
		Map numero = response.getNumeroMap();
		String value = numero==null ||numero.get(field_name)==null? EcsOrderConsts.EMPTY_STR_VALUE : numero.get(field_name).toString();
		return value;
	}
	
	/**
	 * 获取es_regions区域信息列表
	 * @return
	 */
	public static List<Map> getRegions(){
		ZteClient client = getDubboZteClient();
		RegionListReq req = new RegionListReq();
		RegionListResp response = client.execute(req, RegionListResp.class);
		List<Map> regions = response.getRegionList();
		return regions;
	}
	
	public static String getLogiCompanyCode(String logi_company_id){
		LogiCompCodeGetReq req = new LogiCompCodeGetReq();
		req.setPost_id(logi_company_id);
		ZteClient client = getDubboZteClient();
		LogiCompCodeGetResp response = client.execute(req, LogiCompCodeGetResp.class);
		String logi_company_code = response.getLogi_company_code();
		return logi_company_code==null?EcsOrderConsts.EMPTY_STR_VALUE:logi_company_code;
	}
	
	/**
	 * 取本地网编码
	 * @param order_city_code 订单归属地市
	 * @return
	 */
	public static String getLanCode(String order_city_code){
		String lan_code = null;
		List<Map> regions = getRegions();//取区域信息列表
		if(regions != null && regions.size()>0){
			for(Map region : regions){
				String region_id = (String) region.get("region_id");
				if(order_city_code.equals(region_id)){//取订单归属地市对应的本地网编码lan_code
					lan_code = region.get("lan_code")==null?EcsOrderConsts.EMPTY_STR_VALUE:region.get("lan_code").toString();
					break ;
				}
			}
		}
		return lan_code;
	}
	
	public static String getTypeIdByTypeCode(String type_code){
		if(StringUtils.isEmpty(type_code)) return null;
		String type_id = null;
		List<GoodsType> types = getGoodsTypeList(EcsOrderConsts.ECS_QUERY_TYPE_PRODUCT);
		if(types!=null && types.size()>0){
			for(GoodsType type : types){
				if(type_code.equals(type.getType_code())){
					type_id = type.getType_id();
					break ;
				}
			}
		}
		return type_id;
	}
	
	/**
	 * 取本地网名称
	 * @param order_city_code 订单归属地市
	 * @return
	 */
	public static String getLocalName(String order_city_code){
		String local_name = null;
		List<Map> regions = getRegions();//取区域信息列表
		if(regions != null && regions.size()>0){
			for(Map region : regions){
				String region_id = (String) region.get("region_id");
				if(order_city_code.equals(region_id)){//取订单归属地市对应的本地网编码lan_code
					local_name = region.get("local_name")==null?EcsOrderConsts.EMPTY_STR_VALUE:region.get("local_name").toString();
					break ;
				}
			}
		}
		return local_name;
	}
	
	public static String getTerminalNum(String order_id){
		String sn = null;
		String terminal_num = null;
		String type_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		if(EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(type_id)){
			sn = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10003, SpecConsts.GOODS_SN);
		}
		else if(EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(type_id) || EcsOrderConsts.GOODS_TYPE_PHONE.equals(type_id)){
			sn = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, SpecConsts.GOODS_SN);
		}
		if(!StringUtils.isEmpty(sn)){
			ZteClient client = getDubboZteClient();
			TerminalNumReq req = new TerminalNumReq();
			req.setSn(sn);
			TerminalNumResp resp = client.execute(req, TerminalNumResp.class);
			terminal_num = resp.getTerminal_no();
		}
		return terminal_num;
	}
	
	/**
	 * 取寄件方信息
	 * @param order_city_code 订单归属地市
	 * @return
	 */
	public static Map getPostRegion(String order_city_code){
		Map postRegion = new HashMap();
		String lan_code = getLanCode(order_city_code); //取订单归属地市对应的本地网编码
		if(!StringUtils.isEmpty(lan_code)){
			ZteClient client = getDubboZteClient();
			//根据lan_code，从es_post_region表取寄件方信息HashMap
			PostRegionGetReq req = new PostRegionGetReq();
			req.setLan_code(lan_code);
			PostRegionGetResp response = client.execute(req, PostRegionGetResp.class);
			postRegion = response.getPostRegion();
		}
		return postRegion;
	}
	
	private static List<BrandModel> getBrandModelByModel(String model_code){
		ZteClient client = getDubboZteClient();
		BrandModelListByModelCodeReq req = new BrandModelListByModelCodeReq();
		req.setModel_code(model_code);
		
		BrandModelListByModelCodeResp response = client.execute(req, BrandModelListByModelCodeResp.class);
		return response.getModelList();
	}
	
	public static String getAgentFee(String order_id){
		String fee = "0";
		//货品小类
		String prod_cat_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getProd_cat_id();
		if(SpecConsts.CAT_ID_690307000.equals(prod_cat_id)){			//合约惠机
//			List<AttrFeeInfoBusiRequest> feeList = CommonDataFactory.getInstance().getOrderTree(order_id).getFeeInfoBusiRequests();
//			if(null != feeList && feeList.size() > 0){
//				int total_fee = 0;
//				for(int i = 0; i < feeList.size(); i++){			// 合约预存款 + 多交预存款 + 靓号预存款
//					AttrFeeInfoBusiRequest feeInfo = feeList.get(i);
//					if(null != feeInfo && null != feeInfo.getN_fee_num()){
//						if(SpecConsts.FEE_DETAIL_2002.equals(feeInfo.getFee_item_code())
//								|| SpecConsts.FEE_DETAIL_4001.equals(feeInfo.getFee_item_code()) 
//								|| SpecConsts.FEE_DETAIL_2001.equals(feeInfo.getFee_item_code())){
//							Double feeD = 0.0;
//							if(null != feeInfo.getN_fee_num())
//								feeD = feeInfo.getN_fee_num() * 100;
//							total_fee += feeD.intValue();
//						}
//					}
//				}
//				fee = total_fee + "" ;
//			}
//			if(StringUtils.isEmpty(fee))
//				fee = "0";
//			return fee;
		}
		//商品大类
		String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, null, SpecConsts.TYPE_ID);
		if(SpecConsts.TYPE_ID_20000.equals(type_id) 
				|| SpecConsts.TYPE_ID_20001.equals(type_id)){			//号卡、上网卡,(套包总价)
			String payFee = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_REAL_FEE);//订单实收总价
			if(!StringUtils.isEmpty(payFee)){
				fee = Double.valueOf(payFee).intValue() * 100 + "";			//将元转为分
			}else {
				fee = "0";
			}
			return fee;
		}
		return fee;
	}
	
	public static boolean matchSendZbOption(String card_time,String pay_type,String goods_type,String sim_type,
			String society_flag,String is_old,String hy_cat_id,String net_type,List<Map> configs){
		boolean flag=false;
		for(int i=0;i<configs.size();i++){
			Map config = configs.get(i);
			String card_time_c = getStrValue(config, "card_time");
			String pay_type_c = getStrValue(config, "pay_type");
			String goods_type_c = getStrValue(config, "prod_offer_type");
			String sim_type_c = getStrValue(config, "sim_type");
			String society_flag_c = getStrValue(config, "society_flag");
			String user_flag = getStrValue(config, "user_flag");
			String package_type = getStrValue(config, "package_type");
			String net_type_c = getStrValue(config, "net_type");
			if(StringUtils.isNotEmpty(card_time_c)){
				if(card_time_c.trim().equals(card_time)){
					flag = true;
				}
				else{
					flag = false;
					continue;
				}
			}
			if(StringUtils.isNotEmpty(pay_type_c)){
				if(pay_type_c.trim().equals(pay_type)){
					flag = true;
				}
				else{
					flag = false;
					continue;
				}
			}
			if(StringUtils.isNotEmpty(goods_type_c)){
				if(goods_type_c.trim().equals(goods_type)){
					flag = true;
				}
				else{
					flag = false;
					continue;
				}
			}
			if(StringUtils.isNotEmpty(sim_type_c)){
				if(sim_type_c.trim().equals(sim_type)){
					flag = true;
				}
				else{
					flag = false;
					continue;
				}
			}
			if(StringUtils.isNotEmpty(society_flag_c)){
				if(society_flag_c.trim().equals(society_flag)){
					flag = true;
				}
				else{
					flag = false;
					continue;
				}
			}
			if(StringUtils.isNotEmpty(user_flag)){
				if(user_flag.trim().equals(is_old)){
					flag = true;
				}
				else{
					flag = false;
					continue;
				}
			}
			if(StringUtils.isNotEmpty(package_type)){
				if(package_type.trim().equals(hy_cat_id)){
					flag = true;
				}
				else{
					flag = false;
					continue;
				}
			}
			if(StringUtils.isNotEmpty(net_type_c)){
				if(net_type_c.trim().equals(net_type)){
					flag = true;
				}
				else{
					flag = false;
					continue;
				}
			}
			if(flag){
				return flag;
			}
		}
		return flag;
	}
	
	public static ZteClient getDubboZteClient(){
    	return ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
    }
	
	public static  String getStrValue(Map m , String name) {
		if(m != null){
			Object t = m.get(name);
			if (t != null && t instanceof String) return ((String) t).trim();
		}
		return "";
	}
}
