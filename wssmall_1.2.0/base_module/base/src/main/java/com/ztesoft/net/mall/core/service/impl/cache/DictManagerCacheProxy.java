package com.ztesoft.net.mall.core.service.impl.cache;

import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.framework.cache.AbstractCacheProxy;
import com.ztesoft.net.framework.cache.CacheFactory;
import com.ztesoft.net.mall.core.service.IDictManager;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DictManagerCacheProxy extends AbstractCacheProxy {
	private IDictManager dictManager;

	public static final String LIST_KEY_PREFIX = "dict_manager_info_list";

	public DictManagerCacheProxy(IDictManager dictManager) {

		super(CacheFactory.DICT_MANAGER_CACHE_NAME_KEY);
		this.dictManager = dictManager;
	}

	/**
	 * 获取es_config_info配置信息
	 * 
	 * @param cfId
	 */
	@SuppressWarnings("unchecked")
	public List loadData(String attr_code) {
//		List<Map> list=new ArrayList<Map>();
		
		// 首先获取列表
		Map cacheMap= (Map) this.cache.get(LIST_KEY_PREFIX);
		if (cacheMap == null || cacheMap.isEmpty()) {
			Map<String,Object> map = this.dictManager.loadAllData();
			this.cache.put(LIST_KEY_PREFIX,map);
			if (map != null && !map.isEmpty()) {
				Iterator it= map.keySet().iterator(); 
				while(it.hasNext()){      
					String key=(String)it.next();   
					List staticList = (List) map.get(key);
					this.cache.put(LIST_KEY_PREFIX+key, staticList);
				  }  
			} 
			cacheMap=(Map) this.cache.get(LIST_KEY_PREFIX);
		}
		//优化性能列表获取,直接获取值
		List staticList = (List) this.cache.get(LIST_KEY_PREFIX+attr_code);
		if(ListUtil.isEmpty(staticList) || staticList.size() ==0){
			// 得到列表后获取key值
			if (cacheMap != null && !cacheMap.isEmpty()) {
				Iterator it= cacheMap.keySet().iterator(); 
				while(it.hasNext()){      
					String key=(String)it.next();  
					if(key.equals(attr_code)){
						staticList = (List) cacheMap.get(key);
						return staticList;
					}  
				} 
			}
		}
		return staticList;
	}

	public Map loadUserRole(String attr_code) {
		return this.dictManager.loadUserRole(attr_code);
	}
	
	public List loadUserRoleList(String role_id) {
		return this.dictManager.loadUserRoleList(role_id.trim());
	}
	
	public List<Map> qryUserRole(String user_id){
		return this.dictManager.qryUserRole(user_id);
	}
	
	/**
	 * 
	 * 刷新缓存方法，可添加其他数据表缓存刷新
	 */
	public void refreshCache() {
		Map<String,Object> map = this.dictManager.loadAllData();
		// 得到列表后获取key值
		if (map != null && !map.isEmpty()) {
			Iterator it= map.keySet().iterator(); 
			while(it.hasNext()){      
				String key=(String)it.next();   
				List staticList = (List) map.get(key);
				this.cache.put(LIST_KEY_PREFIX+key, staticList);
			  }  
			} 
		this.cache.put(LIST_KEY_PREFIX, map);
	}

	public IDictManager getDictManager() {
		return dictManager;
	}

	public void setDictManager(IDictManager dictManager) {
		this.dictManager = dictManager;
	}


}
