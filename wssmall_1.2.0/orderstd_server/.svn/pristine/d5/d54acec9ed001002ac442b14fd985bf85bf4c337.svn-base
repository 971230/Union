package com.ztesoft.orderstd.service;

import com.ztesoft.net.model.TempInst;

import params.req.BroadBandCtnStandardReq;
import params.req.CenterMallOrderStandardReq;
import params.req.CodePurchaseMallOrderStandardReq;
import params.req.FixBusiOrderStandardReq;
import params.req.GroupOrderStandardReq;
import params.req.HBBroadbandOrderStandardReq;
import params.req.HSMallOrderStandardReq;
import params.req.InternetGroupOrderStandardReq;
import params.req.InternetOrderStandardReq;
import params.req.MobileBusiCtnStandardReq;
import params.req.NewMallOrderStandardReq;
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
import params.resp.FixBusiOrderStandardResp;
import params.resp.GroupOrderStandardResp;
import params.resp.HBBroadbandOrderStandardResp;
import params.resp.HSMallOrderStandardResp;
import params.resp.InternetGroupStandardResp;
import params.resp.InternetOrderStandardResp;
import params.resp.MobileBusiCtnStandardResp;
import params.resp.NewMallOrderStandardResp;
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

public interface IOrderStandingManager {
	public TempInst getTempInstByGoodsIdAndSource(String goods_id,
			String source_from);
	
	public NewMallOrderStandardResp newMallOrderStandard(NewMallOrderStandardReq req);
	
	public CenterMallOrderStandardResp centerMallOrderStandard(CenterMallOrderStandardReq req);
	
	public CodePurchaseMallOrderStandardResp codePurchaseMallOrderStandard(CodePurchaseMallOrderStandardReq req);
	
	public TemplatesOrderStandardResp templatesOrderStandard(TemplatesOrderStandardReq req) ;
  
	public TaoBaoMallOrderStandardResp taoBaoMallOrderStandard(TaoBaoMallOrderStandardReq req);
	  
	public TaoBaoFenxiaoOrderStandardResp taoBaoFenxiaoOrderStandard(TaoBaoFenxiaoOrderStandardReq req);
	
	public HSMallOrderStandardResp hsMallOrderStandard(HSMallOrderStandardReq req);
	
	public TaoBaoTianjiOrderStandardResp taoBaoTianjiOrderStandard(TaoBaoTianjiOrderStandardReq req);
	
	public ZJLocalMallOrderStandardResp zjLocalMallOrderStandard(ZJLocalMallOrderStandardReq req);

	public GroupOrderStandardResp groupOrderStandard(GroupOrderStandardReq req);
	
	public HBBroadbandOrderStandardResp hbBroadbandOrderStandard(HBBroadbandOrderStandardReq req);

	public FixBusiOrderStandardResp fixBusiOrderStandard(FixBusiOrderStandardReq req);
	
	public BroadBandCtnStandardResp broadBandCtnStandard(BroadBandCtnStandardReq req);
	
	public InternetOrderStandardResp internetBusiOrderStandard(InternetOrderStandardReq req);
	
	public InternetGroupStandardResp internetGroupOrderStandard(InternetGroupOrderStandardReq req);
	
	public MobileBusiCtnStandardResp mobileBusiCtnStandard(MobileBusiCtnStandardReq req);
	
	public OrderPreCreateCtnStandardResp orderPreCreateCtnStandard(OrderPreCreateCtnStandardReq req);
	
	public OrderDistributeCtnStandardResp orderDistributeCtnStandard(OrderDistributeCtnStandardReq req);
	
	public StartWorkflowRsp doStartWorkflow(StdStartWorkflowReq req) throws Exception;
	
	public WorkflowMatchRsp doWorkflowMatch(StdWorkflowMatchReq req) throws Exception;

    public ChannelConvertQrySubResp channelConvert(StdChannelConvertReq req) throws Exception;
}
