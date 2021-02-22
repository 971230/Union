package com.ztesoft.net.framework.plugin;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.ztesoft.net.mall.core.plugin.goods.BaseAbstractGoodsPlugin;


public class PluginLoader implements BeanPostProcessor {
    private Logger logger = Logger.getLogger(PluginLoader.class);


    @Override
	public Object postProcessAfterInitialization(Object bean, String arg1)
            throws BeansException {
        return bean;
    }

    @Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        //logger.info(" init " + beanName);
    	
        if (bean instanceof AutoRegisterPlugin || bean instanceof BaseAbstractGoodsPlugin) {
            AutoRegisterPlugin plugin = (AutoRegisterPlugin) bean;
            if (plugin.getBundleList() == null) {
               logger.info( plugin.getName() +  " bundlelist is null " );
            } else {
                plugin.register();
                List<IPluginBundle> pluginBundelList = plugin.getBundleList();
                for (IPluginBundle bundle : pluginBundelList) {
                    bundle.registerPlugin(plugin);
                    logger.debug(plugin.getName() + "注册在" + bundle.getClass().getName() + "桩中");
                }
                PluginContext.registerPlugin(plugin);

            }
        }

        return bean;
    }

}
