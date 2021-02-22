package com.zte.cbss.autoprocess.sometestcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.zte.cbss.autoprocess.MyTools;
import com.zte.cbss.autoprocess.WebClientDevWrapper;

/**
 * 测试登录
 * @author tanghaoyang
 *
 */
public class Login {
	private static Logger logger = Logger.getLogger(Login.class);
	private static String BSS_JSESSIONID=null;
	public static void main(String[] args) throws Exception {
		// 获得httpclient对象
		HttpClient httpclient = WebClientDevWrapper.wrapClient(new DefaultHttpClient());
		HttpClientParams.setCookiePolicy(httpclient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);    
		
		logger.info("第一步：获取登录前页面");
		String preLoginHtml=visitLoginPage(httpclient);
		MyTools.logToFile("d:\\1-登录前.html",preLoginHtml);
		printCookies(httpclient);
		
		logger.info("第二步：发送登录请求");
		String afterLoginHtml=sendLoginRequest(httpclient);
		MyTools.logToFile("d:\\2-登录后.html",afterLoginHtml);
		printCookies(httpclient);
		
		logger.info("第三步，登录后再次发送登录参数");
		String redirectHtml=sendRedirectRequest(httpclient);
		MyTools.logToFile("d:\\3-重定向.html",redirectHtml);
		List<Cookie> curCookieList=printCookies(httpclient);
//		for(Cookie ck : curCookieList){
//			String cookieName = ck.getName();
//			if(cookieName.equals("BSS_JSESSIONID")){
//				BSS_JSESSIONID=ck.getValue();
//			}
//		}
		
		String url="https://gd.cbss.10010.com/essframe?service=page/Header&LOGIN_LOG_ID=null";
		String html=null;
		html=getUrl(url,httpclient);
		
		//html=useMyCookies(httpclient);
		
		logger.info(html);
		
		Thread.sleep(1000000);
	}
	
	public static String useMyCookies(HttpClient httpclient) throws ClientProtocolException, IOException{
		
		String url="https://gd.cbss.10010.com/essframe?service=page/Header&LOGIN_LOG_ID=null";
	    String cookies = "BSS_JSESSIONID=D-jp4KL2IaiS99Zup8cSYUa93U8X3iRAoG_BqqaQfiRsmGELrfgK!134916939!-157802056";
        // 创建httpget.
        HttpGet httpget = new HttpGet(url);
        httpget.addHeader("Referer", "https://gd.cbss.10010.com/essframe?service=page/LoginProxy&login_type=redirectLogin");
        httpget.addHeader("Host", "gd.cbss.10010.com");
        httpget.addHeader("Connection", "Keep-Alive");
        httpget.addHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
  
		httpget.addHeader("Cookie", cookies);
        // 执行get请求.
        HttpResponse response = httpclient.execute(httpget);
        // 获取响应实体
        HttpEntity entity = response.getEntity();
        String responseStr = null;
		if (entity != null) {
             responseStr = EntityUtils.toString(entity);
        }
        return responseStr;
	}

	public static List<Cookie> printCookies(HttpClient httpclient){
		logger.info(System.currentTimeMillis());
		List<Cookie> cookies = ((AbstractHttpClient) httpclient).getCookieStore().getCookies();   
		if (cookies.isEmpty()) {    
			logger.info("None");    
		} else {    
			for (int i = 0; i < cookies.size(); i++) {  
				logger.info("- " + cookies.get(i).toString());  
			}    
		}
		return cookies;  
	}
	
