package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.AlarmTask;

public interface IAlarmTaskManager {
	public String saveAlarmTask(String alarm_task_name, String alarm_task_type,
			String task_create_time, String task_aead_time,
			String task_content, String publisher, String release_time,
			String elements_object, String condition,String owner_task_id);
	
	public Page userPhoneList(String user_name, int page, int pageSize);
	
	public Page partnerList(String partner_name, int page,int pageSize);
	
	public Page list(String alarm_task_name,String alarm_task_type,String recevier_phone_num,String recevier_username, int page,int pageSize);
	
	public AlarmTask findAlarmTaskById(String alarm_task_id);
	
	public void modifAlarmTask(String alarm_task_id, String alarm_task_name,
			String task_create_time, String task_aead_time,
			String task_content, String publisher, String release_time,
			String elements_object, String condition,String owner_task_id);
	
	public int deleteAlarmTask(String alarm_task_id);
	
	public boolean isAlarmTaskNameExits(String alarm_task_name);
}
