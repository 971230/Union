package com.ztesoft.net.mall.core.utils;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.service.ISqlUtils;

import services.GoodsCatInf;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unchecked")
public class SqlUtils extends BaseSupport implements ISqlUtils {

    @Resource
    private GoodsCatInf goodCatsServ;

    @Override
    public String getGoodsSqlByCatPath(String cat_id) {

        String appendSql = "";
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        String url = request.getServletPath();
        cat_id = UrlUtils.getParamStringValue(url, "cat"); //add by wui 按目录分类

        if (!StringUtil.isEmpty(cat_id)) {
            String path = goodCatsServ.getCatPathById(cat_id);
            appendSql += " and  g.cat_id in(";
            appendSql += "select c.cat_id from "
                    + this.getTableName("goods_cat")
                    + " c where c.cat_path like '" + path + "%')  ";
        }
        return appendSql;
    }

}
