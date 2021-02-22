package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.AlarmTemplate;

public interface IAlarmTemplateManager {

	public void saveAlarmTemplate(AlarmTemplate alarmTemplate);
	
	public Page list(String template_name,String template_type,String status,String alarm_task_name,int pageNo,int pageSize);

	public int deleteAlarmTemplate(String template_id);
	
	public AlarmTemplate qryAlarmTemplateByTemplateId(String template_id);
	
	public void midifAlarmTemplateByTemplateId(AlarmTemplate alarmTemplate);
	
	public boolean isTempletNameExits(String templet_name) ;

}
