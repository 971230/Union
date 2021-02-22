package com.ztesoft.net.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import params.ZteResponse;
import util.Utils;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.bss.req.ActionRecvBSSReq; 
import zte.net.ecsord.params.bss.req.AgencyAcctPayNotifyReq; 
import zte.net.ecsord.params.bss.req.MobileNetworkServiceHandleReq;
import zte.net.ecsord.params.bss.req.SPBuyServiceHandleReq;
import zte.net.ecsord.params.bss.resp.ActionRecvBSSResp;
import zte.net.ecsord.params.bss.resp.AgencyAcctPayNotifyRsp; 
import zte.net.ecsord.params.bss.resp.MobileNetworkServiceHandleResp;
import zte.net.ecsord.params.bss.resp.SPBuyServiceHandleResp; 
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageSubProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemAgentMoneyBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderSpProductBusiRequest;
import zte.net.ecsord.params.busi.req.OrderSubProductBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.BareMachineSaleReq;
import zte.net.ecsord.params.ecaop.req.BusinessAcceptenceAndVerificationReq;
import zte.net.ecsord.params.ecaop.req.ChangeMachineSubReq;
import zte.net.ecsord.params.ecaop.req.CunFeeSongFeeRequest;
import zte.net.ecsord.params.ecaop.req.FlowPacketApplyReq;
import zte.net.ecsord.params.ecaop.req.FlowPacketPreReq;
import zte.net.ecsord.params.ecaop.req.HandleSpServiceReq;
import zte.net.ecsord.params.ecaop.req.SubProOrderReq;
import zte.net.ecsord.params.ecaop.req.TrafficOrderRequest;
import zte.net.ecsord.params.ecaop.resp.BareMachineSaleResp;
import zte.net.ecsord.params.ecaop.resp.BusinessAcceptenceAndVerificationResponse;
import zte.net.ecsord.params.ecaop.resp.ChangeMachineSubResp;
import zte.net.ecsord.params.ecaop.resp.CunFeeSongFeeResponse;
import zte.net.ecsord.params.ecaop.resp.FlowPacketApplyRsp;
import zte.net.ecsord.params.ecaop.resp.FlowPacketPreRsp;
import zte.net.ecsord.params.ecaop.resp.HandleSpServiceResp;
import zte.net.ecsord.params.ecaop.resp.SubProOrderResp;
import zte.net.ecsord.params.ecaop.resp.TrafficOrderResponse;
import zte.net.ecsord.params.sr.req.SimulationRequset;
import zte.net.ecsord.params.sr.resp.SimulationResponse;
import zte.net.ecsord.params.wyg.req.StatuSynchReq;
import zte.net.ecsord.utils.SpecUtils;

import com.zte.cbss.autoprocess.CreateCustomHandler;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.HttpLoginClientPool;
import com.zte.cbss.autoprocess.model.CustomBill;
import com.zte.cbss.autoprocess.model.CustomInfo;
import com.zte.cbss.autoprocess.model.LoginInfo;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.inf.framework.commons.CodedException;
import com.ztesoft.net.ecsord.params.ecaop.req.AmountPayReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.AmountPayResp;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.IOrdArchiveTacheManager;
import com.ztesoft.net.service.IOrdBSSTacheManager;
import com.ztesoft.net.service.IOrdOpenAccountTacheManager;

import consts.ConstsCore;

/**
 * BBS环节处理类
 * 
 * @author xuefeng
 */
public class OrdBSSTacheManager extends BaseSupport implements IOrdBSSTacheManager {
	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Resource
	private IOrdArchiveTacheManager ordArchiveTacheManager;
	@Resource
	private IOrdOpenAccountTacheManager ordOpenAccountTacheManager;

	private static String agent_deduct = "deduct"; // 扣款
	private static String agent_refund = "refund"; // 返销
 
