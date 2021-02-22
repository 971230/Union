package com.ztesoft.inf.operation;

import com.ztesoft.inf.extend.xstream.XStream;
import com.ztesoft.inf.extend.xstream.mapper.MapperContext;
import com.ztesoft.inf.extend.xstream.mapper.MapperContextBuilder;
import com.ztesoft.inf.framework.cache.Cache;
import com.ztesoft.inf.framework.cache.CacheConstants;
import com.ztesoft.inf.framework.cache.CacheItemCreateCallback;
import com.ztesoft.inf.framework.cache.CacheManager;
import com.ztesoft.inf.operation.bo.OperationBO;
import com.ztesoft.inf.operation.vo.Operation;
import com.ztesoft.inf.operation.vo.OperationArg;
import org.dom4j.Document;
import org.dom4j.Element;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class OperationInvoker {

	private MapperContextBuilder mapperCtxBuilder = new MapperContextBuilder();
	private OperationBO operationBo = new OperationBO();
	private Cache operationCache = CacheManager
			.getCache(CacheConstants.OPERATION);
	private XStream xstream = XStream.instance();

	public Document invoke(String operationCode, Document inXml)
			throws Exception {
		OperationInternal operationIntenal = getOperationConfig(operationCode);
		return invoke(operationIntenal, inXml);
	}

	protected Document invoke(OperationInternal operationIntenal, Document doc)
			throws Exception {
		Object[] arguments = unmarshallArgument(operationIntenal, doc);
		Method method = operationIntenal.getMethod();
		Object result = null;
		Document resultXml = null;
		try {
			result = method.invoke(operationIntenal.getTarget(), arguments);
			if (result != null) {
				resultXml = xstream.toXMLDocument(result, operationIntenal
						.getResultMapperCtx());
			}
			return resultXml;
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (IllegalAccessException e) {
			throw e;
		} catch (InvocationTargetException e) {
			throw (Exception) e.getTargetException();
		}
	}

	protected Object[] unmarshallArgument(OperationInternal operationIntenal,
			Document doc) throws Exception {
		List argContexts = operationIntenal.getInArgMapperContext();
		List argXpaths = operationIntenal.getInArgXpaths();
		Object[] arguments = new Object[argContexts.size()];
		Object argument = null;
		MapperContext argContext;
		Element element = null;
		String xpath;
		for (int i = 0; i < argContexts.size(); i++) {
			argContext = (MapperContext) argContexts.get(i);
			xpath = (String) argXpaths.get(i);
			element = (Element) doc.selectSingleNode(xpath);
			argument = null;
			if (element != null)
				argument = xstream.fromElement(element, argContext);
			arguments[i] = argument;
		}
		return arguments;
	}

	private OperationInternal getOperationConfig(final String operationCode)
			throws Exception {
		OperationInternal operationIntenal = (OperationInternal) operationCache
				.get(operationCode, new CacheItemCreateCallback() {
					@Override
					public Object create() throws Exception {
						Operation operation = operationBo
								.getOperationByCode(operationCode);
						return resolveOperation(operation);
					}
				});
		return operationIntenal;
	}

	private OperationInternal resolveOperation(Operation operation) {
		OperationInternal operationIntenal = new OperationInternal();
		List args = operation.getOperationInArgs();
		OperationArg arg;
		MapperContext ctx;
		Class[] argTypes = new Class[args.size()];
		for (int i = 0; i < args.size(); i++) {
			arg = (OperationArg) args.get(i);
			ctx = mapperCtxBuilder.build(arg.getXmlMapper());
			operationIntenal.addArgMapperContext(ctx);
			argTypes[i] = ctx.getRootType();
			operationIntenal.addArgXpath(arg.getXpath());
		}
		arg = operation.getResultArg();
		ctx = mapperCtxBuilder.build(arg.getXmlMapper());
		operationIntenal.setResultMapperContext(ctx);
		String className = operation.getClassName();
		Class clazz;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			clazz = loader.loadClass(className);
			operationIntenal.setTarget(clazz.newInstance());
		} catch (Exception e) {
			throw new RuntimeException("", e);
		}
		try {
			Method method = clazz
					.getMethod(operation.getMethodName(), argTypes);
			operationIntenal.setMethod(method);
		} catch (Exception e) {
			throw new RuntimeException("", e);
		}
		return operationIntenal;
	}
}

