package com.ztesoft.net.mall.core.service.impl;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.OrderGroupDef;
import com.ztesoft.net.mall.core.model.OrderGroupRel;
import com.ztesoft.net.mall.core.service.IOrderGroupManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class OrderGroupManagerImpl extends BaseSupport implements IOrderGroupManager{

	@Override
	public void deleteOrderGroupDef(String group_id) {
		// TODO Auto-generated method stub
		String sql ="update es_order_group_def set disabled =1 where group_id=?";
		this.baseDaoSupport.execute(sql, group_id);
	}

	@Override
	public void insertOrderGroupDef(OrderGroupDef orderGroupDef) {
		// TODO Auto-generated method stub
		if("qr".equals(orderGroupDef.getGroup_type())){
			orderGroupDef.setGroup_type("confirm");
		}
		String group_id =this.baseDaoSupport.getSequences("ES_ORDER_GROUP_SEQ");
		orderGroupDef.setCreate_time(DBTUtil.current());
		orderGroupDef.setDisabled("0");
		orderGroupDef.setOper_id(ManagerUtils.getUserId());
		orderGroupDef.setGroup_id(group_id);
		
		this.baseDaoSupport.insert("es_order_group_def", orderGroupDef);
	}

	@Override
	public void insertOrderGroupRel(OrderGroupRel orderGroupRel) {
		// TODO Auto-generated method stub
		this.baseDaoSupport.insert("es_order_group_role_rel", orderGroupRel);
	}

	@Override
	public void updateOrderGroupRel(String userid,String role_id,String group_id) {
		// TODO Auto-generated method stub
	   String sql ="update es_order_group_role_rel set userid =?,role_id=? where group_id=?";
	   this.baseDaoSupport.execute(sql, userid,role_id,group_id);
	}

	@Override
	public Page GroupDefList(String group_name,String group_type, int pageSize, int pageNo) {
		// TODO Auto-generated method stub
		String oper_id = ManagerUtils.getUserId();
		Integer founder = ManagerUtils.getFounder();
        
		String sql ="select * from es_order_group_def where disabled=0 ";
        if(founder != Consts.CURR_FOUNDER0 && founder != Consts.CURR_FOUNDER1){
        	sql += "and oper_id ="+oper_id;
        }
		if(StringUtils.isNotBlank(group_name)){
			sql +=" and group_name like '%"+group_name+"%'";
		}
		if(StringUtils.isNotBlank(group_type)&&!"0".equals(group_type)){
			sql +=" and group_type like '%"+group_type+"%'";
		}
		sql += " order by create_time desc";
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, OrderGroupDef.class);
	}

	@Override
	public List getOrderGroupRole(String group_id) {
		// TODO Auto-generated method stub
		String sql ="select role_id from  es_order_group_role_rel where group_id ="+group_id;
		String roleIdStr = this.baseDaoSupport.queryForString(sql);
		if(StringUtils.isNotBlank(roleIdStr)){
			String sqlStr = "select * from es_role where roleid in ("+roleIdStr+")";
			List list = this.baseDaoSupport.queryForList(sqlStr);
		    return list;
		}else{
		    return null;
		}
	}

	@Override
	public void saveOrderGroupRelRole(OrderGroupRel orderGroupRel) {
		// TODO Auto-generated method stub
		String group_id = orderGroupRel.getGroup_id();
		String roleIdStr = orderGroupRel.getRole_id();
		String sql = "select count(*) from es_order_group_role_rel where group_id=?";
		int count = 0 ;
		count = this.baseDaoSupport.queryForInt(sql, group_id);
		if(count>0){
			String updateSql = "update es_order_group_role_rel set role_id =? where group_id=?";
			this.baseDaoSupport.execute(updateSql, roleIdStr,group_id);
		}
		if(count==0){
			String insertSql ="insert into es_order_group_role_rel(group_id,role_id) values(?,?)";
			this.baseDaoSupport.execute(insertSql, group_id,roleIdStr);
		}
		
	}

	@Override
	public List getOrderGroupUser(String group_id) {
		// TODO Auto-generated method stub
		String sql ="select userid from  es_order_group_role_rel where group_id ="+group_id;
		String userIdStr = this.baseDaoSupport.queryForString(sql);
		if(userIdStr!=null&&!"".equals(userIdStr)){
			String sqlStr = "select * from es_adminuser where userid in ("+userIdStr+")";
			List list = this.baseDaoSupport.queryForList(sqlStr);
		    return list;
		}else{
		    return null;
		}
	}

	@Override
	public Page getUserList(String username, String realname, int pageSize,
			int pageNo) {
		// TODO Auto-generated method stub
		String sql ="select userid,username,realname from es_adminuser where state=1";
		if(StringUtils.isNotBlank(realname)){
			sql += " and realname like '%"+realname+"%'";
		}
		if(StringUtils.isNotBlank(username)){
			sql += " and username like '%"+username+"%'";
		}
		return  this.baseDaoSupport.queryForPage(sql, pageNo, pageSize);
	}

	@Override
	public void saveOrderGroupRelUser(OrderGroupRel orderGroupRel) {
		// TODO Auto-generated method stub
		String group_id = orderGroupRel.getGroup_id();
		String userIdStr = orderGroupRel.getUserid();
		String sql = "select count(*) from es_order_group_role_rel where group_id=?";
		int count = 0 ;
		count = this.baseDaoSupport.queryForInt(sql, group_id);
		if(count>0){
			String updateSql = "update es_order_group_role_rel set userid =? where group_id=?";
			this.baseDaoSupport.execute(updateSql, userIdStr,group_id);
		}
		if(count==0){
			String insertSql ="insert into es_order_group_role_rel(group_id,userid) values(?,?)";
			this.baseDaoSupport.execute(insertSql, group_id,userIdStr);
		}
	}

	@Override
	public int getCountByGroupCode(String group_code) {
		// TODO Auto-generated method stub
		int count = 0; 
		String sql ="select count(*) from es_order_group_def where disabled=0 and group_code=?";
		count = this.baseDaoSupport.queryForInt(sql, group_code);
		return count;
	}

	@Override
	public int getCountByGroupName(String groupName) {
		// TODO Auto-generated method stub
		int count = 0; 
		String sql ="select count(*) from es_order_group_def where disabled=0 and group_name=?";
		count = this.baseDaoSupport.queryForInt(sql, groupName);
		return count;
	}

}
