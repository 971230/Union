package com.ztesoft.net.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hisun.crypt.mac.CryptUtilImpl;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.inf.communication.client.CommCaller;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.ecsord.params.ecaop.req.BroadbandOrderSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BroadbandPreSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.CardInfoGetBSSReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ChangeAppReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ChangeSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.DepositOrderReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ElectronicVolumeSendReq;
import com.ztesoft.net.ecsord.params.ecaop.req.FtthPreSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.FuKaPreOpenReq;
import com.ztesoft.net.ecsord.params.ecaop.req.GroupOrderSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.GuWangPreSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ObjectReplacePreSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderFormalSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderInfoUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.PreCommitBSSReq;
import com.ztesoft.net.ecsord.params.ecaop.req.QryBroadbandFeeReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WisdomHomePreSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WotvBroadbandBindReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WotvUserSubReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.BroadbandOrderSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BroadbandPreSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.CardInfoGetBSSResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ChangeAppResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ChangeSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.DepositSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ElectronicVolumeSendResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.FtthPreSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.FuKaPreOpenResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.GroupOrderSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.GuWangPreSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ObjectReplacePreSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.PreCommitBSSResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.QryBroadbandFeeResponse;
import com.ztesoft.net.ecsord.params.ecaop.resp.WisdomHomeSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WotvBroadbandBindResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WotvUserSubResp;
import com.ztesoft.net.ecsord.params.ecaop.vo.ElectronicVolumeSendBody;
import com.ztesoft.net.ecsord.params.ecaop.vo.ElectronicVolumeSendHeader;
import com.ztesoft.net.ecsord.params.ecaop.vo.ElectronicVolumeSendVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.WhiteCardInfoVO;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IOrdOpenAccountTacheManager;
import com.ztesoft.net.sqls.SF;

import consts.ConstsCore;
import params.ZteResponse;
import params.order.req.OrderActionLogReq;
import params.req.CrawlerAccountInfoReq;
import params.req.CrawlerDeliveryInfoReq;
import params.req.ModifyOpenAccountGoodsReq;
import params.req.OpenAccountSubmitOrderReq;
import params.req.ReAllotOrderReq;
import params.resp.CrawlerFeeInfo;
import params.resp.CrawlerResp;
import params.resp.OpenAccountSubmitOrderResp;
import util.EssOperatorInfoUtil;
import util.ThreadLocalUtil;
import util.Utils;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.bss.req.BSSAccountOfficialSubReq;
import zte.net.ecsord.params.bss.req.BSSAccountReq;
import zte.net.ecsord.params.bss.req.BSSOrderSubReq;
import zte.net.ecsord.params.bss.req.PreCommitReq;
import zte.net.ecsord.params.bss.resp.BSSAccountOfficialSubResp;
import zte.net.ecsord.params.bss.resp.PreCommitResp;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageSubProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderAdslBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemsAptPrintsBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPayBusiRequest;
import zte.net.ecsord.params.busi.req.OrderSubProductBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.busi.req.OrderWMSKeyInfoBusiRequest;
import zte.net.ecsord.params.ecaop.req.CardDataQryRequest;
import zte.net.ecsord.params.ecaop.req.CardDataQryRequestZJ;
import zte.net.ecsord.params.ecaop.req.CunFeeSongFee4GReq;
import zte.net.ecsord.params.ecaop.req.CunFeeSongFee4GReqZJ;
import zte.net.ecsord.params.ecaop.req.CunFeeSongFee4GSubmitReq;
import zte.net.ecsord.params.ecaop.req.CunFeeSongFee4GSubmitReqZJ;
import zte.net.ecsord.params.ecaop.req.MainViceOpenReq;
import zte.net.ecsord.params.ecaop.req.OldUserBuyApplyReq;
import zte.net.ecsord.params.ecaop.req.OldUserBuySubmitReq;
import zte.net.ecsord.params.ecaop.req.OldUserRenActivityReq;
import zte.net.ecsord.params.ecaop.req.OldUserRenActivitySubmitReq;
import zte.net.ecsord.params.ecaop.req.Open23to4ApplyReq;
import zte.net.ecsord.params.ecaop.req.OpenAccountSubmitReq;
import zte.net.ecsord.params.ecaop.req.OpenDealApplyReq;
import zte.net.ecsord.params.ecaop.req.OpenDealApplyReqZJ;
import zte.net.ecsord.params.ecaop.req.OpenDealSubmit1Req;
import zte.net.ecsord.params.ecaop.req.OpenDealSubmitReq;
import zte.net.ecsord.params.ecaop.req.OpenDealSubmitReqZJ;
import zte.net.ecsord.params.ecaop.req.ProdChangeApplyReq;
import zte.net.ecsord.params.ecaop.req.ReturnMachineApplyReq;
import zte.net.ecsord.params.ecaop.req.ReturnMachineSubmitReq;
import zte.net.ecsord.params.ecaop.req.UserJoinMainViceCardReq;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.AopBrdOpenAppReq;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_sub.AopBrdOpenSubReq;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyPackageElementVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyProductVo;
import zte.net.ecsord.params.ecaop.req.vo.ProdPackElementReqVo;
import zte.net.ecsord.params.ecaop.req.vo.ProductInfoReqVo;
import zte.net.ecsord.params.ecaop.resp.BSSAccountResponse;
import zte.net.ecsord.params.ecaop.resp.BSSOrderSubResponse;
import zte.net.ecsord.params.ecaop.resp.CardDataQryResponse;
import zte.net.ecsord.params.ecaop.resp.CommAopApplyResultMsg;
import zte.net.ecsord.params.ecaop.resp.CommAopApplyRsp;
import zte.net.ecsord.params.ecaop.resp.CommAopSubmitResultMsg;
import zte.net.ecsord.params.ecaop.resp.CommAopSubmitRsp;
import zte.net.ecsord.params.ecaop.resp.CunFeeSongFee4GResp;
import zte.net.ecsord.params.ecaop.resp.CunFeeSongFee4GSubmitResp;
import zte.net.ecsord.params.ecaop.resp.EmpIdEssIDResp;
import zte.net.ecsord.params.ecaop.resp.MainViceOpenResp;
import zte.net.ecsord.params.ecaop.resp.OldUserBuyApplyResp;
import zte.net.ecsord.params.ecaop.resp.OldUserBuySubmitResp;
import zte.net.ecsord.params.ecaop.resp.OldUserRenActivityResp;
import zte.net.ecsord.params.ecaop.resp.OldUserRenActivitySubmitResp;
import zte.net.ecsord.params.ecaop.resp.OpenAccountSubmitResp;
import zte.net.ecsord.params.ecaop.resp.OpenDealApplyResp;
import zte.net.ecsord.params.ecaop.resp.OpenDealSubmitResp;
import zte.net.ecsord.params.ecaop.resp.ProdChangeApplyResp;
import zte.net.ecsord.params.ecaop.resp.ReturnMachineApplyResp;
import zte.net.ecsord.params.ecaop.resp.ReturnMachineSubmitResp;
import zte.net.ecsord.params.ecaop.resp.UserJoinMainViceCardResp;
import zte.net.ecsord.params.ecaop.resp.vo.AcceptanceParamVo;
import zte.net.ecsord.params.ecaop.resp.vo.BSSFeeInfoRspVo;
import zte.net.ecsord.params.ecaop.resp.vo.FeeInfoRspVo;
import zte.net.ecsord.params.ecaop.util.AopInterUtil;
import zte.net.ecsord.params.wms.req.NotifyOrderAccountWMSReq;
import zte.net.ecsord.params.wms.resp.NotifyOrderAccountWMSResp;
import zte.net.ecsord.params.wyg.req.StatuSynchReq;
import zte.net.ecsord.params.zb.req.NotifyOpenAccountGDRequest;
import zte.net.ecsord.params.zb.vo.AccountInfo;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.ecsord.utils.AutoWriteCardCacheTools;
import zte.net.ecsord.utils.DataUtil;
import zte.net.ecsord.utils.GetAreaInfoUtils;
import zte.net.ecsord.utils.SpecUtils;
import zte.params.ecsord.req.InsertOrderHandLogReq;

/**
 * 开户环节处理类
 * 
 * @author xuefeng
 */
@SuppressWarnings("rawtypes")
public class OrdOpenAccountTacheManager extends BaseSupport implements IOrdOpenAccountTacheManager {

	private Logger log = Logger.getLogger(this.getClass());

	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public BusiDealResult recOpenAcctResult(BusiCompRequest busiCompRequest) {
		BusiDealResult result = new BusiDealResult();
		// 获取规则对象
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		NotifyOpenAccountGDRequest openActReq = (NotifyOpenAccountGDRequest) fact.getRequest();
		String order_id = fact.getOrder_id();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String col3 = orderTree.getCol3();
		if (null == col3 || !EcsOrderConsts.TRACE_TRIGGER_INF.equals(col3)) {// 界面触发
			return result;
		}
		OrderItemBusiRequest orderItemBusi = CommonDataFactory.getInstance().getOrderTree(order_id)
				.getOrderItemBusiRequests().get(0);

		List<AccountInfo> actInfoList = openActReq.getAccountInfo();
		if (null != actInfoList && actInfoList.size() > 0) {
			AccountInfo accountInfo = actInfoList.get(0);
			String open_time = accountInfo.getOpenTime();
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ZB_STATUS, AttrConsts.ACTIVE_NO, AttrConsts.BSS_ACCOUNT_TIME,
							AttrConsts.BSS_OPERATOR, AttrConsts.BSS_OPERATOR_NAME },
					new String[] { EcsOrderConsts.ZB_ORDER_STATE_N06, accountInfo.getTradeId(), open_time,
							accountInfo.getOpenStaff(), accountInfo.getOpenStaffName() });

			OrderItemExtBusiRequest orderItemBusiExt = orderItemBusi.getOrderItemExtBusiRequest();
			orderItemBusiExt.setAccount_status(EcsOrderConsts.ACCOUNT_STATUS_1);
			orderItemBusiExt.setAccount_time(open_time);
			orderItemBusiExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemBusiExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemBusiExt.store();

