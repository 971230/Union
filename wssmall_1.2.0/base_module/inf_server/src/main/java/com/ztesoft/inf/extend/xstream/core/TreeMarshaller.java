/*
 * Copyright (C) 2004, 2005, 2006 Joe Walnes.
 * Copyright (C) 2006, 2007 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 15. March 2004 by Joe Walnes
 */
package com.ztesoft.inf.extend.xstream.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.ztesoft.inf.extend.xstream.XStreamException;
import com.ztesoft.inf.extend.xstream.converters.ConversionException;
import com.ztesoft.inf.extend.xstream.converters.Converter;
import com.ztesoft.inf.extend.xstream.converters.ConverterLookup;
import com.ztesoft.inf.extend.xstream.converters.MarshallingContext;
import com.ztesoft.inf.extend.xstream.core.util.ObjectIdDictionary;
import com.ztesoft.inf.extend.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
import com.ztesoft.inf.extend.xstream.io.path.PathTracker;
import com.ztesoft.inf.extend.xstream.io.path.PathTrackingWriter;
import com.ztesoft.inf.extend.xstream.mapper.Mapper;
import com.ztesoft.inf.extend.xstream.mapper.MapperContext;

public class TreeMarshaller implements MarshallingContext {

	protected HierarchicalStreamWriter writer;
	protected ConverterLookup converterLookup;
	private Mapper mapper;
	private MapperContext mapperCtx;
	private ObjectIdDictionary parentObjects = new ObjectIdDictionary();
	protected PathTracker pathTracker = new PathTracker();

	public TreeMarshaller(HierarchicalStreamWriter writer,
			ConverterLookup converterLookup, MapperContext mapperCtx) {
		this.writer = new PathTrackingWriter(writer, pathTracker);
		this.converterLookup = converterLookup;
		this.mapperCtx = mapperCtx;
		this.mapper = mapperCtx.getMapper();
	}

	@Override
	public void convertAnother(Object item) {
		convertAnother(item, null);
	}

	@Override
	public void convertAnother(Object item, Converter _converter) {
		Converter converter = _converter;
		if (converter == null) {
			converter = converterLookup.lookupConverterForType(item.getClass());
		} else {
			if (!converter.canConvert(item.getClass())) {
				ConversionException e = new ConversionException(
						"Explicit selected converter cannot handle item");
				e.add("item-type", item.getClass().getName());
				e.add("converter-type", converter.getClass().getName());
				throw e;
			}
		}
		convert(item, converter);
	}

	protected void convert(Object item, Converter converter) {
		// logger.info(pathTracker.getPath());
		if (parentObjects.containsId(item)) {
			throw new CircularReferenceException();
		}
		parentObjects.associateId(item, "");
		converter.marshal(item, writer, this);
		parentObjects.removeId(item);
	}

	public void start(Object item) {
		if (item == null) {
			writer.startNode(mapper.serializedClass(null));
			writer.endNode();
		} else {
			String nodeName = null;
			nodeName = (String) get(Constants.ROOT_NMAE);
			if (item instanceof Map || item instanceof Collection) {
				if (nodeName == null) {
					throw new XStreamException(
							"root object is a Map or Collection,must set rootName!");
				}
			}
			if (nodeName == null || nodeName.trim().length() < 1) {
				nodeName = mapper.serializedClass(item.getClass());
			}
			ExtendedHierarchicalStreamWriterHelper.startNode(writer, nodeName,
					item.getClass());
			convertAnother(item);
			writer.endNode();
		}
	}

	@Override
	public Object get(Object key) {
		return mapperCtx.get(key);
	}

	@Override
	public void put(Object key, Object value) {
		mapperCtx.put(key, value);
	}

	@Override
	public Iterator keys() {
		return mapperCtx.keys();
	}

	@Override
	public Mapper getMapper() {
		return this.mapper;
	}

	public static class CircularReferenceException extends XStreamException {

		private static final long serialVersionUID = 1L;
	}

	@Override
	public PathTracker getPathTracker() {
		return pathTracker;
	}
}