	/**
	 * 发送登录请求
	 * @param httpclient
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static String sendLoginRequest(HttpClient httpclient) throws ClientProtocolException, IOException {
		String url="https://gd.cbss.10010.com/essframe?service=page/LoginProxy&login_type=redirectLogin&service=direct%2F1%2FHome%2F%24Form&sp=S0&Form0=LOGIN_PROVINCE_REDIRECT_URL%2CAUTH_TYPE%2CCAPTURE_URL%2CWHITE_LIST_LOGIN%2CIPASS_LOGIN%2CIPASS_SERVICE_URL%2CIPASS_LOGIN_PROVINCE%2CIPASS_LOGINOUT_DOMAIN%2CSIGNATURE_CODE%2CSIGNATURE_DATA%2CIPASS_ACTIVATE%2CSTAFF_ID%2C%24FormConditional%2C%24FormConditional%240%2CLOGIN_PROVINCE_CODE%2C%24FormConditional%241%2C%24FormConditional%242%2C%24FormConditional%243%2C%24FormConditional%244%2C%24FormConditional%245%2C%24TextField%2C%24TextField%240%2C%24TextField%241%2C%24TextField%242%2C%24TextField%243%2C%24TextField%244%2C%24TextField%245%2C%24TextField%246%2C%24TextField%247%2C%24TextField%248%2C%24TextField%249%2C%24TextField%2410%2C%24TextField%2411%2C%24TextField%2412%2C%24TextField%2413%2C%24TextField%2414%2C%24TextField%2415%2C%24TextField%2416%2C%24TextField%2417%2C%24TextField%2418%2C%24TextField%2419%2C%24TextField%2420%2C%24TextField%2421%2C%24TextField%2422%2C%24TextField%2423%2C%24TextField%2424%2C%24TextField%2425%2C%24TextField%2426%2C%24TextField%2427%2C%24TextField%2428%2C%24TextField%2429%2C%24TextField%2430&%24FormConditional=F&%24FormConditional%240=T&%24FormConditional%241=F&%24FormConditional%242=F&%24FormConditional%243=F&%24FormConditional%244=F&%24FormConditional%245=F&LOGIN_PROVINCE_REDIRECT_URL=https%3A%2F%2Fgd.cbss.10010.com%2Fessframe&AUTH_TYPE=0&CAPTURE_URL=%2Fimage%3Fmode%3Dvalidate%26width%3D60%26height%3D20&WHITE_LIST_LOGIN=&IPASS_LOGIN=&IPASS_SERVICE_URL=&IPASS_LOGIN_PROVINCE=&IPASS_LOGINOUT_DOMAIN=&SIGNATURE_CODE=&SIGNATURE_DATA=&IPASS_ACTIVATE=&STAFF_ID=DSZDGL01&LOGIN_PASSWORD=%2BcfManXzORZEM6m7RxVvIztGm4w%3D&LOGIN_PROVINCE_CODE=51";
		HttpGet httpPost = new HttpGet(url);
		//httpPost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		httpPost.addHeader("Referer", "https://cbss.10010.com/essframe");
		httpPost.addHeader("Host", "gd.cbss.10010.com");
		httpPost.addHeader("Connection", "Keep-Alive");
		httpPost.addHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
			
//		List<NameValuePair> nvps = makeLoginParams();
//		httpPost.setEntity(new UrlEncodedFormEntity(nvps));  

		HttpContext localContext = new BasicHttpContext();
		CookieStore gCurCookieStore = new BasicCookieStore();
		localContext.setAttribute(ClientContext.COOKIE_STORE, gCurCookieStore);
		
		// 发送请求
		HttpResponse response = httpclient.execute(httpPost,localContext);
		logger.info("cookie个数："+gCurCookieStore.getCookies().size());
		// 输出返回值
		InputStream is = response.getEntity().getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"gb2312"));
		String line;
		StringBuffer sb=new StringBuffer();
		while((line=br.readLine())!=null){
			sb.append(line);
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * 发送重定向请求
	 * @param httpclient
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static String sendRedirectRequest(HttpClient httpclient) throws ClientProtocolException, IOException {
		logger.info("请输入sessionId");
		Scanner inputReader = new Scanner(System.in);
		
		String sid=inputReader.nextLine();
		String url="https://gd.cbss.10010.com/essframe;BSS_JSESSIONID="+sid;
		HttpPost httpPost = new HttpPost(url);
		//httpPost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");

		List<NameValuePair> nvps = makeRedirectParams();
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));  

		// 发送请求
		HttpResponse response = httpclient.execute(httpPost);
		// 输出返回值
		InputStream is = response.getEntity().getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"gb2312"));
		String line;
		StringBuffer sb=new StringBuffer();
		while((line=br.readLine())!=null){
			sb.append(line);
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * 访问登录页面
	 * @param httpclient
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static String visitLoginPage(HttpClient httpclient) throws ClientProtocolException, IOException {
		String url="https://cbss.10010.com/essframe";
		return getUrl(url, httpclient);
	}
	
	/**
	 * 获取指定url的内容
	 * @param url
	 * @param httpclient
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getUrl(String url,HttpClient httpclient) throws ClientProtocolException, IOException {
		HttpGet Httpget = new HttpGet(url);

		// 发送请求
		HttpResponse response = httpclient.execute(Httpget);
		// 输出返回值
		InputStream is = response.getEntity().getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"gbk"));
		String line;
		StringBuffer sb=new StringBuffer();
		while((line=br.readLine())!=null){
			sb.append(line);
			sb.append("\n");
		}
		return sb.toString();
	}


	/**
	 * 构建登录参数
	 * @return
	 */
	/*public static List<NameValuePair> makeLoginParams(){
		Map<String,String> map=new HashMap<String,String>();
		map.put("service","direct/1/Home/$Form");
		map.put("sp","S0");
		map.put("Form0","LOGIN_PROVINCE_REDIRECT_URL,AUTH_TYPE,CAPTURE_URL,WHITE_LIST_LOGIN,IPASS_LOGIN,IPASS_SERVICE_URL,IPASS_LOGIN_PROVINCE,IPASS_LOGINOUT_DOMAIN,SIGNATURE_CODE,SIGNATURE_DATA,IPASS_ACTIVATE,STAFF_ID,$FormConditional,$FormConditional$0,LOGIN_PROVINCE_CODE,$FormConditional$1,$FormConditional$2,$FormConditional$3,$FormConditional$4,$FormConditional$5,$TextField,$TextField$0,$TextField$1,$TextField$2,$TextField$3,$TextField$4,$TextField$5,$TextField$6,$TextField$7,$TextField$8,$TextField$9,$TextField$10,$TextField$11,$TextField$12,$TextField$13,$TextField$14,$TextField$15,$TextField$16,$TextField$17,$TextField$18,$TextField$19,$TextField$20,$TextField$21,$TextField$22,$TextField$23,$TextField$24,$TextField$25,$TextField$26,$TextField$27,$TextField$28,$TextField$29,$TextField$30");
		map.put("$FormConditional","F");
		map.put("$FormConditional$0","T");
		map.put("$FormConditional$1","F");
		map.put("$FormConditional$2","F");
		map.put("$FormConditional$3","F");
		map.put("$FormConditional$4","F");
		map.put("$FormConditional$5","F");
		map.put("LOGIN_PROVINCE_REDIRECT_URL","https://gd.cbss.10010.com/essframe");
		map.put("AUTH_TYPE","0");
		map.put("CAPTURE_URL","/image?mode=validate&width=60&height=20");
		map.put("STAFF_ID","DSZDGL01");
		map.put("LOGIN_PASSWORD","+cfManXzORZEM6m7RxVvIztGm4w=");
		map.put("LOGIN_PROVINCE_CODE","51");
		
//		map.put("service","direct%2F1%2FHome%2F%24Form");
//		map.put("sp","S0");
//		map.put("Form0","LOGIN_PROVINCE_REDIRECT_URL%2CAUTH_TYPE%2CCAPTURE_URL%2CWHITE_LIST_LOGIN%2CIPASS_LOGIN%2CIPASS_SERVICE_URL%2CIPASS_LOGIN_PROVINCE%2CIPASS_LOGINOUT_DOMAIN%2CSIGNATURE_CODE%2CSIGNATURE_DATA%2CIPASS_ACTIVATE%2CSTAFF_ID%2C%24FormConditional%2C%24FormConditional%240%2CLOGIN_PROVINCE_CODE%2C%24FormConditional%241%2C%24FormConditional%242%2C%24FormConditional%243%2C%24FormConditional%244%2C%24FormConditional%245%2C%24TextField%2C%24TextField%240%2C%24TextField%241%2C%24TextField%242%2C%24TextField%243%2C%24TextField%244%2C%24TextField%245%2C%24TextField%246%2C%24TextField%247%2C%24TextField%248%2C%24TextField%249%2C%24TextField%2410%2C%24TextField%2411%2C%24TextField%2412%2C%24TextField%2413%2C%24TextField%2414%2C%24TextField%2415%2C%24TextField%2416%2C%24TextField%2417%2C%24TextField%2418%2C%24TextField%2419%2C%24TextField%2420%2C%24TextField%2421%2C%24TextField%2422%2C%24TextField%2423%2C%24TextField%2424%2C%24TextField%2425%2C%24TextField%2426%2C%24TextField%2427%2C%24TextField%2428%2C%24TextField%2429%2C%24TextField%2430");
//		map.put("%24FormConditional","F");
//		map.put("%24FormConditional%240","T");
//		map.put("%24FormConditional%241","F");
//		map.put("%24FormConditional%242","F");
//		map.put("%24FormConditional%243","F");
//		map.put("%24FormConditional%244","F");
//		map.put("%24FormConditional%245","F");
//		map.put("LOGIN_PROVINCE_REDIRECT_URL","https%3A%2F%2Fgd.cbss.10010.com%2Fessframe");
//		map.put("AUTH_TYPE","0");
//		map.put("CAPTURE_URL","%2Fimage%3Fmode%3Dvalidate%26width%3D60%26height%3D20");
//		map.put("STAFF_ID","DSZDGL01");
//		map.put("LOGIN_PASSWORD","%2BcfManXzORZEM6m7RxVvIztGm4w%3D");
//		map.put("LOGIN_PROVINCE_CODE","51");

		
		return mapToList(map);
	}*/
	
