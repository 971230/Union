package com.ztesoft.net.action;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.nd.vo.Organization;
import zte.net.ecsord.params.workCustom.po.ES_USER_TEAM;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_CFG;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_CONNECT;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_DEALER;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_NODE;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_NODE_INS;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_WORKFLOW_INS;
import zte.net.ecsord.params.workCustom.po.WORK_CUSTOM_FLOW_DATA;

import com.alibaba.fastjson.JSONObject;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.database.SqlBuilderInterface;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IOrdIntentManager;
import com.ztesoft.net.service.IOrdWorkManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomCfgManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomEngine;
import com.ztesoft.net.sqls.SqlBuilder;
import com.ztesoft.net.sqls.SqlLikeBuilder;
import com.ztesoft.net.sqls.SqlUtil;

import consts.ConstsCore;

public class WorkCustomAction extends WWAction {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7188223018553703595L;
	
	private ES_WORK_CUSTOM_CFG cfg;
	
	private ES_WORK_CUSTOM_NODE node;
	
	private ES_WORK_CUSTOM_CONNECT connect;
	
	private ES_WORK_CUSTOM_DEALER dealer;
	
	private List<ES_WORK_CUSTOM_CFG> cfgs;
	
	private List<ES_WORK_CUSTOM_NODE> nodes;
	
	private List<ES_WORK_CUSTOM_CONNECT> connects;
	
	private List<ES_WORK_CUSTOM_DEALER> dealers;
	
	private ES_WORK_CUSTOM_WORKFLOW_INS workflow;
	
	private List<ES_WORK_CUSTOM_WORKFLOW_INS> workflows;
	
	private ES_WORK_CUSTOM_NODE_INS ins;
	
	private List<ES_WORK_CUSTOM_NODE_INS> inses;
	
	private List<ES_WORK_CUSTOM_NODE_INS> inses_his;
	
	private String order_id ;
	
	private Map<String, Object> acceptDetail;// 预受理客户信息
	
	private List<Map<String, Object>> countyList;// 县分列表
	
	private ES_USER_TEAM team;
	
	@Resource
	private IOrdWorkManager ordWorkManager;
	
	@Resource
	private IOrdIntentManager ordIntentManager;


	public Map<String, Object> getAcceptDetail() {
		return acceptDetail;
	}

	public void setAcceptDetail(Map<String, Object> acceptDetail) {
		this.acceptDetail = acceptDetail;
	}

	public List<Map<String, Object>> getCountyList() {
		return countyList;
	}

