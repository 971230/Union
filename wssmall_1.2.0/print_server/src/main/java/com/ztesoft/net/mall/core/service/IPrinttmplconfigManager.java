package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.mall.core.model.PrintConfigName;
import com.ztesoft.net.mall.core.model.Printtmplconfig;

/**
 * 打印模配置Manager
 * @作者 MoChunrun
 * @创建日期 2013-11-7 
 * @版本 V 1.0
 */
public interface IPrinttmplconfigManager {

	/**
	 * 查询所有配置
	 * @作者 MoChunrun
	 * @创建日期 2013-11-7 
	 * @return
	 */
	public List<Printtmplconfig> listPrinttmplconfig();
	/**
	 * 按ID查询配置
	 * @作者 MoChunrun
	 * @创建日期 2013-11-7 
	 * @param config_id
	 * @return
	 */
	public Printtmplconfig get(String config_id);
	/**
	 * 查询配置的选项
	 * @作者 MoChunrun
	 * @创建日期 2013-11-7 
	 * @param config_id
	 * @return
	 */
	public List<PrintConfigName> getConfigName(String config_id);
	
}
