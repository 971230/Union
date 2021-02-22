package com.ztesoft.inf.extend.xstream.core;

import java.util.Iterator;

import com.ztesoft.inf.extend.xstream.converters.ConversionException;
import com.ztesoft.inf.extend.xstream.converters.Converter;
import com.ztesoft.inf.extend.xstream.converters.ConverterLookup;
import com.ztesoft.inf.extend.xstream.converters.ErrorWriter;
import com.ztesoft.inf.extend.xstream.converters.UnmarshallingContext;
import com.ztesoft.inf.extend.xstream.core.util.FastStack;
import com.ztesoft.inf.extend.xstream.core.util.HierarchicalStreams;
import com.ztesoft.inf.extend.xstream.core.util.PrioritizedList;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
import com.ztesoft.inf.extend.xstream.io.path.PathTracker;
import com.ztesoft.inf.extend.xstream.io.path.PathTrackingReader;
import com.ztesoft.inf.extend.xstream.mapper.Mapper;
import com.ztesoft.inf.extend.xstream.mapper.MapperContext;

public class TreeUnmarshaller implements UnmarshallingContext {

	private Object root;
	protected HierarchicalStreamReader reader;
	private ConverterLookup converterLookup;
	private Mapper mapper;
	private MapperContext mapperCtx;
	private FastStack types = new FastStack(16);
	private final PrioritizedList validationList = new PrioritizedList();
	protected PathTracker pathTracker = new PathTracker();

	public TreeUnmarshaller(Object root, HierarchicalStreamReader reader,
			ConverterLookup converterLookup, MapperContext mapperCtx) {
		this.root = root;
		this.reader = new PathTrackingReader(reader, pathTracker);
		this.converterLookup = converterLookup;
		this.mapperCtx = mapperCtx;
		this.mapper = mapperCtx.getMapper();
	}

	@Override
	public Object convertAnother(Object parent, Class type) {
		return convertAnother(parent, type, null);
	}

	@Override
	public Object convertAnother(Object parent, Class _type,
			Converter _converter) {
		Class type = _type;
		Converter converter = _converter;
		type = mapper.defaultImplementationOf(type);
		if (converter == null) {
			converter = converterLookup.lookupConverterForType(type);
		} else {
			if (!converter.canConvert(type)) {
				ConversionException e = new ConversionException(
						"Explicit selected converter cannot handle type");
				e.add("item-type", type.getName());
				e.add("converter-type", converter.getClass().getName());
				throw e;
			}
		}
		return convert(parent, type, converter);
	}

	protected Object convert(Object parent, Class type, Converter converter) {
		try {
			types.push(type);
			Object result = converter.unmarshal(reader, this);
			types.popSilently();
			return result;
		} catch (ConversionException conversionException) {
			addInformationTo(conversionException, type);
			throw conversionException;
		} catch (RuntimeException e) {
			ConversionException conversionException = new ConversionException(e);
			addInformationTo(conversionException, type);
			throw conversionException;
		}
	}

	private void addInformationTo(ErrorWriter errorWriter, Class type) {
		errorWriter.add("class", type.getName());
		errorWriter.add("required-type", getRequiredType().getName());
		reader.appendErrors(errorWriter);
	}

	@Override
	public void addCompletionCallback(Runnable work, int priority) {
		validationList.add(work, priority);
	}

	@Override
	public Object currentObject() {
		return types.size() == 1 ? root : null;
	}

	@Override
	public Class getRequiredType() {
		return (Class) types.peek();
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

	public Object start() {
		Class type = HierarchicalStreams.readClassType(reader, mapper, this,
				mapper.defaultImplementationOf(mapperCtx.getRootType()));
		Object result = convertAnother(null, type);
		Iterator validations = validationList.iterator();
		while (validations.hasNext()) {
			Runnable runnable = (Runnable) validations.next();
			runnable.run();
		}
		return result;
	}

	@Override
	public Mapper getMapper() {
		return this.mapper;
	}

	@Override
	public PathTracker getPathTracker() {
		return pathTracker;
	}
}
