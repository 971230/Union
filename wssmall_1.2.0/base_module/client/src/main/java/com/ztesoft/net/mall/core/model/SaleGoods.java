package com.ztesoft.net.mall.core.model;

import java.util.List;

import com.ztesoft.net.framework.database.NotDbField;

public class SaleGoods implements java.io.Serializable {
	private String sale_gid;//销售商品ID
	private String sku;//SKU
	private String sale_gname;//销售商品名称
	private String public_title;//宣传标题
	private String selling_point1;//卖点1
	private String selling_point2;//卖点2
	private String selling_point3;//卖点3
	private String package_type;//商品类型
	private String publish_city;//发布地市
	private String publish_shop;//发布商城
	private String channel_type;//渠道类型
	private String intro;//描述信息
	private String market_enable;//发布状态
	private String mall_staff_id;//商城工号
	private String creator_id;//创建人
	private String create_time;//创建时间
	private String modifier_id;//修改操作人
	private String modify_time;//修改时间
	private String source_from;//来源平台
	private String goods_type;
	private List<AgentInfo> agent_names ; //
	
	@NotDbField
	public String getGoods_type() {
		return goods_type;
	}
	@NotDbField
	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}
	public String getSale_gid() {
		return sale_gid;
	}
	public void setSale_gid(String sale_gid) {
		this.sale_gid = sale_gid;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getSale_gname() {
		return sale_gname;
	}
	public void setSale_gname(String sale_gname) {
		this.sale_gname = sale_gname;
	}
	public String getPublic_title() {
		return public_title;
	}
	public void setPublic_title(String public_title) {
		this.public_title = public_title;
	}
	public String getSelling_point1() {
		return selling_point1;
	}
	public void setSelling_point1(String selling_point1) {
		this.selling_point1 = selling_point1;
	}
	public String getSelling_point2() {
		return selling_point2;
	}
	public void setSelling_point2(String selling_point2) {
		this.selling_point2 = selling_point2;
	}
	public String getSelling_point3() {
		return selling_point3;
	}
	public void setSelling_point3(String selling_point3) {
		this.selling_point3 = selling_point3;
	}
	public String getPackage_type() {
		return package_type;
	}
	public void setPackage_type(String package_type) {
		this.package_type = package_type;
	}
	public String getPublish_city() {
		return publish_city;
	}
	public void setPublish_city(String publish_city) {
		this.publish_city = publish_city;
	}
	public String getPublish_shop() {
		return publish_shop;
	}
	public void setPublish_shop(String publish_shop) {
		this.publish_shop = publish_shop;
	}
	public String getChannel_type() {
		return channel_type;
	}
	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getMarket_enable() {
		return market_enable;
	}
	public void setMarket_enable(String market_enable) {
		this.market_enable = market_enable;
	}
	public String getMall_staff_id() {
		return mall_staff_id;
	}
	public void setMall_staff_id(String mall_staff_id) {
		this.mall_staff_id = mall_staff_id;
	}
	public String getCreator_id() {
		return creator_id;
	}
	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getModifier_id() {
		return modifier_id;
	}
	public void setModifier_id(String modifier_id) {
		this.modifier_id = modifier_id;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public List<AgentInfo> getAgent_names() {
		return agent_names;
	}
	public void setAgent_names(List<AgentInfo> agent_names) {
		this.agent_names = agent_names;
	}
	
	
	
}
