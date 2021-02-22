package com.ztesoft.net.mall.core.service;

import java.util.List;


public interface ISinLoginInfoManager {
	public List list();
	public List getSingleLoginByKey(String stype);
}
