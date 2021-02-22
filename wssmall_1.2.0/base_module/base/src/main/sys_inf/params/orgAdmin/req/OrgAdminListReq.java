package params.orgAdmin.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;

public class OrgAdminListReq extends ZteRequest{
	
	private String login_org_id;
	private String party_id;
	private String org_name;
	private String org_type_id;
	private String org_content;
	private String  union_org_code;
	private String lan_id;
	private int pageIndex;
	private int pageSize;
	
	public String getLogin_org_id() {
		return login_org_id;
	}

	public void setLogin_org_id(String login_org_id) {
		this.login_org_id = login_org_id;
	}

	public String getParty_id() {
		return party_id;
	}

	public void setParty_id(String party_id) {
		this.party_id = party_id;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getOrg_type_id() {
		return org_type_id;
	}

	public void setOrg_type_id(String org_type_id) {
		this.org_type_id = org_type_id;
	}

	public String getOrg_content() {
		return org_content;
	}

	public void setOrg_content(String org_content) {
		this.org_content = org_content;
	}

	public String getUnion_org_code() {
		return union_org_code;
	}

	public void setUnion_org_code(String union_org_code) {
		this.union_org_code = union_org_code;
	}

	public String getLan_id() {
		return lan_id;
	}

	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
