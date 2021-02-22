/*
 * Copyright (C) 2004, 2005, 2006 Joe Walnes.
 * Copyright (C) 2006, 2007 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 07. March 2004 by Joe Walnes
 */
package com.ztesoft.inf.extend.xstream.converters.reflection;

public class ReflectionConverter extends AbstractReflectionConverter {

	public ReflectionConverter(ReflectionProvider reflectionProvider) {
		super(reflectionProvider);
	}

	@Override
	public boolean canConvert(Class type) {
		return true;
	}
}
