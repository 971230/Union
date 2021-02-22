package com.ztesoft.net.mall.core.plugin.goods;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import vo.GoodsPlugin;

import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.framework.plugin.page.JspPageTabs;
import com.ztesoft.net.mall.core.consts.Consts;

public abstract class BaseAbstractGoodsPlugin extends AutoRegisterPlugin implements IGoodsFillAddInputDataEvent, IGoodsBeforeAddEvent, IGoodsFillEditInputDataEvent, IGoodsAfterAddEvent, IGoodsAfterEditEvent, IGoodsBeforeEditEvent {
    protected Logger logger = Logger.getLogger(this.getClass());

    private String KEY = "goods.stype_id";

    GoodsPlugin goodsPlugin;

    @Override
	@SuppressWarnings("unchecked")
	public void register() {
        String sql = "select t.*,d.stype_id from es_goods_plugin t , es_goods_stype d WHERE d.source_from = '" + Consts.WSSMALL + "' and t.stype_code=d.stype_code and LOWER(plugin_class)  = ? and t.disabled='0'";
        List list = this.baseDaoSupport.queryForList(sql, GoodsPlugin.class, this.getClass().getSimpleName().toLowerCase());
        if(!ListUtil.isEmpty(list)){
        	Object obj = list.get(0);
        	goodsPlugin = (GoodsPlugin)obj;
        }
        if (null == goodsPlugin) {
            sql = "select * from es_goods_plugin where LOWER(plugin_class)  = ? and disabled='0'";
            List list2 =  this.baseDaoSupport.queryForList(sql, GoodsPlugin.class, this.getClass().getSimpleName().toLowerCase());
            if(!ListUtil.isEmpty(list2)){
            	Object obj = list2.get(0);
            	goodsPlugin = (GoodsPlugin)obj;
            }
        }
        //logger.debug("执行sql:"+sql);
        addTabs();
    }

    public void addTabs() {
        if (null!=goodsPlugin && !StringUtils.isEmpty(goodsPlugin.getTag_num()))
            this.addTags(new Integer(goodsPlugin.getTag_num()).intValue(), goodsPlugin.getPlugin_name(), goodsPlugin.getStype_code());
    }


    protected void addTags(Integer id, String name, String... agr) {
        JspPageTabs.addTab("goods", id, name, agr);
    }


    protected void registerPage(String type, String path) {
        //PluginPageContext.addPage(type,path);
    }


    @Override
    @SuppressWarnings("unused")
    public String onFillGoodsAddInput(HttpServletRequest request) {
        if(null==goodsPlugin)return null;

        FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
        freeMarkerPaser.setPageName(goodsPlugin.getPage_name());
        String str = onFillGoodsAddInputN(request, freeMarkerPaser);

        return freeMarkerPaser.proessPageContent();

    }

    public abstract String onFillGoodsAddInputN(HttpServletRequest request, FreeMarkerPaser freeMarkerPaser);


    @SuppressWarnings("unchecked")
    @Override
    public void onBeforeGoodsAdd(Map goods, HttpServletRequest request) {
        // TODO Auto-generated method stub
        onBeforeGoodsAddN(request);

    }

    public abstract String onBeforeGoodsAddN(HttpServletRequest request);


    @SuppressWarnings("unchecked")
    @Override
    public String onFillGoodsEditInput(Map goods, HttpServletRequest request) {
        if(null==goodsPlugin)return null;

        FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
        freeMarkerPaser.setPageName(goodsPlugin.getPage_name());
        onFillGoodsEditInput(goods, request, freeMarkerPaser);

        return freeMarkerPaser.proessPageContent();

    }

    public abstract String onFillGoodsEditInput(Map goods, HttpServletRequest request, FreeMarkerPaser freeMarkerPaser);


    @Override
    public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
            throws RuntimeException {
        if (!this.check(request)) return;

        onAfterGoodsAddN(goods, request);
    }

    public abstract String onAfterGoodsAddN(Map goods, HttpServletRequest request);

    private boolean check(HttpServletRequest request) {
        String style_id = request.getParameter(KEY);
        if (null!=goodsPlugin && StringUtils.isNotBlank(goodsPlugin.getStype_id())) {
            if (!goodsPlugin.getStype_id().equals(style_id)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void onAfterGoodsEdit(Map goods, HttpServletRequest request) {
        if (!this.check(request)) return;

        // TODO Auto-generated method stub
        onAfterGoodsEditN(goods, request);

    }

    public abstract String onAfterGoodsEditN(Map goods, HttpServletRequest request);


    @Override
    public void onBeforeGoodsEdit(Map goods, HttpServletRequest request) {
        // TODO Auto-generated method stub
        onBeforeGoodsEditN(goods, request);
    }


    public abstract String onBeforeGoodsEditN(Map goods, HttpServletRequest request);


    @Override
    public String getAuthor() {
        // TODO Auto-generated method stub
        return null!=goodsPlugin?goodsPlugin.getAuthor():null;
    }


    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return null!=goodsPlugin?goodsPlugin.getPage_name():null;
    }


    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null!=goodsPlugin?goodsPlugin.getPlugin_name():null;
    }


    @Override
    public String getType() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public String getVersion() {
        // TODO Auto-generated method stub
        return null!=goodsPlugin?goodsPlugin.getVersion():null;
    }


    @Override
    public void perform(Object... params) {
        // TODO Auto-generated method stub

    }


}
