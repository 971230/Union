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
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import zte.net.ecsord.params.nd.vo.ES_DC_PUBLIC_DICT_RELATION;
import zte.net.ecsord.params.nd.vo.Organization;
import zte.net.ecsord.params.nd.vo.User;
import zte.net.ecsord.params.workCustom.po.ES_ORDER_INTENT;
import zte.net.ecsord.params.workCustom.po.ES_ORDER_LOCK;
import zte.net.ecsord.params.workCustom.po.ES_USER_TEAM;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_CFG;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_CONNECT;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_DEALER;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_LOG;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_NODE;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_NODE_INS;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_WORKFLOW_INS;
import zte.net.ecsord.params.workCustom.po.ES_WORK_ORDER;
import zte.net.ecsord.params.workCustom.po.WORK_CUSTOM_FLOW_DATA;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.CrmConstants;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.database.SqlBuilderInterface;
import com.ztesoft.net.mall.core.service.impl.RequestStoreManager;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomCfgManager;
import com.ztesoft.net.sqls.SqlBuilder;
import com.ztesoft.net.sqls.SqlUtil;
import com.ztesoft.userAndOrg.dao.User_And_Org_DAO;
import com.ztesoft.workCustom.dao.ES_ORDER_INTENT_DAO;
import com.ztesoft.workCustom.dao.ES_ORDER_LOCK_DAO;
import com.ztesoft.workCustom.dao.ES_USER_TEAM_DAO;
import com.ztesoft.workCustom.dao.ES_WORK_CUSTOM_CFG_DAO;
import com.ztesoft.workCustom.dao.ES_WORK_CUSTOM_CONNECT_DAO;
import com.ztesoft.workCustom.dao.ES_WORK_CUSTOM_DEALER_DAO;
import com.ztesoft.workCustom.dao.ES_WORK_CUSTOM_LOG_DAO;
import com.ztesoft.workCustom.dao.ES_WORK_CUSTOM_NODE_DAO;
import com.ztesoft.workCustom.dao.ES_WORK_CUSTOM_NODE_INS_DAO;
import com.ztesoft.workCustom.dao.ES_WORK_CUSTOM_WORKFLOW_INS_DAO;
import com.ztesoft.workCustom.dao.ES_WORK_ORDER_DAO;

@Transactional
public class WorkCustomCfgManager implements IWorkCustomCfgManager {
	@SuppressWarnings("unused")
	private Logger log = Logger.getLogger(this.getClass());
	
	private ES_WORK_CUSTOM_CFG_DAO cfgDao;
	private ES_WORK_CUSTOM_NODE_DAO nodeDao;
	private ES_WORK_CUSTOM_CONNECT_DAO connectDao;
	private ES_WORK_CUSTOM_DEALER_DAO dealerDao;
	private ES_WORK_CUSTOM_NODE_INS_DAO insDao;
	private ES_WORK_CUSTOM_WORKFLOW_INS_DAO flowDao;
	private ES_ORDER_INTENT_DAO intentDao;
	private User_And_Org_DAO userOrgDao;
	private ES_ORDER_LOCK_DAO lockDao;
	private ES_WORK_ORDER_DAO workDao;
	private ES_USER_TEAM_DAO teamDao;
	private ES_WORK_CUSTOM_LOG_DAO logDao;
	
	private ES_WORK_CUSTOM_CFG_DAO getCfgDao() throws Exception {
		if(this.cfgDao == null)
			this.cfgDao = SpringContextHolder.getBean("ES_WORK_CUSTOM_CFG_DAO");
		
		return this.cfgDao;
	}
	
	private ES_WORK_CUSTOM_NODE_DAO getNodeDao() throws Exception {
		if(this.nodeDao == null)
			this.nodeDao = SpringContextHolder.getBean("ES_WORK_CUSTOM_NODE_DAO");
		
		return this.nodeDao;
	}
	
	private ES_WORK_CUSTOM_CONNECT_DAO getConnectDao() throws Exception {
		if(this.connectDao == null)
			this.connectDao = SpringContextHolder.getBean("ES_WORK_CUSTOM_CONNECT_DAO");
		
		return this.connectDao;
	}
	
	private ES_WORK_CUSTOM_NODE_INS_DAO getInsDao() throws Exception {
		if(this.insDao == null)
			this.insDao = SpringContextHolder.getBean("ES_WORK_CUSTOM_NODE_INS_DAO");
		
		return this.insDao;
	}
	
	private ES_WORK_CUSTOM_DEALER_DAO getDealerDao() throws Exception {
		if(this.dealerDao == null)
			this.dealerDao = SpringContextHolder.getBean("ES_WORK_CUSTOM_DEALER_DAO");
		
		return this.dealerDao;
	}
	
	private ES_WORK_CUSTOM_WORKFLOW_INS_DAO getFlowDao() throws Exception {
		if(this.flowDao == null)
			this.flowDao = SpringContextHolder.getBean("ES_WORK_CUSTOM_WORKFLOW_INS_DAO");
		
		return this.flowDao;
	}
	
	private ES_ORDER_INTENT_DAO getIntentDao() throws Exception {
		if(this.intentDao == null)
			this.intentDao = SpringContextHolder.getBean("ES_ORDER_INTENT_DAO");
		
		return this.intentDao;
	}
	
	private User_And_Org_DAO getUserOrgDao() throws Exception {
		if(this.userOrgDao == null)
			this.userOrgDao = SpringContextHolder.getBean("User_And_Org_DAO");
		
		return this.userOrgDao;
	}
	
	private ES_USER_TEAM_DAO getTeamDao() throws Exception {
		if(this.teamDao == null)
			this.teamDao = SpringContextHolder.getBean("ES_USER_TEAM_DAO");
		
		return this.teamDao;
	}
	
	private ES_ORDER_LOCK_DAO getLockDao() throws Exception {
		if(this.lockDao == null)
			this.lockDao = SpringContextHolder.getBean("ES_ORDER_LOCK_DAO");
		
		return this.lockDao;
	}
	
	private ES_WORK_ORDER_DAO getWorkDao() throws Exception{
		if(this.workDao == null)
			this.workDao = SpringContextHolder.getBean("ES_WORK_ORDER_DAO");
		
		return this.workDao;
	}
	
	private ES_WORK_CUSTOM_LOG_DAO getLogDao() throws Exception {
		if(this.logDao == null)
			this.logDao = SpringContextHolder.getBean("ES_WORK_CUSTOM_LOG_DAO");
		
		return this.logDao;
	}
	
	public String getSequence(String seq_name) throws Exception{
		return this.getCfgDao().getSequence(seq_name);
	}

