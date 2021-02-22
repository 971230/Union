package com.ztesoft.net.mall.core.plugin.goods;

import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.framework.plugin.page.JspPageTabs;

public abstract class AbstractGoodsPlugin extends AutoRegisterPlugin implements  IGoodsFillAddInputDataEvent, IGoodsBeforeAddEvent ,IGoodsFillEditInputDataEvent, IGoodsAfterAddEvent,IGoodsAfterEditEvent,IGoodsBeforeEditEvent {


	
	@Override
	public void register(){
		addTabs();
	}
 
	
	
	public abstract void addTabs();	
	
	
	
	protected void addTags(Integer id,String name){
		JspPageTabs.addTab("goods",id, name);	
	}
	
	
	protected void registerPage(String type,String path){
		//PluginPageContext.addPage(type,path);
	}
	
	
}
