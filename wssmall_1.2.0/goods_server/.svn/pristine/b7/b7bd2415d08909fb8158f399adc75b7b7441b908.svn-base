package com.ztesoft.net.mall.core.workflow.core;

import com.ztesoft.net.sqls.SF;

import java.math.BigDecimal;
import java.util.Map;

public class JoinApply_CompManager_Audit extends JoinApply_SupplierManager_Audit {

	@Override
	public void doBusinisees(Integer flow_inst_id, String ref_obj_id) {
		super.doBusinisees(flow_inst_id, ref_obj_id) ;
		if("PASS".equals(this.audit_state) ){//审批通过
			String qryMapSql = SF.goodsSql("GOODS_TEMP_GET_BY_ID");
			String qryProductSql = SF.goodsSql("PRODUCT_GET_BY_GOODS_ID");
			Map goodTmep = this.baseDaoSupport.queryForMap(qryMapSql, ref_obj_id);
			
			String name = (String)goodTmep.get("name");
			String sn = (String)goodTmep.get("sn");
			String goods_id = (String)goodTmep.get("goods_id");
			

			BigDecimal price = (BigDecimal)goodTmep.get("price");
			
			String product_id =  this.baseDaoSupport.queryForString(qryProductSql, ref_obj_id );
			
			String sql = SF.goodsSql("GOODS_UPADTE") ;

			String priceLv = SF.goodsSql("GOODS_LV_PRICE_UPDATE_3");
			
			//this.baseDaoSupport.update("goods", goodTmep, "goods_id='"+ ref_obj_id+"'");

			this.baseDaoSupport.execute(priceLv, price,ref_obj_id,product_id) ;
		}
		
		if("NO".equals(this.audit_state) ){//审批不通过通过
			String sql = SF.goodsSql("GOODS_UPDATE_00X");
			this.baseDaoSupport.execute(sql, ref_obj_id) ;
		}
	}

}
