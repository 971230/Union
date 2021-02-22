package com.ztesoft.net.app.base.core.model;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import java.io.Serializable;
import java.util.List;

/**
 * 问答实体
 * @author kingapex
 * 2010-8-6上午07:50:31
 */
public class Ask implements Serializable{
	private int askid;
	@ZteSoftCommentAnnotationParam(name="提问标题",type="String",isNecessary="Y",desc="提问标题")
	private String title;
	@ZteSoftCommentAnnotationParam(name="提问内容",type="String",isNecessary="Y",desc="提问内容")
	private String content;
	private String dateline;
	private int isreply;
	@ZteSoftCommentAnnotationParam(name="提问人ID",type="String",isNecessary="Y",desc="提问人ID")
	private String userid;
	private String siteid;
	@ZteSoftCommentAnnotationParam(name="域名",type="String",isNecessary="N",desc="域名")
	private String domain;
	@ZteSoftCommentAnnotationParam(name="站点名称",type="String",isNecessary="N",desc="站点名称")
	private String sitename;
	@ZteSoftCommentAnnotationParam(name="提问人姓名",type="String",isNecessary="Y",desc="提问人姓名")
	private String username; //提问人姓名
	
	//回复列表非数据库字段
	private List replyList;
	
	public int getAskid() {
		return askid;
	}
	public void setAskid(int askid) {
		this.askid = askid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDatelineLong() {
		return dateline;
	}
	public void setDateline(String dateline) {
		this.dateline = dateline;
	}
	public int getIsreply() {
		return isreply;
	}
	public void setIsreply(int isreply) {
		this.isreply = isreply;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getSiteid() {
		return siteid;
	}
	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getSitename() {
		return sitename;
	}
	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@NotDbField
	public List getReplyList() {
		return replyList;
	}
	public void setReplyList(List replyList) {
		this.replyList = replyList;
	}
	
}
