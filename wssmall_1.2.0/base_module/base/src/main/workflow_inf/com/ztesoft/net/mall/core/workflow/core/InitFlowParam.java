package com.ztesoft.net.mall.core.workflow.core;

public class InitFlowParam {
	
	
	private String flow_code ;
	private String apply_user ;
	private String ref_obj_id ;
	private String apply_reson;

	public InitFlowParam(){
		
	}
	
	public InitFlowParam(String flow_code  , String apply_user ,String ref_obj_id,String  apply_reson ){
		this.flow_code = flow_code ;
		this.apply_user = apply_user ;
		this.ref_obj_id = ref_obj_id ;
		this.apply_reson = apply_reson;
	}
	
	public InitFlowParam(String flow_code  , String apply_user ,String ref_obj_id ){
		this.flow_code = flow_code ;
		this.apply_user = apply_user ;
		this.ref_obj_id = ref_obj_id ;
	}
	
	public String getFlow_code() {
		return flow_code;
	}
	public void setFlow_code(String flow_code) {
		this.flow_code = flow_code;
	}
	public String getApply_user() {
		return apply_user;
	}
	public void setApply_user(String apply_user) {
		this.apply_user = apply_user;
	}
	public String getRef_obj_id() {
		return ref_obj_id;
	}
	public void setRef_obj_id(String ref_obj_id) {
		this.ref_obj_id = ref_obj_id;
	}
	public String getApply_reson() {
		return apply_reson;
	}
	public void setApply_reson(String apply_reson) {
		this.apply_reson = apply_reson;
	}
}
