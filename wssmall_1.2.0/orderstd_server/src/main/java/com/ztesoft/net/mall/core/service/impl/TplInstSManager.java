package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.service.ITplInstSManager;
import com.ztesoft.net.model.AttrDef;
import com.ztesoft.net.model.AttrInst;

public class TplInstSManager extends BaseSupport implements ITplInstSManager{

	/**e
	 * 分组处理
	 * @param inMap
	 * @param temp_inst_id
	 * @param tableInstAttrs
	 * @param attrInstAttr
	 */
	@Override
	public void groupAttrInsts(List<AttrInst> oAttrInsts,Map<String ,List<AttrInst>> tableAttrInsts,List<AttrInst> propAttrInsts,String sub_attr_spec_type){
	
		//分组处理
		for (int i = 0; i < oAttrInsts.size(); i++) {
			AttrInst oAttrInst =oAttrInsts.get(i);
			AttrDef attrDef = getAttrDef(oAttrInst.getField_attr_id());
			
			//指定类型的数据
			if(!sub_attr_spec_type.equals(attrDef.getSub_attr_spec_type()))
				continue;
			
			String table_name =attrDef.getRel_table_name();
			if(!StringUtils.isEmpty(table_name) && "yes".equals(attrDef.getOwner_table_fields())){ //属性字段归属受理表的
				if(tableAttrInsts.get(table_name) ==null )
				{
					List<AttrInst> tList = new ArrayList<AttrInst>();
					tableAttrInsts.put(table_name,tList);
				}
				List<AttrInst> tpList = tableAttrInsts.get(table_name);
				tpList.add(oAttrInst);
			}else {
				propAttrInsts.add(oAttrInst);
			}
		}
		
	}
	
	/**
	 * 保存属性受理数据
	 * @param inMap
	 * @param tableInstAttrs
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void saveTableInst(Order order,OrderItem orderItem,Map<String, List<AttrInst>> tableInstAttrs,Map<String, String> tableInstFkeys) {
		
		Iterator it = tableInstAttrs.keySet().iterator();
		
		if(tableInstAttrs.size()==0){
//	    	logger.info("========================================>tableInstAttrs is null ");
			return;
		}
		//循环表名
		while (it.hasNext()) {
			String table_name = (String) it.next();
			List<AttrInst> tLists  = tableInstAttrs.get(table_name);
			
			StringBuffer insertSQL = new StringBuffer();
			StringBuffer whereSQL = new StringBuffer();
			insertSQL.append("insert into  ").append(table_name).append("(inst_id,order_id,item_id,product_id,state,user_id");
			whereSQL.append("(").append("?,?,?,?,?,?");
			
			List sqlParams =new ArrayList();
			String inst_id =this.baseDaoSupport.getSeqByTableName("ES_ORDER_ACCEPT"); //获取受理序列
			sqlParams.add(inst_id);
			sqlParams.add(orderItem.getOrder_id());
			sqlParams.add(orderItem.getItem_id());
			sqlParams.add(orderItem.getProduct_id());
			sqlParams.add(OrderStatus.ACCEPT_STATUS_0+"");
			String accept_user_id =order.getAccept_user_id();
			if(StringUtils.isEmpty(accept_user_id))
				accept_user_id = "";
			sqlParams.add(accept_user_id);
			
			tableInstFkeys.put(table_name, inst_id);
			String prefix =",";
			
			//获取表名属性数据
			for (int i = 0; i < tLists.size(); i++) {
				AttrInst attrInst = tLists.get(i);
				
				//if(i==tLists.size()-1)
				//	prefix ="";
				insertSQL.append(prefix).append(attrInst.getField_name());
				whereSQL.append(prefix).append("?");
				sqlParams.add(attrInst.getField_value());
			}
			insertSQL.append(",create_time) values ");
			whereSQL.append(","+DBTUtil.current()+")");
			String insertSql = insertSQL.toString()+whereSQL.toString();
			
			//保存数据信息
			this.baseDaoSupport.execute(insertSql, sqlParams.toArray());
			/*String sql = "insert into es_order_items_pay (inst_id, order_id, item_id, product_id, state, "+
				" create_time, app_code, phone_num, opn_code, from_url, opn_session_id, "+
				" dev_staff_id, dev_group_id, from_ord_code, item_type) values "+
				" (201310127643000020, 201310128574003659, 201310127222003485, 201310119014001572, "+
				" 0, sysdate, 'app_code',' phone_num', 'opn_code', 'from_url', 'opn_session_id',' "+
				" dev_staff_id', 'dev_group_id', 'from_ord_code', 'item_type')";
			this.baseDaoSupport.execute(sql);*/
			
		}
	}
	
	/**
	 * 保存属性实例数据
	 * @param inMap
	 * @param tableInstAttrs
	 */
	@Override
	public void saveAttrInst(OrderItem orderItem,List<AttrInst> propAttrInsts,Map<String,String> tableInstFkeys) {
		
		//def
		if(propAttrInsts.size()==0)
			return;
		
		for (int i = 0; i < propAttrInsts.size(); i++) {
			AttrInst attrInst = propAttrInsts.get(i);
			//attrInst.getAttr_spec_id()
			attrInst.setInst_id(tableInstFkeys.get(this.getAttrDef(attrInst.getField_attr_id()).getRel_table_name()));
			attrInst.setOrder_item_id(orderItem.getItem_id());
			attrInst.setOrder_id(orderItem.getOrder_id());
			attrInst.setCreate_date(DBTUtil.current());
			this.baseDaoSupport.insert("es_attr_inst", attrInst);
			
		}
	}
	private AttrDef getAttrDef(String field_attr_id){
		String sql = "select * from es_attr_def where field_attr_id = '"+field_attr_id+"'";
		return (AttrDef) this.baseDaoSupport.queryForObject(sql, AttrDef.class);
	}
	@Override
	public List<AttrInst> getOuterAttrInst(String order_id){
		String sql = "select * from es_outer_attr_inst where order_id = '"+order_id+"'";
		return this.baseDaoSupport.queryForList(sql, AttrInst.class);
	}
}