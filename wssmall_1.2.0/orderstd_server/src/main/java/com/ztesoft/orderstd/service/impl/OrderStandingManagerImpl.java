package com.ztesoft.orderstd.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.rocketmq.common.ThreadFactoryImpl;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.DefaultZteRopClient;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.BaseTools;
import com.ztesoft.common.util.SequenceTools;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.XmlUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.consts.EccConsts;
import com.ztesoft.net.eop.sdk.context.MqEnvGroupConfigSetting;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.BusiUtils;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.model.TempInst;
import com.ztesoft.net.outter.inf.iservice.IOuterService;
import com.ztesoft.net.outter.inf.iservice.IOutterECSTmplManager;
import com.ztesoft.net.outter.inf.model.OuterError;
import com.ztesoft.net.outter.inf.service.SDBUtils;
import com.ztesoft.net.server.BroadBandCtnUtil;
import com.ztesoft.net.server.CenterMallOrderUtil;
import com.ztesoft.net.server.CodePurchaseMallOrderUtil;
import com.ztesoft.net.server.FixBusiOrderUtil;
import com.ztesoft.net.server.GroupOrderUtil;
import com.ztesoft.net.server.HSMallOrderUtil;
import com.ztesoft.net.server.InternetBusiOrderUtil;
import com.ztesoft.net.server.InternetGroupOrderUtil;
import com.ztesoft.net.server.MobileBusiCtnUtil;
import com.ztesoft.net.server.ModelStandardOrderUtil;
import com.ztesoft.net.server.NewMallOrderUtil;
import com.ztesoft.net.server.TaobaoFenxiaoOrderUtil;
import com.ztesoft.net.server.TaobaoOrderUtil;
import com.ztesoft.net.server.TaobaoTianjiOrderUtil;
import com.ztesoft.net.server.ZJLocalMallOrderUtil;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.orderstd.service.IOrderStandingManager;

import commons.CommonNTools;
import commons.CommonTools;
import consts.ConstsCore;
import params.ZteBusiRequest;
import params.ZteRequest;
import params.ZteResponse;
import params.orderqueue.req.AsynExecuteMsgWriteMqReq;
import params.req.BroadBandCtnStandardReq;
import params.orderqueue.resp.OrderCollectionResp;
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
import params.req.OrderExpWriteForBusReq;
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
import params.resp.OrderExpWriteResp;
import params.resp.OrderPreCreateCtnStandardResp;
import params.resp.TaoBaoFenxiaoOrderStandardResp;
import params.resp.TaoBaoMallOrderStandardResp;
import params.resp.TaoBaoTianjiOrderStandardResp;
import params.resp.TemplatesOrderStandardResp;
import params.resp.ZJLocalMallOrderStandardResp;
import params.resp.vo.DATA;
import rule.IZteBusiRequestHander;
import zte.net.common.annontion.context.request.DefaultActionRequestDefine;
import zte.net.common.annontion.context.request.RequestBeanDefinition;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.ChannelConvertQrySubReq;
import zte.net.ecsord.params.ecaop.resp.ChannelConvertQrySubResp;
import zte.net.ecsord.params.order.req.StartOrderPlanReq;
import zte.net.ecsord.params.order.req.StartWorkflowReq;
import zte.net.ecsord.params.order.req.WorkflowMatchReq;
import zte.net.ecsord.params.order.resp.StartOrderPlanResp;
import zte.net.ecsord.params.order.resp.StartWorkflowRsp;
import zte.net.ecsord.params.order.resp.WorkflowMatchRsp;
import zte.net.iservice.IOrderQueueBaseManager;
import zte.net.iservice.consts.InfoConsts;
import zte.net.ord.params.busi.req.OrderQueueBusiRequest;
import zte.params.order.req.OrderStandardPushReq;
import zte.params.order.resp.OrderAddResp;
import zte.params.order.resp.OrderStandardPushResp;
import zte.params.req.CheckReq;
import zte.params.resp.CheckResp;
import zte.params.template.req.GetAttrDefTableReq;
import zte.params.template.req.TemplateAccessConvertReq;
import zte.params.template.resp.AttrDefTableVO;
import zte.params.template.resp.GetAttrDefTableResp;
import zte.params.template.resp.TemplateAccessConvertResp;

public class OrderStandingManagerImpl extends BaseSupport implements IOrderStandingManager {
	@Resource
	private TaobaoOrderUtil taobaoOrderUtil;
	@Resource
	private TaobaoFenxiaoOrderUtil taobaoFenxiaoOrderUtil;
	@Resource
	private TaobaoTianjiOrderUtil taobaoTianjiOrderUtil;

	@Resource
	private IOuterService stdOuterService;

	private IOrderQueueBaseManager orderQueueBaseManager;
	private static ExecutorService threadSingleExecutor = Executors
			.newSingleThreadExecutor(new ThreadFactoryImpl("ThreadPushTestMemExector"));
	private static ExecutorService threadPlanExeExecutor = null;
	private static boolean initFixThreadCount = false;
	@Resource
	private ICacheUtil cacheUtil;

	@Override
	public TempInst getTempInstByGoodsIdAndSource(String goods_id, String source_from) {
		String sql = SF.orderSql("SERVICE_TEMP_INST_SELECT") + " and goods_id=? and source_from=? and temp_type=?";
		List<TempInst> tempsLists = this.baseDaoSupport.queryForList(sql, TempInst.class, goods_id, source_from,
				"goods");
		TempInst inst = null;
		if (!ListUtil.isEmpty(tempsLists))
			inst = tempsLists.get(0);
		if (inst == null) {
			sql = "SELECT a.* FROM es_temp_inst a ,es_goods b WHERE  b.goods_id=? and a.source_from=? and a.goods_id =b.type_id and a.temp_type=?";
			tempsLists = this.baseDaoSupport.queryForList(sql, TempInst.class, goods_id, source_from, "goodstype");
			if (!ListUtil.isEmpty(tempsLists))
				inst = tempsLists.get(0);
		}
		return inst;
	}

	private String getReqMsgPersistentType() {
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String reqMsgPersistentType = cacheUtil.getConfigInfo(Consts.REQ_MSG_PERSISTENT_TYPE);
		if (StringUtil.isEmpty(reqMsgPersistentType)) {
			reqMsgPersistentType = Consts.REQ_MSG_PERSISTENT_TYPE_DB;
		}
		return reqMsgPersistentType;
	}