	/**
	 * 构建重定向参数
	 * @return
	 */
	public static List<NameValuePair> makeRedirectParams(){
		Map<String,String> map=new HashMap<String,String>();
		map.put("service","direct/1/LoginProxy/$Form");
		map.put("sp","S0");
		map.put("Form0","ACTION_MODE,STAFF_ID,LOGIN_PASSWORD,NEED_SMS_VERIFY,SUBSYS_CODE,LOGIN_TYPE,authDomainType,soap,menuId,error,authType,LOGIN_PROVINCE_CODE,VERIFY_CODE,WHITE_LIST_LOGIN,IPASS_SERVICE_URL,IPASS_CHECK_MESSAGE,IPASS_LOGIN_PROVINCE,SIGNATURE_CODE,SIGNATURE_DATA,IPASS_LOGIN,IPASS_ACTIVATE,NEED_INSTALL_CERT,IPASS_INSTALL_RESULT,IPASS_INSTALL_MESSAGE,IPASS_LOGINOUT_DOMAIN,btnProxyLogin");
		map.put("STAFF_ID","DSZDGL01");
		map.put("LOGIN_PASSWORD","+cfManXzORZEM6m7RxVvIztGm4w=");
		map.put("LOGIN_TYPE","redirectLogin");
		map.put("LOGIN_PROVINCE_CODE","51");
		map.put("btnProxyLogin","%CC%E1%BD%BB%B2%E9%D1%AF%C4%DA%C8%DD");

		return mapToList(map);
	}
	
	/**
	 * 将map转化成封装了基本参数的list
	 * @param map
	 * @return
	 */
	public static List<NameValuePair> mapToList(Map<String,String> map){
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Iterator<String> it=map.keySet().iterator();
		String key,value;
		while(it.hasNext()){
			key=it.next();
			value=map.get(key);
			nvps.add(new BasicNameValuePair(key,value));  
		}
		
		return nvps;
	}
}
