package com.ztesoft.net.app.base.core.model;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.util.StringUtil;

/**
 * 供货商员工
 * 
 * @author chenlijun
 * 
 */
public class SupplierStaff extends ZteRequest{

	private String staff_id;
	private String account_number;
	private String password;
	private String department;
	private String user_name;
	private String sex;
	private String id_card;
	private String phone;
	private String email;
	private String qq;
	private String register_time;
	private String record_state;

	private String userid;
	private AdminUser user;
	private String supplier_id;

	public String getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}

	public String getAccount_number() {
		return account_number;
	}

	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getRegister_time() {
		return register_time;
	}

	public void setRegister_time(String register_time) {
		this.register_time = register_time;
	}

	public String getRecord_state() {
		return record_state;
	}

	public void setRecord_state(String record_state) {
		this.record_state = record_state;
	}

	

	public AdminUser getUser() {
		return user;
	}

	public void setUser(AdminUser user) {
		this.user = user;
	}

	public String getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(staff_id) || StringUtil.isEmpty(supplier_id) || StringUtil.isEmpty(userid)){
			throw new ApiRuleException("-1","缺失参数");
		}
	}

	@Override
	public String getApiMethodName() {
		return "adminUserServ.bindingstaff";
	}

	
}
