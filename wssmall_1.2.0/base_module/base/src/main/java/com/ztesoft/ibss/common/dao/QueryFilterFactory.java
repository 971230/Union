package com.ztesoft.ibss.common.dao;

import com.ztesoft.common.dao.PageFilterJdbcImpl;
import com.ztesoft.common.dao.PageFilterOracleImpl;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.ibss.common.util.CrmConstants;


public class QueryFilterFactory {

	public static QueryFilter getPageQueryFilter(PageModel pageModel) {

		if ("informix".equalsIgnoreCase(CrmConstants.CRM_DATABASE_TYPE)) {
			return new PageFilterJdbcImpl(pageModel);
		} else if ("oracle".equalsIgnoreCase(CrmConstants.CRM_DATABASE_TYPE)) {
			return new PageFilterOracleImpl(pageModel);
		}
		
		//default use JdbcImpl
		return new PageFilterJdbcImpl(pageModel);
	}
}
