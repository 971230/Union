package com.ztesoft.net.app.base.core.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.annotation.Resource;

import com.ztesoft.net.app.base.core.service.IPageInfoManager;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.database.Page;

import consts.ConstsCore;

/**
 * 分页总数信息Action
 * @author shizx
 *
 */
public class PageInfoAction extends WWAction {
	
	private static final long serialVersionUID = 6122202109863606039L;
	
	private String url;
	
	private String formReqUrl;
	
	@Resource
	private IPageInfoManager pageInfoManger;
	
	/**
	 * 返回页码总数与最后一页信息
	 */
	public String info(){
		
		try{
			final String sessionId = getRequest().getSession().getId();
			final int totalCount = pageInfoManger.getPageCountInfo(sessionId, url);
			
			Page page = new Page();
			page.setPageSize(getPageSize());
			page.setTotalCount(totalCount);
			
			final long totalPageCount = page.getTotalPageCount();
			logger.info("记录总数=" + totalCount);
			logger.info("页码总数=" + totalPageCount);
			
			this.json="{result:0,totalCount:"+ totalCount +",pageCount:"+ totalPageCount +",bodyString:'" + getBodyString(totalPageCount) + "'}";
		}catch(RuntimeException e){
			this.json="{result:0,message:'"+e.getMessage()+"'}";
		}
		return WWAction.JSON_MESSAGE;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	/**
	 * 生成分页主体字串
	 * @return
	 */
	private String getBodyString(long totalPageCount) {
		int showCount = 5;
		StringBuffer pageStr = new StringBuffer();

		long start = page - showCount / 2;  //pageNum
		start = start <= 1 ? 1 : start;

		long end = start + showCount;
		end = end > totalPageCount ? totalPageCount : end;
		
		try {
			final String decodeURI = URLDecoder.decode(formReqUrl,"UTF-8");
			
			for (long i = start; i <= end; i++) {
				pageStr.append("<li name='gotopage'><a ");
				if (i != page) {
					pageStr.append(" class=\"unselected\"");
					pageStr.append(decodeURI.replace(String.valueOf(ConstsCore.PAGE_COUNT_ASY_FLAG), String.valueOf(i)));
				} else {
					pageStr.append(" class=\"selected\">");
					// 测试
				}

			 
				pageStr.append(i);
				pageStr.append("</a></li>\n");

			}
			
			return URLEncoder.encode(pageStr.toString(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 

		return "";		
	}

	public String getFormReqUrl() {
		return formReqUrl;
	}

	public void setFormReqUrl(String formReqUrl) {
		this.formReqUrl = formReqUrl;
	}
}