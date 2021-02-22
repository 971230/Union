/*
 * Copyright (C) 2003, 2004 Joe Walnes.
 * Copyright (C) 2006, 2007 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 03. October 2003 by Joe Walnes
 */
package com.ztesoft.inf.extend.xstream.converters.basic;

import com.ztesoft.inf.extend.xstream.converters.Converter;
import com.ztesoft.inf.extend.xstream.converters.MarshallingContext;
import com.ztesoft.inf.extend.xstream.converters.UnmarshallingContext;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
import com.ztesoft.inf.extend.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.ztesoft.inf.extend.xstream.mapper.Mapper;

/**
 * Special converter to signify nulls at the root level.
 * 
 * @author Joe Walnes
 */
public class NullConverter implements Converter {

	@Override
	public boolean canConvert(Class type) {
		return type == null || Mapper.Null.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		ExtendedHierarchicalStreamWriterHelper.startNode(writer, "null", null);
		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return null;
	}
}
