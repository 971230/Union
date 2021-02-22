package params.member.req;

import params.ZteRequest;
import zte.params.member.resp.CommentAddResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.Comments;

public class CommentAddReq extends ZteRequest<CommentAddResp> {

	@ZteSoftCommentAnnotationParam(name="用户ID",type="String",isNecessary="Y",desc="用户ID")
	private String member_id;
	@ZteSoftCommentAnnotationParam(name="评论信息",type="String",isNecessary="Y",desc="评论信息",hasChild=true)
	private Comments comment;
	@ZteSoftCommentAnnotationParam(name="类型",type="String",isNecessary="Y",desc="类型：1商品 2订单 默认为1")
	private String action = "1";
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public Comments getComment() {
		return comment;
	}
	public void setComment(Comments comment) {
		this.comment = comment;
	}
	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.memberService.comment.add";
	}
}
