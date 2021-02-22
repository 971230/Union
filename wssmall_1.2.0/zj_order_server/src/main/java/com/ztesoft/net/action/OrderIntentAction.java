package com.ztesoft.net.action;

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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import params.order.req.OrderHandleLogsReq;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.workCustom.po.ES_ORDER_INTENT;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_NODE_INS;
import zte.net.iservice.ILogsServices;
import zte.net.iservice.IOrderServices;
import zte.net.iservice.IRegionService;
import zte.params.order.req.OrderHandleLogsListReq;
import zte.params.order.resp.OrderHandleLogsListResp;
import zte.params.region.req.RegionsListReq;
import zte.params.region.resp.RegionsListResp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.IntentUtil;
import com.ztesoft.net.ecsord.params.ecaop.req.O2OStatusUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.O2OStatusUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.vo.MSGPKGVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.PKGBODYVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.PKGHEADVO;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Regions;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.OrderQueryParams;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IOrdIntentManager;
import com.ztesoft.net.service.IOrdWorkManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomCfgManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomEngine;


import commons.CommonTools;
import consts.ConstsCore;
import params.order.req.OrderHandleLogsReq;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.workCustom.po.ES_ORDER_INTENT;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_NODE_INS;
import zte.net.ecsord.params.workCustom.po.WORK_CUSTOM_FLOW_DATA;
import zte.net.iservice.ILogsServices;
import zte.net.iservice.IOrderServices;
import zte.net.iservice.IRegionService;
import zte.params.order.req.OrderHandleLogsListReq;
import zte.params.order.resp.OrderHandleLogsListResp;
import zte.params.region.req.RegionsListReq;
import zte.params.region.resp.RegionsListResp;

/**
 * @see 意向单处理相关
 * @version 2018年6月15日
 */
public class OrderIntentAction extends WWAction {

	private static Logger logger = Logger.getLogger(OrderIntentAction.class);
	@Resource
	private IEcsOrdManager ecsOrdManager;

	@Resource
	private ILogsServices logsServices;

	@Resource
	private IOrdWorkManager ordWorkManager;

	@Resource
	private IOrdIntentManager ordIntentManager;

	@Resource
	private IOrderServices orderServices;

	@Resource
	private IWorkCustomCfgManager workCustomCfgManager;
	@Resource
	private IWorkCustomEngine workCustomEngine;
	
	@Resource
	private ICacheUtil cacheUtil;
	
	@Resource
	private IRegionService regionService;
	
	@Resource
	private IDcPublicInfoManager dcPublicInfoManager;

	private static final long serialVersionUID = 1L;
	private String order_id;
	private OrderQueryParams params;
	private String query_type;
	private String type;
	private String remarks;

	private List<Map> dc_MODE_REGIONList;
	private List<Map> order_from_list;
	private List<Map> bee_action_query_list;

	private Map<String, Object> intentDetail;// 意向单详情
	private List<Map<String, Object>> intentLogs;// 意向单操作记录
	private List<Map<String, Object>> intentStatusList;// 意向单状态
	private List<Map<String, Object>> countyList;// 县分列表
	
	private List<Map<String, Object>> intentTopSeedProLineList;// 顶级种子专业线
	private List<Map<String, Object>> intentTopSeedTypeList;// 顶级种子类型
	private List<Map<String, Object>> intentTopSeedGroupIdList;// 顶级种子分组
	

	private Map<String, Object> orderDetail;// 订单详情，针对订单领取新页面
	private List<OrderHandleLogsReq> orderLogs;// 订单操作记录

	private List<Map<String, Object>> contactResultsList;// 意向单客户联系结果列表
	private String contactResults;// 意向单客户联系结果
	private String contactSecondResults;// 意向单客户联系结果
	private String checkText;// 意向单客户联系结果
	private String checkText2;// 意向单客户联系结果
	private String contactDetails;// 意向单客户联系说明
	private Map<String, Object> acceptDetail;// 预受理客户信息
	private String acceptName;
	private String acceptCert;
	private String acceptAddr;

	private String is_work_custom;

	private String order_county_code;
	private String statement_work;
	private ES_ORDER_INTENT intent;

	private String isSZT;

	private String frist_id;
	
	private String sms_id;
	private String sms_template;
	private String sms_template_new;
	private String ship_tel;
	private String sms_open;//短信开关标记
	private List<Map<String, Object>> smsStatusList;// 短信模版状态
	private List<Map<String, Object>> smsLevelList;// 短信类别
	private Map<String, Object> smsDetail;// 短信详情
	private List<Map<String, Object>> smsLogs;// 短信操作记录
	private List<Map<String, Object>> smsSendLogs;// 意向单操作记录
	/********************************************************************************************/
	
	private List<Regions> regionList;//地市
	private List<Map> order_type_list;//订单状态
	
	private String dealDesc;
	private String returnedReasonCode;
	private String applyFrom;
	

	/**
	 * 意向单处理页面
	 * 
	 * @return
	 */
	public String intentHandleForm() {
		dc_MODE_REGIONList = ecsOrdManager.getDcSqlByDcName("DC_MODE_REGION");
		order_from_list = ecsOrdManager.getDcSqlByDcName("ORDER_FROM");
		for (int i = 0; i < order_from_list.size(); i++) {
			String key = (String) order_from_list.get(i).get("codea");
			if (!"intent".equals(key)) {
				order_from_list.remove(i);
				i--;
			}
		}
		intentStatusList = ordWorkManager.getIntentStatusList("handle");
		
		intentTopSeedProLineList = ordWorkManager.getTopSeedProfessionalLine("query");
		intentTopSeedTypeList = ordWorkManager.getTopSeedType("query");
		intentTopSeedGroupIdList = ordWorkManager.getTopSeedGroupId("query");
		
		if (params == null) {
			params = new OrderQueryParams();
		}
		countyList = ordWorkManager.getIntentCountyList(params.getOrder_city_code());
		this.webpage = ordWorkManager.queryIntent(this.params, this.getPage(), this.getPageSize());

		return "intentHandle";
	}
	/**
	 * 意向单查询页面
	 * 
	 * @return
	 */
	public String intentHandleQuery() {
		order_from_list = ecsOrdManager.getDcSqlByDcName("ORDER_FROM");

		for (int i = 0; i < order_from_list.size(); i++) {
			String key = (String) order_from_list.get(i).get("codea");
			if (!"intent".equals(key)) {
				order_from_list.remove(i);
				i--;
			}
		}

		intentStatusList = ordWorkManager.getIntentStatusList("query");
		intentTopSeedProLineList = ordWorkManager.getTopSeedProfessionalLine("query");
		intentTopSeedTypeList = ordWorkManager.getTopSeedType("query");
		intentTopSeedGroupIdList = ordWorkManager.getTopSeedGroupId("query");

		isSZT = ordIntentManager.isSZT(order_id);
		return "intentHandleQuery";
	}
	
