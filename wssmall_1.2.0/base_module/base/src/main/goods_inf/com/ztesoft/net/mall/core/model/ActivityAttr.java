package com.ztesoft.net.mall.core.model;

import java.util.List;

/**
 * 活动附加属性
 * @author Administrator
 *
 */
public class ActivityAttr  implements java.io.Serializable{
	
	private String pmt_goods_ids  ;   //活动商品ID,多个用逗号隔开
	private String pmt_goods_names  ; //活动商品名称,多个用逗号隔开
	private String act_org_ids  ;   //活动商城id,多个用逗号隔开
	private String act_org_names  ; //活动商城id,多个用逗号隔开
	private String gift_goods_ids;   //赠品（货品）id,多个用逗号隔开 
	private String publish_status;  //发布状态
	private String isSaveOrg; //是否保存发布组织
	private String goods_ids; //适用商品id
	
	private List<Goods> goodsList;  //适用商品
	private List<Goods> giftList;  //赠品
	
	
	public String getPmt_goods_ids() {
		return pmt_goods_ids;
	}
	public void setPmt_goods_ids(String pmt_goods_ids) {
		this.pmt_goods_ids = pmt_goods_ids;
	}
	public String getPmt_goods_names() {
		return pmt_goods_names;
	}
	public void setPmt_goods_names(String pmt_goods_names) {
		this.pmt_goods_names = pmt_goods_names;
	}
	public String getAct_org_ids() {
		return act_org_ids;
	}
	public void setAct_org_ids(String act_org_ids) {
		this.act_org_ids = act_org_ids;
	}
	public String getAct_org_names() {
		return act_org_names;
	}
	public void setAct_org_names(String act_org_names) {
		this.act_org_names = act_org_names;
	}
	public String getGift_goods_ids() {
		return gift_goods_ids;
	}
	public void setGift_goods_ids(String gift_goods_ids) {
		this.gift_goods_ids = gift_goods_ids;
	}
	public List<Goods> getGiftList() {
		return giftList;
	}
	public void setGiftList(List<Goods> giftList) {
		this.giftList = giftList;
	}
	public String getPublish_status() {
		return publish_status;
	}
	public void setPublish_status(String publish_status) {
		this.publish_status = publish_status;
	}
	public List<Goods> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<Goods> goodsList) {
		this.goodsList = goodsList;
	}
	public String getIsSaveOrg() {
		return isSaveOrg;
	}
	public void setIsSaveOrg(String isSaveOrg) {
		this.isSaveOrg = isSaveOrg;
	}
	public String getGoods_ids() {
		return goods_ids;
	}
	public void setGoods_ids(String goods_ids) {
		this.goods_ids = goods_ids;
	}

	

}
