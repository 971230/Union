package com.ztesoft.net.service.workCustom.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.CrmConstants;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.SqlBuilderInterface;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomCfgManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomEngine;
import com.ztesoft.net.sqls.SqlBuilder;
import com.ztesoft.net.sqls.SqlUtil;
import com.ztesoft.remote.inf.IRemoteSmsService;

import consts.ConstsCore;
import params.ZteRequest;
import params.ZteResponse;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.AopSmsSendReq;
import zte.net.ecsord.params.ecaop.resp.AopSmsSendResp;
import zte.net.ecsord.params.nd.vo.ES_DC_PUBLIC_DICT_RELATION;
import zte.net.ecsord.params.nd.vo.User;
import zte.net.ecsord.params.workCustom.po.ES_ORDER_INTENT;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_CFG;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_CONNECT;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_DEALER;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_LOG;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_NODE;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_NODE_INS;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_WORKFLOW_INS;
import zte.net.ecsord.params.workCustom.po.ES_WORK_SMS_SEND;
import zte.net.ecsord.params.workCustom.po.WORK_CUSTOM_FLOW_DATA;
import zte.net.workCustomBean.common.JSBusiBean;
import zte.net.workCustomBean.common.RuleBusiBean;
import zte.net.workCustomBean.interfaces.IBusiInterface;

@Transactional
public class WorkCustomEngine implements IWorkCustomEngine {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private IWorkCustomCfgManager workCustomCfgManager;
	
	@Resource
	private IEcsOrdManager ecsOrdManager;
	
	@Override
	public WORK_CUSTOM_FLOW_DATA loadWorkFlowDataByInsId(String instance_id)
			throws Exception {
		if(StringUtils.isBlank(instance_id))
			throw new Exception("传入的环节实例编号为空");
		
		//查询环节实例
		ES_WORK_CUSTOM_NODE_INS pojo = new ES_WORK_CUSTOM_NODE_INS();
		pojo.setInstance_id(instance_id);
		List<ES_WORK_CUSTOM_NODE_INS> insRet = this.workCustomCfgManager.qryInsList(pojo , null);
		
		if(insRet==null || insRet.size()==0)
			throw new Exception("根据实例编号"+instance_id+"未在流程实例当前表中查到流程实例！");
		
		if(insRet.size()>1)
			throw new Exception("根据实例编号"+instance_id+"在流程实例当前表中查到多个流程实例！");
		
		String order_id = insRet.get(0).getOrder_id();
		String workflow_id = insRet.get(0).getWorkflow_id();
		
		if(StringUtils.isBlank(order_id))
			throw new Exception("环节实例中的订单编号为空");
		
		//查询订单树
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		ES_ORDER_INTENT intent = null;
		
		if(orderTree == null){
			ES_ORDER_INTENT intentParam = new ES_ORDER_INTENT();
			intentParam.setOrder_id(order_id);
			
			List<ES_ORDER_INTENT> intentRet = this.workCustomCfgManager.qryOrderIntentList(intentParam , null);
			
			if(intentRet==null || intentRet.size()==0)
				throw new Exception("根据订单编号"+order_id+"查询的订单树对象和意向单对象为空");
			
			intent = intentRet.get(0);
		}
		
		//查询流程实例
		ES_WORK_CUSTOM_WORKFLOW_INS flowParam = new ES_WORK_CUSTOM_WORKFLOW_INS();
		flowParam.setWorkflow_id(workflow_id);
		List<ES_WORK_CUSTOM_WORKFLOW_INS> workflowList = this.workCustomCfgManager.qryWorkflowInsFromAll(flowParam , null);
		
		if(workflowList==null || workflowList.size()==0)
			throw new Exception("根据流程编号"+workflow_id+"未查询到流程实例");
		
		if(workflowList.size() > 1)
			throw new Exception("根据流程编号"+workflow_id+"查到多条流程实例！");
		
		//环节实例
		ES_WORK_CUSTOM_NODE_INS insParam = new ES_WORK_CUSTOM_NODE_INS();
		insParam.setWorkflow_id(workflow_id);
		List<ES_WORK_CUSTOM_NODE_INS> inses = this.workCustomCfgManager.qryInsList(insParam , null);
		
		Map<String,ES_WORK_CUSTOM_NODE_INS> insMap = new HashMap<String, ES_WORK_CUSTOM_NODE_INS>();
		
		for(int i=0;i<inses.size();i++){
			String node_id = inses.get(i).getNode_id();
			
			insMap.put(node_id, inses.get(i));
		}
		
		String cfg_id  = inses.get(0).getCfg_id();
		String version_id = inses.get(0).getVersion_id();
		
		//查询配置
		ES_WORK_CUSTOM_CFG cfgParam = new ES_WORK_CUSTOM_CFG();
		cfgParam.setVersion_id(version_id);
		List<ES_WORK_CUSTOM_CFG> cfgs = this.workCustomCfgManager.qryCfgListFromAll(cfgParam , null);
		
		if(cfgs==null || cfgs.size()==0)
			throw new Exception("根据版本编号"+version_id+"未查到配置信息！");
		
		if(cfgs.size() > 1)
			throw new Exception("根据版本编号"+version_id+"查到多条配置信息！");
		
		//查询流程环节信息
		ES_WORK_CUSTOM_NODE nParam = new ES_WORK_CUSTOM_NODE();
		nParam.setVersion_id(version_id);
		List<ES_WORK_CUSTOM_NODE> nodes = this.workCustomCfgManager.qryNodeListFromAll(nParam, null);
		
		if(nodes==null || nodes.size()==0)
			throw new Exception("根据版本编号"+version_id+"未查到流程环节信息！");
		
		//查询流程连接信息
		ES_WORK_CUSTOM_CONNECT cParam = new ES_WORK_CUSTOM_CONNECT();
		cParam.setVersion_id(version_id);
		List<ES_WORK_CUSTOM_CONNECT> connects = this.workCustomCfgManager.qryConnectListFromAll(cParam, null);
		
		if(connects==null || connects.size()==0)
			throw new Exception("根据版本编号"+version_id+"未查到流程连接信息！");
		
		//查询流程环节处理人信息
		ES_WORK_CUSTOM_DEALER dParam = new ES_WORK_CUSTOM_DEALER();
		dParam.setCfg_id(cfg_id);
		List<ES_WORK_CUSTOM_DEALER> dealers = this.workCustomCfgManager.qryDealList(dParam, null);
		
		WORK_CUSTOM_FLOW_DATA data = new WORK_CUSTOM_FLOW_DATA();
		
		//转换为以node_id为KEY值的nodeMap
		Map<String,ES_WORK_CUSTOM_NODE> nodeMap = new HashMap<String, ES_WORK_CUSTOM_NODE>();
		for(int i=0;i<nodes.size();i++){
			String node_id = nodes.get(i).getNode_id();
			
			nodeMap.put(node_id, nodes.get(i));
		}
		
		//转换为以Source_node_id为KEY值的connectMap
		Map<String,List<ES_WORK_CUSTOM_CONNECT>> connectMap = new HashMap<String, List<ES_WORK_CUSTOM_CONNECT>>();
		for(int i=0;i<connects.size();i++){
			String srcNodeId = connects.get(i).getSource_node_id();
			
			if(connectMap.containsKey(srcNodeId)){
				List<ES_WORK_CUSTOM_CONNECT> list = connectMap.get(srcNodeId);
				
				list.add( connects.get(i));
				
				connectMap.put(srcNodeId, list);
			}else{
				List<ES_WORK_CUSTOM_CONNECT> list = new ArrayList<ES_WORK_CUSTOM_CONNECT>();
				
				list.add( connects.get(i));
				
				connectMap.put(srcNodeId, list);
			}
		}
		
		//转换为以Node_id为KEY值的dealMap
		Map<String,List<ES_WORK_CUSTOM_DEALER>> dealMap = new HashMap<String, List<ES_WORK_CUSTOM_DEALER>>();
		for(int i=0;i<dealers.size();i++){
			String node_id = dealers.get(i).getNode_id();
			
			if(dealMap.containsKey(node_id)){
				List<ES_WORK_CUSTOM_DEALER> list = dealMap.get(node_id);
				list.add(dealers.get(i));
				
				dealMap.put(node_id, list);
			}else{
				List<ES_WORK_CUSTOM_DEALER> list = new ArrayList<ES_WORK_CUSTOM_DEALER>();
				list.add(dealers.get(i));
				
				dealMap.put(node_id, list);
			}
		}
		
		data.setOrderTree(orderTree);
		data.setOrderIntent(intent);
		data.setCfg(cfgs.get(0));
		data.setNodeMap(nodeMap);
		data.setConnectMap(connectMap);
		data.setDealMap(dealMap);
		data.setWorkflow(workflowList.get(0));
		data.setInsMap(insMap);
		
		return data;
	}
	
	@Override
	public WORK_CUSTOM_FLOW_DATA loadWorkFlowDataByOrderId(String order_id)
			throws Exception {
		if(StringUtils.isBlank(order_id))
			throw new Exception("传入的订单编号为空");
		
		//订单数据对象
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		ES_ORDER_INTENT intent = null;
		
		if(orderTree == null){
			ES_ORDER_INTENT intentParam = new ES_ORDER_INTENT();
			intentParam.setOrder_id(order_id);
			
			List<ES_ORDER_INTENT> intentRet = this.workCustomCfgManager.qryOrderIntentList(intentParam , null);
			
			if(intentRet==null || intentRet.size()==0)
				throw new Exception("根据订单编号"+order_id+"查询的订单树对象和意向单对象为空");
			
			intent = intentRet.get(0);
		}
		
		//查询流程实例
		ES_WORK_CUSTOM_WORKFLOW_INS flowParam = new ES_WORK_CUSTOM_WORKFLOW_INS();
		flowParam.setOrder_id(order_id);
		List<ES_WORK_CUSTOM_WORKFLOW_INS> workflowList = this.workCustomCfgManager.qryWorkflowInsFromAll(flowParam , null);
		
		if(workflowList==null || workflowList.size()==0)
			throw new Exception("根据订单编号"+order_id+"未查询到流程实例");
		
		if(workflowList.size() > 1)
			throw new Exception("根据订单编号"+order_id+"查到多条正在执行的流程实例！");
		
		String workflow_id = workflowList.get(0).getWorkflow_id();
		
		//查询流程环节实例		
		ES_WORK_CUSTOM_NODE_INS pojo = new ES_WORK_CUSTOM_NODE_INS();
		pojo.setWorkflow_id(workflow_id);
		List<ES_WORK_CUSTOM_NODE_INS> inses = this.workCustomCfgManager.qryInsListFromAll(pojo , null);
		
		if(inses==null || inses.size()==0)
			throw new Exception("根据订单编号"+order_id+"未在流程实例当前表中查到流程实例！");
		
		Map<String,ES_WORK_CUSTOM_NODE_INS> insMap = new HashMap<String, ES_WORK_CUSTOM_NODE_INS>();
		
		for(int i=0;i<inses.size();i++){
			String node_id = inses.get(i).getNode_id();
			
			insMap.put(node_id, inses.get(i));
		}
		
		String cfg_id  = inses.get(0).getCfg_id();
		String version_id = inses.get(0).getVersion_id();
		
		//查询配置
		ES_WORK_CUSTOM_CFG cfgParam = new ES_WORK_CUSTOM_CFG();
		cfgParam.setVersion_id(version_id);
		List<ES_WORK_CUSTOM_CFG> cfgs = this.workCustomCfgManager.qryCfgListFromAll(cfgParam , null);
		
		if(cfgs==null || cfgs.size()==0)
			throw new Exception("根据版本编号"+version_id+"未查到配置信息！");
		
		if(cfgs.size() > 1)
			throw new Exception("根据版本编号"+version_id+"未查到多条配置信息！");
		
		//查询流程环节信息
		ES_WORK_CUSTOM_NODE nParam = new ES_WORK_CUSTOM_NODE();
		nParam.setVersion_id(version_id);
		List<ES_WORK_CUSTOM_NODE> nodes = this.workCustomCfgManager.qryNodeListFromAll(nParam, null);
		
		if(nodes==null || nodes.size()==0)
			throw new Exception("根据版本编号"+version_id+"未查到流程环节信息！");
		
		//查询流程连接信息
		ES_WORK_CUSTOM_CONNECT cParam = new ES_WORK_CUSTOM_CONNECT();
		cParam.setVersion_id(version_id);
		List<ES_WORK_CUSTOM_CONNECT> connects = this.workCustomCfgManager.qryConnectListFromAll(cParam, null);
		
		if(connects==null || connects.size()==0)
			throw new Exception("根据版本编号"+version_id+"未查到流程连接信息！");
		
		//查询流程环节处理人信息
		ES_WORK_CUSTOM_DEALER dParam = new ES_WORK_CUSTOM_DEALER();
		dParam.setCfg_id(cfg_id);
		List<ES_WORK_CUSTOM_DEALER> dealers = this.workCustomCfgManager.qryDealList(dParam, null);
		
		WORK_CUSTOM_FLOW_DATA data = new WORK_CUSTOM_FLOW_DATA();
		
		//转换为以node_id为KEY值的nodeMap
		Map<String,ES_WORK_CUSTOM_NODE> nodeMap = new HashMap<String, ES_WORK_CUSTOM_NODE>();
		for(int i=0;i<nodes.size();i++){
			String node_id = nodes.get(i).getNode_id();
			
			nodeMap.put(node_id, nodes.get(i));
		}
		
		//转换为以Source_node_id为KEY值的connectMap
		Map<String,List<ES_WORK_CUSTOM_CONNECT>> connectMap = new HashMap<String, List<ES_WORK_CUSTOM_CONNECT>>();
		for(int i=0;i<connects.size();i++){
			String srcNodeId = connects.get(i).getSource_node_id();
			
			if(connectMap.containsKey(srcNodeId)){
				List<ES_WORK_CUSTOM_CONNECT> list = connectMap.get(srcNodeId);
				
				list.add( connects.get(i));
				
				connectMap.put(srcNodeId, list);
			}else{
				List<ES_WORK_CUSTOM_CONNECT> list = new ArrayList<ES_WORK_CUSTOM_CONNECT>();
				
				list.add( connects.get(i));
				
				connectMap.put(srcNodeId, list);
			}
		}
		
		//转换为以Node_id为KEY值的dealMap
		Map<String,List<ES_WORK_CUSTOM_DEALER>> dealMap = new HashMap<String, List<ES_WORK_CUSTOM_DEALER>>();
		for(int i=0;i<dealers.size();i++){
			String node_id = dealers.get(i).getNode_id();
			
			if(dealMap.containsKey(node_id)){
				List<ES_WORK_CUSTOM_DEALER> list = dealMap.get(node_id);
				list.add(dealers.get(i));
				
				dealMap.put(node_id, list);
			}else{
				List<ES_WORK_CUSTOM_DEALER> list = new ArrayList<ES_WORK_CUSTOM_DEALER>();
				list.add(dealers.get(i));
				
				dealMap.put(node_id, list);
			}
		}
		
		data.setOrderTree(orderTree);
		data.setOrderIntent(intent);
		data.setCfg(cfgs.get(0));
		data.setNodeMap(nodeMap);
		data.setConnectMap(connectMap);
		data.setDealMap(dealMap);
		data.setWorkflow(workflowList.get(0));
		data.setInsMap(insMap);
		
		return data;
	}
	
