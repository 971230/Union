package com.ztesoft.net.mall.core.model;

import com.ztesoft.api.ApiRuleException;
import params.ZteRequest;


/**
 * 支付请求对象
 * 
 * @author wui
 */
public class PayRequest extends ZteRequest implements java.io.Serializable {

	private Double amount;
	private String bankCode;
	private String paySource;
	private String order_id;
	private String service_type;
	private String type_code;
	/**
	 * 0订单ID 1批量ID
	 */
	private Integer payType;

	public PayRequest(Double amount, String bankCode, String paySource,
			String service_type, String typeCode) {
		this.amount = amount;
		this.bankCode = bankCode;
		this.paySource = paySource;
		this.service_type = service_type;
		this.type_code = typeCode;

	}


	public Integer getPayType() {
		return payType;
	}



	public void setPayType(Integer payType) {
		this.payType = payType;
	}



	public Double getAmount() {
		return amount;
	}



	public void setAmount(Double amount) {
		this.amount = amount;
	}



	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getPaySource() {
		return paySource;
	}

	public void setPaySource(String paySource) {
		this.paySource = paySource;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
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