package com.ztesoft.net.mall.core.workflow.core;

import com.ztesoft.net.sqls.SF;

public class Advice_Manage_Audit extends FlowBasicProcess {

	@Override
	public void doBusinisees(Integer flow_inst_id, String ref_obj_id) {
		if("PASS".equals(this.audit_state) ){//审批不通过通过
			String sql = SF.goodsSql("ADV_UPDATE_1");
			this.baseDaoSupport.execute(sql, ref_obj_id) ;
		}
		
		if("NO".equals(this.audit_state) ){//审批不通过通过
			String sql = SF.goodsSql("ADV_UPDATE_2");
			this.baseDaoSupport.execute(sql, ref_obj_id) ;
		}
		
	}

}

