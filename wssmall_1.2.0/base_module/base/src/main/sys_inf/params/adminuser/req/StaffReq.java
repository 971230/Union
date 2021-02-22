package params.adminuser.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.util.StringUtil;

import params.ZteRequest;
import params.adminuser.resp.StaffInfoResp;

public class StaffReq extends ZteRequest<StaffInfoResp>{

	private String username;
	private String page;
	private String pageSize;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	
	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(username) || StringUtil.isEmpty(page) || StringUtil.isEmpty(pageSize)){
			throw new ApiRuleException("-1","缺失参数");
		}
	}
	@Override
	public String getApiMethodName() {
		return "workLogServ.staffList";
	}
}
