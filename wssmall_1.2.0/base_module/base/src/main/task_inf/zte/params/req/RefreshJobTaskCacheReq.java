package zte.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.resp.RefreshJobTaskCacheResp;

public class RefreshJobTaskCacheReq extends ZteRequest<RefreshJobTaskCacheResp> {

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.taskService.task.cache.refresh";
	}

}
