/*
 * Copyright (C) 2004, 2005 Joe Walnes.
 * Copyright (C) 2006, 2007 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 16. March 2004 by Joe Walnes
 */
package com.ztesoft.inf.extend.xstream.core;

import com.ztesoft.inf.extend.xstream.converters.ConverterLookup;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
import com.ztesoft.inf.extend.xstream.mapper.MapperContext;

public class TreeMarshallingStrategy extends AbstractTreeMarshallingStrategy {

	@Override
	protected TreeUnmarshaller createUnmarshallingContext(Object root,
			HierarchicalStreamReader reader, ConverterLookup converterLookup,
			MapperContext mapperCtx) {
		return new TreeUnmarshaller(root, reader, converterLookup, mapperCtx);
	}

	@Override
	protected TreeMarshaller createMarshallingContext(
			HierarchicalStreamWriter writer, ConverterLookup converterLookup,
			MapperContext mapperCtx) {
		return new TreeMarshaller(writer, converterLookup, mapperCtx);
	}
}
