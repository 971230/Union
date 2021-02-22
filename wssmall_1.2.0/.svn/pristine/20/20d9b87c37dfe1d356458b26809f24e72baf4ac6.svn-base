package com.zte.cbss.autoprocess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
/**
 * 利用httpclent4封装一个自定义浏览器
 * @author tanghaoyang
 *
 */
public class HCBrowser {
	private static final Logger log=Logger.getLogger(HCBrowser.class);
	/**采用CookieStore方案处理cookie*/
	private CookieStore gCurCookieStore = null;
	//private HashMap<Object, Object> calcTimeKeyDict;
	private HashMap<String, Long> calcTimeKeyDict;
	//private Map<String, Long> calcTimeKeyDict;

    //IE7
	private static final String constUserAgent_IE7_x64 = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E)";
    //IE8
	private static final String constUserAgent_IE8_x64 = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E";
    //IE9
	private static final String constUserAgent_IE9_x64 = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)"; // x64
	private static final String constUserAgent_IE9_x86 = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)"; // x86
    //Chrome
	private static final String constUserAgent_Chrome = "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.99 Safari/533.4";
    //Mozilla Firefox
	private static final String constUserAgent_Firefox = "Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:1.9.2.6) Gecko/20100625 Firefox/3.6.6";
	
	private static String gUserAgent = "";
	
	private static int fileNameFLag = 0;
	
	
	public HCBrowser()
	{
		gUserAgent = constUserAgent_IE8_x64;
		gCurCookieStore = new BasicCookieStore();
		
		calcTimeKeyDict = new HashMap<String, Long>();
	}

	/** start calculate time */
	public long calcTimeStart(String uniqueKey)
	{
		long startMilliSec = 0;
		startMilliSec = System.currentTimeMillis(); //1373525642597
		calcTimeKeyDict.put(uniqueKey, startMilliSec); //{load_dd_file=1373525642597}
		return startMilliSec;
	}
	
	/** end calculate time */
	public long calcTimeEnd(String uniqueKey)
	{
		long endMilliSec = System.currentTimeMillis(); //1373525686178
		
		long elapsedMilliSec = 0;
		if(calcTimeKeyDict.containsKey(uniqueKey))
		{
			long startMilliSec = calcTimeKeyDict.get(uniqueKey); //1373525642597
			elapsedMilliSec = endMilliSec - startMilliSec; //43581
		}
		
		return elapsedMilliSec;
	}
	
	/* format date value into string */
	public String dateToString(Date date, String format)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format); 
		String datetimeStr =simpleDateFormat.format(date);  //2013-07-08_033034
		return datetimeStr;
	}
	
	/* output string into file */
	public boolean outputStringToFile(String strToOutput, String fullFilename)
	{
		boolean ouputOk = true;
		
        File newTextFile = new File(fullFilename);
        FileWriter fw;
        try {
			fw = new FileWriter(newTextFile);
	        fw.write(strToOutput);
	        fw.close();
        } catch (IOException e) {
			e.printStackTrace();
        	
        	ouputOk = false;
		}
        
        return ouputOk;
	}
		
	public void dbgPrintCookies(List<Cookie> cookieList, String url)
	{
		if((null != url) && (!url.isEmpty()))
		{
			log.info("Cookies for " + url);
		}
		
		for(Cookie ck : cookieList)
		{
			log.info(ck);
		}
	}
	
	public void dbgPrintCookies(CookieStore cookieStore)
	{
		dbgPrintCookies(cookieStore, null);
	}
	
	public void dbgPrintCookies(CookieStore cookieStore, String url)
	{
		List<Cookie> cookieList = cookieStore.getCookies();
		dbgPrintCookies(cookieList, url);
	}
	
	public void dbgPrintCookies(List<Cookie> cookieList)
	{
		dbgPrintCookies(cookieList, null);
	}
	
	public CookieStore getCurCookieStore()
	{
		return gCurCookieStore;
	}

	public List<Cookie> getCurCookieList()
	{
		if(null != gCurCookieStore)
		{
			return gCurCookieStore.getCookies();
		}
		else
		{
			return null;
		}
	}

	public void setCurCookieStore(CookieStore newCookieStore)
	{
		gCurCookieStore = newCookieStore;
	}

	public void setCurCookieList(List<Cookie> newCookieList)
	{
		gCurCookieStore.clear();
		for(Cookie eachNewCk : newCookieList)
		{
			gCurCookieStore.addCookie(eachNewCk);
		}
	}
	
    /** Get response from url  */
    public HttpResponse getUrlResponse(
    		String url,
    		List<NameValuePair> headerDict,
    		List<NameValuePair> postDict,
    		int timeout
    		)
    {
    	// init
    	HttpResponse response = null;
    	HttpUriRequest request = null;
    
    	//这里改变了默认的hc对象
    	HttpClient httpClient =	WebClientDevWrapper.wrapClient(new DefaultHttpClient());
    	
    	//HttpParams headerParams = new HttpParams();
    	//HttpParams headerParams = new DefaultedHttpParams(headerParams, headerParams);
    	//HttpParams headerParams = new BasicHttpParams();
    	BasicHttpParams headerParams = new BasicHttpParams();
    	//HttpConnectionParams.
		//default enable auto redirect
    	headerParams.setParameter(CoreProtocolPNames.USER_AGENT, gUserAgent);
    	headerParams.setParameter(ClientPNames.HANDLE_REDIRECTS, Boolean.TRUE);
    	headerParams.setParameter(CoreConnectionPNames.SO_KEEPALIVE, Boolean.TRUE);
    	
    	if(postDict != null)
    	{
    		HttpPost postReq = new HttpPost(url);
    		try{
    			HttpEntity postBodyEnt = new UrlEncodedFormEntity(postDict,"UTF-8");
    			postReq.setEntity(postBodyEnt);
    		}
    		catch(Exception e){
    			e.printStackTrace();
    		}

    		request = postReq;
    	}
    	else
    	{
        	HttpGet getReq = new HttpGet(url);
        	
        	request = getReq;
    	}

    	if(headerParams != null)
    	{
    		//HttpProtocolParams.setUserAgent(headerParams, gUserAgent);
    		//headerParams.setHeader(HttpMethodParams.USER_AGENT, gUserAgent);
    		request.setParams(headerParams);
    	}
    	
    	if(headerDict!=null){
    		for(NameValuePair nvp:headerDict){
    			request.addHeader(nvp.getName(), nvp.getValue());
    		}
    	}
    	
    	//request.setHeader("User-Agent", gUserAgent);
    	

		try{			
			HttpContext localContext = new BasicHttpContext();
			localContext.setAttribute(ClientContext.COOKIE_STORE, gCurCookieStore);
			response = httpClient.execute(request, localContext);
		} catch (ClientProtocolException cpe) {
        	cpe.printStackTrace();
        } catch (IOException ioe) {
        	ioe.printStackTrace();
        }
		
    	return response;
    }
    
    /** Get response from url  */
    public HttpResponse getUrlResponse(String url)
    {
    	return getUrlResponse(url, null, null, 0);
    }

    /** Get response html from url, headerDict, html charset, postDict */
    public String getUrlRespHtml(
    							String url,
    							List<NameValuePair> headerDict,
    							List<NameValuePair> postDict,
    				    		int timeout,
    				    		String htmlCharset
								)
    {
    	// init
    	String respHtml = "";
    	String defaultCharset = "gbk";
    	if((null == htmlCharset) || htmlCharset.isEmpty())
    	{
    		htmlCharset = defaultCharset;
    	}
    	//init 
    	//HttpClient httpClient = new DefaultHttpClient();
    	//DefaultHttpClient httpClient = new DefaultHttpClient();
    	//HttpUriRequest request;
    	
    	//headerParams.setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, htmlCharset);

		try{
			
			HttpResponse response = getUrlResponse(url, headerDict, postDict, timeout);
			
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				HttpEntity respEnt = response.getEntity();
				
				respHtml = EntityUtils.toString(respEnt, htmlCharset);
	        }
	        
        } catch (ClientProtocolException cpe) {
        	cpe.printStackTrace();    
        } catch (IOException ioe) {
        	ioe.printStackTrace();
        }
		//write the log for http request and response
		StringBuffer logBuf = new StringBuffer();
		logBuf.append("Thread: "+Thread.currentThread().getName());
		logBuf.append("\r\n");
		logBuf.append("url: "+url);
		logBuf.append("\r\n");
		logBuf.append("====================request Header================");
		logBuf.append("\r\n");
		if(headerDict!=null && headerDict.size() > 0){
			for(NameValuePair nameVal : headerDict){
				logBuf.append("&"+nameVal+"\r\n");
			}
		}
		logBuf.append("\r\n");
		logBuf.append("====================request post entity================");
		logBuf.append("\r\n");
		if(postDict!=null && postDict.size() > 0){
			for(NameValuePair nameVal : postDict){
				logBuf.append(nameVal+"&");
			}
		}
		logBuf.append("\r\n");
		logBuf.append("==================resopnse=========================");		
		logBuf.append("\r\n");
		logBuf.append(respHtml);
		log.info(logBuf.toString());
		
