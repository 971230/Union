
package com.ztesoft.rop.client;

import com.ztesoft.rop.response.ErrorResponse;



/**
 * <pre>
 *    客户端的响应,如果{@link #isSuccessful()}返回true，则调用{@link #getErrorResponse()}，反之，则应该
 * 调用{@link #getSuccessResponse(Class)}
 * </pre>
 *
 * @author 
 * @version 1.0
 */
public interface CompositeResponse<T> {

    /**
     * 获取错误的响应对象
     *
     * @return
     */
    ErrorResponse getErrorResponse();

    /**
     * 获取正确的响应对象
     *
     * @param responseClass
     * @param <T>
     * @return
     */
    T getSuccessResponse();

    /**
     * 响应是否是正确的
     *
     * @return
     */
    boolean isSuccessful();
}

