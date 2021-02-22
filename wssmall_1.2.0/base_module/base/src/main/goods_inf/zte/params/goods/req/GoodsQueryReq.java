package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;

public class GoodsQueryReq extends ZteRequest {

	private String goods_id ;

	private String commentPageSize;
	
	private String commentPageIndex;
	
	private String commentObject_type;
	
	private String commentType;
	
	private String discussPageSize;
	
	private String discussPageIndex;
	
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
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

}
