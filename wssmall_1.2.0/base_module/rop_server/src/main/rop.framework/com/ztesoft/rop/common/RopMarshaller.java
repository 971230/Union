package com.ztesoft.rop.common;

import java.io.OutputStream;

/**
 * <pre>
 *   负责将请求方法返回的{@link RopResponse}流化为相应格式的内容。
 * </pre>
 * 
 * @author
 * @version 1.0
 */
public interface RopMarshaller {
	void marshaller(Object object, OutputStream outputStream);
}
