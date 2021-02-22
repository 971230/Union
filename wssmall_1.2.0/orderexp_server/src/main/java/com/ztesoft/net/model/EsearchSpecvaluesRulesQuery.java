package com.ztesoft.net.model;

import java.io.Serializable;

import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.annotation.Table;

@Table(name="ES_ESEARCH_SPECVALUES_RULES")
public class EsearchSpecvaluesRulesQuery implements Serializable{
	private static final long serialVersionUID = 1L;

	//关键字id
	private String key_id;
	//关键字规则id
	private String key_rule_id;
	//搜索id
	private String search_id;
	//序号
	private Integer seq;
	//解析表达式
	private String express;
	//处理类
	private String hander;
	//创建时间
	private String create_time;
	//工号
	private String staff_no;
	//解析表达式类型
	private String express_type;
	//更新时间
	private String update_time;
	//更新工号
	private String upd_staff_no;
	//开始位置
	private Integer begin_index;
	//结束位置
	private Integer end_index;
	//开始字符
	private String begin_word;
	//结束字符
	private String end_word;
	//关键字标识
	private String key_word_type;
	//匹配字符
	private String match_word;
	//截取字符
	private String cut_word;
	
	//搜索规格名称
	private String search_name;
	//来源
	private String source_from;

	public String getKey_id() {
		return key_id;
	}

	public void setKey_id(String key_id) {
		this.key_id = key_id;
	}

	public String getKey_rule_id() {
		return key_rule_id;
	}

	public void setKey_rule_id(String key_rule_id) {
		this.key_rule_id = key_rule_id;
	}

	public String getSearch_id() {
		return search_id;
	}

	public void setSearch_id(String search_id) {
		this.search_id = search_id;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public String getHander() {
		return hander;
	}

	public void setHander(String hander) {
		this.hander = hander;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getStaff_no() {
		return staff_no;
	}

	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}

	public String getExpress_type() {
		return express_type;
	}

	public void setExpress_type(String express_type) {
		this.express_type = express_type;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getUpd_staff_no() {
		return upd_staff_no;
	}

	public void setUpd_staff_no(String upd_staff_no) {
		this.upd_staff_no = upd_staff_no;
	}

	@NotDbField
	public String getSearch_name() {
		return search_name;
	}

	public void setSearch_name(String search_name) {
		this.search_name = search_name;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public Integer getBegin_index() {
		return begin_index;
	}

	public void setBegin_index(Integer begin_index) {
		this.begin_index = begin_index;
	}

	public Integer getEnd_index() {
		return end_index;
	}

	public void setEnd_index(Integer end_index) {
		this.end_index = end_index;
	}

	public String getBegin_word() {
		return begin_word;
	}

	public void setBegin_word(String begin_word) {
		this.begin_word = begin_word;
	}

	public String getEnd_word() {
		return end_word;
	}

	public void setEnd_word(String end_word) {
		this.end_word = end_word;
	}

	public String getKey_word_type() {
		return key_word_type;
	}

	public void setKey_word_type(String key_word_type) {
		this.key_word_type = key_word_type;
	}

	public String getMatch_word() {
		return match_word;
	}

	public void setMatch_word(String match_word) {
		this.match_word = match_word;
	}

	public String getCut_word() {
		return cut_word;
	}

	public void setCut_word(String cut_word) {
		this.cut_word = cut_word;
	}
	
}
