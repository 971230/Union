package com.ztesoft.net.mall.core.action.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import params.adminuser.req.MessageCountReq;
import params.adminuser.resp.MessageCountResp;
import services.AdminUserInf;
import services.MessageInf;

import com.alibaba.fastjson.JSON;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.cache.ITaskCache;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Task;
import com.ztesoft.net.mall.core.model.TaskDetail;
import com.ztesoft.net.mall.core.service.ITaskManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class TaskAction extends WWAction {

	private String task_id;
	private String node_level;
	private String lan_code;
	private String city_code;
	private String task_name;
	private String task_type;
	private String task_type_name;
	private String task_ids;
	private String lan_ids;
	private String city_ids;
	private String userids;
	private String task_nums;
	private String create_time;
	private String state;
	private String finished;
	private String org_id;
	private String parent_id;
	private String groupBy;//查看任务完成情况-按区域，按分销商
	private Task task;
	private TaskDetail taskDetail;
	private List taskList;
	private List partner_list;
	private List catList;
	private AdminUser adminUser;
	private ITaskManager taskManager;
	private MessageInf messageServ;
	
	private String username;
	private String realname;
	private String lan_name;
	private String p_task_name;
	private String taskFormActionVal = "task!searchTaskList.do";
	private String taskAddFormActionVal = "task!saveAdd.do";
	private AdminUserInf adminUserServ;
	
	private String party_state;
	
	private ITaskCache taskCache;
	
	public String refreshCache(){
		try{
			taskCache.cacheAllJob();
			json = "{status:0,msg:'刷新成功'}";
		}catch(Exception ex){
			json = "{status:1,msg:'刷新失败'}";
		}
		return JSON_MESSAGE;
	}
	
	public String taskList(){
		
		return "task1";
	}
	
	/**
	 * 查询任务信息列表
	 * @return
	 */
	public String searchTaskList(){
		String s = node_level;
		this.adminUser = ManagerUtils.getAdminUser();
		String lan_id = this.adminUser.getLan_id();
		String city_id = this.adminUser.getCity_id();
		if(StringUtils.isNotEmpty(city_id)){
			this.webpage = taskManager.searchTaskList(node_level,org_id,lan_code,city_code,task_type,task_name,state,finished,create_time,  this.getPage(), this.getPageSize());
		}else{
			this.webpage = taskManager.searchTaskList(lan_id, node_level,org_id,lan_code,city_code,task_type,task_name,state,finished,create_time,  this.getPage(), this.getPageSize());
		}
		List<Task> list = this.webpage.getResult();
		return "taskList";
	}
	
	/**
	 * 去往任务录入界面
	 * @return
	 */
	public String addTask(){
		
		int founder = ManagerUtils.getAdminUser().getFounder().intValue();
		if(founder != 1){
			lan_code = ManagerUtils.getAdminUser().getLan_id();
			lan_name = taskManager.qryLanNamebyCode(lan_code);
		}
		catList = taskManager.qryServiceList();
		
		return "edit_task";
	}
	
	/**
	 * 新录入保存任务信息
	 * @return
	 */
	public String saveAdd(){
		AdminUser admin = ManagerUtils.getAdminUser();
		String userid = admin.getUserid();
		task.setOp_id(userid);
		
		taskManager.saveAdd(task);
		this.json = "{'result':0,'message':'任务录入成功！'}";
		return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 去往修改任务信息界面，初始化要修改的任务信息
	 * @return
	 */
	public String edit(){
		Map result = taskManager.searchTaskByTaskId(task_id);
		catList = taskManager.qryServiceList();
		p_task_name = taskManager.qryPTaskName(task_id);
		try{
			this.taskAddFormActionVal = "task!saveEdit.do";
			task = new Task();
			BeanUtils.copyProperties(task, result);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "edit_task";
	}
	
	/**
	 * 修改保存任务信息
	 * @return
	 */
	public String saveEdit(){
		taskManager.saveEdit(task);
		this.json = "{'result':0,'message':'任务修改成功！'}";
		return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 去往任务分解界面，初始化相关数据
	 * @return
	 */
	public String taskSplit(){
		try{
			Map result = taskManager.searchTaskByTaskId(task_id);
			task = new Task();
			BeanUtils.copyProperties(task, result);
			
			//catList = taskManager.searchAdminUser(null);
		//	catList = taskManager.searchUserCondi(null);
			catList = taskManager.searchPartnerList(task_id);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "taskSplit";
	}
	/**
	 * 获取分销商列表
	 * @return
	 */
	public String partnerList(){
		String username = adminUser==null?null:adminUser.getUsername();
		catList = taskManager.searchAdminUser(username);
		return "partner_list";
	}
	
	/**
	 * 获取任务区域对应分销商
	 * */
	public String getPartner(){
		if(StringUtils.isNotEmpty(task_id)){
			Map map = taskManager.searchTaskByTaskId(task_id);
			if(map != null){
				if(adminUser == null) adminUser = new AdminUser();
				adminUser.setUsername(username);
				adminUser.setRealname(realname);
				String lan_id = Const.getStrValue(map, "lan_code");
				String city_id = Const.getStrValue(map, "city_code");
				this.webpage = taskManager.getPartner(adminUser, lan_id, city_id, getPage(), getPageSize());
			}
		}
		
		return "partner_list";
	}
	/**
	 * 保存任务分解数据
	 * @return
	 */
	public String savePartnerTask(){
		//System.out.print("dddddddddddddd");
		String[] taskIds = task_ids != null ? task_ids.substring(0, task_ids.length()-1).split(",") : null;
		String[] userIds = userids != null ? userids.substring(0, userids.length()-1).split(",") : null;
		String[] lanIds = lan_ids != null ? lan_ids.substring(0, lan_ids.length()-1).split(",") : null;
		String[] cityIds = city_ids != null ? city_ids.substring(0,city_ids.length() - 1).split(",") : null;
		String[] taskNums = task_nums!=null ? task_nums.substring(0, task_nums.length()-1).split(",") : null;
		List datas = new ArrayList();
		String d_task_id = "";
		if(taskIds!=null){
			
			for(int i=0;i<taskIds.length;i++){
				String taskId = taskIds[i];
				String partner_id = userIds[i];
				String taskRegion = "";
				if(cityIds != null && cityIds.length > i){
					taskRegion = cityIds[i];
				}else{
					taskRegion = lanIds[i];
				}
				if(StringUtils.isEmpty(taskRegion)){
					taskRegion = lanIds[i];
				}	
				String taskNum = taskNums[i];
				
				TaskDetail taskDetail = new TaskDetail();
				taskDetail.setTask_id(taskId);
				taskDetail.setTask_region(taskRegion);
				taskDetail.setTask_num(Integer.valueOf(taskNum));
				
				taskDetail.setUserid(partner_id);
				datas.add(taskDetail);
			}
			d_task_id = taskIds[0];
			if(StringUtils.isNotEmpty(d_task_id));
				taskManager.deleSplitTaskDetail(d_task_id);
		}
		taskManager.savePartnerTask(datas);
		this.json = "{'result':0,'message':'任务分解成功！'}";
		return WWAction.JSON_MESSAGE;
	}
	
	public String showTask(){
		Map result = taskManager.searchTaskByTaskId(task_id);
		try{
			task = new Task();
			BeanUtils.copyProperties(task, result);
			if("partner".equals(groupBy)){
				partner_list = taskManager.showTaskDetailGroupByPartner(task_id);
			}else{
				taskList = taskManager.showTaskDetailGroupByRegion(task_id);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "finished_list";
	}
	
	public String mobTaskDetail(){
		
	//	String userid = ManagerUtils.getAdminUser().getUserid();
	//	if(ManagerUtils.getAdminUser().getFounder() == 1){
			this.webpage = taskManager.qryTaskDetail(task_name, getPage(), getPageSize());
	//	}else{
	//		this.webpage = taskManager.qryTaskDetail(userid, task_name, getPage(), getPageSize());
	//	}
		return "mobTaskDetail";
	}
	
	public String assignTask(){
		if(this.task == null) this.task = new Task();
		this.webpage = taskManager.assignTask(this.task.getTask_name(), getPage(), getPageSize());
		
		return "assignTaskList";
	}
	
	public String assignment(){
		
		this.taskList = taskManager.qryEachTaskDetail(task_id);
		Map map = taskManager.qryTaskById(task_id);
		if(map != null){
			if(this.task == null) this.task = new Task();
			this.task.setTask_code(Const.getStrValue(map, "task_code"));
			this.task.setTask_name(Const.getStrValue(map, "task_name"));
			if(StringUtils.isNotEmpty(Const.getStrValue(map, "task_num"))){
				this.task.setTask_num(Integer.parseInt(Const.getStrValue(map, "task_num")));
			}
				
			if(StringUtils.isNotEmpty(Const.getStrValue(map, "target_num"))){
				this.task.setTarget_num(Integer.parseInt(Const.getStrValue(map, "target_num")));
			}
			this.task.setTask_id(task_id);
			this.task_type_name = Const.getStrValue(map, "task_type_name");
		}
		return "assignment";
	}
	
	public String confirmSend(){
		
		String str = taskManager.confirmSend(task_id);
		this.json = str;
		return WWAction.JSON_MESSAGE;
		
	}
	
	public String finishTask(){
		String str = taskManager.finishTask(task_id);
		this.json = str;
		return WWAction.JSON_MESSAGE;
	}
	
	public String revokeTask(){
		
		String str = taskManager.revokeTask(task_id);
		this.json = str;
		
		return WWAction.JSON_MESSAGE;
	}
	
	public String cancelTask(){
		
		String str = taskManager.cancelTask(task_id);
		this.json = str;
		
		return WWAction.JSON_MESSAGE;
	}
	
	public String revoke_list(){
		if(this.task == null) this.task = new Task();
		this.webpage = taskManager.qryRevokeList(this.task.getTask_name(), getPage(), getPageSize());
		
		return "revoke_list";
	}
	
	public String cancel_list(){
		if(this.task == null) this.task = new Task();
		this.webpage = taskManager.qryCancelList(this.task.getTask_name(), getPage(), getPageSize());
		
		return "cancel_list";
	}
	
	
	public String qryTotalDetail(){
		
		 String userid = ManagerUtils.getAdminUser().getUserid();
		
		 Map map = taskManager.qryTotalDetail(userid);
		 MessageCountResp messageCountResp = new MessageCountResp();
		 MessageCountReq messageCountReq = new MessageCountReq();
		 messageCountResp = this.messageServ.noReadCount(messageCountReq);
		 int wait_message = 0;
		 if(messageCountResp != null){
			 wait_message = messageCountResp.getCount();
		 }

		 if(map == null) {
			map = new HashMap();
			map.put("new_task", 0);
			map.put("unfinished_task", 0);
			map.put("finished_task", 0);
		 }
		 map.put("wait_message", wait_message);
		 this.json = JSON.toJSONString(map);
		 return JSON_MESSAGE;
		
	}
	
	
	public String getCurrOrg(){
		Map map = new HashMap();
        String login_org_id = ManagerUtils.getAdminUser().getOrg_id();
        List org_list = new ArrayList();
        Map o_map = new HashMap();
        o_map.put("party_id", "-3");
        o_map.put("org_name", "任务管理中心");
        o_map.put("state", "closed");
        o_map.put("party_state", "");
        o_map.put("is_area", "0");
        o_map.put("lan_id", "");
        o_map.put("region_id", "");
        org_list.add(o_map);
        map.put("total", org_list.size());
        map.put("rows", org_list);
        this.json = JSON.toJSONString(map);
		return JSON_MESSAGE;
	}
	
	
	
	
	public String viewTaskDetail(){
		
		List list = taskManager.viewTaskDetail(task_id, detail_id);
		if(taskList == null) taskList = new ArrayList();
		if(!ListUtil.isEmpty(list)){
			taskList.add(list.get(0));
		}
		return "each_detail";
	}
	
	
	public String getOrgRoot(){
		Map map = new HashMap();
        String login_org_id = ManagerUtils.getAdminUser().getOrg_id();
        List org_list = new ArrayList();
        if (StringUtils.isEmpty(login_org_id)) return "";
        
        if (Consts.ADMIN_ORG_ID.equals(login_org_id)) {
            org_list = taskManager.getRootOrg();
        } else {
            org_list = taskManager.getCurrOrg(login_org_id);
        }
        if (!ListUtil.isEmpty(org_list)) {
            for (int i = 0; i < org_list.size(); i++) {
                Map o_map = (Map) org_list.get(i);
                String code = Const.getStrValue(o_map, "party_id");
                o_map.put("party_id", code+"_"+org_id);
                int cnt = taskManager.qryChildrenOrgCount(code);
                if (cnt > 0) {
                    o_map.put("state", "closed");
                } else {
                    o_map.put("state", "open");
                }
                o_map.put("is_area", "1");
                if(StringUtils.isNotEmpty(party_state)){
                	o_map.put("party_state", party_state);
                }
            }
        }
        this.json = JSON.toJSONString(org_list);
        return JSON_MESSAGE;
	}
	public String getChildrenOrg() {
		Map map = new HashMap();
        if (StringUtils.isEmpty(org_id)) return "error";
        List children_list = new ArrayList();
        if("-3".equals(org_id)){
            Map map1 = new HashMap();
            map1.put("party_id", "1");
            map1.put("org_name", "已录入的任务");
            map1.put("party_state", "001");
            map1.put("is_area", "0");
            map1.put("state", "closed");
            map1.put("lan_id", "");
            map1.put("region_id", "");
            children_list.add(map1);
            
            Map map2 = new HashMap();
            map2.put("party_id", "2");
            map2.put("org_name", "已分解的任务");
            map2.put("party_state", "002");
            map2.put("is_area", "0");
            map2.put("state", "closed");
            map2.put("lan_id", "");
            map2.put("region_id", "");
            children_list.add(map2);
            
            Map map3 = new HashMap();
            map3.put("party_id", "3");
            map3.put("org_name", "已下发的任务");
            map3.put("party_state", "003");
            map3.put("is_area", "0");
            map3.put("state", "closed");
            map3.put("lan_id", "");
            map3.put("region_id", "");
            children_list.add(map3);
            
            Map map4 = new HashMap();
            map4.put("party_id", "4");
            map4.put("org_name", "已撤销的任务");
            map4.put("party_state", "004");
            map4.put("is_area", "0");
            map4.put("state", "closed");
            map4.put("lan_id", "");
            map4.put("region_id", "");
            children_list.add(map4);
            
            Map map5 = new HashMap();
            map5.put("party_id", "5");
            map5.put("org_name", "已完成的任务");
            map5.put("party_state", "010");
            map5.put("is_area", "0");
            map5.put("state", "closed");
            map5.put("lan_id", "");
            map5.put("region_id", "");
            children_list.add(map5);
            
            Map map6 = new HashMap();
            map6.put("party_id", "6");
            map6.put("org_name", "正在进行任务");
            map6.put("party_state", "009");
            map6.put("is_area", "0");
            map6.put("state", "closed");
            map6.put("lan_id", "");
            map6.put("region_id", "");
            children_list.add(map6);
            
            Map map7 = new HashMap();
            map7.put("party_id", "7");
            map7.put("org_name", "已删除的任务");
            map7.put("party_state", "005");
            map7.put("is_area", "0");
            map7.put("state", "closed");
            map7.put("lan_id", "");
            map7.put("region_id", "");
            children_list.add(map7);
            
           /* Map map8 = new HashMap();
            map8.put("party_id", "8");
            map8.put("org_name", "当前所有任务");
            map8.put("state", "closed");
            children_list.add(map8);*/
            map.put("total", children_list.size());
            map.put("rows", children_list);
            
        }
        else{
        	children_list = taskManager.getChildrenOrg(org_id);
            if (!ListUtil.isEmpty(children_list)) {
                for (int i = 0; i < children_list.size(); i++) {
                    Map o_map = (Map) children_list.get(i);
                    String code = Const.getStrValue(o_map, "party_id");
                    int cnt = taskManager.qryChildrenOrgCount(code);
                    if (cnt > 0) {
                        o_map.put("state", "closed");
                    } else {
                        o_map.put("state", "open");
                    }
                    o_map.put("is_area", "1");
                    o_map.put("party_id", code+"_"+parent_id);
                    if(StringUtils.isNotEmpty(party_state)){
                    	o_map.put("party_state", party_state);
                    }
                }
            }
        }
        this.json = JSON.toJSONString(children_list);
        return WWAction.JSON_MESSAGE;
    }
	
	public String getOrg(){
		return "show_org";
	}
	/**
	 * 获取区县
	 * @return
	 */
	public String getRegion(){
		json = taskManager.getRegionByLanId(lan_code);
		return WWAction.JSON_MESSAGE;
	}
	/**
	 * 获取上级下发任务列表
	 * */
	public String getPTask(){
		
		String lan_id = ManagerUtils.getAdminUser().getLan_id();
		String city_id = ManagerUtils.getAdminUser().getCity_id();

		int founder = ManagerUtils.getAdminUser().getFounder();
		this.webpage = taskManager.getPTask(lan_id, city_id, founder, getPage(), getPageSize());

		
		return "p_task";
	}
	
	
	public String qryTask(){
		
		String userid = ManagerUtils.getAdminUser().getUserid();
		this.webpage = taskManager.qryTask(flag, userid, getPage(), getPageSize());
		return "qry_task";
	}
	
	private String lan_id;
	private String flag; //1新下发任务 2已完成任务 3未完成任务
	private String detail_id;
	
	public String getTask_id() {
		return task_id;
	}
	public String getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(String detail_id) {
		this.detail_id = detail_id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getLan_id() {
		return lan_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public String getNode_level() {
		return node_level;
	}
	public void setNode_level(String node_level) {
		this.node_level = node_level;
	}
	public String getLan_code() {
		return lan_code;
	}
	public void setLan_code(String lan_code) {
		this.lan_code = lan_code;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public String getTask_name() {
		return task_name;
	}
	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}
	public String getTask_type() {
		return task_type;
	}
	public void setTask_type(String task_type) {
		this.task_type = task_type;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getFinished() {
		return finished;
	}
	public void setFinished(String finished) {
		this.finished = finished;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public TaskDetail getTaskDetail() {
		return taskDetail;
	}
	public void setTaskDetail(TaskDetail taskDetail) {
		this.taskDetail = taskDetail;
	}
	public List getTaskList() {
		return taskList;
	}
	public void setTaskList(List taskList) {
		this.taskList = taskList;
	}
	public List getCatList() {
		return catList;
	}
	public void setCatList(List catList) {
		this.catList = catList;
	}
	public AdminUser getAdminUser() {
		return adminUser;
	}
	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}
	public ITaskManager getTaskManager() {
		return taskManager;
	}
	public void setTaskManager(ITaskManager taskManager) {
		this.taskManager = taskManager;
	}
	
	public String getTaskFormActionVal() {
		return taskFormActionVal;
	}
	public void setTaskFormActionVal(String taskFormActionVal) {
		this.taskFormActionVal = taskFormActionVal;
	}
	public String getTaskAddFormActionVal() {
		return taskAddFormActionVal;
	}
	public void setTaskAddFormActionVal(String taskAddFormActionVal) {
		this.taskAddFormActionVal = taskAddFormActionVal;
	}
	public String getTask_type_name() {
		return task_type_name;
	}
	public void setTask_type_name(String task_type_name) {
		this.task_type_name = task_type_name;
	}
	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}

	public String getTask_ids() {
		return task_ids;
	}

	public void setTask_ids(String task_ids) {
		this.task_ids = task_ids;
	}

	
	public String getLan_ids() {
		return lan_ids;
	}

	public void setLan_ids(String lan_ids) {
		this.lan_ids = lan_ids;
	}

	public String getCity_ids() {
		return city_ids;
	}

	public void setCity_ids(String city_ids) {
		this.city_ids = city_ids;
	}

	public String getUserids() {
		return userids;
	}

	public void setUserids(String userids) {
		this.userids = userids;
	}

	public String getTask_nums() {
		return task_nums;
	}

	public void setTask_nums(String task_nums) {
		this.task_nums = task_nums;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public String getLan_name() {
		return lan_name;
	}

	public void setLan_name(String lan_name) {
		this.lan_name = lan_name;
	}

	public String getP_task_name() {
		return p_task_name;
	}

	public void setP_task_name(String p_task_name) {
		this.p_task_name = p_task_name;
	}


	public MessageInf getMessageServ() {
		return messageServ;
	}

	public void setMessageServ(MessageInf messageServ) {
		this.messageServ = messageServ;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
	public AdminUserInf getAdminUserServ() {
		return adminUserServ;
	}

	public void setAdminUserServ(AdminUserInf adminUserServ) {
		this.adminUserServ = adminUserServ;
	}

	public List getPartner_list() {
		return partner_list;
	}

	public void setPartner_list(List partner_list) {
		this.partner_list = partner_list;
	}

	public String getParty_state() {
		return party_state;
	}

	public void setParty_state(String party_state) {
		this.party_state = party_state;
	}

	public ITaskCache getTaskCache() {
		return taskCache;
	}

	public void setTaskCache(ITaskCache taskCache) {
		this.taskCache = taskCache;
	}
	
}
