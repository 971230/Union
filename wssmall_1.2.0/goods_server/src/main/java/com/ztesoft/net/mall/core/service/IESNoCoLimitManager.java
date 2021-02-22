package com.ztesoft.net.mall.core.service;

import java.util.Map;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.EsCoNoLimitEntity;
public interface IESNoCoLimitManager {
	/**
	 * 添加号码限制
	 */
	public void addorUpdate(Map params,EsCoNoLimitEntity esCnEntity) throws Exception;
	Page getFormList(int pageNo, int pageSize, Map<String,String> params);
	public void update(EsCoNoLimitEntity esCnEntity)throws Exception;
	public EsCoNoLimitEntity get(String org_id, String region_id);
	public void autoCreatePublish(String org_id, String region_id,int max_ordinary,int max_lucky);

}



