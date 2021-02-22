package com.ztesoft.net.service;

import model.EsearchSpec;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.param.inner.SpecDefineQueryInner;
import com.ztesoft.net.param.outer.SpecDefineQueryOuter;

public interface IEsearchSpecDefineManager {
	
	/**
	 * 规格定义查询(支持接口编码 )
	 * @param inner
	 * @return
	 */
	public  SpecDefineQueryOuter querySpecDefineList(SpecDefineQueryInner inner);

	/**
	 * 规格定义查询(支持接口编码 )-分页
	 * @param inner
	 * @return
	 */
	public Page getSpecDefinePage(SpecDefineQueryInner inner, int pageNo, int pageSize);
	
	/**
	 * 规格定义查询
	 * @param inner
	 * @return
	 */
	public  SpecDefineQueryOuter  querySpecDefineListNoCache(SpecDefineQueryInner inner);
	
	/**
	 * 添加关键字规格定义
	 * @param inner
	 */
	public void addSpecDefine(SpecDefineQueryInner inner);
	
	/**
	 * 根据搜索编码和搜索id查找关键字规格定义
	 * @param inner
	 * @return
	 */
	public EsearchSpec findEsearchSpec(SpecDefineQueryInner inner);
	
	/**
	 * 根据搜索编码和搜索id更新关键字规格定义
	 * @param inner
	 */
	public void updateEsearchSpec(SpecDefineQueryInner inner);
}
