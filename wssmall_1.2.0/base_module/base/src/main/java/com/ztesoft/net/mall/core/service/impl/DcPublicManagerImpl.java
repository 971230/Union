package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

import model.EsearchSpec;
import model.EsearchSpecvalues;

public class DcPublicManagerImpl extends BaseSupport implements IDcPublicInfoManager {
	
	@Override
	public List list(){
		List list1 = this.baseDaoSupport.queryForList("select * from dc_public where source_from is not null or source_from is null order by sortby");
		List list2 = null;
		try{
			list2 = this.baseDaoSupport.queryForList("select * from es_dc_public_ext where source_from is not null or source_from is null order by sortby");
		}catch(Exception ex){
			
		}
		if(list1!=null){
			if(list2!=null)list1.addAll(list2);
		}else{
			list1 = list2;
		}
		return list1;
	}
	@Override
	public List list(String stype){
		List list1 =null;
		try{
		 list1 = this.baseDaoSupport.queryForList("select * from dc_public where (source_from is not null or source_from is null)  and stype = "+stype+" order by sortby");
		}catch(Exception ex){
			
		}
		List list2 = null;
		try{
			list2 = this.baseDaoSupport.queryForList("select * from es_dc_public_ext where (source_from is not null or source_from is null) and stype = '"+stype+"' order by sortby");
		}catch(Exception ex){
			
		}
		if(list1!=null){
			if(list2!=null)list1.addAll(list2);
		}else{
			list1 = list2;
		}
		return list1;
	}
	@Override
	public List getDuPublicByKey(String stype) {
		return this.baseDaoSupport.queryForList("select * from dc_public where stype = ? and ( source_from is not null or source_from is null ) order by sortby", stype);
	}

	@Override
	public List getRegions() {
		return this.baseDaoSupport.queryForList("select * from es_regions where source_from is not null or source_from is null ");
	}
	
	@Override
	public Map getRegionSingle(String region_id) {
		return this.baseDaoSupport.queryForMap("select * from es_regions where  region_id = ? and (source_from is not null or source_from is null) ", region_id);
	}
	

	@Override
	public List getLogiCompanyPersons() {
		return this.baseDaoSupport.queryForList("select a.* from es_logi_company_person a where a.source_from is not null or a.source_from is null ");
	}

	@Override
	public List getDictRelations() {
		return this.baseDaoSupport.queryForList("select a.* from es_dc_public_dict_relation a where a.source_from is not null or a.source_from is null ");
	}
	
	@Override
	public List getDictRelationStypes() {
		return this.baseDaoSupport.queryForList("select distinct a.stype from es_dc_public_dict_relation a where a.source_from is not null or a.source_from is null ");
	}

	@Override
	public List getdcPublicExtByKey() {
		return this.baseDaoSupport.queryForList("select a.* from es_dc_public_ext a where a.source_from is not null or a.source_from is null ");
	}
	
	@Override
	public List getdcPublicExtByKey(String stype) {
		return this.baseDaoSupport.queryForList("select a.* from es_dc_public_ext a where a.stype = ? and (a.source_from is not null or a.source_from is null) ",stype);
	}

	
	@Override
	public List getDevelopmentCodeList() {
		return this.baseDaoSupport.queryForList(" select a.* from es_development_code a where  a.source_from is not null or a.source_from is null ");
	}

	@Override
	public List getDevelopmentNameList(){
		return this.baseDaoSupport.queryForList(" select a.* from es_mall_config a where a.field_name='development_name' and a.source_from='"+ManagerUtils.getSourceFrom()+"'");
	}

	@Override
	public List getSendZbConfigList() {
		return this.baseDaoSupport.queryForList("select a.* from es_send_zb_option a where a.source_from is not null or a.source_from is null ");
	}

	@Override
	public List getLogiCompanyList() {
		return this.baseDaoSupport.queryForList("select a.* from es_logi_company a where a.source_from is not null or a.source_from is null ");
	}

	@Override
	public List getTidCategoryList() {
		return this.baseDaoSupport.queryForList("select a.* from es_inf_tid_category a where a.source_from is not null or a.source_from is null");
	}

	@Override
	public List getPostRegionList() {
		return this.baseDaoSupport.queryForList("select a.* from es_post_region a where a.source_from is not null or a.source_from is null");
	}
	
	@Override
	public List getStypeDictRelations(String stype) {
		return this.baseDaoSupport.queryForList("select a.* from es_dc_public_dict_relation a where a.stype=? ",stype);
	}
	
	@Override
	public List getOpenServiceCfgList() {
		return this.baseDaoSupport.queryForList("select a.* from ES_OPEN_SERVICE_CFG a where a.source_from='"+ManagerUtils.getSourceFrom()+"'");
	}
	
	@Override
	public Map<String,String> getOpenServiceCfgSingle(String outServiceCode,String version) {
		return this.baseDaoSupport.queryForMap("select a.* from ES_OPEN_SERVICE_CFG a where a.source_from=? and out_service_code=? and version=?", 
				ManagerUtils.getSourceFrom(),outServiceCode,version);
	}
	
	@Override
	public List getBProductPackage(String product_id) {
		return this.baseDaoSupport.queryForList("select a.package_id from ES_TD_B_PRODUCT_PACKAGE a where a.product_id=? and a.force_tag='1' and a.default_tag='1' and a.source_from='"+ManagerUtils.getSourceFrom()+"'",product_id);
	}
	
	@Override
	public List getBProductElement(Integer package_id) {
		return this.baseDaoSupport.queryForList("select a.package_id,a.element_id,a.element_type_code from ES_TD_B_PACKAGE_ELEMENT a where a.package_id=? and a.element_type_code='D' and a.force_tag='1' and a.default_tag='1' and a.source_from='"+ManagerUtils.getSourceFrom()+"'",package_id);
	}

