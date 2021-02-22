package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.util.StringUtil;

import params.ZteRequest;
import params.resp.PartnerAddrResp;


/**
 * 查询分销商地址入参
 * @author hu.yi
 * @date 2013.12.26
 */
public class PartnerAddrReq extends ZteRequest<PartnerAddrResp>{

	private String addressNum;

	public String getAddressNum() {
		return addressNum;
	}

	public void setAddressNum(String addressNum) {
		this.addressNum = addressNum;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(addressNum)){
			throw new ApiRuleException("-1","地址id不能为空");
		}
	}

	@Override
	public String getApiMethodName() {
		return "partnerServ.getPartnerAddressByAddr_id";
	}
}
