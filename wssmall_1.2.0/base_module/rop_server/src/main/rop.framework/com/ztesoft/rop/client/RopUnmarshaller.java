
package com.ztesoft.rop.client;

/**
 * <pre>
 *   对响应进行反流化
 * </pre>
 *
 * @author 
 * @version 1.0
 */
public interface RopUnmarshaller {

    /**
     * 将字符串反序列化为相应的对象
     *
     * @param inputStream
     * @param objectType
     * @param <T>
     * @return
     */
    <T> T unmarshaller(String content, Class<T> objectType);
}