	private void insertSysParams(List<String> orderIdList) {
		try {
			for (String order_id : orderIdList) {
				String source_from = ManagerUtils.getSourceFrom();
				OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
				String out_tid = ordertree.getOrderExtBusiRequest().getOut_tid();
				String ship_name = ordertree.getOrderDeliveryBusiRequests().get(0).getShip_name();
				String remark = ordertree.getOrderBusiRequest().getRemark();
				String order_channel = ordertree.getOrderExtBusiRequest().getOrder_channel();
				String zb_inf_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ZB_INF_ID);
				String env_code = "";
				if (Consts.TAOBAO_CODE_ID.equals(order_channel)) {// 淘宝单需要单独处理测试单和灰度单，淘宝单只在1.0中存在
					if (Consts.ZTE_CESHI.equals(ship_name) || Consts.ZTE_CESHI.equals(remark)) {
						env_code = Consts.ZTE_CESH_ENV_TYPE_SERVER;
					} else if (Consts.ZTE_GRAY.equals(ship_name) || Consts.ZTE_GRAY.equals(remark)) {
						String sql = "select env_code from es_abgray_hostenv where env_type='"
								+ Consts.ZTE_ENV_TYPE_SERVER + "'  and env_status='00X' and rownum=1";
						env_code = baseDaoSupport.queryForString(sql);
					}
				}
				if (StringUtil.isEmpty(env_code)) {
					env_code = (String) com.ztesoft.common.util.BeanUtils.getCurrHostEnv().get("env_code");
				}
				// 订单关联环境信息<<begin
				HashMap p = new HashMap();
				p.put("source_from", source_from);
				p.put("order_id", order_id);
				p.put("op_code", "ordstd");
				p.put("out_tid", out_tid);
				p.put("ship_name", ship_name);
				p.put("remark", remark);
				p.put("order_channel", order_channel);
				p.put("zb_inf_id", zb_inf_id);
				p.put("env_code", env_code);
				logger.info("OrderStandardizing:新订单标准化写入主机环境信息：外部单号" + out_tid + ",订单信息" + order_id + ",订单渠道:"
						+ order_channel + "," + "收货人：" + ship_name + ",客户留言:" + remark + "总部单号：" + zb_inf_id);
				long start = System.currentTimeMillis();
				com.ztesoft.common.util.BeanUtils.ordBindEvn(p);
				com.ztesoft.common.util.BeanUtils.ordBindEvnLog(p);
				long end = System.currentTimeMillis();
				logger.info("新商城写入主机环境：" + (end - start));
				// 订单关联环境信息<<endstart
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public NewMallOrderStandardResp newMallOrderStandard(NewMallOrderStandardReq req) {
		NewMallOrderStandardResp resp = new NewMallOrderStandardResp();
		String co_id = req.getBase_co_id();
		// 1.调用校验系统进行校验
		CheckResp checkResp = this.validateOrder(co_id, req.getFormat());// 调用校验系统进行校验
		// 校验失败直接返回 校验系统已写入异常单,这里不用重复写入异常单
		if (ConstsCore.ERROR_FAIL.equals(checkResp.getError_code())) {
			resp.setError_msg("标准化前置校验失败");
			resp.setError_stack_msg("队列id:" + co_id + checkResp.getError_msg());
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		// 2.订单收单本地化校验--邵初
		long val_start = System.currentTimeMillis();
		try {
			resp = NewMallOrderUtil.orderStandardValidate(req);
			// 校验失败调用异常单方案
			if (ConstsCore.ERROR_FAIL.equals(resp.getError_code())) {
				resp.setError_stack_msg("队列id:" + co_id + resp.getError_msg());
				this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
				return resp;
			}
		} catch (Exception e) {
			String error_msg = CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("订单收单本地化校验失败");
			resp.setError_stack_msg("队列id:" + co_id + error_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			return resp;
		}
		long val_end = System.currentTimeMillis();
		logger.info("本地校验耗时：" + (val_end - val_start));
		// 3.进行标准化
		OrderStandardPushResp orderStandardPushResp = new OrderStandardPushResp();
		try {
			orderStandardPushResp = saveOrder(resp.getOut_order_List());
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("标准化成功");
			List orderIdList = this.getOrderIdList(orderStandardPushResp.getOrderAddRespList());
			// 4.1标准化成功调用正常单标准化方案
			this.exeOrd(orderIdList);// 标准化成功执行正常单方案
			// 写入主机环境日志
			this.insertSysParams(orderIdList);
		} catch (Exception e) {
			e.printStackTrace();
			String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			// add by xiang.yangbo begin 捕捉原本的报错信息
			if (!ConstsCore.ERROR_FAIL.equals(resp.getError_code())) {
				resp.setError_msg("标准化失败");
			}
			// add by xiang.yangbo end
			resp.setError_stack_msg(error_stack_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			// 4.2 标准化失败调用异常单方案
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			// 4.3 标准化失败写入outError表
			if (resp.getOut_order_List().size() > 0) {
				insertOuterError(resp.getOut_order_List());
			}
			return resp;
		}
		resp = (NewMallOrderStandardResp) this.exeOrdRuleLog(resp, req.getOut_id());
		return resp;
	}

	public void insertOuterError(List<Outer> out_order_List) {
		try {
			IOutterECSTmplManager outterECSTmplManager = SpringContextHolder.getBean("outterStdTmplManager");
			String out_tid = out_order_List.get(0).getOut_tid();
			String plat_type = out_order_List.get(0).getPlat_type();
			OuterError ng = new OuterError(null, "", "", null, plat_type, out_tid, "sysdate", "goodserror");
			ng.setDeal_flag("-1");
			outterECSTmplManager.insertOuterError(ng);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public CenterMallOrderStandardResp centerMallOrderStandard(CenterMallOrderStandardReq req) {
		CenterMallOrderStandardResp resp = new CenterMallOrderStandardResp();
		String co_id = req.getBase_co_id();
		CheckResp checkResp = this.validateOrder(co_id, req.getFormat());// 调用校验系统进行校验
		if (ConstsCore.ERROR_FAIL.equals(checkResp.getError_code())) {
			resp.setError_msg("标准化前置校验失败");
			resp.setError_stack_msg("队列id:" + co_id + checkResp.getError_msg());
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		// 2.订单收单本地化校验--邵初
		try {
			resp = CenterMallOrderUtil.orderStandardValidate(req);
			// 校验失败调用异常单方案
			if (ConstsCore.ERROR_FAIL.equals(resp.getError_code())) {
				this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
				resp.setError_stack_msg("队列id:" + co_id + resp.getError_msg());
				return resp;
			}
		} catch (Exception e) {
			String error_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("订单收单本地化校验失败");
			resp.setError_stack_msg(error_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			return resp;
		}
		// 3.进行标准化
		OrderStandardPushResp orderStandardPushResp = new OrderStandardPushResp();
		try {
			orderStandardPushResp = saveOrder(resp.getOut_order_List());
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("标准化成功");
			List orderIdList = this.getOrderIdList(orderStandardPushResp.getOrderAddRespList());
			// 4.1标准化成功调用正常单标准化方案
			this.exeOrd(orderIdList);// 标准化成功执行正常单方案
			// 写入主机环境日志
			this.insertSysParams(orderIdList);
		} catch (Exception e) {
			String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("标准化失败");
			resp.setError_stack_msg(error_stack_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			// 4.2 标准化失败调用异常单方案
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			// 4.3总部标准化失败写入es_fail_order_msg表
			try {
				String out_tid = resp.getOut_order_List().get(0).getOut_tid();
				String req_content = this.getReqContent(null, req.getBase_co_id(), req.isIs_exception());
				insertFailOrd("ZB", out_tid, req_content, "队列id:" + co_id + resp.getError_msg());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return resp;
		}
		return resp;
	}

	@Override
	public CodePurchaseMallOrderStandardResp codePurchaseMallOrderStandard(CodePurchaseMallOrderStandardReq req) {

		CodePurchaseMallOrderStandardResp resp = new CodePurchaseMallOrderStandardResp();
		String co_id = req.getBase_co_id();
		CheckResp checkResp = this.validateOrder(co_id, req.getFormat());// 调用校验系统进行校验
		if (ConstsCore.ERROR_FAIL.equals(checkResp.getError_code())) {
			resp.setError_msg("标准化前置校验失败");
			resp.setError_stack_msg("队列id:" + co_id + checkResp.getError_msg());
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		// 2.订单收单本地化校验--邵初
		try {
			resp = CodePurchaseMallOrderUtil.orderStandardValidate(req);
			// 校验失败调用异常单方案
			if (ConstsCore.ERROR_FAIL.equals(resp.getError_code())) {
				this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
				resp.setError_stack_msg("队列id:" + co_id + resp.getError_msg());
				return resp;
			}
		} catch (Exception e) {
			String error_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("订单收单本地化校验失败");
			resp.setError_stack_msg(error_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			e.printStackTrace();
			return resp;
		}
		// 3.进行标准化
		OrderStandardPushResp orderStandardPushResp = new OrderStandardPushResp();
		try {
			orderStandardPushResp = saveOrder(resp.getOut_order_List());
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("标准化成功");
			List orderIdList = this.getOrderIdList(orderStandardPushResp.getOrderAddRespList());
			// 4.1标准化成功调用正常单标准化方案
			this.exeOrd(orderIdList);// 标准化成功执行正常单方案
			// 写入主机环境日志
			this.insertSysParams(orderIdList);
		} catch (Exception e) {
			String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("标准化失败");
			resp.setError_stack_msg(error_stack_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			// 4.2 标准化失败调用异常单方案
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			// 4.3总部标准化失败写入es_fail_order_msg表
			try {
				String out_tid = resp.getOut_order_List().get(0).getOut_tid();
				String req_content = this.getReqContent(null, req.getBase_co_id(), req.isIs_exception());
				insertFailOrd("ZB", out_tid, req_content, "队列id:" + co_id + resp.getError_msg());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return resp;
		}
		resp = (CodePurchaseMallOrderStandardResp) this.exeOrdRuleLog(resp, req.getOut_id());
		return resp;
	}

	@Override
	public GroupOrderStandardResp groupOrderStandard(GroupOrderStandardReq req) {

		GroupOrderStandardResp resp = new GroupOrderStandardResp();
		String co_id = req.getBase_co_id();
		// 1.前置校验开关
		resp = (GroupOrderStandardResp) validatePreCheck(req, resp);

		// 2.订单收单本地化校验--邵初
		try {
			resp = GroupOrderUtil.orderStandardValidate(req);
			// 校验失败调用异常单方案
			if (ConstsCore.ERROR_FAIL.equals(resp.getError_code())) {
				this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
				resp.setError_stack_msg("队列id:" + co_id + resp.getError_msg());
				return resp;
			}
		} catch (Exception e) {
			String error_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("订单收单本地化校验失败");
			resp.setError_stack_msg(error_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			e.printStackTrace();
			return resp;
		}
		// 3.进行标准化
		OrderStandardPushResp orderStandardPushResp = new OrderStandardPushResp();
		try {
			orderStandardPushResp = saveOrder(resp.getOut_order_List());
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("标准化成功");
			List orderIdList = this.getOrderIdList(orderStandardPushResp.getOrderAddRespList());
			// 4.1标准化成功调用正常单标准化方案
			this.exeOrd(orderIdList);// 标准化成功执行正常单方案
			// 写入主机环境日志
			this.insertSysParams(orderIdList);
		} catch (Exception e) {
			String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("标准化失败");
			resp.setError_stack_msg(error_stack_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			// 4.2 标准化失败调用异常单方案
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			// 4.3总部标准化失败写入es_fail_order_msg表
			try {
				String out_tid = resp.getOut_order_List().get(0).getOut_tid();
				String req_content = this.getReqContent(null, req.getBase_co_id(), req.isIs_exception());
				insertFailOrd("ZB", out_tid, req_content, "队列id:" + co_id + resp.getError_msg());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return resp;
		}
		return resp;
	}

	public static void insertFailOrd(String source_system, String out_tid, String inJson, String checkResult) {
		SDBUtils dbUtils = SpringContextHolder.getBean("sdbUtils");
		Map fieldsMap = new HashMap();
		fieldsMap.put("out_tid", out_tid);
		fieldsMap.put("source_from", ManagerUtils.getSourceFrom());
		fieldsMap.put("source_system", source_system);
		fieldsMap.put("dealnum", "1");
		fieldsMap.put("failure_desc", checkResult);
		fieldsMap.put("jsonclob", inJson);
		dbUtils.insertOrderData("es_fail_order_msg", fieldsMap);
	}

	@Override
	public TaoBaoMallOrderStandardResp taoBaoMallOrderStandard(TaoBaoMallOrderStandardReq req) {
		TaoBaoMallOrderStandardResp resp = new TaoBaoMallOrderStandardResp();
		String format = req.getFormat();
		String order_id = req.getBase_order_id();
		String co_id = req.getBase_co_id();
		// 1.校验系统校验
		CheckResp checkResp = this.validateOrder(co_id, format);// 调用校验系统进行校验
		if (ConstsCore.ERROR_FAIL.equals(checkResp.getError_code())) {
			resp.setError_msg("队列id:" + co_id + "标准化前置校验失败");
			resp.setError_stack_msg("队列id:" + co_id + checkResp.getError_msg());
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		// 2.同步淘宝订单
		try {
			if (taobaoOrderUtil == null) {
				taobaoOrderUtil = SpringContextHolder.getBean("taobaoOrderUtil");
			}
			resp = taobaoOrderUtil.getOrderContent(req);
			// 校验失败调用异常单方案
			if (ConstsCore.ERROR_FAIL.equals(resp.getError_code())) {
				this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
				resp.setError_stack_msg("队列id:" + co_id + resp.getError_msg());
				return resp;
			}
		} catch (Exception e) {
			String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("队列id:" + co_id + "淘宝同步订单失败");
			resp.setError_stack_msg(error_stack_msg);
			return resp;
		}
		// 3.进行标准化
		OrderStandardPushResp orderStandardPushResp = new OrderStandardPushResp();
		try {
			orderStandardPushResp = saveOrder(resp.getOut_order_List());
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("标准化成功");
			List orderIdList = this.getOrderIdList(orderStandardPushResp.getOrderAddRespList());
			// 4.1标准化成功调用正常单标准化方案
			this.exeOrd(orderIdList);// 标准化成功执行正常单方案
			// 写入主机环境日志
			this.insertSysParams(orderIdList);
		} catch (Exception e) {
			String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("标准化失败");
			resp.setError_stack_msg(error_stack_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			// 4.2 标准化失败调用异常单方案
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			// 4.3 标准化失败写入outError表
			if (resp.getOut_order_List().size() > 0) {
				insertOuterError(resp.getOut_order_List());
			}
			return resp;
		}
		return resp;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public TaoBaoFenxiaoOrderStandardResp taoBaoFenxiaoOrderStandard(TaoBaoFenxiaoOrderStandardReq req) {
		TaoBaoFenxiaoOrderStandardResp resp = new TaoBaoFenxiaoOrderStandardResp();

		String format = req.getFormat();
		String order_id = req.getBase_order_id();
		String co_id = req.getBase_co_id();
		// 1.校验系统校验
		CheckResp checkResp = this.validateOrder(co_id, format);// 调用校验系统进行校验
		if (ConstsCore.ERROR_FAIL.equals(checkResp.getError_code())) {
			resp.setError_msg("队列id:" + co_id + "标准化前置校验失败");
			resp.setError_stack_msg("队列id:" + co_id + checkResp.getError_msg());
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		// 2.同步淘宝订单
		try {
			if (taobaoOrderUtil == null) {
				taobaoOrderUtil = SpringContextHolder.getBean("taobaoOrderUtil");
			}
			resp = taobaoFenxiaoOrderUtil.getOrderContent(req);
			// 校验失败调用异常单方案
			if (ConstsCore.ERROR_FAIL.equals(resp.getError_code())) {
				this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
				resp.setError_stack_msg("队列id:" + co_id + resp.getError_msg());
				return resp;
			}
		} catch (Exception e) {
			String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("队列id:" + co_id + "淘宝同步订单失败");
			resp.setError_stack_msg(error_stack_msg);
			return resp;
		}
		// 3.进行标准化
		OrderStandardPushResp orderStandardPushResp = new OrderStandardPushResp();
		try {
			orderStandardPushResp = saveOrder(resp.getOut_order_List());
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("标准化成功");
			List orderIdList = this.getOrderIdList(orderStandardPushResp.getOrderAddRespList());
			// 4.1标准化成功调用正常单标准化方案
			this.exeOrd(orderIdList);// 标准化成功执行正常单方案
			// 写入主机环境日志
			this.insertSysParams(orderIdList);
		} catch (Exception e) {
			String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("标准化失败");
			resp.setError_stack_msg(error_stack_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			// 4.2 标准化失败调用异常单方案
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			// 4.3 标准化失败写入outError表
			if (resp.getOut_order_List().size() > 0) {
				insertOuterError(resp.getOut_order_List());
			}
			return resp;
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public TemplatesOrderStandardResp templateOrdSave(TemplatesOrderStandardReq req) {
		// 构建返回对象
		TemplatesOrderStandardResp resp = new TemplatesOrderStandardResp();
		resp.setError_code("0");

		final String orderId = req.getBase_order_id();
		final String coId = req.getBase_co_id();
		// 1.获取请求报文并转为Map格式
		long start1 = System.currentTimeMillis();
		Map<String, Object> reqMsgStrMap = getReqMsgByCoId(coId, req.isIs_exception());
		long end1 = System.currentTimeMillis();
		logger.info("获取报文耗时=======" + (end1 - start1));
		// 2.调用核心版本模版转换,核心版本获取属性处理器配置
		long start2 = System.currentTimeMillis();
		Map<String, Object> remoteReturnMap = templateAccessConvertAndGetAttrDefTable(reqMsgStrMap, orderId, req);
		long end2 = System.currentTimeMillis();
		logger.info("模板转换耗时=======" + (end2 - start2));
		// 3.插入OrderTrees
		long start3 = System.currentTimeMillis();
		insertOrderTree(orderId);
		long end3 = System.currentTimeMillis();
		logger.info("插入订单树耗时=======" + (end3 - start3));
		// 4.数据填充,走属性处理逻辑
		Map<String, List<Map<String, String>>> allObjMap = (Map<String, List<Map<String, String>>>) remoteReturnMap
				.get("allObjMap");
		Map innerMap = (Map) reqMsgStrMap.get("Request");

		long start4 = System.currentTimeMillis();
		Map returnMap = ModelStandardOrderUtil.fillOrderInfo(innerMap, allObjMap, orderId);
		long end4 = System.currentTimeMillis();
		logger.info("填充数据耗时=======" + (end4 - start4));

		long start5 = System.currentTimeMillis();
		// 5.将map转换为对象
		allObjMap = (Map<String, List<Map<String, String>>>) returnMap.get("allObjMap");
		Map<String, Object> zteBusiRequestMap = new HashMap<String, Object>();
		map2ZteBusiReques(allObjMap, zteBusiRequestMap, orderId);
		long end5 = System.currentTimeMillis();
		logger.info("转换对象耗时=======" + (end5 - start5));

		long start6 = System.currentTimeMillis();
		// 5.对象入库
		storeBusiMap(zteBusiRequestMap, orderId);
		long end6 = System.currentTimeMillis();
		logger.info("转换对象耗时=======" + (end6 - start6));
		return resp;
	}

	@Override
	public TemplatesOrderStandardResp templatesOrderStandard(TemplatesOrderStandardReq req) {
		long start = System.currentTimeMillis();
		TemplatesOrderStandardResp resp = new TemplatesOrderStandardResp();
		String format = req.getFormat();
		String co_id = req.getBase_co_id();
		// 1.校验系统进行校验
		CheckResp checkResp = this.validateOrder(co_id, format);// 调用校验系统进行校验
		if (ConstsCore.ERROR_FAIL.equals(checkResp.getError_code())) {
			resp.setError_msg("队列id:" + co_id + "标准化前置校验失败");
			resp.setError_stack_msg("队列id:" + co_id + checkResp.getError_msg());
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		// 2.调用订单标准化
		try {

			resp = templateOrdSave(req);
			long end = System.currentTimeMillis();
			logger.info("模板标准化时间=================================：" + (end - start));
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("标准化成功");

			// 3.标准化成功调用标准化方案
			List orderIdList = new ArrayList();
			orderIdList.add(req.getBase_order_id());
			this.exeOrd(orderIdList);
			// 写入主机环境日志
			this.insertSysParams(orderIdList);
		} catch (Exception e) {
			String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("标准化失败");
			resp.setError_stack_msg(error_stack_msg);
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());
			return resp;
		}
		return resp;
	}

	/**
	 * 
	 * @Description: 调用核心系统做模版转换.获取属性定义
	 * @param reqMsgStrMap
	 * @param orderId
	 * @param req
	 * @return
	 * @author zhouqiangang
	 * @date 2015年12月11日 上午9:41:44
	 */
	private Map<String, Object> templateAccessConvertAndGetAttrDefTable(Map<String, Object> reqMsgStrMap,
			String orderId, TemplatesOrderStandardReq req) {
		/**
		 * rop调用说明： 1、被调用方查询语句[select t.app_key, t.* from pm_app t] 必须有传入
		 * AppKeyEnum.APP_KEY_WSSMALL_ESC key值;
		 * 2、配环境变量：-DREMOTE_ROP_ADDRESS=http://10.45.54.87:8080/router
		 * 值为被调用方ip.端口
		 * 3、此处逻辑,先获取jvm配置[REMOTE_ROP_ADDRESS]值,如果没有配置则继续获取es_config_info配置,
		 * 如果都没配置则抛出错误
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获取核心版本rop调用地址
		String url = getRemoteRopAddress();
		// 构建请求对象
		TemplateAccessConvertReq tplCovReq = new TemplateAccessConvertReq();
		tplCovReq.setInMap(reqMsgStrMap);
		tplCovReq.setOrder_id(orderId);
		tplCovReq.setTpl_inout_type("in#out");
		tplCovReq.setTpl_code(req.getTemplate_code());
		tplCovReq.setTpl_ver(req.getTemplate_version());
		// 获取client
		AppKeyEnum appKeyEnum = AppKeyEnum.APP_KEY_WSSMALL_ESC;// ClientFactory.getAppKeyEnum(ManagerUtils.getSourceFrom());
		ZteClient client = new DefaultZteRopClient(url, appKeyEnum.getAppKey(), appKeyEnum.getAppSec(), "1.0");

		// 调用核心版本模版转换
		TemplateAccessConvertResp tempResp = client.execute(tplCovReq, TemplateAccessConvertResp.class);
		if (!InfoConsts.API_RET_SUCC_FLAG.equals(tempResp.getError_code())) {
			CommonNTools.addFailError("模版转化报文信息失败！");
		} else {
			resultMap.put("allObjMap", tempResp.getOutObjMap());
		}
		// 调用核心版本获取属性处理器配置
		GetAttrDefTableReq attrDefTableReq = new GetAttrDefTableReq();
		GetAttrDefTableResp attrDefTableResp = client.execute(attrDefTableReq, GetAttrDefTableResp.class);
		if (!InfoConsts.API_RET_SUCC_FLAG.equals(attrDefTableResp.getError_code())) {
			CommonNTools.addFailError("获取属性定义失败！");
		} else {
			resultMap.put("attrDefTableVOList", attrDefTableResp.getAttrDefTableVOList());
		}
		return resultMap;
	}

	/**
	 * 
	 * @Description: 获取核心版本rop调用地址
	 * @return
	 * @author zhouqiangang
	 * @date 2015年12月9日 下午2:45:17
	 */
	private String getRemoteRopAddress() {
		String url = System.getProperty("REMOTE_ROP_ADDRESS");
		if (StringUtil.isEmpty(url)) {
			url = getConfigInfoValueByCfID("TEMPLATE_REMOTE_ROP_ADDRESS");
		}
		if (StringUtil.isEmpty(url)) {
			CommonTools.addError(ConstsCore.ERROR_FAIL, "模版转换服务未配置！");
		}
		return url;
	}

	/**
	 * 
	 * @Description: 根据coid获取报文并转为map格式
	 * @param coId
	 * @return
	 * @author zhouqiangang
	 * @date 2015年12月9日 下午2:45:36
	 */
	private Map<String, Object> getReqMsgByCoId(String coId, boolean isIs_exception) {
		// 1.调用模版转换 将报文转成 List<Map<表名称, Map<属性, 属性值>>>;
		String reqMsgStr = this.getReqContent(null, coId, isIs_exception);
		// 报文转换为map格式
		Map<String, Object> reqMsgStrMap = null;
		if (reqMsgStr.indexOf("<root>") > -1) {
			reqMsgStrMap = XmlUtils.xmlToMap(reqMsgStr);
		} else {
			reqMsgStrMap = XmlUtils.xmlToMap("<root>" + reqMsgStr + "</root>");
		}
		return reqMsgStrMap;
	}

	/**
	 * 
	 * @Description: 插入orderTree数据
	 * @param orderId
	 * @author zhouqiangang
	 * @date 2015年12月9日 下午2:45:57
	 */
	private void insertOrderTree(String orderId) {
		OrderTreeBusiRequest orderTreeBusiRequest = new OrderTreeBusiRequest();
		orderTreeBusiRequest.setOrder_id(orderId);
		orderTreeBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
		orderTreeBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderTreeBusiRequest.setCreate_time(Consts.SYSDATE);
		orderTreeBusiRequest.setUpdate_time(Consts.SYSDATE);
		orderTreeBusiRequest.store();
	}

	/**
	 * 
	 * @Description: 对象map格式转为对象
	 * @param allObjMap
	 * @param zteBusiRequestMap
	 * @param orderId
	 * @author zhouqiangang
	 * @date 2015年12月9日 下午2:46:39
	 */
	private void map2ZteBusiReques(Map<String, List<Map<String, String>>> allObjMap,
			Map<String, Object> zteBusiRequestMap, String orderId) {
		for (Map.Entry<String, List<Map<String, String>>> allEntry : allObjMap.entrySet()) {
			List<ZteBusiRequest> zteBusiRequestList = new ArrayList<ZteBusiRequest>();
			for (Map<String, String> objMap : allEntry.getValue()) {
				objMap.put("order_id", orderId);
				RequestBeanDefinition beanDef = DefaultActionRequestDefine.getInstance()
						.getActionRequestTableNameMap(allEntry.getKey());
				if (null != beanDef) {
					ZteBusiRequest beanInst = (ZteBusiRequest) BeanUtils.instantiateClass(beanDef.getBeanClass());
					// 获取主键.设置主键.
					RequestBeanDefinition servicebean = BusiUtils.getRequestServiceDefinition(beanInst);
					final String primaryKeys = servicebean.getPrimary_keys();
					if (!StringUtil.isEmpty(primaryKeys)) {
						String[] primaryKeysArray = primaryKeys.split(",");
						for (int i = 0; i < primaryKeysArray.length; i++) {
							final String primaryKey = primaryKeysArray[i];
							if (!"order_id".equals(primaryKey)) {
								objMap.put(primaryKey, SequenceTools.getShort18PrimaryKey());
							}
						}
					}
					try {
						// commons.BeanUtils.mapToBeanByTemplates(objMap,
						// beanInst);
						// commons.BeanUtils.mapToBean(objMap, beanInst);
						com.ztesoft.common.util.BeanUtils.copyProperties(beanInst, objMap);
						setDynMap(objMap, beanInst);
					} catch (Exception e) {
						e.printStackTrace();
					}
					zteBusiRequestList.add(beanInst);
				}
			}
			zteBusiRequestMap.put(allEntry.getKey(), zteBusiRequestList);
		}
	}

	/**
	 * 
	 * @Description: 属性处理
	 * @param attrDefTableVOList
	 * @param zteBusiRequestMap
	 * @param orderId
	 * @author zhouqiangang
	 * @date 2015年12月9日 下午2:29:52
	 */
	@SuppressWarnings("unchecked")
	private void attrHandler(List<AttrDefTableVO> attrDefTableVOList, Map<String, Object> zteBusiRequestMap,
			String orderId) {
		if (attrDefTableVOList != null) {
			for (AttrDefTableVO attrDef : attrDefTableVOList) {
				Object obj = zteBusiRequestMap.get(attrDef.getTable_name());
				if (obj != null && attrDef.getHandler_id() != null) {
					if (obj instanceof ZteBusiRequest) {
						zteBusiRequestMap = this.excAttrHandler(attrDef, orderId, zteBusiRequestMap,
								(ZteBusiRequest<?>) obj, 0);
					} else if (obj instanceof List) {
						if (null != obj && ((List<?>) obj).size() > 0) {
							Object temp = obj;
							for (int i = 0; i < ((List<?>) temp).size(); i++) {
								if (((List<?>) temp).get(i) instanceof ZteBusiRequest) {
									ZteBusiRequest<?> busiReq = (ZteBusiRequest<?>) ((List<?>) temp).get(i);
									zteBusiRequestMap = this.excAttrHandler(attrDef, orderId, zteBusiRequestMap,
											busiReq, i);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * @Description: 业务对象入库 -1
	 * @param objMap
	 * @author zhouqiangang
	 * @date 2015年12月9日 下午2:47:31
	 */
	@SuppressWarnings("rawtypes")
	private void storeBusiMap(final Map<String, Object> objMap, final String orderId) {
		if (null == objMap || objMap.isEmpty())
			return;
		List<FutureTask> futureTaskList = new ArrayList<FutureTask>();
		ExecutorService exec = Executors.newFixedThreadPool(objMap.size(), new ThreadFactoryImpl("ThreadBusiStorer"));
		Iterator<Entry<String, Object>> iteratorSave = objMap.entrySet().iterator();
		boolean isThrow = false;
		try {
			while (iteratorSave.hasNext()) {
				Entry<String, Object> entry = iteratorSave.next();
				Object obj = entry.getValue();
				if (obj instanceof ZteBusiRequest) {
					final ZteBusiRequest zteBusi = (ZteBusiRequest) obj;
					if (StringUtils.isEmpty(zteBusi.getDb_action()))
						return;
					createOrderBusiStore(exec, futureTaskList, zteBusi);
				} else if (obj instanceof List) {
					createOrderBusiStore(exec, futureTaskList, obj);
				}
			}
			for (FutureTask futureTask : futureTaskList) {
				futureTask.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
			isThrow = true;
			CommonNTools.addError(ConstsCore.ERROR_FAIL, "模版转入订单创建失败");
		} finally {
			exec.shutdownNow();
			// 手动清除入库成功数据
			if (isThrow) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						storeFailClearDbBusiData(objMap, orderId);
					}
				}).start();
			}
		}
	}

	/**
	 * 订单创建失败后清楚以插入订单数据
	 * 
	 * @param objMap
	 * @param orderId
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	private void storeFailClearDbBusiData(Map<String, Object> objMap, String orderId) {
		StringBuffer delSql = new StringBuffer();
		delSql.append("DELETE #TABLENAME  T WHERE T.ORDER_ID = '").append(orderId).append("'");
		for (Map.Entry<String, Object> entry : objMap.entrySet()) {
			String table_name = entry.getKey();
			RequestBeanDefinition<ZteBusiRequest> serviceDefinition = BusiUtils
					.getRequestServiceDefinitionByTableName(table_name);
			// String tableType = serviceDefinition.getTable_type();
			this.baseDaoSupport.execute(delSql.toString().replace("#TABLENAME", entry.getKey()), null);
		}
	}

	/**
	 * 
	 * @Description: 业务对象入库 -2
	 * @param exec
	 * @param futureTaskList
	 * @param obj
	 * @author zhouqiangang
	 * @date 2015年12月9日 下午2:49:09
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createOrderBusiStore(ExecutorService exec, List<FutureTask> futureTaskList, final Object obj) {
		FutureTask<Boolean> ft = new FutureTask<Boolean>(new Callable() {
			@Override
			public Object call() throws Exception {
				if (obj instanceof ZteBusiRequest) {
					final ZteBusiRequest zteBusi = (ZteBusiRequest) obj;
					zteBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					zteBusi.setDb_action(ConstsCore.DB_ACTION_INSERT);
					IZteBusiRequestHander zteBusiHander = (IZteBusiRequestHander) zteBusi;
					zteBusiHander.store();
				} else if (obj instanceof List) {
					List zteBusiList = (List) obj;
					for (int i = 0; i < zteBusiList.size(); i++) {
						Object objValue = zteBusiList.get(i);
						if (objValue instanceof ZteBusiRequest) {
							final ZteBusiRequest zteBusi = (ZteBusiRequest) objValue;
							zteBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							zteBusi.setDb_action(ConstsCore.DB_ACTION_INSERT);
							IZteBusiRequestHander zteBusiHander = (IZteBusiRequestHander) zteBusi;
							zteBusiHander.store();
						}
					}
				}
				return true;
			}
		});
		futureTaskList.add(ft);
		exec.submit(ft);
	}

	/**
	 * 
	 * @Description: 属性处理
	 * @param attrDef
	 * @param order_id
	 * @param objMap
	 * @param busiReq
	 * @param listIndex
	 * @return
	 * @author zhouqiangang
	 * @date 2015年12月9日 下午2:49:47
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map excAttrHandler(AttrDefTableVO attrDef, String order_id, Map<String, Object> objMap,
			ZteBusiRequest busiReq, int listIndex) {
		AttrSwitchParams params = BaseTools.packageAttrSwitchParams(attrDef, order_id, busiReq, objMap, "in");
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		AttrInstLoadResp resp = client.execute(params, AttrInstLoadResp.class);
		objMap = resp.getObjMap();
		Object value = objMap.get(attrDef.getTable_name());
		if (value instanceof ZteBusiRequest) {
			objMap.put(attrDef.getTable_name(), resp.getBusiRequest());
		} else if (value instanceof List) {
			List<ZteBusiRequest> list = (List<ZteBusiRequest>) value;
			list.remove(listIndex);
			list.add(listIndex, (ZteBusiRequest) resp.getBusiRequest());
			objMap.put(attrDef.getTable_name(), list);
		}
		return objMap;
	}

	/**
	 * 
	 * @Description: 获取模版转换rop服务器地址
	 * @param cfId
	 * @return
	 * @author zhouqiangang
	 * @date 2015年12月4日 上午11:06:11
	 */
	public String getConfigInfoValueByCfID(String cfId) {
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		return cacheUtil.getConfigInfo(cfId);
	}

	// 获取归集系统请求报文
	public String getReqContent(String order_id, String co_id, boolean is_exception) {
		String content = "";
		OrderQueueBusiRequest orderQueue = new OrderQueueBusiRequest();
		if (is_exception) {
			orderQueue = CommonDataFactory.getInstance().getOrderQueueFor(order_id, co_id);
		} else {
			orderQueue = CommonDataFactory.getInstance().getOrderQueue(order_id, co_id);
		}
		if (orderQueue != null) {
			content = orderQueue.getContents();
		}
		return content;
	}

	// 订单标准化以前埋点调用校验系统
	public CheckResp validateOrder(String coId, String format) {
		long start_validate = System.currentTimeMillis();
		CheckReq checkReq = new CheckReq();
		checkReq.setTrace_code(ConstsCore.TRACE_V);
		checkReq.setBiz_id(EccConsts.ORDER_STANDER_BIZ);
		checkReq.setExe_time(EccConsts.EXE_TIME_BEFORE);
		checkReq.setObj_id(coId);
		checkReq.setObj_type("order_queue");

		HashMap<String, String> extParam = new HashMap<String, String>();
		extParam.put("msgFormatType", format);// 报文格式
		checkReq.setExt_param(extParam);
		CheckResp checkRsp = CommonDataFactory.getInstance().checkProxy(checkReq);
		long end_validate = System.currentTimeMillis();
		logger.info("前置校验耗时：" + (end_validate - start_validate));
		return checkRsp;
	}

	// 调用异常单系统执行异常单方案
	public void exeExceptionOrd(final String out_tid, final String co_id, final String error_msg,
			final String error_stack_msg) {
		try {
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String is_exception_run = cacheUtil.getConfigInfo(EcsOrderConsts.IS_EXCEPTION_RUN);// 是否启用异常系统
			if (!StringUtil.isEmpty(is_exception_run) && new Integer(is_exception_run).intValue() != 0) {// 异常系统启动标识
				final OrderExpWriteForBusReq req = new OrderExpWriteForBusReq();
				req.setObj_id(co_id); // 队列id
				req.setOut_tid(out_tid);
				req.setObj_type("order_queue"); // 对象类型（order、order_queue）
				req.setSearch_id(EcsOrderConsts.EXP_STANDARD_CHECK_id); // 搜索id
				req.setSearch_code(EcsOrderConsts.EXP_STANDARD_CHECK_CODE);// 搜索编码
				req.setError_msg(error_msg);
				req.setError_stack_msg(error_stack_msg);// 错误堆栈

				String exe_type = MqEnvGroupConfigSetting.ORD_EXP_EXEC;// 调用方式
																		// m/d
																		// m是走mq
																		// d是走dubbo
				// 消息推送异常单方案
				if (ConstsCore.DECOUPLING_EXEC_D.equals(exe_type)) {// dubbo
					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
							OrderExpWriteResp orderExpWriteResp = client.execute(req, OrderExpWriteResp.class);
						}
					});
					thread.setName("订单标准化业务异常线程");
					thread.start();
				} else {// mq
					ZteResponse mqResp = this.exeOrdMq(req, ConstsCore.MACHINE_EVN_CODE_ECSORD_EXP);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 正常单系统执行正常单标准化方案
	public void exeOrd(List orderIdList) {
		long ord_start = System.currentTimeMillis();
		final StartOrderPlanReq planreq = new StartOrderPlanReq();
		planreq.setOrderIdList(orderIdList);
		String exe_type = MqEnvGroupConfigSetting.ORD_STANDING_EXEC;// 调用方式 m/d
																	// m是走mq
																	// d是走dubbo
		try { // 消息推送正常单标准化方案

			boolean flag = false;// dubbo或MQ失败时使用线程异常调用
			if (ConstsCore.DECOUPLING_EXEC_D.equals(exe_type)) {// 消息开关关闭走dubbo
				flag = true;
			} else {
				ZteResponse resp = this.exeOrdMq(planreq, ConstsCore.MACHINE_EVN_CODE_ECSORD);
				// 消息调用失败就走dubbo
				if (resp == null || ConstsCore.ERROR_FAIL.equals(resp.getError_code())) {
					flag = true;
				}
			}
			if (flag) {
				String PLAN_FIEXE_COUNT = cacheUtil.getConfigInfo("PLAN_FIEXE_COUNT");
				if (!initFixThreadCount && new Integer(PLAN_FIEXE_COUNT).intValue() != 0) {
					PLAN_FIEXE_COUNT = cacheUtil.getConfigInfo("PLAN_FIEXE_COUNT");
					threadPlanExeExecutor = new ThreadPoolExecutor(new Integer(PLAN_FIEXE_COUNT).intValue(),
							new Integer(PLAN_FIEXE_COUNT).intValue(), 1000, TimeUnit.MILLISECONDS,
							new ArrayBlockingQueue<Runnable>(new Integer(PLAN_FIEXE_COUNT).intValue()),
							new ThreadPoolExecutor.CallerRunsPolicy());
					initFixThreadCount = true;
				}
				// //配置数据大于0,则执行,否则不执行
				if (new Integer(PLAN_FIEXE_COUNT).intValue() > 0) {
					threadPlanExeExecutor.execute(new Runnable() {
						@Override
						public void run() {
							ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
							StartOrderPlanResp pResp = client.execute(planreq, StartOrderPlanResp.class);
						}
					});
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long ord_end = System.currentTimeMillis();
		logger.info("订单执行耗时：" + (ord_end - ord_start));
	}

	public List getOrderIdList(List<OrderAddResp> orderAddRespList) {
		List list = new ArrayList();
		if (orderAddRespList != null && orderAddRespList.size() > 0) {
			for (int i = 0; i < orderAddRespList.size(); i++) {
				OrderAddResp orderAddResp = orderAddRespList.get(i);
				List<Order> orderList = orderAddResp.getOrderList();
				if (orderList != null && orderList.size() > 0) {
					for (int j = 0; j < orderList.size(); j++) {
						list.add(orderList.get(j).getOrder_id());
					}
				}
			}
		}
		return list;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public OrderStandardPushResp saveOrder(List<Outer> out_order_List) throws Exception {
		long start_s = System.currentTimeMillis();
		OrderStandardPushResp resp = new OrderStandardPushResp();
		OrderStandardPushReq req = new OrderStandardPushReq();
		req.setOuterList(out_order_List);
		resp = stdOuterService.pushOrderStandard(req);
		long end_s = System.currentTimeMillis();
		logger.info("订单标准化入库总耗时：" + (end_s - start_s));
		return resp;
	}

	public TaobaoOrderUtil getTaobaoOrderUtil() {
		return taobaoOrderUtil;
	}

	public void setTaobaoOrderUtil(TaobaoOrderUtil taobaoOrderUtil) {
		this.taobaoOrderUtil = taobaoOrderUtil;
	}

	public IOuterService getStdOuterService() {
		return stdOuterService;
	}

	public void setStdOuterService(IOuterService stdOuterService) {
		this.stdOuterService = stdOuterService;
	}

	// 正常单系统执行正常单标准化方案
	public ZteResponse exeOrdMq(ZteRequest zteReq, String envCode) {
		long ord_start = System.currentTimeMillis();
		ZteResponse resp = null;
		try {
			AsynExecuteMsgWriteMqReq req = new AsynExecuteMsgWriteMqReq();
			req.setService_code(zteReq.getApiMethodName());
			req.setVersion(ConstsCore.DUBBO_DEFAULT_VERSION);
			req.setZteRequest(zteReq);
			req.setConsume_env_code(com.ztesoft.common.util.BeanUtils.getHostEnvCodeByEnvStatus(envCode));
			if (orderQueueBaseManager == null) {
				orderQueueBaseManager = SpringContextHolder.getBean("orderQueueBaseManager");
			}
			resp = orderQueueBaseManager.asynExecuteMsgWriteMq(req);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		long ord_end = System.currentTimeMillis();
		logger.info("订单执行耗时：" + (ord_end - ord_start));
		return resp;
	}

	public void setDynMap(Map objMap, ZteBusiRequest beanInst) {
		Map<String, Object> beanMap = new HashMap();
		Map<String, Object> dyn_map = null;
		try {
			commons.BeanUtils.bean2Map(beanMap, beanInst);
			Iterator it = objMap.keySet().iterator();
			while (it.hasNext()) {
				String field_name = (String) it.next();
				if (!beanMap.containsKey(field_name)) {
					if (dyn_map == null) {
						dyn_map = new HashMap();
						dyn_map.put("is_model_insert", "1");
					}
					dyn_map.put(field_name, objMap.get(field_name));
				}
			}
			if (dyn_map != null) {
				beanInst.setDyn_field(dyn_map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public HSMallOrderStandardResp hsMallOrderStandard(HSMallOrderStandardReq req) {
		HSMallOrderStandardResp resp = new HSMallOrderStandardResp();
		String co_id = req.getBase_co_id();
		// 1.调用校验系统进行校验
		CheckResp checkResp = this.validateOrder(co_id, req.getFormat());// 调用校验系统进行校验
		// 校验失败直接返回 校验系统已写入异常单,这里不用重复写入异常单
		if (ConstsCore.ERROR_FAIL.equals(checkResp.getError_code())) {
			resp.setError_msg("标准化前置校验失败");
			resp.setError_stack_msg("队列id:" + co_id + checkResp.getError_msg());
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		// 2.订单收单本地化校验--邵初
		long val_start = System.currentTimeMillis();
		try {
			resp = HSMallOrderUtil.orderStandardValidate(req);
			// 校验失败调用异常单方案
			if (ConstsCore.ERROR_FAIL.equals(resp.getError_code())) {
				resp.setError_stack_msg("队列id:" + co_id + resp.getError_msg());
				this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
				return resp;
			}
		} catch (Exception e) {
			String error_msg = CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("订单收单本地化校验失败");
			resp.setError_stack_msg("队列id:" + co_id + error_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			return resp;
		}
		long val_end = System.currentTimeMillis();
		logger.info("本地校验耗时：" + (val_end - val_start));
		// 3.进行标准化
		OrderStandardPushResp orderStandardPushResp = new OrderStandardPushResp();
		try {
			orderStandardPushResp = saveOrder(resp.getOut_order_List());
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("标准化成功");
			List orderIdList = this.getOrderIdList(orderStandardPushResp.getOrderAddRespList());
			// 4.1标准化成功调用正常单标准化方案
			this.exeOrd(orderIdList);// 标准化成功执行正常单方案
			// 写入主机环境日志
			this.insertSysParams(orderIdList);
		} catch (Exception e) {
			e.printStackTrace();
			String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("标准化失败");
			resp.setError_stack_msg(error_stack_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			// 4.2 标准化失败调用异常单方案
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			// 4.3 标准化失败写入outError表
			if (resp.getOut_order_List().size() > 0) {
				insertOuterError(resp.getOut_order_List());
			}
			return resp;
		}
		return resp;
	}

	@Override
	public TaoBaoTianjiOrderStandardResp taoBaoTianjiOrderStandard(TaoBaoTianjiOrderStandardReq req) {
		TaoBaoTianjiOrderStandardResp resp = new TaoBaoTianjiOrderStandardResp();

		String format = req.getFormat();
		String order_id = req.getBase_order_id();
		String co_id = req.getBase_co_id();
		// 1.校验系统校验
		CheckResp checkResp = this.validateOrder(co_id, format);// 调用校验系统进行校验
		if (ConstsCore.ERROR_FAIL.equals(checkResp.getError_code())) {
			resp.setError_msg("队列id:" + co_id + "标准化前置校验失败");
			resp.setError_stack_msg("队列id:" + co_id + checkResp.getError_msg());
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		// 2.同步淘宝订单
		try {
			if (taobaoOrderUtil == null) {
				taobaoOrderUtil = SpringContextHolder.getBean("taobaoOrderUtil");
			}
			resp = taobaoTianjiOrderUtil.getOrderContent(req);
			// 校验失败调用异常单方案
			if (ConstsCore.ERROR_FAIL.equals(resp.getError_code())) {
				this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
				resp.setError_stack_msg("队列id:" + co_id + resp.getError_msg());
				return resp;
			}
		} catch (Exception e) {
			String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("队列id:" + co_id + "淘宝同步订单失败");
			resp.setError_stack_msg(error_stack_msg);
			return resp;
		}
		// 3.进行标准化
		OrderStandardPushResp orderStandardPushResp = new OrderStandardPushResp();
		try {
			orderStandardPushResp = saveOrder(resp.getOut_order_List());
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("标准化成功");
			List orderIdList = this.getOrderIdList(orderStandardPushResp.getOrderAddRespList());
			// 4.1标准化成功调用正常单标准化方案
			this.exeOrd(orderIdList);// 标准化成功执行正常单方案
			// 写入主机环境日志
			this.insertSysParams(orderIdList);
		} catch (Exception e) {
			String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("标准化失败");
			resp.setError_stack_msg(error_stack_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			// 4.2 标准化失败调用异常单方案
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			// 4.3 标准化失败写入outError表
			if (resp.getOut_order_List().size() > 0) {
				insertOuterError(resp.getOut_order_List());
			}
			return resp;
		}
		return resp;
	}

	@Override
	public ZJLocalMallOrderStandardResp zjLocalMallOrderStandard(ZJLocalMallOrderStandardReq req) {
		ZJLocalMallOrderStandardResp resp = new ZJLocalMallOrderStandardResp();
		String co_id = req.getBase_co_id();
		// 1.调用校验系统进行校验
		CheckResp checkResp = this.validateOrder(co_id, req.getFormat());// 调用校验系统进行校验
		// 校验失败直接返回 校验系统已写入异常单,这里不用重复写入异常单
		if (ConstsCore.ERROR_FAIL.equals(checkResp.getError_code())) {
			resp.setError_msg("标准化前置校验失败");
			resp.setError_stack_msg("队列id:" + co_id + checkResp.getError_msg());
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		// 2.订单收单本地化校验--邵初
		long val_start = System.currentTimeMillis();
		try {
			resp = ZJLocalMallOrderUtil.orderStandardValidate(req);
			// 校验失败调用异常单方案
			if (ConstsCore.ERROR_FAIL.equals(resp.getError_code())) {
				resp.setError_stack_msg("队列id:" + co_id + resp.getError_msg());
				this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
				return resp;
			}
		} catch (Exception e) {
			String error_msg = CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("订单收单本地化校验失败");
			resp.setError_stack_msg("队列id:" + co_id + error_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			return resp;
		}
		long val_end = System.currentTimeMillis();
		logger.info("本地校验耗时：" + (val_end - val_start));
		// 3.进行标准化
		OrderStandardPushResp orderStandardPushResp = new OrderStandardPushResp();
		try {
			orderStandardPushResp = saveOrder(resp.getOut_order_List());
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("标准化成功");
			List orderIdList = this.getOrderIdList(orderStandardPushResp.getOrderAddRespList());
			// 4.1标准化成功调用正常单标准化方案
			this.exeOrd(orderIdList);// 标准化成功执行正常单方案
			// 写入主机环境日志
			this.insertSysParams(orderIdList);
		} catch (Exception e) {
			e.printStackTrace();
			String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("标准化失败");
			resp.setError_stack_msg(error_stack_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			// 4.2 标准化失败调用异常单方案
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			// 4.3 标准化失败写入outError表
			if (resp.getOut_order_List().size() > 0) {
				insertOuterError(resp.getOut_order_List());
			}
			return resp;
		}
		return resp;
	}

	@Override
	public HBBroadbandOrderStandardResp hbBroadbandOrderStandard(HBBroadbandOrderStandardReq req) {
		HBBroadbandOrderStandardResp resp = new HBBroadbandOrderStandardResp();
		String co_id = req.getBase_co_id();
		// 1.调用校验系统进行校验
		CheckResp checkResp = this.validateOrder(co_id, req.getFormat());// 调用校验系统进行校验
		// 校验失败直接返回 校验系统已写入异常单,这里不用重复写入异常单
		if (ConstsCore.ERROR_FAIL.equals(checkResp.getError_code())) {
			resp.setError_msg("标准化前置校验失败");
			resp.setError_stack_msg("队列id:" + co_id + checkResp.getError_msg());
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		// 2.订单收单本地化校验--邵初
		long val_start = System.currentTimeMillis();
		try {
			// resp = HBOrderUtil.orderStandardValidate(req);
			// 校验失败调用异常单方案
			if (ConstsCore.ERROR_FAIL.equals(resp.getError_code())) {
				resp.setError_stack_msg("队列id:" + co_id + resp.getError_msg());
				this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
				return resp;
			}
		} catch (Exception e) {
			String error_msg = CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("订单收单本地化校验失败");
			resp.setError_stack_msg("队列id:" + co_id + error_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			return resp;
		}
		long val_end = System.currentTimeMillis();
		logger.info("本地校验耗时：" + (val_end - val_start));
		// 3.进行标准化
		OrderStandardPushResp orderStandardPushResp = new OrderStandardPushResp();
		try {
			orderStandardPushResp = saveOrder(resp.getOut_order_List());
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("标准化成功");
			List orderIdList = this.getOrderIdList(orderStandardPushResp.getOrderAddRespList());
			// 4.1标准化成功调用正常单标准化方案
			this.exeOrd(orderIdList);// 标准化成功执行正常单方案
			// 写入主机环境日志
			this.insertSysParams(orderIdList);
		} catch (Exception e) {
			e.printStackTrace();
			String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("标准化失败");
			resp.setError_stack_msg(error_stack_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			// 4.2 标准化失败调用异常单方案
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			// 4.3 标准化失败写入outError表
			if (resp.getOut_order_List().size() > 0) {
				insertOuterError(resp.getOut_order_List());
			}
			return resp;
		}
		return resp;
	}

	@Override
	public FixBusiOrderStandardResp fixBusiOrderStandard(FixBusiOrderStandardReq req) {
		FixBusiOrderStandardResp resp = new FixBusiOrderStandardResp();
		String co_id = req.getBase_co_id();
		// 1.前置校验开关
		resp = (FixBusiOrderStandardResp) validatePreCheck(req, resp);
		// 2.订单收单本地化校验
		long val_start = System.currentTimeMillis();
		try {
			resp = FixBusiOrderUtil.orderStandardValidate(req);
			// 校验失败调用异常单方案
			if (ConstsCore.ERROR_FAIL.equals(resp.getError_code())) {
				resp.setError_stack_msg("队列id:" + co_id + resp.getError_msg());
				this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
				return resp;
			}
		} catch (Exception e) {
			String error_msg = CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("订单收单本地化校验失败");
			resp.setError_stack_msg("队列id:" + co_id + error_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			return resp;
		}
		long val_end = System.currentTimeMillis();
		logger.info("本地校验耗时：" + (val_end - val_start));
		// 3.进行标准化
		OrderStandardPushResp orderStandardPushResp = new OrderStandardPushResp();
		try {
			orderStandardPushResp = saveOrder(resp.getOut_order_List());
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("标准化成功");
			List orderIdList = this.getOrderIdList(orderStandardPushResp.getOrderAddRespList());
			// 4.1标准化成功调用正常单标准化方案
			this.exeOrd(orderIdList);// 标准化成功执行正常单方案
			// 写入主机环境日志
			this.insertSysParams(orderIdList);
		} catch (Exception e) {
			e.printStackTrace();
			String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("标准化失败");
			resp.setError_stack_msg(error_stack_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			// 4.2 标准化失败调用异常单方案
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			// 4.3 标准化失败写入outError表
			if (resp.getOut_order_List().size() > 0) {
				insertOuterError(resp.getOut_order_List());
			}
			return resp;
		}
		return resp;
	}

	@Override
	public BroadBandCtnStandardResp broadBandCtnStandard(BroadBandCtnStandardReq req) {
		BroadBandCtnStandardResp resp = new BroadBandCtnStandardResp();
		String co_id = req.getBase_co_id();
		// 1.调用校验系统进行校验
		CheckResp checkResp = this.validateOrder(co_id, req.getFormat());// 调用校验系统进行校验
		// 校验失败直接返回 校验系统已写入异常单,这里不用重复写入异常单
		if (ConstsCore.ERROR_FAIL.equals(checkResp.getError_code())) {
			resp.setError_msg("标准化前置校验失败");
			resp.setError_stack_msg("队列id:" + co_id + checkResp.getError_msg());
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		// 2.订单收单本地化校验
		long val_start = System.currentTimeMillis();
		try {
			resp = BroadBandCtnUtil.orderStandardValidate(req);
			// 校验失败调用异常单方案
			if (ConstsCore.ERROR_FAIL.equals(resp.getError_code())) {
				resp.setError_stack_msg("队列id:" + co_id + resp.getError_msg());
				this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
				return resp;
			}
		} catch (Exception e) {
			String error_msg = CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("订单收单本地化校验失败");
			resp.setError_stack_msg("队列id:" + co_id + error_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			return resp;
		}
		long val_end = System.currentTimeMillis();
		logger.info("本地校验耗时：" + (val_end - val_start));
		// 3.进行标准化
		OrderStandardPushResp orderStandardPushResp = new OrderStandardPushResp();
		try {
			orderStandardPushResp = saveOrder(resp.getOut_order_List());
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("标准化成功");
			List orderIdList = this.getOrderIdList(orderStandardPushResp.getOrderAddRespList());
			// 4.1标准化成功调用正常单标准化方案
			this.exeOrd(orderIdList);// 标准化成功执行正常单方案
			// 写入主机环境日志
			this.insertSysParams(orderIdList);
		} catch (Exception e) {
			e.printStackTrace();
			String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("标准化失败");
			resp.setError_stack_msg(error_stack_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			// 4.2 标准化失败调用异常单方案
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			// 4.3 标准化失败写入outError表
			if (resp.getOut_order_List().size() > 0) {
				insertOuterError(resp.getOut_order_List());
			}
			return resp;
		}
		return resp;
	}

	@Override
	public InternetOrderStandardResp internetBusiOrderStandard(InternetOrderStandardReq req) {

		InternetOrderStandardResp resp = new InternetOrderStandardResp();
		String co_id = req.getBase_co_id();
		// 1.前置校验开关
		resp = (InternetOrderStandardResp) validatePreCheck(req, resp);
		// 2.订单收单本地化校验
		long val_start = System.currentTimeMillis();
		try {
			resp = InternetBusiOrderUtil.orderStandardValidate(req);

			// 校验失败调用异常单方案
			if (ConstsCore.ERROR_FAIL.equals(resp.getError_code())) {
				resp.setError_stack_msg("队列id:" + co_id + resp.getError_msg());
				this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
				return resp;
			}
		} catch (Exception e) {
			String error_msg = CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("订单收单本地化校验失败");
			resp.setError_stack_msg("队列id:" + co_id + error_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			return resp;
		}
		long val_end = System.currentTimeMillis();
		logger.info("本地校验耗时：" + (val_end - val_start));
		// 3.进行标准化
		OrderStandardPushResp orderStandardPushResp = new OrderStandardPushResp();
		try {
			List<Outer> testList = resp.getOut_order_List();
			orderStandardPushResp = saveOrder(resp.getOut_order_List());
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("标准化成功");
			List orderIdList = this.getOrderIdList(orderStandardPushResp.getOrderAddRespList());
			// 4.1标准化成功调用正常单标准化方案
			this.exeOrd(orderIdList);// 标准化成功执行正常单方案
			// 写入主机环境日志
			this.insertSysParams(orderIdList);
		} catch (Exception e) {
			e.printStackTrace();
			String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("标准化失败");
			resp.setError_stack_msg(error_stack_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			// 4.2 标准化失败调用异常单方案
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			// 4.3 标准化失败写入outError表
			if (resp.getOut_order_List().size() > 0) {
				insertOuterError(resp.getOut_order_List());
			}
			return resp;
		}
		return resp;

	}

	@Override
	public InternetGroupStandardResp internetGroupOrderStandard(InternetGroupOrderStandardReq req) {
		InternetGroupStandardResp resp = new InternetGroupStandardResp();
		String co_id = req.getBase_co_id();
		// 1.前置校验开关
		resp = (InternetGroupStandardResp) validatePreCheck(req, resp);
		// 2.订单收单本地化校验
		long val_start = System.currentTimeMillis();
		try {
			resp = InternetGroupOrderUtil.orderStandardValidate(req);

			// 校验失败调用异常单方案
			if (ConstsCore.ERROR_FAIL.equals(resp.getError_code())) {
				resp.setError_stack_msg("队列id:" + co_id + resp.getError_msg());
				this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
				return resp;
			}
		} catch (Exception e) {
			String error_msg = CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("订单收单本地化校验失败");
			resp.setError_stack_msg("队列id:" + co_id + error_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			return resp;
		}
		long val_end = System.currentTimeMillis();
		logger.info("本地校验耗时：" + (val_end - val_start));
		// 3.进行标准化
		OrderStandardPushResp orderStandardPushResp = new OrderStandardPushResp();
		try {
			List<Outer> testList = resp.getOut_order_List();
			orderStandardPushResp = saveOrder(resp.getOut_order_List());
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("标准化成功");
			List orderIdList = this.getOrderIdList(orderStandardPushResp.getOrderAddRespList());
			// 4.1标准化成功调用正常单标准化方案
			this.exeOrd(orderIdList);// 标准化成功执行正常单方案
			// 写入主机环境日志
			this.insertSysParams(orderIdList);
		} catch (Exception e) {
			e.printStackTrace();
			String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("标准化失败");
			resp.setError_stack_msg(error_stack_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			// 4.2 标准化失败调用异常单方案
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			// 4.3 标准化失败写入outError表
			if (resp.getOut_order_List().size() > 0) {
				insertOuterError(resp.getOut_order_List());
			}
			return resp;
		}
		return resp;
	}

	public OrderCollectionResp exeOrdRuleLog(OrderCollectionResp resp, String out_tid) {
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String max_num = cacheUtil.getConfigInfo("EXEORDRULELOG_MAXNUM");
		for (int i = 0; i < Integer.parseInt(max_num); i++) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String sql = "select t.exe_result,t.result_msg from es_rule_exe_log t where t.rule_id='201410208505000023' and t.obj_id in (select order_id from es_order_ext where out_tid='"
					+ out_tid + "')";
			logger.info(sql);
			List list = this.baseDaoSupport.queryForList(sql, null);
			if (list.size() > 0) {
				Map map = (Map) list.get(0);
				String exe_result = Const.getStrValue(map, "exe_result");
				if (!"0".equals(exe_result)) {
					resp.setError_code(ConstsCore.ERROR_FAIL);
					resp.setError_msg(Const.getStrValue(map, "result_msg"));
					resp.setError_stack_msg(Const.getStrValue(map, "result_msg"));
					break;
				} else {
					resp.setError_code(ConstsCore.ERROR_SUCC);
					resp.setError_msg("标准化成功");
					break;
				}
			} else {
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("标准化方案执行未完成或失败");
				resp.setError_stack_msg("标准化方案执行未完成或失败");
			}
		}

		return resp;

	}

	@Override
	public MobileBusiCtnStandardResp mobileBusiCtnStandard(MobileBusiCtnStandardReq req) {
		MobileBusiCtnStandardResp resp = new MobileBusiCtnStandardResp();
		String co_id = req.getBase_co_id();

		// 1.前置校验开关
		resp = (MobileBusiCtnStandardResp) validatePreCheck(req, resp);

		// 2.订单收单本地化校验
		long val_start = System.currentTimeMillis();
		try {
			resp = MobileBusiCtnUtil.orderStandardValidate(req);
			// 校验失败调用异常单方案
			if (ConstsCore.ERROR_FAIL.equals(resp.getError_code())) {
				resp.setError_stack_msg("队列id:" + co_id + resp.getError_msg());
				this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
				return resp;
			}
		} catch (Exception e) {
			String error_msg = CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("订单收单本地化校验失败");
			resp.setError_stack_msg("队列id:" + co_id + error_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			return resp;
		}
		long val_end = System.currentTimeMillis();
		logger.info("本地校验耗时：" + (val_end - val_start));
		// 3.进行标准化
		OrderStandardPushResp orderStandardPushResp = new OrderStandardPushResp();
		try {
			orderStandardPushResp = saveOrder(resp.getOut_order_List());
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("标准化成功");
			List orderIdList = this.getOrderIdList(orderStandardPushResp.getOrderAddRespList());
			// 4.1标准化成功调用正常单标准化方案
			this.exeOrd(orderIdList);// 标准化成功执行正常单方案
			// 写入主机环境日志
			this.insertSysParams(orderIdList);
		} catch (Exception e) {
			e.printStackTrace();
			String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("标准化失败");
			resp.setError_stack_msg(error_stack_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			// 4.2 标准化失败调用异常单方案
			this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
			// 4.3 标准化失败写入outError表
			if (resp.getOut_order_List().size() > 0) {
				insertOuterError(resp.getOut_order_List());
			}
			return resp;
		}
		return resp;
	}

	/**
	 * 是否取消前置校验判断
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public ZteResponse validatePreCheck(ZteRequest<ZteResponse> req, ZteResponse resp) {
		String co_id = req.getBase_co_id();
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String is_pre_check_run = "0";// 默认为0，取消前置校验
		is_pre_check_run = cacheUtil.getConfigInfo(EcsOrderConsts.IS_PRE_CHECK_RUN);// 是否取消前置校验
																					// 0：关闭
																					// 1：开启
		// 默认配置在es_config_info为0，关闭前置校验，如需要打开需要配置为1
		if ("1".equals(is_pre_check_run)) {
			// 1.调用校验系统进行校验
			CheckResp checkResp = this.validateOrder(co_id, req.getFormat());// 调用校验系统进行校验
			// 校验失败直接返回 校验系统已写入异常单,这里不用重复写入异常单
			if (ConstsCore.ERROR_FAIL.equals(checkResp.getError_code())) {
				resp.setError_msg("标准化前置校验失败");
				resp.setError_stack_msg("队列id:" + co_id + checkResp.getError_msg());
				resp.setError_code(ConstsCore.ERROR_FAIL);
				return resp;
			}
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单下发接口标准化", summary = "订单下发接口标准化")
	public OrderDistributeCtnStandardResp orderDistributeCtnStandard(OrderDistributeCtnStandardReq req) {
		OrderDistributeCtnStandardResp resp = new OrderDistributeCtnStandardResp();
		Map<String, Object> intent = new HashMap<String, Object>();

		IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String sql = "";
		Map ORDER = (Map) req.getMapReq().get("ORDER");
		String ORDER_ID = Const.getStrValue(ORDER, "ORDER_ID");
		intent.put("intent_order_id", ORDER_ID);
		String ORDER_TYPE = Const.getStrValue(ORDER, "ORDER_TYPE");// 客户单类型3001蜂行动正式单回填3002蜂行动订单下发省分9999购物车下单
		String IN_MODE_CODE = Const.getStrValue(ORDER, "IN_MODE_CODE");// 接入渠道编码，参见附录3.4
		intent.put("IN_MODE_CODE", IN_MODE_CODE);
		String BUSI_CUST_ID = Const.getStrValue(ORDER, "BUSI_CUST_ID");// 业务办理客户

		String MEMBER_ID = Const.getStrValue(ORDER, "MEMBER_ID");// 会员号
		intent.put("MEMBER_ID", MEMBER_ID);
		String CHANNEL_ID = Const.getStrValue(ORDER, "CHANNEL_ID");// 渠道编码
		intent.put("channel_id", CHANNEL_ID);
		String CHANNEL_TYPE = Const.getStrValue(ORDER, "CHANNEL_TYPE");// 渠道类型
		intent.put("channeltype", CHANNEL_TYPE);
		String STORE_ID = Const.getStrValue(ORDER, "STORE_ID");// 门店编码
		intent.put("STORE_ID", STORE_ID);
		String CREATE_DATE = Const.getStrValue(ORDER, "CREATE_DATE");// 下单时间
		intent.put("create_time", CREATE_DATE);
		String PAY_TAG = Const.getStrValue(ORDER, "PAY_TAG");// 支付标识：0未支付,1已支付,9无需支付
		intent.put("pay_tag", PAY_TAG);

		if (null != ORDER.get("ORDER_ATTR")) {
			List<Map> ORDER_ATTR = (List) ORDER.get("ORDER_ATTR");// 客户单属性信息
			for (Map map : ORDER_ATTR) {
				String ATTR_CODE = Const.getStrValue(map, "ATTR_CODE");// 属性编码
				String ATTR_VALUE = Const.getStrValue(map, "ATTR_VALUE");// 属性值
				String ATTR_VALUE_NAME = Const.getStrValue(map, "ATTR_VALUE_NAME");// 属性值名称
				String START_DATE = Const.getStrValue(map, "START_DATE");// 属性开始时间
				String END_DATE = Const.getStrValue(map, "END_DATE");// 属性结束时间
				String ACTION = Const.getStrValue(map, "ACTION");// 操作标志0：新增1：删除2：修改
			}
		}
		if (null != ORDER.get("DEVELOPER_INFO")) {
			Map DEVELOPER_INFO = (Map) ORDER.get("DEVELOPER_INFO");// 发展人信息
			String DEVELOP_TYPE = Const.getStrValue(DEVELOPER_INFO, "DEVELOP_TYPE");// 发展类型（0：用户；1：商品；2：能人）
			String DEVELOP_EPARCHY_CODE = Const.getStrValue(DEVELOPER_INFO, "DEVELOP_EPARCHY_CODE");// 发展人地市
			String DEVELOP_DEPART_NAME = Const.getStrValue(DEVELOPER_INFO, "DEVELOP_ DEPART_NAME");// 发展人所在部门名称
			String DEVELOP_DEPART_ID = Const.getStrValue(DEVELOPER_INFO, "DEVELOP_DEPART_ID");// 发展人所在部门编码
			String top_share_userid = Const.getStrValue(DEVELOPER_INFO, "DEVELOP_DEPART_ID");// 发展人所在部门编码
			String DEVELOP_STAFF_ID = Const.getStrValue(DEVELOPER_INFO, "DEVELOP_STAFF_ID");// 发展员工
			String seed_user_id = Const.getStrValue(DEVELOPER_INFO, "DEVELOP_STAFF_ID");// 发展员工
			String DEVELOP_STAFF_NAME = Const.getStrValue(DEVELOPER_INFO, "DEVELOP_STAFF_NAME");// 发展人名称
			String DEVELOP_DATE = Const.getStrValue(DEVELOPER_INFO, "DEVELOP_DATE");// 发展时间
			String DEVELOP_MANAGER_ID = Const.getStrValue(DEVELOPER_INFO, "DEVELOP_MANAGER_ID");// 发展人经理编码
			String DEVELOP_MANAGER_NAME = Const.getStrValue(DEVELOPER_INFO, "DEVELOP_MANAGER_NAME");// 发展人经理名称
			String DEVELOP_NICK_ID = Const.getStrValue(DEVELOPER_INFO, "DEVELOP_NICK_ID");// 发展人别名编码
			String DEVELOP_NICK_NAME = Const.getStrValue(DEVELOPER_INFO, "DEVELOP_NICK_NAME");// 发展人别名名称

			intent.put("DEVELOP_TYPE", DEVELOP_TYPE);// 发展类型（0：用户；1：商品；2：能人）
			intent.put("DEVELOP_EPARCHY_CODE", DEVELOP_EPARCHY_CODE);// 发展人地市
			intent.put("develop_point_name", DEVELOP_DEPART_NAME);// 发展人所在部门名称
			intent.put("develop_point_code", DEVELOP_DEPART_ID);// 发展人所在部门编码
			intent.put("develop_code", DEVELOP_STAFF_ID);// 发展员工
			intent.put("top_share_userid", top_share_userid);
			intent.put("seed_user_id", seed_user_id);// 
			intent.put("develop_name", DEVELOP_STAFF_NAME);// 发展人名称
			intent.put("DEVELOP_DATE", DEVELOP_DATE);// 发展时间
			intent.put("DEVELOP_MANAGER_ID", DEVELOP_MANAGER_ID);// 发展人经理编码
			intent.put("DEVELOP_MANAGER_NAME", DEVELOP_MANAGER_NAME);// 发展人经理名称
			intent.put("DEVELOP_NICK_ID", DEVELOP_NICK_ID);// 发展人别名编码
			intent.put("DEVELOP_NICK_NAME", DEVELOP_NICK_NAME);// 发展人别名名称
		}
		if (null != ORDER.get("COMMON_INFO")) {
			List<Map> COMMON_INFO = (List) ORDER.get("COMMON_INFO");// 公共信息
			for (Map COMMON : COMMON_INFO) {
				String CUST_TYPE = Const.getStrValue(COMMON, "CUST_TYPE");// 新老客户标识,0：新增客户,1：老客户
				String DEPART_ID = Const.getStrValue(COMMON, "DEPART_ID");// 操作部门
				String STAFF_ID = Const.getStrValue(COMMON, "STAFF_ID");// 操作员工
				String CITY_CODE = Const.getStrValue(COMMON, "CITY_CODE");// 员工归属区县
				String EPARCHY_CODE = Const.getStrValue(COMMON, "EPARCHY_CODE");// 员工归属地市
				String PROVINCE_CODE = Const.getStrValue(COMMON, "PROVINCE_CODE");// 员工归属省分
				String VALET_STAFF = Const.getStrValue(COMMON, "VALET_STAFF");// 代客下单员工

				intent.put("deal_office_id", DEPART_ID);// 操作部门
				intent.put("deal_operator", STAFF_ID);// 操作员工
			}
		}
		if (null != ORDER.get("FEEPAY_INFO")) {
			List<Map> FEEPAY_INFO = (List) ORDER.get("FEEPAY_INFO");// 支付
			for (Map FEEPAY : FEEPAY_INFO) {
				String PAY_ID = Const.getStrValue(FEEPAY, "PAY_ID");// 支付流水
				String PAY_MONEY_CODE = Const.getStrValue(FEEPAY, "PAY_MONEY_CODE");// 支付方式，参见附录3.10
				String MONEY = Const.getStrValue(FEEPAY, "MONEY");// 支付金额
				String PAY_CHANNEL = Const.getStrValue(FEEPAY, "PAY_CHANNEL");// 支付渠道，参见附录3.11
				String FEE_STAFF_ID = Const.getStrValue(FEEPAY, "FEE_STAFF_ID");// 收费员工
				String FEE_DEPART_ID = Const.getStrValue(FEEPAY, "FEE_DEPART_ID");// 收费部门
				String PAY_DATE = Const.getStrValue(FEEPAY, "PAY_DATE");// 支付日期
			}
		}
		if (null != ORDER.get("CUST_INFO")) {
			Map CUST_INFO = (Map) ORDER.get("CUST_INFO");// 办理客户信息
			String CUST_TYPE = Const.getStrValue(CUST_INFO, "CUST_TYPE");// 新老客户标识,0：新增客户,1：老客户
			String CUST_ID = Const.getStrValue(CUST_INFO, "CUST_ID");// 用于继承老客户
			intent.put("CUST_ID", CUST_ID);// 用于继承老客户
			String CUST_NAME = Const.getStrValue(CUST_INFO, "CUST_NAME");// 客户名称
			intent.put("customer_name", CUST_NAME);// 客户名称
			intent.put("ACCEPTNAME", CUST_NAME);// 客户名称
			String SEX = Const.getStrValue(CUST_INFO, "SEX");// 性别，0：女 1：男
			intent.put("CERT_SEX", SEX);//
			String CUST_PHONE = Const.getStrValue(CUST_INFO, "CUST_PHONE");// 客户电话
			intent.put("CUST_TEL", CUST_PHONE);// 客户电话
			String POST_ADDRESS="";
			//String POST_ADDRESS = Const.getStrValue(CUST_INFO, "POST_ADDRESS");// 通讯地址
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
	        String mode_print = cacheUtil.getConfigInfo("DELIVER_INFO_inf");//
	        if(!StringUtil.isEmpty(mode_print)&&"1".equals(mode_print)){//避免出现问题，先加开关试试
	            if (null != ORDER.get("DELIVER_INFO")) {
	                Map DELIVER_INFO = (Map) ORDER.get("DELIVER_INFO");// 交付信息
	                POST_ADDRESS = Const.getStrValue(DELIVER_INFO, "POST_ADDRESS");// 详细地址
	            }
	        }else{
	                POST_ADDRESS = Const.getStrValue(CUST_INFO, "POST_ADDRESS");// 通讯地址
	        }
			intent.put("ship_addr", POST_ADDRESS);// 联系地址
			intent.put("ACCEPTADDR", POST_ADDRESS);// 联系地址
			String PSPT_ID = Const.getStrValue(CUST_INFO, "PSPT_ID");// 证件id
			intent.put("cert_num", PSPT_ID);// 证件号码
			intent.put("ACCEPTCERT", PSPT_ID);// 证件号码
			String PSPT_TYPE_CODE = Const.getStrValue(CUST_INFO, "PSPT_TYPE_CODE");// 证件类型
			intent.put("cert_type", "SFZ18");// 证件类型
			String PSPT_END_DATE = Const.getStrValue(CUST_INFO, "PSPT_END_DATE");// 证件有效期
			intent.put("CERT_EXPIRE_DATE", PSPT_END_DATE);
			String PSPT_ADDR = Const.getStrValue(CUST_INFO, "PSPT_ADDR");// 证件地址
			intent.put("CERT_ADDR", PSPT_ADDR);
			String CONTACT_NAME = Const.getStrValue(CUST_INFO, "CONTACT_NAME");// 联系人（不能全数字）
			intent.put("ship_name", CONTACT_NAME);// 联系人（不能全数字）
			String CONTACT_PHONE = Const.getStrValue(CUST_INFO, "CONTACT_PHONE");// 联系人电话>6位
			intent.put("ship_tel", CONTACT_PHONE);// 联系人电话>6位
			String CHECK_TAG = Const.getStrValue(CUST_INFO, "CHECK_TAG");// 认证类型：
			String ACCT_INFO = Const.getStrValue(CUST_INFO, "ACCT_INFO");// 账户信息

		}
		if (null != ORDER.get("DELIVER_INFO")) {
			Map DELIVER_INFO = (Map) ORDER.get("DELIVER_INFO");// 交付信息
			String POST_NAME = Const.getStrValue(DELIVER_INFO, "POST_NAME");// 收件人姓名
			String POST_CERT_TYPE = Const.getStrValue(DELIVER_INFO, "POST_CERT_TYPE");// 收件人证件类型，参见附录3.13
			String POST_CERT_ID = Const.getStrValue(DELIVER_INFO, "POST_CERT_ID");// 收件人证件号码
			String POST_MOBILE_1 = Const.getStrValue(DELIVER_INFO, "POST_MOBILE_1");// 收件人证件联系电话1
			String POST_MOBILE_2 = Const.getStrValue(DELIVER_INFO, "POST_MOBILE_2");// 收件人证件联系电话2
			String POST_PROVINCE = Const.getStrValue(DELIVER_INFO, "POST_PROVINCE");// 收件省
			String POST_CITY = Const.getStrValue(DELIVER_INFO, "POST_CITY");// 收件市
			String POST_DIST = Const.getStrValue(DELIVER_INFO, "POST_DIST");// 收件区县
			String POST_STREET = Const.getStrValue(DELIVER_INFO, "POST_STREET");// 收件街道
			String POST_ADDRESS = Const.getStrValue(DELIVER_INFO, "POST_ADDRESS");// 详细地址
			String POST_REMARK = Const.getStrValue(DELIVER_INFO, "POST_REMARK");// 备注
			String POST_EXPECT_TIME = Const.getStrValue(DELIVER_INFO, "POST_EXPECT_TIME");// 期望送达时间
			String POST_TYPE = Const.getStrValue(DELIVER_INFO, "POST_TYPE");// 配送方式10:物流，20：直提；30：自提
			String SELF_PICKUP_CODE = Const.getStrValue(DELIVER_INFO, "SELF_PICKUP_CODE");// 自提、直提点编码
			String SELF_PICKUP_NAME = Const.getStrValue(DELIVER_INFO, "SELF_PICKUP_NAME");// 自提、直提点名称
			String POST_COMPANY = Const.getStrValue(DELIVER_INFO, "POST_COMPANY");// 快递公司名称

		}
		if (null != ORDER.get("ORDER_LINE")) {
			List<Map> ORDER_LINE = (List) ORDER.get("ORDER_LINE");// 订单行信息
			for (Map LINE : ORDER_LINE) {
				String ORDER_LINE_ID = Const.getStrValue(LINE, "ORDER_LINE_ID");// 订单行标识
				String SERIAL_NUMBER = Const.getStrValue(LINE, "SERIAL_NUMBER");// 业务受理号码
				intent.put("ACC_NBR", SERIAL_NUMBER);
				String TRADE_TYPE_CODE = Const.getStrValue(LINE, "TRADE_TYPE_CODE");// 业务类型参见附录3.8
				String NET_TYPE_CODE = Const.getStrValue(LINE, "NET_TYPE_CODE");// 电信类型，参见附录3.12
				String CANCEL_TAG = Const.getStrValue(LINE, "CANCEL_TAG");// 返销标识，参加附录3.14
				String IS_COMBOFFER_ORDERLINE = Const.getStrValue(LINE, "IS_COMBOFFER_ORDERLINE");// 是否组合商品订单行,1:组合商品或单商品，0子商品
				String ORDERLINE_REL_TYPE = Const.getStrValue(LINE, "ORDERLINE_REL_TYPE");// 订单行关系类型01：单商品02：组合商品03：合约商品
				String BELONG_COMBOFFER_ORDERLINE = Const.getStrValue(LINE, "BELONG_COMBOFFER_ORDERLINE");// 子商品订单归属主商品订单行
				if (null != LINE.get("PIC_DATAS")) {
					Map PIC_DATAS = (Map) ORDER.get("PIC_DATAS");
					String PIC_URL1 = Const.getStrValue(PIC_DATAS, "PIC_URL1");// 图片地址
					String PIC_URL2 = Const.getStrValue(PIC_DATAS, "PIC_URL2");// 图片地址
					String PIC_URL3 = Const.getStrValue(PIC_DATAS, "PIC_URL3");// 图片地址
					String PIC_URL4 = Const.getStrValue(PIC_DATAS, "PIC_URL4");// 图片地址
					String PIC_URL5 = Const.getStrValue(PIC_DATAS, "PIC_URL5");// 图片地址
					String PIC_URL6 = Const.getStrValue(PIC_DATAS, "PIC_URL6");// 图片地址
					String PIC_URL7 = Const.getStrValue(PIC_DATAS, "PIC_URL7");// 图片地址
					String PIC_URL8 = Const.getStrValue(PIC_DATAS, "PIC_URL8");// 图片地址
					String PIC_URL9 = Const.getStrValue(PIC_DATAS, "PIC_URL9");// 图片地址
				}
				if (null != LINE.get("NETWORKRES_INFO")) {
					List<Map> NETWORKRES_INFO = (List) LINE.get("NETWORKRES_INFO");// 固网资源信息
					for (Map INFO : NETWORKRES_INFO) {
						String EPARCHY_CODE = Const.getStrValue(INFO, "EPARCHY_CODE");// 地市编码
					}
				}
				if (null != LINE.get("NETWORKRES_BOOK")) {
					List<Map> NETWORKRES_BOOK = (List) LINE.get("NETWORKRES_BOOK");// 预约装机信息
					for (Map BOOK : NETWORKRES_BOOK) {
					}
				}
				if (null != LINE.get("USER_INFO")) {
					List<Map> USER_INFO = (List) LINE.get("USER_INFO");// 用户信息
					for (Map USER : USER_INFO) {
						String USER_TYPE = Const.getStrValue(USER, "EPARCHY_CODE");// 用户类型,0：新用户
																					// 1：老用户
						String USER_ID = Const.getStrValue(USER, "EPARCHY_CODE");// 老用户标识
						String USER_PASSWD = Const.getStrValue(USER, "EPARCHY_CODE");// 用户密码
					}
				}
				if (null != LINE.get("ORDER_LINE_ATTR")) {
					List<Map> ORDER_LINE_ATTR = (List) LINE.get("ORDER_LINE_ATTR");// 订单行属性
					for (Map ATTR : ORDER_LINE_ATTR) {
					}
				}
				if (null != LINE.get("OFFER_INFO")) {
					List<Map> OFFER_INFO = (List) LINE.get("OFFER_INFO");// 商品节点
					for (Map OFFER : OFFER_INFO) {
						String OFFER_ID = Const.getStrValue(OFFER, "OFFER_ID");// 商品标识
						sql = "select t.* from es_dc_public_ext t where t.stype='DIC_FXD_GOODS'";
						list = this.baseDaoSupport.queryForList(sql);
						// list = dcPublicCache.getList("DIC_FXD_GOODS");

						for (Map<String, String> map : list) {
							if (OFFER_ID.equals(map.get("pkey"))) {
								OFFER_ID = map.get("pname");
								break;
							}
						}
						intent.put("goods_id", OFFER_ID);// 商品编码，对应新零售产商品中心的商品编码
						intent.put("prod_offer_code", OFFER_ID);
						String OFFER_NAME = Const.getStrValue(OFFER, "OFFER_NAME");// 商品名称
						intent.put("goods_name", OFFER_NAME);// 商品名称
						intent.put("prod_offer_name", OFFER_NAME);// 商品名称
						String BRAND_CODE = Const.getStrValue(OFFER, "BRAND_CODE");// 商品品牌
						String BRAND_NAME = Const.getStrValue(OFFER, "BRAND_NAME");// 商品品牌名称
						String MODEL_CODE = Const.getStrValue(OFFER, "MODEL_CODE");// 商品型号
						String MODEL_NAME = Const.getStrValue(OFFER, "MODEL_NAME");// 商品型号名称
						String OFFER_DIVIDE = Const.getStrValue(OFFER, "OFFER_DIVIDE");// 商品划分
						String OFFER_TYPE_CODE = Const.getStrValue(OFFER, "OFFER_TYPE_CODE");// 商品分类
						String BELONG_PLATFORM = Const.getStrValue(OFFER, "BELONG_PLATFORM");// 商品归属商家
						String BELONG_PLATFORM_NAME = Const.getStrValue(OFFER, "BELONG_PLATFORM_NAME");// 商品归属名称
						String OFFER_SUPPLIER = Const.getStrValue(OFFER, "OFFER_SUPPLIER");// 商品供应商
						String OFFER_SUPPLIER_NAME = Const.getStrValue(OFFER, "OFFER_SUPPLIER_NAME");// 商品供应商名称
						String SETT_PRICE = Const.getStrValue(OFFER, "SETT_PRICE");// 结算价格（单位分）
						String SELL_PRICE = Const.getStrValue(OFFER, "SELL_PRICE");// 销售价格（单位分）
						String DISCNT_TYPE = Const.getStrValue(OFFER, "DISCNT_TYPE");// 首月资费方式：01：标准，02：全月，03：减半
						intent.put("offer_eff_type", DISCNT_TYPE);
						String COUNT = Const.getStrValue(OFFER, "COUNT");// 商品数量
						intent.put("GOODS_NUM", COUNT);
						String START_DATE = Const.getStrValue(OFFER, "START_DATE");// 商品生效时间
						String END_DATE = Const.getStrValue(OFFER, "END_DATE");// 商品失效时间
						String RS_ID = Const.getStrValue(OFFER, "RS_ID");// 商品sku
						String IS_MAIN_OFFER = Const.getStrValue(OFFER, "IS_MAIN_OFFER");// 是否组合商品，0单商品
																							// 或子商品；1组合商品
						String PARENT_OFFER = Const.getStrValue(OFFER, "PARENT_OFFER");// 子商品对应组合商品
						String ACTION = Const.getStrValue(OFFER, "ACTION");// 操作标志
																			// 0：新增
																			// 1：删除，2：修改
						String OFFER_ELEMENT = Const.getStrValue(OFFER, "OFFER_ELEMENT");// 商品下元素信息
						String PRODUCT_ID = Const.getStrValue(OFFER, "PRODUCT_ID");// 归属系统产品标识
						if (null != OFFER.get("PROM_INFO")) {
							List<Map> PROM_INFO = (List) OFFER.get("PROM_INFO");// 活动节点
						}
						if (null != OFFER.get("COMM_ATTR_INFO")) {
							List<Map> COMM_ATTR_INFO = (List) OFFER.get("COMM_ATTR_INFO");// 商品属性节点
						}
					}
				}
				if (null != LINE.get("MPHONE_INFO")) {
					Map MPHONE_INFO = (Map) LINE.get("MPHONE_INFO");// 号码节点
					SERIAL_NUMBER = Const.getStrValue(MPHONE_INFO, "SERIAL_NUMBER");//
					if (!StringUtils.isEmpty(SERIAL_NUMBER)) {
						intent.put("ACC_NBR", SERIAL_NUMBER);
					}
					String NUMBER_LEVER = Const.getStrValue(MPHONE_INFO, "NUMBER_LEVER");//号码等级
					intent.put("CLASSID", NUMBER_LEVER);// 
					String LIMIT_FEE = Const.getStrValue(MPHONE_INFO, "LIMIT_FEE");//月最低消费，单位分
					if (!StringUtils.isEmpty(LIMIT_FEE)) {
						int limit = Integer.parseInt(LIMIT_FEE);
						intent.put("LOWCOSTPRO", limit * 10);//
					}
					String STORE_FEE = Const.getStrValue(MPHONE_INFO, "STORE_FEE");//靓号预存，单位分
					if (!StringUtils.isEmpty(STORE_FEE)) {
						int store = Integer.parseInt(STORE_FEE);
						intent.put("PRE_FEE", store * 10);//
					}
					String NUM_PROVINCE = Const.getStrValue(MPHONE_INFO, "NUM_PROVINCE");// 36
					String AGREEMENT_MONTHS = Const.getStrValue(MPHONE_INFO, "AGREEMENT_MONTHS");// 协议月份
					String AGREEMEN_START = Const.getStrValue(MPHONE_INFO, "AGREEMEN_START");// 协议开始时间
					intent.put("TIMEDURPRO", AGREEMENT_MONTHS);// 
					intent.put("CONTRACT_MONTH", AGREEMENT_MONTHS);// 
					String IS_OLD_USER = Const.getStrValue(MPHONE_INFO, "IS_OLD_USER");//主卡是否老用户
					String MAIN_NUMBER_TAG = Const.getStrValue(MPHONE_INFO, "MAIN_NUMBER_TAG");//主卡是否老用户
					String MAIN_NUMBER = Const.getStrValue(MPHONE_INFO, "MAIN_NUMBER");//主卡是否老用户
					intent.put("MAINNUMBER", MAIN_NUMBER);// 
					if ("36".equals(NUM_PROVINCE)) {
						intent.put("order_province_code", "330000");// 省份编码转换
					} else {
						intent.put("order_province_code", NUM_PROVINCE);// 省份编码转换
					}
					String NUM_CITY = Const.getStrValue(MPHONE_INFO, "NUM_CITY");// 0571
					// list = dcPublicCache.getList("DIC_FXD_CITY_CODE");
					sql = "select t.* from es_dc_public_ext t where t.stype='DIC_FXD_CITY_CODE'";
					list = this.baseDaoSupport.queryForList(sql);
					// 正式环境只保留浙江省的地市，测试数据是全的
					for (Map<String, String> map : list) {
						if (NUM_CITY.equals(map.get("pkey"))) {
							NUM_CITY = map.get("pname");
							break;
						}
					}
					intent.put("order_city_code", NUM_CITY);// 地市编码转换
				}
				if (null != LINE.get("CARD_INFO")) {
					Map CARD_INFO = (Map) LINE.get("CARD_INFO");// 卡信息
				}
				if (null != LINE.get("TERMINAL_INFO")) {
					List<Map> TERMINAL_INFO = (List) LINE.get("TERMINAL_INFO");// 终端信息
					for (Map TERMINAL : TERMINAL_INFO) {
					}
				}
				if (null != LINE.get("FEE_INFO")) {
					List<Map> FEE_INFO = (List) LINE.get("FEE_INFO");// 费用信息
					for (Map FEE : FEE_INFO) {
					}
				}

			}
		}
		if (null != ORDER.get("INVOICE_INFO")) {
			List<Map> INVOICE_INFO = (List) ORDER.get("INVOICE_INFO");// 发票信息
			for (Map map : INVOICE_INFO) {
				String POST_COMPANY = Const.getStrValue(map, "POST_COMPANY");// 快递公司名称
				String TICKET_TYPE_CODE = Const.getStrValue(map, "POST_COMPANY");// 0-纸质发票1电子发票1-押金2-预存3-和打
				String TICKET_ID = Const.getStrValue(map, "POST_COMPANY");// 票据号码
				String TAX_NO = Const.getStrValue(map, "POST_COMPANY");// 税务本号
				String INVOICE_CHECK_CODE = Const.getStrValue(map, "POST_COMPANY");// 发票代码
				String INVOICE_HEADER = Const.getStrValue(map, "POST_COMPANY");// 电子发票抬头
				String RECEIVE_MESSAGE_NUM = Const.getStrValue(map, "POST_COMPANY");// 接收号码
				String BUYER_TAXPAYER_ID = Const.getStrValue(map, "POST_COMPANY");// 纳税人识别号
				String BUYER_ADD = Const.getStrValue(map, "POST_COMPANY");// 公司地址
				String BUYER_BANK_ACCOUNT = Const.getStrValue(map, "POST_COMPANY");// 公司银行账户
				String RECEIVE_MESSAGE_EMAIL = Const.getStrValue(map, "POST_COMPANY");// 接收邮箱
				String INVOICE_URL = Const.getStrValue(map, "POST_COMPANY");// 地址url
				String FEE = Const.getStrValue(map, "POST_COMPANY");// 发票金额，单位分
				String MONTHLY_TAG = Const.getStrValue(map, "POST_COMPANY");// 分月打印标识0：否1：是
				String FEEITEM_CODE = Const.getStrValue(map, "POST_COMPANY");// 费用类型
				String FEEITEM_NAME = Const.getStrValue(map, "POST_COMPANY");// 费用项名称
				String OLD_TICKET_ID = Const.getStrValue(map, "POST_COMPANY");// 原发票号码
				String IS_PRINT = Const.getStrValue(map, "POST_COMPANY");// 是否已打印0：未打印1：已打印2已红冲
				String REMARK = Const.getStrValue(map, "POST_COMPANY");// 备注

			}
		}

		String order_id = cacheUtil.getConfigInfo("ORDER_PREX") + this.baseDaoSupport.getSequences("s_es_order");

		//不可修改字段,发展人/点编码不可修改
		intent.put("IS_NO_MODIFY", cacheUtil.getConfigInfo("IS_NO_MODIFY_100104"));
		
		intent.put("order_id", order_id);
		intent.put("status", "01");
		// intent.put("req", req.getMapReq().toString());
		intent.put("source_system", "AS");
		intent.put("source_system_type", "100104");// 总部蜂行动
		intent.put("source_from", ManagerUtils.getSourceFrom());

		String gonghao = cacheUtil.getConfigInfo("PRO_USER_ID_100104");
		intent.put("lock_id", gonghao);
		intent.put("lock_name", "意向单省中台虚拟工号");

		baseDaoSupport.insert("es_order_intent", intent);

		Map<String, Object> queue_his = new HashMap<String, Object>();
		queue_his.put("co_id", this.baseDaoSupport.getSequences("s_es_order"));
		queue_his.put("service_code", "zte.net.orderstd.OrderDistributeCtnStandard");
		queue_his.put("action_code", "A");
		queue_his.put("status", "XYCG");
		queue_his.put("created_date", new Date());
		queue_his.put("order_id", order_id);
		queue_his.put("object_id", ORDER_ID);
		queue_his.put("contents", req.getMapReq().toString());
		queue_his.put("source_from", ManagerUtils.getSourceFrom());
		baseDaoSupport.insert("es_order_queue_his", queue_his);

		resp.setRESP_CODE("1");
		resp.setRESP_DESC("成功");
		Map<String, String> DATA = new HashMap<String, String>();
		DATA.put("PROV_ORDER_ID", order_id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DATA.put("PROV_ORDER_DATE", sdf.format(new Date()));// 2018-06-29
															// 14:32:57
		resp.setDATA(DATA);
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "新零售商品预定接口标准化", summary = "新零售商品预定接口标准化")
	public OrderPreCreateCtnStandardResp orderPreCreateCtnStandard(OrderPreCreateCtnStandardReq req) {
		OrderPreCreateCtnStandardResp resp = new OrderPreCreateCtnStandardResp();
		try {
			Map mapReqHead = (Map) req.getMapReq().get("REQ_HEAD");
			String TOUCH_ORDER_ID = Const.getStrValue(mapReqHead, "TOUCH_ORDER_ID");
			String order_id = exist(TOUCH_ORDER_ID);
			DATA data = new DATA();
			if (!StringUtils.isEmpty(order_id)) {
				resp.setCODE("1003");
				resp.setDESC("单号重复");
				data.setORDER_ID(order_id);
				resp.setDATA(data);
				return resp;
			} else {
				order_id = cacheUtil.getConfigInfo("ORDER_PREX") + this.baseDaoSupport.getSequences("s_es_order");
			}
			Map<String, Object> intent = new HashMap<String, Object>();
			intent.put("order_id", order_id);
			// REQ_HEAD
			intent.put("intent_order_id", TOUCH_ORDER_ID);
			intent.put("IN_MODE_CODE", Const.getStrValue(mapReqHead, "IN_MODE_CODE"));// 订单接入渠道类型，参考3.1.接入渠道
			String ORDER_PROVINCE = Const.getStrValue(mapReqHead, "ORDER_PROVINCE");
			if ("36".equals(ORDER_PROVINCE)) {
				intent.put("order_province_code", "330000");// 省份编码转换
			} else {
				intent.put("order_province_code", ORDER_PROVINCE);// 省份编码转换
			}
			IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
			DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);

			String sql = "select t.* from es_dc_public_ext t where t.stype='DIC_BLD_CITY_CODE'";
			List<Map<String, String>> list = this.baseDaoSupport.queryForList(sql);
			// 正式环境只保留浙江省的地市，测试数据是全的
			String ORDER_CITY = Const.getStrValue(mapReqHead, "ORDER_CITY");
			for (Map<String, String> map : list) {
				if (ORDER_CITY.equals(map.get("pkey"))) {
					ORDER_CITY = map.get("codea");
					break;
				}
			}
			intent.put("order_city_code", ORDER_CITY);// 地市编码转换
			// 县分
			// intent.put("order_county_code", Const.getStrValue(mapReqHead,
			// ""));//编码转换
			// intent.put("county_id", Const.getStrValue(mapReqHead, ""));//编码转换
			intent.put("STORE_ID", Const.getStrValue(mapReqHead, "STORE_ID"));// 门店
			intent.put("STORE_TYPE", Const.getStrValue(mapReqHead, "STORE_TYPE"));// 门店类型：01京东便利店
			intent.put("deal_office_id", Const.getStrValue(mapReqHead, "DEPART_ID"));// 操作部门
			intent.put("deal_operator", Const.getStrValue(mapReqHead, "TRADE_STAFFID"));// 操作员工
			intent.put("CHANNEL_ID", Const.getStrValue(mapReqHead, "CHANNEL_ID"));// 渠道编码
			intent.put("channeltype", Const.getStrValue(mapReqHead, "CHANNEL_TYPE"));// 渠道类型
			intent.put("create_time", Const.getStrValue(mapReqHead, "ORDER_TIME"));// 销售时间YYYY-MM-DDhh24:mi:ss
			// REQ_DATA
			Map mapReqData = (Map) req.getMapReq().get("REQ_DATA");
			intent.put("MEMBER_ID", Const.getStrValue(mapReqData, "MEMBER_ID"));// 会员号
			// 暂时按单节点处理
			List mapReqDataCommObject = (List) mapReqData.get("COMM_OBJECT");
			Map mapComm = (Map) mapReqDataCommObject.get(0);
			String COMM_ID = Const.getStrValue(mapComm, "COMM_ID");
			String goods_id = COMM_ID;
			// list = dcPublicCache.getList("DIC_BLD_GOODS");
			sql = "select t.* from es_dc_public_ext t where t.stype='DIC_BLD_GOODS'";
			list = this.baseDaoSupport.queryForList(sql);
			for (Map<String, String> map : list) {
				if (COMM_ID.equals(map.get("pkey"))) {
					goods_id = map.get("pname");
					break;
				}
			}
			intent.put("goods_id", goods_id);// 商品编码，对应新零售产商品中心的商品编码
			intent.put("prod_offer_code", goods_id);
			intent.put("goods_name", Const.getStrValue(mapComm, "COMM_NAME"));// 商品名称
			intent.put("prod_offer_name", Const.getStrValue(mapComm, "COMM_NAME"));// 商品名称
			intent.put("IMEI", Const.getStrValue(mapComm, "IMEI"));// 终端串码
			intent.put("goods_num", Const.getStrValue(mapComm, "COMM_COUNT"));// 商品数量
			intent.put("BOOKING_NUM_TAG", Const.getStrValue(mapComm, "BOOKING_NUM_TAG"));// 预约号的规则（1-11位号码，2-号头匹配，3-号位匹配，4-中间匹配）
			intent.put("acc_nbr", Const.getStrValue(mapComm, "BOOKING_NUM"));// 号码

			if (mapReqData.get("CUST_INFO") != null) {
				Map mapReqDataCust = (Map) mapReqData.get("CUST_INFO");
				intent.put("customer_name", Const.getStrValue(mapReqDataCust, "CUST_NAME"));// 客户名称
				intent.put("ship_name", Const.getStrValue(mapReqDataCust, "CUST_NAME"));// 客户名称
				intent.put("ship_tel", Const.getStrValue(mapReqDataCust, "CONTACT_PHONE"));// 联系人电话
				intent.put("ship_addr", Const.getStrValue(mapReqDataCust, "POST_ADDRESS"));// 联系地址
				String CERT_TYPE = Const.getStrValue(mapReqDataCust, "CERT_TYPE");
				if (!StringUtils.isEmpty(CERT_TYPE)) {
					// list = dcPublicCache.getList("DIC_BLD_CERT_TYPE");
					sql = "select t.* from es_dc_public_ext t where t.stype='DIC_BLD_CERT_TYPE'";
					list = this.baseDaoSupport.queryForList(sql);
					String cert_type = CERT_TYPE;
					for (Map<String, String> map : list) {
						if (CERT_TYPE.equals(map.get("pkey"))) {
							cert_type = map.get("codea");
							break;
						}
					}
					intent.put("cert_type", cert_type);// 证件类型
				}
				intent.put("cert_num", Const.getStrValue(mapReqDataCust, "CERT_ID"));// 证件号码
			}
			// 暂时按单节点处理
			if (mapReqData.get("DEVELOPER_INFO") != null) {
				List mapReqDataDeveloper = (List) mapReqData.get("DEVELOPER_INFO");
				Map developer = (Map) mapReqDataDeveloper.get(0);
				intent.put("DEVELOP_TYPE", Const.getStrValue(developer, "DEVELOP_TYPE"));// 发展类型（0：用户；1：商品；2：能人）
				intent.put("DEVELOP_EPARCHY_CODE", Const.getStrValue(developer, "DEVELOP_EPARCHY_CODE"));// 发展人地市
				intent.put("develop_point_name", Const.getStrValue(developer, "DEVELOP_DEPART_NAME"));// 发展人所在部门名称
				intent.put("develop_point_code", Const.getStrValue(developer, "DEVELOP_DEPART_ID"));// 发展人所在部门编码
				intent.put("develop_code", Const.getStrValue(developer, "DEVELOP_STAFF_ID"));// 发展员工
				intent.put("top_share_userid", Const.getStrValue(developer, "DEVELOP_DEPART_ID"));
				intent.put("seed_user_id", Const.getStrValue(developer, "DEVELOP_STAFF_ID"));// 
				intent.put("develop_name", Const.getStrValue(developer, "DEVELOP_STAFF_NAME"));// 发展人名称
				intent.put("DEVELOP_DATE", Const.getStrValue(developer, "DEVELOP_DATE"));// 发展时间
				intent.put("DEVELOP_MANAGER_ID", Const.getStrValue(developer, "DEVELOP_MANAGER_ID"));// 发展人经理编码
				intent.put("DEVELOP_MANAGER_NAME", Const.getStrValue(developer, "DEVELOP_MANAGER_NAME"));// 发展人经理名称
				intent.put("DEVELOP_NICK_ID", Const.getStrValue(developer, "DEVELOP_NICK_ID"));// 发展人别名编码
				intent.put("DEVELOP_NICK_NAME", Const.getStrValue(developer, "DEVELOP_NICK_NAME"));// 发展人别名名称
			}
			
			//不可修改字段,发展人/点编码不可修改
			intent.put("IS_NO_MODIFY", cacheUtil.getConfigInfo("IS_NO_MODIFY_100101"));

			intent.put("source_from", ManagerUtils.getSourceFrom());
			intent.put("status", "01");
			intent.put("req", req.getMapReq().toString());
			intent.put("source_system", "AS");
			intent.put("source_system_type", "100101");// 暂定

			String gonghao = cacheUtil.getConfigInfo("PRO_USER_ID_100101");
			intent.put("lock_id", gonghao);
			intent.put("lock_name", "意向单省中台虚拟工号");

			baseDaoSupport.insert("es_order_intent", intent);

			resp.setCODE("0000");
			resp.setDESC("成功");
			data.setORDER_ID(order_id);
			resp.setDATA(data);
		} catch (Exception e) {
			resp.setCODE("2004");
			resp.setDESC("异常错误2：" + (e.getMessage() == null ? e.toString() : e.getMessage()));
			e.printStackTrace();
			logger.info(e, e);
		}

		return resp;
	}

	/**
	 * 判断重复订单
	 * 
	 * @param TOUCH_ORDER_ID
	 * @return
	 */
	public String exist(String TOUCH_ORDER_ID) {
		String sql = "select order_id from es_order_intent t where t.intent_order_id='" + TOUCH_ORDER_ID
				+ "' and t.source_from='" + ManagerUtils.getSourceFrom() + "'";
		String order_id = this.baseDaoSupport.queryForString(sql);
		return order_id;
	}

	@Override
	public StartWorkflowRsp doStartWorkflow(StdStartWorkflowReq req)
			throws Exception {
		StartWorkflowReq flowReq = new StartWorkflowReq();
		
		flowReq.setOrderIntent(req.getOrderIntent());
		flowReq.setCfg(req.getCfg());
        
        ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        StartWorkflowRsp flowRsp = client.execute(flowReq, StartWorkflowRsp.class);
        
		return flowRsp;
	}

	@Override
	public WorkflowMatchRsp doWorkflowMatch(StdWorkflowMatchReq req)
			throws Exception {
		
		WorkflowMatchReq matchReq = new WorkflowMatchReq();
		
		matchReq.setOrderIntent(req.getOrderIntent());
		matchReq.setCfg_type(req.getCfg_type());
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		WorkflowMatchRsp matchRsp = client.execute(matchReq, WorkflowMatchRsp.class);

		return matchRsp;
	}

    @Override
    public ChannelConvertQrySubResp channelConvert(StdChannelConvertReq req)  throws Exception {
        ChannelConvertQrySubReq channelSubReq = new ChannelConvertQrySubReq();
        channelSubReq.setOp_type(req.getOp_type());
        channelSubReq.setOp_value(req.getOp_value());
        ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        ChannelConvertQrySubResp Rsp = client.execute(channelSubReq, ChannelConvertQrySubResp.class);
        return Rsp;
    }
}
