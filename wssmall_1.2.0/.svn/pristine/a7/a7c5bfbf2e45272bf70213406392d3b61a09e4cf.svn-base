package zte.net.iservice.impl;

import params.req.BroadBandCtnStandardReq;
import params.req.CenterMallOrderStandardReq;
import params.req.CodePurchaseMallOrderStandardReq;
import params.req.DepositOrderStandardReq;
import params.req.FixBusiOrderStandardReq;
import params.req.GroupOrderStandardReq;
import params.req.HSMallOrderStandardReq;
import params.req.InternetGroupOrderStandardReq;
import params.req.InternetOrderStandardReq;
import params.req.MobileBusiCtnStandardReq;
import params.req.NewMallOrderStandardReq;
import params.req.ObjectReplaceStandardReq;
import params.req.OrderBeeReq;
import params.req.OrderDistributeCtnStandardReq;
import params.req.OrderPreCreateCtnStandardReq;
import params.req.StdChannelConvertReq;
import params.req.StdStartWorkflowReq;
import params.req.StdWorkflowMatchReq;
import params.req.TaoBaoFenxiaoOrderStandardReq;
import params.req.TaoBaoMallOrderStandardReq;
import params.req.TaoBaoTianjiOrderStandardReq;
import params.req.TemplatesOrderStandardReq;
import params.req.ZJLocalMallOrderStandardReq;
import params.resp.BroadBandCtnStandardResp;
import params.resp.CenterMallOrderStandardResp;
import params.resp.CodePurchaseMallOrderStandardResp;
import params.resp.DepositOrderStandardResp;
import params.resp.FixBusiOrderStandardResp;
import params.resp.GroupOrderStandardResp;
import params.resp.HSMallOrderStandardResp;
import params.resp.InternetGroupStandardResp;
import params.resp.InternetOrderStandardResp;
import params.resp.MobileBusiCtnStandardResp;
import params.resp.NewMallOrderStandardResp;
import params.resp.ObjectReplaceStandardResp;
import params.resp.OrderBeeResp;
import params.resp.OrderDistributeCtnStandardResp;
import params.resp.OrderPreCreateCtnStandardResp;
import params.resp.TaoBaoFenxiaoOrderStandardResp;
import params.resp.TaoBaoMallOrderStandardResp;
import params.resp.TaoBaoTianjiOrderStandardResp;
import params.resp.TemplatesOrderStandardResp;
import params.resp.ZJLocalMallOrderStandardResp;
import zte.net.ecsord.params.ecaop.resp.ChannelConvertQrySubResp;
import zte.net.ecsord.params.order.resp.StartWorkflowRsp;
import zte.net.ecsord.params.order.resp.WorkflowMatchRsp;
import zte.net.iservice.IOrderstdService;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;

@ServiceMethodBean(version="1.0")
public class ZteOrderstdOpensService {
	private IOrderstdService orderstdService;
	 
	public void init(){
		orderstdService = ApiContextHolder.getBean("orderstdService");;
	}
	
	@ServiceMethod(method="zte.net.orderstd.newMallOrderStandard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public NewMallOrderStandardResp newMallOrderStandard(
			NewMallOrderStandardReq req) {
		init();
		return orderstdService.newMallOrderStandard(req);
	}

	@ServiceMethod(method="zte.net.orderstd.centerMallOrderStandard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CenterMallOrderStandardResp centerMallOrderStandard(
			CenterMallOrderStandardReq req) {
		init();
		return orderstdService.centerMallOrderStandard(req);
	}

	@ServiceMethod(method="zte.net.orderstd.codePurchaseMallOrderStandard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CodePurchaseMallOrderStandardResp codePurchaseMallOrderStandard(
			CodePurchaseMallOrderStandardReq req) {
		init();
		return orderstdService.codePurchaseMallOrderStandard(req);
	}
	
	@ServiceMethod(method="zte.net.orderstd.GroupOrderStandard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GroupOrderStandardResp GroupOrderStandard(GroupOrderStandardReq req) {
		init();
		return orderstdService.GroupOrderStandard(req);
	}
	
	@ServiceMethod(method="zte.net.orderstd.taoBaoMallOrderStandard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public TaoBaoMallOrderStandardResp taoBaoMallOrderStandard(
			TaoBaoMallOrderStandardReq req) {
		init();
		return orderstdService.taoBaoMallOrderStandard(req);
	}
	
	@ServiceMethod(method="zte.net.orderstd.taoBaoFenxiaoOrderStandard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public TaoBaoFenxiaoOrderStandardResp taoBaoFenxiaoOrderStandard(
			TaoBaoFenxiaoOrderStandardReq req) {
		init();
		return orderstdService.taoBaoFenxiaoOrderStandard(req);
	}
	
