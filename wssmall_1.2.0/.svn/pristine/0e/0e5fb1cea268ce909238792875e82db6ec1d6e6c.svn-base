/**
 * 
 */
package com.ztesoft.ibss.common.service;

import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.WBCacheUtil;
import com.ztesoft.common.util.ContextHelper;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.ct.config.ConfigUtil;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Musoon
 * 
 */
public class ServiceFilter {

	private static Logger log = Logger.getLogger(ServiceFilter.class);

	private ServiceFilter() {
	}

	private static ServiceFilter instance = null;

	public static ServiceFilter getInst() {
		if (null == instance)
			instance = new ServiceFilter();
		return instance;
	}

	// 默认有限时间是5分钟
	private int limitTime = 5;
	// 默认限制次数是10次
	private int limitCount = 10;

	private static String CALL_DWR_TIME = "CALL_DWR_TIME";
	private static String ACTION_ID = "ACTION_ID";

	private static Map<String, List<Map<String, String>>> callDwrMap = new HashMap<String, List<Map<String, String>>>();

	/**
	 * 过滤方法
	 * 
	 * @param actionId
	 * @return false 表示调用频繁
	 */
	public boolean filterDWR(String actionId) {
		try {
			// 过滤开关(1:执行过滤，其他值不执行)
			String filterFlag = (String) WBCacheUtil.getCache(
					ConfigUtil.CtConfigCatalog, "LIMIT_ACCESS_DWR_FLAG");
			if (!"1".equals(filterFlag)) {
				return true;
			}

			initFilterParams();

			String sessionId = ContextHelper.getSession().getId();
			String key = sessionId + actionId;

			List<Map<String, String>> curCallDwrList = null;
			if (!callDwrMap.containsKey(key)) {
				doWhenCallDwrListNull(actionId, key);
			} else {
				// 同一个actionId多次调用
				curCallDwrList = callDwrMap.get(key);
				if (null != curCallDwrList && !curCallDwrList.isEmpty()) {
					// 取第一个即是当前actionId的最先调用记录
					Map<String, String> firstCallMap = curCallDwrList.get(0);
					// 最先调用时间
					long firstCallTime = Long.parseLong(Const.getStrValue(
							firstCallMap, CALL_DWR_TIME));
					log.info("###### 当前用户最先调用【" + actionId + "】时间==>"
							+ firstCallTime);
					// 当前调用时间
					long curAccessTime = System.currentTimeMillis();
					long limitTime = curAccessTime - firstCallTime;
					log.info("###### 当前用户调用【" + actionId + "】间隔时间==>"
							+ limitTime);
					// 累计到当前的调用次数
					int curCallCount = curCallDwrList.size() + 1;

					// 限定有效时间内(分*秒*1000=毫秒)
					if (limitTime <= (this.limitTime * 60 * 1000)) {
						log.info("###### 当前用户在【有限时间" + this.limitTime * 60
								+ "秒，限制次数" + this.limitCount + "次】内调用【"
								+ actionId + "】次数为" + curCallCount + "次");
						// 限定有效时间内超过限定次数
						if (curCallCount > this.limitCount) {
							// throw new FrameException("请求过于频繁，请稍后操作！");
							// throw new DWRException(-1, "请求过于频繁，请稍后操作！", "");
							return false;
						} else {
							curCallDwrList.add(getCurCallMap(actionId,
									curAccessTime));
							callDwrMap.put(key, curCallDwrList);
						}
					} else {
						// 限定有效时间外，则清空之前的，并增加当前的
						curCallDwrList.clear();
						curCallDwrList.add(getCurCallMap(actionId,
								curAccessTime));
						callDwrMap.put(key, curCallDwrList);
					}
				} else {
					doWhenCallDwrListNull(actionId, key);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 当前用户调用actionId为空时处理
	 * 
	 * @param actionId
	 * @param key
	 * @throws FrameException
	 */
	private void doWhenCallDwrListNull(String actionId, String key)
			throws FrameException {
		List<Map<String, String>> curDwrList = new ArrayList<Map<String, String>>();
		curDwrList.add(getCurCallMap(actionId, System.currentTimeMillis()));
		callDwrMap.put(key, curDwrList);
	}

	/**
	 * 获取当前调用actionId保存信息对象
	 * 
	 * @param actionId
	 * @param callDwrTime
	 * @return
	 * @throws FrameException
	 */
	private Map<String, String> getCurCallMap(String actionId, long callDwrTime)
			throws FrameException {
		Map<String, String> curCallMap = new HashMap<String, String>();
		curCallMap.put(CALL_DWR_TIME, "" + callDwrTime);
		curCallMap.put(ACTION_ID, actionId);
		return curCallMap;
	}

	/**
	 * 初始化过滤参数
	 * 
	 * @throws FrameException
	 */
	private void initFilterParams() throws FrameException {

		// 1:5,DWR请求过滤参数【有限时间(分为单位:限制次数)】
		String param = (String) WBCacheUtil.getCache(
				ConfigUtil.CtConfigCatalog, "LIMIT_ACCESS_DWR_PARAM");
		if (null != param && !"".equals(param) && param.indexOf(":") > 0
				&& param.split(":").length == 2) {
			String[] p = param.split(":");
			this.limitTime = Integer.parseInt(p[0]);
			this.limitCount = Integer.parseInt(p[1]);
		}
	}
}
