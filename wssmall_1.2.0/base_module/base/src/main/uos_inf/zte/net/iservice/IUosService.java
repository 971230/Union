package zte.net.iservice;

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

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

/**
 * @author Reason.Yea
 * @version 创建时间：2014-9-16
 */
@ZteSoftCommentAnnotation(type = "class", desc = "工作流API", summary = "工作流API[流程启动、流程调度、流程]")
public interface IUosService {
	@ZteSoftCommentAnnotation(type = "method", desc = "启动流程", summary = "启动流程")
	public UosStartResp startProcess(UosStartReq req);

	@ZteSoftCommentAnnotation(type = "method", desc = "处理流程", summary = "处理流程")
	public UosDealResp dealProcess(UosDealReq req);

	@ZteSoftCommentAnnotation(type = "method", desc = "查询流程定义表表", summary = "")
	public UosFlowListResp queryFlowDefineList(UosFlowListReq req);

	@ZteSoftCommentAnnotation(type = "method", desc = "查询流程图", summary = "查询流程图")
	public UosFlowResp queryFlow(UosFlowReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "查询流程实例的流程图", summary = "查询流程实例的流程图")
	public UosFlowResp queryFlowInstance(UosFlowInstanceReq req);

	@ZteSoftCommentAnnotation(type = "method", desc = "查询流程图xml", summary = "查询流程图xml")
	public String queryFlowXml(UosFlowXmlReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "查询流程实例的流程图xml", summary = "查询流程实例的流程图xml")
	public String queryFlowInstanceXml(UosFlowInstanceXmlReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "消费Mq进行数据同步", summary = "消费Mq进行数据同步")
	public MqFlowResq dealMqInfo(MqFlowReq req);
}
