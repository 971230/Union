package com.ztesoft.net.app.base.core.model;

import java.io.Serializable;
/**
 * 分销商商铺
 * @author dengxiuping
 *
 */
public class PartnerShop implements Serializable{

	private String partner_id;
	private String code;
	private String name;
	private String address;
	private String net_type;
	private String area_code;
	private String star;
	private String channel_type;
	private String sub_channel_type;
	private String create_date;
	private String linknam;
	private String phone_no;
	private String eff_date;
	private String exp_date;
	private String shop_url;
	private String send_address;
	private Integer sequ;
	private String state;
	private String audit_idea;
	 
	private String partner_name_desc;
	private String star_desc;
	private String last_update_date;
	private String shop_default_image;//店铺默认图片
	private String shop_detail_image;//店铺详情图片
	private String shop_desc;//店铺详情
	private String source_from;//
	private String weixin;//
	
	public String getPartner_id() {
		return partner_id;
	}
	public String getLast_update_date() {
		return last_update_date;
	}
	public void setLast_update_date(String last_update_date) {
		this.last_update_date = last_update_date;
	}
	public String getAudit_idea() {
		return audit_idea;
	}
	public void setAudit_idea(String audit_idea) {
		this.audit_idea = audit_idea;
	}
	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}
	public String getPartner_name_desc() {
		return partner_name_desc;
	}
	public void setPartner_name_desc(String partner_name_desc) {
		this.partner_name_desc = partner_name_desc;
	}
	public String getStar_desc() {
		return star_desc;
	}
	public void setStar_desc(String star_desc) {
		this.star_desc = star_desc;
	}
	public Integer getSequ() {
		return sequ;
	}
	public void setSequ(Integer sequ) {
		this.sequ = sequ;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNet_type() {
		return net_type;
	}
	public void setNet_type(String net_type) {
		this.net_type = net_type;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getChannel_type() {
		return channel_type;
	}
	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}
	public String getSub_channel_type() {
		return sub_channel_type;
	}
	public void setSub_channel_type(String sub_channel_type) {
		this.sub_channel_type = sub_channel_type;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getLinknam() {
		return linknam;
	}
	public void setLinknam(String linknam) {
		this.linknam = linknam;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	
	public String getEff_date() {
		return eff_date;
	}
	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}
	public String getExp_date() {
		return exp_date;
	}
	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}
	public String getShop_url() {
		return shop_url;
	}
	public void setShop_url(String shop_url) {
		this.shop_url = shop_url;
	}
	public String getSend_address() {
		return send_address;
	}
	public void setSend_address(String send_address) {
		this.send_address = send_address;
	}
	public String getShop_default_image() {
		return shop_default_image;
	}
	public void setShop_default_image(String shop_default_image) {
		this.shop_default_image = shop_default_image;
	}
	public String getShop_detail_image() {
		return shop_detail_image;
	}
	public void setShop_detail_image(String shop_detail_image) {
		this.shop_detail_image = shop_detail_image;
	}
	public String getShop_desc() {
		return shop_desc;
	}
	public void setShop_desc(String shop_desc) {
		this.shop_desc = shop_desc;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	/**
	 * @return the weixin
	 */
	public String getWeixin() {
		return weixin;
	}
	/**
	 * @param weixin the weixin to set
	 */
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	
	
}
