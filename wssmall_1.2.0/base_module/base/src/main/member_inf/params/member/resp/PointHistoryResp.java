package params.member.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.Page;
public class PointHistoryResp extends ZteResponse{

	@ZteSoftCommentAnnotationParam(name="历史积分列表",type="String",isNecessary="Y",desc="历史积分列表")
	private Page pointHistoryPage;

	public Page getPointHistoryPage() {
		return pointHistoryPage;
	}

	public void setPointHistoryPage(Page pointHistoryPage) {
		this.pointHistoryPage = pointHistoryPage;
	}
}
