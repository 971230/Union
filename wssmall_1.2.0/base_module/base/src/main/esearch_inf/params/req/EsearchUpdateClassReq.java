package params.req;

import java.io.Serializable;
import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.model.ESearchData;

public class EsearchUpdateClassReq extends ZteRequest<ZteResponse> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1981024151586858661L;
	/**
	 * 更新延迟设置；由于esearch有数据插入刷新机制，防止数据插入未刷新没能找到数据更新报错
	 */
	private boolean updateDelay;
	
	private ESearchData esData;

	public ESearchData getEsData() {
		return esData;
	}

	public void setEsData(ESearchData esData) {
		this.esData = esData;
	}

	public boolean isUpdateDelay() {
		return updateDelay;
	}

	public void setUpdateDelay(boolean updateDelay) {
		this.updateDelay = updateDelay;
	}

	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.esearchService.esearch.updateClass";
	}
	
}
