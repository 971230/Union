package com.ztesoft.net.mall.plugin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.plugin.service.IGoodsAreaPluginManager;

/**
 * 商品地域插件处理实现
 * @author hu.yi
 * @date 2014.01.21
 */
public class GoodsAreaPluginManager extends BaseSupport implements IGoodsAreaPluginManager{

	
	@Override
	public List<Map<String, Object>> qryAreaList(){
		
		String sql = "SELECT * FROM es_dc_public WHERE stype = '8020'";
		
		return this.baseDaoSupport.queryForList(sql);
	}
	
	
	@Override
	public void updateCrmOfferId(String goods_id,String crm_offer_id){
		String sql = "UPDATE es_goods SET crm_offer_id = '"+crm_offer_id+"' WHERE goods_id = '"+goods_id+"'";
		this.baseDaoSupport.execute(sql); 
	}
	
	
	@Override
	public void insertGoodsArea(String goods_id,String lan_id){
		String sql = "INSERT INTO es_goods_area(goods_id,lan_id) VALUES ('"+goods_id+"','"+lan_id+"')";
		this.baseDaoSupport.execute(sql);
	}
	
	
	
	@Override
	public Map<String, Object> qryGoodsAreaMap(String goods_id){
		String sql = "SELECT a.crm_offer_id,b.lan_id FROM es_goods a,es_goods_area b WHERE a.source_from=b.source_from and a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.goods_id = b.goods_id AND a.goods_id = '"+goods_id+"'";
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql);
		if(!ListUtil.isEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				map = list.get(0);
			}
		}
		return map;
	}
	
	
	@Override
	public void updateGoodsArea(String goods_id,String lan_id){
		String sql = "UPDATE es_goods_area SET lan_id = '"+lan_id+"' WHERE goods_id = '"+goods_id+"'";
		this.baseDaoSupport.execute(sql);
	}
}
