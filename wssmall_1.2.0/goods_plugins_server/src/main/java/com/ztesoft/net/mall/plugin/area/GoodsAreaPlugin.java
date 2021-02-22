package com.ztesoft.net.mall.plugin.area;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.mall.core.plugin.goods.BaseAbstractGoodsPlugin;
import com.ztesoft.net.mall.plugin.service.IGoodsAreaPluginManager;

/**
 * 商品地域
 * @author hu.yi
 * @date 2014.01.21
 */
public class GoodsAreaPlugin extends BaseAbstractGoodsPlugin {
    @Resource
    private IGoodsAreaPluginManager goodsAreaPluginManager;

    @Override
    public String onFillGoodsAddInputN(HttpServletRequest request, FreeMarkerPaser freeMarkerPaser) {
    	 List list = goodsAreaPluginManager.qryAreaList();
         freeMarkerPaser.putData("listArea",list);
         return freeMarkerPaser.proessPageContent();
    }

    @Override
    public String onBeforeGoodsAddN(HttpServletRequest request) {
    	
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override  //编辑商品前初始化数据
    public String onFillGoodsEditInput(Map goods, HttpServletRequest request, FreeMarkerPaser freeMarkerPaser) {
    	String goods_id = goods.get("goods_id").toString();
    	List list = goodsAreaPluginManager.qryAreaList();
        freeMarkerPaser.putData("listArea",list);
    	Map<String, Object> map = goodsAreaPluginManager.qryGoodsAreaMap(goods_id);
    	freeMarkerPaser.putData("areaInfo",map);
    	return freeMarkerPaser.proessPageContent();
    }

    @Override //商品添加后触发
    public String onAfterGoodsAddN(Map goods, HttpServletRequest request) {
    	String goods_id = goods.get("goods_id").toString();
    	String param = (String) request.getSession().getAttribute("goodsAreaParam");
		if(!StringUtil.isEmpty(param)){
			String[] params = param.split("_");
			String lan_id = params[0];
			String crm_offer_id = params[1];
			
			//更新crm_offer_id
			this.goodsAreaPluginManager.updateCrmOfferId(goods_id, crm_offer_id);
			// 新增goods_area表数据
			this.goodsAreaPluginManager.insertGoodsArea(goods_id,lan_id);
		}
	    	
	    	
		return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override //修改后触发
    public String onAfterGoodsEditN(Map goods, HttpServletRequest request) {
    	String goods_id = goods.get("goods_id").toString();
    	String param = (String) request.getSession().getAttribute("goodsAreaParam");
		if(!StringUtil.isEmpty(param)){
			// 更新goods_area表数据
			this.goodsAreaPluginManager.updateGoodsArea(goods_id,param);
		}
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String onBeforeGoodsEditN(Map goods, HttpServletRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
