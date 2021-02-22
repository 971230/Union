package com.ztesoft.net.param.inner;

import java.io.Serializable;

/**
 * 关键字插入入参
 * @author shen.qiyu
 *
 */
public class EsearchSpecvaluesInner implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//搜索编码
	private  String search_code;
	//搜索id
	private String search_id;
	//关键字id
	private String key_id;
	//关键字值
	private String key_word;
	//抽取规则id
	private String key_rule_id;
	//成功标识
	private String match_content_type;
	//存未匹配关键字对象id
	private String col3;
	//预警时常
	private String warming_limit;
	//是否预警
	private String warming_flag;
	//是否超时预警
	private String timeout_flag;
	//超时预警时长
	private String timeout_limit;
	
	public String getSearch_code() {
		return search_code;
	}
	public void setSearch_code(String search_code) {
		this.search_code = search_code;
	}
	public String getSearch_id() {
		return search_id;
	}
	public void setSearch_id(String search_id) {
		this.search_id = search_id;
	}
	public String getKey_id() {
		return key_id;
	}
	public void setKey_id(String key_id) {
		this.key_id = key_id;
	}
	public String getKey_word() {
		return key_word;
	}
	public void setKey_word(String key_word) {
		this.key_word = key_word;
	}
	public String getKey_rule_id() {
		return key_rule_id;
	}
	public void setKey_rule_id(String key_rule_id) {
		this.key_rule_id = key_rule_id;
	}
	public String getMatch_content_type() {
		return match_content_type;
	}
	public void setMatch_content_type(String match_content_type) {
		this.match_content_type = match_content_type;
	}
	public String getCol3() {
		return col3;
	}
	public void setCol3(String col3) {
		this.col3 = col3;
	}
	public String getWarming_limit() {
		return warming_limit;
	}
	public void setWarming_limit(String warming_limit) {
		this.warming_limit = warming_limit;
	}
	public String getWarming_flag() {
		return warming_flag;
	}
	public void setWarming_flag(String warming_flag) {
		this.warming_flag = warming_flag;
	}
	public String getTimeout_flag() {
		return timeout_flag;
	}
	public void setTimeout_flag(String timeout_flag) {
		this.timeout_flag = timeout_flag;
	}
	public String getTimeout_limit() {
		return timeout_limit;
	}
	public void setTimeout_limit(String timeout_limit) {
		this.timeout_limit = timeout_limit;
	}
}