	@Override
	public List getBProductElementFiltration(String elementIds,String first_payment) {
		if(StringUtils.isBlank(elementIds)){
			return null;
		}
		return this.baseDaoSupport.queryForList("select a.discnt_code from ES_TD_B_DISCNT a where a.discnt_code in ("+elementIds+") and ( a.discnt_name like '%"+first_payment+"%' or (a.discnt_name not like '%"+EcsOrderConsts.FIRST_PAYMENT_ALLM+"%' and a.discnt_name not like '%"+EcsOrderConsts.FIRST_PAYMENT_HALF+"%' and a.discnt_name not like '%"+EcsOrderConsts.FIRST_PAYMENT_COMM+"%') ) and a.source_from='"+ManagerUtils.getSourceFrom()+"'");
	}
	
	@Override
	public List getDependElements(Integer element_id,String element_type_code) {
		return this.baseDaoSupport.queryForList("select a.element_id_b,a.element_type_code_b,a.limit_tag from ES_TD_B_ELEMENT_LIMIT a where a.element_id_a=? and a.element_type_code_a=? and a.element_type_code_b=a.element_type_code_a and (a.limit_tag='1' or a.limit_tag='2')  and a.source_from='"+ManagerUtils.getSourceFrom()+"'",element_id,element_type_code);
	}
	
	@Override
	public List getElementsInfo(String elementIds) {
		if(StringUtils.isBlank(elementIds)){
			return null;
		}
		return this.baseDaoSupport.queryForList("select a.* from ES_TD_B_DISCNT a where a.discnt_code in ("+elementIds+") and a.source_from='"+ManagerUtils.getSourceFrom()+"'");
	}
	
	@Override
	public List getElementsTrueInfo(String discntNames) {
		if(StringUtils.isBlank(discntNames)){
			return null;
		}
		return this.baseDaoSupport.queryForList("select a.* from ES_TD_B_DISCNT a where a.discnt_name in ("+discntNames+") and a.source_from='"+ManagerUtils.getSourceFrom()+"'");
	}
	
	@Override
	public List getDependElementPackageIds(String elementIds,String product_id) {
		if(StringUtils.isBlank(elementIds)){
			return null;
		}
		return this.baseDaoSupport.queryForList("select a.element_id,a.package_id,a.element_type_code from ES_TD_B_PACKAGE_ELEMENT a,ES_TD_B_PRODUCT_PACKAGE e where e.package_id=a.package_id and e.product_id=? and a.element_id in ("+elementIds+") and a.source_from=e.source_from and a.source_from='"+ManagerUtils.getSourceFrom()+"'",product_id);
	}
	
	@Override
	public List getALLProductIds() {
		return this.baseDaoSupport.queryForList("select distinct a.product_id from es_td_b_product_package a where a.source_from='"+ManagerUtils.getSourceFrom()+"'");
	}
	
	@Override
	public List getALLElementIds(String product_id) {
		return this.baseDaoSupport.queryForList("select distinct a.element_id from es_td_B_PACKAGE_ELEMENT a left join es_td_b_product_package b on a.package_id=b.package_id and a.source_from = b.source_from  where b.product_id=? and a.element_type_code='D' and a.source_from='"+ManagerUtils.getSourceFrom()+"'",product_id);
	}
	
	@Override
	public List getAllSpecDefine() {
		return this.baseDaoSupport.queryForList("select a.* from es_esearch_spec a where a.source_from='"+ManagerUtils.getSourceFrom()+"'",EsearchSpec.class);
	}
	
	@Override
	public List getAllSpecValues() {
		return this.baseDaoSupport.queryForList("select a.* from es_esearch_specvalues a where a.source_from='"+ManagerUtils.getSourceFrom()+"'",EsearchSpecvalues.class);
	}
	
	@Override
	public List getSpecvalues(String op_code, String key_id, String key_word) {
		String sql = SF.orderExpSql("SpecvaluesBySearchCodeAndKey");
		List<String> sqlParams = new ArrayList<String>();
		if (!StringUtil.isEmpty(op_code)){
		  sql = sql + " and search_code = ? ";
		  sqlParams.add(op_code);
		}
		if (!StringUtil.isEmpty(key_id)){
		  sql = sql + " and key_id = ? ";
		  sqlParams.add(key_id);
		}
		if (!StringUtil.isEmpty(key_word)){
		  sql = sql + " and match_content = ? ";
		  sqlParams.add(key_word);
		}
		return this.baseDaoSupport.queryForList(sql,EsearchSpecvalues.class,sqlParams.toArray());

	}
	@Override
	public List getSpecDefine(String op_code, String op_id) {
		String sql = SF.orderExpSql("SpecBySearchCode");
		List<String> sqlParams = new ArrayList<String>();
		if (!StringUtil.isEmpty(op_code)){
			sql = sql + " and search_code = ? ";
			sqlParams.add(op_code);
		}
		if (!StringUtil.isEmpty(op_id)){
			sql = sql + " and search_id = ? ";
			sqlParams.add(op_id);
		}
		List list = this.baseDaoSupport.queryForList(sql,EsearchSpec.class,sqlParams.toArray());
		if(list!=null && !list.isEmpty()){
			EsearchSpec spec  = (EsearchSpec) list.get(0);
			list.clear();
			list.add(spec);
		}
		return list;
	}
	@Override
	public List getBatchPreGoods() {
		return this.baseDaoSupport.queryForList("select * from es_dc_batchpre_cfg c where c.source_from = '"+ManagerUtils.getSourceFrom()+"'");
	}
}
