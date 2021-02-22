package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.AlarmTask;
import com.ztesoft.net.mall.core.service.IAlarmTaskManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class AlarmTaskManager extends BaseSupport implements IAlarmTaskManager {

	@Override
	public String saveAlarmTask(String alarm_task_name, String alarm_task_type,
			String task_create_time, String task_aead_time,
			String task_content, String publisher, String release_time,
			String elements_object, String condition,String owner_task_id) {

		String alarm_task_id = this.baseDaoSupport
				.getSequences("S_ES_ALARM_TASK");
		AlarmTask alarmTask = new AlarmTask();
		alarmTask.setAlarm_task_id(alarm_task_id);
		alarmTask.setAlarm_task_name(alarm_task_name);
		alarmTask.setAlarm_task_type(alarm_task_type);
		alarmTask.setTask_create_time(task_create_time);
		alarmTask.setTask_aead_time(task_aead_time);
		alarmTask.setTask_content(task_content);
		alarmTask.setPublisher(publisher);
		alarmTask.setRelease_time(release_time);
		alarmTask.setElements_object(elements_object);
		alarmTask.setCondition_desc(condition);
		alarmTask.setOwner_task_id(owner_task_id);

		this.baseDaoSupport.insert("es_alarm_task", alarmTask);

		return alarm_task_id;
	}

	@Override
	public Page userPhoneList(String user_name, int page, int pageSize) {
		String sql = "SELECT a.pkey phone,a.pname user_name FROM es_dc_public a  WHERE a.stype='201203' ";

		List partm = new ArrayList();

		StringBuffer sqlAccount = new StringBuffer(" ");

		if (StringUtils.isNotEmpty(user_name)) {
			sqlAccount.append(" and a.pname like ?");
			partm.add("%" + user_name + "%");
		}

		sql = sql + sqlAccount.toString();
		return this.baseDaoSupport.queryForPage(sql, page, pageSize, partm
				.toArray());
	}

	@Override
	public Page partnerList(String partner_name, int page, int pageSize) {
		String sql = "SELECT a.userid,a.realname FROM es_adminuser a WHERE a.founder=3 ";

		List partm = new ArrayList();

		StringBuffer sqlAccount = new StringBuffer(" ");

		if (StringUtils.isNotEmpty(partner_name)) {
			sqlAccount.append(" and a.realname like ?");
			partm.add("%" + partner_name + "%");
		}

		sql = sql + sqlAccount.toString();
		return this.baseDaoSupport.queryForPage(sql, page, pageSize, partm
				.toArray());
	}

	@Override
	public Page list(String alarm_task_name,String alarm_task_type,String recevier_phone_num,String recevier_username, int page, int pageSize) {
		String sql = "SELECT b.username,a.*,c.recevier_username,c.recevier_phone_num  FROM " +
				"es_alarm_task a,es_adminuser b,es_alarm_receiver c WHERE " +
				"a.publisher=b.userid and c.alarm_task_id =a.alarm_task_id and a.source_from = b.source_from " +
				"and b.source_from = c.source_from and c.source_from = '" + ManagerUtils.getSourceFrom() + "' ";

		List partm = new ArrayList();

		StringBuffer sqlAccount = new StringBuffer(" ");

		if (StringUtils.isNotEmpty(alarm_task_name)) {
			sqlAccount.append(" and a.alarm_task_name like ?");
			partm.add("%" + alarm_task_name + "%");
		}
		
		if (StringUtils.isNotEmpty(recevier_phone_num)) {
			sqlAccount.append(" and c.recevier_phone_num like ?");
			partm.add("%" + recevier_phone_num + "%");
		}
		
		if (StringUtils.isNotEmpty(recevier_username)) {
			sqlAccount.append(" and c.recevier_username like ?");
			partm.add("%" + recevier_username + "%");
		}
		
		if (StringUtils.isNotEmpty(alarm_task_type)) {
			sqlAccount.append(" and a.alarm_task_type = ?");
			partm.add(alarm_task_type);
		}

		sql = sql + sqlAccount.toString()+" order by release_time desc";
		return this.baseDaoSupport.queryForPage(sql, page, pageSize, partm
				.toArray());
	}

	@Override
	public AlarmTask findAlarmTaskById(String alarm_task_id) {
		String sql="select *from es_alarm_task where alarm_task_id=?";
		return (AlarmTask)this.baseDaoSupport.queryForObject(sql, AlarmTask.class, alarm_task_id);
	}

	@Override
	public void modifAlarmTask(String alarm_task_id, String alarm_task_name,
			String task_create_time, String task_aead_time,
			String task_content, String publisher, String release_time,
			String elements_object, String condition,String owner_task_id) {
		
		AlarmTask alarmTask = new AlarmTask();
		alarmTask.setAlarm_task_id(alarm_task_id);
		alarmTask.setAlarm_task_name(alarm_task_name);
		alarmTask.setTask_create_time(task_create_time);
		alarmTask.setTask_aead_time(task_aead_time);
		alarmTask.setTask_content(task_content);
		alarmTask.setPublisher(publisher);
		alarmTask.setRelease_time(release_time);
		alarmTask.setElements_object(elements_object);
		alarmTask.setCondition_desc(condition);
		alarmTask.setOwner_task_id(owner_task_id);
		this.baseDaoSupport.update("es_alarm_task", alarmTask, "alarm_task_id="+alarm_task_id);
	}

	@Override
	public int deleteAlarmTask(String alarm_task_id) {
		if (alarm_task_id == null || alarm_task_id.equals("")) {
			return 0;
		} else {
			String sql = "delete from es_alarm_task where alarm_task_id in ("
					+ alarm_task_id + ") ";
			this.baseDaoSupport.execute(sql);
			return 1;
		}
	}
	
	@Override
	public boolean isAlarmTaskNameExits(String alarm_task_name) {
		String sql = "select alarm_task_name from es_alarm_task where alarm_task_name=? ";
		List list = this.baseDaoSupport.queryForList(sql, alarm_task_name);
		return list.size() > 0 ? true : false;
	}
}
