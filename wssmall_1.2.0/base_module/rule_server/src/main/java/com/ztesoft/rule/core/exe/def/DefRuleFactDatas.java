package com.ztesoft.rule.core.exe.def;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.rule.core.exe.IRuleFactDatas;
import com.ztesoft.rule.core.ext.DefFactDataConfigLoader;
import com.ztesoft.rule.core.ext.IFactDataLoader;
import com.ztesoft.rule.core.module.cache.FactCacheDatas;
import com.ztesoft.rule.core.util.Const;
import com.ztesoft.rule.core.util.LocalBeanFactory;


/**
 * 方案涉及规则所需的cache类型数据
 * @author easonwu 
 * @creation Dec 13, 2013
 * 
 */
public class DefRuleFactDatas implements IRuleFactDatas {

	private DefFactDataConfigLoader defFactDataConfigLoader ;

	private IFactDataLoader factDataLoader ;
	
	@Override
	public FactCacheDatas loadFactDatas(String planCode) {
		List<Map<String,String>> cfgs = defFactDataConfigLoader.loadFactDataConfigs(planCode);
		
		//FactCacheDatas
		FactCacheDatas factCacheDatas = new FactCacheDatas() ;
		factCacheDatas.setPlanCode(planCode) ;
		
		//加载每个配置对应的数据
		for(Map<String,String> cfg : cfgs){//fact_code data_load_mode data_load_impl
			
			//fact_code setter
			FactCacheDatas.FactCacheData factCacheData = new FactCacheDatas.FactCacheData() ;
			factCacheDatas.addFactCacheData(factCacheData) ;
			factCacheData.setFactCode(cfg.get("fact_code")) ;
			
			List<Map> datas = null ;
			
			if(Const.SQL.equals(cfg.get("data_load_mode"))){//SQL类型配置
				String sql = cfg.get("data_load_impl") ;
				datas =  factDataLoader.loadFactDatas(sql) ;
			}else{//java类型配置
				IFactDataLoader factLoader = LocalBeanFactory.createFactDataLoader(cfg.get("data_load_impl") ) ;
				datas =   factLoader.loadFactDatas();
			}
			//设置结果值
			factCacheData.setDatas(datas) ;
			
		}
		
		return factCacheDatas ;
	}
	
	

	//【注】提供默认实现
	public IFactDataLoader getFactDataLoader() {
		if( factDataLoader == null )
			factDataLoader = SpringContextHolder.getBean("defFactDataLoader");
		
		return factDataLoader ;
	}

	
	public void setFactDataLoader(IFactDataLoader factDataLoader) {
		this.factDataLoader = factDataLoader;
	}

	public DefFactDataConfigLoader getDefFactDataConfigLoader() {
		return defFactDataConfigLoader;
	}

	public void setDefFactDataConfigLoader(
			DefFactDataConfigLoader defFactDataConfigLoader) {
		this.defFactDataConfigLoader = defFactDataConfigLoader;
	}



	@Override
	public void expireCache(String planCode) {
		defFactDataConfigLoader.expireCache(planCode) ;
	}


}
