package com.ztesoft.net.mall.core.service.impl.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.cache.AbstractCacheProxy;
import com.ztesoft.net.framework.cache.CacheFactory;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.sqls.SF;

public class DcPublicSQLInfoCacheProxy extends AbstractCacheProxy<List> {

	public static final String DROP_DOWN_DATA = "drop_down_data";

	public DcPublicSQLInfoCacheProxy(IDcPublicInfoManager dcPublicInfoManager) {
		super(CacheFactory.DC_PUBLIC_CACHE_NAME_KEY);
	}

	public DcPublicSQLInfoCacheProxy() {
		super(CacheFactory.DC_PUBLIC_CACHE_NAME_KEY);
	}
	
	public List getDropdownDataLocal(String attrCode) {
		IDaoSupport daoSupport = SpringContextHolder.getBean("baseDaoSupport");
		List datas = null;
		List<Map> sqls = daoSupport.queryForList(SF.baseSql("ATTR_CODE_GET"), attrCode);
		if(sqls!=null && sqls.size()>0){
			Map sql = sqls.get(0);
			datas = daoSupport.queryForList(sql.get("dc_sql").toString());
		}
		return datas;
	}
	
	/**
	 * 获取下拉框静态数据
	 * @param attrCode 字典编码，见es_dc_sql
	 * @return
	 */
	public List getDropdownData(String attrCode){
		List list = new ArrayList();
		List<Map> datas = this.cache.get(DROP_DOWN_DATA);
		if(datas == null || datas.isEmpty()){
			List DropdownData = getDropdownDataLocal(attrCode);
			Map data = new HashMap();
			data.put(attrCode, DropdownData);
			datas = new ArrayList();
			datas.add(data);
			this.cache.put(DROP_DOWN_DATA, datas);
			return DropdownData;
		}
		for(Map map : datas){
			if(map.containsKey(attrCode)){
				return (List)map.get(attrCode);
			}
		}
		if(list.size()==0){
			List DropdownData =getDropdownDataLocal(attrCode);
			Map data = new HashMap();
			data.put(attrCode, DropdownData);
			datas.add(data);
			this.cache.put(DROP_DOWN_DATA, datas);	
			return DropdownData;
		}
		return list;
	}
	

}
