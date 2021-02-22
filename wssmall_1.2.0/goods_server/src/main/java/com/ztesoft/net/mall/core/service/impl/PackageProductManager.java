package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.util.DateUtil;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.PackageProduct;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.service.IPackageProductManager;
import com.ztesoft.net.mall.core.service.IProductManager;
import com.ztesoft.net.sqls.SF;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 捆绑商品列表
 * @author lzf<br/>
 * 2010-4-7 下午03:27:45<br/>
 * version 1.0<br/>
 */
public class PackageProductManager extends BaseSupport implements
		IPackageProductManager {
	
	private IGoodsManager goodsManager;
	private IProductManager productManager;
	
	
	@Override
	public void add(PackageProduct packageProduct) {
		this.baseDaoSupport.insert("package_product", packageProduct);

	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)  
	public void add(Goods goods, String[] product_id, int[] pkgnum) {
		String sn = "P" + DateUtil.toString( new Date(System.currentTimeMillis()) ,"yyyyMMddhhmmss" );
		goods.setSn(sn);
		goods.setPoint(0);
		this.baseDaoSupport.insert("goods", goods);
		String goods_id = goods.getGoods_id();
		for(int i=0;i<product_id.length;i++){
			PackageProduct product = new PackageProduct();
			product.setGoods_id(goods_id);
			product.setProduct_id(product_id[i]);
			product.setPkgnum(pkgnum[i]);
			this.add(product);
		}
		
		Product product = new Product();
		product.setGoods_id(goods_id);
		//product.setCost(  goods.get );
		product.setName(goods.getName());
		product.setPrice(   goods.getPrice() );
		product.setSn( sn);
		product.setStore(goods.getStore());
		product.setWeight(goods.getWeight());
		
		List<Product> productList = new ArrayList<Product>();
		productList.add(product);
		this.productManager.add(productList , goods.getGoods_id());
		
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void edit(Goods goods, String[] product_id,
			int[] pkgnum) {
		this.baseDaoSupport.update("goods", goods, "goods_id="+goods.getGoods_id());
		this.baseDaoSupport.execute(SF.goodsSql("DELETE_PACKAGE_PRODUCT"), goods.getGoods_id());
		for(int i=0;i<product_id.length;i++){
			PackageProduct product = new PackageProduct();
			product.setGoods_id(goods.getGoods_id());
			product.setProduct_id(product_id[i]);
			product.setPkgnum(pkgnum[i]);
			this.add(product);
		}

	 
		Product product = new Product();
		product.setGoods_id(goods.getGoods_id());
		product.setName(goods.getName());
		product.setPrice(   goods.getPrice() );
		product.setStore(goods.getStore());
		product.setWeight(goods.getWeight());
		product.setSn(goods.getSn());
		List<Product> productList = new ArrayList<Product>();
		productList.add(product);
		this.productManager.add(productList , goods.getGoods_id());
		
	}

	
	@Override
	public List list(String goods_id) {
		String sql = SF.goodsSql("PACKAGE_PRODUCT_SELECT");
		List list = this.daoSupport.queryForList(sql, goods_id);
		return list;
	}

	public IGoodsManager getGoodsManager() {
		return goodsManager;
	}

	public void setGoodsManager(IGoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}

	public IProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(IProductManager productManager) {
		this.productManager = productManager;
	}




}
