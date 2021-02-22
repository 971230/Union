package services;

import java.util.List;

import params.regions.req.RegionChildrenJsonReq;
import params.regions.req.RegionChildrenListReq;
import params.regions.req.RegionCityListByProvinceReq;
import params.regions.req.RegionDeleteRegionsReq;
import params.regions.req.RegionListByCityReq;
import params.regions.req.RegionNewRegionsReq;
import params.regions.req.RegionProvinceListReq;
import params.regions.req.RegionReq;
import params.regions.req.RegionResetRegionsReq;
import params.regions.req.RegionUpdateRegionsReq;
import params.regions.resp.RegionChildrenJsonResp;
import params.regions.resp.RegionChildrenListResp;
import params.regions.resp.RegionCityListByProvinceResp;
import params.regions.resp.RegionDeleteRegionsResp;
import params.regions.resp.RegionListByCityResp;
import params.regions.resp.RegionNewRegionsResp;
import params.regions.resp.RegionProvinceListResp;
import params.regions.resp.RegionResetRegionsResp;
import params.regions.resp.RegionResp;
import params.regions.resp.RegionUpdateRegionsResp;

import com.ztesoft.net.mall.core.model.Regions;
import com.ztesoft.net.mall.core.service.IRegionsManager;

public class RegionsServ extends ServiceBase implements RegionsInf{
	
	private IRegionsManager regionsManager;

	public IRegionsManager getRegionsManager() {
		return regionsManager;
	}

	public void setRegionsManager(IRegionsManager regionsManager) {
		this.regionsManager = regionsManager;
	}

	@Override
	public RegionChildrenListResp getChildrenListById(RegionChildrenListReq req) {
		RegionChildrenListResp resp = new RegionChildrenListResp();
		
		List list = this.regionsManager.listChildren(req.getRegion_id());
		resp.setRegionLists(list);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public RegionProvinceListResp getProvinceList(RegionProvinceListReq req) {

		RegionProvinceListResp resp = new RegionProvinceListResp();
		
		List list = regionsManager.listProvince();
		resp.setProvince_list(list);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public RegionCityListByProvinceResp getCityListByProvinceId(RegionCityListByProvinceReq req) {

		RegionCityListByProvinceResp resp = new RegionCityListByProvinceResp();
		
		List list = regionsManager.listCity(req.getProvince_id());
		resp.setChild_list(list);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public RegionChildrenJsonResp getChildrenJson(RegionChildrenJsonReq req) {
		
		RegionChildrenJsonResp resp = new RegionChildrenJsonResp();
		
		String json_str = regionsManager.getChildrenJson(req.getRegion_id());
		resp.setChild_json(json_str);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public RegionListByCityResp getRegionListByCityId(RegionListByCityReq req) {

		RegionListByCityResp resp = new RegionListByCityResp();
		
		List list = regionsManager.listRegion(req.getCity_id());
		resp.setChild_list(list);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}
	
	@Override
	public RegionNewRegionsResp addNewRegions(RegionNewRegionsReq req){
		
		RegionNewRegionsResp resp = new RegionNewRegionsResp();
		try{
			regionsManager.add(req.getRegions());
			resp.setError_code("0");
			resp.setError_msg("成功");
		}catch(Exception e){
			e.printStackTrace();
			resp = null;
			addReturn(req, resp);
			return resp;
		}
		addReturn(req, resp);
		return resp;
	}

	@Override
	public RegionDeleteRegionsResp deleteRegions(RegionDeleteRegionsReq req) {
		RegionDeleteRegionsResp resp = new RegionDeleteRegionsResp();
		try{
			this.regionsManager.delete(req.getRegion_id());
			resp.setError_code("0");
			resp.setError_msg("成功");
		}catch(Exception e){
			e.printStackTrace();
			resp = null;
			addReturn(req, resp);
			return resp;
		}
		addReturn(req, resp);
		return resp;
	}

	@Override
	public RegionResp getRegionById(RegionReq req) {
		RegionResp resp = new RegionResp();
		try{
			Regions region = regionsManager.get(req.getRegion_id());
			resp.setRegions(region);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}catch(Exception e){
			e.printStackTrace();
			resp = null;
			addReturn(req, resp);
			return resp;
		}
		addReturn(req, resp);
		return resp;
	}

	@Override
	public RegionResetRegionsResp resetRegions(RegionResetRegionsReq req) {
		RegionResetRegionsResp resp = new RegionResetRegionsResp();
		try{
			this.regionsManager.reset();
			resp.setError_code("0");
			resp.setError_msg("成功");
		}catch(Exception e){
			e.printStackTrace();
			resp = null;
			addReturn(req, resp);
			return resp;
		}
		addReturn(req, resp);
		return resp;
	}

	@Override
	public RegionUpdateRegionsResp updateRegions(RegionUpdateRegionsReq req) {
		RegionUpdateRegionsResp resp = new RegionUpdateRegionsResp();
		try{
			regionsManager.update(req.getRegions());
			resp.setError_code("0");
			resp.setError_msg("成功");
		}catch(Exception e){
			e.printStackTrace();
			resp = null;
			addReturn(req, resp);
			return resp;
		}
		addReturn(req, resp);
		return resp;
	}
	
}
