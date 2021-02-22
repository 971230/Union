package com.ztesoft.net.mall.core.workflow.core;

public class ModPrice_DeptManager_Audit extends ModPrice_ProductManager_Audit {

	@Override
	public void doBusinisees(Integer flow_inst_id, String ref_obj_id) {
		super.doBusinisees(flow_inst_id, ref_obj_id) ;
	}
	
	@Override
	public String handleCond(Integer flow_inst_id,String ref_obj_id){
		return super.handleCond(flow_inst_id, ref_obj_id);
	}

}
