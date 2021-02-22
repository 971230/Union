package com.ztesoft.net.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.warehouse.req.WareHouseMatchReq;
import zte.net.ecsord.params.warehouse.resp.WareHouseMatchResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.WareHouseAttrCfg;
import com.ztesoft.net.mall.core.model.WareHouseAttrCfgList;
import com.ztesoft.net.mall.core.model.Warehouse;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.WareHouseVO;
import com.ztesoft.net.service.IWareHouseManager;

import commons.CommonTools;
import consts.ConstsCore;

public class WareHouseManager extends BaseSupport implements IWareHouseManager{
	private static Logger logger = Logger.getLogger(WareHouseManager.class);
	@Override
	public WareHouseMatchResp cityCodeFirst(WareHouseMatchReq req) throws ApiBusiException{

		// TODO Auto-generated method stub
		List<Object> list = new ArrayList<Object>();
		
		WareHouseMatchResp resp = new WareHouseMatchResp();
		String order_id = req.getOrder_id();
		OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderDeliveryBusiRequest delivery = ordertree.getOrderDeliveryBusiRequests().get(0);
		String outHouseID = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_HOUSE_ID);
		//获取订单下手机货品的产品编码
		//目前只针对手机货品进行仓库匹配，后续增加实物礼品等匹配规则
		String product_id = getProductIdByOrderId(order_id);
		// product_id = "201403215011461";
		if (StringUtil.isEmpty(product_id)) {
			// 如果没有手机货品则记录订单为不需要匹配仓库
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.IS_MATCH_WAREHOUSE },
					new String[] { EcsOrderConsts.NO_DEFAULT_VALUE });
			resp.setError_code("0");
			resp.setError_msg("无需匹配仓库");
			resp.setWareHouseList(list);
			return resp;
		} else {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.IS_MATCH_WAREHOUSE },
					new String[] { "1" });
		}
		String plat_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PLAT_TYPE);
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
		if(EcsOrderConsts.PLAT_TYPE_10008.equals(plat_type)&&!EcsOrderConsts.ORDER_FROM_100312.equals(order_from)){
			order_from = EcsOrderConsts.ORDER_FROM_10031;
		}
		List vList = new ArrayList();
		if(StringUtil.isEmpty(outHouseID)||"-1".equals(outHouseID)){
			//如果商城没有传物理仓编码，则根据商城编码和产品编码匹配到虚拟仓，再通过规则过滤找到逻辑仓和物理仓，其中如果订单来源系统是沃云购则统一为沃云购虚拟仓
			vList = queryHouseInfo(product_id, order_from);
			
		}else{
			//如果商城有传物理仓编码，则根据商城编码 产品编码和物理仓编码匹配到逻辑仓和虚拟仓，其中如果订单来源系统是沃云购则统一为沃云购虚拟仓
			//先通过house_code查询仓库表获取物理仓的house_id
			String p_house_id = queryPHouseId(outHouseID);
			if(StringUtil.isEmpty(p_house_id)){
				delivery.setHouse_id("-1");
				delivery.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				delivery.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				delivery.store();
				CommonTools.addBusiError("-1", "未找到仓库编码："+outHouseID+"对应的仓库数据");
			}
			vList = queryHouseInfo(product_id, order_from, p_house_id);
		}
		
		if(vList.size()==0){
			delivery.setHouse_id("-1");
			delivery.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			delivery.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			delivery.store();
			CommonTools.addBusiError("-1", "未找到产品："+product_id+" 对应的仓库数据");
		}
		
		String v_house_id = "";
		String l_house_id = "";
		String city = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);
		
		List<Object> resultList = new ArrayList<Object>();
		for(int i=0;i<vList.size();i++){
			Map vMap = (HashMap)vList.get(i);
			l_house_id = vMap.get("house_id_out").toString();
			v_house_id = vMap.get("house_id_in").toString();
			//查询逻辑仓的配送范围，如果配送范围为全省或包含订单归属地市编码则记录为优先仓库
			String sql = "select scope_code,p_house_id,priority,attribution from es_warehouse where house_id = ?";
			List wList = baseDaoSupport.queryForList(sql, l_house_id);
			Map wMap = (Map)wList.get(0);
			WareHouseVO house = new WareHouseVO();
			house.setV_house_id(v_house_id);
			house.setL_house_id(l_house_id);
			house.setScope_code(wMap.get("scope_code").toString());
			house.setP_house_id(wMap.get("p_house_id").toString());
			house.setPriority(wMap.get("priority").toString());
			String attribution = wMap.get("attribution").toString();
			if("1".equals(attribution)){
				house.setIs_owner(EcsOrderConsts.IS_DEFAULT_VALUE);
			}else{
				house.setIs_owner(EcsOrderConsts.NO_DEFAULT_VALUE);
			}
			house.setProduct_id(product_id);
			if(house.getScope_code().contains(city)){				
				list.add(house);
			}else{
				resultList.add(house);
			}
		}
		if(list.size()==0){
			resp.setWareHouseList(resultList);
			delivery.setHouse_id("-1");
			delivery.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			delivery.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			delivery.store();
			CommonTools.addBusiError("-1", "没有可以配送到"+city+"的仓库");
		}else if(list.size()==1){
			WareHouseVO house = (WareHouseVO)list.get(0);
			delivery.setHouse_id(JsonUtil.toJson(house));
			delivery.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			delivery.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			delivery.store();
			resp.setWareHouseList(list);
			resp.setError_code("0");
			resp.setError_msg("仓库匹配成功");
			return resp;
		}else{
			resp.setWareHouseList(list);
			resp.setError_code("0");
			resp.setError_msg("地市优先规则执行成功");
		}		
		return resp;
	}
	
	public WareHouseMatchResp hasNumFirst(WareHouseMatchReq req) throws ApiBusiException{

		List<Object> list = new ArrayList<Object>();		
		WareHouseMatchResp resp = new WareHouseMatchResp();
		list = req.getWareHouseList();
		List<Object> resultList = new ArrayList<Object>();
		String order_id = req.getOrder_id();
		OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderDeliveryBusiRequest delivery = ordertree.getOrderDeliveryBusiRequests().get(0);
		for(int i=0;i<list.size();i++){
			WareHouseVO house = (WareHouseVO)list.get(i);
			String l_house_id = house.getL_house_id();
			String product_id = house.getProduct_id();
			String sql = "select inventory_num from es_goods_inventory_apply where  inventory_num>0 and apply_type='P2L' and house_id_in=? and goods_id = ?";
			List result = baseDaoSupport.queryForList(sql, l_house_id,product_id);
			if(result.size()==1){
				resultList.add(list.get(i));
			}
		}
		resp.setWareHouseList(resultList);
		if(resultList.size()==0){
			WareHouseVO house = (WareHouseVO)list.get(0);
			delivery.setHouse_id("-1");
			delivery.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			delivery.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			delivery.store();
			CommonTools.addBusiError("-1", "货品"+house.getProduct_id()+"没有找到库存");
		}else if(resultList.size()==1){			
			WareHouseVO house = (WareHouseVO)list.get(0);			
			delivery.setHouse_id(JsonUtil.toJson(house));
			delivery.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			delivery.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			delivery.store();
			resp.setError_code("0");
			resp.setError_msg("仓库匹配成功");
			return resp;
		}
		resp.setError_code("0");
		resp.setError_msg("库存优先规则执行成功");
		return resp;
	}
	
	public WareHouseMatchResp ownerFirst(WareHouseMatchReq req) throws ApiBusiException{
		//目前仓库版本没有添加是否自营的数据，后续再添加此部分逻辑
		List<Object> list = new ArrayList<Object>();		
		WareHouseMatchResp resp = new WareHouseMatchResp();
		list = req.getWareHouseList();
		List<Object> resultList = new ArrayList<Object>();
		for(int i=0;i<list.size();i++){
			WareHouseVO house = (WareHouseVO)list.get(i);
			if(house.getIs_owner().equals(EcsOrderConsts.IS_DEFAULT_VALUE)){
				resultList.add(house);
			}
		}		
		if(resultList.size()==0){
			resp.setWareHouseList(list);
		}else if(resultList.size()==1){
			String order_id = req.getOrder_id();
			WareHouseVO house = (WareHouseVO)list.get(0);
			OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
			OrderDeliveryBusiRequest delivery = ordertree.getOrderDeliveryBusiRequests().get(0);
			delivery.setHouse_id(JsonUtil.toJson(house));
			delivery.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			delivery.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			delivery.store();
			resp.setWareHouseList(resultList);
			resp.setError_code("0");
			resp.setError_msg("仓库匹配成功");
			return resp;
		}else{
			resp.setWareHouseList(resultList);
		}
		resp.setError_code("0");
		resp.setError_msg("自营优先规则执行成功");
		return resp;
	}
	
	public WareHouseMatchResp priceFirst(WareHouseMatchReq req) throws ApiBusiException{
		List<Object> list = new ArrayList<Object>();		
		WareHouseMatchResp resp = new WareHouseMatchResp();
		list = req.getWareHouseList();
		List<Object> resultList = new ArrayList<Object>();
		Map<String,Double> priceMap = new HashMap<String, Double>();
		for(int i=0;i<list.size();i++){
			WareHouseVO house = (WareHouseVO)list.get(i);
			String p_house_id = house.getP_house_id();
			String product_id = house.getProduct_id();
			String sql = "select purchase_prices from es_goods_inventory where  house_id=? and product_id = ?";
			String purchase_price = baseDaoSupport.queryForString(sql,  p_house_id,product_id);
			double price = Double.parseDouble(purchase_price);
			house.setPurchase_price(purchase_price);
			if(i==0){
				priceMap.put("price", price);
			}else{
				if(price<priceMap.get("price")){
					priceMap.put("price", price);
				}
			}
		}
		for(int i=0;i<list.size();i++){
			WareHouseVO house = (WareHouseVO)list.get(i);
			Double price = Double.parseDouble(house.getPurchase_price());
			if(price.compareTo(priceMap.get("price"))==0){
				resultList.add(house);
			}
		}
		resp.setWareHouseList(resultList);
		if(resultList.size()==1){
			String order_id = req.getOrder_id();
			WareHouseVO house = (WareHouseVO)list.get(0);
			OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
			OrderDeliveryBusiRequest delivery = ordertree.getOrderDeliveryBusiRequests().get(0);
			delivery.setHouse_id(JsonUtil.toJson(house));
			delivery.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			delivery.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			delivery.store();
			resp.setError_code("0");
			resp.setError_msg("仓库匹配成功");
			return resp;
		}
		resp.setError_code("0");
		resp.setError_msg("价格优先规则执行成功");
		return resp;
	}
	
	public WareHouseMatchResp priorityFirst(WareHouseMatchReq req) throws ApiBusiException{
		List<Object> list = new ArrayList<Object>();		
		WareHouseMatchResp resp = new WareHouseMatchResp();
		list = req.getWareHouseList();
		List<Object> resultList = new ArrayList<Object>();
		Map<String,Integer> priceMap = new HashMap<String, Integer>();
		for(int i=0;i<list.size();i++){
			WareHouseVO house = (WareHouseVO)list.get(i);
			int priority =Integer.parseInt(house.getPriority());
			if(i==0){
				priceMap.put("priority", priority);
			}else{
				if(priority<priceMap.get("priority")){
					priceMap.put("priority", priority);
				}
			}
		}
		for(int i=0;i<list.size();i++){
			WareHouseVO house = (WareHouseVO)list.get(i);
			int priority =Integer.parseInt(house.getPriority());
			if(priority==priceMap.get("priority")){
				resultList.add(house);
			}
		}
		resp.setWareHouseList(resultList);
		if(resultList.size()==1){
			String order_id = req.getOrder_id();
			WareHouseVO house = (WareHouseVO)list.get(0);
			OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
			OrderDeliveryBusiRequest delivery = ordertree.getOrderDeliveryBusiRequests().get(0);
			delivery.setHouse_id(JsonUtil.toJson(house));
			delivery.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			delivery.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			delivery.store();
			resp.setError_code("0");
			resp.setError_msg("仓库匹配成功");
			return resp;
		}
		resp.setError_code("0");
		resp.setError_msg("优先级优先规则执行成功");
		return resp;
	}
	
	public WareHouseMatchResp RandomFirst(WareHouseMatchReq req) throws ApiBusiException{
		String order_id = req.getOrder_id();
		List<Object> list = req.getWareHouseList();
		List<Object> resultList = new ArrayList<Object>();
		WareHouseMatchResp resp = new WareHouseMatchResp();
		String endStr = order_id.substring(order_id.length()-3);
		int end =Integer.parseInt(endStr) ;
		int index = end%(list.size());
		WareHouseVO house = (WareHouseVO)list.get(index);
		OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderDeliveryBusiRequest delivery = ordertree.getOrderDeliveryBusiRequests().get(0);
		delivery.setHouse_id(JsonUtil.toJson(house));
		delivery.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		delivery.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		delivery.store();
		resultList.add(house);
		resp.setWareHouseList(resultList);
		resp.setError_code("0");
		resp.setError_msg("仓库匹配成功");
		return resp;
	}
	
	public Boolean isMatch(String order_id) throws ApiBusiException{
		String is_match = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_MATCH_WAREHOUSE);
		if(StringUtil.isEmpty(is_match)){
			String product_id = getProductIdByOrderId(order_id);
			if(StringUtil.isEmpty(product_id)){
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.IS_MATCH_WAREHOUSE}, new String[]{EcsOrderConsts.NO_DEFAULT_VALUE});
				return false;
			}else{
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.IS_MATCH_WAREHOUSE}, new String[]{EcsOrderConsts.IS_DEFAULT_VALUE});
				return true;
			}
		}else if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_match)){
			OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
			OrderDeliveryBusiRequest delivery = ordertree.getOrderDeliveryBusiRequests().get(0);
			String house_id = delivery.getHouse_id();
			if(StringUtil.isEmpty(house_id)){
				return true;
			}else{
				String product_id = getProductIdByOrderId(order_id);
				String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
				List vList = queryHouseInfo(product_id, order_from);
				boolean flag = true;
				for(int i=0;i<vList.size();i++){
					Map map = (HashMap)vList.get(i);
					if(house_id.equals(map.get("house_id_out").toString())){
						flag = false;
						break;
					}
				}
				return flag;
			}
		}else{
			return false;
		}
		
	}
	
	private String getProductIdByOrderId(String order_id) throws ApiBusiException{
		OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
		if(null==ordertree){
			CommonTools.addBusiError("-1", "订单不存在");
		}
		//获取订单下手机货品的产品编码
		//目前只针对手机货品进行仓库匹配，后续增加实物礼品等匹配规则
		List<Goods> goodsList = CommonDataFactory.getInstance().getEntityProducts(order_id);
		String product_id = "";
		for(Goods product : goodsList){
			if(EcsOrderConsts.PRODUCT_TYPE_TERMINAL.equals(product.getType_id())){
				product_id = product.getProduct_id();
			}
		}
		//如果商品不包含手机，则根据实物礼品匹配仓库
		if(StringUtil.isEmpty(product_id)){
			List<AttrGiftInfoBusiRequest> gifts = CommonDataFactory.getInstance().getOrderTree(order_id).getGiftInfoBusiRequests();
			for(AttrGiftInfoBusiRequest gift : gifts){
				//实物礼品
				if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(gift.getIs_virtual())){
					Goods goods = CommonDataFactory.getInstance().getGoodsBySku(gift.getSku_id());
					if(null!=goods){
						product_id = goods.getProduct_id();
						break;
					}
				}
			}
		}
		return product_id;
	}
	
	private List queryHouseInfo(String product_id,String order_from){
		String sql = "select house_id_in,house_id_out from es_goods_inventory_apply a,es_virtual_warehouse b " +
				"where  a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.house_id_in = b.house_id and a.inventory_num>0 " +
						"and a.apply_type='L2V' and a.goods_id =? and b.org_id_str=?";
		List vList = baseDaoSupport.queryForList(sql, product_id,order_from);
		
		return vList;
	}
	private List queryHouseInfo(String product_id,String order_from,String p_house_id){
		String sql = "select house_id_in,house_id_out from es_goods_inventory_apply a,es_virtual_warehouse b " +
				"where  a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.house_id_in = b.house_id and a.inventory_num>0 " +
						"and a.apply_type='L2V' and a.goods_id =? and b.org_id_str=? and a.house_id_out in (select house_id from es_warehouse where p_house_id = ?)";
		List vList = baseDaoSupport.queryForList(sql, product_id,order_from,p_house_id);
		
		return vList;
	}
	public static void main(String[] args) {
		String order_id = "201507233524452911";
		String endStr = order_id.substring(order_id.length()-3);
		int end =Integer.parseInt(endStr) ;
		logger.info(end);
	}
	private String queryPHouseId(String house_code){
		String sql = "select house_id from es_warehouse a where a.house_code = ?";
		String house_id = baseDaoSupport.queryForString(sql, house_code);
		return house_id;
	}
}
