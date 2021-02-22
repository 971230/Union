package params.req;

import params.ZteRequest;
import params.resp.DespostBusResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;


/**
 * 预存金支付扣款入参
 * @author hu.yi
 * @date 2013.12.26
 */
public class DespostBillReq extends ZteRequest<DespostBusResp>{

	private String partnerId;
	private Integer amount;
	private String flag;
	
	
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(partnerId)){
			throw new ApiRuleException("-1","分销商id不能为空");
		}
		if(null == amount){
			throw new ApiRuleException("-1","金额不能为空");
		}
		if(StringUtil.isEmpty(flag)){
			throw new ApiRuleException("-1","金额标识不能为空");
		}
		
	}
	@Override
	public String getApiMethodName() {
		return "partnerServ.chargeBill";
	}
}
