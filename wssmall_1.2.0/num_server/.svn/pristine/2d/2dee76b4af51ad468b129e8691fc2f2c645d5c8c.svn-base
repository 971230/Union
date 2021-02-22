package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.NumeroGrupo;


/**
 * 号码管理接口
 * 
 * @author cc
 * 
 */
public interface INumeroGrupoManager {
	
	void del(String ids,Map<String,String> params);
	void saveOrUpdate(NumeroGrupo ng) throws Exception;
	Page getFormList(int page, int pageSize,Map<String,String> params);
	List getFormList(Map<String,String> params);
	NumeroGrupo get(String id);
	List getEstatos();
}
