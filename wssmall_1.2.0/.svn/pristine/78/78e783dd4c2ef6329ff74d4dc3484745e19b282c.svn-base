package com.ztesoft.remote.inf;

import com.ztesoft.remote.params.article.req.ArticleReq;
import com.ztesoft.remote.params.article.req.DelArticleReq;
import com.ztesoft.remote.params.article.resp.ArticleResp;
import com.ztesoft.remote.params.article.resp.DelArticleResp;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="帮助与支持API",summary="帮助与支持相关管理API")
public interface IArticleService {

	@ZteSoftCommentAnnotation(type="method",desc="根据标识获取帮助与支持信息",summary="根据标识获取帮助与支持信息")
	public ArticleResp getArticleById(ArticleReq articleReq);
	
	@ZteSoftCommentAnnotation(type="method",desc="根据工号删除帮助与支持信息",summary="根据工号删除帮助与支持信息")
	public DelArticleResp delArticleByStaffNo(DelArticleReq delArticleReq);
	
}
