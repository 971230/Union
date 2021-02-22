package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.mall.core.model.SpecValue;
import com.ztesoft.net.mall.core.model.Specification;

import java.util.List;
import java.util.Map;

/**
 * 规格管理接口
 * @author kingapex
 *2010-3-6下午03:48:59
 */
public interface ISpecManager {
	
	
	/**
	 * 检测规格是否被使用
	 * @param ids
	 * @return
	 */
	public boolean checkUsed(String[] ids);
	
	
	
	
	/**
	 * 读取规格列表
	 * @return
	 */
	public List list();
	
	/**
	 * 添加一种规格，同时添加其规格值
	 * @param spec
	 * @param valueList
	 */
	public void add(Specification spec,List<SpecValue> valueList);
	
	
	/**
	 * 修改一个规格，同时修改其规格值
	 * @param spec
	 * @param valueList
	 */
	public void edit(Specification spec,List<SpecValue> valueList);
	
	
	/**
	 * 删除某组规格
	 */
	public void delete(String[] idArray);
	

	/**
	 * 读取一个规格详细
	 * @param spec_id
	 * @return
	 */
	public Map get(String spec_id);
	
	
}
