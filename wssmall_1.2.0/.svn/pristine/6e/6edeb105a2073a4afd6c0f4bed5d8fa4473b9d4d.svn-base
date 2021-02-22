package zte.params.member.req;

import java.util.Map;

import params.ZteRequest;
import zte.params.member.resp.AgentPageListResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class AgentPageListReq extends ZteRequest<AgentPageListResp>{
	
	@ZteSoftCommentAnnotationParam(name="代理商信息",type="String",isNecessary="Y",desc="userCode:代理商编码。userName:用户名。lanId:本地网。userType:用户类型",hasChild=true)
	private Map infoMap;
	@ZteSoftCommentAnnotationParam(name="分页页码",type="String",isNecessary="Y",desc="分页页码")
	private int page_index;
	@ZteSoftCommentAnnotationParam(name="分页大小",type="String",isNecessary="Y",desc="分页大小")
	private int page_size;
	
	
	public Map getInfoMap() {
		return infoMap;
	}

	public void setInfoMap(Map infoMap) {
		this.infoMap = infoMap;
	}

	public int getPage_index() {
		return page_index;
	}

	public void setPage_index(int page_index) {
		this.page_index = page_index;
	}

	public int getPage_size() {
		return page_size;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.memberService.agent.page";
	}
	
}
