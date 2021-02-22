package com.ztesoft.net.cms.core.plugin;

import com.ztesoft.net.cms.core.model.DataField;
import com.ztesoft.net.framework.plugin.AutoRegisterPluginsBundle;
import com.ztesoft.net.framework.plugin.IPlugin;

import java.util.List;
import java.util.Map;

/**
 * 文章插件桩
 * 
 * @author kingapex 2010-7-5下午02:29:17
 */
public class ArticlePluginBundle extends AutoRegisterPluginsBundle {

	@Override
	public String getName() {

		return "文章插件桩";
	}

	
	/**
	 * 根据DataField获取
	 * @param field
	 * @return
	 */
	public AbstractFieldPlugin getFieldPlugin(DataField field){
		if (plugins != null) {
			for (IPlugin plugin : plugins) {
				if (plugin instanceof AbstractFieldPlugin) {
					AbstractFieldPlugin fieldPlugin = (AbstractFieldPlugin) plugin;
					if(fieldPlugin.getId().equals(field.getShow_form())){
						return fieldPlugin;
					}
				}  
				
			}
		}
		
		return null;
	}
	
	
	public void onSave(Map article,DataField field) {
		try {

			if (plugins != null) {
				for (IPlugin plugin : plugins) {
					if (plugin instanceof AbstractFieldPlugin) {
						AbstractFieldPlugin fieldPlugin = (AbstractFieldPlugin) plugin;
						if(fieldPlugin.getId().equals(field.getShow_form())){
							fieldPlugin.onSave(article ,field);
						}
					}  
					
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onSave(Map article) {
		try {

			if (plugins != null) {
				for (IPlugin plugin : plugins) {
					  if (plugin instanceof IDataSaveEvent){
						IDataSaveEvent dataSaveEvent =(IDataSaveEvent) plugin;
						dataSaveEvent.onSave(article);
					}
					
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**数据删除事件 lzf add 2010-12-01
	 * @param catid
	 * @param articleid
	 */
	public void onDelete(String catid,String articleid,String source_from){
		try{
			if (plugins != null) {
				for (IPlugin plugin : plugins) {
					  if (plugin instanceof IDataDeleteEvent){
						  IDataDeleteEvent dataDeleteEvent = (IDataDeleteEvent) plugin;
						  dataDeleteEvent.onDelete(catid, articleid,source_from);
					}
					
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String onDisplay(DataField field,Object value){
		try {

			if (plugins != null) {
				for (IPlugin plugin : plugins) {
					if (plugin instanceof AbstractFieldPlugin) {
						AbstractFieldPlugin fieldPlugin = (AbstractFieldPlugin) plugin;
						if(fieldPlugin.getId().equals(field.getShow_form())){
							return  fieldPlugin.onDisplay(field, value);
						}
					}
				}
			}

			return "输入项"+ field.getShow_form() +"未找到插件解析";
		} catch (Exception e) {
			e.printStackTrace();
			return "输入项"+field.getShow_form()+"发生错误";
		}		
	}
	
	public IPlugin findPlugin(String id){
		if (plugins != null) {
			for (IPlugin plugin : plugins) {
				if(id.equals(plugin.getId())){
					return plugin;
				}
			}
		}
		return null;
	}
	
	public List getPlugins(){
		return this.plugins;
	}
}
