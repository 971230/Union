package com.ztesoft.inf.extend.xstream;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.ztesoft.inf.extend.xstream.converters.Converter;
import com.ztesoft.inf.extend.xstream.converters.ConverterLookup;
import com.ztesoft.inf.extend.xstream.converters.ConverterRegistry;
import com.ztesoft.inf.extend.xstream.converters.SingleValueConverter;
import com.ztesoft.inf.extend.xstream.converters.SingleValueConverterWrapper;
import com.ztesoft.inf.extend.xstream.converters.basic.BigDecimalConverter;
import com.ztesoft.inf.extend.xstream.converters.basic.BigIntegerConverter;
import com.ztesoft.inf.extend.xstream.converters.basic.BooleanConverter;
import com.ztesoft.inf.extend.xstream.converters.basic.ByteConverter;
import com.ztesoft.inf.extend.xstream.converters.basic.CharConverter;
import com.ztesoft.inf.extend.xstream.converters.basic.DateConverter;
import com.ztesoft.inf.extend.xstream.converters.basic.DoubleConverter;
import com.ztesoft.inf.extend.xstream.converters.basic.FloatConverter;
import com.ztesoft.inf.extend.xstream.converters.basic.IntConverter;
import com.ztesoft.inf.extend.xstream.converters.basic.LongConverter;
import com.ztesoft.inf.extend.xstream.converters.basic.NullConverter;
import com.ztesoft.inf.extend.xstream.converters.basic.ShortConverter;
import com.ztesoft.inf.extend.xstream.converters.basic.StringBufferConverter;
import com.ztesoft.inf.extend.xstream.converters.basic.StringConverter;
import com.ztesoft.inf.extend.xstream.converters.collections.ArrayConverter;
import com.ztesoft.inf.extend.xstream.converters.collections.CharArrayConverter;
import com.ztesoft.inf.extend.xstream.converters.collections.CollectionConverter;
import com.ztesoft.inf.extend.xstream.converters.collections.MapConverter;
import com.ztesoft.inf.extend.xstream.converters.extended.JavaClassConverter;
import com.ztesoft.inf.extend.xstream.converters.extended.JavaMethodConverter;
import com.ztesoft.inf.extend.xstream.converters.extended.LocaleConverter;
import com.ztesoft.inf.extend.xstream.converters.extended.SqlDateConverter;
import com.ztesoft.inf.extend.xstream.converters.extended.SqlTimeConverter;
import com.ztesoft.inf.extend.xstream.converters.extended.SqlTimestampConverter;
import com.ztesoft.inf.extend.xstream.converters.reflection.ReflectionConverter;
import com.ztesoft.inf.extend.xstream.converters.reflection.ReflectionProvider;
import com.ztesoft.inf.extend.xstream.core.DefaultConverterLookup;
import com.ztesoft.inf.extend.xstream.core.JVM;
import com.ztesoft.inf.extend.xstream.core.TreeMarshallingStrategy;
import com.ztesoft.inf.extend.xstream.core.util.ClassLoaderReference;
import com.ztesoft.inf.extend.xstream.core.util.CompositeClassLoader;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamDriver;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
import com.ztesoft.inf.extend.xstream.io.xml.DocumentWriter;
import com.ztesoft.inf.extend.xstream.io.xml.Dom4JDriver;
import com.ztesoft.inf.extend.xstream.mapper.Mapper;
import com.ztesoft.inf.extend.xstream.mapper.MapperContext;

public class XStream {

	private static XStream instance;
	private ReflectionProvider reflectionProvider;
	private HierarchicalStreamDriver hierarchicalStreamDriver;
	private ClassLoaderReference classLoaderReference;
	private MarshallingStrategy marshallingStrategy;
	private ConverterLookup converterLookup;
	private ConverterRegistry converterRegistry;
	private transient JVM jvm = new JVM();
	public static final int PRIORITY_VERY_HIGH = 10000;
	public static final int PRIORITY_NORMAL = 0;
	public static final int PRIORITY_LOW = -10;
	public static final int PRIORITY_VERY_LOW = -20;

	public static synchronized XStream instance() {
		if (instance == null) {
			instance = new XStream();
		}
		return instance;
	}

	public XStream() {
		this(null, (Mapper) null, new Dom4JDriver());
	}

	public XStream(ReflectionProvider reflectionProvider) {
		this(reflectionProvider, (Mapper) null, new Dom4JDriver());
	}

