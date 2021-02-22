package params.adminuser.req;

import java.util.Map;

import com.ztesoft.api.ApiRuleException;
import params.ZteRequest;
import params.ZteResponse;

/**
 * 修改用户信息参数实体
 * @author hu.yi
 * @date 2013.12.23
 */
public class AdminBossUpdateReq extends ZteRequest<ZteResponse>{

	private Map<String, String> bossPwdMap;
	
	public Map<String, String> getBossPwdMap() {
		return bossPwdMap;
	}
	public void setBossPwdMap(Map<String, String> bossPwdMap) {
		this.bossPwdMap = bossPwdMap;
	}
	
	
	@Override
	public void check() throws ApiRuleException {
		if(null == bossPwdMap){
			throw new ApiRuleException("-1","bossPwdMap不能为空");
		}
	}
	@Override
	public String getApiMethodName() {
		return "adminUserServ.updateBossPwd";
	}
}