	@ServiceMethod(method="zte.net.orderstd.taoBaoTianjiOrderStandard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public TaoBaoTianjiOrderStandardResp taoBaoTianjiOrderStandard(TaoBaoTianjiOrderStandardReq req) {
		init();
		return orderstdService.taoBaoTianjiOrderStandard(req);
	}
	
	@ServiceMethod(method="zte.net.orderstd.templatesOrderStandard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public TemplatesOrderStandardResp templatesOrderStandard(
			TemplatesOrderStandardReq req) {
		init();
		return orderstdService.templatesOrderStandard(req);
	}
	
	@ServiceMethod(method="zte.net.orderstd.HSMallOrderStandard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public HSMallOrderStandardResp hsMallOrderStandard(
			HSMallOrderStandardReq req) {
		init();
		return orderstdService.hsMallOrderStandard(req);
	}
	
	@ServiceMethod(method="zte.net.orderstd.ZJLocalMallOrderStandard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZJLocalMallOrderStandardResp zjLocalMallOrderStandard(
			ZJLocalMallOrderStandardReq req) {
		init();
		return orderstdService.zjLocalMallOrderStandard(req);
	}
	
	@ServiceMethod(method="zte.net.orderstd.fixBusiOrderStandard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public FixBusiOrderStandardResp fixBusiOrderStandard(FixBusiOrderStandardReq req) {
		init();
		return orderstdService.fixBusiOrderStandard(req);
	}

	@ServiceMethod(method="zte.net.orderstd.broadBandCtnStandard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public BroadBandCtnStandardResp broadBandCtnStandard(BroadBandCtnStandardReq req) {
		init();
		return orderstdService.broadBandCtnStandard(req);
	}
	
	@ServiceMethod(method="zte.net.orderstd.internetBusiOrderStandard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public InternetOrderStandardResp internetBusiOrderStandard(InternetOrderStandardReq req) {
		init();
		return orderstdService.internetBusiOrderStandard(req);
	}
	
	@ServiceMethod(method="zte.net.orderstd.internetGroupOrderStandard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public InternetGroupStandardResp internetGroupOrderStandard(InternetGroupOrderStandardReq req) {
		init();
		return orderstdService.internetGroupOrderStandard(req);
	}
	
	@ServiceMethod(method="zte.net.orderstd.MobileBusiCtnStandard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MobileBusiCtnStandardResp mobileBusiCtnStandard(MobileBusiCtnStandardReq req) {
		init();
		return orderstdService.mobileBusiCtnStandard(req);
	}
	
	@ServiceMethod(method="zte.net.orderstd.OrderPreCreateCtnStandard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderPreCreateCtnStandardResp orderPreCreateCtnStandard(OrderPreCreateCtnStandardReq req) {
		init();
		return orderstdService.orderPreCreateCtnStandard(req);
	}
	
	@ServiceMethod(method="zte.net.orderstd.OrderDistributeCtnStandard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderDistributeCtnStandardResp orderDistributeCtnStandard(OrderDistributeCtnStandardReq req) {
		init();
		return orderstdService.orderDistributeCtnStandard(req);
	}
	
	@ServiceMethod(method="zte.net.orderstd.workCustom.startWorkflow",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public StartWorkflowRsp doStartWorkflow(StdStartWorkflowReq req)
			throws Exception {
		init();
		return orderstdService.doStartWorkflow(req);
	}

	@ServiceMethod(method="zte.net.orderstd.workCustom.workflowMatch",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public WorkflowMatchRsp doWorkflowMatch(StdWorkflowMatchReq req)
			throws Exception {
		init();
		return orderstdService.doWorkflowMatch(req);
	}
	@ServiceMethod(method="ecaop.trades.query.channelStd.convert.qry",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
    public ChannelConvertQrySubResp channelConvert(StdChannelConvertReq req)throws Exception {
        init();
        return orderstdService.channelConvert(req);
    }
	@ServiceMethod(method="zte.net.orderstd.objectReplaceStandard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ObjectReplaceStandardResp objectReplaceStandard(ObjectReplaceStandardReq req) {
		init();
		return orderstdService.objectReplaceStandard(req);
	}
	
	@ServiceMethod(method="zte.net.orderstd.orderUpdate",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public NewMallOrderStandardResp orderUpdate(NewMallOrderStandardReq req) throws Exception{
		init();
		return orderstdService.orderUpdate(req);
	}
	
	@ServiceMethod(method="zte.net.orderstd.orderDepositStandard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public DepositOrderStandardResp orderDepositStandard(
			DepositOrderStandardReq req) throws Exception {
		init();
		return orderstdService.orderDepositStandard(req);
	}
	
	@ServiceMethod(method = "zte.net.orderstd.orderMakeupInsert", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderBeeResp orderBeeMakeupInsert(OrderBeeReq req) throws Exception {
		init();
		return orderstdService.orderBeeMakeupInsert(req);
	}
}