	/**
	 * 总部蜂行动定时任务手动执行
	 * @throws Exception 
	 */
	public String beeActionIntenCreate() throws Exception{
		//IntentUtil intentUtil = new IntentUtil();
		String result=ordIntentManager.beeActionHandle(order_id);
		//String result=intentUtil.doneIntent(order_id);
		logger.info("-----------总部蜂行动訂單手动處理-----------");
		this.webpage =  this.ecsOrdManager.queryBeeActionIntentMidList(this.getPage(), this.getPageSize(), params);	
		this.listRegions();
		this.json = "{result:0,message:'"+result+"'}";
		return this.JSON_MESSAGE;
	}
	
	/**
	 * 总部蜂行动订单查询列表页面
	 * @return
	 * @throws Exception 
	 */
	public String beeActionOrderQuery() throws Exception {
		this.webpage =  this.ecsOrdManager.queryBeeActionIntentMidList(this.getPage(), this.getPageSize(), params);	
		this.listRegions();
		return "bee_action_order_query";
	}
	
	
	
	/**
	 * 短信异步
	 * @return
	 */
	public String smsAsysSend() throws Exception {
		this.webpage =  this.ecsOrdManager.queryMessageSendMidList(this.getPage(), this.getPageSize(), params);	
		return "sms_send_list";
	}
	
	/**
	 * 短信异步手动执行
	 * @return
	 */
	
	public String smsAsysSendByBtn() throws Exception {
		String result=ordIntentManager.smsSendHandle(sms_id);
		this.webpage =  this.ecsOrdManager.queryMessageSendMidList(this.getPage(), this.getPageSize(), params);	
		this.json = "{result:0,message:'"+result+"'}";
		return this.JSON_MESSAGE;
	}
	
	
	
	
	

	public String intentQueryTable() {
		System.out.println( this.getPage()+this.getPageSize()+"------------------------------------->");
		isSZT = ordIntentManager.isSZT(order_id);
		this.webpage = ordWorkManager.intentHandleQuery(this.params, this.getPage(), this.getPageSize());

		return "intent_query_table";
	}
	//判断走新老收单
	public String neworOldClose() {

		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		StringBuffer sql_Intent=new  StringBuffer();
		sql_Intent.append("select a.instance_id from ES_WORK_CUSTOM_NODE_INS a where a.order_id='").append(order_id).append("' and a.node_code in ('isResourceRelease','refund_money') ");
		String instance_id=baseDaoSupport.queryForString(sql_Intent.toString());
		
		if(!(instance_id==null||"".equals(instance_id))) {
			//新增退单流程后的泛智能退单
			this.json="{status:0,msg:'泛智能终端退单'}";
		}else {
			//以往订单、非泛智能退单
			this.json="{status:1,msg:'以往订单、非泛智能退单'}";
		}
		return this.JSON_MESSAGE;
	}
	
	//泛智能终端退单
	@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
	public String newCloseOrder() {
		Map map_param = new HashMap();
		map_param.put("dealDesc", this.statement_work);
		map_param.put("returnedReasonCode", this.returnedReasonCode);
		map_param.put("applyFrom", this.applyFrom);
		
		String json_param = JSON.toJSONString(map_param);
		String node_code="";
		StringBuffer cfg_sql=new StringBuffer();
		cfg_sql.append("select distinct a.cfg_id from ES_WORK_CUSTOM_NODE_INS a where a.order_id='").append(order_id).append("' ");
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		String cfg_id=baseDaoSupport.queryForString(cfg_sql.toString());
		String flow_ids = cacheUtil.getConfigInfo("is_mobile_release");
		//判断是否有未完成或处理成功的工单，不允许退单
		String sql = "select * from es_work_order where order_id='" + this.order_id.trim() + "' and status in('0','1')";
		List list = baseDaoSupport.queryForList(sql);
		if (list!=null&&list.size() > 0) {
			this.json = "{status:1,msg:'有未完成或处理成功工单，不允许退单'}";
			return this.JSON_MESSAGE;
		}
		if("9372".equals(cfg_id)) {
			
			//泛智能终端是否走支付判断环节
			StringBuffer sql_Intent=new  StringBuffer();
			sql_Intent.append("select a.instance_id from ES_WORK_CUSTOM_NODE_INS a where a.order_id='").append(this.order_id).append("' and a.node_code ='judge_pay' ");
			String instance_id=baseDaoSupport.queryForString(sql_Intent.toString());
			if(!(instance_id==null||"".equals(instance_id))) {
				node_code = "judge_pay";
			}else {
				node_code = "refund_money";
			}
		}else if(flow_ids.indexOf(cfg_id)>=0){
			//退单号码资源释放
			node_code = "isResourceRelease";
		}
		
		
		Map retMap = new HashMap();
		
		try {
			if(StringUtils.isEmpty(this.statement_work)){     
				throw new Exception("请填写订单备注！");
			}
			
			this.workCustomEngine.gotoNode(this.order_id, node_code,"");
			
			WORK_CUSTOM_FLOW_DATA flow_data = this.workCustomEngine.runNodeManualByCode(order_id, 
					node_code, true, "", "", json_param);
			
			if(ConstsCore.ERROR_FAIL.equals(flow_data.getRun_result()))
				throw new Exception(flow_data.getRun_msg());
			
			//添加退单人员工号和退单时间到es_order_extvtl表中,便于订单报表导出
			AdminUser user = ManagerUtils.getAdminUser();
			Calendar date = new GregorianCalendar();
			DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String refund_time=DF.format(date.getTime());
			String user_id = user.getUsername();
			if(!StringUtil.isEmpty(user_id)) {
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "refund_userid" }, new String[] { user_id });
				}
			String update_county_extvtl_sql = "update es_order_extvtl set refund_time = to_date('"+refund_time+"','yyyy-MM-dd HH24:mi:ss') where order_id='" + order_id + "'";
			baseDaoSupport.execute(update_county_extvtl_sql);
			
