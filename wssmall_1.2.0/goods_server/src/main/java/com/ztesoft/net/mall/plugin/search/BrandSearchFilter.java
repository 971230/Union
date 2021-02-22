package com.ztesoft.net.mall.plugin.search;

import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Brand;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.plugin.search.IGoodsSearchFilter;
import com.ztesoft.net.mall.core.plugin.search.SearchSelector;
import com.ztesoft.net.mall.core.utils.UrlUtils;

import services.BrandInf;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 品牌过滤器
 * @author kingapex
 *
 */
public class BrandSearchFilter extends AutoRegisterPlugin implements
		IGoodsSearchFilter {

    @Resource
	private BrandInf brandServ;
	@Override
	public void filter(StringBuffer sql,Cat cat, String urlFragment) {
		if (!StringUtil.isEmpty(urlFragment) && !"0".equals(urlFragment)) {
			sql.append(" and g.brand_id=" + urlFragment);
		}
	}

	@Override
	public String getFilterId() {

		return "brand";
	}

	
	@Override
	public List<SearchSelector> createSelectorList(Cat cat,String url, String urlFragment) {

		List<SearchSelector> selectorList = new ArrayList<SearchSelector>();

		//此分类的品牌列表
		List<Brand> brandList = null;
		
		if(cat!=null){
			brandList  = this.brandServ.listByTypeId(cat.getType_id());
		}else{
			brandList  = this.brandServ.list();
		}
		

		/**
		 * ------------------------ 生成'全部'品牌的选择器 -----------------------
		 */
		SearchSelector allselector = new SearchSelector();
		allselector.setName("全部");
		allselector.setUrl(url+".html");
		if (StringUtil.isEmpty(urlFragment) || "0".equals(urlFragment)) {
			allselector.setSelected(true);
		} else {
			allselector.setSelected(false);
		}
		selectorList.add(allselector);

		for (Brand brand : brandList) {
			SearchSelector selector = new SearchSelector();
			selector.setName(brand.getName());
			selector.setUrl(UrlUtils.addUrl(url,"brand",brand.getBrand_id().toString()));
			if (brand.getBrand_id().toString().equals(urlFragment)) {
				selector.setSelected(true);
			} else {
				selector.setSelected(false);
			}
			selectorList.add(selector);
		}

		return selectorList;
	}

	@Override
	public String getAuthor() {

		return "kingapex";
	}

	@Override
	public String getId() {

		return "brandSearchFilter";
	}

	@Override
	public String getName() {

		return "品牌搜索过虑器";
	}

	@Override
	public String getType() {

		return "goodssearch";
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
