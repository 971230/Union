package com.ztesoft.net.mall.plugin.search;

import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.framework.database.IDBRouter;
import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.plugin.search.IGoodsSearchFilter;
import com.ztesoft.net.mall.core.plugin.search.SearchSelector;

import java.util.List;

/**
 * 商品分类搜索过虑器
 * @author kingapex
 *
 */
public class CatSearchFilter extends AutoRegisterPlugin implements
		IGoodsSearchFilter {
	
	private IDBRouter baseDBRouter; 

	@Override
	public List<SearchSelector> createSelectorList(Cat cat, String url,
			String urlFragment) {
		return null;
	}

	@Override
	public void filter(StringBuffer sql,Cat cat, String urlFragment) {
		FreeMarkerPaser.getInstance().putData("cat",cat);
		if(! StringUtil.isEmpty(urlFragment) ){
			String cat_path  = cat.getCat_path();
			if (cat_path != null) {
				sql.append( " and  g.cat_id in(" ) ;
				sql.append("select c.cat_id from " + baseDBRouter.getTableName("goods_cat"));
				sql.append(" c where c.cat_path like '" + cat_path + "%')");
			}
		}
	}

	@Override
	public IDBRouter getBaseDBRouter() {
		return baseDBRouter;
	}

	@Override
	public void setBaseDBRouter(IDBRouter baseDBRouter) {
		this.baseDBRouter = baseDBRouter;
	}

	@Override
	public String getFilterId() {		
		return "cat";
	}

	
	@Override
	public String getAuthor() {
		return "kingapex";
	}

	@Override
	public String getId() {		
		return "catSearchFilter";
	}
	
	@Override
	public String getName() {	
		return "商品分类筛选器";
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
