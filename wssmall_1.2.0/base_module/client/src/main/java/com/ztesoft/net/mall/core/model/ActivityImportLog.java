package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

public class ActivityImportLog implements Serializable {
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public Integer getBatch_amount() {
		return batch_amount;
	}
	public void setBatch_amount(Integer batch_amount) {
		this.batch_amount = batch_amount;
	}
	public String getOper_id() {
		return oper_id;
	}
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRelation_name() {
		return relation_name;
	}
	public void setRelation_name(String relation_name) {
		this.relation_name = relation_name;
	}
	public String getPmt_type() {
		return pmt_type;
	}
	public void setPmt_type(String pmt_type) {
		this.pmt_type = pmt_type;
	}
	public String getPackage_class() {
		return package_class;
	}
	public void setPackage_class(String package_class) {
		this.package_class = package_class;
	}
	public String getMin_price() {
		return min_price;
	}
	public void setMin_price(String min_price) {
		this.min_price = min_price;
	}
	public String getMax_price() {
		return max_price;
	}
	public void setMax_price(String max_price) {
		this.max_price = max_price;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getModify_eff_time() {
		return modify_eff_time;
	}
	public void setModify_eff_time(String modify_eff_time) {
		this.modify_eff_time = modify_eff_time;
	}
	public String getOrg_id_str() {
		return org_id_str;
	}
	public void setOrg_id_str(String org_id_str) {
		this.org_id_str = org_id_str;
	}
	public String getPmt_condition() {
		return pmt_condition;
	}
	public void setPmt_condition(String pmt_condition) {
		this.pmt_condition = pmt_condition;
	}
	public String getPmt_price() {
		return pmt_price;
	}
	public void setPmt_price(String pmt_price) {
		this.pmt_price = pmt_price;
	}
	public String getAvailable_period() {
		return available_period;
	}
	public void setAvailable_period(String available_period) {
		this.available_period = available_period;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getGift_zjzdb() {
		return gift_zjzdb;
	}
	public void setGift_zjzdb(String gift_zjzdb) {
		this.gift_zjzdb = gift_zjzdb;
	}
	public String getGift_zsyw() {
		return gift_zsyw;
	}
	public void setGift_zsyw(String gift_zsyw) {
		this.gift_zsyw = gift_zsyw;
	}
	public String getGift_lp() {
		return gift_lp;
	}
	public void setGift_lp(String gift_lp) {
		this.gift_lp = gift_lp;
	}
	public String getGift_other() {
		return gift_other;
	}
	public void setGift_other(String gift_other) {
		this.gift_other = gift_other;
	}
	public String getRelief_no_class() {
		return relief_no_class;
	}
	public void setRelief_no_class(String relief_no_class) {
		this.relief_no_class = relief_no_class;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getDeal_flag() {
		return deal_flag;
	}
	public void setDeal_flag(String deal_flag) {
		this.deal_flag = deal_flag;
	}
	public String getDeal_num() {
		return deal_num;
	}
	public void setDeal_num(String deal_num) {
		this.deal_num = deal_num;
	}
	public String getDeal_desc() {
		return deal_desc;
	}
	public void setDeal_desc(String deal_desc) {
		this.deal_desc = deal_desc;
	}
	public String getStatus_date() {
		return status_date;
	}
	public void setStatus_date(String status_date) {
		this.status_date = status_date;
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
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private static final long serialVersionUID = 8084009345238655514L;

	private String log_id;            //日志ID
	private String batch_id;          //批次号
	private Integer batch_amount;     //该批次导入总数
	private String oper_id;           //操作员
	private String created_date;      //导入时间
	private String file_name;		  //文件名称
	private String name;              //活动名称
	private String relation_name;     //货品包名
	private String pmt_type;          //活动类别
	private String package_class;     //套餐类别
	private String min_price;         //最低套餐档次
	private String max_price;         //最高套餐档次
	private String region;            //活动地市
	private String modify_eff_time;   //生效时间
	private String org_id_str;        //活动商城
	private String pmt_condition;     //活动条件
	private String pmt_price;         //活动价格
	private String available_period;  //活动有效期
	private String brief;  		      //活动内容
	private String gift_zjzdb;        //直降转兑包
	private String gift_zsyw;         //赠送业务
	private String gift_lp;           //礼品
	private String gift_other;        //其他赠品
	private String relief_no_class;	  //靓号减免类型
	private String comments;          //备注
	private String deal_flag;         //处理状态
	private String deal_num;          //处理次数
	private String deal_desc;         //处理结果描述
	private String status_date;       //处理状态更新时间
	private String param1;            //备用字段1
	private String param2;            //备用字段2
	private String param3;            //备用字段3
	private String param4;            //备用字段4
	private String param5;            //备用字段5
	private String source_from;		  //系统来源
}