package com.ztesoft.net.mall.core.action.backend;

import java.util.List;

import params.regions.req.RegionChildrenListReq;
import params.regions.req.RegionDeleteRegionsReq;
import params.regions.req.RegionNewRegionsReq;
import params.regions.req.RegionReq;
import params.regions.req.RegionResetRegionsReq;
import params.regions.req.RegionUpdateRegionsReq;
import params.regions.resp.RegionChildrenListResp;
import params.regions.resp.RegionDeleteRegionsResp;
import params.regions.resp.RegionNewRegionsResp;
import params.regions.resp.RegionResetRegionsResp;
import params.regions.resp.RegionResp;
import params.regions.resp.RegionUpdateRegionsResp;
import services.RegionsInf;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.Regions;

/**
 * 充值卡类
 * 
 * @author wui
 */
public class RegionAction extends WWAction {
	
	private List listRegion;
	private String parentid;
	private Regions regions;
	private String region_id;
	private Integer regiongrade;
	private RegionsInf regionsServ;
	
	
	
	
	public String list(){
		return "list";
	}
	
	public String listChildren(){
		
		RegionChildrenListReq req1 = new RegionChildrenListReq();
		req1.setRegion_id(parentid);
		RegionChildrenListResp resp1 = regionsServ.getChildrenListById(req1);
		
		if(resp1 != null){
			listRegion = resp1.getRegionLists();
		}
		return "listChildren";
	}
	
	public String add(){
		return "add";
	}
	
	public String saveAdd(){
		try{
			RegionNewRegionsReq req = new RegionNewRegionsReq();
			req.setRegions(regions);
			RegionNewRegionsResp resp = regionsServ.addNewRegions(req);
			if(resp != null){
				this.msgs.add("地区添加成功");
			}else{
				this.msgs.add("地区添加失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			this.msgs.add("地区添加失败");
		}
		this.urls.put("地区管理", "region!list.do");
		return WWAction.MESSAGE;
	}
	
	public String edit(){
		
		RegionReq req = new RegionReq();
		req.setRegion_id(region_id);
		RegionResp resp = regionsServ.getRegionById(req);
		if(resp != null){
			regions = resp.getRegions();
		}
		return "edit";
	}
	
	public String saveEdit(){
		try{
			RegionUpdateRegionsReq req = new RegionUpdateRegionsReq();
			req.setRegions(regions);
			RegionUpdateRegionsResp resp = regionsServ.updateRegions(req);
			if(resp != null){
				this.msgs.add("地区修改成功");
			}else{
				this.msgs.add("地区修改失败");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			this.msgs.add("地区修改失败");
		}
		this.urls.put("地区管理", "region!list.do");
		return WWAction.MESSAGE;
	}
	
	public String delete(){
		try {
			
			RegionDeleteRegionsReq req = new RegionDeleteRegionsReq();
			req.setRegion_id(region_id);
			RegionDeleteRegionsResp resp = regionsServ.deleteRegions(req);
			if(resp != null){
				this.json = "{'result':0,'message':'删除成功'}";
			}else{
				this.json = "{'result':1,'message':'删除失败'}";
			}
			
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'删除失败'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String reset(){
		try {
			
			RegionResetRegionsReq req = new RegionResetRegionsReq();
			RegionResetRegionsResp resp = regionsServ.resetRegions(req);
			if(resp != null){
				this.json = "{'result':0,'message':'成功'}";
			}else{
				this.json = "{'result':1,'message':'失败'}";
			}
			
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'失败'}";
			e.printStackTrace();
		}
		return  WWAction.JSON_MESSAGE;
	}

	public List getListRegion() {
		return listRegion;
	}

	public void setListRegion(List listRegion) {
		this.listRegion = listRegion;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public Regions getRegions() {
		return regions;
	}

	public void setRegions(Regions regions) {
		this.regions = regions;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String regionId) {
		region_id = regionId;
	}

	public Integer getRegiongrade() {
		return regiongrade;
	}

	public void setRegiongrade(Integer regiongrade) {
		this.regiongrade = regiongrade;
	}

	public RegionsInf getRegionsServ() {
		return regionsServ;
	}

	public void setRegionsServ(RegionsInf regionsServ) {
		this.regionsServ = regionsServ;
	}
}
