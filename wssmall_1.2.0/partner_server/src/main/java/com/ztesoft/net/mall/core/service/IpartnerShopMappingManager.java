package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.app.base.core.model.PartnerShopMapping;
import com.ztesoft.net.framework.database.Page;

public interface IpartnerShopMappingManager {
	public Page list(PartnerShopMapping obj, int page, int pageSize) ;
	public  PartnerShopMapping getParnterShopMappingByShopCode(String shop_code);
	public void add(PartnerShopMapping obj);
	public int delete(String shop_code);
	public Map getUserInfoByShopCode(String shopCode);
	
	public Map getUserShopingInfoByUserId(String userid);
	
	public List listShopType();
}
