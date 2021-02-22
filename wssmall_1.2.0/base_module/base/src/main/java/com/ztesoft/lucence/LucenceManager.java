package com.ztesoft.lucence;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class LucenceManager extends BaseSupport implements ILucenceManager {

	@Override
	public List<LucenceConfig> listConfigByStatus(String status) {
		String sql = "select * from es_lucence_config t where t.source_from=? and t.next_exe_time<="+DBTUtil.current();
		List param = new ArrayList();
		param.add(ManagerUtils.getSourceFrom());
		if(!StringUtil.isEmpty(status)){
			sql += " and t.run_status=?";
			param.add(status);
		}
		return this.baseDaoSupport.queryForList(sql, LucenceConfig.class, param.toArray());
	}

	@Override
	public void updateConfigStatus(String id, String status,int exe_min,String end_time,String result_msg) {
		String sql = "update es_lucence_config t set t.run_status=?,t.result_msg=?";
		if(!StringUtil.isEmpty(end_time)){
			sql += ",t.is_new=1,t.last_update_time="+DBTUtil.to_sql_date("?", 2);
		}
		sql += ",t.next_exe_time="+DBTUtil.to_sql_date("?", 2)+" where t.id=?";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String next_execute = df.format(new Date(System.currentTimeMillis()+exe_min*1000*60));
		try {
			next_execute = df.format(new Date(df.parse(DBTUtil.getDBCurrentTime()).getTime()+exe_min*1000*60));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List param = new ArrayList();
		param.add(status);
		param.add(result_msg);
		if(!StringUtil.isEmpty(end_time))param.add(end_time);
		param.add(next_execute);
		param.add(id);
		this.baseDaoSupport.execute(sql, param.toArray());
	}

	@Override
	public void updateConfigStatus(String id, String status) {
		String sql = "update es_lucence_config t set t.run_status=? where t.id=?";
		this.baseDaoSupport.execute(sql, status,id);
	}

}
