package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * developer_info节点（人员信息）
 * 
 * @author song.qi 2017年12月26日
 */
public class DeveloperInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "develop_point_code", type = "String", isNecessary = "N", desc = "发展点编码")
	private String develop_point_code;
	
	@ZteSoftCommentAnnotationParam(name = "develop_point_name", type = "String", isNecessary = "N", desc = "发展点名称")
	private String develop_point_name;
	
	@ZteSoftCommentAnnotationParam(name = "develop_code", type = "String", isNecessary = "N", desc = "发展人编码")
	private String develop_code;

	@ZteSoftCommentAnnotationParam(name = "develop_name", type = "String", isNecessary = "N", desc = "发展人姓名")
	private String develop_name;
	
	@ZteSoftCommentAnnotationParam(name = "referee_num", type = "String", isNecessary = "N", desc = "推荐人号码")
	private String referee_num;//推荐人号码（联系方式）
	
	@ZteSoftCommentAnnotationParam(name = "referee_name", type = "String", isNecessary = "N", desc = "推荐人名称")
	private String referee_name;//推荐人名称
	
	@ZteSoftCommentAnnotationParam(name = "county_id", type = "String", isNecessary = "N", desc = "营业县分编码")
	private String county_id;//根据业务具体确认county_id
	
	@ZteSoftCommentAnnotationParam(name = "deal_office_type", type = "String", isNecessary = "N", desc = "渠道类型1")
	private String deal_office_type;// 01：营业渠道	02：行销渠道	03：代理渠道
	
	@ZteSoftCommentAnnotationParam(name = "channelType", type = "String", isNecessary = "N", desc = "渠道分类")
	private String channelType;// 渠道分类 AOP（cbss）业务必传，详见AOP文档分类编码
	
	@ZteSoftCommentAnnotationParam(name = "deal_office_id", type = "String", isNecessary = "N", desc = "操作点")
	private String deal_office_id;// 操作点
	
	@ZteSoftCommentAnnotationParam(name = "deal_operator", type = "String", isNecessary = "N", desc = "操作员编码")
	private String deal_operator;// 操作员编码
	
	@ZteSoftCommentAnnotationParam(name = "deal_operator_name", type = "String", isNecessary = "N", desc = "操作员姓名")
	private String deal_operator_name;// 操作员姓名
	
	@ZteSoftCommentAnnotationParam(name = "deal_operator_num", type = "String", isNecessary = "N", desc = "操作员号码")
	private String deal_operator_num;// 操作员号码
	@ZteSoftCommentAnnotationParam(name = "develop_phone", type = "String", isNecessary = "N", desc = "发展人联系电话")
    private String develop_phone;//发展人联系电话

	public String getDevelop_code() {
		return develop_code;
	}

	public void setDevelop_code(String develop_code) {
		this.develop_code = develop_code;
	}

	public String getDevelop_name() {
		return develop_name;
	}

	public void setDevelop_name(String develop_name) {
		this.develop_name = develop_name;
	}

	public String getDevelop_point_code() {
		return develop_point_code;
	}

	public void setDevelop_point_code(String develop_point_code) {
		this.develop_point_code = develop_point_code;
	}

	public String getDevelop_point_name() {
		return develop_point_name;
	}

	public void setDevelop_point_name(String develop_point_name) {
		this.develop_point_name = develop_point_name;
	}

	public String getReferee_num() {
		return referee_num;
	}

	public void setReferee_num(String referee_num) {
		this.referee_num = referee_num;
	}

	public String getReferee_name() {
		return referee_name;
	}

	public void setReferee_name(String referee_name) {
		this.referee_name = referee_name;
	}

	public String getCounty_id() {
		return county_id;
	}

	public void setCounty_id(String county_id) {
		this.county_id = county_id;
	}

	public String getDeal_office_type() {
		return deal_office_type;
	}

	public void setDeal_office_type(String deal_office_type) {
		this.deal_office_type = deal_office_type;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getDeal_office_id() {
		return deal_office_id;
	}

	public void setDeal_office_id(String deal_office_id) {
		this.deal_office_id = deal_office_id;
	}

	public String getDeal_operator() {
		return deal_operator;
	}

	public void setDeal_operator(String deal_operator) {
		this.deal_operator = deal_operator;
	}

	public String getDeal_operator_name() {
		return deal_operator_name;
	}

	public void setDeal_operator_name(String deal_operator_name) {
		this.deal_operator_name = deal_operator_name;
	}

	public String getDeal_operator_num() {
		return deal_operator_num;
	}

	public void setDeal_operator_num(String deal_operator_num) {
		this.deal_operator_num = deal_operator_num;
	}

    public String getDevelop_phone() {
        return develop_phone;
    }

    public void setDevelop_phone(String develop_phone) {
        this.develop_phone = develop_phone;
    }

}
