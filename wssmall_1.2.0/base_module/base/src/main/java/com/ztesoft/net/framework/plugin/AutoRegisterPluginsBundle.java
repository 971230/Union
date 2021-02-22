package com.ztesoft.net.framework.plugin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import vo.GoodsPlugin;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.plugin.IPlugin;

/**
 * 自动注册插件桩
 * 
 * @author apexking
 * 
 */
public abstract class AutoRegisterPluginsBundle  extends BaseSupport implements IPluginBundle {
	protected static final Log loger = LogFactory
			.getLog(AutoRegisterPluginsBundle.class);

	protected List<IPlugin> plugins;

	public static List<GoodsPlugin> goodsPlugins  = new ArrayList<GoodsPlugin>();
	public static boolean loadPlugin = false;
	public static String p_source_from ="";
	@Override
	public void registerPlugin(IPlugin plugin) {
		if (plugins == null) {
			plugins = new ArrayList<IPlugin>();
		}
		
		//add by wui平台插件注册处理
		if(!loadPlugin)
		{
			p_source_from = this.baseDaoSupport.queryForString("select a.cf_value from es_config_info a  where a.cf_id='SOURCE_FROM'");
			loadPlugin = true;
			goodsPlugins = this.baseDaoSupport.queryForList("select p.* from es_goods_plugin p where 1=1", GoodsPlugin.class);
		}
		
		
		//获取平台配置的插件信息
		boolean c_continue = true;
		for (int i = 0; i < goodsPlugins.size(); i++) {
			GoodsPlugin goodsPlugin = goodsPlugins.get(i);
			if(goodsPlugin.getPage_name().equalsIgnoreCase(plugin.getId()) && !goodsPlugin.getSource_from().equals(p_source_from) && "0".equals(goodsPlugin.getDisabled())) //有效商品
				c_continue = false;
		}
		if(!c_continue)
			return;
		if (loger.isDebugEnabled()) {
			loger.debug("为插件桩" + getName() + "注册插件：" + plugin.getName()
					+ "，id：" + plugin.getId() + "，版本：" + plugin.getVersion()
					+ "，作者：" + plugin.getAuthor() + "。");
		}
		plugins.add(plugin);
	}

	abstract public String getName();

}
