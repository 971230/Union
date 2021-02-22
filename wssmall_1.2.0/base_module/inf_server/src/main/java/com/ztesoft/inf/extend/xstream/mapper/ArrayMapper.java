package com.ztesoft.inf.extend.xstream.mapper;

import java.util.Arrays;
import java.util.Collection;

public class ArrayMapper extends MapperWrapper {

	private final static Collection BOXED_TYPES = Arrays.asList(new Class[] {
			Boolean.class, Byte.class, Character.class, Short.class,
			Integer.class, Long.class, Float.class, Double.class });

	public ArrayMapper(Mapper wrapped) {
		super(wrapped);
	}

	@Override
	public String serializedClass(Class _type) {
		StringBuffer arraySuffix = new StringBuffer();
		String name = null;
		Class type = _type;
		while (type.isArray()) {
			name = super.serializedClass(type);
			if (type.getName().equals(name)) {
				type = type.getComponentType();
				arraySuffix.append("-array");
				name = null;
			} else {
				break;
			}
		}
		if (name == null) {
			name = boxedTypeName(type);
		}
		if (name == null) {
			name = super.serializedClass(type);
		}
		if (arraySuffix.length() > 0) {
			return name + arraySuffix;
		} else {
			return name;
		}
	}

	@Override
	public Class realClass(String _elementName) {
		String elementName = _elementName;
		int dimensions = 0;
		while (elementName.endsWith("-array")) {
			elementName = elementName.substring(0, elementName.length() - 6);
			++dimensions;
		}
		if (dimensions > 0) {
			Class componentType = primitiveClassNamed(elementName);
			if (componentType == null) {
				componentType = super.realClass(elementName);
			}
			while (componentType.isArray()) {
				componentType = componentType.getComponentType();
				++dimensions;
			}
			return super.realClass(arrayType(dimensions, componentType));
		} else {
			return super.realClass(elementName);
		}
	}

	private String arrayType(int dimensions, Class componentType) {
		StringBuffer className = new StringBuffer();
		for (int i = 0; i < dimensions; i++) {
			className.append('[');
		}
		if (componentType.isPrimitive()) {
			className
					.append(charThatJavaUsesToRepresentPrimitiveArrayType(componentType));
			return className.toString();
		} else {
			className.append('L').append(componentType.getName()).append(';');
			return className.toString();
		}
	}

	private Class primitiveClassNamed(String name) {
		return name.equals("void") ? Void.TYPE
				: name.equals("boolean") ? Boolean.TYPE
						: name.equals("byte") ? Byte.TYPE
								: name.equals("char") ? Character.TYPE : name
										.equals("short") ? Short.TYPE : name
										.equals("int") ? Integer.TYPE : name
										.equals("long") ? Long.TYPE : name
										.equals("float") ? Float.TYPE : name
										.equals("double") ? Double.TYPE : null;
	}

	private char charThatJavaUsesToRepresentPrimitiveArrayType(Class primvCls) {
		return (primvCls == boolean.class) ? 'Z'
				: (primvCls == byte.class) ? 'B'
						: (primvCls == char.class) ? 'C'
								: (primvCls == short.class) ? 'S'
										: (primvCls == int.class) ? 'I'
												: (primvCls == long.class) ? 'J'
														: (primvCls == float.class) ? 'F'
																: (primvCls == double.class) ? 'D'
																		: 0;
	}

	private String boxedTypeName(Class type) {
		return BOXED_TYPES.contains(type) ? type.getName() : null;
	}
}
