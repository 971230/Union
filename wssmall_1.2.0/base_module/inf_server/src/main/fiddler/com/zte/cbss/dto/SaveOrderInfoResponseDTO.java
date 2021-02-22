package com.zte.cbss.dto;

import java.io.Serializable;

public class SaveOrderInfoResponseDTO implements Serializable{

	private static final long serialVersionUID = 5648113118927682676L;

	private String code;//结果编码。0-开户写卡成功，1-开户失败，2-开户成功，写卡失败。-1-超时，-2-未知异常
	
	private String message;//结果描述
	
	private String usim;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUsim() {
		return usim;
	}

	public void setUsim(String usim) {
		this.usim = usim;
	}
	
}
