package com.ztesoft.net.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.jdbc.core.RowMapper;

import params.req.OrderDistributeCtnStandardReq;
import params.req.OrderPreCreateCtnStandardReq;
import params.resp.OrderDistributeCtnStandardResp;
import params.resp.OrderPreCreateCtnStandardResp;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.MD5Util;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.ibss.common.util.IntentUtil;
import com.ztesoft.inf.communication.client.CommCaller;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.ecsord.params.ecaop.req.CmcSmsSendReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderMakeupReq;
import com.ztesoft.net.ecsord.params.ecaop.req.orderInfoBackfill.ORDER_INFO_BACKFILL_REQ;
import com.ztesoft.net.ecsord.params.ecaop.req.orderInfoBackfill.OrderInfoBackfillReq;
import com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack.COMM_OBJECT;
import com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack.CUST_INFO;
import com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack.DEVELOPER_INFO;
import com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack.NUMBER_INFO;
import com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack.ORDER_RECEIVE_BACK_REQ;
import com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack.OrderReceiveBackReq;
import com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack.PAY_INFO;
import com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack.REQ_DATA;
import com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack.REQ_HEAD;
import com.ztesoft.net.ecsord.params.ecaop.resp.CmcSmsSendResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderMakeupResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.orderInfoBackfill.OrderInfoBackfillRsp;
import com.ztesoft.net.ecsord.params.ecaop.resp.orderReceiveBack.ORDER_RECEIVE_BACK_RSP;
import com.ztesoft.net.ecsord.params.ecaop.resp.orderReceiveBack.OrderReceiveBackResp;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.context.webcontext.WebSessionContext;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.RequestStoreManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.model.OrderQueryParams;
import com.ztesoft.net.service.IOrdIntentManager;
import com.ztesoft.net.util.ZjCommonUtils;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderFileBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.AopSmsSendReq;
import zte.net.ecsord.params.ecaop.resp.AopSmsSendResp;

@SuppressWarnings("rawtypes")
public class OrdIntentManager extends BaseSupport implements IOrdIntentManager {

	@Resource
	private IDcPublicInfoManager dcPublicInfoManager;

	@Resource
	private ICacheUtil cacheUtil;

	@Resource
	private IntentUtil intentUtil;

	@Override
	public String addIntentRecord(Map<String, Object> map) {

		AdminUser adminUser = ManagerUtils.getAdminUser();
		String user_id = "1";
		String user_name = "系统管理员";
		if (adminUser != null) {
			user_id = adminUser.getUserid();
			user_name = adminUser.getRealname();
		}
		map.put("op_id", user_id);
		map.put("op_name", user_name);
		map.put("create_time", new Date());
		map.put("source_from", ManagerUtils.getSourceFrom());
		map.put("type_code", "intent");// 标记意向单备注

		baseDaoSupport.insert("es_intent_handle_logs", map);

		return "0";
	}

	@Override
	public Map<String, Object> getAcceptDetail(String order_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("acceptName", "");
		map.put("acceptCert", "");
		map.put("acceptAddr", "");
		map.put("order_county_code", "");
		map.put("county_id", "");
		map.put("order_city_code", "");
		if (StringUtils.isEmpty(order_id)) {
			return map;
		}
		String sql = "select ship_name as acceptName, ship_addr as  acceptAddr,cert_num as acceptCert,source_system_type as order_from,order_county_code,county_id,order_city_code from es_order_intent where order_id='"
				+ order_id + "' and source_from = '" + ManagerUtils.getSourceFrom() + "'";
		List<Map<String, Object>> list = baseDaoSupport.queryForLists(sql);
		if (list.size() > 0) {
			map = list.get(0);
		}
		String order_from = Const.getStrValue(map, "order_from");
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String can_update = cacheUtil.getConfigInfo("YXD_CAN_UPDATE_" + order_from);
		if (StringUtil.isEmpty(can_update)) {
			can_update = "yes";
		}
		map.put("can_update", can_update);
		return map;
	}

	@Override
	public Map<String, Object> saveIntent(Map<String, Object> map) {
		Map<String, Object> fieldsMap = new HashMap<String, Object>();
		fieldsMap.putAll(map);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("order_id", map.get("order_id"));
		baseDaoSupport.update("es_order_intent", fieldsMap, whereMap);
		return null;
	}

	/**
	 * 商品预定回填接口 针对京东便利店
	 */
	@Override
	public BusiDealResult orderReceiveBack(String order_id) {
		BusiDealResult result = new BusiDealResult();
		OrderReceiveBackReq req = new OrderReceiveBackReq();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);

		String sql = "select i.req,e.* from es_order_intent i,es_order_extvtl e where i.order_id=e.order_id and i.order_id='"
				+ order_id + "' and i.source_from='" + ManagerUtils.getSourceFrom()
				+ "' and i.source_system_type='100101' ";
		List<Map<String, Object>> intentList = baseDaoSupport.queryForLists(sql);
		if (intentList.size() != 1) {
			result.setError_code("0000");
			result.setError_msg("意向单信息不存在(跳过)");
			return result;
		}

		Gson gson = new Gson();
		Map<String, Object> mapReq = new HashMap<String, Object>();
		Map<String, Object> mapVtl = intentList.get(0);
		mapReq = gson.fromJson(intentList.get(0).get("req").toString(), mapReq.getClass());// 商品预定接口请求
		Map mapReqHead = (Map) mapReq.get("REQ_HEAD");
		Map mapReqData = (Map) mapReq.get("REQ_DATA");
		List mapReqDataCommObject = (List) mapReqData.get("COMM_OBJECT");
		Map mapReqDataComm = (Map) mapReqDataCommObject.get(0);
		Map mapReqDataCust;
		if (mapReqData.get("CUST_INFO") != null) {
			mapReqDataCust = (Map) mapReqData.get("CUST_INFO");
		}
		// Map mapReqDeveloper;
		// if (mapReqData.get("DEVELOPER_INFO") != null) {
		// List mapReqDataDeveloper = (List) mapReqData.get("DEVELOPER_INFO");
		// mapReqDeveloper = (Map) mapReqDataDeveloper.get(0);
		// }

		req.setNotNeedReqStrOrderId(order_id);
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<Map<String, String>> list;

		ORDER_RECEIVE_BACK_REQ back = new ORDER_RECEIVE_BACK_REQ();
		REQ_HEAD req_head = new REQ_HEAD();
		String IN_MODE_CODE = Const.getStrValue(mapReqHead, "IN_MODE_CODE");// 订单接入渠道类型，参考3.1.接入渠道
		IN_MODE_CODE = "2010";// 省份统一编码
		// String OUTER_ORDER_ID = Const.getStrValue(mapReqHead,
		// "TOUCH_ORDER_ID");// 生产系统订单号
		String OUTER_ORDER_ID = order_id;// 生产系统订单号
		String ORDER_PROVINCE = Const.getStrValue(mapReqHead, "ORDER_PROVINCE");// 省份
		String ORDER_CITY = Const.getStrValue(mapReqHead, "ORDER_CITY");// 地市
		String STORE_ID = Const.getStrValue(mapReqHead, "STORE_ID");// 门店
		String TRADE_STAFFID = Const.getStrValue(mapVtl, "OUT_OPERATOR");// 操作员工
		String DEPART_ID = Const.getStrValue(mapVtl, "OUT_OFFICE");// 操作部门
		String CHANNEL_ID = Const.getStrValue(mapVtl, "CHANNELID");// 渠道编码
		String CHANNEL_TYPE = Const.getStrValue(mapVtl, "CHANNEL_TYPE");// 渠道类型
		String ORDER_TIME = DateFormatUtils.formatDate("yyyy-MM-dd hh:mm:ss");// 回填时间YYYY-MM-DDhh24:mi:ss
		String PRODUCE_CUST_ID = Const.getStrValue(mapVtl, "CUST_ID");// 生产系统客户标识
		req_head.setIN_MODE_CODE(IN_MODE_CODE);
		req_head.setOUTER_ORDER_ID(OUTER_ORDER_ID);
		req_head.setORDER_PROVINCE(ORDER_PROVINCE);
		req_head.setORDER_CITY(ORDER_CITY);
		req_head.setSTORE_ID(STORE_ID);
		req_head.setTRADE_STAFFID(TRADE_STAFFID);
		req_head.setDEPART_ID(DEPART_ID);
		req_head.setCHANNEL_ID(CHANNEL_ID);
		req_head.setCHANNEL_TYPE(CHANNEL_TYPE);
		req_head.setORDER_TIME(ORDER_TIME);
		req_head.setPRODUCE_CUST_ID(PRODUCE_CUST_ID);

		REQ_DATA req_data = new REQ_DATA();
		String pay_tag = "1";// 支付标识：0未支付1已支付
		CUST_INFO cust_info = new CUST_INFO();
		String CUST_NAME = Const.getStrValue(mapVtl, "PHONE_OWNER_NAME");// 客户名称
		String CUST_TYPE = Const.getStrValue(mapVtl, "CUSTOMERTYPE");// 客户类型，0公众客户，1集团客户
		if (null != CUST_TYPE && !StringUtils.isEmpty(CUST_TYPE)) {
			list = dcPublicCache.getList("CustomerType");
			for (Map<String, String> map : list) {
				if (CUST_TYPE.equals(map.get("pkey"))) {
					CUST_TYPE = map.get("codea");
					break;
				}
			}
		}
		String IS_ASSURE = "";// 是否为担保人新建
		OrderItemProdExtBusiRequest itemProdExt = orderTree.getOrderItemBusiRequests().get(0)
				.getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest();
		OrderDeliveryBusiRequest delivery = orderTree.getOrderDeliveryBusiRequests().get(0);
		String CERT_EXPIRE_DATE = itemProdExt.getCert_failure_time().substring(0, 10).replace("-", "");// 证件有效期格式YYYYMMDD******************************************************
		String CERT_ADDRESS = itemProdExt.getCert_address();// 证件地址
		String SEX = StringUtils.isEmpty(itemProdExt.getCert_card_sex()) ? itemProdExt.getCert_card_sex()
				: Const.getStrValue(mapVtl, "SEX");// 性别***************************************************************
		if (null != SEX && !StringUtils.isEmpty(SEX)) {
			list = dcPublicCache.getList("DIC_CUSTOMER_SEX");
			for (Map<String, String> map : list) {
				if (SEX.equals(map.get("codea"))) {
					SEX = map.get("codeb");
					break;
				}
			}
		}
		String POST_ADDRESS = delivery.getShip_addr();// 通讯地址
		String CERT_TYPE = itemProdExt.getCerti_type();// 证件类型******************************************************
		if (null != CERT_TYPE && !StringUtils.isEmpty(CERT_TYPE)) {
			list = dcPublicCache.getList("DIC_BLD_CERT_TYPE");
			for (Map<String, String> map : list) {
				if (CERT_TYPE.equals(map.get("codea"))) {
					CERT_TYPE = map.get("pkey");
					break;
				}
			}
		}
		String CERT_ID = itemProdExt.getCert_card_num();// 证件号码
		String CUST_PHONE = delivery.getShip_tel();// 电话
		String CONTACT_PERSON = delivery.getShip_name();// 联系人
		String CONTACT_PHONE = delivery.getShip_tel();// 联系人电话
		String CUST_ID = Const.getStrValue(mapVtl, "CUST_ID");// 客户ID
		cust_info.setCUST_NAME(CUST_NAME);
		cust_info.setCUST_TYPE(CUST_TYPE);
		cust_info.setIS_ASSURE(IS_ASSURE);
		cust_info.setCERT_EXPIRE_DATE(CERT_EXPIRE_DATE);
		cust_info.setCERT_ADDRESS(CERT_ADDRESS);
		cust_info.setSEX(SEX);
		cust_info.setPOST_ADDRESS(POST_ADDRESS);
		cust_info.setCERT_TYPE(CERT_TYPE);
		cust_info.setCERT_ID(CERT_ID);
		cust_info.setCUST_PHONE(CUST_PHONE);
		cust_info.setCONTACT_PERSON(CONTACT_PERSON);
		cust_info.setCONTACT_PHONE(CONTACT_PHONE);
		cust_info.setCUST_ID(CUST_ID);

