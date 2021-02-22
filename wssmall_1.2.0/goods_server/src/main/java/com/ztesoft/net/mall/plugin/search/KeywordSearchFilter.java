package com.ztesoft.net.mall.plugin.search;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.plugin.search.IGoodsSearchFilter;
import com.ztesoft.net.mall.core.plugin.search.IPutWidgetParamsEvent;
import com.ztesoft.net.mall.core.plugin.search.SearchSelector;

/**
 * 关键字搜索过滤器
 * @author kingapex
 *
 */
public class KeywordSearchFilter extends AutoRegisterPlugin implements
		IGoodsSearchFilter,IPutWidgetParamsEvent {

	
	@Override
	public List<SearchSelector> createSelectorList(Cat cat, String url,
			String urlFragment) {
		
			return null;
	}
	
	@Override
	public void putParams(Map<String, Object> params, String urlFragment) {
		String keyword = urlFragment==null ?"" :urlFragment;
		params.put("keyword", keyword);
	}
	
	@Override
	public void filter(StringBuffer sql, Cat cat, String urlFragment) {
		String keyword = urlFragment==null ?"" :urlFragment;
		if (!StringUtil.isEmpty(keyword)) {
			String encoding  = EopSetting.ENCODING;
			if(!StringUtil.isEmpty(encoding)){
				keyword = StringUtil.to(keyword,encoding);
			}
			sql.append(" and g.name like '%");
			sql.append(keyword);
			sql.append("%'");
		}
	}

	
	@Override
	public String getFilterId() {
		
		return "keyword";
	}

	
	@Override
	public String getAuthor() {
		
		return "kingapex";
	}

	
	@Override
	public String getId() {
		
		return "keywordSearchFilter";
	}

	
	@Override
	public String getName() {
		
		return "关键字搜索过滤器";
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
