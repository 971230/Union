/*
 * Copyright (C) 2006, 2007 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 15. February 2006 by Mauro Talevi
 */
package com.ztesoft.inf.extend.xstream.converters.basic;

import com.ztesoft.inf.extend.xstream.converters.SingleValueConverter;

/**
 * Base abstract implementation of
 * {@link com.ztesoft.inf.extend.xstream.converters.SingleValueConverter}.
 * <p/>
 * <p>
 * Subclasses should implement methods canConvert(Class) and fromString(String)
 * for the conversion.
 * </p>
 * 
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 * @author Mauro Talevi
 * @see com.ztesoft.inf.extend.xstream.converters.SingleValueConverter
 */
public abstract class AbstractSingleValueConverter implements
		SingleValueConverter {

	@Override
	public abstract boolean canConvert(Class type);

	@Override
	public String toString(Object obj) {
		return obj == null ? null : obj.toString();
	}

	@Override
	public abstract Object fromString(String str);

}
