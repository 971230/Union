package com.ztesoft.net.service;

import java.util.List;

import com.ztesoft.api.ApiBusiException;

import zte.net.ecsord.params.warehouse.req.WareHouseMatchReq;
import zte.net.ecsord.params.warehouse.resp.WareHouseMatchResp;

public interface IWareHouseManager {

	/**
	 * 仓库匹配地市优先处理逻辑
	 * @param req
	 * @return
	 */
	public WareHouseMatchResp cityCodeFirst(WareHouseMatchReq req) throws ApiBusiException;
	
	/**
	 * 仓库匹配库存优先处理逻辑
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	public WareHouseMatchResp hasNumFirst(WareHouseMatchReq req) throws ApiBusiException;
	
	/**
	 * 仓库匹配自营优先处理逻辑
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	public WareHouseMatchResp ownerFirst(WareHouseMatchReq req) throws ApiBusiException;
	
	/**
	 * 仓库匹配价格优先处理逻辑
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	public WareHouseMatchResp priceFirst(WareHouseMatchReq req) throws ApiBusiException;
	
	/**
	 * 仓库匹配优先级优先处理逻辑
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	public WareHouseMatchResp priorityFirst(WareHouseMatchReq req) throws ApiBusiException;
	
	/**
	 * 仓库匹配随机优先处理逻辑
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	public WareHouseMatchResp RandomFirst(WareHouseMatchReq req) throws ApiBusiException;
	
	/**
	 * 是否需要匹配仓库
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	public Boolean isMatch(String order_id) throws ApiBusiException;
}
