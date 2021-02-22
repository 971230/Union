package com.ztesoft.net.mall.plugin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.GoodsServiceType;
import com.ztesoft.net.mall.plugin.model.GoodsTempInst;
import com.ztesoft.net.mall.plugin.service.IGoodsTempInstManager;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-11-04 12:12
 * To change this template use File | Settings | File Templates.
 */
public class GoodsTempInstManager extends BaseSupport implements IGoodsTempInstManager {
    @Override
    public boolean save(GoodsTempInst arg) {
        if (null == arg) return false;

        daoSupport.insert("es_temp_inst", arg);
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

	@Override
	public List querys(String[] ids, GoodsServiceType arg) {
		        if (null == ids) return new ArrayList();

		        String sql = "select * from es_t";
		        List param = new ArrayList();
		        param.add(String.valueOf(arg));
		        StringBuilder builder = new StringBuilder(1000);
		        builder.append(sql);
		        builder.append(" and g.goods_id in(");
		        for (String str : ids) {
		            builder.append(str);
		            builder.append(",");
		        }
		        builder.deleteCharAt(builder.length() - 1);
		        builder.append(")");
		        List list = null;
		        try {
		            list = this.daoSupport.queryForList(builder.toString(), param.toArray());
		        } catch (Exception e) {
		            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		        }
		        return list;  //To change body of implemented methods use File | Settings | File Templates.
	}
	@Override
    public List querys(String id, GoodsServiceType type) {
        if (StringUtils.isEmpty(id)) return null;

        String sql = "select* from es_temp_inst"; 
               
        List list = daoSupport.queryForList(sql, id);
        return list;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
