package params.req;

import java.io.Serializable;
import java.util.List;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;

public class EsearchLogInfoIdsReq extends ZteRequest<ZteResponse> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7602682591490639138L;
	/**
	 * 
	 */
	private List<String> idList;
	
	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.esearchService.esearch.indexByIds";
	}
	
}


