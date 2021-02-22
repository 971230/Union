/*
 * Copyright (C) 2003, 2004 Joe Walnes.
 * Copyright (C) 2006, 2007 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 26. September 2003 by Joe Walnes
 */
package com.ztesoft.inf.extend.xstream.converters.basic;

import com.ztesoft.inf.extend.xstream.converters.Converter;
import com.ztesoft.inf.extend.xstream.converters.MarshallingContext;
import com.ztesoft.inf.extend.xstream.converters.SingleValueConverter;
import com.ztesoft.inf.extend.xstream.converters.UnmarshallingContext;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;

/**
 * Converts a char primitive or java.lang.Character wrapper to a String. If char
 * is \0 the representing String is empty.
 * 
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 */
public class CharConverter implements Converter, SingleValueConverter {

	@Override
	public boolean canConvert(Class type) {
		return type.equals(char.class) || type.equals(Character.class);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		writer.setValue(toString(source));
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		String nullAttribute = reader.getAttribute("null");
		if (nullAttribute != null && nullAttribute.equals("true")) {
			return new Character('\0');
		} else {
			return fromString(reader.getValue());
		}
	}

	@Override
	public Object fromString(String str) {
		if (str.length() == 0) {
			return new Character('\0');
		} else {
			return new Character(str.charAt(0));
		}
	}

	@Override
	public String toString(Object obj) {
		char ch = ((Character) obj).charValue();
		return ch == '\0' ? "" : obj.toString();
	}

}
