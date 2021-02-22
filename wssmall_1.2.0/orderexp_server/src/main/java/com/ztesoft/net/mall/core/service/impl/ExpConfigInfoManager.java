package com.ztesoft.net.mall.core.service.impl;
 
import java.util.ArrayList;
import java.util.List;

import model.EsearchSpec;
import model.EsearchSpecvalues;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.service.IExpConfigInfoManager;
import com.ztesoft.net.model.EsearchSpecvaluesRules;
import com.ztesoft.net.sqls.SF;

public class ExpConfigInfoManager extends BaseSupport implements IExpConfigInfoManager{
	
	@Override
	public EsearchSpecvalues getSpecvalues(String op_code, String key_id,String key_word){
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
		return (EsearchSpecvalues) this.baseDaoSupport.queryForObject(sql, EsearchSpecvalues.class, sqlParams.toArray());
   }
   
   @Override
public EsearchSpec getSpecDefine(String op_code,String op_id){
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
	   List<EsearchSpec> specList = this.baseDaoSupport.queryForList(sql, EsearchSpec.class, sqlParams.toArray());
	   if(specList!=null &&!specList.isEmpty()){
		   return specList.get(0);
	   }else{
		   return null;
	   }
   	}

	@Override
	public List<EsearchSpec> getSpecDefineList() {
		String sql = SF.orderExpSql("SpecDefineList");
		List<EsearchSpec> list = this.baseDaoSupport.queryForList(sql,EsearchSpec.class);
		return list;
	}
	
	@Override
	public List<EsearchSpecvaluesRules> getSpecvaluesRulesList(String search_id) {
		String sql = SF.orderExpSql("SpecvaluesRulesBySearchId");
		List<EsearchSpecvaluesRules> list = this.baseDaoSupport.queryForList(sql,EsearchSpecvaluesRules.class,search_id);
		return list;
	}
   
}