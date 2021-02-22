package com.ztesoft.net.app.base.core.service;

import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;

/**
 * 数据库创建者工厂
 * @author kingapex
 *
 */
public abstract class DataBaseCreaterFactory {
	private  DataBaseCreaterFactory(){}

	
	public static IDataBaseCreater getDataBaseCreater(){
		if(EopSetting.DBTYPE.equals("1")){
			return SpringContextHolder.getBean("mysqlDataBaseCreater") ;
		} else if(EopSetting.DBTYPE.equals("2")){
			return SpringContextHolder.getBean("oracleDataBaseCreater") ;
		} else if(EopSetting.DBTYPE.equals("3")){
			return SpringContextHolder.getBean("mssqlDataBaseCreater") ;
		}
		throw new RuntimeException("未知的数据库类型");
				
	}
	

	
	
	
}
