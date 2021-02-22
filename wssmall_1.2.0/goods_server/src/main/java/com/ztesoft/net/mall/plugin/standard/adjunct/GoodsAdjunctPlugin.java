package com.ztesoft.net.mall.plugin.standard.adjunct;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.AdjunctItem;
import com.ztesoft.net.mall.core.model.GoodsAdjunct;
import com.ztesoft.net.mall.core.plugin.goods.AbstractGoodsPlugin;
import com.ztesoft.net.mall.core.service.IGoodsAdjunctManager;
import com.ztesoft.net.mall.core.service.IProductManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GoodsAdjunctPlugin extends AbstractGoodsPlugin implements IGoodsAdjunctPlugin {

    private IGoodsAdjunctManager goodsAdjunctManager;
    private IProductManager productManager;


    @Override
	public void addTabs() {
        this.addTags(5, "配件");

    }


    @Override
	public String onFillGoodsAddInput(HttpServletRequest request) {
        FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();

        freeMarkerPaser.setPageName("adjunct");
        return freeMarkerPaser.proessPageContent();
    }


    @Override
	public void onBeforeGoodsAdd(Map goods, HttpServletRequest request) {

    }


    @Override
	@SuppressWarnings("unchecked")
    public String onFillGoodsEditInput(Map goods, HttpServletRequest request) {
        FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
        String goods_id = goods.get("goods_id").toString();
        List<Map> listGoodsAdjunct = goodsAdjunctManager.list(goods_id);
        for (Map map : listGoodsAdjunct) {
            String json = String.valueOf(map.get("items"));
            if ("".equals(json)) json = "[]";//by liqingyi
            JSONArray jsonArray = JSON.parseArray(json);
            List<AdjunctItem> listAdjunctItem = new ArrayList<AdjunctItem>();
            for (int i = 0; i < jsonArray.size(); i++) {
                listAdjunctItem.add(JSON.parseObject(jsonArray.get(i).toString(), AdjunctItem.class));
            }
            map.put("listAdjunctItem", listAdjunctItem);
        }
        freeMarkerPaser.putData("listGoodsAdjunct", listGoodsAdjunct);

        freeMarkerPaser.setPageName("adjunct");
        return freeMarkerPaser.proessPageContent();
    }


    @Override
	public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
            throws RuntimeException {
        save(goods);
    }


    @Override
	public void onAfterGoodsEdit(Map goods, HttpServletRequest request) {
        save(goods);
    }


    @Override
    public void importAdjust(Map ap) {
        String goodsId = (String) ap.get("goods_id");
        List productIds = (ArrayList) ap.get("productIds");

        HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();


        String[] adjunct_name = new String[]{"配件名称"};
        String[] adjunct_minnum = new String[]{"1"};
        String[] adjunct_maxnum = new String[]{"1000"};
        String[] adjunct_setprice = new String[]{"discount"};
        String[] adjunct_price = new String[]{"1"};
        String[] manual = new String[]{""};


        List<GoodsAdjunct> list = new ArrayList<GoodsAdjunct>();
        if (adjunct_name != null) {
            for (int i = 0; i < adjunct_name.length; i++) {


                GoodsAdjunct adjunct = new GoodsAdjunct();

                adjunct.setGoods_id(goodsId);
                adjunct.setAdjunct_name(adjunct_name[i]);
                adjunct.setMin_num((adjunct_minnum[i] == null ? null : Integer.valueOf(adjunct_minnum[i])));
                adjunct.setSet_price(adjunct_setprice[i]);
                String p_adjunct_price = adjunct_price[i];
                if (StringUtil.isEmpty(p_adjunct_price))
                    p_adjunct_price = "1";
                adjunct.setPrice(Double.valueOf(p_adjunct_price));
                adjunct.setItems(this.getItemJson(adjunct, ManagerUtils.listToStrArr(productIds)));

                list.add(adjunct);
            }
        }

        //保存前 获取旧配件信息
        goodsAdjunctManager.save(goodsId, list);

        //if(excel){ 保存后获取配件信息    旧配件信息+（去除]）+","+(去除[)+现有的 }
        //update
    }


    private void save(Map goods) {
        if (goods.get("goods_id") == null) throw new RuntimeException("商品id不能为空");
        String goodsId = goods.get("goods_id").toString();
        HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();


        String[] adjunct_name = httpRequest.getParameterValues("adjunct.adjunct_name");
        String[] adjunct_minnum = httpRequest.getParameterValues("adjunct.min_num");
        String[] adjunct_maxnum = httpRequest.getParameterValues("adjunct.max_num");
        String[] adjunct_setprice = httpRequest.getParameterValues("pricetype");
        String[] adjunct_price = httpRequest.getParameterValues("adjunct.price");
        String[] manual = httpRequest.getParameterValues("complex.manual");


        List<GoodsAdjunct> list = new ArrayList<GoodsAdjunct>();
        if (adjunct_name != null) {
            for (int i = 0; i < adjunct_name.length; i++) {

                String[] productids = httpRequest.getParameterValues("productid_" + i);

                GoodsAdjunct adjunct = new GoodsAdjunct();
                if (adjunct_minnum[i] == "") {
                    adjunct_minnum[i] = "1";
                }
                if (adjunct_maxnum[i] == "") {
                    adjunct_maxnum[i] = "1000";
                }
                adjunct.setGoods_id(goodsId);
                adjunct.setAdjunct_name(adjunct_name[i]);
                adjunct.setMin_num((adjunct_minnum[i] == null ? null : Integer.valueOf(adjunct_minnum[i])));
                adjunct.setMax_num(adjunct_maxnum[i] == null ? null : Integer.valueOf(adjunct_maxnum[i]));
                adjunct.setSet_price(adjunct_setprice[i]);
                String p_adjunct_price = adjunct_price[i];
                if (StringUtil.isEmpty(p_adjunct_price))
                    p_adjunct_price = "1";
                adjunct.setPrice(Double.valueOf(p_adjunct_price));
                adjunct.setItems(this.getItemJson(adjunct, productids));

                list.add(adjunct);
            }
        }
        goodsAdjunctManager.save(goodsId, list);
    }


    @SuppressWarnings("unchecked")
    private String getItemJson(GoodsAdjunct adjunct, String[] productids) {
        if (productids == null) return null;//by liqingyi
        String[] productidArray = new String[productids.length];
        for (int i = 0; i < productids.length; i++) {
            productidArray[i] = productids[i];

        }
        List<Map> proList = this.productManager.list(productidArray);

        List<AdjunctItem> itemList = new ArrayList<AdjunctItem>();
        for (Map pro : proList) {
            String productid = pro.get("product_id").toString();
            String goodsid = pro.get("goods_id").toString();
            String name = (String) pro.get("name");
            String specs = (String) pro.get("specs");
            Double price = Double.valueOf(pro.get("price").toString());
            AdjunctItem adjunctItem = new AdjunctItem();
            adjunctItem.setProductid(productid);
            adjunctItem.setGoodsid(goodsid);
            adjunctItem.setName(name);
            //adjunctItem.setSpecs(specs);//描述不写表，不展示在后台页面
            if ("minus".equals(adjunct.getSet_price()))
                adjunctItem.setCoupPrice(price - adjunct.getPrice());
            else { //打折
                adjunctItem.setCoupPrice(price - price * adjunct.getPrice());
            }
            adjunctItem.setPrice(price);
            itemList.add(adjunctItem);

        }
        return JSON.toJSONString(itemList);
    }


    @Override
	public void onBeforeGoodsEdit(Map goods, HttpServletRequest request) {

    }


    @Override
	public String getAuthor() {
        return "kingapex";
    }


    @Override
	public String getId() {
        return "goodsadjunct";
    }


    @Override
	public String getName() {
        return "商品配件";
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

    public IGoodsAdjunctManager getGoodsAdjunctManager() {
        return goodsAdjunctManager;
    }

    public void setGoodsAdjunctManager(IGoodsAdjunctManager goodsAdjunctManager) {
        this.goodsAdjunctManager = goodsAdjunctManager;
    }

    public IProductManager getProductManager() {
        return productManager;
    }

    public void setProductManager(IProductManager productManager) {
        this.productManager = productManager;
    }


}
