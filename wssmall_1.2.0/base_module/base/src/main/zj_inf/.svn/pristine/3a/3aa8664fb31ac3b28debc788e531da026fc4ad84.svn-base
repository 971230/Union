package zte.net.ecsord.params.ems.req;

import java.util.List;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;
import zte.net.ecsord.params.sf.vo.WaybillRoute;

public class EmsRoutePushServiceReq extends ZteRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5485678907452949605L;
	@ZteSoftCommentAnnotationParam(name="路由信息列表",type="String",isNecessary="Y",desc="WaybillRoute：最多10个")
	private List<WaybillRoute> WaybillRoute;
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
	}

	@Override
	public String getApiMethodName() {
		return null;
	}

	public List<WaybillRoute> getWaybillRoute() {
		return WaybillRoute;
	}

	public void setWaybillRoute(List<WaybillRoute> waybillRoute) {
		WaybillRoute = waybillRoute;
	}

}
