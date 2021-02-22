package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.GnotifyPageListResp;

public class GnotifyPageListReq extends ZteRequest<GnotifyPageListResp> {
	@ZteSoftCommentAnnotationParam(name="页码",type="String",isNecessary="Y",desc="页码")
	private int pageNo=1;
	@ZteSoftCommentAnnotationParam(name="每页数量",type="String",isNecessary="Y",desc="每页数量")
	private int pageSize=50;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.gnoift.page";
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
