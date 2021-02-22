package com.ztesoft.net.service.workCustom.interfaces;

import java.util.List;
import java.util.Map;

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

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.database.SqlBuilderInterface;

/**
 * 流程配置管理器接口
 * @author zhaoc
 *
 */
public interface IWorkCustomCfgManager {
	
	/**
	 * 获取序列
	 * @param seq_name
	 * @return
	 * @throws Exception
	 */
	public String getSequence(String seq_name) throws Exception;

	/**
	 * 获取新的配置编号
	 * @return
	 * @throws Exception
	 */
	public String getNewCfgId() throws Exception;
	
	/**
	 * 批量获取配置编号
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<String> getNewCfgIdBatch(int size) throws Exception;
	
	/**
	 * 获取版本号
	 * @return
	 * @throws Exception
	 */
	public String getNewVersionId() throws Exception;
	
	/**
	 * 新增流程配置
	 * @param cfg
	 * @param nodes
	 * @param connects
	 * @return
	 * @throws Exception
	 */
	public String addCfg(ES_WORK_CUSTOM_CFG cfg,List<ES_WORK_CUSTOM_NODE> nodes,
			List<ES_WORK_CUSTOM_CONNECT> connects) throws Exception;
	
	/**
	 * 修改流程配置
	 * @param cfg
	 * @param nodes
	 * @param connects
	 * @throws Exception
	 */
	public void updateCfg(ES_WORK_CUSTOM_CFG cfg,List<ES_WORK_CUSTOM_NODE> nodes,
			List<ES_WORK_CUSTOM_CONNECT> connects) throws Exception;
	
	/**
	 * 删除流程配置
	 * @param cfg
	 * @throws Exception
	 */
	public void deleteCfg(ES_WORK_CUSTOM_CFG cfg) throws Exception;
	
	/**
	 * 查询流程配置TABLE
	 * @param pojo
	 * @param sqlBuilds
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page qryCfgPageByPojo(ES_WORK_CUSTOM_CFG pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception;
	
	/**
	 * 查询历史流程配置TABLE
	 * @param pojo
	 * @param sqlBuilds
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page qryCfgHisPageByPojo(ES_WORK_CUSTOM_CFG pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception;
	
	/**
	 * 查询流程配置列表
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public List<ES_WORK_CUSTOM_CFG> qryCfgList(ES_WORK_CUSTOM_CFG pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 查询流程环节列表
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public List<ES_WORK_CUSTOM_NODE> qryNodeList(ES_WORK_CUSTOM_NODE pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 查询流程连接列表
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public List<ES_WORK_CUSTOM_CONNECT> qryConnectList(ES_WORK_CUSTOM_CONNECT pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 流程配置移历史表
	 * @param cfg_id
	 * @throws Exception
	 */
	public void moveCfg2His(String cfg_id) throws Exception;
	
	/**
	 * 查询所有流程配置列表（当前表和历史表）
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public List<ES_WORK_CUSTOM_CFG> qryCfgListFromAll(ES_WORK_CUSTOM_CFG pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 查询所有流程环节列表（当前表和历史表）
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public List<ES_WORK_CUSTOM_NODE> qryNodeListFromAll(ES_WORK_CUSTOM_NODE pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 查询所有流程连接列表（当前表和历史表）
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public List<ES_WORK_CUSTOM_CONNECT> qryConnectListFromAll(ES_WORK_CUSTOM_CONNECT pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 查询处理人TABLE
	 * @param pojo
	 * @param sqlBuilds
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page qryDealPageByPojo(ES_WORK_CUSTOM_DEALER pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception;
	
	/**
	 * 查询处理人历史TABLE
	 * @param pojo
	 * @param sqlBuilds
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page qryDealHisPageByPojo(ES_WORK_CUSTOM_DEALER pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception;
	
	/**
	 * 新增处理人配置
	 * @param dealer
	 * @throws Exception
	 */
	public void addDealer(ES_WORK_CUSTOM_DEALER dealer) throws Exception;
	
