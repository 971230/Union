package com.zte.cbss.autoprocess;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

/**
 * 模拟登录联通cbss系统，主要目的在于获取登录成功后回写到cookie中的BSS_JSESSIONID值
 * @author tanghaoyang
 *
 */
public class Login {
	private static final Logger log=Logger.getLogger(Login.class);
	
	/**用于标识客户端已登录的cookie名*/
	public static final String BSS_JSESSIONID="BSS_JSESSIONID";
	/**用于判断是否登录成功的字符串  */
	public static final String IS_LOGIN_JUDGE_STRING="<frameset id=\"aset\"";
	
	private static HCBrowser hcb;

	public static void main(String[] args) throws Exception {
		emulateLogin();
	}

	
	/**模拟登录
	 * @throws Exception */
	public static HCBrowser emulateLogin() throws Exception {
		//初始化浏览器对象
		hcb = new HCBrowser();
		
		log.info("第一步,访问登录前页面");
		visitLoginPage();

		log.info("第二步,发送登录请求");
		sendLoginRequest();

		log.info("第三步,登录后重定向操作");
		String redirectHtml=redirectToIndex();
		if(redirectHtml!=null&&redirectHtml.indexOf(IS_LOGIN_JUDGE_STRING)!=-1){
			log.info("========恭喜！登录[cbss系统]成功！========");
		}else{
			log.warn("========登录失败！！！========");
			log.warn("登录失败！请确认用户名和密码及其他参数是否正确:\n"+redirectHtml);
			throw new Exception("登录失败！请确认用户名和密码及其他参数是否正确！");
		}
		
		log.info("第四步，初始化访问首页的一系列其他url，尽量模拟得像真实浏览器访问");
		visitOtherPage();

		List<NameValuePair> headParams = new ArrayList<NameValuePair>();
		headParams.add(new BasicNameValuePair("Referer","https://gd.cbss.10010.com/essframe"));//?service=page/component.Navigation&listener=init&needNotify=true&staffId=DSZDGL01&departId=51b12z3&subSysCode=BSS&eparchyCode=0757"));
		
		String redirectLoginUrl="https://gd.cbss.10010.com/essframe?service=ajaxDirect/1/component.NavMenu/component.NavMenu/javascript/NotifysPart&pagename=component.NavMenu&eventname=queryNotifys&staffId=DSZDGL01&departId=51b12z3&subSysCode=BSS&eparchyCode=0757&partids=NotifysPart&random=20145112247245&ajaxSubmitType=get";
		String html = hcb.getUrlRespHtml(redirectLoginUrl, headParams, null);
//		logger.info(html);
		return hcb;
	}

	/**访问其他页面*/
	private static void visitOtherPage() {
		String urls[]=new String[]{"https://gd.cbss.10010.com/essframe?service=page/Header&LOGIN_LOG_ID=null",
				"https://gd.cbss.10010.com/essframe?service=page/Nav&STAFF_ID=DSZDGL01",
				"https://gd.cbss.10010.com/essframe?service=page/Sidebar",
				"https://gd.cbss.10010.com/essframe?service=page/Slip",
				"https://gd.cbss.10010.com/essframe?service=page/Notice&listener=init"};
			
		for(String url:urls){
			hcb.getUrlRespHtml(url);	
		}
	}

	/**
	 * 登录后，重定向到操作首页
	 * @return 重定向后的页面
	 */
	private static String redirectToIndex() {
		String sid=MyTools.getCookieValue(hcb.getCurCookieList(),BSS_JSESSIONID);
		String redirectLoginUrl="https://gd.cbss.10010.com/essframe;BSS_JSESSIONID="+sid;
		List<NameValuePair> postDict = new ArrayList<NameValuePair>();
		/**如下是最关键的三个参数*/
		postDict.add(new BasicNameValuePair("STAFF_ID","DSZDGL01"));//工号，即登录名
		postDict.add(new BasicNameValuePair("LOGIN_PASSWORD","+cfManXzORZEM6m7RxVvIztGm4w="));//加密后的密码
		postDict.add(new BasicNameValuePair("LOGIN_PROVINCE_CODE","51"));//省份编码
		/**如下参数为空值的不能去掉！！！否则可能无法正常登录！*/
		postDict.add(new BasicNameValuePair("ACTION_MODE",""));
		postDict.add(new BasicNameValuePair("Form0","ACTION_MODE,STAFF_ID,LOGIN_PASSWORD,NEED_SMS_VERIFY,SUBSYS_CODE,LOGIN_TYPE,authDomainType,soap,menuId,error,authType,LOGIN_PROVINCE_CODE,VERIFY_CODE,WHITE_LIST_LOGIN,IPASS_SERVICE_URL,IPASS_CHECK_MESSAGE,IPASS_LOGIN_PROVINCE,SIGNATURE_CODE,SIGNATURE_DATA,IPASS_LOGIN,IPASS_ACTIVATE,NEED_INSTALL_CERT,IPASS_INSTALL_RESULT,IPASS_INSTALL_MESSAGE,IPASS_LOGINOUT_DOMAIN,btnProxyLogin"));
		postDict.add(new BasicNameValuePair("IPASS_ACTIVATE",""));
		postDict.add(new BasicNameValuePair("IPASS_CHECK_MESSAGE",""));
		postDict.add(new BasicNameValuePair("IPASS_INSTALL_MESSAGE",""));
		postDict.add(new BasicNameValuePair("IPASS_INSTALL_RESULT",""));
		postDict.add(new BasicNameValuePair("IPASS_LOGIN",""));
		postDict.add(new BasicNameValuePair("IPASS_LOGINOUT_DOMAIN",""));
		postDict.add(new BasicNameValuePair("IPASS_LOGIN_PROVINCE",""));
		postDict.add(new BasicNameValuePair("IPASS_SERVICE_URL",""));
		postDict.add(new BasicNameValuePair("LOGIN_TYPE","redirectLogin"));
		postDict.add(new BasicNameValuePair("NEED_INSTALL_CERT",""));
		postDict.add(new BasicNameValuePair("NEED_SMS_VERIFY",""));
		postDict.add(new BasicNameValuePair("SIGNATURE_CODE",""));
		postDict.add(new BasicNameValuePair("SIGNATURE_DATA",""));
		postDict.add(new BasicNameValuePair("SUBSYS_CODE",""));
		postDict.add(new BasicNameValuePair("VERIFY_CODE",""));
		postDict.add(new BasicNameValuePair("WHITE_LIST_LOGIN",""));
		postDict.add(new BasicNameValuePair("authDomainType",""));
		postDict.add(new BasicNameValuePair("authType",""));
		postDict.add(new BasicNameValuePair("btnProxyLogin","提交查询内容"));
		postDict.add(new BasicNameValuePair("error",""));
		postDict.add(new BasicNameValuePair("menuId",""));
		postDict.add(new BasicNameValuePair("service","direct/1/LoginProxy/$Form"));
		postDict.add(new BasicNameValuePair("soap",""));
		postDict.add(new BasicNameValuePair("sp","S0"));

		List<NameValuePair> headParams = new ArrayList<NameValuePair>();
		//非常关键，如果没有设置Referer，服务端会报错
		headParams.add(new BasicNameValuePair("Referer","https://gd.cbss.10010.com/essframe?service=page/LoginProxy&login_type=redirectLogin"));
		
		String redirectHtml = hcb.getUrlRespHtml(redirectLoginUrl, headParams, postDict);
//		MyTools.logToFile("d:\\3-重定向.html",redirectHtml);
		
		hcb.dbgPrintCookies(hcb.getCurCookieList(), redirectLoginUrl);
		
		return redirectHtml;
	}

