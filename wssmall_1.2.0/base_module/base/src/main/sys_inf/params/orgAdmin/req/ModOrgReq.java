package params.orgAdmin.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;

public class ModOrgReq extends ZteRequest{
	
	private String party_id;
	private String org_code;
	private String org_name;
	private String org_type_id;
	private String union_org_code;
	private String org_content;
	private String channel_type;
	
	public String getParty_id() {
		return party_id;
	}

	public void setParty_id(String party_id) {
		this.party_id = party_id;
	}

	public String getOrg_code() {
		return org_code;
	}

	public void setOrg_code(String org_code) {
		this.org_code = org_code;
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

	public String getUnion_org_code() {
		return union_org_code;
	}

	public void setUnion_org_code(String union_org_code) {
		this.union_org_code = union_org_code;
	}

	public String getOrg_content() {
		return org_content;
	}

	public void setOrg_content(String org_content) {
		this.org_content = org_content;
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

	public String getChannel_type() {
		return channel_type;
	}

	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}
	
}
