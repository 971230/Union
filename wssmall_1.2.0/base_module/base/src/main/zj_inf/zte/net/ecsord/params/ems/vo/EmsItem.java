package zte.net.ecsord.params.ems.vo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class EmsItem {
	@ZteSoftCommentAnnotationParam(name = "商品名称", type = "String", isNecessary = "Y", desc = "商品名称")
	private String itemName;
	@ZteSoftCommentAnnotationParam(name = "商品数量", type = "String", isNecessary = "Y", desc = "商品数量")
	private int number;
	@ZteSoftCommentAnnotationParam(name = "商品单价", type = "String", isNecessary = "Y", desc = "商品单价（单位：分）")
	private long itemValue;
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public long getItemValue() {
		return itemValue;
	}
	public void setItemValue(long itemValue) {
		this.itemValue = itemValue;
	}
}
