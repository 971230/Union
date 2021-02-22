package com.ztesoft.net.mall.core.model;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.eop.sdk.utils.UploadUtilc;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.model.Goods;

/**
 * 团购实体
 * @author kingapex
 *
 */
public class GroupBuy implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String groupid;
	private Double price;
	private Double discount;
	
	private String title;		//	团购标题
	private String descript;	//	说明文字
	private String content;		//	详细说明
	private String start_time;		//	开始时间
	private String end_time;		//	结束时间
	private String goodsid;		//	商品ID
	private int buy_count;		//	购买数量
	private String add_time;
	private int dis_type;
	private String img;			//	团购图片，使用image可返回完整路径
	private int is_index;
	private double oldprice;	//	商品的原始价格，非数据库字段 
	
	private String catid;			//商品所在的类别
	private String show_time_type;//展示类别  0：准时展示，1：提前展示
	private String show_day;	//提前展示天数
	private int disabled;	//是否有效    0：正常，1：无效
	private int buyed_count;			//已团购数量
	

	private Goods goods;
	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	private List<GroupBuyCount> countRuleList; //数量增长规则

	@NotDbField
	public final List<GroupBuyCount> getCountRuleList() {
		return countRuleList;
	}

	public final void setCountRuleList(List<GroupBuyCount> countRuleList) {
		this.countRuleList = countRuleList;
	}
	
	@NotDbField
	public final Goods getGoods() {
		return goods;
	}

	public final void setGoods(Goods goods) {
		this.goods = goods;
	}

	public   String getGroupid() {
		return groupid;
	}

	public   void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public  String getTitle() {
		return title;
	}

	public  void setTitle(String title) {
		this.title = title;
	}

	public  String getContent() {
		return content;
	}

	public  void setContent(String content) {
		this.content = content;
	}

	public  String getStart_time() {
		return start_time;
	}

	public  void setStart_time(String startTime) {
		start_time = startTime;
	}

	public  String getEnd_time() {
		return end_time;
	}

	public  void setEnd_time(String endTime) {
		end_time = endTime;
	}

	public  String getGoodsid() {
		return goodsid;
	}

	public  void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public  int getBuy_count() {
		return buy_count;
	}

	public  void setBuy_count(int buyCount) {
		buy_count = buyCount;
	}

	public  String getAdd_time() {
		return add_time;
	}

	public  void setAdd_time(String addTime) {
		add_time = addTime;
	}

	public  Double getPrice() {
		return price;
	}

	public  void setPrice(Double price) {
		this.price = price;
	}

	public  Double getDiscount() {
		return discount;
	}

	public  void setDiscount(Double discount) {
		this.discount = discount;
	}

	public   int getDis_type() {
		return dis_type;
	}

	public   void setDis_type(int disType) {
		dis_type = disType;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@NotDbField
	public String getImage(){
		return UploadUtilc.replacePath(img);
		
	}

	public int getIs_index() {
		return is_index;
	}

	public void setIs_index(int isIndex) {
		is_index = isIndex;
	}

	
	@NotDbField
	public String getCatid() {
		return catid;
	}
	@NotDbField
	public void setCatid(String catid) {
		this.catid = catid;
	}

	@NotDbField
	public double getOldprice() {
		return oldprice;
	}

	public void setOldprice(double oldprice) {
		this.oldprice = oldprice;
	}

	public int getBuyed_count() {
		return buyed_count;
	}

	public void setBuyed_count(int buyed_count) {
		this.buyed_count = buyed_count;
	}

	public String getShow_time_type() {
		return show_time_type;
	}

	public void setShow_time_type(String show_time_type) {
		this.show_time_type = show_time_type;
	}

	public String getShow_day() {
		return show_day;
	}

	public void setShow_day(String show_day) {
		this.show_day = show_day;
	}

	public int getDisabled() {
		return disabled;
	}

	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}
}
