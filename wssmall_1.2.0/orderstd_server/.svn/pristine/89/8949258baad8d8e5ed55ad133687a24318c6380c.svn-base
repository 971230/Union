package com.ztesoft.net.sqls;

import com.ztesoft.net.mall.core.utils.ManagerUtils;
/**
 * 
 * @author wu.i
 * 移植CRM代码
 *
 */
public class Sqls_Orderstd extends Sqls {
	
	public Sqls_Orderstd(){
		
	} 
	

	public String getACTIVE_TYPE_GET(){return "select (case "+
         " when b.cat_id = '690301000' then  5 "+
			" when b.cat_id = '690302000' then  4 "+
			" when b.cat_id = '690303000' then  3 "+
			" end) ative_type "+
			" from es_goods_rel a, es_goods b "+
			" where a.a_goods_id = ? "+
			" and b.type_id = 10001 "+
			" and b.type = 'product'" +
			" and b.source_from='"+ManagerUtils.getSourceFrom()+"'"+
			" and a.z_goods_id = b.goods_id";}
	
	
	public String getPRODUCT_MODEL(){return "select b.model_code as specification_code, "+
				" (select c.model_name from es_brand_model c where c.model_code=b.model_code  "+ManagerUtils.apSFromCond("c")+") as specification_name "+
				" from es_goods_rel a, es_goods b "+
				" where a.a_goods_id = ? "+
				" and b.type_id = 10000 "+
				" and b.type = 'product'"+
				" and b.source_from='"+ManagerUtils.getSourceFrom()+"'"+
				" and a.z_goods_id = b.goods_id";}
	
	public String getIS_IPHONE_PLAN(){return "select (case "+
        " when b.cat_id = '690102000' then  '1' "+
			" else '0' "+
			" end) is_iphone_plan "+
			" from es_goods_rel a, es_goods b "+
			" where a.a_goods_id = ? "+
			" and b.type_id = 10002 "+
			" and b.type = 'product' "+
			" and b.source_from='"+ManagerUtils.getSourceFrom()+"'"+
			" and a.z_goods_id = b.goods_id";}
	
	public String getCOLOR_GET(){return "select color color_code,(select pname from es_dc_public t "
			+ " where p.color=t.pkey and t.stype=10002"
			+ " and t.source_from = '" + ManagerUtils.getSourceFrom() + "') color_name "+
			" from es_goods g,es_product p, es_goods_rel r where g.goods_id=p.goods_id and p.product_id=r.product_id "+
			" and g.source_from='"+ManagerUtils.getSourceFrom()+"' and p.color is not null and a_goods_id=?";}
	
	public String getSERVICE_TEMP_INST_SELECT(){return "SELECT * FROM es_temp_inst WHERE 1=1 ";}
	
}
