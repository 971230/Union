package com.ztesoft.net.mall.core.plugin.goods;

import java.util.Map;

import com.ztesoft.net.mall.core.params.GoodsExtData;

/**
 * 商品修改前事件
 * 
 * @author zou.qh
 * 
 */
public interface IGoodsBeforeEditEventN {
 
	
	public void onBeforeGoodsEdit(Map goods, GoodsExtData goodsExtData);

}
