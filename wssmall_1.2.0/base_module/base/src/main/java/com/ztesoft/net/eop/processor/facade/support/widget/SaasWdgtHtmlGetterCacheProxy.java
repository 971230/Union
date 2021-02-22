package com.ztesoft.net.eop.processor.facade.support.widget;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.app.base.core.service.IWidgetCacheManager;
import com.ztesoft.net.cache.common.SerializeObject;
import com.ztesoft.net.eop.processor.widget.IWidgetHtmlGetter;
import com.ztesoft.net.eop.resource.model.EopSite;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.eop.sdk.widget.IWidget;
import com.ztesoft.net.framework.cache.AbstractMemCacheProxy;
import com.ztesoft.net.framework.cache.CacheFactory;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.core.utils.UrlUtils;

/**
 * Saas挂件html缓存代理<br>
 * 缓存规则：
 * 以 userid_siteid为key划分出缓存区域，以map作为值。
 * map中存储此站点的挂件html片断 map
 * 
 * @author kingapex
 *
 */
public class SaasWdgtHtmlGetterCacheProxy extends AbstractMemCacheProxy<Map<String,String>>
		implements IWidgetHtmlGetter {
	private IWidgetHtmlGetter widgetHtmlGetter;

	public SaasWdgtHtmlGetterCacheProxy(IWidgetHtmlGetter _widgetHtmlGetter) {
		super(CacheFactory.WIDGET_CACHE_NAME_KEY);
		this.widgetHtmlGetter = _widgetHtmlGetter;
	} 
	
	private boolean getCacheOpen(){
		 IWidgetCacheManager widgetCacheManager =  SpringContextHolder.getBean("widgetCacheManager");
		 return  widgetCacheManager.isOpen();
	}
	/**
	 * 实现挂件解析的缓存包装
	 */
	@Override
	@SuppressWarnings("unused")
	public String process(Map<String, String> params, String pageUri) {
		String widgetType = params.get("type");
		String html = null;
		
		//得到挂件类
		IWidget widget = SpringContextHolder.getBean(widgetType);
		if (widget == null)
			return "widget[" + widgetType + "] is null";
		
		//初始化参数(静态模式，在这里初始化参数)
		if(widget.isStaticMode())
			widget.initParam();
		
		//以当前站点作为整站挂件缓存的key
		//缓存的是一个map，map的key为当前页面uri和挂件id的组合
		EopSite site = EopContext.getContext().getCurrentSite();
		String site_key = "widget_"+ site.getUserid() +"_"+site.getId();
		
		//由缓存中找此站点的挂件级存集合
		@SuppressWarnings("unchecked")
		Map<String,String> htmlCache = (Map<String,String>) this.getCache(site_key);
		
		//未找到缓存，生成一个新map
		if(htmlCache==null){
			htmlCache = new HashMap<String, String>();
		}
//		if(pageUri.indexOf("mshop-apprelid-10001-page_id-100001")>-1)
//			logger.info("111111111111111");
//		if(widgetType.equals("shopImageNewsWidget"))
//			logger.info("2222222222");
		// 挂件可以被缓存，则尝试由缓存读取
		if (getCacheOpen() && widget.cacheAble() && widget.isStaticMode()){
			//挂件map集合的key
			String key = pageUri + "_" + params.get("widgetid");
			if(ThreadContextHolder.isMemberLvCache()){
				String lv_id = ManagerUtils.getCurrentLvId();
				key += "_"+lv_id;
			}
			//add by wui按key缓存
			String page_id =this.getPageId();
			if(!StringUtil.isEmpty(page_id))
				key+="_"+page_id;
			//获取挂件html
			html = htmlCache.get(key);
			
			// 缓存未命中，解析挂件的内容并压入缓存 html==null
			if (html==null || StringUtil.isEmpty(html)) { 
				html = widgetHtmlGetter.process(params, pageUri);
				htmlCache.put(key, html);
				
			// 如果缓存命中，则只执行挂件更新操作	
			} else { 
				widget.update(params);
			}
			this.setCache(site_key, htmlCache);
		} else { // 挂件不缓存，由htmlGetter解析
			html = widgetHtmlGetter.process(params, pageUri);
		}
		return html;
	}
	
	//设置缓存
	public void setCache(String key, Object value){
		SerializeObject serializeObj = new SerializeObject();
		serializeObj.setObj(value);
		cache.set(Const.WIDGET_CACHE_SPACE, key, serializeObj);
	}
	
	//获取缓存
	public Object getCache(String key){
		SerializeObject obj = (SerializeObject)cache.get(Const.WIDGET_CACHE_SPACE, key);
		if(null == obj)
			return null;
		return obj.getObj();
	}
	
	public String getPageId(){
		HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
		String uri = httpRequest.getServletPath();
		String p= UrlUtils.getParamStr(uri);
		String page_id = UrlUtils.getParamStringValue(p, "page_id");
		return page_id;
	}
	
}