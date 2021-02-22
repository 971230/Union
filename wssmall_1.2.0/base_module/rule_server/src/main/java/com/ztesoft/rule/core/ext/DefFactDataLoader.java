package com.ztesoft.rule.core.ext;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.rule.core.util.ServiceException;


/**
 * 默认参与LHS条件判断部分相关信息加载器实现
 * 功能：
 * 1.提供SQL loadFactDatas(String sql)【已实现】
 *   与javabean两种模式【子类实现】
 *   
 * @author easonwu 
 * @creation Dec 13, 2013
 * 
 */

public class DefFactDataLoader extends BaseSupport  implements IFactDataLoader {

	@Override
	public List<Map> loadFactDatas(String sql) {
		return baseDaoSupport.queryForList(sql);
	}

	
	@Override
	/**
	 * 
	 * 提供给javabean模式覆盖实现
	 * 
	 */
	public List<Map> loadFactDatas() {
		throw new ServiceException("DefFactDataLoader unsupport loadFactDatas() invoke") ;
	}
	
}
