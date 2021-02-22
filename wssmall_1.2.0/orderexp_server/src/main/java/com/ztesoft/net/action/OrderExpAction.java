package com.ztesoft.net.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import model.EsearchSpecvalues;

import org.drools.core.util.StringUtils;

import zte.net.ecsord.common.StypeConsts;
import zte.net.iservice.IRegionService;
import zte.params.region.req.RegionsListReq;
import zte.params.region.resp.RegionsListResp;

import com.alibaba.fastjson.JSON;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Regions;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.EsearchExpInst;
import com.ztesoft.net.model.EsearchExpInstQuery;
import com.ztesoft.net.param.inner.ExpInstBatchProcessedInner;
import com.ztesoft.net.param.inner.ExpInstQueryInner;
import com.ztesoft.net.param.inner.ExpInstSpecInner;
import com.ztesoft.net.param.inner.OrderExpMarkProcessedInner;
import com.ztesoft.net.param.inner.SpecvalueInner;
import com.ztesoft.net.param.outer.ExpInstBatchProcessedOuter;
import com.ztesoft.net.param.outer.ExpInstSpecCatalogResp;
import com.ztesoft.net.param.outer.ExpInstSpecOnDayOuter;
import com.ztesoft.net.param.outer.OrderExpMarkProcessedOuter;
import com.ztesoft.net.param.outer.TopExpKey;
import com.ztesoft.net.service.IEsearchSpecvaluesManager;
import com.ztesoft.net.service.IOrderExpManager;
import com.ztesoft.net.vo.CatalogAndSolution;

/**
 * 异常单管理
 * @author qin.yingxiong
 */
public class OrderExpAction extends WWAction {
	private static final long serialVersionUID = 1L;
	public static final String FLOW_TRACE_CACHE_KEY = "DIC_ORDER_NODE";
	public static final String ORDER_FROM_CACHE_KEY = "source_from";
	
	private ExpInstQueryInner eiqInner;
	private IOrderExpManager orderExpManager;
	@Resource
	private ICacheUtil cacheUtil;
	private OrderExpMarkProcessedInner oepInner;
	private String[] expInstIds;
	private String record_status;
	private String deal_result;
	private String is_return_back = "0"; //是否调用返回方法，默认0为不是，如果值为1则从session取查询条件添加到页面
	private String start_time;
	
	private String end_time;
	private String key_id;
	
	
	private ExpInstBatchProcessedInner eipInner;
	
	private ExpInstSpecInner eisInner;
	
	private List<TopExpKey> topExpKeys;
	private List<CatalogAndSolution> satalogAndSolutions;
	private String  shortcut_catalog_id;
	private String catalog_id;
	private String order_model;
	
	private EsearchExpInstQuery eeiq;
	//正常单查询条件
	private List<Map> flowTraceList;//流程列表
	private List<Regions> regionList;//地市列表
	private List<Map> order_from_list;//订单来源
	private List<EsearchSpecvalues> exceptionList; // 异常敞亮集合
	@Resource
	private IDcPublicInfoManager dcPublicInfoManager;
	@Resource
	private IRegionService regionService;
	@Resource
	private IEsearchSpecvaluesManager esearchSpecvaluesManager;
	private ExpInstSpecOnDayOuter specOuter;
	
