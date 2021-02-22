package com.ztesoft.net.framework.database.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.model.PageCountCacheModel;
import com.ztesoft.util.CacheUtils;

/**
 * 数据库分页查询集合与总数异步进行的DAO。<br>
 * 当查询总数时，只记录查询数据的sql，将总数查询的sql记录至缓存，等待异步来查询<br>
 * 
 * @作者 Z
 * @创建日期 2015年12月24日 下午12:52:50
 * @版本 V 1.0
 */
@SuppressWarnings("unchecked")
public class JdbcDaoSupportForAsyCount<T> extends JdbcDaoSupport<T> {
	
	@Override
	public int queryForInt(String sql, Object... args) {

		addPageCountToCached(sql, args);
		
		return -1;

	}	
	
	@SuppressWarnings("rawtypes")
	@Override
	public int queryForIntByMap(String sql, Map args) {
		
		addPageCountToCached(sql, args);
		
		return -1;
	}
	
	/**
	 * 分页查询（总数SQL记录在缓存中）
	 * 
	 * @作者 Z
	 * @创建日期 2015年12月24日 
	 * @return
	 */
	private void addPageCountToCached(String countSql,Object... args) {

		HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
		if (httpRequest != null) {
			final String countSqlKey = httpRequest.getSession().getId() + "_" + httpRequest.getServletPath();
			final int expireTime = 60 * 5;	//5分钟失效
			
			PageCountCacheModel cacheModel = new PageCountCacheModel();
			cacheModel.setCountSql(countSql);
			cacheModel.setVarArgs(args);
			CacheUtils.addCache(countSqlKey, cacheModel, expireTime);
		}
	}
	
}
