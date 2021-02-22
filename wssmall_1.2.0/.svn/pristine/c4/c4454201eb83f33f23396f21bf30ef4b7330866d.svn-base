package zte.net.iservice;

import params.order.req.OrderIdReq;
import params.order.resp.OrderIdResp;
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

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="订单标准化管理API",summary="订单标准化管理API[订单标准化]")
public interface IOrderstdService {
	
	@ZteSoftCommentAnnotation(type="method",desc="新商城订单标准化",summary="新商城订单标准化")
	public NewMallOrderStandardResp newMallOrderStandard(NewMallOrderStandardReq req);

	@ZteSoftCommentAnnotation(type="method",desc="总部商城订单标准化",summary="总部商城订单标准化")
	public CenterMallOrderStandardResp centerMallOrderStandard(CenterMallOrderStandardReq req);

	@ZteSoftCommentAnnotation(type="method",desc="码上购商城订单标准化",summary="总部商城订单标准化")
	public CodePurchaseMallOrderStandardResp codePurchaseMallOrderStandard(CodePurchaseMallOrderStandardReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="淘宝商城订单标准化",summary="淘宝商城订单标准化")
	public TaoBaoMallOrderStandardResp taoBaoMallOrderStandard(TaoBaoMallOrderStandardReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="淘宝分销订单标准化",summary="淘宝分销订单标准化")
	public TaoBaoFenxiaoOrderStandardResp taoBaoFenxiaoOrderStandard(TaoBaoFenxiaoOrderStandardReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="淘宝天机订单标准化",summary="淘宝天机订单标准化")
	public TaoBaoTianjiOrderStandardResp taoBaoTianjiOrderStandard(TaoBaoTianjiOrderStandardReq req);
//    public OrderStandardPushResp pushOrderStandard(OrderStandardPushReq req) throws Exception;
	
    public OrderIdResp orderId(OrderIdReq req);
    
    @ZteSoftCommentAnnotation(type="method",desc="模版转换订单标准化",summary="模版转换订单标准化")
    public TemplatesOrderStandardResp templatesOrderStandard(TemplatesOrderStandardReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="华盛订单标准化",summary="华盛订单标准化")
	public HSMallOrderStandardResp hsMallOrderStandard(HSMallOrderStandardReq req);

	@ZteSoftCommentAnnotation(type="method",desc="浙江本地商城订单标准化",summary="浙江本地商城订单标准化")
	public ZJLocalMallOrderStandardResp zjLocalMallOrderStandard(ZJLocalMallOrderStandardReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="融合业务订单标准化",summary="融合业务订单标准化")
	public GroupOrderStandardResp GroupOrderStandard(GroupOrderStandardReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="温大固网业务订单标准化",summary="温大固网业务订单标准化")
	public FixBusiOrderStandardResp fixBusiOrderStandard(FixBusiOrderStandardReq req);

	@ZteSoftCommentAnnotation(type="method",desc="新固网业务订单标准化",summary="新固网业务订单标准化")
	public BroadBandCtnStandardResp broadBandCtnStandard(BroadBandCtnStandardReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="互联网业务订单标准化",summary="互联网业务订单标准化")
	public InternetOrderStandardResp internetBusiOrderStandard(InternetOrderStandardReq req) ;

	@ZteSoftCommentAnnotation(type="method",desc="互联网融合业务订单标准化",summary="互联网融合业务订单标准化")
	public InternetGroupStandardResp internetGroupOrderStandard(InternetGroupOrderStandardReq req) ;
	
	@ZteSoftCommentAnnotation(type="method",desc="移网产品活动业务标准化",summary="移网产品活动业务标准化")
	public MobileBusiCtnStandardResp mobileBusiCtnStandard(MobileBusiCtnStandardReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="新零售商品预定接口标准化",summary="新零售商品预定接口标准化")
	public OrderPreCreateCtnStandardResp orderPreCreateCtnStandard(OrderPreCreateCtnStandardReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="订单下发接口标准化",summary="订单下发接口标准化")
	public OrderDistributeCtnStandardResp orderDistributeCtnStandard(OrderDistributeCtnStandardReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "启动自定义流程", summary = "启动自定义流程")
	public StartWorkflowRsp doStartWorkflow(StdStartWorkflowReq req) throws Exception;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "自定义流程匹配", summary = "自定义流程匹配")
	public WorkflowMatchRsp doWorkflowMatch(StdWorkflowMatchReq req) throws Exception;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "终端更换标准化", summary = "终端更换标准化")
	public ObjectReplaceStandardResp objectReplaceStandard(ObjectReplaceStandardReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "渠道转换", summary = "渠道转换")
    public ChannelConvertQrySubResp channelConvert(StdChannelConvertReq req)throws Exception;

	@ZteSoftCommentAnnotation(type="method",desc="订单更新",summary="订单更新")
	public NewMallOrderStandardResp orderUpdate(NewMallOrderStandardReq req) throws Exception;
	
	@ZteSoftCommentAnnotation(type="method",desc="押金收单标准化",summary="押金收单标准化")
	public DepositOrderStandardResp orderDepositStandard(DepositOrderStandardReq req) throws Exception;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "总部蜂行动订单接收接口", summary = "总部蜂行动订单接收接口")
	public OrderBeeResp orderBeeMakeupInsert(OrderBeeReq requ) throws Exception;
}
