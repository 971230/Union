package com.ztesoft.net.mall.plugin.payway;

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
 * Date: 2013-11-07 10:13
 * To change this template use File | Settings | File Templates.
 * 终端关联购买方式
 */
public class GoodsBuyWayPlugin extends BaseAbstractGoodsPlugin {
    @Resource
    private IGoodsContractManager goodsContractManager;

    private String KEY="goods_buyWays";

    @Override
    public String onFillGoodsAddInputN(HttpServletRequest request, FreeMarkerPaser freeMarkerPaser) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String onBeforeGoodsAddN(HttpServletRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String onFillGoodsEditInput(Map goods, HttpServletRequest request, FreeMarkerPaser freeMarkerPaser) {
        String goods_id = goods.get("goods_id").toString();
        logger.debug("商品ID:" + goods_id);
        List list=goodsContractManager.query(goods_id, GoodsServiceTypeEnum.TERMINAL_PLAN);
        freeMarkerPaser.putData("listGoodsBuyWay",list);
        freeMarkerPaser.putData("editGoodsId",goods_id);
        return freeMarkerPaser.proessPageContent();
    }

    @Override
    public String onAfterGoodsAddN(Map goods, HttpServletRequest request) {
        String goods_id = goods.get("goods_id").toString();
        logger.debug("商品ID:" + goods_id);
        String[] ids=request.getParameterValues(KEY);
        String[] rel_contract_inst = request.getParameterValues("rel_contract_inst");
        if(null!=ids){
        	GoodsRel goodsRel=null;
            goodsContractManager.delRel(goods_id,GoodsServiceTypeEnum.TERMINAL_PLAN);
         //   for(String str : ids){
            for(int i= 0;i<ids.length;i++){
            	goodsRel=new GoodsRel();
            	goodsRel.setA_goods_id(goods_id);
            	goodsRel.setZ_goods_id(ids[i]);
            	goodsRel.setRel_contract_inst(rel_contract_inst[i]);
            	goodsRel.setRel_type(GoodsServiceTypeEnum.TERMINAL_PLAN+"");
                goodsContractManager.save(goodsRel);
            }
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String onAfterGoodsEditN(Map goods, HttpServletRequest request) {
        String goods_id = goods.get("goods_id").toString();
        
        logger.debug("商品ID:" + goods_id);
        String[] ids=request.getParameterValues(KEY);
        String[] rel_contract_inst = request.getParameterValues("rel_contract_inst");
        if(null!=ids){
        	GoodsRel goodsRel=null;
            goodsContractManager.delRel(goods_id,GoodsServiceTypeEnum.TERMINAL_PLAN);
         //   for(String str : ids){
            for(int i= 0;i<ids.length;i++){
            	goodsRel=new GoodsRel();
            	goodsRel.setA_goods_id(goods_id);
            	goodsRel.setZ_goods_id(ids[i]);
            	goodsRel.setRel_contract_inst(rel_contract_inst[i]);
            	goodsRel.setRel_type(GoodsServiceTypeEnum.TERMINAL_PLAN+"");
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
