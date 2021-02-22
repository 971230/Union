package com.ztesoft.net.mall.core.timer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.ztesoft.net.ecsord.params.ecaop.vo.BroadbandMonitorReportVO;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

public class BroadbandMonitorReportTimer {

	private static Logger logger = Logger.getLogger(BroadbandMonitorReportTimer.class);
	
	public void run(){
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
        Calendar   cal_1=Calendar.getInstance();//获取当前日期 
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	    String  startTime = format.format(cal_1.getTime());//上月第一天
		Long sta = System.currentTimeMillis();
        String sql = "";
        String sql1=cacheUtil.getConfigInfo("BROADBAND_MONITOR_SQL1");
		String sql2=cacheUtil.getConfigInfo("BROADBAND_MONITOR_SQL2");
		String sql_count=cacheUtil.getConfigInfo("COUNT_BROADBAND_MONITOR_SQL");
		sql=sql1+sql2;
		
	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd"); 
        Calendar cal=Calendar.getInstance();//获取当前日期 
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        Calendar cale = Calendar.getInstance();   
        cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
        //startTime 当月第一天
        //endTime 当月最后一天
        //endTimes 当月最后一天24时
        String MonthStarTime = format1.format(cal.getTime())+" 00:00:00";//上月第一天
        String MonthEndTime = format1.format(cale.getTime())+" 23:59:59";//上月最后天
        String DayStarTime = format1.format(cale.getTime())+" 00:00:00";//上月最后一天天
        String DayEndTime = format1.format(cale.getTime())+" 23:59:59";//上月最后一最后一秒
        
		sql=sql.replace("{DayStarTime}", DayStarTime);
		sql=sql.replace("{DayEndTime}", DayEndTime);
		sql=sql.replace("{MonthStarTime}", MonthStarTime);
		sql=sql.replace("{MonthEndTime}", MonthEndTime);
		
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		    List<BroadbandMonitorReportVO> pList = new ArrayList<BroadbandMonitorReportVO>();
	        Page page = null;
	        page = baseDaoSupport.queryForPage(sql,sql_count, 1, 20, new RowMapper() {
	            @Override
				public Object mapRow(ResultSet rs, int c) throws SQLException {
	            	BroadbandMonitorReportVO vo = new BroadbandMonitorReportVO();
	                vo.setCITY_CODE(rs.getString("field_value"));
	                vo.setCITY_NAME(rs.getString("order_city"));
	                vo.setBOOK_DAY_COUNT(rs.getString("sum00"));
	                vo.setBOOK_MONTH_COUNT(rs.getString("sum0"));
	                vo.setRECEIVE_DAY_COUNT(rs.getString("sum01"));
	                vo.setRECEIVE_MONTH_COUNT(rs.getString("sum1"));
	                vo.setAVG_RECEIVE_TIME(rs.getString("rate1"));
	                vo.setUNRECEIVE_DAY_COUNT(rs.getString("sum02"));
	                vo.setUNRECEIVE_MONTH_COUNT(rs.getString("sum2"));
	                vo.setDISTRIBUTE_DAY_COUNT(rs.getString("sum03"));
	                vo.setDISTRIBUTE_MONTH_COUNT(rs.getString("sum3"));
	                vo.setAVG_DISTRIBUTE_TIME(rs.getString("rate2"));
	                vo.setDISTRIBUTE_RETURN_DAY_COUNT(rs.getString("sum04"));
	                vo.setDISTRIBUTE_RETURN_MONTH_COUNT(rs.getString("sum4"));
	                vo.setHANG_DAY_COUNT(rs.getString("sum05"));
	                vo.setHANG_MONTH_COUNT(rs.getString("sum5"));
	                vo.setCOMPLETE_DAY_COUNT(rs.getString("sum06"));
	                vo.setCOMPLETE_MONTH_COUNT(rs.getString("sum6"));
//	                vo.setAVG_COMPLETE_TIME(rs.getString("rate3"));
	                vo.setOUTSIDE_RETURN_DAY_COUNT(rs.getString("sum07"));
	                vo.setOUTSIDE_RETURN_MONTH_COUNT(rs.getString("sum7"));
	                vo.setSOURCE_FROM("ECS");
	                return vo;
	            }
	        }, pList.toArray());
		List list = page.getResult();
		for (int i = 0; i < list.size(); i++) {
			BroadbandMonitorReportVO yydVo = (BroadbandMonitorReportVO)list.get(i);
		    yydVo.setCREATE_TIME(startTime);
			baseDaoSupport.insert("es_broadband_monitor_his", yydVo);
		}
		Long end = System.currentTimeMillis();
	}
	   public static void main(String[] args) {
	       BroadbandMonitorReportTimer a = new BroadbandMonitorReportTimer();
	       a.run();
	   }      
}
