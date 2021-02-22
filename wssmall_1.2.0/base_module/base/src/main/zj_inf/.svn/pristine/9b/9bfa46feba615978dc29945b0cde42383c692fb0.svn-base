package com.ztesoft.zteopen.service.ecsord;

import params.RuleParams;
import params.ZteResponse;
import params.coqueue.req.CoQueueStandingReq;
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
import zte.net.iservice.IEcsOrdServices;
import zte.params.ecsord.req.GetOrderByBssIdReq;
import zte.params.ecsord.req.GetOrderByInfIdReq;
import zte.params.ecsord.req.GetOrderByOutTidReq;
import zte.params.ecsord.req.GetOrderByVBELNReq;
import zte.params.ecsord.req.GetOutOrderByOrderIdReq;
import zte.params.ecsord.req.GetShippingTypeByIdReq;
import zte.params.ecsord.req.InsertOrderHandLogReq;
import zte.params.ecsord.resp.GetOrderByBssIdResp;
import zte.params.ecsord.resp.GetOrderByInfIdResp;
import zte.params.ecsord.resp.GetOrderByOutTidResp;
import zte.params.ecsord.resp.GetOrderByVBELNResp;
import zte.params.ecsord.resp.GetOutOrderByOrderIdResp;
import zte.params.ecsord.resp.GetShippingTypeByIdResp;
import zte.params.ecsord.resp.InsertOrderHandLogResp;
import zte.params.order.req.OffLineOpenActReq;
import zte.params.order.req.UnimallOrderQueryReq;
import zte.params.order.resp.OffLineOpenActResp;
import zte.params.order.resp.UnimallOrderQueryResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;
/**
 * 
 * @author wu.i
 * 本地dubbo开放服务类
 *
 */
@ServiceMethodBean(version = "1.0")
public class ZteEcsOrdOpenService{

	private IEcsOrdServices ecsOrdServices;
	private void init() {
		if (null == ecsOrdServices) ecsOrdServices = ApiContextHolder.getBean("ecsOrdServices");
	}
	
