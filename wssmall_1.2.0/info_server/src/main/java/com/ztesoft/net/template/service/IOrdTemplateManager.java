package com.ztesoft.net.template.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.model.AttrDef;
import com.ztesoft.net.model.AttrTable;
import com.ztesoft.net.template.model.Catalogue;
import com.ztesoft.net.template.model.NodeModel;
import com.ztesoft.net.template.model.OrderTemplate;
import com.ztesoft.net.template.model.OrderTemplateNode;
import com.ztesoft.net.template.vo.OrdTplDTO;
import com.ztesoft.net.template.vo.TepletaParam;

public interface IOrdTemplateManager {
	/**
	 * 查询根节点
	 * @param id
	 * @author chen.zuoqin
	 * @return
	 */
	List<OrderTemplateNode> qryTemplateTree(String id,String super_id);
	
	/**
	 * 查询节点信息
	 * @param id
	 * @author chen.zuoqin
	 * @return
	 */
	OrderTemplateNode qryTemplateNode(String id);
	
	/**
	 * 保存节点信息
	 * @author chen.zuoqin
	 * @param orderTemplateNode
	 */
	void saveTemplateNode(OrderTemplateNode orderTemplateNode);
	
	/**
	 * 编辑节点
	 * @author chen.zuoqin
	 * @param orderTemplateNode
	 */
	void editTemplateNode(OrderTemplateNode orderTemplateNode);
	
	/**
	 * 删除节点
	 * @author chen.zuoqin
	 * @param id
	 */
	void delTemplateNode(String id);
	
	/**
	 * 通过模板id删除节点
	 * @author chen.zuoqin
	 * @param id
	 */
	public void delTemplateNodeByTplId(String tplId);

	/**
	 * 查询目录树节点
	 * @author chen.zuoqin
	 * @return
	 */
	List<Catalogue> qryRootCatalogue(String id,String pid);
	
	/**
	 * 保存目录
	 * @author chen.zuoqin
	 * @param catalogue
	 */
	void saveCatalogue(Catalogue catalogue);
	
	/**
	 * 编辑目录
	 * @author chen.zuoqin
	 * @param catalogue
	 */
	void editCatalogue(Catalogue catalogue);
	
	/**
	 * 删除目录
	 * @author chen.zuoqin
	 * @param catalogue
	 */
	void delCatalogue(String id);
	
	/**
	 * 根据目录ID获取目录下的模板列表
	 * @author chen.zuoqin
	 * @param id
	 * @return
	 */
	public List<OrderTemplate> getOrderTemplateList(String id);
	
	/**
	 * 根据ID查询树对象
	 * @author chen.zuoqin
	 * @param id
	 * @return
	 */
	public Catalogue qryCatalogue(String id);
	
	/**
	 * 复制节点树
	 * @author chen.zuoqin
	 * @param id
	 * @return
	 */
	public String copyOrderTpl(String id);
	
	/**
	 * 根据模板ID获取树（新的节点树）
	 * @author chen.zuoqin
	 * @param id
	 * @return
	 */
	public List<OrderTemplateNode> getNodeTreeByTpl(String id);
	
	/**
	 * 改变序号
	 * @author chen.zuoqin
	 * @param nodeId
	 * @param nodeSeq
	 */
	public void updateNodeSeq(String nodeId,String nodeSeq);
	
	/**
	 * 根据ID获取子节点树
	 * @author chen.zuoqin
	 * @param id
	 * @return
	 */
	public  List<OrderTemplateNode> getChildNodeTreeByTpl(String id);
	
	/**
	 * 修改table_code和field_code
	 * @author chen.zuoqin
	 * @param node_id
	 * @param table_code
	 * @param field_code
	 */
	public void updateNodeTable(String node_id,String table_code,String field_code);
	
	/**
	 * 获取表字段
	 * @author chen.zuoqin
	 * @param tableName
	 * @return
	 */
	public List getAttrTableList(String tableName);
	
	/**
	 * 获取节点Map
	 * @author chen.zuoqin
	 * @return
	 */
	public Map<String,NodeModel> getNodeModel(String orderTemplateId,String nodeId);
	//获取序列
	public String getEsAttrDefSequence();
	
	/**
	 * 查询EsAttrDefVO对象
	 * 
	 * Copyright (c) 2015, 南京中兴软创科技股份有限公司 All rights reserved
	 * @author      : 张金叶
	 * @createTime  : 2015-3-3
	 * @version     : 1.0
	 */
	public  List<AttrDef>  selectEsAttrDefVO(AttrDef vo);
	
	/**
	 * 插入数据
	 * 
	 * Copyright (c) 2015, 南京中兴软创科技股份有限公司 All rights reserved
	 * @author      : 张金叶
	 * @createTime  : 2015-3-3
	 * @version     : 1.0
	 */
	public void insertEsAttrDef(AttrDef vo);
	
	/**
	 * 更新es_attr_def数据
	 * 
	 * Copyright (c) 2015, 南京中兴软创科技股份有限公司 All rights reserved
	 * @author      : 张金叶
	 * @createTime  : 2015-3-4
	 * @version     : 1.0
	 */
	public void updateEsAttrDef(AttrDef vo);
	
	/**
	 * 删除es_attr_def数据
	 * 
	 * Copyright (c) 2015, 南京中兴软创科技股份有限公司 All rights reserved
	 * @author      : 张金叶
	 * @createTime  : 2015-3-4
	 * @version     : 1.0
	 */
	public void deleteEsAttrDef(AttrDef vo);
	
	//查询分页参数
	public Page selectEsAttrDefByTable(TepletaParam vo,int pageNo, int pageSize);
	
	/**
	 * 查询模板列表
	 * @param pageNo
	 * @param pageSize
	 * @param ordTplDTO 查询条件
	 * @return
	 */
	public Page queryTplList(int pageNo,int pageSize,OrdTplDTO ordTplDTO);
	
	/**
	 * 根据模板编码查询
	 * @return
	 */
	public OrdTplDTO queryTplByCode(OrdTplDTO ordTplDTO);
	
	/**
	 * 根据模板code和目录id查询
	 * @param tplDto
	 * @return
	 */
	public OrdTplDTO qryTplByCodeAndCataId(OrdTplDTO tplDto);
	
	/**
	 * 根据模板id查询
	 * @return
	 */
	public OrdTplDTO queryTplById(OrdTplDTO ordTplDTO);
	
	/**
	 * 添加一条模板
	 * @param ordTpl
	 */
	public void insertTpl(OrdTplDTO ordTplDTO);
	
	/**
	 * 添加主模板日志
	 * @param ordTplDTO:模板对象
	 */
	public void insertTplLog(OrdTplDTO ordTplDTO);
	
	/**
	 * 粘贴模板入库
	 */
	public String insertTplNode(String jsonStr);
	
	/**
	 * 修改模板信息
	 * @param ordTpl
	 */
	public void updateTpl(OrdTplDTO ordTplDTO);
	
	/**
	 * 删除一条模板
	 */
	public void deleteTpl(OrdTplDTO ordTplDTO);
	
	public  List<AttrTable>  selectEsAttrTableVO(AttrTable vo);
	
	public void insertEsAttrTable(AttrTable vo);
	
	public void deleteEsAttrTable(AttrTable vo);
	
	
	public void udpateNodeTableCode(OrderTemplateNode node);
}