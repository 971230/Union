package zte.net.ecsord.params.pub.resp;

import java.util.List;
import java.util.Map;

import params.ZteResponse;

public class RegionListResp extends ZteResponse {

	List<Map> regionList;

	public List<Map> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<Map> regionList) {
		this.regionList = regionList;
	}
}
