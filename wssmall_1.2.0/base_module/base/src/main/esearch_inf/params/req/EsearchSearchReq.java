package params.req;

import java.io.Serializable;
import java.util.Map;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;

public class EsearchSearchReq extends ZteRequest<ZteResponse> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1675739002169509055L;

	private String indexName;
	
	private String typeName;
	
	private Map<String,String> qsParams;
	
	private Map<String,String> boolParams;
	
	
	
	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Map<String, String> getQsParams() {
		return qsParams;
	}

	public void setQsParams(Map<String, String> qsParams) {
		this.qsParams = qsParams;
	}

	public Map<String, String> getBoolParams() {
		return boolParams;
	}

	public void setBoolParams(Map<String, String> boolParams) {
		this.boolParams = boolParams;
	}

	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.esearchService.esearch.search";
	}
	
}
