package com.ztesoft.net.mall.core.model;

import java.io.Serializable;
import java.util.Date;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.eop.sdk.utils.UploadUtilc;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 评论/咨询实体
 * 
 * @author 李志富 lzf<br/>
 *         2010-1-26 下午02:17:29<br/>
 *         version 1.0<br/>
 * <br/>
 */
public class Comments implements Serializable{
	private String comment_id;
	private String for_comment_id;
	@ZteSoftCommentAnnotationParam(name="商品ID或订单ID",type="String",isNecessary="Y",desc="商品ID或订单ID")
	private String object_id; // meens goods_id or article_id
	@ZteSoftCommentAnnotationParam(name="类型",type="String",isNecessary="Y",desc="商品类型为goods，供货商类型为suppler，订单类型为order")
	private String object_type; 
	@ZteSoftCommentAnnotationParam(name="评论人ID",type="String",isNecessary="Y",desc="评论人ID")
	private String author_id;
	@ZteSoftCommentAnnotationParam(name="评论人名称",type="String",isNecessary="Y",desc="评论人名称")
	private String author;
	private String levelname;
	@ZteSoftCommentAnnotationParam(name="评论人电话号码",type="String",isNecessary="Y",desc="评论人电话号码")
	private String contact;
	private String mem_read_status;
	private String adm_read_status;
	private String time;
	private String lastreply;
	private String reply_name;
	@ZteSoftCommentAnnotationParam(name="标题",type="String",isNecessary="Y",desc="标题")
	private String title;
	private String acomment;
	private String ip;
	private String display;
	private Integer p_index;
	private String disabled;
	private String commenttype;
	@ZteSoftCommentAnnotationParam(name="评份等级",type="String",isNecessary="Y",desc="评份等级:1-5")
	private int grade; //评分
	private String img;
	private String commt;
	@ZteSoftCommentAnnotationParam(name="评论内容",type="String",isNecessary="N",desc="评论内容")
	private String quotas;
	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="N",desc="订单ID")
	private String order_id;
	@NotDbField
	public Date getDate(){
//		return new Date(this.getTime()); 
		return new Date();
	}
	
	public String getCommenttype() {
		return commenttype;
	}

	public void setCommenttype(String commenttype) {
		this.commenttype = commenttype;
	}

	public String getComment_id() {
		return comment_id;
	}

	public void setComment_id(String commentId) {
		comment_id = commentId;
	}

	public String getFor_comment_id() {
		return for_comment_id;
	}

	public void setFor_comment_id(String forCommentId) {
		for_comment_id = forCommentId;
	}

	public String getObject_id() {
		return object_id;
	}

	public void setObject_id(String objectId) {
		object_id = objectId;
	}

	public String getObject_type() {
		return object_type;
	}

	public void setObject_type(String objectType) {
		object_type = objectType;
	}

	public String getAuthor_id() {
		return author_id;
	}

	public void setAuthor_id(String authorId) {
		author_id = authorId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getLevelname() {
		return levelname;
	}

	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getMem_read_status() {
		return mem_read_status;
	}

	public void setMem_read_status(String memReadStatus) {
		mem_read_status = memReadStatus;
	}

	public String getAdm_read_status() {
		return adm_read_status;
	}

	public void setAdm_read_status(String admReadStatus) {
		adm_read_status = admReadStatus;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLastreply() {
		return lastreply;
	}

	public void setLastreply(String lastreply) {
		this.lastreply = lastreply;
	}

	public String getReply_name() {
		return reply_name;
	}

	public void setReply_name(String replyName) {
		reply_name = replyName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@NotDbField
	public String getAcomment() {
		return acomment;
	}

	public void setAcomment(String acomment) {
		this.acomment = acomment;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public Integer getP_index() {
		return p_index;
	}

	public void setP_index(Integer pIndex) {
		p_index = pIndex;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@NotDbField
	public String getImage(){
		img  =UploadUtilc.replacePath(img);
		return img;
	}

	public String getCommt() {
		return commt;
	}

	public void setCommt(String commt) {
		this.commt = commt;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getQuotas() {
		return quotas;
	}

	public void setQuotas(String quotas) {
		this.quotas = quotas;
	}
	
}
