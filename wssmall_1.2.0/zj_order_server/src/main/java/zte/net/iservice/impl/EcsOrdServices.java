package zte.net.iservice.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.MethodUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import params.RuleParams;
import params.ZteError;
import params.ZteResponse;
import params.order.req.OrderExceptionLogsReq;
import params.order.req.OrderHandleLogsReq;
import params.order.req.OrderQueueCardActionLogReq;
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
import rule.AbstractRuleHander;
import services.OrderRuleInf;
import services.ServiceBase;
import util.ChannelidProductcompRefUtil;
import util.EssInfoUtil;
import util.EssOperatorInfoUtil;
import utils.CoreThreadLocalHolder;
import vo.Rule;
import zte.net.card.params.req.PCAutoOrderICCIDReq;
import zte.net.card.params.resp.PCAutoOrderICCIDResp;
import zte.net.common.rule.ZteCommonTraceRule;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.req.CompCodeReq;
import zte.net.ecsord.params.base.req.HuaShengGoodsReq;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.base.resp.CompCodeResp;
import zte.net.ecsord.params.base.resp.HuaShengGoodsResp;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.busiopen.ordinfo.req.OrderInfoReq;
import zte.net.ecsord.params.busiopen.ordinfo.resp.OrderInfoResp;
import zte.net.ecsord.params.ecaop.req.EmpIdEssIDReq;
import zte.net.ecsord.params.ecaop.req.EmpInfoByEssIDReq;
import zte.net.ecsord.params.ecaop.req.EmpInfoByOrderFrom;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.resp.EmpIdEssIDResp;
import zte.net.ecsord.params.order.req.OrderReturnedApplyReq;
import zte.net.ecsord.params.order.resp.OrderReturnedApplyResp;
import zte.net.ecsord.params.wyg.req.MallOpIDSynInfoReq;
import zte.net.ecsord.params.wyg.req.WYGAcceptanceQueryReq;
import zte.net.ecsord.params.wyg.resp.MallOpIDSynInfoResp;
import zte.net.ecsord.params.wyg.resp.WYGAcceptanceQueryResp;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.ecsord.utils.PCWriteCardTools;
import zte.net.iservice.IEcsOrdServices;
import zte.net.iservice.IRuleService;
import zte.net.params.req.RuleExeLogDelReq;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.RuleTreeExeResp;
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
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.rule.util.RuleFlowUtil;
import com.ztesoft.net.service.ICommonManager;
import com.ztesoft.net.service.IOrderCrawlerManager;
import com.ztesoft.net.service.IOrderExtManager;
import com.ztesoft.net.service.IOrderFlowManager;
import com.ztesoft.net.service.impl.UnimallOrderQueryManager;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.rop.annotation.ServiceMethodBean;
import commons.CommonTools;

import consts.ConstsCore;

@ServiceMethodBean(version="1.0")
public class EcsOrdServices extends ServiceBase implements IEcsOrdServices{

	@Resource
	private UnimallOrderQueryManager unimallOrderQueryManager;
	
	@Resource
	private IOrderExtManager orderExtManager;
	
	@Resource
	private IOrderFlowManager ordFlowManager;

	@Resource
	private ICommonManager commonManager;
	
	@Resource
	private IRuleService ruleService;
	
