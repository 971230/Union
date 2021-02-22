package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.AlarmTemplate;
import com.ztesoft.net.mall.core.service.IAlarmTemplateManager;

public class AlarmTemplateManager extends BaseSupport implements IAlarmTemplateManager {

	@Override
	public Page list(String template_name, String template_type,String status,String alarm_task_name, int pageNo,
			int pageSize) {
		String sql="select *from es_alarm_template a where 1=1 #cont";
		
		StringBuilder whereCond = new StringBuilder();
		List pList = new ArrayList();
		if(StringUtils.isNotEmpty(template_name)){
			whereCond.append(" and a.template_name like ?");
			pList.add("%"+template_name+"%");
		}
		
		if(StringUtils.isNotEmpty(alarm_task_name)){
			whereCond.append(" and a.applic_scene_name like ?");
			pList.add("%"+alarm_task_name+"%");
		}
		
		if(StringUtils.isNotEmpty(template_type)){
			whereCond.append(" and a.template_type = ?");
			pList.add(template_type);
		}
		
		if(StringUtils.isNotEmpty(status)){
			whereCond.append(" and a.status= ?");
			pList.add(status);
		}
		
		sql=sql.replaceAll("#cont", whereCond.toString());
		sql=sql+" order by a.operation_time desc ";
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, pList.toArray());
	}

	@Override
	public void saveAlarmTemplate(AlarmTemplate alarmTemplate) {
		this.baseDaoSupport.insert("es_alarm_template", alarmTemplate);
	}

	@Override
	public int deleteAlarmTemplate(String template_id) {
		if (template_id == null || template_id.equals("")) {
			return 0;
		} else {
			String sql = "delete from es_alarm_template where template_id in ("
					+ template_id + ") ";
			this.baseDaoSupport.execute(sql);
			return 1;
		}
	}

	@Override
	public void midifAlarmTemplateByTemplateId(AlarmTemplate alarmTemplate) {
		this.baseDaoSupport.update("es_alarm_template", alarmTemplate, " template_id="+alarmTemplate.getTemplate_id());
	}

	@Override
	public AlarmTemplate qryAlarmTemplateByTemplateId(String template_id) {
		String sql="select *from es_alarm_template a where a.template_id=?";
		return (AlarmTemplate)this.baseDaoSupport.queryForObject(sql, AlarmTemplate.class, template_id);
	}

	@Override
	public boolean isTempletNameExits(String templet_name) {
		String sql = "select template_name from es_alarm_template where template_name=? ";
		List list = this.baseDaoSupport.queryForList(sql, templet_name);
		return list.size() > 0 ? true : false;
	}
}
