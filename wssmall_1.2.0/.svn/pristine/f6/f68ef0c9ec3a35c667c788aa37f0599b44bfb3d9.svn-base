package zte.net.iservice.impl;

import zte.net.iservice.IUosService;
import zte.params.process.req.MqFlowReq;
import zte.params.process.req.UosDealReq;
import zte.params.process.req.UosFlowInstanceReq;
import zte.params.process.req.UosFlowInstanceXmlReq;
import zte.params.process.req.UosFlowListReq;
import zte.params.process.req.UosFlowReq;
import zte.params.process.req.UosFlowXmlReq;
import zte.params.process.req.UosStartReq;
import zte.params.process.resp.MqFlowResq;
import zte.params.process.resp.UosDealResp;
import zte.params.process.resp.UosFlowListResp;
import zte.params.process.resp.UosFlowResp;
import zte.params.process.resp.UosStartResp;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;

/**
 * @author Reason.Yea
 * @version 创建时间：2014-9-16
 */
@ServiceMethodBean(version = "1.0")
public class ZteUosOpenService implements IUosService {
	private IUosService uosService;

	private void init() {
		if (uosService == null)
			uosService = ApiContextHolder.getBean("uosService");
	}

	@Override
	@ServiceMethod(method = "zte.uosService.process.start", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public UosStartResp startProcess(UosStartReq req) {
		this.init();
		return uosService.startProcess(req);
	}

	@Override
	@ServiceMethod(method = "zte.uosService.process.deal", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public UosDealResp dealProcess(UosDealReq req) {
		// TODO Auto-generated method stub
		this.init();
		return uosService.dealProcess(req);
	}

	@Override
	@ServiceMethod(method = "zte.uosService.process.flow", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public UosFlowResp queryFlow(UosFlowReq req) {
		// TODO Auto-generated method stub
		this.init();
		return uosService.queryFlow(req);
	}

	@Override
	@ServiceMethod(method = "zte.uosService.process.list", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public UosFlowListResp queryFlowDefineList(UosFlowListReq req) {
		// TODO Auto-generated method stub
		this.init();
		return uosService.queryFlowDefineList(req);
	}

	@Override
	@ServiceMethod(method = "zte.uosService.process.flowInstance", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@ZteSoftCommentAnnotation(type = "method", desc = "查询流程实例的流程图", summary = "查询流程实例的流程图")
	public UosFlowResp queryFlowInstance(UosFlowInstanceReq req) {
		// TODO Auto-generated method stub
		this.init();
		return uosService.queryFlowInstance(req);
	}

	@Override
	@ServiceMethod(method = "zte.uosService.process.flowXml", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@ZteSoftCommentAnnotation(type = "method", desc = "查询流程图xml", summary = "查询流程图xml")
	public String queryFlowXml(UosFlowXmlReq req) {
		// TODO Auto-generated method stub
		this.init();
		return uosService.queryFlowXml(req);
	}

	@Override
	@ServiceMethod(method = "zte.uosService.process.flowInstanceXml", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@ZteSoftCommentAnnotation(type = "method", desc = "查询流程实例的流程图xml", summary = "查询流程实例的流程图xml")
	public String queryFlowInstanceXml(UosFlowInstanceXmlReq req) {
		// TODO Auto-generated method stub
		this.init();
		return uosService.queryFlowInstanceXml(req);
	}

	
	
	@Override
	@ServiceMethod(method = "zte.params.process.req.MqSend", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@ZteSoftCommentAnnotation(type = "method", desc = "消费Mq进行数据同步", summary = "消费Mq进行数据同步")
	public MqFlowResq dealMqInfo(MqFlowReq req) {
		// TODO Auto-generated method stub
		this.init();
		return uosService.dealMqInfo(req);
	}
}
