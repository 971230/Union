package com.ztesoft.inf.extend.xstream.converters.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.ztesoft.inf.extend.xstream.converters.MarshallingContext;
import com.ztesoft.inf.extend.xstream.converters.UnmarshallingContext;
import com.ztesoft.inf.extend.xstream.converters.reflection.PureJavaReflectionProvider;
import com.ztesoft.inf.extend.xstream.converters.reflection.ReflectionProvider;
import com.ztesoft.inf.extend.xstream.core.util.HierarchicalStreams;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
import com.ztesoft.inf.extend.xstream.io.path.PathTracker;
import com.ztesoft.inf.extend.xstream.mapper.CannotResolveClassException;
import com.ztesoft.inf.extend.xstream.mapper.Mapper;

public class MapConverter extends AbstractCollectionConverter {

	private ReflectionProvider pureJavaReflectionProvider;

	@Override
	public boolean canConvert(Class type) {
		return Map.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		final Mapper mapper = context.getMapper();
		Set<Map.Entry> set = ((Map) source).entrySet();
		for (Map.Entry entry : set) {
			Object value = entry.getValue();
			String fieldName = entry.getKey().toString();
			PathTracker tracker = context.getPathTracker();
			tracker.pushElement(fieldName);
			String nodeName = mapper
					.getImplicitCollectionItemNameByPath(tracker.getPath()
							.toString());
			tracker.popElement();
			boolean isImplicitCollection = false;
			if (nodeName != null) {
				if (!(value instanceof Collection)) {
					throw new RuntimeException();// TODO
				}
				isImplicitCollection = true;
			} else {
				if (mapper.globalImplicitCollection()
						&& (value instanceof Collection)) {
					isImplicitCollection = true;
				}
				nodeName = fieldName;
			}
			if (isImplicitCollection) {
				Collection list = (Collection) value;
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					Object obj = iter.next();
					writer.startNode(nodeName);
					context.convertAnother(obj);
					writer.endNode();
				}
			} else {
				writer.startNode(fieldName);
				if (value != null)
					context.convertAnother(value);
				writer.endNode();
			}
		}
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Map map = (Map) createCollection(context.getRequiredType(), context);
		populateMap(reader, context, map);
		return map;
	}

	protected void populateMap(HierarchicalStreamReader reader,
			UnmarshallingContext context, Map result) {
		final Mapper mapper = context.getMapper();
		Map implicitCollectionsForCurrentObject = null;
		PathTracker pathTracker = context.getPathTracker();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			String fieldName = mapper.getColFieldNameByPath(pathTracker
					.getPath().toString());
			boolean isImplicitCollection = false;
			if (fieldName == null) {
				fieldName = mapper.getAliasNameByPath(context.getPathTracker()
						.getPath().toString());
				if (fieldName == null) {
					fieldName = reader.getNodeName();
				}
			} else {
				isImplicitCollection = true;
			}
			Class type;
			try {
				type = HierarchicalStreams.readClassType(reader, mapper,
						context);
			} catch (CannotResolveClassException e) {
				if (!reader.hasMoreChildren() && reader.getAttributeCount() < 1) {
					type = String.class;
				} else
					type = mapper.defaultImplementationOf(Map.class);
			}
			Object value = context.convertAnother(result, type);
			if (value instanceof Map) {
				Iterator<String> iter = reader.getAttributeNames();
				Map valueMap = (Map) value;
				String attrName;
				while (iter.hasNext()) {
					attrName = iter.next();
					valueMap.put(attrName, reader.getAttribute(attrName));
				}
			}
			if (isImplicitCollection) {
				if (implicitCollectionsForCurrentObject == null) {
					try {
						implicitCollectionsForCurrentObject = (Map) mapper
								.defaultImplementationOf(Map.class)
								.newInstance();
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
				implicitCollectionsForCurrentObject = writeValueToImplicitCollection(
						context, result, implicitCollectionsForCurrentObject,
						value, fieldName,
						mapper.defaultImplementationOf(Collection.class));
			} else
				result.put(fieldName, value);
			reader.moveUp();
		}
	}

	protected Map writeValueToImplicitCollection(UnmarshallingContext context,
			Map result, Map implicitCollections, Object value,
			String fieldName, Class fieldType) {
		Collection collection = (Collection) implicitCollections.get(fieldName);
		if (collection == null) {
			if (pureJavaReflectionProvider == null) {
				pureJavaReflectionProvider = new PureJavaReflectionProvider();
			}
			collection = (Collection) pureJavaReflectionProvider
					.newInstance(fieldType);
			result.put(fieldName, collection);
			implicitCollections.put(fieldName, collection);
		}
		collection.add(value);
		return implicitCollections;
	}
}
