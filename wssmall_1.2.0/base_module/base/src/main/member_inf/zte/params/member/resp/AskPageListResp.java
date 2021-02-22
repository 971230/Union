package zte.params.member.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.Page;

import params.ZteResponse;

public class AskPageListResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="问题分页数据",type="String",isNecessary="Y",desc="问题分页数据")
	private Page askPage;

	public Page getAskPage() {
		return askPage;
	}

	public void setAskPage(Page askPage) {
		this.askPage = askPage;
	}
	
}
