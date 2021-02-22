package com.ztesoft.net.mall.plugin.standard.complex;

import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.mall.core.model.GoodsComplex;
import com.ztesoft.net.mall.core.plugin.goods.AbstractGoodsPlugin;

import services.GoodsComplexInf;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GoodsComplexPlugin extends AbstractGoodsPlugin {

    @Resource
    private GoodsComplexInf goodsComplexServ;


    @Override
	public void addTabs() {
        this.addTags(11, "绑定产品");

    }


    @Override
	public String onFillGoodsAddInput(HttpServletRequest request) {
        FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
        freeMarkerPaser.setPageName("complex");
//		Page pageGoods = goodsManager.searchGoods(null,null, null, null, 1, 20);
//		List listGoods = (List)pageGoods.getResult();
//		freeMarkerPaser.putData("listGoods", listGoods);
        return freeMarkerPaser.proessPageContent();
    }


    @Override
	@SuppressWarnings("unchecked")
    public String onFillGoodsEditInput(Map goods, HttpServletRequest request) {
        FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
        String goods_id = goods.get("goods_id").toString();
        List listGoodsComplex = this.goodsComplexServ.listComplex(goods_id);
        freeMarkerPaser.putData("listGoodsComplex", listGoodsComplex);
        //	Page pageGoods = goodsManager.searchGoods(null,null, null, null, 1, 20);
        //	Long pageCount = pageGoods.getTotalPageCount();
        //List listGoods = (List)pageGoods.getResult();
//		freeMarkerPaser.putData("listGoods", listGoods);
//		freeMarkerPaser.putData("pageCount", pageCount);
//		freeMarkerPaser.putData("page", 1);
//		freeMarkerPaser.putData("pageSize", 20);
        freeMarkerPaser.setPageName("complex");
        return freeMarkerPaser.proessPageContent();
    }

    @Override
	public void onBeforeGoodsAdd(Map goods, HttpServletRequest request) {

    }

    @Override
	public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
            throws RuntimeException {
        if (goods.get("goods_id") == null) throw new RuntimeException("商品id不能为空");
        String goodsId = goods.get("goods_id").toString();
        HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
        String[] goods_2 = httpRequest.getParameterValues("complex.goods_2");
        String[] manual = httpRequest.getParameterValues("complex.manual");

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
	public void onAfterGoodsEdit(Map goods, HttpServletRequest request) {
        if (goods.get("goods_id") == null) throw new RuntimeException("商品id不能为空");
        String goodsId = goods.get("goods_id").toString();
        HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
        String[] goods_2 = httpRequest.getParameterValues("complex.goods_2");
        String[] manual = httpRequest.getParameterValues("complex.manual");
//		if(goods_2==null ) return ;
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
	public void onBeforeGoodsEdit(Map goods, HttpServletRequest request) {

    }


    @Override
	public String getAuthor() {
        return "lzf";
    }


    @Override
	public String getId() {
        return "goodscomplex";
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
