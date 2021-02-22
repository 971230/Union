package com.ztesoft.net.mall.core.workflow.core;

import com.ztesoft.net.mall.core.workflow.core.FlowBasicProcess;

public class RegisterApply_ResourceManager_Audit extends FlowBasicProcess {

	@Override
	public void doBusinisees(Integer flow_inst_id, String ref_obj_id) {
		if("PASS".equals(this.audit_state) ){//审批通过
			String sql = "update es_supplier_resources set record_state='1' where resources_id=? " ;
			this.baseDaoSupport.execute(sql, ref_obj_id) ;
		}else if("NO".equals(this.audit_state)){
			String sql = "update es_supplier_resources set record_state='-1' where resources_id=? " ;
			this.baseDaoSupport.execute(sql, ref_obj_id) ;
		}
	}

}
