package com.ztesoft.net.app.base.core.service.impl;

import java.util.Map;

import com.ztesoft.net.app.base.core.service.IPageInfoManager;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.model.PageCountCacheModel;
import com.ztesoft.util.CacheUtils;

/**
 * 分页信息管理
 * 
 * @作者 Z
 * @创建日期 2015年12月24日 上午10:20:53
 * @版本 V 1.0
 */
@SuppressWarnings("rawtypes")
public class PageInfoManger extends BaseSupport implements IPageInfoManager {

	@Override
	public int getPageCountInfo(String sessionId, String url) {
		final String countSqlKey = sessionId + "_" + url;
		PageCountCacheModel cacheModel = CacheUtils.getCache(countSqlKey);
		
		if (cacheModel == null) {
			return -1;
		}
		
		final String countSql = cacheModel.getCountSql();
		final Object[] args = cacheModel.getVarArgs();
		
		if (args!=null &&args.length==1 && args[0] instanceof Map) {
			return this.daoSupport.queryForIntByMap(countSql, (Map)args[0]);
		}
		
		return this.daoSupport.queryForInt(countSql, args);
	}

	
}
