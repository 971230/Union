package com.ztesoft.net.mall.core.model.support;

import java.io.Serializable;

/**
 * 增量刷新商品缓存DTO
 */
public class GoodsRefreshDTO implements Serializable {

	private static final long serialVersionUID = -8805100812117996490L;

	private String goods_ids;

	private String create_start;// 创建时间

	private String create_end;// 创建时间

	public String recently_time;//最近时间
	
	private String sku;
	
	private String product_id;
	
	private String name;
	
	private String tag_id;

	public String getGoods_ids() {
		return goods_ids;
	}

	public void setGoods_ids(String goods_ids) {
		this.goods_ids = goods_ids;
	}

	public String getCreate_start() {
		return create_start;
	}

	public void setCreate_start(String create_start) {
		this.create_start = create_start;
	}

	public String getCreate_end() {
		return create_end;
	}

	public void setCreate_end(String create_end) {
		this.create_end = create_end;
	}

	public String getRecently_time() {
		return recently_time;
	}

	public void setRecently_time(String recently_time) {
		this.recently_time = recently_time;
	}

	public String getSku(){
		return sku;
	}
	public void setSku(String sku){
		this.sku = sku;
	}
	
	public String getProduct_id(){
		return product_id;
	}
	public void setProduct_id(String product_id){
		this.product_id = product_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}
}
