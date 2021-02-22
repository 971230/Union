package com.ztesoft.net.eop.resource.impl.cache;

import com.ztesoft.net.eop.processor.core.EopException;
import com.ztesoft.net.eop.resource.IThemeUriManager;
import com.ztesoft.net.eop.resource.model.EopSite;
import com.ztesoft.net.eop.resource.model.ThemeUri;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.framework.cache.AbstractCacheProxy;
import com.ztesoft.net.framework.cache.CacheFactory;
import com.ztesoft.net.framework.util.StringUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * Theme Uri 缓存代理
 * @author kingapex
 * <p>2009-12-16 下午05:48:25</p>
 * @version 1.0
 */
public class ThemeUriCacheProxy extends AbstractCacheProxy<List<ThemeUri>> implements IThemeUriManager {

	private IThemeUriManager themeUriManager;
	public static final String LIST_KEY_PREFIX ="theme_uri_list_";
	private static Logger logger = Logger.getLogger(ThemeUriCacheProxy.class);

	@Override
	public void clean() {
		this.themeUriManager.clean();
	}
	@Override
	public void add(ThemeUri uri) {
		 this.themeUriManager.add(uri);
		 this.cleanCache();
	}
	
	@Override
	public void edit(List<ThemeUri> uriList) {
		themeUriManager.edit(uriList);
		 this.cleanCache();
	}	
	
	@Override
	public void edit(ThemeUri themeUri) {
		themeUriManager.edit(themeUri);
		this.cleanCache();
	}
	
	
	public ThemeUriCacheProxy(IThemeUriManager themeUriManager) {
		super(CacheFactory.THEMEURI_CACHE_NAME_KEY);
		this.themeUriManager = themeUriManager;
	}

	private void cleanCache(){
		EopSite site  = EopContext.getContext().getCurrentSite();
		String userid  = site.getUserid();
		String siteid  = site.getId();
		this.cache.remove(LIST_KEY_PREFIX+userid+"_"+siteid);
	}
	
	@Override
	public List<ThemeUri> list() {
			EopSite site  = EopContext.getContext().getCurrentSite();
			String userid  = site.getUserid();
			String siteid  = site.getId();
			List<ThemeUri> uriList  = this.cache.get(LIST_KEY_PREFIX+userid+"_"+siteid);
			
			if(uriList==null || 0==uriList.size()){
//				if(logger.isDebugEnabled()){
//					logger.debug("get user:"+userid+" site: "+ siteid +" theme uri list from database");
//				}
				uriList = this.themeUriManager.list();
				this.cache.put(LIST_KEY_PREFIX+userid+"_"+siteid, uriList);
			}else{
//				if(logger.isDebugEnabled()){
//					logger.debug("get user:"+userid+" site: "+ siteid +" theme uri list from cache");
//				}				
			}
			
 		return uriList;
	}
	
	
	/**
	 * 从缓存列表中匹配uri
	 */
	
	@Override
	public ThemeUri getPath( String uri) {
		if(uri.equals("/")){
			uri="/index.html";
		}
//		if(logger.isDebugEnabled()){
//			logger.debug("parse uri["+ uri+"]...");
//		}	
		try { //add by wui 中文编码转换
			uri= new String(uri.getBytes("iso8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
			
		List<ThemeUri> uriList  =  this.list();
		int i=0;
		for( ThemeUri themeUri: uriList){
			String key = themeUri.getUri();
			String path = themeUri.getPath();
			Pattern p = Pattern.compile(key, 2 | Pattern.DOTALL);
			Matcher m = p.matcher(uri);
			i++;
			if(i==42){
				logger.info("__________"+i);
				logger.info("__________"+i);
				
			}
			
//			if(logger.isDebugEnabled()){
//				logger.debug("compile["+key+"],matcher["+uri+"],replace["+path+"]");
//			}	
			
			if (m.find()) {
				//String s = m.replaceAll(path);
//				if(logger.isDebugEnabled()){
//					logger.debug("found...");
//					//logger.debug("dispatch  uri["+key+"=="+ uri+"] to "+ s+"["+path+"]");
//					
//				}
				
				if((themeUri.getPath().equals("/wssdetails.html") && themeUri.getUri().equals("/wssdetails-(\\d+).html"))
					||(themeUri.getPath().equals("/goods_mobile.html"))
					||(themeUri.getUri().equals("/wssquery-(.*).html") && themeUri.getPath().equals("/default.html"))
					||(themeUri.getUri().equals("/wssquery-(.*).html") && themeUri.getPath().equals("/default2.html"))
					||(themeUri.getUri().equals("/wssquery-(.*).html") && themeUri.getPath().equals("/default_mobile.html"))
					||(themeUri.getUri().equals("/user_(.*).html") && themeUri.getPath().equals("/user.html"))
					||(themeUri.getPath().equals("/user_mobile.html")))
				{
						if(!StringUtil.getThemePath().equals("mobile")){
							themeUri.setPath(themeUri.getPath().replaceAll("_mobile",""));
						}else{
							if(!(themeUri.getPath().indexOf("mobile")>-1))
								themeUri.setPath(themeUri.getPath().substring(0, themeUri.getPath().indexOf("."))+"_"+StringUtil.getThemePath()+themeUri.getPath().substring(themeUri.getPath().indexOf(".")));
						}
				}
				
				return themeUri;
			}else{
//				if(logger.isDebugEnabled())
//					logger.debug("not found...");
			}
		}
		
		throw new EopException(uri + " not found");
	}

	
	public static void main(String[] args){
/*		Pattern p = Pattern.compile("/goods-(\\d+).html", 2 | Pattern.DOTALL);
		Matcher m = p.matcher("/goods-1.html");  
		String s = m.replaceAll("/goods.do?id=$1");		
		logger.info(s);*/
//[/goods-(\\d+).html],matcher[/goods-1.html],replace[/goods.jsp]
		Pattern p = Pattern.compile("/goods-(\\d+).html", 2 | Pattern.DOTALL);
		Matcher m = p.matcher("/goods-1.html");  
		if(m.find()){
			logger.info("found...");
			String s = m.replaceAll("/goods.jsp");		
			logger.info(s);			
		}else{
			logger.info("not found...");
		}

	}
	
	@Override
	public void delete(int id){
		this.themeUriManager.delete(id);
		this.cleanCache();
	}

	@Override
	public ThemeUri get(Integer id) {
		return this.themeUriManager.get(id);
	}




}
