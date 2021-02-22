package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.mall.core.model.DlyCenter;

/**
 * 发货中心
 * 
 * @author lzf<br/>
 *         2010-4-30上午10:12:50<br/>
 *         version 1.0
 */
public interface IDlyCenterManager {

	public List<DlyCenter> list();
	
	public DlyCenter get(String dly_center_id);

	public void add(DlyCenter dlyCenter);

	public void edit(DlyCenter dlyCenter);

	public void delete(String[] id);

}
