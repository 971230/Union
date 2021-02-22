package com.ztesoft.net.mall.core.workflow.core;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import services.ModHandlerInf;

import com.ztesoft.net.mall.core.workflow.util.ModInstVO;
import com.ztesoft.net.sqls.SF;

public class ModPrice_ProductManager_Audit extends FlowBasicProcess {

	private ModHandlerInf modInfoHandler ;
	public ModHandlerInf getModInfoHandler() {
		return modInfoHandler;
	}
	public void setModInfoHandler(ModHandlerInf modInfoHandler) {
		this.modInfoHandler = modInfoHandler;
	}
	@Override
	public void doBusinisees(Integer flow_inst_id, String ref_obj_id) {
		if("NO".equals(this.audit_state) ){//审批不通过
			String sql = SF.goodsSql("GOODS_UPDATE_BY_GOODS_ID_2");
			this.baseDaoSupport.execute(sql, ref_obj_id) ;
		}else if("PASS".equals(this.audit_state) ){//审批通过
			//商品状态
			String sql = SF.goodsSql("GOODS_UPDATE_BY_GOODS_ID_3");
			this.baseDaoSupport.execute(sql, ref_obj_id) ;
			
			List<ModInstVO> mods = modInfoHandler.getModsBy(flow_inst_id, ref_obj_id) ;
			boolean hasA = false ;
			
			if(mods != null && !mods.isEmpty()){
				for(ModInstVO modInfo : mods){
					String table_name = modInfo.getTable_name() ;
					String action = modInfo.getAction() ;
					if("A".equals(action) && !hasA){
						hasA = true ;
						sql = SF.goodsSql("GOODS_LV_PRICE_UPDATE_BY_GOODS_ID");
						this.baseDaoSupport.execute(sql, ref_obj_id) ;
					}else if("D".equals(action)){
						hasA = true ;
						sql = SF.goodsSql("GOODS_LV_PRICE_DEL");
						this.baseDaoSupport.execute(sql, ref_obj_id , modInfo.getRef_obj_id()) ;
					}else if("M".equals(action) ){
						if("es_goods".equals(table_name.toLowerCase())){//商品表
							sql = SF.goodsSql("GOODS_PRICE_BY_GOODS_ID");
							this.baseDaoSupport.execute(sql, modInfo.getNew_change() , modInfo.getRef_obj_id()) ;
						}else{//会员价格表
							sql = SF.goodsSql("GOODS_LV_PRICE_BY_GOODS_ID");
							this.baseDaoSupport.execute(sql, modInfo.getNew_change() , modInfo.getRef_obj_id()) ;
						}
					}
				}
			}
			
		}
//		else if("NEXT".equals(this.audit_state) ){//给领导
//			String sql = "update es_goods set audit_state='00X'  where goods_id=?" ;
//			this.baseDaoSupport.execute(sql, ref_obj_id) ;
//		}
	}

	public String handleCond(Integer flow_inst_id,String ref_obj_id){
		String priceSql = SF.goodsSql("HANDLE_COND");
		
		Map resultMap = this.baseDaoSupport.queryForMap(priceSql, flow_inst_id,ref_obj_id);
		
		String oldChangeStr = (String)resultMap.get("old_change");
		String newChangeStr = (String)resultMap.get("new_change");
		
		BigDecimal oldBig = new BigDecimal(oldChangeStr);
		BigDecimal newBig = new BigDecimal(newChangeStr);
		Double oldChangeDub = oldBig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		Double newChangeDub = newBig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		if(oldChangeDub>=newChangeDub){
			return "PASS";
		}else{
			return "NEXT";
		}
		
	}
	

}
