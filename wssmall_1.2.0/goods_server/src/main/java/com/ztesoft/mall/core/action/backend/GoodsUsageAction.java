package com.ztesoft.mall.core.action.backend;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.GoodsUsage;
import com.ztesoft.net.mall.core.service.IGoodsUsageManager;

public class GoodsUsageAction extends WWAction {
	protected GoodsUsage goodsUsage;
	protected IGoodsUsageManager goodsUsageManager;
	private String goods_id;
	
	public String editByGoodsid(){
		try {
			goodsUsageManager.edit(goodsUsage,goods_id);
			this.json = "{result:1,message:'修改成功'}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:0,message:\"修改失败：" + e.getMessage() + "\"}";
		}
		return WWAction.JSON_MESSAGE;
		
	}
	
	public GoodsUsage getGoodsUsage() {
		return goodsUsage;
	}
	public void setGoodsUsage(GoodsUsage goodsUsage) {
		this.goodsUsage = goodsUsage;
	}
	public IGoodsUsageManager getGoodsUsageManager() {
		return goodsUsageManager;
	}
	public void setGoodsUsageManager(IGoodsUsageManager goodsUsageManager) {
		this.goodsUsageManager = goodsUsageManager;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	
	
}
