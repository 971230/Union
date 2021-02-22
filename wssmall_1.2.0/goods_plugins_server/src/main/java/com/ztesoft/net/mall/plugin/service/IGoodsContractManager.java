package com.ztesoft.net.mall.plugin.service;

import java.util.List;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.GoodsRelAttConvert;
import com.ztesoft.net.mall.core.model.GoodsServiceType;
import com.ztesoft.net.mall.plugin.model.GoodsRel;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-11-05 14:26
 * To change this template use File | Settings | File Templates.
 */
public interface IGoodsContractManager {
	public List<GoodsRelAttConvert> getSetContract(String a_goods_id, String goods_id);
	
    public Page query(String name,int pageIndex,int pageSize,GoodsServiceType arg);

    public List query(String[] ids,GoodsServiceType arg);

    public List query(String id,GoodsServiceType type);

    public String save(GoodsRel arg);

    public void delRel(String goods_id,GoodsServiceType arg);

}
