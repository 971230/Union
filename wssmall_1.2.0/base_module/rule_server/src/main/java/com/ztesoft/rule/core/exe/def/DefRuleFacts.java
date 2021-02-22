package com.ztesoft.rule.core.exe.def;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.rule.core.exe.IRuleFacts;
import com.ztesoft.rule.core.ext.IFactLoader;
import com.ztesoft.rule.core.module.cfg.MidDataConfig;
import com.ztesoft.rule.core.util.ClazzUtil;
import com.ztesoft.rule.core.util.Const;
import com.ztesoft.rule.core.util.LocalBeanFactory;


/**
 * 默认规则事实加载
 * @author easonwu 
 * @creation Dec 13, 2013
 * 
 */
public class DefRuleFacts implements IRuleFacts {

	private IFactLoader factLoader ;

	//【注】提供默认实现
	public IFactLoader getFactLoader() {
		if( factLoader == null )
			factLoader = SpringContextHolder.getBean("defFactLoader");
		
		return factLoader ;
	}


	@Override
	public void setFactLoader(IFactLoader factLoader) {
		this.factLoader = factLoader;
	}
	

	@Override
	public List loadFacts(MidDataConfig cfg , Map partner) {
		Class factClazz = ClazzUtil.getClassByName(cfg.getFact_java_class()) ;

		if(Const.SQL.equals(cfg.getCal_type())){//SQL类型配置
			String sql = cfg.getCal_logic() ;
			List datas = getFactLoader().loadFacts(sql, factClazz, partner, cfg.getPlan()) ; 
			return rtnResult( datas ) ;
		}else{//java类型配置
			IFactLoader factLoader = LocalBeanFactory.createFactLoader(cfg.getCal_logic()) ;
			List datas =  factLoader.loadFacts( factClazz , partner, cfg.getPlan()) ;
			return rtnResult( datas ) ;
		}
	}
	
	private List rtnResult(List datas){
		if(datas == null )
			datas = new ArrayList() ;
		return datas ;
	}

}
