package com.service;

import javax.jws.WebService;

import com.service.beans.CommonResponse;
import com.service.beans.SyncAuditingInfoRequest;
import com.service.beans.SyncOrderStatusRequest;
import com.service.beans.SyncTerminalImeiRequest;
import com.service.beans.SyncWriteCardRequest;

@WebService
public interface WmsInfoService {

	/**
	 * 同步终端串号
	 * 调用方：WMS
	 * @param request
	 * @return
	 */
	public CommonResponse syncTerminalImei(SyncTerminalImeiRequest request);
	
	/**
	 * 同步写卡数据
	 * 调用方：WMS
	 * @param request
	 * @return
	 */
	public CommonResponse syncWriteCardInfo(SyncWriteCardRequest request);
	
	/**
	 * 同步订单状态
	 * 调用方：WMS
	 * @param request
	 * @return
	 */
	public CommonResponse syncOrderStatus(SyncOrderStatusRequest request);
	
	/**
	 * 同步稽查数据
	 * 调用方：WMS
	 * @param request
	 * @return
	 */
	public CommonResponse syncAuditingInfo(SyncAuditingInfoRequest request);
}
