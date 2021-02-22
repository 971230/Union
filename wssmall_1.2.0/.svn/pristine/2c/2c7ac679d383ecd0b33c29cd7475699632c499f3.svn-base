package params.adminuser.req;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.mall.core.consts.Operations;

public class AdminUserByObjPageReq extends ZteRequest<ZteResponse>{

	
	public AdminUser queryUser;
	public AdminUser loginUser;
	public Operations operations;
	
	public String pageNo;
	public String pageSize;
	
	
	
	public AdminUser getQueryUser() {
		return queryUser;
	}
	public void setQueryUser(AdminUser queryUser) {
		this.queryUser = queryUser;
	}
	public AdminUser getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(AdminUser loginUser) {
		this.loginUser = loginUser;
	}
	public Operations getOperations() {
		return operations;
	}
	public void setOperations(Operations operations) {
		this.operations = operations;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public void check() throws ApiRuleException {
		if(null == queryUser){
			throw new ApiRuleException("-1","缺失queryUser实体");
		}
		if(null == loginUser){
			throw new ApiRuleException("-1","缺失loginUser实体");
		}
		if(null == operations){
			throw new ApiRuleException("-1","缺失operations实体");
		}
		if(StringUtil.isEmpty(pageNo)){
			throw new ApiRuleException("-1","pageNo不能为空");
		}
		if(StringUtil.isEmpty(pageSize)){
			throw new ApiRuleException("-1","pageSize不能为空");
		}
	}
	@Override
	public String getApiMethodName() {
		return "adminUserServ.queryUser";
	}
}
