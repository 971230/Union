package net.buffalo.protocal.converters.basic;

import net.buffalo.protocal.ProtocalTag;
import net.buffalo.protocal.converters.Converter;

public class StringConverter extends AbstractBasicConverter implements Converter{

	@Override
	protected String getType() {
		return ProtocalTag.TAG_STRING;
	}
	
	

	@Override
	public boolean canConvert(Class value) {
		if (value == null) return false;
		return value.equals(String.class) ||
				value.equals(char.class) ||
				value.equals(Character.class);
	}
	
	@Override
	public Object fromString(String string) {
		return string;
	}

}
