package com.ztesoft.net.template.action;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ztesoft.ibss.common.util.DateUtil;
import com.opensymphony.xwork2.ModelDriven;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.model.AttrDef;
import com.ztesoft.net.template.model.EsAttrDef;
import com.ztesoft.net.template.model.NodeModel;
import com.ztesoft.net.template.model.OrderTemplateNode;
import com.ztesoft.net.template.service.IOrdTemplateManager;
import com.ztesoft.net.template.util.NodeModelParseUtil;
import com.ztesoft.net.template.util.OrdTemplateUtil;
import com.ztesoft.net.template.vo.OrdTplDTO;
import com.ztesoft.net.template.vo.TepletaParam;
import commons.CommonTools;

public class OrdTemplateAction extends WWAction implements ModelDriven<TepletaParam>{
	
	private static final long serialVersionUID = 1L;
	private TepletaParam tepletaParam; //领域对象
	@Resource
	private IOrdTemplateManager ordTemplateManager;
	
	private OrdTplDTO ordTplDTO;

	public OrdTplDTO getOrdTplDTO() {
		return ordTplDTO;
	}

	public void setOrdTplDTO(OrdTplDTO ordTplDTO) {
		this.ordTplDTO = ordTplDTO;
	}

	/**
	 * 查询订单模板列表
	 * @return
	 */
	public String getOrdTplList(){
		if(ordTplDTO==null){
			ordTplDTO=new OrdTplDTO();
		}
		this.webpage=this.ordTemplateManager.queryTplList(this.getPage(), 5, ordTplDTO);
		return "tpl_main";
	}
	
	/**
	 * 跳转到模板配置页面,设置保存标志
	 * @return
	 */
	public String toTplConfig(){
		if("update".equals(ordTplDTO.getFlag())){
			//根据id查询模板是否存在
			ordTplDTO=ordTemplateManager.queryTplById(ordTplDTO);
			if(ordTplDTO==null){
				json="{'status':1,'msg':'操作有误'}";
				return JSON_MESSAGE;
			}
			if(ordTplDTO!=null){
				ordTplDTO.setFlag("update");
			}
		}
		return "tpl_config";
	}
	
	/**
	 * 新增模板信息入库
	 * @return
	 */
	public String saveTpl(){
		try {
			ordTplDTO.setFlag("add");
			//判断模板是否存在
			OrdTplDTO dto=this.ordTemplateManager.queryTplByCode(ordTplDTO);
			if(dto!=null){
				this.json="{'status':'1','msg':'该模板已存在'}";
			}else{
				this.ordTemplateManager.insertTpl(ordTplDTO);
				String tplId=ordTplDTO.getOrder_template_id();
				this.json = "{'status':'0','msg':'保存成功','tplId':'"+tplId+"'}";
				//写入主模版日志表
				ordTemplateManager.insertTplLog(ordTplDTO);
			}
		} catch (Exception e) {
			this.json="{'status':'1','msg':'保存失败'}";
		}
		return  JSON_MESSAGE;
	}
	
	/**
	 * 粘贴模板入库
	 * @return
	 */
	public String saveTplByCatalogueId(){
		String param=this.getRequest().getParameter("param");
		json=this.ordTemplateManager.insertTplNode(param);
		return JSON_MESSAGE;
	}
 
	/**
	 * 修改模板信息
	 * @return
	 */
	public String updateTpl(){
		try {
			ordTplDTO.setFlag("modify");
			//判断模板是否存在
			OrdTplDTO dto=this.ordTemplateManager.queryTplByCode(ordTplDTO);
			if(dto==null){
				ordTemplateManager.updateTpl(ordTplDTO);
				json="{'status':'0','msg':'修改成功'}";
				//写入模版日志
				ordTemplateManager.insertTplLog(ordTplDTO);
			}else{
				json="{'status':'1','msg':'该模板已存在'}";
			}
		} catch (Exception e) {
			json="{'status':'1','msg':'操作失败'}";
		}
		return JSON_MESSAGE;
	}
	
	/**
	 * 删除一条模板
	 * @return
	 */
	public String delTel(){
		try {
			ordTplDTO.setFlag("delete");
			//写入主模板日志表
			ordTemplateManager.insertTplLog(ordTplDTO);
			//删除模板
			ordTemplateManager.deleteTpl(ordTplDTO);
			//删除对应的节点
			ordTemplateManager.delTemplateNodeByTplId(ordTplDTO.getOrder_template_id());
			json="{'status':'0','msg':'删除成功'}";
		} catch (Exception e) {
			e.printStackTrace();
			json="{'status':'1','msg':'操作失败'}";
		}
		return  JSON_MESSAGE;
	}
	
