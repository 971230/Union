package com.ztesoft.lucence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class GoodsLucenceService extends AbsLucence {
	private static Logger logger = Logger.getLogger(GoodsLucenceService.class);

	@Override
	public List<Map> execute(LucenceConfig config, String startTime,
			String endTime) {
		String sql = "select * from(select t.goods_id as id,t.name as name,type from es_goods t where t.type='goods' and t.source_from=? and t.create_time>"+DBTUtil.to_sql_date("?", 2)+" and t.create_time<"+DBTUtil.to_sql_date("?", 2);
		sql += " union select t.relation_id as id,t.relation_name as name,'package' as type from es_relation t where t.source_from=? and t.created_date>"+DBTUtil.to_sql_date("?", 2)+" and t.created_date<"+DBTUtil.to_sql_date("?", 2) +")";
		int pageNo = 1;
		String source_from = ManagerUtils.getSourceFrom();
		List<Map> resultList = new ArrayList<Map>();
		List<Map> dataList = null;
		if(!StringUtil.isEmpty(sql)){
			do{
				Page page = baseDaoSupport.queryForPage(sql, pageNo, pageSize,source_from,startTime,endTime,source_from,startTime,endTime);
				dataList = page.getResult();
				resultList.addAll(dataList);
				pageNo ++;
			}while(dataList!=null && dataList.size()==pageSize);
		}
		return resultList;
	}
	
	public static void main(String[] args) throws Exception {
		//String startTime = "2000-01-01 00:00:00.0";
		//logger.info(startTime.substring(0,startTime.length()-2));
		//String str = null;
		AbsLucence al = new GoodsLucenceService();
		//LucenceConfig cg = new LucenceConfig();
		//cg.setIs_new(1);
		//al.perform(cg);
		logger.info("==========创建完成===================");
		
		
		Map map = new HashMap();
		map.put(TITLE, "22 AND 显示器");  //AND OR NOT 
		Page pg = al.search(map);
		List<Map> list = pg.getResult();
		for(Map m:list){
			logger.info(m.get(ID)+"\t"+m.get(TITLE)+"\t"+m.get(CONTENT));
		}
	}

	
}
