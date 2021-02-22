package com.ztesoft.net.sqls;



/**
 * 
 * @author wu.i
 * 移植CRM代码
 *
 */
public class Sqls_Info extends Sqls {

	/*static class SingletonHolder {
		static Sqls sqlsManager = new Sqls_Info();
		static { 
			try{
				Map<String , String>  mysqls = sqlsManager.sqls;
				Class o_sql_local_class = Class.forName(sqlsManager.getClass().getName() + "_LOCAL");
				Sqls o_sql_local = (Sqls)o_sql_local_class.newInstance();
				SqlUtil.initSqls(o_sql_local_class, o_sql_local , mysqls) ;
			}catch(Exception ex){
				//throw new RuntimeException(ex);
			}
		}
	}*/
	
	public Sqls_Info(){
		//SqlUtil.initSqls(Sqls_Info.class, this , sqls) ;
	}
	
	/*public static Sqls getInstance() {
		return SingletonHolder.sqlsManager;
	}*/
	
	// 配置的SQL...
	public String getADCOLUMN_BY_ID(){return "select * from adcolumn where 1=1 and source_from = ? and acid = ?";}
	
	public String getORDER_TEMPLATE(){
		return "SELECT ot.order_template_id,ot.order_template_code,ot.order_template_version,ot.order_template_name,ot.order_template_type,ot.order_business_type,ot.order_area,ot.call_source_system,ot.exchange_protocol,ot.callback_info,ot.status,ot.basic_response_content,ot.sub_template_id,ot.create_staff_id,ot.create_date,ot.source_from,ot.catalogue_id FROM es_order_template ot";
	}
	//-----------表 es_order_template的sql-----------------------------
	public String getTEMPLATE_mod(){
		return "SELECT ot.order_template_id,ot.order_template_code,ot.order_template_version,ot.order_template_name,ot.order_template_type,ot.order_business_type,ot.order_area,ot.call_source_system,ot.exchange_protocol,ot.callback_info,ot.status,ot.basic_response_content,ot.sub_template_id,ot.create_staff_id,ot.create_date,ot.source_from,ot.catalogue_id FROM es_order_template ot"
				+" WHERE ot.order_template_code=? AND ot.order_template_id<>?";
	}
	
	public String getTEMPLATE_qry(){
		return "SELECT ot.order_template_id,ot.order_template_code,ot.order_template_version,ot.order_template_name,ot.order_template_type,ot.order_business_type,ot.order_area,ot.call_source_system,ot.exchange_protocol,ot.callback_info,ot.status,ot.basic_response_content,ot.sub_template_id,ot.create_staff_id,ot.create_date,ot.source_from,ot.catalogue_id FROM es_order_template ot"
				+" WHERE ot.order_template_code=?";
	}
	
	public String getQre_template_code_CataId(){
		return "SELECT ot.order_template_id,ot.order_template_code,ot.order_template_version,ot.order_template_name,ot.order_template_type,ot.order_business_type,ot.order_area,ot.call_source_system,ot.exchange_protocol,ot.callback_info,ot.status,ot.basic_response_content,ot.sub_template_id,ot.create_staff_id,ot.create_date,ot.source_from,ot.catalogue_id FROM es_order_template ot"
				+" WHERE ot.order_template_code=? AND ot.catalogue_id=?";
	}
	