    /**
	 * 刷新模板组件
	 * 
	 * Copyright (c) 2015, 南京中兴软创科技股份有限公司 All rights reserved
	 * @author      : 张金叶
	 * @createTime  : 2015-3-3
	 * @version     : 1.0
	 */
	public String refrushTempleteComp(){
		try{
			int count = OrdTemplateUtil.scanTemplete("com.ztesoft.net.template.busi");
			json = "{status:0,msg:'刷新成功，总共刷对象数量["+ (count ) +"]个'}";
		}catch(Exception ex){
			ex.printStackTrace();
			json = "{status:1,msg:'失败'}";
		}
		return JSON_MESSAGE;
	} 
    //打开树界面，后面可以删除
	public String templateTree(){
		return "template_tree";
	}
	//打开树界面，后面可以删除
	public String CatalogueTree(){
		return "catalogue_tree";
	}
	
	/**
	 * 获取节点树
	 * @author chen.zuoqin
	 * @return
	 */
	public String qryRootDirectory(){
		tepletaParam.setNodeList(ordTemplateManager.qryTemplateTree(tepletaParam.getOrderTemplateId(),tepletaParam.getSuper_id()));
		json = JSON.toJSONString(tepletaParam.getNodeList());		
		return JSON_MESSAGE;
	}

	/**
	 * 编辑节点树(单元格值)
	 * @return
	 */
	public String modTemplateNode(){
		OrderTemplateNode node=tepletaParam.getOrderTemplateNode();
		try {
			if(node.getNode_name()!=null){
				node.setNode_name(java.net.URLDecoder.decode(node.getNode_name(),"UTF-8"));
			}
			if(node.getNode_code()!=null){
				node.setNode_code(java.net.URLDecoder.decode(node.getNode_code(), "UTF-8"));
			}
			this.ordTemplateManager.editTemplateNode(node);
			json="{'status':'0','msg':'修改成功'}";
		} catch (Exception e) {
			e.printStackTrace();
			json="{'status':'1','msg':'操作失败'}";
		}
		return JSON_MESSAGE;
	}
	
	/**
	 * 打开节点信息界面，包括新增、修改、删除  (老的模式，不用)
	 * @author chen.zuoqin
	 * @return
	 */
	public String addTemplateNode(){
		if (StringUtils.isNotBlank(tepletaParam.getFlag())) {
			if (tepletaParam.getFlag().equals("edit") || tepletaParam.getFlag().equals("view")){
				//JSON对象转换为对象
				 //OrderTemplateNode  teplNode=CommonTools.jsonToBean(tepletaParam.getId(), OrderTemplateNode.class);
				 
				tepletaParam.setOrderTemplateNode(ordTemplateManager.qryTemplateNode(tepletaParam.getId()));
				String superId=tepletaParam.getOrderTemplateNode().getSuper_node_id();
			 
				if(superId!=null&&!"".equals(superId.toString())){
					if("-0".equals(superId)){
						tepletaParam.setNodeName("");
					}else{
						OrderTemplateNode addTemplate=ordTemplateManager.qryTemplateNode(superId.toString());
						tepletaParam.setNodeName(addTemplate.getNode_name());
					}
					
				}else{
					tepletaParam.setNodeName("");
				}
				
			}
		}
		return "add_template_node";
	}
	
