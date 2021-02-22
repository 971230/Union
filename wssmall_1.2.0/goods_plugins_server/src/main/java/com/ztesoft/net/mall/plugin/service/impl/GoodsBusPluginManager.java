package com.ztesoft.net.mall.plugin.service.impl;

import java.util.List;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.GoodsBusiness;
import com.ztesoft.net.mall.plugin.service.IGoodsBusPluginManager;

/**
 * 商品外系统扩展属性处理类
 * @author hu.yi
 * @date 2014.01.22
 */
public class GoodsBusPluginManager extends BaseSupport implements IGoodsBusPluginManager {

	@Override
	public List qryBusType(){
		String sql = "SELECT * FROM es_dc_public WHERE stype = '8055'";
		return this.baseDaoSupport.queryForList(sql);
	}
	
	
	
	@Override
	public void saveGoodsBusiness(GoodsBusiness goodsBusiness){
		String business_id = this.baseDaoSupport.getSequences("SEQ_ES_GOODS_BUSINESS");
		goodsBusiness.setBusiness_id(business_id);
		this.baseDaoSupport.insert("es_goods_business", goodsBusiness);
	}
	
	
	@Override
	public void updateGoodsBusiness(GoodsBusiness goodsBusiness){
		
		this.baseDaoSupport.update("es_goods_business", goodsBusiness, "goods_id='"+goodsBusiness.getGoods_id()+"'");
	}
	
	
	@Override
	public GoodsBusiness getGoodsBusiness(String goods_id){
		
		String sql = "SELECT * FROM es_goods_business WHERE goods_id = '"+goods_id+"'";
		
		return (GoodsBusiness) this.baseDaoSupport.queryForObject(sql, GoodsBusiness.class);
	}
}