	public String getINSERT_TEMPLATE(){
		return "INSERT INTO ES_ORDER_TEMPLATE(ORDER_TEMPLATE_ID,ORDER_TEMPLATE_CODE,ORDER_TEMPLATE_VERSION,ORDER_TEMPLATE_NAME,"
				+"ORDER_TEMPLATE_TYPE,ORDER_BUSINESS_TYPE,ORDER_AREA,CALL_SOURCE_SYSTEM,"
				+"EXCHANGE_PROTOCOL,CALLBACK_INFO,STATUS,BASIC_RESPONSE_CONTENT,SUB_TEMPLATE_ID,CREATE_STAFF_ID,CREATE_DATE,SOURCE_FROM,CATALOGUE_ID)"
				+" VALUES(SEQ_ORDER_TEMPLATE.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}
	
	public String getTEMPLATE_ID(){
		return "SELECT ot.order_template_id,ot.order_template_code,ot.order_template_version,ot.order_template_name,ot.order_template_type,ot.order_business_type,ot.order_area,ot.call_source_system,ot.exchange_protocol,ot.callback_info,ot.status,ot.basic_response_content,ot.sub_template_id,ot.create_staff_id,ot.create_date,ot.source_from,ot.catalogue_id FROM es_order_template ot WHERE ORDER_TEMPLATE_ID=? ";
	}
	
	public String getUPDATE_TPL(){
		return "UPDATE es_order_template ot SET ot.order_template_code=?,ot.order_template_version=?,ot.order_template_name=?,"
				+"ot.order_template_type=?,ot.order_business_type=?,ot.order_area=?,ot.call_source_system=?,ot.exchange_protocol=?,"
				+"ot.callback_info=?,ot.status=?,ot.basic_response_content=?,ot.sub_template_id=?,ot.create_staff_id=?,"
				+"ot.create_date=?,ot.source_from=?,ot.catalogue_id=? WHERE ot.order_template_id=? ";
	}
	
	public String getDEL_TPL(){
		return "DELETE FROM es_order_template WHERE order_template_id=?";
	}
	//------------------------------------------------------------------
	public String getDEL_NODE_TPLID(){
		return "DELETE FROM es_order_template_node a WHERE a.order_template_id =?";
	}
	
	public String getEsAttrDefVO(){
		return "SELECT ATTR_SPEC_ID,REL_TABLE_NAME,FIELD_ATTR_ID,FIELD_TYPE,OWNER_TABLE_FIELDS,ATTR_SPEC_ID,ATTR_SPEC_TYPE,SUB_ATTR_SPEC_TYPE FROM ES_ATTR_DEF WHERE REL_TABLE_NAME=?";
	}
	
	public String getInsertESAttrDef(){
		return "INSERT INTO ES_ATTR_DEF (ATTR_SPEC_ID,ATTR_SPEC_TYPE,SUB_ATTR_SPEC_TYPE "
				+" ,FIELD_NAME,FIELD_DESC ,FIELD_ATTR_ID,FIELD_TYPE,SOURCE_FROM,REL_TABLE_NAME,OWERN_TABLE_FIELDS)"
				+"VALUES(#ATTR_SPEC_ID,#ATTR_SPEC_TYPE,#SUB_ATTR_SPEC_TYPE "
				+" ,#FIELD_NAME,#FIELD_DESC ,#FIELD_ATTR_ID,#FIELD_TYPE,#SOURCE_FROM,#REL_TABLE_NAME,#OWERN_TABLE_FIELDS)";
	}
	
	public String getUpdateEsAttrDef() {
		return "UPDATE ES_ATTR_DEF T SET ATTR_SPEC_TYPE=#ATTR_SPEC_TYPE,SET SUB_ATTR_SPEC_TYPE=#SUB_ATTR_SPEC_TYPE "
				+ " ,SET FIELD_NAME=#FIELD_NAME,SET FIELD_DESC=#FIELD_DESC ,SET FIELD_ATTR_ID=#FIELD_ATTR_ID,SET FIELD_TYPE=#FIELD_TYPE,SET SOURCE_FROM=#SOURCE_FROM,SET REL_TABLE_NAME=#REL_TABLE_NAME,SET OWERN_TABLE_FIELDS=#OWERN_TABLE_FIELDS ";
	}
	
	public String getDelEsAttrDef(){
		return "DELETE FROM es_attr_def T WHERE T.ATTR_SPEC_ID=?";
	}
	
	public String getDelAttrTable(){
		return "DELETE FROM es_attr_table T WHERE T.ATTR_DEF_ID=?";
	}
	
	public String getEsAttrTableVO(){
		return "SELECT ATTR_DEF_ID,TABLE_NAME,SOURCE_FROM,HINT,FIELD_NAME,TABLE_DESC from es_attr_table WHERE table_name=?";
	}
}