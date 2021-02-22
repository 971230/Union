package com.ztesoft.net.mall.core.service;

/**
 * 会员积分管理接口
 * @author kingapex
 *
 */
public interface IMemberPointManger {

	
	/**
	 * 使用消费积分 
	 * @param memberid
	 * @param point
	 * @param reson
	 * @param relatedId
	 */
	public void useMarketPoint(String memberid,int point,String reson,String relatedId);
	
	
	
	/**
	 * 检测某项是否获取积分
	 * @param itemname
	 * @return
	 */
	public boolean checkIsOpen(String itemname);
	
	/**
	 * 获取某项的获取积分值
	 * @param itemname
	 * @return
	 */
	public int getItemPoint(String itemname);
	
	/**
	 * 为会员增加积分
	 * @param point
	 * @param itemname
	 */
	public void add(String memberid,int point,String itemname,String relatedId);
	
	
}
