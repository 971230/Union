package com.ztesoft.net.mall.core.service.impl.cache;
 
import java.io.Serializable;
import java.util.List;

import model.EsearchSpec;
import model.EsearchSpecvalues;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.service.IExpConfigInfoManager;
import com.ztesoft.net.model.EsearchSpecvaluesRules;
import com.ztesoft.util.CacheUtils;

/**
 * 异常配置信息缓存管理类
 * @author shen.qiyu
 *
 */
public class ExpConfigInfoCacheProxy{
	
	private IExpConfigInfoManager expConfigInfoManager;
	
	public static final String SPECVALUES = "specvalues-";
	public static final String SPEC_LIST = "spec_list-";
	public static final String SPECVALUES_RULES = "specvalues_rules-";
   
	public ExpConfigInfoCacheProxy(){
	}
	
	public void init(){
		if(this.expConfigInfoManager==null){
			this.expConfigInfoManager =SpringContextHolder.getBean("expConfigInfoManager");
		}
	}
   
	/**
	 * 获取规格关键字（支持按接口编码和关键字id或者接口编码和关键字值查询）
	 * @param op_code
	 * @param key_id
	 * @param key_word
	 * @return
	 */
	public EsearchSpecvalues getSpecvalues(String op_code, String key_id, String key_word){
		init();
	    EsearchSpecvalues specvalues = (EsearchSpecvalues)CacheUtils.getCache(SPECVALUES + op_code + "-" + key_id);
	    if (specvalues == null){
	    	specvalues = this.expConfigInfoManager.getSpecvalues(op_code, key_id,key_word);
	    	if(!StringUtils.isEmpty(key_id)){
		    	CacheUtils.addCache(SPECVALUES + op_code + "-" + key_id, specvalues);
			}
			if(!StringUtils.isEmpty(key_word)){
		    	CacheUtils.addCache(SPECVALUES + op_code + "-" + key_word, specvalues);
			}
	    }
	    return specvalues;
	}
   
	/**
	 * 缓存规格关键字（按接口编码和关键字id或者接口编码和关键字值缓存）
	 * @param op_code
	 * @param key_id
	 */
	public void refreshSpecvaluesCache(String op_code, String key_id,String key_word){
		init();
		EsearchSpecvalues specvalues = this.expConfigInfoManager.getSpecvalues(op_code, key_id,key_word);
		if(!StringUtils.isEmpty(key_id)){
	    	CacheUtils.addCache(SPECVALUES + op_code + "-" + key_id, specvalues);
		}
		if(!StringUtils.isEmpty(key_word)){
	    	CacheUtils.addCache(SPECVALUES + op_code + "-" + key_word, specvalues);
		}
	}
	
	/**
	 * 获取规格定义(支持按接口编码查询)
	 * @param op_code
	 * @param op_id
	 * @return
	 */
	public EsearchSpec getSpecDefine(String op_code,String op_id){
		init();
		EsearchSpec spec = CacheUtils.getCache(SPEC_LIST + op_code);
		if (spec == null){
			spec = this.expConfigInfoManager.getSpecDefine(op_code,op_id);
			if(!StringUtils.isEmpty(op_code)){
				CacheUtils.addCache(SPEC_LIST + op_code, spec);
			}
			if(!StringUtils.isEmpty(op_id)){
				CacheUtils.addCache(SPEC_LIST + op_id, spec);
			}
		}
		return spec;
	}
	
	/**
	 * 缓存规格定义（按接口编码缓存）
	 * @param op_code
	 */
	public void refreshSpecDefineListCache(String op_code,String op_id){
		init();
		EsearchSpec spec = this.expConfigInfoManager.getSpecDefine(op_code,op_id);
		if(!StringUtils.isEmpty(op_code)){
			CacheUtils.addCache(SPEC_LIST + op_code, spec);
		}
		if(!StringUtils.isEmpty(op_id)){
			CacheUtils.addCache(SPEC_LIST + op_id, spec);
		}
	}
	
	public List<EsearchSpecvaluesRules> getSpecvaluesRules(String search_id){
		init();
		List<EsearchSpecvaluesRules> rulesList = CacheUtils.getCache(SPECVALUES_RULES + search_id);
		if(rulesList==null || rulesList.isEmpty()){
			rulesList = this.expConfigInfoManager.getSpecvaluesRulesList(search_id);
			CacheUtils.addCache(SPECVALUES_RULES + search_id, (Serializable)rulesList);
		}
		return rulesList;
	}
	
	public void refreshSpecvaluesRules(){
		init();
		List<EsearchSpec> specList = this.expConfigInfoManager.getSpecDefineList();
		for(int i=0;i<specList.size();i++){
			EsearchSpec spec = specList.get(i);
			if(spec!=null){
				String search_id = spec.getSearch_id();
				List<EsearchSpecvaluesRules> rulesList = this.expConfigInfoManager.getSpecvaluesRulesList(search_id);
				if(rulesList!=null&&rulesList.size()>0){
					CacheUtils.addCache(SPECVALUES_RULES + search_id, (Serializable)rulesList);
				}
			}
		}
	}
}