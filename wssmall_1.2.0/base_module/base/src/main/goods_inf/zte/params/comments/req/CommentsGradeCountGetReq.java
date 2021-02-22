package zte.params.comments.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class CommentsGradeCountGetReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="评论类型",type="String",isNecessary="Y",desc="comment_type：goods|wssdetails")	
	private String comment_type;
	@ZteSoftCommentAnnotationParam(name="商品ID",type="String",isNecessary="Y",desc="goods_id：商品ID")	
	private String goods_id;
	
	public String getComment_type() {
		return comment_type;
	}

	public void setComment_type(String comment_type) {
		this.comment_type = comment_type;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(comment_type==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"comment_type不能为空"));
		if(goods_id==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"goods_id不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.comment.grade.count";
	}

}
