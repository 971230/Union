package com.ztesoft.rop.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rop.core.memcached.cache.CacheFactory;
import com.rop.core.memcached.util.CacheValues;
import com.ztesoft.net.cache.common.INetCache;

/**
 * @author 
 * @author 
 */
public final class DefaultSessionManager implements SessionManager {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    public static final String KEY_PREFIX = "FJ_OPEN_";

  //  private final Map<String, Session> sessionCache = new ConcurrentHashMap<String, Session>(128, 0.75f, 32);
    private final int NAMESPACE = 0;
    @Override
    public void addSession(String sessionId, Session session) {
        //sessionCache.put(sessionId, session);    
//    	com.ztesoft.net.cache.common.CacheFactory.getCache();
    	INetCache cache  = CacheFactory.getDefaultCache();
    	cache.set(NAMESPACE,KEY_PREFIX + sessionId, session,  CacheValues.oneDaySecond );
//        CacheFactory.getDefaultCache().set(KEY_PREFIX + sessionId, CacheValues.oneDaySecond , session);
    }

    @Override
    public Session getSession(String sessionId) {
    	INetCache cache  = CacheFactory.getDefaultCache();
    	return (Session) cache.get(KEY_PREFIX + sessionId)	;
       // return sessionCache.get(KEY_PREFIX + sessionId);
    }

    @Override
    public void removeSession(String sessionId) {
    	INetCache cache  = CacheFactory.getDefaultCache();
    	cache.delete(KEY_PREFIX + sessionId);
//    	CacheFactory.getDefaultCache().deleteWithNoReply(KEY_PREFIX + sessionId);
       // sessionCache.remove(KEY_PREFIX + sessionId);
    }
}