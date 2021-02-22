package com.ztesoft.net.mall.plugin.search;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.mall.core.plugin.search.IGoodsDataFilter;

/**
 * 商品属性数据过滤器
 * @author kingapex
 *
 */
public class GoodsPropertyDataFilter extends AutoRegisterPlugin implements
		IGoodsDataFilter {

	
	@Override
	public void filter(Map<String, Object> goods, ResultSet rs) {
		//lzf add
		Map propMap = new HashMap();
		
		for(int i=0;i<20;i++){
			String value;
			try {
				value = rs.getString("p" + (i+1));
				propMap.put("p"+(i+1),value);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		goods.put("propMap", propMap);
	}

	
	@Override
	public String getAuthor() {
		
		return "kingapex";
	}

	
	@Override
	public String getId() {
		
		return "goodsPropertyDataFilter";
	}

	
	@Override
	public String getName() {
		
		return "商品属性数据过滤器";
	}

	
	@Override
	public String getType() {
		
		return "searchFilter";
	}

	
	@Override
	public String getVersion() {
		
		return "1.0";
	}

	
	@Override
	public void perform(Object... params) {
		

	}
	@Override
	public void register() {
		

	}
}