	private List<Map> order_model_list;
	
	
	public String list() {
		if(eiqInner==null){
			eiqInner = new ExpInstQueryInner();
		}
		//返回，查询参数
		if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_return_back)){
			ThreadContextHolder.getSessionContext().setAttribute("orderexp_query_params", eiqInner);
		}else if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_return_back)){
			eiqInner = (ExpInstQueryInner)ThreadContextHolder.getSessionContext().getAttribute("orderexp_query_params");
		}

		if(!StringUtils.isEmpty(record_status)){
			eiqInner.setRecord_status(record_status);
		}
		//默认查过去一个月
		if(!StringUtils.isEmpty(start_time)){
			eiqInner.setStart_excp_time(start_time);
		}else{
			if(StringUtils.isEmpty(eiqInner.getStart_excp_time())){
				Calendar date = new GregorianCalendar();
				date.add(Calendar.MONTH, -1);
		        eiqInner.setStart_excp_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date.getTime()));
			}
		}
		if(!StringUtils.isEmpty(end_time)){
			eiqInner.setEnd_excp_time(end_time);
		}else{
			if(StringUtils.isEmpty(eiqInner.getEnd_excp_time())){
				eiqInner.setEnd_excp_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
			}
		}		
		if(!StringUtils.isEmpty(key_id)){
			eiqInner.setKey_id(key_id);
		}	
		this.webpage = orderExpManager.queryExpInstPage(eiqInner, ManagerUtils.getAdminUser(),this.getPage(),this.getPageSize());
		Page page = orderExpManager.topExpKey(null,null,1,10);//页面top10只需要展示10条数据
		topExpKeys = page.getResult();
		satalogAndSolutions = orderExpManager.qryCatalogAndSolution();
		
		this.listFlowTrace();//订单流程
		this.listOrderFrom();//订单来源
		this.listRegions();//地市列表
		this.listOrderExpceptionType();//人工标记异常类型
		
		return "list";
	}
	
	
	public String detail() {
		AdminUser admin = ManagerUtils.getAdminUser();
		eeiq = orderExpManager.getExpInstDetailById(eiqInner.getExcp_inst_id());
		return "detail";
	}
	
	public String expProcessed() {
		AdminUser admin = ManagerUtils.getAdminUser();
		if(eipInner == null || StringUtils.isEmpty(eipInner.getDeal_result())) {
			this.json = "{'outer_status':'-1','outer_msg':'处理结果不能为空'}";
			return WWAction.JSON_MESSAGE;
		}
		if(admin != null) {
			eipInner.setStaff_no(admin.getUserid());
		}else {
			this.json = "{'outer_status':'-1','outer_msg':'获取登录用户失败'}";
			return WWAction.JSON_MESSAGE;
		}
		if(expInstIds == null || expInstIds.length < 1) {
			this.json = "{'outer_status':'-1','outer_msg':'至少选择一条异常单信息'}";
			return WWAction.JSON_MESSAGE;
		}
		if(expInstIds.length > 50) {
			this.json = "{'outer_status':'-1','outer_msg':'最多不能超过50条'}";
			return WWAction.JSON_MESSAGE;
		}
		eipInner.setExcepInstIds(expInstIds);
		ExpInstBatchProcessedOuter outer = orderExpManager.expInstBatchProcessed(eipInner);
		this.json = JSON.toJSONString(outer);
    	return WWAction.JSON_MESSAGE;
	}
	
	public String expMark() {
		if(oepInner == null)
			oepInner = new OrderExpMarkProcessedInner();
		AdminUser admin = ManagerUtils.getAdminUser();
		if(StringUtils.isEmpty(record_status)) {
			this.json = "{'outer_status':'-1','outer_msg':'处理结果不能为空'}";
			return WWAction.JSON_MESSAGE;
		}
		if(admin != null) {
			oepInner.setDeal_staff_no(admin.getUserid());
		}else {
			this.json = "{'outer_status':'-1','outer_msg':'获取登录用户失败'}";
			return WWAction.JSON_MESSAGE;
		}
		
		if(expInstIds == null || expInstIds.length < 1) {
			this.json = "{'outer_status':'-1','outer_msg':'至少选择一条异常单信息'}";
			return WWAction.JSON_MESSAGE;
		}
		
		List<EsearchExpInst> eeis= new ArrayList<EsearchExpInst>();
		for(String expInstId:expInstIds) {
			EsearchExpInst eei = new EsearchExpInst();
			eei.setExcp_inst_id(expInstId);
			eei.setDeal_result(deal_result);
			eei.setRecord_status(record_status);
			eeis.add(eei);
		}
		oepInner.setExcepInsts(eeis);
		OrderExpMarkProcessedOuter outer = orderExpManager.orderExpMarkProcessed(oepInner);
		this.json = JSON.toJSONString(outer);
    	return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 订单环节
	 */
	private void listFlowTrace(){
		flowTraceList = getConsts(EcsOrderConsts.FLOW_TRACE_CACHE_KEY);
		if(flowTraceList==null){
			flowTraceList = new ArrayList<Map>();
		}
		Map m0 = new HashMap();
		m0.put("pkey", "-1");
		m0.put("pname", "--请选择--");
		flowTraceList.add(0, m0);
	}
	/**
	 * 地市查询条件
	 */
	private void listRegions(){
		RegionsListReq req = new RegionsListReq();
		req.setRegion_type(RegionsListReq.CITY);
		req.setRegion_parent_id(cacheUtil.getConfigInfo("PROVINCE_REGION_CODE"));
		RegionsListResp resp = regionService.listRegions(req);
		regionList = resp.getRegionList();
	}
	/**
	 * 人工标记异常类型
	 */
	private void listOrderExpceptionType(){
		SpecvalueInner inner = new SpecvalueInner();
		inner.setMatch_content("人工标记异常（");
		exceptionList = this.esearchSpecvaluesManager.querySpecvalues(inner);
	}
	/**
	 * 订单来源查询条件
	 * @作者 MoChunrun
	 * @创建日期 2014-10-9
	 */
	private void listOrderFrom() {
		order_from_list = getConsts(ORDER_FROM_CACHE_KEY);
		if (order_from_list == null) {
			order_from_list = new ArrayList<Map>();
		}else{
			//XMJ 修改订单来源排序,另外添加了 /ecsord_server/src/main/java/com/ztesoft/net/model/MapComparator.java/
			//如果你先调用MapComparator()排序,请确保sortby字段从1开始
//			for(Map order_from_map:order_from_list)
//				logger.info(order_from_map);
			//因为此处的MapComparator在ecsord_server下，因此使用匿名内部类
			try {
				Collections.sort(order_from_list,new Comparator<Map>(){

					@Override
					public int compare(Map o1, Map o2) {
						try {
							int b1 = Integer.valueOf(o1.get("sortby") + "");
							int b2 = Integer.valueOf(o2.get("sortby") + "");
							if (b1 == 0) {
								b1 = 9999;
							}
							if (b2 == 0) {
								b2 = 9999;
							}
							return b1 - b2;
						} catch (Exception e) {
							e.printStackTrace();
						}
						return 0;
					}
					
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private List<Map> getConsts(String key){
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
        List<Map> list = dcPublicCache.getList(key);
        return list;
	}
	
	public String specList(){
		if(eisInner==null){
			eisInner = new ExpInstSpecInner();
		}
		if(StringUtil.isEmpty(start_time)&&StringUtil.isEmpty(eisInner.getStart_time())){
			try {
				String endtime = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
				start_time = DateUtil.minusDate(endtime, "yyyy-MM-dd", 30, "day");
				eisInner.setStart_time(start_time);
			} catch (FrameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(StringUtil.isEmpty(end_time)&&StringUtil.isEmpty(eisInner.getEnd_time())){
			try {
				end_time = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
				eisInner.setEnd_time(end_time);
			} catch (FrameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.webpage = orderExpManager.queryExpInstSpecList(eisInner, this.getPage(), this.getPageSize());
		return "specList";
	}
	
	public String specViewList(){
		List list = orderExpManager.queryInstSpecOnDayList(start_time, end_time, key_id);
		String time = start_time;
		String match_content = orderExpManager.getContentBykeyid(key_id);
		specOuter = new ExpInstSpecOnDayOuter();
		specOuter.setText("关键字："+match_content);
		specOuter.setSubtext("该关键字在 "+start_time+"-"+end_time+" 期间生成异常的统计视图");
		int maxdata = 0;
		String xdata = "";
		String ydata = "";
		try{
			int i=0;
			while(compareDate(time,end_time,"yyyy-MM-dd")){
				Date date = DateUtil.parseStrToDate(time, "yyyy-MM-dd");
				String day = DateUtil.formatDate(date, "MM-dd");
				xdata = xdata + day + ",";
				if(i<list.size()){
					Map map = (Map)list.get(i);
					if(time.equals(map.get("cday").toString())){
						ydata = ydata + map.get("c").toString() + ",";
						int c = Integer.valueOf(map.get("c").toString());
						if(c>maxdata){
							maxdata = c;
						}
						i++;
					}else{
						ydata = ydata + "0,";
					}
				}else{
					ydata = ydata + "0,";
				}
				time = DateUtil.addDate(time, "yyyy-MM-dd", 1, "day");
			}
			maxdata = maxdata + maxdata/5;
			specOuter.setMaxdata(String.valueOf(maxdata));
			specOuter.setXdata(xdata.substring(0, xdata.length()-1));
			specOuter.setYdata(ydata.substring(0, ydata.length()-1));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "specViewList";
	}
	private String addDay(String time,String pattern){
		String t = time;
		try {
			t = DateUtil.addDate(time, pattern, 1, "day");
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}
	
	private Boolean compareDate(String time1,String time2,String pattern){
		int i = 1;
		try {
			i=DateUtil.compareDate(time1, time2, pattern);
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(i<=0){
			return true;
		}else{
			return false;
		}
	}
	
	public String specCatalogList(){
		listorderModel();
		this.webpage = orderExpManager.querySpecCatalogList(catalog_id, order_model, this.getPage(), this.getPageSize(),start_time,end_time);
		int count = orderExpManager.queryExpInstCount(start_time,end_time);
		List list = webpage.getResult();
		List result = new ArrayList();
		for(int i=0;i<list.size();i++){
			ExpInstSpecCatalogResp resp = (ExpInstSpecCatalogResp)list.get(i);
			int c = Integer.valueOf(resp.getCatalog_count());
			double percent = c*100/count;
			logger.info(percent);
			if(percent<0.1){
				resp.setPercent("0.1%");
			}else if(String.valueOf(percent).indexOf(".")>-1){
				String per = String.valueOf(percent);
				per = per.substring(0, per.indexOf(".")+2)+"%";
				resp.setPercent(per);
			}else{
				resp.setPercent(percent+".0%");
			}
			result.add(resp);
		}
		webpage.setResult(result);
		return "specCatalogList";
	}
	
	public IOrderExpManager getOrderExpManager() {
		return orderExpManager;
	}

	public void setOrderExpManager(IOrderExpManager orderExpManager) {
		this.orderExpManager = orderExpManager;
	}

	public ExpInstQueryInner getEiqInner() {
		return eiqInner;
	}

	public void setEiqInner(ExpInstQueryInner eiqInner) {
		this.eiqInner = eiqInner;
	}

	public List<TopExpKey> getTopExpKeys() {
		return topExpKeys;
	}

	public void setTopExpKeys(List<TopExpKey> topExpKeys) {
		this.topExpKeys = topExpKeys;
	}

	public String[] getExpInstIds() {
		return expInstIds;
	}

	public void setExpInstIds(String[] expInstIds) {
		this.expInstIds = expInstIds;
	}

	public OrderExpMarkProcessedInner getOepInner() {
		return oepInner;
	}

	public void setOepInner(OrderExpMarkProcessedInner oepInner) {
		this.oepInner = oepInner;
	}

	public List<CatalogAndSolution> getSatalogAndSolutions() {
		return satalogAndSolutions;
	}

	public void setSatalogAndSolutions(List<CatalogAndSolution> satalogAndSolutions) {
		this.satalogAndSolutions = satalogAndSolutions;
	}

	public String getShortcut_catalog_id() {
		return shortcut_catalog_id;
	}

	public void setShortcut_catalog_id(String shortcut_catalog_id) {
		this.shortcut_catalog_id = shortcut_catalog_id;
	}

	public EsearchExpInstQuery getEeiq() {
		return eeiq;
	}

	public void setEeiq(EsearchExpInstQuery eeiq) {
		this.eeiq = eeiq;
	}

	public ExpInstBatchProcessedInner getEipInner() {
		return eipInner;
	}

	public void setEipInner(ExpInstBatchProcessedInner eipInner) {
		this.eipInner = eipInner;
	}

	public String getRecord_status() {
		return record_status;
	}

	public void setRecord_status(String record_status) {
		this.record_status = record_status;
	}

	public String getDeal_result() {
		return deal_result;
	}

	public void setDeal_result(String deal_result) {
		this.deal_result = deal_result;
	}

	public String getIs_return_back() {
		return is_return_back;
	}

	public void setIs_return_back(String is_return_back) {
		this.is_return_back = is_return_back;
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

	public String getKey_id() {
		return key_id;
	}

	public void setKey_id(String key_id) {
		this.key_id = key_id;
	}

	public List<Map> getFlowTraceList() {
		return flowTraceList;
	}

	public void setFlowTraceList(List<Map> flowTraceList) {
		this.flowTraceList = flowTraceList;
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
	public ExpInstSpecInner getEisInner(){
		return eisInner;
	}
	public void setEisInner(ExpInstSpecInner eisInner){
		this.eisInner = eisInner;
	}
	public ExpInstSpecOnDayOuter getSpecOuter(){
		return specOuter;
	}
	public void setSpecOuter(ExpInstSpecOnDayOuter specOuter){
		this.specOuter = specOuter;
	}
	public List<EsearchSpecvalues> getExceptionList() {
		return exceptionList;
	}
	public void setExceptionList(List<EsearchSpecvalues> exceptionList) {
		this.exceptionList = exceptionList;
	}
	public String getCatalog_id() {
		return catalog_id;
	}

	public void setCatalog_id(String catalog_id) {
		this.catalog_id = catalog_id;
	}
	public String getOrder_model(){
		return order_model;
	}
	public void setOrder_model(String order_model){
		this.order_model = order_model;
	}
	public List<Map> getOrder_model_list() {
		return order_model_list;
	}

	public void setOrder_model_list(List<Map> order_model_list) {
		this.order_model_list = order_model_list;
	}
	private void listorderModel(){
		order_model_list = getConsts(StypeConsts.DIC_OPER_MODE);
		if(order_model_list==null){
			order_model_list = new ArrayList<Map>();
		}
		Map m0 = new HashMap();
		m0.put("pkey", "-1");
		m0.put("pname", "--请选择--");
		order_model_list.add(0,m0);
	}
}
