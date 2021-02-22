package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.model.EsearchSpecvaluesRules;

import model.EsearchSpec;
import model.EsearchSpecvalues;

public interface IExpConfigInfoManager{
	
	/**
	 * 获取规格关键字（支持按接口编码和关键字id或者接口编码和关键字值查询）
	 * @param op_code
	 * @param key_id
	 * @param key_word
	 * @return
	 */
	public EsearchSpecvalues getSpecvalues(String op_code, String key_id,String key_word);
	
	/**
	 * 获取规格定义（支持按接口编码和关键字id或者接口编码和关键字值查询）
	 * @param op_code
	 * @param op_id
	 * @return
	 */
	public EsearchSpec getSpecDefine(String op_code,String op_id);
	
	/**
	 * 查询所有的规格
	 * @return
	 */
	public List<EsearchSpec> getSpecDefineList();
	
	List<EsearchSpecvaluesRules> getSpecvaluesRulesList(String search_id);

}