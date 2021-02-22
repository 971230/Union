package services;

import params.adminuser.req.SinLoginReq;
import params.adminuser.resp.SinLoginResp;


/**
 * 单点登录接口
 * @author hu.yi
 * @date 2013.12.23
 */
public interface SinLoginInf {

	/**
	 * 列出登录列表
	 * @return
	 */
	public SinLoginResp list(SinLoginReq sinLoginReq);
}
