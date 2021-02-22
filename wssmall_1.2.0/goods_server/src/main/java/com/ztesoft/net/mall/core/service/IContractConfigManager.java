package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.database.Page;

public interface IContractConfigManager {
	
	/**
	 * 查询终端
	 * @param pageNo
	 * @param pageSize
	 * @param terminal_name
	 * @return
	 */
	public Page getTerminalList(int pageNo, int pageSize,String terminal_name,String terminal_type);
	
	/**
	 * 查询合约
	 * @param pageNo
	 * @param pageSize
	 * @param contract_name
	 * @return
	 */
	public Page getContractList(int pageNo, int pageSize,String contract_name);
	
	/**
	 * 查询套餐
	 * @param pageNo
	 * @param pageSize
	 * @param contract_name
	 * @return
	 */
	public Page getOfferList(int pageNo, int pageSize,String contract_name,String selected_contracts);
	
	
	/**
	 * 查询商品包
	 * @param name
	 * @return
	 */
	public Map getTagsByName(String tag_name);
	
	
	/**
	 * 获取套餐档次
	 * @return
	 */
	public List getPackegeLevel();
}