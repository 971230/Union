package params.adminuser.req;

import params.ZteRequest;
import params.adminuser.resp.AuthListResp;

import com.ztesoft.api.ApiRuleException;


/**
 * 权限分页列表入参实体
 * @author hu.yi
 * @date 2013.12.24
 */
public class AuthPageReq extends ZteRequest<AuthListResp>{

	
	private String act_id;
	private String name;
	private String type;
	private int page;
	private int pageSize;
	
	public String getAct_id() {
		return act_id;
	}

	public void setAct_id(String act_id) {
		this.act_id = act_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
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
		return "authServ.authPage";
	}
}
