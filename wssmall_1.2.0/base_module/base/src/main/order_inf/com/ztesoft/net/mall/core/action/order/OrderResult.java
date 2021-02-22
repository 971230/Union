package com.ztesoft.net.mall.core.action.order;

import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.PayReponse;

import java.util.ArrayList;
import java.util.List;

public class OrderResult {
	private Order order;
	private List<Button> buttons = new ArrayList<Button>(); //按钮对象
	
	private PayReponse payReponse;
	
	private String code ="0000"; //返回错误信息编码
	private String message;//返回错误信息内容
	

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<Button> getButtons() {
		return buttons;
	}

	
	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}
	
	
	public void addButtons(Button button ) {
		buttons.add(button);
	}

	public PayReponse getPayReponse() {
		return payReponse;
	}

	public void setPayReponse(PayReponse payReponse) {
		this.payReponse = payReponse;
	}

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
	
	
	
	

}
