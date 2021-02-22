package params.req;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;


/**
 * 预存金充值入参
 * @author hu.yi
 * @date 2013.12.26 
 */
public class DespostChargeReq extends ZteRequest<ZteResponse>{

	private String partnerId;
	private Integer amount;
	private String flag;
	private String tableName;
	private String orderId;
	
	
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
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(partnerId)){
			throw new ApiRuleException("-1","分销商id不能为空");
		}
		if(StringUtil.isEmpty(flag)){
			throw new ApiRuleException("-1","金额标识不能为空");
		}
		if(StringUtil.isEmpty(tableName)){
			throw new ApiRuleException("-1","所属表名不能为空");
		}
		if(StringUtil.isEmpty(orderId)){
			throw new ApiRuleException("-1","订单id不能为空");
		}
		if(null == amount){
			throw new ApiRuleException("-1","金额不能为空");
		}
		
	}
	@Override
	public String getApiMethodName() {
		return "partnerServ.charge";
	}
}