	@Override
	public String getNewCfgId() throws Exception {
		return this.getCfgDao().getNewCfgId();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<String> getNewCfgIdBatch(int size) throws Exception {
		List<String> ids = new ArrayList<String>();
		
		String sql = "select Seq_Es_Work_Cfg.Nextval cfg_id from dual connect by rownum <=" + size;
		List<Map> retList = this.getCfgDao().qryForMapList(sql );
		
		for(int i=0;i<retList.size();i++){
			Map m = retList.get(i);
			
			String id = String.valueOf(m.get("cfg_id"));
			
			ids.add(id);
		}
		
		return ids;
	}

	@Override
	public String getNewVersionId() throws Exception {
		return this.getCfgDao().getNewVersionId();
	}
	
	@Override
	public String addCfg(ES_WORK_CUSTOM_CFG cfg,
			List<ES_WORK_CUSTOM_NODE> nodes,
			List<ES_WORK_CUSTOM_CONNECT> connects) throws Exception {
		//输入校验
		if(cfg==null)
			throw new Exception("传入配置为空");
		
		if(StringUtils.isBlank(cfg.getCfg_level()))
			throw new Exception("传入配置级别为空");
		
		if(StringUtils.isBlank(cfg.getMatch_type()))
			throw new Exception("传入匹配方式为空");
		
		if(StringUtils.isBlank(cfg.getGoods_type_id()) && 
				StringUtils.isBlank(cfg.getGoods_cat_id()) &&
				StringUtils.isBlank(cfg.getGoods_id()))
			throw new Exception("商品大类，商品小类，商品编号不能全为空");
		
		if(nodes==null || nodes.size()==0)
			throw new Exception("传入节点为空");
		
		if(connects==null || connects.size()==0)
			throw new Exception("传入连接为空");
		
		if ("1".equals(cfg.getMatch_type())){
			// 按商品方式匹配，校验相同配置条件的配置是否已存在
			ES_WORK_CUSTOM_CFG param = new ES_WORK_CUSTOM_CFG();
			param.setCfg_level(cfg.getCfg_level());
			param.setGoods_type_id(cfg.getGoods_type_id());
			param.setGoods_cat_id(cfg.getGoods_cat_id());
			param.setGoods_id(cfg.getGoods_id());
			param.setOrder_from(cfg.getOrder_from());
			param.setCfg_type(cfg.getCfg_type());
			param.setMatch_type("1");
			
			List<ES_WORK_CUSTOM_CFG> ret = this.getCfgDao().qryPojoList(param, null);
			
			if(ret!=null && ret.size()>0)
				throw new Exception("相同匹配条件的流程已存在，流程编号："+ret.get(0).getCfg_id());
		}else if ("2".equals(cfg.getMatch_type())){
			if(StringUtils.isBlank(cfg.getFlow_code()))
				throw new Exception("传入流程编码为空");
			
			// 按流程编码匹配，校验相同流程编码的配置是否已存在
			ES_WORK_CUSTOM_CFG param = new ES_WORK_CUSTOM_CFG();
			param.setFlow_code(cfg.getFlow_code());
			param.setMatch_type("2");
			
			List<ES_WORK_CUSTOM_CFG> ret = this.getCfgDao().qryPojoList(param, null);
			
			if(ret!=null && ret.size()>0)
				throw new Exception("相同流程编码的流程已存在，流程编号："+ret.get(0).getCfg_id());
		}else{
			throw new Exception("非法的匹配方式枚举值："+cfg.getMatch_type());
		}
		
		//获取配置编号
		String cfgId = this.getNewCfgId();
		
		//获取版本编号
		String versionId = this.getNewVersionId();
		
		AdminUser user = ManagerUtils.getAdminUser();
		String opId = user.getUserid();
		String orgId = user.getOrg_id();
		String opName = user.getRealname();
		String orgName = user.getDep_name();
		
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		String date = format.format(now);
		
		//流程配置
		cfg.setCfg_id(cfgId);
		cfg.setVersion_id(versionId);
		cfg.setCreate_date(date);
		cfg.setUpdate_date(date);
		cfg.setOp_id(opId);
		cfg.setOrg_id(orgId);
		cfg.setOp_name(opName);
		cfg.setOrg_name(orgName);
		cfg.setSource_from("ECS");
		
		//流程节点信息
		Map<String,ES_WORK_CUSTOM_NODE> indexNodeMap = new HashMap<String, ES_WORK_CUSTOM_NODE>();
		
		List<String> nodeNewIds = this.getNewCfgIdBatch(nodes.size());
		
		for(int i=0;i<nodes.size();i++){
			String node_id = nodeNewIds.get(i);
			
			nodes.get(i).setNode_id(node_id);
			nodes.get(i).setCfg_id(cfgId);
			nodes.get(i).setVersion_id(versionId);
			
			nodes.get(i).setCreate_date(date);
			nodes.get(i).setOp_id(opId);
			nodes.get(i).setOrg_id(orgId);
			nodes.get(i).setOp_name(opName);
			nodes.get(i).setOrg_name(orgName);
			nodes.get(i).setState("1");
			nodes.get(i).setSource_from("ECS");
			
			indexNodeMap.put(nodes.get(i).getNode_index(), nodes.get(i));
		}
		
		//流程连接信息
		List<String> connectNewIds = this.getNewCfgIdBatch(connects.size());
		for(int i=0;i<connects.size();i++){
			String connect_id = connectNewIds.get(i);
			
			String s_index = connects.get(i).getSource_node_index();
			String t_index = connects.get(i).getTarget_node_index();
			
			if(indexNodeMap.containsKey(s_index)){
				connects.get(i).setSource_node_id(indexNodeMap.get(s_index).getNode_id());
			}else{
				throw new Exception("连接的源节点不在传入的节点列表中");
			}
			
			if(indexNodeMap.containsKey(t_index)){
				connects.get(i).setTarget_node_id(indexNodeMap.get(t_index).getNode_id());
			}else{
				throw new Exception("连接的目标节点不在传入的节点列表中");
			}
			
			connects.get(i).setConnect_id(connect_id);
			connects.get(i).setCfg_id(cfgId);
			connects.get(i).setVersion_id(versionId);
			
			connects.get(i).setCreate_date(date);
			connects.get(i).setOp_id(opId);
			connects.get(i).setOrg_id(orgId);
			connects.get(i).setOp_name(opName);
			connects.get(i).setOrg_name(orgName);
			connects.get(i).setState("1");
			connects.get(i).setSource_from("ECS");
		}
		
		this.getCfgDao().save("insert", cfg, null);
		this.getNodeDao().saveBatch("insert", nodes, null);
		this.getConnectDao().saveBatch("insert", connects, null);
		
		return cfgId;
	}

	@Override
	public void updateCfg(ES_WORK_CUSTOM_CFG cfg,
			List<ES_WORK_CUSTOM_NODE> nodes,
			List<ES_WORK_CUSTOM_CONNECT> connects) throws Exception {
		//输入校验
		if(cfg==null)
			throw new Exception("传入配置为空");
		
		if(StringUtils.isBlank(cfg.getCfg_id()))
			throw new Exception("传入流程编号为空");
		
		if(StringUtils.isBlank(cfg.getCfg_level()))
			throw new Exception("传入配置级别为空");
		
		if(StringUtils.isBlank(cfg.getMatch_type()))
			throw new Exception("传入匹配方式为空");
		
		if(StringUtils.isBlank(cfg.getGoods_type_id()) && 
				StringUtils.isBlank(cfg.getGoods_cat_id()) &&
				StringUtils.isBlank(cfg.getGoods_id()))
			throw new Exception("商品大类，商品小类，商品编号不能全为空");
		
		if(nodes==null || nodes.size()==0)
			throw new Exception("传入节点为空");
		
		if(connects==null || connects.size()==0)
			throw new Exception("传入连接为空");
		
		if ("1".equals(cfg.getMatch_type())){
			// 按商品匹配，校验相同配置条件的配置是否已存在
			ES_WORK_CUSTOM_CFG param = new ES_WORK_CUSTOM_CFG();
			param.setCfg_level(cfg.getCfg_level());
			param.setGoods_type_id(cfg.getGoods_type_id());
			param.setGoods_cat_id(cfg.getGoods_cat_id());
			param.setGoods_id(cfg.getGoods_id());
			param.setOrder_from(cfg.getOrder_from());
			param.setCfg_type(cfg.getCfg_type());
			param.setMatch_type("1");
			
			List<SqlBuilderInterface> sqlBuilds = new ArrayList<SqlBuilderInterface>();
			SqlBuilder cfgId = new SqlBuilder("cfg_id"," <> "+cfg.getCfg_id()+" ");
			sqlBuilds.add(cfgId);
			
			List<ES_WORK_CUSTOM_CFG> ret = this.getCfgDao().qryPojoList(param, sqlBuilds);
			
			if(ret!=null && ret.size()>0)
				throw new Exception("相同匹配条件的流程已存在，流程编号："+ret.get(0).getCfg_id());
		}else if ("2".equals(cfg.getMatch_type())){
			if(StringUtils.isBlank(cfg.getFlow_code()))
				throw new Exception("传入流程编码为空");
			
			// 按流程编码匹配，校验相同流程编码的配置是否已存在
			ES_WORK_CUSTOM_CFG param = new ES_WORK_CUSTOM_CFG();
			param.setFlow_code(cfg.getFlow_code());
			param.setMatch_type("2");
			
			List<SqlBuilderInterface> sqlBuilds = new ArrayList<SqlBuilderInterface>();
			SqlBuilder cfgId = new SqlBuilder("cfg_id"," <> "+cfg.getCfg_id()+" ");
			sqlBuilds.add(cfgId);
			
			List<ES_WORK_CUSTOM_CFG> ret = this.getCfgDao().qryPojoList(param, sqlBuilds);
			
			if(ret!=null && ret.size()>0)
				throw new Exception("相同流程编码的流程已存在，流程编号："+ret.get(0).getCfg_id());
		}else{
			throw new Exception("非法的匹配方式枚举值："+cfg.getMatch_type());
		}

		//当前版本移历史表
		String cfg_id = cfg.getCfg_id();
		this.moveCfg2His(cfg_id);
		
		//获取新版本编号
		String versionId = this.getNewVersionId();
		
		AdminUser user = ManagerUtils.getAdminUser();
		String opId = user.getUserid();
		String orgId = user.getOrg_id();
		String opName = user.getRealname();
		String orgName = user.getDep_name();
		
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		String date = format.format(now);
		
		String state = "1";
		if(StringUtils.isNotBlank(cfg.getState()))
			state = cfg.getState();
		
		//流程配置
		cfg.setVersion_id(versionId);
		cfg.setCreate_date(date);
		cfg.setOp_id(opId);
		cfg.setOrg_id(orgId);
		cfg.setOp_name(opName);
		cfg.setOrg_name(orgName);
		cfg.setState(state);
		cfg.setSource_from("ECS");
		
		//流程节点信息
		Map<String,ES_WORK_CUSTOM_NODE> indexNodeMap = new HashMap<String, ES_WORK_CUSTOM_NODE>();
		
		int count = 0;
		for(int i=0;i<nodes.size();i++){
			if(StringUtils.isBlank(nodes.get(i).getNode_id())){
				count++;
			}
		}
		
		List<String> nodeNewIds = this.getNewCfgIdBatch(count);
		Iterator<String> nodeIdIt = nodeNewIds.iterator();
		
		for(int i=0;i<nodes.size();i++){
			
			if(StringUtils.isBlank(nodes.get(i).getNode_id())){
				String node_id = nodeIdIt.next();
				
				nodes.get(i).setNode_id(node_id);
			}
			
			nodes.get(i).setCfg_id(cfg_id);
			nodes.get(i).setVersion_id(versionId);
			
			nodes.get(i).setCreate_date(date);
			nodes.get(i).setOp_id(opId);
			nodes.get(i).setOrg_id(orgId);
			nodes.get(i).setOp_name(opName);
			nodes.get(i).setOrg_name(orgName);
			nodes.get(i).setState(state);
			nodes.get(i).setSource_from("ECS");
			
			indexNodeMap.put(nodes.get(i).getNode_index(), nodes.get(i));
		}
		
		//流程连接信息
		List<String> connectNewIds = this.getNewCfgIdBatch(connects.size());
		for(int i=0;i<connects.size();i++){
			String connect_id = connectNewIds.get(i);
			
			String s_index = connects.get(i).getSource_node_index();
			String t_index = connects.get(i).getTarget_node_index();
			
			if(indexNodeMap.containsKey(s_index)){
				connects.get(i).setSource_node_id(indexNodeMap.get(s_index).getNode_id());
			}else{
				throw new Exception("连接的源节点不在传入的节点列表中");
			}
			
			if(indexNodeMap.containsKey(t_index)){
				connects.get(i).setTarget_node_id(indexNodeMap.get(t_index).getNode_id());
			}else{
				throw new Exception("连接的目标节点不在传入的节点列表中");
			}
			
			connects.get(i).setConnect_id(connect_id);
			connects.get(i).setCfg_id(cfg_id);
			connects.get(i).setVersion_id(versionId);
			
			connects.get(i).setCreate_date(date);
			connects.get(i).setOp_id(opId);
			connects.get(i).setOrg_id(orgId);
			connects.get(i).setOp_name(opName);
			connects.get(i).setOrg_name(orgName);
			connects.get(i).setState(state);
			connects.get(i).setSource_from("ECS");
		}
		
		this.getCfgDao().save("insert", cfg, null);
		this.getNodeDao().saveBatch("insert", nodes, null);
		this.getConnectDao().saveBatch("insert", connects, null);
	}

	@Override
	public void deleteCfg(ES_WORK_CUSTOM_CFG cfg) throws Exception {
		if(cfg==null)
			throw new Exception("传入配置为空");
		
		if(StringUtils.isBlank(cfg.getCfg_id()))
			throw new Exception("传入流程编号为空");
		
		this.moveCfg2His(cfg.getCfg_id());
		
		this.moveDeal2HisByCfgId(cfg.getCfg_id());
	}
	
	@Override
	public List<ES_WORK_CUSTOM_CFG> qryCfgList(ES_WORK_CUSTOM_CFG pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception {
		return this.getCfgDao().qryPojoList(pojo, sqlBuilds);
	}

	@Override
	public Page qryCfgPageByPojo(ES_WORK_CUSTOM_CFG pojo,
			List<SqlBuilderInterface> sqlBuilds, int pageNo, int pageSize)
			throws Exception {
		return this.getCfgDao().qryPageByPojo(pojo, sqlBuilds, pageNo, pageSize);
	}

	@Override
	public Page qryCfgHisPageByPojo(ES_WORK_CUSTOM_CFG pojo,
			List<SqlBuilderInterface> sqlBuilds, int pageNo, int pageSize)
			throws Exception {
		return this.getCfgDao().qryHisPageByPojo(pojo, sqlBuilds, pageNo, pageSize);
	}

	@Override
	public List<ES_WORK_CUSTOM_NODE> qryNodeList(ES_WORK_CUSTOM_NODE pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception {
		return this.getNodeDao().qryPojoList(pojo, sqlBuilds);
	}

	@Override
	public List<ES_WORK_CUSTOM_CONNECT> qryConnectList(
			ES_WORK_CUSTOM_CONNECT pojo, List<SqlBuilderInterface> sqlBuilds)
			throws Exception {
		return this.getConnectDao().qryPojoList(pojo, sqlBuilds);
	}

	@Override
	public void moveCfg2His(String cfg_id) throws Exception {
		if(StringUtils.isBlank(cfg_id))
			throw new Exception("传入流程编号为空");
		
		//更新状态为历史
		String cfgUpdateSql = "update es_work_custom_cfg a set a.state =2 WHERE a.cfg_id=" + cfg_id;
		String nodeUpdateSql = "update es_work_custom_node a set a.state =2 WHERE a.cfg_id=" + cfg_id;
		String connectUpdateSql = "update es_work_custom_connect a set a.state =2 WHERE a.cfg_id=" + cfg_id;
		
		this.getCfgDao().execute(cfgUpdateSql );
		this.getCfgDao().execute(nodeUpdateSql );
		this.getCfgDao().execute(connectUpdateSql );
		
		//插入历史表
		String cfgMoveSql = "insert into es_work_custom_cfg_h SELECT * FROM es_work_custom_cfg a WHERE a.cfg_id=" + cfg_id;
		String nodeMoveSql = "insert into es_work_custom_node_h SELECT * FROM es_work_custom_node a WHERE a.cfg_id=" + cfg_id;
		String connectMoveSql = "insert into es_work_custom_connect_h SELECT * FROM es_work_custom_connect a WHERE a.cfg_id=" + cfg_id;
		
		this.getCfgDao().execute(cfgMoveSql );
		this.getCfgDao().execute(nodeMoveSql );
		this.getCfgDao().execute(connectMoveSql );
		
		//从当前表中删除
		String cfgDeleteSql = "delete FROM es_work_custom_cfg a WHERE a.cfg_id=" + cfg_id;
		String nodeDeleteSql = "delete FROM es_work_custom_node a WHERE a.cfg_id=" + cfg_id;
		String connectDeleteSql = "delete FROM es_work_custom_connect a WHERE a.cfg_id=" + cfg_id;
		
		this.getCfgDao().execute(cfgDeleteSql );
		this.getCfgDao().execute(nodeDeleteSql );
		this.getCfgDao().execute(connectDeleteSql );
	}

	@Override
	public List<ES_WORK_CUSTOM_CFG> qryCfgListFromAll(ES_WORK_CUSTOM_CFG pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception {
		return this.getCfgDao().qryPojoListFromAll(pojo, sqlBuilds);
	}

	@Override
	public List<ES_WORK_CUSTOM_NODE> qryNodeListFromAll(
			ES_WORK_CUSTOM_NODE pojo, List<SqlBuilderInterface> sqlBuilds)
			throws Exception {
		return this.getNodeDao().qryPojoListFromAll(pojo, sqlBuilds);
	}

	@Override
	public List<ES_WORK_CUSTOM_CONNECT> qryConnectListFromAll(
			ES_WORK_CUSTOM_CONNECT pojo, List<SqlBuilderInterface> sqlBuilds)
			throws Exception {
		return this.getConnectDao().qryPojoListFromAll(pojo, sqlBuilds);
	}

	@Override
	public Page qryDealPageByPojo(ES_WORK_CUSTOM_DEALER pojo,
			List<SqlBuilderInterface> sqlBuilds, int pageNo, int pageSize)
			throws Exception {
		return this.getDealerDao().qryPageByPojo(pojo, sqlBuilds, pageNo, pageSize);
	}

	@Override
	public Page qryDealHisPageByPojo(ES_WORK_CUSTOM_DEALER pojo,
			List<SqlBuilderInterface> sqlBuilds, int pageNo, int pageSize)
			throws Exception {
		return this.getDealerDao().qryHisPageByPojo(pojo, sqlBuilds, pageNo, pageSize);
	}

	@Override
	public void addDealer(ES_WORK_CUSTOM_DEALER dealer) throws Exception {
		//字段校验
		if(dealer==null)
			throw new Exception("传入处理人配置为空");
		
		if(StringUtils.isBlank(dealer.getCfg_id()))
			throw new Exception("传入流程配置编号为空");
		
		if(StringUtils.isBlank(dealer.getNode_id()))
			throw new Exception("传入流程环节编号为空");
		
		if(StringUtils.isBlank(dealer.getNode_index()))
			throw new Exception("传入流程环节序列为空");
			
		if(StringUtils.isBlank(dealer.getDeal_level()))
			throw new Exception("传入处理单位级别为空");
		
		if(StringUtils.isBlank(dealer.getDealer_type()))
			throw new Exception("传入处理单位类型为空");
		
		if(StringUtils.isBlank(dealer.getDealer_code()))
			throw new Exception("传入处理单位编码为空");
		
		//校验是否有相同的配置
		ES_WORK_CUSTOM_DEALER pojo = new ES_WORK_CUSTOM_DEALER();
		pojo.setCfg_id(dealer.getCfg_id());
		pojo.setNode_id(dealer.getNode_id());
		pojo.setDeal_level(dealer.getDeal_level());
		pojo.setRegion_id(dealer.getRegion_id());
		pojo.setCounty_id(dealer.getCounty_id());
		
		List<ES_WORK_CUSTOM_DEALER> ret = this.getDealerDao().qryPojoList(pojo, null);
		
		if(ret!=null && ret.size()>0){
			throw new Exception("相同处理人配置已存在，处理人配置编号：" + ret.get(0).getDeal_id());
		}
		
		//获取配置编号
		String dealId = this.getNewCfgId();
		
		//获取版本编号
		String versionId = this.getNewVersionId();
		
		AdminUser user = ManagerUtils.getAdminUser();
		String opId = user.getUserid();
		String orgId = user.getOrg_id();
		String opName = user.getRealname();
		String orgName = user.getDep_name();
		
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		String date = format.format(now);
		
		//设置通用字段
		dealer.setDeal_id(dealId);
		dealer.setD_version_id(versionId);;
		dealer.setCreate_date(date);
		dealer.setOp_id(opId);
		dealer.setOrg_id(orgId);
		dealer.setOp_name(opName);
		dealer.setOrg_name(orgName);
		dealer.setState("1");
		dealer.setSource_from("ECS");
		
		this.getDealerDao().save("insert", dealer, null);
	}

	@Override
	public void updateDealer(ES_WORK_CUSTOM_DEALER dealer) throws Exception {
		//字段校验
		if(dealer==null)
			throw new Exception("传入处理人配置为空");
		
		if(StringUtils.isBlank(dealer.getDeal_id()))
			throw new Exception("传入处理人配置编号为空");
		
		if(StringUtils.isBlank(dealer.getCfg_id()))
			throw new Exception("传入流程配置编号为空");
		
		if(StringUtils.isBlank(dealer.getNode_id()))
			throw new Exception("传入流程环节编号为空");
		
		if(StringUtils.isBlank(dealer.getNode_index()))
			throw new Exception("传入流程环节序列为空");
			
		if(StringUtils.isBlank(dealer.getDeal_level()))
			throw new Exception("传入处理单位级别为空");
		
		if(StringUtils.isBlank(dealer.getDealer_type()))
			throw new Exception("传入处理单位类型为空");
		
		if(StringUtils.isBlank(dealer.getDealer_code()))
			throw new Exception("传入处理单位编码为空");
		
		//校验是否有相同的配置
		ES_WORK_CUSTOM_DEALER pojo = new ES_WORK_CUSTOM_DEALER();
		pojo.setCfg_id(dealer.getCfg_id());
		pojo.setNode_id(dealer.getNode_id());
		pojo.setDeal_level(dealer.getDeal_level());
		pojo.setRegion_id(dealer.getRegion_id());
		pojo.setCounty_id(dealer.getCounty_id());
		
		List<SqlBuilderInterface> sqlBuilds = new ArrayList<SqlBuilderInterface>();
		SqlBuilder dealId = new SqlBuilder("deal_id"," <>"+dealer.getDeal_id()+" ");
		sqlBuilds.add(dealId);
		
		List<ES_WORK_CUSTOM_DEALER> ret = this.getDealerDao().qryPojoList(pojo, sqlBuilds);
		
		if(ret!=null && ret.size()>0){
			throw new Exception("相同处理人配置已存在，处理人配置编号：" + ret.get(0).getDeal_id());
		}
		
		//当前处理人配置移历史表
		this.moveDeal2His(dealer.getDeal_id());
		
		//获取版本编号
		String versionId = this.getNewVersionId();
		
		AdminUser user = ManagerUtils.getAdminUser();
		String opId = user.getUserid();
		String orgId = user.getOrg_id();
		String opName = user.getRealname();
		String orgName = user.getDep_name();
		
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		String date = format.format(now);
		
		//设置通用字段
		dealer.setD_version_id(versionId);;
		dealer.setCreate_date(date);
		dealer.setOp_id(opId);
		dealer.setOrg_id(orgId);
		dealer.setOp_name(opName);
		dealer.setOrg_name(orgName);
		dealer.setState("1");
		dealer.setSource_from("ECS");
		
		this.getDealerDao().save("update", dealer, new String[]{"deal_id"});
	}

	@Override
	public void deleteDealer(ES_WORK_CUSTOM_DEALER dealer) throws Exception {
		//字段校验
		if(dealer==null)
			throw new Exception("传入处理人配置为空");
		
		if(StringUtils.isBlank(dealer.getDeal_id()))
			throw new Exception("传入处理人配置编号为空");
		
		//当前处理人配置移历史表
		this.moveDeal2His(dealer.getDeal_id());
		
		//删除
		this.getDealerDao().save("delete", dealer, new String[]{"deal_id"});
	}

	@Override
	public void moveDeal2His(String deal_id) throws Exception {
		if(StringUtils.isBlank(deal_id))
			throw new Exception("传入处理单位配置编号为空");
		
		//更新状态为历史
		String updateSql = "update ES_WORK_CUSTOM_DEALER a set a.state =2 WHERE a.deal_id=" + deal_id;
		
		this.getDealerDao().execute(updateSql);
		
		//插入历史表
		String moveSql = "insert into ES_WORK_CUSTOM_DEALER_H SELECT * FROM ES_WORK_CUSTOM_DEALER a WHERE a.deal_id=" + deal_id;
		
		this.getDealerDao().execute(moveSql);
	}
	
	@Override
	public void moveDeal2HisByCfgId(String cfg_id) throws Exception {
		if(StringUtils.isBlank(cfg_id))
			throw new Exception("传入流程编号为空");
		
		//更新状态为历史
		String dealerUpdateSql = "update es_work_custom_dealer a set a.state =2 WHERE a.cfg_id=" + cfg_id;
		
		this.getCfgDao().execute(dealerUpdateSql);
		
		//插入历史表
		String dealerMoveSql = "insert into es_work_custom_dealer_h SELECT * FROM es_work_custom_dealer a WHERE a.cfg_id=" + cfg_id;
		
		this.getCfgDao().execute(dealerMoveSql);
		
		//从当前表中删除
		String dealerDeleteSql = "delete FROM es_work_custom_dealer a WHERE a.cfg_id=" + cfg_id;
		
		this.getCfgDao().execute(dealerDeleteSql);
	}

	@Override
	public List<ES_WORK_CUSTOM_DEALER> qryDealList(ES_WORK_CUSTOM_DEALER pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception {
		return this.getDealerDao().qryPojoList(pojo, sqlBuilds);
	}

	@Override
	public List<ES_WORK_CUSTOM_DEALER> qryDealListFromAll(
			ES_WORK_CUSTOM_DEALER pojo, List<SqlBuilderInterface> sqlBuilds)
			throws Exception {
		return this.getDealerDao().qryPojoListFromAll(pojo, sqlBuilds);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ES_DC_PUBLIC_DICT_RELATION> getBusiCountyInfoMap()
			throws Exception {
		Map<String,ES_DC_PUBLIC_DICT_RELATION> map = new HashMap<String, ES_DC_PUBLIC_DICT_RELATION>();
		
		ES_DC_PUBLIC_DICT_RELATION param = new ES_DC_PUBLIC_DICT_RELATION();
		param.setStype("county");
		param.setSource_from("ECS");
		
		List<ES_DC_PUBLIC_DICT_RELATION> retList = this.getCfgDao().
				qryPojoList("es_dc_public_dict_relation", param, null);
		
		if(retList!=null && retList.size()>0){
			for(int i=0;i<retList.size();i++){
				String countyId = retList.get(i).getOther_field_value();
				
				map.put(countyId, retList.get(i));
			}
		}
		
		return map;
	}
	
	@Override
	public String getNewInstanceId() throws Exception {
		return  this.getInsDao().getNewInstanceId();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<String> getNewInstanceIdBatch(int size) throws Exception {
		List<String> ids = new ArrayList<String>();
		
		String sql = "select seq_es_work_instance.Nextval ins_id from dual connect by rownum <=" + size;
		List<Map> retList = this.getInsDao().qryForMapList(sql );
		
		for(int i=0;i<retList.size();i++){
			Map m = retList.get(i);
			
			String id = String.valueOf(m.get("ins_id"));
			
			ids.add(id);
		}
		
		return ids;
	}

	@Override
	public void addNodeInstances(List<ES_WORK_CUSTOM_NODE_INS> inses)
			throws Exception {
		if(inses==null || inses.size()==0)
			return;
		
		//获取编号
		List<String> ids = this.getNewInstanceIdBatch(inses.size());
		
		String opId = "";
		String orgId = "";
		String opName = "";
		String orgName = "";
		
		//操作员信息
		AdminUser user = ManagerUtils.getAdminUser();
		if(user != null){
			opId = user.getUserid();
			orgId = user.getOrg_id();
			opName = user.getRealname();
			orgName = user.getDep_name();
		}
		
		for(int i=0;i<inses.size();i++){
			inses.get(i).setInstance_id(ids.get(i));
			
			inses.get(i).setOp_id(opId);
			inses.get(i).setOrg_id(orgId);
			inses.get(i).setOp_name(opName);
			inses.get(i).setOrg_name(orgName);
			inses.get(i).setSource_from("ECS");
		}
		
		this.getInsDao().saveBatch("insert", inses, null);
	}
	
	private void initInsRemarkAndExt(ES_WORK_CUSTOM_NODE_INS ins) throws Exception{
		if(StringUtils.isBlank(ins.getRemark()))
			ins.setRemark("");
		else{
			ins.setRemark(StringUtil.getSubStringByByte(ins.getRemark(), 0, 256));
		}
		
		if(StringUtils.isBlank(ins.getExt_1()))
			ins.setExt_1("");
		
		if(StringUtils.isBlank(ins.getExt_2()))
			ins.setExt_2("");
		
		if(StringUtils.isBlank(ins.getExt_3()))
			ins.setExt_3("");
	}

	@Override
	public void updaeNodeInstances(WORK_CUSTOM_FLOW_DATA data,boolean moveHis) throws Exception {
		if(data==null)
			return;
		
		//获取执行过的环节编号
		List<String> changeNodeIds = data.getChangedNodeIds();
		
		List<ES_WORK_CUSTOM_NODE_INS> toUpdateNodes = new ArrayList<ES_WORK_CUSTOM_NODE_INS>();
		
		for(String id : changeNodeIds){
			if(data.getInsMap().containsKey(id)){
				ES_WORK_CUSTOM_NODE_INS ins = data.getInsMap().get(id);
				
				toUpdateNodes.add(ins);
			}
		}
		
		if(toUpdateNodes!=null && toUpdateNodes.size()>0){
			String opId = "";
			String orgId = "";
			String opName = "";
			String orgName = "";
			
			//操作员信息
			AdminUser user = ManagerUtils.getAdminUser();
			if(user != null){
				opId = user.getUserid();
				orgId = user.getOrg_id();
				opName = user.getRealname();
				orgName = user.getDep_name();
			}
			
			List<String> insIds = new ArrayList<String>();
			
			for(int i=0;i<toUpdateNodes.size();i++){
				insIds.add(toUpdateNodes.get(i).getInstance_id());
				
				toUpdateNodes.get(i).setOp_id(opId);
				toUpdateNodes.get(i).setOrg_id(orgId);
				toUpdateNodes.get(i).setOp_name(opName);
				toUpdateNodes.get(i).setOrg_name(orgName);
				toUpdateNodes.get(i).setSource_from("ECS");
				
				initInsRemarkAndExt(toUpdateNodes.get(i));
			}
			
			//更新环节实例当前表
			this.getInsDao().saveBatch("update", toUpdateNodes, new String[]{"instance_id"});
			
			//修改的环节实例移历史表
			if(moveHis)
				this.moveIns2his(insIds);
		}
	}
	
	public void updateNodeInstances(List<ES_WORK_CUSTOM_NODE_INS> inses) throws Exception{
		if(inses!=null && inses.size()>0){
			String opId = "";
			String orgId = "";
			String opName = "";
			String orgName = "";
			
			AdminUser user = ManagerUtils.getAdminUser();
			if(user != null){
				opId = user.getUserid();
				orgId = user.getOrg_id();
				opName = user.getRealname();
				orgName = user.getDep_name();
			}
			
			List<String> insIds = new ArrayList<String>();
			
			for(int i=0;i<inses.size();i++){
				insIds.add(inses.get(i).getInstance_id());
				
				inses.get(i).setOp_id(opId);
				inses.get(i).setOrg_id(orgId);
				inses.get(i).setOp_name(opName);
				inses.get(i).setOrg_name(orgName);
				inses.get(i).setSource_from("ECS");
				
				this.initInsRemarkAndExt(inses.get(i));
			}
			
			//更新数据
			this.getInsDao().saveBatch("update", inses, new String[]{"instance_id"});
			
			//记录操作记录
			this.moveIns2his(insIds);
		}
	}
	
	@Override
	public void deleteNodeInstances(WORK_CUSTOM_FLOW_DATA data)
			throws Exception {
		if(data.getInsMap()==null)
			return;
		
		if(data.getInsMap().size()==0)
			return;
		
		ES_WORK_CUSTOM_WORKFLOW_INS workflow = data.getWorkflow();
		List<ES_WORK_CUSTOM_NODE_INS> inses = new ArrayList<ES_WORK_CUSTOM_NODE_INS>(data.getInsMap().values());
		
		String opId = "";
		String orgId = "";
		String opName = "";
		String orgName = "";
		
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		String date = format.format(now);
		
		//操作员信息
		AdminUser user = ManagerUtils.getAdminUser();
		if(user != null){
			opId = user.getUserid();
			orgId = user.getOrg_id();
			opName = user.getRealname();
			orgName = user.getDep_name();
		}

		workflow.setRemark("删除");
		workflow.setOp_id(opId);
		workflow.setOrg_id(orgId);
		workflow.setOp_name(opName);
		workflow.setOrg_name(orgName);
		workflow.setSource_from("ECS");
		workflow.setUpdate_date(date);
		
		for(int i=0;i<inses.size();i++){
			inses.get(i).setRemark("删除");
			inses.get(i).setOp_id(opId);
			inses.get(i).setOrg_id(orgId);
			inses.get(i).setOp_name(opName);
			inses.get(i).setOrg_name(orgName);
			inses.get(i).setSource_from("ECS");
			
			initInsRemarkAndExt(inses.get(i));
		}
		
		this.getFlowDao().save("update", workflow, new String[]{"workflow_id"});
		
		this.getInsDao().saveBatch("update", inses, new String[]{"instance_id"});
		
		this.moveIns2Finish(data.getWorkflow().getWorkflow_id(),"3");
	}

	@Override
	public void moveIns2his(List<String> insIds) throws Exception {
		//插入历史表
		String inStr = SqlUtil.getSqlInStr("a.instance_id", insIds, true, true);
		
		String moveSql = "insert into es_work_custom_node_ins_h SELECT * FROM es_work_custom_node_ins a WHERE 1=1 " + inStr;
		
		this.getInsDao().execute(moveSql);
	}

	@Override
	public void moveIns2Finish(String workflow_id,String state) throws Exception {
		//流程实例移完成表
		String updateSql = "update es_work_custom_workflow_ins a set a.state ="+state+" WHERE a.workflow_id=" + workflow_id;
		this.getFlowDao().execute(updateSql);
		
		String moveSql = "insert into es_work_custom_workflow_ins_f SELECT * FROM es_work_custom_workflow_ins a WHERE a.workflow_id=" + workflow_id ;
		this.getInsDao().execute(moveSql);
		
		String deleteSql = "delete FROM es_work_custom_workflow_ins a WHERE a.workflow_id=" + workflow_id;
		this.getInsDao().execute(deleteSql);
		
		//环节实例移完成表
		updateSql = "update es_work_custom_node_ins a set a.state =2 WHERE a.workflow_id=" + workflow_id;
		this.getInsDao().execute(updateSql);
		
		moveSql = "insert into es_work_custom_node_ins_f SELECT * FROM es_work_custom_node_ins a WHERE a.workflow_id=" + workflow_id ;
		this.getInsDao().execute(moveSql);
		
		deleteSql = "delete FROM es_work_custom_node_ins a WHERE a.workflow_id=" + workflow_id;
		this.getInsDao().execute(deleteSql);
	}
	
	@Override
	public void moveFinsh2Ins(String order_id) throws Exception {
		
		//流程实例移在途表
		String moveSql = "insert into es_work_custom_workflow_ins SELECT * FROM es_work_custom_workflow_ins_f a WHERE a.order_id='" + order_id+"' " ;
		this.getInsDao().execute(moveSql);
				
		String deleteSql = "delete FROM es_work_custom_workflow_ins_f a WHERE a.order_id='" + order_id+"' ";
		this.getInsDao().execute(deleteSql);
				
		String updateSql = "update es_work_custom_workflow_ins a set a.state =1 WHERE a.order_id='" + order_id+"' ";
		this.getFlowDao().execute(updateSql);
				
		//环节实例移在途表
		moveSql = "insert into es_work_custom_node_ins SELECT * FROM es_work_custom_node_ins_f a WHERE a.order_id='" + order_id+"' " ;
		this.getInsDao().execute(moveSql);
				
		deleteSql = "delete FROM es_work_custom_node_ins_f a WHERE a.order_id='" + order_id+"' ";
		this.getInsDao().execute(deleteSql);
				
		updateSql = "update es_work_custom_node_ins a set a.state =1 WHERE a.order_id='" + order_id+"' ";
		this.getInsDao().execute(updateSql);
	}
	
	@Override
	public void deleteIns(List<String> insIds) throws Exception {
		String inStr = SqlUtil.getSqlInStr("a.instance_id", insIds, true, true);
		
		String deleteSql = "delete FROM es_work_custom_node_ins a WHERE 1=1 " + inStr;
		
		this.getInsDao().execute(deleteSql);
	}

	@Override
	public List<ES_WORK_CUSTOM_NODE_INS> qryInsList(
			ES_WORK_CUSTOM_NODE_INS pojo, List<SqlBuilderInterface> sqlBuilds)
			throws Exception {
		return this.getInsDao().qryPojoList(pojo, sqlBuilds);
	}
	
	@Override
	public List<ES_WORK_CUSTOM_NODE_INS> qryInsListFromAll(
			ES_WORK_CUSTOM_NODE_INS pojo, List<SqlBuilderInterface> sqlBuilds)
			throws Exception {
		return this.getInsDao().qryPojoListFromAll(pojo, sqlBuilds);
	}

	@Override
	public List<ES_WORK_CUSTOM_NODE_INS> qryInsListHis(
			ES_WORK_CUSTOM_NODE_INS pojo, List<SqlBuilderInterface> sqlBuilds)
			throws Exception {
		return this.getInsDao().qryPojoListFromHis(pojo, sqlBuilds);
	}

	@Override
	public void setWorkCustomFlag(WORK_CUSTOM_FLOW_DATA data) throws Exception {
		if(data == null)
			return;
		
		if(data.getOrderTree() == null && data.getOrderIntent() == null)
			return;
		
		String order_id = "";
		if(data.getOrderTree() != null){
			order_id = data.getOrderTree().getOrder_id();
			
			if(StringUtils.isBlank(order_id))
				return;
			
			//数据库更新标志位
			String sql ="update es_order_ext a set a.is_work_custom='1' where order_id='"+data.getOrderTree().getOrder_id()+"'";
			this.getCfgDao().execute(sql);
			
			//订单树对象设置标志位
			data.getOrderTree().getOrderExtBusiRequest().setIs_work_custom("1");
			
			//更新订单树缓存
			INetCache cache = (INetCache) com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
			cache.set(RequestStoreManager.NAMESPACE, 
					RequestStoreManager.DC_TREE_CACHE_NAME_KEY+"order_id"+ data.getOrderTree().getOrder_id(),
					data.getOrderTree(), RequestStoreManager.time);
		}else{
			order_id = data.getOrderIntent().getOrder_id();
			
			if(StringUtils.isBlank(order_id))
				return;
			
			ES_ORDER_INTENT order_intent = data.getOrderIntent();
			order_intent.setIs_work_custom("1");
			
			//更新意向单自定义流程标识
			this.updateOrderIntent(order_intent);
		}
	}

	@Override
	public int qryInsCountByPojo(ES_WORK_CUSTOM_NODE_INS pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception {
		return this.getInsDao().qryCountByPojo(pojo, sqlBuilds);
	}

	@Override
	public void addWorkFlowIns(ES_WORK_CUSTOM_WORKFLOW_INS workflow)
			throws Exception {

		if(workflow==null)
			return;
		
		//获取编号
		String id = this.getNewInstanceId();
		
		String opId = "";
		String orgId = "";
		String opName = "";
		String orgName = "";
		
		//操作员信息
		AdminUser user = ManagerUtils.getAdminUser();
		if(user != null){
			opId = user.getUserid();
			orgId = user.getOrg_id();
			opName = user.getRealname();
			orgName = user.getDep_name();
		}
		
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		String date = format.format(now);
		
		workflow.setWorkflow_id(id);
		workflow.setCreate_date(date);
		workflow.setUpdate_date(date);
		workflow.setOp_id(opId);
		workflow.setOrg_id(orgId);
		workflow.setOp_name(opName);
		workflow.setOrg_name(orgName);
		workflow.setSource_from("ECS");
		
		this.getFlowDao().save("insert", workflow, null);
	}

	@Override
	public List<ES_WORK_CUSTOM_WORKFLOW_INS> qryWorkflowInsList(
			ES_WORK_CUSTOM_WORKFLOW_INS pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception {
		return this.getFlowDao().qryPojoList(pojo, sqlBuilds);
	}

	@Override
	public List<ES_WORK_CUSTOM_WORKFLOW_INS> qryWorkflowInsFromAll(
			ES_WORK_CUSTOM_WORKFLOW_INS pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception {
		return this.getFlowDao().qryPojoListFromAll(pojo, sqlBuilds);
	}

	@Override
	public void updateOrderIntent(ES_ORDER_INTENT orderIntent) throws Exception {
		this.getIntentDao().save("update", orderIntent, new String[]{"order_id"});
	}
	
	@Override
	public void newOrderIntent(ES_ORDER_INTENT orderIntent) throws Exception{
		this.getIntentDao().save("insert", orderIntent, null);
	}

	@Override
	public List<ES_ORDER_INTENT> qryOrderIntentList(ES_ORDER_INTENT pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception {
		return this.getIntentDao().qryPojoList(pojo, sqlBuilds);
	}

	@Override
	public void saveNodeInsBatch(String operate,
			List<ES_WORK_CUSTOM_NODE_INS> pojoList, String[] conditionCol)
			throws Exception {
		this.getInsDao().saveBatch(operate, pojoList, conditionCol);
	}

	@Override
	public List<User> qryUserList(User pojo, List<SqlBuilderInterface> sqlBuilds)
			throws Exception {
		return this.getUserOrgDao().qryUserList(pojo, sqlBuilds);
	}

	@Override
	public List<Organization> qryOrganizationList(Organization pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception {
		return this.getUserOrgDao().qryOrganizationList(pojo, sqlBuilds);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> qryGoodsInfoByGoodsid(String goods_id) throws Exception {
		String sql = "SELECT a.type_id,a.cat_id,a.goods_id FROM es_goods a  WHERE a.goods_id='" + goods_id + "'";
		return this.getCfgDao().qryMapList(sql);
	}

	@Override
	public void insert(String table, Map<String, Object> fields, Object... args)
			throws Exception {
		this.getIntentDao().insert(table, fields, args);
	}

	@Override
	public List<ES_ORDER_LOCK> qryOrderLockList(ES_ORDER_LOCK pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception {
		return this.getLockDao().qryPojoList(pojo, sqlBuilds);
	}

	@Override
	public void saveLock(String operate, ES_ORDER_LOCK pojo, String[] conditionCol)
			throws Exception {
		this.getLockDao().save(operate, pojo, conditionCol);
	}

	@Override
	public int qryOrderLockCount(ES_ORDER_LOCK pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception {
		return this.getLockDao().qryCountByPojo(pojo, sqlBuilds);
	}

	@Override
	public void execute(String sql, Object... args) throws Exception {
		this.getCfgDao().execute(sql, args);
	}

	@Override
	public int qryWorkOrderCount(ES_WORK_ORDER pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception {		
		return this.getWorkDao().qryCountByPojo(pojo, sqlBuilds);
	}

	@Override
	public Page qryTeamPageByPojo(ES_USER_TEAM pojo,
			List<SqlBuilderInterface> sqlBuilds, int pageNo, int pageSize)
			throws Exception {
		return this.getTeamDao().qryPageByPojo(pojo, sqlBuilds, pageNo, pageSize);
	}

	@Override
	public void saveLogBatch(String operate,List<ES_WORK_CUSTOM_LOG> pojoList,
			String[] conditionCol) throws Exception {
		this.getLogDao().saveBatch(operate, pojoList, conditionCol);
	}

	@Override
	public List<ES_WORK_CUSTOM_LOG> qryPojoList(ES_WORK_CUSTOM_LOG pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception {
		return this.getLogDao().qryPojoList(pojo, sqlBuilds);
	}

	@Override
	public boolean isUpdateOrderNode(String deal_url) throws Exception {
		//根据处理类判断是否在“更新订单数据环节”
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String cfg = cacheUtil.getConfigInfo("WORKFLOW_UPDATE_ORDER_NODES");
		
		if(org.apache.commons.lang.StringUtils.isBlank(cfg))
			return false;
		
		String[] beans = cfg.split(",");
		List<String> beans_list = Arrays.asList(beans);
		Set<String> beans_set = new HashSet<String>(beans_list);
		
		if(beans_set.contains(deal_url))
			return true;
		else
			return false;
	}

	@Override
	public String copyCfg(ES_WORK_CUSTOM_CFG copy_cfg) throws Exception {
		String copyCfgId = copy_cfg.getCfg_id();
		
		if(StringUtils.isBlank(copyCfgId))
			throw new Exception("传入的复制对象编号为空");
		
		copy_cfg.setCfg_id("");
		copy_cfg.setVersion_id("");
		
		//查询复制对象流程的环节
		ES_WORK_CUSTOM_NODE nodeParam = new ES_WORK_CUSTOM_NODE();
		nodeParam.setCfg_id(copyCfgId);
		List<ES_WORK_CUSTOM_NODE> nodes = this.qryNodeList(nodeParam , null);
		
		if(nodes == null || nodes.size()==0){
			throw new Exception("复制对象的流程环节为空");
		}
		
		//查询复制对象流程的连接
		ES_WORK_CUSTOM_CONNECT connectParam = new ES_WORK_CUSTOM_CONNECT();
		connectParam.setCfg_id(copyCfgId);
		List<ES_WORK_CUSTOM_CONNECT> connects = this.qryConnectList(connectParam , null);
		
		if(connects == null || connects.size()==0){
			throw new Exception("复制对象的连接信息为空");
		}
		
		//清空编号和版本号
		for(Iterator<ES_WORK_CUSTOM_NODE> it = nodes.iterator();it.hasNext();){
			ES_WORK_CUSTOM_NODE node = it.next();
			node.setCfg_id("");
			node.setVersion_id("");
			node.setNode_id("");
		}
		
		//清空编号和版本号
		for(Iterator<ES_WORK_CUSTOM_CONNECT> it = connects.iterator();it.hasNext();){
			ES_WORK_CUSTOM_CONNECT connect = it.next();
			connect.setCfg_id("");
			connect.setVersion_id("");
			connect.setConnect_id("");
		}
		
		//新增流程
		this.addCfg(copy_cfg, nodes, connects);
		
		//复制处理人
		ES_WORK_CUSTOM_DEALER dealerParam = new ES_WORK_CUSTOM_DEALER();
		dealerParam.setCfg_id(copyCfgId);
		List<ES_WORK_CUSTOM_DEALER> dealers = this.qryDealList(dealerParam , null);
		
		if(dealers!=null && dealers.size()>0){
			Map<String,String> nodeIndex2IDMap = new HashMap<String, String>();
			
			//批量取新的编号
			List<String> newIds = this.getNewCfgIdBatch(dealers.size());
			List<String> newVersionIds = this.getNewCfgIdBatch(dealers.size());
			
			for(Iterator<ES_WORK_CUSTOM_NODE> it = nodes.iterator();it.hasNext();){
				ES_WORK_CUSTOM_NODE node = it.next();
				nodeIndex2IDMap.put(node.getNode_index(), node.getNode_id());
			}
			
			int count = 0;
			for(Iterator<ES_WORK_CUSTOM_DEALER> it = dealers.iterator();it.hasNext();){
				ES_WORK_CUSTOM_DEALER dealer = it.next();
				dealer.setDeal_id(newIds.get(count));
				dealer.setD_version_id(newVersionIds.get(count));
				dealer.setCfg_id(copy_cfg.getCfg_id());
				dealer.setNode_id(nodeIndex2IDMap.get(dealer.getNode_index()));
				
				count++;
			}
			
			//保存处理人信息
			this.getDealerDao().saveBatch("insert", dealers, null);
		}
		
		return copy_cfg.getCfg_id();
	}
}