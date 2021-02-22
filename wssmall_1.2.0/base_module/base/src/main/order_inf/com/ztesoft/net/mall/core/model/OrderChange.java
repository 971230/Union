package com.ztesoft.net.mall.core.model;

/**
 * 订单变动对象
 * 
 * @author wui
 */
public class OrderChange implements java.io.Serializable {

	private String order_id;
	private String item_id;
	private String table_name;
	private String field_name;
	private String old_value;
	private String new_value;
	private String create_date;
	private Integer sequ;
	private String old_value_desc;
	private String new_value_desc;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	public String getOld_value() {
		return old_value;
	}

	public void setOld_value(String old_value) {
		this.old_value = old_value;
	}

	public String getNew_value() {
		return new_value;
	}

	public void setNew_value(String new_value) {
		this.new_value = new_value;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public Integer getSequ() {
		return sequ;
	}

	public void setSequ(Integer sequ) {
		this.sequ = sequ;
	}

	public String getOld_value_desc() {
		return old_value_desc;
	}

	public void setOld_value_desc(String old_value_desc) {
		this.old_value_desc = old_value_desc;
	}

	public String getNew_value_desc() {
		return new_value_desc;
	}

	public void setNew_value_desc(String new_value_desc) {
		this.new_value_desc = new_value_desc;
	}

}