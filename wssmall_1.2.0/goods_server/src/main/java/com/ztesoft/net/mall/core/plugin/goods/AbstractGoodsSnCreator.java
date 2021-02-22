package com.ztesoft.net.mall.core.plugin.goods;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.framework.plugin.IPlugin;

/**
 * 商品货号生成插件抽像类
 * @author kingapex
 *
 */
public abstract class AbstractGoodsSnCreator extends AutoRegisterPlugin implements IPlugin , IGoodsBeforeAddEvent,IGoodsBeforeEditEvent   {

 
	
	
	@Override
	public void onBeforeGoodsAdd(Map goods, HttpServletRequest request) {
		goods.put( "sn", createSn(goods) );
	}
	
	
	@Override
	public void onBeforeGoodsEdit(Map goods, HttpServletRequest request)  {
	 
		goods.put( "sn", createSn(goods) );
	}


	/**
	 * 生成货号的算法
	 * @param goods
	 * @return
	 */
	 public abstract String createSn(Map goods);
	 
	 
}