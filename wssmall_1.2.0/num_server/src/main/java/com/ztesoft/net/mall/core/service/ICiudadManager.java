package com.ztesoft.net.mall.core.service;

import java.io.File;
import java.util.Map;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Ciudad;


/**
 * 地域管理接口
 * 
 * @author cc
 * 
 */
public interface ICiudadManager {
	
	void del(String id);
	void saveOrUpdate(Ciudad ciudad) throws Exception;
	Page getFormList(int page, int pageSize,Map<String,String> params);
	Ciudad get(String id);
	void importacion(File file,String tipo);
}