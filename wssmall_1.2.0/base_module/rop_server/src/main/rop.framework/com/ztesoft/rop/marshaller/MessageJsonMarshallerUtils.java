package com.ztesoft.rop.marshaller;

import com.ztesoft.rop.common.RopException;
import com.ztesoft.rop.common.RopRequest;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class MessageJsonMarshallerUtils {
	protected static final Logger logger = LoggerFactory.getLogger(MessageJsonMarshallerUtils.class);

	private static final String UTF_8 = "utf-8";

	private static ObjectMapper jsonObjectMapper = new ObjectMapper();

	static {
		SerializationConfig serializationConfig = jsonObjectMapper
				.getSerializationConfig();
		serializationConfig = serializationConfig.without(
				SerializationConfig.Feature.WRAP_ROOT_VALUE).with(
				SerializationConfig.Feature.INDENT_OUTPUT);
	}

	/**
	 * 将请求对象转换为String
	 * 
	 * @param request
	 * @param format
	 * @return
	 */
	public static String getMessage(RopRequest request, String format) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
			if (format.equals("json")) {
				if (request.getRopRequestContext() != null) {
					jsonObjectMapper.writeValue(bos, request
							.getRopRequestContext().getAllParams());
				} else {
					return "";
				}
			}
			return bos.toString(UTF_8);
		} catch (Exception e) {
			throw new RopException(e);
		}
	}

	/**
	 * 将请求对象转换为String
	 * 
	 * @param request
	 * @param format
	 * @return
	 */
	public static String asUrlString(RopRequest request) {
		Map<String, String> allParams = request.getRopRequestContext()
				.getAllParams();
		StringBuilder sb = new StringBuilder(256);
		boolean first = true;
		for (Map.Entry<String, String> entry : allParams.entrySet()) {
			if (!first) {
				sb.append("&");
			}
			first = false;
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
		}
		return sb.toString();
	}

	/**
	 * 将{@link RopRequest}转换为字符串
	 * 
	 * @param object
	 * @param format
	 * @return
	 */
	public static String getMessage(Object object, String format) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
		try {
			if (format.equals("json")) {
				JsonGenerator jsonGenerator = jsonObjectMapper.getJsonFactory()
						.createJsonGenerator(bos, JsonEncoding.UTF8);
				jsonObjectMapper.writeValue(jsonGenerator, object);
			}
			return bos.toString(UTF_8);
		} catch (Throwable e) {
			throw new RopException(e);
		} finally {
			try {
				bos.close();
			} catch (IOException ee) {
				logger.info("消息转换异常", ee);
			}
		}
	}
}
