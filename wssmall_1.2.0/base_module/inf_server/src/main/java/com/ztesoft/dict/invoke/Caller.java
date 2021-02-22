package com.ztesoft.dict.invoke;

import java.io.InputStream;
import java.lang.reflect.Type;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import com.google.gson.reflect.TypeToken;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.Base64;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.dict.config.ServerConfig;

/**
 * 
 * httpClient工具类，封装JSON交互协议
 * 
 * @author Thunder.xu
 */
public class Caller {
	private static DefaultHttpClient httpclient = null;

	public final static class RESULT {
		public static final String SUCCESS_CODE = "0";
		public static final String FAIL_CODE = "-1";
	}

	public final static class ACCEPT {
		public static final String JSON = "application/json";
		public static final String TEXT = "text/plain";
	}

	/**
	 * Singleton HttpClient
	 * 
	 * @param timeout
	 *            超时时间 ，单位为�?
	 * @return
	 */
	private synchronized static DefaultHttpClient getHttpClient() {
		if (null != httpclient) {
			return httpclient;
		}

		int timeoutConnection = 23 * 1000;
		int timeoutSocket = 28 * 1000;

		int BUFFER_SIZE = 8192 * 20;

		HttpParams httpParameters = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		HttpConnectionParams.setSocketBufferSize(httpParameters, BUFFER_SIZE);

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));
		SingleClientConnManager mgr = new SingleClientConnManager(
				httpParameters, schemeRegistry);

		HttpClientParams.setRedirecting(httpParameters, true);
		HttpClientParams.setCookiePolicy(httpParameters,
				CookiePolicy.BROWSER_COMPATIBILITY);
		HttpProtocolParams.setUserAgent(httpParameters, "apache.http.client");
		httpclient = new DefaultHttpClient(mgr, httpParameters);
		return httpclient;
	}

	private synchronized static DefaultHttpClient getHttpClient(int timeout) {
		int timeoutConnection = timeout == -1 ? 23 * 1000 : timeout * 1000;
		int timeoutSocket = timeout == -1 ? 28 * 1000 : timeout * 1000;
		int BUFFER_SIZE = 8192 * 20;
		DefaultHttpClient client = null;

		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		HttpConnectionParams.setSocketBufferSize(httpParameters, BUFFER_SIZE);

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));
		SingleClientConnManager mgr = new SingleClientConnManager(
				httpParameters, schemeRegistry);

		HttpClientParams.setRedirecting(httpParameters, true);
		HttpClientParams.setCookiePolicy(httpParameters,
				CookiePolicy.BROWSER_COMPATIBILITY);
		HttpProtocolParams.setUserAgent(httpParameters, "apache.http.client");
		client = new DefaultHttpClient(mgr, httpParameters);
		return client;
	}

	/**
	 * 后台请求统一调用接口 add by xiaof 111221
	 */
	public static <T> T invoke(DynamicDict dto, TypeToken<T> rstClass,
			int timeoutSeconds) throws FrameException {

		// 1.校验参数合法
		if (dto == null) {
			throw new FrameException(
					"Mothod:Caller.invoke param dto can not input null!");
		}

		return invokeJsonPost(dto, rstClass, timeoutSeconds);

	}

	public static <T> T invoke(DynamicDict dto, TypeToken<T> rstClass)
			throws FrameException {

		return invoke(dto, rstClass, -1);

	}

	private static <T> T invokeJsonPost(DynamicDict dto,
			TypeToken<T> typeToken, int timeoutSeconds)
			throws FrameException {
		String url = ServerConfig.getServerPath() + ServerConfig.COMMON_REQ_URL;
		return invokeJsonPost(url, dto, typeToken, timeoutSeconds);

	}

	public static <T> T invokeByUrl(String paramUrl, TypeToken<T> typeToken)
			throws FrameException {
		String url = ServerConfig.getServerUrl() + ServerConfig.QUERY_REQ_URL
				+ paramUrl;
		return invokeJsonPost(url, null, typeToken, -1);

	}

	private synchronized static <T> T invokeJsonPost(String url,
			DynamicDict dto, TypeToken<T> typeToken, int timeoutSeconds)
			throws FrameException {
		DefaultHttpClient client = null;
		HttpEntity entity2 = null;
		HttpPost httpPost = null;
		T obj2 = null;
		try {
			httpPost = new HttpPost(url);
			// 1.参入公共参数
			Caller.setHeader(httpPost, ACCEPT.JSON, dto);
			Type targetType = new TypeToken<DynamicDict>() {
			}.getType();

			String req = Base64.encodeObject(dto);
			HttpEntity entity = new StringEntity(req, "UTF-8");
			// HttpEntity entity = new StringEntity(JsonUtil.toJson(dto,
			// targetType), "UTF-8");
			httpPost.setEntity(entity);
			if (timeoutSeconds == -1) {
				client = getHttpClient();
			} else {
				client = getHttpClient(timeoutSeconds);
			}
			HttpResponse response = client.execute(httpPost);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK
					&& statusCode != HttpStatus.SC_NO_CONTENT) {
				throw new Exception("HttpStatusCode:" + statusCode);
			} else if (statusCode == HttpStatus.SC_REQUEST_TIMEOUT) {
				throw new Exception("网络连接超时");
			}

			entity2 = response.getEntity();
			if (entity2 != null) {
				InputStream instream = entity2.getContent();
				String result = StringUtils.convertStreamToString(instream);
				instream.close();
				T serviceResult = null;
				if (dto != null) {
					Object obj = Base64.decodeToObject(result);
					serviceResult = (T) obj;

				} else {
					result = result.trim();
					serviceResult = (T) result;
					// result = JsonUtil.toJsonStr(result);
					// serviceResult = JsonUtil.fromJson(result, typeToken);
					//
					// if (typeToken != null && serviceResult == null) {
					// serviceResult = (T) JsonUtil.fromJson(result,
					// typeToken.getRawType());
					// }
				}
				if (null == serviceResult) {
					throwException(result);
				}
				obj2 = serviceResult;
				
			}
		} catch (Exception e) {
			if (httpPost.isAborted()) {// 强制关闭联接
				httpPost.abort();
			}
			if (e instanceof HttpHostConnectException) {
				throw new FrameException("网络不可用，或服务端正在维护中，请稍后再试");
			} else {
				throw new FrameException("请求服务器出错：" + e.getMessage());
			}
		} finally {
			try {

				if (timeoutSeconds != -1 && client != null) {
					client.getConnectionManager().shutdown();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj2;
	}

	/**
	 * 请求头封�?
	 */
	private static void setHeader(HttpUriRequest httpUriRequest, String accept) {
		setHeader(httpUriRequest, accept, null);
	}

	/**
	 * 请求头封�?
	 */
	private static void setHeader(HttpUriRequest httpUriRequest, String accept,
			DynamicDict dto) {
		if (httpUriRequest == null)
			return;

//		String userName = ClientCache.getInstance().getUserName();
//		String passWord = ClientCache.getInstance().getPassWord();
//
//		httpUriRequest.addHeader(ParamConst.USER_NAME, userName);
//		httpUriRequest.addHeader(ParamConst.PASS_WORD, passWord);
//
//		httpUriRequest.addHeader("connectType", Constant.getConnectType());

		// default accept value
		if (StringUtils.isEmpty(accept)) {
			accept = ACCEPT.JSON;
		}
		if (StringUtils.isEqual(ACCEPT.JSON, accept)) {
			httpUriRequest.addHeader("content-type", ACCEPT.JSON);
			httpUriRequest.setHeader("Accept", ACCEPT.JSON);
		} else if (StringUtils.isEqual(ACCEPT.TEXT, accept)) {
			httpUriRequest.setHeader("Accept", ACCEPT.TEXT);
		}

	}

	private static void throwException(String result) throws Exception {
		if (result == null) {
			// 抛出异常
			throw new Exception("service response error!");
		}

		throw new Exception(result);

	}

	

	
}