	/**
	 * 保存节点信息---表格树使用
	 * @author chen.zuoqin
	 * @return
	 */
	public String addTplNode(){
		try {
			String id=tepletaParam.getId();
			String superId=tepletaParam.getSuper_id();
			String orderTemplateId=tepletaParam.getOrderTemplateId();
			String nodeName=java.net.URLDecoder.decode(tepletaParam.getNodeName(),"UTF-8"); 
			String nodeCode=java.net.URLDecoder.decode(tepletaParam.getPid(),"UTF-8"); 
			String nodePath=java.net.URLDecoder.decode(tepletaParam.getNodePath(),"UTF-8"); 
			String node_table_field_code=java.net.URLDecoder.decode(tepletaParam.getFlag(),"UTF-8"); 
			Long nodeDepth=tepletaParam.getNodeDepth();
			Long nodeSeq=tepletaParam.getSequ();
			if(nodeSeq==null||"".equals(nodeSeq.toString())){
				nodeSeq=new Long(1);
			}
			
			OrderTemplateNode tplNode= new OrderTemplateNode();
			tplNode.setOrder_template_id(orderTemplateId);
			tplNode.setNode_id(id);
			tplNode.setNode_name(nodeName);
			tplNode.setNode_code(nodeCode);
			tplNode.setNode_type("0");
			tplNode.setNode_path(nodePath+"->"+nodeCode);
			if(nodeDepth==null||"".equals(nodeDepth.toString())){
				nodeDepth=new Long(1);
			}
			tplNode.setNode_depth(nodeDepth);
			tplNode.setSuper_node_id(superId);
			tplNode.setNode_table_code("");
			tplNode.setNode_table_field_code(node_table_field_code);
			tplNode.setNode_comments("");
			tplNode.setNode_read_comments("");
			tplNode.setNode_repeat(new Long(0));
			tplNode.setNode_status("00A");
			tplNode.setNode_value_length(null);
			tplNode.setNode_exp("");
			tplNode.setNode_seq(nodeSeq);
			tplNode.setNode_repeat_desc("");
			tplNode.setCol1("");
			tplNode.setCol2("");
			tplNode.setCol3("");
			tplNode.setCol4("");
			tplNode.setCol5("");
			ordTemplateManager.saveTemplateNode(tplNode);
			json = "{'result':0,'message':'添加成功！'}";
		} catch (Exception e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}
		
		return JSON_MESSAGE;
	}
	
	/**
	 * 节点上下移---表格树使用
	 * @author chen.zuoqin
	 * @return
	 */
	public String downNodeTree(){
		try {
			String id=tepletaParam.getId();
			String[] splits=id.split(";");
			if(splits.length==2){
				for(int i=0;i<splits.length;i++){
					String one=splits[i];
					String[] ones=one.split(":");
					if(ones.length==2){
						 String nodeId=ones[0];
						 String nodeSeq=ones[1];
						 ordTemplateManager.updateNodeSeq(nodeId, nodeSeq);
					}
				}
			}
			json = "{'result':0,'message':'操作成功！'}";
		} catch (Exception e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}
		
		return JSON_MESSAGE;
	}
	
	public String updateNodeTable(){
		try {
			String id=tepletaParam.getId();
			String table_code=tepletaParam.getNodeName();
			String field_code=tepletaParam.getNodePath();
			ordTemplateManager.updateNodeTable(id, table_code, field_code);
			json = "{'result':0,'message':'操作成功！'}";
		} catch (Exception e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}
		
		return JSON_MESSAGE;
	}
	
	/**
	 * 保存节点信息(弹出界面修改使用)
	 * @author chen.zuoqin
	 * @return
	 */
	public String saveTemplateNode(){
		try {
			if (StringUtils.isNotBlank(tepletaParam.getFlag())) {
				if (tepletaParam.getFlag().equals("add")) {
					//String create_date = DateUtil.getTime2();
					ordTemplateManager.saveTemplateNode(tepletaParam.getOrderTemplateNode());
					json = "{'result':0,'message':'添加成功！'}";
				}
				else if (tepletaParam.getFlag().equals("edit")) {
					//orderTemplateNode.setNode_id(orderTemplateNode.getNode_id());
					ordTemplateManager.editTemplateNode(tepletaParam.getOrderTemplateNode());
					json = "{'result':0,'message':'修改成功！'}";
				}
			}
		} catch (Exception e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}
		return JSON_MESSAGE;
	}
	
	/**
	 * 删除节点
	 * @author chen.zuoqin
	 * @return
	 */
	public String delTemplateNode(){
		try {
			//从界面获取表单数据
			String id=tepletaParam.getId();
			String retNodeIds="";
			List<OrderTemplateNode> listTeplNode=ordTemplateManager.getChildNodeTreeByTpl(id);
			    for(OrderTemplateNode s:listTeplNode){
			    	String tempId=s.getNode_id();
			    	retNodeIds=retNodeIds+tempId+",";
					if(tempId!=null&&tempId.length()>0){
						ordTemplateManager.delTemplateNode(tempId);
					}
				}
			json = "{'result':0,'message':'操作成功！','retNodeIds':'"+retNodeIds+"'}";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json = "{'result':1,'message':'操作失败！'}";
		}
		return JSON_MESSAGE;
	}
	
