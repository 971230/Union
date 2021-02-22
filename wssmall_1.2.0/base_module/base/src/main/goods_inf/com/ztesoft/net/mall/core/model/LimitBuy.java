package com.ztesoft.net.mall.core.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.eop.sdk.utils.UploadUtilc;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 限时抢购
 * 
 * @author kingapex
 * 
 */
public class LimitBuy implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String start_time;
	private String end_time;
	private String add_time;
	private String img;
	private int is_index;
	private String show_time_type;//展示类别  0：准时展示，1：提前展示
	private String show_day;	//提前展示天数
	private int disabled;	//是否有效    0：正常，1：无效
	
	//限时购买商品对照列表，非数据库字段
	private List<LimitBuyGoods> limitBuyGoodsList;
	private List<Map> goodsList;
	
	private int cart_num; //购物车限购数量
	@NotDbField
	public String getEndTime(){
		
		return end_time;
	}
	
	@NotDbField
	public List<Map> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Map> goodsList) {
		this.goodsList = goodsList;
	}

	@NotDbField
	public List<LimitBuyGoods> getLimitBuyGoodsList() {
		return limitBuyGoodsList;
	}

	public void setLimitBuyGoodsList(List<LimitBuyGoods> limitBuyGoodsList) {
		this.limitBuyGoodsList = limitBuyGoodsList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String startTime) {
		start_time = startTime;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String endTime) {
		end_time = endTime;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String addTime) {
		add_time = addTime;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	 //转换路径后的图片路径，非数据库字段
	@NotDbField
	public String getImage() {
		return UploadUtilc.replacePath(img);
	}

	public int getIs_index() {
		return is_index;
	}

	public void setIs_index(int isIndex) {
		is_index = isIndex;
	}
	
	@NotDbField
	public int getCart_num() {
		return cart_num;
	}
	@NotDbField
	public void setCart_num(int cart_num) {
		this.cart_num = cart_num;
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
