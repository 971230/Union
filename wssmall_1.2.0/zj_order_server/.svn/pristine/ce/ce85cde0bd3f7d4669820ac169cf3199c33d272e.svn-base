package com.ztesoft.net.service.workCustom.interfaces;

import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.workCustom.po.ES_ORDER_INTENT;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_CFG;
import zte.net.ecsord.params.workCustom.po.WORK_CUSTOM_FLOW_DATA;

public interface IWorkCustomEngine {
	/**
	 * 根据流程环节实例编号装载流程数据
	 * @param instance_id
	 * @return
	 * @throws Exception
	 */
	public WORK_CUSTOM_FLOW_DATA loadWorkFlowDataByInsId(String instance_id) throws Exception;
	
	/**
	 * 根据订单编号装载流程数据
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	public WORK_CUSTOM_FLOW_DATA loadWorkFlowDataByOrderId(String order_id) throws Exception;
	
	/**
	 * 根据流程编号装载流程数据
	 * @param workflow_id
	 * @return
	 * @throws Exception
	 */
	public WORK_CUSTOM_FLOW_DATA loadWorkFlowDataByFlowId(String workflow_id) throws Exception;

	/**
	 * 正式订单启动工作流（包括执行流程）
	 * @param orderTree 订单树对象
	 * @param cfg_type 流程类型
	 * @return 如果匹配到流程并执行成功，返回流程数据;否则返回null
	 * @throws Exception
	 */
	@Deprecated
	public WORK_CUSTOM_FLOW_DATA startWorkFlow(OrderTreeBusiRequest orderTree,String cfg_type) throws Exception;
	
	/**
	 * 正式订单启动工作流（包括执行流程）
	 * @param orderTree 订单树对象
	 * @param cfg 流程配置
	 * @return
	 * @throws Exception
	 */
	public WORK_CUSTOM_FLOW_DATA startWorkFlow(OrderTreeBusiRequest orderTree,ES_WORK_CUSTOM_CFG cfg) throws Exception;
	
	/**
	 * 意向单启动工作流
	 * @param orderIntent 意向单对象
	 * @param cfg 流程配置
	 * @return 如果匹配到流程并执行成功，返回流程数据;否则返回null
	 * @throws Exception
	 */
	public WORK_CUSTOM_FLOW_DATA startWorkFlow(ES_ORDER_INTENT orderIntent,ES_WORK_CUSTOM_CFG cfg) throws Exception;
	
	/**
	 * 订单是否匹配自定义流程
	 * @param flow_code 流程编码
	 * @param orderTree 订单树对象
	 * @param cfg_type 流程类型
	 * @return 匹配返回配置信息，不匹配返回null
	 * @throws Exception
	 */
	public ES_WORK_CUSTOM_CFG doWorkCustomCfgMatch(String flow_code,OrderTreeBusiRequest orderTree,String cfg_type) throws Exception;
	
	/**
	 * 订单是否匹配自定义流程
	 * @param flow_code 流程编码
	 * @param orderIntent 意向单对象
	 * @param cfg_type 流程类型
	 * @return 匹配返回流程配置，不匹配返回null
	 * @throws Exception
	 */
	public ES_WORK_CUSTOM_CFG doWorkCustomCfgMatch(String flow_code,ES_ORDER_INTENT orderIntent,String cfg_type) throws Exception;
	
	/**
	 * 执行流程（从流程环节实例中的当前环节开始执行）
	 * @param flowData 流程数据
	 * @param moveHis 操作环节是否记录历史表
	 * @throws Exception
	 */
	@Deprecated
	public WORK_CUSTOM_FLOW_DATA runWorkFlow(WORK_CUSTOM_FLOW_DATA flowData,boolean moveHis) throws Exception;
	
	/**
	 * 执行流程环节实例
	 * @param node_id  环节编号
	 * @param flowData 流程数据
	 * @param isGoNextManual 是否往下一环节执行（一般前台选择、前台处理、后台等待环节指定往下一环节执行时，传入true；否则传入false）
	 * @param webCondition	分支条件（前台选择环节往下一环节执行，需要选择分支时传入）
	 * @param count 执行流程环节计数器（防止死循环，一次执行的环节数量不能大于100）
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public boolean runNode(String node_id,WORK_CUSTOM_FLOW_DATA flowData,
			boolean isGoNextManual,String webCondition,int count) throws Exception;
	
	/**
	 * 执行环节实例
	 * @param instance_id 环节实例编号
	 * @param isGoNextManual 是否往下一环节执行（一般前台选择、前台处理、后台等待环节指定往下一环节执行时，传入true；否则传入false）
	 * @param webCondition 分支条件（前台选择环节往下一环节执行，需要选择分支时传入）
	 * @param remark 备注
	 * @param remark JSON参数字符串
	 * @throws Exception
	 */
	public WORK_CUSTOM_FLOW_DATA runNodeManual(String instance_id,boolean isGoNextManual,
			String webCondition,String remark,String json_param) throws Exception;
	
	/**
	 * 根据环节编码执行环节实例
	 * @param order_id 订单编号
	 * @param node_code 环节编码
	 * @param isGoNextManual 是否往下一环节执行（一般前台选择、前台处理、后台等待环节指定往下一环节执行时，传入true；否则传入false）
	 * @param webCondition 分支条件（前台选择环节往下一环节执行，需要选择分支时传入）
	 * @param remark 备注
	 * @param json_param JSON参数字符串
	 * @return
	 * @throws Exception
	 */
	public WORK_CUSTOM_FLOW_DATA runNodeManualByCode(String order_id,String node_code,boolean isGoNextManual,
			String webCondition,String remark,String json_param) throws Exception;
	
	/**
	 * 订单流程继续执行（从流程环节实例中的当前环节开始执行），一般用于订单报错时从报错环开始再次执行
	 * @param order_id 订单编号
	 * @throws Exception
	 */
	public WORK_CUSTOM_FLOW_DATA continueWorkFlow(String order_id) throws Exception;
	
	/**
	 * 流程跳转到某一环节 
	 * @param instance_id 流程环节实例
	 * @param remark 备注
	 * @throws Exception
	 */
	public String gotoNode(String instance_id,String remark) throws Exception;
	
	/**
	 * 取消流程（流程环节实例转历史表）
	 * @param order_id 订单编号
	 * @throws Exception
	 */
	public void cancelWorkFlow(String order_id) throws Exception;
	
	/**
	 * 回退到上一环节
	 * @param instance_id 当前环节实例编号
	 * @param remark 备注
	 * @throws Exception 如果没有上一个环节抛出异常
	 */
	public void rollback(String instance_id,String remark) throws Exception;
	
	/**
	 * 校验当前环节
	 * @param order_id 订单编号
	 * @param node_name 环节名称
	 * @param cfg_code 环节处理方法配置
	 * @return
	 * @throws Exception
	 */
	public String checkCurrNodes(String order_id,
			String node_name,String cfg_code) throws Exception;
	
	/**
	 * 根据环节编码跳转
	 * @param order_id 订单编号
	 * @param node_code 环节编码
	 * @param remark 备注
	 * @return
	 * @throws Exception
	 */
	public String gotoNode(String order_id,String node_code,
			String remark) throws Exception;
	
}
