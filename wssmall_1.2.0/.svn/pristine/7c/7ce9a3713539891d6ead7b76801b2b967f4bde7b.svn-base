
package com.ztesoft.rop.security;

import com.ztesoft.rop.common.RopRequestContext;
import com.ztesoft.rop.session.Session;



/**
 * <pre>
 *    安全控制控制器，决定用户是否有。
 * </pre>
 *
 * @author 
 * @version 1.0
 */
public interface ServiceAccessController {

    /**
     * 服务方法是否向ISV开放
     * @param method
     * @param userId
     * @return
     */
    boolean isAppGranted(RopRequestContext rrc,String appKey, String method, String version);

    /**
     *  服务方法是否向当前用户开放
     * @param ropRequestContext
     * @return
     */
    boolean isUserGranted(RopRequestContext rrc,Session session,String method,String version);
}

