package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.app.base.plugin.fileUpload.BatchResult;

import java.util.List;
import java.util.Map;


/**
 * 商品管理接口
 * 
 * @author kingapex
 * 
 */
public interface IGoodsImportManager {
	public BatchResult importAdjunct(List inList,String goods_id);
	
	public Map getAdjunct(String adjunctid);
	
}
