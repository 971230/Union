package zte.params.orderctn.req;

import params.ZteRequest;
import zte.params.orderctn.resp.FailAndExpQueueHandleResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class FailAndExpQueueHandleReq extends ZteRequest<FailAndExpQueueHandleResp> {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "队列id", type = "String", isNecessary = "Y", desc = "队列id,订单队列数据转工作数据需要传入")
	private String co_id;

	@ZteSoftCommentAnnotationParam(name = "是否是异常系统调用 ", type = "String", isNecessary = "N", desc = "是否是异常系统调用 :true是异常系统调用,false正常系统")
	private boolean is_exception = false;

	public String getCo_id() {
		return co_id;
	}

	public void setCo_id(String co_id) {
		this.co_id = co_id;
	}

	public boolean isIs_exception() {
		return is_exception;
	}

	public void setIs_exception(boolean is_exception) {
		this.is_exception = is_exception;
	}

	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "zte.service.orderqueue.orderCollectionWork";
	}
}
