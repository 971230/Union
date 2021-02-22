package com.ztesoft.net.sqls;

import com.ztesoft.net.mall.core.utils.ManagerUtils;
/**
 * 
 * @author wu.i
 * 移植CRM代码
 *
 */
public class Sqls_Print extends Sqls {

	/*static class SingletonHolder {
		static Sqls sqlsManager = new Sqls_Print();
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
	
	public Sqls_Print(){
		//SqlUtil.initSqls(Sqls_Print.class, this , sqls) ;
	}
	
	/*public static Sqls getInstance() {
		return SingletonHolder.sqlsManager;
	}*/
	// 配置的SQL...
	public String getTEMPLET_NAME_INFORAMTION()
	{
	    return "select a.* from es_invoice_print_model a where 1=1";
	}
	public String getINVOICE_PRINT_FILED()
	{
	    return "select e.* from es_invoice_print_field e";
	}
	public String getINVOICEMODE()
	{
	    return "select a.* from es_invoice_print_model a where a.model_cd=?";
	}
	public String getINVOICEMODEFIELD(){
		 return "select * from es_invoice_model_field a where a.model_cd=? and a.field_cd=?";
	}
	public String getDELETE_TEMPLATE()
	{
	    return "delete from es_invoice_model_field a where a.model_cd=?";
	}
	public String getINSERT_TEMPLATE_INFORMATION()
	{
	    return "insert into es_invoice_model_field(id,model_cd,field_cd,pos_x,pos_y,source_from) values(ES_INVOICE_MODEL_SEQUENCE.NEXTVAL,?,?,?,?,?)";
	}
	public String getSELECT_PRINT_FILED()
	{
	    return "select i.pos_x,i.pos_y,i.field_cd,e.field_nam from es_invoice_model_field i,es_invoice_print_field e where i.source_from='"+ManagerUtils.getSourceFrom()+"' and i.field_cd=e.field_cd and i.model_cd=?";
	}
	public String getPRINT_MODE_BACKGROUND()
	{
	    return "select a.paper_height,a.paper_width,a.img_url,a.model_cd from es_invoice_print_model a where a.model_cd=?";
	}
	public String getUPDATE_FIELD_POSITION()
	{
	    return "update es_invoice_model_field a set a.pos_x=?,a.pos_y=? where a.model_cd=? and a.field_cd=? ";
	}
	public String getHad_PRINT_FIELD()
	{
	    return "select e.field_cd,e.field_nam,i.pos_x,i.pos_y from es_invoice_print_field e,"+
               "(select a.model_cd,a.field_cd,a.pos_x,a.pos_y from es_invoice_model_field a where a.model_cd=?) i where e.field_cd=i.field_cd";
	}
	
	
}