			result.setError_msg(EcsOrderConsts.ACCOUNT_STATUS_1_DESC);
		} else {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACCOUNT_STATUS },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_0 });
			result.setError_msg(EcsOrderConsts.ACCOUNT_STATUS_0_DESC);
		}
		result.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
		result.setError_msg("处理成功");
		return result;
	}

	@Override
	public BusiDealResult notifyOpenAcctToWMS(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		NotifyOrderAccountWMSReq req = new NotifyOrderAccountWMSReq();
		req.setOrderId(order_id);
		NotifyOrderAccountWMSResp infResp = client.execute(req, NotifyOrderAccountWMSResp.class);
		if (!infResp.getErrorCode().equals(EcsOrderConsts.WMS_INF_RESP_CODE_0000)) {
			result.setError_msg("错误编码：" + infResp.getErrorCode() + ";错误信息：" + infResp.getErrorMessage());
			result.setError_code(infResp.getErrorCode());
			// 异常标记
			Map params = new HashMap();
			BusiCompRequest bcr = new BusiCompRequest();
			bcr.setEnginePath("zteCommonTraceRule.markedException");
			bcr.setOrder_id(order_id);
			params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			bcr.setQueryParams(params);
			CommonDataFactory.getInstance().execBusiComp(bcr);
		}
		return result;
	}

	/**
	 * 新用户 开户申请
	 */
	@Override
	public BusiDealResult openApplyToAop(String order_id) throws ApiBusiException {
		// 获取订单树
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
		OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();

		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OpenDealApplyReq openActReq = new OpenDealApplyReq();
		openActReq.setNotNeedReqStrOrderId(order_id);
		// 接口调用开户申请
		OpenDealApplyResp rsp = client.execute(openActReq, OpenDealApplyResp.class);
		// rsp = new OpenDealApplyResp();
		// rsp.setError_code(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200+"");
		// 获取环节
		String flowNode = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		if (StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
			// 回访环节替换,非回访环节不再替换（因为回访环节可以修改减免金额，后面如果还替换，会冲掉这个值）
			if (StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)) {
				dealFeeInfoAopByLi(rsp.getFeeInfo(), order_id);
				orderItemExtBusiRequest.setEss_pre_status(EcsOrderConsts.ESS_PRE_STATUS_1);
				orderItemExtBusiRequest.setEss_pre_desc("开户预校验成功");
			}

			// 更新货品表里的相关数据
			orderItemExtBusiRequest.setBssOrderId(rsp.getBssOrderId());
			orderItemExtBusiRequest.setTotalFee(rsp.getTotalFee());
			String sim_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SIM_TYPE);// 卡种类
																												// 号卡性质
			if (EcsOrderConsts.SIM_TYPE_BCK.equals(sim_type)) {// 半成卡
				try {// 总费用减去卡费
					Double totalFee_rsp = Double.parseDouble(rsp.getTotalFee());// 返回的总费用
					Double reliefFee = 0d;// 要减掉的费用
					List<AttrFeeInfoBusiRequest> feeInfo = CommonDataFactory.getInstance().getOrderTree(order_id)
							.getFeeInfoBusiRequests();
					for (AttrFeeInfoBusiRequest vo : feeInfo) {
						if (EcsOrderConsts.BASE_YES_FLAG_1.equals(vo.getIs_aop_fee())
								&& EcsOrderConsts.KF_FEE_ITEM_ID.equals(vo.getFee_item_code())) {// 卡费
							reliefFee += vo.getDiscount_fee() * 1000;// 扣掉
																		// 卡费的减免费用
						}
					}
					String totalFee = totalFee_rsp - reliefFee + "";
					orderItemExtBusiRequest.setTotalFee(totalFee.substring(0, totalFee.indexOf(".")));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			// 社会机全国合约惠机C1，对终端费用进行全额减免
			// 获取商品小类
			String cat_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);
			if (StringUtils.isNotEmpty(cat_id) && EcsOrderConsts.GOODS_CAT_MACHINE_YOUHUI_C1.equals(cat_id)) {
				try {// 总费用减去终端费用
					Double totalFee_rsp = Double.parseDouble(rsp.getTotalFee());// 返回的总费用
					Double reliefFee = 0d;// 要减掉的费用
					List<AttrFeeInfoBusiRequest> feeInfo = CommonDataFactory.getInstance().getOrderTree(order_id)
							.getFeeInfoBusiRequests();
					for (AttrFeeInfoBusiRequest vo : feeInfo) {
						if (EcsOrderConsts.BASE_YES_FLAG_1.equals(vo.getIs_aop_fee())
								&& EcsOrderConsts.AOP_FEE_ID_4310.equals(vo.getFee_item_code())) {// 终端
							reliefFee += vo.getDiscount_fee() * 1000;// 扣掉
																		// 终端的减免费用
						}
					}
					String totalFee = totalFee_rsp - reliefFee + "";
					orderItemExtBusiRequest.setTotalFee(totalFee.substring(0, totalFee.indexOf(".")));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemExtBusiRequest.store();

			// 更新开户流水号
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACTIVE_NO },
					new String[] { rsp.getProvOrderId() });

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		} else {
			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getDetail()) ? rsp.getCode() : rsp.getDetail()));
			if (!StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)) {
				// 标记异常
				// Utils.markException(order_id,false);
				Utils.markException(order_id, false, "", result.getError_msg());
			} else {
				orderItemExtBusiRequest.setEss_pre_status(EcsOrderConsts.ESS_PRE_STATUS_0);
				orderItemExtBusiRequest.setEss_pre_desc("错误编码：" + rsp.getCode() + ";错误信息：" + rsp.getDetail());
				orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderItemExtBusiRequest.store();
			}

			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getDetail()) ? rsp.getCode() : rsp.getDetail()));
		}

		result.setResponse(rsp);

		return result;
	}

	/**
	 * 新用户开户提交
	 */
	@Override
	public BusiDealResult openDealSubmitToAop(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		OpenDealSubmitReq req = new OpenDealSubmitReq();
		req.setNotNeedReqStrOrderId(order_id);

		OpenDealSubmitResp rsp = client.execute(req, OpenDealSubmitResp.class);

		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {// 失败
			// 设置错误返回码
			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getDetail()) ? rsp.getCode() : rsp.getDetail()));

			long start1 = System.currentTimeMillis();
			// 标记异常
			// Utils.markException(order_id,false);
			Utils.markException(order_id, false, "", result.getError_msg());
			logger.info(order_id + "调用开户处理提交接口【openDealSubmitToAop】耗时：" + (System.currentTimeMillis() - start1));

			// 更新开户状态
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACCOUNT_STATUS },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_0 });
			// 记录多写卡开户异常操作日志
			OrderActionLogReq logReq = new OrderActionLogReq();
			logReq = new OrderActionLogReq();
			logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_05);
			logReq.setAction_desc("开户异常");
			logReq.setOrder_id(order_id);
			AutoWriteCardCacheTools.saveWriteCardActiveLog(logReq);
		} else {// 成功
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			// 保存受理单信息、保存货品表中开户结果相关字段
			AcceptanceParamVo param = new AcceptanceParamVo();
			if (!StringUtils.isEmpty(rsp.getAcceptanceForm()))
				param.setAcceptanceForm(rsp.getAcceptanceForm());
			if (!StringUtils.isEmpty(rsp.getAcceptanceMode()))
				param.setAcceptanceMode(rsp.getAcceptanceMode());
			if (!StringUtils.isEmpty(rsp.getAcceptanceTp()))
				param.setAcceptanceTp(rsp.getAcceptanceTp());
			if (!DataUtil.checkFieldValueNull(param)) {
				param.setNotNeedReqStrOrderId(order_id);
				saveAcceptanceInfos(param, orderTree);
			}

			// 标记主商品附加产品办理成功
			List<OrderSubProductBusiRequest> subProductLists = CommonDataFactory.getInstance().getOrderTree(order_id)
					.getOrderSubProductBusiRequest();
			List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance()
					.getOrderTree(order_id).getAttrPackageSubProdBusiRequest();
			if (attrPackageSubProdList != null && attrPackageSubProdList.size() > 0 && subProductLists != null
					&& subProductLists.size() > 0) {
				for (OrderSubProductBusiRequest subProduct : subProductLists) {
					if (!StringUtils.equals("0", subProduct.getProd_inst_id()))
						continue;
					for (AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList) { // 设置线上办理成功
						if (StringUtils.equals(subProduct.getSub_pro_inst_id(), subPackage.getSubProd_inst_id())
								&& (StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_0, subPackage.getStatus())
										|| StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1,
												subPackage.getStatus()))) { // 取待办理、办理失败的记录
							subPackage.setSub_bss_order_id(
									CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACTIVE_NO));
							subPackage.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
							subPackage.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							subPackage.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							subPackage.store();
						}
					}
				}
			}

			// 更新各种开户状态和结果
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ACCOUNT_STATUS, AttrConsts.ZB_STATUS, AttrConsts.ACTIVE_NO,
							AttrConsts.BSS_ACCOUNT_TIME, AttrConsts.BSS_OPERATOR, AttrConsts.BSS_OPERATOR_NAME,
							AttrConsts.BSSORDERID, AttrConsts.ZB_ESS_ORDER_ID, AttrConsts.TAX_NO },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_1, EcsOrderConsts.ZB_ORDER_STATE_N06,
							rsp.getProvOrderId(), dateformat.format(new Date()), req.getOperatorId(),
							req.getEssOperInfo().getStaffName(), rsp.getBssOrderId(), rsp.getEssOrderId(),
							rsp.getTaxNo() });

			// 开户结果要通知新商城：仅通知，不用考虑新商城接受的状态。流程运行到写卡环节，获取到总部的写卡数据时还要通知新商城
			/*
			 * StatuSynchReq statuSyn = new
			 * StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_D,
			 * EcsOrderConsts.DIC_ORDER_NODE_D_DESC,EcsOrderConsts.
			 * ACCOUNT_STATUS_1,EcsOrderConsts.ACCOUNT_STATUS_1_DESC,"");
			 * CommonDataFactory.getInstance().notifyNewShop(statuSyn);
			 */

			// 半成卡一键开户,通知新商城业务办理环节
			String is_write_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
					AttrConsts.IS_WRITE_CARD);
			if (EcsOrderConsts.IS_WRITE_CARD_NO.equals(is_write_card)) {// 不用写卡(半成卡)才在这里通知新商城业务办理;成卡(也不用写卡)的订单不会走这个业务组件

				List<AttrGiftInfoBusiRequest> giftInfoList = CommonDataFactory.getInstance().getOrderTree(order_id)
						.getGiftInfoBusiRequests();
				for (AttrGiftInfoBusiRequest giftInfo : giftInfoList) {
					String bss_status = giftInfo.getBss_status();
					if (EcsOrderConsts.GIFT_BSS_STATUS_1.equals(bss_status)
							|| EcsOrderConsts.GIFT_BSS_STATUS_3.equals(bss_status)) {
						logger.info("更新订单bss办理状态，id：" + order_id + "  ");
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
								new String[] { AttrConsts.BSS_STATUS }, new String[] { EcsOrderConsts.BSS_STATUS_0 });
						break;
					}
				}

				// 通知新商城业务办理环节
				/*
				 * StatuSynchReq statuSynY = new
				 * StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_Y,
				 * EcsOrderConsts.DIC_ORDER_NODE_Y_DESC,EcsOrderConsts.
				 * BSS_STATUS_1,EcsOrderConsts.BSS_STATUS_1_DESC,"");
				 * CommonDataFactory.getInstance().notifyNewShop(statuSynY);
				 */
			}

			// 记录多写卡开户完成操作日志
			OrderActionLogReq logReq = new OrderActionLogReq();
			logReq = new OrderActionLogReq();
			logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_04);
			logReq.setAction_desc("开户完成");
			logReq.setOrder_id(order_id);
			AutoWriteCardCacheTools.saveWriteCardActiveLog(logReq);
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * aop费用信息处理---接口单位厘 aop开户接口返回费用处理
	 * 
	 * @param list
	 */
	private void dealFeeInfoAopByLi(List<FeeInfoRspVo> list, String order_id) {
		// 获取订单费用
		List<AttrFeeInfoBusiRequest> oldfeelist = CommonDataFactory.getInstance().getOrderTree(order_id)
				.getFeeInfoBusiRequests();
		// 删除旧的开户预提交返回的费用is_aop_fee=1
		for (AttrFeeInfoBusiRequest oldvo : oldfeelist) {
			if (StringUtils.equals(oldvo.getIs_aop_fee(), EcsOrderConsts.BASE_YES_FLAG_1)) {
				oldvo.setDb_action(ConstsCore.DB_ACTION_DELETE);
				oldvo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				oldvo.store();// 更新到数据库，同时更新到缓存
			}
		}
		String sim_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SIM_TYPE);// 卡种类
																											// 号卡性质
		String cat_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);// 商品小类
		// 获取接口返回的费用信息
		List<FeeInfoRspVo> feeInfo = list;
		if (null != feeInfo) {
			for (FeeInfoRspVo vo : feeInfo) {
				AttrFeeInfoBusiRequest req = new AttrFeeInfoBusiRequest();
				req.setOrder_id(order_id);
				req.setInst_id(order_id);
				req.setFee_inst_id(daoSupport.getSequences("SEQ_ATTR_FEE_INFO", "1", 18));
				req.setIs_aop_fee(EcsOrderConsts.BASE_YES_FLAG_1);
				req.setFee_item_code(vo.getFeeId());
				req.setFee_item_name(vo.getFeeDes());
				req.setO_fee_num(vo.getOrigFee() / 1000);
				req.setFee_category(vo.getFeeCategory());
				req.setMax_relief(vo.getMaxRelief() / 1000);
				req.setDiscount_fee(0.00);
				if (EcsOrderConsts.SIM_TYPE_BCK.equals(sim_type)
						&& EcsOrderConsts.KF_FEE_ITEM_ID.equals(vo.getFeeId())) {// 半成卡卡费全减免
					req.setDiscount_fee(vo.getOrigFee() / 1000);
				} else if (EcsOrderConsts.GOODS_CAT_MACHINE_YOUHUI_C1.equals(cat_id)
						&& EcsOrderConsts.AOP_FEE_ID_4310.equals(vo.getFeeId())) {// 社会机合约惠机C1终端费用减免
					req.setDiscount_fee(vo.getOrigFee() / 1000);
				}
				req.setDb_action(ConstsCore.DB_ACTION_INSERT);
				req.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				// 实收金额=应收金额-减免金额add by wui
				req.setN_fee_num(req.getO_fee_num() - req.getDiscount_fee());
				req.store();// 更新到数据库，同时更新到缓存
			}
		}

		// 获取订单树最新费用，是否有"4001多交预存"，如果有，给aop费用拷贝一份
		oldfeelist = CommonDataFactory.getInstance().getOrderTree(order_id).getFeeInfoBusiRequests();
		for (AttrFeeInfoBusiRequest feevo : oldfeelist) {
			if (StringUtils.equals(EcsOrderConsts.DJYCK_FEE_ITEM_ID, feevo.getFee_item_code())) {
				AttrFeeInfoBusiRequest feenew = new AttrFeeInfoBusiRequest();
				feenew.setOrder_id(feevo.getOrder_id());
				feenew.setInst_id(feevo.getInst_id());
				feenew.setFee_inst_id(daoSupport.getSequences("SEQ_ATTR_FEE_INFO", "1", 18));
				feenew.setIs_aop_fee(EcsOrderConsts.BASE_YES_FLAG_1);
				feenew.setFee_item_code(EcsOrderConsts.AOP_FEE_ID_99);
				feenew.setFee_item_name(feevo.getFee_item_name());
				feenew.setO_fee_num(feevo.getO_fee_num());
				feenew.setFee_category(EcsOrderConsts.AOP_FEE_CATEGORY_2);
				feenew.setDiscount_fee(feevo.getDiscount_fee());
				feenew.setMax_relief(0.00);
				feenew.setN_fee_num(feevo.getN_fee_num());
				feenew.setDb_action(ConstsCore.DB_ACTION_INSERT);// 操作
				feenew.setIs_dirty(ConstsCore.IS_DIRTY_YES);// 脏数据处理
				feenew.store();
			}
		}
	}

	/**
	 * 保存受理单信息、保存货品表中开户结果相关字段
	 * 
	 * @param orderItemBusiRequests
	 * @param param
	 */
	private void saveAcceptanceInfos(AcceptanceParamVo param) {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(param.getNotNeedReqStrOrderId());

		OrderItemsAptPrintsBusiRequest orderItemsAptPrintsBusiRequest = null;
		List<OrderItemsAptPrintsBusiRequest> orderItemsAptPrintsRequestList = orderTree.getOrderItemBusiRequests()
				.get(0).getOrderItemsAptPrintsRequests();
		if (!orderItemsAptPrintsRequestList.isEmpty()) {
			orderItemsAptPrintsBusiRequest = orderItemsAptPrintsRequestList.get(0);
			orderItemsAptPrintsBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		} else {
			orderItemsAptPrintsBusiRequest = new OrderItemsAptPrintsBusiRequest();
			orderItemsAptPrintsRequestList.add(orderItemsAptPrintsBusiRequest);
			orderItemsAptPrintsBusiRequest.setOrder_id(param.getNotNeedReqStrOrderId());
			orderItemsAptPrintsBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
		}
		orderItemsAptPrintsBusiRequest.setReceipt_code(param.getAcceptanceTp());
		orderItemsAptPrintsBusiRequest.setReceipt_mode(param.getAcceptanceMode());

		String acceptanceForm = param.getAcceptanceForm();
		if (StringUtils.isNotEmpty(acceptanceForm)) {
			String accept_form = "";

			// 将各种转义符都替换成人话
			Map map = new HashMap();
			map = JsonUtil.fromJson(acceptanceForm, Map.class);
			for (int i = 1; i <= 20; i++) {
				String accept_info = (String) map.get("RECEIPT_INFO" + i);
				if (accept_info != null && !"null".equals(accept_info)) {
					accept_form += accept_info;
				}
			}
			accept_form = accept_form.replaceAll("^^", " ").replaceAll("lt;", "<").replaceAll("gt;", ">")
					.replaceAll("zt;", "/").replaceAll("lp;", "{").replaceAll("rp;", "}").replaceAll("np;", "\"")
					.replaceAll("amp;", "&").replaceAll("iexcl;", "\\?").replaceAll("~", "<br>").replaceAll("^", " ")
					.replace("^^", " ");

			if (accept_form.length() < 3000) {
				orderItemsAptPrintsBusiRequest.setAcceptance_html(accept_form.substring(0, 2999));
				orderItemsAptPrintsBusiRequest.setAcceptance_html_2(ConstsCore.NULL_VAL);
				orderItemsAptPrintsBusiRequest.setAcceptance_html_3(ConstsCore.NULL_VAL);
			}
			if (accept_form.length() > 2999 && accept_form.length() < 6000) {
				orderItemsAptPrintsBusiRequest.setAcceptance_html(accept_form.substring(0, 2999));
				orderItemsAptPrintsBusiRequest.setAcceptance_html_2(accept_form.substring(2999));
				orderItemsAptPrintsBusiRequest.setAcceptance_html_3(ConstsCore.NULL_VAL);
			}
			if (accept_form.length() > 5999) {
				orderItemsAptPrintsBusiRequest.setAcceptance_html(accept_form.substring(0, 2999));
				orderItemsAptPrintsBusiRequest.setAcceptance_html_2(accept_form.substring(2999, 5999));
				orderItemsAptPrintsBusiRequest.setAcceptance_html_3(accept_form.substring(5999));
			}
			orderItemsAptPrintsBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemsAptPrintsBusiRequest.store();
		}
		// 更新货品记录开户信息相关字段
		OrderItemBusiRequest orderItemBusi = CommonDataFactory.getInstance()
				.getOrderTree(param.getNotNeedReqStrOrderId()).getOrderItemBusiRequests().get(0);
		OrderItemExtBusiRequest orderItemBusiExt = orderItemBusi.getOrderItemExtBusiRequest();
		orderItemBusiExt.setAccount_status(EcsOrderConsts.ACCOUNT_STATUS_1);
		orderItemBusiExt.setAccount_time(dateformat.format(new Date()));
		orderItemBusiExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderItemBusiExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderItemBusiExt.store();
	}

	/**
	 * 老用户优惠购机申请
	 */
	public BusiDealResult oldActivityMobileApply(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OldUserBuyApplyReq req = new OldUserBuyApplyReq();
		req.setNotNeedReqStrOrderId(order_id);
		OldUserBuyApplyResp resp = client.execute(req, OldUserBuyApplyResp.class);

		// 获取订单树
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String flowNode = orderTree.getOrderExtBusiRequest().getFlow_trace_id();

		if (StringUtils.equals(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "", resp.getError_code())) {
			// 只在回访环节保存开户申请返回的费用明细
			// if(StringUtils.equals(flowNode,
			// EcsOrderConsts.DIC_ORDER_NODE_B)){
			dealFeeInfoAopByLi(resp.getFeeInfo(), order_id);
			// }

			// 更新货品表里的相关数据
			List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
			OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
			orderItemExtBusiRequest.setTotalFee(resp.getTotalFee());
			orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemExtBusiRequest.store();

			// 更新流水号
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ACTIVE_NO, AttrConsts.BSSORDERID },
					new String[] { resp.getEssSubscribeId(), resp.getEssSubscribeId() });

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		} else {
			if (!StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)) {
				Utils.markException(order_id, false);
			}

			result.setError_msg((StringUtils.isEmpty(resp.getDetail()) ? resp.getCode() : resp.getDetail()));
			result.setError_code(resp.getCode());
		}
		return result;
	}

	/**
	 * 老用户优惠购机提交
	 */
	public BusiDealResult oldActivityMobileSubmit(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OldUserBuySubmitReq req = new OldUserBuySubmitReq();
		req.setNotNeedReqStrOrderId(order_id);
		OldUserBuySubmitResp resp = client.execute(req, OldUserBuySubmitResp.class);
		if (StringUtils.equals(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "", resp.getError_code())) {
			// 保存受理单信息、保存货品表中开户结果相关字段
			AcceptanceParamVo param = new AcceptanceParamVo();
			if (!StringUtils.isEmpty(resp.getAcceptanceForm()))
				param.setAcceptanceForm(resp.getAcceptanceForm());
			if (!StringUtils.isEmpty(resp.getAcceptanceMode()))
				param.setAcceptanceMode(resp.getAcceptanceMode());
			if (!StringUtils.isEmpty(resp.getAcceptanceTp()))
				param.setAcceptanceTp(resp.getAcceptanceTp());
			if (!DataUtil.checkFieldValueNull(param)) {
				param.setNotNeedReqStrOrderId(order_id);
				saveAcceptanceInfos(param);
			}

			// 标记主商品附加产品办理成功
			List<OrderSubProductBusiRequest> subProductLists = CommonDataFactory.getInstance().getOrderTree(order_id)
					.getOrderSubProductBusiRequest();
			List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance()
					.getOrderTree(order_id).getAttrPackageSubProdBusiRequest();
			if (attrPackageSubProdList != null && attrPackageSubProdList.size() > 0 && subProductLists != null
					&& subProductLists.size() > 0) {
				for (OrderSubProductBusiRequest subProduct : subProductLists) {
					if (!StringUtils.equals("0", subProduct.getProd_inst_id()))
						continue;
					for (AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList) { // 设置线上办理成功
						if (StringUtils.equals(subProduct.getSub_pro_inst_id(), subPackage.getSubProd_inst_id())
								&& (StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_0, subPackage.getStatus())
										|| StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1,
												subPackage.getStatus()))) { // 取待办理、办理失败的记录
							subPackage.setSub_bss_order_id(
									CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACTIVE_NO));
							subPackage.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
							subPackage.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							subPackage.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							subPackage.store();
						}
					}
				}
			}

			// 开户成功，保存旧资源串码
			String old_terminal_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
					AttrConsts.TERMINAL_NUM);
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.OLD_TERMINAL_NUM },
					new String[] { old_terminal_num });

			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ZB_STATUS, AttrConsts.BSS_ACCOUNT_TIME, AttrConsts.BSS_OPERATOR,
							AttrConsts.BSS_OPERATOR_NAME, AttrConsts.TAX_NO },
					new String[] { EcsOrderConsts.ZB_ORDER_STATE_N06, dateformat.format(new Date()),
							req.getOperatorId(), req.getEssOperInfo().getStaffName(), resp.getTaxNo() });

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		} else {
			result.setError_code(resp.getCode());
			result.setError_msg(resp.getDetail());

			// 更新开户状态
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACCOUNT_STATUS },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_0 });
			// 标记异常
			Utils.markException(order_id, false);
		}
		return result;
	}

	/**
	 * 套餐变更申请
	 */
	@Override
	public BusiDealResult productChangeApply(String order_id) throws ApiBusiException {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		/*
		 *  cbss附加产品低消送权益预提交，需要校验的小类id，如果货品小类为“附加产品”，走新代码入口，
		 *  如果不是，走原代码入口
		 */
		String cat_id = cacheUtil.getConfigInfo("CBSS_CAT_ID");
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ProdChangeApplyReq req = new ProdChangeApplyReq();
		req.setNotNeedReqStrOrderId(order_id);
		req.getEssOperInfo();
		// 接口可以处理不同业务
		List<ProductInfoReqVo> list = new ArrayList<ProductInfoReqVo>();
		ProductInfoReqVo productInfo = new ProductInfoReqVo();
		String productId = null;
		String elementType = null;
		String changeType = null;
		String packageId = "";
		String elementId = "";
		List<ProdPackElementReqVo> packageElement = new ArrayList<ProdPackElementReqVo>();
		List<Goods> products = SpecUtils.getAllOrderProducts(order_id);// 所有货品
		  String modType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "modType");//0 订购 1 退订
	        if(StringUtil.isEmpty(modType)){
	            modType="0";
	        }
		List<Goods> productList = new ArrayList<Goods>();
		
		for (Goods p : products) {
			if (cat_id.contains(p.getCat_id())) {// CBSS附加产品 货品类型
				productList.add(p);
			}
			if (productList.size() == 0) {
				Map specs = SpecUtils.getProductSpecMap(p);
				if (specs != null && !specs.isEmpty()) {
					productId = specs.get("cbss_product_code") == null ? "" : specs.get("cbss_product_code").toString();
					packageId = specs.get("package_code") == null ? null : specs.get("package_code").toString();
					elementId = specs.get("element_code") == null ? null : specs.get("element_code").toString();
					elementType = specs.get("elementType") == null ? null : specs.get("elementType").toString();
					changeType = specs.get("changeType") == null ? null : specs.get("changeType").toString();
				}
				// 为空的话整个节点去掉
				if (!StringUtils.isEmpty(elementId) || !StringUtils.isEmpty(packageId)) {
					ProdPackElementReqVo vo = new ProdPackElementReqVo();
					vo.setElementId(elementId);
					vo.setElementType(elementType);// 元素类型 D资费,S服务,A活动，X S服务
					vo.setPackageId(packageId);
					vo.setModType(modType);
					// 服务资费属性
					// List<ServiceAttrReqVo> serviceAttr = new
					// ArrayList<ServiceAttrReqVo>();
					// ServiceAttrReqVo attr = new ServiceAttrReqVo();
					// attr.setCode("");
					// attr.setValue("");
					// vo.setServiceAttr(serviceAttr);
					vo.setServiceAttr(null);
					packageElement.add(vo);
				}
				String optType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "optType");
				if (StringUtil.isEmpty(optType)) {
					optType = "00";
				}
				productInfo.setOptType(optType); // 待定， 00：订购；01：退订；02：变更
				String productMode = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "productMode");
				if (!StringUtil.isEmpty(productMode)) {
					productInfo.setProductMode(productMode); // 0：可选产品；1：主产品
				} else {
					productInfo.setProductMode("0"); // 0：可选产品；1：主产品
				}
			}
		}
		productInfo.setPackageElement(packageElement);
		productInfo.setProductId(productId);
		productInfo.setCompanyId(null);
		productInfo.setProductNameX(null);
		if (productList.size() > 0) {
			for (Goods goods : productList) {
				ProductInfoReqVo productInfos = new ProductInfoReqVo();
				List<ProdPackElementReqVo> packageElements = new ArrayList<ProdPackElementReqVo>();

				String productIds=null;
				String packageIds=null;
				String elementIds=null;
				String elementTypes=null;
				
				Map specs = SpecUtils.getProductSpecMap(goods);
				if (specs != null && !specs.isEmpty()) {
					productIds = specs.get("cbss_product_code") == null ? "" : specs.get("cbss_product_code").toString();
					packageIds = specs.get("package_code") == null ? null : specs.get("package_code").toString();
					elementIds = specs.get("element_code") == null ? null : specs.get("element_code").toString();
					elementTypes = specs.get("elementType") == null ? null : specs.get("elementType").toString();
					changeType = specs.get("changeType") == null ? null : specs.get("changeType").toString();
				}
				// 为空的话整个节点去掉
				if (!StringUtils.isEmpty(elementIds) || !StringUtils.isEmpty(packageIds)) {
					ProdPackElementReqVo vo = new ProdPackElementReqVo();
					vo.setElementId(elementIds);
					vo.setElementType(elementTypes);// 元素类型 D资费,S服务,A活动，X S服务
					vo.setPackageId(packageIds);
					vo.setModType(modType);
					vo.setServiceAttr(null);
					packageElements.add(vo);
				}
				String optType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "optType");
				if (StringUtil.isEmpty(optType)) {
					optType = "00";
				}
				productInfos.setOptType(optType); // 待定， 00：订购；01：退订；02：变更
				String productMode = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "productMode");
				if (!StringUtil.isEmpty(productMode)) {
					productInfos.setProductMode(productMode); // 0：可选产品；1：主产品
				} else {
					productInfos.setProductMode("0"); // 0：可选产品；1：主产品
				}
				
				productInfos.setProductId(productIds);
				productInfos.setPackageElement(packageElements);
				productInfos.setCompanyId(null);
				productInfos.setProductNameX(null);
				list.add(productInfos);
			}
		} else {
			list.add(productInfo);
		}

		req.setProductInfo(list);

		req.setChangeType(changeType);// 变更类型：0：套餐间变更，如A主套餐变更为B主套餐1：套餐内变更，如取消流量封顶；不传默认套餐间变更
		ProdChangeApplyResp resp = client.execute(req, ProdChangeApplyResp.class);

		// 获取订单树
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		// String flowNode =
		// orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		if (null != resp.getResultMsg() && !StringUtils.isEmpty(resp.getResultMsg().getBssOrderId())) {

			if (null != resp.getResultMsg().getFeeInfo() && resp.getResultMsg().getFeeInfo().size() > 0) {
				dealFeeInfoAopByLi(resp.getResultMsg().getFeeInfo(), order_id);
			}
			// 更新货品表里的相关数据
			List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
			OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
			orderItemExtBusiRequest.setBssOrderId(resp.getResultMsg().getBssOrderId());
			orderItemExtBusiRequest.setActive_no(resp.getResultMsg().getBssOrderId());
			orderItemExtBusiRequest.setTotalFee(resp.getResultMsg().getTotalFee());
			orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemExtBusiRequest.store();

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");

			// 设置附加产品资费包的开户流水号
			// List<OrderSubProductBusiRequest> subProductLists =
			// CommonDataFactory.getInstance().getOrderTree(order_id).getOrderSubProductBusiRequest();
			// List<AttrPackageSubProdBusiRequest> attrPackageSubProdList =
			// CommonDataFactory.getInstance().getOrderTree(order_id).getAttrPackageSubProdBusiRequest();
			// if (attrPackageSubProdList != null &&
			// attrPackageSubProdList.size() > 0 && subProductLists != null &&
			// subProductLists.size() > 0) {
			// for (OrderSubProductBusiRequest subProduct : subProductLists) {
			// if (!StringUtils.equals("0", subProduct.getProd_inst_id()))
			// continue;
			// for (AttrPackageSubProdBusiRequest subPackage :
			// attrPackageSubProdList) { // 设置线上办理成功
			// if (StringUtils.equals(subProduct.getSub_pro_inst_id(),
			// subPackage.getSubProd_inst_id())
			// && (StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_0,
			// subPackage.getStatus())
			// || StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1,
			// subPackage.getStatus()))) { // 取待办理、办理失败的记录
			// subPackage.setSub_bss_order_id(resp.getResultMsg().getBssOrderId());
			// subPackage.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			// subPackage.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			// subPackage.store();
			// }
			// }
			// }
			// }

		} else {

			result.setError_code(null != resp.getResultMsg() ? resp.getResultMsg().getCode() : (resp.getError_code() + resp.getRes_code()));
			result.setError_msg(null != resp.getResultMsg() ? resp.getResultMsg().getDetail() : (resp.getError_msg()+resp.getRes_message()).replace("成功", ""));
			Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_BSS, null != resp.getResultMsg() ? resp.getResultMsg().getDetail() : (resp.getError_msg()+resp.getRes_message().replace("成功", "")));

			// 标记办理失败
			// List<OrderSubProductBusiRequest> subProductLists =
			// CommonDataFactory.getInstance().getOrderTree(order_id).getOrderSubProductBusiRequest();
			// List<AttrPackageSubProdBusiRequest> attrPackageSubProdList =
			// CommonDataFactory.getInstance().getOrderTree(order_id).getAttrPackageSubProdBusiRequest();
			// if (attrPackageSubProdList != null &&
			// attrPackageSubProdList.size() > 0 && subProductLists != null &&
			// subProductLists.size() > 0) {
			// for (OrderSubProductBusiRequest subProduct : subProductLists) {
			// if (!StringUtils.equals("0", subProduct.getProd_inst_id()))
			// continue;
			// for (AttrPackageSubProdBusiRequest subPackage :
			// attrPackageSubProdList) { // 设置线上办理成功
			// if (StringUtils.equals(subProduct.getSub_pro_inst_id(),
			// subPackage.getSubProd_inst_id())
			// && StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_0,
			// subPackage.getStatus())) { // 取待办理、办理失败的记录
			// subPackage.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1);
			// subPackage.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			// subPackage.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			// subPackage.store();
			// }
			// }
			// }
			// }
		}
		return result;
	}

	/**
	 * 套餐变更提交
	 */
	@Override
	public BusiDealResult productChangeSubmit(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		OpenDealSubmit1Req req = new OpenDealSubmit1Req();
		req.setNotNeedReqStrOrderId(order_id);

		OpenDealSubmitResp rsp = client.execute(req, OpenDealSubmitResp.class);

		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {// 失败
			// 设置错误返回码
			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getDetail()) ? rsp.getCode() : rsp.getDetail()));

			// 标记办理失败
			List<OrderSubProductBusiRequest> subProductLists = CommonDataFactory.getInstance().getOrderTree(order_id)
					.getOrderSubProductBusiRequest();
			List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance()
					.getOrderTree(order_id).getAttrPackageSubProdBusiRequest();
			if (attrPackageSubProdList != null && attrPackageSubProdList.size() > 0 && subProductLists != null
					&& subProductLists.size() > 0) {
				for (OrderSubProductBusiRequest subProduct : subProductLists) {
					if (!StringUtils.equals("0", subProduct.getProd_inst_id()))
						continue;
					for (AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList) { // 设置线上办理成功
						if (StringUtils.equals(subProduct.getSub_pro_inst_id(), subPackage.getSubProd_inst_id())
								&& StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_0,
										subPackage.getStatus())) { // 取待办理、办理失败的记录
							subPackage.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1);
							subPackage.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							subPackage.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							subPackage.store();
						}
					}
				}
			}

			/**
			 * 暂时不处理异常，只用来做附加产品的办理 add by shusx 2016-05-09 //标记异常
			 * Utils.markException(order_id,false);
			 **/
		} else {// 成功

			/** 暂时不记录受理单，只用来做附加产品的办理 add by shusx 2016-05-09 **/

			// 标记主商品附加产品办理成功
			List<OrderSubProductBusiRequest> subProductLists = CommonDataFactory.getInstance().getOrderTree(order_id)
					.getOrderSubProductBusiRequest();
			List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance()
					.getOrderTree(order_id).getAttrPackageSubProdBusiRequest();
			if (attrPackageSubProdList != null && attrPackageSubProdList.size() > 0 && subProductLists != null
					&& subProductLists.size() > 0) {
				for (OrderSubProductBusiRequest subProduct : subProductLists) {
					if (!StringUtils.equals("0", subProduct.getProd_inst_id()))
						continue;
					for (AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList) { // 设置线上办理成功
						if (StringUtils.equals(subProduct.getSub_pro_inst_id(), subPackage.getSubProd_inst_id())
								&& (StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_0, subPackage.getStatus())
										|| StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1,
												subPackage.getStatus()))) { // 取待办理、办理失败的记录
							subPackage.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
							subPackage.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							subPackage.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							subPackage.store();
						}
					}
				}
			}

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * 2-3G转4G 开户处理申请
	 */
	@Override
	public BusiDealResult openAccApply23to4(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		Open23to4ApplyReq openActReq = new Open23to4ApplyReq();
		openActReq.setNotNeedReqStrOrderId(order_id);
		// 接口调用开户申请
		OpenDealApplyResp infResp = client.execute(openActReq, OpenDealApplyResp.class);
		// 获取订单树
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);

		String flowNode = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		if (StringUtils.equals(infResp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
			// 只在回访环节保存开户申请返回的费用明细
			// if(StringUtils.equals(flowNode,
			// EcsOrderConsts.DIC_ORDER_NODE_B)){
			dealFeeInfoAopByLi(infResp.getFeeInfo(), order_id);
			// }

			// 更新货品表里的相关数据
			List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
			OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
			orderItemExtBusiRequest.setTotalFee(infResp.getTotalFee());
			orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemExtBusiRequest.store();

			// 更新流水号
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ACTIVE_NO, AttrConsts.BSSORDERID },
					new String[] { infResp.getProvOrderId(), infResp.getBssOrderId() });

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		} else {
			if (!StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)) {
				// 标记异常
				Utils.markException(order_id, false);
			}

			result.setError_code(infResp.getCode());
			result.setError_msg(infResp.getDetail());
		}
		result.setResponse(infResp);
		return result;
	}

	/**
	 * 2-3G转4G 开户处理提交
	 */
	@Override
	public BusiDealResult openAccSubmit23to4(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		OpenDealSubmitReq req = new OpenDealSubmitReq();
		req.setNotNeedReqStrOrderId(order_id);

		OpenDealSubmitResp rsp = client.execute(req, OpenDealSubmitResp.class);

		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {// 失败
			// 设置错误返回码
			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getDetail()) ? rsp.getCode() : rsp.getDetail()));

			// 标记异常
			Utils.markException(order_id, false);

			// 更新开户状态
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACCOUNT_STATUS },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_0 });
		} else {// 成功
				// 保存受理单信息、保存货品表中开户结果相关字段
			AcceptanceParamVo param = new AcceptanceParamVo();
			if (!StringUtils.isEmpty(rsp.getAcceptanceForm()))
				param.setAcceptanceForm(rsp.getAcceptanceForm());
			if (!StringUtils.isEmpty(rsp.getAcceptanceMode()))
				param.setAcceptanceMode(rsp.getAcceptanceMode());
			if (!StringUtils.isEmpty(rsp.getAcceptanceTp()))
				param.setAcceptanceTp(rsp.getAcceptanceTp());
			if (!DataUtil.checkFieldValueNull(param)) {
				param.setNotNeedReqStrOrderId(order_id);
				saveAcceptanceInfos(param);
			}

			// 更新各种开户状态和结果
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ACCOUNT_STATUS, AttrConsts.ZB_STATUS, AttrConsts.ACTIVE_NO,
							AttrConsts.BSS_ACCOUNT_TIME, AttrConsts.BSS_OPERATOR, AttrConsts.BSS_OPERATOR_NAME,
							AttrConsts.BSSORDERID, AttrConsts.ZB_ESS_ORDER_ID, AttrConsts.TAX_NO },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_1, EcsOrderConsts.ZB_ORDER_STATE_N06,
							rsp.getProvOrderId(), dateformat.format(new Date()), req.getOperatorId(),
							req.getEssOperInfo().getStaffName(), rsp.getBssOrderId(), rsp.getEssOrderId(),
							rsp.getTaxNo() });

			// 开户结果要通知新商城：仅通知，不用考虑新商城接受的状态。流程运行到写卡环节，获取到总部的写卡数据时还要通知新商城
			StatuSynchReq statuSyn = new StatuSynchReq(order_id, EcsOrderConsts.DIC_ORDER_NODE_D,
					EcsOrderConsts.DIC_ORDER_NODE_D_DESC, EcsOrderConsts.ACCOUNT_STATUS_1,
					EcsOrderConsts.ACCOUNT_STATUS_1_DESC, "");
			CommonDataFactory.getInstance().notifyNewShop(statuSyn);

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * 退机申请
	 */
	@Override
	public BusiDealResult returnMachineApply(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ReturnMachineApplyReq req = new ReturnMachineApplyReq();
		req.setNotNeedReqStrOrderId(order_id);
		ReturnMachineApplyResp rsp = client.execute(req, ReturnMachineApplyResp.class);// 调用接口

		// 获取订单树
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String flowNode = orderTree.getOrderExtBusiRequest().getFlow_trace_id();

		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
			if (!StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)) {
				// 标记异常
				Utils.markException(order_id, false);
			}

			result.setError_msg(rsp.getDetail());
			result.setError_code(rsp.getCode());
		} else {
			// 只在回访环节保存开户申请返回的费用明细
			if (StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)) {
				dealFeeInfoAopByLi(rsp.getFeeInfo(), order_id);
			}

			// 更新货品表里的相关数据
			List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
			OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
			orderItemExtBusiRequest.setTotalFee(rsp.getTotalFee());
			orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemExtBusiRequest.store();

			// 更新流水号
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACTIVE_NO },
					new String[] { rsp.getProvOrdersId() });

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * 退机提交
	 */
	@Override
	public BusiDealResult returnMachineSubmit(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ReturnMachineSubmitReq req = new ReturnMachineSubmitReq();
		req.setNotNeedReqStrOrderId(order_id);
		ReturnMachineSubmitResp rsp = client.execute(req, ReturnMachineSubmitResp.class);// 调用接口

		// 获取订单树
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);

		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
			result.setError_msg(rsp.getDetail());
			result.setError_code(rsp.getCode());

			// 更新开户状态
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACCOUNT_STATUS },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_0 });
			// 标记异常
			Utils.markException(order_id, false);
		} else {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ZB_STATUS, AttrConsts.ACTIVE_NO, AttrConsts.BSS_ACCOUNT_TIME,
							AttrConsts.BSS_OPERATOR, AttrConsts.BSS_OPERATOR_NAME, AttrConsts.BSSORDERID },
					new String[] { EcsOrderConsts.ZB_ORDER_STATE_N06, rsp.getProvOrderId(),
							dateformat.format(new Date()), req.getOperatorId(), req.getEssOperInfo().getStaffName(),
							rsp.getBssOrderId() });

			// 更新货品记录开户信息相关字段
			OrderItemBusiRequest orderItemBusi = orderTree.getOrderItemBusiRequests().get(0);
			OrderItemExtBusiRequest orderItemBusiExt = orderItemBusi.getOrderItemExtBusiRequest();
			orderItemBusiExt.setAccount_status(EcsOrderConsts.ACCOUNT_STATUS_1);
			orderItemBusiExt.setAccount_time(AttrConsts.BSS_ACCOUNT_TIME);
			orderItemBusiExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemBusiExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemBusiExt.store();

			// 设置开户成功状态标志
			result.setError_msg(EcsOrderConsts.ACCOUNT_STATUS_1_DESC);

			// 开户结果要通知新商城：仅通知，不用考虑新商城接受的状态。流程运行到写卡环节，获取到总部的写卡数据时还要通知新商城
			StatuSynchReq statuSyn = new StatuSynchReq(order_id, EcsOrderConsts.DIC_ORDER_NODE_D,
					EcsOrderConsts.DIC_ORDER_NODE_D_DESC, EcsOrderConsts.ACCOUNT_STATUS_1,
					EcsOrderConsts.ACCOUNT_STATUS_1_DESC, "");
			CommonDataFactory.getInstance().notifyNewShop(statuSyn);

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * 4G老用户存费送费
	 */
	@Override
	public BusiDealResult cunFeeSongFee4GAop(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		CunFeeSongFee4GReq req = new CunFeeSongFee4GReq();
		req.setNotNeedReqStrOrderId(order_id);

		CunFeeSongFee4GResp resp = client.execute(req, CunFeeSongFee4GResp.class);
		// 获取订单树
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);

		if (!StringUtils.equals(resp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {// 失败
			// 设置错误返回码
			result.setError_code(resp.getCode());
			result.setError_msg(resp.getDetail());

			// 标记异常
			Utils.markException(order_id, false);
		} else {
			// 只在回访环节保存开户申请返回的费用明细
			// if(StringUtils.equals(flowNode,
			// EcsOrderConsts.DIC_ORDER_NODE_B)){
			dealFeeInfoAOPByFen(resp.getFeeInfo(), order_id);
			// }

			// 更新货品表里的相关数据
			List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
			OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
			orderItemExtBusiRequest.setBssOrderId(resp.getBssOrderId());
			orderItemExtBusiRequest.setTotalFee(resp.getTotalFee());
			orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemExtBusiRequest.store();

			// 更新流水号
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ACTIVE_NO, AttrConsts.BSSORDERID },
					new String[] { resp.getProvOrderId(), resp.getBssOrderId() });

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(resp);
		return result;
	}

	/**
	 * 4G老用户存费送费
	 */
	@Override
	public BusiDealResult cunFeeSongFee4GAopZJ(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		CunFeeSongFee4GReqZJ req = new CunFeeSongFee4GReqZJ();
		req.setNotNeedReqStrOrderId(order_id);

		CunFeeSongFee4GResp resp = client.execute(req, CunFeeSongFee4GResp.class);
		// 获取订单树
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);

		if (!StringUtils.equals(resp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")
				|| !StringUtils.isEmpty(resp.getCode())) {// 失败
			// 设置错误返回码
			result.setError_code(resp.getCode());
			result.setError_msg(resp.getDetail());

			// 标记异常
			Utils.markException(order_id, false);
		} else {
			// 只在回访环节保存开户申请返回的费用明细
			// if(StringUtils.equals(flowNode,
			// EcsOrderConsts.DIC_ORDER_NODE_B)){
			dealFeeInfoAOPByFen(resp.getFeeInfo(), order_id);
			// }

			// 更新货品表里的相关数据
			List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
			OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
			orderItemExtBusiRequest.setBssOrderId(resp.getBssOrderId());
			orderItemExtBusiRequest.setTotalFee(resp.getTotalFee());
			orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemExtBusiRequest.store();

			// 更新流水号
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ACTIVE_NO, AttrConsts.BSSORDERID },
					new String[] { resp.getProvOrderId(), resp.getBssOrderId() });

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(resp);
		return result;
	}

	/**
	 * 4G老用户存费送费正式提交
	 */
	@Override
	public BusiDealResult cunFeeSongFee4GSubmitAop(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		CunFeeSongFee4GSubmitReq req = new CunFeeSongFee4GSubmitReq();
		req.setNotNeedReqStrOrderId(order_id);

		CunFeeSongFee4GSubmitResp rsp = client.execute(req, CunFeeSongFee4GSubmitResp.class);

		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {// 失败
			// 设置错误返回码
			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getDetail()) ? rsp.getCode() : rsp.getDetail()));

			// 标记异常
			Utils.markException(order_id, false);

			// 更新开户状态
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACCOUNT_STATUS },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_0 });
		} else {// 成功
				// 保存受理单信息、保存货品表中开户结果相关字段
			AcceptanceParamVo param = new AcceptanceParamVo();
			if (!StringUtils.isEmpty(rsp.getAcceptanceForm()))
				param.setAcceptanceForm(rsp.getAcceptanceForm());
			if (!StringUtils.isEmpty(rsp.getAcceptanceMode()))
				param.setAcceptanceMode(rsp.getAcceptanceMode());
			if (!StringUtils.isEmpty(rsp.getAcceptanceTp()))
				param.setAcceptanceTp(rsp.getAcceptanceTp());
			if (!DataUtil.checkFieldValueNull(param)) {
				param.setNotNeedReqStrOrderId(order_id);
				saveAcceptanceInfos(param);
			}

			// 标记主商品附加产品办理成功
			List<OrderSubProductBusiRequest> subProductLists = CommonDataFactory.getInstance().getOrderTree(order_id)
					.getOrderSubProductBusiRequest();
			List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance()
					.getOrderTree(order_id).getAttrPackageSubProdBusiRequest();
			if (attrPackageSubProdList != null && attrPackageSubProdList.size() > 0 && subProductLists != null
					&& subProductLists.size() > 0) {
				for (OrderSubProductBusiRequest subProduct : subProductLists) {
					if (!StringUtils.equals("0", subProduct.getProd_inst_id()))
						continue;
					for (AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList) { // 设置线上办理成功
						if (StringUtils.equals(subProduct.getSub_pro_inst_id(), subPackage.getSubProd_inst_id())
								&& (StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_0, subPackage.getStatus())
										|| StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1,
												subPackage.getStatus()))) { // 取待办理、办理失败的记录
							subPackage.setSub_bss_order_id(
									CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACTIVE_NO));
							subPackage.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
							subPackage.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							subPackage.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							subPackage.store();
						}
					}
				}
			}

			// 更新各种开户状态和结果
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ACCOUNT_STATUS, AttrConsts.ZB_STATUS, AttrConsts.ACTIVE_NO,
							AttrConsts.BSS_ACCOUNT_TIME, AttrConsts.BSS_OPERATOR, AttrConsts.BSS_OPERATOR_NAME,
							AttrConsts.BSSORDERID, AttrConsts.ZB_ESS_ORDER_ID, AttrConsts.TAX_NO },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_1, EcsOrderConsts.ZB_ORDER_STATE_N06,
							rsp.getProvOrderId(), dateformat.format(new Date()), req.getOperatorId(),
							req.getEssOperInfo().getStaffName(), rsp.getBssOrderId(), rsp.getEssOrderId(),
							rsp.getTaxNo() });

			// 开户结果要通知新商城：仅通知，不用考虑新商城接受的状态。流程运行到写卡环节，获取到总部的写卡数据时还要通知新商城
			StatuSynchReq statuSyn = new StatuSynchReq(order_id, EcsOrderConsts.DIC_ORDER_NODE_D,
					EcsOrderConsts.DIC_ORDER_NODE_D_DESC, EcsOrderConsts.ACCOUNT_STATUS_1,
					EcsOrderConsts.ACCOUNT_STATUS_1_DESC, "");
			CommonDataFactory.getInstance().notifyNewShop(statuSyn);

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * 4G老用户存费送费正式提交
	 */
	@Override
	public BusiDealResult cunFeeSongFee4GSubmitAopZJ(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		CunFeeSongFee4GSubmitReqZJ req = new CunFeeSongFee4GSubmitReqZJ();
		req.setNotNeedReqStrOrderId(order_id);

		CunFeeSongFee4GSubmitResp rsp = client.execute(req, CunFeeSongFee4GSubmitResp.class);

		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")
				|| !StringUtils.isEmpty(rsp.getCode())) {// 失败
			// 设置错误返回码
			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getDetail()) ? rsp.getCode() : rsp.getDetail()));

			// 标记异常
			Utils.markException(order_id, false);

			// 更新开户状态
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACCOUNT_STATUS },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_0 });
		} else {// 成功
				// 保存受理单信息、保存货品表中开户结果相关字段
			AcceptanceParamVo param = new AcceptanceParamVo();
			if (!StringUtils.isEmpty(rsp.getAcceptanceForm()))
				param.setAcceptanceForm(rsp.getAcceptanceForm());
			if (!StringUtils.isEmpty(rsp.getAcceptanceMode()))
				param.setAcceptanceMode(rsp.getAcceptanceMode());
			if (!StringUtils.isEmpty(rsp.getAcceptanceTp()))
				param.setAcceptanceTp(rsp.getAcceptanceTp());
			if (!DataUtil.checkFieldValueNull(param)) {
				param.setNotNeedReqStrOrderId(order_id);
				saveAcceptanceInfos(param);
			}

			// 标记主商品附加产品办理成功
			List<OrderSubProductBusiRequest> subProductLists = CommonDataFactory.getInstance().getOrderTree(order_id)
					.getOrderSubProductBusiRequest();
			List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance()
					.getOrderTree(order_id).getAttrPackageSubProdBusiRequest();
			if (attrPackageSubProdList != null && attrPackageSubProdList.size() > 0 && subProductLists != null
					&& subProductLists.size() > 0) {
				for (OrderSubProductBusiRequest subProduct : subProductLists) {
					if (!StringUtils.equals("0", subProduct.getProd_inst_id()))
						continue;
					for (AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList) { // 设置线上办理成功
						if (StringUtils.equals(subProduct.getSub_pro_inst_id(), subPackage.getSubProd_inst_id())
								&& (StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_0, subPackage.getStatus())
										|| StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1,
												subPackage.getStatus()))) { // 取待办理、办理失败的记录
							subPackage.setSub_bss_order_id(
									CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACTIVE_NO));
							subPackage.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
							subPackage.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							subPackage.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							subPackage.store();
						}
					}
				}
			}

			// 更新各种开户状态和结果
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ACCOUNT_STATUS, AttrConsts.ZB_STATUS, AttrConsts.ACTIVE_NO,
							AttrConsts.BSS_ACCOUNT_TIME, AttrConsts.BSS_OPERATOR, AttrConsts.BSS_OPERATOR_NAME,
							AttrConsts.BSSORDERID, AttrConsts.ZB_ESS_ORDER_ID, AttrConsts.TAX_NO },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_1, EcsOrderConsts.ZB_ORDER_STATE_N06,
							rsp.getProvOrderId(), dateformat.format(new Date()), req.getOperatorId(), "",
							rsp.getBssOrderId(), rsp.getEssOrderId(), rsp.getTaxNo() });

			// 开户结果要通知新商城：仅通知，不用考虑新商城接受的状态。流程运行到写卡环节，获取到总部的写卡数据时还要通知新商城
			StatuSynchReq statuSyn = new StatuSynchReq(order_id, EcsOrderConsts.DIC_ORDER_NODE_D,
					EcsOrderConsts.DIC_ORDER_NODE_D_DESC, EcsOrderConsts.ACCOUNT_STATUS_1,
					EcsOrderConsts.ACCOUNT_STATUS_1_DESC, "");
			CommonDataFactory.getInstance().notifyNewShop(statuSyn);

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * 费用信息处理--接口单位分 aop开户接口返回费用处理
	 * 
	 * @param list
	 * @param order_id
	 */
	private void dealFeeInfoAOPByFen(List<FeeInfoRspVo> list, String order_id) {
		// 获取订单费用
		List<AttrFeeInfoBusiRequest> oldfeelist = CommonDataFactory.getInstance().getOrderTree(order_id)
				.getFeeInfoBusiRequests();
		// 删除旧的开户预提交返回的费用is_aop_fee=1
		for (AttrFeeInfoBusiRequest oldvo : oldfeelist) {
			if (StringUtils.equals(oldvo.getIs_aop_fee(), EcsOrderConsts.BASE_YES_FLAG_1)) {
				oldvo.setDb_action(ConstsCore.DB_ACTION_DELETE);
				oldvo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				oldvo.store();// 更新到数据库，同时更新到缓存
			}
		}
		String sim_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SIM_TYPE);// 卡种类
																											// 号卡性质
		// 获取接口返回的费用信息
		List<FeeInfoRspVo> feeInfo = list;
		if (null != feeInfo) {
			for (FeeInfoRspVo vo : feeInfo) {
				AttrFeeInfoBusiRequest req = new AttrFeeInfoBusiRequest();
				req.setOrder_id(order_id);
				req.setInst_id(order_id);
				req.setFee_inst_id(daoSupport.getSequences("SEQ_ATTR_FEE_INFO", "1", 18));
				req.setIs_aop_fee(EcsOrderConsts.BASE_YES_FLAG_1);
				req.setFee_item_code(vo.getFeeId());
				req.setFee_item_name(vo.getFeeDes());
				req.setO_fee_num(vo.getOrigFee() / 100);
				req.setFee_category(vo.getFeeCategory());
				req.setMax_relief(vo.getMaxRelief() / 100);
				req.setDiscount_fee(0.00);
				if (EcsOrderConsts.SIM_TYPE_BCK.equals(sim_type)
						&& EcsOrderConsts.KF_FEE_ITEM_ID.equals(vo.getFeeId())) {// 半成卡卡费全减免
					req.setDiscount_fee(vo.getOrigFee() / 100);
				}
				req.setDb_action(ConstsCore.DB_ACTION_INSERT);
				req.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				// 实收金额=应收金额-减免金额add by wui
				req.setN_fee_num(req.getO_fee_num() - req.getDiscount_fee());
				req.store();// 更新到数据库，同时更新到缓存
			}
		}

		// 获取订单树最新费用，是否有"4001多交预存"，如果有，给aop费用拷贝一份
		oldfeelist = CommonDataFactory.getInstance().getOrderTree(order_id).getFeeInfoBusiRequests();
		for (AttrFeeInfoBusiRequest feevo : oldfeelist) {
			if (StringUtils.equals(EcsOrderConsts.DJYCK_FEE_ITEM_ID, feevo.getFee_item_code())) {
				AttrFeeInfoBusiRequest feenew = new AttrFeeInfoBusiRequest();
				feenew.setOrder_id(feevo.getOrder_id());
				feenew.setInst_id(feevo.getInst_id());
				feenew.setFee_inst_id(daoSupport.getSequences("SEQ_ATTR_FEE_INFO", "1", 18));
				feenew.setIs_aop_fee(EcsOrderConsts.BASE_YES_FLAG_1);
				feenew.setFee_item_code(EcsOrderConsts.AOP_FEE_ID_99);
				feenew.setFee_item_name(feevo.getFee_item_name());
				feenew.setO_fee_num(feevo.getO_fee_num());
				feenew.setFee_category(EcsOrderConsts.AOP_FEE_CATEGORY_2);
				feenew.setDiscount_fee(feevo.getDiscount_fee());
				feenew.setMax_relief(0.00);
				feenew.setN_fee_num(feevo.getN_fee_num());
				feenew.setDb_action(ConstsCore.DB_ACTION_INSERT);// 操作
				feenew.setIs_dirty(ConstsCore.IS_DIRTY_YES);// 脏数据处理
				feenew.store();
			}
		}
	}

	/**
	 * 新用户 开户申请
	 */
	@Override
	public BusiDealResult openApplyToBSS(String order_id) throws ApiBusiException {
		// 获取订单树
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
		OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();

		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		BSSAccountReq accountReq = new BSSAccountReq();
		accountReq.setNotNeedReqStrOrderId(order_id);
		// 接口调用开户申请
		BSSAccountResponse rsp = client.execute(accountReq, BSSAccountResponse.class);

		// 获取环节
		String flowNode = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		if (StringUtils.equals(rsp.getError_code(), EcsOrderConsts.BSS_ANSWER_CODE_0000)) {
			// 回访环节替换,非回访环节不再替换（因为回访环节可以修改减免金额，后面如果还替换，会冲掉这个值）
			if (StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)) {
				dealFeeInfoBSS(rsp.getFeeInfo(), order_id, 100);
				orderItemExtBusiRequest.setEss_pre_status(EcsOrderConsts.ESS_PRE_STATUS_1);
				orderItemExtBusiRequest.setEss_pre_desc("BSS开户预校验成功");
			}

			// 更新货品表里的相关数据
			orderItemExtBusiRequest.setBssOrderId(rsp.getProvOrderId());
			orderItemExtBusiRequest.setTotalFee(rsp.getTotalFee());
			String sim_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SIM_TYPE);// 卡种类
																												// 号卡性质
			if (EcsOrderConsts.SIM_TYPE_BCK.equals(sim_type)) {// 半成卡
				try {// 总费用减去卡费
					Double totalFee_rsp = Double.parseDouble(rsp.getTotalFee());// 返回的总费用
					Double reliefFee = 0d;// 要减掉的费用
					List<AttrFeeInfoBusiRequest> feeInfo = CommonDataFactory.getInstance().getOrderTree(order_id)
							.getFeeInfoBusiRequests();
					for (AttrFeeInfoBusiRequest vo : feeInfo) {
						if (EcsOrderConsts.BASE_YES_FLAG_1.equals(vo.getIs_aop_fee())
								&& (EcsOrderConsts.KF_FEE_ITEM_ID.equals(vo.getFee_item_code())
										|| EcsOrderConsts.KF_FEE_ITEM_ID_BSS.equals(vo.getFee_item_code()))) {// 卡费
							reliefFee += vo.getDiscount_fee() * 1000;// 扣掉
																		// 卡费的减免费用
						}
					}
					String totalFee = totalFee_rsp - reliefFee + "";
					orderItemExtBusiRequest.setTotalFee(totalFee.substring(0, totalFee.indexOf(".")));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemExtBusiRequest.store();

			// 更新开户流水号
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACTIVE_NO },
					new String[] { rsp.getProvOrderId() });

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		} else {
			result.setError_code(rsp.getError_code());
			result.setError_msg(rsp.getError_msg());
			if (!StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)) {
				// 标记异常
				// Utils.markException(order_id,false);
				Utils.markException(order_id, false, "", result.getError_msg());
			} else {
				orderItemExtBusiRequest.setEss_pre_status(EcsOrderConsts.ESS_PRE_STATUS_0);
				orderItemExtBusiRequest.setEss_pre_desc("错误编码：" + rsp.getRespCode() + ";错误信息：" + rsp.getRespDesc());
				orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderItemExtBusiRequest.store();
			}

			result.setError_code(rsp.getError_code());
			result.setError_msg(rsp.getError_msg());
		}

		result.setResponse(rsp);

		return result;
	}

	/**
	 * 新用户开户提交
	 */
	@Override
	public BusiDealResult openDealSubmitToBSS(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		BSSOrderSubReq req = new BSSOrderSubReq();
		req.setNotNeedReqStrOrderId(order_id);

		BSSOrderSubResponse rsp = client.execute(req, BSSOrderSubResponse.class);

		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.BSS_ANSWER_CODE_0000)) {// 失败
			// 设置错误返回码
			result.setError_code(rsp.getError_code());
			result.setError_msg(rsp.getError_msg());

			// 标记异常
			Utils.markException(order_id, false);

			// 更新开户状态
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACCOUNT_STATUS },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_0 });
		} else {// 成功
				// 保存受理单信息、保存货品表中开户结果相关字段
			AcceptanceParamVo param = new AcceptanceParamVo();
			// param.setAcceptanceForm(rsp.getAcceptanceForm());
			// param.setAcceptanceMode(rsp.getAcceptanceMode());
			// param.setAcceptanceTp(rsp.getAcceptanceTp());
			param.setNotNeedReqStrOrderId(order_id);
			// saveAcceptanceInfos(param);

			// 标记主商品附加产品办理成功
			List<OrderSubProductBusiRequest> subProductLists = CommonDataFactory.getInstance().getOrderTree(order_id)
					.getOrderSubProductBusiRequest();
			List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance()
					.getOrderTree(order_id).getAttrPackageSubProdBusiRequest();
			if (attrPackageSubProdList != null && attrPackageSubProdList.size() > 0 && subProductLists != null
					&& subProductLists.size() > 0) {
				for (OrderSubProductBusiRequest subProduct : subProductLists) {
					if (!StringUtils.equals("0", subProduct.getProd_inst_id()))
						continue;
					for (AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList) { // 设置线上办理成功
						if (StringUtils.equals(subProduct.getSub_pro_inst_id(), subPackage.getSubProd_inst_id())
								&& (StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_0, subPackage.getStatus())
										|| StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1,
												subPackage.getStatus()))) { // 取待办理、办理失败的记录
							subPackage.setSub_bss_order_id(
									CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACTIVE_NO));
							subPackage.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
							subPackage.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							subPackage.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							subPackage.store();
						}
					}
				}
			}

			// 更新各种开户状态和结果
			// CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
			// new
			// String[]{AttrConsts.ACCOUNT_STATUS,AttrConsts.ZB_STATUS,AttrConsts.ACTIVE_NO,
			// AttrConsts.BSS_ACCOUNT_TIME,AttrConsts.BSS_OPERATOR,
			// AttrConsts.BSS_OPERATOR_NAME,AttrConsts.BSSORDERID,AttrConsts.ZB_ESS_ORDER_ID,AttrConsts.TAX_NO},
			// new
			// String[]{EcsOrderConsts.ACCOUNT_STATUS_1,EcsOrderConsts.ZB_ORDER_STATE_N06,rsp.getOrderSubRsp().getProvOrderId(),dateformat.format(new
			// Date()),req.getOperatorId(),req.getEssOperInfo().getStaffName(),rsp.getBssOrderId(),rsp.getEssOrderId(),rsp.getOrderSubRsp().getTaxInvoiceNo()});
			// BSS返回没有传新流水
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ACCOUNT_STATUS, AttrConsts.ZB_STATUS, AttrConsts.BSS_ACCOUNT_TIME,
							AttrConsts.BSS_OPERATOR, AttrConsts.BSS_OPERATOR_NAME, AttrConsts.TAX_NO },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_1, EcsOrderConsts.ZB_ORDER_STATE_N06,
							dateformat.format(new Date()), req.getOperatorID(), req.getEssOperInfo().getStaffName(),
							rsp.getTaxInvoiceNo() });

			// 开户结果要通知新商城：仅通知，不用考虑新商城接受的状态。流程运行到写卡环节，获取到总部的写卡数据时还要通知新商城
			StatuSynchReq statuSyn = new StatuSynchReq(order_id, EcsOrderConsts.DIC_ORDER_NODE_D,
					EcsOrderConsts.DIC_ORDER_NODE_D_DESC, EcsOrderConsts.ACCOUNT_STATUS_1,
					EcsOrderConsts.ACCOUNT_STATUS_1_DESC, "");
			CommonDataFactory.getInstance().notifyNewShop(statuSyn);

			// 半成卡一键开户,通知新商城业务办理环节
			String is_write_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
					AttrConsts.IS_WRITE_CARD);
			if (EcsOrderConsts.IS_WRITE_CARD_NO.equals(is_write_card)) {// 不用写卡(半成卡)才在这里通知新商城业务办理;成卡(也不用写卡)的订单不会走这个业务组件

				List<AttrGiftInfoBusiRequest> giftInfoList = CommonDataFactory.getInstance().getOrderTree(order_id)
						.getGiftInfoBusiRequests();
				for (AttrGiftInfoBusiRequest giftInfo : giftInfoList) {
					String bss_status = giftInfo.getBss_status();
					if (EcsOrderConsts.GIFT_BSS_STATUS_1.equals(bss_status)
							|| EcsOrderConsts.GIFT_BSS_STATUS_3.equals(bss_status)) {
						logger.info("更新订单bss办理状态，id：" + order_id + "  ");
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
								new String[] { AttrConsts.BSS_STATUS }, new String[] { EcsOrderConsts.BSS_STATUS_0 });
						break;
					}
				}

				// 通知新商城业务办理环节
				/*
				 * StatuSynchReq statuSynY = new
				 * StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_Y,
				 * EcsOrderConsts.DIC_ORDER_NODE_Y_DESC,EcsOrderConsts.
				 * BSS_STATUS_1,EcsOrderConsts.BSS_STATUS_1_DESC,"");
				 * CommonDataFactory.getInstance().notifyNewShop(statuSynY);
				 */
			}

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * BSS费用信息处理 BSS开户接口返回费用处理
	 * 
	 * @param list
	 * @param fee
	 *            费用处理单位
	 */
	private void dealFeeInfoBSS(List<BSSFeeInfoRspVo> list, String order_id, int fee) {
		// 获取订单费用
		List<AttrFeeInfoBusiRequest> oldfeelist = CommonDataFactory.getInstance().getOrderTree(order_id)
				.getFeeInfoBusiRequests();
		// 删除旧的开户预提交返回的费用is_aop_fee=1
		for (AttrFeeInfoBusiRequest oldvo : oldfeelist) {
			if (StringUtils.equals(oldvo.getIs_aop_fee(), EcsOrderConsts.BASE_YES_FLAG_1)) {
				oldvo.setDb_action(ConstsCore.DB_ACTION_DELETE);
				oldvo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				oldvo.store();// 更新到数据库，同时更新到缓存
			}
		}
		String sim_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SIM_TYPE);// 卡种类
																											// 号卡性质
		// 获取接口返回的费用信息
		List<BSSFeeInfoRspVo> feeInfo = list;
		if (null != feeInfo) {
			for (BSSFeeInfoRspVo vo : feeInfo) {
				AttrFeeInfoBusiRequest req = new AttrFeeInfoBusiRequest();
				req.setOrder_id(order_id);
				req.setInst_id(order_id);
				req.setFee_inst_id(daoSupport.getSequences("SEQ_ATTR_FEE_INFO", "1", 18));
				req.setIs_aop_fee(EcsOrderConsts.BASE_YES_FLAG_1);
				req.setFee_item_code(vo.getFeeId());
				req.setFee_item_name(vo.getFeeDes());
				req.setO_fee_num(vo.getOrigFee() / fee);
				req.setFee_category(vo.getFeeCategory());
				req.setMax_relief(vo.getMaxRelief() / fee);
				req.setDiscount_fee(0.00);
				if (EcsOrderConsts.SIM_TYPE_BCK.equals(sim_type) && (EcsOrderConsts.KF_FEE_ITEM_ID.equals(vo.getFeeId())
						|| EcsOrderConsts.KF_FEE_ITEM_ID_BSS.equals(vo.getFeeId()))) {// 半成卡卡费全减免
					req.setDiscount_fee(vo.getOrigFee() / fee);
				}
				req.setDb_action(ConstsCore.DB_ACTION_INSERT);
				req.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				// 实收金额=应收金额-减免金额add by wui
				req.setN_fee_num(req.getO_fee_num() - req.getDiscount_fee());
				req.store();// 更新到数据库，同时更新到缓存
			}
		}

		// 获取订单树最新费用，是否有"4001多交预存"，如果有，给aop费用拷贝一份
		oldfeelist = CommonDataFactory.getInstance().getOrderTree(order_id).getFeeInfoBusiRequests();
		for (AttrFeeInfoBusiRequest feevo : oldfeelist) {
			if (StringUtils.equals(EcsOrderConsts.DJYCK_FEE_ITEM_ID, feevo.getFee_item_code())) {
				AttrFeeInfoBusiRequest feenew = new AttrFeeInfoBusiRequest();
				feenew.setOrder_id(feevo.getOrder_id());
				feenew.setInst_id(feevo.getInst_id());
				feenew.setFee_inst_id(daoSupport.getSequences("SEQ_ATTR_FEE_INFO", "1", 18));
				feenew.setIs_aop_fee(EcsOrderConsts.BASE_YES_FLAG_1);
				feenew.setFee_item_code(EcsOrderConsts.BSS_FEE_ID_99);
				feenew.setFee_item_name("普通预存款");
				feenew.setO_fee_num(feevo.getO_fee_num());
				feenew.setFee_category(EcsOrderConsts.AOP_FEE_CATEGORY_2);
				feenew.setDiscount_fee(feevo.getDiscount_fee());
				feenew.setMax_relief(0.00);
				feenew.setN_fee_num(feevo.getN_fee_num());
				feenew.setDb_action(ConstsCore.DB_ACTION_INSERT);// 操作
				feenew.setIs_dirty(ConstsCore.IS_DIRTY_YES);// 脏数据处理
				feenew.store();
			}
		}
	}

	/**
	 * 老用户续约申请（AOP）
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult oldRenActivityApply(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OldUserRenActivityReq req = new OldUserRenActivityReq();
		req.setNotNeedReqStrOrderId(order_id);
		OldUserRenActivityResp resp = client.execute(req, OldUserRenActivityResp.class);

		// 获取订单树
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String flowNode = orderTree.getOrderExtBusiRequest().getFlow_trace_id();

		if (StringUtils.equals(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "", resp.getError_code())) {
			// 只在回访环节保存开户申请返回的费用明细
			// if(StringUtils.equals(flowNode,
			// EcsOrderConsts.DIC_ORDER_NODE_B)){
			dealFeeInfoAOPByFen(resp.getFeeInfo(), order_id);
			// }

			// 更新货品表里的相关数据
			List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
			OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
			orderItemExtBusiRequest.setTotalFee(resp.getTotalFee());
			orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemExtBusiRequest.store();

			// 更新流水号
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACTIVE_NO },
					new String[] { resp.getEssSubscribeId() });

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		} else {
			if (!StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)) {
				Utils.markException(order_id, false);
			}

			result.setError_msg((StringUtils.isEmpty(resp.getDetail()) ? resp.getCode() : resp.getDetail()));
			result.setError_code(resp.getCode());
		}
		return result;
	}

	/**
	 * 老用户续约提交（AOP）
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult oldRenActivitySubmit(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OldUserRenActivitySubmitReq req = new OldUserRenActivitySubmitReq();
		req.setNotNeedReqStrOrderId(order_id);
		OldUserRenActivitySubmitResp resp = client.execute(req, OldUserRenActivitySubmitResp.class);
		if (StringUtils.equals(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "", resp.getError_code())) {
			// 保存受理单信息、保存货品表中开户结果相关字段
			AcceptanceParamVo param = new AcceptanceParamVo();
			if (!StringUtils.isEmpty(resp.getAcceptanceForm()))
				param.setAcceptanceForm(resp.getAcceptanceForm());
			if (!StringUtils.isEmpty(resp.getAcceptanceMode()))
				param.setAcceptanceMode(resp.getAcceptanceMode());
			if (!StringUtils.isEmpty(resp.getAcceptanceTp()))
				param.setAcceptanceTp(resp.getAcceptanceTp());
			if (!DataUtil.checkFieldValueNull(param)) {
				param.setNotNeedReqStrOrderId(order_id);
				saveAcceptanceInfos(param);
			}

			// 开户成功，保存旧资源串码
			String old_terminal_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
					AttrConsts.TERMINAL_NUM);
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.OLD_TERMINAL_NUM },
					new String[] { old_terminal_num });

			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ZB_STATUS, AttrConsts.BSS_ACCOUNT_TIME, AttrConsts.BSS_OPERATOR,
							AttrConsts.BSS_OPERATOR_NAME, AttrConsts.TAX_NO },
					new String[] { EcsOrderConsts.ZB_ORDER_STATE_N06, dateformat.format(new Date()),
							req.getOperatorId(), req.getEssOperInfo().getStaffName(), resp.getTaxNo() });

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		} else {
			result.setError_code(resp.getCode());
			result.setError_msg(resp.getDetail());

			// 更新开户状态
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACCOUNT_STATUS },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_0 });
			// 标记异常
			Utils.markException(order_id, false);

		}
		return result;
	}

	/**
	 * 更新套餐业务包的办理状态
	 * 
	 * @param order_id
	 * @param status,1:办理失败,3:线上办理成功
	 * @return
	 */
	public String modifyAttrPackageStatus(String order_id, String status) {
		String result = "0000";
		try {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			List<AttrPackageBusiRequest> packageBusiRequests = orderTree
					.getPackageBusiRequests();
			if (null != packageBusiRequests && packageBusiRequests.size() > 0) {
				for (AttrPackageBusiRequest attrPackage : packageBusiRequests) {
					attrPackage.setStatus(status);
					attrPackage.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					attrPackage.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrPackage.store();
				}
			}
		} catch (Exception e) {
			result = e.getMessage();
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * BSS开户正式提交
	 * 
	 * @param order_id
	 * @return
	 */
	@Override
	public BusiDealResult subAccountOfficial(String order_id) throws ApiBusiException {
		// TODO Auto-generated method stub
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		BSSAccountOfficialSubReq req = new BSSAccountOfficialSubReq();
		req.setSerial_num(order_id);
		BSSAccountOfficialSubResp rsp = client.execute(req, BSSAccountOfficialSubResp.class);

		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {// 失败
			// 设置错误返回码
			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getMsg()) ? rsp.getCode() : rsp.getMsg()));

			// 标记异常
			Utils.markException(order_id, false);

			// 更新开户状态
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACCOUNT_STATUS },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_0 });
		} else {// 成功
				// 保存受理单信息、保存货品表中开户结果相关字段
			AcceptanceParamVo param = new AcceptanceParamVo();
			// if(!StringUtils.isEmpty(rsp.getAcceptanceForm()))param.setAcceptanceForm(rsp.getAcceptanceForm());
			// if(!StringUtils.isEmpty(rsp.getAcceptanceMode()))param.setAcceptanceMode(rsp.getAcceptanceMode());
			// if(!StringUtils.isEmpty(rsp.getAcceptanceTp()))param.setAcceptanceTp(rsp.getAcceptanceTp());
			if (!DataUtil.checkFieldValueNull(param)) {
				param.setNotNeedReqStrOrderId(order_id);
				saveAcceptanceInfos(param);
			}

			// 标记主商品附加产品办理成功
			List<OrderSubProductBusiRequest> subProductLists = CommonDataFactory.getInstance().getOrderTree(order_id)
					.getOrderSubProductBusiRequest();
			List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance()
					.getOrderTree(order_id).getAttrPackageSubProdBusiRequest();
			if (attrPackageSubProdList != null && attrPackageSubProdList.size() > 0 && subProductLists != null
					&& subProductLists.size() > 0) {
				for (OrderSubProductBusiRequest subProduct : subProductLists) {
					if (!StringUtils.equals("0", subProduct.getProd_inst_id()))
						continue;
					for (AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList) { // 设置线上办理成功
						if (StringUtils.equals(subProduct.getSub_pro_inst_id(), subPackage.getSubProd_inst_id())
								&& (StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_0, subPackage.getStatus())
										|| StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1,
												subPackage.getStatus()))) { // 取待办理、办理失败的记录
							subPackage.setSub_bss_order_id(
									CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACTIVE_NO));
							subPackage.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
							subPackage.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							subPackage.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							subPackage.store();
						}
					}
				}
			}

			// 更新各种开户状态和结果
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ACCOUNT_STATUS, AttrConsts.ZB_STATUS, AttrConsts.BSS_ACCOUNT_TIME },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_1, EcsOrderConsts.ZB_ORDER_STATE_N06,
							dateformat.format(new Date()) });

			// 开户结果要通知新商城：仅通知，不用考虑新商城接受的状态。流程运行到写卡环节，获取到总部的写卡数据时还要通知新商城
			/*
			 * StatuSynchReq statuSyn = new
			 * StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_D,
			 * EcsOrderConsts.DIC_ORDER_NODE_D_DESC,EcsOrderConsts.
			 * ACCOUNT_STATUS_1,EcsOrderConsts.ACCOUNT_STATUS_1_DESC,"");
			 * CommonDataFactory.getInstance().notifyNewShop(statuSyn);
			 */

			// 半成卡一键开户,通知新商城业务办理环节
			String is_write_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
					AttrConsts.IS_WRITE_CARD);
			if (EcsOrderConsts.IS_WRITE_CARD_NO.equals(is_write_card)) {// 不用写卡(半成卡)才在这里通知新商城业务办理;成卡(也不用写卡)的订单不会走这个业务组件

				List<AttrGiftInfoBusiRequest> giftInfoList = CommonDataFactory.getInstance().getOrderTree(order_id)
						.getGiftInfoBusiRequests();
				for (AttrGiftInfoBusiRequest giftInfo : giftInfoList) {
					String bss_status = giftInfo.getBss_status();
					if (EcsOrderConsts.GIFT_BSS_STATUS_1.equals(bss_status)
							|| EcsOrderConsts.GIFT_BSS_STATUS_3.equals(bss_status)) {
						logger.info("更新订单bss办理状态，id：" + order_id + "  ");
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
								new String[] { AttrConsts.BSS_STATUS }, new String[] { EcsOrderConsts.BSS_STATUS_0 });
						break;
					}
				}

				// 通知新商城业务办理环节
				/*
				 * StatuSynchReq statuSynY = new
				 * StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_Y,
				 * EcsOrderConsts.DIC_ORDER_NODE_Y_DESC,EcsOrderConsts.
				 * BSS_STATUS_1,EcsOrderConsts.BSS_STATUS_1_DESC,"");
				 * CommonDataFactory.getInstance().notifyNewShop(statuSynY);
				 */
			}

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * 开户预提交
	 */
	@Override
	public BusiDealResult bssPreCommit(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		PreCommitReq req = new PreCommitReq();
		req.setNotNeedReqStrOrderId(order_id);

		PreCommitResp rsp = client.execute(req, PreCommitResp.class);

		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {// 失败
			// 设置错误返回码
			result.setError_code(rsp.getError_code());
			result.setError_msg(rsp.getError_msg());
		} else {// 成功
				// 保存订单预提交信息
			JSONObject respJson = JSON.parseObject(rsp.getRespJson());
			String serial_num = respJson.getString("serial_num");
			CommonDataFactory.getInstance().updateAttrFieldValue(req.getNotNeedReqStrOrderId(),
					new String[] { AttrConsts.ACTIVE_NO }, new String[] { serial_num });
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	@Override
	public BusiDealResult modifyDeliveryInfoToZb(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		CrawlerDeliveryInfoReq req = new CrawlerDeliveryInfoReq();
		req.setOrderId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_NUM));
		req.setOldProvinceId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PROVINCE_CODE));
		req.setOldCityId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CITY_CODE));
		req.setProvinceId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PROVINCE_CODE));
		req.setCityId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CITY_CODE));
		req.setDistrictId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.AREA_CODE));
		req.setPostAddr(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_ADDR));
		String sending_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SENDING_TYPE);
		String dispachCode = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ZB_CARRY_MODE_CRAWLER,
				sending_type);
		if (StringUtils.isEmpty(dispachCode)) {
			dispachCode = "01";
		}
		req.setDispachCode(dispachCode);
		String shipping_time = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIPPING_TIME);
		String dlvTypeCode = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ZB_SHIPPING_TIME,
				shipping_time);
		req.setDlvTypeCode(dlvTypeCode);
		String shipping_company = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
				AttrConsts.SHIPPING_COMPANY);
		String logisticCode = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ZB_SHIPPING_COMPANY,
				shipping_company);
		req.setLogisticCode(logisticCode);
		String delayTime = null;
		if (EcsOrderConsts.SHIPPING_TIME_HOLIDAY.equals(shipping_time)) {
			delayTime = "只有双休日、节假日送货";
		} else if (EcsOrderConsts.SHIPPING_TIME_WORKDAY.equals(shipping_time)) {
			delayTime = "周一到周五工作时间";
		} else if (EcsOrderConsts.SHIPPING_TIME_NOLIMIT.equals(shipping_time)) {
			delayTime = "工作日、双休日、节假日均可送货";
		}
		req.setDelayTime(delayTime);
		req.setDeliverySelfName(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BUYER_NAME));
		req.setDeliverySelfPhone(
				CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REVEIVER_MOBILE));
		req.setOrderNo(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID));
		req.setIsManual(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ZB_OPEN_TYPE));
		CrawlerResp rsp = client.execute(req, CrawlerResp.class);
		if (!StringUtils.equals(rsp.getError_code(), ConstsCore.ERROR_SUCC)) {

			logger.info("=====OrdOpenAccountTacheManager modifyDeliveryInfoToZb 爬虫修改总商配送信息接口调用出错【errorMsg:" + rsp.getError_msg() + "】");

			// 标记异常
			Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_OPEN, rsp.getError_msg());
		}
		result.setError_code(rsp.getResp_code());
		result.setError_msg(rsp.getResp_msg());
		result.setResponse(rsp);
		return result;
	}

	@Override
	public BusiDealResult modifyGoodsInfoToZb(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		ModifyOpenAccountGoodsReq req = new ModifyOpenAccountGoodsReq();
		req.setOrderId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_NUM));
		req.setOrderNo(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID));
		req.setIdCard(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM));
		// 证件类型01:15位身份证,02:18位身份证
		String cert_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE);
		String certType = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ZB_CERT_TYPE_RELATION,
				cert_type);
		req.setCertType(certType);
		String certName = AttrUtils.getInstance().getOtherDictCodeValueDesc(StypeConsts.ZB_CERT_TYPE_RELATION,
				cert_type);
		req.setCertName(certName);
		req.setCertAddr(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS));
		req.setCustName(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_OWNER_NAME));
		req.setReferrerName(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.RECOMMENDED_NAME));
		req.setReferrerPhone(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.RECOMMENDED_PHONE));
		req.setDevelopmentName(
				CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.DEVELOPMENT_NAME));
		req.setDevelopmentCode(
				CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.DEVELOPMENT_CODE));
		req.setDevelopmentDepartId(
				CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.DEVELOPDEP_ID));
		req.setChannelName(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CHA_NAME));
		req.setRecommendNumber(
				CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CHA_PHONENUM));
		req.setIsManual(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ZB_OPEN_TYPE));
		ZteResponse rsp = client.execute(req, ZteResponse.class);
		if (!StringUtils.equals(rsp.getError_code(), ConstsCore.ERROR_SUCC)) {

			logger.info("=====OrdOpenAccountTacheManager modifyGoodsInfoToZb 修改开户环节商品信息接口调用出错【errorMsg:" + rsp.getError_msg() + "】");

			// 标记异常
			Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_OPEN, rsp.getError_msg());
		}
		result.setError_code(rsp.getError_code());
		result.setError_msg(rsp.getError_msg());
		result.setResponse(rsp);
		return result;
	}

	// @Override
	// public BusiDealResult zbEnterAccountDetailPage(String order_id)
	// throws ApiBusiException {
	// BusiDealResult result = new BusiDealResult();
	// ZteClient client =
	// ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
	//
	// OpenAccountDetailReq req = new OpenAccountDetailReq();
	// req.setOrderId(CommonDataFactory.getInstance().getAttrFieldValue(order_id,
	// AttrConsts.ORDER_NUM));
	// req.setOrderNo(CommonDataFactory.getInstance().getAttrFieldValue(order_id,
	// AttrConsts.OUT_TID));
	// OpenAccountDetailResp rsp = client.execute(req,
	// OpenAccountDetailResp.class);
	// if(!StringUtils.equals(rsp.getError_code(), ConstsCore.ERROR_SUCC)){
	// logger.info("=====OrdOpenAccountTacheManager
	// zbEnterAccountDetailPage
	// 进入总部开户详情页接口调用出错【errorMsg:"+rsp.getError_msg()+"】");
	// //标记异常
	// Utils.markException(order_id,false,EcsOrderConsts.ABNORMAL_TYPE_OPEN,rsp.getError_msg());
	// }
	// String zbOpenType = rsp.getZb_open_type();//总部开户类型
	// String orderModel =
	// CommonDataFactory.getInstance().getAttrFieldValue(order_id,
	// AttrConsts.ORDER_MODEL);
	// if("1".equals(zbOpenType)&&ZjEcsOrderConsts.ORDER_MODEL_08.equals(orderModel)){//总商手动开户订单在订单系统也只能手动开户
	// CommonDataFactory.getInstance().updateAttrFieldValue(order_id,new
	// String[]{AttrConsts.ORDER_MODEL}, new
	// String[]{ZjEcsOrderConsts.ORDER_MODEL_07});
	// OrderTreeBusiRequest orderTree =
	// CommonDataFactory.getInstance().getOrderTree(order_id);
	// orderTree.getOrderExtBusiRequest().setOrder_model(ZjEcsOrderConsts.ORDER_MODEL_07);
	// orderTree.store();
	// }
	// CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new
	// String[]{AttrConsts.ZB_OPEN_TYPE}, new String[]{zbOpenType}) ;
	// result.setError_code(rsp.getError_code());
	// result.setError_msg(rsp.getError_msg());
	// result.setResponse(rsp);
	// return result;
	// }

	@Override
	public BusiDealResult zbReAllotOrder(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		ReAllotOrderReq req = new ReAllotOrderReq();
		req.setOrderId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_NUM));
		req.setOrderNo(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID));
		CrawlerResp rsp = client.execute(req, CrawlerResp.class);

		result.setError_code(rsp.getError_code());
		result.setError_msg(rsp.getError_msg());
		result.setResponse(rsp);
		return result;
	}

	@Override
	public BusiDealResult openAccountValidateZb(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		CrawlerAccountInfoReq req = new CrawlerAccountInfoReq();
		req.setOrderId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_NUM));
		// 如果是上网卡带号码，合约机则传开户号码
		String type_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		if (EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(type_id)
				|| EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(type_id)) {
			req.setNetCardNum(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM));
		}
		req.setResourcesCode(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.TERMINAL_NUM));
		String cert_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE);
		String certType = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ZB_CERT_TYPE_RELATION,
				cert_type);
		req.setCertType(certType);
		req.setCertNum(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM));
		String customerName = AttrUtils.getInstance().getOtherDictCodeValueDesc(StypeConsts.ZB_CERT_TYPE_RELATION,
				cert_type);
		req.setCustomerName(customerName);
		req.setContactPerson(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_NAME));
		req.setContactPhone(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REVEIVER_MOBILE));
		req.setIccId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID));
		req.setPostAddr(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_ADDR));
		if (!EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(type_id)
				&& !EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(type_id)) {
			req.setPreNum(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM));
		}
		// 如果是新用户，且特殊业务类型为副卡，则认为是主副卡新带新且副卡带有号码的订单
		String isZFKNewOrder = "0";
		String is_old = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		String specialBusiType = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
				AttrConsts.SPECIAL_BUSI_TYPE);
		if (!EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_old) && "02".equals(specialBusiType)) {
			isZFKNewOrder = "1";
		}
		req.setIsZFKNewOrder(isZFKNewOrder);
		req.setCertAddress(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS));
		req.setIsCardChange(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD));
		req.setOrderNo(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID));

		CrawlerResp rsp = client.execute(req, CrawlerResp.class);
		if (!StringUtils.equals(rsp.getError_code(), ConstsCore.ERROR_SUCC)) {


			logger.info("=====OrdOpenAccountTacheManager openAccountValidateZb 爬虫开户校验信息接口调用出错【errorMsg:" + rsp.getError_msg() + "】");

			// 爬虫自动开户失败，生产模式转为爬虫手工写卡生产模式
			String order_model = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_MODEL);
			if (ZjEcsOrderConsts.ORDER_MODEL_08.equals(order_model)) {
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ORDER_MODEL },
						new String[] { ZjEcsOrderConsts.ORDER_MODEL_07 });
				// 标记异常
				Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_OPEN, rsp.getError_msg());
			}
			result.setError_code(rsp.getResp_code());
			result.setError_msg(rsp.getResp_msg());
		} else {
			try {
				List<FeeInfoRspVo> feeList = new ArrayList<FeeInfoRspVo>();
				String yucunFee = rsp.getOtherFee();
				String usimFee = rsp.getUsimFee();
				FeeInfoRspVo vo1 = new FeeInfoRspVo();
				vo1.setFeeId("CRAWLER001");
				vo1.setFeeCategory("");
				vo1.setFeeDes("多交预存款");
				// vo1.setOrigFee(50000D);
				vo1.setMaxRelief(0D);
				vo1.setFeeCategory("99");
				vo1.setOrigFee(StringUtils.isEmpty(yucunFee) ? 0 : Double.valueOf(yucunFee) * 1000);
				feeList.add(vo1);

				FeeInfoRspVo vo2 = new FeeInfoRspVo();
				vo2.setFeeId("CRAWLER002");
				vo2.setFeeCategory("");
				vo2.setFeeDes("应收卡费");
				// vo2.setOrigFee(0D);
				vo2.setMaxRelief(0D);
				vo2.setFeeCategory("99");
				vo2.setOrigFee(StringUtils.isEmpty(usimFee) ? 0 : Double.valueOf(usimFee) * 1000);
				feeList.add(vo2);

				List<CrawlerFeeInfo> crawlerFeeInfos = rsp.getCrawlerFeeInfo();
				if (crawlerFeeInfos != null && crawlerFeeInfos.size() > 0) {
					for (int i = 0; i < crawlerFeeInfos.size(); i++) {
						CrawlerFeeInfo feeInfo = crawlerFeeInfos.get(i);
						FeeInfoRspVo vo = new FeeInfoRspVo();
						vo.setFeeId(feeInfo.getFeeID());
						vo.setFeeCategory(feeInfo.getFeeCategory());
						vo.setFeeDes(feeInfo.getFeeDes());
						vo.setOrigFee(StringUtils.isEmpty(feeInfo.getOrigFee()) ? 0
								: Double.valueOf(feeInfo.getOrigFee()) * 1000);
						vo.setMaxRelief(StringUtils.isEmpty(feeInfo.getMaxRelief()) ? 0
								: Double.valueOf(feeInfo.getMaxRelief()) * 1000);
						feeList.add(vo);
					}
				}
				dealFeeInfoAopByLi(feeList, order_id);
				result.setError_code(rsp.getResp_code());
				result.setError_msg(rsp.getResp_msg());
			} catch (Exception e) {
				e.printStackTrace();
				result.setError_code("-1");
				result.setError_msg("保存费用信息出错：" + e.getMessage());
			}
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * PC批量写卡队列插入
	 */
	@Override
	public BusiDealResult writeCardSetQueueByPc(String order_id, String queue_code) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		logger.info("=========================PC批量写卡队列插入订单号：" + order_id + "\t队列号：" + queue_code);
		boolean flag = false;
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest order_ext = orderTree.getOrderExtBusiRequest();
		if (!StringUtils.isEmpty(queue_code)) {
			String sql = SF.ecsordSql("SWITCH_BY_PC_QUEUE");
			List<Map<String, String>> list = this.baseDaoSupport.queryForList(sql, queue_code);
			if (list != null && list.size() > 0) {
				logger.info("=========================PC批量写卡队列插入订单号：" + order_id + "\t队列大小：" + list.size());
				Map map = list.get(0);

				logger.info("=========================PC批量写卡队列插入订单号：" + order_id + "\t队列开启状态：" + map.get("queue_switch"));

				if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(map.get("queue_switch"))) { // 队列开启状态
					flag = true;
				} else {
					// 队列关闭记录es_order_handlog日志
					InsertOrderHandLogReq logReq = new InsertOrderHandLogReq();
					logReq.setOrder_id(order_id);
					logReq.setOp_id("1");
					logReq.setOp_name("超级管理员");
					logReq.setFlow_id(order_ext.getFlow_id());
					logReq.setFlow_trace_id(order_ext.getFlow_trace_id());
					logReq.setHandler_type(Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
					logReq.setType_code("");
					logReq.setComments("PC批量写卡队列：" + queue_code + "状态为关闭,转人工集中生产模式处理!");
					logReq.setCreate_time("sysdate");
					logReq.setSource_from(ManagerUtils.getSourceFrom());
					CommonDataFactory.getInstance().insertOrderHandlerLogs(logReq);
				}
			} else {
				// 未匹配到队列记录es_order_handlog日志
				InsertOrderHandLogReq logReq = new InsertOrderHandLogReq();
				logReq.setOrder_id(order_id);
				logReq.setOp_id("1");
				logReq.setOp_name("超级管理员");
				logReq.setFlow_id(order_ext.getFlow_id());
				logReq.setFlow_trace_id(order_ext.getFlow_trace_id());
				logReq.setHandler_type(Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
				logReq.setType_code("");
				logReq.setComments("PC批量写卡队列不存在,转人工集中生产模式处理!队列编码：" + queue_code);
				logReq.setCreate_time("sysdate");
				logReq.setSource_from(ManagerUtils.getSourceFrom());
				CommonDataFactory.getInstance().insertOrderHandlerLogs(logReq);
			}
		} else {
			// 未匹配到队列记录es_order_handlog日志
			InsertOrderHandLogReq logReq = new InsertOrderHandLogReq();
			logReq.setOrder_id(order_id);
			logReq.setOp_id("1");
			logReq.setOp_name("超级管理员");
			logReq.setFlow_id(order_ext.getFlow_id());
			logReq.setFlow_trace_id(order_ext.getFlow_trace_id());
			logReq.setHandler_type(Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
			logReq.setType_code("");
			logReq.setComments("未匹配到PC批量写卡队列,转自动化生产模式处理!");
			logReq.setCreate_time("sysdate");
			logReq.setSource_from(ManagerUtils.getSourceFrom());
			CommonDataFactory.getInstance().insertOrderHandlerLogs(logReq);
		}
		logger.info("================自动写卡队列订单号：" + order_id + "\t开启标志：" + flag);
		if (flag) { // 插入PC批量写卡队列
			// 保存队列编码
			OrderWMSKeyInfoBusiRequest orderWMSReq = orderTree.getOrderWMSKeyInfoBusiRequest();
			orderWMSReq.setWrite_machine_code(queue_code);
			orderWMSReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderWMSReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderWMSReq.store();
			logger.info("================自动写卡队列订单号：" + order_id + "\t执行自动化关键信息状态记录表修改：" + queue_code);
		} else { // 转人工集中生产模式
			logger.info("================自动写卡队列订单号：" + order_id + "转人工集中生产模式");
			String new_order_model = null;
			String order_model = order_ext.getOrder_model();
			if (EcsOrderConsts.ORDER_MODEL_08.equals(order_model)) {
				new_order_model = EcsOrderConsts.ORDER_MODEL_07;
			} else if (EcsOrderConsts.ORDER_MODEL_06.equals(order_model)) {
				new_order_model = EcsOrderConsts.ORDER_MODEL_02;
			}
			order_ext.setOrder_model(new_order_model);
			order_ext.setOld_order_model(order_model);
			order_ext.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			order_ext.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			order_ext.store();
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ORDER_MODEL },
					new String[] { new_order_model });
		}

		result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
		result.setError_msg("成功");
		return result;
	}

	/**
	 * 保存受理单信息、保存货品表中开户结果相关字段
	 * 
	 * @param orderItemBusiRequests
	 * @param param
	 */
	private void saveAcceptanceInfos(AcceptanceParamVo param, OrderTreeBusiRequest orderTree) throws ApiBusiException {
		if (orderTree == null) {
			orderTree = CommonDataFactory.getInstance().getOrderTree(param.getNotNeedReqStrOrderId());
		}

		OrderItemsAptPrintsBusiRequest orderItemsAptPrintsBusiRequest = null;
		List<OrderItemsAptPrintsBusiRequest> orderItemsAptPrintsRequestList = orderTree.getOrderItemBusiRequests()
				.get(0).getOrderItemsAptPrintsRequests();
		if (!orderItemsAptPrintsRequestList.isEmpty()) {
			orderItemsAptPrintsBusiRequest = orderItemsAptPrintsRequestList.get(0);
			orderItemsAptPrintsBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		} else {
			orderItemsAptPrintsBusiRequest = new OrderItemsAptPrintsBusiRequest();
			orderItemsAptPrintsRequestList.add(orderItemsAptPrintsBusiRequest);
			orderItemsAptPrintsBusiRequest.setOrder_id(param.getNotNeedReqStrOrderId());
			orderItemsAptPrintsBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
		}
		orderItemsAptPrintsBusiRequest.setReceipt_code(param.getAcceptanceTp());
		orderItemsAptPrintsBusiRequest.setReceipt_mode(param.getAcceptanceMode());

		String acceptanceForm = param.getAcceptanceForm();
		if (StringUtils.isNotEmpty(acceptanceForm)) {
			String accept_form = "";

			// 将各种转义符都替换成人话
			Map map = new HashMap();
			map = JsonUtil.fromJson(acceptanceForm, Map.class);
			for (int i = 1; i <= 20; i++) {
				String accept_info = (String) map.get("RECEIPT_INFO" + i);
				if (accept_info != null && !"null".equals(accept_info)) {
					accept_form += accept_info;
				}
			}
			accept_form = accept_form.replaceAll("^^", " ").replaceAll("lt;", "<").replaceAll("gt;", ">")
					.replaceAll("zt;", "/").replaceAll("lp;", "{").replaceAll("rp;", "}").replaceAll("np;", "\"")
					.replaceAll("amp;", "&").replaceAll("iexcl;", "\\?").replaceAll("~", "<br>").replaceAll("^", " ")
					.replace("^^", " ");
			
			//  add by zhaochen 20190603 分隔代码还是有问题，
			// 有时分隔的长度还是会超出4000，先将分隔的长度改为配置，后续再优化
			int split_size = 4000;
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String size = cacheUtil.getConfigInfo("SPLIT_STRING_4_ORACLE");
			if(org.apache.commons.lang.StringUtils.isNotBlank(size) 
					&& org.apache.commons.lang.StringUtils.isNumeric(size)){
				split_size = Integer.valueOf(size);
			}
			// end
			
			List<String> splitList = null;
			try {
				splitList = StringUtil.getSplitStrings4Oracle(accept_form, split_size);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new ApiBusiException(e.getMessage());
			}
			
			if(splitList.size() > 0 && org.apache.commons.lang.StringUtils.isNotBlank(splitList.get(0))){
				orderItemsAptPrintsBusiRequest.setAcceptance_html(splitList.get(0));
			}
			
			if(splitList.size() > 1 && org.apache.commons.lang.StringUtils.isNotBlank(splitList.get(1))){
				orderItemsAptPrintsBusiRequest.setAcceptance_html_2(splitList.get(1));
			}
			
			if(splitList.size() > 2 && org.apache.commons.lang.StringUtils.isNotBlank(splitList.get(2))){
				orderItemsAptPrintsBusiRequest.setAcceptance_html_3(splitList.get(2));
			}

			orderItemsAptPrintsBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemsAptPrintsBusiRequest.store();
		}
		// 更新货品记录开户信息相关字段
		OrderItemBusiRequest orderItemBusi = orderTree.getOrderItemBusiRequests().get(0);
		OrderItemExtBusiRequest orderItemBusiExt = orderItemBusi.getOrderItemExtBusiRequest();
		orderItemBusiExt.setAccount_status(EcsOrderConsts.ACCOUNT_STATUS_1);
		orderItemBusiExt.setAccount_time(dateformat.format(new Date()));
		orderItemBusiExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderItemBusiExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderItemBusiExt.store();
	}

	/**
	 * [宽带用户]宽带受理费用查询
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	@Override
	public BusiDealResult openAccountBroadbandFee(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		QryBroadbandFeeReq req = new QryBroadbandFeeReq();// 宽带费用请求参数类
		req.setNotNeedReqStrOrderId(order_id);// 订单系统内部订单号

		QryBroadbandFeeResponse rsp = client.execute(req, QryBroadbandFeeResponse.class);

		if (!StringUtils.equals(rsp.getCode(), EcsOrderConsts.BSS_SUCCESS_00000)) {// 失败
			// 设置错误返回码
			result.setError_code(rsp.getCode());
			result.setError_msg(rsp.getMsg());
		} else {// 成功
			result.setError_code(EcsOrderConsts.BSS_SUCCESS_00000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * [宽带用户]开户预受理申请
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	@Override
	public BusiDealResult openAccountApplyBroadband(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String goods_id = orderTree.getOrderItemBusiRequests().get(0).getGoods_id();
		String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, goods_id, "type_id");
		if (!StringUtil.isEmpty(type_id) && StringUtils.equals(type_id, SpecConsts.TYPE_ID_20021)) {
			BroadbandPreSubReq req = new BroadbandPreSubReq();// 宽带预受理请求参数类
			req.setNotNeedReqStrOrderId(order_id);// 订单系统内部订单号

			BroadbandPreSubResp rsp = client.execute(req, BroadbandPreSubResp.class);

			if (!StringUtils.equals(rsp.getCode(), EcsOrderConsts.BSS_SUCCESS_00000)) {// 失败
				// 标记异常(订单转异常单)
				Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_OPEN, rsp.getError_msg());
				// 设置错误返回码
				result.setError_code(rsp.getCode());
				result.setError_msg(rsp.getMsg());

			} else {// 成功
					// 保存宽带预提交订单号
				/*
				 * JSONObject respJson = JSON.parseObject(rsp.getRespJson());
				 * String serial_num = respJson.getString("bms_accept_id");
				 * CommonDataFactory.getInstance().updateAttrFieldValue(req.
				 * getNotNeedReqStrOrderId(), new
				 * String[]{AttrConsts.ACTIVE_NO}, new String[]{serial_num});
				 */
				result.setError_code(EcsOrderConsts.BSS_SUCCESS_00000);
				result.setError_msg("成功");
			}
			result.setResponse(rsp);
		} else if (!StringUtil.isEmpty(type_id) && "170502124302000763".equals(type_id)) {// 新装固话
			GuWangPreSubReq req = new GuWangPreSubReq();// 宽带预受理请求参数类
			req.setNotNeedReqStrOrderId(order_id);// 订单系统内部订单号

			GuWangPreSubResp rsp = client.execute(req, GuWangPreSubResp.class);

			if (!StringUtils.equals(rsp.getCode(), EcsOrderConsts.BSS_SUCCESS_00000)) {// 失败
				// 标记异常(订单转异常单)
				Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_OPEN, rsp.getError_msg());
				// 设置错误返回码
				result.setError_code(rsp.getCode());
				result.setError_msg(rsp.getMsg());
			} else {// 成功
					// 保存宽带预提交订单号
				/*
				 * JSONObject respJson = JSON.parseObject(rsp.getRespJson());
				 * String serial_num = respJson.getString("bms_accept_id");
				 * CommonDataFactory.getInstance().updateAttrFieldValue(req.
				 * getNotNeedReqStrOrderId(), new
				 * String[]{AttrConsts.ACTIVE_NO}, new String[]{serial_num});
				 */
				result.setError_code(EcsOrderConsts.BSS_SUCCESS_00000);
				result.setError_msg("成功");
			}
			result.setResponse(rsp);
		} else if (!StringUtil.isEmpty(type_id) && "170502123552000755".equals(type_id)) {// 新装沃tv
			WotvUserSubReq req = new WotvUserSubReq();// 宽带预受理请求参数类
			req.setNotNeedReqStrOrderId(order_id);// 订单系统内部订单号

			WotvUserSubResp rsp = client.execute(req, WotvUserSubResp.class);

			if (!StringUtils.equals(rsp.getCode(), EcsOrderConsts.BSS_SUCCESS_00000)) {// 失败
				// 标记异常(订单转异常单)
				Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_OPEN, rsp.getError_msg());
				// 设置错误返回码
				result.setError_code(rsp.getCode());
				result.setError_msg(rsp.getMsg());
			} else {// 成功
					// 保存宽带预提交订单号
				/*
				 * JSONObject respJson = JSON.parseObject(rsp.getRespJson());
				 * String serial_num = respJson.getString("bms_accept_id");
				 * CommonDataFactory.getInstance().updateAttrFieldValue(req.
				 * getNotNeedReqStrOrderId(), new
				 * String[]{AttrConsts.ACTIVE_NO}, new String[]{serial_num});
				 */
				result.setError_code(EcsOrderConsts.BSS_SUCCESS_00000);
				result.setError_msg("成功");
			}
			result.setResponse(rsp);
		} else if (!StringUtil.isEmpty(type_id) && "170502125012000771".equals(type_id)) {// 光改新装
			FtthPreSubReq req = new FtthPreSubReq();// 宽带预受理请求参数类
			req.setNotNeedReqStrOrderId(order_id);// 订单系统内部订单号

			FtthPreSubResp rsp = client.execute(req, FtthPreSubResp.class);

			if (!StringUtils.equals(rsp.getCode(), EcsOrderConsts.BSS_SUCCESS_00000)) {// 失败
				// 标记异常(订单转异常单)
				Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_OPEN, rsp.getError_msg());
				// 设置错误返回码
				result.setError_code(rsp.getCode());
				result.setError_msg(rsp.getMsg());
			} else {// 成功
					// 保存宽带预提交订单号
				/*
				 * JSONObject respJson = JSON.parseObject(rsp.getRespJson());
				 * String serial_num = respJson.getString("bms_accept_id");
				 * CommonDataFactory.getInstance().updateAttrFieldValue(req.
				 * getNotNeedReqStrOrderId(), new
				 * String[]{AttrConsts.ACTIVE_NO}, new String[]{serial_num});
				 */
				result.setError_code(EcsOrderConsts.BSS_SUCCESS_00000);
				result.setError_msg("成功");
			}
			result.setResponse(rsp);
		} else if (!StringUtil.isEmpty(type_id) && "170502112412000711".equals(type_id)) {// 融合业务
			GroupOrderSubReq req = new GroupOrderSubReq();// 宽带预受理请求参数类
			req.setNotNeedReqStrOrderId(order_id);// 订单系统内部订单号

			GroupOrderSubResp rsp = client.execute(req, GroupOrderSubResp.class);

			if (!StringUtils.equals(rsp.getCode(), EcsOrderConsts.BSS_SUCCESS_00000)) {// 失败
				// 标记异常(订单转异常单)
				Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_OPEN, rsp.getError_msg());
				// 设置错误返回码
				result.setError_code(rsp.getCode());
				result.setError_msg(rsp.getMsg());
			} else {// 成功
					// 保存宽带预提交订单号
				/*
				 * JSONObject respJson = JSON.parseObject(rsp.getRespJson());
				 * String serial_num = respJson.getString("bms_accept_id");
				 * CommonDataFactory.getInstance().updateAttrFieldValue(req.
				 * getNotNeedReqStrOrderId(), new
				 * String[]{AttrConsts.ACTIVE_NO}, new String[]{serial_num});
				 */
				result.setError_code(EcsOrderConsts.BSS_SUCCESS_00000);
				result.setError_msg("成功");
			}
			result.setResponse(rsp);
		}else if(!StringUtil.isEmpty(type_id) &&"251723379126562816".equals(type_id)){
		    // 智慧到家
            WisdomHomePreSubReq req = new WisdomHomePreSubReq();// 智慧到家
            req.setNotNeedReqStrOrderId(order_id);// 订单系统内部订单号
            WisdomHomeSubResp rsp = client.execute(req, WisdomHomeSubResp.class);
            if (!StringUtils.equals(rsp.getCode(), EcsOrderConsts.BSS_SUCCESS_00000)) {// 失败
                // 标记异常(订单转异常单)
                Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_OPEN, rsp.getError_msg());
                // 设置错误返回码
                result.setError_code(rsp.getCode());
                result.setError_msg(rsp.getMsg());
            } else {// 成功
                result.setError_code(EcsOrderConsts.BSS_SUCCESS_00000);
                result.setError_msg("成功");
            }
            result.setResponse(rsp);
		} else if(!StringUtil.isEmpty(type_id) &&"90000000000000621".equals(type_id)){
		    // 押金
			DepositOrderReq req = new DepositOrderReq();
            req.setNotNeedReqStrOrderId(order_id);// 订单系统内部订单号
            DepositSubResp rsp = client.execute(req, DepositSubResp.class);
            if (!StringUtils.equals(rsp.getCode(), EcsOrderConsts.BSS_SUCCESS_00000)) {// 失败
                // 标记异常(订单转异常单)
                Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_OPEN, rsp.getError_msg());
                // 设置错误返回码
                result.setError_code(rsp.getCode());
                result.setError_msg(rsp.getMsg());
            } else {// 成功
                result.setError_code(EcsOrderConsts.BSS_SUCCESS_00000);
                result.setError_msg("成功");
            }
            result.setResponse(rsp);
		}
		return result;
	}

	/**
	 * [宽带用户]正式开户提交
	 * 
	 * @param order_id
	 * @return
	 */
	@Override
	public BusiDealResult openAccountSubmitBroadband(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		BroadbandOrderSubReq req = new BroadbandOrderSubReq();// 宽带正式提交请求参数类
		req.setNotNeedReqStrOrderId(order_id);// 订单系统内部订单号
		String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);//地市// 4位
        EssOperatorInfoUtil essinfo = new EssOperatorInfoUtil();
        EmpOperInfoVo essOperInfo = null;
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String groupFlowKg = cacheUtil.getConfigInfo("groupFlowKg");
        String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);//地市
        String cat_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);//地市
        if(StringUtils.isNotEmpty(groupFlowKg) &&"1".equals(groupFlowKg)&&"10093".equals(order_from) && "221668199563784192".equals(cat_id)){
             essOperInfo = essinfo.getEmpInfoFromDataBase(null,"WFGROUPUPINTENT",order_city_code);//无线宽带线上取默认的渠道和工号信息
        }
        if(essOperInfo != null){
         String    operatorId = essOperInfo.getEss_emp_id();//操作员
         String  offid = essOperInfo.getDep_id();//操作点
          req.setOperator_id(operatorId);
          req.setOffice_id(offid);
        }
		BroadbandOrderSubResp rsp = client.execute(req, BroadbandOrderSubResp.class);

		if (!StringUtils.equals(rsp.getCode(), EcsOrderConsts.BSS_SUCCESS_00000)) {// 开户失败
			// 设置错误返回码
			result.setError_code(rsp.getCode());
			result.setError_msg(rsp.getMsg());

			// 标记异常(订单转异常单)
			Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_OPEN, rsp.getMsg());

			// 更新开户状态
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACCOUNT_STATUS },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_0 });
		} else {// 开户成功
				// 保存受理单信息、保存货品表中开户结果相关字段
			AcceptanceParamVo param = new AcceptanceParamVo();
			// if(!StringUtils.isEmpty(rsp.getAcceptanceForm()))param.setAcceptanceForm(rsp.getAcceptanceForm());
			// if(!StringUtils.isEmpty(rsp.getAcceptanceMode()))param.setAcceptanceMode(rsp.getAcceptanceMode());
			// if(!StringUtils.isEmpty(rsp.getAcceptanceTp()))param.setAcceptanceTp(rsp.getAcceptanceTp());
			if (!DataUtil.checkFieldValueNull(param)) {
				param.setNotNeedReqStrOrderId(order_id);
				saveAcceptanceInfos(param);
			}

			// 标记主商品附加产品办理成功
			List<OrderSubProductBusiRequest> subProductLists = CommonDataFactory.getInstance().getOrderTree(order_id)
					.getOrderSubProductBusiRequest();
			List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance()
					.getOrderTree(order_id).getAttrPackageSubProdBusiRequest();
			if (attrPackageSubProdList != null && attrPackageSubProdList.size() > 0 && subProductLists != null
					&& subProductLists.size() > 0) {
				for (OrderSubProductBusiRequest subProduct : subProductLists) {
					if (!StringUtils.equals("0", subProduct.getProd_inst_id()))
						continue;
					for (AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList) { // 设置线上办理成功
						if (StringUtils.equals(subProduct.getSub_pro_inst_id(), subPackage.getSubProd_inst_id())
								&& (StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_0, subPackage.getStatus())
										|| StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1,
												subPackage.getStatus()))) { // 取待办理、办理失败的记录
							subPackage.setSub_bss_order_id(
									CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACTIVE_NO));
							subPackage.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
							subPackage.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							subPackage.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							subPackage.store();
						}
					}
				}
			}

			// 更新各种开户状态和结果
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ACCOUNT_STATUS, AttrConsts.BSS_ACCOUNT_TIME },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_1, dateformat.format(new Date()) });

			// 开户结果要通知新商城：仅通知，不用考虑新商城接受的状态。流程运行到写卡环节，获取到总部的写卡数据时还要通知新商城
			/*
			 * StatuSynchReq statuSyn = new
			 * StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_D,
			 * EcsOrderConsts.DIC_ORDER_NODE_D_DESC,EcsOrderConsts.
			 * ACCOUNT_STATUS_1,EcsOrderConsts.ACCOUNT_STATUS_1_DESC,"");
			 * CommonDataFactory.getInstance().notifyNewShop(statuSyn);
			 */

			// 半成卡一键开户,通知新商城业务办理环节
			String is_write_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
					AttrConsts.IS_WRITE_CARD);
			if (EcsOrderConsts.IS_WRITE_CARD_NO.equals(is_write_card)) {// 不用写卡(半成卡)才在这里通知新商城业务办理;成卡(也不用写卡)的订单不会走这个业务组件

				List<AttrGiftInfoBusiRequest> giftInfoList = CommonDataFactory.getInstance().getOrderTree(order_id)
						.getGiftInfoBusiRequests();
				for (AttrGiftInfoBusiRequest giftInfo : giftInfoList) {
					String bss_status = giftInfo.getBss_status();
					if (EcsOrderConsts.GIFT_BSS_STATUS_1.equals(bss_status)
							|| EcsOrderConsts.GIFT_BSS_STATUS_3.equals(bss_status)) {
						logger.info("更新订单bss办理状态，id：" + order_id + "  ");
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
								new String[] { AttrConsts.BSS_STATUS }, new String[] { EcsOrderConsts.BSS_STATUS_0 });
						break;
					}
				}
			}

			result.setError_code(EcsOrderConsts.BSS_SUCCESS_00000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * [宽带用户]开户状态同步码上购系统(该组件没有对应接口调用)
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	@Override
	public BusiDealResult accountStatusSynToO2M(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		// 获取开户状态
		String accountStatus = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACCOUNT_STATUS);
		ZteResponse rsp = null;
		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.BSS_SUCCESS_00000)) {// 同步开户状态失败
			// 设置错误返回码
			result.setError_code(rsp.getError_code());
			result.setError_msg(rsp.getError_msg());
		} else {// 同步开户状态成功
			result.setError_code(EcsOrderConsts.BSS_SUCCESS_00000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * [宽带用户]接收省分BSS施工结果
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@Override
	public BusiDealResult receiveConstructionStatus(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		OrderInfoUpdateReq req = (OrderInfoUpdateReq) fact.getRequest();
		String order_id = fact.getOrder_id();
		// 如果返回施工完成，订单正常流转；如果返回施工异常，订单转入异常单并发起退单。(童欣说施工异常可以转异常单，但不立即发起退单操作，由页面发起退单操作)
		if (req != null && StringUtils.isNotEmpty(req.getWork_result())
				&& !StringUtils.equals("0", req.getWork_result())) {// 接收省份BSS施工状态失败
			// 标记异常(订单转异常单)
			Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_BSS, "施工异常");
			// 更新订单系统竣工状态失败
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_STATUS },
					new String[] { ZjEcsOrderConsts.BSS_STATUS_3 });
			// 设置错误返回码
			result.setError_code("1");
			result.setError_msg("竣工失败");
		} else {// 接收省份BSS施工状态成功
				// 更新订单系统竣工状态
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_STATUS },
					new String[] { ZjEcsOrderConsts.BSS_STATUS_2 });
			result.setError_code(EcsOrderConsts.BSS_SUCCESS_00000);
			result.setError_msg("成功");
		}
		// result.setResponse(rsp);
		return result;
	}

	/**
	 * [宽带用户]BSS施工结果同步码上购(该组件没有对应接口调用)
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	@Override
	public BusiDealResult constructionStatusSynToO2M(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		// 获取施工状态
		String constructionStatus = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_STATUS);
		String status = "04";// 传入到接口的状态
		if (StringUtils.isNotEmpty(constructionStatus)
				&& StringUtils.equals(constructionStatus, ZjEcsOrderConsts.BSS_STATUS_2)) {
			status = "04";// 竣工成功
		} else {
			status = "-3";// 竣工失败
		}
		ZteResponse rsp = null;
		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.BSS_SUCCESS_00000)) {// 同步施工状态失败
			// 设置错误返回码
			result.setError_code(rsp.getError_code());
			result.setError_msg(rsp.getError_msg());
		} else {// 同步施工状态成功
			result.setError_code(EcsOrderConsts.BSS_SUCCESS_00000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * [号卡]AOP号卡开户预提交
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@Override
	public BusiDealResult openAccountPreCommitApplyAop(String order_id) throws ApiBusiException {
		// 获取订单树
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
		OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();

		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OpenDealApplyResp rsp = new OpenDealApplyResp();
		OpenDealApplyReq openActReq = new OpenDealApplyReq();
		openActReq.setNotNeedReqStrOrderId(order_id);
		// 接口调用开户申请
		rsp = client.execute(openActReq, OpenDealApplyResp.class);

		// rsp = new OpenDealApplyResp();
		// rsp.setError_code(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200+"");
		// 获取环节
		String flowNode = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.TYPE_ID);
		if (StringUtils.equals(rsp.getError_code(), String.valueOf(EcsOrderConsts.AOP_SUCCESS_0000))
				|| StringUtils.equals(rsp.getError_code(), String.valueOf(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200))) {
			orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemExtBusiRequest.store();
			orderItemExtBusiRequest.setBssOrderId(rsp.getBssOrderId());
			// 更新开户流水号
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACTIVE_NO },
					new String[] { rsp.getProvOrderId() });

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		} else {
			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getDetail()) ? rsp.getCode() : rsp.getDetail()));
			if (!StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)) {
				// 标记异常
				// Utils.markException(order_id,false);
				Utils.markException(order_id, false, "", result.getError_msg());
			} else {
				orderItemExtBusiRequest.setEss_pre_status(EcsOrderConsts.ESS_PRE_STATUS_0);
				orderItemExtBusiRequest.setEss_pre_desc("错误编码：" + rsp.getCode() + ";错误信息：" + rsp.getDetail());
				orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderItemExtBusiRequest.store();
			}

			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getDetail()) ? rsp.getCode() : rsp.getDetail()));
		}
		result.setResponse(rsp);
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String xx_card_center = cacheUtil.getConfigInfo("xx_card_center");//号卡-总部（AOP）
        Boolean flag = false;
        if(!StringUtils.isEmpty(xx_card_center) && xx_card_center.contains(type_id)){
                flag = true;
        }
		if (flag ||StringUtil.equals(type_id, SpecConsts.TYPE_ID_GOODS_CBSS) || "226723870818693120".equals(type_id)) {// 号卡-总部（AOP）
			ThreadLocalUtil.setProperty(order_id, rsp);
		}
		return result;
	}

	/**
	 * [号卡]AOP总部号卡开户预提交zj
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@Override
	public BusiDealResult openAccountPreCommitApplyAopZJ(String order_id) throws ApiBusiException {
		// 获取订单树
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
		OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();

		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OpenDealApplyResp rsp = new OpenDealApplyResp();
		OpenDealApplyReqZJ openActReq = new OpenDealApplyReqZJ();
		openActReq.setNotNeedReqStrOrderId(order_id);
		// 接口调用开户申请
		rsp = client.execute(openActReq, OpenDealApplyResp.class);

		// rsp = new OpenDealApplyResp();
		// rsp.setError_code(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200+"");
		// 获取环节
		String flowNode = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.TYPE_ID);
		if (StringUtils.isEmpty(rsp.getCode())
				&& StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
			orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemExtBusiRequest.setBssOrderId(rsp.getBssOrderId());
			orderItemExtBusiRequest.store();
			// 更新开户流水号
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACTIVE_NO },
					new String[] { rsp.getProvOrderId() });
			List<FeeInfoRspVo> feeInfoLs = rsp.getFeeInfo();
			if (feeInfoLs != null && feeInfoLs.size() > 0) {
				AttrFeeInfoBusiRequest feenew = new AttrFeeInfoBusiRequest();
				for (FeeInfoRspVo feeInfo : feeInfoLs) {
					feenew.setOrder_id(order_id);
					feenew.setInst_id(order_id);
					feenew.setFee_inst_id(daoSupport.getSequences("SEQ_ATTR_FEE_INFO", "1", 18));
					feenew.setFee_item_code(feeInfo.getFeeId());
					feenew.setFee_item_name(feeInfo.getFeeDes());
					feenew.setO_fee_num(feeInfo.getOrigFee());
					feenew.setDiscount_fee(feeInfo.getMaxRelief());
					feenew.setN_fee_num(feeInfo.getMaxRelief());
					feenew.setFee_category(feeInfo.getFeeCategory());
					feenew.setMax_relief(feeInfo.getMaxRelief());
					feenew.setIs_aop_fee(EcsOrderConsts.BASE_YES_FLAG_1);
					feenew.setDb_action(ConstsCore.DB_ACTION_INSERT);// 操作
					feenew.setIs_dirty(ConstsCore.IS_DIRTY_YES);// 脏数据处理
					feenew.store();
				}
			}

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		} else {
			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getDetail()) ? rsp.getCode() : rsp.getDetail()));
			if (!StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)) {
				// 标记异常
				// Utils.markException(order_id,false);
				Utils.markException(order_id, false, "", result.getError_msg());
			} else {
				orderItemExtBusiRequest.setEss_pre_status(EcsOrderConsts.ESS_PRE_STATUS_0);
				orderItemExtBusiRequest.setEss_pre_desc("错误编码：" + rsp.getCode() + ";错误信息：" + rsp.getDetail());
				orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderItemExtBusiRequest.store();
			}

			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getDetail()) ? rsp.getCode() : rsp.getDetail()));
		}
		result.setResponse(rsp);
		ThreadLocalUtil.setProperty(order_id, rsp);
		return result;
	}

	/**
	 * BSS号卡开户预提交
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@Override
	public BusiDealResult openAccountPreCommitApplyBss(String order_id) throws ApiBusiException {
		// 获取订单树
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
		OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();

		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		PreCommitBSSReq req = new PreCommitBSSReq();
		req.setOrder_id(order_id);
		;
		// 接口调用开户申请
		PreCommitBSSResp rsp = new PreCommitBSSResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		Object obj = new Object();
		try {
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getOrder_id());// 记录日志
			rsp = (PreCommitBSSResp) caller.invoke("ecaop.trades.ass.account.comm.preSubs.openAcct", param);// 调用接口待查
		} catch (Exception e) {// 调用接口失败
			rsp.setCode("-9999");
			rsp.setMsg("异常错误：" + e.getMessage() == null ? e.toString() : e.getMessage());
			e.printStackTrace();
		}

		// 获取环节
		String flowNode = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		if (StringUtils.equals(rsp.getCode(), String.valueOf(EcsOrderConsts.BSS_SUCCESS_00000))
				|| StringUtils.equals(rsp.getError_code(), String.valueOf(EcsOrderConsts.BSS_SUCCESS_00000))
				|| StringUtils.equals(rsp.getError_code(), String.valueOf(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200))) {

			// 更新开户流水号(BSS接口未返回该值)
			// CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
			// new String[]{AttrConsts.ACTIVE_NO}, new
			// String[]{rsp.getProvOrderId()});
			// 需保存数据到数据库
			String serial_num = rsp.getRespJson().getSerial_num();
			/*
			 * Map map = new HashMap(); Map mapwhere = new HashMap();
			 * map.put("active_no", serial_num); map.put("bssorderid",
			 * serial_num); mapwhere.put("order_id", order_id);
			 * baseDaoSupport.update("es_order_items_ext", map, mapwhere);
			 * CommonDataFactory.getInstance().updateOrderTree(order_id);
			 */
			orderItemExtBusiRequest.setBssOrderId(serial_num);
			orderItemExtBusiRequest.setActive_no(serial_num);
			orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemExtBusiRequest.store();
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ACTIVE_NO, AttrConsts.BSSORDERID },
					new String[] { serial_num, serial_num });
			result.setError_code(EcsOrderConsts.BSS_SUCCESS_00000);
			result.setError_msg("成功");
		} else {
			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getBody()) ? rsp.getMsg() : rsp.getBody()));
			if (!StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)) {
				// 标记异常
				// Utils.markException(order_id,false);
				Utils.markException(order_id, false, "", result.getError_msg());
			} else {
				orderItemExtBusiRequest.setEss_pre_status(EcsOrderConsts.ESS_PRE_STATUS_0);
				orderItemExtBusiRequest.setEss_pre_desc("错误编码：" + rsp.getMsg() + ";错误信息：" + rsp.getBody());
				orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderItemExtBusiRequest.store();
			}
			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getBody()) ? rsp.getMsg() : rsp.getBody()));
		}
		result.setResponse(rsp);
		ThreadLocalUtil.setProperty(order_id, rsp);
		return result;
	}

	/**
	 * AOP号卡开户正式提交
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@Override
	public BusiDealResult openAccountFormalCommitApplyAop(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		OpenDealSubmitReq req = new OpenDealSubmitReq();
		req.setNotNeedReqStrOrderId(order_id);

		OpenDealSubmitResp rsp = client.execute(req, OpenDealSubmitResp.class);

		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {// 失败
			// 设置错误返回码
			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getDetail()) ? rsp.getCode() : rsp.getDetail()));

			long start1 = System.currentTimeMillis();
			// 标记异常
			// Utils.markException(order_id,false);
			Utils.markException(order_id, false, "", result.getError_msg());
			logger.info(order_id + "调用开户处理提交接口【openDealSubmitToAop】耗时：" + (System.currentTimeMillis() - start1));

			// 更新开户状态
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACCOUNT_STATUS },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_0 });
			// 记录多写卡开户异常操作日志
			OrderActionLogReq logReq = new OrderActionLogReq();
			logReq = new OrderActionLogReq();
			logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_05);
			logReq.setAction_desc("开户异常");
			logReq.setOrder_id(order_id);
			AutoWriteCardCacheTools.saveWriteCardActiveLog(logReq);
		} else {// 成功
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			// 保存受理单信息、保存货品表中开户结果相关字段
			AcceptanceParamVo param = new AcceptanceParamVo();
			if (!StringUtils.isEmpty(rsp.getAcceptanceForm()))
				param.setAcceptanceForm(rsp.getAcceptanceForm());
			if (!StringUtils.isEmpty(rsp.getAcceptanceMode()))
				param.setAcceptanceMode(rsp.getAcceptanceMode());
			if (!StringUtils.isEmpty(rsp.getAcceptanceTp()))
				param.setAcceptanceTp(rsp.getAcceptanceTp());
			if (!DataUtil.checkFieldValueNull(param)) {
				param.setNotNeedReqStrOrderId(order_id);
				saveAcceptanceInfos(param, orderTree);
			}

			// 标记主商品附加产品办理成功
			List<OrderSubProductBusiRequest> subProductLists = CommonDataFactory.getInstance().getOrderTree(order_id)
					.getOrderSubProductBusiRequest();
			List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance()
					.getOrderTree(order_id).getAttrPackageSubProdBusiRequest();
			if (attrPackageSubProdList != null && attrPackageSubProdList.size() > 0 && subProductLists != null
					&& subProductLists.size() > 0) {
				for (OrderSubProductBusiRequest subProduct : subProductLists) {
					if (!StringUtils.equals("0", subProduct.getProd_inst_id()))
						continue;
					for (AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList) { // 设置线上办理成功
						if (StringUtils.equals(subProduct.getSub_pro_inst_id(), subPackage.getSubProd_inst_id())
								&& (StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_0, subPackage.getStatus())
										|| StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1,
												subPackage.getStatus()))) { // 取待办理、办理失败的记录
							subPackage.setSub_bss_order_id(
									CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACTIVE_NO));
							subPackage.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
							subPackage.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							subPackage.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							subPackage.store();
						}
					}
				}
			}

			// 更新各种开户状态和结果
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ACCOUNT_STATUS, AttrConsts.ZB_STATUS, AttrConsts.ACTIVE_NO,
							AttrConsts.BSS_ACCOUNT_TIME, AttrConsts.BSS_OPERATOR, AttrConsts.BSS_OPERATOR_NAME,
							AttrConsts.BSSORDERID, AttrConsts.ZB_ESS_ORDER_ID, AttrConsts.TAX_NO },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_1, EcsOrderConsts.ZB_ORDER_STATE_N06,
							rsp.getProvOrderId(), dateformat.format(new Date()), req.getOperatorId(),
							req.getEssOperInfo().getStaffName(), rsp.getBssOrderId(), rsp.getEssOrderId(),
							rsp.getTaxNo() });

			// 开户结果要通知新商城：仅通知，不用考虑新商城接受的状态。流程运行到写卡环节，获取到总部的写卡数据时还要通知新商城
			/*
			 * StatuSynchReq statuSyn = new
			 * StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_D,
			 * EcsOrderConsts.DIC_ORDER_NODE_D_DESC,EcsOrderConsts.
			 * ACCOUNT_STATUS_1,EcsOrderConsts.ACCOUNT_STATUS_1_DESC,"");
			 * CommonDataFactory.getInstance().notifyNewShop(statuSyn);
			 */

			// 半成卡一键开户,通知新商城业务办理环节
			String is_write_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
					AttrConsts.IS_WRITE_CARD);
			if (EcsOrderConsts.IS_WRITE_CARD_NO.equals(is_write_card)) {// 不用写卡(半成卡)才在这里通知新商城业务办理;成卡(也不用写卡)的订单不会走这个业务组件

				List<AttrGiftInfoBusiRequest> giftInfoList = CommonDataFactory.getInstance().getOrderTree(order_id)
						.getGiftInfoBusiRequests();
				for (AttrGiftInfoBusiRequest giftInfo : giftInfoList) {
					String bss_status = giftInfo.getBss_status();
					if (EcsOrderConsts.GIFT_BSS_STATUS_1.equals(bss_status)
							|| EcsOrderConsts.GIFT_BSS_STATUS_3.equals(bss_status)) {
						logger.info("更新订单bss办理状态，id：" + order_id + "  ");
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
								new String[] { AttrConsts.BSS_STATUS }, new String[] { EcsOrderConsts.BSS_STATUS_0 });
						break;
					}
				}

				// 通知新商城业务办理环节
				/*
				 * StatuSynchReq statuSynY = new
				 * StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_Y,
				 * EcsOrderConsts.DIC_ORDER_NODE_Y_DESC,EcsOrderConsts.
				 * BSS_STATUS_1,EcsOrderConsts.BSS_STATUS_1_DESC,"");
				 * CommonDataFactory.getInstance().notifyNewShop(statuSynY);
				 */
			}

			// 记录多写卡开户完成操作日志
			OrderActionLogReq logReq = new OrderActionLogReq();
			logReq = new OrderActionLogReq();
			logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_04);
			logReq.setAction_desc("开户完成");
			logReq.setOrder_id(order_id);
			AutoWriteCardCacheTools.saveWriteCardActiveLog(logReq);
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * AOP号卡开户正式提交
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@Override
	public BusiDealResult openAccountFormalCommitApplyAopZJ(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		OpenDealSubmitReqZJ req = new OpenDealSubmitReqZJ();
		req.setNotNeedReqStrOrderId(order_id);

		OpenDealSubmitResp rsp = client.execute(req, OpenDealSubmitResp.class);

		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")
				|| !StringUtils.isEmpty(rsp.getCode())) {// 失败
			// 设置错误返回码
			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getDetail()) ? rsp.getCode() : rsp.getDetail()));

			long start1 = System.currentTimeMillis();
			// 标记异常
			// Utils.markException(order_id,false);
			Utils.markException(order_id, false, "", result.getError_msg());
			logger.info(order_id + "调用开户处理提交接口【openDealSubmitToAop】耗时：" + (System.currentTimeMillis() - start1));

			// 更新开户状态
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACCOUNT_STATUS },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_0 });
			// 记录多写卡开户异常操作日志
			OrderActionLogReq logReq = new OrderActionLogReq();
			logReq = new OrderActionLogReq();
			logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_05);
			logReq.setAction_desc("开户异常");
			logReq.setOrder_id(order_id);
			AutoWriteCardCacheTools.saveWriteCardActiveLog(logReq);
		} else {// 成功
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			// 保存受理单信息、保存货品表中开户结果相关字段
			AcceptanceParamVo param = new AcceptanceParamVo();
			if (!StringUtils.isEmpty(rsp.getAcceptanceForm()))
				param.setAcceptanceForm(rsp.getAcceptanceForm());
			if (!StringUtils.isEmpty(rsp.getAcceptanceMode()))
				param.setAcceptanceMode(rsp.getAcceptanceMode());
			if (!StringUtils.isEmpty(rsp.getAcceptanceTp()))
				param.setAcceptanceTp(rsp.getAcceptanceTp());
			if (!DataUtil.checkFieldValueNull(param)) {
				param.setNotNeedReqStrOrderId(order_id);
				saveAcceptanceInfos(param, orderTree);
			}

			// 标记主商品附加产品办理成功
			List<OrderSubProductBusiRequest> subProductLists = CommonDataFactory.getInstance().getOrderTree(order_id)
					.getOrderSubProductBusiRequest();
			List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance()
					.getOrderTree(order_id).getAttrPackageSubProdBusiRequest();
			if (attrPackageSubProdList != null && attrPackageSubProdList.size() > 0 && subProductLists != null
					&& subProductLists.size() > 0) {
				for (OrderSubProductBusiRequest subProduct : subProductLists) {
					if (!StringUtils.equals("0", subProduct.getProd_inst_id()))
						continue;
					for (AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList) { // 设置线上办理成功
						if (StringUtils.equals(subProduct.getSub_pro_inst_id(), subPackage.getSubProd_inst_id())
								&& (StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_0, subPackage.getStatus())
										|| StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1,
												subPackage.getStatus()))) { // 取待办理、办理失败的记录
							subPackage.setSub_bss_order_id(
									CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACTIVE_NO));
							subPackage.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
							subPackage.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							subPackage.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							subPackage.store();
						}
					}
				}
			}

			// 更新各种开户状态和结果
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ACCOUNT_STATUS, AttrConsts.ZB_STATUS, AttrConsts.ACTIVE_NO,
							AttrConsts.BSS_ACCOUNT_TIME, AttrConsts.BSS_OPERATOR, AttrConsts.BSS_OPERATOR_NAME,
							AttrConsts.BSSORDERID, AttrConsts.ZB_ESS_ORDER_ID, AttrConsts.TAX_NO },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_1, EcsOrderConsts.ZB_ORDER_STATE_N06,
							rsp.getProvOrderId(), dateformat.format(new Date()), req.getOperatorId(), "",
							rsp.getBssOrderId(), rsp.getEssOrderId(), rsp.getTaxNo() });

			// 开户结果要通知新商城：仅通知，不用考虑新商城接受的状态。流程运行到写卡环节，获取到总部的写卡数据时还要通知新商城
			/*
			 * StatuSynchReq statuSyn = new
			 * StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_D,
			 * EcsOrderConsts.DIC_ORDER_NODE_D_DESC,EcsOrderConsts.
			 * ACCOUNT_STATUS_1,EcsOrderConsts.ACCOUNT_STATUS_1_DESC,"");
			 * CommonDataFactory.getInstance().notifyNewShop(statuSyn);
			 */

			// 半成卡一键开户,通知新商城业务办理环节
			String is_write_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
					AttrConsts.IS_WRITE_CARD);
			if (EcsOrderConsts.IS_WRITE_CARD_NO.equals(is_write_card)) {// 不用写卡(半成卡)才在这里通知新商城业务办理;成卡(也不用写卡)的订单不会走这个业务组件

				List<AttrGiftInfoBusiRequest> giftInfoList = CommonDataFactory.getInstance().getOrderTree(order_id)
						.getGiftInfoBusiRequests();
				for (AttrGiftInfoBusiRequest giftInfo : giftInfoList) {
					String bss_status = giftInfo.getBss_status();
					if (EcsOrderConsts.GIFT_BSS_STATUS_1.equals(bss_status)
							|| EcsOrderConsts.GIFT_BSS_STATUS_3.equals(bss_status)) {
						logger.info("更新订单bss办理状态，id：" + order_id + "  ");
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
								new String[] { AttrConsts.BSS_STATUS }, new String[] { EcsOrderConsts.BSS_STATUS_0 });
						break;
					}
				}

				// 通知新商城业务办理环节
				/*
				 * StatuSynchReq statuSynY = new
				 * StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_Y,
				 * EcsOrderConsts.DIC_ORDER_NODE_Y_DESC,EcsOrderConsts.
				 * BSS_STATUS_1,EcsOrderConsts.BSS_STATUS_1_DESC,"");
				 * CommonDataFactory.getInstance().notifyNewShop(statuSynY);
				 */
			}

			// 记录多写卡开户完成操作日志
			OrderActionLogReq logReq = new OrderActionLogReq();
			logReq = new OrderActionLogReq();
			logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_04);
			logReq.setAction_desc("开户完成");
			logReq.setOrder_id(order_id);
			AutoWriteCardCacheTools.saveWriteCardActiveLog(logReq);
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * [号卡]AOP 卡信息获取
	 * 
	 * @param order_id
	 * @return
	 */
	@Override
	public BusiDealResult cardInfoGet(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();

		CardDataQryRequest req = new CardDataQryRequest();
		req.setNotNeedReqStrOrderId(order_id);
		CardDataQryResponse rsp = new CardDataQryResponse();

		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL)
					|| essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				com.ztesoft.common.util.BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());
				String opcode = EcsOrderConsts.CARD_DATE_QUERY;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", "TS-3G-005");
				rsp = (CardDataQryResponse) caller.invoke(opcode, param);
				// 缓存报文
				ThreadLocalUtil.setProperty(order_id, rsp);
				// 保存字段到数据库
				Map map = new HashMap();
				Map mapwhere = new HashMap();
				map.put("active_id", rsp.getActiveId());
				map.put("capacity_type_code", rsp.getCapacityTypeCode());
				map.put("simid", rsp.getImsi());
				map.put("proc_id", rsp.getProcId());
				map.put("res_kind_code", rsp.getResKindCode());
				map.put("script_seq", rsp.getScriptSeq());
				mapwhere.put("order_id", order_id);
				baseDaoSupport.update("es_order_extvtl", map, mapwhere);
				map.clear();
				map.put("card_data", rsp.getCardData());
				baseDaoSupport.update("es_order_items_ext", map, mapwhere);
				CommonDataFactory.getInstance().updateOrderTree(order_id);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}

		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {// 失败
			// 设置错误返回码
			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getDetail()) ? rsp.getError_msg() : rsp.getDetail()));

			long start1 = System.currentTimeMillis();
			// 标记异常
			// Utils.markException(order_id,false);
			Utils.markException(order_id, false, "", result.getError_msg());
			logger.info(order_id + "调用卡信息获取接口【cardInfoGet】耗时：" + (System.currentTimeMillis() - start1));

		} else {// 成功
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * [号卡]AOP 卡信息获取
	 * 
	 * @param order_id
	 * @return
	 */
	@Override
	public BusiDealResult cardInfoGetZJ(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();

		CardDataQryRequestZJ req = new CardDataQryRequestZJ();
		req.setNotNeedReqStrOrderId(order_id);
		CardDataQryResponse rsp = new CardDataQryResponse();

		try {
			// 获取工号信息
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			com.ztesoft.common.util.BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			String opcode = EcsOrderConsts.CARD_DATE_QUERY_AOPZJ;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", "TS-3G-005");
			rsp = (CardDataQryResponse) caller.invoke(opcode, param);

		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}

		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")
				|| StringUtils.equals(rsp.getCode(), EcsOrderConsts.AOP_CARD_QUERY_0001 + "")
				|| StringUtils.equals(rsp.getCode(), EcsOrderConsts.AOP_CARD_QUERY_9999 + "")) {// 失败
			// 设置错误返回码
			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getDetail()) ? rsp.getError_msg() : rsp.getDetail()));

			long start1 = System.currentTimeMillis();
			// 标记异常
			// Utils.markException(order_id,false);
			Utils.markException(order_id, false, "", result.getError_msg());
			logger.info(order_id + "调用卡信息获取接口【cardInfoGet】耗时：" + (System.currentTimeMillis() - start1));

		} else {// 成功
				// 缓存报文
			ThreadLocalUtil.setProperty(order_id, rsp);
			// 保存字段到数据库
			Map map = new HashMap();
			Map mapwhere = new HashMap();
			map.put("active_id", rsp.getActiveId());
			map.put("capacity_type_code", rsp.getCapacityTypeCode());
			map.put("simid", rsp.getImsi());
			map.put("proc_id", rsp.getProcId());
			map.put("res_kind_code", rsp.getResKindCode());
			map.put("script_seq", rsp.getScriptSeq());
			mapwhere.put("order_id", order_id);
			baseDaoSupport.update("es_order_extvtl", map, mapwhere);
			map.clear();
			map.put("card_data", rsp.getCardData());
			baseDaoSupport.update("es_order_items_ext", map, mapwhere);
			CommonDataFactory.getInstance().updateOrderTree(order_id);
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * BSS 卡信息获取
	 * 
	 * @param order_id
	 * @return
	 */
	public BusiDealResult cardInfoGetBss(String order_id) throws ApiBusiException {
		// 获取订单树
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
		OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();

		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		CardInfoGetBSSReq req = new CardInfoGetBSSReq();
		req.setNotNeedReqStrOrderId(order_id);
		logger.info((String) ThreadLocalUtil.getProperty(order_id + "_region_id"));
		req.setRegion_id((String) ThreadLocalUtil.getProperty(order_id + "_region_id"));
		ThreadLocalUtil.removeProperty(order_id + "_region_id");
		// 接口调用开户申请
		CardInfoGetBSSResp rsp = new CardInfoGetBSSResp();

		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		Object obj = new Object();
		try {
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
			rsp = (CardInfoGetBSSResp) caller.invoke("ecaop.trades.serv.newu.getblanckcard.app", param);
			// rsp = (CardInfoGetBSSResp)
			// JsonUtil.fromJson("{\"code\":\"00000\",\"msg\":\"操作成功\",\"respJson\":{\"scriptseq\":\"A3636010270\",\"proc_id\":\"J3617050921263908831\",\"imsi\":\"460010845205689\"}}",
			// CardInfoGetBSSResp.class);

		} catch (Exception e) {// 调用接口失败
			rsp.setCode("-9999");
			rsp.setMsg("异常错误：" + e.getMessage() == null ? e.toString() : e.getMessage());
			e.printStackTrace();
		}

		// 获取环节
		String flowNode = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		if (StringUtils.equals(rsp.getError_code(), String.valueOf(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200))
				|| StringUtils.equals(rsp.getCode(), String.valueOf(EcsOrderConsts.BSS_SUCCESS_00000))) {
			orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemExtBusiRequest.store();
			// orderItemExtBusiRequest.setBssOrderId(rsp.getRespJson());
			// 更新开户流水号(BSS接口未返回该值)
			// CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
			// new String[]{AttrConsts.ACTIVE_NO}, new
			// String[]{rsp.getProvOrderId()});
			// 保存数据到数据库
			// 更新各种开户状态和结果
			WhiteCardInfoVO respJson = rsp.getRespJson();
			// 保存字段到数据库
			IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
			DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
			List<Map> list = dcPublicCache.getList("zb_card_script");
			String pname = "";
			for (int i = 0; i < list.size(); i++) {
				if (StringUtil.equals(respJson.getScriptseq(), (String) list.get(i).get("pkey"))) {
					pname = (String) list.get(i).get("pname");
					respJson.setScriptseq(pname);
					break;
				}
			}
			if (StringUtil.isEmpty(pname)) {
				throw new ApiBusiException("系统错误：写卡脚本转换失败");
			}
			/**
			 * Add Wcl 存入属性
			 */
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { "simid", "proc_id", "script_seq" },
					new String[] { respJson.getImsi(), respJson.getProc_id(), respJson.getScriptseq() });
			// Map map = new HashMap();
			// Map mapwhere = new HashMap();
			// map.put("simid", respJson.getImsi());
			// map.put("proc_id", respJson.getProc_id());
			// map.put("script_seq", respJson.getScriptseq());
			// mapwhere.put("order_id", order_id);
			// baseDaoSupport.update("es_order_extvtl", map, mapwhere);
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		} else {
			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getBody()) ? rsp.getMsg() : rsp.getBody()));
			if (!StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)) {
				// 标记异常
				// Utils.markException(order_id,false);
				Utils.markException(order_id, false, "", result.getError_msg());
			} else {
				orderItemExtBusiRequest.setEss_pre_status(EcsOrderConsts.ESS_PRE_STATUS_0);
				orderItemExtBusiRequest.setEss_pre_desc("错误编码：" + rsp.getCode() + ";错误信息：" + rsp.getMsg());
				orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderItemExtBusiRequest.store();
			}

			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getBody()) ? rsp.getMsg() : rsp.getBody()));
		}
		result.setResponse(rsp);
		ThreadLocalUtil.setProperty(order_id, rsp);
		return result;
	}

	/**
	 * AOP 写卡结果通知
	 * 
	 * @param order_id
	 * @return
	 */
	@Override
	public BusiDealResult writeCardResult(String order_id) throws ApiBusiException {
		return null;
	}

	/**
	 * [号卡]BSS 开户订单正式提交
	 * 
	 * @param order_id
	 * @return
	 */
	@Override
	public BusiDealResult OrderFormalSub(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		CommonDataFactory dataFactory = CommonDataFactory.getInstance();
		OrderFormalSubReq req = new OrderFormalSubReq();
		req.setNotNeedReqStrOrderId(order_id);
		req.setSerial_num(dataFactory.getAttrFieldValue(order_id, AttrConsts.PHONE_NUM));
		req.setOrder_type(dataFactory.getAttrFieldValue(order_id, AttrConsts.ORDER_TYPE));
		req.setOffice_id(dataFactory.getAttrFieldValue(order_id, AttrConsts.OUT_OFFICE));
		req.setOperator_id(dataFactory.getAttrFieldValue(order_id, AttrConsts.OUT_OPERATOR));

		BroadbandOrderSubResp rsp = client.execute(req, BroadbandOrderSubResp.class);

		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {// 失败
			// 设置错误返回码
			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getMsg()) ? rsp.getCode() : rsp.getMsg()));

			long start1 = System.currentTimeMillis();
			// 标记异常
			// Utils.markException(order_id,false);
			Utils.markException(order_id, false, "", result.getError_msg());
			logger.info(order_id + "调用订单正式提交接口【OrderFormalSub】耗时：" + (System.currentTimeMillis() - start1));

			// 更新开户状态
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACCOUNT_STATUS },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_0 });
			// //记录多写卡开户异常操作日志
			// OrderActionLogReq logReq = new OrderActionLogReq();
			// logReq = new OrderActionLogReq();
			// logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_05);
			// logReq.setAction_desc("开户异常");
			// logReq.setOrder_id(order_id);
			// AutoWriteCardCacheTools.saveWriteCardActiveLog(logReq);
		} else {// 成功

			// 标记主商品附加产品办理成功
			List<OrderSubProductBusiRequest> subProductLists = CommonDataFactory.getInstance().getOrderTree(order_id)
					.getOrderSubProductBusiRequest();
			List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance()
					.getOrderTree(order_id).getAttrPackageSubProdBusiRequest();
			if (attrPackageSubProdList != null && attrPackageSubProdList.size() > 0 && subProductLists != null
					&& subProductLists.size() > 0) {
				for (OrderSubProductBusiRequest subProduct : subProductLists) {
					if (!StringUtils.equals("0", subProduct.getProd_inst_id()))
						continue;
					for (AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList) { // 设置线上办理成功
						if (StringUtils.equals(subProduct.getSub_pro_inst_id(), subPackage.getSubProd_inst_id())
								&& (StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_0, subPackage.getStatus())
										|| StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1,
												subPackage.getStatus()))) { // 取待办理、办理失败的记录
							subPackage.setSub_bss_order_id(
									CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACTIVE_NO));
							subPackage.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
							subPackage.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							subPackage.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							subPackage.store();
						}
					}
				}
			}

			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ACCOUNT_STATUS, AttrConsts.BSS_ACCOUNT_TIME, AttrConsts.BSS_OPERATOR,
							AttrConsts.BSS_OPERATOR_NAME, AttrConsts.BSSORDERID, AttrConsts.TAX_NO },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_1, dateformat.format(new Date()) });

			// 半成卡一键开户,通知新商城业务办理环节
			String is_write_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
					AttrConsts.IS_WRITE_CARD);
			if (EcsOrderConsts.IS_WRITE_CARD_NO.equals(is_write_card)) {// 不用写卡(半成卡)才在这里通知新商城业务办理;成卡(也不用写卡)的订单不会走这个业务组件

				List<AttrGiftInfoBusiRequest> giftInfoList = CommonDataFactory.getInstance().getOrderTree(order_id)
						.getGiftInfoBusiRequests();
				for (AttrGiftInfoBusiRequest giftInfo : giftInfoList) {
					String bss_status = giftInfo.getBss_status();
					if (EcsOrderConsts.GIFT_BSS_STATUS_1.equals(bss_status)
							|| EcsOrderConsts.GIFT_BSS_STATUS_3.equals(bss_status)) {
						logger.info("更新订单bss办理状态，id：" + order_id + "  ");
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
								new String[] { AttrConsts.BSS_STATUS }, new String[] { EcsOrderConsts.BSS_STATUS_0 });
						break;
					}
				}

			}

			// //记录多写卡开户完成操作日志
			// OrderActionLogReq logReq = new OrderActionLogReq();
			// logReq = new OrderActionLogReq();
			// logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_04);
			// logReq.setAction_desc("开户完成");
			// logReq.setOrder_id(order_id);
			// AutoWriteCardCacheTools.saveWriteCardActiveLog(logReq);
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		// ThreadLocalUtil.setProperty(order_id, rsp);
		return result;
	}

	// 沃TV绑定
	@Override
	public BusiDealResult wotvBroadbandBind(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		WotvBroadbandBindReq req = new WotvBroadbandBindReq();
		req.setNotNeedReqStrOrderId(order_id);

		WotvBroadbandBindResp rsp = client.execute(req, WotvBroadbandBindResp.class);
		if (!StringUtils.equals(rsp.getCode(), EcsOrderConsts.BSS_SUCCESS_00000)) {// 失败
			// 标记异常(订单转异常单)
			Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_OPEN, rsp.getError_msg());
			// 设置错误返回码
			result.setError_code(rsp.getCode());
			result.setError_msg(rsp.getMsg());
		} else {// 成功
				// 保存宽带预提交订单号
			/*
			 * JSONObject respJson = JSON.parseObject(rsp.getRespJson()); String
			 * serial_num = respJson.getString("bms_accept_id");
			 * CommonDataFactory.getInstance().updateAttrFieldValue(req.
			 * getNotNeedReqStrOrderId(), new String[]{AttrConsts.ACTIVE_NO},
			 * new String[]{serial_num});
			 */
			result.setError_code(EcsOrderConsts.BSS_SUCCESS_00000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	public BusiDealResult manualOpenAccountSubmit(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.IS_WRITE_CARD },
				new String[] { "0" });// 总商手动开户不需要写卡
		OpenAccountSubmitOrderReq req = new OpenAccountSubmitOrderReq();
		req.setOrderNo(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID));
		req.setOrderId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_NUM));
		req.setManualUsimCardNum(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID));
		req.setManualOrderNo(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACTIVE_NO));
		OpenAccountSubmitOrderResp rsp = client.execute(req, OpenAccountSubmitOrderResp.class);
		if (!StringUtils.equals(rsp.getError_code(), ConstsCore.ERROR_SUCC)) {// 失败
			// 标记异常(订单转异常单)
			Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_OPEN, rsp.getError_msg());
			// 设置错误返回码
			result.setError_code(rsp.getError_code());
			result.setError_msg(rsp.getError_msg());
		} else {// 成功
				// 手动开户提交的订单修改开户状态为开户完成
			String sql = "update es_queue_write_card set open_account_status='2' ,open_account_time=sysdate where order_id =?";
			this.baseDaoSupport.execute(sql, order_id);
			result.setError_code(ConstsCore.ERROR_SUCC);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	public BusiDealResult electronicVolumeSend(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ICacheUtil cacheUtil = (ICacheUtil) SpringContextHolder.getBean("cacheUtil");
		/*
		 * 拼装body数据和header数据加密后，再set ElectronicVolumeSendReq数据传能开
		 */
		ElectronicVolumeSendReq req = new ElectronicVolumeSendReq();
		req.setNotNeedReqStrOrderId(order_id);
		// 拼装body数据
		ElectronicVolumeSendBody body = new ElectronicVolumeSendBody();
		body.setBsendjnl(order_id); // bss侧发放流水 我们的订单号
		req.setBsendjnl(order_id);

		body.setBssusrid("");// bss用户id cb非必传
		req.setBssusrid("");

		String eparchyCode = GetAreaInfoUtils.getEparchyCode(order_id);

		String cityCode = GetAreaInfoUtils.getAreaCode(eparchyCode);
		cityCode = cityCode.substring(1, cityCode.length()); // 区号取后3位
		body.setCitycode(cityCode);
		req.setCitycode(cityCode);

		body.setMobileno(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM));
		req.setMobileno(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM));

		try {
			String orddt = DateUtil.getTime3();
			body.setOrddt(orddt);
			req.setOrddt(orddt);

			String ordtm = DateUtil.getTime10();
			body.setOrdtm(ordtm);
			req.setOrdtm(ordtm);
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 所有货品
		List<Goods> products = SpecUtils.getAllOrderProducts(order_id);

		String qy_code = "";
		for (Goods p : products) {
			Map specs = SpecUtils.getProductSpecMap(p);
			qy_code = MapUtils.getString(specs, "qy_code");
		}
		body.setRuleid(qy_code);// 权益中心侧券规则ID 从货品中取
		req.setRuleid(qy_code);

		body.setValdt(body.getOrddt());
		req.setValdt(req.getOrddt());

		String goodsId = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0)
				.getGoods_id();
		String goodsSql = SF.goodsSql("GOODS_PACKAGE_QUERY");
		goodsSql += " and goods_id = ?";
		List<Map> goodsPkgLst = this.baseDaoSupport.queryForList(goodsSql, goodsId);

		String p_code = "";
		if (null != goodsPkgLst && goodsPkgLst.size() > 0) {
			p_code = MapUtils.getString(goodsPkgLst.get(0), "p_code");
		}

		body.setActid(p_code);
		req.setActid(p_code);

		String goodsName = CommonDataFactory.getInstance().getGoodSpec(order_id, null, "name");
		body.setActnm(goodsName);
		req.setActnm(goodsName);
		/*
		 * 1：受理成功 2：受理中 3：受理失败 4：受理异常 当前流程下，订单处理完成才发放电子卷,所以暂时都为 1:受理成功
		 */
		body.setActsts("1");
		req.setActsts("1");

		// 新增订单中心流水
		body.setSerialno(order_id);
		req.setSerialno(order_id);

		// 拼装header数据
		ElectronicVolumeSendHeader header = new ElectronicVolumeSendHeader();
		header.setApiid("100001"); // 100001 电子卷发放接口编码
		req.setApiid("100001");

		header.setBusdt(body.getOrddt());
		req.setBusdt(req.getOrddt());

		String chnno = cacheUtil.getConfigInfo("YR_CHNNO");
		header.setChnno(chnno);// 数据从配置表中取
		req.setChnno(chnno);

		String ipaddr = cacheUtil.getConfigInfo("YR_IPADDR");
		header.setIpaddr(ipaddr); // 数据从配置表中取
		req.setIpaddr(ipaddr);

		String reqjnl = this.baseDaoSupport.getSequences("req_jnl_header");
		header.setReqjnl(reqjnl);
		req.setReqjnl(reqjnl);

		try {
			String reqopetm = DateUtil.getTime5();
			header.setReqopetm(reqopetm);
			req.setReqopetm(reqopetm);
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 请求不传，返回才有内容
		header.setRespcode("");
		req.setRespcode("");

		header.setRespjnl("");
		req.setRespjnl("");

		header.setRespmsg("");
		req.setRespmsg("");

		header.setRespopetm("");
		req.setRespopetm("");

		String version = cacheUtil.getConfigInfo("YR_VERSION");
		header.setVersion(version); // 配置到数据库中
		req.setVersion(version);

		// 拼装加密签名数据
		ElectronicVolumeSendVO vo = new ElectronicVolumeSendVO();
		vo.setBody(body);
		vo.setHeader(header);

		String reqData = JSON.toJSONString(vo);

		String signKey = cacheUtil.getConfigInfo("YR_MD5_KEY"); // 0Yz8RDM72w
		String desKey = cacheUtil.getConfigInfo("YR_DES_KEY"); // Z96A5864X4
		desKey = desKey + req.getReqopetm();
		signKey = signKey + req.getReqopetm();

		CryptUtilImpl cryptUtil = new CryptUtilImpl();

		String desvalue = cryptUtil.cryptDes(desKey, desKey);
		String signatureValue = cryptUtil.cryptMd5(reqData, signKey);

		req.setDesvalue(desvalue);
		req.setSignvalue(signatureValue);

		ElectronicVolumeSendResp rsp = client.execute(req, ElectronicVolumeSendResp.class);
		/*
		 * RMP0000 交易成功 RMP9999 此接口不存在或已关闭 RMP9998 系统正忙，请稍后提交 RMP9997 渠道不存在
		 * RMP9996 渠道未开通访问该接口的权限或接口编码不存在 RMP9995 渠道IP错误或未开通访问权限 RMP9992 未知错误
		 * RMP9991 调第三方接口超时 RMP9990 系统错误 RMP9901 验证签名错误 RMP9902 验证加密数据错误 RMP9903
		 * 请求参数错误
		 */
		String respCode = rsp.getHeader().getRespcode();
		String respMsg = rsp.getHeader().getRespmsg();
		// 发卷成功
		if (StringUtils.equals(respCode, EcsOrderConsts.RMP0000)) {
			String jnl = rsp.getBodyJson().getJnl();// 权益平台发放流水号
			String userid = rsp.getBodyJson().getUsrid(); // 电子卷账号
			// Todo: 将返回信息沉淀
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "qy_seq_no", "qy_user_id" },
					new String[] { jnl, userid });

			result.setError_code(respCode);
			result.setError_msg(respMsg);
		} else {
			Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_OPEN, respMsg);
			// 设置错误返回码
			result.setError_code(respCode);
			result.setError_msg(respMsg);
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * 不同功能参数不同
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "新用户主副卡开户处理申请", summary = "新用户主副卡开户处理申请")
	public BusiDealResult mainViceOpen(String order_id, OrderTreeBusiRequest orderTree) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		MainViceOpenReq req = new MainViceOpenReq();
		MainViceOpenResp resp = new MainViceOpenResp();
		try {
			req.setNotNeedReqStrOrderId(order_id);

			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			resp = client.execute(req, MainViceOpenResp.class);
			if (resp.getResultMsg() != null && !StringUtils.isEmpty(resp.getResultMsg().getProvOrderId())) {
				OrderItemExtBusiRequest orderItemExtBusiRequest = orderTree.getOrderItemBusiRequests().get(0)
						.getOrderItemExtBusiRequest();
				orderItemExtBusiRequest.setActive_no(resp.getResultMsg().getProvOrderId());
				orderItemExtBusiRequest.setBssOrderId(resp.getResultMsg().getProvOrderId());
				orderItemExtBusiRequest.setTotalFee(resp.getResultMsg().getTotalFee());
				if (resp.getResultMsg().getFeeInfo() != null && resp.getResultMsg().getFeeInfo().size() > 0) {
					this.dealFeeInfoAopByLi(resp.getResultMsg().getFeeInfo(), order_id);
					//方法中有更新缓存的方法
					orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				}
//				orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
//				orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
//				orderItemExtBusiRequest.store();
				
				result.setError_code("0");
				result.setError_msg("成功");
			} else {
				result.setError_code(null != resp.getResultMsg() ? resp.getResultMsg().getCode()
						: resp.getError_code() + resp.getCode());
				result.setError_msg(null != resp.getResultMsg() ? resp.getResultMsg().getDetail()
						: resp.getError_msg() + resp.getDetail());
			}
		} catch (Exception e) {
			result.setError_code("-9999");
			result.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		result.setResponse(resp);
		ThreadLocalUtil.setProperty(order_id, resp);
		return result;
	}

	@Override
	public BusiDealResult broadBandOpenApplyAop(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();

		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		String goods_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0)
				.getGoods_id();
		String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, goods_id, "type_id");

		if (AopInterUtil.isAopBrdOpenApply(type_id)) {
			AopBrdOpenAppReq req;

			try {
				req = AopInterUtil.getAopBrdOpenAppReq(order_id);
			} catch (Exception e) {
				log.info(order_id + "订单预提交失败：" + e.getMessage(), e);
				throw new ApiBusiException(order_id + "订单预提交失败：" + e.getMessage());
			}

			// 调用方法ZteInfOpenService--doAopBrdOpenApp（ecaop.trades.sell.brd.sinp.open.app_aop）
			CommAopApplyRsp rsp = client.execute(req, CommAopApplyRsp.class);

			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
			OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();

			String flowNode = orderTree.getOrderExtBusiRequest().getFlow_trace_id();

			boolean isSuccess = false;
			CommAopApplyResultMsg aopMsg = null;

			if (rsp == null) {
				result.setError_code("-9999");
				result.setError_msg("能力开放平台放回信息为空");
			} else if (!"200".equals(rsp.getError_code())) {
				// 连接失败
				result.setError_code(rsp.getError_code());
				result.setError_msg(rsp.getError_msg());
			} else if (!"00000".equals(rsp.getRes_code())) {
				// 能力平台返回返回失败
				result.setError_code(rsp.getRes_code());
				result.setError_msg(rsp.getRes_message());
			} else {
				aopMsg = rsp.getResultMsg();

				if (aopMsg == null) {
					// 失败
					result.setError_code("9999");
					result.setError_msg("AOP接口返回信息为空！");
				} else if (aopMsg.getFeeInfo() != null && aopMsg.getFeeInfo().size() > 0) {
					// 成功
					result.setError_code("0");
					result.setError_msg("成功");
					isSuccess = true;
				} else {
					// 失败
					result.setError_code(aopMsg.getCode());
					result.setError_msg(
							(StringUtils.isEmpty(aopMsg.getDetail()) ? aopMsg.getCode() : aopMsg.getDetail()));
				}
			}

			if (isSuccess) {
				this.dealFeeInfoAopByLi(aopMsg.getFeeInfo(), order_id);

				orderItemExtBusiRequest.setTotalFee(aopMsg.getTotalFee());
				orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderItemExtBusiRequest.store();
				orderItemExtBusiRequest.setBssOrderId(aopMsg.getBssOrderId());
				// 更新开户流水号
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACTIVE_NO },
						new String[] { aopMsg.getProvOrderId() });
			} else {
				if (!StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)) {
					// 标记异常
					// Utils.markException(order_id,false);
					Utils.markException(order_id, false, "", result.getError_msg());
				} else {
					orderItemExtBusiRequest.setEss_pre_status(EcsOrderConsts.ESS_PRE_STATUS_0);
					orderItemExtBusiRequest
							.setEss_pre_desc("错误编码：" + rsp.getError_code() + ";错误信息：" + result.getError_msg());
					orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderItemExtBusiRequest.store();
				}
			}

			// 设置返回对象
			result.setResponse(rsp);
		} else {
			throw new ApiBusiException(order_id + "订单预提交失败：" + goods_id + "商品的商品类型不是融合业务-总部");
		}

		return result;
	}

	@Override
	public BusiDealResult broadBandOpenSubmitAop(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		String goods_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0)
				.getGoods_id();
		String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, goods_id, "type_id");

		if (AopInterUtil.isAopBrdOpenApply(type_id)) {

			AopBrdOpenSubReq req;

			try {
				req = AopInterUtil.getAopBrdOpenSubReq(order_id);
			} catch (Exception e) {
				log.info(order_id + "订单提交失败：" + e.getMessage(), e);
				throw new ApiBusiException(order_id + "订单提交失败：" + e.getMessage());
			}

			long start1 = System.currentTimeMillis();
			// 调用方法ZteInfOpenService--doAopBrdOpenSub（ecaop.trades.sell.brd.sinp.open.sub_aop）
			CommAopSubmitRsp rsp = client.execute(req, CommAopSubmitRsp.class);

			logger.info(order_id + "调用开户处理提交接口【broadBandOpenSubmitAop】耗时：" + (System.currentTimeMillis() - start1));

			boolean isSuccess = false;

			if (rsp == null) {
				result.setError_code("-9999");
				result.setError_msg("能力开放平台放回信息为空");
			} else if (!"200".equals(rsp.getError_code())) {
				// 连接失败
				result.setError_code(rsp.getError_code());
				result.setError_msg(rsp.getError_msg());
			} else if (!"00000".equals(rsp.getRes_code())) {
				// 能力平台返回返回失败
				result.setError_code(rsp.getRes_code());
				result.setError_msg(rsp.getRes_message());
			} else {
				CommAopSubmitResultMsg aopMsg = rsp.getResultMsg();

				if (aopMsg == null) {
					// 失败
					result.setError_code("9999");
					result.setError_msg("AOP接口返回信息为空！");
				} else if (StringUtils.isNotEmpty(aopMsg.getProvOrderId())) {
					// 成功
					result.setError_code("0");
					result.setError_msg("成功");
					isSuccess = true;
				} else {
					// 失败
					result.setError_code(aopMsg.getCode());
					result.setError_msg(
							(StringUtils.isEmpty(aopMsg.getDetail()) ? aopMsg.getCode() : aopMsg.getDetail()));
				}
			}

			if (isSuccess) {
				// 保存受理单信息、保存货品表中开户结果相关字段
				AcceptanceParamVo param = new AcceptanceParamVo();

				if (!DataUtil.checkFieldValueNull(param)) {
					param.setNotNeedReqStrOrderId(order_id);
					saveAcceptanceInfos(param);
				}

				// 标记主商品附加产品办理成功
				List<OrderSubProductBusiRequest> subProductLists = CommonDataFactory.getInstance()
						.getOrderTree(order_id).getOrderSubProductBusiRequest();
				List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance()
						.getOrderTree(order_id).getAttrPackageSubProdBusiRequest();
				if (attrPackageSubProdList != null && attrPackageSubProdList.size() > 0 && subProductLists != null
						&& subProductLists.size() > 0) {
					for (OrderSubProductBusiRequest subProduct : subProductLists) {
						if (!StringUtils.equals("0", subProduct.getProd_inst_id()))
							continue;
						for (AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList) { // 设置线上办理成功
							if (StringUtils.equals(subProduct.getSub_pro_inst_id(), subPackage.getSubProd_inst_id())
									&& (StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_0,
											subPackage.getStatus())
											|| StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1,
													subPackage.getStatus()))) { // 取待办理、办理失败的记录
								subPackage.setSub_bss_order_id(CommonDataFactory.getInstance()
										.getAttrFieldValue(order_id, AttrConsts.ACTIVE_NO));
								subPackage.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
								subPackage.setDb_action(ConstsCore.DB_ACTION_UPDATE);
								subPackage.setIs_dirty(ConstsCore.IS_DIRTY_YES);
								subPackage.store();
							}
						}
					}
				}

				// 更新各种开户状态和结果
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
						new String[] { AttrConsts.ACCOUNT_STATUS, AttrConsts.BSS_ACCOUNT_TIME },
						new String[] { EcsOrderConsts.ACCOUNT_STATUS_1, dateformat.format(new Date()) });

				// 开户结果要通知新商城：仅通知，不用考虑新商城接受的状态。流程运行到写卡环节，获取到总部的写卡数据时还要通知新商城
				/*
				 * StatuSynchReq statuSyn = new
				 * StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_D,
				 * EcsOrderConsts.DIC_ORDER_NODE_D_DESC,EcsOrderConsts.
				 * ACCOUNT_STATUS_1,EcsOrderConsts.ACCOUNT_STATUS_1_DESC,"");
				 * CommonDataFactory.getInstance().notifyNewShop(statuSyn);
				 */

				// 半成卡一键开户,通知新商城业务办理环节
				String is_write_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
						AttrConsts.IS_WRITE_CARD);
				if (EcsOrderConsts.IS_WRITE_CARD_NO.equals(is_write_card)) {// 不用写卡(半成卡)才在这里通知新商城业务办理;成卡(也不用写卡)的订单不会走这个业务组件

					List<AttrGiftInfoBusiRequest> giftInfoList = CommonDataFactory.getInstance().getOrderTree(order_id)
							.getGiftInfoBusiRequests();
					for (AttrGiftInfoBusiRequest giftInfo : giftInfoList) {
						String bss_status = giftInfo.getBss_status();
						if (EcsOrderConsts.GIFT_BSS_STATUS_1.equals(bss_status)
								|| EcsOrderConsts.GIFT_BSS_STATUS_3.equals(bss_status)) {
							logger.info("更新订单bss办理状态，id：" + order_id + "  ");
							CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
									new String[] { AttrConsts.BSS_STATUS },
									new String[] { EcsOrderConsts.BSS_STATUS_0 });
							break;
						}
					}
				}
			} else {
				// 标记异常(订单转异常单)
				Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_OPEN, rsp.getError_msg());

				// 更新开户状态
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
						new String[] { AttrConsts.ACCOUNT_STATUS }, new String[] { EcsOrderConsts.ACCOUNT_STATUS_0 });
			}

			// 设置返回对象
			result.setResponse(rsp);
		} else {
			throw new ApiBusiException(order_id + "订单正式提交失败：" + goods_id + "商品的商品类型不是融合业务-总部");
		}

		return result;
	}

	/**
	 * [AOP]号卡副卡产品变更、、、开户提交
	 * 
	 * @author song.qi 2018年5月18日
	 */
	@Override
	public BusiDealResult openAccountSubmit(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		OpenAccountSubmitReq req = new OpenAccountSubmitReq();
		req.setNotNeedReqStrOrderId(order_id);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OpenAccountSubmitResp resp = client.execute(req, OpenAccountSubmitResp.class);
		if (null != resp.getResultMsg() && !StringUtils.isEmpty(resp.getResultMsg().getProvOrderId())) {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			// 更新货品表里的相关数据
			List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
			OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
			orderItemExtBusiRequest.setBssOrderId(resp.getResultMsg().getBssOrderId());
			orderItemExtBusiRequest.setActive_no(resp.getResultMsg().getProvOrderId());
			orderItemExtBusiRequest.setZb_ess_order_id(resp.getResultMsg().getEssOrderId());
			orderItemExtBusiRequest.setTax_no(resp.getResultMsg().getTaxNo());
			orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemExtBusiRequest.store();

			// 更新各种开户状态和结果
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ACCOUNT_STATUS, AttrConsts.BSS_ACCOUNT_TIME, "acceptanceForm" },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_1, dateformat.format(new Date()),
							resp.getResultMsg().getAcceptanceForm() });
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("处理成功");
		} else {
			result.setError_code(null != resp.getResultMsg() ? resp.getResultMsg().getCode() : resp.getError_code());
			result.setError_msg(null != resp.getResultMsg() ? resp.getResultMsg().getDetail() : resp.getError_msg());
			Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_BSS,
					null != resp.getResultMsg() ? resp.getResultMsg().getDetail() : resp.getError_msg());
		}
		result.setResponse(resp);
		return result;
	}

	/**
	 * [AOP]老用户加入主副卡
	 * 
	 * @author song.qi 2018年5月18日
	 */
	@Override
	public BusiDealResult joinMVCard(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		UserJoinMainViceCardReq req = new UserJoinMainViceCardReq();
		req.setNotNeedReqStrOrderId(order_id);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		UserJoinMainViceCardResp resp = client.execute(req, UserJoinMainViceCardResp.class);
		if (null != resp.getResultMsg() && !StringUtils.isEmpty(resp.getResultMsg().getOrderId())) {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			// 更新货品表里的相关数据
			List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
			OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
			orderItemExtBusiRequest.setBssOrderId(resp.getResultMsg().getOrderId());
			orderItemExtBusiRequest.setActive_no(resp.getResultMsg().getOrderId());
			orderItemExtBusiRequest.setCardCBssOrderId(resp.getResultMsg().getOrderId());
/*			orderItemExtBusiRequest.setCardCBssAcceptId(resp.getResultMsg().getOrderId());
*/			orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemExtBusiRequest.store();

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("处理成功");
		} else {
			result.setError_code(null != resp.getResultMsg() ? resp.getResultMsg().getCode()
					: resp.getError_code() + resp.getCode());
			result.setError_msg(null != resp.getResultMsg() ? resp.getResultMsg().getDetail()
					: resp.getError_msg() + resp.getDetail());
			Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_BSS, null != resp.getResultMsg()
					? resp.getResultMsg().getDetail() : resp.getError_msg() + resp.getDetail());
		}
		result.setResponse(resp);
		return result;
	}

	@Override
	public BusiDealResult changeAppPre(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ChangeAppReq req = new ChangeAppReq();
		req.setNotNeedReqStrOrderId(order_id);
		ChangeAppResp resp = client.execute(req, ChangeAppResp.class);
		if (EcsOrderConsts.AIP_STATUS_CODE_SUCC.equals(resp.getCode())) {
			OrderItemExtBusiRequest orderItemExtBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id)
					.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest();
			String bms_accept_id = resp.getRespJson().getOrder_id();
			if (!StringUtils.isEmpty(bms_accept_id)) {
				if (orderItemExtBusiRequest != null) {
					orderItemExtBusiRequest.setBssOrderId(bms_accept_id);
					orderItemExtBusiRequest.setActive_no(bms_accept_id);
					orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderItemExtBusiRequest.store();
					result.setError_code(EcsOrderConsts.BSS_SUCCESS_00000);
					result.setError_msg("成功");
				}
			} else {
				result.setError_code("-1");
				result.setError_msg("未返回业务单号！");
			}

		} else {
			result.setError_code(resp.getCode());
			result.setError_msg(resp.getMsg());
		}
		return result;
	}

	public BusiDealResult changeSub(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ChangeSubReq req = new ChangeSubReq();
		req.setNotNeedReqStrOrderId(order_id);
		ChangeSubResp resp = client.execute(req, ChangeSubResp.class);
		result.setError_code(resp.getCode());
		result.setError_msg(resp.getMsg());
		return result;
	}

	@Override
	public BusiDealResult kuKaPreOpenBSS(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		FuKaPreOpenReq req = new FuKaPreOpenReq();
		req.setNotNeedReqStrOrderId(order_id);
		FuKaPreOpenResp resp = client.execute(req, FuKaPreOpenResp.class);
		if (EcsOrderConsts.AIP_STATUS_CODE_SUCC.equals(resp.getCode())) {
			OrderItemExtBusiRequest orderItemExtBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id)
					.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest();
			String bms_accept_id = resp.getRespJson().getBms_accept_id();
			if (!StringUtils.isEmpty(bms_accept_id)) {
				if (orderItemExtBusiRequest != null) {
					orderItemExtBusiRequest.setBssOrderId(bms_accept_id);
					orderItemExtBusiRequest.setActive_no(bms_accept_id);
					orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderItemExtBusiRequest.store();
					result.setError_code(EcsOrderConsts.BSS_SUCCESS_00000);
					result.setError_msg("成功");
				}
			} else {
				result.setError_code("-1");
				result.setError_msg("未返回业务单号！");
			}

		} else {
			result.setError_code(resp.getCode());
			result.setError_msg(resp.getMsg());
		}
		return result;

	}

    @Override
    public BusiDealResult openAccountMasterSubmit(String order_id)  throws ApiBusiException {
        BusiDealResult result = new BusiDealResult();
        OpenAccountSubmitReq req = new OpenAccountSubmitReq();
        req.setNotNeedReqStrOrderId(order_id);
        ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        OpenAccountSubmitResp resp = client.execute(req, OpenAccountSubmitResp.class);
        if (null != resp.getResultMsg() && !StringUtils.isEmpty(resp.getResultMsg().getProvOrderId())) {
            result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
            result.setError_msg("处理成功");
        } else {
            result.setError_code(null != resp.getResultMsg() ? resp.getResultMsg().getCode() : resp.getError_code());
            result.setError_msg(null != resp.getResultMsg() ? resp.getResultMsg().getDetail() : resp.getError_msg());
            Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_BSS,
                    null != resp.getResultMsg() ? resp.getResultMsg().getDetail() : resp.getError_msg());
        }
        CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "active_no", "bssOrderId" }, new String[] { "", "" });
        result.setResponse(resp);
        return result;
    }
    
    @Override
	public BusiDealResult objectReplaceSub(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult() ; 
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		if(orderTree == null) {
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
			result.setError_msg("单号有误");
			
			return result ;
		}
		
		OrderItemExtBusiRequest orderItemExtBusiRequest = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest();
		OrderAdslBusiRequest adslBusi = orderTree.getOrderAdslBusiRequest().get(0) ;
		OrderExtBusiRequest extBusi = orderTree.getOrderExtBusiRequest() ;
		OrderPayBusiRequest payBusi = orderTree.getOrderPayBusiRequests().get(0);
		//接口参数赋值
		String serviceNum = orderItemExtBusiRequest.getPhone_num() ;
		String officeId = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OFFICE);
		String operatorId = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OPERATOR);
		String ObjectId = adslBusi.getModerm_id();
		String regionId = extBusi.getOrder_city_code();
		regionId = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.BSS_AREA_CODE, regionId);
		String saleMode = adslBusi.getSale_mode() ;
		String devicGear = adslBusi.getDevic_gear() ;
		String oldObjEsn = adslBusi.getOld_obj_esn();
		String oldObjOwner = adslBusi.getOld_obj_owner() ;
		//serviceType Todo
		String serviceType = adslBusi.getService_type() ;
		String feeList = "";
		String source_from = extBusi.getOrder_from();
		String Fee_list  = "";
		boolean flag = false;
		IEcsOrdManager ecsOrdManager = SpringContextHolder.getBean("ecsOrdManager");
		List dc_list = ecsOrdManager.getDcSqlByDcName("DC_KD_FY_JM");
		for (int j=0;j<dc_list.size();j++){
			String value = Const.getStrValue((Map) dc_list.get(j), "value");
			String value_desc = Const.getStrValue((Map) dc_list.get(j), "value_desc");
			if (StringUtil.equals(value, source_from)) {
				flag = true;
				break;
			}
		}
		List<AttrFeeInfoBusiRequest> feeInfoBusiRequests = orderTree.getFeeInfoBusiRequests();
		for(int i=0;i<feeInfoBusiRequests.size();i++){
			if(!StringUtils.isEmpty(feeInfoBusiRequests.get(i).getIs_aop_fee()) &&
					StringUtils.equals(feeInfoBusiRequests.get(i).getIs_aop_fee(), "2")){
				if(flag){
					Fee_list += feeInfoBusiRequests.get(i).getFee_item_code()+"&"+feeInfoBusiRequests.get(i).getO_fee_num()+"&"+feeInfoBusiRequests.get(i).getN_fee_num()+"&"+feeInfoBusiRequests.get(i).getDiscount_fee()+"&"+feeInfoBusiRequests.get(i).getFee_category()+";";
				}else{
					Fee_list += feeInfoBusiRequests.get(i).getFee_item_code()+"&"+feeInfoBusiRequests.get(i).getO_fee_num()+"&"+feeInfoBusiRequests.get(i).getDiscount_fee()+"&"+feeInfoBusiRequests.get(i).getN_fee_num()+"&"+feeInfoBusiRequests.get(i).getFee_category()+";";
				}
				
			}
		}
		if(!StringUtil.isEmpty(Fee_list)){
			Fee_list = Fee_list.substring(0, Fee_list.lastIndexOf(";"));
		}
		feeList = Fee_list;
		String feeRemark = "";
		String fundSource = "";
		IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		String payMethod = payBusi.getPay_method();
		List<Map> payMethodlist = dcPublicCache.getList("DIC_PAY_METHOD_BSS");
        for(int i=0;i<payMethodlist.size();i++){
        	if(StringUtil.equals(payMethod, (String)payMethodlist.get(i).get("pkey"))){
        		fundSource = (String)payMethodlist.get(i).get("pname");
        		break;
        	}
        }
		//is_pay ;
		String isPay = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "is_pay");
		
		ObjectReplacePreSubReq req = new ObjectReplacePreSubReq.Builder().devicGear(devicGear).feeList(feeList).feeRemark(feeRemark)
				.fundSource(fundSource).isPay(isPay).objectId(ObjectId).officeId(officeId).oldObjEsn(oldObjEsn).oldObjOwner(oldObjOwner)
				.operatorId(operatorId).regionId(regionId).saleMode(saleMode).serviceNum(serviceNum).serviceType(serviceType).notNeedReqStrOrderId(order_id)
				.build();
		
		ObjectReplacePreSubResp resp = client.execute(req, ObjectReplacePreSubResp.class);
		if(!StringUtils.equals(resp.getCode(), EcsOrderConsts.AIP_STATUS_CODE_SUCC)) {
			result.setError_code(resp.getCode());
			result.setError_msg(resp.getMsg());
		}else {
			String bssAccemptId = resp.getRespJson().getBms_accept_id();
			if(StringUtils.isEmpty(bssAccemptId)) {
				result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
				result.setError_msg("业务单号为空");
			} else {
				orderItemExtBusiRequest.setBssOrderId(bssAccemptId);
				orderItemExtBusiRequest.setActive_no(bssAccemptId);
				orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderItemExtBusiRequest.store();
				result.setError_code(EcsOrderConsts.AIP_STATUS_CODE_SUCC);
				result.setError_msg("成功");
			}
		}
		return result ;
	}
}