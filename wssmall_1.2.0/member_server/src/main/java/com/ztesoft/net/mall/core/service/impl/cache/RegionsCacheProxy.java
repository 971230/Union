package com.ztesoft.net.mall.core.service.impl.cache;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.cache.CacheFactory;
import com.ztesoft.net.framework.cache.ICache;
import com.ztesoft.net.mall.core.model.Regions;
import com.ztesoft.net.mall.core.service.IRegionsManager;
import com.ztesoft.net.sqls.SF;

public class RegionsCacheProxy extends BaseSupport<Regions> implements IRegionsManager {
	
	//FIXME 检查发现此Cache逻辑混乱，应未实现Cache功能
	
	protected final Logger logger = Logger.getLogger(getClass());
	protected ICache<List<Regions>> cache;
	
	private IRegionsManager regionsManager;
	private static final String REGION_LIST_CACHE_KEY = "regionCache";
	public  RegionsCacheProxy(IRegionsManager regionsManager){
		cache = CacheFactory.getCache(REGION_LIST_CACHE_KEY);
		this.regionsManager =regionsManager;
	}
	
	
	@Override
	public List listCity(long province_id) {
		 
		return regionsManager.listCity(province_id);
	}

	
	@Override
	public List listProvince() {
 
		return regionsManager.listProvince();
	}
	
	@Override
	public List listProvince(String stype) {
		 
		return regionsManager.listProvince(stype);
	}

	
	@Override
	public List listRegion(long city_id) {
		 
		return this.regionsManager.listRegion(city_id);
	}

	
	@Override
	public List listChildren(String regionid) {
	 
		return this.regionsManager.listChildren(regionid);
	}

	private List<Regions> listAll(){
		
		String sql = SF.memberSql("MEMBER_REGIONS_LIST");
		return this.baseDaoSupport.queryForList(sql, Regions.class);
	}
	
	
	@Override
	public List listChildrenP(String regionid) {
		if(logger.isDebugEnabled()){
			logger.info("find parents is " + regionid);
		}
		if(regionid==null || "".equals(regionid))return new ArrayList();
		
		List<Regions> regionsList = cache.get(REGION_LIST_CACHE_KEY);
		if(regionsList==null){
			if(logger.isDebugEnabled()){
				logger.info("load regions list from db");
			}
			regionsList = listAll() ;
			cache.put(REGION_LIST_CACHE_KEY,regionsList );
		}else{
			if(logger.isDebugEnabled()){
				logger.info("load regions list from cache");
			}
		}
		List<String> list = new ArrayList<String>();
		String[] regionsArray =regionid.split(",");
		for(String id:regionsArray){

			for(Regions  region:regionsList){
				if(region.getRegion_path().indexOf(","+id+",")>=0){
					list.add(region.getRegion_id());
				}
			}
		}
		
		return list;
	}
	

	
	@Override
	public String getChildrenJson(String regionid) {
		 
		return this.regionsManager.getChildrenJson(regionid);
	}
	
	@Override
	public String getChildrenJson(String regionid,String stype) {
		 
		return this.regionsManager.getChildrenJson(regionid,stype);
	}

	
	@Override
	public void add(Regions regions) {
		this.regionsManager.add(regions);
		
	}

	
	@Override
	public void delete(String regionId) {
		this.regionsManager.delete(regionId);
		
	}

	
	@Override
	public Regions get(String regionId) {
		return this.regionsManager.get(regionId);
	}

	
	@Override
	public void update(Regions regions) {
		this.regionsManager.update(regions);
		
	}


	@Override
	public void reset() {
		this.regionsManager.reset();
	}


	@Override
	public Regions[] getS(String area) {
		String[] areas = area.split("-");
		List<Regions> list = this.listAll();
		Regions[] result = new Regions[3];
		for(Regions regions:list){
			if(regions.getLocal_name().equals(areas[0])) result[0] = regions;
			if(regions.getLocal_name().equals(areas[1])) result[1] = regions;
			if(regions.getLocal_name().equals(areas[2])) result[2] = regions;
		}
		return result;
	}


	@Override
	public Regions getByName(String name) {
		return this.regionsManager.getByName(name);
	}

}
