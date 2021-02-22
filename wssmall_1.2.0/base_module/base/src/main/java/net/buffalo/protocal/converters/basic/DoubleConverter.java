package net.buffalo.protocal.converters.basic;

import net.buffalo.protocal.ProtocalTag;
import net.buffalo.protocal.converters.Converter;

public class DoubleConverter extends AbstractBasicConverter implements Converter{

	@Override
	public boolean canConvert(Class value) {
		if (value == null) return false;
		return value.equals(double.class) || 
				value.equals(Double.class) ||
				value.equals(float.class) ||
				value.equals(Float.class);
	}
	
	@Override
	protected String getType() {
		return ProtocalTag.TAG_DOUBLE;
	}

	@Override
	public Object fromString(String string) {
		return Double.valueOf(string);
	}
	
}
