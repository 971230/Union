package com.ztesoft.net.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import params.ZteResponse;
import params.req.CallOutOperationReq;
import params.req.SingleApplicationReq;
import params.resp.CrawlerResp;
import util.EssOperatorInfoUtil;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.bss.req.CustomerCheckReq;
import zte.net.ecsord.params.bss.req.PreCommitReq;
import zte.net.ecsord.params.bss.resp.CustomerCheckResp;
import zte.net.ecsord.params.bss.resp.PreCommitResp;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.BSSCustomerInfoCheckReq;
import zte.net.ecsord.params.ecaop.req.BSSOrderReverseSalesReq;
import zte.net.ecsord.params.ecaop.req.CannelOrder23to4Request;
import zte.net.ecsord.params.ecaop.req.Check23to4Request;
import zte.net.ecsord.params.ecaop.req.CheckIDECAOPRequset;
import zte.net.ecsord.params.ecaop.req.CustInfoCreateReq;
import zte.net.ecsord.params.ecaop.req.CustomerInfoCheckReq;
import zte.net.ecsord.params.ecaop.req.DevelopPersonQueryReq;
import zte.net.ecsord.params.ecaop.req.EmployeesInfoCheckRequest;
import zte.net.ecsord.params.ecaop.req.OldUserCheck3GReq;
import zte.net.ecsord.params.ecaop.req.OrderQueryReq;
import zte.net.ecsord.params.ecaop.req.OrderReverseSalesReq;
import zte.net.ecsord.params.ecaop.req.ProdChangeCannelRequest;
import zte.net.ecsord.params.ecaop.req.ReturnFileReq;
import zte.net.ecsord.params.ecaop.req.UserCountCheckReq;
import zte.net.ecsord.params.ecaop.req.UserInfoCheck3BackReq;
import zte.net.ecsord.params.ecaop.req.UserInfoCheck3BackReqZJ;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.resp.BSSCustomerInfoResponse;
import zte.net.ecsord.params.ecaop.resp.BSSOrderReverseSalesResp;
import zte.net.ecsord.params.ecaop.resp.CannelOrder23to4Resp;
import zte.net.ecsord.params.ecaop.resp.Check23to4Resp;
import zte.net.ecsord.params.ecaop.resp.CheckIDECAOPResponse;
import zte.net.ecsord.params.ecaop.resp.CustInfoCreateResponse;
import zte.net.ecsord.params.ecaop.resp.CustomerInfoResponse;
import zte.net.ecsord.params.ecaop.resp.DevelopPersonResponse;
import zte.net.ecsord.params.ecaop.resp.EmployeesInfoResponse;
import zte.net.ecsord.params.ecaop.resp.OldUserCheck3GResp;
import zte.net.ecsord.params.ecaop.resp.OrderQueryRespone;
import zte.net.ecsord.params.ecaop.resp.OrderReverseSalesResp;
import zte.net.ecsord.params.ecaop.resp.ProdChangeCannelResp;
import zte.net.ecsord.params.ecaop.resp.ReturnFileResp;
import zte.net.ecsord.params.ecaop.resp.UserCountCheckRsp;
import zte.net.ecsord.params.ecaop.resp.UserInfoCheck3BackResp;
import zte.net.ecsord.params.ecaop.resp.vo.ActivityInfoRspVo;
import zte.net.ecsord.params.ecaop.resp.vo.ElementInfoRspVo;
import zte.net.ecsord.params.ecaop.resp.vo.ErrorListRspVo;
import zte.net.ecsord.params.ecaop.resp.vo.ExistedCustomerVo;
import zte.net.ecsord.params.ems.req.EmsLogisticsInfoSyncReq;
import zte.net.ecsord.params.ems.req.EmsLogisticsNumberGetReq;
import zte.net.ecsord.params.ems.req.LogisticsInfoSyncReq;
import zte.net.ecsord.params.ems.req.LogisticsNumberGetReq;
import zte.net.ecsord.params.ems.resp.EmsLogisticsInfoSyncResp;
import zte.net.ecsord.params.ems.resp.EmsLogisticsNumberGetResp;
import zte.net.ecsord.params.ems.resp.LogisticsInfoSyncResp;
import zte.net.ecsord.params.ems.resp.LogisticsNumberGetResp;
import zte.net.ecsord.params.nd.req.NotifyOrderInfoNDRequset;
import zte.net.ecsord.params.nd.resp.NotifyOrderInfoNDResponse;
import zte.net.ecsord.params.sf.req.NotifyOrderInfoSFRequset;
import zte.net.ecsord.params.sf.req.OrderSearchServiceRequest;
import zte.net.ecsord.params.sf.req.RouteServiceRequest;
import zte.net.ecsord.params.sf.resp.NotifyOrderInfoSFResponse;
import zte.net.ecsord.params.sf.resp.RouteServiceResponse;
import zte.net.ecsord.params.sf.vo.OrderOptionAddedService;
import zte.net.ecsord.params.sf.vo.Route;
import zte.net.ecsord.params.sf.vo.RouteResponse;
import zte.net.ecsord.params.wyg.req.StatuSynchReq;
import zte.net.ecsord.params.zb.req.CertCheckZBRequest;
import zte.net.ecsord.params.zb.req.NotifyOrderAbnormalToZBRequest;
import zte.net.ecsord.params.zb.req.PrecheckOpenAcctRequest;
import zte.net.ecsord.params.zb.req.StateSynToZBRequest;
import zte.net.ecsord.params.zb.req.StateUtil;
import zte.net.ecsord.params.zb.resp.CertCheckZBResponse;
import zte.net.ecsord.params.zb.resp.NotifyOrderAbnormalToZBResponse;
import zte.net.ecsord.params.zb.resp.PrecheckOpenAcctResponse;
import zte.net.ecsord.params.zb.resp.StateSynToZBResponse;
import zte.net.ecsord.params.zb.vo.orderattr.Fee;
import zte.net.ecsord.rule.tache.TacheFact;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.powerise.ibss.framework.Const;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.ecsord.params.ecaop.req.UserDataQryReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.UserDataQryResp;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.model.RouteInfoVO;
import com.ztesoft.net.model.SDSStatusLogVO;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IOrdVisitTacheManager;
import com.ztesoft.net.service.IOrderSDSModelManager;
import com.ztesoft.net.util.ZjCommonUtils;

import consts.ConstsCore;

/**
 * 回访环节处理类
 * 
 * @author xuefeng
 */
public class OrdVisitTacheManager extends BaseSupport implements IOrdVisitTacheManager {
	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Resource
	private IOrderSDSModelManager orderSDSModelManager;
	@Resource
	private IEcsOrdManager ecsOrdManager;

