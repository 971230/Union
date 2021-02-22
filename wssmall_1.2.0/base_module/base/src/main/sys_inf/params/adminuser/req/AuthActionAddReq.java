package params.adminuser.req;

import params.ZteRequest;
import params.adminuser.resp.AuthEditResp;

import com.ztesoft.api.ApiRuleException;

/**
 * 权限入参实体
 * @author hu.yi
 * @date 2013.12.24
 */
public class AuthActionAddReq extends ZteRequest<AuthEditResp>{

	private String actid;
	private String name;
	private String type;
	private String objvalue;
	private String userid;
	
	
	public String getActid() {
		return actid;
	}
	public void setActid(String actid) {
		this.actid = actid;
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
	public String getObjvalue() {
		return objvalue;
	}
	public void setObjvalue(String objvalue) {
		this.objvalue = objvalue;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		return "authServ.add";
	}
}
