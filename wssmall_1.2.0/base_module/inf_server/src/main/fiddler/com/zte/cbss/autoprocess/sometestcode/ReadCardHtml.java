package com.zte.cbss.autoprocess.sometestcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.zte.cbss.autoprocess.WebClientDevWrapper;

/**
 * <p>Title: </p>
 * <p>Description:   </p>
 * <p>Company: 从兴技术有限公司 </p>
 * @author: yuchanghong
 * @version 1.0
 * @date: 2014-4-29 下午5:39:51
 */

public class ReadCardHtml {
	public static void main(String[] args) throws Exception {
		String url = "https://hq.cbss.10010.com/essframe?service=page/LoginProxy&login_type=redirectLogin";
		String filePath = "d:\\test.txt";
		File file = new File(filePath);
		FileOutputStream outputStream = new FileOutputStream(file);

		// 获得httpclient对象
		HttpClient httpclient = WebClientDevWrapper.wrapClient(new DefaultHttpClient());
		HttpPost httpPost = null;
		httpPost = new HttpPost(url);
//		httpPost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "vip"));  
		nvps.add(new BasicNameValuePair("password", "secret")); 
		nvps.add(new BasicNameValuePair("LOGIN_PROVINCE_CODE", "11"));  
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));  

		// 发送请求
		HttpResponse response = httpclient.execute(httpPost);
		// 输出返回值
		InputStream is = response.getEntity().getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		byte b[] = new byte[1024];
		int j = 0;
		while ((j = is.read(b)) != -1) {
			outputStream.write(b, 0, j);
		}
		outputStream.flush();
		outputStream.close();

	}
}
