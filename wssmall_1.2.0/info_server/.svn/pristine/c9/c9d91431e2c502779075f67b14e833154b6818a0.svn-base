package com.ztesoft.net.template.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import params.ZteBusiRequest;
import zte.net.common.annontion.context.request.DefaultActionRequestDefine;
import zte.net.common.annontion.context.request.RequestBeanDefinition;
import zte.net.iservice.consts.InfoConsts;
import zte.params.template.resp.TemplateAccessConvertResp;
import zte.params.template.resp.TemplateOuterConvertResp;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.template.model.NodeModel;
import com.ztesoft.net.template.model.OrderTemplateNode;
import com.ztesoft.net.template.service.ITemplateConvertManager;

public class TemplateConvertManager extends BaseSupport implements ITemplateConvertManager{

	public static DefaultActionRequestDefine context = null;
	
	private ThreadLocal<Map<String, Integer>> threadRecMap = new ThreadLocal<Map<String, Integer>>();		//记录多记录字段存储次数{'表名_字段':'次数'}
	
	@Override
	public TemplateAccessConvertResp tplAccessConvert(Map<String, Object> busiMap,
			Map<String, NodeModel> tplMap) {
		TemplateAccessConvertResp resp = new TemplateAccessConvertResp();
		Map<String, Object> objMap = new HashMap<String, Object>();
		try{
			//生成业务对象
			Map<String, Integer> recMap = new HashMap<String, Integer>();
			threadRecMap.set(recMap);
			this.genBusiResult(objMap, busiMap, tplMap, "");
			//保存业务对象
			Iterator<Entry<String, Object>> iterator = objMap.entrySet().iterator();
			while(iterator.hasNext()){
				 Entry<String, Object> entry = iterator.next();
				 Object obj = entry.getValue();
				 if(obj instanceof ZteBusiRequest){
					 Method method = obj.getClass().getMethod("store");
					 method.invoke(obj);
				 }
			}
		}catch(Exception e){
			resp.setError_msg("构建业务对象失败");
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public TemplateOuterConvertResp tplOuterConvert(Map<String, NodeModel> tplMap, String inst_id) {
		TemplateOuterConvertResp resp = new TemplateOuterConvertResp();
		try{
			Map<String, Object> infMap = new HashMap<String, Object>();
			this.analyTpl(tplMap, inst_id, infMap);
			resp.setInfMap(infMap);
		}catch(Exception e){
			resp.setError_msg("拼装报文异常");
			e.printStackTrace();
		}
		return resp;
	}
	
	
	/**
	 * 递归生成业务对象
	 * @param objMap
	 * @param busiMap
	 * @param tplMap
	 * @param node_path
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void genBusiResult(Map<String, Object> objMap, 
			Map<String, Object> busiMap, Map<String, NodeModel> tplMap, String node_path){
		try{
			Iterator<Entry<String, Object>> iterator = busiMap.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<String, Object> entry = iterator.next();
				String key = entry.getKey();	//值、是否表节点、那张表
				Object key_val = entry.getValue();
				if(null != key_val){		//不为空才保存,为空不做处理
					if(key_val instanceof Map){
						node_path += key + ".";
						genBusiResult(objMap, (Map<String, Object>)key_val, tplMap, node_path);
					}else if(key_val instanceof List){	//遍历列表
						node_path += key + ".";
						for(int i = 0; i < ((List)key_val).size(); i++){
							Map<String, Object> subBusiMap = (Map<String, Object>)((List)key_val).get(i);
							genBusiResult(objMap, subBusiMap, tplMap, node_path);
						}
					}else if(key_val instanceof String){ 	//	处理叶子节点
						this.dealLeafNode(objMap, tplMap, entry, node_path);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取叶子节点的归属对象、并赋值
	 * @param objMap
	 * @param tplMap
	 * @param entry
	 * @param node_path
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void dealLeafNode(Map<String, Object> objMap, Map<String, NodeModel> tplMap, 
			Entry<String, Object> entry, String node_path){
		this.initContext();
		String key = entry.getKey();	
		OrderTemplateNode  ordTplNode = this.getTplVal(tplMap, node_path, key);
		String table_code = ordTplNode.getNode_table_code();
		Object busiObj = objMap.get(table_code);
		//根据表名得到业务对象
		RequestBeanDefinition requestBeanDefinition = this.getRequestServiceDefinitionByTableName(table_code);
		//获取表类型
		RequestBeanDefinition<ZteBusiRequest> serviceDefinition = context.getActionRequestServiceMap(requestBeanDefinition.getService_name());
		String tableType = serviceDefinition.getTable_type();
		//
		Object key_val = entry.getValue();
		if(null == busiObj){	//业务对象不存在
			if(InfoConsts.TABEL_TYPE.equals(tableType)){	//多记录表(相对于一个订单)
				List<Object> busiList = new ArrayList<Object>();
				//实例化对象
				Object beanInst = ApiContextHolder.getBean(requestBeanDefinition.getService_name());
				this.setObjVal(beanInst, ordTplNode.getNode_table_field_code(), key_val);
				busiList.add(beanInst);
				//记录字段的记录数
				Map<String, Integer> recMap = threadRecMap.get();
				recMap.put(table_code + "_" + ordTplNode.getNode_table_field_code(), 1);
				threadRecMap.set(recMap);
				//保存业务对象
				objMap.put(table_code, busiList);
			}else {		//单记录表
				Object beanInst = ApiContextHolder.getBean(requestBeanDefinition.getService_name());
				this.setObjVal(beanInst, ordTplNode.getNode_table_field_code(), key_val);
				objMap.put(table_code, beanInst);
			}
		}else {		//业务对象已存在
			if(InfoConsts.TABEL_TYPE.equals(tableType)){	//多记录表
				List busiList = (List)busiObj;
				Map<String, Integer> recMap = threadRecMap.get();
				Integer saveCount = recMap.get(table_code + "_" + ordTplNode.getNode_table_field_code()) == null ? 0 : recMap.get(table_code + "_" + ordTplNode.getNode_table_field_code());
				Integer size = busiList.size();
				if(saveCount == size && saveCount != 0){		//已保存,需要新增实例
					Object beanInst = ApiContextHolder.getBean(requestBeanDefinition.getService_name());
					this.setObjVal(beanInst, ordTplNode.getNode_table_field_code(), key_val);
					recMap.put(table_code + "_" + ordTplNode.getNode_table_field_code(), saveCount++);
					busiList.add(beanInst);
				}else {		//未曾保存，可用原对象继续保存
					Object beanInst = busiList.get(size - 1);
					this.setObjVal(beanInst, ordTplNode.getNode_table_field_code(), key_val);
					recMap.put(table_code + "_" + ordTplNode.getNode_table_field_code(), saveCount++);
				}
				threadRecMap.set(recMap);
				objMap.put(table_code, busiList);
			}else {		//单记录表
				this.setObjVal(busiObj, ordTplNode.getNode_table_field_code(), key_val);
			}
		}
	}
	
	/**
	 * 获取模板值(说明：模板的叶子节点，全部是OrderTemplateNode类型)
	 * @param tplMap
	 * @param node_path
	 * @param node_code
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private OrderTemplateNode getTplVal(Map<String, NodeModel> tplMap, String node_path, String node_code){
		OrderTemplateNode tplNode = new OrderTemplateNode();
		try{
			if(StringUtils.isEmpty(node_path)){
				tplNode = this.getNodeInfo(tplMap, node_code);
			}else {
				String[] paths = node_path.split(".");
				Map<String, NodeModel> pMap = this.getSubNode(tplMap, paths[0]);  //tplMap只做遍历，不能修改
				for(int i = 1; i < paths.length; i++){
					pMap = this.getSubNode(pMap, paths[i]);
				}
				tplNode = this.getNodeInfo(pMap, node_code);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return tplNode;
	}
	
	/**
	 * 解析模板内容
	 * @param tplMap
	 * @param inst_id
	 * @param inf_content
	 */
	private void analyTpl(Map<String, NodeModel> tplMap, String inst_id, Map<String, Object> infMap){
		Iterator<Entry<String, NodeModel>> iterator = tplMap.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, NodeModel> entry = iterator.next();
			String node_name = entry.getKey();
			NodeModel node_obj = entry.getValue();
			OrderTemplateNode nodeInfo = node_obj.getNodeInfo();
			if(node_obj.getSub_node() == null){//叶子节点
				String node_val = this.getNodeVal(nodeInfo, inst_id);
				this.setInfVal(infMap, nodeInfo.getNode_path(), node_name, node_val);
			}else {//非叶子节点
				if(nodeInfo.getNode_repeat() == InfoConsts.NODE_REPEAT_YES){		//可重复
					List<Map<String, Object>> infList = this.analyTplList(nodeInfo.getNode_read_comments(),node_obj.getSub_node(), inst_id);
					this.setInfVal(infMap, nodeInfo.getNode_path(), node_name, infList);
				}else {
					this.setInfVal(infMap, nodeInfo.getNode_path(), node_name, new HashMap<String, Object>());
				}
			}
		}
	}
	
	
	/**
	 * 可重复记录数处理
	 * @param tplMap
	 * @param inst_id
	 * @param infMap
	 */
	private List<Map<String, Object>> analyTplList(String nodeRead, Map<String, NodeModel> tplMap, String inst_id){
		List<Map<String, Object>> tplList = new ArrayList<Map<String, Object>>();
		//调用本地代码，待补充
		//nodeRead , 业务组件 ZteInfTraceRule.orderAccountOrBuybackInform， zte,  es_assembly
		
		
		return tplList;
	}
	
	
	/**
	 * 获取模板值
	 * @param ordTplNode
	 * @param inst_id
	 * @return
	 */
	private String getNodeVal(OrderTemplateNode ordTplNode, String inst_id){
		String node_value = "";
		try{
			RequestBeanDefinition<ZteBusiRequest> serviceDefinition = null;//context.getActionRequestServiceMap(ordTplNode.getNode_class_name());
			if(InfoConsts.TABEL_TYPE.equals(serviceDefinition.getTable_type())){//多条记录
				ordTplNode.getNode_read_comments();		//辅助解析信息
				//公用方法待提供（根据辅助信息获取字段值）  {'table_name': 'table', 'field_name': 'order_itme_id', 'field_value': '123'}
				ordTplNode.getNode_table_field_code();
				
				
			}else{
				//公共方法待提供（根据字段、表名获取字段值）
				//getValueByKeyName(String order_id,String keyName,String table_name) 默认获取第0个对象中的keyName字段的值
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return node_value;
	}
	
	
	@SuppressWarnings("unchecked")
	private void setInfVal(Map<String, Object> infMap, String node_path,String node_name, Object node_value){
		try{
			if(StringUtils.isEmpty(node_path)){
				infMap.put(node_name, node_value);
			}else{
				String[] paths = node_path.split(".");		//其中不存在list（特殊处理），全部是map结构
				String new_path = "";
				Map<String, Object> nodeMap = (Map<String, Object>)infMap.get(paths[0]);
				for(int i = 1; i < paths.length; i++){
					if(i != paths.length - 1){
						new_path += paths[i] + ".";
					}
					nodeMap = (Map<String, Object>)nodeMap.get(paths[i]);			//不需要做空判断
				}
				new_path = new_path.substring(0, new_path.length() - 1);			//去掉分隔符
				nodeMap.put(node_name, node_value);
				setInfVal(infMap, new_path, paths[paths.length - 1], nodeMap);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取子节点
	 * @param tplMap
	 * @param node_name
	 * @return
	 */
	private Map<String, NodeModel> getSubNode(Map<String, NodeModel> tplMap, String node_name){
		NodeModel nodeModel = tplMap.get(node_name);
		if(null != nodeModel)
			return nodeModel.getSub_node();
		return null;
	}
	
	/**
	 * 获取当前节点的详细信息
	 * @param tplMap
	 * @param node_name
	 * @return
	 */
	private OrderTemplateNode getNodeInfo(Map<String, NodeModel> tplMap, String node_name){
		NodeModel nodeModel = tplMap.get(node_name);
		if(null != nodeModel)
			return nodeModel.getNodeInfo();
		return null;
	}
	
	/**
	 * 业务对象设值
	 * @param busiObj
	 * @param field_name
	 * @param field_value
	 */
	private void setObjVal(Object busiObj,String field_name, Object field_value){
		try{
			Field field = busiObj.getClass().getField(field_name);
			field.setAccessible(true);
			field.set(busiObj, field_value);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据表名获取业务对象信息
	 * @param table_name
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private RequestBeanDefinition<ZteBusiRequest> getRequestServiceDefinitionByTableName(String table_name){
		this.initContext();
		Map<String, RequestBeanDefinition> maps  = context.getActionRequestServiceMap();
		Iterator it =maps.keySet().iterator();
		while (it.hasNext()) {
			String key = (String)it.next();
			RequestBeanDefinition serviceDefinition = maps.get(key);
			if(serviceDefinition.getTable_name().equals(table_name))
				return serviceDefinition;
		}
		return null;
	}
	
	public void initContext (){
		context = DefaultActionRequestDefine.getInstance();
	}
}
