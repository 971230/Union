package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

public class NumeroImportLog implements Serializable {

	private String batch_id;          //批次号
	private String log_id;            //日志标识
	private String dn_no;             //手机号码
	private String no_gen;            //网别
	private Double deposit;           //预存款
	private Integer period;           //合约期
	private Double lowest;            //最低消费额
	private Double fee_adjust;        //减免金额
	private String charge_type;       //付费方式
	private String file_name;         //文件名称
	private Integer batch_amount;     //该批次导入总数
	private String created_date;      //导入时间
	private String action_code;       //动作，A-导入，M-修改，B-发布
	private String oper_id;           //导入工号
	private String oper_name;         //操作人名称
	private Integer deal_flag;        //处理状态
	private String status_date;       //处理状态更新时间
	private Integer deal_num;         //处理次数
	private String deal_desc;         //处理结果描述
	private String param1;            //
	private String param2;            //
	private String param3;            //
	private String param4;            //
	private String param5;            //
	private String group_code;		  //号码分组编码
	public String getGroup_code() {
		return group_code;
	}
	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}

	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getDn_no() {
		return dn_no;
	}
	public void setDn_no(String dn_no) {
		this.dn_no = dn_no;
	}
	public String getNo_gen() {
		return no_gen;
	}
	public void setNo_gen(String no_gen) {
		this.no_gen = no_gen;
	}
	public Double getDeposit() {
		return deposit;
	}
	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public Double getLowest() {
		return lowest;
	}
	public void setLowest(Double lowest) {
		this.lowest = lowest;
	}
	public Double getFee_adjust() {
		return fee_adjust;
	}
	public void setFee_adjust(Double fee_adjust) {
		this.fee_adjust = fee_adjust;
	}
	public String getCharge_type() {
		return charge_type;
	}
	public void setCharge_type(String charge_type) {
		this.charge_type = charge_type;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public Integer getBatch_amount() {
		return batch_amount;
	}
	public void setBatch_amount(Integer batch_amount) {
		this.batch_amount = batch_amount;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getAction_code() {
		return action_code;
	}
	public void setAction_code(String action_code) {
		this.action_code = action_code;
	}
	public String getOper_id() {
		return oper_id;
	}
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	public String getOper_name() {
		return oper_name;
	}
	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}
	public Integer getDeal_flag() {
		return deal_flag;
	}
	public void setDeal_flag(Integer deal_flag) {
		this.deal_flag = deal_flag;
	}
	public String getStatus_date() {
		return status_date;
	}
	public void setStatus_date(String status_date) {
		this.status_date = status_date;
	}
	public Integer getDeal_num() {
		return deal_num;
	}
	public void setDeal_num(Integer deal_num) {
		this.deal_num = deal_num;
	}
	public String getDeal_desc() {
		return deal_desc;
	}
	public void setDeal_desc(String deal_desc) {
		this.deal_desc = deal_desc;
	}
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public String getParam2() {
		return param2;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	public String getParam3() {
		return param3;
	}
	public void setParam3(String param3) {
		this.param3 = param3;
	}
	public String getParam4() {
		return param4;
	}
	public void setParam4(String param4) {
		this.param4 = param4;
	}
	public String getParam5() {
		return param5;
	}
	public void setParam5(String param5) {
		this.param5 = param5;
	}
	
}
