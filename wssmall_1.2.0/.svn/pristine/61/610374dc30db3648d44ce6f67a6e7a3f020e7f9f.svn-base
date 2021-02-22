package zte.net.iservice;

import zte.params.comments.req.CommentListReq;
import zte.params.comments.req.CommentsGradeCountGetReq;
import zte.params.comments.req.CommentsPageListReq;
import zte.params.comments.req.ObjectTypeCountGetReq;
import zte.params.comments.resp.CommentListResp;
import zte.params.comments.resp.CommentsGradeCountGetResp;
import zte.params.comments.resp.CommentsPageListResp;
import zte.params.comments.resp.ObjectTypeCountGetResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="商品评论API",summary="商品评论API[商品评论信息]")
public interface ICommentsService {

	@ZteSoftCommentAnnotation(type="method",desc="获取商品评论信息",summary="获取商品评论信息")	
	public CommentsPageListResp listGoodsComments(CommentsPageListReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取商品评论等级统计信息",summary="获取商品评论等级统计信息")	
	public CommentsGradeCountGetResp getGradeCount(CommentsGradeCountGetReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取评论类型统计信息",summary="获取评论类型统计信")	
	public ObjectTypeCountGetResp getObjectTypeCount(ObjectTypeCountGetReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取所有评论",summary="获取所有评论")
	public CommentListResp listComments(CommentListReq req);
}