//		fileNameFLag ++;
//		StringBuffer sb= new StringBuffer("?");
//		if(postDict!=null && postDict.size() > 0){
//			for(NameValuePair nameVal : postDict){
//				sb.append("&"+nameVal+"\r\n");
//			}
//		}
//		MyTools.logToFile("/usr/Tomcat_test/logs/responseText"+fileNameFLag+".txt", url+"\r\n"+sb.toString()+"\r\n\r\n"+respHtml);
    	return respHtml;
    }
    
    public String getUrlRespHtml(String url, List<NameValuePair> headerDict, List<NameValuePair> postDict)
    {
    	return getUrlRespHtml(url, headerDict, postDict, 0, "");
    }
    
    public String getUrlRespHtml(String url, String htmlCharset)
    {
    	return getUrlRespHtml(url, null, null, 0, htmlCharset);
    }
    
    public String getUrlRespHtml(String url)
    {
    	String defaulCharset = "UTF-8";
    	return getUrlRespHtml(url, defaulCharset);
    }
    
    public interface UpdateProgressCallback
    {
        // This is just a regular method so it can return something or
        // take arguments if you like.
        public void updateProgress(long currentSize, long totalSize);
    }

    /**
     *  download file from file url
     * eg:
     * http://m5.songtaste.com/201212211424/2e8a8a85d93f56370d7fd96b5dc6ff23/5/5c/5cf23a97cef6fad6a464eb506c409dbd.mp3
     * with header: Referer=http://songtaste.com/
     *  */
    public Boolean downlodFile(String url, File fullFilename, List<NameValuePair> headerDict, UpdateProgressCallback updateProgressCallbak)
    {
    	Boolean downloadOk = Boolean.FALSE;
    	
    	HttpResponse response = getUrlResponse(url, headerDict, null, 0);

		if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
			
			HttpEntity respEnt = response.getEntity();
			
			log.info("isChunked" + respEnt.isChunked());
			log.info("Streaming" + respEnt.isStreaming());
			
			Boolean isStream = respEnt.isStreaming();
			if(isStream){
				try {
					InputStream fileInStream = respEnt.getContent();
					
					FileOutputStream fileOutStream = new FileOutputStream(fullFilename);
					
					long totalSize = respEnt.getContentLength();
					byte[] tmpBuf = new byte[8192];
					int bufLen = 0;
					long downloadedSize = 0;
					while( (bufLen = fileInStream.read(tmpBuf)) > 0 ) {
						fileOutStream.write(tmpBuf,0, bufLen);
						downloadedSize += bufLen;
						
						//log.info(Long.toString((downloadedSize/totalSize)*100)+"%");
						//log.info(Long.toString((downloadedSize*100)/totalSize)+"%");
						updateProgressCallbak.updateProgress(downloadedSize, totalSize);
					}
					fileOutStream.close();
					downloadOk = Boolean.TRUE;
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        }

		return downloadOk;
    }

    /**
     *  none header version of downlodFile
     *  */
    public String downlodFile(String url, String fullFilename)
    {
    	return downlodFile(url, fullFilename);
    }
    
    /** Extract single string from input whole string
     *	Note:
     * 1. input pattern should include one group, like 'xxx(xxx)xxx'
     * 2. output is in extractedStr
     *  */
    public Boolean extractSingleStr(String pattern, String extractFrom, int flags, StringBuilder extractedStr)
    {
    	Pattern strP = Pattern.compile(pattern, flags);
    	Matcher foundStr = strP.matcher(extractFrom);
    	Boolean found = foundStr.find();
    	if(found)
    	{
    		extractedStr.append(foundStr.group(1));
    	}
    	return found;
    }

    /**
     * None pattern version of  extractSingleStr
     * */
    public Boolean extractSingleStr(String pattern, String extractFrom, StringBuilder extractedStr)
    {
    	return extractSingleStr(pattern, extractFrom, 0, extractedStr);
    }

}
