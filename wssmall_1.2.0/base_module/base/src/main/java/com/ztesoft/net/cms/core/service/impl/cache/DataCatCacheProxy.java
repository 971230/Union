package com.ztesoft.net.cms.core.service.impl.cache;

import com.ztesoft.net.cms.core.model.DataCat;
import com.ztesoft.net.cms.core.service.IDataCatManager;
import com.ztesoft.net.eop.resource.model.EopSite;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.framework.cache.AbstractCacheProxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章分类缓存代理<br>
 * 首先在分类缓存中以cacheName+"_"+site.getUserid() +"_"+site.getId();  为key存储当站点的分类缓存。以实现站点缓存隔离<br>
 * 在上述缓存中再以 cacheName+"_cat_"+catid;为key存储各分类的所有子类数据。
 * 
 * @author kingapex
 * 2010-7-5上午09:46:44
 */
public class DataCatCacheProxy extends AbstractCacheProxy<Map> implements
		IDataCatManager { 
	
	public static final String cacheName ="cms_data_cat";
	private IDataCatManager articleCatManager;
	public DataCatCacheProxy(IDataCatManager articleCatManager) {
		super(cacheName);
		this.articleCatManager = articleCatManager;
	}
	private String getKey(String catid){
		return cacheName+"_cat_"+catid;
	}
	
	/**
	 * 压入cat缓存
	 * @param key
	 * @param list
	 */
	private void put(String key,List<DataCat> list){
		
		//首先由缓存中获取本站点的cat缓存
		EopSite site  = EopContext.getContext().getCurrentSite();
		String mainkey = cacheName+"_"+site.getUserid() +"_"+site.getId();
		Map catCache= this.cache.get(mainkey );
		
		//无缓存新建cat缓存map,并压入缓存
		if(catCache==null){
			catCache = new HashMap();
			cache.put(mainkey, catCache);
		}
		
		catCache.put(key, list);
		
	}
	
	/**
	 * 由缓存中读取分类缓存
	 * @param key
	 * @return
	 */
	private List<DataCat> getN(String key){

		//首先由缓存中获取本站点的cat缓存
		EopSite site  = EopContext.getContext().getCurrentSite();
		String mainkey = cacheName+"_"+site.getUserid() +"_"+site.getId();
		Map<String,List<DataCat>> catCache= this.cache.get(mainkey );
		if(catCache==null){
			return  null;
		}
		//由类别缓存中获取数据
		return catCache.get(key);
	}
	
	/**
	 * 清除类别缓存
	 */
	private void cleanCache( ){
		
		EopSite site  = EopContext.getContext().getCurrentSite();
		String mainkey = cacheName+"_"+site.getUserid() +"_"+site.getId();
		this.cache.remove( mainkey);
		 
	 
	}
	
	@Override
	public void add(DataCat cat) {
		this.articleCatManager.add(cat);
		this.cleanCache( );
	}

	
	@Override
	public int delete(String catid) {
		int r = this.articleCatManager.delete(catid);
		if(r==0){
			this.cleanCache( );
		}
		return r;
	}

	
	@Override
	public void edit(DataCat cat) {
		this.articleCatManager.edit(cat);
		this.cleanCache( );
	}

	
	@Override
	public DataCat get(String catid,String source_from) {
		
		return this.articleCatManager.get(catid,source_from);
	}

	
	@Override
	public List<DataCat> listAllChildren(String parentid) {
		List<DataCat> catList  = getN(this.getKey(parentid));
		if(catList== null ){
			catList  = this.articleCatManager.listAllChildren(parentid);
			put(this.getKey(parentid), catList);
			if(this.logger.isDebugEnabled()){
				this.logger.debug("load article cat form database");
			}
		}else{
			if(this.logger.isDebugEnabled()){
				this.logger.debug("load article cat form cache");
			}
		}
		
		return catList;
	 
	}

	
	@Override
	public void saveSort(String[] catIds, int[] catSorts) {
		this.articleCatManager.saveSort(catIds, catSorts);
		this.cleanCache();
	}
	@Override
	public List<DataCat> getParents(String catid,String source_from) {

		return this.articleCatManager.getParents(catid,source_from);
	}

}
