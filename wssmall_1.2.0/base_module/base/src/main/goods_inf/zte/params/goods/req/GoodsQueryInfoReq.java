package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class GoodsQueryInfoReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品ID",type="String",isNecessary="Y",desc="goods_id：商品基本信息查询商品ID 不能为空。")
	private String goods_id ;
	@ZteSoftCommentAnnotationParam(name="商品评论：每页记录数",type="String",isNecessary="Y",desc="每页记录数：默认10")
	private String commentPageSize = "10";
	@ZteSoftCommentAnnotationParam(name="商品评论：第几页",type="String",isNecessary="Y",desc="第几页 默认1")
	private String commentPageIndex = "1";
	
	private String commentObject_type;
	
	private String commentType;
	@ZteSoftCommentAnnotationParam(name="商品咨询：每页记录数",type="String",isNecessary="Y",desc="每页记录数：默认10")
	private String discussPageSize = "10";
	@ZteSoftCommentAnnotationParam(name="商品咨询：第几页",type="String",isNecessary="Y",desc="第几页 默认1")	
	private String discussPageIndex = "1";
	
	private String discussObject_type;
	
	private String discussType;
	
	public String getCommentPageSize() {
		return commentPageSize;
	}

	public void setCommentPageSize(String commentPageSize) {
		this.commentPageSize = commentPageSize;
	}

	public String getCommentPageIndex() {
		return commentPageIndex;
	}

	public void setCommentPageIndex(String commentPageIndex) {
		this.commentPageIndex = commentPageIndex;
	}

	public String getCommentObject_type() {
		return commentObject_type;
	}

	public void setCommentObject_type(String commentObject_type) {
		this.commentObject_type = commentObject_type;
	}

	public String getCommentType() {
		return commentType;
	}

	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}

	public String getDiscussPageSize() {
		return discussPageSize;
	}

	public void setDiscussPageSize(String discussPageSize) {
		this.discussPageSize = discussPageSize;
	}

	public String getDiscussPageIndex() {
		return discussPageIndex;
	}

	public void setDiscussPageIndex(String discussPageIndex) {
		this.discussPageIndex = discussPageIndex;
	}

	public String getDiscussObject_type() {
		return discussObject_type;
	}

	public void setDiscussObject_type(String discussObject_type) {
		this.discussObject_type = discussObject_type;
	}

	public String getDiscussType() {
		return discussType;
	}

	public void setDiscussType(String discussType) {
		this.discussType = discussType;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(goods_id==null || "".equals(goods_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "商品ID不允许为空"));
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.goodsService.goods.info.get";
	}

}
