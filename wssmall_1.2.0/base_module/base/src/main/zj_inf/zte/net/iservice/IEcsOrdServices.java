package zte.net.iservice;

import params.RuleParams;
import params.ZteResponse;
import params.req.CrawlerProcCondReq;
import params.req.CrawlerUpdateHandleNumReq;
import params.req.ZbAuditOrderQueryReq;
import params.req.ZbAuditStatusUpdateReq;
import params.req.ZbAuditSuccOrderQueryReq;
import params.req.ZbCrawlerStatusUpdateReq;
import params.resp.CrawlerProcCondResp;
import params.resp.ZbAuditOrderQueryResp;
import params.resp.ZbAuditStatusUpdateResp;
import params.resp.ZbAuditSuccOrderQueryResp;
import params.resp.ZbCrawlerStatusUpdateResp;
import zte.net.card.params.req.PCAutoOrderICCIDReq;
import zte.net.card.params.resp.PCAutoOrderICCIDResp;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.req.CompCodeReq;
import zte.net.ecsord.params.base.req.HuaShengGoodsReq;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.base.resp.CompCodeResp;
import zte.net.ecsord.params.base.resp.HuaShengGoodsResp;
import zte.net.ecsord.params.busiopen.ordinfo.req.OrderInfoReq;
import zte.net.ecsord.params.busiopen.ordinfo.resp.OrderInfoResp;
import zte.net.ecsord.params.ecaop.req.EmpIdEssIDReq;
import zte.net.ecsord.params.ecaop.req.EmpInfoByEssIDReq;
import zte.net.ecsord.params.ecaop.req.EmpInfoByOrderFrom;
import zte.net.ecsord.params.ecaop.resp.EmpIdEssIDResp;
import zte.net.ecsord.params.order.req.OrderReturnedApplyReq;
import zte.net.ecsord.params.order.resp.OrderReturnedApplyResp;
import zte.net.ecsord.params.wyg.req.MallOpIDSynInfoReq;
import zte.net.ecsord.params.wyg.req.WYGAcceptanceQueryReq;
import zte.net.ecsord.params.wyg.resp.MallOpIDSynInfoResp;
import zte.net.ecsord.params.wyg.resp.WYGAcceptanceQueryResp;
import zte.params.ecsord.req.GetOrderByBssIdReq;
import zte.params.ecsord.req.GetOrderByInfIdReq;
import zte.params.ecsord.req.GetOrderByOutTidReq;
import zte.params.ecsord.req.GetOrderByVBELNReq;
import zte.params.ecsord.req.GetOutOrderByOrderIdReq;
import zte.params.ecsord.req.GetShippingTypeByIdReq;
import zte.params.ecsord.req.HasAutoExceptionReq;
import zte.params.ecsord.req.InsertOrderHandLogReq;
import zte.params.ecsord.resp.GetOrderByBssIdResp;
import zte.params.ecsord.resp.GetOrderByInfIdResp;
import zte.params.ecsord.resp.GetOrderByOutTidResp;
import zte.params.ecsord.resp.GetOrderByVBELNResp;
import zte.params.ecsord.resp.GetOutOrderByOrderIdResp;
import zte.params.ecsord.resp.GetShippingTypeByIdResp;
import zte.params.ecsord.resp.HasAutoExceptionResp;
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
	
	@ZteSoftCommentAnnotation(type = "method", desc = "根据BSS_ORDER_ID订单号获取order_id", summary = "根据BSS_ORDER_ID订单号获取order_id")
	public GetOrderByBssIdResp getOrdByBssId(GetOrderByBssIdReq req) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "根据外部订单号获取order_id", summary = "根据外部订单号获取order_id")
	public GetOrderByOutTidResp getOrdByOutTid(GetOrderByOutTidReq req) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "根据外部订单号获取order_id", summary = "根据外部订单号获取zb_inf_id")
	public GetOrderByInfIdResp getOrdByInfId(GetOrderByInfIdReq req) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "根据出库单号获取order_id", summary = "根据出库单号获取order_id")
	public GetOrderByVBELNResp getOrdByVBELN(GetOrderByVBELNReq req) throws ApiBusiException;
	
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
	
	@ZteSoftCommentAnnotation(type = "method", desc = "订单退单申请", summary = "订单退单申请")
	public OrderReturnedApplyResp orderReturnedAapply(OrderReturnedApplyReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "配送方式", summary = "配送方式")
	public GetShippingTypeByIdResp getShippingTypeById(GetShippingTypeByIdReq req)throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "AOP-根据订单号获取ESS工号详情", summary = "AOP-根据订单号获取ESS工号详情")
	public EmpIdEssIDResp getEssInfoByOrderId(EmpIdEssIDReq req) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "AOP-根据ess工号获取ESS工号详情", summary = "AOP-根据ess工号获取ESS工号详情")
	public EmpIdEssIDResp getEssInfoByEssId(EmpInfoByEssIDReq req) throws ApiBusiException;
	@ZteSoftCommentAnnotation(type = "method", desc = "查询是否有未恢复自动化异常", summary = "查询是否有未恢复自动化异常")
	public HasAutoExceptionResp hasAutoException(HasAutoExceptionReq req);
	
	
	@ZteSoftCommentAnnotation(type = "method", desc = "AOP-根据订单来源获取ESS工号信息", summary = "AOP-根据订单来源获取ESS工号信息")
	public EmpIdEssIDResp getEssinfoByOrderFrom(EmpInfoByOrderFrom req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "新商城同步工号信息", summary = "新商城同步工号信息")
	public MallOpIDSynInfoResp wygOperatorIDSyn(MallOpIDSynInfoReq req);

	@ZteSoftCommentAnnotation(type = "method", desc = "根据渠道编码获取货主编码", summary = "根据渠道编码获取货主编码")
	public CompCodeResp getCompCode(CompCodeReq req);

	@ZteSoftCommentAnnotation(type = "method", desc = "沃云购 受理单信息查询", summary = "沃云购 受理单信息查询")
	public WYGAcceptanceQueryResp acceptanceQuery(WYGAcceptanceQueryReq req);

	@ZteSoftCommentAnnotation(type = "method", desc = "获取华盛商品信息", summary = "获取华盛商品信息")
	public HuaShengGoodsResp getHuaShengGoods(HuaShengGoodsReq req);

	@ZteSoftCommentAnnotation(type = "method", desc = "查询总商待审核订单", summary = "查询总商待审核订单")
	public ZbAuditOrderQueryResp queryZbAuditOrders(ZbAuditOrderQueryReq req) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "查询总商审核成功订单", summary = "查询总商审核成功订单")
	public ZbAuditSuccOrderQueryResp queryZbAuditSuccOrders(ZbAuditSuccOrderQueryReq req) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "修改总商订单审核状态", summary = "修改总商订单审核状态")
	public ZbAuditStatusUpdateResp updateZbAuditStatus(ZbAuditStatusUpdateReq req) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "修改总商订单抓取状态", summary = "修改总商订单抓取状态")
	public ZbCrawlerStatusUpdateResp updateZbCrawlerStatus(ZbCrawlerStatusUpdateReq req) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type="method",desc="获取爬虫进程条件",summary="获取爬虫进程条件")
	public CrawlerProcCondResp getProcCond(CrawlerProcCondReq cProcCondReq);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "设置自动写卡ICCID", summary = "设置自动写卡ICCID")
	public PCAutoOrderICCIDResp setPCAutoOrderICCID(PCAutoOrderICCIDReq req); 
	
	@ZteSoftCommentAnnotation(type = "method", desc = "更新爬虫处理条数", summary = "更新爬虫处理条数")
	public ZteResponse updateCrawlerHandleNum(CrawlerUpdateHandleNumReq req); 
	

}
