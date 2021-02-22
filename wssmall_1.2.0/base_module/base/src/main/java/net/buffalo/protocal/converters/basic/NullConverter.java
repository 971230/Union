package net.buffalo.protocal.converters.basic;

import net.buffalo.protocal.ProtocalTag;
import net.buffalo.protocal.converters.Converter;
import net.buffalo.protocal.io.MarshallingContext;
import net.buffalo.protocal.io.StreamReader;
import net.buffalo.protocal.io.StreamWriter;
import net.buffalo.protocal.io.UnmarshallingContext;

public class NullConverter implements Converter{

	@Override
	public boolean canConvert(Class type) {
		return type == null;
	}

	@Override
	public void marshal(Object object, MarshallingContext context, StreamWriter streamWriter) {
		streamWriter.startNode(ProtocalTag.TAG_NULL);
		streamWriter.endNode();
	}

	@Override
	public Object unmarshal(StreamReader reader, UnmarshallingContext unmarshallingContext) {
		return null;
	}

}
