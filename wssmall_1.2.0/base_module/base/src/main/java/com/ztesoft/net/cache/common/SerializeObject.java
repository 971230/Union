package com.ztesoft.net.cache.common;

import java.io.Serializable;

public class SerializeObject implements Serializable {
	Object obj = new Object();

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}
