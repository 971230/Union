package com.ztesoft.net.action;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.powerise.ibss.framework.FrameException;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.api.internal.utils.WebUtils;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.AES;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.ecsord.params.ecaop.req.CardInfoGetAPPReq;
import com.ztesoft.net.ecsord.params.ecaop.req.GroupOrderCardCheckReq;
import com.ztesoft.net.ecsord.params.ecaop.req.GroupOrderFixedNetworkCheckReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.CardInfoGetBSSResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ResourCecenterAppResp;
import com.ztesoft.net.ecsord.params.ecaop.vo.ApiTerminalCheckResReqVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.CheckCreateCardResultReqVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.GroupOrderApiTerminal;
import com.ztesoft.net.ecsord.params.ecaop.vo.GroupUnibssBoVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.RESOURCES_INFOVO;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.model.Logi;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.RequestStoreManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.model.OrdReceipt;
import com.ztesoft.net.rule.util.RuleFlowUtil;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IOrdBSSTacheManager;
import com.ztesoft.net.service.IOrdCollectManagerManager;
import com.ztesoft.net.service.IOrdOpenAccountTacheManager;
import com.ztesoft.net.service.IOrdVisitTacheManager;
import com.ztesoft.net.service.IOrdWorkManager;
import com.ztesoft.net.service.IOrdWriteCardTacheManager;
import com.ztesoft.net.service.IOrderExtManager;
import com.ztesoft.net.service.IOrderFlowManager;
import com.ztesoft.net.service.IOrderSupplyManager;
import com.ztesoft.net.service.impl.EcsOrdManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomCfgManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomEngine;
import com.ztesoft.net.util.ZjCommonUtils;

import commons.CommonTools;
import consts.ConstsCore;
import net.sf.json.JSONObject;
import params.ZteResponse;
import params.order.req.Iphone6sReq;
import params.order.req.OrderExceptionLogsReq;
import params.order.req.OrderHandleLogsReq;
import params.order.resp.OrderExceptionCollectResp;
import params.req.OperationRecordReq;
import params.resp.CrawlerResp;
import params.resp.OperationRecordResp;
import rule.impl.OrdExceptionHandleImpl;
import util.EssOperatorInfoUtil;
import util.ThreadLocalUtil;
import util.Utils;
import zte.net.ecsord.common.AttrBusiInstTools;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.bss.req.NumberResourceQueryZjBssRequest;
import zte.net.ecsord.params.bss.resp.NumberResourceQueryZjBssResponse;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrInstBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageFukaBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageSubProdBusiRequest;
import zte.net.ecsord.params.busi.req.HuashengOrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderAdslBusiRequest;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderInternetBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtvtlBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemsAptPrintsBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemsValiLogBusiRequest;
import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoFukaBusiRequest;
import zte.net.ecsord.params.busi.req.OrderRealNameInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderSpProductBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.OrderTerminalCheckReq;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.resp.CheckIDECAOPResponse;
import zte.net.ecsord.params.ecaop.resp.OrderTerminalCheckResp;
import zte.net.ecsord.params.pub.req.OrderSubPackageList;
import zte.net.ecsord.params.sf.req.RouteServiceRequest;
import zte.net.ecsord.params.sf.resp.RouteServiceResponse;
import zte.net.ecsord.params.sf.vo.Route;
import zte.net.ecsord.params.sf.vo.RouteResponse;
import zte.net.ecsord.params.taobao.resp.TaobaoIdentityGetResponse;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_NODE_INS;
import zte.net.ecsord.params.workCustom.po.WORK_CUSTOM_FLOW_DATA;
import zte.net.ecsord.params.wyg.req.NumberChangeWYGRequest;
import zte.net.ecsord.params.wyg.resp.NotifyOrderInfoWYGResponse;
import zte.net.ecsord.params.zb.req.NumberResourceQueryZbRequest;
import zte.net.ecsord.params.zb.resp.CertCheckZBResponse;
import zte.net.ecsord.params.zb.resp.NumberResourceQueryZbResponse;
import zte.net.ecsord.params.zb.vo.QueryParasZb;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.rule.warehouse.WareHouseFact;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.ecsord.utils.DataUtil;
import zte.net.ecsord.utils.SpecUtils;
import zte.net.iservice.ILogsServices;
import zte.net.iservice.IOrderServices;
import zte.net.iservice.IUosService;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.req.RuleExeLogDelReq;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;
import zte.net.params.resp.RuleExeLogDelResp;
import zte.net.params.resp.RuleTreeExeResp;
import zte.params.process.req.UosDealReq;
import zte.params.process.resp.UosDealResp;
import zte.params.process.resp.WorkItem;

public class OrderFlowAction extends WWAction {
	@Resource
	private IOrderSupplyManager iOrderSupplyManager;
	@Resource
	private IOrderExtManager orderExtManager;
	@Resource
	private IEcsOrdManager ecsOrdManager;
	private IOrderFlowManager ordFlowManager;// 流程处理类
	@Resource
	protected IDcPublicInfoManager dcPublicInfoManager;
	private IOrdVisitTacheManager ordVisitTacheManager; // 订单预校验
	private IOrdBSSTacheManager ordBSSTacheManager;
	@Resource
	private OrdExceptionHandleImpl ordExceptionHandleImpl;
	@Resource
	private IOrdCollectManagerManager ordCollectManager;
	@Resource
	protected IUosService uosService;
	@Resource
	private IOrdOpenAccountTacheManager ordOpenAccountTacheManager;

	@Resource
	private IOrdWriteCardTacheManager ordWriteCardTacheManager;

	@Resource
	private ILogsServices logsServices;

	@Resource
	private IOrdWorkManager ordWorkManager;
	
	@Resource
	private IWorkCustomCfgManager workCustomCfgManager;
	
	@Resource
	private IWorkCustomEngine workCustomEngine;

	/* 订单预处理 */
	private String order_status;// 订单状态
	private String order_id;// 订单ID
	private String guhua_number;// 固话号码
	private String ext_order_id;
	private String acceptanceTp;// 受理单模版编码
	private String accept_print_type;// 受理单打印模式
	private String acceptanceForm;// 受理单模版内容
	private String sending_type;// 配送方式
	private Long provinc_code;// 省
	private Long city_code;// 市
	private Long district_id;// 区
	private List provList;// 省列表
	private List cityList;// 市列表
	private List districtList;// 区列表
	private String development_point_name;//发展点名称
	private String development_point_code;//发展点编码
	private String developmentCode;// 发展人编码
    private String developmentName;// 发展人名称
    private String cbss_develop_code;//cbss发展人名称
	private String cbss_development_point_code;//cbss发展人编码
	private String ordercitycode;// 订单地市代码，用于订单补录
	private String mainnumber;// 主卡号码，针对副卡开户
    //ICCID  成卡卡号
    //old_terminal_num  终端型号
    //terminal_num 终端串号   add by sgf
	private String ICCID_INFO;
	private String old_terminal_num;
	private String terminal_num;
	private String object_name;
	// @Resource
	// private IRuleService ruleService;

	// 货品信息字段
	private List proTypeList;// 货品类型
	private List proCatList;// 货品小类

	private IOrderServices orderServices;
	private String product_type;// 货品类型
	private String cat_id;// 小类
	private String brand_code;// 品牌编码

	private String[] orderidArray;

	private List giftBrandList;
	private List<AttrGiftInfoBusiRequest> giftInfoList;// 礼品信息列表
	private List<AttrFeeInfoBusiRequest> feeInfoList;// 费用信息列表
	private String feeInfo;
	private int giftInfoSize;
	private Map buyInfoMap;// 买家电话 地址
	private String tel;
	private String address;
	private String mobile;
	private String isTerm;// 是否存在终端 1 是 0 否
	private String isAgree;// 证件照图片是否一致 1 是 0 否
	// 处理信息
	private String dealType;// 处理类型
	private String dealDesc;// 处理内容
	private String dealDescIE8;// IE8 无法取到页面表单的这个数据，临时增加一个变量

	private List<Route> signRoute;// 发货路由信息
	private List<Route> receiptRoute;// 回单路由信息
	private String logi_no;// 运单号
	private String receipt_no;// 回单号
	private String sign_status;// 签收状态
	private String receipt_status;// 回单状态
	private List<Logi> logiCompanyList;// 物流公司
	private String carray_free; // 运费
	private String protect_free; // 保费
	private String n_shipping_amount; // 实收运费
	private String shipping_company; // 物流公司
	private String prod_audit_status; // 质检稽核状态
	private OrdReceipt ordReceipt;// 回单对象
	private OrderTreeBusiRequest orderTree;
	private OrderDeliveryBusiRequest delivery;
	private String ic_Card;
	private String order_is_his;// 1-历史单
	// 规则ID
	private String rule_id;
	// 方案ID
	private String plan_id;
	// 开户方式
	private String openAccountType;

	// 包含页面
	private String includePage;
	// 资料补录页面
	private String replenishPage;
	private List validateMsgList;
	private String writeCardResult;// 页面写卡结果
	private String need_receipt;
	private String oldCertNum;// 旧的证件号码 供预校验比较
	private String btnType;// 按钮类型

	private List<Iphone6sReq> relationOrders;// 关联订单列表
	private String isShowBindRadio;// 是否可操作绑定
	private String commentsText;// 订单环节录入备注

	// 商品原价
	private String good_price_system;
	// 号码预存
	private String num_price;
	// 多交预存
	private String deposit_price;
	// 开户费用
	private String openAcc_price;
	private Page orderhisPage;
	private String orderhisPageRowCount;
    private List<Map> Termain_list; // 订单来源

	private List orderchangeList;
	private Map orderDtl;
	// 副卡信息保存
	private String first_load; // 是否首次加载
	private Map<String, String> updateParam = new HashMap<String, String>();
	private Map operatorInfoMap;//
	
	private String goods_id;//商品id
	private String goods_name;//商品名称
	private String goods_type_name;//商品大类名称
	
	//add by mahui
	private String mobile_type; //终端类型
	private String scheme_id; //活动编码
	private String element_id; //元素编码
	private String mobile_imei;//终端串号
	private String object_esn;//终端串号
	private String terminal_name; //终端名称
	
	public String getTerminal_name() {
		return terminal_name;
	}

	public void setTerminal_name(String terminal_name) {
		this.terminal_name = terminal_name;
	}

	public String getObject_esn() {
		return object_esn;
	}

	public void setObject_esn(String object_esn) {
		this.object_esn = object_esn;
	}

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

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getGoods_type_name() {
		return goods_type_name;
	}

	public void setGoods_type_name(String goods_type_name) {
		this.goods_type_name = goods_type_name;
	}

	public Map getOperatorInfoMap() {
		return operatorInfoMap;
	}

	public void setOperatorInfoMap(Map operatorInfoMap) {
		this.operatorInfoMap = operatorInfoMap;
	}

	private String kingcard_status;
	
	public String getDevelopment_point_name() {
        return development_point_name;
    }

    public void setDevelopment_point_name(String development_point_name) {
        this.development_point_name = development_point_name;
    }

    public String getDevelopment_point_code() {
        return development_point_code;
    }

    public void setDevelopment_point_code(String development_point_code) {
        this.development_point_code = development_point_code;
    }

    public Map<String, String> getUpdateParam() {
		return updateParam;
	}

	public void setUpdateParam(Map<String, String> updateParam) {
		this.updateParam = updateParam;
	}

	public String getFirst_load() {
		return first_load;
	}

	public void setFirst_load(String first_load) {
		this.first_load = first_load;
	}

	public List getOrderchangeList() {
		return orderchangeList;
	}

	public void setOrderchangeList(List orderchangeList) {
		this.orderchangeList = orderchangeList;
	}

	private List<OrderPhoneInfoFukaBusiRequest> orderPhoneInfoFuka;

	public String getIncludePage() {
		return includePage;
	}

	public void setIncludePage(String includePage) {
		this.includePage = includePage;
	}

	private String specValidateMsg;// 每个页面的特殊检验
	private String d_type = "apply";// cfm确认 apply申请 ycl 预处理

	private double order_amount;// 订单总价
	private double discount;// 优惠价
	

	private String order_from;
	private String order_model;

	private String abnormal_type;// 异常类型

	private String query_type;// 查询类型
	private String q_check; // WMS质检稽核特殊处理（不需要做环节界面验证）
	private String order_city_code;// 订单归属地市

	private OrderPhoneInfoBusiRequest orderPhoneInfo;
	List<OrderPhoneInfoFukaBusiRequest> orderPhoneInfoFukaList;// 副卡列表
	private String phone_num;
	private Map<String, String> phoneQueryMap;
	private Map<String, String> phoneInfoMap;

	private String queryPara_03;// 号码查询条件 号码关键字
	private String queryPara_04;// 号码查询条件 靓号等级
	private List<Map> operatorStateList;// 号码状态映射集合
	private List phoneChangeStateList;// 副卡号码变更状态字典集合

	// 号码查询表
	private String selNumChannel;// 选号途径
	private String queryPara_10;// 省份号码查询条件
	private String segmentCode;// 号码查询网别 号码头（185/186）
	private String queryPara_21_Keywords;// 查询关键字
	private String queryPara_22_NumLevel;// 查询靓号等级
	private String infIsZb;// 是否走总部号码查询接口

	private String queryTypeChooseReturnVal;// 总部号码页面查询类型返回参数
	private String queryTypeZbChooseReturnVal;// 总部号码查询查询条件选项返回参数
	private String queryPara_24_numRange;// 总部号码查询查询范围
	private String queryPara_23_prepaidProductCode;// 总部号码查询预付费产品编码
	private String queryPara_30_serType;// 销售类型

	private String provinceQueryPara_001;// 号码地市
	private String provinceQueryPara_002;// 随机类型
	private String provinceQueryPara_003;// 返回个数

	// private String pageSizeReturn;//号码查询返回单页显示的条目数量

	private String ys_order_id;// iphone6s 预收单id
	List<OrderItemExtvtlBusiRequest> orderItemExtvtlBusiRequests;// 多商品对象
	private String resourceCodeStr;// 多商品串号拼接字符串
	private String resourceItemsStr;// 多商品串号Item_id拼接字符串

	private String numType;// 主副卡类型
	private String old_phone_num;// 变更前的号码
	private String fukaInstId;// 变更号码的主键
	private String isQuery;// 是否默认查询
	private String isChangePhone = "no";// 是否可以变更号码
	/**
	 * ZX add 2015-12-31 start 副卡号码
	 */
	private List<String> fuka_phonenum;
	/**
	 * ZX add 2015-12-31 end 副卡号码
	 */
	private String sp_inst_id;// 业务补办实例id
	private String operation;// 业务补办操作内容
	private String operationType; // 业务补办类型
	// 附加产品信息
	private List<OrderSubPackageList> subProductList = new ArrayList<OrderSubPackageList>();
	private List<Map> list;
	private OrderRealNameInfoBusiRequest orderRealNameInfoBusiRequest;
	private String active_type;

	private Map<String, String> certPhotosMap;

	private CrawlerResp crawlerResp;
	private List ordReturnedReasonList;// 退单原因列表
	private String reasonDesc;// 退单原因
	private String outcall_type_c;// 外呼类型

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
	private String orders;// 多个单号以','隔开
	private String show_flag;// 用于判断是有展示退款下拉
	private String adsl_account;// 宽带账号
	private String adsl_number;// 宽带号码
	private String user_kind;// 用户种类
	private String exch_code;// 局向编码
	private String adsl_grid;// 网格
	private String appt_date;// 预约装机时间
	private String adsl_addr;// 标准地址
	private String temp_adsl_addr_desc;//临时地址
	private String user_address; // 用户地址
	private String county_id;// 营业县分
	private String moderm_id;// 光猫ID
	private String devic_gear;//新设备档位 00：标准版(光猫) 01：加强版(光猫)
	private String sale_mode;//销售模式：01：免费租用 06：用户自购 07：用户自备用户自备
	private String is_done_active;//是否竣工生效 1：是 0：不是
	private String service_type;// 业务类型
	private String county_name;
	
	private Map topSeedInfoMap;
	private Map<String, Object> intentDetail;// 意向单详情

	public String getCounty_name() {
		return county_name;
	}

	public void setCounty_name(String county_name) {
		this.county_name = county_name;
	}
	
	//add by cqq 20181129 自定义流程详情
	public String onAndOffCustomFlow() {
		return "custom_flow";
	}
	
	public String returnedOrd() {
		
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		if (orderTree == null) { // 如果确认退单操作后进入该页面,订单可能已经归档
			orderTree = CommonDataFactory.getInstance().getOrderTreeHis(order_id);
		}
		List<OrderDeliveryBusiRequest> list = orderTree.getOrderDeliveryBusiRequests();
		if (list != null && list.size() > 0) {
			delivery = list.get(0);
		}
		this.giftInfoList = orderTree.getGiftInfoBusiRequests();

		// 获取副卡细腻
		this.setOrderPhoneInfoFukaList(orderTree.getOrderPhoneInfoFukaBusiRequests());

		// 获取价格信息
		getPrice(orderTree);

		// 附加产品信息
		this.setSubProductList(CommonDataFactory.getInstance().getSubProdPackList(orderTree));
		// this.dealDesc="test";
		this.ordReturnedReasonList();
		if ("cfm".equals(d_type) || "cfmn".equals(d_type) || "bss".equals(d_type)) {

			this.getReason(order_id);
			return "returned_ord_cfm";
		} else {
			return "returnedOrd";
		}
	}

	private void getReason(String order_id) {
		String reasonCode = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.RETURNED_REASON_CODE);
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
	}

	public String reBackToPrepick() {
		try {
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
			// 退单订单不允许回退，防止回退后删除退单规则执行日志
			if (EcsOrderConsts.REFUND_STATUS_08.equals(orderExt.getRefund_status())// 退单申请
					|| EcsOrderConsts.REFUND_STATUS_01.equals(orderExt.getRefund_status())// 退单确认通过
					|| EcsOrderConsts.REFUND_STATUS_02.equals(orderExt.getRefund_status())// BSS返销
					|| EcsOrderConsts.REFUND_STATUS_03.equals(orderExt.getRefund_status())) {// ESS返销
				this.json = "{result:1,message:'订单已退单或退单申请，不允许回退'}";
				return this.JSON_MESSAGE;
			}

			// 华盛订单不允许回退
			String platType = orderExt.getPlat_type();
			if (StringUtils.equals(EcsOrderConsts.PLAT_TYPE_10061, platType)) {
				this.json = "{result:1,message:'华盛订单不允许回退'}";
				return this.JSON_MESSAGE;
			}

			String lockMsg = checkLockUser();
			if (!StringUtils.isEmpty(lockMsg)) {
				this.json = "{result:1,message:'" + lockMsg + "'}";
				return this.JSON_MESSAGE;
			}
			BusiCompResponse response = ordFlowManager.reBackToPrepick(order_id);
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			String action_url = this.getActionUrl(orderTree.getOrderExtBusiRequest().getFlow_trace_id(), order_id);
			this.json = "{result:0,message:'回退成功',action_url:'" + action_url + "'}";
		} catch (Exception ex) {
			ex.printStackTrace();
			this.json = "{result:1,message:'回退失败'}";
		}
		return JSON_MESSAGE;
	}

	/**
	 * 增加回退到回访功能
	 * 
	 * @author zengxianlian
	 */
	public String reBackToVisit() {
		try {
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
			// 退单订单不允许回退，防止回退后删除退单规则执行日志
			if (EcsOrderConsts.REFUND_STATUS_08.equals(orderExt.getRefund_status())// 退单申请
					|| EcsOrderConsts.REFUND_STATUS_01.equals(orderExt.getRefund_status())// 退单确认通过
					|| EcsOrderConsts.REFUND_STATUS_02.equals(orderExt.getRefund_status())// BSS返销
					|| EcsOrderConsts.REFUND_STATUS_03.equals(orderExt.getRefund_status())) {// ESS返销
				this.json = "{result:1,message:'订单已退单或退单申请，不允许回退'}";
				return this.JSON_MESSAGE;
			}
			// 华盛订单不允许回退
			String platType = orderExt.getPlat_type();
			if (StringUtils.equals(EcsOrderConsts.PLAT_TYPE_10061, platType)) {
				this.json = "{result:1,message:'华盛订单不允许回退'}";
				return this.JSON_MESSAGE;
			}
			// 预提交过后不能回退  add -wjq
			String activeNo=orderExtManager.getActiveNo(order_id);
			if (!StringUtils.isEmpty(activeNo)) {
				this.json = "{result:1,message:'该订单已过预提交,不允许回退'}";
				return this.JSON_MESSAGE;
			}

			String lockMsg = checkLockUser();
			if (!StringUtils.isEmpty(lockMsg)) {
				this.json = "{result:1,message:'" + lockMsg + "'}";
				return this.JSON_MESSAGE;
			}
			/*
			 * if (EcsOrderConsts.WRITE_CARD_STATUS_5.equals(orderTree.
			 * getOrderExtBusiRequest().getWrite_card_status())) { this.json =
			 * "{result:1,message:'写卡成功,不能回退'}"; return this.JSON_MESSAGE; }
			 */

			BusiCompResponse response = ordFlowManager.reBackToVisit(order_id);
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			String action_url = this.getActionUrl(orderTree.getOrderExtBusiRequest().getFlow_trace_id(), order_id);
			this.json = "{result:0,message:'回退成功',action_url:'" + action_url + "'}";
		} catch (Exception ex) {
			ex.printStackTrace();
			this.json = "{result:1,message:'回退失败'}";
		}
		return JSON_MESSAGE;
	}

	public String preDealOrd() {
		long start = System.currentTimeMillis();
		Long time1= System.currentTimeMillis();
		String order_id = this.order_id;
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		int order_status = orderTree.getOrderBusiRequest().getStatus();

		this.orderTree = orderTree;
		ext_order_id = orderTree.getOrderExtBusiRequest().getOut_tid();
		this.delivery = CommonDataFactory.getInstance().getDeliveryBusiRequest(order_id, "0");
		this.feeInfoList = orderTree.getFeeInfoBusiRequests();
		this.orderPhoneInfo = orderTree.getPhoneInfoBusiRequest();
		this.setOrderPhoneInfoFukaList(orderTree.getOrderPhoneInfoFukaBusiRequests());
		// 获取号码操作结果字典值
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		this.operatorStateList = dcPublicCache.getdcPublicExtList(StypeConsts.OPERATOR_STATE);// 页面用到的号码状态集合
		this.phoneChangeStateList = dcPublicCache.getdcPublicExtList(EcsOrderConsts.PHONE_CHANGE_STATE);

		String isOld = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		
		String isAop = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_AOP);
		if ((StringUtils.equals(isAop, EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP) || StringUtils.equals(isAop, EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS))) {
			this.isChangePhone = "yes";
		}
		Long time2= System.currentTimeMillis();
		logger.info("性能测试****************************订单"+order_id+"详情1耗时："+(time2-time1));
		this.order_amount = orderTree.getOrderBusiRequest().getOrder_amount();
		this.discount = orderTree.getOrderBusiRequest().getDiscount();
		// 收货信息|物流信息
		if (orderTree.getOrderDeliveryBusiRequests().size() > 0) {
			OrderDeliveryBusiRequest deliverBusiReq = orderTree.getOrderDeliveryBusiRequests().get(0);
			this.provinc_code = deliverBusiReq.getProvince_id();
			this.city_code = deliverBusiReq.getCity_id();
			this.district_id = deliverBusiReq.getRegion_id();
		}
		this.provList = this.ordFlowManager.getProvList();
		this.shipping_company = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIPPING_COMPANY);
		Long prov_code = this.provinc_code;
		if (prov_code == null || "".equals(prov_code)) {
			prov_code = Long.parseLong(((Map) provList.get(0)).get("code").toString());
		}
		this.cityList = this.ordFlowManager.getCityList(prov_code);
		Long city_id = this.city_code;
		this.districtList = this.ordFlowManager.getDistrictList(city_id);
		String city_name = null;
		if (city_id == null || "".equals(city_id)) {
			Map cityMap = (Map) this.cityList.get(0);
			city_id = Long.parseLong(Const.getStrValue(cityMap, "code"));
			city_name = Const.getStrValue(cityMap, "name");
		}
		if (city_id != null || !"".equals(city_id)) {
			for (int i = 0; i < this.cityList.size(); i++) {
				Map cityMap = (Map) this.cityList.get(i);
				String id = Const.getStrValue(cityMap, "code");
				if (id.equals(city_id + "")) {
					city_name = Const.getStrValue(cityMap, "name");
					break;
				}
			}
			// 收货区县增加地市，本地商城临时方案收货区县没传
			/*
			 * Map city = new HashMap(); city.put("code", city_id);
			 * city.put("name", city_name); if(!districtList.contains(city)){
			 * districtList.add(0, city); }
			 */
		}

		// 获取物流公司
		logiCompanyList = ecsOrdManager.logi_company(order_id);
		Long time3= System.currentTimeMillis();
		logger.info("性能测试****************************订单"+order_id+"详情2耗时："+(time3-time2));
		// 货品类型处理
		// 先获取货品类型 货品小类根据商品类型查询 货品品牌也是根据商品类型查询 货品型号根据品牌ID查询
		// this.proTypeList =
		// CommonDataFactory.getInstance().getGoodsTypeList("product");//货品类型
		// product_type =
		// CommonDataFactory.getInstance().getAttrFieldValue(order_id,
		// AttrConsts.PRODUCT_TYPE);
		String pro_type = this.product_type;
		if (pro_type == null && proTypeList != null && proTypeList.size() > 0) {
			pro_type = ((GoodsType) proTypeList.get(0)).getType_id().toString();
		}
		if (!StringUtils.isEmpty(pro_type)) {
			this.proCatList = CommonDataFactory.getInstance().getCatsByTypeId(pro_type);
		}

		// 受理单信息
		List<OrderItemsAptPrintsBusiRequest> printList = orderTree.getOrderItemBusiRequests().get(0).getOrderItemsAptPrintsRequests();
		if (printList != null && printList.size() > 0) {
			this.acceptanceTp = printList.get(0).getReceipt_code();// 受理单模版编码
			this.accept_print_type = printList.get(0).getReceipt_mode();// 受理单打印模式
		}
		// 获取礼品列表
		this.giftInfoList = orderTree.getGiftInfoBusiRequests();
		this.giftInfoSize = giftInfoList.size();
		// 获取礼品品牌列表
		this.giftBrandList = CommonDataFactory.getInstance().getBrandByTypeId(SpecConsts.TYPE_ID_10007);

		String member_id = orderTree.getOrderBusiRequest().getMember_id();
		this.buyInfoMap = new HashMap();
		this.buyInfoMap = this.ordFlowManager.getBuyInfoByMemberId(member_id);
		this.specValidateMsg = this.ordFlowManager.getSpecErrorMsg(order_id);

		// 获取异常类型
		this.abnormal_type = orderTree.getOrderExtBusiRequest().getAbnormal_type();
		this.order_model = orderTree.getOrderExtBusiRequest().getOrder_model();

		if ("view_goods_info_more".equals(includePage)) {// 多商品货品详情页面
			orderItemExtvtlBusiRequests = orderTree.getOrderItemExtvtlBusiRequests();
			if (orderItemExtvtlBusiRequests != null && orderItemExtvtlBusiRequests.size() > 0) {
				for (int i = 0; i < orderItemExtvtlBusiRequests.size(); i++) {
					String goods_type = orderItemExtvtlBusiRequests.get(i).getGoods_type();
					String goods_type_name = SpecUtils.getGoodsTypeNameById(goods_type);
					orderItemExtvtlBusiRequests.get(i).setGoods_type_name(goods_type_name);
				}
			}
		}
		String out_tid = orderTree.getOrderExtBusiRequest().getOut_tid();
        List<Map<String, String>> esOrderQueue = this.ordFlowManager.getServiceCodeByObjectId(out_tid);
        Map<String, String>  developParams  = this.orderExtManager.changeDevelopmetInfo(order_id,orderTree,esOrderQueue);
        this.development_point_code = developParams.get("development_point_code");
        this.development_point_name = developParams.get("development_point_name");
        //sgf 20190813
	    this.cbss_development_point_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "cbss_development_point_code");
	    this.cbss_develop_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "cbss_develop_code");

       // this.developmentCode = developParams.get("developmentCode");
       //this.developmentName = developParams.get("developmentName");
		// 获取价格信息
		getPrice(orderTree);

		// 附加产品信息
		this.setSubProductList(CommonDataFactory.getInstance().getSubProdPackList(orderTree));
		Long time4= System.currentTimeMillis();
		logger.info("性能测试****************************订单"+order_id+"详情3耗时："+(time4-time3));
		String kingcardstatus  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "kingcard_status");
		List stateList = ecsOrdManager.getDcSqlByDcName("DC_ORDER_KINGCARD_STATE");
		if (!StringUtils.isEmpty(kingcardstatus)) {
			if (stateList != null) {
				for (int i = 0; i < stateList.size(); i++) {
					String value = Const.getStrValue((Map) stateList.get(i), "value");
					String value_desc = Const.getStrValue((Map) stateList.get(i), "value_desc");
					if (StringUtil.equals(value, kingcardstatus)) {
						kingcard_status = value_desc;
						break;
					}
				}
			}
		}
		
		operatorInfoMap = ecsOrdManager.getOperatorInfo(order_id);
		topSeedInfoMap = ecsOrdManager.gettopSeedInfo(order_id);
		goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		if (EcsOrderConsts.GOODS_TYPE_DKD.equals(goods_type) || EcsOrderConsts.GOODS_TYPE_RONGHE.equals(goods_type) || goods_type.equals("180101547042001934")||goods_type.equals("180441456282001431")) {
			// 宽带信息
			p_code = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10054, SpecConsts.P_CODE);// 合约编码
			p_name = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10054, SpecConsts.P_NAME);// 合约名称
			city = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10054, SpecConsts.CITY);// 地市
			packge_limit = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10054, SpecConsts.PACKAGE_LIMIT);// 合约期
			net_speed = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10053, SpecConsts.NET_SPEED);// 速率
			
			plan_title = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10053, SpecConsts.BSS_NAME);// 套餐名称
			ess_code = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10053, SpecConsts.BSS_CODE);// 套餐编码
			terminal_type = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10055, SpecConsts.TERMINAL_TYPE);// 终端类型
			service_type = CommonDataFactory.getInstance().getGoodSpec(order_id, null, AttrConsts.ORDER_SERVICE_TYPE);// 业务类型
			if(orderTree.getOrderAdslBusiRequest() != null && orderTree.getOrderAdslBusiRequest().size()>0){
			    adsl_account = orderTree.getOrderAdslBusiRequest().get(0).getAdsl_account();
			    adsl_number = orderTree.getOrderAdslBusiRequest().get(0).getAdsl_number();
			    user_kind = orderTree.getOrderAdslBusiRequest().get(0).getUser_kind();
			    exch_code = orderTree.getOrderAdslBusiRequest().get(0).getExch_code();
			    adsl_grid = orderTree.getOrderAdslBusiRequest().get(0).getAdsl_grid();
			    appt_date = orderTree.getOrderAdslBusiRequest().get(0).getAppt_date();
			    adsl_addr = orderTree.getOrderAdslBusiRequest().get(0).getAdsl_addr();
			    user_address = orderTree.getOrderAdslBusiRequest().get(0).getUser_address();
			    county_id = orderTree.getOrderAdslBusiRequest().get(0).getCounty_id();
			    moderm_id = orderTree.getOrderAdslBusiRequest().get(0).getModerm_id();
			    sale_mode = orderTree.getOrderAdslBusiRequest().get(0).getSale_mode();
			    devic_gear = orderTree.getOrderAdslBusiRequest().get(0).getDevic_gear();
			    is_done_active = orderTree.getOrderAdslBusiRequest().get(0).getIs_done_active();
			    access_type = orderTree.getOrderAdslBusiRequest().get(0).getAccess_type();
			}
			if(StringUtil.isEmpty(access_type)){
				access_type = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10054, SpecConsts.ACCESS_TYPE);// 接入方式
			}
			
			if(adsl_addr == null || "".equals(adsl_addr.trim())
					||  "0".equals(adsl_addr.trim()) || "-1".equals(adsl_addr.trim())){
				//标准地址编号为空，设置临时地址属性用于页面展示
			    if(orderTree.getOrderAdslBusiRequest() != null && orderTree.getOrderAdslBusiRequest().size()>0){
			        temp_adsl_addr_desc = orderTree.getOrderAdslBusiRequest().get(0).getAdsl_addr_desc();
			    }else{
			        temp_adsl_addr_desc="";
			    }
			}else{
				temp_adsl_addr_desc = "";
			}
			 
			
			List countyList = ecsOrdManager.getDcSqlByDcName("DC_ORDER_BUSICTY_NEW");
			if (!StringUtils.isEmpty(county_id)) {
				if (countyList != null) {
					for (int i = 0; i < countyList.size(); i++) {
						String value = Const.getStrValue((Map) countyList.get(i), "value");
						String value_desc = Const.getStrValue((Map) countyList.get(i), "value_desc");
						if (StringUtil.equals(value, county_id)) {
							county_name = value_desc;
							break;
						}
					}
				}
			}

		}
		
		if(StringUtils.isEmpty(county_name)){
		       this.county_name = this.ordFlowManager.getCountyName(this.district_id+"",null);
		}
		
		Long time5= System.currentTimeMillis();
		logger.info("性能测试****************************订单"+order_id+"详情4耗时："+(time5-time4));
		long end = System.currentTimeMillis();
		logger.info("用时：" + (end - start));
		
		if (ZjEcsOrderConsts.DIC_ORDER_STATUS_11 == order_status) {
			return "callOutPreDeal_Order";
		} else {
			//商品信息
			this.goods_id=orderTree.getOrderBusiRequest().getGoods_id();
			IDaoSupport baseDaoSupport=SpringContextHolder.getBean("baseDaoSupport");
			StringBuffer sql_goods_info=new StringBuffer();
			sql_goods_info.append("select eg.name goods_name, egt.name goods_type_name ").
			append(" from es_goods eg left join es_goods_type egt on eg.type_id = egt.type_id ").
			append(" where  eg.source_from='").append(ManagerUtils.getSourceFrom()).append("' ").append(" and egt.source_from ='").
			append(ManagerUtils.getSourceFrom()).append("' and ").append("eg.goods_id='").append(goods_id).append("'");
			 List<Map<String, String>> listInfo = baseDaoSupport.queryForList(sql_goods_info.toString());
			 if(listInfo.size()>0){
				 this.goods_name=listInfo.get(0).get("goods_name");
				 this.goods_type_name=listInfo.get(0).get("goods_type_name");
		        }
			 if(ZjEcsOrderConsts.DIC_ORDER_STATUS_2 == order_status) {
				 this.cat_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);
				 this.terminal_name="";
				 String terminal = CommonDataFactory.getInstance().getAttrFieldValueHis(order_id,"object_name");
	             if(!org.apache.commons.lang.StringUtils.isBlank(terminal)) {
	            	 this.terminal_name=terminal;
	             }else {
	            	 StringBuffer sql_terminal_name = new StringBuffer();
					 sql_terminal_name.append("select b.terminal_name from es_order_extvtl a left join ES_GOODS_ACTION_ELEMENT b ")
					 .append(" on a.element_id=b.element_id  ")
					 .append(" where a.mobile_type=b.mobile_type and a.source_from=b.source_from and a.order_id='").append(this.order_id)
					 .append("' and b.goods_id='").append(this.goods_id).append("' ");
					 List<Map<String, Object>> list_terminal_name = baseDaoSupport.queryForList(sql_terminal_name.toString());
					 if(list_terminal_name != null && list_terminal_name.size()>0 
							 && list_terminal_name.get(0).containsKey("terminal_name")) {
						 this.terminal_name = String.valueOf(list_terminal_name.get(0).get("terminal_name"));
						 this.terminal_name = terminal_name == null ? "" : terminal_name;
					 }
	             }
				 
			 }

			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String NEW = cacheUtil.getConfigInfo("IS_NEW_JSP");
			if(!StringUtil.isEmpty(NEW)){
				return NEW+"preDeal_Order";
			}
			return "preDeal_Order";
		}

	}

	public String getRealtionOrders() {
		// 控制关联订单的按钮和radio显示
		this.isShowBindRadio = "true";

		String order_id = this.order_id;
		if (!StringUtils.isEmpty(order_id)) {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);

			// 获取关联订单
			String orderFrom = orderTree.getOrderExtBusiRequest().getOrder_from();
			String certName = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NAME);
			String certNum = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM);
			String isreservation = orderTree.getOrderExtBusiRequest().getWm_isreservation_from();
			String flowNode = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			String bindOrderId = orderTree.getOrderExtBusiRequest().getRelated_order_id();

			// 如果当前订单是预售单、已经绑定过的正式单、非B环节、历史订单，只查与当前单绑定的订单；且不能操作绑定
			if (StringUtils.equals(isreservation, EcsOrderConsts.IS_DEFAULT_VALUE)// 预售单
					|| (!StringUtils.equals(isreservation, EcsOrderConsts.IS_DEFAULT_VALUE) && !StringUtils.isEmpty(bindOrderId))// 绑定过的正式单
					|| !StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)// 非B环节
					|| StringUtils.equals(order_is_his, EcsOrderConsts.IS_ORDER_HIS_YES)) {// 历史订单
				this.isShowBindRadio = "false";

				List<Iphone6sReq> list = new ArrayList<Iphone6sReq>();
				if (!StringUtils.isEmpty(bindOrderId)) {
					Iphone6sReq vo = new Iphone6sReq();
					vo.setOrder_id(bindOrderId);
					vo.setOut_tid(CommonDataFactory.getInstance().getAttrFieldValue(bindOrderId, AttrConsts.OUT_TID));
					vo.setPaymoney(CommonDataFactory.getInstance().getAttrFieldValue(bindOrderId, AttrConsts.ORDER_REAL_FEE));
					vo.setCert_card_name(CommonDataFactory.getInstance().getAttrFieldValue(bindOrderId, AttrConsts.CERT_CARD_NAME));
					vo.setCert_card_num(CommonDataFactory.getInstance().getAttrFieldValue(bindOrderId, AttrConsts.CERT_CARD_NUM));
					vo.setDevelopment_code(CommonDataFactory.getInstance().getAttrFieldValue(bindOrderId, AttrConsts.DEVELOPMENT_CODE));
					vo.setDevelopment_name(CommonDataFactory.getInstance().getAttrFieldValue(bindOrderId, AttrConsts.DEVELOPMENT_NAME));
					vo.setRelated_order_id(CommonDataFactory.getInstance().getAttrFieldValue(bindOrderId, AttrConsts.RELATED_ORDER_ID));
					vo.setWm_isreservation_from(CommonDataFactory.getInstance().getAttrFieldValue(bindOrderId, AttrConsts.WM_ISRESERVATION_FROM));
					list.add(vo);
				}
				this.relationOrders = list;
			} else {// B环节+正式单+没有绑定过
				this.relationOrders = this.ordFlowManager.getRelationOrders(order_id, orderFrom, certName, certNum);

				// B环节才操作按钮
				if (!StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)) {
					this.isShowBindRadio = "false";
				} // 没记录就不要显示按钮了
				else if (relationOrders.size() == 0) {
					this.isShowBindRadio = "false";
				} // 已经绑定的订单不让操作了
				else if (!StringUtils.isEmpty(orderTree.getOrderExtBusiRequest().getRelated_order_id())) {
					this.isShowBindRadio = "false";
				}
			}
		} else {
			this.isShowBindRadio = "false";
		}
		return "relations_info";
	}

	/***
	 * 订单页面包含子页面处理
	 * 
	 * @return
	 */
	public String includePage() {
		preDealOrd();
		return includePage;
	}

	public String toReplenish() {
		preDealOrd();
		return "replenish";
	}

	public String replenish() {
		preDealOrd();
		return replenishPage;
	}

	public String hsB2Cgoods() {
		OrderTreeBusiRequest orderTree = null;
		if (order_is_his != null && EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)) {// 历史单
			orderTree = CommonDataFactory.getInstance().getOrderTreeHis(order_id);
		} else {
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		}
		this.orderTree = orderTree;
		List<HuashengOrderItemBusiRequest> hsOrderItems = orderTree.getHuashengOrderItemBusiRequest();
		List<OrderItemExtvtlBusiRequest> orderItems = orderTree.getOrderItemExtvtlBusiRequests();
		list = new ArrayList<Map>();
		Map map;
		for (HuashengOrderItemBusiRequest hsOrderItem : hsOrderItems) {
			map = new HashMap();
			for (OrderItemExtvtlBusiRequest orderItem : orderItems) {
				if (StringUtils.equals(hsOrderItem.getSku(), orderItem.getSku())) {
					map.put("goods_name", orderItem.getGoods_name());
					map.put("goods_type_name", SpecUtils.getGoodsTypeNameById(orderItem.getGoods_type()));
					map.put("machine_type_name", orderItem.getMachine_type_name());
					map.put("resources_brand_name", orderItem.getResources_brand_name());
					map.put("resources_model_name", orderItem.getResources_model_name());
					map.put("resources_color", orderItem.getResources_color());
					break;
				}
			}
			map.put("menge", hsOrderItem.getMenge());
			list.add(map);
		}
		return includePage;
	}

	public String updateFeeInfo() {
		try {
			AttrFeeInfoBusiRequest feeinfo = JsonUtil.fromJson(feeInfo, AttrFeeInfoBusiRequest.class);
			String fee_inst_id = feeinfo.getFee_inst_id();
			String order_id = this.order_id;
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			feeInfoList = orderTree.getFeeInfoBusiRequests();
			for (int i = 0; i < feeInfoList.size(); i++) {
				AttrFeeInfoBusiRequest fee = feeInfoList.get(i);
				if (fee_inst_id.equals(fee.getFee_inst_id()) && !fee.getDiscount_fee().equals(feeinfo.getDiscount_fee())) {
					fee.setDiscount_fee(feeinfo.getDiscount_fee());
					fee.setN_fee_num(feeinfo.getN_fee_num());
					fee.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					fee.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					fee.store();
				}
			}
			this.json = "{result:0,message:'操作成功'}";
		} catch (Exception e) {
			e.printStackTrace();
			this.json = "{result:1,message:'操作失败'}";
		}
		return this.JSON_MESSAGE;
	}

	public String replenishSave() {
		try {
			String lockMsg = checkLockUser();
			if (!StringUtils.isEmpty(lockMsg)) {
				this.json = "{result:1,message:'" + lockMsg + "'}";
				return this.JSON_MESSAGE;
			}
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			ThreadContextHolder.setHttpRequest(getRequest());

			List<AttrInstBusiRequest> pageAttrInstBusiRequests = AttrBusiInstTools.getPageDirtyOrderAttrInst(order_id, orderTree);

			int col1Count = 0;
			for (AttrInstBusiRequest pageAttrInstBusiRequest : pageAttrInstBusiRequests) {
				// 是否闪电送和物流公司字段变动时 重新匹配模式
				if (AttrConsts.SHIPPING_QUICK.equals(pageAttrInstBusiRequest.getField_name()) || AttrConsts.SHIPPING_COMPANY.equals(pageAttrInstBusiRequest.getField_name())) {
					col1Count++;
				}
				pageAttrInstBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				pageAttrInstBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				long start2 = System.currentTimeMillis();
				pageAttrInstBusiRequest.store(); // 属性数据入库
				long end2 = System.currentTimeMillis();
				logger.info("store:==" + (end2 - start2));
			}
			this.preDealSave();
			String action_url = EcsOrderConsts.REPLENISH_URL + "?order_id=" + order_id + "&d_type=ycl";
			this.json = "{result:0,message:'操作成功',action_url:'" + action_url + "'}";
		} catch (Exception e) {
			e.printStackTrace();
			this.json = "{result:1,message:'操作失败：" + e.getMessage() + "'}";

		}
		/*
		 * try { HandlerOrderRoleReq hreq = new HandlerOrderRoleReq();
		 * hreq.setOrder_id(order_id); long start = System.currentTimeMillis();
		 * //orderServices.handlerOrderRole(hreq); long end =
		 * System.currentTimeMillis(); logger.info("订单权限处理使用时间------>" +
		 * (end - start)); } catch (Exception ex) { ex.printStackTrace(); }
		 */
		return this.JSON_MESSAGE;
	}

	/**
	 * 订单详情查询-（再用单、历史单）
	 * 
	 * @return
	 */
	public String order_detail_view() {
		String order_id = this.order_id;
		OrderTreeBusiRequest orderTree = null;
		if (order_is_his != null && EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)) {// 历史单
			orderTree = CommonDataFactory.getInstance().getOrderTreeHis(order_id);
		} else {
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		}
		this.orderTree = orderTree;
		if ("view_goods_info_more".equals(includePage)) {// 多商品货品详情页面
			orderItemExtvtlBusiRequests = orderTree.getOrderItemExtvtlBusiRequests();
			if (orderItemExtvtlBusiRequests != null && orderItemExtvtlBusiRequests.size() > 0) {
				for (int i = 0; i < orderItemExtvtlBusiRequests.size(); i++) {
					String goods_type = orderItemExtvtlBusiRequests.get(i).getGoods_type();
					String goods_type_name = SpecUtils.getGoodsTypeNameById(goods_type);
					orderItemExtvtlBusiRequests.get(i).setGoods_type_name(goods_type_name);
				}
			}
		}
		this.ext_order_id = orderTree.getOrderExtBusiRequest().getOut_tid();
		// 收货信息|物流信息
		List<OrderDeliveryBusiRequest> list = orderTree.getOrderDeliveryBusiRequests();
		OrderDeliveryBusiRequest deliverBusiReq = new OrderDeliveryBusiRequest();
		for (OrderDeliveryBusiRequest de : list) {
			if (EcsOrderConsts.DELIVERY_TYPE_NORMAL.equals(de.getDelivery_type())) {// 肯定有一条记录是正常发货的
				deliverBusiReq = de;
				break;
			}
		}
		//根据外部编号将数据从es_order_queue_his中进行判断
		String out_tid = orderTree.getOrderExtBusiRequest().getOut_tid();
		List<Map<String, String>> esOrderQueue = this.ordFlowManager.getServiceCodeByObjectId(out_tid);
		Map<String, String>  developParams  = this.orderExtManager.changeDevelopmetInfo(order_id,orderTree,esOrderQueue);
		this.development_point_code = developParams.get("development_point_code");
		this.development_point_name = developParams.get("development_point_name");
		this.developmentCode = developParams.get("developmentCode");
		this.developmentName = developParams.get("developmentName");
	     //sgf 20190813
       	this.cbss_development_point_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "cbss_development_point_code");
	    this.cbss_develop_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "cbss_develop_code");

		this.order_amount = orderTree.getOrderBusiRequest().getOrder_amount();
		this.discount = orderTree.getOrderBusiRequest().getDiscount();
		this.provinc_code = deliverBusiReq.getProvince_id();
		this.city_code = deliverBusiReq.getCity_id();
		this.district_id = deliverBusiReq.getRegion_id();
		this.provList = this.ordFlowManager.getProvList();
		this.shipping_company = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIPPING_COMPANY);
		this.order_model = orderTree.getOrderExtBusiRequest().getOrder_model();
		Long prov_code = this.provinc_code;
		if (prov_code == null || "".equals(prov_code)) {
			prov_code = Long.parseLong(((Map) provList.get(0)).get("code").toString());
		}
		this.cityList = this.ordFlowManager.getCityList(prov_code);
		
		// 获取营业县份 
		String districtCode = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.DISTRICT_CODE);
		String countyId = CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.COUNTY_ID);
		this.county_name = this.ordFlowManager.getCountyName(districtCode,countyId);
		
		Long city_id = this.city_code;
		if (city_id == null || "".equals(city_id)) {
			city_id = Long.parseLong(((Map) this.cityList.get(0)).get("code").toString());
		}
		this.districtList = this.ordFlowManager.getDistrictList(city_id);

		// 获取物流公司
		logiCompanyList = ecsOrdManager.logi_company(order_id);

		// 货品类型处理
		// 先获取货品类型 货品小类根据商品类型查询 货品品牌也是根据商品类型查询 货品型号根据品牌ID查询
		// this.proTypeList =
		// CommonDataFactory.getInstance().getGoodsTypeList("product");//货品类型
		// product_type =
		// CommonDataFactory.getInstance().getAttrFieldValue(order_id,
		// AttrConsts.PRODUCT_TYPE);
		String pro_type = this.product_type;
		if (pro_type == null && proTypeList != null && proTypeList.size() > 0) {
			pro_type = ((GoodsType) proTypeList.get(0)).getType_id().toString();
		}
		if (!StringUtils.isEmpty(pro_type)) {
			this.proCatList = CommonDataFactory.getInstance().getCatsByTypeId(pro_type);
		}

		// 受理单信息
		List<OrderItemsAptPrintsBusiRequest> printList = orderTree.getOrderItemBusiRequests().get(0).getOrderItemsAptPrintsRequests();
		if (printList != null && printList.size() > 0) {
			this.acceptanceTp = printList.get(0).getReceipt_code();// 受理单模版编码
			this.accept_print_type = printList.get(0).getReceipt_mode();// 受理单打印模式
		}
		// 获取礼品列表
		this.giftInfoList = orderTree.getGiftInfoBusiRequests();
		this.giftInfoSize = giftInfoList.size();

		// 获取礼品品牌列表
		this.giftBrandList = CommonDataFactory.getInstance().getBrandByTypeId(SpecConsts.TYPE_ID_10007);

		String member_id = orderTree.getOrderBusiRequest().getMember_id();// select
																			// *
																			// from
																			// es_member
																			// where
																			// member_id
																			// =
																			// ''
		this.buyInfoMap = new HashMap();
		this.buyInfoMap = this.ordFlowManager.getBuyInfoByMemberId(member_id);
		// this.specValidateMsg = this.ordFlowManager.getSpecErrorMsg(order_id);

		// 副卡信息
		this.setOrderPhoneInfoFukaList(orderTree.getOrderPhoneInfoFukaBusiRequests());
		// 获取价格信息
		getPrice(orderTree);

		// 附加产品信息
		this.setSubProductList(CommonDataFactory.getInstance().getSubProdPackList(orderTree));

		// 客户历史订单
		this.orderhisPage = this.ecsOrdManager.getOrderHisPage(order_id, this.getPage(), this.getPageSize());
		this.orderchangeList = this.ecsOrdManager.getOrderChangeList(order_id);
		this.orderDtl = this.ecsOrdManager.getOrderDtl(order_id);
		
		//商品信息
		//商品信息
				this.goods_id=orderTree.getOrderBusiRequest().getGoods_id();
				this.order_id=orderTree.getOrderBusiRequest().getOrder_id();
				IDaoSupport baseDaoSupport=SpringContextHolder.getBean("baseDaoSupport");
				StringBuffer sql_goods_info=new StringBuffer();
				sql_goods_info.append("select eg.name goods_name, egt.name goods_type_name").
				append(" from es_goods eg left join es_goods_type egt on eg.type_id = egt.type_id").
				append(" where  eg.source_from='").append(ManagerUtils.getSourceFrom()).append("' ").append(" and egt.source_from ='").
				append(ManagerUtils.getSourceFrom()).append("' and ").append("eg.goods_id='").append(goods_id).append("'");
				StringBuffer sql_goods_elem_info=new StringBuffer();
		 		sql_goods_elem_info.append("select eoe.element_id,eoe.mobile_type,eoe.object_esn,ae.terminal_name ").
				append("from es_order_extvtl eoe left join ES_GOODS_ACTION_ELEMENT ae on ae.element_id=eoe.element_id "
						+ "and ae.mobile_type=eoe.mobile_type").append(" where eoe.order_id='").append(order_id).append("'")
						.append(" and ae.mobile_type=eoe.mobile_type and ae.goods_id='").append(goods_id).append("'")
						.append(" and ae.source_from=eoe.source_from");
				List<Map<String, String>> listInfo = baseDaoSupport.queryForList(sql_goods_info.toString());
				List<Map<String, String>> listelemInfo = baseDaoSupport.queryForList(sql_goods_elem_info.toString());
				 if(listInfo.size()>0){
					 this.goods_name=listInfo.get(0).get("goods_name");
					 this.goods_type_name=listInfo.get(0).get("goods_type_name");
			        }
				 if (listelemInfo.size()>0) {
					 this.element_id=listelemInfo.get(0).get("element_id");
					 this.mobile_type=listelemInfo.get(0).get("mobile_type");
					 this.object_esn=listelemInfo.get(0).get("object_esn");
					 this.terminal_name=listelemInfo.get(0).get("terminal_name");
				}
		 topSeedInfoMap = ecsOrdManager.gettopSeedInfo(order_id);
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String NEW = cacheUtil.getConfigInfo("IS_NEW_JSP");
		if(!StringUtil.isEmpty(NEW)){
			return NEW+"order_detail_view";
		}
		return "order_detail_view";
	}

	public String getOrderHis() {
		this.orderhisPage = this.ecsOrdManager.getOrderHisPage(order_id, this.getPage(), this.getPageSize());
		return "order_his";
	}

	/**
	 * 获取订单历史记录page和条数
	 * 
	 * @return
	 */

	public String loadOrderHisPage() {
		String order_id = this.order_id;
		this.orderhisPage = this.ecsOrdManager.getOrderHisPage(order_id, this.getPage(), this.getPageSize());
		this.setOrderhisPageRowCount(String.valueOf(this.orderhisPage.getTotalCount()));
		return includePage;
	}
	public String newLoadOrderHisPage() {
		if (!StringUtil.isEmpty(first_load) && StringUtil.equals(first_load, "yes")){
			this.orderhisPage = new Page();
		}else{
			String order_id = this.order_id;
			this.orderhisPage = this.ecsOrdManager.getOrderHisPage(order_id, this.getPage(), this.getPageSize());
			this.setOrderhisPageRowCount(String.valueOf(this.orderhisPage.getTotalCount()));
		}
		
		return includePage;
	}

	public String getOrderChangeLogs(){
		this.orderchangeList = this.ecsOrdManager.getOrderChangeList(order_id);
		return includePage;
	}
	/**
	 * 发货路由信息
	 * 
	 * @return
	 */
	public String showSignRoutes() {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String OrderFrom = orderTree.getOrderExtBusiRequest().getOrder_from();
		if (ZjEcsOrderConsts.ORDER_FROM_100032.equals(OrderFrom)) {
			OperationRecordReq req = new OperationRecordReq();
			req.setOrderId(order_id);

			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			OperationRecordResp resp = client.execute(req, OperationRecordResp.class);
			if ("0".equals(resp.getError_code())) {
				List<Route> list = resp.getOperationRecordList();
				if (list != null) {
					logi_no = resp.getLogi_no();
					signRoute = resp.getOperationRecordList();

				}
				sign_status = resp.getSign_status();
			} else {
				logger.info("=====OrderFlowAction showSignRoutes 总商查询发货路由信息失败【orderId：" + order_id + ",errorMsg:" + resp.getError_msg() + "】");
			}

		} else {
			List<OrderDeliveryBusiRequest> orderDeliveryBusiRequest = orderTree.getOrderDeliveryBusiRequests();
			OrderDeliveryBusiRequest deliverBusiReq = new OrderDeliveryBusiRequest();
			if (orderDeliveryBusiRequest != null && orderDeliveryBusiRequest.size() > 0) {// 取正常发货记录
				for (OrderDeliveryBusiRequest deli : orderDeliveryBusiRequest) {
					if (EcsOrderConsts.LOGIS_NORMAL.equals(deli.getDelivery_type())) {
						deliverBusiReq = deli;
						break;
					}
				}
			}

			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			RouteServiceRequest req = new RouteServiceRequest();
			req.setTracking_type(2);
			req.setTracking_number(order_id);
			RouteServiceResponse resp = client.execute(req, RouteServiceResponse.class);
			List<RouteResponse> list = resp.getRouteResponseList();
			if (list != null) {
				for (RouteResponse route : list) {
					if (route.getMailno().equals(deliverBusiReq.getLogi_no())) {// 运单号
						logi_no = route.getMailno();
						signRoute = route.getRouteList();
						for (Route route1 : signRoute) {
							if (route1.getOpcode().equals("80")) {
								deliverBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
								deliverBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
								deliverBusiReq.setSign_status(EcsOrderConsts.SIGN_STATUS_1);// 运单设为已签收
								deliverBusiReq.store(); // 属性数据入库
							}
						}
					}
				}
			}
			if (null != deliverBusiReq && EcsOrderConsts.SIGN_STATUS_1.equals(deliverBusiReq.getSign_status())) {
				sign_status = "已签收";
			} else {
				sign_status = "未签收";
			}
		}
		return "sign_route";
	}

	/**
	 * 回单路由信息
	 * 
	 * @return
	 */
	public String showReceiptRoutes() {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<OrderDeliveryBusiRequest> orderDeliveryBusiRequest = orderTree.getOrderDeliveryBusiRequests();
		OrderDeliveryBusiRequest deliverBusiReq = new OrderDeliveryBusiRequest();
		if (orderDeliveryBusiRequest != null && orderDeliveryBusiRequest.size() > 0) {// 取正常发货记录
			for (OrderDeliveryBusiRequest deli : orderDeliveryBusiRequest) {
				if (EcsOrderConsts.LOGIS_NORMAL.equals(deli.getDelivery_type())) {
					deliverBusiReq = deli;
					break;
				}
			}
		}

		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		RouteServiceRequest req = new RouteServiceRequest();
		req.setTracking_type(2);
		req.setTracking_number(order_id);
		RouteServiceResponse resp = client.execute(req, RouteServiceResponse.class);
		List<RouteResponse> list = resp.getRouteResponseList();
		if (list != null) {
			for (RouteResponse route : list) {
				if (route.getMailno().equals(deliverBusiReq.getReceipt_no())) {// 回单号
					receipt_no = route.getMailno();
					receiptRoute = route.getRouteList();
					for (Route route1 : receiptRoute) {
						if (route1.getOpcode().equals("80")) {
							deliverBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							deliverBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							deliverBusiReq.setReceipt_status(EcsOrderConsts.SIGN_STATUS_1);// 回单设为已签收
							deliverBusiReq.store(); // 属性数据入库
						}
					}
				}
			}
		}
		if (null != deliverBusiReq && EcsOrderConsts.SIGN_STATUS_1.equals(deliverBusiReq.getReceipt_status())) {
			receipt_status = "已签收";
		} else {
			receipt_status = "未签收";
		}

		return "receipt_route";
	}

	/**
	 * 订单详情页面包含子页面处理
	 * 
	 * @return
	 */
	public String includeViewPage() {
		order_detail_view();
		return includePage;
	}

	public String preDealSave() {// 预处理保存

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		// 收货信息|物流信息
		if (orderTree.getOrderDeliveryBusiRequests().size() > 0) {
			OrderDeliveryBusiRequest deliverBusiReq = null;
			List<OrderDeliveryBusiRequest> orderDeliveryBusiRequest = orderTree.getOrderDeliveryBusiRequests();
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
			deliverBusiReq.setProvince_id(this.provinc_code);
			deliverBusiReq.setCity_id(this.city_code);
			deliverBusiReq.setRegion_id(this.district_id);
			
			// 县分
			if (null != this.district_id) {
				deliverBusiReq.setRegion(CommonDataFactory.getInstance().getLocalName(this.district_id.toString()));
			}
			
			//省
			if(null!=this.provinc_code) {
			  deliverBusiReq.setProvince(CommonDataFactory.getInstance().getLocalName(this.provinc_code.toString()));
			}
			//市
			if(null!=this.city_code) {
				deliverBusiReq.setCity(CommonDataFactory.getInstance().getLocalName(this.city_code.toString()));
			}
			
			deliverBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			deliverBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			deliverBusiReq.store(); // 属性数据入库
		} else {
			OrderDeliveryBusiRequest deliverBusiReq = new OrderDeliveryBusiRequest();
			deliverBusiReq.setDelivery_type(EcsOrderConsts.LOGIS_NORMAL);// 创建一条正常发货的记录
			deliverBusiReq.setShip_status(OrderStatus.DELIVERY_SHIP_SATUS_NOT);// 未发货、未打印
			deliverBusiReq.setProvince_id(this.provinc_code);
			deliverBusiReq.setCity_id(this.city_code);
			deliverBusiReq.setRegion_id(this.district_id);
			//县分
			if (null != this.district_id) {
				deliverBusiReq.setRegion(CommonDataFactory.getInstance().getLocalName(this.district_id.toString()));
			}
			//省
			if(null!=this.provinc_code) {
			  deliverBusiReq.setProvince(CommonDataFactory.getInstance().getLocalName(this.provinc_code.toString()));
			}
			//市
			if(null!=this.city_code) {
				deliverBusiReq.setCity(CommonDataFactory.getInstance().getLocalName(this.city_code.toString()));
			}
			
			deliverBusiReq.setOrder_id(order_id);
			deliverBusiReq.setDb_action(ConstsCore.DB_ACTION_INSERT);
			deliverBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			deliverBusiReq.store();
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			orderTree.getOrderDeliveryBusiRequests().add(deliverBusiReq);
			try {
				logsServices.cacheOrderTree(orderTree);
			} catch (ApiBusiException e) {
				e.printStackTrace();
			}
		}

		// 保存订单总价

		orderTree.getOrderBusiRequest().setOrder_amount(this.order_amount);
		orderTree.getOrderBusiRequest().setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderTree.getOrderBusiRequest().setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderTree.getOrderBusiRequest().store();
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "order_amount", "shipping_company" }, new String[] { order_amount + "", this.shipping_company });

		// 保存异常类型

		// if(EcsOrderConsts.)
		String quick_ship_company_code = "";
		// 顺丰
		if (EcsOrderConsts.LOGI_COMPANY_SF0001.equals(shipping_company) || EcsOrderConsts.LOGI_COMPANY_SF0002.equals(shipping_company) || EcsOrderConsts.LOGI_COMPANY_SF0003.equals(shipping_company)) {
			quick_ship_company_code = EcsOrderConsts.SDS_COMP_SF;
		}
		// 南都
		if (EcsOrderConsts.LOGI_COMPANY_ND0001.equals(shipping_company) || EcsOrderConsts.LOGI_COMPANY_ND0002.equals(shipping_company)) {
			quick_ship_company_code = EcsOrderConsts.SDS_COMP_ND;
		}
		orderTree.getOrderExtBusiRequest().setQuick_ship_company_code(quick_ship_company_code);
		orderTree.getOrderExtBusiRequest().setAbnormal_type(abnormal_type);
		orderTree.getOrderExtBusiRequest().setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderTree.getOrderExtBusiRequest().setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderTree.getOrderExtBusiRequest().store();

		String member_id = orderTree.getOrderBusiRequest().getMember_id();
		if (member_id != null && !"".equals(member_id)) {
			Map map = new HashMap();
			map.put("address", address);
			map.put("tel", tel);
			map.put("mobile", mobile);
			map.put("member_id", member_id);
			this.ordFlowManager.updateBuyInfoByMemberId(new String[] { mobile, tel, address, member_id });
		}
		return "";
	}

	/* 预拣货 */
	public String prePick() {
		// return "prePick_bak";
		this.isTerm = CommonDataFactory.getInstance().hasTerminal(order_id);
		return "prePick";
	}

	/* 业务办理页面 */
	public String business() {
		// 获取礼品列表
		// CommonDataFactory.getInstance().updateOrderTree(order_id);
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		// XMJ 业务办理 环节添加订单金额,business.jsp添加 <span>${order_amount}</span>
		order_amount = orderTree.getOrderBusiRequest().getOrder_amount();
		this.giftInfoList = orderTree.getGiftInfoBusiRequests();
		this.setOrderPhoneInfoFukaList(orderTree.getOrderPhoneInfoFukaBusiRequests());
		this.orderTree = orderTree;

		// 获取价格信息
		getPrice(orderTree);

		// 附加产品信息
		this.setSubProductList(CommonDataFactory.getInstance().getSubProdPackList(orderTree));
		return "business";
	}

	/* 写卡 */
	public String writeCard() {
		// return "writeCardTest";
		return "writeCard";
	}

	public String readCard() {
		try {
			if (StringUtils.isEmpty(this.ic_Card)) {
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			}
			this.json = "{result:0,message:'操作成功'}";
		} catch (Exception e) {
			e.printStackTrace();
			String msg = e.getMessage();
			this.json = "{result:0,message:'" + msg + "'}";
		}
		return this.JSON_MESSAGE;
	}

	/* 写卡 */
	public String fkWriteCard() {
		// return "writeCardTest";
		orderPhoneInfoFukaList = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderPhoneInfoFukaBusiRequests();
		return "fkWriteCard";
	}

	private String logi_receiver;
	private String logi_receiver_phone;
	private String delivery_id;

	public void shipping() {
		/*
		 * OrderTreeBusiRequest orderTree =
		 * CommonDataFactory.getInstance().getOrderTree(order_id);
		 * List<OrderDeliveryBusiRequest> list =
		 * orderTree.getOrderDeliveryBusiRequests(); if(list!=null &&
		 * list.size()>0){ for(OrderDeliveryBusiRequest o:list){
		 * if(o.getDelivery_id().equals(delivery_id)){
		 * o.setLogi_receiver(logi_receiver);
		 * o.setLogi_receiver_phone(logi_receiver_phone);
		 * //o.setShip_status(OrderStatus.DELIVERY_SHIP_SATUS_1);
		 * o.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		 * o.setDb_action(ConstsCore.DB_ACTION_UPDATE); o.store(); break ; } } }
		 */
	}

	public void orderArchive() {
		// OrderTreeBusiRequest orderTree =
		// CommonDataFactory.getInstance().getOrderTree(order_id);
		logger.info("归档完成===================================");
	}

	public void dealWorkFlowNext(String order_id) {
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<Map> workNo = dcPublicCache.getList(EcsOrderConsts.work_flow_staff_no);
		Map workNoMap = workNo.get(0);
		int staffId = Integer.parseInt(workNoMap.get("pkey").toString());
		String staffName = (String) workNoMap.get("pname");
		String processInstanceId = orderTree.getOrderExtBusiRequest().getFlow_inst_id();
		String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		String flowStatus = Const.FLOW_DEAL_TYPE_AM;
		UosDealReq ureq = new UosDealReq();
		ureq.setStaffId(staffId);
		ureq.setStaffName("");
		ureq.setProcessInstanceId(processInstanceId);
		ureq.setTacheCode(trace_id);
		ureq.setDealType(flowStatus);// 环节处理状态
		UosDealResp flowresp = uosService.dealProcess(ureq);// 工作流返回下一个环节的信息
		ArrayList<WorkItem> workItems = flowresp.getWorkItems();
		// 修改环节为下一个环节
		// 修改订单处理状态为未处理
		if (workItems != null && workItems.size() > 0) {
			// 目前只有一个不节，所以只拿第一个就行了
			WorkItem wi = workItems.get(0);
			String next_trace_id = wi.getTacheCode();
			OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
			orderExtBusiRequest.setFlow_status(Const.ORDER_FLOW_STATUS_0);
			orderExtBusiRequest.setFlow_trace_id(next_trace_id);
			orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			// 修改订单流程状态
			orderExtBusiRequest.store();
		}
	}

	/**
	 * 转手工开户按钮
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-11-13
	 * @return
	 * @throws Exception
	 */
	public String offLineOpenAccount() throws Exception {
		String currFlowTraceId = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
		String lockMsg = checkLockUser();
		if (!StringUtils.isEmpty(lockMsg)) {
			this.json = "{result:1,message:'" + lockMsg + "'}";
			return this.JSON_MESSAGE;
		}
		String msg = editSave();
		if (msg != null && msg.length() > 0) {
			String validateMsg = JSONArray.toJSONString(validateMsgList);
			this.json = "{result:1,message:'" + msg + "',validateMsgList:" + validateMsg + "}";
		} else {
			// 调用订单转手工开户方案
			TacheFact fact = new TacheFact();
			fact.setOrder_id(order_id);
			PlanRuleTreeExeResp presp = RuleFlowUtil.executePlanRuleTree(EcsOrderConsts.CHANGE_OFFLINE_OPENACCOUNT_PLAN, EcsOrderConsts.RULE_EXE_ALL, fact, EcsOrderConsts.DEAL_FROM_PAGE, dealType,
					"[转手工开户]" + getDealDesc());
			String errorMsg = RuleFlowUtil.getAllRuleErrorMsg(presp.getFact());
			if (!StringUtil.isEmpty(errorMsg)) {
				this.json = "{result:1,message:'" + errorMsg + "'}";
				return this.JSON_MESSAGE;
			}
			insertDealInfo(currFlowTraceId, "[转手工开户]");
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			String action_url = this.getActionUrl(orderTree.getOrderExtBusiRequest().getFlow_trace_id(), order_id);
			this.json = "{result:0,message:'操作成功',action_url:'" + action_url + "'}";

			Utils.commonUnlock(order_id, currFlowTraceId, trace_id); // 解锁公共方法
		}
		return this.JSON_MESSAGE;
	}

	public String openAccount() {
		// 获取礼品列表
		OrderTreeBusiRequest orderTree = new OrderTreeBusiRequest();
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		this.giftInfoList = orderTree.getGiftInfoBusiRequests();
		this.orderTree = orderTree;

		this.setOrderPhoneInfoFukaList(orderTree.getOrderPhoneInfoFukaBusiRequests());

		// 获取价格信息
		getPrice(orderTree);

		// 附加产品信息
		this.setSubProductList(CommonDataFactory.getInstance().getSubProdPackList(orderTree));

		// 收货信息|物流信息
		if (orderTree.getOrderDeliveryBusiRequests().size() > 0) {
			OrderDeliveryBusiRequest deliverBusiReq = orderTree.getOrderDeliveryBusiRequests().get(0);
			this.provinc_code = deliverBusiReq.getProvince_id();
			this.city_code = deliverBusiReq.getCity_id();
			this.district_id = deliverBusiReq.getRegion_id();
		}
		this.provList = this.ordFlowManager.getProvList();
		this.shipping_company = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIPPING_COMPANY);
		Long prov_code = this.provinc_code;
		if (prov_code == null || "".equals(prov_code)) {
			prov_code = Long.parseLong(((Map) provList.get(0)).get("code").toString());
		}
		this.cityList = this.ordFlowManager.getCityList(prov_code);
		Long city_id = this.city_code;
		if (city_id == null || "".equals(city_id)) {
			city_id = Long.parseLong(((Map) this.cityList.get(0)).get("code").toString());
		}
		//20180718 sgf 新增发展点 发展人 节点转换
		String out_tid = orderTree.getOrderExtBusiRequest().getOut_tid();
        List<Map<String, String>> esOrderQueue = this.ordFlowManager.getServiceCodeByObjectId(out_tid);
        Map<String, String>  developParams  = this.orderExtManager.changeDevelopmetInfo(order_id,orderTree,esOrderQueue);
        this.development_point_code = developParams.get("development_point_code");
        this.development_point_name = developParams.get("development_point_name");
        this.developmentCode = developParams.get("developmentCode");
        this.developmentName = developParams.get("developmentName");
        //sgf 20190813
	    ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
	    this.cbss_development_point_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "cbss_development_point_code");
	    this.cbss_develop_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "cbss_develop_code");
        
		this.districtList = this.ordFlowManager.getDistrictList(city_id);

		goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		if (EcsOrderConsts.GOODS_TYPE_DKD.equals(goods_type)) {
			// 宽带信息
			p_code = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10054, SpecConsts.P_CODE);// 合约编码
			p_name = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10054, SpecConsts.P_NAME);// 合约名称
			city = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10054, SpecConsts.CITY);// 地市
			packge_limit = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10054, SpecConsts.PACKAGE_LIMIT);// 合约期
			net_speed = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10053, SpecConsts.NET_SPEED);// 速率
			access_type = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10054, SpecConsts.ACCESS_TYPE);// 接入方式
			plan_title = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10053, SpecConsts.BSS_NAME);// 套餐名称
			ess_code = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10053, SpecConsts.BSS_CODE);// 套餐编码
			terminal_type = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10055, SpecConsts.TERMINAL_TYPE);// 终端类型
		}
		//ICCID  成卡卡号
		//old_terminal_num  终端型号
		//terminal_num 终端串号   add by sgf
		this.ICCID_INFO = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
	    this.old_terminal_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "object_id");//物品id
	    this.terminal_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "object_esn");//
        String cur_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
	    String order_froms = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOrder_from();
	    String goods_cat = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);
        String close_group_order_catid = cacheUtil.getConfigInfo("page_look_info");
	    if("MK".equals(cur_trace_id)&&"10093".equals(order_froms) && StringUtils.isNotEmpty(close_group_order_catid) && close_group_order_catid.contains(goods_cat)){
	        Termain_list = this.ordFlowManager.getTearminalObjectQuery(order_id);
	        if (Termain_list == null) {
	            Termain_list = new ArrayList<Map>();
	        }
	    }
		// 获取物流公司
		logiCompanyList = ecsOrdManager.logi_company(order_id);
		String zbOpenType = orderTree.getOrderExtBusiRequest().getZb_open_type();
		String order_model = orderTree.getOrderExtBusiRequest().getOrder_model();

		
		//add by mahui
		
		this.element_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "element_id");
		this.mobile_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "mobile_type");
		this.goods_id = orderTree.getOrderBusiRequest().getGoods_id();
		if(!StringUtils.isEmpty(this.element_id)) {
			IDaoSupport baseDaoSupport=SpringContextHolder.getBean("baseDaoSupport");
			String sql = "SELECT scheme_id,terminal_name FROM ES_GOODS_ACTION_ELEMENT WHERE ELEMENT_ID = '"+element_id+"' and goods_id = '"+this.goods_id+"'";
			/*this.scheme_id = baseDaoSupport.queryForString(sql);*/
			List list = baseDaoSupport.queryForList(sql, null);
			if(list.size()>0){
				Map map = (Map) list.get(0);
				this.scheme_id = Const.getStrValue(map, "scheme_id");
				this.terminal_name = Const.getStrValue(map, "terminal_name");
			}else{
				this.scheme_id = "";
				this.terminal_name = "";
			}
		}
		if ((order_model.equals(ZjEcsOrderConsts.ORDER_MODEL_07) || order_model.equals(ZjEcsOrderConsts.ORDER_MODEL_08)) && !"0".equals(zbOpenType)) {// 爬虫总商手动开户
			// return "zbManualOpenAccount";
			return "openAccountOffline";
		} else if (StringUtils.equals(openAccountType, "online")) {
			return "openAccountOnline";
		} else if("MK".equals(cur_trace_id)) {
		    return "inventoryKeeper";
		}else{
			return "openAccountOffline";
		}
	}
    // 统一环节保存方法
	public String flowSave() {
		String lockMsg = checkLockUser();
		if (!StringUtils.isEmpty(lockMsg)) {
			this.json = "{result:1,message:'" + lockMsg + "'}";
			return this.JSON_MESSAGE;
		}
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		this.insertDealInfo(trace_id, "");
		List<OrderAdslBusiRequest> adslreqList = orderTree.getOrderAdslBusiRequest();
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
	    String goods_cat = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String save_group_cat_id = cacheUtil.getConfigInfo("save_group_cat_id");
		if("D".equals(trace_id) && "10093".equals(order_from) && StringUtils.isNotEmpty(save_group_cat_id) && save_group_cat_id.contains(goods_cat)){//来源和商品小类
		    String check_card_number = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "check_card_number");//卡号校验标志位
		    String check_terminal_series = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "check_terminal_series");//终端串号校验标志位
		    if(StringUtils.isEmpty(check_card_number) || "0".equals(check_card_number)){
		        this.json = "{result:1,message:'请校验成卡卡号'}";
                return this.JSON_MESSAGE;
		    }
		    if(StringUtils.isEmpty(check_terminal_series) || "0".equals(check_terminal_series)){
                this.json = "{result:1,message:'请校验终端串号'}";
                return this.JSON_MESSAGE;
            }		    
		}
		
		// 沉淀物流公司编码 
		if ("H".equals(trace_id) && shipping_company != null) {
			String[] str = new String[1];
			str[0] = shipping_company;
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "shipping_company" }, str);
		}
		
		String order_service_type = CommonDataFactory.getInstance().getGoodSpec(order_id, null, AttrConsts.ORDER_SERVICE_TYPE);
		OrderTreeBusiRequest otreq = CommonDataFactory.getInstance().getOrderTree(order_id);
		  try {
              Boolean flag = ordWorkManager.isKDYQ(order_id, otreq);
              if(flag){
                  Map attrMaps = getPageOrderAttrMap(order_id);
                  String adslId = ecsOrdManager.getAttrIdByName("adsl_addr");
                  String adsl_addr = (String) attrMaps.get("oattrinstspec_" + order_id + "_" + adslId + "_" + adslId + "#adsl_addr");
                  if (StringUtils.isEmpty(adsl_addr) || StringUtils.equals(adsl_addr, "-1")) {
                     
                      this.json = "{result:1,message:'请选择标准地址'}";
                      return this.JSON_MESSAGE;
                  }
              }
           } catch (ApiBusiException e1) {
               e1.printStackTrace();
           }
		if (null != adslreqList && adslreqList.size() > 0 && (StringUtils.equals(order_from, "10078")/*||StringUtils.equals(order_from, "10091")*/)) {
			Map attrMap = getPageOrderAttrMap(order_id);
			String adslId = ecsOrdManager.getAttrIdByName("adsl_addr");
			String countyId = ecsOrdManager.getAttrIdByName("county_id");
			String modermId = ecsOrdManager.getAttrIdByName("moderm_id");

			String adsl_addr = (String) attrMap.get("oattrinstspec_" + order_id + "_" + adslId + "_" + adslId + "#adsl_addr");
			String county_id = (String) attrMap.get("oattrinstspec_" + order_id + "_" + countyId + "_" + countyId + "#county_id");
			String moderm_id = (String) attrMap.get("oattrinstspec_" + order_id + "_" + modermId + "_" + modermId + "#moderm_id");

			String catId = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);
			String gyrh_cat_id = cacheUtil.getConfigInfo("gyrh_cat_id");// 固移融合单商品小类
			if (!gyrh_cat_id.contains(catId)) {// 派工单需要营业县分
				if (StringUtils.isEmpty(adsl_addr) || StringUtils.equals(adsl_addr, "-1")) {
					this.json = "{result:1,message:'请选择标准地址'}";
					return this.JSON_MESSAGE;
				}
				// FTTH宽带才校验光猫ID
				if (StringUtils.isEmpty(moderm_id) && StringUtils.equals(order_service_type, "6115")) {
					this.json = "{result:1,message:'请选择光猫ID'}";
					return this.JSON_MESSAGE;
				}
			}
			if (StringUtils.isEmpty(county_id)) {
				this.json = "{result:1,message:'请选择营业县分'}";
				return this.JSON_MESSAGE;
			}
		}
		String set_sql = "";
		String table_name = "es_order_zhwq_adsl";
		if(!StringUtils.isEmpty(sale_mode)){
			set_sql += " sale_mode = '"+sale_mode+"', ";
		}
		if(!StringUtils.isEmpty(devic_gear)){
			set_sql += " devic_gear = '"+devic_gear+"', ";
		}
		if(!StringUtils.isEmpty(is_done_active)){
			set_sql += " is_done_active = '"+is_done_active+"', ";
		}
		if(!StringUtil.isEmpty(access_type)){
			set_sql += " access_type = '"+access_type+"', ";
		}
		if(!StringUtils.isEmpty(set_sql)){
			set_sql = set_sql.substring(0, set_sql.lastIndexOf(","));
			List<OrderAdslBusiRequest> orderAdslBusiRequests = orderTree.getOrderAdslBusiRequest();
			for (OrderAdslBusiRequest orderAdslBusiRequest : orderAdslBusiRequests) {
				if(!StringUtils.isEmpty(sale_mode)){
					orderAdslBusiRequest.setSale_mode(sale_mode);
				}
				if(!StringUtils.isEmpty(devic_gear)){
					orderAdslBusiRequest.setDevic_gear(devic_gear);
				}
				if(!StringUtils.isEmpty(is_done_active)){
					orderAdslBusiRequest.setIs_done_active(is_done_active);
				}
				if(!StringUtils.isEmpty(access_type)){
					orderAdslBusiRequest.setAccess_type(access_type);
				}
			}
			String sql ="update "+table_name+" set "+set_sql+" where order_id=?";
			IDaoSupport baseDaoSupport=SpringContextHolder.getBean("baseDaoSupport");
			baseDaoSupport.execute(sql, order_id);
			//更新订单树缓存
			cache.set(EcsOrderConsts.ORDER_TREE_NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY+"order_id"+ order_id,orderTree, 60*24*60*5);

		}
		
		String msg = "";
		// WMS质检稽核，不做环节界面验证
		msg = editSave();
		CommonDataFactory.getInstance().updateOrderTree(order_id);
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<OrderAdslBusiRequest> orderAdslBusiRequests = orderTree.getOrderAdslBusiRequest();
		if (msg != null && msg.length() > 0) {// WMS质检稽核，不做环节界面验证
			String validateMsg = JSONArray.toJSONString(validateMsgList);
			this.json = "{result:1,message:'" + msg + "',validateMsgList:" + validateMsg + ",order_id:'" + order_id + "'}";
			return this.JSON_MESSAGE;
		} else {
			String action_url = "";
			if (EcsOrdManager.QUERY_TYPE_YCL.equals(this.query_type)) {
				action_url = EcsOrderConsts.REPLENISH_URL + "?order_id=" + order_id + "&d_type=" + d_type + "&query_type=" + query_type;
			} else {
				orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
				DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
				List<Map> n_list = dcPublicCache.getList(EcsOrderConsts.DIC_ORDER_NODE);
				action_url = getActionUrl(trace_id, orderTree.getOrder_id());
			}
			this.json = "{result:0,message:'操作成功',action_url:'" + action_url + "'}";
		}
		return this.JSON_MESSAGE;
	}

	private List<Map> getConsts(String key) {
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<Map> list = dcPublicCache.getList(key);
		return list;
	}

	public String openAccountNext() {
		String orderid = this.order_id;
		String guhuaNumber = this.guhua_number;
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		//固话号码插入es_order_extvtl表
		if(!StringUtils.isEmpty(guhuaNumber) && !StringUtils.isEmpty(orderid)) {
			String sql = "update es_order_extvtl set guhua_num  = '" +guhuaNumber +"' where order_id = '"+orderid + "' and source_from = 'ECS'";
			baseDaoSupport.execute(sql);
		}
		//添加开户人员工号和开户时间到es_order_extvtl表中,便于订单报表导出
		AdminUser user = ManagerUtils.getAdminUser();
		String user_id = user.getUsername();
		Calendar date = new GregorianCalendar();
		DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String open_account_time=DF.format(date.getTime());
		if(!StringUtil.isEmpty(user_id)) {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "open_account_userid" }, new String[] { user_id });
		}
		String update_county_extvtl_sql = "update es_order_extvtl set open_account_time = to_date('"+open_account_time+"','yyyy-MM-dd HH24:mi:ss') where order_id='" + order_id + "'";
	
		baseDaoSupport.execute(update_county_extvtl_sql);
				
		String lockMsg = checkLockUser();// checkLockUser();
		if (!StringUtils.isEmpty(lockMsg)) {
			this.json = "{result:1,message:'" + lockMsg + "'}";
			return this.JSON_MESSAGE;
		}
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
        String goods_cat = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);
        String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();

        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String save_group_cat_id = cacheUtil.getConfigInfo("save_group_cat_id");
        if("D".equals(trace_id)&&"10093".equals(order_from) && StringUtils.isNotEmpty(save_group_cat_id) && save_group_cat_id.contains(goods_cat)){//来源和商品小类
            String check_card_number = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "check_card_number");//卡号校验标志位
            String check_terminal_series = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "check_terminal_series");//终端串号校验标志位
            if(StringUtils.isEmpty(check_card_number) || "0".equals(check_card_number)){
                this.json = "{result:1,message:'请回退到库管环节，校验成卡卡号'}";
                return this.JSON_MESSAGE;
            }
            if(StringUtils.isEmpty(check_terminal_series) || "0".equals(check_terminal_series)){
                this.json = "{result:1,message:'请回退到库管环节，校验终端串号'}";
                return this.JSON_MESSAGE;
            }           
        }
		String msg = editSave();
		if (msg != null && msg.length() > 0) {
			String validateMsg = JSONArray.toJSONString(validateMsgList);
			this.json = "{result:1,message:'" + msg + "',validateMsgList:" + validateMsg + "}";
			// this.json = "{result:1,message:'"+msg+"'}";
			return this.JSON_MESSAGE;
		}
		// 获取当前环节编码
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String zbOpenType = orderTree.getOrderExtBusiRequest().getZb_open_type();
		String orderModel = orderTree.getOrderExtBusiRequest().getOrder_model();
		if (ZjEcsOrderConsts.ORDER_MODEL_07.equals(orderModel) || ZjEcsOrderConsts.ORDER_MODEL_08.equals(orderModel)) {
			// 总商爬虫手动或者自动开户未进入开户详情时不能点击开户完成按钮
			if (StringUtils.isEmpty(zbOpenType)) {
				this.json = "{result:1,message:'总商未进入开户环节或者开户环节尚不能操作，请过一段时间再操作'}";
				return JSON_MESSAGE;
			} else {
				String activeNo = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACTIVE_NO);
				String iccid = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
				if (StringUtil.isEmpty(activeNo) || StringUtil.isEmpty(iccid)) {
					this.json = "{result:1,message:'总商手动开户订单开户流水号和SIM卡号不能为空'}";
					return JSON_MESSAGE;
				}
			}

		}
		
		String cur_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		
		try{
			if("1".equals(this.orderTree.getOrderExtBusiRequest().getIs_work_custom())){
				//自定义流程
				this.workCutomOrderContinue(EcsOrderConsts.DIC_ORDER_NODE_D);
				
				//下一环节页面
				String action_url = this.getWorkCustomUrl();
				
				this.json = "{result:0,message:'操作成功',action_url:'" + action_url + "'}";
			}else{
				plan_id = EcsOrderConsts.OPEN_ACCOUNT_PLAN_ID;
				// 执行开户方案
				exeOpenAccountPlan();
			}
		}catch(Exception e){
			this.json = "{result:1,message:'"+e.getMessage()+"'}";
		}finally{
			// 获取下一环节环节
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		    trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
						
			Utils.commonUnlock(order_id, cur_trace_id, trace_id); // 解锁公共方法
		}

		if (!StringUtil.isEmpty(json))
			json = json.replace("status:", "result:").replace(",msg:", ",message:");
		
		return JSON_MESSAGE;
	}

	private String exeRelyOnRule = "";

	public String getExeRelyOnRule() {
		return exeRelyOnRule;
	}

	public void setExeRelyOnRule(String exeRelyOnRule) {
		this.exeRelyOnRule = exeRelyOnRule;
	}

	public String exeOpenAccountPlan() {
		try {
			BusiCompResponse response = null;
			try {
				PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
				TacheFact fact = new TacheFact();
				fact.setOrder_id(this.order_id);
				req.setPlan_id(EcsOrderConsts.OPEN_ACCOUNT_PLAN_ID);
				req.setFact(fact);
				req.setDeleteLogs(false);// 不删除日志，不允许二次操作
				req.setAuto_exe(-1);
				PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
				response = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
			} catch (Exception ee) {
				throw ee;
			} finally {
				orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
				String orderModel = orderTree.getOrderExtBusiRequest().getOrder_model();
				// 如果当前环节是开户环节，下一环节是写卡环节侧跳过写卡环节
				if (EcsOrderConsts.OPER_MODE_RGSG.equals(orderModel) && EcsOrderConsts.DIC_ORDER_NODE_X.equals(trace_id)) {
					TacheFact fact = new TacheFact();
					fact.setOrder_id(order_id);
					// 跳过写卡环节
					String next_rule_id = "";
					String mode = orderTree.getOrderExtBusiRequest().getOrder_model();
					String is_aop = orderTree.getOrderExtBusiRequest().getIs_aop();
					if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
						next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_01;
						if (EcsOrderConsts.ORDER_MODEL_02.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_02;
						} else if (EcsOrderConsts.ORDER_MODEL_03.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_03;
						} else if (EcsOrderConsts.ORDER_MODEL_04.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_04;
						} else if (EcsOrderConsts.ORDER_MODEL_05.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_05;
						}
					} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
						next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_01_BSSKL;
						if (EcsOrderConsts.ORDER_MODEL_02.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_02_BSSKL;
						} else if (EcsOrderConsts.ORDER_MODEL_03.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_03_BSSKL;
						} else if (EcsOrderConsts.ORDER_MODEL_04.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_04_BSSKL;
						} else if (EcsOrderConsts.ORDER_MODEL_05.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_05_BSSKL;
						}
					} else {
						next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_01_AOP;
						if (EcsOrderConsts.ORDER_MODEL_02.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_02_AOP;
						} else if (EcsOrderConsts.ORDER_MODEL_03.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_03_AOP;
						} else if (EcsOrderConsts.ORDER_MODEL_04.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_04_AOP;
						} else if (EcsOrderConsts.ORDER_MODEL_05.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_05_AOP;
						}
					}
					RuleTreeExeResp rresp = RuleFlowUtil.executeRuleTree(EcsOrderConsts.RULE_PLAN_ID_103, next_rule_id, fact, false, false, EcsOrderConsts.DEAL_FROM_PAGE);
				}
			}
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			String trace_id_u = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			String action_url = getActionUrl(trace_id_u, orderTree.getOrder_id());
			if (ConstsCore.ERROR_SUCC.equals(response.getError_code())) {
			    String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
		        String goods_cat = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);
		        String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		        String save_group_cat_id = cacheUtil.getConfigInfo("save_group_cat_id");
		        String save_terminal_cat_id = cacheUtil.getConfigInfo("save_terminal_cat_id");
		        if(("H".equals(trace_id) || "X".equals(trace_id)) &&"10093".equals(order_from) && StringUtils.isNotEmpty(save_group_cat_id) && save_group_cat_id.contains(goods_cat)){//来源和商品小类
		              this.json = "{status:9,msg:'操作成功',action_url:'" + action_url + "'}";
		        }else if (("H".equals(trace_id) || "X".equals(trace_id)) &&"10093".equals(order_from) && StringUtils.isNotEmpty(save_terminal_cat_id) && save_terminal_cat_id.contains(goods_cat)) {
		        	  this.json = "{status:9,msg:'操作成功',action_url:'" + action_url + "'}";
				}else{
		              this.json = "{status:" + response.getError_code() + ",msg:'操作成功',action_url:'" + action_url + "'}";
		        }
			} else {
				this.json = "{status:" + response.getError_code() + ",msg:'操作失败【" + response.getError_msg() + "】',action_url:'" + action_url + "'}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.json = "{status:1,msg:'执行失败'}";
		}
		return JSON_MESSAGE;
	}

	/**
	 * 执行规则
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-10-7
	 * @return
	 */
	public String executeRule() {
		BusiCompRequest busi = new BusiCompRequest();
		Map queryParams = new HashMap();
		queryParams.put("order_id", order_id);
		queryParams.put("plan_id", plan_id);
		queryParams.put("rule_id", rule_id);
		busi.setEnginePath("enterTraceRule.exec");
		queryParams.put("deal_from", EcsOrderConsts.DEAL_FROM_PAGE);
		busi.setQueryParams(queryParams);
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String flow_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		List<Map> nodeList = this.getConsts(OrderAutoAction.FLOW_TRACE_CACHE_KEY);
		String taceName = "";
		for (Map m : nodeList) {
			String pkey = (String) m.get("pkey");
			if (pkey.equals(flow_trace_id)) {
				taceName = (String) m.get("pname");
			}
		}
		try {
			BusiCompResponse resp = null;
			try {
				if (!StringUtil.isEmpty(exeRelyOnRule) && "0".equals(exeRelyOnRule)) {
					RuleTreeExeReq req = new RuleTreeExeReq();
					ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
					req.setRule_id(rule_id);
					req.setExePeerAfRules(false);
					req.setExeParentsPeerAfRules(false);
					TacheFact fact = new TacheFact();
					fact.setOrder_id(order_id);
					req.setFact(fact);
					RuleTreeExeResp busiResp = client.execute(req, RuleTreeExeResp.class);
					resp = new BusiCompResponse();
					resp.setError_code(busiResp.getError_code());
					resp.setError_msg(busiResp.getError_msg());
				} else {
					resp = orderServices.execBusiComp(busi);
				}
			} catch (Exception ee) {
				throw ee;
			} finally {
				orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
				String orderModel = orderTree.getOrderExtBusiRequest().getOrder_model();
				// 如果当前环节是开户环节，下一环节是写卡环节侧跳过写卡环节
				if (EcsOrderConsts.OPER_MODE_RGSG.equals(orderModel) && EcsOrderConsts.DIC_ORDER_NODE_X.equals(trace_id)) {
					TacheFact fact = new TacheFact();
					fact.setOrder_id(order_id);
					// 跳过写卡环节
					String next_rule_id = "";
					String mode = orderTree.getOrderExtBusiRequest().getOrder_model();
					String is_aop = orderTree.getOrderExtBusiRequest().getIs_aop();
					if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
						next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_01;
						if (EcsOrderConsts.ORDER_MODEL_02.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_02;
						} else if (EcsOrderConsts.ORDER_MODEL_03.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_03;
						} else if (EcsOrderConsts.ORDER_MODEL_04.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_04;
						} else if (EcsOrderConsts.ORDER_MODEL_05.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_05;
						}
					} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
						next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_01_BSSKL;
						if (EcsOrderConsts.ORDER_MODEL_02.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_02_BSSKL;
						} else if (EcsOrderConsts.ORDER_MODEL_03.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_03_BSSKL;
						} else if (EcsOrderConsts.ORDER_MODEL_04.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_04_BSSKL;
						} else if (EcsOrderConsts.ORDER_MODEL_05.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_05_BSSKL;
						}
					} else {
						next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_01_AOP;
						if (EcsOrderConsts.ORDER_MODEL_02.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_02_AOP;
						} else if (EcsOrderConsts.ORDER_MODEL_03.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_03_AOP;
						} else if (EcsOrderConsts.ORDER_MODEL_04.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_04_AOP;
						} else if (EcsOrderConsts.ORDER_MODEL_05.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_05_AOP;
						}
					}
					RuleTreeExeResp rresp = RuleFlowUtil.executeRuleTree(EcsOrderConsts.RULE_PLAN_ID_103, next_rule_id, fact, false, false, EcsOrderConsts.DEAL_FROM_PAGE);
				}
			}

			// 写日志
			// if(!StringUtil.isEmpty(rule_id))insertDealInfo(flow_trace_id,"");
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			String trace_id_u = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			String action_url = getActionUrl(trace_id_u, orderTree.getOrder_id());
			this.json = "{status:" + resp.getError_code() + ",msg:'" + resp.getError_msg() + "',action_url:'" + action_url + "'}";
		} catch (Exception e) {
			e.printStackTrace();
			this.json = "{status:1,msg:'执行失败'}";
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
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		List<OrderLockBusiRequest> orderLockRequest = orderTree.getOrderLockBusiRequests();// 某些单子会出现锁定状态为null的情况
		String currTacheCode = orderExtBusiRequest.getFlow_trace_id();
		OrderLockBusiRequest orderLockBusiRequest = null;

		if (orderLockRequest == null) {
			return msg = "订单锁定状态请求为null，异常";
		} else {
			for (int i = 0; i < orderLockRequest.size(); i++) {
				if (!"bss_parallel".equals(query_type)) {
					if (orderExtBusiRequest.getFlow_trace_id().equals(orderLockRequest.get(i).getTache_code())) {
						orderLockBusiRequest = orderLockRequest.get(i);
					}
				} else {
					// 并行业务办理环节权限处理
					if ("Y2".equals(orderLockRequest.get(i).getTache_code())) {
						orderLockBusiRequest = orderLockRequest.get(i);
						currTacheCode = "Y2";
					}
				}
			}
		}
		if (orderLockBusiRequest == null || EcsOrderConsts.UNLOCK_STATUS.equals(orderLockBusiRequest.getLock_status())) {
			msg = "请先锁定订单";
		} else if (!user.getUserid().equals(orderLockBusiRequest.getLock_user_id())) {
			msg = "订单已经被其他用户锁定,不能进行操作";
		} else if (ManagerUtils.getAdminUser().getFounder() != 1
				&& (StringUtils.isEmpty(ManagerUtils.getAdminUser().getTacheAuth()) || !ManagerUtils.getAdminUser().getTacheAuth().contains(currTacheCode))) {
			msg = "您没有订单当前环节的操作权限";
		}
		return msg;
	}

	/**
	 * 处理业务
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-11-12
	 */
	private void flowDealBusiness() {
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		if (EcsOrderConsts.DIC_ORDER_NODE_H.equals(trace_id)) {
			List<OrderDeliveryBusiRequest> list = orderTree.getOrderDeliveryBusiRequests();
			if (list != null && list.size() > 0) {
				for (OrderDeliveryBusiRequest o : list) {
					if (o.getDelivery_id().equals(delivery_id)) {
						o.setShip_status(OrderStatus.DELIVERY_SHIP_SATUS_1);
						o.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						o.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						o.store();
						break;
					}
				}
			}
		}
	}

	/**
	 * 批量处理订单
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-11-12
	 * @return
	 */
	public String flowBatchDeal() {
		String error_msg = "";
		String success_msg = "";
		if (orderidArray != null) {
			for (String oid : orderidArray) {
				this.order_id = oid;
				String lockMsg = checkLockUser();
				if (!StringUtils.isEmpty(lockMsg)) {
					error_msg += "[" + oid + "]" + lockMsg + "。\\n";
					continue;
				}
				ThreadContextHolder.setHttpRequest(getRequest());// 线程变量
				// pageLoad属性验证方法校验
				List<AttrInstLoadResp> attrInstLoadResps = AttrBusiInstTools.validateOrderAttrInstsForPage(order_id, ConstsCore.ATTR_ACTION_LOAD_AND_UPDATE);
				if (attrInstLoadResps != null && attrInstLoadResps.size() > 0) {
					for (AttrInstLoadResp al : attrInstLoadResps) {
						error_msg += "[" + oid + "][" + al.getField_desc() + "]" + al.getCheck_message() + "\\n";
					}
					continue;
				}
				BusiCompRequest busi = new BusiCompRequest();
				Map queryParams = new HashMap();
				queryParams.put("order_id", this.order_id);
				busi.setEnginePath("enterTraceRule.exec");
				queryParams.put("deal_from", EcsOrderConsts.DEAL_FROM_PAGE);
				busi.setOrder_id(this.order_id);
				busi.setQueryParams(queryParams);
				queryParams.put("is_auto", EcsOrderConsts.RULE_EXE_ALL);
				BusiCompResponse response = null;
				try {
					response = orderServices.execBusiComp(busi);
				} catch (Exception e) {
					e.printStackTrace();
					response = new BusiCompResponse();
					response.setError_code("1");
					response.setError_msg("处理失败");
				}
				if ("0".equals(response.getError_code())) {
					flowDealBusiness();
					success_msg += "[" + oid + "]处理成功!\\n";
				} else {
					if (response != null && response.getResponse() != null) {
						error_msg += "[" + oid + "]" + response.getResponse().getError_msg() + "\\n";
					} else {
						error_msg += "[" + oid + "]处理失败!\\n";
					}
				}
			}
		}
		this.json = "{status:0,error_msg:'" + error_msg + "',success_msg:'" + success_msg + "'}";
		return this.JSON_MESSAGE;
	}

	private String is_order_exp;

	public String getIs_order_exp() {
		return is_order_exp;
	}

	public void setIs_order_exp(String is_order_exp) {
		this.is_order_exp = is_order_exp;
	}

	public String getActionUrl(String trace_id, String order_id) {
		String action_url = RuleFlowUtil.getOrderUrl(order_id, trace_id);
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
		return action_url;
	}

	static INetCache cache;
	public static int NAMESPACE = 308;
	public static String CURR_ORDER_CLICK_PAGE = "CURR_ORDER_CLICK_PAGE_";
	public static String LOCK_ORDER_FLOW_DEAL = "LOCK_ORDER_FLOW_DEAL_";

	static {
		cache = (INetCache) com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	}

	public String getFlowDealLock() {
		// 设置缓存锁
		String lock_flag = "";
		try {
			lock_flag = (String) cache.get(NAMESPACE, LOCK_ORDER_FLOW_DEAL + order_id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (!StringUtil.isEmpty(lock_flag)) {
			// 已有缓存锁，有进程正在处理料箱队列
			this.json = "{result:1,message:'订单正在处理,请等待!'}";
		} else {
			this.json = "{result:0,message:'订单可处理!'}";
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 并行业务办理
	 * 
	 * @return
	 */
	public String bssParallelDeal() {
		// add by wui对象锁处理,避免重复处理调用
		String lock_name = "O" + order_id;
		this.query_type = "bss_parallel";
		Map lock = getMutexLock(lock_name);
		synchronized (lock) {
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			logger.info("订单：" + order_id + "进入锁定信息！");
			String lockMsg = checkLockUser();
			if (!StringUtils.isEmpty(lockMsg)) {
				this.json = "{result:1,message:'" + lockMsg + "'}";
				removeMutexLock(lock_name);
				return this.JSON_MESSAGE;
			}
			// 退单状态不允许流转20160516
			if (EcsOrderConsts.REFUND_DEAL_TYPE_02.equals(CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getRefund_deal_type())) {
				this.json = "{result:1,message:'该订单已退单或退单申请!'}";
				removeMutexLock(lock_name);
				return this.JSON_MESSAGE;
			}
			// 每个缓解流程保存
			String msg = "";
			// WMS质检稽核，单独校验,不走共用环节界面验证
			if (!StringUtil.isEmpty(this.q_check)) {
				msg = this.physicsCheck();
			}
			q_check = "nocheck";// 并行业务办理设置不校验物流单号
			if (StringUtil.isEmpty(msg))
				msg = editSave();
			if (msg != null && msg.length() > 0) {
				String validateMsg = JSONArray.toJSONString(validateMsgList);
				this.json = "{result:1,message:'" + msg + "',validateMsgList:" + validateMsg + "}";
				removeMutexLock(lock_name);
				return this.JSON_MESSAGE;
			}
			try {
				ZteResponse response = null;
				String trace_id = "Y2";
				// 调用业务组件
				try {
					PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
					TacheFact fact = new TacheFact();
					fact.setOrder_id(this.order_id);
					req.setPlan_id(EcsOrderConsts.BUSI_HANDLE_PLAN_ID);
					req.setFact(fact);
					req.setDeleteLogs(false);// 不删除日志
					req.setAuto_exe(-1);// 手动执行
					PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
					response = CommonDataFactory.getInstance().getRuleTreeresult(planResp);

				} finally {

				}
				String currFlowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
				long end2 = System.currentTimeMillis();
				String action_url = "shop/admin/orderFlowAction!business.do?order_id=" + order_id + "&query_type=bss_parallel";
				long end3 = System.currentTimeMillis();
				logger.info("=====================>获取页面URL时间：" + (end3 - end2));
				if (!ConstsCore.ERROR_SUCC.equals(response.getError_code())) {
					msg = response.getError_msg();
					this.json = "{result:1,message:'" + msg + "'}";
				} else {
					this.json = "{result:0,message:'操作成功',action_url:'" + action_url + "'}";
					// 如果走总部通道，则表示业务是线下办理，流过业务办理环节，则标记附加产品、SP服务为线下办理成功 add by
					// shusx 2016-05-12
					if (EcsOrderConsts.DIC_ORDER_NODE_F.equals(currFlowTraceId) || EcsOrderConsts.DIC_ORDER_NODE_H.equals(currFlowTraceId) || EcsOrderConsts.DIC_ORDER_NODE_J.equals(currFlowTraceId)
							|| EcsOrderConsts.DIC_ORDER_NODE_L.equals(currFlowTraceId)) {
						String is_aop = orderTree.getOrderExtBusiRequest().getIs_aop();
						if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
							// 标记附加产品办理成功
							List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance().getOrderTree(order_id).getAttrPackageSubProdBusiRequest();
							if (attrPackageSubProdList != null && attrPackageSubProdList.size() > 0) {
								for (AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList) { // 设置线下办理成功
									subPackage.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2);
									subPackage.setDb_action(ConstsCore.DB_ACTION_UPDATE);
									subPackage.setIs_dirty(ConstsCore.IS_DIRTY_YES);
									subPackage.store();
								}
							}
							// 标记SP产品办理成功
							List<OrderSpProductBusiRequest> OrderSpProductList = CommonDataFactory.getInstance().getOrderTree(order_id).getSpProductBusiRequest();
							if (OrderSpProductList != null && OrderSpProductList.size() > 0) {
								for (OrderSpProductBusiRequest spProd : OrderSpProductList) { // 设置线下办理成功
									spProd.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2);
									spProd.setDb_action(ConstsCore.DB_ACTION_UPDATE);
									spProd.setIs_dirty(ConstsCore.IS_DIRTY_YES);
									spProd.store();
								}
							}
						}
					}
					Utils.commonUnlock(order_id, currFlowTraceId, trace_id); // 解锁公共方法
				}

			} catch (Exception e) {
				e.printStackTrace();
				String error_msg = e.getMessage();
				this.json = "{result:1,message:'" + error_msg + "'}";
			} finally {
				removeMutexLock(lock_name);
			}
		}
		return this.JSON_MESSAGE;
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
	// 每个环节的业务处理 走流程组件
	@SuppressWarnings("unchecked")
	public String flowDeal() {
		long start = System.currentTimeMillis();
		// add by wui对象锁处理,避免重复处理调用
		String lock_name = "O" + order_id;
		final String lock_flow_deal = LOCK_ORDER_FLOW_DEAL + order_id;
		Map lock = getMutexLock(lock_name);
		synchronized (lock) {
			// 设置缓存锁
			String lockOrder = (String) cache.get(NAMESPACE, lock_flow_deal);
			// boolean lock_flag = cache.add(NAMESPACE,
			// lock_flow_deal,order_id,300);
			if (!StringUtils.isEmpty(lockOrder)) {
				// 已有缓存锁，有进程正在处理料箱队列
				this.json = "{result:1,message:'订单正在处理,请等待!'}";
				return this.JSON_MESSAGE;
			}
			cache.set(NAMESPACE, lock_flow_deal, order_id, 1);
			logger.info("订单：" + order_id + "进入锁定信息！");
			// add by wui二次抓取，同步调用时验证处理
			String page_trace_id = (String) cache.get(NAMESPACE, CURR_ORDER_CLICK_PAGE + order_id); // 获取进入页面时的环节id
			OrderTreeBusiRequest otreq = CommonDataFactory.getInstance().getOrderTree(order_id);
			List<OrderAdslBusiRequest> adslreqList = otreq.getOrderAdslBusiRequest();
			String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
			String order_service_type = CommonDataFactory.getInstance().getGoodSpec(order_id, null, AttrConsts.ORDER_SERVICE_TYPE);
			String line_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.LINE_TYPR);
			//判断是否是宽带一期  进行地址保存 sgf
			try {
               Boolean flag = ordWorkManager.isKDYQ(order_id, otreq);
               if(flag){
                   Map attrMaps = getPageOrderAttrMap(order_id);
                   String adslId = ecsOrdManager.getAttrIdByName("adsl_addr");
                   String adsl_addr = (String) attrMaps.get("oattrinstspec_" + order_id + "_" + adslId + "_" + adslId + "#adsl_addr");
                   if (StringUtils.isEmpty(adsl_addr) || StringUtils.equals(adsl_addr, "-1")) {
                       this.json = "{result:1,message:'请选择标准地址,请先进行保存在审核通过'}";
                       return this.JSON_MESSAGE;
                   }
                   List<OrderAdslBusiRequest>  lists= otreq.getOrderAdslBusiRequest();
                   if(lists.size()>0){
                      String adsl_addrS = lists.get(0).getAdsl_addr();
                      if(StringUtils.isEmpty(adsl_addrS) ||"-1".equals(adsl_addrS) ){
                          this.json = "{result:1,message:'标准地址选择后，未进行保存,请保存'}";
                          return this.JSON_MESSAGE;
                      }
                   }
               }
            } catch (ApiBusiException e1) {
                e1.printStackTrace();
            }
			
			if (null != adslreqList && adslreqList.size() > 0 && StringUtils.equals(order_from, "10078")) {
				Map attrMap = getPageOrderAttrMap(order_id);
				String adslId = ecsOrdManager.getAttrIdByName("adsl_addr");
				String countyId = ecsOrdManager.getAttrIdByName("county_id");
				String modermId = ecsOrdManager.getAttrIdByName("moderm_id");

				String adsl_addr = (String) attrMap.get("oattrinstspec_" + order_id + "_" + adslId + "_" + adslId + "#adsl_addr");
				String county_id = (String) attrMap.get("oattrinstspec_" + order_id + "_" + countyId + "_" + countyId + "#county_id");
				String moderm_id = (String) attrMap.get("oattrinstspec_" + order_id + "_" + modermId + "_" + modermId + "#moderm_id");

				// 二期
				String catId = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String gyrh_cat_id = cacheUtil.getConfigInfo("gyrh_cat_id");// 固移融合单商品小类
				if (!gyrh_cat_id.contains(catId)) {// 派工单需要营业县分
					if (StringUtils.isEmpty(adsl_addr) || StringUtils.equals(adsl_addr, "-1")) {
						this.json = "{result:1,message:'请选择标准地址'}";
						return this.JSON_MESSAGE;
					}
					// FTTH宽带才校验光猫ID
					if (StringUtils.isEmpty(moderm_id) && StringUtils.equals(order_service_type, "6115")) {
						this.json = "{result:1,message:'请选择光猫ID'}";
						return this.JSON_MESSAGE;
					}

				}
				if (StringUtils.isEmpty(county_id)) {
					this.json = "{result:1,message:'请选择营业县分'}";
					return this.JSON_MESSAGE;
				}
			}
			// 判断工单是否添加
			if (null != adslreqList && adslreqList.size() > 0 && StringUtils.equals(order_from, "10078")) {
				String workMsg = ordWorkManager.orderCheck(order_id);
				if (!"".equals(workMsg)) {
					this.json = "{result:1,message:'请添加工单:" + workMsg + "'}";
					return this.JSON_MESSAGE;
				}
			}

			String currFlowTraceId = otreq.getOrderExtBusiRequest().getFlow_trace_id(); // 获取正在处理的环节id
			if (!StringUtil.isEmpty(page_trace_id) && (EcsOrderConsts.DIC_ORDER_NODE_F.equals(page_trace_id) || EcsOrderConsts.DIC_ORDER_NODE_B.equals(page_trace_id))
					&& !currFlowTraceId.equals(page_trace_id)) {
				this.json = "{result:1,message:'正在处理页面为" + page_trace_id + "，实际处理环节为" + currFlowTraceId + "与实际不一致，请确认是否重复执行。'}";
				logger.info("订单" + order_id + "正在处理页面为" + page_trace_id + "，实际处理流程为" + currFlowTraceId + "与实际不一致，请确认是否重复执行。");
				removeMutexLock(lock_name);
				cache.delete(NAMESPACE, lock_flow_deal);
				return this.JSON_MESSAGE;
			}
			// add wjq 发货按钮保存物流单号和公司
			if ("H".equals(currFlowTraceId) && shipping_company != null) {
				String[] str = new String[1];
				str[0] = shipping_company;
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "shipping_company" }, str);
			}
			
			String lockMsg = checkLockUser();
			if (!StringUtils.isEmpty(lockMsg)) {
				this.json = "{result:1,message:'" + lockMsg + "'}";
				removeMutexLock(lock_name);
				cache.delete(NAMESPACE, lock_flow_deal);
				return this.JSON_MESSAGE;
			}
			// 退单状态不允许流转20160516
			if (EcsOrderConsts.REFUND_DEAL_TYPE_02.equals(CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getRefund_deal_type())) {
				this.json = "{result:1,message:'该订单已退单或退单申请!'}";
				removeMutexLock(lock_name);
				cache.delete(NAMESPACE, lock_flow_deal);
				return this.JSON_MESSAGE;
			}
			// 每个缓解流程保存
			String msg = "";
			// WMS质检稽核，单独校验,不走共用环节界面验证
			if (!StringUtil.isEmpty(this.q_check)) {
				msg = this.physicsCheck();
			}
			if (StringUtil.isEmpty(msg))
				msg = editSave();
			if (msg != null && msg.length() > 0) {
				String validateMsg = JSONArray.toJSONString(validateMsgList);
				this.json = "{result:1,message:'" + msg + "',validateMsgList:" + validateMsg + "}";
				removeMutexLock(lock_name);
				cache.delete(NAMESPACE, lock_flow_deal);
				return this.JSON_MESSAGE;
			}

			// 宽带互联网化业务类型校验
			if (null != adslreqList && adslreqList.size() > 0 && StringUtils.equals(order_from, "10078") && !StringUtils.isEmpty(line_type)) {
				if (!StringUtils.isEqual(line_type, order_service_type)) {
					String order_service_name = CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_SERVICE_TYPE", order_service_type);
					String line_name = CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_SERVICE_TYPE", line_type);
					this.json = "{result:1,message:'所选地址业务类型（" + line_name + "）与商品业务类型（" + order_service_name + "）不一致，请重新选择地址或重新选择商品！'}";
					return this.JSON_MESSAGE;
				}
			}
			String abnormal_type = orderTree.getOrderExtBusiRequest().getAbnormal_type();
			// 如果异常没有恢复，则先恢复异常
			if (!StringUtil.isEmpty(abnormal_type) && !abnormal_type.equals(EcsOrderConsts.ORDER_ABNORMAL_TYPE_0)) {
				// 恢复订单异常
				OrderExtBusiRequest orderExtBusiReq = orderTree.getOrderExtBusiRequest();
				BusiCompRequest busi = new BusiCompRequest();
				Map queryParams = new HashMap();
				queryParams.put("order_id", order_id);
				queryParams.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
				queryParams.put(EcsOrderConsts.EXCEPTION_TYPE, orderExtBusiReq.getException_type());
				queryParams.put(EcsOrderConsts.EXCEPTION_REMARK, orderExtBusiReq.getException_desc());
				busi.setEnginePath("zteCommonTraceRule.restorationException");
				busi.setOrder_id(order_id);
				busi.setQueryParams(queryParams);
				try {
					ZteResponse response = orderServices.execBusiComp(busi);
					if (!EcsOrderConsts.RULE_EXE_FLAG_SUCC.equals(response.getError_code())) {
						CommonTools.addBusiError(response.getError_code(), response.getError_msg());
					}
				} catch (ApiBusiException busiE) {
					this.json = "{result:1,message:'操作失败：' " + busiE.getMessage() + "}";
					return this.JSON_MESSAGE;
				} catch (Exception e) {
					this.json = "{result:1,message:'操作失败：异常恢复失败'}";
					return this.JSON_MESSAGE;
				}
			}
			
			if("1".equals(otreq.getOrderExtBusiRequest().getIs_work_custom())){
				//处理自定义流程
				try{
					//执行自定义流程
					this.workCutomOrderContinue(page_trace_id);
					
					//重新获取订单树
					OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(this.order_id);
					
					//下一环节页面
					String action_url = this.getWorkCustomUrl();
					
					this.json = "{result:0,message:'操作成功',action_url:'" + action_url + "'}";
				}catch(Exception e) {
					this.json = "{result:1,message:'操作失败，错误信息:" + e.getMessage() + "'}";
					e.printStackTrace();
				} finally {
					cache.delete(NAMESPACE, lock_flow_deal);
					removeMutexLock(lock_name);
				}
			}else{
				//处理原有规则
				long end2 = System.currentTimeMillis();
				try {
					final String p_order_id = order_id;
					final String p_dealType = dealType;
					final String p_dealDesc = getDealDesc();
					final Map<String, String> map = new HashMap<String, String>();
					AdminUser user = ManagerUtils.getAdminUser();
					map.put("user_id", user.getUserid());
					map.put("user_name", user.getUsername());
					map.put("currFlowTraceId", currFlowTraceId);
					if (EcsOrderConsts.DIC_ORDER_NODE_B.equals(currFlowTraceId) || EcsOrderConsts.DIC_ORDER_NODE_H.equals(currFlowTraceId) || EcsOrderConsts.DIC_ORDER_NODE_X.equals(currFlowTraceId)
							|| (EcsOrderConsts.DIC_ORDER_NODE_F.equals(currFlowTraceId) && !EcsOrderConsts.ORDER_MODEL_01.equals(otreq.getOrderExtBusiRequest().getOrder_model()))) {
						Runnable run = new Runnable() {
							@Override
							public void run() {
								try {
									ordCollectManager.setOrderHide(order_id, "4", "异步隐藏");
									String msg = flow_deal_detail(order_id, p_dealType, p_dealDesc, map);

									String page_tra_id = map.get("currFlowTraceId");
									JSONObject jsonObj = JSONObject.fromObject(msg);
									if (jsonObj.get("result") != null && "1".equals(jsonObj.get("result").toString())) {
										OrderTreeBusiRequest ot = CommonDataFactory.getInstance().getOrderTree(order_id);
										// syncProcAddExce(page_tra_id, ot,
										// jsonObj.get("message"));
										OrderHandleLogsReq logReq = new OrderHandleLogsReq();
										logReq.setOrder_id(order_id);
										logReq.setFlow_id(ot.getOrderExtBusiRequest().getFlow_id());
										logReq.setFlow_trace_id(ot.getOrderExtBusiRequest().getFlow_trace_id());
										logReq.setComments("审核失败");
										logReq.setOp_id(ManagerUtils.getUserId());
										logReq.setOp_name(ManagerUtils.getAdminUser().getUsername());
										logReq.setHandler_type(ZjEcsOrderConsts.BATCH_PREHANDLE_EXP);
										IOrderFlowManager orderFlowManager = SpringContextHolder.getBean("ordFlowManager");
										orderFlowManager.insertOrderHandLog(logReq);
									}
								} catch (Exception e) {
									e.printStackTrace();
								} finally {
									cache.delete(NAMESPACE, lock_flow_deal);
									ordCollectManager.cancelOrderHide(order_id);
								}
							}
						};
						ThreadPoolFactory.getRoleDataThreadPoolExector().execute(run);
						String action_url = getActionUrl(currFlowTraceId, orderTree.getOrder_id());
						this.json = "{result:0,message:'操作成功',action_url:'" + action_url + "'}";
					} else {
						this.json = flow_deal_detail(order_id, p_dealType, p_dealDesc, null);
						cache.delete(NAMESPACE, lock_flow_deal);
					}
				} catch (Exception e) {
					this.json = "{result:1,message:'操作失败,系统异常:" + e.getMessage() + "'}";
					e.printStackTrace();
				} finally {
					removeMutexLock(lock_name);
				}
			}
			if("H".equals(page_trace_id)){
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String logisticsSMS = cacheUtil.getConfigInfo("logisticsSMS");
				if(!StringUtil.isEmpty(logisticsSMS)&&"1".equals(logisticsSMS)){
					ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
					BusiCompRequest busiCompRequest = new BusiCompRequest();
					busiCompRequest.setEnginePath("zteCommonTraceRule.logisticsSMS");
					busiCompRequest.setOrder_id(order_id);
					Map queryParams = new HashMap();
					busiCompRequest.setQueryParams(queryParams);
					BusiCompResponse busiCompResponse = client.execute(busiCompRequest, BusiCompResponse.class);
				}
				
			}
		}
		
		return this.JSON_MESSAGE;
	}
	
	private void workCutomOrderContinue(String flow_trace_id) throws Exception{
		//根据订单编号查询自定义流程当前环节
		ES_WORK_CUSTOM_NODE_INS pojo = new ES_WORK_CUSTOM_NODE_INS();
		pojo.setOrder_id(this.order_id);
		pojo.setIs_curr_step("1");
		
		List<ES_WORK_CUSTOM_NODE_INS> inses = this.workCustomCfgManager.qryInsList(pojo , null);
		
		String curr_old_node_code = "";
		
		if(inses!=null && inses.size()>0 
				&& org.apache.commons.lang.StringUtils.isNotBlank(inses.get(0).getOld_node_code())){
			//获取当前环节的旧环节编码
			curr_old_node_code = inses.get(0).getOld_node_code();
		}
		
		if(!curr_old_node_code.equals(flow_trace_id)){
			//环节校验失败，抛出异常
			throw new Exception("正在处理页面为" + flow_trace_id + 
					"，实际处理环节为" + curr_old_node_code + "与实际不一致，请确认是否重复执行。'}");
		}
		
		//继续执行流程
		WORK_CUSTOM_FLOW_DATA flowData = this.workCustomEngine.continueWorkFlow(this.order_id);
		
		//执行失败的抛出异常
		if(ConstsCore.ERROR_FAIL.equals(flowData.getRun_result()))
			throw new Exception(flowData.getRun_msg());
	}

	public String flow_deal_detail(String order_id, String p_dealType, String p_dealDesc, Map<String, String> map) {
		String msg = "";
		String json = "";
		try {
			if (null != map) {
				AdminUser importUser = new AdminUser();
				importUser.setUserid(map.get("user_id"));
				importUser.setUsername(map.get("user_name"));
				ManagerUtils.createSession(importUser);
			}
	        ZteResponse response = null;
            OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			String curr_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			String currFlowTraceId = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id(); // 获取正在处理的环节id
            String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
            String cat_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);
			String trace_id = null;
			// 调用业务组件
			try {
			    if("10093".equals(order_from) && "221668199563784192".equals(cat_id) && "B".equals(curr_trace_id)){
			        PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
			        TacheFact facts = new TacheFact();
			        facts.setOrder_id(order_id);
			        req.setPlan_id("100");
			        req.setFact(facts);
			        req.setDeleteLogs(false);// 不删除日志，不允许二次操作
			        req.setAuto_exe(-1);
			        PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
			        response = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
			    }else{
			        // 预处理现场交付的订单不需要校验身份证 设置校验 状态 不真实调接口校验
			        String send_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SENDING_TYPE);
			        if (EcsOrderConsts.SHIPPING_TYPE_XJ.equals(send_type) && EcsOrderConsts.DIC_ORDER_NODE_B.equals(currFlowTraceId)) {
			            // 成功设置预校验成功状态
			            String idCardStatus = EcsOrderConsts.ACCOUNT_VALI_1;// 身份证校验成功状态
			            CommonDataFactory.getInstance().updateAttrFieldValue(this.order_id, new String[] { AttrConsts.ACCOUNT_VALI }, new String[] { idCardStatus });
			        }
			        BusiCompRequest busi = new BusiCompRequest();
			        Map queryParams = new HashMap();
			        queryParams.put("order_id", this.order_id);
			        busi.setEnginePath("enterTraceRule.exec");
			        busi.setOrder_id(this.order_id);
			        queryParams.put("deal_from", EcsOrderConsts.DEAL_FROM_PAGE);
			        queryParams.put("deal_type", dealType);
			        queryParams.put("deal_desc", getDealDesc());
			        queryParams.put("is_auto", EcsOrderConsts.RULE_EXE_ALL);
			        busi.setQueryParams(queryParams);
			        
			        /**
			         * 1、处理思路：调用+同步锁方法，控制按串行流程执行
			         * 2、对质检环节特殊处理，当前为质检环节、且数据库也为治检环节，则退出处理
			         */
			        response = orderServices.execBusiComp(busi);
			    }
	            

			} finally {
				orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();

				// 如果当前环节是开户环节，下一环节是写卡环节侧跳过写卡环节
				// EcsOrderConsts.DIC_ORDER_NODE_D.equals(curr_trace_id) &&
				if (EcsOrderConsts.DIC_ORDER_NODE_D.equals(curr_trace_id) && EcsOrderConsts.DIC_ORDER_NODE_X.equals(trace_id)) {
					TacheFact fact = new TacheFact();
					fact.setOrder_id(order_id);
					// 跳过写卡环节
					String next_rule_id = "";
					String mode = orderTree.getOrderExtBusiRequest().getOrder_model();
					String is_aop = orderTree.getOrderExtBusiRequest().getIs_aop();
					if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
						next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_01;
						if (EcsOrderConsts.ORDER_MODEL_02.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_02;
						} else if (EcsOrderConsts.ORDER_MODEL_03.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_03;
						} else if (EcsOrderConsts.ORDER_MODEL_04.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_04;
						} else if (EcsOrderConsts.ORDER_MODEL_05.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_05;
						} else if (EcsOrderConsts.ORDER_MODEL_05.equals(mode)) {
							next_rule_id = ZjEcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_06;
						}
					} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
						next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_01_BSSKL;
						if (EcsOrderConsts.ORDER_MODEL_02.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_02_BSSKL;
						} else if (EcsOrderConsts.ORDER_MODEL_03.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_03_BSSKL;
						} else if (EcsOrderConsts.ORDER_MODEL_04.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_04_BSSKL;
						} else if (EcsOrderConsts.ORDER_MODEL_05.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_05_BSSKL;
						}
					} else {
						next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_01_AOP;
						if (EcsOrderConsts.ORDER_MODEL_02.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_02_AOP;
						} else if (EcsOrderConsts.ORDER_MODEL_03.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_03_AOP;
						} else if (EcsOrderConsts.ORDER_MODEL_04.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_04_AOP;
						} else if (EcsOrderConsts.ORDER_MODEL_05.equals(mode)) {
							next_rule_id = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_05_AOP;
						}
					}
					RuleTreeExeResp rresp = RuleFlowUtil.executeRuleTree(EcsOrderConsts.RULE_PLAN_ID_103, next_rule_id, fact, false, false, EcsOrderConsts.DEAL_FROM_PAGE);
				}
			}

			if (response != null && "0".equals(response.getError_code())) {
				flowDealBusiness();
			}
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			String action_url = getActionUrl(trace_id, orderTree.getOrder_id());
			if (!ConstsCore.ERROR_SUCC.equals(response.getError_code())) {
				msg = response.getError_msg();
				json = "{result:1,message:'" + msg + "'}";
			} else {
				json = "{result:0,message:'操作成功',action_url:'" + action_url + "'}";
				// 记录最后一次页面操作预处理、预捡货的工号信息，供开户使用
				if (EcsOrderConsts.DIC_ORDER_NODE_B.equals(currFlowTraceId) || EcsOrderConsts.DIC_ORDER_NODE_C.equals(currFlowTraceId)) {
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.PRE_LOCK_USER_ID }, new String[] { ManagerUtils.getUserId() });
				}
				// 如果走总部通道，则表示业务是线下办理，流过业务办理环节，则标记附加产品、SP服务为线下办理成功 add by
				// shusx 2016-05-12
				if (EcsOrderConsts.DIC_ORDER_NODE_F.equals(currFlowTraceId) || EcsOrderConsts.DIC_ORDER_NODE_H.equals(currFlowTraceId) || EcsOrderConsts.DIC_ORDER_NODE_J.equals(currFlowTraceId)
						|| EcsOrderConsts.DIC_ORDER_NODE_L.equals(currFlowTraceId)) {
					String is_aop = orderTree.getOrderExtBusiRequest().getIs_aop();
					if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
						// 标记附加产品办理成功
						List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance().getOrderTree(order_id).getAttrPackageSubProdBusiRequest();
						if (attrPackageSubProdList != null && attrPackageSubProdList.size() > 0) {
							for (AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList) { // 设置线下办理成功
								subPackage.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2);
								subPackage.setDb_action(ConstsCore.DB_ACTION_UPDATE);
								subPackage.setIs_dirty(ConstsCore.IS_DIRTY_YES);
								subPackage.store();
							}
						}
						// 标记SP产品办理成功
						List<OrderSpProductBusiRequest> OrderSpProductList = CommonDataFactory.getInstance().getOrderTree(order_id).getSpProductBusiRequest();
						if (OrderSpProductList != null && OrderSpProductList.size() > 0) {
							for (OrderSpProductBusiRequest spProd : OrderSpProductList) { // 设置线下办理成功
								spProd.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2);
								spProd.setDb_action(ConstsCore.DB_ACTION_UPDATE);
								spProd.setIs_dirty(ConstsCore.IS_DIRTY_YES);
								spProd.store();
							}
						}
					}
				}
				Utils.commonUnlock(order_id, curr_trace_id, trace_id); // 解锁公共方法
			}

		} catch (Exception e) {
			e.printStackTrace();
			String error_msg = e.getMessage();
			json = "{result:1,message:'" + error_msg + "'}";
		}
		return json;
	}

	public void syncProcAddExce(String page_tra_id, OrderTreeBusiRequest ot, Object object) {
		String exception_id = "99";
		String node_desc = "";
		if (EcsOrderConsts.DIC_ORDER_NODE_B.equals(page_tra_id)) {
			node_desc = EcsOrderConsts.DIC_ORDER_NODE_B_DESC;
		} else if (EcsOrderConsts.DIC_ORDER_NODE_X.equals(page_tra_id)) {
			node_desc = EcsOrderConsts.DIC_ORDER_NODE_X_DESC;
		} else if (EcsOrderConsts.DIC_ORDER_NODE_F.equals(page_tra_id)) {
			node_desc = EcsOrderConsts.DIC_ORDER_NODE_F_DESC;
		} else if (EcsOrderConsts.DIC_ORDER_NODE_H.equals(page_tra_id)) {
			node_desc = EcsOrderConsts.DIC_ORDER_NODE_H_DESC;
		}
		String exception_remark = node_desc + "后台处理失败:" + object;
		Map params = new HashMap();
		params.put("order_id", order_id);
		params.put("exception_id", exception_id);
		params.put("exception_remark", exception_remark);
		params.put("abnormal_type", EcsOrderConsts.ORDER_ABNORMAL_TYPE_1);
		OrderExceptionCollectResp orderExceptionCollectResp = ordExceptionHandleImpl.exceptionHandleAct(params);
		// 异常记录
		OrderExceptionLogsReq exceptionLogReq = new OrderExceptionLogsReq();
		exceptionLogReq.setOrder_id(order_id);
		exceptionLogReq.setFlow_id(ot.getOrderExtBusiRequest().getFlow_id());
		exceptionLogReq.setFlow_trace_id(ot.getOrderExtBusiRequest().getFlow_trace_id());
		exceptionLogReq.setMark_op_id("admin");
		exceptionLogReq.setMark_op_name("系统");
		exceptionLogReq.setAbnormal_type(EcsOrderConsts.ORDER_ABNORMAL_TYPE_1);
		exceptionLogReq.setException_desc(exception_remark);
		exceptionLogReq.setException_type(exception_id);
		ordFlowManager.insertOrderExceptionLog(exceptionLogReq);
		// 调用异常系统
		Utils.writeExp(order_id, exception_id, exception_remark, EcsOrderConsts.BASE_YES_FLAG_1);
	}

	public String editSave() {
		Map old_map = ecsOrdManager.getUpdate(order_id);
		long start_s = System.currentTimeMillis();
		// 校验前，先将校验值清空处理
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		ThreadContextHolder.setHttpRequest(getRequest());

		String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		// 属性验证方法校验
		long start1 = System.currentTimeMillis();
		String msg = "";
		if (q_check == null || q_check.equals("")) { // WMS质检稽核环节q_check='n',不做界面环节验证
			msg = this.validte();
		} else if ("n".equals(q_check)) {// WMS质检稽核环节q_check='n',物流单号还是要校验的
			msg = this.validteLogino();
		}
		if (!StringUtil.isEmpty(msg)) {
			return msg;
		}
		msg = validteTerminalNum();
		if (!StringUtil.isEmpty(msg)) {
			return msg;
		}

		long end1 = System.currentTimeMillis();

		if (msg.length() == 0) {

			List<AttrInstBusiRequest> pageAttrInstBusiRequests = AttrBusiInstTools.getPageDirtyOrderAttrInst(order_id, orderTree);

			int col1Count = 0;
			for (AttrInstBusiRequest pageAttrInstBusiRequest : pageAttrInstBusiRequests) {
				// 是否闪电送和物流公司字段变动时 重新匹配模式
				if (AttrConsts.SHIPPING_QUICK.equals(pageAttrInstBusiRequest.getField_name()) || AttrConsts.SHIPPING_COMPANY.equals(pageAttrInstBusiRequest.getField_name())) {
					col1Count++;
				}
				pageAttrInstBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				pageAttrInstBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				long start2 = System.currentTimeMillis();
				pageAttrInstBusiRequest.store(); // 属性数据入库
				long end2 = System.currentTimeMillis();
				logger.info("store:==" + (end2 - start2));
			}

			String flow_trace = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();

			everyDealSave(flow_trace);// 不同环节的特殊处理
			// 在预处理环节 是否闪电送和物流公司字段变动时 重新匹配模式
			if (col1Count > 0 && EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace)) {
				OrderTreeBusiRequest orderTree1 = new OrderTreeBusiRequest();
				orderTree1.setOrder_id(orderTree.getOrder_id());
				orderTree1.setCol1(EcsOrderConsts.REP_EXEC_YES);
				orderTree1.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderTree1.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderTree1.store(); // 属性数据入库
			}
			// 保存的时候 校验信息同步总部
			String pre_trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
			if (EcsOrderConsts.BTN_TYPE_SAVE.equals(this.btnType) && EcsOrderConsts.DIC_ORDER_NODE_B.equals(pre_trace_id)) {
				logger.info("=============预处理调变更信息同步总部==================");
				msg = this.PreDealInfoToZB();
			}
		}
		Map new_map = ecsOrdManager.getUpdate(order_id);
		ecsOrdManager.saveChange(old_map, new_map, order_id);
		long end_s = System.currentTimeMillis();
		logger.info("订单信息保存总耗时============================================================>" + (end_s - start_s));
		return msg;
	}

	// 不同环节的保存方法
	public String everyDealSave(String flow_trace) {
		/*
		 * if(ConstsCore.TRACE_B.equals(flow_trace)||EcsOrdManager.
		 * QUERY_TYPE_YCL .equals(this.query_type)){ ord_receipt_new();//测试代码
		 * return ""; }
		 */
		if (ConstsCore.TRACE_B.equals(flow_trace) || EcsOrdManager.QUERY_TYPE_YCL.equals(this.query_type)) {
			this.preDealSave();
		} else if (ConstsCore.TRACE_C.equals(flow_trace)) {
			String order_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_TYPE);
			String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
			if (EcsOrderConsts.ORDER_TYPE_09.equals(order_type) || // 当订单类型为多商品类型时
					order_from.equals(EcsOrderConsts.ORDER_FROM_10061)) {// 当来源为华盛B2C时
				this.savePrePickMore();
			}
		} else if (ConstsCore.TRACE_H.equals(flow_trace)) {
			shipping();
		} else if (ConstsCore.TRACE_F.equals(flow_trace)) {
			quality_audit();
		} else if (ConstsCore.TRACE_J.equals(flow_trace)) {
			ord_receipt_new();
		} else if (ConstsCore.TRACE_L.equals(flow_trace)) {
			orderArchive();
		}
		return "";
	}

	/**
	 * 回单确认
	 */
	public void ord_receipt() {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
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
		} else {
			deliverBusiReq = new OrderDeliveryBusiRequest();
			deliverBusiReq.setDelivery_type(EcsOrderConsts.LOGIS_NORMAL);// 创建一条正常发货的记录
			deliverBusiReq.setShip_status(OrderStatus.DELIVERY_SHIP_SATUS_NOT);// 未发货、未打印
			deliverBusiReq.setOrder_id(order_id);
			deliverBusiReq.setDb_action(ConstsCore.DB_ACTION_INSERT);
			deliverBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			deliverBusiReq.store();
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			orderTree.getOrderDeliveryBusiRequests().add(deliverBusiReq);
			try {
				logsServices.cacheOrderTree(orderTree);
			} catch (ApiBusiException e) {
				e.printStackTrace();
			}
		}
		deliverBusiReq.setUser_recieve_time(ordReceipt == null ? "" : ordReceipt.getUser_recieve_time());
		orderExtBusiRequest.setMaterial_retrieve(ordReceipt == null ? "" : ordReceipt.getMaterial_retrieve());
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		deliverBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.store();
		deliverBusiReq.store();
		String[] field_name = new String[] { "is_upload", "file_return_type" };
		String[] field_value = new String[] { ordReceipt == null ? "" : ordReceipt.getIs_upload(), ordReceipt == null ? "" : ordReceipt.getFile_return_type() };
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, field_name, field_value);
	}

	/**
	 * 回单（浙江联通资料归档环节保存）
	 */
	public void ord_receipt_new() {
		// 1、保存资料信息到数据库
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();

		orderExtBusiRequest.setArchive_num(ordReceipt.getArchive_num());
		orderExtBusiRequest.setNet_certi(Integer.valueOf(ordReceipt.getNet_certi()));
		orderExtBusiRequest.setAccept_agree(Integer.valueOf(ordReceipt.getAccept_agree()));
		orderExtBusiRequest.setLiang_agree(Integer.valueOf(ordReceipt.getLiang_agree()));

		orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.store();

		OrderBusiRequest orderBusiRequest = orderTree.getOrderBusiRequest();
		orderBusiRequest.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_10);// 交易成功
		orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusiRequest.store();

	}

	/**
	 * 质检稽核界面保存
	 */
	public void quality_audit() {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);

		// 收货信息|物流信息
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
		} else {
			deliverBusiReq = new OrderDeliveryBusiRequest();
			deliverBusiReq.setDelivery_type(EcsOrderConsts.LOGIS_NORMAL);// 创建一条正常发货的记录
			deliverBusiReq.setShip_status(OrderStatus.DELIVERY_SHIP_SATUS_NOT);// 未发货、未打印
			deliverBusiReq.setOrder_id(order_id);
			deliverBusiReq.setDb_action(ConstsCore.DB_ACTION_INSERT);
			deliverBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			deliverBusiReq.store();
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			orderTree.getOrderDeliveryBusiRequests().add(deliverBusiReq);
			try {
				logsServices.cacheOrderTree(orderTree);
			} catch (ApiBusiException e) {
				e.printStackTrace();
			}
		}
		deliverBusiReq.setProvince_id(provinc_code);
		deliverBusiReq.setCity_id(city_code);
		deliverBusiReq.setRegion_id(district_id);
		deliverBusiReq.setProtect_price(protect_free != null ? Double.parseDouble(protect_free) : 0.0);
		deliverBusiReq.setLogi_receiver_phone(logi_receiver_phone);
		deliverBusiReq.setLogi_receiver(logi_receiver);
		if ("SF-FYZQYF".equals(shipping_company) || "SF0001".equals(shipping_company) || "SF0002".equals(shipping_company) || "SF0003".equals(shipping_company)) {
			deliverBusiReq.setNeed_receipt(need_receipt);
		}
		deliverBusiReq.setDelivery_type(EcsOrderConsts.LOGIS_NORMAL); // 正常单（ZX
																		// add
																		// 2014-12-26
																		// 12:12:12）
		deliverBusiReq.setLogi_no(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.LOGI_NO));
		// orderTree.getOrderDeliveryBusiRequests().set(0, deliverBus、iReq);
		deliverBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		deliverBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		deliverBusiReq.store(); // 属性数据入库
		String shipping_company_name = this.ordFlowManager.getCompanyNameByCode(this.shipping_company);
		String[] field_name = new String[] { "prod_audit_status", "shipping_company", "carry_person", "carry_person_mobile", "shipping_company_name" };
		String[] field_value = new String[] { prod_audit_status, shipping_company, logi_receiver, logi_receiver_phone, shipping_company_name };
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, field_name, field_value);

	}

	// 保存每个环节的处理信息
	public String insertDealInfo(String flow_trace_id, String addMsg) {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);

		OrderHandleLogsReq req = new OrderHandleLogsReq();
		String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
		req.setOrder_id(order_id);
		req.setFlow_id(flow_id);
		req.setFlow_trace_id(flow_trace_id);
		req.setComments(addMsg + this.getDealDesc());
		req.setHandler_type(Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
		req.setType_code(this.dealType);
		this.ordFlowManager.insertOrderHandLog(req);
		return "";
	}

	public String validte() {
		String msg = "";
		ThreadContextHolder.setHttpRequest(getRequest());// 线程变量
		// pageLoad属性验证方法校验
		validateMsgList = new ArrayList();
		long start = System.currentTimeMillis();
		List<AttrInstLoadResp> attrInstLoadResps = AttrBusiInstTools.validateOrderAttrInstsForPage(order_id, ConstsCore.ATTR_ACTION_LOAD_AND_UPDATE);
		long end = System.currentTimeMillis();
		// logger.info("=============>属性解析耗时:"+(end-start));
		if (attrInstLoadResps.size() > 0) {
			msg += "校验失败,请更新页面高亮部分。";

			for (AttrInstLoadResp attrInstLoadResp : attrInstLoadResps) {
				Map map = new HashMap();
				map.put("field_name", attrInstLoadResp.getField_name());
				map.put("check_msg", attrInstLoadResp.getCheck_message());
				this.validateMsgList.add(map);
				msg += attrInstLoadResp.getCheck_message();
			}
		}
		String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(this.order_id).getOrderExtBusiRequest().getFlow_trace_id();

		if ("ordList".equals(this.btnType)) {// 如果是点订单列表上的按钮直接处理 就需要从订单树上取

			OrderDeliveryBusiRequest deliverBusiReq = CommonDataFactory.getInstance().getOrderTree(this.order_id).getOrderDeliveryBusiRequests().get(0);
			if (deliverBusiReq != null) {
				this.provinc_code = deliverBusiReq.getProvince_id();
				this.city_code = deliverBusiReq.getCity_id();
				this.district_id = deliverBusiReq.getRegion_id();
			}
			this.shipping_company = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIPPING_COMPANY);
		}
		if (EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id)) {
			String ss = AttrUtils.isShowShipVisiableBySendType(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SENDING_TYPE));
			if ((EcsOrderConsts.IS_DEFAULT_VALUE).equals(ss)) {

				if (provinc_code == null) {
					Map map = new HashMap();
					map.put("field_name", "provinc_code");
					map.put("check_msg", "收货省份不能为空。");
					this.validateMsgList.add(map);
					msg += "收货省份不能为空。";
				}
				if (this.city_code == null) {
					Map map = new HashMap();
					map.put("field_name", "city_code");
					map.put("check_msg", "收货地市不能为空。");
					this.validateMsgList.add(map);
					msg += "收货地市不能为空。";
				}
				if (this.district_id == null) {
					Map map = new HashMap();
					map.put("field_name", "district_id");
					map.put("check_msg", "收货区县不能为空。");
					this.validateMsgList.add(map);
					msg += "收货区县不能为空。";
				}
			}
			String order_model = CommonDataFactory.getInstance().getAttrFieldValue(this.order_id, AttrConsts.ORDER_MODEL);
			if ((StringUtil.isEmpty(this.shipping_company) || ConstsCore.NULL_VAL.equals(this.shipping_company))&& (EcsOrderConsts.DIC_ORDER_NODE_F.equals(flow_trace_id) || (EcsOrderConsts.ORDER_MODEL_01.equals(order_model) && EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id)))) {
				Map map = new HashMap();
				map.put("field_name", "shipping_company");
				map.put("check_msg", "物流公司名称不能为空。");
				this.validateMsgList.add(map);
				msg += "物流公司名称不能为空。";
			}

		}
		return msg;
	}

	public String validteTerminalNum() {// 终端串号校验
		String msg = "";
		String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(this.order_id).getOrderExtBusiRequest().getFlow_trace_id();
		if (EcsOrderConsts.DIC_ORDER_NODE_C.equals(flow_trace_id)) {
			String terminal_num = "";
			boolean is_terminal_num_dirty = false;
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);// 获取脏数据
			List<AttrInstBusiRequest> pageAttrInstBusiRequests = AttrBusiInstTools.getPageDirtyOrderAttrInst(order_id, orderTree);
			for (AttrInstBusiRequest pageAttrInstBusiRequest : pageAttrInstBusiRequests) {
				if (AttrConsts.TERMINAL_NUM.equals(pageAttrInstBusiRequest.getField_name())) {
					terminal_num = pageAttrInstBusiRequest.getField_value();// 获取页面新的物流单号(最理想是直接获取页面传过来的值)
					is_terminal_num_dirty = true;
					break;
				}
			}
			// if(!is_terminal_num_dirty){//若终端串号未修改，认为单号没问题
			// terminal_num =
			// CommonDataFactory.getInstance().getAttrFieldValue(this.order_id,
			// AttrConsts.TERMINAL_NUM);
			// }
			Map map = new HashMap();
			validateMsgList = new ArrayList();
			/*
			 * if(org.apache.commons.lang3.StringUtils.isBlank(logi_no)){//
			 * 物流单号不能为空
			 * 。这一点在页面已经校验过，但页面没有过滤纯空格的字符串。纯空格不是脏数据，这里会导致页面提示操作成功，但实际不会保存 // msg
			 * += "校验失败,请更新页面高亮部分。"; map.put("field_name",AttrConsts.LOGI_NO);
			 * map.put("check_msg", "物流单号不能为空。"); this.validateMsgList.add(map);
			 * msg += "物流单号不能为空。"; return msg; }
			 */
			if (!StringUtil.isEmpty(terminal_num)) {
				boolean flag = ecsOrdManager.terminalNumCheck(order_id, terminal_num);
				if (!flag) {// 页面修改的物流单号已经被其他订单使用过
					// msg += "校验失败,请更新页面高亮部分。";
					map.put("field_name", AttrConsts.TERMINAL_NUM);
					map.put("check_msg", "校验串号失败");
					this.validateMsgList.add(map);
					msg += "校验串号失败" + terminal_num + "。";
					return msg;
				}
			}
		}
		return msg;
	}

	public String validteLogino() {// 实物稽核页面保存校验物流单号
		String msg = "";
		String logi_no = "";
		boolean is_logiNo_dirty = false;
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);// 获取脏数据
		List<AttrInstBusiRequest> pageAttrInstBusiRequests = AttrBusiInstTools.getPageDirtyOrderAttrInst(order_id, orderTree);
		for (AttrInstBusiRequest pageAttrInstBusiRequest : pageAttrInstBusiRequests) {
			if (AttrConsts.LOGI_NO.equals(pageAttrInstBusiRequest.getField_name())) {
				logi_no = pageAttrInstBusiRequest.getField_value();// 获取页面新的物流单号(最理想是直接获取页面传过来的值)
				is_logiNo_dirty = true;
				break;
			}
		}
		if (!is_logiNo_dirty) {// 若logi_no物流单号未修改，认为单号没问题
			logi_no = CommonDataFactory.getInstance().getAttrFieldValue(this.order_id, AttrConsts.LOGI_NO);
		}
		Map map = new HashMap();
		validateMsgList = new ArrayList();
		if (org.apache.commons.lang3.StringUtils.isBlank(logi_no)) {// 物流单号不能为空。这一点在页面已经校验过，但页面没有过滤纯空格的字符串。纯空格不是脏数据，这里会导致页面提示操作成功，但实际不会保存
			// msg += "校验失败,请更新页面高亮部分。";
			map.put("field_name", AttrConsts.LOGI_NO);
			map.put("check_msg", "物流单号不能为空。");
			this.validateMsgList.add(map);
			msg += "物流单号不能为空。";
			return msg;
		}
		String count = orderExtManager.getOrderCountByLogino(logi_no, order_id);
		if (!StringUtils.equals("0", count)) {// 页面修改的物流单号已经被其他订单使用过
			// msg += "校验失败,请更新页面高亮部分。";
			map.put("field_name", AttrConsts.LOGI_NO);
			map.put("check_msg", "物流单号" + logi_no + "已被使用过。");
			this.validateMsgList.add(map);
			msg += "物流单号" + logi_no + "已被使用过。";
			return msg;
		}
		return msg;
	}

	public String getCatListByProdType() {
		this.proCatList = CommonDataFactory.getInstance().getCatsByTypeId(this.product_type);
		this.json = JSONArray.toJSONString(proCatList);
		return this.JSON_MESSAGE;
	}

	// 根据类型Id查询品牌列表
	public String getBrandListByTypeId() {
		List brand_List = new ArrayList();
		brand_List = CommonDataFactory.getInstance().getBrandByTypeId(this.product_type);
		return this.JSON_MESSAGE;
	}

	// 根据品牌Id查询型号列表
	public String getAccessoriesByBrandId() {
		List accessories_List = CommonDataFactory.getInstance().getModelByBrandId(this.brand_code);
		return this.JSON_MESSAGE;
	}

	// 获取省列表
	public String ProvList() {
		List provList = this.ordFlowManager.getProvList();
		this.json = JSONArray.toJSONString(provList);
		return this.JSON_MESSAGE;
	}

	// 根据省级Id获取市级列表
	public String getCityListByProvId() {// 根据省份获取市信息
		List cityList = this.ordFlowManager.getCityList(provinc_code);
		this.json = JSONArray.toJSONString(cityList);
		return this.JSON_MESSAGE;
	}

	// 根据城市Id获取区县
	public String getDistrictListByCityId() {
		List DistrictList = this.ordFlowManager.getDistrictList(city_code);
		this.json = JSONArray.toJSONString(DistrictList);
		return this.JSON_MESSAGE;
	}
	
	/**
	 * 支持重新写卡 
	 * @author GL
	 * @return
	 * @throws Exception
	 */
	public String writeCardRules() throws Exception {
		String resurlJson = null;
		boolean flag = false;
		
		String is_work_custom = CommonDataFactory.getInstance().
				getAttrFieldValue(order_id, AttrConsts.IS_WORK_CUSTOM);
		String iccid = CommonDataFactory.getInstance().
				getAttrFieldValue(order_id, AttrConsts.ICCID);
		
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String open = cacheUtil.getConfigInfo("WriteCardRulesSwitch");
		
		if(org.apache.commons.lang.StringUtils.isBlank(this.ic_Card)) {
			throw new Exception("传入的卡号为空！");
		}

		//open开关   is_work_custom开关   是否自定义流程 
		if ("1".equals(open) && "1".equals(is_work_custom) 
				&& !this.ic_Card.equals(iccid)) {
			// 获取当前环节
			String node_code = this.ordFlowManager.flowGetNodeCode(order_id);
			
			// 是否写卡环节
			if (!"wait_write_card_rules".equals(node_code)) {
				// 跳转环节
				String resultStr = this.workCustomEngine.gotoNode(order_id, 
						"wait_write_card_rules", "重新获取卡数据跳转");
				// 保存ICCID
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ICCID },
						new String[] { this.ic_Card });
				
				flag = true;
				resurlJson = "{'code':'3','msg':'环节跳转成功'}";
			}
		}

		if (!flag) {
			resurlJson = "{'code':'0','msg':'成功'}";
		}

		this.json = resurlJson;
		return this.JSON_MESSAGE;
	}

	// 获取写卡数据
	public String getWriteCardInfo() {
		try {
			// 保存ICCID到订单树
			String ICCID = this.ic_Card;
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			// 设置属性信息
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ICCID }, new String[] { ICCID });
			String iccid1 = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
			// 先删除方案日志
			RuleExeLogDelReq req = new RuleExeLogDelReq();
			req.setObj_id(this.order_id);
			req.setPlan_id(new String[] { EcsOrderConsts.RULE_PLAN_ID_103 });
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			client.execute(req, RuleExeLogDelResp.class);
			String order_from = orderTree.getOrderExtBusiRequest().getOrder_from();
			String order_model = orderTree.getOrderExtBusiRequest().getOrder_model();
			String is_aop = orderTree.getOrderExtBusiRequest().getIs_aop();
			String ruleid = "";
			CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String ydkhfrom = cacheUtil.getConfigInfo("ydkhfrom");
			
			if("1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())){
				//自定义流程获取写卡数据，直接获取写卡数据，不执行规则
                this.workCutomOrderContinue(EcsOrderConsts.DIC_ORDER_NODE_X);

			}else if (EcsOrderConsts.ORDER_MODEL_02.equals(order_model) 
					&& !order_from.equals("10070") 
					&& !order_from.equals("10012") 
					&& !ydkhfrom.contains(order_from)) {
				
				// 如果是人工集中模式就调规则 不是就调方案
				if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
					ruleid = EcsOrderConsts.ORDER_MODEL_02_ICCID_RULE_ID;
				} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
					ruleid = EcsOrderConsts.ORDER_MODEL_02_ICCID_RULE_ID_BSSKL;
				} else {
					ruleid = EcsOrderConsts.ORDER_MODEL_02_ICCID_RULE_ID_AOP;
				}
				// ruleid = (!EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_aop)) ?
				// EcsOrderConsts.ORDER_MODEL_02_ICCID_RULE_ID
				// : EcsOrderConsts.ORDER_MODEL_02_ICCID_RULE_ID_AOP;
				TacheFact fact = new TacheFact();
				fact.setOrder_id(orderTree.getOrder_id());
				RuleTreeExeResp resp = RuleFlowUtil.executeRuleTree(null, ruleid, fact, true, true, EcsOrderConsts.DEAL_FROM_PAGE);
			} else if (EcsOrderConsts.ORDER_MODEL_04.equals(order_model)) {
				if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
					ruleid = EcsOrderConsts.ORDER_MODEL_04_ICCID_RULE_ID;
				} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
					ruleid = EcsOrderConsts.ORDER_MODEL_04_ICCID_RULE_ID_BSSKL;
				} else {
					ruleid = EcsOrderConsts.ORDER_MODEL_04_ICCID_RULE_ID_AOP;
				}
				// ruleid = (!EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_aop)) ?
				// EcsOrderConsts.ORDER_MODEL_04_ICCID_RULE_ID
				// : EcsOrderConsts.ORDER_MODEL_04_ICCID_RULE_ID_AOP;
				TacheFact fact = new TacheFact();
				fact.setOrder_id(orderTree.getOrder_id());
				RuleTreeExeResp resp = RuleFlowUtil.executeRuleTree(null, ruleid, fact, true, true, EcsOrderConsts.DEAL_FROM_PAGE);
			} else if (EcsOrderConsts.ORDER_MODEL_03.equals(order_model)) {
				if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
					ruleid = EcsOrderConsts.ORDER_MODEL_03_ICCID_RULE_ID;
				} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
					ruleid = EcsOrderConsts.ORDER_MODEL_03_ICCID_RULE_ID_BSSKL;
				} else {
					ruleid = EcsOrderConsts.ORDER_MODEL_03_ICCID_RULE_ID_AOP;
				}
				// ruleid = (!EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_aop)) ?
				// EcsOrderConsts.ORDER_MODEL_03_ICCID_RULE_ID
				// : EcsOrderConsts.ORDER_MODEL_03_ICCID_RULE_ID_AOP;
				TacheFact fact = new TacheFact();
				fact.setOrder_id(orderTree.getOrder_id());
				RuleTreeExeResp resp = RuleFlowUtil.executeRuleTree(null, ruleid, fact, true, true, EcsOrderConsts.DEAL_FROM_PAGE);
			} else if (EcsOrderConsts.ORDER_MODEL_07.equals(order_model)) {// 爬虫生产模式
				ruleid = ZjEcsOrderConsts.ORDER_MODEL_07_ICCID_RULE_ID;
				TacheFact fact = new TacheFact();
				fact.setOrder_id(orderTree.getOrder_id());
				RuleTreeExeResp resp = RuleFlowUtil.executeRuleTree(null, ruleid, fact, true, true, EcsOrderConsts.DEAL_FROM_PAGE);
				logger.info("***************");
				if (!ConstsCore.ERROR_SUCC.equals(resp.getError_code())) {
					this.json = "{result:1,message:'获取ICCID失败:" + resp.getError_msg() + "'}";
					return this.JSON_MESSAGE;
				}
			} else if (order_from.equals("10070") || order_from.equals("10012")|| ((ydkhfrom.contains(order_from))&&EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop))) {// 日租卡业务不执行规则
			//} else if (order_from.equals("10070") || order_from.equals("10012")) {// 日租卡业务不执行规则
			} else {
				// 执行写卡方案
				BusiCompRequest busi = new BusiCompRequest();
				Map queryParams = new HashMap();
				queryParams.put("order_id", this.order_id);
				queryParams.put("is_auto", EcsOrderConsts.RULE_EXE_ALL);
				busi.setEnginePath("enterTraceRule.exec");
				busi.setOrder_id(this.order_id);
				queryParams.put("deal_from", EcsOrderConsts.DEAL_FROM_PAGE);
				busi.setQueryParams(queryParams);
				try {
					ZteResponse response = orderServices.execBusiComp(busi);
				} catch (Exception e) {
					e.printStackTrace();
					// add zhangjun 不再标记异常
					/*
					 * BusiCompRequest busiComReq = new BusiCompRequest(); Map
					 * params = new HashMap();
					 * params.put(EcsOrderConsts.EXCEPTION_FROM,
					 * EcsOrderConsts.EXCEPTION_FROM_ORD);
					 * params.put("order_id", order_id); busiComReq
					 * .setEnginePath("zteCommonTraceRule.markedException");
					 * busiComReq.setOrder_id(order_id);
					 * busiComReq.setQueryParams(params);
					 * orderServices.execBusiComp(busiComReq);
					 */
					this.json = "{result:1,message:'获取写卡数据失败 :" + e.getMessage() + "'}";
					return this.JSON_MESSAGE;
				}
			}
			Map cardInfo = new HashMap();
			String iccid = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
			String script_seq = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SCRIPT_SEQ);
			String ismi = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SIMID);
			if (!StringUtils.isEmpty(iccid) && !StringUtils.isEmpty(script_seq) && !StringUtils.isEmpty(ismi)) {
				cardInfo.put("iccid", iccid);
				cardInfo.put("script_seq", script_seq);
				cardInfo.put("ismi", ismi);
				String jsonStr = JSONArray.toJSONString(cardInfo);
				this.json = "{result:0,message:'操作成功',cardInfo:" + jsonStr + "}";
			} else {
				this.json = "{result:1,message:'获取写卡数据失败'}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.json = "{result:1,message:'" + e.getMessage() + "'}";
		}
		return this.JSON_MESSAGE;
	}

	// /*保存写卡结果到订单树*/
	public String saveWriteCardResult() {
		long start = System.currentTimeMillis();
		try {
			String result = "";
			if ("0".equals(this.writeCardResult)) {
				result = EcsOrderConsts.WRITE_CARD_RESULT_SUCC;
			} else {
				result = EcsOrderConsts.WRITE_CARD_RESULT_FAIL;
			}
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.WRITE_CARD_RESULT }, new String[] { result });
			String str = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.WRITE_CARD_RESULT);
			this.json = "{result:0,message:'操作成功'}";
			logger.info("writeCardInfo===2==" + order_id + "=====" + (System.currentTimeMillis() - start));
		} catch (Exception e) {
			e.printStackTrace();
			this.json = "{result:1,message:'" + e.getMessage() + "'}";
			logger.info("writeCardInfo===2==" + order_id + "=====" + (System.currentTimeMillis() - start));
		}
		return this.JSON_MESSAGE;
	}
	
	/**
	 * 执行“等待写卡成功”环节
	 * @throws Exception
	 */
	private void runWriteCardSuccessNode() throws Exception{
		WORK_CUSTOM_FLOW_DATA flowData = this.workCustomEngine.runNodeManualByCode(this.order_id, 
				"wait_write_card_success", true, "", "", Const.WRITE_CARD_SUCCESS);
		
		//执行失败的抛出异常
		if(ConstsCore.ERROR_FAIL.equals(flowData.getRun_result()))
			throw new Exception(flowData.getRun_msg());
	}

	// 通知总部写卡结果
	public String getResultToZB() {
		long start = System.currentTimeMillis();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		TacheFact fact = new TacheFact();
		fact.setOrder_id(this.order_id);
		try {
			String order_model = orderTree.getOrderExtBusiRequest().getOrder_model();
			String order_from = orderTree.getOrderExtBusiRequest().getOrder_from();
			String is_aop = orderTree.getOrderExtBusiRequest().getIs_aop();
			String ruleid = "";
			CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String ydkhfrom = cacheUtil.getConfigInfo("ydkhfrom");
			
			
			if("1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())){
				//自定义流程
				this.runWriteCardSuccessNode();
				
				this.json = "{result:0,message:'操作成功'}";
			}else{
				if (order_from.equals("10070")||(ydkhfrom.contains(order_from)&&EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop))) {// 日租卡业务执行写卡方案
					// 执行写卡方案
					RuleTreeExeReq requ = new RuleTreeExeReq();
					requ.setFact(fact);
					requ.setRule_id(EcsOrderConsts.BUSI_CARD_MASHANGGOU_RULE_ID);
					requ.setCheckAllRelyOnRule(true);
					requ.setCheckCurrRelyOnRule(true);
					ZteClient client1 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
					try {
						RuleTreeExeResp ruleResp = client1.execute(requ, RuleTreeExeResp.class);
						// 如果规则调用失败
						if (!ConstsCore.ERROR_SUCC.equals(ruleResp.getError_code())) {
							logger.info("卡数据同步规则调用失败：" + ruleResp.getError_msg());
							this.json = "{result:1,message:'操作失败'}";
							return this.JSON_MESSAGE;
						} else {
							this.json = "{result:0,message:'操作成功'}";
							return this.JSON_MESSAGE;
						}
					} catch (Exception e) {
						e.printStackTrace();
						// add zhangjun 不再标记异常
						/*
						 * BusiCompRequest busiComReq = new BusiCompRequest(); Map
						 * params = new HashMap();
						 * params.put(EcsOrderConsts.EXCEPTION_FROM,
						 * EcsOrderConsts.EXCEPTION_FROM_ORD);
						 * params.put("order_id", order_id); busiComReq
						 * .setEnginePath("zteCommonTraceRule.markedException");
						 * busiComReq.setOrder_id(order_id);
						 * busiComReq.setQueryParams(params);
						 * orderServices.execBusiComp(busiComReq);
						 */
						this.json = "{result:1,message:'操作失败'}";
						return this.JSON_MESSAGE;
					}
				}
				if (EcsOrderConsts.ORDER_MODEL_07.equals(order_model)) {// 爬虫生产模式
					ruleid = ZjEcsOrderConsts.WRITE_CARD_INFO_TO_ZB_07;
				} else {
					if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
						ruleid = EcsOrderConsts.WRITE_CARD_INFO_TO_ZB_02;
						if (EcsOrderConsts.ORDER_MODEL_04.equals(order_model)) {
							ruleid = EcsOrderConsts.WRITE_CARD_INFO_TO_ZB_04;
						}
					} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
						ruleid = EcsOrderConsts.WRITE_CARD_INFO_TO_ZB_02_BSSKL;
						if (EcsOrderConsts.ORDER_MODEL_04.equals(order_model)) {
							ruleid = EcsOrderConsts.WRITE_CARD_INFO_TO_ZB_04_BSSKL;
						}
					} else {
						ruleid = EcsOrderConsts.WRITE_CARD_INFO_TO_ZB_02_AOP;
						if (EcsOrderConsts.ORDER_MODEL_04.equals(order_model)) {
							ruleid = EcsOrderConsts.WRITE_CARD_INFO_TO_ZB_04_AOP;
						}
					}
				}

				RuleTreeExeResp resp = RuleFlowUtil.executeRuleTree(EcsOrderConsts.RULE_PLAN_ID_103, ruleid, fact, true, true, EcsOrderConsts.DEAL_FROM_PAGE);
				if (resp != null) {
					if (ConstsCore.ERROR_SUCC.equals(resp.getError_code())) {
						String rst = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.WRITE_CARD_STATUS);

						if (EcsOrderConsts.WRITE_CARD_STATUS_5.equals(rst)) {
							String trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
							this.json = "{result:0,message:'操作成功'}";
						} else {
							this.json = "{result:1,message:'操作失败'}";
						}
					} else {
						this.json = "{result:1,message:'操作失败:" + resp.getError_msg() + "'}";
					}
				} else {
					this.json = "{result:1,message:'操作失败'}";
				}
				logger.info("writeCardInfo===3==" + order_id + "=====" + (System.currentTimeMillis() - start));
				/*
				 * BusiCompRequest busi = new BusiCompRequest(); Map queryParams =
				 * new HashMap(); queryParams.put("order_id", this.order_id);
				 * queryParams.put("is_auto", EcsOrderConsts.RULE_EXE_ALL);
				 * busi.setEnginePath("enterTraceRule.exec");
				 * queryParams.put("deal_from",EcsOrderConsts.DEAL_FROM_PAGE);
				 * busi.setOrder_id(this.order_id);
				 * busi.setQueryParams(queryParams);
				 * 
				 * ZteResponse response = orderServices.execBusiComp(busi);
				 */
			}
		} catch (Exception e) {
			logger.info("写卡通知总部失败=================" + order_id);
			e.printStackTrace();
			// add zhangju 不再标记异常
			/*
			 * BusiCompRequest busiComReq = new BusiCompRequest(); Map params =
			 * new HashMap();
			 * params.put(EcsOrderConsts.EXCEPTION_FROM,EcsOrderConsts.
			 * EXCEPTION_FROM_ORD); params.put("order_id", order_id);
			 * busiComReq.setEnginePath("zteCommonTraceRule.markedException");
			 * busiComReq.setOrder_id(order_id);
			 * busiComReq.setQueryParams(params); try {
			 * orderServices.execBusiComp(busiComReq); } catch (Exception e1) {
			 * e1.printStackTrace(); }
			 */
			this.json = "{result:1,message:'" + e.getMessage() + "'}";
			return this.JSON_MESSAGE;
		}

		return this.JSON_MESSAGE;
	}

	// 写卡下一步方法 直接跳过
	@SuppressWarnings("unchecked")
	public String writeCardNext() {
		final String lock_flow_deal = LOCK_ORDER_FLOW_DEAL + order_id;
		// 设置缓存锁
		boolean lock_flag = cache.add(NAMESPACE, lock_flow_deal, order_id, 300);
		if (!lock_flag) {
			// 已有缓存锁，有进程正在处理料箱队列
			this.json = "{result:1,message:'订单正在处理,请等待!'}";
			return this.JSON_MESSAGE;
		}
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String abnormal_type = orderTree.getOrderExtBusiRequest().getAbnormal_type();
		String cur_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		String order_model = orderTree.getOrderExtBusiRequest().getOrder_model();
		if (!EcsOrderConsts.OPER_MODE_DL.equals(order_model) && !EcsOrderConsts.WRITE_CARD_ZB_SUCC.equals(orderTree.getOrderExtBusiRequest().getCol3())
				&& abnormal_type.equals(EcsOrderConsts.ORDER_ABNORMAL_TYPE_0)) {
			this.json = "{result:1,message:'请先标记异常再点击写卡完成！'}";
			cache.delete(NAMESPACE, lock_flow_deal);
			return this.JSON_MESSAGE;
		}
		// 如果异常没有恢复，则先恢复异常
		if (!StringUtil.isEmpty(abnormal_type) && !abnormal_type.equals(EcsOrderConsts.ORDER_ABNORMAL_TYPE_0)) {
			// 恢复订单异常
			OrderExtBusiRequest orderExtBusiReq = orderTree.getOrderExtBusiRequest();
			BusiCompRequest busi = new BusiCompRequest();
			Map queryParams = new HashMap();
			queryParams.put("order_id", order_id);
			queryParams.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			queryParams.put(EcsOrderConsts.EXCEPTION_TYPE, orderExtBusiReq.getException_type());
			queryParams.put(EcsOrderConsts.EXCEPTION_REMARK, orderExtBusiReq.getException_desc());
			busi.setEnginePath("zteCommonTraceRule.restorationException");
			busi.setOrder_id(order_id);
			busi.setQueryParams(queryParams);
			try {
				ZteResponse response = orderServices.execBusiComp(busi);
				if (!EcsOrderConsts.RULE_EXE_FLAG_SUCC.equals(response.getError_code())) {
					CommonTools.addBusiError(response.getError_code(), response.getError_msg());
				}
			} catch (ApiBusiException busiE) {
				this.json = "{result:1,message:'操作失败：' " + busiE.getMessage() + "}";
				cache.delete(NAMESPACE, lock_flow_deal);
				return this.JSON_MESSAGE;
			} catch (Exception e) {
				this.json = "{result:1,message:'操作失败：异常恢复失败'}";
				cache.delete(NAMESPACE, lock_flow_deal);
				return this.JSON_MESSAGE;
			}
		}
		
		String trace_id = "";
		
		try{
			if("1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())){
				//自定义流程
				this.workCutomOrderContinue(EcsOrderConsts.DIC_ORDER_NODE_X);
				
				//下一环节页面
				String action_url = this.getWorkCustomUrl();
				
				//更新开户时间
				String[] field_name = new String[] { "bss_account_time" };
				String[] field_value = null;
				
				try {
					field_value = new String[] { DateUtil.getTime2() };
				} catch (FrameException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, field_name, field_value);
				
				this.json = "{result:0,message:'操作成功',action_url:'" + action_url + "'}";
			}else{
				CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				
				TacheFact fact = new TacheFact();
				fact.setOrder_id(this.order_id);
				String ruleid = EcsOrderConsts.ORDER_WRITE_CARD_RULE;
				String isAop = orderTree.getOrderExtBusiRequest().getIs_aop();
				if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(isAop)) {
					ruleid = EcsOrderConsts.WRITE_CARD_INFO_TO_ZB_02_BSSKL;
					if (EcsOrderConsts.ORDER_MODEL_04.equals(order_model)) {
						ruleid = EcsOrderConsts.WRITE_CARD_INFO_TO_ZB_04_BSSKL;
					}
					if (EcsOrderConsts.ORDER_MODEL_02.equals(order_model)) {//省内副卡
						ruleid = cacheUtil.getConfigInfo("RULE_WRITE_CARD_NEXT");
					}
				} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP.equals(isAop)) {
					ruleid = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_02_AOP;// 人工集中生产模式
					if (EcsOrderConsts.ORDER_MODEL_04.equals(order_model)) {
						ruleid = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_04_AOP;
					} else if (EcsOrderConsts.ORDER_MODEL_06.equals(order_model)) {
						ruleid = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_06_AOP;
					} else if (EcsOrderConsts.ORDER_MODEL_05.equals(order_model)) {
						ruleid = EcsOrderConsts.ORDER_WRITE_CARD_NEXT_RULE_05_AOP;
					}
				}
				RuleTreeExeResp resp = RuleFlowUtil.executeRuleTree(EcsOrderConsts.RULE_PLAN_ID_103, ruleid, fact, false, false, EcsOrderConsts.DEAL_FROM_PAGE);
				
				if (resp != null && "0".equals(resp.getError_code())) {
					trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
					
					String action_url = getActionUrl(trace_id, this.order_id);
					this.json = "{result:0,message:'操作成功',action_url:'" + action_url + "'}";
					String[] field_name = new String[] { "bss_account_time" };
					String[] field_value = null;
					
					try {
						field_value = new String[] { DateUtil.getTime2() };
					} catch (FrameException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, field_name, field_value);
				} else {
					this.json = "{result:1,message:'操作失败：'" + resp.getError_msg() + "}";
				}
			}
		}catch(Exception e){
			this.json = "{result:1,message:'操作失败：'" + e.getMessage() + "}";
		}finally{
			trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
			
			Utils.commonUnlock(order_id, cur_trace_id, trace_id); // 解锁公共方法
		}

		return this.JSON_MESSAGE;
	}

	/**
	 * 线下办理【开户失败转异常，登录BSS开户后，订单直接调下一步规则流转环节】
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String offlineHandleNext() {
		final String lock_flow_deal = LOCK_ORDER_FLOW_DEAL + order_id;
		// 设置缓存锁
		boolean lock_flag = cache.add(NAMESPACE, lock_flow_deal, order_id, 300);
		if (!lock_flag) {
			// 已有缓存锁，有进程正在处理料箱队列
			this.json = "{result:1,message:'订单正在处理,请等待!'}";
			return this.JSON_MESSAGE;
		}
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String abnormal_type = orderTree.getOrderExtBusiRequest().getAbnormal_type();
		String cur_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		String order_model = orderTree.getOrderExtBusiRequest().getOrder_model();
		if (EcsOrderConsts.OPER_MODE_RG.equals(order_model) && !StringUtil.isEmpty(abnormal_type) && abnormal_type.equals(EcsOrderConsts.ORDER_ABNORMAL_TYPE_0)) {
			this.json = "{result:1,message:'请先标记异常再点击线下办理！'}";
			cache.delete(NAMESPACE, lock_flow_deal);
			return this.JSON_MESSAGE;
		}
		OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();

		// 退单订单不允许回退，防止回退后删除退单规则执行日志
		if (EcsOrderConsts.REFUND_STATUS_08.equals(orderExt.getRefund_status())// 退单申请
				|| EcsOrderConsts.REFUND_STATUS_01.equals(orderExt.getRefund_status())// 退单确认通过
				|| EcsOrderConsts.REFUND_STATUS_02.equals(orderExt.getRefund_status())// BSS返销
				|| EcsOrderConsts.REFUND_STATUS_03.equals(orderExt.getRefund_status())) {// ESS返销
			this.json = "{result:1,message:'订单已退单或退单申请，不能点击线下办理！'}";
			cache.delete(NAMESPACE, lock_flow_deal);
			return this.JSON_MESSAGE;
		}

		String activeNo = "";
		List<AttrInstBusiRequest> pageAttrInstBusiRequests = AttrBusiInstTools.getPageDirtyOrderAttrInst(order_id, orderTree);
		if (pageAttrInstBusiRequests != null && pageAttrInstBusiRequests.size() > 0) {
			for (AttrInstBusiRequest pageAttrInstBusiRequest : pageAttrInstBusiRequests) {
				// 获取页面开户流水号值
				if (AttrConsts.ACTIVE_NO.equals(pageAttrInstBusiRequest.getField_name())) {
					activeNo = pageAttrInstBusiRequest.getField_value();
					break;
				}
			}
			if (StringUtil.isEmpty(activeNo)) {// 开户流水号
				this.json = "{result:1,message:'请输入开户流水号！'}";
				cache.delete(NAMESPACE, lock_flow_deal);
				return this.JSON_MESSAGE;
			}
		}
		TacheFact fact = new TacheFact();
		fact.setOrder_id(this.order_id);
		// 单宽带开户环节下一步规则ID
		String ruleid = EcsOrderConsts.OPEN_ACCOUNT_NEXT_RULE_ID.get(EcsOrderConsts.OPER_MODE_RG + "_DKD");
		RuleTreeExeResp resp = RuleFlowUtil.executeRuleTree(EcsOrderConsts.OPEN_ACCOUNT_PLAN_ID, ruleid, fact, false, false, EcsOrderConsts.DEAL_FROM_PAGE);

		if (resp != null && "0".equals(resp.getError_code())) {
			// 如果异常没有恢复，则先恢复异常
			if (!StringUtil.isEmpty(abnormal_type) && !abnormal_type.equals(EcsOrderConsts.ORDER_ABNORMAL_TYPE_0)) {
				// 恢复订单异常
				OrderExtBusiRequest orderExtBusiReq = orderTree.getOrderExtBusiRequest();
				BusiCompRequest busi = new BusiCompRequest();
				Map queryParams = new HashMap();
				queryParams.put("order_id", order_id);
				queryParams.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
				queryParams.put(EcsOrderConsts.EXCEPTION_TYPE, orderExtBusiReq.getException_type());
				queryParams.put(EcsOrderConsts.EXCEPTION_REMARK, orderExtBusiReq.getException_desc());
				busi.setEnginePath("zteCommonTraceRule.restorationException");
				busi.setOrder_id(order_id);
				busi.setQueryParams(queryParams);
				try {
					ZteResponse response = orderServices.execBusiComp(busi);
					if (!EcsOrderConsts.RULE_EXE_FLAG_SUCC.equals(response.getError_code())) {
						CommonTools.addBusiError(response.getError_code(), response.getError_msg());
					}
				} catch (ApiBusiException busiE) {
					this.json = "{result:1,message:'操作失败：' " + busiE.getMessage() + "}";
					cache.delete(NAMESPACE, lock_flow_deal);
					return this.JSON_MESSAGE;
				} catch (Exception e) {
					this.json = "{result:1,message:'操作失败：异常恢复失败'}";
					cache.delete(NAMESPACE, lock_flow_deal);
					return this.JSON_MESSAGE;
				}
			}
			String bssAccountTime = "";
			try {
				bssAccountTime = DateUtil.getTime2();
			} catch (FrameException e1) {
				e1.printStackTrace();
			}

			// 保存开户流水号
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACTIVE_NO }, new String[] { activeNo });
			// 更新各种开户状态和结果
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACCOUNT_STATUS, AttrConsts.BSS_ACCOUNT_TIME },
					new String[] { EcsOrderConsts.ACCOUNT_STATUS_1, bssAccountTime });
			// 更新订单状态为已竣工
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_STATUS }, new String[] { ZjEcsOrderConsts.BSS_STATUS_2 });

			String trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
			String action_url = getActionUrl(trace_id, this.order_id);
			this.json = "{result:0,message:'操作成功',action_url:'" + action_url + "'}";
			Utils.commonUnlock(order_id, cur_trace_id, trace_id); // 解锁公共方法
		} else {
			this.json = "{result:1,message:'操作失败：'" + resp.getError_msg() + "}";
		}
		return this.JSON_MESSAGE;
	}

	public String cardInfoGet() {
		logger.info(order_id + ":卡信息获取规则调用开始");
		// 调用规则
		CardInfoGetAPPReq req = new CardInfoGetAPPReq();
		RuleTreeExeReq requ = new RuleTreeExeReq();
		TacheFact fact = new TacheFact();
		fact.setOrder_id(req.getOrder_id());
		ThreadLocalUtil.setProperty(req.getOrder_id() + "_region_id", req.getRegion_id());
		requ.setFact(fact);
		requ.setRule_id(EcsOrderConsts.BUSI_CARD_BSS_RULE_ID);
		requ.setCheckAllRelyOnRule(true);
		requ.setCheckCurrRelyOnRule(true);
		ZteClient client1 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		RuleTreeExeResp ruleResp = client1.execute(requ, RuleTreeExeResp.class);
		// 如果开户预提交规则调用失败
		if (!ConstsCore.ERROR_SUCC.equals(ruleResp.getError_code())) {
			logger.info("卡信息获取规则调用失败：" + ruleResp.getError_msg());
			this.json = "{result:1,message:'操作失败：" + ruleResp.getError_msg() + "'}";
		} else {
			CardInfoGetBSSResp cardInfoGetBSSResp = (com.ztesoft.net.ecsord.params.ecaop.resp.CardInfoGetBSSResp) ThreadLocalUtil.getProperty(order_id);
			ThreadLocalUtil.removeProperty(order_id);
			this.json = "{result:0,message:'操作成功'}";
		}

		return this.JSON_MESSAGE;
	}

	public String orderPay() {
		final String lock_flow_deal = LOCK_ORDER_FLOW_DEAL + order_id;
		// 设置缓存锁
		boolean lock_flag = cache.add(NAMESPACE, lock_flow_deal, order_id, 300);
		if (!lock_flag) {
			// 已有缓存锁，有进程正在处理料箱队列
			this.json = "{result:1,message:'订单正在处理,请等待!'}";
			return this.JSON_MESSAGE;
		}
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String abnormal_type = orderTree.getOrderExtBusiRequest().getAbnormal_type();
		String cur_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		String order_model = orderTree.getOrderExtBusiRequest().getOrder_model();
		if (EcsOrderConsts.OPER_MODE_RG.equals(order_model) && !StringUtil.isEmpty(abnormal_type) && abnormal_type.equals(EcsOrderConsts.ORDER_ABNORMAL_TYPE_0)) {
			this.json = "{result:1,message:'正常单不能进行此操作，请等待支付结果通知！'}";
			cache.delete(NAMESPACE, lock_flow_deal);
			return this.JSON_MESSAGE;
		}
		OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
		// 退单订单不允许回退，防止回退后删除退单规则执行日志
		if (EcsOrderConsts.REFUND_STATUS_08.equals(orderExt.getRefund_status())// 退单申请
				|| EcsOrderConsts.REFUND_STATUS_01.equals(orderExt.getRefund_status())// 退单确认通过
				|| EcsOrderConsts.REFUND_STATUS_02.equals(orderExt.getRefund_status())// BSS返销
				|| EcsOrderConsts.REFUND_STATUS_03.equals(orderExt.getRefund_status())) {// ESS返销
			this.json = "{result:1,message:'订单已退单或退单申请，不能点击订单支付！'}";
			cache.delete(NAMESPACE, lock_flow_deal);
			return this.JSON_MESSAGE;
		}

		String planId = CommonDataFactory.getInstance().getPlanIdOfTache(EcsOrderConsts.DIC_ORDER_NODE_P);
		if (StringUtils.isEmpty(planId)) {
			this.json = "{result:1,message:'操作失败：订单支付环节未配置其方案ID！'}";
			cache.delete(NAMESPACE, lock_flow_deal);
			return this.JSON_MESSAGE;
		}
		PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
		TacheFact fact = new TacheFact();
		fact.setOrder_id(this.order_id);
		req.setPlan_id(planId);
		req.setFact(fact);
		req.setAuto_exe(-1);
		req.setDeleteLogs(false);// 不删除日志，不允许二次操作
		PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
		BusiCompResponse resp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);

		if (resp != null && "0".equals(resp.getError_code())) {
			// 如果异常没有恢复，则先恢复异常
			if (!StringUtil.isEmpty(abnormal_type) && !abnormal_type.equals(EcsOrderConsts.ORDER_ABNORMAL_TYPE_0)) {
				// 恢复订单异常
				OrderExtBusiRequest orderExtBusiReq = orderTree.getOrderExtBusiRequest();
				BusiCompRequest busi = new BusiCompRequest();
				Map queryParams = new HashMap();
				queryParams.put("order_id", order_id);
				queryParams.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
				queryParams.put(EcsOrderConsts.EXCEPTION_TYPE, orderExtBusiReq.getException_type());
				queryParams.put(EcsOrderConsts.EXCEPTION_REMARK, orderExtBusiReq.getException_desc());
				busi.setEnginePath("zteCommonTraceRule.restorationException");
				busi.setOrder_id(order_id);
				busi.setQueryParams(queryParams);
				try {
					ZteResponse response = orderServices.execBusiComp(busi);
					if (!EcsOrderConsts.RULE_EXE_FLAG_SUCC.equals(response.getError_code())) {
						CommonTools.addBusiError(response.getError_code(), response.getError_msg());
					}
				} catch (ApiBusiException busiE) {
					this.json = "{result:1,message:'操作失败：' " + busiE.getMessage() + "}";
					cache.delete(NAMESPACE, lock_flow_deal);
					return this.JSON_MESSAGE;
				} catch (Exception e) {
					this.json = "{result:1,message:'操作失败：异常恢复失败'}";
					cache.delete(NAMESPACE, lock_flow_deal);
					return this.JSON_MESSAGE;
				}
			}
			String trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
			String action_url = getActionUrl(trace_id, this.order_id);
			this.json = "{result:0,message:'操作成功',action_url:'" + action_url + "'}";
			Utils.commonUnlock(order_id, cur_trace_id, trace_id); // 解锁公共方法
		} else {
			this.json = "{result:1,message:'操作失败：'" + resp.getError_msg() + "}";
		}
		return this.JSON_MESSAGE;
	}

	/* 将闪电送改成非闪电送 然后处理人和处理状态改为空 将订单改成未锁定 */
	public String changeQuickShipStatus() {
		try {
			CommonDataFactory.getInstance().updateAttrFieldValue(this.order_id, new String[] { AttrConsts.SHIPPING_QUICK, AttrConsts.LOCK_USER_ID, AttrConsts.LOCK_STATUS },
					new String[] { EcsOrderConsts.SHIPPING_QUICK_02, "", EcsOrderConsts.UNLOCK_STATUS });

			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			String action_url = this.getActionUrl(orderTree.getOrderExtBusiRequest().getFlow_trace_id(), order_id);

			this.json = "{result:0,message:'操作成功',action_url:'" + action_url + "'}";
		} catch (Exception e) {
			e.printStackTrace();
			String errMsg = e.getMessage();
			this.json = "{result:1,message:'" + errMsg + "'}";
		}
		return this.JSON_MESSAGE;
	}

	/* 预校验 (身份证和国政通校验) */
	public String preValidate() {
		try {
			// 配送方式是现场交付

			/*
			 * BusiCompRequest busi = new BusiCompRequest();
			 * busi.setOrder_id(order_id);
			 * busi.setEnginePath("ZteCustomVisitTraceRule.preCheckToZb");
			 * BusiCompResponse response = orderServices.execBusiComp(busi);
			 */

			String lockMsg = checkLockUser();
			if (!StringUtils.isEmpty(lockMsg)) {
				this.json = "{result:1,message:'" + lockMsg + "'}";
				return this.JSON_MESSAGE;
			}
			String send_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SENDING_TYPE);
			if (EcsOrderConsts.SHIPPING_TYPE_XJ.equals(send_type)) {
				// 成功设置预校验成功状态
				String idCardStatus = EcsOrderConsts.ACCOUNT_VALI_1;// 身份证校验成功状态
				CommonDataFactory.getInstance().updateAttrFieldValue(this.order_id, new String[] { AttrConsts.ACCOUNT_VALI }, new String[] { idCardStatus });

			}
			String msg = this.editSave();
			if (msg.length() > 0) {
				this.json = "{result:1,message:'预校验失败：保存页面信息失败," + msg + "'}";
				return this.JSON_MESSAGE;
			}
			/*
			 * update by shusx 换成调方案的方式 RuleTreeExeReq req = new
			 * RuleTreeExeReq(); TacheFact fact = new TacheFact();
			 * fact.setOrder_id(order_id);
			 * req.setRule_id(EcsOrderConsts.ORDER_PRE_COMMON_RULE);
			 * req.setFact(fact); RuleTreeExeResp resp = null; try{ resp =
			 * CommonDataFactory.getInstance().exeRuleTree(req);
			 * }catch(Exception e){ e.printStackTrace(); this.json
			 * ="{result:1,message:'预校验失败："+e.getMessage()+"'}"; }
			 */
			String is_aop = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_AOP);
			PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
			TacheFact fact = new TacheFact();
			fact.setOrder_id(order_id);
			if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
				req.setPlan_id(EcsOrderConsts.ORDER_PRE_VALIDATE);
			} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
				req.setPlan_id(EcsOrderConsts.ORDER_PRE_VALIDATE_BSS);
			} else {
				req.setPlan_id(EcsOrderConsts.ORDER_PRE_VALIDATE_AOP);
			}
			req.setFact(fact);
			BusiCompResponse resp = null;
			try {
				PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
				resp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
			} catch (Exception e) {
				e.printStackTrace();
				this.json = "{result:1,message:'预校验失败：" + e.getMessage() + "'}";
			}
			if (!resp.getError_code().equals(ConstsCore.ERROR_SUCC + "")) {
				this.json = "{result:1,message:'预校验失败：" + resp.getError_msg() + "'}";
			} else {
				// 成功设置预校验成功状态
				String idCardStatus = EcsOrderConsts.ACCOUNT_VALI_1;// 身份证校验成功状态
				CommonDataFactory.getInstance().updateAttrFieldValue(this.order_id, new String[] { AttrConsts.ACCOUNT_VALI }, new String[] { idCardStatus });

				// ESS校验成功状态设置
				String essPreStatus = EcsOrderConsts.ESS_PRE_STATUS_1;
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				OrderItemExtBusiRequest orderItemExtUpdate = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest();

				orderItemExtUpdate.setEss_pre_status(essPreStatus);
				orderItemExtUpdate.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderItemExtUpdate.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderItemExtUpdate.store(); // 属性数据入库

				String trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
				String action_url = getActionUrl(trace_id, this.order_id);
				this.json = "{result:0,message:'预校验成功',action_url:'" + action_url + "'}";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.json = "{result:1,message:预检验失败：'" + e.getMessage() + "'}";
		}
		return this.JSON_MESSAGE;
	}

	/* 单独校验省份证 */
	public String validateIdCard() {

		String lockMsg = checkLockUser();
		if (!StringUtils.isEmpty(lockMsg)) {
			this.json = "{result:1,message:'" + lockMsg + "'}";
			return this.JSON_MESSAGE;
		}
		String send_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SENDING_TYPE);
		if (EcsOrderConsts.SHIPPING_TYPE_XJ.equals(send_type)) {
			this.json = "{result:1,message:'现场交付订单不需要做身份证预校验'}";
			return this.JSON_MESSAGE;

		}
		String is_examine_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_EXAMINE_CARD);
		if ("0".equals(is_examine_card)) {
			this.json = "{result:1,message:'已校验身份证订单不需要重复校验'}";
			return this.JSON_MESSAGE;
		}
		String account_vali = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACCOUNT_VALI);
		if (EcsOrderConsts.ACCOUNT_VALI_3.equals(account_vali)) {
			this.json = "{result:1,message:'开户人证件照已审核通过，不需要重复校验'}";
			return this.JSON_MESSAGE;
		}
		// 清空校验信息
		CommonDataFactory.getInstance().updateAttrFieldValue(this.order_id, new String[] { AttrConsts.ACCOUNT_VALI }, new String[] { "" });
		PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
		plan.setPlan_id(EcsOrderConsts.VALIDATE_CARD_PLAN);
		TacheFact fact = new TacheFact();
		fact.setOrder_id(order_id);
		plan.setFact(fact);
		plan.setDeleteLogs(true);
		PlanRuleTreeExeResp resp = CommonDataFactory.getInstance().exePlanRuleTree(plan);
		resp.getError_code();
		// BusiDealResult certi_valide =
		// ordVisitTacheManager.certiValide(order_id);
		if (!resp.getError_code().equals(ConstsCore.ERROR_SUCC)) {
			String msg = "身份证预校验失败:" + resp.getError_msg();
			this.json = "{result:1,message:'" + msg + "'}";
		} else {
			String status = EcsOrderConsts.ACCOUNT_VALI_1;// 身份证校验成功状态
			CommonDataFactory.getInstance().updateAttrFieldValue(this.order_id, new String[] { AttrConsts.ACCOUNT_VALI }, new String[] { status });

			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			String out_tid = orderTree.getOrderExtBusiRequest().getOut_tid();
			if (out_tid.startsWith("WCSV2")) {// 不再检验身份证
				this.json = "{result:1,message:'身份证预校验成功，证件照无需校验。'}";
			} else {
				String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
				// String action_url = getActionUrl(trace_id,this.order_id);
				AutoFact autoFact = resp.getFact();
				Map<String, ZteResponse> respMap = autoFact.getResponses();
				String photo = "";
				Set<String> key = respMap.keySet();
				CheckIDECAOPResponse certResp = new CheckIDECAOPResponse();
				for (Iterator it = key.iterator(); it.hasNext();) {
					BusiCompResponse busiResp = (BusiCompResponse) respMap.get(it.next());
					if (busiResp.getRule_id().equals(EcsOrderConsts.ACCOUNT_CERT_VALI_RULE))
						;
					{
						certResp = (CheckIDECAOPResponse) busiResp.getResponse();
						break;
					}
				}
				if (null != certResp) {
					photo = certResp.getPhoto();
					try {
						// and zhangjun 不再检验图片
						// List<String> picUrls = getCradPicPath(order_id);
						// String picjson = JsonUtil.toJson(picUrls);
						certResp.setAddr(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS));
						String certInfoJson = JsonUtil.toJson(certResp);
						// add zhangjun
						/*
						 * this.json =
						 * "{result:0,message:'身份证预校验成功，请审核证件照是否一致。',photo:'" +
						 * photo + "',picurls:" + picjson + ",certinfo:" +
						 * certInfoJson + "}";
						 */
						this.json = "{result:0,message:'身份证预校验成功。',photo:''" + ",certinfo:" + certInfoJson + "}";
					} catch (Exception e) {
						this.json = "{result:-1,message:'" + e.getMessage() + "'}";
					}
				} else {
					this.json = "{result:-1,message:'无须校验身份证。'}";
				}
			}

		}
		return this.JSON_MESSAGE;
	}

	public void getRemoteImg() throws Exception {
		String enString = getRequest().getParameter("sec");
		String url = EcsOrderConsts.PHOTO_HTTPS_PATH + "?sec=" + enString;
		InputStream imgIs = WebUtils.getRemoteImg(url);
		OutputStream out = null;
		try {
			out = getResponse().getOutputStream();
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().setContentType("image/jpg");
			int len = 0;
			byte[] b = new byte[1024];
			while ((len = imgIs.read(b, 0, b.length)) != -1) {
				out.write(b, 0, len);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	private List<String> getCradPicPath(String orderId) throws Exception {
		List<String> response = new ArrayList<String>();
		// try{
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
		String out_tid = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID);
		boolean isTbOrder = CommonDataFactory.getInstance().isTbOrder(order_from);// 是否淘宝订单,默认否
		if (EcsOrderConsts.ORDER_FROM_10003.equals(order_from)) {// 总部订单
			System.out.print("=====================================" + order_id + "证件查询开始");
			PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
			plan.setPlan_id(EcsOrderConsts.GET_CERT_PHOTO_FROM_ZB);
			TacheFact fact = new TacheFact();
			fact.setOrder_id(order_id);
			plan.setFact(fact);
			plan.setDeleteLogs(true);
			PlanRuleTreeExeResp resp = CommonDataFactory.getInstance().exePlanRuleTree(plan);
			System.out.print("=====================================" + order_id + "证件查询结束1");
			if (resp.getError_code().equals(ConstsCore.ERROR_SUCC)) {
				AutoFact autoFact = resp.getFact();
				Map<String, ZteResponse> respMap = autoFact.getResponses();
				String urls = "";
				Set<String> key = respMap.keySet();
				CertCheckZBResponse certPhotoResp = new CertCheckZBResponse();
				// for(Iterator it = key.iterator(); it.hasNext();){
				// BusiCompResponse busiResp =
				// (BusiCompResponse)respMap.get(it.next());
				// if(busiResp.getRule_id().equals(EcsOrderConsts.GET_CERT_PHOTO_FROM_ZB));{
				// certPhotoResp = (CertCheckZBResponse)busiResp.getResponse();
				// if(null!=certPhotoResp){
				// urls = certPhotoResp.getPhotoInfo();
				// break;
				// }
				// }
				// }
				// if(null!=urls&&!"".equals(urls)){
				// urls = urls.replace("{", "");
				// urls = urls.replace("}", "");
				// urls = urls.replace("\"", "");
				// String[] temp = urls.split(",");
				// for (int i=0;i<temp.length&&i<3;i++){//超出3张图片则过滤掉
				// if(temp[i].contains("http://")){
				// response.add(temp[i]);
				// }
				// }
				// }

				for (Iterator it = key.iterator(); it.hasNext();) {
					BusiCompResponse busiResp = (BusiCompResponse) respMap.get(it.next());
					if (busiResp.getRule_id().equals(EcsOrderConsts.GET_CERT_PHOTO_FROM_ZB))
						;
					{
						certPhotoResp = (CertCheckZBResponse) busiResp.getResponse();
						if (null != certPhotoResp) {
							response.add("data:image/gif;base64," + certPhotoResp.getPhotoInfo().getPhotoFront());
							response.add("data:image/gif;base64," + certPhotoResp.getPhotoInfo().getPhotoBack());
							response.add("data:image/gif;base64," + certPhotoResp.getPhotoInfo().getPhotoHand());
							break;
						}
					}
				}
				System.out.print("=====================================" + order_id + "证件查询结束2");
			} else {
				throw new Exception("总部证件信息获取失败：" + resp.getError_msg());
			}
		} else if (isTbOrder) {// 是淘宝订单
			PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
			plan.setPlan_id(EcsOrderConsts.GET_CERT_PHOTO_FROM_TB);// plan_id要换成淘宝的
			TacheFact fact = new TacheFact();
			fact.setOrder_id(order_id);
			plan.setFact(fact);
			plan.setDeleteLogs(true);
			PlanRuleTreeExeResp resp = CommonDataFactory.getInstance().exePlanRuleTree(plan);
			if (resp.getError_code().equals(ConstsCore.ERROR_SUCC)) {
				AutoFact autoFact = resp.getFact();
				Map<String, ZteResponse> respMap = autoFact.getResponses();
				String urls = "";
				Set<String> key = respMap.keySet();
				TaobaoIdentityGetResponse certPhotoResp = new TaobaoIdentityGetResponse();
				for (Iterator it = key.iterator(); it.hasNext();) {
					BusiCompResponse busiResp = (BusiCompResponse) respMap.get(it.next());
					if (busiResp.getRule_id().equals(EcsOrderConsts.GET_CERT_PHOTO_FROM_ZB))
						;
					{
						certPhotoResp = (TaobaoIdentityGetResponse) busiResp.getResponse();
						if (null != certPhotoResp) {
							response.add("data:image/gif;base64," + certPhotoResp.getFrontImageUrl());
							response.add("data:image/gif;base64," + certPhotoResp.getBackImageUrl());
							response.add("data:image/gif;base64," + certPhotoResp.getHoldImageUrl());
							break;
						}
					}
				}
			}
		} else {
			for (int i = 1; i <= 3; i++) {
				String timestamp = DateUtil.getTime5();
				// out_tid = "WCS15061117272663115829";
				String cSrc = "orderId=" + out_tid + "&file=" + i + "&timestamp=" + timestamp;
				String enString = AES.encrypt(cSrc, EcsOrderConsts.PHOTO_SKEY, "UTF-8");
				Random ra = new Random();
				int kk = ra.nextInt(1000);
				response.add(getRequest().getContextPath() + "/shop/admin/orderFlowAction!getRemoteImg.do?sec=" + enString + "&ra=" + kk);
			}
		}
		// }catch(Exception e){
		// e.printStackTrace();
		// }
		while (response.size() < 3) {// 返回图片少于3张，余下设置为错误图片
			response.add(EcsOrderConsts.ERROE_PHOTO_PATH);
		}
		System.out.print("=====================================" + order_id + "证件查询结束3");
		return response;
	}

	/**
	 * 后激活证件校验
	 * 
	 * @return
	 */
	public String validateIdCardActivateDelay() {
		String lockMsg = checkLockUser();
		this.certPhotosMap = new HashMap();
		// 清空校验信息
		CommonDataFactory.getInstance().updateAttrFieldValue(this.order_id, new String[] { AttrConsts.ACCOUNT_VALI }, new String[] { "" });
		PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
		plan.setPlan_id(EcsOrderConsts.VALIDATE_CARD_PLAN);
		TacheFact fact = new TacheFact();
		fact.setOrder_id(order_id);
		plan.setFact(fact);
		plan.setDeleteLogs(true);
		PlanRuleTreeExeResp resp = CommonDataFactory.getInstance().exePlanRuleTree(plan);
		resp.getError_code();
		if (!resp.getError_code().equals(ConstsCore.ERROR_SUCC)) {
			String msg = "身份证预校验失败:" + resp.getError_msg();
			certPhotosMap.put("result", "1");
			certPhotosMap.put("message", msg);
		} else {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			String out_tid = orderTree.getOrderExtBusiRequest().getOut_tid();
			String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();

			AutoFact autoFact = resp.getFact();
			Map<String, ZteResponse> respMap = autoFact.getResponses();
			String photo = "";
			Set<String> key = respMap.keySet();
			CheckIDECAOPResponse certResp = new CheckIDECAOPResponse();
			for (Iterator it = key.iterator(); it.hasNext();) {
				BusiCompResponse busiResp = (BusiCompResponse) respMap.get(it.next());
				if (busiResp.getRule_id().equals(EcsOrderConsts.ACCOUNT_CERT_VALI_RULE))
					;
				{
					certResp = (CheckIDECAOPResponse) busiResp.getResponse();
					break;
				}
			}
			if (null != certResp) {
				// 设置国政通证件照显示信息
				photo = certResp.getPhoto();
				certPhotosMap.put("gztPhoto", photo);
				try {
					// 获取用户上传证件照
					List<String> picUrls = getCradPicPathActivateDelay(order_id);
					String picjson = JsonUtil.toJson(picUrls);
					certResp.setAddr(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS));
					String certInfoJson = JsonUtil.toJson(certResp);
					certPhotosMap.put("result", "0");
					certPhotosMap.put("message", "证件照无需审核");
					certPhotosMap.put("imgTitle1", "姓名：" + certResp.getCertName());
					certPhotosMap.put("imgTitle2", "性别：" + certResp.getSex());
					certPhotosMap.put("imgTitle3", "民族：" + certResp.getNation());
					certPhotosMap.put("imgTitle4", "身份证号码：" + certResp.getCertId());
					certPhotosMap.put("imgTitle5", "地址：" + certResp.getAddr());
					certPhotosMap.put("userPhoto1", picUrls.get(0).toString());
					certPhotosMap.put("userPhoto2", picUrls.get(1).toString());
					certPhotosMap.put("userPhoto3", picUrls.get(2).toString());
					certPhotosMap.put("userPhoto4", picUrls.get(2).toString());
				} catch (Exception e) {
					this.json = "{result:1,message:'" + e.getMessage() + "'}";
				}
			} else {
				certPhotosMap.put("result", "1");
				certPhotosMap.put("message", "证件照无需审核");
			}

		}
		return "certPhotos";
	}

	// 后激活证件照获取
	private List<String> getCradPicPathActivateDelay(String orderId) throws Exception {
		List<String> response = new ArrayList<String>();
		// try{
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
		String out_tid = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID);
		boolean isTbOrder = CommonDataFactory.getInstance().isTbOrder(order_from);// 是否淘宝订单,默认否
		if (EcsOrderConsts.ORDER_FROM_10003.equals(order_from)) {// 总部订单
			System.out.print("=====================================" + order_id + "证件查询开始");
			PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
			plan.setPlan_id(EcsOrderConsts.GET_CERT_PHOTO_FROM_ZB);
			TacheFact fact = new TacheFact();
			fact.setOrder_id(order_id);
			plan.setFact(fact);
			plan.setDeleteLogs(true);
			PlanRuleTreeExeResp resp = CommonDataFactory.getInstance().exePlanRuleTree(plan);
			System.out.print("=====================================" + order_id + "证件查询结束1");
			if (resp.getError_code().equals(ConstsCore.ERROR_SUCC)) {
				AutoFact autoFact = resp.getFact();
				Map<String, ZteResponse> respMap = autoFact.getResponses();
				String urls = "";
				Set<String> key = respMap.keySet();
				CertCheckZBResponse certPhotoResp = new CertCheckZBResponse();

				for (Iterator it = key.iterator(); it.hasNext();) {
					BusiCompResponse busiResp = (BusiCompResponse) respMap.get(it.next());
					if (busiResp.getRule_id().equals(EcsOrderConsts.GET_CERT_PHOTO_FROM_ZB))
						;
					{
						certPhotoResp = (CertCheckZBResponse) busiResp.getResponse();
						if (null != certPhotoResp) {
							response.add("data:image/gif;base64," + certPhotoResp.getPhotoInfo().getPhotoFront());
							response.add("data:image/gif;base64," + certPhotoResp.getPhotoInfo().getPhotoBack());
							response.add("data:image/gif;base64," + certPhotoResp.getPhotoInfo().getPhotoHand());
							break;
						}
					}
				}
				System.out.print("=====================================" + order_id + "证件查询结束2");
			} else {
				throw new Exception("总部证件信息获取失败：" + resp.getError_msg());
			}
		} else if (isTbOrder) {// 是淘宝订单
			PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
			plan.setPlan_id(EcsOrderConsts.GET_CERT_PHOTO_FROM_TB);// plan_id要换成淘宝的
			TacheFact fact = new TacheFact();
			fact.setOrder_id(order_id);
			plan.setFact(fact);
			plan.setDeleteLogs(true);
			PlanRuleTreeExeResp resp = CommonDataFactory.getInstance().exePlanRuleTree(plan);
			if (resp.getError_code().equals(ConstsCore.ERROR_SUCC)) {
				AutoFact autoFact = resp.getFact();
				Map<String, ZteResponse> respMap = autoFact.getResponses();
				String urls = "";
				Set<String> key = respMap.keySet();
				TaobaoIdentityGetResponse certPhotoResp = new TaobaoIdentityGetResponse();
				for (Iterator it = key.iterator(); it.hasNext();) {
					BusiCompResponse busiResp = (BusiCompResponse) respMap.get(it.next());
					if (busiResp.getRule_id().equals(EcsOrderConsts.GET_CERT_PHOTO_FROM_ZB))
						;
					{
						certPhotoResp = (TaobaoIdentityGetResponse) busiResp.getResponse();
						if (null != certPhotoResp) {
							response.add("data:image/gif;base64," + certPhotoResp.getFrontImageUrl());
							response.add("data:image/gif;base64," + certPhotoResp.getBackImageUrl());
							response.add("data:image/gif;base64," + certPhotoResp.getHoldImageUrl());
							break;
						}
					}
				}
			}
		} else {
			for (int i = 1; i <= 4; i++) {
				String timestamp = DateUtil.getTime5();
				// out_tid = "WCS15061117272663115829";
				String cSrc = "orderId=" + out_tid + "&file=" + i + "&timestamp=" + timestamp;
				String enString = AES.encrypt(cSrc, EcsOrderConsts.PHOTO_SKEY, "UTF-8");
				Random ra = new Random();
				int kk = ra.nextInt(1000);
				response.add(getRequest().getContextPath() + "/shop/admin/orderFlowAction!getRemoteImg.do?sec=" + enString + "&ra=" + kk);
			}
		}
		// }catch(Exception e){
		// e.printStackTrace();
		// }
		while (response.size() < 4) {// 返回图片少于3张，余下设置为错误图片
			response.add(EcsOrderConsts.ERROE_PHOTO_PATH);
		}
		System.out.print("=====================================" + order_id + "证件查询结束3");
		return response;
	}

	public String valiPhotoLog() {
		try {
			String valiCode = "";
			String valiName = "";
			if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(isAgree)) {
				valiCode = EcsOrderConsts.ACCOUNT_VALI_2;
				valiName = "图片不一致";

				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CERT_UPLOAD_STATUS }, new String[] { EcsOrderConsts.CERT_STATUS_WAIT_REUPLOAD });
			} else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(isAgree)) {
				valiCode = EcsOrderConsts.ACCOUNT_VALI_3;
				valiName = "图片一致";
			}
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACCOUNT_VALI }, new String[] { valiCode });
			OrderItemsValiLogBusiRequest valiLogRequest = new OrderItemsValiLogBusiRequest();
			String log_id = "";
			String vali_time = "";

			log_id = DateUtil.getTime("yyyyMMddHHmmssSSS");
			vali_time = DateUtil.getTime2();
			valiLogRequest.setLog_id(log_id);
			valiLogRequest.setOrder_id(order_id);
			String item_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0).getItem_id();
			valiLogRequest.setItem_id(item_id);
			valiLogRequest.setAccount_vali(valiCode);
			valiLogRequest.setVali_desc(valiName);
			valiLogRequest.setVali_time(vali_time);
			AdminUser user = ManagerUtils.getAdminUser();
			valiLogRequest.setVali_user(user.getUserid());
			valiLogRequest.setSource_from("ECS");
			valiLogRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			valiLogRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
			valiLogRequest.store();
			this.json = "{result:0,message:'证件照" + valiName + "设置成功'}";
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.json = "{result:0,message:'证件照一致性设置错误'}";
		}
		return this.JSON_MESSAGE;
	}

	// 后向实名制，照片一致性审核
	public String valiPhotoLogActivateDelay() {
		try {
			String msg = editSave();
			if (msg != null && msg.length() > 0) {
				String validateMsg = JSONArray.toJSONString(validateMsgList);
				this.json = "{result:1,message:'" + msg + "',validateMsgList:" + validateMsg + "}";
				return this.JSON_MESSAGE;
			}

			String valiCode = "";
			String valiName = "";
			if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(isAgree)) {
				valiCode = EcsOrderConsts.ACCOUNT_VALI_2;
				valiName = "图片不一致";

				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CERT_UPLOAD_STATUS }, new String[] { EcsOrderConsts.CERT_STATUS_WAIT_REUPLOAD });
			} else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(isAgree)) {
				valiCode = EcsOrderConsts.ACCOUNT_VALI_3;
				valiName = "图片一致";
			}
			// 记录审核结果
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ACCOUNT_VALI }, new String[] { valiCode });

			OrderItemsValiLogBusiRequest valiLogRequest = new OrderItemsValiLogBusiRequest();
			String log_id = "";
			String vali_time = "";

			log_id = DateUtil.getTime("yyyyMMddHHmmssSSS");
			vali_time = DateUtil.getTime2();

			valiLogRequest.setLog_id(log_id);
			valiLogRequest.setOrder_id(order_id);
			String item_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0).getItem_id();
			valiLogRequest.setItem_id(item_id);
			valiLogRequest.setAccount_vali(valiCode);
			valiLogRequest.setVali_desc(valiName);
			valiLogRequest.setVali_time(vali_time);
			AdminUser user = ManagerUtils.getAdminUser();
			valiLogRequest.setVali_user(user.getUserid());
			valiLogRequest.setSource_from("ECS");
			valiLogRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			valiLogRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
			valiLogRequest.store();
			this.json = "{result:0,message:'证件照" + valiName + "设置成功'}";
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.json = "{result:0,message:'证件照一致性设置错误'}";
		}

		return this.JSON_MESSAGE;
	}

	// 修改预校验信息 同时同步总部 然后清空校验信息以便处理的时候重新校验
	public String PreDealInfoToZB() {

		// 先删除规则日志
		RuleExeLogDelReq delExeReq = new RuleExeLogDelReq();
		delExeReq.setObj_id(this.order_id);
		delExeReq.setRule_id(EcsOrderConsts.ORDER_PRE_INFO_TO_ZB_RULE);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		client.execute(delExeReq, RuleExeLogDelResp.class);

		String error_msg = "";
		// 已经保存的预处理页面的信息 然后掉变更接口
		String preValidateJson = "";

		RuleTreeExeReq req = new RuleTreeExeReq();
		TacheFact fact = new TacheFact();
		fact.setOrder_id(order_id);
		req.setRule_id(EcsOrderConsts.ORDER_PRE_INFO_TO_ZB_RULE);
		req.setFact(fact);
		RuleTreeExeResp response = null;
		try {
			response = CommonDataFactory.getInstance().exeRuleTree(req);
			if (response != null && "0".equals(response.getError_code())) {
				// 同步信息成功后设置校验状态
				// 接下来清空省份证校验状态和开户校验信息状态(从页面去身份证信息 如果省份证没变化 就不清空身份证校验状态)
				String newCertNum = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM);
				if (!StringUtils.equals(oldCertNum, newCertNum)) {
					// 成功设置预校验成功状态
					String idCardStatus = "";// 身份证校验状态置空 以便重新预校验
					CommonDataFactory.getInstance().updateAttrFieldValue(this.order_id, new String[] { AttrConsts.ACCOUNT_VALI }, new String[] { idCardStatus });
				}
				// ESS校验状态置空
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				OrderItemExtBusiRequest orderItemExtUpdate = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest();

				orderItemExtUpdate.setEss_pre_status("");
				orderItemExtUpdate.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderItemExtUpdate.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderItemExtUpdate.store(); // 属性数据入库
			} else {
				error_msg = response.getError_msg();
			}
		} catch (Exception e) {
			e.printStackTrace();
			error_msg = e.getMessage();
		}
		return error_msg;
	}

	public String getDevelopnmentName() {
		List developmentList = this.ordFlowManager.getDevelopmentName(this.developmentCode, this.order_from);
		String developmentName = "";
		if (developmentList.size() > 0) {
			developmentName = (String) ((Map) developmentList.get(0)).get("development_name");
			this.json = "{result:0,developmentName:'" + developmentName + "'}";
		} else {
			developmentName = developmentCode;
			// this.json ="{result:1,message:'发展人信息里面不存在该数据'}";
		}
		this.json = "{result:0,developmentName:'" + developmentName + "'}";
		return this.JSON_MESSAGE;
	}

	public String setBssStatusFinish() {
		String bssStatus = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_STATUS);
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<AttrGiftInfoBusiRequest> giftList = orderTree.getGiftInfoBusiRequests();
		AttrGiftInfoBusiRequest request1 = new AttrGiftInfoBusiRequest();
		StringBuffer giftSB = new StringBuffer();
		for (AttrGiftInfoBusiRequest gift : giftList) {
			giftSB.append("{\"sku\":\"" + gift.getSku_id() + "\",\"goodsName\":\"" + gift.getGoods_name() + "\",\"bssStatus\":" + gift.getBss_status() + "},");
		}
		String result = "1";
		if (ZjEcsOrderConsts.BSS_STATUS_2.equals(bssStatus)) {
			result = "2";
		}
		if (giftSB.length() > 1) {
			this.json = "{result:" + result + ",msg:'[" + giftSB.substring(0, giftSB.length() - 1) + "]',order_id:'" + order_id + "'}";
		} else {
			this.json = "{result:" + result + ",msg:'[]',order_id:'" + order_id + "'}";
		}

		return this.JSON_MESSAGE;
	}

	public String setBssFinish() {
		BusiDealResult result = ordBSSTacheManager.setStatusFinish(order_id);
		if (null != result && EcsOrderConsts.BUSI_DEAL_RESULT_0000.equals(result.getError_code())) {
			this.json = "{result:0,msg:'BSS业务办理完成设置成功'}";
		} else {
			this.json = "{result:1,msg:'BSS业务办理完成设置失败:" + result.getError_msg() + "'}";
		}
		return this.JSON_MESSAGE;
	}

	public String orderCBSSDeal() {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<AttrGiftInfoBusiRequest> giftList = orderTree.getGiftInfoBusiRequests();
		HttpLoginClient httpLogin = null;
		try {
			// httpLogin = ordBSSTacheManager.getHttpLoginClient(order_id);
			for (AttrGiftInfoBusiRequest gift : giftList) {
				String bssStatus = gift.getBss_status();
				if ("1".equals(bssStatus)) {
					gift.setBss_status("2");
					gift.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					gift.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					gift.store();
					ordBSSTacheManager.orderCBSSDeal(gift, httpLogin);
				}
			}
			ordBSSTacheManager.toChanageGiftBssStatus(order_id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 业务补办列表(某个订单es_order_sp_product、es_attr_package_subprod表下数据)
	 */
	public String rehandleBusinessList() {
		if (StringUtils.isEmpty(operation)) {// 首次加载页面
			this.orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			return "rehandleBusinessList";
		}
		// 加锁避免重复处理
		String lock_name = "YWBB" + order_id;
		Map lock = getMutexLock(lock_name);
		synchronized (lock) {
			String lockMsg = checkLockUser();
			if (!StringUtils.isEmpty(lockMsg)) {
				this.json = "{result:1,msg:'" + lockMsg + "'}";
				removeMutexLock(lock_name);
				return this.JSON_MESSAGE;
			}
			// 补办操作
			Map<String, String> operatParams = new HashMap<String, String>();
			String result = "";
			operatParams.put("order_id", order_id);
			operatParams.put("operation", operation);// 办理类型:线上；线下
			operatParams.put("sp_inst_id", sp_inst_id);// 要办理的服务，为all时则全部办理(剔除已办理的)
			operatParams.put("operationType", operationType);// 补办类型
			result = ordBSSTacheManager.rehandleBusiness(operatParams);
			this.orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			this.json = "{result:1,msg:'" + result + "'}";
			removeMutexLock(lock_name);
			return this.JSON_MESSAGE;
		}
	}

	/**
	 * 实物稽核校验
	 * 
	 * @return
	 */
	private String physicsCheck() {
		String msg = "";
		// 校验物流单号
		msg = this.validteLogino();
		if ("".equals(msg)) {
			// 判断是否通知总部写卡完成
			// OrderExtBusiRequest orderExt =
			// CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			// String is_write_card =
			// CommonDataFactory.getInstance().getAttrFieldValue(order_id,
			// AttrConsts.IS_WRITE_CARD);
			// if(EcsOrderConsts.IS_WRITE_CARD_YES.equals(is_write_card)){
			// if(EcsOrderConsts.WRITE_CARD_ZB_FAIL.equals(orderExt.getCol3())){
			// //通知失败
			// msg = "写卡结果通知总部失败";
			// }else if(StringUtil.isEmpty(orderExt.getCol3())){ //未通知
			// msg = "请等待总部写卡结果通知";
			// }
			// }
		}
		// 实物稽核界面数据校验, 在界面处理
		return msg;
	}

	public String getLogiCompanyByCityCode() {
		logiCompanyList = ecsOrdManager.logi_company_city_code(order_city_code);
		if (logiCompanyList.size() > 0) {
			this.json = "{result:0,msg:'" + JsonUtil.toJson(logiCompanyList) + "'}";
		} else {
			this.json = "{result:1,msg:''}";
		}
		return JSON_MESSAGE;
	}

	public String matchWareHouse() {
		PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
		plan.setPlan_id(EcsOrderConsts.MATCH_WAREHOUSE_PLAN_ID);// 201507229490000131
		WareHouseFact fact = new WareHouseFact();
		fact.setOrder_id(order_id);
		plan.setFact(fact);
		plan.setDeleteLogs(true);
		PlanRuleTreeExeResp resp = CommonDataFactory.getInstance().exePlanRuleTree(plan);
		if (resp.getError_code().equals("0")) {
			this.json = "{result:0,msg:'" + resp.getError_msg() + "'}";
		} else {
			this.json = "{result:1,msg:'" + resp.getError_msg() + "'}";
		}
		return JSON_MESSAGE;
	}

	/**
	 * 副卡信息
	 * 
	 * @return
	 */
	public String getOrderPhoneFuKaInfo() {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<OrderPhoneInfoFukaBusiRequest> orderPhoneInfoFukaBusiRequests = orderTree.getOrderPhoneInfoFukaBusiRequests();
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		json = JSONArray.toJSONString(orderPhoneInfoFukaBusiRequests);
		return JSON_MESSAGE;
	}

	/**
	 * 副卡可选包信息
	 * 
	 * @return
	 */
	public String getAttrPackageFukaInfo() {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<AttrPackageFukaBusiRequest> attrPackageFukaBusiRequests = orderTree.getAttrPackageFukaBusiRequests();
		json = JSONArray.toJSONString(attrPackageFukaBusiRequests);
		return JSON_MESSAGE;
	}

	//

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getGuhua_number() {
		return guhua_number;
	}

	public void setGuhua_number(String guhua_number) {
		this.guhua_number = guhua_number;
	}

	public String getOrder_Status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getExt_order_id() {
		return ext_order_id;
	}

	public void setExt_order_id(String ext_order_id) {
		this.ext_order_id = ext_order_id;
	}

	public String getAcceptanceTp() {
		return acceptanceTp;
	}

	public void setAcceptanceTp(String acceptanceTp) {
		this.acceptanceTp = acceptanceTp;
	}

	public String getAccept_print_type() {
		return accept_print_type;
	}

	public void setAccept_print_type(String accept_print_type) {
		this.accept_print_type = accept_print_type;
	}

	public String getAcceptanceForm() {
		return acceptanceForm;
	}

	public void setAcceptanceForm(String acceptanceForm) {
		this.acceptanceForm = acceptanceForm;
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

	public void setProvList(List provList) {
		this.provList = provList;
	}

	public List getProvList() {
		return provList;
	}

	public List getProTypeList() {
		return proTypeList;
	}

	public void setProTypeList(List proTypeList) {
		this.proTypeList = proTypeList;
	}

	public List getProCatList() {
		return proCatList;
	}

	public void setProCatList(List proCatList) {
		this.proCatList = proCatList;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public String getBrand_code() {
		return brand_code;
	}

	

    public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}

	public String getCat_id() {
		return cat_id;
	}

	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	public List<AttrGiftInfoBusiRequest> getGiftInfoList() {
		return giftInfoList;
	}

	public void setGiftInfoList(List<AttrGiftInfoBusiRequest> giftInfoList) {
		this.giftInfoList = giftInfoList;
	}

	public List getGiftBrandList() {
		return giftBrandList;
	}

	public void setGiftBrandList(List giftBrandList) {
		this.giftBrandList = giftBrandList;
	}

	public Map getBuyInfoMap() {
		return buyInfoMap;
	}

	public void setBuyInfoMap(Map buyInfoMap) {
		this.buyInfoMap = buyInfoMap;
	}

	public String getIsTerm() {
		return isTerm;
	}

	public void setIsTerm(String isTerm) {
		this.isTerm = isTerm;
	}

	public String getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}

	public IOrderServices getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(IOrderServices orderServices) {
		this.orderServices = orderServices;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getDealDesc() {
		// if(StringUtils.isEmpty(dealDesc))dealDesc = dealDescIE8;
		return dealDesc;
	}

	public void setDealDesc(String dealDesc) {
		this.dealDesc = dealDesc;
	}

	public String getLogi_receiver() {
		return logi_receiver;
	}

	public void setLogi_receiver(String logi_receiver) {
		this.logi_receiver = logi_receiver;
	}

	public String getLogi_receiver_phone() {
		return logi_receiver_phone;
	}

	public void setLogi_receiver_phone(String logi_receiver_phone) {
		this.logi_receiver_phone = logi_receiver_phone;
	}

	public String getDelivery_id() {
		return delivery_id;
	}

	public void setDelivery_id(String delivery_id) {
		this.delivery_id = delivery_id;
	}

	public String getCarray_free() {
		return carray_free;
	}

	public void setCarray_free(String carray_free) {
		this.carray_free = carray_free;
	}

	public String getProtect_free() {
		return protect_free;
	}

	public void setProtect_free(String protect_free) {
		this.protect_free = protect_free;
	}

	public String getProd_audit_status() {
		return prod_audit_status;
	}

	public void setProd_audit_status(String prod_audit_status) {
		this.prod_audit_status = prod_audit_status;
	}

	public OrdReceipt getOrdReceipt() {
		return ordReceipt;
	}

	public void setOrdReceipt(OrdReceipt ordReceipt) {
		this.ordReceipt = ordReceipt;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public OrderTreeBusiRequest getOrderTree() {
		return orderTree;
	}

	public void setOrderTree(OrderTreeBusiRequest orderTree) {
		this.orderTree = orderTree;
	}

	public String getIc_Card() {
		return ic_Card;
	}

	public void setIc_Card(String ic_Card) {
		this.ic_Card = ic_Card;
	}

	public OrderDeliveryBusiRequest getDelivery() {
		return delivery;
	}

	public void setDelivery(OrderDeliveryBusiRequest delivery) {
		this.delivery = delivery;
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

	public String getSpecValidateMsg() {
		return specValidateMsg;
	}

	public void setSpecValidateMsg(String specValidateMsg) {
		this.specValidateMsg = specValidateMsg;
	}

	public int getGiftInfoSize() {
		return giftInfoSize;
	}

	public void setGiftInfoSize(int giftInfoSize) {
		this.giftInfoSize = giftInfoSize;
	}

	public String getD_type() {
		return d_type;
	}

	public void setD_type(String d_type) {
		this.d_type = d_type;
	}

	public double getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(double order_amount) {
		this.order_amount = order_amount;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String[] getOrderidArray() {
		return orderidArray;
	}

	public void setOrderidArray(String[] orderidArray) {
		this.orderidArray = orderidArray;
	}

	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}

	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}

	public String getN_shipping_amount() {
		return n_shipping_amount;
	}

	public void setN_shipping_amount(String n_shipping_amount) {
		this.n_shipping_amount = n_shipping_amount;
	}

	public String getShipping_company() {
		return shipping_company;
	}

	public void setShipping_company(String shipping_company) {
		this.shipping_company = shipping_company;
	}

	public String getWriteCardResult() {
		return writeCardResult;
	}

	public void setWriteCardResult(String writeCardResult) {
		this.writeCardResult = writeCardResult;
	}

	public List<Logi> getLogiCompanyList() {
		return logiCompanyList;
	}

	public void setLogiCompanyList(List<Logi> logiCompanyList) {
		this.logiCompanyList = logiCompanyList;
	}

	public IEcsOrdManager getEcsOrdManager() {
		return ecsOrdManager;
	}

	public void setEcsOrdManager(IEcsOrdManager ecsOrdManager) {
		this.ecsOrdManager = ecsOrdManager;
	}

	public IOrdVisitTacheManager getOrdVisitTacheManager() {
		return ordVisitTacheManager;
	}

	public void setOrdVisitTacheManager(IOrdVisitTacheManager ordVisitTacheManager) {
		this.ordVisitTacheManager = ordVisitTacheManager;
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
    
    public String getCbss_develop_code() {
		return cbss_develop_code;
	}

	public void setCbss_develop_code(String cbss_develop_code) {
		this.cbss_develop_code = cbss_develop_code;
	}

	public String getCbss_development_point_code() {
		return cbss_development_point_code;
	}

	public void setCbss_development_point_code(String cbss_development_point_code) {
		this.cbss_development_point_code = cbss_development_point_code;
	}

	public String getICCID_INFO() {
        return ICCID_INFO;
    }

    public void setICCID_INFO(String iCCID_INFO) {
        ICCID_INFO = iCCID_INFO;
    }

    public String getOld_terminal_num() {
        return old_terminal_num;
    }

    public void setOld_terminal_num(String old_terminal_num) {
        this.old_terminal_num = old_terminal_num;
    }

    public String getTerminal_num() {
        return terminal_num;
    }

    public void setTerminal_num(String terminal_num) {
        this.terminal_num = terminal_num;
    }

    public String getObject_name() {
        return object_name;
    }

    public void setObject_name(String object_name) {
        this.object_name = object_name;
    }

    public String getOrder_from() {
		return order_from;
	}

	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}

	public String getOrder_model() {
		return order_model;
	}

	public void setOrder_model(String order_model) {
		this.order_model = order_model;
	}

	public String getOrder_is_his() {
		return order_is_his;
	}

	public void setOrder_is_his(String order_is_his) {
		this.order_is_his = order_is_his;
	}

	public List getValidateMsgList() {
		return validateMsgList;
	}

	public void setValidateMsgList(List validateMsgList) {
		this.validateMsgList = validateMsgList;
	}

	public String getAbnormal_type() {
		return abnormal_type;
	}

	public void setAbnormal_type(String abnormal_type) {
		this.abnormal_type = abnormal_type;
	}

	public String getQuery_type() {
		return query_type;
	}

	public void setQuery_type(String query_type) {
		this.query_type = query_type;
	}

	public IOrdBSSTacheManager getOrdBSSTacheManager() {
		return ordBSSTacheManager;
	}

	public void setOrdBSSTacheManager(IOrdBSSTacheManager ordBSSTacheManager) {
		this.ordBSSTacheManager = ordBSSTacheManager;
	}

	public String getReplenishPage() {
		return replenishPage;
	}

	public void setReplenishPage(String replenishPage) {
		this.replenishPage = replenishPage;
	}

	public String getQ_check() {
		return q_check;
	}

	public void setQ_check(String q_check) {
		this.q_check = q_check;
	}

	public List<AttrFeeInfoBusiRequest> getFeeInfoList() {
		return feeInfoList;
	}

	public void setFeeInfoList(List<AttrFeeInfoBusiRequest> feeInfoList) {
		this.feeInfoList = feeInfoList;
	}

	public String getFeeInfo() {
		return feeInfo;
	}

	public void setFeeInfo(String feeInfo) {
		this.feeInfo = feeInfo;
	}

	public String getOldCertNum() {
		return oldCertNum;
	}

	public void setOldCertNum(String oldCertNum) {
		this.oldCertNum = oldCertNum;
	}

	public String getBtnType() {
		return btnType;
	}

	public void setBtnType(String btnType) {
		this.btnType = btnType;
	}

	public String getOrder_city_code() {
		return order_city_code;
	}

	public void setOrder_city_code(String order_city_code) {
		this.order_city_code = order_city_code;
	}

	public OrderPhoneInfoBusiRequest getOrderPhoneInfo() {
		return orderPhoneInfo;
	}

	public void setOrderPhoneInfo(OrderPhoneInfoBusiRequest orderPhoneInfo) {
		this.orderPhoneInfo = orderPhoneInfo;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getQueryTypeChooseReturnVal() {
		return queryTypeChooseReturnVal;
	}

	public void setQueryTypeChooseReturnVal(String queryTypeChooseReturnVal) {
		this.queryTypeChooseReturnVal = queryTypeChooseReturnVal;
	}

	// 省份号码的获取具体方法
	public List getPhoneListByProvince() {
		List list = new ArrayList();
		NumberResourceQueryZjBssRequest req = new NumberResourceQueryZjBssRequest();

		if ("330100".equals(provinceQueryPara_001)) {
			req.setRegion_id("A");// 杭州市
		} else if ("330500".equals(provinceQueryPara_001)) {
			req.setRegion_id("D");// 湖州
		} else if ("330400".equals(provinceQueryPara_001)) {
			req.setRegion_id("E");// 嘉兴
		} else if ("330700".equals(provinceQueryPara_001)) {
			req.setRegion_id("I");// 金华
		} else if ("331100".equals(provinceQueryPara_001)) {
			req.setRegion_id("H");// 丽水
		} else if ("330200".equals(provinceQueryPara_001)) {
			req.setRegion_id("K");// 宁波
		} else if ("330600".equals(provinceQueryPara_001)) {
			req.setRegion_id("F");// 绍兴
		} else if ("331000".equals(provinceQueryPara_001)) {
			req.setRegion_id("G");// 台州
		} else if ("330300".equals(provinceQueryPara_001)) {
			req.setRegion_id("B");// 温州
		} else if ("330900".equals(provinceQueryPara_001)) {
			req.setRegion_id("J");// 舟山
		} else if ("330800".equals(provinceQueryPara_001)) {
			req.setRegion_id("C");// 衢州
		} else if ("330000".equals(provinceQueryPara_001)) {
			req.setRegion_id("Z");// 浙江
		} else {
			req.setRegion_id("Z");// 浙江
		}
		req.setQuery_key(queryPara_03);// 01：移网随机 02：移网关键字 暂时先设置为随机查询
		req.setQuery_value(provinceQueryPara_002);// 随机类型
		req.setNumber(provinceQueryPara_003);// 返回号码数
		req.setOffice_id("AQ0000");// 设置操作点

		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		NumberResourceQueryZjBssResponse resp = client.execute(req, NumberResourceQueryZjBssResponse.class);

		if (resp != null && EcsOrderConsts.BSS_SUCCESS_00000.equals(resp.getCode())) {
			list = resp.getRespJson();// 拿到号码
		}
		return list;

	}

	// 总部号码获取具体方法
	public List getPhoneList() {

		List list = new ArrayList();

		// 先根据订单号获取ESS工号信息
		EssOperatorInfoUtil essutil = new EssOperatorInfoUtil();
		EmpOperInfoVo empinfo;
		NumberResourceQueryZbRequest req = new NumberResourceQueryZbRequest();
		req.setQueryParas(new ArrayList<QueryParasZb>());
		/* req.setPara(new ParaZb()); */
		try {
			empinfo = essutil.getEssInfoByOrderId(order_id);
			String gonghao = empinfo.getEss_emp_id();
			req.setOperatorId(gonghao);// 设置工号
			// req.setOperatorId("AEDKH135");// 设置工号
			req.setProvince(empinfo.getProvince());// 设置省份36
			req.setCity(empinfo.getCity());// 设置城市360
			req.setDistrict(empinfo.getDistrict());// 设置区县00000000
			req.setChannelId(empinfo.getChannel_id());// 设置渠道编码 36a0187
			req.setChannelType(empinfo.getChannel_type());// 设置渠道编码 1010300
		} catch (ApiBusiException e) {
			e.printStackTrace();
		}
		req.setSerType(queryPara_30_serType);// 设置后付费，预付费类型

		QueryParasZb a = new QueryParasZb();
		if (!StringUtils.isEmpty(queryPara_10)) {// 设置查询类型
			a.setQueryType(queryPara_10);
		}

		// 设置查询具体参数
		if (!StringUtils.isEmpty(queryPara_10) && "02".equals(queryPara_10)) {
			a.setQueryPara(segmentCode);
		} else if (!StringUtils.isEmpty(queryPara_10) && "03".equals(queryPara_10)) {
			a.setQueryPara(queryPara_21_Keywords);
		} else if (!StringUtils.isEmpty(queryPara_10) && "04".equals(queryPara_10)) {
			a.setQueryPara(queryPara_22_NumLevel);
		} else if (!StringUtils.isEmpty(queryPara_10) && "05".equals(queryPara_10)) {
			a.setQueryPara(queryPara_23_prepaidProductCode);
		} else if (!StringUtils.isEmpty(queryPara_10) && "06".equals(queryPara_10)) {
			a.setQueryPara(queryPara_24_numRange);
		}
		req.getQueryParas().add(a);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		NumberResourceQueryZbResponse resp = new NumberResourceQueryZbResponse();

		resp = client.execute(req, NumberResourceQueryZbResponse.class);
		// if(resp!=null&&EcsOrderConsts.AOP_SUCCESS_0000.equals(resp.getCode())){
		list = resp.getNumInfo();// 拿到号码信息
		// }
		return list;
	}

	public List getPhoneList(String qwithoutid) {// 有参的getPhoneList只是用于订单补录总部选号用。

		if ("yes".equals(qwithoutid)) {
			List list = new ArrayList();
			// 先根据登录用户获取ESS工号信息
			AdminUser user = ManagerUtils.getAdminUser();// 获取登录用户
			String orderOperId = user.getUserid();// 获得操作工号
			String cityCode = CommonDataFactory.getInstance().getOtherDictVodeValue("city", ordercitycode);// 获取AOP城市代码
			EmpOperInfoVo empinfo = new EmpOperInfoVo();
			try {
				empinfo = EssOperatorInfoUtil.getECOperatorRelInfo(null, orderOperId, cityCode);
			} catch (ApiBusiException e) {

				e.printStackTrace();
				logger.info("根据登录人获取操作工号信息为空或者失败");
				return null;
			}
			NumberResourceQueryZbRequest req = new NumberResourceQueryZbRequest();
			req.setQueryParas(new ArrayList<QueryParasZb>());// 初始化一个参数对象

			String gonghao = empinfo.getEss_emp_id();
			req.setOperatorId(gonghao);// 设置工号
			// req.setOperatorId("AEDKH135");// 设置工号
			req.setProvince(empinfo.getProvince());// 设置省份36
			req.setCity(empinfo.getCity());// 设置城市360
			req.setDistrict(empinfo.getDistrict());// 设置区县00000000
			req.setChannelId(empinfo.getChannel_id());// 设置渠道编码 36a0187
			req.setChannelType(empinfo.getChannel_type());// 设置渠道编码 1010300

			req.setSerType(queryPara_30_serType);// 设置后付费，预付费类型

			QueryParasZb a = new QueryParasZb();
			if (!StringUtils.isEmpty(queryPara_10)) {// 设置查询类型
				a.setQueryType(queryPara_10);
			}

			// 设置查询具体参数
			if (!StringUtils.isEmpty(queryPara_10) && "02".equals(queryPara_10)) {
				a.setQueryPara(segmentCode);
			} else if (!StringUtils.isEmpty(queryPara_10) && "03".equals(queryPara_10)) {
				a.setQueryPara(queryPara_21_Keywords);
			} else if (!StringUtils.isEmpty(queryPara_10) && "04".equals(queryPara_10)) {
				a.setQueryPara(queryPara_22_NumLevel);
			} else if (!StringUtils.isEmpty(queryPara_10) && "05".equals(queryPara_10)) {
				a.setQueryPara(queryPara_23_prepaidProductCode);
			} else if (!StringUtils.isEmpty(queryPara_10) && "06".equals(queryPara_10)) {
				a.setQueryPara(queryPara_24_numRange);
			}

			req.getQueryParas().add(a);

			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

			NumberResourceQueryZbResponse resp = new NumberResourceQueryZbResponse();

			resp = client.execute(req, NumberResourceQueryZbResponse.class);
			// if(resp!=null&&EcsOrderConsts.AOP_SUCCESS_0000.equals(resp.getCode())){
			list = resp.getNumInfo();// 拿到号码信息
			// }

			return list;
		}
		return null;
	}

	public String qryBssPhoneNumList() {
		List numList = new ArrayList();
		List fenye_list = new ArrayList();
		if ("yes".equals(isQuery)) {
			numList = getPhoneListByProvince();// 获取省份号码清单

			if (numList != null && numList.size() > 0) {
				if (this.getPage() * this.getPageSize() > numList.size()) {
					for (int i = (this.getPage() - 1) * this.getPageSize(); i < numList.size(); i++) {
						fenye_list.add(numList.get(i));
					}
				} else {
					for (int i = (this.getPage() - 1) * this.getPageSize(); i < (this.getPage() - 1) * this.getPageSize() + 10; i++) {
						fenye_list.add(numList.get(i));
					}
				}

				this.webpage = new Page(this.getPage(), numList.size(), this.getPageSize(), fenye_list);
			}

		}

		return "bss_phone_num";
	}

	public String qryZbPhoneNumList() {
		List numList = new ArrayList();
		List fenye_list = new ArrayList();
		if ("yes".equals(isQuery)) {
			numList = getPhoneList("yes"); // 获取总部号码清单，订单补录调用有参的这个方法

			if (numList != null && numList.size() > 0) {
				if (this.getPage() * 10 > numList.size()) {
					for (int i = (this.getPage() - 1) * 10; i < numList.size(); i++) {
						fenye_list.add(numList.get(i));
					}
				} else {
					for (int i = (this.getPage() - 1) * 10; i < (this.getPage() - 1) * 10 + 10; i++) {
						fenye_list.add(numList.get(i));
					}
				}
				this.webpage = new Page(this.getPage(), numList.size(), 10, fenye_list);
			}

		}

		return "zb_phone_num";
	}

	// 获取号码查询清单
	public String getPhoneNumList() {
		/*
		 * OrderTreeBusiRequest orderTree =
		 * CommonDataFactory.getInstance().getOrderTree(order_id);//订单树取得ID
		 * 
		 * //页面默认号段设置
		 * 
		 * if(StringUtils.isEmpty(segmentCode)){
		 * if(!StringUtils.isEmpty(phone_num)){ this.segmentCode =
		 * phone_num.substring(0, 3); }else{ String type_id =
		 * CommonDataFactory.getInstance().getGoodSpec(order_id, "",
		 * SpecConsts.TYPE_ID); this.segmentCode =
		 * CommonDataFactory.getInstance().getOtherDictVodeValue("segmentCode",
		 * type_id); } }
		 * 
		 * 
		 * this.orderPhoneInfo = orderTree.getPhoneInfoBusiRequest(); String
		 * phone_num = this.phone_num ;
		 */
		if (StringUtils.equals(isQuery, "yes")) {
			List list = new ArrayList();

			if (!StringUtils.isEmpty(infIsZb) && "yes".equals(infIsZb)) {
				list = getPhoneList(); // 获取总部号码清单
			} else {
				list = getPhoneListByProvince();// 获取省份号码清单
			}

			List fenye_list = new ArrayList();
			if (list != null && list.size() > 0) {

				if (this.getPage() * this.getPageSize() > list.size()) {
					for (int i = (this.getPage() - 1) * this.getPageSize(); i < list.size(); i++) {
						fenye_list.add(list.get(i));
					}
				} else {
					for (int i = (this.getPage() - 1) * this.getPageSize(); i < (this.getPage() - 1) * this.getPageSize() + this.getPageSize(); i++) {
						fenye_list.add(list.get(i));
					}
				}
				this.webpage = new Page(this.getPage(), list.size(), this.getPageSize(), fenye_list);
			}

		}
		if ("zb".equals(selNumChannel)) {
			return "phone_num_listZb";
		} else {
			return "phone_num_listProvince";
		}

	}

	// 号码变更 信息需要预占或者同步总部
	public String changePhoneInfo() throws Exception {
		try {
			Map old_map = ecsOrdManager.getUpdate(order_id);// 旧号码MAP
			// 先获取旧号码
			String old_phone = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);// 旧的电话号码(数据库取得）
			Map map = this.phoneInfoMap;
			String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);// 订单来源(数据库取得）
			orderPhoneInfo = CommonDataFactory.getInstance().getOrderTree(order_id).getPhoneInfoBusiRequest();// 数据库取得订单树里的号码信息分支
			String operatorState = orderPhoneInfo.getOperatorState();// 从订单树取得操作状态
			BeanUtils.copyProperties(orderPhoneInfo, this.phoneInfoMap);// 和页面的phoneInfoMap数据合并

			if (StringUtils.isEmpty(orderPhoneInfo.getClassId() + "") || "0".equals(orderPhoneInfo.getClassId() + "")) {// 如果ClassId为空或者为0，那直接改为9，9位普通号码，靓号等级
				orderPhoneInfo.setClassId(9);
			}
			if (StringUtil.isEmpty(this.orderPhoneInfo.getPhone_num())) {// 取得电话号码
				this.json = "{result:0,message:'新选号不能为空!',view_phone_num:'',operatorState:''}";
				return this.JSON_MESSAGE;// 将JSON信息返回到页面
			}
			if (StringUtil.equals(old_phone, this.orderPhoneInfo.getPhone_num())) {
				this.json = "{result:0,message:'新选号与旧号码不能相同!',view_phone_num:'" + old_phone + "',operatorState:''}";
				return this.JSON_MESSAGE;
			}

			// 获取号码预占的系统
			// String occupancySysOld =
			// CommonDataFactory.getInstance().sectionNoOccupancySysPlan(order_id,
			// old_phone);
			// String occupancySysNew =
			// CommonDataFactory.getInstance().sectionNoOccupancySysPlan(order_id,this.orderPhoneInfo.getPhone_num());
			String isAop = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_AOP);
			// 186号段 旧号码释放
			// if(!StringUtils.isEmpty(old_phone) &&
			// old_phone.startsWith("186")){
			if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(isAop)) {// ||
																		// EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP.equals(isAop)

				PlanRuleTreeExeReq req186 = new PlanRuleTreeExeReq();// 规则树请求参数
				TacheFact fact186 = new TacheFact();// 环节因子参数
				fact186.setOrder_id(order_id);// 环节参数塞入订单号
				req186.setPlan_id(EcsOrderConsts.NUMBER_RELEASE_BSS);// 页面设置控制用的规则ID，对应页面的选项
				req186.setFact(fact186);// 请求参数塞入环节参数
				req186.setDeleteLogs(true);// 设置删除日志参数

				PlanRuleTreeExeResp planResp = new PlanRuleTreeExeResp();// 规则树相应参数
				BusiCompResponse busiResp = new BusiCompResponse();// 业务组件返回对象

				planResp = CommonDataFactory.getInstance().exePlanRuleTree(req186);// 执行规则树，生成返回对象
				busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);// 规则树对象输入规则树，或得规则结果

				if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
					this.json = "{result:0,message:'旧号码 " + old_phone + " 释放失败，变更号码失败',view_phone_num:'" + old_phone + "',operatorState:'" + operatorState + "'}";
					return this.JSON_MESSAGE;
				}
			}

			// 旧号码释放
			if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP.equals(isAop)) {
				PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
				TacheFact fact = new TacheFact();
				fact.setOrder_id(order_id);
				req.setPlan_id(EcsOrderConsts.NUMBER_CHANGE_AOP);
				req.setFact(fact);
				req.setDeleteLogs(true);
				PlanRuleTreeExeResp planResp = new PlanRuleTreeExeResp();
				BusiCompResponse busiResp = null;
				if (!StringUtils.isEmpty(old_phone)) {
					planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
					busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
				} else {
					busiResp = new BusiCompResponse();
					busiResp.setError_code(ConstsCore.ERROR_SUCC);
					busiResp.setError_msg("无号码释放");
				}
				if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
					this.json = "{result:0,message:'旧号码 " + old_phone + " 释放失败，变更号码失败',view_phone_num:'" + old_phone + "',operatorState:'" + operatorState + "'}";
					return this.JSON_MESSAGE;
				}
			}

			// 变更号码 保存号码信息
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.PHONE_NUM }, new String[] { this.orderPhoneInfo.getPhone_num() });
			// 更新号码信息表 初始化预占相关字段
			orderPhoneInfo.setOccupied_msg(ConstsCore.NULL_VAL);
			orderPhoneInfo.setOccupiedFlag(EcsOrderConsts.OCCUPIEDFLAG_0);
			orderPhoneInfo.setBssOccupiedFlag(EcsOrderConsts.OCCUPIEDFLAG_0);
			orderPhoneInfo.setOperatorState(ConstsCore.NULL_VAL);
			orderPhoneInfo.setProKey(ConstsCore.NULL_VAL);
			orderPhoneInfo.setOccupiedTime(ConstsCore.NULL_VAL);
			orderPhoneInfo.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderPhoneInfo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderPhoneInfo.store();
			// 商城2.0订单号码状态变更通知新商城
			if (EcsOrderConsts.ORDER_FROM_100312.equals(order_from)) {
				String msg = this.changePhoneInfoToWYG(orderPhoneInfo.getPhone_num());
				if (msg.length() > 0) {
					this.json = "{result:1,message:'变更号码通知新商城失败',view_phone_num:'" + orderPhoneInfo.getPhone_num() + "',operatorState:'" + operatorState + "'}";
					return this.JSON_MESSAGE;
				}
			}

			// 新号码预占
			// isAop="2";//省份测试预占临时设值，测试完需要删除

			// 总部AOP预占
			if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP.equals(isAop)) {
				PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
				TacheFact fact = new TacheFact();
				fact.setOrder_id(order_id);
				req.setPlan_id(EcsOrderConsts.NUMBER_PRE_OCCUPANCY_AOP);
				req.setFact(fact);
				req.setDeleteLogs(true);
				PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
				BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
				if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
					this.json = "{result:1,message:'新号码 " + phone_num + " 预占失败:" + planResp.getError_msg() + "',view_phone_num:'" + orderPhoneInfo.getPhone_num() + "',operatorState:'"
							+ EcsOrderConsts.OPERATOR_STATE_1 + "'}";
					return this.JSON_MESSAGE;
				}
			}

			// 总部号码预占
			if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP.equals(isAop)) {
				PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
				TacheFact fact = new TacheFact();
				fact.setOrder_id(order_id);
				req.setPlan_id(EcsOrderConsts.NUMBER_OCCUPANCY_AOP);
				req.setFact(fact);
				req.setDeleteLogs(true);
				PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
				BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
				if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
					this.json = "{result:1,message:'新号码 " + phone_num + " 预定失败:" + planResp.getError_msg() + "',view_phone_num:'" + orderPhoneInfo.getPhone_num() + "',operatorState:'"
							+ EcsOrderConsts.OPERATOR_STATE_3 + "'}";
					return this.JSON_MESSAGE;
				}
			}

			// 186号段 号码预占 BSS省份预占
			// if(!orderPhoneInfo.getPhone_num().isEmpty() &&
			// orderPhoneInfo.getPhone_num().startsWith("186")){
			if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(isAop)) {// ||
																		// EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP.equals(isAop)
				PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
				TacheFact fact = new TacheFact();
				fact.setOrder_id(order_id);
				req.setFact(fact);
				req.setDeleteLogs(true);
				req.setPlan_id(EcsOrderConsts.NUMBER_OCCUPANCY_BSS);
				PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
				BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
				if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
					this.json = "{result:1,message:'新号码 " + phone_num + " 预占失败:" + planResp.getError_msg() + "',view_phone_num:'" + orderPhoneInfo.getPhone_num() + "',operatorState:'"
							+ EcsOrderConsts.OPERATOR_STATE_1 + "'}";
					return this.JSON_MESSAGE;
				}
			}
			Map new_map = ecsOrdManager.getUpdate(order_id);
			ecsOrdManager.saveChange(old_map, new_map, order_id);// 更新数据库号码
			String syn_ord_zb = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SYN_ORD_ZB);
			if (EcsOrderConsts.SYN_ORD_ZB_1.equals(syn_ord_zb)) {
				// 调用订单信息同步总部的接口
				this.PreDealInfoToZB();
			}
			this.json = "{result:0,message:'号码变更成功',view_phone_num:'" + orderPhoneInfo.getPhone_num() + "',operatorState:'" + EcsOrderConsts.OPERATOR_STATE_2 + "'}";
			return this.JSON_MESSAGE;

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 打开副卡查询选择页面
	 * 
	 * @return
	 * 
	 */
	public String getPhoneNumFukaList() {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		// 页面默认号段设置
		if (StringUtils.isEmpty(segmentCode)) {
			if (!StringUtils.isEmpty(old_phone_num)) {
				this.segmentCode = old_phone_num.substring(0, 3);
			} else {
				String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.TYPE_ID);
				this.segmentCode = CommonDataFactory.getInstance().getOtherDictVodeValue("segmentCode", type_id);
			}
		}
		if (StringUtils.equals(isQuery, "yes")) {
			List list = getPhoneList();
			List fenye_list = new ArrayList();
			if (list.size() > 0) {
				if (this.getPage() * 10 > list.size()) {
					for (int i = (this.getPage() - 1) * 10; i < list.size(); i++) {
						fenye_list.add(list.get(i));
					}
				} else {
					for (int i = (this.getPage() - 1) * 10; i < (this.getPage() - 1) * 10 + 10; i++) {
						fenye_list.add(list.get(i));
					}
				}
			}
			this.webpage = new Page(this.getPage(), list.size(), 10, fenye_list);
		}
		// System.out.print("fukaInstId="+this.fukaInstId+",order_id="+order_id+",old_phone_num="+old_phone_num+"\n");
		return "phone_num_fuka_list";
	}

	/**
	 * 单个号码变更--支持主副卡
	 * 
	 * @return
	 * @throws Exception
	 */
	public String changePhoneNumSinge() {
		BusiCompRequest busiComReq = new BusiCompRequest();
		Map params = new HashMap();
		params.put("order_id", order_id);
		params.put("numType", numType);
		busiComReq.setOrder_id(order_id);
		busiComReq.setQueryParams(params);

		String errorMsg = "接口异常";
		if (StringUtils.isEmpty(phone_num)) {
			this.json = "{result:0,message:'变更失败,新号码获取异常，请联系运维查询'" + ",view_phone_num:'" + old_phone_num + "'" + ",operatorState:'" + EcsOrderConsts.OCCUPIEDFLAG_7 + "'}";
			return this.JSON_MESSAGE;
		}

		// 获取号码预占的系统
		String occupancySysOld = CommonDataFactory.getInstance().sectionNoOccupancySysPlan(order_id, old_phone_num);
		String occupancySysNew = CommonDataFactory.getInstance().sectionNoOccupancySysPlan(order_id, phone_num);

		// 186号段号码释放
		// if(!StringUtils.isEmpty(old_phone_num) &&
		// old_phone_num.startsWith("186")){
		if (StringUtils.equals(occupancySysOld, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_0) || StringUtils.equals(occupancySysOld, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_1)) {
			if (StringUtils.equals(numType, EcsOrderConsts.ZB_CARD_TYPE_FU)) {
				params.put("phoneNum", old_phone_num);
				params.put("fukaInstId", fukaInstId);
				busiComReq.setEnginePath("zteCommonTraceRule.numberReleaseBatchFukaBss");
			} else {
				busiComReq.setEnginePath("zteCommonTraceRule.numberReleaseBss");
			}
			try {
				BusiCompResponse rsp = orderServices.execBusiComp(busiComReq);
				if (!StringUtils.equals(rsp.getError_code(), ConstsCore.ERROR_SUCC)) {
					errorMsg = rsp.getError_msg() == null ? errorMsg : rsp.getError_msg();

					this.json = "{result:0,message:'变更失败,原号码186释放失败:" + errorMsg + "'" + ",view_phone_num:'" + old_phone_num + "'" + ",operatorState:'" + EcsOrderConsts.OCCUPIEDFLAG_9 + "'}";
					return this.JSON_MESSAGE;
				}
			} catch (Exception e) {
				this.json = "{result:0,message:'变更失败,接口异常,请联系运维检查!'" + ",view_phone_num:'" + old_phone_num + "'" + ",operatorState:'" + EcsOrderConsts.OCCUPIEDFLAG_9 + "'}";
				return this.JSON_MESSAGE;
			}
		}

		// aop释放
		if (StringUtils.equals(occupancySysOld, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_1) || StringUtils.equals(occupancySysOld, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_2)) {
			if (!StringUtils.isEmpty(old_phone_num)) {
				if (StringUtils.equals(numType, EcsOrderConsts.ZB_CARD_TYPE_FU)) {
					params.put("phoneNum", old_phone_num);
					params.put("fukaInstId", fukaInstId);
					busiComReq.setEnginePath("zteCommonTraceRule.numberReleaseBatchAopFuKa");
				} else {
					busiComReq.setEnginePath("zteCommonTraceRule.numberReleaseAop");
				}
				try {
					BusiCompResponse rsp0 = orderServices.execBusiComp(busiComReq);
					if (!StringUtils.equals(rsp0.getError_code(), ConstsCore.ERROR_SUCC)) {
						errorMsg = rsp0.getError_msg() == null ? errorMsg : rsp0.getError_msg();

						this.json = "{result:0,message:'变更失败,原号码释放失败:" + errorMsg + "'" + ",view_phone_num:'" + old_phone_num + "'" + ",operatorState:'" + EcsOrderConsts.OCCUPIEDFLAG_7 + "'}";
						return this.JSON_MESSAGE;
					}
				} catch (Exception e) {
					this.json = "{result:0,message:'变更失败,接口异常,请联系运维检查!'" + ",view_phone_num:'" + old_phone_num + "'" + ",operatorState:'" + EcsOrderConsts.OCCUPIEDFLAG_7 + "'}";
					return this.JSON_MESSAGE;
				}
			}
		}

		// 旧号码释放成功时候，就需要把新号码的查询信息先保存到数据库了
		// 主卡处理
		if (StringUtils.equals(numType, EcsOrderConsts.ZB_CARD_TYPE_ZHU)) {
			// 将新号码设置到主卡字段
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.PHONE_NUM }, new String[] { this.orderPhoneInfo.getPhone_num() });
			OrderPhoneInfoBusiRequest zhukavo = CommonDataFactory.getInstance().getOrderTree(order_id).getPhoneInfoBusiRequest();
			try {
				BeanUtils.copyProperties(zhukavo, phoneInfoMap);
			} catch (Exception e) {
				this.json = "{result:0,message:'变更失败,系统操作异常,请联系运维人员查询后台日志!'" + ",view_phone_num:'" + old_phone_num + "'" + ",operatorState:'" + EcsOrderConsts.OCCUPIEDFLAG_5 + "'}";
				return this.JSON_MESSAGE;
			}
			// 初始化预占信息
			zhukavo.setPhone_num(phone_num);
			zhukavo.setOccupied_msg(ConstsCore.NULL_VAL);
			zhukavo.setOccupiedFlag(EcsOrderConsts.OCCUPIEDFLAG_0);
			zhukavo.setBssOccupiedFlag(EcsOrderConsts.OCCUPIEDFLAG_0);
			zhukavo.setOperatorState(ConstsCore.NULL_VAL);
			zhukavo.setProKey((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + ManagerUtils.genRandowNum(6));
			zhukavo.setOccupiedTime(ConstsCore.NULL_VAL);
			zhukavo.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			zhukavo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			zhukavo.store();
		} else {
			// 副卡处理
			List<OrderPhoneInfoFukaBusiRequest> fukaList = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderPhoneInfoFukaBusiRequests();
			for (OrderPhoneInfoFukaBusiRequest fukavo : fukaList) {
				if (StringUtils.equals(fukavo.getInst_id(), fukaInstId)) {
					try {
						BeanUtils.copyProperties(fukavo, phoneInfoMap);
					} catch (Exception e) {
						this.json = "{result:0,message:'变更失败,系统操作异常,请联系运维人员查询后台日志!'" + ",view_phone_num:'" + old_phone_num + "'" + ",operatorState:'" + EcsOrderConsts.OCCUPIEDFLAG_5 + "'}";
						return this.JSON_MESSAGE;
					}
					// 初始化预占信息
					fukavo.setPhoneNum(phone_num);
					fukavo.setResult_msg(ConstsCore.NULL_VAL);
					fukavo.setOccupiedflag(EcsOrderConsts.OCCUPIEDFLAG_0);
					fukavo.setBss_occupied_flag(EcsOrderConsts.OCCUPIEDFLAG_0);
					fukavo.setOperatorstate(ConstsCore.NULL_VAL);
					fukavo.setProkey((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + ManagerUtils.genRandowNum(6));
					fukavo.setOccupiedTime(ConstsCore.NULL_VAL);
					fukavo.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					fukavo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					fukavo.store();
				}
			}
		}

		// 新号码预占
		if (StringUtils.equals(occupancySysNew, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_1) || StringUtils.equals(occupancySysNew, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_2)) {
			if (!StringUtils.isEmpty(phone_num)) {
				if (StringUtils.equals(numType, EcsOrderConsts.ZB_CARD_TYPE_FU)) {
					params.put("phoneNum", phone_num);
					params.put("fukaInstId", fukaInstId);
					busiComReq.setEnginePath("zteCommonTraceRule.numberPreOccBatchAopFuKa");
				} else {
					busiComReq.setEnginePath("zteCommonTraceRule.numberPreOccupancyAop");
				}
				try {
					BusiCompResponse rsp1 = orderServices.execBusiComp(busiComReq);
					if (!StringUtils.equals(rsp1.getError_code(), ConstsCore.ERROR_SUCC)) {
						errorMsg = rsp1.getError_msg() == null ? errorMsg : rsp1.getError_msg();

						this.json = "{result:0,message:'变更失败,新号码预占失败:" + errorMsg + "'" + ",view_phone_num:'" + phone_num + "'" + ",operatorState:'" + EcsOrderConsts.OCCUPIEDFLAG_5 + "'}";
						return this.JSON_MESSAGE;
					}
				} catch (Exception e) {
					this.json = "{result:0,message:'变更失败,接口异常,请联系运维检查!'" + ",view_phone_num:'" + old_phone_num + "'" + ",operatorState:'" + EcsOrderConsts.OCCUPIEDFLAG_5 + "'}";
					return this.JSON_MESSAGE;
				}
			}
		}

		// 新号码预定
		if (StringUtils.equals(occupancySysNew, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_1) || StringUtils.equals(occupancySysNew, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_2)) {
			if (!StringUtils.isEmpty(phone_num)) {
				if (StringUtils.equals(numType, EcsOrderConsts.ZB_CARD_TYPE_FU)) {
					params.put("phoneNum", phone_num);
					params.put("fukaInstId", fukaInstId);
					busiComReq.setEnginePath("zteCommonTraceRule.numberOccupancyBatchAopFuKa");
				} else {
					busiComReq.setEnginePath("zteCommonTraceRule.numberOccupancyAop");
				}
				try {
					BusiCompResponse rsp2 = orderServices.execBusiComp(busiComReq);
					if (!StringUtils.equals(rsp2.getError_code(), ConstsCore.ERROR_SUCC)) {
						errorMsg = rsp2.getError_msg() == null ? errorMsg : rsp2.getError_msg();

						this.json = "{result:0,message:'变更失败,新号码预定失败:" + errorMsg + "'" + ",view_phone_num:'" + phone_num + "'" + ",operatorState:'" + EcsOrderConsts.OCCUPIEDFLAG_6 + "'}";
						return this.JSON_MESSAGE;
					}
				} catch (Exception e) {
					this.json = "{result:0,message:'变更失败,接口异常,请联系运维检查!'" + ",view_phone_num:'" + old_phone_num + "'" + ",operatorState:'" + EcsOrderConsts.OCCUPIEDFLAG_6 + "'}";
					return this.JSON_MESSAGE;
				}
			}
		}

		// 新号码bss预占
		if (StringUtils.equals(occupancySysNew, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_0) || StringUtils.equals(occupancySysNew, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_1)) {
			if (!StringUtils.isEmpty(phone_num)) {
				if (StringUtils.equals(numType, EcsOrderConsts.ZB_CARD_TYPE_FU)) {
					params.put("phoneNum", phone_num);
					params.put("fukaInstId", fukaInstId);
					busiComReq.setEnginePath("zteCommonTraceRule.numberPreOccupancyBatchFukaBss");
				} else {
					busiComReq.setEnginePath("zteCommonTraceRule.numberPreOccupancyBss");
				}
				try {
					BusiCompResponse rsp3 = orderServices.execBusiComp(busiComReq);
					if (!StringUtils.equals(rsp3.getError_code(), ConstsCore.ERROR_SUCC)) {
						errorMsg = rsp3.getError_msg() == null ? errorMsg : rsp3.getError_msg();

						this.json = "{result:0,message:'变更失败,新号码BSS预占失败:" + errorMsg + "'" + ",view_phone_num:'" + phone_num + "'" + ",operatorState:'" + EcsOrderConsts.OCCUPIEDFLAG_8 + "'}";
						return this.JSON_MESSAGE;
					}
				} catch (Exception e) {
					this.json = "{result:0,message:'变更失败,接口异常,请联系运维检查!'" + ",view_phone_num:'" + old_phone_num + "'" + ",operatorState:'" + EcsOrderConsts.OCCUPIEDFLAG_8 + "'}";
					return this.JSON_MESSAGE;
				}
			}
		}

		// 商城2.0订单号码状态变更通知新商城
		// String order_from =
		// CommonDataFactory.getInstance().getAttrFieldValue(order_id,
		// AttrConsts.ORDER_FROM);
		// if(EcsOrderConsts.ORDER_FROM_100312.equals(order_from)){
		// String msg = this.changePhoneInfoToWYG(phone_num);
		// if(msg.length()>0){
		// this.json = "{result:0,message:'变更号码通知新商城失败,错误描述:"+msg+"'" +
		// ",view_phone_num:'"+phone_num+"'" +
		// ",operatorState:'"+EcsOrderConsts.OCCUPIEDFLAG_2+"'}";
		// return this.JSON_MESSAGE;
		// }
		// }

		// 通知总部
		if (EcsOrderConsts.ORDER_FROM_10003.equals(order_from)) {
			// 调用订单信息同步总部的接口
			this.PreDealInfoToZB();
		}

		this.json = "{result:1,message:'号码变更成功',view_phone_num:'" + phone_num + "',operatorState:'" + EcsOrderConsts.OPERATOR_STATE_2 + "'}";

		return this.JSON_MESSAGE;
	}

	/* 号码变更通知新商城 */
	public String changePhoneInfoToWYG(String phone_num) {
		String msg = "";
		NumberChangeWYGRequest req = new NumberChangeWYGRequest();
		req.setNotNeedReqStrOrderId(this.order_id);
		req.setNumber(phone_num);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		NotifyOrderInfoWYGResponse resp = client.execute(req, NotifyOrderInfoWYGResponse.class);
		if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getResp_code())) {
			msg = resp.getResp_msg();
		}
		return msg;
	}

	/**
	 * iphone6s临时版本需求 确定关联订单逻辑
	 */
	public String iphone6RelevanceOrder() throws Exception {
		try {
			// 预售单id
			String ys_order_id = this.ys_order_id;
			// 正式单id
			String zs_order_id = this.order_id;

			//
			OrderTreeBusiRequest zs_orderTree = CommonDataFactory.getInstance().getOrderTree(zs_order_id);
			OrderTreeBusiRequest ys_orderTree = CommonDataFactory.getInstance().getOrderTree(ys_order_id);
			// 取正式单预收单的外部订单号，在拷贝转兑包数据的时候需要用到
			String zs_out_order_id = zs_orderTree.getOrderExtBusiRequest().getOut_tid();
			String ys_out_order_id = ys_orderTree.getOrderExtBusiRequest().getOut_tid();

			// 校验相互关联的预售单、正式单，是否已经存关联单号
			String ys_related_order_id = CommonDataFactory.getInstance().getAttrFieldValue(ys_order_id, AttrConsts.RELATED_ORDER_ID);
			String zs_related_order_id = CommonDataFactory.getInstance().getAttrFieldValue(zs_order_id, AttrConsts.RELATED_ORDER_ID);
			// 如果其中一个已经存在关联单号，则返回
			if (!StringUtil.isEmpty(zs_related_order_id) || !StringUtil.isEmpty(ys_related_order_id)) {
				this.json = "{result:-1,message:'已经关联过预售单'}";
				return this.JSON_MESSAGE;
			}

			// 校验预售单是否还在预处理环节（B），如果不存预处理环节，则返回
			String currFlowTraceId = CommonDataFactory.getInstance().getOrderTree(ys_order_id).getOrderExtBusiRequest().getFlow_trace_id();
			if (!EcsOrderConsts.DIC_ORDER_NODE_B.equals(currFlowTraceId)) {
				this.json = "{result:-1,message:'预售单不在预处理环节'}";
				return this.JSON_MESSAGE;
			}

			// 校验相互关联的订单，来源是否相同，并且来源为淘宝或总商，如果不符合这个两个条件的其中一个，则返回
			String ys_order_from = CommonDataFactory.getInstance().getAttrFieldValue(ys_order_id, AttrConsts.ORDER_FROM);
			String zs_order_from = CommonDataFactory.getInstance().getAttrFieldValue(zs_order_id, AttrConsts.ORDER_FROM);
			if (!StringUtil.equals(ys_order_from, zs_order_from)) {
				this.json = "{result:-1,message:'关联订单来源不一样'}";
				return this.JSON_MESSAGE;
			}
			if (!(EcsOrderConsts.ORDER_FROM_10001.equals(ys_order_from) || EcsOrderConsts.ORDER_FROM_10003.equals(ys_order_from))) {
				this.json = "{result:-1,message:'订单不来自淘宝或总商'}";
				return this.JSON_MESSAGE;
			}

			// 预售单活动数据拷贝到正式单
			// 如果正式单是新用户订单，刚将预售单的多缴预存款叠加到正式单的多缴预存款中
			String is_new = CommonDataFactory.getInstance().getAttrFieldValue(zs_order_id, AttrConsts.IS_OLD);
			List<AttrFeeInfoBusiRequest> ys_fee_list = CommonDataFactory.getInstance().getOrderTree(ys_order_id).getFeeInfoBusiRequests();
			List<AttrFeeInfoBusiRequest> zs_fee_list = CommonDataFactory.getInstance().getOrderTree(zs_order_id).getFeeInfoBusiRequests();

			AttrFeeInfoBusiRequest ys_djyck_feevo = null;
			AttrFeeInfoBusiRequest zs_djyck_feevo = null;
			if (StringUtil.equals(EcsOrderConsts.IS_OLD_0, is_new)) {
				// 取预收单的多缴预存款
				for (AttrFeeInfoBusiRequest feevo : ys_fee_list) {
					if (StringUtil.equals(EcsOrderConsts.DJYCK_FEE_ITEM_ID, feevo.getFee_item_code())) {
						ys_djyck_feevo = feevo;
						break;
					}
				}
				// 取正式单的多缴预存款
				for (AttrFeeInfoBusiRequest feevo : zs_fee_list) {
					if (StringUtil.equals(EcsOrderConsts.DJYCK_FEE_ITEM_ID, feevo.getFee_item_code())) {
						zs_djyck_feevo = feevo;
						break;
					}
				}
				// 如果预售单多缴预存款不为null
				if (!DataUtil.checkFieldValueNull(ys_djyck_feevo)) {
					// 如果正式单多缴预存款为null，则新增
					if (DataUtil.checkFieldValueNull(zs_djyck_feevo)) {
						AttrFeeInfoBusiRequest feenew = new AttrFeeInfoBusiRequest();
						feenew.setOrder_id(zs_order_id);
						feenew.setInst_id(zs_order_id);
						feenew.setFee_inst_id(iOrderSupplyManager.getSequences());
						feenew.setFee_item_code(ys_djyck_feevo.getFee_item_code());
						feenew.setFee_item_name(ys_djyck_feevo.getFee_item_name());
						feenew.setO_fee_num(ys_djyck_feevo.getO_fee_num());
						feenew.setDiscount_fee(ys_djyck_feevo.getDiscount_fee());
						feenew.setDiscount_reason(ys_djyck_feevo.getDiscount_reason());
						feenew.setN_fee_num(ys_djyck_feevo.getN_fee_num());
						feenew.setFee_category(ys_djyck_feevo.getFee_category());
						feenew.setIs_aop_fee(ys_djyck_feevo.getIs_aop_fee());
						feenew.setMax_relief(ys_djyck_feevo.getMax_relief());
						feenew.setOrigin_mall(ys_djyck_feevo.getOrigin_mall());
						feenew.setDb_action(ConstsCore.DB_ACTION_INSERT);// 操作
						feenew.setIs_dirty(ConstsCore.IS_DIRTY_YES);// 脏数据处理
						feenew.store();
					} else {
						// 否则则叠加
						zs_djyck_feevo.setO_fee_num(zs_djyck_feevo.getO_fee_num() + ys_djyck_feevo.getO_fee_num());
						zs_djyck_feevo.setDiscount_fee(zs_djyck_feevo.getDiscount_fee() + ys_djyck_feevo.getDiscount_fee());
						zs_djyck_feevo.setN_fee_num(zs_djyck_feevo.getN_fee_num() + ys_djyck_feevo.getN_fee_num());
						zs_djyck_feevo.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						zs_djyck_feevo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						zs_djyck_feevo.store();

					}
				} else {
					this.json = "{result:-1,message:'预售单多缴预存款为null'}";
					return this.JSON_MESSAGE;
				}
			}

			// 将预售单的转兑包保存到正式单中
			iOrderSupplyManager.copyzhuanduibao(ys_out_order_id, zs_out_order_id, ys_order_id, zs_order_id);

			// 修改预售单、正式单数据，保存关联单号；预售单关联单号为正式单号，正式单关联单号为预售单号；
			CommonDataFactory.getInstance().updateAttrFieldValue(zs_order_id, new String[] { AttrConsts.RELATED_ORDER_ID }, new String[] { ys_order_id });
			CommonDataFactory.getInstance().updateAttrFieldValue(ys_order_id, new String[] { AttrConsts.RELATED_ORDER_ID }, new String[] { zs_order_id });

			// 将预售单流转至下一环节；
			BusiCompRequest busi = new BusiCompRequest();
			Map queryParams = new HashMap();
			queryParams.put("order_id", ys_order_id);
			busi.setEnginePath("ZteCustomVisitTraceRule.nextStep");
			busi.setOrder_id(ys_order_id);
			busi.setQueryParams(queryParams);
			BusiCompResponse busiCompResp = CommonDataFactory.getInstance().execBusiComp(busi);
			if (!ConstsCore.ERROR_SUCC.equals(busiCompResp.getError_code())) {
				this.json = "{result:-1,message:'预收单流转至下一环节失败'}";
			}
			this.json = "{result:0,message:'关联订单成功，正式单号：" + zs_order_id + "预售单号:" + ys_order_id + "'}";

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 业务办理完成
	 * 
	 * @author ZX
	 * @see 2015-10-20
	 * @return
	 */
	public String busiOffLineHandle() {
		String lockMsg = checkLockUser();
		if (!StringUtils.isEmpty(lockMsg)) {
			this.json = "{result:1,message:'" + lockMsg + "'}";
			return this.JSON_MESSAGE;
		}
		String msg = editSave();
		if (msg != null && msg.length() > 0) {
			String validateMsg = JSONArray.toJSONString(validateMsgList);
			this.json = "{result:1,message:'" + msg + "',validateMsgList:" + validateMsg + "}";
			return this.JSON_MESSAGE;
		}
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String model = orderTree.getOrderExtBusiRequest().getOrder_model();
		String is_aop = orderTree.getOrderExtBusiRequest().getIs_aop();
		String cur_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();

		if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
			rule_id = EcsOrderConsts.BUSI_HANDLE_NEXT_RULE_ID.get(model);
		} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
			rule_id = EcsOrderConsts.BUSI_HANDLE_NEXT_RULE_ID.get(model + "_BSSKL");
		} else {
			rule_id = EcsOrderConsts.BUSI_HANDLE_NEXT_RULE_ID.get(model + "_AOP");
		}
		plan_id = EcsOrderConsts.BUSI_HANDLE_PLAN_ID;
		// 执行开户下一步 message
		executeRule();
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		Utils.commonUnlock(order_id, cur_trace_id, trace_id); // 解锁公共方法

		// 标记附加产品办理成功
		List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance().getOrderTree(order_id).getAttrPackageSubProdBusiRequest();
		if (attrPackageSubProdList != null && attrPackageSubProdList.size() > 0) {
			for (AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList) { // 设置线下办理成功
				subPackage.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2);
				subPackage.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				subPackage.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				subPackage.store();
			}
		}
		// 标记SP产品办理成功
		List<OrderSpProductBusiRequest> OrderSpProductList = CommonDataFactory.getInstance().getOrderTree(order_id).getSpProductBusiRequest();
		if (OrderSpProductList != null && OrderSpProductList.size() > 0) {
			for (OrderSpProductBusiRequest spProd : OrderSpProductList) { // 设置线下办理成功
				spProd.setStatus(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2);
				spProd.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				spProd.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				spProd.store();
			}
		}

		if (!StringUtil.isEmpty(json))
			json = json.replace("status:", "result:").replace(",msg:", ",message:");
		return JSON_MESSAGE;
	}

	public String loadPhoneInfo() {
		this.orderPhoneInfo = CommonDataFactory.getInstance().getOrderTree(order_id).getPhoneInfoBusiRequest();
		return "phone_info";
	}

	/* 多商品 预拣货 */
	public String prePickMore() {
		this.orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		if (!EcsOrderConsts.ORDER_FROM_10062.equals(orderTree.getOrderExtBusiRequest().getOrder_from())) {// 华盛B2B不加载商品项信息
			this.orderItemExtvtlBusiRequests = orderTree.getOrderItemExtvtlBusiRequests();
			if (orderItemExtvtlBusiRequests != null && orderItemExtvtlBusiRequests.size() > 0) {
				for (int i = 0; i < orderItemExtvtlBusiRequests.size(); i++) {
					String goods_type = orderItemExtvtlBusiRequests.get(i).getGoods_type();
					String goods_type_name = SpecUtils.getGoodsTypeNameById(goods_type);
					orderItemExtvtlBusiRequests.get(i).setGoods_type_name(goods_type_name);
				}
			}
		}
		return "prePickMore";
	}

	/* 多商品预拣货保存 */
	public String savePrePickMore() {
		String msg = "";
		try {
			if (!StringUtil.isEmpty(resourceCodeStr)) {
				Map map = new HashMap();
				String[] resourceCodeArr = this.resourceCodeStr.split(",");
				String[] resourceItemsArr = this.resourceItemsStr.split(",");
				for (int j = 0; j < resourceCodeArr.length; j++) {
					String resourceCode = resourceCodeArr[j];
					if (!StringUtil.isEmpty(resourceCode)) {
						String items_id = resourceItemsArr[j];
						map.put(items_id, resourceCode);
					}
				}
				List<OrderItemExtvtlBusiRequest> ordItemExtList = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemExtvtlBusiRequests();
				for (int i = 0; i < ordItemExtList.size(); i++) {
					OrderItemExtvtlBusiRequest orderItemExt = ordItemExtList.get(i);
					String items_id = orderItemExt.getItems_id();
					String new_resource_code = (String) map.get(items_id);
					if (!StringUtil.isEmpty(new_resource_code)) {
						orderItemExt.setResources_code(new_resource_code);
						orderItemExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						orderItemExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						orderItemExt.store();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return msg;
	}

	public String sms3NetSend() {
		// 标记一下,-1是单纯的短信通知
		if (!StringUtils.isEmpty(isAgree))
			valiPhotoLog();
		// 调短信通知
		TacheFact fact = new TacheFact();
		fact.setOrder_id(order_id);
		// RuleTreeExeResp resp =
		// RuleFlowUtil.executeRuleTree(EcsOrderConsts.SMS_3NET_SEND_NEW,
		// EcsOrderConsts.SMS_3NET_SEND_NEW_RULE,
		// fact,false,false,EcsOrderConsts.DEAL_FROM_PAGE);
		PlanRuleTreeExeResp resp = RuleFlowUtil.executePlanRuleTree(EcsOrderConsts.SMS_3NET_SEND_NEW, -1, fact, true, EcsOrderConsts.DEAL_FROM_PAGE, null, null);
		if (!resp.getError_code().equals(ConstsCore.ERROR_SUCC)) {
			String msg = "短信通知发送失败:" + resp.getError_msg();
			this.json = "{result:1,message:'" + msg + "'}";
		} else {
			json = "{'result':0,'message':'短信通知发送成功！'}";
		}
		return JSON_MESSAGE;
	}

	/**
	 * 检查锁定
	 * 
	 * @return
	 */
	public String checkLockStatus() {
		String lockMsg = checkLockUser();
		if (StringUtils.isEmpty(lockMsg)) {
			this.json = "{result:1,message:''}";
		} else {
			this.json = "{result:0,message:'" + lockMsg + "'}";
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 保存订单列表处提交的订单备注
	 * 
	 * @return
	 */
	public String saveOrderComments() {

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		this.insertDealInfo(trace_id, "");// 备注信息在方法的DESC字段
		this.json = "{result:0,message:'备注添加成功" + dealDesc + "'}";
		return this.JSON_MESSAGE;

	}

	// 开户环节流转--新开户
	public String flowDealOpenAcct() {
		//添加开户人员工号和开户时间到es_order_extvtl表中,便于订单报表导出
		AdminUser user = ManagerUtils.getAdminUser();
		String user_id = user.getUsername();
		Calendar date = new GregorianCalendar();
		DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String open_account_time=DF.format(date.getTime());
		if(!StringUtil.isEmpty(user_id)) {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "open_account_userid" }, new String[] { user_id });
		}
		String update_county_extvtl_sql = "update es_order_extvtl set open_account_time = to_date('"+open_account_time+"','yyyy-MM-dd HH24:mi:ss') where order_id='" + order_id + "'";
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		baseDaoSupport.execute(update_county_extvtl_sql);

		// add by wui对象锁处理,避免重复处理调用
		String lock_name = "O" + order_id;
		Map lock = getMutexLock(lock_name);
		synchronized (lock) {
			logger.info("订单：" + order_id + "进入锁定信息！");
			long start = System.currentTimeMillis();
			// add by wui二次抓取，同步调用时验证处理
			String page_trace_id = (String) cache.get(NAMESPACE, CURR_ORDER_CLICK_PAGE + order_id); // 获取进入页面时的环节id
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			String currFlowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id(); // 获取正在处理的环节id
			if (!StringUtil.isEmpty(page_trace_id) && (EcsOrderConsts.DIC_ORDER_NODE_F.equals(page_trace_id) || EcsOrderConsts.DIC_ORDER_NODE_B.equals(page_trace_id))
					&& !currFlowTraceId.equals(page_trace_id)) {
				this.json = "{result:1,message:'正在处理页面为" + page_trace_id + "，实际处理环节为" + currFlowTraceId + "与实际不一致，请确认是否重复执行。',order_id:'" + order_id + "'}";
				removeMutexLock(lock_name);
				return this.JSON_MESSAGE;
			}

			String lockMsg = checkLockUser();
			if (!StringUtils.isEmpty(lockMsg)) {
				this.json = "{result:1,message:'" + lockMsg + "',order_id:'" + order_id + "'}";
				removeMutexLock(lock_name);
				return this.JSON_MESSAGE;
			}
			// 退单状态不允许流转20160516
			if (EcsOrderConsts.REFUND_DEAL_TYPE_02.equals(orderTree.getOrderExtBusiRequest().getRefund_deal_type())) {
				this.json = "{result:1,message:'该订单已退单或退单申请!',order_id:'" + order_id + "'}";
				removeMutexLock(lock_name);
				return this.JSON_MESSAGE;
			}
			long end = System.currentTimeMillis();
			logger.info("flowDealOpenAcct==1===" + order_id + "=====" + (end - start));
			// 每个缓解流程保存
			String msg = "";
			try {
				String curr_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
				ZteResponse response = null;
				String trace_id = null;
				// 调用业务组件
				try {
					CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
					String flag = cacheUtil.getConfigInfo("RULE_ACT_WRITE_FLAG");
					start = System.currentTimeMillis();
					
					if("1".equals(this.orderTree.getOrderExtBusiRequest().getIs_work_custom())){
						//自定义流程
						this.workCutomOrderContinue(EcsOrderConsts.DIC_ORDER_NODE_D);
						
					}else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP.equals(orderTree.getOrderExtBusiRequest().getIs_aop())
							&& EcsOrderConsts.ORDER_MODEL_02.equals(orderTree.getOrderExtBusiRequest().getOrder_model())
							&& EcsOrderConsts.ORDER_FROM_10003.equals(orderTree.getOrderExtBusiRequest().getOrder_from()) && "no".equals(flag)) {
						// AOP开户处理申请
						BusiDealResult openActResp = ordOpenAccountTacheManager.openApplyToAop(order_id);
						if (!StringUtils.equals(openActResp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)) {
							response = new ZteResponse();
							response.setError_code(ConstsCore.ERROR_FAIL);
							response.setError_msg(openActResp.getError_msg());
						} else {
							// AOP开户处理提交
							BusiDealResult dealResult = ordOpenAccountTacheManager.openDealSubmitToAop(order_id);
							if (!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)) {
								// 标记办理失败
								ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1);
								response = new ZteResponse();
								response.setError_code(ConstsCore.ERROR_FAIL);
								response.setError_msg(dealResult.getError_msg());
							} else {
								// 标记办理
								ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
								BusiCompRequest bcr = new BusiCompRequest();
								bcr.setEnginePath("ZteActTraceRule.nextStepAsyn");
								bcr.setOrder_id(order_id);
								bcr.setQueryParams(new HashMap());
								BusiCompResponse resp = CommonDataFactory.getInstance().execBusiComp(bcr);
								if (!ConstsCore.ERROR_SUCC.equals(resp.getError_code())) {
									response = new ZteResponse();
									response.setError_code(ConstsCore.ERROR_FAIL);
									response.setError_msg(resp.getError_msg());
								} else {
									response = new ZteResponse();
									response.setError_code(ConstsCore.ERROR_SUCC);
									response.setError_msg("操作成功");
								}
							}
						}
					}else {
						BusiCompRequest busi = new BusiCompRequest();
						Map queryParams = new HashMap();
						queryParams.put("order_id", this.order_id);
						busi.setEnginePath("enterTraceRule.exec");
						busi.setOrder_id(this.order_id);
						queryParams.put("deal_from", EcsOrderConsts.DEAL_FROM_PAGE);
						queryParams.put("deal_type", dealType);
						queryParams.put("deal_desc", getDealDesc());
						queryParams.put("is_auto", EcsOrderConsts.RULE_EXE_ALL);
						busi.setQueryParams(queryParams);

						/**
						 * 1、处理思路：调用+同步锁方法，控制按串行流程执行
						 * 2、对质检环节特殊处理，当前为质检环节、且数据库也为治检环节，则退出处理
						 */

						response = orderServices.execBusiComp(busi);
					}
					end = System.currentTimeMillis();
					logger.info("flowDealOpenAcct==2===" + order_id + "=====" + (end - start));

				} finally {
					start = System.currentTimeMillis();
					orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
					trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
					end = System.currentTimeMillis();
					logger.info("flowDealOpenAcct==3===" + order_id + "=====" + (end - start));
				}

				start = System.currentTimeMillis();
				if (response != null 
						&& !ConstsCore.ERROR_SUCC.equals(response.getError_code())) {
					msg = response.getError_msg();
					this.json = "{result:1,message:'" + msg + "',order_id:'" + order_id + "'}";
				} else {
					this.json = "{result:0,message:'操作成功',order_id:'" + order_id + "'}";
					Utils.commonUnlock(order_id, curr_trace_id, trace_id); // 解锁公共方法
				}
				end = System.currentTimeMillis();
				logger.info("flowDealOpenAcct==4===" + order_id + "=====" + (end - start));
			} catch (Exception e) {
				e.printStackTrace();
				this.json = "{result:1,message:'" + e.getMessage() + "',order_id:'" + order_id + "'}";
			} finally {
				removeMutexLock(lock_name);
			}
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 静态数据：退单原因code
	 */
	private void ordReturnedReasonList() {
		ordReturnedReasonList = getConsts(StypeConsts.RETURNED_REASON_TYPE);
		if (ordReturnedReasonList == null) {
			ordReturnedReasonList = new ArrayList<Map>();
		}
		Boolean flag = true;
        for (int i = 0; i < ordReturnedReasonList.size(); i++) {
            String key =  ((Map<String, String>) ordReturnedReasonList.get(i)).get("pkey");
            if ("-1".equals(key)) {
                flag = false;
                break;
            }
        }
        if (flag) {
            Map m0 = new HashMap();
            m0.put("pkey", "-1");
            m0.put("pname", "--请选择--");
            ordReturnedReasonList.add(0, m0);
        }
	}
	public String CheckChange(){
	    if(!StringUtil.isEmpty(this.ICCID_INFO)){
	        String check_card_number = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "check_card_number");//卡号校验标志位
            if(StringUtils.isEmpty(check_card_number) || "0".equals(check_card_number)){
                this.json = "{result:0,message:''}";
                return this.JSON_MESSAGE;
            }else{
                this.json = "{result:1,message:''}";//提示语前端处理
                return this.JSON_MESSAGE;
            }
	    }
	    if(!StringUtil.isEmpty(this.terminal_num)){
	        String check_terminal_series = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "check_terminal_series");//终端串号校验标志位
	        if(StringUtils.isEmpty(check_terminal_series) || "0".equals(check_terminal_series)){
	            this.json = "{result:0,message:''}";
	            return this.JSON_MESSAGE;
	        }else{
                this.json = "{result:1,message:''}";//提示语前端处理
                return this.JSON_MESSAGE;
            }
	    }
        this.json = "{result:1,message:''}";
	    return this.JSON_MESSAGE;
	}
	
	public String cleanCheckInfo(){
        if(!StringUtil.isEmpty(this.ICCID_INFO)){
          CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "ICCID","simid","check_card_number" }, new String[] {"","","0"});
        }
        if(!StringUtil.isEmpty(this.terminal_num)){
            OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
            List<OrderInternetBusiRequest> orderInternet = orderTree.getOrderInternetBusiRequest();
            if(orderInternet.size()>0){
                for (int i = 0; i < orderInternet.size(); i++) {
                    OrderInternetBusiRequest orderintentbusi = orderInternet.get(i);
                    orderintentbusi.setObject_esn(ConstsCore.NULL_VAL);
                    orderintentbusi.setObject_id(ConstsCore.NULL_VAL);
                    orderintentbusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
                    orderintentbusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
                    orderintentbusi.store();
              }
            }
            CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "object_esn","check_terminal_series" }, new String[] {"","0"});
        }
        this.json = "{result:1,message:''}";
        return this.JSON_MESSAGE;
    }
	/**
	 * 
	 * <p>Title: 终端串号校验</p>
	 * <p>Description: TODO</p>
	 * @author sgf
	 * @time 2019年3月5日 上午9:35:08
	 * @version 1.0
	 * @return
	 */
	public String terminalcheck() {
	    GroupOrderFixedNetworkCheckReq req = new GroupOrderFixedNetworkCheckReq();
	    GroupOrderApiTerminal groupOrderApiTerminal = new GroupOrderApiTerminal();
	    ApiTerminalCheckResReqVO apiTerminalCheckResReqVO = new ApiTerminalCheckResReqVO();
	    RESOURCES_INFOVO resource_info = new RESOURCES_INFOVO();
	    String terminal_num = this.terminal_num;
	    String old_terminal_num = this.old_terminal_num;
	    String object_name="";
	    try {
            object_name = URLDecoder.decode(this.object_name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
/*        CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "object_esn", "object_id","check_terminal_series","object_name" }, new String[] { this.terminal_num, this.old_terminal_num,"1",this.object_name});
*/
	    if(StringUtils.isEmpty(terminal_num)){//请输入终端串号
	        this.json = "{result:1,message:'请输入终端串号'}";
	        return this.JSON_MESSAGE;
	    }
	    String operatorId = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OPERATOR);//外部操作员--order_provinc_code
	    String procince_code ="36";//省份 浙江省 
        String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);//地市// 4位
        
        String city_id="";
        if (!StringUtils.isEmpty(order_city_code) && order_city_code.length() == 6) {
            city_id = CommonDataFactory.getInstance().getOtherDictVodeValue("city", order_city_code);
        }else{
            city_id= order_city_code;
        }
	    String district = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.DISTRICT_CODE);//县分
	    String channel_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CHA_CODE);//渠道id
	    String channel_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CHANNEL_TYPE);//渠道类型 -- 会取默认配置的
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
            operatorId = essOperInfo.getEss_emp_id();
            channel_id = essOperInfo.getChannel_id();
            channel_type = essOperInfo.getChannel_type();
        }
	    String check_type = "01";//01 可用    04 备机  GX 工单和串码的绑定关系
	    String fix_order_id="";
	    String resources_code = terminal_num;
	    apiTerminalCheckResReqVO.setCHANNEL_ID(channel_id);
	    apiTerminalCheckResReqVO.setCHANNEL_TYPE(channel_type);
	    apiTerminalCheckResReqVO.setCITY(city_id);
	    apiTerminalCheckResReqVO.setCHECK_TYPE(check_type);
	    apiTerminalCheckResReqVO.setPROVINCE(procince_code);
	    apiTerminalCheckResReqVO.setDISTRICT(district);
	    apiTerminalCheckResReqVO.setOPERATOR_ID(operatorId);
	    apiTerminalCheckResReqVO.setFIX_ORDER_ID(fix_order_id);
	    apiTerminalCheckResReqVO.setSOURCE_SYSTEM("BSS");
	    resource_info.setRESOURCES_CODE(resources_code);
	    RESOURCES_INFOVO resource = new RESOURCES_INFOVO();
	    resource.setRESOURCES_CODE(resources_code);
	    List<RESOURCES_INFOVO> list = new ArrayList<RESOURCES_INFOVO>();
	    list.add(resource);
	    apiTerminalCheckResReqVO.setRESOURCES_INFO(list);
	    groupOrderApiTerminal.setAPI_TERMINAL_CHECK_RES_REQ(apiTerminalCheckResReqVO);//第二层
	    req.setUnibssbody(groupOrderApiTerminal);//最外层
	    req.setNotNeedReqStrOrderId(order_id);
	    req.setApi("HardLinkTerminalSer");
	    req.setService_name("apiTerminalCheckRes");
	    req.setSystem_id("fixedTerminal");
	    ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
	    ResourCecenterAppResp resp = client.execute(req, ResourCecenterAppResp.class);
	    if(!StringUtil.isEmpty(resp.getCode())&&StringUtil.equals(resp.getCode(), EcsOrderConsts.INF_RESP_CODE_00000)){
            if(resp.getRespJson().getAPI_TERMINAL_CHECK_RES_RSP()!= null&& resp.getRespJson().getAPI_TERMINAL_CHECK_RES_RSP().getRESOURCES_INFO().size()>0){
                if(!StringUtil.isEmpty(resp.getRespJson().getAPI_TERMINAL_CHECK_RES_RSP().getRESOURCES_INFO().get(0).getRESP_CODE())&&StringUtil.equals(resp.getRespJson().getAPI_TERMINAL_CHECK_RES_RSP().getRESOURCES_INFO().get(0).getRESP_CODE(), EcsOrderConsts.INF_RESP_CODE_0000)){
                    List<OrderInternetBusiRequest> orderInternet = orderTree.getOrderInternetBusiRequest();
                    if(orderInternet.size()>0){
                        for (int i = 0; i < orderInternet.size(); i++) {
                            OrderInternetBusiRequest orderintentbusi = orderInternet.get(i);
                            orderintentbusi.setObject_esn(this.terminal_num);
                            orderintentbusi.setObject_id(this.old_terminal_num);
                            orderintentbusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
                            orderintentbusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
                            orderintentbusi.store();
                      }
                    }
                    CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "object_esn", "object_id","check_terminal_series","object_name" }, new String[] { this.terminal_num, this.old_terminal_num,"1",object_name});
                    this.json = "{result:0,message:'" + "资源验证成功" + "'}";
                    return this.JSON_MESSAGE;
                }else{
                    String codeString = resp.getRespJson().getAPI_TERMINAL_CHECK_RES_RSP().getRESOURCES_INFO().get(0).getRESP_CODE();
                    String code_desc = resp.getRespJson().getAPI_TERMINAL_CHECK_RES_RSP().getRESOURCES_INFO().get(0).getRESP_DESC();
                    this.json = "{result:1,message:'" + code_desc + "'}";
                    return this.JSON_MESSAGE;
                }
            }else {
                this.json = "{result:1,message:'" + "接口调用失败," +resp.getMsg()+ "'}";
                return JSON_MESSAGE;
            }
        }else{
            this.json = "{result:1,message:'" + "接口调用失败," +resp.getMsg()+ "'}";
            return JSON_MESSAGE;
        }
	}
	/**
	 * <p>Title: 成卡卡号校验</p>
	 * <p>Description: TODO</p>
	 * @author sgf
	 * @time 2019年3月5日 上午9:35:56
	 * @version 1.0
	 * @return
	 */
	public String iccidcheck() {
	    String orderid = this.order_id;
	    String iccid_info = this.ICCID_INFO;
	    OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
        GroupOrderCardCheckReq req = new GroupOrderCardCheckReq();
        GroupUnibssBoVO groupUnibssBoVO = new GroupUnibssBoVO();
        CheckCreateCardResultReqVO checkCreateCardResultReqVO = new CheckCreateCardResultReqVO();
        
        if(StringUtils.isEmpty(iccid_info)){//请输入终端串号
            this.json = "{result:1,message:'请输入成卡卡号'}";
            return this.JSON_MESSAGE;
        }
        String operatorId = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OPERATOR);//外部操作员--order_provinc_code
/*        String procince_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_PROVINCE_CODE);//省份  两位*/      
        String procince_code ="36";
        String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);//地市// 4位
        String city_id = "";
        if (!StringUtils.isEmpty(order_city_code) && order_city_code.length() == 6) {
            city_id = CommonDataFactory.getInstance().getOtherDictVodeValue("city", order_city_code);
        }else{
            city_id = order_city_code;
        }
     /*   city_id = "360";*/
        String district = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.DISTRICT_CODE);//县分
        String channel_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CHA_CODE);//渠道id    
/*        String channel_id="36a0187";
*/       
        String channel_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CHANNEL_TYPE);//渠道类型
/*        String channel_type="1010300";
*/      
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
            operatorId = essOperInfo.getEss_emp_id();
            channel_id = essOperInfo.getChannel_id();
            channel_type = essOperInfo.getChannel_type();
        }
        checkCreateCardResultReqVO.setCHANNEL_ID(channel_id);
        checkCreateCardResultReqVO.setCHANNEL_TYPE(channel_type);
        checkCreateCardResultReqVO.setCITY_CODE(city_id);
        checkCreateCardResultReqVO.setDISTRICT_CODE(district);
        checkCreateCardResultReqVO.setPROVINCE_CODE(procince_code);
        checkCreateCardResultReqVO.setSTAFF_ID(operatorId);
        String sys_code = "zjpre";//机构系统编码
        String req_no = order_id;//订单编码
        String iccid  = iccid_info;
        String serial_number="";
        List<OrderInternetBusiRequest> lists = orderTree.getOrderInternetBusiRequest();
        for (int i = 0; i < lists.size(); i++) {
            if("1".equals(lists.get(i).getIs_new())){
                serial_number = lists.get(i).getService_num();
                break;
            }
        }
        String trade_type = "13";//开户校验
        checkCreateCardResultReqVO.setSYS_CODE(sys_code);
        checkCreateCardResultReqVO.setICCID(iccid);
        checkCreateCardResultReqVO.setSERIAL_NUMBER(serial_number);
        checkCreateCardResultReqVO.setTRADE_TYPE(trade_type);
        checkCreateCardResultReqVO.setREQ_NO(req_no);
        groupUnibssBoVO.setCHECK_CREATE_CARD_RESULT_REQ(checkCreateCardResultReqVO);//第二层
        req.setUnibssbody(groupUnibssBoVO);
        req.setNotNeedReqStrOrderId(order_id);
        req.setApi("cardCenter");
        req.setService_name("checkCreateCardResult");
        req.setSystem_id("resourceCenter");
        ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        ResourCecenterAppResp resp = client.execute(req, ResourCecenterAppResp.class);
        if(!StringUtil.isEmpty(resp.getCode())&&StringUtil.equals(resp.getCode(), EcsOrderConsts.INF_RESP_CODE_00000)){
            if(!StringUtil.isEmpty(resp.getRespJson().getCHECK_CREATE_CARD_RESULT_RSP().getRESP_CODE())&&StringUtil.equals(resp.getRespJson().getCHECK_CREATE_CARD_RESULT_RSP().getRESP_CODE(), EcsOrderConsts.INF_RESP_CODE_0000)){
                this.json = "{result:0,message:'" + "校验成功" + "'}";
                CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "ICCID","simid","check_card_number"}, new String[] { this.ICCID_INFO,resp.getRespJson().getCHECK_CREATE_CARD_RESULT_RSP().getIMSI(),"1"});
                return this.JSON_MESSAGE;
            }else{
                String codeString = resp.getRespJson().getCHECK_CREATE_CARD_RESULT_RSP().getRESP_CODE();
                String code_desc = resp.getRespJson().getCHECK_CREATE_CARD_RESULT_RSP().getRESP_DESC();
                this.json = "{result:1,message:'" + code_desc + "'}";
                return this.JSON_MESSAGE;
            }
        }else{
            this.json = "{result:1,message:'" + "接口调用失败:" +resp.getMsg()+ "'}";
            return JSON_MESSAGE;
        }
	}
	/**
	 * add by mahui
	 * 泛智能终端校验
	 * @return
	 */
	public String orderTerminalCheck() {
	    String orderid = this.order_id;
	    String mobile_imei = this.mobile_imei;
	    String element_id = CommonDataFactory.getInstance().getAttrFieldValue(orderid, "element_id");
	    String mobile_type = CommonDataFactory.getInstance().getAttrFieldValue(orderid, "mobile_type");
	    OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
	    
	    OrderTerminalCheckReq req=new OrderTerminalCheckReq();
	    
	    String goods_id = orderTree.getOrderBusiRequest().getGoods_id();
		
	    if(!StringUtils.isEmpty(element_id)) {
			IDaoSupport baseDaoSupport=SpringContextHolder.getBean("baseDaoSupport");
			String sql = "SELECT scheme_id FROM ES_GOODS_ACTION_ELEMENT WHERE ELEMENT_ID = '"+element_id+"' and goods_id = '"+goods_id+"'";
			String scheme_id = baseDaoSupport.queryForString(sql);
			req.setScheme_id(scheme_id);
		}
	    
        
        req.setNotNeedReqStrOrderId(orderid);
        req.setMobile_imei(mobile_imei);
        req.setMobile_type(mobile_type);
        req.setElement_id(element_id);
        
////		如果是自传播的泛智能终端来源
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.ORDER_FROM);
	    String goods_cat = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.GOODS_CAT_ID);
	    ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
	    String save_terminal_cat_id = cacheUtil.getConfigInfo("save_terminal_cat_id");
	    if ("10093".equals(order_from) && StringUtils.isNotEmpty(save_terminal_cat_id) && save_terminal_cat_id.contains(goods_cat)) {
//			取默认值
	    	req.setOperator_id(ZjCommonUtils.getGonghaoInfoByOrderId(orderid).getUser_code());
	    	req.setOffice_id(ZjCommonUtils.getGonghaoInfoByOrderId(orderid).getDept_id());

		}else {
		
			req.setOffice_id(CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.OUT_OFFICE));
			req.setOperator_id(CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.OUT_OPERATOR));

		}
        
    	
        
        if(StringUtils.isEmpty(mobile_imei)){//请输入终端串号
            this.json = "{result:1,message:'请输入终端串号'}";
            return this.JSON_MESSAGE;
        }
        
        ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        OrderTerminalCheckResp resp = client.execute(req, OrderTerminalCheckResp.class);
        if(!StringUtil.isEmpty(resp.getCode())&&StringUtil.equals(resp.getCode(), EcsOrderConsts.INF_RESP_CODE_00000)){
        	this.json = "{result:0,message:'" + "校验成功" + "'}";
        	
            CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "order_terminal_check","object_esn"}, new String[] { "0",mobile_imei});
          
        }else{
            this.json = "{result:1,message:'" + "接口调用失败:" +resp.getMsg()+ "'}";
            CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "order_terminal_check"}, new String[] { "1"});
          
            return JSON_MESSAGE;
        }
        return JSON_MESSAGE;
	}
	
	
	public String showBackDesc() {
		this.ordReturnedReasonList();// 退单描述下拉
		return "order_back_desc";
	}

	// 跳转流程图页面
	public String toAutoFlow() {
		return "auto_flow";
	}

	public Map<String, String> getPhoneQueryMap() {
		return phoneQueryMap;
	}

	public void setPhoneQueryMap(Map<String, String> phoneQueryMap) {
		this.phoneQueryMap = phoneQueryMap;
	}

	public String getSegmentCode() {
		return segmentCode;
	}

	public void setSegmentCode(String segmentCode) {
		this.segmentCode = segmentCode;
	}

	public String getQueryPara_03() {
		return queryPara_03;
	}

	public void setQueryPara_03(String queryPara_03) {
		this.queryPara_03 = queryPara_03;
	}

	public String getQueryPara_04() {
		return queryPara_04;
	}

	public void setQueryPara_04(String queryPara_04) {
		this.queryPara_04 = queryPara_04;
	}

	public Map<String, String> getPhoneInfoMap() {
		return phoneInfoMap;
	}

	public void setPhoneInfoMap(Map<String, String> phoneInfoMap) {
		this.phoneInfoMap = phoneInfoMap;
	}

	public List<Route> getSignRoute() {
		return signRoute;
	}

	public void setSignRoute(List<Route> signRoute) {
		this.signRoute = signRoute;
	}

	public List<Route> getReceiptRoute() {
		return receiptRoute;
	}

	public void setReceiptRoute(List<Route> receiptRoute) {
		this.receiptRoute = receiptRoute;
	}

	public String getLogi_no() {
		return logi_no;
	}

	public void setLogi_no(String logi_no) {
		this.logi_no = logi_no;
	}

	public String getReceipt_no() {
		return receipt_no;
	}

	public void setReceipt_no(String receipt_no) {
		this.receipt_no = receipt_no;
	}

	public String getSign_status() {
		return sign_status;
	}

	public void setSign_status(String sign_status) {
		this.sign_status = sign_status;
	}

	public String getReceipt_status() {
		return receipt_status;
	}

	public void setReceipt_status(String receipt_status) {
		this.receipt_status = receipt_status;
	}

	public String getNeed_receipt() {
		return need_receipt;
	}

	public void setNeed_receipt(String need_receipt) {
		this.need_receipt = need_receipt;
	}

	public List<Map> getOperatorStateList() {
		return operatorStateList;
	}

	public void setOperatorStateList(List<Map> operatorStateList) {
		this.operatorStateList = operatorStateList;
	}

	public String getYs_order_id() {
		return ys_order_id;
	}

	public void setYs_order_id(String ys_order_id) {
		this.ys_order_id = ys_order_id;
	}

	public List<Iphone6sReq> getRelationOrders() {
		return relationOrders;
	}

	public void setRelationOrders(List<Iphone6sReq> relationOrders) {
		this.relationOrders = relationOrders;
	}

	public String getIsShowBindRadio() {
		return isShowBindRadio;
	}

	public void setIsShowBindRadio(String isShowBindRadio) {
		this.isShowBindRadio = isShowBindRadio;
	}

	private static Map mutexLocks = Collections.synchronizedMap(new HashMap());

	public static void setMutexLock(String lockName, HashMap lock) {
		mutexLocks.put(lockName, lock);
	}

	public static Map getMutexLock(String lockName) {
		HashMap lock = (HashMap) mutexLocks.get(lockName);
		if (lock == null) {
			lock = new HashMap();
			mutexLocks.put(lockName, lock);
		}
		return lock;
	}

	public static void removeMutexLock(String lockName) {
		mutexLocks.remove(lockName);
	}

	public static Map getMutexLocks() {
		return mutexLocks;
	}

	public List<OrderItemExtvtlBusiRequest> getOrderItemExtvtlBusiRequests() {
		return orderItemExtvtlBusiRequests;
	}

	public void setOrderItemExtvtlBusiRequests(List<OrderItemExtvtlBusiRequest> orderItemExtvtlBusiRequests) {
		this.orderItemExtvtlBusiRequests = orderItemExtvtlBusiRequests;
	}

	public String getResourceCodeStr() {
		return resourceCodeStr;
	}

	public void setResourceCodeStr(String resourceCodeStr) {
		this.resourceCodeStr = resourceCodeStr;
	}

	public String getResourceItemsStr() {
		return resourceItemsStr;
	}

	public void setResourceItemsStr(String resourceItemsStr) {
		this.resourceItemsStr = resourceItemsStr;
	}

	public String getNumType() {
		return numType;
	}

	public void setNumType(String numType) {
		this.numType = numType;
	}

	public String getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}

	public String getOld_phone_num() {
		return old_phone_num;
	}

	public void setOld_phone_num(String old_phone_num) {
		this.old_phone_num = old_phone_num;
	}

	public List<String> getFuka_phonenum() {
		return fuka_phonenum;
	}

	public void setFuka_phonenum(List<String> fuka_phonenum) {
		this.fuka_phonenum = fuka_phonenum;
	}

	public String getFukaInstId() {
		return fukaInstId;
	}

	public void setFukaInstId(String fukaInstId) {
		this.fukaInstId = fukaInstId;
	}

	public List<OrderPhoneInfoFukaBusiRequest> getOrderPhoneInfoFukaList() {
		return orderPhoneInfoFukaList;
	}

	public void setOrderPhoneInfoFukaList(List<OrderPhoneInfoFukaBusiRequest> orderPhoneInfoFukaList) {
		this.orderPhoneInfoFukaList = orderPhoneInfoFukaList;
	}

	public String getIsChangePhone() {
		return isChangePhone;
	}

	public void setIsChangePhone(String isChangePhone) {
		this.isChangePhone = isChangePhone;
	}

	public List getPhoneChangeStateList() {
		return phoneChangeStateList;
	}

	public void setPhoneChangeStateList(List phoneChangeStateList) {
		this.phoneChangeStateList = phoneChangeStateList;
	}

	public String getSending_type() {
		return sending_type;
	}

	public void setSending_type(String sending_type) {
		this.sending_type = sending_type;
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

	public String getSp_inst_id() {
		return sp_inst_id;
	}

	public void setSp_inst_id(String sp_inst_id) {
		this.sp_inst_id = sp_inst_id;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public List<OrderPhoneInfoFukaBusiRequest> getOrderPhoneInfoFuka() {
		return orderPhoneInfoFuka;
	}

	public void setOrderPhoneInfoFuka(List<OrderPhoneInfoFukaBusiRequest> orderPhoneInfoFuka) {
		this.orderPhoneInfoFuka = orderPhoneInfoFuka;
	}

	public String getOpenAcc_price() {
		return openAcc_price;
	}

	public void setOpenAcc_price(String openAcc_price) {
		this.openAcc_price = openAcc_price;
	}

	private void getPrice(OrderTreeBusiRequest orderTree) {

		this.feeInfoList = orderTree.getFeeInfoBusiRequests();
		for (AttrFeeInfoBusiRequest feevo : this.feeInfoList) {
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
		if (StringUtils.isEmpty(this.deposit_price)) {
			this.deposit_price = "0";
		}
		if (StringUtils.isEmpty(this.openAcc_price)) {
			this.openAcc_price = "0";
		}
		if (orderTree.getPhoneInfoBusiRequest() != null && !StringUtils.equals(EcsOrderConsts.PHONE_CLASS_ID_9, orderTree.getPhoneInfoBusiRequest().getClassId() + "")
				&& !StringUtils.isEmpty(orderTree.getPhoneInfoBusiRequest().getAdvancePay())) {
			this.num_price = (Double.parseDouble(orderTree.getPhoneInfoBusiRequest().getAdvancePay()) / 1000) + "";
		} else {
			this.num_price = "0";
		}
		this.good_price_system = CommonDataFactory.getInstance().getGoodSpec(order_id, null, SpecConsts.PRICE);
	}

	public List<OrderSubPackageList> getSubProductList() {
		return subProductList;
	}

	public void setSubProductList(List<OrderSubPackageList> subProductList) {
		this.subProductList = subProductList;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getDealDescIE8() {
		return dealDescIE8;
	}

	public void setDealDescIE8(String dealDescIE8) {
		this.dealDescIE8 = dealDescIE8;
	}

	public List<Map> getList() {
		return list;
	}

	public void setList(List<Map> list) {
		this.list = list;
	}

	public OrderRealNameInfoBusiRequest getOrderRealNameInfoBusiRequest() {
		return orderRealNameInfoBusiRequest;
	}

	public void setOrderRealNameInfoBusiRequest(OrderRealNameInfoBusiRequest orderRealNameInfoBusiRequest) {
		this.orderRealNameInfoBusiRequest = orderRealNameInfoBusiRequest;
	}

	public String getActive_type() {
		return active_type;
	}

	public void setActive_type(String active_type) {
		this.active_type = active_type;
	}

	public Map<String, String> getCertPhotosMap() {
		return certPhotosMap;
	}

	public void setCertPhotosMap(Map<String, String> certPhotosMap) {
		this.certPhotosMap = certPhotosMap;
	}

	public String getProvinceQueryPara_001() {
		return provinceQueryPara_001;
	}

	public void setProvinceQueryPara_001(String provinceQueryPara_001) {
		this.provinceQueryPara_001 = provinceQueryPara_001;
	}

	public String getProvinceQueryPara_002() {
		return provinceQueryPara_002;
	}

	public void setProvinceQueryPara_002(String provinceQueryPara_002) {
		this.provinceQueryPara_002 = provinceQueryPara_002;
	}

	public String getProvinceQueryPara_003() {
		return provinceQueryPara_003;
	}

	public void setProvinceQueryPara_003(String provinceQueryPara_003) {
		this.provinceQueryPara_003 = provinceQueryPara_003;
	}

	public String getQueryPara_10() {
		return queryPara_10;
	}

	public void setQueryPara_10(String queryPara_10) {
		this.queryPara_10 = queryPara_10;
	}

	public String getQueryPara_21_Keywords() {
		return queryPara_21_Keywords;
	}

	public void setQueryPara_21_Keywords(String queryPara_21_Keywords) {
		this.queryPara_21_Keywords = queryPara_21_Keywords;
	}

	public String getQueryPara_22_NumLevel() {
		return queryPara_22_NumLevel;
	}

	public void setQueryPara_22_NumLevel(String queryPara_22_NumLevel) {
		this.queryPara_22_NumLevel = queryPara_22_NumLevel;
	}

	public String getQueryTypeZbChooseReturnVal() {
		return queryTypeZbChooseReturnVal;
	}

	public void setQueryTypeZbChooseReturnVal(String queryTypeZbChooseReturnVal) {
		this.queryTypeZbChooseReturnVal = queryTypeZbChooseReturnVal;
	}

	public String getQueryPara_24_numRange() {
		return queryPara_24_numRange;
	}

	public void setQueryPara_24_numRange(String queryPara_24_numRange) {
		this.queryPara_24_numRange = queryPara_24_numRange;
	}

	public String getQueryPara_23_prepaidProductCode() {
		return queryPara_23_prepaidProductCode;
	}

	public void setQueryPara_23_prepaidProductCode(String queryPara_23_prepaidProductCode) {
		this.queryPara_23_prepaidProductCode = queryPara_23_prepaidProductCode;
	}

	public String getInfIsZb() {
		return infIsZb;
	}

	public void setInfIsZb(String infIsZb) {
		this.infIsZb = infIsZb;
	}

	public String getSelNumChannel() {
		return selNumChannel;
	}

	public void setSelNumChannel(String selNumChannel) {
		this.selNumChannel = selNumChannel;
	}

	public Page getOrderhisPage() {
		return orderhisPage;
	}

	public void setOrderhisPage(Page orderhisPage) {
		this.orderhisPage = orderhisPage;
	}

	public String getOrderhisPageRowCount() {
		return orderhisPageRowCount;
	}

	public void setOrderhisPageRowCount(String orderhisPageRowCount) {
		this.orderhisPageRowCount = orderhisPageRowCount;
	}

	public String getOpenAccountType() {
		return openAccountType;
	}

	public void setOpenAccountType(String openAccountType) {
		this.openAccountType = openAccountType;
	}

	public Map getOrderDtl() {
		return orderDtl;
	}

	public void setOrderDtl(Map orderDtl) {
		this.orderDtl = orderDtl;
	}

	public String getQueryPara_30_serType() {
		return queryPara_30_serType;
	}

	public void setQueryPara_30_serType(String queryPara_30_serType) {
		this.queryPara_30_serType = queryPara_30_serType;
	}

	public String getOrdercitycode() {
		return ordercitycode;
	}

	public void setOrdercitycode(String ordercitycode) {
		this.ordercitycode = ordercitycode;
	}

	public CrawlerResp getCrawlerResp() {
		return crawlerResp;
	}

	public void setCrawlerResp(CrawlerResp crawlerResp) {
		this.crawlerResp = crawlerResp;
	}

	public List getOrdReturnedReasonList() {
		return ordReturnedReasonList;
	}

	public void setOrdReturnedReasonList(List ordReturnedReasonList) {
		this.ordReturnedReasonList = ordReturnedReasonList;
	}

	public String getReasonDesc() {
		return reasonDesc;
	}

	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}

	public String getOutcall_type_c() {
		return outcall_type_c;
	}

	public void setOutcall_type_c(String outcall_type_c) {
		this.outcall_type_c = outcall_type_c;
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

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public String getShow_flag() {
		return show_flag;
	}

	public void setShow_flag(String show_flag) {
		this.show_flag = show_flag;
	}

	// 回退到订单归档
	public String reBackToVisitNEW() {
		try {
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
			// 退单订单不允许回退，防止回退后删除退单规则执行日志
			if (EcsOrderConsts.REFUND_STATUS_08.equals(orderExt.getRefund_status())// 退单申请
					|| EcsOrderConsts.REFUND_STATUS_01.equals(orderExt.getRefund_status())// 退单确认通过
					|| EcsOrderConsts.REFUND_STATUS_02.equals(orderExt.getRefund_status())// BSS返销
					|| EcsOrderConsts.REFUND_STATUS_03.equals(orderExt.getRefund_status())) {// ESS返销
				this.json = "{result:1,message:'订单已退单或退单申请，不允许回退'}";
				return this.JSON_MESSAGE;
			}
			// 华盛订单不允许回退
			String platType = orderExt.getPlat_type();
			if (StringUtils.equals(EcsOrderConsts.PLAT_TYPE_10061, platType)) {
				this.json = "{result:1,message:'华盛订单不允许回退'}";
				return this.JSON_MESSAGE;
			}

			String lockMsg = checkLockUser();
			if (!StringUtils.isEmpty(lockMsg)) {
				this.json = "{result:1,message:'" + lockMsg + "'}";
				return this.JSON_MESSAGE;
			}
			/*
			 * if (EcsOrderConsts.WRITE_CARD_STATUS_5.equals(orderTree.
			 * getOrderExtBusiRequest().getWrite_card_status())) { this.json =
			 * "{result:1,message:'写卡成功,不能回退'}"; return this.JSON_MESSAGE; }
			 */

			BusiCompResponse response = ordFlowManager.reBackToVisitNEW(order_id);
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			String action_url = this.getActionUrl(orderTree.getOrderExtBusiRequest().getFlow_trace_id(), order_id);
			this.json = "{result:0,message:'回退成功',action_url:'" + action_url + "'}";
		} catch (Exception ex) {
			ex.printStackTrace();
			this.json = "{result:1,message:'回退失败'}";
		}
		return JSON_MESSAGE;
	}

	public String nextStep() {
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String currFlowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		if (!StringUtil.isEmpty(first_load) && StringUtil.equals(first_load, "yes") && !StringUtil.isEmpty(currFlowTraceId) && StringUtil.equals(currFlowTraceId, "D")) {
			updateParam = new HashMap<String, String>();
			updateParam.put("phone_num", CommonDataFactory.getInstance().getAttrFieldValue(order_id, "phone_num"));
			updateParam.put("iccid", CommonDataFactory.getInstance().getAttrFieldValue(order_id, "ICCID"));
			updateParam.put("receiving_order_id", CommonDataFactory.getInstance().getAttrFieldValue(order_id, "bssOrderId"));
			return "openAccount_update";
		}
		try {
			if (!StringUtil.isEmpty(currFlowTraceId) && StringUtil.equals(currFlowTraceId, "D")) {
				String phone_num = Const.getStrValue(updateParam, "phone_num");
				String iccid = Const.getStrValue(updateParam, "iccid");
				String receiving_order_id = Const.getStrValue(updateParam, "receiving_order_id");
				if (!StringUtils.isEmpty(phone_num)) {
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "phone_num" }, new String[] { phone_num });
				} else {
					this.json = "{result:1,message:'业务号码不能为空!'}";
					return JSON_MESSAGE;
				}
				iccid = iccid.replaceAll(" ", "");
				if (!StringUtils.isEmpty(iccid)) {
					if (iccid.length() == 20) {
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "ICCID" }, new String[] { iccid });
					} else {
						this.json = "{result:1,message:'ICCID必须是20位,并且不能包含空格!'}";
						return JSON_MESSAGE;
					}
				} else {
					this.json = "{result:1,message:'ICCID不能为空!'}";
					return JSON_MESSAGE;
				}
				if (!StringUtil.isEmpty(receiving_order_id)) {
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "bssOrderId", "active_no" }, new String[] { receiving_order_id, receiving_order_id });
				}
			}
			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			RuleTreeExeReq req = new RuleTreeExeReq();
			TacheFact fact = new TacheFact();

			fact.setOrder_id(order_id);
			req.setRule_id(rule_id);
			req.setFact(fact);
			req.setCheckAllRelyOnRule(false);
			req.setCheckCurrRelyOnRule(false);
			RuleTreeExeResp rsp = client.execute(req, RuleTreeExeResp.class);
			if (!StringUtil.equals(rsp.getError_code(), "1")) {
				orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				currFlowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
				String action_url = getActionUrl(currFlowTraceId, orderTree.getOrder_id());
				logger.info(action_url);
				this.json = "{result:0,message:'操作成功',action_url:'" + action_url + "'}";
			} else {
				this.json = "{result:1,message:'" + rsp.getError_msg() + "'}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.json = "{result:1,message:'操作失败！'}";
		}

		return JSON_MESSAGE;
	}

	public String getAdsl_account() {
		return adsl_account;
	}

	public void setAdsl_account(String adsl_account) {
		this.adsl_account = adsl_account;
	}

	public String getAdsl_number() {
		return adsl_number;
	}

	public void setAdsl_number(String adsl_number) {
		this.adsl_number = adsl_number;
	}

	public String getUser_kind() {
		return user_kind;
	}

	public void setUser_kind(String user_kind) {
		this.user_kind = user_kind;
	}

	public String getExch_code() {
		return exch_code;
	}

	public void setExch_code(String exch_code) {
		this.exch_code = exch_code;
	}

	public String getAdsl_grid() {
		return adsl_grid;
	}

	public void setAdsl_grid(String adsl_grid) {
		this.adsl_grid = adsl_grid;
	}

	public String getAppt_date() {
		return appt_date;
	}

	public void setAppt_date(String appt_date) {
		this.appt_date = appt_date;
	}

	public String getAdsl_addr() {
		return adsl_addr;
	}

	public void setAdsl_addr(String adsl_addr) {
		this.adsl_addr = adsl_addr;
	}

	public String getUser_address() {
		return user_address;
	}

	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}

	public String getCounty_id() {
		return county_id;
	}

	public void setCounty_id(String county_id) {
		this.county_id = county_id;
	}

	public String getModerm_id() {
		return moderm_id;
	}

	public void setModerm_id(String moderm_id) {
		this.moderm_id = moderm_id;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	
	
	public Map<String, Object> getIntentDetail() {
		return intentDetail;
	}

	public void setIntentDetail(Map<String, Object> intentDetail) {
		this.intentDetail = intentDetail;
	}

	/**
	 * 意向单详情页面+订单信息
	 * 
	 * @return
	 */
	public String intentOrderDetails() {
		try {
			intentDetail = ordWorkManager.getIntentDetail(order_id.trim());
			if("1".equals(intentDetail.get("exist")+"" )){
				this.order_detail_view();
			}
		} catch (Exception e) {
			json = "{result:1,message:'意向单详情查询失败" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return "order_detail_view_intent";
	}
	

	public String getTemp_adsl_addr_desc() {
		return temp_adsl_addr_desc;
	}

	public void setTemp_adsl_addr_desc(String temp_adsl_addr_desc) {
		this.temp_adsl_addr_desc = temp_adsl_addr_desc;
	}

	public String getDevic_gear() {
		return devic_gear;
	}

	public void setDevic_gear(String devic_gear) {
		this.devic_gear = devic_gear;
	}

	public String getSale_mode() {
		return sale_mode;
	}

	public void setSale_mode(String sale_mode) {
		this.sale_mode = sale_mode;
	}

	public String getIs_done_active() {
		return is_done_active;
	}

	public void setIs_done_active(String is_done_active) {
		this.is_done_active = is_done_active;
	}

	public String getKingcard_status() {
		return kingcard_status;
	}

	public void setKingcard_status(String kingcard_status) {
		this.kingcard_status = kingcard_status;
	}

	public String getMainnumber() {
		if(!StringUtil.isEmpty(order_id)){
			mainnumber = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "mainnumber");
		}
		return mainnumber;
	}

	public void setMainnumber(String mainnumber) {
		this.mainnumber = mainnumber;
	}

	/**
	 * 获取自定义流程处理页面
	 * @return
	 * @throws Exception
	 */
	private String getWorkCustomUrl() throws Exception{
		//默认的基础页面
		String url = "/shop/admin/ordAuto!getBasicBackNodeUrl.do";
		
		ES_WORK_CUSTOM_NODE_INS pojo = new ES_WORK_CUSTOM_NODE_INS();
		pojo.setOrder_id(this.getOrder_id());
		pojo.setIs_curr_step("1");
		
		List<ES_WORK_CUSTOM_NODE_INS> inses = this.workCustomCfgManager.qryInsList(pojo , null);
		
		if(inses!=null && inses.size()>0 
				&& StringUtils.isNotEmpty(inses.get(0).getDeal_url())){
			//取当前环节配置的处理页面
			return inses.get(0).getDeal_url();
		}
		
		if (url != null && url.indexOf("?") != -1) {
			url += "&order_id=" + order_id;
		} else {
			url += "?order_id=" + order_id;
		}
		
		if (!StringUtil.isEmpty(is_order_exp) && "1".equals(is_order_exp)) {
			url += "&is_order_exp=" + is_order_exp;
		}
		
		if (!StringUtil.isEmpty(query_type)) {
			url += "&query_type=" + query_type;
		}
		
		return url;
	}
    /**
     * <p>Title: flowDealMK</p>
     * <p>Description: 库管环境审核通过 DOTO</p>
     * @author sgf
     * @time 2019年4月27日 下午3:51:18
     * @version 1.0
     * @return
     */
    @SuppressWarnings("unchecked")
    public String flowDealMK() {
    	
    	String orderid = this.order_id;
		String guhuaNumber = this.guhua_number;
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		//固话号码插入es_order_extvtl表
		if(!StringUtils.isEmpty(guhuaNumber) && !StringUtils.isEmpty(orderid)) {
			String sql = "update es_order_extvtl set guhua_num  = '" +guhuaNumber +"' where order_id = '"+orderid + "' and source_from = 'ECS'";
			baseDaoSupport.execute(sql);
		}
    	
    	
        String lockMsg = checkLockUser();// checkLockUser();
        if (!StringUtils.isEmpty(lockMsg)) {
            this.json = "{result:1,message:'" + lockMsg + "'}";
            return this.JSON_MESSAGE;
        }
        String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
        String goods_cat = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);
        String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();

        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String save_group_cat_id = cacheUtil.getConfigInfo("save_group_cat_id");
        if("MK".equals(trace_id)&&"10093".equals(order_from) && StringUtils.isNotEmpty(save_group_cat_id) && save_group_cat_id.contains(goods_cat)){//来源和商品小类
 
            	String check_card_number = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "check_card_number");//卡号校验标志位
                String check_terminal_series = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "check_terminal_series");//终端串号校验标志位
                if(StringUtils.isEmpty(check_card_number) || "0".equals(check_card_number)){
                    this.json = "{result:1,message:'请校验成卡卡号'}";
                    return this.JSON_MESSAGE;
                }
                if(StringUtils.isEmpty(check_terminal_series) || "0".equals(check_terminal_series)){
                    this.json = "{result:1,message:'请校验终端串号'}";
                    return this.JSON_MESSAGE;
                }  
                 
        }
        
        String save_terminal_cat_id = cacheUtil.getConfigInfo("save_terminal_cat_id");
        if ("MK".equals(trace_id)&&"10093".equals(order_from) && StringUtils.equals(save_terminal_cat_id, goods_cat) ) {
        	 String order_terminal_check = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "order_terminal_check");//终端串号
             if(StringUtils.isEmpty(order_terminal_check) || "1".equals(order_terminal_check)){
                 this.json = "{result:1,message:'请校验泛智能终端串号'}";
                 return this.JSON_MESSAGE;
             } 
		}
	
        if (!StringUtil.isEmpty(shipping_company) && shipping_company != null) {
			String[] str = new String[1];
			str[0] = shipping_company;
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "shipping_company" }, str);
		}
        String cur_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
        if("1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())){
        	String page_trace_id = (String) cache.get(NAMESPACE, CURR_ORDER_CLICK_PAGE + order_id); // 获取进入页面时的环节id
        	final String lock_flow_deal = LOCK_ORDER_FLOW_DEAL + order_id;
        	String lock_name = "O" + order_id;
			//处理自定义流程
			try{
				//执行自定义流程
				this.workCutomOrderContinue(page_trace_id);
				
				//重新获取订单树
				OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(this.order_id);
				
				//下一环节页面
				String action_url = this.getWorkCustomUrl();
				
				this.json = "{result:9,message:'操作成功',action_url:'" + action_url + "'}";
			}catch(Exception e) {
				this.json = "{result:1,message:'操作失败，错误信息:" + e.getMessage() + "'}";
				e.printStackTrace();
			} finally {
				cache.delete(NAMESPACE, lock_flow_deal);
				removeMutexLock(lock_name);
			}
		}else{
			try{
		           String Mk_plan_id =  cacheUtil.getConfigInfo("Mk_plan_id");//90000000000000202
		           plan_id = Mk_plan_id;//库管环节的审核planId
		           //执行订单库管环节的审核
		           exeInventoryKeeperPlan();
		        }catch(Exception e){
		            this.json = "{result:1,message:'"+e.getMessage()+"'}";
		        }finally{
		            // 获取下一环节环节
		            orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		            trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		            Utils.commonUnlock(order_id, cur_trace_id, trace_id); // 解锁公共方法
		        }
		}
        

        if (!StringUtil.isEmpty(json))
            json = json.replace("status:", "result:").replace(",msg:", ",message:");
        return JSON_MESSAGE;
    }
    public String exeInventoryKeeperPlan() {
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String Mk_plan_id =  cacheUtil.getConfigInfo("Mk_plan_id");//90000000000000202
        try {
            BusiCompResponse response = null;
            try {
                PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
                TacheFact fact = new TacheFact();
                fact.setOrder_id(this.order_id);
                req.setPlan_id(Mk_plan_id);//待补充的
                req.setFact(fact);
                req.setDeleteLogs(false);// 不删除日志，不允许二次操作
                req.setAuto_exe(-1);
                PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
                response = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
            } catch (Exception ee) {
                throw ee;
            }
            OrderHandleLogsReq req = new OrderHandleLogsReq();
            String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
            req.setOrder_id(order_id);
            req.setFlow_id(flow_id);
            req.setFlow_trace_id("MK");
            req.setComments("库管审核通过");
            req.setHandler_type(Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
            req.setType_code("o_sure");
            this.ordFlowManager.insertOrderHandLog(req);
            orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
            String trace_id_u = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
            String action_url = getActionUrl(trace_id_u, orderTree.getOrder_id());
            if (ConstsCore.ERROR_SUCC.equals(response.getError_code())) {
/*                this.json = "{status:" + response.getError_code() + ",msg:'操作成功',action_url:'" + action_url + "'}";*/
                  this.json = "{status:9,msg:'操作成功',action_url:'" + action_url + "'}";
            } else {
                this.json = "{status:" + response.getError_code() + ",msg:'操作失败【" + response.getError_msg() + "】',action_url:'" + action_url + "'}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.json = "{status:1,msg:'执行失败'}";
        }
        return JSON_MESSAGE;
    }
    // 回退到库管环节
    public String reBackToInventoryKeeper() {
        try {
            orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
            OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
            // 退单订单不允许回退，防止回退后删除退单规则执行日志
            if (EcsOrderConsts.REFUND_STATUS_08.equals(orderExt.getRefund_status())// 退单申请
                    || EcsOrderConsts.REFUND_STATUS_01.equals(orderExt.getRefund_status())// 退单确认通过
                    || EcsOrderConsts.REFUND_STATUS_02.equals(orderExt.getRefund_status())// BSS返销
                    || EcsOrderConsts.REFUND_STATUS_03.equals(orderExt.getRefund_status())) {// ESS返销
                this.json = "{result:1,message:'订单已退单或退单申请，不允许回退'}";
                return this.JSON_MESSAGE;
            }
            // 华盛订单不允许回退
            String platType = orderExt.getPlat_type();
            if (StringUtils.equals(EcsOrderConsts.PLAT_TYPE_10061, platType)) {
                this.json = "{result:1,message:'华盛订单不允许回退'}";
                return this.JSON_MESSAGE;
            }

            String lockMsg = checkLockUser();
            if (!StringUtils.isEmpty(lockMsg)) {
                this.json = "{result:1,message:'" + lockMsg + "'}";
                return this.JSON_MESSAGE;
            }
            String flowStrings = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
            BusiCompResponse response = ordFlowManager.reBackToInventoryKeeper(order_id,"MK");
            orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
            String action_url = this.getActionUrl(orderTree.getOrderExtBusiRequest().getFlow_trace_id(), order_id);
            OrderHandleLogsReq req = new OrderHandleLogsReq();
            String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
            req.setOrder_id(order_id);
            req.setFlow_id(flow_id);
            req.setFlow_trace_id(flowStrings);
            req.setComments("回退");
            req.setHandler_type(Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
            req.setType_code("o_bake");
            this.ordFlowManager.insertOrderHandLog(req);
            this.json = "{result:0,message:'回退成功',action_url:'" + action_url + "'}";
        } catch (Exception ex) {
            ex.printStackTrace();
            this.json = "{result:1,message:'回退失败'}";
        }
        return JSON_MESSAGE;
    }
    public List<Map> getTermain_list() {
        return Termain_list;
    }

    public void setTermain_list(List<Map> termain_list) {
        Termain_list = termain_list;
    }

	public Map getTopSeedInfoMap() {
		return topSeedInfoMap;
	}

	public void setTopSeedInfoMap(Map topSeedInfoMap) {
		this.topSeedInfoMap = topSeedInfoMap;
	}
}
