package params.adminuser.req;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;

/**
 * 修改用户信息参数实体
 * @author hu.yi
 * @date 2013.12.23
 */
public class AdminStatusUpdateReq extends ZteRequest<ZteResponse>{

	private Integer status;
	private String userid;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Override
	public void check() throws ApiRuleException {
		if(null == status){
			throw new ApiRuleException("-1","status不能为空");
		}
		if(StringUtil.isEmpty(userid)){
			throw new ApiRuleException("-1","userid不能为空");
		}
	}
	@Override
	public String getApiMethodName() {
		return "adminUserServ.updateLoginStatus";
	}
}
