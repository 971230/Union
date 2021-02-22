package com.ztesoft.net.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.mchange.v2.lang.ThreadUtils;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.StringUtil; 
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.ecsord.params.ecaop.req.QryFtthObjIdReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.QryFtthObjIDResp;
import com.ztesoft.net.ecsord.params.ecaop.vo.FtthObjectInfo;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.DesUtil;
import com.ztesoft.net.framework.util.HttpClientUtils4Work;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Regions;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.RequestStoreManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.timer.PayAssureTimer;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.OrderWarning;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IOrdWarningManager;

import consts.ConstsCore;
import net.sf.json.JSONObject;
import util.ThreadLocalUtil;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.OrderAdslBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.AreaQueryReq;
import zte.net.ecsord.params.ecaop.req.QueryStdAddressReq;
import zte.net.ecsord.params.ecaop.req.ResourcePreCheckReq;
import zte.net.ecsord.params.ecaop.resp.AreaQueryResp;
import zte.net.ecsord.params.ecaop.resp.QueryStdAddrResp;
import zte.net.ecsord.params.ecaop.resp.ResourcePreCheckResp;
import zte.net.ecsord.params.ecaop.resp.vo.AddrInfos;
import zte.net.ecsord.params.ecaop.resp.vo.BrandInfoList;
import zte.net.ecsord.utils.GetAreaInfoUtils;
import zte.net.iservice.IRegionService;
import zte.params.region.req.RegionsListReq;
import zte.params.region.resp.RegionsListResp;

public class OrderWarningAction extends WWAction {
	
	@Resource
	private IOrdWarningManager ordWarningManager;
	@Resource
	private IDcPublicInfoManager dcPublicInfoManager;
	@Resource
	private IRegionService regionService;
	
	
	private OrderWarning orderWarning;
	private String  queryType;//edit-编辑，view-查看
	
	//静态数据
	private List<Map> order_from_list;
	private List<Regions> regionList;
	private List<Map> flowTraceList;
	private List<Map> goods_type_list;
	
	private String developmentCode;
	private String developmentName;
	private String countyName;
	private String stdArrName ;
	private String regionID ;
	private String first_load ;
	private String stdOrderId ;
	private String standard_addr_id ;
	private String post_code ;
	private String county_id;
	private String goodsName;
	private String new_goods_id;
	private String extraInfo;
	private String access_type;
	private String exch_code;
	private List<Map<String,String>> service_type_list;
	private String service_type;
	/**
	 * 查询列表
	 * @param orderWarning
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	
	public String searchList() {
		this.listFlowTrace();
		this.listOrderFrom();
		this.listRegions();
		this.listGoodType();
		this.webpage = ordWarningManager.queryList(orderWarning,this.getPage(), this.getPageSize());
		return "list";
	}
	
	public String queryDetails() {
		this.listFlowTrace();
		this.listOrderFrom();
		this.listRegions();
		this.listGoodType();
		orderWarning = ordWarningManager.queryOrderWarnById(orderWarning.getWarning_id());
		
		return "edit";
		
	}
	public String toAdd() {
		this.listFlowTrace();
		this.listOrderFrom();
		this.listRegions();
		this.listGoodType();
		//this.inserDataFromOldSys();//
		return "add";
	}
	
	/**
	 * 新增保存
	 * @return
	 */
	public String add() {
		try {
			String time = DateUtil.getTime2();
			String user=ManagerUtils.getAdminUser().getUsername();
			orderWarning.setCreate_time(time);
			orderWarning.setCreate_user(user);
			ordWarningManager.save(orderWarning);
			HttpServletRequest request = ServletActionContext.getRequest();
			String url=request.getContextPath()+EcsOrderConsts.WARNING_LIST_URL;
			Map map=new HashMap();
			map.put("列表", url);
			this.urls=map;
			
			this.msgs.add("操作成功");
		} catch (Exception e) {
			
			this.msgs.add("操作失败："+e.getMessage());
			e.printStackTrace();
		}
		return this.MESSAGE;
	}
	/**
	 * 更新
	 * @return
	 */
	public String edit() {
		try {
			String time = DateUtil.getTime2();
			String user=ManagerUtils.getAdminUser().getUsername();
			orderWarning.setUpdate_time(time);
			orderWarning.setUpdate_user(user);
			ordWarningManager.update(orderWarning);
			
			HttpServletRequest request = ServletActionContext.getRequest();
			String url=request.getContextPath()+EcsOrderConsts.WARNING_LIST_URL;
			Map map=new HashMap();
			map.put("列表", url);
			this.urls=map;
			
			this.msgs.add("操作成功");
		} catch (Exception e) {
			
			 this.msgs.add("操作失败："+e.getMessage());
			e.printStackTrace();
		}
		 return this.MESSAGE;
	}
	
