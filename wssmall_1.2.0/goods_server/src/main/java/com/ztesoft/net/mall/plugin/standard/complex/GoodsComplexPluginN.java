package com.ztesoft.net.mall.plugin.standard.complex;

import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.mall.core.model.GoodsComplex;
import com.ztesoft.net.mall.core.params.GoodsExtData;
import com.ztesoft.net.mall.core.plugin.goods.AbstractGoodsPluginN;

import services.GoodsComplexInf;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GoodsComplexPluginN extends AbstractGoodsPluginN {

    @Resource
    private GoodsComplexInf goodsComplexServ;
    @Override
	public void addTabs() {
    }

    @Override
	public String onFillGoodsAddInput() {
        FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
        HttpServletRequest request =  ThreadContextHolder.getHttpRequest();
        freeMarkerPaser.putData("ctx", request.getContextPath());
        freeMarkerPaser.putData("have_complex", "0");
        freeMarkerPaser.setPageName("complexn");
        return freeMarkerPaser.proessPageContent();
    }

    @Override
	@SuppressWarnings("unchecked")
    public String onFillGoodsEditInput(Map goods, GoodsExtData goodsExtData) {
        FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
        String goods_id = goods.get("goods_id").toString();
        String have_complex = "1";
        List listGoodsComplex = this.goodsComplexServ.listComplex(goods_id);
        if(listGoodsComplex.size()==0){
        	listGoodsComplex = null;
        	have_complex = "0";
        }
        freeMarkerPaser.putData("have_complex", have_complex);
        freeMarkerPaser.putData("listGoodsComplex", listGoodsComplex);
        HttpServletRequest request =  ThreadContextHolder.getHttpRequest();
        freeMarkerPaser.putData("ctx", request.getContextPath());
        freeMarkerPaser.setPageName("complexn");
        return freeMarkerPaser.proessPageContent();
    }

    @Override
	public void onBeforeGoodsAdd(Map goods, GoodsExtData goodsExtData) {

    }

    @Override
	public void onAfterGoodsAdd(Map goods, GoodsExtData goodsExtData)
            throws RuntimeException {
        if (goods.get("goods_id") == null) throw new RuntimeException("商品id不能为空");
        String goodsId = goods.get("goods_id").toString();
        String[] goods_2 = goodsExtData.getComplex_goods_ids();
        String[] manual = goodsExtData.getManual();

        if (goods_2 == null) return;

        List<GoodsComplex> list = new ArrayList<GoodsComplex>();
        for (int i = 0; i < goods_2.length; i++) {
            GoodsComplex complex = new GoodsComplex();
            complex.setGoods_1(goodsId);
            complex.setGoods_2(goods_2[i]);
            complex.setManual(manual[i]);
            complex.setRate(100);
            list.add(complex);
        }
        this.goodsComplexServ.globalGoodsComplex(goodsId, list);
    }

    @Override
	public void onAfterGoodsEdit(Map goods, GoodsExtData goodsExtData) {
        if (goods.get("goods_id") == null) throw new RuntimeException("商品id不能为空");
        String goodsId = goods.get("goods_id").toString();
        String[] goods_2 = goodsExtData.getComplex_goods_ids();
        String[] manual = goodsExtData.getManual();
        List<GoodsComplex> list = new ArrayList<GoodsComplex>();
        if (goods_2 != null && goods_2.length > 0) {
            for (int i = 0; i < goods_2.length; i++) {
                GoodsComplex complex = new GoodsComplex();
                complex.setGoods_1(goodsId);
                complex.setGoods_2(goods_2[i]);
                complex.setManual(manual[i]);
                complex.setRate(100);
                list.add(complex);
            }
        }
        this.goodsComplexServ.globalGoodsComplex(goodsId, list);
    }

    @Override
	public void onBeforeGoodsEdit(Map goods, GoodsExtData goodsExtData) {

    }

    @Override
	public String getAuthor() {
        return "zou.qh";
    }


    @Override
	public String getId() {
        return "goodscomplexN";
    }


    @Override
	public String getName() {
        return "相关商品插件";
    }


    @Override
	public String getType() {
        return "";
    }


    @Override
	public String getVersion() {
        return "1.0";
    }


    @Override
	public void perform(Object... params) {

    }


}
