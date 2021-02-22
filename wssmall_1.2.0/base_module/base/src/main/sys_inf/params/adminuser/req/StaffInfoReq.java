package params.adminuser.req;

import params.ZteRequest;
import params.adminuser.resp.StaffInfoResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.util.StringUtil;

/**
 * 客户信息查询实体
 * @author hu.yi
 * @date 2013.12.23
 */
public class StaffInfoReq extends ZteRequest<StaffInfoResp>{

	private String party_id;
	private String staff_id;
	private String staff_name;
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
	public String getParty_id() {
		return party_id;
	}
	public void setParty_id(String party_id) {
		this.party_id = party_id;
	}
	public String getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}
	public String getStaff_name() {
		return staff_name;
	}
	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}
	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(party_id) || StringUtil.isEmpty(page) || StringUtil.isEmpty(pageSize)){
			throw new ApiRuleException("-1","缺失参数");
		}
	}
	@Override
	public String getApiMethodName() {
		return "adminUserServ.getStaffList";
	}
	
}
