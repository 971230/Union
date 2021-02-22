package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class GeneralOrderQueryReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name="外部系统订单号",type="String",isNecessary="N",desc="收单单号")
	private String out_order_id;
	@ZteSoftCommentAnnotationParam(name="订单号",type="String",isNecessary="N",desc="收单单号")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="查询类型",type="String",isNecessary="N",desc="查询类型 01 订单来源 02 v计划 03 按照种子用户号码查询")
	private String query_type;
	@ZteSoftCommentAnnotationParam(name="查询参数",type="String",isNecessary="N",desc="查询参数")
	private String query_para;
	@ZteSoftCommentAnnotationParam(name="创建开始时间",type="String",isNecessary="N",desc="创建开始时间")
	private String start_date;
	@ZteSoftCommentAnnotationParam(name="创建结束时间",type="String",isNecessary="N",desc="创建结束时间")
	private String end_date;
	@ZteSoftCommentAnnotationParam(name="营销用户ID",type="String",isNecessary="N",desc="营销用户ID")
	private String market_user_id;
	@ZteSoftCommentAnnotationParam(name="业务号码",type="String",isNecessary="N",desc="业务号码")
    private String acc_nbr ;
	@ZteSoftCommentAnnotationParam(name="证件号",type="String",isNecessary="N",desc="证件号")
    private String cert_num ;
	@ZteSoftCommentAnnotationParam(name="证件号类型",type="String",isNecessary="N",desc="证件类型")
    private String cert_type ;
	@ZteSoftCommentAnnotationParam(name="page_size",type="String",isNecessary="Y",desc="page_size：每页展示条数")
	private String page_size;
	//具体页数
	@ZteSoftCommentAnnotationParam(name="page_no",type="String",isNecessary="Y",desc="page_no：具体页数")
	private String page_no;
    public String getOut_order_id() {
		return out_order_id;
	}

	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getMarket_user_id() {
		return market_user_id;
	}

	public void setMarket_user_id(String market_user_id) {
		this.market_user_id = market_user_id;
	}


	public String getAcc_nbr() {
		return acc_nbr;
	}

	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}

	public String getCert_num() {
		return cert_num;
	}

	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	public String getPage_size() {
		return page_size;
	}

	public void setPage_size(String page_size) {
		this.page_size = page_size;
	}

	public String getPage_no() {
		return page_no;
	}

	public void setPage_no(String page_no) {
		this.page_no = page_no;
	}


	public String getQuery_type() {
		return query_type;
	}

	public void setQuery_type(String query_type) {
		this.query_type = query_type;
	}

	public String getQuery_para() {
		return query_para;
	}

	public void setQuery_para(String query_para) {
		this.query_para = query_para;
	}

	public String getCert_type() {
		return cert_type;
	}

	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.ztesoft.net.ecsord.params.ecaop.req.GeneralOrderQueryReq";
	}

}
