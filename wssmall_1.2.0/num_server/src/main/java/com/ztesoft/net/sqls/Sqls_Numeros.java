package com.ztesoft.net.sqls;

import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 
 * @author wu.i 移植CRM代码
 * 
 */
public class Sqls_Numeros extends Sqls {
	
	public String getNUMEROS_LIST_BATCH(){
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct n.no_id,n.source_from,n.dn_no,n.batch_id,g.group_name,"
				+ "r.region_name,n.region_id,n.deposit deposit,n.period period,n.lowest lowest,n.fee_adjust,"
				+ "n.created_date,e.org_id_str,");

		sql.append(" (case when n.is_lucky ='0' then '否' when n.is_lucky ='1' then '是' else '否' end) is_lucky ,");

		sql.append(" (case when  n.status='000' then '未使用' when  n.status='111' then '已使用'");
		sql.append(" when  n.status='001' then '已预占' when  n.status='00X' then '已作废' else '' end)  status,");

		sql.append(" (select pname from es_dc_public where pkey = n.no_classify and stype= " + Consts.DC_PUBLIC_STYPE_LHJB
				+ " and source_from = '"+ ManagerUtils.getSourceFrom() +"') no_classify  ,");

		sql.append(" (case when  e.status='0' then '未发布' when  e.status='1' then '已发布'");
		sql.append(" when  e.status='2' then '发布中' when  e.status='3' then '发布可修改' else '未发布' end)  distribute_status ");

		sql.append(" from es_no  n left join ES_NO_GROUP g on  n.group_id =g.group_id ");
		sql.append(" left join ES_NO_SEG c on n.seg_id = c.seg_id  ");
		sql.append(" left join ES_NO_CO e on e.no = n.dn_no  ");
		sql.append(" left join es_common_region r on n.region_id = r.region_id ");

