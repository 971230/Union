package com.ztesoft.net.mall.plugin.standard.product;

import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsAfterAddEvent;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsAfterEditEvent;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsDeleteEvent;
import com.ztesoft.net.mall.core.service.IProductManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 普通货品插件
 * @author kingapex
 * @date 2011-11-6 下午4:34:51 
 * @version V1.0
 */
public class GeneralProductPlugin extends AutoRegisterPlugin implements IGoodsAfterAddEvent,IGoodsAfterEditEvent,IGoodsDeleteEvent {
	 
	private IProductManager productManager;

	@Override
	public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
			throws RuntimeException {
		
		
		HttpServletRequest httpRequest =ThreadContextHolder.getHttpRequest();
		String haveSpec = httpRequest.getParameter("haveSpec");
		
		//add by wui 吴辉屏蔽代码（没开启规格的走GoodsSpecPlugin.java 入口方法
		
//		if(!"1".equals(haveSpec)){ //没有规格的时候才插入进去
//			if(goods.get("goods_id")==null) throw new RuntimeException("商品id不能为空");
//				Integer goodsId = Integer.valueOf(goods.get("goods_id").toString());
//				Integer brandid =null;
//				if(goods.get("brand_id")!=null){
//					brandid = Integer.valueOf( goods.get("brand_id").toString() );
//				}
//				String sn=(String)goods.get("sn");
//				
//				Product product = new Product();
//				product.setGoods_id(goodsId);
//				product.setCost(  Double.valueOf( ""+goods.get("cost") ) );
//				product.setPrice(   Double.valueOf( ""+goods.get("price"))  );
//				product.setSn(sn);
//				product.setStore(Integer.valueOf(""+goods.get("store")));
//				product.setWeight(Double.valueOf( ""+goods.get("weight")));
//				product.setName((String)goods.get("name"));
//				 
//				List<Product> productList = new ArrayList<Product>();
//				productList.add(product);
//				this.productManager.add(productList);
//		}
	}

	@Override
	public void onAfterGoodsEdit(Map goods, HttpServletRequest request) {
		
		HttpServletRequest httpRequest =ThreadContextHolder.getHttpRequest();
		String haveSpec = httpRequest.getParameter("haveSpec");
		if(!"1".equals(haveSpec)){
		
			//add by wui 吴辉屏蔽代码（没开启规格的走GoodsSpecPlugin.java 入口方法
//			if(goods.get("goods_id")==null) throw new RuntimeException("商品id不能为空");
//				Integer goodsId = Integer.valueOf(goods.get("goods_id").toString());
//				Product product = this.productManager.getByGoodsId(goodsId);
//				product.setGoods_id(goodsId);
//				product.setCost(  Double.valueOf( ""+goods.get("cost") ) );
//				product.setPrice(   Double.valueOf( ""+goods.get("price"))  );
//				product.setSn((String)goods.get("sn"));
//				product.setStore(Integer.valueOf(""+goods.get("store")));
//				product.setWeight(Double.valueOf( ""+goods.get("weight")));
//				product.setName((String)goods.get("name"));
//				 
//				List<Product> productList = new ArrayList<Product>();
//				productList.add(product);
//				this.productManager.add(productList);
		}
					
	}

 

	@Override
	public String getAuthor() {
		
		return "kingapex";
	}

	@Override
	public String getId() {
		
		return "general_product";
	}

	@Override
	public String getName() {
		
		return "一般货品插件";
	}

	@Override
	public String getType() {
		
		return "goods";
	}

	@Override
	public String getVersion() {
		
		return "1.0";
	}

	@Override
	public void perform(Object... params) {
		

	}


	public IProductManager getProductManager() {
		return productManager;
	}


	public void setProductManager(IProductManager productManager) {
		this.productManager = productManager;
	}


	@Override
	public void onGoodsDelete(String[] goodsid) {
		 this.productManager.delete(goodsid);
	}

	@Override
	public void register() {
		
	}
	
	

}
