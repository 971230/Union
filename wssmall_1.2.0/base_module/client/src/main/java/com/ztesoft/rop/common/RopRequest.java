/**
 *
 * 日    期：12-2-10
 */
package com.ztesoft.rop.common;

import com.ztesoft.net.framework.database.NotDbField;

import java.io.Serializable;

/**
 * <pre>
 *    ROP服务的请求对象，请求方法的入参必须是继承于该类。
 * </pre>
 *
 * @author 陈雄华
 * @version 1.0
 */
public interface RopRequest extends Serializable{

	@NotDbField
	public String getSessionId();
	@NotDbField
	public void setSessionId(String sessionId);
	
	@NotDbField
	public String getUserSessionId();
	@NotDbField
	public void setUserSessionId(String userSessionId);
	
    /**
     * 获取服务方法的上下文
     *
     * @return
     */
	@NotDbField
    RopRequestContext getRopRequestContext();

}

