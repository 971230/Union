
package com.ztesoft.rop.marshaller;


import com.ztesoft.rop.common.RopException;
import com.ztesoft.rop.common.RopMarshaller;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <pre>
 *    将{@link com.rop.RopResponse}流化成JSON。 {@link ObjectMapper}是线程安全的。
 * </pre>
 *
 * @author
 * @version 1.0
 */
public class JacksonJsonRopMarshaller implements RopMarshaller {

    private static ObjectMapper objectMapper;
    @Override
	public void marshaller(Object object, OutputStream outputStream) {
        try {
           // JsonGenerator jsonGenerator = getObjectMapper().getJsonFactory().createJsonGenerator(outputStream, JsonEncoding.UTF8);
            
             String xml =  MessageMarshallerUtils.getMessage(object, "json");
             
             outputStream.write(xml.getBytes("utf-8"));
            
           /// getObjectMapper().writeValue(jsonGenerator, object);
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RopException(e);
        }
    }

    private ObjectMapper getObjectMapper() throws IOException {
        if (JacksonJsonRopMarshaller.objectMapper == null) {
            ObjectMapper objectMapper = new ObjectMapper();
            AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
            SerializationConfig serializationConfig = objectMapper.getSerializationConfig();
            serializationConfig = serializationConfig.without(SerializationConfig.Feature.WRAP_ROOT_VALUE)
                                                     .with(SerializationConfig.Feature.INDENT_OUTPUT)
                                                     .withAnnotationIntrospector(introspector);
            objectMapper.setSerializationConfig(serializationConfig);
            JacksonJsonRopMarshaller.objectMapper = objectMapper;
        }
        return JacksonJsonRopMarshaller.objectMapper;
    }
}

