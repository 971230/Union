package com.ztesoft.net.app.base.core.model;

import com.ztesoft.net.framework.database.NotDbField;

import java.io.Serializable;

/**
 * 广告
 * 
 * @author 李志富 lzf<br/>
 *         2010-2-4 下午03:19:38<br/>
 *         version 1.0<br/>
 * <br/>
 */
public class Adv implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 808400934523865215L;
	private String aid;
	private String acid;
	private String atype;
	private String begintime;
	private String endtime;
	private Integer isclose;
	private String attachment;
	private String atturl;
	private String url;
	private String aname;
	private Integer clickcount;
 
	private String disabled;
	private String linkman;
	private String company;
	private String contact;
	
	private String user_id;
	private Integer state;
	
	private String resol;
	private String create_date;
	private String source_from ;

	private String attribute;
	private String subtype;
	private Long staff_no;

	/**
	 * 所属广告位名
	 * 非数据库字段
	 */
	private String cname;
	//显示图片的路径，经过工具类转换后的路径
	private String img_url;
	
	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getAcid() {
		return acid;
	}

	public void setAcid(String acid) {
		this.acid = acid;
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

	public Integer getIsclose() {
		return isclose;
	}

	public void setIsclose(Integer isclose) {
		this.isclose = isclose;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getAtturl() {
		return atturl;
	}

	public void setAtturl(String atturl) {
		this.atturl = atturl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	public Integer getClickcount() {
		return clickcount;
	}

	public void setClickcount(Integer clickcount) {
		this.clickcount = clickcount;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
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

	@NotDbField
	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getResol() {
		return resol;
	}

	public void setResol(String resol) {
		this.resol = resol;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}


	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	@NotDbField
	public String getImg_url() {
		return img_url;
	}

	@NotDbField
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public Long getStaff_no() {
		return staff_no;
	}

	public void setStaff_no(Long staff_no) {
		this.staff_no = staff_no;
	}

	public String getAtype() {
		return atype;
	}

	public void setAtype(String atype) {
		this.atype = atype;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	
}