package com.ztesoft.net.mall.plugin.property;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.mall.core.model.GoodsServiceTypeEnum;
import com.ztesoft.net.mall.core.plugin.goods.BaseAbstractGoodsPlugin;
import com.ztesoft.net.mall.plugin.model.GoodsAttrDef;
import com.ztesoft.net.mall.plugin.service.IGoodsAttrDefManager;
import com.ztesoft.net.mall.plugin.service.IGoodsTempInstManager;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-11-04 11:15
 * To change this template use File | Settings | File Templates.
 */
public class GoodsPropertyPlugin extends BaseAbstractGoodsPlugin {

    @Resource
    private IGoodsAttrDefManager goodsAttrDefManager;

    @Resource
    private IGoodsTempInstManager goodsTempInstManager;
    private String KEY="stypesids";
    @Override
    public String onFillGoodsAddInputN(HttpServletRequest request, FreeMarkerPaser freeMarkerPaser) {
    	freeMarkerPaser.putData("editGoodsId","");
    	return freeMarkerPaser.proessPageContent();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String onBeforeGoodsAddN(HttpServletRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
    @Override
    public String onFillGoodsEditInput(Map goods, HttpServletRequest request, FreeMarkerPaser freeMarkerPaser) {
    	 String goods_id = goods.get("goods_id").toString();
         logger.debug("商品ID:" + goods_id);
         List list=goodsAttrDefManager.query(goods_id,GoodsServiceTypeEnum.PROPERTY);
         List lists=goodsAttrDefManager.querys(goods_id,GoodsServiceTypeEnum.MODULE);
         freeMarkerPaser.putData("listGoodsProperty",list);
         freeMarkerPaser.putData("listGoodsModule",lists);
         freeMarkerPaser.putData("editGoodsId",goods_id);
         return freeMarkerPaser.proessPageContent();    
    }
    

    @Override  //添加商品属性
    public String onAfterGoodsAddN(Map property, HttpServletRequest request) {
    	 String goods_id = property.get("goods_id").toString();
         logger.debug("商品属性ID:" + goods_id);
         String[] ids=request.getParameterValues(KEY);
         if(null!=ids){
        	 GoodsAttrDef type=null;
        	 goodsAttrDefManager.delRel(goods_id,GoodsServiceTypeEnum.PROPERTY);
             for(String str : ids){
                 type=new GoodsAttrDef();
                 type.setGoods_id(goods_id);
                 type.setAttr_spec_id(str);
                 type.setAttr_spec_type(str);
                 type.setDefault_value(str);
                 type.setDefault_value_desc(str);
                 type.setField_desc(str);
                 type.setO_field_name(str);
                 type.setSub_attr_spec_type(str);
                 type.setSource_from(str);
                 type.setOwner_table_fields(str);
                 goodsAttrDefManager.save(type);
             }
         }
         return null;  
    }

    @Override   //修改商品属性
    public String onAfterGoodsEditN(Map goods, HttpServletRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String onBeforeGoodsEditN(Map goods, HttpServletRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
