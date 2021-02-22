package params.req;

import java.io.Serializable;
import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.model.ESearchData;

public class EsearchGetReq extends ZteRequest<ZteResponse> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6256999737128767429L;
	/**
	 * 
	 */

	private ESearchData esData;

	public ESearchData getEsData() {
		return esData;
	}

	public void setEsData(ESearchData esData) {
		this.esData = esData;
	}

	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.esearchService.esearch.get";
	}
	
}
