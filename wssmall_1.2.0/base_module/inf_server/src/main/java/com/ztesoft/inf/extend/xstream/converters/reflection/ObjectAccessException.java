/*
 * Copyright (C) 2004 Joe Walnes.
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

import com.ztesoft.inf.extend.xstream.XStreamException;

public class ObjectAccessException extends XStreamException {
	private static final long serialVersionUID = 1L;

	public ObjectAccessException(String message) {
		super(message);
	}

	public ObjectAccessException(String message, Throwable cause) {
		super(message, cause);
	}
}
