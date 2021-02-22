package com.ztesoft.net.sqls;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
/**
 * 
 * @author wu.i
 * 移植CRM代码
 *
 */
public class Sqls_Base extends Sqls {

	
	
	public Sqls_Base(){
		//SqlUtil.initSqls(Sqls_Base.class, this , sqls) ;
	}
	
	
	
	public String getGET_MENU_LIST_0(){return "select * from menu where deleteflag = '0' order by sorder asc";}
    
	public String getQUERY_CO_QUEUE_BY_ID(){return "select * from es_co_queue where 1=1 and co_id = ?";}
    public String getQUERY_CO_QUEUE_ALL(){return "select * from es_co_queue where source_from='"+ManagerUtils.getSourceFrom()+"' ";}
    public String getQUERY_CO_QUEUE_BAK_ALL(){return "select * from es_co_queue_bak where 1=1";}
    public String getLOCK_CO_QUEUE_BY_ID(){return "update es_co_queue "
			+ " set status = ?, status_date="+ DBTUtil.current()
			+ " where 1=1 and co_id = ?";}
    
    //shengchan
    public String getLOCK_CO_QUEUE_BY_ID_2(){return "update es_co_queue_bak "
			+ " set deal_status = ? "
			+ " where 1=1 and co_id = ?";}
	
	public String getUPDATE_CO_QUEUE_BY_ID(){return "update es_co_queue "
			+ " set status = ?, failure_desc = ?, "
			+ " deal_num = deal_num + 1, "
			+ " status_date="+ DBTUtil.current()
			+ " where 1=1 "
			+ " and co_id = ?";}
	
	public String getMODIFY_CO_QUEUE_BY_ID(){
		return "update es_co_queue "
			+ " set status = ?, failure_desc = ?, status_date="+ DBTUtil.current()
			+ " where 1=1 "
			+ " and co_id = ?";
	}
	
	public String getDELETE_CO_QUEUE_BY_ID(){return "delete from es_co_queue where 1=1 and co_id = ?";}
	
	
	public String getQRY_SHORT_CUT_MENU(){
		return "SELECT * FROM es_user_smenu WHERE user_id = ? AND disabled=0 "+ManagerUtils.apSFromCond("")+" ORDER BY sort DESC";
	}
	
	public String getTABLE_COLUM_TYPE(){
		return "select a.DATA_TYPE from user_tab_columns a where a.TABLE_NAME = ? and a.COLUMN_NAME = ?";
	}
	
	public String getUPDATE_CO_QUEUE_STATUS_WFS(){
		return "update es_co_queue set status='"+Consts.CO_QUEUE_STATUS_WFS+"',deal_num=0,failure_desc=null where status='"+Consts.CO_QUEUE_STATUS_XYSB+"' and co_id=? ";
	}

	public String getUPDATE_CO_QUEUE_SERVICE_CODE_AND_COID(){
		return "update es_co_queue a set a.service_code = ? ,a.co_name = ? where co_id = ?  ";
	}
	
	public String getCO_QUEUE_GUIJI_LIST(){
		return "select t.co_id,t.co_name,t.batch_id,'CO_GUIJI_NEW' service_code,t.action_code,t.object_type,t.object_id,t.contents,t.status, "+
				" t.status_date,t.oper_id,t.created_date,t.req_class,t.deal_num,t.failure_desc,t.send_date,t.resp_date,t.source_from, "+
				" t.org_id_str,t.org_id_belong,t.url from es_co_queue_bak t where source_from='"+ManagerUtils.getSourceFrom()+"' and service_code=? and created_date>=to_date(?,'yyyy/mm/dd hh24:mi:ss') ";
	}
	
	public String getORDER_OUTER_LIST(){
		return "select * from es_order_outer where create_time>=to_date(?,'yyyy/mm/dd hh24:mi:ss') and source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	
	public String getCO_QUEUE_GUIJI_GET(){
		return "select t.co_id,t.co_name,t.batch_id,'CO_GUIJI_NEW' service_code,t.action_code,t.object_type,t.object_id,t.contents,t.status, "+
				" t.status_date,t.oper_id,t.created_date,t.req_class,t.deal_num,t.failure_desc,t.send_date,t.resp_date,t.source_from, "+
				" t.org_id_str,t.org_id_belong,t.url from #tab_name t where source_from='"+ManagerUtils.getSourceFrom()+"' and service_code=? and object_id=? and rownum<2";
	}
	
	public String getORDER_OUTER_GET(){
		return "select * from es_order_outer where source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	
	public String getGOODS_GET(){
		return "select * from es_goods where goods_id=? ";
	}
	
	public String getGOODS_RELATIONS(){
		return "select * from es_goods_rel where a_goods_id=? ";
	}
	
	public String getGOODS_PACKAGE_GET(){
		return "select * from es_goods_package where goods_id=? ";
	}
	
	public String getPRODUCTS_LIST(){
		return "select t.* from es_goods t where t.goods_id in(select t.z_goods_id from es_goods_rel t where t.a_goods_id=?)";
	}
	
	public String getPRODUCT_TABLE_GET(){
		return "select * from es_product where goods_id=? ";
	}
	
	public String getPRODUCT_TABLE_LIST(){
		return "select t.* from es_product t where t.goods_id in(select t.z_goods_id from es_goods_rel t where t.a_goods_id=?)";
	}
	
	public String getPMT_GOODS(){
		return "select * from es_pmt_goods where goods_id=? ";
	}
	
	public String getPROMOTION_GET(){
		return "select * from es_promotion where pmt_id=? ";
	}
	
	public String getPROMOTION_ACTIVITY_GET(){
		return "select t.* from es_promotion_activity t where t.id=? ";
	}
	
	public String getATTR_CODE_GET (){
		return "select dc_sql from es_dc_sql where source_from='"+ManagerUtils.getSourceFrom()+"' and dc_name=?";
	}
	
}
