package com.ztesoft.rule.core.module.cfg;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.ztesoft.rule.core.util.Const;

public class BizPlan {
	//方案信息
	private String plan_code;
	private String plan_id ;
	private String plan_name;
	private String exec_time;
	private String exec_cycle;
	private String cycle_id;
	
	
	private String flow_code ;
	private String global_obj_code ;
	
	
	private String lan_id;
	private String financial_type;
	private String plan_type;
	private String year_type;
	
	private String ctrl_type ;
	private String ctrl_val ;

	private String run_type ;
	
	private String status_cd ;
	
	private Integer thread_num  =  10 ;//Const.PLAN_DEF_PROCESS_THREADS;
	
	//完成度(默认为100,0容忍)
	private int success_rate = Const.PLAN_SUCCESS_RATE ;
	
	
	
	//当前方案参与则人数
	private long partnerNum  ;
	
	
	private AtomicLong execParterNum = new AtomicLong(0) ;
	
	
	//存储：外部传递参数
	private Map paramMap ;
	
	private Map mapData = new HashMap() ;
	
	
	public BizPlan(){
		
	}
	
//	public boolean canContinue(){
//		
//	}
	
	//FIXME 如若新增业务相关属性，此方法需要修改！！！
	public Map toMap(){
//		Map tmp = BeanUtils.describe(this) ;
		if(mapData.isEmpty()){
			doMapSetter();
		}
		return mapData ;
	}
	
	/**
	 * 每次获取最新值
	 * @return
	 */
	public Map getFreshMap(){
		doMapSetter() ;
		return mapData ; 
	}
	
	private void doMapSetter(){
		if(paramMap != null )//设置外部调用上下文相关参数信息
			mapData.putAll(paramMap) ;
		
		mapData.put("plan_code", plan_code) ;
		mapData.put("plan_id", plan_id) ;
		mapData.put("plan_name", plan_name) ;
		mapData.put("exec_time", exec_time) ;
		mapData.put("cycle_id", cycle_id) ;
		mapData.put("flow_code", flow_code) ;
		mapData.put("financial_type",financial_type) ;
		mapData.put("plan_type", plan_type) ;
		mapData.put("exec_cycle", exec_cycle) ;
		
	}
	
	public String getPlan_id() {
		return plan_id;
	}


	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}


	public String getFlow_code() {
		return flow_code;
	}


	public void setFlow_code(String flow_code) {
		this.flow_code = flow_code;
	}


	public String getGlobal_obj_code() {
		return global_obj_code;
	}


	public void setGlobal_obj_code(String global_obj_code) {
		this.global_obj_code = global_obj_code;
	}



	public String getPlan_code() {
		return plan_code;
	}

	public void setPlan_code(String plan_code) {
		this.plan_code = plan_code;
	}

	public String getPlan_name() {
		return plan_name;
	}

	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}

	public String getExec_time() {
		return exec_time;
	}

	public void setExec_time(String exec_time) {
		this.exec_time = exec_time;
	}

	public String getExec_cycle() {
		return exec_cycle;
	}

	public void setExec_cycle(String exec_cycle) {
		this.exec_cycle = exec_cycle;
	}

	public String getCycle_id() {
		return cycle_id;
	}

	public void setCycle_id(String cycle_id) {
		this.cycle_id = cycle_id;
	}

	public String getLan_id() {
		return lan_id;
	}

	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}

	public String getFinancial_type() {
		return financial_type;
	}

	public void setFinancial_type(String financial_type) {
		this.financial_type = financial_type;
	}

	public String getPlan_type() {
		return plan_type;
	}

	public void setPlan_type(String plan_type) {
		this.plan_type = plan_type;
	}

	public String getYear_type() {
		return year_type;
	}

	public void setYear_type(String year_type) {
		this.year_type = year_type;
	}

	public String getCtrl_type() {
		return ctrl_type;
	}

	public void setCtrl_type(String ctrl_type) {
		this.ctrl_type = ctrl_type;
	}

	public String getCtrl_val() {
		return ctrl_val;
	}

	public void setCtrl_val(String ctrl_val) {
		this.ctrl_val = ctrl_val;
	}

	public Map getMapData() {
		return mapData;
	}

	public void setMapData(Map mapData) {
		this.mapData = mapData;
	}

	public String getRun_type() {
		return run_type;
	}

	public void setRun_type(String run_type) {
		this.run_type = run_type;
	}

	public Map getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map paramMap) {
		this.paramMap = paramMap;
	}

	public String getStatus_cd() {
		return status_cd;
	}

	public void setStatus_cd(String status_cd) {
		this.status_cd = status_cd;
	}

	public Integer getThread_num() {
		return thread_num;
	}

	public void setThread_num(Integer thread_num) {
		if(thread_num != null && thread_num > 0)
			this.thread_num = thread_num;
	}

	public int getSuccess_rate() {
		return success_rate;
	}

	public void setSuccess_rate(int success_rate) {
		this.success_rate = success_rate;
	}

}
