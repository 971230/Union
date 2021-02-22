package com.ztesoft.net.mall.core.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.AlarmReceiver;
import com.ztesoft.net.mall.core.service.IAlarmReceiverManager;

public class AlarmReceiverManager extends BaseSupport implements IAlarmReceiverManager {

	@Override
	public void saveAlarmReceiver(String recevier_phone_num,
			String recevier_userid, String recevier_username,
			String recevier_type, String alarm_task_id, String create_time) {
		AlarmReceiver alarmReceiver = new AlarmReceiver();
		alarmReceiver.setRecevier_phone_num(recevier_phone_num);
		alarmReceiver.setRecevier_userid(recevier_userid);
		alarmReceiver.setRecevier_username(recevier_username);
		alarmReceiver.setRecevier_type(recevier_type);
		alarmReceiver.setAlarm_task_id(alarm_task_id);
		alarmReceiver.setCreate_time(create_time);
		
		this.baseDaoSupport.insert("es_alarm_receiver", alarmReceiver);
	}

	@Override
	public AlarmReceiver findAlarmReceiverByAlarmTaskId(String alarm_task_id) {
		String sql="select *from es_alarm_receiver where alarm_task_id=?";
		return (AlarmReceiver)this.baseDaoSupport.queryForObject(sql, AlarmReceiver.class, alarm_task_id);
	}

	@Override
	public void modifAlarmReceiver(String recevier_id,String recevier_phone_num,
			String recevier_userid, String recevier_username) {
		AlarmReceiver alarmReceiver = new AlarmReceiver();
		alarmReceiver.setRecevier_phone_num(recevier_phone_num);
		alarmReceiver.setRecevier_userid(recevier_userid);
		alarmReceiver.setRecevier_username(recevier_username);
		
		Map where=new HashMap(); 
		where.put("recevier_id", recevier_id);
		this.baseDaoSupport.update("es_alarm_receiver", alarmReceiver, where);
	}

	@Override
	public void deleteAlarmReceiver(String recevier_id) {
		String sql="delete from es_alarm_receiver where recevier_id=?";
		this.baseDaoSupport.execute(sql, recevier_id);
	}
}
