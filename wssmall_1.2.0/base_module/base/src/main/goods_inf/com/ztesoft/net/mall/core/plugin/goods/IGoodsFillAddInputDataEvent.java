package com.ztesoft.net.mall.core.plugin.goods;

import javax.servlet.http.HttpServletRequest;

/**
 * 到商品添加前提供填充事件
 * @author kingapex
 *
 */
public  interface IGoodsFillAddInputDataEvent {
	 
	 public String onFillGoodsAddInput(HttpServletRequest request);
	
	
} 