		List<DEVELOPER_INFO> developer_info = new ArrayList<DEVELOPER_INFO>();// 暂时按单节点处理
		DEVELOPER_INFO developer = new DEVELOPER_INFO();
		String DEVELOP_TYPE = "2";// 发展类型（0：用户；1：商品；2：能人）******************************************************
		// String DEVELOP_EPARCHY_CODE = null;// 发展人地市
		String DEVELOP_EPARCHY_CODE = Const.getStrValue(mapReqHead, "ORDER_CITY");// 发展人地市
		String DEVELOP_DEPART_NAME = null;// 发展人所在部门名称
		// (a<b)?a:b;
		String DEVELOP_DEPART_ID = StringUtils.isEmpty(Const.getStrValue(mapVtl, "DEVELOPDEP_ID"))
				? Const.getStrValue(mapVtl, "DEVELOPDEP_ID")
				: Const.getStrValue(mapVtl, "DEVELOPMENT_POINT_CODE");// 发展人所在部门编码
		String DEVELOP_STAFF_ID = Const.getStrValue(mapVtl, "DEVELOPMENT_CODE");// 发展员工
		String DEVELOP_STAFF_NAME = Const.getStrValue(mapVtl, "DEVELOPMENT_NAME");// 发展人名称
		String DEVELOP_DATE = DateFormatUtils.formatDate("yyyy-MM-dd hh:mm:ss");// 发展时间
		String DEVELOP_MANAGER_ID = null;// 发展人经理编码
		String DEVELOP_MANAGER_NAME = null;// 发展人经理名称
		String DEVELOP_NICK_ID = null;// 发展人别名编码
		String DEVELOP_NICK_NAME = null;// 发展人别名名称
		developer.setDEVELOP_TYPE(DEVELOP_TYPE);
		developer.setDEVELOP_EPARCHY_CODE(DEVELOP_EPARCHY_CODE);
		developer.setDEVELOP_DEPART_NAME(DEVELOP_DEPART_NAME);
		developer.setDEVELOP_DEPART_ID(DEVELOP_DEPART_ID);
		developer.setDEVELOP_STAFF_ID(DEVELOP_STAFF_ID);
		developer.setDEVELOP_STAFF_NAME(DEVELOP_STAFF_NAME);
		developer.setDEVELOP_DATE(DEVELOP_DATE);
		developer.setDEVELOP_MANAGER_ID(DEVELOP_MANAGER_ID);
		developer.setDEVELOP_MANAGER_NAME(DEVELOP_MANAGER_NAME);
		developer.setDEVELOP_NICK_ID(DEVELOP_NICK_ID);
		developer.setDEVELOP_NICK_NAME(DEVELOP_NICK_NAME);
		developer_info.add(developer);

		List<COMM_OBJECT> comm_object = new ArrayList<COMM_OBJECT>();
		COMM_OBJECT comm = new COMM_OBJECT();
		String COMM_ID = Const.getStrValue(mapReqDataComm, "COMM_ID");// 商品编码，对应电商的：goodsId。如果是组合商品，直接传组合商品。
		String COMM_COUNT = Const.getStrValue(mapReqDataComm, "COMM_COUNT");// 商品数量
		String COMM_PRICE = Const.getStrValue(mapVtl, "SELL_PRICE");// 销售价格（单价）******************************************************
		String IMEI = Const.getStrValue(mapReqDataComm, "IMEI");// 终端串码（终端类商品对应串码）
		String DISCNT_TYPE = itemProdExt.getFirst_payment();// 首月资费方式******************************************************
		if (null != DISCNT_TYPE && !StringUtils.isEmpty(DISCNT_TYPE)) {
			list = dcPublicCache.getList("offer_eff_type");
			for (Map<String, String> map : list) {
				if (DISCNT_TYPE.equals(map.get("pkey"))) {
					DISCNT_TYPE = map.get("codea");
					break;
				}
			}
		}
		// List<SUB_COMM_INFO> SUB_COMM_INFO = new ArrayList<SUB_COMM_INFO>();//
		// 子商品信息
		// SUB_COMM_INFO sub = new SUB_COMM_INFO();
		// String SUB_COMM_ID = "";// 子商品ID
		// String SUB_IMEI = "";// 子商品终端串码
		// sub.setSUB_COMM_ID(SUB_COMM_ID);
		// sub.setSUB_IMEI(SUB_IMEI);
		// SUB_COMM_INFO.add(sub);

		OrderItemExtBusiRequest itemExt = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest();
		OrderPhoneInfoBusiRequest phoneInfo = orderTree.getPhoneInfoBusiRequest();
		List<NUMBER_INFO> NUMBER_INFO = new ArrayList<NUMBER_INFO>();// 号码信息节点，购买号卡、或合约机有此节点。说明：合约机只有一个主套餐，故号码节点可以放在商品上。
		NUMBER_INFO number = new NUMBER_INFO();
		String SERIAL_NUMBER = itemExt.getPhone_num();// 号码
		String NUMBER_LEVER = phoneInfo.getClassId() + "";// 号码等级******************************************************
		String comm_id = Const.getStrValue(mapReqDataComm, "COMM_ID");// 商品id
		String NUM_PROVINCE = Const.getStrValue(mapReqHead, "ORDER_PROVINCE");// 号码省份
		String NUM_CITY = Const.getStrValue(mapReqHead, "ORDER_CITY");// 号码地市
		String STORE_FEE = phoneInfo.getAdvancePay();// 预存款******************************************************
		String LIMIT_FEE = phoneInfo.getLowCostPro();// 每月最低消费******************************************************
		number.setSERIAL_NUMBER(SERIAL_NUMBER);
		number.setNUMBER_LEVER(NUMBER_LEVER);
		number.setCOMM_ID(comm_id);
		number.setNUM_PROVINCE(NUM_PROVINCE);
		number.setNUM_CITY(NUM_CITY);
		if (StringUtils.isEmpty(STORE_FEE)) {
			number.setSTORE_FEE(0);
		} else {
			number.setSTORE_FEE(Double.valueOf(STORE_FEE).doubleValue());
		}
		if (StringUtils.isEmpty(LIMIT_FEE)) {
			number.setLIMIT_FEE(0);
		} else {
			number.setLIMIT_FEE(Double.valueOf(LIMIT_FEE).doubleValue());
		}
		NUMBER_INFO.add(number);

		// List<NETWORKRES_INFO> NETWORKRES_INFO = new
		// ArrayList<NETWORKRES_INFO>();// 固网信息节点
		// NETWORKRES_INFO networkres = new NETWORKRES_INFO();
		// NETWORKRES_INFO.add(networkres);
		// List<COMM_ATTR_INFO> COMM_ATTR_INFO = new
		// ArrayList<COMM_ATTR_INFO>();// 商品属性节点
		// COMM_ATTR_INFO comm_attr = new COMM_ATTR_INFO();
		// String ATTR_TYPE = "";// 商品属性类型
		// String ATTR_CODE = "";// 属性编码
		// String ATTR_VALUE = "";// 属性值
		// String START_DATE = "";// 开始时间
		// String END_DATE = "";// 结束时间
		// comm_attr.setATTR_TYPE(ATTR_TYPE);
		// comm_attr.setATTR_CODE(ATTR_CODE);
		// comm_attr.setATTR_VALUE(ATTR_VALUE);
		// comm_attr.setSTART_DATE(START_DATE);
		// comm_attr.setEND_DATE(END_DATE);
		// COMM_ATTR_INFO.add(comm_attr);
		// List<PROM_INFO> PROM_INFO = new ArrayList<PROM_INFO>();//
		// 活动节点—商品已参加的活动

		comm.setCOMM_ID(COMM_ID);
		if (StringUtils.isEmpty(COMM_COUNT)) {
			comm.setCOMM_COUNT(1);
		} else {
			comm.setCOMM_COUNT(Double.valueOf(COMM_COUNT).doubleValue());
		}
		if (StringUtils.isEmpty(COMM_PRICE)) {
			comm.setCOMM_PRICE(0);
		} else {
			comm.setCOMM_PRICE(Double.valueOf(COMM_PRICE).doubleValue());
		}
		comm.setIMEI(IMEI);
		comm.setDISCNT_TYPE(DISCNT_TYPE);
		comm.setSUB_COMM_INFO(null);
		comm.setNUMBER_INFO(NUMBER_INFO);
		comm.setNETWORKRES_INFO(null);
		comm.setCOMM_ATTR_INFO(null);
		comm.setPROM_INFO(null);
		comm_object.add(comm);

		// POST_INFO post_info = new POST_INFO();
		// String POST_TYPE = "";// 配送方式，参考附录。
		// String POST_PROVINCE = "";// 省份
		// String POST_CITY = "";// 地市
		// String POST_AREA = "";// 区县
		// String POST_STREET = "";// 街道
		// String POST_NAME = "";// 收货人姓名
		// String POST_PHONE = "";// 收货人电话
		// String post_address = "";// 收货地址
		// String POST_CODE = "";// 邮政编码
		// String SELF_PICKUP_CODE = "";// 自提店编码
		// String SELF_PICKUP_NAME = "";// 自提店名称
		// String EXPECT_START_TIME = "";// 期望交付开始时间
		// String EXPECT_END_TIME = "";// 期望交付结束时间
		// post_info.setPOST_TYPE(POST_TYPE);
		// post_info.setPOST_PROVINCE(POST_PROVINCE);
		// post_info.setPOST_CITY(POST_CITY);
		// post_info.setPOST_AREA(POST_AREA);
		// post_info.setPOST_STREET(POST_STREET);
		// post_info.setPOST_NAME(POST_NAME);
		// post_info.setPOST_PHONE(POST_PHONE);
		// post_info.setPOST_ADDRESS(post_address);
		// post_info.setPOST_CODE(POST_CODE);
		// post_info.setSELF_PICKUP_CODE(SELF_PICKUP_CODE);
		// post_info.setSELF_PICKUP_NAME(SELF_PICKUP_NAME);
		// post_info.setEXPECT_START_TIME(EXPECT_START_TIME);
		// post_info.setEXPECT_END_TIME(EXPECT_END_TIME);

