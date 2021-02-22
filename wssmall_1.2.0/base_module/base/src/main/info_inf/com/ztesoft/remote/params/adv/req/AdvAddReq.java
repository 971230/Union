package com.ztesoft.remote.params.adv.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class AdvAddReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="广告名称",type="String",isNecessary="Y",desc="aname：广告名称")
	private String aname;
	@ZteSoftCommentAnnotationParam(name="广告位",type="String",isNecessary="N",desc="acid：广告位，为空则默认1【顶部Banner】")
	private String acid="1";
	@ZteSoftCommentAnnotationParam(name="分辨率",type="String",isNecessary="N",desc="resol：分辨率，可为空")
	private String resol;
	@ZteSoftCommentAnnotationParam(name="是否开启",type="String",isNecessary="N",desc="isclose：是否开启，默认开启")
	private String isclose="0";
	@ZteSoftCommentAnnotationParam(name="开始时间",type="String",isNecessary="N",desc="begintime：开始时间")
	private String begintime;
	@ZteSoftCommentAnnotationParam(name="结束时间",type="String",isNecessary="N",desc="endtime：结束时间")
	private String endtime;
	@ZteSoftCommentAnnotationParam(name="广告连接",type="String",isNecessary="N",desc="url：广告连接，可为空")
	private String url;
	@ZteSoftCommentAnnotationParam(name="广告图片",type="String",isNecessary="Y",desc="atturl：广告图片")
	private String atturl;
	@ZteSoftCommentAnnotationParam(name="单位名称",type="String",isNecessary="N",desc="company：单位名称，可为空")
	private String company;
	@ZteSoftCommentAnnotationParam(name="联系方式",type="String",isNecessary="N",desc="contact：联系方式，可为空")
	private String contact;
	@ZteSoftCommentAnnotationParam(name="联系人",type="String",isNecessary="N",desc="linkman：联系人，可为空")
	private String linkman;
	@ZteSoftCommentAnnotationParam(name="分销商ID",type="String",isNecessary="N",desc="staff_no：分销商ID")
	private String staff_no;
	
	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	public String getAcid() {
		return acid;
	}

	public void setAcid(String acid) {
		this.acid = acid;
	}

	public String getResol() {
		return resol;
	}

	public void setResol(String resol) {
		this.resol = resol;
	}

	public String getIsclose() {
		return isclose;
	}

	public void setIsclose(String isclose) {
		this.isclose = isclose;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAtturl() {
		return atturl;
	}

	public void setAtturl(String atturl) {
		this.atturl = atturl;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getStaff_no() {
		return staff_no;
	}

	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}

	@Override
	public void check() throws ApiRuleException {
		if(aname == null || "".equals(aname)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "aname：广告名称不允许为空"));
		if(atturl == null || "".equals(atturl)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "atturl：广告图片不允许为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.ztesoft.remote.adv.add";
	}

}
