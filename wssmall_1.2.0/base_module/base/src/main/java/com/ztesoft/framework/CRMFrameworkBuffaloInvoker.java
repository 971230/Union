package com.ztesoft.framework;

import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import net.buffalo.protocal.BuffaloCall;
import net.buffalo.protocal.BuffaloProtocal;
import net.buffalo.protocal.Signature;
import net.buffalo.request.RequestContext;
import net.buffalo.service.ServiceInvocationException;
import net.buffalo.service.invoker.MatchedResult;
import net.buffalo.service.invoker.ServiceInvoker;

import org.apache.log4j.Logger;
import org.omg.CORBA.portable.UnknownException;

import com.ztesoft.buffalo.burlap.NFBurlapDatasetOutput;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.exception.CommonError;
import com.ztesoft.common.exception.CommonException;
import com.ztesoft.common.exception.CrmException;

public class CRMFrameworkBuffaloInvoker implements ServiceInvoker{

	private static final Logger _logger =Logger.getLogger(CRMFrameworkBuffaloInvoker.class);
	private Map methodCache;
	private static CRMFrameworkBuffaloInvoker instance;
	private CRMFrameworkBuffaloInvoker() {
		this.methodCache = new HashMap();
	}
	
	public static CRMFrameworkBuffaloInvoker getInstance() {
		if (instance == null) {
			instance = new CRMFrameworkBuffaloInvoker();
		}
		return instance;
	}
	
	/**
	 * Invoke the service. Note: this method is for appserver
	 * 
	 * @param service
	 * @param inputStream
	 * @param outputStream
	 */
	public void invoke(Object service, InputStream inputStream, Writer writer) throws Throwable {
		invoke0(service, writer, BuffaloProtocal.getInstance().unmarshall(
				inputStream));
	}

	@Override
	public void invoke(Object service, Reader reader, Writer writer) throws Throwable {
		invoke0(service, writer, BuffaloProtocal.getInstance().unmarshall(
				reader));
	}
	
