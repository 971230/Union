//package com.ztesoft.inf.service.util;
//
//import com.powerise.ibss.framework.FrameException;
//import com.powerise.ibss.util.DBUtil;
//import com.ztesoft.ibss.common.util.SqlMapExe;
//import org.apache.axis.AxisFault;
//import org.apache.axis.Constants;
//import org.apache.axis.NoEndPointException;
//import org.apache.axis.client.Call;
//import org.apache.axis.client.Service;
//import org.apache.axis.client.Stub;
//import org.apache.axis.description.OperationDesc;
//import org.apache.axis.encoding.DeserializerFactory;
//import org.apache.axis.encoding.SerializerFactory;
//import org.apache.axis.encoding.ser.ArraySerializerFactory;
//import org.apache.axis.message.SOAPHeaderElement;
//import org.apache.axis.soap.SOAPConstants;
//
//import javax.xml.namespace.QName;
//import javax.xml.soap.SOAPException;
//import java.rmi.RemoteException;
//import java.sql.Connection;
//import java.util.*;
//
///**
// * 实现webService客户端公共父类
// * 
// * @author hzcl-sky gong.zhiwei
// * 
// */
//public abstract class WebServicesClient extends Stub {
//	protected static final String SQL = "select b.ep_address from inf_comm_client_operation a, inf_comm_client_endpoint b where a.ep_id = b.ep_id and a.close_flag <> 'T' and a.op_code = ?";
//	protected Vector cachedSerClasses = new Vector();
//	protected Vector cachedSerQNames = new Vector();
//	protected Vector cachedSerFactories = new Vector();
//	protected Vector cachedDeserFactories = new Vector();
//	protected static String QName;
//	protected String SOAPActionURI = "";
//	protected static String OP_CODE="";
//
//	/**
//	 * 序列化数组类型
//	 * 
//	 * @param qName
//	 * @param qName2
//	 * @return
//	 */
//	protected ArraySerializerFactory arraySerializer(QName qName, QName qName2) {
//		return new ArraySerializerFactory(qName, qName2);
//	}
//
//	/**
//	 * 创建调用对象
//	 * 
//	 * @return
//	 * @throws RemoteException
//	 */
//	protected Call createCall() throws RemoteException {
//		try {
//			Call _call = super._createCall();
//			if (super.maintainSessionSet) {
//				_call.setMaintainSession(super.maintainSession);
//			}
//			if (super.cachedUsername != null) {
//				_call.setUsername(super.cachedUsername);
//			}
//			if (super.cachedPassword != null) {
//				_call.setPassword(super.cachedPassword);
//			}
//			if (super.cachedEndpoint != null) {
//				_call.setTargetEndpointAddress(super.cachedEndpoint);
//			}
//			if (super.cachedTimeout != null) {
//				_call.setTimeout(super.cachedTimeout);
//			}
//			if (super.cachedPortName != null) {
//				_call.setPortName(super.cachedPortName);
//			}
//			Enumeration keys = super.cachedProperties.keys();
//			while (keys.hasMoreElements()) {
//				String key = (String) keys.nextElement();
//				_call.setProperty(key, super.cachedProperties.get(key));
//			}
//			synchronized (this) {
//				if (firstCall()) {
//					_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
//					_call.setEncodingStyle(Constants.URI_SOAP11_ENC);
//					for (int i = 0; i < cachedSerFactories.size(); ++i) {
//						Class cls = (Class) cachedSerClasses.get(i);
//						QName qName = (QName) cachedSerQNames.get(i);
//						Object x = cachedSerFactories.get(i);
//						if (x instanceof Class) {
//							Class sf = (Class) cachedSerFactories.get(i);
//							Class df = (Class) cachedDeserFactories.get(i);
//							_call.registerTypeMapping(cls, qName, sf, df, false);
//						} else if (x instanceof SerializerFactory) {
//							SerializerFactory sf = (SerializerFactory) cachedSerFactories
//									.get(i);
//							DeserializerFactory df = (DeserializerFactory) cachedDeserFactories
//									.get(i);
//							_call.registerTypeMapping(cls, qName, sf, df, false);
//						}
//					}
//				}
//			}
//			return _call;
//		} catch (Throwable _t) {
//			throw new AxisFault("Failure trying to get the Call object", _t);
//		}
//	}
//
//	/**
//	 * 统一调用入口
//	 * 
//	 * @param operation
//	 *            处理对象
//	 * @param request
//	 *            请求参数
//	 * @param methodName
//	 *            调用方法名称
//	 * @return
//	 * @throws NoEndPointException
//	 * @throws RemoteException
//	 * @throws AxisFault
//	 */
//	protected Object invoke(OperationDesc operation,String methodName,Object[] request)
//			throws NoEndPointException, RemoteException, AxisFault {
//		SqlMapExe sqlExe = null;
//		Connection conn =null;
//		String endpoint ="";
//		try {
//			conn = DBUtil.getConnection();
//			sqlExe = new SqlMapExe(conn);
//			endpoint = sqlExe.querySingleValue(SQL, OP_CODE);
//		} catch (FrameException e) {
//			e.printStackTrace();
//			throw new RemoteException("无法获取服务地址：");
//		}finally{
//			DBUtil.closeConnection(conn);
//		}
//	
//		Service service1 = new Service();
//		Call call = new Call(service1);
//		call.setTargetEndpointAddress(endpoint);
//		call.setTimeout(new Integer(30 * 1000));
//		call.setOperation(operation);
//		call.setUseSOAPAction(true);
//		call.setSOAPActionURI(SOAPActionURI);
//		call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
//		QName operName = new QName(QName,methodName);
//
//		call.setOperationName(operName);
//		setRequestHeaders(call);
//		setAttachments(call);
//		try {
//			Object response = call.invoke(request);
//			if (response instanceof RemoteException) {
//				throw (RemoteException) response;
//			} else {
//				extractAttachments(call);
//				return response;
//			}
//		} catch (AxisFault axisFaultException) {
//			throw axisFaultException;
//		}
//	}
//
//	public SOAPHeaderElement getHeader(String nameSpance, String locaPart,
//			List list) throws SOAPException {
//		SOAPHeaderElement header = new SOAPHeaderElement(nameSpance, locaPart);
//		header.setNamespaceURI(nameSpance);
//		for (int i = 0; i < list.size(); i++) {
//			Map map = (Map) list.get(i);
//			Set set = map.keySet();
//			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
//				String key = (String) iterator.next();
//				String value = (String) map.get(key);
//				header.addChildElement(key).setValue(value);
//			}
//		}
//		return header;
//	}
//
//
//}
//
