package com.ztesoft.net.mall.core.timer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.ecsord.params.ecaop.vo.CommerChannelCountVO;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class CommerChannelCountSaveTimer {

	private static Logger logger = Logger.getLogger(CommerChannelCountSaveTimer.class);
	public void run(){
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
        Calendar   cal_1=Calendar.getInstance();//获取当前日期 
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	    String  startTime = format.format(cal_1.getTime());//上月第一天
		Long sta = System.currentTimeMillis();
		String sql = paramsSql();
		String count_sql = countSql();
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		    List<CommerChannelCountVO> pList = new ArrayList<CommerChannelCountVO>();
		    CommerChannelCountVO vo = new CommerChannelCountVO();
	        Page page = null;
	        page = baseDaoSupport.queryForPage(sql,count_sql, 1, 2000, new RowMapper() {
	            @Override
				public Object mapRow(ResultSet rs, int c) throws SQLException {
	                CommerChannelCountVO vo = new CommerChannelCountVO();
	                vo.setOld_date(rs.getString("old_date"));
	                vo.setCity_code(rs.getString("city_code"));
	                vo.setCity_name(rs.getString("city_name"));
	                vo.setCounty_code(rs.getString("county_code"));
	                vo.setCounty_name(rs.getString("county_name"));
	                vo.setPkey(rs.getString("pkey"));
	                vo.setPname(rs.getString("pname"));
	                vo.setP1(rs.getString("p1"));
	                vo.setP2(rs.getString("p2"));
	                vo.setP3(rs.getString("p3"));
	                vo.setP4(rs.getString("p4"));
	                vo.setP5(rs.getString("p5"));
	                vo.setP6(rs.getString("p6"));
	                vo.setP7(rs.getString("p7"));
	                vo.setP8(rs.getString("p8"));
	                vo.setP9(rs.getString("p9"));
	                vo.setP10(rs.getString("p10"));
	                vo.setSource_from(rs.getString("source_from"));
	                return vo;
	            }
	        }, pList.toArray());
		List list = page.getResult();
		for (int i = 0; i < list.size(); i++) {
		    CommerChannelCountVO yydVo = (CommerChannelCountVO)list.get(i);
		    yydVo.setOld_date(startTime);
			baseDaoSupport.insert("es_commerce_channel_count", yydVo);
		}
		Long end = System.currentTimeMillis();
	}
	private static String countSql(){
	    ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String order_from =  cacheUtil.getConfigInfo("COMMERCE_ORDER_FROM");//默认查询的来源
        String order_from_intent =  cacheUtil.getConfigInfo("COMMERCE_ORDER_FROM_INTENT");//默认查询的来源
        String order_from_c = "";
        String order_from_intent_c = "";
        if(!StringUtil.isEmpty(order_from)){
            order_from_c = "'"+order_from.replace(",", "','")+"'";
        }else{
            order_from_c = "''";
        }
        if(!StringUtil.isEmpty(order_from_intent)){
            order_from_intent_c = "'"+order_from_intent.replace(",", "','")+"'";
        }else{
            order_from_intent_c = "''";
        }
	    String sqlsString = 
                " select beseTal.num1 + baseTal2.num2 as num                                                                                                         "+
                        "   from (select count(1) as num1                                                                                                                    "+
                        "           from (select c.col1 city_code,                                                                                                           "+
                        "                        max(c.col2) city_name,                                                                                                      "+
                        "                        c.field_value county_code,                                                                                                  "+
                        "                        max(c.field_value_desc) county_name                                                                                         "+
                        "                   from es_dc_public_dict_relation c                                                                                                "+
                        "                  where c.stype = 'county'                                                                                                          "+
                        "                  and c.source_from = '"+ManagerUtils.getSourceFrom()+"' "+
                        "                  group by c.col1, c.field_value) county,                                                                                           "+
                        "                (select a.pname, a.pkey                                                                                                             "+
                        "                   from es_dc_public_ext a                                                                                                          "+
                        "                  where stype = 'source_from'                                                                                                       "+
                        "                  and a.source_from = '"+ManagerUtils.getSourceFrom()+"' "+
                        "                    and a.pkey in ({order_from})) order_from) beseTal,     "+
                        "        (select count(1) as num2                                                                                                                    "+
                        "           from (select c.col1 city_code,                                                                                                           "+
                        "                        max(c.col2) city_name,                                                                                                      "+
                        "                        c.field_value county_code,                                                                                                  "+
                        "                        max(c.field_value_desc) county_name                                                                                         "+
                        "                   from es_dc_public_dict_relation c                                                                                                "+
                        "                  where c.stype = 'county'                                                                                                          "+
                        "                  and c.source_from = '"+ManagerUtils.getSourceFrom()+"' "+
                        "                  group by c.col1, c.field_value) county,                                                                                           "+
                        "                (select a.pname, a.pkey                                                                                                             "+
                        "                   from es_dc_public_ext a                                                                                                          "+
                        "                  where stype = 'source_from'                                                                                                       "+
                        "                  and a.source_from = '"+ManagerUtils.getSourceFrom()+"' "+
                        "                    and a.pkey in ({order_from_intent})) order_from) baseTal2                                          ";
	    sqlsString = sqlsString.replace("{order_from}", order_from_c);
	    sqlsString = sqlsString.replace("{order_from_intent}",order_from_intent_c);
	    return sqlsString;
	}
	private static String paramsSql(){
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
        Calendar   cal_1=Calendar.getInstance();//获取当前日期 
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        Calendar cale = Calendar.getInstance();   
        cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
        //startTime 当月第一天
        //endTime 当月最后一天
        //endTimes 当月最后一天24时
        String  startTime = format.format(cal_1.getTime())+" 00:00:00";//上月第一天
        String endTime = format.format(cale.getTime())+" 00:00:00";//上月最后一天天
        String endTime1 = format.format(cale.getTime())+" 23:59:59";//上月最后一最后一秒

        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String order_from =  cacheUtil.getConfigInfo("COMMERCE_ORDER_FROM");//默认查询的来源
        String order_from_intent =  cacheUtil.getConfigInfo("COMMERCE_ORDER_FROM_INTENT");//默认查询的来源
        String order_from_c = "";
        String order_from_intent_c = "";
        if(!StringUtil.isEmpty(order_from)){
            order_from_c = "'"+order_from.replace(",", "','")+"'";
        }else{
            order_from_c = "''";
        }
        if(!StringUtil.isEmpty(order_from_intent)){
            order_from_intent_c = "'"+order_from_intent.replace(",", "','")+"'";
        }else{
            order_from_intent_c = "''";
        }
        String sql="";
	    StringBuilder sbBulider = new StringBuilder();
	    sbBulider.append(" select * from (select distinct beseTal.city_code, '' as old_date   ,                                                           ");
	    sbBulider.append("                        beseTal.city_name,                                                                       ");
	    sbBulider.append("                        beseTal.county_code,                                                                     ");
	    sbBulider.append("                        beseTal.county_name,                                                                     ");
	    sbBulider.append("                        beseTal.pkey,                                                                            ");
	    sbBulider.append("                        beseTal.pname,                                                                           ");
	    sbBulider.append("                        to_char(round(nvl(round(t5.nums / t1.nums, 3), 0) * 100,4),                              ");
	    sbBulider.append("                                'fm9999990.999') || '0%' as p1,                                                  ");
	    sbBulider.append("                        to_char(round(nvl(round(t5.nums / t3.nums, 3), 0) * 100,  4),                            ");
	    sbBulider.append("                                'fm9999990.999') || '0%' as p2,                                                  ");
	    sbBulider.append("                        nvl(t0.nums, 0) as p3,                                                                   ");
	    sbBulider.append("                        nvl(t1.nums, 0) as p4,                                                                   ");
	    sbBulider.append("                        nvl(t2.nums, 0) as p5,                                                                   ");
	    sbBulider.append("                        nvl(t3.nums, 0) as p6,                                                                   ");
	    sbBulider.append("                        nvl(t4.nums, 0) as p7,                                                                   ");
	    sbBulider.append("                        nvl(t5.nums, 0) as p8,                                                                   ");
	    sbBulider.append("                        nvl(t6.nums, 0) as p9,                                                                   ");
	    sbBulider.append("                        nvl(t8.nums, 0) as p10,                                                                  ");
	    sbBulider.append("                        nvl(t1.source_from,'ECS') AS source_from                                                 ");
	    sbBulider.append("          from (select county.city_code,                                                                         ");
	    sbBulider.append("                       county.city_name,                                                                         ");
	    sbBulider.append("                       county.county_code,                                                                       ");
	    sbBulider.append("                       county.county_name,                                                                       ");
	    sbBulider.append("                       order_from.pkey,                                                                          ");
	    sbBulider.append("                       order_from.pname                                                                          ");
	    sbBulider.append("                  from (select c.col1 city_code,                                                                 ");
	    sbBulider.append("                               max(c.col2) city_name,                                                            ");
	    sbBulider.append("                               c.field_value county_code,                                                        ");
	    sbBulider.append("                               max(c.other_field_value_desc) county_name                                         ");
	    sbBulider.append("                          from es_dc_public_dict_relation c                                                      ");
	    sbBulider.append("                         where c.stype = 'county'                                                                ");
	    sbBulider.append("                         group by c.col1, c.field_value) county,                                                 ");
	    sbBulider.append("                       (select a.pname, a.pkey                                                                   ");
	    sbBulider.append("                          from es_dc_public_ext a                                                                ");
	    sbBulider.append("                         where stype = 'source_from'                                                             ");
	    sbBulider.append("                           and a.pkey in ({order_from})) order_from) beseTal,                      ");
	    sbBulider.append("               (select eoet.order_city_code,                                                                     ");
	    sbBulider.append("                       eoext.district_code,                                                                      ");
	    sbBulider.append("                       eoet.order_from,                                                                          ");
	    sbBulider.append("                       count(1) as nums                                                                          ");
	    sbBulider.append("                  from es_order_ext eoet                                                                         ");
	    sbBulider.append("                  left join es_order_extvtl eoext                                                                ");
	    sbBulider.append("                    on eoet.order_id = eoext.order_id                                                            ");
	    sbBulider.append("                 where eoet.order_from in ({order_from})                                                         ");
	    sbBulider.append("                   and eoet.tid_time >=                                                                          ");
	    sbBulider.append("                       to_date('{endTime}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                   and eoet.tid_time <=                                                                          ");
        sbBulider.append("                       to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                 group by eoet.order_city_code,                                                                  ");
	    sbBulider.append("                          eoext.district_code,                                                                   ");
	    sbBulider.append("                          eoet.order_from) t0,                                                                   ");
	    sbBulider.append("               (select eoet.order_city_code,                                                                     ");
	    sbBulider.append("                       eoext.district_code,                                                                      ");
	    sbBulider.append("                       eoet.order_from,                                                                          ");
	    sbBulider.append("                       eoet.source_from,                                                                         ");
	    sbBulider.append("                       count(1) as nums                                                                          ");
	    sbBulider.append("                  from es_order_ext eoet                                                                         ");
	    sbBulider.append("                  left join es_order_extvtl eoext                                                                ");
	    sbBulider.append("                    on eoet.order_id = eoext.order_id                                                            ");
	    sbBulider.append("                 where eoet.order_from in ({order_from})                                           ");
	    sbBulider.append("                   and eoet.tid_time >=                                                                          ");
	    sbBulider.append("                       to_date('{startTime}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                   and eoet.tid_time <=                                                                          ");
        sbBulider.append("                       to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                 group by eoet.order_city_code,                                                                  ");
	    sbBulider.append("                          eoext.district_code,                                                                   ");
	    sbBulider.append("                          eoet.order_from,eoet.source_from) t1,                                                  ");
	    sbBulider.append("               (select eoet.order_city_code,                                                                     ");
	    sbBulider.append("                       eoext.district_code,                                                                      ");
	    sbBulider.append("                       eoet.order_from,                                                                          ");
	    sbBulider.append("                       count(1) as nums                                                                          ");
	    sbBulider.append("                  from es_order_ext eoet                                                                         ");
	    sbBulider.append("                  left join es_order_extvtl eoext                                                                ");
	    sbBulider.append("                    on eoet.order_id = eoext.order_id                                                            ");
	    sbBulider.append("                 where eoet.order_from in ({order_from})                                           ");
	    sbBulider.append("                   and eoet.tid_time >=                                                                          ");
	    sbBulider.append("                       to_date('{startTime}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                   and eoet.tid_time <=                                                                          ");
        sbBulider.append("                       to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                   and eoet.order_id in                                                                          ");
	    sbBulider.append("                       (select distinct order_id                                                                          ");
	    sbBulider.append("                          from es_work_order ewo                                                                 ");
	    sbBulider.append("                         where ewo.create_time >=                                                                ");
	    sbBulider.append("                               to_date('{endTime}', 'yyyy-MM-dd hh24:mi:ss')                          ");
	    sbBulider.append("                         and  ewo.create_time <=                                                                ");
        sbBulider.append("                               to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss'))                          ");
	    sbBulider.append("                 group by eoet.order_city_code,                                                                  ");
	    sbBulider.append("                          eoext.district_code,                                                                   ");
	    sbBulider.append("                          eoet.order_from) t2,                                                                   ");
	    sbBulider.append("               (select eoet.order_city_code,                                                                     ");
	    sbBulider.append("                       eoext.district_code,                                                                      ");
	    sbBulider.append("                       eoet.order_from,                                                                          ");
	    sbBulider.append("                       count(1) as nums                                                                          ");
	    sbBulider.append("                  from es_order_ext eoet                                                                         ");
	    sbBulider.append("                  left join es_order_extvtl eoext                                                                ");
	    sbBulider.append("                    on eoet.order_id = eoext.order_id                                                            ");
	    sbBulider.append("                 where eoet.order_from in ({order_from})                                           ");
	    sbBulider.append("                   and eoet.tid_time >=                                                                          ");
	    sbBulider.append("                       to_date('{startTime}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                   and eoet.tid_time <=                                                                          ");
        sbBulider.append("                       to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                   and eoet.order_id in                                                                          ");
	    sbBulider.append("                       (select distinct order_id                                                                 ");
	    sbBulider.append("                          from es_work_order ewo                                                                 ");
	    sbBulider.append("                         where ewo.create_time >=                                                                ");
	    sbBulider.append("                               to_date('{startTime}', 'yyyy-MM-dd hh24:mi:ss')                          ");
	    sbBulider.append("                         and ewo.create_time <=                                                               ");
        sbBulider.append("                               to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss'))                          ");
	    sbBulider.append("                 group by eoet.order_city_code,                                                                  ");
	    sbBulider.append("                          eoext.district_code,                                                                   ");
	    sbBulider.append("                          eoet.order_from) t3,                                                                   ");
	    sbBulider.append("               (select eoet.order_city_code,                                                                     ");
	    sbBulider.append("                       eoext.district_code,                                                                      ");
	    sbBulider.append("                       eoet.order_from,                                                                          ");
	    sbBulider.append("                       count(1) as nums                                                                          ");
	    sbBulider.append("                  from es_order_ext eoet                                                                         ");
	    sbBulider.append("                  left join es_order_extvtl eoext                                                                ");
	    sbBulider.append("                    on eoet.order_id = eoext.order_id                                                            ");
	    sbBulider.append("                  left join es_order eo                                                                          ");
	    sbBulider.append("                    on eo.order_id = eoet.order_id                                                               ");
	    sbBulider.append("                 where eoet.order_from in ({order_from})                                           ");
	    sbBulider.append("                   and eoet.tid_time >=                                                                          ");
        sbBulider.append("                       to_date('{startTime}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
        sbBulider.append("                   and eoet.tid_time <=                                                                          ");
        sbBulider.append("                       to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                   and eo.order_state in ('4', '5')                                                              ");
	    sbBulider.append("                   and eoet.order_id in                                                                          ");
	    sbBulider.append("                       (select distinct order_id                                                                 ");
	    sbBulider.append("                          from es_work_order ewo                                                                 ");
	    sbBulider.append("                         where ewo.update_time >=                                                                ");
	    sbBulider.append("                               to_date('{endTime}', 'yyyy-MM-dd hh24:mi:ss')                          ");
	    sbBulider.append("                         and  ewo.update_time <=                                                               ");
        sbBulider.append("                               to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss'))                          ");
	    sbBulider.append("                 group by eoet.order_city_code,                                                                  ");
	    sbBulider.append("                          eoext.district_code,                                                                   ");
	    sbBulider.append("                          eoet.order_from) t4,                                                                   ");
	    sbBulider.append("               (select eoet.order_city_code,                                                                     ");
	    sbBulider.append("                       eoext.district_code,                                                                      ");
	    sbBulider.append("                       eoet.order_from,                                                                          ");
	    sbBulider.append("                       count(1) as nums                                                                          ");
	    sbBulider.append("                  from es_order_ext eoet                                                                         ");
	    sbBulider.append("                  left join es_order_extvtl eoext                                                                ");
	    sbBulider.append("                    on eoet.order_id = eoext.order_id                                                            ");
	    sbBulider.append("                  left join es_order eo                                                                          ");
	    sbBulider.append("                    on eo.order_id = eoet.order_id                                                               ");
	    sbBulider.append("                 where eoet.order_from in ({order_from})                                           ");
	    sbBulider.append("                   and eoet.tid_time >=                                                                          ");
        sbBulider.append("                       to_date('{startTime}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
        sbBulider.append("                   and eoet.tid_time <=                                                                          ");
        sbBulider.append("                       to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                   and eo.order_state in ('4', '5')                                                              ");
	    sbBulider.append("                   and eoet.order_id in                                                                          ");
	    sbBulider.append("                       (select distinct order_id                                                                 ");
	    sbBulider.append("                          from es_work_order ewo                                                                 ");
	    sbBulider.append("                         where ewo.update_time >=                                                                ");
	    sbBulider.append("                               to_date('{startTime}', 'yyyy-MM-dd hh24:mi:ss')                         ");
	    sbBulider.append("                         and ewo.update_time >=                                                                ");
        sbBulider.append("                               to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss'))                          ");
	    sbBulider.append("                 group by eoet.order_city_code,                                                                  ");
	    sbBulider.append("                          eoext.district_code,                                                                   ");
	    sbBulider.append("                          eoet.order_from) t5,                                                                   ");
	    sbBulider.append("               (select eoet.order_city_code,                                                                     ");
	    sbBulider.append("                       eoext.district_code,                                                                      ");
	    sbBulider.append("                       eoet.order_from,                                                                          ");
	    sbBulider.append("                       count(1) as nums                                                                          ");
	    sbBulider.append("                  from es_order_ext eoet                                                                         ");
	    sbBulider.append("                  left join es_order_extvtl eoext                                                                ");
	    sbBulider.append("                    on eoet.order_id = eoext.order_id                                                            ");
	    sbBulider.append("                 where eoet.order_from in ({order_from})                                           ");
	    sbBulider.append("                   and eoet.abnormal_type = '0' and eoet.refund_deal_type='01'                                                                ");
	    sbBulider.append("                   and eoet.order_id not in                                                                      ");
	    sbBulider.append("                       (select order_id from es_work_order)                                                      ");
	    sbBulider.append("                   and eoet.tid_time >=                                                                          ");
        sbBulider.append("                       to_date('{endTime}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
        sbBulider.append("                   and eoet.tid_time <=                                                                          ");
        sbBulider.append("                       to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                 group by eoet.order_city_code,                                                                  ");
	    sbBulider.append("                          eoext.district_code,                                                                   ");
	    sbBulider.append("                          eoet.order_from) t6,                                                                   ");
	    sbBulider.append("               (select eoet.order_city_code,                                                                     ");
	    sbBulider.append("                       eoext.district_code,                                                                      ");
	    sbBulider.append("                       eoet.order_from,                                                                          ");
	    sbBulider.append("                       count(1) as nums                                                                          ");
	    sbBulider.append("                  from es_order_ext eoet                                                                         ");
	    sbBulider.append("                  left join es_order_extvtl eoext                                                                ");
	    sbBulider.append("                    on eoet.order_id = eoext.order_id                                                            ");
	    sbBulider.append("                 where eoet.order_from in ({order_from})                                                     ");
	    sbBulider.append("                   and eoet.abnormal_type = '0' and eoet.refund_deal_type='01'                                                                  ");
	    sbBulider.append("                   and eoet.order_id not in                                                                      ");
	    sbBulider.append("                       (select order_id from es_work_order)                                                      ");
	    sbBulider.append("                   and eoet.tid_time >=                                                                          ");
        sbBulider.append("                       to_date('{startTime}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
        sbBulider.append("                   and eoet.tid_time <=                                                                          ");
        sbBulider.append("                       to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                 group by eoet.order_city_code,                                                                  ");
	    sbBulider.append("                          eoext.district_code,                                                                   ");
	    sbBulider.append("                          eoet.order_from) t8                                                                    ");
	    sbBulider.append("         where beseTal.city_code = t0.order_city_code(+)                                                         ");
	    sbBulider.append("           and beseTal.county_code = t0.district_code(+)                                                         ");
	    sbBulider.append("           and beseTal.pkey = t0.order_from(+)                                                                   ");
	    sbBulider.append("           and beseTal.city_code = t1.order_city_code(+)                                                         ");
	    sbBulider.append("           and beseTal.county_code = t1.district_code(+)                                                         ");
	    sbBulider.append("           and beseTal.pkey = t1.order_from(+)                                                                   ");
	    sbBulider.append("           and beseTal.city_code = t2.order_city_code(+)                                                         ");
	    sbBulider.append("           and beseTal.county_code = t2.district_code(+)                                                         ");
	    sbBulider.append("           and beseTal.pkey = t2.order_from(+)                                                                   ");
	    sbBulider.append("           and beseTal.city_code = t3.order_city_code(+)                                                         ");
	    sbBulider.append("           and beseTal.county_code = t3.district_code(+)                                                         ");
	    sbBulider.append("           and beseTal.pkey = t3.order_from(+)                                                                   ");
	    sbBulider.append("           and beseTal.city_code = t4.order_city_code(+)                                                         ");
	    sbBulider.append("           and beseTal.county_code = t4.district_code(+)                                                         ");
	    sbBulider.append("           and beseTal.pkey = t4.order_from(+)                                                                   ");
	    sbBulider.append("           and beseTal.city_code = t5.order_city_code(+)                                                         ");
	    sbBulider.append("           and beseTal.county_code = t5.district_code(+)                                                         ");
	    sbBulider.append("           and beseTal.pkey = t5.order_from(+)                                                                   ");
	    sbBulider.append("           and beseTal.city_code = t6.order_city_code(+)                                                         ");
	    sbBulider.append("           and beseTal.county_code = t6.district_code(+)                                                         ");
	    sbBulider.append("           and beseTal.pkey = t6.order_from(+)                                                                   ");
	    sbBulider.append("           and beseTal.city_code = t8.order_city_code(+)                                                         ");
	    sbBulider.append("           and beseTal.county_code = t8.district_code(+)                                                         ");
	    sbBulider.append("           and beseTal.pkey = t8.order_from(+)                                                                   ");
	    sbBulider.append("         order by beseTal.city_code, beseTal.county_code, beseTal.pkey) b where b.source_from ='").append(ManagerUtils.getSourceFrom()).append("'");
	    sbBulider.append(" union all  select *    from (select beseTal.city_code,  '' as old_date ,                                                         ");
	    sbBulider.append("               beseTal.city_name,                                                                                ");
	    sbBulider.append("               beseTal.county_code,                                                                              ");
	    sbBulider.append("               beseTal.county_name,                                                                              ");
	    sbBulider.append("               beseTal.pkey,                                                                                     ");
	    sbBulider.append("               beseTal.pname,                                                                                    ");
	    sbBulider.append("               to_char(round(nvl(round(t5.nums / t1.nums, 3), 0) * 100, 4),                                      ");
	    sbBulider.append("                       'fm9999990.999') || '0%' as p1,                                                           ");
	    sbBulider.append("               to_char(round(nvl(round(t5.nums / t3.nums, 3), 0) * 100, 4),                                      ");
	    sbBulider.append("                       'fm9999990.999') || '0%' as p2,                                                           ");
	    sbBulider.append("               nvl(t0.nums, 0) as p3, nvl(t1.nums, 0) as p4,   nvl(t2.nums, 0) as p5,  nvl(t3.nums, 0) as p6,  ");
	    sbBulider.append("               nvl(t4.nums, 0) as p7,nvl(t5.nums, 0) as p8,    nvl(t6.nums, 0) as p9,  nvl(t8.nums, 0) as p10,  ");
	    sbBulider.append("               nvl(t1.order_from,'ECS') as source_from                                                           ");
	    sbBulider.append("          from (select county.city_code,                                                                         ");
	    sbBulider.append("                       county.city_name,                                                                         ");
	    sbBulider.append("                       county.county_code,                                                                       ");
	    sbBulider.append("                       county.county_name,                                                                       ");
	    sbBulider.append("                       order_from.pkey,                                                                          ");
	    sbBulider.append("                       order_from.pname                                                                          ");
	    sbBulider.append("                  from (select c.col1 city_code,                                                                 ");
	    sbBulider.append("                               max(c.col2) city_name,                                                            ");
	    sbBulider.append("                               c.field_value county_code,                                                        ");
	    sbBulider.append("                               max(c.other_field_value_desc) county_name                                         ");
	    sbBulider.append("                          from es_dc_public_dict_relation c                                                      ");
	    sbBulider.append("                         where c.stype = 'county'                                                                ");
	    sbBulider.append("                         group by c.col1, c.field_value) county,                                                 ");
	    sbBulider.append("                       (select a.pname, a.pkey                                                                   ");
	    sbBulider.append("                          from es_dc_public_ext a                                                                ");
	    sbBulider.append("                         where stype = 'source_from'                                                             ");
	    sbBulider.append("                           and a.pkey in ({order_from_intent})) order_from) beseTal,                                         ");
	    sbBulider.append("               (select a.order_city_code,                                                                        ");
	    sbBulider.append("                       a.order_county_code,                                                                      ");
	    sbBulider.append("                       a.source_system_type as source_from_type,                                                 ");
	    sbBulider.append("                       count(1) as nums                                                                          ");
	    sbBulider.append("                  from es_order_intent a                                                                         ");
	    sbBulider.append("                 where a.source_system_type in ({order_from_intent})                                                         ");
	    sbBulider.append("            and a.create_time >=                                                                                 ");
	    sbBulider.append("                       to_date('{endTime}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("            and a.create_time <=                                                                                 ");
        sbBulider.append("                       to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                 group by a.order_city_code,                                                                     ");
	    sbBulider.append("                          a.order_county_code,                                                                   ");
	    sbBulider.append("                          a.source_system_type) t0,                                                              ");
	    sbBulider.append("               (select a.order_city_code,                                                                        ");
	    sbBulider.append("                       a.order_county_code,                                                                      ");
	    sbBulider.append("                       a.source_system_type as source_from_type,                                                 ");
	    sbBulider.append("                       a.source_from as order_from,                                                              ");
	    sbBulider.append("                       count(1) as nums                                                                          ");
	    sbBulider.append("                  from es_order_intent a                                                                         ");
	    sbBulider.append("                 where a.source_system_type in ({order_from_intent})                                                         ");
	    sbBulider.append("            and a.create_time >=                                                                                 ");
        sbBulider.append("                       to_date('{startTime}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
        sbBulider.append("            and a.create_time <=                                                                                 ");
        sbBulider.append("                       to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                 group by a.order_city_code,                                                                     ");
	    sbBulider.append("                          a.order_county_code,                                                                   ");
	    sbBulider.append("                          a.source_system_type,                                                                  ");
	    sbBulider.append("                          a.source_from) t1,                                                                     ");
	    sbBulider.append("               (select a.order_city_code,                                                                        ");
	    sbBulider.append("                       a.order_county_code,                                                                      ");
	    sbBulider.append("                       a.source_system_type as source_from_type,                                                 ");
	    sbBulider.append("                       count(wo.order_id) as nums                                                                ");
	    sbBulider.append("                  from es_order_intent a                                                                         ");
	    sbBulider.append("                  left join es_work_order wo                                                                     ");
	    sbBulider.append("                    on wo.order_id = a.order_id                                                                  ");
	    sbBulider.append("                 where a.source_system_type in ({order_from_intent})                                                         ");
	    sbBulider.append("                   and a.create_time >=                                                                          ");
	    sbBulider.append("                       to_date('{startTime}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                   and wo.create_time >=                                                                         ");
	    sbBulider.append("                       to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                   and wo.create_time >=                                                                        ");
        sbBulider.append("                       to_date('{endTime}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
        sbBulider.append("                   and wo.create_time <=                                                                        ");
        sbBulider.append("                       to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                 group by a.order_city_code,                                                                     ");
	    sbBulider.append("                          a.order_county_code,                                                                   ");
	    sbBulider.append("                          a.source_system_type) t2,                                                              ");
	    sbBulider.append("               (select a.order_city_code,                                                                        ");
	    sbBulider.append("                       a.order_county_code,                                                                      ");
	    sbBulider.append("                       a.source_system_type as source_from_type,                                                 ");
	    sbBulider.append("                       count(wo1.order_id) as nums                                                               ");
	    sbBulider.append("                  from es_order_intent a                                                                         ");
	    sbBulider.append("                  left join es_work_order wo1                                                                    ");
	    sbBulider.append("                    on wo1.order_id = a.order_id                                                                 ");
	    sbBulider.append("                 where a.source_system_type in ({order_from_intent})                                                         ");
	    sbBulider.append("            and a.create_time >=                                                                                 ");
        sbBulider.append("                       to_date('{startTime}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
        sbBulider.append("            and a.create_time <=                                                                                 ");
        sbBulider.append("                       to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                   and wo1.create_time >=                                                                        ");
	    sbBulider.append("                       to_date('{startTime}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                   and wo1.create_time <=                                                                        ");
        sbBulider.append("                       to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                 group by a.order_city_code,                                                                     ");
	    sbBulider.append("                          a.order_county_code,                                                                   ");
	    sbBulider.append("                          a.source_system_type) t3,                                                              ");
	    sbBulider.append("               (select a.order_city_code,                                                                        ");
	    sbBulider.append("                       a.order_county_code,                                                                      ");
	    sbBulider.append("                       a.source_system_type as source_from_type,                                                 ");
	    sbBulider.append("                       count(1) as nums                                                                          ");
	    sbBulider.append("                  from es_order_intent a                                                                         ");
	    sbBulider.append("                 where a.source_system_type in ({order_from_intent})                                                         ");
	    sbBulider.append("            and a.create_time >=                                                                                 ");
        sbBulider.append("                       to_date('{startTime}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
        sbBulider.append("            and a.create_time <=                                                                                 ");
        sbBulider.append("                       to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss') and  a.status='03'                                 ");
	    sbBulider.append("                   and a.order_id in                                                                             ");
	    sbBulider.append("                       (select distinct order_id                                                                          ");
	    sbBulider.append("                          from es_work_order s                                                                   ");
	    sbBulider.append("                         where s.status = '1'                                                                   ");
	    sbBulider.append("                           and s.update_time >=                                                                  ");
	    sbBulider.append("                               to_date('{endTime}', 'yyyy-MM-dd hh24:mi:ss')                          ");
	    sbBulider.append("                           and s.update_time <=                                                                  ");
        sbBulider.append("                               to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss'))                          ");
	    sbBulider.append("                 group by a.order_city_code,                                                                     ");
	    sbBulider.append("                          a.order_county_code,                                                                   ");
	    sbBulider.append("                          a.source_system_type) t4,                                                              ");
	    sbBulider.append("               (select a.order_city_code,                                                                        ");
	    sbBulider.append("                       a.order_county_code,                                                                      ");
	    sbBulider.append("                       a.source_system_type as source_from_type,                                                 ");
	    sbBulider.append("                       count(1) as nums                                                                          ");
	    sbBulider.append("                  from es_order_intent a                                                                         ");
	    sbBulider.append("                 where a.source_system_type in ({order_from_intent})                                                         ");
	    sbBulider.append("            and a.create_time >=                                                                                 ");
        sbBulider.append("                       to_date('{startTime}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
        sbBulider.append("            and a.create_time <=                                                                                 ");
        sbBulider.append("                       to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss')  and a.status='03'                                 ");
	    sbBulider.append("                   and a.order_id in                                                                             ");
	    sbBulider.append("                       (select order_id                                                                          ");
	    sbBulider.append("                          from es_work_order s                                                                   ");
	    sbBulider.append("                         where s.status = '1'                                                                   ");
	    sbBulider.append("                           and s.update_time >=                                                                  ");
        sbBulider.append("                               to_date('{startTime}', 'yyyy-MM-dd hh24:mi:ss')                          ");
        sbBulider.append("                           and s.update_time <=                                                                  ");
        sbBulider.append("                               to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss'))                          ");
	    sbBulider.append("                 group by a.order_city_code,                                                                     ");
	    sbBulider.append("                          a.order_county_code,                                                                   ");
	    sbBulider.append("                          a.source_system_type) t5,                                                              ");
	    sbBulider.append("               (select a.order_city_code,                                                                        ");
	    sbBulider.append("                       a.order_county_code,                                                                      ");
	    sbBulider.append("                       a.source_system_type as source_from_type,                                                 ");
	    sbBulider.append("                       count(1) as nums                                                                          ");
	    sbBulider.append("                  from es_order_intent a                                                                         ");
	    sbBulider.append("                 where a.source_system_type in ({order_from_intent})                                                         ");
	    sbBulider.append("           and a.status = '01'                                                                                   ");
	    sbBulider.append("                   and a.order_id not in (select order_id from es_work_order)                                    ");
	    sbBulider.append("            and a.create_time >=                                                                                 ");
        sbBulider.append("                       to_date('{endTime}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
        sbBulider.append("            and a.create_time <=                                                                                 ");
        sbBulider.append("                       to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                 group by a.order_city_code,                                                                     ");
	    sbBulider.append("                          a.order_county_code,                                                                   ");
	    sbBulider.append("                          a.source_system_type) t6,                                                              ");
	    sbBulider.append("               (select a.order_city_code,                                                                        ");
	    sbBulider.append("                       a.order_county_code,                                                                      ");
	    sbBulider.append("                       a.source_system_type as source_from_type,                                                 ");
	    sbBulider.append("                       count(1) as nums                                                                          ");
	    sbBulider.append("                  from es_order_intent a                                                                         ");
	    sbBulider.append("                 where a.source_system_type in ({order_from_intent})                                                         ");
	    sbBulider.append("                   and a.status = '01'                                                                           ");
	    sbBulider.append("                   and a.order_id not in (select order_id from es_work_order)                                    ");
	    sbBulider.append("            and a.create_time >=                                                                                 ");
        sbBulider.append("                       to_date('{startTime}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
        sbBulider.append("            and a.create_time <=                                                                                 ");
        sbBulider.append("                       to_date('{endTime1}', 'yyyy-MM-dd hh24:mi:ss')                                   ");
	    sbBulider.append("                 group by a.order_city_code,                                                                     ");
	    sbBulider.append("                          a.order_county_code,                                                                   ");
	    sbBulider.append("                          a.source_system_type) t8                                                               ");
	    sbBulider.append("         where beseTal.city_code = t0.order_city_code(+)                                                         ");
	    sbBulider.append("           and beseTal.county_code = t0.order_county_code(+)                                                     ");
	    sbBulider.append("           and beseTal.pkey = t0.source_from_type(+)                                                             ");
	    sbBulider.append("           and beseTal.city_code = t1.order_city_code(+)                                                         ");
	    sbBulider.append("           and beseTal.county_code = t1.order_county_code(+)                                                     ");
	    sbBulider.append("           and beseTal.pkey = t1.source_from_type(+)                                                             ");
	    sbBulider.append("           and beseTal.city_code = t2.order_city_code(+)                                                         ");
	    sbBulider.append("           and beseTal.county_code = t2.order_county_code(+)                                                     ");
	    sbBulider.append("           and beseTal.pkey = t2.source_from_type(+)                                                             ");
	    sbBulider.append("           and beseTal.city_code = t3.order_city_code(+)                                                         ");
	    sbBulider.append("           and beseTal.county_code = t3.order_county_code(+)                                                     ");
	    sbBulider.append("           and beseTal.pkey = t3.source_from_type(+)                                                             ");
	    sbBulider.append("           and beseTal.city_code = t4.order_city_code(+)                                                         ");
	    sbBulider.append("           and beseTal.county_code = t4.order_county_code(+)                                                     ");
	    sbBulider.append("           and beseTal.pkey = t4.source_from_type(+)                                                             ");
	    sbBulider.append("           and beseTal.city_code = t5.order_city_code(+)                                                         ");
	    sbBulider.append("           and beseTal.county_code = t5.order_county_code(+)                                                     ");
	    sbBulider.append("           and beseTal.pkey = t5.source_from_type(+)                                                             ");
	    sbBulider.append("           and beseTal.city_code = t6.order_city_code(+)                                                         ");
	    sbBulider.append("           and beseTal.county_code = t6.order_county_code(+)                                                     ");
	    sbBulider.append("           and beseTal.pkey = t6.source_from_type(+)                                                             ");
	    sbBulider.append("           and beseTal.city_code = t8.order_city_code(+)                                                         ");
	    sbBulider.append("           and beseTal.county_code = t8.order_county_code(+)                                                     ");
	    sbBulider.append("           and beseTal.pkey = t8.source_from_type(+)                                                             ");
	    sbBulider.append("         order by beseTal.city_code, beseTal.county_code, beseTal.pkey) bl where bl.source_from='").append(ManagerUtils.getSourceFrom()).append("'");
	    sql=sbBulider.toString();
        sql = sql.replace("{startTime}", startTime);
        sql = sql.replace("{endTime}", endTime);
        sql = sql.replace("{endTime1}", endTime1);
        sql = sql.replace("{order_from}", order_from_c);
        sql = sql.replace("{order_from_intent}",order_from_intent_c);
        return sql;
	}
	   public static void main(String[] args) {
	       CommerChannelCountSaveTimer a = new CommerChannelCountSaveTimer();
	       a.run();
	   }      
}
