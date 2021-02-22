package com.ztesoft.net.mall.core.service.impl.batchimport;

import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.model.ImportDataSource;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.service.IProductManager;
import com.ztesoft.net.mall.core.service.batchimport.IGoodsDataImporter;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品规格导入器 此规格导入器，商品是未开启规格的
 * 
 * @author kingapex
 * 
 */
public class GoodsSpecImporter implements IGoodsDataImporter {
	protected IProductManager productManager;
	protected IDaoSupport daoSupport;

	@Override
	public void imported(Object value, Element node, ImportDataSource importDs,
			Map goods) {
		String goodsid = goods.get("goods_id").toString();
		Product product = new Product();
		product.setGoods_id(goodsid);
		product.setCost(Double.valueOf("" + goods.get("cost")));
		product.setPrice(Double.valueOf("" + goods.get("price")));
		product.setSn((String) goods.get("sn"));
		product.setStore(Integer.valueOf("" + goods.get("store")));
		product.setWeight(Double.valueOf("" + goods.get("weight")));
		product.setName((String) goods.get("name"));

		List<Product> productList = new ArrayList<Product>();
		
		productList.add(product);
		this.productManager.add(productList,goodsid);
	}

	public IProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(IProductManager productManager) {
		this.productManager = productManager;
	}

	public IDaoSupport getDaoSupport() {
		return daoSupport;
	}

	public void setDaoSupport(IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

}