	/**
	 * 结果输出到客户端
	 * 针对dataset 沿用buffalo1.2方式处理
	 * 针对vo 采用2.0协议处理
	 * 
	 * @param result
	 * @param writer
	 * @author easonwu 2009-12-10
	 * 
	 */
	private void resultOutput(Object result ,  Writer writer ){
		// extend dataset分支处理
		String replyType = RequestContext.getContext().
			getHttpRequest().getParameter("replyType");
		

		if ("dataset".equals(replyType)) {
			try {
				String fields = RequestContext.getContext().
					getHttpRequest().getParameter("fields");
				
				NFBurlapDatasetOutput dataSetOut = new NFBurlapDatasetOutput(
						RequestContext.getContext().getHttpResponse()
								.getOutputStream());
				dataSetOut.setFields(fields.split(","));
				dataSetOut.startReply(result);
				dataSetOut.writeObject(result);
				dataSetOut.completeReply();
				writer.flush() ;
				writer.close() ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			BuffaloProtocal.getInstance().marshall(result, writer);
		}
	}
	
	private void invoke0(Object service, Writer writer, BuffaloCall call) throws Throwable {
		Signature signature = new Signature(service.getClass(), call
				.getMethodName(), call.getArgumentTypes());

		Method method = null;
		if (methodCache.get(signature) != null) {
			method = (Method) methodCache.get(signature);
		} else {
			method = lookupMethod(service, call, signature);
		}

		if (method == null) {
			throw new ServiceInvocationException(("cannot find the method "
					+ call + " for " + service.getClass().getName()));
		}
		_logger.debug("-------->service:"+service.getClass().getName()+";method:"+method.getName());
		//logger.info("-------->service:"+service.getClass().getName()+";method:"+method.getName());
		Object result = null;
		try {
			result = method.invoke(service, call.getArguments());
		} catch (Throwable tx) {
			int loopCount = 0;
			while (tx instanceof InvocationTargetException
					&& (loopCount++) < 10) {
				tx = ((InvocationTargetException) tx).getTargetException();
			}

			tx.printStackTrace();
			Throwable cause = tx;
			CrmException crmEx = null;

			if (tx instanceof UnknownException) {
				tx = ((UnknownException) tx).originalEx;
				//logger.debug("((UnknownException)tx).originalEx"+tx);
			}

//			if (tx instanceof RemoteException) {
//				cause = ((RemoteException) tx).detail;
//				//logger.debug("((RemoteException)tx).detail "+cause);
//				if (cause instanceof EJBException) {
//					cause = ((EJBException) cause).getCausedByException();
//					//logger.debug("((EJBException)cause).getCausedByException() "+cause);
//				}
//			} else if (tx instanceof EJBException) {
//				cause = ((EJBException) tx).getCausedByException();
//				//logger.debug("tx instanceof EJBException "+cause);
//			}

			if (cause instanceof DAOSystemException) {
				crmEx = new CommonException(new CommonError(
						CommonError.DATABASE_ERROR), cause);
			} else if (cause instanceof CrmException) {
				crmEx = (CrmException) cause;
			} else {
				crmEx = new CommonException(new CommonError(
						CommonError.COMMON_ERROR), tx);
			}
			if (crmEx != null) {
				throw crmEx;
			} else {
				throw tx;
			}
		}
			//result = ClassUtil.invokeMethod(service, method, call.getArguments());
//		} catch (IllegalArgumentException e) {
//			throw new ServiceInvocationException(e);
//		} catch (IllegalAccessException e) {
//			throw new ServiceInvocationException(e);
//		} catch (InvocationTargetException e) {
//			InvocationTargetException y = e ;
//			throw new ServiceInvocationException(e);
////			result = e.getTargetException();
//		}
		
		//extend 
		resultOutput( result ,   writer ) ;
	}

	private Method lookupMethod(Object service, BuffaloCall call,
			Signature signature) {
		Method[] methods = service.getClass().getMethods();
		List matchedResults = new ArrayList();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method.getName().equals(call.getMethodName())) {
				Class[] parameterTypes = method.getParameterTypes();
				int matched = 0, weight = 0;
				if (parameterTypes.length == call.getArguments().length) {
					for (int j = 0; j < parameterTypes.length; j++) {
						int matchWeight = parameterAssignable(
								parameterTypes[j], call.getArguments()[j]
										.getClass());
						if (matchWeight > 0) {
							matched++;
							weight += matchWeight;
						}
					}
				}
				if (matched == parameterTypes.length) {
					methodCache.put(signature, method);
					matchedResults.add(new MatchedResult(weight, method));
				}
			}
		}

		if (matchedResults.size() == 0) {
			return null;
		} else if (matchedResults.size() == 1) {
			methodCache.put(signature, ((MatchedResult) matchedResults.get(0))
					.getMethod());
			return ((MatchedResult) matchedResults.get(0)).getMethod();
		} else {
			Collections.sort(matchedResults);
			methodCache.put(signature, ((MatchedResult) matchedResults.get(0))
					.getMethod());
			return ((MatchedResult) matchedResults.get(0)).getMethod();
		}

	}

	/*private int parameterAssignable(Class targetType, Class sourceType) {
		if (targetType.equals(sourceType)) return 6;

		if (targetType.isAssignableFrom(sourceType))
			return 5;

		if (targetType.isPrimitive()) {
			targetType = ClassUtil.getWrapperClass(targetType);
		}

		if (targetType.equals(sourceType)) {
			return 4;
		} else if (Number.class.isAssignableFrom(targetType)
				&& Number.class.isAssignableFrom(sourceType)) {
			return 3;
		}

		return 0;
	}*/
	private int parameterAssignable(Class targetType, Class sourceType) {
		if (targetType.equals(sourceType)) return 6;
		if (targetType.isPrimitive()) {
			if (getWrapperClass(targetType).equals(sourceType)) return 5;
			else if (Number.class.isAssignableFrom(getWrapperClass(targetType)) && 
					 Number.class.isAssignableFrom(sourceType)) {
				return 4;
			}
		}
		if (targetType.isAssignableFrom(sourceType)) return 3;
		
		return 0;
	}
	private Class getWrapperClass(Class primitiveClass) {
		Map map = new HashMap();
		map.put(int.class, Integer.class);
		map.put(double.class, Double.class);
		return (Class) map.get(primitiveClass);
	}
}
