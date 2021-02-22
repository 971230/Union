package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.util.StringUtil;

import params.ZteRequest;
import params.resp.PartnerAddrListResp;

/**
 * 查询分销商店铺列表入参
 * @author hu.yi
 * @date 2013.12.26
 */
public class PartnerAddrListReq extends ZteRequest<PartnerAddrListResp>{

	@ZteSoftCommentAnnotationParam(name="分销商id",type="String",isNecessary="Y",desc="分销商id")
	private String partner_id;

	public String getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(partner_id)){
			throw new ApiRuleException("-1","分销商id不能为空");
		}
		
	}

	@Override
	public String getApiMethodName() {
		return "partnerServ.getPartnerAddress";
	}
}