	public XStream(HierarchicalStreamDriver hierarchicalStreamDriver) {
		this(null, (Mapper) null, hierarchicalStreamDriver);
	}

	public XStream(ReflectionProvider reflectionProvider,
			HierarchicalStreamDriver hierarchicalStreamDriver) {
		this(reflectionProvider, (Mapper) null, hierarchicalStreamDriver);
	}

	public XStream(ReflectionProvider reflectionProvider, Mapper mapper,
			HierarchicalStreamDriver hierarchicalStreamDriver) {
		this(reflectionProvider, hierarchicalStreamDriver,
				new ClassLoaderReference(new CompositeClassLoader()), mapper);
	}

	public XStream(ReflectionProvider reflectionProvider,
			HierarchicalStreamDriver driver, ClassLoader classLoader) {
		this(reflectionProvider, driver, classLoader, null);
	}

	public XStream(ReflectionProvider reflectionProvider,
			HierarchicalStreamDriver driver, ClassLoader classLoader,
			Mapper mapper) {
		this(reflectionProvider, driver, classLoader, mapper,
				new DefaultConverterLookup(), null);
	}

	public XStream(ReflectionProvider reflectionProvider,
			HierarchicalStreamDriver driver, ClassLoader classLoader,
			Mapper mapper, ConverterLookup converterLookup,
			ConverterRegistry converterRegistry) {
		jvm = new JVM();
		if (reflectionProvider == null) {
			this.reflectionProvider = jvm.bestReflectionProvider();
		} else
			this.reflectionProvider = reflectionProvider;
		this.hierarchicalStreamDriver = driver;
		this.classLoaderReference = classLoader instanceof ClassLoaderReference ? (ClassLoaderReference) classLoader
				: new ClassLoaderReference(classLoader);
		this.converterLookup = converterLookup;
		this.converterRegistry = converterRegistry != null ? converterRegistry
				: (converterLookup instanceof ConverterRegistry ? (ConverterRegistry) converterLookup
						: null);
		setupConverters();
		setMarshallingStrategy(new TreeMarshallingStrategy());
	}

	protected boolean useXStream11XmlFriendlyMapper() {
		return false;
	}

	protected void setupConverters() {
		final ReflectionConverter reflectionConverter = new ReflectionConverter(
				reflectionProvider);
		registerConverter(reflectionConverter, PRIORITY_VERY_LOW);
		registerConverter(new NullConverter(), PRIORITY_VERY_HIGH);
		registerConverter(new IntConverter(), PRIORITY_NORMAL);
		registerConverter(new FloatConverter(), PRIORITY_NORMAL);
		registerConverter(new DoubleConverter(), PRIORITY_NORMAL);
		registerConverter(new LongConverter(), PRIORITY_NORMAL);
		registerConverter(new ShortConverter(), PRIORITY_NORMAL);
		registerConverter((Converter) new CharConverter(), PRIORITY_NORMAL);
		registerConverter(new BooleanConverter(), PRIORITY_NORMAL);
		registerConverter(new ByteConverter(), PRIORITY_NORMAL);
		registerConverter(new StringConverter(), PRIORITY_NORMAL);
		registerConverter(new StringBufferConverter(), PRIORITY_NORMAL);
		registerConverter(new DateConverter(), PRIORITY_NORMAL);
		registerConverter(new BigIntegerConverter(), PRIORITY_NORMAL);
		registerConverter(new BigDecimalConverter(), PRIORITY_NORMAL);
		registerConverter(new ArrayConverter(), PRIORITY_NORMAL);
		registerConverter(new CharArrayConverter(), PRIORITY_NORMAL);
		registerConverter(new CollectionConverter(), PRIORITY_NORMAL);
		registerConverter(new MapConverter(), PRIORITY_NORMAL);
		if (jvm.supportsSQL()) {
			registerConverter(new SqlTimestampConverter(), PRIORITY_NORMAL);
			registerConverter(new SqlTimeConverter(), PRIORITY_NORMAL);
			registerConverter(new SqlDateConverter(), PRIORITY_NORMAL);
		}
		registerConverter(new JavaClassConverter(classLoaderReference),
				PRIORITY_NORMAL);
		registerConverter(new JavaMethodConverter(classLoaderReference),
				PRIORITY_NORMAL);
		registerConverter(new LocaleConverter(), PRIORITY_NORMAL);
	}

	public void setMarshallingStrategy(MarshallingStrategy marshallingStrategy) {
		this.marshallingStrategy = marshallingStrategy;
	}