	/**
	 * 发送登录请求
	 * @return
	 */
	private static String sendLoginRequest() {
		String loginUrl="https://gd.cbss.10010.com/essframe?service=page/LoginProxy&login_type=redirectLogin&service=direct%2F1%2FHome%2F%24Form&sp=S0&Form0=LOGIN_PROVINCE_REDIRECT_URL%2CAUTH_TYPE%2CCAPTURE_URL%2CWHITE_LIST_LOGIN%2CIPASS_LOGIN%2CIPASS_SERVICE_URL%2CIPASS_LOGIN_PROVINCE%2CIPASS_LOGINOUT_DOMAIN%2CSIGNATURE_CODE%2CSIGNATURE_DATA%2CIPASS_ACTIVATE%2CSTAFF_ID%2C%24FormConditional%2C%24FormConditional%240%2CLOGIN_PROVINCE_CODE%2C%24FormConditional%241%2C%24FormConditional%242%2C%24FormConditional%243%2C%24FormConditional%244%2C%24FormConditional%245%2C%24TextField%2C%24TextField%240%2C%24TextField%241%2C%24TextField%242%2C%24TextField%243%2C%24TextField%244%2C%24TextField%245%2C%24TextField%246%2C%24TextField%247%2C%24TextField%248%2C%24TextField%249%2C%24TextField%2410%2C%24TextField%2411%2C%24TextField%2412%2C%24TextField%2413%2C%24TextField%2414%2C%24TextField%2415%2C%24TextField%2416%2C%24TextField%2417%2C%24TextField%2418%2C%24TextField%2419%2C%24TextField%2420%2C%24TextField%2421%2C%24TextField%2422%2C%24TextField%2423%2C%24TextField%2424%2C%24TextField%2425%2C%24TextField%2426%2C%24TextField%2427%2C%24TextField%2428%2C%24TextField%2429%2C%24TextField%2430&%24FormConditional=F&%24FormConditional%240=T&%24FormConditional%241=F&%24FormConditional%242=F&%24FormConditional%243=F&%24FormConditional%244=F&%24FormConditional%245=F&LOGIN_PROVINCE_REDIRECT_URL=https%3A%2F%2Fgd.cbss.10010.com%2Fessframe&AUTH_TYPE=0&CAPTURE_URL=%2Fimage%3Fmode%3Dvalidate%26width%3D60%26height%3D20&WHITE_LIST_LOGIN=&IPASS_LOGIN=&IPASS_SERVICE_URL=&IPASS_LOGIN_PROVINCE=&IPASS_LOGINOUT_DOMAIN=&SIGNATURE_CODE=&SIGNATURE_DATA=&IPASS_ACTIVATE=&STAFF_ID=DSZDGL01&LOGIN_PASSWORD=%2BcfManXzORZEM6m7RxVvIztGm4w%3D&LOGIN_PROVINCE_CODE=51";
		
		String afterLoginHtml = hcb.getUrlRespHtml(loginUrl);
//		MyTools.logToFile("d:\\2-登录后.html",afterLoginHtml);
		
		hcb.dbgPrintCookies(hcb.getCurCookieList(), loginUrl);
		
		return afterLoginHtml;
	}

	/**
	 * 访问登录前首页
	 * @return  页面内容
	 */
	private static String visitLoginPage() {
		String preLoginUrl = "https://cbss.10010.com/essframe";
		String preLoginHtml = hcb.getUrlRespHtml(preLoginUrl);
//		MyTools.logToFile("d:\\1-登录前.html",preLoginHtml);
		
		hcb.dbgPrintCookies(hcb.getCurCookieList(), preLoginUrl);
		
		return preLoginHtml;
	}

}
