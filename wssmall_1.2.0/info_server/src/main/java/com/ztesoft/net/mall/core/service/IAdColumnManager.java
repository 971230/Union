package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.app.base.core.model.AdColumn;
import com.ztesoft.net.framework.database.Page;

import java.util.List;


/**
 * 广告位接口
 * 
 * @author 李志富 lzf<br/>
 *         2010-2-4 下午03:26:05<br/>
 *         version 1.0<br/>
 * <br/>
 */
public interface IAdColumnManager {

	/**
	 * 广告位信息修改
	 * 
	 * @param PageAdColumn
	 */
	public void updateAdvc(AdColumn adColumn);

	/**
	 * 获取广告位详细
	 * 
	 * @param acid
	 * @return
	 */
	public AdColumn getADcolumnDetail(String acid);

	/**
	 * 广告位新增
	 * 
	 * @param adColumn
	 */
	public String addAdvc(AdColumn adColumn);

	/**
	 * 删除广告位
	 * 
	 * @param acid
	 */
	public void delAdcs(String ids);
	
	
	/**
	 * 增加广告位置的关联
	 * @param acid
	 * @param catid
	 * @param position
	 */
	public void addAdcolumnCat(String acid,String catid,String position);

	/**
	 * 分页读取广告位列表
	 * 
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Page pageAdvPos(int page, int pageSize);

	/**
	 * 读取所有广告位列表
	 * 
	 * @return
	 */
	public List listAllAdvPos();
	
	public AdColumn getADcolumnDetail(String catid,String position);
	
	public List listResol();
	
}