	/**
	 * BSS办理状态设置（未办理）
	 * 
	 * @param order_id
	 * @return
	 */
	@Override
	public BusiDealResult setStatusNoFinish(String order_id) {
		BusiDealResult result = new BusiDealResult();
		String bssStatus = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_STATUS);
		if (ZjEcsOrderConsts.BSS_STATUS_2.equals(bssStatus) || ZjEcsOrderConsts.BSS_STATUS_1.equals(bssStatus)) {
			result.setError_code("-1");
			result.setError_msg("BSS办理状态为已办理，状态不可更改为未办理，环节终止。");
		} else {
			String[] name = { AttrConsts.BSS_STATUS };
			String[] value = { ZjEcsOrderConsts.BSS_STATUS_0 };
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, name, value);
		}
		return result;
	}

	/**
	 * BSS社会机TAC码、商品折扣包录入
	 * 
	 * @param order_id
	 * @return
	 */
	@Override
	public BusiDealResult actionRecv(String order_id) {
		BusiDealResult result = new BusiDealResult();
		try {
			ordArchiveTacheManager.ordArchive(order_id);

			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			ActionRecvBSSReq req = new ActionRecvBSSReq();
			req.setORDER_ID(order_id);
			ActionRecvBSSResp resp = new ActionRecvBSSResp();// client.execute(req,
																// ActionRecvBSSResp.class);
			resp.setRSP_CODE(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
			if (!resp.getRSP_CODE().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {
				result.setError_msg("错误编码：" + resp.getResp().getOrd_code() + ";错误信息：" + resp.getResp().getDeal_msg());
				result.setError_code(resp.getResp().getOrd_code());

				ordArchiveTacheManager.ordArchive(order_id);
			} else {
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_STATUS }, new String[] { ZjEcsOrderConsts.BSS_STATUS_2 });
				StatuSynchReq statuSyn = new StatuSynchReq(order_id, EcsOrderConsts.DIC_ORDER_NODE_Y, EcsOrderConsts.DIC_ORDER_NODE_Y_DESC, ZjEcsOrderConsts.BSS_STATUS_2,
						ZjEcsOrderConsts.BSS_STATUS_2_DESC, "");
				CommonDataFactory.getInstance().notifyNewShop(statuSyn);
			}
		} catch (Exception e) {
			result.setError_msg(e.getMessage());
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
		}

		return result;
	}

	/**
	 * BSS办理状态设置（已办理）
	 * 
	 * @param order_id
	 * @return
	 */
	@Override
	public BusiDealResult setStatusFinish(String order_id) {
		BusiDealResult result = new BusiDealResult();
		String[] name = { AttrConsts.BSS_STATUS };
		String[] value = { ZjEcsOrderConsts.BSS_STATUS_2 };
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, name, value);
		result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
		return result;
	}

	@Override
	public BusiDealResult orderCBSSDeal(AttrGiftInfoBusiRequest attrGiftInfo, HttpLoginClient httpLoginclient) {
		BusiDealResult result = new BusiDealResult();
		try {
			String order_id = attrGiftInfo.getOrder_id();
			String goodsName = attrGiftInfo.getGoods_name();
			// ordBSSTacheManager.setStatusFinish(order_id);
			if (null == goodsName || "".equals(goodsName)) {
				result.setError_code("1");
				result.setError_msg("赠品名称为空");
				return result;
			}
			String phoneNum = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);// orderItemExt.getPhone_num();
			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			if (goodsName.indexOf("省内闲时流量") > -1) {
				logger.info(goodsName + "  调用CBSS-移网产品服务变更接口");
				MobileNetworkServiceHandleReq req = new MobileNetworkServiceHandleReq();
				req.setOrderid(order_id);
				req.setMobileNo(phoneNum);
				req.setSourceFrom(ManagerUtils.getSourceFrom());
				if (goodsName.indexOf("3G省内闲时流量") > -1) {
					req.setPackFlag(EcsOrderConsts.BSS_PACK_FLAG_B);
				} else if (goodsName.indexOf("1G省内闲时流量") > -1) {
					req.setPackFlag(EcsOrderConsts.BSS_PACK_FLAG_A);
				}
				// req.setHttpLoginclient(httpLoginclient);
				MobileNetworkServiceHandleResp resp = client.execute(req, MobileNetworkServiceHandleResp.class);
				if (EcsOrderConsts.BUSI_DEAL_RESULT_0000.equals(resp.getError_code())) {
					attrGiftInfo.setBss_status(EcsOrderConsts.GIFT_BSS_STATUS_4);
					attrGiftInfo.setBss_desc(EcsOrderConsts.GIFT_BSS_STATUS_4_DESC);
				} else {
					attrGiftInfo.setBss_status(EcsOrderConsts.GIFT_BSS_STATUS_3);
					String desc = resp.getError_msg().length() > 200 ? resp.getError_msg().substring(0, 200) : resp.getError_msg();
					attrGiftInfo.setBss_desc(desc);
				}
			} else if (goodsName.indexOf("WO") > -1 && goodsName.indexOf("视频") > -1) {
				logger.info(goodsName + "  调用CBSS-SP订购接口");
				SPBuyServiceHandleReq req = new SPBuyServiceHandleReq();
				req.setOrderid(order_id);
				req.setMobileNo(phoneNum);
				req.setSourceFrom(ManagerUtils.getSourceFrom());
				req.setTake_effect_type("0");
				// req.setHttpLoginclient(httpLoginclient);
				SPBuyServiceHandleResp resp = client.execute(req, SPBuyServiceHandleResp.class);
				if (EcsOrderConsts.BUSI_DEAL_RESULT_0000.equals(resp.getError_code())) {
					attrGiftInfo.setBss_status(EcsOrderConsts.GIFT_BSS_STATUS_4);
					attrGiftInfo.setBss_desc(EcsOrderConsts.GIFT_BSS_STATUS_4_DESC);
				} else {
					attrGiftInfo.setBss_status(EcsOrderConsts.GIFT_BSS_STATUS_3);
					String desc = resp.getError_msg().length() > 200 ? resp.getError_msg().substring(0, 200) : resp.getError_msg();
					attrGiftInfo.setBss_desc(desc);
				}
			} else {
				result.setError_code("1");
				result.setError_msg("赠品不需要进行CBSS业务办理");
				return result;
			}
			attrGiftInfo.setGift_inst_id(attrGiftInfo.getGift_inst_id());
			attrGiftInfo.setOrder_id(order_id);
			attrGiftInfo.setBss_time(DateUtil.currentDateTime());
			attrGiftInfo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			attrGiftInfo.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			attrGiftInfo.store();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		result.setError_code("0");
		result.setError_msg("CBSS业务成功");
		return result;
	}

	/**
	 * 代理商资金扣减
	 * 
	 * @param order_id
	 * @return
	 */
	@Override
	public BusiDealResult agentDeduct(String order_id) {
		BusiDealResult result = new BusiDealResult();
		// 货品小类
		String prod_cat_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getProd_cat_id();
		if (SpecConsts.CAT_ID_690302000.equals(prod_cat_id)) // 存费送机，不做扣减
			return result;

		OrderItemBusiRequest orderItem = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0);
		OrderItemAgentMoneyBusiRequest orderItemAgent = orderItem.getOrderItemAgentMoneyBusiRequest();
		// 扣款成功,不再扣款
		if (EcsOrderConsts.AGENCY_NOTIFY_SUCC.equals(orderItemAgent.getCol8()))
			return result;

		// 扣款金额
		String payFee = SpecUtils.getAgentFee(order_id);
		if (StringUtil.isEmpty(payFee) || Integer.valueOf(payFee) <= 0)
			return result;
		// 调用接口
		AgencyAcctPayNotifyReq req = new AgencyAcctPayNotifyReq();
		req.setNotNeedReqStrOrderId(order_id);
		req.setNotNeedReqTRADE_TYPE(EcsOrderConsts.TRADE_TYPE_0); // 资金扣减
		req.setPAY_FEE(payFee);

		req.setORDER_ID(CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getCol2()); // 传总部的order_id
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		AgencyAcctPayNotifyRsp resp = client.execute(req, AgencyAcctPayNotifyRsp.class);
		if (!EcsOrderConsts.BSS_DEAL_RESULT_0000.equals(resp.getAGENCY_ACCT_PAY_NOTIFY_RSP().getRESP_CODE())) { // 扣款失败,标记异常
			// 标记异常,异常处理
			Map params = new HashMap();
			params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			CommonDataFactory.getInstance().markException(order_id, params);
		}
		// 记录扣款结果
		saveAgentMoney(resp, order_id);
		result.setError_code(resp.getAGENCY_ACCT_PAY_NOTIFY_RSP().getRESP_CODE());
		result.setError_msg(resp.getAGENCY_ACCT_PAY_NOTIFY_RSP().getRESP_DESC());
		return result;
	}

	/**
	 * 代理商资金返销
	 * 
	 * @param order_id
	 * @return
	 */
	@Override
	public BusiDealResult agentRefund(String order_id) {
		BusiDealResult result = new BusiDealResult();
		// 货品小类
		String prod_cat_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getProd_cat_id();
		if (SpecConsts.CAT_ID_690302000.equals(prod_cat_id)) // 合约惠机，不做返销
			return result;

		OrderItemBusiRequest orderItem = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0);
		OrderItemAgentMoneyBusiRequest orderItemAgent = orderItem.getOrderItemAgentMoneyBusiRequest();
		if (!(!EcsOrderConsts.AGENCY_NOTIFY_SUCC.equals(orderItemAgent.getCol9()) && EcsOrderConsts.AGENCY_NOTIFY_SUCC.equals(orderItemAgent.getCol8()))) // 非（扣款成功
																																							// 且
																																							// 未返销）的订单直接返回
			return result;

		OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
		if (StringUtil.isEmpty(orderItemAgent.getCol6()) || Integer.valueOf(orderItemAgent.getCol6()) <= 0)
			return result;
		// 调用接口
		AgencyAcctPayNotifyReq req = new AgencyAcctPayNotifyReq();
		req.setNotNeedReqStrOrderId(order_id);
		req.setNotNeedReqTRADE_TYPE(EcsOrderConsts.TRADE_TYPE_1); // 资金返销
		req.setPAY_FEE(orderItemAgent.getCol6());
		req.setORG_ORDER_ID(orderExt.getCol2());
		OrderItemBusiRequest orderItemExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0);
		req.setORDER_ID(orderItemExt.getOrderItemExtBusiRequest().getActive_no());
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		AgencyAcctPayNotifyRsp resp = client.execute(req, AgencyAcctPayNotifyRsp.class);

		// 记录返销结果
		orderItemAgent.setCol5(Const.SYSDATE);
		if (EcsOrderConsts.BSS_DEAL_RESULT_0000.equals(resp.getAGENCY_ACCT_PAY_NOTIFY_RSP().getRESP_CODE())) {
			orderItemAgent.setCol9(EcsOrderConsts.AGENCY_NOTIFY_SUCC);
		} else {
			orderItemAgent.setCol9(EcsOrderConsts.AGENCY_NOTIFY_FAIL);
		}
		orderItemAgent.setCol7(req.getPAY_FEE());
		orderItemAgent.setC_order_id(orderExt.getCol2());
		orderItemAgent.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderItemAgent.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderItemAgent.store();

		result.setError_code(resp.getAGENCY_ACCT_PAY_NOTIFY_RSP().getRESP_CODE());
		result.setError_msg(resp.getAGENCY_ACCT_PAY_NOTIFY_RSP().getRESP_DESC());
		return result;
	}

	private void saveAgentMoney(AgencyAcctPayNotifyRsp resp, String order_id) {
		OrderItemBusiRequest orderItem = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0);
		OrderItemAgentMoneyBusiRequest orderItemAgent = orderItem.getOrderItemAgentMoneyBusiRequest();
		OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
		// 接口标识
		String inf_flag = EcsOrderConsts.AGENCY_NOTIFY_FAIL;
		if (EcsOrderConsts.BSS_DEAL_RESULT_0000.equals(resp.getAGENCY_ACCT_PAY_NOTIFY_RSP().getRESP_CODE())) {
			inf_flag = EcsOrderConsts.AGENCY_NOTIFY_SUCC;
		}
		String db_action = ConstsCore.DB_ACTION_UPDATE;
		if (orderItemAgent.getOrder_id() == null) {
			orderItemAgent = new OrderItemAgentMoneyBusiRequest();
			orderItemAgent.setItem_id(orderItem.getItem_id());
			orderItemAgent.setOrder_id(order_id);
			orderItemAgent.setGoods_id(orderItem.getGoods_id());
			db_action = ConstsCore.DB_ACTION_INSERT;
		}
		orderItemAgent.setO_order_id(orderExt.getCol2());
		orderItemAgent.setCol3(Const.SYSDATE);
		orderItemAgent.setCol6(SpecUtils.getAgentFee(order_id));
		orderItemAgent.setCol8(inf_flag);
		orderItemAgent.setDb_action(db_action);
		orderItemAgent.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderItemAgent.store();
	}

	@SuppressWarnings("unused")
	private String getC_order_id() {
		Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		return year + "" + month + "" + date + "" + hour + "" + minute + "" + second + Math.round(Math.random() * 8999 + 1000);
	}

	@Override
	public HttpLoginClient getHttpLoginClient(String order_id) throws CodedException, Exception {
		// TODO Auto-generated method stub
		HttpLoginClient client = null;
		client = login(order_id);
		client.getParam().setOtree_order_id(order_id);
		client.getParam().setOrder_create_time(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.TID_TIME));
		client.getParam().setCustomInfo(getCustomInfo(client));
		return client;
	}

	/**
	 * 登陆CBSS系统
	 * 
	 * @param order_id
	 * @return
	 * @throws CodedException
	 * @throws Exception
	 */
	private HttpLoginClient login(String order_id) throws CodedException, Exception {
		try {
			OrderTreeBusiRequest orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);

			String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);
			/**
			 * select a.*,a.rowid from es_dc_public_dict_relation a ; select
			 * a.region_id value,a.local_name value_desc from es_regions a where
			 * a.p_region_id='440000';
			 */
			List list = CommonDataFactory.getInstance().listDcPublicData(StypeConsts.CBSS_LOGIN_USER_INFO);
			if (list == null || list.size() <= 0) {
				logger.info("====》【请检查es_dc_public_ext表是否有配置登录信息！stype='cbss_login_user_info']】");
				return null;
			}
			Map map = (Map) list.get(0);
			if (map == null || map.size() <= 0) {
				logger.info("====》【请检查es_dc_public_ext表，是否有配置用户名和密码和省份】《====");
				return null;
			}
			LoginInfo info = new LoginInfo();
			info.setCbssAccount(map.get("PKEY") != null ? map.get("PKEY").toString() : "");
			info.setCbssPassword(map.get("PNAME") != null ? map.get("PNAME").toString() : "");
			info.setProvinceCode(map.get("CODEA") != null ? map.get("CODEA").toString() : "");
			//
			HttpLoginClient client = HttpLoginClientPool.add(info);
			return client;
		} catch (Exception e) {
			throw new CodedException("8001", "用户登录失败!");
		}
	}

	/**
	 * 认证客户(order tree 取数据)
	 */
	private CustomInfo getCustomInfo(HttpLoginClient client) throws CodedException, Exception {
		if (null == client)
			return null;
		String order_id = client.getParam().getOtree_order_id();
		// OrderTreeBusiRequest orderTreeBusiRequest =
		// CommonDataFactory.getInstance().getOrderTree(order_id);
		String acc_nbr = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);// CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getPhone_num();
		try {
			CustomBill bill = new CustomBill();
			bill.setPsptId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM));
			bill.setCustomName(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_OWNER_NAME));
			bill.setContact(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_OWNER_NAME));
			bill.setContactPhone(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM));
			bill.setPsptEndDate("2018-05-08");
			bill.setIdTypeCode("1");
			bill.setPhone(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFERENCE_PHONE));
			bill.setPostAddress(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS)); // 通信地址必须大于八个字符
			bill.setSerialNumber(acc_nbr); // 设置要办理业务号码 真实环境需要改代码
			CustomInfo customInfo = CreateCustomHandler.emulate(bill, client);
			return customInfo;
		} catch (Exception e) {
			throw new CodedException("8002", "客户认证失败!");
		}
	}

	@Override
	public boolean toChanageGiftBssStatus(String order_id) {
		// TODO Auto-generated method stub
		BusiDealResult result = null;
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		// 更改礼品表状态
		boolean flag = true;
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<AttrGiftInfoBusiRequest> giftList = orderTree.getGiftInfoBusiRequests();
		for (AttrGiftInfoBusiRequest giftBusi : giftList) {
			if (!EcsOrderConsts.GIFT_BSS_STATUS_4.equals(giftBusi.getBss_status())) {
				flag = false;
			}
		}
		// 全部处理完成，归档
		if (flag && giftList.size() > 0) {
			result = setStatusFinish(order_id);
			if (result != null && result.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 老用户存费送费
	 */
	@Override
	public BusiDealResult cunFeeSongFeeGDAop(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		CunFeeSongFeeRequest req = new CunFeeSongFeeRequest();
		req.setNotNeedReqStrOrderId(order_id);

		CunFeeSongFeeResponse rsp = client.execute(req, CunFeeSongFeeResponse.class);

		if (!StringUtils.equals(rsp.getCode(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {// 失败
			// 设置错误返回码
			result.setError_code(rsp.getCode());
			result.setError_msg(rsp.getDetail());

			// 标记异常
			Utils.markException(order_id, false);
		} else {// 成功
			// CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
			// new String[]{AttrConsts.BSS_STATUS,AttrConsts.ACTIVE_NO,
			// AttrConsts.BSS_ACCOUNT_TIME,AttrConsts.BSS_OPERATOR,
			// AttrConsts.BSS_OPERATOR_NAME},
			// new
			// String[]{EcsOrderConsts.BSS_STATUS_1,rsp.getSeqNo(),dateformat.format(new
			// Date()),req.getOper_id(),req.getEssOperInfo().getStaffName()});
			//
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ACTIVE_NO, AttrConsts.BSS_ACCOUNT_TIME, AttrConsts.BSS_OPERATOR, AttrConsts.BSS_OPERATOR_NAME, AttrConsts.BSS_STATUS },
					new String[] { rsp.getSeqNo(), dateformat.format(new Date()), req.getOper_id(), req.getEssOperInfo().getStaffName(), ZjEcsOrderConsts.BSS_STATUS_2 });

			// 业务办理结果要通知新商城
			// StatuSynchReq statuSyn = new
			// StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_Y,EcsOrderConsts.DIC_ORDER_NODE_Y_DESC,EcsOrderConsts.BSS_STATUS_1,EcsOrderConsts.BSS_STATUS_1_DESC,"");
			// CommonDataFactory.getInstance().notifyNewShop(statuSyn);
			//
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * 流量包订购
	 * 
	 * @author XMJ
	 * @param order_id
	 * @return
	 */
	public BusiDealResult trafficOrder(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		TrafficOrderRequest req = new TrafficOrderRequest();
		req.setNotNeedReqStrOrderId(order_id);
		TrafficOrderResponse rsp = client.execute(req, TrafficOrderResponse.class);// 调用接口
		if (StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
			result.setError_msg(rsp.getDetail());
			result.setError_code(rsp.getCode());

			// 标记异常
			Utils.markException(order_id, false);
		} else {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_ACCOUNT_TIME, AttrConsts.BSS_OPERATOR, AttrConsts.BSS_OPERATOR_NAME },
					new String[] { dateformat.format(new Date()), req.getOperatorId(), req.getEssOperInfo().getStaffName() });

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * 流量包订购/退订 日包/夜猫包/月包: ecaop.trades.sell.mob.comm.traffic.sub
	 * 
	 * @author songqi
	 * @param order_id
	 * @return
	 */
	public BusiDealResult flowPacket(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		FlowPacketApplyReq req = new FlowPacketApplyReq();
		req.setNotNeedReqStrOrderId(order_id);
		FlowPacketApplyRsp resp = null;
		try {
			resp = client.execute(req, FlowPacketApplyRsp.class);// 调用接口
			if (null == resp.getResultMsg()) {
				result.setError_msg("业务异常" + resp.getDetail());
				result.setError_code(resp.getCode());
				// 标记异常
				Utils.markException(order_id, false);
			}
			String bssOrderId = "";
			if (!StringUtil.isEmpty(resp.getResultMsg().getProvOrderId())) {
				bssOrderId = resp.getResultMsg().getProvOrderId();
			}
			if (!StringUtil.isEmpty(resp.getResultMsg().getBssOrderId())) {
				bssOrderId = resp.getResultMsg().getBssOrderId();
			}
			if (StringUtil.isEmpty(bssOrderId)) {
				result.setError_msg("业务异常" + resp.getResultMsg().getDetail());
				result.setError_code(resp.getResultMsg().getCode());
				// 标记异常
				Utils.markException(order_id, false);
			} else {
				// 记录返回
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
				OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
				orderItemExtBusiRequest.setBssOrderId(bssOrderId);
				orderItemExtBusiRequest.setActive_no(bssOrderId);
				orderItemExtBusiRequest.setBss_status("1");// 业务办理成功
				orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderItemExtBusiRequest.store();

				result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
				result.setError_msg("成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setError_msg("流量包接口异常");
			result.setError_code("-1");
		}

		result.setResponse(resp);
		return result;
	}

	/**
	 * 视频包：
	 * 
	 * @author songqi
	 * @param order_id
	 * @return
	 */
	public BusiDealResult spPacket(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		FlowPacketPreReq req = new FlowPacketPreReq();
		req.setNotNeedReqStrOrderId(order_id);
		FlowPacketPreRsp resp = null;
		try {
			resp = client.execute(req, FlowPacketPreRsp.class);// 调用接口
			if (null == resp.getResultMsg()) {
				result.setError_msg("业务异常" + resp.getDetail());
				result.setError_code(resp.getCode());
				// 标记异常
				Utils.markException(order_id, false);
			}
			String bssOrderId = "";
			if (!StringUtil.isEmpty(resp.getResultMsg().getProvOrderId())) {
				bssOrderId = resp.getResultMsg().getProvOrderId();
			}
			if (!StringUtil.isEmpty(resp.getResultMsg().getBssOrderId())) {
				bssOrderId = resp.getResultMsg().getBssOrderId();
			}
			if (StringUtil.isEmpty(bssOrderId)) {
				result.setError_msg("业务异常" + resp.getResultMsg().getDetail());
				result.setError_code(resp.getResultMsg().getCode());
				// 标记异常
				Utils.markException(order_id, false);
			} else {
				// 记录返回
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
				OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
				orderItemExtBusiRequest.setBssOrderId(bssOrderId);
				orderItemExtBusiRequest.setActive_no(bssOrderId);
				orderItemExtBusiRequest.setBss_status("1");// 业务办理成功
				orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderItemExtBusiRequest.store();

				result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
				result.setError_msg("成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setError_msg("视频包接口异常");
			result.setError_code("-1");
		}
		result.setResponse(resp);
		return result;
	}

	/**
	 * 换机提交
	 */
	@Override
	public BusiDealResult changeMachineSubmit(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ChangeMachineSubReq req = new ChangeMachineSubReq();
		req.setNotNeedReqStrOrderId(order_id);
		ChangeMachineSubResp rsp = client.execute(req, ChangeMachineSubResp.class);// 调用接口

		// 获取订单树
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);

		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
			result.setError_msg(rsp.getDetail());
			result.setError_code(rsp.getCode());

			// 标记异常
			Utils.markException(order_id, false);
		} else {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ZB_STATUS, AttrConsts.ACTIVE_NO, AttrConsts.BSS_ACCOUNT_TIME, AttrConsts.BSS_OPERATOR, AttrConsts.BSS_OPERATOR_NAME, AttrConsts.BSSORDERID },
					new String[] { EcsOrderConsts.ZB_ORDER_STATE_N06, rsp.getProvOrderId(), dateformat.format(new Date()), req.getOperatorId(), req.getEssOperInfo().getStaffName(),
							rsp.getBssOrderId() });

			// //更新货品记录开户信息相关字段
			// OrderItemBusiRequest orderItemBusi =
			// orderTree.getOrderItemBusiRequests().get(0);
			// OrderItemExtBusiRequest orderItemBusiExt =
			// orderItemBusi.getOrderItemExtBusiRequest();
			// orderItemBusiExt.setAccount_status(EcsOrderConsts.ACCOUNT_STATUS_1);
			// orderItemBusiExt.setAccount_time(AttrConsts.BSS_ACCOUNT_TIME);
			// orderItemBusiExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			// orderItemBusiExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			// orderItemBusiExt.store();

			// 开户结果要通知新商城：仅通知，不用考虑新商城接受的状态。流程运行到写卡环节，获取到总部的写卡数据时还要通知新商城
			// StatuSynchReq statuSyn = new
			// StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_D,EcsOrderConsts.DIC_ORDER_NODE_D_DESC,EcsOrderConsts.ACCOUNT_STATUS_1,EcsOrderConsts.ACCOUNT_STATUS_1_DESC,"");
			// CommonDataFactory.getInstance().notifyNewShop(statuSyn);

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * 订单系统访问森锐仿真系统
	 */
	@Override
	public BusiDealResult simulation(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		SimulationRequset req = new SimulationRequset();
		req.setNotNeedReqStrOrderId(order_id);
		SimulationResponse resp = client.execute(req, SimulationResponse.class);
		if (EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getError_code())) {// 接口调用成功并返回结果
			if (EcsOrderConsts.SIMULATION_SUCC_CODE.equals(resp.getCode())) {// 仿真派单成功
				result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);// 设置：业务处理成功
				result.setResponse(resp);
			} else {// 仿真派单失败
				result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);// 设置：业务处理失败
				result.setError_msg("向森锐仿真系统派单失败");
			}
		} else {// 接口调用问题
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
			result.setError_msg(resp.getError_msg());
		}

		return result;
	}

	/**
	 * 裸机销售
	 */
	@Override
	public BusiDealResult bareMachineSale(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		BareMachineSaleReq req = new BareMachineSaleReq();
		req.setNotNeedReqStrOrderId(order_id);

		BareMachineSaleResp rsp = client.execute(req, BareMachineSaleResp.class);

		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {// 失败
			// 设置错误返回码
			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getDetail()) ? rsp.getCode() : rsp.getDetail()));

			// 标记异常
			Utils.markException(order_id, false);

		} else {// 成功

			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.ACCOUNT_STATUS, AttrConsts.ZB_STATUS, AttrConsts.BSS_ACCOUNT_TIME, AttrConsts.BSS_OPERATOR, AttrConsts.BSS_OPERATOR_NAME, AttrConsts.BSSORDERID,
							AttrConsts.ZB_ESS_ORDER_ID },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_1, EcsOrderConsts.ZB_ORDER_STATE_N06, dateformat.format(new Date()), req.getOperatorId(), req.getEssOperInfo().getStaffName(),
							rsp.getBssOrderId(), rsp.getEssOrderId() });

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	@Override
	public BusiDealResult handleSpServices(String orderId) throws ApiBusiException {
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		BusiDealResult result = new BusiDealResult();
		// 根据orderId查询订单树
		OrderTreeBusiRequest orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(orderId);
		List<OrderSpProductBusiRequest> orderSps = orderTreeBusiRequest.getSpProductBusiRequest();
		// String sql = "SELECT sp_code,sp_id,sp_inst_id,status,order_id FROM
		// es_order_sp_product WHERE order_id=? AND (status=0 OR status=1)";
		// List<OrderSpProductBusiRequest> orderSps =
		// this.baseDaoSupport.queryForList(sql,
		// OrderSpProductBusiRequest.class, orderId);
		// 对sp服务列表进行处理
		StringBuffer errorDetail = new StringBuffer();// 处理错误信息描述
		result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);// 默认是成功状态，倘若有一个失败，则认为失败，返回那个调用失败的错误信息
		boolean flag = false; // 标记接口是否调用成功
		// logger.info("orderSps:"+orderSps.size());
		if (orderSps != null) {
			for (OrderSpProductBusiRequest orderSp : orderSps) {
				// 只对0：待办理 1：办理失败的进行处理
				if (EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2.equals(orderSp.getStatus()) || EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3.equals(orderSp.getStatus())) {
					continue;
				}
				HandleSpServiceReq req = new HandleSpServiceReq();
				req.setSp_product_id(orderSp.getSp_id());// 仕鹏反馈接口sp_id、sp_product_id传反
				req.setSp_id(orderSp.getSp_code());
				req.setNotNeedReqStrOrderId(orderId);
				req.setTrade_tag(EcsOrderConsts.ORDER_SP_PRODUCT_TRACE_CODE_O);
				// 调用增值业务订购接口
				HandleSpServiceResp rsp = client.execute(req, HandleSpServiceResp.class);
				Map<String, String> param = new HashMap<String, String>();
				// 对接口调用结果进行处理
				if (EcsOrderConsts.AOP_SUCCESS_0000.equals(rsp.getRespCode())) {
					logger.info("sp_inst_id:" + orderSp.getSp_inst_id() + " 调用增值业务订购接口成功！");
					param.put("status", EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);// 线上处理成功
				} else {
					logger.info("sp_inst_id:" + orderSp.getSp_inst_id() + " 调用增值业务订购接口失败！");
					errorDetail.append("sp_inst_id:" + orderSp.getSp_inst_id() + " 调用增值业务订购接口失败！</br>");
					// 接口调用失败也设置为成功，确保不卡流程;2016-06-07,这里设置失败，外层可以根据此状态决定是否卡流程
					result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
					param.put("status", EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1);// 处理失败
					flag = true;
				}
				// 更新状态
				try {
					// this.ordArchiveTacheManager.updateOrderSpProductState(param);
					orderSp.setStatus(param.get("status"));
					orderSp.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderSp.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					orderSp.store();
				} catch (Exception e) {
					logger.info("sp_inst_id:" + orderSp.getSp_inst_id() + " 更新状态失败！");
					errorDetail.append("sp_inst_id:" + orderSp.getSp_inst_id() + " 更新状态失败！");
					e.printStackTrace();
				}
				// 接口调用失败退出循环
				if (flag) {
					break;
				}
			}
		}
		result.setError_msg(errorDetail.toString());

		return result;
	}

	/**
	 * 业务补办(目前暂有SP服务 、附加产品)
	 */
	@Override
	public String rehandleBusiness(Map<String, String> params) {
		String dealResult = "";

		String order_id = params.get("order_id");
		String operation = params.get("operation");
		String operationType = params.get("operationType");
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);// 不考虑历史单(order_id为null时,orderTree为null,不会抛错)
		if (null == orderTree) {
			dealResult = "订单不存在或已归档";
			return dealResult;
		}

		if (EcsOrderConsts.REHANDLER_BUSINESS_SP.equals(operationType)) {
			// SP业务补办
			return rehandleBusinessSP(params);
		} else if (EcsOrderConsts.REHANDLER_BUSINESS_PACKAGE_SUB_PRODUCT.equals(operationType)) {
			// 附加产品补办
			return rehandleBusinessSubProduct(params);
		} else {
			return "不存在的补办类型，请检查！" + params;
		}
	}

	/**
	 * SP业务补办
	 * 
	 * @param params
	 * @return
	 */
	public String rehandleBusinessSP(Map<String, String> params) {
		String dealResult = "";

		String order_id = params.get("order_id");
		String operation = params.get("operation");// 办理类型:线上；线下(约定与rehandleBusinessList页面一致)
		String sp_inst_id = params.get("sp_inst_id");// 要办理的服务，为all时则全部办理(剔除已办理的)
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);// 不考虑历史单(order_id为null时,orderTree为null,不会抛错)
		List<OrderSpProductBusiRequest> spProductList = orderTree.getSpProductBusiRequest();
		if ("markOffline".equals(operation)) {// 线下办理
			if ("all".equals(sp_inst_id)) {// 全量办理
				for (OrderSpProductBusiRequest spProduct : spProductList) {
					if (!(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2.equals(spProduct.getStatus()) || EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3.equals(spProduct.getStatus()))) {// 未办理成功的标记为线下办理成功(待换常量)
						spProduct.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2);
						spProduct.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						spProduct.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						spProduct.store();
					}
				}
				dealResult = "全部成功标记线下完成";
			} else if (null != sp_inst_id) {// 指定办理(sp_inst_id为空基本判定是页面传值出现问题)
				String sp_name = "";
				for (OrderSpProductBusiRequest spProduct : spProductList) {
					if (!(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2.equals(spProduct.getStatus()) || EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3.equals(spProduct.getStatus()))
							&& sp_inst_id.equals(spProduct.getSp_inst_id())) {// 未办理成功的标记为线下办理成功(待换常量)
						sp_name = spProduct.getSp_name();
						spProduct.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2);
						spProduct.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						spProduct.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						spProduct.store();
						break;// 默认sp_inst_id不会重复
					}
				}
				dealResult = sp_name + "成功标记线下完成";
			} else {// sp_inst_id传值出现问题
				dealResult = "成功标记线下完成";
			}
		} else if ("reHandle".equals(operation)) {// 线上重新办理

			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			HandleSpServiceReq req = null;
			HandleSpServiceResp resp = null;
			if ("all".equals(sp_inst_id)) {// 全量办理
				StringBuffer fail_names = new StringBuffer();
				boolean allSuccess = true;
				for (OrderSpProductBusiRequest spProduct : spProductList) {
					if (!(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2.equals(spProduct.getStatus()) || EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3.equals(spProduct.getStatus()))) {// 未办理成功进行线上补办(待换常量)
						// 调接口
						req = new HandleSpServiceReq();
						/**
						 * 参数设置开始
						 */
						req.setNotNeedReqStrOrderId(order_id);
						req.setSp_id(spProduct.getSp_code());// 仕鹏反馈接口sp_id、sp_product_id传反
						req.setSp_product_id(spProduct.getSp_id());
						req.setTrade_tag(EcsOrderConsts.ORDER_SP_PRODUCT_TRACE_CODE_O);
						/**
						 * 参数设置结束
						 */
						resp = client.execute(req, HandleSpServiceResp.class);
						if (EcsOrderConsts.AOP_SUCCESS_0000.equals(resp.getRespCode())) {// 调接口成功,成功编码待确认
							spProduct.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);// 线上办理成功
							spProduct.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							spProduct.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							spProduct.store();
						} else {
							allSuccess = false;
							fail_names.append(spProduct.getSp_name()).append(',');
							if (!EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1.equals(spProduct.getStatus())) {// 调接口失败，原本非办理失败的才改为办理失败
								spProduct.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1);// 办理失败
								spProduct.setDb_action(ConstsCore.DB_ACTION_UPDATE);
								spProduct.setIs_dirty(ConstsCore.IS_DIRTY_YES);
								spProduct.store();
							}
							break;
						}
					}
				}
				if (allSuccess) {
					dealResult = "全部补办完成";
				} else {
					dealResult = fail_names.toString() + "办理失败,其余皆补办成功";
				}
			} else if (null != sp_inst_id) {// 指定办理(sp_inst_id为空基本判定是页面传值出现问题)
				String sp_name = "";
				boolean in_flag = false;
				for (OrderSpProductBusiRequest spProduct : spProductList) {
					if (!(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2.equals(spProduct.getStatus()) || EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3.equals(spProduct.getStatus()))
							&& sp_inst_id.equals(spProduct.getSp_inst_id())) {// 未办理成功进行线上补办(待换常量)
						in_flag = true;// 存在此条记录
						sp_name = spProduct.getSp_name();
						// 调接口
						req = new HandleSpServiceReq();
						/**
						 * 参数设置开始
						 */
						req.setNotNeedReqStrOrderId(order_id);
						req.setSp_id(spProduct.getSp_code());// 仕鹏反馈接口sp_id、sp_product_id传反
						req.setSp_product_id(spProduct.getSp_id());
						req.setTrade_tag(EcsOrderConsts.ORDER_SP_PRODUCT_TRACE_CODE_O);
						/**
						 * 参数设置结束
						 */
						resp = client.execute(req, HandleSpServiceResp.class);
						if (EcsOrderConsts.AOP_SUCCESS_0000.equals(resp.getRespCode())) {// 调接口成功,成功编码待确认
							spProduct.setStatus("3");// 线上办理成功
							spProduct.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							spProduct.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							spProduct.store();
							dealResult = sp_name + "补办完成";
						} else {
							if (!EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1.equals(spProduct.getStatus())) {// 调接口失败，原本非办理失败的才改为办理失败
								spProduct.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1);// 办理失败
								spProduct.setDb_action(ConstsCore.DB_ACTION_UPDATE);
								spProduct.setIs_dirty(ConstsCore.IS_DIRTY_YES);
								spProduct.store();
							}
							dealResult = sp_name + "补办失败";
						}
						break;// 默认sp_inst_id不会重复
					}
				}
				if (!in_flag) {
					dealResult = "无此业务,补办失败";
				}
			} else {
				dealResult = "重新办理成功";
			}
		} else {
			dealResult = "您并未做任何操作";
		}
		return dealResult;
	}

	/**
	 * 附加产品补办
	 * 
	 * @param params
	 * @return
	 */
	public String rehandleBusinessSubProduct(Map<String, String> params) {
		// 附加产品业务补办
		String dealResult = "";
		try {
			String order_id = params.get("order_id");
			String operation = params.get("operation");
			String sp_inst_id = params.get("sp_inst_id");
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);// 不考虑历史单(order_id为null时,orderTree为null,不会抛错)
			if (StringUtils.isNotEmpty(operation) && operation.equals("markOffline")) {
				// 线下补办
				List<AttrPackageSubProdBusiRequest> attrPackageSubProdBusiRequest = orderTree.getAttrPackageSubProdBusiRequest();
				List<OrderSubProductBusiRequest> orderSubProductBusiRequest = orderTree.getOrderSubProductBusiRequest();
				for (OrderSubProductBusiRequest subProduct : orderSubProductBusiRequest) {
					if (!"0".equals(subProduct.getProd_inst_id())) {
						continue;
					}
					for (AttrPackageSubProdBusiRequest attrPackageSubProduct : attrPackageSubProdBusiRequest) {
						if (!(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2.equals(attrPackageSubProduct.getStatus())
								|| EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3.equals(attrPackageSubProduct.getStatus()))) {// 未办理成功的标记为线下办理成功(待换常量)
							if (StringUtils.isNotEmpty(sp_inst_id) && "all".equals(sp_inst_id)) {
								attrPackageSubProduct.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2);
								attrPackageSubProduct.setDb_action(ConstsCore.DB_ACTION_UPDATE);
								attrPackageSubProduct.setIs_dirty(ConstsCore.IS_DIRTY_YES);
								attrPackageSubProduct.store();
								dealResult = "全部成功标记线下完成";
							} else if (StringUtils.isNotEmpty(sp_inst_id) && !"all".equals(sp_inst_id) && attrPackageSubProduct.getPackage_inst_id().equals(sp_inst_id)) {
								attrPackageSubProduct.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2);
								attrPackageSubProduct.setDb_action(ConstsCore.DB_ACTION_UPDATE);
								attrPackageSubProduct.setIs_dirty(ConstsCore.IS_DIRTY_YES);
								attrPackageSubProduct.store();
								dealResult = attrPackageSubProduct.getPackage_name() + "成功标记线下完成";
							}
						}
					}
				}
			} else if (StringUtils.isNotEmpty(operation) && operation.equals("reHandle")) {
				// 线上补办
				// 套餐变更申请
				BusiDealResult busiResult = ordOpenAccountTacheManager.productChangeApply(order_id);
				if (!StringUtils.equals(busiResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)) {
					dealResult = "附加产品补办失败;" + busiResult.getError_msg();
				} else {
					// 套餐变更提交
					busiResult = ordOpenAccountTacheManager.productChangeSubmit(order_id);
					if (!StringUtils.equals(busiResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)) {
						dealResult = "附加产品补办失败;" + busiResult.getError_msg();
					} else {
						dealResult = "附加产品补办成功";
					}
				}
			} else {
				dealResult = "不存在的补办操作，请检查！" + params;
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			dealResult = "附加产品补办失败;" + e.getMessage();
		}
		return dealResult;
	}

	@Override
	public BusiDealResult businessStatusSync(String order_id) throws ApiBusiException {
		BusiDealResult dealResult = new BusiDealResult();
		StatuSynchReq statuSynY = new StatuSynchReq(order_id, EcsOrderConsts.DIC_ORDER_NODE_Y, EcsOrderConsts.DIC_ORDER_NODE_Y_DESC, ZjEcsOrderConsts.BSS_STATUS_2, ZjEcsOrderConsts.BSS_STATUS_2_DESC,
				"");
		ZteResponse resp = CommonDataFactory.getInstance().notifyNewShop(statuSynY);
		dealResult.setError_code(resp.getError_code());
		dealResult.setError_msg(resp.getError_msg());
		dealResult.setResponse(resp);
		return dealResult;
	}

	/**
	 * 附加产品订购(广东AOP->BSS)
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult subProOrder(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		boolean success = true;
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String first_payment = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.FIRST_PAYMENT);// 首月资费方式(订单系统编码)
		String enable_tag = CommonDataFactory.getInstance().getOtherDictVodeValue("gdaop_first_payment", first_payment);// 生效模式(接口协议编码)
		if (StringUtils.isEmpty(enable_tag)) {
			success = false;
			result.setError_msg("未获取到生效模式，请检查订单数据或配置信息");
		}
		// 非套餐下附加产品办理
		if (success) {
			List<AttrPackageSubProdBusiRequest> attrPackageSubProdBusiRequest = orderTree.getAttrPackageSubProdBusiRequest();
			List<OrderSubProductBusiRequest> orderSubProductBusiRequest = orderTree.getOrderSubProductBusiRequest();
			for (OrderSubProductBusiRequest subProduct : orderSubProductBusiRequest) {
				if (!"0".equals(subProduct.getProd_inst_id())) {// 非主卡不办理
					continue;
				}
				for (AttrPackageSubProdBusiRequest attrPackageSubProd : attrPackageSubProdBusiRequest) {
					if ((StringUtils.equals(subProduct.getSub_pro_inst_id(), attrPackageSubProd.getSubProd_inst_id()))
							&& !(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2.equals(attrPackageSubProd.getStatus()) || EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3.equals(attrPackageSubProd.getStatus()))) {// 附加产品下的可选包
						// 调接口
						SubProOrderReq req = new SubProOrderReq();
						req.setNotNeedReqStrOrderId(order_id);
						req.setProduct_id(attrPackageSubProd.getProduct_code());
						req.setPackage_id(attrPackageSubProd.getPackage_code());
						req.setElement_id(attrPackageSubProd.getElement_code());
						req.setEnable_tag(enable_tag);// 与开户接口使用的生效方式相同，确认是否有既存映射关系，否则新建
						req.setOper_type("F");// 附加产品
						SubProOrderResp resp = client.execute(req, SubProOrderResp.class);

						if (EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getError_code())) {// 接口成功
							attrPackageSubProd.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);// 线上办理成功
							attrPackageSubProd.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							attrPackageSubProd.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							attrPackageSubProd.store();
						} else {// 接口失败
							attrPackageSubProd.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1);// 办理失败
							attrPackageSubProd.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							attrPackageSubProd.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							attrPackageSubProd.store();
							result.setError_msg(resp.getError_msg());
							success = false;
							break;
						}
					}
				}
			}
		}
		// 套餐下附加产品办理
		if (success) {// 所有非套餐下附加产品办理成功
			List<AttrPackageBusiRequest> packageBusiRequests = orderTree.getPackageBusiRequests();
			for (AttrPackageBusiRequest package_ : packageBusiRequests) {
				if (!(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2.equals(package_.getStatus()) || EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3.equals(package_.getStatus()))) {
					// 调接口
					SubProOrderReq req = new SubProOrderReq();
					req.setNotNeedReqStrOrderId(order_id);
					req.setProduct_id("-1");// 主产品设-1
					req.setPackage_id(package_.getPackageCode());
					req.setElement_id(package_.getElementCode());
					req.setEnable_tag(enable_tag);// 与开户接口使用的生效方式相同，确认是否有既存映射关系，否则新建
					req.setOper_type("E");// 主产品
					SubProOrderResp resp = client.execute(req, SubProOrderResp.class);

					if (EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getError_code())) {// 接口成功
						package_.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);// 线上办理成功
						package_.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						package_.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						package_.store();
					} else {// 接口失败
						package_.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1);// 办理失败
						package_.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						package_.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						package_.store();
						result.setError_msg(resp.getError_msg());
						success = false;
						break;
					}
				}
			}
		}
		if (success) {// 所有产品办理成功
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);// 业务组件成功
			result.setError_msg("附加产品订购成功");
		} else {
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);// 业务组件失败
			Utils.markException(order_id, false);// 标记异常
		}
		return result;
	}

	@Override
	public BusiDealResult setStatusFinishComplete(String order_id) {
		BusiDealResult result = new BusiDealResult();
		String[] name = { AttrConsts.BSS_STATUS };
		String[] value = { ZjEcsOrderConsts.BSS_STATUS_2 };
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, name, value);
		result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
		return result;
	}

	@Override
	public BusiDealResult setStatusFinishNotComplete(String order_id) {
		BusiDealResult result = new BusiDealResult();
		String[] name = { AttrConsts.BSS_STATUS };
		String[] value = { ZjEcsOrderConsts.BSS_STATUS_1 };
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, name, value);
		result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
		return result;
	}

	/**
	 * 2.1.15 续约活动校验和受理
	 */
	@Override
	public BusiDealResult businessAcceptenceAndVerification(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		try {

			BusinessAcceptenceAndVerificationReq req = new BusinessAcceptenceAndVerificationReq();
			req.setQuery_data("1");// 暂时先设置为服务号码(1=服务号码2=上网账号)
			String phoneNum = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
			req.setQuery_data(phoneNum);
			req.setSubmit_type("1");// 暂时设置为 0=预提交1=正式提交 2=订单作废3=精准校验
			req.setSubmit_type(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.RESERVE9));
			req.setPre_submit_orderID(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACTIVE_NO));
			req.setChannel_type(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CHANNEL_TYPE));
			req.setDeveloper_first(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.DEVELOPMENT_CODE));
			req.setOffice_id(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OFFICE));
			req.setOperator_id(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OPERATOR));

			BusinessAcceptenceAndVerificationResponse resp = client.execute(req, BusinessAcceptenceAndVerificationResponse.class);

			if (EcsOrderConsts.AOP_SUCCESS_0000.equals(resp.getCode())) {
				result.setError_msg("调用续约活动校验和受理成功");
				System.out.print("调用续约活动校验和受理成功");
			} else {
				result.setError_code(resp.getError_code());
				result.setError_msg(resp.getError_msg());
			}
		} catch (Exception e) {
			result.setError_msg(e.getMessage());
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
		}
		return result;
	}

	
	public BusiDealResult paymentByBSS(String order_id){
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		try{
			AmountPayReq req = new AmountPayReq();
			req.setNotNeedReqStrOrderId(order_id);
			AmountPayResp resp = client.execute(req,AmountPayResp.class);
			if(EcsOrderConsts.BSS_SUCCESS_00000.equals(resp.getCode())){
				
				String bms_accept_id = resp.getBms_accept_id();
				if(!StringUtils.isEmpty(bms_accept_id)){
					OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
					OrderItemExtBusiRequest itemsExt = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest();
					itemsExt.setActive_no(bms_accept_id);
					itemsExt.setBssOrderId(bms_accept_id);
					itemsExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					itemsExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					itemsExt.store();
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{"active_no","bssOrderId"}, new String[]{bms_accept_id,bms_accept_id});
					result.setError_msg(resp.getMsg());
					result.setError_code("0");
				}else{
					result.setError_msg("未返回BSS单号,未知异常");
					result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
					Utils.markException(order_id, false, "", result.getError_msg());
				}
				
			}else{
				result.setError_msg(resp.getMsg());
				result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
				Utils.markException(order_id, false, "", result.getError_msg());
			}
		}catch(Exception e){
			
			result.setError_msg(e.getMessage());
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
			e.printStackTrace();
		}
		
		
		return result;
	}
}