	@Override
	public WORK_CUSTOM_FLOW_DATA loadWorkFlowDataByFlowId(String workflow_id)
			throws Exception {
		//查询流程实例
		ES_WORK_CUSTOM_WORKFLOW_INS flowParam = new ES_WORK_CUSTOM_WORKFLOW_INS();
		flowParam.setWorkflow_id(workflow_id);
		List<ES_WORK_CUSTOM_WORKFLOW_INS> workflowList = this.workCustomCfgManager.qryWorkflowInsFromAll(flowParam , null);
		
		if(workflowList==null || workflowList.size()==0)
			throw new Exception("根据流程编号"+workflow_id+"未查询到流程实例");
		
		if(workflowList.size() > 1)
			throw new Exception("根据流程编号"+workflow_id+"查到多条流程实例！");
		
		String order_id = workflowList.get(0).getOrder_id();
		
		//订单数据对象
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		ES_ORDER_INTENT intent = null;
		
		if(orderTree == null){
			ES_ORDER_INTENT intentParam = new ES_ORDER_INTENT();
			intentParam.setOrder_id(order_id);
			
			List<ES_ORDER_INTENT> intentRet = this.workCustomCfgManager.qryOrderIntentList(intentParam , null);
			
			if(intentRet==null || intentRet.size()==0)
				throw new Exception("根据订单编号"+order_id+"查询的订单树对象和意向单对象为空");
			
			intent = intentRet.get(0);
		}
		
		//查询流程环节实例		
		ES_WORK_CUSTOM_NODE_INS pojo = new ES_WORK_CUSTOM_NODE_INS();
		pojo.setWorkflow_id(workflow_id);
		List<ES_WORK_CUSTOM_NODE_INS> inses = this.workCustomCfgManager.qryInsListFromAll(pojo , null);
		
		if(inses==null || inses.size()==0)
			throw new Exception("根据流程编号"+workflow_id+"未在环节实例当前表中查到流程实例！");
		
		Map<String,ES_WORK_CUSTOM_NODE_INS> insMap = new HashMap<String, ES_WORK_CUSTOM_NODE_INS>();
		
		for(int i=0;i<inses.size();i++){
			String node_id = inses.get(i).getNode_id();
			
			insMap.put(node_id, inses.get(i));
		}
		
		String cfg_id  = inses.get(0).getCfg_id();
		String version_id = inses.get(0).getVersion_id();
		
		//查询配置
		ES_WORK_CUSTOM_CFG cfgParam = new ES_WORK_CUSTOM_CFG();
		cfgParam.setVersion_id(version_id);
		List<ES_WORK_CUSTOM_CFG> cfgs = this.workCustomCfgManager.qryCfgListFromAll(cfgParam , null);
		
		if(cfgs==null || cfgs.size()==0)
			throw new Exception("根据版本编号"+version_id+"未查到配置信息！");
		
		if(cfgs.size() > 1)
			throw new Exception("根据版本编号"+version_id+"未查到多条配置信息！");
		
		//查询流程环节信息
		ES_WORK_CUSTOM_NODE nParam = new ES_WORK_CUSTOM_NODE();
		nParam.setVersion_id(version_id);
		List<ES_WORK_CUSTOM_NODE> nodes = this.workCustomCfgManager.qryNodeListFromAll(nParam, null);
		
		if(nodes==null || nodes.size()==0)
			throw new Exception("根据版本编号"+version_id+"未查到流程环节信息！");
		
		//查询流程连接信息
		ES_WORK_CUSTOM_CONNECT cParam = new ES_WORK_CUSTOM_CONNECT();
		cParam.setVersion_id(version_id);
		List<ES_WORK_CUSTOM_CONNECT> connects = this.workCustomCfgManager.qryConnectListFromAll(cParam, null);
		
		if(connects==null || connects.size()==0)
			throw new Exception("根据版本编号"+version_id+"未查到流程连接信息！");
		
		//查询流程环节处理人信息
		ES_WORK_CUSTOM_DEALER dParam = new ES_WORK_CUSTOM_DEALER();
		dParam.setCfg_id(cfg_id);
		List<ES_WORK_CUSTOM_DEALER> dealers = this.workCustomCfgManager.qryDealList(dParam, null);
		
		WORK_CUSTOM_FLOW_DATA data = new WORK_CUSTOM_FLOW_DATA();
		
		//转换为以node_id为KEY值的nodeMap
		Map<String,ES_WORK_CUSTOM_NODE> nodeMap = new HashMap<String, ES_WORK_CUSTOM_NODE>();
		for(int i=0;i<nodes.size();i++){
			String node_id = nodes.get(i).getNode_id();
			
			nodeMap.put(node_id, nodes.get(i));
		}
		
		//转换为以Source_node_id为KEY值的connectMap
		Map<String,List<ES_WORK_CUSTOM_CONNECT>> connectMap = new HashMap<String, List<ES_WORK_CUSTOM_CONNECT>>();
		for(int i=0;i<connects.size();i++){
			String srcNodeId = connects.get(i).getSource_node_id();
			
			if(connectMap.containsKey(srcNodeId)){
				List<ES_WORK_CUSTOM_CONNECT> list = connectMap.get(srcNodeId);
				
				list.add( connects.get(i));
				
				connectMap.put(srcNodeId, list);
			}else{
				List<ES_WORK_CUSTOM_CONNECT> list = new ArrayList<ES_WORK_CUSTOM_CONNECT>();
				
				list.add( connects.get(i));
				
				connectMap.put(srcNodeId, list);
			}
		}
		
		//转换为以Node_id为KEY值的dealMap
		Map<String,List<ES_WORK_CUSTOM_DEALER>> dealMap = new HashMap<String, List<ES_WORK_CUSTOM_DEALER>>();
		for(int i=0;i<dealers.size();i++){
			String node_id = dealers.get(i).getNode_id();
			
			if(dealMap.containsKey(node_id)){
				List<ES_WORK_CUSTOM_DEALER> list = dealMap.get(node_id);
				list.add(dealers.get(i));
				
				dealMap.put(node_id, list);
			}else{
				List<ES_WORK_CUSTOM_DEALER> list = new ArrayList<ES_WORK_CUSTOM_DEALER>();
				list.add(dealers.get(i));
				
				dealMap.put(node_id, list);
			}
		}
		
		data.setOrderTree(orderTree);
		data.setOrderIntent(intent);
		data.setCfg(cfgs.get(0));
		data.setNodeMap(nodeMap);
		data.setConnectMap(connectMap);
		data.setDealMap(dealMap);
		data.setWorkflow(workflowList.get(0));
		data.setInsMap(insMap);
		
		return data;
	}

	@Override
	public WORK_CUSTOM_FLOW_DATA startWorkFlow(OrderTreeBusiRequest orderTree,String cfg_type)
			throws Exception {
		//校验
		this.doWorkFlowCheck(orderTree,cfg_type);
				
		//构建流程数据
		WORK_CUSTOM_FLOW_DATA data = this.builderWorkData(orderTree,cfg_type);
		
		if(data == null)
			return null;
		
		//实例化
		this.newWorkCustomInstance(data);
		
		//修改自定义流程标志
		this.workCustomCfgManager.setWorkCustomFlag(data);
		
		//执行流程
		this.runWorkFlow(data,false);

		return data;
	}
	
	@Override
	public WORK_CUSTOM_FLOW_DATA startWorkFlow(OrderTreeBusiRequest orderTree,ES_WORK_CUSTOM_CFG cfg) throws Exception{
		//校验
		this.doWorkFlowCheck(orderTree,cfg);
				
		//构建流程数据
		WORK_CUSTOM_FLOW_DATA data = this.builderWorkData(orderTree,cfg);
		
		if(data == null)
			return null;
		
		//实例化
		this.newWorkCustomInstance(data);
		
		//修改自定义流程标志
		this.workCustomCfgManager.setWorkCustomFlag(data);
		
		//执行流程
		this.runWorkFlow(data,false);

		return data;
	}
	
	@Override
	public WORK_CUSTOM_FLOW_DATA startWorkFlow(ES_ORDER_INTENT orderIntent,ES_WORK_CUSTOM_CFG cfg) throws Exception{
		//构建流程数据
		WORK_CUSTOM_FLOW_DATA data = this.builderWorkData(orderIntent,cfg);
		
		if(data==null)
			return null;
		
		//校验
		this.doWorkFlowCheck(orderIntent, cfg);
		
		//实例化
		this.newWorkCustomInstance(data);
		
		//修改自定义流程标志
		this.workCustomCfgManager.setWorkCustomFlag(data);
		
		//执行流程
		this.runWorkFlow(data,false);

		return data;
	
	}
	
	/**
	 * 流程启动前的参数校验和订单是否已存在流程的校验
	 * @param orderTree
	 * @param cfg_type
	 * @throws Exception
	 */
	private void doWorkFlowCheck(OrderTreeBusiRequest orderTree,String cfg_type) throws Exception{
		if(orderTree==null)
			throw new Exception("传入的订单树对象为空");
		
		if(StringUtils.isBlank(cfg_type))
			throw new Exception("传入的流程类型为空");
		
		String order_id = orderTree.getOrder_id();
		
		if(StringUtils.isBlank(order_id))
			throw new Exception("传入订单编号为空");
		
		ES_WORK_CUSTOM_NODE_INS pojo = new ES_WORK_CUSTOM_NODE_INS();
		pojo.setOrder_id(order_id);
		int count = this.workCustomCfgManager.qryInsCountByPojo(pojo, null);
		
		if(count>0)
			throw new Exception("订单"+order_id+"已存在正在执行的流程实例");
	}
	
	/**
	 * 流程启动前的参数校验和订单是否已存在流程的校验
	 * @param orderTree
	 * @param cfg_type
	 * @throws Exception
	 */
	private void doWorkFlowCheck(OrderTreeBusiRequest orderTree,ES_WORK_CUSTOM_CFG cfg) throws Exception{
		if(orderTree==null)
			throw new Exception("传入的订单树对象为空");
		
		if(cfg == null)
			throw new Exception("传入的流程配置为空");
		
		String order_id = orderTree.getOrder_id();
		
		if(StringUtils.isBlank(order_id))
			throw new Exception("传入订单编号为空");
		
		ES_WORK_CUSTOM_NODE_INS pojo = new ES_WORK_CUSTOM_NODE_INS();
		pojo.setOrder_id(order_id);
		int count = this.workCustomCfgManager.qryInsCountByPojo(pojo, null);
		
		if(count>0)
			throw new Exception("订单"+order_id+"已存在正在执行的流程实例");
	}
	
	/**
	 * 流程启动前的参数校验和订单是否已存在流程的校验
	 * @param orderIntent
	 * @param cfg
	 * @throws Exception
	 */
	private void doWorkFlowCheck(ES_ORDER_INTENT orderIntent,ES_WORK_CUSTOM_CFG cfg) throws Exception{
		if(orderIntent == null)
			throw new Exception("传入的意向单对象为空");
		
		if(cfg == null)
			throw new Exception("传入的流程配置为空");
		
		String order_id = orderIntent.getOrder_id();
		
		if(StringUtils.isBlank(order_id))
			throw new Exception("传入订单编号为空");
		
		ES_WORK_CUSTOM_NODE_INS pojo = new ES_WORK_CUSTOM_NODE_INS();
		pojo.setOrder_id(order_id);
		int count = this.workCustomCfgManager.qryInsCountByPojo(pojo, null);
		
		if(count>0)
			throw new Exception("订单"+order_id+"已存在正在执行的流程实例");
	}
	
