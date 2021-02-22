package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.mall.core.model.GoodsComplex;
import com.ztesoft.net.mall.core.service.IGoodsComplexManager;
import com.ztesoft.net.sqls.SF;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public class GoodsComplexManager extends BaseSupport implements
		IGoodsComplexManager {

	
	@Override
	public List listAllComplex(String goods_id) {
		//String sql = "select r.*, g.goods_id, g.sn, g.name, g.image_default image, g.price, g.mktprice from " + this.getTableName("goods_complex") + " r, " + this.getTableName("goods") + " g  where ((goods_2 = goods_id AND goods_1="+goods_id+") or (goods_1 = goods_id and goods_2 ="+goods_id+" and manual='both')) and rate > 99";
		
		//查询单项关联的商品(goods1=goods_id) 或者是双向关联的商品(goods2=goods_id and manual='both')
		String sql = SF.goodsSql("LIST_ALL_COMPLEX");
		
		List<Map> list = this.daoSupport.queryForList(sql, goods_id, goods_id);
		for(Map map :list){
			String image  =(String) map.get("image");
			image = UploadUtil.replacePath(image);
			map.put("image", image);
		}
		return list;
	}

	
	@Override
	public void addCoodsComplex(GoodsComplex complex) {
		this.baseDaoSupport.insert("goods_complex", complex);
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void globalGoodsComplex(String goods_1, List<GoodsComplex> list) {
		//删除原有
		String deleteSql = SF.goodsSql("GLOBAL_GOODS_COMPLEX");
		this.baseDaoSupport.execute(deleteSql, goods_1);
		
		//依次加入
		if(list != null && !list.isEmpty()){
			for(GoodsComplex complex:list){
				this.addCoodsComplex(complex);
			}
		}
	}


	@Override
	public List listComplex(String goods_id) {
		//查询单项关联的商品(goods1=goods_id)
		String sql = SF.goodsSql("LIST_COMPLEX");
		List<Map> list = this.daoSupport.queryForList(sql,goods_id);
		for(Map map :list){
			String image  =(String) map.get("image");
			image = UploadUtil.replacePath(image);
			map.put("image", image);
		}
		return list;
		 
	}

}
