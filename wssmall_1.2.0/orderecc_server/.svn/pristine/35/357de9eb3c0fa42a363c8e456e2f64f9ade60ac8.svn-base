package com.ztesoft.check.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.check.model.Biz;
import com.ztesoft.check.model.BizCheckFilter;
import com.ztesoft.check.model.BizRequirements;

/**
 * 过滤校验管理
 * @author duanshaochu
 *
 */
public interface IBizCheckFilterManager {

	/**
	 * 过虑校验列表
	 */
	public List<BizCheckFilter> queryFilterListByParam(Map<String,String> params);
	
	/**
	 * 修改过虑项目
	 */
	public void modifyFilterItem(BizCheckFilter filterItem);
	
	public List bizFactorCfgList();
	
	/**
	 * 新增过滤项目
	 * @param filterDto
	 * @param list
	 */
	public void saveCheckFilter(Biz biz, ArrayList<BizRequirements> list);
	
}
