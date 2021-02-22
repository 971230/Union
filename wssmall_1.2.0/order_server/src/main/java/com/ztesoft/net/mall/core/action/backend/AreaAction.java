package com.ztesoft.net.mall.core.action.backend;

import java.util.List;

import params.regions.req.RegionChildrenJsonReq;
import params.regions.req.RegionCityListByProvinceReq;
import params.regions.req.RegionListByCityReq;
import params.regions.req.RegionProvinceListReq;
import params.regions.resp.RegionChildrenJsonResp;
import params.regions.resp.RegionCityListByProvinceResp;
import params.regions.resp.RegionListByCityResp;
import params.regions.resp.RegionProvinceListResp;
import services.RegionsInf;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.DlyArea;
import com.ztesoft.net.mall.core.model.Regions;
import com.ztesoft.net.mall.core.service.IAreaManager;

/**
 * @author lzf<br/>
 * 2010-3-17 上午10:07:46<br/>
 * version 1.0<br/>
 */
public class AreaAction extends WWAction {
	
	/*
	 * 属性
	 */
	private Regions regions;
	private List provinceList;
	private List cityList;
	private List regionList;
	private int province_id;
	private int city_id;
	private String regionid;
	private RegionsInf regionsServ;
	
	/*
	 * 方法
	 */
	
	public String add_region(){
		return "add_region";
	}
	
	public String edit_region(){
		return "eidt_region";
	}
	
	public String list_province(){
		
		RegionProvinceListReq req = new RegionProvinceListReq();
		RegionProvinceListResp resp = regionsServ.getProvinceList(req);
		if(resp != null){
			provinceList = resp.getProvince_list();
		}
		return "list_province";
	}
	
	public String list_city(){
		
		RegionCityListByProvinceReq req = new RegionCityListByProvinceReq();
		req.setProvince_id(province_id);
		RegionCityListByProvinceResp resp = regionsServ.getCityListByProvinceId(req);
		if(resp != null){
			cityList = resp.getChild_list();
		}
		return "list_city";
	}
	
	public String list_region(){
		
		RegionListByCityReq req = new RegionListByCityReq();
		req.setCity_id(city_id);
		RegionListByCityResp resp = regionsServ.getRegionListByCityId(req);
		if(resp != null){
			regionList = resp.getChild_list();
		}
		
		return "list_region";
	}
	
	public String listChildren(){
		
		RegionChildrenJsonReq req = new RegionChildrenJsonReq();
		req.setRegion_id(regionid);
		RegionChildrenJsonResp resp = regionsServ.getChildrenJson(req);
		if(resp != null){
			json = resp.getChild_json();
		}
		
		return WWAction.JSON_MESSAGE;
	}
	/*
	 * 属性公开
	 */

	public Regions getRegions() {
		return regions;
	}

	public void setRegions(Regions regions) {
		this.regions = regions;
	}

	public List getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List provinceList) {
		this.provinceList = provinceList;
	}

	public List getCityList() {
		return cityList;
	}

	public void setCityList(List cityList) {
		this.cityList = cityList;
	}

	public List getRegionList() {
		return regionList;
	}

	public void setRegionList(List regionList) {
		this.regionList = regionList;
	}

	public int getProvince_id() {
		return province_id;
	}

	public void setProvince_id(int provinceId) {
		province_id = provinceId;
	}

	public int getCity_id() {
		return city_id;
	}

	public void setCity_id(int cityId) {
		city_id = cityId;
	}

//////////////////////////////////////////////////////
	
	
	private Integer area_id;
	private String name;
	private IAreaManager areaManager;
	private String order;
	private String id;
	private DlyArea area;
	
	public String add_area(){
		return "add_area";
	}
	
	public String edit_area(){
		area = this.areaManager.getDlyAreaById(area_id);
		return "edit_area";
	}
	
	public String list(){
		this.webpage = this.areaManager.pageArea(order, this.getPage(), this.getPageSize());
		return "list_area";
	}
	
	public String delete(){
		try {
			this.areaManager.delete(id);
			this.json = "{'result':0,'message':'删除成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'删除失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String saveAdd(){
		areaManager.saveAdd(name);
		this.msgs.add("地区保存成功");
		this.urls.put("地区列表", "area!list.do" ); 
		return WWAction.MESSAGE;
	}
	
	
	public String saveEdit(){
		areaManager.saveEdit(area_id,name);
		this.msgs.add("地区保存成功");
		this.urls.put("地区列表", "area!list.do" ); 		
		return WWAction.MESSAGE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	 

	public IAreaManager getAreaManager() {
		return areaManager;
	}


	public void setAreaManager(IAreaManager areaManager) {
		this.areaManager = areaManager;
	}


	public Integer getArea_id() {
		return area_id;
	}

	public void setArea_id(Integer area_id) {
		this.area_id = area_id;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DlyArea getArea() {
		return area;
	}

	public void setArea(DlyArea area) {
		this.area = area;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public RegionsInf getRegionsServ() {
		return regionsServ;
	}

	public void setRegionsServ(RegionsInf regionsServ) {
		this.regionsServ = regionsServ;
	}
	
	
	
}