	public void setCountyList(List<Map<String, Object>> countyList) {
		
		this.countyList = countyList;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public ES_WORK_CUSTOM_CFG getCfg() {
		return cfg;
	}

	public void setCfg(ES_WORK_CUSTOM_CFG cfg) {
		this.cfg = cfg;
	}

	public ES_WORK_CUSTOM_NODE getNode() {
		return node;
	}

	public void setNode(ES_WORK_CUSTOM_NODE node) {
		this.node = node;
	}

	public ES_WORK_CUSTOM_CONNECT getConnect() {
		return connect;
	}

	public void setConnect(ES_WORK_CUSTOM_CONNECT connect) {
		this.connect = connect;
	}

	public ES_WORK_CUSTOM_DEALER getDealer() {
		return dealer;
	}

	public void setDealer(ES_WORK_CUSTOM_DEALER dealer) {
		this.dealer = dealer;
	}

	public List<ES_WORK_CUSTOM_CFG> getCfgs() {
		return cfgs;
	}

	public void setCfgs(List<ES_WORK_CUSTOM_CFG> cfgs) {
		this.cfgs = cfgs;
	}

	public List<ES_WORK_CUSTOM_NODE> getNodes() {
		return nodes;
	}

	public void setNodes(List<ES_WORK_CUSTOM_NODE> nodes) {
		this.nodes = nodes;
	}

	public List<ES_WORK_CUSTOM_CONNECT> getConnects() {
		return connects;
	}

	public void setConnects(List<ES_WORK_CUSTOM_CONNECT> connects) {
		this.connects = connects;
	}

	public List<ES_WORK_CUSTOM_DEALER> getDealers() {
		return dealers;
	}

	public void setDealers(List<ES_WORK_CUSTOM_DEALER> dealers) {
		this.dealers = dealers;
	}

	public List<ES_WORK_CUSTOM_NODE_INS> getInses() {
		return inses;
	}

	public void setInses(List<ES_WORK_CUSTOM_NODE_INS> inses) {
		this.inses = inses;
	}

	public List<ES_WORK_CUSTOM_NODE_INS> getInses_his() {
		return inses_his;
	}

	public void setInses_his(List<ES_WORK_CUSTOM_NODE_INS> inses_his) {
		this.inses_his = inses_his;
	}
	
	public ES_WORK_CUSTOM_NODE_INS getIns() {
		return ins;
	}

	public void setIns(ES_WORK_CUSTOM_NODE_INS ins) {
		this.ins = ins;
	}
	
	public ES_WORK_CUSTOM_WORKFLOW_INS getWorkflow() {
		return workflow;
	}

	public void setWorkflow(ES_WORK_CUSTOM_WORKFLOW_INS workflow) {
		this.workflow = workflow;
	}
	
	public List<ES_WORK_CUSTOM_WORKFLOW_INS> getWorkflows() {
		return workflows;
	}

	public void setWorkflows(List<ES_WORK_CUSTOM_WORKFLOW_INS> workflows) {
		this.workflows = workflows;
	}
	
	public ES_USER_TEAM getTeam() {
		return team;
	}

	public void setTeam(ES_USER_TEAM team) {
		this.team = team;
	}
	
	@Resource
	private IWorkCustomCfgManager workCustomCfgManager;
	
	@Resource
	private IWorkCustomEngine workCustomEngine;
	
	@Resource
	private IEcsOrdManager ecsOrdManager;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getOrderFrom() throws Exception{
		List orderFrom = ecsOrdManager.getDcSqlByDcName("ORDER_FROM");
		
		if(this.cfg != null){
			System.out.println("hello");
		}
		
		Map retMap = new HashMap();
		
		retMap.put("orderFrom", orderFrom);
		
		this.json = JSONObject.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	public String getNodeEditUrl(){
		return "node_edit";
	}
	
	public String getLabelEditUrl(){
		return "label_edit";
	}
	
	public String getWorkCustomCfgURL(){
		return "work_custom_cfg";
	}
	
	public String getCustomCfgEditUrl(){
		return "custom_cfg_edit";
	}
	
	public String getDealerEdit(){
		return "dealer_edit";
	}
	
	public String getSearchOrg(){
		return "search_org";
	}
	
	public String getWorkCustomCfgTable() throws Exception {
		List<SqlBuilderInterface> sqlBuilds = new ArrayList<SqlBuilderInterface>();
		
		if(this.cfg == null)
			return "work_custom_cfg_table";
		
		ES_WORK_CUSTOM_CFG pojo = new ES_WORK_CUSTOM_CFG();
		
		pojo.setCfg_id(this.cfg.getCfg_id());
		pojo.setVersion_id(this.cfg.getVersion_id());
		pojo.setCfg_level(this.cfg.getCfg_level());
		pojo.setGoods_type_id(this.cfg.getGoods_type_id());
		pojo.setGoods_cat_id(this.cfg.getGoods_cat_id());
		pojo.setGoods_id(this.cfg.getGoods_id());
		pojo.setOrder_from(this.cfg.getOrder_from());
		
		if(StringUtils.isNotBlank(this.cfg.getCfg_name())){
			String name = URLDecoder.decode(this.cfg.getCfg_name(), "UTF-8");
			this.cfg.setCfg_name(name);
		}
		
		SqlLikeBuilder cfgName = new SqlLikeBuilder("cfg_name",this.cfg.getCfg_name());
		
		sqlBuilds.add(cfgName);
		
		if("1".equals(this.cfg.getExt_1())){
			
			//查询当前版本
			this.webpage = workCustomCfgManager.qryCfgPageByPojo(pojo, sqlBuilds, this.getPage(), this.getPageSize());
		}else{
			//查询历史版本
			this.webpage = workCustomCfgManager.qryCfgHisPageByPojo(pojo, sqlBuilds, this.getPage(), this.getPageSize());
		}
		
		return "work_custom_cfg_table";
	}
	
	@SuppressWarnings("unchecked")
	public String getWorkCustomDealTable() throws Exception {
		List<SqlBuilderInterface> sqlBuilds = new ArrayList<SqlBuilderInterface>();
		
		if(this.dealer == null)
			return "work_custom_deal_table";
		
		ES_WORK_CUSTOM_DEALER pojo = new ES_WORK_CUSTOM_DEALER();
		
		pojo.setCfg_id(this.dealer.getCfg_id());
		pojo.setDeal_level(this.dealer.getDeal_level());
		pojo.setRegion_id(this.dealer.getRegion_id());
		pojo.setCounty_id(this.dealer.getCounty_id());
		pojo.setNode_id(this.dealer.getNode_id());
		pojo.setNode_index(this.dealer.getNode_index());
		pojo.setD_version_id(this.dealer.getD_version_id());
		pojo.setDeal_id(this.dealer.getDeal_id());
		
		if("1".equals(this.dealer.getExt_1())){
			//查询当前版本
			this.webpage = workCustomCfgManager.qryDealPageByPojo(pojo, sqlBuilds, this.getPage(), this.getPageSize());
		}else{
			//查询历史版本
			this.webpage = workCustomCfgManager.qryDealHisPageByPojo(pojo, sqlBuilds, this.getPage(), this.getPageSize());
		}
		
		AdminUser user = ManagerUtils.getAdminUser();
		//判断用户权限，不在权限范围内的记录不许修改
		if(user != null && this.webpage.getResult()!=null 
				&& "3".equals(user.getPermission_level())){
			List<String> permission_regions = user.getPermission_region();
			List<String> permission_countys = user.getPermission_county();
			
			List<ES_WORK_CUSTOM_DEALER> list = this.webpage.getResult();
			
			for(int i=0;i<list.size();i++){
				if(!permission_regions.contains(list.get(i).getRegion_id()) || 
						!permission_countys.contains(list.get(i).getCounty_id())){
					list.get(i).setState("2");
				}
			}
		}
		
		return "work_custom_deal_table";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getWorkCustomCfgById() throws Exception{
		Map retMap = new HashMap();
		
		try{
			if(this.cfg ==  null)
				throw new Exception("传入流程信息为空");
			
			if(StringUtils.isBlank(this.getCfg().getCfg_id()))
				throw new Exception("传入流程编号为空");
			
			String cfg_id = this.getCfg().getCfg_id();
			
			ES_WORK_CUSTOM_CFG pojoCfg = new ES_WORK_CUSTOM_CFG();
			pojoCfg.setCfg_id(cfg_id);
			List<ES_WORK_CUSTOM_CFG> retCfgs = workCustomCfgManager.qryCfgList(pojoCfg, null);
			
			ES_WORK_CUSTOM_NODE nodePojo = new ES_WORK_CUSTOM_NODE();
			nodePojo.setCfg_id(cfg_id);
			List<ES_WORK_CUSTOM_NODE> retNodes = workCustomCfgManager.qryNodeList(nodePojo, null);
			
			ES_WORK_CUSTOM_CONNECT connectPojo = new ES_WORK_CUSTOM_CONNECT();
			connectPojo.setCfg_id(cfg_id);
			List<ES_WORK_CUSTOM_CONNECT> retConnects = workCustomCfgManager.qryConnectList(connectPojo, null);
			
			if(retCfgs==null || retCfgs.size()==0)
				throw new Exception("根据流程编号"+cfg_id+"未查到流程配置");
			
			if(retCfgs.size()>1)
				throw new Exception("根据流程编号"+cfg_id+"查到多条流程配置");
			
			//获取当前操作员
			AdminUser user = ManagerUtils.getAdminUser();
			String org_id = user.getOrg_id();
			if("07XX".equals(org_id))
				org_id = "0010";
			
			//获取登录组织
			Organization pojo = new Organization();
			pojo.setParty_id(org_id);
			List<Organization> orgs = this.workCustomCfgManager.qryOrganizationList(pojo, null);
			
			Organization org = new Organization();
			if(orgs!=null && orgs.size()>0)
				org = orgs.get(0);
			
			retMap.put("code", "0");
			retMap.put("cfg", retCfgs.get(0));
			retMap.put("nodes", retNodes);
			retMap.put("connects", retConnects);
			retMap.put("user", user);
			retMap.put("org", org);
		}catch(Exception e){
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}
		
		this.json = JSONObject.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getWorkCustomCfgByVersion() throws Exception{
		Map retMap = new HashMap();
		
		try{
			if(this.cfg ==  null)
				throw new Exception("传入流程信息为空");
			
			if(StringUtils.isBlank(this.getCfg().getVersion_id()))
				throw new Exception("传入版本编号为空");
			
			String versionId = this.getCfg().getVersion_id();
			
			ES_WORK_CUSTOM_CFG pojoCfg = new ES_WORK_CUSTOM_CFG();
			pojoCfg.setVersion_id(versionId);
			List<ES_WORK_CUSTOM_CFG> retCfgs = workCustomCfgManager.qryCfgListFromAll(pojoCfg, null);
			
			ES_WORK_CUSTOM_NODE nodePojo = new ES_WORK_CUSTOM_NODE();
			nodePojo.setVersion_id(versionId);
			List<ES_WORK_CUSTOM_NODE> retNodes = workCustomCfgManager.qryNodeListFromAll(nodePojo, null);
			
			ES_WORK_CUSTOM_CONNECT connectPojo = new ES_WORK_CUSTOM_CONNECT();
			connectPojo.setVersion_id(versionId);
			List<ES_WORK_CUSTOM_CONNECT> retConnects = workCustomCfgManager.qryConnectListFromAll(connectPojo, null);
			
			if(retCfgs==null || retCfgs.size()==0)
				throw new Exception("根据版本编号"+versionId+"未查到流程配置");
			
			if(retCfgs.size()>1)
				throw new Exception("根据版本编号"+versionId+"查到多条流程配置");
			
			//获取当前操作员
			AdminUser user = ManagerUtils.getAdminUser();
			String org_id = user.getOrg_id();
			if("07XX".equals(org_id))
				org_id = "0010";
			
			//获取登录组织
			Organization pojo = new Organization();
			pojo.setParty_id(org_id);
			List<Organization> orgs = this.workCustomCfgManager.qryOrganizationList(pojo, null);
			
			Organization org = new Organization();
			if(orgs!=null && orgs.size()>0)
				org = orgs.get(0);

			retMap.put("code", "0");
			retMap.put("cfg", retCfgs.get(0));
			retMap.put("nodes", retNodes);
			retMap.put("connects", retConnects);
			retMap.put("user", user);
			retMap.put("org", org);
		}catch(Exception e){
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}
		
		this.json = JSONObject.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	public String addWorkCustomCfg() throws Exception{
		
		Map<String,Object> retMap = new HashMap<String, Object>();
		
		retMap.put("code", "0");
		retMap.put("msg", "新增成功");
		
		try{
			workCustomCfgManager.addCfg(this.cfg, this.nodes, this.connects);
		}catch(Exception e){
			retMap.put("code", "1");
			retMap.put("msg", "新增失败" + e.getMessage());
		}
		
		this.json = JSONObject.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	public String updateWorkCustomCfg() throws Exception{
		Map<String,Object> retMap = new HashMap<String, Object>();
		
		retMap.put("code", "0");
		retMap.put("msg", "修改成功");
		
		try{
			workCustomCfgManager.updateCfg(this.cfg, this.nodes, this.connects);
		}catch(Exception e){
			retMap.put("code", "1");
			retMap.put("msg", "修改失败" + e.getMessage());
		}
		
		this.json = JSONObject.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	public String deleteWorkCustomCfg() throws Exception{
		Map<String,Object> retMap = new HashMap<String, Object>();
		
		retMap.put("code", "0");
		retMap.put("msg", "修改成功");
		
		try{
			workCustomCfgManager.deleteCfg(this.cfg);
		}catch(Exception e){
			retMap.put("code", "1");
			retMap.put("msg", "修改失败" + e.getMessage());
		}
		
		this.json = JSONObject.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	public String addDealer() throws Exception{
		Map<String,Object> retMap = new HashMap<String, Object>();
		
		retMap.put("code", "0");
		retMap.put("msg", "新增处理单位配置成功");
		
		try{
			workCustomCfgManager.addDealer(this.dealer);
		}catch(Exception e){
			retMap.put("code", "1");
			retMap.put("msg", "新增处理单位配置失败：" + e.getMessage());
		}
		
		this.json = JSONObject.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	public String updateDealer() throws Exception{
		Map<String,Object> retMap = new HashMap<String, Object>();
		
		retMap.put("code", "0");
		retMap.put("msg", "新增处理单位配置成功");
		
		try{
			workCustomCfgManager.updateDealer(this.dealer);
		}catch(Exception e){
			retMap.put("code", "1");
			retMap.put("msg", "新增处理单位配置" + e.getMessage());
		}
		
		this.json = JSONObject.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	public String deleteDealer() throws Exception{
		Map<String,Object> retMap = new HashMap<String, Object>();
		
		retMap.put("code", "0");
		retMap.put("msg", "新增处理单位配置成功");
		
		try{
			workCustomCfgManager.deleteDealer(this.dealer);
		}catch(Exception e){
			retMap.put("code", "1");
			retMap.put("msg", "新增处理单位配置" + e.getMessage());
		}
		
		this.json = JSONObject.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getDealById() throws Exception{
		Map retMap = new HashMap();
		
		try{
			if(this.dealer ==  null)
				throw new Exception("传入处理单位配置信息为空");
			
			if(StringUtils.isBlank(this.dealer.getDeal_id()))
				throw new Exception("传入处理单位配置编号为空");
			
			String deal_id = this.dealer.getDeal_id();
			
			ES_WORK_CUSTOM_DEALER pojo = new ES_WORK_CUSTOM_DEALER();
			pojo.setDeal_id(deal_id);
			List<ES_WORK_CUSTOM_DEALER> retDeals = workCustomCfgManager.qryDealList(pojo, null);
			
			if(retDeals==null || retDeals.size()==0)
				throw new Exception("根据处理单位编号"+deal_id+"未查到流程配置");
			
			if(retDeals.size()>1)
				throw new Exception("根据处理单位编号"+deal_id+"查到多条流程配置");
			
			retMap.put("code", "0");
			retMap.put("dealer", retDeals.get(0));
		}catch(Exception e){
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}
		
		this.json = JSONObject.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}

	/******************流程DEMO********************/
	
	public String getDemoUrl() throws Exception{
		return "workCustomDemo";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String startWorkFlow() throws Exception{
		Map retMap = new HashMap();
		
		try{
			if(this.workflow == null)
				throw new Exception("传入参数为空");
			
			if(StringUtils.isBlank(this.workflow.getOrder_id()))
				throw new Exception("传入订单编号为空");
			
			if(StringUtils.isBlank(this.workflow.getCfg_type()))
				throw new Exception("传入流程类型为空");
			
			String order_id = this.workflow.getOrder_id();
			
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			
			if(orderTree == null)
				throw new Exception("根据订单编号"+order_id+"未查到订单树对象");
			
			WORK_CUSTOM_FLOW_DATA flowData = workCustomEngine.startWorkFlow(orderTree,"order");
			
			if(flowData == null)
				throw new Exception("未匹配到自定义流程配置");
			
			retMap.put("code", "0");
		}catch(Exception e){
			log.error(e.getMessage(), e);
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}
		
		this.json = JSONObject.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String loadFlowData() throws Exception{
		Map retMap = new HashMap();
		
		try{
			if(this.ins == null)
				throw new Exception("传入参数为空");
			
			if(StringUtils.isBlank(this.ins.getOrder_id()))
				throw new Exception("传入订单编号为空");
			
			String order_id = this.ins.getOrder_id();
			
			WORK_CUSTOM_FLOW_DATA flowData = workCustomEngine.loadWorkFlowDataByOrderId(order_id);
			
			if(flowData == null)
				throw new Exception("未查到订单"+order_id+"的流程实例");
			
			this.cfg = flowData.getCfg();
			this.inses = new ArrayList<ES_WORK_CUSTOM_NODE_INS>(flowData.getInsMap().values());
			this.connects = new ArrayList<ES_WORK_CUSTOM_CONNECT>();
			
			Map<String, List<ES_WORK_CUSTOM_CONNECT>> connectMap = flowData.getConnectMap();
			Iterator<Entry<String, List<ES_WORK_CUSTOM_CONNECT>>> connectIt = connectMap.entrySet().iterator();
			for(;connectIt.hasNext();){
				this.connects.addAll(connectIt.next().getValue());
			}
			
			//获取当前操作员
			AdminUser user = ManagerUtils.getAdminUser();
			String org_id = user.getOrg_id();
			if("07XX".equals(org_id))
				org_id = "0010";
			
			//获取登录组织
			Organization pojo = new Organization();
			pojo.setParty_id(org_id);
			List<Organization> orgs = this.workCustomCfgManager.qryOrganizationList(pojo, null);
			
			Organization org = new Organization();
			if(orgs!=null && orgs.size()>0)
				org = orgs.get(0);
			
			retMap.put("code", "0");
			retMap.put("cfg", this.cfg);
			retMap.put("inses", this.inses);
			retMap.put("connects", this.connects);
			retMap.put("user", user);
			retMap.put("org", org);
			
			//获取锁定人信息
			retMap.putAll(this.getLockInfoMap(flowData));
		}catch(Exception e){
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}
		
		this.json = JSONObject.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String continueWorkFlow() throws Exception{
		Map retMap = new HashMap();
		
		try{
			if(this.ins == null)
				throw new Exception("传入参数为空");
			
			if(StringUtils.isBlank(this.ins.getOrder_id()))
				throw new Exception("传入订单编号为空");
			
			String order_id = this.ins.getOrder_id();
			
			workCustomEngine.continueWorkFlow(order_id);
			
			retMap.put("code", "0");
			
		}catch(Exception e){
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}
		
		this.json = JSONObject.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String runNodeManual() throws Exception{
		Map retMap = new HashMap();
		
		try{
			if(this.ins == null)
				throw new Exception("传入参数为空");
			
			if(StringUtils.isBlank(this.ins.getInstance_id()))
				throw new Exception("传入环节实例编号为空");
			
			String instance_id = SqlUtil.getStrValue(this.ins.getInstance_id());
			String webCondition = SqlUtil.getStrValue(this.ins.getExt_3());
			String remark = SqlUtil.getStrValue(this.ins.getRemark());
			
			WORK_CUSTOM_FLOW_DATA flowdata = workCustomEngine.runNodeManual(instance_id, true, webCondition,remark,null);
			
			if(ConstsCore.ERROR_FAIL.equals(flowdata.getRun_result()))
				throw new Exception(flowdata.getRun_msg());
			
			retMap.put("code", "0");
		}catch(Exception e){
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}
		
		this.json = JSONObject.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String gotoNode() throws Exception{
		Map retMap = new HashMap();
		
		try{
			if(this.ins == null)
				throw new Exception("传入参数为空");
			
			if(StringUtils.isBlank(this.ins.getInstance_id()))
				throw new Exception("传入环节实例编号为空");
			
			String instance_id = this.ins.getInstance_id();
			String remark = this.ins.getRemark();
			
			workCustomEngine.gotoNode(instance_id,remark);
			
			retMap.put("code", "0");
			
		}catch(Exception e){
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}
		
		this.json = JSONObject.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String rollback() throws Exception{
		Map retMap = new HashMap();
		
		try{
			if(this.ins == null)
				throw new Exception("传入参数为空");
			
			if(StringUtils.isBlank(this.ins.getInstance_id()))
				throw new Exception("传入环节实例编号为空");
			
			String instance_id = this.ins.getInstance_id();
			String remark = this.ins.getRemark();
			
			workCustomEngine.rollback(instance_id,remark);
			
			retMap.put("code", "0");
			
		}catch(Exception e){
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}
		
		this.json = JSONObject.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String cancelWorkFlow() throws Exception{
		Map retMap = new HashMap();
		
		try{
			if(this.ins == null)
				throw new Exception("传入参数为空");
			
			if(StringUtils.isBlank(this.ins.getOrder_id()))
				throw new Exception("传入订单编号为空");
			
			String order_id = this.ins.getOrder_id();
			
			workCustomEngine.cancelWorkFlow(order_id);
			
			retMap.put("code", "0");
			
		}catch(Exception e){
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}
		
		this.json = JSONObject.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	public String getBasicWebConditionUrl() throws Exception{
		acceptDetail = ordIntentManager.getAcceptDetail(order_id);
		//
		countyList = ordWorkManager.getIntentCountyList(acceptDetail.get("order_city_code")+"");
		return "basicWebCondition";
	}
	
	public String getUserTeamTable() throws Exception {
		
		if(this.team == null){
			throw new Exception("传入团队查询参数为空");
		}
		
		if(StringUtils.isNotBlank(this.team.getTeam_name())){
			String teamName = URLDecoder.decode(this.team.getTeam_name(), "UTF-8");
			this.team.setTeam_name(teamName);
		}
        
		ES_USER_TEAM pojo = new ES_USER_TEAM();
		pojo.setTeam_id(this.team.getTeam_id());
		pojo.setTeam_name(this.team.getTeam_name());
		pojo.setTeam_level(this.team.getTeam_level());
		pojo.setState("1");
		
		List<SqlBuilderInterface> sqlBuilds = new ArrayList<SqlBuilderInterface>();
		
		SqlLikeBuilder teamName = new SqlLikeBuilder("team_name",this.team.getTeam_name());
		
		sqlBuilds.add(teamName);
		
		if(StringUtils.isNotBlank(this.team.getRegion_id()) || 
				StringUtils.isNotBlank(this.team.getCounty_id())){
			
			SqlBuilder countyBuilder = new SqlBuilder();
			
			StringBuilder builder = new StringBuilder();
			
			builder.append(" ( ");
			
			builder.append(" ( ");
			
			builder.append(" 1=1 ");
			
			if(StringUtils.isNotBlank(this.team.getRegion_id())){
				builder.append(" and ");
				builder.append(" region_id=").append(this.team.getRegion_id()).append(" ");
			}
			
			builder.append(" ) ");
			
			builder.append(" or team_level='province' ");
			
			builder.append(" ) ");
			
			countyBuilder.setCol_name("");
			countyBuilder.setParm(builder.toString());
			
			sqlBuilds.add(countyBuilder);
		}
		
		this.webpage = this.workCustomCfgManager.qryTeamPageByPojo(pojo , sqlBuilds, this.getPage(), this.getPageSize());
		
        return "work_custom_team_table";
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getWorkCustomPermission() throws Exception{
		Map retMap = new HashMap();
		
		try{
			AdminUser user = ManagerUtils.getAdminUser();
			
			Map<String,Object> temp = new HashMap<String, Object>();
			
			temp.put("userid", user.getUserid());
			temp.put("level", user.getPermission_level());
			temp.put("region", user.getPermission_region());
			temp.put("county", user.getPermission_county());
			temp.put("busi_county", user.getPerm_busi_county());
			
			retMap.put("code", "0");
			retMap.put("permission", temp);
		}catch(Exception e){
			retMap.put("code", "1");
			retMap.put("msg", e.getMessage());
		}
		
		this.json = JSONObject.toJSONString(retMap);
		
		return JSON_MESSAGE;
	}
	
	public String getOrderDetailUrl() throws Exception{
		return "order_detail";
	}
	
	/**
	 * 获取锁定人信息
	 * @param flowData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map getLockInfoMap(WORK_CUSTOM_FLOW_DATA flowData) throws Exception{
		Map lockInfoMap = new HashMap();
		
		if(flowData.getOrderTree()!=null){
			lockInfoMap.put("order_type", "order");
			
			//取正式订单处理人员
			String flow_trace_id = flowData.getOrderTree().getOrderExtBusiRequest().getFlow_trace_id();
			
			OrderLockBusiRequest curr_lock = CommonDataFactory.getInstance().
					getOrderLockBusiRequest(flowData.getOrderTree(),flow_trace_id);
			
			if(curr_lock!=null){
				lockInfoMap.put("lock_id", curr_lock.getLock_user_id());
				lockInfoMap.put("lock_name", curr_lock.getLock_user_name());
				lockInfoMap.put("lock_type", curr_lock.getDealer_type());
			}else{
				lockInfoMap.put("lock_id", "");
				lockInfoMap.put("lock_name", "");
				lockInfoMap.put("lock_type", "");
			}
		}else{
			//取意向单订单处理人员
			lockInfoMap.put("order_type", "order_intent");
			
			String lock_id = "";
			String lock_name = "";
			String lock_type = "";
			
			for(int i=0;i<this.inses.size();i++){
				ES_WORK_CUSTOM_NODE_INS ins = this.inses.get(i);
				
				if("1".equals(ins.getIs_curr_step())){
					if(StringUtils.isNotBlank(ins.getCurr_user_id())){
						lock_id = ins.getCurr_user_id();
						lock_name = ins.getCurr_user_name();
						lock_type = "person";
					}else{
						lock_id = ins.getDealer_code();
						lock_name = ins.getDealer_name();
						lock_type = ins.getDealer_type();
					}
					break;
				}
			}
			
			lockInfoMap.put("lock_id", lock_id);
			lockInfoMap.put("lock_name", lock_name);
			lockInfoMap.put("lock_type", lock_type);
		}
		
		return lockInfoMap;
	}
	
	public String getCopyWorkFlowComfirmUrl() throws Exception{
		return "CopyWorkFlowComfirm";
	}
	
	public String copyWorkFlow() throws Exception{
		Map<String,Object> retMap = new HashMap<String, Object>();
		
		retMap.put("code", "0");
		retMap.put("msg", "复制成功");
		
		try{
			this.workCustomCfgManager.copyCfg(this.cfg);
		}catch(Exception e){
			retMap.put("code", "1");
			retMap.put("msg", "复制失败：" + e.getMessage());
		}
		
		this.json = JSONObject.toJSONString(retMap);

		return JSON_MESSAGE;
	}
}
