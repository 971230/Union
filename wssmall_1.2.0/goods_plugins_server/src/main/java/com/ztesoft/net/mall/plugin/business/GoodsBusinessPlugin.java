package com.ztesoft.net.mall.plugin.business;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.mall.core.model.GoodsBusiness;
import com.ztesoft.net.mall.core.plugin.goods.BaseAbstractGoodsPlugin;
import com.ztesoft.net.mall.plugin.service.IGoodsBusPluginManager;

/**
 * 商品外系统扩展属性
 * @author hu.yi
 * @date 2014.01.21
 */
public class GoodsBusinessPlugin extends BaseAbstractGoodsPlugin {
    @Resource
    private IGoodsBusPluginManager goodsBusPluginManager;

    @Override
    public String onFillGoodsAddInputN(HttpServletRequest request, FreeMarkerPaser freeMarkerPaser) {
    	List list = goodsBusPluginManager.qryBusType();
    	freeMarkerPaser.putData("listBusType",list); 
    	return freeMarkerPaser.proessPageContent();
    }

    @Override
    public String onBeforeGoodsAddN(HttpServletRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override  //编辑商品前初始化数据
    public String onFillGoodsEditInput(Map goods, HttpServletRequest request, FreeMarkerPaser freeMarkerPaser) {
    	String goods_id = goods.get("goods_id").toString();
    	List list = goodsBusPluginManager.qryBusType();
    	freeMarkerPaser.putData("listBusType",list); 
    	GoodsBusiness goodsBusiness = goodsBusPluginManager.getGoodsBusiness(goods_id);
    	freeMarkerPaser.putData("goodsBusiness",goodsBusiness); 
    	return freeMarkerPaser.proessPageContent();
    }

    @Override //商品添加后触发
    public String onAfterGoodsAddN(Map goods, HttpServletRequest request) {
    	String goods_id = goods.get("goods_id").toString();
    	GoodsBusiness goodsBusiness = (GoodsBusiness) request.getSession().getAttribute("goodsBusiness");
		if(goodsBusiness != null){
			// 新增es_goods_business表数据
			goodsBusiness.setGoods_id(goods_id);
			goodsBusPluginManager.saveGoodsBusiness(goodsBusiness);
		}
	    	
	    	
		return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override //修改后触发
    public String onAfterGoodsEditN(Map goods, HttpServletRequest request) {
    	String goods_id = goods.get("goods_id").toString();
    	GoodsBusiness goodsBusiness = (GoodsBusiness) request.getSession().getAttribute("goodsBusiness");
		if(goodsBusiness != null){
			// 更新es_goods_business表数据
			goodsBusiness.setGoods_id(goods_id);
			goodsBusPluginManager.updateGoodsBusiness(goodsBusiness);
		}
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String onBeforeGoodsEditN(Map goods, HttpServletRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
