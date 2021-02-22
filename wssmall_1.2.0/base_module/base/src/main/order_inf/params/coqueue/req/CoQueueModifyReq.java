package params.coqueue.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;
import params.coqueue.resp.CoQueueModifyResp;

import java.util.List;

public class CoQueueModifyReq extends ZteRequest<CoQueueModifyResp> {
	@ZteSoftCommentAnnotationParam(name="消息队列ID",type="List",isNecessary="Y",desc="消息队列ID")
	private List co_ids;

	public List getCo_ids() {
		return co_ids;
	}

	public void setCo_ids(List co_ids) {
		this.co_ids = co_ids;
	}

	@Override
	public void check() throws ApiRuleException {
		if (co_ids==null || co_ids.size()==0) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "消息队列ID【co_id】不能为空！"));
        }
	}

	@Override
	public String getApiMethodName() {
		return "zte.service.coqueue.modify";
	}

}