	private final static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Resource
	private IOrderCrawlerManager orderCrawlerManager;
	/**
	 * 通用反射工具类
	 */
	@Override
    public ZteResponse processrule(RuleParams ruleParams) throws ApiBusiException {
    	OrderRuleInf orderRuleServ = SpringContextHolder.getBean("orderRuleServ");
    	List<Rule> rules  = orderRuleServ.getRulesByCond(ruleParams);
        ZteResponse obj = null;
        String s = "";
        logger.info("EcsServices.processrule.rules.size() == " + rules.size());
//        logger.info(ruleParams.getUserSessionId()+"===================================wui");
        for (int i = 0; i < rules.size(); i++) {
            s = "";
            Rule rule = rules.get(i);
            AbstractRuleHander acceptBaseRule = null;
            try {
                try {
                	String ruleBean = rule.getRule_java() + CommonTools.getSourceForm();
                    acceptBaseRule = SpringContextHolder.getBean(ruleBean);
                } catch (NoSuchBeanDefinitionException e) {
                    acceptBaseRule = SpringContextHolder.getBean(rule.getRule_java());
                }
                s = ruleParams.getMethod_name() + "Perform"; //执行抽象类规则
                logger.debug("反射规则类:" + rule.toString() + ",方法:" + s);
                obj = (ZteResponse) MethodUtils.invokeMethod(acceptBaseRule, s, new Object[]{ruleParams.getZteRequest()});

                if ((i + 1) == rules.size()) {
                    return (null == obj) ? null : (ZteResponse) obj;
                }

            } catch (Exception e) {
                e.printStackTrace();
                ZteError entity = CoreThreadLocalHolder.getInstance().getZteCommonData().getZteError();
                if (entity != null && Consts.EXP_BUSS.equals(entity.getType_code()))
                    throw new ApiBusiException(e.getMessage());
                else
                    throw new RuntimeException(e);
            }
        }
        return null;
    }
    
