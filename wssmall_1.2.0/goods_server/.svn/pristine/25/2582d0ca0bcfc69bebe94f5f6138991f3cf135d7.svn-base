package com.ztesoft.net.mall.core.workflow.core;

import com.ztesoft.net.sqls.SF;

public class JoinApply_SupplierManager_Audit extends FlowBasicProcess {

	@Override
	public void doBusinisees(Integer flow_inst_id, String ref_obj_id) {
		if("NO".equals(this.audit_state) ){//审批不通过通过
			String sql = SF.goodsSql("GOODS_UPDATE_BY_GOODS_ID_1");
			this.baseDaoSupport.execute(sql, ref_obj_id) ;
		}
	}

}
