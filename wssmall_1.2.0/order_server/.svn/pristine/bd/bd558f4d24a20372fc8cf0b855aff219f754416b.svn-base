package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.framework.database.Page;

public interface IReportManager {

	/**
	 * 云卡买断列表 返回的List中的Map中key为 lan_name, username, sum_pay, userid, lan_id
	 * @param userid 
	 * @param lan_id
	 * @param start_time
	 * @param end_time
	 * @param pageNo
	 * @param pageSize
	 * @param isExport
	 * @return
	 */
	public Page getBuyoutCloudPage(String userid, String[] lan_id, String start_time,
			String end_time, int pageNo, int pageSize, boolean isExport);

	/**
	 * 云卡买断详情
	 * @param username
	 * @param lan_id
	 * @param start_time
	 * @param end_time
	 * @param pageNo
	 * @param pageSize
	 * @param isExport
	 * @return
	 */
	public Page getBuyoutCloudDetail(String userid, String[] lan_id,
			String start_time, String end_time, int pageNo, int pageSize,
			boolean isExport);
	
	/**
	 * 合约计划销售额列表 返回的List中的Map的key有lan_name, userid, username, lan_id, sum_crm_fee, sum_sec_fee
	 * @param userid
	 * @param lan_id
	 * @param start_time
	 * @param end_time
	 * @param pageNo
	 * @param pageSize
	 * @param isExport
	 * @return
	 */
	public Page getContractSaleroomPage(String userid, String[] lan_id,
			String start_time, String end_time, int pageNo, int pageSize,
			boolean isExport);

	/**
	 * 合约计划销售额详情
	 * @param username
	 * @param lan_id
	 * @param start_time
	 * @param end_time
	 * @param pageNo
	 * @param pageSize
	 * @param isExport
	 * @return
	 */
	public Page getContractDetail(String userid, String[] lan_id, String state,
			String start_time, String end_time,  int pageNo, int pageSize,
			boolean isExport);

	
	@Deprecated
	public Page getCloudActiveOfPartnerPage(String userid, String state, String start_time,
			String end_time, int pageNo, int pageSize, boolean isExport);

	@Deprecated
	public Page getCloudActiveOfPartnerDetail(String userid, String[] lan_id,String state,
			String start_time, String end_time, int pageNo, int pageSize,
			boolean isExport);

	@Deprecated
	public Page getCloudActiveOfNetPage(String userid, String[] lan_id, String state,
			String start_time, String end_time, int pageNo, int pageSize,
			boolean isExport);

	@Deprecated
	public Page getCloudActiveOfNetDetail(String userid, String[] lan_id, String state, 
			String start_time, String end_time, int pageNo, int pageSize,
			boolean isExport);

	public Page getContractHandlePage(String userid, String[] lan_id, String state, 
			String start_time, String end_time, int pageNo, int pageSize,
			boolean isExport);

	public Page getCloudActivePage(String userid, String[] lan_id,  String start_time,
			String end_time, int pageNo, int pageSize, boolean isExport);
	
	public Page getCloudActiveDetail(String userid, String[] lan_id, String cloudState, 
			String start_time, String end_time, int pageNo, int pageSize,
			boolean isExport);
	
	
}
