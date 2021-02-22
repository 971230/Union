package com.ztesoft.net.param.inner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KeyRuleInner implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String key_id; //关键字id
	private String match_content;//检索关键字值
	private String search_id;//搜索id
	private String search_code;//搜索编码
	private String express;//解析表达式
	private String staff_no;//工号
	private String search_name;//搜索规格名称
	private String hander;//处理类
	private String key_rule_id;//关键字规则id
	private int pageIndex;
	private int pageSize;
	
	private String excp_inst_ids;//异常实例id（多个逗号隔开）
	private String search_ids;//搜索id（多个用逗号隔开）
	private String log_ids;//esearch实例id（多个用逗号隔开）
	
	private String seq;//抽取顺序
	private String begin_index;//开始位置
	private String end_index;//结束位置
	private String begin_word;//开始字符
	private String end_word;//结束字符
	private String key_word_type;//关键字标识
	private String match_word;//匹配字符
	private String cut_word;//截取字符
	private String out_tid;//外部单号
	private String excp_inst_id;//异常单id
	
	private String has_match_content;//有无关键字
	
	public String getKey_id() {
		return key_id;
	}
	public void setKey_id(String key_id) {
		this.key_id = key_id;
	}
	public String getMatch_content() {
		return match_content;
	}
	public void setMatch_content(String match_content) {
		this.match_content = match_content;
	}
	
	public String getKey_rule_id() {
		return key_rule_id;
	}
	public void setKey_rule_id(String key_rule_id) {
		this.key_rule_id = key_rule_id;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getSearch_name() {
		return search_name;
	}
	public void setSearch_name(String search_name) {
		this.search_name = search_name;
	}
	public String getHander() {
		return hander;
	}
	public void setHander(String hander) {
		this.hander = hander;
	}
	public String getSearch_id() {
		return search_id;
	}
	public void setSearch_id(String search_id) {
		this.search_id = search_id;
	}
	public String getSearch_code() {
		return search_code;
	}
	public void setSearch_code(String search_code) {
		this.search_code = search_code;
	}
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
	}
	public String getStaff_no() {
		return staff_no;
	}
	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}
	public String getExcp_inst_ids() {
		return excp_inst_ids;
	}
	public void setExcp_inst_ids(String excp_inst_ids) {
		this.excp_inst_ids = excp_inst_ids;
	}
	public String getSearch_ids() {
		return search_ids;
	}
	public void setSearch_ids(String search_ids) {
		this.search_ids = search_ids;
	}
	public String getLog_ids() {
		return log_ids;
	}
	public void setLog_ids(String log_ids) {
		this.log_ids = log_ids;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getBegin_index() {
		return begin_index;
	}
	public void setBegin_index(String begin_index) {
		this.begin_index = begin_index;
	}
	public String getEnd_index() {
		return end_index;
	}
	public void setEnd_index(String end_index) {
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
	public String getOut_tid() {
		return out_tid;
	}
	public void setOut_tid(String out_tid) {
		this.out_tid = out_tid;
	}
	public String getExcp_inst_id() {
		return excp_inst_id;
	}
	public void setExcp_inst_id(String excp_inst_id) {
		this.excp_inst_id = excp_inst_id;
	}
	public String getHas_match_content() {
		return has_match_content;
	}
	public void setHas_match_content(String has_match_content) {
		this.has_match_content = has_match_content;
	}
	
	
	public List<ExpInstQueryInner> getExpInstList(){
		String[] excpInstIds = excp_inst_ids.split(",");
		String[] searchIds = search_ids.split(",");
		String[] logIds = log_ids.split(",");
		String[] matchContents = match_content.split(",");
		String[] searchCodes = search_code.split(",");
		
		List<ExpInstQueryInner> result = new ArrayList<ExpInstQueryInner>();
		for(int i=0,length=excpInstIds.length;i<length;i++){
			ExpInstQueryInner tem = new ExpInstQueryInner();
			tem.setExcp_inst_id(excpInstIds[i]);
			tem.setSearch_id(searchIds[i]);
			tem.setLog_id(logIds[i]);
			tem.setMatch_content(matchContents[i]);
			tem.setKey_id(key_id);
			tem.setSearch_code(searchCodes[i]);
			result.add(tem);
		}
		
		return result;
	}
}