	public String toXML(Object obj, MapperContext mapperCtx) {
		if (mapperCtx == null)
			mapperCtx = new MapperContext();
		Writer writer = new StringWriter();
		toXML(obj, writer, mapperCtx);
		return writer.toString();
	}

	public Document toXMLDocument(Object obj, MapperContext mapperCtx) {
		DocumentWriter writer = hierarchicalStreamDriver.createDoumentWriter();
		try {
			marshal(obj, writer, mapperCtx);
			List tops = writer.getTopLevelNodes();
			return (Document) tops.get(0);
		} finally {
			writer.flush();
		}
	}

	public void toXML(Object obj, Writer out, MapperContext mapperCtx) {
		HierarchicalStreamWriter writer = hierarchicalStreamDriver
				.createWriter(out);
		try {
			marshal(obj, writer, mapperCtx);
		} finally {
			writer.flush();
		}
	}

	public void toXML(Object obj, OutputStream out) {
		HierarchicalStreamWriter writer = hierarchicalStreamDriver
				.createWriter(out);
		try {
			marshal(obj, writer);
		} finally {
			writer.flush();
		}
	}

	public void marshal(Object obj, HierarchicalStreamWriter writer) {
		marshal(obj, writer, new MapperContext());
	}

	public void marshal(Object obj, HierarchicalStreamWriter writer,
			MapperContext mapCtx) {
		mapCtx.setClassLoader(classLoaderReference);
		mapCtx.setConverterLookup(converterLookup);
		marshallingStrategy.marshal(writer, obj, converterLookup, mapCtx);
	}

	public Object fromXML(String xml, MapperContext mapperCtx) {
		return fromXML(new StringReader(xml), mapperCtx);
	}

	public Object fromXML(Reader xml, MapperContext mapperCtx) {
		return unmarshal(hierarchicalStreamDriver.createReader(xml), null,
				mapperCtx);
	}

	public Object fromXML(InputStream input, MapperContext mapperCtx) {
		return unmarshal(hierarchicalStreamDriver.createReader(input), null,
				mapperCtx);
	}

	public Object fromXML(String xml, Object root, MapperContext mapperCtx) {
		return fromXML(new StringReader(xml), root, mapperCtx);
	}

	public Object fromXML(Reader xml, Object root, MapperContext mapperCtx) {
		return unmarshal(hierarchicalStreamDriver.createReader(xml), root,
				mapperCtx);
	}

	public Object fromXML(InputStream xml, Object root) {
		return unmarshal(hierarchicalStreamDriver.createReader(xml), root);
	}

	public Object fromElement(Element element, MapperContext mapperCtx) {
		return unmarshal(hierarchicalStreamDriver.createReader(element), null,
				mapperCtx);
	}

	public Object unmarshal(HierarchicalStreamReader reader) {
		return unmarshal(reader, null, null);
	}

	public Object unmarshal(HierarchicalStreamReader reader, Object root) {
		return unmarshal(reader, root, new MapperContext());
	}

	public Object unmarshal(HierarchicalStreamReader reader, Object root,
			MapperContext mapperCtx) {
		mapperCtx.setClassLoader(classLoaderReference);
		mapperCtx.setConverterLookup(converterLookup);
		return marshallingStrategy.unmarshal(root, reader, converterLookup,
				mapperCtx);
	}

	public void registerConverter(Converter converter) {
		registerConverter(converter, PRIORITY_NORMAL);
	}

	public void registerConverter(Converter converter, int priority) {
		if (converterRegistry != null) {
			converterRegistry.registerConverter(converter, priority);
		}
	}

	public void registerConverter(SingleValueConverter converter) {
		registerConverter(converter, PRIORITY_NORMAL);
	}

	public void registerConverter(SingleValueConverter converter, int priority) {
		if (converterRegistry != null) {
			converterRegistry.registerConverter(
					new SingleValueConverterWrapper(converter), priority);
		}
	}

	public ReflectionProvider getReflectionProvider() {
		return reflectionProvider;
	}

	public ConverterLookup getConverterLookup() {
		return converterLookup;
	}

	public void setClassLoader(ClassLoader classLoader) {
		classLoaderReference.setReference(classLoader);
	}

	public ClassLoader getClassLoader() {
		return classLoaderReference.getReference();
	}

	private Object readResolve() {
		jvm = new JVM();
		return this;
	}
}
