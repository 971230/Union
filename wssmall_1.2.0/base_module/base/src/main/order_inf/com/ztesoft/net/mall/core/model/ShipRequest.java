package com.ztesoft.net.mall.core.model;

/**
 * 
 * 物流请求参数
 * @author wui
 */
public class ShipRequest implements java.io.Serializable {
	private String[] goods_nameArray;
	private String[] goods_snArray;
	private String[] goods_idArray;
	private String[] product_idArray;
	
	private String[] giftidArray;
	private String[] giftnameArray;
	private Integer[] giftnumArray;
	private String[] itemid_idArray;
	
	
	private String[] oldterminalArray;
	private String[] newterminalArray;
	
	private Integer[] numArray;
	
	private String [] order_applyArray;

	private Delivery delivery; //物流支付处理
	
	private String btn_action;
	
	private String action = "returned";//exchange 换货  returned 退货
	
	public String[] getOrder_applyArray() {
		return order_applyArray;
	}

	public void setOrder_applyArray(String[] order_applyArray) {
		this.order_applyArray = order_applyArray;
	}

	public String[] getGoods_nameArray() {
		return goods_nameArray;
	}

	public void setGoods_nameArray(String[] goods_nameArray) {
		this.goods_nameArray = goods_nameArray;
	}

	public String[] getGoods_snArray() {
		return goods_snArray;
	}

	public void setGoods_snArray(String[] goods_snArray) {
		this.goods_snArray = goods_snArray;
	}

	public String[] getGoods_idArray() {
		return goods_idArray;
	}

	public void setGoods_idArray(String[] goods_idArray) {
		this.goods_idArray = goods_idArray;
	}

	public String[] getProduct_idArray() {
		return product_idArray;
	}

	public void setProduct_idArray(String[] product_idArray) {
		this.product_idArray = product_idArray;
	}

	public Integer[] getNumArray() {
		return numArray;
	}

	public void setNumArray(Integer[] numArray) {
		this.numArray = numArray;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public String[] getGiftidArray() {
		return giftidArray;
	}

	public void setGiftidArray(String[] giftidArray) {
		this.giftidArray = giftidArray;
	}

	public String[] getGiftnameArray() {
		return giftnameArray;
	}

	public void setGiftnameArray(String[] giftnameArray) {
		this.giftnameArray = giftnameArray;
	}

	public Integer[] getGiftnumArray() {
		return giftnumArray;
	}

	public void setGiftnumArray(Integer[] giftnumArray) {
		this.giftnumArray = giftnumArray;
	}

	public String[] getOldterminalArray() {
		return oldterminalArray;
	}

	public void setOldterminalArray(String[] oldterminalArray) {
		this.oldterminalArray = oldterminalArray;
	}

	public String[] getNewterminalArray() {
		return newterminalArray;
	}

	public void setNewterminalArray(String[] newterminalArray) {
		this.newterminalArray = newterminalArray;
	}

	public String[] getItemid_idArray() {
		return itemid_idArray;
	}

	public void setItemid_idArray(String[] itemid_idArray) {
		this.itemid_idArray = itemid_idArray;
	}

	public String getBtn_action() {
		return btn_action;
	}

	public void setBtn_action(String btn_action) {
		this.btn_action = btn_action;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	
}