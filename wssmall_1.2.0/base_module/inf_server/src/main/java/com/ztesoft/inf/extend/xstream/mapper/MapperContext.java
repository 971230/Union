package com.ztesoft.inf.extend.xstream.mapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ztesoft.inf.extend.xstream.InitializationException;
import com.ztesoft.inf.extend.xstream.converters.ConverterLookup;
import com.ztesoft.inf.extend.xstream.converters.DataHolder;
import com.ztesoft.inf.extend.xstream.core.Constants;
import com.ztesoft.inf.extend.xstream.core.MapBackedDataHolder;


public class MapperContext implements DataHolder {

	private static final String ROOT_TYPE = "_ROOT_TYPE_";
	private Mapper mapper;
	private DefaultMapper defaultMapper;
	private ClassAliasingMapper classAliasingMapper;
	private FieldAliasingMapper fieldAliasingMapper;
	private XPathMapper xpathMapper;
	private AttributeAliasingMapper attributeAliasingMapper;
	private AttributeMapper attributeMapper;
	private DefaultImplementationsMapper defaultImplementationsMapper;
	private ImplicitCollectionMapper implicitCollectionMapper;
	private DataHolder dataHolder = new MapBackedDataHolder();

	public MapperContext() {
		mapper = buildMapper();
		setupMappers();
		setupDefaultImplementations();
		put(ROOT_TYPE, Map.class);
	}

	public void setClassLoader(ClassLoader classLoader) {
		defaultMapper.setClassLoader(classLoader);
	}

	public void setConverterLookup(ConverterLookup converterLookup) {
		attributeMapper.setConverterLookup(converterLookup);
	}

	private Mapper buildMapper() {
		Mapper mapper = new DefaultMapper();
		defaultMapper = (DefaultMapper) mapper;
		mapper = new ClassAliasingMapper(mapper);
		mapper = new FieldAliasingMapper(mapper);
		mapper = new AttributeAliasingMapper(mapper);
		mapper = new XPathMapper(mapper);
		mapper = new ImplicitCollectionMapper(mapper);
		mapper = new ArrayMapper(mapper);
		mapper = new DefaultImplementationsMapper(mapper);
		mapper = new AttributeMapper(mapper);
		mapper = new CachingMapper(mapper);
		return mapper;
	}

	protected void setupDefaultImplementations() {
		if (defaultImplementationsMapper == null) {
			return;
		}
		addDefaultImplementation(HashMap.class, Map.class);
		addDefaultImplementation(ArrayList.class, List.class);
		addDefaultImplementation(HashSet.class, Set.class);
		addDefaultImplementation(GregorianCalendar.class, Calendar.class);
	}

	private void setupMappers() {
		xpathMapper = (XPathMapper) this.mapper
				.lookupMapperOfType(XPathMapper.class);
		classAliasingMapper = (ClassAliasingMapper) this.mapper
				.lookupMapperOfType(ClassAliasingMapper.class);
		fieldAliasingMapper = (FieldAliasingMapper) this.mapper
				.lookupMapperOfType(FieldAliasingMapper.class);
		attributeMapper = (AttributeMapper) this.mapper
				.lookupMapperOfType(AttributeMapper.class);
		attributeAliasingMapper = (AttributeAliasingMapper) this.mapper
				.lookupMapperOfType(AttributeAliasingMapper.class);
		implicitCollectionMapper = (ImplicitCollectionMapper) this.mapper
				.lookupMapperOfType(ImplicitCollectionMapper.class);
		defaultImplementationsMapper = (DefaultImplementationsMapper) this.mapper
				.lookupMapperOfType(DefaultImplementationsMapper.class);
	}

	public void xpathToCollection(String xpath, String nodeName, String itemType) {
		Class clz = null;
		if (org.springframework.util.StringUtils.hasLength(itemType)) {
			try {
				clz = Class.forName(itemType);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException();
			}
		}
		xpathToCollection(xpath, nodeName, clz);
	}

	public void xpathToCollection(String xpath, String nodeName, Class _itemType) {
		if (xpathMapper == null) {
			throw new InitializationException("No "
					+ XPathMapper.class.getName() + " available");
		}
		xpathMapper.addXpathType(xpath, defaultImplementationsMapper
				.defaultImplementationOf(Collection.class));
		if (nodeName != null) {
			xpathMapper.addXpathAlias(xpath, nodeName);
		}
		Class itemType = _itemType;
		if (itemType == null) {
			itemType = defaultImplementationsMapper
					.defaultImplementationOf(Map.class);
		}
		xpathMapper.addXpathType(xpath + "/" + nodeName, itemType);
	}

	public void xpathAlias(String xpath, String aliasName) {
		xpathMapper.addXpathAlias(xpath, aliasName);
	}

	public void xpathToCollection(String xpath, String nodeName) {
		xpathToCollection(xpath, nodeName, (Class) null);
	}

	public void xpathToCollection(String xpath) {
		xpathToCollection(xpath, null, (Class) null);
	}

	public void setRootNodeName(String rootNodeName) {
		dataHolder.put(Constants.ROOT_NMAE, rootNodeName);
	}

	public void addXpathImplicitCollection(String xpath, String nodeName,
			String fieldName, String itemType) {
		Class clz = null;
		if (org.springframework.util.StringUtils.hasLength(itemType)) {
			try {
				clz = Class.forName(itemType);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException();
			}
		}
		addXpathImplicitCollection(xpath, nodeName, fieldName, clz);
	}

