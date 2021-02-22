package zte.net.ecsord.params.sf.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

@SuppressWarnings("rawtypes")
public class RouteServiceRequest extends ZteRequest {

	private static final long serialVersionUID = -2594028257859924773L;

	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		
		return "com.zte.unicomService.sf.routeService";
	}
	
	@ZteSoftCommentAnnotationParam(name="查询类别",type="String",isNecessary="N",desc="tracking_type：1-根据运单号查询、2-根据订单号查询，默认2")
	private Integer tracking_type = 2;
	@ZteSoftCommentAnnotationParam(name="查询号",type="String",isNecessary="Y",desc="tracking_number：如果查询类别为1，则此值为运单号；如果查询类别为2，则此职为订单号。如果有多个单号，以逗号分隔。最多10个。")
	private String tracking_number;
	@ZteSoftCommentAnnotationParam(name="查询方法",type="String",isNecessary="N",desc="method_type：1-标准查询、2-定制查询，默认1")
	private Integer method_type = 1;

	public Integer getTracking_type() {
		return tracking_type;
	}

	public void setTracking_type(Integer tracking_type) {
		this.tracking_type = tracking_type;
	}

	public String getTracking_number() {
		return tracking_number;
	}

	public void setTracking_number(String tracking_number) {
		this.tracking_number = tracking_number;
	}

	public Integer getMethod_type() {
		return method_type;
	}

	public void setMethod_type(Integer method_type) {
		this.method_type = method_type;
	}

}
