package zte.net.ecsord.params.ems.resp;

import java.util.List;

import params.ZteResponse;
import zte.net.ecsord.params.ems.vo.EmsWaybillResp;

public class EmsLogisticsInfoSyncResp extends ZteResponse {

	private String success;
	private List<EmsWaybillResp> responseWaybills;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public List<EmsWaybillResp> getResponseWaybills() {
		return responseWaybills;
	}

	public void setResponseWaybills(List<EmsWaybillResp> responseWaybills) {
		this.responseWaybills = responseWaybills;
	}
}
