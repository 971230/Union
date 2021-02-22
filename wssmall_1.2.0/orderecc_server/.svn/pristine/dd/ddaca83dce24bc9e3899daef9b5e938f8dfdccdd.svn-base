package com.ztesoft.net.sqls;

import com.ztesoft.net.mall.core.utils.ManagerUtils;



public class Sqls_Ecc extends Sqls {

	public Sqls_Ecc(){
		
	}
	
	//实物货品是否同步WMS
	public String getPRODUCT_IS_SYNC_WMS(){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("SELECT COUNT(*) FROM ES_CO_QUEUE_BAK ECQ");
		sqlBuff.append(" LEFT JOIN ES_PRODUCT EP ON EP.PRODUCT_ID = ECQ.OBJECT_ID AND ECQ.SOURCE_FROM = EP.SOURCE_FROM");
		sqlBuff.append(" LEFT JOIN ES_GOODS EG ON EG.GOODS_ID = EP.GOODS_ID AND EP.SOURCE_FROM = EG.SOURCE_FROM");
		sqlBuff.append(" WHERE EP.GOODS_ID = ? AND ECQ.ORG_ID_BELONG = '20001' AND ECQ.SOURCE_FROM = '" + ManagerUtils.getSourceFrom() + "'");
		sqlBuff.append(" AND EG.TYPE = 'product' AND STATUS = 'XYCG'");
		return sqlBuff.toString();
	}
	
	//实物礼品是否同步WMS
	public String getGIFT_IS_SYNC_WMS(){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("SELECT COUNT(*) FROM ES_CO_QUEUE_BAK ECQ");
		sqlBuff.append(" LEFT JOIN ES_PRODUCT EP ON EP.PRODUCT_ID = ECQ.OBJECT_ID AND ECQ.SOURCE_FROM = EP.SOURCE_FROM");
		sqlBuff.append(" LEFT JOIN ES_GOODS EG ON EG.GOODS_ID = EP.GOODS_ID AND EP.SOURCE_FROM = EG.SOURCE_FROM");
		sqlBuff.append(" WHERE EP.SKU = ? AND ECQ.ORG_ID_BELONG = '20001' AND ECQ.SOURCE_FROM = '" + ManagerUtils.getSourceFrom() + "'");
		sqlBuff.append(" AND EG.TYPE = 'product' AND STATUS = 'XYCG'");
		return sqlBuff.toString();
	}
	
	//礼品是否存在
	public String getGIFT_IS_EXISTS(){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("SELECT COUNT(*) FROM ES_GOODS EG WHERE EG.NAME = ? AND EG.SOURCE_FROM = '" + ManagerUtils.getSourceFrom() + "'");
		return sqlBuff.toString();
	}
	
	//根据goods_id查找商品是否存在
	public String getGOODS_IS_EXISTS_0(){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("SELECT COUNT(1) FROM ES_GOODS G LEFT JOIN ES_GOODS_PACKAGE PKG ");
		sqlBuff.append(" ON PKG.GOODS_ID = G.GOODS_ID AND PKG.SOURCE_FROM = G.SOURCE_FROM ");
		sqlBuff.append(" WHERE G.SOURCE_FROM = '" + ManagerUtils.getSourceFrom() + "' AND G.TYPE = 'goods' AND G.GOODS_ID = ?");
		return sqlBuff.toString();
	}
	
	//根据sku查找商品是否存在
	public String getGOODS_IS_EXISTS_1(){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("SELECT COUNT(1) FROM ES_GOODS WHERE SKU = ? AND SOURCE_FROM = '" + ManagerUtils.getSourceFrom() + "'");
		return sqlBuff.toString();
	}
	
	//根据p_code、sn查找商品是否存在
	public String getGOODS_IS_EXISTS_2(){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("SELECT COUNT(1) FROM ES_GOODS G LEFT JOIN ES_GOODS_PACKAGE PKG ");
		sqlBuff.append(" ON PKG.GOODS_ID = G.GOODS_ID AND PKG.SOURCE_FROM = G.SOURCE_FROM WHERE G.SOURCE_FROM = '" + ManagerUtils.getSourceFrom() + "'");
		return sqlBuff.toString();
	}
	
	/**
	 * 获取es_ecc_fun表中配置的校验原子数据
	 * @return
	 */
	public String getES_ECC_FUN_LIST(){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("SELECT a.*, b.pname verify_mode, c.pname verify_status");
		sqlBuff.append(" FROM ES_ECC_FUN a, es_dc_public b, es_dc_public c");
		sqlBuff.append(" WHERE 1 = 1");
		sqlBuff.append(" and a.impl = b.codea");
		sqlBuff.append(" and b.stype = '80101'");
		sqlBuff.append(" and a.status = c.codea");
		sqlBuff.append(" and c.stype = '80102'");
		sqlBuff.append(" and b.source_from = c.source_from");
		sqlBuff.append(" and a.source_from = b.source_from ");
		return sqlBuff.toString();
	}

	
	public String getFUN_GET(){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("SELECT * FROM ES_ECC_FUN WHERE fun_id=? and source_from = '" + ManagerUtils.getSourceFrom() + "'");
		return sqlBuff.toString();
	}

	//业务校验列表
	public String getBIZ_PAGE(){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("SELECT * FROM ES_ECC_BIZ WHERE STATUS='00A' AND biz_type='003'");
		return sqlBuff.toString();
	}	
	
	//业务匹配因子数据
	public String getBIZREQUIREMENTS_LIST(){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("SELECT I.* FROM ES_ECC_BIZ_FACTOR_CFG I WHERE I.FACTOR_TPYE='001' AND I.STATUS='00A'");
		return sqlBuff.toString();
	}	
	
	//业务匹配因子数据:反例
	public String getBIZREQUIREMENTS_ANTI_LIST(){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("SELECT I.* FROM ES_ECC_BIZ_FACTOR_CFG I WHERE I.FACTOR_TPYE='002' AND I.STATUS='00A'");
		return sqlBuff.toString();
	}
	
	//业务校验功能数据
	public String getFUN_LIST(){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("SELECT I.* FROM ES_ECC_FUN I WHERE I.STATUS='00A'");
		return sqlBuff.toString();
	}

	
	//查询过滤校验因子
	public String getCHECK_FILTER_LIST(){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("select * from (select eebr.attr_code,eebr.required_id,efc.attr_name,");
		sqlBuff.append("eebr.z_value,eebr.z_cvalue,decode(eebr.pre_log,'&&','AND','||','OR',eebr.pre_log) pre_log,eebr.opt_type,eebr.source_from,");
		sqlBuff.append("eebr.status,eeb.biz_name,eebr.opt_index from es_ecc_biz_requirements eebr");
		sqlBuff.append(" left join es_ecc_biz eeb on eeb.biz_id = eebr.biz_id and eeb.source_from = eebr.source_from");
		sqlBuff.append(" left join es_ecc_biz_factor_cfg efc on efc.attr_code = eebr.attr_code and efc.source_from = eebr.source_from");
		sqlBuff.append(" where eebr.source_from = '" + ManagerUtils.getSourceFrom() + "' and eeb.biz_type='001' order by eebr.biz_id, eebr.opt_index )");
		return sqlBuff.toString();
	}
	
	//修改过滤校验因子数据
	public String getCHECK_FILTER_UPDATE_0(){
		return "update es_ecc_biz_requirements ebr set ebr.pre_log = ?,ebr.z_value = ?,ebr.z_cvalue = ?,ebr.status = ? where ebr.required_id = ?";
	}
}