	@ServiceMethod(method = "zte.orderService.return.apply.req", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderReturnedApplyResp orderReturnedAapply(OrderReturnedApplyReq req){
		this.init();
		return ecsOrdServices.orderReturnedAapply(req);
	}
	
	@ServiceMethod(method = "zte.service.ecsord.process.ruleparams", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ZteResponse processrule(RuleParams ruleParams) throws ApiBusiException {
		this.init();
		return ecsOrdServices.processrule(ruleParams);
	}
	
	@ServiceMethod(method="zte.orderService.unimallorder.query",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public UnimallOrderQueryResp unimallOrderQuery(UnimallOrderQueryReq req) {
		this.init();
		return ecsOrdServices.unimallOrderQuery(req);
	}
	                       
	@ServiceMethod(method="zte.net.ecsord.params.attr.req.attrsyloadReq",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public AttrSyLoadResp attrSyVali(AttrSyLoadReq req) throws ApiBusiException {
		this.init();
		return ecsOrdServices.attrSyVali(req);
	}

	
	@ServiceMethod(method="zte.net.ecsord.params.attr.req.attrswitchparams",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public AttrInstLoadResp handler(AttrSwitchParams req) throws ApiBusiException {
		this.init();
		return ecsOrdServices.handler(req);
	}
	@ServiceMethod(method="zte.net.ecsord.params.attr.req.getOrderByOutTid",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GetOrderByOutTidResp getOrdByOutTid(GetOrderByOutTidReq req)
			throws ApiBusiException {
		this.init();
		return ecsOrdServices.getOrdByOutTid(req);
	}
	
	@ServiceMethod(method="zte.net.ecsord.params.attr.req.getOrdByBssId",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GetOrderByBssIdResp getOrdByBssId(GetOrderByBssIdReq req)
			throws ApiBusiException {
		this.init();
		return ecsOrdServices.getOrdByBssId(req);
	}
	
	@ServiceMethod(method="zte.net.ecsord.params.attr.req.getOrderByInfId",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GetOrderByInfIdResp getOrdByInfId(GetOrderByInfIdReq req)
			throws ApiBusiException {
		this.init();
		return ecsOrdServices.getOrdByInfId(req);
	}
	
	@ServiceMethod(method="zte.net.ecsord.params.attr.req.getOrderByVbeln",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GetOrderByVBELNResp getOrdByVBELN(GetOrderByVBELNReq req)
			throws ApiBusiException {
		this.init();
		return ecsOrdServices.getOrdByVBELN(req);
	}
	
	@ServiceMethod(method="zte.net.ecsord.params.attr.req.getShippingTypeById",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GetShippingTypeByIdResp getShippingTypeById(GetShippingTypeByIdReq req)
			throws ApiBusiException {
		this.init();
		return ecsOrdServices.getShippingTypeById(req);
	}
	
	@ServiceMethod(method="zte.net.ecsord.params.attr.req.getOutOrderByOrderId",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GetOutOrderByOrderIdResp getOutOrderByOrderId(GetOutOrderByOrderIdReq req)
			throws ApiBusiException {
		this.init();
		return ecsOrdServices.getOutOrderByOrderId(req);
	}
	
	
	@ServiceMethod(method="zte.net.ecsord.params.attr.req.getOrderInfo",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderInfoResp getOrderInfo(OrderInfoReq req)
			throws ApiBusiException {
		this.init();
		return ecsOrdServices.getOrderInfo(req);
	}
	
	@ServiceMethod(method="zte.params.ecsord.req.InsertOrderHandLogReq",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public InsertOrderHandLogResp insertOrderHandLog(InsertOrderHandLogReq req) 
			throws ApiBusiException {
		this.init();
		return ecsOrdServices.insertOrderHandLog(req);
	}
	
	@ServiceMethod(method="zte.service.wssmall.ecs.offLineOpenAct",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OffLineOpenActResp offLineOpenAct(OffLineOpenActReq req) throws ApiBusiException {
		this.init();
		return ecsOrdServices.offLineOpenAct(req);
	}
	
	
	@ServiceMethod(method="zte.net.ecsord.exe.coqueuestandingreq",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public BusiCompResponse execBusiComp(CoQueueStandingReq req) 
			throws ApiBusiException {
		this.init();
		BusiCompRequest busiCompRequest = new BusiCompRequest();
		busiCompRequest.setQueryParams(req.getQueryParams());
		busiCompRequest.setEnginePath(req.getEnginePath());
		busiCompRequest.setOrder_id(req.getOrder_id());
		return ecsOrdServices.execBusiComp(busiCompRequest);
	}
	
	@ServiceMethod(method = "zte.net.ecsord.params.ecaop.req.empidessidreq", version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public EmpIdEssIDResp getEssInfoByOrderId(EmpIdEssIDReq req)
			throws ApiBusiException {
		this.init();
		return ecsOrdServices.getEssInfoByOrderId(req);
	}
	
	@ServiceMethod(method = "zte.net.ecsord.params.ecaop.req.empoperinfoessidreq", version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public EmpIdEssIDResp getEssInfoByEssId(EmpInfoByEssIDReq req)
			throws ApiBusiException {
		this.init();
		return ecsOrdServices.getEssInfoByEssId(req);
	}
	
	@ServiceMethod(method = "zte.net.ecsord.params.ecaop.req.EmpInfoByOrderFrom", version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public EmpIdEssIDResp getEssinfoByOrderFrom(EmpInfoByOrderFrom req)
			throws ApiBusiException {
		this.init();
		return ecsOrdServices.getEssinfoByOrderFrom(req);
	}

	@ServiceMethod(method = "zte.net.ecsord.params.attr.req.wygOperatorIDSyn", version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MallOpIDSynInfoResp wygOperatorIDSyn(MallOpIDSynInfoReq req)
			throws ApiBusiException {
		this.init();
		return ecsOrdServices.wygOperatorIDSyn(req);
	}

	@ServiceMethod(method = "zte.net.ecsord.compCodeReq", version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CompCodeResp getCompCode(CompCodeReq req)
			throws ApiBusiException {
		this.init();
		return ecsOrdServices.getCompCode(req);
	}

	@ServiceMethod(method = "zte.net.ecsord.acceptanceQuery", version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public WYGAcceptanceQueryResp acceptanceQuery(WYGAcceptanceQueryReq req)
			throws ApiBusiException {
		this.init();
		return ecsOrdServices.acceptanceQuery(req);
	}

	@ServiceMethod(method = "zte.net.ecsord.huaShengGoodsReq", version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public HuaShengGoodsResp getHuaShengGoods(HuaShengGoodsReq req)
			throws ApiBusiException {
		this.init();
		return ecsOrdServices.getHuaShengGoods(req);
	}

	@ServiceMethod(method = "zte.net.ecsord.queryZbAuditOrders", version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZbAuditOrderQueryResp queryZbAuditOrders(ZbAuditOrderQueryReq req) throws ApiBusiException {
		this.init();
		return this.ecsOrdServices.queryZbAuditOrders(req);
	}
	
	@ServiceMethod(method = "zte.net.ecsord.updateZbAuditStatus", version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZbAuditStatusUpdateResp updateZbAuditStatus(ZbAuditStatusUpdateReq req) throws ApiBusiException {
		this.init();
		return this.ecsOrdServices.updateZbAuditStatus(req);
	}
	@ServiceMethod(method = "zte.net.ecsord.queryZbAuditSuccOrders", version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZbAuditSuccOrderQueryResp queryZbAuditSuccOrders(ZbAuditSuccOrderQueryReq req) throws ApiBusiException {
		this.init();
		return this.ecsOrdServices.queryZbAuditSuccOrders(req);
	}
	@ServiceMethod(method = "zte.net.ecsord.updateZbCrawlerStatus", version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZbCrawlerStatusUpdateResp updateZbCrawlerStatus(ZbCrawlerStatusUpdateReq req) throws ApiBusiException {
		this.init();
		return this.ecsOrdServices.updateZbCrawlerStatus(req);
	}	
	@ServiceMethod(method = "zte.net.iservice.impl.CrawlerProcCondService.getProcCond", version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CrawlerProcCondResp getProcCond(CrawlerProcCondReq cProcCondReq) {
		this.init();
		return ecsOrdServices.getProcCond(cProcCondReq);
	}
	@ServiceMethod(method = "zte.net.ecsord.setPCAutoOrderICCID", version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PCAutoOrderICCIDResp setPCAutoOrderICCID(PCAutoOrderICCIDReq req) {
		// TODO Auto-generated method stub
		this.init();
		return this.ecsOrdServices.setPCAutoOrderICCID(req);
	}
	@ServiceMethod(method = "zte.net.ecsord.updateCrawlerHandleNum", version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse updateCrawlerHandleNum(CrawlerUpdateHandleNumReq req) {
		// TODO Auto-generated method stub
		this.init();
		return this.ecsOrdServices.updateCrawlerHandleNum(req);
	}
}
