package com.ztesoft.net.mall.widget.goods.search;

import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.utils.UrlUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchUrlDirectiveModel implements TemplateDirectiveModel {

	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		String type  = params.get("type").toString();
		String value  = params.get("value").toString();
		Object menu = params.get("menu");
	 	
		
		HttpServletRequest request  =ThreadContextHolder.getHttpRequest(); 
		String url = request.getServletPath();
		
		if("/index_mobile.html".equals(url) || url.indexOf("index")>-1 || url.indexOf("goods")>-1){ //add by wui 首页访问，热词链接地址
			url ="/wssquery-keyword-.html";
		}
		if(menu!=null && "main".equals(menu.toString())){
			url ="/wssquery-keyword-.html";
		}
		
		String encode = request.getCharacterEncoding();
		url  = StringUtil.toUTF8(url);  //add by wui全部用uft-8编码
        if(url.lastIndexOf(".")!=-1){
            url= url.substring(0,url.lastIndexOf("."));//UrlUtils.getParamStr(url); //add by wui
        }

		if("all".equals(value)){
			if( "brand".equals(type) ){
				url= UrlUtils.getExParamUrl(url, type);
			}if( "tag".equals(type) ){
				url= UrlUtils.getExParamUrl(url, type);
			}else if("prop".equals(type)){
			  String index =params.get("index").toString();
			   url = UrlUtils.getPropExSelf(Integer.valueOf(index), url);
			}
			 url =url+".html";
		}else{
			if(!type.equals("prop") )
				url= UrlUtils.getExParamUrl(url, type);
			url= UrlUtils.appendParamValue(url, type, value);
			//url =url+".html";
		}
		//env.getOut().write("search-"+url+".html"); //add by wui
		if(url.equals("/wssquery-keyword-.html"))
			url ="/wssquery-prop-0_0.html";
		if(!type.equals("agn") && !StringUtil.isEmpty(StringUtil.getAgnId()))
			url = UrlUtils.addUrl(url, "agn", StringUtil.getAgnId());
		
		Pattern p = Pattern.compile("-page-\\d+", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(url);
		url=m.replaceAll("");
		env.getOut().write(url.substring(1));
	}

}
