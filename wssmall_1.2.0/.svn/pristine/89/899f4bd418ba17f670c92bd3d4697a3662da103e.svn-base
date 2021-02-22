package com.ztesoft.inf.extend.xstream.core;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import com.ztesoft.inf.extend.xstream.converters.reflection.PureJavaReflectionProvider;
import com.ztesoft.inf.extend.xstream.converters.reflection.ReflectionProvider;

public class JVM {

	private ReflectionProvider reflectionProvider;
	private transient Map loaderCache = new HashMap();

	private final boolean supportsAWT = loadClass("java.awt.Color") != null;
	private final boolean supportsSwing = loadClass("javax.swing.LookAndFeel") != null;
	private final boolean supportsSQL = loadClass("java.sql.Date") != null;

	private static final String vendor = System.getProperty("java.vm.vendor");
	private static final float majorJavaVersion = getMajorJavaVersion();
	private static final boolean reverseFieldOrder = isHarmony()
			|| (isIBM() && !is15());

	static final float DEFAULT_JAVA_VERSION = 1.3f;

	private static final float getMajorJavaVersion() {
		try {
			return Float.parseFloat(System
					.getProperty("java.specification.version"));
		} catch (NumberFormatException e) {
			return DEFAULT_JAVA_VERSION;
		}
	}

	public static boolean is14() {
		return majorJavaVersion >= 1.4f;
	}

	public static boolean is15() {
		return majorJavaVersion >= 1.5f;
	}

	public static boolean is16() {
		return majorJavaVersion >= 1.6f;
	}

	private static boolean isIBM() {
		return vendor.indexOf("IBM") != -1;
	}

	private static boolean isHarmony() {
		return vendor.indexOf("Apache Software Foundation") != -1;
	}

	public Class loadClass(String name) {
		try {
			WeakReference reference = (WeakReference) loaderCache.get(name);
			if (reference != null) {
				Class cached = (Class) reference.get();
				if (cached != null) {
					return cached;
				}
			}

			Class clazz = Class.forName(name, false, getClass()
					.getClassLoader());
			loaderCache.put(name, new WeakReference(clazz));
			return clazz;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public synchronized ReflectionProvider bestReflectionProvider() {
		if (reflectionProvider == null) {
			reflectionProvider = new PureJavaReflectionProvider();
		}
		return reflectionProvider;
	}

	public static boolean reverseFieldDefinition() {
		return reverseFieldOrder;
	}

	/**
	 * Checks if the jvm supports awt.
	 */
	public boolean supportsAWT() {
		return this.supportsAWT;
	}

	/**
	 * Checks if the jvm supports swing.
	 */
	public boolean supportsSwing() {
		return this.supportsSwing;
	}

	/**
	 * Checks if the jvm supports sql.
	 */
	public boolean supportsSQL() {
		return this.supportsSQL;
	}

	private Object readResolve() {
		loaderCache = new HashMap();
		return this;
	}
}
