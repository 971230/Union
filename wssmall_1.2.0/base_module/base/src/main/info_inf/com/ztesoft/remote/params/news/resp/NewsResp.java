package com.ztesoft.remote.params.news.resp;

import java.util.List;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.NewsVO;

/**
 * 
 * @author wui 消息返回实体
 * 
 */
@SuppressWarnings("unchecked")
public class NewsResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="讯息列表",type="List",isNecessary="N",desc="讯息列表Lis<NewsVO>",hasChild=true)
	private List<NewsVO> newsList;
	
	@ZteSoftCommentAnnotationParam(name="讯息信息",type="NewsVO",isNecessary="N",desc="讯息信息",hasChild=true)
	private NewsVO newsVo;

	public List<NewsVO> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<NewsVO> newsList) {
		this.newsList = newsList;
	}

	public NewsVO getNewsVo() {
		return newsVo;
	}

	public void setNewsVo(NewsVO newsVo) {
		this.newsVo = newsVo;
	}

}