	/**
	 * 百度ftp文件上传定时任务测试方法
	 * @return
	 */
	public String payAssureTimerTest() {
		PayAssureTimer pay=new PayAssureTimer();
		pay.run();
		return this.MESSAGE;
		
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 订单环节
	 */
	public void listFlowTrace(){
		flowTraceList = getConsts(StypeConsts.DIC_ORDER_NODE);
		if(flowTraceList==null){
			flowTraceList = new ArrayList<Map>();
		}
		Map m0 = new HashMap();
		m0.put("pkey", "");
		m0.put("pname", "");
		flowTraceList.add(0, m0);
	}
	/**
	 * 订单来源查询条件
	 */
	private void listOrderFrom(){
		order_from_list = getConsts(StypeConsts.SOURCE_FROM);
		if(order_from_list==null){
			order_from_list = new ArrayList<Map>();
		}
		Map m0 = new HashMap();
		m0.put("pkey", "");
		m0.put("pname", "");
		order_from_list.add(0, m0);
	}
	
	/**
	 * 地市查询条件
	 */
	private void listRegions(){
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String province_region = cacheUtil.getConfigInfo("PROVINCE_REGION_CODE");
		
		RegionsListReq req = new RegionsListReq();
		req.setRegion_type(RegionsListReq.CITY);
		req.setRegion_parent_id(province_region);
		RegionsListResp resp = regionService.listRegions(req);
		regionList = resp.getRegionList();
		Regions r=new Regions();
		r.setP_region_id("");
		r.setLocal_name("");
		regionList.add(0,r);
	}
	
	/**
	 * 商品类型查询条件
	 */
	private void listGoodType(){
		goods_type_list=getConsts(StypeConsts.GOODS_TYPE);
		Map m0 = new HashMap();
		m0.put("pkey", "");
		m0.put("pname", "");
		goods_type_list.add(0, m0);
	}
	
	private List<Map> getConsts(String key){
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
        List<Map> list = dcPublicCache.getList(key);
        return list;
	}
	
	public String getDevelopmentList(){
    	Page p = ordWarningManager.getDevelopmentList(this.developmentCode,this.developmentName, this.getPage(), this.getPageSize());
    	this.webpage = p;
    	return "dev_list";
    }

	public String getXcountyList() {
		logger.info("stdOrderId ====== "+stdOrderId);
		String region_id = ordWarningManager.qryAreaIdByOrderId(stdOrderId.split(",")[0]);
		this.webpage = ordWarningManager.getXcountyList(countyName,region_id,this.getPage(), this.getPageSize());
		return "county_list";
	}
	
	//获取标准地址
	public String getStdAddress() {
		List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
		//返回数据条数
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String rowNum = cacheUtil.getConfigInfo("RETURN_ROW_NUM");
		if(first_load.equals(EcsOrderConsts.NO_FIRST_LOAD)) {
			QueryStdAddressReq req = new QueryStdAddressReq();
			String region_id = ordWarningManager.qryAreaIdByOrderId(stdOrderId.split(",")[0]);
				
			req.setKey_word(stdArrName);
			req.setRow_num(rowNum);
			req.setRegion_id(region_id);
				
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			try {
				QueryStdAddrResp resp = client.execute(req, QueryStdAddrResp.class);
				if(resp.getCode().equals(EcsOrderConsts.BSS_SUCCESS_00000)) {
					List<AddrInfos> addrInfos = resp.getRespJson().getAddr_infos();
						
					for (AddrInfos addrInfo : addrInfos) {
						Map<String,String> dataMap = new HashMap<String,String>();
						dataMap.put("standard_addr_id",addrInfo.getStandard_addr_id() );
						dataMap.put("post_code", addrInfo.getPost_code());
						dataMap.put("standard_addr_name", addrInfo.getStandard_addr_name());
						dataList.add(dataMap);
					}
				}
			} catch (Exception e) {
				this.json = "未根据输入的条件查到标准地址。";
				return this.JSON_MESSAGE;
			}	
		}
		int num = Integer.valueOf(rowNum);
		int count = num<=dataList.size()?num:dataList.size();
		Page dataPage = new Page();
		dataPage.setData(dataList);
		dataPage.setTotalCount(count);
		dataPage.setStart(0);
		dataPage.setPageSize(count);
		
		this.webpage = dataPage ;
		return "stdAddress_list";
	}
	
	public String queryModermId () {
		List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
		QryFtthObjIdReq ftthReq = new QryFtthObjIdReq();
		QryFtthObjIDResp ftthRsp = new QryFtthObjIDResp();
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		if(StringUtil.isEmpty(extraInfo)){
			extraInfo = CommonDataFactory.getInstance().getAttrFieldValue(stdOrderId, "extra_info");
		}
		ftthReq.setRegion_id(ordWarningManager.qryAreaIdByOrderId(stdOrderId));
		ftthReq.setExtra_info(extraInfo);
		ftthReq.setNotNeedReqStrOrderId(stdOrderId);
		
		ftthRsp=client.execute(ftthReq, QryFtthObjIDResp.class);
		if(ftthRsp.getCode().equals(EcsOrderConsts.BSS_SUCCESS_00000)) {
			try {
				List<FtthObjectInfo> resultList = ftthRsp.getRespJson().getObject_list();
				for (FtthObjectInfo ftthObjectInfo : resultList) {
					Map<String,String>dataMap = new HashMap<String,String>();
					dataMap.put("object_id", ftthObjectInfo.getObject_id());
					dataMap.put("object_name", ftthObjectInfo.getObject_name());
					dataMap.put("onu_mode", ftthObjectInfo.getOnu_mode());
					
					dataList.add(dataMap);
				}
			} catch (Exception e) {
				// TODO: handle exception
				this.json = "未查到相关光猫资源，请重试。";
				return this.JSON_MESSAGE;
			}
		}

		Page dataPage = new Page();
		dataPage.setData(dataList);
		dataPage.setTotalCount(dataList.size());
		dataPage.setPageSize(dataList.size());
		dataPage.setStart(this.getPage());
		
		this.webpage = dataPage;
		return "modermid_list";
	}
	
	public String areaQuery() {
		AreaQueryReq req = new AreaQueryReq();
		req.setNotNeedReqStrOrderId(stdOrderId);
		
		AreaQueryResp resp = new AreaQueryResp();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		resp = client.execute(req, AreaQueryResp.class);
		
		if(StringUtils.equals(resp.getCode(), EcsOrderConsts.INF_RESP_CODE_00000)) {
			String cityCode = resp.getRespJson().getCityCode();
			if(StringUtils.isEmpty(cityCode)) {
				this.json = "{result:-1,msg:'自动查询失败，请手动选择营业县分。'}";
			}else {
				List countyList = ordWarningManager.getDcSqlByDcName("DC_ORDER_BUSICTY_NEW");
				String county_name = "";
					if(countyList!=null){
						for (int i = 0; i < countyList.size(); i++) {
							String value = Const.getStrValue((Map) countyList.get(i), "value");
							String value_desc = Const.getStrValue((Map) countyList.get(i), "value_desc");
							if (StringUtil.equals(value, cityCode)) {
								county_name = value_desc;
								break;
							}
						}
					}
				this.json = "{result:0,msg:'查询成功。',data:'"+county_name+","+cityCode+"'}";
			}
		}else {
			this.json = "{result:-1,msg:'自动查询失败，请手动选择营业县分。'}";
		}
		return this.JSON_MESSAGE;
	}
	

	public String queryGoodsList(){
		String line_type = CommonDataFactory.getInstance().getAttrFieldValue(stdOrderId, "line_type");
		String[] line_types = line_type.split(",");
		List<Map<String,String>> service_type_list1 = new ArrayList();
		Map<String,String> line_type_map = new HashMap();
		for (int i = 0; i < line_types.length; i++) {
			line_type_map = new HashMap();
			line_type_map.put("value", line_types[i]);
			line_type_map.put("value_desc", CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_SERVICE_TYPE", line_types[i]));
			service_type_list1.add(line_type_map);
		}
		service_type_list = service_type_list1;
		this.webpage = ordWarningManager.queryGoodsList(goodsName,stdOrderId,line_type,service_type,this.getPage(),this.getPageSize());
		return "new_goods_list";
		
	}
	
	public String changeNewGoods(){
		this.json = ordWarningManager.changeNewGoods(new_goods_id,stdOrderId);
		return this.JSON_MESSAGE;
		
	}

	public String resourcePre() {
		//当前商品的业务类型编码和名称
		String goods_service_type = CommonDataFactory.getInstance().getGoodSpec(stdOrderId, null, AttrConsts.ORDER_SERVICE_TYPE);
		String goods_service_name = CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_SERVICE_TYPE", goods_service_type);
		
		ResourcePreCheckReq req = new ResourcePreCheckReq();	
		ResourcePreCheckResp rsp = new ResourcePreCheckResp();
		
		req.setNotNeedReqStrOrderId(stdOrderId);
		String eparchy_code = GetAreaInfoUtils.getEparchyCode(stdOrderId);
		Map<String,Object>map = new HashMap<String,Object>();
		map.put("ANTI_TYPE", "0");//根据标准地址预判
		map.put("ANTI_MODE", "0");//0-按产品目录预判
		map.put("EPARCHY_CODE", eparchy_code);//地市编码 Todo
		map.put("TRADE_TYPE_CODE", "0010");//业务受理类型
		map.put("SHARE_TYPE", "N");//共线类型
		map.put("PROD_CAT_ID", "P04");//产品目录编码
		map.put("EXCH_CODE", post_code);//局向编码 post_code	
		map.put("CITY_CODE", county_id);//营业区编码
		map.put("SERVICE_CLASS_CODE", "0040");//共线电信类型
		map.put("AREA_CODE", GetAreaInfoUtils.getAreaCode(eparchy_code));//共线区号
		map.put("ADDRESS_CODE",standard_addr_id);//标准地址编码  standard_addr_id 
		JSONObject json = JSONObject.fromObject(map);
		req.setMsg(json);
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		rsp = client.execute(req, ResourcePreCheckResp.class);
		
		if(StringUtils.equals(rsp.getCode(), EcsOrderConsts.INF_RESP_CODE_00000)) {
			if(null == rsp.getRespJson().getBRAND_LIST() || rsp.getRespJson().getBRAND_LIST().size() == 0) {
				this.json = "{result:-1,msg:'该地址无可用资源，请重新选择标准地址'}";
			}else {
				List<BrandInfoList> brand_list = rsp.getRespJson().getBRAND_LIST();
				int m = 0;
				String brand_name = "";
				List<String> brandCodes = new ArrayList<String>();
				for (BrandInfoList brandInfoList : brand_list) {
					//
					String brand_code = brandInfoList.getBRAND_CODE();
					if(StringUtils.isNotEmpty(brand_code)) {
						brandCodes.add(brand_code);
					}
					//预判返回若是ADSL商品，不需要资源预判
//					if(StringUtils.equals(brand_code, "6005") && brand_list.size() == 1) {
//						this.json = "{result:-1,msg:'无可用资源'}";
//						return this.JSON_MESSAGE;
//					}
					//如果返回的业务类型与当前商品相同,当前不会有ADSL的商品，如果返回的是adsl 6005,判断也不会通过
					if(StringUtils.equals(goods_service_type, brand_code)) {
						m = 1;
						brand_name += CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_SERVICE_TYPE", brand_code)+",";
						//break;
					}else {
						brand_name += CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_SERVICE_TYPE", brand_code)+",";
					}
				}
				//将资源预判返回的业务类型入库
				String values = StringUtils.join(brandCodes, ",");
				CommonDataFactory.getInstance().updateAttrFieldValue(stdOrderId, new String[]{"line_type"},new String[]{values});
				if( m == 1) {
					//之后光猫ID查询需要该值
					String extra_info = rsp.getRespJson().getEXTRA_INFO();
					String access_type = rsp.getRespJson().getBRAND_LIST().get(0).getACCESS_LIST().get(0).getACCESS_TYPE();
					CommonDataFactory.getInstance().updateAttrFieldValue(stdOrderId, new String[]{"extra_info"},new String[]{extra_info});
					String data = extra_info+"|"+access_type;
					this.json = "{result:0,msg:'该地址资源可用。'"+",data:'"+data+"'}";
				}else {
					String extra_info = rsp.getRespJson().getEXTRA_INFO();
					String access_type = rsp.getRespJson().getBRAND_LIST().get(0).getACCESS_LIST().get(0).getACCESS_TYPE();
					CommonDataFactory.getInstance().updateAttrFieldValue(stdOrderId, new String[]{"extra_info"},new String[]{extra_info});
					String data = extra_info+"|"+access_type;
					this.json = "{result:0,msg:'标准地址支持"+brand_name+"当前商品类型为"+goods_service_name+",需要在选择标准地址后重新选择商品。'"+",data:'"+data+"'}";
					//this.json = "{result:-1,msg:'预判不通过，原因:标准地址支持"+brand_name+",当前商品类型为"+goods_service_name+",请重新选择标准地址或调整商品。'}";
				}
			}
		}else {
			this.json = "{result:-1,msg:'该地址无可用资源，请重新选择标准地址'}";
		}
		return this.JSON_MESSAGE;
	}
	
	public String saveStdAddress(){
		try{
			INetCache cache = (INetCache) com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(stdOrderId);
			List<OrderAdslBusiRequest> orderAdslBusiRequests = orderTree.getOrderAdslBusiRequest();
			Map<String,String> old_map = new HashMap();
			Map<String,String> new_map = new HashMap();
			old_map.put("access_type", orderAdslBusiRequests.get(0).getAccess_type());
			old_map.put("exch_code", orderAdslBusiRequests.get(0).getExch_code());
			old_map.put("adsl_addr", orderAdslBusiRequests.get(0).getAdsl_addr());
			old_map.put("adsl_addr_desc", orderAdslBusiRequests.get(0).getAdsl_addr_desc());
			new_map.put("access_type", access_type);
			new_map.put("exch_code", exch_code);
			new_map.put("adsl_addr", standard_addr_id);
			new_map.put("adsl_addr_desc", stdArrName);
			IEcsOrdManager ecsOrdManager = SpringContextHolder.getBean("ecsOrdManager");
			ecsOrdManager.saveChange(old_map,new_map,stdOrderId);
			for (OrderAdslBusiRequest orderAdslBusiRequest : orderAdslBusiRequests) {
				orderAdslBusiRequest.setAccess_type(access_type);
				orderAdslBusiRequest.setExch_code(exch_code);
				orderAdslBusiRequest.setAdsl_addr(standard_addr_id);
				orderAdslBusiRequest.setAdsl_addr_desc(stdArrName);
			}
			String update_sql = " update es_order_zhwq_adsl  set access_type=?,exch_code=?,adsl_addr=?,adsl_addr_desc=? where order_id=? ";
			IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
			baseDaoSupport.execute(update_sql, new String[]{access_type,exch_code,standard_addr_id,stdArrName,stdOrderId});
			cache.set(RequestStoreManager.NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY + "order_id" + stdOrderId,
					orderTree, RequestStoreManager.time);
			this.json = "{result:0,msg:'操作成功。'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{result:1,msg:'操作失败。'}";
		}
		
		return this.JSON_MESSAGE;
	}

	private void inserDataFromOldSys(){
		ordWarningManager.inserDataFromOldSys();
	}
	public IOrdWarningManager getOrdWarningManager() {
		return ordWarningManager;
	}

	public void setOrdWarningManager(IOrdWarningManager ordWarningManager) {
		this.ordWarningManager = ordWarningManager;
	}

	public IRegionService getRegionService() {
		return regionService;
	}

	public void setRegionService(IRegionService regionService) {
		this.regionService = regionService;
	}

	public OrderWarning getOrderWarning() {
		return orderWarning;
	}

	public void setOrderWarning(OrderWarning orderWarning) {
		this.orderWarning = orderWarning;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public List<Map> getOrder_from_list() {
		return order_from_list;
	}

	public void setOrder_from_list(List<Map> order_from_list) {
		this.order_from_list = order_from_list;
	}

	public List<Regions> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<Regions> regionList) {
		this.regionList = regionList;
	}

	public List<Map> getFlowTraceList() {
		return flowTraceList;
	}

	public void setFlowTraceList(List<Map> flowTraceList) {
		this.flowTraceList = flowTraceList;
	}

	public List<Map> getGoods_type_list() {
		return goods_type_list;
	}

	public void setGoods_type_list(List<Map> goods_type_list) {
		this.goods_type_list = goods_type_list;
	}
	
	public String getDevelopmentCode(){
		return this.developmentCode;
	}
	public void setDevelopmentCode(String developmentCode){
		this.developmentCode = developmentCode;
	}
	public String getDevelopmentName(){
		return this.developmentName;
	}
	public void setDevelopmentName(String developmentName){
		this.developmentName = developmentName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getStdArrName() {
		return stdArrName;
	}

	public void setStdArrName(String stdArrName) {
		this.stdArrName = stdArrName;
	}

	public String getRegionID() {
		return regionID;
	}

	public void setRegionID(String regionID) {
		this.regionID = regionID;
	}

	public String getFirst_load() {
		return first_load;
	}

	public void setFirst_load(String first_load) {
		this.first_load = first_load;
	}

	public String getStdOrderId() {
		return stdOrderId;
	}

	public void setStdOrderId(String stdOrderId) {
		this.stdOrderId = stdOrderId;
	}

	public String getStandard_addr_id() {
		return standard_addr_id;
	}

	public void setStandard_addr_id(String standard_addr_id) {
		this.standard_addr_id = standard_addr_id;
	}

	public String getPost_code() {
		return post_code;
	}

	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}

	public String getCounty_id() {
		return county_id;
	}

	public void setCounty_id(String county_id) {
		this.county_id = county_id;
	}


	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getNew_goods_id() {
		return new_goods_id;
	}

	public void setNew_goods_id(String new_goods_id) {
		this.new_goods_id = new_goods_id;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	public String getAccess_type() {
		return access_type;
	}

	public void setAccess_type(String access_type) {
		this.access_type = access_type;
	}

	public String getExch_code() {
		return exch_code;
	}

	public void setExch_code(String exch_code) {
		this.exch_code = exch_code;
	}

	public List<Map<String, String>> getService_type_list() {
		return service_type_list;
	}

	public void setService_type_list(List<Map<String, String>> service_type_list) {
		this.service_type_list = service_type_list;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	
}
