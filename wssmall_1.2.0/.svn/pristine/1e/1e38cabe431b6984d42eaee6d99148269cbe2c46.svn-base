package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class ProxyGoodsPageQueryReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="分销商ID",type="String",isNecessary="Y",desc="userid：分销商ID。")
	private String userid;
	@ZteSoftCommentAnnotationParam(name="商品搜索关键字",type="String",isNecessary="N",desc="search_key：商品搜索关键字。")
	private String search_key;
	@ZteSoftCommentAnnotationParam(name="页数",type="String",isNecessary="Y",desc="pageNo：页数。")
	private int pageNo=1;
	@ZteSoftCommentAnnotationParam(name="分页大小",type="String",isNecessary="Y",desc="pageSize：分页大小。")
	private int pageSize=10;
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSearch_key() {
		return search_key;
	}

	public void setSearch_key(String search_key) {
		this.search_key = search_key;
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

	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.proxy.goods.query";
	}

}
