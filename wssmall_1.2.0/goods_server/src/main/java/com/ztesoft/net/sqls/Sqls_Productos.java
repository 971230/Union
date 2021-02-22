package com.ztesoft.net.sqls;

/**
 * 
 * @author wu.i
 * 移植CRM代码
 *
 */
public class Sqls_Productos extends Sqls {
	/*static class SingletonHolder {
		static Sqls sqlsManager = new Sqls_Productos();
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
	
	public Sqls_Productos(){
		//SqlUtil.initSqls(Sqls_Productos.class, this , sqls) ;
	}
	
	/*public static Sqls getInstance() {
		//add by wui解决source_from固定不变的问题
		Sqls sqlsManager = new Sqls_Productos();
		return sqlsManager;
	}*/
	public String getPRODUCTO_LIST(){ 
		StringBuilder sql = new StringBuilder("select go.org_name org_id, pc.created_date,");
		sql.append(" (case when  pc.status = '0' then  '未发布'   when  pc.status = '1' then  '已发布'   when  pc.status = '2' then  '发布中' ");
		sql.append(" when  pc.status = '3' then  '发布失败' else '0'  end ) status ");
		sql.append("  from es_product_co pc ");
		sql.append("  inner join es_goods_org go    on pc.org_id = go.party_id");
		sql.append(" where 1 = 1 ");
		return	sql.toString();}
	public String getCHECK_SAVE(){return "select count(0) from es_product_co where product_id = ? ";}
	public String getPRODUCTO_ESTADOS(){return "select p.pkey key, p.pname value from es_dc_public p where p.stype=800420 order by p.pkey";}
}