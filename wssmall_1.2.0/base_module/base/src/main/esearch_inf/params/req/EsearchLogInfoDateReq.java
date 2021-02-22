package params.req;

import java.io.Serializable;
import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;

public class EsearchLogInfoDateReq extends ZteRequest<ZteResponse> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3796098348597503830L;

	/**
	 * 
	 */
	

	private String fromTime;
	
	private String toTime;
	
	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.esearchService.esearch.indexByDate";
	}
	
}