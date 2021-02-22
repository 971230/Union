package params.member.req;

import com.ztesoft.api.ApiRuleException;
import params.ZteRequest;

import java.util.Map;

public class AgentReq extends ZteRequest{
	
	private Map infoMap;
	private int page_index;
	private int page_size;
	
	
	public Map getInfoMap() {
		return infoMap;
	}

	public void setInfoMap(Map infoMap) {
		this.infoMap = infoMap;
	}

	public int getPage_index() {
		return page_index;
	}

	public void setPage_index(int page_index) {
		this.page_index = page_index;
	}

	public int getPage_size() {
		return page_size;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
