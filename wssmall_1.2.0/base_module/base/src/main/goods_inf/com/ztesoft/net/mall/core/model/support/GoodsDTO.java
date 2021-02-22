package com.ztesoft.net.mall.core.model.support;

import com.ztesoft.net.mall.core.model.Goods;

import java.io.Serializable;

public class GoodsDTO implements Serializable {
	private Goods goods;
	private String[] photos;
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public String[]getPhotos() {
		return photos;
	}
	public void setPhotos(String[] photos) {
		this.photos = photos;
	}
	
}
