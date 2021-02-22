package com.ztesoft.net.eop.sdk.database;

import org.springframework.jdbc.core.JdbcTemplate;

import com.ztesoft.net.eop.resource.model.EopSite;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.database.IDBRouter;
import com.ztesoft.net.framework.util.StringUtil;

/**
 * 简单的分表方式SAAS数据路由器<br/>
 * 由模块名连接上用户id形成表名
 * 
 * @author kingapex
 *         <p>
 *         2009-12-31 下午12:10:05
 *         </p>
 * @version 1.0
 */
public class DBRouter implements IDBRouter {
	private JdbcTemplate jdbcTemplate;

	// 表前缀
	private String prefix;

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public void createTable(String moudle) {
		if ("1".equals(EopSetting.RUNMODE)) {
			return;
		}

		EopSite site = EopContext.getContext().getCurrentSite();
		String userid = site.getUserid();
		String siteid = site.getId();

		prefix = prefix == null ? "" : prefix;
		String sql = "drop table if exists " + prefix + moudle + "_" + userid
				+ "_" + siteid;
		this.jdbcTemplate.execute(sql);
		sql = "create table " + prefix + moudle + "_" + userid + "_" + siteid
				+ " like " + prefix + moudle;
		this.jdbcTemplate.execute(sql);
	}

	@Override
	public String getTableName(String moudle) {
		
		moudle = moudle.toLowerCase();
		
		if("inf_comm_client_calllog".equalsIgnoreCase(moudle)||
				"dual".equalsIgnoreCase(moudle) || 
				"user_col_comments".equalsIgnoreCase(moudle)||
				 "inf_comm_client_endpoint".equalsIgnoreCase(moudle) ||
				 "inf_comm_client_request".equals(moudle) ||
				 "inf_comm_client_response".equals(moudle) || 
				"inf_comm_client_calllog_his".equalsIgnoreCase(moudle) || 
				"inf_comm_client_operation".equals(moudle) || 
				"inf_comm_client_request_user".equals(moudle) ||
				//组织同步的表不需要修改表名
				"zjucrm2o".equals(moudle) ||
		        "tab_office".equals(moudle) ||
		        "agent_channel".equals(moudle) ||
		        "agent_channel_hq".equals(moudle) ||
		        "es_syn_org_map".equals(moudle) ||
		        "tab_user".equals(moudle) ||
		        "tab_employee".equals(moudle) ||
		        "tab_user_sub_system".equals(moudle)
		        ) 
			return moudle;
		
		String result = StringUtil.addPrefix(moudle, prefix);
		if ("1".equals(EopSetting.RUNMODE)) {
			return result;
		}

		EopSite site = EopContext.getContext().getCurrentSite();
		String userid = site.getUserid();
		String siteid = site.getId();

		return result + "_" + userid + "_" + siteid;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
