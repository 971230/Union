package com.ztesoft.net.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import params.req.QueryExpOrderStatisticsReq;
import params.resp.QueryExpOrderStatisticsResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IOrderStatisticManager;

public class OrderWorkPlatformAction extends WWAction {
	
	
	private Map<String, String> params = new HashMap<String, String>(0);	//参数
	private Map<String, String> orderPlatFormView = new HashMap<String, String>(0);	//正常单输出参数
	private Map<String,String> pieView = new HashMap<String, String>(0);	//饼图结果集
	private Map expOrderPlatFormView = new HashMap(0);	//异常单输出参数
	public static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	private List topExpKeys = new ArrayList();
	
	private String start_time;
	private String end_time;
	
	private String show_type;
	
	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Map<String, String> getOrderPlatFormView() {
		return orderPlatFormView;
	}

	public void setOrderPlatFormView(Map<String, String> orderPlatFormView) {
		this.orderPlatFormView = orderPlatFormView;
	}

	public Map getExpOrderPlatFormView() {
		return expOrderPlatFormView;
	}

	public void setExpOrderPlatFormView(Map expOrderPlatFormView) {
		this.expOrderPlatFormView = expOrderPlatFormView;
	}

//	public List getTopExpKeys() {
//		return topExpKeys;
//	}
//
//	public void setTopExpKeys(List topExpKeys) {
//		this.topExpKeys = topExpKeys;
//	}



	@Resource
	protected IOrderStatisticManager orderStatisticManager;

	/**
	 * 显示工作台数据
	 * @return
	 */
	public String showOrderWorkPlatform(){
		if(params.containsKey("show_type") && StringUtils.isNotEmpty(params.get("show_type"))){
			show_type = params.get("show_type");
		}else{
			params.put("show_type", "single");
		}
		logger.info("===============开始进入工作台数据查询类，等待查询数据的方式执行============");
		AdminUser user = ManagerUtils.getAdminUser();
		String user_id = user.getUserid();
		String start_time = "";
		String end_time = "";
		if(params.containsKey("query_type") && "1".equals(params.get("query_type"))){
			//页面传时间
			params.put("query_type", "0");
		}else{
			//初始化时间
			String showType = params.get("show_type");
			params = new HashMap<String, String>(0);
			params.put("show_type", showType);
		}
		params.put("username", user.getUsername());
		if(params.containsKey("start_time") && !"".equals(params.get("start_time"))){
			start_time = params.get("start_time");
		}else{
			Calendar date = new GregorianCalendar();
			date.add(Calendar.MONTH, -1);
			start_time = DF.format(date.getTime());
			params.put("start_time", start_time);
		}
		if(params.containsKey("end_time") && !"".equals(params.get("end_time"))){
			end_time = params.get("end_time");
		}else{
			Calendar date = new GregorianCalendar();
			date.add(Calendar.MONTH, -2);
			end_time = DF.format(new Date());
			params.put("end_time", end_time);
		}
		
		long start = System.currentTimeMillis();
		logger.info("===============获取每日订单量数据开始============orderStatisticManager.queryDayOrders");
		//获取每日订单量
		start = System.currentTimeMillis();
		Map<String,String> m = orderStatisticManager.queryDayOrders(user_id, user.getFounder(), start_time, end_time);
		logger.info("每日订单量-----"+(System.currentTimeMillis()-start)+"ms");
		logger.info("===============获取每日订单量数据结束============");

		params.put("series",m.get("series"));
		params.put("xaxis",m.get("xaxis"));
		
		//获取饼图结果
		start = System.currentTimeMillis();
		pieView = orderStatisticManager.queryPieData(start_time, end_time);
		logger.info("获取饼图结果-----"+(System.currentTimeMillis()-start)+"ms");
		return "workPlatform";
	}
	
	/**
	 * 显示工作台数据-个人
	 * @return
	 */
	public String showOrderWorkPlatformSingle(){
		params.put("show_type", "single");
		showOrderWorkPlatform();
		return "workPlatformSingle";
	}
	
	/**
	 * 显示工作台数据-全量的
	 * @return
	 */
	public String showOrderWorkPlatformAll(){
		params.put("show_type", "all");
		showOrderWorkPlatform();
		return "workPlatformAll";
	}
	
