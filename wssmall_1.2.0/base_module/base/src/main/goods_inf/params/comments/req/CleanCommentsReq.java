package params.comments.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;

public class CleanCommentsReq extends ZteRequest {
	
	private String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
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