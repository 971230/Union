package zte.net.iservice;

import params.RuleParams;
import params.ZteResponse;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busiopen.ordinfo.req.OrderInfoReq;
import zte.net.ecsord.params.busiopen.ordinfo.resp.OrderInfoResp;
import zte.params.ecsord.req.GetOrderByInfIdReq;
import zte.params.ecsord.req.GetOrderByOutTidReq;
import zte.params.ecsord.req.GetOutOrderByOrderIdReq;
import zte.params.ecsord.req.InsertOrderHandLogReq;
import zte.params.ecsord.resp.GetOrderByInfIdResp;
import zte.params.ecsord.resp.GetOrderByOutTidResp;
import zte.params.ecsord.resp.GetOutOrderByOrderIdResp;
import zte.params.ecsord.resp.InsertOrderHandLogResp;
import zte.params.order.req.OffLineOpenActReq;
import zte.params.order.req.UnimallOrderQueryReq;
import zte.params.order.resp.OffLineOpenActResp;
import zte.params.order.resp.UnimallOrderQueryResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.attr.hander.AttrSwitchParams;

@ZteSoftCommentAnnotation(type="class",desc="交易中心对外开放API能力",summary="封装交易处理逻辑")
public interface IEcsOrdServices {

	
	/**
	 * 执行规则
	 * @作者 wu.i
	 * @创建日期 2014-3-4 
	 * @param RuleParams
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="执行应用平台规则",summary="执行应用平台规则")
	public ZteResponse processrule(RuleParams ruleParams) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "订单查询优化", summary = "订单查询优化")
	public UnimallOrderQueryResp unimallOrderQuery(UnimallOrderQueryReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "订单同步属性验证", summary = "订单同步属性验证")
	public AttrSyLoadResp attrSyVali(AttrSyLoadReq req) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "订单归集属性值标准化", summary = "订单归集属性值标准化")
	public AttrInstLoadResp handler(AttrSwitchParams req) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "根据外部订单号获取order_id", summary = "根据外部订单号获取order_id")
	public GetOrderByOutTidResp getOrdByOutTid(GetOrderByOutTidReq req) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "根据外部订单号获取order_id", summary = "根据外部订单号获取zb_inf_id")
	public GetOrderByInfIdResp getOrdByInfId(GetOrderByInfIdReq req) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "根据内部订单号获取out_order_id", summary = "根据外部订单号获取zb_inf_id")
	public GetOutOrderByOrderIdResp getOutOrderByOrderId(GetOutOrderByOrderIdReq req) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "调用业务组件执行", summary = "调用业务组件执行")
	public BusiCompResponse execBusiComp(BusiCompRequest busiCompRequest) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "根据条件获取订单信息", summary = "根据条件获取订单信息")
	public OrderInfoResp getOrderInfo(OrderInfoReq orderIsExistReq);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "订单操作日志", summary = "订单操作日志")
	public InsertOrderHandLogResp insertOrderHandLog(InsertOrderHandLogReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "转手工开户", summary = "转手工开户")
	public OffLineOpenActResp offLineOpenAct(OffLineOpenActReq req);

}
