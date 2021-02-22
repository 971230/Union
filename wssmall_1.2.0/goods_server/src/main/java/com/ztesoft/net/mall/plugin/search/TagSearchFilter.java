package com.ztesoft.net.mall.plugin.search;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.plugin.search.IGoodsSearchFilter;
import com.ztesoft.net.mall.core.plugin.search.IPutWidgetParamsEvent;
import com.ztesoft.net.mall.core.plugin.search.SearchSelector;


/**
 * 商品标签搜索过虑器  
 * @author kingapex
 *
 */
public class TagSearchFilter extends AutoRegisterPlugin implements
		IGoodsSearchFilter ,IPutWidgetParamsEvent{

	@Override
	public List<SearchSelector> createSelectorList(Cat cat, String url,
			String urlFragment) {
		
		return null;
	}


	@Override
	public void filter(StringBuffer sql, Cat cat, String urlFragment) {
		if(!StringUtil.isEmpty(urlFragment))
		sql.append(" and goods_id in (select rel_id from es_tag_rel where tag_id in("+urlFragment +") )");
	}



	@Override
	public void putParams(Map<String, Object> params, String urlFragment) {
		if(!StringUtil.isEmpty(urlFragment)){
			params.put("tag", urlFragment);
		}
	}
	
	
	@Override
	public String getFilterId() {
		
		return "tag";
	}


	@Override
	public String getAuthor() {
		
		return "kingapex";
	}


	@Override
	public String getId() {
		
		return "tagSearchFilter";
	}


	@Override
	public String getName() {
		
		return "商品标签搜索过虑器";
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