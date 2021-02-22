/*
 * Copyright (C) 2006, 2007, 2008 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 26.09.2007 by Joerg Schaible
 */
package com.ztesoft.inf.extend.xstream.core;

import com.ztesoft.inf.extend.xstream.MarshallingStrategy;
import com.ztesoft.inf.extend.xstream.converters.ConverterLookup;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
import com.ztesoft.inf.extend.xstream.mapper.MapperContext;

/**
 * Basic functionality of a tree based marshalling strategy.
 * 
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 * @since 1.3
 */
public abstract class AbstractTreeMarshallingStrategy implements
		MarshallingStrategy {

	@Override
	public Object unmarshal(Object root, HierarchicalStreamReader reader,
			ConverterLookup converterLookup, MapperContext mapperCtx) {
		TreeUnmarshaller context = createUnmarshallingContext(root, reader,
				converterLookup, mapperCtx);
		return context.start();
	}

	@Override
	public void marshal(HierarchicalStreamWriter writer, Object obj,
			ConverterLookup converterLookup, MapperContext mapperCtx) {
		TreeMarshaller context = createMarshallingContext(writer,
				converterLookup, mapperCtx);
		context.start(obj);
	}

	protected abstract TreeUnmarshaller createUnmarshallingContext(Object root,
			HierarchicalStreamReader reader, ConverterLookup converterLookup,
			MapperContext mapper);

	protected abstract TreeMarshaller createMarshallingContext(
			HierarchicalStreamWriter writer, ConverterLookup converterLookup,
			MapperContext mapper);

}
