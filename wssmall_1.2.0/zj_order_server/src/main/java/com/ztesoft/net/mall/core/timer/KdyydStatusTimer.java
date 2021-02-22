package com.ztesoft.net.mall.core.timer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.impl.ByLtstoreBackService;
import com.ztesoft.net.service.impl.KafKaManager;

public class KdyydStatusTimer {

	private String sql = " select e.syn_mode,a.* from es_kd_order_status_syn a left join es_order_extvtl e on a.order_id=e.order_id where a.source_from = '"+ManagerUtils.getSourceFrom()+"' and a.syn_status not in ('CLZ','CLCG') and a.syn_num<4 ";
	public void run(){
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
		//System.out.println("KdyydStatusTimer-----------begin-------------------");
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		Page syn_page = baseDaoSupport.queryForPage(sql, 1, 100, new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int c) throws SQLException {
				Map data  = new HashMap();
				data.put("syn_mode",rs.getString("syn_mode"));
				data.put("id",rs.getString("id"));
				data.put("bespeakId",rs.getString("bespeakid"));
				data.put("order_id",rs.getString("order_id"));
				data.put("dealState",rs.getString("dealstate"));
				data.put("dealTime",rs.getString("dealtime"));
				data.put("syn_status",rs.getString("syn_status"));
				data.put("syn_num",rs.getString("syn_num"));
				data.put("source_from",rs.getString("source_from"));
				return data;
			}
		}, null);
		List list = syn_page.getResult();
		ByLtstoreBackService a = new ByLtstoreBackService();
		KafKaManager b = new KafKaManager();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map)list.get(i);
			String syn_mode = (String)(map.get("syn_mode")==null?"":map.get("syn_mode"));
			try {
				ByLtstoreBackService.modStatus(map, "CLZ","0");
				if(!StringUtil.isEmpty(syn_mode)&&"kafka".equals(syn_mode)){
					b.send(map);
				}else{
					a.execute(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
