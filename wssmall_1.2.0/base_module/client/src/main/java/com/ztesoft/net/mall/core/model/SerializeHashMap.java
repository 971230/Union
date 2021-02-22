package com.ztesoft.net.mall.core.model;

import java.io.Serializable;
import java.util.Map;

public class SerializeHashMap implements Serializable {
	private static final long serialVersionUID = 808400934523865552L;
	private Map specMap;

	public Map getSpecMap() {
		return specMap;
	}

	public void setSpecMap(Map specMap) {
		this.specMap = specMap;
	}
}
