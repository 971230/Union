package com.ztesoft.net.template.lucence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.lucence.AbsLucence;
import com.ztesoft.lucence.LucenceConfig;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class LucenAttrTableService extends AbsLucence {
	

	@Override
	public List<Map> execute(LucenceConfig config, String startTime,
			String endTime) {
		//查询es_attr_def表中数据建立分区
		String sql = "select * from(" +
				" SELECT dd.attr_def_id as id,dd.table_name||'  |  '||dd.field_name as re_show_name,dd.table_name as re_table_name,dd.field_name as re_field_name  FROM es_attr_table dd WHERE dd.source_from=? " +
				" union " +
				" SELECT dd.attr_def_id as id,dd.table_name||'  |  '||dd.field_name as re_show_name,dd.table_name as re_table_name,aa.field_name as re_field_name  FROM es_attr_table dd,ES_ATTR_DEF aa WHERE dd.source_from=? and aa.field_attr_id=dd.attr_def_id " +
				" )";
		int pageNo = 1;
		String source_from = ManagerUtils.getSourceFrom();
		List<Map> resultList = new ArrayList<Map>();
		List<Map> dataList = null;
		if(!StringUtil.isEmpty(sql)){
			do{
				Page page = baseDaoSupport.queryForPage(sql, pageNo, pageSize,source_from,source_from);
				dataList = page.getResult();
				resultList.addAll(dataList);
				pageNo ++;
			}while(dataList!=null && dataList.size()==pageSize);
		}
		return resultList;
	}
}
