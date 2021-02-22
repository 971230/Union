package com.ztesoft.net.cms.core.service;

import com.ztesoft.net.cms.core.model.DataModel;

import java.util.List;

/**
 * 数据模型管理
 * @author kingapex
 * 2010-7-2下午02:25:37
 */
public interface IDataModelManager {
	/**
	 * 添加一个数据模型
	 * @param dataModel
	 */
	public void add(DataModel dataModel,String source_from);
	
	
	/**
	 * 修改一个数据模型
	 * @param dataModel
	 */
	public void edit(DataModel dataModel,String source_from);
	
	
	/**
	 * 删除一个数据模型
	 * @param modelid
	 */
	public void delete(Integer modelid,String source_from);
	
	/**
	 * 读取所有数据模型列表
	 * @return
	 */
	public List<DataModel>  list();
	
	
	/**
	 * 获取一个数据模型
	 * @param modelid
	 * @return
	 */
	public DataModel get(Integer modelid,String source_from);
	
	/**
	 * 检查数据模型是否已经被使用
	 * @param modelid
	 * @return 0:未被使用
	 */
	public int checkIfModelInUse(Integer modelid,String source_from);
	
	
	
	
}