	public String getChildNode(){
		try {
			//从界面获取表单数据
			String id=tepletaParam.getId();
			
			json=JSON.toJSONString(ordTemplateManager.getChildNodeTreeByTpl(id));
			 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json = "{'result':1,'message':'操作失败！'}";
		}
		return JSON_MESSAGE;
	}
	/**
	 * 查询模板目录树
	 * @author chen.zuoqin
	 * @return
	 */
	public String qryRootCatalogue(){
		tepletaParam.setCatalogueList(ordTemplateManager.qryRootCatalogue(tepletaParam.getId(), tepletaParam.getPid()));
		json = JSON.toJSONString(tepletaParam.getCatalogueList());
		return JSON_MESSAGE;
	}
	
	/**
	 * 打开目录界面
	 * @author chen.zuoqin
	 * @return
	 */
	public String addCatalogue(){
		  if (StringUtils.isNotBlank(tepletaParam.getFlag())) {
			if (tepletaParam.getFlag().equals("add") ){
				
			}else{
				tepletaParam.setCatalogue(ordTemplateManager.qryCatalogue(tepletaParam.getId()));
			}
		}
		return "add_catalogue";
	}
	
	/**
	 * 保存目录
	 * @author chen.zuoqin
	 * @return
	 */
	public String saveCatalogue(){
		try {
			if (StringUtils.isNotBlank(tepletaParam.getFlag())) {
				if (tepletaParam.getFlag().equals("add")) {
					String create_date = DateUtil.getTime2();
					tepletaParam.getCatalogue().setCreate_time(create_date);
					ordTemplateManager.saveCatalogue(tepletaParam.getCatalogue());
					json = "{'result':0,'message':'添加成功！'}";
				}
				else if (tepletaParam.getFlag().equals("edit")) {
					//orderTemplateNode.setNode_id(orderTemplateNode.getNode_id());
					ordTemplateManager.editCatalogue(tepletaParam.getCatalogue());
					json = "{'result':0,'message':'修改成功！'}";
				}
			}
		} catch (Exception e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}
		return JSON_MESSAGE;
	}
	
	/**
	 * 删除目录
	 * @author chen.zuoqin
	 * @return
	 */
	public String delCatalogue(){
		try {
			ordTemplateManager.delCatalogue(tepletaParam.getId());
			json = "{'result':0,'message':'操作成功！'}";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json = "{'result':1,'message':'操作失败！'}";
		}
		return JSON_MESSAGE;
	}
	
	/**
	 * 根据目录ID查询模板列表
	 * @author chen.zuoqin
	 * @return
	 */
	public String getTplList(){
		tepletaParam.setOrderTemplateList(ordTemplateManager.getOrderTemplateList(tepletaParam.getId()));
		json=JSON.toJSONString(tepletaParam.getOrderTemplateList());
		return JSON_MESSAGE;
	}
	
	/**
	 * 复制模板树
	 * @author chen.zuoqin
	 * @return
	 */
	public String copyOrderTpl(){
		String retStr=ordTemplateManager.copyOrderTpl(tepletaParam.getId());
		json = retStr;
		return JSON_MESSAGE;
	}
	
	/**
	 * 获取节点树（新的）
	 * @author chen.zuoqin
	 * @return
	 */
	public String getNodeTreeByTpl(){
		
		json=JSON.toJSONString(ordTemplateManager.getNodeTreeByTpl(tepletaParam.getId()));
		return JSON_MESSAGE;
	}
	
	/**
	 * 打开一键设置界面
	 * @author chen.zuoqin
	 * @return
	 */
	public String setupTree(){
		
		return "setup_tree";
	}
	
	/**
	 * 打开一键设置界面
	 * @author chen.zuoqin
	 * @return
	 */
	public String setupTreeNode(){
		try {
			String id=tepletaParam.getId();
			String tableCode=tepletaParam.getNodeName();
			String retStr="";
			List<OrderTemplateNode> listTeplNode=ordTemplateManager.getChildNodeTreeByTpl(id);
			List tableList=ordTemplateManager.getAttrTableList(tableCode);
			if(listTeplNode!=null&&!listTeplNode.isEmpty()&&tableList!=null&&!tableList.isEmpty()){
				Iterator nodeIt=listTeplNode.iterator();
				while(nodeIt.hasNext()){
					OrderTemplateNode node=(OrderTemplateNode)nodeIt.next();
					String nodeId=node.getNode_id();
					String nodeCode=node.getNode_code();
					for(int j=0;j<tableList.size();j++){
						Map table=(Map)tableList.get(j);
						String fieldCode=(String)table.get("field_name");
						String tableName=(String)table.get("table_name");
						if(fieldCode.equalsIgnoreCase(nodeCode)){
							ordTemplateManager.updateNodeTable(nodeId, tableName, fieldCode);
							retStr=retStr+nodeId+","+tableName+","+fieldCode+";";
						}
					}
				}
			}
			
			json = "{'result':0,'message':'操作成功！','retStr':'"+retStr+"'}";
		} catch (Exception e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}
		
		return JSON_MESSAGE;
	}
	
