package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.framework.database.Page;

/**
 * 缺货登记
 * 
 * @author lzf<br/>
 *         2010-3-19 下午02:30:40<br/>
 *         version 1.0<br/>
 */
public interface IGnotifyManager {

	/**
	 * 分页显示缺货登记
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page pageGnotify(int pageNo, int pageSize);

	/**
	 * 删除登记
	 * 
	 * @param gnotify_id
	 */
	public void deleteGnotify(String gnotify_id);
	
	/**
	 * 登记
	 * @param productid
	 */
	public void addGnotify(String goodsid);

}
