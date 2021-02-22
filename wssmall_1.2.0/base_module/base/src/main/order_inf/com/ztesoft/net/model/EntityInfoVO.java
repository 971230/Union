/**
 * 
 */
package com.ztesoft.net.model;

import java.io.Serializable;

/**
 * @author ZX
 * EntityInfoVO.java
 * 订单实物信息
 * 2014-11-19
 */
public class EntityInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String entity_type; // 实物类型
	private String sku;
	private String entity_name; // 实物名称
	private String serial_number; // 串号
	private String input_serial_number; // 输入串号
	private String state; // 状态
	private String type; // 1-实物礼品，2-终端，3-号卡
	
	public String getEntity_type() {
		return entity_type;
	}
	public void setEntity_type(String entity_type) {
		this.entity_type = entity_type;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getEntity_name() {
		return entity_name;
	}
	public void setEntity_name(String entity_name) {
		this.entity_name = entity_name;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getInput_serial_number() {
		return input_serial_number;
	}
	public void setInput_serial_number(String input_serial_number) {
		this.input_serial_number = input_serial_number;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
