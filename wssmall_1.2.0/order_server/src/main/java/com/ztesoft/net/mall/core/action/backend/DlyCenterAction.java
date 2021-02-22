package com.ztesoft.net.mall.core.action.backend;

import java.util.List;

import params.regions.req.RegionCityListByProvinceReq;
import params.regions.req.RegionListByCityReq;
import params.regions.req.RegionProvinceListReq;
import params.regions.resp.RegionCityListByProvinceResp;
import params.regions.resp.RegionListByCityResp;
import params.regions.resp.RegionProvinceListResp;
import services.RegionsInf;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.DlyCenter;
import com.ztesoft.net.mall.core.service.IDlyCenterManager;

public class DlyCenterAction extends WWAction {
	
	private IDlyCenterManager dlyCenterManager;
	private RegionsInf regionsServ;
	private DlyCenter dlyCenter;
	private String dlyCenterId;
	private String[] id;
	private List<DlyCenter> list;
	private List provinceList;
	private List cityList;
	private List regionList;
	
	public String add(){
		RegionProvinceListReq req = new RegionProvinceListReq();
		RegionProvinceListResp resp = regionsServ.getProvinceList(req);
		if(resp != null){
			provinceList = resp.getProvince_list();
		}
		return "add";
	}
	
	public String edit(){
		dlyCenter = dlyCenterManager.get(dlyCenterId);
		
		RegionProvinceListReq req = new RegionProvinceListReq();
		RegionProvinceListResp resp = regionsServ.getProvinceList(req);
		if(resp != null){
			provinceList = resp.getProvince_list();
		}
		
		
		if (dlyCenter.getProvince_id() != null) {
			RegionCityListByProvinceReq req1 = new RegionCityListByProvinceReq();
			req1.setProvince_id(new Long(dlyCenter.getProvince_id()).longValue());
			RegionCityListByProvinceResp resp1 = regionsServ.getCityListByProvinceId(req1);
			if(resp1 != null){
				cityList = resp1.getChild_list();
			}
		}
		
		if (dlyCenter.getCity_id() != null) {
			RegionListByCityReq req2 = new RegionListByCityReq();
			req2.setCity_id(new Long(dlyCenter.getCity_id()).longValue());
			RegionListByCityResp resp2 = regionsServ.getRegionListByCityId(req2);
			if(resp2 != null){
				regionList = resp2.getChild_list();
			}
		}
		return "edit";
	}
	
	public String list(){
		list = dlyCenterManager.list();
		return "list";
	}
	
	public String delete(){
		try {
			this.dlyCenterManager.delete(id);
			this.json = "{result:0,message:'发货信息删除成功'}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:1,message:\"发货信息删除失败：" + e.getMessage() + "\"}";

		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String saveAdd(){
		try{
			dlyCenterManager.add(dlyCenter);
			this.msgs.add("发货信息添加成功");
			
		}catch(Exception e){
			e.printStackTrace();
			this.msgs.add("发货信息添加失败");
		}
		this.urls.put("发货信息管理", "dlyCenter!list.do");
		return WWAction.MESSAGE;
	}
	
	public String saveEdit(){
		try{
			dlyCenterManager.edit(dlyCenter);
			this.msgs.add("发货信息修改成功");
			
		}catch(Exception e){
			e.printStackTrace();
			this.msgs.add("发货信息修改失败");
		}
		this.urls.put("发货信息管理", "dlyCenter!list.do");
		return WWAction.MESSAGE;
	}

	public IDlyCenterManager getDlyCenterManager() {
		return dlyCenterManager;
	}

	public void setDlyCenterManager(IDlyCenterManager dlyCenterManager) {
		this.dlyCenterManager = dlyCenterManager;
	}

	public DlyCenter getDlyCenter() {
		return dlyCenter;
	}

	public void setDlyCenter(DlyCenter dlyCenter) {
		this.dlyCenter = dlyCenter;
	}

	public String getDlyCenterId() {
		return dlyCenterId;
	}

	public void setDlyCenterId(String dlyCenterId) {
		this.dlyCenterId = dlyCenterId;
	}

	public String[] getId() {
		return id;
	}

	public void setId(String[] id) {
		this.id = id;
	}

	public List<DlyCenter> getList() {
		return list;
	}

	public void setList(List<DlyCenter> list) {
		this.list = list;
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

	public RegionsInf getRegionsServ() {
		return regionsServ;
	}

	public void setRegionsServ(RegionsInf regionsServ) {
		this.regionsServ = regionsServ;
	}

}