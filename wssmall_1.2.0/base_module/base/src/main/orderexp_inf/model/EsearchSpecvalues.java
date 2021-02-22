package model;

import java.io.Serializable;

import com.ztesoft.net.framework.database.NotDbField;

/**
 * 规格关键字定义实体
 * @author qin.yingxiong
 */
public class EsearchSpecvalues implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String search_code;//搜索编码
	private String search_id;//搜索规格id
	private String key_field_type;//关键字类型
	private String key_id;//关键字id
	private String match_content;//检索关键字值
	private String create_time;//创建时间
	private String col1;
	private String col2;
	private String col3;
	private String col4;
	private String col5;
	private String match_type;//字典匹配类型
	private String match_content_type;//字典匹配类型
	private String record_status;//记录状态
	private String alarm_limit;//告警阈值
	private String alarm_flag;//是否告警
	private String warming_limit;//预警时常
	private String warming_flag;//是否预警
	private String alarm_time_interval;//告警时间区间（秒钟）
	private String timeout_flag;//是否超时预警
	private String timeout_limit;//超时预警时长
	
	private String catalog_id;//归类id
	private String catalog_name;//归类名称
	
	private String key_rule_id;//抽取规格id
	
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
	public String getKey_field_type() {
		return key_field_type;
	}
	public void setKey_field_type(String key_field_type) {
		this.key_field_type = key_field_type;
	}
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
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getCol1() {
		return col1;
	}
	public void setCol1(String col1) {
		this.col1 = col1;
	}
	public String getCol2() {
		return col2;
	}
	public void setCol2(String col2) {
		this.col2 = col2;
	}
	public String getCol3() {
		return col3;
	}
	public void setCol3(String col3) {
		this.col3 = col3;
	}
	public String getCol4() {
		return col4;
	}
	public void setCol4(String col4) {
		this.col4 = col4;
	}
	public String getCol5() {
		return col5;
	}
	public void setCol5(String col5) {
		this.col5 = col5;
	}
	public String getMatch_type() {
		return match_type;
	}
	public void setMatch_type(String match_type) {
		this.match_type = match_type;
	}
	public String getMatch_content_type() {
		return match_content_type;
	}
	public void setMatch_content_type(String match_content_type) {
		this.match_content_type = match_content_type;
	}
	public String getRecord_status() {
		return record_status;
	}
	public void setRecord_status(String record_status) {
		this.record_status = record_status;
	}
	public String getAlarm_limit() {
		return alarm_limit;
	}
	public void setAlarm_limit(String alarm_limit) {
		this.alarm_limit = alarm_limit;
	}
	public String getAlarm_flag() {
		return alarm_flag;
	}
	public void setAlarm_flag(String alarm_flag) {
		this.alarm_flag = alarm_flag;
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
	public String getAlarm_time_interval() {
		return alarm_time_interval;
	}
	public void setAlarm_time_interval(String alarm_time_interval) {
		this.alarm_time_interval = alarm_time_interval;
	}
	@NotDbField
	public String getCatalog_id() {
		return catalog_id;
	}
	@NotDbField
	public void setCatalog_id(String catalog_id) {
		this.catalog_id = catalog_id;
	}
	@NotDbField
	public String getCatalog_name() {
		return catalog_name;
	}
	@NotDbField
	public void setCatalog_name(String catalog_name) {
		this.catalog_name = catalog_name;
	}
	public String getKey_rule_id() {
		return key_rule_id;
	}
	public void setKey_rule_id(String key_rule_id) {
		this.key_rule_id = key_rule_id;
	}

}
