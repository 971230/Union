package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable;
import java.util.List;

public class Flow_Info_Cat_Cfg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1295685168462633257L;

	private String goods_type_id;

	private String goods_cat_id;
	
	private String goods_id;
	
	private List<Flow_Info_Node_Cfg> nodes;

	public String getGoods_type_id() {
		return goods_type_id;
	}

	public void setGoods_type_id(String goods_type_id) {
		this.goods_type_id = goods_type_id;
	}

	public String getGoods_cat_id() {
		return goods_cat_id;
	}

	public void setGoods_cat_id(String goods_cat_id) {
		this.goods_cat_id = goods_cat_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public List<Flow_Info_Node_Cfg> getNodes() {
		return nodes;
	}

	public void setNodes(List<Flow_Info_Node_Cfg> nodes) {
		this.nodes = nodes;
	}
	
}
