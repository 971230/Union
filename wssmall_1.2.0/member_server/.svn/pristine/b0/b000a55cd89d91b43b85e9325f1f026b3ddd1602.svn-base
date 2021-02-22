package zte.service;

import java.util.List;

import javax.annotation.Resource;

import services.ServiceBase;
import zte.net.iservice.IRegionService;
import zte.params.region.req.RegionsGetReq;
import zte.params.region.req.RegionsListReq;
import zte.params.region.resp.RegionsGetResp;
import zte.params.region.resp.RegionsListResp;

import com.ztesoft.net.mall.core.model.Regions;
import com.ztesoft.net.mall.core.service.IRegionsManager;
import com.ztesoft.rop.annotation.ServiceMethodBean;

@ServiceMethodBean(version = "1.0")
public class RegionService extends ServiceBase implements IRegionService {

	@Resource
	private IRegionsManager regionsManager;

	@Override
	public RegionsListResp listRegions(RegionsListReq req) {
		List<Regions> list = null;
		if (RegionsListReq.PROVICE.equals(req.getRegion_type())) {
			list = regionsManager.listProvince();
		} else {
			list = regionsManager.listChildren(req.getRegion_parent_id());
		}
		RegionsListResp resp = new RegionsListResp();
		resp.setRegionList(list);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public RegionsGetResp getRegion(RegionsGetReq req) {	
		Regions region = null;
		if(RegionsGetReq.ID_COND_TYPE.equals(req.getRegion_cond_type())){
			region = regionsManager.get(req.getRegion_id());
		}
		// by name
		else{
			region = regionsManager.getByName(req.getLocal_name());
		}
//		Regions regions = regionsManager.get(req.getRegion_id());
		RegionsGetResp resp = new RegionsGetResp();
		resp.setRegions(region);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}
}
