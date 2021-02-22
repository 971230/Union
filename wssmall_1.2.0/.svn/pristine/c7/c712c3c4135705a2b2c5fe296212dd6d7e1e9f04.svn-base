package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.util.StringUtil;

import params.ZteRequest;
import params.resp.PartnerExistsResp;

/**
 * 判断分销商是否存在入参
 * @author hu.yi
 * @date 2013.12.26
 */
public class PartnerExistsReq extends ZteRequest<PartnerExistsResp>{

	private String partner_code;
	private String partner_name;
	
	
	public String getPartner_code() {
		return partner_code;
	}
	public void setPartner_code(String partner_code) {
		this.partner_code = partner_code;
	}
	public String getPartner_name() {
		return partner_name;
	}
	public void setPartner_name(String partner_name) {
		this.partner_name = partner_name;
	}
	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(partner_code) && StringUtil.isEmpty(partner_name)){
			throw new ApiRuleException("-1","分销商id或分销商名称不能为空");
		}
		
	}
	@Override
	public String getApiMethodName() {
		return "partnerServ.isPartnerExits";
	}
}
