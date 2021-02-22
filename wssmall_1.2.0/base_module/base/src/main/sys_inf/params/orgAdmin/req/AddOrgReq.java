package params.orgAdmin.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;

public class AddOrgReq extends ZteRequest{
	
	private String parent_party_id;
	private String org_code;
	private String org_name;
	private String org_type_id;
	private String union_org_code;
	private String org_content;
	private String org_level;
	private String lan_id;
	private String region_id;
    private String channel_type;
	public String getParent_party_id() {
		return parent_party_id;
	}

	public void setParent_party_id(String parent_party_id) {
		this.parent_party_id = parent_party_id;
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

	public String getOrg_level() {
		return org_level;
	}

	public void setOrg_level(String org_level) {
		this.org_level = org_level;
	}

	public String getLan_id() {
		return lan_id;
	}

	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
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
