package com.ztesoft.inf.extend.xstream.mapper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ClassAliasingMapper extends MapperWrapper {

	private final Map typeToName = new HashMap();
	private final Map classToName = new HashMap();
	private transient Map nameToType = new HashMap();

	public ClassAliasingMapper(Mapper wrapped) {
		super(wrapped);
	}

	public void addClassAlias(String name, Class type) {
		nameToType.put(name, type.getName());
		classToName.put(type.getName(), name);
	}

	public void addTypeAlias(String name, Class type) {
		nameToType.put(name, type.getName());
		typeToName.put(type, name);
	}

	@Override
	public String serializedClass(Class type) {
		String alias = (String) classToName.get(type.getName());
		if (alias != null) {
			return alias;
		} else {
			for (final Iterator iter = typeToName.keySet().iterator(); iter
					.hasNext();) {
				final Class compatibleType = (Class) iter.next();
				if (compatibleType.isAssignableFrom(type)) {
					return (String) typeToName.get(compatibleType);
				}
			}
			return super.serializedClass(type);
		}
	}

	@Override
	public Class realClass(String _elementName) {

		String elementName = _elementName;
		String mappedName = (String) nameToType.get(elementName);

		if (mappedName != null) {
			Class type = primitiveClassNamed(mappedName);
			if (type != null) {
				return type;
			}
			elementName = mappedName;
		}

		return super.realClass(elementName);
	}

	public boolean itemTypeAsAttribute(Class clazz) {
		return classToName.containsKey(clazz);
	}

	public boolean aliasIsAttribute(String name) {
		return nameToType.containsKey(name);
	}

	private Object readResolve() {
		nameToType = new HashMap();
		for (final Iterator iter = classToName.keySet().iterator(); iter
				.hasNext();) {
			final Object type = iter.next();
			nameToType.put(classToName.get(type), type);
		}
		for (final Iterator iter = typeToName.keySet().iterator(); iter
				.hasNext();) {
			final Class type = (Class) iter.next();
			nameToType.put(typeToName.get(type), type.getName());
		}
		return this;
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
}
