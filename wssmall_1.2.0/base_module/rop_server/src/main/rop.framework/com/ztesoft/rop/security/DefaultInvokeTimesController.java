package com.ztesoft.rop.security;

import java.util.HashMap;
import java.util.Map;

import com.rop.core.memcached.cache.CacheFactory;
import com.rop.core.memcached.util.CacheValues;
import com.ztesoft.rop.common.CurrentThreadVar;
import com.ztesoft.rop.common.RopRequestContext;
import com.ztesoft.rop.db.RopDaoImpl;
import com.ztesoft.rop.session.DefaultSession;
import com.ztesoft.rop.session.Session;

/**
 * <pre>
 * 默认的实现
 * </pre>
 * 
 * @author
 * @version 1.0
 */
public class DefaultInvokeTimesController implements InvokeTimesController {

	@Override
	public void caculateInvokeTimes(RopRequestContext rrc, String appKey,
			Session session) {
	}

	@Override
	public void caculateInvokeTimes(RopRequestContext rrc, String appKey,
			String method, String version, Session session) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAppSessionInvokeFrequencyExceed(RopRequestContext rrc,
			String appKey, Session session) {

		RopDaoImpl dao = RopDaoImpl.getInstance();
		try {
			DefaultSession defSession = (DefaultSession) session;
			Map<String, String> pramas = new HashMap<String, String>();
			if(0 == CurrentThreadVar.getVar().getAppId()){
				new DefaultSecurityManager().initCurrentThreadVal(appKey, rrc.getMethod(), rrc.getVersion()) ;
			}
			pramas.put(RopDaoImpl.APP_ID, String.valueOf( CurrentThreadVar.getVar().getAppId()));
			pramas.put(RopDaoImpl.FUN_ID, String.valueOf( CurrentThreadVar.getVar().getFunId()));
			pramas.put(RopDaoImpl.SESSION_ID, defSession.getSession_id());
			pramas.put(RopDaoImpl.APP_LEVEL, CurrentThreadVar.getVar().getAppLevel());
			// pramas.put(RopDaoImpl.FUN_CODE, rrc.getMethod());
			// pramas.put(RopDaoImpl.FUN_VERSION, rrc.getVersion());
			//
			int[] returnValue = initDefineNumber(dao, rrc, appKey,
					RopDaoImpl.APP_DEF, pramas);

			int session_max_num = returnValue[1];

			int rqeustNum = dao.findRquestNumber(pramas,
					RopDaoImpl.APP_SESSION_REQ_LEVEL, true);
			if (rqeustNum > session_max_num) {
				return true;
			}

		} catch (Exception ex) {

		}

		return false;
	}

	/**
	 * RopDaoImpl.APP_DEF
	 */
	@Override
	public boolean isAppInvokeLimitExceed(RopRequestContext rrc, String appId) {

		RopDaoImpl dao = RopDaoImpl.getInstance();
		try {

			Map<String, String> pramas = new HashMap<String, String>();
			pramas.put(RopDaoImpl.APP_ID, appId);
			
			
			// pramas.put(RopDaoImpl.FUN_CODE, rrc.getMethod());
			// pramas.put(RopDaoImpl.FUN_VERSION, rrc.getVersion());
			//
			int[] returnValue = initDefineNumber(dao, rrc, appId,
					RopDaoImpl.APP_DEF, pramas);

			int n_max_num = returnValue[0];

			int rqeustNum = dao.findRquestNumber(pramas,
					RopDaoImpl.APP_REQ_LEVEL, false);
			if (rqeustNum > n_max_num) {
				return true;
			}

		} catch (Exception ex) {

		}

		return false;
	}

	@Override
	public boolean isAppMethodInvokeLimitExceed(RopRequestContext rrc,
			String appKey) {
		RopDaoImpl dao = RopDaoImpl.getInstance();
		try {

			Map<String, String> pramas = new HashMap<String, String>();
			if(0 == CurrentThreadVar.getVar().getAppId()){
				new DefaultSecurityManager().initCurrentThreadVal(appKey, rrc.getMethod(), rrc.getVersion()) ;
//				Map data = RopDaoImpl.getInstance().qryAppInfoBy(appKey,rrc.getMethod(), rrc.getVersion())  ;
			}
			pramas.put(RopDaoImpl.APP_ID, String.valueOf( CurrentThreadVar.getVar().getAppId()));
			pramas.put(RopDaoImpl.FUN_ID, String.valueOf( CurrentThreadVar.getVar().getFunId()));
			pramas.put(RopDaoImpl.APP_LEVEL, CurrentThreadVar.getVar().getAppLevel());
			// pramas.put(RopDaoImpl.SESSION_ID, rrc.getSessionId());
			//
			int[] returnValue = initDefineNumber(dao, rrc, appKey,
					RopDaoImpl.APP_FUN_DEF, pramas);

			int n_max_num = returnValue[0];

			int rqeustNum = dao.findRquestNumber(pramas,
					RopDaoImpl.APP_FUN_REQ_LEVEL, false);
			if (n_max_num > 0 && rqeustNum > n_max_num) {
				return true;
			}

		} catch (Exception ex) {

		}

		return false;
	}

	/**
	 * 初始化信息
	 * 
	 * @param rrc
	 * @param appKey
	 * @return
	 */
	private final int NAMESPACE = 100;
	private final String KEY_PREFIX = "QUERY_DEFMAX_";
	private int[] initDefineNumber(RopDaoImpl dao, RopRequestContext rrc,
			String appId, String level, Map<String, String> pramas) {
		int n_max_num = 0, n_session_max_num = 0;
		try {
			String KEY = KEY_PREFIX + level +  appId;
			Object obj = CacheFactory.getDefaultCache().get(KEY);
			//
			if(obj != null) {
				 int[] arr_max = (int[]) obj;
				 return arr_max;
			}						
			pramas.put(RopDaoImpl.APP_LEVEL, CurrentThreadVar.getVar().getAppLevel());
			String max_num = "0", session_max_num = "0";
			try {
				Map resultMap = dao.findDefineInvokeTimes(pramas, level);
				max_num = (String) resultMap.get("max_num");
				session_max_num = (String) resultMap.get("session_max");
				n_max_num = Integer.parseInt(max_num);
				n_session_max_num = Integer.parseInt(session_max_num);
				//
				CacheFactory.getDefaultCache().set(NAMESPACE,KEY,new int[] {n_max_num, n_session_max_num},CacheValues.oneDaySecond);
				//
			} catch (Exception e) {
				n_max_num = 0;
				n_session_max_num = 0;
			}

		} catch (Exception ex) {
			// ex.printStackTrace();
		}
		
		
		
		return new int[] { n_max_num, n_session_max_num };
	}

}
