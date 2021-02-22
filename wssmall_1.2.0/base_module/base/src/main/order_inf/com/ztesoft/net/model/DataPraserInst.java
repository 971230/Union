/**
 * 
 */
package com.ztesoft.net.model;

import com.ztesoft.net.framework.database.NotDbField;

/**
 * @author ZX
 * @version
 * @see ES_DATA_PRASER_INST
 *
 */
public class DataPraserInst {
	
	private String log_id; //日志ID
	private String op_code; //组件编码
	private String dict_id; //字典ID
	private String catalog_id; //字典目录ID
	private String rsp_msg; //日志概要描述
	private String obj_id; //业务ID
	private String create_time; //创建时间
	private String update_time; //修改时间
	private String deal_state; //处理状态(0-未处理，1-已处理)
	private String deal_content; //处理内容
	private String local_log_id; //本地接口日志ID
	private String source_from; //数据来源
	private String dict_name; // 字典名称
	private String deal_opinion; // 处理建议
	private String col1;
	private String col2;
	private String COL3;
	private String COL4;
	private String COL5;
	
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getOp_code() {
		return op_code;
	}
	public void setOp_code(String op_code) {
		this.op_code = op_code;
	}
	public String getDict_id() {
		return dict_id;
	}
	public void setDict_id(String dict_id) {
		this.dict_id = dict_id;
	}
	public String getCatalog_id() {
		return catalog_id;
	}
	public void setCatalog_id(String catalog_id) {
		this.catalog_id = catalog_id;
	}
	public String getRsp_msg() {
		return rsp_msg;
	}
	public void setRsp_msg(String rsp_msg) {
		this.rsp_msg = rsp_msg;
	}
	public String getObj_id() {
		return obj_id;
	}
	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getDeal_state() {
		return deal_state;
	}
	public void setDeal_state(String deal_state) {
		this.deal_state = deal_state;
	}
	public String getDeal_content() {
		return deal_content;
	}
	public void setDeal_content(String deal_content) {
		this.deal_content = deal_content;
	}
	public String getLocal_log_id() {
		return local_log_id;
	}
	public void setLocal_log_id(String local_log_id) {
		this.local_log_id = local_log_id;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	@NotDbField
	public String getDict_name() {
		return dict_name;
	}
	public void setDict_name(String dict_name) {
		this.dict_name = dict_name;
	}
	@NotDbField
	public String getDeal_opinion() {
		return deal_opinion;
	}
	public void setDeal_opinion(String deal_opinion) {
		this.deal_opinion = deal_opinion;
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
	public String getCOL3() {
		return COL3;
	}
	public void setCOL3(String cOL3) {
		COL3 = cOL3;
	}
	public String getCOL4() {
		return COL4;
	}
	public void setCOL4(String cOL4) {
		COL4 = cOL4;
	}
	public String getCOL5() {
		return COL5;
	}
	public void setCOL5(String cOL5) {
		COL5 = cOL5;
	}
	
}
