package net.buffalo.protocal.converters.basic;

import net.buffalo.protocal.ProtocalTag;
import net.buffalo.protocal.converters.Converter;

public class LongConverter extends AbstractBasicConverter implements Converter {

	@Override
	protected String getType() {
		return ProtocalTag.TAG_LONG;
	}
	@Override
	public boolean canConvert(Class value) {
		if (value == null) return false;
		return value.equals(long.class) || 
				value.equals(Long.class);
	}
	
	@Override
	public Object fromString(String string) {
		return new Long(string);
	}
	
}
