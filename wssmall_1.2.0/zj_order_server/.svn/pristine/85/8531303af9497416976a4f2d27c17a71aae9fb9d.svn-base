package com.ztesoft.net.service;

import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.nd.req.DispatchingNumReceivingNDRequset;
import zte.net.ecsord.params.nd.req.NotifyOrderStatuNDRequset;
import zte.net.ecsord.params.nd.req.OrderDealSuccessNDRequset;
import zte.net.ecsord.params.sf.req.ArtificialSelectRequest;
import zte.net.ecsord.params.sf.req.RoutePushServiceRequset;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.model.SDSStatusLogVO;

/**
 * 闪电送业务处理类
 * @author 吴家勇
 * 2015.01.20
 */
public interface IOrderSDSModelManager {

	/**
	 * 根据闪电送状态对象新增数据
	 * @param sdsStatusLogVO
	 */
	public void insertLogByVO(SDSStatusLogVO sdsStatusLogVO);
	
	public BusiCompResponse lockOrderByWlUser(DispatchingNumReceivingNDRequset request) throws ApiBusiException;
	
	public BusiCompResponse sfOrderRespSyn(ArtificialSelectRequest request) throws ApiBusiException;
	
	public BusiCompResponse sfRoutePush(RoutePushServiceRequset req) throws ApiBusiException;
	
	public BusiCompResponse statusNoticFromND(NotifyOrderStatuNDRequset request) throws ApiBusiException ;
	
	public BusiCompResponse ndFinishNotic(OrderDealSuccessNDRequset request) throws ApiBusiException ;

	String findSfMonthAccount(String orderid);
}
