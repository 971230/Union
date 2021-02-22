package com.ztesoft.net.app.base.core.service.solution.impl;

import com.ztesoft.net.app.base.core.model.ProductColor;
import com.ztesoft.net.app.base.core.service.solution.IProductColorService;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;

import java.util.List;

public class ProductColorService extends BaseSupport<ProductColor> implements
		IProductColorService {

	@Override
	public void add(ProductColor color) {
		if(StringUtil.isEmpty(color.getColorname()) ) throw new IllegalArgumentException("argument  color.colorname is null");
		this.daoSupport.insert("eop_product_color", color);

	}

	@Override
	public int detete(int id) {
		String sql ="select count(0) from eop_product where colorid=?";
		int count  = this.daoSupport.queryForInt(sql, id);
		if(count>0){
			return 2;
		}
		sql  ="delete from eop_product_color where id=?";
		this.daoSupport.execute(sql, id);
		return 1;
	}

	@Override
	public void edit(ProductColor color) {
		if(color.getId()==0) throw new IllegalArgumentException("argument  color.id is null");
		if(StringUtil.isEmpty(color.getColorname()) ) throw new IllegalArgumentException("argument  color.colorname is null");
		this.daoSupport.update("eop_product_color", color, "id="+color.getId());

	}

	@Override
	public ProductColor get(int id) {
		return this.daoSupport.queryForObject("select * from eop_product_color where id=?", ProductColor.class, id);
	}

	@Override
	public List<ProductColor> list() {
		String sql  ="select * from eop_product_color order by id";
		return this.daoSupport.queryForList(sql, ProductColor.class);
	}

	@Override
	public Page list(int pageNo, int pageSize) {
		String sql  ="select * from eop_product_color order by id";
		return this.daoSupport.queryForPage(sql, pageNo, pageSize);
	}

}
