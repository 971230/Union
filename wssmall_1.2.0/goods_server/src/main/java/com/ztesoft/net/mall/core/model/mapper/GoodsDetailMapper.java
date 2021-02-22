package com.ztesoft.net.mall.core.model.mapper;

import com.ztesoft.net.mall.core.model.support.GoodsView;
import com.ztesoft.net.mall.core.utils.GoodsUtils;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsDetailMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		GoodsView  goods = new GoodsView();
		goods.setName(rs.getString("name"));
		goods.setSn(rs.getString("sn"));
//		goods.setUnit(rs.getString("unit"));
		goods.setWeight(rs.getDouble("weight"));
		goods.setGoods_id(rs.getString("goods_id"));
		goods.setImage_default(rs.getString("image_default"));
		goods.setImage_file(rs.getString("image_file"));
		goods.setMktprice(rs.getDouble("mktprice"));
		goods.setMarket_enable(rs.getInt("market_enable"));
		goods.setPrice(rs.getDouble("price"));
		goods.setCreate_time(rs.getString("create_time"));
		goods.setLast_modify(rs.getString("last_modify"));
		goods.setBrand_name(rs.getString("brand_name"));
		goods.setParams(rs.getString("params"));
		goods.setIntro(rs.getString("intro"));
		goods.setBrief(rs.getString("brief"));
		goods.setPage_title(rs.getString("page_title"));
		goods.setMeta_keywords(rs.getString("meta_keywords"));
		goods.setMeta_description(rs.getString("meta_description"));
		goods.setSpecs(rs.getString("specs"));
		List specList = GoodsUtils.getSpecList(goods.getSpecs());
 		goods.setSpecList(specList);
		goods.setType_id(rs.getString("type_id"));
		goods.setBrand_id(rs.getString("brand_id"));
		goods.setCat_id(rs.getString("cat_id"));
		goods.setAdjuncts(rs.getString("adjuncts"));
		if(goods.getAdjuncts()!=null && goods.getAdjuncts().equals("")){//由于update的时候，不能保存null故保存为空串。但是原有程序之对null做了判断。
			goods.setAdjuncts(null);
		}
		goods.setStore(rs.getInt("store"));
		goods.setDisabled(rs.getInt("disabled"));
		goods.setMarket_enable(rs.getInt("market_enable"));
		
		
		Map propMap = new HashMap();
		
		for(int i=0;i<20;i++){
			String value = rs.getString("p" + (i+1));
			propMap.put("p"+i,value);
		}
		goods.setPropMap(propMap);
		return goods;
	}
	
 


	
	
} 
