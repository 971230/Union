package params.req;

import java.io.Serializable;
import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.model.ESearchData;

public class EsearchUpdateReq extends ZteRequest<ZteResponse> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9182191205167829538L;
	private ESearchData esData;
	
	private boolean updateNull;
	/**
	 * 更新延迟设置；由于esearch有数据插入刷新机制，防止数据插入未刷新没能找到数据更新报错
	 */
	private boolean updateDelay;

	public boolean isUpdateNull() {
		return updateNull;
	}

	public void setUpdateNull(boolean UpdateNull) {
		updateNull = UpdateNull;
	}
	
	public boolean isUpdateDelay() {
		return updateDelay;
	}

	public void setUpdateDelay(boolean updateDelay) {
		this.updateDelay = updateDelay;
	}

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
		return "zte.esearchService.esearch.update";
	}
	
}
