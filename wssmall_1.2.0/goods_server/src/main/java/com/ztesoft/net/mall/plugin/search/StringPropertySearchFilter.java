package com.ztesoft.net.mall.plugin.search;

import java.util.List;

import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.plugin.search.IGoodsSearchFilter;
import com.ztesoft.net.mall.core.plugin.search.SearchSelector;

/**
 * 字串属性搜索过虑器
 * @author kingapex
 *
 */
public class StringPropertySearchFilter extends AutoRegisterPlugin implements
		IGoodsSearchFilter {

	
	@Override
	public void register() {

	}

	
	@Override
	public List<SearchSelector> createSelectorList(Cat cat, String url,
			String urlFragment) {
		return null;
	}

	
	@Override
	public void filter(StringBuffer sql, Cat cat, String urlFragment) {

	}

	
	@Override
	public String getFilterId() {
		
		return "sprop";
	}

	
	@Override
	public String getAuthor() {
		
		return "kingapex";
	}

	
	@Override
	public String getId() {
		
		return "stringPropertySearchFilter";
	}

	
	@Override
	public String getName() {
		
		return "字串属性搜索过虑器";
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

}