		sql.append("  where 1 = 1  ");
		return sql.toString();
	}
	
	public String getNUMEROS_LIST_BATCH_PUBLISH(){
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct n.no_id,n.dn_no,n.batch_id,n.region_id,n.deposit,"
				+ "n.period,n.lowest,n.is_lucky,n.status,n.no_classify,n.created_date");
		sql.append(" from es_no  n left join ES_NO_GROUP g on  n.group_id =g.group_id ");
		sql.append(" left join ES_NO_SEG c on n.seg_id = c.seg_id  ");
		sql.append(" left join ES_NO_CO e on e.no = n.dn_no  ");
		sql.append("  where 1 = 1 and (e.status = '"+Consts.NUMBER_STATUS_0+"' or e.status is null) ");
		return sql.toString();
	}
	
	public String getQRY_NUMBERINFO_BY_ID(){
		return "select * from es_no a where a.dn_no=? and a.source_from=? and rownum<2";
	}
	
	public String getQRY_NUMBRE_LIMIT_BY_ORGID(){
		return "select * from es_no_co_limit where org_id =? and region_id = ? and source_from=? ";
	}
	
	public String getQRY_NUMBER_BY_ID(){
		return "select b.is_lucky, count(1) rule_id from es_no_co a left join es_no b on a.no=b.dn_no where 1=1 ";
	}
	
	public String getUPDATE_ES_NO_CO_STATE() {
		return "update es_no_co c set c.status=? where c.no=? and c.source_from=? ";
	}
	
	public String getGOODS_ORG_PARENT_ID(){
		return "select g.parent_party_id from es_goods_org g where g.party_id=?";
	}
	
	public String getFIND_NOCO_BY_NUMBER() {
		return "select * from es_no_co c where c.no=? and c.source_from=?";
	}

	public String getAUTO_PUBLISH_SQL() {
		return "select * from es_no n where n.dn_no not in (select no from es_no_co where org_id_str like ?) and n.status=? and n.region_id =? and n.is_lucky=? and rownum<?";
	}

	public String getALREADY_PUBLISH_NO_NUM() {
		return "select count(1) from es_no_co c where c.org_id_str like ? and c.no in (select t.dn_no from es_no t where t.region_id=? and t.is_lucky=?)";
	}

	public String getNUMBER_LIMIT_SUM_ORDINARY() {
		return "select sum(t.max_ordinary) max_ordinary from es_no_co_limit t where t.org_id=? and t.source_from=? ";
	}

	public String getNUMBER_LIMIT_SUM_LUCKY() {
		return "select sum(t.max_lucky) max_lucky from es_no_co_limit t where t.org_id=? and t.source_from=? ";
	}

	public String getNUMBER_LIMIT_AREALY_COUNT() {
		return "select count(1) from es_no_co t where 1=1 and t.source_from=?";
	}

	public String getGET_ORG_NAME_ID() {
		return "select t.org_name from es_goods_org t where t.party_id=? and t.source_from=?";
	}

	public String getNUMBER_CO_CHECK() {
		return "select id,no,org_id_str from es_no_co where 1=1 and source_from=? and no=? ";
	}

	public String getUPDATE_NUMBER_CO_PUBLISH() {
		return "update es_no_co n set n.org_id_str=? where n.no=? and n.source_from=? ";
	}

	public String getNUMEROS_LIST() {
		StringBuilder sql = new StringBuilder();
		sql.append("select n.no_id,n.source_from,n.dn_no,n.no_gen,n.charge_type,n.deposit,n.period,n.lowest," +
				"n.batch_id,g.group_name,r.region_name,n.region_id,n.fee_adjust,");

		sql.append(" (case when n.is_lucky ='0' then '否' when n.is_lucky ='1' then '是' else '否' end) is_lucky ,");

		sql.append(" (select pname from es_dc_public where n.status = pkey and stype = '10086')  status,");

		sql.append(" (select pname from es_dc_public where n.no_classify = pkey and stype = '10087')  no_classify  ,");

		sql.append(" (case when  e.status='0' then '未发布' when  e.status='1' then '已发布'");
		sql.append(" when  e.status='2' then '发布中' when  e.status='3' then '发布可修改' else '未发布' end)  distribute_status ");

		sql.append(" from es_no  n left join ES_NO_GROUP g on  n.group_id =g.group_id ");
		sql.append(" left join ES_NO_SEG c on n.seg_id = c.seg_id  ");
		sql.append(" left join ES_NO_CO e on e.no = n.dn_no  ");
		sql.append(" left join es_common_region r on n.region_id = r.region_id ");

		sql.append("  where 1 = 1  ");
		return sql.toString();
	}

	public String getNUMEROS_ESTATOS() {
		return "SELECT P.PKEY key, P.PNAME value FROM ES_DC_PUBLIC P WHERE P.STYPE=10086 ORDER BY P.PKEY";
	}

	public String getNUMEROS_LIBERACION() {
		return "SELECT P.PKEY key, P.PNAME value FROM ES_DC_PUBLIC P WHERE P.STYPE=10091 ORDER BY P.PKEY";
	}

	public String getNUMEROS_INTERNET() {
		return "SELECT P.PKEY key, P.PNAME value FROM ES_DC_PUBLIC P WHERE P.STYPE=10098 ORDER BY P.PKEY";
	}

	public String getNUMEROS_METODO() {
		return "SELECT P.PKEY key,p.pname value FROM ES_DC_PUBLIC P WHERE P.STYPE=10088 ORDER BY P.PKEY";
	}

	public String getNUMEROS_GRUPOS_LIST() {
		StringBuilder sql = new StringBuilder();
		sql.append("select t.group_id,t.group_code,t.group_name,t.group_desc,");
		sql.append(" (case when  t.status='00A' then '有效' when t.status='00X' then '无效' else '0' end)  status");
		sql.append(" from ES_NO_GROUP t where 1 = 1 and source_from ='"+ManagerUtils.getSourceFrom()+"' ");
		return sql.toString();
	}

	public String getGRUPOS_LIST() {
		return "select group_id key,group_name value from ES_NO_GROUP where 1 = 1 ";
	}

	public String getNUMEROS_GRUPOS_GET() {
		return "select * from ES_NO_GROUP where  1 = 1 and group_id = ?";
	}

	public String getNUMEROS_GRUPOS_DEL() {
		return "delete from  ES_NO_GROUP where 1 = 1 ";
	}

	public String getNUMEROS_GRUPO_ESTATOS() {
		return "select p.pkey key, p.pname value from es_dc_public p where p.stype=10090 order by p.pkey";
	}

	public String getREGLA_TIPOS() {
		return "select p.pkey key, p.pname value from es_dc_public p where p.stype=10087 order by p.pkey";
	}

	public String getNUMEROS_CHECK_UPDATE() {
		return "select group_id from ES_NO_GROUP where group_code = ? ";
	}

	public String getNUMEROS_CHECK_SAVE() {
		return "select count(0) from ES_NO_GROUP where  group_code = ?";
	}

	public String getREGLA_LIST() {
		StringBuilder sql = new StringBuilder();
		sql.append("select t.rule_id, t.rule_name,t.deposit,t.period,t.lowest, ");
		sql.append(" (case when t.no_classify='1' then '一类' when t.no_classify ='2' then '二类' ");
		sql.append(" when t.no_classify ='3' then '三类' when t.no_classify ='4' then '四类'  ");
		sql.append(" when t.no_classify ='5' then '五类' when t.no_classify ='11' then '+1类'  ");
		sql.append(" when t.no_classify ='12' then '+2类' when t.no_classify ='13' then '+3类'  ");
		sql.append(" when t.no_classify ='14' then '+4类' when t.no_classify ='15' then '+5类' else '0' end ) no_classify  ");
		sql.append("from  ES_NO_RULE t where 1 = 1 ");
		return sql.toString();
	}

	public String getREGLA_GET() {
		return "select * from ES_NO_RULE where  1 = 1 and rule_id = ?";
	}

	public String getREGLA_DEL() {
		return "delete from  ES_NO_RULE where 1 = 1 ";
	}

	public String getREGLA_CHECK_UPDATE() {
		return "select rule_id from ES_NO_RULE where rule_name = ? ";
	}

	public String getREGLA_CHECK_SAVE() {
		return "	select count(0) from ES_NO_RULE where  rule_name = ?";
	}

	public String getREGLA_ESTATOS() {
		return "select p.pkey key, p.pname value from es_dc_public p where p.stype=10087 order by p.pname";
	}

	public String getCIUDAD_LIST() {
		return "select a.seg_id, a.seg_no, b.region_id, b.region_name "
				+ " from ES_NO_SEG a, es_common_region b "
				+ " where a.region_id = b.region_id"
				+ " and a.source_from = b.source_from";
	}

	public String getCIUDAD_GET() {
		return "select * from ES_NO_SEG where  1 = 1 and seg_id = ?";
	}

	public String getCIUDAD_DEL() {
		return "delete from  ES_NO_SEG where 1 = 1 and seg_id = ?";
	}

	public String getCIUDAD_CHECK_UPDATE() {
		return "select seg_id from ES_NO_SEG where area_code = ? ";
	}

	public String getCIUDAD_CHECK_SAVE() {
		return "select count(0) from ES_NO_SEG where seg_no = ?";
	}

	String source = ManagerUtils.getSourceFrom();

	public String getESCONOLIMIT_CHECK_SAVE() {
		return "select count(0) from ES_NO_CO_LIMIT e where 1 =1 and e.source_from='"
				+ source + "'";
	}

	public String getESCONOLIMIT_LIST() {
		return "select distinct a.*,b.local_name,e.org_name from ES_NO_CO_LIMIT a,es_regions b,es_goods_org e "
				+ "where a.source_from='"
				+ source
				+ "' and a.region_id=b.region_id and a.org_id=e.party_id ";
	}

	public String getESCONOLIMIT_CHECK_UPDATE() {
		return "select count(0) from ES_NO_CO_LIMIT e where e.source_from='"
				+ source + "' ";
	}

	public String getESCONOLIMIT_GET() {
		return "select a.*,e.org_name from ES_NO_CO_LIMIT a,es_goods_org e where e.source_from='"
				+ source
				+ "' and e.party_id=a.org_id and a.org_id = ? and a.region_id= ?";
	}

	public String getNUMEROS_BAK_DEL() {
		return " delete from  ES_NO_BAK where 1 = 1 ";
	}
	
	public String getNO_INFO() {
		return " select a.id, a.no, a.org_id_str, "
				+ " b.no_id, b.batch_id, b.dn_no, b.no_gen, b.seg_id, b.region_id, b.status, "
				+ " b.group_id, b.is_lucky, b.no_classify, b.rule_id, b.charge_type, "
				+ " b.deposit as o_deposit, b.period, b.lowest, b.oper_id,"
				+ " b.created_date, b.status_date, b.order_id,  b.fee_adjust, "
				+ " (b.deposit-b.fee_adjust) as deposit, c.group_name "
				+ " from es_no_co a "
				+ " inner join es_no b on a.no = b.dn_no and a.source_from = b.source_from"
				+ " left join es_no_group c on b.group_id = c.group_id  "
				+ " where 1=1";
	}

	public String getNUMBER_SYN_INFO() {
		return " select a.id, a.no, a.org_id_str, "
				+ " b.no_id, b.batch_id, b.dn_no, b.no_gen, b.seg_id, b.region_id, b.status, "
				+ " b.group_id, b.is_lucky, b.no_classify, b.rule_id, b.charge_type, "
				+ " b.deposit as o_deposit, b.period, b.lowest, b.oper_id,"
				+ " b.created_date, b.status_date, b.order_id,  b.fee_adjust, "
				+ " (b.deposit-b.fee_adjust) as deposit, c.group_name "
				+ " from es_no_co a "
				+ " inner join es_no b on a.no = b.dn_no and a.source_from = b.source_from"
				+ " left join es_no_group c on b.group_id = c.group_id  "
				+ " where a.source_from = '" + ManagerUtils.getSourceFrom() + "'"
				+ " and a.batch_id=?";
	}

	public String getNUMEROS_REGIONS() {
		return "select region_id key,local_name value from es_regions where region_grade ='2'";
	}
	
	public String getNUMERO() {
		return "select count(0) from ES_NO where  dn_no = ?";
	}
	public String getNUMERO_TEMP() {
		return "select count(0) from ES_NO where  dn_no = ? and source_from ='"+ManagerUtils.getSourceFrom()+"' ";
	}
	
	public String getNUMERO_IMPORT_LOGS(){
		return " select batch_id,log_id,dn_no,no_gen,deposit,period,lowest,charge_type,fee_adjust,"
				+ " batch_amount,created_date,status_date,deal_num,deal_flag,group_code," 
				+ " deal_desc,b.realname oper_name,action_code "
				+ " from es_no_import_logs a "
				+ " left join es_adminuser b on a.oper_id=b.userid "
				+ " where 1=1 and a.source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	
	public String getNUMERO_SYNCH_LOG(){
		return "select co_id,co_name,batch_id,action_code,status,status_date,created_date,failure_desc,org_id_str "
				+ " from es_co_queue where service_code ='CO_HAOMA' "
				+ " and source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	
	public String getNUMERO_SYNCH_BATCH(){
		return "select batch_id from es_no_co where source_from='"+ManagerUtils.getSourceFrom()+"' and no=? ";
	}
	
	public String getNUMERO_SYNCH_NUM(){
		return "select count(*) amount from es_no_co where source_from ='"+ManagerUtils.getSourceFrom()+"' and batch_id=? ";
	}
	
	public String getUPDATE_NUMERO_IMPORT_LOGS(){
		return "update es_no_import_logs set deal_flag = ?, deal_desc = ? "
				+ " where 1=1 and log_id = ?";
	}
	
	public String getNUMERO_UPDATE_DATA(){
		return "select no_id,dn_no,no_gen,deposit,period,lowest,charge_type,fee_adjust from es_no where source_from='"+ManagerUtils.getSourceFrom()+"' and status='000' and dn_no=? ";
	}
	
	public String getNO_CO_DELETE(){
		return "delete from es_no_co where source_from='"+ManagerUtils.getSourceFrom()+"' and no=? ";
	}
	
	
	public String getNUMBER_SPEC(){
		return "select dn_no,no_gen,is_lucky,charge_type,deposit,period,lowest,fee_adjust,region_id from es_no where 1=1 ";
	}
	
	public String getNO_GROUP_ID(){
		return "select group_id from es_no_group where group_code=?";
	}
}