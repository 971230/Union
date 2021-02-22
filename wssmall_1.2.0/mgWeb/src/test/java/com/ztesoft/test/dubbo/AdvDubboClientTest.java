package com.ztesoft.test.dubbo;

import org.junit.Assert;
import org.testng.annotations.Test;

import params.ZteResponse;

import com.ztesoft.api.ZteClient;
import com.ztesoft.remote.params.adv.req.AdColumnReq;
import com.ztesoft.remote.params.adv.req.AdvCountReq;
import com.ztesoft.remote.params.adv.req.AdvHomeReq;
import com.ztesoft.remote.params.adv.req.AdvPosReq;
import com.ztesoft.remote.params.adv.req.AdvReq;
import com.ztesoft.remote.params.adv.req.DelAdvReq;
import com.ztesoft.remote.params.adv.resp.AdColumnResp;
import com.ztesoft.remote.params.adv.resp.AdvCountResp;
import com.ztesoft.remote.params.adv.resp.AdvHomeResp;
import com.ztesoft.remote.params.adv.resp.AdvResp;
import com.ztesoft.remote.params.adv.resp.EmptyParamOutResp;
import com.ztesoft.remote.params.article.req.ArticleReq;
import com.ztesoft.remote.params.article.req.DelArticleReq;
import com.ztesoft.remote.params.article.resp.ArticleResp;
import com.ztesoft.remote.params.article.resp.DelArticleResp;
import com.ztesoft.remote.params.news.req.NewsCountReq;
import com.ztesoft.remote.params.news.req.NewsPageReq;
import com.ztesoft.remote.params.news.req.NewsReq;
import com.ztesoft.remote.params.news.req.NewsVoReq;
import com.ztesoft.remote.params.news.resp.NewsCountResp;
import com.ztesoft.remote.params.news.resp.NewsPageResp;
import com.ztesoft.remote.params.news.resp.NewsResp;
import com.ztesoft.remote.params.notice.req.NoticeDetailReq;
import com.ztesoft.remote.params.notice.req.NoticeReq;
import com.ztesoft.remote.params.notice.resp.NoticeDetailResp;
import com.ztesoft.remote.params.notice.resp.NoticeResp;
import com.ztesoft.test.dubbo.base.DubboClientTest;

public class AdvDubboClientTest extends DubboClientTest{
	//-----------失败------
	@Test(enabled=false)
	public void queryAdByAcId(){
		AdvReq advReq=new AdvReq();
		advReq.setAcid("1000901022");
		advReq.setUser_id("-1");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(advReq, AdvResp.class);
		logger.info("--------"+response.getError_msg());
		Assert.assertEquals(response.getError_msg(), "0");
	}
	//-----------失败------
	@Test(enabled=true)
	public void delAdvbyStaffNo(){
		DelAdvReq delAdvReq=new DelAdvReq();
		delAdvReq.setStaff_no("1");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(delAdvReq, EmptyParamOutResp.class);
		logger.info("--------"+response.getError_msg());
		Assert.assertEquals(response.getError_msg(), "0");
	}
	//成功
	@Test(enabled=false)
	public void getAdvCount(){
		AdvCountReq advCountReq=new AdvCountReq();
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(advCountReq, AdvCountResp.class);
		logger.info("--------"+response.getError_msg());
//		Assert.assertEquals(response.getError_msg(), "0");
	}
	@Test(enabled=false)
	public void getAdColumnDetail(){
		AdColumnReq adColumnReq=new AdColumnReq();
//		adColumnReq.setAcid("1");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(adColumnReq, AdColumnResp.class);
		logger.info("--------"+response.getError_msg());
		Assert.assertEquals(response.getError_msg(), "0");
	}
	@Test(enabled=false)
	public void listAllAdvPos(){
		AdvPosReq advPosReq=new AdvPosReq();
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(advPosReq, AdColumnResp.class);
		logger.info("--------"+response.getError_msg());
		Assert.assertEquals(response.getError_msg(), "0");
	}
	@Test(enabled=false)
	public void getHomeAdv(){
		AdvHomeReq advHomeReq=new AdvHomeReq();
		advHomeReq.setProduct_id("12");//商品名称
		advHomeReq.setService_code("CO_DINGDAN");//规则编码
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(advHomeReq, AdvHomeResp.class);
		logger.info("--------"+response.getError_msg());
		Assert.assertEquals(response.getError_msg(), "0");
	}
	@Test(enabled=false)
	public void getArticleById(){
		ArticleReq articleReq=new ArticleReq();
		articleReq.setId("5");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(articleReq, ArticleResp.class);
		logger.info("--------"+response.getError_msg());
		Assert.assertEquals(response.getError_msg(), "0");
	}
	@Test(enabled=false)
	public void delArticleByStaffNo(){
		DelArticleReq delArticleReq=new DelArticleReq();
		delArticleReq.setStaff_no("1");//工号
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(delArticleReq, DelArticleResp.class);
		logger.info("--------"+response.getError_msg());
		Assert.assertEquals(response.getError_msg(), "0");
	}
	@Test(enabled=false)
	public void queryNews(){
		NewsReq newsReq=new NewsReq();
		newsReq.setLv_id("-1");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(newsReq, NewsResp.class);
		logger.info("--------"+response.getError_msg());
		Assert.assertEquals(response.getError_msg(), "0");
	}
	@Test(enabled=false)
	public void getNewsPage(){
		NewsPageReq newsPageReq=new NewsPageReq();
		newsPageReq.setPageNo(1);
		newsPageReq.setPageSize(10);
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(newsPageReq, NewsPageResp.class);
		logger.info("--------"+response.getError_msg());
		Assert.assertEquals(response.getError_msg(), "0");
	}
	@Test(enabled=false)
	public void getNewsVo(){
		NewsVoReq newsVoReq=new NewsVoReq();
		newsVoReq.setNews_id("201309161549000160");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(newsVoReq, NewsResp.class);
		logger.info("--------"+response.getError_msg());
		Assert.assertEquals(response.getError_msg(), "0");
	}
	@Test(enabled=false)
	public void getNewsCount(){
		NewsCountReq newsCountReq=new NewsCountReq();
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(newsCountReq, NewsCountResp.class);
		logger.info("--------"+response.getError_msg());
		Assert.assertEquals(response.getError_msg(), "0");
	}
	@Test(enabled=false)
	public void queryNotice(){
		NoticeReq noticeReq=new NoticeReq();
		noticeReq.setCatid("201403138514000001");//分类Id
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(noticeReq, NoticeResp.class);
		logger.info("--------"+response.getError_msg());
		Assert.assertEquals(response.getError_msg(), "0");
	}
	@Test(enabled=false)
	public void queryNoticeDetails(){
		NoticeDetailReq noticeDetailReq=new NoticeDetailReq();
		noticeDetailReq.setCatid("1");
		noticeDetailReq.setArticleid("201403138514000001");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(noticeDetailReq, NoticeDetailResp.class);
		logger.info("--------"+response.getError_msg());
		Assert.assertEquals(response.getError_msg(), "0");
	}
}
