package com.ztesoft.net.mall.core.service;


import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.database.Page;


/**
 * 货品管理接口
 * 
 * @author cc
 * 
 */
public interface IProductoManager {
	void liberacion(String ids,Map params);
	List getEstados();
	Page getFormList(int pageNo, int pageSize,Map<String, String> params);
}
