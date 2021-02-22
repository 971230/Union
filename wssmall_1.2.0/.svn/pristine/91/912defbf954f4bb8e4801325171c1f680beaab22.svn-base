/*
 * Copyright (C) 2003, 2004, 2005 Joe Walnes.
 * Copyright (C) 2006, 2007, 2008 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 26. September 2003 by Joe Walnes
 */
package com.ztesoft.inf.extend.xstream.converters.collections;

import java.util.Map;

import com.ztesoft.inf.extend.xstream.converters.ConversionException;
import com.ztesoft.inf.extend.xstream.converters.Converter;
import com.ztesoft.inf.extend.xstream.converters.MarshallingContext;
import com.ztesoft.inf.extend.xstream.converters.UnmarshallingContext;
import com.ztesoft.inf.extend.xstream.core.util.HierarchicalStreams;
import com.ztesoft.inf.extend.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
import com.ztesoft.inf.extend.xstream.mapper.Mapper;

/**
 * Base helper class for converters that need to handle collections of items
 * (arrays, Lists, Maps, etc).
 * <p/>
 * <p>
 * Typically, subclasses of this will converter the outer structure of the
 * collection, loop through the contents and call readItem() or writeItem() for
 * each item.
 * </p>
 * 
 * @author Joe Walnes
 */
public abstract class AbstractCollectionConverter implements Converter {

	public abstract boolean canConvert(Class type);

	public AbstractCollectionConverter() {
	}

	public abstract void marshal(Object source,
			HierarchicalStreamWriter writer, MarshallingContext context);

	public abstract Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context);

	protected void writeItem(Object item, MarshallingContext context,
			HierarchicalStreamWriter writer) {
		// PUBLISHED API METHOD! If changing signature, ensure backwards
		// compatibility.
		final Mapper mapper = context.getMapper();
		if (item == null) {
			// todo: this is duplicated in TreeMarshaller.start()
			String name = mapper.serializedClass(null);
			writer.startNode(name);
			writer.endNode();
		} else {
			String path = context.getPathTracker().getPath().toString();
			String name = mapper.getColFieldNameByPath(path);
			if (name == null) {
				if (item instanceof Map)
					name = mapper.genMapNodeNameByPath(path);
				else
					name = mapper.serializedClass(item.getClass());
			}
			ExtendedHierarchicalStreamWriterHelper.startNode(writer, name,
					item.getClass());
			context.convertAnother(item);
			writer.endNode();
		}
	}

	protected Object readItem(HierarchicalStreamReader reader,
			UnmarshallingContext context, Object current) {
		final Mapper mapper = context.getMapper();
		Class type = HierarchicalStreams.readClassType(reader, mapper, context,
				Map.class);
		return context.convertAnother(current, type);
	}

	protected Object createCollection(Class type, UnmarshallingContext context) {
		final Mapper mapper = context.getMapper();
		Class defaultType = mapper.defaultImplementationOf(type);
		try {
			return defaultType.newInstance();
		} catch (InstantiationException e) {
			throw new ConversionException("Cannot instantiate "
					+ defaultType.getName(), e);
		} catch (IllegalAccessException e) {
			throw new ConversionException("Cannot instantiate "
					+ defaultType.getName(), e);
		}
	}
}
