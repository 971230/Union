package com.ztesoft.net.cms.core.service;

import com.ztesoft.net.cms.core.model.DataField;

import java.util.List;

public interface IDataFieldManager {
	
	/**
	 * 添加某字段
	 * @param dataField
	 * @return
	 */
	public String add(DataField dataField );
	
	
	
	
	
	/**
	 * 修改某字段信息
	 * @param dataField
	 */
	public void edit(DataField dataField );
	
	
	/**
	 * 删除某模型的所有字段
	 * @param modelid
	 */
	public void delete(String modelid);
	
	
	
	/**
	 * 查询某模型下的字段列表
	 * @param modelid
	 * @return
	 */
	public List<DataField> list(Integer modelid);

	/**
	 * 查询某模型下的字段列表
	 * @param modelid
	 * @return
	 */
	public List<DataField> list(Integer modelid,String source_from);
	
	/**
	 * 读取某模型可以显示在列表中的字段列表
	 * @param modelid
	 * @return
	 */
	public List<DataField> listIsShow(Integer modelid);
	
	public List<DataField> listIsShow(Integer modelid,String source_from);
	/**
	 * 获取某字段的字段详细
	 * @param fieldid
	 * @return
	 */
	public DataField get(String fieldid);
	
	
	/**
	 * 查询某类别下的字段列表
	 * @param catid
	 * @return
	 */
	public List<DataField> listByCatId(String catid);
	
	public List<DataField> listByCatId(String catid,String source_from);
	
	/**
	 *  更新字段排序
	 * @param ids
	 * @param sorts
	 */
	public void saveSort(String[] ids,Integer sorts[]);
	
}
