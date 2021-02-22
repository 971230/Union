package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Task;

public interface ITaskManager {

	/**
	 * 查询任务列表
	 * @param node_level
	 * @param org_id
	 * @param lan_code
	 * @param city_code
	 * @param task_type
	 * @param task_name
	 * @param state
	 * @param finished
	 * @param create_time
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page searchTaskList(String node_level,String org_id,String lan_code,String city_code,String task_type,String task_name,String state,String finished,String create_time,int pageNum,int pageSize);
	public Page searchTaskList(String lan_id, String node_level,String org_id,String lan_code,String city_code,String task_type,String task_name,String state,String finished,String create_time,int pageNum,int pageSize);
	/**
	 * 根据task_id查询任务信息
	 * @param task_id
	 * @return
	 */
	public Map searchTaskByTaskId(String task_id);
	/**
	 * 查询分销商列表
	 * @return
	 */
	public List searchAdminUser(String username);
	
	public List searchUserCondi(String username);
	
	public String getRegionByLanId(String lan_id);
	/**
	 * 任务录入保存
	 * @param task
	 */
	public void saveAdd(Task task);
	/**
	 * 任务编辑保存
	 * @param task
	 */
	public void saveEdit(Task task);
	
	public void savePartnerTask(List datas);
	
	/**
	 * 管理员查询分销商任务完成情况
	 * */
	public Page qryTaskDetail(String task_name, int pageIndex, int pageSize);
	/**
	 * 分销商任务完成情况
	 * */
	public Page qryTaskDetail(String userid, String task_name, int pageIndex, int pageSize);
	
	/**
	 * 分销商任务提醒
	 * */
	public Map qryTotalDetail(String userid);
	
	/**
	 * 点击任务提醒查询任务列表
	 * */
	public Page qryTask(String flag, String userid, int pageIndex, int pageSize);
	
	/**
	 * 任务具体情况查看 
	 * */
	public List viewTaskDetail(String task_id, String detail_id);
	
	/**
	 * 查询下发任务列表
	 * */
	public Page assignTask(String task_name, int pageIndex, int pageSize);
	
	/**
	 * 查询每个任务列表
	 * */
	public List qryEachTaskDetail(String task_id);
	/**
	 * 根据task_id查询具体任务信息
	 * */
	public Map qryTaskById(String task_id);
	
	/**
	 * 下发
	 * */
	public String confirmSend(String task_id);
	
	public String finishTask(String task_id);
	
	public List showTaskDetailGroupByRegion(String task_id);
	
	public List showTaskDetailGroupByPartner(String task_id);
	
	/**
	 * 撤销任务
	 * */
	public String revokeTask(String task_id);
	
	/**
	 * 删除任务
	 * */
	public String cancelTask(String task_id);
	
	/**
	 * 查询要撤销的任务
	 * */
	public Page qryRevokeList(String task_name, int pageIndex, int pageSize);
	
	/**
	 * 查询要删除的任务
	 * */
	public Page qryCancelList(String task_name, int pageIndex, int pageSize);
	
	public List qryServiceList();
	
	public Page getPTask(String lan_id, String city_id, int founder, int pageIndex, int pageSize);
	
	public String qryLanNamebyCode(String lan_id);
	
	public String qryPTaskName(String task_id);
	
	public Page getPartner(AdminUser adminUser, String lan_id, String city_id, int pageInx, int PageSize);
	
	public List searchPartnerList(String task_id);
	
	public void deleSplitTaskDetail(String task_id);
	
	public List getRootOrg();
	
	public List getCurrOrg(String login_org_id);
	
	public int qryChildrenOrgCount(String org_id);
	public List getChildrenOrg(String org_id);
}
