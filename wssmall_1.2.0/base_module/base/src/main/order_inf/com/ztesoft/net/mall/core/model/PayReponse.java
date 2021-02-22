package com.ztesoft.net.mall.core.model;

import params.ZteResponse;

import java.util.Iterator;
import java.util.Map;

/**
 * 支付返回对象
 * 
 * @author wui
 */
public class PayReponse  extends ZteResponse implements java.io.Serializable {
	private String transaction_id;
	private Map result;
	

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public Map getResult() {
		return result;
	}

	public void setResult(Map result) {
		this.result = result;
	}
	
	@Override
	public String toString(){
		StringBuffer jsonBuffer = new StringBuffer();
		Iterator it = result.keySet().iterator();
		while (it.hasNext()) {
			String key = (String)it.next();
			String value = (String)result.get(key);
			jsonBuffer.append(key).append(":").append("'").append(value).append("',");
		}
		return jsonBuffer.substring(0, jsonBuffer.length()-1).toString();
		
	}
	

}