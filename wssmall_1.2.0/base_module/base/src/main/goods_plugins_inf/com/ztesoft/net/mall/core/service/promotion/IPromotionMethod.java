package com.ztesoft.net.mall.core.service.promotion;

/**
 * 促销方式接口
 * @author kingapex
 *2010-4-15下午03:30:09
 */
public interface IPromotionMethod {

	/**
	 * 定义促销方式名
	 * @return
	 */
	public String getName();
	
	
	
	
	public String getInputHtml(String pmtid,String solution);
	
	
	
	/**
	 * 
	 * @param pmtid
	 * @return
	 */
	public String onPromotionSave(String pmtid);
	
 
	
}
