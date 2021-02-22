package params.member.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.mall.core.model.PointHistory;
import params.ZteRequest;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class PointHistoryReq extends ZteRequest{
	
	@ZteSoftCommentAnnotationParam(name="历史积分参数",type="String",isNecessary="Y",desc="历史积分参数",hasChild=true)
	private PointHistory pointHistory;
	@ZteSoftCommentAnnotationParam(name="分页大小",type="String",isNecessary="Y",desc="pageSize：分页大小， 不能为空。")
	private String pageSize;
	@ZteSoftCommentAnnotationParam(name="页数",type="String",isNecessary="Y",desc="pageIndex：页数， 不能为空。")
	private String pageIndex;

	public PointHistory getPointHistory() {
		return pointHistory;
	}

	public void setPointHistory(PointHistory pointHistory) {
		this.pointHistory = pointHistory;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.memberServer.member.point.history";
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

}
