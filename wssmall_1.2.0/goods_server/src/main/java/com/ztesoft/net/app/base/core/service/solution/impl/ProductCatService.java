package com.ztesoft.net.app.base.core.service.solution.impl;

import com.ztesoft.net.app.base.core.model.ProductCat;
import com.ztesoft.net.app.base.core.service.solution.IProductCatService;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;

import java.util.List;


/**
 * 解决方案分类管理实现 
 * @author kingapex
 * 2010-9-1下午01:55:23
 */
public class ProductCatService extends BaseSupport<ProductCat> implements IProductCatService {

	
	@Override
	public void add(ProductCat cat) {
		if(StringUtil.isEmpty(cat.getName()) ) throw new IllegalArgumentException("argument  cat.name is null");
		cat.setNum(0);		
		this.daoSupport.insert("eop_product_cat", cat);
	}

	
	
	
	@Override
	public int detete(int id) {
		String sql ="select count(0) from eop_product where catid=?";
		int count  = this.daoSupport.queryForInt(sql, id);
		if(count>0){
			return 2;
		}
		sql  ="delete from eop_product_cat where id=?";
		this.daoSupport.execute(sql, id);
		return 1;
		
	}

	
	@Override
	public void edit(ProductCat cat) {
		if(cat.getId()==0) throw new IllegalArgumentException("argument  cat.id is null");
		if(StringUtil.isEmpty(cat.getName()) ) throw new IllegalArgumentException("argument  cat.name is null");
		this.daoSupport.update("eop_product_cat", cat, "id="+cat.getId());
	}

	
	
	@Override
	public List<ProductCat> list() {
		String sql  ="select * from eop_product_cat order by sort desc";
		return this.daoSupport.queryForList(sql, ProductCat.class);
	}

	
	@Override
	public Page list(int pageNo, int pageSize) {
		String sql  ="select * from eop_product_cat order by sort desc";
		return this.daoSupport.queryForPage(sql, pageNo, pageSize);
	}

	@Override
	public ProductCat get(int id) {
		
		return this.daoSupport.queryForObject("select * from eop_product_cat where id=?", ProductCat.class, id);
	}

}
