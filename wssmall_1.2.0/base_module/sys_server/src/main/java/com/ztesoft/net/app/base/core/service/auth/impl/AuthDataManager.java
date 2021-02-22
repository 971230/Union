package com.ztesoft.net.app.base.core.service.auth.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.adminuser.req.AuthPageReq;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.app.base.core.model.AuthData;
import com.ztesoft.net.app.base.core.model.RoleData;
import com.ztesoft.net.app.base.core.service.auth.IAuthDataManager;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import commons.CommonTools;

public class AuthDataManager extends BaseSupport<AuthData> implements IAuthDataManager {

	@Override
	public Page authListPage(AuthPageReq authPageReq) {
		int pageIndex = authPageReq.getPage();
		int pageSize = authPageReq.getPageSize();
		String sql = "select a.role_id role_id,"+
					"                a.auth_id,"+
					"                a.name role_name,"+
					"                r.rolememo role_desc,"+
					"                a.type,"+
					"                a.createtime create_time"+
					"  from (select actid auth_id, actid role_id, type, createtime,name"+
					"          from es_auth_action"+
					"         where source_from = ?"+
					"        union all"+
					"        select d.id auth_id, d.role_code role_id, 'data' type, d.create_time,d.role_code name"+
					"          from es_role_data d " +
					"          left join es_role_auth a1 on a1.authid=d.id  "+
					"         where d.source_from = ?) a"+
					"  left join es_role r on r.roleid = a.role_id where 1=1 ";
		List arr = new ArrayList();
		arr.add(CommonTools.getSourceForm());
		arr.add(CommonTools.getSourceForm());
		if(!StringUtil.isEmpty(authPageReq.getName())){
			sql+= " and a.name like '%"+authPageReq.getName()+"%'";
		}
		if(!StringUtil.isEmpty(authPageReq.getAct_id())){
			sql+= "and a.auth_id = ?";
			arr.add(authPageReq.getAct_id());
		}
		if(!StringUtil.isEmpty(authPageReq.getType())){
			sql+= "and a.type = ?";
			arr.add(authPageReq.getType());
		}
		String countSql = "select count(1) from ("+sql+")";
		Page page = this.baseDaoSupport.queryForCPage(sql, pageIndex, pageSize, AuthData.class, countSql, arr.toArray());   
		return page; 
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String add(RoleData roleData) {
		/*AuthPageReq authPageReq = new AuthPageReq();
		authPageReq.setName(roleData.getRole_code());
		authPageReq.setPage(1);
		authPageReq.setPageSize(10);
		Page page = authListPage(authPageReq) ;
		if(null != page && page.getTotalCount()>0){
			roleData.setRole_code(roleData.getRole_code()+"("+(page.getTotalCount())+")");
		}*/
		if(StringUtil.isEmpty(roleData.getId())){
			roleData.setId(this.baseDaoSupport.getSequences("S_ES_AUTH_ACTION"));
		}
		roleData.setCreate_time(DBTUtil.current());
		roleData.setCreate_user(CommonTools.getUserId());
		roleData.setUserid(CommonTools.getUserId());
		this.baseDaoSupport.insert("es_role_data", roleData);
		return roleData.getId();
	}
	
	private boolean checkRoleData(String name){
		String sql = "select d.id auth_id, d.role_code role_id, 'data' type, d.create_time,d.role_code name"+
					 "  from es_role_data d " +
					 " where d.source_from = ? and d.role_code=?";
		List list = this.baseDaoSupport.queryForList(sql, ManagerUtils.getSourceFrom(),name);
		return list.size()>0;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void edit(RoleData roleData) {
		if(StringUtil.isEmpty(roleData.getId())){
			return ;
		}
		roleData.setUpdate_time(DBTUtil.current());
		roleData.setUpdate_user(CommonTools.getUserId());
		Map params = new HashMap();
		try {
			BeanUtils.beanToMap(params, roleData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sql = "update es_role_data set ";
		Iterator it = params.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			Object value = params.get(key);
			
			if(!"id".equals(key) && !"ROLEMEMO".equalsIgnoreCase(key) && !"ROLENAME".equalsIgnoreCase(key)){
				if("sysdate".equals(value)){
					sql += key + " = "+value+",";
				}
				else if(value instanceof String){
					sql += key + " = '"+value+"',";
				}
				else if(value instanceof Integer || value instanceof Double || value instanceof Float){
					sql += key + " = "+value+",";
				}
			}
		}
		sql = sql.substring(0, sql.length()-1);
		sql+=" where id='"+roleData.getId()+"' ";
		this.baseDaoSupport.update(sql, params);
		//this.baseDaoSupport.update("es_role_data", params, wheresql);
	}

	@Override
	public void delete(String id) {
		//删除角色权限表中对应的数据
		this.baseDaoSupport.execute("delete from es_role_data where id=?", id);
		//删除权限基本数据
		this.baseDaoSupport.execute("delete from es_auth_action where actid=?", id);
	}
}
