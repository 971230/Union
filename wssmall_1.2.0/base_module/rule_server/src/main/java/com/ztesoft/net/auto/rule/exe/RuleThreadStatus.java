package com.ztesoft.net.auto.rule.exe;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class RuleThreadStatus {

	private boolean curr_local;
	private boolean all_local;
	private boolean check_auto_local;
	private int error_type = EcsOrderConsts.RULE_EXE_STATUS_1;
	private AtomicInteger exception_couter = new AtomicInteger();
	private boolean check_curr_relyon_rule = true;
	private boolean check_all_relyon_rule = true;
	//全部互拆空跑不算成功，其它的算成功
	private boolean allmutexrule;
	private int is_auto;
	private boolean plan_exe = true;
	
	private Map params;
	
	public boolean isPlan_exe() {
		return plan_exe;
	}
	public void setPlan_exe(boolean plan_exe) {
		this.plan_exe = plan_exe;
	}
	public boolean isCurr_local() {
		return curr_local;
	}
	public void setCurr_local(boolean curr_local) {
		this.curr_local = curr_local;
	}
	public boolean isAll_local() {
		//return all_local;
		return false;
	}
	public void setAll_local(boolean all_local) {
		this.all_local = all_local;
	}
	public boolean isCheck_auto_local() {
		return check_auto_local;
	}
	public void setCheck_auto_local(boolean check_auto_local) {
		this.check_auto_local = check_auto_local;
	}
	public int getError_type() {
		return error_type;
	}
	public void setError_type(int error_type) {
		this.error_type = error_type;
	}
	public AtomicInteger getException_couter() {
		return exception_couter;
	}
	public void setException_couter(AtomicInteger exception_couter) {
		this.exception_couter = exception_couter;
	}
	public boolean isCheck_curr_relyon_rule() {
		return check_curr_relyon_rule;
	}
	public void setCheck_curr_relyon_rule(boolean check_curr_relyon_rule) {
		this.check_curr_relyon_rule = check_curr_relyon_rule;
	}
	public boolean isCheck_all_relyon_rule() {
		return check_all_relyon_rule;
	}
	public void setCheck_all_relyon_rule(boolean check_all_relyon_rule) {
		this.check_all_relyon_rule = check_all_relyon_rule;
	}
	public boolean isAllmutexrule() {
		return allmutexrule;
	}
	public void setAllmutexrule(boolean allmutexrule) {
		this.allmutexrule = allmutexrule;
	}
	public int getIs_auto() {
		return is_auto;
	}
	public void setIs_auto(int is_auto) {
		this.is_auto = is_auto;
	}
	
	public void clearExceptionCounter(){
		exception_couter.set(0);
	}
	public Map getParams() {
		return params;
	}
	public void setParams(Map params) {
		this.params = params;
	}
	
}
