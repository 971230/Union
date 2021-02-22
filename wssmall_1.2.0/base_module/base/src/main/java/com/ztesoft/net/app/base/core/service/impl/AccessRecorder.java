package com.ztesoft.net.app.base.core.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.app.base.core.service.IAccessRecorder;
import com.ztesoft.net.eop.resource.model.Access;
import com.ztesoft.common.util.ContextHelper;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.resource.model.ThemeUri;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.DateUtil;
import com.ztesoft.net.framework.util.RequestUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 访问记录器
 * 
 * @author wui 2010-7-23下午03:47:25
 */
public class AccessRecorder extends BaseSupport implements IAccessRecorder {
	private static Logger logger = Logger.getLogger(AccessRecorder.class);
	/**
	 * 每次都从session中得到上一次的访问，如果不为空则计算停留时间，并记录上一次访问信息<br/>
	 * 存在问题：无法记录最后一次访问
	 * ——记录过程中有问题，不得影响正常访问
	 */
	@Override
	public int record(ThemeUri themeUri) {
		try{
			HttpServletRequest request = ThreadContextHolder.getHttpRequest();
			Access access = new Access();
			access.setAccess_time(DateFormatUtils.getFormatedDateTime());
			String ip = ContextHelper.getRequestIp(request);
			access.setIp(ip);
			access.setPage(themeUri.getPagename());
			access.setUrl(RequestUtil.getRequestUrl(request));
			access.setPoint(themeUri.getPoint());
			String area = "";
//			try{
//				if(!"".equals(access.getIp()) && null!=access.getIp()){
//					IPSeeker seeker = new IPSeeker();
//					area = seeker.getArea(access.getIp());
//				}
//			}catch(Exception e){}
			access.setArea(area);
			access.setSource_from(themeUri.getSource_from());
			String sessionid = request.getRequestedSessionId();
			access.setSessionid(sessionid);
			Member member = UserServiceFactory.getUserService().getCurrentMember();
			if (member != null)
				access.setMembername(member.getUname());
			IUserService userService = UserServiceFactory.getUserService();
			AdminUser adminUser = ManagerUtils.getAdminUser();
			
			if("后台页面".equals(themeUri.getPagename())){
				if(null!=adminUser && null!=userService){
					access.setObj_type("adminuser");
					access.setObjname(adminUser.getUsername());
				}
			}else{
				if(null!=member){
					access.setObj_type("member");
					access.setObjname(member.getUname());
				}
			}
			
			String id = baseDaoSupport.getSequences("SEQ_ACCESS_ID");
			access.setId(id);
			this.baseDaoSupport.insert("access", access);
/*
			Access last_access = (Access) ThreadContextHolder.getSessionContext().getAttribute(ManagerUtils.getSourceFrom()+"_USER_ACCESS");
			if (last_access != null) {
				
				int stay_time = new Long(DateFormatUtils.formatStringToDate(access.getAccess_time()).getTime()).intValue()
						- new Long(DateFormatUtils.formatStringToDate(last_access.getAccess_time()).getTime()).intValue();
				last_access.setStay_time(stay_time);
				String sql = SF.sysSql("ACCESS_COUNT_ONE_HOUR");
				// 记录一个小小时内不重复的ip
				{
					EopSite site = EopContext.getContext().getCurrentSite();
					int point = site.getPoint();
					if (point == -1 || site.getIsimported() == 1) {// -1的点数表示不限制积分只记录访问记录
						this.baseDaoSupport.insert("access", last_access);
						return 1;
					}
					if (point > access.getPoint()) {
						// 更新当前站点各积分
						this.daoSupport.execute(
								"update eop_site set point=point-? where id=?",
								last_access.getPoint(), site.getId());
						if ("2".equals(EopSetting.DBTYPE)) {
							String id = baseDaoSupport.getSequences("SEQ_ACCESS_ID");
							last_access.setId(id);
						}
						this.baseDaoSupport.insert("access", last_access);
						site.setPoint(site.getPoint() - last_access.getPoint());
					} else {
						return 0;
					}
	
				
				}
 

				return 1;
	
			}
			
			ThreadContextHolder.getSessionContext().setAttribute(ManagerUtils.getSourceFrom()+"_USER_ACCESS", access);
 */
		}catch(Exception ex){
			
		}
		return 1;
	}

	public static void main(String args[]) {
		// int now = (int) (System.currentTimeMillis()/1000);
		//
		// int stime = (int) ( now - 3600 *24 *30 );
		// Calendar cal=Calendar.getInstance();
		// cal.setTime(new Date());
		// int year=cal.get(Calendar.YEAR);
		// logger.info(year);

		int todaystart = (int) (DateUtil.toDate(
				DateUtil.toString(new Date(), "yyyy-MM-dd 00:00"),
				"yyyy-MM-dd mm:ss").getTime() / 1000);
		logger.info((int) (DateUtil.toDate("2010-11-01 00:00",
				"yyyy-MM-dd mm:ss").getTime() / 1000));
		logger.info(todaystart);
		logger.info(System.currentTimeMillis() / 1000);
	}

