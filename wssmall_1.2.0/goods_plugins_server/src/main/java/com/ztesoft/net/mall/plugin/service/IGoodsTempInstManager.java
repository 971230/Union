package com.ztesoft.net.mall.plugin.service;

import java.util.List;

import com.ztesoft.net.mall.core.model.GoodsServiceType;
import com.ztesoft.net.mall.plugin.model.GoodsTempInst;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-11-04 11:39
 * To change this template use File | Settings | File Templates.
 */
public interface IGoodsTempInstManager {
    public boolean save(GoodsTempInst arg);
    public List querys(String id,GoodsServiceType type);
    public List querys(String[] ids,GoodsServiceType arg);

}