	public String getNodeModel(){
		Map<String, NodeModel> nodeMap=ordTemplateManager.getNodeModel("20150308163431100108","");
		logger.info(nodeMap.toString());
		String str=NodeModelParseUtil.mapToXml(nodeMap);
		json = "{'result':0,'message':'操作成功！'}";
		return JSON_MESSAGE;
	}
	/**
	 * 打开
	 * @return
	 */
	public String getOrdTplTree(){
		
		return "node_tree";
	}
	public String selectTempleteComp(){
		//new对象解决空指针问题
		if(tepletaParam.getEsAttrDef()==null){
			EsAttrDef def=new EsAttrDef();
			tepletaParam.setEsAttrDef(def);
		}
		//查询所有的数据
		this.webpage = ordTemplateManager.selectEsAttrDefByTable(tepletaParam, this.getPage(), this.getPageSize());
		return "selectTempleteComp";
	}
	
	public String selectAttrDef(){
		//new对象解决空指针问题
		if(tepletaParam.getEsAttrDef()==null){
			EsAttrDef def=new EsAttrDef();
			tepletaParam.setEsAttrDef(def);
		}
		//查询所有的数据
		this.webpage = ordTemplateManager.selectEsAttrDefByTable(tepletaParam, this.getPage(), this.getPageSize());
		return "selectAttrDef";
	}
	
	//加载模型驱动
	@Override
	public TepletaParam getModel() {
		if(tepletaParam==null){
			tepletaParam=new TepletaParam();
		}
		return tepletaParam;
	}
	
//	public String selectOrderTemplateByCatId(){
//		OrderTemplate template=ordTemplateManager.selectOrderTemplateByCatId(ordTplDTO);
//		//然后进行分页查询
//		this.webpage=this.ordTemplateManager.queryTplList(this.getPage(), 5, ordTplDTO);
//		return "selectTempleteComp";
//	}
	
	//一键设置action
	public String udpateNodeTableCode(){
		try {
			//从界面获取表单数据
			String id=tepletaParam.getId();
			if(id!=null&&id.length()>0){
				//直接转换为JSON数组
			    List<OrderTemplateNode> listTeplNode=CommonTools.jsonToList(id, OrderTemplateNode.class);
				for(OrderTemplateNode node:listTeplNode){
					ordTemplateManager.udpateNodeTableCode(node);
				}
					json = "{'result':0,'message':'操作成功！'}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = "{'result':1,'message':'操作失败！'}";
		}
		return JSON_MESSAGE;
	}
	
	public String getJsonPage (){
		return "json_format";
	}
	

	public String saveAKeySet(){
		//根据表名获取对应的字段
		AttrDef vo =new AttrDef();
		vo.setRel_table_name(tepletaParam.getEsAttrDef().getRel_table_name());
		List<AttrDef>  listAttrDef=  ordTemplateManager.selectEsAttrDefVO( vo);
		if(listAttrDef!=null&&listAttrDef.size()>0){
			for(AttrDef def:listAttrDef){
				//从界面获取对应的值
			}
		}
		logger.info(tepletaParam.getId());
		List<OrderTemplateNode> listTeplNode=CommonTools.jsonToList(tepletaParam.getId(), OrderTemplateNode.class);
		logger.info(listTeplNode);
		return "";
	}
	
	public IOrdTemplateManager getOrdTemplateManager() {
		return ordTemplateManager;
	}

	public void setOrdTemplateManager(IOrdTemplateManager ordTemplateManager) {
		this.ordTemplateManager = ordTemplateManager;
	}
	
	public String getTplMain(){
			return "tpl_index";
	}
	 

	public TepletaParam getTepletaParam() {
		return tepletaParam;
	}

	public void setTepletaParam(TepletaParam tepletaParam) {
		this.tepletaParam = tepletaParam;
	}

	@Override
	public String getJson() {
		return json;
	}

	@Override
	public void setJson(String json) {
		this.json = json;
	}
	public static void main(String[] args) {
		OrdTemplateAction o=new OrdTemplateAction();
		o.refrushTempleteComp();
	}
	
	//跳转到目录树界面
	public String toCatalogTree(){
		return "catalog_tree";
	}
}
