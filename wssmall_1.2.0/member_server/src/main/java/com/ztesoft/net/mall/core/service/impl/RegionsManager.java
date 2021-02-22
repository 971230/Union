package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.ztesoft.net.eop.resource.model.EopSite;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.ISqlFileExecutor;
import com.ztesoft.net.framework.util.FileBaseUtil;
import com.ztesoft.net.mall.core.model.Regions;
import com.ztesoft.net.mall.core.service.IRegionsManager;
import com.ztesoft.net.sqls.SF;

/**
 * 地区管理
 * @author kingapex
 * 2010-7-18下午08:12:03
 */
public class RegionsManager extends BaseSupport<Regions> implements IRegionsManager {

	private ISqlFileExecutor sqlFileExecutor;
	@Override
	public List listCity(long province_id) {
		String sql = SF.memberSql("MEMBER_LIST_CITY");
		List list = this.baseDaoSupport.queryForList(sql, Regions.class, province_id);
		list = list == null ? new ArrayList() : list;
		return list;
	}

	@Override
	public List listProvince() {
		String sql = SF.memberSql("MEMBER_LIST_PROVINCE")+" and source_from is not null";
		List list = this.baseDaoSupport.queryForList(sql, Regions.class);
		list = list == null ? new ArrayList() : list;
		return list;
	}
	
	@Override
	public List listProvince(String stype) {
		String sql = SF.memberSql("MEMBER_LIST_PROVINCE")+" and source_from is not null";
		if(StringUtils.isEmpty(stype)){
			sql+=" and T.NGN_FLAG='1' ";
		}
		List list = this.baseDaoSupport.queryForList(sql, Regions.class);
		list = list == null ? new ArrayList() : list;
		return list;
	}

	@Override
	public List listRegion(long city_id) {
		String sql = SF.memberSql("MEMBER_LIST_REGION");
		List list = this.baseDaoSupport.queryForList(sql, Regions.class, city_id);
		list = list == null ? new ArrayList() : list;
		return list;
	}

	
	@Override
	public List listChildren(String regionid) {
		String sql  = SF.memberSql("MEMBER_LIST_CHILDREN").replace("replaceSql", regionid);
		List list = this.daoSupport.queryForList(sql);
		return list;
	}

	
	@Override
	public List listChildrenP(String regionid) {
		
		if(regionid==null || regionid.equals("")) return new ArrayList();
		String sql  = SF.memberSql("MEMBER_LIST_CHILDRENP").replace("replaceSql", regionid);
		
		List list = this.daoSupport.queryForList(sql.toString());
		return list;
	}
	

	
	@Override
	public String getChildrenJson(String regionid) {
		List list  = this.listChildren(regionid);
		return JSON.toJSONString(list);
	}

	@Override
	public String getChildrenJson(String regionid,String stype) {
		List list  = this.listChildren(regionid,stype);
		return JSON.toJSONString(list);
	}
	
	public List listChildren(String regionid,String stype) {
		String sql  = SF.memberSql("MEMBER_LIST_CHILDREN").replace("replaceSql", regionid);
		if(StringUtils.isNotEmpty(stype)){
			sql=sql.replaceAll("#cont", " and c.NGN_FLAG='1' ");
		}else{
			sql=sql.replaceAll("#cont", " ");
		}
		List list = this.daoSupport.queryForList(sql);
		return list;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(Regions regions) {
		this.baseDaoSupport.insert("regions", regions);
		String region_path = "";
		String region_id = regions.getRegion_id();
		if(!regions.getP_region_id().equals("0")){
			Regions p = get(regions.getP_region_id());
			region_path = p.getRegion_path() + region_id + ",";
		}else{
			region_path = "," + region_id + ",";
		}
		regions = get(region_id);
		regions.setRegion_path(region_path);
		update(regions);
	}

	
	@Override
	public void delete(String regionId) {
		String sql = SF.memberSql("MEMBER_DEL_REGIONS_BY_PATH");
		this.baseDaoSupport.execute(sql, regionId + ",%");
		
	}

	
	@Override
	public Regions get(String regionId) {
		String sql = SF.memberSql("MEMBER_GET_REGIONS_BY_ID");
		Regions region =  this.baseDaoSupport.queryForObject(sql, Regions.class, regionId);
		if(region==null){
			try{
				String sql2 = "select t.code region_id,t.name local_name,t.pre_code p_region_id,t.add_level region_grade from es_regions_zb t where t.code=?";
				region = this.baseDaoSupport.queryForObject(sql2, Regions.class, regionId);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return region;
	}
	
	@Override
	public Regions getByName(String name) {
		try{
			String sql = SF.memberSql("MEMBER_GET_REGIONS_BY_NAME");
			List<Regions> list = this.baseDaoSupport.queryForList(sql, Regions.class, name  );
			if(list== null || list.isEmpty()) {
				return null;
			}
			return list.get(0);
		}catch(RuntimeException e){
			return null;
		}
	}

	
	@Override
	public void update(Regions regions) {
		this.baseDaoSupport.update("regions", regions, "region_id="+regions.getRegion_id());
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED) 
	public void reset() {
		String sql  ="truncate table "+ this.getTableName("regions") ;
		this.daoSupport.execute(sql);
		String  content = FileBaseUtil.readFile("com/enation/mall/city.sql");
		if("2".equals(EopSetting.RUNMODE) ){
			EopSite site  = EopContext.getContext().getCurrentSite();
			content = content.replaceAll("<userid>",String.valueOf( site.getUserid() ));
			content = content.replaceAll("<siteid>",String.valueOf( site.getId()));
		}else{
			content = content.replaceAll("_<userid>","");
			content = content.replaceAll("_<siteid>","");		
	 
		}		
		sqlFileExecutor.execute(content);
	}


	public ISqlFileExecutor getSqlFileExecutor() {
		return sqlFileExecutor;
	}


	public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor) {
		this.sqlFileExecutor = sqlFileExecutor;
	}


	@Override
	public Regions[] getS(String area) {
		//这里什么也不做
		return null;
	}

}
