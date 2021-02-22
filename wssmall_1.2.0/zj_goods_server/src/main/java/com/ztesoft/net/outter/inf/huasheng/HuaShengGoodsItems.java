package com.ztesoft.net.outter.inf.huasheng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HuaShengGoodsItems  implements Serializable{

	private List<HuaShengGoodsItemsDetail> ITEM = new ArrayList<HuaShengGoodsItemsDetail>();

	public List<HuaShengGoodsItemsDetail> getITEM() {
		return ITEM;
	}

	public void setITEM(List<HuaShengGoodsItemsDetail> iTEM) {
		ITEM = iTEM;
	}
	
	
	
}
