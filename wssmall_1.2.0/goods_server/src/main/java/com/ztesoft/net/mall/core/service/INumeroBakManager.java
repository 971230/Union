package com.ztesoft.net.mall.core.service;


import java.util.Map;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.NumeroBak;


/**
 * 号码分组管理接口
 * 
 * @author cc
 * 
 */
public interface INumeroBakManager {
	
	Page getFormList(int page, int pageSize,Map<String,String> params);
	void save(NumeroBak nb) throws Exception;
	void del(String ids,Map<String,String> params);
	void delByNo(String ids,Map<String,String> params);
}
