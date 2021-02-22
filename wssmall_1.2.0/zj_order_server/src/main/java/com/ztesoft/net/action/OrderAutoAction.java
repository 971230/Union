package com.ztesoft.net.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import params.ZteRequest;
import params.ZteResponse;
import params.adminuser.req.SmsValidCodeGetReq;
import params.adminuser.resp.SmsValidCodeGetResp;
import params.order.req.OrderExceptionCollectReq;
import params.order.req.OrderExceptionLogsReq;
import params.order.req.OrderHandleLogsReq;
import params.order.req.OutcallLogsReq;
import params.order.resp.OrderExceptionCollectResp;
import params.req.CrawlerCodeReq;
import params.req.CrawlerReq;
import params.req.CrawlerUpdatePropertiesReq;
import params.req.GetCrawlerPropertiesReq;
import params.req.HeadquartersMallBusiRequest;
import params.req.SaveCrawlerPropertiesReq;
import params.resp.CrawlerResp;
import params.resp.GetCrawlerPropertiesResp;
import rule.impl.OrdExceptionHandleImpl;
import util.Utils;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.base.po.ATTR_OPTIONS;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.bss.req.OrderListForWorkReq;
import zte.net.ecsord.params.bss.resp.OrderListForWorkResp;
import zte.net.ecsord.params.bss.vo.StaffList;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderAdslBusiRequest;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtvtlBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoFukaBusiRequest;
import zte.net.ecsord.params.busi.req.OrderRealNameInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.AopSmsSendReq;
import zte.net.ecsord.params.ecaop.req.ResourcePreCheckReq;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.resp.AopSmsSendResp;
import zte.net.ecsord.params.ecaop.resp.ResourcePreCheckResp;
import zte.net.ecsord.params.ecaop.resp.vo.BrandInfoList;
import zte.net.ecsord.params.pub.req.OrderSubPackageList;
import zte.net.ecsord.params.wms.req.GetOrdByBoxIdFromWMSReq;
import zte.net.ecsord.params.wms.resp.GetOrdByBoxIdFromWMSResp;
import zte.net.ecsord.params.workCustom.po.ES_ORDER_INTENT;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_NODE_INS;
import zte.net.ecsord.params.workCustom.po.ES_WORK_ORDER;
import zte.net.ecsord.params.workCustom.po.WORK_CUSTOM_FLOW_DATA;
import zte.net.ecsord.params.zb.req.NotifyOrderAbnormalToZBRequest;
import zte.net.ecsord.params.zb.resp.NotifyOrderAbnormalToZBResponse;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.ecsord.utils.GetAreaInfoUtils;
import zte.net.ecsord.utils.SpecUtils;
import zte.net.iservice.IOrderServices;
import zte.net.iservice.IRegionService;
import zte.net.iservice.IRuleService;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.req.PlanRuleTreeQueryReq;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;
import zte.net.params.resp.PlanRuleTreeQueryResp;
import zte.net.params.resp.RuleTreeExeResp;
import zte.params.goodstype.req.GoodsTypeGetReq;
import zte.params.goodstype.resp.GoodsTypeGetResp;
import zte.params.order.req.DeliveryItemAddReq;
import zte.params.order.req.DeliveryItemDelReq;
import zte.params.order.req.DeliveryItemsQueryReq;
import zte.params.order.req.OrderHandleLogsListReq;
import zte.params.order.req.OutcallLogsListReq;
import zte.params.order.resp.DeliveryItemAddResp;
import zte.params.order.resp.DeliveryItemsQueryResp;
import zte.params.order.resp.OrderHandleLogsListResp;
import zte.params.order.resp.OutcallLogsListResp;
import zte.params.orderctn.resp.OrderCtnResp;
import zte.params.region.req.RegionsListReq;
import zte.params.region.resp.RegionsListResp;

import com.alibaba.fastjson.JSONArray;
import com.jcraft.jsch.ChannelSftp;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.MD5Util;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.ecsord.params.ecaop.req.CmcSmsSendReq;
import com.ztesoft.net.ecsord.params.ecaop.req.DwzCnCreateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.UserActivationReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.CmcSmsSendResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.DwzCnCreateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.UserActivationResp;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.DownLoadUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.model.Logi;
import com.ztesoft.net.mall.core.model.Regions;
import com.ztesoft.net.mall.core.model.RuleConfig;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.RequestStoreManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AutoOrderTree;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.model.EntityInfoVO;
import com.ztesoft.net.model.OrdReceipt;
import com.ztesoft.net.model.OrderBtn;
import com.ztesoft.net.model.OrderQueryParams;
import com.ztesoft.net.model.SDSStatusLogVO;
import com.ztesoft.net.model.WriteCardPercent;
import com.ztesoft.net.rule.util.RuleFlowUtil;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IOrdArchiveTacheManager;
import com.ztesoft.net.service.IOrdCollectManagerManager;
import com.ztesoft.net.service.IOrdVisitTacheManager;
import com.ztesoft.net.service.IOrdWorkManager;
import com.ztesoft.net.service.IOrdWriteCardTacheManager;
import com.ztesoft.net.service.IOrderExtManager;
import com.ztesoft.net.service.IOrderFlowManager;
import com.ztesoft.net.service.IOrderSupplyManager;
import com.ztesoft.net.service.IWorkPoolManager;
import com.ztesoft.net.service.impl.EcsOrdManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomCfgManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomEngine;
import com.ztesoft.net.sqls.SqlUtil;
import com.ztesoft.net.util.SFTPChannel;
import com.ztesoft.remote.inf.IRemoteSmsService;

import consts.ConstsCore;
import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * 订单自动化action
 * 
 * @作者 MoChunrun
 * @创建日期 2014-10-6
 * @版本 V 1.0
 * 
 */
public class OrderAutoAction extends WWAction {
	private Logger logger = Logger.getLogger(OrderAutoAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private IEcsOrdManager ecsOrdManager;
	@Resource
	private IRuleService ruleService;
	@Resource
	private IDcPublicInfoManager dcPublicInfoManager;
	@Resource
	private IRegionService regionService;
	@Resource
	private IOrderServices orderServices;
	@Resource
	private IOrderSupplyManager iOrderSupplyManager;
	@Resource
	private IOrderExtManager orderExtManager;
	@Resource
	private IOrdArchiveTacheManager ordArchiveTacheManager;
	@Resource
	private IOrdVisitTacheManager ordVisitTacheManager; // 订单预校验
	@Resource
	private IOrdWriteCardTacheManager ordWriteCardTacheManager;
	@Resource
	private IOrdCollectManagerManager ordCollectManager;
	@Resource
	private IWorkPoolManager workPoolManager;
	@Resource
	private IOrdWorkManager ordWorkManager;
	@Resource
	private IWorkCustomCfgManager workCustomCfgManager;

	/**
	 * 方案与流程对应数据
	 */
	public static final String PLAN_CACHE_KEY = "DIC_ORDER_NODE";
	// public static final String ORDER_FROM_CACHE_KEY = "source_from";
	public static final String ORDER_FROM_CACHE_KEY = "source_type";

	public static final String CHANNEL_MARK_CACHE_KEY = "channel_mark";
	public static final String QUICK_STATUS_CACHE_KEY = "DIC_LIGHT_NING_STATUS";
	public static final String SHIPPING_TYPE_CACHE_KEY = "shipping_type";
	public static final String IS_QUICK_SHIP = "shipping_quick";
	public static final String PAY_TYPE_CACHE_KEY = "pay_way";
	public static final String DEAL_STATUS_CACHE_KEY = "deal_status";
	public static final String SHIPPINT_COP_CACHE_KEY = "DIC_SDS_COMP";
	public static final String FLOW_TRACE_CACHE_KEY = "DIC_ORDER_NODE";
	public static final String DIC_OPER_MODE = "DIC_OPER_MODE";
	public static final String DIC_ORDER_EXCEPTION_TYPE = "DIC_ORDER_EXCEPTION_TYPE";
	public static final String DC_MODE_REGION = "DC_MODE_REGION"; // 订单所属地市

	private String query_type = "order"; // order 订单处理 exception 异常单 returned
											// 退单申请列表 returned_cfm退单确认
	private String is_return_back = "0"; // 是否调用返回方法，默认0为不是，如果值为1则从session取查询条件添加到页面
	private String orderReceiveRetrunMark;// 订单领取查询页面返回码
	private String check_type; // 区分是否实物稽核
	private String newOrderList = "0";
	private String order_id;
	private String btn_type = "list";// list订单列表按钮 detail订单详细页面按钮
	private String order_from_c;
	private String outcall_type;// 外呼类型
	private String outcall_reason;// 外呼原因
	private String source_system;
	private String type;

	private String work_type;// 工单类型
	private String work_reason;// 原因
	private String workReason;// 原因
	private String operator_num;// 操作员号码
	private String operator_name;//
	private String cityCode;//
	private String mainnumber;// 主卡号码，针对副卡开户
	private List workList;// 单个订单工单列表
	private String is_fzn;// 泛智能终端

	private List<Map<String, Object>> countyList;// 县分列表
	private List<Map<String, Object>> countyList_new;// 县分列表
	private List<Map<String, String>> groupList;// 团队
	private String team_level; // 团队层级
	private String team_id; // 团队ID
	private String team_name;// 团队名称
	private String team_type;// 区分团队还是人员
	private String deal_level;

	private String field_names;
	private String field_values;

	private String option_stypes;
	
	private String cat_id;//商品小类
	

	public String getCat_id() {
		return cat_id;
	}

	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	public String getDeal_level() {
		return deal_level;
	}

	public void setDeal_level(String deal_level) {
		this.deal_level = deal_level;
	}

	public String getTeam_level() {
		return team_level;
	}

	public void setTeam_level(String team_level) {
		this.team_level = team_level;
	}

	public String getTeam_id() {
		return team_id;
	}

	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public String getTeam_type() {
		return team_type;
	}

	public void setTeam_type(String team_type) {
		this.team_type = team_type;
	}
	
	

	public String getIs_fzn() {
		return is_fzn;
	}

	public void setIs_fzn(String is_fzn) {
		this.is_fzn = is_fzn;
	}



	public static final String UNI_EXPRESS_TYPE_CACHE_KEY = "uni_express_type";
	public static final String UNI_ORDER_STATUS_CACHE_KEY = "uni_order_status";
	public static final String UNI_ORDER_TYPE_CACHE_KEY = "uni_order_type";
	public static final String UNI_ORDER_SRC_CACHE_KEY = "uni_order_src";
	public static final String UNI_DATA_SRC_CACHE_KEY = "uni_data_src";
	public static final String UNI_PAY_TYPE_CACHE_KEY = "uni_pay_type";
	public static final String UNI_AREA_ID_CACHE_KEY = "uni_area_id";
	public static final String ZJ_UNI_DATA_SRC_CACHE_KEY = "source_from";

	public String getOrder_from_c() {
		return order_from_c;
	}

	public void setOrder_from_c(String order_from_c) {
		this.order_from_c = order_from_c;
	}

	private List<OrderBtn> ordBtns;

	private List<RuleConfig> planRuleTreeList;
	private List<OrderHandleLogsReq> logsList;
	private List<OutcallLogsReq> outcalllogsList;
	private List<OrderExceptionCollectReq> exceptionLogsList;
	private List<SDSStatusLogVO> sdsLogsList;

	List<OrderPhoneInfoFukaBusiRequest> orderPhoneInfoFukaList;// 副卡列表
	// 规则ID
	private String rule_id;
	// 方案ID
	private String plan_id;
	// 环节ID
	private String trace_id;

	private String start_time;

	private String end_time;
	private String platform_locked_status;

	private int exeRelyOnRule = 0; // 0 执行依赖规则 1不执行当前依赖规则 2不执行所有依赖规则

	private List<Regions> regionList;

	private AdminUser user;// 当前登录用户

	private List<Map> order_from_list; // 订单来源

	private List<Map> order_role_list; // 人员角色列表

	private List<Map> order_vplan_list; // V计划活动名字,照片上传状态

	private List<Map> role_group_list; // 证件类型

	public List<Map> getRole_group_list() {
		return role_group_list;
	}

	public void setRole_group_list(List<Map> role_group_list) {
		this.role_group_list = role_group_list;
	}

	private List<Map> channel_mark_list;
	private List<Map> quick_status_list;
	private List<Map> shipping_type_list;
	private List<Map> is_quick_ship_list;
	private List<Map> pay_type_list;
	private List<Map> deal_status;
	private List<Map> shipping_cop_list;
	private List<Map> flowTraceList;
	private List<Map> exceptionTypeList;
	private List<Map> orderExceptionTypeList;
	private List<Map> dc_MODE_REGIONList;
	private List<Map> is_order_his_list;
	private List<Map> order_supply_status_list;
	private List<Map> order_model_list;
	private List<Map> goods_type_list;
	private List<Map> wms_refund_status_list;
	private List<Map> plat_type_list;
	private List<Map> refund_satus_list;
	private List<Map> bss_refund_satus_list;
	private List<Map> is_or_no_list;
	private List<Map> order_type_list;
	private List<Map> dc_MODE_OrderStatusList;
	private List<Map> outcallTypeList;
	private List<Map> order_status_list;
	private List<Map> order_src_list;
	private List<Map> data_src_list;
	private List<Map> area_id_list;
	private List<Map> activity_type_list;
	private String textInfo;
	private String county_id;
	private String county_name;
	private String allot_county_id;
	private String allot_county_name;
	private String remarks;
	private String is_yxd;
	private String is_work_custom;// 是否自定义
	private String county_code;// 县分编码
	private String countyname;// 县分名称
	private String instance_id;// 流程环节实例编号
	private String checkFee;

	private String mobile_type; //终端类型
	private String scheme_id; //活动编码
	private String element_id; //元素编码
	private String mobile_imei;//终端串号
	private String object_esn;//终端串号
	private String terminal_name; //终端名称
	
	public String getMobile_type() {
		return mobile_type;
	}

	public void setMobile_type(String mobile_type) {
		this.mobile_type = mobile_type;
	}

	public String getScheme_id() {
		return scheme_id;
	}

	public void setScheme_id(String scheme_id) {
		this.scheme_id = scheme_id;
	}

	public String getElement_id() {
		return element_id;
	}

	public void setElement_id(String element_id) {
		this.element_id = element_id;
	}

	public String getMobile_imei() {
		return mobile_imei;
	}

	public void setMobile_imei(String mobile_imei) {
		this.mobile_imei = mobile_imei;
	}

	public String getObject_esn() {
		return object_esn;
	}

	public void setObject_esn(String object_esn) {
		this.object_esn = object_esn;
	}

	public String getTerminal_name() {
		return terminal_name;
	}

	public void setTerminal_name(String terminal_name) {
		this.terminal_name = terminal_name;
	}

	public String getCheckFee() {
		return checkFee;
	}

	public void setCheckFee(String checkFee) {
		this.checkFee = checkFee;
	}

	public String getInstance_id() {
		return instance_id;
	}

	public void setInstance_id(String instance_id) {
		this.instance_id = instance_id;
	}

	public String getCounty_code() {
		return county_code;
	}

	public void setCounty_code(String county_code) {
		this.county_code = county_code;
	}

	public String getIs_work_custom() {
		return is_work_custom;
	}

	public void setIs_work_custom(String is_work_custom) {
		this.is_work_custom = is_work_custom;
	}

	private List<Map<String, Object>> contactResultsList;// 订单处理 退单选择
	private String contactResults;// 订单处理 退单选择

	public List<Map<String, Object>> getContactResultsList() {
		return contactResultsList;
	}

	public void setContactResultsList(List<Map<String, Object>> contactResultsList) {
		this.contactResultsList = contactResultsList;
	}

	public String getContactResults() {
		return contactResults;
	}

	public void setContactResults(String contactResults) {
		this.contactResults = contactResults;
	}

	public String getIs_yxd() {
		return is_yxd;
	}

	public void setIs_yxd(String is_yxd) {
		this.is_yxd = is_yxd;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAllot_county_id() {
		return allot_county_id;
	}

	public void setAllot_county_id(String allot_county_id) {
		this.allot_county_id = allot_county_id;
	}

	public String getAllot_county_name() {
		return allot_county_name;
	}

	public void setAllot_county_name(String allot_county_name) {
		this.allot_county_name = allot_county_name;
	}

	public String getCounty_name() {
		return county_name;
	}

	public void setCounty_name(String county_name) {
		this.county_name = county_name;
	}

	public String getCounty_id() {
		return county_id;
	}

	public void setCounty_id(String county_id) {
		this.county_id = county_id;
	}

	public List<Map> getDc_MODE_OrderStatusList() {
		return dc_MODE_OrderStatusList;
	}

	public void setDc_MODE_OrderStatusList(List<Map> dc_MODE_OrderStatusList) {
		this.dc_MODE_OrderStatusList = dc_MODE_OrderStatusList;
	}

	public List<Map> getOutcallTypeList() {
		return outcallTypeList;
	}

	public void setOutcallTypeList(List<Map> outcallTypeList) {
		this.outcallTypeList = outcallTypeList;
	}

	private List<Map> express_type_list;
	private List<Map> batch_pre_goods_list;
	private String exception_from;
	private OrderTreeBusiRequest orderTree;

	private OrderQueryParams params;

	private String org_name;
	private String lan_id;// 地市编码
	private String order_county_name;
	private String district_code;// 行政县分编码
	private String development_point_name;// 发展点名称
	private String development_point_code;// 发展点编码
	private String developmentCode;// 发展人编码
	private String developmentName;// 发展人名称
	private String mode_name;
	// 订单分配用户查询条件
	private String userparams;

	private String log_id;// 外呼日志id
	private String pending_reason; // 挂起原因 (ZX add 2014-10-14)
	private List<Map> exceptionList; // 异常敞亮集合(ZX add 2014-10-14)
	private String exception_id; // 异常ID(ZX add 2014-10-14)
	private String exception_remark; // 异常备注(ZX add 2014-10-14)
	private String abnormal_type; // 异常类型（暂时页面写死为"1"）
	private String order_deal_method; //订单处理类型，线上/线下
	private String obj_name; // 查询对象名称
	private String userid; // 用户ID
	private String permission_level;// 用户权限
	private String col5;
	private String username; // 用户名
	private String realname; // 用户姓名
	private String phone_num;// 手机号码
	private String reissue_info; // 赠品名称
	private String reissue_id; // 补寄物品ID
	private String reissue_num; // 赠品数量
	private List provList;// 省列表
	private List cityList;// 市列表
	private List districtList;// 区列表
	private Long provinc_code;// 省
	private Long city_code;// 市
	private Long district_id;// 区
	private List<Logi> logiCompanyList;// 物流公司
	private String logiCompanyCode;// 物流公司编码
	private String flowCode;// 流程编码
	private String logi_post_id;// 物流公司ID
	private String logi_no; // 物流单号
	private String logi_receiver_phone; // 物流公司电话
	private String logi_receiver; // 物流人员
	private List<Map> logiCompanyRegionsList;// 物流公司和地域映射
	private List<Map> dic_material_retrieve;// 回收资料
	private String material_retrieve; // 资料回收
	private OrdReceipt ordReceipt;// 回单对象
	private OrdExceptionHandleImpl ordExceptionHandleImpl;
	private IOrderFlowManager ordFlowManager;
	private String dealType; // 处理类型
	private String dealDesc; // 处理内容
	private DeliveryItemsQueryResp deliveryItemsQueryResp;
	private AutoOrderTree autoOrderTree;
	private List<EntityInfoVO> entityInfoList; // 订单实物信息
	private String serial_num; // 串号
	private String entity_quality_state; // 实物稽核状态
	private String order_state; // 订单领取，照片上传状态
	private String is_his_order;
	private String is_order_view;
	private String lockOrderIdStr;
	private String lockDealType;
	private String order_ids;
	private String sms_phone;// 系统用户手机号码
	private String sms_content;// 短信内容
	private String order_sn;// 订单取货领取条形码
	private long totalCount;// 订单锁定总记录条数
	private String need_customer_visit;
	private String is_rg_exception;
	private String release_id;
	private String terminal_nums;
	private String ess_emp_id;
	private List<EmpOperInfoVo> empOperInfos;
	private int empOperInfosSize = 0;
	List<OrderItemExtvtlBusiRequest> orderItemExtvtlBusiRequests;// 多商品对象

	// 商品原价
	private String good_price_system;
	// 号码预存
	private String num_price;
	// 多交预存
	private String deposit_price;
	// 开户费用
	private String openAcc_price;
	// 首次加载页面
	private String first_load;
	// 附加产品信息
	private List<OrderSubPackageList> subProductList = new ArrayList<OrderSubPackageList>();

	private List<AttrGiftInfoBusiRequest> giftInfoList;// 礼品信息列表
	private int giftInfoSize;
	private String auto;// 是否自动打印及流转

	public static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat DF2 = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat DF3 = new SimpleDateFormat("yyyy-MM");
	private OrderBusiRequest orderBusiObject;
	private OrderExtBusiRequest orderExtBusi;

	public Map<String, String> manualOrder = new HashMap<String, String>(); // 手工补录订单
	private String receive_num;

	private String lock_user_id;
	private String allotType;

	private String enreust_status;// 判断订单委托时是否初始查询

	private HeadquartersMallBusiRequest headquartersMall;// = new
															// HeadquartersMallBusiRequest();
	private String staffName;
	private String staffPassword;
	private String messageCode;
	private String sendType;

	private List<Map> OrderQryStatusList; // 订单状态

	private SaveCrawlerPropertiesReq crawlerProperties;

	private String p_code;// 合约编码
	private String p_name;// 合约名称
	private String city;// 地市
	private String packge_limit;// 合约期
	private String net_speed;// 速率
	private String access_type;// 接入方式
	private String plan_title;// 套餐编码
	private String ess_code;// 套餐名称
	private String terminal_type;// 终端类型
	private String goods_type;// 商品类型
	private String bat_id; // 实例id

	private List<String> ord_agrt_img;// 协议图片
	private String isChangePhone = "no";// 是否可以变更号码
	private String ip;
	// 爬虫进程list
	private List<Map<String, String>> crawlerProcList;
	private int pageSize = 20000;// 报表导出上限
	private String cookie;

	// add by cqq 20181227
	private String ship_name;
	private String certi_num;
	private String ship_addr;
	private String district_name;

	/**
	 * 各人员订单量报表查询条件
	 */
	private String order_role; // 查询条件：人员角色
	private String create_start; // 查询条件：开始时间
	private String create_end; // 查询条件：结束时间
	private String order_from; // 查询条件：订单来源
	private String order_city_code; // 查询条件：归属地市
	private String order_county_code; // 查询条件：归属县分

	private List<Map> top_seed_professional_line_list;// 查询条件：顶级种子专业线
	private List<Map> top_seed_type_list;// 查询条件：顶级种子类型
	private List<Map> top_seed_group_list;// 查询条件：顶级种子分组

	public String getCreate_start() {
		return create_start;
	}

	public void setCreate_start(String create_start) {
		this.create_start = create_start;
	}

	public String getCreate_end() {
		return create_end;
	}

	public void setCreate_end(String create_end) {
		this.create_end = create_end;
	}

	public String getOrder_from() {
		return order_from;
	}

	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}

	public String getOrder_city_code() {
		return order_city_code;
	}

	public void setOrder_city_code(String order_city_code) {
		this.order_city_code = order_city_code;
	}

	public String getOrder_county_code() {
		return order_county_code;
	}

	public void setOrder_county_code(String order_county_code) {
		this.order_county_code = order_county_code;
	}

	public String getShip_name() {
		return ship_name;
	}

	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}

	public String getCerti_num() {
		return certi_num;
	}

	public void setCerti_num(String certi_num) {
		this.certi_num = certi_num;
	}

	public String getShip_addr() {
		return ship_addr;
	}

	public void setShip_addr(String ship_addr) {
		this.ship_addr = ship_addr;
	}

	public String getDistrict_name() {
		return district_name;
	}

	public void setDistrict_name(String district_name) {
		this.district_name = district_name;
	}

	public String getCol5() {
		return col5;
	}

	public void setCol5(String col5) {
		this.col5 = col5;
	}

	public String getPermission_level() {
		return permission_level;
	}

	public void setPermission_level(String permission_level) {
		this.permission_level = permission_level;
	}

	public SaveCrawlerPropertiesReq getCrawlerProperties() {
		return crawlerProperties;
	}

	public void setCrawlerProperties(SaveCrawlerPropertiesReq crawlerProperties) {
		this.crawlerProperties = crawlerProperties;
	}
	
	public String getOrder_state() {
		return order_state;
	}

	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}

	private void listExceptionTypeList() {
		exceptionTypeList = getConsts(DIC_ORDER_EXCEPTION_TYPE);
		if (exceptionTypeList == null) {
			exceptionTypeList = new ArrayList<Map>();
		}
		toCheckPkey(exceptionTypeList);
	}

	// 静态数据取区域信息
	private void listDcModeRegion() {
		dc_MODE_REGIONList = ecsOrdManager.getDcSqlByDcName("DC_MODE_REGION");
		if (dc_MODE_REGIONList == null) {
			dc_MODE_REGIONList = new ArrayList<Map>();
		}
		Map m0 = new HashMap();
		m0.put("value", "-1");
		m0.put("value_desc", "--请选择--");
		dc_MODE_REGIONList.add(0, m0);
	}

	private void listOrderQryStatus() {
		List list = new ArrayList();
		Map m0 = new HashMap();
		m0.put("pkey", "qxz");
		m0.put("pname", "--请选择--");
		list.add(0, m0);
		Map m1 = new HashMap();
		m1.put("pkey", "00");
		m1.put("pname", "正常");
		list.add(1, m1);
		Map m2 = new HashMap();
		m2.put("pkey", "01");
		m2.put("pname", "退单");
		list.add(2, m2);
		Map m3 = new HashMap();
		m3.put("pkey", "02");
		m3.put("pname", "外呼");
		list.add(3, m3);
		Map m4 = new HashMap();
		m4.put("pkey", "03");
		m4.put("pname", "异常");
		list.add(4, m4);

		Map m5 = new HashMap();
		m5.put("pkey", "0");
		m5.put("pname", "待处理");
		list.add(5, m5);

		Map m6 = new HashMap();
		m6.put("pkey", "1");
		m6.put("pname", "中台已领取");
		list.add(6, m6);

		Map m7 = new HashMap();
		m7.put("pkey", "2");
		m7.put("pname", "中台已审单");
		list.add(7, m7);

		Map m8 = new HashMap();
		m8.put("pkey", "3");
		m8.put("pname", "已派工单");
		list.add(8, m8);

		Map m9 = new HashMap();
		m9.put("pkey", "4");
		m9.put("pname", "受理成功");
		list.add(9, m9);

		Map m10 = new HashMap();
		m10.put("pkey", "5");
		m10.put("pname", "装机成功");
		list.add(10, m10);

		Map m11 = new HashMap();
		m11.put("pkey", "6");
		m11.put("pname", "受理失败");
		list.add(11, m11);

		Map m12 = new HashMap();
		m12.put("pkey", "-1");
		m12.put("pname", "退单-电商");
		list.add(12, m12);

		Map m13 = new HashMap();
		m13.put("pkey", "-2");
		m13.put("pname", "退单-用户取消");
		list.add(13, m13);

		Map m14 = new HashMap();
		m14.put("pkey", "-3");
		m14.put("pname", "退单-无资源退单");
		list.add(14, m14);

		Map m15 = new HashMap();
		m15.put("pkey", "-4");
		m15.put("pname", "退单-其它");
		list.add(15, m15);

		OrderQryStatusList = list;
	}

	// 静态数据取商品信息
	private void listDcModeGoodsType() {
		goods_type_list = ecsOrdManager.getDcSqlByDcName("dc_goods_type_zjuni");
		if (goods_type_list == null) {
			goods_type_list = new ArrayList<Map>();
		}
		Map m0 = new HashMap();
		m0.put("value", "-1");
		m0.put("value_desc", "--请选择--");
		goods_type_list.add(0, m0);

	}

	private void listDcModeOrderStatus() {

		dc_MODE_OrderStatusList = ecsOrdManager.getDcSqlByDcName("dc_order_status_new");
		if (dc_MODE_OrderStatusList == null) {
			dc_MODE_OrderStatusList = new ArrayList<Map>();
		}
		Map m0 = new HashMap();
		m0.put("value", "-1");
		m0.put("value_desc", "--请选择--");
		dc_MODE_OrderStatusList.add(0, m0);

	}

	private void listOutcallTypeList() {

		outcallTypeList = ecsOrdManager.getDcSqlByDcName("DC_OUTCALL_TYPE");
		if (outcallTypeList == null) {
			outcallTypeList = new ArrayList<Map>();
		}

	}

	private String changeOutcallTypeParams(String outcall_type_params) {
		String result = "";
		if (null != outcall_type_params && !"".equals(outcall_type_params)) {
			if (outcall_type_params.indexOf(",") != -1) {
				String[] params = outcall_type_params.split(",");
				if (params.length > 1) {
					for (int i = 0; i < params.length; i++) {
						result += params[i].trim();
						// 通过trim()方法来处理,去掉页面传来的值的空格
						if (i < params.length - 1) {
							result += ",";
						}
					}
				}
			} else {
				return outcall_type_params;
			}
		}
		return result;
	}

	private void listDcModeActivityType() {

		activity_type_list = ecsOrdManager.getDcSqlByDcName("DIC_ActivityType");
		if (activity_type_list == null) {
			activity_type_list = new ArrayList<Map>();
		}
		Map m0 = new HashMap();
		m0.put("value", "-1");
		m0.put("value_desc", "--请选择--");
		activity_type_list.add(0, m0);

	}

	/**
	 * 获取自定义流程处理页面
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getWorkCustomUrl() throws Exception {
		// 默认的基础页面
		String url = "shop/admin/ordAuto!getBasicBackNodeUrl.do";

		ES_WORK_CUSTOM_NODE_INS pojo = new ES_WORK_CUSTOM_NODE_INS();
		pojo.setOrder_id(this.getOrder_id());
		pojo.setIs_curr_step("1");

		List<ES_WORK_CUSTOM_NODE_INS> inses = this.workCustomCfgManager.qryInsListFromAll(pojo, null);

		if (inses != null && inses.size() > 0 && StringUtils.isNotBlank(inses.get(0).getDeal_url())) {
			// 取当前环节配置的处理页面
			return inses.get(0).getDeal_url();
		}

		return url;
	}

	public String getJspPath() {
		try {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);

			// add by cqq 20181129 自定义流程
			String is_work_custom = orderTree.getOrderExtBusiRequest().getIs_work_custom();
			String action_url = "";
			if ("1".equals(is_work_custom)) {
				action_url = this.getWorkCustomUrl();
			} else {
				String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
				action_url = RuleFlowUtil.getOrderUrl(order_id, trace_id);
			}

			if (StringUtil.isEmpty(action_url)) {
				action_url = EcsOrderConsts.YCL_URL;
			}
			this.json = "{result:0,action_url:'" + action_url + "'}";
		} catch (Exception e) {
			e.printStackTrace();
			this.json = "{result:1,message:'" + e.getMessage() + "'}";
		}
		return this.JSON_MESSAGE;
	}

	public void listExceptionType() {
		orderExceptionTypeList = getConsts("ORDER_ABNORMAL_TYPE");
		if (orderExceptionTypeList == null) {
			orderExceptionTypeList = new ArrayList<Map>();
		}
		toCheckPkey(orderExceptionTypeList);
	}

	/**
	 * 订单环节
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-14
	 */
	public void listFlowTrace() {
		flowTraceList = new ArrayList<Map>();
		List tacheList = getConsts(FLOW_TRACE_CACHE_KEY);
		if (tacheList == null) {
			tacheList = new ArrayList<Map>();
		}
		if (!EcsOrdManager.QUERY_TYPE_ORDER_VIEW.equals(query_type) && ManagerUtils.getAdminUser().getFounder() != 1) {
			String tacheAuth = ManagerUtils.getAdminUser().getTacheAuth();
			for (int i = 0; i < tacheList.size(); i++) {
				Map tacheMap = (Map) tacheList.get(i);
				String tacheCode = (String) tacheMap.get("pkey");
				if (tacheAuth != null && tacheAuth.contains(tacheCode)) {
					flowTraceList.add(tacheMap);
				}
			}
		} else {
			flowTraceList.addAll(tacheList);
		}
		/*
		 * flowTraceList = getConsts(FLOW_TRACE_CACHE_KEY); if(flowTraceList==null){
		 * flowTraceList = new ArrayList<Map>(); }
		 */
		Map m0 = new HashMap();
		m0.put("pkey", "-1");
		m0.put("pname", "--请选择--");
		flowTraceList.add(0, m0);
	}

	/**
	 * 闪电送公司
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-9
	 */
	private void listShiipingCop() {
		shipping_cop_list = getConsts(SHIPPINT_COP_CACHE_KEY);
		if (shipping_cop_list == null) {
			shipping_cop_list = new ArrayList<Map>();
		}
		toCheckPkey(shipping_cop_list);
		/*
		 * Map m2 = new HashMap(); m2.put("pkey", "04"); m2.put("pname", "闪电送公司1"); Map
		 * m3 = new HashMap(); m3.put("pkey", "02"); m3.put("pname", "闪电送公司2"); Map m1 =
		 * new HashMap(); m1.put("pkey", "01"); m1.put("pname", "闪电送公司3");
		 * shipping_cop_list.add(m2); shipping_cop_list.add(m3);
		 * shipping_cop_list.add(m1);
		 */
	}

	/**
	 * 处理状成
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-9
	 */
	private void listDealStatus() {
		deal_status = getConsts(DEAL_STATUS_CACHE_KEY);
		if (deal_status == null) {
			deal_status = new ArrayList<Map>();
		}
		toCheckPkey(deal_status);
	}

	/**
	 * 支付方式
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-9
	 */
	private void listPayType() {
		pay_type_list = getConsts(PAY_TYPE_CACHE_KEY);
		if (pay_type_list == null) {
			pay_type_list = new ArrayList<Map>();
		}
		/*
		 * Map m2 = new HashMap(); m2.put("pkey", "FQFK"); m2.put("pname", "分期付款"); Map
		 * m3 = new HashMap(); m3.put("pkey", "POSC"); m3.put("pname", "POS机刷卡");
		 * pay_type_list.add(m2); pay_type_list.add(m3);
		 */
	}

	/**
	 * 是否闪电送
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-9
	 */
	private void listIsQuickShip() {
		is_quick_ship_list = getConsts(IS_QUICK_SHIP);
		if (is_quick_ship_list == null) {
			is_quick_ship_list = new ArrayList<Map>();
		}
		toCheckPkey(is_quick_ship_list);
		/*
		 * Map m2 = new HashMap(); m2.put("pkey", "01"); m2.put("pname", "非闪电送"); Map m3
		 * = new HashMap(); m3.put("pkey", "02"); m3.put("pname", "闪电送");
		 * is_quick_ship_list.add(m2); is_quick_ship_list.add(m3);
		 */
	}

	/**
	 * 配关方式
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-9
	 */
	private void listShippingType() {
		shipping_type_list = getConsts(SHIPPING_TYPE_CACHE_KEY);
		if (shipping_type_list == null) {
			shipping_type_list = new ArrayList<Map>();
		}
		/*
		 * Map m1 = new HashMap(); m1.put("pkey", "00"); m1.put("pname", "发送失败"); Map m2
		 * = new HashMap(); m2.put("pkey", "01"); m2.put("pname", "待接收"); Map m3 = new
		 * HashMap(); m3.put("pkey", "02"); m3.put("pname", "成功接收");
		 * shipping_type_list.add(m1); shipping_type_list.add(m2);
		 * shipping_type_list.add(m3);
		 */
	}

	/**
	 * 闪电送状态
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-9
	 */
	private void listQuickStatus() {
		quick_status_list = getConsts(QUICK_STATUS_CACHE_KEY);
		if (quick_status_list == null) {
			quick_status_list = new ArrayList<Map>();
		}
		toCheckPkey(quick_status_list);

		/*
		 * Map m1 = new HashMap(); m1.put("pkey", "00"); m1.put("pname", "发送失败"); Map m2
		 * = new HashMap(); m2.put("pkey", "01"); m2.put("pname", "待接收"); Map m3 = new
		 * HashMap(); m3.put("pkey", "02"); m3.put("pname", "成功接收");
		 * quick_status_list.add(m1); quick_status_list.add(m2);
		 * quick_status_list.add(m3);
		 */
	}

	/**
	 * 渠道标记,订单发展渠道查询条件
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-9
	 */
	private void listChannelMark() {
		channel_mark_list = getConsts(CHANNEL_MARK_CACHE_KEY);
		if (channel_mark_list == null) {
			channel_mark_list = new ArrayList<Map>();
		}
		/*
		 * Map m1 = new HashMap(); m1.put("pkey", "1"); m1.put("pname", "传统营业厅"); Map m2
		 * = new HashMap(); m2.put("pkey", "2"); m2.put("pname", "电话营销"); Map m3 = new
		 * HashMap(); m3.put("pkey", "3"); m3.put("pname", "非集团合作直销");
		 * channel_mark_list.add(m1); channel_mark_list.add(m2);
		 * channel_mark_list.add(m3);
		 */
	}

	/**
	 * 订单来源查询条件 证件上传类型
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-9
	 */
	private void listOrderFrom() {
		// order_from_list = getConsts(ORDER_FROM_CACHE_KEY);
		// 订单来源
		order_from_list = ecsOrdManager.getDcSqlByDcName("ORDER_FROM");
		if (order_from_list == null) {
			order_from_list = new ArrayList<Map>();
		}
		// 证件上传类型
		role_group_list = ecsOrdManager.getRoleGroup();
		if (role_group_list == null) {
			role_group_list = new ArrayList<Map>();
		}
		/*
		 * if (order_from_list == null) { order_from_list = new ArrayList<Map>(); }else{
		 * //XMJ 修改订单来源排序,另外添加了
		 * /ecsord_server/src/main/java/com/ztesoft/net/model/MapComparator. java/
		 * //如果你先调用MapComparator()排序,请确保sortby字段从1开始 // for(Map
		 * order_from_map:order_from_list) // logger.info(order_from_map);
		 * Collections.sort(order_from_list,new MapComparator()); }
		 */
	}

	private void listPlatType() {
		plat_type_list = getConsts("plat_type");
		if (plat_type_list == null) {
			plat_type_list = new ArrayList<Map>();
		}
	}

	/**
	 * 地市查询条件
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-9
	 */
	private void listRegions() {
		RegionsListReq req = new RegionsListReq();
		req.setRegion_type(RegionsListReq.CITY);
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String province_region = cacheUtil.getConfigInfo("PROVINCE_REGION_CODE");
		req.setRegion_parent_id(province_region);
		RegionsListResp resp = regionService.listRegions(req);
		regionList = resp.getRegionList();
	}

	/**
	 * 是否是历史单查询条件
	 * 
	 * @作者 zhangjun
	 * @创建日期 2014-12-4
	 */
	private void listOrderListIsHis() {
		is_order_his_list = getConsts(StypeConsts.IS_ORDER_HIS);
		if (is_order_his_list == null) {
			is_order_his_list = new ArrayList<Map>();
		}
	}

	/**
	 * 补寄状态
	 * 
	 * @作者 zhangjun
	 * @创建日期 2014-12-4
	 */
	private void listOrderSupplyStatus() {
		order_supply_status_list = getConsts(StypeConsts.ORDER_SUPPLY_STATUS);
		if (order_supply_status_list == null) {
			order_supply_status_list = new ArrayList<Map>();
		}
		toCheckPkey(order_supply_status_list);
	}

	/**
	 * 生产模式
	 * 
	 * @作者 zhangjun
	 * @创建日期 2015-01-18
	 */
	private void listorderModel() {
		order_model_list = getConsts(StypeConsts.DIC_OPER_MODE);
		if (order_model_list == null) {
			order_model_list = new ArrayList<Map>();
		}
		toCheckPkey(order_model_list);
	}

	/**
	 * WMS退单状态
	 */
	private void listWMSRefundStatusList() {
		wms_refund_status_list = getConsts("WMS_REFUND_STATUS");
		if (wms_refund_status_list == null) {
			wms_refund_status_list = new ArrayList<Map>();
		}
		toCheckPkey(wms_refund_status_list);
	}

	/**
	 * 订单系统退单状态
	 */
	private void listRefundStatusList() {
		refund_satus_list = getConsts(StypeConsts.DIC_REFUND_STATUS);
		if (refund_satus_list == null) {
			refund_satus_list = new ArrayList<Map>();
		}
		toCheckPkey(refund_satus_list);

		/*
		 * for(Map mp : refund_satus_list){ Iterator i = mp.entrySet().iterator(); while
		 * (i.hasNext()) { Entry entry = (java.util.Map.Entry)i.next();
		 * logger.info("key:"+entry.getKey()); if("05".equals(entry.getKey())){
		 * mp.remove(entry.getKey()); } } }
		 */

	}

	/**
	 * 公共方法抽取
	 * <p>
	 * Title: toCheckPkey
	 * </p>
	 * <p>
	 * Description: TODO
	 * </p>
	 * 
	 * @author Name
	 * @time 2018年8月3日 下午5:17:24
	 * @version 1.0
	 * @param refund_satus_list2
	 */
	private void toCheckPkey(List<Map> list) {
		Boolean flag = true;
		for (int i = 0; i < list.size(); i++) {
			String key = (String) list.get(i).get("pkey");
			if ("-1".equals(key)) {
				flag = false;
				break;
			}
		}
		if (flag) {
			Map m0 = new HashMap();
			m0.put("pkey", "-1");
			m0.put("pname", "--请选择--");
			list.add(0, m0);
		}
	}

	/**
	 * 订单系统Bss退款状态
	 */
	private void listBssRefundStatusList() {
		bss_refund_satus_list = getConsts(StypeConsts.DIC_BSS_REFUND_STATUS);
		if (bss_refund_satus_list == null) {
			bss_refund_satus_list = new ArrayList<Map>();
		}
		toCheckPkey(bss_refund_satus_list);
	}

	/**
	 * 订单系统订单类型
	 */
	private void listOrderTypeList() {
		order_type_list = getConsts(StypeConsts.DIC_ORDER_NEW_TYPE);
		if (order_type_list == null) {
			order_type_list = new ArrayList<Map>();
		}
	}

	private List<Map> getConsts(String key) {
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<Map> list = dcPublicCache.getList(key);
		return list;
	}

	private void listExpressType() {
		express_type_list = getConsts(UNI_EXPRESS_TYPE_CACHE_KEY);
		if (express_type_list == null) {
			express_type_list = new ArrayList<Map>();
		}
	}

	private void listOrderStatus() {
		order_status_list = getConsts(UNI_ORDER_STATUS_CACHE_KEY);
		if (order_status_list == null) {
			order_status_list = new ArrayList<Map>();
		}
	}

	private void listOrderType() {
		order_type_list = getConsts(UNI_ORDER_TYPE_CACHE_KEY);
		if (order_type_list == null) {
			order_type_list = new ArrayList<Map>();
		}
	}

	private void listOrderSrc() {
		order_src_list = getConsts(UNI_ORDER_SRC_CACHE_KEY);
		if (order_src_list == null) {
			order_src_list = new ArrayList<Map>();
		}
	}

	private void listDataSrc() {
		data_src_list = getConsts(UNI_DATA_SRC_CACHE_KEY);
		if (data_src_list == null) {
			data_src_list = new ArrayList<Map>();
		}
	}

	private void listDataSrcZj() {
		data_src_list = getConsts(ZJ_UNI_DATA_SRC_CACHE_KEY);
		if (data_src_list == null) {
			data_src_list = new ArrayList<Map>();
		}
	}

	private void listUniPayType() {
		pay_type_list = getConsts(UNI_PAY_TYPE_CACHE_KEY);
		if (pay_type_list == null) {
			pay_type_list = new ArrayList<Map>();
		}
	}

	private void listAreaId() {
		area_id_list = getConsts(UNI_AREA_ID_CACHE_KEY);
		if (area_id_list == null) {
			area_id_list = new ArrayList<Map>();
		}
	}

	/**
	 * 获取老系统历史订单列表
	 * 
	 * @return
	 */
	public String ordHisList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		this.listExpressType();
		this.listOrderStatus();
		this.listOrderType();
		this.listOrderSrc();
		this.listDataSrc();
		this.listUniPayType();
		this.listAreaId();
		if (params == null) {
			params = new OrderQueryParams();
			params.setArea_id("-1");
			params.setOrder_src("-1");
			params.setOrder_status("-1");
			params.setOrder_type("-1");
			params.setData_src("-1");
			params.setExpress_type("-1");
			params.setPay_type("-1");
		}
		this.webpage = ecsOrdManager.qryOrdHisList(this.getPage(), this.getPageSize(), params);
		return "order_print_list";
	}

	/**
	 * 保存订单列表处提交的订单备注
	 * 
	 * @return
	 */
	public String saveOrderHisComments() {
		ecsOrdManager.dealDealInfo(order_id, dealDesc);// 备注信息在方法的DESC字段
		this.json = "{result:0,message:'备注添加成功" + dealDesc + "'}";
		return this.JSON_MESSAGE;

	}

	public String showBatchPreOrderList() {
		long start = System.currentTimeMillis();
		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_return_back)) {
			ThreadContextHolder.getSessionContext().setAttribute("order_query_params", new OrderQueryParams());
		} else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_return_back)) {
			params = (OrderQueryParams) ThreadContextHolder.getSessionContext().getAttribute("order_query_params");
		}
		if (params == null) {
			params = new OrderQueryParams();
		}
		if (StringUtil.isEmpty(params.getOrder_id()) && StringUtil.isEmpty(params.getOut_tid())
				&& StringUtil.isEmpty(params.getQuery_btn_flag())) {
			Calendar date = new GregorianCalendar();
			date.add(Calendar.DATE, -1);
			String startTime = DF.format(date.getTime());
			String endTime = DF.format(new Date());
			if (StringUtil.isEmpty(params.getCreate_start())) {
				params.setCreate_start(startTime);
			}
			if (StringUtil.isEmpty(params.getCreate_end())) {
				params.setCreate_end(endTime);
			}
		}
		if (StringUtils.isNotEmpty(start_time) && StringUtils.isNotEmpty(params.getCreate_start())) {
			params.setCreate_start(start_time);
		}
		if (StringUtils.isNotEmpty(end_time) && StringUtils.isNotEmpty(params.getCreate_end())) {
			params.setCreate_end(end_time);
		}
		// 地市
		this.listRegions();
		// 批量预处理商品
		this.listBatchPreGoods();
		this.listOrderFrom();
		long end = System.currentTimeMillis();
		ThreadContextHolder.getSessionContext().setAttribute("order_query_params", params);
		logger.info("列表查询时间=========================================================>" + (end - start));
		if (!"yes".equals(first_load)) {
			try {
				this.webpage = ecsOrdManager.queryOrderForPageByBatchPre(this.getPage(), this.getPageSize(), params);
				long end1 = System.currentTimeMillis();
				logger.info("记录查询返回时间=========================================================>" + (end1 - end));
			} catch (Exception e) {
				e.printStackTrace();
				String msg = e.getMessage();
			}
		}
		return "batch_pre_ord_list";
	}

	/**
	 * 批量预处理商品
	 */
	private void listBatchPreGoods() {
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		batch_pre_goods_list = dcPublicCache.getBatchPreGoodsList();
		if (null == batch_pre_goods_list) {
			batch_pre_goods_list = new ArrayList<Map>();
		}
	}

	/**
	 * 显示订单列表页面
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-6
	 * @return
	 */

        public String showOrderList() throws Exception {
		Long time1 = System.currentTimeMillis();
		long start = System.currentTimeMillis();
		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_return_back)) {
			ThreadContextHolder.getSessionContext().setAttribute("order_query_params", new OrderQueryParams());
		} else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_return_back)) {
			params = (OrderQueryParams) ThreadContextHolder.getSessionContext().getAttribute("order_query_params");
		}

		if (params == null) {
			params = new OrderQueryParams();
			// if(!EcsOrdManager.QUERY_TYPE_EXCEPTION.equals(query_type)){//异常订单管理页面,“订单类型”改为默认“请选择”；
			// params.setOrder_type(EcsOrderConsts.ORDER_TYPE_03);//默认订购
			// }
		}

		if (EcsOrdManager.QUERY_TYPE_ORDER_RECOVER.equals(query_type)) {// 归档恢复只查历史订单
			params.setOrder_is_his(EcsOrderConsts.IS_ORDER_HIS_YES);
		}
		// 根据料箱好查询单号
		if (!StringUtil.isEmpty(boxCode)) {
			GetOrdByBoxIdFromWMSReq req = new GetOrdByBoxIdFromWMSReq();
			GetOrdByBoxIdFromWMSResp resp = new GetOrdByBoxIdFromWMSResp();
			try {
				req.setBoxCode(boxCode);
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				resp = client.execute(req, GetOrdByBoxIdFromWMSResp.class);
				if (null != resp && !StringUtils.isEmpty(resp.getOrderId())) {
					params.setOrder_id(resp.getOrderId());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (StringUtil.isEmpty(params.getOrder_id()) && StringUtil.isEmpty(params.getOut_tid())
				&& StringUtil.isEmpty(params.getQuery_btn_flag())) {
			Calendar date = new GregorianCalendar();
			date.add(Calendar.DATE, -7);
			String startTime = DF.format(date.getTime());
			date.add(Calendar.MONTH, -1);
			String startTimeMonth = DF.format(date.getTime());
			String endTime = DF.format(new Date());

			if (StringUtil.isEmpty(params.getCreate_start())) {
				if ("returned".equals(query_type) || "returned_cfm".equals(query_type)
						|| "returned_bss".equals(query_type) || "returned_cfmn".equals(query_type)) {
					if ("1".equals(orderReceiveRetrunMark)) {// 如果试订单领取返回页面，则不起始时间
					} else {
						params.setCreate_start(startTimeMonth);
					}
				} else {
					if ("1".equals(orderReceiveRetrunMark)) {// 如果试订单领取返回页面，则不起始时间
					} else {
						params.setCreate_start(startTime);
					}
				}
			}
			if (StringUtil.isEmpty(params.getCreate_end())) {
				if ("1".equals(orderReceiveRetrunMark)) {// 如果试订单领取返回页面，则不起始时间
				} else {
					params.setCreate_end(endTime);
				}
			}
		}

		params.setQuery_type(query_type);
		if (EcsOrdManager.QUERY_TYPE_ORDER.equals(query_type)
				|| EcsOrdManager.QUERY_TYPE_EXCEPTION.equals(query_type)) {// 异常单
			params.setRefund_deal_type(EcsOrderConsts.REFUND_DEAL_TYPE_01);
		}
		if (EcsOrdManager.QUERY_TYPE_ORDER_RECEIVE.equals(query_type)
				|| EcsOrdManager.QUERY_TYPE_ORDER_RECEIVE2.equals(params.getQuery_type())) {
			params.setAbnormal_type(EcsOrderConsts.ORDER_ABNORMAL_TYPE_0);
		}
		Long time2 = System.currentTimeMillis();
		logger.info("性能测试****************************订单查询条件1封装耗时：" + (time2 - time1));
		this.listRegions();
		this.listChannelMark();
		this.listIsQuickShip();
		this.listOrderFrom();
		this.listPlatType();
		this.listPayType();
		this.listQuickStatus();
		this.listShippingType();
		this.listShiipingCop();
		this.listDealStatus();
		this.listExceptionType();
		this.listFlowTrace();
		this.listExceptionTypeList();
		this.listDcModeRegion();
		this.listOrderListIsHis();
		this.listOrderSupplyStatus();
		this.listorderModel();
		this.listWMSRefundStatusList();
		this.listRefundStatusList();
		this.listBssRefundStatusList();
		this.listOrderTypeList();
		this.listOrderQryStatus();
		this.listTopSeedProfessionalLine();
		this.listTopSeedType();
		this.listTopSeedGroup();
		// add by zhaochen 2018-11-1 如果是订单查询页面，根据工号权限过滤地市县分
		this.filterRegionCountyByPermission();

		Long time3 = System.currentTimeMillis();
		logger.info("性能测试****************************订单查询条件2封装耗时：" + (time3 - time2));
		if (StringUtils.isNotEmpty(start_time) && StringUtils.isNotEmpty(params.getCreate_start())) {
			params.setCreate_start(start_time);
		}
		if (StringUtils.isNotEmpty(end_time) && StringUtils.isNotEmpty(params.getCreate_end())) {
			params.setCreate_end(end_time);
		}
		if (StringUtils.isNotEmpty(platform_locked_status)) {
			params.setStatus(platform_locked_status);
			// 工作台统计查询所有锁定订单
			params.setOrder_type("");
		}
		// 流程
		if (StringUtils.isNotEmpty(trace_id)) {
			params.setFlow_id(trace_id);
			for (Map mp : flowTraceList) {
				if (mp.get("pkey").equals(trace_id)) {
					params.setFlow_id_c(mp.get("pname").toString());
				}
			}
		}
		long end = System.currentTimeMillis();
		// logger.info("列表查询时间=========================================================>"+(end-start));
		if (EcsOrdManager.QUERY_TYPE_EXCEPTION.equals(query_type)) {// 异常单
			List<Map> etlist = new ArrayList<Map>();
			for (Map m : orderExceptionTypeList) {
				String pkey = (String) m.get("pkey");
				if (!EcsOrderConsts.ORDER_ABNORMAL_TYPE_2.equals(pkey)
						&& !EcsOrderConsts.ORDER_ABNORMAL_TYPE_0.equals(pkey)) {
					etlist.add(m);
				}
			}
			orderExceptionTypeList = etlist;
			params.setIs_exception(EcsOrderConsts.ORDER_ABNORMAL_STATUS_1);
			params.setRefund_deal_type(EcsOrderConsts.REFUND_DEAL_TYPE_01);
		} else if (EcsOrdManager.QUERY_TYPE_RETURNED.equals(query_type)) {
			// params.setNot_flow_model_code(EcsOrderConsts.OPER_MODE_ZD);
			params.setQuick_shipping_status(EcsOrderConsts.SHIPPING_QUICK_02);
			params.setVisible_status(-1);
			params.setRefund_deal_type(EcsOrderConsts.REFUND_DEAL_TYPE_01);
		} else if (EcsOrdManager.QUERY_TYPE_RETURNED_CFM.equals(query_type)
				|| EcsOrdManager.QUERY_TYPE_RETURNED_BSS.equals(query_type)
				|| EcsOrdManager.QUERY_TYPE_RETURNED_CFMN.equals(query_type)) {
			params.setRefund_deal_type(EcsOrderConsts.REFUND_DEAL_TYPE_02);
			params.setVisible_status(-1);
			// params.setRefund_status(EcsOrderConsts.RETURNED_STATUS_02);
		} else if (EcsOrdManager.QUERY_TYPE_REFUND_APPLY.equals(query_type)) {
			// params.setNot_flow_model_code(EcsOrderConsts.OPER_MODE_ZD);
			params.setVisible_status(-1);
		} else if (EcsOrdManager.QUERY_TYPE_REFUND_AUDIT.equals(query_type)) {
			params.setVisible_status(-1);
			// params.setRefund_status(EcsOrderConsts.RETURNED_STATUS_02);
		} else if (EcsOrdManager.QUERY_TYPE_SUPPLY.equals(query_type)) {
			params.setVisible_status(-1);
		} else if (EcsOrdManager.QUERY_TYPE_ORDER_VIEW.equals(query_type)) {
			// if(params.getVisible_status()==0){
			// params.setVisible_status(-1);
			// }
			this.setIs_order_view("order_view");

		} else if (EcsOrdManager.QUERY_TYPE_ORDER_QH.equals(query_type)) {// 取货页面
			params.setOrder_status(String.valueOf(ZjEcsOrderConsts.DIC_ORDER_STATUS_72));
			params.setCreate_start("");
			params.setCreate_end("");
			if (StringUtils.isEmpty(params.getOrder_sn()) && StringUtils.isEmpty(params.getIccid())) {
				params.setOrder_sn("-1");
				params.setIccid("-1");
			}
		} else if (EcsOrdManager.QUERY_TYPE_ORDER_RECEIVE.equals(query_type)
				|| EcsOrdManager.QUERY_TYPE_ORDER_RECEIVE2.equals(params.getQuery_type())) {
			/*
			 * params.setLock_status(String.valueOf(ZjEcsOrderConsts.LOCK_STATUS ));
			 * params.setCreate_start(""); params.setCreate_end("");
			 */
		}
		Long time4 = System.currentTimeMillis();
		logger.info("性能测试****************************订单查询条件3封装耗时：" + (time4 - time3));
		ThreadContextHolder.getSessionContext().setAttribute("order_query_params", params);
		if (!"yes".equals(first_load)) {// 首次加载页面不做查询，当前仅归档恢复页面用到，其他页面要使用可在es_menu配置参数

			try {
				this.webpage = ecsOrdManager.queryOrderForPageBuyFlow(this.getPage(), this.getPageSize(), params);
				long end1 = System.currentTimeMillis();
				// logger.info("记录查询返回时间=========================================================>"+(end1-end));
			} catch (Exception e) {
				e.printStackTrace();
				String msg = e.getMessage();
			}
		}
		Long time5 = System.currentTimeMillis();
		logger.info("性能测试****************************订单查询总耗时：" + (time5 - time4));
		if (EcsOrdManager.QUERY_TYPE_ORDER_QH.equals(query_type)) {// 取货页面
			if ((!StringUtils.isEmpty(params.getOrder_sn()) && "-1".equals(params.getOrder_sn()))
					&& (!StringUtils.isEmpty(params.getIccid()) && "-1".equals(params.getIccid()))) {
				params.setOrder_sn("");
				params.setIccid("");
			}
		}
		// 获取登录用户名
		this.setUserid(ManagerUtils.getUserId());
		params.setUsername(userid);

		if ("returned".equals(query_type) || "returned_cfm".equals(query_type) || "returned_bss".equals(query_type)
				|| "returned_cfmn".equals(query_type)) {
			return "ord_return_apply_list";
		} else if (EcsOrdManager.QUERY_TYPE_SUPPLY.equals(query_type)) {
			return "order_manager";
		} else if (EcsOrdManager.QUERY_TYPE_ORDER_VIEW.equals(query_type)) {
			return "auto_ord_list_his";
		} else if (EcsOrdManager.QUERY_TYPE_ORDER_RECOVER.equals(query_type)) {
			return "order_recover_list";
		} else if (EcsOrdManager.QUERY_TYPE_LOGISTICS.equals(query_type)) {
			return "auto_ord_logistics_list";
		} else if (EcsOrdManager.QUERY_TYPE_LOGISTICS_PRINT.equals(query_type)) {
			return "delivery_order_print_list";
		} else if (EcsOrdManager.QUERY_TYPE_BSS_PARALLEL.equals(query_type)) {
			return "bss_parallel_list";
		} else if (EcsOrdManager.QUERY_TYPE_ORDER_QH.equals(query_type)) {
			return "auto_ord_list_qh";
		} else if (EcsOrdManager.QUERY_TYPE_ORDER_RECEIVE.equals(query_type)) {
			// 照片上传状态
			order_vplan_list = ecsOrdManager.getDcSqlByDcName("photo_up_status");
			return "auto_ord_receive";
		} else if (EcsOrdManager.QUERY_TYPE_ORDER_RECEIVE2.equals(params.getQuery_type())) {
			return "auto_ord_receive2";
		} else if ("refund_apply".equals(query_type) || "refund_audit".equals(query_type)) {
			return "ord_refund_list";
		} else {
			return "auto_ord_list";
		}
	}

	/**
	 * 显示外呼订单列表页面
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-6
	 * @return
	 */
	public String showCallOutOrderList() {
		long start = System.currentTimeMillis();

		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_return_back)) {
			ThreadContextHolder.getSessionContext().setAttribute("order_query_params", new OrderQueryParams());
		} else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_return_back)) {
			params = (OrderQueryParams) ThreadContextHolder.getSessionContext().getAttribute("order_query_params");
		}

		if (params == null) {
			params = new OrderQueryParams();
			// if(!EcsOrdManager.QUERY_TYPE_EXCEPTION.equals(query_type)){//异常订单管理页面,“订单类型”改为默认“请选择”；
			// params.setOrder_type(EcsOrderConsts.ORDER_TYPE_03);//默认订购
			// }
		}

		params.setOrder_status("11");
		if (!StringUtil.isEmpty(params.getStatus()) && "-1".equals(params.getStatus())) {
			params.setStatus("");
		}
		if (EcsOrdManager.QUERY_TYPE_ORDER_RECOVER.equals(query_type)) {// 归档恢复只查历史订单
			params.setOrder_is_his(EcsOrderConsts.IS_ORDER_HIS_YES);
		}
		// 根据料箱好查询单号
		if (!StringUtil.isEmpty(boxCode)) {
			GetOrdByBoxIdFromWMSReq req = new GetOrdByBoxIdFromWMSReq();
			GetOrdByBoxIdFromWMSResp resp = new GetOrdByBoxIdFromWMSResp();
			try {
				req.setBoxCode(boxCode);
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				resp = client.execute(req, GetOrdByBoxIdFromWMSResp.class);
				if (null != resp && !StringUtils.isEmpty(resp.getOrderId())) {
					params.setOrder_id(resp.getOrderId());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/*
		 * if(StringUtil.isEmpty(params.getOrder_id()) &&
		 * StringUtil.isEmpty(params.getOut_tid()) &&
		 * StringUtil.isEmpty(params.getQuery_btn_flag())){ Calendar date = new
		 * GregorianCalendar(); date.add(Calendar.DATE, -1); String startTime =
		 * DF.format(date.getTime()); date.add(Calendar.MONTH, -1); String
		 * startTimeMonth = DF.format(date.getTime()); String endTime = DF.format(new
		 * Date());
		 * 
		 * if(StringUtil.isEmpty(params.getCreate_start())){
		 * if("returned".equals(query_type) || "returned_cfm".equals(query_type)){
		 * params.setCreate_start(startTimeMonth); }else{
		 * params.setCreate_start(startTime); } }
		 * if(StringUtil.isEmpty(params.getCreate_end())){
		 * params.setCreate_end(endTime); } }
		 */
		params.setQuery_type("call_out_order");

		if (EcsOrdManager.QUERY_TYPE_ORDER.equals(query_type)
				|| EcsOrdManager.QUERY_TYPE_EXCEPTION.equals(query_type)) {
			params.setRefund_deal_type(EcsOrderConsts.REFUND_DEAL_TYPE_01);
		}
		this.listRegions();
		this.listChannelMark();
		this.listIsQuickShip();
		this.listOrderFrom();
		this.listPlatType();
		this.listPayType();
		this.listQuickStatus();
		this.listShippingType();
		this.listShiipingCop();
		this.listDealStatus();
		this.listExceptionType();
		this.listFlowTrace();
		this.listExceptionTypeList();
		this.listDcModeRegion();
		this.listOrderListIsHis();
		this.listOrderSupplyStatus();
		this.listorderModel();
		this.listWMSRefundStatusList();
		this.listRefundStatusList();
		this.listBssRefundStatusList();
		this.listOrderTypeList();
		this.listOutcallTypeList();

		if (StringUtils.isNotEmpty(start_time) && StringUtils.isNotEmpty(params.getCreate_start())) {
			params.setCreate_start(start_time);
		}
		if (StringUtils.isNotEmpty(end_time) && StringUtils.isNotEmpty(params.getCreate_end())) {
			params.setCreate_end(end_time);
		}
		if (StringUtils.isNotEmpty(platform_locked_status)) {
			params.setStatus(platform_locked_status);
			// 工作台统计查询所有锁定订单
			params.setOrder_type("");
		}
		// 流程
		if (StringUtils.isNotEmpty(trace_id)) {
			params.setFlow_id(trace_id);
			for (Map mp : flowTraceList) {
				if (mp.get("pkey").equals(trace_id)) {
					params.setFlow_id_c(mp.get("pname").toString());
				}
			}
		}
		// 外呼类型
		if (StringUtils.isNotEmpty(params.getOutcall_type())) {
			params.setOutcall_type(this.changeOutcallTypeParams(params.getOutcall_type()));
		}
		long end = System.currentTimeMillis();
		// logger.info("列表查询时间=========================================================>"+(end-start));
		if (EcsOrdManager.QUERY_TYPE_EXCEPTION.equals(query_type)) {
			List<Map> etlist = new ArrayList<Map>();
			for (Map m : orderExceptionTypeList) {
				String pkey = (String) m.get("pkey");
				if (!EcsOrderConsts.ORDER_ABNORMAL_TYPE_2.equals(pkey)
						&& !EcsOrderConsts.ORDER_ABNORMAL_TYPE_0.equals(pkey)) {
					etlist.add(m);
				}
			}
			orderExceptionTypeList = etlist;
			params.setIs_exception(EcsOrderConsts.ORDER_ABNORMAL_STATUS_1);
			params.setRefund_deal_type(EcsOrderConsts.REFUND_DEAL_TYPE_01);
		} else if (EcsOrdManager.QUERY_TYPE_RETURNED.equals(query_type)) {
			// params.setNot_flow_model_code(EcsOrderConsts.OPER_MODE_ZD);
			params.setQuick_shipping_status(EcsOrderConsts.SHIPPING_QUICK_02);
			params.setVisible_status(-1);
			params.setRefund_deal_type(EcsOrderConsts.REFUND_DEAL_TYPE_01);
		} else if (EcsOrdManager.QUERY_TYPE_RETURNED_CFM.equals(query_type)) {
			params.setRefund_deal_type(EcsOrderConsts.REFUND_DEAL_TYPE_02);
			params.setVisible_status(-1);
			// params.setRefund_status(EcsOrderConsts.RETURNED_STATUS_02);
		} else if (EcsOrdManager.QUERY_TYPE_SUPPLY.equals(query_type)) {
			params.setVisible_status(-1);
		} else if (EcsOrdManager.QUERY_TYPE_ORDER_VIEW.equals(query_type)) {
			// if(params.getVisible_status()==0){
			// params.setVisible_status(-1);
			// }

		}
		ThreadContextHolder.getSessionContext().setAttribute("order_query_params", params);
		if (!"yes".equals(first_load)) {// 首次加载页面不做查询，当前仅归档恢复页面用到，其他页面要使用可在es_menu配置参数
			try {
				this.webpage = ecsOrdManager.queryOrderForPageBuyFlow(this.getPage(), this.getPageSize(), params);
				long end1 = System.currentTimeMillis();
				// logger.info("记录查询返回时间=========================================================>"+(end1-end));
			} catch (Exception e) {
				e.printStackTrace();
				String msg = e.getMessage();
			}
		}
		if ("returned".equals(query_type) || "returned_cfm".equals(query_type)) {
			return "ord_return_apply_list";
		} else if (EcsOrdManager.QUERY_TYPE_SUPPLY.equals(query_type)) {
			return "order_manager";
		} else if (EcsOrdManager.QUERY_TYPE_ORDER_VIEW.equals(query_type)) {
			return "auto_ord_list_his";
		} else if (EcsOrdManager.QUERY_TYPE_ORDER_RECOVER.equals(query_type)) {
			return "order_recover_list";
		} else if (EcsOrdManager.QUERY_TYPE_LOGISTICS.equals(query_type)) {
			return "auto_ord_logistics_list";
		} else {
			return "call_out_list";
		}
	}

	/**
	 * 显示系统首页订单分布页面
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-6
	 * @return
	 */
	public String showSysHomeList() {
		/* long begin = System.currentTimeMillis(); */
		// 查询状态分布
		List<Map> order_status_group = ecsOrdManager.queryOrderStatusGroup();
		// 查询领取分布
		List<Map> order_get_group = ecsOrdManager.queryOrderGetGroup();

		/*
		 * long end = System.currentTimeMillis(); logger.info(
		 * "查询时间：============================================"+(end-begin));
		 */
		String status_group = JSONArray.toJSONString(order_status_group);
		String get_group = JSONArray.toJSONString(order_get_group);
		this.json = "{'result':0,'return_data':{'status_group': " + status_group + ",'get_group': " + get_group + "}}";
		return JSON_MESSAGE;

	}

	/**
	 * 系统首页柱状图数据获取
	 */
	public String showSysHomeListWithAccountOpen() {
		// List<Map> account_noopen_group =
		// ecsOrdManager.queryAccount_noopen_group();
		// List<Map> account_noopen_op_group =
		// ecsOrdManager.queryAccount_noopen_op_group();

		List<Map> account_noopen_group = ecsOrdManager.queryAccount_noopen_group();
		List<Map> account_audit_group = ecsOrdManager.queryAccount_audit_group();

		String noop_group = JSONArray.toJSONString(account_noopen_group);
		String audit_group = JSONArray.toJSONString(account_audit_group);
		this.json = "{'result':0,'return_data2':{'noop_group': " + noop_group + ",'audit_group': " + audit_group + "}}";
		// this.json = "{'result':0,'return_data2':{'noop_group':
		// "+noop_group+"}}";
		return JSON_MESSAGE;

	}

	/**
	 * 显示系统首页订单分布页面
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-6
	 * @return
	 */
	public String showSysHome() {

		return "roleinput";
	}

	// 校验订单配货环节录入的终端串号与现取货的终端串号是否一致
	public String validateTerminalNum() {
		// 参数非空校验
		if (StringUtil.isEmpty(terminal_nums)) {
			super.json = "{'result':'-1','message':'录入的终端串号不能为空！'}";
			return super.JSON_MESSAGE;
		}
		if (StringUtil.isEmpty(order_sn)) {
			super.json = "{'result':'-1','message':'录入的订单领取号不能为空！'}";
			return super.JSON_MESSAGE;
		}
		// 串号一致性校验
		Map result = this.ecsOrdManager.validateTerminalNum(terminal_nums, order_sn);
		if (ConstsCore.ERROR_SUCC.equals(result.get("error_code"))) {
			super.json = "{'result':'0','message':'校验成功！','order_id':'" + result.get("order_id") + "'}";
		} else {
			super.json = "{'result':'-1','message':'校验失败，终端串号和配货录入的终端串号不匹配！'}";
		}
		return super.JSON_MESSAGE;
	}

	public String exePhPlan() {
		String type_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		if (!EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(type_id) && !EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(type_id)
				&& !EcsOrderConsts.GOODS_TYPE_FENXIANGKA.equals(type_id)
				&& !EcsOrderConsts.GOODS_TYPE_HUANKA.equals(type_id)) {
			TacheFact fact = new TacheFact();
			fact.setOrder_id(order_id);
			PlanRuleTreeExeResp resp = RuleFlowUtil.executePlanRuleTree(ZjEcsOrderConsts.YJH_PLAN_ID,
					EcsOrderConsts.RULE_EXE_ALL, fact, false, EcsOrderConsts.DEAL_FROM_PAGE, null, null);
			if (ConstsCore.ERROR_SUCC.equals(resp.getError_code())) {
				super.json = "{'result':'0','message':'配货成功！'}";
			} else {
				super.json = "{'result':'-1','message':'配货失败！'}";
			}
		}

		return super.JSON_MESSAGE;
	}

	public String checkFh() {
		String hasTerminal = CommonDataFactory.getInstance().hasTerminal(order_id);
		if (ZjEcsOrderConsts.IS_DEFAULT_VALUE.equals(hasTerminal)) {
			super.json = "{'result':'0','message':'订单存在手机终端，请取货！','has_terminal':'1'}";
		} else {
			super.json = "{'result':'0','message':'订单可以发货！','has_terminal':'0'}";
		}
		return super.JSON_MESSAGE;
	}

	// 执行发货方案
	public String exeFhPlan() {
		OrderTreeBusiRequest otreq = CommonDataFactory.getInstance().getOrderTree(order_id);
		String flow_trace_id = otreq.getOrderExtBusiRequest().getFlow_trace_id();
		if ("1".equals(otreq.getOrderExtBusiRequest().getIs_work_custom())) {
			// 处理自定义流程
			try {
				// 执行自定义流程
				this.workCutomOrderContinue(flow_trace_id);

				super.json = "{'result':'0','message':'订单 " + order_id + " 发货成功！'}";
			} catch (Exception e) {
				super.json = "{'result':'-1','message':'校验成功，但发货处理失败！'}";
				e.printStackTrace();
			}
		} else {
			PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
			TacheFact fact = new TacheFact();
			fact.setOrder_id(this.order_id);
			req.setPlan_id(ZjEcsOrderConsts.FA_HUO_PLAN);
			req.setFact(fact);
			req.setDeleteLogs(false);// 不删除日志，不允许二次操作
			req.setAuto_exe(-1);
			PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
			if (ConstsCore.ERROR_SUCC.equals(planResp.getError_code())) {
				super.json = "{'result':'0','message':'订单 " + order_id + " 发货成功！'}";
			} else {
				super.json = "{'result':'-1','message':'校验成功，但发货处理失败！'}";
			}
		}
		if ("H".equals(flow_trace_id)) {
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String logisticsSMS = cacheUtil.getConfigInfo("logisticsSMS");
			if (!StringUtil.isEmpty(logisticsSMS) && "1".equals(logisticsSMS)) {
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				BusiCompRequest busiCompRequest = new BusiCompRequest();
				busiCompRequest.setEnginePath("zteCommonTraceRule.logisticsSMS");
				busiCompRequest.setOrder_id(order_id);
				Map queryParams = new HashMap();
				busiCompRequest.setQueryParams(queryParams);
				BusiCompResponse busiCompResponse = client.execute(busiCompRequest, BusiCompResponse.class);
			}

		}
		return super.JSON_MESSAGE;
	}

	/**
	 * 营业日报
	 */

	public String getOrderBusinessReport() {

		// this.listRegions();
		// this.listChannelMark();
		long start = System.currentTimeMillis();
		this.listOrderFrom();

		// 处理参数并根据是否返回状态把查询参数放入session
		if (params == null) {
			params = new OrderQueryParams();
		}
		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_return_back)) {// 是否调用返回方法，0为往session里放查询条件，
			ThreadContextHolder.getSessionContext().setAttribute("order_query_params", new OrderQueryParams());
		} else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_return_back)) {// 是否调用返回方法，
																			// 1则从session取查询条件添加到页面
			params = (OrderQueryParams) ThreadContextHolder.getSessionContext().getAttribute("order_query_params");
		}

		// 订单ID,outtid,query_btn_flag都为空的话，默认放入一个查询的10天以内的时间
		if (StringUtil.isEmpty(params.getOrder_id()) && StringUtil.isEmpty(params.getOut_tid())
				&& StringUtil.isEmpty(params.getQuery_btn_flag())) {
			Calendar date = new GregorianCalendar();
			date.add(Calendar.DAY_OF_MONTH, -10);
			String startTime = DF2.format(date.getTime());
			String endTime = DF2.format(new Date());
			if (StringUtil.isEmpty(params.getCreate_start())) {
				params.setCreate_start(startTime);
			}
			if (StringUtil.isEmpty(params.getCreate_end())) {
				params.setCreate_end(endTime);
			}
		}
		// 对日期进行格式化
		params.setCreate_start(params.getCreate_start() + " 00:00:00");
		params.setCreate_end(params.getCreate_end() + " 23:59:59");

		ThreadContextHolder.getSessionContext().setAttribute("order_query_params", params);// 往session里塞新的查询参数

		if (ConstsCore.CONSTS_YES.equals(params.getQuery_btn_flag())) {// 页面点击按钮查询才加载数据.如果页面按钮查询标志为yes,则开始查询
			// 添加查询参数
			params.setUsername(username);// 添加工号参数
			this.webpage = this.ecsOrdManager.queryOrderBusinessReportList(this.getPage(), this.getPageSize(), params);// 执行订单查询，查询结果放入父类继承过来的的webpage
																														// 的page对象.
		}

		String[] title = new String[] { "写卡完成时间", "订单日期", "订单处理人", "操作类型", "数据来源", "订单来源", "订单编号", "订单类型", "商品类型",
				"商品名称", "套餐名称", "新用户/老用户", "首月模式", "合约期限", "地市", "订购号码", "入网姓名", "证件类型", "证件号码", "收件人", "收件人电话", "收货地址",
				"支付方式", "开户方式", "配送方式", "物流类型", "物流单号", "手机串号", "发票编号", "手机", "优惠活动及赠品", "买家备注或留言", "商城实收", "营业额",
				"自提地址", "营业厅收货人", "营业厅联系电话", "自提营业厅" };
		String[] content = { "card_write_time", "tid_time", "order_operator", "order_type", "order_plat_type",
				"order_from_str", "out_order_id", "order_type", "goods_type2", "goodsname", "plan_title", "is_old",
				"first_payment", "contract_month", "order_city", "phone_num", "phone_owner_name", "certi_type",
				"cert_card_num", "ship_name", "ship_tel", "ship_addr", "pay_type", "order_model", "ship_type",
				"shipping_company", "logi_no", "terminal_num", "invoice_no", "cellphone_type", "discountname",
				"buyer_message", "paymoney", "busi_money", "pickup_self_address", "outlet_receiver",
				"outlet_contact_number", "outlet_pickup_selft" };
		String fileTitle = "营业报表";

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);// 将查询的page数据放入request对象

		if (excel != null && !"".equals(excel)) {// 如果excel标记不为空，那么准备导出数据
			List list = this.webpage.getResult();// 取出页中的记录
			long total_count = webpage.getTotalCount();// 取出总记录数

			if ("check".equals(excel)) {// 如果excel标记为check
				if (total_count > pageSize) {// 同时记录少于10000，那么结果为成功，否则结果为失败，最后返回。
					this.json = "{result:0,count:" + pageSize + "}";
				} else {
					this.json = "{result:1}";
				}
				return this.JSON_MESSAGE;
			}

			if (webpage.getTotalCount() > this.getPage()) {// 如果页数大大于1000条，那么就改成一万，
				if (total_count > pageSize) {
					total_count = pageSize;
				}
				Page pageList = ecsOrdManager.queryOrderBusinessReportList(1, (int) total_count, params);// 重新开始取数据，取1-10000.
				list = pageList.getResult();
			}

			DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());// 导出工具开始导出数据。

			params.setCreate_start(params.getCreate_start().substring(0, 10));
			params.setCreate_end(params.getCreate_end().substring(0, 10));
			return null;

		} else {
			// 查询完毕后重新对显示到页面的日期进行格式化
			params.setCreate_start(params.getCreate_start().substring(0, 10));
			params.setCreate_end(params.getCreate_end().substring(0, 10));

			return "order_business_report";
		}

	}

	/**
	 * 宽带电商各环节订单监控表
	 * 
	 * @throws Exception
	 * 
	 */
	public String getBroadbandMonitorReport() throws Exception {
		if (params == null) {
			params = new OrderQueryParams();
		}
		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_return_back)) {
			// 是否调用返回方法，0为往session里放查询条件，
			ThreadContextHolder.getSessionContext().setAttribute("order_query_params", new OrderQueryParams());
		} else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_return_back)) {
			// 是否调用返回方法，
			// 1则从session取查询条件添加到页面
			params = (OrderQueryParams) ThreadContextHolder.getSessionContext().getAttribute("order_query_params");
		}
		// 订单query_btn_flag都为空的话，默认放入一个查询的10天以内的时间
		if (StringUtil.isEmpty(params.getQuery_btn_flag())) {
			Calendar date = new GregorianCalendar();
			date.add(Calendar.DAY_OF_MONTH, -0);
			String startTime = DF3.format(date.getTime());

			if (StringUtil.isEmpty(params.getCreate_start())) {
				params.setCreate_start(startTime);
			}

		}
		/*
		 * 执行订单查询，查询结果放入父类继承过来的的webpage 的page对象.
		 */
		String nowDate = DF3.format(new Date());
		boolean flag = true;
		if (!StringUtil.isEmpty(params.getCreate_start()) && nowDate.equals(params.getCreate_start())) {
			flag = false;
		} else if (!StringUtil.isEmpty(params.getCreate_start()) && !nowDate.equals(params.getCreate_start())) {
			flag = true;
		} else {
			flag = false;
		}
		if (flag) {
			// 以往历史表进行查询 此处暂时未进行涉及到该数据信息
			if (StringUtils.isNotBlank(this.excel)) {
				// 导出数据
				if ("check".equals(excel)) {// 如果excel标记为check
					// 数量校验
					this.json = "{result:1}";

					return this.JSON_MESSAGE;
				} else {
					String[] title = new String[] { "地市", "当日预约单", "当月预约单", "中台当日领单量", "中台当月领单量", "平均领取时间(Min)",
							"中台当日未领单量", "中台当月未领单量", "中台当日派单量", "中台当月派单量", "平均派单时间(Min)", "中台当日退单量", "中台当月退单量",
							"当日挂起订单量", "当月挂起订单量", "当日已竣工量", "当月已竣工量", "外线装机当日退单量", "外线装机当月退单量" };
					String[] content = { "city_name", "book_day_count", "book_month_count", "receive_day_count",
							"receive_month_count", "avg_receive_time", "unreceive_day_count", "unreceive_month_count",
							"distribute_day_count", "distribute_month_count", "avg_distribute_time",
							"distribute_return_day_count", "distribute_return_month_count", "hang_day_count",
							"hang_month_count", "complete_day_count", "complete_month_count",
							"outside_return_day_count", "outside_return_month_count" };

					String fileTitle = "宽带电商订单监控信息导出_" + params.getCreate_start();
					HttpServletRequest request = ServletActionContext.getRequest();
					request.setAttribute("webpage", this.webpage);// 将查询的page数据放入request对象

					// 报表导出
					List list = ecsOrdManager.queryBroadbandMonitorListDtl_His(params);

					DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());// 导出工具开始导出数据。
					return null;
				}
			} else {

				int pageNo = 1;
				int pageSize = 20;
				if (ConstsCore.CONSTS_YES.equals(params.getQuery_btn_flag())) {// 页面点击按钮查询才加载数据.如果页面按钮查询标志为yes,则开始查询
					this.webpage = this.ecsOrdManager.queryBroadbandMonitorList_His(pageNo, pageSize, params);
					logger.info("宽带数据：" + this.webpage.getData());
				}
				return "broadband_monitor_report";
			}
		} else {
			// 查询当月数据
			params.setCreate_start(DF3.format(new Date()));// 设置当天时间 年月 用于ECSordManager
			params.setCreate_end(DF2.format(new Date()));// 年月日
			if (StringUtils.isNotBlank(this.excel)) {
				// 导出数据
				if ("check".equals(excel)) {// 如果excel标记为check
					// 数量校验
					this.json = "{result:1}";

					return this.JSON_MESSAGE;
				} else {
					String[] title = new String[] { "地市", "当日预约单", "当月预约单", "中台当日领单量", "中台当月领单量", "平均领取时间(Min)",
							"中台当日未领单量", "中台当月未领单量", "中台当日派单量", "中台当月派单量", "平均派单时间(Min)", "中台当日退单量", "中台当月退单量",
							"当日挂起订单量", "当月挂起订单量", "当日已竣工量", "当月已竣工量", "外线装机当日退单量", "外线装机当月退单量" };
					String[] content = { "order_city", "sum00", "sum0", "sum01", "sum1", "rate1", "sum02", "sum2",
							"sum03", "sum3", "rate2", "sum04", "sum4", "sum05", "sum5", "sum06", "sum6", "sum07",
							"sum7" };
					String fileTitle = "宽带电商订单监控信息导出_" + params.getCreate_start();

					HttpServletRequest request = ServletActionContext.getRequest();
					request.setAttribute("webpage", this.webpage);// 将查询的page数据放入request对象

					// 报表导出
					List list = ecsOrdManager.queryBroadbandMonitorListDtl(params);

					DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());// 导出工具开始导出数据。
					return null;
				}
			} else {
				// 查询
				int page = 1;
				int pageSize = 20;
				if (ConstsCore.CONSTS_YES.equals(params.getQuery_btn_flag())) {// 页面点击按钮查询才加载数据.如果页面按钮查询标志为yes,则开始查询
					this.webpage = this.ecsOrdManager.queryBroadbandMonitorList(page, pageSize, params);
					logger.info("宽带数据：" + this.webpage.getData());
				}
				return "broadband_monitor_report";
			}
		}

	}

	/**
	 * 宽带电商化报表
	 */

	public String commerceDevelopChannel() throws Exception {
		// 1 判断页面选择日期时间
		Calendar calendar = Calendar.getInstance();
		if (params == null) {
			params = new OrderQueryParams();
		}
		dc_MODE_REGIONList = ecsOrdManager.getDcSqlByDcName("DC_MODE_REGION");
		countyList = ordWorkManager.getIntentCountyList(params.getOrder_city_code());
		order_from_list = ecsOrdManager.getDcSqlByDcName("ORDER_FROM");
		for (int i = 0; i < order_from_list.size(); i++) {
			String key = (String) order_from_list.get(i).get("codeb");
			if (!"report".equals(key)) {
				order_from_list.remove(i);
				i--;
			}
		}
		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_return_back)) {
			ThreadContextHolder.getSessionContext().setAttribute("order_query_params", new OrderQueryParams());
		} else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_return_back)) {
			// 1则从session取查询条件添加到页面
			params = (OrderQueryParams) ThreadContextHolder.getSessionContext().getAttribute("order_query_params");
		}
		// 对日期进行格式化
		if (!StringUtil.isEmpty(params.getCreate_start())) {
			params.setCreate_start(params.getCreate_start());
		}
		ThreadContextHolder.getSessionContext().setAttribute("order_query_params", params);// 往session里塞新的查询参数
		String nowDate = DF3.format(new Date());
		boolean flag = true;
		String[] dates = null;
		if (!StringUtil.isEmpty(params.getCreate_start()) && nowDate.equals(params.getCreate_start())) {
			flag = false;
		} else if (!StringUtil.isEmpty(params.getCreate_start()) && !nowDate.equals(params.getCreate_start())) {
			flag = true;
		} else {
			flag = false;
		}
		if (flag) {// 直接进行以往历史表进行查询 此处暂时未进行涉及到该数据信息
			if (StringUtils.isNotBlank(this.excel)) {
				if ("check".equals(excel)) {
					this.json = "{result:1}";
					return this.JSON_MESSAGE;
				}
				if ("dtl".equals(this.type)) {
					return null;
				} else {
					String fileTitle = "宽带电商化统计量信息报表导出";
					String[] title = null;
					String[] content = null;
					title = new String[] { "地市", "县分", "订单来源", "预约竣工率", "派单竣工率", "当日预约量", "当月累计预约量", "当日派单量", "月当累计派单量",
							"当日竣工量", "当月累计竣工量", "当日未处理订单", "当月未处理" };
					content = new String[] { "city_name", "county_name", "pname", "p1", "p2", "p3", "p4", "p5", "p6",
							"p7", "p8", "p9", "p10" };
					HttpServletRequest request = ServletActionContext.getRequest();
					request.setAttribute("webpage", this.webpage);// 将查询的page数据放入request对象
					List list = this.ecsOrdManager.findByCommerChannelExcel(params);
					DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());// 导出工具开始导出数据。
					return null;
				}
			} else {
				this.webpage = this.ecsOrdManager.findByCommerChannel(this.getPage(), this.getPageSize(), params);
				return "commerce_develop_channel_report";
			}
		} else {// 统计当前月份的数据量
			params.setCreate_start(DF3.format(new Date()));// 设置当天时间 年月 用于ECSordManager
			params.setCreate_end(DF2.format(new Date()));// 年月日
			if (StringUtils.isNotBlank(this.excel)) {
				if ("check".equals(excel)) {
					this.json = "{result:1}";
					return this.JSON_MESSAGE;
				}
				if ("dtl".equals(this.type)) {
					return null;
				} else {
					String fileTitle = "宽带电商化统计量信息报表导出";
					String[] title = null;
					String[] content = null;
					title = new String[] { "地市", "县分", "订单来源", "预约竣工率", "派单竣工率", "当日预约量", "当月累计预约量", "当日派单量", "月当累计派单量",
							"当日竣工量", "当月累计竣工量", "当日未处理订单", "当月未处理" };
					content = new String[] { "city_name", "county_name", "pname", "p1", "p2", "p3", "p4", "p5", "p6",
							"p7", "p8", "p9", "p10" };
					HttpServletRequest request = ServletActionContext.getRequest();
					request.setAttribute("webpage", this.webpage);// 将查询的page数据放入request对象
					List list = this.ecsOrdManager.queryCommerceDevelopChannelReportListExcel(params);
					DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());// 导出工具开始导出数据。
					return null;
				}
			} else {
				if (ConstsCore.CONSTS_YES.equals(params.getQuery_btn_flag())) {// 页面点击按钮查询才加载数据.如果页面按钮查询标志为yes,则开始查询
					this.webpage = this.ecsOrdManager.queryCommerceDevelopChannelReportList(this.getPage(),
							this.getPageSize(), params);
				}
				return "commerce_develop_channel_report";
			}
		}
	}

	public String getOrderBroadbandReport() {

		// this.listRegions();
		// this.listChannelMark();
		long start = System.currentTimeMillis();
		// 订单来源
		order_from_list = new ArrayList<Map>();
		order_from_list = ecsOrdManager.getDcSqlByDcName("ORDER_FROM");
		String[] array = { "100909", "100908", "100907", "100906", "100905", "100904", "100903", "100902", "100901",
				"10098" };
		for (int i = 0; i < order_from_list.size(); i++) {
			String key = (String) order_from_list.get(i).get("value");
			boolean boo = false;
			for (int j = 0; j < array.length; j++) {
				if (array[j].equals(key)) {
					boo = true;
				}
			}
			if (!boo) {
				order_from_list.remove(i);
				i--;
			}

		}

		// 处理参数并根据是否返回状态把查询参数放入session
		if (params == null) {
			params = new OrderQueryParams();
		}
		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_return_back)) {// 是否调用返回方法，0为往session里放查询条件，
			ThreadContextHolder.getSessionContext().setAttribute("order_query_params", new OrderQueryParams());
		} else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_return_back)) {// 是否调用返回方法，
																			// 1则从session取查询条件添加到页面
			params = (OrderQueryParams) ThreadContextHolder.getSessionContext().getAttribute("order_query_params");
		}

		// 订单ID,outtid,query_btn_flag都为空的话，默认放入一个查询的10天以内的时间
		if (StringUtil.isEmpty(params.getOrder_id()) && StringUtil.isEmpty(params.getOut_tid())
				&& StringUtil.isEmpty(params.getQuery_btn_flag())) {
			Calendar date = new GregorianCalendar();
			date.add(Calendar.DAY_OF_MONTH, -10);
			String startTime = DF2.format(date.getTime());
			String endTime = DF2.format(new Date());
			if (StringUtil.isEmpty(params.getCreate_start())) {
				params.setCreate_start(startTime);
			}
			if (StringUtil.isEmpty(params.getCreate_end())) {
				params.setCreate_end(endTime);
			}
		}
		// 对日期进行格式化
		params.setCreate_start(params.getCreate_start() + " 00:00:00");
		params.setCreate_end(params.getCreate_end() + " 23:59:59");

		ThreadContextHolder.getSessionContext().setAttribute("order_query_params", params);// 往session里塞新的查询参数

		if (ConstsCore.CONSTS_YES.equals(params.getQuery_btn_flag())) {// 页面点击按钮查询才加载数据.如果页面按钮查询标志为yes,则开始查询
			// 添加查询参数
			params.setUsername(username);// 添加工号参数
			this.webpage = this.ecsOrdManager.queryOrderBroadbandCommerceList(this.getPage(), this.getPageSize(),
					params);// 执行订单查询，查询结果放入父类继承过来的的webpage
							// 的page对象.
		}

		String[] title = new String[] { "地市", "县分", "订单来源", "内部订单号", "外部订单号", "收单时间", "联系人", "联系电话", "联系地址", "商品编码",
				"商品名称", "下单渠道编码", "下单渠道名称", "下单人编码", "下单人名称", "发展点编码", "发展点名称", "发展人编码", "发展人名称", "订单状态", "受理点编码",
				"受理点名称", "受理人工号", "受理人姓名", "订单金额", "外线施工状态", "竣工时间", "订单派单时间" };
		String[] content = { "order_city", "order_county", "order_from", "order_id", "out_tid", "receipt_time",
				"ship_name", "ship_tel", "ship_addr", "goods_id", "goodsname", "office_id", "office_name",
				"deal_operator_num", "deal_operator_name", "development_point_code", "development_point_name",
				"development_code", "development_name", "status", "out_office_id", "out_office_name", "operator_id",
				"operator_name", "pro_realfee", "wxsg_status", "complete_time", "dispatch_time" };
		String fileTitle = "宽带电商化报表";

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);// 将查询的page数据放入request对象

		if (excel != null && !"".equals(excel)) {// 如果excel标记不为空，那么准备导出数据
			List list = this.webpage.getResult();// 取出页中的记录
			long total_count = webpage.getTotalCount();// 取出总记录数

			if ("check".equals(excel)) {// 如果excel标记为check
				if (total_count > pageSize) {// 同时记录少于10000，那么结果为成功，否则结果为失败，最后返回。
					this.json = "{result:0,count:" + pageSize + "}";
				} else {
					this.json = "{result:1}";
				}
				return this.JSON_MESSAGE;
			}

			if (webpage.getTotalCount() > this.getPage()) {// 如果页数大大于1000条，那么就改成一万，
				if (total_count > pageSize) {
					total_count = pageSize;
				}
				Page pageList = ecsOrdManager.queryOrderBroadbandCommerceList(1, (int) total_count, params);// 重新开始取数据，取1-10000.
				list = pageList.getResult();
			}

			DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());// 导出工具开始导出数据。

			params.setCreate_start(params.getCreate_start().substring(0, 10));
			params.setCreate_end(params.getCreate_end().substring(0, 10));
			return null;

		} else {
			// 查询完毕后重新对显示到页面的日期进行格式化
			if (params.getCreate_start().length() < 11) {
				params.setCreate_start("");
			} else {
				params.setCreate_start(params.getCreate_start().substring(0, 10));
			}
			if (params.getCreate_end().length() < 11) {
				params.setCreate_end("");
			} else {
				params.setCreate_end(params.getCreate_end().substring(0, 10));
			}

			return "order_broadband_commerce";
		}

	}

	/**
	 * getOrdDistributionReport 物流报表
	 * 
	 */

	public String getOrdDistributionReport() {

		long start = System.currentTimeMillis();
		this.listChannelMark();
		// 处理参数并根据是否返回状态把查询参数放入session
		if (params == null) {
			params = new OrderQueryParams();
		}
		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_return_back)) {// 是否调用返回方法，0为往session里放查询条件，
			ThreadContextHolder.getSessionContext().setAttribute("order_query_params", new OrderQueryParams());
		} else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_return_back)) {// 是否调用返回方法，
																			// 1则从session取查询条件添加到页面
			params = (OrderQueryParams) ThreadContextHolder.getSessionContext().getAttribute("order_query_params");
		}

		// 订单ID,outtid,query_btn_flag都为空的话，默认放入一个查询的10天以内的时间
		if (StringUtil.isEmpty(params.getOrder_id()) && StringUtil.isEmpty(params.getOut_tid())
				&& StringUtil.isEmpty(params.getQuery_btn_flag())) {
			Calendar date = new GregorianCalendar();
			date.add(Calendar.DAY_OF_MONTH, -10);
			String startTime = DF2.format(date.getTime());
			String endTime = DF2.format(new Date());
			if (StringUtil.isEmpty(params.getCreate_start())) {
				params.setCreate_start(startTime);
			}
			if (StringUtil.isEmpty(params.getCreate_end())) {
				params.setCreate_end(endTime);
			}
		}

		// 对日期进行格式化
		params.setCreate_start(params.getCreate_start() + " 00:00:00");
		params.setCreate_end(params.getCreate_end() + " 23:59:59");

		ThreadContextHolder.getSessionContext().setAttribute("order_query_params", params);// 往session里塞新的查询参数

		if (ConstsCore.CONSTS_YES.equals(params.getQuery_btn_flag())) {// 页面点击按钮查询才加载数据.如果页面按钮查询标志为yes,则开始查询
			this.webpage = this.ecsOrdManager.queryDistributionReportList(this.getPage(), this.getPageSize(), params);// 执行订单查询，查询结果放入父类继承过来的的webpage
																														// 的page对象.
		}

		String[] title = new String[] { "序号", "订单编号", "订单状态", "发货人", "发货时间", "商品名称", "手机型号", "手机串号", "成交时间", "稽核人",
				"稽核时间", "新老用户", "入网姓名", "入网号码", "联系人姓名", "联系电话", "物流单号", "返回单号", "物流地址", "物流公司", "付款方式", "金额", "发货批次",
				"发货人", "发货时间", "入网证件", "受理协议", "靓号协议", "存档编号", "资料归档人", "资料归档时间" };
		String[] content = { "sequence_id", "out_order_id", "status", "opname", "h_end_time", "goodsname",
				"specificatio_nname", "terminal_num", "tid_time", "lock_user_name", "lock_time", "is_old",
				"phone_owner_name", "phone_num", "ship_name", "ship_tel", "logi_no", "receipt_no", "ship_addr",
				"shipping_company", "pay_method", "fee", "ship_batch", "goods_shipper", "goods_send_time", "net_certi",
				"accept_agree", "liang_agree", "receive_num", "j_op_id", "archive_time" };
		String fileTitle = "物流报表";

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);// 将查询的page数据放入request对象

		if (excel != null && !"".equals(excel)) {// 如果excel标记不为空，那么准备导出数据
			/*
			 * List list = this.webpage.getResult();// 取出页中的记录 long total_count =
			 * webpage.getTotalCount();// 取出总记录数
			 * System.out.println(webpage.getResult().size());
			 */
			if ("check".equals(excel)) {// 如果excel标记为check
				/*
				 * if (total_count > pageSize) {// 同时记录少于10000，那么结果为成功，否则结果为失败，最后返回。 this.json =
				 * "{result:0,count:" + pageSize + "}"; } else {
				 */
				this.json = "{result:1}";
				/* } */
				return this.JSON_MESSAGE;
			}

			/*
			 * if (webpage.getTotalCount() > this.getPage()) {// 如果页数大大于1000条，那么就改成一万， if
			 * (total_count > pageSize) { total_count = pageSize; } Page pageList =
			 * ecsOrdManager.queryDistributionReportList(1, (int) total_count, params);//
			 * 重新开始取数据，取1-10000. list = pageList.getResult(); }
			 * 
			 * DownLoadUtil.export(list, fileTitle, title, content,
			 * ServletActionContext.getResponse());// 导出工具开始导出数据。
			 */ // 查询完毕后重新对显示到页面的日期进行格式化
			ChannelSftp sftpchannel;
			SFTPChannel channel;
			HttpServletResponse response = ServletActionContext.getResponse();
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String url = cacheUtil.getConfigInfo("DistributionReportURL");
			String sftp_param = cacheUtil.getConfigInfo("VOX2WAV_PARAM");
			OutputStream out = null;
			try {
				net.sf.json.JSONObject jsonobj = net.sf.json.JSONObject.fromObject(sftp_param);
				Map<String, String> login_map = new HashMap<String, String>();
				login_map.put("ip", jsonobj.getString("ip"));
				login_map.put("port", jsonobj.getString("port"));
				login_map.put("userName", jsonobj.getString("userName"));
				login_map.put("passWord", jsonobj.getString("passWord"));
				channel = new SFTPChannel();
				sftpchannel = channel.getChannel(login_map, 200000);
				String uplod_url = jsonobj.getString("uplod_url");
				out = new FileOutputStream(url);
				// sftpchannel.cd(uplod_url);
				sftpchannel.get(uplod_url + url.substring(url.lastIndexOf("/") + 1), out);
				out.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			File f = new File(url);
			if (!f.exists()) {
				try {
					response.sendError(404, "File not found!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			BufferedInputStream br;
			try {
				br = new BufferedInputStream(new FileInputStream(f));

				byte[] buf = new byte[1024];
				int len = 0;

				response.reset(); // 非常重要
				// 纯下载方式
				String fileName = new String("物流报表".getBytes("gb2312"), "iso-8859-1");
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");

				out = response.getOutputStream();
				while ((len = br.read(buf)) > 0)
					out.write(buf, 0, len);
				br.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			params.setCreate_start(params.getCreate_start().substring(0, 10));
			params.setCreate_end(params.getCreate_end().substring(0, 10));
			return null;
		} else {
			// 查询完毕后重新对显示到页面的日期进行格式化
			params.setCreate_start(params.getCreate_start().substring(0, 10));
			params.setCreate_end(params.getCreate_end().substring(0, 10));
			return "order_distribution_report";
		}

	}

	/**
	 * 订单领取报表
	 */

	public String getOrderReceiveReport() {

		// this.listRegions();
		// this.listChannelMark();
		long start = System.currentTimeMillis();

		// 处理参数并根据是否返回状态把查询参数放入session
		if (params == null) {
			params = new OrderQueryParams();
		}
		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_return_back)) {// 是否调用返回方法，0为往session里放查询条件，
			ThreadContextHolder.getSessionContext().setAttribute("order_query_params", new OrderQueryParams());
		} else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_return_back)) {// 是否调用返回方法，
																			// 1则从session取查询条件添加到页面
			params = (OrderQueryParams) ThreadContextHolder.getSessionContext().getAttribute("order_query_params");
		}

		// 订单ID,outtid,query_btn_flag都为空的话，默认放入一个查询的10天以内的时间
		if (StringUtil.isEmpty(params.getOrder_id()) && StringUtil.isEmpty(params.getOut_tid())
				&& StringUtil.isEmpty(params.getQuery_btn_flag())) {
			Calendar date = new GregorianCalendar();
			date.add(Calendar.DAY_OF_MONTH, -10);
			String startTime = DF2.format(date.getTime());
			String endTime = DF2.format(new Date());
			if (StringUtil.isEmpty(params.getCreate_start())) {
				params.setCreate_start(startTime);
			}
			if (StringUtil.isEmpty(params.getCreate_end())) {
				params.setCreate_end(endTime);
			}
		}

		// 对日期进行格式化
		params.setCreate_start(params.getCreate_start() + " 00:00:00");
		params.setCreate_end(params.getCreate_end() + " 23:59:59");
		ThreadContextHolder.getSessionContext().setAttribute("order_query_params", params);// 往session里塞新的查询参数

		if (ConstsCore.CONSTS_YES.equals(params.getQuery_btn_flag())) {// 页面点击按钮查询才加载数据.如果页面按钮查询标志为yes,则开始查询
			// 添加查询参数
			params.setUsername(username);// 添加工号参数
			this.webpage = this.ecsOrdManager.queryOrderReceiveReportList(this.getPage(), this.getPageSize(), params);// 执行订单查询，查询结果放入父类继承过来的的webpage
																														// 的page对象.
		}

		String[] title = new String[] { "订单编号", "订单来源", "地市", "商品名称", "订单状态", "是否领取", "领取人", "订购号码", "套餐名称", "成交时间",
				"支付状态", "支付类型", "订单金额", "实收金额", "终端类型", "终端品牌", "终端型号", "终端串号", "物流单号" };
		String[] content = { "order_id", "order_from_str", "order_city", "order_goodsname", "order_status",
				"order_receive", "lock_user_name", "phone_num", "plan_title", "tid_time", "pay_status", "pay_type",
				"order_amount", "paymoney", "pack_type", "brand_name", "specificatio_nname", "terminal_num",
				"logi_no" };
		String fileTitle = "订单领取报表";

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);// 将查询的page数据放入request对象

		if (excel != null && !"".equals(excel)) {// 如果excel标记不为空，那么准备导出数据
			List list = this.webpage.getResult();// 取出页中的记录
			long total_count = webpage.getTotalCount();// 取出总记录数

			if ("check".equals(excel)) {// 如果excel标记为check
				if (total_count > pageSize) {// 同时记录少于10000，那么结果为成功，否则结果为失败，最后返回。
					this.json = "{result:0,count:" + pageSize + "}";
				} else {
					this.json = "{result:1}";
				}
				return this.JSON_MESSAGE;
			}

			if (webpage.getTotalCount() > this.getPage()) {// 如果页数大大于1000条，那么就改成一万，
				if (total_count > pageSize) {
					total_count = pageSize;
				}
				Page pageList = ecsOrdManager.queryOrderReceiveReportList(1, (int) total_count, params);// 重新开始取数据，取1-10000.
				list = pageList.getResult();
			}

			DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());// 导出工具开始导出数据。
			// 查询完毕后重新对显示到页面的日期进行格式化
			params.setCreate_start(params.getCreate_start().substring(0, 10));
			params.setCreate_end(params.getCreate_end().substring(0, 10));
			return null;

		} else {
			// 查询完毕后重新对显示到页面的日期进行格式化
			params.setCreate_start(params.getCreate_start().substring(0, 10));
			params.setCreate_end(params.getCreate_end().substring(0, 10));

			return "order_receive_report";
		}

	}

	/**
	 * 提取库管日报
	 * 
	 * @return
	 */
	public String getWareHouseDailyReport() {

		// this.listRegions();
		// this.listChannelMark();
		// this.listIsQuickShip();
		// this.listOrderFrom();
		// this.listPlatType();
		// this.listPayType();
		// this.listQuickStatus();
		// this.listShippingType();
		// this.listShiipingCop();
		// this.listDealStatus();
		// this.listExceptionType();
		// this.listFlowTrace();
		// this.listExceptionTypeList();
		// this.listDcModeRegion();
		// this.listOrderListIsHis();
		// this.listOrderSupplyStatus();
		// this.listorderModel();
		// this.listWMSRefundStatusList();

		long start = System.currentTimeMillis();

		// 处理参数并根据是否返回状态把查询参数放入session
		if (params == null) {
			params = new OrderQueryParams();
		}
		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_return_back)) {// 是否调用返回方法，0为往session里放查询条件，
			ThreadContextHolder.getSessionContext().setAttribute("order_query_params", new OrderQueryParams());
		} else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_return_back)) {// 是否调用返回方法，
																			// 1则从session取查询条件添加到页面
			params = (OrderQueryParams) ThreadContextHolder.getSessionContext().getAttribute("order_query_params");
		}

		// 订单ID,outtid,query_btn_flag都为空的话，默认放入一个查询的10天以内的时间
		if (StringUtil.isEmpty(params.getOrder_id()) && StringUtil.isEmpty(params.getOut_tid())
				&& StringUtil.isEmpty(params.getQuery_btn_flag())) {
			Calendar date = new GregorianCalendar();
			date.add(Calendar.DAY_OF_MONTH, -10);
			String startTime = DF2.format(date.getTime());
			String endTime = DF2.format(new Date());
			if (StringUtil.isEmpty(params.getCreate_start())) {
				params.setCreate_start(startTime);
			}
			if (StringUtil.isEmpty(params.getCreate_end())) {
				params.setCreate_end(endTime);
			}
		}

		// 对日期进行格式化
		params.setCreate_start(params.getCreate_start() + " 00:00:00");
		params.setCreate_end(params.getCreate_end() + " 23:59:59");

		ThreadContextHolder.getSessionContext().setAttribute("order_query_params", params);// 往session里塞新的查询参数

		if (ConstsCore.CONSTS_YES.equals(params.getQuery_btn_flag())) {// 页面点击按钮查询才加载数据.如果页面按钮查询标志为yes,则开始查询
			this.webpage = this.ecsOrdManager.queryOrderWarehouseDailyList(this.getPage(), this.getPageSize(), params);// 执行订单查询，查询结果放入父类继承过来的的webpage
																														// 的page对象.
		}

		String[] title = new String[] { "日期", "领取号", "订单号", "订单状态", "订单来源", "入网姓名", "订购号", "地市", "产品类型", "型号", "终端串号",
				"领取人" };
		String[] content = { "create_time2", "receive_num", "order_id", "order_status", "order_from_str",
				"phone_owner_name", "phone_num", "order_city", "specificatio_nname", "pack_type", "terminal_num",
				"phone_owner_name" };
		String fileTitle = "库管日报";

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);// 将查询的page数据放入request对象

		if (excel != null && !"".equals(excel)) {// 如果excel标记不为空，那么准备导出数据
			List list = this.webpage.getResult();// 取出页中的记录
			long total_count = webpage.getTotalCount();// 取出总记录数

			if ("check".equals(excel)) {// 如果excel标记为check
				if (total_count > pageSize) {// 同时记录少于10000，那么结果为成功，否则结果为失败，最后返回。
					this.json = "{result:0,count:" + pageSize + "}";
				} else {
					this.json = "{result:1}";
				}
				return this.JSON_MESSAGE;
			}

			if (webpage.getTotalCount() > this.getPage()) {// 如果页数大大于1000条，那么就改成一万，
				if (total_count > pageSize) {
					total_count = pageSize;
				}
				Page pageList = ecsOrdManager.queryOrderWarehouseDailyList(1, (int) total_count, params);// 重新开始取数据，取1-10000.
				list = pageList.getResult();
			}

			DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());// 导出工具开始导出数据。
			// 查询完毕后重新对显示到页面的日期进行格式化
			params.setCreate_start(params.getCreate_start().substring(0, 10));
			params.setCreate_end(params.getCreate_end().substring(0, 10));
			return null;
		} else {
			// 查询完毕后重新对显示到页面的日期进行格式化
			params.setCreate_start(params.getCreate_start().substring(0, 10));
			params.setCreate_end(params.getCreate_end().substring(0, 10));
			return "warehouse_daily_report";
		}

	}

	// 实收款稽核报表
	public String getActualRevenueAuditReport() {
		long start = System.currentTimeMillis();

		// 处理参数并根据是否返回状态把查询参数放入session
		if (params == null) {
			params = new OrderQueryParams();
		}
		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_return_back)) {// 是否调用返回方法，0为往session里放查询条件，
			ThreadContextHolder.getSessionContext().setAttribute("order_query_params", new OrderQueryParams());
		} else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_return_back)) {// 是否调用返回方法，
																			// 1则从session取查询条件添加到页面
			params = (OrderQueryParams) ThreadContextHolder.getSessionContext().getAttribute("order_query_params");
		}

		// 订单ID,outtid,query_btn_flag都为空的话，默认放入一个查询的10天以内的时间
		if (StringUtil.isEmpty(params.getOrder_id()) && StringUtil.isEmpty(params.getOut_tid())
				&& StringUtil.isEmpty(params.getQuery_btn_flag())) {
			Calendar date = new GregorianCalendar();
			date.add(Calendar.DAY_OF_MONTH, -10);
			String startTime = DF.format(date.getTime());
			String endTime = DF.format(new Date());
			if (StringUtil.isEmpty(params.getCreate_start())) {
				params.setCreate_start(startTime);
			}
			if (StringUtil.isEmpty(params.getCreate_end())) {
				params.setCreate_end(endTime);
			}
		}
		ThreadContextHolder.getSessionContext().setAttribute("order_query_params", params);// 往session里塞新的查询参数

		if (ConstsCore.CONSTS_YES.equals(params.getQuery_btn_flag())) {// 页面点击按钮查询才加载数据.如果页面按钮查询标志为yes,则开始查询
			this.webpage = this.ecsOrdManager.queryOrderActualRevenueAuditReport(this.getPage(), this.getPageSize(),
					params);// 执行订单查询，查询结果放入父类继承过来的的webpage
							// 的page对象.
		}

		String[] title = new String[] { "序号", "开户日期", "地市", "业务号码", "订单来源", "商城订单号", "支付机构", "支付日期", "退款日期", "支付公司订单号",
				"支付类型", "账户实收金额", "订单实收金额", "实收差额", "BSS金额", "CBSS金额", "折扣金额", "物流单号", "资金稽核状态", "稽核说明" };
		String[] content = { "seq_num", "d_begin_time", "city", "phone_num", "order_from", "out_tid", "payprovidername",
				"pay_time", "refund_time", "payplatformorderid", "paytype", "account_paymoney", "paymoney",
				"paid_in_difference", "busi_money", "ess_money", "discountrange", "logi_no",
				"audit_receive_money_status", "audit_comments" };
		String fileTitle = "实收款稽核报表";

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);// 将查询的page数据放入request对象

		if (excel != null && !"".equals(excel)) {// 如果excel标记不为空，那么准备导出数据
			List list = this.webpage.getResult();// 取出页中的记录
			long total_count = webpage.getTotalCount();// 取出总记录数

			if ("check".equals(excel)) {// 如果excel标记为check
				if (total_count > 10000) {// 同时记录少于10000，那么结果为成功，否则结果为失败，最后返回。
					this.json = "{result:0}";
				} else {
					this.json = "{result:1}";
				}
				return this.JSON_MESSAGE;
			}

			if (webpage.getTotalCount() > this.getPage()) {// 如果页数大大于1000条，那么就改成一万，
				if (total_count > 10000) {
					total_count = 10000;
				}
				Page pageList = ecsOrdManager.queryOrderWarehouseDailyList(1, (int) total_count, params);// 重新开始取数据，取1-10000.
				list = pageList.getResult();
			}

			DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());// 导出工具开始导出数据。
			return null;
		} else {

			return "actual_revenue_audit_report";
		}
	}

	// 退款订单报表

	public String getOrderWhichRefund() {

		// this.listRegions();
		// this.listChannelMark();
		// this.listIsQuickShip();
		// this.listOrderFrom();
		// this.listPlatType();
		// this.listPayType();
		// this.listQuickStatus();
		// this.listShippingType();
		// this.listShiipingCop();
		// this.listDealStatus();
		// this.listExceptionType();
		// this.listFlowTrace();
		// this.listExceptionTypeList();
		// this.listDcModeRegion();
		// this.listOrderListIsHis();
		// this.listOrderSupplyStatus();
		// this.listorderModel();
		// this.listWMSRefundStatusList();

		long start = System.currentTimeMillis();

		// 处理参数并根据是否返回状态把查询参数放入session
		if (params == null) {
			params = new OrderQueryParams();
		}
		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_return_back)) {// 是否调用返回方法，0为往session里放查询条件，
			ThreadContextHolder.getSessionContext().setAttribute("order_query_params", new OrderQueryParams());
		} else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_return_back)) {// 是否调用返回方法，
																			// 1则从session取查询条件添加到页面
			params = (OrderQueryParams) ThreadContextHolder.getSessionContext().getAttribute("order_query_params");
		}

		// 订单ID,outtid,query_btn_flag都为空的话，默认放入一个查询的10天以内的时间
		if (StringUtil.isEmpty(params.getOrder_id()) && StringUtil.isEmpty(params.getOut_tid())
				&& StringUtil.isEmpty(params.getQuery_btn_flag())) {
			Calendar date = new GregorianCalendar();
			date.add(Calendar.DAY_OF_MONTH, -10);
			String startTime = DF2.format(date.getTime());
			String endTime = DF2.format(new Date());
			if (StringUtil.isEmpty(params.getCreate_start())) {
				params.setCreate_start(startTime);
			}
			if (StringUtil.isEmpty(params.getCreate_end())) {
				params.setCreate_end(endTime);
			}
		}

		// 对日期进行格式化
		params.setCreate_start(params.getCreate_start() + " 00:00:00");
		params.setCreate_end(params.getCreate_end() + " 23:59:59");

		ThreadContextHolder.getSessionContext().setAttribute("order_query_params", params);// 往session里塞新的查询参数

		if (ConstsCore.CONSTS_YES.equals(params.getQuery_btn_flag())) {// 页面点击按钮查询才加载数据.如果页面按钮查询标志为yes,则开始查询
			this.webpage = this.ecsOrdManager.queryOrderWhichRefundList(this.getPage(), this.getPageSize(), params);// 执行订单查询，查询结果放入父类继承过来的的webpage
																													// 的page对象.
		}

		String[] title = new String[] { "订单来源", "订单编号", "订单状态", "订单受理日期", "退款日期", "地市", "商品类型", "商品名称", "订购号码", "实收",
				"营业款", "终端品牌", "终端型号", "终端颜色", "终端串号", "用户类型", "联系人", "联系人电话" };
		String[] content = { "order_from", "out_tid", "status", "bss_account_time", "refund_time", "order_city",
				"goods_type", "GoodsName", "phone_num", "paymoney", "busi_money", "brand_name", "specificatio_nname",
				"terminal_color", "terminal_num", "is_old", "phone_owner_name", "ship_tel" };

		String fileTitle = "退款订单报表";

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);// 将查询的page数据放入request对象

		if (excel != null && !"".equals(excel)) {// 如果excel标记不为空，那么准备导出数据
			List list = this.webpage.getResult();// 取出页中的记录
			long total_count = webpage.getTotalCount();// 取出总记录数

			if ("check".equals(excel)) {// 如果excel标记为check
				if (total_count > pageSize) {// 同时记录少于10000，那么结果为成功，否则结果为失败，最后返回。
					this.json = "{result:0,count:" + pageSize + "}";
				} else {
					this.json = "{result:1}";
				}
				return this.JSON_MESSAGE;
			}

			if (webpage.getTotalCount() > this.getPage()) {// 如果页数大大于1000条，那么就改成一万，
				if (total_count > pageSize) {
					total_count = pageSize;
				}
				Page pageList = ecsOrdManager.queryOrderWhichRefundList(1, (int) total_count, params);// 重新开始取数据，取1-10000.
				list = pageList.getResult();
			}

			DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());// 导出工具开始导出数据。
			// 查询完毕后重新对显示到页面的日期进行格式化
			params.setCreate_start(params.getCreate_start().substring(0, 10));
			params.setCreate_end(params.getCreate_end().substring(0, 10));
			return null;
		} else {
			// 查询完毕后重新对显示到页面的日期进行格式化
			params.setCreate_start(params.getCreate_start().substring(0, 10));
			params.setCreate_end(params.getCreate_end().substring(0, 10));
			return "order_refund_report";
		}

	}

	// public String showMonitorList(){
	// this.query_type = "";
	// this.showOrderList();
	// return "monitor_order_list";
	// }

	public String showMonitorList() {
		this.listRegions();
		this.listChannelMark();
		this.listIsQuickShip();
		this.listOrderFrom();
		this.listPlatType();
		this.listPayType();
		this.listQuickStatus();
		this.listShippingType();
		this.listShiipingCop();
		this.listDealStatus();
		this.listExceptionType();
		this.listFlowTrace();
		this.listExceptionTypeList();
		this.listDcModeRegion();
		this.listOrderListIsHis();
		this.listOrderSupplyStatus();
		this.listorderModel();
		this.listWMSRefundStatusList();

		long start = System.currentTimeMillis();
		if (params == null)
			params = new OrderQueryParams();

		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_return_back)) {
			ThreadContextHolder.getSessionContext().setAttribute("order_query_params", new OrderQueryParams());
		} else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_return_back)) {
			params = (OrderQueryParams) ThreadContextHolder.getSessionContext().getAttribute("order_query_params");
		}
		if (StringUtil.isEmpty(params.getOrder_id()) && StringUtil.isEmpty(params.getOut_tid())
				&& StringUtil.isEmpty(params.getQuery_btn_flag())) {
			Calendar date = new GregorianCalendar();
			date.add(Calendar.DAY_OF_MONTH, -10);
			String startTime = DF.format(date.getTime());
			String endTime = DF.format(new Date());
			if (StringUtil.isEmpty(params.getCreate_start())) {
				params.setCreate_start(startTime);
			}
			if (StringUtil.isEmpty(params.getCreate_end())) {
				params.setCreate_end(endTime);
			}
			/*
			 * if(StringUtil.isEmpty(params.getPay_start())){
			 * params.setPay_start(startTime); }
			 * if(StringUtil.isEmpty(params.getPay_end())){ params.setPay_end(endTime); }
			 */
		}
		ThreadContextHolder.getSessionContext().setAttribute("order_query_params", params);
		if (ConstsCore.CONSTS_YES.equals(params.getQuery_btn_flag())) {// 页面点击按钮查询才加载数据
			this.webpage = this.ecsOrdManager.queryOrderMonitorList(this.getPage(), this.getPageSize(), params);
		}
		String[] title = new String[] { "外部订单号", "内部订单号", "外部下单时间", "订单创建时间", "订单来源", "地市", "环节", "商品类型", "商品名称",
				"商品数量", "品牌", "终端串号", "卡串号", "机型", "套餐名称", "首月资费方式", "活动类型", "开户人姓名", "开户号码", "证件类型", "证件号码", "证件地址",
				"支付类型", "支付方式", "支付状态", "支付时间", "支付流水号", "备注信息", "生产模式", "订单处理类型", "配送方式", "发展人编码", "发展人名称", "推广渠道",
				"异常原因", "WMS退单状态", "发票抬头", "推荐人姓名", "推荐人号码", "物流公司", "物流单号", "收货人姓名", "收货人电话", "买家留言", "卖家留言",
				"自动化异常类型", "自动化异常原因", "人工标记类型", "人工标记类型原因", "发票号码", "订单金额(元)", "开户时间", "签收状态", "回单状态", "是否预约单",
				"关联单号" };

		String[] content = { "out_tid", "order_id", "tid_time", "create_time", "order_from_str", "order_city",
				"current_flow_name", "pack_type", "goodsname", "goods_num", "brand_name", "terminal_num", "iccid",
				"model_code", "plan_title", "first_payment", "ative_type", "phone_owner_name", "phone_num",
				"certi_type", "cert_card_num", "cert_address", "pay_type", "pay_method", "pay_status", "pay_time",
				"payplatformorderid", "service_remarks", "order_mode_str", "shipping_quick", "shipping_type",
				"development_code", "development_name", "spread_channel", "exception_desc", "wms_refund_status",
				"invoice_title", "recommended_name", "recommended_phone", "shipping_company_name", "logi_no",
				"ship_name", "ship_tel", "buyer_message", "seller_message", "auto_exception_type",
				"auto_exception_desc", "exception_type", "exception_desc", "invoice_num", "paymoney",
				"bss_account_time", "sign_status", "receipt_status", "wm_isreservation_from", "related_order_id" };

		String fileTitle = "实时监控报表";

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);
		if (excel != null && !"".equals(excel)) {
			List list = this.webpage.getResult();
			long total_count = webpage.getTotalCount();
			if ("check".equals(excel)) {
				if (total_count > 10000) {
					this.json = "{result:0}";
				} else {
					this.json = "{result:1}";
				}
				return this.JSON_MESSAGE;
			}
			if (webpage.getTotalCount() > this.getPage()) {
				if (total_count > 10000) {
					total_count = 10000;
				}
				Page pageList = ecsOrdManager.queryOrderMonitorList(1, (int) total_count, params);
				list = pageList.getResult();
			}
			DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());
			return null;
		} else {
			return "monitor_order_list";
		}
	}

	public void exportMonitorList() {
		List list;
		this.params.setQuery_type(EcsOrderConsts.QUERY_TYPE_ORDER_LIST);
		this.webpage = ecsOrdManager.queryOrderForPageBuyFlow(this.getPage(), this.getPageSize(), params);
		list = webpage.getResult();
		if (webpage.getTotalCount() > this.getPage()) {
			Page pageList = ecsOrdManager.queryOrderForPageBuyFlow(1, (int) webpage.getTotalCount(), params);
			list = pageList.getResult();
		}

		String[] title = new String[] { "外部订单号", "创建时间", "订单来源", "地市", "商品类型", "商品名称", "套餐名称", "所选号码", "支付类型", "发展人编码",
				"发展人名称", "推广渠道", "异常原因", "WMS退单状态", "发票号码", "签收状态", "回单状态" };

		String[] content = { "out_tid", "tid_time", "order_from_c", "city_name", "goods_type_name", "goods_name",
				"offer_name", "phone_num", "pay_type_name", "development_code", "development_name", "channel_mark",
				"exception_desc", "wms_refund_status", "invoice_num", "sign_status", "receipt_status" };

		String fileTitle = "实时监控报表";
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);
		if (excel != null && !"".equals(excel)) {
			DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());
		}
	}

	/**
	 * 订单数据查询
	 */
	public String getOrderDataBySearch() {

		long start = System.currentTimeMillis();
		this.listOrderFrom();
		// this.listDataSrc();
		this.listDataSrcZj();

		this.listOrderTypeList();

		this.listRegions();
		this.listPayType();
		this.listShippingType();

		this.listDcModeOrderStatus();
		this.listDcModeRegion();
		this.listDcModeGoodsType();
		this.listDcModeActivityType();

		if (params == null)
			params = new OrderQueryParams();

		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_return_back)) {
			ThreadContextHolder.getSessionContext().setAttribute("order_query_params", new OrderQueryParams());
		} else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_return_back)) {
			params = (OrderQueryParams) ThreadContextHolder.getSessionContext().getAttribute("order_query_params");
		}
		if (StringUtil.isEmpty(params.getOrder_id()) && StringUtil.isEmpty(params.getOut_tid())
				&& StringUtil.isEmpty(params.getQuery_btn_flag())) {
			Calendar date = new GregorianCalendar();
			date.add(Calendar.DAY_OF_MONTH, -10);
			String startTime = DF.format(date.getTime());
			String endTime = DF.format(new Date());
			if (StringUtil.isEmpty(params.getCreate_start())) {
				params.setCreate_start(startTime);
			}
			if (StringUtil.isEmpty(params.getCreate_end())) {
				params.setCreate_end(endTime);
			}
		}
		ThreadContextHolder.getSessionContext().setAttribute("order_query_params", params);
		if (ConstsCore.CONSTS_YES.equals(params.getQuery_btn_flag())) {// 页面点击按钮查询才加载数据
			this.webpage = this.ecsOrdManager.queryOrderDataBySearchList(this.getPage(), this.getPageSize(), params);
		}
		/*
		 * String[] title = new String[] { "订单号", "订单生成日期", "订单领取时间", "用户签收时间",
		 * "时间差(用户签收时间-订单领取时间)", "数据来源", "商城来源", "推广渠道", "发展点名称", "发展点编码", "订单处理人",
		 * "订单处理时间", "订单稽核人", "订单稽核时间", "发货时间", "物流编号", "终端串号", "退单原因", "地市", "商品类型",
		 * "商品名称", "发票串号", "套餐名称", "特权包", "用户号码", "副卡", "终端", "合约类型", "合约期限", "实收(元)",
		 * "支付状态", "支付类型", "订单状态", "是否开户（是/否）", "用户类型", "联系人", "联系人电话", "收货地址",
		 * "其他联系电话", "操作备注", "订单备注", "发展人", "配送方式", "奖品", "特色包", "入网人姓名" }; String[]
		 * content = { "out_tid", "tid_time", "order_collect_time", "package_acp_date",
		 * "time_difference", "order_from", "order_origin", "spread_channel",
		 * "development_name", "development_code", "handle_name", "handle_time",
		 * "f_op_id", "f_end_time", "shipping_time", "logi_no", "terminal_num",
		 * "refund_desc", "lan_code", "goods_type", "goodsName", "invoice_no",
		 * "plan_title", "privilege_combo", "phone_num", "zb_fuka_info", "terminal",
		 * "prod_cat_id", "contract_month", "pro_realfee", "pay_status", "paytype",
		 * "status", "account_status", "is_old", "ship_name", "reference_phone",
		 * "contact_addr", "carry_person_mobile", "audit_remark", "service_remarks",
		 * "devlopname", "sending_type", "prize", "special_combo", "phone_owner_name" };
		 */

		String[] title = new String[] { "订单号", "订单生成日期", "订单领取时间", "签收状态", "用户签收时间", "时间差(用户签收时间-订单领取时间)", "数据来源",
				"商城来源", "推广渠道", "发展点名称", "发展点编码", "订单处理人", "订单处理时间", "订单稽核人", "订单稽核时间", "发货时间", "物流编号", "终端串号", "退单原因",
				"地市", "商品类型", "商品名称", "发票串号", "套餐名称", "特权包", "用户号码", "副卡", "终端", "合约类型", "合约期限", "实收(元)", "支付状态",
				"支付类型", "订单状态", "是否开户（是/否）", "用户类型", "联系人", "联系人电话", "收货地址", "其他联系电话", "操作备注", "订单备注", "发展人", "配送方式",
				"奖品", "特色包", "入网人姓名", "主号码", "激活状态", "激活时间" };
		String[] content = { "out_tid", "tid_time", "order_collect_time", "sign_status", "route_acceptime",
				"time_difference", "order_from", "order_origin", "spread_channel", "development_name",
				"development_code", "handle_name", "handle_time", "f_op_id", "f_end_time", "ship_end_time", "logi_no",
				"terminal_num", "refund_desc", "lan_code", "goods_type", "goodsName", "invoice_no", "plan_title",
				"privilege_combo", "phone_num", "zb_fuka_info", "terminal", "prod_cat_id", "contract_month",
				"pro_realfee", "pay_status", "paytype", "status", "account_status", "is_old", "ship_name",
				"reference_phone", "ship_addr", "carry_person_mobile", "audit_remark", "service_remarks", "devlopname",
				"sending_type", "prize", "special_combo", "phone_owner_name", "mainnumber", "active_flag",
				"active_time" };

		String fileTitle = "订单数据查询";

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);
		if (excel != null && !"".equals(excel)) {
			List list = this.webpage.getResult();
			long total_count = webpage.getTotalCount();
			if ("check".equals(excel)) {
				/*
				 * if (total_count > pageSize) { this.json = "{result:0,count:" + pageSize +
				 * "}"; } else {
				 */
				this.json = "{result:1}";
				/* } */
				return this.JSON_MESSAGE;
			}
			/*
			 * if (webpage.getTotalCount() > this.getPage()) { if (total_count > pageSize) {
			 * total_count = pageSize; } Page pageList =
			 * ecsOrdManager.queryOrderDataBySearchList(1, (int) total_count, params); list
			 * = pageList.getResult(); } DownLoadUtil.export(list, fileTitle, title,
			 * content, ServletActionContext.getResponse());
			 */
			ChannelSftp sftpchannel;
			SFTPChannel channel;
			HttpServletResponse response = ServletActionContext.getResponse();
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String url = cacheUtil.getConfigInfo("OrderDataBySearchURL");
			String sftp_param = cacheUtil.getConfigInfo("VOX2WAV_PARAM");
			OutputStream out = null;
			try {
				net.sf.json.JSONObject jsonobj = net.sf.json.JSONObject.fromObject(sftp_param);
				Map<String, String> login_map = new HashMap<String, String>();
				login_map.put("ip", jsonobj.getString("ip"));
				login_map.put("port", jsonobj.getString("port"));
				login_map.put("userName", jsonobj.getString("userName"));
				login_map.put("passWord", jsonobj.getString("passWord"));
				channel = new SFTPChannel();
				sftpchannel = channel.getChannel(login_map, 200000);
				String uplod_url = jsonobj.getString("uplod_url");
				out = new FileOutputStream(url);
				// sftpchannel.cd(uplod_url);
				sftpchannel.get(uplod_url + url.substring(url.lastIndexOf("/") + 1), out);
				out.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			File f = new File(url);
			if (!f.exists()) {
				try {
					response.sendError(404, "File not found!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			BufferedInputStream br;
			try {
				br = new BufferedInputStream(new FileInputStream(f));

				byte[] buf = new byte[1024];
				int len = 0;

				response.reset(); // 非常重要
				// 纯下载方式
				String fileName = new String("订单数据查询".getBytes("gb2312"), "iso-8859-1");
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");

				out = response.getOutputStream();
				while ((len = br.read(buf)) > 0)
					out.write(buf, 0, len);
				br.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
			return "order_data_by_search";
		}

	}

	// 外呼查询记录
	public String queryOutcallLogs() {
		OutcallLogsListReq req = new OutcallLogsListReq();
		req.setOrder_id(order_id);
		OutcallLogsListResp resp = orderServices.listOutcallLogs(req);
		if (resp != null)
			outcalllogsList = resp.getOutcalllogList();
		return "outcall_logs";
	}

	public String queryOrderHandlerLogs() throws Exception {
		OrderHandleLogsListReq req = new OrderHandleLogsListReq();
		req.setOrder_id(order_id);
		req.setHandler_type(Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
		req.setOrder_is_his(is_his_order);

		OrderHandleLogsListResp resp = orderServices.listOrderHandlerLogs(req);

		if (resp != null)
			logsList = resp.getLogList();

		String photo_logs_sql = "select '照片审核' as trace_name,'' as type_name,audit_msg as comments,audit_time as create_time,(select realname from es_adminuser a where a.userid=audit_user_id) as op_name from es_order_photos_audit_logs where order_id='"
				+ order_id + "'";
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		List<OrderHandleLogsReq> photo_logsList = baseDaoSupport.queryForListByMap(photo_logs_sql,
				OrderHandleLogsReq.class, null);

		logsList.addAll(photo_logsList);

		// 查询自定义流程日志
		this.getWorkCustomLog();

		// 排序
		Collections.sort(this.logsList, new Comparator<OrderHandleLogsReq>() {
			public int compare(OrderHandleLogsReq o1, OrderHandleLogsReq o2) {
				String str1 = o1.getCreate_time();
				String str2 = o2.getCreate_time();

				SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);

				try {
					Date t1 = format.parse(str1);
					Date t2 = format.parse(str2);

					// 降序排列
					if (t1.getTime() > t2.getTime()) {
						return -1;
					} else {
						return 1;
					}
				} catch (Exception e) {

				}

				return 1;
			}
		});

		return "handler_logs";
	}

	/**
	 * ZX add (2014-12-13) 查询标记异常
	 * 
	 * @return
	 */
	public String queryOrderExceptionLogs() {
		// OrderExceptionLogsListReq req = new OrderExceptionLogsListReq();
		// req.setOrder_id(order_id);
		// req.setFlow_trace_id(trace_id);
		// OrderExceptionLogsListResp resp =
		// orderServices.listOrderExceptionLogs(req);
		// if(resp != null)exceptionLogsList = resp.getExceptionList();

		exceptionLogsList = this.ecsOrdManager.getExceptionLogList(order_id);
		return "exception_logs";
	}

	/**
	 * ZX add (2015-03-07) 闪电送日志
	 * 
	 * @return
	 */
	public String querySdsLogs() {
		sdsLogsList = this.ecsOrdManager.getSdsLogList(order_id);
		return "sds_logs";
	}

	/**
	 * 显示订单明细信息
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-6
	 * @return
	 */
	public String showDetail() {
		// TODO 待实现
		// this.orderTree =
		// CommonDataFactory.getInstance().getOrderTree(order_id);
		// List<AttrInstLoadResp> attrInstLoadResps =
		// AttrBusiInstTools.validateOrderAttrInstsForPage(order_id,ConstsCore.ATTR_ACTION_lOAD);
		if (is_his_order != null && EcsOrderConsts.IS_ORDER_HIS_YES.equals(is_his_order)) {
			orderTree = CommonDataFactory.getInstance().getOrderTreeHis(order_id);
		} else {
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		}

		return "auto_ord_detail";
	}

	/**
	 * 显示外呼订单明细信息
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-6
	 * @return
	 */
	public String showCallOutDetail() {
		// TODO 待实现
		// this.orderTree =
		// CommonDataFactory.getInstance().getOrderTree(order_id);
		// List<AttrInstLoadResp> attrInstLoadResps =
		// AttrBusiInstTools.validateOrderAttrInstsForPage(order_id,ConstsCore.ATTR_ACTION_lOAD);
		if (is_his_order != null && EcsOrderConsts.IS_ORDER_HIS_YES.equals(is_his_order)) {
			orderTree = CommonDataFactory.getInstance().getOrderTreeHis(order_id);
		} else {
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		}

		return "call_out_ord_detail";
	}

	/**
	 * 显示规则侧明细信息
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-8
	 * @return
	 */
	public String showFlowsDetail() {
		this.orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		if (StringUtil.isEmpty(trace_id))
			trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		String is_aop = orderTree.getOrderExtBusiRequest().getIs_aop();
		String shippingType = orderTree.getOrderBusiRequest().getShipping_type();
		String orderForm = orderTree.getOrderExtBusiRequest().getOrder_from();
		String pre = "总商";
		if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP.equals(is_aop)) {
			pre = "AOP";
		} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
			pre = "BSS";
		}
		// 取得订单的退款模式
		String refundMode = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFUND_AUDIT_MODE);
		if (StringUtil.isEmpty(plan_id)) {
			if (EcsOrderConsts.RETURNED_TRACE_ID.equals(trace_id)) {
				// if(EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP.equals(is_aop) &&
				// EcsOrderConsts.SENDING_TYPE_XJ.equals(shippingType)){
				// plan_id = EcsOrderConsts.ORDER_RETURNED_PLAN_AUTO;//退单
				// }else
				// if(EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop) &&
				// EcsOrderConsts.SENDING_TYPE_XJ.equals(shippingType)){
				// plan_id = EcsOrderConsts.ORDER_RETURNED_PLAN_AUTO;//退单
				// }else if(StringUtils.equals(refundMode,
				// EcsOrderConsts.REFUND_AUDIT_AUTO)){
				// plan_id = EcsOrderConsts.ORDER_RETURNED_PLAN_AUTO;//退单
				// }else {
				// plan_id = EcsOrderConsts.ORDER_RETURNED_PLAN;//退单
				// }
				Map m = AttrUtils.getInstance().getRefundPlanInfo(order_id);
				plan_id = m.get("plan_id").toString();
			} else if ("REFUND".equals(trace_id)) {
				plan_id = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ORDER_REFUND_PLAN,
						"CRAWLER_REFUND");
			} else if (EcsOrderConsts.ORDER_COLLECT_PLAN.equals(trace_id)) {
				plan_id = EcsOrderConsts.ORDER_COLLECT_PLAN;// 订单归集
			} else if (EcsOrderConsts.ORDER_RETURNED_PLAN_KD.equals(trace_id)) {
				plan_id = trace_id;
			} else {
				plan_id = this.getPlanId(trace_id);
			}
		}
		if (!StringUtil.isEmpty(plan_id)) {
			PlanRuleTreeQueryReq req = new PlanRuleTreeQueryReq();
			req.setPlan_id(plan_id);
			req.setObj_id(order_id);
			req.setIs_his_order(is_his_order);
			PlanRuleTreeQueryResp resp = ruleService.queryPlanRuleTree(req);
			if (resp != null) {
				List<RuleConfig> list = resp.getPlanRuleTreeList();
				flowsTree(list);
			}
		}
		if (orderTree != null) {
			List<Map> model = getConsts(DIC_OPER_MODE);
			String mode_code = orderTree.getOrderExtBusiRequest().getOrder_model();
			if (model != null && model.size() > 0 && mode_code != null) {
				for (Map m : model) {
					String pkey = (String) m.get("pkey");
					if (mode_code.equals(pkey)) {
						mode_name = (String) m.get("pname");
						break;
					}
				}
			}
		}
		// 归集、客户回访环节显示所有，其他根据生产模式名字和开户通道过滤，特殊处理含有办理、跳过的规则
		if (!EcsOrderConsts.DIC_ORDER_NODE_B.equals(trace_id) && !"MK".equals(trace_id)
				&& !EcsOrderConsts.ORDER_COLLECT_PLAN.equals(trace_id)
				&& !EcsOrderConsts.ORDER_RETURNED_PLAN_KD.equals(trace_id)
				&& !EcsOrderConsts.RETURNED_TRACE_ID.equals(trace_id)
				&& !EcsOrderConsts.DIC_ORDER_NODE_Y.equals(trace_id)
				&& !EcsOrderConsts.DIC_ORDER_NODE_P.equals(trace_id)
				&& !EcsOrderConsts.DIC_ORDER_NODE_ZJ_O_YWBL.equals(trace_id) && !"K".equals(trace_id)) {// 归档
			List<RuleConfig> endList = new ArrayList<RuleConfig>();
			String ppid = "";
			String pid = "";
			for (RuleConfig c : planRuleTreeList) {
				if ((pre + "接口").equals(c.getRule_name())) {// 匹配显示开户通道“xx接口”这一层
					endList.add(c);
					ppid = c.getRule_id();
				}
				boolean flag = false;
				int level = 0;
				if (c.getPid().equals(ppid) && // 控制只显示xx接口一棵树
						(c.getRule_name().contains(mode_name)// 模式匹配
								|| c.getRule_name().contains("办理")// 办理、跳过单独列出来
								|| c.getRule_name().contains("跳过")// 如果以后配置有其他非模式分支，也要单独列出来
								|| c.getRule_name().contains("回单")// 回单和归档，由于没有模式分支，需要单独列出来
								|| c.getRule_name().contains("归档"))) {
					flag = true;
					level = c.getLevel();
					pid = c.getRule_id();
					endList.add(c);
				} else if (c.getRule_name().contains("终端订单发货") || c.getRule_name().contains("非终端订单发货")
						|| c.getRule_name().contains("码上购发货")) {
					flag = true;
					level = c.getLevel();
					pid = c.getRule_id();
					endList.add(c);
				} else if (c.getPid().equals(pid)) {// 模式下面一层级，全部显示
					flag = false;
					level = c.getLevel();
					endList.add(c);
				} else if (flag && c.getLevel() == level) {
					break;
				} else if (flag && c.getLevel() != level) {
					endList.add(c);
				}
			}
			planRuleTreeList = endList;
		}

		return "auto_flows_detail";
	}

	private String getPlanId(String flowid) {
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<Map> planFlowList = dcPublicCache.getList("DIC_ORDER_NODE");
		for (Map map : planFlowList) {
			String _flow_id = (String) map.get("pkey");
			if (flowid.equals(_flow_id)) {
				String _plan_id = (String) map.get("codea");
				return _plan_id;
			}
		}
		return null;
	}

	int ruleLevel = 1;

	private void flowsTree(List<RuleConfig> list) {
		if (planRuleTreeList == null)
			planRuleTreeList = new ArrayList<RuleConfig>();
		if (list != null && list.size() > 0) {
			for (RuleConfig rc : list) {
				if ("0".equals(rc.getPid()))
					ruleLevel = 1;
				rc.setLevel(ruleLevel);
				planRuleTreeList.add(rc);
				if (rc.isHasChildren()) {
					ruleLevel = rc.getLevel() + 1;
					flowsTree(rc.getChildrenList());
					ruleLevel = rc.getLevel();
				}
			}
		}
	}

	/**
	 * 显示按钮
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-6
	 * @return
	 */
	static INetCache cache;
	static int order_tache_time = 60 * 24 * 60;// 缺省
	public static int NAMESPACE = 308;
	public static String CURR_ORDER_CLICK_PAGE = "CURR_ORDER_CLICK_PAGE_";

	static {
		cache = (INetCache) com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	}

	private String is_order_exp;

	public String getIs_order_exp() {
		return is_order_exp;
	}

	public void setIs_order_exp(String is_order_exp) {
		this.is_order_exp = is_order_exp;
	}

	public String orderCancel() {
		// 执行退单流程
		PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
		TacheFact fact = new TacheFact();
		fact.setOrder_id(this.order_id);
		req.setPlan_id(EcsOrderConsts.ORDER_RETURNED_PLAN);
		req.setFact(fact);
		req.setDeleteLogs(false);// 不删除日志，不允许二次操作
		PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);

		// 执行外呼处理操作
		AdminUser user = ManagerUtils.getAdminUser();
		Map<String, String> params = new HashMap<String, String>();
		params.put("oper_id", user.getUsername());
		params.put("order_id", this.order_id);
		params.put("isOrderCancel", "true");
		this.ecsOrdManager.ordOutCallApply(params);

		// 查询数据
		return showCallOutOrderList();
	}

	public String showBtns() {
		this.orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String order_model = this.orderTree.getOrderExtBusiRequest().getOrder_model();
		if (EcsOrderConsts.OPER_MODE_PCSG.equals(order_model) || EcsOrderConsts.OPER_MODE_PCZD.equals(order_model)) {
			return showCrawlerBtns();
		} else {
			return showTacheBtns();
		}
	}

	public String showTacheBtns() {
		this.orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String status = this.orderTree.getOrderBusiRequest().getStatus().toString();
		if (status != null && "11".equals(status)) {// 外呼处理按钮
			trace_id = "OUTCALL";// CALLOUTORDER
		} else if (StringUtil.isEmpty(trace_id)) {// TODO 根据order_id获取流程ID
			trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			// trace_id = "F";
		}
		// 获取订单商品大类
		String goods_id = orderTree.getOrderBusiRequest().getGoods_id();
		String type_id = CommonDataFactory.getInstance().getGoodSpec(null, goods_id, SpecConsts.TYPE_ID);
		String cat_id = CommonDataFactory.getInstance().getGoodSpec(null, goods_id, SpecConsts.CAT_ID);

		// ====mochunrun=====20150115 核心功能======================
		if (StringUtil.isEmpty(trace_id))
			trace_id = EcsOrderConsts.ORDER_COLLECT_PLAN;
		// ====mochunrun=====20150115 核心功能======================
		List<OrderBtn> btns = this.ecsOrdManager.listOrderFlowBtns(trace_id);
		List<OrderBtn> temBtns = new ArrayList<OrderBtn>();
		ordBtns = new ArrayList<OrderBtn>();
		if (!StringUtil.isEmpty(is_order_exp) && "1".equals(is_order_exp)) {
			for (OrderBtn btn : btns) {
				String url = btn.getAction_url();
				if (url.indexOf("?") == -1) {
					url = url + "?";
				} else {
					url = url + "&";
				}
				url = url + "is_order_exp=" + is_order_exp;
				btn.setAction_url(url);
				temBtns.add(btn);
			}
		} else {
			// add by gong.lei
			for (OrderBtn btn : btns) {
				String name = btn.getBtn_ename();
				if ("o_return_back".equals(name)) {// 如果是返回按钮，增加query_type参数
					String url = btn.getAction_url();
					if (url.indexOf("?") == -1) {
						url = url + "?";
					} else {
						url = url + "&";
					}
					if (!url.contains("query_type")) {
						url = url + "query_type=" + query_type;
					}
					btn.setAction_url(url);
				}
				temBtns.add(btn);
			}
			// temBtns = btns;
		}
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String bill_num = cacheUtil.getConfigInfo("SHOW_BTN_" + trace_id);
		String show_btn_goods = cacheUtil.getConfigInfo("SHOW_BTN_INT");// 无线宽带需求提出部分按钮不需要的 同时对bss和cbss号卡业务处理
		String BSS_CBSS_SHOW_BTN = cacheUtil.getConfigInfo("BSS_CBSS_SHOW_BTN_" + trace_id);

		String BACK_BTN_ORDER_FROM = cacheUtil.getConfigInfo("BACK_BTN_ORDER_FROM");
		String BACK_BTN_ORDER_TRACE = cacheUtil.getConfigInfo("BACK_BTN_ORDER_TRACE");
		String kingcard_status = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "kingcard_status");
		// 订单生产模式(如果是10代表后激活的订单)
		String order_model = orderTree.getOrderExtBusiRequest().getOrder_model();
		// 对开户环节中的<回退到配货重试>按钮做处理 (只有合约机和裸机的才会显示<回退到配货重试>按钮)
		for (OrderBtn btn : temBtns) {
			String order_from = orderTree.getOrderExtBusiRequest().getOrder_from();
			// !"180101547042001934".equals(type_id) 处理宽带几件
			String plat_type = orderTree.getOrderExtBusiRequest().getPlat_type();

			if ("退单".equals(btn.getBtn_cname())) {
				if (BACK_BTN_ORDER_FROM.contains(order_from)) {
					if (EcsOrderConsts.ACCOUNT_STATUS_1.equals(orderTree.getOrderItemBusiRequests().get(0)
							.getOrderItemExtBusiRequest().getAccount_status())
							|| BACK_BTN_ORDER_TRACE.contains(trace_id)) {
						continue;
					}
				}
			}
			// 短信发送关闭
			if ("o_sendSMS".equals(btn.getBtn_ename())) {
				AdminUser user = new AdminUser();
				user = ManagerUtils.getAdminUser();
				if (!"open".equals(user.getCol1())) {
					continue;
				}
			}

			// System.out.println("----------------------------对宽带互联网化
			// 宽带预约单-----------------------------------");
			// System.out.println("对宽带互联网化 宽带预约单 order_from:" + order_from +
			// "-----plat_type:" + plat_type + "--type_id:" + type_id);
			// System.out.println("getBtn_ename:"+btn.getBtn_ename()+"----getBtn_cname:"+btn.getBtn_cname());
			if (("10078".equals(order_from) || "AO".equals(plat_type) || "100901".equals(order_from)
					|| "100902".equals(order_from) || "100903".equals(order_from) || "100904".equals(order_from)
					|| "100905".equals(order_from) || "100906".equals(order_from) || "100907".equals(order_from)
					|| "100908".equals(order_from) || "100909".equals(order_from))
					&& !"180101547042001934".equals(type_id)) {
				// 工单申请按钮针对宽带互联网化 宽带预约单
				if (!StringUtil.isEmpty(kingcard_status) && !"01".equals(kingcard_status)) {
					if ("o_dispose".equals(btn.getBtn_ename()) || "o_hang".equals(btn.getBtn_ename())
							|| "o_return_back".equals(btn.getBtn_ename())) {
						ordBtns.add(btn);
						continue;
					} else {
						continue;
					}
				}
				if (bill_num.contains(btn.getBtn_ename() + ",")) {
					ordBtns.add(btn);
					continue;
				}

			} else {

				if ("B".equals(trace_id)) {
					if (!StringUtil.isEmpty(show_btn_goods)
							&& (show_btn_goods.contains(type_id) || show_btn_goods.contains(cat_id))) {
						if (!StringUtil.isEmpty(BSS_CBSS_SHOW_BTN)
								&& BSS_CBSS_SHOW_BTN.contains(btn.getBtn_ename() + ",")) {
							continue;
						}
					}
					if (bill_num.contains(btn.getBtn_ename() + ",")) {
						if (!"180101547042001934".equals(type_id)) {
							if (!"o_save".equals(btn.getBtn_ename()) && !"o_return_back".equals(btn.getBtn_ename())) {
								continue;
							}
						} else {
							if (!"o_workApply".equals(btn.getBtn_ename()) && !"o_save".equals(btn.getBtn_ename())
									&& !"o_return_back".equals(btn.getBtn_ename())) {
								continue;
							}
						}
					}
				}

				if ("D".equals(trace_id) && "rebackprepick".equals(btn.getBtn_ename())) {
					if (!(ZjEcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(type_id)
							|| ZjEcsOrderConsts.GOODS_TYPE_PHONE.equals(type_id))) {
						continue;
					}
				}
				if ("D".equals(trace_id) && !StringUtil.isEmpty(show_btn_goods)
						&& (show_btn_goods.contains(type_id) || show_btn_goods.contains(cat_id))) {
					if (!StringUtil.isEmpty(BSS_CBSS_SHOW_BTN)
							&& BSS_CBSS_SHOW_BTN.contains(btn.getBtn_ename() + ",")) {
						continue;
					}
				}
				if ("D".equals(trace_id) && !"221668199563784192".equals(cat_id) && !"10093".equals(order_from)
						&& "rebackmk".equals(btn.getBtn_ename())) {// 库管环节处理 无线宽带融合才显示的按钮
					continue;
				}
				// 温州绿森发货环节不需要打印 物流热敏单打印 打印物流单 保存按钮
				if ("H".equals(trace_id) && "10106".equals(order_from)) {
					if ("o_print_wl".equals(btn.getBtn_ename()) || "free_print_wl".equals(btn.getBtn_ename())
							|| "o_save".equals(btn.getBtn_ename())) {
						continue;
					}
				}
				/*
				 * else if ("D".equals(trace_id) &&
				 * "o_offlineHandle".equals(btn.getBtn_ename())){ //对开户环节中的<线下办理>按钮做处理
				 * (只有单宽带的才会显示<线下办理>按钮) if(!(EcsOrderConsts.GOODS_TYPE_DKD.equals(type_id))){
				 * continue; } }
				 */

				// 号卡-省内和非订单中心生产号卡和号卡测试 显示认证成功”、“认证失败”、“后激活退单按钮”
				// 号卡省内170801435262003016 非订单中心生产号卡170601900021724272
				//
				// if((type_id.equals(ZjEcsOrderConsts.GOODS_TYPE_HAOKA)) ||
				// type_id.equals(ZjEcsOrderConsts.GOODS_TYPE_JIHUO)) {
				//
				// }

				if (!"10012".equals(order_from)) {
					if ("do_shipping".equals(btn.getBtn_ename())) {
						continue;
					}
				}
				String IS_SHOW_UPLOAD_PHOTOS_BTN = cacheUtil
						.getConfigInfo("UPLOAD_PHOTOS_BTN_" + order_from + "_CAT_ID_" + cat_id);
				if (!StringUtil.isEmpty(IS_SHOW_UPLOAD_PHOTOS_BTN) && "yes".equals(IS_SHOW_UPLOAD_PHOTOS_BTN)) {
					if ("o_sms".equals(btn.getBtn_ename())) {
						ordBtns.add(btn);
						continue;
					}
				} else {
					if ("o_sms".equals(btn.getBtn_ename())) {
						continue;
					}
				}
				// free_print_wl
				/*
				 * if ("10093".equals(order_from) && "221668199563784192".equals(cat_id) &&
				 * "H".equals(trace_id)) { if ("free_print_wl".equals(btn.getBtn_ename()) &&
				 * btn.getAction_url().contains("print_type")) { continue; } }
				 */
				// 是后激活的订单或者商品类型为号卡-省内和非订单中心生产号卡的订单展示所有按钮
				if (type_id.equals(ZjEcsOrderConsts.GOODS_TYPE_HAOKA)
						|| type_id.equals(ZjEcsOrderConsts.GOODS_TYPE_JIHUO)) {
					if ("o_shop".equals(btn.getBtn_ename())) {// 判断是否是新增的按钮
					} else {
						ordBtns.add(btn);
					}
					continue;
				}

				// 如果不是后激活的订单，不展示“认证成功”、“认证失败”、“后激活退单按钮”
				if (!"10".equals(order_model)) {
					if ("o_auth_succ".equals(btn.getBtn_ename()) || "o_auth_fail".equals(btn.getBtn_ename())
							|| "o_auth_back".equals(btn.getBtn_ename())) {
						continue;
					}
				}
				ordBtns.add(btn);
			}
		}

		cache.set(NAMESPACE, CURR_ORDER_CLICK_PAGE + order_id, trace_id, order_tache_time); // 设置当前页面操作环节
		return "list_btns";
	}

	public String showCrawlerBtns() {
		this.orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String status = this.orderTree.getOrderBusiRequest().getStatus().toString();
		if (status != null && "11".equals(status)) {// 外呼处理按钮
			trace_id = "OUTCALL";// CALLOUTORDER
		} else if (StringUtil.isEmpty(trace_id)) {// TODO 根据order_id获取流程ID
			trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			// trace_id = "F";
		}
		// 获取订单商品大类
		String goods_id = orderTree.getOrderBusiRequest().getGoods_id();
		String type_id = CommonDataFactory.getInstance().getGoodSpec(null, goods_id, SpecConsts.TYPE_ID);

		// ====mochunrun=====20150115 核心功能======================
		if (StringUtil.isEmpty(trace_id))
			trace_id = EcsOrderConsts.ORDER_COLLECT_PLAN;
		// ====mochunrun=====20150115 核心功能======================
		List<OrderBtn> btns = this.ecsOrdManager.listOrderCrawlerBtns(trace_id);
		List<OrderBtn> temBtns = new ArrayList<OrderBtn>();
		ordBtns = new ArrayList<OrderBtn>();
		if (!StringUtil.isEmpty(is_order_exp) && "1".equals(is_order_exp)) {
			for (OrderBtn btn : btns) {
				String url = btn.getAction_url();
				if (url.indexOf("?") == -1) {
					url = url + "?";
				} else {
					url = url + "&";
				}
				url = url + "is_order_exp=" + is_order_exp;
				btn.setAction_url(url);
				temBtns.add(btn);
			}
		} else {
			for (OrderBtn btn : btns) {
				String name = btn.getBtn_ename();
				if ("o_return_back".equals(name)) {// 如果是返回按钮，增加query_type参数
					String url = btn.getAction_url();
					if (url.indexOf("?") == -1) {
						url = url + "?";
					} else {
						url = url + "&";
					}
					if (!url.contains("query_type")) {
						url = url + "query_type=" + query_type;
					}
					btn.setAction_url(url);
				}
				temBtns.add(btn);
			}
		}
		// 对开户环节中的<回退到配货重试>按钮做处理 (只有合约机和裸机的才会显示<回退到配货重试>按钮)
		for (OrderBtn btn : temBtns) {
			if ("D".equals(trace_id) && "rebackprepick".equals(btn.getBtn_ename())) {
				if (!(ZjEcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(type_id)
						|| ZjEcsOrderConsts.GOODS_TYPE_PHONE.equals(type_id))) {
					continue;
				}
			}
			ordBtns.add(btn);
		}
		cache.set(NAMESPACE, CURR_ORDER_CLICK_PAGE + order_id, trace_id, order_tache_time); // 设置当前页面操作环节
		return "list_btns";
	}

	/**
	 * 执行订单流程环节规则
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-8
	 * @return
	 */
	public String exeFlows() {
		// TODO 根据order_id获取plan_id
		// this.orderTree =
		// CommonDataFactory.getInstance().getOrderTree(order_id);
		// String flowid = orderTree.getOrderExtBusiRequest().getFlow_id();
		// plan_id = this.getPlanId(flowid);
		plan_id = "1";
		// executeRule();
		json = "{status:0,msg:'成功'}";
		return JSON_MESSAGE;
	}

	private OrderDeliveryBusiRequest delivery;
	private String shipping_cop_name;
	private String shipping_cop_id;
	private String deal_type = "";
	private String quick_shipping_cop = "";
	private String quick_shipping_status = "";

	public String showShipping() {
		// CommonDataFactory.getInstance().updateOrderTree(order_id);
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		orderTree.getOrderExtBusiRequest().setAbnormal_status("1");

		// 流程编码
		flowCode = ecsOrdManager.getFlowCode(order_id);
		// 物流公司编码
		logiCompanyCode = orderTree.getOrderDeliveryBusiRequests().get(0).getShipping_company();
		// 显示物流公司
		logiCompanyList = ecsOrdManager.logi_company(order_id);

		if (EcsOrderConsts.SHIPPING_QUICK_02.equals(orderTree.getOrderExtBusiRequest().getShipping_quick())) {
			deal_type = "闪电送";
			if (!StringUtil.isEmpty(orderTree.getOrderExtBusiRequest().getQuick_ship_company_code())) {
				this.listShiipingCop();
				for (Map m : shipping_cop_list) {
					String pkey = (String) m.get("pkey");
					if (orderTree.getOrderExtBusiRequest().getQuick_ship_company_code().equals(pkey)) {
						quick_shipping_cop = (String) m.get("pname");
						break;
					}
				}
			}
			String q_status = orderTree.getOrderExtBusiRequest().getQuick_ship_status();
			if (!StringUtil.isEmpty(q_status)) {
				this.listQuickStatus();
				for (Map m : quick_status_list) {
					String key = (String) m.get("pkey");
					if (q_status.equals(key)) {
						quick_shipping_status = (String) m.get("pname");
						break;
					}
				}
			}
		}
		if (orderTree.getOrderDeliveryBusiRequests() != null && orderTree.getOrderDeliveryBusiRequests().size() > 0) {
			delivery = orderTree.getOrderDeliveryBusiRequests().get(0);
			List<Map> list = this.getConsts("shipping_company");
			String cop_code = delivery.getShipping_company();
			if (!StringUtil.isEmpty(cop_code)) {
				shipping_cop_id = cop_code;
				for (Map m : list) {
					String pkey = (String) m.get("pkey");
					if (cop_code.equals(pkey)) {
						shipping_cop_name = (String) m.get("pname");
						break;
					}
				}
			}
		}
		String goods_id=orderTree.getOrderBusiRequest().getGoods_id();
		StringBuffer sql_goods_elem_info=new StringBuffer();
 		sql_goods_elem_info.append("select eoe.element_id,eoe.mobile_type,eoe.object_esn,ae.terminal_name,ae.scheme_id ").
		append("from es_order_extvtl eoe left join ES_GOODS_ACTION_ELEMENT ae on ae.element_id=eoe.element_id "
				+ "and ae.mobile_type=eoe.mobile_type").append(" where eoe.order_id='").append(order_id).append("'")
				.append(" and ae.mobile_type=eoe.mobile_type and ae.goods_id='").append(goods_id).append("'")
				.append(" and ae.source_from=eoe.source_from");
 		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
 		List<Map<String, String>> listelemInfo = baseDaoSupport.queryForList(sql_goods_elem_info.toString());
 		if (listelemInfo.size()>0) {
			 this.element_id=listelemInfo.get(0).get("element_id");
			 this.mobile_type=listelemInfo.get(0).get("mobile_type");
			 this.object_esn=listelemInfo.get(0).get("object_esn");
			 this.terminal_name=listelemInfo.get(0).get("terminal_name");
			 this.scheme_id=listelemInfo.get(0).get("scheme_id");
		}
		// List<AttrInstLoadResp> attrInstLoadResps =
		// AttrBusiInstTools.validateOrderAttrInstsForPage(order_id,ConstsCore.ATTR_ACTION_lOAD);
		// if(attrInstLoadResps.size()>0)
		// attrInstLoadResps
		// =AttrBusiInstTools.validateOrderAttrInstsForPage(order_id,
		// ConstsCore.ATTR_ACTION_UPDATE);
		// if(attrInstLoadResps.size()>0)
		return "auto_order_shipping";
	}

	/**
	 * 保存订单信息
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-8
	 * @return
	 */
	public String save() {
		// TODO 保存与校验订单信息

		// orderTree.store();
		// CommonDataFactory.getInstance().updateOrderTree(orderTree.getOrder_id());
		json = "{status:0,msg:'成功'}";
		return JSON_MESSAGE;
	}

	/**
	 * 订单挂起
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-8
	 * @return
	 */
	public String suspend() {
		// TODO 订单挂起
		return "ord_suspend";
	}

	/**
	 * 订单挂起保存
	 * 
	 * @return
	 */
	public String suspend_save() {
		// TODO 订单挂起保存
		try {
			if (StringUtils.isNotBlank(order_id)) {
				String lockMsg = checkLockUser();
				if (!StringUtils.isEmpty(lockMsg)) {
					this.json = "{result:1,message:'" + lockMsg + "'}";
					return this.JSON_MESSAGE;
				}
				ecsOrdManager.suspend_save(order_id, pending_reason);
				json = "{result:0,message:'成功'}";
			} else {
				json = "{result:2,message:'order_id不可空'}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = "{result:1,message:'失败'}";
		}
		return JSON_MESSAGE;
	}

	/**
	 * 订单委托
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-8
	 * @return
	 */
	public String entrust() {
		// TODO 订单委托
		// userid = ManagerUtils.getAdminUser().getUserid();
		// orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		// orderTree.getOrderExtBusiRequest().getLock_user_id();

		webpage = ecsOrdManager.adminUserList(this.getPage(), this.getPageSize(), obj_name, order_id);

		return "ord_entrust";
	}

	/**
	 * 订单委托保存
	 * 
	 * @作者 ZX
	 * @创建日期 2014-10-16
	 * @return
	 */
	public String entrust_save() {
		// TODO 订单委托
		try {
			String lockMsg = checkLockUser();
			if (!StringUtils.isEmpty(lockMsg)) {
				this.json = "{result:1,message:'" + lockMsg + "'}";
				return this.JSON_MESSAGE;
			}
			String res = ecsOrdManager.entrust_save(order_id, userid, username);
			if (res.equals("0"))
				json = "{result:0, message:'成功'}";
			else
				json = "{result:1, message:'此单已被锁定！'}";
		} catch (Exception e) {
			e.printStackTrace();
			json = "{result:2, message:'失败'}";
		}
		return JSON_MESSAGE;
	}

	/**
	 * 订单退单
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-8
	 * @return
	 */
	public String returned() {
		// TODO 订单退单
		json = "{status:0,msg:'成功'}";
		return JSON_MESSAGE;
	}

	/**
	 * 订单驳回
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-8
	 * @return
	 */
	public String rebut() {
		// TODO 订单驳回
		json = "{status:0,msg:'成功'}";
		return JSON_MESSAGE;
	}

	/**
	 * 标志异常
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-13
	 * @return
	 */
	public String addException() {
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		exceptionList = dcPublicCache.getList("DIC_ORDER_EXCEPTION_TYPE");
		is_or_no_list = dcPublicCache.getList("is_or_no");
		// 批量预处理标记异常
		if (StringUtils.isNotEmpty(exception_from) && "batch_pre".equals(exception_from)) {
			return "batch_ord_exception";
		}
		// json = JSONArray.toJSONString(exceptionList);
		return "ord_exception";

	}

	/**
	 * @作者 ZX
	 * @创建日期 2014-10-15
	 * @return
	 */
	public String addException_save() {
		/*
		 * if(!"yes".equals(is_rg_exception)){
		 * 
		 * }
		 */
		String lockMsg = checkLockUser();
		if (!StringUtils.isEmpty(lockMsg)) {
			this.json = "{result:1,message:'" + lockMsg + "'}";
			return this.JSON_MESSAGE;
		}
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String flow_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();

		// is_rg_exception 是否人工标志异常
		if ("yes".equals(is_rg_exception) || EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id)
				|| EcsOrderConsts.DIC_ORDER_NODE_C.equals(flow_trace_id)) {
			// 预校验 直接标志异常
			/*
			 * exception_id - 异常ID exception_remark - 备注 abnormal_type - 异常类型默认（1）
			 */
			Map params = new HashMap();
			params.put("order_id", order_id);
			params.put("exception_id", exception_id);
			params.put("exception_remark", exception_remark);
			params.put("abnormal_type", abnormal_type);
			params.put("need_customer_visit", need_customer_visit);
			OrderExceptionCollectResp orderExceptionCollectResp = ordExceptionHandleImpl.exceptionHandleAct(params);

			/*
			 * RuleFlowUtil.delRuleExeLogs(EcsOrderConsts. ORDER_STATUS_SYN_OLD_SYS_PLAN,
			 * EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_RULE, order_id); PlanRuleTreeExeReq
			 * plan = new PlanRuleTreeExeReq(); TacheFact fact = new TacheFact();
			 * fact.setOrder_id(order_id);
			 * fact.setSrc_tache_code(CommonDataFactory.getInstance().
			 * getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id( ));
			 * fact.setTar_tache_code(CommonDataFactory.getInstance().
			 * getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id( ));
			 * fact.setProcess_type(EcsOrderConsts.PROCESS_TYPE_3);
			 * fact.setException_from("ORD"); fact.setException_type(exception_id);
			 * fact.setException_desc(exception_remark); plan.setFact(fact);
			 * plan.setPlan_id(EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_PLAN);
			 * PlanRuleTreeExeResp planResp =
			 * CommonDataFactory.getInstance().exePlanRuleTree(plan);
			 */

			json = "{result:" + orderExceptionCollectResp.getError_code() + "," + "message:'"
					+ orderExceptionCollectResp.getError_msg() + "'}";

			// 异常记录
			OrderExceptionLogsReq exceptionLogReq = new OrderExceptionLogsReq();
			AdminUser user = new AdminUser();
			user = ManagerUtils.getAdminUser();
			String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
			exceptionLogReq.setOrder_id(order_id);
			exceptionLogReq.setFlow_id(flow_id);
			exceptionLogReq.setFlow_trace_id(flow_trace_id);
			if (null != user) {
				exceptionLogReq.setMark_op_id(user.getUserid());
				exceptionLogReq.setMark_op_name(user.getUsername());
			}
			if (EcsOrderConsts.ORDER_ABNORMAL_TYPE_2.equals(abnormal_type)) {
				abnormal_type = EcsOrderConsts.ORDER_ABNORMAL_TYPE_3;
			}
			exceptionLogReq.setAbnormal_type(abnormal_type);
			exceptionLogReq.setException_desc(exception_remark);
			exceptionLogReq.setException_type(exception_id);
			exceptionLogReq.setNeed_customer_visit(need_customer_visit);
			this.ordFlowManager.insertOrderExceptionLog(exceptionLogReq);

			// 调用异常系统
			Utils.writeExp(order_id, exception_id, exception_remark, EcsOrderConsts.BASE_YES_FLAG_1);

			// 人工异常标志按钮 预处理环节不需要解锁，其它环节需要解锁订单
			if ("yes".equals(is_rg_exception) && !EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id)) {
				orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
				// orderExtBusiRequest.setOrder_id(order_id);
				// orderExtBusiRequest.setLock_status(EcsOrderConsts.UNLOCK_STATUS);
				// orderExtBusiRequest.setLock_user_id(ConstsCore.NULL_VAL);
				// orderExtBusiRequest.setLock_user_name(ConstsCore.NULL_VAL);
				// orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				// orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				// orderExtBusiRequest.store();

				/*
				 * List<OrderLockBusiRequest>
				 * orderLockRequest=orderTree.getOrderLockBusiRequests(); OrderLockBusiRequest
				 * orderLockBusiRequest=null; for(int i=0;i<orderLockRequest.size();i++){
				 * if(orderExtBusiRequest.getFlow_trace_id().equals(
				 * orderLockRequest.get(i).getLock_status())){
				 * orderLockBusiRequest=orderLockRequest.get(i); } }
				 */

				String currTacheCode = orderExtBusiRequest.getFlow_trace_id();
				OrderLockBusiRequest orderLockBusiRequest = CommonDataFactory.getInstance()
						.getOrderLockBusiRequest(orderTree, currTacheCode);
				if (orderLockBusiRequest == null) {// 该环节没有锁定
					orderLockBusiRequest = new OrderLockBusiRequest();
					orderLockBusiRequest.setLock_id(ecsOrdManager.getSequences("o_outcall_log"));
					orderLockBusiRequest.setOrder_id(order_id);
					orderLockBusiRequest.setTache_code(orderExtBusiRequest.getFlow_trace_id());
					orderLockBusiRequest.setLock_user_id(ConstsCore.NULL_VAL);
					orderLockBusiRequest.setLock_user_name(ConstsCore.NULL_VAL);
					orderLockBusiRequest.setLock_status(EcsOrderConsts.UNLOCK_STATUS);
					try {
						orderLockBusiRequest.setLock_time(DateUtil.getTime2());
					} catch (FrameException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					orderLockBusiRequest.setSource_from(ManagerUtils.getSourceFrom());
					orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					// 解锁订单释放工号池和锁单结束时间
					orderLockBusiRequest.setPool_id(ConstsCore.NULL_VAL);
					orderLockBusiRequest.setLock_end_time(ConstsCore.NULL_VAL);
					orderLockBusiRequest.store();
				} else {// 该环节有锁定
					orderLockBusiRequest.setOrder_id(order_id);
					orderLockBusiRequest.setLock_status(EcsOrderConsts.UNLOCK_STATUS);
					orderLockBusiRequest.setLock_user_id(ConstsCore.NULL_VAL);
					orderLockBusiRequest.setLock_user_name(ConstsCore.NULL_VAL);
					orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					// 解锁订单释放工号池和锁单结束时间
					orderLockBusiRequest.setPool_id(ConstsCore.NULL_VAL);
					orderLockBusiRequest.setLock_end_time(ConstsCore.NULL_VAL);
					orderLockBusiRequest.store();
				}

			}

		} else if (EcsOrderConsts.DIC_ORDER_NODE_D.equals(flow_trace_id)) {
			BusiCompRequest busi = new BusiCompRequest();
			Map queryParams = new HashMap();
			queryParams.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			queryParams.put("order_id", this.order_id);
			queryParams.put("exception_type", EcsOrderConsts.ABNORMAL_TYPE_OPEN);
			queryParams.put("exception_remark", "开户异常");
			queryParams.put("write_exp_flag", EcsOrderConsts.BASE_YES_FLAG_1);
			busi.setEnginePath("zteCommonTraceRule.markedException");
			busi.setOrder_id(this.order_id);
			busi.setQueryParams(queryParams);
			try {
				ZteResponse response = orderServices.execBusiComp(busi);
				if (response == null || !"0".equals(response.getError_code())) {
					json = "{result:1," + "message:'标记异常失败'}";
				} else {
					json = "{result:0," + "message:'标记异常成功'}";

				}
			} catch (Exception e) {
				json = "{result:1," + "message:'处理失败'}";
			}
		} else {
			// 预拣货--标志异常修改为转手工开户
			// 转手工开户逻辑-->调异常标志业务组件
			// 调用业务组件
			BusiCompRequest busi = new BusiCompRequest();
			Map queryParams = new HashMap();
			queryParams.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			queryParams.put("order_id", this.order_id);
			queryParams.put("exception_type", exception_id);
			queryParams.put("exception_remark", exception_remark);
			queryParams.put("write_exp_flag", EcsOrderConsts.BASE_YES_FLAG_1);
			busi.setEnginePath("zteCommonTraceRule.markedException");
			busi.setOrder_id(this.order_id);
			busi.setQueryParams(queryParams);
			try {
				ZteResponse response = orderServices.execBusiComp(busi);
				if (response == null || !"0".equals(response.getError_code())) {
					json = "{result:1," + "message:'标记异常失败'}";
				} else {
					json = "{result:0," + "message:'标记异常成功'}";

				}
			} catch (Exception e) {
				json = "{result:1," + "message:'处理失败'}";
			}
		}

		return JSON_MESSAGE;
	}

	/**
	 * 标记开户异常并回退到预捡货
	 * 
	 * @return
	 */
	public String addOpenException() {
		String write_card_status = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
				AttrConsts.WRITE_CARD_STATUS);
		if (EcsOrderConsts.WRITE_CARD_STATUS_1.equals(write_card_status)
				|| EcsOrderConsts.WRITE_CARD_STATUS_2.equals(write_card_status)
				|| EcsOrderConsts.WRITE_CARD_STATUS_3.equals(write_card_status)
				|| EcsOrderConsts.WRITE_CARD_STATUS_4.equals(write_card_status)
				|| StringUtils.isEmpty(write_card_status)) {

			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);// CommonDataFactory.getInstance().getOrderTree(order_id);
			OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();

			/*
			 * List<OrderLockBusiRequest>
			 * orderLockRequest=orderTree.getOrderLockBusiRequests(); OrderLockBusiRequest
			 * orderLockBusiRequest=null; for(int i=0;i<orderLockRequest.size();i++){
			 * if(orderExt.getFlow_trace_id().equals(orderLockRequest.get(i).
			 * getLock_status())){ orderLockBusiRequest=orderLockRequest.get(i); }
			 * 
			 * }
			 */

			// 退单订单不允许回退，防止回退后删除退单规则执行日志
			if (EcsOrderConsts.REFUND_STATUS_08.equals(orderExt.getRefund_status())// 退单申请
					|| EcsOrderConsts.REFUND_STATUS_01.equals(orderExt.getRefund_status())// 退单确认通过
					|| EcsOrderConsts.REFUND_STATUS_02.equals(orderExt.getRefund_status())// BSS返销
					|| EcsOrderConsts.REFUND_STATUS_03.equals(orderExt.getRefund_status())) {// ESS返销
				this.json = "{result:1,message:'订单已退单或退单申请，不允许回退'}";
				return this.JSON_MESSAGE;
			}
			// 解锁订单
			// orderExt.setLock_user_id(userid);
			// orderExt.setLock_user_name(ConstsCore.NULL_VAL);
			// orderExt.setLock_status(EcsOrderConsts.LOCK_STATUS);
			// orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			// orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			// orderExt.store();

			String currTacheCode = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			OrderLockBusiRequest orderLockBusiRequest = CommonDataFactory.getInstance()
					.getOrderLockBusiRequest(orderTree, currTacheCode);
			if (orderLockBusiRequest == null) {// 该环节没有锁定记录
				orderLockBusiRequest = new OrderLockBusiRequest();
				orderLockBusiRequest.setLock_id(ecsOrdManager.getSequences("o_outcall_log"));
				orderLockBusiRequest.setOrder_id(order_id);
				orderLockBusiRequest.setTache_code(orderTree.getOrderExtBusiRequest().getFlow_trace_id());
				orderLockBusiRequest.setLock_user_id(ConstsCore.NULL_VAL);
				orderLockBusiRequest.setLock_user_name(ConstsCore.NULL_VAL);
				orderLockBusiRequest.setLock_status(EcsOrderConsts.UNLOCK_STATUS);
				try {
					orderLockBusiRequest.setLock_time(DateUtil.getTime2());
				} catch (FrameException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				orderLockBusiRequest.setSource_from(ManagerUtils.getSourceFrom());
				orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
				// 解锁订单释放工号池和锁单结束时间
				orderLockBusiRequest.setPool_id(ConstsCore.NULL_VAL);
				orderLockBusiRequest.setLock_end_time(ConstsCore.NULL_VAL);
				orderLockBusiRequest.store();
			} else {// 该环节有锁定

				orderLockBusiRequest.setLock_user_id(userid);
				orderLockBusiRequest.setLock_user_name(ConstsCore.NULL_VAL);
				orderLockBusiRequest.setLock_status(EcsOrderConsts.LOCK_STATUS);
				orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				// 锁单信息增加工号池和锁单结束时间
				orderLockBusiRequest.setPool_id(workPoolManager.getLockTimeByUserId(userid).getPool_id());
				orderLockBusiRequest.setLock_end_time(workPoolManager.getLockTimeByUserId(userid).getLock_end_time());
				orderLockBusiRequest.store();
			}

			try {
				markOpenErException(this.order_id);
				String lockMsg = checkLockUser();
				if (!StringUtils.isEmpty(lockMsg)) {
					this.json = "{result:1,message:'" + lockMsg + "'}";
					return this.JSON_MESSAGE;
				}
				BusiCompResponse response2 = ordFlowManager.reBackToPrepick(order_id);
				this.json = "{result:0,message:'标记开户异常并回退成功'}";
			} catch (Exception e) {
				json = "{result:1," + "message:'处理失败'}";
			}
		} else {
			json = "{result:1," + "message:'已写卡完成，不能回退订单'}";
		}
		return JSON_MESSAGE;
	}

	public void markOpenErException(String order_id) {
		OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();

		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		String exception_from = "ORD";
		String exceptionType = "OpenEr";
		String exceptionDesc = "开户异常";

		String abnormal_type = EcsOrderConsts.ORDER_ABNORMAL_TYPE_0; // 默认正常
		if (EcsOrderConsts.SEND_ZB_1.equals(orderExt.getSend_zb())) { // 判断是否总部交互
			// 生产模式
			if (EcsOrderConsts.ORDER_MODEL_01.equals(orderExt.getOrder_model())) {
				// 标记自动化异常
				abnormal_type = EcsOrderConsts.ORDER_ABNORMAL_TYPE_3;
				// 是否已发送到WMS
				String syn_ord_wms = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
						AttrConsts.SYN_ORD_WMS);
				// 通知WMS自动化异常
				/*
				 * if(!EcsOrderConsts.EXCEPTION_FROM_WMS.equals(exception_from) &&
				 * EcsOrderConsts.SYN_ORD_WMS_1.equals(syn_ord_wms)){ String currTraceId =
				 * orderExt.getFlow_trace_id(); //当前环节ID String wms_status = "";
				 * if(EcsOrderConsts.DIC_ORDER_NODE_C.equals(currTraceId) ||
				 * EcsOrderConsts.DIC_ORDER_NODE_D.equals(currTraceId)){ //预检货、开户异常 wms_status =
				 * EcsOrderConsts.ORDER_STATUS_WMS_17; }else
				 * if(EcsOrderConsts.DIC_ORDER_NODE_X.equals(currTraceId)){ //写卡异常 wms_status =
				 * EcsOrderConsts.ORDER_STATUS_WMS_18; }else
				 * if(EcsOrderConsts.DIC_ORDER_NODE_Y.equals(currTraceId)){ //业务办理异常 wms_status
				 * = EcsOrderConsts.ORDER_STATUS_WMS_19; } NotifyOrderStatusToWMSReq notifyWMS =
				 * new NotifyOrderStatusToWMSReq(); notifyWMS.setOrderId(order_id);
				 * notifyWMS.setNotNeedReqStrWms_status(wms_status); client.execute(notifyWMS,
				 * NotifyOrderStatusToWMSResp.class); }
				 */
				// 通知森锐掉卡
				// if(EcsOrderConsts.NOTIFY_SR_DK_VAL.equals(excep.getNotify_sr())){
				// RevertCardRequset request = new RevertCardRequset();
				// request.setNotNeedReqStrOrderId(order_id);
				// request.setRequest(InfConst.SR_RECYCLE_CARD);
				// client.execute(request, RevertCardResponse.class);
				// }

			} else {
				// 订单系统 标记伪自动化异常
				abnormal_type = EcsOrderConsts.ORDER_ABNORMAL_TYPE_2;
			}

			// 通知总部自动化异常
			if (!EcsOrderConsts.EXCEPTION_FROM_ZB.equals(exception_from) && !StringUtil.isEmpty(exceptionType)) {
				NotifyOrderAbnormalToZBRequest zbReq = new NotifyOrderAbnormalToZBRequest();
				zbReq.setNotNeedReqStrOrderId(order_id);
				zbReq.setNoticeType(EcsOrderConsts.Notice_Type_MarkException);
				zbReq.setExceptionType(exceptionType);
				zbReq.setExceptionDesc(exceptionDesc);
				client.execute(zbReq, NotifyOrderAbnormalToZBResponse.class);
			}
			if (StringUtil.isEmpty(exceptionType))
				abnormal_type = EcsOrderConsts.ORDER_ABNORMAL_TYPE_1;
			// 异常标记后处理add by wui
			// if(!StringUtils.isEmpty(excep.getException_desc())){
			// HashMap para = new HashMap();
			// para.put("order_id", busiCompRequest.getOrder_id());
			// para.put("error_msg",excep.getException_desc());
			// CommonDataFactory.getInstance().notifyBusiException(para);
			// }
		} else {
			abnormal_type = EcsOrderConsts.ORDER_ABNORMAL_TYPE_1;
		}
		exceptionType = StringUtil.isEmpty(exceptionType) ? exception_id : exceptionType;
		exceptionDesc = StringUtil.isEmpty(exceptionDesc) ? exceptionDesc : exceptionDesc;
		orderExt.setException_type(exceptionType);
		orderExt.setException_desc(exceptionDesc);
		orderExt.setAbnormal_type(abnormal_type);
		orderExt.setAbnormal_status(EcsOrderConsts.ABNORMAL_STATUS_1);
		orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExt.store();

		// 人工标记异常、自动化异常通知老系统
		String process_type = null;
		if (EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(abnormal_type)
				|| EcsOrderConsts.ORDER_ABNORMAL_TYPE_2.equals(abnormal_type)) {// 自动化异常
			process_type = EcsOrderConsts.PROCESS_TYPE_2;
		} else if (EcsOrderConsts.ORDER_ABNORMAL_TYPE_1.equals(abnormal_type)) {
			process_type = EcsOrderConsts.PROCESS_TYPE_3;
		}
		if (!StringUtils.isEmpty(process_type)) {
			RuleFlowUtil.delRuleExeLogs(EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_PLAN,
					EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_RULE, order_id);
			PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
			TacheFact fact = new TacheFact();
			fact.setOrder_id(order_id);
			fact.setSrc_tache_code(
					CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id());
			fact.setTar_tache_code(
					CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id());
			fact.setProcess_type(process_type);
			fact.setException_from(exception_from);
			fact.setException_type(exceptionType);
			fact.setException_desc(exceptionDesc);
			plan.setFact(fact);
			plan.setPlan_id(EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_PLAN);
			PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(plan);
		}

		// 写日志
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderHandleLogsReq logReq = new OrderHandleLogsReq();
		String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
		String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		logReq.setOrder_id(order_id);
		logReq.setFlow_id(flow_id);
		logReq.setFlow_trace_id(flowTraceId);
		logReq.setComments(orderExt.getException_desc());
		logReq.setHandler_type(EcsOrderConsts.ORDER_HANDLER_TYPE_EXCEPTION);
		logReq.setException_id(exception_id);
		logReq.setType_code(exceptionType);
		AdminUser user = new AdminUser();
		user = ManagerUtils.getAdminUser();
		if (null != user) {
			logReq.setOp_id(user.getUserid());
			logReq.setOp_name(user.getUsername());
		}
		this.ordFlowManager.insertOrderHandLog(logReq);

		// 异常记录
		OrderExceptionLogsReq exceptionLogReq = new OrderExceptionLogsReq();
		exceptionLogReq.setOrder_id(order_id);
		exceptionLogReq.setFlow_id(flow_id);
		exceptionLogReq.setFlow_trace_id(flowTraceId);
		if (null != user) {
			exceptionLogReq.setMark_op_id(user.getUserid());
			exceptionLogReq.setMark_op_name(user.getUsername());
		}
		if (EcsOrderConsts.ORDER_ABNORMAL_TYPE_2.equals(abnormal_type)) {
			abnormal_type = EcsOrderConsts.ORDER_ABNORMAL_TYPE_3;
		}
		exceptionLogReq.setAbnormal_type(abnormal_type);
		exceptionLogReq.setException_desc(exceptionDesc);
		exceptionLogReq.setException_type(exceptionType);
		this.ordFlowManager.insertOrderExceptionLog(exceptionLogReq);

		// 调用异常系统
		Utils.writeExp(order_id, exceptionType, exceptionDesc, EcsOrderConsts.BASE_YES_FLAG_1);

		// 可见性设置
		// this.setOrderVisable(order_id);
		OrderExtBusiRequest orderExtReq = CommonDataFactory.getInstance().getOrderTree(order_id)
				.getOrderExtBusiRequest();
		String order_visible = getOrderVisible(order_id);
		orderExtReq.setVisible_status(order_visible);
		orderExtReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExtReq.store();
	}

	// /**
	// * 自动化线线强制踢出订单
	// * @param order_id
	// * @return
	// */
	// public String shotOffAutoProduction(){
	// OrderTreeBusiRequest orderTree =
	// CommonDataFactory.getInstance().getOrderTree(order_id);
	// if (orderTree == null) {
	// json = "{result:1," + "message:'操作失败,订单不存在或者订单已归档!'}";
	// return JSON_MESSAGE;
	// }
	//
	// ordWriteCardTacheManager.shotOffAutoProduction(order_id);
	//
	// this.json = "{result:0,message:'订单["+order_id+"]成功踢出!'}";
	// return JSON_MESSAGE;
	// }

	public String getOrderVisible(String order_id) {
		OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
		// 订单处理类型
		String refundDealType = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest()
				.getRefund_deal_type();

		// 退单状态
		String refundStatus = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest()
				.getRefund_status();

		String orderMode = orderExt.getOrder_model();

		String flowTraceId = orderExt.getFlow_trace_id();

		// 质检环节订单都设为可见
		if (EcsOrderConsts.DIC_ORDER_NODE_F.equals(flowTraceId)) {
			return EcsOrderConsts.VISIBLE_STATUS_0;
		}

		if (EcsOrderConsts.OPER_MODE_DL.equals(orderMode)) {
			return EcsOrderConsts.VISIBLE_STATUS_0;
		}

		// 退单驳回
		if (EcsOrderConsts.REFUND_STATUS_00.equals(refundStatus))
			return EcsOrderConsts.VISIBLE_STATUS_0;

		// 退单类型，非正常订单
		if (EcsOrderConsts.REFUND_DEAL_TYPE_02.equals(refundDealType)) {
			return EcsOrderConsts.VISIBLE_STATUS_1;
		}
		// 回访，且与总部交互
		if (EcsOrderConsts.DIC_ORDER_NODE_J.equals(orderExt.getFlow_trace_id())
				&& EcsOrderConsts.SEND_ZB_1.equals(orderExt.getSend_zb())) {
			return EcsOrderConsts.VISIBLE_STATUS_1;
		}

		// 发货，且与WMS交互
		if (EcsOrderConsts.DIC_ORDER_NODE_H.equals(orderExt.getFlow_trace_id())
				&& EcsOrderConsts.OPER_MODE_ZD.equals(orderExt.getOrder_model())) {
			return EcsOrderConsts.VISIBLE_STATUS_1;
		}

		// 写卡，且与WMS交互
		if (EcsOrderConsts.DIC_ORDER_NODE_X.equals(orderExt.getFlow_trace_id())
				&& EcsOrderConsts.OPER_MODE_ZD.equals(orderExt.getOrder_model())) {
			return EcsOrderConsts.VISIBLE_STATUS_1;
		}

		// 开户，订单与总户交互、且订单异常类型（ABNORMAL_TYPE）为“非伪自动化异常”，则设置订单不可见。
		if (EcsOrderConsts.DIC_ORDER_NODE_D.equals(orderExt.getFlow_trace_id())
				&& EcsOrderConsts.SEND_ZB_1.equals(orderExt.getSend_zb())
				&& !EcsOrderConsts.ORDER_ABNORMAL_TYPE_2.equals(orderExt.getAbnormal_type())) {
			return EcsOrderConsts.VISIBLE_STATUS_1;
		}

		// 预拣货：订单与WMS交互，则设置订单不可见
		if (EcsOrderConsts.DIC_ORDER_NODE_C.equals(orderExt.getFlow_trace_id())
				&& EcsOrderConsts.OPER_MODE_ZD.equals(orderExt.getOrder_model())) {
			return EcsOrderConsts.VISIBLE_STATUS_1;
		}

		// 自动化订单，在预拣货、开户、写卡都不可见 (正常单)
		if (EcsOrderConsts.OPER_MODE_ZD.equals(orderMode)
				&& EcsOrderConsts.ORDER_ABNORMAL_TYPE_0.equals(orderExt.getException_type())
				&& (EcsOrderConsts.DIC_ORDER_NODE_C.equals(flowTraceId)
						|| EcsOrderConsts.DIC_ORDER_NODE_D.equals(flowTraceId)
						|| EcsOrderConsts.DIC_ORDER_NODE_X.equals(flowTraceId))) {
			return EcsOrderConsts.VISIBLE_STATUS_1;
		}
		return EcsOrderConsts.VISIBLE_STATUS_0;
	}

	public String orderHandle() {
		// TODO 订单处理
		return "order_handle";
	}

	/**
	 * @作者 ZX
	 * @创建日期 2014-10-23 订单锁定保存
	 * @return
	 */
	public String order_lock() {
		try {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			String visible_status = orderTree.getOrderExtBusiRequest().getVisible_status();
			String abnoraml_type = orderTree.getOrderExtBusiRequest().getAbnormal_type();
			// 不可见订单，如果是伪自动化、自动化异常，可以锁定；
			boolean abnoramlFlag = ordFlowManager.queryExceptionLogsList(order_id).size() > 0;
			if (EcsOrderConsts.VISIBLE_STATUS_0.equals(visible_status) || abnoramlFlag) {
				String tacheCode = null;
				if (EcsOrdManager.QUERY_TYPE_BSS_PARALLEL.equals(query_type)) {
					tacheCode = "Y2";
				}
				ecsOrdManager.order_lock(order_id, tacheCode);
				json = "{result:0,message:'成功'}";
			} else {
				json = "{result:1,message:'不可见的订单不能被锁定'}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = "{result:1,message:'失败'}";
		}
		return JSON_MESSAGE;
	}

	/**
	 * @作者 ZX
	 * @创建日期 外呼环节锁定信息新增校验
	 */
	public String order_lock_showCallOut() {
		try {
			AdminUser user = ManagerUtils.getAdminUser();
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			String visible_status = orderTree.getOrderExtBusiRequest().getVisible_status();
			String abnoraml_type = orderTree.getOrderExtBusiRequest().getAbnormal_type();
			List<OrderLockBusiRequest> orderLockBusiRequestsList = orderTree.getOrderLockBusiRequests();
			String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			boolean c = this.ecsOrdManager.getLockShowCallOut(order_id);
			if (c && !"1".equals(user.getUserid())) {
				json = "{result:3,message:'该订单已被其他人员锁定，请刷新页面'}";
				return JSON_MESSAGE;
			}

			// 不可见订单，如果是伪自动化、自动化异常，可以锁定；
			boolean abnoramlFlag = ordFlowManager.queryExceptionLogsList(order_id).size() > 0;
			if (EcsOrderConsts.VISIBLE_STATUS_0.equals(visible_status) || abnoramlFlag) {
				String tacheCode = null;
				if (EcsOrdManager.QUERY_TYPE_BSS_PARALLEL.equals(query_type)) {
					tacheCode = "Y2";
				}
				ecsOrdManager.order_lock_showCallout(order_id, tacheCode);
				json = "{result:0,message:'成功'}";
			} else {
				json = "{result:1,message:'不可见的订单不能被锁定'}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = "{result:1,message:'失败'}";
		}
		return JSON_MESSAGE;
	}

	/**
	 * 查询预警信息
	 * 
	 * @return
	 */
	public String order_warning() {

		this.json = ecsOrdManager.getWarning(order_ids);

		return JSON_MESSAGE;
	}

	public String reissue_goods_add() {
		if (is_his_order != null && EcsOrderConsts.IS_ORDER_HIS_YES.equals(is_his_order)) {// 历史单

			OrderDeliveryItemBusiRequest orderDeliveryItemBusiRequest = new OrderDeliveryItemBusiRequest();
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTreeHis(order_id);
			OrderDeliveryBusiRequest orderDeliveryBusiRequest = new OrderDeliveryBusiRequest();
			List<OrderDeliveryBusiRequest> list = orderTree.getOrderDeliveryBusiRequests();

			for (OrderDeliveryBusiRequest de : list) {
				if (EcsOrderConsts.DELIVERY_TYPE_NORMAL.equals(de.getDelivery_type())) {// 肯定有一条记录是正常发货的
					orderDeliveryBusiRequest = de;
					break;
				}
			}
			orderDeliveryItemBusiRequest
					.setDelivery_id(orderDeliveryBusiRequest != null ? orderDeliveryBusiRequest.getDelivery_id() : "");
			orderDeliveryItemBusiRequest.setName(reissue_info);
			Integer num = 1;
			if (reissue_num != null && !reissue_num.equals("")) {
				num = Integer.parseInt(reissue_num);
			}
			orderDeliveryItemBusiRequest.setNum(num);
			orderDeliveryItemBusiRequest.setOrder_id(order_id);
			orderDeliveryItemBusiRequest.setItemtype(OrderStatus.DELIVERY_ITEM_TYPE_3);
			OrderDeliveryItemBusiRequest items = iOrderSupplyManager.addDeliveryItem(orderDeliveryItemBusiRequest,
					true);
			List<OrderDeliveryItemBusiRequest> respList = new ArrayList<OrderDeliveryItemBusiRequest>();
			respList.add(items);
			json = JSONArray.toJSONString(respList);

		} else {
			DeliveryItemAddReq req = new DeliveryItemAddReq();
			OrderDeliveryItemBusiRequest orderDeliveryItemBusiRequest = new OrderDeliveryItemBusiRequest();
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			List<OrderDeliveryBusiRequest> list = orderTree.getOrderDeliveryBusiRequests();
			OrderDeliveryBusiRequest orderDeliveryBusiRequest = new OrderDeliveryBusiRequest();
			for (OrderDeliveryBusiRequest de : list) {
				if (EcsOrderConsts.DELIVERY_TYPE_NORMAL.equals(de.getDelivery_type())) {// 肯定有一条记录是正常发货的
					orderDeliveryBusiRequest = de;
					break;
				}
			}
			orderDeliveryItemBusiRequest
					.setDelivery_id(orderDeliveryBusiRequest != null ? orderDeliveryBusiRequest.getDelivery_id() : "");
			orderDeliveryItemBusiRequest.setName(reissue_info);
			Integer num = 1;
			if (reissue_num != null && !reissue_num.equals("")) {
				num = Integer.parseInt(reissue_num);
			}
			orderDeliveryItemBusiRequest.setNum(num);
			orderDeliveryItemBusiRequest.setOrder_id(order_id);
			orderDeliveryItemBusiRequest.setItemtype(OrderStatus.DELIVERY_ITEM_TYPE_3);
			req.setDeliveryItem(orderDeliveryItemBusiRequest);
			List<OrderDeliveryItemBusiRequest> respList = new ArrayList<OrderDeliveryItemBusiRequest>();
			DeliveryItemAddResp resp = orderServices.addDeliveryItem(req);
			OrderDeliveryItemBusiRequest orderDelivery = resp.getDeliveryItem();
			respList.add(orderDelivery);
			json = JSONArray.toJSONString(respList);
		}
		return JSON_MESSAGE;
	}

	public String reissue_goods_del() {
		try {
			if (is_his_order != null && EcsOrderConsts.IS_ORDER_HIS_YES.equals(is_his_order)) {// 历史单
				iOrderSupplyManager.delDeliveryItemHis(reissue_id);
			} else {
				DeliveryItemDelReq req = new DeliveryItemDelReq();
				req.setItem_id(reissue_id);
				orderServices.delDeliveryItem(req);
			}

			// ecsOrdManager.reissue_goods_del(reissue_id);
			json = "{result:0, message:'成功'}";
		} catch (Exception e) {
			e.printStackTrace();
			json = "{result:1, message:'失败'}";
		}

		return JSON_MESSAGE;
	}

	/**
	 * 质检稽核
	 * 
	 * @return
	 */
	public String quality_audit() {
		// TODO 待实现
		// this.orderTree =
		// CommonDataFactory.getInstance().getOrderTree(order_id);
		// List<AttrInstLoadResp> attrInstLoadResps =
		// AttrBusiInstTools.validateOrderAttrInstsForPage(order_id,ConstsCore.ATTR_ACTION_lOAD);
		DeliveryItemsQueryReq req = new DeliveryItemsQueryReq();
		req.setOrder_id(order_id);
		deliveryItemsQueryResp = orderServices.queryDeliveryItems(req);

		logiCompanyList = ecsOrdManager.logi_company(order_id);

		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);

		OrderDeliveryBusiRequest deliverBusiReq = null;
		List<OrderDeliveryBusiRequest> orderDeliveryBusiRequest = orderTree.getOrderDeliveryBusiRequests();
		if (orderDeliveryBusiRequest != null && orderDeliveryBusiRequest.size() > 0) {
			for (OrderDeliveryBusiRequest deli : orderDeliveryBusiRequest) {
				if (EcsOrderConsts.LOGIS_NORMAL.equals(deli.getDelivery_type())) {
					deliverBusiReq = deli;
					break;
				}
			}
			if (null == deliverBusiReq) {// 没有正常发货的记录(整个业务过程应该控制到有且仅有一条正常发货记录)
				deliverBusiReq = new OrderDeliveryBusiRequest();
				try {
					BeanUtils.copyProperties(deliverBusiReq, orderDeliveryBusiRequest.get(0));// 从其他发货记录copy
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				deliverBusiReq.setDelivery_id(iOrderSupplyManager.getSequences("S_ES_DELIVERY"));
				deliverBusiReq.setDelivery_type(EcsOrderConsts.LOGIS_NORMAL);// 创建一条正常发货的记录
				deliverBusiReq.setShip_status(OrderStatus.DELIVERY_SHIP_SATUS_NOT);// 未发货、未打印
				deliverBusiReq.setLogi_no("");// 物流单号
				deliverBusiReq.setReceipt_no("");// 回单号
				deliverBusiReq.setSign_status("");// 签收状态
				deliverBusiReq.setReceipt_status("");// 回单签收状态
				deliverBusiReq.setDb_action(ConstsCore.DB_ACTION_INSERT);
				deliverBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				deliverBusiReq.store();
			}
			this.delivery = deliverBusiReq;
			provinc_code = deliverBusiReq.getProvince_id();
			city_code = deliverBusiReq.getCity_id();
			// district_id = deliverBusiReq.getRegion_id();
			// logi_no = deliverBusiReq.getLogi_no();
			provList = ordFlowManager.getProvList();
			Long prov_code = provinc_code;
			if (prov_code == null) {
				prov_code = Long.parseLong(((Map) provList.get(0)).get("code").toString());
			}
			cityList = ordFlowManager.getCityList(prov_code);
			Long city_id = city_code;
			if (city_id == null) {
				city_id = Long.parseLong(((Map) this.cityList.get(0)).get("code").toString());
			}
			districtList = ordFlowManager.getDistrictList(city_id);
		}
		// if(orderDeliveryBusiRequest.size()>0){
		// this.logi_receiver =
		// orderDeliveryBusiRequest.get(0).getLogi_receiver();
		// this.logi_receiver_phone =
		// orderDeliveryBusiRequest.get(0).getLogi_receiver_phone();
		// }

		// 获取价格信息
		getPrice(orderTree);

		// 获取副卡信息
		this.setOrderPhoneInfoFukaList(orderTree.getOrderPhoneInfoFukaBusiRequests());

		// 附加产品信息
		this.setSubProductList(CommonDataFactory.getInstance().getSubProdPackList(orderTree));

		// 礼品信息
		this.setGiftInfoList(orderTree.getGiftInfoBusiRequests());
		this.giftInfoSize = giftInfoList.size();
		return "quality_audit";
	}

	public String quality_audit_more() {
		this.quality_audit();
		this.orderItemExtvtlBusiRequests = CommonDataFactory.getInstance().getOrderTree(order_id)
				.getOrderItemExtvtlBusiRequests();
		if (orderItemExtvtlBusiRequests != null && orderItemExtvtlBusiRequests.size() > 0) {
			for (int i = 0; i < orderItemExtvtlBusiRequests.size(); i++) {
				String goods_type = orderItemExtvtlBusiRequests.get(i).getGoods_type();
				String goods_type_name = SpecUtils.getGoodsTypeNameById(goods_type);
				orderItemExtvtlBusiRequests.get(i).setGoods_type_name(goods_type_name);
			}
		}
		return "quality_audit_more";
	}

	/**
	 * 历史订单详情
	 * 
	 * @return
	 */
	public String his_order_detail() {
		// TODO 待实现
		// this.orderTree =
		// CommonDataFactory.getInstance().getOrderTree(order_id);
		// List<AttrInstLoadResp> attrInstLoadResps =
		// AttrBusiInstTools.validateOrderAttrInstsForPage(order_id,ConstsCore.ATTR_ACTION_lOAD);
		/*
		 * DeliveryItemsQueryReq req = new DeliveryItemsQueryReq();
		 * req.setOrder_id(order_id); deliveryItemsQueryResp =
		 * IOrderSupplyManager.queryDeliveryItems(order_id,true);
		 */

		logiCompanyList = ecsOrdManager.logi_company(order_id);

		orderTree = CommonDataFactory.getInstance().getOrderTreeHis(order_id);

		List<OrderDeliveryBusiRequest> orderDeliveryBusiRequestList = orderTree.getOrderDeliveryBusiRequests();
		OrderDeliveryBusiRequest deliverBusiReq = new OrderDeliveryBusiRequest();
		if (orderDeliveryBusiRequestList != null && orderDeliveryBusiRequestList.size() > 0) {

			for (OrderDeliveryBusiRequest de : orderDeliveryBusiRequestList) {
				if (EcsOrderConsts.DELIVERY_TYPE_NORMAL.equals(de.getDelivery_type())) {// 肯定有一条记录是正常发货的
					deliverBusiReq = de;
					break;
				}
			}
			provinc_code = deliverBusiReq.getProvince_id();
			city_code = deliverBusiReq.getCity_id();
			district_id = deliverBusiReq.getRegion_id();
			provList = ordFlowManager.getProvList();
			Long prov_code = provinc_code;
			if (prov_code == null) {
				prov_code = Long.parseLong(((Map) provList.get(0)).get("code").toString());
			}
			cityList = ordFlowManager.getCityList(prov_code);
			Long city_id = city_code;
			if (city_id == null) {
				city_id = Long.parseLong(((Map) this.cityList.get(0)).get("code").toString());
			}
			districtList = ordFlowManager.getDistrictList(city_id);
		}
		if (orderDeliveryBusiRequestList.size() > 0) {
			this.logi_receiver = deliverBusiReq.getLogi_receiver();
			this.logi_receiver_phone = deliverBusiReq.getLogi_receiver_phone();
		}
		// 获取价格
		getPrice(orderTree);
		// 获取副卡信息
		this.setOrderPhoneInfoFukaList(orderTree.getOrderPhoneInfoFukaBusiRequests());

		// 附加产品信息
		this.setSubProductList(CommonDataFactory.getInstance().getSubProdPackList(orderTree));

		return "ord_detail_his";
	}

	// 实物稽核检查订单
	public String check_order_id() {
		String msg = "无此单号！";
		try {
			GetOrdByBoxIdFromWMSReq boxReq = new GetOrdByBoxIdFromWMSReq();
			GetOrdByBoxIdFromWMSResp boxResp = new GetOrdByBoxIdFromWMSResp();
			try {
				boxReq.setBoxCode(boxCode);
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				boxResp = client.execute(boxReq, GetOrdByBoxIdFromWMSResp.class);
				if (null != boxResp && !StringUtils.isEmpty(boxResp.getOrderId())) {
					order_id = boxResp.getOrderId();

					if (order_id == null) {
						this.json = "{result:1, message:'料箱号[" + boxCode + "]未获取到订单号!'}";
						return JSON_MESSAGE;
					}
					if (!ecsOrdManager.getOrderCtn(order_id)) {
						HttpServletRequest request = ServletActionContext.getRequest();
						String myServerPort = request.getLocalPort() + "";
						if (StringUtil.equals(myServerPort, "8086"))
							myServerPort = "8087";
						else if (StringUtil.equals(myServerPort, "8087"))
							myServerPort = "8086";
						this.json = "{result:1, message:'料箱号[" + boxCode + "]获取到订单号[" + order_id
								+ "],该订单应该在【http://10.123.180.120:" + myServerPort + "/mgWeb】地址生产!'}";
						return JSON_MESSAGE;
					}
					orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
					if (orderTree == null) {
						this.json = "{result:1, message:'料箱号[" + boxCode + "]获取到订单号[" + order_id + "],该订单在订单系统不存在!'}";
						return JSON_MESSAGE;
					}
					OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();

					List<OrderLockBusiRequest> orderLockRequest = orderTree.getOrderLockBusiRequests();
					OrderLockBusiRequest orderLockBusiRequest = null;
					for (int i = 0; i < orderLockRequest.size(); i++) {
						if (orderExtBusiRequest.getFlow_trace_id().equals(orderLockRequest.get(i).getTache_code())) {
							orderLockBusiRequest = orderLockRequest.get(i);
						}
					}

					if (!EcsOrderConsts.OPER_MODE_ZD.equals(orderExtBusiRequest.getOrder_model())) {
						this.json = "{result:1, message:'料箱号[" + boxCode + "]获取到订单号[" + order_id + "],该订单不是自动化生产模式!'}";
						return JSON_MESSAGE;
					}
					if (!EcsOrderConsts.DIC_ORDER_NODE_F.equals(orderExtBusiRequest.getFlow_trace_id())) {
						this.json = "{result:1, message:'料箱号[" + boxCode + "]获取到订单号[" + order_id + "],该订单不在稽核环节!'}";
						return JSON_MESSAGE;
					}
					if (EcsOrderConsts.ORDER_ABNORMAL_TYPE_2.equals(orderExtBusiRequest.getException_type())
							|| EcsOrderConsts.ORDER_ABNORMAL_TYPE_2.equals(orderExtBusiRequest.getException_type())) {
						this.json = "{result:1, message:'料箱号[" + boxCode + "]获取到订单号[" + order_id + "],该订单为自动化处理异常订单!'}";
						return JSON_MESSAGE;
					}
					if (EcsOrderConsts.REFUND_DEAL_TYPE_02.equals(orderExtBusiRequest.getRefund_deal_type())) {
						this.json = "{result:1, message:'料箱号[" + boxCode + "]获取到订单号[" + order_id + "],该订单已退单或退单申请!'}";
						return JSON_MESSAGE;
					}
					if (EcsOrderConsts.DIC_ORDER_NODE_F.equals(orderExtBusiRequest.getFlow_trace_id())) {
						// 质检稽核环节之后(发货、回单、归档)不能再做实物稽核
						// orderExtBusiRequest.setLock_status(EcsOrderConsts.LOCK_STATUS);
						orderLockBusiRequest.setLock_status(EcsOrderConsts.LOCK_STATUS);
						try {
							// orderExtBusiRequest.setLock_time(DateUtil.getTime2());
							orderLockBusiRequest.setLock_time(DateUtil.getTime2());
						} catch (FrameException e) {
							e.printStackTrace();
						}
						// orderExtBusiRequest.setLock_user_id(ManagerUtils.getUserId());
						// orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						// orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						// orderExtBusiRequest.store();

						orderLockBusiRequest.setLock_user_id(ManagerUtils.getUserId());
						orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						// 锁单信息增加工号池和锁单结束时间
						orderLockBusiRequest
								.setPool_id(workPoolManager.getLockTimeByUserId(ManagerUtils.getUserId()).getPool_id());
						orderLockBusiRequest.setLock_end_time(
								workPoolManager.getLockTimeByUserId(ManagerUtils.getUserId()).getLock_end_time());
						orderLockBusiRequest.store();

					} else {
						orderTree = null;
						msg = "订单[" + order_id + "]已通过质检稽核";
					}
				}
			} catch (Exception f) {
				f.printStackTrace();
			}
		} catch (Exception e) {
			// TODO: handle exception
			json = "{result:1, message:'无此单号！'}";
		}
		if (orderTree == null) {
			json = "{result:1, message:'" + msg + "'}";
		} else {
			json = "{result:0, message:'成功！', 'order_id':'" + order_id + "'}";
		}
		return JSON_MESSAGE;
	}

	// 实物稽核检查订单，测试专用
	public String check_order_id_test() {
		boxCode = boxCodeTest;
		return check_order_id();
	}

	/**
	 * 实物稽核
	 * 
	 * @return
	 */
	public String entity_quality_audit() {
		if (order_id != null) {
			trace_id = "entity_quality";

			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			List<OrderDeliveryBusiRequest> orderDeliveryBusiRequest = orderTree.getOrderDeliveryBusiRequests();
			OrderDeliveryBusiRequest deliverBusiReq = null;
			if (null != orderDeliveryBusiRequest && orderDeliveryBusiRequest.size() > 0) {// 前面业务保证有正常发货记录
				for (OrderDeliveryBusiRequest deli : orderDeliveryBusiRequest) {
					if (EcsOrderConsts.LOGIS_NORMAL.equals(deli.getDelivery_type())) {
						deliverBusiReq = deli;
					}
				}
				if (null == deliverBusiReq) {// 没有正常发货的记录(整个业务过程应该控制到有且仅有一条正常发货记录)
					deliverBusiReq = new OrderDeliveryBusiRequest();
					try {
						BeanUtils.copyProperties(deliverBusiReq, orderDeliveryBusiRequest.get(0));// 从其他发货记录copy
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					deliverBusiReq.setDelivery_id(iOrderSupplyManager.getSequences("S_ES_DELIVERY"));
					deliverBusiReq.setDelivery_type(EcsOrderConsts.LOGIS_NORMAL);// 创建一条正常发货的记录
					deliverBusiReq.setShip_status(OrderStatus.DELIVERY_SHIP_SATUS_NOT);// 未发货、未打印
					deliverBusiReq.setLogi_no("");// 物流单号
					deliverBusiReq.setReceipt_no("");// 回单号
					deliverBusiReq.setSign_status("");// 签收状态
					deliverBusiReq.setReceipt_status("");// 回单签收状态
					deliverBusiReq.setDb_action(ConstsCore.DB_ACTION_INSERT);
					deliverBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					deliverBusiReq.store();
				}
				this.delivery = deliverBusiReq;
			}
			if (autoOrderTree == null)
				autoOrderTree = new AutoOrderTree();
			autoOrderTree.setOrderTree(orderTree);
			String goods_package = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "plan_title");
			autoOrderTree.setGoods_package(goods_package);
			String shipping_type = orderTree.getOrderBusiRequest().getShipping_type();
			String shipping_type_name = "";
			this.listShippingType();
			for (Map map : shipping_type_list) {
				if (shipping_type.equals(map.get("pkey"))) {
					shipping_type_name = map.get("pname").toString();
				}
			}
			autoOrderTree.setShipping_type(shipping_type);
			autoOrderTree.setShipping_type_name(shipping_type_name);
			String plat_type = orderTree.getOrderExtBusiRequest().getPlat_type();
			String order_from_c = "";
			this.listPlatType();
			for (Map map : plat_type_list) {
				if (plat_type.equals(map.get("pkey"))) {
					order_from_c = map.get("pname").toString();
				}
			}
			autoOrderTree.setOrder_from_c(order_from_c);
			List<Map> send_type = getConsts(EcsOrderConsts.DIC_ORDER_NODE);
			if (send_type != null && shipping_type != null) {
				for (Map m : send_type) {
					String pkey = (String) m.get("pkey");
					if (shipping_type.equals(pkey)) {
						autoOrderTree.setShipping_type((String) m.get("pname"));
						break;
					}
				}
			}
			logiCompanyList = ecsOrdManager.logi_company(order_id);

			entityInfoList = new ArrayList<EntityInfoVO>();
			if (orderTree != null) {
				List<AttrGiftInfoBusiRequest> giftInfoList = orderTree.getGiftInfoBusiRequests();
				List<Goods> goodsList = CommonDataFactory.getInstance().getEntityProducts(order_id);
				String terminal_num = "";
				List<OrderItemProdBusiRequest> itemsprod = orderTree.getOrderItemBusiRequests().get(0)
						.getOrderItemProdBusiRequests();
				for (OrderItemProdBusiRequest prod : itemsprod) {
					if (prod.getProd_spec_type_code().equals(EcsOrderConsts.PRODUCT_TYPE_CODE_TERMINAL)) { // 手机终端
						terminal_num = prod.getOrderItemProdExtBusiRequest().getTerminal_num();
					}
				}
				if (giftInfoList != null) {
					for (AttrGiftInfoBusiRequest giftInfo : giftInfoList) { // 取实物礼品信息
						if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(giftInfo.getIs_virtual()))
							continue;
						EntityInfoVO entityInfo = new EntityInfoVO();
						String type_id = giftInfo.getGoods_type();
						GoodsTypeGetReq req = new GoodsTypeGetReq();
						req.setType_id(type_id);
						GoodsTypeGetResp goodsTypeGetResp = orderServices.getGoodsTypeName(req);
						GoodsType goodsType = goodsTypeGetResp.getGoodsType();
						String goodsTypeName = goodsType.getName();
						String entity_type = goodsTypeName;
						entityInfo.setEntity_type(entity_type);
						entityInfo.setSku(giftInfo.getSku_id());
						entityInfo.setEntity_name(giftInfo.getGoods_name());
						entityInfo.setSerial_number(giftInfo.getSeries_num());
						entityInfo.setType("1");
						entityInfoList.add(entityInfo);
					}
				}
				String order_type = orderTree.getOrderBusiRequest().getOrder_type();
				String order_from = orderTree.getOrderExtBusiRequest().getOrder_from();
				if (EcsOrderConsts.ORDER_TYPE_09.equals(order_type)
						|| order_from.equals(EcsOrderConsts.ORDER_FROM_10061)) {
					List<OrderItemExtvtlBusiRequest> orderItemExtvtls = orderTree.getOrderItemExtvtlBusiRequests();
					for (OrderItemExtvtlBusiRequest orderItemExtvtl : orderItemExtvtls) {
						EntityInfoVO entityInfo = new EntityInfoVO();
						entityInfo.setEntity_type(orderItemExtvtl.getGoods_id());
						entityInfo.setEntity_name(orderItemExtvtl.getGoods_name());
						entityInfo.setSerial_number(orderItemExtvtl.getResources_code());
						entityInfo.setEntity_type("2");
						entityInfo.setSku(orderItemExtvtl.getSku());
						entityInfoList.add(entityInfo);
					}
				} else if (goodsList != null) {
					for (Goods goods : goodsList) { // 取终端信息
						String product_type_id = CommonDataFactory.getInstance()
								.getTypeIdByTypeCode(goods.getType_code());
						String is_physical_product = CommonDataFactory.getInstance()
								.getDictCodeValue(StypeConsts.IS_PHYSICAL, product_type_id);
						EntityInfoVO entityInfo = new EntityInfoVO();
						String type_id = goods.getType_id();
						GoodsTypeGetReq req = new GoodsTypeGetReq();
						req.setType_id(type_id);
						GoodsTypeGetResp goodsTypeGetResp = orderServices.getGoodsTypeName(req);
						GoodsType goodsType = goodsTypeGetResp.getGoodsType();
						String goodsTypeName = goodsType.getName();
						String entity_type = goodsTypeName;
						entityInfo.setEntity_type(entity_type);
						entityInfo.setSku(goods.getSku());
						entityInfo.setEntity_name(goods.getName());
						entityInfo.setSerial_number(terminal_num);
						entityInfo.setType("2");
						entityInfoList.add(entityInfo);
					}
				}

				String sim_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SIM_TYPE);
				String sim_card_sku = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
						AttrConsts.SIM_CARD_SKU);
				String iccid = "";
				String prod_type = "";
				String entity_name = "";
				List<OrderItemBusiRequest> orderItemBusiRequests = CommonDataFactory.getInstance()
						.getOrderTree(order_id).getOrderItemBusiRequests();
				OrderItemBusiRequest orderItemBusiRequest = orderItemBusiRequests.get(0);
				List<OrderItemProdBusiRequest> prods = orderItemBusiRequest.getOrderItemProdBusiRequests();
				for (OrderItemProdBusiRequest prod : prods) {
					if (EcsOrderConsts.PRODUCT_TYPE_CODE_OFFER.equals(prod.getProd_spec_type_code())
							|| EcsOrderConsts.PRODUCT_TYPE_CODE_CONTRACT.equals(prod.getProd_spec_type_code())) {
						entity_name += prod.getName() + ";";// 拼接合约计划和套餐货品的名称，作为号卡的名称
					}
				}
				if (EcsOrderConsts.SIM_TYPE_CK.equals(sim_type)) {
					List<OrderItemProdBusiRequest> prodBusis = CommonDataFactory.getInstance().getOrderTree(order_id)
							.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests();
					for (OrderItemProdBusiRequest prodBusi : prodBusis) {
						OrderItemProdExtBusiRequest prodExt = prodBusi.getOrderItemProdExtBusiRequest();
						if (EcsOrderConsts.PRODUCT_TYPE_CODE_OFFER.equals(prodBusi.getProd_spec_type_code())) {
							iccid = prodExt.getICCID();
							prod_type = "成卡";
							entity_name = prodExt.getCert_card_name();
						}
					}
				} else {
					iccid = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
					prod_type = "白卡";
				}
				if (StringUtils.isNotBlank(iccid)) {
					EntityInfoVO entityInfo = new EntityInfoVO();
					entityInfo.setEntity_type(prod_type);
					entityInfo.setSku(sim_card_sku);
					entityInfo.setEntity_name(entity_name);
					entityInfo.setSerial_number(iccid);
					entityInfo.setType("3");
					entityInfoList.add(entityInfo);
				}

			}
		}
		return "entity_quality_audit";
	}

	/**
	 * 总商爬虫查询总商工号列表
	 * 
	 * @return
	 */
	public String getHeadquartersMallStaff() {
		this.webpage = ecsOrdManager.queryHeadquartersMallStaff(this.getPage(), this.getPageSize(), staffName);
		crawlerProcList = this.ecsOrdManager.qryCrawlerProcList(null);
		return "headquartersMall";
	}

	/**
	 * 总商爬虫查询总商工号详情
	 * 
	 * @return
	 */
	public String getHeadquartersMallStaffDetail() {
		if (!StringUtil.isEmpty(headquartersMall.getStaff_code())) {
			this.headquartersMall = ecsOrdManager.queryHeadquartersMallStaffDetail(headquartersMall.getStaff_code());
		}
		return "headquartersMallDetail";
	}

	/**
	 * 总商爬虫修改总商工号
	 * 
	 * @return
	 */
	public String editHeadquartersMallStaff() {
		ecsOrdManager.editHeadquartersMallStaff(headquartersMall);
		return "headquartersMall";
	}

	/**
	 * 总商爬虫删除总商工号
	 * 
	 * @return
	 */
	public String deleteHeadquartersMallStaff() {
		ecsOrdManager.deleteHeadquartersMallStaff(headquartersMall);
		return "headquartersMall";
	}

	/**
	 * 总商爬虫保存添加总商工号
	 * 
	 * @return
	 */
	public String saveHeadquartersMallStaff() {
		ecsOrdManager.addHeadquartersMallStaff(headquartersMall);
		return getHeadquartersMallStaff();
	}

	/**
	 * 总商爬虫添加总商工号
	 * 
	 * @return
	 */
	public String addHeadquartersMallStaff() {
		return "addHeadquartersMall";
	}

	/**
	 * 总商爬虫发送验证码
	 * 
	 * @return
	 */
	public String sendHeadquartersMallMessage() {
		if (StringUtil.isEmpty(staffName)) {
			this.json = "{'result':1,'msg':'总商登录工号为空','return_data':{}}";
			return JSON_MESSAGE;
		}
		if (StringUtil.isEmpty(staffPassword)) {
			this.json = "{'result':1,'msg':'总商登录密码为空','return_data':{}}";
			return JSON_MESSAGE;
		}
		if (StringUtil.isEmpty(sendType)) {
			this.json = "{'result':1,'msg':'请选择接受验证码方式','return_data':{}}";
			return JSON_MESSAGE;
		}
		CrawlerCodeReq req = new CrawlerCodeReq();
		req.setUserName(staffName);
		req.setPassWord(staffPassword);
		req.setSendType(sendType);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		CrawlerResp resp = client.execute(req, CrawlerResp.class);
		logger.info(resp.getResp_code());
		logger.info(resp.getResp_msg());

		this.json = "{'result':" + resp.getResp_code() + ",'msg':'" + resp.getResp_msg() + "','return_data':{}}";
		return JSON_MESSAGE;

	}

	/**
	 * 总商爬虫登录方法
	 * 
	 * @return
	 */
	public String loginHeadquartersMallMessage() {
		if (StringUtil.isEmpty(staffName)) {
			this.json = "{'result':1,'msg':'总商登录工号为空','return_data':{}}";
			return JSON_MESSAGE;
		}
		if (StringUtil.isEmpty(staffPassword)) {
			this.json = "{'result':1,'msg':'总商登录密码为空','return_data':{}}";
			return JSON_MESSAGE;
		}
		if (StringUtil.isEmpty(ip)) {
			this.json = "{'result':1,'msg':'总商登录进程IP,port为空','return_data':{}}";
			return JSON_MESSAGE;
		}
		if (StringUtil.isEmpty(messageCode) && StringUtil.isEmpty(cookie)) {
			this.json = "{'result':1,'msg':'总商登录验证码为空','return_data':{}}";
			return JSON_MESSAGE;
		}
		CrawlerReq req = new CrawlerReq();
		req.setUserName(staffName);
		req.setPassWord(staffPassword);
		req.setValidateCode(messageCode);
		req.setCookie(cookie);
		String[] ipPortArray = ip.split(",");
		// 如果有多个抓单进行时，第一个使用验证码登陆，后续使用cookie登陆
		String first = ipPortArray[0];
		// ZteClient client =
		// ClientFactory.getZteRopClient("http://"+first+"/router",
		// "wssmall_ecsord", "123456");
		ZteClient client = ClientFactory.getZteDubboClient("ECS");
		CrawlerResp resp = client.execute(req, CrawlerResp.class);
		String result = "";
		if ("0".equals(resp.getResp_code())) {// 标记为登录成功
			ecsOrdManager.editHeadquartersMallStaffStatus(staffName);
			result = first + "登陆成功;";
		} else {
			logger.info("=====OrderAutoAction loginHeadquartersMallMessage 爬虫登录接口调用出错【errorMsg:" + resp.getError_msg()
					+ "】");
			this.json = "{'result':1,'msg':'" + first + "登陆失败" + "','return_data':{}}";
			return JSON_MESSAGE;
		}
		for (int i = 1; i < ipPortArray.length; i++) {
			String next = ipPortArray[i];
			// client = ClientFactory.getZteRopClient("http://"+next+"/router",
			// "wssmall_ecsord", "123456");
			client = ClientFactory.getZteDubboClient("ECS");
			req.setCookie(resp.getCookie());
			CrawlerResp resp1 = client.execute(req, CrawlerResp.class);
			if ("0".equals(resp1.getResp_code()))
				result += next + "登陆成功;";
			else {
				this.json = "{'result':1,'msg':'" + next + "登陆失败" + "','return_data':{}}";
				return JSON_MESSAGE;
			}
		}
		this.json = "{'result':0,'msg':'" + result + "','return_data':{}}";

		/*
		 * CrawlerReq req = new CrawlerReq(); req.setUserName(staffName);
		 * req.setPassWord(staffPassword); req.setValidateCode(messageCode);
		 * req.setCookie(cookie); List<Map<String,String>> procList =
		 * this.ecsOrdManager.getCrawlerProc(); if(null==procList||procList.size()<=0){
		 * this.json = "{'result':1,'msg':'没有可用的爬虫进程,请检查爬虫配置表','return_data':{}}";
		 * }else{ String msg=""; //如果有多个抓单进行时，第一个使用验证码登陆，后续使用cookie登陆 Map<String,String>
		 * param = procList.get(0); String ip= param.get("ip"); String port=
		 * param.get("port"); ZteClient client =
		 * ClientFactory.getZteRopClient("http://"+ip+":"+port+"/router",
		 * "wssmall_ecsord", "123456"); CrawlerResp resp = client.execute(req,
		 * CrawlerResp.class); String result="";
		 * if("0".equals(resp.getResp_code())){//标记为登录成功
		 * ecsOrdManager.editHeadquartersMallStaffStatus(staffName);
		 * result=ip+":"+port+"登陆成功;"; }else{ logger.info(
		 * "=====OrderAutoAction loginHeadquartersMallMessage 爬虫登录接口调用出错【errorMsg:"
		 * +resp.getError_msg()+"】"); this.json =
		 * "{'result':1,'msg':'"+ip+":"+port+"登陆失败"+"','return_data':{}}"; return
		 * JSON_MESSAGE; } for(int i=1;i<procList.size();i++){ Map<String,String>
		 * paramMap = procList.get(i); ip= paramMap.get("ip"); port=
		 * paramMap.get("port"); client =
		 * ClientFactory.getZteRopClient("http://"+ip+":"+port+"/router",
		 * "wssmall_ecsord", "123456"); req.setCookie(resp.getCookie()); CrawlerResp
		 * resp1 = client.execute(req, CrawlerResp.class);
		 * if("0".equals(resp1.getResp_code())){ result+=ip+":"+port+"登陆成功;"; }else{
		 * result+=ip+":"+port+"登陆失败"+resp1.getError_msg()+";"; } } this.json =
		 * "{'result':0,'msg':'"+result+"','return_data':{}}"; }
		 */
		return JSON_MESSAGE;
	}

	/**
	 * 获取爬虫配置
	 * 
	 * @return
	 */
	public String queryCrawlerProperties() {
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		GetCrawlerPropertiesReq req = new GetCrawlerPropertiesReq();
		GetCrawlerPropertiesResp resp = client.execute(req, GetCrawlerPropertiesResp.class);
		crawlerProperties = resp.getCrawlerPropertes();
		return "crawlerProperties";
	}

	/**
	 * 保存爬虫配置
	 * 
	 * @return
	 */
	public String saveCrawlerProperties() {
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		ZteResponse resp = client.execute(crawlerProperties, ZteResponse.class);

		return "crawlerProperties";
	}

	/**
	 * 实物稽核录入串号
	 * 
	 * @return
	 */
	public String entity_quality_lrch() {

		List<EntityInfoVO> entityInfo = ecsOrdManager.entityInfoList(order_id, serial_num);
		json = JSONArray.toJSONString(entityInfo);
		return JSON_MESSAGE;
	}

	/**
	 * z 动态地市
	 * 
	 * @return
	 */
	public String changeProvinc() {

		List<Map> ls = ordFlowManager.getCityList(provinc_code);
		json = JSONArray.toJSONString(ls);
		return JSON_MESSAGE;
	}

	/**
	 * 动态县区
	 * 
	 * @return
	 */
	public String changeCity() {

		List<Map> ls = ordFlowManager.getDistrictList(city_code);
		json = JSONArray.toJSONString(ls);
		return JSON_MESSAGE;
	}

	/**
	 * 物流公司
	 * 
	 * @return
	 */
	public String logi_company_regions() {
		try {
			logiCompanyRegionsList = ecsOrdManager.logi_company_regions(logi_post_id);
			json = JSONArray.toJSONString(logiCompanyRegionsList);
		} catch (Exception e) {
			e.printStackTrace();
			json = "{result:1, message:'失败'}";
		}
		return JSON_MESSAGE;
	}

	public String editSave() {
		// //校验前，先将校验值清空处理
		// OrderTreeBusiRequest orderTree=
		// CommonDataFactory.getInstance().getOrderTree(order_id);
		// ThreadContextHolder.setHttpRequest(getRequest());
		// String msg ="";
		// // PageLoad属性验证方法校验
		// List<AttrInstLoadResp> attrInstLoadResps =
		// AttrBusiInstTools.validateOrderAttrInstsForPage(order_id,ConstsCore.ATTR_ACTION_lOAD);
		// if(attrInstLoadResps.size()>0)
		// msg= "调用load方法，返回错误校验信息";
		// attrInstLoadResps
		// =AttrBusiInstTools.validateOrderAttrInstsForPage(order_id,
		// ConstsCore.ATTR_ACTION_UPDATE);
		// if(attrInstLoadResps.size()>0)
		// msg= "调用update方法，返回错误校验信息";
		// // 待所有验证通过，处理逻辑获取界面修改脏数据,保存入库,增量数据入库处理
		// List<AttrInstBusiRequest> pageAttrInstBusiRequests =
		// AttrBusiInstTools.getPageDirtyOrderAttrInst(order_id,orderTree);
		// for ( AttrInstBusiRequest pageAttrInstBusiRequest
		// :pageAttrInstBusiRequests) {
		// pageAttrInstBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		// pageAttrInstBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		// pageAttrInstBusiRequest.store(); //属性数据入库
		// }
		return "";
	}

	/**
	 * 物流单号
	 * 
	 * @return
	 */
	public String logi_no() {

		json = EcsOrdManager.getUniqueRandomNum();

		return JSON_MESSAGE;
	}

	/**
	 * 回单
	 * 
	 * @return
	 */
	public String ord_receipt() {
		// order_id="ZBWO161001605150000044";
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		dic_material_retrieve = ecsOrdManager.dic_material_retrieve();
		material_retrieve = orderTree.getOrderExtBusiRequest().getMaterial_retrieve();

		ordReceipt = new OrdReceipt();
		ordReceipt.setLiang_agree(
				orderExtBusiRequest.getLiang_agree() == null ? "" : orderExtBusiRequest.getLiang_agree().toString());
		ordReceipt.setArchive_num(orderExtBusiRequest.getArchive_num());
		ordReceipt.setNet_certi(
				orderExtBusiRequest.getNet_certi() == null ? "" : orderExtBusiRequest.getNet_certi().toString());
		ordReceipt.setAccept_agree(
				orderExtBusiRequest.getAccept_agree() == null ? "" : orderExtBusiRequest.getAccept_agree().toString());
		String orderFrom = orderExtBusiRequest.getOrder_from();

		if (StringUtils.equals(orderFrom, "10078")) {
			String CountyId = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderAdslBusiRequest().get(0)
					.getCounty_id();
			List countyList = ecsOrdManager.getDcSqlByDcName("DC_ORDER_BUSICTY_NEW");
			if (!StringUtils.isEmpty(CountyId)) {
				if (countyList != null) {
					for (int i = 0; i < countyList.size(); i++) {
						String value = Const.getStrValue((Map) countyList.get(i), "value");
						String value_desc = Const.getStrValue((Map) countyList.get(i), "value_desc");
						if (StringUtil.equals(value, CountyId)) {
							county_name = value_desc;
							break;
						}
					}
				}
			}
			ordReceipt.setCounty_name(county_name);
		}

		return "ord_receipt";
	}

	/**
	 * 确认回单
	 * 
	 * @return
	 */
	public String affirm_receipt() {
		try {
			ecsOrdManager.affirm_receipt(order_id, ordReceipt);
			json = "{result:0,msg:'执行成功'}";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json = "{result:1,msg:'执行失败'}";
		}

		return JSON_MESSAGE;
	}

	/**
	 * 保存每个环节的处理信息
	 * 
	 * @return
	 */
	public String insertDealInfo() {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderHandleLogsReq req = new OrderHandleLogsReq();
		String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
		String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		req.setOrder_id(order_id);
		req.setFlow_id(flow_id);
		req.setFlow_trace_id(flowTraceId);
		req.setComments(dealDesc);
		req.setHandler_type(Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
		req.setType_code(dealType);
		this.ordFlowManager.insertOrderHandLog(req);
		return "";
	}

	public String unLock() {
		json = ecsOrdManager.unLock(order_id);
		return this.JSON_MESSAGE;
	}

	/**
	 * 历史单详情
	 * 
	 * @return
	 */
	public String his_details() {

		DeliveryItemsQueryReq req = new DeliveryItemsQueryReq();
		req.setOrder_id(order_id);
		deliveryItemsQueryResp = orderServices.queryDeliveryItems(req);

		logiCompanyList = ecsOrdManager.logi_company(order_id);

		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);

		List<OrderDeliveryBusiRequest> orderDeliveryBusiRequest = orderTree.getOrderDeliveryBusiRequests();
		if (orderDeliveryBusiRequest != null && orderDeliveryBusiRequest.size() > 0) {
			OrderDeliveryBusiRequest deliverBusiReq = orderTree.getOrderDeliveryBusiRequests().get(0);

			provinc_code = deliverBusiReq.getProvince_id();
			city_code = deliverBusiReq.getCity_id();
			district_id = deliverBusiReq.getRegion_id();
			provList = ordFlowManager.getProvList();
			Long prov_code = provinc_code;
			if (prov_code == null) {
				prov_code = Long.parseLong(((Map) provList.get(0)).get("code").toString());
			}
			cityList = ordFlowManager.getCityList(prov_code);
			Long city_id = city_code;
			if (city_id == null) {
				city_id = Long.parseLong(((Map) this.cityList.get(0)).get("code").toString());
			}
			districtList = ordFlowManager.getDistrictList(city_id);
		}
		if (orderDeliveryBusiRequest.size() > 0) {
			this.logi_receiver = orderDeliveryBusiRequest.get(0).getLogi_receiver();
			this.logi_receiver_phone = orderDeliveryBusiRequest.get(0).getLogi_receiver_phone();
		}

		return "his_details";
	}

	public String orderArchive() {
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);

		// sgf
		String out_tid = orderTree.getOrderExtBusiRequest().getOut_tid();
		List<Map<String, String>> esOrderQueue = this.ordFlowManager.getServiceCodeByObjectId(out_tid);
		Map<String, String> developParams = this.orderExtManager.changeDevelopmetInfo(order_id, orderTree,
				esOrderQueue);

		this.development_point_code = developParams.get("development_point_code");
		this.development_point_name = developParams.get("development_point_name");
		this.developmentCode = developParams.get("developmentCode");
		this.developmentName = developParams.get("developmentName");

		goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		if (EcsOrderConsts.GOODS_TYPE_DKD.equals(goods_type)) {
			p_code = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10054,
					SpecConsts.P_CODE);// 合约编码
			p_name = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10054,
					SpecConsts.P_NAME);// 合约名称
			city = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10054, SpecConsts.CITY);// 地市
			packge_limit = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10054,
					SpecConsts.PACKAGE_LIMIT);// 合约期
			net_speed = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10053,
					SpecConsts.NET_SPEED);// 速率
			access_type = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10054,
					SpecConsts.ACCESS_TYPE);// 接入方式
			plan_title = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10053,
					SpecConsts.BSS_NAME);// 套餐名称
			ess_code = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10053,
					SpecConsts.BSS_CODE);// 套餐编码
			terminal_type = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10055,
					SpecConsts.TERMINAL_TYPE);// 终端类型
		}
		return "order_archive";
	}

	public String queryOrdAgrtImg() {
		ord_agrt_img = ecsOrdManager.queryOrdAgrtImg(order_id);
		return "agreement_img";
	}

	/* 订单解锁列表 */
	public String LockOrderList() {
		if ("inp".equals(lockDealType)) {
			return "unlockOrderByInput";
		}
		this.listOrderFrom();
		this.listFlowTrace();
		this.listOrderTypeList();
		this.listRegions();
		if (!"yes".equals(first_load)) {
			this.webpage = this.ecsOrdManager.getLockOrdList(this.getPage(), this.getPageSize(), params);
			totalCount = webpage.getTotalCount();
		}
		return "lockOrderList";
	}

	/* 订单解锁 */
	public String unLockOrder() {
		try {
			if ("inp".equals(lockDealType)) {// 解锁手动输入的订单
				/**
				 * 校验输入订单号是否可解锁
				 */
				String err_msg = "";
				String out_tids = params.getOut_tid();
				if (StringUtils.isEmpty(out_tids)) {
					err_msg = "输入订单号为空";
					// }else{
					// String [] out_tid_array = out_tids.split(",");
					// for(int i=0;i<out_tid_array.length;i++){
					// String order_id =
					// orderExtManager.getOrderIdByOutTid(out_tid_array[i]);
					// if(StringUtils.isEmpty(order_id)){
					// err_msg = out_tid_array[i]+"不存在或已归档";
					// break;
					// }
					// OrderTreeBusiRequest orderTree =
					// CommonDataFactory.getInstance().getOrderTree(order_id);
					// if(null==orderTree){
					// err_msg = out_tid_array[i]+"不存在或已归档";
					// break;
					// }
					// OrderExtBusiRequest orderExtBusiRequest =
					// orderTree.getOrderExtBusiRequest();
					// if(null==orderExtBusiRequest){
					// err_msg = out_tid_array[i]+"订单数据错误";
					// break;
					// }
					// if(!(EcsOrderConsts.VISIBLE_STATUS_0.equals(orderExtBusiRequest.getVisible_status())||
					// (EcsOrderConsts.VISIBLE_STATUS_1.equals(orderExtBusiRequest.getVisible_status())&&
					// (EcsOrderConsts.ORDER_ABNORMAL_TYPE_2.equals(orderExtBusiRequest.getAbnormal_type())||
					// EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(orderExtBusiRequest.getAbnormal_type()))))){//不可见不允许解锁(判断逻辑保持与解锁订单列表查询逻辑致)
					// err_msg = out_tid_array[i]+"不允许解锁";
					// break;
					// }
					// }
				}
				if (!StringUtils.isEmpty(err_msg)) {
					this.json = "{result:1,message:'" + err_msg + "'}";
					return this.JSON_MESSAGE;
				}
				/**
				 * 校验输入订单号是否可解锁
				 */
				lockOrderIdStr = "";
				List list = this.ecsOrdManager.getLockOrderIdsByOuttids(this.params);
				for (int i = 0; i < list.size(); i++) {
					Map map = (Map) list.get(i);
					if (map != null) {
						String order_id = (String) map.get("order_id");
						if (!StringUtils.isEmpty(order_id)) {
							this.lockOrderIdStr += order_id + ",";
						}
					}
				}
			}

			if ("all".equals(lockDealType)) {// 解除所有
				lockOrderIdStr = "";
				List list = this.ecsOrdManager.getLockOrderIds(this.params);
				for (int i = 0; i < list.size(); i++) {
					Map map = (Map) list.get(i);
					if (map != null) {
						String order_id = (String) map.get("order_id");
						if (!StringUtils.isEmpty(order_id)) {
							this.lockOrderIdStr += order_id + ",";
						}
					}
				}
			}
			if (!StringUtils.isEmpty(this.lockOrderIdStr)) {
				INetCache cache1 = (INetCache) com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
				String[] str = this.lockOrderIdStr.split(",");
				for (int j = 0; j < str.length; j++) {
					String order_id = str[j];
					OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);// CommonDataFactory.getInstance().getOrderTree(order_id);
					OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
					/*
					 * orderExt.setLock_user_id(ConstsCore.NULL_VAL);
					 * orderExt.setLock_user_name(ConstsCore.NULL_VAL);
					 * orderExt.setLock_status(EcsOrderConsts.UNLOCK_STATUS);
					 */
					String trace_id = orderExt.getFlow_trace_id();
					/*
					 * String is_work_custom = orderExt.getIs_work_custom(); String instance_id="";
					 * IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
					 * if("1".equals(is_work_custom)){ String sql_instance=
					 * " select instance_id from es_work_custom_node_ins  t where is_curr_step='1' and is_done='0' and order_id='"
					 * +order_id+"'" ; instance_id = baseDaoSupport.queryForString(sql_instance);
					 * if(null != instance_id){ trace_id = instance_id; } }
					 */
					String key = new StringBuffer().append("order_lock_").append(trace_id).append("_").append(order_id)
							.toString();
					key = MD5Util.MD5(key);
					String def = String.valueOf(cache1.get(ZjEcsOrderConsts.ORDER_LOCK_NAMESPACE, key));
					List<OrderLockBusiRequest> orderLockBusiRequestList = orderTree.getOrderLockBusiRequests();
					for (int i = 0; i < orderLockBusiRequestList.size(); i++) {
						OrderLockBusiRequest orderLockBusiRequest = orderLockBusiRequestList.get(i);
						String aa = orderLockBusiRequest.getTache_code();
						if (trace_id.equals(orderLockBusiRequest.getTache_code())) {
							cache.delete(ZjEcsOrderConsts.ORDER_LOCK_NAMESPACE, key);
							/*
							 * if("1".equals(is_work_custom)){ String sql_instance=
							 * "update es_work_custom_node_ins set is_lock_flag = '0',curr_user_id='',curr_user_name='' where order_id='"
							 * +order_id+"' and instance_id='"+trace_id+"'";
							 * baseDaoSupport.update(sql_instance, null); }
							 */
							orderLockBusiRequest.setLock_user_id(ConstsCore.NULL_VAL);
							orderLockBusiRequest.setLock_user_name(ConstsCore.NULL_VAL);
							orderLockBusiRequest.setLock_status(EcsOrderConsts.UNLOCK_STATUS);
							orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							// 解锁订单释放工号池和锁单结束时间
							orderLockBusiRequest.setPool_id(ConstsCore.NULL_VAL);
							orderLockBusiRequest.setLock_end_time(ConstsCore.NULL_VAL);
							orderLockBusiRequest.store();
							IOrderFlowManager myOrderFlowManager;
							final AdminUser orderBatchUser = ManagerUtils.getAdminUser();
							OrderHandleLogsReq logReq = new OrderHandleLogsReq();
							logReq.setOrder_id(order_id);
							logReq.setFlow_id(orderExt.getFlow_id());
							logReq.setFlow_trace_id(orderExt.getFlow_trace_id());
							logReq.setComments("订单解锁");
							logReq.setOp_id(orderBatchUser.getUserid());
							logReq.setOp_name(orderBatchUser.getUsername());
							logReq.setHandler_type("unlock");
							myOrderFlowManager = SpringContextHolder.getBean("ordFlowManager");
							myOrderFlowManager.insertOrderHandLog(logReq);
						}
					}

					/*
					 * orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					 * orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE); orderExt.store();
					 */
				}
			}
			this.json = "{result:0,message:'操作成功'}";
		} catch (Exception e) {
			e.printStackTrace();
			this.json = "{result:1,message:'" + e.getMessage() + "'}";
		}
		return this.JSON_MESSAGE;
	}

	/* 订单分配列表 */
	public String AllotOrderList() {
		this.listOrderTypeList();
		this.listFlowTrace();
		this.listOrderFrom();
		this.listRegions();
		// 照片上传状态
		order_vplan_list = ecsOrdManager.getDcSqlByDcName("photo_up_status");
		if (!"yes".equals(first_load)) {
			this.webpage = this.ecsOrdManager.getAllotOrdList(this.getPage(), this.getPageSize(), params);
		}
		return "allotOrderList";
	}

	/* 订单分配列表 */
	public String NewAllotOrderList() {
		this.listOrderTypeList();
		this.listFlowTrace();
		this.listOrderFrom();
		this.listRegions();
		if (params == null) {
			params = new OrderQueryParams();
		}
		countyList = ordWorkManager.getIntentCountyList(params.getOrder_city_code());
		// 照片上传状态
		order_vplan_list = ecsOrdManager.getDcSqlByDcName("photo_up_status");
		if (!"yes".equals(first_load)) {
			this.webpage = this.ecsOrdManager.newGetAllotOrdList(this.getPage(), this.getPageSize(), params);
		}
		return "newAllotOrderList";
	}

	public String queryUser() {

		if (!StringUtil.isEmpty(lock_user_id)) {
			String Lock_user_id = lock_user_id;
			OrderQueryParams p = new OrderQueryParams();
			p.setLock_user_id(Lock_user_id);
			params = p;
			userparams = lock_user_id;
		}
		this.webpage = this.ecsOrdManager.getUserList(this.getPage(), this.getPageSize(), userparams, allotType,
				lockOrderIdStr, allot_county_id);
		return "allotUserList";

	}

	// 订单分配操作 Gl wjq
	public String newQueryUser() throws Exception {
		String city = ecsOrdManager.getOrderCityOrder(lockOrderIdStr);
		countyList = ordWorkManager.getCountyList(city);// 用户权限是营业县分
		allot_county_name = ordWorkManager.getCountyName(county_code);
		is_work_custom = this.isWorkCustom(lockOrderIdStr);
		String ordFrom = this.orderFrom(lockOrderIdStr);
		// 判断是否是意向单
		String is_yxd = this.isYxd(lockOrderIdStr);
		if ("Y".equals(is_yxd)) {
			// 判断意向单是否是自定义流程
			if (("1".equals(is_work_custom))) {
				if ("allot".equals(allotType)) {
					allotType = "yxd";
				} else if ("return".equals(allotType)) {
					allotType = "yxdreturn";
				}
			}
		} else {
			// 判断正式单是否是自定义流程
			is_work_custom = this.isFormalWorkCustom(lockOrderIdStr);
			if (("1".equals(is_work_custom))) {
				if ("allot".equals(allotType)) {
					allotType = "yxd";
				} else if ("return".equals(allotType)) {
					allotType = "yxdreturn";
				}
			}
		}
		return "newAllotUserList";

	}

	// 判断正式单是否是自定义流程
	public String isFormalWorkCustom(String order_id) {
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		String is_work_sql = "  select t.is_work_custom from es_order_ext t where t.source_from='"
				+ ManagerUtils.getSourceFrom() + "' and t.order_id='" + order_id + "' ";
		return baseDaoSupport.queryForString(is_work_sql);
	}

	public String orderFrom(String order_id) {
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		String order_from = "select a.source_system_type from es_order_intent a where a.source_from='"
				+ ManagerUtils.getSourceFrom() + "' and a.order_id='" + order_id + "'";
		return baseDaoSupport.queryForString(order_from);
	}

	// 判断意向单是否是自定义流程
	public String isWorkCustom(String order_id) {
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		String is_work_sql = " select t.is_work_custom from es_order_intent t where t.source_from='"
				+ ManagerUtils.getSourceFrom() + "' and t.order_id='" + order_id + "'";
		return baseDaoSupport.queryForString(is_work_sql);
	}

	// 订单分配团队 GL
	public String newQueryGroup() {
		this.webpage = this.ecsOrdManager.getUserGroupList(this.getPage(), this.getPageSize(), this.team_level,
				this.team_id, this.team_name);
		return "newAllotUserGroupList";
	}

	// 订单分配人员 GL
	public String newQueryPersonnel() throws Exception {
		// lock_user_id 锁定人ID
		if (!StringUtil.isEmpty(lock_user_id)) {
			String Lock_user_id = lock_user_id;
			OrderQueryParams p = new OrderQueryParams();
			p.setLock_user_id(Lock_user_id);
			params = p;
			userparams = lock_user_id;
		}
		// countyList = ordWorkManager.getCountyList("");// 用户权限是营业县分
		if (!StringUtil.isEmpty(county_code)) {
			allot_county_name = ordWorkManager.getCountyName(county_code);
		}

		if (!StringUtils.isEmpty(allot_county_name) && allot_county_name.startsWith("undefined")) {// undefined,
																									// 温岭市
			allot_county_name = allot_county_name.replace("undefined", "").replace(",", "").trim();
		}
		if (!StringUtils.isEmpty(allot_county_id) && allot_county_id.startsWith("undefined")) {// undefined,
																								// ZJ331081
			allot_county_id = allot_county_id.replace("undefined", "").replace(",", "").trim();
		}
		logger.info("allot_county_id:" + allot_county_id);
		if (!StringUtil.isEmpty(userparams)) {
			try {
				userparams = URLDecoder.decode(userparams, "UTF-8");
				// userparams = new String (userparams.getBytes("iso8859-1"),"UTF-8");
			} catch (Exception e) {
				userparams = null;
			}
		}
		String is_yxd = this.isYxd(lockOrderIdStr);
		if ("Y".equals(is_yxd)) {
			// 判断意向单是否是自定义流程
			is_work_custom = this.isWorkCustom(lockOrderIdStr);
			if (("1".equals(is_work_custom))) {
				if ("allot".equals(allotType)) {
					allotType = "yxd";
				} else if ("return".equals(allotType)) {
					allotType = "yxdreturn";
				}
			}
		} else {
			// 判断正式单是否是自定义流程
			is_work_custom = this.isFormalWorkCustom(lockOrderIdStr);
			if (("1".equals(is_work_custom))) {
				if ("allot".equals(allotType)) {
					allotType = "yxd";
				} else if ("return".equals(allotType)) {
					allotType = "yxdreturn";
				}
			}
		}
		allot_county_name = ordWorkManager.getCountyName(county_code);
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		String dealLevelSql = "select deal_level from es_work_custom_node_ins where order_id='" + this.lockOrderIdStr
				+ "' and is_curr_step=1 and source_from='" + ManagerUtils.getSourceFrom() + "'";
		this.deal_level = baseDaoSupport.queryForString(dealLevelSql);
		this.webpage = this.ecsOrdManager.newGetUserList(this.getPage(), this.getPageSize(), userparams, allotType,
				lockOrderIdStr, allot_county_id, county_code);
		return "newAllotUserPersonnelList";
	}

	public String queryCounty() {
		this.webpage = this.ecsOrdManager.getCountyList(this.getPage(), this.getPageSize(), county_name);
		List list = webpage.getResult();
		return "allotCountyList";
	}

	public String updateLock() {
		try {
			if (!StringUtils.isEmpty(this.lockOrderIdStr)) {
				IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
				String[] str = this.lockOrderIdStr.split(",");
				logger.info("多个OrderID——lockOrderIdStr:" + lockOrderIdStr);
				logger.info("userid:" + userid);
				logger.info("realname:" + realname);
				for (int j = 0; j < str.length; j++) {
					String action = "";
					String remark = remarks;
					String order_id = str[j];
					this.order_id = order_id;
					String countyCode3 = "";
					String countyCode6 = "";
					String county_sql = "";
					String is_yxd = this.isYxd(order_id);
					// 判断是否意向单
					if ("N".equals(is_yxd)) {
						if ("1".equals(this.isFormalWorkCustom(order_id))) {
							allotType = "yxd";
						}
						// 不是意向单走这里
						order_lock();
						OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);// CommonDataFactory.getInstance().getOrderTree(order_id);
						OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();

						// 获取工作流环节ID
						String trace_id = orderExt.getFlow_trace_id();
						//
						List<OrderLockBusiRequest> orderLockBusiRequestList = orderTree.getOrderLockBusiRequests();
						for (int i = 0; i < orderLockBusiRequestList.size(); i++) {

							OrderLockBusiRequest orderLockBusiRequest = orderLockBusiRequestList.get(i);

							if (trace_id.equals(orderLockBusiRequest.getTache_code())) {
								orderLockBusiRequest.setLock_user_id(userid);
								orderLockBusiRequest.setLock_user_name(URLDecoder.decode(realname, "UTF-8"));
								orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
								orderLockBusiRequest.setDealer_type(col5);
								orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
								orderLockBusiRequest.store();
							}
						}
						if (!StringUtils.isEmpty(allotType) && "return".equals(allotType)) {
							CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
									new String[] { "allot_status" }, new String[] { "2" });
						} else if (!StringUtils.isEmpty(allotType) && "yxd".equals(allotType)) { // 正式单,自定义流程,更新数据 add
																									// wjq
							CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
									new String[] { "allot_status" }, new String[] { "0" });
							String update_formal_sql = "";
							county_sql = "select t.other_field_value,t.field_value from es_dc_public_dict_relation t where t.stype = 'county' and t.field_value = (select a.county_id from es_work_custom_dealer a left join es_work_custom_node_ins esw on esw.cfg_id = a.cfg_id and a.node_index = esw.node_index where esw.order_id = '"
									+ order_id + "' and esw.is_curr_step = 1 and a.dealer_code = '" + userid
									+ "' and a.dealer_type = '" + col5 + "')  and t.source_from = '"
									+ ManagerUtils.getSourceFrom() + "'";
							List<Map> list = baseDaoSupport.queryForList(county_sql, null);
							if (list.size() == 1) {
								countyCode3 = (String) list.get(0).get("other_field_value");
								countyCode6 = (String) list.get(0).get("field_value");
							} else {
								countyCode3 = "";
							}
							action = "订单领取";
							remarks = "订单重新分配给:" + this.userid;
							if (!StringUtils.isEmpty(countyCode3) && countyCode3 != "") {
								String update_county_extvtl_sql = "update es_order_extvtl set district_code = '"
										+ countyCode6 + "' where order_id='" + order_id + "'";
								String update_county_zhwq_sql = "update es_order_zhwq_adsl set county_id = '"
										+ countyCode3 + "' where order_id='" + order_id + "'";
								baseDaoSupport.execute(update_county_extvtl_sql);
								baseDaoSupport.execute(update_county_zhwq_sql);
							}
							if ("person".equals(this.col5)) {
								update_formal_sql = " update es_order_lock set lock_user_id='" + userid
										+ "',lock_user_name='" + URLDecoder.decode(realname, "UTF-8")
										+ "',dealer_type='" + this.col5 + "' where order_id='" + order_id + "' ";
							} else {
								update_formal_sql = " update es_order_lock set lock_user_id='" + userid
										+ "',lock_user_name='" + URLDecoder.decode(realname, "UTF-8")
										+ "',dealer_type='" + this.col5 + "' where order_id='" + order_id + "' ";
								String sendSms = "select a.userid,a.phone_num,o.org_name from es_adminuser a left join es_organization o on a.org_id=o.org_code left join es_user_team_rel r on a.userid = r.userid left join es_user_team t on r.team_id = t.team_id where a.source_from='ECS' and t.team_id='"
										+ this.userid + "' and a.sms_receive='1' ";
								List<Map> gonghaoInfos = baseDaoSupport.queryForList(sendSms);
								this.sendMessage(gonghaoInfos, this.order_id);
							}
							String update_formal_work_custom_sql = "update es_work_custom_node_ins w set w.curr_user_id='"
									+ userid + "',w.curr_user_name='" + URLDecoder.decode(realname, "UTF-8")
									+ "',w.deal_level='" + this.permission_level + "' where w.order_id='" + order_id
									+ "' and w.is_curr_step='1'";

							baseDaoSupport.execute(update_formal_sql);
							baseDaoSupport.execute(update_formal_work_custom_sql);
						} else if (!StringUtils.isEmpty(allotType) && "yxdreturn".equals(allotType)) {
							CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
									new String[] { "allot_status" }, new String[] { "2" });
							String update_formal_sql = "";
							action = "订单回退";
							remarks = "订单回退给:" + this.userid;
							if ("person".equals(this.col5)) {
								update_formal_sql = " update es_order_lock set lock_user_id='" + userid
										+ "',lock_user_name='" + URLDecoder.decode(realname, "UTF-8")
										+ "',dealer_type='" + this.col5 + "' where order_id='" + order_id + "' ";
							} else {
								update_formal_sql = " update es_order_lock set lock_user_id='" + userid
										+ "',lock_user_name='" + URLDecoder.decode(realname, "UTF-8")
										+ "',dealer_type='" + this.col5 + "' where order_id='" + order_id + "' ";
								String sendSms = "select a.userid,a.phone_num,o.org_name from es_adminuser a left join es_organization o on a.org_id=o.org_code left join es_user_team_rel r on a.userid = r.userid left join es_user_team t on r.team_id = t.team_id where a.source_from='ECS' and t.team_id='"
										+ this.userid + "'";
								List<Map> gonghaoInfos = baseDaoSupport.queryForList(sendSms);
								this.sendMessage(gonghaoInfos, this.order_id);
							}
							String update_formal_work_custom_sql = "update es_work_custom_node_ins w set w.curr_user_id='"
									+ userid + "',w.curr_user_name='" + URLDecoder.decode(realname, "UTF-8")
									+ "',w.deal_level='" + this.permission_level + "' where w.order_id='" + order_id
									+ "' and w.is_curr_step='1'";

							baseDaoSupport.execute(update_formal_sql);
							baseDaoSupport.execute(update_formal_work_custom_sql);
						} else {
							action = "订单领取";
							CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
									new String[] { "allot_status" }, new String[] { "0" });
							county_sql = "SELECT distinct c.busicty_id FROM es_user_role a, es_role_auth b, es_role_data c where a.userid ='"
									+ userid
									+ "' and a.roleid = b.roleid and b.authid = c.id  and c.busicty_id is not null and a.source_from = '"
									+ ManagerUtils.getSourceFrom() + "' ";
							List<Map> list = baseDaoSupport.queryForList(county_sql, null);
							if (list.size() == 1) {
								countyCode3 = (String) list.get(0).get("busicty_id");
								countyCode3 = countyCode3.substring(0, 3);
								String county_sql2 = "select t.field_value from es_dc_public_dict_relation t where t.stype = 'county' and t.other_field_value = '"
										+ countyCode3 + "' and t.source_from = '" + ManagerUtils.getSourceFrom() + "'";
								countyCode6 = baseDaoSupport.queryForString(county_sql2);
							} else {
								countyCode3 = "";
							}
							if (!StringUtils.isEmpty(countyCode3) && countyCode3 != "") {

								String update_county_extvtl_sql = "update es_order_extvtl set district_code = '"
										+ countyCode6 + "' where order_id='" + order_id + "'";
								String update_county_zhwq_sql = "update es_order_zhwq_adsl set county_id = '"
										+ countyCode3 + "' where order_id='" + order_id + "'";
								baseDaoSupport.execute(update_county_extvtl_sql);
								baseDaoSupport.execute(update_county_zhwq_sql);
							}
						}
						// orderRollback(this.userid, this.order_id);

					} else {
						// 非空判断
						if ("team".equals(col5) || "org".equals(col5)) {
							updateLockGro();
							remarks = "订单重新分配给：" + java.net.URLDecoder.decode(this.realname, "UTF-8");
							action = "订单分配";
						} else if (org.apache.commons.lang.StringUtils.isNotBlank(allotType)
								&& "group".equals(allotType)) {
							// 团队分配
							updateLockGroup();
							remarks = "订单重新分配给：" + java.net.URLDecoder.decode(this.realname, "UTF-8");
							action = "订单分配";
						} else {

							String update_sql1 = "";
							is_work_custom = this.isWorkCustom(order_id);

							if (!StringUtils.isEmpty(allotType)
									&& ("return".equals(allotType) || "yxdreturn".equals(allotType))) {// 订单回退
								update_sql1 += ",allot_status='2' ";
								action = "订单回退";
								remarks = "默认回退到省中台" + URLDecoder.decode(remarks, "UTF-8");
								// 更新自定义流程
								/*
								 * ES_WORK_CUSTOM_NODE_INS pojo = new ES_WORK_CUSTOM_NODE_INS();
								 * pojo.setOrder_id(order_id); pojo.setIs_curr_step("1"); pojo.setIs_done("0");
								 * List<ES_WORK_CUSTOM_NODE_INS> insRet =
								 * this.workCustomCfgManager.qryInsList(pojo , null); if(insRet!=null &&
								 * insRet.size()>0) { for (int i = 0; i < insRet.size(); i++) {
								 * insRet.get(i).setCurr_user_id(userid);
								 * insRet.get(i).setCurr_user_name(URLDecoder.decode(realname, "UTF-8"));
								 * insRet.get(i).setIs_lock_flag("1"); }
								 * workCustomCfgManager.updateNodeInstances(insRet); }
								 */
							} else {
								update_sql1 += ",allot_status='0' ";
								remarks = "订单重新分配给：" + java.net.URLDecoder.decode(realname, "UTF-8");
								action = "订单分配";
								// 更新自定义流程 分配
								if ("1".equals(is_work_custom)) {
									county_sql = "select t.other_field_value,t.field_value from es_dc_public_dict_relation t where t.stype = 'county' and t.field_value = (select a.county_id from es_work_custom_dealer a left join es_work_custom_node_ins esw on esw.cfg_id = a.cfg_id and a.node_index = esw.node_index where esw.order_id = '"
											+ order_id + "' and esw.is_curr_step = 1 and a.dealer_code = '" + userid
											+ "' and a.dealer_type = '" + col5 + "')  and t.source_from = '"
											+ ManagerUtils.getSourceFrom() + "'";
									List<Map> list = baseDaoSupport.queryForList(county_sql, null);
									if (list.size() == 1) {
										countyCode3 = (String) list.get(0).get("other_field_value");
										countyCode6 = (String) list.get(0).get("field_value");
									} else {
										countyCode3 = "";
									}

								} else {
									county_sql = "SELECT distinct c.busicty_id FROM es_user_role a, es_role_auth b, es_role_data c where a.userid ='"
											+ userid
											+ "' and a.roleid = b.roleid and b.authid = c.id  and c.busicty_id is not null and a.source_from = '"
											+ ManagerUtils.getSourceFrom() + "' ";
									List<Map> list = baseDaoSupport.queryForList(county_sql, null);
									if (list.size() == 1) {
										countyCode3 = (String) list.get(0).get("busicty_id");
										countyCode3 = countyCode3.substring(0, 3);
										String county_sql2 = "select t.field_value from es_dc_public_dict_relation t where t.stype = 'county' and t.other_field_value = '"
												+ countyCode3 + "' and t.source_from = '" + ManagerUtils.getSourceFrom()
												+ "'";
										countyCode6 = baseDaoSupport.queryForString(county_sql2);
									} else {
										countyCode3 = "";
									}

								}
							}
							String update_sql = "";
							if (!StringUtils.isEmpty(countyCode3) && countyCode3 != "") {
								update_sql = " update es_order_intent set lock_id='" + userid + "',lock_name='"
										+ URLDecoder.decode(realname, "UTF-8") + "' " + update_sql1 + " , county_id='"
										+ countyCode3 + "',order_county_code = '" + countyCode6 + "' where order_id='"
										+ order_id + "' ";
							} else {
								update_sql = " update es_order_intent set lock_id='" + userid + "',lock_name='"
										+ URLDecoder.decode(realname, "UTF-8") + "' " + update_sql1
										+ "  where order_id='" + order_id + "' ";
							}

							String update_work_custom_sql = "update es_work_custom_node_ins w set w.curr_user_id='"
									+ userid + "',w.curr_user_name='" + URLDecoder.decode(realname, "UTF-8")
									+ "',w.deal_level='" + this.permission_level + "' where w.order_id='" + order_id
									+ "' and w.is_curr_step='1'";
							baseDaoSupport.execute(update_sql);
							baseDaoSupport.execute(update_work_custom_sql);

							OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
							if (orderTree != null) {
								order_lock();
								orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);// CommonDataFactory.getInstance().getOrderTree(order_id);
								OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();

								String trace_id = orderExt.getFlow_trace_id();
								List<OrderLockBusiRequest> orderLockBusiRequestList = orderTree
										.getOrderLockBusiRequests();
								for (int i = 0; i < orderLockBusiRequestList.size(); i++) {
									OrderLockBusiRequest orderLockBusiRequest = orderLockBusiRequestList.get(i);
									if (trace_id.equals(orderLockBusiRequest.getTache_code())) {
										orderLockBusiRequest.setLock_user_id(userid);
										orderLockBusiRequest.setLock_user_name(URLDecoder.decode(realname, "UTF-8"));
										orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
										orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
										orderLockBusiRequest.store();
									}
								}
								if (!StringUtils.isEmpty(allotType)
										&& ("return".equals(allotType) || "yxdreturn".equals(allotType))) {
									CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
											new String[] { "allot_status" }, new String[] { "2" });
								}
							}
							logger.info("YES ");
							// 订单回退到省中台以短信方式通知处理人员
							// orderRollback(this.userid, this.order_id);
						}
					}
					if ("Y".equals(is_yxd)) {
						AdminUser user = ManagerUtils.getAdminUser();
						Map<String, Object> map = new HashMap();
						map.put("order_id", order_id);
						map.put("remark", remarks);
						map.put("source_from", "ECS");
						map.put("create_time", "sysdate");
						map.put("op_id", user.getUserid());
						map.put("op_name", user.getRealname());
						map.put("type_code", "intent");
						map.put("action", action);
						baseDaoSupport.insert("es_intent_handle_logs", map);
					}
					/*
					 * orderExt.setLock_user_id(userid); orderExt.setLock_user_name(realname);
					 * orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					 * orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					 * 
					 * orderExt.store();
					 */

				}
				orderRollback(this.userid, this.lockOrderIdStr);
				OrderTreeBusiRequest orderTree = null;
				// if (!StringUtils.isEmpty(remarks)) {
				orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				if (orderTree != null) {
					String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
					OrderHandleLogsReq req = new OrderHandleLogsReq();
					String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
					AdminUser user = ManagerUtils.getAdminUser();
					req.setOp_id(user.getUserid());
					req.setOp_name(user.getRealname());
					req.setOrder_id(order_id);
					req.setFlow_id(flow_id);
					req.setFlow_trace_id(trace_id);
					req.setComments(remarks);
					req.setHandler_type(Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
					req.setType_code(allotType);
					this.ordFlowManager.insertOrderHandLog(req);
				}
				// }
			}
			this.json = "{result:0,message:'操作成功'}";
			return JSON_MESSAGE;
		} catch (Exception e) {
			e.printStackTrace();
			this.json = "{result:1,message:'" + e.getMessage() + "'}";
			return JSON_MESSAGE;
		}
	}

	/**
	 * 查询是否是意向单
	 * 
	 * @param order_id
	 * @return
	 */
	public String isYxd(String order_id) {
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		String is_yxd_sql = " select decode((select order_id from es_order_intent where source_from = '"
				+ ManagerUtils.getSourceFrom()
				+ "'  and (is_online_order <> 1 or is_online_order is null) and order_id='" + order_id
				+ "' ),null,'N','Y') is_yxd from dual ";
		return baseDaoSupport.queryForString(is_yxd_sql);
	}

	/**
	 * 自定义团队分配
	 */
	public void updateLockGro() {
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		AdminUser user = ManagerUtils.getAdminUser();
		String realName = "";
		String county_sql = "select t.other_field_value,t.field_value from es_dc_public_dict_relation t where t.stype = 'county' and t.field_value = (select a.county_id from es_work_custom_dealer a left join es_work_custom_node_ins esw on esw.cfg_id = a.cfg_id and a.node_index = esw.node_index where esw.order_id = '"
				+ order_id + "' and esw.is_curr_step = 1 and a.dealer_code = '" + userid + "' and a.dealer_type = '"
				+ col5 + "')  and t.source_from = '" + ManagerUtils.getSourceFrom() + "'";
		List<Map> map = baseDaoSupport.queryForLists(county_sql);
		String countyCode3 = "";
		String countyCode6 = "";
		if (map.size() == 1) {
			countyCode3 = (String) map.get(0).get("other_field_value");
			countyCode6 = (String) map.get(0).get("field_value");
		}

		String es_order_intent_sql = "";
		try {
			realName = java.net.URLDecoder.decode(this.realname, "UTF-8"); // 进行解码，中文乱码
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (!StringUtils.isEmpty(countyCode3) && countyCode3 != "") {
			es_order_intent_sql = "update  es_order_intent t set t.lock_id='',t.lock_name='',t.allot_status='0', t.county_id='"
					+ countyCode3 + "', t.order_county_code = '" + countyCode6 + "' where t.order_id='" + this.order_id
					+ "'";
		} else {
			es_order_intent_sql = "update  es_order_intent t set t.lock_id='',t.lock_name='',t.allot_status='0' where t.order_id='"
					+ this.order_id + "'";
		}
		String es_work_custom_node_ins_sql = "update es_work_custom_node_ins w set w.dealer_code='" + this.userid
				+ "',w.dealer_name='" + realName + "',w.is_lock_flag='0',w.curr_user_id='" + user.getUserid()
				+ "',w.curr_user_name='" + user.getRealname() + "',w.deal_level = '" + this.permission_level
				+ "' where w.order_id='" + this.order_id + "' and w.is_curr_step='1' ";
		if ("org".equals(col5)) {
			userid = "1";
		}
		String sendSms = "select a.userid,a.phone_num,o.org_name from es_adminuser a left join es_organization o on a.org_id=o.org_code left join es_user_team_rel r on a.userid = r.userid left join es_user_team t on r.team_id = t.team_id where a.source_from='ECS' and t.team_id='"
				+ this.userid + "'";

		// 意向单更改锁定人
		baseDaoSupport.execute(es_order_intent_sql);
		// 沉淀团队分配数据
		baseDaoSupport.execute(es_work_custom_node_ins_sql);
		// 查询短信发送数据
		List<Map> gonghaoInfos = baseDaoSupport.queryForList(sendSms);
		// 发送短信
		this.sendMessage(gonghaoInfos, this.order_id);
	}

	/**
	 * 团队分配
	 */
	public void updateLockGroup() {
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		AdminUser user = ManagerUtils.getAdminUser();
		String es_order_intent_sql = "update  es_order_intent t set t.lock_id='',t.lock_name='',t.allot_status='0' where t.order_id='"
				+ this.order_id + "'";
		String es_work_custom_node_ins_sql = "update es_work_custom_node_ins w set w.dealer_code='" + this.team_id
				+ "',w.dealer_name='" + this.team_name + "',w.is_lock_flag='0',w.curr_user_id='" + user.getUserid()
				+ "',w.curr_user_name='" + user.getRealname() + "' where w.order_id='" + this.order_id
				+ "' and w.is_curr_step='1'";
		String sendSms = "select a.userid,a.phone_num,o.org_name from es_adminuser a left join es_organization o on a.org_id=o.org_code left join es_user_team_rel r on a.userid = r.userid left join es_user_team t on r.team_id = t.team_id where a.source_from='ECS' and t.team_id='"
				+ this.team_id + "'";

		// 意向单更改锁定人
		baseDaoSupport.execute(es_order_intent_sql);
		// 沉淀团队分配数据
		baseDaoSupport.execute(es_work_custom_node_ins_sql);
		// 查询短信发送数据
		List<Map> gonghaoInfos = baseDaoSupport.queryForList(sendSms);
		// 发送短信
		this.sendMessage(gonghaoInfos, this.order_id);
	}

	/**
	 * 
	 * <p>
	 * Title: findByIsNewCustom
	 * </p>
	 * <p>
	 * Description: TODO
	 * </p>
	 * 
	 * @author sgf
	 * @time 2018年9月10日 下午5:38:28
	 * @version 1.0
	 * @param is_work_custom
	 * @return
	 */
	private String findByIsNewCustom(String is_work_custom) {
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		String instance_id = "";
		if ("1".equals(is_work_custom)) {
			String sql_instance = " select instance_id from es_work_custom_node_ins  t where is_curr_step='1' and is_done='0' and order_id='"
					+ order_id + "'";
			instance_id = baseDaoSupport.queryForString(sql_instance);
			if (null != instance_id) {
				return instance_id;
			}
		}
		return null;
	}

	// 订单回退到省中台以短信方式通知处理人员

	public void orderRollback(String userId, String order_ids) throws Exception {
		List<Map> gonghaoInfos = null;
		gonghaoInfos = ecsOrdManager.queryAllAdminUserId(userId);
		if (gonghaoInfos != null && gonghaoInfos.size() > 0) {
			sendMessage(gonghaoInfos, order_ids);
		} else {
			gonghaoInfos = ecsOrdManager.queryAllocationAdminUserId(userId);
			if (gonghaoInfos != null && gonghaoInfos.size() > 0) {
				sendMessage(gonghaoInfos, order_ids);
			}
		}
	}

	public void sendMessage(List<Map> gonghaoInfos, String order_ids) {
		for (Map map : gonghaoInfos) {
			Map data = new HashMap<String, String>();
			String[] orderids = order_ids.split(",");
			String order_id = "";
			if (orderids.length > 1) {
				order_id = orderids[0] + "等" + orderids.length + "条订单";
			} else {
				order_id = orderids[0];
			}
			data.put("orgname", MapUtils.getString(map, "org_name"));
			data.put("orderid", order_id);
			data.put("userid", MapUtils.getString(map, "userid"));
			// 您的【${orgname}订单池】收到待处理订单${orderid}（订单编号），可用工号【${userid}】登录订单中心领取处理。
			IRemoteSmsService localRemoteSmsService = ApiContextHolder.getBean("localRemoteSmsService");
			String smsContent = localRemoteSmsService.getSMSTemplate("SMS_LOGIN_CODE", data);
			AopSmsSendReq smsSendReq = new AopSmsSendReq();
			smsSendReq.setBill_num("10010");// 短信发送号码
			smsSendReq.setService_num(MapUtils.getString(map, "phone_num"));// 接受号码--省内联通号码
			smsSendReq.setSms_data(smsContent);
			smsSendReq.setOrder_id(order_ids);
			/*
			TaskThreadPool taskPool = new TaskThreadPool(new Task(smsSendReq) {
				@Override
				public ZteResponse execute(ZteRequest zteRequest) throws Exception {
					// TODO Auto-generated method stub
					AopSmsSendReq smsSendReq = (AopSmsSendReq) zteRequest;
					ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
					AopSmsSendResp resp = client.execute(smsSendReq, AopSmsSendResp.class);
					return resp;
				}
			});
			ThreadPoolFactory.orderExecute(taskPool);
			*/
		}
	}

	public String checkFlowTrace() {
		String[] str = this.lockOrderIdStr.split(",");
		String flag = "true";
		String first_order_id = str[0];
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(first_order_id);// CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
		String trace_id = orderExt.getFlow_trace_id();
		for (int j = 0; j < str.length; j++) {
			String order_id = str[j];
			OrderTreeBusiRequest orderTree2 = CommonDataFactory.getInstance().getOrderTree(order_id);// CommonDataFactory.getInstance().getOrderTree(order_id);
			OrderExtBusiRequest orderExt2 = orderTree2.getOrderExtBusiRequest();

			String trace_id2 = orderExt2.getFlow_trace_id();
			if (trace_id.equals(trace_id2)) {
				continue;
			} else {
				this.json = "{result:1,msg:'不能批量分配处于不同环节的订单'}";
				return JSON_MESSAGE;
			}
		}
		this.json = "{result:0,msg:'通过'}";
		return JSON_MESSAGE;

	}

	private String boxCode; // 料箱号
	private String boxCodeTest; // 料箱号测试用

	/**
	 * 根据料箱号，获取外部订单编号
	 * 
	 * @return
	 */
	public String getOrdByBoxIdFromWMS() {
		GetOrdByBoxIdFromWMSReq req = new GetOrdByBoxIdFromWMSReq();
		GetOrdByBoxIdFromWMSResp resp = new GetOrdByBoxIdFromWMSResp();
		if (StringUtils.isNotBlank(boxCode)) {
			req.setBoxCode(boxCode);
			ZteClient client = ClientFactory.getZteDubboClient("ECS");
			resp = client.execute(req, GetOrdByBoxIdFromWMSResp.class);
			if (resp != null && StringUtils.isNotBlank(resp.getOrderId())) {
				json = "{result:0, message:'" + resp.getOrderId() + "'}";
			} else {
				json = "{result:1, message:'没有对应的订单编号!'}";
			}
		}
		return JSON_MESSAGE;
	}

	/**
	 * 检查是否当前锁定用户
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-11-11
	 * @return
	 */
	public String checkLockUser() {
		String msg = "";
		AdminUser user = ManagerUtils.getAdminUser();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();

		List<OrderLockBusiRequest> orderLockRequest = orderTree.getOrderLockBusiRequests();
		OrderLockBusiRequest orderLockBusiRequest = null;
		for (int i = 0; i < orderLockRequest.size(); i++) {
			if (orderExtBusiRequest.getFlow_trace_id().equals(orderLockRequest.get(i).getTache_code())) {
				orderLockBusiRequest = orderLockRequest.get(i);
			}
		}
		if (orderLockBusiRequest == null
				|| EcsOrderConsts.UNLOCK_STATUS.equals(orderLockBusiRequest.getLock_status())) {
			msg = "请先锁定订单";
		} else if (!user.getUserid().equals(orderLockBusiRequest.getLock_user_id())) {
			msg = "订单已经被其他用户锁定,不能进行操作";
		}
		return msg;
	}

	/**
	 * 终端串号/号码释放失败记录
	 * 
	 * @return
	 */
	public String showReleaseList() {
		if (null == params) {
			params = new OrderQueryParams();
		}
		if (StringUtils.isEmpty(params.getDeal_type())) {
			params.setDeal_type("0");
		}
		this.webpage = ecsOrdManager.queryReleaseList(this.getPage(), this.getPageSize(), params);
		return "release_record";
	}

	/**
	 * 释放终端串号/号码
	 * 
	 * @return
	 */
	public String releaseRecord() {
		String deal_result = ecsOrdManager.releaseRecord(release_id);
		this.json = "{result:1,message:'" + deal_result + "'}";
		return this.JSON_MESSAGE;
	}

	/**
	 * 通过接口释放终端串号/号码
	 * 
	 * @return
	 */
	public String releaseRecordByI() {
		String deal_result = ecsOrdManager.releaseRecordByI(release_id);
		this.json = "{result:1,message:'" + deal_result + "'}";
		return this.JSON_MESSAGE;
	}

	/**
	 * 校验终端串号并保存
	 * 
	 * @return
	 */
	public String saveTerminalNum() {

		// 测试代码
		/*
		 * ZteClient client =
		 * ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		 * CustomerInfoCheckReq req=new CustomerInfoCheckReq();
		 * req.setNotNeedReqStrOrderId(order_id);
		 * req.setOpeSysType(EcsOrderConsts.AOP_OPE_SYS_TYPE_1);// AOP_OPE_SYS_TYPE_2
		 * -cbss CustomerInfoResponse rsp=client.execute(req,
		 * CustomerInfoResponse.class);
		 */
		boolean flag = true;
		// boolean flag=false;
		// try {
		// flag=ecsOrdManager.terminalNumCheck(order_id,terminal_nums);//aop接口查询终端串号
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		if (flag) {// 校验通过
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.TERMINAL_NUM },
					new String[] { terminal_nums });
			this.json = "{result:1,msg:'成功'}";
		} else {
			this.json = "{result:0,msg:'串号校验失败'}";
		}

		return this.JSON_MESSAGE;
	}

	/**
	 * 页面输入串号实现终端调拨,多个串号以","隔开
	 * 
	 * @return
	 */
	public String terminalTransfer() {
		if (StringUtils.isEmpty(terminal_nums)) {// 首次载入页面
			AdminUser user = ManagerUtils.getAdminUser();
			if (null != user && !StringUtils.isEmpty(user.getUserid())) {// 获取当前登录工号绑定列表
				empOperInfos = ecsOrdManager.getBindRelByOrderGonghao(user.getUserid());
				if (null != empOperInfos) {
					empOperInfosSize = empOperInfos.size();
				}
			}
			cityList = CommonDataFactory.getInstance().getDictRelationListData("city");
			return "terminal_transfer";
		}
		String deal_result = "";
		deal_result = ecsOrdManager.terminalTransfer(ess_emp_id, terminal_nums);
		this.json = "{result:1,message:'" + deal_result + "'}";
		return this.JSON_MESSAGE;// 点击调拨按钮
	}

	/**
	 * 已归档订单恢复
	 * 
	 * @return
	 */
	public String ordRecover() {
		String result_flag = "";
		String result_msg = "";
		String lock_name = "ordRecover=" + order_id;
		Map lock = OrderFlowAction.getMutexLock(lock_name);
		synchronized (lock) {
			try {
				Map map = new HashMap();
				map = orderExtManager.getMapByOrderid(order_id);
				String archive_type = (String) map.get("archive_type");
				if (EcsOrderConsts.ARCHIVE_TYPE_4.equals(archive_type)) {

					BusiDealResult result = ordArchiveTacheManager.ordRecover(order_id);
					result_msg = result.getError_msg();
					result_flag = result.getError_code();

					if (EcsOrderConsts.BUSI_DEAL_RESULT_0000.equals(result_flag)) {

						// 保存恢复时间
						OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
						OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
						orderExtBusiRequest.setRecover_time(DateUtil.getTime2()); // 这个很重要，防止一恢复又被归档
						orderExtBusiRequest.setIs_archive(EcsOrderConsts.NO_DEFAULT_VALUE); // 这个很重要，防止一恢复又被归档
						orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						orderExtBusiRequest.store();

						// 记录es_order_handle_logs表
						OrderHandleLogsReq req = new OrderHandleLogsReq();
						String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
						String flow_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
						archive_type = orderTree.getOrderExtBusiRequest().getArchive_type();
						req.setOrder_id(order_id);
						req.setFlow_id(flow_id);
						req.setFlow_trace_id(flow_trace_id);
						req.setComments("归档订单恢复");
						req.setHandler_type(Const.ORDER_HANDLER_TYPE_RECOVER);
						req.setType_code(archive_type);
						this.ordFlowManager.insertOrderHandLog(req);
					}
				} else {
					result_msg = "非积压归档,不允许恢复数据!";
					result_flag = EcsOrderConsts.BUSI_DEAL_RESULT_FAIL;
				}

			} catch (Exception e) {
				e.printStackTrace();
				result_msg = "数据恢复失败";
				result_flag = EcsOrderConsts.BUSI_DEAL_RESULT_FAIL;
			} finally {
				OrderFlowAction.removeMutexLock(lock_name);
			}
		}
		this.json = "{result:" + result_flag + ",message:'" + result_msg + "'}";
		return this.JSON_MESSAGE;
	}

	/**
	 * 已归档订单恢复(手动输入外部单号)
	 * 
	 * @return
	 */
	public String ordRecoverByInp() {
		String result_flag = "";
		String result_msg = "";
		String suc_ids = "";
		if (StringUtils.isEmpty(order_ids)) {
			result_flag = EcsOrderConsts.BUSI_DEAL_RESULT_FAIL;
			result_msg = "请输入外部订单号！";
		} else {
			String[] out_tid_array = order_ids.split(",");
			int max = 10;
			if (max < out_tid_array.length) {
				result_flag = EcsOrderConsts.BUSI_DEAL_RESULT_FAIL;
				result_msg = "每次恢复不可超过" + max + "个订单！";
			} else {
				for (int i = 0; i < out_tid_array.length; i++) {
					Map map = new HashMap();
					try {
						map = orderExtManager.getMapByOutTid(out_tid_array[i]);
					} catch (Exception e) {
					}
					String order_id = (String) map.get("order_id");
					if (StringUtils.isEmpty(order_id)) {
						result_msg += out_tid_array[i] + "不存在或未归档;";
						continue;
					}

					String archive_type = (String) map.get("archive_type");
					if (!EcsOrderConsts.ARCHIVE_TYPE_4.equals(archive_type)) {
						result_msg += out_tid_array[i] + "非积压订单归档，不可恢复;";
						continue;
					}

					String lock_name = "R" + order_id;
					Map lock = OrderFlowAction.getMutexLock(lock_name);
					synchronized (lock) {
						try {
							BusiDealResult result = ordArchiveTacheManager.ordRecover(order_id);
							// result_msg = result.getError_msg();
							result_flag = result.getError_code();

							if (EcsOrderConsts.BUSI_DEAL_RESULT_0000.equals(result_flag)) {

								// 保存恢复时间
								orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
								OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
								orderExtBusiRequest.setRecover_time(DateUtil.getTime2()); // 这个很重要，防止一恢复又被归档
								orderExtBusiRequest.setIs_archive(EcsOrderConsts.NO_DEFAULT_VALUE); // 这个很重要，防止一恢复又被归档
								orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
								orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
								orderExtBusiRequest.store();

								// 记录es_order_handle_logs表
								OrderHandleLogsReq req = new OrderHandleLogsReq();
								String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
								String flow_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
								archive_type = orderTree.getOrderExtBusiRequest().getArchive_type();
								req.setOrder_id(order_id);
								req.setFlow_id(flow_id);
								req.setFlow_trace_id(flow_trace_id);
								req.setComments("归档订单恢复");
								req.setHandler_type(Const.ORDER_HANDLER_TYPE_RECOVER);
								req.setType_code(archive_type);
								this.ordFlowManager.insertOrderHandLog(req);

								suc_ids += out_tid_array[i] + "、";
							} else {
								result_msg += out_tid_array[i] + result_msg;
							}
						} catch (Exception e) {
							e.printStackTrace();
							result_msg += out_tid_array[i] + "数据恢复失败;";
						} finally {
							OrderFlowAction.removeMutexLock(lock_name);
						}
					}
				}
				if (StringUtils.isEmpty(result_msg)) {
					result_msg = "数据恢复成功";
					result_flag = EcsOrderConsts.BUSI_DEAL_RESULT_0000;
				} else {
					if (!StringUtils.isEmpty(suc_ids)) {
						result_msg = suc_ids + "恢复成功！##   " + result_msg;
					}
					result_flag = EcsOrderConsts.BUSI_DEAL_RESULT_FAIL;
				}
			}

		}
		result_msg = result_msg.replace("\n", "");
		this.json = "{result:" + result_flag + ",message:'" + result_msg + "'}";
		return this.JSON_MESSAGE;
	}

	public String expTerminalNum() {
		OrderTreeBusiRequest orderTree = null;
		if (is_his_order != null && EcsOrderConsts.IS_ORDER_HIS_YES.equals(is_his_order)) {// 历史单
			orderTree = CommonDataFactory.getInstance().getOrderTreeHis(order_id);
		} else {
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		}

		List<OrderItemExtvtlBusiRequest> list = orderTree.getOrderItemExtvtlBusiRequests();
		if (list != null && list.size() > 0) {
			List<Map<String, String>> flags = CommonDataFactory.getInstance().listDcPublicData("termi_occupied_flag");
			Map map = new HashMap();
			for (Map<String, String> flag : flags) {
				map.put(flag.get("pkey"), flag.get("pname"));
			}
			for (OrderItemExtvtlBusiRequest item : list) {
				String goods_type = item.getGoods_type();
				String goods_type_name = SpecUtils.getGoodsTypeNameById(goods_type);
				item.setGoods_type_name(goods_type_name);
				String status = (String) map.get(item.getOperation_status());
				if (null != status) {
					item.setOperation_status(status);
				}
			}
		}
		String fileTitle = order_id + "货品明细";
		String[] title = { "商品名称", "商品价格", "货品类型", "机型编码", "品牌名称", "型号名称", "颜色", "终端串号", "操作状态", "操作描述" };
		String[] content = { "goods_name", "goods_price", "goods_type_name", "machine_type_code",
				"resources_brand_name", "resources_model_name", "resources_color", "resources_code", "operation_status",
				"operation_desc" };
		DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());
		// this.json = "{result:1,message:'"+order_id+"'}";
		// return this.JSON_MESSAGE;
		return null;
	}

	public String sendSms() {
		AopSmsSendReq req = new AopSmsSendReq();
		req.setService_num(this.sms_phone);
		req.setSms_data(this.sms_content);
		req.setBill_num(null);

		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		AopSmsSendResp resp = client.execute(req, AopSmsSendResp.class);
		this.json = "{result:0,message:'发送成功'}";
		return this.JSON_MESSAGE;
	}

	/**
	 * 保存补录的物流单号
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-8
	 * @return
	 */
	public String saveLogiNo() {
		try {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			List<OrderDeliveryBusiRequest> deliveryList = orderTree.getOrderDeliveryBusiRequests();
			for (OrderDeliveryBusiRequest delivery : deliveryList) {
				if (EcsOrderConsts.LOGIS_NORMAL.equals(delivery.getDelivery_type())) {
					delivery.setLogi_no(logi_no);
					delivery.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					delivery.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					delivery.store();
				}
			}
			json = "{result:'0',msg:'成功'}";
		} catch (Exception e) {
			json = "{result:'-1',msg:'失败" + e.getMessage() + "'}";
		}

		return JSON_MESSAGE;
	}

	/**
	 * 外呼订单确认
	 * 
	 * @作者 范琪杰
	 * @创建日期 2016-10-17
	 * @return
	 */
	public String ordOutCallApply() {
		String lockMsg = checkLockUser();
		if (!StringUtils.isEmpty(lockMsg)) {
			this.json = "{result:1,message:'" + lockMsg + "'}";
			return this.JSON_MESSAGE;
		}

		AdminUser user = ManagerUtils.getAdminUser();
		if (this.order_id == null && this.order_id.isEmpty()) {
			this.json = "{result:1,message:'缺少订单编号'}";
			return this.JSON_MESSAGE;
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("oper_id", user.getUsername());
		params.put("order_id", this.order_id);
		params.put("isOrderCancel", "false");

		// 外呼确认后订单需要解锁
		OrderExtBusiRequest orderExtReq = CommonDataFactory.getInstance().getOrderTree(order_id)
				.getOrderExtBusiRequest();
		/*
		 * orderExtReq.setLock_user_id(ConstsCore.NULL_VAL);
		 * orderExtReq.setLock_user_name(ConstsCore.NULL_VAL);
		 * orderExtReq.setLock_status("0");
		 */

		String trace_id = orderExtReq.getFlow_trace_id();
		List<Map> admlist = ecsOrdManager.getOPerId(order_id, trace_id);
		String res = ecsOrdManager.ordOutCallApply(params);
		List<OrderLockBusiRequest> orderLockBusiRequestList = CommonDataFactory.getInstance().getOrderTree(order_id)
				.getOrderLockBusiRequests();
		if (null != orderLockBusiRequestList && orderLockBusiRequestList.size() > 0) {
			for (int i = 0; i < orderLockBusiRequestList.size(); i++) {
				OrderLockBusiRequest orderLockBusiRequest = orderLockBusiRequestList.get(i);
				if (trace_id.equals(orderLockBusiRequest.getTache_code())) {
					/*
					 * String adm = ecsOrdManager.getOPerId(order_id,orderLockBusiRequest.
					 * getTache_code());
					 */
					orderLockBusiRequest.setLock_user_id(Const.getStrValue(admlist.get(0), "oper_id"));
					orderLockBusiRequest.setLock_user_name(Const.getStrValue(admlist.get(0), "username"));
					orderLockBusiRequest.setLock_status(EcsOrderConsts.LOCK_STATUS);
					orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					// 锁单信息增加工号池和锁单结束时间
					orderLockBusiRequest.setPool_id(workPoolManager
							.getLockTimeByUserId(Const.getStrValue(admlist.get(0), "oper_id")).getPool_id());
					orderLockBusiRequest.setLock_end_time(workPoolManager
							.getLockTimeByUserId(Const.getStrValue(admlist.get(0), "oper_id")).getLock_end_time());
					orderLockBusiRequest.store();
				}
			}
		}

		/*
		 * orderExtReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		 * orderExtReq.setDb_action(ConstsCore.DB_ACTION_UPDATE); orderExtReq.store();
		 */

		if ("success".equals(res)) {
			this.json = "{result:0,message:'操作成功',action_url:'" + getRequest().getContextPath()
					+ "/shop/admin/ordAuto!showCallOutOrderList.do'}";
		} else {
			this.json = "{result:1,message:'" + res + "'}";
		}

		return this.JSON_MESSAGE;
	}

	public String orderInput() {

		return "order_input";
	}

	public String saveManualOrder() {
		OrderCtnResp resp = this.orderExtManager.saveManualOrder(manualOrder, "D");
		this.json = "{result:'" + resp.getError_code() + "',msg:'" + resp.getError_msg() + "',order_id:'"
				+ resp.getBase_order_id() + "'}";
		return this.JSON_MESSAGE;
	}

	public String ord_outCallApply() {

		return "outCallApply";
	}

	/**
	 * 工单详情页面
	 * 
	 * @author song.qi
	 * @return 自动分页会和查询界面冲突
	 */
	public String workList4Order() {
		this.webpage = ordWorkManager.getWorkListByOrderId(this.order_id.replace(",", "").trim(), this.getPage(),
				this.getPageSize());
		return "workList4Order";
	}

	/**
	 * 工单查询页面
	 * 
	 * @author song.qi
	 * @return
	 */
	public String searchWorksListForm() {
		if (null == this.params) {
			this.params = new OrderQueryParams();
		}
		if (StringUtils.isEmpty(this.params.getCreate_end())) {
			String endTime = DF.format(new Date());
			this.params.setCreate_end(endTime);
		}
		order_from_list = ecsOrdManager.getDcSqlByDcName("ORDER_FROM");
		this.webpage = ordWorkManager.queryWorkListByOrder_id(this.params, this.getPage(), this.getPageSize());
		return "workByOrder";
	}

	public String work_check() {
		// Map attrMap = getPageOrderAttrMap(order_id);
		// 页面取值营业县分
		// String countyId = ecsOrdManager.getAttrIdByName("county_id");
		// String county_id = (String) attrMap.get("oattrinstspec_" + order_id +
		// "_" + countyId + "_" + countyId + "#county_id");
		// 数据库取值
		List<OrderAdslBusiRequest> orderAdslBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id)
				.getOrderAdslBusiRequest();
		if (null != orderAdslBusiRequest && orderAdslBusiRequest.size() > 0) {
			cityCode = orderAdslBusiRequest.get(0).getCounty_id();
		} else {
			cityCode = "";
		}
		// 页面取值营业县分
		if (StringUtils.isEmpty(county_id)) {
			json = "{result:1,message:'请选择营业县分!'}";
		} else {
			if (!StringUtils.isEmpty(cityCode)) {
				if (!StringUtil.equals(county_id, cityCode)) {
					json = "{result:1,message:'请点击保存按钮!'}";
				} else {
					json = "{result:0,message:'可以申请工单'}";
				}
			} else {
				json = "{result:1,message:'请点击保存按钮!'}";
			}
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 工单回收
	 * 
	 * @return
	 */
	public String closeOrdWork() {
		try {
			if (!StringUtils.isEmpty(order_id.trim())) {
				String msg = ordWorkManager.closeOrdWork(order_id.trim(), workReason);
				if (StringUtils.isEmpty(msg)) {
					json = "{result:0,message:'工单关闭成功'}";
				} else {
					json = "{result:1,message:'" + msg + "'}";
				}
			} else {
				json = "{result:1,message:'单号order_id为空!'}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = "{result:1,message:'" + e.getMessage() + "'}";
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 工单回收弹框页面
	 * 
	 * @author song.qi
	 * @return
	 */
	public String closeWork() {
		return "closeWork";
	}

	/**
	 * 派单页面
	 * 
	 * @author song.qi
	 * @return
	 */
	public String ord_Work() {
		this.webpage = ordWorkManager.getWorkListByOrderId(this.order_id.replace(",", "").trim(), this.getPage(),
				this.getPageSize());

		if ("1".equals(this.is_work_custom)) {
			return "work_custom_ord_work";
		} else {
			return "ord_Work";
		}
	}

	private static Map getPageOrderAttrMap(String order_id) {
		Map attrInstMap = new HashMap();
		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
		if (request == null)
			return new HashMap();
		Map parameterMap = request.getParameterMap();
		for (Iterator iterator = parameterMap.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if (key.indexOf(getAttrInstElementPrefix(order_id)) > -1) {
				String values[] = request.getParameterValues(key);
				String value = stingArrayToStr(values);
				attrInstMap.put(key, value);
			}
		}
		return attrInstMap;
	}

	public static String getAttrInstElementPrefix(String order_id) {
		return "oattrinstspec_" + order_id + "_";
	}

	private static String stingArrayToStr(String values[]) {
		String str = "";
		String prefix = ",";
		for (int i = 0; i < values.length; i++) {
			if (i == values.length - 1)
				prefix = "";
			str += values[i] + prefix;
		}
		return str;
	}

	/**
	 * 派单页面选择操作员
	 * 
	 * @author song.qi
	 * @return
	 */
	public String getOperator() {
		try {
			OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
			if (ordertree != null) {
				String order_from = ordertree.getOrderExtBusiRequest().getOrder_from();
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String SERVICE_TYPE_CHECK = cacheUtil.getConfigInfo("SERVICE_TYPE_CHECK");
				if (!StringUtil.isEmpty(SERVICE_TYPE_CHECK) && SERVICE_TYPE_CHECK.contains(order_from + ",")) {
					String order_state = ordertree.getOrderBusiRequest().getOrder_state();
					String order_state_check = cacheUtil.getConfigInfo("ORDER_STATE_CHECK");
					if (!StringUtil.isEmpty(order_state_check) && order_state_check.contains(order_state + ",")) {
						json = "{result:1,message:'请先进行订单审核'}";
						return this.JSON_MESSAGE;
					}
				}
			}

			OrderListForWorkResp resp = getResp();
			if ("00000".equals(resp.getCode())) {
				if (null != resp.getRespJson()) {
					Map<String, Object> respMap = new HashMap<String, Object>();
					respMap.put("list", resp.getRespJson().getStaffList());
					if (null == resp.getRespJson().getStaffList() || resp.getRespJson().getStaffList().size() == 0) {
						json = "{result:1,message:'可选任务人员为空！请确认营业县分是否为空'}";
					} else {
						String operatorList = JSONObject.fromObject(respMap).toString();
						operatorList = operatorList.replace("\"", "'");
						operatorList = operatorList.substring(1);
						operatorList = operatorList.substring(0, operatorList.length() - 1);
						json = "{result:0,message:'任务人员查询成功！'," + operatorList + "}";
					}
				} else {
					json = "{result:1,message:'可选任务人员为空！请确认营业县分是否为空'}";
				}
			} else {
				json = "{result:1,message:'任务人员查询失败！'}";
			}
			// json = "{result:0,message:'任务人员查询成功！'}";
		} catch (Exception e) {
			json = "{result:1,message:'任务人员查询异常！'}";
			e.printStackTrace();
		}
		return this.JSON_MESSAGE;
	}

	private OrderListForWorkResp getResp() {
		OrderListForWorkReq req = new OrderListForWorkReq();
		req.setNotNeedReqStrOrderId(order_id);
		req.setOrdertype(work_type);
		// 从页面取值
		// req.setCityCode(cityCode);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderListForWorkResp resp = client.execute(req, OrderListForWorkResp.class);
		return resp;
	}

	/**
	 * 保存工单
	 * 
	 * @return
	 */
	public String save_work() {
		try {

			// sgf 宽带一期工单派发
			OrderTreeBusiRequest otreq = CommonDataFactory.getInstance().getOrderTree(this.order_id);
			Boolean flags = ordWorkManager.isKDYQ(this.order_id, otreq);
			if (flags) {
				List<OrderAdslBusiRequest> lists = otreq.getOrderAdslBusiRequest();
				if (lists.size() > 0) {
					String adsl_addrS = lists.get(0).getAdsl_addr();
					if (StringUtils.isEmpty(adsl_addrS) || "-1".equals(adsl_addrS)) {
						this.json = "{result:1,message:'请回到订单审核环节,选择标准地址且进行保存'}";
						return this.JSON_MESSAGE;
					}
				}
			}
			if (StringUtils.isEmpty(operator_num)) {
				json = "{result:1,message:'保存异常！操作员联系电话为空'}";
				return this.JSON_MESSAGE;
			}

			if (operator_num.length() > 11) {
				operator_num = operator_num.substring(0, 11);
			}

			String order_id = this.order_id;
			Map work = new HashMap();
			work.put("order_id", this.order_id);
			// work.put("type", this.work_type);
			work.put("remark", this.work_reason);

			// 自定义流程流程环节实例编号
			work.put("node_ins_id", SqlUtil.getStrValue(this.instance_id));
			//
			String operator_name = "";
			OrderListForWorkResp resp = getResp();
			if ("00000".equals(resp.getCode())) {
				String operator_id = "";
				String operator_office_id = "";
				List<StaffList> staffList = resp.getRespJson().getStaffList();
				if (null != staffList && staffList.size() > 0) {
					for (StaffList obj : staffList) {
						if (operator_num.trim().equals(obj.getPhoneNum())) {
							operator_id = obj.getStaffCode();
							operator_name = obj.getStaffName();
							break;
						}
					}
					work.put("operator_id", operator_id);// 操作员工号
					work.put("operator_office_id", operator_office_id);// 操作点id
					work.put("operator_num", operator_num.trim());// 操作员联系电话
					work.put("operator_name", operator_name);// 操作员姓名
					// 预留字段
					work.put("order_priority", "C");// 工单优先级预留，例如： A：高；B：中；C：普通
					work.put("order_time_limit", new Date());// 工单处理时限
					String[] types = this.work_type.replace(" ", "").trim().split(",");// 多选框处理
					String flag = "";
					for (String type : types) {
						if (!StringUtil.isEmpty(type.trim())) {
							work.put("type", type.trim());
							flag += orderExtManager.saveWork(work);
						}
					}
					if (!StringUtil.isEmpty(flag) && flag.indexOf("0") != -1) {
						if (types.length == flag.length()) {// 全部保存成功
							json = "{result:0,message:'保存成功！'}";
						} else {// 部分工单成功
							flag = flag.replace("0", "");
							json = "{result:1,message:'部分保存成功！" + flag + "重复'}";
						}
						ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
						String bill_num = cacheUtil.getConfigInfo("SYN_BILL_NUM");
						String msg = "工单";// 发送内容
						// 工单类型01 – 收费单；02 -- 外勘单；03 – 实名单；04 – 挽留单；05 – 写卡单

						IDaoSupport baseDaoSupport = null;
						if (baseDaoSupport == null) {
							baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
						}

						String sql = "select o.order_contact_phone, o.order_contact_addr, decode(i.phone_num, '',t.acc_nbr , i.phone_num) as acc_nbr from es_work_order o left join es_order_intent t on o.order_id = t.order_id left join es_order_items_ext i on o.order_id = i.order_id where o.source_from = '"
								+ ManagerUtils.getSourceFrom() + "' and o.order_id = '" + order_id
								+ "' and o.status='0' ";
						List<Map<String, String>> list = baseDaoSupport.queryForList(sql);
						Map map = list.get(0);

						Map data = new HashMap();
						data.put("order_contact_phone", MapUtils.getObject(map, "order_contact_phone"));
						data.put("order_contact_addr", MapUtils.getObject(map, "order_contact_addr"));
						data.put("acc_nbr", MapUtils.getObject(map, "acc_nbr"));

						IRemoteSmsService localRemoteSmsService = ApiContextHolder.getBean("localRemoteSmsService");
						String smsContent = localRemoteSmsService.getSMSTemplate("WORK_INFO", data);
						AopSmsSendReq smsSendReq = new AopSmsSendReq();
						smsSendReq.setSms_data(smsContent);
						smsSendReq.setBill_num(bill_num);// 短信发送号码
						smsSendReq.setService_num(operator_num);// 接受号码--省内联通号码
						smsSendReq.setOrder_id(order_id);
						ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
						AopSmsSendResp smsSendResp = client.execute(smsSendReq, AopSmsSendResp.class);
						if (smsSendResp != null && ConstsCore.ERROR_SUCC.equals(smsSendResp.getError_code())) {
							logger.info("工单提醒内容发送成功！");
						} else {
							logger.info("工单提醒内容发送失败！");
						}
					} else {
						if (flag.indexOf("订单") != -1) {
						} else if (flag.indexOf("单") != -1) {

							flag = flag + "已存在，请不要重复提交";
						}
						json = "{result:1,message:'保存异常！" + flag + "'}";
					}
				}
			} else {
				json = "{result:2,message:'保存异常！操作人员获取异常'}";
				return this.JSON_MESSAGE;
			}
			this.orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			if (orderTree != null) {
				String is_work_custom = orderTree.getOrderExtBusiRequest().getIs_work_custom();
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String kgs = cacheUtil.getConfigInfo("work_save_kg");
				OrderHandleLogsReq req = new OrderHandleLogsReq();
				if ("1".equals(is_work_custom) && "1".equals(kgs)) {
					IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
					ES_WORK_CUSTOM_NODE_INS pojo = new ES_WORK_CUSTOM_NODE_INS();
					pojo.setOrder_id(order_id);
					pojo.setIs_curr_step("1");
					pojo.setIs_done("0");
					List<ES_WORK_CUSTOM_NODE_INS> insRet = this.workCustomCfgManager.qryInsList(pojo, null);
					if (insRet != null && insRet.size() > 0) {
						pojo = insRet.get(0);
					}
					Map<String, Object> map = new HashMap();
					map.put("order_id", order_id);
					map.put("instance_id", pojo.getInstance_id());
					map.put("source_from", "ECS");
					map.put("create_date", "sysdate");
					map.put("op_id", operator_num);
					map.put("op_name", operator_name + "(" + operator_num + ")");
					map.put("workflow_id", pojo.getWorkflow_id());
					map.put("node_name", "派单");
					map.put("remark", work_reason + " /工单");
					baseDaoSupport.insert("es_work_custom_log", map);
				} else {
					AdminUser user = ManagerUtils.getAdminUser();
					req.setOp_id(user.getUserid());
					req.setOp_name(user.getRealname());
					req.setOrder_id(otreq.getOrder_id());
					req.setFlow_id(otreq.getOrderExtBusiRequest().getFlow_id());
					req.setFlow_trace_id(otreq.getOrderExtBusiRequest().getFlow_trace_id());
					req.setComments(work_reason);
					req.setHandler_type(Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
					req.setType_code("o_workApply");
					this.ordFlowManager.insertOrderHandLog(req);
				}
				updateOrderState(orderTree, "es_order", "order_state", "3", work_reason);
			}
		} catch (Exception e) {

			json = "{result:1,message:'保存失败！请联系管理员'}";
			e.printStackTrace();
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 外呼申请
	 * 
	 * @return
	 */
	public String save_outCallApply() {
		// TODO 外呼申请保存
		try {
			String order_id = this.order_id;
			String outcall_type = this.getOutcall_type();
			String lockMsg = this.ecsOrdManager.checkLockUser(this.order_id, query_type);
			if (!StringUtils.isEmpty(lockMsg)) {
				this.json = "{result:1,message:'" + lockMsg + "'}";
				return this.JSON_MESSAGE;
			}
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			OrderBusiRequest orderBusiObject = orderTree.getOrderBusiRequest();
			OrderExtBusiRequest orderExtBusi = orderTree.getOrderExtBusiRequest();
			Map<String, String> orderOutCall = new HashMap<String, String>();
			String userId = ManagerUtils.getAdminUser().getUserid();
			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String status = Integer.toString(orderBusiObject.getStatus());
			if ("11".equals(status)) {
				this.json = "{result:1,message:'订单已在外呼状态'}";
				return this.JSON_MESSAGE;
			}
			orderOutCall.put("order_id", order_id);
			orderOutCall.put("tache_code", orderExtBusi.getFlow_trace_id());
			orderOutCall.put("order_status", status);
			orderOutCall.put("oper_time", time.format(new Date()));
			orderOutCall.put("oper_id", userId);
			orderOutCall.put("is_finish", "0");
			orderOutCall.put("oper_remark", outcall_reason);
			orderOutCall.put("deal_remark", outcall_type);
			// this.ordFlowManager.insertOrderOutCallLog(orderOutCall);
			orderBusiObject.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_11);
			orderBusiObject.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderBusiObject.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderBusiObject.store();
			// 防止更新订单树缓存异常，把插入记录放在
			this.ordFlowManager.insertOrderOutCallLog(orderOutCall);

			String trace_id = orderExtBusi.getFlow_trace_id();
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
			json = "{result:0,message:'操作成功！'}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 获取并发送短信验证码
	 * 
	 * @return
	 */
	public String getSmsValidCode() {
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		SmsValidCodeGetReq req = new SmsValidCodeGetReq();
		req.setUser_name(this.username);
		SmsValidCodeGetResp resp = client.execute(req, SmsValidCodeGetResp.class);
		if (ConstsCore.ERROR_SUCC.equals(resp.getError_code())) {
			AopSmsSendReq smsSendReq = new AopSmsSendReq();
			smsSendReq.setService_num(resp.getReceive_phone());
			smsSendReq.setSms_data(resp.getValid_code());
			smsSendReq.setBill_num(null);

			AopSmsSendResp smsSendResp = client.execute(smsSendReq, AopSmsSendResp.class);
			if (smsSendResp != null && ConstsCore.ERROR_SUCC.equals(smsSendResp.getError_code())) {
				this.json = "{result:0,message:'短信验证码发送成功，三分钟内有效',timeInterval:'" + resp.getTime_interval() + "'}";
			} else {
				this.json = "{result:1,message:'短信验证码发送失败',timeInterval:'" + resp.getTime_interval() + "'}";
			}
		} else {
			this.json = "{result:1,message:'" + resp.getError_msg() + "'}";
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 订单领取
	 * 
	 * @return
	 */
	public String orderReceive() {
		int num = 0;
		INetCache cache1 = (INetCache) com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
		try {
			if (!StringUtils.isEmpty(this.lockOrderIdStr)) {
				String[] str = this.lockOrderIdStr.split(",");
				for (int j = 0; j < str.length; j++) {
					String order_id = str[j];

					String lock_id_intent = "select t.usertype from es_adminuser t where t.userid in (select lock_id  from es_order_intent where order_id = '"
							+ order_id + "') and t.source_from='" + ManagerUtils.getSourceFrom() + "'";
					IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");

					// 是否意向单 是否线上线下定义订单 1-是 0-否 null-否
					String is_online_order = this.isYxd(order_id);

					// 意向单领取
					if ("Y".equals(is_online_order)) {
						String usertype = baseDaoSupport.queryForString(lock_id_intent);
						ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
						String receive_info = cacheUtil.getConfigInfo("receive_info");
						// sgf 本次不提交
						if ("1".equals(receive_info) && !"1".equals(ManagerUtils.getAdminUser().getUserid())
								&& "1".equals(usertype)) {
							this.json = "{result:1,message:'领取失败，该订单已被领取,请刷新页面。'}";
							return this.JSON_MESSAGE;
						}
						String update_sql = " update es_order_intent set lock_id='"
								+ ManagerUtils.getAdminUser().getUserid() + "',lock_name='"
								+ ManagerUtils.getAdminUser().getRealname() + "' where order_id='" + order_id + "' ";
						baseDaoSupport.execute(update_sql);
						AdminUser user = ManagerUtils.getAdminUser();
						// 更新自定义流程
						ES_WORK_CUSTOM_NODE_INS pojo = new ES_WORK_CUSTOM_NODE_INS();
						pojo.setOrder_id(order_id);
						pojo.setIs_curr_step("1");
						pojo.setIs_done("0");
						List<ES_WORK_CUSTOM_NODE_INS> insRet = this.workCustomCfgManager.qryInsList(pojo, null);
						if (insRet != null && insRet.size() > 0) {
							for (int i = 0; i < insRet.size(); i++) {
								insRet.get(i).setCurr_user_id(user.getUserid());
								insRet.get(i).setCurr_user_name(user.getRealname());
								insRet.get(i).setIs_lock_flag("1");
							}
							workCustomCfgManager.updateNodeInstances(insRet);
						}
						Map<String, Object> map = new HashMap();
						map.put("order_id", order_id);
						map.put("remark", remarks);
						map.put("source_from", "ECS");
						map.put("create_time", "sysdate");
						map.put("op_id", user.getUserid());
						map.put("op_name", user.getRealname());
						map.put("type_code", "intent");
						map.put("action", "订单领取");
						baseDaoSupport.insert("es_intent_handle_logs", map);
					}

					// 正式订单领取
					String is_work_custom = this.ecsOrdManager.queryisWorkCustom(order_id);

					OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);// CommonDataFactory.getInstance().getOrderTree(order_id);
					if (orderTree == null) {
						num++;
						continue;
					}
					OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
					String trace_id = orderExt.getFlow_trace_id();

					String key = new StringBuffer().append("order_lock_").append(trace_id).append("_").append(order_id)
							.toString();
					key = MD5Util.MD5(key);
					String def = String.valueOf(cache1.get(ZjEcsOrderConsts.ORDER_LOCK_NAMESPACE, key));
					OrderLockBusiRequest orderLockBusiRequest = CommonDataFactory.getInstance()
							.getOrderLockBusiRequest(orderTree, trace_id);
					if (orderLockBusiRequest == null) {
						cache1.set(ZjEcsOrderConsts.ORDER_LOCK_NAMESPACE, key, key,
								ZjEcsOrderConsts.ORDER_LOCK_NAMESPACE_TIMEOUT);
						try {
							// 线上线下自定义流程
							if (!org.apache.commons.lang.StringUtils.isBlank(is_work_custom)
									&& "1".equals(is_work_custom)) {
								this.ecsOrdManager.updateWorkCustom(order_id);
							}
							orderLockBusiRequest = new OrderLockBusiRequest();
							String lock_id = ecsOrdManager.getSequences("o_outcall_log");
							orderLockBusiRequest.setLock_id(lock_id);
							orderLockBusiRequest.setLock_user_id(ManagerUtils.getAdminUser().getUserid());
							orderLockBusiRequest.setLock_user_name(ManagerUtils.getAdminUser().getUsername());
							orderLockBusiRequest.setLock_status(EcsOrderConsts.LOCK_STATUS);
							orderLockBusiRequest.setDealer_type("person");
							orderLockBusiRequest.setTache_code(trace_id);
							orderLockBusiRequest.setOrder_id(order_id);
							orderTree.getOrderLockBusiRequests().add(orderLockBusiRequest);
							String insert_sql = " insert into es_order_lock(lock_id,order_id,tache_code,lock_user_id,lock_user_name,lock_status,lock_time,remark,source_from,lock_end_time,pool_id,dealer_type) values (?,?,?,?,?,?,sysdate,'','ECS',null,null,'person') ";
							baseDaoSupport.execute(insert_sql, lock_id, order_id, trace_id,
									ManagerUtils.getAdminUser().getUserid(), ManagerUtils.getAdminUser().getUsername(),
									EcsOrderConsts.LOCK_STATUS);
							num++;
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}
					} else {
						// 线上线下自定义流程
						if (!org.apache.commons.lang.StringUtils.isBlank(is_work_custom)
								&& "1".equals(is_work_custom)) {
							this.ecsOrdManager.updateWorkCustom(order_id);
						}
						orderLockBusiRequest.setLock_user_id(ManagerUtils.getAdminUser().getUserid());
						orderLockBusiRequest.setLock_user_name(ManagerUtils.getAdminUser().getUsername());
						orderLockBusiRequest.setLock_status(EcsOrderConsts.LOCK_STATUS);
						orderLockBusiRequest.setDealer_type("person");
						String update_sql = " update es_order_lock set lock_user_id=?,lock_user_name=?,lock_status=?,dealer_type=? where order_id=? and tache_code=? ";
						baseDaoSupport.execute(update_sql,
								new String[] { ManagerUtils.getAdminUser().getUserid(),
										ManagerUtils.getAdminUser().getUsername(), EcsOrderConsts.LOCK_STATUS, "person",
										order_id, trace_id });
						num++;
					}
					updateOrderState(orderTree, "es_order", "order_state", "1", "订单领取");
					AdminUser user = ManagerUtils.getAdminUser();

					OrderHandleLogsReq req = new OrderHandleLogsReq();
					req.setOp_id(user.getUserid());
					req.setOp_name(user.getRealname());
					req.setOrder_id(orderTree.getOrder_id());
					req.setFlow_id(orderTree.getOrderExtBusiRequest().getFlow_id());
					req.setOp_id(user.getUserid());
					req.setOp_name(user.getRealname());
					req.setFlow_trace_id(orderTree.getOrderExtBusiRequest().getFlow_trace_id());
					req.setComments("工号: " + user.getUserid() + "订单领取:" + orderTree.getOrder_id());
					req.setHandler_type(Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
					req.setType_code("receive");
					this.ordFlowManager.insertOrderHandLog(req);

				}
			} else {
				int num1 = Integer.parseInt(receive_num);
				if (num1 != 0) {
					params = new OrderQueryParams();
					params.setLock_status(String.valueOf(ZjEcsOrderConsts.LOCK_STATUS));
					params.setCreate_start("");
					params.setCreate_end("");
					params.setQuery_type("order_receive");
					IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
					List<Map> orderReceiveList = ecsOrdManager.getOrderReceiveList(this.getPage(), num1, params);
					for (int i = 0; i < orderReceiveList.size(); i++) {
						String order_id = orderReceiveList.get(i).get("order_id").toString();
						OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
						OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
						String trace_id = orderExt.getFlow_trace_id();
						String key = new StringBuffer().append("order_lock_").append(trace_id).append("_")
								.append(order_id).toString();
						key = MD5Util.MD5(key);
						String def = String.valueOf(cache1.get(ZjEcsOrderConsts.ORDER_LOCK_NAMESPACE, key));
						OrderLockBusiRequest orderLockBusiRequest = CommonDataFactory.getInstance()
								.getOrderLockBusiRequest(orderTree, trace_id);
						if (orderLockBusiRequest == null) {
							cache1.set(ZjEcsOrderConsts.ORDER_LOCK_NAMESPACE, key, key,
									ZjEcsOrderConsts.ORDER_LOCK_NAMESPACE_TIMEOUT);
							try {
								orderLockBusiRequest = new OrderLockBusiRequest();
								orderLockBusiRequest.setLock_id(ecsOrdManager.getSequences("o_outcall_log"));
								orderLockBusiRequest.setLock_user_id(ManagerUtils.getAdminUser().getUserid());
								orderLockBusiRequest.setLock_user_name(ManagerUtils.getAdminUser().getUsername());
								orderLockBusiRequest.setOrder_id(order_id);
								orderLockBusiRequest.setTache_code(trace_id);
								orderLockBusiRequest.setLock_status(EcsOrderConsts.LOCK_STATUS);
								orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
								orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
								// 锁单信息增加工号池和锁单结束时间
								orderLockBusiRequest.setPool_id(workPoolManager
										.getLockTimeByUserId(ManagerUtils.getAdminUser().getUserid()).getPool_id());
								orderLockBusiRequest.setLock_end_time(
										workPoolManager.getLockTimeByUserId(ManagerUtils.getAdminUser().getUserid())
												.getLock_end_time());
								orderLockBusiRequest.store();
								num++;
							} catch (Exception e) {
								e.printStackTrace();
								continue;
							}

						} else {

							orderLockBusiRequest.setLock_user_id(ManagerUtils.getAdminUser().getUserid());
							orderLockBusiRequest.setLock_user_name(ManagerUtils.getAdminUser().getUsername());
							orderLockBusiRequest.setLock_status(EcsOrderConsts.LOCK_STATUS);
							orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							// 锁单信息增加工号池和锁单结束时间
							orderLockBusiRequest.setPool_id(workPoolManager
									.getLockTimeByUserId(ManagerUtils.getAdminUser().getUserid()).getPool_id());
							orderLockBusiRequest.setLock_end_time(workPoolManager
									.getLockTimeByUserId(ManagerUtils.getAdminUser().getUserid()).getLock_end_time());
							orderLockBusiRequest.store();
							num++;
						}
					}
				}
				updateOrderState(orderTree, "es_order", "order_state", "1", "订单领取");
			}

			this.json = "{result:0,message:'操作成功,你已领取" + num + "个订单。'}";
		} catch (NumberFormatException e) {
			e.printStackTrace();
			this.json = "{result:1,message:'领取数量必须为整数'}";
		} catch (Exception e) {
			e.printStackTrace();
			this.json = "{result:1,message:'" + e.getMessage() + "'}";
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 写卡完成率查询
	 * 
	 * @return
	 */

	public String queryWriteCardPer() {
		if (params == null) {
			params = new OrderQueryParams();
		}

		if (!EcsOrderConsts.IS_FIRST_LOAD.equals(first_load)) {
			Page dataPage = ecsOrdManager.qryQueueCardOrderNum(params, this.getPage(), this.getPageSize());
			List<WriteCardPercent> dataList = dataPage.getResult();

			for (WriteCardPercent ele : dataList) {
				String totalNum = ele.getNum();
				String failNum = ele.getFail();

				NumberFormat numberFormat = NumberFormat.getInstance();
				// 保留1位小数
				numberFormat.setMaximumFractionDigits(1);
				String failPercent = numberFormat.format(Float.valueOf(failNum) / Float.valueOf(totalNum) * 100);

				ele.setFailPercent(failPercent);
			}
			this.webpage = dataPage;
		} else {
			// 默认为当天的查询时间
			try {
				params.setCreate_start(DateUtil.getTime1());
				params.setCreate_end(DateUtil.getTime1());
			} catch (FrameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return "writeCardPerList";
	}

	/**
	 * 批量导入订单查询
	 */
	public String queryInputOrder() {
		if (null == params) {
			params = new OrderQueryParams();
		}
		// 获取当前工号
		AdminUser user = ManagerUtils.getAdminUser();
		String user_id = user.getUserid();
		String user_name = user.getUsername();
		// 判断是否为首次登陆
		if (!EcsOrderConsts.IS_FIRST_LOAD.equals(first_load)) {
			// 判断是否为管理员登陆
			if (EcsOrderConsts.ADMIN_USER_ID.equals(user_id)) {
				params.setUsername(username);
				params.setLock_user_id(
						EcsOrderConsts.INF_ADMINUSER.equals(username) ? EcsOrderConsts.ADMIN_USER_ID : username);

				this.webpage = ecsOrdManager.queryInputOrder(params, this.getPage(), this.getPageSize());

				params.setLock_user_id(user_id);
			} else {
				params.setUsername(user_name);
				params.setLock_user_id(user_id);

				this.webpage = ecsOrdManager.queryInputOrder(params, this.getPage(), this.getPageSize());
			}
		} else {
			params.setLock_user_id(user_id);
		}
		return "inputOrderList";
	}

	/**
	 * 查看导入订单明细 Todo:
	 * 
	 * @return
	 */
	public String inputOrderInfo() {

		Page dataPage = new Page();

		if (StringUtils.isNotBlank(excel) && excel.equals(EcsOrderConsts.IS_IMPORT_YES)) {
			dataPage = ecsOrdManager.queryInputOrderInfo(textInfo, this.getPage(), this.getPageSize());
		} else {
			dataPage = ecsOrdManager.queryInputOrderInfo(bat_id, this.getPage(), this.getPageSize());
		}
		List<Map<String, String>> dataList = (List) dataPage.getData();
		List<Map<String, String>> newData = new ArrayList();
		for (Map<String, String> map : dataList) {
			if (MapUtils.getString(map, "special_busi_type") != null
					&& !MapUtils.getString(map, "special_busi_type").equals("")) {
				String special_busi_type = CommonDataFactory.getInstance().getDcPublicDataByPkey(
						"DIC_SPECIAL_BUSINESS_TYPE", MapUtils.getString(map, "special_busi_type"));
				map.put("special_busi_type", special_busi_type);
			}

			if (MapUtils.getString(map, "is_realname") != null && !MapUtils.getString(map, "is_realname").equals("")) {
				String is_realname = MapUtils.getString(map, "is_realname").equals("1") ? "是" : "否";
				map.put("is_realname", is_realname);
			}

			if (MapUtils.getString(map, "is_old") != null && !MapUtils.getString(map, "is_old").equals("")) {
				String is_old = MapUtils.getString(map, "is_old").equals("0") ? "新用户" : "老用户";
				map.put("is_old", is_old);
			}

			if (MapUtils.getString(map, "is_attached") != null && !MapUtils.getString(map, "is_attached").equals("")) {
				String is_attached = MapUtils.getString(map, "is_attached").equals("0") ? "否" : "是";
				map.put("is_attached", is_attached);
			}

			if (MapUtils.getString(map, "pay_method") != null && !MapUtils.getString(map, "pay_method").equals("")) {
				String pay_method = CommonDataFactory.getInstance().getDcPublicDataByPkey("pay_way",
						MapUtils.getString(map, "pay_method"));
				map.put("pay_method", pay_method);
			}

			if (MapUtils.getString(map, "source_from") != null && !MapUtils.getString(map, "source_from").equals("")) {
				String source_from = CommonDataFactory.getInstance().getDcPublicDataByPkey("source_from",
						MapUtils.getString(map, "source_from"));
				map.put("source_from", source_from);
			}

			if (MapUtils.getString(map, "prod_offer_type") != null
					&& !MapUtils.getString(map, "prod_offer_type").equals("")) {
				String prod_offer_type = CommonDataFactory.getInstance().getGoodSpec(MapUtils
						.getString(ecsOrdManager.queryOrderID(MapUtils.getString(map, "out_order_id")), "order_id"),
						null, "type_name");
				map.put("prod_offer_type", prod_offer_type);
			}

			if (MapUtils.getString(map, "prod_offer_code") != null
					&& !MapUtils.getString(map, "prod_offer_code").equals("")) {
				String prod_offer_code = CommonDataFactory.getInstance().getGoodSpec(MapUtils.getString(
						ecsOrdManager.queryOrderID(MapUtils.getString(map, "out_order_id")), "order_id"), null, "name");
				map.put("prod_offer_code", prod_offer_code);
			}

			if (MapUtils.getString(map, "offer_eff_type") != null
					&& !MapUtils.getString(map, "offer_eff_type").equals("")) {
				String offer_eff_type = CommonDataFactory.getInstance().getDcPublicDataByPkey("offer_eff_type",
						MapUtils.getString(map, "offer_eff_type"));
				map.put("offer_eff_type", offer_eff_type);
			}

			if (MapUtils.getString(map, "certi_type") != null && !MapUtils.getString(map, "certi_type").equals("")) {
				String certi_type = ecsOrdManager.queryCertiType(MapUtils.getString(map, "certi_type"));
				map.put("certi_type", certi_type);
			}

			if (MapUtils.getString(map, "order_city_code") != null
					&& !MapUtils.getString(map, "order_city_code").equals("")) {
				String order_city_code = ecsOrdManager.queryCityCode(MapUtils.getString(map, "order_city_code"));
				map.put("order_city_code", order_city_code);
			}

			if (MapUtils.getString(map, "ship_city") != null && !MapUtils.getString(map, "ship_city").equals("")) {
				String ship_city = ecsOrdManager.queryCityCode(MapUtils.getString(map, "ship_city"));
				map.put("ship_city", ship_city);
			}

			if (MapUtils.getString(map, "ship_county") != null && !MapUtils.getString(map, "ship_county").equals("")) {
				String ship_county = ecsOrdManager.queryRegionCode(MapUtils.getString(map, "ship_county"));
				map.put("ship_county", ship_county);
			}

			String exception_desc = MapUtils
					.getString(ecsOrdManager.queryOrderID(MapUtils.getString(map, "out_order_id")), "exception_desc");
			if (exception_desc != null && !exception_desc.equals("")) {
				map.put("status", "失败");
				if (MapUtils.getString(map, "out_order_id") != null
						&& !"".equals(MapUtils.getString(map, "out_order_id"))) {
					map.put("exception_desc", MapUtils.getString(
							ecsOrdManager.queryOrderID(MapUtils.getString(map, "out_order_id")), "exception_desc"));
				}
			} else {
				map.put("status", "成功");
			}

			newData.add(map);
		}

		dataPage.setData(newData);

		this.webpage = dataPage;
		if (params == null) {
			params = new OrderQueryParams();
		}
		params.setBat_id(bat_id);
		// 导出数据
		if (StringUtils.isNotBlank(excel) && excel.equals(EcsOrderConsts.IS_IMPORT_YES)) {
			// 导出文件名
			String fileTitle = "导入订单明细";
			// 表列名
			String[] title = new String[] { "外系统单号", "用户号码", "是否实名", "业务类型", "用户姓名", "证件类型", "证件号码", "当前套餐", "地市",
					"订单来源", "新老用户", "首月", "支付方式", "商品分类", "商品名称", "是否副卡", "套餐名称", "合约期", "商城实收", "营业款", "收货人姓名",
					"收货人电话", "收货地址", "收货地市", "收货县分", "物流单号", "发票号码", "手机串号", "买家留言", "ICCID", "备注", "订单状态", "失败原因" };

			String[] content = { "out_order_id", "acc_nbr", "is_realname", "special_busi_type", "cust_name",
					"certi_type", "certi_num", "old_plan_title", "order_city_code", "source_from", "is_old",
					"offer_eff_type", "pay_method", "prod_offer_type", "prod_offer_code", "is_attached", "plan_title",
					"contract_month", "pay_money", "ess_money", "ship_name", "ship_phone", "ship_addr", "ship_city",
					"ship_county", "logi_no", "invoice_no", "terminal_num", "buyer_message", "iccid", "service_remarks",
					"status", "exception_desc" };

			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("webpage", this.webpage);// 将查询的page数据放入request对象
			List<Map> inputdate = ecsOrdManager.queryAllInputOrderInfoByBatID(textInfo);
			List<Map> otherData = new ArrayList<Map>();
			for (Map map : inputdate) {
				if (MapUtils.getString(map, "special_busi_type") != null
						&& !MapUtils.getString(map, "special_busi_type").equals("")) {
					String special_busi_type = CommonDataFactory.getInstance().getDcPublicDataByPkey(
							"DIC_SPECIAL_BUSINESS_TYPE", MapUtils.getString(map, "special_busi_type"));
					map.put("special_busi_type", special_busi_type);
				}

				if (MapUtils.getString(map, "is_realname") != null
						&& !MapUtils.getString(map, "is_realname").equals("")) {
					String is_realname = MapUtils.getString(map, "is_realname").equals("1") ? "是" : "否";
					map.put("is_realname", is_realname);
				}

				if (MapUtils.getString(map, "is_old") != null && !MapUtils.getString(map, "is_old").equals("")) {
					String is_old = MapUtils.getString(map, "is_old").equals("0") ? "新用户" : "老用户";
					map.put("is_old", is_old);
				}

				if (MapUtils.getString(map, "is_attached") != null
						&& !MapUtils.getString(map, "is_attached").equals("")) {
					String is_attached = MapUtils.getString(map, "is_attached").equals("0") ? "否" : "是";
					map.put("is_attached", is_attached);
				}

				if (MapUtils.getString(map, "pay_method") != null
						&& !MapUtils.getString(map, "pay_method").equals("")) {
					String pay_method = CommonDataFactory.getInstance().getDcPublicDataByPkey("pay_way",
							MapUtils.getString(map, "pay_method"));
					map.put("pay_method", pay_method);
				}

				if (MapUtils.getString(map, "source_from") != null
						&& !MapUtils.getString(map, "source_from").equals("")) {
					String source_from = CommonDataFactory.getInstance().getDcPublicDataByPkey("source_from",
							MapUtils.getString(map, "source_from"));
					map.put("source_from", source_from);
				}

				if (MapUtils.getString(map, "prod_offer_type") != null
						&& !MapUtils.getString(map, "prod_offer_type").equals("")) {
					String prod_offer_type = CommonDataFactory.getInstance()
							.getGoodSpec(MapUtils.getString(
									ecsOrdManager.queryOrderID(MapUtils.getString(map, "out_order_id")), "order_id"),
									null, "type_name");
					map.put("prod_offer_type", prod_offer_type);
				}

				if (MapUtils.getString(map, "prod_offer_code") != null
						&& !MapUtils.getString(map, "prod_offer_code").equals("")) {
					String prod_offer_code = CommonDataFactory.getInstance()
							.getGoodSpec(MapUtils.getString(
									ecsOrdManager.queryOrderID(MapUtils.getString(map, "out_order_id")), "order_id"),
									null, "name");
					map.put("prod_offer_code", prod_offer_code);
				}

				if (MapUtils.getString(map, "offer_eff_type") != null
						&& !MapUtils.getString(map, "offer_eff_type").equals("")) {
					String offer_eff_type = CommonDataFactory.getInstance().getDcPublicDataByPkey("offer_eff_type",
							MapUtils.getString(map, "offer_eff_type"));
					map.put("offer_eff_type", offer_eff_type);
				}

				if (MapUtils.getString(map, "certi_type") != null
						&& !MapUtils.getString(map, "certi_type").equals("")) {
					String certi_type = ecsOrdManager.queryCertiType(MapUtils.getString(map, "certi_type"));
					map.put("certi_type", certi_type);
				}

				if (MapUtils.getString(map, "order_city_code") != null
						&& !MapUtils.getString(map, "order_city_code").equals("")) {
					String order_city_code = ecsOrdManager.queryCityCode(MapUtils.getString(map, "order_city_code"));
					map.put("order_city_code", order_city_code);
				}

				if (MapUtils.getString(map, "ship_city") != null && !MapUtils.getString(map, "ship_city").equals("")) {
					String ship_city = ecsOrdManager.queryCityCode(MapUtils.getString(map, "ship_city"));
					map.put("ship_city", ship_city);
				}

				if (MapUtils.getString(map, "ship_county") != null
						&& !MapUtils.getString(map, "ship_county").equals("")) {
					String ship_county = ecsOrdManager.queryRegionCode(MapUtils.getString(map, "ship_county"));
					map.put("ship_county", ship_county);
				}

				String exception_desc = MapUtils.getString(
						ecsOrdManager.queryOrderID(MapUtils.getString(map, "out_order_id")), "exception_desc");
				if (exception_desc != null && !exception_desc.equals("")) {
					map.put("status", "失败");
					if (MapUtils.getString(map, "out_order_id") != null
							&& !"".equals(MapUtils.getString(map, "out_order_id"))) {
						map.put("exception_desc", MapUtils.getString(
								ecsOrdManager.queryOrderID(MapUtils.getString(map, "out_order_id")), "exception_desc"));
					}
				} else {
					map.put("status", "成功");
				}
				otherData.add(map);
			}
			DownLoadUtil.export(otherData, fileTitle, title, content, ServletActionContext.getResponse());// 导出工具开始导出数据。
			return null;
		} else {

		}
		return "inputOrderInfo";

	}

	/**
	 * 订单批量预处理
	 * 
	 * @return
	 */
	public String orderBatchPre() {
		StringBuffer strBuff = new StringBuffer();
		logger.info(DF.format(new Date()) + "#######orderBatchPreorderBatchPre" + order_ids);
		if (StringUtils.isNotEmpty(order_ids)) {
			String[] ordArray = order_ids.split(",");
			for (int i = 0; i < ordArray.length; i++) {
				String pre_order_id = ordArray[i];
				String lock_name = "";
				// final String lock_flow_deal =
				// OrderFlowAction.LOCK_ORDER_FLOW_DEAL+pre_order_id;
				try {
					lock_name = "O" + pre_order_id;
					Map lock = OrderFlowAction.getMutexLock(lock_name);
					// boolean lock_flag = cache.add(NAMESPACE,
					// lock_flow_deal,pre_order_id,300);
					// if(!lock_flag){
					// 已有缓存锁，有进程正在处理料箱队列
					// strBuff.append("订单" + pre_order_id + "正在处理,忽略;");
					// continue;
					// }
					logger.info(DF.format(new Date()) + "#######orderBatchPreorderBatchPre" + pre_order_id);
					synchronized (lock) {
						final AdminUser orderBatchUser = ManagerUtils.getAdminUser();
						String user_id = orderBatchUser.getUserid();
						if (StringUtils.isEmpty(user_id) || user_id.length() == 0) {
							strBuff.append("订单锁定人为空,预处理失败.");
							break;
						}
						OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(pre_order_id)
								.getOrderExtBusiRequest();
						String pre_flow_trace_id = orderExt.getFlow_trace_id();
						String pre_user_id = orderBatchUser.getUserid();
						// 记录最后一次页面操作预处理、预捡货的工号信息，供开户使用
						if (EcsOrderConsts.DIC_ORDER_NODE_B.equals(pre_flow_trace_id)) {
							CommonDataFactory.getInstance().updateAttrFieldValue(pre_order_id,
									new String[] { AttrConsts.PRE_LOCK_USER_ID }, new String[] { pre_user_id });
						} else {
							strBuff.append("订单").append(orderExt.getOut_tid()).append("不在审核环节.");
							continue;
						}
						logger.info(DF.format(new Date()) + "#######orderBatchPreorderBatchPre" + pre_order_id);
						// 插入es_order_hide表
						if (ordCollectManager.orderISHide(pre_order_id)) {
							// 已隐藏表示已在批量预处理 跳过
							continue;
						} else {
							// 隐藏
							ordCollectManager.setOrderHide(pre_order_id);
						}
						RuleTreeExeReq req = new RuleTreeExeReq();
						TacheFact fact = new TacheFact();
						fact.setOrder_id(pre_order_id);
						req.setFact(fact);
						TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(req) {
							public ZteResponse execute(ZteRequest zteRequest) {
								String my_order_id = "";
								IOrderFlowManager myOrderFlowManager;
								IOrdCollectManagerManager manager;
								try {
									RuleTreeExeReq exereq = (RuleTreeExeReq) zteRequest;
									TacheFact f = (TacheFact) exereq.getFact();
									my_order_id = f.getOrder_id();
									OrderExtBusiRequest orderExt = CommonDataFactory.getInstance()
											.getOrderTree(my_order_id).getOrderExtBusiRequest();
									if (!StringUtils.equals(EcsOrderConsts.DIC_ORDER_NODE_B,
											orderExt.getFlow_trace_id())) {
										logger.info("订单[" + my_order_id + "]不在审核环节,当前环节[" + orderExt.getFlow_trace_id()
												+ "],不需要预处理!");
										return null;
									}
									BusiCompRequest busiCompReq = new BusiCompRequest();
									busiCompReq.setOrder_id(my_order_id);
									Map queryParams = new HashMap();
									queryParams.put("order_id", my_order_id);
									queryParams.put("is_auto", EcsOrderConsts.RULE_EXE_ALL);
									busiCompReq.setQueryParams(queryParams);
									busiCompReq.setEnginePath("enterTraceRule.exec");
									BusiCompResponse busiCompResp = CommonDataFactory.getInstance()
											.execBusiComp(busiCompReq);
									BusiCompResponse busiResp = CommonDataFactory.getInstance()
											.getRuleTreeresult(busiCompResp);

									// 取消隐藏
									manager = SpringContextHolder.getBean("ordCollectManager");
									manager.cancelOrderHide(my_order_id);

									// 记录es_order_handle_logs日志
									logger.info(DF.format(new Date()) + "#######orderBatchPreorderBatchPre"
											+ busiResp.getError_code());
									if (ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())) {
										OrderHandleLogsReq logReq = new OrderHandleLogsReq();
										logReq.setOrder_id(my_order_id);
										logReq.setFlow_id(orderExt.getFlow_id());
										logReq.setFlow_trace_id(orderExt.getFlow_trace_id());
										logReq.setComments("批量预处理");
										logReq.setOp_id(orderBatchUser.getUserid());
										logReq.setOp_name(orderBatchUser.getUsername());
										myOrderFlowManager = SpringContextHolder.getBean("ordFlowManager");
										myOrderFlowManager.insertOrderHandLog(logReq);

										logger.info(DF.format(new Date()) + "#######orderBatchPreorderBatchPre解锁");
										// 解锁
										orderExt = CommonDataFactory.getInstance().getOrderTree(my_order_id)
												.getOrderExtBusiRequest();
										orderExt.setOrder_id(my_order_id);
										orderExt.setLock_status(EcsOrderConsts.UNLOCK_STATUS);
										orderExt.setLock_user_id(ConstsCore.NULL_VAL);
										orderExt.setLock_user_name(ConstsCore.NULL_VAL);
										orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
										orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
										orderExt.store();
									} else {// 如果处理失败，则向es_order_handle_logs插入日志，handle_type为BATCH_PREHANDLE_EXP
										OrderHandleLogsReq logReq = new OrderHandleLogsReq();
										logReq.setOrder_id(my_order_id);
										logReq.setFlow_id(orderExt.getFlow_id());
										logReq.setFlow_trace_id(orderExt.getFlow_trace_id());
										logReq.setComments("批量审核失败");
										logReq.setOp_id(orderBatchUser.getUserid());
										logReq.setOp_name(orderBatchUser.getUsername());
										logReq.setHandler_type(ZjEcsOrderConsts.BATCH_PREHANDLE_EXP);
										myOrderFlowManager = SpringContextHolder.getBean("ordFlowManager");
										myOrderFlowManager.insertOrderHandLog(logReq);
									}
								} catch (Exception e) {
									logger.info("订单[" + my_order_id + "]批量审核失败." + e.getMessage());
									e.printStackTrace();
								}
								return null;
							}
						});
						ThreadPoolFactory.getOrderThreadPoolExector().execute(taskThreadPool);
					}
				} catch (Exception e) {
					strBuff.append("订单[" + ordArray[i] + "]标记预处理失败!");
					logger.info("订单[" + ordArray[i] + "]标记预处理失败" + e.getMessage());
					e.printStackTrace();
				} finally {
					OrderFlowAction.removeMutexLock(lock_name);
					// cache.delete(NAMESPACE, lock_flow_deal);
				}
			}
		}
		if (null == strBuff || strBuff.length() == 0) {
			strBuff.append("处理成功.");
		}
		json = "{result:0," + "message:'" + strBuff.toString() + "'}";
		return JSON_MESSAGE;
	}

	/**
	 * 批量异常（批量预处理页面）
	 * 
	 * @return
	 */
	public String addBatchException_save() {
		if (StringUtils.isNotEmpty(order_ids)) {
			String[] ordArray = order_ids.split(",");
			Map<String, String> m = new HashMap<String, String>();
			AdminUser adminUser = ManagerUtils.getAdminUser();
			String user_id = adminUser.getUserid();
			String user_name = adminUser.getUsername();
			m.put("exception_id", exception_id);
			m.put("need_customer_visit", need_customer_visit);
			m.put("exception_remark", exception_remark);
			m.put("abnormal_type", abnormal_type);
			m.put("is_rg_exception", is_rg_exception);
			m.put("user_name", user_name);
			m.put("user_id", user_id);
			final String[] batchOrders = ordArray;
			final Map<String, String> map = m;
			try {
				ZteRequest req = null;
				TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(req) {
					public ZteResponse execute(ZteRequest zteRequest) {
						for (int i = 0; i < batchOrders.length; i++) {
							try {
								exception_id = map.get("exception_id");
								need_customer_visit = map.get("need_customer_visit");
								exception_remark = map.get("exception_remark");
								abnormal_type = map.get("abnormal_type");
								is_rg_exception = map.get("is_rg_exception");
								order_id = batchOrders[i];
								AdminUser importUser = new AdminUser();
								importUser.setUserid(map.get("user_id"));
								importUser.setUsername(map.get("user_name"));
								ManagerUtils.createSession(importUser);
								addException_save();
							} catch (Exception e) {
								logger.info("订单[" + order_id + "]标记异常失败." + e.getMessage());
								e.printStackTrace();
							}
						}
						return null;
					}
				});
				ThreadPoolFactory.getOrderThreadPoolExector().execute(taskThreadPool);
			} catch (Exception e) {
				logger.info(order_ids + "标记异常失败!" + e.getMessage());
				e.printStackTrace();
				json = "{result:1," + "message:'标志异常失败," + e.getMessage() + "'}";
				return JSON_MESSAGE;
			}
		}
		json = "{result:0," + "message:'标志异常成功'}";
		return JSON_MESSAGE;
	}

	public String getOrderInfoBySn() {
		try {
			String orderId = this.ecsOrdManager.getOrderInfoBySn(params.getOrder_sn(), params.getIccid());
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(orderId);
			if (orderTree == null) {
				this.json = "{result:2,message:'没有查询到订单！',order_id:'',flow_trace_id:''}";
			} else {
				this.json = "{result:0,message:'订单查询成功！',order_id:'" + orderTree.getOrder_id() + "',flow_trace_id:'"
						+ orderTree.getOrderExtBusiRequest().getFlow_trace_id() + "'}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.json = "{result:1,message:'" + e.getMessage() + "'}";
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 认证成功
	 * 
	 * @作者 刘权
	 * @创建日期 2017-3-11
	 * @return
	 */
	public String authSuccess() {
		String lockMsg = checkLockUser();
		if (!StringUtils.isEmpty(lockMsg)) {
			this.json = "{result:1,message:'" + lockMsg + "'}";
			return this.JSON_MESSAGE;
		}

		AdminUser user = ManagerUtils.getAdminUser();
		if (this.order_id == null && this.order_id.isEmpty()) {
			this.json = "{result:1,message:'缺少订单编号'}";
			return this.JSON_MESSAGE;
		}

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderRealNameInfoBusiRequest orderRealNameInfoBusiRequest = orderTree.getOrderRealNameInfoBusiRequest();
		// 激活状态 0:未激活 1:线下激活失败 2:线下激活成功 3:线上激活成功 4:人工认证中 5:人工认证失败
		String active_flag = orderRealNameInfoBusiRequest.getActive_flag();
		// 如果状态不是“人工认证中”或“人工认证失败”
		if (!"4".equals(active_flag) && !"5".equals(active_flag)) {
			this.json = "{result:1,message:'操作失败：当前订单激活状态不是“人工认证中”或“人工认证失败”无法进行“认证成功”操作，请核查订单激活状态'}";
		} else {
			// 调用用户激活接口业务逻辑处理
			UserActivationReq userRequ = new UserActivationReq();
			userRequ.setNotNeedReqStrOrderId(order_id);
			userRequ.setService_num(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM));// 联通服务号码
			// 调用激活接口
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			UserActivationResp resp = client.execute(userRequ, UserActivationResp.class);

			// 如果激活成功
			if ("00000".equals(resp.getCode())) {
				// 查询外呼前订单状态
				String order_status = ecsOrdManager.queryOrderStatusByorderId(order_id);
				// 更新订单外呼状态
				ecsOrdManager.updateEsOrderOutallLog(order_id);
				// 更新订单到外呼前的订单状态
				orderBusiObject = orderTree.getOrderBusiRequest();
				orderBusiObject.setStatus(Integer.valueOf(order_status));
				orderBusiObject.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderBusiObject.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderBusiObject.store();
				// 调用订单归档规则
				RuleTreeExeReq req = new RuleTreeExeReq();
				TacheFact fact = new TacheFact();
				fact.setOrder_id(order_id);
				req.setFact(fact);
				req.setRule_id(EcsOrderConsts.ORDER_PHOTO_RULE_ID);
				req.setCheckAllRelyOnRule(true);
				req.setCheckCurrRelyOnRule(true);
				ZteClient client1 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
				RuleTreeExeResp ruleResp = client1.execute(req, RuleTreeExeResp.class);
				// 如果订单归档规则调用失败
				if (!ConstsCore.ERROR_SUCC.equals(ruleResp.getError_code())) {
					logger.info("订单归档规则调用失败：" + ruleResp.getError_msg());
				}
				// 更新实名制信息表的激活状态为 2:线下激活成功
				orderRealNameInfoBusiRequest.setActive_flag("2");// 0:未激活
																	// 2:线下激活成功
																	// 3:线上激活成功
																	// 4:人工认证中
																	// 5:人工认证失败
				orderRealNameInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderRealNameInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderRealNameInfoBusiRequest.store();
				// 解锁
				orderExtBusi = orderTree.getOrderExtBusiRequest();
				String trace_id = orderExtBusi.getFlow_trace_id();
				List<OrderLockBusiRequest> orderLockBusiRequestList = orderTree.getOrderLockBusiRequests();
				for (int i = 0; i < orderLockBusiRequestList.size(); i++) {
					OrderLockBusiRequest orderLockBusiRequest = orderLockBusiRequestList.get(i);
					if (trace_id.equals(orderLockBusiRequest.getTache_code())) {
						orderLockBusiRequest.setLock_user_id(ConstsCore.NULL_VAL);
						orderLockBusiRequest.setLock_user_name(ConstsCore.NULL_VAL);
						orderLockBusiRequest.setLock_status(EcsOrderConsts.UNLOCK_STATUS);
						orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						orderLockBusiRequest.store();
					}
				}
				this.json = "{result:1,message:'激活成功'}";
			} else {
				this.json = "{result:0,message:'激活失败：" + resp.getMsg() + "'}";
			}
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 认证失败
	 * 
	 * @作者 刘权
	 * @创建日期 2017-3-11
	 * @return
	 */
	public String authFail() {
		String lockMsg = checkLockUser();
		if (!StringUtils.isEmpty(lockMsg)) {
			this.json = "{result:1,message:'" + lockMsg + "'}";
			return this.JSON_MESSAGE;
		}

		AdminUser user = ManagerUtils.getAdminUser();
		if (this.order_id == null && this.order_id.isEmpty()) {
			this.json = "{result:1,message:'缺少订单编号'}";
			return this.JSON_MESSAGE;
		}

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderRealNameInfoBusiRequest orderRealNameInfoBusiRequest = orderTree.getOrderRealNameInfoBusiRequest();
		// 激活状态 0:未激活 1:线下激活失败 2:线下激活成功 3:线上激活成功 4:人工认证中 5:人工认证失败
		String active_flag = orderRealNameInfoBusiRequest.getActive_flag();
		// 如果状态不是“人工认证中”
		if (!"4".equals(active_flag)) {
			this.json = "{result:1,message:'操作失败：当前订单激活状态不是“人工认证中”无法进行“认证失败”操作，请核查订单激活状态'}";
		} else {
			// 查询外呼前订单状态
			String order_status = ecsOrdManager.queryOrderStatusByorderId(order_id);
			// 更新订单外呼状态
			ecsOrdManager.updateEsOrderOutallLog(order_id);
			// 更新订单到外呼前的订单状态
			orderBusiObject = orderTree.getOrderBusiRequest();
			orderBusiObject.setStatus(Integer.valueOf(order_status));
			orderBusiObject.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderBusiObject.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderBusiObject.store();
			// 更新实名制信息表的激活状态为 5:人工认证失败
			orderRealNameInfoBusiRequest.setActive_flag("5");// 0:未激活 2:线下激活成功
																// 3:线上激活成功
																// 4:人工认证中
																// 5:人工认证失败
			orderRealNameInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderRealNameInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderRealNameInfoBusiRequest.store();
			this.json = "{result:1,message:'操作成功'}";
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 后激活退单
	 * 
	 * @作者 刘权
	 * @创建日期 2017-3-11
	 * @return
	 */
	public String authBack() {
		String lockMsg = checkLockUser();
		if (!StringUtils.isEmpty(lockMsg)) {
			this.json = "{result:1,message:'" + lockMsg + "'}";
			return this.JSON_MESSAGE;
		}

		AdminUser user = ManagerUtils.getAdminUser();
		if (this.order_id == null && this.order_id.isEmpty()) {
			this.json = "{result:1,message:'缺少订单编号'}";
			return this.JSON_MESSAGE;
		}
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		// 更新订单到退单状态
		orderBusiObject = orderTree.getOrderBusiRequest();
		int order_status = orderBusiObject.getStatus();
		if (order_status == ZjEcsOrderConsts.DIC_ORDER_STATUS_12) {
			this.json = "{result:1,message:'操作失败：当前订单已经“退单中”，请勿重复操作'}";
		} else {
			// 更新订单状态为退单中
			orderBusiObject.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_12);
			orderBusiObject.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderBusiObject.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderBusiObject.store();
			// 更新订单外呼处理状态
			ecsOrdManager.updateEsOrderOutallLog(order_id);
			// 解锁
			orderExtBusi = orderTree.getOrderExtBusiRequest();
			String trace_id = orderExtBusi.getFlow_trace_id();
			List<OrderLockBusiRequest> orderLockBusiRequestList = orderTree.getOrderLockBusiRequests();
			for (int i = 0; i < orderLockBusiRequestList.size(); i++) {
				OrderLockBusiRequest orderLockBusiRequest = orderLockBusiRequestList.get(i);
				if (trace_id.equals(orderLockBusiRequest.getTache_code())) {
					orderLockBusiRequest.setLock_user_id(ConstsCore.NULL_VAL);
					orderLockBusiRequest.setLock_user_name(ConstsCore.NULL_VAL);
					orderLockBusiRequest.setLock_status(EcsOrderConsts.UNLOCK_STATUS);
					orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					orderLockBusiRequest.store();
				}
			}
			this.json = "{result:1,message:'操作成功'}";
		}

		return this.JSON_MESSAGE;
	}

	/**
	 * 
	 * 顺丰物流单号查询
	 * 
	 * @return
	 */
	public String getSFlogiOrder() {
		try {

			// PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
			// TacheFact fact = new TacheFact();
			// fact.setOrder_id("TBFX172252255117000024");
			// req.setPlan_id(EcsOrderConsts.ORDER_ARCHIVE_PLAN);
			// req.setFact(fact);
			// req.setDeleteLogs(false);//不删除日志，不允许二次操作
			// req.setAuto_exe(-1);
			// PlanRuleTreeExeResp planResp =
			// CommonDataFactory.getInstance().exePlanRuleTree(req);

			// long start=System.currentTimeMillis();//获取时间
			// if(params==null){
			// params = new OrderQueryParams();
			// }
			// //订单ID,out_id,phone_num,delivery_id都为空的话，默认放入一个查询的10天以内的时间
			// if(StringUtil.isEmpty(params.getOut_tid()) &&
			// StringUtil.isEmpty(params.getOrder_id()) &&
			// StringUtil.isEmpty(params.getPhone_num()) &&
			// StringUtil.isEmpty(params.getDelivery_id())){
			// Calendar date = new GregorianCalendar();
			// date.add(Calendar.DAY_OF_MONTH, -10);
			// String startTime = DF2.format(date.getTime());
			// String endTime = DF2.format(new Date());
			// if(StringUtil.isEmpty(params.getCreate_start())){
			// params.setCreate_start(startTime);
			// }
			// if(StringUtil.isEmpty(params.getCreate_end())){
			// params.setCreate_end(endTime);
			// }
			// //对日期进行格式化
			// params.setCreate_start(params.getCreate_start()+" 00:00:00");
			// params.setCreate_end(params.getCreate_end()+" 23:59:59");
			// }
			logger.info(params);
			this.webpage = ecsOrdManager.querySFlogiOrder(this.getPage(), this.getPageSize(), params);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "order_sfLogi";

	}

	/**
	 * 总商爬虫刷新爬虫配置方法
	 * 
	 * @return
	 */
	public String updateProperties() {

		CrawlerUpdatePropertiesReq req = new CrawlerUpdatePropertiesReq();

		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		CrawlerResp resp = client.execute(req, CrawlerResp.class);
		this.json = "{'result':" + resp.getResp_code() + ",'msg':'" + resp.getResp_msg() + "','return_data':{}}";

		return JSON_MESSAGE;

	}

	public String getLockOrderIdStr() {
		return lockOrderIdStr;
	}

	public void setLockOrderIdStr(String lockOrderIdStr) {
		this.lockOrderIdStr = lockOrderIdStr;
	}

	public String getLockDealType() {
		return lockDealType;
	}

	public void setLockDealType(String lockDealType) {
		this.lockDealType = lockDealType;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getBtn_type() {
		return btn_type;
	}

	public void setBtn_type(String btn_type) {
		this.btn_type = btn_type;
	}

	public List<OrderBtn> getOrdBtns() {
		return ordBtns;
	}

	public void setOrdBtns(List<OrderBtn> ordBtns) {
		this.ordBtns = ordBtns;
	}

	public List<RuleConfig> getPlanRuleTreeList() {
		return planRuleTreeList;
	}

	public void setPlanRuleTreeList(List<RuleConfig> planRuleTreeList) {
		this.planRuleTreeList = planRuleTreeList;
	}

	public String getRule_id() {
		return rule_id;
	}

	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}

	public String getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

	public int getExeRelyOnRule() {
		return exeRelyOnRule;
	}

	public void setExeRelyOnRule(int exeRelyOnRule) {
		this.exeRelyOnRule = exeRelyOnRule;
	}

	public OrderQueryParams getParams() {
		return params;
	}

	public void setParams(OrderQueryParams params) {
		this.params = params;
	}

	public List<Regions> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<Regions> regionList) {
		this.regionList = regionList;
	}

	public List<Map> getOrder_from_list() {
		return order_from_list;
	}

	public void setOrder_from_list(List<Map> order_from_list) {
		this.order_from_list = order_from_list;
	}

	public List<Map> getOrder_vplan_list() {
		return order_vplan_list;
	}

	public void setOrder_vplan_list(List<Map> order_vplan_list) {
		this.order_vplan_list = order_vplan_list;
	}

	public List<Map> getOrder_role_list() {
		return order_role_list;
	}

	public void setOrder_role_list(List<Map> order_role_list) {
		this.order_role_list = order_role_list;
	}

	public String getOrder_role() {
		return order_role;
	}

	public void setOrder_role(String order_role) {
		this.order_role = order_role;
	}

	public List<Map> getPlat_type_list() {
		return plat_type_list;
	}

	public void setPlat_type_list(List<Map> plat_type_list) {
		this.plat_type_list = plat_type_list;
	}

	public List<Map> getChannel_mark_list() {
		return channel_mark_list;
	}

	public void setChannel_mark_list(List<Map> channel_mark_list) {
		this.channel_mark_list = channel_mark_list;
	}

	public List<Map> getQuick_status_list() {
		return quick_status_list;
	}

	public void setQuick_status_list(List<Map> quick_status_list) {
		this.quick_status_list = quick_status_list;
	}

	public List<Map> getShipping_type_list() {
		return shipping_type_list;
	}

	public void setShipping_type_list(List<Map> shipping_type_list) {
		this.shipping_type_list = shipping_type_list;
	}

	public List<Map> getIs_quick_ship_list() {
		return is_quick_ship_list;
	}

	public void setIs_quick_ship_list(List<Map> is_quick_ship_list) {
		this.is_quick_ship_list = is_quick_ship_list;
	}

	public List<Map> getPay_type_list() {
		return pay_type_list;
	}

	public void setPay_type_list(List<Map> pay_type_list) {
		this.pay_type_list = pay_type_list;
	}

	public OrderTreeBusiRequest getOrderTree() {
		return orderTree;
	}

	public void setOrderTree(OrderTreeBusiRequest orderTree) {
		this.orderTree = orderTree;
	}

	public List<Map> getDeal_status() {
		return deal_status;
	}

	public void setDeal_status(List<Map> deal_status) {
		this.deal_status = deal_status;
	}

	public List<Map> getShipping_cop_list() {
		return shipping_cop_list;
	}

	public void setShipping_cop_list(List<Map> shipping_cop_list) {
		this.shipping_cop_list = shipping_cop_list;
	}

	public String getPending_reason() {
		return pending_reason;
	}

	public void setPending_reason(String pending_reason) {
		this.pending_reason = pending_reason;
	}

	public List<Map> getExceptionList() {
		return exceptionList;
	}

	public void setExceptionList(List<Map> exceptionList) {
		this.exceptionList = exceptionList;
	}

	public String getException_id() {
		return exception_id;
	}

	public void setException_id(String exception_id) {
		this.exception_id = exception_id;
	}

	public String getException_remark() {
		return exception_remark;
	}

	public void setException_remark(String exception_remark) {
		this.exception_remark = exception_remark;
	}

	public String getAbnormal_type() {
		return abnormal_type;
	}

	public void setAbnormal_type(String abnormal_type) {
		this.abnormal_type = abnormal_type;
	}
	
	
	
	public String getOrder_deal_method() {
		return order_deal_method;
	}

	public void setOrder_deal_method(String order_deal_method) {
		this.order_deal_method = order_deal_method;
	}

	public OrdExceptionHandleImpl getOrdExceptionHandleImpl() {
		return ordExceptionHandleImpl;
	}

	public void setOrdExceptionHandleImpl(OrdExceptionHandleImpl ordExceptionHandleImpl) {
		this.ordExceptionHandleImpl = ordExceptionHandleImpl;
	}

	public List<Map> getFlowTraceList() {
		return flowTraceList;
	}

	public void setFlowTraceList(List<Map> flowTraceList) {
		this.flowTraceList = flowTraceList;
	}

	public List<Map> getOrderExceptionTypeList() {
		return orderExceptionTypeList;
	}

	public void setOrderExceptionTypeList(List<Map> orderExceptionTypeList) {
		this.orderExceptionTypeList = orderExceptionTypeList;
	}

	public String getTrace_id() {
		return trace_id;
	}

	public void setTrace_id(String trace_id) {
		this.trace_id = trace_id;
	}

	public String getObj_name() {
		return obj_name;
	}

	public void setObj_name(String obj_name) {
		this.obj_name = obj_name;
	}

	public IRuleService getRuleService() {
		return ruleService;
	}

	public void setRuleService(IRuleService ruleService) {
		this.ruleService = ruleService;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMode_name() {
		return mode_name;
	}

	public void setMode_name(String mode_name) {
		this.mode_name = mode_name;
	}

	public OrderDeliveryBusiRequest getDelivery() {
		return delivery;
	}

	public void setDelivery(OrderDeliveryBusiRequest delivery) {
		this.delivery = delivery;
	}

	public String getShipping_cop_name() {
		return shipping_cop_name;
	}

	public void setShipping_cop_name(String shipping_cop_name) {
		this.shipping_cop_name = shipping_cop_name;
	}

	public String getDeal_type() {
		return deal_type;
	}

	public void setDeal_type(String deal_type) {
		this.deal_type = deal_type;
	}

	public String getQuick_shipping_cop() {
		return quick_shipping_cop;
	}

	public void setQuick_shipping_cop(String quick_shipping_cop) {
		this.quick_shipping_cop = quick_shipping_cop;
	}

	public String getQuick_shipping_status() {
		return quick_shipping_status;
	}

	public void setQuick_shipping_status(String quick_shipping_status) {
		this.quick_shipping_status = quick_shipping_status;
	}

	public String getReissue_info() {
		return reissue_info;
	}

	public void setReissue_info(String reissue_info) {
		this.reissue_info = reissue_info;
	}

	public String getReissue_id() {
		return reissue_id;
	}

	public void setReissue_id(String reissue_id) {
		this.reissue_id = reissue_id;
	}

	public List getProvList() {
		return provList;
	}

	public void setProvList(List provList) {
		this.provList = provList;
	}

	public List getCityList() {
		return cityList;
	}

	public void setCityList(List cityList) {
		this.cityList = cityList;
	}

	public List getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List districtList) {
		this.districtList = districtList;
	}

	public IOrderFlowManager getOrdFlowManager() {
		return ordFlowManager;
	}

	public void setOrdFlowManager(IOrderFlowManager ordFlowManager) {
		this.ordFlowManager = ordFlowManager;
	}

	public IEcsOrdManager getEcsOrdManager() {
		return ecsOrdManager;
	}

	public void setEcsOrdManager(IEcsOrdManager ecsOrdManager) {
		this.ecsOrdManager = ecsOrdManager;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Long getProvinc_code() {
		return provinc_code;
	}

	public void setProvinc_code(Long provinc_code) {
		this.provinc_code = provinc_code;
	}

	public Long getCity_code() {
		return city_code;
	}

	public void setCity_code(Long city_code) {
		this.city_code = city_code;
	}

	public Long getDistrict_id() {
		return district_id;
	}

	public void setDistrict_id(Long district_id) {
		this.district_id = district_id;
	}

	public String getReissue_num() {
		return reissue_num;
	}

	public void setReissue_num(String reissue_num) {
		if (StringUtils.isNotBlank(reissue_num)) {
			this.reissue_num = reissue_num;
		} else {
			this.reissue_num = "0";
		}
	}

	public List<Logi> getLogiCompanyList() {
		return logiCompanyList;
	}

	public void setLogiCompanyList(List<Logi> logiCompanyList) {
		this.logiCompanyList = logiCompanyList;
	}

	public String getLogiCompanyCode() {
		return logiCompanyCode;
	}

	public String getFlowCode() {
		return flowCode;
	}

	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}

	public void setLogiCompanyCode(String logiCompanyCode) {
		this.logiCompanyCode = logiCompanyCode;
	}

	public String getLogi_post_id() {
		return logi_post_id;
	}

	public void setLogi_post_id(String logi_post_id) {
		this.logi_post_id = logi_post_id;
	}

	public List<Map> getLogiCompanyRegionsList() {
		return logiCompanyRegionsList;
	}

	public void setLogiCompanyRegionsList(List<Map> logiCompanyRegionsList) {
		this.logiCompanyRegionsList = logiCompanyRegionsList;
	}

	public String getLogi_no() {
		return logi_no;
	}

	public void setLogi_no(String logi_no) {
		this.logi_no = logi_no;
	}

	public String getLogi_receiver_phone() {
		return logi_receiver_phone;
	}

	public void setLogi_receiver_phone(String logi_receiver_phone) {
		this.logi_receiver_phone = logi_receiver_phone;
	}

	public String getLogi_receiver() {
		return logi_receiver;
	}

	public void setLogi_receiver(String logi_receiver) {
		this.logi_receiver = logi_receiver;
	}

	public List<Map> getDic_material_retrieve() {
		return dic_material_retrieve;
	}

	public void setDic_material_retrieve(List<Map> dic_material_retrieve) {
		this.dic_material_retrieve = dic_material_retrieve;
	}

	public OrdReceipt getOrdReceipt() {
		return ordReceipt;
	}

	public void setOrdReceipt(OrdReceipt ordReceipt) {
		this.ordReceipt = ordReceipt;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getDealDesc() {
		return dealDesc;
	}

	public void setDealDesc(String dealDesc) {
		this.dealDesc = dealDesc;
	}

	public String getQuery_type() {
		return query_type;
	}

	public void setQuery_type(String query_type) {
		this.query_type = query_type;
	}

	public String getIs_return_back() {
		return is_return_back;
	}

	public void setIs_return_back(String is_return_back) {
		this.is_return_back = is_return_back;
	}

	public List<Map> getExceptionTypeList() {
		return exceptionTypeList;
	}

	public void setExceptionTypeList(List<Map> exceptionTypeList) {
		this.exceptionTypeList = exceptionTypeList;
	}

	public DeliveryItemsQueryResp getDeliveryItemsQueryResp() {
		return deliveryItemsQueryResp;
	}

	public void setDeliveryItemsQueryResp(DeliveryItemsQueryResp deliveryItemsQueryResp) {
		this.deliveryItemsQueryResp = deliveryItemsQueryResp;
	}

	public List<OrderHandleLogsReq> getLogsList() {
		return logsList;
	}

	public void setLogsList(List<OrderHandleLogsReq> logsList) {
		this.logsList = logsList;
	}

	public List<Map> getDc_MODE_REGIONList() {
		return dc_MODE_REGIONList;
	}

	public void setDc_MODE_REGIONList(List<Map> dc_MODE_REGIONList) {
		this.dc_MODE_REGIONList = dc_MODE_REGIONList;
	}

	public IOrderServices getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(IOrderServices orderServices) {
		this.orderServices = orderServices;
	}

	public List<EntityInfoVO> getEntityInfoList() {
		return entityInfoList;
	}

	public void setEntityInfoList(List<EntityInfoVO> entityInfoList) {
		this.entityInfoList = entityInfoList;
	}

	public String getSerial_num() {
		return serial_num;
	}

	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}

	public AutoOrderTree getAutoOrderTree() {
		return autoOrderTree;
	}

	public void setAutoOrderTree(AutoOrderTree autoOrderTree) {
		this.autoOrderTree = autoOrderTree;
	}

	public String getEntity_quality_state() {
		return entity_quality_state;
	}

	public void setEntity_quality_state(String entity_quality_state) {
		this.entity_quality_state = entity_quality_state;
	}

	public String getMaterial_retrieve() {
		return material_retrieve;
	}

	public void setMaterial_retrieve(String material_retrieve) {
		this.material_retrieve = material_retrieve;
	}

	public List<Map> getIs_order_his_list() {
		return is_order_his_list;
	}

	public void setIs_order_his_list(List<Map> is_order_his_list) {
		this.is_order_his_list = is_order_his_list;
	}

	public List<Map> getOrder_supply_status_list() {
		return order_supply_status_list;
	}

	public void setOrder_supply_status_list(List<Map> order_supply_status_list) {
		this.order_supply_status_list = order_supply_status_list;
	}

	public String getIs_his_order() {
		return is_his_order;
	}

	public void setIs_his_order(String is_his_order) {
		this.is_his_order = is_his_order;
	}

	public List<OrderExceptionCollectReq> getExceptionLogsList() {
		return exceptionLogsList;
	}

	public void setExceptionLogsList(List<OrderExceptionCollectReq> exceptionLogsList) {
		this.exceptionLogsList = exceptionLogsList;
	}

	public String getOrder_ids() {
		return order_ids;
	}

	public void setOrder_ids(String order_ids) {
		this.order_ids = order_ids;
	}

	public String getIs_order_view() {
		return is_order_view;
	}

	public void setIs_order_view(String is_order_view) {
		this.is_order_view = is_order_view;
	}

	public String getBoxCode() {
		return boxCode;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}

	public List<Map> getOrder_model_list() {
		return order_model_list;
	}

	public void setOrder_model_list(List<Map> order_model_list) {
		this.order_model_list = order_model_list;
	}

	public String getIs_rg_exception() {
		return is_rg_exception;
	}

	public void setIs_rg_exception(String is_rg_exception) {
		this.is_rg_exception = is_rg_exception;
	}

	public String getCheck_type() {
		return check_type;
	}

	public void setCheck_type(String check_type) {
		this.check_type = check_type;
	}

	public List<SDSStatusLogVO> getSdsLogsList() {
		return sdsLogsList;
	}

	public void setSdsLogsList(List<SDSStatusLogVO> sdsLogsList) {
		this.sdsLogsList = sdsLogsList;
	}

	public String getShipping_cop_id() {
		return shipping_cop_id;
	}

	public void setShipping_cop_id(String shipping_cop_id) {
		this.shipping_cop_id = shipping_cop_id;
	}

	public String getRelease_id() {
		return release_id;
	}

	public void setRelease_id(String release_id) {
		this.release_id = release_id;
	}

	public List<OrderItemExtvtlBusiRequest> getOrderItemExtvtlBusiRequests() {
		return orderItemExtvtlBusiRequests;
	}

	public void setOrderItemExtvtlBusiRequests(List<OrderItemExtvtlBusiRequest> orderItemExtvtlBusiRequests) {
		this.orderItemExtvtlBusiRequests = orderItemExtvtlBusiRequests;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getTerminal_nums() {
		return terminal_nums;
	}

	public void setTerminal_nums(String terminal_nums) {
		this.terminal_nums = terminal_nums;
	}

	public String getEss_emp_id() {
		return ess_emp_id;
	}

	public void setEss_emp_id(String ess_emp_id) {
		this.ess_emp_id = ess_emp_id;
	}

	public List<EmpOperInfoVo> getEmpOperInfos() {
		return empOperInfos;
	}

	public void setEmpOperInfos(List<EmpOperInfoVo> empOperInfos) {
		this.empOperInfos = empOperInfos;
	}

	public int getEmpOperInfosSize() {
		return empOperInfosSize;
	}

	public void setEmpOperInfosSize(int empOperInfosSize) {
		this.empOperInfosSize = empOperInfosSize;
	}

	public String getPlatform_locked_status() {
		return platform_locked_status;
	}

	public void setPlatform_locked_status(String platform_locked_status) {
		this.platform_locked_status = platform_locked_status;
	}

	public List<Map> getRefund_satus_list() {
		return refund_satus_list;
	}

	public void setRefund_satus_list(List<Map> refund_satus_list) {
		this.refund_satus_list = refund_satus_list;
	}

	public List<Map> getBss_refund_satus_list() {
		return bss_refund_satus_list;
	}

	public void setBss_refund_satus_list(List<Map> bss_refund_satus_list) {
		this.bss_refund_satus_list = bss_refund_satus_list;
	}

	public String getGood_price_system() {
		return good_price_system;
	}

	public void setGood_price_system(String good_price_system) {
		this.good_price_system = good_price_system;
	}

	public String getNum_price() {
		return num_price;
	}

	public void setNum_price(String num_price) {
		this.num_price = num_price;
	}

	public String getDeposit_price() {
		return deposit_price;
	}

	public void setDeposit_price(String deposit_price) {
		this.deposit_price = deposit_price;
	}

	public String getOpenAcc_price() {
		return openAcc_price;
	}

	public void setOpenAcc_price(String openAcc_price) {
		this.openAcc_price = openAcc_price;
	}

	private void getPrice(OrderTreeBusiRequest orderTree) {
		for (AttrFeeInfoBusiRequest feevo : orderTree.getFeeInfoBusiRequests()) {
			// if(StringUtils.equals(feevo.getFee_item_code(),
			// EcsOrderConsts.NUMYCK_FEE_ITEM_ID)){
			// this.num_price = feevo.getN_fee_num()+"";
			// }
			if (StringUtils.equals(feevo.getFee_item_code(), EcsOrderConsts.DJYCK_FEE_ITEM_ID)) {
				this.deposit_price = feevo.getN_fee_num() + "";
			}
			if (StringUtils.equals(feevo.getFee_item_code(), EcsOrderConsts.OPEN_ACC_FEE_ITEM_ID)) {
				this.openAcc_price = feevo.getN_fee_num() + "";
			}
		}
		if (orderTree.getPhoneInfoBusiRequest() != null
				&& !StringUtils.equals(EcsOrderConsts.PHONE_CLASS_ID_9,
						orderTree.getPhoneInfoBusiRequest().getClassId() + "")
				&& !StringUtils.isEmpty(orderTree.getPhoneInfoBusiRequest().getAdvancePay())) {
			this.num_price = (Double.parseDouble(orderTree.getPhoneInfoBusiRequest().getAdvancePay()) / 1000) + "";
		} else {
			this.num_price = "0";
		}
		this.good_price_system = CommonDataFactory.getInstance().getGoodSpec(order_id, null, SpecConsts.PRICE);
	}

	public String getOrderReceiveList() {
		if (params == null) {
			params = new OrderQueryParams();
			// if(!EcsOrdManager.QUERY_TYPE_EXCEPTION.equals(query_type)){//异常订单管理页面,“订单类型”改为默认“请选择”；
			// params.setOrder_type(EcsOrderConsts.ORDER_TYPE_03);//默认订购
			// }
		}
		countyList = ordWorkManager.getIntentCountyList(params.getOrder_city_code());
		// 照片上传状态
		order_vplan_list = ecsOrdManager.getDcSqlByDcName("photo_up_status");
		/*
		 * this.listRegions(); this.listChannelMark(); this.listIsQuickShip();
		 * this.listOrderFrom(); this.listPlatType(); this.listPayType();
		 * this.listQuickStatus(); this.listShippingType(); this.listShiipingCop();
		 * this.listDealStatus(); this.listExceptionType(); this.listFlowTrace();
		 * this.listExceptionTypeList(); this.listDcModeRegion();
		 * this.listOrderListIsHis(); this.listOrderSupplyStatus();
		 * this.listorderModel(); this.listWMSRefundStatusList();
		 * this.listRefundStatusList(); this.listBssRefundStatusList();
		 * this.listOrderTypeList(); this.listOrderQryStatus();
		 */
		// if (!StringUtils.isEmpty(lan_id)) {
		// String[] lan_ids = lan_id.split(",");
		// lan_id = lan_ids[lan_ids.length - 1];
		// lan_id = lan_id.replace(" ", "");
		// params.setOrder_city_code(lan_id);
		// }
		//
		// if (!StringUtils.isEmpty(order_county_name)) {
		// String[] order_county_names = order_county_name.split(",");
		// lan_id = order_county_names[order_county_names.length - 1];
		// order_county_name = order_county_name.replace(" ", "");
		// params.setOrder_county_code(order_county_name);
		// }
		this.listRegions();
		this.listOrderFrom();
		this.listFlowTrace();
		if (!"yes".equals(first_load)) {// 首次加载页面不做查询，当前仅归档恢复页面用到，其他页面要使用可在es_menu配置参数
			try {
				// this.webpage =
				// ecsOrdManager.queryOrderReceiveList(this.getPage(),
				// this.getPageSize(), params);
				this.webpage = ecsOrdManager.queryOrderReceiveList(this.getPage(), this.getPageSize(), params);
				long end1 = System.currentTimeMillis();
				// logger.info("记录查询返回时间=========================================================>"+(end1-end));
			} catch (Exception e) {
				e.printStackTrace();
				String msg = e.getMessage();
			}
		}
		return "auto_ord_receive2";
	}

	public String importOrder() {
		return "orderInput_import";
	}

	public List<OrderPhoneInfoFukaBusiRequest> getOrderPhoneInfoFukaList() {
		return orderPhoneInfoFukaList;
	}

	public void setOrderPhoneInfoFukaList(List<OrderPhoneInfoFukaBusiRequest> orderPhoneInfoFukaList) {
		this.orderPhoneInfoFukaList = orderPhoneInfoFukaList;
	}

	public List<OrderSubPackageList> getSubProductList() {
		return subProductList;
	}

	public void setSubProductList(List<OrderSubPackageList> subProductList) {
		this.subProductList = subProductList;
	}

	public List<AttrGiftInfoBusiRequest> getGiftInfoList() {
		return giftInfoList;
	}

	public void setGiftInfoList(List<AttrGiftInfoBusiRequest> giftInfoList) {
		this.giftInfoList = giftInfoList;
	}

	public int getGiftInfoSize() {
		return giftInfoSize;
	}

	public void setGiftInfoSize(int giftInfoSize) {
		this.giftInfoSize = giftInfoSize;
	}

	public String getBoxCodeTest() {
		return boxCodeTest;
	}

	public void setBoxCodeTest(String boxCodeTest) {
		this.boxCodeTest = boxCodeTest;
	}

	public List<Map> getIs_or_no_list() {
		return is_or_no_list;
	}

	public void setIs_or_no_list(List<Map> is_or_no_list) {
		this.is_or_no_list = is_or_no_list;
	}

	public String getNeed_customer_visit() {
		return need_customer_visit;
	}

	public void setNeed_customer_visit(String need_customer_visit) {
		this.need_customer_visit = need_customer_visit;
	}

	public String getFirst_load() {
		return first_load;
	}

	public void setFirst_load(String first_load) {
		this.first_load = first_load;
	}

	public List<Map> getOrder_type_list() {
		return order_type_list;
	}

	public void setOrder_type_list(List<Map> order_type_list) {
		this.order_type_list = order_type_list;
	}

	public String getSms_phone() {
		return sms_phone;
	}

	public void setSms_phone(String sms_phone) {
		this.sms_phone = sms_phone;
	}

	public String getSms_content() {
		return sms_content;
	}

	public void setSms_content(String sms_content) {
		this.sms_content = sms_content;
	}

	public String getUserparams() {
		return userparams;
	}

	public void setUserparams(String userparams) {
		this.userparams = userparams;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getLog_id() {
		return log_id;
	}

	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}

	public Map<String, String> getManualOrder() {
		return manualOrder;
	}

	public void setManualOrder(Map<String, String> manualOrder) {
		this.manualOrder = manualOrder;
	}

	public String getOrder_sn() {
		return order_sn;
	}

	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	public OrderBusiRequest getOrderBusiObject() {
		return orderBusiObject;
	}

	public void setOrderBusiObject(OrderBusiRequest orderBusiObject) {
		this.orderBusiObject = orderBusiObject;
	}

	public String getOutcall_type() {
		return outcall_type;
	}

	public void setOutcall_type(String outcall_type) {
		this.outcall_type = outcall_type;
	}

	public String getOutcall_reason() {
		return outcall_reason;
	}

	public void setOutcall_reason(String outcall_reason) {
		this.outcall_reason = outcall_reason;
	}

	public OrderExtBusiRequest getOrderExtBusi() {
		return orderExtBusi;
	}

	public void setOrderExtBusi(OrderExtBusiRequest orderExtBusi) {
		this.orderExtBusi = orderExtBusi;
	}

	public String getSource_system() {
		return source_system;
	}

	public void setSource_system(String source_system) {
		this.source_system = source_system;
	}

	public List<Map> getOrder_status_list() {
		return order_status_list;
	}

	public void setOrder_status_list(List<Map> order_status_list) {
		this.order_status_list = order_status_list;
	}

	public List<Map> getOrder_src_list() {
		return order_src_list;
	}

	public void setOrder_src_list(List<Map> order_src_list) {
		this.order_src_list = order_src_list;
	}

	public List<Map> getData_src_list() {
		return data_src_list;
	}

	public void setData_src_list(List<Map> data_src_list) {
		this.data_src_list = data_src_list;
	}

	public List<Map> getArea_id_list() {
		return area_id_list;
	}

	public void setArea_id_list(List<Map> area_id_list) {
		this.area_id_list = area_id_list;
	}

	public List<Map> getExpress_type_list() {
		return express_type_list;
	}

	public void setExpress_type_list(List<Map> express_type_list) {
		this.express_type_list = express_type_list;
	}

	public String getReceive_num() {
		return receive_num;
	}

	public void setReceive_num(String receive_num) {
		this.receive_num = receive_num;
	}

	public String getException_from() {
		return exception_from;
	}

	public void setException_from(String exception_from) {
		this.exception_from = exception_from;
	}

	public List<Map> getBatch_pre_goods_list() {
		return batch_pre_goods_list;
	}

	public void setBatch_pre_goods_list(List<Map> batch_pre_goods_list) {
		this.batch_pre_goods_list = batch_pre_goods_list;
	}

	public String getLock_user_id() {
		return lock_user_id;
	}

	public void setLock_user_id(String lock_user_id) {
		this.lock_user_id = lock_user_id;
	}

	public String getAuto() {
		return auto;
	}

	public void setAuto(String auto) {
		this.auto = auto;
	}

	public String getAllotType() {
		return allotType;
	}

	public void setAllotType(String allotType) {
		this.allotType = allotType;
	}

	public String getEnreust_status() {
		return enreust_status;
	}

	public void setEnreust_status(String enreust_status) {
		this.enreust_status = enreust_status;
	}

	public String getOrderReceiveRetrunMark() {
		return orderReceiveRetrunMark;
	}

	public void setOrderReceiveRetrunMark(String orderReceiveRetrunMark) {
		this.orderReceiveRetrunMark = orderReceiveRetrunMark;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public HeadquartersMallBusiRequest getHeadquartersMall() {
		return headquartersMall;
	}

	public void setHeadquartersMall(HeadquartersMallBusiRequest headquartersMall) {
		this.headquartersMall = headquartersMall;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffPassword() {
		return staffPassword;
	}

	public void setStaffPassword(String staffPassword) {
		this.staffPassword = staffPassword;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public List<Map> getOrderQryStatusList() {
		return OrderQryStatusList;
	}

	public void setOrderQryStatusList(List<Map> orderQryStatusList) {
		OrderQryStatusList = orderQryStatusList;
	}

	public List<Map> getGoods_type_list() {
		return goods_type_list;
	}

	public void setGoods_type_list(List<Map> goods_type_list) {
		this.goods_type_list = goods_type_list;
	}

	public List<Map> getActivity_type_list() {
		return activity_type_list;
	}

	public void setActivity_type_list(List<Map> activity_type_list) {
		this.activity_type_list = activity_type_list;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public List<OutcallLogsReq> getOutcalllogsList() {
		return outcalllogsList;
	}

	public void setOutcalllogsList(List<OutcallLogsReq> outcalllogsList) {
		this.outcalllogsList = outcalllogsList;
	}

	public String getP_code() {
		return p_code;
	}

	public void setP_code(String p_code) {
		this.p_code = p_code;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPackge_limit() {
		return packge_limit;
	}

	public void setPackge_limit(String packge_limit) {
		this.packge_limit = packge_limit;
	}

	public String getPlan_title() {
		return plan_title;
	}

	public void setPlan_title(String plan_title) {
		this.plan_title = plan_title;
	}

	public String getEss_code() {
		return ess_code;
	}

	public void setEss_code(String ess_code) {
		this.ess_code = ess_code;
	}

	public String getTerminal_type() {
		return terminal_type;
	}

	public void setTerminal_type(String terminal_type) {
		this.terminal_type = terminal_type;
	}

	public List<String> getOrd_agrt_img() {
		return ord_agrt_img;
	}

	public void setOrd_agrt_img(List<String> ord_agrt_img) {
		this.ord_agrt_img = ord_agrt_img;
	}

	public String getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public String getNet_speed() {
		return net_speed;
	}

	public void setNet_speed(String net_speed) {
		this.net_speed = net_speed;
	}

	public String getAccess_type() {
		return access_type;
	}

	public void setAccess_type(String access_type) {
		this.access_type = access_type;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Map<String, String>> getCrawlerProcList() {
		return crawlerProcList;
	}

	public void setCrawlerProcList(List<Map<String, String>> crawlerProcList) {
		this.crawlerProcList = crawlerProcList;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBat_id() {
		return bat_id;
	}

	public void setBat_id(String bat_id) {
		this.bat_id = bat_id;
	}

	public String getWork_type() {
		return work_type;
	}

	public void setWork_type(String work_type) {
		this.work_type = work_type;
	}

	public String getWork_reason() {
		return work_reason;
	}

	public void setWork_reason(String work_reason) {
		this.work_reason = work_reason;
	}

	public String getWorkReason() {
		return workReason;
	}

	public void setWorkReason(String workReason) {
		this.workReason = workReason;
	}

	public String getOperator_num() {
		return operator_num;
	}

	public void setOperator_num(String operator_num) {
		this.operator_num = operator_num;
	}

	public String getOperator_name() {
		return operator_name;
	}

	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}

	public String getTextInfo() {
		return textInfo;
	}

	public void setTextInfo(String textInfo) {
		this.textInfo = textInfo;
	}

	public List getWorkList() {
		return workList;
	}

	public void setWorkList(List workList) {
		this.workList = workList;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * 根据商品小类判断是否为固移融合单
	 * 
	 * @return
	 */
	public String isMixOrd() {
		json = ordWorkManager.isMixOrd(order_id);
		return this.JSON_MESSAGE;
	}

	public List<Map<String, Object>> getCountyList() {
		return countyList;
	}

	public void setCountyList(List<Map<String, Object>> countyList) {
		this.countyList = countyList;
	}

	/**
	 * 派发工单校验
	 * 
	 * @return
	 */
	public String checkWork() {
		try {
			IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
			String sql = "select * from es_order_intent where order_id='" + order_id.trim()
					+ "' and status in ('00','03')";
			List<Map<String, Object>> list = baseDaoSupport.queryForList(sql);
			if (list.size() > 0) {
				json = "{result:1,message:'改意向单已退单，不允许派发工单！'}";
			} else {
				json = "{result:0,message:''}";
			}
			// 暂时不做限制
			json = "{result:0,message:''}";
		} catch (Exception e) {
			json = "{result:1,message:'工单派发失败" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return this.JSON_MESSAGE;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getLan_id() {
		return lan_id;
	}

	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}

	public String getOrder_county_name() {
		return order_county_name;
	}

	public void setOrder_county_name(String order_county_name) {
		this.order_county_name = order_county_name;
	}

	public String getDistrict_code() {
		return district_code;
	}

	public void setDistrict_code(String district_code) {
		this.district_code = district_code;
	}

	public String getDevelopment_point_name() {
		return development_point_name;
	}

	public String getDevelopment_point_code() {
		return development_point_code;
	}

	public void setDevelopment_point_code(String development_point_code) {
		this.development_point_code = development_point_code;
	}

	public String getDevelopmentCode() {
		return developmentCode;
	}

	public void setDevelopmentCode(String developmentCode) {
		this.developmentCode = developmentCode;
	}

	public String getDevelopmentName() {
		return developmentName;
	}

	public void setDevelopmentName(String developmentName) {
		this.developmentName = developmentName;
	}

	public String list() {

		// this.listRegions();
		// OrgAdminListReq orgAdminListReq = new OrgAdminListReq();
		// String login_org_id = ManagerUtils.getAdminUser().getOrg_id();
		// orgAdminListReq.setLogin_org_id(login_org_id);
		//// orgAdminListReq.setParty_id(party_id);
		if (StringUtils.isNotBlank(org_name)) {
			String[] org_names = org_name.split(",");
			org_name = org_names[org_names.length - 1];
			org_name = org_name.replace(" ", "");
		}
		// logger.info("org_name ============== "+org_name);
		// orgAdminListReq.setOrg_name(org_name);
		//// orgAdminListReq.setOrg_type_id(org_type_id);
		//// orgAdminListReq.setOrg_content(org_content);
		//// orgAdminListReq.setUnion_org_code(union_org_code);
		// if(StringUtils.isNotBlank(lan_id)){
		// String[] lan_ids = lan_id.split(",");
		// lan_id = lan_ids[lan_ids.length-1];
		// lan_id = lan_id.replace(" ", "");
		// }
		// logger.info("lan_id ================"+lan_id);
		// orgAdminListReq.setLan_id(lan_id);
		// orgAdminListReq.setPageIndex(this.getPage());
		// orgAdminListReq.setPageSize(this.getPageSize());
		webpage = ecsOrdManager.getCountInfoByName(org_name, this.getPage(), this.getPageSize());

		return "orgList";
	}

	/**
	 * 根据地市编码获取行政县分
	 * 
	 * @return
	 */
	public String getCountyListByCity() {
		try {
			if (params == null) {
				params = new OrderQueryParams();
			}
			countyList = ordWorkManager.getIntentCountyList(params.getOrder_city_code());
			if (countyList.size() > 0) {
				Map<String, Object> respMap = new HashMap<String, Object>();
				respMap.put("list", countyList);
				String list = JSONObject.fromObject(respMap).toString();
				list = list.substring(1, list.length() - 1).replace("\"list\":", "");
				json = "{result:0,message:'查询成功',list:'" + list + "'}";
			} else {
				json = "{result:2,message:'根据地市编码获取行政县分为空,请联系管理员'}";
			}
		} catch (Exception e) {
			json = "{result:1,message:'根据地市编码获取行政县分失败" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 根据当前用户权限获取行政县分
	 * 
	 * @return
	 */
	public String getCountyListByPower() {
		try {
			if (params == null) {
				params = new OrderQueryParams();
			}
			user = ManagerUtils.getAdminUser();
			countyList_new = ordWorkManager.getIntentCountyList(String.valueOf(city_code));
			countyList = new ArrayList<Map<String, Object>>();
			List<String> county = user.getPermission_county();
			for (Map<String, Object> m : countyList_new) {
				String countyss = (String) m.get("field_value");
				for (int i = 0; i < county.size(); i++) {
					String countys = county.get(i);
					if (countyss.equals(countys)) {
						countyList.add(m);
					}
				}
			}
			if (countyList.size() > 0) {
				Map<String, Object> respMap = new HashMap<String, Object>();
				respMap.put("list", countyList);
				String list = JSONObject.fromObject(respMap).toString();
				list = list.substring(1, list.length() - 1).replace("\"list\":", "");
				json = "{result:0,message:'查询成功',list:'" + list + "'}";
			} else {
				json = "{result:2,message:'根据地市编码获取行政县分为空,请联系管理员'}";
			}
		} catch (Exception e) {
			json = "{result:1,message:'根据地市编码获取行政县分失败" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 
	 * <p>
	 * Title: exportOrderReport
	 * </p>
	 * <p>
	 * Description: 订单查询数据导出
	 * </p>
	 * 
	 * @autho sgf
	 * @time 2018年7月9日 上午10:19:11
	 * @version 1.2
	 * @throws Exception
	 */
	public String exportOrderReport() throws Exception {
		long start = System.currentTimeMillis();// 调用导出开始时间
		if (params == null) {
			params = new OrderQueryParams();
		}
		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_return_back)) {
			ThreadContextHolder.getSessionContext().setAttribute("order_query_params", new OrderQueryParams());
		} else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_return_back)) {
			params = (OrderQueryParams) ThreadContextHolder.getSessionContext().getAttribute("order_query_params");
		}
		if (StringUtil.isEmpty(params.getOrder_id()) && StringUtil.isEmpty(params.getOut_tid())
				&& StringUtil.isEmpty(params.getQuery_btn_flag())) {
			Calendar date = new GregorianCalendar();
			date.add(Calendar.DATE, -7);
			String startTime = DF.format(date.getTime());
			date.add(Calendar.MONTH, -1);
			String startTimeMonth = DF.format(date.getTime());
			String endTime = DF.format(new Date());
			if (StringUtil.isEmpty(params.getCreate_start())) {
				params.setCreate_start(startTime);
			}
			if (StringUtil.isEmpty(params.getCreate_end())) {
				params.setCreate_end(endTime);
			}
		}

		ThreadContextHolder.getSessionContext().setAttribute("order_query_params", params);// 往session里塞新的查询参数
		if (StringUtils.isNotBlank(this.excel)) {
			int count = 0;// 先查询总数
			count = ecsOrdManager.getExportOrderReport(params);// 总数查询实现待完成
			if ("check".equals(excel)) {
				// 数量校验
				if (count > pageSize) {// 同时记录少于10000，那么结果为成功，否则结果为失败，最后返回。
					this.json = "{result:0,count:" + pageSize + "}";
				} else {
					this.json = "{result:1}";
				}
				return this.JSON_MESSAGE;
			} else {
				String[] title = new String[] { "地市", "县分", "订单号", "订单来源", "外部订单号", "商品名称", "客户名称", "客户联系电话", "身份证号码", "开户人员工号", "开户时间", "退单人员工号", "退单时间", 
						"标准地址", "客户联系地址", "发展点编码", "发展点名称", "发展人编码", "发展人名称", "订单状态", "工单状态", "锁定人工号", "锁定人姓名", "宽带账号",
						"宽带业务号码", "开户号码", "Sim号", "物流公司", "物流单号", "下单时间","订单环节状态","退单备注","照片上传短链接" };

				String[] content = { "city_name", "county_name", "order_id", "order_from", "out_tid", "goods_name",
						"buy_name", "mobile", "cert_card_num", "open_account_userid", "open_account_time", "refund_userid", "refund_time", "address", "adsl_addr", "development_point_code",
						"development_point_name", "development_code", "development_name", "order_state", "status",
						"lock_user_id", "lock_user_name", "adsl_account", "adsl_number", "phone_num", "sim",
						"shipping_company", "logi_no", "tid_time","orderNodeStatus","cancelOrderMemo","photoShortUrl" };
				String fileTitle = "订单信息导出";
				HttpServletRequest request = ServletActionContext.getRequest();
				request.setAttribute("webpage", this.webpage);// 将查询的page数据放入request对象
				// 订单信息导出
				Page pageList = ecsOrdManager.exportOrderReportExcel(1, count, params);// 重新开始取数据，取1-10000.
				List list = pageList.getResult();
				DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());// 导出工具开始导出数据。
				// 查询完毕后重新对显示到页面的日期进行格式化
				params.setCreate_start(params.getCreate_start().substring(0, 10));
				params.setCreate_end(params.getCreate_end().substring(0, 10));
				return null;
			}
		}
		return null;
	}

	/**
	 * 杭州联名卡报表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String jointCardDevelopmentDaily() throws Exception {
		if (null == params) {
			params = new OrderQueryParams();
			String time = null;
			if(params.getCreate_date() == null || params.getCreate_date().equals("")) {
				//获取查询日期为当前日期前一天
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				date = calendar.getTime();
				time = sdf.format(date);
				params.setCreate_date(time);
			}
			//获取当前时间所在月的第一天和最后一天
			if(params.getCreate_start() == null || params.getCreate_start().equals("")
					||params.getCreate_end() == null || params.getCreate_end().equals("") ) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-01 00:00:00");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
				String time1 = sdf.format(new Date());

				Date date1 = sdf.parse(time1);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date1);
				String monthStarTime = sdf1.format(cal.getTime()); // 第一天
				Calendar ca = Calendar.getInstance();
				ca.add(Calendar.MONTH, 0); // 加一个月
				ca.setTime(date1);
				ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
				String monthEndTime = sdf2.format(ca.getTime()); // 最后一天
				
				params.setCreate_start(monthStarTime);
				params.setCreate_end(monthEndTime);
			}
		} else if (params.getQuery_btn_flag().equals("yes")) {
			if(!"dtl".equals(this.type)) {
				this.webpage = this.ecsOrdManager.queryHangZhouDayReport(this.getPage(), this.getPageSize(), params);
			}
			// 导出数据
			if ("check".equals(excel)) {// 如果excel标记为check
				// 数量校验
				this.json = "{result:1}";

				return this.JSON_MESSAGE;
			} else if ("report".equals(this.type)) {
				String[] title = new String[] { "地市", "冰激凌129", "冰激凌199", "腾讯天王卡", "腾讯地王卡", "腾讯大王卡", "当日发展量", "当日激活量",
						"当月发展量", "当月发展激活量", "当月累计激活量", "累计发展量", "累计激活量", "转化率" };
				String[] content = { "cityName", "bjl129", "bjl199", "skyCard", "groundCard", "bigCard", "dayDev",
						"dayact", "monthDev", "monthAct", "totalAct", "leiJiDev", "leiJiDevAct",
						"zhuanhualv" };
				
				String time = null;
				if(params.getCreate_date() == null || params.getCreate_date().equals("")) {
					//获取查询日期为当前日期前一天
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = new Date();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.DAY_OF_MONTH, -1);
					date = calendar.getTime();
					time = sdf.format(date);
				}else {
					time  = params.getCreate_date(); 
				}
				
				String fileTitle = time+"日联名卡发展日报";

				HttpServletRequest request = ServletActionContext.getRequest();
				Page pageList = this.ecsOrdManager.queryHangZhouDayReport(this.getPage(), this.getPageSize()+1, params);
				request.setAttribute("webpage", this.webpage);// 将查询的page数据放入request对象

				// 杭州银行联名卡发展日报
				List list = pageList.getResult();

				DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());// 导出工具开始导出数据。

				return null;
			} else if ("dtl".equals(this.type)) {
				/*
				String[] title = new String[] { "地市", "订单号", "下单时间", "开户人", "开户号码", "订单状态", "商品ID", "商品名字", "激活状态",
						"激活时间","联系人名称","联系人电话","联系人地址" };
				
				String[] content = { "field_value_desc", "order_id", "create_time", "ship_name", "phone_num",
						"orderStatus", "goods_id", "goodsname", "actStatus", "actTime","contactName","contactTel","contactAddr" };
				*/

				  String[] title = new String[] { "地市", "订单号", "下单时间", "开户人", "开户号码","证件号码", "联系电话", "联系人", "联系地址", "订单状态",
				            "商品ID","商品名字","激活状态","激活时间","finish_time" };
				        
				        String[] content = { "field_value_desc", "order_id", "create_time", "open_ship_name", "phone_num","cert_card_num",
				            "ship_tel", "ship_name", "contactAddr", "orderStatus", "goods_id","goodsname","actStatus","actTime","finish_time" };


				String fileTitle = "杭州银行联名卡发展详单";

				HttpServletRequest request = ServletActionContext.getRequest();
				request.setAttribute("webpage", this.webpage);// 将查询的page数据放入request对象

				// 导出杭州银行联名卡发展详单
				List list = ecsOrdManager.queryHangZhouDevReport(params);

				DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());// 导出工具开始导出数据。

				return null;
			}
		}
		return "jointCardDevelopmentDaily";
	}

	public String intentOrderReportQuery() throws Exception {

		DateUtil dateUtil=new DateUtil();
		Calendar date = new GregorianCalendar();
		long start = System.currentTimeMillis();
		// 订单来源
		order_from_list = ecsOrdManager.getDcSqlByDcName("ORDER_FROM");
		for (int i = 0; i < order_from_list.size(); i++) {
			String key = (String) order_from_list.get(i).get("codea");
			if (!"intent".equals(key)) {
				order_from_list.remove(i);
				i--;
			}
		}
		// 处理参数并根据是否返回状态把查询参数放入session
		if (params == null) {
			params = new OrderQueryParams();
		}

		/*if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_return_back)) {
			// 是否调用返回方法，0为往session里放查询条件，
			ThreadContextHolder.getHttpRequest().setAttribute("order_query_params", new OrderQueryParams());
			//ThreadContextHolder.getSessionContext().setAttribute("order_query_params", new OrderQueryParams());
		} else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_return_back)) {
			// 是否调用返回方法，
			// 1则从session取查询条件添加到页面
			params = (OrderQueryParams) ThreadContextHolder.getHttpRequest().getAttribute("order_query_params");
		}*/

		// 订单ID,outtid,query_btn_flag都为空的话，默认放入一个查询的10天以内的时间
		if (StringUtil.isEmpty(params.getQuery_btn_flag())) {
			
			date.add(Calendar.DAY_OF_MONTH, -10);
			String startTime = DF2.format(date.getTime());
			String endTime = DF2.format(new Date());

			if (StringUtil.isEmpty(params.getCreate_start())) {
				params.setCreate_start(startTime + " 00:00:00");
			}
			if (StringUtil.isEmpty(params.getCreate_end())) {
				params.setCreate_end(endTime + " 23:59:59");
			}
		}

		if (StringUtils.isBlank(params.getReport_search_type())) {
			// 默认按地市统计
			params.setReport_search_type("1");
		}

		// 对日期进行格式化
/*		if (!StringUtil.isEmpty(params.getCreate_start()))
			params.setCreate_start(params.getCreate_start());

		if (!StringUtil.isEmpty(params.getCreate_end()))
			params.setCreate_end(params.getCreate_end());*/

	//	ThreadContextHolder.getHttpRequest().setAttribute("order_query_params", params);// 往session里塞新的查询参数

		if (StringUtils.isNotBlank(this.excel)) {
			// 导出数据
			if ("check".equals(excel)) {// 如果excel标记为check
				// 数量校验
				this.json = "{result:1}";

				return this.JSON_MESSAGE;
			} else if ("dtl".equals(this.type)) {
				String[] title = new String[] { "地市", "县分", "订单号", "意向单号", "商品小类", "商品名称", "自传播活动编码", "自传播活动名称", "订单来源",
						"客户姓名", "客户联系号码", "客户地址", "推荐人号码", "创建时间", "备注", "开户号码", "开户时间", "特投人员姓名", "特投人员电话", "派单时间",
						"状态", "结束时间", "锁单人工号", "锁单人姓名", "下单渠道", "下单渠道名称", "下单人工号", "下单人姓名", "下单发展点编码", "下单发展点名称",
						"下单发展人编码", "下单发展人姓名", "开户点编码", "开户点名称", "开户人工号", "开户人姓名", "开户发展点编码", "开户发展点名称", "开户发展人编码",
						"开户发展人名称", "顶级种子专业线", "顶级种子类型", "顶级种子分组" };
				String[] content = new String[] { "city_name", "county_name", "order_id", "intent_order_id", "name",
						"goods_name", "market_user_id", "acc_nbr", "source_system_name", "ship_name", "ship_tel",
						"ship_addr", "referee_num", "create_time", "remark", "activity_name", "bss_account_time",
						"handler_name", "handler_num", "create_time_send", "status", "intent_finish_time", "lock_id",
						"lock_name", "deal_office_id", "deal_office_name", "deal_operator", "deal_operator_name",
						"development_name", "development_point_code_eoi", "development_code_eoi",
						"development_point_name_eoi", "out_office", "development_point_name", "accept_man_id",
						"accept_man_name", "accept_point", "accept_name", "development_code", "development_name_eoi",
						"top_seed_professional_line", "top_seed_type", "top_seed_group_id" };
				
				String fileName = DateFormatUtils.formatDate(CrmConstants.DATE_TIME_FORMAT_14);
				
				String fileTitle = "意向单订单信息导出_"+fileName;

				HttpServletRequest request = ServletActionContext.getRequest();
				request.setAttribute("webpage", this.webpage);// 将查询的page数据放入request对象

				// 意向单明细导出
				List list = ecsOrdManager.exportIntentOrderDtl(params);

				DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());// 导出工具开始导出数据。

				return null;
			} else {
				// 意向单统计导出
				String fileTitle = "";
				String[] title = null;
				String[] content = null;

				if ("1".equals(params.getReport_search_type())) {
					// 按地市统计导出
					fileTitle = "意向单按地市统计导出";
					title = new String[] { "地市", "总量", "未处理", "处理中", "已完成", "结单" };
					content = new String[] { "city_name", "total", "undeal", "dealing", "done", "can" };
				} else {
					// 按县分统计导出
					String fileName = DateFormatUtils.formatDate(CrmConstants.DATE_TIME_FORMAT_14);
					fileTitle = "意向单按县分统计导出_"+fileName;
					title = new String[] { "地市", "县分", "总量", "未处理", "处理中", "已完成", "结单" };
					content = new String[] { "city_name", "county_name", "total", "undeal", "dealing", "done", "can" };
				}

				HttpServletRequest request = ServletActionContext.getRequest();
				request.setAttribute("webpage", this.webpage);// 将查询的page数据放入request对象

				// 意向单报表导出
				List list = this.ecsOrdManager.queryIntentOrderReportList(params);

				DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());// 导出工具开始导出数据。

				return null;
			}
		} else {
			logger.info("意向单查询：Order_from：" + params.getOrder_from() + "---Order_from_c：" + params.getOrder_from_c());
			// 查询
			if (ConstsCore.CONSTS_YES.equals(params.getQuery_btn_flag())) {// 页面点击按钮查询才加载数据.如果页面按钮查询标志为yes,则开始查询
				this.webpage = this.ecsOrdManager.queryIntentOrderReportList(this.getPage(), this.getPageSize(),
						params);// 执行订单查询，查询结果放入父类继承过来的的webpage
								// 的page对象.
			}

			return "intent_order_report";
		}

	}

	/**
	 * 各人员各环节订单量查询报表by name
	 * 
	 * @return
	 * @throws Exception
	 */
	public String personnelOrderQuantityQueryByName() throws Exception {
		return "personnel_order_quantity_byname";
	}

	public String getPersonnelOrderQuantityTableUrl() throws Exception {
		try {
			if (params == null) {
				params = new OrderQueryParams();
			}
			params.setUsername(username);
			params.setPhone_num(phone_num);
			params.setOrder_city_code(order_city_code);
			params.setOrder_county_code(order_county_code);
			params.setOrder_role(order_role);
			params.setCreate_start(create_start);
			params.setCreate_end(create_end);
			params.setOrder_from(order_from);
			this.webpage = ecsOrdManager.queryOrderQuantityReportListByName(this.getPage(), this.getPageSize(), params);
		} catch (Exception e) {
			json = "{result:1,message:'加载页面失败" + e.getMessage() + "'}";
			e.printStackTrace();
		}

		return "personnelOrderQuantityTable";
	}

	/**
	 * 各人员各环节订单量查询报表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String personnelOrderQuantityQuery() throws Exception {
		user = ManagerUtils.getAdminUser();
		if (params == null) {
			params = new OrderQueryParams();
		}
		permission_level = user.getPermission_level();

		order_from_list = ecsOrdManager.getDcSqlByDcName("ORDER_FROM");
		order_role_list = ecsOrdManager.getDcSqlByDcName("order_role");
		dc_MODE_REGIONList = ecsOrdManager.getDcSqlByDcName("DC_MODE_REGION");
		List<Map> dc_MODE_REGIONList_new = ecsOrdManager.getDcSqlByDcName("DC_MODE_REGION");
		countyList = ordWorkManager.getIntentCountyList(params.getOrder_city_code());
		List<Map<String, Object>> countyList_new = ordWorkManager.getIntentCountyList(params.getOrder_city_code());
		/*
		 * 对订单来源过滤（仅是意向单来源）
		 */
		for (int i = 0; i < order_from_list.size(); i++) {
			String key = (String) order_from_list.get(i).get("codea");
			if (!"intent".equals(key)) {
				order_from_list.remove(i);
				i--;
			}
		}
		// 权限判断
		if (!StringUtil.isEmpty(user.getPermission_level())) {
			// 权限为市中台
			if ("2".equals(user.getPermission_level())) {
				dc_MODE_REGIONList.clear();
				countyList.clear();
				for (int i = 0; i < order_role_list.size(); i++) {
					String key = (String) order_role_list.get(i).get("value");
					if ("03121".equals(key)) {
						order_role_list.remove(i);
						i--;
					}
				}
				Iterator<Map> cityIterator = dc_MODE_REGIONList_new.iterator();

				while (cityIterator.hasNext()) {
					Map<String, Object> product = cityIterator.next();
					if (user.getPermission_region().size() == 1) {
						String citys = user.getPermission_region().get(0);

						if (citys.equals(product.get("value")))
							dc_MODE_REGIONList.add(product);
					}
					if (user.getPermission_region().size() > 1) {

						for (int n = 0; user.getPermission_region().size() > n; n++) {

							String citys = user.getPermission_region().get(n);
							if (citys.equals(product.get("value"))) {
								// cityIterator.remove();
								dc_MODE_REGIONList.add(product);
							}
						}
					}
				}
				countyList = ordWorkManager.getIntentCountyList((String) dc_MODE_REGIONList.get(0).get("value"));
				/*
				 * countyList_new =
				 * ordWorkManager.getIntentCountyList(String.valueOf(city_code)); countyList=new
				 * ArrayList<Map<String,Object>>(); List<String>
				 * county=user.getPermission_county(); for (Map<String, Object> m :
				 * countyList_new){ String countyss=(String) m.get("field_value"); for (int i =
				 * 0; i < county.size(); i++) { String countys=county.get(i);
				 * if(countyss.equals(countys)){ countyList.add(m); } } }
				 */
			} else if ("3".equals(user.getPermission_level())) { // 权限为县分中台
				dc_MODE_REGIONList.clear();
				countyList.clear();
				for (int i = 0; i < order_role_list.size(); i++) {
					String key = (String) order_role_list.get(i).get("value");
					if ("03121".equals(key) || "03122".equals(key)) {
						order_role_list.remove(i);
						i--;
					}
				}

				Iterator<Map> cityIterator = dc_MODE_REGIONList_new.iterator();

				while (cityIterator.hasNext()) {
					Map<String, Object> product = cityIterator.next();
					if (user.getPermission_region().size() == 1) {
						String citys = user.getPermission_region().get(0);

						if (citys.equals(product.get("value")))
							dc_MODE_REGIONList.add(product);
					}
					if (user.getPermission_region().size() > 1) {

						for (int n = 0; user.getPermission_region().size() > n; n++) {

							String citys = user.getPermission_region().get(n);
							if (citys.equals(product.get("value"))) {
								cityIterator.remove();
								dc_MODE_REGIONList.add(product);
							}
						}
					}
				}

				countyList_new = ordWorkManager.getIntentCountyList((String) dc_MODE_REGIONList.get(0).get("value"));
				Iterator<Map<String, Object>> countyIterator = countyList_new.iterator();
				while (countyIterator.hasNext()) {
					Map<String, Object> county = countyIterator.next();
					if (user.getPermission_county().size() == 1) {
						String countys = user.getPermission_county().get(0);

						if (countys.equals(county.get("field_value")))
							countyList.add(county);
					}
					if (user.getPermission_county().size() > 1) {

						for (int n = 0; user.getPermission_county().size() > n; n++) {

							String countys = user.getPermission_county().get(n);
							if (countys.equals(county.get("field_value"))) {
								countyIterator.remove();
								countyList.add(county);
							}
						}
					}
				}
			}
		}

		/**
		 * 根据当前用户的权限设置查询参数
		 */

		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_return_back)) {
			// 是否调用返回方法，0为往session里放查询条件，
			ThreadContextHolder.getSessionContext().setAttribute("order_query_params", new OrderQueryParams());
		} else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_return_back)) {
			// 是否调用返回方法，
			// 1则从session取查询条件添加到页面
			params = (OrderQueryParams) ThreadContextHolder.getSessionContext().getAttribute("order_query_params");
		}
		// 地市县分,outtid,query_btn_flag都为空的话，默认放入一个查询的10天以内的时间
		if (StringUtil.isEmpty(params.getQuery_btn_flag())) {
			Calendar date = new GregorianCalendar();
			date.add(Calendar.DAY_OF_MONTH, -10);
			String startTime = DF2.format(date.getTime());
			String endTime = DF2.format(new Date());

			if (StringUtil.isEmpty(params.getCreate_start())) {
				params.setCreate_start(startTime + " 00:00:00");
			}
			if (StringUtil.isEmpty(params.getCreate_end())) {
				params.setCreate_end(endTime + " 23:59:59");
			}
		}
		// 对日期进行格式化
		if (!StringUtil.isEmpty(params.getCreate_start()))
			params.setCreate_start(params.getCreate_start());

		if (!StringUtil.isEmpty(params.getCreate_end()))
			params.setCreate_end(params.getCreate_end());

		ThreadContextHolder.getSessionContext().setAttribute("order_query_params", params);// 往session里塞新的查询参数
		// 执行订单查询，查询结果放入父类继承过来的的webpage 的page对象.
		if (ConstsCore.CONSTS_YES.equals(params.getQuery_btn_flag())) {// 页面点击按钮查询才加载数据.如果页面按钮查询标志为yes,则开始查询
			this.webpage = this.ecsOrdManager.queryOrderQuantityReportList(this.getPage(), this.getPageSize(), params);
		}
		return "personnel_order_quantity";
	}

	/*
	 * v计划报表查询导出
	 */
	public String vPlanOrderReportQuery() throws Exception {

		// V计划活动名称
		order_vplan_list = ecsOrdManager.getDcSqlByDcName("V_PLAN");
		// 处理参数并根据是否返回状态把查询参数放入session
		if (params == null) {
			params = new OrderQueryParams();
		}

		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_return_back)) {
			// 是否调用返回方法，0为往session里放查询条件，
			ThreadContextHolder.getSessionContext().setAttribute("order_query_params", new OrderQueryParams());
		} else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_return_back)) {
			// 是否调用返回方法，
			// 1则从session取查询条件添加到页面
			params = (OrderQueryParams) ThreadContextHolder.getSessionContext().getAttribute("order_query_params");
		}

		// 订单ID,outtid,query_btn_flag都为空的话，默认放入一个查询的10天以内的时间
		if (StringUtil.isEmpty(params.getOrder_id()) && StringUtil.isEmpty(params.getOut_tid())
				&& StringUtil.isEmpty(params.getQuery_btn_flag())) {
			Calendar date = new GregorianCalendar();
			date.add(Calendar.DAY_OF_MONTH, -10);
			String startTime = DF2.format(date.getTime());
			String endTime = DF2.format(new Date());

			if (StringUtil.isEmpty(params.getCreate_start())) {
				params.setCreate_start(startTime + " 00:00:00");
			}
			if (StringUtil.isEmpty(params.getCreate_end())) {
				params.setCreate_end(endTime + " 23:59:59");
			}
		}

		// 对日期进行格式化
		if (!StringUtil.isEmpty(params.getCreate_start()))
			params.setCreate_start(params.getCreate_start());

		if (!StringUtil.isEmpty(params.getCreate_end()))
			params.setCreate_end(params.getCreate_end());

		ThreadContextHolder.getSessionContext().setAttribute("order_query_params", params);// 往session里塞新的查询参数

		if (StringUtils.isNotBlank(this.excel)) {
			// 导出数据
			if ("check".equals(excel)) {// 如果excel标记为check
				// 数量校验
				this.json = "{result:1}";

				return this.JSON_MESSAGE;
			} else if ("dtl".equals(this.type)) {
				String[] title = new String[] { "地市", "县分", "商品名称", "订单创建时间", "MARKET_USER_ID", "活动名称", "订单号", "业务号码",
						"客户姓名", "联系电话", "联系地址", "订单状态", "订单锁定人", "结单原因", "办理方式" };
				String[] content = { "CITY", "COUNTY", "GOODS_NAME", "CREATE_TIME", "MARKET_USER_ID", "ACTIVITY_NAME",
						"ORDER_ID", "ACC_NBR", "CUSTOMER_NAME", "SHIP_TEL", "SHIP_ADDR", "STATUS", "LOCK_NAME",
						"REMARK", "HANDLE_MODE" };
				String fileTitle = "V计划订单信息导出";

				HttpServletRequest request = ServletActionContext.getRequest();
				request.setAttribute("webpage", this.webpage);// 将查询的page数据放入request对象

				// V计划明细导出
				List list = ecsOrdManager.exportVplanOrderDtl(params);

				DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());// 导出工具开始导出数据。

				return null;
			} else {
				String[] title = new String[] { "地市", "王卡订单总量", "王卡办理成功", "宽带订单总量", "有线订单量", "有线办理成功", "无线订单量",
						"无线办理成功", "副卡订单总量", "线上订单总量", "线上订单已开户", "线下订单总量",
						/*
						 * "APP撤单", "其他", "联系不上", "不符合条件", "再考虑", "需要邮寄", "未申请","人在外地",
						 * "后续到营业厅办理","测试单", "外省用户",
						 */
						"不需要", "不符合办理条件", "测试单", "开户其他号码", "再考虑", "需要邮寄", "无法联系上", "操作错误", "其他", "系统原因", "未处理", "工单已派发",
						"办理成功" };
				String[] content = { "city", "counts1", "counts2", "counts3", "counts4", "counts5", "counts6",
						"counts7", "counts8", "counts9", "counts10", "counts11", "counts12", "counts13", "counts14",
						"counts15", "counts16", "counts17", "counts18", "counts19", "counts20", "counts21", "counts23",
						"counts24", "counts25" };

				String fileTitle = "V计划订单信息导出";

				HttpServletRequest request = ServletActionContext.getRequest();
				request.setAttribute("webpage", this.webpage);// 将查询的page数据放入request对象

				// V计划订单信息导出
				List list = ecsOrdManager.queryVplanOrderReportList(params);

				DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());// 导出工具开始导出数据。

				return null;
			}
		} else {
			// 查询
			if (ConstsCore.CONSTS_YES.equals(params.getQuery_btn_flag())) {// 页面点击按钮查询才加载数据.如果页面按钮查询标志为yes,则开始查询
				this.webpage = this.ecsOrdManager.queryVplanOrderReportList(this.getPage(), this.getPageSize(), params);// 执行订单查询，查询结果放入父类继承过来的的webpage
																														// 的page对象.
			}
			return "v_plan_report";
		}
	}

	// 订单审核 跳转页面
	public String ordAduit() {
		/*
		 * this.webpage = ordWorkManager.getWorkListByOrderId(this.order_id.replace(",",
		 * "").trim(), this.getPage(), this.getPageSize());
		 */
		if (StringUtil.isEmpty(first_load) || !"yes".equals(first_load)) {
			checkFee(order_id);
			return JSON_MESSAGE;
		}
		return "ordAduit";
	}

	// 订单审核 信息保存
	public String save_ordAduit() {
		try {
			// sgf 宽带一期工单派发
			OrderTreeBusiRequest otreq = CommonDataFactory.getInstance().getOrderTree(order_id);
			Boolean flags = ordWorkManager.isKDYQ(order_id, otreq);
			if (flags) {
				List<OrderAdslBusiRequest> lists = otreq.getOrderAdslBusiRequest();
				if (lists.size() > 0) {
					String adsl_addrS = lists.get(0).getAdsl_addr();
					if (StringUtils.isEmpty(adsl_addrS) || "-1".equals(adsl_addrS)) {
						this.json = "{result:1,message:'请回到订单审核环节,选择标准地址且进行保存'}";
						return this.JSON_MESSAGE;
					}
				}
			}
			this.orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			updateOrderState(orderTree, "es_order", "order_state", "2", work_reason);
			AdminUser user = ManagerUtils.getAdminUser();
			OrderExtBusiRequest extReq = otreq.getOrderExtBusiRequest();

			OrderHandleLogsReq req = new OrderHandleLogsReq();
			req.setOp_id(user.getUserid());
			req.setOp_name(user.getRealname());
			req.setOrder_id(otreq.getOrder_id());
			req.setFlow_id(extReq.getFlow_id());
			req.setOp_id(user.getUserid());
			req.setOp_name(user.getRealname());
			req.setFlow_trace_id(extReq.getFlow_trace_id());
			req.setComments(work_reason);
			req.setHandler_type(Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
			req.setType_code("o_audit");
			this.ordFlowManager.insertOrderHandLog(req);
			json = "{result:0,message:'保存成功'}";
		} catch (Exception e) {
			json = "{result:1,message:'订单审核 存在异常" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return JSON_MESSAGE;
	}

	// 退单处理 跳转页面
	public String ordDispose() {
		this.webpage = ordWorkManager.getWorkListByOrderId(this.order_id.replace(",", "").trim(), this.getPage(),
				this.getPageSize());
		contactResultsList = ordWorkManager.getDisposeResultsList();
		return "ordDispose";
	}

	// 退单处理 保存
	public String save_ordDispose() {
		this.orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		// 外部单号
		String out_tid = orderTree.getOrderExtBusiRequest().getOut_tid();
		// 获取 当前时间
		String date = DateFormatUtils.formatDate(CrmConstants.DATE_TIME_FORMAT);
		try {
			this.orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			int count = getOrderStateSynCount(order_id);
			// 翻转 状态
			updateOrderTreestate(orderTree, order_id);
			// 添加 备注
			insertHandleLog(orderTree, work_reason);
			if ("-2".equals(contactResults)) {
				if (count != 0) {
					updateOrderState(orderTree, "5", order_id, date);
				} else {
					insetOrderState(out_tid, order_id, "5", date);
				}
			} else if ("-3".equals(contactResults)) {
				if (count != 0) {
					updateOrderState(orderTree, "6", order_id, date);
				} else {
					insetOrderState(out_tid, order_id, "6", date);
				}
			} else if ("-4".equals(contactResults)) {
				if (count != 0) {
					updateOrderState(orderTree, "4", order_id, date);
				} else {
					insetOrderState(out_tid, order_id, "4", date);
				}
			}
			if (!StringUtil.isEmpty(contactResults)) {
				updateOrderState(orderTree, "es_order", "order_state", contactResults, work_reason);
			}
			OrderHandleLogsReq req = new OrderHandleLogsReq();
			String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
			String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			AdminUser user = ManagerUtils.getAdminUser();
			req.setOp_id(user.getUserid());
			req.setOp_name(user.getRealname());
			req.setOrder_id(order_id);
			req.setFlow_id(flow_id);
			req.setFlow_trace_id(flowTraceId);
			req.setComments(work_reason);
			req.setHandler_type(Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
			req.setType_code("o_dispose");
			this.ordFlowManager.insertOrderHandLog(req);
			json = "{result:0,message:'保存成功'}";
		} catch (Exception e) {
			json = "{result:1,message:'订单挂起 存在异常" + e.getMessage() + "'}";
			e.printStackTrace();
		}

		return JSON_MESSAGE;
	}

	// 订单挂起 跳转页面
	public String ordHang() {
		this.webpage = ordWorkManager.getWorkListByOrderId(this.order_id.replace(",", "").trim(), this.getPage(),
				this.getPageSize());
		return "ordHang";
	}

	// 订单挂起 信息保存
	public String save_ordHang() {
		try {
			this.orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			updateOrderState(orderTree, "es_order", "order_state", "99", work_reason);
			OrderHandleLogsReq req = new OrderHandleLogsReq();
			String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
			String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			AdminUser user = ManagerUtils.getAdminUser();
			req.setOp_id(user.getUserid());
			req.setOp_name(user.getRealname());
			req.setOrder_id(order_id);
			req.setFlow_id(flow_id);
			req.setFlow_trace_id(flowTraceId);
			req.setComments(work_reason);
			req.setHandler_type(Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
			req.setType_code("o_hang");
			this.ordFlowManager.insertOrderHandLog(req);
			json = "{result:0,message:'保存成功'}";

		} catch (Exception e) {
			json = "{result:1,message:'订单挂起 存在异常" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return JSON_MESSAGE;
	}

	// 翻转 退单状态 退单处理 类型
	public void updateOrderTreestate(OrderTreeBusiRequest orderTree, String order_id) {
		String tableName = "es_order_ext";
		String setSql = "refund_deal_type='02',refund_status='04'";
		OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
		orderExt.setRefund_deal_type("02");
		orderExt.setRefund_status("04");
		updateOrderTree(setSql, tableName, orderExt.getOrder_id(), orderTree);
	}

	// 查询预约单处理状态同步总商消息表有没有 WCL||SBCL记录
	public int getOrderStateSynCount(String order_id) {
		IDaoSupport baseDaoSupport = null;
		if (baseDaoSupport == null) {
			baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		}
		String sql = "select count(order_id) from ES_KD_ORDER_STATUS_SYN where order_id=?";
		return baseDaoSupport.queryForInt(sql, order_id);
	}

	// 查询预约单处理状态同步总商消息表 添加记录
	public void insetOrderState(String out_tid, String order_id, String state, String date) {
		IDaoSupport baseDaoSupport = null;
		if (baseDaoSupport == null) {
			baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		}
		String id = baseDaoSupport.getSequences("seq_kd_status_syn");
		String sql = "insert into ES_KD_ORDER_STATUS_SYN "
				+ "(id,BESPEAKID,ORDER_ID,DEALSTATE,DEALTIME,SYN_STATUS,SYN_NUM,SOURCE_FROM)" + "values " + " ('" + id
				+ "','" + out_tid + "','" + order_id + "','" + state + "','" + date + "','WCL','1','ECS')" + "";
		baseDaoSupport.execute(sql, null);

	}

	// 查询预约单处理状态同步总商消息表 更新
	public void updateOrderState(OrderTreeBusiRequest orderTree, String state, String order_id, String date) {
		String tableName = "ES_KD_ORDER_STATUS_SYN";
		String setSql = "DEALSTATE='" + state + "',DEALTIME='" + date + "',SYN_STATUS='WCL',SYN_NUM='0'";
		OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
		orderExt.setOrder_id(order_id);
		updateOrderTree(setSql, tableName, orderExt.getOrder_id(), orderTree);
	}

	private void updateOrderState(OrderTreeBusiRequest orderTree, String tableName, String fieldName, String state,
			String operType) {
		OrderBusiRequest orderBusi = orderTree.getOrderBusiRequest();
		orderBusi.setOrder_state(state);
		String setSql = fieldName + " = '" + state + "'";
		updateOrderTree(setSql, tableName, orderBusi.getOrder_id(), orderTree);
		// insertHandleLog(orderTree, operType);
	}

	private void updateOrderTree(String set_sql, String table_name, String order_id, OrderTreeBusiRequest orderTree) {
		String sql = "update " + table_name + " set " + set_sql + " where order_id=?";
		IDaoSupport baseDaoSupport = null;
		if (baseDaoSupport == null) {
			baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		}
		baseDaoSupport.execute(sql, order_id);
		// 更新订单树缓存
		cache.set(RequestStoreManager.NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY + "order_id" + order_id,
				orderTree, RequestStoreManager.time);
	}

	private void insertHandleLog(OrderTreeBusiRequest orderTree, String operType) {
		AdminUser user = ManagerUtils.getAdminUser();
		OrderExtBusiRequest extReq = orderTree.getOrderExtBusiRequest();

		OrderHandleLogsReq req = new OrderHandleLogsReq();
		req.setOrder_id(orderTree.getOrder_id());
		req.setFlow_id(extReq.getFlow_id());
		req.setFlow_trace_id(extReq.getFlow_trace_id());
		if ("订单领取".equals(operType)) {
			req.setComments("工号: " + user.getUserid() + operType + ":" + orderTree.getOrder_id());
		} else {
			req.setComments(operType);
		}

		req.setHandler_type(Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
		req.setType_code(Const.ORDER_HANDLER_TYPE_DEFAULT);
		this.ordFlowManager.insertOrderHandLog(req);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getOrderIntentCount() throws Exception {
		Map retMap = new HashMap();

		try {
			if (StringUtil.isEmpty(this.order_id))
				throw new Exception("传入订单编号为空");

			ES_WORK_ORDER todoParm = new ES_WORK_ORDER();
			todoParm.setOrder_id(this.order_id);
			todoParm.setStatus("0");
			int todo = this.workCustomCfgManager.qryWorkOrderCount(todoParm, null);

			ES_WORK_ORDER doneParm = new ES_WORK_ORDER();
			doneParm.setOrder_id(this.order_id);
			doneParm.setStatus("1");
			int done = this.workCustomCfgManager.qryWorkOrderCount(doneParm, null);

			retMap.put("code", "0");
			retMap.put("todo", todo);
			retMap.put("done", done);
		} catch (Exception e) {
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}

		this.json = com.alibaba.fastjson.JSONObject.toJSONString(retMap);

		return JSON_MESSAGE;
	}

	public String photosChangeSms() {
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		Map data = new HashMap<String, String>();
		String afterurl = baseDaoSupport
				.queryForString("select e.short_url from es_order_extvtl e where e.order_id='" + order_id + "'");
		String url = afterurl;
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		if (StringUtils.isEmpty(url)) {
			DwzCnCreateReq req = new DwzCnCreateReq();
			req.setNotNeedReqStrOrderId(order_id);
			DwzCnCreateResp resp = client.execute(req, DwzCnCreateResp.class);
			if (!StringUtil.isEmpty(resp.getCode() + "") && "0".equals(resp.getCode() + "")) {
				url = resp.getShortUrl();
			} else {

				// 记录失败短信及次数
				ecsOrdManager.messages_send_count(order_id);

				this.json = "{result:1,message:'短信发送失败：" + resp.getErrMsg() + "'}";
				return JSON_MESSAGE;
			}
		}
		/*
		 * Map data = new HashMap<String, String>(); DwzCnCreateReq req = new
		 * DwzCnCreateReq(); req.setNotNeedReqStrOrderId(order_id); ZteClient client =
		 * ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		 * DwzCnCreateResp resp = client.execute(req, DwzCnCreateResp.class); String url
		 * = "";
		 * if(!StringUtil.isEmpty(resp.getCode()+"")&&"0".equals(resp.getCode()+"")){
		 * url = resp.getShortUrl(); }else{
		 * 
		 * //记录失败短信及次数 ecsOrdManager.messages_send_count(order_id);
		 * 
		 * this.json = "{result:1,message:'短信发送失败："+resp.getErrMsg()+"'}"; return
		 * JSON_MESSAGE; }
		 */
		OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String phone_owner_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
				AttrConsts.PHONE_OWNER_NAME);
		String phone_num = ordertree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getPhone_num();
		String ship_tel = ordertree.getOrderDeliveryBusiRequests().get(0).getShip_mobile();
		if (StringUtil.isEmpty(ship_tel)) {
			ship_tel = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "receiver_mobile");
		}
		if (StringUtil.isEmpty(ship_tel)) {

			// 记录失败短信及次数
			ecsOrdManager.messages_send_count(order_id);

			this.json = "{result:1,message:'无联系号码，短信发送失败'}";
			return JSON_MESSAGE;
		}
		// url = "1232123";
		data.put("name", phone_owner_name.substring(0, 1) + "**");
		data.put("phone_num", phone_num);
		data.put("upload_url", url);
		// 您的【${orgname}订单池】收到待处理订单${orderid}（订单编号），可用工号【${userid}】登录订单中心领取处理。
		IRemoteSmsService localRemoteSmsService = ApiContextHolder.getBean("localRemoteSmsService");
		String smsContent = localRemoteSmsService.getSMSTemplate("SMS_PHOTOS_UPLOAD", data);
		String cmcContent = localRemoteSmsService.getSMSTemplate("CMC_PHOTOS_UPLOAD", data);
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String UNICOM_NUM_SECTION = cacheUtil.getConfigInfo("UNICOM_NUM_SECTION");
		String[] unicom_num_section = UNICOM_NUM_SECTION.split(",");
		boolean flag = false;
		for (int i = 0; i < unicom_num_section.length; i++) {
			if (unicom_num_section[i].equals(ship_tel.substring(0, unicom_num_section[i].length()))) {
				flag = true;
				break;
			}
		}
		if (flag) {
			AopSmsSendReq smsSendReq = new AopSmsSendReq();
			smsSendReq.setBill_num("10010");// 短信发送号码
			smsSendReq.setService_num(ship_tel);// 接受号码--省内联通号码
			smsSendReq.setSms_data(smsContent);
			AopSmsSendResp sms_resp = client.execute(smsSendReq, AopSmsSendResp.class);
			if (StringUtil.isEmpty(sms_resp.getCode()) || !"00000".equals(sms_resp.getCode())) {

				// 记录失败短信及次数
				ecsOrdManager.messages_send_count(order_id);

				this.json = "{result:1,message:'短信发送失败：" + sms_resp.getMsg() + "'}";
				return JSON_MESSAGE;
			}
		} else {
			/*
			 * this.json = "{result:1,message:'短信发送失败：暂不支持发送异网号码短信'}"; return JSON_MESSAGE;
			 */
			CmcSmsSendReq cmcSendReq = new CmcSmsSendReq();
			cmcSendReq.setNotNeedReqStrOrderId(order_id);
			cmcSendReq.setMessageContent(cmcContent);
			cmcSendReq.setUserNumber(ship_tel);
			CmcSmsSendResp cmcSendResp = client.execute(cmcSendReq, CmcSmsSendResp.class);

			if (StringUtil.isEmpty(cmcSendResp.getResultcode()) || !"0".equals(cmcSendResp.getResultcode())) {
				String msg = "";
				List<Map> list = this.getConsts("DIC_YXT_SMS_RESULT");
				for (Map m : list) {
					String pkey = (String) m.get("pkey");
					if (cmcSendResp.getResultcode().equals(pkey)) {
						msg = (String) m.get("pname");
						break;
					}
				}

				// 记录失败短信及次数
				ecsOrdManager.messages_send_count(order_id);

				this.json = "{result:1,message:'短信发送失败：" + msg + "'}";
				return JSON_MESSAGE;
			}
		}

		// 记录成功短信及次数
		ecsOrdManager.messages_send_count(order_id);

		String insert_sql = " insert into es_order_photos_audit_logs (photos_audit_id,order_id,audit_user_id,audit_time,audit_msg,source_from) values (?,?,?,sysdate,?,'ECS') ";
		AdminUser user = ManagerUtils.getAdminUser();
		baseDaoSupport.execute(insert_sql,
				new String[] { baseDaoSupport.getSequences("s_es_order"), order_id, user.getUserid(), "照片审核不通过" });
		String currFlowTraceId = ordertree.getOrderExtBusiRequest().getFlow_trace_id();
		ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
		ordertree.getOrderExtBusiRequest().setIf_Send_Photos("8");
		String update_sql = " update es_order_ext set if_send_photos='8' where order_id='" + order_id + "' ";
		baseDaoSupport.execute(update_sql, null);

		if (StringUtils.isEmpty(afterurl)) {
			// 更新短连接sql
			String short_url_sql = cacheUtil.getConfigInfo("SAVE_SHORT_URL");
			if (!StringUtils.isBlank(url)) {
				short_url_sql = short_url_sql.replace("{url}", url);
			}
			short_url_sql = short_url_sql.replace("{order_id}", order_id);
			baseDaoSupport.execute(short_url_sql, null);
		}
		cache.set(RequestStoreManager.NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY + "order_id" + order_id,
				ordertree, RequestStoreManager.time);
		String action_url = RuleFlowUtil.getOrderUrl(order_id, currFlowTraceId);
		if (action_url != null && action_url.indexOf("?") != -1) {
			action_url += "&order_id=" + order_id;
		} else {
			action_url += "?order_id=" + order_id;
		}
		if (!StringUtil.isEmpty(is_order_exp) && "1".equals(is_order_exp)) {
			action_url += "&is_order_exp=" + is_order_exp;
		}
		if (!StringUtil.isEmpty(query_type)) {
			action_url += "&query_type=" + query_type;
		}
		this.json = "{result:0,message:'操作成功',action_url:'" + action_url + "'}";
		return JSON_MESSAGE;
	}

	private String checkFee(String orderid) {

		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, null, SpecConsts.TYPE_ID);
		String order_from = ordertree.getOrderExtBusiRequest().getOrder_from();
		String check_fee = cacheUtil.getConfigInfo("CHECK_FEE_" + order_from + "_" + type_id);
		if (!StringUtil.isEmpty(check_fee)) {
			String Adsl_addr = ordertree.getOrderAdslBusiRequest().get(0).getAdsl_addr();
			if (StringUtil.isEmpty(Adsl_addr) || "-1".equals(Adsl_addr)) {
				this.json = "{result:1,message:'请选择标准地址'}";
				return null;
			}
			String County_id = ordertree.getOrderAdslBusiRequest().get(0).getCounty_id();
			if (StringUtil.isEmpty(County_id) || "-1".equals(County_id)) {
				this.json = "{result:1,message:'请选择营业县份并保存'}";
				return null;
			}
			ResourcePreCheckReq req = new ResourcePreCheckReq();
			ResourcePreCheckResp rsp = new ResourcePreCheckResp();

			req.setNotNeedReqStrOrderId(order_id);
			String eparchy_code = GetAreaInfoUtils.getEparchyCode(order_id);
			Map<String, Object> map = new HashMap<String, Object>();
			Map attrMap = getPageOrderAttrMap(order_id);
			/*
			 * String adslId = ecsOrdManager.getAttrIdByName("adsl_addr"); String countyId =
			 * ecsOrdManager.getAttrIdByName("county_id"); String exchcode =
			 * ecsOrdManager.getAttrIdByName("exch_code"); String adsl_addr = (String)
			 * attrMap.get("oattrinstspec_" + order_id + "_" + adslId + "_" + adslId +
			 * "#adsl_addr"); String county_id = (String) attrMap.get("oattrinstspec_" +
			 * order_id + "_" + countyId + "_" + countyId + "#county_id"); String exch_code
			 * = (String) attrMap.get("oattrinstspec_" + order_id + "_" + exchcode + "_" +
			 * exchcode + "#exch_code");
			 */
			map.put("ANTI_TYPE", "0");// 根据标准地址预判
			map.put("ANTI_MODE", "0");// 0-按产品目录预判
			map.put("EPARCHY_CODE", eparchy_code);// 地市编码 Todo
			map.put("TRADE_TYPE_CODE", "0010");// 业务受理类型
			map.put("SHARE_TYPE", "N");// 共线类型
			map.put("PROD_CAT_ID", "P04");// 产品目录编码
			map.put("EXCH_CODE", ordertree.getOrderAdslBusiRequest().get(0).getExch_code());// 局向编码 post_code
			map.put("CITY_CODE", ordertree.getOrderAdslBusiRequest().get(0).getCounty_id());// 营业区编码
			map.put("SERVICE_CLASS_CODE", "0040");// 共线电信类型
			map.put("AREA_CODE", GetAreaInfoUtils.getAreaCode(eparchy_code));// 共线区号
			map.put("ADDRESS_CODE", ordertree.getOrderAdslBusiRequest().get(0).getAdsl_addr());// 标准地址编码
																								// standard_addr_id
			JSONObject json = JSONObject.fromObject(map);
			req.setMsg(json);
			String service_type = "";
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			rsp = client.execute(req, ResourcePreCheckResp.class);
			String goods_service_type = CommonDataFactory.getInstance().getGoodSpec(order_id, null,
					AttrConsts.ORDER_SERVICE_TYPE);
			String goods_service_name = CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_SERVICE_TYPE",
					goods_service_type);
			if (StringUtils.equals(rsp.getCode(), EcsOrderConsts.INF_RESP_CODE_00000)) {
				if (null == rsp.getRespJson().getBRAND_LIST() || rsp.getRespJson().getBRAND_LIST().size() == 0) {
					// return "1,无可用资源";
					this.json = "{result:1,message:'无可用资源'}";
					return null;
				} else {
					List<BrandInfoList> brand_list = rsp.getRespJson().getBRAND_LIST();
					int m = 0;
					String brand_name = "";
					List<String> brandCodes = new ArrayList<String>();
					for (BrandInfoList brandInfoList : brand_list) {
						//
						String brand_code = brandInfoList.getBRAND_CODE();
						if (StringUtils.isNotEmpty(brand_code)) {
							brandCodes.add(brand_code);
						}
						if (StringUtils.equals(goods_service_type, brand_code)) {
							m = 1;
							brand_name += CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_SERVICE_TYPE",
									brand_code) + "，";
							// break;
						} else {
							brand_name += CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_SERVICE_TYPE",
									brand_code) + "，";
						}
					}
					// 将资源预判返回的业务类型入库
					service_type = StringUtils.join(brandCodes, ",");
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "line_type" },
							new String[] { service_type });
					if (m == 1) {
					} else {
						// return "1,标准地址支持"+brand_name+"当前商品类型为"+goods_service_name+"，不允许审核，请重新选择商品。";
						this.json = "{result:1,message:'标准地址支持" + brand_name + "当前商品类型为" + goods_service_name
								+ "，不允许审核，请重新选择商品。'}";
						return null;
					}
				}
			} else {
				this.json = "{result:1,message:'资源预判接口调用失败'}";
				return null;
			}

			// String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, null,
			// SpecConsts.TYPE_ID);
			BusiCompRequest busi = new BusiCompRequest();
			Map queryParams = new HashMap();
			queryParams.put("order_id", order_id);
			if ("20021".equals(type_id)) {
				busi.setEnginePath("zteActTraceRule.openAccountBroadbandFee");
			} else {
				busi.setEnginePath("zteCommonTraceRule.businessFeeQry");
			}
			busi.setOrder_id(order_id);
			busi.setQueryParams(queryParams);
			BusiCompResponse busiResp = CommonDataFactory.getInstance().execBusiComp(busi);
			if (ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())) {
			} else {
				// return "1,营业费用查询失败";
				this.json = "{result:1,message:'营业费用查询失败'}";
				return null;

			}
			busi = new BusiCompRequest();
			queryParams = new HashMap();
			queryParams.put("order_id", order_id);
			busi.setEnginePath("zteCommonTraceRule.schemeFeeQry");
			busi.setOrder_id(order_id);
			busi.setQueryParams(queryParams);
			busiResp = CommonDataFactory.getInstance().execBusiComp(busi);
			if (ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())) {
			} else {
				// return "1,活动费用查询失败";
				this.json = "{result:1,message:'活动费用查询失败'}";
				return null;
			}
			ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
			Double paymoney = ordertree.getOrderBusiRequest().getOrder_amount() * 1000;
			int orderFee = paymoney.intValue();
			List<AttrFeeInfoBusiRequest> attrFeeList = ordertree.getFeeInfoBusiRequests();
			String Phone_deposit = ordertree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest()
					.getPhone_deposit();
			Double schemefee = new Double(Phone_deposit == null ? "0.00" : Phone_deposit) * 1000;
			int schemeFee = schemefee.intValue();
			int businessFee = 0;
			if (attrFeeList != null && attrFeeList.size() > 0) {
				for (int i = 0; i < attrFeeList.size(); i++) {
					AttrFeeInfoBusiRequest attrFee = attrFeeList.get(i);
					Double N_fee_num = attrFee.getN_fee_num() * 1000;
					businessFee += N_fee_num.intValue();
				}
			}

			if (orderFee == (businessFee + schemeFee)) {
				// return "0,校验通过";
				this.json = "{result:0,message:'校验通过'}";
				return null;
			} else {
				ordertree.getOrderBusiRequest().setPaymoney(new Double(businessFee + schemeFee) / 1000);
				String sql = "update es_order set paymoney=?  where order_id=?";
				IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
				baseDaoSupport.execute(sql, (businessFee + schemeFee) / 1000, order_id);
				// 更新订单树缓存
				cache.set(RequestStoreManager.NAMESPACE,
						RequestStoreManager.DC_TREE_CACHE_NAME_KEY + "order_id" + order_id, ordertree,
						RequestStoreManager.time);
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "order_realfee" },
						new String[] { ((businessFee + schemeFee) / 1000) + "" });
				// return "2,订单费用("+new Double(orderFee/1000)+")与实际费用("+new
				// Double((businessFee+schemeFee)/1000)+")不一致,确认后以实际费用收费。";
				this.json = "{result:2,message:'订单费用(" + new Double(orderFee / 1000) + ")与实际费用("
						+ new Double((businessFee + schemeFee) / 1000) + ")不一致,确认后以实际费用收费。'}";
				return null;
			}
		} else {
			// return "0,不需要校验";
			this.json = "{result:0,message:'不需要校验'}";
			return null;
		}

	}

	/**
	 * 根据人员权限过滤地市，县分
	 * 
	 * @throws Exception
	 */
	private void filterRegionCountyByPermission() throws Exception {
		// 查询配置
		AdminUser user = ManagerUtils.getAdminUser();

		if (EcsOrdManager.QUERY_TYPE_ORDER_VIEW.equals(this.query_type) && (!"1".equals(user.getPermission_level()))) {
			// 订单查询，不是省级权限，过滤地市
			List<String> pRegionList = user.getPermission_region();
			if (pRegionList == null)
				pRegionList = new ArrayList<String>();

			Set<String> regionSet = new HashSet<String>(pRegionList);

			if (this.regionList != null && this.regionList.size() > 0) {
				Iterator<Regions> it = this.regionList.iterator();

				for (; it.hasNext();) {
					Map region = (Map) it.next();

					if (!regionSet.contains(region.get("region_id"))) {
						it.remove();
					}
				}
			}

			if ("2".equals(user.getPermission_level())) {
				// 地市权限
				// 根据地市查询县分
				this.countyList = this.ordWorkManager.getBusiCountyByIds(pRegionList, null);
			} else if ("3".equals(user.getPermission_level())) {
				// 县分权限
				// 根据县分编码营业县分
				this.countyList = this.ordWorkManager.getBusiCountyByIds(null, user.getPerm_busi_county());
			}
		} else {
			// 是省级权限，查询全量县分
			this.countyList = this.ordWorkManager.getBusiCountyByIds(null, null);
		}
	}

	public String getOrderTreeById() throws Exception {
		Map retMap = new HashMap();

		try {
			if (StringUtils.isBlank(this.order_id))
				throw new Exception("传入订单编号为空");

			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(this.order_id);

			retMap.put("code", "0");
			retMap.put("orderTree", orderTree);

		} catch (Exception e) {
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}

		this.json = com.alibaba.fastjson.JSONObject.toJSONString(retMap);

		return JSON_MESSAGE;
	}

	public String getMainnumber() {
		if (!StringUtil.isEmpty(order_id)) {
			mainnumber = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "mainnumber");
		}
		return mainnumber;
	}

	public void setMainnumber(String mainnumber) {
		this.mainnumber = mainnumber;
	}

	public String getField_names() {
		return field_names;
	}

	public void setField_names(String field_names) {
		this.field_names = field_names;
	}

	public String getOption_stypes() {
		return option_stypes;
	}

	public void setOption_stypes(String option_stypes) {
		this.option_stypes = option_stypes;
	}

	// add by cqq 自定义流程 ---客户联系页面
	public String getCustomerContactNewUrl() throws Exception {
		this.goodsSetTerminalName();
		return "customer_contact_new";
	}

	// add by cqq 自定义流程 ---订单审核页面
	public String getOrderAuditUrl() throws Exception {
		return "order_audit";
	}

	// add by cqq 自定义流程 ---开户页面
	public String getOpenAccountUrl() throws Exception {
		return "open_account";
	}

	// add by cqq 自定义流程 ---写卡页面
	public String getWriteCardUrl() throws Exception {
		return "write_card";
	}

	// add by cqq 自定义流程 ---发货页面
	public String getDeliverGoodsUrl() throws Exception {
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		orderTree.getOrderExtBusiRequest().setAbnormal_status("1");
		if (EcsOrderConsts.SHIPPING_QUICK_02.equals(orderTree.getOrderExtBusiRequest().getShipping_quick())) {
			deal_type = "闪电送";
			if (!StringUtil.isEmpty(orderTree.getOrderExtBusiRequest().getQuick_ship_company_code())) {
				this.listShiipingCop();
				for (Map m : shipping_cop_list) {
					String pkey = (String) m.get("pkey");
					if (orderTree.getOrderExtBusiRequest().getQuick_ship_company_code().equals(pkey)) {
						quick_shipping_cop = (String) m.get("pname");
						break;
					}
				}
			}
			String q_status = orderTree.getOrderExtBusiRequest().getQuick_ship_status();
			if (!StringUtil.isEmpty(q_status)) {
				this.listQuickStatus();
				for (Map m : quick_status_list) {
					String key = (String) m.get("pkey");
					if (q_status.equals(key)) {
						quick_shipping_status = (String) m.get("pname");
						break;
					}
				}
			}
		}
		if (orderTree.getOrderDeliveryBusiRequests() != null && orderTree.getOrderDeliveryBusiRequests().size() > 0) {
			delivery = orderTree.getOrderDeliveryBusiRequests().get(0);
			List<Map> list = this.getConsts("shipping_company");
			String cop_code = delivery.getShipping_company();
			if (!StringUtil.isEmpty(cop_code)) {
				shipping_cop_id = cop_code;
				for (Map m : list) {
					String pkey = (String) m.get("pkey");
					if (cop_code.equals(pkey)) {
						shipping_cop_name = (String) m.get("pname");
						break;
					}
				}
			}
		}

		// 获取副卡信息
		this.setOrderPhoneInfoFukaList(orderTree.getOrderPhoneInfoFukaBusiRequests());

		return "deliver_goods";
	}

	public String getAttrValuesBatch() throws Exception {
		Map retMap = new HashMap();

		try {
			if (StringUtils.isNotBlank(this.order_id) && StringUtils.isNotBlank(this.field_names)) {
				String[] arr = this.field_names.split(",");
				List<String> attr_names = Arrays.asList(arr);

				Map ret = this.ecsOrdManager.getAttrValuesBatch(this.order_id, attr_names);

				retMap.put("attrs", ret);
			}

			if (StringUtils.isNotBlank(this.option_stypes)) {
				List<String> option_list = new ArrayList<String>();

				String[] stypes = this.option_stypes.split(",");
				option_list = Arrays.asList(stypes);

				Map<String, List<ATTR_OPTIONS>> op_ret = this.ecsOrdManager.getAttrOptions(option_list);

				retMap.put("options", op_ret);
			}

			retMap.put("code", "0");
		} catch (Exception e) {
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}

		this.json = com.alibaba.fastjson.JSONObject.toJSONString(retMap);

		return JSON_MESSAGE;
	}

	/**
	 * 查询自定义流程日志
	 * 
	 * @throws Exception
	 */
	private void getWorkCustomLog() throws Exception {
		// 自定义流程查询自定义流程日志
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);

		if (orderTree != null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())) {
			IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");

			StringBuilder builder = new StringBuilder();
			builder.append(" SELECT a.node_name as trace_name, ");
			builder.append(" '' as type_name, ");
			builder.append(" a.remark as comments, ");
			builder.append(" a.create_date as create_time, ");
			builder.append(" a.op_name ");
			builder.append(" FROM es_work_custom_log a ");
			builder.append(" WHERE a.order_id='" + this.order_id + "' ");

			List<OrderHandleLogsReq> ret = baseDaoSupport.queryForListByMap(builder.toString(),
					OrderHandleLogsReq.class, null);

			if (ret != null && ret.size() > 0)
				this.logsList.addAll(ret);
		}
	}

	// add by cqq 自定义流程 ---订单归档页面
	public String getOrderArchivingUrl() throws Exception {
		return "order_archiving";
	}

	// add by cqq 自定义流程 ---线下派单
	public String getDispatchUrl() throws Exception {
		work_type=this.is_fzn;
		this.webpage = ordWorkManager.getWorkListByOrderId(this.order_id.replace(",", "").trim(), this.getPage(),
				this.getPageSize());
		this.goodsSetTerminalName();
		return "dispatch_url";
	}

	/**
	 * 客户联系页面 add by cqq 20181227
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getOrderCustomByOrderId() throws Exception {
		Map retMap = new HashMap();

		try {
			if (StringUtil.isEmpty(this.order_id))
				throw new Exception("传入订单编号为空");

			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			List<OrderDeliveryBusiRequest> orderDeliveryBusi = orderTree.getOrderDeliveryBusiRequests();

			String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
					AttrConsts.ORDER_CITY_CODE);
			countyList = ordWorkManager.getIntentCountyList(order_city_code);

			Map customOrder = new HashMap();
			String district_code = orderDeliveryBusi.get(0).getRegion_id() + "";
			customOrder.put("district_code", "ZJ" + district_code);
			customOrder.put("ship_name",
					CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_NAME));
			customOrder.put("certi_num",
					CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM));
			customOrder.put("ship_addr",
					CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_ADDR));

			retMap.put("code", "0");
			retMap.put("customOrder", customOrder);
			retMap.put("countyList", countyList);
			String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String can_update = cacheUtil.getConfigInfo("XSXX_CAN_UPDATE_" + order_from);
			if (StringUtil.isEmpty(can_update)) {
				can_update = "yes";
			}
			retMap.put("can_update", can_update);
		} catch (Exception e) {
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}

		this.json = com.alibaba.fastjson.JSONObject.toJSONString(retMap);

		return JSON_MESSAGE;
	}

	/**
	 * 客户联系-修改受理人信息
	 * 
	 * @author cqq 20181227
	 * @return
	 * @throws Exception
	 */
	public String updateCustomOrder() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map retMap = new HashMap();

		try {

			if (StringUtil.isEmpty(this.order_id))
				throw new Exception("传入订单编号为空");

			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			List<OrderDeliveryBusiRequest> orderDeliveryBusi = orderTree.getOrderDeliveryBusiRequests();

			String update_remark = "";
			String old_ship_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_NAME);
			String old_ship_addr = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_ADDR);
			String old_cert_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM);
			String old_district_code = "ZJ" + orderDeliveryBusi.get(0).getRegion_id() + "";
			String old_region_id = orderDeliveryBusi.get(0).getRegion_id() + "";
			String old_region_name = orderDeliveryBusi.get(0).getRegion();

			if (!StringUtil.isEmpty(this.district_code)) {
				if (!this.district_code.equals(old_district_code)) {
					String raw_org_name = ecsOrdManager.queryOrgName(old_district_code);
					String update_org_name = ecsOrdManager.queryOrgName(this.district_code);

					logger.info("order_id:" + this.order_id);
					logger.info("原order_county_code:" + old_district_code);
					logger.info("修order_county_code:" + this.district_code);
					update_remark += "'县分'原值：'" + raw_org_name + "',修改为:'" + update_org_name + "'。";

					old_region_id = this.district_code.substring(2);
					old_region_name = update_org_name;
				}
			} else {
				// 前台传入值为空，认为不修改，将值设为旧值
				this.setDistrict_code(old_district_code);
			}

			if (!StringUtil.isEmpty(this.ship_name)) {
				if (!this.ship_name.equals(old_ship_name)) {
					update_remark += "'预约受理姓名'原值：'" + old_ship_name + "',修改为:'" + this.ship_name + "'。";
				}
			} else {
				// 前台传入值为空，认为不修改，将值设为旧值
				this.setShip_name(old_ship_name);
			}

			if (!StringUtil.isEmpty(this.ship_addr)) {
				if (!this.ship_addr.equals(old_ship_addr)) {
					update_remark += "'预约受理地址'原值：'" + old_ship_addr + "',修改为:'" + this.ship_addr + "'。";
				}
			} else {
				// 前台传入值为空，认为不修改，将值设为旧值
				this.setShip_addr(old_ship_addr);
			}

			if (!StringUtil.isEmpty(this.certi_num)) {
				if (!this.certi_num.equals(old_cert_num)) {
					update_remark += "'预约受理身份证'原值：'" + old_cert_num + "',修改为:'" + this.certi_num + "'。";
				}
			} else {
				// 前台传入值为空，认为不修改，将值设为旧值
				this.setCerti_num(old_cert_num);
			}

			// String county_id = ecsOrdManager.queryCountyCoding("ZJ"+this.district_code);

			// 更新
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.SHIP_NAME },
					new String[] { this.ship_name });
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CERT_CARD_NUM },
					new String[] { this.certi_num });
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.SHIP_ADDR },
					new String[] { this.ship_addr });
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.DISTRICT_CODE },
					new String[] { this.district_code });
			// CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new
			// String[]{AttrConsts.REGION_ID}, new String[]{old_region_id});

			orderDeliveryBusi.get(0).setRegion_id(Long.valueOf(old_region_id));
			orderDeliveryBusi.get(0).setRegion(old_region_name);
			orderDeliveryBusi.get(0).setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderDeliveryBusi.get(0).setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderDeliveryBusi.get(0).store();

			OrderBusiRequest orderBusi = orderTree.getOrderBusiRequest();
			String shipping_area = orderDeliveryBusi.get(0).getProvince() + "-" + orderDeliveryBusi.get(0).getCity()
					+ "-" + orderDeliveryBusi.get(0).getRegion();
			orderBusi.setShipping_area(shipping_area);
			orderBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderBusi.store();

			// 更新订单树缓存
			// cache.set(RequestStoreManager.NAMESPACE,
			// RequestStoreManager.DC_TREE_CACHE_NAME_KEY+"order_id"+ order_id,orderTree,
			// RequestStoreManager.time);
			retMap.put("code", "0");

		} catch (Exception e) {
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}

		this.json = com.alibaba.fastjson.JSONObject.toJSONString(retMap);

		return JSON_MESSAGE;
	}

	public String getField_values() {
		return field_values;
	}

	public void setField_values(String field_values) {
		this.field_values = field_values;
	}

	/**
	 * 更新意向单客户联系结果
	 * 
	 * @param value_map
	 * @throws Exception
	 */
	private void updateIntentContactResults(Map<String, String> value_map) throws Exception {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(this.order_id);

		ES_ORDER_INTENT param = new ES_ORDER_INTENT();
		param.setOrder_id(this.order_id);
		List<ES_ORDER_INTENT> ret = this.workCustomCfgManager.qryOrderIntentList(param, null);

		// 自定义流程有意向单的，更新意向单的客户联系结果
		if (orderTree != null && ret != null && ret.size() > 0
				&& "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& value_map.containsKey("contact_results_frist") && value_map.containsKey("contact_results_second")) {
			String contact_results_frist = value_map.get("contact_results_frist");
			String contact_results_second = value_map.get("contact_results_second");

			ES_ORDER_INTENT intent = ret.get(0);
			intent.setContact_results_frist(contact_results_frist);
			intent.setContact_results_second(contact_results_second);

			this.workCustomCfgManager.updateOrderIntent(intent);
		}
	}

	public String updateAttrsValuesBatch() throws Exception {
		Map retMap = new HashMap();

		try {
			if (StringUtils.isBlank(this.order_id))
				throw new Exception("传入订单编号为空");

			if (StringUtils.isBlank(this.field_names))
				throw new Exception("传入属性字符串为空");

			if (StringUtils.isBlank(this.field_values))
				throw new Exception("传入属性值字符串为空");

			String[] name_arr = this.field_names.split(",");
			String[] value_arr = this.field_values.split(",");

			if (name_arr.length == 0)
				throw new Exception("未传入需要修改的属性");

			if (name_arr.length != value_arr.length)
				throw new Exception("需要修改的属性和属性值数量不一致");

			List<String> name_list = Arrays.asList(name_arr);
			Set<String> name_set = new HashSet<String>(name_list);

			Map<String, String> value_map = new HashMap<String, String>();

			// 将字段和值的字符串转换为MAP
			for (int i = 0; i < name_arr.length; i++) {
				String key = name_arr[i];
				String value = value_arr[i];

				// 前台需要将空字符串转换为“null”字符串传入
				if ("null".equals(value))
					value = "";

				value_map.put(key, value);
			}

			if (name_set.contains("district_code") && !name_set.contains("county_id")) {
				// 有行政县分但是没有营业县分
				String district_code = value_map.get("district_code");

				String code = district_code;
				if (!code.contains("ZJ"))
					code = "ZJ" + code;

				String busi_county = this.ecsOrdManager.queryCountyCoding(code);

				value_map.put("county_id", busi_county);
				value_map.put("district_code", code.replace("ZJ", ""));
			} else if (!name_set.contains("district_code") && name_set.contains("county_id")) {
				// 有营业县分但是没有行政县分
				String county_id = value_map.get("county_id");
				List<String> county_list = new ArrayList<String>();
				county_list.add(county_id);

				List<String> ret = this.ecsOrdManager.transBusiCounty2County(county_list);

				if (ret != null && ret.size() > 0) {
					String district_code = ret.get(0);

					value_map.put("district_code", district_code.replace("ZJ", ""));
				}
			}
			
			if (name_set.contains("province_id") && name_set.contains("city_code") && name_set.contains("area_code")) {				
				//物流信息省份地市县分校验 --zwh
				String provinceId = value_map.get("province_id");
				String cityId = value_map.get("city_code");
				String areaId = value_map.get("area_code");				
				if (org.apache.commons.lang.StringUtils.isBlank(provinceId)) {
					if (org.apache.commons.lang.StringUtils.isBlank(cityId)) {
						if (!org.apache.commons.lang.StringUtils.isBlank(areaId)) {
							String CityPreCode = this.getParentCode(areaId);
						    value_map.put("city_code", CityPreCode);
						    String ProvinceCode = this.getParentCode(CityPreCode);
						    value_map.put("province_id", ProvinceCode);
						}
					}else {
						String ProvinceCode = this.getParentCode(cityId);						
					    value_map.put("province_id", ProvinceCode);
					}
				}				
				//物流信息省份地市县分地址保存  --zwh												
				if (!org.apache.commons.lang.StringUtils.isBlank(provinceId)) {
				    String name = this.getProName(provinceId);
				    value_map.put("province", name);
				}
				if (!org.apache.commons.lang.StringUtils.isBlank(cityId)) {
					String name = this.getProName(cityId);
				    value_map.put("city", name);
				}
				if (!org.apache.commons.lang.StringUtils.isBlank(areaId)) {
					String name = this.getProName(areaId);
				    value_map.put("region", name);
				}
			}
			

			// 将MAP装换为字段数组和值数组
			if (value_map.size() > 0) {
				int size = value_map.size();
				name_arr = new String[size];
				value_arr = new String[size];

				Iterator<Entry<String, String>> it = value_map.entrySet().iterator();

				int index = 0;
				for (; it.hasNext();) {
					Entry<String, String> entry = it.next();

					String key = entry.getKey();
					String value = entry.getValue();

					if (org.apache.commons.lang.StringUtils.isBlank(value) || "null".equals(value)) {
						value = "";
					}

					name_arr[index] = key;
					value_arr[index] = value;

					index++;
				}

				CommonDataFactory.getInstance().updateAttrFieldValue(this.order_id, name_arr, value_arr);
			}

			// 更新意向单客户联系结果
			this.updateIntentContactResults(value_map);

			retMap.put("code", "0");
			retMap.put("msg", "更新成功");
		} catch (Exception e) {
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}

		this.json = com.alibaba.fastjson.JSONObject.toJSONString(retMap);

		return JSON_MESSAGE;
	}
	
	//省份地市县分获取父级编码
	private String getParentCode(String str) {
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		String getPreNameSql = "select es.pre_code from es_regions_zb es where es.code = ? ";
		List list = new ArrayList();
	    list = baseDaoSupport.queryForList(getPreNameSql, str);
	    String preCode = "";
	    if(list != null && list.size() > 0) {
	    	preCode = String.valueOf(((Map) list.get(0)).get("pre_code"));
	    }	    
		return preCode;
	}
	//省份地市县分根据编码获取中文名称
	private String getProName(String str) {
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		String getNameSql = "select es.name from es_regions_zb es where es.code = ? ";
		List list = new ArrayList();
		list = baseDaoSupport.queryForList(getNameSql, str);
		String preName = "";
	    if(list != null && list.size() > 0) {
	    	preName = String.valueOf(((Map) list.get(0)).get("name"));
		}	    
		return preName;
		}

	// 静态数据取顶级种子专业线
	private void listTopSeedProfessionalLine() {
		top_seed_professional_line_list = ecsOrdManager.getDcSqlByDcName("top_seed_professional_line");
		if (top_seed_professional_line_list == null) {
			top_seed_professional_line_list = new ArrayList<Map>();
		}
		Map m0 = new HashMap();
		m0.put("value", "-1");
		m0.put("value_desc", "--请选择--");
		top_seed_professional_line_list.add(0, m0);

	}

	// 静态数据取顶级种子类型
	private void listTopSeedType() {
		top_seed_type_list = ecsOrdManager.getDcSqlByDcName("top_seed_type");
		if (top_seed_type_list == null) {
			top_seed_type_list = new ArrayList<Map>();
		}
		Map m0 = new HashMap();
		m0.put("value", "-1");
		m0.put("value_desc", "--请选择--");
		top_seed_type_list.add(0, m0);

	}

	// 静态数据取顶级种子类型
	private void listTopSeedGroup() {
		top_seed_group_list = ecsOrdManager.getDcSqlByDcName("top_seed_group_id");
		if (top_seed_group_list == null) {
			top_seed_group_list = new ArrayList<Map>();
		}
		Map m0 = new HashMap();
		m0.put("value", "-1");
		m0.put("value_desc", "--请选择--");
		top_seed_group_list.add(0, m0);

	}

	private void workCutomOrderContinue(String flow_trace_id) throws Exception {
		// 根据订单编号查询自定义流程当前环节
		ES_WORK_CUSTOM_NODE_INS pojo = new ES_WORK_CUSTOM_NODE_INS();
		pojo.setOrder_id(this.order_id);
		pojo.setIs_curr_step("1");

		List<ES_WORK_CUSTOM_NODE_INS> inses = this.workCustomCfgManager.qryInsList(pojo, null);

		String curr_old_node_code = "";

		if (inses != null && inses.size() > 0
				&& org.apache.commons.lang.StringUtils.isNotBlank(inses.get(0).getOld_node_code())) {
			// 获取当前环节的旧环节编码
			curr_old_node_code = inses.get(0).getOld_node_code();
		}

		if (!curr_old_node_code.equals(flow_trace_id)) {
			// 环节校验失败，抛出异常
			throw new Exception("正在处理页面为" + flow_trace_id + "，实际处理环节为" + curr_old_node_code + "与实际不一致，请确认是否重复执行。'}");
		}
		IWorkCustomEngine workCustomEngine = SpringContextHolder.getBean("WorkCustomEngine");
		;
		// 继续执行流程
		WORK_CUSTOM_FLOW_DATA flowData = workCustomEngine.continueWorkFlow(this.order_id);

		// 执行失败的抛出异常
		if (ConstsCore.ERROR_FAIL.equals(flowData.getRun_result()))
			throw new Exception(flowData.getRun_msg());
	}

	public String getOpenAccountErrorUrl() throws Exception {
		return "open_account_error";
	}

	public String getWriteCardErrorUrl() throws Exception {
		return "write_card_error";
	}

	public String getBasicBackNodeUrl() throws Exception {
		this.goodsSetTerminalName();
		return "basic_back_node";
	}
	public void goodsSetTerminalName() {
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		String order_id = this.order_id;
		OrderTreeBusiRequest  order_Tree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String goods_id = order_Tree.getOrderBusiRequest().getGoods_id();
		String terminal="";
		this.cat_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);
		if("90000000000000901".equals(this.cat_id)) {
			 terminal = CommonDataFactory.getInstance().getAttrFieldValueHis(order_id,"object_name");
             if(!org.apache.commons.lang.StringUtils.isBlank(terminal)) {
            	 this.terminal_name=terminal;
             }else {
            	 StringBuffer sql_terminal_name = new StringBuffer();
    			 sql_terminal_name.append("select b.terminal_name from es_order_extvtl a left join ES_GOODS_ACTION_ELEMENT b ")
    			 .append(" on a.element_id=b.element_id  ")
    			 .append(" where a.mobile_type=b.mobile_type and a.source_from=b.source_from and a.order_id='").append(this.order_id)
    			 .append("' and b.goods_id='").append(goods_id).append("' ");
    			 List<Map<String, Object>> list_terminal_name = baseDaoSupport.queryForList(sql_terminal_name.toString());
    			 if(list_terminal_name != null && list_terminal_name.size()>0 
    					 && list_terminal_name.get(0).containsKey("terminal_name")) {
    				 this.terminal_name = String.valueOf(list_terminal_name.get(0).get("terminal_name"));
    				 this.terminal_name = terminal_name == null ? "" : terminal_name;
    			 }
             }
			 
		}
	}

	public String getCancelOrderComfirmUrl() throws Exception {
		return "CancelOrderComfirm";
	}

	public String getWorkflowJumpComfirmUrl() throws Exception {
		return "WorkflowJumpComfirm";
	}

	public List<Map> getTop_seed_professional_line_list() {
		return top_seed_professional_line_list;
	}

	public void setTop_seed_professional_line_list(List<Map> top_seed_professional_line_list) {
		this.top_seed_professional_line_list = top_seed_professional_line_list;
	}

	public List<Map> getTop_seed_type_list() {
		return top_seed_type_list;
	}

	public void setTop_seed_type_list(List<Map> top_seed_type_list) {
		this.top_seed_type_list = top_seed_type_list;
	}

	public List<Map> getTop_seed_group_list() {
		return top_seed_group_list;
	}

	public void setTop_seed_group_list(List<Map> top_seed_group_list) {
		this.top_seed_group_list = top_seed_group_list;
	}
	
}