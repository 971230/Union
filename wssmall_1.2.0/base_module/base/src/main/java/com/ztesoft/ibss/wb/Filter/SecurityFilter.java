/**
 * 
 */
package com.ztesoft.ibss.wb.Filter;

import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.WBCacheUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.ct.config.ConfigUtil;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**

<filter>
	<filter-name>SecurityFilter</filter-name>
	<filter-class>com.ztesoft.ibss.wb.Filter.SecurityFilter</filter-class>
</filter>

<filter-mapping>
	<filter-name>SecurityFilter</filter-name>
	<url-pattern>/*</url-pattern>
</filter-mapping>

 * 网厅访问安全过滤(防止暴力攻击)
 * 
 * @author Musoon
 *
 */
public class SecurityFilter implements Filter {
	
	private static Logger log = Logger.getLogger(SecurityFilter.class);
	
	//错误重定向jsp
	private String errorJsp = "";
	
	//不用过滤的URL
	private String unLimitUrl = "";
	
	//用户访问session集合
	private static Map accessSessMap = new HashMap();
	
	//限制参数集合
	private static List limitParams = null;
	
	//key
	private static String ACCESS_TIME = "ACCESS_TIME";
	private static String ACCESS_URL = "ACCESS_URL";

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws IOException, ServletException {

		try {
			
			//过滤开关(1:执行过滤，其他值不执行)
			String filterFlag = (String) WBCacheUtil.getCache(ConfigUtil.CtConfigCatalog, "LIMIT_ACCESS_FILTER_FLAG");
			if ("1".equals(filterFlag)) {
				//访问限制过滤
				doAccessLimitFilter(request, response, filter);
			} else {
				filter.doFilter(request, response);
			}
			
			//后续其他安全过滤
			//xxx
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void init(FilterConfig filter) throws ServletException {

		//后续其他安全过滤初始化
		//xxx
	}
	
	/**
	 * 当前访问session list为空时处理函数
	 * @param req
	 * @throws ServletException
	 */
	private void doWhenAccessSessListNull(HttpServletRequest req, String key) throws ServletException {
		List curSessList = new ArrayList();
		curSessList.add(getCurAccessMap(req, System.currentTimeMillis()));
		accessSessMap.put(key, curSessList);
	}
	
	/**
	 * 获取当前访问session集合
	 * @param req
	 * @param accessTime
	 * @return
	 * @throws ServletException
	 */
	private Map getCurAccessMap(HttpServletRequest req, long accessTime) throws ServletException {
		Map curSessMap = new HashMap();
		//访问时间
		curSessMap.put(ACCESS_TIME, "" + accessTime);
		//访问URL
		curSessMap.put(ACCESS_URL, req.getRequestURI());
		
		return curSessMap;
	}
	
	private SecurityFilterVo getSFV(String postfix) throws FrameException {
		if (null != limitParams && !limitParams.isEmpty()) {
			SecurityFilterVo sfv = null;
			for (int i = 0; i < limitParams.size(); i++) {
				sfv = (SecurityFilterVo) limitParams.get(i);
				if (postfix.trim().equals(sfv.getUrlPostfix().trim())) {
					return sfv;
				}
			}
		}
		return null;
	}
	
	/**
	 * 初始化限制参数
	 * @throws FrameException
	 */
	private void initAccessLimitParams() throws FrameException {
		
		//错误重定向URL
		String url = (String) WBCacheUtil.getCache(ConfigUtil.CtConfigCatalog, "LIMIT_ACCESS_ERROR_URL");
		if (null == url || "".equals(url.trim())) url = "/public/748.jsp";
		this.errorJsp = url;
		
		//不用过滤的URL，例如一些404.jsp之类
		String unUrl = (String) WBCacheUtil.getCache(ConfigUtil.CtConfigCatalog, "UNLIMIT_ACCESS_URL");
		this.unLimitUrl = unUrl;
		
		//url后缀:有限时间:限制次数
		//jsp:2:10,css:1:5,jpg:1:7,png:3:9
		String paramStrNew = (String) WBCacheUtil.getCache(ConfigUtil.CtConfigCatalog, "LIMIT_ACCESS_PARAMS");
		
		//判断一下，不用每次请求都处理以下转vo处理逻辑
		String paramStr = Const.getStrValue(accessSessMap, "LIMIT_ACCESS_PARAMS");
		if (!paramStr.equals(paramStrNew)) {
			accessSessMap.put("LIMIT_ACCESS_PARAMS", paramStrNew);
			paramStr = paramStrNew;
			
			if (null == limitParams || limitParams.isEmpty()) {
				limitParams = new ArrayList();
			} else {
				limitParams.clear();
			}
			
			String[] urlStrs = new String[] {paramStr};
			if (paramStr.indexOf(",") >= 0) {
				urlStrs = paramStr.split(",");
			}
			
			if (null != urlStrs && !"".equals(urlStrs) && urlStrs.length > 0) {
				for (int i = 0; i < urlStrs.length; i++) {
					addSFV(urlStrs[i]);
				}
			}
			
		} 
	}
	
	private void addSFV(String urlStr) throws FrameException {
		//url后缀:有限时间:限制次数
		//jsp:2:10
		if (null != urlStr && !"".equals(urlStr) 
				&& urlStr.indexOf(":") >= 0 && urlStr.split(":").length == 3) {
			String[] urls = urlStr.split(":");
			SecurityFilterVo sfv = new SecurityFilterVo();
			sfv.setUrlPostfix(urls[0]);
			sfv.setLimitTime(Integer.parseInt(urls[1]));
			sfv.setLimitCount(Integer.parseInt(urls[2]));
			limitParams.add(sfv);
		}
	}

	/**
	 * 访问限制过滤
	 * @param request
	 * @param response
	 * @param filter
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doAccessLimitFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws IOException, ServletException, FrameException {

		//初始化访问参数
		initAccessLimitParams();
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		//请求URL
		String reqUrl = req.getRequestURI();
		
		//出错页面不用过滤
		if (reqUrl.indexOf(this.errorJsp) >= 0) {
			filter.doFilter(request, response);
			return;
		}
		
		//一些指定的错误重定向URL不用过滤，防止一个链接内报很多错时出现很多次404之类的错误跳转页面
		String urlOweName = reqUrl.substring(reqUrl.lastIndexOf("/") + 1, reqUrl.length());
		if (!"".equals(this.unLimitUrl) && this.unLimitUrl.indexOf(urlOweName) >= 0) {
			filter.doFilter(request, response);
			return;
		}
		
		//URL后缀
		String reqUrlPostfix = reqUrl.substring(reqUrl.lastIndexOf(".") + 1, reqUrl.length());
		SecurityFilterVo sfv = getSFV(reqUrlPostfix);
		if (null == sfv) {
			filter.doFilter(request, response);
			return;
		}

		//当前访问用户session ID
		String sId = req.getSession().getId();
		List curSessList = null;
		
		//如果当前用户第一次访问(session ID +　 请求URL　 作为key)
		if (!accessSessMap.containsKey(sId + reqUrl)) {
			doWhenAccessSessListNull(req, sId + reqUrl);
		} else {
			//同一个session多次访问
			curSessList = (ArrayList) accessSessMap.get(sId + reqUrl);
			if (null != curSessList && !curSessList.isEmpty()) {
				//取第一个即是当前session的最先访问记录
				Map firstSessMap = (HashMap) curSessList.get(0);
				//最先访问时间
				long firstAccessTime = Long.parseLong(Const.getStrValue(firstSessMap, ACCESS_TIME));
				log.info("###### 当前用户最先访问【" + reqUrl + "】时间==>" + firstAccessTime);
				//当前访问时间
				long curAccessTime = System.currentTimeMillis();
				long limitTime = curAccessTime - firstAccessTime;
				log.info("###### 当前用户访问【" + reqUrl + "】间隔时间==>" + limitTime);
				//累计到当前的访问次数
				int curAccessCount = curSessList.size() + 1;
				
				//限定有效时间内(分*秒*1000=毫秒)
				if (limitTime <= (sfv.getLimitTime() * 60 * 1000)) {
					log.info("###### 当前用户在【有限时间" + sfv.getLimitTime() * 60 + "秒，限制次数" + sfv.getLimitCount() + "次】内访问【" + reqUrl + "】次数为" + curAccessCount + "次");
					//限定有效时间内超过限定次数
					if (curAccessCount >= sfv.getLimitCount()) {
						res.sendRedirect(this.errorJsp);
						return;
					} else {
						curSessList.add(getCurAccessMap(req, curAccessTime));
						accessSessMap.put(sId + reqUrl, curSessList);
					}
				} else {
					//限定有效时间外，则清空之前的，并增加当前的
					curSessList.clear();
					curSessList.add(getCurAccessMap(req, curAccessTime));
					accessSessMap.put(sId + reqUrl, curSessList);
				}
			} else {
				doWhenAccessSessListNull(req, sId + reqUrl);
			}
		}
		filter.doFilter(request, response);
	}	
}
