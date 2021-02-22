package params.req;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;


/**
 * 预存金后续操作传参实体
 * @author hu.yi
 * @date 2013.12.26
 */
public class DespostAfterPayReq extends ZteRequest<ZteResponse>{

	private String ordeSeq;
	private Integer orderAmount;
	private String  retnCode;
	private String tranDate;
	
	
	
	public String getOrdeSeq() {
		return ordeSeq;
	}
	public void setOrdeSeq(String ordeSeq) {
		this.ordeSeq = ordeSeq;
	}
	public Integer getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getRetnCode() {
		return retnCode;
	}
	public void setRetnCode(String retnCode) {
		this.retnCode = retnCode;
	}
	public String getTranDate() {
		return tranDate;
	}
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(ordeSeq)){
			throw new ApiRuleException("-1","订单序列不能为空");
		}
		if(null == orderAmount){
			throw new ApiRuleException("-1","金额不能为空");
		}
		if(StringUtil.isEmpty(retnCode)){
			throw new ApiRuleException("-1","支付编码不能为空");
		}
		if(StringUtil.isEmpty(tranDate)){
			throw new ApiRuleException("-1","日期不能为空");
		}
		
	}
	@Override
	public String getApiMethodName() {
		return "partnerServ.afterPay";
	}
}
