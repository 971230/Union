package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.app.base.core.model.Agent;
import com.ztesoft.net.framework.database.Page;


/**
 * 商户申请
 * @author wui
 */
public interface IAgentManager {
	/**
	 * 添加商户申请
	 * 
	 * @param member
	 * @return 0：用户名已存在，1：添加成功
	 */
	public int add(Agent agent);
	
	
	public List<Map> list();
	
	
	public List<Map> agentList();
	
	public void edit(Agent agent);
	
	public void delAgentById(String agent_id);
	
	
	public void  approve(Integer[] ids);
	
	public Page searchAgents(String username,String state,String order,int page,int pageSize);
	
	public Page searchStaff(String staff_no,String staff_name,int page,int pageSize); //查询工号
	
	public Page searchStaff2(String staff_no, String staff_name, int page,
			int pageSize);//查询代理商
	public List searchStaffList();
	public Page searchStaff3(Map map, int page,int pageSize);//查找CRM
	
	public Map getAgentById(String agent_id);
}