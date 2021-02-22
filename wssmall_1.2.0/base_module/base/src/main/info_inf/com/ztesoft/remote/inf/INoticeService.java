package com.ztesoft.remote.inf;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.remote.params.notice.req.NoticeDetailReq;
import com.ztesoft.remote.params.notice.req.NoticePageReq;
import com.ztesoft.remote.params.notice.req.NoticeReq;
import com.ztesoft.remote.params.notice.resp.NoticeDetailResp;
import com.ztesoft.remote.params.notice.resp.NoticePageResp;
import com.ztesoft.remote.params.notice.resp.NoticeResp;

@ZteSoftCommentAnnotation(type = "class", desc = "公告文章信息API", summary = "公告文章信息API")
public interface INoticeService {

	@ZteSoftCommentAnnotation(type = "method", desc = "获取公告信息", summary = "获取公告信息")
	public NoticeResp queryNotice(NoticeReq noticeReq);

	@ZteSoftCommentAnnotation(type = "method", desc = "获取公告 文章详情信息", summary = "获取公告 文章详情信息")
	public NoticeDetailResp queryNoticeDetails(NoticeDetailReq noticeReq);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "获取公告分页对象", summary = "获取公告分页对象")
	public NoticePageResp listNotice(NoticePageReq noticePageReq);
}