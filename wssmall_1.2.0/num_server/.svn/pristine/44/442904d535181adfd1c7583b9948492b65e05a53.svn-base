package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.NumeroRegla;


/**
 * 靓号规则接口
 * 
 * @author cc
 * 
 */
public interface INumeroReglaManager {
	
	void del(String ids,Map<String,String> params);
	void saveOrUpdate(NumeroRegla nr) throws Exception;
	Page getFormList(int page, int pageSize,Map<String,String> params);
	NumeroRegla get(String id);
	List getEstatos();
	List getTipos();
}
