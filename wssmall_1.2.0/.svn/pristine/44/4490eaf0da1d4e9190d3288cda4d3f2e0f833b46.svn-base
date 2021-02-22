package zte.params.comments.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class CommentListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品ID",type="String",isNecessary="Y",desc="type_id：商品ID。")
	private String goods_id;
	@ZteSoftCommentAnnotationParam(name="评论类型",type="String",isNecessary="Y",desc="comment_type：评论类型：goods、wssdetails。")
	private String comment_type;
	private String object_type;
	@ZteSoftCommentAnnotationParam(name="页码",type="String",isNecessary="N",desc="pageNo：页码，返回第几页数据，默认为1。")
	private int pageNo=1;
	@ZteSoftCommentAnnotationParam(name="分页大小",type="String",isNecessary="N",desc="pageSize：分页大小，每页多少条数据，默认为10。")
	private int pageSize=10;
	
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

	public String getObject_type() {
		return object_type;
	}

	public void setObject_type(String object_type) {
		this.object_type = object_type;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public void check() throws ApiRuleException {
		if(goods_id==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"goods_id不能为空"));
		if(comment_type==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"comment_type不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.commentsService.comments.list";
	}

}