	public void addXpathImplicitCollection(String xpath, String nodeName,
			String fieldName, Class _itemType) {
		if (xpathMapper == null) {
			throw new InitializationException("No "
					+ XPathMapper.class.getName() + " available");
		}
		Class itemType = _itemType;
		if (_itemType == null) {
			itemType = defaultImplementationsMapper
					.defaultImplementationOf(Map.class);
		}
		xpathMapper.addXpathImplicitCollection(xpath, nodeName, fieldName,
				itemType);
	}

	public void addXpathImplicitCollection(String xpath, String nodeName,
			String fieldName) {
		addXpathImplicitCollection(xpath, nodeName, fieldName, (Class) null);
	}

	public void omitField(Class definedIn, String fieldName) {
		if (fieldAliasingMapper == null) {
			throw new InitializationException("No "
					+ FieldAliasingMapper.class.getName() + " available");
		}
		fieldAliasingMapper.omitField(definedIn, fieldName);
	}

	public void addImplicitCollection(Class ownerType, String fieldName) {
		if (implicitCollectionMapper == null) {
			throw new InitializationException("No "
					+ ImplicitCollectionMapper.class.getName() + " available");
		}
		implicitCollectionMapper.add(ownerType, fieldName, null, null);
	}

	public void addImplicitCollection(Class ownerType, String fieldName,
			Class itemType) {
		if (implicitCollectionMapper == null) {
			throw new InitializationException("No "
					+ ImplicitCollectionMapper.class.getName() + " available");
		}
		implicitCollectionMapper.add(ownerType, fieldName, null, itemType);
	}

	public void addImplicitCollection(Class ownerType, String fieldName,
			String itemFieldName, Class itemType) {
		if (implicitCollectionMapper == null) {
			throw new InitializationException("No "
					+ ImplicitCollectionMapper.class.getName() + " available");
		}
		implicitCollectionMapper.add(ownerType, fieldName, itemFieldName,
				itemType);
	}

	public void alias(String name, Class type) {
		if (classAliasingMapper == null) {
			throw new InitializationException("No "
					+ ClassAliasingMapper.class.getName() + " available");
		}
		classAliasingMapper.addClassAlias(name, type);
	}

	public void xpathToType(String name, Class type) {
		if (xpathMapper == null) {
			throw new InitializationException("No "
					+ XPathMapper.class.getName() + " available");
		}
		xpathMapper.addXpathType(name, type);
	}

	public void xpathToType(String name, String typeName) {
		if (xpathMapper == null) {
			throw new InitializationException("No "
					+ XPathMapper.class.getName() + " available");
		}
		xpathMapper.addXpathType(name, typeName);
	}

	public void aliasType(String name, Class type) {
		if (classAliasingMapper == null) {
			throw new InitializationException("No "
					+ ClassAliasingMapper.class.getName() + " available");
		}
		classAliasingMapper.addTypeAlias(name, type);
	}

	public void alias(String name, Class type, Class defaultImplementation) {
		alias(name, type);
		addDefaultImplementation(defaultImplementation, type);
	}

	public void aliasField(String alias, Class definedIn, String fieldName) {
		if (fieldAliasingMapper == null) {
			throw new InitializationException("No "
					+ FieldAliasingMapper.class.getName() + " available");
		}
		fieldAliasingMapper.addFieldAlias(alias, definedIn, fieldName);
	}

	public void aliasAttribute(String alias, String attributeName) {
		if (attributeAliasingMapper == null) {
			throw new InitializationException("No "
					+ AttributeAliasingMapper.class.getName() + " available");
		}
		attributeAliasingMapper.addAliasFor(attributeName, alias);
	}

	public void aliasAttribute(Class definedIn, String attributeName,
			String alias) {
		aliasField(alias, definedIn, attributeName);
		useAttributeFor(definedIn, attributeName);
	}

	public void useAttributeFor(String fieldName, Class type) {
		if (attributeMapper == null) {
			throw new InitializationException("No "
					+ AttributeMapper.class.getName() + " available");
		}
		attributeMapper.addAttributeFor(fieldName, type);
	}

	public void useGlobalImplicitCollection(boolean flag) {
		defaultMapper.useGlobalImplicitCollection(flag);
	}

	public void useAttributeFor(Class definedIn, String fieldName) {
		if (attributeMapper == null) {
			throw new InitializationException("No "
					+ AttributeMapper.class.getName() + " available");
		}
		attributeMapper.addAttributeFor(definedIn, fieldName);
	}

	public void useAttributeFor(Class type) {
		if (attributeMapper == null) {
			throw new InitializationException("No "
					+ AttributeMapper.class.getName() + " available");
		}
		attributeMapper.addAttributeFor(type);
	}

	public void addDefaultImplementation(Class defaultImplementation,
			Class ofType) {
		if (defaultImplementationsMapper == null) {
			throw new InitializationException("No "
					+ DefaultImplementationsMapper.class.getName()
					+ " available");
		}
		defaultImplementationsMapper.addDefaultImplementation(
				defaultImplementation, ofType);
	}

	public void setMapNodeElementSuffix(String suffix) {
		xpathMapper.setMapNodeElementSuffix(suffix);
	}

	@Override
	public Object get(Object key) {
		return dataHolder.get(key);
	}

	@Override
	public Iterator keys() {
		return dataHolder.keys();
	}

	@Override
	public void put(Object key, Object value) {
		dataHolder.put(key, value);
	}

	public Mapper getMapper() {
		return mapper;
	}

	public Class getRootType() {
		return (Class) get(ROOT_TYPE);
	}

	public void setRootType(Class type) {
		put(ROOT_TYPE, type);
	}
}
