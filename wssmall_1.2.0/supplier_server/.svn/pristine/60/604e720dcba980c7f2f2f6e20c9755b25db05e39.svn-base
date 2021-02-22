package com.ztesoft.net.mall.core.workflow.core;

import com.ztesoft.net.mall.core.workflow.core.FlowBasicProcess;

public class RegisterApply_CertificateManager_Audit extends FlowBasicProcess {

	@Override
	public void doBusinisees(Integer flow_inst_id, String ref_obj_id) {
		if("PASS".equals(this.audit_state) ){//审批通过
			String sql = "update es_supplier_certificate set record_state='1' where certificate_id=? " ;
			this.baseDaoSupport.execute(sql, ref_obj_id) ;
		}else if("NO".equals(this.audit_state)){
			String sql = "update es_supplier_certificate set record_state='-1' where certificate_id=? " ;
			this.baseDaoSupport.execute(sql, ref_obj_id) ;
		}
	}

}
