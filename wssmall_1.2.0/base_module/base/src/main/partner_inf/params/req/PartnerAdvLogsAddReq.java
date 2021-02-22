package params.req;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.mall.core.model.AdvanceLogs;


/**
 * 预存款日志添加入参
 * @author hu.yi
 * @date 2013.12.26
 */
public class PartnerAdvLogsAddReq extends ZteRequest<ZteResponse>{

	private AdvanceLogs advanceLogs;

	public AdvanceLogs getAdvanceLogs() {
		return advanceLogs;
	}

	public void setAdvanceLogs(AdvanceLogs advanceLogs) {
		this.advanceLogs = advanceLogs;
	}

	@Override
	public void check() throws ApiRuleException {
		if(null == advanceLogs){
			throw new ApiRuleException("-1","请传入日志实体");
		}		
	}

	@Override
	public String getApiMethodName() {
		return "partnerServ.addAdvLogs";
	}
}
