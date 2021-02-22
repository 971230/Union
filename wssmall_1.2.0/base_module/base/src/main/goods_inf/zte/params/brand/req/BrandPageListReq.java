package zte.params.brand.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.BrandQueryParam;
import params.ZteRequest;

public class BrandPageListReq extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name="查询参数",type="String",isNecessary="N",desc="查询参数",hasChild=true)
	private BrandQueryParam brandQueryParam;
	@ZteSoftCommentAnnotationParam(name="第几页",type="String",isNecessary="Y",desc="第几页  默认50")
	private String page_index ="1";
	@ZteSoftCommentAnnotationParam(name="每页记录数",type="String",isNecessary="Y",desc="每页记录数：默认50")
	private String page_size ="50";
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.goodsService.brand.page";
	}

	public BrandQueryParam getBrandQueryParam() {
		return brandQueryParam;
	}

	public void setBrandQueryParam(BrandQueryParam brandQueryParam) {
		this.brandQueryParam = brandQueryParam;
	}

	public String getPage_index() {
		return page_index;
	}

	public void setPage_index(String pageIndex) {
		page_index = pageIndex;
	}

	public String getPage_size() {
		return page_size;
	}

	public void setPage_size(String pageSize) {
		page_size = pageSize;
	}

	
}
