package com.ztesoft.net.mall.core.action.cache;

import java.util.List;

import zte.net.iservice.IDcPublicInfoCacheService;

import com.alibaba.fastjson.JSON;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.CacheConfig;
import com.ztesoft.net.mall.core.model.ConfigInfo;
import com.ztesoft.net.mall.core.service.ICacheManager;
import com.ztesoft.net.mall.core.service.IConfigInfoManager;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

public class CacheAction extends WWAction{
	
	private ICacheUtil cacheUtil;
	private String serviceIp;
	private List<ConfigInfo> serviceList;
	private String refreshType;
	
	
	private IConfigInfoManager configInfoManager;
	private IDcPublicInfoManager dcPublicInfoManager;
	
	private IDcPublicInfoCacheService dcPublicInfoCacheService;
	
	private ICacheManager cacheManagerImpl;
	
	private String cache_key;//缓存值
	private String cache_config_id;//缓存查询配置主键
	private List cacheAddressList;//ehcache缓存地址
	private String cache_type;//缓存类型
	private String cacheInfo;//缓存数据信息
    private String requestUrl;//请求路径
	/**
	 * 刷新配置数据
	 * @return
	 */
	public String refreshCache(){
		
		cacheUtil.refreshCache();
		return "refresh_result";
	}
	
	public String refreshGoodsCache(){
		
		return "refresh_result";
	}
	
	/**
	 * 静态数据缓存*/
	public String refreshDcPublicCache(){
		cacheUtil.refreshDcPublicCache();
		cacheUtil.refreshDictCache();
		return "refresh_result";
	}
	
	public String refreshList(){
		serviceList = configInfoManager.getServiceList("SERVER_IP_ADDR_");
		return "refresh_list";
	}
	
	/**
	 * 缓存自由组合依赖元素
	 * @author SQY (2015-09-13)
	 * @return
	 */
	public String cacheFreedomGruopDependElement() {
		dcPublicInfoCacheService.cacheFreedomGruopDependElement();
		return "refresh_result";
	}
	
	public String refresh(){
		
		return "refresh_cache";
	}
	public String toCacheInfoJsp(){
	    return "cache_info";
	}
	public String cacheAddressList(){
		try{
			cacheAddressList = this.cacheManagerImpl.getCacheAddressListByCacheType(cache_type);
			String cacheAddressStr = JSON.toJSONString(cacheAddressList);
			this.json ="{result:0,cacheAddressStr:"+cacheAddressStr+"}";
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{result:1,message:'"+e.getMessage()+"'}";
		}
	    return WWAction.JSON_MESSAGE;
	}
	public String getCacheDesc(){
		try{
			CacheConfig cacheConfig =  this.cacheManagerImpl.getCacheConfigById(cache_config_id);
	        if(cacheConfig!=null){
	        	String cache_desc = cacheConfig.getCache_desc();
	        	String key_is_null = cacheConfig.getKey_is_null();
	        	String cacheConfigStr = JSON.toJSONString(cacheConfig);;
	        	this.json ="{result:0,cacheConfig:"+cacheConfigStr+"}";
	        }else{
	        	 this.json = "{result:1,message:'为找到缓存配置'}";
	        }
		}catch(Exception e){
			e.printStackTrace();
			this.json ="{result:1,message:'"+e.getMessage()+"'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * @作者 shen.qiyu
	 * 刷新规格定义和规格关键字
	 * @return
	 */
	public String refreshSpecDefineAndSpecValues() {		
			DcPublicInfoCacheProxy dcPublicInfoCacheProxy = new DcPublicInfoCacheProxy(dcPublicInfoManager);
			dcPublicInfoCacheProxy.refreshSpecDefineAndSpecValues();
			return "refresh_result";
	}
	
	public ICacheUtil getCacheUtil() {
		return cacheUtil;
	}

	public void setCacheUtil(ICacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}

	public String getServiceIp() {
		return serviceIp;
	}

	public void setServiceIp(String serviceIp) {
		this.serviceIp = serviceIp;
	}

	public IConfigInfoManager getConfigInfoManager() {
		return configInfoManager;
	}

	public void setConfigInfoManager(IConfigInfoManager configInfoManager) {
		this.configInfoManager = configInfoManager;
	}

	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}

	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}

	public void setServiceList(List<ConfigInfo> serviceList) {
		this.serviceList = serviceList;
	}

	public List<ConfigInfo> getServiceList() {
		return serviceList;
	}

	public String getRefreshType() {
		return refreshType;
	}

	public void setRefreshType(String refreshType) {
		this.refreshType = refreshType;
	}

	public IDcPublicInfoCacheService getDcPublicInfoCacheService() {
		return dcPublicInfoCacheService;
	}

	public void setDcPublicInfoCacheService(
			IDcPublicInfoCacheService dcPublicInfoCacheService) {
		this.dcPublicInfoCacheService = dcPublicInfoCacheService;
	}

	public ICacheManager getCacheManagerImpl() {
		return cacheManagerImpl;
	}

	public void setCacheManagerImpl(ICacheManager cacheManagerImpl) {
		this.cacheManagerImpl = cacheManagerImpl;
	}

	public String getCache_key() {
		return cache_key;
	}

	public void setCache_key(String cache_key) {
		this.cache_key = cache_key;
	}

	public String getCache_config_id() {
		return cache_config_id;
	}

	public void setCache_config_id(String cache_config_id) {
		this.cache_config_id = cache_config_id;
	}

	public List getCacheAddressList() {
		return cacheAddressList;
	}

	public void setCacheAddressList(List cacheAddressList) {
		this.cacheAddressList = cacheAddressList;
	}

	public String getCache_type() {
		return cache_type;
	}

	public void setCache_type(String cache_type) {
		this.cache_type = cache_type;
	}

	public String getCacheInfo() {
		return cacheInfo;
	}

	public void setCacheInfo(String cacheInfo) {
		this.cacheInfo = cacheInfo;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	
}