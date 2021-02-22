
package com.ztesoft.rop.security;

import com.ztesoft.rop.common.RopRequestContext;
import com.ztesoft.rop.session.Session;



/**
 * <pre>
 *   服务访问次数及频率的控制管理器
 * </pre>
 *
 * @author 
 * @version 1.0
 */
public interface InvokeTimesController {

    /**
     * 计算应用、会话及用户服务调度总数
     * @param appKey
     * @param session
     */
    void caculateInvokeTimes(RopRequestContext rrc,String appKey, Session session);
    
    /**
     * 计算应用、会话及用户服务调度总数
     * @param appKey
     * @param session
     */
    void caculateInvokeTimes(RopRequestContext rrc,String appKey,String method,String version, Session session);
    
    
    /**
     * 应用调用频率是否超过次数
     * @param appKey
     * @return
     */
    boolean isAppSessionInvokeFrequencyExceed(RopRequestContext rrc,String appKey,Session session);

    /**
     * 应用服务访问次数是否超限
     * @param session
     * @return
     */
    boolean isAppInvokeLimitExceed(RopRequestContext rrc,String appKey);

  
    /**
     * 应用的服务方法访问次数是否超限
     * @param appKey
     * @return
     */
    boolean isAppMethodInvokeLimitExceed(RopRequestContext rrc,String appKey);

  
}