		List<PAY_INFO> pay_info = new ArrayList<PAY_INFO>();
		PAY_INFO pay = new PAY_INFO();
		String PAY_MODE = orderTree.getOrderPayBusiRequests().get(0).getPay_method();// 支付方式******************************************************
		if (null != PAY_MODE && !StringUtils.isEmpty(PAY_MODE)) {
			list = dcPublicCache.getList("pay_way");
			for (Map<String, String> map : list) {
				if (PAY_MODE.equals(map.get("pkey"))) {
					PAY_MODE = map.get("codea");
					break;
				}
			}
		}
		String PAY_CHANNEL = Const.getStrValue(mapVtl, "PAYCHANNELID");// 支付渠道
		String PAY_ID = Const.getStrValue(mapVtl, "PAYPLATFORMORDERID");// 支付流水
		String PAY_TIME = orderTree.getOrderBusiRequest().getPay_time();// 支付时间******************************************************
		pay.setPAY_MODE(PAY_MODE);
		pay.setPAY_CHANNEL(PAY_CHANNEL);
		pay.setPAY_ID(PAY_ID);
		pay.setPAY_TIME(PAY_TIME);

		pay_info.add(pay);
		String price_sum = orderTree.getOrderBusiRequest().getPaymoney() + "";// 订单总价******************************************************
		// List<COMM_PRICE_DETAIL> comm_price_detail = new
		// ArrayList<COMM_PRICE_DETAIL>();//商品的费用明细节点
		// COMM_PRICE_DETAIL comm_price = new COMM_PRICE_DETAIL();
		// String FEE_MODE = "";// 费用类型 0营业费 1押金 2预存
		// String PRICE_TYPE = "";// 费用项：增加一个运费.异业才有运费
		// String SINGLE_PRICE = "";// 单价
		// String comm_count = "";// 数量
		// String OLD_PRICE_VAL = "";// 应收价格(单价*数量)
		// String PRICE_VAL = "";// 实收价格
		// String MAX_DERATE_PRICE = "";// 最大折扣金额
		// String comm_id2 = "";// 商品编码
		// comm_price.setFEE_MODE(FEE_MODE);
		// comm_price.setPRICE_TYPE(PRICE_TYPE);
		// comm_price.setSINGLE_PRICE(SINGLE_PRICE);
		// comm_price.setCOMM_COUNT(comm_count);
		// comm_price.setOLD_PRICE_VAL(OLD_PRICE_VAL);
		// comm_price.setPRICE_VAL(PRICE_VAL);
		// comm_price.setMAX_DERATE_PRICE(MAX_DERATE_PRICE);
		// comm_price.setCOMM_ID(comm_id2);
		// comm_price_detail.add(comm_price);

		// List<INVOICE_INFO> invoice_info = new ArrayList<INVOICE_INFO>();
		// INVOICE_INFO invoice = new INVOICE_INFO();
		// String RECEIVE_MESSAGE_EMAIL = "";// 接收邮件
		// String RECEIVE_MESSAGE_NUM = "";// 接收电话
		// String BUYER_TAX_PAYER_ID = "";// 纳税人识别号
		// String BUYER_BANK_ACCOUNT = "";// 公司银行账户
		// String IN_VOICE_HEADER = "";// 发票抬头
		// String BUYER_ADDR = "";// 公司地址
		// String INVOICE_URL = "";// 地址url
		// String FEE = "";// 发票金额
		// String FEE_ITEM_CODE = "";// 发票项
		// String FEE_ITEM_NAME = "";// 发票名称
		// String IS_PRINT = "";// 是否已打印0：未打印  1：已打印
		// String TICKET_TYPE_CODE = "";// 发票类型0-纸质发票 1 电子发票
		// String TICKET_ID = "";// 发票号码
		// String INVOICE_CHECK_CODE = "";// 发票代码
		// String TAX_NO = "";// 税号
		// invoice.setRECEIVE_MESSAGE_EMAIL(RECEIVE_MESSAGE_EMAIL);
		// invoice.setRECEIVE_MESSAGE_NUM(RECEIVE_MESSAGE_NUM);
		// invoice.setBUYER_TAX_PAYER_ID(BUYER_TAX_PAYER_ID);
		// invoice.setBUYER_BANK_ACCOUNT(BUYER_BANK_ACCOUNT);
		// invoice.setIN_VOICE_HEADER(IN_VOICE_HEADER);
		// invoice.setBUYER_ADDR(BUYER_ADDR);
		// invoice.setINVOICE_URL(INVOICE_URL);
		// invoice.setFEE(FEE);
		// invoice.setFEE_ITEM_CODE(FEE_ITEM_CODE);
		// invoice.setFEE_ITEM_NAME(FEE_ITEM_NAME);
		// invoice.setIS_PRINT(IS_PRINT);
		// invoice.setTICKET_TYPE_CODE(TICKET_TYPE_CODE);
		// invoice.setTICKET_ID(TICKET_ID);
		// invoice.setINVOICE_CHECK_CODE(INVOICE_CHECK_CODE);
		// invoice.setTAX_NO(TAX_NO);
		//
		// invoice_info.add(invoice);

		req_data.setPAY_TAG(pay_tag);
		req_data.setCUST_INFO(cust_info);
		// req_data.setDEVELOPER_INFO(developer_info);
		req_data.setCOMM_OBJECT(comm_object);
		req_data.setPOST_INFO(null);
		// req_data.setPAY_INFO(pay_info);
		if (StringUtils.isEmpty(price_sum)) {
			req_data.setPRICE_SUM(0);
		} else {
			req_data.setPRICE_SUM(Double.valueOf(price_sum).doubleValue());
		}
		req_data.setCOMM_PRICE_DETAIL(null);
		req_data.setINVOICE_INFO(null);