	/**
	 * 修改处理人配置
	 * @param dealer
	 * @throws Exception
	 */
	public void updateDealer(ES_WORK_CUSTOM_DEALER dealer) throws Exception;
	
	/**
	 * 删除处理人配置
	 * @param dealer
	 * @throws Exception
	 */
	public void deleteDealer(ES_WORK_CUSTOM_DEALER dealer) throws Exception;
	
	/**
	 * 处理人配置移历史表
	 * @param deal_id
	 * @throws Exception
	 */
	public void moveDeal2His(String deal_id) throws Exception;
	
	/**
	 * 根据流程配置编号将处理人配置移历史表
	 * @param cfg_id
	 * @throws Exception
	 */
	public void moveDeal2HisByCfgId(String cfg_id) throws Exception;
	
	/**
	 * 查询处理人配置
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public List<ES_WORK_CUSTOM_DEALER> qryDealList(ES_WORK_CUSTOM_DEALER pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 查询所有处理人配置（当前表和历史表）
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public List<ES_WORK_CUSTOM_DEALER> qryDealListFromAll(ES_WORK_CUSTOM_DEALER pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 获取营业县分信息
	 * @return 以营业县分编号为KEY值，营业信息为VALUE值的MAP
	 * @throws Exception
	 */
	public Map<String,ES_DC_PUBLIC_DICT_RELATION> getBusiCountyInfoMap() throws Exception;
	
	/**
	 * 获取实例编号
	 * @return
	 * @throws Exception
	 */
	public String getNewInstanceId() throws Exception;
	
	/**
	 * 批量获取实例编号
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<String> getNewInstanceIdBatch(int size) throws Exception;
	
	/**
	 * 新增流程环节实例
	 * @param inses
	 * @throws Exception
	 */
	public void addNodeInstances(List<ES_WORK_CUSTOM_NODE_INS> inses) throws Exception;
	
	/**
	 * 更新流程环节实例
	 * @param data 流程数据
	 * @param moveHis 操作换机是否记录历史表
	 * @throws Exception
	 */
	public void updaeNodeInstances(WORK_CUSTOM_FLOW_DATA data,boolean moveHis) throws Exception;
	
	/**
	 * 更新流程环节实例
	 * @param inses 环节实例
	 * @throws Exception
	 */
	public void updateNodeInstances(List<ES_WORK_CUSTOM_NODE_INS> inses) throws Exception;
	
	/**
	 * 删除流程环节实例
	 * @param data
	 * @throws Exception
	 */
	public void deleteNodeInstances(WORK_CUSTOM_FLOW_DATA data) throws Exception;
	
	/**
	 * 流程环节实例移历史表
	 * @param insIds
	 * @throws Exception
	 */
	public void moveIns2his(List<String> insIds) throws Exception;
	
	/**
	 * 流程实例移历史表
	 * @param workflow_id 流程编号
	 * @param state 状态。2--正常结束，3--删除
	 * @throws Exception
	 */
	public void moveIns2Finish(String workflow_id,String state) throws Exception;
	
	/**
	 * 从当前表中删除环节实例
	 * @param insIds
	 * @throws Exception
	 */
	public void deleteIns(List<String> insIds) throws Exception;
	
	/**
	 * 查询流程环节实例当前表
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public List<ES_WORK_CUSTOM_NODE_INS> qryInsList(ES_WORK_CUSTOM_NODE_INS pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 查询流程环节实例当前表、完成表
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public List<ES_WORK_CUSTOM_NODE_INS> qryInsListFromAll(ES_WORK_CUSTOM_NODE_INS pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 查询流程环节实例历史表
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public List<ES_WORK_CUSTOM_NODE_INS> qryInsListHis(ES_WORK_CUSTOM_NODE_INS pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 设置自定义流程标志位
	 * @param data
	 * @throws Exception
	 */
	public void setWorkCustomFlag(WORK_CUSTOM_FLOW_DATA data) throws Exception;
	