	@Override
	public UnimallOrderQueryResp unimallOrderQuery(UnimallOrderQueryReq req) {
		// TODO Auto-generated method stub
		UnimallOrderQueryResp resp = new UnimallOrderQueryResp();
		Map queryParams = req.getParam();
		try{
			resp=unimallOrderQueryManager.queryUnimallOrder(queryParams);
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		catch(Exception e){
			e.printStackTrace();
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败");
		}
		return resp;
	}

	@Override
	public AttrSyLoadResp attrSyVali(AttrSyLoadReq req)throws ApiBusiException {
		AttrSyLoadResp resp = new AttrSyLoadResp();
		resp.setError_code(ConstsCore.ERROR_SUCC); //缺省为成功
		if(!StringUtils.isEmpty(req.getHander_class())){
			IAttrHandler handler = null;
			try{
				handler = SpringContextHolder.getBean(req.getHander_class());
			}catch(Exception ex){
				
			}
			resp = handler.attrSyingVali(req);
		}
		if(resp ==null){
			resp = new AttrSyLoadResp();
			resp.setError_code(ConstsCore.ERROR_SUCC); //缺省为成功
		}
		return resp;
	}
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单归集属性值标准化", summary = "订单归集属性值标准化")
	public AttrInstLoadResp handler(AttrSwitchParams req) throws ApiBusiException {
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setError_code(ConstsCore.ERROR_SUCC); //缺省为成功
		if(!StringUtils.isEmpty(req.getHander_class())){
			try{
				IAttrHandler handler = SpringContextHolder.getBean(req.getHander_class());
				resp = handler.handler(req);
			}catch(Exception e){
//				e.printStackTrace();
			}
			
		}
		if(resp ==null){
			resp = new AttrInstLoadResp();
			resp.setError_code(ConstsCore.ERROR_SUCC); //缺省为成功
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "根据外部订单号获取order_id", summary = "根据外部订单号获取order_id")
	public GetOrderByOutTidResp getOrdByOutTid(GetOrderByOutTidReq req)
			throws ApiBusiException {
		String out_tid = req.getOut_tid();
		String order_id = orderExtManager.getOrderIdByOutTid(out_tid);
		GetOrderByOutTidResp resp = new GetOrderByOutTidResp();
		resp.setOrder_id(order_id);
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "根据BSS_ORDER_ID订单号获取order_id", summary = "根据BSS_ORDER_ID订单号获取order_id")
	public GetOrderByBssIdResp getOrdByBssId(GetOrderByBssIdReq req)
			throws ApiBusiException {
		String out_tid = req.getBss_id();
		String order_id = orderExtManager.getOrderIdByBssId(out_tid);
		GetOrderByBssIdResp resp = new GetOrderByBssIdResp();
		resp.setOrder_id(order_id);
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "由外部订单号查询配送方式", summary = "由外部订单号查询配送方式")
	public GetShippingTypeByIdResp getShippingTypeById(GetShippingTypeByIdReq req)
			throws ApiBusiException {
		String order_id = req.getOrder_id();
		String inf_id = req.getInf_id();
		String shipping_type = orderExtManager.getShippingTypeById(order_id, inf_id);
		GetShippingTypeByIdResp resp = new GetShippingTypeByIdResp();
		resp.setShipping_type(shipping_type);
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "根据外部订单号获取order_id", summary = "根据外部订单号获取zb_inf_id")
	public GetOrderByInfIdResp getOrdByInfId(GetOrderByInfIdReq req)
			throws ApiBusiException {
		String inf_id = req.getZb_inf_id();
		String order_id = orderExtManager.getOrderIdByInfId(inf_id);
		GetOrderByInfIdResp resp = new GetOrderByInfIdResp();
		resp.setOrder_id(order_id);
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "根据出库单号获取order_id", summary = "根据出库单号获取order_id")
	public GetOrderByVBELNResp getOrdByVBELN(GetOrderByVBELNReq req)
			throws ApiBusiException {
		String vbeln = req.getVbeln();
		String order_id = orderExtManager.getOrderIdByVBELN(vbeln);
		GetOrderByVBELNResp resp = new GetOrderByVBELNResp();
		resp.setOrder_id(order_id);
		return resp;
	}
	
	/**
	 * 调用业务组件执行
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "调用业务组件执行", summary = "调用业务组件执行")
	public BusiCompResponse execBusiComp(BusiCompRequest busiCompRequest) throws ApiBusiException{
		ZteCommonTraceRule commonTraceRule = SpringContextHolder.getBean("zteCommonTraceRule");
		Map params = busiCompRequest.getQueryParams();
		String service_code = (String)params.get("service_code");
		if(Consts.SERVICE_CODE_CO_GUIJI_NEW.equals(service_code)){
			/*IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
			DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
	        List<Map> list = dcPublicCache.getList("IS_NEW_STANDAED_ORDER");*/
	        String sql = "select t.* from es_dc_public_ext t where t.stype='IS_NEW_STANDAED_ORDER'";
	        List<Map> list = this.baseDaoSupport.queryForList(sql);
	        if(list!=null && list.size()>0){
	        	Map map = list.get(0);
	        	String flag = (String) map.get("pkey");
	        	if(!StringUtil.isEmpty(flag) && "yes".equals(flag)){
	        		return commonTraceRule.orderSyNew(busiCompRequest);
	        	}
	        }else{
	        	return commonTraceRule.orderSyNew(busiCompRequest);
	        }
		}else{
			return commonTraceRule.orderSyOld(busiCompRequest);
		}
		return new BusiCompResponse();
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "根据内部订单号获取out_order_id", summary = "根据外部订单号获取zb_inf_id")
	public GetOutOrderByOrderIdResp getOutOrderByOrderId(
			GetOutOrderByOrderIdReq req) throws ApiBusiException {
		String order_id = req.getOrder_id();
		String out_order_id = orderExtManager.getOutOrderIdByInfId(order_id);
		GetOutOrderByOrderIdResp resp = new GetOutOrderByOrderIdResp();
		resp.setOut_order_id(out_order_id);
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "根据条件查询订单信息", summary = "根据条件查询订单信息")
	public OrderInfoResp getOrderInfo(OrderInfoReq orderInfoReq){
		OrderInfoResp orderInfoResp = new OrderInfoResp();
		orderInfoResp.setOrderInfo(orderExtManager.getOrderInfo(orderInfoReq));
		return orderInfoResp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单操作日志", summary = "订单操作日志")
	public InsertOrderHandLogResp insertOrderHandLog(InsertOrderHandLogReq req) {
		// TODO Auto-generated method stub
		OrderHandleLogsReq handLogReq = new OrderHandleLogsReq();
		handLogReq.setComments(req.getComments());
		handLogReq.setCreate_time(req.getCreate_time());
		handLogReq.setFlow_id(req.getFlow_id());
		handLogReq.setFlow_trace_id(req.getFlow_trace_id());
		handLogReq.setHandler_type(req.getHandler_type());
		handLogReq.setOp_id(req.getOp_id());
		handLogReq.setOp_name(req.getOp_name());
		handLogReq.setOrder_id(req.getOrder_id());
		handLogReq.setSource_from(req.getSource_from());
		handLogReq.setTrace_name(req.getTrace_name());
		handLogReq.setType_code(req.getType_code());
		handLogReq.setType_name(req.getType_name());
		ordFlowManager.insertOrderHandLog(handLogReq);
		//异常处理记录
		this.ordFlowManager.updateOrderExceptionLog(req.getOrder_id());
		InsertOrderHandLogResp resp = new InsertOrderHandLogResp();
		resp.setOrder_id(handLogReq.getOrder_id());
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "转手工开户", summary = "转手工开户")
	public OffLineOpenActResp offLineOpenAct(OffLineOpenActReq req) {
		// TODO Auto-generated method stub
		OffLineOpenActResp resp = new OffLineOpenActResp();
		try{
			ordFlowManager.offLineOpenAccount(req.getOrder_id());
		}catch(Exception e){
			e.printStackTrace();
		}
		return resp;
	}
	
	@Override
	public OrderReturnedApplyResp orderReturnedAapply(OrderReturnedApplyReq req){
		OrderReturnedApplyResp resp = new OrderReturnedApplyResp();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(req.getOrder_id());
		//获取方案信息
		Map m = AttrUtils.getInstance().getRefundPlanInfo(orderTree.getOrder_id());
		String plan_mode = m.get("plan_id").toString();
		String rule_mode = m.get("app_rule_id").toString();
		TacheFact fact = new TacheFact();
		fact.setOrder_id(orderTree.getOrder_id());
		RuleExeLogDelReq dreq = new RuleExeLogDelReq();
		dreq.setPlan_id(new String[]{plan_mode});
		dreq.setObj_id(orderTree.getOrder_id());
		ruleService.delRuleExeLog(dreq);
		RuleTreeExeResp rresp = RuleFlowUtil.executeRuleTree(plan_mode, rule_mode, fact,true,true,EcsOrderConsts.DEAL_FROM_PAGE);
		if(rresp!=null && "0".equals(rresp.getError_code())){
			//写日志
			OrderHandleLogsReq rreq = new OrderHandleLogsReq();
			String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
			String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			rreq.setOrder_id(req.getOrder_id());
			rreq.setFlow_id(flow_id);
			rreq.setFlow_trace_id(flowTraceId);
			rreq.setComments(req.getMessage());
			rreq.setHandler_type(Const.ORDER_HANDLER_TYPE_RETURNED);
			rreq.setType_code(EcsOrderConsts.REFUND_STATUS_08);
			//rreq.setOp_id(user.getUserid());
			//rreq.setOp_name(user.getUsername());
			this.ordFlowManager.insertOrderHandLog(rreq);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}else{
			String msg = resp==null?"":resp.getError_msg();
			resp.setError_code("1");
			resp.setError_msg("msg");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "AOP-根据orderId获取ESS工号信息", summary = "AOP-获取ESS工号信息")
	public EmpIdEssIDResp getEssInfoByOrderId(EmpIdEssIDReq req) throws ApiBusiException {
		EmpIdEssIDResp resp = new EmpIdEssIDResp();
		EmpOperInfoVo operInfo = new EmpOperInfoVo();
		operInfo = EssOperatorInfoUtil.getEssInfoByOrderId(req.getOrder_id());
		resp.setError_code("0");
		resp.setOperInfo(operInfo);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "AOP-根据ess_id获取ESS工号信息", summary = "AOP-根据ess_id获取ESS工号信息")
	public EmpIdEssIDResp getEssInfoByEssId(EmpInfoByEssIDReq req)
			throws ApiBusiException {
		EmpIdEssIDResp resp = new EmpIdEssIDResp();
		EmpOperInfoVo operInfo = new EmpOperInfoVo();
		operInfo = EssInfoUtil.getEssInfoByEssId(req.getOperator_id(),req.getOrder_id(),req.getOperIdType());
		resp.setError_code("0");
		resp.setOperInfo(operInfo);
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "AOP-根据订单来源获取ESS工号信息", summary = "AOP-根据订单来源获取ESS工号信息")
	public EmpIdEssIDResp getEssinfoByOrderFrom(EmpInfoByOrderFrom req) throws ApiBusiException {
		EmpIdEssIDResp resp = new EmpIdEssIDResp();
		EmpOperInfoVo operInfo = new EmpOperInfoVo();
		operInfo = EssOperatorInfoUtil.getEssInfoByOrderFrom(req.getOrder_id());
		resp.setError_code("0");
		resp.setOperInfo(operInfo);
		return resp;
	}
	
	@Override
	public HasAutoExceptionResp hasAutoException(HasAutoExceptionReq req){
		HasAutoExceptionResp resp = new HasAutoExceptionResp();
		List<OrderExceptionLogsReq> logsList = ordFlowManager.queryExceptionLogsList(req.getOrder_id());
		if(logsList.size()>0){
			resp.setHasAutoException("1");
		}else{
			resp.setHasAutoException("0");
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "新商城同步工号信息", summary = "新商城同步工号信息")
	public MallOpIDSynInfoResp wygOperatorIDSyn(MallOpIDSynInfoReq req) {
		MallOpIDSynInfoResp resp = new MallOpIDSynInfoResp();
		try{
			resp = commonManager.wygOperatorIDSyn(req);
		}catch(Exception e){
			e.printStackTrace();
			resp.setResp_code("1");
			resp.setResp_msg("工号信息同步失败");
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "根据渠道编码获取货主编码", summary = "根据渠道编码获取货主编码")
	public CompCodeResp getCompCode(CompCodeReq req) {
		CompCodeResp resp = new CompCodeResp();
		try{
			String channel_id = req.getChannel_id();
			String comp_code = ChannelidProductcompRefUtil.getCompCode(channel_id);
			resp.setComp_code(comp_code);
		}catch(Exception e){
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "沃云购 受理单信息查询", summary = "沃云购 受理单信息查询")
	public WYGAcceptanceQueryResp acceptanceQuery(WYGAcceptanceQueryReq req) {
		WYGAcceptanceQueryResp resp = new WYGAcceptanceQueryResp();
		try{
			String out_tid = req.getOut_order_id();
			String order_id = orderExtManager.getOrderIdByOutTid(out_tid);
			if(StringUtils.isEmpty(order_id)){
				order_id = orderExtManager.getHisOrderIdByOutTid(out_tid);
			}
			if(!StringUtils.isEmpty(order_id)){
				resp = commonManager.acceptanceQuery(order_id);
			}else{
				resp.setResp_code(EcsOrderConsts.WYG_INF_FAIL_CODE);
				resp.setResp_msg("不存在此订单");
			}
		}catch(Exception e){
			logger.info(req.getOut_order_id()+"受理单模板获取报错:");
			e.printStackTrace();
			resp.setResp_code(EcsOrderConsts.WYG_INF_FAIL_CODE);
			resp.setResp_msg("获取受理单信息出错");
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取华盛商品信息", summary = "获取华盛商品信息")
	public HuaShengGoodsResp getHuaShengGoods(HuaShengGoodsReq req) {
		HuaShengGoodsResp resp = new HuaShengGoodsResp();
		try{
			resp = orderExtManager.getHuaShengGoods(req);
		}catch(Exception e){
			logger.info("获取华盛商品信息失败,物料号["+req.getMatnr()+"]");
			e.printStackTrace();
		}
		return resp;
	}
	
	@Override
	public ZbAuditOrderQueryResp queryZbAuditOrders(ZbAuditOrderQueryReq req)
			throws ApiBusiException {
		List orders = this.orderExtManager.queryZbAuditOrders();
		ZbAuditOrderQueryResp resp = new ZbAuditOrderQueryResp();
		if(orders!=null && orders.size()>0){
			resp.setError_code("0");
			resp.setError_msg("成功");
			resp.setOrders(orders);
		}
		else{
			resp.setError_code("-1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public ZbAuditStatusUpdateResp updateZbAuditStatus(ZbAuditStatusUpdateReq req) throws ApiBusiException {
		this.orderExtManager.updateZbAuditStatus(req);
		
		ZbAuditStatusUpdateResp resp = new ZbAuditStatusUpdateResp();
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}
	@Override
	public ZbAuditSuccOrderQueryResp queryZbAuditSuccOrders(
			ZbAuditSuccOrderQueryReq req) throws ApiBusiException {
		List orders = this.orderExtManager.queryZbAuditSuccOrders();
		ZbAuditSuccOrderQueryResp resp = new ZbAuditSuccOrderQueryResp();
		if(orders!=null && orders.size()>0){
			resp.setError_code("0");
			resp.setError_msg("成功");
			resp.setOrders(orders);
		}
		else{
			resp.setError_code("-1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}
	@Override
	public ZbCrawlerStatusUpdateResp updateZbCrawlerStatus(
			ZbCrawlerStatusUpdateReq req) throws ApiBusiException {
		this.orderExtManager.updateZbCrawlerStatus(req);
		
		ZbCrawlerStatusUpdateResp resp = new ZbCrawlerStatusUpdateResp();
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}
	@Override
	public CrawlerProcCondResp getProcCond(CrawlerProcCondReq cProcCondReq) {
		CrawlerProcCondResp condResp = new CrawlerProcCondResp();
		
		String ip = cProcCondReq.getIp();
		String port = cProcCondReq.getPort();
		if(StringUtils.isEmpty(port) || StringUtils.isEmpty(ip)){
			condResp.setError_code("1");
			condResp.setError_msg("接收到的IP或端口为空！");
		}else{
			condResp = orderCrawlerManager.getProcCond(ip,port);
		}
		
		return condResp;
	}
	@Override
	/**
	 * 爬虫
	 * @param req
	 * @return
	 */
	public PCAutoOrderICCIDResp setPCAutoOrderICCID(PCAutoOrderICCIDReq req) {
		// TODO Auto-generated method stub
		PCAutoOrderICCIDResp resp= new PCAutoOrderICCIDResp();
		String ICCID = req.getIccid();
		String orderId= req.getOredrId();
		String machineNo = req.getMachineNo();
		String queueCode = req.getQueueCode();
		if(StringUtils.isEmpty(orderId) || StringUtils.isEmpty(ICCID)){
			logger.info("PC批量写卡开户回填ICCID时order_id["+orderId+"]为空或iccid["+ICCID+"]为空.");
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("订单号或者ICCID为空");
			return resp;
		}
		
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(orderId);
		OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
		//保存ICCID
        CommonDataFactory.getInstance().updateAttrFieldValue(orderId, new String[]{AttrConsts.ICCID}, new String[]{ICCID});
      //爬虫自动生产模式 ，处于开户环节
        if(StringUtils.equals(orderExt.getOrder_model(), ZjEcsOrderConsts.ORDER_MODEL_08) 
				&& StringUtils.equals(orderExt.getFlow_trace_id(), EcsOrderConsts.DIC_ORDER_NODE_D)
				&& (StringUtils.equals(orderExt.getIs_aop(), EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP)) ){
	        //保存写卡机编码 start
	        List<OrderItemProdBusiRequest> prods = orderTree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests();
	        for(OrderItemProdBusiRequest prod:prods){
				OrderItemProdExtBusiRequest prodExt = prod.getOrderItemProdExtBusiRequest();
				prodExt.setReadId(machineNo);
				prodExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				prodExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				prodExt.store();
			}
	        String is_write_card = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.IS_WRITE_CARD);
			if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_write_card)){		
				//已接受写卡机编码
				CommonDataFactory.getInstance().updateAttrFieldValue(orderId, new String[]{AttrConsts.READID},new String[]{machineNo});
			}
			
			String ruleid  = ZjEcsOrderConsts.ORDER_MODEL_08_ICCID_RULE_ID_AOP;//爬虫自动生成模式对应的获取自动写卡ICCID
			
			//开户
			RuleTreeExeReq reqNext = new RuleTreeExeReq();
			reqNext.setRule_id(ruleid);
			TacheFact factNext = new TacheFact();
			factNext.setOrder_id(orderId);
			reqNext.setFact(factNext);
			reqNext.setCheckAllRelyOnRule(false);
			reqNext.setCheckCurrRelyOnRule(false);
			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			RuleTreeExeResp rsp = client.execute(reqNext, RuleTreeExeResp.class);
			BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
			
			if(!ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())){
				logger.info("订单["+orderId+"]执行开户环节获取自动写卡机ICCID规则失败");
				//标记开户异常
				String sql1= SF.ecsordSql("UPDATE_ORDER_OPEN_ACCOUNT_STATUS");
				baseDaoSupport.execute(sql1, ZjEcsOrderConsts.QUEUE_ORDER_OPEN_3,orderId);
				
				OrderQueueCardActionLogReq logReq = new OrderQueueCardActionLogReq();
				logReq.setAction_code(EcsOrderConsts.QUEUE_ACTION_01);
				logReq.setAction_desc("队列订单开户异常"+busiResp.getError_msg());
				logReq.setOrder_id(orderId);
				logReq.setQueue_code(queueCode);
				logReq.setIccid(ICCID);
				logReq.setMachine_no(machineNo);
				PCWriteCardTools.saveQueueCardActionLog(logReq);
				
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("订单执行开户操作异常");
			}else{
			
				logger.info("orderorderorder["+orderId+"],error_code["+busiResp.getError_code()+"],error_msg["+busiResp.getError_msg()+"]");
				//开户完成
				String sql1= SF.ecsordSql("UPDATE_ORDER_OPEN_ACCOUNT_STATUS");
				baseDaoSupport.execute(sql1, ZjEcsOrderConsts.QUEUE_ORDER_OPEN_2,orderId);

				OrderQueueCardActionLogReq logReq = new OrderQueueCardActionLogReq();
				logReq.setAction_code(EcsOrderConsts.QUEUE_ACTION_02);
				logReq.setAction_desc("队列订单开户完成");
				logReq.setOrder_id(orderId);
				logReq.setQueue_code(queueCode);
				logReq.setIccid(ICCID);
				logReq.setMachine_no(machineNo);
				PCWriteCardTools.saveQueueCardActionLog(logReq);
				
				resp.setError_code(ConstsCore.ERROR_SUCC);
				resp.setError_msg("队列订单开户完成");
			}
        }else{
        	logger.info("订单["+orderId+"]数据不正确["+orderExt.getOrder_model()+","+orderExt.getFlow_trace_id()+","+orderExt.getIs_aop()+"]");
        	resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("订单数据不对，请检查订单环节，订单模型是否有误");
        }
       
		return resp;
	}
	@Override
	public ZteResponse updateCrawlerHandleNum(CrawlerUpdateHandleNumReq req) {
		// TODO Auto-generated method stub
		ZteResponse resp = new ZteResponse();
		String sql = "update es_crawler_proc_cond c set COND_VALUE = ? WHERE c.COND_CODE = 'HANDLE_NUM' and c.PROC_ID = (select p.proc_id from es_crawler_proc p where p.ip = ? and p.port = ? and p.thread_name = ?)";
		List<String> params = new ArrayList<String>();
		params.add(String.valueOf(req.getHandleNum()));
		params.add(req.getIp());
		params.add(req.getPort());
		params.add(req.getThreadName());
		baseDaoSupport.execute(sql, params.toArray());
		resp.setError_code("0");
		return resp;
	}
}