	@Override
	public Page list(String starttime, String endtime, int pageNo, int pageSize) {

		// 默认的结束时间为当前
		int now = (int) (System.currentTimeMillis() / 1000);

		// 默认开始时间为当前时间的前30天
		int stime = now - 3600 * 24 * 30;

		// 用户输入了开始时间，则以输入的时间为准
		if (starttime != null) {
			stime = (int) (DateUtil.toDate(starttime, "yyyy-MM-dd").getTime() / 1000);
		}

		// 用户输入了结束时间，则以输入的时间为准
		if (endtime != null) {
			now = (int) (DateUtil.toDate(endtime, "yyyy-MM-dd").getTime() / 1000);
		}

		String sql = "select access_time,max(membername) mname,floor(access_time/86400) daytime,count(0) count,sum(stay_time) sum_stay_time,max(access_time) maxtime,min(access_time) mintime,sum(point) point from access where access_time>=? and access_time<=? group by ip,floor(access_time/86400),access_time order by access_time desc";
		sql = baseDaoSupport.buildPageSql(sql, pageNo, pageSize);

		List list = baseDaoSupport.queryForList(sql, stime, now);
		sql = "select count(0) from (select access_time from access where access_time>=? and access_time<=? group by ip, floor(access_time/86400)) tb";
		
		int count = this.baseDaoSupport.queryForInt(sql, stime, now);
		Page page = new Page(0, count, pageSize, list);

		return page;
	}

	/**
	 * 读取某个ip，某天的详细流量
	 * 
	 * @param ip
	 * @param daytime
	 * @return
	 */
	@Override
	public List detaillist(String ip, String daytime) {
		String sql = "select * from access where ip=? and floor(access_time/86400)=? order by access_time asc ";
		return this.baseDaoSupport.queryForList(sql, ip, daytime);
	}

	@Override
	public void export() {

		// 读取所有站点信息
		String sql = "select * from eop_site ";
		List<Map> list = this.daoSupport.queryForList(sql);

		// 为每个用户开启一个线程导出上个月的流量数据
		for (Map map : list) {
			AccessExporter accessExporter = SpringContextHolder
					.getBean("accessExporter");
			accessExporter.setContext(
					Integer.valueOf(map.get("userid").toString()),
					Integer.valueOf(map.get("id").toString()));
			Thread thread = new Thread(accessExporter);
			thread.start();
		}

	}

	@Override
	public Map<String, Long> census() {

		/** 日流量及日积分累计 **/
		// 今天开始秒数
		int todaystart = (int) (DateUtil.toDate(
				DateUtil.toString(new Date(), "yyyy-MM-dd 00:00"),
				"yyyy-MM-dd mm:ss").getTime() / 1000);
		// 今天 结束秒数
		int todayend = (int) (System.currentTimeMillis() / 1000);

		// 日访问量
		String sql = "select count(0) from access where access_time>=?  and access_time<=?";
		long todayaccess = this.baseDaoSupport.queryForLong(sql, todaystart,
				todayend);

		// 日累计消耗积分
		sql = "select sum(point) from access where access_time>=?  and access_time<=?";
		long todaypoint = this.baseDaoSupport.queryForLong(sql, todaystart,
				todayend);

		/** 月流量及月积分累计 **/
		String[] currentMonth = DateUtil.getCurrentMonth(); // 得到本月第一天和最后一天的字串数组
		int monthstart = (int) (DateUtil.toDate(currentMonth[0], "yyyy-MM-dd")
				.getTime() / 1000); // 本月第一天的秒数
		int monthend = (int) (DateUtil.toDate(currentMonth[1], "yyyy-MM-dd")
				.getTime() / 1000); // 本月最后一天的秒数

		// 月访问量
		sql = "select count(0) from access where access_time>=? and access_time<=? order by access_time";
		long monthaccess = this.baseDaoSupport.queryForLong(sql, monthstart,
				monthend);

		// 月消耗积分累计
		sql = "select sum(point) from access where access_time>=? and access_time<=? order by access_time";
		long monthpoint = this.baseDaoSupport.queryForLong(sql, monthstart,
				monthend);

		/** 年流量及年积分累计 **/

		// 查询历史（从开始至上个月）
		sql = "select sumpoint,sumaccess from eop_site where id=?";
		List<Map> list = this.daoSupport.queryForList(sql, EopContext
				.getContext().getCurrentSite().getId());
		if ( list == null || list.size() == 0) {
			throw new RuntimeException("站点["
					+ EopContext.getContext().getCurrentSite().getId() + "]不存在");
		}
		Map siteData = list.get(0);
		long sumaccess = Long.valueOf("" + siteData.get("sumaccess"));
		long sumpoint = Long.valueOf("" + siteData.get("sumpoint"));

		// 累加本月
		sumaccess += monthaccess;
		sumpoint += monthpoint;

		/** 压入统计数据map **/
		Map<String, Long> sData = new HashMap<String, Long>();
		sData.put("todayaccess", todayaccess); // 日访问量
		sData.put("todaypoint", todaypoint); // 日消费积分
		sData.put("monthaccess", monthaccess); // 月访问量
		sData.put("monthpoint", monthpoint); // 月消费积分
		sData.put("sumaccess", sumaccess); // 年访问量
		sData.put("sumpoint", sumpoint); // 年消费积分
		return sData;
	}

}