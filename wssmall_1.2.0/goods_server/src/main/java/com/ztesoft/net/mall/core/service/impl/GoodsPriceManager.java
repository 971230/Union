package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.GoodsPrice;
import com.ztesoft.net.mall.core.service.IGoodsPriceManager;
import com.ztesoft.net.sqls.SF;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public class GoodsPriceManager extends BaseSupport implements
	IGoodsPriceManager {

	
	@Override
	public List<Map> list(String goods_id) {
		String sql = SF.goodsSql("GOODS_PRICE_SELECT");
		List list = this.baseDaoSupport.queryForList(sql, goods_id);
		return list;
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(String goods_id, List<GoodsPrice> list) {
		//删除原有
		String deleteSql = SF.goodsSql("GOODS_PRICE_DELETE");
		this.baseDaoSupport.execute(deleteSql, goods_id);
		
		//依次加入
		for(GoodsPrice adjunct:list){
			this.add(adjunct);
		}
	}
	
	private void add(GoodsPrice price) {
		this.baseDaoSupport.insert("es_goods_price", price);
	}

}