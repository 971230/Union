package com.ztesoft.rule.core.ext;

import java.util.List;

import com.ztesoft.rule.core.module.filter.IFactFilter;


/**
 * TODO
 * @author easonwu 
 * @creation Dec 18, 2013
 * 
 */
public interface IFactFilterLoader {
	public List<IFactFilter> loadFactFilters(String planId) ;
}