	/**
	 * 查询流程实例当前表数量
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public int qryInsCountByPojo(ES_WORK_CUSTOM_NODE_INS pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 新增流程实例
	 * @param workflow
	 * @throws Exception
	 */
	public void addWorkFlowIns(ES_WORK_CUSTOM_WORKFLOW_INS  workflow) throws Exception;
	
	/**
	 * 查询流程实例SQL
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public List<ES_WORK_CUSTOM_WORKFLOW_INS> qryWorkflowInsList(ES_WORK_CUSTOM_WORKFLOW_INS pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 从当前表和完成表中查询流程实例SQL
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public List<ES_WORK_CUSTOM_WORKFLOW_INS> qryWorkflowInsFromAll(ES_WORK_CUSTOM_WORKFLOW_INS pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 更新意向单对象
	 * @param orderIntent
	 * @throws Exception
	 */
	public void updateOrderIntent(ES_ORDER_INTENT orderIntent) throws Exception;
	
	/**
	 * 新增意向单
	 * @param orderIntent
	 * @throws Exception
	 */
	public void newOrderIntent(ES_ORDER_INTENT orderIntent) throws Exception;
	
	/**
	 * 查询意向单列表
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public List<ES_ORDER_INTENT> qryOrderIntentList(ES_ORDER_INTENT pojo, 
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 流程环节实例批量保存
	 * @param operate
	 * @param pojoList
	 * @param conditionCol
	 * @throws Exception
	 */
	public void saveNodeInsBatch(String operate,List<ES_WORK_CUSTOM_NODE_INS> pojoList,
			String[] conditionCol) throws Exception;
	
	/**
	 * 查询人员
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public List<User> qryUserList(User pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 查询组织
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public List<Organization> qryOrganizationList(Organization pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 根据商品编号查询商品信息
	 * @param goods_id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> qryGoodsInfoByGoodsid(String goods_id) throws Exception;
	
	/**
	 * 插入数据
	 * @param table
	 * @param fields
	 * @param args
	 * @throws Exception
	 */
	public void insert(String table, Map<String,Object> fields, Object... args) throws Exception;
	
	/**
	 * 查询订单锁定列表
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public List<ES_ORDER_LOCK> qryOrderLockList(ES_ORDER_LOCK pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 保存锁定信息
	 * @param operate
	 * @param pojo
	 * @param conditionCol
	 * @throws Exception
	 */
	public void saveLock(String operate,ES_ORDER_LOCK pojo,String[] conditionCol) throws Exception;
	
	/**
	 * 查询锁定数量
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public int qryOrderLockCount(ES_ORDER_LOCK pojo,List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 执行SQL语句
	 * @param sql
	 * @param args
	 */
	public void execute(String sql, Object... args) throws Exception;
	
	/**
	 * 查询工单数量
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public int qryWorkOrderCount(ES_WORK_ORDER pojo, 
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 查询团队PAGE
	 * @param pojo
	 * @param sqlBuilds
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page qryTeamPageByPojo(ES_USER_TEAM pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception;
	
	/**
	 * 批量保存日志
	 * @param operate
	 * @param pojoList
	 * @param conditionCol
	 * @throws Exception
	 */
	public void saveLogBatch(String operate,List<ES_WORK_CUSTOM_LOG> pojoList,
			String[] conditionCol) throws Exception;
	
	/**
	 * 查询日志
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public List<ES_WORK_CUSTOM_LOG> qryPojoList(ES_WORK_CUSTOM_LOG pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception;
	
	/**
	 * 是否为“更新订单信息”环节
	 * @param deal_url
	 * @return
	 * @throws Exception
	 */
	public boolean isUpdateOrderNode(String deal_url) throws Exception;
	
	/**
	 * 复制流程
	 * @param copy_cfg
	 * @return
	 * @throws Exception
	 */
	public String copyCfg(ES_WORK_CUSTOM_CFG copy_cfg) throws Exception;
	
	/**转移实例数据到在途表
	 * @param order_id
	 * @throws Exception
	 */
	public void moveFinsh2Ins(String order_id) throws Exception;
}
