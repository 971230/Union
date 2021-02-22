package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class GoodsSearchPageListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="搜索uri",type="String",isNecessary="Y",desc="uri:搜索uri")
	private String uri;
	@ZteSoftCommentAnnotationParam(name="页码",type="String",isNecessary="Y",desc="页码")
	private int pageNo=1;
	@ZteSoftCommentAnnotationParam(name="每页数量",type="String",isNecessary="Y",desc="每页数量")
	private int pageSize=10;
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

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public void check() throws ApiRuleException {
		if(uri==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"uri不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goods.searchByUrl";
	}

}