		back.setREQ_HEAD(req_head);
		back.setREQ_DATA(req_data);
		req.setORDER_RECEIVE_BACK_REQ(back);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderReceiveBackResp resp = client.execute(req, OrderReceiveBackResp.class);
		if (null != resp.getResultMsg() && null != resp.getResultMsg().getUNI_BSS_BODY()
				&& null != resp.getResultMsg().getUNI_BSS_BODY().getORDER_RECEIVE_BACK_RSP()) {
			ORDER_RECEIVE_BACK_RSP backRsp = resp.getResultMsg().getUNI_BSS_BODY().getORDER_RECEIVE_BACK_RSP();
			result.setError_code(backRsp.getCODE());
			result.setError_msg(backRsp.getDESC());
			if (null != backRsp.getDATA() && !StringUtils.isEmpty(backRsp.getDATA().getORDER_ID())) {
				orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest()
						.setBssOrderId(backRsp.getDATA().getORDER_ID());
				orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest()
						.setActive_no(backRsp.getDATA().getORDER_ID());
				String set_sql = " bssOrderId='" + backRsp.getDATA().getORDER_ID() + "', active_no='"
						+ backRsp.getDATA().getORDER_ID() + "' ";
				ZjCommonUtils.updateOrderTree(set_sql, "es_order_items_ext", order_id, orderTree);
			}
		} else {
			result.setError_code("-999");
			result.setError_msg("回填异常");
		}
		result.setResponse(resp);
		return result;
	}

	/**
	 * 若有意向单，则翻转工单状态为处理失败，备注（撤单），翻转意向单状态为结束； 若无意向单，则保持现有撤单逻辑
	 */
	@Override
	public void cancelIntent(String order_id) {
		String sql = "select t.* from es_order_intent t where t.order_id='" + order_id + "' and t.source_from='"
				+ ManagerUtils.getSourceFrom() + "'";
		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql);
		if (null != list && list.size() > 0) {
			Map<String, Object> fieldsMap = new HashMap<String, Object>();
			Map<String, Object> fieldsMap2 = new HashMap<String, Object>();
			Map<String, Object> whereMap = new HashMap<String, Object>();
			Map<String, Object> whereMap2 = new HashMap<String, Object>();
			fieldsMap.put("status", "00");// 处理失败
			whereMap.put("order_id", order_id);
			daoSupport.update("es_order_intent", fieldsMap, whereMap);

			fieldsMap.clear();
			fieldsMap.put("remark", "APP撤单:撤单");
			fieldsMap.put("order_id", order_id);
			fieldsMap.put("action", "结单");
			// this.addIntentRecord(fieldsMap);
			String user_id = "1";// 人员获取异常，
			String user_name = "系统自动";
			fieldsMap.put("op_id", user_id);
			fieldsMap.put("op_name", user_name);
			fieldsMap.put("create_time", new Date());
			fieldsMap.put("source_from", ManagerUtils.getSourceFrom());
			fieldsMap.put("type_code", "intent");// 标记意向单备注
			baseDaoSupport.insert("es_intent_handle_logs", fieldsMap);

			fieldsMap.clear();
			whereMap.clear();
			fieldsMap.put("status", "2");// 处理失败
			fieldsMap.put("result_remark", "APP撤单:撤单");
			fieldsMap.put("update_time", new Date());
			fieldsMap2.put("contact_results_frist", "110");
			fieldsMap2.put("contact_results_second", "110004");
			whereMap.put("order_id", order_id);
			whereMap.put("status", "0");
			whereMap2.put("order_id", order_id);
			whereMap2.put("status", "00");
			daoSupport.update("es_work_order", fieldsMap, whereMap);
			daoSupport.update("es_order_intent", fieldsMap2, whereMap2);
		}
	}

	/**
	 * 意向单回收直省中台
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String intentBack(String order_id) {
		String sql = "select order_id from es_work_order where order_id='" + order_id + "'and status in ("
				+ cacheUtil.getConfigInfo("INTENT_BACK_WORK_STATUS") + ") and source_from='"
				+ ManagerUtils.getSourceFrom() + "'";
		List<Map<String, String>> list = baseDaoSupport.queryForLists(sql);
		if (list.size() > 0) {
			return "存在未处理工单，不能回收";
		}
		sql = "select * from es_order_intent where order_id='" + order_id + "' and source_from='"
				+ ManagerUtils.getSourceFrom() + "'";
		list = baseDaoSupport.queryForLists(sql);
		if (list.size() > 0) {
			if ("00,03".contains(list.get(0).get("status"))) {
				return "订单已完成或结束";
			}
			String gonghao = cacheUtil.getConfigInfo("PRO_USER_ID_" + list.get(0).get("source_system_type"));
			if (StringUtil.isEmpty(gonghao)) {
				return "未配置省中台虚拟工号";
			}
			if (gonghao.equals(list.get(0).get("lock_id"))) {
				return "已在省中台工号池，不需要回收";
			}
			Map<String, Object> fieldsMap = new HashMap<String, Object>();
			Map<String, Object> whereMap = new HashMap<String, Object>();

			fieldsMap.put("lock_id", gonghao);
			fieldsMap.put("lock_name", "省中台虚拟工号");
			whereMap.put("order_id", order_id);
			daoSupport.update("es_order_intent", fieldsMap, whereMap);

			fieldsMap.clear();
			fieldsMap.put("remark", "省中台回收订单");
			fieldsMap.put("order_id", order_id);
			fieldsMap.put("action", "订单回收");
			this.addIntentRecord(fieldsMap);
		} else {
			return "单号异常" + order_id;
		}
		return "";
	}

	@Override
	public String isSZT(String order_id) {
		AdminUser user = ManagerUtils.getAdminUser();
		if ("1".equals(user.getUserid())) {
			return "1";
		}
		String receiveUserAuth = user.getReceiveUserAuth();// 订单领取工号权限
		if (!StringUtil.isEmpty(receiveUserAuth)) {
			String sql = "select distinct t.cf_value from es_config_info t where t.cf_id like 'PRO_USER_ID_%' and t.source_from='"
					+ ManagerUtils.getSourceFrom() + "'";
			List<Map<String, String>> list = baseDaoSupport.queryForLists(sql);
			if (list.size() > 0) {
				for (Map<String, String> map : list) {
					if (receiveUserAuth.contains(map.get("cf_value"))) {
						return "1";
					}
				}
			}
		}
		return "";
	}

	@Override
	public List qryOrderIntentByOrderId(String order_id) throws Exception {
		StringBuilder builder = new StringBuilder();

		builder.append(" SELECT ");
		builder.append(" a.referee_num, ");
		builder.append(" a.referee_name, ");
		builder.append(" a.intent_order_id, ");
		builder.append(" a.order_province_code, ");
		builder.append(" a.order_city_code, ");
		builder.append(" a.order_county_code, ");
		builder.append(" a.goods_name, ");
		builder.append(" a.goods_id, ");
		builder.append(" a.source_system, ");
		builder.append(" a.source_system_type, ");
		builder.append(" a.remark, ");
		builder.append(" a.status, ");
		builder.append(" a.source_from, ");
		builder.append(" a.order_id, ");
		builder.append(" a.req, ");
		builder.append(" to_char(a.create_time,'yyyy-MM-dd hh24:mi:ss') as create_time, ");
		builder.append(" a.county_id, ");
		builder.append(" a.prod_offer_code, ");
		builder.append(" a.prod_offer_name, ");
		builder.append(" a.goods_num, ");
		builder.append(" a.offer_price, ");
		builder.append(" a.real_offer_price, ");
		builder.append(" a.develop_point_code, ");
		builder.append(" a.develop_point_name, ");
		builder.append(" a.develop_code, ");
		builder.append(" a.develop_name, ");
		builder.append(" a.deal_office_type, ");
		builder.append(" a.channeltype, ");
		builder.append(" a.deal_office_id, ");
		builder.append(" a.deal_operator, ");
		builder.append(" a.deal_operator_name, ");
		builder.append(" a.deal_operator_num, ");
		builder.append(" a.develop_phone, ");
		builder.append(" a.ship_name, ");
		builder.append(" a.ship_tel, ");
		builder.append(" a.ship_addr, ");
		builder.append(" a.ship_tel2, ");
		builder.append(" a.intent_finish_time, ");
		builder.append(" a.lock_id, ");
		builder.append(" a.lock_name, ");
		builder.append(" a.allot_status, ");
		builder.append(" a.acceptname, ");
		builder.append(" a.acceptcert, ");
		builder.append(" a.acceptaddr, ");
		builder.append(" a.is_real_name, ");
		builder.append(" a.customer_name, ");
		builder.append(" a.cert_type, ");
		builder.append(" a.cert_num, ");
		builder.append(" a.cert_addr, ");
		builder.append(" a.cert_nation, ");
		builder.append(" a.cert_sex, ");
		builder.append(" a.cust_birthday, ");
		builder.append(" a.cert_issuedat, ");
		builder.append(" a.cert_expire_date, ");
		builder.append(" a.cert_effected_date, ");
		builder.append(" a.cust_tel, ");
		builder.append(" a.customer_type, ");
		builder.append(" a.cust_id, ");
		builder.append(" a.group_code, ");
		builder.append(" a.req_swift_num, ");
		builder.append(" a.check_type, ");
		builder.append(" a.acc_nbr, ");
		builder.append(" a.contract_month, ");
		builder.append(" a.lhscheme_id, ");
		builder.append(" a.pre_fee, ");
		builder.append(" a.advancepay, ");
		builder.append(" a.classid, ");
		builder.append(" a.lowcostpro, ");
		builder.append(" a.timedurpro, ");
		builder.append(" a.market_user_id, ");
		builder.append(" a.seed_user_id, ");
		builder.append(" a.is_no_modify, ");
		builder.append(" a.share_svc_num, ");
		builder.append(" a.mainnumber, ");
		builder.append(" a.in_mode_code, ");
		builder.append(" a.store_id, ");
		builder.append(" a.store_type, ");
		builder.append(" a.channel_id, ");
		builder.append(" a.member_id, ");
		builder.append(" a.imei, ");
		builder.append(" a.booking_num_tag, ");
		builder.append(" a.develop_type, ");
		builder.append(" a.develop_eparchy_code, ");
		builder.append(" a.develop_date, ");
		builder.append(" a.develop_manager_id, ");
		builder.append(" a.develop_manager_name, ");
		builder.append(" a.develop_nick_id, ");
		builder.append(" a.develop_nick_name, ");
		builder.append(" a.is_work_custom, ");
		builder.append(" a.activity_name, ");
		builder.append(" a.share_goods_id, ");
		builder.append(" a.cert_num2, ");
		builder.append(" nvl(c.cat_id,'') cat_id, ");
		builder.append(" nvl(c.name,'') cat_name ");
		builder.append("   FROM es_order_intent a,es_goods b,es_goods_cat c ");
		builder.append("    WHERE a.source_from='ECS' ");
		builder.append("   and a.order_id = '").append(order_id).append("' ");
		builder.append("   and a.goods_id=b.goods_id(+) ");
		builder.append("   and b.cat_id=c.cat_id(+) ");

		List ret = this.baseDaoSupport.queryForList(builder.toString());

		return ret;
	}

	/**
	 * 订单信息回填接口 针对总部蜂行动
	 * 
	 * @param order_id
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public BusiDealResult orderInfoBackfill(String order_id) throws Exception {
		BusiDealResult result = new BusiDealResult();

		String sql = "select q.contents,e.* from es_order_intent i left join es_order_extvtl e on i.order_id = e.order_id left join es_order_queue_his q on i.order_id = q.order_id where e.order_id is not null and i.order_id ='"
				+ order_id + "' and i.source_from='" + ManagerUtils.getSourceFrom()
				+ "' and i.source_system_type='100104' ";
		List<Map<String, Object>> intentList = baseDaoSupport.queryForLists(sql);
		if (intentList.size() != 1) {
			result.setError_code("0000");
			result.setError_msg("意向单信息不存在(跳过)");
			return result;
		}
		OrderInfoBackfillReq req = new OrderInfoBackfillReq();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		req.setNotNeedReqStrOrderId(order_id);
		Gson gson = new Gson();
		Map<String, Object> mapReq = new HashMap<String, Object>();
		Map<String, Object> mapVtl = intentList.get(0);
		mapReq = gson.fromJson(intentList.get(0).get("contents").toString(), mapReq.getClass());
		Map<String, Object> ORDER = (Map<String, Object>) mapReq.get("ORDER");
		ORDER_INFO_BACKFILL_REQ backfillReq = new ORDER_INFO_BACKFILL_REQ();
		Map<String, String> REQ_HEAD = new HashMap<String, String>();
		REQ_HEAD.put("IN_MODE_CODE", ORDER.get("IN_MODE_CODE") + "");// 接入渠道
		Map<String, Object> REQ_DATA = new HashMap<String, Object>();
		REQ_DATA.put("ORDER_ID", ORDER.get("ORDER_ID") + "");// 上游系统客户订单编号
		if (null != ORDER.get("ORDER_LINE")) {
			List<Map> ORDER_LINE = (List) ORDER.get("ORDER_LINE");
			if (ORDER_LINE.size() > 0) {
				REQ_DATA.put("ORDER_LINE_ID", ORDER_LINE.get(0).get("ORDER_LINE_ID"));// 上游系统业务订单编号

			}
		}
		if (null != ORDER.get("COMMON_INFO")) {
			List<Map> COMMON_INFO = (List) ORDER.get("COMMON_INFO");// 公共信息
			for (Map COMMON : COMMON_INFO) {
				String STAFF_ID = Const.getStrValue(COMMON, "STAFF_ID");// 操作员工
				REQ_DATA.put("DEAL_STAFF_ID", STAFF_ID);// 处理员工
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		REQ_DATA.put("DEAL_TIME", sdf.format(new Date()));// V10 处理时间2018-11-21
															// 10:14:39
		List<Map<String, String>> FEE_INFO = new ArrayList();
		// 暂不支持 多费用列表
		// Map<String, String> info = new HashMap<String, String>();
		// info.put("FEE_MODE", "0");// 费用类型：0-营业费用项，1-押金，2-预存
		// info.put("FEE_DESC", "");// 费用项描述
		// info.put("FEE_TYPE_CODE", "");// 费用项编码
		// info.put("OLDFEE", "0");// 应收费用（单位：分）
		// info.put("DEDUCT_MONEY", "0");// 最大减免金额（单位：分）
		// info.put("FEE", "0");// 实收金额（单位：分）
		// FEE_INFO.add(info);

		List<Map<String, String>> PARA = new ArrayList();
		// 列表
		Map<String, String> PRODUCE_TIME = new HashMap<String, String>();
		PRODUCE_TIME.put("PARA_TYPE", "11");// 信息类型：10：写卡信息,11:生产信息
		PRODUCE_TIME.put("PARA_ID", "PRODUCE_TIME");// 生产时间
		PRODUCE_TIME.put("PARA_VALUE", sdf.format(new Date()));
		PARA.add(PRODUCE_TIME);
		Map<String, String> PRODUCE_ORDER_STATE = new HashMap<String, String>();
		PRODUCE_ORDER_STATE.put("PARA_TYPE", "11");
		PRODUCE_ORDER_STATE.put("PARA_ID", "PRODUCE_ORDER_STATE");// 生产结果状态
		PRODUCE_ORDER_STATE.put("PARA_VALUE", "0");
		PARA.add(PRODUCE_ORDER_STATE);
		Map<String, String> PRODUCE_ORDER_ID = new HashMap<String, String>();
		PRODUCE_ORDER_ID.put("PARA_TYPE", "11");
		PRODUCE_ORDER_ID.put("PARA_ID", "PRODUCE_ORDER_ID");// 生产单号
		PRODUCE_ORDER_ID.put("PARA_VALUE",
				orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getActive_no());
		PARA.add(PRODUCE_ORDER_ID);

		if (FEE_INFO.size() > 0) {
			REQ_DATA.put("FEE_INFO", FEE_INFO);
		}
		REQ_DATA.put("PARA", PARA);// 回填信息节点*
		backfillReq.setREQ_HEAD(REQ_HEAD);
		backfillReq.setREQ_DATA(REQ_DATA);

		ORDER_INFO_BACKFILL_REQ ORDER_INFO_BACKFILL_REQ = new ORDER_INFO_BACKFILL_REQ();
		ORDER_INFO_BACKFILL_REQ.setREQ_HEAD(REQ_HEAD);
		ORDER_INFO_BACKFILL_REQ.setREQ_DATA(REQ_DATA);
		req.setORDER_INFO_BACKFILL_REQ(ORDER_INFO_BACKFILL_REQ);

		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderInfoBackfillRsp resp = client.execute(req, OrderInfoBackfillRsp.class);
		if (null != resp.getResultMsg()) {
			Map<String, Object> resultMsg = (Map<String, Object>) resp.getResultMsg();
			if (null != resultMsg.get("UNI_BSS_BODY")) {
				Map<String, Object> UNI_BSS_BODY = (Map<String, Object>) resultMsg.get("UNI_BSS_BODY");
				if (null != UNI_BSS_BODY.get("ORDER_INFO_BACKFILL_RSP")) {
					Map<String, String> ORDER_INFO_BACKFILL_RSP = (Map<String, String>) UNI_BSS_BODY
							.get("ORDER_INFO_BACKFILL_RSP");
					result.setError_code(ORDER_INFO_BACKFILL_RSP.get("CODE"));
					result.setError_msg(ORDER_INFO_BACKFILL_RSP.get("DESC"));
				}
			}
		} else {
			result.setError_code("9999");
			result.setError_msg("回填信息失败");
		}
		return result;
	}

	/**
	 * 短信模版页面
	 * 
	 * @param order_id
	 * @return
	 */
	public Page smsTemplateFormQuery(OrderQueryParams params, int pageNo, int pageSize) {
		String sql = " select t.sms_id, (select realname from es_adminuser where userid=t.creater_id) as creater_id, to_char(t.creater_time, 'yyyy-MM-dd HH24:mi:ss') as creater_time, (select pname from es_dc_public_ext where stype = 'SMS_STATUS' and pkey = t.status) as status, (select pname from es_dc_public_ext where stype = 'SMS_LEVEL' and pkey = t.sms_level) as sms_level, t.sms_template, t.is_delete from es_sms_template t where t.source_from='"
				+ ManagerUtils.getSourceFrom() + "' ";

		if (!StringUtils.isEmpty(params.getSms_id())) {
			sql += " and t.Sms_id in('" + params.getSms_id() + "')";
		}
		if (!StringUtils.isEmpty(params.getSms_level())) {
			sql += " and t.Sms_level in('" + params.getSms_level() + "')";
		}
		if (!StringUtils.isEmpty(params.getStatus())) {
			sql += " and t.status in('" + params.getStatus() + "')";
		}
		if (!StringUtils.isEmpty(params.getCreate_start())) {
			sql += " and t.creater_time >= to_date('" + params.getCreate_start() + "','yyyy-MM-dd HH24:mi:ss')";
		}
		if (!StringUtils.isEmpty(params.getCreate_end())) {
			sql += " and t.creater_time <= to_date('" + params.getCreate_end() + "','yyyy-MM-dd HH24:mi:ss')";
		}
		AdminUser user = ManagerUtils.getAdminUser();
		if (!"1".equals(user.getUserid()) && "0".equals(params.getSms_level())) {
			sql += " and t.creater_id = '" + user.getUserid() + "' ";
		}
		if (!"1".equals(user.getUserid())) {
			sql += " and t.is_delete='0' ";
		}
		sql += " order by t.is_delete,t.status,t.creater_time desc ";
		Page webpage = this.baseDaoSupport.queryForPage(sql.toString(), pageNo, pageSize, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sms_id", rs.getString("sms_id"));
				map.put("creater_id", rs.getString("creater_id"));
				map.put("creater_time", rs.getString("creater_time"));
				map.put("status", rs.getString("status"));
				map.put("sms_level", rs.getString("sms_level"));
				map.put("sms_template", rs.getString("sms_template"));
				map.put("is_delete", rs.getString("is_delete"));
				return map;
			}
		});
		return webpage;
	}

	/**
	 * 选择短信模版查询
	 */
	public Page selectSMSFormQuery(OrderQueryParams params, int pageNo, int pageSize) {
		String sql = " select t.sms_id, (select realname from es_adminuser where userid=t.creater_id) as creater_id, to_char(t.creater_time, 'yyyy-MM-dd HH24:mi:ss') as creater_time, (select pname from es_dc_public_ext where stype = 'SMS_STATUS' and pkey = t.status) as status, (select pname from es_dc_public_ext where stype = 'SMS_LEVEL' and pkey = t.sms_level) as sms_level, t.sms_template, t.is_delete from es_sms_template t where t.source_from='"
				+ ManagerUtils.getSourceFrom() + "' ";

		// if (!StringUtils.isEmpty(params.getSms_id())) {
		// sql += " and t.Sms_id ='" + params.getSms_id() + "'";
		// }
		// if (!StringUtils.isEmpty(params.getSms_level())) {
		// sql += " and t.Sms_level ='" + params.getSms_level() + "'";
		// }
		// if (!StringUtils.isEmpty(params.getStatus())) {
		// sql += " and t.status ='" + params.getStatus() + "'";
		// }
		if (!StringUtils.isEmpty(params.getCreate_start())) {
			sql += " and t.creater_time >= to_date('" + params.getCreate_start() + "','yyyy-MM-dd HH24:mi:ss')";
		}
		if (!StringUtils.isEmpty(params.getCreate_end())) {
			sql += " and t.creater_time <= to_date('" + params.getCreate_end() + "','yyyy-MM-dd HH24:mi:ss')";
		}
		if (!StringUtils.isEmpty(params.getType())) {
			if (!"unicom".equals(params.getType())) {
				sql += " and t.Sms_level ='1'";
			}
		}
		AdminUser user = ManagerUtils.getAdminUser();
		if (!"1".equals(user.getUserid())) {
			sql += " and t.creater_id = '" + user.getUserid() + "' ";
		}
		if (!"1".equals(user.getUserid())) {
			sql += " and t.is_delete='0' ";
		}
		sql += " and t.status ='0'";
		sql += " order by t.Sms_level,t.creater_time desc ";
		Page webpage = this.baseDaoSupport.queryForPage(sql.toString(), pageNo, pageSize, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sms_id", rs.getString("sms_id"));
				map.put("creater_id", rs.getString("creater_id"));
				map.put("creater_time", rs.getString("creater_time"));
				map.put("status", rs.getString("status"));
				map.put("sms_level", rs.getString("sms_level"));
				map.put("sms_template", rs.getString("sms_template"));
				map.put("is_delete", rs.getString("is_delete"));
				return map;
			}
		});
		return webpage;
	}

	public List<Map<String, Object>> getSmsStatusList() {
		String sql = "select a.pkey value,a.pname value_desc from es_dc_public_ext a where a.stype='SMS_STATUS'";
		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql);
		return list;
	}

	public List<Map<String, Object>> getSmsLevelList() {
		String sql = "select a.pkey value,a.pname value_desc from es_dc_public_ext a where a.stype='SMS_LEVEL'";
		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql);
		return list;
	}

	/**
	 * 新增短信模版
	 * 
	 * @param order_id
	 */
	public String addSMS(String sms_template) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		AdminUser adminUser = ManagerUtils.getAdminUser();
		String user_id = "1";
		if (adminUser != null) {
			user_id = adminUser.getUserid();
		}
		String sms_id = this.baseDaoSupport.getSequences("O_OUTCALL_LOG");
		map.put("sms_id", sms_id);
		map.put("creater_id", user_id);
		map.put("creater_time", new Date());
		map.put("sms_template", sms_template);
		map.put("status", "0");
		map.put("is_delete", "0");
		map.put("sms_level", "0");
		map.put("source_from", ManagerUtils.getSourceFrom());
		baseDaoSupport.insert("es_sms_template", map);

		map.clear();
		map.put("sms_id", sms_id);
		map.put("op_id", user_id);
		map.put("action", "A");
		map.put("sms_template", "");
		map.put("sms_template_new", sms_template);
		addSmsLogs(map);
		return "";
	}

	/**
	 * 修改短信模版
	 * 
	 * @param order_id
	 */
	public String editSMS(Map<String, Object> map) throws Exception {
		Map<String, Object> sms = getSMSDetail(map.get("sms_id") + "");
		if (!"正常".equals(sms.get("status") + "")) {
			return "模版状态异常不能修改";
		}
		Map<String, Object> fieldsMap = new HashMap<String, Object>();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		fieldsMap.put("sms_template", map.get("sms_template_new"));
		whereMap.put("sms_id", map.get("sms_id"));
		daoSupport.update("es_sms_template", fieldsMap, whereMap);

		map.put("action", "M");
		addSmsLogs(map);
		return "";
	}

	public String banSMS(String sms_id) throws Exception {
		Map<String, Object> sms = getSMSDetail(sms_id);
		if (!"正常".equals(sms.get("status") + "")) {
			return "模版状态异常不能禁用";
		}
		Map<String, Object> fieldsMap = new HashMap<String, Object>();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		fieldsMap.put("status", "1");
		whereMap.put("sms_id", sms_id);
		daoSupport.update("es_sms_template", fieldsMap, whereMap);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("action", "B");
		map.put("sms_id", sms_id);
		addSmsLogs(map);
		return "";
	}

	public String pickSMS(String sms_id) throws Exception {
		Map<String, Object> sms = getSMSDetail(sms_id);
		if (!"禁用".equals(sms.get("status") + "")) {
			return "模版状态异常不能禁用";
		}
		Map<String, Object> fieldsMap = new HashMap<String, Object>();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		fieldsMap.put("status", "0");
		whereMap.put("sms_id", sms_id);
		daoSupport.update("es_sms_template", fieldsMap, whereMap);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("action", "P");
		map.put("sms_id", sms_id);
		addSmsLogs(map);
		return "";
	}

	public String deleteSMS(String sms_id) throws Exception {
		Map<String, Object> fieldsMap = new HashMap<String, Object>();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		fieldsMap.put("is_delete", "1");
		fieldsMap.put("status", "1");
		whereMap.put("sms_id", sms_id);
		daoSupport.update("es_sms_template", fieldsMap, whereMap);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("action", "D");
		map.put("sms_id", sms_id);
		addSmsLogs(map);
		return "";
	}

	/**
	 * 新增短信模版操作记录
	 * 
	 * @return
	 */
	public void addSmsLogs(Map<String, Object> map) throws Exception {

		if (null == map.get("op_id")) {
			AdminUser adminUser = ManagerUtils.getAdminUser();
			String user_id = "1";
			if (adminUser != null) {
				user_id = adminUser.getUserid();
			}
			map.put("op_id", user_id);
		}

		map.put("log_id", this.baseDaoSupport.getSequences("O_OUTCALL_LOG"));
		// map.put("sms_id", "");
		// map.put("action", "");
		// map.put("sms_template", "");
		// map.put("sms_template_new", "");
		map.put("create_time", new Date());
		map.put("source_from", ManagerUtils.getSourceFrom());
		baseDaoSupport.insert("es_sms_handle_logs", map);
	}

	/**
	 * 新增短信发送记录
	 * 
	 * @return
	 */
	public void addSmsSendLogs(Map<String, Object> map) throws Exception {

		if (null == map.get("userid")) {
			AdminUser adminUser = ManagerUtils.getAdminUser();
			String user_id = "1";
			String user_name = "系统管理员";
			if (adminUser != null) {
				user_id = adminUser.getUserid();
				user_name = adminUser.getRealname();
			}
			map.put("userid", user_id);
			map.put("username", user_name);
		}

		// map.put("order_id", "");
		// map.put("caller", "");//主叫/短信发送号码
		// map.put("callee", "");//被叫/短信接收号码
		// map.put("sms_data", "");
		// map.put("userid", "");
		// map.put("username", "");
		map.put("log_id", this.baseDaoSupport.getSequences("O_OUTCALL_LOG"));
		map.put("create_time", new Date());
		map.put("source_from", ManagerUtils.getSourceFrom());
		baseDaoSupport.insert("es_order_call_sms_logs", map);
	}

	/**
	 * 短信模版信息
	 * 
	 * @return
	 */
	public Map<String, Object> getSMSDetail(String sms_id) throws Exception {
		String sql = "select t.sms_id, (select realname from es_adminuser where userid=t.creater_id) as creater_id, to_char(t.creater_time, 'yyyy-MM-dd HH24:mi:ss') as creater_time, (select pname from es_dc_public_ext where stype = 'SMS_STATUS' and pkey = t.status) as status, (select pname from es_dc_public_ext where stype = 'SMS_LEVEL' and pkey = t.sms_level) as sms_level, t.sms_template, t.is_delete from es_sms_template t where t.source_from = '"
				+ ManagerUtils.getSourceFrom() + "' and t.sms_id='" + sms_id + "'";
		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 短信操作记录
	 * 
	 * @param sms_id
	 * @return
	 */
	public List<Map<String, Object>> getSmsLogs(String sms_id) throws Exception {
		String sql = "select (select pname from es_dc_public_ext where stype = 'SMS_ACTION' and pkey = t.action) as action, (select realname from es_adminuser where userid=t.op_id) as op_id, to_char(t.create_time, 'yyyy-MM-dd HH24:mi:ss') as create_time, t.sms_template, t.sms_template_new from es_sms_handle_logs t where t.source_from = '"
				+ ManagerUtils.getSourceFrom() + "' and t.sms_id='" + sms_id + "'";
		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql);
		return list;
	}

	/**
	 * 短信发送记录
	 * 
	 * @param sms_id
	 * @return
	 */
	public List<Map<String, Object>> getSmsSendLogs(String order_id) throws Exception {
		String sql = "select t.order_id,t.callee,t.caller,to_char(t.create_time, 'yyyy-MM-dd HH24:mi:ss') as create_time,t.sms_data,t.userid,t.username from es_order_call_sms_logs t where t.source_from = '"
				+ ManagerUtils.getSourceFrom() + "' and t.order_id='" + order_id + "'";
		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql);
		return list;
	}

	/**
	 * 发送短信内容
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String sendSMS(Map<String, Object> map) throws Exception {
		String msg = "";
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		if ("unicom".equals(map.get("type"))) {
			AopSmsSendReq smsSendReq = new AopSmsSendReq();
			// smsSendReq.setBill_num("10010");// 短信发送号码
			smsSendReq.setBill_num(cacheUtil.getConfigInfo("SEND_SMS_BILL_NUM"));// 短信发送号码
			smsSendReq.setService_num(map.get("ship_tel") + "");// 接受号码--省内联通号码
			smsSendReq.setSms_data(map.get("sms_template") + "");
			AopSmsSendResp sms_resp = client.execute(smsSendReq, AopSmsSendResp.class);
			if (StringUtil.isEmpty(sms_resp.getCode()) || !"00000".equals(sms_resp.getCode())) {
				msg = sms_resp.getMsg();
			}
		} else if ("other".equals(map.get("type"))) {
			CmcSmsSendReq cmcSendReq = new CmcSmsSendReq();
			cmcSendReq.setNotNeedReqStrOrderId(map.get("order_id") + "");
			cmcSendReq.setMessageContent(map.get("sms_template") + "");
			cmcSendReq.setUserNumber(map.get("ship_tel") + "");
			CmcSmsSendResp cmcSendResp = client.execute(cmcSendReq, CmcSmsSendResp.class);
			if (StringUtil.isEmpty(cmcSendResp.getResultcode()) || !"0".equals(cmcSendResp.getResultcode())) {
				DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
				List<Map> list = dcPublicCache.getList("DIC_YXT_SMS_RESULT");
				msg = "接口异常";
				for (Map m : list) {
					String pkey = (String) m.get("pkey");
					if (cmcSendResp.getResultcode().equals(pkey)) {
						msg = (String) m.get("pname");
						break;
					}
				}
			}
		} else {
			msg = "号码类型异常";
		}
		if (StringUtils.isEmpty(msg)) {
			Map<String, Object> log = new HashMap<String, Object>();
			log.put("order_id", map.get("order_id"));
			// log.put("caller", "");//主叫/短信发送号码
			log.put("callee", map.get("ship_tel"));// 被叫/短信接收号码
			log.put("sms_data", map.get("sms_template"));
			// log.put("userid", "");
			// log.put("username", "");
			addSmsSendLogs(log);
		}
		return msg;
	}

	/**
	 * 获取订单客户联系号码
	 * 
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	public String getOrderShipTel(String order_id) throws Exception {
		String ship_tel = "";
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		if (null != orderTree && !StringUtil.isEmpty(orderTree.getOrder_id())) {
			ship_tel = orderTree.getOrderDeliveryBusiRequests().get(0).getShip_tel();
			if (StringUtil.isEmpty(ship_tel) || ship_tel.length() != 11) {
				ship_tel = orderTree.getOrderDeliveryBusiRequests().get(0).getShip_mobile();
			}
			if (StringUtil.isEmpty(ship_tel) || ship_tel.length() != 11) {
				ship_tel = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "receiver_mobile");
			}
		}
		if (StringUtil.isEmpty(ship_tel) || ship_tel.length() != 11) {
			String sql = "select t.ship_tel from es_order_intent t where t.source_from = '"
					+ ManagerUtils.getSourceFrom() + "' and t.order_id='" + order_id + "'";
			List<Map<String, String>> list = this.baseDaoSupport.queryForList(sql);
			if (list.size() == 1) {
				ship_tel = list.get(0).get("ship_tel");
			}
		}
		return ship_tel;
	}

	/**
	 * 是否为联通号码
	 * 
	 * @param ship_tel
	 * @return
	 * @throws Exception
	 */
	public Boolean isUnicom(String ship_tel) throws Exception {
		String UNICOM_NUM_SECTION = cacheUtil.getConfigInfo("UNICOM_NUM_SECTION");
		String[] unicom_num_section = UNICOM_NUM_SECTION.split(",");
		boolean flag = false;
		for (int i = 0; i < unicom_num_section.length; i++) {
			if (unicom_num_section[i].equals(ship_tel.substring(0, unicom_num_section[i].length()))) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public String openSmsSend() throws Exception {
		try {
			Map<String, Object> fieldsMap = new HashMap<String, Object>();
			Map<String, Object> whereMap = new HashMap<String, Object>();
			fieldsMap.put("col1", "open");
			AdminUser adminUser = ManagerUtils.getAdminUser();
			whereMap.put("userid", adminUser.getUserid());
			daoSupport.update("es_adminuser", fieldsMap, whereMap);
			adminUser.setCol1("open");
			WebSessionContext sessonContext = ThreadContextHolder.getHttpSessionContext();
			sessonContext.setAttribute("admin_user_key", adminUser);
		} catch (Exception e) {
			return e.getMessage();
		}
		return "";
	}

	public String colseSmsSend() throws Exception {
		try {
			Map<String, Object> fieldsMap = new HashMap<String, Object>();
			Map<String, Object> whereMap = new HashMap<String, Object>();
			fieldsMap.put("col1", "colse");
			AdminUser adminUser = ManagerUtils.getAdminUser();
			whereMap.put("userid", adminUser.getUserid());
			daoSupport.update("es_adminuser", fieldsMap, whereMap);
			adminUser.setCol1("colse");
			WebSessionContext sessonContext = ThreadContextHolder.getHttpSessionContext();
			sessonContext.setAttribute("admin_user_key", adminUser);
		} catch (Exception e) {
			return e.getMessage();
		}
		return "";
	}

	/***
	 * 总部蜂行动处理
	 */
	@Override
	public String beeActionHandle(String order_id) throws Exception {
		OrderMakeupResp resp = new OrderMakeupResp();
		OrderDistributeCtnStandardResp resp_dist = new OrderDistributeCtnStandardResp();
		OrderPreCreateCtnStandardResp resp_order_pre = new OrderPreCreateCtnStandardResp();
		OrderMakeupReq requ = new OrderMakeupReq();
		ZteClient client = null;
		String respJsonStr = "";// 返回json字符串
		String order_type = ""; // 订单类型
		String num = ""; // 处理次数
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");

		// String sql = cacheUtil.getConfigInfo("handle_intent_min_update");
		String sqlByOrderId = cacheUtil.getConfigInfo("handle_intent_min_update_by_orderId");
		String updatesql = cacheUtil.getConfigInfo("order_intent_min_update");
		String flag = cacheUtil.getConfigInfo("order_intent_min_timer_on");
		List<?> queryList = null;
		double money = 0;
		String PAY_TAG = "";
		sqlByOrderId = sqlByOrderId.replace("{order_id}", order_id);
		logger.info("BeeAction handle by web,order_id:总部蜂行动开始处理，订单号：" + order_id);
		queryList = baseDaoSupport.queryForList(sqlByOrderId);
		Map<?, ?> query_map = (Map<?, ?>) queryList.get(0);
		order_type = (String) query_map.get("order_type");
		String requJsonStr = (String) query_map.get("req");

		try {
			if (!StringUtils.isEmpty(requJsonStr)) {
				Map<?, ?> map = (Map<?, ?>) JSON.parse(requJsonStr);
				if (!map.isEmpty()) {
					if ("100104".equals(order_type)) {// 总部蜂行动
						String desc = intentUtil.cheakBee(map);
						String pay_money = "";
						Map<?, ?> ORDER = (Map<?, ?>) map.get("ORDER");
						PAY_TAG = Const.getStrValue(ORDER, "PAY_TAG");
						if (null != ORDER.get("FEEPAY_INFO")) {
							List<Map> FEEPAY_INFO = (List) ORDER.get("FEEPAY_INFO");// 支付
							for (Map FEEPAY : FEEPAY_INFO) {
								pay_money = Const.getStrValue(FEEPAY, "MONEY");// 支付金额
							}
						}
						order_id = Const.getStrValue(ORDER, "ORDER_ID");
						// 总部蜂行动处理次数
						String done_num_sql = cacheUtil.getConfigInfo("order_intent_done_num");
						done_num_sql = done_num_sql.replace("{order_id}", order_id);
						int done_num = baseDaoSupport.queryForInt(done_num_sql);
						done_num++;
						num = String.valueOf(done_num);
						// 总部蜂行动中间表更新订单返回状态
						updatesql = updatesql.replace("t.exception_desc = '{exception_desc}',", "");
						updatesql = updatesql.replace("t.done_num='{num}',", "");
						updatesql = updatesql.replace("{done_status}", "1");
						updatesql = updatesql.replace("{order_id}", order_id);
						// 执行更新语句
						baseDaoSupport.execute(updatesql);
						money = Double.valueOf(pay_money.toString());
						if (StringUtils.isEmpty(desc)) {
							/*
							 * 根据支付金额是否大于0，判断订单的生产模式
							 */
							if (money > 0 && "1".equals(PAY_TAG) && "1".equals(flag)) {
								logger.info("总部蜂行动线上生产模式处理，订单号：" + order_id);
								// 线上生产模式，拼接报文字段
								JSONObject makeUp = intentUtil.getBeeMakeupMessage(requJsonStr);
								// 解析请求参数
								JSONObject requJson = JSONObject.fromObject(makeUp);
								// 请求入参
								JSONObject orderInfoJson = requJson.getJSONObject("mall_req");
								String inJson = orderInfoJson.toString();

								if (org.apache.commons.lang.StringUtils.isBlank(inJson)) {
									try {
										throw new Exception("传入订单信息为空");
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								logger.info("by web beeAction timer begin：调用makeupReq");

								requ = (OrderMakeupReq) JSONObject.toBean(orderInfoJson, OrderMakeupReq.class);
								client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
								resp = client.execute(requ, OrderMakeupResp.class);
								
								//向es_order_file表中存入总部蜂行动的照片链接
								if("0".equals(resp.getResp_code())) {
									if (null != ORDER.get("ORDER_LINE")) {
										List ORDER_LINE = (List) ORDER.get("ORDER_LINE");
										for (Object obj : ORDER_LINE) {
											Map<Object, Object> line_map = (Map<Object, Object>) obj;
											Map<Object,Object> PIC_DATAS=(Map) line_map.get("PIC_DATAS");
											if (PIC_DATAS != null && PIC_DATAS.size()>0) {
												//获取订单树插入缓存
												String orderId = resp.getOrder_id();
												OrderTreeBusiRequest orderTree =  CommonDataFactory.getInstance().getOrderTree(orderId);
												List<OrderFileBusiRequest> orderFileBusiRequests = orderTree.getOrderFileBusiRequests();
												//从配置表中获取总部蜂照片地址前缀url
										        String bee_photo_url = cacheUtil.getConfigInfo("bee_photo_url");
												//获取插入信息
												int fileNameNum = 1;
												Map<Object, Object> picMap = new HashMap<Object, Object>();
												picMap.put("order_id", orderId);
												picMap.put("operator_id", "1");
												picMap.put("status", "1");												
												picMap.put("tache_code", "B");
												picMap.put("source_from", "ECS");
												SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式												
												for (Object key:PIC_DATAS.keySet()) {
													
													String url = bee_photo_url+(String) PIC_DATAS.get(key);
													String file_type = ((String) PIC_DATAS.get(key)).split("\\.")[1].toString();
													String fileName = orderId+"_0"+fileNameNum+"."+file_type;
													fileNameNum++;
													String date = df.format(new Date());// new Date()为获取当前系统时间													
													picMap.put("file_type", file_type);
													picMap.put("file_id", fileName);
													picMap.put("file_path", url);
													picMap.put("file_name", fileName);
													picMap.put("create_time", date);													
													try {
														baseDaoSupport.insert("es_order_file", picMap);

													} catch (Exception e) {
														e.printStackTrace();
													}
													OrderFileBusiRequest orderFileBusiRequest =new OrderFileBusiRequest();
													orderFileBusiRequest.setOrder_id(orderId);
													orderFileBusiRequest.setFile_id(fileName);
													orderFileBusiRequest.setFile_type(file_type);
													orderFileBusiRequest.setFile_path(url);
													orderFileBusiRequest.setFile_name(fileName);
													orderFileBusiRequest.setCreate_time(date);
													orderFileBusiRequest.setOperator_id("1");
													orderFileBusiRequest.setStatus("1");
													orderFileBusiRequest.setTache_code("B");
													orderFileBusiRequest.setSource_from("ECS");
													orderFileBusiRequests.add(orderFileBusiRequest);													
												}
												//更新订单照片上传状态
												String updateSql = "update es_order_ext set if_send_photos = '1' where order_id = '"+orderId+"'";
												baseDaoSupport.execute(updateSql);
												OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
												orderExt.setIf_Send_Photos("1");
												//更新订单树缓存
												INetCache cache = (INetCache) com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
												cache.set(RequestStoreManager.NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY+"order_id"+ orderId,orderTree, RequestStoreManager.time);
											}
										}
									}
									
								}
								

							} else {
								// 线下生产模式
								logger.info("总部蜂行动线下生产模式处理，订单号：" + order_id);
								OrderDistributeCtnStandardReq requ_dist = new OrderDistributeCtnStandardReq();
								requ_dist.setMapReq(map);
								logger.info("by web beeAction timer begin：调用订单下发接口req");
								client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
								resp_dist = client.execute(requ_dist, OrderDistributeCtnStandardResp.class);
							}
						} else {
							resp_dist.setRESP_CODE("0");
							resp_dist.setRESP_DESC(desc);
						}
					} else {// 京东便利店
						String desc = intentUtil.cheakJD(map);
						logger.info("JDAction handle begin：京东便利店订单开始处理");
						Map<?, ?> REQ_HEAD = (Map<?, ?>) map.get("REQ_HEAD");
						// order_id = Const.getStrValue(REQ_HEAD, "TOUCH_ORDER_ID");
						// 京东便利店处理次数
						String done_num_sql = cacheUtil.getConfigInfo("order_intent_done_num");
						done_num_sql = done_num_sql.replace("{order_id}", order_id);
						int done_num = baseDaoSupport.queryForInt(done_num_sql);
						done_num++;
						num = String.valueOf(done_num);
						// 总部蜂行动中间表更新订单返回状态
						updatesql = updatesql.replace("t.exception_desc = '{exception_desc}',", "");
						updatesql = updatesql.replace("t.done_num='{num}',", "");
						updatesql = updatesql.replace("{done_status}", "1");
						updatesql = updatesql.replace("{order_id}", order_id);
						// 执行更新语句
						baseDaoSupport.execute(updatesql);
						if (StringUtils.isEmpty(desc)) {
							OrderPreCreateCtnStandardReq requ_pre = new OrderPreCreateCtnStandardReq();
							requ_pre.setMapReq(map);
							client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
							resp_order_pre = client.execute(requ_pre, OrderPreCreateCtnStandardResp.class);
						} else {
							resp_order_pre.setCODE("1002");
							resp_order_pre.setDESC(desc);
						}
					}
				} else {
					resp_dist.setRESP_CODE("0");
					resp_dist.setRESP_DESC("请求报文参数异常");
				}
			} else {
				resp_dist.setRESP_CODE("0");
				resp_dist.setRESP_DESC("请求报文为空");
			}
		} catch (Exception e) {
			resp_dist.setRESP_CODE("0");
			resp_dist.setRESP_DESC("create order faile:" + (e.getMessage() == null ? e.toString() : e.getMessage()));
			e.printStackTrace();
			logger.info(e, e);
		}
		//
		updatesql = cacheUtil.getConfigInfo("order_intent_min_update");
		Map<String, Object> respMap = new HashMap<String, Object>();
		if ("1".equals(resp_dist.getRESP_CODE())) {
			// respMap.put("DATA", resp_dist.getDATA());
			respJsonStr = resp_dist.getRESP_DESC();
			updatesql = updatesql.replace("{order_id}", order_id);
			updatesql = updatesql.replace("{done_status}", "2");
			updatesql = updatesql.replace("{num}", num);
			updatesql = updatesql.replace("{exception_desc}", resp_dist.getRESP_DESC());
			// 执行更新语句
			baseDaoSupport.execute(updatesql);
			// 向中间表历史表插入数据
			String copysql = cacheUtil.getConfigInfo("order_intent_min_his_copy");
			copysql = copysql.replace("{order_id}", order_id);
			baseDaoSupport.execute(copysql);
			// 删除中间表数据
			String deletesql = cacheUtil.getConfigInfo("order_intent_min_delete");
			deletesql = deletesql.replace("{order_id}", order_id);
			baseDaoSupport.execute(deletesql);
			// respMap.put("RESP_CODE", resp_dist.getRESP_CODE());
			respMap.put("RESP_DESC", resp_dist.getRESP_DESC());
		} else if ("0".equals(resp.getResp_code())) {
			// respMap.put("DATA", resp.getDATA());
			respJsonStr = resp.getResp_msg();
			updatesql = updatesql.replace("{order_id}", order_id);
			updatesql = updatesql.replace("{done_status}", "2");
			updatesql = updatesql.replace("{num}", num);
			updatesql = updatesql.replace("{exception_desc}", resp.getResp_msg());
			// 执行更新语句
			baseDaoSupport.execute(updatesql);
			// 向中间表历史表插入数据
			String copysql = cacheUtil.getConfigInfo("order_intent_min_his_copy");
			copysql = copysql.replace("{order_id}", order_id);
			baseDaoSupport.execute(copysql);
			// 删除中间表数据
			String deletesql = cacheUtil.getConfigInfo("order_intent_min_delete");
			deletesql = deletesql.replace("{order_id}", order_id);
			baseDaoSupport.execute(deletesql);
			// respMap.put("RESP_CODE", resp.getResp_code());
			respMap.put("RESP_DESC", resp.getResp_msg());
		} else if ("0000".equals(resp_order_pre.getCODE())) {
			respJsonStr = resp_order_pre.getDESC();
			// respMap.put("DATA", resp_order_pre.getDATA());
			updatesql = updatesql.replace("{order_id}", order_id);
			updatesql = updatesql.replace("{done_status}", "2");
			updatesql = updatesql.replace("{num}", num);
			updatesql = updatesql.replace("{exception_desc}", resp_order_pre.getDESC());
			// 执行更新语句
			baseDaoSupport.execute(updatesql);
			// 向中间表历史表插入数据
			String copysql = cacheUtil.getConfigInfo("order_intent_min_his_copy");
			copysql = copysql.replace("{order_id}", order_id);
			baseDaoSupport.execute(copysql);
			// 删除中间表数据
			String deletesql = cacheUtil.getConfigInfo("order_intent_min_delete");
			deletesql = deletesql.replace("{order_id}", order_id);
			baseDaoSupport.execute(deletesql);
			// respMap.put("RESP_CODE", resp_order_pre.getCODE());
			respMap.put("RESP_DESC", resp_order_pre.getDESC());
		} else {
			updatesql = updatesql.replace("{num}", num);
			updatesql = updatesql.replace("{done_status}", "3");
			if ("100104".equals(order_type)) {
				updatesql = updatesql.replace("{exception_desc}", resp_dist.getRESP_DESC());
				respJsonStr = resp_dist.getRESP_DESC();
				respMap.put("RESP_CODE", resp_dist.getRESP_DESC());
				// respMap.put("RESP_DESC", resp_dist.getRESP_DESC());
			} else if ("100104".equals(order_type) && money > 0 && "1".equals(PAY_TAG)) {
				updatesql = updatesql.replace("{exception_desc}", resp.getResp_msg());
				respJsonStr = resp.getResp_msg();
				// respMap.put("RESP_CODE", resp.getResp_code());
				respMap.put("RESP_DESC", resp.getResp_msg());
			} else {
				updatesql = updatesql.replace("{exception_desc}", resp_order_pre.getDESC());
				respJsonStr = resp_order_pre.getDESC();
				// respMap.put("RESP_CODE", resp_order_pre.getCODE());
				respMap.put("RESP_DESC", resp_order_pre.getDESC());
			}
			updatesql = updatesql.replace("{order_id}", order_id);
			baseDaoSupport.execute(updatesql);
		}
		// respMap.put("RESP_CODE", resp.getRESP_CODE());
		// respMap.put("RESP_DESC", resp.getRESP_DESC());
		// 将返回数据转成json格式的字符串
		respJsonStr = JSONObject.fromObject(respMap).toString();
		// 返回接口调用结果
		logger.info("[OrderDistributeServlet]-response:" + respJsonStr);
		return respJsonStr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String smsSendHandle(String sms_id) throws Exception {
		String respJsonStr = "操作成功";// 返回json字符串
		String selecBySmsIdSql = "select * from es_order_mid_sms a where a.source_from = 'ECS' and a.sms_id = '"
				+ sms_id + "'";
		List<?> listSms = baseDaoSupport.queryForListNoSourceFrom(selecBySmsIdSql);
		if (listSms != null && listSms.size() > 0) {
			CommCaller caller = new CommCaller();
			Map<String, String> map = (Map<String, String>) listSms.get(0);
			String service_num = map.get("service_num");// 接收号码
			String bill_num = map.get("bill_num");
			String sms_data = map.get("sms_data");// 发送内容
			String sms_flag = map.get("sms_flag");// 0：短内容 1：长内容
			String sms_type = map.get("sms_type");// 短信类型（sms本网，cmc异网）
			String order_id = map.get("order_id");

			if ("sms".equals(sms_type)) {// sms是本网
				AopSmsSendReq sendReq = new AopSmsSendReq();
				AopSmsSendResp resp = new AopSmsSendResp();
				sendReq.setBill_num(bill_num);// 短信发送号码
				sendReq.setService_num(service_num);// 接受号码--省内联通号码
				sendReq.setSms_data(sms_data);
				sendReq.setSms_flag(sms_flag);

				Map<String, Object> param = new HashMap<String, Object>();
				try {
					BeanUtils.bean2MapForAiPBSS(param, sendReq);

					String method = cacheUtil.getConfigInfo("SMS_METHOD");
					if (!StringUtil.isEmpty(method) && EcsOrderConsts.NEW_SMS_SEND.equals(method)) {
						param.remove("sms_flag");
						resp = (AopSmsSendResp) caller.invoke(EcsOrderConsts.NEW_SMS_SEND, param);
					} else {
						resp = (AopSmsSendResp) caller.invoke(EcsOrderConsts.ECAOP_SMS_SEND, param);
					}
					if (resp != null && "00000".equals(resp.getCode())) {// 成功
						 String delSql = "delete from es_order_mid_sms a where a.sms_id = '"+sms_id +"'";
						 baseDaoSupport.execute(delSql);
						 map.put("send_status", "1");
						 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
						 map.put("done_time", df.format(new Date()));
							map.put("exception_desc", "发送成功");
						 baseDaoSupport.insertByMap("es_order_his_sms", map);
					} else {
						//发送失败：1，中间表记录失败原因，2，记录发送次数，3，记录发送时间。4,发送状态改为发送失败
						
						String sql = "select send_num from es_order_mid_sms where sms_id = '"+sms_id +"'";
						int send_num = baseDaoSupport.queryForInt(sql);
						send_num = send_num + 1;
						String exception_desc = resp.getMsg();
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
						String updateSql = "update es_order_mid_sms set exception_desc = '" + exception_desc +"',done_time = to_date('"+df.format(new Date())+
								"','yyyy-mm-dd hh24:mi:ss'),send_num = "+send_num+",send_status = '2' where sms_id = '"+sms_id +"'";
						baseDaoSupport.execute(updateSql);
						respJsonStr = resp.getError_msg();
					}
				} catch (Exception e) {// 调用接口失败
					resp = new AopSmsSendResp();
					resp.setError_msg(e.getMessage());
					e.printStackTrace();
				}
			} else if ("cmc".equals(sms_type)) {// 1是本网
				CmcSmsSendReq req = new CmcSmsSendReq();
				CmcSmsSendResp cmcSendResp = new CmcSmsSendResp();
				String spCode = cacheUtil.getConfigInfo("YXT_spCode");
				String userName = cacheUtil.getConfigInfo("YXT_userName");
				String Password = cacheUtil.getConfigInfo("YXT_Password");
				// String key = cacheUtil.getConfigInfo("YXT_KEY");
				req.setSpCode(spCode);
				req.setLoginName(userName);
				req.setPassword(Password);
				req.setNotNeedReqStrOrderId(order_id);
				req.setUserNumber(service_num);// 接收号码
				req.setMessageContent(sms_data);
				try {
					req.setSerialNumber(DateUtil.getTime7() + "000");
				} catch (FrameException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				req.setF("1");
				Map<String, Object> param = new HashMap<String, Object>();

				try {
					param.put("SpCode", req.getSpCode());
					param.put("LoginName", req.getLoginName());
					param.put("Password", req.getPassword());
					param.put("MessageContent", URLEncoder.encode(req.getMessageContent(), "gbk"));
					param.put("UserNumber", req.getUserNumber());
					param.put("SerialNumber", req.getSerialNumber());
					param.put("f", req.getF());
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
				cmcSendResp = (CmcSmsSendResp) caller.invoke("yeion.user.message.send", param);
				if (!StringUtil.isEmpty(cmcSendResp.getResultcode()) && "0".equals(cmcSendResp.getResultcode())) {// 成功
					String delSql = "delete from es_order_mid_sms a where a.sms_id = '"+sms_id +"'";
					baseDaoSupport.execute(delSql);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					map.put("done_time", df.format(new Date()));
					map.put("send_status", "1");
					map.put("exception_desc", "发送成功");
					baseDaoSupport.insertByMap("es_order_his_sms", map);
				} else {
					//发送失败：1，中间表记录失败原因，2，记录发送次数，3，记录发送时间。4,发送状态改为发送失败
					String msg = "";
					List<Map> list = this.getConsts("DIC_YXT_SMS_RESULT");
					for (Map m : list) {
						String pkey = (String) m.get("pkey");
						if (cmcSendResp.getResultcode().equals(pkey)) {
							msg = (String) m.get("pname");
							break;
						}
					}
					String sql = "select send_num from es_order_mid_sms where sms_id = '"+sms_id +"'";
					int send_num = baseDaoSupport.queryForInt(sql);
					send_num = send_num + 1;
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					String updateSql = "update es_order_mid_sms set exception_desc = '" + msg +"',done_time = to_date('"+df.format(new Date())+
							"','yyyy-mm-dd hh24:mi:ss'),send_num = "+send_num+",send_status = '2' where sms_id = '"+sms_id +"'";
					baseDaoSupport.execute(updateSql);
					respJsonStr = cmcSendResp.getError_msg();
				}
			}
		} else {
			respJsonStr = "操作失败";
		}

		return respJsonStr;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String smsSendTimer(Map<String, String> map) throws Exception {
		String respJsonStr = "操作成功";// 返回json字符串
		if (null != map) {
			CommCaller caller = new CommCaller();
			String service_num = map.get("service_num");// 接收号码
			String bill_num = map.get("bill_num");
			String sms_data = map.get("sms_data");// 发送内容
			String sms_flag = map.get("sms_flag");// 0：短内容 1：长内容
			String sms_type = map.get("sms_type");// 短信类型（sms本网，cmc异网）
			String order_id = map.get("order_id");
			String sms_id  =  map.get("sms_id");
			if ("sms".equals(sms_type)) {// sms是本网
				AopSmsSendReq sendReq = new AopSmsSendReq();
				AopSmsSendResp resp = new AopSmsSendResp();
				sendReq.setBill_num(bill_num);// 短信发送号码
				sendReq.setService_num(service_num);// 接受号码--省内联通号码
				sendReq.setSms_data(sms_data);
				sendReq.setSms_flag(sms_flag);

				Map<String, Object> param = new HashMap<String, Object>();
				try {
					BeanUtils.bean2MapForAiPBSS(param, sendReq);

					String method = cacheUtil.getConfigInfo("SMS_METHOD");
					if (!StringUtil.isEmpty(method) && EcsOrderConsts.NEW_SMS_SEND.equals(method)) {
						param.remove("sms_flag");
						resp = (AopSmsSendResp) caller.invoke(EcsOrderConsts.NEW_SMS_SEND, param);
					} else {
						resp = (AopSmsSendResp) caller.invoke(EcsOrderConsts.ECAOP_SMS_SEND, param);
					}
					if (resp != null && "00000".equals(resp.getCode())) {// 成功
						 String delSql = "delete from es_order_mid_sms a where a.sms_id = '"+sms_id +"'";
						 baseDaoSupport.execute(delSql);
						 map.put("send_status", "1");
						 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
						 map.put("done_time", df.format(new Date()));
							map.put("exception_desc", "发送成功");
						 baseDaoSupport.insertByMap("es_order_his_sms", map);
					} else {
						//发送失败：1，中间表记录失败原因，2，记录发送次数，3，记录发送时间。4,发送状态改为发送失败
						
						String sql = "select send_num from es_order_mid_sms where sms_id = '"+sms_id +"'";
						int send_num = baseDaoSupport.queryForInt(sql);
						send_num = send_num + 1;
						String exception_desc = resp.getMsg();
						
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
						String updateSql = "update es_order_mid_sms set exception_desc = '" + exception_desc +"',done_time = to_date('"+df.format(new Date())+
								"','yyyy-mm-dd hh24:mi:ss'),send_num = "+send_num+",send_status = '2' where sms_id = '"+sms_id +"'";
						baseDaoSupport.execute(updateSql);
						respJsonStr = resp.getError_msg();
					}
				} catch (Exception e) {// 调用接口失败
					resp = new AopSmsSendResp();
					resp.setError_msg(e.getMessage());
					e.printStackTrace();
				}
			} else if ("cmc".equals(sms_type)) {// 1是本网
				CmcSmsSendReq req = new CmcSmsSendReq();
				CmcSmsSendResp cmcSendResp = new CmcSmsSendResp();
				String spCode = cacheUtil.getConfigInfo("YXT_spCode");
				String userName = cacheUtil.getConfigInfo("YXT_userName");
				String Password = cacheUtil.getConfigInfo("YXT_Password");
				// String key = cacheUtil.getConfigInfo("YXT_KEY");
				req.setSpCode(spCode);
				req.setLoginName(userName);
				req.setPassword(Password);
				req.setNotNeedReqStrOrderId(order_id);
				req.setUserNumber(service_num);// 接收号码
				req.setMessageContent(sms_data);
				try {
					req.setSerialNumber(DateUtil.getTime7() + "000");
				} catch (FrameException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				req.setF("1");
				Map<String, Object> param = new HashMap<String, Object>();

				try {
					param.put("SpCode", req.getSpCode());
					param.put("LoginName", req.getLoginName());
					param.put("Password", req.getPassword());
					param.put("MessageContent", URLEncoder.encode(req.getMessageContent(), "gbk"));
					param.put("UserNumber", req.getUserNumber());
					param.put("SerialNumber", req.getSerialNumber());
					param.put("f", req.getF());
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
				cmcSendResp = (CmcSmsSendResp) caller.invoke("yeion.user.message.send", param);
				if (!StringUtil.isEmpty(cmcSendResp.getResultcode()) && "0".equals(cmcSendResp.getResultcode())) {// 成功
					String delSql = "delete from es_order_mid_sms a where a.sms_id = '"+sms_id +"'";
					baseDaoSupport.execute(delSql);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					map.put("done_time", df.format(new Date()));
					map.put("send_status", "1");
					map.put("exception_desc", "发送成功");
					baseDaoSupport.insertByMap("es_order_his_sms", map);
				} else {
					//发送失败：1，中间表记录失败原因，2，记录发送次数，3，记录发送时间。4,发送状态改为发送失败
					String msg = "";
					List<Map> list = this.getConsts("DIC_YXT_SMS_RESULT");
					for (Map m : list) {
						String pkey = (String) m.get("pkey");
						if (cmcSendResp.getResultcode().equals(pkey)) {
							msg = (String) m.get("pname");
							break;
						}
					}
					// 记录失败
					String sql = "select send_num from es_order_mid_sms where sms_id = '"+sms_id +"'";
					int send_num = baseDaoSupport.queryForInt(sql);
					send_num = send_num + 1;
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					String updateSql = "update es_order_mid_sms set exception_desc = '" + msg +"',done_time = to_date('"+df.format(new Date())+
							"','yyyy-mm-dd hh24:mi:ss'),send_num = "+send_num+",send_status = '2' where sms_id = '"+sms_id +"'";
					baseDaoSupport.execute(updateSql);
					respJsonStr = cmcSendResp.getError_msg();
				}
			}
		} else {
			respJsonStr = "操作失败";
		}

		return respJsonStr;
	}
	
	
	/*
	 * 
	 */
	private List<Map> getConsts(String key) {
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<Map> list = dcPublicCache.getList(key);
		return list;
	}

}
