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

public abstract class MapperWrapper implements Mapper {

	private final Mapper wrapped;

	public MapperWrapper(Mapper wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public String serializedClass(Class type) {
		return wrapped.serializedClass(type);
	}

	@Override
	public Class realClass(String elementName) {
		return wrapped.realClass(elementName);
	}

	@Override
	public String serializedMember(Class type, String memberName) {
		return wrapped.serializedMember(type, memberName);
	}

	@Override
	public String realMember(Class type, String serialized) {
		return wrapped.realMember(type, serialized);
	}

	@Override
	public boolean isImmutableValueType(Class type) {
		return wrapped.isImmutableValueType(type);
	}

	@Override
	public Class defaultImplementationOf(Class type) {
		return wrapped.defaultImplementationOf(type);
	}

	/**
	 * @deprecated since 1.2, use aliasForAttribute instead.
	 */
	@Override
	@Deprecated
	public String attributeForClassDefiningField() {
		return wrapped.attributeForClassDefiningField();
	}

	/**
	 * @deprecated since 1.2, use aliasForAttribute instead.
	 */
	@Override
	@Deprecated
	public String attributeForImplementationClass() {
		return wrapped.attributeForImplementationClass();
	}

	/**
	 * @deprecated since 1.2, use aliasForAttribute instead.
	 */
	@Override
	@Deprecated
	public String attributeForReadResolveField() {
		return wrapped.attributeForReadResolveField();
	}

	/**
	 * @deprecated since 1.2, use aliasForAttribute instead.
	 */
	@Override
	@Deprecated
	public String attributeForEnumType() {
		return wrapped.attributeForEnumType();
	}

	@Override
	public String aliasForAttribute(String attribute) {
		return wrapped.aliasForAttribute(attribute);
	}

	@Override
	public String attributeForAlias(String alias) {
		return wrapped.attributeForAlias(alias);
	}

	@Override
	public String aliasForSystemAttribute(String attribute) {
		return wrapped.aliasForSystemAttribute(attribute);
	}

	@Override
	public String getFieldNameForItemTypeAndName(Class definedIn,
			Class itemType, String itemFieldName) {
		return wrapped.getFieldNameForItemTypeAndName(definedIn, itemType,
				itemFieldName);
	}

	@Override
	public Class getItemTypeForItemFieldName(Class definedIn,
			String itemFieldName) {
		return wrapped.getItemTypeForItemFieldName(definedIn, itemFieldName);
	}

	@Override
	public ImplicitCollectionMapping getImplicitCollectionDefForFieldName(
			Class itemType, String fieldName) {
		return wrapped
				.getImplicitCollectionDefForFieldName(itemType, fieldName);
	}

	@Override
	public boolean shouldSerializeMember(Class definedIn, String fieldName) {
		return wrapped.shouldSerializeMember(definedIn, fieldName);
	}

	/**
	 * @deprecated since 1.3, use
	 *             {@link #getConverterFromItemType(String, Class, Class)}
	 */
	@Override
	@Deprecated
	public SingleValueConverter getConverterFromItemType(String fieldName,
			Class type) {
		return wrapped.getConverterFromItemType(fieldName, type);
	}

	/**
	 * @deprecated since 1.3, use
	 *             {@link #getConverterFromItemType(String, Class, Class)}
	 */
	@Override
	@Deprecated
	public SingleValueConverter getConverterFromItemType(Class type) {
		return wrapped.getConverterFromItemType(type);
	}

	/**
	 * @deprecated since 1.3, use
	 *             {@link #getConverterFromAttribute(Class, String, Class)}
	 */
	@Override
	@Deprecated
	public SingleValueConverter getConverterFromAttribute(String name) {
		return wrapped.getConverterFromAttribute(name);
	}

	@Override
	public Converter getLocalConverter(Class definedIn, String fieldName) {
		return wrapped.getLocalConverter(definedIn, fieldName);
	}

	@Override
	public Mapper lookupMapperOfType(Class type) {
		return type.isAssignableFrom(getClass()) ? this : wrapped
				.lookupMapperOfType(type);
	}

	@Override
	public SingleValueConverter getConverterFromItemType(String fieldName,
			Class type, Class definedIn) {
		return wrapped.getConverterFromItemType(fieldName, type, definedIn);
	}

	/**
	 * @deprecated since 1.3, use combination of
	 *             {@link #serializedMember(Class, String)} and
	 *             {@link #getConverterFromItemType(String, Class, Class)}
	 */
	@Override
	@Deprecated
	public String aliasForAttribute(Class definedIn, String fieldName) {
		return wrapped.aliasForAttribute(definedIn, fieldName);
	}

	/**
	 * @deprecated since 1.3, use combination of
	 *             {@link #realMember(Class, String)} and
	 *             {@link #getConverterFromItemType(String, Class, Class)}
	 */
	@Override
	@Deprecated
	public String attributeForAlias(Class definedIn, String alias) {
		return wrapped.attributeForAlias(definedIn, alias);
	}

	/**
	 * @deprecated since 1.3.1, use
	 *             {@link #getConverterFromAttribute(Class, String, Class)}
	 */
	@Override
	@Deprecated
	public SingleValueConverter getConverterFromAttribute(Class type,
			String attribute) {
		return wrapped.getConverterFromAttribute(type, attribute);
	}

	@Override
	public SingleValueConverter getConverterFromAttribute(Class definedIn,
			String attribute, Class type) {
		return wrapped.getConverterFromAttribute(definedIn, attribute, type);
	}

	@Override
	public Class realClassByPath(String path) {
		return wrapped.realClassByPath(path);
	}

	@Override
	public String getColFieldNameByPath(String path) {
		return wrapped.getColFieldNameByPath(path);
	}

	@Override
	public String getAliasNameByPath(String path) {
		return wrapped.getAliasNameByPath(path);
	}

	@Override
	public String getImplicitCollectionItemNameByPath(String path) {
		return wrapped.getImplicitCollectionItemNameByPath(path);
	}

	@Override
	public String genMapNodeNameByPath(String path) {
		return wrapped.genMapNodeNameByPath(path);
	}

	@Override
	public boolean globalImplicitCollection() {
		return wrapped.globalImplicitCollection();
	}
}
