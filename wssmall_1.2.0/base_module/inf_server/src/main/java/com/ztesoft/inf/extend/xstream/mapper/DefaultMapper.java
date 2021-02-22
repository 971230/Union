/*
 * Copyright (C) 2005, 2006 Joe Walnes.
 * Copyright (C) 2006, 2007, 2008 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 22. January 2005 by Joe Walnes
 */
package com.ztesoft.inf.extend.xstream.mapper;

import com.ztesoft.inf.extend.xstream.converters.Converter;
import com.ztesoft.inf.extend.xstream.converters.SingleValueConverter;

/**
 * Default mapper implementation with 'vanilla' functionality. To build up the
 * functionality required, wrap this mapper with other mapper implementations.
 * 
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 */
public class DefaultMapper implements Mapper {

	private ClassLoader classLoader;
	private boolean useSimpleName = true;
	private boolean useGlobalImplicitCollection = false;
	/**
	 * @deprecated since 1.2, no necessity for field anymore.
	 */
	@Deprecated
	private transient String classAttributeIdentifier;

	public DefaultMapper() {
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public DefaultMapper(ClassLoader classLoader) {
		this.classLoader = classLoader;
		this.classAttributeIdentifier = "class";
	}

	/**
	 * @deprecated since 1.2, use XStream.aliasAttrbute() for a different
	 *             attribute name.
	 */
	@Deprecated
	public DefaultMapper(ClassLoader classLoader,
			String classAttributeIdentifier) {
		this(classLoader);
		this.classAttributeIdentifier = classAttributeIdentifier == null ? "class"
				: classAttributeIdentifier;
	}

	/**
	 * @deprecated since 1.2, no necessity for method anymore.
	 */
	@Deprecated
	private Object readResolve() {
		classAttributeIdentifier = "class";
		return this;
	}

	@Override
	public String serializedClass(Class type) {
		if (useSimpleName)
			return type.getSimpleName();
		return type.getName();
	}

	@Override
	public Class realClass(String elementName) {
		try {
			if (elementName.charAt(0) != '[') {
				return classLoader.loadClass(elementName);
			} else if (elementName.endsWith(";")) {
				return Class
						.forName(elementName.toString(), false, classLoader);
			} else {
				return Class.forName(elementName.toString());
			}
		} catch (ClassNotFoundException e) {
			throw new CannotResolveClassException(elementName + " : "
					+ e.getMessage());
		}
	}

	@Override
	public Class defaultImplementationOf(Class type) {
		return type;
	}

	/**
	 * @deprecated since 1.2, use aliasForAttribute instead.
	 */
	@Override
	@Deprecated
	public String attributeForClassDefiningField() {
		return "defined-in";
	}

	/**
	 * @deprecated since 1.2, use aliasForAttribute instead.
	 */
	@Override
	@Deprecated
	public String attributeForReadResolveField() {
		return "resolves-to";
	}

	/**
	 * @deprecated since 1.2, use aliasForAttribute instead.
	 */
	@Override
	@Deprecated
	public String attributeForEnumType() {
		return "enum-type";
	}

	/**
	 * @deprecated since 1.2, use aliasForAttribute instead.
	 */
	@Override
	@Deprecated
	public String attributeForImplementationClass() {
		return classAttributeIdentifier;
	}

	@Override
	public String aliasForAttribute(String attribute) {
		return attribute;
	}

	@Override
	public String attributeForAlias(String alias) {
		return alias;
	}

	@Override
	public String aliasForSystemAttribute(String attribute) {
		return attribute;
	}

	@Override
	public boolean isImmutableValueType(Class type) {
		return false;
	}

	@Override
	public String getFieldNameForItemTypeAndName(Class definedIn,
			Class itemType, String itemFieldName) {
		return null;
	}

	@Override
	public Class getItemTypeForItemFieldName(Class definedIn,
			String itemFieldName) {
		return null;
	}

	@Override
	public ImplicitCollectionMapping getImplicitCollectionDefForFieldName(
			Class itemType, String fieldName) {
		return null;
	}

	@Override
	public boolean shouldSerializeMember(Class definedIn, String fieldName) {
		return true;
	}

	public String lookupName(Class type) {
		return serializedClass(type);
	}

	public Class lookupType(String elementName) {
		return realClass(elementName);
	}

	@Override
	public String serializedMember(Class type, String memberName) {
		return memberName;
	}

	@Override
	public String realMember(Class type, String serialized) {
		return serialized;
	}

	/**
	 * @deprecated since 1.3, use
	 *             {@link #getConverterFromAttribute(Class, String, Class)}
	 */
	@Override
	@Deprecated
	public SingleValueConverter getConverterFromAttribute(String name) {
		return null;
	}

	/**
	 * @deprecated since 1.3, use
	 *             {@link #getConverterFromItemType(String, Class, Class)}
	 */
	@Override
	@Deprecated
	public SingleValueConverter getConverterFromItemType(String fieldName,
			Class type) {
		return null;
	}

	/**
	 * @deprecated since 1.3, use
	 *             {@link #getConverterFromItemType(String, Class, Class)}
	 */
	@Override
	@Deprecated
	public SingleValueConverter getConverterFromItemType(Class type) {
		return null;
	}

	@Override
	public SingleValueConverter getConverterFromItemType(String fieldName,
			Class type, Class definedIn) {
		return null;
	}

	@Override
	public Converter getLocalConverter(Class definedIn, String fieldName) {
		return null;
	}

	@Override
	public Mapper lookupMapperOfType(Class type) {
		return null;
	}

	/**
	 * @deprecated since 1.3, use combination of
	 *             {@link #serializedMember(Class, String)} and
	 *             {@link #getConverterFromItemType(String, Class, Class)}
	 */
	@Override
	@Deprecated
	public String aliasForAttribute(Class definedIn, String fieldName) {
		return fieldName;
	}

	/**
	 * @deprecated since 1.3, use combination of
	 *             {@link #realMember(Class, String)} and
	 *             {@link #getConverterFromItemType(String, Class, Class)}
	 */
	@Override
	@Deprecated
	public String attributeForAlias(Class definedIn, String alias) {
		return alias;
	}

	/**
	 * @deprecated since 1.3.1, use
	 *             {@link #getConverterFromAttribute(Class, String, Class)}
	 */
	@Override
	@Deprecated
	public SingleValueConverter getConverterFromAttribute(Class definedIn,
			String attribute) {
		return null;
	}

	@Override
	public SingleValueConverter getConverterFromAttribute(Class definedIn,
			String attribute, Class type) {
		return null;
	}

	@Override
	public Class realClassByPath(String path) {
		return null;
	}

	@Override
	public String getColFieldNameByPath(String path) {
		return null;
	}

	@Override
	public String getAliasNameByPath(String path) {
		return null;
	}

	@Override
	public String getImplicitCollectionItemNameByPath(String path) {
		return null;
	}

	@Override
	public String genMapNodeNameByPath(String path) {
		return null;
	}

	@Override
	public boolean globalImplicitCollection() {
		return useGlobalImplicitCollection;
	}

	public void useGlobalImplicitCollection(boolean flag) {
		this.useGlobalImplicitCollection = flag;
	}
}
