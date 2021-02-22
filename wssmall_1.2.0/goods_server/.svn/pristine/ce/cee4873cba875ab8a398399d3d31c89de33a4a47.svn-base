package com.ztesoft.net.service;

import java.util.Map;

import services.ServiceBase;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.service.ICommentsManager;

import zte.net.iservice.ICommentsService;
import zte.params.comments.req.CommentListReq;
import zte.params.comments.req.CommentsGradeCountGetReq;
import zte.params.comments.req.CommentsPageListReq;
import zte.params.comments.req.ObjectTypeCountGetReq;
import zte.params.comments.resp.CommentListResp;
import zte.params.comments.resp.CommentsGradeCountGetResp;
import zte.params.comments.resp.CommentsPageListResp;
import zte.params.comments.resp.ObjectTypeCountGetResp;

public class CommentsService extends ServiceBase implements ICommentsService {

	private ICommentsManager commentsManager;
	
	public ICommentsManager getCommentsManager() {
		return commentsManager;
	}

	public void setCommentsManager(ICommentsManager commentsManager) {
		this.commentsManager = commentsManager;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "商品评论列表", summary = "商品的评论")
	public CommentsPageListResp listGoodsComments(CommentsPageListReq req) {
		CommentsPageListResp resp = new CommentsPageListResp();
		int pageNo = req.getPageNo();
		int pageSize = req.getPageSize();
        String object_id=req.getGoods_id();
		//Integer object_id = Integer.valueOf(req.getGoods_id());
		String commenttype = req.getComment_type();
		Page page = commentsManager.listAll(pageNo, pageSize, object_id, commenttype);
		resp.setPage(page);
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req,resp);
		return resp;
	}

	@Override
	public CommentsGradeCountGetResp getGradeCount(CommentsGradeCountGetReq req) {
		String comment_type = req.getComment_type();
		String goods_id = req.getGoods_id();
		Map grade = commentsManager.coutObjectGrade(comment_type, Integer.valueOf(goods_id));
		
		CommentsGradeCountGetResp resp = new CommentsGradeCountGetResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setGrade(grade);
		addReturn(req,resp);
		return resp;
	}

	@Override
	public ObjectTypeCountGetResp getObjectTypeCount(ObjectTypeCountGetReq req) {
		String comment_type = req.getComment_type();
		String goods_id = req.getGoods_id();
		Map grade = commentsManager.coutObjectGrade(comment_type, Integer.valueOf(goods_id));
		
		ObjectTypeCountGetResp resp = new ObjectTypeCountGetResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setObjTypeCount(grade);
		addReturn(req,resp);
		return resp;
	}

	@Override
	public CommentListResp listComments(CommentListReq req) {
		int pageNo = req.getPageNo();
		int pageSize = req.getPageSize();
		Long object_id = Long.valueOf(req.getGoods_id());
		String object_type = req.getObject_type();
		String commentType = req.getComment_type();
		Page page = commentsManager.pageComments_Display(pageNo, pageSize, object_id, object_type, commentType);
		CommentListResp resp = new CommentListResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setPage(page);
		addReturn(req,resp);
		return resp;
	}

}
