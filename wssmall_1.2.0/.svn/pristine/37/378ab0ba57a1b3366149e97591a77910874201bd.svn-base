package zte.net.iservice.impl;

import params.req.OrderExpMarkProcessedReq;
import params.req.OrderExpWriteForBusReq;
import params.req.OrderExpWriteForInfReq;
import params.req.QueryExpCatalogReq;
import params.req.QueryExpOrderStatisticsReq;
import params.resp.OrderExpMarkProcessedResp;
import params.resp.OrderExpWriteResp;
import params.resp.QueryExpCatalogResp;
import params.resp.QueryExpOrderStatisticsResp;
import zte.net.iservice.IOrderExpServices;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;

/**
 * 
 * @Package zte.net.iservice.impl
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author shenqiyu 
 * @date 2015年11月19日 下午3:40:09
 */
@ServiceMethodBean(version="1.0")
public class ZteOrderExpOpenService implements IOrderExpServices{
	private IOrderExpServices orderExpServices;
	private void init() {
		if (null == orderExpServices) orderExpServices = ApiContextHolder.getBean("orderExpServices");
	}
	
	@Override
	@ServiceMethod(method="zte.net.orderexp.orderexpWriteForInf",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderExpWriteResp exeOrderExpWrite(OrderExpWriteForInfReq req) {
		init();
		return orderExpServices.exeOrderExpWrite(req);
	}
	
	@Override
	@ServiceMethod(method="zte.net.orderexp.orderexpWriteForBus",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderExpWriteResp exeOrderExpWrite(OrderExpWriteForBusReq req) {
		init();
		return orderExpServices.exeOrderExpWrite(req);
	}

	@Override
	@ServiceMethod(method="zte.net.orderexp.queryExpCatalog",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public QueryExpCatalogResp queryExpCatalogs(QueryExpCatalogReq req) {
		init();
		return orderExpServices.queryExpCatalogs(req);
	}

	@Override
	@ServiceMethod(method="zte.net.orderexp.queryExpOrderStatistics",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public QueryExpOrderStatisticsResp queryExpOrderStatistics(
			QueryExpOrderStatisticsReq req) {
		init();
		return orderExpServices.queryExpOrderStatistics(req);
	}

	@Override
	@ServiceMethod(method="zte.net.orderexp.orderExpMarkProcessed",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderExpMarkProcessedResp orderExpMarkProcessed(
			OrderExpMarkProcessedReq req) {
		init();
		return orderExpServices.orderExpMarkProcessed(req);
	}
}
