package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.AlarmLog;
import com.ztesoft.net.mall.core.service.IAlarmLogManager;

public class AlarmLogManager extends BaseSupport implements IAlarmLogManager {

	@Override
	public Page list(String template_id,String startDate, String endDate, int pageNo, int pageSize) {
		String sql = "select *from es_alarm_log a where 1=1 #cont";

		StringBuilder whereCond = new StringBuilder();
		List pList = new ArrayList();
		if (StringUtils.isNotEmpty(startDate)) {
			whereCond.append(" and a.startDate= ?");
			pList.add(startDate);
		}
		
		if (StringUtils.isNotEmpty(template_id)) {
			whereCond.append(" and a.templet_id= ?");
			pList.add(template_id);
		}

		if (StringUtils.isNotEmpty(endDate)) {
			whereCond.append(" and a.endDate= ?");
			pList.add(endDate);
		}

		sql = sql.replaceAll("#cont", whereCond.toString());
		sql = sql + " order by a.alarm_time  desc ";
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, pList
				.toArray());
	}

	@Override
	public AlarmLog qryAlarmLogById(String alarm_log_id) {
		String sql="select a.template_name,b.* from es_alarm_template a,es_alarm_log b where a.template_id=b.templet_id and b.alarm_log_id=?";
		
		return (AlarmLog)this.baseDaoSupport.queryForObject(sql, AlarmLog.class, alarm_log_id);
	}

}