	@Override
	public BusiDealResult certiValide(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		String certi_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE);
		String account_vali = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACCOUNT_VALI);
		// 证件类型是身份证，没有校验通过的订单才进行校验 11/10
		if ((EcsOrderConsts.CERTI_TYPE_SFZ15.equals(certi_type) || EcsOrderConsts.CERTI_TYPE_SFZ18.equals(certi_type) || "12".equals(certi_type))
				&& !EcsOrderConsts.ACCOUNT_VALI_3.equals(account_vali)) {

			CheckIDECAOPRequset idReq = new CheckIDECAOPRequset();
			idReq.setNotNeedReqStrOrderId(order_id);
			CheckIDECAOPResponse idResp = client.execute(idReq, CheckIDECAOPResponse.class);
			if (StringUtils.equals(idResp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
				// 记录校验结果 、补充客户资料
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
						new String[] { AttrConsts.ACCOUNT_VALI, AttrConsts.CERT_CARD_BIRTH, AttrConsts.CERT_CARD_NATION },
						new String[] { EcsOrderConsts.ACCOUNT_VALI_1, idResp.getBirthday(), idResp.getNation() });
				if (!StringUtil.isEmpty(idResp.getExp())) {
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CERT_FAILURE_TIME },
							new String[] { idResp.getExp().substring(0, 4) + "-" + idResp.getExp().substring(4, 6) + "-"
									+ idResp.getExp().substring(6) + " 23:59:59" });
				}
				if (!StringUtil.isEmpty(idResp.getSex())) {
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CERTI_SEX },
							new String[] { idResp.getSex() });
				}
				String cert_address = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS);
				if (StringUtil.isEmpty(cert_address) || EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(cert_address)) {
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CERT_ADDRESS },
							new String[] { idResp.getAddr() });
					/*
					 * List<OrderItemBusiRequest> orderItem =
					 * CommonDataFactory.getInstance().getOrderTree(order_id).
					 * getOrderItemBusiRequests(); for (int i = 0; i <
					 * orderItem.size(); i++) { List<OrderItemProdBusiRequest>
					 * orderItemProdBusiRequests =
					 * orderItem.get(i).getOrderItemProdBusiRequests(); for (int
					 * j = 0; j < orderItemProdBusiRequests.size(); j++) {
					 * OrderItemProdExtBusiRequest orderItemProdExtBusiRequest =
					 * orderItemProdBusiRequests.get(i).
					 * getOrderItemProdExtBusiRequest();
					 * orderItemProdExtBusiRequest.setCert_address(idResp.
					 * getAddr());
					 * orderItemProdExtBusiRequest.setIs_dirty(ConstsCore.
					 * IS_DIRTY_YES);
					 * orderItemProdExtBusiRequest.setDb_action(ConstsCore.
					 * DB_ACTION_UPDATE); orderItemProdExtBusiRequest.store(); }
					 * }
					 */
				}
				if ((StringUtil.isEmpty(cert_address) || EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(cert_address))
						|| !StringUtil.isEmpty(idResp.getSex()) || !StringUtil.isEmpty(idResp.getExp())) {
					List<OrderItemBusiRequest> orderItem = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests();
					for (int i = 0; i < orderItem.size(); i++) {
						List<OrderItemProdBusiRequest> orderItemProdBusiRequests = orderItem.get(i).getOrderItemProdBusiRequests();
						for (int j = 0; j < orderItemProdBusiRequests.size(); j++) {
							OrderItemProdExtBusiRequest orderItemProdExtBusiRequest = orderItemProdBusiRequests.get(i)
									.getOrderItemProdExtBusiRequest();
							if (StringUtil.isEmpty(cert_address) || EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(cert_address)) {
								orderItemProdExtBusiRequest.setCert_address(idResp.getAddr());
							}
							if (!StringUtil.isEmpty(idResp.getExp())) {
								orderItemProdExtBusiRequest.setCert_failure_time(idResp.getExp().substring(0, 4) + "-"
										+ idResp.getExp().substring(4, 6) + "-" + idResp.getExp().substring(6) + " 23:59:59");
							}
							if (!StringUtil.isEmpty(idResp.getSex())) {
								orderItemProdExtBusiRequest.setCert_card_sex(idResp.getSex());
							}
							if (!StringUtil.isEmpty(idResp.getNation())) {
								orderItemProdExtBusiRequest.setCert_card_nation(idResp.getNation());
							}
							orderItemProdExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							orderItemProdExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							orderItemProdExtBusiRequest.store();
						}
					}
				}

				result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
				result.setResponse(idResp);
			} else {
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACCOUNT_VALI },
						new String[] { EcsOrderConsts.ACCOUNT_VALI_0 });
				// result.setError_code("-999999");
				// result.setError_msg("身份证预校验失败"+idResp.getError_msg());
				result.setError_code(idResp.getError_code());
				result.setError_msg(idResp.getError_msg());
			}
		}
		return result;
	}

	// 宋琪 客户下用户数据查询接口
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BusiDealResult userDataQry(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		UserDataQryReq req = new UserDataQryReq();// 其他组件一些req的把值写在了get方法里
		req.setNotNeedReqStrOrderId(order_id);
		// 办理操作员
		String deal_operator = "";
		String source_from = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOrder_from();
		String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OPERATOR);
		if (!StringUtils.isEmpty(source_from) && StringUtil.equals(source_from, "10071") && !StringUtil.isEmpty(OUT_OPERATOR)) {
			deal_operator = OUT_OPERATOR;
		} else {
			deal_operator = ZjCommonUtils.getGonghaoInfoByOrderId(order_id).getUser_code();
		}
		req.setOperator_id(deal_operator);// 办理操作员
		// 办理操作点
		String deal_office_id = "";
		String OUT_OFFICE = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OFFICE);
		if (!StringUtils.isEmpty(source_from) && StringUtil.equals(source_from, "10071") && !StringUtil.isEmpty(OUT_OFFICE)) {
			deal_office_id = OUT_OFFICE;
		} else {
			deal_office_id = ZjCommonUtils.getGonghaoInfoByOrderId(order_id).getDept_id();
		}
		req.setOffice_id(deal_office_id);// 办理操作点
		// select t.*,t.rowid from es_dc_public_ext t where
		// t.stype='DIC_BSS_CERT_TYPE'
		// 证件类型->转换
		String certi_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE);
		IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<Map> list = dcPublicCache.getList("DIC_BSS_CERT_TYPE");
		String pname = "";
		for (int i = 0; i < list.size(); i++) {
			if (StringUtil.equals(certi_type, (String) list.get(i).get("pkey"))) {
				pname = (String) list.get(i).get("pname");
				break;
			}
		}
		req.setCert_type(pname);// 证件类型
		req.setCert_num(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM));// 证件号码
		req.setAdd_num("1");// 附加用户数，默认1
		req.setCust_name(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BUYER_NAME));// 客户名称
		UserDataQryResp resp = client.execute(req, UserDataQryResp.class);
		// 判断是否符合 check_result返回为1，并且user_num小于5
		Map respMap = (Map) resp.getRespJson();
		boolean flag = false;
		if (null != respMap) {
			String check_result = Const.getStrValue(respMap, "check_result");
			String user_num = Const.getStrValue(respMap, "user_num");
			if (null == user_num || "".equals(user_num)) {
				user_num = "0";
			}
			if ("1".equals(check_result) && Integer.parseInt(user_num) < 5) {
				flag = true;
			}
		}
		if (flag) {
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);// 查询成功
			result.setResponse(resp);
		} else {
			/**
			 * 操作成功 00000 系统错误（网络连接错误、网络连接超时） 00002 数据库查找操作出错 00601 数据库更新操作出错
			 * 00606
			 */
			result.setError_code("-999999");
			if ("00000".equals(resp.getCode())) {
				result.setError_msg("查询成功:不符合一证5卡");
			} else if ("00002".equals(resp.getCode())) {
				result.setError_msg("系统错误（网络连接错误、网络连接超时）");
			} else if ("00601".equals(resp.getCode())) {
				result.setError_msg("数据库查找操作出错");
			} else if ("00606".equals(resp.getCode())) {
				result.setError_msg("数据库更新操作出错");
			} else {
				result.setError_msg("不符合一证5卡(其他异常)");
			}
		}
		return result;
	}

	/**
	 * 宋琪 订单异常通知 通知/接收异常状态，挂起或恢复订单生产
	 */
	@Override
	public BusiDealResult notifyOrderAbnormalToZB(BusiCompRequest busiCompRequest, String order_id) {
		BusiDealResult result = new BusiDealResult();
		try {
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			NotifyOrderAbnormalToZBRequest req = new NotifyOrderAbnormalToZBRequest();
			req.setNotNeedReqStrOrderId(order_id);// 其他属性在get方法里有写
			// OrderExtBusiRequest orderExt =
			// CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			Map map = busiCompRequest.getQueryParams();
			String exceptionCode = map.get("msg").toString();
			String exceptionType = "GZTCHECKFAIL";
			String exceptionDesc = "";
			if ("201".equals(exceptionCode)) {
				exceptionDesc = "姓名证件号不匹配";
			} else if ("0001".equals(exceptionCode)) {
				exceptionDesc = "没有查询到该身份证号码";
			} else if ("0002".equals(exceptionCode)) {
				exceptionDesc = "姓名核查不一致";
			} else if ("0003".equals(exceptionCode)) {
				exceptionDesc = "很抱歉，本地系统认证暂时关闭";
			} else if ("0004".equals(exceptionCode)) {
				exceptionDesc = "很抱歉，公安认证暂时关闭";
			} else if ("0005".equals(exceptionCode)) {
				exceptionDesc = "国政通无头像";
			} else if ("9999".equals(exceptionCode) || "-1".equals(exceptionCode)) {
				exceptionCode = "9999";
				exceptionDesc = "其他错误";
			} else {
				exceptionCode = "9999";
				exceptionDesc = "其他错误";
			}
			if (!StringUtil.isEmpty(exceptionType)) {
				req.setNoticeType(EcsOrderConsts.Notice_Type_MarkException);
				req.setExceptionType(exceptionType);
				req.setExceptionCode(exceptionCode);
				req.setExceptionDesc(exceptionDesc);
			} else {
				result.setError_code("-1");
				result.setError_msg("订单无异常信息,异常没有录入");
				return result;
			}
			// 异常通知类型 MarkException标记异常 RestorationException恢复异常
			// req.setNoticeType("");
			// 异常类型 NoGoods缺货异常 OpenEr开户异常 BSSEr业务办理异常（本来就要手工办理，理论上不需要通知异常）
			// CHECKEr稽核异常 GZTCHECKFAIL国政通校验异常
			// req.setExceptionType("");
			// 异常编码 201：姓名证件号不匹配 0001 没有查询到该身份证号码 0002 姓名核查不一致 0003
			// 很抱歉，本地系统认证暂时关闭 0004 很抱歉，公安认证暂时关闭 0005 国政通无头像 9999 其他错误
			// req.setExceptionCode("");
			// 异常描述
			// req.setExceptionDesc("");

			NotifyOrderAbnormalToZBResponse resp = client.execute(req, NotifyOrderAbnormalToZBResponse.class);
			if (EcsOrderConsts.ZB_RETURN_0000.equals(resp.getRespCode())) {
				result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
				result.setError_msg("订单异常通知成功");
			} else if (EcsOrderConsts.ZB_RETURN_9999.equals(resp.getRespCode())) {
				result.setError_code(EcsOrderConsts.ZB_RETURN_9999);
				result.setError_msg("订单异常通知失败,(总部)其它错误" + resp.getRespDesc());
			} else {
				result.setError_code(resp.getRespCode());
				result.setError_msg("其它错误" + resp.getRespDesc());
			}
			result.setResponse(resp);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError_code("-9999999");
			result.setError_msg("订单异常通知失败," + e.getMessage());
		}
		return result;
	}

	/**
	 * 12周岁校验
	 * 
	 * @author 宋琪 2017年6月24日 11:52:39
	 */
	@Override
	public BusiDealResult ageValide(String order_id, int age) {
		BusiDealResult result = new BusiDealResult();
		String idCard = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM);
		if (isIdCrad(idCard, age)) {// 校验成功
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
		} else {// 失败
			result.setError_code("-999999");
			result.setError_msg(age + "周岁校验失败");
		}
		return result;
	}

	/**
	 * 宋琪2017年6月24日 11:52:34 判断身份证格式
	 * 
	 * @param idNum
	 * @return
	 */
	public boolean isIdCrad(String idNum, int age) {
		// 中国公民身份证格式：长度为15或18位，最后一位可以为字母
		Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
		// 格式验证
		if (!idNumPattern.matcher(idNum).matches())
			return false;
		// 合法性验证
		int year = 0;
		int month = 0;
		int day = 0;
		if (idNum.length() == 15) {
			// 一代身份证
			// logger.info("一代身份证：" + idNum);
			// 提取身份证上的前6位以及出生年月日
			Pattern birthDatePattern = Pattern.compile("\\d{6}(\\d{2})(\\d{2})(\\d{2}).*");
			Matcher birthDateMather = birthDatePattern.matcher(idNum);
			if (birthDateMather.find()) {
				year = Integer.valueOf("19" + birthDateMather.group(1));
				month = Integer.valueOf(birthDateMather.group(2));
				day = Integer.valueOf(birthDateMather.group(3));
			}
		} else if (idNum.length() == 18) {
			// 二代身份证
			// logger.info("二代身份证：" + idNum);
			// 提取身份证上的前6位以及出生年月日
			Pattern birthDatePattern = Pattern.compile("\\d{6}(\\d{4})(\\d{2})(\\d{2}).*");
			Matcher birthDateMather = birthDatePattern.matcher(idNum);
			if (birthDateMather.find()) {
				year = Integer.valueOf(birthDateMather.group(1));
				month = Integer.valueOf(birthDateMather.group(2));
				day = Integer.valueOf(birthDateMather.group(3));
			}
		}
		// 年份判断
		Calendar cal = Calendar.getInstance();
		// 当前年份
		int currentYear = cal.get(Calendar.YEAR);
		int currentMonth = cal.get(Calendar.MONTH) + 1;
		int currentDay = cal.get(Calendar.DAY_OF_MONTH);
		if (year > currentYear)
			return false;
		// 月份判断
		if (month < 1 || month > 12)
			return false;
		// 日期判断
		// 计算月份天数
		int dayCount = 31;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			dayCount = 31;
			break;
		case 2:
			// 2月份判断是否为闰年
			if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
				dayCount = 29;
				break;
			} else {
				dayCount = 28;
				break;
			}
		case 4:
		case 6:
		case 9:
		case 11:
			dayCount = 30;
			break;
		}
		// logger.info(String.format("生日：%d年%d月%d日", year, month, day));
		// logger.info(month + "月份有：" + dayCount + "天");
		if (day < 1 || day > dayCount)
			return false;
		Integer max_num = age;
		// 大于12、16周岁
		if ((currentYear - year) > max_num) {
			return true;
		} else if ((currentYear - year) == max_num) {
			if (currentMonth > month) {
				return true;
			} else if (currentMonth == month) {
				if (currentDay >= day) {
					return true;
				} else {
					return false;
				}
			} else if (currentMonth < month) {
				return false;
			}
		} else {
			return false;
		}

		return true;
	}

	@Override
	public BusiDealResult queryPicinfo(String order_id) {// 这里要内部单号
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		CertCheckZBRequest req = new CertCheckZBRequest();
		req.setNotNeedReqStrOrderId(order_id);// 这里要总部单号，不过没关系，CertCheckZBRequest已经强制让order_id返回总部单号
		CertCheckZBResponse resp = client.execute(req, CertCheckZBResponse.class);// 调接口（接口做了封装，保证resp不为空）
		if (EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getError_code())) {// 接口调通并返回了结果，这结果是总部给的
			if (EcsOrderConsts.GET_ACCOUNT_PHOTO_FROM_ZB_0000.equals(resp.getRespCode())) {// 总部返回的是成功。
				result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);// 设置：业务处理逻辑成功
				result.setResponse(resp);
			} else if (EcsOrderConsts.GET_ACCOUNT_PHOTO_FROM_ZB_NO_EXSITS.equals(resp.getRespCode())) {// 总部说该订单对应的照片不存在
				result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
				result.setError_msg("证件照片不存在");
			} else {// 总部说其它错误
				result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
				result.setError_msg("其它错误(总部说的)");
			}
		} else {// 调接口出问题了
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
			result.setError_msg(resp.getError_msg());
		}

		return result;
	}

	@Override
	public BusiDealResult preOpenAccountValide(String order_id) {
		BusiDealResult result = new BusiDealResult();
		OrderItemExtBusiRequest orderItemExtUpdate = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0)
				.getOrderItemExtBusiRequest();

		if (EcsOrderConsts.ESS_PRE_STATUS_1.equals(orderItemExtUpdate.getEss_pre_status())) {
			return result;

		}
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		PrecheckOpenAcctRequest req = new PrecheckOpenAcctRequest();
		req.setNotNeedReqStrOrderId(order_id);
		PrecheckOpenAcctResponse resp = client.execute(req, PrecheckOpenAcctResponse.class);
		if (resp.getAccountRsp().getRespCode().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000)) { // 总部预校验成功
			orderItemExtUpdate.setEss_pre_status(EcsOrderConsts.ESS_PRE_STATUS_1);
			orderItemExtUpdate.setEss_pre_desc("开户预校验成功");
			// 总部返回的费用
			List<Fee> fee = resp.getAccountRsp().getFeeInfo();

			// //如果开户申请成功，则清除数据库中以前存在的预校验接口返回的费用明细，替换为最新的(is_aop_fee为空表示归集的费用，不处理)
			// baseDaoSupport.execute("delete from es_attr_fee_info where
			// is_aop_fee='"+EcsOrderConsts.BASE_YES_FLAG_1+"' and order_id =
			// ?", order_id);
			// //删除之后，刷新一下订单树
			// CommonDataFactory.getInstance().updateOrderTree(order_id);

			// 获取订单费用
			List<AttrFeeInfoBusiRequest> oldfeelist = CommonDataFactory.getInstance().getOrderTree(order_id).getFeeInfoBusiRequests();
			// 删除旧的开户预提交返回的费用is_aop_fee=1
			for (AttrFeeInfoBusiRequest oldvo : oldfeelist) {
				if (StringUtils.equals(oldvo.getIs_aop_fee(), EcsOrderConsts.BASE_YES_FLAG_1)) {
					oldvo.setDb_action(ConstsCore.DB_ACTION_DELETE);
					oldvo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					oldvo.store();// 更新到数据库，同时更新到缓存
				}
			}

			for (Fee f : fee) {
				AttrFeeInfoBusiRequest r = new AttrFeeInfoBusiRequest();
				r.setFee_inst_id(this.baseDaoSupport.getSequences("seq_attr_fee_info"));
				r.setIs_aop_fee(EcsOrderConsts.BASE_YES_FLAG_1);
				r.setOrder_id(order_id);
				r.setInst_id(order_id);
				r.setFee_item_code(f.getFeeId());
				r.setFee_item_name(f.getFeeDes());
				r.setO_fee_num(f.getOrigFee() / 1000);
				r.setDiscount_fee(0.00);
				r.setMax_relief(f.getMaxRelief() / 1000);
				r.setDiscount_reason(f.getReliefResult());

				// 实收金额=应收金额-减免金额add by wui
				double real_fee = r.getO_fee_num() - r.getDiscount_fee();
				r.setN_fee_num(real_fee);

				r.setDb_action(ConstsCore.DB_ACTION_INSERT);
				r.setIs_dirty(ConstsCore.IS_DIRTY_YES);

				r.store();// 更新到数据库，同时更新到缓存
			}
			//
			// for(Fee f : fee){
			// boolean isexists = false;
			// for(AttrFeeInfoBusiRequest af : feeInfoBusiRequests){
			// if(af.getFee_item_code().equals(f.getFeeId())){
			// af.setFee_item_name(f.getFeeDes());
			// af.setO_fee_num(f.getOrigFee()/1000);
			// af.setDiscount_fee(f.getReliefFee()/1000);
			// af.setDiscount_reason(f.getReliefResult());
			//
			// //实收金额=应收金额-减免金额 add by wui
			// double real_fee = (f.getOrigFee()-f.getReliefFee())/1000;
			// af.setN_fee_num(real_fee);
			//
			// af.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			// af.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			// af.store();
			// isexists = true;
			// break ;
			// }
			// }
			// if(!isexists){
			// AttrFeeInfoBusiRequest r = new AttrFeeInfoBusiRequest();
			// r.setFee_inst_id(this.baseDaoSupport.getSequences("seq_attr_fee_info"));
			// r.setOrder_id(order_id);
			// r.setInst_id(order_id);
			// r.setFee_item_code(f.getFeeId());
			// r.setFee_item_name(f.getFeeDes());
			// r.setO_fee_num(f.getOrigFee()/1000);
			// r.setDiscount_fee(f.getReliefFee()/1000);
			// r.setDiscount_reason(f.getReliefResult());
			//
			// //实收金额=应收金额-减免金额add by wui
			// double real_fee = (f.getOrigFee()-f.getReliefFee())/1000;
			// r.setN_fee_num(real_fee);
			//
			// r.setDb_action(ConstsCore.DB_ACTION_INSERT);
			// r.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			// r.store();
			//// feeInfoBusiRequestsappend.add(r);
			// }
			// }
			// for(AttrFeeInfoBusiRequest r : feeInfoBusiRequestsappend){
			// feeInfoBusiRequests.add(r);
			// }
			// OrderTreeBusiRequest tree =
			// CommonDataFactory.getInstance().getOrderTree(order_id);
			// tree.setFeeInfoBusiRequests(feeInfoBusiRequestsappend);
			// tree.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			// tree.store();

			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ZB_STATUS },
					new String[] { EcsOrderConsts.ZB_ORDER_STATE_N02 });
		} else {
			orderItemExtUpdate.setEss_pre_status(EcsOrderConsts.ESS_PRE_STATUS_0);
			orderItemExtUpdate.setEss_pre_desc("错误编码：" + resp.getAccountRsp().getRespCode() + ";错误信息：" + resp.getAccountRsp().getRespDesc());
			result.setError_msg(orderItemExtUpdate.getEss_pre_desc());
		}

		BusiCompRequest busi = new BusiCompRequest();
		Map queryParams = new HashMap();
		AutoFact autoFact = new TacheFact();
		queryParams.put("fact", autoFact);
		StatuSynchReq statuSyn = new StatuSynchReq();
		statuSyn.setORDER_ID(order_id);
		statuSyn.setTRACE_CODE(EcsOrderConsts.DIC_ORDER_NODE_A);
		statuSyn.setTRACE_NAME("订单预处理环节");
		statuSyn.setDEAL_STATUS(orderItemExtUpdate.getEss_pre_status());
		statuSyn.setDEAL_DESC(orderItemExtUpdate.getEss_pre_desc());
		autoFact.setRequest(statuSyn);
		queryParams.put("order_id", order_id);
		busi.setEnginePath("zteCommonTraceRule.notifyWYG");
		busi.setOrder_id(order_id);
		busi.setQueryParams(queryParams);

		// 屏蔽预校验通知新商城功能
		// BusiCompResponse busiCompResp =
		// CommonDataFactory.getInstance().execBusiComp(busi);
		result.setError_code(resp.getAccountRsp().getRespCode());
		orderItemExtUpdate.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderItemExtUpdate.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderItemExtUpdate.store();
		return result;
	}

	@Override
	public BusiDealResult synOrdChangeToZB(String order_id) {
		BusiDealResult result = new BusiDealResult();
		// 调用总部同步接口
		/*
		 * ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
		 * .getSourceFrom()); NotifyOrderInfoZBRequest req = new
		 * NotifyOrderInfoZBRequest(); req.setNotNeedReqStrOrderId(order_id);
		 * NotifyOrderInfoZBResponse infResp = client.execute(req,
		 * NotifyOrderInfoZBResponse.class);
		 * if(!infResp.getRespCode().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000
		 * )){ result.setError_code(infResp.getRespCode());
		 * result.setError_msg("错误编码：" + infResp.getRespCode() + ";错误信息：" +
		 * infResp.getRespDesc()); }else{
		 * CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new
		 * String[]{AttrConsts.ZB_STATUS}, new
		 * String[]{EcsOrderConsts.ZB_ORDER_STATE_N03}); }
		 */
		try {
			// 添加到定时任务队列
			CoQueue queBak = new CoQueue();
			queBak.setService_code("CO_ORDERINFONOTIFY_ZB"); // service_code改为老系统
			queBak.setCo_id("");
			queBak.setCo_name("订单信息变更同步总部商城");
			queBak.setObject_id(order_id);
			queBak.setObject_type("DINGDAN");
			queBak.setStatus(Consts.CO_QUEUE_STATUS_WFS);
			String create_date = com.ztesoft.ibss.common.util.DateUtil.addDate(com.ztesoft.ibss.common.util.DateUtil.getTime2(),
					com.ztesoft.ibss.common.util.DateUtil.DATE_FORMAT_2, ZjEcsOrderConsts.ZS_ES_CO_QUEUE_TIME_CHANGE, "second");
			queBak.setCreated_date(create_date);
			this.baseDaoSupport.insert("co_queue", queBak);

			result.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
			result.setError_msg("执行成功,后台将会自动同步变更信息至总部商城");
		} catch (Exception e) {
			e.printStackTrace();
			result.setError_code("-1");
			result.setError_msg("es_co_queue定时任务插入失败;" + e.getMessage());
		}
		return result;
	}

	@Override
	public BusiDealResult notifyAuditStatusToZB(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		StateSynToZBRequest req = new StateSynToZBRequest();
		req.setNotNeedReqStrOrderId(order_id);
		req.setNotNeedReqStrStateTag(EcsOrderConsts.STATE_CHG_REVIEWED);
		req.setNotNeedReqStrStateChgReason(EcsOrderConsts.STATE_CHG_REASON_OTHEOR);
		req.setNotNeedReqStrStateChgDesc("通知总部客户回访完成");
		StateSynToZBResponse infResp = client.execute(req, StateSynToZBResponse.class);
		if (infResp.getRespCode().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000)) {
			// 更新总部状态
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ZB_STATUS },
					new String[] { EcsOrderConsts.ZB_ORDER_STATE_N04 });
		} else {
			result.setError_msg("错误编码：" + infResp.getRespCode() + ";错误信息：" + infResp.getError_msg());
		}
		result.setError_code(infResp.getRespCode());
		return result;
	}

	@Override
	public BusiDealResult orderSynToWl(String order_id) {
		BusiDealResult result = new BusiDealResult();
		OrderExtBusiRequest orderExtBusiReq = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
		String shippingCompany = null;
		List<OrderDeliveryBusiRequest> deliveryList = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderDeliveryBusiRequests();
		for (OrderDeliveryBusiRequest orderDelivery : deliveryList) {
			if (EcsOrderConsts.LOGIS_NORMAL.equals(orderDelivery.getDelivery_type())) {
				shippingCompany = orderDelivery.getShipping_company();
			}
		}
		String ship_comp = orderExtBusiReq.getQuick_ship_company_code();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		String sf_status = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SF_STATUS);
		String nd_status = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ND_STATUS);
		SDSStatusLogVO sdsStatusLog = new SDSStatusLogVO();// 记录日志
		sdsStatusLog.setSds_id(Long.valueOf(DateUtil.currentTimstamp()));
		sdsStatusLog.setOrig_order_id(order_id);
		sdsStatusLog.setCreate_time(DateUtil.currentDateTime());

		OrderDeliveryBusiRequest delivery = CommonDataFactory.getInstance().getDeliveryBusiRequest(order_id, EcsOrderConsts.LOGIS_NORMAL);
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<OrderLockBusiRequest> orderLockRequestList = tree.getOrderLockBusiRequests();
		String trace_id = orderExtBusiReq.getFlow_trace_id();
		String lock_user_id = "";
		for (OrderLockBusiRequest orderLockRequest : orderLockRequestList) {
			if (trace_id.equals(orderLockRequest.getTache_code())) {
				lock_user_id = orderLockRequest.getLock_user_id();
				break;
			}
		}
		sdsStatusLog.setCreate_user(lock_user_id);

		sdsStatusLog.setRequest_initiator("unimorder");
		if (EcsOrderConsts.LOGI_COMPANY_SFFYZQYF.equals(shippingCompany)) {// 顺风
			sdsStatusLog.setWl_comp_code(EcsOrderConsts.SDS_COMP_SF);
			sdsStatusLog.setWl_comp_name("顺丰");

			// add zhangjun 根据物流单号判断是否需要再次调用顺丰接口
			String logi_no = null;
			if (delivery != null) {
				logi_no = delivery.getLogi_no();
			}
			if (!StringUtil.isEmpty(logi_no)) {// fan hui
				// resp= new NotifyOrderInfoSFResponse();
				// resp.setError_code("");
				result.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);
				result.setError_msg("成功");
				logger.info("物流单号不为空，不再次调用顺丰接口");
				return result;
			}

			NotifyOrderInfoSFRequset sfReq = new NotifyOrderInfoSFRequset();
			sfReq.setOrderid(order_id);
			// 顺丰下单改造：月结账号
			String monthAccount = this.orderSDSModelManager.findSfMonthAccount(order_id);
			sfReq.setCustid(monthAccount);

			NotifyOrderInfoSFResponse sfResp = client.execute(sfReq, NotifyOrderInfoSFResponse.class);
			if (!EcsOrderConsts.SF_INF_SUCC_CODE.equals(sfResp.getErrorCode())) {// 顺丰接单处理失败
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
						new String[] { AttrConsts.SHIPPING_QUICK, AttrConsts.SF_STATUS, AttrConsts.SF_STATUS_DESC },
						new String[] { EcsOrderConsts.SHIPPING_QUICK_02, EcsOrderConsts.SDS_STATUS_01, sfResp.getErrorMessage() });
				result.setError_code(sfResp.getErrorCode());
				result.setError_msg("错误编码：" + sfResp.getErrorCode() + ";错误信息：" + sfResp.getErrorMessage());
				sdsStatusLog.setLight_ning_status(EcsOrderConsts.SDS_STATUS_01);
				sdsStatusLog.setStatus_name("接收失败");
				sdsStatusLog.setRe_code(sfResp.getErrorCode());
				sdsStatusLog.setRe_message(sfResp.getErrorMessage());
			} else {// 顺丰接单处理成功
				String filter_result = sfResp.getOrderInfo().get(0).getFilter_result();// 筛选码

				if (EcsOrderConsts.SF_FILTER_RESULT_2.equals(filter_result)) {// 可派送：（不筛单）正常情况下顺丰返回固定值：2-可派送
					String opid = "SF" + sfResp.getOrderInfo().get(0).getDestcode();// 组装顺丰工号
					// 记录物流单号
					OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
					OrderDeliveryBusiRequest deliverBusiReq = orderTree.getOrderDeliveryBusiRequests().get(0);
					deliverBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					deliverBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					deliverBusiReq.setLogi_no(sfResp.getOrderInfo().get(0).getMailno());
					deliverBusiReq.setOrigin_code(sfResp.getOrderInfo().get(0).getOrigincode());
					deliverBusiReq.setDest_code(sfResp.getOrderInfo().get(0).getDestcode());
					deliverBusiReq.setReceipt_no(sfResp.getOrderInfo().get(0).getReturn_tracking_no());// 记录回单号
					deliverBusiReq.store(); // 属性数据入库

					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.LOGI_NO },
							new String[] { sfResp.getOrderInfo().get(0).getMailno() });
					// 浙江没有闪电送，去掉
					/*
					 * sdsStatusLog.setCreate_user(opid);
					 * sdsStatusLog.setLight_ning_status(EcsOrderConsts.
					 * SDS_STATUS_04); sdsStatusLog.setStatus_name("下单并派工号接收");
					 * unLockOrder(order_id);
					 * 
					 * //opid="GZ000254";//测试代码
					 * 
					 * boolean isLock = ecsOrdManager.orderLockByWl(order_id,
					 * opid); if(isLock){//锁单成功：
					 * CommonDataFactory.getInstance().updateAttrFieldValue(
					 * order_id,new String[]{AttrConsts.SF_STATUS},new
					 * String[]{EcsOrderConsts.SDS_STATUS_04});
					 * sdsStatusLog.setRe_code("0000");
					 * sdsStatusLog.setRe_message("顺丰接单成功，且订单锁定成功");
					 * }else{//锁单失败，此刻的状态设置为-？。怎么处理锁单失败（可见性）？
					 * CommonDataFactory.getInstance().updateAttrFieldValue(
					 * order_id,new String[]{AttrConsts.SF_STATUS},new
					 * String[]{EcsOrderConsts.SDS_STATUS_04});
					 * sdsStatusLog.setRe_code("0001");
					 * sdsStatusLog.setRe_message("顺丰接单成功，但工号"+opid+"不存在,锁单失败！")
					 * ; }
					 */
				} else if (EcsOrderConsts.SF_FILTER_RESULT_3.equals(filter_result)) {// 不可派送
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
							new String[] { AttrConsts.ORDER_MODEL, AttrConsts.SHIPPING_QUICK, AttrConsts.SF_STATUS, AttrConsts.SF_STATUS_DESC },
							new String[] { EcsOrderConsts.ORDER_MODEL_02, EcsOrderConsts.SHIPPING_QUICK_02, EcsOrderConsts.SDS_STATUS_03,
									sfResp.getOrderInfo().get(0).getRemark() });
					result.setError_code("0001");
					result.setError_msg("顺丰拒绝接受");
					sdsStatusLog.setLight_ning_status(EcsOrderConsts.SDS_STATUS_03);
					sdsStatusLog.setStatus_name("顺丰拒绝接受");
				} else if (EcsOrderConsts.SF_FILTER_RESULT_1.equals(filter_result)) {// filter_result=1
																						// 设置订单为不可见，等待人工筛单推送
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.SF_STATUS },
							new String[] { EcsOrderConsts.SDS_STATUS_02 });
					OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
					orderExt.setVisible_status(EcsOrderConsts.VISIBLE_STATUS_1);
					orderExt.setSyn_wl_time(DateUtil.currentDateTime());
					orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					orderExt.store();

					/*
					 * sdsStatusLog.setLight_ning_status(EcsOrderConsts.
					 * SDS_STATUS_02); sdsStatusLog.setStatus_name("顺丰成功接收");
					 * sdsStatusLog.setRe_code("0000");
					 * sdsStatusLog.setRe_message("顺丰接单成功，等待人工筛单推送！");
					 */

				}

			}
		} else if (EcsOrderConsts.SDS_COMP_ND.equals(ship_comp) && (EcsOrderConsts.SDS_STATUS_01.equals(nd_status) || "".equals(nd_status))) { // 南都
			NotifyOrderInfoNDRequset ndReq = new NotifyOrderInfoNDRequset();
			ndReq.setNotNeedReqStrorderId(order_id);
			sdsStatusLog.setWl_comp_code(EcsOrderConsts.SDS_COMP_ND);
			sdsStatusLog.setWl_comp_name("南都");
			NotifyOrderInfoNDResponse ndResp = client.execute(ndReq, NotifyOrderInfoNDResponse.class);
			if (!EcsOrderConsts.ND_INF_SUCC_CODE.equals(ndResp.getErrorCode())) {
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
						new String[] { AttrConsts.ORDER_MODEL, AttrConsts.SHIPPING_QUICK, AttrConsts.ND_STATUS, AttrConsts.ND_STATUS_DESC },
						new String[] { EcsOrderConsts.ORDER_MODEL_02, EcsOrderConsts.SHIPPING_QUICK_02, EcsOrderConsts.SDS_STATUS_01,
								ndResp.getErrorMessage() });
				result.setError_code(ndResp.getErrorCode());
				result.setError_msg("错误编码：" + ndResp.getErrorCode() + ";错误信息：" + ndResp.getErrorMessage());
				sdsStatusLog.setLight_ning_status(EcsOrderConsts.SDS_STATUS_01);
				sdsStatusLog.setStatus_name("接收失败");
				sdsStatusLog.setRe_code(ndResp.getError_code());
				sdsStatusLog.setRe_message(ndResp.getError_msg());
			} else {
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ND_STATUS },
						new String[] { EcsOrderConsts.SDS_STATUS_02 });
				sdsStatusLog.setLight_ning_status(EcsOrderConsts.SDS_STATUS_02);
				sdsStatusLog.setStatus_name("成功接收");
				sdsStatusLog.setRe_code(ndResp.getError_code());
				sdsStatusLog.setRe_message(ndResp.getError_msg());
			}
		} else if (EcsOrderConsts.LOGI_COMPANY_EMS0001.equals(shippingCompany) || EcsOrderConsts.LOGI_COMPANY_EMS0002.equals(shippingCompany)
				|| EcsOrderConsts.LOGI_COMPANY_EMS0003.equals(shippingCompany)) {
			// 之前的代码
			/*
			 * EmsLogisticsInfoSyncReq logisticsInfoSyncReq = new
			 * EmsLogisticsInfoSyncReq();
			 * logisticsInfoSyncReq.setNotNeedReqStrOrderId(order_id); ZteClient
			 * zteClient =
			 * ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			 * EmsLogisticsInfoSyncResp logisticsInfoSyncResp =
			 * zteClient.execute(logisticsInfoSyncReq,
			 * EmsLogisticsInfoSyncResp.class);
			 * result.setError_code(logisticsInfoSyncResp.getError_code());
			 * result.setError_msg(logisticsInfoSyncResp.getError_msg());
			 */
			// 之前的代码 end

			// 代收货款 需要换接口 --songqi
			if (EcsOrderConsts.PAY_TYPE_HDFK.equals(orderExtBusiReq.getPay_type())) {
				LogisticsInfoSyncReq logisticsInfoSyncReq = new LogisticsInfoSyncReq();
				logisticsInfoSyncReq.setNotNeedReqStrOrderId(order_id);
				ZteClient zteClient = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				LogisticsInfoSyncResp logisticsInfoSyncResp = zteClient.execute(logisticsInfoSyncReq, LogisticsInfoSyncResp.class);
				result.setError_code(logisticsInfoSyncResp.getError_code());

				if (logisticsInfoSyncResp.getResultCode().equals("1")) {
					result.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);
					result.setError_msg("成功");
				} else {
					String errorMsg = logisticsInfoSyncResp.getErrorDesc();
					if (logisticsInfoSyncResp.getErrorCode().equals("E028")) {
						errorMsg = errorMsg + "," + logisticsInfoSyncResp.getErrorDetail().getDataError();
					}
					result.setError_code("-9999");
					result.setError_msg(errorMsg);
				}

				// result.setError_msg(logisticsInfoSyncResp.getErrorDesc());
			} else {
				// 之前的代码
				EmsLogisticsInfoSyncReq logisticsInfoSyncReq = new EmsLogisticsInfoSyncReq();
				logisticsInfoSyncReq.setNotNeedReqStrOrderId(order_id);
				ZteClient zteClient = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				EmsLogisticsInfoSyncResp logisticsInfoSyncResp = zteClient.execute(logisticsInfoSyncReq, EmsLogisticsInfoSyncResp.class);
				result.setError_code(logisticsInfoSyncResp.getError_code());
				result.setError_msg(logisticsInfoSyncResp.getError_msg());
			}
		} else {
			result.setError_code("-9999999");
			result.setError_msg("物流公司不存在");
			sdsStatusLog.setLight_ning_status("-1");
			sdsStatusLog.setStatus_name("物流公司不存在");
		}
		// 没有闪电送
		// orderSDSModelManager.insertLogByVO(sdsStatusLog);
		return result;
	}

	private void unLockOrder(String orderId) {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(orderId);// CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
		/*
		 * orderExt.setLock_user_id(ConstsCore.NULL_VAL);
		 * orderExt.setLock_user_name(ConstsCore.NULL_VAL);
		 * orderExt.setLock_status(EcsOrderConsts.UNLOCK_STATUS);
		 */

		String trace_id = orderExt.getFlow_trace_id();
		List<OrderLockBusiRequest> orderLockBusiRequestList = orderTree.getOrderLockBusiRequests();
		for (int i = 0; i < orderLockBusiRequestList.size(); i++) {
			OrderLockBusiRequest orderLockBusiRequest = orderLockBusiRequestList.get(i);
			if (trace_id.equals(orderLockBusiRequest.getTache_code())) {
				orderLockBusiRequest.setLock_user_id(ConstsCore.NULL_VAL);
				orderLockBusiRequest.setLock_user_name(ConstsCore.NULL_VAL);
				orderLockBusiRequest.setLock_status(EcsOrderConsts.UNLOCK_STATUS);
				orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				// 解锁订单释放工号池和锁单结束时间
				orderLockBusiRequest.setPool_id(ConstsCore.NULL_VAL);
				orderLockBusiRequest.setLock_end_time(ConstsCore.NULL_VAL);
				orderLockBusiRequest.store();
			}
		}

		/*
		 * orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		 * orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE); orderExt.store();
		 */
	}

	@Override
	public RouteInfoVO querySfRouteStatus(String order_id) {
		RouteServiceRequest req = new RouteServiceRequest();
		req.setTracking_number(order_id);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		RouteServiceResponse resp = client.execute(req, RouteServiceResponse.class);
		RouteInfoVO vo = new RouteInfoVO();
		vo.setOrderId(order_id);
		if (resp.getRouteResponseList() == null || resp.getRouteResponseList().isEmpty()) {// 顺丰处理失败
			// vo.setSignStatus("未知");
			// vo.setReturnStatus("未知");
		} else {// 顺丰处理成功
			for (RouteResponse routeResp : resp.getRouteResponseList()) {
				if (routeResp.getMailno().startsWith(EcsOrderConsts.RETURN_TRACKING_NO_START_WITH)) {
					if (getSfStatus(routeResp)) {
						vo.setReturnStatus(EcsOrderConsts.SIGN_STATUS_1);
					} else {
						vo.setReturnStatus(EcsOrderConsts.SIGN_STATUS_0);
					}
					vo.setReturnNo(routeResp.getMailno());
				} else if (routeResp.getMailno().startsWith(EcsOrderConsts.MAIL_NO_START_WITH)) {
					if (getSfStatus(routeResp)) {
						vo.setSignStatus(EcsOrderConsts.SIGN_STATUS_1);
					} else {
						vo.setSignStatus(EcsOrderConsts.SIGN_STATUS_0);
					}
					vo.setMailno(routeResp.getMailno());
				}
			}
			// 入库物流表
			OrderDeliveryBusiRequest saveDelivery = null;
			List<OrderDeliveryBusiRequest> deliveryList = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderDeliveryBusiRequests();
			for (OrderDeliveryBusiRequest orderDeliveryBusiRequest : deliveryList) {
				if (vo.getMailno().equals(orderDeliveryBusiRequest.getLogi_no())
						|| vo.getReturnNo().equals(orderDeliveryBusiRequest.getReceipt_no())) {
					saveDelivery = orderDeliveryBusiRequest;
					break;
				}
			}
			String dbAction = "update";
			if (saveDelivery == null) {
				saveDelivery = new OrderDeliveryBusiRequest();
				dbAction = "insert";
			}
			saveDelivery.setOrder_id(order_id);
			saveDelivery.setLogi_no(vo.getMailno());
			saveDelivery.setReceipt_no(vo.getReturnNo());
			saveDelivery.setSign_status(vo.getSignStatus());
			saveDelivery.setReceipt_status(vo.getReturnStatus());
			saveDelivery.setDb_action(dbAction);
			saveDelivery.setIs_dirty("true");
			saveDelivery.store();
		}
		return vo;
	}

	private boolean getSfStatus(RouteResponse routeResp) {
		List<Route> routes = routeResp.getRouteList();
		if (routes != null && !routes.isEmpty()) {
			for (Route route : routes) {
				if (EcsOrderConsts.SF_DEAL_STATUS_80.equals(route.getOpcode())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	// 热敏单打印专用，调用修改须谨慎
	public BusiDealResult synOrderInfoSF(String order_id, String insureValue) {
		NotifyOrderInfoSFRequset sfReq = new NotifyOrderInfoSFRequset();
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		sfReq.setOrderid(order_id);
		String monthAccount = this.orderSDSModelManager.findSfMonthAccount(order_id);
		if (StringUtils.isEmpty(monthAccount)) {// 没有月结账号，不下单
			result.setError_code("0003");
			result.setError_msg("没有对应月结账号");
			return result;
		}
		sfReq.setPay_method("1");// 寄付
		sfReq.setCustid(monthAccount);
		// sfReq.setNeed_return_tracking_no(EcsOrderConsts.IS_DEFAULT_VALUE);

		if (StringUtils.isNotEmpty(insureValue)) {// 保价金额(声明价值)不为空，则向顺丰传送保价增值服务
			if (Double.parseDouble(insureValue) > 0) {// 保价金额(声明价值)大于0才传保价。页面已经保证insureValue必然为非负数字
				List<OrderOptionAddedService> list = new ArrayList<OrderOptionAddedService>();// 保价
				OrderOptionAddedService ooas = new OrderOptionAddedService();
				ooas.setName("INSURE");// 保价--增值服务类型
				ooas.setValue(insureValue);// 声明价值--页面填写
				list.add(ooas);
				sfReq.setAddedService(list);
			}
		}

		NotifyOrderInfoSFResponse sfResp = client.execute(sfReq, NotifyOrderInfoSFResponse.class);
		if (!EcsOrderConsts.SF_INF_SUCC_CODE.equals(sfResp.getErrorCode())) {// 顺丰接单处理失败
			result.setError_code(sfResp.getErrorCode());
			result.setError_msg("错误编码：" + sfResp.getErrorCode() + ";错误信息：" + sfResp.getErrorMessage());
		} else {// 顺丰接单处理成功
			String filter_result = sfResp.getOrderInfo().get(0).getFilter_result();// 筛选码

			if (EcsOrderConsts.SF_FILTER_RESULT_2.equals(filter_result)) {// 可派送：（不筛单）正常情况下顺丰返回固定值：2-可派送

				// 记录物流单号
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				List<OrderDeliveryBusiRequest> orderDeliveryBusiRequest = orderTree.getOrderDeliveryBusiRequests();
				OrderDeliveryBusiRequest deliverBusiReq = null;
				for (OrderDeliveryBusiRequest deli : orderDeliveryBusiRequest) {
					if (EcsOrderConsts.LOGIS_NORMAL.equals(deli.getDelivery_type())) {
						deliverBusiReq = deli;// 取正常发货记录，前面业务逻辑保证有此记录
						break;
					}
				}
				deliverBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				deliverBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				deliverBusiReq.setLogi_no(sfResp.getOrderInfo().get(0).getMailno());
				deliverBusiReq.setReceipt_no(sfResp.getOrderInfo().get(0).getReturn_tracking_no());
				deliverBusiReq.setSign_status(EcsOrderConsts.SIGN_STATUS_0);// 运单设为未签收
				deliverBusiReq.setReceipt_status(EcsOrderConsts.SIGN_STATUS_0);// 回单设为未签收
				deliverBusiReq.store(); // 属性数据入库

				result.setResponse(sfResp);
				result.setError_code("0000");
				result.setError_msg("下单成功");
			} else if (EcsOrderConsts.SF_FILTER_RESULT_3.equals(filter_result)) {// 不可派送

				result.setError_code("0002");
				result.setError_msg("顺丰拒绝接受");
			} else if (EcsOrderConsts.SF_FILTER_RESULT_1.equals(filter_result)) {// filter_result=1
				result.setError_code("0001");
				result.setError_msg("等待人工筛选");
			}
		}
		return result;
	}

	@Override
	public NotifyOrderInfoSFResponse querySfOrder(String order_id) {
		OrderSearchServiceRequest request = new OrderSearchServiceRequest();
		request.setOrderid(order_id);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		NotifyOrderInfoSFResponse response = client.execute(request, NotifyOrderInfoSFResponse.class);
		return response;
	}

	@Override
	/**
	 * 客户资料校验
	 */
	public BusiDealResult custInfoCheck(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		CustomerInfoCheckReq req = new CustomerInfoCheckReq();
		req.setNotNeedReqStrOrderId(order_id);
		CustomerInfoResponse rsp = client.execute(req, CustomerInfoResponse.class);
		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
			// 此前无此客户信息 ,认为校验通过
			if (StringUtils.equals(rsp.getCode(), EcsOrderConsts.AOP_CUST_CHECK_0001)) {
				result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
				result.setError_msg("客户校验成功");
			} else {
				result.setError_msg(rsp.getDetail());
				result.setError_code(rsp.getCode());
			}
		} else {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CUST_ID },
					new String[] { rsp.getCustInfo().get(0).getCustId() });
            CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.IS_OLD },new String[] {"1"});
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("客户校验成功");
		}
		result.setResponse(rsp);
		return result;
	}

	@Override
	/**
	 * ESS客户资料校验
	 */
	public BusiDealResult custInfoCheckFromESS(String order_id, String opeSysType) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		CustomerInfoCheckReq req = new CustomerInfoCheckReq();
		req.setNotNeedReqStrOrderId(order_id);
		req.setOpeSysType(opeSysType);
		CustomerInfoResponse rsp = client.execute(req, CustomerInfoResponse.class);
		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
			// 此前无此客户信息 ,认为校验通过
			if (StringUtils.equals(rsp.getCode(), EcsOrderConsts.AOP_CUST_CHECK_0001)) {
				result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
				result.setError_msg("ESS客户校验成功");
			} else {
				result.setError_msg(rsp.getDetail());
				result.setError_code(rsp.getCode());
			}
		} else {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CUST_ID },
					new String[] { rsp.getCustInfo().get(0).getCustId() });
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("ESS客户校验成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * CB客户资料校验
	 */
	public BusiDealResult custInfoCheckFromCB(String order_id, String opeSysType) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		CustomerInfoCheckReq req = new CustomerInfoCheckReq();
		req.setNotNeedReqStrOrderId(order_id);
		req.setOpeSysType(opeSysType);
		CustomerInfoResponse rsp = client.execute(req, CustomerInfoResponse.class);
		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
			// 此前无此客户信息 ,认为校验通过
			if (StringUtils.equals(rsp.getCode(), EcsOrderConsts.AOP_CUST_CHECK_0001)) {
				result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
				result.setError_msg("CB客户校验成功");
			} else {
				result.setError_msg(rsp.getDetail());
				result.setError_code(rsp.getCode());
			}
		} else {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CUST_ID },
					new String[] { rsp.getCustInfo().get(0).getCustId() });
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("CB客户校验成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * BSS客户资料校验
	 */
	public BusiDealResult custInfoCheckBSS(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		BSSCustomerInfoCheckReq req = new BSSCustomerInfoCheckReq();
		req.setNotNeedReqStrOrderId(order_id);
		BSSCustomerInfoResponse rsp = client.execute(req, BSSCustomerInfoResponse.class);
		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.BSS_ANSWER_CODE_0000)) {
			// 此前无此客户信息 ,认为校验通过
			if (StringUtils.equals(rsp.getRespCode(), EcsOrderConsts.BSS_CUST_CHECK_0001)) {
				result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
				result.setError_msg("BSS客户校验成功");
			} else {
				result.setError_msg(rsp.getError_msg());
				result.setError_code(rsp.getError_code());
			}
		} else {
			// 只取第一个客户信息
			ExistedCustomerVo rsvo = rsp.getExistedCustomer().get(0);
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CUST_ID },
					new String[] { rsvo.getCustomerID() });
			// 是否黑名单
			String blackListFlag = rsvo.getBlackListFlag();
			if (StringUtils.equals(blackListFlag, EcsOrderConsts.IS_DEFAULT_VALUE)) {
				result.setError_msg("黑名单客户");
				result.setError_code("9999");
			} else {
				result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
				result.setError_msg("BSS客户校验成功");
			}
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * 返档接口调用
	 * 
	 * @author Wcl
	 */
	public BusiDealResult returnFile(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ReturnFileReq req = new ReturnFileReq();
		ReturnFileResp resp = new ReturnFileResp();

		req.setOut_order_id(order_id);

		resp = client.execute(req, ReturnFileResp.class);

		if (resp.getCode().equals(EcsOrderConsts.BSS_SUCCESS_00000)) {
			String bssid = resp.getRespJson().getBms_accept_id();
			IDaoSupport baseSupport = SpringContextHolder.getBean("baseDaoSupport");

			Map fields = new HashMap();
			fields.put("bssOrderId", bssid);

			Map where = new HashMap();
			where.put("order_id", order_id);

			baseSupport.update("es_order_items_ext", fields, where);

			result.setError_msg("返档成功");
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
		} else {
			result.setError_msg(resp.getMsg());
			result.setError_code("-9999");
		}

		return result;
	}

	/**
	 * 发展人信息查询
	 * 
	 * @author XMJ
	 * @param order_id
	 * @return BusiDealResult
	 */
	public BusiDealResult developPersonCheck(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		DevelopPersonQueryReq req = new DevelopPersonQueryReq();
		req.setNotNeedReqStrOrderId(order_id);
		DevelopPersonResponse rsp = client.execute(req, DevelopPersonResponse.class);// 调用接口
		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
			result.setError_msg(rsp.getDetail());
			result.setError_code(rsp.getCode());
		} else {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					new String[] { AttrConsts.DEVELOPMENT_NAME, AttrConsts.ORDER_CHA_NAME, AttrConsts.ORDER_CHA_CODE, AttrConsts.ORDER_CHA_PHONENUM },
					new String[] { rsp.getDeveloperInfo().get(0).getDeveloperName(), rsp.getDeveloperInfo().get(0).getChnlName(),
							rsp.getDeveloperInfo().get(0).getChannelId(), rsp.getDeveloperInfo().get(0).getDeveloperNumber() });
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * ess工号信息查询
	 * 
	 * @author XMJ
	 * @param order_id
	 * @return
	 */
	public BusiDealResult essOperatorInfoQuery(String essID, String orderGonghao, String orderId) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		EmployeesInfoCheckRequest req = new EmployeesInfoCheckRequest();
		req.setOperatorId(essID);
		// req.setCity(cityCode);
		req.setNotNeedReqStrOrderId(orderId);
		EmployeesInfoResponse rsp = client.execute(req, EmployeesInfoResponse.class);// 调用接口
		if (!rsp.getError_code().equals(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
			result.setError_msg("查询CB工号出错，接口错误描述：" + rsp.getDetail());
			result.setError_code(rsp.getCode());
		} else {
			EmpOperInfoVo empinfo = new EmpOperInfoVo();
			empinfo.setProvince(rsp.getProvince());
			empinfo.setDistrict(rsp.getDistrict());
			empinfo.setDep_id(rsp.getDepartId());
			empinfo.setDep_name(rsp.getDepartName());
			empinfo.setChannel_id(rsp.getChannelId());
			empinfo.setChannel_type(rsp.getChannelType());
			empinfo.setChannel_name(rsp.getChannelName());
			empinfo.setSource_from(ManagerUtils.getSourceFrom());
			empinfo.setStaffNumber(rsp.getStaffInfo().getStaffNumber());
			empinfo.setStaffSex(rsp.getStaffInfo().getStaffSex() + "");
			empinfo.setStaffPsptType(rsp.getStaffInfo().getStaffPsptType());
			empinfo.setStaffName(rsp.getStaffInfo().getStaffName());
			empinfo.setStaffPsptId(rsp.getStaffInfo().getStaffPsptId());
			String where = " ESS_EMP_ID='" + rsp.getOperatorId() + "'";
			// 把接口数据更新到数据库中
			IDaoSupport<EmpOperInfoVo> support = SpringContextHolder.getBean("baseDaoSupport");
			support.update("es_operator_rel_ext", empinfo, where);

			empinfo.setCity(rsp.getCity());
			empinfo.setEss_emp_id(rsp.getOperatorId());
			empinfo.setOrder_gonghao(orderGonghao);

			// 检查一下接口返回的数据是否完整
			if (EssOperatorInfoUtil.checkEmpOperInfoData(empinfo)) {
				result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
				result.setError_msg("成功");

				// 把接口新数据更新到缓存中
				INetCache cache = CacheFactory.getCacheByType(EssOperatorInfoUtil.CORE_CACHE_TYPE);
				cache.set(EssOperatorInfoUtil.NAMESPACE_OPERATOR_REL_TB, EssOperatorInfoUtil.key + orderGonghao + empinfo.getCity(), empinfo);
				cache.set(EssOperatorInfoUtil.NAMESPACE_OPERATOR_REL_TB, EssOperatorInfoUtil.key + essID, empinfo);
			} else {
				result.setError_code("-9999");
				result.setError_msg("CB(ESS)工号的开户必备数据(工号、渠道id、渠道类型)缺失，请联系运维。cb工号：" + empinfo.getEss_emp_id());
			}
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * 订单返销
	 */
	@Override
	public BusiDealResult orderReverseSales(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderReverseSalesReq req = new OrderReverseSalesReq();
		req.setNotNeedReqStrOrderId(order_id);

		OrderReverseSalesResp rsp = client.execute(req, OrderReverseSalesResp.class);// 调用接口

		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
			result.setError_msg(rsp.getDetail());
			result.setError_code(rsp.getCode());
		} else {
			// 标记ess返销、bss返销
			OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExt.setBss_cancel_status(EcsOrderConsts.BSS_CANCEL_STATUS_1);
			orderExt.setEss_cancel_status(EcsOrderConsts.ESS_CANCEL_STATUS_1);
			orderExt.setBss_reset_time("sysdate");
			orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExt.store();

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * 2-3G转4G校验
	 * 
	 * @author XMJ
	 * @param order_id
	 * @return
	 */
	public BusiDealResult preCheck23to4(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		Check23to4Request req = new Check23to4Request();
		req.setNotNeedReqStrOrderId(order_id);
		Check23to4Resp rsp = client.execute(req, Check23to4Resp.class);// 调用接口
		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
			result.setError_msg(rsp.getDetail());
			result.setError_code(rsp.getCode());
		} else {
			if (rsp.getResponseInfo() != null && rsp.getResponseInfo().size() > 0) {// 错误列表存在,就是校验失败
				StringBuffer detail = new StringBuffer();
				for (ErrorListRspVo errvo : rsp.getResponseInfo()) {
					detail.append(errvo.getRespCode() + ":" + errvo.getRespDesc() + ";");
				}
				result.setError_msg(detail.toString());
				result.setError_code("9999");
			} else {
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
						new String[] { AttrConsts.OLD_PRODUCT_FEE, AttrConsts.OLD_PRODUCT_NAME, AttrConsts.OLD_PRODUCT_ID, AttrConsts.OLD_PLAN_ID,
								AttrConsts.OLD_ACTIVITY_TYPE, AttrConsts.OLD_PLAN_START_TIME, AttrConsts.OLD_PLAN_END_TIME },
						new String[] { rsp.getActivityInfo().get(0).getProductFee(), rsp.getActivityInfo().get(0).getProductName(),
								rsp.getActivityInfo().get(0).getProductId(), rsp.getActivityInfo().get(0).getActivityId(),
								rsp.getActivityInfo().get(0).getActivityType(), rsp.getActivityInfo().get(0).getStartTime(),
								rsp.getActivityInfo().get(0).getEndTime() });

				result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
				result.setError_msg("成功");
			}
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * 2-3G转4G撤单
	 * 
	 * @author XMJ
	 * @param order_id
	 * @return
	 */
	public BusiDealResult cannel23to4(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		CannelOrder23to4Request req = new CannelOrder23to4Request();
		req.setNotNeedReqStrOrderId(order_id);
		CannelOrder23to4Resp rsp = client.execute(req, CannelOrder23to4Resp.class);// 调用接口
		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
			result.setError_msg(rsp.getDetail());
			result.setError_code(rsp.getCode());
		} else {
			if (StringUtils.equals(rsp.getResultCode(), EcsOrderConsts.AOP_RESULT_CODE_SUCCESS)) {
				result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
				result.setError_msg("成功");
			} else {
				result.setError_msg(rsp.getResultMark());
				result.setError_code(rsp.getResultCode());
			}
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * 套餐变更,撤单
	 * 
	 * @author XMJ
	 * @param order_id
	 * @return
	 */
	public BusiDealResult prodChangeCancel(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ProdChangeCannelRequest req = new ProdChangeCannelRequest();
		req.setNotNeedReqStrOrderId(order_id);
		ProdChangeCannelResp rsp = client.execute(req, ProdChangeCannelResp.class);// 调用接口
		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
			result.setError_msg(rsp.getDetail());
			result.setError_code(rsp.getCode());
		} else {
			if (StringUtils.equals(rsp.getResultCode(), EcsOrderConsts.AOP_RESULT_CODE_SUCCESS)) {
				result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
				result.setError_msg("成功");
			} else {
				result.setError_msg(rsp.getResultMark());
				result.setError_code(rsp.getResultCode());
			}
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * 3G老用户业务校验
	 */
	public BusiDealResult oldUserCheck3G(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OldUserCheck3GReq req = new OldUserCheck3GReq();
		req.setNotNeedReqStrOrderId(order_id);
		OldUserCheck3GResp resp = client.execute(req, OldUserCheck3GResp.class);// 调用接口
		if (!StringUtils.equals(resp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
			result.setError_msg(resp.getDetail());
			result.setError_code(resp.getCode());
		} else {// 成功200
			if (StringUtils.equals(resp.getCheckCode(), EcsOrderConsts.AOP_SUCCESS_0000)) {
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
						new String[] { AttrConsts.CERT_CARD_NAME, AttrConsts.CERT_CARD_NUM, AttrConsts.CERT_ADDRESS, AttrConsts.CERTI_TYPE,
								AttrConsts.BIP_CODE },
						new String[] { resp.getCustomerName(), resp.getCertNum(), resp.getCertAdress(),
								CommonDataFactory.getInstance().getDictCodeValue("aop_cert_type", resp.getCertType()), // TODO
																														// 把其他系统字典转成我们的
								CommonDataFactory.getInstance().getDictCodeValue("aop_bip_code", resp.getChangeType()) });
			} else {
				result.setError_code(resp.getCheckCode());
				result.setError_msg(CommonDataFactory.getInstance().getDcPublicDataByPkey("aop_old_check_3g_resCode", resp.getCheckCode()));
			}
		}
		return result;
	}

	/**
	 * 用户资料校验三户返回
	 * 
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult userInfoCheck3Back(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		UserInfoCheck3BackReq req = new UserInfoCheck3BackReq();
		req.setNotNeedReqStrOrderId(order_id);
		UserInfoCheck3BackResp resp = client.execute(req, UserInfoCheck3BackResp.class);
		if (StringUtils.equals(resp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
			if (resp.getUserInfo() != null) {
				String userState = resp.getUserInfo().getUserState();

				// 如果来源是CBSS(2)则号码是4g，来源是Ess(1)则可能是23g
				if (EcsOrderConsts.AOP_OPE_SYS_TYPE_2.equals(resp.getSysType())) {
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.OLD_NET_TYPE },
							new String[] { EcsOrderConsts.NET_TYPE_4G });
				} else if (EcsOrderConsts.AOP_OPE_SYS_TYPE_1.equals(resp.getSysType())) {
					// 订购产品网别
					String net_type = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
					String spBusiType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SPECIAL_BUSI_TYPE);
					String orderFrom = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
					// 数据来源ess+非特殊业务类型+订购产品是4G，标记为23转4--总商
					if (StringUtils.equals(net_type, EcsOrderConsts.NET_TYPE_4G)
							&& StringUtils.equals(spBusiType, EcsOrderConsts.SPECIAL_BUSI_TYPE_00)
							&& StringUtils.equals(orderFrom, EcsOrderConsts.ORDER_FROM_10003)) {
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.SPECIAL_BUSI_TYPE },
								new String[] { EcsOrderConsts.SPECIAL_BUSI_TYPE_04 });
					}

					// 是否3G：1是；2 否
					if (EcsOrderConsts.AOP_IS_YES.equals(resp.getUserInfo().getFlag3G())) {
						// 是3G号码
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.OLD_NET_TYPE },
								new String[] { EcsOrderConsts.NET_TYPE_3G });
					} else {
						// 是2G号码
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.OLD_NET_TYPE },
								new String[] { EcsOrderConsts.NET_TYPE_2G });
					}
				}

				// 01 开通2 正常 这两种状态才是可以办理业务的状态，
				// if(StringUtils.equals(userState,
				// EcsOrderConsts.AOP_USER_STATE_01) ||
				// StringUtils.equals(userState,
				// EcsOrderConsts.AOP_USER_STATE_2)){
				// 原产品信息
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
						new String[] { AttrConsts.SERVICE_CLASS_CODE, AttrConsts.OLD_PRODUCT_ID, AttrConsts.OLD_PRODUCT_NAME }, new String[] {
								resp.getUserInfo().getServiceClassCode(), resp.getUserInfo().getProductId(), resp.getUserInfo().getProductName() });

				// 活动信息
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, // 默认非续约
						new String[] { AttrConsts.EXTENSION_TAG }, new String[] { EcsOrderConsts.NO_DEFAULT_VALUE });
				if (resp.getUserInfo().getActivityInfo() != null && resp.getUserInfo().getActivityInfo().size() > 0) {
					int a = 0, b = 0;
					String curDay = DateUtil.getCurDay();
					for (ActivityInfoRspVo activity : resp.getUserInfo().getActivityInfo()) {
						// 生效或者失效时间为空则退出
						if (StringUtil.isEmpty(activity.getActivityActiveTime()) || StringUtil.isEmpty(activity.getActivityInactiveTime())) {
							continue;
						}

						a = DateUtil.compareDate(curDay, activity.getActivityActiveTime());
						b = DateUtil.compareDate(curDay, activity.getActivityInactiveTime());

						if ((a == 1 || a == 0) && (b == -1 || b == 0)) { // 找到当前时间有效的活动
							CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.OLD_PLAN_ID,
									AttrConsts.OLD_PLAN_NAME, AttrConsts.OLD_PLAN_START_TIME,
									AttrConsts.OLD_PLAN_END_TIME/**
																 * , AttrConsts.
																 * TERMINAL_NUM
																 **/
							}, new String[] { resp.getUserInfo().getActivityInfo().get(0).getActivityId(),
									resp.getUserInfo().getActivityInfo().get(0).getActivityName(),
									resp.getUserInfo().getActivityInfo().get(0).getActivityActiveTime(), resp.getUserInfo().getActivityInfo().get(0)
											.getActivityInactiveTime()/**
																		 * ,
																		 * resp.
																		 * getUserInfo
																		 * ().
																		 * getActivityInfo
																		 * ().
																		 * get(0
																		 * ).
																		 * getImei
																		 * ()
																		 **/
							});
							CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.EXTENSION_TAG },
									new String[] { EcsOrderConsts.IS_DEFAULT_VALUE });
							break;
						}
					}
				}

				// 账户信息
				// String ser_type =
				// CommonDataFactory.getInstance().getDictCodeValue("aop_ser_type",
				// resp.getAcctInfo().getPrepayTag());
				// if(resp.getAcctInfo()!=null){
				// CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
				// new String[]{AttrConsts.SER_TYPE},
				// new String[]{ser_type});
				// }

				// ICCID
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ICCID }, new String[] { resp.getSimCard() });

				// 客户信息
				if (resp.getCustInfo() != null) {
					String certName = resp.getCustInfo().getCustName();
					String certNum = resp.getCustInfo().getCertCode();
					String addr = resp.getCustInfo().getCertAddr();
					String certType = CommonDataFactory.getInstance().getDictCodeValue("aop_cert_type", resp.getCustInfo().getCertTypeCode());
					String custType = CommonDataFactory.getInstance().getDictCodeValue("aop_cust_type", resp.getCustInfo().getCustType());

					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CUST_ID },
							new String[] { resp.getCustInfo().getCustId() });

					if (!StringUtils.isEmpty(certName)) {
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CERT_CARD_NAME },
								new String[] { certName });
					}
					if (!StringUtils.isEmpty(certNum)) {
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CERT_CARD_NUM },
								new String[] { certNum });
					}
					if (!StringUtils.isEmpty(certType)) {
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CERTI_TYPE },
								new String[] { certType });
					}
					// if(!StringUtils.isEmpty(custType)){
					// CommonDataFactory.getInstance().updateAttrFieldValue(order_id,new
					// String[]{AttrConsts.CUSTOMER_TYPE},new
					// String[]{custType});
					// }
					if (!StringUtils.isEmpty(addr)) {
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CERT_ADDRESS },
								new String[] { addr });
					}
				}

				// 判断是否需要变更套餐
				String out_plan_id_ess = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_PLAN_ID_ESS);
				if (StringUtil.equals(out_plan_id_ess, resp.getUserInfo().getProductId())) {
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.IS_CHANGE },
							new String[] { EcsOrderConsts.AOP_IS_NO });
				} else {
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.IS_CHANGE },
							new String[] { EcsOrderConsts.AOP_IS_YES });
				}

				result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
				result.setError_msg("成功");
				// }else{
				// result.setError_code("错误编码："+userState);
				// result.setError_msg("错误信息：
				// "+CommonDataFactory.getInstance().getDcPublicDataByPkey("aop_user_check_3back_resCode",
				// userState));
				// }
			} else {
				result.setError_code("错误编码：9999 ");
				result.setError_msg("错误信息：3户接口未获取到用户信息！ ");
			}
		} else {
			result.setError_code(resp.getCode());
			result.setError_msg(resp.getDetail());
		}
		return result;
	}

	/**
	 * 用户资料校验三户返回
	 * 
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult userInfoCheck3BackZJ(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		UserInfoCheck3BackReqZJ req = new UserInfoCheck3BackReqZJ();
		req.setNotNeedReqStrOrderId(order_id);
		UserInfoCheck3BackResp resp = client.execute(req, UserInfoCheck3BackResp.class);
		if (StringUtils.equals(resp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")
				&&StringUtils.isEmpty(resp.getCode())) {
			if (resp.getUserInfo() != null) {
				String userState = resp.getUserInfo().getUserState();

				// 如果来源是CBSS(2)则号码是4g，来源是Ess(1)则可能是23g
				if (EcsOrderConsts.AOP_OPE_SYS_TYPE_2.equals(resp.getSysType())) {
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.OLD_NET_TYPE },
							new String[] { EcsOrderConsts.NET_TYPE_4G });
				} else if (EcsOrderConsts.AOP_OPE_SYS_TYPE_1.equals(resp.getSysType())) {
					// 订购产品网别
					String net_type = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
					String spBusiType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SPECIAL_BUSI_TYPE);
					String orderFrom = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
					// 数据来源ess+非特殊业务类型+订购产品是4G，标记为23转4--总商
					if (StringUtils.equals(net_type, EcsOrderConsts.NET_TYPE_4G)
							&& StringUtils.equals(spBusiType, EcsOrderConsts.SPECIAL_BUSI_TYPE_00)
							&& StringUtils.equals(orderFrom, EcsOrderConsts.ORDER_FROM_10003)) {
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.SPECIAL_BUSI_TYPE },
								new String[] { EcsOrderConsts.SPECIAL_BUSI_TYPE_04 });
					}

					// 是否3G：1是；2 否
					if (EcsOrderConsts.AOP_IS_YES.equals(resp.getUserInfo().getFlag3G())) {
						// 是3G号码
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.OLD_NET_TYPE },
								new String[] { EcsOrderConsts.NET_TYPE_3G });
					} else {
						// 是2G号码
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.OLD_NET_TYPE },
								new String[] { EcsOrderConsts.NET_TYPE_2G });
					}
				}

				// 01 开通2 正常 这两种状态才是可以办理业务的状态，
				// if(StringUtils.equals(userState,
				// EcsOrderConsts.AOP_USER_STATE_01) ||
				// StringUtils.equals(userState,
				// EcsOrderConsts.AOP_USER_STATE_2)){
				// 原产品信息
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
						new String[] { AttrConsts.SERVICE_CLASS_CODE, AttrConsts.OLD_PRODUCT_ID, AttrConsts.OLD_PRODUCT_NAME }, new String[] {
								resp.getUserInfo().getServiceClassCode(), resp.getUserInfo().getProductId(), resp.getUserInfo().getProductName() });

				// 活动信息
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, // 默认非续约
						new String[] { AttrConsts.EXTENSION_TAG }, new String[] { EcsOrderConsts.NO_DEFAULT_VALUE });
				if (resp.getUserInfo().getActivityInfo() != null && resp.getUserInfo().getActivityInfo().size() > 0) {
					int a = 0, b = 0;
					String curDay = DateUtil.getCurDay();
					for (ActivityInfoRspVo activity : resp.getUserInfo().getActivityInfo()) {
						// 生效或者失效时间为空则退出
						if (StringUtil.isEmpty(activity.getActivityActiveTime()) || StringUtil.isEmpty(activity.getActivityInactiveTime())) {
							continue;
						}

						a = DateUtil.compareDate(curDay, activity.getActivityActiveTime());
						b = DateUtil.compareDate(curDay, activity.getActivityInactiveTime());

						if ((a == 1 || a == 0) && (b == -1 || b == 0)) { // 找到当前时间有效的活动
							CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.OLD_PLAN_ID,
									AttrConsts.OLD_PLAN_NAME, AttrConsts.OLD_PLAN_START_TIME,
									AttrConsts.OLD_PLAN_END_TIME/**
																 * , AttrConsts.
																 * TERMINAL_NUM
																 **/
							}, new String[] { resp.getUserInfo().getActivityInfo().get(0).getActivityId(),
									resp.getUserInfo().getActivityInfo().get(0).getActivityName(),
									resp.getUserInfo().getActivityInfo().get(0).getActivityActiveTime(), resp.getUserInfo().getActivityInfo().get(0)
											.getActivityInactiveTime()/**
																		 * ,
																		 * resp.
																		 * getUserInfo
																		 * ().
																		 * getActivityInfo
																		 * ().
																		 * get(0
																		 * ).
																		 * getImei
																		 * ()
																		 **/
							});
							CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.EXTENSION_TAG },
									new String[] { EcsOrderConsts.IS_DEFAULT_VALUE });
							break;
						}
					}
				}

				// 账户信息
				// String ser_type =
				// CommonDataFactory.getInstance().getDictCodeValue("aop_ser_type",
				// resp.getAcctInfo().getPrepayTag());
				// if(resp.getAcctInfo()!=null){
				// CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
				// new String[]{AttrConsts.SER_TYPE},
				// new String[]{ser_type});
				// }

				// ICCID
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ICCID }, new String[] { resp.getSimCard() });

				// 客户信息
				if (resp.getCustInfo() != null) {
					String certName = resp.getCustInfo().getCustName();
					String certNum = resp.getCustInfo().getCertCode();
					String addr = resp.getCustInfo().getCertAddr();
					String certType = CommonDataFactory.getInstance().getDictCodeValue("aop_cert_type", resp.getCustInfo().getCertTypeCode());
					String custType = CommonDataFactory.getInstance().getDictCodeValue("aop_cust_type", resp.getCustInfo().getCustType());

					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CUST_ID },
							new String[] { resp.getCustInfo().getCustId() });

					if (!StringUtils.isEmpty(certName)) {
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CERT_CARD_NAME },
								new String[] { certName });
					}
					if (!StringUtils.isEmpty(certNum)) {
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CERT_CARD_NUM },
								new String[] { certNum });
					}
					if (!StringUtils.isEmpty(certType)) {
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CERTI_TYPE },
								new String[] { certType });
					}
					// if(!StringUtils.isEmpty(custType)){
					// CommonDataFactory.getInstance().updateAttrFieldValue(order_id,new
					// String[]{AttrConsts.CUSTOMER_TYPE},new
					// String[]{custType});
					// }
					if (!StringUtils.isEmpty(addr)) {
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CERT_ADDRESS },
								new String[] { addr });
					}
				}

				// 判断是否需要变更套餐
				String out_plan_id_ess = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_PLAN_ID_ESS);
				if (StringUtil.equals(out_plan_id_ess, resp.getUserInfo().getProductId())) {
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.IS_CHANGE },
							new String[] { EcsOrderConsts.AOP_IS_NO });
				} else {
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.IS_CHANGE },
							new String[] { EcsOrderConsts.AOP_IS_YES });
				}

				result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
				result.setError_msg("成功");
				// }else{
				// result.setError_code("错误编码："+userState);
				// result.setError_msg("错误信息：
				// "+CommonDataFactory.getInstance().getDcPublicDataByPkey("aop_user_check_3back_resCode",
				// userState));
				// }
				
				if (null != resp.getUserInfo().getElementInfo()) {
					StringBuffer ids = new StringBuffer();
					ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
					String idType = cacheUtil.getConfigInfo("CB_FLOW_IDTYPE");
					for (ElementInfoRspVo element : resp.getUserInfo().getElementInfo()) {
						if (idType.contains(element.getIdType())) {
							ids.append(element.getId() + ",");
						}
					}
					if (ids.length() > 0) {
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "element_ids" },
								new String[] { ids.toString() });
					}
				}
			} else {
				result.setError_code("错误编码：9999 ");
				result.setError_msg("错误信息：3户接口未获取到用户信息！ ");
			}
		} else {
			result.setError_code(resp.getCode());
			result.setError_msg(resp.getDetail());
		}
		return result;
	}

	/**
	 * [AOP]订单查询
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult orderQuery(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderQueryReq req = new OrderQueryReq();
		req.setNotNeedReqStrOrderId(order_id);

		OrderQueryRespone rsp = client.execute(req, OrderQueryRespone.class);// 调用接口
		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
			result.setError_msg(rsp.getDetail());
			result.setError_code(rsp.getCode());
		} else {
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * BSS订单返销
	 */
	@Override
	public BusiDealResult orderReverseSalesBSS(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		BSSOrderReverseSalesReq req = new BSSOrderReverseSalesReq();
		req.setNotNeedReqStrOrderId(order_id);

		BSSOrderReverseSalesResp rsp = client.execute(req, BSSOrderReverseSalesResp.class);// 调用接口

		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.BSS_ANSWER_CODE_0000)) {
			result.setError_msg(rsp.getDetail());
			result.setError_code(rsp.getCode());
		} else {
			// 标记ess返销、bss返销
			OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExt.setBss_cancel_status(EcsOrderConsts.BSS_CANCEL_STATUS_1);
			orderExt.setEss_cancel_status(EcsOrderConsts.ESS_CANCEL_STATUS_1);
			orderExt.setBss_reset_time("sysdate");
			orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExt.store();

			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	/**
	 * BSS使用人数量校验接口
	 */
	@Override
	public BusiDealResult userCountCheckFromBSS(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		UserCountCheckReq req = new UserCountCheckReq();
		req.setNotNeedReqStrOrderId(order_id);

		UserCountCheckRsp rsp = client.execute(req, UserCountCheckRsp.class);// 调用接口

		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.BSS_ANSWER_CODE_0000)) {
			// 此前无此客户信息 ,认为校验通过
			if (StringUtils.equals(rsp.getRespCode(), EcsOrderConsts.BSS_CUST_CHECK_0001)) {
				result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
				result.setError_msg("成功");
			} else {
				result.setError_msg(rsp.getError_msg());
				result.setError_code(rsp.getError_code());
			}
		} else {
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	@Override
	public BusiDealResult StatusNoticeZB(String order_id, StateUtil vo) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		StateSynToZBRequest req = new StateSynToZBRequest();
		req.setNotNeedReqStrOrderId(order_id);
		req.setNotNeedReqStrStateTag(vo.getNotNeedReqStrStateTag()); // 状态标记
		req.setNotNeedReqStrStateChgReason(vo.getNotNeedReqStrStateChgReason()); // 变更原因
		req.setNotNeedReqStrStateChgDesc(vo.getNotNeedReqStrStateChgDesc()); // 变更描述

		StateSynToZBResponse rsp = client.execute(req, StateSynToZBResponse.class);// 调用接口
		if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(rsp.getRespCode())) {
			result.setError_msg("错误编码：" + rsp.getError_code() + ";错误信息：" + rsp.getError_msg());
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
		} else {
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	// 获取EMS物流单号
	@Override
	public BusiDealResult getEmsLogisticsNumber(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient zteClient = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderExtBusiRequest orderExtBusiReq = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
		// 代收货款 需要换接口 --songqi
		if (EcsOrderConsts.PAY_TYPE_HDFK.equals(orderExtBusiReq.getPay_type())) {
			LogisticsNumberGetReq logisticsNumberGetReq = new LogisticsNumberGetReq();
			logisticsNumberGetReq.setNotNeedReqStrOrderId(order_id);
			LogisticsNumberGetResp logisticsNumberGetResp = zteClient.execute(logisticsNumberGetReq, LogisticsNumberGetResp.class);
			if ("E000".equals(logisticsNumberGetResp.getErrorCode()) && "1".equals(logisticsNumberGetResp.getResultCode())) {
				// 保存EMS物流单号 DSDF10628398X
				String logiNum = logisticsNumberGetResp.getAssignIds().getAssignId().getBillno();
				OrderDeliveryBusiRequest orderDeliveryBusiRequest = CommonDataFactory.getInstance().getDeliveryBusiRequest(order_id,
						EcsOrderConsts.LOGIS_NORMAL);
				orderDeliveryBusiRequest.setLogi_no(logiNum);
				orderDeliveryBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderDeliveryBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderDeliveryBusiRequest.store();

				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.LOGI_NO }, new String[] { logiNum });

				result.setError_code(ConstsCore.ERROR_SUCC);
				result.setError_msg(logisticsNumberGetResp.getError_msg());
			} else {
				result.setError_code(ConstsCore.ERROR_FAIL);
				result.setError_msg(logisticsNumberGetResp.getError_msg());
			}
		} else {// 之前的代码
			EmsLogisticsNumberGetReq logisticsNumberGetReq = new EmsLogisticsNumberGetReq();
			logisticsNumberGetReq.setNotNeedReqStrOrderId(order_id);
			EmsLogisticsNumberGetResp logisticsNumberGetResp = zteClient.execute(logisticsNumberGetReq, EmsLogisticsNumberGetResp.class);
			if (EcsOrderConsts.INF_RESP_CODE_0000.equals(logisticsNumberGetResp.getError_code()) && logisticsNumberGetResp.getCount() != 0) {
				// 保存EMS物流单号
				String logiNum = logisticsNumberGetResp.getMailnums().get(0);
				OrderDeliveryBusiRequest orderDeliveryBusiRequest = CommonDataFactory.getInstance().getDeliveryBusiRequest(order_id,
						EcsOrderConsts.LOGIS_NORMAL);
				orderDeliveryBusiRequest.setLogi_no(logiNum);
				orderDeliveryBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderDeliveryBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderDeliveryBusiRequest.store();

				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.LOGI_NO }, new String[] { logiNum });

				result.setError_code(ConstsCore.ERROR_SUCC);
				result.setError_msg(logisticsNumberGetResp.getError_msg());
			} else {
				result.setError_code(ConstsCore.ERROR_FAIL);
				result.setError_msg(logisticsNumberGetResp.getError_msg());
			}
		}

		return result;
	}

	@Override
	public BusiDealResult getLogisticsNumber(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient zteClient = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		EmsLogisticsNumberGetReq logisticsNumberGetReq = new EmsLogisticsNumberGetReq();
		EmsLogisticsNumberGetResp logisticsNumberGetResp = zteClient.execute(logisticsNumberGetReq, EmsLogisticsNumberGetResp.class);
		if (EcsOrderConsts.EMS_INF_SUCC_CODE.equals(logisticsNumberGetResp.getError_code())) {
			List<String> logisticsNumbers = logisticsNumberGetResp.getMailnums();
			// 保存EMS物流单号
			OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(order_id);
			OrderDeliveryBusiRequest orderDeliveryBusiRequest = tree.getOrderDeliveryBusiRequests().get(0);
			orderDeliveryBusiRequest.setLogi_no(logisticsNumbers.get(0));
			orderDeliveryBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderDeliveryBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderDeliveryBusiRequest.store();
		}
		result.setError_code(logisticsNumberGetResp.getError_code());
		result.setError_msg(logisticsNumberGetResp.getError_msg());
		return result;
	}

	@Override
	public BusiDealResult syncLogisticsInfo(String order_id) {
		BusiDealResult result = new BusiDealResult();

		LogisticsInfoSyncReq logisticsInfoSyncReq = new LogisticsInfoSyncReq();
		logisticsInfoSyncReq.setNotNeedReqStrOrderId(order_id);
		ZteClient zteClient = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		LogisticsInfoSyncResp logisticsInfoSyncResp = zteClient.execute(logisticsInfoSyncReq, LogisticsInfoSyncResp.class);
		result.setError_code(logisticsInfoSyncResp.getError_code());
		result.setError_msg(logisticsInfoSyncResp.getError_msg());
		return result;
	}

	/**
	 * 黑名单校验
	 * 
	 * @return
	 */
	@Override
	public BusiDealResult blackListCheck(String order_id) {
		BusiDealResult result = new BusiDealResult();
		CustomerCheckResp resp = new CustomerCheckResp();
		CustomerCheckReq req = new CustomerCheckReq();
		req.setNotNeedReqStrOrderId(order_id);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		try {
			resp = client.execute(req, CustomerCheckResp.class);
			if (EcsOrderConsts.BSS_SUCCESS_00000.equals(resp.getCode())) {
				Map map = resp.getRespJson();
				String is_black = map.get("is_black") + "";// 01:非黑名单；02:黑名单
				// String is_real = map.get("is_real") + "";// 01:实名；02:非实名
				// String net_type = map.get("net_type") + "";//
				// 01：2G；02：3G；03：4G
				// String user_type = map.get("user_type") + "";// 01：预付费；02：后付费
				// String is_owe = ((Map) map.get("owe_info")).get("is_owe") +
				// "";//
				// 01:欠费；02:不欠费
				if ("02".equals(is_black)) {
					result.setError_msg("黑名单客户");
					result.setError_code("9999");
				} else {
					// if (net_type != null) {
					// CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					// new String[] { AttrConsts.OLD_NET_TYPE }, new String[] {
					// net_type });
					// }
					result.setError_code(EcsOrderConsts.EMS_INF_SUCC_CODE);
					result.setError_msg("客户校验成功");
					result.setResponse(resp);
				}
			} else {
				result.setError_code("-999999");
				result.setError_msg("客户校验失败：" + resp.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setError_code("-999999");
			result.setError_msg("客户校验失败：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 开户预提交
	 * 
	 * @return
	 */
	@Override
	public BusiDealResult bssPreCommit(PreCommitReq req) {
		BusiDealResult result = new BusiDealResult();
		PreCommitResp resp = new PreCommitResp();

		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		resp = client.execute(req, PreCommitResp.class);

		if (!StringUtils.equals(resp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {// 失败
			// 设置错误返回码
			result.setError_code(resp.getError_code());
			result.setError_msg(resp.getError_msg());
		} else {// 成功
				// 保存订单预提交信息
			JSONObject respJson = JSON.parseObject(resp.getRespJson());
			String serial_num = respJson.getString("serial_num");// 预提交订单号
			CommonDataFactory.getInstance().updateAttrFieldValue(req.getNotNeedReqStrOrderId(), new String[] { AttrConsts.ACTIVE_NO },
					new String[] { serial_num });
			result.setResponse(resp);
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");
		}

		return result;
	}

	@Override
	public BusiDealResult zbOutCallConfirm(String order_id) {
		BusiDealResult result = new BusiDealResult();
		CallOutOperationReq req = new CallOutOperationReq();
		String outTid = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID);
		req.setNotNeedReqStrOrderId(order_id);
		req.setOrderNo(outTid);
		req.setOrderId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_NUM));
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		CrawlerResp resp = client.execute(req, CrawlerResp.class);
		if (!StringUtils.equals(ConstsCore.ERROR_SUCC, resp.getError_code())) {
			logger.info("=====OrdVisitTacheManager zbOutCallConfirm 爬虫外呼确认操作接口调用出错【errorMsg:" + resp.getError_msg() + "】");
			// 标记异常
			Map params = new HashMap();
			BusiCompRequest bcr = new BusiCompRequest();
			bcr.setEnginePath("zteCommonTraceRule.markedException");
			bcr.setOrder_id(order_id);
			params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			params.put(EcsOrderConsts.EXCEPTION_REMARK, result.getError_msg());
			bcr.setQueryParams(params);
			CommonDataFactory.getInstance().execBusiComp(bcr);
		}
		result.setError_code(resp.getError_code());
		result.setError_msg(resp.getError_msg());
		result.setResponse(resp);
		return result;
	}

	@Override
	public BusiDealResult crawlerStateSynReturnStaToZS(String order_id) {
		BusiDealResult result = new BusiDealResult();
		SingleApplicationReq req = new SingleApplicationReq();

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String reasonCode = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.RETURNED_REASON_CODE);
		String reasonDesc = "";
		if (!StringUtil.isEmpty(reasonCode)) {
			IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
			DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
			List<Map> list = dcPublicCache.getList(StypeConsts.RETURNED_REASON_TYPE);
			if (list != null && list.size() > 0) {
				for (Map map : list) {
					String pname = Const.getStrValue(map, "pname");
					String pkey = Const.getStrValue(map, "pkey");
					if (pkey.equals(reasonCode)) {
						reasonDesc = pname;// 描述
					}
				}
			}
		}
		String cancelRemark = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFUND_DESC);
		String orderId = orderTree.getOrderExtBusiRequest().getOrder_num();
		String link = "";// orderReview：订单审核环节，automatic：自动开户环节，deliverGoods：发货环节
		String tache_code = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
		if (EcsOrderConsts.DIC_ORDER_NODE_B.equals(tache_code) || EcsOrderConsts.DIC_ORDER_NODE_C.equals(tache_code)) {// 审核、配货
			link = "orderReview";
		} else if (EcsOrderConsts.DIC_ORDER_NODE_D.equals(tache_code) || EcsOrderConsts.DIC_ORDER_NODE_X.equals(tache_code)
				|| EcsOrderConsts.DIC_ORDER_NODE_F.equals(tache_code)) {// 开户、写卡、质检稽核
			link = "automatic";
		} else if (EcsOrderConsts.DIC_ORDER_NODE_H.equals(tache_code)) {// 发货
			link = "deliverGoods";
		}
		String orderNo = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID);

		req.setNotNeedReqStrOrderId(order_id);
		req.setReasonCode(reasonCode);
		req.setReasonDesc(reasonDesc);
		req.setCancelRemark(cancelRemark);
		req.setOrderId(orderId);
		req.setLink(link);
		req.setOrderNo(orderNo);

		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ZteResponse rsp = client.execute(req, ZteResponse.class);// 临时注释
		// ZteResponse rsp = new ZteResponse(); //测试代码
		// rsp.setError_code(ConstsCore.ERROR_SUCC);
		if (!ConstsCore.ERROR_SUCC.equals(rsp.getError_code())) {

			logger.info("=====OrdVisitTacheManager crawlerStateSynReturnStaToZS 退单申请接口调用出错【errorMsg:" + rsp.getError_msg() + "】");
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
			result.setError_msg(rsp.getError_msg());
		} else {
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	public int queryZbOrderAuditNum(String outTid) {
		String sql = "select count(1) from es_order_audit_zb where out_tid =?";
		return this.baseDaoSupport.queryForInt(sql, outTid);
	}

	public void insertZbOrderAudit(String outId, String orderNum, String audit_status, String audit_num, String crawler_status,
			String distribute_status) {
		String sql = "insert into es_order_audit_zb(out_tid,zb_id,audit_status,audit_num,source_from,crawler_status,distribute_status) values(?,?,?,?,?,?,?)";
		this.baseDaoSupport.execute(sql, outId, orderNum, audit_status, audit_num, ManagerUtils.getSourceFrom(), crawler_status, distribute_status);
	}

	public void upZbOrderAuditStatus(Map map) {
		String sql = "update es_order_audit_zb set ";
		List list = new ArrayList();
		if (null != map.get("audit_status")) {
			sql += " audit_status = ?";
			list.add(map.get("audit_status"));
		}
		if (null != map.get("crawler_status")) {
			sql += " crawler_status = ?";
			list.add(map.get("crawler_status"));
		}
		if (null != map.get("distribute_status")) {
			sql += " distribute_status = ?";
			list.add(map.get("distribute_status"));
		}
		sql += " where out_tid=? ";
		list.add(map.get("out_tid"));
		this.baseDaoSupport.execute(sql, list.toArray());
	}

	@Override
	public void insertZbOrderStatus(String orderId, String outTid, String zbId, String status, String createTime, String zbLastModifyTime,
			String remark, String sourceFrom) {
		String sql = "insert into es_order_status_zb(order_id,out_tid,zb_id,status,create_time,zb_last_modify_time,remark,source_from) values(?,?,?,?,?,?,?,?,?)";
		List list = new ArrayList();
		list.add(orderId);
		list.add(outTid);
		list.add(zbId);
		list.add(status);
		list.add(createTime);
		list.add(zbLastModifyTime);
		list.add(remark);
		list.add(sourceFrom);
		this.baseDaoSupport.execute(sql, list.toArray());

	}
	
	/**
	 * 业务类型校验
	 * 
	 * @author 喻天琦 2018年01月29日 11:52:39
	 */
	public BusiDealResult serviceTypeCheck (String order_id) {
		BusiDealResult result = new BusiDealResult();
		String order_service_type = CommonDataFactory.getInstance().getGoodSpec(order_id, null, AttrConsts.ORDER_SERVICE_TYPE);
		String line_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.LINE_TYPR);
		if (StringUtils.equals(order_service_type, line_type)||StringUtils.isEmpty(line_type)) {// 校验成功
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
		} else {// 失败
			String order_service_name =CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_SERVICE_TYPE", order_service_type);
			String line_name = CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_SERVICE_TYPE", line_type);
			result.setError_code("-9999");
			result.setError_msg("所选地址业务类型（"+line_name+"）与商品业务类型（"+order_service_name+"）不一致，请重新选择地址或重新选择商品");
		}
		return result;
	}
	/**
	 * 客户资料创建  先进行客户资料校验  存在客户则不进行创建，若不存在则进行创建
	 * add by sgf 2019/02/13
	 */
    @Override
    public BusiDealResult custInfoCreate(String order_id) throws ApiBusiException {
        BusiDealResult result = new BusiDealResult();
        ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        CustomerInfoCheckReq reqCheckReq = new CustomerInfoCheckReq();
        reqCheckReq.setNotNeedReqStrOrderId(order_id);
        CustomerInfoResponse respCheck = client.execute(reqCheckReq, CustomerInfoResponse.class);
        if (!StringUtils.equals(respCheck.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
            // 此前无此客户信息 ,认为校验通过
            if (StringUtils.equals(respCheck.getCode(), EcsOrderConsts.AOP_CUST_CHECK_0001)) {
                CustInfoCreateReq req = new CustInfoCreateReq();
                req.setNotNeedReqStrOrderId(order_id);
                CustInfoCreateResponse rsp = client.execute(req, CustInfoCreateResponse.class);
                if (rsp.getResultMsg() != null && rsp.getResultMsg().getRespInfo() != null){
                    CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CUST_ID },new String[] { rsp.getResultMsg().getRespInfo().getCustId() });
                    CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.IS_OLD },new String[] {"1"});
                    result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
                    result.setError_msg("客户创建成功");
                    result.setResponse(rsp);
                    return result;
                }else{
                    if(rsp.getResultMsg() != null && "1017".equals(rsp.getResultMsg().getCode())){
                        CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.IS_OLD },new String[] {"1"});
                        result.setError_code(EcsOrderConsts.AOP_ERROR_9999);
                        result.setError_msg("客户已存在");
                        result.setResponse(rsp);
                        return result;
                    }else{
                        result.setError_code(null != rsp.getResultMsg() ? rsp.getResultMsg().getCode() : rsp.getError_code());
                        result.setError_msg(null != rsp.getResultMsg() ? rsp.getResultMsg().getDetail() : rsp.getError_msg());
                        result.setResponse(rsp);
                        return result;
                    }
                }
            } else {
                result.setError_msg(respCheck.getDetail());
                result.setError_code(respCheck.getCode());
                result.setResponse(respCheck);
                return result;
            }
        } else {
            CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CUST_ID },new String[] { respCheck.getCustInfo().get(0).getCustId() });
            CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.IS_OLD },new String[] {"1"});
            result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
            result.setError_msg("客户创建成功");
            result.setResponse(respCheck);
            return result;
        }
       /* CustInfoCreateReq req = new CustInfoCreateReq();
        req.setNotNeedReqStrOrderId(order_id);
        CustInfoCreateResponse rsp = client.execute(req, CustInfoCreateResponse.class);
        if(rsp.getResultMsg() != null && rsp.getResultMsg().getRespInfo() != null){
            CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CUST_ID },new String[] { rsp.getResultMsg().getRespInfo().getCustId() });
            CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.IS_OLD },new String[] {"1"});
            result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
            result.setError_msg("客户创建成功");
        }else{
            if(rsp.getResultMsg() != null && "1017".equals(rsp.getResultMsg().getCode())){
                CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.IS_OLD },new String[] {"1"});
                result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
                result.setError_msg("客户创建成功");
            }else{
                result.setError_code(null != rsp.getResultMsg() ? rsp.getResultMsg().getCode() : rsp.getError_code());
                result.setError_msg(null != rsp.getResultMsg() ? rsp.getResultMsg().getDetail() : rsp.getError_msg());
            }
        }*/
       /* result.setResponse(rsp);
        return result;*/
    }
}
