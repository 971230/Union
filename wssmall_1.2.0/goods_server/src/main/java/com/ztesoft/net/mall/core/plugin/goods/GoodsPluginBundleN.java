package com.ztesoft.net.mall.core.plugin.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.plugin.AutoRegisterPluginsBundle;
import com.ztesoft.net.framework.plugin.IPlugin;
import com.ztesoft.net.mall.core.params.GoodsExtData;

/**
 * 商品插件桩
 * @author zou.qh
 *
 */
public class GoodsPluginBundleN extends AutoRegisterPluginsBundle {
 
	@Override
	public String getName() {
		return "商品插件桩";
	}

	/**
	 * 激发商品添加前事件
	 * @param goods  
	 */
	public void onBeforeAdd(Map goods,GoodsExtData goodsExtData) {
		try{
			if (plugins != null) {
				for (IPlugin plugin : plugins) {
					if (plugin instanceof IGoodsBeforeAddEventN) {
						if (loger.isDebugEnabled()) {
							loger.debug("调用插件 : " + plugin.getName() + " onBeforeGoodsAdd 开始...");
						}
						IGoodsBeforeAddEventN event = (IGoodsBeforeAddEventN) plugin;
						event.onBeforeGoodsAdd(goods, goodsExtData);
						if (loger.isDebugEnabled()) {
							loger.debug("调用插件 : " + plugin.getName() + " onBeforeGoodsAdd 结束.");
						}
					}else{
						if (loger.isDebugEnabled()) {
							loger.debug(" no,skip...");
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	/**
	 * 激发商品添加完成后事件
	 * @param goods
	 */
	public void onAfterAdd(Map goods,GoodsExtData goodsExtData) {
		try{
			if (plugins != null) {
				for (IPlugin plugin : plugins) {
	
					if (loger.isDebugEnabled()) {
						loger.debug("check plugin : " + plugin.getName()
								+ " is IGoodsAfterAddEvent ?");
					}
	
					if (plugin instanceof IGoodsAfterAddEventN) {
						if (loger.isDebugEnabled()) {
							loger.debug(" yes ,do event...");
						}
						IGoodsAfterAddEventN event = (IGoodsAfterAddEventN) plugin;
						event.onAfterGoodsAdd(goods, goodsExtData);
					}else{
						if (loger.isDebugEnabled()) {
							loger.debug(" no,skip...");
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * 到添加商品页面，为此页填充input数据
	 * 
	 */
	public List<String>   onFillAddInputData() {
		List<String> htmlList = new ArrayList<String>();
		 
		HttpServletRequest request =  ThreadContextHolder.getHttpRequest();
		
		FreeMarkerPaser freeMarkerPaser =FreeMarkerPaser.getInstance();
		freeMarkerPaser.putData("goods",new HashMap(0));
		if (plugins != null) {
			for (IPlugin plugin : plugins) {
				if (plugin instanceof IGoodsFillAddInputDataEventN) {
					IGoodsFillAddInputDataEventN event = (IGoodsFillAddInputDataEventN) plugin;
					freeMarkerPaser.setClz(event.getClass());
					String html = event.onFillGoodsAddInput();
					logger.info(plugin.getName()+"\t"+plugin.getId()+"\t"+plugin.getClass());
					if(plugin.getId()!=null)
						request.setAttribute(plugin.getId(), html);
				}
			}
		}
		return htmlList;
	}
	
	/**
	 * 到商品修改页面前为此页填充input数据
	 *
	 */
	public List<String> onFillEditInputData(Map goods, GoodsExtData goodsExtData){
		List<String> htmlList = new ArrayList<String>();
		HttpServletRequest request =ThreadContextHolder.getHttpRequest();
		FreeMarkerPaser freeMarkerPaser =FreeMarkerPaser.getInstance();
		freeMarkerPaser.putData("goods", goods);
		if (plugins != null) {
			for (IPlugin plugin : plugins) {
				if (plugin instanceof IGoodsFillEditInputDataEventN) {
					IGoodsFillEditInputDataEventN event = (IGoodsFillEditInputDataEventN) plugin;
					freeMarkerPaser.setClz(event.getClass());
					String html = event.onFillGoodsEditInput(goods, goodsExtData);
					if(html!=null){
						request.setAttribute(plugin.getId(), html);
						htmlList.add(html);
					}
				}
			}
		}	
		return htmlList;
	}
	
	
	/**
	 * 激发商品修改更新前事件
	 * @param goods 页面传递的商品数据
	 */
	public void onBeforeEdit(Map goods,GoodsExtData goodsExtData){
		if (plugins != null) {
			for (IPlugin plugin : plugins) {
				if (plugin instanceof IGoodsBeforeEditEventN) {
					if(loger.isDebugEnabled()){
						loger.debug("调用插件["+ plugin.getName() +"] onBeforeGoodsEdit 开始...");
					}
					IGoodsBeforeEditEventN event = (IGoodsBeforeEditEventN) plugin;
					event.onBeforeGoodsEdit(goods, goodsExtData);
					if(loger.isDebugEnabled()){
						loger.debug("调用插件["+ plugin.getName() +"] onBeforeGoodsEdit 结束.");
					}					
				}
				
			}
		}			
	}
	
	/**
	 * 激发商品修改后事件
	 * @param goods 修改后的商品基本数据
	 */
	public void onAfterEdit(Map goods,GoodsExtData goodsExtData){

		if (plugins != null) {
			for (IPlugin plugin : plugins) {
				if (plugin instanceof IGoodsAfterEditEventN) {
					IGoodsAfterEditEventN event = (IGoodsAfterEditEventN) plugin;
					event.onAfterGoodsEdit(goods, goodsExtData);
				}
			}
		}	
		
	}
	
	/**
	 * 激发商品删除事件
	 * @param goodsid
	 */
	public void onGoodsDelete(String[] goodsid){
		if (plugins != null) {
			for (IPlugin plugin : plugins) {
				if (plugin instanceof IGoodsDeleteEventN) {
					IGoodsDeleteEventN event = (IGoodsDeleteEventN) plugin;
					 event.onGoodsDelete(goodsid);
				}
			}
		}
		
	}
	
}
