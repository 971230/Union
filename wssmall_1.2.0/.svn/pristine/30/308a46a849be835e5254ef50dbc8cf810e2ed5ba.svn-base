package com.ztesoft.api;

import params.ZteBusiRequest;
import params.ZteRequest;
import params.ZteResponse;


/**
 * ZTE客户端。
 * 
 * @author wu.i
 * @since 2013-12-25
 */
public interface ZteClient {
	
	/**
	 * 执行TOP公开API请求。
	 * @param <T>
	 * @param request 具体的TOP请求
	 * @return
	 * @throws ApiException
	 */
	public <T extends ZteResponse> T execute(ZteBusiRequest request,Class<T> class1) throws ApiException ;
	
	/**
	 * 执行TOP公开API请求。
	 * @param <T>
	 * @param request 具体的TOP请求
	 * @return
	 * @throws ApiException
	 */
	public <T extends ZteResponse> T execute(ZteRequest request,Class<T> class1) throws ApiException ;
	/**
	 * 执行TOP隐私API请求。
	 * @param <T>
	 * @param request 具体的TOP请求
	 * @param session 用户会话授权码
	 * @return
	 * @throws ApiException
	 */
	public <T extends ZteResponse> T execute(ZteRequest request,Class<T> class1, String session) throws ApiException ;

}
