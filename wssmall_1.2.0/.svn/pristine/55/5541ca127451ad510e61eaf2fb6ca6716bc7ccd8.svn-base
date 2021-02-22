package com.ztesoft.zteopen.service.logs;

import org.springframework.stereotype.Service;

import params.RuleParams;
import params.ZteResponse;
import zte.net.common.params.req.QueryRequest;
import zte.net.common.params.req.ZteInstInsertRequest;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.common.params.req.ZteVoInsertRequest;
import zte.net.common.params.resp.QueryResponse;
import zte.net.common.params.resp.ZteInstQueryResponse;
import zte.net.common.params.resp.ZteInstUpdateResponse;
import zte.net.common.params.resp.ZteVoInsertResponse;
import zte.net.ecsord.params.base.resp.ZteBusiResponse;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.iservice.ILogsServices;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;
@ServiceMethodBean(version="1.0")
@Service
public class ZteLogsOpenService {

	ILogsServices logsServices;

	private void init() {
		logsServices = ApiContextHolder.getBean("logsServices");
	}
	
	@SuppressWarnings({ "rawtypes"})
	@ServiceMethod(method = "zte.orderService.zteinst.query", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ZteInstQueryResponse getZteRequestInst(ZteInstQueryRequest zteInstParamReq) {
		this.init();
		return logsServices.getZteRequestInst(zteInstParamReq);
	}

	@SuppressWarnings({ "rawtypes"})
	@ServiceMethod(method = "zte.orderService.zteinst.update", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ZteInstUpdateResponse updateZteRequestInst(ZteInstUpdateRequest zteInstParamReq) {
		this.init();
		return logsServices.updateZteRequestInst(zteInstParamReq);
	}
	
	@ZteSoftCommentAnnotation(type = "method", desc = "执行应用平台规则", summary = "执行应用平台规则")
	public ZteResponse processrule(RuleParams ruleParams)
			throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings({ "rawtypes"})
	@ServiceMethod(method = "zte.net.query.basesupportquery", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public QueryResponse baseSupportQuery(QueryRequest req) {
		this.init();
		return logsServices.baseSupportQuery(req);
	}
	

	@SuppressWarnings({ "rawtypes"})
	@ServiceMethod(method = "zte.net.vo.insert", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ZteVoInsertResponse voInsert(ZteVoInsertRequest req) {
		this.init();
		return logsServices.voInsert(req);
	}

	@ZteSoftCommentAnnotation(type = "method", desc = "更新订单树", summary = "更新订单树")
	public ZteResponse cacheOrderTree(OrderTreeBusiRequest orderTreeBusiRequest)
			throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@ZteSoftCommentAnnotation(type = "method", desc = "内存获取订单树", summary = "内存获取订单树")
	public OrderTreeBusiRequest getOrderTreeFromCache(String order_id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings({ "rawtypes"})
	@ServiceMethod(method = "zte.orderService.ztepofinst.pofupdate", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ZteInstUpdateResponse updatePofZteRequestInst(ZteInstUpdateRequest zteInstParamReq) {
		this.init();
		return logsServices.updatePofZteRequestInst(zteInstParamReq);
	}
	
	@ServiceMethod(method = "zte.orderService.zteBusiRequest.insert", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ZteBusiResponse insertZteRequestInst(ZteInstInsertRequest request){
		this.init();
		return this.logsServices.insertZteRequestInst(request);
	}
}