	public String orderStatistics() {
		logger.info("===============开始进入工作台数据查询类，等待查询数据的方式执行============");
		AdminUser user = ManagerUtils.getAdminUser();
		String user_id = user.getUserid();
		String start_time = "";
		String end_time = "";
		if(params.containsKey("query_type") && "1".equals(params.get("query_type"))){
			//页面传时间
			params.put("query_type", "0");
		}else{
			//初始化时间
			params = new HashMap<String, String>(0);
		}
		if(params.containsKey("start_time") && !"".equals(params.get("start_time"))){
			start_time = params.get("start_time");
		}else{
			Calendar date = new GregorianCalendar();
			date.add(Calendar.MONTH, -1);
			start_time = DF.format(date.getTime());
		}
		if(params.containsKey("end_time") && !"".equals(params.get("end_time"))){
			end_time = params.get("end_time");
		}else{
			Calendar date = new GregorianCalendar();
			date.add(Calendar.MONTH, -2);
			end_time = DF.format(new Date());
		}
		long start = System.currentTimeMillis();
		logger.info("===============获取正常单数据开始============orderStatisticManager.queryOrderStatisticsData");
		//正常单
		if(StringUtils.isNotEmpty(show_type) && "single".equals(show_type)){
			orderPlatFormView = orderStatisticManager.queryOrderStatisticsData(user_id, user.getFounder(), start_time, end_time);
			logger.info("1");
		}else if(StringUtils.isNotEmpty(show_type) && "all".equals(show_type)){
			orderPlatFormView = orderStatisticManager.queryOrderStatisticsDataAll(user_id, user.getFounder(), start_time, end_time);
			logger.info("2");
		}else{
			orderPlatFormView = orderStatisticManager.queryOrderStatisticsData(user_id, user.getFounder(), start_time, end_time);
			logger.info("3");
		}
		logger.info("正常单查询-----"+(System.currentTimeMillis()-start)+"ms");
		orderStatisticManager.initMapNullToZero(orderPlatFormView);
		logger.info("===============获取正常单数据结束============");
		JSONObject obj = JSONObject.fromObject(orderPlatFormView);
		json = obj.toString();
		return JSON_MESSAGE;
	}
	
	public String expOrderStatistics() {
		if(params.containsKey("show_type") && StringUtils.isNotEmpty(params.get("show_type"))){
			show_type = params.get("show_type");
		}else{
			show_type = "single";
		}
		if(params.containsKey("start_time") && !"".equals(params.get("start_time"))){
			start_time = params.get("start_time");
		}else{
			Calendar date = new GregorianCalendar();
			date.add(Calendar.MONTH, -1);
			start_time = DF.format(date.getTime());
		}
		if(params.containsKey("end_time") && !"".equals(params.get("end_time"))){
			end_time = params.get("end_time");
		}else{
			Calendar date = new GregorianCalendar();
			date.add(Calendar.MONTH, -2);
			end_time = DF.format(new Date());
		}
		
		//异常单
		long start = System.currentTimeMillis();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		QueryExpOrderStatisticsReq req = new QueryExpOrderStatisticsReq();
		
		if("single".equals(show_type)) {//如果是查询个人的
			AdminUser user = ManagerUtils.getAdminUser();
			String user_id = user.getUserid();
			req.setUser_id(user_id);
			req.setFounder(user.getFounder());
		}
		
		req.setStart_time(start_time);
		req.setEnd_time(end_time);
		logger.info("===============获取异常单数据开始============ExpOrderStatisticsManager.queryExpOrderStatisticsData");
		logger.info("show_type:" + show_type);
		QueryExpOrderStatisticsResp resp = client.execute(req, QueryExpOrderStatisticsResp.class);
		logger.info("===============获取异常单数据结束============");
		logger.info("异常单查询-----"+(System.currentTimeMillis()-start)+"ms");
		resp.getMap();
		JSONObject obj = JSONObject.fromObject(resp.getMap());
		json = obj.toString();
		return JSON_MESSAGE;
	}
	
	public String getTopExpKey() {
		AdminUser user = ManagerUtils.getAdminUser();
		String user_id = user.getUserid();
		String start_time = "";
		String end_time = "";
		if(params.containsKey("start_time") && !"".equals(params.get("start_time"))){
			start_time = params.get("start_time");
		}
		if(params.containsKey("end_time") && !"".equals(params.get("end_time"))){
			end_time = params.get("end_time");
		}
		int pageSize = new Integer(params.get("pageSize"));
		int pageNo = new Integer(params.get("pageNo"));
		//异常单
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		QueryExpOrderStatisticsReq req = new QueryExpOrderStatisticsReq();
		req.setUser_id(user_id);
		req.setFounder(user.getFounder());
		req.setStart_time(start_time);
		req.setEnd_time(end_time);
		req.setPageNo(pageNo);
		req.setPageSize(pageSize);
		logger.info("===============获取异常单数据开始============ExpOrderStatisticsManager.queryExpOrderStatisticsData");
		QueryExpOrderStatisticsResp resp = client.execute(req, QueryExpOrderStatisticsResp.class);
		logger.info("===============获取异常单数据结束============");

		if("0".equals(resp.getError_code())){
			Map topExpKeyMap = resp.getMap();
			List list = (List)topExpKeyMap.get("topExpKeys");
			Long totalCount = (Long) topExpKeyMap.get("totalCount");
			int maxPageNo = 1;
			if (totalCount.intValue()%pageSize != 0) {
				maxPageNo = totalCount.intValue()/pageSize + 1;
			} else {
				maxPageNo = totalCount.intValue()/pageSize;
			}
			String return_data = JSONArray.fromObject(list).toString();
			json = "{'result':0,'maxPageNo':"+maxPageNo+",'return_data':"+return_data+"}";
		} else {
			json = "{'result':-1}";
		}
		
		return JSON_MESSAGE;
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

	public Map<String, String> getPieView() {
		return pieView;
	}

	public void setPieView(Map<String, String> pieView) {
		this.pieView = pieView;
	}

	public String getShow_type() {
		return show_type;
	}

	public void setShow_type(String show_type) {
		this.show_type = show_type;
	}
	
}
