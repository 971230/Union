package com.ztesoft.common.util.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;

import com.ztesoft.common.util.MapUtil;
import com.ztesoft.common.util.StringUtils;

public class SetCookieUtil {

	public static List<Cookie> assembleCookie(Map<String, String> params) {
		List<Cookie> cookies = new ArrayList<Cookie>();
		if (params != null && !params.isEmpty()) {
			for (Iterator<String> iterator = params.keySet().iterator(); iterator
					.hasNext();) {
				String key = iterator.next();
				if (key.startsWith("cookie_")) {
					String name = key.substring(7, key.length());
					String value = params.get(key);
					Cookie cookie = new BasicClientCookie(name, value);
					cookies.add(cookie);
					iterator.remove();
				}
			}

		}
		return cookies;
	}

	public static List<Header> assembleHeaders(List<Cookie> cookies) {
		List<Header> headers = new ArrayList<Header>();

		headers.add(new BasicHeader("Accept",
				"text/html, application/xhtml+xml, */*"));
		headers.add(new BasicHeader("Accept-Encoding", "gzip, deflate"));
		headers.add(new BasicHeader("Accept-Language", "zh-CN"));
		headers.add(new BasicHeader("Connection", "Keep-Alive"));
		// headers.add(new BasicHeader("Host", "hljoa.bf.ctc.com"));
		headers.add(new BasicHeader("Cache-Control", "no-cache"));

		String dot = "";
		StringBuffer cookieBuffer = new StringBuffer();

		for (Cookie cookie : cookies) {
			if (cookieBuffer.length() > 0)
				cookieBuffer.append(";");
			cookieBuffer.append(dot).append(cookie.getName()).append("=")
					.append(cookie.getValue());
		}

		if (cookieBuffer.length() > 0) {
			headers.add(new BasicHeader("Cookie", cookieBuffer.toString()));
		}

		return headers;
	}

	public static CookieStore assembleCookieStore(List<Cookie> inCookies) {
		CookieStore cookieStore = new BasicCookieStore();

		for (Cookie temp : inCookies) {
			cookieStore.addCookie(temp);
		}

		return cookieStore;
	}

	public static Map getHttpParams(Map<String, String> params) {
		Map httpParams = new HashMap();

		for (Iterator<String> iterator = MapUtil.safe(params).keySet()
				.iterator(); iterator.hasNext();) {
			String key = iterator.next();
			if (!key.startsWith("cookie_")) {
				httpParams.put(key, params.get(key));
			}
		}
		return httpParams;
	}

	public static Map<String, String> getCookieParams(Map params) {
		Map<String, String> cookieParams = new HashMap<String, String>();

		for (Iterator<String> iterator = MapUtil.safe(params).keySet()
				.iterator(); iterator.hasNext();) {
			String key = iterator.next();
			if (key.startsWith("cookie_")) {
				String value = StringUtils.getStrValue(params, key);
				cookieParams.put(key, value);
			}
		}
		return cookieParams;
	}

}
