package com.ztesoft.remote.inf;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.remote.params.news.req.NewsCountReq;
import com.ztesoft.remote.params.news.req.NewsPageReq;
import com.ztesoft.remote.params.news.req.NewsReq;
import com.ztesoft.remote.params.news.req.NewsVoReq;
import com.ztesoft.remote.params.news.resp.NewsCountResp;
import com.ztesoft.remote.params.news.resp.NewsPageResp;
import com.ztesoft.remote.params.news.resp.NewsResp;



/**
 *  消息处理类
 * @author wui
 *
 */
@ZteSoftCommentAnnotation(type="class",desc="消息快讯API",summary="消息快讯API")
public  interface INewsService  {

	@ZteSoftCommentAnnotation(type="method",desc="根据会员类型标识查询消息快讯信息",summary="根据会员类型标识查询消息快讯信息")
	public NewsResp queryNews(NewsReq newsReq);
	
	@ZteSoftCommentAnnotation(type="method",desc="根据会员类型标识查询消息快讯信息",summary="根据会员类型标识查询消息快讯信息（带分页的）")
	public NewsPageResp getNewsPage(NewsPageReq newsPageReq);
	
	@ZteSoftCommentAnnotation(type="method",desc="根据新闻快讯标识查询单条消息快讯信息",summary="根据新闻快讯标识查询单条消息快讯信息")
	public NewsResp getNewsVo(NewsVoReq newsVoReq);
	
	@ZteSoftCommentAnnotation(type="method",desc="查询消息快讯信息数量",summary="查询消息快讯信息数量")
	public NewsCountResp getNewsCount(NewsCountReq newsCountReq);
}