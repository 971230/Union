package com.ztesoft.api.internal.parser.fjson;

import com.ztesoft.api.ApiException;
import com.ztesoft.api.internal.mapping.Converter;
import params.ZteResponse;
/**
 * JSON格式转换器。
 * 
 * @author carver.gu
 * @since 1.0, Apr 11, 2010
 */
public class JsonConverter implements Converter {

	@Override
	public <T extends ZteResponse> T toResponse(String rsp, Class<T> clazz) throws ApiException {
//		JSONReader reader = new JSONValidatingReader(new ExceptionErrorListener());
//		Object rootObj = reader.read(rsp);
//		if (rootObj instanceof Map<?, ?>) {
//			Map<?, ?> rootJson = (Map<?, ?>) rootObj;
//			Collection<?> values = rootJson.values();
//			for (Object rspObj : values) {
//				if (rspObj instanceof Map<?, ?>) {
//					Map<?, ?> rspJson = (Map<?, ?>) rspObj;
//					return fromJson(rspJson, clazz);
//				}
//			}
//		}
//		return null;
		
		return null;
	}


}
