package net.buffalo.protocal.converters.basic;

import net.buffalo.protocal.ProtocalTag;
import net.buffalo.protocal.converters.Converter;

public class IntegerConverter extends AbstractBasicConverter implements Converter {

	@Override
	protected String getType() {
		return ProtocalTag.TAG_INT;
	}

	@Override
	public boolean canConvert(Class value) {
		if (value == null) return false;
		return value.equals(int.class) ||
				value.equals(Integer.class) ||
				value.equals(short.class) ||
				value.equals(Short.class) ||
				value.equals(byte.class) ||
				value.equals(Byte.class);
	}
	
	@Override
	public Object fromString(String string) {
		return Integer.valueOf(string);
	}
	
}
