package com.ztesoft.net.mall.plugin.contract;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.mall.core.model.GoodsServiceTypeEnum;
import com.ztesoft.net.mall.core.plugin.goods.BaseAbstractGoodsPlugin;
import com.ztesoft.net.mall.plugin.model.GoodsRel;
import com.ztesoft.net.mall.plugin.service.IGoodsContractManager;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-11-05 14:05
 * To change this template use File | Settings | File Templates.
 * 商品关联合约
 */
public class GoodsContractPlugin extends BaseAbstractGoodsPlugin {
    @Resource
    private IGoodsContractManager goodsContractManager;

    private String KEY="goodsids";

    @Override
    public String onFillGoodsAddInputN(HttpServletRequest request, FreeMarkerPaser freeMarkerPaser) {
        return freeMarkerPaser.proessPageContent();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String onBeforeGoodsAddN(HttpServletRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override  //编辑商品前初始化数据
    public String onFillGoodsEditInput(Map goods, HttpServletRequest request, FreeMarkerPaser freeMarkerPaser) {
        String goods_id = goods.get("goods_id").toString();
        logger.debug("商品ID:" + goods_id);
        List list=goodsContractManager.query(goods_id,GoodsServiceTypeEnum.CONTRACT_OFFER);
        freeMarkerPaser.putData("listGoodsContract",list);
        return freeMarkerPaser.proessPageContent();
    }

    @Override //商品添加后触发
    public String onAfterGoodsAddN(Map goods, HttpServletRequest request) {
        String goods_id = goods.get("goods_id").toString();
        logger.debug("商品ID:" + goods_id);
        String[] ids=request.getParameterValues(KEY);
        if(null!=ids){
        	 GoodsRel goodsRel=null;
            goodsContractManager.delRel(goods_id,GoodsServiceTypeEnum.CONTRACT_OFFER);
            for(String str : ids){
            	goodsRel=new GoodsRel();
            	goodsRel.setA_goods_id(goods_id);
            	goodsRel.setZ_goods_id(str);
            	goodsRel.setRel_type(GoodsServiceTypeEnum.CONTRACT_OFFER+"");
                goodsContractManager.save(goodsRel);
            }
        }
        
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override //修改后触发
    public String onAfterGoodsEditN(Map goods, HttpServletRequest request) {
        String goods_id = goods.get("goods_id").toString();
        logger.debug("商品ID:" + goods_id);
        String[] ids=request.getParameterValues(KEY);
        if(null!=ids){
            GoodsRel goodsRel=null;
            goodsContractManager.delRel(goods_id,GoodsServiceTypeEnum.CONTRACT_OFFER);
            for(String str : ids){
            	goodsRel=new GoodsRel();
            	goodsRel.setA_goods_id(goods_id);
            	goodsRel.setZ_goods_id(str);
            	goodsRel.setRel_type(GoodsServiceTypeEnum.CONTRACT_OFFER+"");
               // service.setGoods_id(goods_id);
                //service.setRel_goods_id(str);
                //service.setRel_code(GoodsServiceTypeEnum.CONTRACT_OFFER);
                goodsContractManager.save(goodsRel);
            }
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String onBeforeGoodsEditN(Map goods, HttpServletRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
