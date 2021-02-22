package zte.net.ecsord.params.sf.req;

import java.util.List;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import zte.net.ecsord.params.sf.vo.WaybillRoute;


/**
 * 
 * @author sguo 
 * 订单信息同步
 * 
 */
public class RoutePushServiceRequset extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="路由信息列表",type="String",isNecessary="Y",desc="WaybillRoute：最多10个")
	private List<WaybillRoute> WaybillRoute;
	
	public List<WaybillRoute> getWaybillRoute() {
		return WaybillRoute;
	}

	public void setWaybillRoute(List<WaybillRoute> waybillRoute) {
		WaybillRoute = waybillRoute;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.sf.routePushService";
	}

}