			retMap.put("status", "0");
			retMap.put("msg", "退单成功！");
		} catch (Exception e) {
			retMap.put("status", "1");
			retMap.put("msg", e.getMessage());
		}
		
		this.json = JSONObject.toJSONString(retMap);
		
		return this.JSON_MESSAGE;
	}

	/**
	 * 意向单退单
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "static-access" })
	public String closeIntent() {
		try {
			IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
			
			String sql = "select * from es_work_order where order_id='" + order_id.trim() + "' and status in('0','1')";
			List list = baseDaoSupport.queryForList(sql);
			if (list!=null&&list.size() > 0) {
				
				json = "{result:1,message:'有未完成或处理成功工单，不允许退单'}";
			} else {
				
				sql = "select * from es_order_intent where order_id='" + order_id.trim()
						+ "' and status in ('00','03')";
				list = baseDaoSupport.queryForList(sql);
				
				if (list.size() > 0) {
					String is_online_order = String.valueOf(((Map)list.get(0)).get("is_online_order"));
					String status = String.valueOf(((Map)list.get(0)).get("status"));
					
					if(!"1".equals(is_online_order)){
						//不是线上线下的订单，直接返回错误
						json = "{result:1,message:'改意向单已退单或已完成，不允许退单'}";
					}else if("00".equals(status)){
						//已退单的返回成功
						json = "{result:0,message:'意向单退单成功'}";
					}else{
						//已完成的返回失败
						json = "{result:1,message:'改意向单已完成，不允许退单'}";
					}
				} else {
					
					//调用意向单通知接口
					
					O2OStatusUpdateResp refundResp = ecsOrdManager.returnToMobileStore(order_id);
					if (!EcsOrderConsts.INF_RESP_CODE_00000.equals(refundResp.getError_code())) {
						json = "{result:1,message:'退单失败："+refundResp.getError_msg()+"'}";
						return this.JSON_MESSAGE;
					}
					
					//更新意向单状态
					sql = "select is_work_custom from es_order_intent where order_id='" + order_id.trim()
							+ "' and status in('01','02')";
					String is_work_custom = baseDaoSupport.queryForString(sql);
					Map<String, Object> fieldsMap = new HashMap<String, Object>();
					fieldsMap.put("status", "00");
					fieldsMap.put("intent_finish_time", new Date());
					fieldsMap.put("contact_results_frist", contactResults);
					fieldsMap.put("contact_results_second", contactSecondResults);
					Map<String, Object> whereMap = new HashMap<String, Object>();
					whereMap.put("order_id", order_id.trim());
					baseDaoSupport.update("es_order_intent", fieldsMap, whereMap);
					
					//记录日志
					Map<String, Object> map = new HashMap<String, Object>();
					if (!StringUtils.isEmpty(statement_work) && "1".equals(is_work_custom)) {
						map.put("remark", statement_work);
					} else if (!"1".equals(is_work_custom)) {
						map.put("remark", checkText + "-"+checkText2+":" + contactDetails);
					}
					map.put("order_id", order_id);
					map.put("action", "结单");
					ordIntentManager.addIntentRecord(map);
					
					json = "{result:0,message:'意向单退单成功'}";
				}
			}
		} catch (Exception e) {
			json = "{result:1,message:'意向单退单失败" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 意向单回收到省中台
	 * 
	 * @return
	 */
	public String intentBack() {
		try {
			String msg = ordIntentManager.intentBack(order_id);
			if (StringUtil.isEmpty(msg)) {
				json = "{result:0,message:'意向单回收成功'}";
			} else {
				json = "{result:0,message:'意向单回收失败：" + msg + "'}";
			}
		} catch (Exception e) {
			json = "{result:1,message:'意向单回收失败" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 意向单详情页面+订单信息
	 * 
	 * @return
	 */
	public String intentDetails() {
		try {
			intentDetail = ordWorkManager.getIntentDetail(order_id.trim());
			// --shop/admin/orderFlowAction!preDealOrd.do
			intentDetail.put("action_url", "shop/admin/orderFlowAction!order_detail_view.do");
			query_type = "order_view";
			intentLogs = ordWorkManager.getIntentLogs(order_id.trim());
			smsSendLogs = ordIntentManager.getSmsSendLogs(order_id);
			if ("1".equals(intentDetail.get("exist") + "")) {
				// this.order_detail_view();
			}
		} catch (Exception e) {
			json = "{result:1,message:'意向单详情查询失败" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return "intentDetails";
	}
	
	/**
	 * 订单领取新页面显示的详情
	 * 
	 * @return
	 */
	public String orderDetails() {
		try {
			orderDetail = ordWorkManager.getOrderDetail(order_id);
			OrderHandleLogsListReq req = new OrderHandleLogsListReq();
			req.setOrder_id(order_id);
			req.setHandler_type(Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
			OrderHandleLogsListResp resp = orderServices.listOrderHandlerLogs(req);
			orderLogs = resp.getLogList();
		} catch (Exception e) {
			json = "{result:1,message:'订单详情查询失败" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return "orderDetails";
	}

	/**
	 * 意向单处理按钮
	 * 
	 * @return
	 */
	public String handleIntent() throws Exception {
		ES_ORDER_INTENT pojo = new ES_ORDER_INTENT();
		pojo.setOrder_id(this.order_id);

		List<ES_ORDER_INTENT> intents = this.workCustomCfgManager.qryOrderIntentList(pojo, null);

		if (intents != null && intents.size() > 0 && "1".equals(intents.get(0).getIs_work_custom())) {
			// 自定义流程意向单处理页面
			return "intent_workflow";
		} else {
			acceptDetail = ordIntentManager.getAcceptDetail(order_id);
			sms_open = cacheUtil.getConfigInfo("sms_open");
			AdminUser user =  ManagerUtils.getAdminUser();
			sms_open = user.getCol1();
			countyList = ordWorkManager.getIntentCountyList(acceptDetail.get("order_city_code") + "");
			return "handleIntent";
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getFristContactResults(){
		List frist_results=ordWorkManager.getContactResultsList();
		Map retMap = new HashMap();
		
		retMap.put("frist_results", frist_results);

		this.json = JSONObject.toJSONString(retMap);
		return JSON_MESSAGE;
		}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getSecondContactResults(){
		List<Map<String, Object>> second_results=ordWorkManager.getContactSecondResultsList(this.frist_id);
		Map retMap = new HashMap();
		
		retMap.put("second_results", second_results);

		this.json = JSONObject.toJSONString(retMap);
		return JSON_MESSAGE;
		}
	/**
	 * 意向单保存
	 * 
	 * @return
	 */
	public String saveIntent() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> update_map = new HashMap<String, Object>();
			Map<String, Object> acceptDetail = ordIntentManager.getAcceptDetail(order_id);
			String old_ship_name = Const.getStrValue(acceptDetail, "acceptName");
			String old_ship_addr = Const.getStrValue(acceptDetail, "acceptAddr");
			String old_cert_num = Const.getStrValue(acceptDetail, "acceptCert");
			String old_order_county_code = Const.getStrValue(acceptDetail, "order_county_code");
			String update_remark = "";
			if (!StringUtil.isEmpty(acceptName)) {
				if (!acceptName.equals(old_ship_name)) {
					update_remark += "'预约受理姓名'原值：'" + old_ship_name + "',修改为:'" + acceptName + "'。";
					update_map.put("ship_name", acceptName);
				}
			}
			if (!StringUtil.isEmpty(acceptAddr)) {
				if (!acceptAddr.equals(old_ship_addr)) {
					update_remark += "'预约受理地址'原值：'" + old_ship_addr + "',修改为:'" + acceptAddr + "'。";
					update_map.put("ship_addr", acceptAddr);
				}
			}
			if (!StringUtil.isEmpty(acceptCert)) {
				if (!acceptCert.equals(old_cert_num)) {
					update_remark += "'预约受理身份证'原值：'" + old_cert_num + "',修改为:'" + acceptCert + "'。";
					update_map.put("cert_num", acceptCert);
				}
			}
			if (!StringUtil.isEmpty(order_county_code)) {
				countyList = ordWorkManager.getIntentCountyList(acceptDetail.get("order_city_code") + "");
				String old_order_county = "";
				String order_county = "";
				String county_id = "";
				for (Map<String, Object> county : countyList) {
					if (old_order_county_code.equals(Const.getStrValue(county, "field_value"))) {
						old_order_county = Const.getStrValue(county, "field_value_desc");
					}
					if (order_county_code.equals(Const.getStrValue(county, "field_value"))) {
						order_county = Const.getStrValue(county, "field_value_desc");
						county_id = Const.getStrValue(county, "other_field_value");
					}
				}
				if (!order_county_code.equals(old_order_county_code)) {
					update_remark += "'用户县分'原值：'" + old_order_county + "',修改为:'" + order_county + "'。";
				}
				update_map.put("order_county_code", order_county_code);
				update_map.put("county_id", county_id);
			}
			map.put("remark", "客户联系说明：" + checkText +"--"+checkText2+ ":" + contactDetails + update_remark);
			update_map.put("contact_results_frist", contactResults);
			update_map.put("contact_results_second", contactSecondResults);
			//System.out.println("客户联系说明：" + checkText +"--"+checkText2+ ":" + contactDetails + update_remark);
			
			map.put("order_id", order_id);
			map.put("action", "客户联系");
			

			ordIntentManager.addIntentRecord(map);
			// map.clear();
			if (null != update_map) {
				update_map.put("order_id", order_id);
			}
			ordIntentManager.saveIntent(update_map);
			json = "{result:0,message:'保存成功'}";
		} catch (Exception e) {
			json = "{result:1,message:'意向单保存异常" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return JSON_MESSAGE;
	}

	/**
	 * 意向单添加备注页面
	 * 
	 * @return
	 */
	public String intentAddRemark() {
		return "intentAddRemark";
	}

	/**
	 * 意向单添加备注
	 * 
	 * @return
	 */
	public String addRemarks() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("remark", remarks);
			map.put("order_id", order_id);
			map.put("action", "备注");
			ordIntentManager.addIntentRecord(map);
			json = "{result:0,message:'备注添加成功'}";
		} catch (Exception e) {
			json = "{result:1,message:'备注添加失败" + e.getMessage() + "'}";
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 短信模版页面
	 * 
	 * @return
	 */
	public String smsTemplateForm() {
		smsStatusList = ordIntentManager.getSmsStatusList();
		smsLevelList = ordIntentManager.getSmsLevelList();
		if (params == null) {
			params = new OrderQueryParams();
		}
		this.webpage = ordIntentManager.smsTemplateFormQuery(this.params, this.getPage(), this.getPageSize());
		sms_open = ManagerUtils.getAdminUser().getCol1();
		if(!"open".equals(sms_open)){
			sms_open = "colse";
		}
		return "smsTemplateForm";
	}

	/**
	 * 新增短信模版页面
	 * 
	 * @return
	 */
	public String addSMSForm() {
		return "addSMSForm";
	}

	/**
	 * 新增短信模版
	 * 
	 * @return
	 */
	public String addSMS() {
		try {
			if (!StringUtils.isEmpty(sms_template)) {
				ordIntentManager.addSMS(sms_template);
				json = "{result:0,message:'新增成功'}";
			} else {
				json = "{result:1,message:'新增失败,内容为空'}";
			}
		} catch (Exception e) {
			json = "{result:1,message:'新增失败" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 修改短信模版页面
	 * 
	 * @return
	 */
	public String editSMSForm() {
		try {
			if (!StringUtils.isEmpty(sms_id)) {
				smsDetail = ordIntentManager.getSMSDetail(sms_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "editSMSForm";
	}

	/**
	 * 修改短信模版
	 * 
	 * @return
	 */
	public String editSMS() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sms_template", sms_template);
			map.put("sms_template_new", sms_template_new);
			map.put("sms_id", sms_id);
			String msg = ordIntentManager.editSMS(map);
			if (StringUtils.isEmpty(msg)) {
				json = "{result:0,message:'修改成功'}";
			} else {
				json = "{result:1,message:'修改失败," + msg + "'}";
			}
		} catch (Exception e) {
			json = "{result:1,message:'修改失败" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 启用短信模版
	 * 
	 * @return
	 */
	public String pickSMS() {
		try {
			String msg = ordIntentManager.pickSMS(sms_id);
			if (StringUtils.isEmpty(msg)) {
				json = "{result:0,message:'启用成功'}";
			} else {
				json = "{result:1,message:'启用失败," + msg + "'}";
			}
		} catch (Exception e) {
			json = "{result:1,message:'启用失败" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 禁用短信模版
	 * 
	 * @return
	 */
	public String banSMS() {
		try {
			String msg = ordIntentManager.banSMS(sms_id);
			if (StringUtils.isEmpty(msg)) {
				json = "{result:0,message:'禁用成功'}";
			} else {
				json = "{result:1,message:'禁用失败," + msg + "'}";
			}
		} catch (Exception e) {
			json = "{result:1,message:'禁用失败" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return this.JSON_MESSAGE;
	}
	
	/**
	 * 打开短信发送功能
	 * 
	 * @return
	 */
	public String openSmsSend() {
		try {
			String msg = ordIntentManager.openSmsSend();
			if (StringUtils.isEmpty(msg)) {
				json = "{result:0,message:'打开短信发送功能成功'}";
			} else {
				json = "{result:1,message:'打开短信发送功能失败," + msg + "'}";
			}
		} catch (Exception e) {
			json = "{result:1,message:'打开短信发送功能失败" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return this.JSON_MESSAGE;
	}
	/**
	 * 关闭短信发送功能
	 * 
	 * @return
	 */
	public String colseSmsSend() {
		try {
			String msg = ordIntentManager.colseSmsSend();
			if (StringUtils.isEmpty(msg)) {
				json = "{result:0,message:'关闭短信发送功能成功'}";
			} else {
				json = "{result:1,message:'关闭短信发送功能失败," + msg + "'}";
			}
		} catch (Exception e) {
			json = "{result:1,message:'关闭短信发送功能失败" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 禁用短信模版
	 * 
	 * @return
	 */
	public String deleteSMS() {
		try {
			String msg = ordIntentManager.deleteSMS(sms_id);
			if (StringUtils.isEmpty(msg)) {
				json = "{result:0,message:'删除成功'}";
			} else {
				json = "{result:1,message:'删除失败," + msg + "'}";
			}
		} catch (Exception e) {
			json = "{result:1,message:'删除失败" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * 短信模版详情页面
	 * 
	 * @return
	 */
	public String smsDetailsForm() {
		try {
			if (!StringUtils.isEmpty(sms_id)) {
				smsDetail = ordIntentManager.getSMSDetail(sms_id);
				smsLogs = ordIntentManager.getSmsLogs(sms_id);
			}
		} catch (Exception e) {
			json = "{result:1,message:'查询失败" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return "smsDetailsForm";
	}

	/**
	 * 发送短信页面
	 * 
	 * @return
	 */
	public String sendingSMSForm() {
		try {
			if (!StringUtils.isEmpty(order_id)) {
				ship_tel = ordIntentManager.getOrderShipTel(order_id);
				if (!StringUtil.isEmpty(ship_tel) && ship_tel.length() == 11) {
					if (ordIntentManager.isUnicom(ship_tel)) {
						type = "unicom";
					} else {
						type = "other";
					}
				} else {
					json = "{result:1,message:'加载页面失败,联系号码" + ship_tel + "异常'}";
					return this.JSON_MESSAGE;
				}
			}
		} catch (Exception e) {
			json = "{result:1,message:'加载页面失败" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return "sendingSMSForm";
	}
	
	/**
	 * 选择短信页面
	 * 
	 * @return
	 */
	public String selectSMSForm() {
		try {
			if (params == null) {
				params = new OrderQueryParams();
			}
			if(!StringUtils.isEmpty(type)){
				params.setType(type);
			}
			this.webpage = ordIntentManager.selectSMSFormQuery(this.params, this.getPage(), this.getPageSize());
		} catch (Exception e) {
			json = "{result:1,message:'加载页面失败" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return "selectSMSForm";
	}

	/**
	 * 发送短信
	 * 
	 * @return
	 */
	public String sendSMS() {
		try {
			if (!StringUtils.isEmpty(order_id)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sms_template", sms_template);
				map.put("order_id", order_id);
				map.put("type", type);
				map.put("ship_tel", ship_tel);
				String msg = ordIntentManager.sendSMS(map);
				if (StringUtils.isEmpty(msg)) {
					json = "{result:0,message:'发送成功'}";
				} else {
					json = "{result:1,message:'发送失败," + msg + "'}";
				}
			} else {
				json = "{result:1,message:'发送失败,单号异常'}";
			}
		} catch (Exception e) {
			json = "{result:1,message:'发送失败" + e.getMessage() + "'}";
			e.printStackTrace();
		}
		return this.JSON_MESSAGE;
	}

	/********************************************************************************************/

	public Map<String, Object> getOrderDetail() {
		return orderDetail;
	}

	public String getFrist_id() {
		return frist_id;
	}

	public void setFrist_id(String frist_id) {
		this.frist_id = frist_id;
	}

	public void setOrderDetail(Map<String, Object> orderDetail) {
		this.orderDetail = orderDetail;
	}

	public List<OrderHandleLogsReq> getOrderLogs() {
		return orderLogs;
	}

	public void setOrderLogs(List<OrderHandleLogsReq> orderLogs) {
		this.orderLogs = orderLogs;
	}

	public List getContactResultsList() {
		return contactResultsList;
	}

	public void setContactResultsList(List contactResultsList) {
		this.contactResultsList = contactResultsList;
	}

	
	public String getContactSecondResults() {
		return contactSecondResults;
	}

	public void setContactSecondResults(
			String contactSecondResults) {
		this.contactSecondResults = contactSecondResults;
	}

	public String getContactResults() {
		return contactResults;
	}

	public void setContactResults(String contactResults) {
		this.contactResults = contactResults;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public OrderQueryParams getParams() {
		return params;
	}

	public void setParams(OrderQueryParams params) {
		this.params = params;
	}

	public String getQuery_type() {
		return query_type;
	}

	public void setQuery_type(String query_type) {
		this.query_type = query_type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<Map> getDc_MODE_REGIONList() {
		return dc_MODE_REGIONList;
	}

	public void setDc_MODE_REGIONList(List<Map> dc_MODE_REGIONList) {
		this.dc_MODE_REGIONList = dc_MODE_REGIONList;
	}

	public List<Map> getOrder_from_list() {
		return order_from_list;
	}

	public void setOrder_from_list(List<Map> order_from_list) {
		this.order_from_list = order_from_list;
	}

	public Map<String, Object> getIntentDetail() {
		return intentDetail;
	}

	public void setIntentDetail(Map<String, Object> intentDetail) {
		this.intentDetail = intentDetail;
	}

	public List<Map<String, Object>> getIntentLogs() {
		return intentLogs;
	}

	public void setIntentLogs(List<Map<String, Object>> intentLogs) {
		this.intentLogs = intentLogs;
	}

	public List<Map<String, Object>> getIntentStatusList() {
		return intentStatusList;
	}

	public void setIntentStatusList(List<Map<String, Object>> intentStatusList) {
		this.intentStatusList = intentStatusList;
	}

	public List<Map<String, Object>> getIntentTopSeedProLineList() {
		return intentTopSeedProLineList;
	}

	public void setIntentTopSeedProLineList(List<Map<String, Object>> intentTopSeedProLineList) {
		this.intentTopSeedProLineList = intentTopSeedProLineList;
	}

	public List<Map<String, Object>> getIntentTopSeedTypeList() {
		return intentTopSeedTypeList;
	}

	public void setIntentTopSeedTypeList(List<Map<String, Object>> intentTopSeedTypeList) {
		this.intentTopSeedTypeList = intentTopSeedTypeList;
	}
	

	public List<Map<String, Object>> getIntentTopSeedGroupIdList() {
		return intentTopSeedGroupIdList;
	}

	public void setIntentTopSeedGroupIdList(List<Map<String, Object>> intentTopSeedGroupIdList) {
		this.intentTopSeedGroupIdList = intentTopSeedGroupIdList;
	}

	public List<Map<String, Object>> getCountyList() {
		return countyList;
	}

	public void setCountyList(List<Map<String, Object>> countyList) {
		this.countyList = countyList;
	}

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

	public Map<String, Object> getAcceptDetail() {
		return acceptDetail;
	}

	public void setAcceptDetail(Map<String, Object> acceptDetail) {
		this.acceptDetail = acceptDetail;
	}

	public String getCheckText() {
		return checkText;
	}

	public void setCheckText(String checkText) {
		this.checkText = checkText;
	}

	public String getCheckText2() {
		return checkText2;
	}

	public void setCheckText2(String checkText2) {
		this.checkText2 = checkText2;
	}

	public String getAcceptName() {
		return acceptName;
	}

	public void setAcceptName(String acceptName) {
		this.acceptName = acceptName;
	}

	public String getAcceptCert() {
		return acceptCert;
	}

	public void setAcceptCert(String acceptCert) {
		this.acceptCert = acceptCert;
	}

	public String getAcceptAddr() {
		return acceptAddr;
	}

	public void setAcceptAddr(String acceptAddr) {
		this.acceptAddr = acceptAddr;
	}

	public ES_ORDER_INTENT getIntent() {
		return intent;
	}

	public String getIs_work_custom() {
		return is_work_custom;
	}

	public void setIs_work_custom(String is_work_custom) {
		this.is_work_custom = is_work_custom;
	}

	public String getOrder_county_code() {
		return order_county_code;
	}

	public void setOrder_county_code(String order_county_code) {
		this.order_county_code = order_county_code;
	}

	public String getStatement_work() {
		return statement_work;
	}

	public void setStatement_work(String statement_work) {
		this.statement_work = statement_work;
	}

	public String getIsSZT() {
		return isSZT;
	}

	public void setIsSZT(String isSZT) {
		this.isSZT = isSZT;
	}

	public String getSms_id() {
		return sms_id;
	}

	public void setSms_id(String sms_id) {
		this.sms_id = sms_id;
	}

	public String getSms_template() {
		return sms_template;
	}

	public void setSms_template(String sms_template) {
		this.sms_template = sms_template;
	}

	public String getSms_template_new() {
		return sms_template_new;
	}

	public void setSms_template_new(String sms_template_new) {
		this.sms_template_new = sms_template_new;
	}

	public List<Map<String, Object>> getSmsStatusList() {
		return smsStatusList;
	}

	public void setSmsStatusList(List<Map<String, Object>> smsStatusList) {
		this.smsStatusList = smsStatusList;
	}

	public List<Map<String, Object>> getSmsLevelList() {
		return smsLevelList;
	}

	public void setSmsLevelList(List<Map<String, Object>> smsLevelList) {
		this.smsLevelList = smsLevelList;
	}

	public Map<String, Object> getSmsDetail() {
		return smsDetail;
	}

	public void setSmsDetail(Map<String, Object> smsDetail) {
		this.smsDetail = smsDetail;
	}

	public List<Map<String, Object>> getSmsLogs() {
		return smsLogs;
	}

	public void setSmsLogs(List<Map<String, Object>> smsLogs) {
		this.smsLogs = smsLogs;
	}

	public List<Map<String, Object>> getSmsSendLogs() {
		return smsSendLogs;
	}

	public void setSmsSendLogs(List<Map<String, Object>> smsSendLogs) {
		this.smsSendLogs = smsSendLogs;
	}

	public String getShip_tel() {
		return ship_tel;
	}

	public void setShip_tel(String ship_tel) {
		this.ship_tel = ship_tel;
	}

	public String getSms_open() {
		return sms_open;
	}

	public void setSms_open(String sms_open) {
		this.sms_open = sms_open;
	}

	public void setIntent(ES_ORDER_INTENT intent) {
		this.intent = intent;
	}

	public String getCustomerContactUrl() throws Exception {
		return "customer_contact";
	}

	public String getNoResCheckCustomerContactUrl() throws Exception {
		return "no_res_check_customer_contact";
	}
	
	
	public List<Regions> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<Regions> regionList) {
		this.regionList = regionList;
	}

	public List<Map> getOrder_type_list() {
		return order_type_list;
	}

	public void setOrder_type_list(List<Map> order_type_list) {
		this.order_type_list = order_type_list;
	}
	
	//蜂行动
	
	public List<Map> getBee_action_query_list() {
		return bee_action_query_list;
	}

	public void setBee_action_query_list(List<Map> bee_action_query_list) {
		this.bee_action_query_list = bee_action_query_list;
	}
	

	public String getDealDesc() {
		return dealDesc;
	}

	public void setDealDesc(String dealDesc) {
		this.dealDesc = dealDesc;
	}

	public String getReturnedReasonCode() {
		return returnedReasonCode;
	}

	public void setReturnedReasonCode(String returnedReasonCode) {
		this.returnedReasonCode = returnedReasonCode;
	}

	public String getApplyFrom() {
		return applyFrom;
	}

	public void setApplyFrom(String applyFrom) {
		this.applyFrom = applyFrom;
	}

	/**
	 * 重选择商品
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getReSelectGoodsUrl() throws Exception {

		return "re_select_goods";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getOrderIntentByOrderId() throws Exception {
		Map retMap = new HashMap();
		acceptDetail = ordIntentManager.getAcceptDetail(order_id);
		try {
			if (StringUtil.isEmpty(this.order_id))
				throw new Exception("传入订单编号为空");

			ES_ORDER_INTENT pojo = new ES_ORDER_INTENT();
			pojo.setOrder_id(this.order_id);
			List<ES_ORDER_INTENT> intents = this.workCustomCfgManager.qryOrderIntentList(pojo, null);

			countyList = ordWorkManager.getIntentCountyList(acceptDetail.get("order_city_code") + "");

			if (intents == null || intents.size() == 0) {
				retMap.put("code", "1");
				retMap.put("msg", "根据订单编号" + this.order_id + "未查到意向单信息");
			} else {
				retMap.put("code", "0");
				retMap.put("intent", intents.get(0));
				retMap.put("countyList", countyList);
				String order_from = intents.get(0).getSource_system_type();
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String can_update = cacheUtil.getConfigInfo("YXD_CAN_UPDATE_" + order_from);
				if (StringUtil.isEmpty(can_update)) {
					can_update = "yes";
				}
				retMap.put("can_update", can_update);
			}
		} catch (Exception e) {
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}

		this.json = JSONObject.toJSONString(retMap);

		return JSON_MESSAGE;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public String updateOrderIntent() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map retMap = new HashMap();

		try {
			if (this.intent == null)
				throw new Exception("传入意向单对象为空");

			if (StringUtil.isEmpty(this.intent.getOrder_id()))
				throw new Exception("传入订单编号为空");

			ES_ORDER_INTENT pojo = new ES_ORDER_INTENT();
			pojo.setOrder_id(this.intent.getOrder_id());

			List<ES_ORDER_INTENT> intents = this.workCustomCfgManager.qryOrderIntentList(pojo, null);

			if (intents == null || intents.size() == 0) {
				throw new Exception("根据订单编号" + this.order_id + "未查到意向单信息");
			}

			ES_ORDER_INTENT orderIntent = intents.get(0);

			String update_remark = "";
			String old_ship_name = orderIntent.getShip_name();
			String old_ship_addr = orderIntent.getShip_addr();
			String old_cert_num = orderIntent.getCert_num();
			String order_county_code = orderIntent.getOrder_county_code();
			String old_contact_result_frist = orderIntent.getContact_results_frist();
			String old_contact_result_second = orderIntent.getContact_results_second();

			if (!StringUtil.isEmpty(this.intent.getOrder_county_code())) {
				if (!this.intent.getOrder_county_code().equals(order_county_code)) {
					String raw_org_name = ecsOrdManager.queryOrgName(order_county_code);
					String update_org_name = ecsOrdManager.queryOrgName(this.intent.getOrder_county_code());

					logger.info("order_id:" + this.intent.getOrder_id());
					logger.info("原order_county_code:" + order_county_code);
					logger.info("修order_county_code:" + this.intent.getOrder_county_code());
					update_remark += "'县分'原值：'" + raw_org_name + "',修改为:'" + update_org_name + "'。";
				}
			} else {
				// 前台传入值为空，认为不修改，将值设为旧值
				this.intent.setShip_name(old_ship_name);
			}

			if (!StringUtil.isEmpty(this.intent.getShip_name())) {
				if (!this.intent.getShip_name().equals(old_ship_name)) {
					update_remark += "'预约受理姓名'原值：'" + old_ship_name + "',修改为:'" + this.intent.getShip_name() + "'。";
				}
			} else {
				// 前台传入值为空，认为不修改，将值设为旧值
				this.intent.setShip_name(old_ship_name);
			}

			if (!StringUtil.isEmpty(this.intent.getShip_addr())) {
				if (!this.intent.getShip_addr().equals(old_ship_addr)) {
					update_remark += "'预约受理地址'原值：'" + old_ship_addr + "',修改为:'" + this.intent.getShip_addr() + "'。";
				}
			} else {
				// 前台传入值为空，认为不修改，将值设为旧值
				this.intent.setShip_addr(old_ship_addr);
			}

			if (!StringUtil.isEmpty(this.intent.getCert_num())) {
				if (!this.intent.getCert_num().equals(old_cert_num)) {
					update_remark += "'预约受理身份证'原值：'" + old_cert_num + "',修改为:'" + this.intent.getCert_num() + "'。";
				}
			} else {
				// 前台传入值为空，认为不修改，将值设为旧值
				this.intent.setCreate_time(old_cert_num);
			}
			
			// 存储日志
			logger.info("setRemark:" + update_remark);
			String remark = null;
			if (!StringUtil.isEmpty(this.intent.getRemark())) {
				remark = "客户联系说明'" + this.intent.getRemark() + ":" + update_remark;
			} else {
				remark = "客户联系说明:" + update_remark;
			}
			//客户联系结果
			if (!StringUtil.isEmpty(this.intent.getContact_results_frist())) {
				if (!this.intent.getContact_results_frist().equals(old_contact_result_frist)) {
					remark += "'客户联系一级结果：" + checkText +"；";
				}
			} else {
				// 前台传入值为空，认为不修改，将值设为旧值
				this.intent.setContact_results_frist(old_contact_result_frist);
			}
			if (!StringUtil.isEmpty(this.intent.getContact_results_second())) {
				if (!this.intent.getContact_results_second().equals(old_contact_result_second)) {
					remark += "'客户联系二级结果：'" + checkText2 +"。";
				}
			} else {
				// 前台传入值为空，认为不修改，将值设为旧值
				this.intent.setContact_results_frist(old_contact_result_second);
			}

			ES_WORK_CUSTOM_NODE_INS ins = new ES_WORK_CUSTOM_NODE_INS();
			ins.setOrder_id(this.intent.getOrder_id());
			ins.setIs_curr_step("1");
			List<ES_WORK_CUSTOM_NODE_INS> insRet = this.workCustomCfgManager.qryInsList(ins, null);
			String node_name = "";
			DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (insRet != null && insRet.size() > 0) {
				for (int i = 0; i < insRet.size(); i++) {
					insRet.get(i).setRemark(remark);
					insRet.get(i).setCreate_date(DF.format(new Date()));
					node_name = insRet.get(i).getNode_name();
				}
				this.workCustomCfgManager.updateNodeInstances(insRet);
			}

			String county_id = ecsOrdManager.queryCountyCoding(this.intent.getOrder_county_code());
			this.intent.setCounty_id(county_id);
			
			// 更新
			this.workCustomCfgManager.updateOrderIntent(this.intent);
			Map<String, Object> mapinfo = new HashMap();
			mapinfo.put("order_id", intent.getOrder_id());
			mapinfo.put("remark", remark);
			mapinfo.put("action", node_name);
			ordIntentManager.addIntentRecord(mapinfo);
			retMap.put("code", "0");

		} catch (Exception e) {
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}

		this.json = JSONObject.toJSONString(retMap);

		return JSON_MESSAGE;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String updateOrderIntentGoods() throws Exception {
		Map retMap = new HashMap();

		try {
			if (this.intent == null)
				throw new Exception("传入意向单对象为空");

			if (StringUtil.isEmpty(this.intent.getOrder_id()))
				throw new Exception("传入订单编号为空");

			if (StringUtil.isEmpty(this.intent.getGoods_id()))
				throw new Exception("传入商品编号为空");

			this.workCustomCfgManager.updateOrderIntent(this.intent);
			Map<String, Object> mapinfo = new HashMap();
			mapinfo.put("order_id", intent.getOrder_id());
			mapinfo.put("remark", "更改商品为" + this.intent.getGoods_name());
			mapinfo.put("action", "修改商品");
			ordIntentManager.addIntentRecord(mapinfo);
			retMap.put("code", "0");

		} catch (Exception e) {
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}

		this.json = JSONObject.toJSONString(retMap);

		return JSON_MESSAGE;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getOrderIntentInfoByOrderId() throws Exception {
		Map retMap = new HashMap();

		try {
			if (StringUtil.isEmpty(this.order_id))
				throw new Exception("传入订单编号为空");

			List intents = this.ordIntentManager.qryOrderIntentByOrderId(this.order_id);

			if (intents == null || intents.size() == 0) {
				retMap.put("code", "1");
				retMap.put("msg", "根据订单编号" + this.order_id + "未查到意向单信息");
			} else {
				retMap.put("code", "0");
				retMap.put("intent", intents.get(0));
			}
		} catch (Exception e) {
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}

		this.json = JSONObject.toJSONString(retMap);

		return JSON_MESSAGE;
	}

	public String getEnterpriseIntentCheckUrl() throws Exception {
		return "enterprise_order_intent_check";
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

}