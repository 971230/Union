/*
 * Copyright (C) 2008 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 09. October 2008 by Joerg Schaible
 */
package com.ztesoft.inf.extend.xstream.core.util;

import com.ztesoft.inf.extend.xstream.converters.UnmarshallingContext;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
import com.ztesoft.inf.extend.xstream.io.path.PathTracker;
import com.ztesoft.inf.extend.xstream.mapper.CannotResolveClassException;
import com.ztesoft.inf.extend.xstream.mapper.Mapper;

/**
 * Helper methods for {@link HierarchicalStreamReader} and
 * {@link HierarchicalStreamWriter}.
 * 
 * @author J&ouml;rg Schaible
 * @since 1.3.1
 */
public class HierarchicalStreams {

	public static Class readClassType(HierarchicalStreamReader reader,
			Mapper mapper, UnmarshallingContext context) {
		return readClassType(reader, mapper, context, null);
	}

	public static Class readClassType(HierarchicalStreamReader reader,
			Mapper mapper, UnmarshallingContext context, Class defaultType) {
		String classAttribute = readClassAttribute(reader, mapper);
		Class type = readClassByXPath(mapper, context);
		if (type == null) {
			type = readClassType(classAttribute == null ? reader.getNodeName()
					: classAttribute, reader, mapper, defaultType);
		}
		return type;
	}

	public static Class readClassByXPath(Mapper mapper,
			UnmarshallingContext context) {
		PathTracker pathTracker = context.getPathTracker();
		if (pathTracker != null) {
			return mapper.realClassByPath(pathTracker.getPath().toString());
		}
		return null;
	}

	public static Class readClassType(String elementName,
			HierarchicalStreamReader reader, Mapper mapper) {
		return mapper.realClass(elementName);
	}

	public static Class readClassType(String elementName,
			HierarchicalStreamReader reader, Mapper mapper, Class defaultType) {
		try {
			return mapper.realClass(elementName);
		} catch (CannotResolveClassException e) {
			if (defaultType != null)
				return mapper.defaultImplementationOf(defaultType);
			throw e;
		}

	}

	public static String readClassAttribute(HierarchicalStreamReader reader,
			Mapper mapper) {
		String attributeName = mapper.aliasForSystemAttribute("resolves-to");
		String classAttribute = attributeName == null ? null : reader
				.getAttribute(attributeName);
		if (classAttribute == null) {
			attributeName = mapper.aliasForSystemAttribute("class");
			if (attributeName != null) {
				classAttribute = reader.getAttribute(attributeName);
			}
		}
		return classAttribute;
	}

}
