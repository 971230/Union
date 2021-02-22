package params.checkacctconfig.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.mall.core.model.CheckAcctConfig;

public class CheckAcctConfigReq extends ZteRequest {
	
	private CheckAcctConfig checkAcctConfig;
	
	private String system_id;//对账规格id
	private String dsystem_id;//对账规格id+年月日 20131024105

	public CheckAcctConfig getCheckAcctConfig() {
		return checkAcctConfig;
	}

	public void setCheckAcctConfig(CheckAcctConfig checkAcctConfig) {
		this.checkAcctConfig = checkAcctConfig;
	}

	public String getSystem_id() {
		return system_id;
	}

	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}

	public String getDsystem_id() {
		return dsystem_id;
	}

	public void setDsystem_id(String dsystem_id) {
		this.dsystem_id = dsystem_id;
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
