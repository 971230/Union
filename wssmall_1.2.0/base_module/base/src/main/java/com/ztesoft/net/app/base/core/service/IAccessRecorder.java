package com.ztesoft.net.app.base.core.service;

import com.ztesoft.net.eop.resource.model.ThemeUri;
import com.ztesoft.net.framework.database.Page;

import java.util.List;
import java.util.Map;

public interface IAccessRecorder {

	public int record(ThemeUri themeUri);

	public void export();

	public Page list(String starttime, String endtime, int pageNo, int pageSize);

	public List detaillist(String ip, String daytime);

	/**
	 * 统计访问量信息
	 * 
	 * @return Map<String,Long> sData = new HashMap<String, Long>();
	 *         sData.put("todayaccess", todayaccess); //日访问量
	 *         sData.put("todaypoint", todaypoint); //日消费积分
	 *         sData.put("monthaccess", monthaccess); //月访问量
	 *         sData.put("monthpoint", monthpoint); //月消费积分
	 *         sData.put("yearaccess", yearaccess); //年访问量
	 *         sData.put("yearpoint", yearpoint); //年消费积分
	 */
	public Map<String, Long> census();
}