	@Override
	public ES_WORK_CUSTOM_CFG doWorkCustomCfgMatch(String flow_code,
			OrderTreeBusiRequest orderTree,String cfg_type) throws Exception{
		if(orderTree==null)
			return null;
		
		String goods_id = "";
		String orderFrom = "";
		String goods_cat_id = "";
		String goods_type = "";
		
		if("null".equals(flow_code))
			flow_code = "";
		
		//获取订单信息
		if(orderTree.getOrderBusiRequest() != null)
			goods_id = SqlUtil.getStrValue(orderTree.getOrderBusiRequest().getGoods_id());
		
		if(orderTree.getOrderExtBusiRequest() != null)
			orderFrom = SqlUtil.getStrValue(orderTree.getOrderExtBusiRequest().getOrder_from());
		
		if(orderTree.getOrderItemBusiRequests()!=null && orderTree.getOrderItemBusiRequests().size()>0 &&
				orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest()!=null){
			goods_cat_id = SqlUtil.getStrValue(orderTree.getOrderItemBusiRequests().get(0).
					getOrderItemExtBusiRequest().getGoods_cat_id());
			goods_type = SqlUtil.getStrValue(orderTree.getOrderItemBusiRequests().get(0).
					getOrderItemExtBusiRequest().getGoods_type());
		}
		
		List<ES_WORK_CUSTOM_CFG> cfgs = null;
		
		if (StringUtils.isNotBlank(flow_code)){
			//传入流程编码，根据流程编码自定义流程配置
			ES_WORK_CUSTOM_CFG param = new ES_WORK_CUSTOM_CFG();
			param.setState("1");
			param.setCfg_type(cfg_type);
			param.setFlow_code(flow_code);
			param.setMatch_type("2");
			
			cfgs = this.workCustomCfgManager.qryCfgList(param , null);
		}else{
			//没有传入流程编码，根据商品大类、商品小类、商品编号用OR条件查询自定义流程配置
			List<SqlBuilderInterface> sqlBuilds = new ArrayList<SqlBuilderInterface>();
			
			StringBuilder builder = new StringBuilder();
			builder.append(" ( ");
			builder.append(" goods_type_id='"+goods_type+"' ");
			builder.append(" or goods_cat_id='"+goods_cat_id+"' ");
			builder.append(" or goods_id='"+goods_id+"' ");
			builder.append(" ) ");
			
			SqlBuilder condition = new SqlBuilder("",builder.toString(),true);
			
			sqlBuilds.add(condition);
			
			ES_WORK_CUSTOM_CFG param = new ES_WORK_CUSTOM_CFG();
			param.setState("1");
			param.setCfg_type(cfg_type);
			param.setMatch_type("1");
			
			cfgs = this.workCustomCfgManager.qryCfgList(param , sqlBuilds);
		}

		if(cfgs==null || cfgs.size()==0){
			if(StringUtils.isNotBlank(flow_code)){
				throw new Exception("编码为："+flow_code+"的流程不存在");
			}else{
				return null;
			}
		}
		
		//将配置列表转化为以配置级别为KEY值的MAP
		Map<String,List<ES_WORK_CUSTOM_CFG>> cfgLevelMap = new HashMap<String, List<ES_WORK_CUSTOM_CFG>>();
		
		for(int i=0;i<cfgs.size();i++){
			ES_WORK_CUSTOM_CFG c = cfgs.get(i);
			
			String cfgLevel = c.getCfg_level();
			
			boolean add2Map = false;
			
			if ("id".equals(cfgLevel) && goods_id.equals(c.getGoods_id())) {
				add2Map = true;
			}else if ("cat".equals(cfgLevel) && goods_cat_id.equals(c.getGoods_cat_id())) {
				add2Map = true;
			}else if ("type".equals(cfgLevel) && goods_type.equals(c.getGoods_type_id())) {
				add2Map = true;
			}
			
			if (!add2Map)
				continue;
			
			if(cfgLevelMap.containsKey(cfgLevel)){
				List<ES_WORK_CUSTOM_CFG> list = cfgLevelMap.get(cfgLevel);
				
				list.add(c);
				
				cfgLevelMap.put(cfgLevel, list);
			}else{
				List<ES_WORK_CUSTOM_CFG> list = new ArrayList<ES_WORK_CUSTOM_CFG>();
				
				list.add(c);
				
				cfgLevelMap.put(cfgLevel, list);
			}
		}
		
		ES_WORK_CUSTOM_CFG cfg = null;
		
		//先匹配配置级别为商品的配置
		if(cfgLevelMap.containsKey("id")){
			List<ES_WORK_CUSTOM_CFG> list = cfgLevelMap.get("id");
			
			for(int i=0;i<list.size();i++){
				if(StringUtils.isBlank(list.get(i).getOrder_from())){
					cfg = list.get(i);
				}else if(list.get(i).getOrder_from().equals(orderFrom)){
					cfg = list.get(i);
					break;
				}
			}
		}
		
		//再匹配配置级别为商品小类的配置
		if(cfg==null && cfgLevelMap.containsKey("cat")){
			List<ES_WORK_CUSTOM_CFG> list = cfgLevelMap.get("cat");
			
			for(int i=0;i<list.size();i++){
				if(StringUtils.isBlank(list.get(i).getOrder_from())){
					cfg = list.get(i);
				}else if(list.get(i).getOrder_from().equals(orderFrom)){
					cfg = list.get(i);
					break;
				}
			}
		}
		
		//最后匹配配置级别为商品大类的配置
		if(cfg==null && cfgLevelMap.containsKey("type")){
			List<ES_WORK_CUSTOM_CFG> list = cfgLevelMap.get("type");
			
			for(int i=0;i<list.size();i++){
				if(StringUtils.isBlank(list.get(i).getOrder_from())){
					cfg = list.get(i);
				}else if(list.get(i).getOrder_from().equals(orderFrom)){
					cfg = list.get(i);
					break;
				}
			}
		}
		
		//处理方式校验
		if(cfg != null 
				&& StringUtils.isNotBlank(cfg.getOrder_deal_method())
				&& StringUtils.isNotBlank(orderTree.getOrderExtBusiRequest().getOrder_deal_method())
				&& !cfg.getOrder_deal_method().equals(orderTree.getOrderExtBusiRequest().getOrder_deal_method())){
			String dealMethodNotMatchMsg = "";
			if("1".equals(cfg.getOrder_deal_method())){
				dealMethodNotMatchMsg = "当前业务只能以线上方式受理！";
			}else{
				dealMethodNotMatchMsg = "当前业务只能以线下方式受理！";
			}
			
			throw new Exception(dealMethodNotMatchMsg );
		}
		
		if(StringUtils.isNotBlank(flow_code) && cfg==null)
			throw new Exception("编码为："+flow_code+"的流程不匹配当前订单来源和商品");
		
		return cfg;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public ES_WORK_CUSTOM_CFG doWorkCustomCfgMatch(String flow_code,
			ES_ORDER_INTENT orderIntent,String cfg_type) throws Exception{
		if(orderIntent==null)
			return null;
		
		String goods_id = "";
		String orderFrom = "";
		String goods_cat_id = "";
		String goods_type = "";
		
		if("null".equals(flow_code))
			flow_code = "";
		
		//获取订单信息
		goods_id = SqlUtil.getStrValue(orderIntent.getGoods_id());
		orderFrom = SqlUtil.getStrValue(orderIntent.getSource_system_type());
		
		if(StringUtils.isBlank(goods_id) && StringUtils.isBlank(orderFrom))
			return null;
		
		//根据商品编号查询商品大类、商品小类
		List<Map> retGoods = this.workCustomCfgManager.qryGoodsInfoByGoodsid(goods_id);
		if(retGoods==null || retGoods.size()==0)
			throw new Exception("根据商品编号" + goods_id + "查不到商品");
		
		if(retGoods.size() > 1)
			throw new Exception("根据商品编号" + goods_id + "查到多条商品");
		
		goods_type = String.valueOf(retGoods.get(0).get("type_id"));
		goods_cat_id = String.valueOf(retGoods.get(0).get("cat_id"));
		
		List<ES_WORK_CUSTOM_CFG> cfgs = null;
		
		if (StringUtils.isNotBlank(flow_code)){
			// 传入流程编码，根据流程编码自定义流程配置
			ES_WORK_CUSTOM_CFG param = new ES_WORK_CUSTOM_CFG();
			param.setState("1");
			param.setCfg_type(cfg_type);
			param.setFlow_code(flow_code);
			param.setMatch_type("2");
			
			cfgs = this.workCustomCfgManager.qryCfgList(param , null);
		}else{
			// 没有传入流程编码，根据商品大类、商品小类、商品编号用OR条件查询自定义流程配置
			List<SqlBuilderInterface> sqlBuilds = new ArrayList<SqlBuilderInterface>();
			
			StringBuilder builder = new StringBuilder();
			builder.append(" ( ");
			builder.append(" goods_type_id='"+goods_type+"' ");
			builder.append(" or goods_cat_id='"+goods_cat_id+"' ");
			builder.append(" or goods_id='"+goods_id+"' ");
			builder.append(" ) ");
			
			SqlBuilder condition = new SqlBuilder("",builder.toString(),true);
			
			sqlBuilds.add(condition);
			
			ES_WORK_CUSTOM_CFG param = new ES_WORK_CUSTOM_CFG();
			param.setState("1");
			param.setCfg_type(cfg_type);
			param.setMatch_type("1");
			
			cfgs = this.workCustomCfgManager.qryCfgList(param , sqlBuilds);
		}
		
		if(cfgs==null || cfgs.size()==0){
			if(StringUtils.isNotBlank(flow_code)){
				throw new Exception("编码为："+flow_code+"的流程不存在");
			}else{
				return null;
			}
		}
		
		//将配置列表转化为以配置级别为KEY值的MAP
		Map<String,List<ES_WORK_CUSTOM_CFG>> cfgLevelMap = new HashMap<String, List<ES_WORK_CUSTOM_CFG>>();
		
		for(int i=0;i<cfgs.size();i++){
			ES_WORK_CUSTOM_CFG c = cfgs.get(i);
			
			String cfgLevel = c.getCfg_level();
			
			boolean add2Map = false;
			
			if ("id".equals(cfgLevel) && goods_id.equals(c.getGoods_id())) {
				add2Map = true;
			}else if ("cat".equals(cfgLevel) && goods_cat_id.equals(c.getGoods_cat_id())) {
				add2Map = true;
			}else if ("type".equals(cfgLevel) && goods_type.equals(c.getGoods_type_id())) {
				add2Map = true;
			}
			
			if (!add2Map)
				continue;
			
			if(cfgLevelMap.containsKey(cfgLevel)){
				List<ES_WORK_CUSTOM_CFG> list = cfgLevelMap.get(cfgLevel);
				
				list.add(c);
				
				cfgLevelMap.put(cfgLevel, list);
			}else{
				List<ES_WORK_CUSTOM_CFG> list = new ArrayList<ES_WORK_CUSTOM_CFG>();
				
				list.add(c);
				
				cfgLevelMap.put(cfgLevel, list);
			}
		}
		
		ES_WORK_CUSTOM_CFG cfg = null;
		
		//先匹配配置级别为商品的配置
		if(cfgLevelMap.containsKey("id")){
			List<ES_WORK_CUSTOM_CFG> list = cfgLevelMap.get("id");
			
			for(int i=0;i<list.size();i++){
				if(StringUtils.isBlank(list.get(i).getOrder_from())){
					cfg = list.get(i);
				}else if(list.get(i).getOrder_from().equals(orderFrom)){
					cfg = list.get(i);
					break;
				}
			}
		}
		
		//再匹配配置级别为商品小类的配置
		if(cfg==null && cfgLevelMap.containsKey("cat")){
			List<ES_WORK_CUSTOM_CFG> list = cfgLevelMap.get("cat");
			
			for(int i=0;i<list.size();i++){
				if(StringUtils.isBlank(list.get(i).getOrder_from())){
					cfg = list.get(i);
				}else if(list.get(i).getOrder_from().equals(orderFrom)){
					cfg = list.get(i);
					break;
				}
			}
		}
		
		//最后匹配配置级别为商品大类的配置
		if(cfg==null && cfgLevelMap.containsKey("type")){
			List<ES_WORK_CUSTOM_CFG> list = cfgLevelMap.get("type");
			
			for(int i=0;i<list.size();i++){
				if(StringUtils.isBlank(list.get(i).getOrder_from())){
					cfg = list.get(i);
				}else if(list.get(i).getOrder_from().equals(orderFrom)){
					cfg = list.get(i);
					break;
				}
			}
		}
		
		if(StringUtils.isNotBlank(flow_code) && cfg==null)
			throw new Exception("编码为："+flow_code+"的流程不匹配当前订单来源和商品");
		
		return cfg;
	}
	
	/**
	 * 构建流程数据
	 * @param orderTree 订单对象
	 * @param cfg_type 流程类型
	 * @return
	 * @throws Exception
	 */
	private WORK_CUSTOM_FLOW_DATA builderWorkData(OrderTreeBusiRequest orderTree,String cfg_type) throws Exception{
		
		//匹配自定义流程配置
		ES_WORK_CUSTOM_CFG cfg = this.doWorkCustomCfgMatch("",orderTree,cfg_type);
		
		if("cancel".equals(cfg_type) && cfg == null)
			throw new Exception("未匹配到退单流程配置");
		
		if(cfg == null)
			return null;
		
		String cfg_id = cfg.getCfg_id();
		String version_id = cfg.getVersion_id();
		
		ES_WORK_CUSTOM_NODE nParam = new ES_WORK_CUSTOM_NODE();
		nParam.setVersion_id(version_id);
		//查询流程环节配置
		List<ES_WORK_CUSTOM_NODE> nodes = this.workCustomCfgManager.qryNodeListFromAll(nParam, null);
		
		if(nodes==null || nodes.size()==0)
			return null;
		
		//查询流程连接配置
		ES_WORK_CUSTOM_CONNECT cParam = new ES_WORK_CUSTOM_CONNECT();
		cParam.setVersion_id(version_id);
		List<ES_WORK_CUSTOM_CONNECT> connects = this.workCustomCfgManager.qryConnectListFromAll(cParam, null);
		
		if(connects==null || connects.size()==0)
			return null;
		
		//查询流程环节处理人配置
		ES_WORK_CUSTOM_DEALER dParam = new ES_WORK_CUSTOM_DEALER();
		dParam.setCfg_id(cfg_id);
		List<ES_WORK_CUSTOM_DEALER> dealers = this.workCustomCfgManager.qryDealList(dParam, null);
		
		WORK_CUSTOM_FLOW_DATA data = new WORK_CUSTOM_FLOW_DATA();
		
		//转换为以node_id为KEY值的nodeMap
		Map<String,ES_WORK_CUSTOM_NODE> nodeMap = new HashMap<String, ES_WORK_CUSTOM_NODE>();
		for(int i=0;i<nodes.size();i++){
			String node_id = nodes.get(i).getNode_id();
			
			nodeMap.put(node_id, nodes.get(i));
		}
		
		//转换为以Source_node_id为KEY值的connectMap
		Map<String,List<ES_WORK_CUSTOM_CONNECT>> connectMap = new HashMap<String, List<ES_WORK_CUSTOM_CONNECT>>();
		for(int i=0;i<connects.size();i++){
			String srcNodeId = connects.get(i).getSource_node_id();
			
			if(connectMap.containsKey(srcNodeId)){
				List<ES_WORK_CUSTOM_CONNECT> list = connectMap.get(srcNodeId);
				
				list.add( connects.get(i));
				
				connectMap.put(srcNodeId, list);
			}else{
				List<ES_WORK_CUSTOM_CONNECT> list = new ArrayList<ES_WORK_CUSTOM_CONNECT>();
				
				list.add( connects.get(i));
				
				connectMap.put(srcNodeId, list);
			}
		}
		
		//转换为以Node_id为KEY值的dealMap
		Map<String,List<ES_WORK_CUSTOM_DEALER>> dealMap = new HashMap<String, List<ES_WORK_CUSTOM_DEALER>>();
		for(int i=0;i<dealers.size();i++){
			String node_id = dealers.get(i).getNode_id();
			
			if(dealMap.containsKey(node_id)){
				List<ES_WORK_CUSTOM_DEALER> list = dealMap.get(node_id);
				list.add(dealers.get(i));
				
				dealMap.put(node_id, list);
			}else{
				List<ES_WORK_CUSTOM_DEALER> list = new ArrayList<ES_WORK_CUSTOM_DEALER>();
				list.add(dealers.get(i));
				
				dealMap.put(node_id, list);
			}
		}
		
		data.setOrderTree(orderTree);
		data.setCfg(cfg);
		data.setNodeMap(nodeMap);
		data.setConnectMap(connectMap);
		data.setDealMap(dealMap);
		
		return data;
	}
	
	/**
	 * 
	 * @param orderTree
	 * @param cfg
	 * @return
	 * @throws Exception
	 */
	private WORK_CUSTOM_FLOW_DATA builderWorkData(OrderTreeBusiRequest orderTree,ES_WORK_CUSTOM_CFG cfg) throws Exception{
		if(cfg == null)
			return null;
		
		String cfg_id = cfg.getCfg_id();
		String version_id = cfg.getVersion_id();
		
		ES_WORK_CUSTOM_NODE nParam = new ES_WORK_CUSTOM_NODE();
		nParam.setVersion_id(version_id);
		//查询流程环节配置
		List<ES_WORK_CUSTOM_NODE> nodes = this.workCustomCfgManager.qryNodeListFromAll(nParam, null);
		
		if(nodes==null || nodes.size()==0)
			return null;
		
		//查询流程连接配置
		ES_WORK_CUSTOM_CONNECT cParam = new ES_WORK_CUSTOM_CONNECT();
		cParam.setVersion_id(version_id);
		List<ES_WORK_CUSTOM_CONNECT> connects = this.workCustomCfgManager.qryConnectListFromAll(cParam, null);
		
		if(connects==null || connects.size()==0)
			return null;
		
		//查询流程环节处理人配置
		ES_WORK_CUSTOM_DEALER dParam = new ES_WORK_CUSTOM_DEALER();
		dParam.setCfg_id(cfg_id);
		List<ES_WORK_CUSTOM_DEALER> dealers = this.workCustomCfgManager.qryDealList(dParam, null);
		
		WORK_CUSTOM_FLOW_DATA data = new WORK_CUSTOM_FLOW_DATA();
		
		//转换为以node_id为KEY值的nodeMap
		Map<String,ES_WORK_CUSTOM_NODE> nodeMap = new HashMap<String, ES_WORK_CUSTOM_NODE>();
		for(int i=0;i<nodes.size();i++){
			String node_id = nodes.get(i).getNode_id();
			
			nodeMap.put(node_id, nodes.get(i));
		}
		
		//转换为以Source_node_id为KEY值的connectMap
		Map<String,List<ES_WORK_CUSTOM_CONNECT>> connectMap = new HashMap<String, List<ES_WORK_CUSTOM_CONNECT>>();
		for(int i=0;i<connects.size();i++){
			String srcNodeId = connects.get(i).getSource_node_id();
			
			if(connectMap.containsKey(srcNodeId)){
				List<ES_WORK_CUSTOM_CONNECT> list = connectMap.get(srcNodeId);
				
				list.add( connects.get(i));
				
				connectMap.put(srcNodeId, list);
			}else{
				List<ES_WORK_CUSTOM_CONNECT> list = new ArrayList<ES_WORK_CUSTOM_CONNECT>();
				
				list.add( connects.get(i));
				
				connectMap.put(srcNodeId, list);
			}
		}
		
		//转换为以Node_id为KEY值的dealMap
		Map<String,List<ES_WORK_CUSTOM_DEALER>> dealMap = new HashMap<String, List<ES_WORK_CUSTOM_DEALER>>();
		for(int i=0;i<dealers.size();i++){
			String node_id = dealers.get(i).getNode_id();
			
			if(dealMap.containsKey(node_id)){
				List<ES_WORK_CUSTOM_DEALER> list = dealMap.get(node_id);
				list.add(dealers.get(i));
				
				dealMap.put(node_id, list);
			}else{
				List<ES_WORK_CUSTOM_DEALER> list = new ArrayList<ES_WORK_CUSTOM_DEALER>();
				list.add(dealers.get(i));
				
				dealMap.put(node_id, list);
			}
		}
		
		data.setOrderTree(orderTree);
		data.setCfg(cfg);
		data.setNodeMap(nodeMap);
		data.setConnectMap(connectMap);
		data.setDealMap(dealMap);
		
		return data;
	}
	
	/**
	 * 构建流程对象
	 * @param orderIntent 意向单对象
	 * @param cfg 流程配置
	 * @return
	 * @throws Exception
	 */
	private WORK_CUSTOM_FLOW_DATA builderWorkData(ES_ORDER_INTENT orderIntent,ES_WORK_CUSTOM_CFG cfg) throws Exception{
		if(cfg == null)
			return null;
		
		String cfg_id = cfg.getCfg_id();
		String version_id = cfg.getVersion_id();
		
		ES_WORK_CUSTOM_NODE nParam = new ES_WORK_CUSTOM_NODE();
		nParam.setVersion_id(version_id);
		//查询流程环节配置
		List<ES_WORK_CUSTOM_NODE> nodes = this.workCustomCfgManager.qryNodeListFromAll(nParam, null);
		
		if(nodes==null || nodes.size()==0)
			return null;
		
		//查询流程连接配置
		ES_WORK_CUSTOM_CONNECT cParam = new ES_WORK_CUSTOM_CONNECT();
		cParam.setVersion_id(version_id);
		List<ES_WORK_CUSTOM_CONNECT> connects = this.workCustomCfgManager.qryConnectListFromAll(cParam, null);
		
		if(connects==null || connects.size()==0)
			return null;
		
		//查询流程环节处理人配置
		ES_WORK_CUSTOM_DEALER dParam = new ES_WORK_CUSTOM_DEALER();
		dParam.setCfg_id(cfg_id);
		List<ES_WORK_CUSTOM_DEALER> dealers = this.workCustomCfgManager.qryDealList(dParam, null);
		
		WORK_CUSTOM_FLOW_DATA data = new WORK_CUSTOM_FLOW_DATA();
		
		//转换为以node_id为KEY值的nodeMap
		Map<String,ES_WORK_CUSTOM_NODE> nodeMap = new HashMap<String, ES_WORK_CUSTOM_NODE>();
		for(int i=0;i<nodes.size();i++){
			String node_id = nodes.get(i).getNode_id();
			
			nodeMap.put(node_id, nodes.get(i));
		}
		
		//转换为以Source_node_id为KEY值的connectMap
		Map<String,List<ES_WORK_CUSTOM_CONNECT>> connectMap = new HashMap<String, List<ES_WORK_CUSTOM_CONNECT>>();
		for(int i=0;i<connects.size();i++){
			String srcNodeId = connects.get(i).getSource_node_id();
			
			if(connectMap.containsKey(srcNodeId)){
				List<ES_WORK_CUSTOM_CONNECT> list = connectMap.get(srcNodeId);
				
				list.add( connects.get(i));
				
				connectMap.put(srcNodeId, list);
			}else{
				List<ES_WORK_CUSTOM_CONNECT> list = new ArrayList<ES_WORK_CUSTOM_CONNECT>();
				
				list.add( connects.get(i));
				
				connectMap.put(srcNodeId, list);
			}
		}
		
		//转换为以Node_id为KEY值的dealMap
		Map<String,List<ES_WORK_CUSTOM_DEALER>> dealMap = new HashMap<String, List<ES_WORK_CUSTOM_DEALER>>();
		for(int i=0;i<dealers.size();i++){
			String node_id = dealers.get(i).getNode_id();
			
			if(dealMap.containsKey(node_id)){
				List<ES_WORK_CUSTOM_DEALER> list = dealMap.get(node_id);
				list.add(dealers.get(i));
				
				dealMap.put(node_id, list);
			}else{
				List<ES_WORK_CUSTOM_DEALER> list = new ArrayList<ES_WORK_CUSTOM_DEALER>();
				list.add(dealers.get(i));
				
				dealMap.put(node_id, list);
			}
		}
		
		data.setOrderIntent(orderIntent);
		data.setCfg(cfg);
		data.setNodeMap(nodeMap);
		data.setConnectMap(connectMap);
		data.setDealMap(dealMap);
		
		return data;
	}

	/**
	 * 流程实例化
	 * @param data
	 * @throws Exception
	 */
	private void newWorkCustomInstance(WORK_CUSTOM_FLOW_DATA data) throws Exception{
		if(data.getOrderTree()==null && data.getOrderIntent()==null)
			throw new Exception("传入的订单对象和意向单对象不能全为空");
		
		List<ES_WORK_CUSTOM_NODE_INS> inses = new ArrayList<ES_WORK_CUSTOM_NODE_INS>();
		
		String cfg_id = data.getCfg().getCfg_id();
		String orderId = "";
		String regionId = "";
		String countyId = "";
		
		if(data.getOrderTree() != null){
			orderId = data.getOrderTree().getOrder_id();
			Map<String, ES_DC_PUBLIC_DICT_RELATION> busiCountyMap = this.workCustomCfgManager.getBusiCountyInfoMap();
			
			//查询订单地市、县分信息
			regionId = data.getOrderTree().getOrderExtBusiRequest().getOrder_city_code();
			countyId = CommonDataFactory.getInstance().getAttrFieldValue(orderId, "district_code");
			
			if(StringUtils.isBlank(countyId) && 
					data.getOrderTree().getOrderAdslBusiRequest()!=null &&
					data.getOrderTree().getOrderAdslBusiRequest().size()>0){
				countyId = data.getOrderTree().getOrderAdslBusiRequest().get(0).getCounty_id();
				
				if(StringUtils.isNotBlank(countyId) && busiCountyMap.containsKey(countyId)){
					countyId = busiCountyMap.get(countyId).getField_value();
				}
			}
		}else{
			orderId = data.getOrderIntent().getOrder_id();
			regionId = data.getOrderIntent().getOrder_city_code();
			countyId = data.getOrderIntent().getOrder_county_code();
		}
		
		//流程实例化
		ES_WORK_CUSTOM_WORKFLOW_INS workflow = new ES_WORK_CUSTOM_WORKFLOW_INS();
		workflow.setOrder_id(orderId);
		workflow.setCfg_id(cfg_id);
		workflow.setCfg_name(data.getCfg().getCfg_name());
		workflow.setVersion_id(data.getCfg().getVersion_id());
		workflow.setCfg_level(data.getCfg().getCfg_level());
		workflow.setCfg_type(data.getCfg().getCfg_type());
		workflow.setOrder_from(data.getCfg().getOrder_from());
		workflow.setGoods_type_id(data.getCfg().getGoods_type_id());
		workflow.setGoods_type_name(data.getCfg().getGoods_type_name());
		workflow.setGoods_cat_id(data.getCfg().getGoods_cat_id());
		workflow.setGoods_cat_name(data.getCfg().getGoods_cat_name());
		workflow.setGoods_id(data.getCfg().getGoods_id());
		workflow.setGoods_name(data.getCfg().getGoods_name());
		workflow.setMatch_type(data.getCfg().getMatch_type());
		workflow.setFlow_code(data.getCfg().getFlow_code());
		workflow.setState("1");
		
		this.workCustomCfgManager.addWorkFlowIns(workflow);
		
		String workflow_id = workflow.getWorkflow_id();
		
		//流程环节实例化
		Iterator<Entry<String, ES_WORK_CUSTOM_NODE>> it = data.getNodeMap().entrySet().iterator();
		for(;it.hasNext();){
			Entry<String, ES_WORK_CUSTOM_NODE> entry = it.next();			
			ES_WORK_CUSTOM_NODE node = entry.getValue();
			
			ES_WORK_CUSTOM_NODE_INS nodeInstance = new ES_WORK_CUSTOM_NODE_INS();
			
			nodeInstance.setWorkflow_id(workflow_id);
			nodeInstance.setOrder_id(orderId);
			
			//配置信息
			nodeInstance.setCfg_id(cfg_id);
			nodeInstance.setVersion_id(data.getCfg().getVersion_id());
			nodeInstance.setCfg_name(data.getCfg().getCfg_name());
			
			//环节信息
			nodeInstance.setNode_id(node.getNode_id());
			nodeInstance.setNode_index(node.getNode_index());
			nodeInstance.setNode_name(node.getNode_name());
			nodeInstance.setNode_code(node.getNode_code());
			nodeInstance.setOut_node_code(node.getOut_node_code());
			nodeInstance.setOld_node_code(node.getOld_node_code());
			nodeInstance.setNode_type(node.getNode_type());
			nodeInstance.setDeal_type(node.getDeal_type());
			nodeInstance.setDeal_url(node.getDeal_url());
			nodeInstance.setDeal_bean(node.getDeal_bean());
			nodeInstance.setDeal_param(node.getDeal_param());
			nodeInstance.setTop_px(node.getTop_px());
			nodeInstance.setLeft_px(node.getLeft_px());
			
			//处理人信息
			if("web_condition".equals(node.getNode_type()) || "web".equals(node.getNode_type())){
				ES_WORK_CUSTOM_DEALER deal = null;
				List<ES_WORK_CUSTOM_DEALER> dealList = data.getDealMap().get(node.getNode_id());
				boolean hasDealCfg = false;
				
				if(dealList!=null && dealList.size()>0){
					Map<String,List<ES_WORK_CUSTOM_DEALER>> dealLevelMap = new HashMap<String, List<ES_WORK_CUSTOM_DEALER>>();
					
					for(int j=0;j<dealList.size();j++){
						String dealLevel = dealList.get(j).getDeal_level();
						
						if(dealLevelMap.containsKey(dealLevel)){
							List<ES_WORK_CUSTOM_DEALER> list = dealLevelMap.get(dealLevel);
							
							list.add(dealList.get(j));
							
							dealLevelMap.put(dealLevel, list);
						}else{
							List<ES_WORK_CUSTOM_DEALER> list = new ArrayList<ES_WORK_CUSTOM_DEALER>();
							
							list.add(dealList.get(j));
							
							dealLevelMap.put(dealLevel, list);
						}
					}
					
					//县分层级的处理人配置
					if(dealLevelMap.containsKey("county")){
						List<ES_WORK_CUSTOM_DEALER> list = dealLevelMap.get("county");
						
						for(int k=0;k<list.size();k++){
							if(countyId.equals(list.get(k).getCounty_id())){
								deal = list.get(k);
								break;
							}
						}
					}
					
					//地市层级的处理人配置
					if(deal==null && dealLevelMap.containsKey("region")){
						List<ES_WORK_CUSTOM_DEALER> list = dealLevelMap.get("region");
						
						for(int k=0;k<list.size();k++){
							if(regionId.equals(list.get(k).getRegion_id())){
								deal = list.get(k);
								break;
							}
						}
					}
					
					//设置环节处理单位信息
					if(deal!=null){
						nodeInstance.setDeal_id(SqlUtil.getStrValue(deal.getDeal_id()));
						nodeInstance.setD_version_id(SqlUtil.getStrValue(deal.getD_version_id()));
						nodeInstance.setDealer_type(SqlUtil.getStrValue(deal.getDealer_type()));
						nodeInstance.setDealer_code(SqlUtil.getStrValue(deal.getDealer_code()));
						nodeInstance.setDealer_name(SqlUtil.getStrValue(deal.getDealer_name()));
						nodeInstance.setDeal_level(SqlUtil.getStrValue(deal.getDeal_level()));
						hasDealCfg = true;
					}
				}
				
				if(!hasDealCfg){
					if("order".equals(data.getCfg().getCfg_type())){
						//正式订单类型的流程，没有处理人配置，设置为省中台虚拟工号
						nodeInstance.setDealer_type("default");
						nodeInstance.setDealer_code("SZT001");
						nodeInstance.setDealer_name("省中台虚拟工号");
					}else{
						//意向单类型的流程，没有处理人配置，设置为意向单省中台虚拟工号
						nodeInstance.setDealer_type("default");
						nodeInstance.setDealer_code("YXDSZT001");
						nodeInstance.setDealer_name("意向单省中台虚拟工号");
					}
				}
			}
			
			
			//实例化环节状态信息
			nodeInstance.setState_code("init");
			nodeInstance.setState_name("初始化");
			
			if("start".equals(nodeInstance.getNode_type()))
				nodeInstance.setIs_curr_step("1");
			else
				nodeInstance.setIs_curr_step("0");
			
			nodeInstance.setIs_done("0");
			nodeInstance.setState("1");
			
			inses.add(nodeInstance);
		}
		
		//保存实例
		this.workCustomCfgManager.addNodeInstances(inses);
		
		Map<String,ES_WORK_CUSTOM_NODE_INS> insMap = new HashMap<String, ES_WORK_CUSTOM_NODE_INS>();
		
		for(int i=0;i<inses.size();i++){
			String node_id = inses.get(i).getNode_id();
			
			insMap.put(node_id, inses.get(i));
		}
		
		data.setWorkflow(workflow);
		data.setInsMap(insMap);
	}

	@Override
	public WORK_CUSTOM_FLOW_DATA runWorkFlow(WORK_CUSTOM_FLOW_DATA flowData,boolean moveHis) throws Exception {
		//找到当前环节
		Map<String,ES_WORK_CUSTOM_NODE_INS> insMap = flowData.getInsMap();
		
		List<String> startNodeIds = new ArrayList<String>();
		
		Iterator<Entry<String, ES_WORK_CUSTOM_NODE_INS>> it = insMap.entrySet().iterator();
		for(;it.hasNext();){
			Entry<String, ES_WORK_CUSTOM_NODE_INS> entry = it.next();
			String node_id = entry.getKey();
			ES_WORK_CUSTOM_NODE_INS ins = entry.getValue();
			
			if("1".equals(ins.getIs_curr_step())){
				startNodeIds.add(node_id);
			}
		}
		
		boolean isFinish = false;
		
		//递归执行环节
		try{
			Iterator<String> startIt = startNodeIds.iterator();
			for(;startIt.hasNext();){
				String node_id = startIt.next();
				
				boolean branchFinish = false;
				
				branchFinish = this.runNode(node_id, flowData,true,null,0);
				
				if(branchFinish)
					isFinish = true;
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
			flowData.setRun_result(ConstsCore.ERROR_FAIL);
			flowData.setRun_msg(e.getMessage());
		}finally{
			//更新实例数据
			this.workCustomCfgManager.updaeNodeInstances(flowData,moveHis);
			
			//正式订单更新日志表
			if(flowData.getOrderTree() != null)
				this.workCustomCfgManager.saveLogBatch("insert", flowData.getRun_logs(), null);
		}
		
		if(isFinish){
			//流转完成，流程实例转完成表			
			this.workCustomCfgManager.moveIns2Finish(flowData.getWorkflow().getWorkflow_id(),"2");
		}
		
		return flowData;
	}

	@Override
	public boolean runNode(String node_id, WORK_CUSTOM_FLOW_DATA flowData,
			boolean isGoNextManual,String webCondition,int count)
			throws Exception {
		boolean isFinish = false;
		
		count++;
		//为防止死循环，加入执行次数限制
		if(count > 100)
			throw new Exception("达到最大环节执行次数，终止执行流程");
		
		//执行环节
		if(!flowData.getInsMap().containsKey(node_id)){
			return isFinish;
		}
		
		//加入已执行环节列表
		flowData.getChangedNodeIds().add(node_id);
		
		ES_WORK_CUSTOM_NODE_INS ins = flowData.getInsMap().get(node_id);
		
		//从序列中取执行次序，以便历史表按操作顺序排序
		String run_index = this.workCustomCfgManager.getNewInstanceId();
		ins.setExt_1(run_index);
		
		//流程环节类型
		String node_type = ins.getNode_type();
		
		if("start".equals(node_type)){
			isFinish = this.runStartNode(node_id, flowData,count);
		}else if("back_condition".equals(node_type)){
			isFinish = this.runBackCondition(node_id, flowData, count);
		}else if("web_condition".equals(node_type)){
			isFinish = this.runWebConditionNode(node_id, flowData, isGoNextManual, webCondition, count);
		}else if("auto".equals(node_type)){
			isFinish = this.runAutoNode(node_id, flowData, count);
		}else if("web".equals(node_type)){
			isFinish = this.runWebNode(node_id, flowData,isGoNextManual, count);
		}else if("back_wait".equals(node_type)){
			isFinish = this.runBackWaitNode(node_id, flowData, count);
		}else if("end".equals(node_type)){
			isFinish = this.runEndNode(node_id, flowData);
		}
		
		return isFinish;
	}
	
	private String getCurrTimeString() throws Exception{
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		String date = format.format(now);
		
		return date;
	}
	
	/**
	 * 执行后台代码
	 * @param node_id 环节编号
	 * @param flowData 流程数据
	 * @return
	 * @throws Exception
	 */
	private String runJava(String node_id, WORK_CUSTOM_FLOW_DATA flowData) throws Exception{
		ES_WORK_CUSTOM_NODE_INS ins = flowData.getInsMap().get(node_id);
		
		if(ins==null)
			return "";
		
		String ret = "";
		
		String node_type = ins.getNode_type();
		String deal_type = ins.getDeal_type();
		String deal_bean = ins.getDeal_bean();
		String deal_param = ins.getDeal_param();
		
		if(StringUtils.isBlank(deal_type) || (StringUtils.isBlank(deal_bean) && StringUtils.isBlank(deal_param)))
			return "";
		
		if("rule".equals(deal_type)){
			//执行规则
			RuleBusiBean ruleBean =  SpringContextHolder.getBean("RuleBusiBean");
			
			ruleBean.setNode_id(node_id);
			ruleBean.setFlowData(flowData);
			
			ruleBean.doBusi();
		}else if("bean".equals(deal_type)){
			//获取业务BEAN
			IBusiInterface bean = SpringContextHolder.getBean(deal_bean);
			
			bean.setNode_id(node_id);
			bean.setFlowData(flowData);
			
			if("back_wait".equals(node_type)){
				//后台等待类型的环节，执行后台判断接口doBackWaitCheck
				boolean b = bean.doBackWaitCheck();
				
				ret = String.valueOf(b);
			}else{
				ret = bean.doBusi();
			}
		}else if("js".equals(deal_type)){
			//执行JS
			JSBusiBean jsBean =  SpringContextHolder.getBean("JSBusiBean");
			
			jsBean.setNode_id(node_id);
			jsBean.setFlowData(flowData);
			
			ret = jsBean.doBusi();
		}
		
		return ret;
	}
	
	private ES_WORK_CUSTOM_LOG getNewLog(ES_WORK_CUSTOM_NODE_INS ins) throws Exception{
		ES_WORK_CUSTOM_LOG new_log = new ES_WORK_CUSTOM_LOG();

		new_log.setInstance_id(ins.getInstance_id());
		new_log.setWorkflow_id(ins.getWorkflow_id());
		new_log.setOrder_id(ins.getOrder_id());
		new_log.setNode_name(ins.getNode_name());
		new_log.setSource_from("ECS");
		new_log.setCreate_date(this.getCurrTimeString());
		
		AdminUser user = ManagerUtils.getAdminUser();
		
		if(user != null){
			new_log.setOp_id(user.getUserid());
			new_log.setOp_name(user.getRealname());
        }
		
		return new_log;
	}
	
	/**
	 * 设置日志备注
	 * @param flowData 流程数据
	 * @param ins 环节实例
	 * @param new_log 新创建的日志对象
	 * @throws Exception
	 */
	private void setLogRemark(WORK_CUSTOM_FLOW_DATA flowData,ES_WORK_CUSTOM_NODE_INS ins,
			ES_WORK_CUSTOM_LOG new_log) throws Exception{
		new_log.setRemark(ins.getRemark());
		
		flowData.getRun_logs().add(new_log);
	}

	/**
	 * 执行开始类型的环节
	 * @param node_id 环节编号
	 * @param flowData 流程数据
	 * @param count 计数器
	 * @return
	 * @throws Exception
	 */
	private boolean runStartNode(String node_id, WORK_CUSTOM_FLOW_DATA flowData,int count) throws Exception{
		boolean isFinish = false;
		
		ES_WORK_CUSTOM_NODE_INS ins = flowData.getInsMap().get(node_id);
		
		ES_WORK_CUSTOM_LOG new_log = this.getNewLog(ins);
		
		this.newEsOrderLock4BackNode(flowData, ins);
		
		try{
			//执行环节代码
			this.runJava(node_id,flowData);
			
			//实例化环节状态信息
			ins.setState_code("finish");
			ins.setState_name("完成");
			ins.setIs_curr_step("0");
			ins.setIs_done("1");
			
			//流程执行时，只有执行的第一个环节记录传入的备注
			if(count > 1)
				ins.setRemark("");
			
			flowData.setRun_result(ConstsCore.ERROR_SUCC);
		}catch(Exception e){
			//实例化环节状态信息
			ins.setState_code("error");
			ins.setState_name("错误");
			ins.setIs_curr_step("1");
			ins.setIs_done("0");
			ins.setRemark(e.getMessage());
			
			flowData.setRun_result(ConstsCore.ERROR_FAIL);
			flowData.setRun_msg(e.getMessage());
			
			throw e;
		}finally{
			this.setLogRemark(flowData, ins, new_log);
		}
		
		//设置更新时间
		String date = this.getCurrTimeString();
		ins.setUpdate_date(date);
		
		//获取连接信息
		List<ES_WORK_CUSTOM_CONNECT> connects = flowData.getConnectMap().get(node_id);
		
		for(int i=0;i<connects.size();i++){
			String target_node_id = connects.get(i).getTarget_node_id();
			//执行下一环节
			boolean branchFinish = false;
			
			branchFinish = this.runNode(target_node_id, flowData, false, null,count);
			
			if(branchFinish)
				isFinish = true;
		}
		
		return isFinish;
	}
	
	/**
	 * 执行后台条件类型的环节
	 * @param node_id 环节编号
	 * @param flowData 流程数据
	 * @param count 计数器
	 * @return
	 * @throws Exception
	 */
	private boolean runBackCondition(String node_id, WORK_CUSTOM_FLOW_DATA flowData,int count) throws Exception{
		boolean isFinish = false;
		
		ES_WORK_CUSTOM_NODE_INS ins = flowData.getInsMap().get(node_id);
		
		String retCondition = "";
		
		ES_WORK_CUSTOM_LOG new_log = this.getNewLog(ins);
		
		this.newEsOrderLock4BackNode(flowData, ins);
		
		try{
			//执行后台代码，获取返回的分支条件
			retCondition = this.runJava(node_id,flowData);
			
			//更新环节实例状态
			ins.setState_code("finish");
			ins.setState_name("完成");
			ins.setIs_curr_step("0");
			ins.setIs_done("1");
			
			//流程执行时，只有执行的第一个环节记录传入的备注
			if(count > 1)
				ins.setRemark("");
			
			this.setLogRemark(flowData, ins, new_log);
			
			flowData.setRun_result(ConstsCore.ERROR_SUCC);
		}catch(Exception e){
			//更新环节实例状态
			ins.setState_code("error");
			ins.setState_name("错误");
			ins.setIs_curr_step("1");
			ins.setIs_done("0");
			ins.setRemark(e.getMessage());
			
			this.setLogRemark(flowData, ins, new_log);
			
			flowData.setRun_result(ConstsCore.ERROR_FAIL);
			flowData.setRun_msg(e.getMessage());
			
			throw e;
		}
		
		//设置更新时间
		String date = this.getCurrTimeString();
		ins.setUpdate_date(date);
		
		//获取连接信息
		List<ES_WORK_CUSTOM_CONNECT> connects = flowData.getConnectMap().get(node_id);
		
		boolean isMatchCondition = false;
		
		for(int i=0;i<connects.size();i++){
			String condition = connects.get(i).getConnect_label();
			String target_node_id = connects.get(i).getTarget_node_id();
			
			//匹配条件
			if(retCondition.equals(condition)){
				isMatchCondition = true;
				
				boolean branchFinish = false;
				//执行下一环节
				branchFinish = this.runNode(target_node_id, flowData, false, null,count);
				
				if(branchFinish)
					isFinish = true;
				
				break;
			}
		}
		
		//未找到匹配条件
		if(!isMatchCondition){
			ins.setState_code("error");
			ins.setState_name("错误");
			ins.setIs_curr_step("1");
			ins.setIs_done("0");
			ins.setRemark("未匹配到下一环节的分支条件，执行的分支条件为："+retCondition);
			
			this.setLogRemark(flowData, ins, new_log);
			
			flowData.setRun_result(ConstsCore.ERROR_FAIL);
			flowData.setRun_msg(ins.getRemark());
		}
		
		return isFinish;
	}
	
	/**
	 * 执行页面条件类型的环节
	 * @param node_id 环节编号
	 * @param flowData 流程数据
	 * @param isGoNextManual 是否流转到下一环节
	 * @param webCondition  分支条件
	 * @param count 计数器
	 * @return
	 * @throws Exception
	 */
	private boolean runWebConditionNode(String node_id, WORK_CUSTOM_FLOW_DATA flowData,
			boolean isGoNextManual,String webCondition,int count) throws Exception{
		boolean isFinish = false;
		
		ES_WORK_CUSTOM_NODE_INS ins = flowData.getInsMap().get(node_id);
		
		if(webCondition==null)
			webCondition = "";
		
		ES_WORK_CUSTOM_LOG new_log = this.getNewLog(ins);
		
		if(isGoNextManual){
			//手动前往下一环节
			ins.setState_code("finish");
			ins.setState_name("完成");
			ins.setIs_curr_step("0");
			ins.setIs_done("1");
			
			//流程执行时，只有执行的第一个环节记录传入的备注
			if(count > 1)
				ins.setRemark("");
			
			flowData.setRun_result(ConstsCore.ERROR_SUCC);
			
			//设置更新时间
			String date = this.getCurrTimeString();
			ins.setUpdate_date(date);
			
			//获取连接信息
			List<ES_WORK_CUSTOM_CONNECT> connects = flowData.getConnectMap().get(node_id);
			
			boolean isMatchCondition = false;
			
			for(int i=0;i<connects.size();i++){
				String condition = connects.get(i).getConnect_label();
				String target_node_id = connects.get(i).getTarget_node_id();
				
				if(webCondition.equals(condition)){
					isMatchCondition = true;
					
					boolean branchFinish = false;
					branchFinish = this.runNode(target_node_id, flowData, false, null,count);
					
					if(branchFinish)
						isFinish = true;
					
					break;
				}
			}
			
			if(!isMatchCondition){
				ins.setState_code("error");
				ins.setState_name("错误");
				ins.setIs_curr_step("1");
				ins.setIs_done("0");
				ins.setRemark("未匹配到下一环节的分支条件，执行的分支条件为："+webCondition);
				
				flowData.setRun_result(ConstsCore.ERROR_FAIL);
				flowData.setRun_msg(ins.getRemark());
			}
			
			this.setLogRemark(flowData, ins, new_log);
		}else{
			//处理单位配置为个人时沉淀处理人员
			this.updateLockInfo(flowData, ins);
			
			//等待前台处理
			ins.setState_code("wait");
			ins.setState_name("等待");
			ins.setIs_curr_step("1");
			ins.setIs_done("0");
			ins.setRemark("等待处理");
			
			flowData.setRun_result(ConstsCore.ERROR_SUCC);
		}
		
		return isFinish;
	}
	
	
	

	/**
	 * 执行后台类型的环节
	 * @param node_id 环节编号
	 * @param flowData 流程数据
	 * @param count 计数器
	 * @return
	 * @throws Exception
	 */
	private boolean runAutoNode(String node_id, WORK_CUSTOM_FLOW_DATA flowData,int count) throws Exception{
		boolean isFinish = false;
		
		ES_WORK_CUSTOM_NODE_INS ins = flowData.getInsMap().get(node_id);
		
		ES_WORK_CUSTOM_LOG new_log = this.getNewLog(ins);
		
		this.newEsOrderLock4BackNode(flowData, ins);
		
		try{
			//执行环节代码
			this.runJava(node_id,flowData);
			
			//更新环节实例状态
			ins.setState_code("finish");
			ins.setState_name("完成");
			ins.setIs_curr_step("0");
			ins.setIs_done("1");
			
			//流程执行时，只有执行的第一个环节记录传入的备注
			if(count > 1)
				ins.setRemark("");
			
			this.setLogRemark(flowData, ins, new_log);
			
			flowData.setRun_result(ConstsCore.ERROR_SUCC);
		}catch(Exception e){
			//更新环节实例状态
			ins.setState_code("error");
			ins.setState_name("错误");
			ins.setIs_curr_step("1");
			ins.setIs_done("0");
			
			if(e instanceof java.lang.reflect.InvocationTargetException){
				String msg = ((java.lang.reflect.InvocationTargetException) e).getTargetException().getMessage();
				ins.setRemark(msg);
			}else{
				ins.setRemark(e.getMessage());
			}
			
			this.setLogRemark(flowData, ins, new_log);
			
			flowData.setRun_result(ConstsCore.ERROR_FAIL);
			flowData.setRun_msg(e.getMessage());
			
			throw e;
		}
		
		//设置更新时间
		String date = this.getCurrTimeString();
		ins.setUpdate_date(date);
		
		List<ES_WORK_CUSTOM_CONNECT> connects = flowData.getConnectMap().get(node_id);
		
		for(int i=0;i<connects.size();i++){
			String target_node_id = connects.get(i).getTarget_node_id();
			
			boolean branchFinish = false;
			
			branchFinish = this.runNode(target_node_id, flowData, false, null,count);
			
			if(branchFinish)
				isFinish = true;
		}
		
		return isFinish;
	}
	
	/**
	 * 执行前台页面类型的环节
	 * @param node_id 环节编号
	 * @param flowData 流程数据
	 * @param isGoNextManual 是否流转到下一环节
	 * @param count 计数器
	 * @return
	 * @throws Exception
	 */
	private boolean runWebNode(String node_id, WORK_CUSTOM_FLOW_DATA flowData,
			boolean isGoNextManual,int count) throws Exception{
		boolean isFinish = false;
		
		ES_WORK_CUSTOM_NODE_INS ins = flowData.getInsMap().get(node_id);
		
		ES_WORK_CUSTOM_LOG new_log = this.getNewLog(ins);
		
		if(isGoNextManual){
			//手动前往下一环节
			ins.setState_code("finish");
			ins.setState_name("完成");
			ins.setIs_curr_step("0");
			ins.setIs_done("1");
			
			//流程执行时，只有执行的第一个环节记录传入的备注
			if(count > 1)
				ins.setRemark("");
			
			flowData.setRun_result(ConstsCore.ERROR_SUCC);
			
			//设置更新时间
			String date = this.getCurrTimeString();
			ins.setUpdate_date(date);
			
			//执行下一环节
			List<ES_WORK_CUSTOM_CONNECT> connects = flowData.getConnectMap().get(node_id);
			
			for(int i=0;i<connects.size();i++){
				String target_node_id = connects.get(i).getTarget_node_id();
				
				boolean branchFinish = false;
				
				branchFinish = this.runNode(target_node_id, flowData, false, null,count);
				
				if(branchFinish)
					isFinish = true;
			}
			
			this.setLogRemark(flowData, ins, new_log);
		}else{
			//处理单位配置为个人时沉淀处理人员
			this.updateLockInfo(flowData, ins);
			
			//等待页面处理
			ins.setState_code("wait");
			ins.setState_name("等待");
			ins.setIs_curr_step("1");
			ins.setIs_done("0");
			ins.setRemark("等待处理");
			
			flowData.setRun_result(ConstsCore.ERROR_SUCC);
		}
		
		return isFinish;
	}
	
	/**
	 * 执行后台等待类型的环节
	 * @param node_id 环节编号
	 * @param flowData 流程数据
	 * @param isGoNextManual 是否流转到下一环节
	 * @param count 计数器
	 * @return
	 * @throws Exception
	 */
	private boolean runBackWaitNode(String node_id, WORK_CUSTOM_FLOW_DATA flowData,
			int count) throws Exception{
		boolean isFinish = false;
		
		ES_WORK_CUSTOM_NODE_INS ins = flowData.getInsMap().get(node_id);
		
		ES_WORK_CUSTOM_LOG new_log = this.getNewLog(ins);
		
		this.newEsOrderLock4BackNode(flowData, ins);
		
		//根据代码判断是否等待
		String ret = "";
		try{
			ret = this.runJava(node_id,flowData);
		}catch(Exception e){
			//更新环节实例状态
			ins.setState_code("error");
			ins.setState_name("错误");
			ins.setIs_curr_step("1");
			ins.setIs_done("0");
			ins.setRemark(e.getMessage());
			
			this.setLogRemark(flowData, ins, new_log);
			
			flowData.setRun_result(ConstsCore.ERROR_FAIL);
			flowData.setRun_msg(e.getMessage());
			
			throw e;
		}
		
		if("true".equals(ret)){
			//执行流程
			ins.setState_code("finish");
			ins.setState_name("完成");
			ins.setIs_curr_step("0");
			ins.setIs_done("1");
			
			//流程执行时，只有执行的第一个环节记录传入的备注
			if(count > 1)
				ins.setRemark("");
			
			flowData.setRun_result(ConstsCore.ERROR_SUCC);
			
			//执行下一环节
			List<ES_WORK_CUSTOM_CONNECT> connects = flowData.getConnectMap().get(node_id);
			
			for(int i=0;i<connects.size();i++){
				String target_node_id = connects.get(i).getTarget_node_id();
				boolean branchFinish = false;
				
				branchFinish = this.runNode(target_node_id, flowData, false, null,count);
				
				if(branchFinish)
					isFinish = true;
			}
			
			this.setLogRemark(flowData, ins, new_log);
		}else{
			//等待
			ins.setState_code("wait");
			ins.setState_name("等待");
			ins.setIs_curr_step("1");
			ins.setIs_done("0");
			ins.setRemark("后台等待");
			
			flowData.setRun_result(ConstsCore.ERROR_SUCC);
		}
		
		return isFinish;
	}
	
	/**
	 * 执行结束环节的代码
	 * @param node_id
	 * @param flowData
	 * @return
	 * @throws Exception
	 */
	private boolean runEndNode(String node_id, WORK_CUSTOM_FLOW_DATA flowData) throws Exception{
		//执行环节代码
		ES_WORK_CUSTOM_NODE_INS ins = flowData.getInsMap().get(node_id);
		
		ES_WORK_CUSTOM_LOG new_log = this.getNewLog(ins);
		
		this.newEsOrderLock4BackNode(flowData, ins);
		
		try{
			this.runJava(node_id,flowData);
			
			//更新环节实例状态
			ins.setState_code("finish");
			ins.setState_name("完成");
			ins.setIs_curr_step("1");
			ins.setIs_done("1");
			ins.setRemark("");
			
			this.setLogRemark(flowData, ins, new_log);
			
			//订单完成，清空锁定人信息
			this.clearLockInfo(flowData, ins);

			flowData.setRun_result(ConstsCore.ERROR_SUCC);
		}catch(Exception e){
			//更新环节实例状态
			ins.setState_code("error");
			ins.setState_name("错误");
			ins.setIs_curr_step("1");
			ins.setIs_done("0");
			ins.setRemark(e.getMessage());
			
			this.setLogRemark(flowData, ins, new_log);
			
			flowData.setRun_result(ConstsCore.ERROR_FAIL);
			flowData.setRun_msg(e.getMessage());
			
			throw e;
		}
		
		return true;
	}
	
	/**
	 * 清空锁定人信息
	 * @param flowData
	 * @param ins
	 * @throws Exception
	 */
	private void clearLockInfo(WORK_CUSTOM_FLOW_DATA flowData,ES_WORK_CUSTOM_NODE_INS ins) throws Exception{
		if(flowData.getOrderTree() == null)
			return;
		
		String curr_node_code = ins.getOld_node_code();
		
		if(StringUtils.isBlank(curr_node_code))
			return;
		
		OrderLockBusiRequest curr_lock = CommonDataFactory.getInstance().
				getOrderLockBusiRequest(flowData.getOrderTree(),curr_node_code);
		
		//没有锁定信息，无需更新
		if(curr_lock == null)
			return;
		
		//curr_lock.store();无法更新空字符串，因此先删除，再插入
		String deleteSql = "delete from es_order_lock a WHERE a.order_id='"
				+curr_lock.getOrder_id()+"' and a.tache_code='"+curr_lock.getTache_code()+"'";
		
		this.workCustomCfgManager.execute(deleteSql);
		
		curr_lock.setOrder_id(flowData.getOrderTree().getOrder_id());
		curr_lock.setTache_code(curr_node_code);	//NODE_CODE设置为锁定环节编码
		curr_lock.setLock_user_id("");
		curr_lock.setLock_user_name("");
		curr_lock.setLock_status(EcsOrderConsts.LOCK_STATUS);
		curr_lock.setDealer_type("");
		
		try {
			curr_lock.setLock_time(DateUtil.getTime2());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		curr_lock.setSource_from(ManagerUtils.getSourceFrom());
		curr_lock.setDb_action(ConstsCore.DB_ACTION_INSERT);
		curr_lock.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		curr_lock.store();
	}
	
	/**
	 * 锁定意向单表
	 * @param flowData
	 * @param deal
	 * @throws Exception
	 */
	private void lockOrderIntent(WORK_CUSTOM_FLOW_DATA flowData,ES_WORK_CUSTOM_DEALER deal) throws Exception{
		if(flowData.getOrderIntent() == null)
			return;
		
		//更新意向单表锁定信息
		ES_ORDER_INTENT orderIntent = flowData.getOrderIntent();
		
		orderIntent.setLock_id(deal.getDealer_code());
		orderIntent.setLock_name(deal.getDealer_name());
		
		this.workCustomCfgManager.updateOrderIntent(orderIntent);
	}
	
	/**
	 * 解锁意向单表
	 * @param flowData
	 * @throws Exception
	 */
	private void unLockOrderIntent(WORK_CUSTOM_FLOW_DATA flowData) throws Exception{
		if(flowData.getOrderIntent() == null)
			return;
		
		//更新意向单表锁定信息
		ES_ORDER_INTENT orderIntent = flowData.getOrderIntent();
		
		orderIntent.setLock_id("");
		orderIntent.setLock_name("");
		
		this.workCustomCfgManager.updateOrderIntent(orderIntent);
	}
	
	/**
	 * 发送订单通知短信
	 * @param order_id
	 * @param deal
	 * @throws Exception
	 */
	private void sendNoticeSms(String order_id,ES_WORK_CUSTOM_DEALER deal) throws Exception{
		List<User> users = null;
		
		if("person".equals(deal.getDealer_type())){
			//根据人员编号查询人员
			String userid = deal.getDealer_code();
			
			if(StringUtils.isNotBlank(userid)){
				User pojo = new User();
				pojo.setUserid(userid);
				pojo.setState("1");
				pojo.setSms_receive("1");
				
				users = this.workCustomCfgManager.qryUserList(pojo, null);
			}
		}else if("team".equals(deal.getDealer_type())){
			String team_id = deal.getDealer_code();
			
			if(StringUtils.isNotBlank(team_id)){
				User pojo = new User();
				pojo.setState("1");
				pojo.setSms_receive("1");
				
				List<SqlBuilderInterface> sqlBuilds = new ArrayList<SqlBuilderInterface>();
				
				SqlBuilder teamCondition = new SqlBuilder();
				
				StringBuilder builder = new StringBuilder();
				
				builder.append(" userid in ( select rel.userid from es_user_team_rel rel ").
					append(" where rel.team_id='"+team_id+"' ) ");
				
				teamCondition.setCol_name("");
				teamCondition.setParm(builder.toString());
				
				sqlBuilds.add(teamCondition);			
				
				users = this.workCustomCfgManager.qryUserList(pojo, sqlBuilds);
			}
		}else if("org".equals(deal.getDealer_type())){
			//根据组织编号查询人员
			String orgid = deal.getDealer_code();

			if(StringUtils.isNotBlank(orgid)){
				User pojo = new User();
				pojo.setOrg_id(orgid);
				pojo.setState("1");
				pojo.setSms_receive("1");
				
				users = this.workCustomCfgManager.qryUserList(pojo, null);
			}
		}
		
		if(users!=null && users.size()>0){
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			List<ES_WORK_SMS_SEND> listPojo = new ArrayList<ES_WORK_SMS_SEND>();
			for(int i=0;i<users.size();i++){
				User user = users.get(i);
				String phone_num = user.getPhone_num();
				
				Map<String, String> msgMap = new HashMap<String, String>();
				msgMap.put("orgname", user.getDep_name());
				msgMap.put("orderid", order_id);
				msgMap.put("userid", user.getUserid());
				
				//获取短信内容
				// 您的【${orgname}订单池】收到待处理订单${orderid}（订单编号），可用工号【${userid}】登录订单中心领取处理。
				IRemoteSmsService localRemoteSmsService = ApiContextHolder.getBean("localRemoteSmsService");
				String smsContent = localRemoteSmsService.getSMSTemplate("SMS_LOGIN_CODE", msgMap);
				
				ES_WORK_SMS_SEND smsSendPojo = new ES_WORK_SMS_SEND();
				smsSendPojo.setBill_num("10010");
				smsSendPojo.setService_num(phone_num);
				smsSendPojo.setSms_data(smsContent);
				smsSendPojo.setOrder_id(order_id);
				listPojo.add(smsSendPojo);
				
				/*
				AopSmsSendReq smsSendReq = new AopSmsSendReq();
				smsSendReq.setBill_num("10010");// 短信发送号码
				smsSendReq.setService_num(phone_num);// 接受号码--省内联通号码
				smsSendReq.setSms_data(smsContent);
				
				
				TaskThreadPool taskPool = new TaskThreadPool(new Task(smsSendReq) {
					@SuppressWarnings("rawtypes")
					@Override
					public ZteResponse execute(ZteRequest zteRequest) throws Exception {
						AopSmsSendReq smsSendReq = (AopSmsSendReq) zteRequest;
						ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
						AopSmsSendResp resp = client.execute(smsSendReq, AopSmsSendResp.class);
						return resp;
					}
				});
				ThreadPoolFactory.orderExecute(taskPool);
				*/
			}
			AopSmsSendReq smsSendReq = new AopSmsSendReq();
			smsSendReq.setListpojo(listPojo);
			client.execute(smsSendReq, AopSmsSendResp.class);
		}
	}
	
	/**
	 * 流程流转时重新获取处理人信息
	 * @param flowData
	 * @param dealers
	 * @return
	 * @throws Exception
	 */
	private ES_WORK_CUSTOM_DEALER getDealerCfg(WORK_CUSTOM_FLOW_DATA flowData,List<ES_WORK_CUSTOM_DEALER> dealers) throws Exception{
		String regionId = "";
		String countyId = "";
		String orderId = "";
		ES_WORK_CUSTOM_DEALER deal = null;
		
		if(flowData.getOrderTree() != null){
			orderId = flowData.getOrderTree().getOrder_id();

			//查询订单地市、县分信息
			regionId = flowData.getOrderTree().getOrderExtBusiRequest().getOrder_city_code();
			//首先看行政县分字段是否有沉淀
			countyId = CommonDataFactory.getInstance().getAttrFieldValue(orderId, "district_code");
			
			if(StringUtils.isBlank(countyId) && 
					flowData.getOrderTree().getOrderAdslBusiRequest()!=null &&
							flowData.getOrderTree().getOrderAdslBusiRequest().size()>0){
				Map<String, ES_DC_PUBLIC_DICT_RELATION> busiCountyMap = this.workCustomCfgManager.getBusiCountyInfoMap();
				
				//行政县分未沉淀数据，看营业县分
				countyId = flowData.getOrderTree().getOrderAdslBusiRequest().get(0).getCounty_id();
				
				//根据营业县分查行政县分
				if(StringUtils.isNotBlank(countyId) && busiCountyMap.containsKey(countyId)){
					countyId = busiCountyMap.get(countyId).getField_value();
				}
			}else if(!countyId.contains("ZJ")){
				//没有ZJ的加上ZJ
				countyId = "ZJ"+countyId;
			}
		}else{
			orderId = flowData.getOrderIntent().getOrder_id();
			regionId = flowData.getOrderIntent().getOrder_city_code();
			countyId = flowData.getOrderIntent().getOrder_county_code();
		}
		
		if(dealers!=null && dealers.size()>0){
			Map<String,List<ES_WORK_CUSTOM_DEALER>> dealLevelMap = new HashMap<String, List<ES_WORK_CUSTOM_DEALER>>();
			
			for(int j=0;j<dealers.size();j++){
				String dealLevel = dealers.get(j).getDeal_level();
				
				if(dealLevelMap.containsKey(dealLevel)){
					List<ES_WORK_CUSTOM_DEALER> list = dealLevelMap.get(dealLevel);
					
					list.add(dealers.get(j));
					
					dealLevelMap.put(dealLevel, list);
				}else{
					List<ES_WORK_CUSTOM_DEALER> list = new ArrayList<ES_WORK_CUSTOM_DEALER>();
					
					list.add(dealers.get(j));
					
					dealLevelMap.put(dealLevel, list);
				}
			}
			
			//县分层级的处理人配置
			if(dealLevelMap.containsKey("county")){
				List<ES_WORK_CUSTOM_DEALER> list = dealLevelMap.get("county");
				
				for(int k=0;k<list.size();k++){
					if(countyId.equals(list.get(k).getCounty_id())){
						deal = list.get(k);
						break;
					}
				}
			}
			
			//地市层级的处理人配置
			if(deal==null && dealLevelMap.containsKey("region")){
				List<ES_WORK_CUSTOM_DEALER> list = dealLevelMap.get("region");
				
				for(int k=0;k<list.size();k++){
					if(regionId.equals(list.get(k).getRegion_id())){
						deal = list.get(k);
						break;
					}
				}
			}
		}
		
		return deal;
	}

    @Override
	public WORK_CUSTOM_FLOW_DATA runNodeManual(String instance_id,boolean isGoNextManual,
			String webCondition,String remark,String json_param) throws Exception {
		//装载流程数据
		WORK_CUSTOM_FLOW_DATA flowData = this.loadWorkFlowDataByInsId(instance_id);
		
		flowData.setJson_param(json_param);
		
		Map<String, ES_WORK_CUSTOM_NODE_INS> insMap = flowData.getInsMap();
		
		String node_id = "";
		String orderId="";
		String node_name ="";
		
		//根据传入的环节实例编号匹配环节
		Iterator<Entry<String, ES_WORK_CUSTOM_NODE_INS>> it = insMap.entrySet().iterator();
		for(;it.hasNext();){
			Entry<String, ES_WORK_CUSTOM_NODE_INS> entry = it.next();
			
			ES_WORK_CUSTOM_NODE_INS ins = entry.getValue();
			
			if(ins.getInstance_id().equals(instance_id)){
				node_id = ins.getNode_id();
				orderId = ins.getOrder_id();
				node_name = ins.getNode_name();
				ins.setRemark(remark);
				ins.setExt_3(webCondition);
				
				if(!"1".equals(ins.getIs_curr_step()))
					throw new Exception("不能执行非当前环节的流程节点");
			}
		}
		
		if(StringUtils.isBlank(node_id))
			throw new Exception("根据环节实例编号"+instance_id+"未查询到实例");
		
		boolean isFinish = false;
		
		//递归执行环节
		try{
			isFinish = this.runNode(node_id , flowData, isGoNextManual, webCondition, 0);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			flowData.setRun_result(ConstsCore.ERROR_FAIL);
			flowData.setRun_msg(e.getMessage());
		}finally{
			//更新实例数据
			this.workCustomCfgManager.updaeNodeInstances(flowData,false);
			
			//正式订单更新日志表
			if(flowData.getOrderTree() != null)
				this.workCustomCfgManager.saveLogBatch("insert", flowData.getRun_logs(), null);
		}
		
		if(isFinish){
			//流转完成，流程实例转完成表			
			this.workCustomCfgManager.moveIns2Finish(flowData.getWorkflow().getWorkflow_id(),"2");
		}
		
		remark = node_name+"环节提交 ; "+remark;
		
		this.insertIntentHanleLogs(flowData,orderId,remark,node_name);
		
		return flowData;
	}
    
    /**
     * 根据订单编号和环节实例编码获取环节实例编号
     * @param order_id
     * @param node_code
     * @return
     * @throws Exception
     */
    private String getInstanceIdByNodeCode(String order_id,String node_code) throws Exception{
    	ES_WORK_CUSTOM_NODE_INS pojo = new ES_WORK_CUSTOM_NODE_INS();
    	pojo.setOrder_id(order_id);
    	pojo.setNode_code(node_code);
    	
    	List<ES_WORK_CUSTOM_NODE_INS> ret = this.workCustomCfgManager.qryInsList(pojo , null);
    	
    	if (ret==null || ret.size()==0)
    		throw new Exception("根据订单号："+order_id+"和环节编码："+node_code+"未找到环节实例！");
    	
    	if (ret.size() > 1)
    		throw new Exception("根据订单号："+order_id+"和环节编码："+node_code+"找到多个环节实例！");
    	
    	return ret.get(0).getInstance_id();
    }
    
    @Override
    public WORK_CUSTOM_FLOW_DATA runNodeManualByCode(String order_id,String node_code,boolean isGoNextManual,
			String webCondition,String remark,String json_param) throws Exception{
    	
    	String instance_id = this.getInstanceIdByNodeCode(order_id, node_code);
    	
    	return this.runNodeManual(instance_id, isGoNextManual, webCondition, remark, json_param);
    }

    /**
     * 写入意向单日志
     * @param flowData
     * @param orderId
     * @param remark
     * @param node_name
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void insertIntentHanleLogs(WORK_CUSTOM_FLOW_DATA flowData, String orderId, 
			String remark, String node_name) {
	    if(flowData.getOrderIntent() == null){
            return;
	    }
	    
        IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
        
        Map<String, Object> map = new HashMap();
        AdminUser user = ManagerUtils.getAdminUser();
        
        map.put("order_id", orderId);
        map.put("remark", remark);
        map.put("source_from", "ECS");
        map.put("create_time", "sysdate");
        map.put("type_code", "intent");
        map.put("action", node_name);
        
        if(user != null){
        	map.put("op_id", user.getUserid());
        	map.put("op_name", user.getRealname());
        }
        
        baseDaoSupport.insert("es_intent_handle_logs", map);
    }

    @Override
	public WORK_CUSTOM_FLOW_DATA continueWorkFlow(String order_id) throws Exception {
		//装载流程数据
		WORK_CUSTOM_FLOW_DATA flowData = this.loadWorkFlowDataByOrderId(order_id);
		
		//执行流程
		return this.runWorkFlow(flowData,false);
	}

	@Override
	public String gotoNode(String instance_id,String remark) throws Exception {
		//装载流程数据
		WORK_CUSTOM_FLOW_DATA flowData = this.loadWorkFlowDataByInsId(instance_id);
		
		String node_id = "";
		ES_WORK_CUSTOM_NODE_INS gotoIns = null;
		Map<String, ES_WORK_CUSTOM_NODE_INS> insMap = flowData.getInsMap();
		
		List<ES_WORK_CUSTOM_NODE_INS> currNodeIns = new ArrayList<ES_WORK_CUSTOM_NODE_INS>();
		
		//根据传入的环节实例编号匹配环节
		Iterator<Entry<String, ES_WORK_CUSTOM_NODE_INS>> it = insMap.entrySet().iterator();
		for(;it.hasNext();){
			Entry<String, ES_WORK_CUSTOM_NODE_INS> entry = it.next();
			
			ES_WORK_CUSTOM_NODE_INS ins = entry.getValue();
			
			if(ins.getInstance_id().equals(instance_id)){
				if("1".equals(ins.getIs_curr_step()))
					throw new Exception("跳转的环节为当前环节，不能跳转");
				
				node_id = ins.getNode_id();
				gotoIns = ins;
				gotoIns.setIs_curr_step("1");
				gotoIns.setState_code("init");
				gotoIns.setState_name("初始化");
				
				flowData.getChangedNodeIds().add(node_id);
			}else if("1".equals(ins.getIs_curr_step())){
				currNodeIns.add(ins);
			}
		}
		
		if(StringUtils.isBlank(node_id))
			throw new Exception("根据环节实例编号"+instance_id+"未查询到实例");
		
		ES_WORK_CUSTOM_LOG new_log = this.getNewLog(gotoIns);
		
		//流程跳转到某一节点
		StringBuilder jumpMsg = new StringBuilder();
		
		jumpMsg.append(remark).append("。环节跳转：");
		
		for(int i=0;i<currNodeIns.size();i++){
			if(i != 0)
				jumpMsg.append(",");
			
			jumpMsg.append("【"+currNodeIns.get(i).getNode_id()+"】"+currNodeIns.get(i).getNode_name());
		}
		
		jumpMsg.append(" ==> 【"+gotoIns.getNode_id()+"】"+gotoIns.getNode_name());
		
		//截取最大256个字节
		String msg = StringUtil.getSubStringByByte(jumpMsg.toString(), 0, 256);
		
		gotoIns.setRemark(msg);
		
		this.setLogRemark(flowData, gotoIns, new_log);
		
		//修改当前环节的备注
		for(int i=0;i<currNodeIns.size();i++){
			//修改环节信息，设为非当前环节
			currNodeIns.get(i).setIs_curr_step("0");
			
			currNodeIns.get(i).setRemark(msg);
			
			//加入改变的环节列表
			flowData.getChangedNodeIds().add(currNodeIns.get(i).getNode_id());
		}
		
		//跳转标记，防止死循环
		String jumpMark = this.workCustomCfgManager.getNewInstanceId();
		
		//跳转环节之后的所有环节改为--未执行
		this.SetNodeUnDone(node_id, msg, jumpMark, flowData);
		
		//从序列中取执行次序，以便历史表按操作顺序排序，目标环节最后更新
		String run_index = this.workCustomCfgManager.getNewInstanceId();
		gotoIns.setExt_1(run_index);
		
		//重新沉淀处理人信息
		this.updateLockInfo(flowData, gotoIns);
		
		//更新实例数据
		this.workCustomCfgManager.updaeNodeInstances(flowData,false);
		
		//正式订单更新日志表
		if(flowData.getOrderTree() != null)
			this.workCustomCfgManager.saveLogBatch("insert", flowData.getRun_logs(), null);
		
		return msg;
	}
	
	/**
	 * 更新锁定信息
	 * @param flowData
	 * @param ins
	 * @throws Exception
	 */
	private void updateLockInfo(WORK_CUSTOM_FLOW_DATA flowData,ES_WORK_CUSTOM_NODE_INS ins) throws Exception{
		if(flowData.getOrderTree() != null){
			this.updateOrderLockInfo(flowData, ins);
		}else{
			this.updateOrderIntentLockInfo(flowData, ins);
		}
	}
	
	/**
	 * 更新正式订单锁定信息
	 * @param flowData
	 * @param ins
	 * @throws Exception
	 */
	private void updateOrderLockInfo(WORK_CUSTOM_FLOW_DATA flowData,ES_WORK_CUSTOM_NODE_INS ins) throws Exception{
		//获取当前环节处理人配置
		ES_WORK_CUSTOM_DEALER deal = null;
		String node_code = ins.getOld_node_code();
		if(StringUtils.isBlank(node_code))
			node_code = "CW";
		
		if(flowData.getDealMap().containsKey(ins.getNode_id())){
			List<ES_WORK_CUSTOM_DEALER> dealers = flowData.getDealMap().get(ins.getNode_id());
			
			deal = this.getDealerCfg(flowData, dealers);
		}

		if(deal == null && flowData.getOrderTree()!=null 
				&& CommonDataFactory.getInstance().
				getOrderLockBusiRequest(flowData.getOrderTree(),node_code)!=null){
			
			OrderLockBusiRequest order_lock = CommonDataFactory.getInstance().
					getOrderLockBusiRequest(flowData.getOrderTree(),node_code);
			
			//如果是正式订单，且已经有环节锁定信息的，取原有的锁定信息
			deal = new ES_WORK_CUSTOM_DEALER();
			
			deal.setDeal_id("0");
			deal.setD_version_id("0");
			deal.setDealer_type(order_lock.getDealer_type());
			deal.setDealer_code(order_lock.getLock_user_id());
			deal.setDealer_name(order_lock.getLock_user_name());
		}
			
		if(deal == null){
			deal = new ES_WORK_CUSTOM_DEALER();
			
			// 开户环节锁定工号
			OrderLockBusiRequest d_lock = CommonDataFactory.getInstance().
					getOrderLockBusiRequest(flowData.getOrderTree(),"D");
			
			if("X".equals(node_code) && d_lock != null){
				// 写卡环节，如果开户环节的锁定信息不为空，则取开户环节的锁定信息
				deal.setDeal_id("0");
				deal.setD_version_id("0");
				deal.setDealer_type(d_lock.getDealer_type());
				deal.setDealer_code(d_lock.getLock_user_id());
				deal.setDealer_name(d_lock.getLock_user_name());
				deal.setDeal_level("default");
			}else{
				// 配置的处理人为空，默认为省中台
				deal.setDeal_id("0");
				deal.setD_version_id("0");
				deal.setDealer_type("default");
				deal.setDealer_code("SZT001");
				deal.setDealer_name("省中台虚拟工号");
				deal.setDeal_level("default");
			}
		}
		
		ins.setDeal_id(SqlUtil.getStrValue(deal.getDeal_id()));
		ins.setD_version_id(SqlUtil.getStrValue(deal.getD_version_id()));
		ins.setDealer_type(SqlUtil.getStrValue(deal.getDealer_type()));
		ins.setDealer_code(SqlUtil.getStrValue(deal.getDealer_code()));
		ins.setDealer_name(SqlUtil.getStrValue(deal.getDealer_name()));
		ins.setDeal_level(SqlUtil.getStrValue(deal.getDeal_level()));
		
		if("person".equals(deal.getDealer_type())){
			//处理单位为个人的，直接锁定为当前处理人
			ins.setCurr_user_id(SqlUtil.getStrValue(deal.getDealer_code()));
			ins.setCurr_user_name(SqlUtil.getStrValue(deal.getDealer_name()));
			ins.setIs_lock_flag("1");
		}else{
			//处理单位为团队、组织、虚拟工号的，清空当前处理人
			ins.setCurr_user_id("");
			ins.setCurr_user_name("");
			ins.setIs_lock_flag("0");
		}
		
		//锁定正式订单
		this.lockRealOrder(flowData, deal,ins);
		
		//发送通知短信
		this.sendNoticeSms(ins.getOrder_id(), deal);
	}
	
	/**
	 * 更新意向单锁定信息
	 * @param flowData
	 * @param ins
	 * @throws Exception
	 */
	private void updateOrderIntentLockInfo(WORK_CUSTOM_FLOW_DATA flowData,ES_WORK_CUSTOM_NODE_INS ins) throws Exception{
		if(flowData.getDealMap().containsKey(ins.getNode_id())){
			List<ES_WORK_CUSTOM_DEALER> dealers = flowData.getDealMap().get(ins.getNode_id());
			
			ES_WORK_CUSTOM_DEALER deal = this.getDealerCfg(flowData, dealers);
			
			if(deal != null){
				ins.setDeal_id(SqlUtil.getStrValue(deal.getDeal_id()));
				ins.setD_version_id(SqlUtil.getStrValue(deal.getD_version_id()));
				ins.setDealer_type(SqlUtil.getStrValue(deal.getDealer_type()));
				ins.setDealer_code(SqlUtil.getStrValue(deal.getDealer_code()));
				ins.setDealer_name(SqlUtil.getStrValue(deal.getDealer_name()));
				ins.setDeal_level(SqlUtil.getStrValue(deal.getDeal_level()));
				
				if("person".equals(deal.getDealer_type())){
					ins.setCurr_user_id(SqlUtil.getStrValue(deal.getDealer_code()));
					ins.setCurr_user_name(SqlUtil.getStrValue(deal.getDealer_name()));
					ins.setIs_lock_flag("1");
					
					//处理单位为个人的，直接锁定为当前处理人
					this.lockOrderIntent(flowData, deal);
				}else{
					//处理单位为团队、组织、虚拟工号的，清空当前处理人
					ins.setCurr_user_id("");
					ins.setCurr_user_name("");
					ins.setIs_lock_flag("0");
					
					this.unLockOrderIntent(flowData);
				}
				
				//发送通知短信
				this.sendNoticeSms(ins.getOrder_id(), deal);
			}else{
				//意向单类型的流程，没有处理人配置，设置为意向单省中台虚拟工号
				ins.setDeal_id("0");
				ins.setD_version_id("0");
				ins.setDealer_type("default");
				ins.setDealer_code("YXDSZT001");
				ins.setDealer_name("意向单省中台虚拟工号");
				ins.setDeal_level("0");
				
				this.unLockOrderIntent(flowData);
			}
		}
	}
	
	/**
	 * 锁定正式订单
	 * @param flowData
	 * @param deal
	 * @throws Exception
	 */
	private void lockRealOrder(WORK_CUSTOM_FLOW_DATA flowData,ES_WORK_CUSTOM_DEALER deal
			,ES_WORK_CUSTOM_NODE_INS ins) throws Exception{
		String lock_user_id = deal.getDealer_code();
		String lock_user_name = deal.getDealer_name();
		String dealer_type = deal.getDealer_type();
		String node_code = ins.getOld_node_code();
		if(StringUtils.isBlank(node_code))
			node_code = "CW";
		
		OrderLockBusiRequest orderLockBusiRequest = CommonDataFactory.getInstance().
				getOrderLockBusiRequest(flowData.getOrderTree(),node_code);
		
		if(orderLockBusiRequest==null){//根据当前环节编码找不到锁定记录，则认为是没有锁定，此时要可以锁定订单
			orderLockBusiRequest = new OrderLockBusiRequest();
			
			orderLockBusiRequest.setLock_id(this.workCustomCfgManager.getSequence("o_outcall_log"));
			orderLockBusiRequest.setOrder_id(flowData.getOrderTree().getOrder_id());
			orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
			
			flowData.getOrderTree().getOrderLockBusiRequests().add(orderLockBusiRequest);
		}else{
			orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		}

		orderLockBusiRequest.setTache_code(node_code);	//NODE_CODE设置为锁定环节编码
		orderLockBusiRequest.setLock_user_id(lock_user_id);
		orderLockBusiRequest.setLock_user_name(lock_user_name);
		orderLockBusiRequest.setLock_status(EcsOrderConsts.LOCK_STATUS);
		orderLockBusiRequest.setDealer_type(dealer_type);
		
		try {
			orderLockBusiRequest.setLock_time(DateUtil.getTime2());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		orderLockBusiRequest.setSource_from(ManagerUtils.getSourceFrom());
		orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderLockBusiRequest.store();
		
		OrderExtBusiRequest orderExtBusiRequest = flowData.getOrderTree().getOrderExtBusiRequest();
		
		orderExtBusiRequest.setFlow_status(Const.ORDER_FLOW_STATUS_0);
		orderExtBusiRequest.setFlow_trace_id(node_code);
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		//修改订单流程状态
		orderExtBusiRequest.store();
	}
	
	/**
	 * 后台处理环节新增ES_ORDER_LOCK表记录
	 * @param flowData
	 * @param ins
	 * @throws Exception
	 */
	private void newEsOrderLock4BackNode(WORK_CUSTOM_FLOW_DATA flowData
			,ES_WORK_CUSTOM_NODE_INS ins) throws Exception{
		if(flowData.getOrderTree() == null)
			return;
		
		String curr_node_code = ins.getOld_node_code();
		
		if(StringUtils.isBlank(curr_node_code))
			return;
		
		String pre_node_code = flowData.getOrderTree().getOrderExtBusiRequest().getFlow_trace_id();
		
		OrderLockBusiRequest pre_lock = null;
		
		if(StringUtils.isNotBlank(pre_node_code)){
			pre_lock = CommonDataFactory.getInstance().getOrderLockBusiRequest(flowData.getOrderTree(),pre_node_code);
		}

		OrderLockBusiRequest curr_lock = CommonDataFactory.getInstance().
				getOrderLockBusiRequest(flowData.getOrderTree(),curr_node_code);
		
		//已有锁定信息，无需更新
		if(curr_lock != null)
			return;
		
		String lock_user_id = "";
		String lock_user_name = "";
		String dealer_type = "";
		
		if(pre_lock != null){
			//从前一个环节取锁定人信息
			lock_user_id = pre_lock.getLock_user_id();
			lock_user_name = pre_lock.getLock_user_name();
			dealer_type = pre_lock.getDealer_type();
		}
		
		curr_lock = new OrderLockBusiRequest();
		
		curr_lock.setLock_id(this.workCustomCfgManager.getSequence("o_outcall_log"));
		curr_lock.setOrder_id(flowData.getOrderTree().getOrder_id());
		curr_lock.setTache_code(curr_node_code);	//NODE_CODE设置为锁定环节编码
		curr_lock.setLock_user_id(lock_user_id);
		curr_lock.setLock_user_name(lock_user_name);
		curr_lock.setLock_status(EcsOrderConsts.LOCK_STATUS);
		
		curr_lock.setDealer_type(dealer_type);
		
		try {
			curr_lock.setLock_time(DateUtil.getTime2());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		curr_lock.setSource_from(ManagerUtils.getSourceFrom());
		curr_lock.setDb_action(ConstsCore.DB_ACTION_INSERT);
		curr_lock.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		curr_lock.store();
		
		flowData.getOrderTree().getOrderLockBusiRequests().add(curr_lock);
		
		OrderExtBusiRequest orderExtBusiRequest = flowData.getOrderTree().getOrderExtBusiRequest();
		
		orderExtBusiRequest.setFlow_status(Const.ORDER_FLOW_STATUS_0);
		orderExtBusiRequest.setFlow_trace_id(curr_node_code);
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		
		//修改订单流程状态
		orderExtBusiRequest.store();
	}
	
	/**
	 * 设置环节为未处理
	 * @param node_id 环节办好
	 * @param jumpMsg 跳转信息
	 * @param jumpMark 跳转标识
	 * @param flowData 流程数据
	 * @throws Exception
	 */
	private void SetNodeUnDone(String node_id,String jumpMsg,String jumpMark,WORK_CUSTOM_FLOW_DATA flowData) throws Exception{
		String msg = StringUtil.getSubStringByByte(jumpMsg, 0, 256);
		
		if(flowData.getInsMap()==null)
			return;
		
		if(!flowData.getInsMap().containsKey(node_id))
			return;
		
		//获取当前环节实例
		ES_WORK_CUSTOM_NODE_INS ins = flowData.getInsMap().get(node_id);
		
		//设置跳转标志
		ins.setExt_2(jumpMark);
		
		//判断是否需要修改流程环节实例
		if("1".equals(ins.getIs_done())){
			//修改环节为未执行
			ins.setIs_done("0");
			ins.setRemark(msg);
			ins.setState_code("init");
			ins.setState_name("初始化");
			
			//从序列中取执行次序，以便历史表按操作顺序排序
			String run_index = this.workCustomCfgManager.getNewInstanceId();
			ins.setExt_1(run_index);
			
			//加入修改环节列表
			flowData.getChangedNodeIds().add(node_id);
		}
		
		//判断是否修改后续环节
		//环节类型为--end，不需要修改后续环节
		if("end".equals(ins.getNode_type()))
			return;
		
		if(flowData.getConnectMap()==null)
			return;
		
		if(!flowData.getConnectMap().containsKey(node_id))
			return;
		
		List<ES_WORK_CUSTOM_CONNECT> connects = flowData.getConnectMap().get(node_id);
		
		for(int i=0;i<connects.size();i++){
			ES_WORK_CUSTOM_CONNECT connect = connects.get(i);
			
			ES_WORK_CUSTOM_NODE_INS nextIns = flowData.getInsMap().get(connect.getTarget_node_id());
			
			//已处理过的环节，不再递归处理
			if(jumpMark.equals(nextIns.getExt_2())){
				continue;
			}
			
			this.SetNodeUnDone(nextIns.getNode_id(), jumpMsg, jumpMark, flowData);
		}
	}

	@Override
	public void cancelWorkFlow(String order_id) throws Exception {
		WORK_CUSTOM_FLOW_DATA flowData = this.loadWorkFlowDataByOrderId(order_id);
		
		this.workCustomCfgManager.deleteNodeInstances(flowData);
	}

	@Override
	public void rollback(String instance_id,String remark) throws Exception {
		remark = "回退：" + remark;
		WORK_CUSTOM_FLOW_DATA flowData = this.loadWorkFlowDataByInsId(instance_id);
		
		if(flowData == null)
			throw new Exception("根据环节实例编号"+instance_id+"装载流程数据失败");
		
		String node_id = "";
		Map<String, ES_WORK_CUSTOM_NODE_INS> insMap = flowData.getInsMap();
		
		//根据传入的环节实例编号匹配环节
		Iterator<Entry<String, ES_WORK_CUSTOM_NODE_INS>> it = insMap.entrySet().iterator();
	      String node_name="";
		for(;it.hasNext();){
			Entry<String, ES_WORK_CUSTOM_NODE_INS> entry = it.next();
			
			ES_WORK_CUSTOM_NODE_INS ins = entry.getValue();
			
			if(ins.getInstance_id().equals(instance_id)){
				if(!"1".equals(ins.getIs_curr_step()))
					throw new Exception("所选环节不是流程当前环节，不能回退");
				
				node_id = ins.getNode_id();
				node_name= ins.getNode_name();
				break;
			}
		}
		
		if(StringUtils.isBlank(node_id))
			throw new Exception("未根据环节实例编号"+instance_id+"匹配到环节编号");
		
		Map<String, List<ES_WORK_CUSTOM_CONNECT>> connectMap = flowData.getConnectMap();
		
		String rollback_instance_id = "";
		String order_id="";
		Iterator<Entry<String, List<ES_WORK_CUSTOM_CONNECT>>> connectIt = connectMap.entrySet().iterator();
		boolean getIsDoneNode = false;
		
		for(;connectIt.hasNext();){
			Entry<String, List<ES_WORK_CUSTOM_CONNECT>> entry = connectIt.next();
			
			List<ES_WORK_CUSTOM_CONNECT> tempList = entry.getValue();
			
			for(int i=0;i<tempList.size();i++){
				if(node_id.equals(tempList.get(i).getTarget_node_id())){
					String source_node_id = tempList.get(i).getSource_node_id();
					
					ES_WORK_CUSTOM_NODE_INS pre_node = insMap.get(source_node_id);
					
					if(pre_node != null){
						rollback_instance_id = pre_node.getInstance_id();
						order_id = pre_node.getOrder_id();
						
						//上一环节已执行，跳出循环
						if("1".equals(pre_node.getIs_done())){
							getIsDoneNode = true;
						}
						
						break;
					}
				}
			}
			
			if(getIsDoneNode)
				break;
		}
		
		if(StringUtils.isNotBlank(rollback_instance_id)){//gotoNode方法添加返回值  用于插入日志  sgf
			String remarkInfo = this.gotoNode(rollback_instance_id,remark);
			
			this.insertIntentHanleLogs(flowData,order_id, remarkInfo, node_name);
		}else{
			throw new Exception("未找到上一环节");
		}
	}

	@Override
	public String checkCurrNodes(String order_id, String node_name,
			String cfg_code) throws Exception {
		ES_WORK_CUSTOM_NODE_INS pojo = new ES_WORK_CUSTOM_NODE_INS();
		pojo.setOrder_id(order_id);
		pojo.setIs_curr_step("1");
		
		List<ES_WORK_CUSTOM_NODE_INS> curr_nodes = this.workCustomCfgManager.qryInsList(pojo , null);
		
		if(curr_nodes==null || curr_nodes.size()==0)
			throw new Exception("根据订单编号未找到当前环节");
		
		String deal_bean = curr_nodes.get(0).getDeal_bean();
		
		//根据处理方法判断在配置的环节
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String cfg = cacheUtil.getConfigInfo(cfg_code);
		
		if(org.apache.commons.lang.StringUtils.isBlank(cfg))
			throw new Exception("未找到"+node_name+"环节处理方法配置信息");
		
		String[] beans = cfg.split(",");
		List<String> beans_list = Arrays.asList(beans);
		Set<String> beans_set = new HashSet<String>(beans_list);
		
		if(beans_set.contains(deal_bean)){
			return curr_nodes.get(0).getInstance_id();
		}else{
			throw new Exception(order_id+"订单不在"+node_name+"环节");
		}
	}

	@Override
	public String gotoNode(String order_id, String node_code, String remark)
			throws Exception {
		if(StringUtils.isBlank(order_id)){
			throw new Exception("传入订单编号为空！");
		}
		
		if(StringUtils.isBlank(node_code)){
			throw new Exception("传入流程编码为空！");
		}
		
		String instance_id = this.getInstanceIdByNodeCode(order_id, node_code);
		
		return this.gotoNode(instance_id, remark);
	}
}
