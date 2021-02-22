package com.ztesoft.remote.inf.impl;

import javax.annotation.Resource;

import services.ServiceBase;

import com.ztesoft.net.app.base.core.model.Article;
import com.ztesoft.net.mall.core.service.IArticleManager;
import com.ztesoft.remote.inf.IArticleService;
import com.ztesoft.remote.params.article.req.ArticleReq;
import com.ztesoft.remote.params.article.req.DelArticleReq;
import com.ztesoft.remote.params.article.resp.ArticleResp;
import com.ztesoft.remote.params.article.resp.DelArticleResp;

public class ArticleService extends ServiceBase implements IArticleService{

    @Resource
	private IArticleManager articleManager;

	@Override
	public ArticleResp getArticleById(ArticleReq articleReq) {
		
		ArticleResp articleResp = new ArticleResp();
		
		String  id = articleReq.getId();
		Article article = this.articleManager.get(id);
		articleResp.setArticle(article);
		this.addReturn(articleReq, articleResp);
		return articleResp;
	}
	
	@Override
	public DelArticleResp delArticleByStaffNo(DelArticleReq delArticleReq) {
		
		 DelArticleResp delArticleResp = new DelArticleResp();
		 
		 String staff_no = delArticleReq.getStaff_no();
		 this.articleManager.delArticleByStaffNo(staff_no);
		
		 this.addReturn(delArticleReq, delArticleResp);
		 
		 return delArticleResp;
	}

}
